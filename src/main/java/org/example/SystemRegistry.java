package org.example;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

class SystemRegistry {
    private final Map<String, Card> cards = new HashMap<>();
    private int idCounter = 1;

    public Card issueTripsCard(String type, String validityTerm, int trips, LocalDate validFrom) {
        String id = "C" + (idCounter++);

        Card card = switch (type) {
            case "regular" -> new RegularCard(id, validityTerm, trips, validFrom);
            case "student" -> new StudentCard(id, validityTerm, trips, validFrom);
            case "pupil" -> new PupilCard(id, validityTerm, trips, validFrom);
            default -> throw new IllegalArgumentException("Unknown card type: " + type);
        };

        cards.put(id, card);
        return card;
    }

    public AccumulatedCard issueAccumulatedCard(double initialBalance) {
        String id = "C" + (idCounter++);
        AccumulatedCard card = new AccumulatedCard(id, initialBalance);
        cards.put(id, card);

        return card;
    }

    public Card getCard(String id) {
        return cards.get(id);
    }
}
