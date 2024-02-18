# HDB Insight backend application

## Overview

This application is using gradle as build tool and spring boot as framework. It is using the following dependencies:
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- spring-boot-starter-web
- spring-session-core
## Related repositories
- [Frontend Next Js](https://github.com/chenyu-01/ad-frontend)
- [Java Spring Boot](https://github.com/chenyu-01/ad-backend)
- [Android](https://github.com/preethivenkat5/ad_android)
- [ML Model](https://github.com/CsCesium/DNN-NCF-Transformer)
## How to run

### Prerequisite
Set up a mysql database with the following properties:
- database name: hdbinsight
- username: ${MYSQL_USER}
- password: ${MYSQL_PASSWORD}

### Set up environment variables in the `application.properties`
- MYSQL_USER - the username of the mysql database
- MYSQL_PASSWORD - the password of the mysql database
- EXTERNAL - the external storage for serving images in java spring boot
> it should be empty and do not store image files because in commandlineRunner, all images in that folder will be deleted.

### If you are using Mac or Linux
in main/java/sa57/team01/adproject/config/WebConfig.java, change the following line
```
registry.addResourceHandler("/uploads/**").addResourceLocations(resourceLocation);
```
replace `resourceLocattion` with `macLocation`


## REST API Endpoints

- `/api/property/salelist` - GET method, return a list of properties for sale
- `/api/property/rentlist` - GET method, return a list of properties for rent
- `/api/property/detail/{id}` - GET, return the detail of a property
- `/api/property/list/search` - POST, search properties, return a list of properties
- `/api/user/details/{id}` - GET, return the detail of a user
- `/api/user/login` - POST, login a user
- `/api/user/logout` - POST, logout a user
- `/api/user/register` - POST, register a user
- `/api/user/update` - POST, update a user
- `/api/appointment/create` - POST, create an appointment
- `/api/appointment/cancel/{appointmentId}`- DELETE , cancelling an appointment
- `/api/appointment/getAppointments`- GET , returns appointments for buyer and rental seeker
- `/api/appointment/getAppointmentsForOwner`- GET, returns appointments for owner
- `/api/appointment/confirm/{appointmentId}`-GET, confirms appointment
- Properties
- `/api/property/list`- GET method, return a list of properties 
- `/api/property/{id}`- GET, return the detail of a property
- `/api/property/list/search/` - POST, search properties, return a list of properties 
- `/api/property/details/{id}` - POST, return the detail of a property
- `/api/property/upload/{propertyId}` - POST, upload images of a property
- `/api/property/recommend/{id}` - GET, recommend properties
- `/api/property/predict/{id}` - GET, make prediction
- `/api/property/dashboard/{id}` - GET, display property dashboard for users
- `/api/property/fetchImg/{propertyId}` - GET, fetch images of a property
- `/api/property/update/{id}` - POST, update a property
- `/api/property/fetchImg/{propertyId}` - GET, fetch images of a property
- `/api/usersetting/getProfile` - GET, get user profile

## Set up environment variables
- MYSQL_USER - the username of the mysql database
- MYSQL_PASSWORD - the password of the mysql database
- EXTERNAL - the external storage for the images

## Set up the database
- Create a database with the name `hdbinsight`

