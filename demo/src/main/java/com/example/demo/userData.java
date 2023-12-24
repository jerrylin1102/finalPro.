package com.example.demo;

public class userData {
    private static userData instance;

    private String name;
    private int pw;

    private String account;

    private userData(){}

    public static synchronized  userData getInstance(){
        if(instance == null){
            instance = new userData();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPw() {
        return pw;
    }

    public void setPw(int pw) {
        this.pw = pw;
    }

    public void  setaccount(String account){
        this.account = account;
    }

}
