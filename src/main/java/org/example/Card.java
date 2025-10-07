package org.example;

abstract class Card {
    protected String id;
    protected String type;

    public Card(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() { return id; }
    public String getType() { return type; }

    public abstract boolean isValid();
    public abstract boolean canPass();
    public abstract void useTrip();
    public abstract boolean addTrips(int count);
    public abstract int getCountOfTrips();
}
