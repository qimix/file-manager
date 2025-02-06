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
import ru.netology.file_manager.exception.*;
import ru.netology.file_manager.model.FileInfo;
import ru.netology.file_manager.service.FileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.netology.file_manager.utils.LogConstants.LOG_SEPARATOR;

@RestController
@AllArgsConstructor
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;

    @CrossOrigin
    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam("filename") String filename, @RequestBody MultipartFile file) throws Exception {
        logger.info("start upload file: {}", filename);
        logger.info(LOG_SEPARATOR);
        try {
            fileService.upload(file);
            logger.info("file {} uploaded", filename);
            logger.info(LOG_SEPARATOR);
            return ResponseEntity.ok("upload file to server");
        } catch (Exception e) {
            logger.error("error upload file: {}", filename);
            logger.info(LOG_SEPARATOR);
            throw new UploadFileException(e);
        }
    }

    @CrossOrigin
    @GetMapping("/list")
    public ResponseEntity<List<FileListResp>> listFile(@RequestParam("limit") Integer limit) throws ErrorGettingFileListException {
        List<FileInfo> fileInfoList = fileService.filelist().stream().limit(limit).toList();
        List<FileListResp> fileListResp = new ArrayList<>();
        logger.info("starting getting file list");
        logger.info(LOG_SEPARATOR);
        if (fileInfoList.isEmpty()) {
            logger.error("error getting file list");
            throw new ErrorGettingFileListException("error getting file list");
        }
        for (FileInfo fileInfo : fileInfoList) {
            fileListResp.add(new FileListResp().builder().setFilename(fileInfo.getName()).build());
        }
        logger.info("getting file list successful");
        return ResponseEntity.ok(fileListResp);
    }

    @CrossOrigin
    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestParam("filename") String filename) throws IOException, DeleteFileException, ErrorDeleteFileException {
        logger.info("starting delete file");
        logger.info(LOG_SEPARATOR);
        if (filename.isEmpty()) {
            logger.error("error input data");
            throw new DeleteFileException("error input data");
        }
        if (fileService.checkFileExits(filename)) {
            fileService.delete(filename);
            logger.info("success deleted");
            return ResponseEntity.ok("success deleted");
        } else {
            logger.error("error delete file");
            throw new ErrorDeleteFileException("error delete file");
        }
    }

    @CrossOrigin
    @GetMapping(path = "/file")
    public ResponseEntity<Resource> downloadFile(@RequestParam("filename") String filename) {
        logger.info("Starting download file: {}", filename);
        logger.info(LOG_SEPARATOR);
        try {
            FileInfo foundFile = fileService.findByName(filename);
            Resource resource = fileService.download(foundFile.getKeyFile());
            logger.info("Succeed download file: {}", filename);
            return ResponseEntity.ok()
                    .body(resource);
        } catch (IOException e) {
            logger.error("Error download file: {}", filename);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
