package contactsapp.dao;

import contactsapp.dao.connectionmanager.ConnectionManager;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

class ConnectionManagerTest {

    @Test
    void shouldGetValidConnection() throws IOException, SQLException, NamingException {
        ConnectionManager manager = ConnectionManager.getInstance();
        try(Connection connection = manager.getConnection()) {
            Assert.assertTrue(connection.isValid(1000));
        }

    }
}