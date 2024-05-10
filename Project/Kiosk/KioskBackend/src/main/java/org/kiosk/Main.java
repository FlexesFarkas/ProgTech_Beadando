package org.kiosk;

public class Main {
    public static void main(String[] args) {
        Database.checkIfDatabaseExists();
        Database.checkIngredient(1, IngredientType.Burger);
        Database.checkIngredient(1, IngredientType.Shake);
    }
}