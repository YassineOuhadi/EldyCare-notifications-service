# Eldycare Services

## Components
- [API Gateway](#api-gateway)
- [Discovery Service](#discovery-service)
- [Authentication Service](#authentication-service)
- [Relationship Service](#relationship-service)
- [Notification Service](#notification-service)
- [Reminder Service](#reminder-service)

---

# API Gateway

---

# Discovery Service

---

# Authentication Service

---

# Relationship Service

---

# Notification Service

> **Push Notifications Service using Spring, RabbitMQ as Message Broker and WebSocket.**


## Table of Contents

* [Spring Boot RabbitMQ WebSocket](#spring-boot-rabbitmq-websocket)
  * [Running Instructions](#running-instructions)
    * [Installing RabbitMQ Server](#installing-rabbitmq-server)
    * [Run Application](#run-application)
  * [Rest API](#rest-api)
    * [Server API](#server-api)

## Running Instructions

### Installing RabbitMQ Server
- Download and install latest version of [RabbitMQServer](https://www.cherryservers.com/blog/how-to-install-and-start-using-rabbitmq-on-ubuntu-22-04)

- Create RabbitMQ Virtual Host

  	rabbitmqctl add_vhost eldycare_broker

- Assign User Permissions on a Virtual Host

  	sudo rabbitmqctl set_permissions -p eldycare_broker guest ".*" ".*" ".*"

### Run Application
- Start client and notification services
- Open Client web page [http://localhost:8083/notifications](http://localhost:8083/rabbit)
- Invoke Server API

## Rest API

### Send Notification

**Method**: `POST`

**URI**: `/notification/send`

**Description**: Send notification example

**Body**:

```json
{
  "elderId": "elder1",
  "alertMessage": "Hi, Mr. Abderkarem is not well, the system has detected a cardiac anomaly accompanied by a fall. We are sending this message to inform you.",
  "alertTime": "2023-12-12T12:30:00",
  "alertType": [
    "cardiac anomaly",
    "fall"
  ],
  "location": "https://www.google.com/maps?q=123.456,-789.012"
}
```
---

# Reminder Service
