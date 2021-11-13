
# Mancala Game [![GitHub Workflow Status](https://img.shields.io/github/workflow/status/amrkramzy/store-locator/Continuous%20Integration?label=Continuous%20Integration&logo=sonarqube)](https://github.com/amrkramzy/store-locator/actions/workflows/main.yml) [![GitHub Workflow Status](https://img.shields.io/github/workflow/status/amrkramzy/store-locator/Deploy%20to%20Amazon%20ECS?label=Deploy%20to%20Amazon%20ECS&logo=amazon)](https://github.com/amrkramzy/store-locator/actions/workflows/aws.yml) [![Sonar Tests](https://img.shields.io/sonar/tests/amrkramzy_store-locator?compact_message&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=amrkramzy_store-locator) [![Docker Image Version (latest by date)](https://img.shields.io/docker/v/amrkramzy/store-locator?logo=docker)](https://hub.docker.com/r/amrkramzy/store-locator) [![Docker Image Size (latest by date)](https://img.shields.io/docker/image-size/amrkramzy/store-locator?logo=docker)](https://hub.docker.com/r/amrkramzy/store-locator)

[![screenshots1](images/mancala-ui.png)](images/mancala-ui.png)
### Board Setup
Each of the two players has his six pits in front of him. To the right of the six pits, each player has a larger pit. At the start of the game, there are six stones in each of the six round pits .
### Rules
#### Game Play
The player who begins with the first move picks up all the stones in any of his
own six pits, and sows the stones on to the right, one in each of the following
pits, including his own big pit. No stones are put in the opponents' big pit. If the
player's last stone lands in his own big pit, he gets another turn. This can be
repeated several times before it's the other player's turn.
#### Capturing Stones
During the game the pits are emptied on both sides. Always when the last stone
lands in an own empty pit, the player captures his own stone and all stones in the
opposite pit (the other player’s pit) and puts them in his own (big or little?) pit.

#### The Game Ends
The game is over as soon as one of the sides runs out of stones. The player who
still has stones in his pits keeps them and puts them in his big pit. The winner of
the game is the player who has the most stones in his big pit.
You can also find some visual explanations of the game rules by running a
Google Search for Mancala or Kalaha game.




<div align="center">
  <sub>Built with ❤︎ by <a href="mailto:eng.amr.kamel@gmail.com">Amr Ramzy</a> 
</div>

</br>

### The application is deployed on **AWS ECS** and a Demo Angular application can be used to demo the APIs on the **following link** [http://jumbo-alb-2004901793.eu-central-1.elb.amazonaws.com](http://jumbo-alb-2004901793.eu-central-1.elb.amazonaws.com)


<p >
	<a href="https://github.com/AmrRamzy/mancala-ui"><strong>FrontEnd application repository »</strong></a>
	<br />
   <a href="https://hub.docker.com/r/amrkramzy/bol-mancala-ui-demo"><strong>FrontEnd application Docker image »</strong></a>
</p>

## Application screenshots

### choose a location by one of the following methods:
- Enter address in search bar
- choose a location on the map 
- Enter latitude/longitude in the input field

[![screenshots1](images/store-locator-ui-demo-aws-1.png)](images/store-locator-ui-demo-aws-1.png)
[![screenshots2](images/store-locator-ui-demo-aws-2.png)](images/store-locator-ui-demo-aws-2.png)
[![screenshots3](images/store-locator-ui-demo-aws-3.png)](images/store-locator-ui-demo-aws-3.png)
### Error flow
[![Error screenshots](images/store-locator-ui-demo-aws-validation-error.png)](images/store-locator-ui-demo-aws-validation-error.png)
<br />


## Details

- [Technology stack & other Open-source libraries](#Technology-Stack)   
- [Installation](#Installation)  
- [Deployment](#Deployment)  
- [Rest API](#rest-api)
- [CI/CD](#Continuous-integration-and-Continuous-delivery)
- [Documentation](#Documentation) 



## Technology Stack

### Main Technology


|                                      Technology                                               |                              Description                                                                                                                      |
|-----------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
|<a href="https://spring.io/projects/spring-boot">Spring Boot2</a>                             |Spring Boot is an open source Java-based framework used to create a micro Service.                                                                                                                         |
|<a href="https://docs.spring.io/spring-framework/docs/current/reference/html/web.html">Spring Web MVC</a>                             |Spring Web MVC is the original web framework built on the Servlet API.                                                       |
|<a href="https://spring.io/projects/spring-data-mongodb">Spring Data Mongodb</a>|The Spring Data MongoDB project provides integration with the MongoDB document database.                        |
|<a href="https://www.mongodb.com/cloud/atlas">MongoDB Atlas</a>                                                      |MongoDB Atlas is a fully-managed cloud database developed by the same people that build MongoDB.           |
|<a href="https://www.docker.com/">Docker</a>                        |Docker is an open source containerization platform. It enables developers to package applications into containers. |                                                                                            

###  Libraries and Plugins

|                                      Technology                                               |                              Description                                                                                                                      |
|-----------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
|<a href="https://projectlombok.org/">Lombok</a>                                                |Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.|                                                
|<a href="https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-devtools.html">Spring Developer tools</a>|Spring Developer tools provide an additional set of tools that can make the application development experience a little more pleasant.                 |
|<a href="https://github.com/ulisesbocchio/jasypt-spring-boot">jasypt-spring-boot</a>      |Jasypt Spring Boot provides Encryption support for property sources in Spring Boot Applications.                                                     |
|<a href="https://spring.io/guides/gs/validating-form-input/">Spring Boot Validation</a>                             |Spring MVC validation library that takes user input and checks the input by using standard validation annotations.                                     |
|<a href="https://swagger.io/">Swagger</a>                                                      |Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTFUL Web services.           |



## Installation

### Prerequisites
*	[Java 11](https://jdk.java.net/11/)
*	[Apache Maven](https://maven.apache.org/)
### Profiles
The application has 3 profiles to work with 
*	dev: Connect the application to an instance of MongoDB on local machine
*	test: Connect the application to an instance of MongoDB on test environment
*	prod: Connect the application to an instance of MongoDB Atlas (cloud database) for production <strong style='color:green;'>(default)</strong>
#### To specify the active profile
*	Maven : use the -P parameter to switch which Maven profile will be applied
	```shell
	mvn clean package -Pdev
	```
*	Environment Variable : profiles can also be activated via the environment variable "spring_profiles_active"
	```shell
	export spring_profiles_active=dev
	```
### Building the application with Maven

Build the application using  [Apache Maven](https://maven.apache.org/) like so:

```shell
git clone https://github.com/AmrRamzy/mancala.git
cd mancala
mvn clean package -Pprod
```

## Deployment

### Important Note:	Front-end Angular application [mancala-ui](https://github.com/AmrRamzy/mancala-ui) can be used for demonstration purpose .


<br>
<details open="open">
   <ul>
      <li>
         Running the application with Executable JAR
         <ul>
            <li>
               <a href="#running-the-application-with-executable-jar-linux-os">Linux OS</a>
            </li>
            <li>
               <a href="#running-the-application-with-executable-jar-windows-os">Windows OS</a>
            </li>
         </ul>
      </li>
      <li><a href="#running-the-application-via-docker-container">Running the application via docker container</a></li>
      <li><a href="#running-the-application-via-docker-compose">Running the application via docker-compose <strong style='color:green;'>(recommended)</strong></a></li>
   </ul>
</details>
<br>
<br>
<strong style='color:red;'>IMPORTANT DEPLOYMENT INFO</strong>
<ul>
   <li>
      The application requires an environment variables for DB password encryption 
      <ul>
         <li>DB_JASYPT_ENCRYPTOR_PASSWORD=DB-secret</li>
      </ul>
   </li>
   <li>The application will run by default on port 8989 URL: <a href="http://localhost:8989/">http://localhost:8989/</strong></a></li>
</ul>
<br>
<br>

#### Running the application with Executable JAR Linux OS

The code can be built into a jar and then executed/run by using the below commands

```shell
git clone https://github.com/AmrRamzy/mancala.git
cd mancala
mvn clean package -Pprod 
export DB_JASYPT_ENCRYPTOR_PASSWORD=DB-secret
java -jar ./target/mancala-0.0.1-SNAPSHOT.jar
```

#### Running the application with Executable JAR Windows OS

The code can be built into a jar and then executed/run by using the below commands

```shell
git clone https://github.com/AmrRamzy/mancala.git
cd mancala
mvn clean package -Pprod
set DB_JASYPT_ENCRYPTOR_PASSWORD=DB-secret
java -jar ./target/mancala-0.0.1-SNAPSHOT.jar
```

#### Running the application via docker container

The code can be built into a jar and then executed/run on Docker container by using the below commands

* 	build the jar and Docker image .

```shell
git clone https://github.com/AmrRamzy/mancala.git
cd mancala
mvn clean package -Pprod
docker build -t bol-mancala .
docker container run -p 8989:8989 -e DB_JASYPT_ENCRYPTOR_PASSWORD=DB-secret bol-mancala
```

* 	or run the container from dockerHub image directly.

```shell
docker container run -p 8989:8989 -e DB_JASYPT_ENCRYPTOR_PASSWORD=DB-secret amrkramzy/bol-mancala
```


#### Running the application via docker-compose 
<div>
<strong style='color:green;'>The recommended</strong> way it to run the docker-compose file which will start the Spring boot application and an angular UI application for demo purpose on the following url : <a href="http://localhost:8585/">angular UI Demo</a>
</div>


```shell
docker-compose pull
docker-compose up
```
## REST API

This is a simple  documentation for the application REST APIs.<br>
For full APIs documentation please check [Documentation section](#documents) 


## Continuous integration and Continuous delivery
### GitHub action is used here to create 2 workflows
[![workflow](images/github-workflows.png)](images/github-workflows.png)
#### Continuous Integration (will be triggered on push and pull requests)
- Build the application and run both unit test and integration test using Maven  
- Apply code analytics with SonarCloud
- Build a docker image and publish it to dockerhub
#### Deploy to Amazon ECS (will be triggered on creating a release )
- Deploy the created DockerHub image to Amazon ECS cluster




## Documentation

* 	[Postman Collection APIs](https://documenter.getpostman.com/view/13659214/UVC8DRzy) - `https://documenter.getpostman.com/view/13659214/UVC8DRzy`- Documentation & Testing
* 	[Swagger](http://localhost:8989/bol/swagger-ui/#/game-rest-controller) - `http://localhost:8989/bol/swagger-ui/#/game-rest-controller`- Documentation & Testing 
* 	[Swagger](http://localhost:8989/bol/v2/api-docs) - `http://localhost:8989/bol/v2/api-docs`- Documentation & Testing
*	Find Java Doc in **doc** folder



## Contact

Amr Ramzy - eng.amr.kamel@gmail.com

Project Link: [https://github.com/AmrRamzy/mancala](https://github.com/AmrRamzy/mancala)
