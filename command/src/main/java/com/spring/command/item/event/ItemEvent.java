package com.spring.command.item.event;

import com.spring.command.item.ItemType;

import java.math.BigDecimal;

public record ItemEvent(Long id, String name, BigDecimal value, ItemType type, ItemOperationEvent operation){
}
