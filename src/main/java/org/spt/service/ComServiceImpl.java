package org.spt.service;

import com.itextpdf.text.DocumentException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.spt.model.Contact;
import org.spt.service.ContactService;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.internet.AddressException;
import org.spt.service.DocumentService;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation of the ComService interface. Defers operations to the
 * ComServiceImpl implementation.
 */
@Service
public class ComServiceImpl implements ComService {
    private final Logger logger = LoggerFactory.getLogger(ComServiceImpl.class);
    @Autowired
    private ContactService contactService;
    @Autowired
    private DocumentService documentService;

    @Override
    public boolean sendEmail(final String filename, final String username, final String password, final String sendTo)
            throws AddressException, RuntimeException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        int i = filename.lastIndexOf(File.separatorChar);
        String period[] = filename.substring(i + 1).split("_");
        String revisedPeriod = period[0] + " " + period[1];
        File attachedFile = null;
        boolean status = false;
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("rspo@gmail.com"));
            try {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));

            } catch (Exception e) {
                List<Contact> contacts = this.contactService.searchForContact(sendTo);
                this.contactService.addMissingAddress(contacts, "Invalid Email Address");
                this.documentService.deleteFile(filename);
                return true;
            }
            String emailSubject = getEmailSubject(filename);
            message.setSubject(emailSubject);

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // message is set to empty
            messageBodyPart.setText("Please find attached payslip. Use your ID Number to open it.");

            // Create a multipar message
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            attachedFile = new File(filename);
            messageBodyPart.setFileName(attachedFile.getName());
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            Transport.send(message);
            attachedFile.delete();
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
            logger.info(filename + " sent at" + timeStamp);
            status = true;

        } catch (Exception e) {
            logger.info("Error Occurred while trying to send the email");
            List<Contact> contacts = this.contactService.searchForContact(sendTo);
            logger.info(contacts.size() + "==ssssssssssssssssssssss==" + contacts.get(0).getPfNumber());
            e.printStackTrace();

        }

        return status;

    }

    @Override
    public boolean emailAllContacts(String attachmentsFolder, final String username, final String password,
            List<Contact> contactsList) throws IOException, DocumentException {
        boolean isSent = false;

        List<Contact> contactsWithPendingData = new ArrayList<Contact>();
        File[] files = new File(attachmentsFolder).listFiles();

        for (File f : files) {
            int i = f.getName().lastIndexOf("_");
            String searchKys[] = f.getName().substring(i + 1).split(".pdf");
            List<Contact> contacts = null;
            contacts = contactService.searchContactByPf(searchKys[0]);
            String attachmentFilePath = attachmentsFolder + f.getName();
            if (contacts.size() == 1) {

                try {
                    isSent = this.sendEmail(attachmentFilePath, username, password, contacts.get(0).getEmailAddress());
                    if (!isSent) {
                        return isSent;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return isSent;
    }

    @Override
    public List<Contact> pendingFilesContacts(String attachmentsFolder, List<Contact> contactsList)
            throws IOException, DocumentException {
        List<Contact> contactsWithPendingData = new ArrayList<Contact>();
        File[] files = new File(attachmentsFolder).listFiles();

        for (File f : files) {
            int i = f.getName().lastIndexOf("_");
            String searchKys[] = f.getName().substring(i + 1).split(".pdf");
            List<Contact> contacts = null;
            contacts = contactService.searchContactByPf(searchKys[0]);
            if (contacts.size() == 1) {
                contactsWithPendingData.add(contacts.get(0));
            }
        }

        return contactsWithPendingData;
    }

    private String getEmailSubject(String filename) {

        int i = filename.lastIndexOf(File.separatorChar);
        String f = filename.substring(i + 1).split(".pdf")[0];
        if (f.contains("TAX")) {
            return f.replace("_", " ");
        } else {
            String period[] = filename.substring(i + 1).split("_");
            return period[0] + " " + period[1] + " Payslip";
        }
    }

}
