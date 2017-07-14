package org.spt.model;

/**
 * Created by ALFAYO on 6/21/2017.
 */
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

//iText imports

public class SptPDFReader {

        public static void readPdfData(String sourcePath, String destinationPath) {
            try {

                PdfReader reader = new PdfReader(sourcePath);
                System.out.println("This PDF has "+reader.getNumberOfPages()+" pages.");
                String page = PdfTextExtractor.getTextFromPage(reader, 2);
                String s1[]=page.split("\n");
                String Empny=s1[1];
                System.out.println(Empny+"=Page Content:\n\n"+page+"\n\n");
                System.out.println("Is this document tampered: "+reader.isTampered());
                System.out.println("Is this document encrypted: "+reader.isEncrypted());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    public static String  getPFNumber(String sourcePath) {
            String pfNumber="";
        try {
            PdfReader reader = new PdfReader(sourcePath);
            String page = PdfTextExtractor.getTextFromPage(reader, 2);
            String s1[]=page.split("\n");
            pfNumber= s1[1];


        } catch (IOException e) {
            e.printStackTrace();
        }
      return pfNumber;
    }

    public static void createPdf(String filename, List<PdfReader> readers)
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

    public static void mergePdfFiles() throws IOException,DocumentException{
        String parentFile="c:/tomcat6/spt/tests/3684_test.pdf";
        String restFile="c:/tomcat6/spt/tests/3684_test_merged.pdf";
        List<PdfReader>readers=new ArrayList<PdfReader>();
        for(int i=0;i<5;i++){
            PdfReader reader = new PdfReader(parentFile);
            readers.add(reader);
        }
           SptPDFReader.createPdf(restFile,readers);
    }

    public static void manipulatePdf() throws DocumentException, IOException {
        String dest="c:/tomcat6/spt/tests/3684_test_merged.pdf";
        String DATA="c:/tomcat6/spt/uploaded/sample_employee_data_ampath.csv";
        String SRC="c:/tomcat6/spt/tests/splitDocument1_1.pdf";

        Document document = new Document();
        PdfCopy copy = new PdfSmartCopy(document, new FileOutputStream(dest));
        document.open();
       PdfReader r=new PdfReader(SRC);
        for(int i=0;i<5;i++){
            copy.addDocument(r);
        }
        document.close();
    }

}
