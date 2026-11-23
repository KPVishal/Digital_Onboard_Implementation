package com.bijlipay.service;

import com.bijlipay.model.*;
import com.bijlipay.model.dto.*;
import com.bijlipay.model.marsdto.EDCMarsResponseDto;
import com.bijlipay.model.marsdto.SavingToTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface ImplementationService {
    MarsOnboardingInformation setMarsDetails(LeadInformation leadInformation) throws JsonProcessingException, ParseException;

    MarsDeviceDetails saveDatasToMarsAndLeadTable(SavingToTable savingToTable);

    MarsDeviceDetails saveDatasToMarsAndLeadTableAddOnPos(SavingToTable savingToTable);

    MarsDeviceDetails saveDatasToMarsAndLeadTableAddOn(SavingToTable savingToTable);

    Pair<Integer,Object> tmsOnboardingretry(SavingToTable savingToTable, MarsDeviceDetails dataStorage);

    MarsDeviceDetails saveDatasToMarsAndLeadTable1(SavingToTable savingToTable);

    SavingToTable setValuesToDto(EDCMarsResponseDto edcMarsResponseDto);

    List<MarsDeviceDetails> getImplementationDetailsApp(LeadInformation leadInformation, User sessionUser);

    List<HashMap<String, Object>> getmerchantdataStatename();

    List<HashMap<String, Object>> getmerchantdataDistrictname(Optional<String> statename);

    List<HashMap<String, Object>> getmerchantdataSubDistrictname(Optional<String> statename, Optional<String> districtname);

    List<HashMap<String, Object>> getmerchantdataTownVillagename(Optional<String> statename,Optional<String> districtname,Optional<String> subDistrictname);

    String getmerchantTiername(Optional<String> statename,Optional<String> districtname,Optional<String> subDistrictname,Optional<String> villagename );

    LeadInformation checkLead(long leadId);

    Pair<Integer, Object> leadApproval(long leadId, LeadInformation leadInformation) throws JsonProcessingException;

    Pair<Integer, Object> leadApprovalDcb(long leadId, LeadInformation leadInformation);

    Pair<Integer, Object> leadReject(long lead, long action, LeadInformation leadInformation);

    void sendSmsToMerchantForReschedule(long leadId);

    void sendSmsToMerchantForApprove(long leadId);

    void sendSmsToMerchantForReject(long leadId);

    LeadInformation check(long leadId);


    List<UploadedDocuments> findByLeadId(LeadInformation leadInformation);

    List<SignzyFetchAndVerification> getPanDetails(List<UploadedDocuments> uploadedDocumentsList);

    boolean checkAllDocumentVerifiedByOps(LeadInformation leadInformation1);

    MarsDeviceDetails tidCheck(String tid);

    AmountDto collectAmountDetails(String tid) throws SQLException;

    void updateLeadAndMarsData(MarsDeviceDetails marsDeviceDetails) throws ExecutionException, InterruptedException;

    void tmsOnboardAddOn(MarsDeviceDetails marsDeviceDetails1, SavingToTable savingToTable);
    Pair<Integer,String> tmsOnboardAddOnretriggered(MarsDeviceDetails marsDeviceDetails1, SavingToTable savingToTable);

    boolean checkAllAggregatorDocumentsVerifiedByOps(LeadInformation leadInfo);

    Pair<Integer, Object> aggregatorLeadApproval(long leadId) throws MessagingException;

    Pair<Integer, Object> aggregatorLeadReject(long leadId, LeadInformation leadInformation) throws MessagingException;

    void updateLeadAndMarsDataToOnboard(MarsDeviceDetails deviceDetails);

    MarsDeviceDetails updateLeadAndMarsDataToOnboard1(MarsDeviceDetails deviceDetails);


    boolean checkUploadedDocuments(LeadInformation leadInfo);

    void updateSwitchRejectedToOnboard(LeadInformation leadInfo);

    MarsOnboardingInformation setMarsDetails2(LeadInformation leadInfo) throws JsonProcessingException;
    LeadInformation findLeadByLeadNumber(String leadNumber);

    LeadMarsDataUpdateDto updateMarsDataFromInventory(String leadNumber, LeadMarsDataUpdateDto leadMarsDataUpdateDto);

    Pair<Integer, Object> aggregatorLeadRejectMcc(long leadId, DocumentUpdateDto DocumentUpdateDto, User curuser) throws MessagingException;
    MarsOnboardingInformation setQRMarsDetails(LeadInformation leadInformation) throws JsonProcessingException, ParseException;

    void updateLeadAndMarsDataFromDigiOnboardSqr(MarsDeviceDetails marsDeviceDetails1);

    Boolean check1(MarsDeviceDetails marsDeviceDetails, VpaQr vpaQr);

    List<String> getMccList();

    void tmsOnboard(MarsDeviceDetails marsDeviceDetails1, SavingToTable setValuesToDto);

    Pair<Integer, Object> leadApprovalAddOn(long leadId, LeadInformation leadInformation) throws JsonProcessingException;

    MarsDeviceDetails saveDatasToMarsAndLeadTablePos(SavingToTable setValuesToDto);

    Pair<Integer, Object> leadRejectAddOn(long leadId, long action, LeadInformation leadInformation);

    Pair<String, Object> merchantDeactivation(long leadId);

    void updateLeadImplOnboardPortal(MarsDeviceDetails marsDeviceDetails);

    void sms(String smsTemplate, String phn, String templateId);

    void updateLeadAndMarsDataToOnboardMulti(MarsDeviceDetails marsDeviceDetails);





    MarsOnboardingInformation setMarsDetailsAgg(LeadInformation leadInformation) throws JsonProcessingException, ParseException;

    void tmsOnboardQ161(MarsDeviceDetails marsDeviceDetails, MarsDeviceDetails marsDeviceDetail, SavingToTable setValuesToDto);

    //new api changes new api digi



    /**
     * Updates Lead Contact details & Triggers 6-digit Mobile OTP directly.
     * Enforces 60 seconds cooldown check.
     */
    boolean triggerMobileOTP(MobileOtpRequestDto dto);

    /**
     * Verifies OSV Mobile OTP with 24 hours validity window.
     */
    boolean verifyMobileOTP(MobileOtpRequestDto dto);


    void sendToRoleSubmit(String role, String payload);

    void sendToRoleTech(String role, String payload);


}
