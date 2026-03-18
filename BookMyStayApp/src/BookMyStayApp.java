import java.util.*;
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingValidator {

    private static final Set<String> VALID_ROOM_TYPES =
            new HashSet<>(Arrays.asList("Standard", "Deluxe", "Suite"));

    public static void validate(Reservation reservation)
            throws InvalidBookingException {


        if (reservation.getGuestName() == null ||
                reservation.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!VALID_ROOM_TYPES.contains(reservation.getRoomType())) {
            throw new InvalidBookingException(
                    "Invalid room type: " + reservation.getRoomType()
            );
        }
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {

        Reservation validBooking =
                new Reservation("RES101", "Alice", "Deluxe");

        Reservation invalidBooking =
                new Reservation("RES102", "Bob", "Penthouse");

        processBooking(validBooking);
        processBooking(invalidBooking);
    }
    public static void processBooking(Reservation reservation) {
        try {
            BookingValidator.validate(reservation);
            System.out.println("Booking Successful for ID: "
                    + reservation.getReservationId());

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed for ID: "
                    + reservation.getReservationId());
            System.out.println("Reason: " + e.getMessage());
        }
    }
}