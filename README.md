# JSON Parser

Json parser framework is built upon [circe](https://circe.github.io/circe/parsing.html) library and provides a mechanism
to parse JSON object hierarchies. The project provides a REST endpoint to which a JSON string can be posted and verified
if it's a valid JSON string.

## Running via Docker

This project can be run via docker containers. There is an integration within SBT build tool to create and publish
docker images. Perform the following steps in order to run this project via docker:

1. To publish a docker image to local docker repo, run the following command:

```
sbt docker:publishLocal
```

2. To bring up the parser API from the docker container, run the below command:

```
docker-compose -f docker-compose.yml up -d
```

3. If you don't have any other docker containers running, you can shut down the ones for this project with the following
   command:

```
docker stop $(docker ps -aq)
```

4. Finally, if you want to remove all docker images:

```
docker system prune -a -f --volumes
```

## Running without Docker

You can simply run this project as a main application by running `Server.scala` file as a main runnable application if
you wish to run without using docker.

## Testing JSON string inputs

To test whether a JSON string input was valid or not not, you can send a POST request to the running API at:

```
http://localhost:9000/api/parser
```

You can change the application host and port via `application.conf`.