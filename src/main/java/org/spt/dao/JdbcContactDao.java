package org.spt.dao;

import org.spt.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.*;

/**
 * JDBC implementation of the ContactDao. This class makes use of Spring's
 * SimpleJdcbTemplate, along with other Spring JDBC classes, for all its
 * operations.
 */
@Repository
public class JdbcContactDao implements ContactDao {

    private final static String SQL_SELECT = "SELECT id, last_name,middle_name,first_name, pf_number,id_number,email_address FROM contacts ";
    private final static String SQL_UPDATE = "UPDATE contacts set first_name = :firstName, last_name = :lastName,middle_name = :middleName " +
            " ,email_address = :emailAddress " +
            " ,pf_number = :pfNumber " +
            " ,id_number = :idNumber " +
            "WHERE id = :id";
    private final static String SQL_DELETE = "DELETE FROM contacts WHERE id = ?";
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
	public void init(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource);
        this.simpleInsert.withTableName("contacts").usingGeneratedKeyColumns("id");
	}

    @Override
    @Transactional(readOnly = true)
    public Contact findById(Integer id) {

        Contact contact;
		try {
			contact = this.simpleJdbcTemplate.queryForObject(
					SQL_SELECT + "WHERE id=?",
					BeanPropertyRowMapper.newInstance(Contact.class),
					id);
		}
		catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Contact.class, id);
		}

        return contact;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> searchForContact(String criteria) {

        Map<String,String> params = new HashMap<String,String>();
        params.put("criteria","%" + criteria + "%");

        List<Contact> contacts = this.simpleJdbcTemplate.query(
				SQL_SELECT + "WHERE first_name LIKE :criteria OR last_name LIKE :criteria",
				BeanPropertyRowMapper.newInstance(Contact.class), params);

        return contacts;
    }

    @Transactional(readOnly = true)
    public List<Contact> listAll() {

        List<Contact> contacts = this.simpleJdbcTemplate.query(
				SQL_SELECT + "ORDER BY id DESC",
				BeanPropertyRowMapper.newInstance(Contact.class));

        return contacts;
    }

    @Override
    @Transactional
    public Contact updateContact(Contact contact) {

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id", contact.getId());
        params.put("firstName", contact.getFirstName());
        params.put("middleName", contact.getMiddleName());
        params.put("lastName", contact.getLastName());
        params.put("idNumber", contact.getIdNumber());
        params.put("emailAddress", contact.getEmailAddress());
        params.put("pfNumber", contact.getPfNumber());


        this.simpleJdbcTemplate.update(SQL_UPDATE, params);

        return contact;
    }

    @Override
    @Transactional
    public void deleteContact(Integer id) {

        this.simpleJdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public Contact addContact(Contact contact) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("first_name", contact.getFirstName());
        params.put("last_name", contact.getLastName());
        params.put("middle_name", contact.getMiddleName());
        params.put("pf_number", contact.getPfNumber());
        params.put("email_address", contact.getEmailAddress());
        params.put("id_number", contact.getIdNumber());

        Number newId = simpleInsert.executeAndReturnKey(params);
        contact.setId(newId.intValue());
        return contact;
    }

    @Override
    public List<Contact> searchForContactByPf(String pf_number) {

        Map<String,String> params = new HashMap<String,String>();
        params.put("criteria", pf_number);

        List<Contact> contacts = this.simpleJdbcTemplate.query(
                SQL_SELECT + "WHERE pf_number LIKE :criteria",
                BeanPropertyRowMapper.newInstance(Contact.class), params);

        return contacts;

    }
}
