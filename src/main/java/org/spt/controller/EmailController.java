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
import org.spt.properties.Config;
import org.spt.properties.SptConstants;
import org.spt.service.ComService;
import org.spt.service.ContactService;
import org.spt.service.DocumentService;

import java.awt.*;
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

        ExtJSFormResult extjsFormResult = new ExtJSFormResult();

        extjsFormResult.setSuccess(true);

        return extjsFormResult.toString();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String addContacts(@RequestBody String queue) throws IOException,DocumentException {

        ExtJSFormResult extjsFormResult = new ExtJSFormResult();

        String encryptedDir=Config.getProperty(SptConstants.GP_SPT_ENCRYPTED_FILE_DIR);

        String noContactDir=Config.getProperty(SptConstants.GP_PF_HAS_NO_EMAIL_ADDRESS_DIR);

        String senderEmailAddress=Config.getProperty(SptConstants.GP_EMAIL_ADDRESS);

        String senderEmailPassword=Config.getProperty(SptConstants.GP_EMAIL_PWD);

        boolean isSent=false;

        if(StringUtils.contains(queue,"encrypted")){

            isSent= comService.emailAllContacts(
                    encryptedDir,
                    senderEmailAddress,
                    senderEmailPassword,
                    contactService.getAllContacts()
            );

        }

        if(StringUtils.contains(queue,"no_contacts")){
            documentService.encryptDirFiles(noContactDir, encryptedDir,noContactDir);

            isSent= comService.emailAllContacts(
                    encryptedDir,
                    senderEmailAddress,
                    senderEmailPassword,
                    contactService.getAllContacts()
            );
        }

        extjsFormResult.setSuccess(isSent);

        return extjsFormResult.toString();
    }


}
