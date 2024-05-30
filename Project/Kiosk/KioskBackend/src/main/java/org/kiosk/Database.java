package org.kiosk;
import org.kiosk.exceptions.DatabaseFailedToResetException;
import org.kiosk.exceptions.NotEnoughIngredientsException;
import org.kiosk.exceptions.NotEnoughIngredietnsException;
import org.kiosk.food.GenFood;
import org.kiosk.food.IFood;
import org.kiosk.food.GenericIngredient;
import org.kiosk.food.IngridientDecorator;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.sql.*;
import java.io.File;
import java.util.*;
import java.util.logging.Logger;

public class Database {
    private static final String DATABASE_FILE = "./kiosk_db.sqlite";
    private static final String CONNECTION_URL = "jdbc:sqlite:" + DATABASE_FILE;
    private static final int STOCK = 100;
    private static final Logger logger = Logger.getLogger(Database.class.getName());
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

    public static Boolean saveOrder(ArrayList<IFood> foods, String orderID) {
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String sql = "BEGIN TRANSACTION;";
            for (IFood food : foods) {
                String description = food.getDescription();
                String[] ingredients = Arrays.copyOfRange(description.split(","), 1, description.split(",").length);
                String[][] countedIngredients = countGroupedIngredients(ingredients, groupIngredients(ingredients));

                for (String[] ingredientInfo : countedIngredients) {
                    String ingredientName = ingredientInfo[0];
                    int requiredAmount = Integer.parseInt(ingredientInfo[1]);
                    int availableAmount = countIngredient(ingredientName);
                    if (availableAmount < requiredAmount) {
                        throw new NotEnoughIngredientsException("Not enough " + ingredientName + " available.");
                    }
                }

                for (String[] ingredientInfo : countedIngredients) {
                    String ingredientName = ingredientInfo[0];
                    int requiredAmount = Integer.parseInt(ingredientInfo[1]);
                    int newAmount = countIngredient(ingredientName) - requiredAmount;
                    sql += "UPDATE Ingredients SET ingredient_amount = '" + newAmount + "' WHERE ingredient_name = '" + ingredientName + "';";
                }

                String ingredientString = String.join(",", ingredients);
                double foodPrice = getFoodPrice(countedIngredients);
                String foodType = food.getFoodType().toString();
                sql += "INSERT INTO Foods (ingredients, food_type, price) VALUES ('" + ingredientString + "', '" + foodType + "', '" + foodPrice + "');";
            }

            sql += "COMMIT;";
            statement.executeLargeUpdate(sql);
            ResultSet resultSet = statement.executeQuery("SELECT food_id FROM Foods ORDER BY food_id DESC LIMIT " + foods.size() + ";");
            sql = "BEGIN TRANSACTION;";
            while (resultSet.next()) {
                int foodID = resultSet.getInt(1);
                sql += "INSERT INTO Orders VALUES ('" + orderID + "', '" + foodID + "');";
            }
            sql += "COMMIT;";
            statement.executeUpdate(sql);

            resultSet.close();
            statement.close();
            disconnect(connection);
            return true;
        } catch (NotEnoughIngredientsException e) {
            logger.severe(e.getMessage());
            return false;
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return false;
        }
    }


    private static int countIngredient(String ingredient){
        try{
                Connection connection2 = connect();
                Statement statement = connection2.createStatement();
                String sql = "SELECT ingredient_amount FROM Ingredients WHERE ingredient_name = '" + ingredient + "';";
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                logger.info("Number of "+ ingredient +" found: " + resultSet.getInt(1));
                int result = resultSet.getInt(1);
                resultSet.close();
                statement.close();
                disconnect(connection2);
                return result;
            }
        catch (Exception e){
                logger.severe(e.getMessage());
                return 0;
        }
    }

    public static Boolean GenerateType() {
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            FoodType[] Ftypes = FoodType.values();
            for (FoodType type : Ftypes) {
                String sql = "INSERT INTO Types VALUES ('" + type + "')";
                statement.executeUpdate(sql);
            }
            disconnect(connection);
            logger.info("Types successfully generated in the database!");
            return true;
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return false;
        }
    }

    private static int getFoodPrice(String[][] ingredients) {
        try{
            int totalPrice = 0;
            Connection connection2 = connect();
            Statement statement = connection2.createStatement();
            for (int i = 0; i < ingredients.length; i++) {
                String sql = "SELECT ingredient_price FROM Ingredients WHERE ingredient_name='" + ingredients[i][0] + "';";
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                int price = resultSet.getInt(1);
                totalPrice += price * Integer.parseInt(ingredients[i][1]);
                resultSet.close();
            }
            statement.close();
            disconnect(connection2);
            return totalPrice;
        }
        catch(Exception e){
            logger.severe(e.getMessage());
            return 0;
        }
    }

    private static String[] groupIngredients(String[] ingredients) {
        int size = countDistinctIngredients(ingredients);
        String[] groupedIngredients = new String[size];
        int i = 0;
        for (int j = 0; j < ingredients.length; j++) {
            if (!Arrays.stream(groupedIngredients).anyMatch(ingredients[j]::equals)) {
                groupedIngredients[i] = ingredients[j];
                i++;
            }
        }
        return groupedIngredients;
    }

    private static String[][] countGroupedIngredients(String[] ingredients, String[] groupedIngredients) {
        String[][] returnValue = new String[groupedIngredients.length][2];
        for (int i = 0; i < groupedIngredients.length; i++) {
            returnValue[i][0] = groupedIngredients[i];
            int counter = 0;
            for (int j = 0; j < ingredients.length; j++) {
                /*
                if (Arrays.stream(groupedIngredients).anyMatch(ingredients[i]::equals)) {
                    counter++;
                }
                 */
                if (groupedIngredients[i].equals(ingredients[j])) {
                    counter++;
                }
            }
            returnValue[i][1] = String.valueOf(counter);
        }
        return returnValue;
    }

    private static int countDistinctIngredients(String[] ingredients) {
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < ingredients.length; i++) {
            set.add(ingredients[i]);
        }
        return set.size();
    }

    public static boolean processPayment(String paymentDetails) {
        try {
            Connection dbConnection = connect();
            if (paymentDetails == null || paymentDetails.length() != 16) {
                logger.warning("Invalid payment details.");
                disconnect(dbConnection);
                return false;
            }
            logger.info("Payment processed successfully.");
            disconnect(dbConnection);
            return true;

        } catch (Exception e) {
            logger.severe(e.getMessage());
            return false;
        }
    }

    public static ArrayList<GenericIngredient> returnSpecificIngredients(FoodType type) {
        try{
            ArrayList<GenericIngredient> ingredients = new ArrayList<>();
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String sql = "SELECT ingredient_name, ingredient_price FROM Ingredients INNER JOIN IngredientTypes ON Ingredients.ingredient_id = IngredientTypes.ingredient_id WHERE ingredient_type = '" + type.toString() + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String ingredientName = resultSet.getString(1);
                double ingredientPrice = resultSet.getDouble(2);
                GenericIngredient ingredient = new GenericIngredient(ingredientName, ingredientPrice);
                ingredients.add(ingredient);
            }
            resultSet.close();
            statement.close();
            disconnect(connection);
            logger.info("Ingredients of type " + type + " successfully retrieved.");
            return ingredients;
        }
        catch(Exception e){
            logger.severe(e.getMessage());
            return null;
        }
    }

    public static void populateIngredientsFromDecorators() {
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();

            ClassLoader classLoader = Database.class.getClassLoader();

            String packageName = "org.kiosk.food.decorators";

            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(packageName, classLoader))
                    .setScanners(new SubTypesScanner(false)));

            Set<Class<? extends IngridientDecorator>> decorators = reflections.getSubTypesOf(IngridientDecorator.class);

            for (Class<? extends IngridientDecorator> decorator : decorators) {
                String ingredientName = decorator.getSimpleName();
                int ingredientPrice = (int) decorator.getMethod("getIngredientPrice").invoke(null);
                int typeAmount = (int) decorator.getMethod("getIngredientTypesLength").invoke(null);

                String sql = "INSERT INTO Ingredients (ingredient_name, ingredient_price, ingredient_amount) VALUES ('" + ingredientName + "', " + ingredientPrice + ", "+ STOCK +");";
                statement.executeUpdate(sql);
                for (int i = 0; i < typeAmount; i++) {
                    FoodType ingredientType = (FoodType) decorator.getMethod("getIngredientTypes", int.class).invoke(null, i);
                    sql = "SELECT ingredient_id FROM Ingredients WHERE ingredient_name='" + ingredientName + "';";
                    ResultSet resultSet = statement.executeQuery(sql);
                    resultSet.next();
                    int ingredientId = resultSet.getInt(1);
                    resultSet.close();
                    sql = "INSERT INTO IngredientTypes VALUES ('" + ingredientId + "', '" + ingredientType + "');";
                    statement.executeUpdate(sql);
                }
            }
            statement.close();
            disconnect(connection);
            logger.info("Ingredients successfully populated in the database from IngredientDecorators!");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }


    public static List<String> getFoodTypes() {
        List<String> foodTypes = new ArrayList<>();
        try {
            Connection dbConnection = connect();
            Statement statement = dbConnection.createStatement();
            String sql = "SELECT type FROM Types";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                foodTypes.add(resultSet.getString("type"));
            }
            if (foodTypes.size()<4){
                while (foodTypes.size()!=4)
                foodTypes.add("-");
            }
            disconnect(dbConnection);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return foodTypes;
    }

    public static void resetDatabase() throws DatabaseFailedToResetException {
        File file = new File(DATABASE_FILE);
        if(file.delete()){
            logger.info("Database successfully reset.");
        }
        else {
            throw new DatabaseFailedToResetException();
        }
    }

    public static ArrayList<GenericIngredient> returnIndredientByFoodtype(String type) {
        try{
            ArrayList<GenericIngredient> ingredients = new ArrayList<>();
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String sql = "SELECT ingredient_name, ingredient_price FROM Ingredients INNER JOIN IngredientTypes ON Ingredients.ingredient_id = IngredientTypes.ingredient_id WHERE ingredient_type = '" + type + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String ingredientName = resultSet.getString(1);
                double ingredientPrice = resultSet.getDouble(2);
                GenericIngredient ingredient = new GenericIngredient(ingredientName, ingredientPrice);
                ingredients.add(ingredient);
            }
            resultSet.close();
            statement.close();
            disconnect(connection);
            logger.info("Ingredients of type " + type + " successfully retrieved.");
            if (ingredients.size()<5){
                while (ingredients.size()!=5){
                    ingredients.add(new GenericIngredient("-",0));
                }

            }
            return ingredients;
        }
        catch(Exception e){
            logger.severe(e.getMessage());
            return null;
        }
    }


    public static int returnIndredientCountByFoodtype(String type,int index) {
        try{
            ArrayList<GenericIngredient> ingredients = new ArrayList<>();
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String sql = "SELECT ingredient_name, ingredient_price, ingredient_amount FROM Ingredients INNER JOIN IngredientTypes ON Ingredients.ingredient_id = IngredientTypes.ingredient_id WHERE ingredient_type = '" + type + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String ingredientName = resultSet.getString(1);
                double ingredientPrice = resultSet.getDouble(2);
                int ingredientAmount = resultSet.getInt(3);
                GenericIngredient ingredient = new GenericIngredient(ingredientName, ingredientPrice,ingredientAmount);
                ingredients.add(ingredient);
            }
            resultSet.close();
            statement.close();
            disconnect(connection);
            logger.info("Ingredient amount of type " + ingredients.get(index).getName() + " successfully retrieved= "+ingredients.get(index).getAmount());
            return ingredients.get(index).getAmount();
        }
        catch(Exception e){
            logger.severe(e.getMessage());
            return 0;
        }
    }
    public static String returnIndredientNameByFoodtype(String type, int index) {
        try{
            ArrayList<String> ingredients = new ArrayList<>();
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String sql = "SELECT ingredient_name FROM Ingredients INNER JOIN IngredientTypes ON Ingredients.ingredient_id = IngredientTypes.ingredient_id WHERE ingredient_type = '" + type + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String ingredientName = resultSet.getString(1);
                ingredients.add(ingredientName);
            }
            resultSet.close();
            statement.close();
            disconnect(connection);
            logger.info("Ingredient "+ ingredients.get(index) +" of type " + type + " successfully found at " + index);
            return ingredients.get(index);
        }
        catch(Exception e){
            logger.severe(e.getMessage());
            return "";
        }
    }

    public static void ProcessPayment(List<GenFood> newfoods) {
        try {
            int temp = 0;
            for (GenFood food : newfoods) {
                if ((returnIndredientCountByFoodtype(food.getFoodtype(), temp) - food.getIngredientAmounts()[temp] >= 0)) {
                    if (!(returnIndredientByFoodtype(food.getFoodtype()).get(temp).getName().equals("-"))) {
                        Connection connection = connect();
                        Statement statement = connection.createStatement();
                        String sql = "UPDATE Ingredients SET ingredient_amount = ingredient_amount - " + food.getIngredientAmounts()[temp] +
                                " WHERE ingredient_id IN (SELECT ingredient_id FROM IngredientTypes WHERE ingredient_type = '" + food.getFoodtype() + "') " +
                                "AND ingredient_name = '" + returnIndredientNameByFoodtype(food.getFoodtype(), temp) + "'";
                        statement.executeUpdate(sql);
                        statement.close();
                        disconnect(connection);
                        logger.info("Successful ingredient update: " + food.getIngredientAmounts()[temp] + " " + food.getFoodtype());
                    }
                }
                temp++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int ProcessPrice(List<GenFood> newfoods) {
        try {
            int price = 0;
            int temp = 0;
            for (GenFood food : newfoods) {
                Connection connection = connect();
                Statement statement = connection.createStatement();
                String sql = "SELECT Ingredients.ingredient_price FROM Ingredients INNER JOIN IngredientTypes ON Ingredients.ingredient_id = IngredientTypes.ingredient_id " +
                        "WHERE IngredientTypes.ingredient_type = '" + food.getFoodtype() + "' " +
                        "AND Ingredients.ingredient_name = '" + returnIndredientNameByFoodtype(food.getFoodtype(), temp) + "'";
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int ingredientPrice = resultSet.getInt(1);
                    price+= (ingredientPrice*food.getIngredientAmounts()[temp]);

                }
                resultSet.close();
                statement.close();
                disconnect(connection);
                logger.info("Successful ingredient price check: " + food.getIngredientAmounts()[temp] + " " + food.getFoodtype());
                temp++;
            }
            return price;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}
