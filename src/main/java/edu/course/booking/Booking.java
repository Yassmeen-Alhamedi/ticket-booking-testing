
package edu.course.booking;

public class Booking {

    private final String customerId;
    private final String eventId;
    private final int ticketQuantity;
    private final int totalAmountInCents;
    private final String transactionId;

    public Booking(String customerId,
                   String eventId,
                   int ticketQuantity,
                   int totalAmountInCents,
                   String transactionId) {

        this.customerId = customerId;
        this.eventId = eventId;
        this.ticketQuantity = ticketQuantity;
        this.totalAmountInCents = totalAmountInCents;
        this.transactionId = transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getEventId() {
        return eventId;
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public int getTotalAmountInCents() {
        return totalAmountInCents;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
