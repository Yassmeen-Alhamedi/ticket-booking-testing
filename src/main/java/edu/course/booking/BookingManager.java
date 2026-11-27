
package edu.course.booking;

public class BookingManager {

    private final IEventRepository eventRepository;
    private final IPaymentGateway paymentGateway;
    private final INotificationService notificationService;

    // Constructor Dependency Injection
    public BookingManager(
            IEventRepository eventRepository,
            IPaymentGateway paymentGateway,
            INotificationService notificationService) {

        this.eventRepository = eventRepository;
        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
    }

    public String bookTicket(BookingRequest request) {

        // 1. Validate the booking request
        validateRequest(request);

        // 2. Calculate the total amount
        int totalAmountInCents;

        try {
            totalAmountInCents = Math.multiplyExact(
                    request.getTicketQuantity(),
                    request.getPricePerTicketInCents()
            );
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Total amount is too large");
        }

        // 3. Check if the event is sold out
        boolean soldOut = eventRepository.isSoldOut(
                request.getEventId()
        );

        if (soldOut) {
            throw new EventSoldOutException(request.getEventId());
        }

        // 4. Process payment
        // A null or blank transaction ID means payment failed.
        String transactionId = paymentGateway.processPayment(
                request.getCustomerId(),
                totalAmountInCents
        );

        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalStateException("Payment failed");
        }

        // 5. Create and save the booking
        Booking booking = new Booking(
                request.getCustomerId(),
                request.getEventId(),
                request.getTicketQuantity(),
                totalAmountInCents,
                transactionId
        );

        eventRepository.saveBooking(booking);

        // 6. Send booking confirmation
        notificationService.sendConfirmation(
                request.getCustomerId()
        );

        // 7. Return the transaction ID
        return transactionId;
    }

    private void validateRequest(BookingRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        if (request.getCustomerId() == null ||
                request.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        if (request.getEventId() == null ||
                request.getEventId().isBlank()) {
            throw new IllegalArgumentException("Invalid event ID");
        }

        if (request.getTicketQuantity() < 1 ||
                request.getTicketQuantity() > 5) {
            throw new IllegalArgumentException("Invalid ticket quantity");
        }

        if (request.getPricePerTicketInCents() <= 0) {
            throw new IllegalArgumentException("Invalid ticket price");
        }
    }
}
