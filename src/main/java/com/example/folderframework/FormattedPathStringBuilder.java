package com.example.folderframework;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.Locale;

public class FormattedPathStringBuilder {
    public FormattedPathStringBuilder() {
    }

    private final StringBuilder formatted_path_string_builder = new StringBuilder();
    private final Formatter path_formatter = new Formatter(formatted_path_string_builder, Locale.GERMANY);

    private Path root_path;
    private Path current_path;

    public String build_formatted_path_from_target_path(Path source_path, Path target_path) {
        try {
            check_if_paths_are_valid(source_path, target_path);
        } catch (IOException e) {
            System.err.println("Path not found.");
            return "";
        }
        if (!current_path.startsWith(root_path))
            return "";
        build_formatted_root();
        try {
            build_path_string();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return formatted_path_string_builder.toString();
    }

    private void check_if_paths_are_valid(Path source_path, Path target_path) throws IOException {
        root_path = source_path.toRealPath();
        this.current_path = target_path.toRealPath();
    }

    private void build_formatted_root() {
        formatted_path_string_builder.setLength(0);
        formatted_path_string_builder.append(root_path.getRoot().toString());
        root_path.iterator().forEachRemaining(p -> path_formatter.format("%s %c", p.toString(), File.separatorChar));
        formatted_path_string_builder.setLength(formatted_path_string_builder.length() - 2);
    }

    private void build_path_string() throws NullPointerException {
        Path relative_path = this.root_path.relativize(this.current_path);
        Path working_path = Paths.get(root_path.toString());

        for (Path value : relative_path) {
            if (!value.toString().isEmpty()) {
                working_path = working_path.resolve(value);
                build_formatted_path_part(working_path);
            }
        }
    }

    private void build_formatted_path_part(Path working_path) throws NullPointerException {
        Path parent_path = working_path.getParent();
        int max_length_of_siblings = get_max_length(parent_path);
        String last_name = working_path.getName(working_path.getNameCount() - 1).toString();
        String format_string = String.format(" %c%%-%ds", File.separatorChar, max_length_of_siblings);
        path_formatter.format(format_string, last_name);
    }

    private static int get_max_length(Path target_path) throws NullPointerException {
        int max_length = 0;
        for (File sibling : target_path.toFile().listFiles()) {
            if (sibling.isDirectory()) {
                Path current_Path = sibling.toPath();
                String last_name = current_Path.getName(current_Path.getNameCount() - 1).toString();
                max_length = Math.max(max_length, last_name.length());
            }
        }
        return max_length;
    }


}
