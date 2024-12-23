package ru.netology.file_manager.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<FileListResp>> filelist(@RequestParam("limit") Integer limit) {
        var fileInfoList = fileService.filelist().stream().limit(limit).toList();
        //FileInfo fileInfo = FileInfo.builder().setName(fileInfoList.get(0).getName()).build();
        FileListResp fileListResp = FileListResp.builder().setFilename(fileInfoList.get(0).getName()).build();
        List<FileListResp> list = new ArrayList<FileListResp>();
        list.add(fileListResp);
        return ResponseEntity.ok(list);
    }
}
