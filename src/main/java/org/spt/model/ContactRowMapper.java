package org.spt.model;

/**
 * Created by ALFAYO on 7/23/2017.
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ContactRowMapper implements RowMapper<Contact> {

    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {

        Contact contact = new Contact();

        contact.setId(rs.getInt("id"));
        contact.setEmailAddress(rs.getString("email_address"));
        contact.setPfNumber(rs.getString("pf_number"));
        contact.setFirstName(rs.getString("first_name"));
        contact.setMiddleName(rs.getString("middle_name"));
        contact.setLastName(rs.getString("last_name"));
        contact.setIdNumber(rs.getString("id_number"));
        contact.setKraPinNumber(rs.getString("kra_num"));

        return contact;
    }
}
