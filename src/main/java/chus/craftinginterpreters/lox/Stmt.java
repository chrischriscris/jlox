package chus.craftinginterpreters.lox;

import java.util.List;
import lombok.AllArgsConstructor;

abstract class Stmt {
  interface Visitor<R> {
    R visitBlockStmt(Block stmt);

    R visitExpressionStmt(Expression stmt);

    R visitIfStmt(If stmt);

    R visitWhileStmt(While stmt);

    R visitPrintStmt(Print stmt);

    R visitVarStmt(Var stmt);
  }

  @AllArgsConstructor
  static class Block extends Stmt {
    final List<Stmt> statements;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitBlockStmt(this);
    }
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
  static class If extends Stmt {
    final Expr condition;
    final Stmt thenBranch;
    final Stmt elseBranch;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitIfStmt(this);
    }
  }

  @AllArgsConstructor
  static class While extends Stmt {
    final Expr condition;
    final Stmt body;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitWhileStmt(this);
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
