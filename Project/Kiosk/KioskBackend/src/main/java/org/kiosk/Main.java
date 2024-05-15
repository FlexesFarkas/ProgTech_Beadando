package org.kiosk;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Database.checkIfDatabaseExists();
        Database.checkIngredient(2, 11);
    }


}