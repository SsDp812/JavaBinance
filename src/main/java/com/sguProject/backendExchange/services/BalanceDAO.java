package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Coin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.sguProject.backendExchange.models.Coin.*;

public class BalanceDAO {
    private static String TABLENAME = "coins";

    public Connection getConnection() {
        return DataBaseConnector.getConnection();
    }

    public List<Coin> getAllCoins() {
        var coins = new ArrayList<Coin>();

        try {
            Statement statement = getConnection().createStatement();
            String SQL = "SELECT * FROM " + TABLENAME;
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                CoinType type = CoinType.valueOf(
                        resultSet.getString("type")
                );
                double balance = resultSet.getDouble("balance");

                Coin coin = new Coin(type, balance);
                coins.add(coin);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return coins;
    }

    public Coin getCoin(CoinType type) throws NoSuchElementException {
        checkCoinType(type);

        Coin coin = new Coin();

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM " + TABLENAME + " WHERE type=?");
            preparedStatement.setString(1, type.name());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                throw new NoSuchElementException("Coin with CoinType " + type + " not found");
            }

            coin.setType(CoinType.valueOf( resultSet.getString("type") ));
            coin.setBalance( resultSet.getDouble("balance") );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coin;
    }

    public void delete(CoinType type) {
        checkCoinType(type);

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM " + TABLENAME + " WHERE type=?");

            preparedStatement.setString(1, type.name());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Coin coin) {
        if (isExist(coin.getType())) {
            update(coin);
        }
        else {
            insert(coin);
        }
    }

    private void update(Coin coin) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE " + TABLENAME + " SET balance=? WHERE type=?");

            preparedStatement.setDouble(1, coin.getBalance());
            preparedStatement.setString(2, coin.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insert(Coin coin) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO " + TABLENAME + " VALUES(?, ?)");
            preparedStatement.setString(1, coin.getName());
            preparedStatement.setDouble(2, coin.getBalance());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkCoinType(CoinType type) throws IllegalArgumentException {
        if (type == CoinType.None) {
            throw new IllegalArgumentException("type cannot be CoinType.None");
        }
    }

    private boolean isExist(CoinType type) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM " + TABLENAME + " WHERE type=?");
            preparedStatement.setString(1, type.name());
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
