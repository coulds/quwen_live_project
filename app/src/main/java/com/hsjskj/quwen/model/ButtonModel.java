package com.hsjskj.quwen.model;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class ButtonModel {
    private boolean isButton;
    private String tvContent;

    public boolean isButton() {
        return isButton;
    }

    public void setButton(boolean button) {
        isButton = button;
    }

    public String getTvContent() {
        return tvContent;
    }

    public void setTvContent(String tvContent) {
        this.tvContent = tvContent;
    }

    public ButtonModel(boolean isButton) {
        this.isButton = isButton;
    }
}
