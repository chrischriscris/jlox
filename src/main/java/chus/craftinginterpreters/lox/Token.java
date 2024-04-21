package chus.craftinginterpreters.lox;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Token {
  final TokenType type;
  final String lexeme;
  final Object literal;
  final int line;
}
