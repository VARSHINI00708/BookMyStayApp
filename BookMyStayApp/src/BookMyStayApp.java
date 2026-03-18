import java.util.HashMap;
import java.util.Map;


    private Map<String, Integer> roomAvailability;


    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }


    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }


    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }


    public void displayInventory() {
        System.out.println("\n===== Room Inventory =====");

        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }

    public class BookMyStayApp {

        public static void main(String[] args) {

            // Create inventory object
            RoomInventory inventory = new RoomInventory();

            // Display inventory
            inventory.displayInventory();

            // Example update
            System.out.println("\nUpdating availability...");
            inventory.updateAvailability("Single", 4);

            // Display updated inventory
            inventory.displayInventory();

            System.out.println("\nSystem execution completed.");
        }
    }