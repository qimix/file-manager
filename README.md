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
2. Загрузка файла на сервер: ```curl -X POST -H "multipart/form-data" -H "auth-token: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlkIjo5OTgsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE3Mzg2MTAzMDEsImV4cCI6MTczODc1NDMwMX0.VpSa3VBS8ha4hgSnrg9gfqRdBH-KZXZnVHjiY9D18eE" -F file=@file.txt -L "http://localhost:8080/file?filename=file.txt"```
3. Получение списка файлов: ```curl -X GET -H "auth-token: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlkIjo5OTgsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE3Mzg2MTAzMDEsImV4cCI6MTczODc1NDMwMX0.VpSa3VBS8ha4hgSnrg9gfqRdBH-KZXZnVHjiY9D18eE" -L "http://localhost:8080/list?limit=3"```
4. Удаление файла: ```curl -X DELETE -H "auth-token: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlkIjo5OTgsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE3Mzg2MTAzMDEsImV4cCI6MTczODc1NDMwMX0.VpSa3VBS8ha4hgSnrg9gfqRdBH-KZXZnVHjiY9D18eE" -L "http://localhost:8080/file?filename=file.txt"```

#### Запуск фронтенда:
1. Склонировать репозиторий: ```git clone https://github.com/qimix/netology-diplom-frontend.git```
2. Перейти в папку проекта и запустить сервис: ```cd netology-diplom-frontend```, ```npm install```, ```npm run serve```
3. Тестовый логин\пароль ```"login":"admin@gmail.com", "password":"admin1234```
