import java.util.*;

public class HotelReservationSystem {

    // Room class
    static class Room {
        String roomNumber;
        String category;
        double price;
        boolean isAvailable = true;

        Room(String roomNumber, String category, double price) {
            this.roomNumber = roomNumber;
            this.category = category;
            this.price = price;
        }
    }

    // Reservation class
    static class Reservation {
        String customerName;
        Room room;
        String reservationId;

        Reservation(String customerName, Room room, String reservationId) {
            this.customerName = customerName;
            this.room = room;
            this.reservationId = reservationId;
        }
    }

    // Hotel class
    static class Hotel {
        List<Room> rooms = new ArrayList<>();
        Map<String, Reservation> reservations = new HashMap<>();
        Random random = new Random();

        // Add sample rooms
        public void initializeRooms() {
            rooms.add(new Room("101", "Standard", 2000));
            rooms.add(new Room("102", "Standard", 2000));
            rooms.add(new Room("201", "Deluxe", 3500));
            rooms.add(new Room("202", "Deluxe", 3500));
            rooms.add(new Room("301", "Suite", 5000));
        }

        // Display all available rooms
        public void displayAvailableRooms() {
            System.out.println("\n Available Rooms:");
            for (Room room : rooms) {
                if (room.isAvailable) {
                    System.out.println("Room " + room.roomNumber + " (" + room.category + ") - ₹" + room.price);
                }
            }
        }

        // Book a room
        public String bookRoom(String category, String customerName) {
            for (Room room : rooms) {
                if (room.category.equalsIgnoreCase(category) && room.isAvailable) {
                    room.isAvailable = false;
                    String reservationId = "R" + (1000 + random.nextInt(9000));
                    reservations.put(reservationId, new Reservation(customerName, room, reservationId));
                    System.out.println(" Booking Successful! Reservation ID: " + reservationId);
                    simulatePayment(room.price);
                    return reservationId;
                }
            }
            System.out.println(" No available rooms in this category.");
            return null;
        }

        // Cancel reservation
        public void cancelReservation(String reservationId) {
            Reservation res = reservations.get(reservationId);
            if (res != null) {
                res.room.isAvailable = true;
                reservations.remove(reservationId);
                System.out.println(" Reservation " + reservationId + " cancelled.");
            } else {
                System.out.println(" Reservation not found.");
            }
        }

        // View reservation details
        public void viewReservation(String reservationId) {
            Reservation res = reservations.get(reservationId);
            if (res != null) {
                System.out.println("\n Reservation Details:");
                System.out.println("Customer: " + res.customerName);
                System.out.println("Room: " + res.room.roomNumber + " (" + res.room.category + ")");
                System.out.println("Price: ₹" + res.room.price);
            } else {
                System.out.println("Reservation not found.");
            }
        }

        // Simulate payment
        private void simulatePayment(double amount) {
            System.out.println("Processing payment of ₹" + amount + "...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            System.out.println(" Payment successful!");
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();
        hotel.initializeRooms();

        int choice;
        do {
            System.out.println("\n --- Hotel Reservation System ---");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Reservation Details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    hotel.displayAvailableRooms();
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter room category (Standard/Deluxe/Suite): ");
                    String category = scanner.nextLine();
                    hotel.bookRoom(category, name);
                    break;
                case 3:
                    System.out.print("Enter Reservation ID to cancel: ");
                    String cancelId = scanner.nextLine();
                    hotel.cancelReservation(cancelId);
                    break;
                case 4:
                    System.out.print("Enter Reservation ID to view: ");
                    String viewId = scanner.nextLine();
                    hotel.viewReservation(viewId);
                    break;
                case 5:
                    System.out.println("Thank you for using the Hotel Reservation System.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 5);

        scanner.close();
    }
}