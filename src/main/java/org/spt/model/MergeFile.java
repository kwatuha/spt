package org.spt.model;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by ALFAYO on 7/14/2017.
 */
public class MergeFile {
//    public Map<String, PdfDocument> filesToMerge;
//    public static final String DEST = "./target/test/resources/sandbox/merge/merge_with_toc.pdf";
//    public static final String SRC1 = "./src/test/resources/pdfs/united_states.pdf";
//    public static final String SRC2 = "./src/test/resources/pdfs/hello.pdf";
//    public static final String SRC3 = "./src/test/resources/pdfs/toc.pdf";




//    public static void mergeFiles() throws IOException {
//        String parentFile="c:/tomcat6/spt/tests/3684_test.pdf";
//        String restFile="c:/tomcat6/spt/tests/3684_test_merged.pdf";
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(restFile));
//       Map<String, PdfDocument> fileList=new TreeMap<String, PdfDocument>();
//        for(int i=0;i<=10;i++){
//            fileList.put("Document "+i, new PdfDocument(new PdfReader(parentFile)));
//        }
//
//
//
//    }
    public static void createPdf(String filename, PdfReader[] readers)
            throws IOException, DocumentException {
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(filename));
        copy.setMergeFields();
        document.open();
        for (PdfReader reader : readers) {
            copy.addDocument(reader);
        }
        document.close();
        for (PdfReader reader : readers) {
            reader.close();
        }
    }

}
