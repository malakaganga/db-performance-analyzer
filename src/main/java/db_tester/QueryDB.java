package db_tester;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by malaka on 5/3/17.
 */
public class QueryDB {

    /**
     * String constants representing table names.
     */
    private static final String QUEUE_CONTENT = "QUEUE_CONTENT";

    /**
     * String constants representing table columns.
     */
    private static final String MESSAGE_ID = "MESSAGE_ID";
    private static final String MESSAGE_CONTENT = "MESSAGE_CONTENT";


    /**
     * Prepared statements to create,read, insert and update queue details.
     */
    private static final String CREATE_QUEUE_CONTENT_TABLE = "CREATE TABLE " + QUEUE_CONTENT + "(MESSAGE_ID INT, " +
            "MESSAGE_CONTENT VARCHAR(512) NOT NULL, PRIMARY KEY (MESSAGE_ID))";

    private static final String INSERT_QUEUE_CONTENT = "INSERT INTO " + QUEUE_CONTENT + " ("
            + MESSAGE_ID + ", "
            + MESSAGE_CONTENT + ") "
            + " VALUES (?,?)";

    private static final String GET_QUEUE_CONTENT = "SELECT * FROM " + QUEUE_CONTENT;

    /**
     * Connection to the database.
     */
    private Connection connection;

    /**
     * The instance of the DBConnector which reads and writes queues, bindings and subscriptions
     */
    private DBConnector connector;

    /**
     * Initializes the processor with properties given in config.properties file.
     */
    public QueryDB() {

        Properties prop = new Properties();
        try {

            // load the properties file
            File configFile = new File("config.properties");
            FileReader reader = new FileReader(configFile);
            prop.load(reader);
            connector = new DBConnector(prop);
            connection = connector.getConnection();

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * Create table queue content.
     */
    public void createQueueContent() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUEUE_CONTENT_TABLE);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert data into table queue content.
     */
    public void insertToQueueContent() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUEUE_CONTENT);
            for (int i = 0; i < 1000; i++) {
                String messageContent = UUID.randomUUID().toString();
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, messageContent);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get queue content from Queue Content.
     */
    public void getQueueContent() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_QUEUE_CONTENT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int queueName = resultSet.getInt(MESSAGE_ID);
                String queueContent = resultSet.getString(MESSAGE_CONTENT);
                System.out.println("Id = "+queueName+" Content = "+queueContent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close connection.
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
