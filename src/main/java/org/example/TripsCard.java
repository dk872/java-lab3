package org.example;

import java.time.LocalDate;
import java.util.Set;

class TripsCard extends Card {
    private final String validityTerm;
    private int trips;
    private final LocalDate validFrom;

    private static final Set<String> ALLOWED_VALIDITY_TERMS = Set.of("month", "10 days");
    private static final Set<Integer> ALLOWED_COUNT_TRIPS = Set.of(5, 10);

    public TripsCard(String id, String type, String validityTerm, int trips, LocalDate validFrom) {
        super(id, type);

        validateInputs(type, validityTerm, trips);
        this.validityTerm = validityTerm;
        this.trips = trips;
        this.validFrom = validFrom;
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
    public boolean canPass(double tripCost) {
        return isValid() && trips > 0;
    }

    @Override
    public boolean useTrip(double tripCost) {
        if (canPass(tripCost)) {
            trips--;
            return true;
        }
        return false;
    }

    @Override
    public boolean addTrips(int count) {
        if (count <= 0) {
            System.out.println("The number of trips to add must be greater than 0!");
            return false;
        }
        trips += count;

        return true;
    }

    @Override
    public int getCountOfTrips() { return trips; }

    private void validateInputs(String type, String validityTerm, int trips) {
        if (!ALLOWED_VALIDITY_TERMS.contains(validityTerm)) {
            throw new IllegalArgumentException("Invalid validity term \"" + validityTerm + "\" for card type:" +
                    " " + type);
        }
        if (!ALLOWED_COUNT_TRIPS.contains(trips)) {
            throw new IllegalArgumentException("The number of trips can only be 5 or 10 when the card is issued, " +
                    "got: " + trips);
        }
    }
}
