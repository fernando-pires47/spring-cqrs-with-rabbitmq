package com.spring.command.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemGateway extends JpaRepository<Item, Long> {
}
