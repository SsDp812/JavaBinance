package com.sguProject.backendExchange.models;

import java.util.HashMap;

public class Account {
    private int id;
    private int user_id;
    private HashMap<String, Double> balance;

    public Account(int id, int user_id) {
        this.id = id;
        this.user_id = user_id;
        balance = new HashMap<>();
    }
}
