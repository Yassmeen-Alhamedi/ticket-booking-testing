package edu.course.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingRequestTest {

    @Test
    void shouldCreateBookingRequestWithValidData() {

        BookingRequest request = new BookingRequest(
                "C101",
                "E201",
                2,
                1500
        );

        assertEquals("C101", request.getCustomerId());
        assertEquals("E201", request.getEventId());
        assertEquals(2, request.getTicketQuantity());
        assertEquals(1500, request.getPricePerTicketInCents());
    }

    @Test
    void shouldRejectNullCustomerId() {

        assertThrows(IllegalArgumentException.class, () -> {
            new BookingRequest(null, "E201", 2, 1500);
        });
    }

    @Test
    void shouldRejectBlankCustomerId() {

        assertThrows(IllegalArgumentException.class, () -> {
            new BookingRequest(" ", "E201", 2, 1500);
        });
    }

    @Test
    void shouldRejectNullEventId() {

        assertThrows(IllegalArgumentException.class, () -> {
            new BookingRequest("C101", null, 2, 1500);
        });
    }

    @Test
    void shouldRejectBlankEventId() {

        assertThrows(IllegalArgumentException.class, () -> {
            new BookingRequest("C101", " ", 2, 1500);
        });
    }

    @Test
    void shouldRejectTicketQuantityLessThanOne() {

        assertThrows(IllegalArgumentException.class, () -> {
            new BookingRequest("C101", "E201", 0, 1500);
        });
    }

    @Test
    void shouldRejectTicketQuantityGreaterThanFive() {

        assertThrows(IllegalArgumentException.class, () -> {
            new BookingRequest("C101", "E201", 6, 1500);
        });
    }

    @Test
    void shouldRejectZeroPrice() {

        assertThrows(IllegalArgumentException.class, () -> {
            new BookingRequest("C101", "E201", 2, 0);
        });
    }

    @Test
    void shouldRejectNegativePrice() {

        assertThrows(IllegalArgumentException.class, () -> {
            new BookingRequest("C101", "E201", 2, -100);
        });
    }
}