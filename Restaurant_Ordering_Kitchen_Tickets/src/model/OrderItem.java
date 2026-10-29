package model;

public class OrderItem {
    private final MenuItem menuItem;
    private final int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        if (menuItem == null) throw new IllegalArgumentException("Menu item is required.");
        if (!menuItem.isAvailable()) throw new IllegalArgumentException("This menu item is unavailable.");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }
    public double getLineTotal() { return menuItem.getPrice() * quantity; }
}
