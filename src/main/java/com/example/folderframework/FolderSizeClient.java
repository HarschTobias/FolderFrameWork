package com.example.folderframework;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;


public class FolderSizeClient implements IFolderClient {

    public FolderSizeClient() {
    }

    @Override
    public String call(Path path) {
        return get_kb_string(path);
    }

    private String get_kb_string(Path path) {
        return String.format("%,d kB", Math.round(determine_size_of_files_in_bytes(path) / 1024.0));
    }

    private long determine_size_of_files_in_bytes(Path path) {
        File[] file_info_list = path.toFile().listFiles();
        if (file_info_list != null) {
            try {
                return Arrays.stream(file_info_list)
                        .filter(File::isFile)
                        .map(File::length)
                        .reduce(Long::sum)
                        .get();
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }
}
