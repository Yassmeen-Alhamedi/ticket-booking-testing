package edu.course.booking;

public interface IEventRepository {
    Event findById(String eventId);
}