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


    public void delete(List<Contact> list) {
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
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Contact getById(Number id) {
        Contact contact = null;
        try (Connection connection = connectionManager.getConnection()) {
            contact = dao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
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

    public List<Contact> selectAll() {
        List<Contact> contacts = null;
        try(Connection connection = connectionManager.getConnection()){
            contacts = dao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public void update(Contact contact) {
        try (Connection connection = connectionManager.getConnection()) {
            dao.update(connection, contact);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void insert(Contact contact) {
        try (Connection connection = connectionManager.getConnection()) {
            dao.insert(connection, contact);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public int getRecordsNum() {
        int num = 0;
        try (Connection connection = connectionManager.getConnection()) {
            num = dao.getTableSize(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    public List<Contact> getContactsBorn(int month, int day) {
        List<Contact> contacts = new ArrayList<>();
        try(Connection connection = connectionManager.getConnection()){
            contacts = dao.getContactsBorn(connection, month, day);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

}
