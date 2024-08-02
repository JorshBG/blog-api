# RESTFul API for Blog

"Practical purposes"

## Requirements
- Java 18+
- Postgresql 15+
- IDE

## Instructions
- Clone the repo
- Build with Gradle
- Check `aplication.properties` file and make changes if it is necessary
- Run

## Danger zone
### Faker
There are a service un-activated to load faker data in the database, these service deletes all the data in your database. Take care if you decide active the service and only use it with a dev database or change the `application.properties` to use another `datasource`.

### Testing
All testing classes delete all data in the database, take care and use it with another datasource.

### Profiles
With Spring, you can create many `application.properties` files and give a name to create a profile and use it for a specific purpose like develop mode. 