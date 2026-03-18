public class BookMyStayApp {
    public class BookMyStayApp {

        public static abstract class Room {

            protected int numberOfBeds;
            protected int squareFeet;
            protected double pricePerNight;

            public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
                this.numberOfBeds = numberOfBeds;
                this.squareFeet = squareFeet;
                this.pricePerNight = pricePerNight;
            }

            public void displayRoomDetails() {
                System.out.println("Beds: " + numberOfBeds);
                System.out.println("Size: " + squareFeet + " sq.ft");
                System.out.println("Price per night: ₹" + pricePerNight);
            }
        }

        public static class SingleRoom extends Room {
            public SingleRoom() {
                super(1, 150, 2000.0);
            }

            @Override
            public void displayRoomDetails() {
                System.out.println("\n--- Single Room ---");
                super.displayRoomDetails();
            }
        }

        public static class DoubleRoom extends Room {
            public DoubleRoom() {
                super(2, 250, 3500.0);
            }

            @Override
            public void displayRoomDetails() {
                System.out.println("\n--- Double Room ---");
                super.displayRoomDetails();
            }
        }

        public static class SuiteRoom extends Room {
            public SuiteRoom() {
                super(3, 400, 6000.0);
            }

            @Override
            public void displayRoomDetails() {
                System.out.println("\n--- Suite Room ---");
                super.displayRoomDetails();
            }
        }

        public static void main(String[] args) {

            Room singleRoom = new SingleRoom();
            Room doubleRoom = new DoubleRoom();
            Room suiteRoom = new SuiteRoom();

            int singleAvailable = 5;
            int doubleAvailable = 3;
            int suiteAvailable = 2;

            System.out.println("===== Room Details & Availability =====");

            singleRoom.displayRoomDetails();
            System.out.println("Available: " + singleAvailable);

            doubleRoom.displayRoomDetails();
            System.out.println("Available: " + doubleAvailable);

            suiteRoom.displayRoomDetails();
            System.out.println("Available: " + suiteAvailable);

            System.out.println("\nSystem execution completed.");
        }
    }
}