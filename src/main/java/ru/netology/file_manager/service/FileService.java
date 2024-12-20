package ru.netology.file_manager.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.file_manager.model.FileInfo;

import java.io.IOException;
import java.util.List;

public interface FileService {
    FileInfo upload(MultipartFile resource) throws IOException;

    List<FileInfo> filelist();

}
