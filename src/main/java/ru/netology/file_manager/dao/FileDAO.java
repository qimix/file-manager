package ru.netology.file_manager.dao;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.netology.file_manager.repository.FileInfoRepository;
import ru.netology.file_manager.model.FileInfo;

import java.util.List;

@Component
public class FileDAO {
    @Autowired
    @Qualifier("fileInfoRepository")
    FileInfoRepository fileInfoRepository;

    public FileInfo create(FileInfo createdFile) {
        return fileInfoRepository.save(createdFile);
    }

    public List<FileInfo> filelist() {
        return (List<FileInfo>) fileInfoRepository.findAll();
    }

    public void delete(Long id) {
        fileInfoRepository.deleteById(id);
    }

    public FileInfo findByName(String filename) {
        return fileInfoRepository.findByName(filename);
    }
}
