package com.hsjskj.quwen.ui.user.activity;

/**
 * @author : sen
 * time          : 2021年01月05日 10:49
 * description   : quwen_live
 */
public class MessageActivity {

    public static final int TYPE_RECEVED = 0;
    // 发出一条消息
    public static final int TYPE_SENT = 1;

    //消息内容
    private String content;
    // 消息类型
    private int type;

    public MessageActivity(String content,int type){
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
