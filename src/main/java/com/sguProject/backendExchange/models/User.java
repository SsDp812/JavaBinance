package com.sguProject.backendExchange.models;

public class User {
    private int id;
    private String login;
    private String password;
    private String id_account;

    public User(int id, String login, String password, String id_account) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.id_account = id_account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId_account() {
        return id_account;
    }

    public void setId_account(String id_account) {
        this.id_account = id_account;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", id_account='" + id_account + '\'' +
                '}';
    }
}
