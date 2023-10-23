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

### Concepts
* EDA(Event Driven Architecture)
* Message Broker
* CQRS(Command and Query Responsibility Segregation)
* Eventual Consistency

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

To see it first access RabbitMQ Manager and realize login:

Access:

```bash
http://localhost:8090
```

User and Password default is it, to change access file "application.properties" in command service:

```bash
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin
```

![](https://github.com/fernando-pires47/spring-cqrs-with-rabbitmq/blob/main/images/login-rabbitmq.png)

To see your clients connected:

![](https://github.com/fernando-pires47/spring-cqrs-with-rabbitmq/blob/main/images/see-your-client-connected-rabbitmq.png)

To see your exchanges, the "direct-exchange" is connected with queues used in this application:

![](https://github.com/fernando-pires47/spring-cqrs-with-rabbitmq/blob/main/images/see-your-exchanges-rabbitmq.png)

To see your queues:

![](https://github.com/fernando-pires47/spring-cqrs-with-rabbitmq/blob/main/images/see-your-queues-rabbitmq.png)



### Operate the services

Right flow: It will create new item in command database and send message to queue service, next it, the query service will be listening and pick up the message to save in your database.

To create new item using CURL:

```bash
curl -X POST http://localhost:8080/item/create -H 'Content-Type: application/json' -d '{"name": "teste","value": 20, "type": "P"}'
```

To see itens saves:

```bash
curl http://localhost:8070/item/list
```

To see specific item save:

```bash
curl http://localhost:8070/item/get/{id}
```

Incorrect flow: 

Access RabbitMQ Manager, next it, access queue "queue.item" and in publish message send one incorrect message to see behavior.

![](https://github.com/fernando-pires47/spring-cqrs-with-rabbitmq/blob/main/images/publish-message-rabbitmq.png)

After send message, listening operation throws an error after 3 attempts and send message to dead letter queue "queue.item.dl".

![](https://github.com/fernando-pires47/spring-cqrs-with-rabbitmq/blob/main/images/published-message-in-item-queue.png)
![](https://github.com/fernando-pires47/spring-cqrs-with-rabbitmq/blob/main/images/published-message-in-item-dl-queue.png.png)

After arriving in dl queue, there is a waiting time to send to unsended queue "queue.item.us", she is a durable queue with message retention

![](https://github.com/fernando-pires47/spring-cqrs-with-rabbitmq/blob/main/images/published-message-in-item-us-queue.png)

To see it in spring logs, follow query container.

```bash
sudo docker-compose logs cqrs-query -f
```


## License

This application is available under the
[MIT license](https://opensource.org/licenses/MIT).

