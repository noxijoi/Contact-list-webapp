package contactsapp.dao;

import java.sql.SQLException;
import java.sql.Connection;


public interface ConManager {
    Connection getConnection() throws SQLException;
}
