package org.spt.controller;

import com.itextpdf.text.DocumentException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.spt.model.Contact;
import org.spt.properties.App;
import org.spt.model.ExtJSFormResult;
import org.spt.properties.SptConstants;
import org.spt.service.ComService;
import org.spt.service.ContactService;
import org.spt.service.DocumentService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 *  Controller for Contacts
 */
@Controller
@RequestMapping("/email")
public class EmailController {

    private final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private ContactService contactService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private ComService comService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String getBouncedEmail(  @RequestParam(value = "emailType") String emailType) {

        logger.info("getBouncedEmail called");
        ExtJSFormResult extjsFormResult = new ExtJSFormResult();
        logger.info("Reading Bounced Emails");
        System.out.println("Uloading=====" +emailType);
        extjsFormResult.setSuccess(true);

        return extjsFormResult.toString();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String addContacts(@RequestBody String queue) throws IOException,DocumentException {
        ExtJSFormResult extjsFormResult = new ExtJSFormResult();
        String fileName="C:\\tomcat6\\spt\\config\\config.properties";
        Properties prop=App.getProperties(fileName);
        String sptDir=prop.getProperty(SptConstants.GP_SPT_FILE_DIR);
        String queueDir=sptDir+"queue\\";
        String encryptedDir=sptDir+"encrypted\\";
        String noContactDir=sptDir+"no_contacts\\";
        List<Contact>contactList=null;
        String senderEmailAddress=prop.getProperty(SptConstants.GP_EMAIL_ADDRESS);
        String senderEmailPassword=prop.getProperty(SptConstants.GP_EMAIL_PWD);

        //contactList=comService.missingContactsFiles(encryptedDir,contactService.getAllContacts());
if(StringUtils.contains(queue,"encrypted")){
            comService.emailAllContacts(encryptedDir,senderEmailAddress,senderEmailPassword,
                contactService.getAllContacts());

}

        if(StringUtils.contains(queue,"no_contacts")){
            documentService.encryptDirFiles(noContactDir, encryptedDir,noContactDir);
            comService.emailAllContacts(encryptedDir,senderEmailAddress,senderEmailPassword,
                    contactService.getAllContacts());
        }

//        documentService.deleteFiles(noContactDir,queueDir);
        extjsFormResult.setSuccess(true);
        return extjsFormResult.toString();
    }


}
