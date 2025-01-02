package ru.netology.file_manager;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileManagerApplication {
	static final Logger log =
            (Logger) LoggerFactory.getLogger(FileManagerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(FileManagerApplication.class, args);
	}
}
