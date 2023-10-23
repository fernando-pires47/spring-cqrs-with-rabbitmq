package com.spring.query.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.query.item.event.ItemEvent;
import com.spring.query.item.event.ItemOperationEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemGateway itemGateway;

    @Autowired()
    private RabbitTemplate rabbitTemplate;


    @RabbitListener(queues = "queue.item")
    public void itemOperation(String event, @Headers Map<?,?> headers) throws Exception {
        if((Boolean)headers.get("amqp_redelivered")){
            return;
        }
        System.out.println("*** Item Queue event: " + event + " ***");
        ItemEvent itemEvent = new ObjectMapper().readValue(event, ItemEvent.class);;
        System.out.println(itemEvent);
        if(itemEvent.operation().getValue().equals(ItemOperationEvent.CREATE.getValue())){
            this.itemGateway.save(itemEvent.getItem());
        }
    }

    @RabbitListener(queues = "queue.item.dl")
    public void itemDeadLetter(String event, @Headers Map<?,?> headers) throws Exception {
        if((Boolean)headers.get("amqp_redelivered")){
            return;
        }
        System.out.println("*** Dead Letter Queue event: " + event + " ***");
        Thread.sleep(8000);
        System.out.println("*** Send to Unsended Queue ***");

        this.rabbitTemplate.convertAndSend("queue.item.us", event);
    }

    @GetMapping("/list")
    public List<Item> getList() {
        return this.itemGateway.findAll();
    }

    @GetMapping("/get/{id}")
    public Item get(@PathVariable Long id) {
        Optional<Item> optionalItem = this.itemGateway.findById(id);
       return optionalItem.orElse(null);
    }
}
