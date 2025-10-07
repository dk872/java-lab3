package org.example;

import java.time.LocalDate;

public class StudentCard extends TripsCard {
    public StudentCard(String id, String validityTerm, int trips, LocalDate validFrom) {
        super(id, "student", validityTerm, trips, validFrom);
    }
}
