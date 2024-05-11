package org.kiosk.food;

import org.kiosk.FoodType;

public interface IFood {
    String getDescription();
    double getCost();
    FoodType getFoodType();
}
