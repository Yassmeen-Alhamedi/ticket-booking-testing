public interface IEventRepository {

    boolean isSoldOut(String eventId);

    void saveBooking(Booking booking);
}