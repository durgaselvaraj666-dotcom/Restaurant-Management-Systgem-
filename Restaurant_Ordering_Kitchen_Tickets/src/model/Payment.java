package model;

public class Payment {
    public enum Method { CASH, UPI, CARD }

    private final int paymentId;
    private final Bill bill;
    private final Method method;
    private final double amount;
    private final boolean successful;

    public Payment(int paymentId, Bill bill, Method method, double amount) {
        if (bill == null || method == null) throw new IllegalArgumentException("Bill and payment method are required.");
        if (bill.isPaid()) throw new IllegalStateException("This bill has already been paid.");
        if (amount + 0.000001 < bill.getTotal()) throw new IllegalArgumentException("Payment amount is less than the bill total.");
        this.paymentId = paymentId;
        this.bill = bill;
        this.method = method;
        this.amount = amount;
        this.successful = true;
    }

    public int getPaymentId() { return paymentId; }
    public Bill getBill() { return bill; }
    public Method getMethod() { return method; }
    public double getAmount() { return amount; }
    public boolean isSuccessful() { return successful; }
}
