
package edu.course.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingManagerSuccessfulBookingTest {

    @Mock
    private IEventRepository eventRepository;

    @Mock
    private IPaymentGateway paymentGateway;

    @Mock
    private INotificationService notificationService;

    @InjectMocks
    private BookingManager bookingManager;

    @Test
    void shouldBookTicketSuccessfully() {

        // Arrange
        BookingRequest request = new BookingRequest(
                "C101",
                "E201",
                2,
                1500
        );

        when(eventRepository.isSoldOut("E201"))
                .thenReturn(false);

        when(paymentGateway.processPayment("C101", 3000))
                .thenReturn("TX123");

        // Act
        String result = bookingManager.bookTicket(request);

        // Assert: Check returned transaction ID
        assertEquals("TX123", result);

        // Verify event availability check
        verify(eventRepository, times(1))
                .isSoldOut("E201");

        // Verify payment with calculated amount
        verify(paymentGateway, times(1))
                .processPayment("C101", 3000);

        // Verify booking was saved
        ArgumentCaptor<Booking> captor =
                ArgumentCaptor.forClass(Booking.class);

        verify(eventRepository, times(1))
                .saveBooking(captor.capture());

        Booking savedBooking = captor.getValue();

        assertEquals("C101", savedBooking.getCustomerId());
        assertEquals("E201", savedBooking.getEventId());
        assertEquals(2, savedBooking.getTicketQuantity());
        assertEquals(3000, savedBooking.getTotalAmountInCents());
        assertEquals("TX123", savedBooking.getTransactionId());

        // Verify confirmation was sent
        verify(notificationService, times(1))
                .sendConfirmation("C101");
    }
}
