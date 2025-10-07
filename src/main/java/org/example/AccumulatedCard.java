package org.example;

class AccumulatedCard extends Card {
    private double balance;
    private final double tripCost;

    public AccumulatedCard(String id, double initialBalance, double tripCost) {
        super(id, "regular");

        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
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

    @Override
    public boolean addTrips(int count) {
        System.out.println("Trip top-up is not available for prepaid cards!");
        return false;
    }

    @Override
    public int getCountOfTrips() { return 0; }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        balance += amount;
    }
}
