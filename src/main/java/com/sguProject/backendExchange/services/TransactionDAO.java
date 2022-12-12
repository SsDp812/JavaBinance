package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Coin;
import com.sguProject.backendExchange.models.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private static String TABLENAME = "transactions";

    public Connection getConnection() {
        return DataBaseConnector.getConnection();
    }

    public List<Transaction> getAllTransactions() {
        var transactions = new ArrayList<Transaction>();

        try {
            Statement statement = getConnection().createStatement();
            String SQL = "SELECT * FROM " + TABLENAME;
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Coin.CoinType typeBoughtCoin = Coin.CoinType.valueOf( resultSet.getString("typeBoughtCoin") );
                double balanceBoughtCoin = resultSet.getDouble("balanceBoughtCoin");
                Coin boughtCoin = new Coin(typeBoughtCoin, balanceBoughtCoin);

                Coin.CoinType typeSalableCoin = Coin.CoinType.valueOf( resultSet.getString("typeSalableCoin") );
                double balanceSalableCoin = resultSet.getDouble("balanceSalableCoin");
                Coin salableCoin = new Coin(typeSalableCoin, balanceSalableCoin);

                double courseDeal = resultSet.getDouble("courseDeal");

                Transaction coin = new Transaction(boughtCoin, salableCoin, courseDeal);
                transactions.add(coin);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }


    public void save(Transaction transaction) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO " + TABLENAME + " VALUES(?, ?, ?, ?, ?)");
            Coin boughtCoin = transaction.getBought();
            preparedStatement.setString(1, boughtCoin.getType().name());
            preparedStatement.setDouble(2, boughtCoin.getBalance());

            Coin salableCoin = transaction.getSalable();
            preparedStatement.setString(3, salableCoin.getType().name());
            preparedStatement.setDouble(4, salableCoin.getBalance());

            preparedStatement.setDouble(5, transaction.getCourse());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
