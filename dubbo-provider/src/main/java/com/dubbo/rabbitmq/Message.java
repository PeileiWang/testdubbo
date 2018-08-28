package com.dubbo.rabbitmq;

import java.io.Serializable;

public class Message implements Serializable {

    private int id;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
