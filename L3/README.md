# Laboratory 2

Implementation about a facade server that consumes an API REST about movie's data, a client and a test that verifies the concurrency of server. With the particularity that the index.html and other files will be given by the server.

## Arquitecture

This project has the next components or layers:

`Server.java` Starts the server and creates threads for each request, then it waits the information of Service Layer, which uses the persistence layer for obtain: The movie's data and the files of web page.

Additionally, there is a domain layer, which contains a cache implementation that is used for an implementation about IMovieDAO.

## Instalation

You must to have a SDK and maven. Then you have to download this folder, go to /server and run next commands:

```cmd
mvn package -Dskiptests
```

## Execution

1. Run the server. For this run the next command into server folder

```cmd
mvn compile exec:java
```

2. Open `http://localhost:35000/index.html`

3. Open `http://localhost:35000/testjp.jpg`


## Running Tests

For run the unitaries tests:

1. Run the server with the previuos command
2. Run `mvn test`

## Author

Juan Pablo Fonseca


