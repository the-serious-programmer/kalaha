# Improvements for the project

Suggested improvements for the project, ordered by priority.

1. Bug: Run Cucumber integration tests in the Maven build
2. Technical debt: Improve integration tests for the back-end.  
   The integration tests currently only call the back-end endpoints, but do not assert deeply if the logic works as
   expected.
3. Feature: improve front-end button lay-out by showing stones instead of numbers.
4. Feature: improve front-end score pit lay-out by showing it aside from the rest of the buttons, from top to bottom.
5. Feature: add Dockerfile to front-end and back-end, so that the whole application can be deployed with a single
   docker-compose file, and the front-end can be run in production mode.
6. Feature: add front-end state management.  
   Currently, the state of the application is emitted among the components, from children to parents and the other way
   around.  
   It would be cleaner to have one state store, for instance implemented with [ngrx](https://ngrx.io/), especially when
   the application is set to grow in the future.
7. Feature: add end-to-end tests.
8. Feature: add previously played game outcomes to the front-end.
9. Feature: add Swagger UI for the back-end API.

When deployment to for instance a cloud platform is added, a secure way of inserting credentials into the
application.properties and docker-compose.yml should be used.  
Do not commit secrets into git!
