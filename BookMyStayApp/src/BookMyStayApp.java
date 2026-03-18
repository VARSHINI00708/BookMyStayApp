import java.util.Map;
public class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms() {

        System.out.println("\n===== Available Rooms =====");

        // Get inventory (read-only)
        Map<String, Integer> availability = inventory.getRoomAvailability();

        for (Map.Entry<String, Integer> entry : availability.entrySet()) {

            String roomType = entry.getKey();
            int count = entry.getValue();

            // ✅ Filter only available rooms
            if (count > 0) {

                Room room = createRoom(roomType);

                if (room != null) {
                    room.displayRoomDetails();
                    System.out.println("Available: " + count);
                }
            }
        }
    }


    private Room createRoom(String roomType) {

        switch (roomType) {
            case "Single":
                return new SingleRoom();
            case "Double":
                return new DoubleRoom();
            case "Suite":
                return new SuiteRoom();
            default:
                return null;
        }
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService(inventory);
        searchService.searchAvailableRooms();
        System.out.println("\nSearch completed. System state unchanged.");
    }
}