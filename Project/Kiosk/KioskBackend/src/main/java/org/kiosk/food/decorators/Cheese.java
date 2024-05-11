package org.kiosk.food.decorators;

import org.kiosk.FoodType;
import org.kiosk.food.IFood;
import org.kiosk.food.IngridientDecorator;

public class Cheese extends IngridientDecorator {
    public Cheese(IFood food) {
        super(food);
    }

    @Override
    public String getDescription() {
        return food.getDescription() + ", cheese";
    }

    @Override
    public double getCost() {
        return food.getCost() + 200;
    }

    @Override
    public FoodType getFoodType() {
        return food.getFoodType();
    }
}
