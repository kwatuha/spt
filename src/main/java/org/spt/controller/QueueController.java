package org.spt.controller;

import com.itextpdf.text.DocumentException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.spt.model.ExtJSFormResult;
import org.spt.properties.Batch;
import org.spt.properties.Config;
import org.spt.properties.SptConstants;
import org.spt.service.DocumentService;

import java.io.IOException;

/**
 * Controller for Contacts
 */
@Controller
@RequestMapping("/queue")
public class QueueController {

    private final Logger logger = LoggerFactory.getLogger(QueueController.class);

    @Autowired
    private DocumentService documentService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String getBouncedEmail(@RequestParam(value = "queue") String queue)
            throws IOException, DocumentException {

        ExtJSFormResult extjsFormResult = new ExtJSFormResult();

        String clearQueue = Config.getProperty(SptConstants.GP_SPT_FILE_DIR) + queue;

        documentService.clearFileDir(clearQueue);
        if (StringUtils.equalsIgnoreCase(queue, "encrypted")) {
            String quueQueue = Config.getProperty(SptConstants.GP_SPT_FILE_DIR) + "queued";
            documentService.clearFileDir(quueQueue);
        }
        Batch.run();
        extjsFormResult.setSuccess(true);

        return extjsFormResult.toString();
    }

}
