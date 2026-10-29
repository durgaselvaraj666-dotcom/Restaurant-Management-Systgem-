package service;

import java.util.ArrayList;
import java.util.List;
import model.*;

public class RestaurantService {
    private final List<Table> tables = new ArrayList<>();
    private final List<MenuItem> menu = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private final List<KitchenTicket> tickets = new ArrayList<>();
    private final List<Bill> bills = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();
    private int nextOrderId = 1, nextTicketId = 1, nextBillId = 1, nextPaymentId = 1;

    public void addTable(int id) {
        if (findTable(id) != null) throw new IllegalArgumentException("Table ID already exists.");
        tables.add(new Table(id));
    }

    public void addMenuItem(int id, String name, double price) {
        if (findMenuItem(id) != null) throw new IllegalArgumentException("Menu item ID already exists.");
        menu.add(new MenuItem(id, name, price));
    }

    public Table findTable(int id) {
        for (Table t : tables) if (t.getTableId() == id) return t;
        return null;
    }

    public MenuItem findMenuItem(int id) {
        for (MenuItem item : menu) if (item.getItemId() == id) return item;
        return null;
    }

    public Order findOrder(int id) {
        for (Order o : orders) if (o.getOrderId() == id) return o;
        return null;
    }

    public KitchenTicket findTicketByOrder(int orderId) {
        for (KitchenTicket t : tickets) if (t.getOrder().getOrderId() == orderId) return t;
        return null;
    }

    public Bill findBill(int id) {
        for (Bill b : bills) if (b.getBillId() == id) return b;
        return null;
    }

    public Bill findUnpaidBillForOrder(int orderId) {
        for (Bill b : bills) if (b.getOrder().getOrderId() == orderId && !b.isPaid()) return b;
        return null;
    }

    public List<Table> getTables() { return new ArrayList<>(tables); }
    public List<MenuItem> getMenu() { return new ArrayList<>(menu); }
    public List<Order> getOrders() { return new ArrayList<>(orders); }
    public List<KitchenTicket> getTickets() { return new ArrayList<>(tickets); }
    public List<Bill> getBills() { return new ArrayList<>(bills); }

    public void seatCustomer(int tableId, int customerId, String customerName) {
        Table table = findTable(tableId);
        if (table == null) throw new IllegalArgumentException("Table not found. Add it first.");
        table.seatCustomer(new Customer(customerId, customerName));
    }

    public Order createOrder(int tableId) {
        Table table = findTable(tableId);
        if (table == null) throw new IllegalArgumentException("Table not found.");
        if (!table.isOccupied()) throw new IllegalStateException("Seat a customer at this table first.");
        Order order = new Order(nextOrderId++, table.getCustomer(), table);
        orders.add(order);
        return order;
    }

    public void addOrderItem(int orderId, int itemId, int quantity) {
        Order order = findOrder(orderId);
        if (order == null) throw new IllegalArgumentException("Order not found.");
        MenuItem item = findMenuItem(itemId);
        if (item == null) throw new IllegalArgumentException("Menu item not found.");
        order.addItem(item, quantity);
    }

    public KitchenTicket sendToKitchen(int orderId) {
        Order order = findOrder(orderId);
        if (order == null) throw new IllegalArgumentException("Order not found.");
        if (order.isSentToKitchen()) throw new IllegalStateException("This order is already sent to the kitchen.");
        order.markSentToKitchen();
        KitchenTicket ticket = new KitchenTicket(nextTicketId++, order);
        tickets.add(ticket);
        return ticket;
    }

    public Bill generateBill(int orderId) {
        Order order = findOrder(orderId);
        if (order == null) throw new IllegalArgumentException("Order not found.");
        if (findUnpaidBillForOrder(orderId) != null) throw new IllegalStateException("An unpaid bill already exists for this order.");
        if (order.isPaid()) throw new IllegalStateException("This order is already paid.");
        Bill bill = new Bill(nextBillId++, order, 5.0, 5.0);
        bills.add(bill);
        return bill;
    }

    public Payment recordPayment(int billId, Payment.Method method, double amount) {
        Bill bill = findBill(billId);
        if (bill == null) throw new IllegalArgumentException("Bill not found.");
        Payment payment = new Payment(nextPaymentId++, bill, method, amount);
        bill.markPaid();
        bill.getOrder().markPaid();
        bill.getOrder().getTable().release();
        payments.add(payment);
        return payment;
    }

    public void displayTables() {
        if (tables.isEmpty()) { System.out.println("No tables added yet."); return; }
        for (Table table : tables) System.out.println(table);
    }

    public void displayMenu() {
        if (menu.isEmpty()) { System.out.println("No menu items added yet."); return; }
        for (MenuItem item : menu) System.out.println(item);
    }

    public void displayTickets() {
        if (tickets.isEmpty()) { System.out.println("No kitchen tickets yet."); return; }
        for (KitchenTicket t : tickets) {
            System.out.println("Ticket " + t.getTicketId() + " | Order " + t.getOrder().getOrderId()
                    + " | Table " + t.getOrder().getTable().getTableId() + " | Status: " + t.getStatus());
        }
    }

    public void updateTicketStatus(int ticketId, KitchenTicket.Status status) {
        for (KitchenTicket t : tickets) {
            if (t.getTicketId() == ticketId) { t.setStatus(status); return; }
        }
        throw new IllegalArgumentException("Kitchen ticket not found.");
    }
}
