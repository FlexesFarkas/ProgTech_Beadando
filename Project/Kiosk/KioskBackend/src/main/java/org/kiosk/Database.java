package org.kiosk;
import org.kiosk.food.Food;

import java.sql.*;
import java.io.File;
import java.util.logging.Logger;

public class Database {
    private static final String DATABASE_FILE = "./kiosk_db.sqlite";
    private static final String CONNECTION_URL = "jdbc:sqlite:" + DATABASE_FILE;
    private static  final Logger logger = Logger.getLogger(Database.class.getName());
    public static Connection connect(){
        try {
            Connection dbConnection = DriverManager.getConnection(CONNECTION_URL);
            logger.info("Connected to database");
            return dbConnection;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            return null;
        }
    }

    public static void disconnect(Connection dbConnection){
        try {
            dbConnection.close();
            logger.info("Disconnected from database");
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

    public static void checkIfDatabaseExists() {
        File file = new File(DATABASE_FILE);
        if(file.exists()){
            logger.info("Database found!");
        }
        else {
            logger.warning("Database not found!");
            createDatabase();
        }
    }

    private static void createDatabase() {
        try{
            logger.info("Attempting to create a new database...");
            FoodType[] foodTypes = FoodType.values();
            Connection dbConenction = connect();
            Statement statement = dbConenction.createStatement();
            String sql = "BEGIN TRANSACTION;\n" +
                    "CREATE TABLE IF NOT EXISTS \"Ingredients\" (\n" +
                    "\t\"ingredient_id\"\tINTEGER NOT NULL UNIQUE,\n" +
                    "\t\"ingredient_name\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\t\"ingredient_price\"\tINTEGER NOT NULL,\n" +
                    "\t\"ingredient_amount\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"ingredient_id\" AUTOINCREMENT)\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Orders\" (\n" +
                    "\t\"order_id\"\tTEXT NOT NULL,\n" +
                    "\t\"food_id\"\tINTEGER NOT NULL UNIQUE,\n" +
                    "\tPRIMARY KEY(\"order_id\",\"food_id\"),\n" +
                    "\tFOREIGN KEY(\"food_id\") REFERENCES \"Foods\"(\"food_id\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Types\" (\n" +
                    "\t\"type\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\tPRIMARY KEY(\"type\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"IngredientTypes\" (\n" +
                    "\t\"ingredient_id\"\tINTEGER NOT NULL,\n" +
                    "\t\"ingredient_type\"\tTEXT NOT NULL,\n" +
                    "\tFOREIGN KEY(\"ingredient_id\") REFERENCES \"Ingredients\"(\"ingredient_id\"),\n" +
                    "\tFOREIGN KEY(\"ingredient_type\") REFERENCES \"Types\"(\"type\"),\n" +
                    "\tPRIMARY KEY(\"ingredient_id\",\"ingredient_type\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Foods\" (\n" +
                    "\t\"food_id\"\tINTEGER NOT NULL UNIQUE,\n" +
                    "\t\"ingredients\"\tTEXT NOT NULL,\n" +
                    "\t\"food_type\"\tTEXT NOT NULL,\n" +
                    "\t\"price\"\tINTEGER NOT NULL,\n" +
                    "\tFOREIGN KEY(\"food_type\") REFERENCES \"Types\"(\"type\"),\n" +
                    "\tPRIMARY KEY(\"food_id\" AUTOINCREMENT)\n" +
                    ");\n" +
                    "COMMIT;\n";
            statement.executeLargeUpdate(sql);
            disconnect(dbConenction);
            logger.info("Database successfully created!");
        } catch(Exception e){
            logger.severe(e.getMessage());
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
                logger.warning("Not enough ingredients to serve!");
                return false;
            }
            else{
                disconnect(connection);
                logger.info("Ingredient can be served, remaining amount: " + (amountToServe - amount));
                return true;
            }
        }
        catch(Exception e){
            logger.severe(e.getMessage());
            return false;
        }
    }

    public static Boolean saveOrder(Food food, FoodType type, String orderID) {
        try{
            String foodString = food.toString();
            for (int i = 0; i < foodString.length(); i++) {
                if(!checkIngredient(i, Character.getNumericValue(foodString.charAt(i)) ,type)) {
                    logger.info("Order could not be saved, not enough ingredients!");
                    return false;
                }
            }
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO Orders VALUES (\"" + orderID + "\", \""+ foodString +"\")";
            if (statement.execute(sql)) {
                logger.info("Order successfully saved");
                disconnect(connection);
                return true;
            }
            else {
                logger.info("Order could not be saved");
                disconnect(connection);
                return false;
            }
        }
        catch(Exception e){
            logger.severe(e.getMessage());
            return false;
        }
    }
    public static boolean processPayment(String paymentDetails) {
        try {
            Connection dbConnection = connect();
            if (paymentDetails == null || paymentDetails.length() != 16) {
                logger.warning("Invalid payment details.");
                disconnect(dbConnection);
                return false;
            }
            //ide kell majd az, hogy megnézi mennyi pénz van a kártyán
            logger.info("Payment processed successfully.");
            disconnect(dbConnection);
            return true;

        } catch (Exception e) {
            logger.severe(e.getMessage());
            return false;
        }
    }

}
