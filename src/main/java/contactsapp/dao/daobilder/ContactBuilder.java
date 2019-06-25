package contactsapp.dao.daobilder;

import contactsapp.core.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactBuilder implements DaoBuilder<Contact> {
    @Override
    public Contact buildSingle(ResultSet rs) throws SQLException {
        Contact contact =  new Contact();
        FullName fullName = builtFullName(rs);
        Address addr = buildAddress(rs);
        contact.setAddress(addr);
        contact.setFullName(fullName);
        contact.setId(rs.getInt("id"));
        contact.setEmail(rs.getString("email"));
        contact.setBirthDate(rs.getDate("b_date"));
        contact.setSex(Sex.valueOf(rs.getString("sex").toUpperCase()));
        contact.setNationality(rs.getString("nationality"));
        contact.setMaritalStatus(MaritalStatus.valueOf(rs.getString("marital_status").toUpperCase()));
        contact.setWebsite(rs.getString("web_site"));
        contact.setEmail(rs.getString("email"));
        contact.setCompany(rs.getString("company"));

        return contact;
    }

    private FullName builtFullName(ResultSet rs) throws SQLException {
        FullName fullName = new FullName();
        fullName.setFirstName(rs.getString("f_name"));
        fullName.setLastName(rs.getString("l_name"));
        fullName.setParentName(rs.getString("p_name"));
        return fullName;
    }

    private Address buildAddress(ResultSet rs) throws SQLException {
        Address address =  new Address();
        address.setCountry(rs.getString("country"));
        address.setCity(rs.getString("city"));
        address.setStreet(rs.getString("street"));
        address.setHouse(rs.getString("house_n"));
        address.setIndex(rs.getInt("post_index"));
        return address;
    }

    @Override
    public List<Contact> buildList(ResultSet rs) throws SQLException {
        List<Contact> contactList =  new ArrayList<>();
        while (rs.next()){
            contactList.add(buildSingle(rs));
        }
        return contactList;
    }
}
