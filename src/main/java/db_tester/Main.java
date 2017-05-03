package db_tester;

import java.sql.SQLException;

/**
 * Created by malaka on 5/3/17.
 */
public class Main {
    public static void main(String[] args) {
        QueryDB qdb = new QueryDB();
        qdb.createQueueContent();
        qdb.insertToQueueContent();
        qdb.getQueueContent();
        qdb.closeConnection();
    }
}
