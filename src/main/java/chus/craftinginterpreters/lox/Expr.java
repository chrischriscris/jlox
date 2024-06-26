package chus.craftinginterpreters.lox;

import java.util.List;
import lombok.AllArgsConstructor;

abstract class Expr {
  interface Visitor<R> {
    R visitAssignExpr(Assign expr);

    R visitCallExpr(Call expr);

    R visitGetExpr(Get expr);

    R visitSetExpr(Set expr);

    R visitSuperExpr(Super expr);

    R visitThisExpr(This expr);

    R visitBinaryExpr(Binary expr);

    R visitLogicalExpr(Logical expr);

    R visitGroupingExpr(Grouping expr);

    R visitLiteralExpr(Literal expr);

    R visitUnaryExpr(Unary expr);

    R visitVariableExpr(Variable expr);
  }

  @AllArgsConstructor
  static class Assign extends Expr {
    final Token name;
    final Expr value;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitAssignExpr(this);
    }
  }

  @AllArgsConstructor
  static class Call extends Expr {
    final Expr callee;
    final Token paren;
    final List<Expr> arguments;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitCallExpr(this);
    }
  }

  @AllArgsConstructor
  static class Get extends Expr {
    final Expr object;
    final Token name;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitGetExpr(this);
    }
  }

  @AllArgsConstructor
  static class Set extends Expr {
    final Expr object;
    final Token name;
    final Expr value;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitSetExpr(this);
    }
  }

  @AllArgsConstructor
  static class Super extends Expr {
    final Token keyword;
    final Token method;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitSuperExpr(this);
    }
  }

  @AllArgsConstructor
  static class This extends Expr {
    final Token keyword;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitThisExpr(this);
    }
  }

  @AllArgsConstructor
  static class Binary extends Expr {
    final Expr left;
    final Token operator;
    final Expr right;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitBinaryExpr(this);
    }
  }

  @AllArgsConstructor
  static class Logical extends Expr {
    final Expr left;
    final Token operator;
    final Expr right;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitLogicalExpr(this);
    }
  }

  @AllArgsConstructor
  static class Grouping extends Expr {
    final Expr expression;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitGroupingExpr(this);
    }
  }

  @AllArgsConstructor
  static class Literal extends Expr {
    final Object value;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitLiteralExpr(this);
    }
  }

  @AllArgsConstructor
  static class Unary extends Expr {
    final Token operator;
    final Expr right;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitUnaryExpr(this);
    }
  }

  @AllArgsConstructor
  static class Variable extends Expr {
    final Token name;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitVariableExpr(this);
    }
  }

  abstract <R> R accept(Visitor<R> visitor);
}
