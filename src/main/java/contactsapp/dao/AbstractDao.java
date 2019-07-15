package contactsapp.dao;


import contactsapp.core.entity.Identified;
import contactsapp.dao.daobilder.DaoBuilder;

import java.sql.*;
import java.util.List;

public abstract class AbstractDao<T extends Identified,PK extends Number> implements GenericDao{

    public DaoBuilder<T> builder;

    public AbstractDao(){

    }

    public AbstractDao( DaoBuilder builder){

        this.builder = builder;
    }


    @Override
    public List getAll(Connection connection) throws SQLException {
        List<T> list;
        String query = getSelectAllQuery();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            ResultSet rs = statement.executeQuery();
            list = builder.buildList(rs);
        }
        return list;
    }

    @Override
    public List<T> exequtePreparedStatement(Connection connection, PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.executeQuery();
        List<T> resultList = builder.buildList(rs);
        return resultList;
    }

    @Override
    public T getById(Connection connection, Number number) throws SQLException {
        T result;
        String query = getSelectByIdQuery();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, (Integer) number);
            ResultSet rs = statement.executeQuery();
            rs.next();
            result = builder.buildSingle(rs);
        }
        return result;
    }

    @Override
    public T insert(Connection connection, Identified object) throws DaoException, SQLException {
        T result;
        String query = getInsertQuery();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            prepareInsertStatement(statement, object);
            int count = statement.executeUpdate();
            if (count != 1){
                throw new DaoException("Modified more than 1 recond on insert");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return null;
    }

    @Override
    public void update(Connection connection, Identified object) throws DaoException, SQLException {
        String query = getUpdateQuery();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            prepareUpdateStatement(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DaoException("On update modify more then 1 record: " + count);
            }
        }
    }

    @Override
    public void delete(Connection connection, Identified object) throws DaoException, SQLException {
        String query = getDeleteQuery();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            prepareDeleteStatement(statement, object);
            int count = statement.executeUpdate();
            if(count != 1){
                throw new DaoException("On delete modify more then 1 record: " + count);
            }
        }

    }

    protected abstract String getDeleteQuery();
    protected abstract String getUpdateQuery();
    protected abstract String getInsertQuery();
    protected abstract String getSelectAllQuery();
    protected abstract String getSelectByIdQuery();
    protected abstract void prepareDeleteStatement(PreparedStatement statement, Identified object) throws SQLException;

    protected abstract void prepareUpdateStatement(PreparedStatement statement, Identified object) throws SQLException;

    protected abstract void prepareInsertStatement(PreparedStatement statement, Identified object) throws SQLException;
}
