#Pokedex
A REST API that returns Pokemon information.

The API has two main endpoints:
1. Return basic Pokemon information
2. Return basic Pokemon information but with a 'fun' translation of the Pokemon description.

#Run Application
1. Create docker image
```dtd
./gradlew bootBuildImage --imageName=pokedex
```
1. Run docker container
```dtd
 docker run -p 8080:8080 -t pokedex
```
1. Pokedex application is up and running! Try hitting the following URL to test.
```dtd
http://localhost:8080/pokemon/translated/pikachu
```

### Pending requisites for production readiness
* Add logs to log all requests and responses
* Add error logs
* Handle request timeouts for all API clients