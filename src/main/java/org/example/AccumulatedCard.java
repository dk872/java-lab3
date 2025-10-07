package org.example;

class AccumulatedCard extends Card {
    private double balance;
    private final double tripCost;

    public AccumulatedCard(String id, double initialBalance, double tripCost) {
        super(id, "regular", "unlimited", 0, true, null);
        this.balance = initialBalance;
        this.tripCost = tripCost;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean canPass() {
        return balance >= tripCost;
    }

    @Override
    public void useTrip() {
        if (!canPass()) return;
        balance -= tripCost;
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}
