package ru.netology.file_manager;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.file_manager.model.FileInfo;
import ru.netology.file_manager.utils.FileManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

@SpringBootTest
class FileManagerApplicationTests {
    private static MultipartFile multipartFile;
    private static FileManager manager;
    private static FileInfo file;

    @Test
    void contextLoads() {
    }


    @BeforeClass
    public static void prepareTestData() throws IOException {
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
    }

}
