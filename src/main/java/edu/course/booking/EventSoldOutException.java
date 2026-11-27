package edu.course.booking;

public class EventSoldOutException extends RuntimeException {
    public EventSoldOutException(String eventId) {
        super("Event is sold out: " + eventId);
    }
}