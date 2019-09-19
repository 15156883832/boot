package com.spring.boot.base.rabbitMqConfig;

public enum ExchangeType {

    DIRECT("amq.direct","直接交换器"),FANOUT("amq.fanout","扇形交换器"),TOPIC("amq.topic","主题交换器"),HEADERS("amq.headers","头交换器");

    private String name;
    private String desc;
    ExchangeType(String name,String desc) {
        this.name=name;
        this.desc=desc;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
