package org.kiosk.food;

import org.kiosk.FoodType;

import java.util.List;

public abstract class IngridientDecorator implements IFood{
    protected IFood food;
    protected static String ingredientName;
    protected static int ingredientPrice;
    protected static List<FoodType> foodTypes;

    public IngridientDecorator(IFood food) {
        this.food = food;
    }
}
