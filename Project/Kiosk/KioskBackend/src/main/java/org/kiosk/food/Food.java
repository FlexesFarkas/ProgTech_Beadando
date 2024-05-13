package org.kiosk.food;

import org.kiosk.FoodType;

public class Food implements IFood{

    FoodType foodType;
    public Food(FoodType foodType) {
        this.foodType = foodType;
    }

    @Override
    public String getDescription() {
        return foodType.name();
    }

    @Override
    public double getCost() {
        return 0;
    }

    @Override
    public FoodType getFoodType() {
        return foodType;
    }

    @Override
    public String getName() {
        return "Food";
    }

    public String toString(){
        return getDescription();
    }
}
