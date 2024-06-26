package chus.craftinginterpreters.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.err.println("Usage: generate_ast <output_directory>");
      System.exit(64);
    }

    String outputDir = args[0];
    defineAst(
        outputDir,
        "Expr",
        Arrays.asList(
            "Assign: Token name, Expr value",
            "Call: Expr callee, Token paren, List<Expr> arguments",
            "Get: Expr object, Token name",
            "Set: Expr object, Token name, Expr value",
            "Super: Token keyword, Token method",
            "This: Token keyword",
            "Binary: Expr left, Token operator, Expr right",
            "Logical: Expr left, Token operator, Expr right",
            "Grouping: Expr expression",
            "Literal: Object value",
            "Unary: Token operator, Expr right",
            "Variable: Token name"));

    defineAst(
        outputDir,
        "Stmt",
        Arrays.asList(
            "Block: List<Stmt> statements",
            "Class: Token name, Expr.Variable superclass, List<Stmt.Function> methods",
            "Expression: Expr expression",
            "Function: Token name, List<Token> params, List<Stmt> body",
            "If: Expr condition, Stmt thenBranch, Stmt elseBranch",
            "While: Expr condition, Stmt body",
            "Print: Expr expression",
            "Return: Token keyword, Expr value",
            "Var: Token name, Expr initializer"));
  }

  private static void defineAst(String outputDir, String baseName, List<String> types)
      throws IOException {
    String path = String.format("%s/%s.java", outputDir, baseName);

    try (PrintWriter writer = new PrintWriter(path, "UTF-8")) {
      // Automatic formatting will do its thing
      writer.println("package chus.craftinginterpreters.lox;");

      writer.println("import java.util.List;");
      writer.println("import lombok.AllArgsConstructor;");

      writer.printf("abstract class %s {\n", baseName);

      defineVisitor(writer, baseName, types);

      for (String type : types) {
        String[] splitType = type.split(":");

        String className = splitType[0].trim();
        String fields = splitType[1].trim();

        defineType(writer, baseName, className, fields);
      }

      writer.println("abstract <R> R accept(Visitor<R> visitor);");

      writer.println("}");
    }
  }

  private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
    writer.println("interface Visitor<R> {");

    for (String type : types) {
      String typeName = type.split(":")[0].trim();
      writer.printf("R visit%s%s(%s %s);%n", typeName, baseName, typeName, baseName.toLowerCase());
    }

    writer.println("}");
  }

  private static void defineType(
      PrintWriter writer, String baseName, String className, String fieldList) {
    writer.println("@AllArgsConstructor");
    writer.printf("static class %s extends %s {\n", className, baseName);

    for (String field : fieldList.split(", ")) {
      writer.printf("final %s;%n", field);
    }

    writer.println("@Override");
    writer.println("<R> R accept(Visitor<R> visitor) {");
    writer.printf("return visitor.visit%s%s(this);%n}", className, baseName);

    writer.println("}");
  }
}
