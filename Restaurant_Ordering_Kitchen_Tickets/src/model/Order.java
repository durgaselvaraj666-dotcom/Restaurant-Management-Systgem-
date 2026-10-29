package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final int orderId;
    private final Customer customer;
    private final Table table;
    private final List<OrderItem> items;
    private boolean sentToKitchen;
    private boolean paid;

    public Order(int orderId, Customer customer, Table table) {
        if (customer == null || table == null) throw new IllegalArgumentException("Customer and table are required.");
        if (!table.isOccupied()) throw new IllegalStateException("Customer must be seated before ordering.");
        this.orderId = orderId;
        this.customer = customer;
        this.table = table;
        this.items = new ArrayList<>();
        this.sentToKitchen = false;
        this.paid = false;
    }

    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public Table getTable() { return table; }
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
    public boolean isSentToKitchen() { return sentToKitchen; }
    public boolean isPaid() { return paid; }

    public void addItem(MenuItem item, int quantity) {
        if (sentToKitchen) throw new IllegalStateException("Order has already been sent to the kitchen.");
        items.add(new OrderItem(item, quantity));
    }

    public void markSentToKitchen() {
        if (items.isEmpty()) throw new IllegalStateException("Cannot send an empty order to the kitchen.");
        sentToKitchen = true;
    }

    public void markPaid() {
        if (!sentToKitchen) throw new IllegalStateException("Send the order to the kitchen before payment.");
        paid = true;
    }

    public double getSubtotal() {
        double total = 0;
        for (OrderItem item : items) total += item.getLineTotal();
        return total;
    }
}
