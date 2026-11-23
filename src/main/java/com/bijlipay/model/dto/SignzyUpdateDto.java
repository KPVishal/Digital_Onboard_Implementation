package com.bijlipay.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignzyUpdateDto {

    private boolean isVerified;
    private String message;
    private String name;
    private String dob;

    //pan details
    private String panNumber;
    private String isIndividual;
    private String isValid;
    private String firstName;
    private String middleName;
    private String lastName;
    private String panStatus;

    //passport details
    private String fileNumber;
    private String typeOfApplication;

    //voterId details
    private String state;
    private String district;
    private String fatherName;
    private String gender;
    private String epicNumber;

    //driving Licence
    private String dateOfIssue;
    private String fatherOrHusbandName;
    private String dlNumber;

    // GST
    private String constitutionOfBusiness;
    private String legalNameOfBusiness;
    private String tradeNameOfBusiness;
    private String registrationDate;
    private String taxPayerType;
    private String gstinStatus;
    private String gstin;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsIndividual() {
        return isIndividual;
    }

    public void setIsIndividual(String isIndividual) {
        this.isIndividual = isIndividual;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPanStatus() {
        return panStatus;
    }

    public void setPanStatus(String panStatus) {
        this.panStatus = panStatus;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getTypeOfApplication() {
        return typeOfApplication;
    }

    public void setTypeOfApplication(String typeOfApplication) {
        this.typeOfApplication = typeOfApplication;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEpicNumber() {
        return epicNumber;
    }

    public void setEpicNumber(String epicNumber) {
        this.epicNumber = epicNumber;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getFatherOrHusbandName() {
        return fatherOrHusbandName;
    }

    public void setFatherOrHusbandName(String fatherOrHusbandName) {
        this.fatherOrHusbandName = fatherOrHusbandName;
    }

    public String getDlNumber() {
        return dlNumber;
    }

    public void setDlNumber(String dlNumber) {
        this.dlNumber = dlNumber;
    }

    public String getConstitutionOfBusiness() {
        return constitutionOfBusiness;
    }

    public void setConstitutionOfBusiness(String constitutionOfBusiness) {
        this.constitutionOfBusiness = constitutionOfBusiness;
    }

    public String getLegalNameOfBusiness() {
        return legalNameOfBusiness;
    }

    public void setLegalNameOfBusiness(String legalNameOfBusiness) {
        this.legalNameOfBusiness = legalNameOfBusiness;
    }

    public String getTradeNameOfBusiness() {
        return tradeNameOfBusiness;
    }

    public void setTradeNameOfBusiness(String tradeNameOfBusiness) {
        this.tradeNameOfBusiness = tradeNameOfBusiness;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getTaxPayerType() {
        return taxPayerType;
    }

    public void setTaxPayerType(String taxPayerType) {
        this.taxPayerType = taxPayerType;
    }

    public String getGstinStatus() {
        return gstinStatus;
    }

    public void setGstinStatus(String gstinStatus) {
        this.gstinStatus = gstinStatus;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    @Override
    public String toString() {
        return "SignzyUpdateDto{" +
                "isVerified=" + isVerified +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", isIndividual='" + isIndividual + '\'' +
                ", isValid='" + isValid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", panStatus='" + panStatus + '\'' +
                ", fileNumber='" + fileNumber + '\'' +
                ", typeOfApplication='" + typeOfApplication + '\'' +
                ", state='" + state + '\'' +
                ", district='" + district + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", gender='" + gender + '\'' +
                ", epicNumber='" + epicNumber + '\'' +
                ", dateOfIssue='" + dateOfIssue + '\'' +
                ", fatherOrHusbandName='" + fatherOrHusbandName + '\'' +
                ", dlNumber='" + dlNumber + '\'' +
                ", constitutionOfBusiness='" + constitutionOfBusiness + '\'' +
                ", legalNameOfBusiness='" + legalNameOfBusiness + '\'' +
                ", tradeNameOfBusiness='" + tradeNameOfBusiness + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", taxPayerType='" + taxPayerType + '\'' +
                ", gstinStatus='" + gstinStatus + '\'' +
                ", gstin='" + gstin + '\'' +
                '}';
    }
}
