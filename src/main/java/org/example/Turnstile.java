package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

class Turnstile {
    private int passesAllowed = 0;
    private int passesDenied = 0;
    private final Map<String, Integer> passesByType = new HashMap<>();
    private final Map<String, Integer> deniedByType = new HashMap<>();
    private final double tripCost;

    public Turnstile(double tripCost) {
        if (tripCost < 0) {
            throw new IllegalArgumentException("Trip cost cannot be negative.");
        }

        this.tripCost = tripCost;
    }

    public double getTripCost() {
        return tripCost;
    }

    public int getPassCount() {
        return passesAllowed;
    }

    public int getDenialCount() {
        return passesDenied;
    }

    public Map<String, Integer> getPassesByType() {
        return Collections.unmodifiableMap(passesByType);
    }

    public Map<String, Integer> getDeniedByType() {
        return Collections.unmodifiableMap(deniedByType);
    }

    public boolean payTrip(Card card) {
        if (card == null) {
            passesDenied++;
            return false;
        }

        if (card.canPass()) {
            card.useTrip();
            passesAllowed++;
            passesByType.put(card.getType(), passesByType.getOrDefault(card.getType(), 0) + 1);
            return true;
        } else {
            passesDenied++;
            deniedByType.put(card.getType(), deniedByType.getOrDefault(card.getType(), 0) + 1);
            return false;
        }
    }

    public void printSummary() {
        System.out.println("\nTotal permissions: " + passesAllowed);
        System.out.println("Total rejections: " + passesDenied);
    }

    public void printByType() {
        System.out.println("\nPermissions by type:");
        for (String type : passesByType.keySet()) {
            System.out.println(type + ": " + passesByType.get(type));
        }

        System.out.println("Rejections by type:");
        for (String type : deniedByType.keySet()) {
            System.out.println(type + ": " + deniedByType.get(type));
        }
    }
}
