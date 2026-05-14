package com.example.todoanalyzer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public final class Main {
    private static int sPassedCount;
    private static int sFailedCount;
    private static String sFailureMessage;

    private Main() {
    }

    public static void main(String[] args) {
        verifyInvalidDirectory();
        verifyTodoReportGeneration();
        verifyReportNameConflictAndEmptyReport();

        printSummary();
    }

    private static void verifyInvalidDirectory() {
        Path filePath = null;

        try {
            filePath = Files.createTempFile("todo-analyzer-main-", ".txt");

            verifyScenario(
                    "invalid directory inputs return null",
                    checkNull("null directory", TodoAnalyzer.generateTodoReportOrNull(null))
                            && checkNull("blank directory", TodoAnalyzer.generateTodoReportOrNull("   "))
                            && checkNull("file path", TodoAnalyzer.generateTodoReportOrNull(filePath.toString()))
            );
        } catch (IOException exception) {
            fail("invalid directory setup", exception.getMessage());
        } finally {
            deletePathIfExists(filePath);
        }
    }

    private static void verifyTodoReportGeneration() {
        Path directoryPath = null;

        try {
            directoryPath = Files.createTempDirectory("todo-analyzer-main-");

            Files.writeString(
                    directoryPath.resolve("alpha.c"),
                    String.join(
                            "\n",
                            "int add(int a, int b)",
                            "{",
                            "    return a + b; // TODO: handle overflow later",
                            "}"
                    ),
                    StandardCharsets.UTF_8
            );

            Files.writeString(
                    directoryPath.resolve("beta.java"),
                    String.join(
                            "\n",
                            "public final class Beta {",
                            "    public static void main(String[] args) {",
                            "    /* TODO: refactor main logic",
                            "             - extract method",
                            "             - remove duplication ",
                            "    */",
                            "    // TODO: add input validation",
                            "    }",
                            "}"
                    ),
                    StandardCharsets.UTF_8
            );

            Files.writeString(directoryPath.resolve("ignored.md"), "// TODO: this file extension is ignored", StandardCharsets.UTF_8);

            Path nestedDirectoryPath = Files.createDirectory(directoryPath.resolve("nested"));
            Files.writeString(nestedDirectoryPath.resolve("nested.java"), "// TODO: nested directories are ignored", StandardCharsets.UTF_8);

            String reportPathOrNull = TodoAnalyzer.generateTodoReportOrNull(directoryPath.toString());
            boolean isPassed = checkNotNull("report path", reportPathOrNull);

            if (isPassed) {
                Path reportPath = Path.of(reportPathOrNull);
                String expectedReport = String.join(
                        "\n",
                        "alpha.c",
                        "- handle overflow later",
                        "",
                        "beta.java",
                        "- refactor main logic - extract method - remove duplication",
                        "- add input validation"
                );

                isPassed = checkEquals("report file name", "report.txt", reportPath.getFileName().toString())
                        && checkEquals("report content", expectedReport, Files.readString(reportPath, StandardCharsets.UTF_8));
            }

            verifyScenario("generates sorted TODO report", isPassed);
        } catch (IOException exception) {
            fail("report generation setup", exception.getMessage());
        } finally {
            deleteDirectoryTree(directoryPath);
        }
    }

    private static void verifyReportNameConflictAndEmptyReport() {
        Path directoryPath = null;

        try {
            directoryPath = Files.createTempDirectory("todo-analyzer-main-empty-");
            Files.writeString(directoryPath.resolve("report.txt"), "existing report", StandardCharsets.UTF_8);
            Files.writeString(directoryPath.resolve("notes.java"), "public final class Notes {}", StandardCharsets.UTF_8);

            String reportPathOrNull = TodoAnalyzer.generateTodoReportOrNull(directoryPath.toString());
            boolean isPassed = checkNotNull("report path", reportPathOrNull);

            if (isPassed) {
                Path reportPath = Path.of(reportPathOrNull);
                isPassed = checkEquals("next report file name", "report-2.txt", reportPath.getFileName().toString())
                        && checkEquals("empty report content", "", Files.readString(reportPath, StandardCharsets.UTF_8));
            }

            verifyScenario("uses next report name and supports empty reports", isPassed);
        } catch (IOException exception) {
            fail("empty report setup", exception.getMessage());
        } finally {
            deleteDirectoryTree(directoryPath);
        }
    }

    private static boolean checkEquals(String name, String expected, String actual) {
        if (expected.equals(actual)) {
            return true;
        }

        sFailureMessage = name + ": expected=" + expected + ", actual=" + actual;
        return false;
    }

    private static boolean checkNull(String name, String actual) {
        if (actual == null) {
            return true;
        }

        sFailureMessage = name + ": expected=null, actual=" + actual;
        return false;
    }

    private static boolean checkNotNull(String name, String actual) {
        if (actual != null) {
            return true;
        }

        sFailureMessage = name + ": expected non-null value";
        return false;
    }

    private static void verifyScenario(String name, boolean isPassed) {
        if (isPassed) {
            pass(name);
            return;
        }

        fail(name, sFailureMessage);
        sFailureMessage = null;
    }

    private static void deleteDirectoryTree(Path directoryPathOrNull) {
        if (directoryPathOrNull == null) {
            return;
        }

        try (Stream<Path> paths = Files.walk(directoryPathOrNull)) {
            paths.sorted(Comparator.reverseOrder()).forEach(Main::deletePathIfExists);
        } catch (IOException ignored) {
        }
    }

    private static void deletePathIfExists(Path pathOrNull) {
        if (pathOrNull == null) {
            return;
        }

        try {
            Files.deleteIfExists(pathOrNull);
        } catch (IOException ignored) {
        }
    }

    private static void pass(String name) {
        ++sPassedCount;
        System.out.println("[PASS] " + name);
    }

    private static void fail(String name, String message) {
        ++sFailedCount;
        System.out.println("[FAIL] " + name + " (" + message + ")");
    }

    private static void printSummary() {
        System.out.println();
        System.out.println("Passed: " + sPassedCount);
        System.out.println("Failed: " + sFailedCount);
    }
}
