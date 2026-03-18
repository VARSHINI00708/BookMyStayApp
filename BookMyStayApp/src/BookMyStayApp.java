import java.util.*;
class AddOnService {
    private String serviceId;
    private String name;
    private double price;

    public AddOnService(String serviceId, String name, double price) {
        this.serviceId = serviceId;
        this.name = name;
        this.price = price;
    }
    public String getServiceId() {
        return serviceId;
    }

    public String getName() {
        return name;
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

    // Reservation ID → List of Services
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
        for (AddOnService service : getServices(reservationId)) {
            total += service.getPrice();
        }
        return total;
    }

    public void printServices(String reservationId) {
        List<AddOnService> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Add-On Services for Reservation " + reservationId + ":");
        for (AddOnService service : services) {
            System.out.println("- " + service);
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();
        String reservationId = "RES101";
        AddOnService breakfast = new AddOnService("S1", "Breakfast", 15.0);
        AddOnService pickup = new AddOnService("S2", "Airport Pickup", 40.0);
        AddOnService spa = new AddOnService("S3", "Spa Access", 25.0);
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, pickup);
        manager.addService(reservationId, spa);

        manager.printServices(reservationId);
        double totalCost = manager.calculateTotalServiceCost(reservationId);
        System.out.println("Total Add-On Cost: $" + totalCost);
        System.out.println("\nBooking & room allocation remain unchanged.");
    }
}