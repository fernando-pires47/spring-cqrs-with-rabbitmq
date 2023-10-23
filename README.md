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

To see services running:

```bash
sudo docker-compose ps
```

### After running

After run the command service, 3 queues are created and 1 exchange.

Queues:

* queue.item => Principal queue where the events are saved (not durable). 
* queue.item.dl => Queue where messages expired of "queue.item" will come (not durable).
* queue.item.us => Queue that receives messages expired from "queue.item.dl", also, listening behavior of "queue.item.dl" is sending message to it.

To see it:

### Operate the services

To create new item using CURL:

```bash
curl -X POST http://localhost:8080/item/create -H 'Content-Type: application/json' -d '{"name": "teste","value": 20, "type": "P"}'
```

It will create new item in command database and send message to queue service, next, the query service will be listening and pick up the message to save in your database.

To see itens saves:

```bash
curl http://localhost:8070/item/list
```

To see specific item save:

```bash
curl http://localhost:8070/item/get/{id}
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

