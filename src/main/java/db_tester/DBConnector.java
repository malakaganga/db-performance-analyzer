package db_tester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by malaka on 5/2/17.
 */
public class DBConnector {
    /**
     * Variables which hold the database connection parameters
     */
    private String DB_URL;
    private String USER;
    private String PASSWORD;

    /**
     * Connection to the database.
     */
    private Connection connection;

    /**
     * Initializes the DBConnector with properties given in config.properties file.
     *
     * @param properties the list of properties used to access database.
     */
    public DBConnector(Properties properties) {
        try {
            // Initialize the Driver and the connection parameters
            Class.forName(properties.getProperty("driverclassname")).newInstance();
            DB_URL = properties.getProperty("dburl");
            USER = properties.getProperty("dbuser");
            PASSWORD = properties.getProperty("dbpassword");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the connection object initialized by the provided properties.
     *
     * @return the connection object
     */
    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
