# Laboratory 1

Implementation about a facade server that consumes an API REST about movie's data, a client and a test that verifies the concurrency of server.

## Instalation

You must to have a SDK and maven. Then you have to download this folder, go to /server and run next commands:

```cmd
mvn package -Dskiptests
```

## Execution

1. Run the server. For this run the next command into server folder

```cmd
mvn exec:java
```

2. Open client/index.html and use it.


## Running Tests

For run the unitaries tests:

1. Run the server with the previuos command
2. Run `mvn test`

## Author

Juan Pablo Fonseca


