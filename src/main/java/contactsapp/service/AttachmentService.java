package contactsapp.service;

import contactsapp.core.entity.Attachment;
import contactsapp.dao.AttachmentDao;

import java.util.List;

public class AttachmentService implements Service<Attachment>
{
    AttachmentDao dao = new AttachmentDao();
    @Override
    public void delete(List<Attachment> list) {
        
    }

    @Override
    public Attachment getById(Number id) {
        return null;
    }

    public List<Attachment> getByOwnerId(Number ownerId){
            return null;
    }
    @Override
    public List<Attachment> select() {
        return null;
    }

    @Override
    public void update(Attachment attachment) {

    }

    @Override
    public void insert(Attachment attachment) {

    }
}
