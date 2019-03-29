package org.spt.service;

import com.itextpdf.text.DocumentException;
import javax.mail.internet.AddressException;
import org.spt.model.Contact;
import java.io.IOException;
import java.util.List;

/**
 * Interface for the Contact Service
 */
public interface ComService {
    public  boolean sendEmail(final String filename, final String username ,final String password,final String sendTo) throws AddressException, RuntimeException;
    public boolean  emailAllContacts(String attachmentsFolder,final String username ,final String password, List<Contact> contactsList)  throws IOException, DocumentException;
    public List<Contact>   pendingFilesContacts(String attachmentsFolder, List<Contact> contactsList)  throws IOException, DocumentException;

}

