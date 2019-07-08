package contactsapp.service;

import contactsapp.core.entity.Contact;
import contactsapp.core.entity.Phone;
import contactsapp.dao.DaoException;
import contactsapp.dao.PhoneDao;
import contactsapp.dao.connectionmanager.ConManager;
import contactsapp.dao.connectionmanager.ConnectionManager;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneService implements Service<Phone> {
    private PhoneDao dao;
    private ConManager connectionManager;

    public PhoneService() throws IOException {
        connectionManager = ConnectionManager.getInstance();
        dao = new PhoneDao();
    }

    @Override
    public Phone getById(Number id) throws SQLException {
        Phone phone = null;
        try(Connection connection = connectionManager.getConnection()) {
            phone = dao.getById(connection, id);
        }
        return phone;
    }

    @Override
    public void delete(List<Phone> list) throws DaoException {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Phone phone : list) {
                    dao.delete(connection, phone);
                }
                connection.commit();
                connection.setAutoCommit(false);
            } catch (DaoException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new DaoException("can't delete all phones "  + e );
                }

            } catch (SQLException e) {
                throw new DaoException(e);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Phone> getByOwnerId(Integer ownerId) throws DaoException {
        List<Phone> phones = new ArrayList<>();
        try(Connection connection = connectionManager.getConnection()){
            phones = dao.getByOwnerId(connection, ownerId);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return phones;
    }
    @Override
    public List<Phone> selectAll() {
        return null;
    }

    @Override
    public void update(Phone phone) throws  DaoException {
        try(Connection connection = connectionManager.getConnection()){
            dao.update(connection, phone);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void insert(Phone phone) throws DaoException {

        try(Connection connection = connectionManager.getConnection()){
            dao.insert(connection, phone);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
