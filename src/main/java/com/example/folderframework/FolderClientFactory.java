package com.example.folderframework;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class FolderClientFactory {
    private static final Map<String, Class<? extends IFolderClient>> registered_types = new HashMap<>();

    static {
        registered_types.put("Plugin FolderSize", FolderSizeClient.class);
    }

    private FolderClientFactory() {

    }

    private static IFolderClient create(String name) {
        try {
            Class<? extends IFolderClient> folder_client_class = registered_types.get(name);
            Constructor folder_client_constructor = folder_client_class.getDeclaredConstructor();
            return (IFolderClient) folder_client_constructor.newInstance(new Object[]{});
        } catch (Exception e) {
            System.err.println(String.format("Unable to create folder client from %s", name));
            return null;
        }
    }

    public static Map<String, IFolderClient> create_all() {
        return registered_types.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> Optional.ofNullable(create(entry.getKey()))))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPresent())
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get()));
    }

    public static void register_folder_client(String name, Class<? extends IFolderClient> folder_client_class) {
        registered_types.putIfAbsent(name, folder_client_class);
    }

    public static void unregister_folder_client(String name) {
        registered_types.remove(name);
    }
}
