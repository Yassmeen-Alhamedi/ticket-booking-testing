
package edu.course.booking;

public class BookingManager {

    private final IEventRepository eventRepository;
    private final IPaymentGateway paymentGateway;
    private final INotificationService notificationService;

    public BookingManager(
            IEventRepository eventRepository,
            IPaymentGateway paymentGateway,
            INotificationService notificationService) {

        this.eventRepository = eventRepository;
        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
    }

    public boolean bookTicket(String eventId, String customerId) {

        // Find the event
        Event event = eventRepository.findById(eventId);

        // Check if event exists
        if (event == null) {
            return false;
        }

        // Check available seats
        if (event.getAvailableSeats() <= 0) {
            return false;
        }

        // Process payment
        boolean paymentSuccessful = paymentGateway.processPayment(
                customerId,
                event.getTicketPrice()
        );

        if (!paymentSuccessful) {
            return false;
        }

        // Send booking confirmation
        notificationService.sendConfirmation(customerId);

        return true;
    }
}
