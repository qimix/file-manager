package ru.netology.file_manager.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String token;

    public static Token.Builder builder() {
        return new Token().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Token.Builder setToken(String token) {
            Token.this.token = token;
            return this;
        }

        public Token build() {
            return Token.this;
        }
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
