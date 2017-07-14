package org.spt.controller;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.spt.model.ExtJSFormResult;
import org.spt.model.SptPDFReader;
import org.spt.properties.App;
import org.spt.properties.SptConstants;
import org.spt.service.DocumentService;
import org.w3c.dom.DOMException;

import java.io.IOException;
import java.util.Properties;

/**
 *  Controller for Contacts
 */
@Controller
@RequestMapping("/queue")
public class QueueController {

    private final Logger logger = LoggerFactory.getLogger(QueueController.class);

    @Autowired
    private DocumentService documentService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String getBouncedEmail(  @RequestParam(value = "queue") String queue) throws IOException,DocumentException{
        ExtJSFormResult extjsFormResult = new ExtJSFormResult();

        String fileName="C:\\tomcat6\\spt\\config\\config.properties";
        SptPDFReader.manipulatePdf();
        Properties prop=App.getProperties(fileName);
        String sptDir=prop.getProperty(SptConstants.GP_SPT_FILE_DIR);
        String cleareQueue=sptDir+queue;
        documentService.clearFileDir(cleareQueue);
        extjsFormResult.setSuccess(true);
        return extjsFormResult.toString();
    }


}
