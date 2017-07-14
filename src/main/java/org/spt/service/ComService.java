package org.spt.service;

import com.itextpdf.text.DocumentException;
import org.spt.model.Contact;
import org.spt.model.PayslipData;

import java.io.IOException;
import java.util.List;

/**
 * Interface for the Contact Service
 */
public interface ComService {
    public  void sendEmail(final String filename, final String username ,final String password,final String sendTo) throws RuntimeException;
    public void  emailAllContacts(String attachmentsFolder,final String username ,final String password, List<Contact> contactsList)  throws IOException, DocumentException;
    public List<Contact>   pendingFilesContacts(String attachmentsFolder, List<Contact> contactsList)  throws IOException, DocumentException;



}

