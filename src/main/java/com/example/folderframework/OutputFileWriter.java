package com.example.folderframework;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class OutputFileWriter {
    private final TreeMap<String, String> sorted_text_file_data = new TreeMap<>();
    private final String header;
    private final int max_length_of_values;
    private final String format_string;


    public OutputFileWriter(String header, Map<String, String> text_file_data) {
        this.header = header;
        this.sorted_text_file_data.putAll(text_file_data);
        this.max_length_of_values = get_max_length_of_values();
        format_string = create_line_format_string();
    }

    private int get_max_length_of_values() {
        try {
            return sorted_text_file_data.values().stream().max(Comparator.comparingInt(String::length)).get().length();
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    private String create_line_format_string() {
        return "%n%-70s: %" + max_length_of_values + "s";
    }


    public void write_folder_information_to_writer(BufferedWriter writer) throws IOException {
        writer.write(String.format("%s:", header));
        for (Map.Entry<String, String> entry : sorted_text_file_data.entrySet()) {
            write_one_line_to(writer, entry);
        }
    }

    private void write_one_line_to(BufferedWriter writer, Map.Entry<String, String> entry) throws IOException {
        writer.write(String.format(format_string, entry.getKey(), entry.getValue()));
    }
}
