package ru.netology.file_manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.netology.file_manager.model.FileInfo;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileListResp {
    @JsonProperty()
    String filename;

    public static FileListResp.Builder builder() {
        return new FileListResp().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public FileListResp.Builder setFilename(String filename) {
            FileListResp.this.filename = filename;
            return this;
        }

        public FileListResp build() {
            return FileListResp.this;
        }
    }

}
