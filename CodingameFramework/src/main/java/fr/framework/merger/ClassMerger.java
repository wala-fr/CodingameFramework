package fr.framework.merger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import fr.code.variable.Parameter;

public class ClassMerger {

  private static Path root = Paths.get("src\\main\\java");

  // to exclude whole package
  private static List<String> excludedPackages = Arrays.asList("fr.framework.merger");

  // to exclude single package (here it could be empty the ClassMerger package is already excluded)
  private static List<String> excludedFiles =
      Arrays.asList(ClassMerger.class).stream().map(c -> c.getName()).collect(Collectors.toList());

  public static void main(String[] args) throws IOException {
    Stream<Path> files =
        Files.walk(root)
            .filter(p -> p.getFileName().toString().endsWith(".java"))
            .filter(
                p ->
                    excludedPackages
                        .stream()
                        .noneMatch(s -> p.toString().replace(File.separator, ".").contains(s)))
            .filter(
                p ->
                    excludedFiles
                        .stream()
                        .noneMatch(s -> p.toString().replace(File.separator, ".").contains(s)));
    System.out.println("import java.util.*;");
    System.out.println("import java.text.*;");
    System.out.println("import java.util.function.*;");
    System.out.println("import java.util.stream.*;");
    System.out.println("import java.util.concurrent.*;");
    System.out.println("import java.util.Map.*;");

    files.forEach(ClassMerger::printFileWithoutImports);
  }

  private static void printFileWithoutImports(Path p) {
    StringBuilder sb = new StringBuilder();
    try {
      Files.lines(p)
          .map(s -> s.trim())
          .filter(s -> !s.startsWith("import "))
          .filter(s -> !s.startsWith("package "))
          .map(s -> s.replaceAll("public class ", "class "))
          .map(s -> s.replaceAll("private", ""))
          .map(s -> s.replaceAll("public enum ", "enum "))
          .map(s -> s.replaceAll("public interface ", "interface "))
          .map(s -> s.replaceAll("public abstract ", "abstract "))
          .map( // to remove useless spaces
              s ->
                  s.replaceAll(" == ", "==")
                      .replaceAll(" \\{", "{")
                      .replaceAll(", ", ",")
                      .replaceAll(" = ", "=")
                      .replaceAll("} ", "}")
                      .replaceAll(" = ", "=")
                      .replaceAll(" > ", ">")
                      .replaceAll(" >= ", ">=")
                      .replaceAll(" < ", "<")
                      .replaceAll(" <= ", "<=")
                      .replaceAll(" \\+= ", "+=")
                      .replaceAll(" \\*= ", "*=")
                      .replaceAll(" -= ", "-=")
                      .replaceAll(" \\)", ")")
                      .replaceAll(" ^ ", "^")
                      .replaceAll(" && ", "&&")
                      .replaceAll(" \\|\\| ", "||")
                      .replaceAll(" & ", "&")
                      .replaceAll(" \\| ", "|")
                      .replaceAll(" == ", "==")
                      .replaceAll(" != ", "!=")
                      .replaceAll(" \\? ", "?")
                      .replaceAll(", ", ",")
                      .replaceAll(" : ", ":")
                      .replaceAll(": ", ":")
                      .replaceAll(" \\+ ", "+")
                      .replaceAll(" - ", "-")
                      .replaceAll(" \\* ", "*")
                      .replaceAll(" %= ", "%=")
                      .replaceAll(" % ", "%")
                      .replaceAll(" / ", "/")
                      .replaceAll(" \\(", "(")
                      .replaceAll("; ", ";")
                      .replaceAll(" -> ", "->")
                      .replaceAll(" => ", "=>")
                      .replaceAll(" =>", "=>")
                      .replaceAll(" ` `", "` `")
                      .replaceAll(" ``", "``")
                      .replaceAll("\\) ", ")")
                      .replaceAll("//.*", ""))
          .map(s -> s.trim())
          .filter(s -> !s.startsWith("//")) // remove comments
          .filter(s -> !s.isEmpty())
          .forEach(s -> sb.append(s.equals("@Override") ? s + " " : (s + "\n")));
      String str = sb.toString();
      if (Parameter.REMOVE_ASSERT) {
        str = str.replaceAll("AssertUtils\\.test\\([^\\;]+\\;", "");
      }
      if (Parameter.REMOVE_LOG) {
        str = str.replaceAll("logger\\.error\\([^\\;]+\\;", "");
      }
      System.out.println(str);
    } catch (IOException e) {
    }
  }
}
