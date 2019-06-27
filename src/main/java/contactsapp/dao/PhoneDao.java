package contactsapp.dao;

import contactsapp.core.entity.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao extends AbstractDao<Phone,Integer> {

    @Override
    public List getAll(Connection connection) throws DaoException {
        return super.getAll(connection);
    }

    @Override
    public Phone getById(Connection connection, Number number) throws DaoException {
        return (Phone)super.getById(connection, number);
    }

    @Override
    public Phone insert(Connection connection, Identified object) throws DaoException {
        return super.insert(connection, object);
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
    protected String getDeleteQuery() {
        return "DELETE FROM phone_number WHERE id = ?;";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE phone_number SET country_code = ?" +
                "SET operator_code = ?" +
                "SET number = ?" +
                "SET type = ?"+
                "SET comment = ?" +
                "WHERe id = ?;";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO phone_number (owner_id, country_code, operator_code, number, type, comment)" +
                "VALUES(?,?,?,?,?,?)";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM phone_number";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM phone_number WHERE id = ?";
    }

    @Override
    protected void prepareDeleteStatement(PreparedStatement statement, Identified object) throws SQLException {
        statement.setInt(1,object.getId());
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Identified object) throws SQLException {
        Phone phone = (Phone) object;
        statement.setString(1, phone.getCountryCode());
        statement.setString(2, phone.getOperatorCode());
        statement.setString(3, phone.getNumber());
        statement.setString(4, phone.getType().name());
        statement.setString(5, phone.getComment());
        statement.setInt(6, phone.getId());

    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Identified object) throws SQLException {
        Phone phone = (Phone) object;
        statement.setInt(1, phone.getOwnerId());
        statement.setString(2, phone.getCountryCode());
        statement.setString(3, phone.getOperatorCode());
        statement.setString(4, phone.getNumber());
        statement.setString(5, phone.getType().name());
        statement.setString(6, phone.getComment());
    }

    public List<Phone> getByOwnerId(Connection connection, Integer ownerId) {
        String query ="SELECT * FROM phone_number WHERE owner_id = ?";
        List<Phone> result = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, ownerId);
            ResultSet rs = statement.executeQuery();
            result = builder.buildList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
