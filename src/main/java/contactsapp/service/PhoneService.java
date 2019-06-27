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
    public Phone getById(Number id) {
        Phone phone = null;
        try(Connection connection = connectionManager.getConnection()) {
            phone = dao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return phone;
    }

    @Override
    public void delete(List<Phone> list) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Phone phone : list) {
                    dao.delete(connection, phone);
                }
                connection.commit();
                connection.setAutoCommit(false);
            } catch (DaoException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Phone> getByOwnerId(Integer ownerId){
        List<Phone> phones = new ArrayList<>();
        try(Connection connection = connectionManager.getConnection()){
            phones = dao.getByOwnerId(connection, ownerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phones;
    }
    @Override
    public List<Phone> select() {
        return null;
    }

    @Override
    public void update(Phone phone) {
        try(Connection connection = connectionManager.getConnection()){
            dao.update(connection, phone);
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Phone phone) {

        try(Connection connection = connectionManager.getConnection()){
            dao.insert(connection, phone);
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
