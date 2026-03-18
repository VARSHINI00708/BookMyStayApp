import java.util.*;

class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class InventoryManager {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryManager() {
        inventory.put("Standard", 1);
        inventory.put("Deluxe", 1);
    }


    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            // Simulate delay (to expose race condition if not synchronized)
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void printInventory() {
        System.out.println("\nFinal Inventory: " + inventory);
    }
}

class BookingProcessor implements Runnable {

    private Queue<BookingRequest> bookingQueue;
    private InventoryManager inventoryManager;

    public BookingProcessor(Queue<BookingRequest> bookingQueue,
                            InventoryManager inventoryManager) {
        this.bookingQueue = bookingQueue;
        this.inventoryManager = inventoryManager;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            // Synchronize queue access
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    return;
                }
                request = bookingQueue.poll();
            }

            // Process booking
            boolean success =
                    inventoryManager.allocateRoom(request.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName()
                        + " booked " + request.getRoomType()
                        + " for " + request.getGuestName());
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " FAILED booking for " + request.getGuestName()
                        + " (No rooms available)");
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Queue<BookingRequest> bookingQueue = new LinkedList<>();

        bookingQueue.add(new BookingRequest("Alice", "Deluxe"));
        bookingQueue.add(new BookingRequest("Bob", "Deluxe"));
        bookingQueue.add(new BookingRequest("Charlie", "Standard"));
        bookingQueue.add(new BookingRequest("David", "Standard"));

        InventoryManager inventoryManager = new InventoryManager();

        Thread t1 = new Thread(new BookingProcessor(bookingQueue, inventoryManager), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(bookingQueue, inventoryManager), "Thread-2");

        t1.start();
        t2.start();


        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventoryManager.printInventory();
    }
}