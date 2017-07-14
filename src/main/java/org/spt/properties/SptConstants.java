package org.spt.properties;

/**
 * Created by ALFAYO on 7/10/2017.
 */
public class SptConstants {
    /**
     * Name of the global properties for the directory where payroll and contact special files are stored.
     */
    public final static String GP_SPT_FILE_DIR = "sptFileDir";
    public final static String GP_SPT_ENCRYPTED_FILE_DIR = SptConstants.GP_SPT_FILE_DIR+"\\encrypted\\";
    public final static String GP_SPT_QUEUED_FILE_DIR = SptConstants.GP_SPT_FILE_DIR+"\\queued\\";
    public final static String GP_PF_HAS_NO_EMAIL_ADDRESS_DIR = SptConstants.GP_SPT_FILE_DIR+"\\no_address\\";
    public final static String GP_EMAIL_ADDRESS = "emailAddress";
    public final static String GP_EMAIL_PWD = "emailPassWord";


}
