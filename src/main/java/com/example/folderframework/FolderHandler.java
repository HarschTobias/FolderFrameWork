package com.example.folderframework;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

public class FolderHandler {
    private final Map<String, IFolderClient> folder_clients = FolderClientFactory.create_all();
    private final Path root_path;
    private final FormattedPathStringBuilder formatted_path_string_builder = new FormattedPathStringBuilder();
    private final RecursiveFolderWalker recursive_folder_walker = new RecursiveFolderWalker();

    public FolderHandler(Path root_path) {
        this.root_path = root_path;
    }

    public void write_all_folder_information_to_separate_files(Path out_path) throws IOException {
        Path real_out_path = out_path.toRealPath();
        if (!out_path.toFile().isDirectory()) {
            System.err.println("IOError: " + out_path.toString() + " is no directory.");
            return;
        }
        for (Map.Entry<String, IFolderClient> entry : folder_clients.entrySet()) {
            Path absolute_file_name = real_out_path.resolve(entry.getKey() + ".txt");
            try (BufferedWriter buffered_writer = Files.newBufferedWriter(absolute_file_name)) {
                write_folder_information_to(buffered_writer, entry);
                int a = 2;
            } catch (IOException e) {
                System.err.println("IOError: Cannot write to " + absolute_file_name.toString() + ".");
            }
        }
    }

    public void write_all_folder_information_to_writer(BufferedWriter writer) throws IOException {
        for (Map.Entry<String, IFolderClient> entry : folder_clients.entrySet()) {
            write_folder_information_to(writer, entry);
        }
    }

    private void write_folder_information_to(BufferedWriter writer, Map.Entry<String, IFolderClient> entry) throws IOException {
        recursive_folder_walker.setFolder_client(entry.getValue());
        Map<Path, String> result_data = recursive_folder_walker.get_formatted_path_folder_client_result_map(root_path);
        Map<String, String> result_text_data = result_data.entrySet().stream().collect(Collectors.toMap(e -> formatted_path_string_builder.build_formatted_path_from_target_path(root_path, e.getKey()), Map.Entry::getValue));
        OutputFileWriter output_file_writer = new OutputFileWriter(entry.getKey(), result_text_data);
        output_file_writer.write_folder_information_to_writer(writer);
    }

}
