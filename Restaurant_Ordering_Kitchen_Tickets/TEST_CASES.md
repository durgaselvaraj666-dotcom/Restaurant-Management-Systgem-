# Test cases and viva checklist

## Test cases
1. Add duplicate table ID: should display an error.
2. Seat a customer at an unknown table: should display an error.
3. Seat two customers at the same table: second attempt should fail.
4. Enter a negative quantity: should fail.
5. Send an empty order to kitchen: should fail.
6. Generate bill before sending order to kitchen: should fail.
7. Pay less than bill total: should fail and table should remain occupied.
8. Pay the full amount: bill becomes PAID and table becomes AVAILABLE.
9. Try to pay the same bill again: should fail.
10. Enter letters where a number is expected: program should ask again.

## Viva topics to understand
- Encapsulation: private fields and public getters/methods.
- Composition/aggregation: Order contains OrderItem objects; Order references Customer and Table.
- Enum: KitchenTicket.Status and Payment.Method limit values to valid choices.
- ArrayList: stores tables, menu items, orders, tickets, bills and payments.
- Exception handling: try/catch prevents invalid operations from terminating the menu loop.
- Business rules: table availability, valid quantities, order-before-bill, full payment-before-release.
- Limitations: in-memory data resets on restart; tax/service rates are sample values.
