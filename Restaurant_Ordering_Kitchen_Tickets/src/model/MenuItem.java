package model;

public class MenuItem {
    private final int itemId;
    private final String name;
    private final double price;
    private boolean available;

    public MenuItem(int itemId, String name, double price) {
        if (itemId <= 0) throw new IllegalArgumentException("Item ID must be positive.");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Item name cannot be empty.");
        if (price <= 0) throw new IllegalArgumentException("Price must be greater than zero.");
        this.itemId = itemId;
        this.name = name.trim();
        this.price = price;
        this.available = true;
    }

    public int getItemId() { return itemId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return itemId + ". " + name + " - Rs. " + String.format("%.2f", price)
                + (available ? " (Available)" : " (Unavailable)");
    }
}
