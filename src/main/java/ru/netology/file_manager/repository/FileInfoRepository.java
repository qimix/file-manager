package ru.netology.file_manager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.netology.file_manager.model.FileInfo;

@Repository
public interface FileInfoRepository extends CrudRepository<FileInfo,Long> {
    FileInfo findByName(String filename);
}
