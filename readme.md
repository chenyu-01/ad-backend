# HDB Insight backend application

## Overview

This application is using gradle as build tool and spring boot as framework. It is using the following dependencies:
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- spring-boot-starter-web
- spring-session-core

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
