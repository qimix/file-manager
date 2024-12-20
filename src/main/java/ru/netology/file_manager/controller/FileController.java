package ru.netology.file_manager.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.file_manager.model.FileInfo;
import ru.netology.file_manager.service.FileService;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @CrossOrigin
    @PostMapping("/file")
    public ResponseEntity<FileInfo> upload(@RequestParam MultipartFile attachment) {
        try {
            return new ResponseEntity<>(fileService.upload(attachment), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping("/list")
    public List<FileInfo> filelist(@RequestParam("limit") int limit) {
        return fileService.filelist();
    }
}
