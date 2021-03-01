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

2. To bring up the parser API within a docker container, run the below command from the project root directory:

```
docker-compose up -d
```

3. You can stop the running container using the following command from the project root directory:

```
docker-compose rm --stop
```

4. Finally, if you want to remove all docker images:

```
docker system prune -a -f --volumes
```

## Running without Docker

You can simply run this project as a main application by running `Server.scala` file as a main runnable application if
you wish to run without using docker.

## Testing JSON string inputs

To test whether a JSON string input was valid or not, you can send a POST request to the running API at:

```
http://localhost:9000/api/parser
```

You can change the application host and port via resources file `application.conf`.