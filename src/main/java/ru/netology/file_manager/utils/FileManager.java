package ru.netology.file_manager.utils;

import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManager {
    private final String DIRECTORY_PATH = ".";

    public void upload(byte[] resource, String keyName) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH, keyName);
        Path file = Files.createFile(path);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file.toString());
            stream.write(resource);
        } finally {
            stream.close();
        }
    }
}
