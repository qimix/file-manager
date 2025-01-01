package ru.netology.file_manager;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.file_manager.model.FileInfo;
import ru.netology.file_manager.utils.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FileManagerApplicationTests {
    private static MultipartFile multipartFile;
    private static FileManager manager;
    private static FileInfo file;

    @BeforeAll
    public static void initData() throws IOException {
        file = FileInfo.builder()
                .setId(9L)
                .setName("mockFile.txt")
                .setKeyFile("mockFile.txt")
                .setSize(38975L)
                .setLocalDate(LocalDate.now())
                .build();
        multipartFile = new MockMultipartFile("mockFile", "mockFile.txt", "txt",
                new FileInputStream("src/test/resources/mockFile.txt"));
        manager = new FileManager();
        File dir = new File("src/test/resources/testFileStorage");
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Test
    void contextLoads() {
        assertThat(multipartFile).isNotNull();
        assertThat(manager).isNotNull();
        assertThat(file).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for FileManager.upload")
    public void uploadTest() throws IOException {
        ReflectionTestUtils.setField(manager, "DIRECTORY_PATH", "src/test/resources/testFileStorage/");
        manager.upload(multipartFile.getBytes(), "mockFile.txt");

        Path checkFile = Paths.get("src/test/resources/testFileStorage/mockFile.txt");
        assertThat(Files.exists(checkFile)).isTrue();
        assertThat(Files.isRegularFile(checkFile)).isTrue();
        assertThat(Files.size(checkFile)).isEqualTo(multipartFile.getSize());
        Files.delete(checkFile);
    }

    @Test
    @DisplayName("JUnit test for FileManager.download")
    public void downloadTest() throws IOException {
        ReflectionTestUtils.setField(manager, "DIRECTORY_PATH", "src/test/resources/");
        Resource resource = manager.download(file.getKeyFile());

        assertThat(resource.isFile()).isTrue();
        assertThat(resource.getFilename()).isEqualTo(file.getName());
        assertThat(resource.exists()).isTrue();
    }

    @Test
    @DisplayName("JUnit test for FileManager.delete")
    public void deleteTest() throws IOException {
        Path checkFile = Paths.get("src/test/resources/testFileStorage/mockFile.txt");
        Files.createFile(checkFile);
        assertThat(Files.exists(checkFile)).isTrue();
        assertThat(Files.isRegularFile(checkFile)).isTrue();
        ReflectionTestUtils.setField(manager, "DIRECTORY_PATH", "src/test/resources/testFileStorage/");

        manager.delete(file.getKeyFile());
        assertThat(Files.notExists(checkFile)).isTrue();
    }

}
