package org.spt.service;

import com.itextpdf.text.DocumentException;
import org.spt.model.PayslipData;

import java.io.IOException;

/**
 * Interface for the Document Service
 */
public interface DocumentService {
    public PayslipData getPFNumber(String sourcePath);
    public void splitPdf( final String source ,final String destFolder) throws IOException;
    public void encryptDirFiles(String source, String destination,String no_contacts_dir)  throws IOException, DocumentException;
    public void clearFileDir(String dir) throws IOException;
    public void deleteFile(String filePath) throws IOException;
    public void addBarcode(String fileDir, String message) throws IOException;
    public void authenticateUser(String username, String password);
                                                                                                                                                                             

}

