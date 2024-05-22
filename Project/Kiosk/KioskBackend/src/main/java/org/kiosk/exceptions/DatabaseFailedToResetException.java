package org.kiosk.exceptions;

public class DatabaseFailedToResetException extends Exception{
    public DatabaseFailedToResetException(){
        super("Database failed to reset");
    }
}
