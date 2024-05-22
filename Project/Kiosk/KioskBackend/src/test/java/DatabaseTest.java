import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kiosk.Database;
import org.kiosk.FoodType;
import org.kiosk.exceptions.DatabaseFailedToResetException;
import org.kiosk.exceptions.NotEnoughIngredietnsException;
import org.kiosk.food.Food;
import org.kiosk.food.GenericIngredient;
import org.kiosk.food.IFood;
import org.kiosk.food.IngridientDecorator;
import org.kiosk.food.decorators.Cheese;
import org.kiosk.food.decorators.Cucumber;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

class DatabaseTest {
    private DatabaseTest() {
    }
/*
    private Connection getConnection() throws SQLException {
        System.out.println("Connecting to database...");
        return DriverManager.getConnection("jdbc:sqlite:./test_db.sqlite");
    }
    private void createTestDatabase() throws SQLException {
        Connection test = getConnection();
        Statement statement = test.createStatement();
        String sql = """
                BEGIN TRANSACTION;
                CREATE TABLE IF NOT EXISTS "Ingredients" (
                \t"ingredient_id"\tINTEGER NOT NULL UNIQUE,
                \t"ingredient_name"\tTEXT NOT NULL UNIQUE,
                \t"ingredient_price"\tINTEGER NOT NULL,
                \t"ingredient_amount"\tINTEGER NOT NULL,
                \tPRIMARY KEY("ingredient_id" AUTOINCREMENT)
                );
                CREATE TABLE IF NOT EXISTS "Orders" (
                \t"order_id"\tTEXT NOT NULL,
                \t"food_id"\tINTEGER NOT NULL UNIQUE,
                \tPRIMARY KEY("order_id","food_id"),
                \tFOREIGN KEY("food_id") REFERENCES "Foods"("food_id")
                );
                CREATE TABLE IF NOT EXISTS "Types" (
                \t"type"\tTEXT NOT NULL UNIQUE,
                \tPRIMARY KEY("type")
                );
                CREATE TABLE IF NOT EXISTS "IngredientTypes" (
                \t"ingredient_id"\tINTEGER NOT NULL,
                \t"ingredient_type"\tTEXT NOT NULL,
                \tFOREIGN KEY("ingredient_id") REFERENCES "Ingredients"("ingredient_id"),
                \tFOREIGN KEY("ingredient_type") REFERENCES "Types"("type"),
                \tPRIMARY KEY("ingredient_id","ingredient_type")
                );
                CREATE TABLE IF NOT EXISTS "Foods" (
                \t"food_id"\tINTEGER NOT NULL UNIQUE,
                \t"ingredients"\tTEXT NOT NULL,
                \t"food_type"\tTEXT NOT NULL,
                \t"price"\tINTEGER NOT NULL,
                \tFOREIGN KEY("food_type") REFERENCES "Types"("type"),
                \tPRIMARY KEY("food_id" AUTOINCREMENT)
                );
                COMMIT;
                """;
        statement.executeLargeUpdate(sql);
        statement.close();
        test.close();
    }
    private void populateIngredientsFromDecorators() throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Connection connection = getConnection();
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
        statement.close();
        connection.close();
    }
    private void generateType() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        FoodType[] types = FoodType.values();
        for (FoodType type : types) {
            String sql = "INSERT OR IGNORE INTO Types (type) VALUES ('" + type.toString() + "')";
            statement.execute(sql);
        }
        connection.close();
    }
*/
    @Test
    void returnSpecificIngredient() throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ArrayList<GenericIngredient> expected = new ArrayList<>();
        expected.add(new GenericIngredient("Cheese", 200));
        expected.add(new GenericIngredient("Tomato", 100));
        expected.add(new GenericIngredient("Cucumber", 150));
        ArrayList<GenericIngredient> actual = Database.returnSpecificIngredients(FoodType.Burger);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void saveOrder() throws ClassNotFoundException, SQLException {
        ArrayList<IFood> foods = new ArrayList<>();
        UUID orderId = UUID.randomUUID();
        foods.add(new Cheese( new Food(FoodType.Burger)));
        Assertions.assertTrue(Database.saveOrder(foods, orderId.toString()));
    }

    @Test
    void saveOrdersTooMuchIngredients() throws ClassNotFoundException, SQLException, NotEnoughIngredietnsException {
        ArrayList<IFood> foods = new ArrayList<>();
        UUID orderId = UUID.randomUUID();
        foods.add(new Cucumber( new Cucumber( new Food(FoodType.Burger))));
        Assertions.assertFalse(Database.saveOrder(foods, orderId.toString()));
    }

    @Test
    void connectAndDisconnectFromDatabase() throws SQLException {
        Connection connection = Database.connect();
        Database.disconnect(connection);
        Assertions.assertTrue(connection.isClosed());
    }

    @Test
    void deleteDatabaseWhileConnectionIsActive() throws DatabaseFailedToResetException, SQLException {
        Connection connection = Database.connect();
        Assertions.assertThrows(DatabaseFailedToResetException.class, Database::resetDatabase);
    }
}
