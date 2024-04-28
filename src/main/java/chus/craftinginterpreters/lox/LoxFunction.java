package chus.craftinginterpreters.lox;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoxFunction implements LoxCallable {
  private final Stmt.Function declaration;
  private final Environment closure;
  private final boolean isInitializer;

  @Override
  public int arity() {
    return declaration.params.size();
  }

  @Override
  public Object call(Interpreter interpreter, List<Object> arguments) {
    Environment environment = new Environment(closure);

    for (int i = 0; i < declaration.params.size(); i++) {
      environment.define(declaration.params.get(i).lexeme, arguments.get(i));
    }

    try {
      interpreter.executeBlock(declaration.body, environment);
    } catch (Return returnValue) {
      if (isInitializer) {
        return closure.getAt(0, "this");
      }

      return returnValue.value;
    }

    if (isInitializer) {
      return closure.getAt(0, "this");
    }

    return null;
  }

  LoxFunction bind(LoxInstance instance) {
    Environment environment = new Environment(closure);
    environment.define("this", instance);
    return new LoxFunction(declaration, environment, isInitializer);
  }

  @Override
  public String toString() {
    return String.format("<fn %s>", declaration.name.lexeme);
  }
}
