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
            // System.out.println(payslipText[i] + " ===XXXX=== " + i);
            // }

            if (page.contains("Employee's PIN")) {
                String p9Text[] = payslipText[8].split("Employee's PIN");
                data.setPinNumber(p9Text[1].trim());
                data.setEmpName(p9Text[0].trim());
                data.setP9Period(payslipText[3]);
            }

            if (page.contains("Employee No.")) {
                pfNumberRow = payslipText[1].split("Employee No.");
                if (pfNumberRow.length > 1 && StringUtils.isNotBlank(pfNumberRow[1])
                        && (StringUtils.isNotEmpty(pfNumberRow[1]))) {
                    data.setPfNumber(pfNumberRow[1].trim());
                    data.setEmpName(payslipText[2].trim());
                    data.setPayPeriod(payslipText[3].trim());
                } else {

                    return null;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        // System.out.println(data.getPinNumber() + " ===XXXX=== " + data.getPfNumber()
        // + "===" + data.getEmpName());
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
        reader.close();

    }

    @Override
    public void encryptDirFiles(String source, String destination, String no_contacts_dir)
            throws IOException, DocumentException {
        File folder = new File(source);
        File[] listOfFiles = folder.listFiles();
        String encryptedFilePath = "";
        String searchKey = "";

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
                        String newperiod = StringUtils.isNotBlank(data.getPayPeriod())
                                ? data.getPayPeriod().replace(" - ", "_")
                                : null;
                        newperiod = StringUtils.isNotBlank(newperiod) ? newperiod.replace("Period: ", "") : null;

                        if (data != null && (StringUtils.isNotBlank(data.getPfNumber())
                                || StringUtils.isNotBlank(data.getPinNumber()))) {

                            if (StringUtils.isNotBlank(data.getPfNumber())) {
                                encryptedFilePath = destination + newperiod + "_" + data.getPfNumber().trim() + ".pdf";
                                searchKey = data.getPfNumber().trim();

                            }
                            if (StringUtils.isNotBlank(data.getPinNumber())) {
                                encryptedFilePath = destination + data.getP9Period().replace(" ", "_") + "_"
                                        + data.getPinNumber().trim() + ".pdf";
                                searchKey = data.getPinNumber().trim();

                            }

                            try {
                                if (StringUtils.isNotBlank(data.getEmpName())) {

                                    List<Contact> contacts = null;
                                    contacts = contactService.searchContactByPf(searchKey);
                                    if (contacts.size() > 0) {
                                        String userPWD = contacts.get(0).getIdNumber().trim();
                                        String ownPWD = contacts.get(0).getIdNumber().trim();
                                        Encrypt.encryptPdf(filePath, encryptedFilePath, userPWD, ownPWD);
                                        this.deleteFile(filePath);
                                    } else {
                                        try {
                                            File newFile = new File(movedFile);
                                            boolean existsOnNewDir = newFile.exists();
                                            if (!existsOnNewDir) {
                                                FileUtils.moveFile(varTmpDir, newFile);
                                            }
                                            this.deleteFile(filePath);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        } else {
                            System.out.println("deleted " + filePath);
                            deleteFile(filePath);
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
        File f1 = new File(filePath);
        RandomAccessFile raf = new RandomAccessFile(f1, "rw");
        raf.close();
        FileUtils.forceDelete(f1);
    }
}
