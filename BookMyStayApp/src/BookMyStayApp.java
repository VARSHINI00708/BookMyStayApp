import java.util.*;
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

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType;
    }
}

class AddOnService {
    private String serviceId;
    private String name;
    private double price;

    public AddOnService(String serviceId, String name, double price) {
        this.serviceId = serviceId;
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

class AddOnServiceManager {

    private Map<String, List<AddOnService>> reservationServicesMap = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        reservationServicesMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public List<AddOnService> getServices(String reservationId) {
        return reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateTotalServiceCost(String reservationId) {
        double total = 0.0;
        for (AddOnService s : getServices(reservationId)) {
            total += s.getPrice();
        }
        return total;
    }
}
class BookingHistory {

    // Stores confirmed bookings in order
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    // Print all bookings
    public void printAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History Report ---");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }


    public void printSummary(List<Reservation> reservations) {
        System.out.println("\n--- Booking Summary ---");
        System.out.println("Total Bookings: " + reservations.size());
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        AddOnServiceManager serviceManager = new AddOnServiceManager();
        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        Reservation r1 = new Reservation("RES101", "Alice", "Deluxe");
        Reservation r2 = new Reservation("RES102", "Bob", "Suite");


        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);

        serviceManager.addService("RES101", new AddOnService("S1", "Breakfast", 15));
        serviceManager.addService("RES101", new AddOnService("S2", "Spa", 25));

        double cost = serviceManager.calculateTotalServiceCost("RES101");
        System.out.println("Add-on cost for RES101: $" + cost);

        List<Reservation> allBookings = bookingHistory.getAllReservations();

        reportService.printAllBookings(allBookings);
        reportService.printSummary(allBookings);
    }
}