package contactsapp.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StupidConnectionManagerTest {

    @Test
    void getConnection() throws SQLException {
        StupidConnectionManager manager = new StupidConnectionManager();
        Connection connection = manager.getConnection();
        Assertions.assertTrue(connection.isValid(1000));
    }
}