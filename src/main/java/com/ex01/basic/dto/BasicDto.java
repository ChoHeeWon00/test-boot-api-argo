package com.ex01.basic.dto;

public class BasicDto {
    private String username;
    private String password;
    private int num;
    public BasicDto(String username, String password, int num){
        this.username = username;
        this.password = password;
        this.num = num;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}

