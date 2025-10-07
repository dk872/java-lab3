package org.example;

import java.time.LocalDate;
import java.util.Set;

abstract class Card {
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
        }
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

    public String getId() { return id; }
    public String getType() { return type; }

    public abstract boolean isValid();
    public abstract boolean canPass();
    public abstract void useTrip();
}
