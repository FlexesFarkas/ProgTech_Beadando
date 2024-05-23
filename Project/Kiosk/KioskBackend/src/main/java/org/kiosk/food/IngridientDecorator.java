package org.kiosk.food;

import org.kiosk.FoodType;

import java.util.List;

public abstract class IngridientDecorator implements IFood{
    protected IFood food;

    public IngridientDecorator(IFood food) {
        this.food = food;
    }
}
