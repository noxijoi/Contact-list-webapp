package contactsapp.dao.connectionmanager;

import contactsapp.dao.connectionmanager.ConManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StupidConnectionManager implements ConManager {
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/Maskaliova_contacts", "maskaliova", "zaqwsx");
    }
}
