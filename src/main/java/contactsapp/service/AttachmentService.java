package contactsapp.service;

import contactsapp.command.Command;
import contactsapp.core.entity.Attachment;
import contactsapp.core.entity.Phone;
import contactsapp.dao.AttachmentDao;
import contactsapp.dao.DaoException;
import contactsapp.dao.connectionmanager.ConManager;
import contactsapp.dao.connectionmanager.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttachmentService implements Service<Attachment>
{
    private AttachmentDao dao = new AttachmentDao();
    private ConManager connectionManager;

    public AttachmentService() throws IOException {
        connectionManager = ConnectionManager.getInstance();
    }

    @Override
    public void delete(List<Attachment> list) throws DaoException {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Attachment attachment : list) {
                    dao.delete(connection, attachment);
                }
                connection.commit();
                connection.setAutoCommit(false);
            } catch (DaoException e) {
                connection.rollback();
                throw new DaoException("can't delete all attachments");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Attachment getById(Number id) {
        Attachment attachment = null;
        try(Connection connection = connectionManager.getConnection()) {
            attachment = dao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attachment;
    }

    public List<Attachment> getByOwnerId(Integer ownerId){
        List<Attachment> attachments = new ArrayList<>();
        try(Connection connection = connectionManager.getConnection()){
            attachments = dao.getByOwnerId(connection, ownerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attachments;
    }

    @Override
    public List<Attachment> selectAll() {
        return null;
    }

    @Override
    public void update(Attachment attachment) {
        try(Connection connection = connectionManager.getConnection()){
            dao.update(connection, attachment);
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Attachment attachment) {
        try(Connection connection = connectionManager.getConnection()){
            dao.insert(connection, attachment);
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLastInsertedId() {
        int result = 0;
        try(Connection connection = connectionManager.getConnection()){
            result = dao.getLastInserted(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
