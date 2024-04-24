package chus.craftinginterpreters.lox;

import lombok.AllArgsConstructor;

abstract class Stmt {
  interface Visitor<R> {
    R visitExpressionStmt(Expression stmt);

    R visitPrintStmt(Print stmt);

    R visitVarStmt(Var stmt);
  }

  @AllArgsConstructor
  static class Expression extends Stmt {
    final Expr expression;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitExpressionStmt(this);
    }
  }

  @AllArgsConstructor
  static class Print extends Stmt {
    final Expr expression;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitPrintStmt(this);
    }
  }

  @AllArgsConstructor
  static class Var extends Stmt {
    final Token name;
    final Expr initializer;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitVarStmt(this);
    }
  }

  abstract <R> R accept(Visitor<R> visitor);
}
