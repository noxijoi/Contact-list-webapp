package contactsapp.dao;

import contactsapp.core.entity.Address;
import contactsapp.core.entity.Contact;
import contactsapp.core.entity.FullName;
import contactsapp.core.entity.Identified;
import contactsapp.dao.daobilder.ContactBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDao extends AbstractDao<Contact, Integer> {

    public ContactDao(){
        super(new ContactBuilder());
    }



    @Override
    public List getAll(Connection connection) throws SQLException {
        return super.getAll(connection);
    }

    @Override
    public Contact getById(Connection connection, Number number) throws  SQLException {
        return  super.getById(connection,number);
    }

    @Override
    public Contact insert(Connection connection, Identified object) throws DaoException, SQLException {
        return  super.insert(connection, object);
    }

    @Override
    public void update(Connection connection, Identified object) throws DaoException, SQLException {
        super.update(connection, object);
    }

    @Override
    public void delete(Connection connection, Identified object) throws DaoException, SQLException {
        super.delete(connection, object);
    }



    @Override
    protected void prepareDeleteStatement(PreparedStatement statement, Identified object) throws SQLException {
        int id = object.getId();
        statement.setInt(1, id);
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Identified object) throws SQLException {
        Contact contact = (Contact) object;
        FullName name = contact.getFullName();
        statement.setString(1, name.getFirstName());
        statement.setString(2, name.getLastName());
        statement.setString(3, name.getParentName());
        Date sqlDate = null;
        if(contact.getBirthDate() != null){
           sqlDate =  Date.valueOf(contact.getBirthDate());
        }
        statement.setDate(4, sqlDate);
        statement.setString(5,contact.getSex().toString());
        statement.setString(6, contact.getNationality());
        statement.setString(7, contact.getMaritalStatus().name().toLowerCase());
        statement.setString(8, contact.getWebsite());
        statement.setString(9, contact.getEmail());
        statement.setString(10, contact.getCompany());
        Address address = contact.getAddress();
        statement.setString(11, address.getCountry());
        statement.setString(12, address.getCity());
        statement.setString(13, address.getStreet());
        statement.setString(14, address.getHouse());
        statement.setInt(15,address.getIndex());
        statement.setString(16, contact.getAvatar().getPath());
        statement.setInt(17,contact.getId());
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Identified object) throws SQLException {
        Contact contact = (Contact) object;
        FullName name = contact.getFullName();
        statement.setString(1, name.getFirstName());
        statement.setString(2, name.getLastName());
        statement.setString(3, name.getParentName());
        Date sqlDate = null;
        if(contact.getBirthDate()!= null) {
            sqlDate = Date.valueOf(contact.getBirthDate());
        }
        statement.setDate(4, sqlDate);
        statement.setString(5,contact.getSex().toString());
        statement.setString(6, contact.getNationality());
        statement.setString(7, contact.getMaritalStatus().name().toLowerCase());
        statement.setString(8, contact.getWebsite());
        statement.setString(9, contact.getEmail());
        statement.setString(10, contact.getCompany());
        Address address = contact.getAddress();
        statement.setString(11, address.getCountry());
        statement.setString(12, address.getCity());
        statement.setString(13, address.getStreet());
        statement.setString(14, address.getHouse());
        statement.setInt(15,address.getIndex());
        statement.setString(16, contact.getAvatar().getPath());
    }



    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM contact WHERE id = ?";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE contact SET " +
                "f_name = ?, " +
                "l_name = ?, " +
                "p_name = ?, " +
                "b_date = ?, " +
                "sex = ?, " +
                "nationality = ?, " +
                "marital_status = ?, " +
                "web_site = ?, " +
                "email = ?, " +
                "company = ?, " +
                "country = ?, " +
                "city = ?, " +
                "street = ?, " +
                "house_n = ?, " +
                "post_index = ?, " +
                "avatar = ? " +
                "WHERE id = ?";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO contact " +
                "(f_name, l_name, p_name," +
                " b_date, sex, nationality," +
                " marital_status, web_site, email, company," +
                " country, city, street, house_n, post_index, avatar)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM contact ORDER BY id";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM contact WHERE id = ?";
    }

    public List<Contact> getPage(Connection connection, int pageN, int pageSize) throws SQLException {
        int startN = (pageN - 1) * pageSize;
        String query = "SELECT * FROM contact ORDER BY id LIMIT ? , ? ";
        List<Contact> result = null;
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, startN);
            statement.setInt(2, pageSize);
            ResultSet rs = statement.executeQuery();
            result = builder.buildList(rs);
        }
        return result;
    }

    public int getTableSize(Connection connection) throws SQLException {
        int result = 0;
        String query = "SELECT COUNT(*) from contact";
        try(Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            result = rs.getInt("count(*)");
        }
        return result;
    }

    public List<Contact> getContactsBorn(Connection connection, int month, int day) throws SQLException {
        List<Contact> contactList = new ArrayList<>();
        String query = "SELECT * FROM contact WHERE EXTRACT(MONTH FROM b_date) = ? AND EXTRACT(MONTH FROM b_date) = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, month);
            statement.setInt(2, day);
            ResultSet rs = statement.executeQuery();
            contactList = builder.buildList(rs);

        }
        return contactList;
    }

    public int getTableSize(Connection connection, String query) throws SQLException {
        int result = 0;
        try(Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            result = rs.getInt("count(*)");
        }
        return result;
    }
}
