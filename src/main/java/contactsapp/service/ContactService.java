package contactsapp.service;

import contactsapp.core.entity.Contact;
import contactsapp.dao.*;
import contactsapp.dao.connectionmanager.ConManager;
import contactsapp.dao.connectionmanager.ConnectionManager;


import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ContactService implements Service<Contact> {
    private ContactDao dao;
    private ConManager connectionManager;

    public ContactService() throws IOException, NamingException {
        connectionManager = ConnectionManager.getInstance();
        dao = new ContactDao();
    }


    public void delete(List<Contact> list) throws DaoException, SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Contact contact : list) {
                    dao.delete(connection, contact);
                }
                connection.commit();
                connection.setAutoCommit(false);
            } catch (DaoException e) {
                connection.rollback();
                throw new DaoException("can't delete all contacts");
            }
        }

    }

    @Override
    public Contact getById(Number id) throws DaoException {
        Contact contact = null;
        try (Connection connection = connectionManager.getConnection()) {
            contact = dao.getById(connection, id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return contact;
    }

    public List<Contact> getPage(int pageN, int pageSize) throws DaoException {
        List<Contact> page = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            page = dao.getPage(connection, pageN, pageSize);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return page;
    }

    public List<Contact> selectAll() throws DaoException {
        List<Contact> contacts = null;
        try(Connection connection = connectionManager.getConnection()){
            contacts = dao.getAll(connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return contacts;
    }

    public void update(Contact contact) throws DaoException {
        try (Connection connection = connectionManager.getConnection()) {
            dao.update(connection, contact);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void insert(Contact contact) throws DaoException {
        try (Connection connection = connectionManager.getConnection()) {
            dao.insert(connection, contact);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public int getRecordsNum() throws DaoException {
        int num = 0;
        try (Connection connection = connectionManager.getConnection()) {
            num = dao.getTableSize(connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return num;
    }

    public List<Contact> getContactsBorn(int month, int day) throws DaoException {
        List<Contact> contacts = new ArrayList<>();
        try(Connection connection = connectionManager.getConnection()){
            contacts = dao.getContactsBorn(connection, month, day);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return contacts;
    }

    public List<Contact> executeSelectQuery(String query) throws DaoException {
        List<Contact> contacts = new ArrayList<>();
        try(Connection connection = connectionManager.getConnection()) {
            contacts = dao.exequtePreparedStatement(connection, connection.prepareStatement(query));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return contacts;
    }

    public int getRecordsNum(String query) throws DaoException {
        int num = 0;
        try (Connection connection = connectionManager.getConnection()) {
            num = dao.getTableSize(connection,query);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return num;
    }
}
