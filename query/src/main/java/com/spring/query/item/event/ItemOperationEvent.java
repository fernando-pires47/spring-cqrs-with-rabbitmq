package com.spring.query.item.event;

public enum ItemOperationEvent {
    CREATE("CREATE"),
    UPDATE("UPDATE")
    ;

    private final String value;
    ItemOperationEvent(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
