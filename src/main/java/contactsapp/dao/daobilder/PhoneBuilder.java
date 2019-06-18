package contactsapp.dao.daobilder;

import contactsapp.core.entity.Contact;
import contactsapp.core.entity.Phone;
import contactsapp.core.entity.PhoneType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneBuilder implements DaoBuilder<Phone> {
    @Override
    public Phone buildSingle(ResultSet rs) throws SQLException {
        Phone phone  = new Phone();
        phone.setId(rs.getInt("id"));
        phone.setOwnerId(rs.getInt("owner_id"));
        phone.setCountryCode(rs.getString("country_code"));
        phone.setOperatorCode(rs.getString("operator_code"));
        phone.setNumber(rs.getString("number"));
        phone.setType(PhoneType.valueOf(rs.getString("type").toUpperCase()));
        phone.setComment(rs.getString("comment"));
        return phone;
    }

    @Override
    public List<Phone> buildList(ResultSet rs) throws SQLException {
        List<Phone> phoneList =  new ArrayList<>();
        while (rs.next()){
            phoneList.add(buildSingle(rs));

        }
        return phoneList;
    }
}
