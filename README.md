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