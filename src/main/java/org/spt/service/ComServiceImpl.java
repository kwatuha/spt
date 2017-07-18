package org.spt.service;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.spt.model.Contact;
import org.spt.model.Encrypt;
import org.spt.model.PayslipData;
import org.spt.properties.App;
import org.spt.properties.SptConstants;

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

/**
 * Implementation of the ComService interface. Defers operations to the
 * ComServiceImpl implementation.
 */
@Service
public class ComServiceImpl implements ComService {
    private final Logger logger = LoggerFactory.getLogger(ComServiceImpl.class);


    @Override
    public  boolean sendEmail(final String filename, final String username ,final String password,final String sendTo) throws RuntimeException{

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        File attachedFile =null;
        boolean status=false;
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("rspo@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sendTo));
            message.setSubject("Payslip");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            //message is set to empty
            messageBodyPart.setText(" ");

            // Create a multipar message
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            attachedFile=new File(filename);
            messageBodyPart.setFileName(attachedFile.getName());
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            Transport.send(message);
            attachedFile.delete();
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
            logger.info(filename+" sent at" +timeStamp);
            status=true;

        } catch (Exception e) {
            logger.info("Error Occurred while trying to send the email");
            e.printStackTrace();

        }

        return status;

    }

    @Override
    public boolean emailAllContacts(String attachmentsFolder,final String username ,final String password, List<Contact> contactsList) throws IOException, DocumentException {
        boolean isSent=false;
        for(Contact contact : contactsList) {
            String filePath=attachmentsFolder+contact.getPfNumber()+".pdf";
            File varTmpDir = new File(filePath);
            boolean exists = varTmpDir.exists();

            if(exists){
                try {
                    isSent= this.sendEmail(filePath,username,password,contact.getEmailAddress());
                    if(!isSent){
                       return isSent;
                    }

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return isSent;
    }

    @Override
    public List<Contact> pendingFilesContacts(String attachmentsFolder, List<Contact> contactsList) throws IOException, DocumentException {
        List<Contact>contactsWithPendingData=new ArrayList<Contact>();
        for(Contact contact : contactsList) {
            String filePath=attachmentsFolder+contact.getPfNumber()+".pdf";
            File varTmpDir = new File(filePath);
            boolean exists = varTmpDir.exists();
            if(exists){
                try {
                    contactsWithPendingData.add(contact);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return contactsWithPendingData;
    }


}
