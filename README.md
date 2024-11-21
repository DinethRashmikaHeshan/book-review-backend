First you have to run this backend application and this would expose to 8080 port
Username and Password of the db need to change application.properties according to your db credentials

In here  for authentication I am using spring boot security and json web tokens when log in it create bearer token for the user
and without authentication token only can access auth API s other all the apis need bearer token to hit the api

All the logics are written in the service class and 
Unit tests are written to the methods of the service class.