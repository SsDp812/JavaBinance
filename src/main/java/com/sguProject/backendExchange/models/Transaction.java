package com.sguProject.backendExchange.models;

public class Transaction {
    private Coin bought;
    private Coin salable;

    private double course;

    public Transaction(Coin bought, Coin salable, double course) {
        this.bought = bought;
        this.salable = salable;
        this.course = course;
    }

    public Coin getBought() { return bought; }

    public Coin getSalable() { return salable; }

    public double getCourse() { return course; }

    @Override
    public String toString() {
        return "Bought: " + bought + "\nSalable: " + salable + "\nCourse: " + course;
    }
}
