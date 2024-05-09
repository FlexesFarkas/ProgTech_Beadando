package org.kiosk;
import java.sql.*;
import java.io.File;

public class Database {
    private static final String DATABASE_FILE = "./kiosk_db.sqlite";
    private static final String CONNECTION_URL = "jdbc:sqlite:" + DATABASE_FILE;
    private static Connection dbConenction;
    public static void connect(){
        try {
            Connection dbConenction = DriverManager.getConnection(CONNECTION_URL);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void disconnect(){
        try {
            dbConenction.close();
            System.out.println("Disconnected from database");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void checkIfDatabaseExists() {
        File file = new File(DATABASE_FILE);
        if(file.exists()){
            System.out.println("Database found!");
        }
        else {
            try{
                System.out.println("Database not found! Creating a new database...");
                Connection dbConenction = DriverManager.getConnection(CONNECTION_URL);
                Statement statement = dbConenction.createStatement();
                String sql = "BEGIN TRANSACTION;\n" +
                        "CREATE TABLE IF NOT EXISTS \"Ingredients\" (\n" +
                        "\t\"ingredient_id\"\tINTEGER NOT NULL UNIQUE,\n" +
                        "\t\"ingredient_name\"\tTEXT NOT NULL UNIQUE,\n" +
                        "\t\"ingredient_ammount\"\tINTEGER NOT NULL,\n" +
                        "\t\"ingredient_price\"\tINTEGER NOT NULL,\n" +
                        "\tPRIMARY KEY(\"ingredient_id\" AUTOINCREMENT)\n" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS \"Burgers\" (\n" +
                        "\t\"burger_id\"\tTEXT NOT NULL,\n" +
                        "\t\"burger_ingredients\"\tINTEGER NOT NULL,\n" +
                        "\t\"burger_base_price\"\tINTEGER NOT NULL,\n" +
                        "\tPRIMARY KEY(\"burger_id\")\n" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS \"Orders\" (\n" +
                        "\t\"order_id\"\tTEXT NOT NULL,\n" +
                        "\t\"burger_id\"\tTEXT NOT NULL,\n" +
                        "\tFOREIGN KEY(\"burger_id\") REFERENCES \"Burgers\"(\"burger_id\"),\n" +
                        "\tPRIMARY KEY(\"order_id\",\"burger_id\")\n" +
                        ");\n" +
                        "COMMIT;\n";
                statement.executeLargeUpdate(sql);
                dbConenction.close();
                System.out.println("New database successfully created!");
            } catch(Exception e){
                System.err.println("Failed to create database: " + e.getMessage());
            }
        }
    }
}
