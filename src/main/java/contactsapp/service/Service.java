package contactsapp.service;

import contactsapp.dao.DaoException;

import java.sql.SQLException;
import java.util.List;

public interface Service<T> {
    void delete(List<T> list) throws SQLException, DaoException;
    T getById(Number id) throws SQLException, DaoException;
    List<T> selectAll() throws SQLException, DaoException;
    void update(T t) throws SQLException, DaoException;
    void insert(T t) throws SQLException, DaoException;
}
