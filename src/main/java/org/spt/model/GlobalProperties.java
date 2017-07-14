package org.spt.model;

import java.util.List;

/**
 * Created by ALFAYO on 7/10/2017.
 */
public class GlobalProperties {

    private int id;
    private String payrollFileDir;
    private String contactFileDir;
    private String messageQueueDir;
    private String emailAddress;
    private String emailAddressPwd;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPayrollFileDir() {
        return payrollFileDir;
    }
    public void setPayrollFileDir(String payrollFileDir) {
        this.payrollFileDir = payrollFileDir;
    }

    public void setContactFileDir(String contactFileDir) {
        this.contactFileDir = contactFileDir;
    }

    public String getContactFileDir() {
        return contactFileDir;
    }

    public void setMessageQueueDir(String messageQueueDir) {
        this.messageQueueDir = messageQueueDir;
    }

    public String getMessageQueueDir() {
        return messageQueueDir;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddressPwd(String emailAddressPwd) {
        this.emailAddressPwd = emailAddressPwd;
    }

    public String getEmailAddressPwd() {
        return emailAddressPwd;
    }
}
