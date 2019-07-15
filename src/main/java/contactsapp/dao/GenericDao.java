package contactsapp.dao;

import contactsapp.core.entity.Identified;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T extends Identified, PK extends Number> {

    T insert(Connection connection, T object) throws DaoException, SQLException;
    T getById(Connection connection, PK pk) throws DaoException, SQLException;
    void update (Connection connection, T object) throws DaoException, SQLException;
    void delete(Connection connection, T object) throws DaoException, SQLException;
    List<T> getAll(Connection connection) throws DaoException, SQLException;

    List<T> exequtePreparedStatement(Connection connection, PreparedStatement statement) throws SQLException;
}
