package com.sguProject.backendExchange.models;

public class Coin {
    public enum CoinType {
        None, BTC, ETH, BNB
    }

    private CoinType type;
    private double balance;

    public Coin(CoinType type, double balance) {
        setType(type);
        setBalance(balance);
    }

    public Coin() {
        this(CoinType.BTC, 0);
    }

    public CoinType getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() { return type.toString(); }

    public void setType(CoinType type) {
        if (type == CoinType.None) {
            throw new IllegalArgumentException("Coin type cannot be CoinType.None");
        }

        this.type = type;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("balance cannot be less than 0");
        }

        this.balance = balance;
    }

    public void replenishment(double numberOfCoins) {
        if (0 > numberOfCoins) {
            throw new IllegalArgumentException("numberOfCoins cannot be less or equal to zero");
        }

        balance += numberOfCoins;
    }

    public void withdraw(double numberOfCoins) {
        if (numberOfCoins > balance) {
            throw new IllegalArgumentException("numberOfCoins cannot be more than balance");
        }

        balance -= numberOfCoins;
    }
}
