import java.util.*;
class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean isCancelled;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        this.isCancelled = true;
    }
}

class InventoryManager {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryManager() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void printInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

class CancellationService {

    private Map<String, Reservation> reservationStore;
    private InventoryManager inventoryManager;

    private Stack<String> releasedRoomStack = new Stack<>();

    public CancellationService(Map<String, Reservation> reservationStore,
                               InventoryManager inventoryManager) {
        this.reservationStore = reservationStore;
        this.inventoryManager = inventoryManager;
    }

    public void cancelBooking(String reservationId)
            throws CancellationException {

        if (!reservationStore.containsKey(reservationId)) {
            throw new CancellationException("Reservation does not exist.");
        }

        Reservation reservation = reservationStore.get(reservationId);

        if (reservation.isCancelled()) {
            throw new CancellationException("Reservation already cancelled.");
        }

        releasedRoomStack.push(reservation.getRoomId());
        inventoryManager.incrementRoom(reservation.getRoomType());

        reservation.cancel();

        System.out.println("Cancellation successful for ID: " + reservationId);
    }

    public void printRollbackStack() {
        System.out.println("\nRollback Stack (Recently Released Rooms): " + releasedRoomStack);
    }
}

/
public class BookMyStayApp {

    public static void main(String[] args) {

        Map<String, Reservation> reservationStore = new HashMap<>();


        InventoryManager inventoryManager = new InventoryManager();


        Reservation r1 = new Reservation("RES101", "Deluxe", "D1");
        Reservation r2 = new Reservation("RES102", "Suite", "S1");

        reservationStore.put(r1.getReservationId(), r1);
        reservationStore.put(r2.getReservationId(), r2);


        CancellationService cancellationService =
                new CancellationService(reservationStore, inventoryManager);

        try {
            cancellationService.cancelBooking("RES101");

            cancellationService.cancelBooking("RES101");

        } catch (CancellationException e) {
            System.out.println("Cancellation Failed: " + e.getMessage());
        }
        inventoryManager.printInventory();
        cancellationService.printRollbackStack();
    }
}