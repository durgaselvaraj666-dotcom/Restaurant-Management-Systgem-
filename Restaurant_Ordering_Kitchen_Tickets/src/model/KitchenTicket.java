package model;

public class KitchenTicket {
    public enum Status { PENDING, PREPARING, READY }

    private final int ticketId;
    private final Order order;
    private Status status;

    public KitchenTicket(int ticketId, Order order) {
        if (order == null) throw new IllegalArgumentException("Order is required.");
        this.ticketId = ticketId;
        this.order = order;
        this.status = Status.PENDING;
    }

    public int getTicketId() { return ticketId; }
    public Order getOrder() { return order; }
    public Status getStatus() { return status; }

    public void setStatus(Status status) {
        if (status == null) throw new IllegalArgumentException("Status is required.");
        this.status = status;
    }
}
