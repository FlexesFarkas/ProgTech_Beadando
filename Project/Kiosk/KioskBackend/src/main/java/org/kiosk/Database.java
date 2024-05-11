package org.kiosk;
import java.sql.*;
import java.io.File;

public class Database {
    private static final String DATABASE_FILE = "./kiosk_db.sqlite";
    private static final String CONNECTION_URL = "jdbc:sqlite:" + DATABASE_FILE;

    public static Connection connect(){
        try {
            Connection dbConnection = DriverManager.getConnection(CONNECTION_URL);
            System.out.println("Connected to database");
            return dbConnection;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static void disconnect(Connection dbConnection){
        try {
            dbConnection.close();
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
            createDatabase();
        }
    }

    private static void createDatabase() {
        try{
            System.out.println("Database not found! Creating a new database...");
            Connection dbConenction = connect();
            Statement statement = dbConenction.createStatement();
            String sql = "BEGIN TRANSACTION;\n" +
                    "CREATE TABLE IF NOT EXISTS \"BurgerIngredients\" (\n" +
                    "\t\"burger_ingredient_id\"\tINTEGER NOT NULL UNIQUE,\n" +
                    "\t\"burger_ingredient_name\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\t\"burger_ingredient_amount\"\tINTEGER NOT NULL,\n" +
                    "\t\"burger_ingredient_price\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"burger_ingredient_id\" AUTOINCREMENT)\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"ShakeIngredients\" (\n" +
                    "\t\"shake_ingredient_id\"\tINTEGER NOT NULL UNIQUE,\n" +
                    "\t\"shake_ingredient_name\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\t\"shake_ingredient_amount\"\tINTEGER NOT NULL,\n" +
                    "\t\"shake_ingredient_price\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"shake_ingredient_id\" AUTOINCREMENT)\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Burgers\" (\n" +
                    "\t\"burger_id\"\tTEXT NOT NULL,\n" +
                    "\t\"burger_ingredients\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"burger_id\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Shakes\" (\n" +
                    "\t\"shake_id\"\tTEXT NOT NULL,\n" +
                    "\t\"shake_ingredients\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"shake_id\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Orders\" (\n" +
                    "\t\"order_id\"\tTEXT NOT NULL,\n" +
                    "\t\"burger_id\"\tTEXT NOT NULL,\n" +
                    "\t\"shake_id\"\tTEXT NOT NULL,\n" +
                    "\tFOREIGN KEY(\"burger_id\") REFERENCES \"Burgers\"(\"burger_id\"),\n" +
                    "\tFOREIGN KEY(\"shake_id\") REFERENCES \"Shakes\"(\"shake_id\"),\n" +
                    "\tPRIMARY KEY(\"order_id\",\"burger_id\",\"shake_id\")\n" +
                    ");\n" +
                    "COMMIT;\n";
            statement.executeLargeUpdate(sql);
            disconnect(dbConenction);
            System.out.println("New database successfully created!");
        } catch(Exception e){
            System.err.println("Failed to create database: " + e.getMessage());
        }
    }

    public static Boolean checkIngredient(int ingredientID, int amountToServe,FoodType type) {
        try{
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String sql = "SELECT " + type.toString().toLowerCase() +"_ingredient_amount FROM \"" + type.toString() +"Ingredients\" WHERE \"" + type.toString().toLowerCase() + "_ingredient_id\" = " + ingredientID;
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            int amount = resultSet.getInt(1);
            if(amountToServe - amount < 0){
                disconnect(connection);
                System.out.println("Not enough ingredients to serve!");
                return false;
            }
            else{
                disconnect(connection);
                System.out.println("Ingredient can be served, remaining amount: " + (amountToServe - amount));
                return true;
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
    }
}
