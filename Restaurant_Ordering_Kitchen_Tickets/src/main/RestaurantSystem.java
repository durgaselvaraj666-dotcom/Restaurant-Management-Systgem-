package main;

import java.util.Scanner;
import model.*;
import service.RestaurantService;

public class RestaurantSystem {
    private static final Scanner sc = new Scanner(System.in);
    private static final RestaurantService service = new RestaurantService();

    public static void main(String[] args) {
        seedDemoData();
        boolean running = true;
        System.out.println("Welcome to Restaurant Ordering & Kitchen Tickets System");
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1: addTable(); break;
                    case 2: addMenuItem(); break;
                    case 3: seatCustomer(); break;
                    case 4: createOrder(); break;
                    case 5: sendToKitchen(); break;
                    case 6: updateKitchenStatus(); break;
                    case 7: generateBill(); break;
                    case 8: recordPayment(); break;
                    case 9: service.displayTables(); service.displayMenu(); break;
                    case 10: service.displayTickets(); break;
                    case 0: running = false; System.out.println("Thank you. Application closed."); break;
                    default: System.out.println("Invalid choice. Select a number from 0 to 10.");
                }
            } catch (IllegalArgumentException | IllegalStateException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Unexpected error: " + ex.getMessage());
            }
            System.out.println();
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n============== MAIN MENU ==============");
        System.out.println("1. Add Table");
        System.out.println("2. Add Menu Item");
        System.out.println("3. Seat Customer");
        System.out.println("4. Create Order");
        System.out.println("5. Send Order to Kitchen");
        System.out.println("6. Update Kitchen Ticket Status");
        System.out.println("7. Generate Bill");
        System.out.println("8. Record Payment");
        System.out.println("9. Display Tables and Menu");
        System.out.println("10. Display Kitchen Tickets");
        System.out.println("0. Exit");
        System.out.println("=======================================");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try { return Integer.parseInt(input); }
            catch (NumberFormatException e) { System.out.println("Please enter a valid whole number."); }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (!Double.isFinite(value)) throw new NumberFormatException();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("This field cannot be empty.");
        }
    }

    private static void addTable() {
        int id = readInt("Table ID: ");
        service.addTable(id);
        System.out.println("Table added successfully.");
    }

    private static void addMenuItem() {
        int id = readInt("Menu item ID: ");
        String name = readNonEmpty("Item name: ");
        double price = readDouble("Price (Rs.): ");
        service.addMenuItem(id, name, price);
        System.out.println("Menu item added successfully.");
    }

    private static void seatCustomer() {
        int tableId = readInt("Table ID: ");
        int customerId = readInt("Customer ID: ");
        String name = readNonEmpty("Customer name: ");
        service.seatCustomer(tableId, customerId, name);
        System.out.println("Customer seated successfully.");
    }

    private static void createOrder() {
        service.displayTables();
        int tableId = readInt("Table ID for this order: ");
        Order order = service.createOrder(tableId);
        service.displayMenu();
        System.out.println("Created order ID: " + order.getOrderId());
        while (true) {
            int itemId = readInt("Menu item ID (0 to finish adding items): ");
            if (itemId == 0) break;
            int quantity = readInt("Quantity: ");
            service.addOrderItem(order.getOrderId(), itemId, quantity);
            System.out.println("Item added.");
        }
        if (order.getItems().isEmpty()) {
            System.out.println("Order is empty. It cannot be sent to the kitchen.");
        } else {
            System.out.printf("Order subtotal: Rs. %.2f%n", order.getSubtotal());
        }
    }

    private static void sendToKitchen() {
        int orderId = readInt("Order ID: ");
        KitchenTicket ticket = service.sendToKitchen(orderId);
        System.out.println("Kitchen ticket created. Ticket ID: " + ticket.getTicketId()
                + ", status: " + ticket.getStatus());
    }

    private static void updateKitchenStatus() {
        service.displayTickets();
        int ticketId = readInt("Ticket ID: ");
        System.out.println("1. PENDING  2. PREPARING  3. READY");
        int statusChoice = readInt("Select status: ");
        KitchenTicket.Status status;
        switch (statusChoice) {
            case 1: status = KitchenTicket.Status.PENDING; break;
            case 2: status = KitchenTicket.Status.PREPARING; break;
            case 3: status = KitchenTicket.Status.READY; break;
            default: throw new IllegalArgumentException("Invalid ticket status choice.");
        }
        service.updateTicketStatus(ticketId, status);
        System.out.println("Kitchen ticket status updated.");
    }

    private static void generateBill() {
        int orderId = readInt("Order ID: ");
        Bill bill = service.generateBill(orderId);
        bill.printBill();
        System.out.println("Save Bill ID " + bill.getBillId() + " for payment.");
    }

    private static void recordPayment() {
        int billId = readInt("Bill ID: ");
        System.out.println("Payment methods: 1. CASH  2. UPI  3. CARD");
        int methodChoice = readInt("Select method: ");
        Payment.Method method;
        switch (methodChoice) {
            case 1: method = Payment.Method.CASH; break;
            case 2: method = Payment.Method.UPI; break;
            case 3: method = Payment.Method.CARD; break;
            default: throw new IllegalArgumentException("Invalid payment method.");
        }
        Bill bill = service.findBill(billId);
        if (bill == null) throw new IllegalArgumentException("Bill not found.");
        double amount = readDouble("Amount received (bill total Rs. " + String.format("%.2f", bill.getTotal()) + "): ");
        Payment payment = service.recordPayment(billId, method, amount);
        System.out.println("Payment successful. Payment ID: " + payment.getPaymentId()
                + " | Method: " + payment.getMethod() + " | Amount: Rs. "
                + String.format("%.2f", payment.getAmount()));
        System.out.println("Table " + bill.getOrder().getTable().getTableId() + " is now available.");
    }

    private static void seedDemoData() {
        // Demo records make the program easy to test. You can add more from the menu.
        service.addTable(1);
        service.addTable(2);
        service.addMenuItem(1, "Masala Dosa", 80.00);
        service.addMenuItem(2, "Idli (2 pcs)", 40.00);
        service.addMenuItem(3, "Coffee", 25.00);
    }
}
