package com.hokaslibs.utils.ease_guide.bean;


/**
 * @author yuyh.
 * @date 2016/12/24.
 */
public class Message {

    public String message;

    public int textSize = -1;

    public Message(String message) {
        this.message = message;
    }

    public Message(String message, int textSize) {
        this.message = message;
        this.textSize = textSize;
    }
}
