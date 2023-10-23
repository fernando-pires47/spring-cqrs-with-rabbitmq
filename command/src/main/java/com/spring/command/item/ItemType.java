package com.spring.command.item;

public enum ItemType {
    P("Product"),
    S("Service")
    ;

    private final String name;
    ItemType(String name) {
        this.name = name;
    }

    public String getValue(){
        return this.name;
    }
}
