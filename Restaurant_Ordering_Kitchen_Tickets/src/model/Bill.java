package model;

public class Bill {
    private final int billId;
    private final Order order;
    private final double taxRate;
    private final double serviceRate;
    private final double subtotal;
    private final double tax;
    private final double serviceCharge;
    private final double total;
    private boolean paid;

    public Bill(int billId, Order order, double taxRate, double serviceRate) {
        if (order == null) throw new IllegalArgumentException("Order is required.");
        if (!order.isSentToKitchen()) throw new IllegalStateException("Order must be sent to the kitchen before billing.");
        if (taxRate < 0 || serviceRate < 0) throw new IllegalArgumentException("Rates cannot be negative.");
        this.billId = billId;
        this.order = order;
        this.taxRate = taxRate;
        this.serviceRate = serviceRate;
        this.subtotal = order.getSubtotal();
        this.tax = subtotal * taxRate / 100.0;
        this.serviceCharge = subtotal * serviceRate / 100.0;
        this.total = subtotal + tax + serviceCharge;
        this.paid = false;
    }

    public int getBillId() { return billId; }
    public Order getOrder() { return order; }
    public double getTotal() { return total; }
    public boolean isPaid() { return paid; }
    public void markPaid() { paid = true; }

    public void printBill() {
        System.out.println("\n========== RESTAURANT BILL ==========");
        System.out.println("Bill ID: " + billId + " | Order ID: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomer().getName());
        System.out.println("Table: " + order.getTable().getTableId());
        System.out.println("-------------------------------------");
        for (OrderItem item : order.getItems()) {
            System.out.printf("%-18s x%-3d Rs. %8.2f%n",
                    item.getMenuItem().getName(), item.getQuantity(), item.getLineTotal());
        }
        System.out.println("-------------------------------------");
        System.out.printf("Subtotal:                 Rs. %8.2f%n", subtotal);
        System.out.printf("Tax (%.1f%%):               Rs. %8.2f%n", taxRate, tax);
        System.out.printf("Service charge (%.1f%%):    Rs. %8.2f%n", serviceRate, serviceCharge);
        System.out.printf("TOTAL:                    Rs. %8.2f%n", total);
        System.out.println("Payment status: " + (paid ? "PAID" : "UNPAID"));
        System.out.println("=====================================");
    }
}
