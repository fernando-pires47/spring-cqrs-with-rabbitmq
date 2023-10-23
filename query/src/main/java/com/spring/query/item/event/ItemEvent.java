package com.spring.query.item.event;

import com.spring.query.item.Item;
import com.spring.query.item.ItemType;

import java.math.BigDecimal;

public record ItemEvent(Long id, String name, BigDecimal value, ItemType type, ItemOperationEvent operation) {

    public Item getItem(){
        return new Item(id,name,value,type);
    }
}
