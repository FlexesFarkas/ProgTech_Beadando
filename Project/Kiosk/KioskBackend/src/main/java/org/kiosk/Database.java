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
            System.out.println("Database not found! Creating a new database...");
            createDatabase();
        }
    }

    private static void createDatabase() {
        try{
            FoodType[] foodTypes = FoodType.values();
            Connection dbConenction = connect();
            Statement statement = dbConenction.createStatement();
            StringBuilder sql = new StringBuilder("BEGIN TRANSACTION;\n");
            for (FoodType foodType : foodTypes) {
                sql.append("CREATE TABLE IF NOT EXISTS \"").append(foodType).append("Ingredients\" (\n").append("\t\"").append(foodType.toString().toLowerCase()).append("_ingredient_id\" INTEGER NOT NULL UNIQUE,\n").append("\t\"").append(foodType.toString().toLowerCase()).append("_ingredient_name\" TEXT NOT NULL UNIQUE,\n").append("\t\"").append(foodType.toString().toLowerCase()).append("_ingredient_amount\" INTEGER NOT NULL,\n").append("\t\"").append(foodType.toString().toLowerCase()).append("_ingredient_price\" INTEGER NOT NULL,\n").append("\tPRIMARY KEY(\"").append(foodType.toString().toLowerCase()).append("_ingredient_id\" AUTOINCREMENT)\n").append(");\n");
            }
            sql.append("CREATE TABLE IF NOT EXISTS \"Orders\" (\n" + "\t\"order_id\"\tTEXT NOT NULL,\n" + "\t\"food_id\"\tTEXT NOT NULL,\n" + "\tPRIMARY KEY(\"order_id\",\"food_id\"\n" + "));\n" + "COMMIT;\n");
            statement.executeLargeUpdate(sql.toString());
            disconnect(dbConenction);
            System.out.println("New database successfully created!");
        } catch(Exception e){
            System.err.println("Failed to create database: " + e.getMessage());
        }
    }

    public static Boolean checkIngredient(int ingredientID, IngredientType type) {
        try{
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String sql = "SELECT " + type.toString().toLowerCase() +"_ingredient_amount FROM \"" + type.toString() +"Ingredients\" WHERE \"" + type.toString().toLowerCase() + "_ingredient_id\" = " + ingredientID;
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            int amount = resultSet.getInt(1);
            if(amount == 0){
                disconnect(connection);
                System.out.println("Ingredient not found");
                return false;
            }
            else{
                disconnect(connection);
                System.out.println("Ingredient found. Amount: " + amount);
                return true;
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
    }
}
