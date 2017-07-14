package org.spt.model;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PageRange;
import com.itextpdf.kernel.utils.PdfSplitter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by ALFAYO on 6/21/2017.
 */
public class SplitPDF {

    public static void splitPdf( final String source ,final String destFolder) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(source));

        List<PdfDocument> splitDocuments = new PdfSplitter(pdfDoc) {
            int partNumber = 1;
            @Override
            protected PdfWriter getNextPdfWriter(PageRange documentPageRange) {
                try {
                    return new PdfWriter(destFolder + "splitDocument1_" + String.valueOf(partNumber++) + ".pdf");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException();
                }
            }
        }.splitBySize(200000);

        for (PdfDocument doc : splitDocuments)
            doc.close();
        pdfDoc.close();
    }
}
