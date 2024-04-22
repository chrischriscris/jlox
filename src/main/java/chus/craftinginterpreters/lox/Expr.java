package chus.craftinginterpreters.lox;

import lombok.AllArgsConstructor;

abstract class Expr {
  @AllArgsConstructor
  static class Binary extends Expr {
    final Expr left;
    final Token operator;
    final Expr right;
  }

  @AllArgsConstructor
  static class Grouping extends Expr {
    final Expr expression;
  }

  @AllArgsConstructor
  static class Literal extends Expr {
    final Object value;
  }

  @AllArgsConstructor
  static class Unary extends Expr {
    final Token operator;
    final Expr right;
  }
}
