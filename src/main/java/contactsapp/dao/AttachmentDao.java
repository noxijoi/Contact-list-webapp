package contactsapp.dao;

import contactsapp.core.entity.Attachment;
import contactsapp.core.entity.Phone;
import contactsapp.dao.daobilder.AttachmentBuilder;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDao extends AbstractDao<Attachment, Integer>{

    public AttachmentDao(){
        super(new AttachmentBuilder());
    }


    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM attachment WHERE id = ?;";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE attachment SET path = ?" +
                "SET name = ?" +
                "SET download_time = ?" +
                "SET comment = ?" +
                "WHERe id = ?;";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO attachment (owner_id, path, name, download_time, comment)" +
                " VALUES (?, ?, ?, ?);";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM attachment";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM attachment WHERE attach_id = ?";
    }

    private String getSelectByOwnerIdQuery(){
        return "SELECT * FROM attachment WHERE owner_id = ?";
    }



    @Override
    protected void prepareDeleteStatement(PreparedStatement statement, Identified object) throws SQLException {
        statement.setInt(1, object.getId());
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Identified object) throws SQLException {
        Attachment attachment = (Attachment) object;
        statement.setString(1, attachment.getFilePath());
        statement.setString(2, attachment.getFileName());
        Date sqlDate = Date.valueOf(attachment.getDownloadTime());
        statement.setDate(3, sqlDate);
        statement.setString(4, attachment.getComment());
        statement.setInt(5, attachment.getId());
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Identified object) throws SQLException {
        Attachment attachment = (Attachment) object;
        statement.setInt(1, attachment.getOwnerId());
        statement.setString(2, attachment.getFilePath());
        statement.setString(3, attachment.getFileName());
        Date sqlDate = Date.valueOf(attachment.getDownloadTime());
        statement.setDate(4, sqlDate);
        statement.setString(5, attachment.getComment());
    }

    public List<Attachment> getByOwnerId(Connection connection, Integer ownerId) {
        String query ="SELECT * FROM attachment WHERE owner_id = ?";
        List<Attachment> result = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, ownerId);
            ResultSet rs = statement.executeQuery();
            result = builder.buildList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getLastInserted(Connection connection) {
        String query = "SELECT Last_insert_id() FROM attacment";
        Integer result = null;
        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            result = rs.getInt("Last_insert_id()");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
