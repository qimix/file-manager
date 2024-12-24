package ru.netology.file_manager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.file_manager.dao.FileDAO;
import ru.netology.file_manager.model.FileInfo;
import ru.netology.file_manager.utils.FileManager;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    @Autowired
    @Qualifier(value = "fileDAO")
    private final FileDAO fileDAO;

    @Autowired
    @Qualifier(value = "fileManager")
    private final FileManager fileManager;

    @Transactional(rollbackOn = {IOException.class})
    @Override
    public FileInfo upload(MultipartFile resource) throws IOException {
        LocalDate uploadDate = LocalDate.now();
        String keyFile = generateKey(resource.getName());
        FileInfo createdFile = FileInfo.builder()
                .setName(resource.getOriginalFilename())
                .setKeyFile(keyFile)
                .setSize(resource.getSize())
                .setLocalDate(uploadDate)
                .build();
        createdFile = fileDAO.create(createdFile);
        fileManager.upload(resource.getBytes(), keyFile);
        return createdFile;
    }

    @Override
    public List<FileInfo> filelist() {
        return fileDAO.filelist();
    }

    @Override
    public void delete(String filename) throws IOException {
        FileInfo file = fileDAO.findByName(filename);
        fileDAO.delete(file.getId());
        System.out.println(file.getKeyFile());
        fileManager.delete(file.getKeyFile());
    }

    private String generateKey(String name) {
        return DigestUtils.md5Hex(name + LocalDateTime.now().toString());
    }

}
