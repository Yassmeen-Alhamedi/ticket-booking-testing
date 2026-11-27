package edu.course.booking;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class BookingManagerInvalidRequestTest {

    @Mock
    private IEventRepository eventRepository;

    @Mock
    private IPaymentGateway paymentGateway;

    @Mock
    private INotificationService notificationService;

    @InjectMocks
    private BookingManager bookingManager;

    @ParameterizedTest
    @MethodSource("invalidRequests")
    void shouldRejectInvalidRequestWithoutTouchingDependencies(BookingRequest request) {

        assertThrows(IllegalArgumentException.class,
                () -> bookingManager.bookTicket(request));

        verifyNoInteractions(eventRepository, paymentGateway, notificationService);
    }

    static Stream<BookingRequest> invalidRequests() {
        return Stream.of(
                new BookingRequest(null, "E201", 2, 1500),   // null customer
                new BookingRequest(" ", "E201", 2, 1500),    // blank customer
                new BookingRequest("C101", null, 2, 1500),   // null event
                new BookingRequest("C101", " ", 2, 1500),    // blank event
                new BookingRequest("C101", "E201", 0, 1500), // quantity too low
                new BookingRequest("C101", "E201", 6, 1500), // quantity too high
                new BookingRequest("C101", "E201", 2, 0),    // zero price
                new BookingRequest("C101", "E201", 2, -100)  // negative price
        );
    }
}