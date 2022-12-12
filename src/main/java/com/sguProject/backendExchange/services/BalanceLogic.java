package com.sguProject.backendExchange.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sguProject.backendExchange.models.Coin;
import com.sguProject.backendExchange.models.Coin.CoinType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BalanceLogic {
    BalanceDAO dao;
    public BalanceLogic(BalanceDAO dao) {
        this.dao = dao;
    }

    public void changeCoins(CoinType buyable, CoinType salable, double numberPurchased) {
        double requiredSalableCoins = getRequiredSalableCoinBalance(buyable, salable, numberPurchased);

        Coin salableCoin = dao.getCoin(salable);
        double currentSalableCoins = salableCoin.getBalance();

        if (requiredSalableCoins > currentSalableCoins) {
            throw new IllegalArgumentException("numberPurchased less than need salable coins for success trade");
        }

        Coin buyableCoin = dao.getCoin(buyable);
        salableCoin.withdraw(requiredSalableCoins);
        buyableCoin.replenishment(numberPurchased);
    }

    public double getRequiredSalableCoinBalance(CoinType buyable, CoinType salable, double numberPurchased) {
        return getCourse(buyable, salable) * numberPurchased;
    }

    public double getCourse(CoinType buyable, CoinType salable) {
        try {
            URL url = new URL("http://localhost:3000/prices?symbol="+ buyable.name() + salable.name() + "&time=5m&count=1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (var in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())))
            {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0.00765;
    }
}
