package org.spt.controller;

/**
 * Created by ALFAYO on 7/6/2017.
 */
//import com.itextpdf.io.IOException;
import com.itextpdf.text.DocumentException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import org.spt.model.Contact;
import org.spt.model.FileUploadBean;
import org.spt.model.ExtJSFormResult;
import org.spt.properties.App;
import org.spt.properties.SptConstants;
import org.spt.service.ContactService;
import org.spt.service.DocumentService;

import java.io.*;
import java.util.List;
import java.util.Properties;


@Controller
@RequestMapping(value = "upload.action")
public class FileUploadController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private DocumentService documentService;
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody    String create(FileUploadBean uploadItem, BindingResult result) throws IOException,DocumentException{

        ExtJSFormResult extjsFormResult = new ExtJSFormResult();
        String fileName="C:\\tomcat6\\spt\\config\\config.properties";
        Properties prop= App.getProperties(fileName);
        String sptDir=prop.getProperty(SptConstants.GP_SPT_FILE_DIR);
        String queueDir=sptDir+"queue\\";
        String encryptedDir=sptDir+"encrypted\\";
        String noContactDir=sptDir+"no_contacts\\";
        String uploadedDir=sptDir+"uploaded\\";
        List<Contact> contactList=null;

        //uploadItem.getFile().getInputStream()
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.err.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
            }

            //set <a target="_blank" title="extjs" href="http://sencha.com/">extjs</a> return - error
            extjsFormResult.setSuccess(false);

            return extjsFormResult.toString();
        }

        // Some type of file processing...
        //File newc= new File("c:\\tomcat6\\spt\\encrypted\\"+uploadItem.getFile().getOriginalFilename());
        System.err.println("-------------------------------------------");
        System.err.println("Test upload: " + uploadItem.getFile().getOriginalFilename());

        InputStream filecontent = null;

        FileOutputStream out = null;
        try{


            out = new FileOutputStream(new File("C:\\tomcat6\\spt\\uploaded\\" + uploadItem.getFile().getOriginalFilename()));


            int read = 0;
            final byte[] bytes = new byte[1024];
            filecontent =uploadItem.getFile().getFileItem().getInputStream();

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }

        }

        //if(uploadContactsFile)
            ////////////// Adding upload
            String fileType = FilenameUtils.getExtension(uploadItem.getFile().getOriginalFilename());
            if(StringUtils.equals(fileType,"pdf")){



                documentService.splitPdf(uploadedDir + uploadItem.getFile().getOriginalFilename(),queueDir);
                documentService.encryptDirFiles(queueDir, encryptedDir,noContactDir);
                System.err.println("Uploading Paryoll: " + uploadItem.getFile().getOriginalFilename());
            }else if (StringUtils.equals(fileType,"csv")){
                contactService.uploadContactsFile("C:\\tomcat6\\spt\\uploaded\\" + uploadItem.getFile().getOriginalFilename());
            }else{
                System.err.println("Uploading Invalid file: " + uploadItem.getFile().getOriginalFilename());
            }

        ////////////////////////////////////
        //set <a target="_blank" title="extjs" href="http://sencha.com/">extjs</a> return - sucsess
        extjsFormResult.setSuccess(true);

        return extjsFormResult.toString();

    }



}