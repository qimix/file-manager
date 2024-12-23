package ru.netology.file_manager.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "fileinfo")
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String name;
    @Column
    private Long size;
    @Column
    private String keyFile;
    @Column
    private LocalDate uploadDate;

    public static Builder builder() {
        return new FileInfo().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(Long id) {
            FileInfo.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            FileInfo.this.name = name;
            return this;
        }

        public Builder setSize(Long size) {
            FileInfo.this.size = size;
            return this;
        }

        public Builder setKeyFile(String keyFile) {
            FileInfo.this.keyFile = keyFile;
            return this;
        }

        public Builder setLocalDate(LocalDate uploadDate) {
            FileInfo.this.uploadDate = uploadDate;
            return this;
        }

        public FileInfo build() {
            return FileInfo.this;
        }

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getSize() {
        return size;
    }

    public String getKeyFile() {
        return keyFile;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }
}
