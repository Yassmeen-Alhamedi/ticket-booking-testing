package edu.course.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingManagerSoldOutTest {

    @Mock
    private IEventRepository eventRepository;

    @Mock
    private IPaymentGateway paymentGateway;

    @Mock
    private INotificationService notificationService;

    @InjectMocks
    private BookingManager bookingManager;

    @Test
    void shouldThrowExceptionWhenEventIsSoldOut() {

        BookingRequest request =
                new BookingRequest(
                        "C101",
                        "E201",
                        2,
                        1500
                );

        when(eventRepository.isSoldOut("E201"))
                .thenReturn(true);

        assertThrows(
                IllegalStateException.class,
                () -> bookingManager.bookTicket(request)
        );

        verify(paymentGateway, never())
                .processPayment(anyString(), anyInt());

        verify(notificationService, never())
                .sendConfirmation(anyString());
    }
}