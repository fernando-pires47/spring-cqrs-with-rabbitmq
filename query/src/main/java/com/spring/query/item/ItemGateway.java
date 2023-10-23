package com.spring.query.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemGateway extends JpaRepository<Item, Long> {
}
