package org.example;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

class SystemRegistry {
    private final Map<String, Card> cards = new HashMap<>();
    private int idCounter = 1;

    public Card issueTripsCard(String type, String validityTerm, int trips, LocalDate validFrom) {
        String id = "C" + (idCounter++);
        Card card = new TripsCard(id, type, validityTerm, trips, validFrom);
        cards.put(id, card);
        return card;
    }

    public AccumulatedCard issueAccumulatedCard(double initialBalance, Turnstile turnstile) {
        String id = "C" + (idCounter++);
        AccumulatedCard card = new AccumulatedCard(id, initialBalance, turnstile.getTripCost());
        cards.put(id, card);
        return card;
    }

    public Card getCard(String id) {
        return cards.get(id);
    }
}
