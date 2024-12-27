package ru.netology.file_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.netology.file_manager.model.FileInfo;

import java.util.List;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo,Long> {
    @Query(nativeQuery = true, value = "select * from fileinfo where name = :filename")
    List<FileInfo> findByName(String filename);
}
