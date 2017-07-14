package org.spt.service;

import org.spt.model.Contact;
import org.spt.model.PayslipData;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Interface for the Contact Service
 */
public interface ContactService {

    public Contact findById(Integer id);

    public List<Contact> searchForContact(String criteria);

    public List<Contact> searchContactByPf(String pf_number);

    public List<Contact> getAllContacts();

    public List<Contact> addContacts(Contact[] contacts);

    public List<Contact> updateContacts(Contact[] contacts);

    public void deleteContacts(int[] ids);
    public void splitPdf( final String source ,final String destFolder) throws IOException;
    public void uploadContactsFile(String contactsFilePath) throws IOException;
    List<Contact> getMissingContacts(String missingContactsDir);
}

