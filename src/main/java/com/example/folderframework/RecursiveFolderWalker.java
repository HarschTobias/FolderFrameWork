package com.example.folderframework;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class RecursiveFolderWalker {
    private final IFolderClient folder_client;
    private final Map<Path, String> folder_client_result_map = new TreeMap<>();
    private Path current_path;

    public RecursiveFolderWalker(IFolderClient folder_client) {
        this.folder_client = folder_client;
    }

    public Map<Path, String> get_formatted_path_folder_client_result_map(Path target_path) {

        folder_client_result_map.clear();
        try {
            current_path = target_path.toRealPath();
        } catch (IOException e) {
            System.err.println("IOException: " + target_path.toString() + " not found.");
        }
        folder_client_result_map.clear();
        try {
            recursive_folder_handler(current_path);
        } catch (NullPointerException e) {
            System.err.println("No folder client chosen.");
            return folder_client_result_map;
        }

        return folder_client_result_map;


    }

    private void recursive_folder_handler(Path path) throws NullPointerException {
        folder_client_result_map.put(path, folder_client.call(path));

        for (File file : path.toFile().listFiles()) {
            if (file.isDirectory()) {
                recursive_folder_handler(file.toPath());
            }
        }
    }
}
