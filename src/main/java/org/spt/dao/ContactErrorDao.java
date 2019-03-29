package org.spt.dao;

import org.spt.model.Contact;

import java.util.List;

/**
 *  The core interface for the Contact DAO
 */
public interface ContactErrorDao {

    public Contact findById(Integer id);

    public List<Contact> searchForContact(String criteria);

    public List<Contact> listAll();
    
    public void deleteContact(Integer id);

    public Contact addContactError(Contact contact, String reason);
    public List<Contact> searchForContactByPf(String pf_number);
}
