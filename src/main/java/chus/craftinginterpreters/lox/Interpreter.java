package chus.craftinginterpreters.lox;

import java.util.List;

class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void> {
  private Environment environment = new Environment();

  void interpret(List<Stmt> statements) {
    try {
      for (Stmt statement : statements) {
        execute(statement);
      }
    } catch (RuntimeError error) {
      Lox.runtimeError(error);
    }
  }

  private void execute(Stmt stmt) {
    stmt.accept(this);
  }

  private Object evaluate(Expr expr) {
    return expr.accept(this);
  }

  // ===== Interpreting logic =====
  @Override
  public Void visitVarStmt(Stmt.Var stmt) {
    Object value = null;
    if (stmt.initializer != null) {
      value = evaluate(stmt.initializer);
    }

    environment.define(stmt.name.lexeme, value);
    return null;
  }

  @Override
  public Void visitPrintStmt(Stmt.Print stmt) {
    Object value = evaluate(stmt.expression);
    System.out.println(stringify(value));

    return null;
  }

  @Override
  public Void visitExpressionStmt(Stmt.Expression stmt) {
    evaluate(stmt.expression);

    return null;
  }

  @Override
  public Object visitBinaryExpr(Expr.Binary expr) {
    Object left = evaluate(expr.left);
    Object right = evaluate(expr.right);

    return switch (expr.operator.type) {
      case GREATER -> {
        checkNumberOperands(expr.operator, left, right);
        yield (double) left > (double) right;
      }
      case GREATER_EQUAL -> {
        checkNumberOperands(expr.operator, left, right);
        yield (double) left >= (double) right;
      }
      case LESS -> {
        checkNumberOperands(expr.operator, left, right);
        yield (double) left < (double) right;
      }
      case LESS_EQUAL -> {
        checkNumberOperands(expr.operator, left, right);
        yield (double) left <= (double) right;
      }
      case BANG_EQUAL -> !isEqual(left, right);
      case EQUAL_EQUAL -> isEqual(left, right);
      case MINUS -> {
        checkNumberOperands(expr.operator, left, right);
        yield (double) left - (double) right;
      }
      case PLUS -> {
        if (left instanceof Double && right instanceof Double) {
          yield (double) left + (double) right;
        }

        if (left instanceof String && right instanceof String) {
          yield (String) left + (String) right;
        }

        throw new RuntimeError(expr.operator, "Operands must be both numbers or strings.");
      }
      case SLASH -> {
        checkNumberOperands(expr.operator, left, right);
        if ((double) right == 0) {
          throw new RuntimeError(expr.operator, "Cannot divide by zero.");
        }

        yield (double) left / (double) right;
      }
      case STAR -> {
        checkNumberOperands(expr.operator, left, right);
        yield (double) left * (double) right;
      }

        // Unreachable
      default -> null;
    };
  }

  @Override
  public Object visitGroupingExpr(Expr.Grouping expr) {
    return evaluate(expr.expression);
  }

  @Override
  public Object visitLiteralExpr(Expr.Literal expr) {
    return expr.value;
  }

  @Override
  public Object visitUnaryExpr(Expr.Unary expr) {
    Object right = evaluate(expr.right);

    return switch (expr.operator.type) {
      case BANG -> !isTruthy(right);
      case MINUS -> {
        checkNumberOperand(expr.operator, right);
        yield -(double) right;
      }

        // Unreachable
      default -> null;
    };
  }

  @Override
  public Object visitVariableExpr(Expr.Variable expr) {
    return environment.get(expr.name);
  }

  // ===== Helpers =====

  private boolean isTruthy(Object object) {
    if (object == null) {
      return false;
    }

    if (object instanceof Boolean) {
      return (boolean) object;
    }

    return true;
  }

  private boolean isEqual(Object a, Object b) {
    if (a == null && b == null) {
      return true;
    }

    if (a == null) {
      return false;
    }

    return a.equals(b);
  }

  private void checkNumberOperand(Token operator, Object operand) {
    if (operand instanceof Double) {
      return;
    }

    throw new RuntimeError(operator, "Operand must be a number.");
  }

  private void checkNumberOperands(Token operator, Object left, Object right) {
    if (left instanceof Double && right instanceof Double) {
      return;
    }

    throw new RuntimeError(operator, "Operands must be numbers.");
  }

  private String stringify(Object object) {
    if (object == null) {
      return "nil";
    }

    String text = object.toString();
    if (object instanceof Double && text.endsWith(".0")) {
      text = text.substring(0, text.length() - 2);
    }

    return text;
  }
}
