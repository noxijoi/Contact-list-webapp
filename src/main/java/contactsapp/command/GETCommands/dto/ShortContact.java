package contactsapp.command.GETCommands.dto;

import contactsapp.core.entity.Address;
import contactsapp.core.entity.FullName;

import java.util.Date;

public class ShortContact {
    public int id;
    public FullName fullName;
    public Address address;
    public Date birthDate;
}
