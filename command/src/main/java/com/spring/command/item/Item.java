package com.spring.command.item;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table( name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "valueTotal", nullable = false)
    private BigDecimal value;

    @Column(name = "itemType", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType type;

    private Item(){}
    public Item(Long id, String name, BigDecimal value, ItemType type){
        this.id = id;
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public BigDecimal getValue() {
        return value;
    }

    public ItemType getType() {
        return type;
    }

}
