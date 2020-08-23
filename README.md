# Product-service
The spring boot java microservice allows you to read all the product data.
It exposes a Rest Api for interaction, check Swagger for more details of Api and Models.

```
Swagger: 
        localhost:8080
``` 

 
### Topics
  * [Assumptions](#assumptions)
  * [What does it do ?](#technical-details)
  * [How to use it ?](#guide)
  * [Tech Stack](#tech-stack)
  * [Screen-shot](#screen-shot)
  
  
###  [Assumptions](#assumptions)
    
    1. The product data is static and thats why no polling cache solution is added. If data was being refreshed 
       periodically , would add polling event to check for any updates.
    2. Basic authentication is added to "/product" endpoint(username: admin & password: admin).
    3. Api filters out the product which has 0 stock.
    4. Api response also ignores the product id to be expose, but can be enabled by removing @JsonIgnore.
    
###  [What does it do ?](#technical-details)
 The microservice exposes through Rest Api the product data which is read from a csv file.
 
 
###  [How to use it ?](#guide) 
 
 1. Docker Run : 
```
    Run the below command from project directory :

    sh run_app.sh

```
 2. Command Line or IDE:

```  
    mvn clean install 
    
    mvn spring-boot:run
    
    Else : 
        Run ProductServiceApplication.java from IDE. 
``` 

NOTE: Basic authentication is enabled on /product endpoint, use below credential in Postman to access.

``` 
    username: admin
    password: admin    
``` 
 
### [Tech Stack](#tech-stack)
Java 11, OpenCSV, Swagger, Spring Boot, Spring Security, Maven, Lombok, Mockito, Junit5, AssertJ, docker and docker-compose.
This microservice has been built with IntelliJ IDE and formatted with google-java-format. 

### [Screen-shot](#screen-shot)
![alt text](https://github.com/anusheelchandra/product-service/blob/master/src/test/resources/ScreenShot1.png)
![alt text](https://github.com/anusheelchandra/product-service/blob/master/src/test/resources/ScreenShot2.png) 
 