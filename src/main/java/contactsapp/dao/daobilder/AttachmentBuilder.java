package contactsapp.dao.daobilder;

import contactsapp.core.entity.Attachment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AttachmentBuilder implements DaoBuilder<Attachment> {
    @Override
    public Attachment buildSingle(ResultSet rs) throws SQLException {
        Attachment attachment = new Attachment();
        attachment.setId(rs.getInt("id"));
        attachment.setOwnerId(rs.getInt("owner_id"));
        attachment.setFilePath(rs.getString("path"));
        attachment.setDownloadTime(rs.getDate("download_time"));
        attachment.setFileName(rs.getString("name"));
        attachment.setComment(rs.getString("comment"));
        return attachment;
    }

    @Override
    public List<Attachment> buildList(ResultSet rs) throws SQLException {
        List<Attachment> attachmentList = new ArrayList<>();
        while (rs.next()) {
            attachmentList.add(buildSingle(rs));
        }
        return attachmentList;
    }
}
