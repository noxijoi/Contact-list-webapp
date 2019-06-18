package contactsapp.dao;

import contactsapp.dao.connectionmanager.StupidConnectionManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class StupidConnectionManagerTest {

    @Test
    void getConnection() throws SQLException {
        StupidConnectionManager manager = new StupidConnectionManager();
        Connection connection = manager.getConnection();
        Assertions.assertTrue(connection.isValid(1000));
    }
}