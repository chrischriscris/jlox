package chus.craftinginterpreters.lox;

import java.util.HashMap;
import java.util.Map;

public class Environment {
  private final Map<String, Object> values = new HashMap<>();

  void define(String name, Object value) {
    values.put(name, value);
  }

  void assign(Token name, Object value) {
    if (values.containsKey(name.lexeme)) {
      values.put(name.lexeme, value);

      return;
    }

    throw new RuntimeError(name, String.format("Undefined variable '%s'.", name.lexeme));
  }

  Object get(Token name) {
    Object value = values.getOrDefault(name.lexeme, null);
    if (value == null) {
      throw new RuntimeError(name, String.format("Undefined variable '%s'.", name.lexeme));
    }

    return value;
  }
}
