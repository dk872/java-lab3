package org.example;

import java.time.LocalDate;

public class PupilCard extends TripsCard {
    public PupilCard(String id, String validityTerm, int trips, LocalDate validFrom) {
        super(id, "pupil", validityTerm, trips, validFrom);
    }
}
