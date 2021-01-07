package com.hsjskj.quwen.ui.my.object;

import androidx.annotation.NonNull;

//银行卡
public class BankCard {
    private String id;
    private String bankCardName;
    private String bankCardNumber;
    private String bankAccount;
    private String remark;

    public BankCard(String id, String bankCardName, String bankCardNumber, String bankAccount) {
        this.id = id;
        this.bankCardName = bankCardName;
        this.bankCardNumber = bankCardNumber;
        this.bankAccount = bankAccount;
    }

    public BankCard(String id, String bankCardName, String bankCardNumber, String bankAccount, String remark) {
        this.id = id;
        this.bankCardName = bankCardName;
        this.bankCardNumber = bankCardNumber;
        this.bankAccount = bankAccount;
        this.remark = remark;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
