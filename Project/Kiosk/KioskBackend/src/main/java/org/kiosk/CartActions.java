package org.kiosk;

import org.kiosk.food.IFood;

import java.util.ArrayList;

public class CartActions {
    public static ArrayList<IFood> addToCart(ArrayList<IFood> foods, IFood food) {
        foods.add(food);
        return foods;
    }

    public static ArrayList<IFood> removeFromCart(ArrayList<IFood> foods, IFood food) {
        foods.remove(food);
        return foods;
    }

    public static double getCartPrice(ArrayList<IFood> foods) {
        double price = 0;
        for (IFood food : foods) {
            price += food.getCost();
        }
        return price;
    }
}
