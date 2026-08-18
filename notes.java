//        .\mvnw.cmd clean test


//         .\mvnw.cmd spring-boot:run







POST 

http://localhost:8080/api/customers

{
    "fullName": "Ahmed Mohamed",
    "email": "ahmed@example.com",
    "phone": "01012345678",
    "password": "Ahmed@123"
}



GET http://localhost:8080/api/customers




git add docker-compose.yml
git add src/main/resources/application.properties
git add src/main/java/
git add src/main/resources/db/migration/
git add src/test/java/

git commit -m "Complete customer management"
git push