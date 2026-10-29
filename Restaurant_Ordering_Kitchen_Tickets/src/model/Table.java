package model;

public class Table {
    private final int tableId;
    private boolean occupied;
    private Customer customer;

    public Table(int tableId) {
        if (tableId <= 0) throw new IllegalArgumentException("Table ID must be positive.");
        this.tableId = tableId;
        this.occupied = false;
    }

    public int getTableId() { return tableId; }
    public boolean isOccupied() { return occupied; }
    public Customer getCustomer() { return customer; }

    public void seatCustomer(Customer customer) {
        if (occupied) throw new IllegalStateException("Table is already occupied.");
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null.");
        this.customer = customer;
        this.occupied = true;
    }

    public void release() {
        this.customer = null;
        this.occupied = false;
    }

    @Override
    public String toString() {
        return "Table " + tableId + " - " + (occupied ? "OCCUPIED by " + customer.getName() : "AVAILABLE");
    }
}
