package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Coin;
import com.sguProject.backendExchange.models.Coin.CoinType;
import com.sguProject.backendExchange.models.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BalanceLogic {
    private BalanceDAO dalanceDao;
    private TransactionDAO transactionDAO;
    public BalanceLogic(BalanceDAO dalanceDao, TransactionDAO transactionDAO) {
        this.dalanceDao = dalanceDao;
        this.transactionDAO = transactionDAO;
    }

    public void changeCoins(CoinType buyable, CoinType salable, double numberPurchased) {
        double course = getCourse(buyable, salable);
        double requiredSalableCoins = course * numberPurchased;

        Coin salableCoin = dalanceDao.getCoin(salable);
        double currentSalableCoins = salableCoin.getBalance();

        if (requiredSalableCoins > currentSalableCoins) {
            throw new IllegalArgumentException("numberPurchased less than need salable coins for success trade");
        }

        Coin buyableCoin = dalanceDao.getCoin(buyable);
        salableCoin.withdraw(requiredSalableCoins);
        buyableCoin.replenishment(numberPurchased);
        dalanceDao.save(salableCoin);
        dalanceDao.save(buyableCoin);

        salableCoin.setBalance(requiredSalableCoins);
        buyableCoin.setBalance(numberPurchased);
        var transaction = new Transaction(buyableCoin, salableCoin, course);
        transactionDAO.save(transaction);
    }

    public double getCourse(CoinType buyable, CoinType salable) {
        double course = 0;
        try {
            URL url = new URL("http://localhost:3000/prices?symbol="+ buyable.name() + salable.name() + "&time=5m&count=1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (var in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())))
            {
                String jsonString = in.readLine();
                JSONArray array = (JSONArray) new JSONParser().parse(jsonString);
                JSONObject jo = (JSONObject) array.get(0);
                course = (Double) jo.get("open");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return course;
    }
}
