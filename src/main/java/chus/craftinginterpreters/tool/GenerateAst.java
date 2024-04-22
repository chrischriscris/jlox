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
    defineAst(outputDir, "Expr", Arrays.asList("Binary: Expr left, Token operator, Expr right", "Grouping: Expr expression", "Literal: Object value",
        "Unary: Token operator, Expr right"));
  }

  private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
    String path = String.format("%s/%s.java", outputDir, baseName);

    try (PrintWriter writer = new PrintWriter(path, "UTF-8")) {
      // Automatic formatting will do its thing
      writer.println("package chus.craftinginterpreters.lox;");

      writer.println("import java.util.List;");
      writer.println("import lombok.AllArgsConstructor;");

      writer.printf("abstract class %s {\n", baseName);

      for (String type : types) {
        String[] splitType = type.split(":");

        String className = splitType[0].trim();
        String fields = splitType[1].trim();

        defineType(writer, baseName, className, fields);
      }

      writer.println("}");
    }

  }

  private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
    writer.println("@AllArgsConstructor");
    writer.printf("static class %s extends %s {\n", className, baseName);
    
    for (String field : fieldList.split(", ")) {
      writer.printf("final %s;", field);
    }
    
    writer.println("}");
  }
}
