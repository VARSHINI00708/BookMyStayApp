import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    @Override
    public String toString() {
        return reservationId + " - " + guestName;
    }
}

class SystemState implements Serializable {
    List<Reservation> bookings;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> bookings,
                       Map<String, Integer> inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}


class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";


    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Corrupted data. Starting with clean state.");
        }

        return new SystemState(new ArrayList<>(), new HashMap<>());
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        PersistenceService persistenceService = new PersistenceService();


        SystemState state = persistenceService.load();

        List<Reservation> bookings = state.bookings;
        Map<String, Integer> inventory = state.inventory;

        if (inventory.isEmpty()) {
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 1);
        }

        Reservation r1 = new Reservation("RES101", "Alice");
        bookings.add(r1);

        inventory.put("Standard", inventory.get("Standard") - 1);

        System.out.println("\nCurrent Bookings: " + bookings);
        System.out.println("Current Inventory: " + inventory);

        SystemState newState = new SystemState(bookings, inventory);
        persistenceService.save(newState);
    }
}