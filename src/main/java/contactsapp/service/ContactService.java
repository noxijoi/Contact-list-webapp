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

public class ContactService implements Service<Contact>{
    private ContactDao dao;
    private ConManager connectionManager;

    public ContactService() throws IOException, NamingException {
        connectionManager = ConnectionManager.getInstance();
        dao = new ContactDao();
    }


    public void delete(List<Contact> list) {
        try(Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            for (Contact contact : list) {
                dao.delete(connection, contact);
            }
            connection.commit();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoException e) {

            e.printStackTrace();
        }

    }

    @Override
    public Contact getById(Number id) {
        Contact contact = null;
        try(Connection connection = connectionManager.getConnection()) {
            contact = dao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return contact;
    }

    public List<Contact> getPage(int recordsForPage, int lastRecordId) throws DaoException {
        List<Contact> page = new ArrayList<>();
        try(Connection connection = connectionManager.getConnection()) {
            dao.getPage(connection, recordsForPage);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return page;
    }

    public List<Contact> select() {
        return null;
    }

    public void update(Contact contact) {
        try(Connection connection = connectionManager.getConnection()) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Contact contact) {

    }
}
