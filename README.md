### Сервис для хранения файлов
##### Сборка и запуск сервиса:
1. Склонировать репозиторий с проектом командой: ```git clone https://github.com/qimix/file-manager.git```
2. Для сборки проекта потребуется установленный [openjdk-21.0.2](https://download.java.net/openjdk/jdk21/ri/openjdk-21+35_windows-x64_bin.zip)
3. Проверить версию java: ```java --version```
4. Перейти в папку с проектом и запустить команду сборки: ```cd c:\file-manager```,  ```mvn clean package```
5. Запуск сервиса в cmd: ```java -jar file-manager-0.0.1-SNAPSHOT.jar```
6. Возможно запустить команду сборки контейнера: ```docker build -t file-manager .```
2. Запустить контейнер с приложением коммандой: ```docker run run -d -p 8080:8080 --name file-manager file-manager```
3. Также возможно запустить приложение через compose: ```docker compose up```

##### Проверка сервиса:
1. Получение токена: ```curl -X POST -H "Content-Type: application/json" -L "http://localhost:8080/login" -d "{\"login\":\"admin@gmail.com\", \"password\":\"admin1234\"}"```
