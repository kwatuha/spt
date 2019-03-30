package org.spt.service;

import org.spt.model.Contact;
import org.spt.model.PayslipData;
import org.spt.dao.ContactDao;
import org.spt.dao.ContactErrorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation of the ClientService interface. Defers operations to the
 * ClientDao implementation.
 */
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDao contactDao;
    @Autowired
    private ContactErrorDao contactErrorDao;
    @Autowired
    private DocumentService documentService;

    @Override
    public Contact findById(Integer id) {
        return contactDao.findById(id);

    }

    @Override
    public List<Contact> searchForContact(String criteria) {

        criteria = (criteria == null ? "" : criteria.trim());

        if (criteria.length() < 3) {
            throw new IllegalArgumentException("Criteria must be at least 3 characters long");
        }

        return contactDao.searchForContact(criteria);
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactDao.listAll();
    }

    @Override
    public List<Contact> addContacts(Contact[] contacts) {

        List<Contact> data = new ArrayList<Contact>();

        for (Contact contact : contacts) {
            if (StringUtils.isNotBlank(contact.getPfNumber()) && StringUtils.isNotBlank(contact.getPfNumber())
                    && StringUtils.isNotBlank(contact.getIdNumber())
                    && StringUtils.isNotBlank(contact.getEmailAddress())) {
                List<Contact> existingEmail = new ArrayList<Contact>();
                existingEmail = this.searchForContact(contact.getEmailAddress());
                if (existingEmail.size() > 0) {
                    Contact updatedConctact = new Contact();
                    updatedConctact = existingEmail.get(0);
                    updatedConctact.setLastName(contact.getLastName());
                    updatedConctact.setMiddleName(contact.getMiddleName());
                    updatedConctact.setFirstName(contact.getFirstName());
                    updatedConctact.setPfNumber(contact.getPfNumber());
                    updatedConctact.setIdNumber(contact.getIdNumber());
                    updatedConctact.setKraPinNumber(contact.getKraPinNumber());
                    contactDao.updateContact(updatedConctact);
                    data.add(updatedConctact);
                } else {
                    Contact newContact = contactDao.addContact(contact);
                    data.add(newContact);
                }

            }
        }

        return data;
    }

    @Override
    public List<Contact> updateContacts(Contact[] contacts) {

        List<Contact> data = new ArrayList<Contact>();

        for (Contact contact : contacts) {

            Contact updatedContact = contactDao.updateContact(contact);
            data.add(updatedContact);

        }

        return data;
    }

    @Override
    public void deleteContacts(int[] ids) {

        for (int id : ids) {
            contactDao.deleteContact(id);
        }
    }

    @Override
    public List<Contact> searchContactByPf(String pf_number) {

        pf_number = (pf_number == null ? "" : pf_number.trim());
        return contactDao.searchForContactByPf(pf_number);

    }

    @Override
    public void uploadContactsFile(String contactsFilePath) throws IOException {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(contactsFilePath));
            int countRows = 0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] contactData = line.split(cvsSplitBy);
                Contact contact = new Contact();
                contact.setPfNumber(contactData[0].trim());
                contact.setFirstName(contactData[1].trim());
                contact.setMiddleName(contactData[2].trim());
                contact.setLastName(contactData[3].trim());
                contact.setEmailAddress(contactData[4].trim());
                contact.setIdNumber(contactData[5].trim());
                contact.setKraPinNumber(contactData[7].trim());

                if (StringUtils.isNotBlank(contact.getPfNumber()) && StringUtils.isNotBlank(contact.getPfNumber())
                        && StringUtils.isNotBlank(contact.getIdNumber())
                        && StringUtils.isNotBlank(contact.getEmailAddress())) {
                    if (countRows > 0) {
                        List<Contact> existingEmail = new ArrayList<Contact>();
                        existingEmail = this.searchForContact(contact.getEmailAddress());

                        if (existingEmail.size() > 0) {
                            Contact updatedConctact = new Contact();
                            updatedConctact = existingEmail.get(0);
                            updatedConctact.setLastName(contact.getLastName());
                            updatedConctact.setMiddleName(contact.getMiddleName());
                            updatedConctact.setFirstName(contact.getFirstName());
                            updatedConctact.setPfNumber(contact.getPfNumber());
                            updatedConctact.setIdNumber(contact.getIdNumber());
                            updatedConctact.setKraPinNumber(contact.getKraPinNumber());
                            contactDao.updateContact(updatedConctact);
                        } else {
                            contactDao.addContact(contact);
                        }

                    }
                }
                countRows++;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public List<Contact> getMissingContacts(String missingContactsDir) {
        File folder = new File(missingContactsDir);
        File[] listOfFiles = folder.listFiles();
        List<Contact> contacts = new ArrayList<Contact>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                PayslipData payslipFile = new PayslipData();
                Contact contact = new Contact();
                String fileName = listOfFiles[i].getName();
                String filePath = missingContactsDir + fileName;
                payslipFile = documentService.getPFNumber(filePath);
                String[] employeeNames = payslipFile.getEmpName().split(" ");

                try {
                    if (employeeNames.length > 1) {
                        if (employeeNames.length == 2) {
                            contact.setFirstName(employeeNames[0].trim());
                            contact.setLastName(employeeNames[1].trim());
                        }

                        if (employeeNames.length >= 3)
                            contact.setFirstName(employeeNames[2].trim());
                        if (employeeNames.length >= 4)
                            contact.setMiddleName(employeeNames[3].trim());
                        if (employeeNames.length >= 5)
                            contact.setLastName(employeeNames[4].trim());
                        contact.setPfNumber(payslipFile.getPfNumber());
                        contact.setKraPinNumber(payslipFile.getPinNumber());
                        contacts.add(contact);

                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }

        return contacts;
    }

    @Override
    public void addMissingAddress(List<Contact> contacts, String reason) {
        List<Contact> contactList = new ArrayList<Contact>();
        for (int i = 0; i < contacts.size(); i++) {
            try {
                contactErrorDao.addContactError(contacts.get(i), reason);
                System.out.println(contacts.get(i).getPfNumber());
            } catch (Exception d) {
                d.printStackTrace();

            }

        }
    }

    @Override
    public void addMissingAddress(Contact contact, String reason) {

        try {
            contactErrorDao.addContactError(contact, reason);
            System.out.println(contact.getPfNumber());
        } catch (Exception d) {
            d.printStackTrace();

        }

    }
}
