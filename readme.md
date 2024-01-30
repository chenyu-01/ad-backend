# HDB Insight backend application

## Overview

This application is using gradle as build tool and spring boot as framework. It is using the following dependencies:
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- spring-boot-starter-web
- spring-session-core

## How to run

### Prerequisite
Set up a mysql database with the following properties:
- database name: hdbinsight
- username: ${MYSQL_USER}
- password: ${MYSQL_PASSWORD}

### Set up environment variables
![how to set up environment variables](./docs/how-to-set-environment-var.png)
## Todo List

- [ ] finish endpoints list
- [ ] design the page

## REST API Endpoints

- `/api/property/salelist` - GET method, return a list of properties for sale
- `/api/property/rentlist` - GET method, return a list of properties for rent
- `/api/property/detail/{id}` - GET, return the detail of a property
- `/api/user/details/{id}` - GET, return the detail of a user
- `/api/user/login` - POST, login a user
- `/api/user/logout` - POST, logout a user
- `/api/user/register` - POST, register a user
- `/api/user/update` - POST, update a user
- `/api/appointment/create` - POST, create an appointment
- Properties
- `/api/property/list`- GET method, return a list of properties 
- `/api/property/{id}`-GET, return the detail of a property
- `/api/property/listbydate`-GET, return the detail of a property by date
- `/api/property/listbyprice`-GET, return the detail of a property by price
- `/api/property/listbytown`-GET, return the detail of a property by town
- `/api/property/listbystreet`-GET, return the detail of a property by street
- `/api/property/listbystorey`-GET, return the detail of a property by storey
- `/api/property/listbyfloorarea`-GET, return the detail of a property by floorarea

