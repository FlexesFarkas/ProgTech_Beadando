package org.kiosk;

import org.kiosk.food.IFood;

import java.util.ArrayList;

public class CartActions {
    public static ArrayList<IFood> addToCart(ArrayList<IFood> foods, IFood food) {
        foods.add(food);
        return foods;
    }
}
