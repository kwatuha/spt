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
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.Document;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private ContactService contactService;
    private String pfNumberRow[] = null;

    @Override
    public PayslipData getPFNumber(String sourcePath) {
        PayslipData data = new PayslipData();
        PdfReader reader = null;
        try {
            reader = new PdfReader(sourcePath);
            String page = PdfTextExtractor.getTextFromPage(reader, 1);
            String payslipText[] = page.split("\n");

            // for (int i = 0; i < payslipText.length; i++) {
            // System.out.println(payslipText[i] + " ===XXXX=== "+i);
            // }

            if (page.contains("Employee's PIN")) {
                String p9Text[] = payslipText[8].split("Employee's PIN");
                // System.out.print("2=====xxxxxxxxxxxxxxxxxxxxxName of Employee========" +
                // p9Text[0].trim());
                System.out.println("2=====xxxxxxxxxxxxxxxxxxx PF Number========" + p9Text[1].trim());
                System.out.println("-------------------------------------");
                data.setPinNumber(p9Text[1].trim());
                data.setEmpName(p9Text[0].trim());
            }

            if (page.contains("Employee No.")) {

                if (payslipText.length > 1) {
                    pfNumberRow = payslipText[1].split("Employee No.");
                    if (pfNumberRow.length > 1) {
                        // System.out.print(payslipText[1]+"^==='"+sourcePath+" HAS
                        // PF===============ffffffffffffffffffffffffff===========================" +
                        // pfNumberRow[1].trim()+"\n");
                    } else {
                        // System.out.print(sourcePath+" No PFN
                        // ===============--------------------==========================="+"\n");
                    }

                } else {
                    // System.out.print(" No text
                    // yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy================="
                    // +sourcePath +"\n");
                }
                // System.out.println("333=====xxxxxxxxxxxxxxxxxxxxxKRA PIN of Employee========"
                // + data.getPinNumber());

                if (pfNumberRow.length > 1 && StringUtils.isNotBlank(pfNumberRow[1])
                        && (StringUtils.isNotEmpty(pfNumberRow[1]) || StringUtils.isNotEmpty(data.getPinNumber()))) {
                    data.setPfNumber(pfNumberRow[1].trim());
                    if (StringUtils.isEmpty(data.getPinNumber()))
                        data.setEmpName(payslipText[2].trim());
                    data.setPayPeriod(payslipText[3].trim());
                    // System.out.print(data.getEmpName() + "====444=====xxxxxxxxxxxxxxxxxxxxxKRA
                    // PIN of Employee========"
                    // + data.getPinNumber());
                } else {

                    return null;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        return data;
    }

    @Override
    public void splitPdf(final String source, final String destFolder) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(source);
        int nps = reader.getNumberOfPages();
        int i = 0;
        while (i < nps) {
            String outFile = destFolder + "spt_" + String.format("%03d", i + 1) + ".pdf";
            Document document = new Document(reader.getPageSizeWithRotation(1));
            PdfCopy writer = new PdfCopy(document, new FileOutputStream(outFile));
            document.open();
            PdfImportedPage page = writer.getImportedPage(reader, ++i);
            writer.addPage(page);
            document.close();
            writer.close();
        }

    }

    @Override
    public void encryptDirFiles(String source, String destination, String no_contacts_dir)
            throws IOException, DocumentException {
        File folder = new File(source);
        File[] listOfFiles = folder.listFiles();
        String encryptedFilePath = "";

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                String filePath = source + fileName;
                String movedFile = no_contacts_dir + fileName;
                File varTmpDir = new File(filePath);
                boolean exists = varTmpDir.exists();

                if (exists) {
                    try {
                        PayslipData data = this.getPFNumber(filePath);
                        String newperiod = data.getPayPeriod().replace(" - ", "_");
                        newperiod = newperiod.replace("Period: ", "");
                        // =exxxxxxxxxxxxxxxxxxxxxeeeegetPayPeriod=" + data.getPayPeriod());
                        if (data != null) {

                            encryptedFilePath = destination + newperiod + "_" + data.getPfNumber().trim() + ".pdf";
                            System.out
                                    .print("encryptedFilePathencryptedFilePathencryptedFilePath==" + encryptedFilePath);

                            try {
                                List<Contact> contacts = null;
                                contacts = contactService.searchContactByPf(data.getPfNumber());
                                if (contacts.size() > 0) {
                                    String userPWD = contacts.get(0).getIdNumber().trim();
                                    String ownPWD = contacts.get(0).getIdNumber().trim();
                                    Encrypt.encryptPdf(filePath, encryptedFilePath, userPWD, ownPWD);
                                    // Remove from no contacts after resenting it
                                    if (StringUtils.contains(filePath, "no_contacts")) {
                                        this.deleteFile(filePath);
                                    }
                                } else {
                                    try {
                                        File newFile = new File(movedFile);
                                        boolean existsOnNewDir = newFile.exists();
                                        if (!existsOnNewDir) {
                                            FileUtils.moveFile(varTmpDir, newFile);
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        } else {
                            System.out.print(filePath);
                        }

                    } catch (Exception e) {
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
