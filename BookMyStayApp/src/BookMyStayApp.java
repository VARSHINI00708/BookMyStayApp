import java.util.LinkedList;
import java.util.Queue;

/* Reservation class */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

/* BookingRequestQueue class */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    public void viewRequests() {
        System.out.println("\n===== Booking Request Queue =====");

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }

    public Reservation getNextRequest() {
        return requestQueue.peek(); // does NOT remove
    }
}

/* Main class */
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Double"));
        queue.addRequest(new Reservation("Charlie", "Suite"));

        queue.viewRequests();

        System.out.println("\nRequests stored in FIFO order. No allocation done.");
    }
}