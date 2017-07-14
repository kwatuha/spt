package org.spt.model;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import java.util.Map;


/**
 * Created by ALFAYO on 6/20/2017.
 */
public class Encrypt {

    /**
     * Manipulates a PDF file src with the file dest as result
     * @param src the original PDF
     * @param dest the resulting PDF
     * @param userPassword to encrypt the resulting PDF
     * @param ownerPassword to encrypt the resulting PDF
     * @throws IOException
     * @throws DocumentException
     */
    public static void encryptPdf(final String src, final String dest, final String userPassword, final String ownerPassword) throws FileNotFoundException,IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.setEncryption(userPassword.getBytes(), ownerPassword.getBytes(),
                PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
        stamper.close();
        reader.close();


    }




}
