# Pokedex
A REST API that returns Pokemon information.

The API has two main endpoints:
1. Return basic Pokemon information
2. Return basic Pokemon information but with a 'fun' translation of the Pokemon description.

# Tech Stack
* Java
* Spring Boot

# Run Application (with Docker)
1. Create docker image
```dtd
./gradlew bootBuildImage --imageName=pokedex
```
2. Run docker container
```dtd
 docker run -p 8080:8080 -t pokedex
```
3. Pokedex application is up and running!

# Run Application (without Docker)
1. Start server locally
```dtd
./gradlew bootRun
```
2. Pokedex application is up and running!

# Test Application
Try hitting the following URLs:

1. To retrieve pokemon details:
```dtd
http://localhost:8080/pokemon/{pokemon_name}
```

3. To retrieve *translated* pokemon details:
```dtd
http://localhost:8080/pokemon/translated/{pokemon_name}
```

### Pending requisites for production readiness
* Add logs to log all requests and responses
* Add error logs
* Handle request timeouts for all API clients
* Handle all possible downstream api error codes in `GlobalExceptionHandler`