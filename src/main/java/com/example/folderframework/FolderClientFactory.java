package com.example.folderframework;

import java.util.HashMap;
import java.util.Map;

class FolderClientFactory {
    private static final Map<String, IFolderClient> registered_types = new HashMap<>();

    static {
        registered_types.put("Plugin FolderSize", new FolderSizeClient());
    }

    private FolderClientFactory() {

    }

    private static IFolderClient get(String name) {
        return registered_types.get(name);
    }

    public static Map<String, IFolderClient> get_all() {
       return registered_types;
    }
}
