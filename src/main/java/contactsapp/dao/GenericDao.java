package contactsapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface GenericDao<T extends Identified, PK extends Number> {

    T insert(Connection connection, T object) throws DaoException;
    T getById(Connection connection, PK pk) throws DaoException;
    void update (Connection connection, T object) throws DaoException;
    void delete(Connection connection, T object) throws  DaoException;
    List<T> getAll(Connection connection) throws DaoException;

    List<T> exequtePreparedStatement(Connection connection, PreparedStatement statement) throws SQLException;
}
