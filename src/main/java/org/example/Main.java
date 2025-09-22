package org.example;

import java.util.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        double tripCost = 10.0;
        LocalDate today = LocalDate.now();

        SystemRegistry registry = new SystemRegistry();
        Turnstile turnstile = new Turnstile(tripCost);

        // Creating different cards
        Card studentCard = registry.issueTripsCard("student", "month", 3, today);
        Card pupilCard = registry.issueTripsCard("pupil", "10 days", 5,
                today.minusDays(5));
        Card regularCard = registry.issueTripsCard("regular", "10 days", 10, today);
        AccumulatedCard accCard = registry.issueAccumulatedCard(50.0, turnstile);

        // Passage demonstration
        System.out.println("Student card pass: " + turnstile.payTrip(studentCard)); // true
        System.out.println("Pupil card pass: " + turnstile.payTrip(pupilCard)); // true
        System.out.println("Regular card pass: " + turnstile.payTrip(regularCard)); // true
        System.out.println("Accumulated card pass: " + turnstile.payTrip(accCard)); // true

        // After several passes
        turnstile.payTrip(studentCard); // one more pass
        turnstile.payTrip(studentCard); // one more pass
        System.out.println("Student card after 3 trips: " + turnstile.payTrip(studentCard)); // false (runs out of trips)

        // Overdue card
        Card expiredPupilCard = registry.issueTripsCard("pupil", "10 days", 5,
                today.minusDays(15));
        System.out.println("Expired pupil card pass: " + turnstile.payTrip(expiredPupilCard)); // false

        // Accumulated card
        AccumulatedCard lowBalanceCard = registry.issueAccumulatedCard(3.0, turnstile);
        System.out.println("Low balance accumulated card pass: " + turnstile.payTrip(lowBalanceCard)); // false
        lowBalanceCard.addBalance(7.0);
        System.out.println("Low balance accumulated card after top-up: " + turnstile.payTrip(lowBalanceCard)); // true

        // Turnstile statistics
        turnstile.printSummary();
        turnstile.printByType();
    }
}