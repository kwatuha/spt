package org.spt.controller;

import com.itextpdf.text.DocumentException;
import org.spt.model.Contact;
import org.spt.extjs.ExtData;
import org.spt.extjs.ExtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spt.properties.Config;
import org.spt.properties.SptConstants;
import org.spt.service.ComService;
import org.spt.service.ContactService;
import org.spt.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * Controller for Contacts
 */
@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private ComService comService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ExtResponse getContacts(@RequestParam(value = "listType") String selectType)
            throws DocumentException, IOException {

        logger.info("getContacts called" + selectType);

        ExtData data = new ExtData();

        List<Contact> contacts = new ArrayList<Contact>();

        if (selectType.equalsIgnoreCase("queue")) {
            contacts = comService.pendingFilesContacts(Config.getProperty(SptConstants.GP_SPT_ENCRYPTED_FILE_DIR),
                    contactService.getAllContacts());
            ;
        }

        if (selectType.equalsIgnoreCase("contacts")) {
            contacts = contactService.getAllContacts();
        }

        if (selectType.equalsIgnoreCase("no_contacts")) {
            contacts = contactService
                    .getMissingContacts(Config.getProperty(SptConstants.GP_PF_HAS_NO_EMAIL_ADDRESS_DIR));
            contactService.addMissingAddress(contacts, "No Email");
        }

        data.add(contacts);
        data.setSuccess(true);

        return data;
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ExtResponse addContacts(@RequestBody Contact[] contacts)
            throws DocumentException, IOException {

        logger.info("addContacts called");

        ExtData ret = new ExtData();

        List<Contact> updated = contactService.addContacts(contacts);

        ret.add(updated);

        ret.setSuccess(true);

        return ret;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody ExtResponse deleteContacts(@RequestBody int[] ids) {

        logger.info("deleteContacts called");

        contactService.deleteContacts(ids);

        return ExtResponse.SUCCESS;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody ExtResponse updateContacts(@RequestBody Contact[] contacts) {

        logger.info("updateContacts called");

        ExtData ret = new ExtData();

        List<Contact> items = contactService.updateContacts(contacts);

        ret.add(items);
        ret.setSuccess(true);

        return ret;
    }


}
