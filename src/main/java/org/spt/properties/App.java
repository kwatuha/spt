package org.spt.properties;

/**
 * Created by ALFAYO on 7/12/2017.
 */
import java.io.*;
import java.util.Properties;


public class App {
    public static void createPropertiesFile(String fileName,Properties prop) {


        OutputStream output = null;

        try {
            output = new FileOutputStream(fileName);
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static Properties getProperties(String fileName) {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(fileName);

            // load a properties file
            prop.load(input);

            // get the property value and print it out

            System.out.println(prop.getProperty(SptConstants.GP_SPT_FILE_DIR));
            System.out.println(prop.getProperty(SptConstants.GP_EMAIL_ADDRESS));
            System.out.println(prop.getProperty(SptConstants.GP_EMAIL_PWD));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
 return prop;
    }

    public static void deleteFile(String filePath) {
        try {
            if (filePath != null) {
                File fileToDelete = new File(filePath);

                //delete the file
                fileToDelete.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}