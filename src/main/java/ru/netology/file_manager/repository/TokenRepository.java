package ru.netology.file_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.netology.file_manager.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(nativeQuery = true, value = "select * from tokens where token = :token")
    Token findByToken(String token);
}
