package org.kiosk;

import org.kiosk.exceptions.DatabaseFailedToResetException;
import org.kiosk.food.GenFood;
import org.kiosk.food.IFood;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, DatabaseFailedToResetException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Database.resetDatabase();
        Database.checkIfDatabaseExists();
        Database.GenerateType();
        Database.populateIngredientsFromDecorators();
        GenFood newfood = new GenFood("Burger",new int[]{1,1,1,0,0});
        IFood newIfood = newfood.convertToIFood();
        System.out.println(newIfood.toString());

    }



}