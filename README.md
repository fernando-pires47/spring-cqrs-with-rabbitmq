# Spring CQRS with RabbitMQ
Project example using spring boot and concepts like CQRS and tools like RabbitMQ

### Dependencies
* Docker
* Docker Compose
* Java 17

### Services to run
* Query Service
* Command Service
* RabbitMQ Service
* RabbitMQ Manager

### To Run using Docker Compose

Execute in root directory:

```bash
sudo docker-compose up -d 
```

### See behavior using RabbitMQ Manager in UI

Access:

```bash
http://localhost:8090
```

To realize login use the configuration of file application.properties in command project.

Default:

```bash
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin
```

## License

This application is available under the
[MIT license](https://opensource.org/licenses/MIT).

