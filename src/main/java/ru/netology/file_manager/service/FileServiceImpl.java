package ru.netology.file_manager.service;


import ch.qos.logback.classic.Logger;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.file_manager.dao.FileDAO;
import ru.netology.file_manager.model.FileInfo;
import ru.netology.file_manager.utils.FileManager;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.netology.file_manager.utils.LogConstants.LOG_SEPARATOR;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    public static final Logger logger = (Logger) LoggerFactory.getLogger(FileServiceImpl.class);
    @Autowired
    @Qualifier(value = "fileDAO")
    private final FileDAO fileDAO;

    @Autowired
    @Qualifier(value = "fileManager")
    private final FileManager fileManager;

    @Transactional(rollbackOn = {IOException.class})
    @Override
    public FileInfo upload(MultipartFile resource) throws IOException {
        logger.info("Start upload file: {}", resource.getOriginalFilename());
        LocalDate uploadDate = LocalDate.now();
        String keyFile = generateKey(resource.getName());
        logger.info("Set internal file name: {}", keyFile);
        FileInfo createdFile = FileInfo.builder()
                .setName(resource.getOriginalFilename())
                .setKeyFile(keyFile)
                .setSize(resource.getSize())
                .setLocalDate(uploadDate)
                .build();
        createdFile = fileDAO.create(createdFile);
        fileManager.upload(resource.getBytes(), keyFile);
        logger.info("Success upload file: {}", resource.getOriginalFilename());
        return createdFile;
    }

    @Override
    public List<FileInfo> filelist() {
        logger.info("Start get filelist");
        return fileDAO.filelist();
    }

    @Override
    public void delete(String filename) throws IOException {
        logger.info("Start delete file");
        FileInfo file = findByName(filename);
        fileDAO.delete(file.getId());
        fileManager.delete(file.getKeyFile());
        logger.info("Deleted file success");
    }

    @Override
    public FileInfo findByName(String filename) {
        logger.info("Starting find file by name");
        return fileDAO.findByName(filename);
    }

    @Override
    public Resource download(String keyFile) throws IOException {
        logger.info("Starting download file");
        return fileManager.download(keyFile);
    }

    private String generateKey(String name) {
        logger.info("Starting generate key");
        String key = DigestUtils.md5Hex(name + LocalDateTime.now().toString());
        logger.info("Generated key: {}", key);
        return key;
    }

    public boolean checkFileExits(String filename) {
        logger.info("Starting check file exit");
        return fileDAO.checkFileExist(filename);
    }

}
