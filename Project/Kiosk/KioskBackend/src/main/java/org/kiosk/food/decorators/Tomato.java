package org.kiosk.food.decorators;

import org.kiosk.FoodType;
import org.kiosk.food.IFood;
import org.kiosk.food.IngridientDecorator;

public class Tomato extends IngridientDecorator {
    public Tomato(IFood food) {
        super(food);
    }

    @Override
    public String getDescription() {
        return food.getDescription() + ", tomato";
    }

    @Override
    public double getCost() {
        return food.getCost() + 100;
    }

    @Override
    public FoodType getFoodType() {
        return food.getFoodType();
    }
}
