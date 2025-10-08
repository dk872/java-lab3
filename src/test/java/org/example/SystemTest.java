package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class SystemTest {
    private SystemRegistry registry;
    private Turnstile turnstile;

    @BeforeEach
    void setup() {
        turnstile = new Turnstile(10.0);
        registry = new SystemRegistry();
    }

    @Test
    void validTripsCardPasses() {
        Card card = registry.issueTripsCard("student", "10 days", 5, LocalDate.now());
        assertTrue(turnstile.payTrip(card));
    }

    @Test
    void expiredTripsCardFails() {
        LocalDate today = LocalDate.now();

        Card card = registry.issueTripsCard("pupil", "10 days", 5, today.minusDays(15));
        assertFalse(turnstile.payTrip(card));
    }

    @Test
    void tripsDecreaseAfterPass() {
        Card card = registry.issueTripsCard("student", "month", 5, LocalDate.now());
        assertTrue(turnstile.payTrip(card));
        assertTrue(turnstile.payTrip(card));
        assertTrue(turnstile.payTrip(card));
        assertTrue(turnstile.payTrip(card));
        assertTrue(turnstile.payTrip(card));
        assertFalse(turnstile.payTrip(card));
    }

    @Test
    void addTripsIncreasesCount() {
        Card card = registry.issueTripsCard("regular", "month", 5, LocalDate.now());
        assertTrue(card.addTrips(2));
        assertEquals(7, card.getCountOfTrips());
    }

    @Test
    void validOnLastDay() {
        LocalDate issued = LocalDate.now().minusDays(10);
        Card card = registry.issueTripsCard("student", "10 days", 5, issued);
        assertTrue(card.isValid());
        assertTrue(turnstile.payTrip(card));
    }

    @Test
    void invalidAfterLastDay() {
        LocalDate issued = LocalDate.now().minusDays(11);
        Card card = registry.issueTripsCard("student", "10 days", 5, issued);
        assertFalse(card.isValid());
        assertFalse(turnstile.payTrip(card));
    }

    @Test
    void validOneMonth() {
        LocalDate issued = LocalDate.now().minusMonths(1);
        Card card = registry.issueTripsCard("student", "month", 5, issued);
        assertTrue(card.isValid());
    }

    @Test
    void invalidAfterMonth() {
        LocalDate issued = LocalDate.now().minusMonths(1).minusDays(1);
        Card card = registry.issueTripsCard("student", "month", 5, issued);
        assertFalse(card.isValid());
    }

    @Test
    void addInvalidTripsFails() {
        Card card = registry.issueTripsCard("regular", "month", 5, LocalDate.now());
        assertFalse(card.addTrips(0));
        assertFalse(card.addTrips(-5));
    }

    @Test
    void getCardReturnsCorrect() {
        Card card = registry.issueTripsCard("regular", "10 days", 5, LocalDate.now());
        Card fetched = registry.getCard(card.getId());
        assertNotNull(fetched);
        assertEquals(card.getId(), fetched.getId());
        assertEquals(card.getType(), fetched.getType());
    }

    @Test
    void accumulatedCardPassesWithBalance() {
        AccumulatedCard card = registry.issueAccumulatedCard(50);
        assertTrue(turnstile.payTrip(card));
    }

    @Test
    void accumulatedCardFailsWithoutBalance() {
        AccumulatedCard card = registry.issueAccumulatedCard(5);
        assertFalse(turnstile.payTrip(card));
    }

    @Test
    void accumulatedCardDecreasesBalance() {
        AccumulatedCard card = registry.issueAccumulatedCard(30);
        assertTrue(turnstile.payTrip(card));
        assertEquals(20, card.getBalance(), 0.001);
    }

    @Test
    void accumulatedCardCanBeToppedUp() {
        AccumulatedCard card = registry.issueAccumulatedCard(5);
        card.addBalance(50);
        assertEquals(55, card.getBalance(), 0.001);
    }

    @Test
    void negativeBalanceThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> registry.issueAccumulatedCard(-10.0));
    }

    @Test
    void invalidCardTypeThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> registry.issueTripsCard("VIP", "month", 5, LocalDate.now()));
    }

    @Test
    void nonAccumulatedCannotBeUnlimited() {
        assertThrows(IllegalArgumentException.class,
                () -> registry.issueTripsCard("student", "unlimited", 5, LocalDate.now()));
    }

    @Test
    void negativeTripsThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> registry.issueTripsCard("regular", "month", -10, LocalDate.now()));
    }

    @Test
    void tripsCardWithFiveTripsValid() {
        Card card = registry.issueTripsCard("student", "10 days", 5, LocalDate.now());
        assertNotNull(card);
        assertEquals(5, card.getCountOfTrips());
    }

    @Test
    void tripsCardWithTenTripsValid() {
        Card card = registry.issueTripsCard("pupil", "10 days", 10, LocalDate.now());
        assertNotNull(card);
        assertEquals(10, card.getCountOfTrips());
    }

    @Test
    void tripsCardWithZeroTripsThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                registry.issueTripsCard("regular", "10 days", 0, LocalDate.now()));
    }

    @Test
    void countsPassesAndDenials() {
        LocalDate today = LocalDate.now();

        Card ok = registry.issueTripsCard("student", "10 days", 5, today);
        Card expired = registry.issueTripsCard("pupil", "10 days", 5, today.minusDays(20));

        assertTrue(turnstile.payTrip(ok));
        assertFalse(turnstile.payTrip(expired));
    }

    @Test
    void tripsCardUnlimitedThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                registry.issueTripsCard("pupil", "unlimited", 3, LocalDate.now()));
    }

    @Test
    void invalidValidityThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                registry.issueTripsCard("student", "year", 5, LocalDate.now()));
    }

    @Test
    void nullCardDenied() {
        assertFalse(turnstile.payTrip(null));
    }

    @Test
    void statisticsShouldCountPassesAndDenials() {
        Card ok = registry.issueTripsCard("student", "10 days", 5, LocalDate.now());
        Card expired = registry.issueTripsCard("pupil", "10 days", 5,
                LocalDate.now().minusDays(20));

        assertTrue(turnstile.payTrip(ok));
        assertFalse(turnstile.payTrip(expired));

        assertEquals(1, turnstile.getPassCount());
        assertEquals(1, turnstile.getDenialCount());
    }

    @Test
    void statisticsShouldCountByType() {
        Card student = registry.issueTripsCard("student", "10 days", 5, LocalDate.now());
        Card regular = registry.issueTripsCard("regular", "10 days", 5, LocalDate.now());
        AccumulatedCard accumulated = registry.issueAccumulatedCard(5.0);

        turnstile.payTrip(student);
        turnstile.payTrip(student);
        turnstile.payTrip(regular);
        turnstile.payTrip(accumulated);
        turnstile.payTrip(accumulated);
        turnstile.payTrip(accumulated);

        Map<String, Integer> statsPasses = turnstile.getPassesByType();
        Map<String, Integer> statsDenied = turnstile.getDeniedByType();
        assertEquals(2, statsPasses.get("student"));
        assertEquals(1, statsPasses.get("regular"));
        assertEquals(3, statsDenied.get("regular"));
    }
}