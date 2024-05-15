package org.kiosk;

import org.kiosk.order.OrderProcess;

public class Main {
    public static void main(String[] args) {
        Database.checkIfDatabaseExists();
        // -itt majd ki tudod próbálni ezt a nagyon kezdetleges verzióját a rendelésnek.
        Database.populateIngredientsFromDecorators();
        Database.generateStock();
        /*OrderProcess orderProcess = new OrderProcess();
        orderProcess.selectFood(FoodType.Burger);

        // Add cheese and tomato
        orderProcess.addCheese();
        orderProcess.addTomato();

        // Add to cart
        orderProcess.addToCart();

        // Make a payment
        orderProcess.makePayment("Payment details here");

        // Place the order
        orderProcess.placeOrder("Order ID here");*/
    }


}