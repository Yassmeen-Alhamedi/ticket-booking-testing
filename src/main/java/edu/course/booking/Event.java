package edu.course.booking;

public class Event {

    private String eventId;
    private String eventName;
    private double ticketPrice;
    private int availableSeats;

    public Event(String eventId,
                 String eventName,
                 double ticketPrice,
                 int availableSeats) {

        this.eventId = eventId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.availableSeats = availableSeats;
    }

    public String getEventId() {
        return eventId;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}