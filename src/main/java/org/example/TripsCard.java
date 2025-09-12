package org.example;

import java.time.LocalDate;

class TripsCard extends Card {
    public TripsCard(String id, String type, String validityTerm, int trips, LocalDate validFrom) {
        super(id, type, validityTerm, trips, false, validFrom);
    }
}
