# FOAAST (Fuck Off As A Service Throttler)

# What is FOOAST?

FOAAST is an application that serves as a Throttler for the original [FOAAS app](https://foaas.com/), it will block the request if an user makes more than a specific number of calls within a period of time.

# How to run it locally?

## Pre-requisites: `java`, `maven`

## Steps:
1. Clone this repo (or download the .zip of the project).
2. Inside the root folder run `mvn package`.
3. Now to run the application `java -jar target/FOAAST-0.0.1-SNAPSHOT.jar`.
4. You should now have FOAAST running locally.

# How does it works?

FOAAST exposes an endpoint to get an Awesome Message from FOAAS and it will limit the amount of requests done to this service (by the same user) within a period of time. Both of this values are configurable in the application.

Getting an awesome message will look like this:

```
curl --location --request GET 'localhost:8080/api/v1/messages/awesome/mike' \
--header 'userId: mike'
```

![image](https://user-images.githubusercontent.com/12452766/151676777-f5bc0899-5c87-4c64-839f-6432d860fa77.png)

But if we exceed the limit of requests (5 by default) for the same user within the period of time (10 seconds by default) we will get a `(429) Too Many Requests` indicating that we need to wait before the next request can be fulfilled.

![image](https://user-images.githubusercontent.com/12452766/151676846-1fbc58ec-08cd-46e5-8fdd-ec6bdcb35f0f.png)

# Solution

# Improvements
