package ru.netology.file_manager.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.file_manager.dto.FileListResp;
import ru.netology.file_manager.model.FileInfo;
import ru.netology.file_manager.service.FileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;

    @CrossOrigin
    @PostMapping("/file")
    public ResponseEntity<String> upload(@RequestParam("filename") String filename, @RequestBody MultipartFile file) throws IOException {
        logger.debug("Request body: " + file);
        try {
            fileService.upload(file);
            return ResponseEntity.ok("Upload file to server");
        } catch (Exception e) {
            throw new IllegalArgumentException("Error input data");
        }
    }

    @CrossOrigin
    @GetMapping("/list")
    public ResponseEntity<List<FileListResp>> filelist(@RequestParam("limit") Integer limit) {
        List<FileInfo> fileInfoList = fileService.filelist().stream().limit(limit).toList();
        List<FileListResp> fileListResp = new ArrayList<>();
        for (FileInfo fileInfo : fileInfoList) {
            fileListResp.add(new FileListResp().builder().setFilename(fileInfo.getName()).build());
        }
        return ResponseEntity.ok(fileListResp);
    }

    @CrossOrigin
    @DeleteMapping("/file")
    public ResponseEntity<String> delete(@RequestParam("filename") String filename) throws IOException {
        try {
            fileService.delete(filename);
            return ResponseEntity.ok("Success deleted");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error input data");
        }
    }

}
