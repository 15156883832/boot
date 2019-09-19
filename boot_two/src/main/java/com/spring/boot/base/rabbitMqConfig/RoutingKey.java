package com.spring.boot.base.rabbitMqConfig;

public enum RoutingKey {

    ROUTER_A("router_a",""),ROUTER_B("router_b",""),ROUTER_C("router_c",""),ROUTER_D("router_d","");

    private String name;
    private String desc;
    RoutingKey(String name,String desc) {
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
