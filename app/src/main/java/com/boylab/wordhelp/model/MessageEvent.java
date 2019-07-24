package com.boylab.wordhelp.model;

/**
 * Author pengle on 2019/7/24 16:40
 * Email  pengle609@163.com
 */
public class MessageEvent {

    private int id;
    private String msg;

    public MessageEvent() {
    }

    public MessageEvent(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
