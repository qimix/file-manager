package ru.netology.file_manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import ru.netology.file_manager.model.FileInfo;
import ru.netology.file_manager.service.FileServiceImpl;
import ru.netology.file_manager.utils.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ServicesTests {
    String fileName = "mockFile.txt";
    private MultipartFile multipartFile;
    @Autowired
    @Qualifier(value = "fileServiceImpl")
    private FileServiceImpl fileService;
    private FileManager fileManager;

    @BeforeEach
    public void initData() throws IOException {
        multipartFile = new MockMultipartFile("mockFile", fileName, "txt",
                new FileInputStream("src/test/resources/mockFile.txt"));
        fileManager = new FileManager();
        ReflectionTestUtils.setField(fileManager, "DIRECTORY_PATH", "src/test/resources/testFileStorage/");
        ReflectionTestUtils.setField(fileService, "fileManager", fileManager);
    }

    @Test
    void contextLoads() {
        assertThat(multipartFile).isNotNull();
        assertThat(fileService).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for FileService.upload")
    public void uploadTest() throws IOException {
        fileService.upload(multipartFile);
        File file = new File("src/test/resources/testFileStorage/");
        File[] files = file.listFiles();
        assertThat(files.length > 0).isTrue();
        //FileUtils.deleteDirectory(new File("src/test/resources/testFileStorage/"));
    }

    @Test
    @DisplayName("JUnit test for FileService.filelist")
    public void filelistTest() {
        List<FileInfo> fileList = fileService.filelist();
        assertThat(fileList.size() > 0).isTrue();
    }

    @Test
    @DisplayName("JUnit test for FileService.delete")
    public void deleteTest() throws IOException {
        fileService.delete(fileName);
        assertThat(FileUtils.isEmptyDirectory(new File("src/test/resources/testFileStorage/"))).isTrue();
    }

}
