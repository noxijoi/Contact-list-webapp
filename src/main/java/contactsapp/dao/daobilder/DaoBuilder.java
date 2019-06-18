package contactsapp.dao.daobilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DaoBuilder<T> {
    T buildSingle(ResultSet rs) throws SQLException;
    List<T> buildList(ResultSet rs) throws SQLException;
}
