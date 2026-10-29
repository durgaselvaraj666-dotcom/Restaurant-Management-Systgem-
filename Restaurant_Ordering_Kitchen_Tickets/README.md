# Restaurant Ordering & Kitchen Tickets System
Java console mini-project for 23CSR306 Java Programming, Assignment II, Specification 24.

## Features
- Add tables and menu items (demo tables and menu items are preloaded).
- Seat customers at available tables.
- Create orders and add menu items with quantities.
- Create kitchen tickets and update status (PENDING, PREPARING, READY).
- Generate itemized bills with 5% tax and 5% service charge.
- Record CASH, UPI, or CARD payment.
- Release a table after successful payment.
- Input validation, duplicate-ID checks, and exception handling.

## Requirements
- JDK 8 or newer
- Eclipse IDE (or any Java IDE)

## Run in Eclipse
1. Open Eclipse and select **File > New > Java Project**.
2. Name it `Restaurant_Ordering_Kitchen_Tickets`.
3. Copy the `src` folder from this project into the Eclipse project's `src` folder.
4. Ensure the packages `main`, `model`, `service`, and `exception` appear under `src`.
5. Open `main/RestaurantSystem.java`.
6. Right-click the file and choose **Run As > Java Application**.

## Run from a terminal
From this project folder:
```bash
javac -d out src/model/*.java src/service/*.java src/exception/*.java src/main/*.java
java -cp out main.RestaurantSystem
```

## Quick test
1. Choose `3`, table ID `1`, customer ID `101`, name `Mahi`.
2. Choose `4`, table ID `1`, add menu item `1` quantity `2`, then enter `0`.
3. Choose `5`, order ID `1`.
4. Choose `6`, ticket ID `1`, status `3` (READY).
5. Choose `7`, order ID `1`. Note the displayed bill ID and total.
6. Choose `8`, enter that bill ID, choose payment method `2` (UPI), and enter the exact total.
7. Choose `9` to confirm table 1 is available again.
8. Choose `10` to view kitchen ticket status.

## Business rules
- A customer can only be seated at a registered, available table.
- Table IDs and menu item IDs must be unique.
- Orders require a seated customer and positive quantities.
- Empty orders cannot be sent to the kitchen.
- Bills can only be generated after sending an order to the kitchen.
- Payment must cover the full bill; duplicate payment is prevented.
- A table becomes available only after successful payment.

## Notes
- Tax and service charge are set to 5% each as demonstration assumptions. Confirm with your faculty if specific rates are required.
- This is an in-memory console application. Records reset when the application restarts.
- The `exception` package contains an example custom exception class; current input validation mainly uses standard Java exceptions.
