import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - BookMyStayApp
 * ============================================================
 * Complete Hotel Booking System (Use Case 1 to 6)
 */

public class BookMyStayApp {

    /* ================= ROOM CLASSES ================= */

    static abstract class Room {
        protected int numberOfBeds;
        protected int squareFeet;
        protected double pricePerNight;

        public Room(int beds, int size, double price) {
            this.numberOfBeds = beds;
            this.squareFeet = size;
            this.pricePerNight = price;
        }

        public void displayRoomDetails() {
            System.out.println("Beds: " + numberOfBeds);
            System.out.println("Size: " + squareFeet + " sq.ft");
            System.out.println("Price: ₹" + pricePerNight);
        }
    }

    static class SingleRoom extends Room {
        public SingleRoom() {
            super(1, 150, 2000);
        }

        public void displayRoomDetails() {
            System.out.println("\n--- Single Room ---");
            super.displayRoomDetails();
        }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super(2, 250, 3500);
        }

        public void displayRoomDetails() {
            System.out.println("\n--- Double Room ---");
            super.displayRoomDetails();
        }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super(3, 400, 6000);
        }

        public void displayRoomDetails() {
            System.out.println("\n--- Suite Room ---");
            super.displayRoomDetails();
        }
    }

    /* ================= INVENTORY ================= */

    static class RoomInventory {
        private Map<String, Integer> availability = new HashMap<>();

        public RoomInventory() {
            availability.put("Single", 5);
            availability.put("Double", 3);
            availability.put("Suite", 2);
        }

        public Map<String, Integer> getRoomAvailability() {
            return availability;
        }

        public void updateAvailability(String type, int count) {
            availability.put(type, count);
        }

        public void displayInventory() {
            System.out.println("\n===== Inventory =====");
            for (Map.Entry<String, Integer> e : availability.entrySet()) {
                System.out.println(e.getKey() + " Rooms: " + e.getValue());
            }
        }
    }

    /* ================= SEARCH ================= */

    static class RoomSearchService {
        private RoomInventory inventory;

        public RoomSearchService(RoomInventory inventory) {
            this.inventory = inventory;
        }

        public void searchAvailableRooms() {
            System.out.println("\n===== Available Rooms =====");

            for (Map.Entry<String, Integer> e : inventory.getRoomAvailability().entrySet()) {

                if (e.getValue() > 0) {
                    Room room = createRoom(e.getKey());

                    if (room != null) {
                        room.displayRoomDetails();
                        System.out.println("Available: " + e.getValue());
                    }
                }
            }
        }

        private Room createRoom(String type) {
            switch (type) {
                case "Single": return new SingleRoom();
                case "Double": return new DoubleRoom();
                case "Suite": return new SuiteRoom();
                default: return null;
            }
        }
    }

    /* ================= RESERVATION ================= */

    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guest, String type) {
            this.guestName = guest;
            this.roomType = type;
        }

        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }

        public void displayReservation() {
            System.out.println(guestName + " → " + roomType);
        }
    }

    /* ================= QUEUE ================= */

    static class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public void addRequest(Reservation r) {
            queue.offer(r);
            System.out.println("Added: " + r.getGuestName());
        }

        public Reservation removeRequest() {
            return queue.poll();
        }

        public void viewRequests() {
            System.out.println("\n===== Queue =====");
            for (Reservation r : queue) {
                r.displayReservation();
            }
        }
    }

    /* ================= BOOKING ================= */

    static class BookingService {
        private BookingRequestQueue queue;
        private RoomInventory inventory;

        private Set<String> allocatedIds = new HashSet<>();
        private Map<String, Set<String>> allocations = new HashMap<>();

        public BookingService(BookingRequestQueue q, RoomInventory i) {
            this.queue = q;
            this.inventory = i;
        }

        public void processBooking() {

            Reservation r = queue.removeRequest();

            if (r == null) {
                System.out.println("No requests.");
                return;
            }

            String type = r.getRoomType();
            int available = inventory.getRoomAvailability().getOrDefault(type, 0);

            if (available > 0) {

                String roomId = generateRoomId(type);

                allocatedIds.add(roomId);

                allocations
                        .computeIfAbsent(type, k -> new HashSet<>())
                        .add(roomId);

                inventory.updateAvailability(type, available - 1);

                System.out.println("\n✅ Booking Confirmed");
                System.out.println("Guest: " + r.getGuestName());
                System.out.println("Room ID: " + roomId);

            } else {
                System.out.println("\n❌ No rooms for " + type);
            }
        }

        private String generateRoomId(String type) {
            String id;
            do {
                id = type.charAt(0) + "" + (int)(Math.random() * 1000);
            } while (allocatedIds.contains(id));
            return id;
        }

        public void showAllocations() {
            System.out.println("\n===== Allocations =====");
            System.out.println(allocations);
        }
    }

    /* ================= MAIN ================= */

    public static void main(String[] args) {

        System.out.println("===== Welcome to Book My Stay App v6.0 =====");

        RoomInventory inventory = new RoomInventory();

        RoomSearchService search = new RoomSearchService(inventory);
        search.searchAvailableRooms();

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Double"));
        queue.addRequest(new Reservation("Charlie", "Suite"));

        queue.viewRequests();

        BookingService service = new BookingService(queue, inventory);

        service.processBooking();
        service.processBooking();
        service.processBooking();

        service.showAllocations();
        inventory.displayInventory();

        System.out.println("\nSystem Completed.");
    }
}