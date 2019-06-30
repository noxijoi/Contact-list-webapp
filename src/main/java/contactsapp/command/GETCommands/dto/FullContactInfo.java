package contactsapp.command.GETCommands.dto;

import contactsapp.core.entity.Attachment;
import contactsapp.core.entity.Contact;
import contactsapp.core.entity.Phone;

import java.util.List;

public class FullContactInfo {

    public Contact contact;
    public String decodedAvatar;
    public List<Phone> phones;
    public List<Attachment> attachs;
    public FullContactInfo(Contact contact, List<Phone> phones, List<Attachment> attachs, String decodedAvatar) {
        this.contact = contact;
        this.phones = phones;
        this.attachs = attachs;
        this.decodedAvatar = decodedAvatar;
    }
}
