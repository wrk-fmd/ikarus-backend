# Backend for Ikarus App

The Ikarus Backend stores attendance data of staff for events.
In combination with the Ikarus App and the web interface of the Backend,
it should be possible to easily manage and track the status of the staff.

## Interface Documentation

[The documentation of the interface can be found at `doc/`](doc/index.md).

## Requirements

* Server
    * Java Runtime Environment (version 1.8 required)
    * Apache Tomcat (tested with version 9.0.17)
    * PostgreSQL Database (tested with version 9.6.12)
* Client
    * Web Browser (tested with Google Chrome)

## Build the project

Run `mvn clean package` to build the Tomcat package.

## Run the project in an IDE

To start the application in an IDE (e.g. IntelliJ) you can use the profile `local-tomcat`
which starts a Tomcat server on it's own.

## Deployment

To deploy the Ikarus Backend be sure to mind the following steps:

* Install and configure a local Tomcat server
* Install and setup a PostgreSQL Database server
* Change the settings of the `application.properties` file to match your needs
* [Build the project](#build-the-project)
* Deploy the `.war` or `.jar` file