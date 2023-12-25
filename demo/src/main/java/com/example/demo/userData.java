package com.example.demo;

public class userData {
    private static userData instance;

    private String name;
    private String pw;
    private String account;
    private String email;

    private userData() {
        // 私有建構子，防止外部實例化
    }

    public static synchronized userData getInstance() {
        if (instance == null) {
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

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





}
