package model;

public class Customer {
    private final int customerId;
    private final String name;

    public Customer(int customerId, String name) {
        if (customerId <= 0) throw new IllegalArgumentException("Customer ID must be positive.");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Customer name cannot be empty.");
        this.customerId = customerId;
        this.name = name.trim();
    }

    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
}
