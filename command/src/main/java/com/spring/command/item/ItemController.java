package com.spring.command.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.command.item.event.ItemEvent;
import com.spring.command.item.event.ItemOperationEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemGateway itemGateway;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ItemInputDto input) throws JsonProcessingException {
        Item ret = this.itemGateway.save(new Item(null, input.name(), input.value(), ItemType.valueOf(input.type())));
        this.rabbitTemplate.convertAndSend("queue.item", new ObjectMapper().writeValueAsString(new ItemEvent(ret.getId(), ret.getName(), ret.getValue(), ret.getType(), ItemOperationEvent.CREATE)));
        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }
}
