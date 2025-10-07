package org.example;

import java.time.LocalDate;

class TripsCard extends Card {
    public TripsCard(String id, String type, String validityTerm, int trips, LocalDate validFrom) {
        super(id, type, validityTerm, trips, false, validFrom);
    }

    @Override
    public boolean isValid() {
        LocalDate now = LocalDate.now();
        LocalDate expiryDate;

        switch (validityTerm) {
            case "month":
                expiryDate = validFrom.plusMonths(1);
                break;
            case "10 days":
                expiryDate = validFrom.plusDays(10);
                break;
            default:
                return false;
        }

        return !now.isAfter(expiryDate);
    }

    @Override
    public boolean canPass() {
        if (!isValid()) return false;

        return trips > 0;
    }

    @Override
    public void useTrip() {
        if (!canPass()) return;
        trips--;
    }
}
