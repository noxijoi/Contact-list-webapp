package contactsapp.dao;

import contactsapp.dao.connectionmanager.HikariConnectionManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

class HikariConnectionManagerTest {

    @Test
    void getConnection() throws SQLException, IOException {
        HikariConnectionManager manager = HikariConnectionManager.getInstance();
        Connection connection = manager.getConnection();
        Assertions.assertTrue(((Connection) connection).isValid(1000));
    }
}