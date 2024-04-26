package chus.craftinginterpreters.lox;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoxFunction implements LoxCallable {
  private final Stmt.Function declaration;

  @Override
  public int arity() {
    return declaration.params.size();
  }

  @Override
  public Object call(Interpreter interpreter, List<Object> arguments) {
    Environment environment = new Environment(interpreter.globals);

    for (int i = 0; i < declaration.params.size(); i++) {
      environment.define(declaration.params.get(i).lexeme, arguments.get(i));
    }

    interpreter.executeBlock(declaration.body, environment);
    return null;
  }

  @Override
  public String toString() {
    return String.format("<fn %s>", declaration.name.lexeme);
  }
}
