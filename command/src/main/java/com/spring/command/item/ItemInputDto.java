package com.spring.command.item;

import java.math.BigDecimal;

public record ItemInputDto(String name, BigDecimal value, String type) {
}
