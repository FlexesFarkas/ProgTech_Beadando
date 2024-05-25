package org.kiosk;

import org.kiosk.exceptions.DatabaseFailedToResetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, DatabaseFailedToResetException {
        Database.resetDatabase();
        Database.checkIfDatabaseExists();
        Database.GenerateType();
        Database.populateIngredientsFromDecorators();
    }



}