package com.bijlipay.service;

import com.bijlipay.model.*;
import com.bijlipay.model.dto.AggLeadApproveRequest;
import com.bijlipay.model.dto.CounterDto;
import com.bijlipay.model.dto.MccApproveDto;
import com.bijlipay.model.dto.TidVpaMap;
import com.bijlipay.model.marsdto.EDCMarsResponseDto;
import com.bijlipay.model.marsdto.SavingToTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LeadInformationService {

    TidVpaMapping checkVpaExists(TidVpaMap tidVpaMapDto);

    LeadInformation findLead(long leadId);

    MarsOnboardingInformation setMarsDetails(LeadInformation leadInformation) throws JsonProcessingException;

    SavingToTable setValuesToDto(EDCMarsResponseDto edcMarsResponseDto);

    MarsDeviceDetails saveDatasToMarsAndLeadTable(SavingToTable savingToTable);
//    MarsDeviceDetails saveDatasToMarsAndLeadTable1(SavingToTable savingToTable);

    Pair<Integer, Object> enableSettlement(MarsDeviceDetails deviceDetails);

    MarsDeviceDetails activateVpa(TidVpaMap tidVpaMapDto);


    boolean checkAllAggregatorDocumentsVerifiedByOps(LeadInformation leadInformation);

    Pair<Integer, Object> aggregatorLeadApproval(LeadInformation leadInformation, String merchantCategoryCode, User curuser ,String aml_status);

    List<CounterDto> listOfCounter(long leadId);

    LeadInformation updateHostData(LeadInformation leadInformation1, LeadInformation leadInformation);

    Pair<Integer, Object> amlStatusUpdated(LeadInformation leadInformation,AggLeadApproveRequest data);

    /*    void callbackapproval(LeadInformation leadInformation);*/

  /*  void documentCallback(MccApproveDto mccApproveDto);*/
}
