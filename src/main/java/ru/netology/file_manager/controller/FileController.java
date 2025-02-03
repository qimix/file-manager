package ru.netology.file_manager.controller;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.file_manager.dto.FileListResp;
import ru.netology.file_manager.exception.DeleteFileException;
import ru.netology.file_manager.exception.ErrorDeleteFileException;
import ru.netology.file_manager.exception.UploadFileException;
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
    public ResponseEntity<String> upload(@RequestParam("filename") String filename, @RequestBody MultipartFile file) throws Exception {
        try {
            fileService.upload(file);
            return ResponseEntity.ok("Upload file to server");
        } catch (Exception e) {
            throw new UploadFileException(e);
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
    public ResponseEntity<String> delete(@RequestParam("filename") String filename) throws IOException, DeleteFileException, ErrorDeleteFileException {
        if (filename.isEmpty()) {
            throw new DeleteFileException("Error input data");
        }
        if (fileService.checkFileExits(filename)) {
            fileService.delete(filename);
            return ResponseEntity.ok("Success deleted");
        } else {
            throw new ErrorDeleteFileException("Error delete file");
        }
    }

    @CrossOrigin
    @GetMapping(path = "/file")
    public ResponseEntity<Resource> download(@RequestParam("filename") String filename) {
        try {
            FileInfo foundFile = fileService.findByName(filename);
            Resource resource = fileService.download(foundFile.getKeyFile());
            return ResponseEntity.ok()
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
