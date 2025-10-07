package org.example;

import java.time.LocalDate;

public class RegularCard extends TripsCard {
    public RegularCard(String id, String validityTerm, int trips, LocalDate validFrom) {
        super(id, "regular", validityTerm, trips, validFrom);
    }
}
