package org.spt.service;

import com.itextpdf.text.DocumentException;
import org.spt.model.PayslipData;

import java.io.IOException;

/**
 * Interface for the Contact Service
 */
public interface DocumentService {
    public PayslipData getPFNumber(String sourcePath);

    public void splitPdf(final String source, final String destFolder) throws IOException, DocumentException;

    public void encryptDirFiles(String source, String destination, String no_contacts_dir)
            throws IOException, DocumentException;

    public void clearFileDir(String dir) throws IOException;

    public void deleteFile(String filePath) throws IOException;

}
