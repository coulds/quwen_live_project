package com.hsjskj.quwen.ui.user.object;

public class Evaluation {

    private String id;
    private int userHeader;
    private String userName;
    private String time;
    private String evaluationMsg;


    public Evaluation(String id, int userHeader, String userName, String time, String evaluationMsg) {
        this.id = id;
        this.userHeader = userHeader;
        this.userName = userName;
        this.time = time;
        this.evaluationMsg = evaluationMsg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(int userHeader) {
        this.userHeader = userHeader;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEvaluationMsg() {
        return evaluationMsg;
    }

    public void setEvaluationMsg(String evaluationMsg) {
        this.evaluationMsg = evaluationMsg;
    }
}
