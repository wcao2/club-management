## Union of European Football Associations(*UEFA*) Clubs


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
### Project Approach  
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
---
* Reference Demo
    #### User Sign Up
    ```URL
    http://localhost:8080/auth/signup
   ```
  Request Body
  ``` Json
    {
    	"name":"Sam",
    	"email":"sam@gmu.edu",
    	"password":"123456789@test"
    }
  ```
  Response Body
  ```Json
    {
        "id": 8,
        "name": "Sam",
        "first_name": null,
        "last_name": null,
        "email": "sam@gmu.edu",
        "address": null,
        "hired_date": "2020-05-10",
        "department": {
            "id": 2,
            "name": "R&D",
            "description": "Research and Development",
            "location": "Room 101, 999 Washington Ave. Falls Church, VA"
        },
        "account": null,
        "roles": [
            {
                "name": "user"
            }
        ]
    }
  ```
   ![](images/signUp.png)
     #### User Login
     Request Body
   ```  Json
     {
        "email":"sam@gmu.edu",
        "password":"123456789@test"
     }
   ```
     Response Body
     ``` Json
     {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4Iiwic3ViIjoiU2FtIiwiaWF0IjoxNTg5MTQ4NjA4LCJpc3MiOiJjb20uYXNjZW5kaW5nIiwiZXhwIjoxNTg5MjM1MDA4LCJhbGxvd2VkUmVzb3VyY2UiOiIvY2x1YnMsL3BsYXllciwvcGxzLC9hY250cywvYWNjb3VudHMiLCJhbGxvd2VkUmVhZFJlc291cmNlcyI6Ii9jbHVicywvcGxheWVyLC9wbHMsL2FjbnRzLC9hY2NvdW50cyIsImFsbG93ZWRDcmVhdGVSZXNvdXJjZXMiOiIvY2x1YnMsL3BsYXllciwvcGxzLC9hY250cywvYWNjb3VudHMiLCJhbGxvd2VkVXBkYXRlUmVzb3VyY2VzIjoiL2NsdWJzLC9wbGF5ZXIsL3BscywvYWNudHMsL2FjY291bnRzIiwiYWxsb3dlZERlbGV0ZVJlc291cmNlcyI6IiJ9.90Z1iGP3yV1J52M7H7TBZQg8vDNRNL4VHx1Jwi-q7HU"
     }
     ```
    ![](images/loginIn.png)
     #### Get All Clubs and Related Players
     ![](images/getAllClubsandItsPlayers.png)
     #### Upload a sample file to s3 and generate one message stored in sqs
     ![](images/uploadS3.png)
     ![](images/s3.png)
     ![](images/sqs.png)
     #### Use one of the Java Message Service(JMS) model point to point. A message is delivered from a producer to one of the consumer which registered for the queue.   
     ![](images/JMS.png)
     ####Deploy application into tomcat.  
     #####1:Package application to a war file
     ![](images/package.png)
     #####2:Add all vm options from IDE into setenv.sh. For ubuntu, the path is usr/share/tomcat9/bin, then gedit setenv.sh  
     #####3:Deploy the war file to tomcat container and run it  
     For build image, there are three essential files(war file, Dockefile, setenv.sh) need to be in the folder called container which in the web folder.
     ![](images/buildImage.png)
     
     ![](images/runImage.png)