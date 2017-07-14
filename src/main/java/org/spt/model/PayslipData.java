package org.spt.model;

public class PayslipData {

    private Integer id;
    private String payPeriod;
    private String pfNumber;
    private String empName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public String getPfNumber() {
        return pfNumber;
    }

    public void   setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    public String getEmpName() {
        return empName;
    }

    public void   setEmpName(String empName) {
        this.empName = empName;
    }




}
