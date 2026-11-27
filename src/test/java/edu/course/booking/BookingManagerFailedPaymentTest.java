package edu.course.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingManagerFailedPaymentTest {

    @Mock
    private IEventRepository eventRepository;

    @Mock
    private IPaymentGateway paymentGateway;

    @Mock
    private INotificationService notificationService;

    @InjectMocks
    private BookingManager bookingManager;

    @Test
    void shouldThrowExceptionWhenPaymentFails() {

        BookingRequest request =
                new BookingRequest(
                        "C101",
                        "E201",
                        2,
                        1500
                );

        when(eventRepository.isSoldOut("E201"))
                .thenReturn(false);

        when(paymentGateway.processPayment(
                "C101",
                3000
        )).thenReturn("");

        assertThrows(
                IllegalStateException.class,
                () -> bookingManager.bookTicket(request)
        );

        verify(eventRepository, times(1)).isSoldOut("E201");
        verify(paymentGateway, times(1)).processPayment("C101", 3000);
        verify(eventRepository, never()).saveBooking(any());
        verify(notificationService, never()).sendConfirmation(anyString());
    }
}