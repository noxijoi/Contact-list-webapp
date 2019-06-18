package contactsapp.dao;

import contactsapp.core.entity.Address;
import contactsapp.core.entity.Contact;
import contactsapp.core.entity.FullName;
import contactsapp.dao.daobilder.ContactBuilder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ContactDao extends AbstractDao<Contact, Integer> {

    public ContactDao(){
        super(new ContactBuilder());
    }

    @Override
    public List getAll(Connection connection) throws DaoException {
        return super.getAll(connection);
    }

    @Override
    public Contact getById(Connection connection, Number number) throws DaoException {
        return (Contact) super.getById(connection,number);
    }

    @Override
    public Contact insert(Connection connection, Identified object) throws DaoException {
        return (Contact) super.insert(connection, object);
    }

    @Override
    public void update(Connection connection, Identified object) throws DaoException {
        super.update(connection, object);
    }

    @Override
    public void delete(Connection connection, Identified object) throws DaoException {
        super.delete(connection, object);
    }

    @Override
    protected void prepareDeleteStatement(PreparedStatement statement, Identified object) throws SQLException {
        int id = object.getId();
        statement.setInt(1, id);

    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Identified object) {
        throw new NoSuchMethodError();
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Identified object) throws SQLException {
        Contact contact = (Contact) object;
        FullName name = contact.getFullName();
        statement.setString(1, name.getFirstName());
        statement.setString(2, name.getLastName());
        statement.setString(3, name.getParentName());
        DateFormat format  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = format.format(contact.getBirthDate());
        Date sqlDate = Date.valueOf(date);
        statement.setDate(4, sqlDate);
        statement.setString(5,contact.getSex().getValue());
        statement.setString(6, contact.getNationality());
        statement.setString(7, contact.getMaritalStatus().name().toLowerCase());
        statement.setString(8, contact.getWebsite());
        statement.setString(9, contact.getEmail());
        statement.setString(10, contact.getCompanyName());
        Address address = contact.getAddress();
        statement.setString(11, address.getCountry());
        statement.setString(12, address.getCity());
        statement.setString(13, address.getStreet());
        statement.setString(13, address.getHomeNumber());
        statement.setInt(14,address.getIndex());

    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM contact WHERE contact_id = ?";
    }

    @Override
    protected String getUpdateQuery() {
        //не понятно как изменять все поля чтоли переписывать
        throw new  NoSuchMethodError();
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO contact " +
                "(f_name, l_name, p_name, b_date, sex, nationality, marital_status, web_site, email, company," +
                " country, city, street, house_n, post_index)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM contact";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM contact WHERE id = ?";
    }

    public List<Contact> getPage(Connection connection, int recordsForPage, int lastRecordId) throws DaoException {
        String query = "SELECT * FROM contact WHERE contact_id > ? LIMIT ?;";
        List<Contact> contactList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, lastRecordId);
            statement.setInt(2, recordsForPage);
            contactList = exequtePreparedStatement(connection, statement);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return contactList;
    }
}
