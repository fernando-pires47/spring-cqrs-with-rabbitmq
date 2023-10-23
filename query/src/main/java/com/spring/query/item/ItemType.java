package com.spring.query.item;

public enum ItemType {
    P("Product"),
    S("Service")
    ;

    private final String value;
    ItemType(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
