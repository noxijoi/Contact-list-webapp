package contactsapp.dao;

import contactsapp.core.entity.Attachment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AttachmentDao extends AbstractDao<Attachment, Integer>{
    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM attachment WHERE attach_id = ?;";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE attachment SET path = ?" +
                "SET name = ?" +
                "SET download_time = ?" +
                "SET comment = ?;";
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
    private String getSelectByOwnerIdQuery(){
        return "SELECT * FROM attachment WHERE owner_id = ?";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM attachment WHERE attach_id = ?";
    }

    @Override
    protected void prepareDeleteStatement(PreparedStatement statement, Identified object) throws SQLException {
        Attachment attachment = (Attachment) object;
        statement.setInt(1,attachment.getId());
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Identified object) throws SQLException {
        Attachment attachment = (Attachment) object;
        statement.setString(1, attachment.getFilePath());
        statement.setString(2, attachment.getFileName());
        DateFormat format  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = format.format(attachment.getDownloadTime());
        Date sqlDate = Date.valueOf(date);
        statement.setDate(3, sqlDate);
        statement.setString(4, attachment.getComment());
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Identified object) throws SQLException {
        Attachment attachment = (Attachment) object;
        statement.setInt(1, attachment.getOwnerId());
        statement.setString(2, attachment.getFilePath());
        statement.setString(3, attachment.getFileName());
        DateFormat format  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = format.format(attachment.getDownloadTime());
        Date sqlDate = Date.valueOf(date);
        statement.setDate(4, sqlDate);
        statement.setString(5, attachment.getComment());
    }
}
