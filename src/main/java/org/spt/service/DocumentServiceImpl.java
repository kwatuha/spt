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
    public void queueDocuments(String source,String destination) {

//        try {
//            SplitPDF.splitPdf(source,destination);
//        } catch(Exception e){
//            e.printStackTrace();
//
//        }
    }

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
    public void encryptPdf(String src, String dest,String userPassword,String ownerPassword) throws IOException, DocumentException {
        //Encrypt.encryptPdf(src,dest);
    }

    @Override
    public void encryptDirFiles(String source, String destination, String no_contacts_dir)  throws IOException, DocumentException {
        File folder = new File(source);
        File[] listOfFiles = folder.listFiles();
        String encryptedFilePath="";

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
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
    public void setGlobalProperties(String fileName) throws IOException {


FileReader fr=new FileReader(fileName);
        BufferedReader textReader=new BufferedReader(fr);

        int numberOfLines=8;
        String[] textData=new String[numberOfLines];
        for(int i=0;i<numberOfLines;i++){
            textData[i]=textReader.readLine();
            //StringUtils.contains(textReader.readLine(),)
        }

        //GlobalProperties emp = objectMapper.readValue(jsonData, GlobalProperties.class);

//        final String json = "some JSON string";
//        final ObjectMapper mapper = new ObjectMapper();
//        final GlobalProperties readValue = mapper.readValue(json, GlobalProperties.class);
        String da=null;
        try{
            da=   textData.toString();
            System.out.println("---------------------------"+da+"   ");
            GlobalProperties gp = new ObjectMapper().readValue(textData.toString(), GlobalProperties.class);
        } catch(Exception e){
            e.printStackTrace();
        }

   /////////////////////////
        String dt=textData.toString();
//        GlobalProperties gp = new ObjectMapper().readValue(textData.toString(), GlobalProperties.class);
 String tb="lllllllll";

        System.out.println("---------------------------"+textData.toString()+"   ");
//        System.out.println(gp.getContactFileDir()+"---------------------------"+textData.toString()+"   "+gp);
        textReader.close();




        InputStream is = new FileInputStream(fileName);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine(); StringBuilder sb = new StringBuilder(); while(line != null){ sb.append(line).append("\n"); line = buf.readLine(); }
        String fileAsString = sb.toString();

        GlobalProperties gp2 = new ObjectMapper().readValue(sb.toString(), GlobalProperties.class);

        System.out.println(gp2.getContactFileDir()+"---------------------------");




    }

    @Override
    public void clearFileDir(String dir) throws IOException {
        FileUtils.cleanDirectory(new File(dir));
    }
}
