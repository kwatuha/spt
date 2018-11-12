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

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.tools.UnitConv;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import java.awt.image.BufferedImage;


import com.sun.jndi.ldap.LdapCtxFactory;
import com.sun.jndi.ldap.LdapCtxFactory;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import javax.naming.Context;
import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import static javax.naming.directory.SearchControls.SUBTREE_SCOPE;


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

    @Override
    public void addBarcode(String fileDir,String message) throws IOException {
        //Create the barcode bean
        Code39Bean bean = new Code39Bean();

        final int dpi = 150;

        //Configure the barcode generator
        bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar, width exactly one pixel
        bean.setWideFactor(3);
        bean.doQuietZone(false);

        //Open output file
        File outputFile = new File(fileDir+"/"+"out.png");
        OutputStream out = new FileOutputStream(outputFile);

        try {

            //Set up the canvas provider for monochrome PNG output
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

            //Generate the barcode
            bean.generateBarcode(canvas, message);

            //Signal end of generation
            canvas.finish();
        } finally {
            out.close();
        }

    }

    public void authenticateUser(String username, String password){
         if ( StringUtils.isNotBlank(password) && StringUtils.isNotBlank(username)) {
            System.out.println("Purpose: authenticate user against Active Directory and list group membership.");
            System.out.println("Usage: App2 <username> <password> <domain> <server>");
            System.out.println("Short usage: App2 <username> <password>");
            System.out.println("(short usage assumes 'xyz.tld' as domain and 'abc' as server)");
            // System.exit(1);
        }

        String domainName ="ampath.or.ke";
        String serverName ="ad1";

        System.out
                .println("Authenticating " + username + "@" + domainName + " through " + serverName + "." + domainName);

        // bind by using the specified username/password
        Hashtable props = new Hashtable();
        String principalName = username + "@" + domainName;
        props.put(Context.SECURITY_PRINCIPAL, principalName);
        props.put(Context.SECURITY_CREDENTIALS, password);
        DirContext context;

        try {
            context = LdapCtxFactory.getLdapCtxInstance("ldap://" + serverName + "." + domainName + '/', props);
            System.out.println("Authentication succeeded!");

            // locate this user's record
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> renum = context.search(toDC(domainName),
                    "(& (userPrincipalName=" + principalName + ")(objectClass=user))", controls);
            if (!renum.hasMore()) {
                System.out.println("Cannot locate user information for " + username);
                System.exit(1);
            }
            SearchResult result = renum.next();

            List<String> groups = new ArrayList<String>();
            Attribute memberOf = result.getAttributes().get("memberOf");
            if (memberOf != null) {// null if this user belongs to no group at all
                for (int i = 0; i < memberOf.size(); i++) {
                    Attributes atts = context.getAttributes(memberOf.get(i).toString(), new String[] { "CN" });
                    Attribute att = atts.get("CN");
                    groups.add(att.get().toString());
                }
            }

            context.close();

            System.out.println();
            System.out.println("User belongs to: ");
            Iterator ig = groups.iterator();
            while (ig.hasNext()) {
                System.out.println("   " + ig.next());
            }

        } catch (AuthenticationException a) {
            System.out.println("Authentication failed: " + a );
        } catch (NamingException e) {
            System.out.println("Failed to bind to LDAP / get account information: " + e);
        }
    }

    private static String toDC(String domainName) {
        StringBuilder buf = new StringBuilder();
        for (String token : domainName.split("\\.")) {
            if (token.length() == 0)
                continue; // defensive check
            if (buf.length() > 0)
                buf.append(",");
            buf.append("DC=").append(token);
        }
        return buf.toString();
    }
}
