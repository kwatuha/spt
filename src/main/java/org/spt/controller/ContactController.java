package org.spt.controller;

import com.itextpdf.text.DocumentException;
import org.spt.model.Contact;
import org.spt.extjs.ExtData;
import org.spt.extjs.ExtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spt.service.ComService;
import org.spt.service.ContactService;
import org.spt.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  Controller for Contacts
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
    public @ResponseBody ExtResponse getContacts(
            @RequestParam(value = "listType") String selectType
    )  throws DocumentException,IOException {

        logger.info("getContacts called" +selectType);

        ExtData data = new ExtData();
        List<Contact> contacts=new ArrayList<Contact>();
        if(selectType.equalsIgnoreCase("queue")){
          contacts = comService.pendingFilesContacts("c:\\tomcat6\\spt\\encrypted\\", contactService.getAllContacts());;
         }
        if(selectType.equalsIgnoreCase("contacts")){
            contacts = contactService.getAllContacts();
        }

        if(selectType.equalsIgnoreCase("no_address")){
            contacts = contactService.getMissingContacts("c:\\tomcat6\\spt\\no_contacts\\");
        }

        data.add(contacts);
        data.setSuccess(true);

        return data;
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ExtResponse addContacts(@RequestBody Contact[] contacts) throws DocumentException,IOException {

        logger.info("addContacts called");

        ExtData ret = new ExtData();

        List<Contact> updated = contactService.addContacts(contacts);

        ret.add(updated);
        // c:/tomcat6/spt/tests/3684.pdf queueDocuments(String source,String destination)
      // String pfn= documentService.getPFNumber("c:/tomcat6/spt/tests/splitDocument1_1.pdf");
        //documentService.encryptDirFiles("c:/tomcat6/spt/tests/3684_itext.pdf", "c:/tomcat6/spt/tests/3684_malllll.pdf");
        //documentService.encryptDirFiles("c:\\tomcat6\\spt/queue\\", "c:\\tomcat6\\spt\\encrypted\\");
        //comService.batchEmail("c:\\tomcat6\\spt\\encrypted\\", contactService.getAllContacts());

        //comService.emailAllContacts("c:\\tomcat6\\spt\\encrypted\\", contactService.getAllContacts());

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
