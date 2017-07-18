package org.spt.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PageRange;
import com.itextpdf.kernel.utils.PdfSplitter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.spt.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;


import com.itextpdf.text.pdf.PdfReader;
import org.spt.properties.App;


@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private ContactService contactService;

    @Override
    public PayslipData getPFNumber(String sourcePath) {
        PayslipData data=new PayslipData();
        PdfReader reader=null;
        try {
            reader = new PdfReader(sourcePath);
            String page = PdfTextExtractor.getTextFromPage(reader, 1);
            String payslipText[]=page.split("\n");
            String pfNumberRow[]=payslipText[1].split("Employee No.");
            data.setPfNumber(pfNumberRow[1].trim());
            data.setEmpName(payslipText[2].trim());
            data.setPayPeriod(payslipText[3].trim());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        return data;
    }

    @Override
   public void splitPdf(final String source, final String destFolder) throws IOException {

        PdfDocument pdfDoc = new PdfDocument(new com.itextpdf.kernel.pdf.PdfReader(source));

        final List<PdfDocument> splitDocuments = new PdfSplitter(pdfDoc) {
            int partNumber = 1;
            @Override
            protected PdfWriter getNextPdfWriter(PageRange documentPageRange) {
                try {
                    return new PdfWriter(destFolder + "spt_" + String.valueOf(partNumber++) + ".pdf");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException();
                }
            }
        }.splitBySize(200000);

        for (PdfDocument doc : splitDocuments)
            doc.close();
        pdfDoc.close();

    }


    @Override
    public void encryptDirFiles(String source, String destination, String no_contacts_dir)  throws IOException, DocumentException {
        File folder = new File(source);
        File[] listOfFiles = folder.listFiles();
        String encryptedFilePath="";

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName=listOfFiles[i].getName();
                String filePath=source+fileName;
                String movedFile=no_contacts_dir+fileName;
                File varTmpDir = new File(filePath);
                boolean exists = varTmpDir.exists();

                if(exists){
                    PayslipData data=this.getPFNumber(filePath);
                    encryptedFilePath=destination+data.getPfNumber().trim()+".pdf";

                    try {
                        List<Contact>contacts=null;
                        contacts=contactService.searchContactByPf(data.getPfNumber());
                        if(contacts.size()>0){
                            String userPWD=contacts.get(0).getIdNumber().trim();
                            String ownPWD=contacts.get(0).getIdNumber().trim();
                            Encrypt.encryptPdf(filePath,encryptedFilePath,userPWD,ownPWD);
                        }else{
                            try{
                               File newFile=new File(movedFile);
                               FileUtils.moveFile(varTmpDir,newFile);
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }

                        }

                    } catch(Exception e){
                        e.printStackTrace();

                    }
                }
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }

        }
    }

    @Override
    public void clearFileDir(String dir) throws IOException {
        FileUtils.cleanDirectory(new File(dir));
    }

    @Override
    public void deleteFile(String filePath) throws IOException {
        FileUtils.forceDelete(new File(filePath));
    }
}
