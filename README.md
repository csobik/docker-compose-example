# Docker Compose Example
Docker Compose lets you define, configure and run multiple docker images required 
for your project. 

## Installing Docker Compose
You can run Docker Compose on Linux, Windows and MacOS

### Linux 
Firstly try to use your package manager. For example:

```
apt search docker-compose 
sudo apt install docker-compose
```

alternatively you can install manually:

```
sudo curl -L "https://github.com/docker/compose/releases/download/1.23.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

### Windows and MacOS

Docker for Mac and Windows and Docker Toolbox already include Compose along with other Docker apps, so Mac users do not need to install Compose separately.

## Create your application

```
gradle init --type java-application
```

and setup some database and entities. Like in this spring tutorial \
 [https://spring.io/guides/gs/accessing-data-mysql/] 

## Setup Docker Compose

Create file compose.yml as many images as you need for your application.

```yaml
version: '3'
services:

  adminer:
    image: adminer
    restart: always
    ports:
      - 9080:8080
    depends_on:
      - example-db

  example-db:
    image: mariadb:10.3.8
    ports:
      - 3306:3306
    environment:
      MYSQL_ROOT_PASSWORD: Password1122
      MYSQL_DATABASE: db_example
    hostname: db
    volumes:
      - "./mariadb-config/:/etc/mysql/conf.d/"
    tmpfs: "/var/lib/mysql"
``` 
In this example we will use adminer and mariadb images.

## Gradle Improvements 

You can extend gradle configuration with some extension.

By applying `apply plugin: 'docker-compose'` you will get possibility to start or stop 
all configured docker images. You will get `composeUp` and `composeDown` tasks

## Dockerize yours Application

Docker compose can help you to run your application fully dockerized.
For that create `Dockerfile` as usual.

```
FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER Csobik <csobik@gmail.com>
ADD docker-compose-example-executable.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
```

then add new service configuration to compose.yml file

```yaml
  user-service:
    image: example/docker-compose-example:latest
    ports:
    - "8080:8080"
    depends_on:
    - example-db
    environment:
      SPRING_PROFILES_ACTIVE: test
      SPRING_DATASOURCE_URL: jdbc:mysql://example-db:3306/db_example
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: Password1122
```

If you add task to your `build.gradle` file you can then create docker file directly from gradle:

```yaml
apply plugin: 'com.bmuschko.docker-java-application'

// build docker image
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
task buildDockerImage(type: DockerBuildImage, dependsOn: [bootJar]) {
    doFirst {
        copy {
            from 'config/Dockerfile'
            into 'build/libs'
        }
    }
    tag = "example/docker-compose-example:latest"
    inputDir = project.file('build/libs')

}
```

