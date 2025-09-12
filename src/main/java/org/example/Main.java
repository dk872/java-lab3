package org.example;

import java.util.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        SystemRegistry registry = new SystemRegistry();
        Turnstile turnstile = new Turnstile(10);

        LocalDate today = LocalDate.now();

        Card studentCard = registry.issueTripsCard("student", "month", 0, today); // ще дійсна
        Card pupilCard = registry.issueTripsCard("pupil", "10 days", 5, today.minusDays(11)); // прострочена
        Card regularCard = registry.issueTripsCard("regular", "10 days", 10, today);
        AccumulatedCard accCard = registry.issueAccumulatedCard(50.0, turnstile);

        System.out.println("Student card pass: " + turnstile.payTrip(studentCard)); // true
        System.out.println("Pupil card pass: " + turnstile.payTrip(pupilCard)); // true
        System.out.println("Regular card pass: " + turnstile.payTrip(regularCard)); // true
        System.out.println("Accumulated card pass: " + turnstile.payTrip(accCard)); // true

        Card emptyTripsCard = registry.issueTripsCard("student", "month", 10, today.minusDays(30));
        System.out.println("Empty trips card pass: " + turnstile.payTrip(emptyTripsCard)); // false

        turnstile.printSummary();
        turnstile.printByType();
    }
}
