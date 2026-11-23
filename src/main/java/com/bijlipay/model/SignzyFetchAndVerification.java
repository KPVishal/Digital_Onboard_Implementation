package com.bijlipay.model;

import javax.persistence.*;

@Entity
@Table(name = "signzy_fetch_verification")
public class SignzyFetchAndVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne(optional = true)
    @JoinColumn(name = "uploaded_doc_id",referencedColumnName = "uploaded_document_id")
    private UploadedDocuments uploadedDocuments;
    @Column(name = "aadhaar_number")
    private String aadhaar_number;
    @Column(name = "aadhaar_details",columnDefinition = "TEXT")
    private String aadhaarDetails;
    @Column(name = "is_aadhaar_verified",columnDefinition = "TEXT")
    private String aadhaarVerified;
    @Column(name = "pan_number")
    private String panNumber;
    @Column(name = "pan_details",columnDefinition = "TEXT")
    private String panDetails;
    @Column(name = "is_pan_fetched" , columnDefinition = "TEXT")
    private String panFetched;
    @Column(name = "is_pan_verified" , columnDefinition = "TEXT")
    private String panVerified;
    @Column(name = "passport_file_number")
    private String passportFileNumber;
    @Column(name = "passport_dob")
    private String passportDOB;
    @Column(name = "passport_name")
    private String passportName;
    @Column(name = "passport_details",columnDefinition = "TEXT")
    private String passportDetails;
    @Column(name = "is_passport_fetched")
    private boolean passportFetched;
    @Column(name ="is_passport_verified")
    private String passportVerified;
    @Column(name = "voterid_epic_number")
    private String voterIdEpicNumber;
    @Column(name = "voterid_name")
    private String voterIdName;
    @Column(name = "voterid_state")
    private String voterIdState;
    @Column(name = "voterid_details",columnDefinition = "TEXT")
    private String voterIdDetails;

    @Column(name = "is_voterid_fetched")
    private boolean voterIdFetched;
    @Column(name ="is_voterid_verified")
    private boolean voterIdVerified;
    @Column(name = "dl_number")
    private String dlNumber;
    @Column(name = "dl_dob")
    private String dlDOB;
    @Column(name = "dl_issue_date")
    private String dlIssueDate;
    @Column(name = "dl_details",columnDefinition = "TEXT")
    private String dlDetails;

    @Column(name = "is_dl_fetched")
    private boolean dLFetched;
    @Column(name = "is_dl_verified")
    private String isDLVerified;
    @Column(name = "gstin")
    private String gstinNumber;
    @Column(name = "gstin_details",columnDefinition = "TEXT")
    private String gstInDetails;
    @Column(name = "is_gstin_fetched")
    private boolean gstinFetched;
    @Column(name = "is_gstin_verified")
    private boolean gstINVerified;
    @Column(name = "message" , nullable = true)
    private String message;
    @Column(name = "type_of_holder", nullable = true)
    private String typeOfHolder;
    @Column(name = "aadhaar_url", columnDefinition = "TEXT", nullable = true)
    private String aadhaarUrl;
    @Column(name = "request_id", nullable = true)
    private String request_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UploadedDocuments getUploadedDocuments() {
        return uploadedDocuments;
    }

    public void setUploadedDocuments(UploadedDocuments uploadedDocuments) {
        this.uploadedDocuments = uploadedDocuments;
    }

    public String getAadhaar_number() {
        return aadhaar_number;
    }

    public void setAadhaar_number(String aadhaar_number) {
        this.aadhaar_number = aadhaar_number;
    }

    public String getAadhaarDetails() {
        return aadhaarDetails;
    }

    public void setAadhaarDetails(String aadhaarDetails) {
        this.aadhaarDetails = aadhaarDetails;
    }

    public String getAadhaarVerified() {
        return aadhaarVerified;
    }

    public void setAadhaarVerified(String aadhaarVerified) {
        this.aadhaarVerified = aadhaarVerified;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getPanDetails() {
        return panDetails;
    }

    public void setPanDetails(String panDetails) {
        this.panDetails = panDetails;
    }

    public String getPanFetched() {
        return panFetched;
    }

    public void setPanFetched(String panFetched) {
        this.panFetched = panFetched;
    }

    public String getPanVerified() {
        return panVerified;
    }

    public void setPanVerified(String panVerified) {
        this.panVerified = panVerified;
    }

    public String getPassportFileNumber() {
        return passportFileNumber;
    }

    public void setPassportFileNumber(String passportFileNumber) {
        this.passportFileNumber = passportFileNumber;
    }

    public String getPassportDOB() {
        return passportDOB;
    }

    public void setPassportDOB(String passportDOB) {
        this.passportDOB = passportDOB;
    }

    public String getPassportName() {
        return passportName;
    }

    public void setPassportName(String passportName) {
        this.passportName = passportName;
    }

    public String getPassportDetails() {
        return passportDetails;
    }

    public void setPassportDetails(String passportDetails) {
        this.passportDetails = passportDetails;
    }

    public boolean isPassportFetched() {
        return passportFetched;
    }

    public void setPassportFetched(boolean passportFetched) {
        this.passportFetched = passportFetched;
    }

    public String getPassportVerified() {
        return passportVerified;
    }

    public void setPassportVerified(String passportVerified) {
        this.passportVerified = passportVerified;
    }

    public String getVoterIdEpicNumber() {
        return voterIdEpicNumber;
    }

    public void setVoterIdEpicNumber(String voterIdEpicNumber) {
        this.voterIdEpicNumber = voterIdEpicNumber;
    }

    public String getVoterIdName() {
        return voterIdName;
    }

    public void setVoterIdName(String voterIdName) {
        this.voterIdName = voterIdName;
    }

    public String getVoterIdState() {
        return voterIdState;
    }

    public void setVoterIdState(String voterIdState) {
        this.voterIdState = voterIdState;
    }

    public String getVoterIdDetails() {
        return voterIdDetails;
    }

    public void setVoterIdDetails(String voterIdDetails) {
        this.voterIdDetails = voterIdDetails;
    }

    public boolean isVoterIdFetched() {
        return voterIdFetched;
    }

    public void setVoterIdFetched(boolean voterIdFetched) {
        this.voterIdFetched = voterIdFetched;
    }

    public boolean isVoterIdVerified() {
        return voterIdVerified;
    }

    public void setVoterIdVerified(boolean voterIdVerified) {
        this.voterIdVerified = voterIdVerified;
    }

    public String getDlNumber() {
        return dlNumber;
    }

    public void setDlNumber(String dlNumber) {
        this.dlNumber = dlNumber;
    }

    public String getDlDOB() {
        return dlDOB;
    }

    public void setDlDOB(String dlDOB) {
        this.dlDOB = dlDOB;
    }

    public String getDlIssueDate() {
        return dlIssueDate;
    }

    public void setDlIssueDate(String dlIssueDate) {
        this.dlIssueDate = dlIssueDate;
    }

    public String getDlDetails() {
        return dlDetails;
    }

    public void setDlDetails(String dlDetails) {
        this.dlDetails = dlDetails;
    }

    public boolean isdLFetched() {
        return dLFetched;
    }

    public void setdLFetched(boolean dLFetched) {
        this.dLFetched = dLFetched;
    }

    public String getIsDLVerified() {
        return isDLVerified;
    }

    public void setIsDLVerified(String isDLVerified) {
        this.isDLVerified = isDLVerified;
    }

    public String getGstinNumber() {
        return gstinNumber;
    }

    public void setGstinNumber(String gstinNumber) {
        this.gstinNumber = gstinNumber;
    }

    public String getGstInDetails() {
        return gstInDetails;
    }

    public void setGstInDetails(String gstInDetails) {
        this.gstInDetails = gstInDetails;
    }

    public boolean isGstinFetched() {
        return gstinFetched;
    }

    public void setGstinFetched(boolean gstinFetched) {
        this.gstinFetched = gstinFetched;
    }

    public boolean isGstINVerified() {
        return gstINVerified;
    }

    public void setGstINVerified(boolean gstINVerified) {
        this.gstINVerified = gstINVerified;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTypeOfHolder() {
        return typeOfHolder;
    }

    public void setTypeOfHolder(String typeOfHolder) {
        this.typeOfHolder = typeOfHolder;
    }

    public String getAadhaarUrl() {
        return aadhaarUrl;
    }

    public void setAadhaarUrl(String aadhaarUrl) {
        this.aadhaarUrl = aadhaarUrl;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    @Override
    public String toString() {
        return "SignzyFetchAndVerification{" +
                "id=" + id +
                ", uploadedDocuments=" + uploadedDocuments +
                ", aadhaar_number='" + aadhaar_number + '\'' +
                ", aadhaarDetails='" + aadhaarDetails + '\'' +
                ", aadhaarVerified='" + aadhaarVerified + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", panDetails='" + panDetails + '\'' +
                ", panFetched='" + panFetched + '\'' +
                ", panVerified='" + panVerified + '\'' +
                ", passportFileNumber='" + passportFileNumber + '\'' +
                ", passportDOB='" + passportDOB + '\'' +
                ", passportName='" + passportName + '\'' +
                ", passportDetails='" + passportDetails + '\'' +
                ", passportFetched=" + passportFetched +
                ", passportVerified='" + passportVerified + '\'' +
                ", voterIdEpicNumber='" + voterIdEpicNumber + '\'' +
                ", voterIdName='" + voterIdName + '\'' +
                ", voterIdState='" + voterIdState + '\'' +
                ", voterIdDetails='" + voterIdDetails + '\'' +
                ", voterIdFetched=" + voterIdFetched +
                ", voterIdVerified=" + voterIdVerified +
                ", dlNumber='" + dlNumber + '\'' +
                ", dlDOB='" + dlDOB + '\'' +
                ", dlIssueDate='" + dlIssueDate + '\'' +
                ", dlDetails='" + dlDetails + '\'' +
                ", dLFetched=" + dLFetched +
                ", isDLVerified='" + isDLVerified + '\'' +
                ", gstinNumber='" + gstinNumber + '\'' +
                ", gstInDetails='" + gstInDetails + '\'' +
                ", gstinFetched=" + gstinFetched +
                ", gstINVerified=" + gstINVerified +
                ", message='" + message + '\'' +
                ", typeOfHolder='" + typeOfHolder + '\'' +
                ", aadhaarUrl='" + aadhaarUrl + '\'' +
                ", request_id='" + request_id + '\'' +
                '}';
    }
}
