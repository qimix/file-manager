package ru.netology.file_manager;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.netology.file_manager.model.FileInfo;
import ru.netology.file_manager.repository.FileInfoRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@Transactional
class RepositoryTest {
    @Autowired
    private FileInfoRepository fileInfoRepository;
    private FileInfo fileInfo;

    @BeforeEach
    public void initData(){
        fileInfo = FileInfo.builder()
                .setName("test")
                .setSize(999L)
                .setKeyFile("test")
                .setLocalDate(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("JUnit test for FileInfoRepository.findAll")
    void testData() {
        fileInfoRepository.save(fileInfo);
        List<FileInfo> results = (List<FileInfo>) fileInfoRepository.findAll();
        assertFalse(results.isEmpty());
    }
}
