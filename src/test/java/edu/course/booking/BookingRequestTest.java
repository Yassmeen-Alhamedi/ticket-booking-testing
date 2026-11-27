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

   
}