package com.example.folderframework;

import java.nio.file.Path;

interface IFolderClient {
    String call(Path target_path);
}
