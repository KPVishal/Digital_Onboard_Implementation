package com.bijlipay.model;

import javax.persistence.*;

@Entity
@Table(name = "file_transfer")
public class FileTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    private long mbNo;
    private String mailId;
    private String name;
    private long AccountNumber
    ;
    private String PanNumber
    ;
    private String ContactName
    ;
    private long ProhibitedMCC;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMbNo() {
        return mbNo;
    }

    public void setMbNo(long mbNo) {
        this.mbNo = mbNo;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getPanNumber() {
        return PanNumber;
    }

    public void setPanNumber(String panNumber) {
        PanNumber = panNumber;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public long getProhibitedMCC() {
        return ProhibitedMCC;
    }

    public void setProhibitedMCC(long prohibitedMCC) {
        ProhibitedMCC = prohibitedMCC;
    }

    @Override
    public String toString() {
        return "FileTransfer{" +
                "id=" + id +
                ", mbNo=" + mbNo +
                ", mailId='" + mailId + '\'' +
                ", name='" + name + '\'' +
                ", AccountNumber='" + AccountNumber + '\'' +
                ", PanNumber='" + PanNumber + '\'' +
                ", ContactName='" + ContactName + '\'' +
                ", ProhibitedMCC='" + ProhibitedMCC + '\'' +
                '}';
    }
}
