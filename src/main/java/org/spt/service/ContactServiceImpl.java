package org.spt.service;

import org.spt.model.Contact;
import org.spt.model.PayslipData;
import org.spt.model.SplitPDF;
import org.spt.dao.ContactDao;
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
    private DocumentService documentService;

    public Contact findById(Integer id) {
        return contactDao.findById(id);

    }

    public List<Contact> searchForContact(String criteria) {

        criteria = (criteria == null ? "":criteria.trim());

        if(criteria.length() < 3) {
            throw new IllegalArgumentException("Criteria must be at least 3 characters long");
        }

        return contactDao.searchForContact(criteria);
    }

    public List<Contact> getAllContacts() {
        return contactDao.listAll();
    }

    @Override
    public List<Contact> addContacts(Contact[] contacts) {

        List<Contact> data = new ArrayList<Contact>();

        for(Contact contact : contacts) {
            if ( StringUtils.isNotBlank(contact.getPfNumber()) && StringUtils.isNotBlank(contact.getPfNumber()) && StringUtils.isNotBlank(contact.getIdNumber())
                    ) {
                Contact newContact = contactDao.addContact(contact);
                data.add(newContact);
            }
        }

        return data;
    }

    @Override
    public List<Contact> updateContacts(Contact[] contacts) {

        List<Contact> data = new ArrayList<Contact>();

        for(Contact contact : contacts) {
            Contact updatedContact = contactDao.updateContact(contact);
            data.add(updatedContact);
        }

        return data;
    }

    @Override
    public void deleteContacts(int[] ids) {

        for(int id : ids ) {
            contactDao.deleteContact(id);
        }
    }

    @Override
    public void splitPdf(String source, String destFolder) throws IOException {
            try{
                System.out.println("splitting documents xxxxxxxxxxxxxxxxxxxxxxxxxxxxxIIIIIIIIIIIIIIIIIIII");
                SplitPDF.splitPdf(source,destFolder);
            } catch (IOException e){
                System.out.println("splitting documents xxxxxxxxxxxxxxxxxxxxxxxxxxxxxErreresd");
                e.printStackTrace();
            }
    }

    @Override
    public List<Contact> searchContactByPf(String pf_number) {

        pf_number = (pf_number == null ? "":pf_number.trim());
        return contactDao.searchForContactByPf(pf_number);

    }

    @Override
    public void uploadContactsFile( String contactsFilePath) throws IOException {
            BufferedReader br = null;
            String line = "";
            String cvsSplitBy = ",";

            try {

                br = new BufferedReader(new FileReader(contactsFilePath));
                int countRows=0;
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] contactData = line.split(cvsSplitBy);
                                Contact contact =new Contact();
                                contact.setPfNumber(contactData[0].trim());
                                contact.setFirstName(contactData[1].trim());
                                contact.setMiddleName(contactData[2].trim());
                                contact.setLastName(contactData[3].trim());
                                contact.setEmailAddress(contactData[4].trim());
                                contact.setIdNumber(contactData[5].trim());

                                if(countRows>0){
                                contactDao.addContact(contact);
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
        List<Contact>contacts=new ArrayList<Contact>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                PayslipData paylipFile=new PayslipData();
                Contact contact=new Contact();
                System.out.println("File " + listOfFiles[i].getName());
                String fileName = listOfFiles[i].getName();
                String filePath = missingContactsDir + fileName;
                paylipFile=documentService.getPFNumber(filePath);
                String[] employeeNames=paylipFile.getEmpName().split(" ");
                contact.setFirstName(employeeNames[2].trim());
                contact.setMiddleName(employeeNames[3].trim());
                contact.setLastName(employeeNames[4].trim());
                contact.setPfNumber(paylipFile.getPfNumber());

                contacts.add(contact);
            }
        }

        return contacts;
    }
}
