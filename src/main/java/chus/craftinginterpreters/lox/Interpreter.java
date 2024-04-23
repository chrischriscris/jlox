package chus.craftinginterpreters.lox;

class Interpreter implements Expr.Visitor<Object> {
  private Object evaluate(Expr expr) {
    return expr.accept(this);
  }

  @Override
  public Object visitBinaryExpr(Expr.Binary expr) {
    Object left = evaluate(expr.left);
    Object right = evaluate(expr.right);

    return switch (expr.operator.type) {
      case GREATER -> (double) left > (double) right;
      case GREATER_EQUAL -> (double) left >= (double) right;
      case LESS -> (double) left < (double) right;
      case LESS_EQUAL -> (double) left <= (double) right;
      case BANG_EQUAL -> !isEqual(left, right);
      case EQUAL_EQUAL -> isEqual(left, right);
      case MINUS -> (double) left - (double) right;
      case PLUS -> {
        if (left instanceof Double && right instanceof Double) {
          yield (double) left + (double) right;
        }

        if (left instanceof String && right instanceof String) {
          yield (String) left + (String) right;
        }

        yield null;
      }
      case SLASH -> (double) left / (double) right;
      case STAR -> (double) left * (double) right;

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
      case MINUS -> -(double) right;

      // Unreachable
      default -> null;
    };
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
}
