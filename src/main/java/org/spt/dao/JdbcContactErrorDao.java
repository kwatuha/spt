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

import com.mysql.jdbc.StringUtils;

import java.util.*;

/**
 * JDBC implementation of the ContactDao. This class makes use of Spring's
 * SimpleJdcbTemplate, along with other Spring JDBC classes, for all its
 * operations.
 */
@Repository
public class JdbcContactErrorDao implements ContactErrorDao {

    private final static String SQL_SELECT = "SELECT id, last_name,middle_name,first_name, pf_number,id_number,email_address FROM no_email_contacts ";
    private final static String SQL_UPDATE = "UPDATE no_email_contacts set first_name = :firstName, last_name = :lastName,middle_name = :middleName "
            + " ,email_address = :emailAddress " + " ,pf_number = :pfNumber " + " ,id_number = :idNumber "
            + "WHERE id = :id";
    private final static String SQL_DELETE = "DELETE FROM no_email_contacts WHERE id = ?";
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public void init(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource);
        this.simpleInsert.withTableName("no_email_contacts").usingGeneratedKeyColumns("id");

    }

    @Override
    @Transactional(readOnly = true)
    public Contact findById(Integer id) {

        Contact contact;
        try {
            contact = this.simpleJdbcTemplate.queryForObject(SQL_SELECT + "WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Contact.class), id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Contact.class, id);
        }

        return contact;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> searchForContact(String criteria) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("criteria", "%" + criteria + "%");

        List<Contact> contacts = this.simpleJdbcTemplate.query(SQL_SELECT
                + "WHERE first_name LIKE :criteria OR last_name LIKE :criteria  OR middle_name LIKE :criteria  OR email_address LIKE :criteria",
                BeanPropertyRowMapper.newInstance(Contact.class), params);

        return contacts;
    }

    @Transactional(readOnly = true)
    public List<Contact> listAll() {

        List<Contact> contacts = this.simpleJdbcTemplate.query(SQL_SELECT + "ORDER BY id DESC",
                BeanPropertyRowMapper.newInstance(Contact.class));

        return contacts;
    }

    @Override
    @Transactional
    public void deleteContact(Integer id) {

        this.simpleJdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public Contact addContactError(Contact contact, String reason) {
        Map<String, Object> params = new HashMap<String, Object>();
        String pfprovided = StringUtils.isEmptyOrWhitespaceOnly(contact.getPfNumber()) ? null : contact.getPfNumber();
        pfprovided = StringUtils.isNullOrEmpty(pfprovided) ? null
                : formatPFNumber(Integer.parseInt(contact.getPfNumber()));

        params.put("first_name", contact.getFirstName());
        params.put("last_name", contact.getLastName());
        params.put("middle_name", contact.getMiddleName());
        params.put("pf_number", pfprovided);
        params.put("email_address", contact.getEmailAddress());
        params.put("id_number", contact.getIdNumber());
        params.put("kra_num", contact.getKraPinNumber());
        params.put("other", reason);

        String searchKey = null;

        if (!StringUtils.isNullOrEmpty(pfprovided) || !StringUtils.isNullOrEmpty(contact.getKraPinNumber())) {

            if (!StringUtils.isNullOrEmpty(contact.getKraPinNumber()))
                searchKey = contact.getKraPinNumber();

            if (!StringUtils.isNullOrEmpty(pfprovided))
                searchKey = pfprovided;

            List<Contact> cList = this.searchForContactByPf(searchKey);
            if (cList.size() <= 0) {
                Number newId = this.simpleInsert.executeAndReturnKey(params);
                contact.setId(newId.intValue());
                return contact;

            }

        }

        return null;
    }

    @Override
    public List<Contact> searchForContactByPf(String pf_number) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("criteria", pf_number);

        List<Contact> contacts = this.simpleJdbcTemplate.query(SQL_SELECT + "WHERE pf_number LIKE :criteria",
                BeanPropertyRowMapper.newInstance(Contact.class), params);

        return contacts;

    }

    private String formatPFNumber(Integer pf) {
        return String.format("%04d", pf);
    }
}
