package org.kiosk;

import org.kiosk.order.OrderProcess;

public class Main {
    public static void main(String[] args) {
        Database.checkIfDatabaseExists();
        /* -itt majd ki tudod próbálni ezt a nagyon kezdetleges verzióját a rendelésnek.

        OrderProcess order = new OrderProcess();

        // Kiválasztjuk a burgert
        order.selectFood(FoodType.Burger);

        // Hozzáadunk sajtot
        order.addCheese();

        // Hozzáadunk paradicsomot
        order.addTomato();

        // Leadjuk a rendelést
        order.placeOrder("1234");*/
    }


}