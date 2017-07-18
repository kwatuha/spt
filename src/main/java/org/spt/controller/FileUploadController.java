package org.spt.controller;

/**
 * Created by ALFAYO on 7/6/2017.
 */

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
import org.spt.model.FileUploadBean;
import org.spt.model.ExtJSFormResult;
import org.spt.properties.Config;
import org.spt.properties.SptConstants;
import org.spt.service.ContactService;
import org.spt.service.DocumentService;

import java.io.*;

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

        if (result.hasErrors()) {

            for (ObjectError error : result.getAllErrors()) {
                System.err.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
            }

            extjsFormResult.setSuccess(false);
            return extjsFormResult.toString();
        }

        InputStream fileContent = null;
        FileOutputStream out = null;

        try{
            out = new FileOutputStream(new File(Config.getProperty(SptConstants.GP_SPT_UPLOADED)+ uploadItem.getFile().getOriginalFilename()));
            int read = 0;
            final byte[] bytes = new byte[1024];
            fileContent =uploadItem.getFile().getFileItem().getInputStream();
            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.close();
            }
            if (fileContent != null) {
                fileContent.close();
            }

        }


        String fileType = FilenameUtils.getExtension(uploadItem.getFile().getOriginalFilename());

        // If pdf, uploading payroll file
         if(StringUtils.equals(fileType,"pdf")){
                documentService.splitPdf(
                        Config.getProperty(SptConstants.GP_SPT_UPLOADED) + uploadItem.getFile().getOriginalFilename(),
                        Config.getProperty(SptConstants.GP_SPT_QUEUED_FILE_DIR)
                );

                documentService.encryptDirFiles(
                        Config.getProperty(SptConstants.GP_SPT_QUEUED_FILE_DIR),
                        Config.getProperty(SptConstants.GP_SPT_ENCRYPTED_FILE_DIR),
                        Config.getProperty(SptConstants.GP_PF_HAS_NO_EMAIL_ADDRESS_DIR)
                );

            }
         // If csv, uploading contacts file
            else if (StringUtils.equals(fileType,"csv")){
                contactService.uploadContactsFile(Config.getProperty(SptConstants.GP_SPT_UPLOADED) + uploadItem.getFile().getOriginalFilename());
         }
         else {
                System.err.println("Uploading Invalid file: " + uploadItem.getFile().getOriginalFilename());
         }

        documentService.deleteFile(Config.getProperty(SptConstants.GP_SPT_UPLOADED) + uploadItem.getFile().getOriginalFilename());
        extjsFormResult.setSuccess(true);

        return extjsFormResult.toString();

    }



}