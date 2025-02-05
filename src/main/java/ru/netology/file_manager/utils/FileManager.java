package ru.netology.file_manager.utils;


import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManager {
    private String DIRECTORY_PATH = ".";
    public static final Logger logger = (Logger) LoggerFactory.getLogger(FileManager.class);

    public void upload(byte[] resource, String keyName) throws IOException {
        logger.info("Start upload file");
        Path path = Paths.get(DIRECTORY_PATH, keyName);
        Path file = Files.createFile(path);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file.toString());
            stream.write(resource);
            logger.info("Finishing upload file");
        } catch (IOException e) {
            logger.error("Error upload file");
        } finally {
            stream.close();
        }
    }

    public void delete(String key) throws IOException {
        logger.info("start delete file");
        try {
            Path path = Paths.get(DIRECTORY_PATH, key);
            Files.delete(path);
            logger.info("Finishing delete file");
        } catch (IOException e) {
            logger.error("Error delete file");
        }
    }

    public Resource download(String key) throws IOException {
        logger.info("Start download file");
        Path path = Paths.get(DIRECTORY_PATH, key);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {
            logger.info("Downloading file finish");
            return resource;
        } else {
            logger.error("Error download file");
            throw new IOException();
        }
    }

}
