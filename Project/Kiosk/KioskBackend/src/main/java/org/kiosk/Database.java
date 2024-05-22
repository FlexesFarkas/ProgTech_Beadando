package org.kiosk;
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

    public static Boolean saveOrder(ArrayList<IFood> foods, String orderID) throws SQLException {

        try{
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String sql = "BEGIN TRANSACTION;";
            for (int i = 0; i < foods.size(); i++) {
                IFood food = foods.get(i);
                String[] ingredients = Arrays.copyOfRange(food.getDescription().split(","), 1, food.getDescription().split(",").length);
                StringBuilder ingredientString = new StringBuilder();
                for (int j = 0; j < ingredients.length; j++) {
                    ingredientString.append(ingredients[j]).append(",");
                }
                ingredientString.deleteCharAt(ingredientString.length() - 1);
                String[][] countedIngredients = countGroupedIngredients(ingredients, groupIngredients(ingredients));
                Connection connection1 = connect();
                Statement statement1 = connection1.createStatement();
                String sql1 = "BEGIN TRANSACTION;";
                for (int j = 0; j < countedIngredients.length; j++) {
                    if((countIngredient(countedIngredients[j][0]) - Integer.parseInt(countedIngredients[j][1])) < 0){
                        logger.warning("Not enough ingredients of " + countedIngredients[j][0]);
                        return false;
                    }
                    else {
                        int newAmount = countIngredient(countedIngredients[j][0]) - Integer.parseInt(countedIngredients[j][1]);
                        sql1 += "UPDATE Ingredients SET ingredient_amount = '" + newAmount + "' WHERE ingredient_name = '" + countedIngredients[j][0] + "';";
                        logger.info("Consumed "+ countedIngredients[j][1] +" pieces of " + countedIngredients[j][0]);
                    }
                }
                sql1 += "COMMIT;";
                statement1.executeUpdate(sql1);
                statement1.close();
                connection1.close();
                sql += "INSERT INTO Foods (ingredients, food_type, price) VALUES ('" +  ingredientString +"', '"+ food.getFoodType() +"', '" + getFoodPrice(countedIngredients) +"');";
            }
            sql += "COMMIT;";
            statement.executeLargeUpdate(sql);
            sql = "SELECT food_id FROM Foods ORDER BY food_id DESC LIMIT "+ String.valueOf(foods.size()) +";";
            ResultSet resultSet = statement.executeQuery(sql);
            sql = "";
            while(resultSet.next()){
                int foodID = resultSet.getInt(1);
                sql += "INSERT INTO Orders VALUES ('" + orderID + "', '" + foodID + "');";
            }
            statement.executeUpdate(sql);
            resultSet.close();
            statement.close();
            disconnect(connection);
            return true;
        }
        catch(Exception e){
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
                String sql = "INSERT OR IGNORE INTO Types (type) VALUES ('" + type.toString() + "')";
                statement.execute(sql);
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
                if (Arrays.stream(groupedIngredients).anyMatch(ingredients[i]::equals)) {
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
            //ide kell majd az, hogy megnézi mennyi pénz van a kártyán
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

                String sql = "INSERT INTO Ingredients (ingredient_name, ingredient_price, ingredient_amount) VALUES ('" + ingredientName + "', " + ingredientPrice + ",0)";
                statement.execute(sql);
            }
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
                foodTypes.add("fejlesztés alatt");
            }
            disconnect(dbConnection);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return foodTypes;
    }


}
