package chus.craftinginterpreters.lox;

import java.util.HashMap;
import java.util.Map;

public class Environment {
  private final Map<String, Object> values = new HashMap<>();

  void define(String name, Object value) {
    values.put(name, value);
  }

  Object get(Token name) {
    Object value = values.getOrDefault(name.lexeme, null);
    if (value == null) {
      throw new RuntimeError(name, String.format("Undefined variable '%s'.", name.lexeme));
    }

    return value;
  }
}
