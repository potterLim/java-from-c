package com.example.todoanalyzer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class TodoAnalyzer {
    private static final String LINE_TODO_PATTERN = "// TODO: ";
    private static final String BLOCK_TODO_PATTERN = "/* TODO: ";
    private static final String BLOCK_TODO_END = "*/";

    private static final List<String> ALLOWED_EXTENSIONS = List.of(".c", ".cpp", ".h", ".java", ".cs", ".py", ".js", ".ts", ".html");

    private TodoAnalyzer() {
    }

    public static String generateTodoReportOrNull(final String directoryPathOrNull) {
        if (!isValidDirectoryPath(directoryPathOrNull)) {
            return null;
        }

        final Path directoryPath = Paths.get(directoryPathOrNull);

        final List<Path> filesInDirectory;
        try {
            filesInDirectory = listRegularFiles(directoryPath);
        } catch (final IOException ignored) {
            return null;
        }

        filesInDirectory.sort(Comparator.comparing(path -> path.getFileName().toString()));

        final Map<String, List<String>> todosByFileName = new HashMap<>();

        for (final Path filePath : filesInDirectory) {
            final String fileName = filePath.getFileName().toString();
            if (!hasAllowedExtension(fileName)) {
                continue;
            }

            final String fileContent;
            try {
                fileContent = Files.readString(filePath, StandardCharsets.UTF_8);
            } catch (final IOException ignored) {
                return null;
            }

            final List<String> extractedTodos = extractTodos(fileContent);
            if (!extractedTodos.isEmpty()) {
                todosByFileName.put(fileName, extractedTodos);
            }
        }

        final String reportContent = buildReportContent(filesInDirectory, todosByFileName);

        final Path reportPath;
        try {
            reportPath = createNextReportPath(directoryPath);
            Files.writeString(reportPath, reportContent, StandardCharsets.UTF_8);
        } catch (final IOException ignored) {
            return null;
        }

        return reportPath.toAbsolutePath().toString();
    }

    private static boolean isValidDirectoryPath(final String directoryPathOrNull) {
        if (directoryPathOrNull == null) {
            return false;
        }

        if (directoryPathOrNull.length() < 1) {
            return false;
        }

        if (isBlank(directoryPathOrNull)) {
            return false;
        }

        final Path directoryPath;
        try {
            directoryPath = Paths.get(directoryPathOrNull);
        } catch (final InvalidPathException ignored) {
            return false;
        }

        return Files.isDirectory(directoryPath);
    }

    private static boolean isBlank(final String text) {
        for (int i = 0; i < text.length(); ++i) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static List<Path> listRegularFiles(final Path directory) throws IOException {
        final List<Path> regularFiles = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (final Path entryPath : stream) {
                if (Files.isRegularFile(entryPath)) {
                    regularFiles.add(entryPath);
                }
            }
        }

        return regularFiles;
    }

    private static boolean hasAllowedExtension(final String fileName) {
        final String lowerFileName = fileName.toLowerCase(Locale.ROOT);

        for (final String extension : ALLOWED_EXTENSIONS) {
            if (lowerFileName.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    private static List<String> extractTodos(final String content) {
        final List<String> todos = new ArrayList<>();

        final int contentLength = content.length();
        final int linePatternLength = LINE_TODO_PATTERN.length();
        final int blockPatternLength = BLOCK_TODO_PATTERN.length();
        final int blockEndLength = BLOCK_TODO_END.length();

        int index = 0;
        while (index < contentLength) {
            if (startsWithAt(content, LINE_TODO_PATTERN, index)) {
                final int todoStartIndex = index + linePatternLength;
                final int todoEndIndex = findLineEnd(content, todoStartIndex);
                final String rawTodo = content.substring(todoStartIndex, todoEndIndex);

                addNormalizedTodoIfNotEmpty(todos, rawTodo);

                index = todoEndIndex;
                continue;
            }

            if (startsWithAt(content, BLOCK_TODO_PATTERN, index)) {
                final int todoStartIndex = index + blockPatternLength;
                final int todoEndIndex = content.indexOf(BLOCK_TODO_END, todoStartIndex);

                if (todoEndIndex < 0) {
                    break;
                }

                final String rawTodo = content.substring(todoStartIndex, todoEndIndex);
                addNormalizedTodoIfNotEmpty(todos, rawTodo);

                index = todoEndIndex + blockEndLength;
                continue;
            }

            ++index;
        }

        return todos;
    }

    private static boolean startsWithAt(final String content, final String pattern, final int index) {
        if (index + pattern.length() > content.length()) {
            return false;
        }

        for (int offset = 0; offset < pattern.length(); ++offset) {
            if (content.charAt(index + offset) != pattern.charAt(offset)) {
                return false;
            }
        }

        return true;
    }

    private static int findLineEnd(final String content, final int startIndex) {
        int index = startIndex;
        while (index < content.length()) {
            final char character = content.charAt(index);
            if (character == '\n' || character == '\r') {
                break;
            }

            ++index;
        }

        return index;
    }

    private static void addNormalizedTodoIfNotEmpty(final List<String> todos, final String rawTodo) {
        final String normalizedTodo = normalizeTodo(rawTodo);
        if (normalizedTodo.length() == 0) {
            return;
        }

        todos.add(normalizedTodo);
    }

    private static String normalizeTodo(final String rawTodo) {
        final String trimmed = trimAllWhitespace(rawTodo);
        if (trimmed.length() == 0) {
            return "";
        }

        final StringBuilder normalizedBuilder = new StringBuilder(trimmed.length());

        boolean previousWasWhitespace = false;
        for (int i = 0; i < trimmed.length(); ++i) {
            final char character = trimmed.charAt(i);

            if (Character.isWhitespace(character)) {
                if (!previousWasWhitespace) {
                    normalizedBuilder.append(' ');
                    previousWasWhitespace = true;
                }

                continue;
            }

            normalizedBuilder.append(character);
            previousWasWhitespace = false;
        }

        if (normalizedBuilder.length() > 0 && normalizedBuilder.charAt(normalizedBuilder.length() - 1) == ' ') {
            normalizedBuilder.setLength(normalizedBuilder.length() - 1);
        }

        return normalizedBuilder.toString();
    }

    private static String trimAllWhitespace(final String text) {
        int leftIndex = 0;
        while (leftIndex < text.length() && Character.isWhitespace(text.charAt(leftIndex))) {
            ++leftIndex;
        }

        int rightIndex = text.length() - 1;
        while (rightIndex >= leftIndex && Character.isWhitespace(text.charAt(rightIndex))) {
            --rightIndex;
        }

        return text.substring(leftIndex, rightIndex + 1);
    }

    private static String buildReportContent(final List<Path> sortedFiles, final Map<String, List<String>> todosByFileName) {
        final StringBuilder reportBuilder = new StringBuilder();

        boolean isFirstFileBlock = true;

        for (final Path filePath : sortedFiles) {
            final String fileName = filePath.getFileName().toString();
            final List<String> todos = todosByFileName.get(fileName);

            if (todos == null || todos.isEmpty()) {
                continue;
            }

            if (!isFirstFileBlock) {
                reportBuilder.append('\n');
                reportBuilder.append('\n');
            }

            reportBuilder.append(fileName);
            reportBuilder.append('\n');

            for (int i = 0; i < todos.size(); ++i) {
                reportBuilder.append("- ");
                reportBuilder.append(todos.get(i));

                if (i != todos.size() - 1) {
                    reportBuilder.append('\n');
                }
            }

            isFirstFileBlock = false;
        }

        return reportBuilder.toString();
    }

    private static Path createNextReportPath(final Path directory) {
        Path reportPath = directory.resolve("report.txt");
        if (!Files.exists(reportPath)) {
            return reportPath;
        }

        int suffixNumber = 2;
        while (true) {
            reportPath = directory.resolve("report-" + suffixNumber + ".txt");
            if (!Files.exists(reportPath)) {
                return reportPath;
            }

            ++suffixNumber;
        }
    }
}
