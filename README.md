#Union of European Football Associations(*UEFA*) Clubs


### Overview
*  Project Original Thoughts

*  Project Technical Overview:  
Based on the Spring Framework by using 
[Spring Boot](https:https://www.tutorialspoint.com/spring_boot/spring_boot_introduction.htm),
[Hibernate](https://howtodoinjava.com/hibernate-tutorials/),
[Spring RESTful web services](https://www.tutorialspoint.com/spring_boot/spring_boot_building_restful_web_services.htm),
[Amazon SQS](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-sqs-messages.html),
[AmazonS3](https://docs.aws.amazon.com/AmazonS3/latest/dev/UploadObjSingleOpJava.html),
Postman, Maven, [flyway](https://flywaydb.org/getstarted/why), PostgresSql, Docker, Amazon SQS, and Amazon S3.
---
* Project Approach  
    1. Create Club, Player, Account, Image, User, Role object, and created related table and columns in database.
    2. The relation between Club(inverse side) and Player(owing side) is One to Many, the club_id is the foreign key(fk) and stored in the players table.
    3. The relation between Player(inverse side) and Account(owing side) is One to Many, the player_id is the fk and stored in accounts table.
    4. The relation between User and Image is One to Many, the user_id is the fk and stored in images table.
    5. The relation between User and Role is Many to Many, by creating Bridge Table users_roles to combine them together.
---
* Building Project
    * Configure local environment(docker)
    ```java
       docker pull postgres
       docker run --name dealerDB -e POSTGRES_DB=dealer -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=password -p 5431:5432 -d postgres
    ```
    * Flyway database migration
    
    ```xml
      <plugin>
          <groupId>org.flywaydb</groupId>
          <artifactId>flyway-maven-plugin</artifactId>
          <version>${flyway.version}</version>
          <configuration>
            <driver>org.postgresql.Driver</driver>
            <url>jdbc:postgresql://localhost:5431/databaseName</url>
            <user>admin</user>
            <password>password</password>
            <schemas>
               <schema>public</schema>
            </schemas>
          </configuration>
       </plugin>
    ```
    
    * Environment properties (Run time VM options)
        
    ```
        -Ddatabase.driver=org.postgresql.Driver
        -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect
        -Ddatabase.url=jdbc:postgresql://localhost:5431/databaseName
        -Ddatabase.user=admin
        -Ddatabase.password=password
        -Daws.accessKeyId=AWS accesskey
        -Daws.secretKey=AWS secretKey
        -Dspring.profiles.active=dev
        -Daws.s3.bucketName=my own bucket name
        -Daws.sqs.name=my own queue name
    ```
      
    * Database Migration :Schema migration for execute DDL DML based on the Environment properties in resources/db.migration folder
    ```
        mvn clean compile flyway:clean
        mvn clean compile flyway:migrate
    ``` 
    * Spring boot entry point
    
    ```Java
    @SpringBootApplication(scanBasePackages = {"com.ascending.training.club"})
    @ServletComponentScan(basePackages = {"com.ascending.training.club.filter"})
    public class AppBootStrap {
        public static void main(String[] args) {
            SpringApplication.run(AppBootStrap.class,args);
        }
    }
    ```
  
    * Reference Demo
    
    