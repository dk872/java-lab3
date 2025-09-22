package org.example;

import java.time.LocalDate;
import java.util.Set;

class Card {
    protected String id;
    protected String type;
    protected String validityTerm;
    protected int trips;
    protected boolean isAccumulated;
    protected LocalDate validFrom;

    private static final Set<String> ALLOWED_TYPES = Set.of("student", "pupil", "regular");
    private static final Set<String> ALLOWED_VALIDITY_TERMS = Set.of("month", "10 days");
    private static final Set<Integer> ALLOWED_COUNT_TRIPS = Set.of(5, 10);

    public Card(String id, String type, String validityTerm, int trips, boolean isAccumulated, LocalDate validFrom) {
        validateInputs(type, validityTerm, trips, isAccumulated);

        this.id = id;
        this.type = type;
        this.validityTerm = validityTerm;
        this.trips = trips;
        this.isAccumulated = isAccumulated;
        this.validFrom = validFrom;
    }

    private void validateInputs(String type, String validityTerm, int trips, boolean isAccumulated) {
        if (!ALLOWED_TYPES.contains(type)) {
            throw new IllegalArgumentException("Invalid card type: " + type);
        }

        if (!isAccumulated) {
            if ("unlimited".equals(validityTerm)) {
                throw new IllegalArgumentException("Non-accumulator cards cannot have validity term 'unlimited'");
            }
            if (!ALLOWED_VALIDITY_TERMS.contains(validityTerm)) {
                throw new IllegalArgumentException("Invalid validity term \"" + validityTerm + "\" for card type:" +
                        " " + type);
            }
            if (!ALLOWED_COUNT_TRIPS.contains(trips)) {
                throw new IllegalArgumentException("The number of trips can only be 5 or 10 when the card is issued, " +
                        "got: " + trips);
            }
            if (trips < 0) {
                throw new IllegalArgumentException("The number of trips cannot be negative: " + trips);
            }
        }
    }

    public String getId() { return id; }
    public String getType() { return type; }

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

    public boolean canPass() {
        if (!isValid()) return false;

        return trips > 0;
    }

    public void useTrip() {
        if (!canPass()) return;
        trips--;
    }

    public boolean addTrips(int count) {
        if (isAccumulated) {
            System.out.println("Trip top-up is not available for prepaid cards!");
            return false;
        }
        if (count <= 0) {
            System.out.println("The number of trips to add must be greater than 0!");
            return false;
        }
        trips += count;

        return true;
    }
}
