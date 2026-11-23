package com.bijlipay.service.implementation;

import com.bijlipay.config.Constants;
import com.bijlipay.model.*;
import com.bijlipay.model.dto.AggLeadApproveRequest;
import com.bijlipay.model.dto.CounterDto;
import com.bijlipay.model.dto.MccApproveDto;
import com.bijlipay.model.dto.TidVpaMap;
import com.bijlipay.model.marsdto.*;
import com.bijlipay.repository.*;
import com.bijlipay.service.LeadInformationService;
import com.bijlipay.util.AttributeEncryptor;
import com.bijlipay.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.net.httpserver.Authenticator;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.bijlipay.config.Constants.*;

import com.bijlipay.controller.BaseController;


@Service
public class LeadInformationServiceImpl extends BaseController implements LeadInformationService {
    @Autowired
    private TidVpaMappingRepository tidVpaMappingRepository;

    @Autowired
    private LeadInformationRepository leadInformationRepository;

    @Autowired
    private AttributeEncryptor attributeEncryptor;


    @Autowired
    private MarsOnboardingRepository marsOnboardingRepository;

    @Autowired
    private MarsDeviceDetailsRepository marsDeviceDetailsRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LeadStatusRepository leadStatusRepository;
    private static final Logger logger = LoggerFactory.getLogger(LeadInformationServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LeadDocumentRepository leadDocumentRepository;
    @Autowired
    private MerchantCategoryCodeRepository merchantCategoryCodeRepository;
    @Autowired
    private AuditingTableRepository auditingTableRepository;
    //    @Autowired
//    private LeadDocumentRepository leadDocumentRepository;
    @Autowired
    private UploadedDocumentRepository uploadedDocumentRepository;
    @Autowired
    private CounterManagementRepository counterManagementRepository;
    @Autowired
    private InternalSubventionRepository internalSubventionRepository;

    //Aml Status

    @Autowired
    private AmlStatusRepository amlStatusRepository;

    @Value("${mars.vpa.settlement}")
    private String marsVpaSettlement;


    @Override
    public TidVpaMapping checkVpaExists(TidVpaMap tidVpaMapDto) {
        return tidVpaMappingRepository.findVpa(tidVpaMapDto.getVpa(), tidVpaMapDto.getTid());

    }

    @Override
    public LeadInformation findLead(long leadId) {
        return leadInformationRepository.findLead(leadId);
    }

    @Override
    public MarsOnboardingInformation setMarsDetails(LeadInformation leadInformation) throws JsonProcessingException {
        String appNo1 = null;
        if (leadInformation.getApplicationNumber() != null) {
            appNo1 = leadInformation.getApplicationNumber();
        } else {
            appNo1 = String.valueOf(new Date().getTime());
        }
//        String appNo = String.valueOf(new Date().getTime());
        logger.info("application number --> {}", appNo1);
        leadInformation.setQrApplicationNumber(appNo1);
        leadInformationRepository.save(leadInformation);

//        User user = getSessionUser();
        Gson gsonObject = new Gson();
        SalesInformation salesInformation = new SalesInformation();
        logger.info("Sales info data.............");


        salesInformation.setApplicationNumber(appNo1);
        if (leadInformation.getMarsMdrCharges() != null) {
            salesInformation.setCategoryType(leadInformation.getMarsMdrCharges().getType().equalsIgnoreCase("Small") ? "S" : "O");
        }
        salesInformation.setInstitutionCode(110); //UPI instance
        String leadFrom = "";
        if (leadInformation.getLeadSource() != null) {
            leadFrom = leadInformation.getLeadSource().getLeadSourceName();
        }
        salesInformation.setLeadFrom(leadFrom);
        salesInformation.setRegion(leadInformation.getMarsRegion());

        CompanyInformation companyInformation = new CompanyInformation();
        logger.info("Company info data.............");


        companyInformation.setConstitution(leadInformation.getConstitution()); //need to check
        companyInformation.setContactEmail(leadInformation.getContactEmail());
        companyInformation.setContactMobile(leadInformation.getContactNumber());
        companyInformation.setContactName(leadInformation.getContactName());
        companyInformation.setContactPhone("91" + leadInformation.getContactNumber());
        companyInformation.setDbaName(leadInformation.getDbaName());
        companyInformation.setEstablishYear(leadInformation.getEstablishYear()); // need to check
        companyInformation.setLegalName(leadInformation.getLegalName());
        companyInformation.setMcc(leadInformation.getMcc().getMccCode());
        companyInformation.setPan(leadInformation.getPanNumber()); //need to check
        companyInformation.setRegisteredAddress(leadInformation.getContactAddress());
        companyInformation.setRegisteredPin(String.valueOf(leadInformation.getPincode())); //need to check
        companyInformation.setRegisterNumber(leadInformation.getRegisterNumber());
        companyInformation.setStatementEmail(leadInformation.getContactEmail());
        companyInformation.setMarsState(leadInformation.getMerchantStateName());
        companyInformation.setMarsCity(leadInformation.getCity());


        BusinessInformation businessInformation = new BusinessInformation();

        logger.info("Business info data.............");
        businessInformation.setAverageBillAmount(leadInformation.getAvgBillAmount());
        businessInformation.setCurrentPosName("");
        businessInformation.setLastTurnoverAmount(leadInformation.getTurnOverLastFinancialYear() == null ? "" : leadInformation.getTurnOverLastFinancialYear()); //need to check
        businessInformation.setMaximumMonthlyUsage(leadInformation.getMaximumMonthlyUsage());
        businessInformation.setMemberSince(DateUtil.formattedTime(leadInformation.getMemberSince(), "dd/MM/yyyy"));
        businessInformation.setMerchantTypeCode(leadInformation.getMerchantTypeCode());
        businessInformation.setWeekdayEndHour(leadInformation.getWeekdayTimingEnd());
        businessInformation.setWeekdayStartHour(leadInformation.getWeekDayTimingStart());
        businessInformation.setWeekendEndHour(leadInformation.getWeekendTimingEnd());
        businessInformation.setWeekendStartHour(leadInformation.getWeekendTimingStart());

        List<PartnerInformation> partnerInformations = new ArrayList<>();

        PartnerInformation partnerInformation = new PartnerInformation();

        logger.info("Partner info data.............");
        partnerInformation.setContactEmail(leadInformation.getContactEmail());
        partnerInformation.setContactMobile(leadInformation.getContactNumber());
        partnerInformation.setPan(leadInformation.getPanNumber());
        partnerInformation.setPin(String.valueOf(leadInformation.getPincode()));
        partnerInformations.add(partnerInformation);
        PaymentDetails paymentDetails = new PaymentDetails();

        logger.info("Payment details info data.............");

        paymentDetails.setAdvanceRentCollected(String.valueOf(leadInformation.getAmountCollected()));
        paymentDetails.setAdvanceRentMode(leadInformation.getAdvanceRentMode());
        paymentDetails.setDeviceOwnedBy(leadInformation.getDeviceOwnedBy());
        paymentDetails.setDevicePrice(leadInformation.getDevicePrice());
        paymentDetails.setEmiStartDate(leadInformation.getEmiStartDate());
        paymentDetails.setEmiTenure(leadInformation.getEmiTenure());
        paymentDetails.setGracePeriod(String.valueOf(leadInformation.getGracePeriod()));
        paymentDetails.setInstallationFee(leadInformation.getMarsRentalPlans().getSetup_fees());
        paymentDetails.setNoOfMonthRentPaidInAdvance(String.valueOf(leadInformation.getNoOfMonthRentPaidInAdvance()));
        paymentDetails.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
        paymentDetails.setRentalMode(leadInformation.getRentalMode());
        paymentDetails.setRentalPlanCode(leadInformation.getMarsRentalPlans().getCode());
        paymentDetails.setRentalType(leadInformation.getRentalType());
        paymentDetails.setTerminalModeCode(leadInformation.getTerminalModeCode()); //need to check
        logger.info("deviceModeCode ---> {}", paymentDetails.getTerminalModeCode());
        paymentDetails.setTerminalType(leadInformation.getTerminalType());
        paymentDetails.setTotalEmiAmount(leadInformation.getTotalEmiAmount());
        PaymentDetails paymentDetails1 = new PaymentDetails();

        logger.info("REV PARAM info data.............");
        paymentDetails1.setBqrEnabled(String.valueOf(leadInformation.getBqrEnabled()));
        paymentDetails1.setCashAtPosEnabled(leadInformation.getCashAtPosEnabled()); //need+ to check
        paymentDetails1.setLinkpaymentFlag(String.valueOf(leadInformation.getLinkpaymentFlag()));
        paymentDetails1.setMatmAllowed(leadInformation.getMatmAllowed());
        paymentDetails1.setPreAuth(leadInformation.getPreAuth()); //need to check
        paymentDetails1.setSaleFlag(String.valueOf(leadInformation.getSaleFlag()));
        paymentDetails1.setSettlementType(leadInformation.getSettlement()); //need to check
//        paymentDetails1.setSodexoEnabled("0"); //need to check
        paymentDetails1.setTipEnabled(leadInformation.getTipEnabled()); //need to check
//        paymentDetails1.setTxnEmiAllowed("0"); //need to check
        paymentDetails1.setUpichargeslipEnabled(String.valueOf(leadInformation.getUpichargeslipEnabled())); //need to check
        paymentDetails1.setUpiFlag(String.valueOf(leadInformation.getUpiFlag())); //need to check
        paymentDetails1.setIsMTIDEnabled(String.valueOf(leadInformation.getUpiFlag()));
        MdrPlanDto mdrPlan = new MdrPlanDto();

        ObjectMapper mapper = new ObjectMapper();

        if (leadInformation.getMarsMdrCharges() != null) {
            mdrPlan.setMasterPass(mapper.readValue(leadInformation.getMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
            mdrPlan.setDiners(mapper.readValue(leadInformation.getMarsMdrCharges().getDiners(), MdrChargesDto.class));
            mdrPlan.setOnus(mapper.readValue(leadInformation.getMarsMdrCharges().getOnus(), MdrChargesDto.class));
            mdrPlan.setStandardOrClassic(mapper.readValue(leadInformation.getMarsMdrCharges().getStandardOrClassic(), MdrChargesDto.class));
            mdrPlan.setDomesticDebitUpTo2000(mapper.readValue(leadInformation.getMarsMdrCharges().getDomesticDebitUpTo2000(), MdrChargesDto.class));
            mdrPlan.setDomesticDebitAbove2000(mapper.readValue(leadInformation.getMarsMdrCharges().getDomesticDebitAbove2000(), MdrChargesDto.class));
            mdrPlan.setSuperPremiumOrSignature(mapper.readValue(leadInformation.getMarsMdrCharges().getSuperPremiumOrSignature(), MdrChargesDto.class));
            mdrPlan.setInternationalCreditCard(mapper.readValue(leadInformation.getMarsMdrCharges().getInternationalCreditCard(), MdrChargesDto.class));
            mdrPlan.setInternationalDebitCard(mapper.readValue(leadInformation.getMarsMdrCharges().getInternationalDebitCard(), MdrChargesDto.class));
            mdrPlan.setCommercialOrCorporate(mapper.readValue(leadInformation.getMarsMdrCharges().getCommercialOrCorporate(), MdrChargesDto.class));
            mdrPlan.setPremiumOrPlatinum(mapper.readValue(leadInformation.getMarsMdrCharges().getPremiumOrPlatinum(), MdrChargesDto.class));
            mdrPlan.setCashAtPos(mapper.readValue(leadInformation.getMarsMdrCharges().getCashAtPos(), MdrChargesDto.class));
            mdrPlan.setCode(Integer.parseInt(leadInformation.getMarsMdrCharges().getCode()));
            mdrPlan.setUpiAbove2000(mapper.readValue(leadInformation.getUpiAbove2000(), MdrChargesDto.class));
            mdrPlan.setUpiUpto2000(mapper.readValue(leadInformation.getUpiUpto2000(), MdrChargesDto.class));
            mdrPlan.setUpiCreditAbove2000(mapper.readValue(leadInformation.getUpiCreditAbove2000(), MdrChargesDto.class));
            mdrPlan.setUpiCreditUpto2000(mapper.readValue(leadInformation.getUpiCreditUpto2000(), MdrChargesDto.class));
        }
        BankInformation bankInformation = new BankInformation();
        BankDetailMars bankDetails = new BankDetailMars();
        logger.info("Bank info data.............");
        bankDetails.setAccountNumber(leadInformation.getAccountNumber());
        bankDetails.setAccountType(leadInformation.getAccountType());
        bankDetails.setBankName(leadInformation.getBank());
        bankDetails.setBranchName(leadInformation.getBranch());
        bankDetails.setIfsc(leadInformation.getIfscCode());
        bankDetails.setPaymentMode(leadInformation.getIfscCode().startsWith("UTIB") ? "D" : "N");
//
        bankInformation.setBankDetails(bankDetails);
        AdditionalInfo additionalInfo = new AdditionalInfo();
        logger.info("Additional  info data.............");
        additionalInfo.setCity(leadInformation.getCity()); //need to check
        additionalInfo.setIaDistrict(leadInformation.getMerchantDistrictName()); //need to check
        additionalInfo.setIaLocation(leadInformation.getMerchantSubDistrictName()); //need to check
        additionalInfo.setLorState(leadInformation.getMerchantStateName()); //need to check
//        additionalInfo.setOwnerDOB(String.valueOf(leadInformation.getOwnerDOB())); //need to check
        additionalInfo.setOwnerFirstName("");
        additionalInfo.setOwnerLastName("");
        additionalInfo.setPin(String.valueOf(leadInformation.getPincode())); //need to check
        MarsOnboardingInformation marsOnboardingData = marsOnboardingRepository.findLeadId(leadInformation.getLeadId());
        if (marsOnboardingData == null) {
            MarsOnboardingInformation marsOnboardingInformation = new MarsOnboardingInformation();
            logger.info("mars details adding info data  1.............");
            // TO-DO - need to apply below encryption
            marsOnboardingInformation.setSalesInformation(gsonObject.toJson(salesInformation));
            marsOnboardingInformation.setBankInformation(attributeEncryptor.encrypt(gsonObject.toJson(bankInformation)));
            marsOnboardingInformation.setBusinessInformation(attributeEncryptor.encrypt(gsonObject.toJson(businessInformation)));
            marsOnboardingInformation.setCompanyInformation(attributeEncryptor.encrypt(gsonObject.toJson(companyInformation)));
            marsOnboardingInformation.setPartnerInformation(attributeEncryptor.encrypt(gsonObject.toJson(partnerInformations)));
            marsOnboardingInformation.setPaymentDetails(gsonObject.toJson(paymentDetails));
            marsOnboardingInformation.setPaymentDetails(gsonObject.toJson(paymentDetails1));
            marsOnboardingInformation.setAdditionalInfo(gsonObject.toJson(additionalInfo));
            marsOnboardingInformation.setMdrPlan(gsonObject.toJson(mdrPlan));
//        marsOnboardingInformation.setCustomIncentiveRates(gsonObject.toJson(customIncentiveRates));
            marsOnboardingInformation.setCreatedAt(DateUtil.currentDate());
//        User user = userRepository.find(leadInformation.getCreatedBy().getId());  //need to add value
//        marsOnboardingInformation.setCreatedBy(user);
            marsOnboardingInformation.setActive(true);
            marsOnboardingInformation.setOnboardingStatus(5);
            marsOnboardingInformation.setLeadInformation(leadInformation);
            marsOnboardingInformation.setQrApplicationNumber(appNo1);
            logger.info("Company info data............. savinggggggg");
//        MarsOnboardingInformation marsOnboardingInformation1 = marsOnboardingRepository.findLeadId(leadInformation.getLeadId());
//        if(marsOnboardingInformation1 !=null){
//            marsOnboardingRepository.deleteDuplicate(leadInformation);
//        }

            return marsOnboardingRepository.save(marsOnboardingInformation);
        } else {
            logger.info("mars details adding info data 2.............");
            // TO-DO - need to apply below encryption
            marsOnboardingData.setSalesInformation(gsonObject.toJson(salesInformation));
            marsOnboardingData.setBankInformation(attributeEncryptor.encrypt(gsonObject.toJson(bankInformation)));
            marsOnboardingData.setBusinessInformation(attributeEncryptor.encrypt(gsonObject.toJson(businessInformation)));
            marsOnboardingData.setCompanyInformation(attributeEncryptor.encrypt(gsonObject.toJson(companyInformation)));
            marsOnboardingData.setPartnerInformation(attributeEncryptor.encrypt(gsonObject.toJson(partnerInformations)));
            marsOnboardingData.setPaymentDetails(gsonObject.toJson(paymentDetails));
            marsOnboardingData.setPaymentDetails(gsonObject.toJson(paymentDetails1));
            marsOnboardingData.setAdditionalInfo(gsonObject.toJson(additionalInfo));
            marsOnboardingData.setMdrPlan(gsonObject.toJson(mdrPlan));
//        marsOnboardingInformation.setCustomIncentiveRates(gsonObject.toJson(customIncentiveRates));
            marsOnboardingData.setCreatedAt(DateUtil.currentDate());
//        User user = userRepository.find(leadInformation.getCreatedBy().getId());  //need to add value
//        marsOnboardingInformation.setCreatedBy(user);
            marsOnboardingData.setActive(true);
            marsOnboardingData.setOnboardingStatus(5);
            marsOnboardingData.setLeadInformation(leadInformation);
            marsOnboardingData.setQrApplicationNumber(appNo1);
            logger.info("Company info data............. savinggggggg");
//        MarsOnboardingInformation marsOnboardingInformation1 = marsOnboardingRepository.findLeadId(leadInformation.getLeadId());
//        if(marsOnboardingInformation1 !=null){
//            marsOnboardingRepository.deleteDuplicate(leadInformation);
//        }

            return marsOnboardingRepository.save(marsOnboardingData);
        }
    }

    @Override
    public SavingToTable setValuesToDto(EDCMarsResponseDto edcMarsResponseDto) {
        EdcResponseDto edcResponseData = edcMarsResponseDto.getData();
        SavingToTable savingToTable = new SavingToTable();
        savingToTable.setMerchantRefCode(Math.toIntExact(edcResponseData.getMerchantRefCode()));
        savingToTable.setApplicationNumber(edcResponseData.getApplicationNumber());
        savingToTable.setMerchantId(edcResponseData.getMerchantId());
        savingToTable.setTerminalId(edcResponseData.getTerminalId());
        savingToTable.setVpa(edcResponseData.getVpa());
        return savingToTable;
    }

    @Override
    public MarsDeviceDetails saveDatasToMarsAndLeadTable(SavingToTable savingToTable) {
        LeadInformation leadInfo = leadInformationRepository.getLeadByQrApplicationNumber(savingToTable.getApplicationNumber());
        if (savingToTable.getMerchantRefCode() != null) {
            leadInfo.setMerchantQrRefCode(String.valueOf(savingToTable.getMerchantRefCode()));
        }
        leadInformationRepository.save(leadInfo);
        MarsOnboardingInformation marsOnboardingInformation = marsOnboardingRepository.findByQrApplicationNumber(savingToTable.getApplicationNumber());

        marsOnboardingInformation.setMerchantQrRefCode(savingToTable.getMerchantRefCode());
        logger.info("saving merchant refcode on MarsonboardingInformation -----> {}", savingToTable.getMerchantRefCode());
        marsOnboardingRepository.save(marsOnboardingInformation);


        logger.info("lead information table -----> {}", leadInfo);
        int appCount = marsDeviceDetailsRepository.findByApplicationNumberCount(savingToTable.getApplicationNumber());
//        if (appCount != 0) {
//            marsDeviceDetailsRepository.deleteDuplicateEntry(leadInfo.getApplicationNumber());
//        }

        MarsDeviceDetails marsDeviceDetails = new MarsDeviceDetails();
        marsDeviceDetails.setLeadInformation(leadInfo);
        marsDeviceDetails.setApplicationNumber(leadInfo.getQrApplicationNumber());
        marsDeviceDetails.setDeviceStatus(MARS_DEVICE_STATUS_SQR_PENDING);
//        marsDeviceDetails.setDeviceStatus(MARS_DEVICE_STATUS_TID_GENERATED);
        marsDeviceDetails.setDeviceAddress(leadInfo.getContactAddress());
//        marsDeviceDetails.setPincode(leadInfo.getPincode());
//        marsDeviceDetails.setState(leadInfo.getState());
//        marsDeviceDetails.setCity(leadInfo.getCity());
//        marsDeviceDetails.setLatitude(leadInfo.getLatitude());
//        marsDeviceDetails.setLongitude(leadInfo.getLongitude());
        marsDeviceDetails.setDeviceStatusDate(DateUtil.currentDate());
        marsDeviceDetails.setCreatedAt(DateUtil.currentDate());
        marsDeviceDetails.setMid(savingToTable.getMerchantId());
        marsDeviceDetails.setTid(savingToTable.getTerminalId());
        marsDeviceDetails.setSqrVpa(savingToTable.getVpa());
        marsDeviceDetails.setCreatedBy(leadInfo.getCreatedBy());
        marsDeviceDetails.setMerchantName(leadInfo.getMerchantName());
//        marsDeviceDetails.setAggregatorId(savingToTable.getAgrName() == null ? "NA" : savingToTable.getAgrName());
        logger.info("details going to be saved ------> {}", marsDeviceDetails);
        logger.info("tid that is saving -----------> {}", savingToTable.getTerminalId());
        logger.info("mid that is saving -----------> {}", savingToTable.getMerchantId());
        logger.info("MerchantRefCode ------>{}", savingToTable.getMerchantRefCode());
        logger.info("vpa data ----->{}", savingToTable.getVpa());
//      leadInfo.setMerchantRefCode(savingToTable.getMerchantRefCode());
//        if(leadInfo.isPosFlag()) {
//            leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(LEAD_STATUS_MARS_APPROVED));
//            leadInfo.setSubmittedToMarsDate(DateUtil.currentDate());
//            leadInformationRepository.save(leadInfo);
//        }
//        else if(leadInfo.isQrFlag()){
//            leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(IMPL_PENDING_SCHEDULE_VISIT));
//            leadInfo.setSubmittedToMarsDate(DateUtil.currentDate());
//            leadInformationRepository.save(leadInfo);
//        } else {
        leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(LEAD_STATUS_MARS_APPROVED));
//            leadInfo.setSubmittedToMarsDate(DateUtil.currentDate());
        leadInformationRepository.save(leadInfo);
//        }
        MarsDeviceDetails dataStorage = marsDeviceDetailsRepository.save(marsDeviceDetails);
        logger.info("---------------------------saved------------{}", dataStorage);
        return dataStorage;
    }

    @Override
    public Pair<Integer, Object> enableSettlement(MarsDeviceDetails deviceDetails) {
        ResponseEntity<String> out = null;
        String url = marsVpaSettlement;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("NII", "110");

        EnableTxnRequestDto enableTxnRequestDto = new EnableTxnRequestDto();
        enableTxnRequestDto.setMid(deviceDetails.getMid());
//                    enableTxnRequestDto.setMid("M8477685049873");
        enableTxnRequestDto.setApplicationNumber(deviceDetails.getApplicationNumber());
//                    enableTxnRequestDto.setApplicationNumber("1699422592647");
        enableTxnRequestDto.setInstitutionCode(110);
        HttpEntity<EnableTxnRequestDto> entity = new HttpEntity<>(enableTxnRequestDto, headers);
        logger.info("payload for settlement -- : {} ", enableTxnRequestDto.getApplicationNumber());
        try {
            out = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            logger.info("out --------: {}", out.getBody());
        } catch (HttpClientErrorException httpClientErrorException) {
            logger.info("client error Exception -------: {}", httpClientErrorException.getResponseBodyAsString());
            return Pair.of(2, deviceDetails.getTid());
        } catch (HttpServerErrorException httpServerErrorException) {
            logger.info("server error Exception ----: {}", httpServerErrorException.getResponseBodyAsString());
            return Pair.of(2, deviceDetails.getTid());
        }
        logger.info("check status -- : {}", deviceDetails.getLeadInformation().getLeadId());
//        mailHandler.sendLeadApprovedEmail(leadInformation1.getContactEmail(),leadInformation1.getLeadNumber());

        return Pair.of(1, deviceDetails.getTid());
    }

    @Override
    public MarsDeviceDetails activateVpa(TidVpaMap tidVpaMapDto) {
        TidVpaMapping tidVpaMapping = new TidVpaMapping();
        tidVpaMapping.setVpa(tidVpaMapDto.getVpa());
        logger.info("----------------------tidDto----{}", tidVpaMapping.getVpa());
        tidVpaMapping.setMarsDeviceDetails(marsDeviceDetailsRepository.findLead(tidVpaMapDto.getLead_id()));
        logger.info("----------------------MarDetails----{}", marsDeviceDetailsRepository.findLead(tidVpaMapDto.getLead_id()));
        TidVpaMapping save = tidVpaMappingRepository.save(tidVpaMapping);
//        TidVpaMapping tidVpaMapping = tidVpaMappingRepository.MapVpaWithTid(tidVpaMapDto.getVpa(), tidVpaMapDto.getMarsDeviceDetails().getTid());
        if (save.getVpa() != null) {
            MarsDeviceDetails marsDeviceDetails = marsDeviceDetailsRepository.findId(tidVpaMapping.getMarsDeviceDetails().getId());
            if (marsDeviceDetails != null) {
                int originalCount = marsDeviceDetails.getSqrCount();
                int total = originalCount + 1;//Increasing sqr count as one vpa will be onboard in a single Api call
                logger.info("total count -- : {}", total);
                marsDeviceDetails.setSqrCount(total);
                return marsDeviceDetailsRepository.save(marsDeviceDetails);
            }
        }
        return null;
    }

    @Override
    public boolean checkAllAggregatorDocumentsVerifiedByOps(LeadInformation leadInformation) {
        List<LeadDocuments> leadDocuments = uploadedDocumentRepository.findByLeadById(leadInformation);
        logger.info("lead------------------------{}", leadDocuments);
        int leadCount = 0;
        for (LeadDocuments leadDocument : leadDocuments) {
            logger.info("doc-----------------{}", leadDocument);
            List<LeadDocuments> leadDocuments2 = leadDocumentRepository.getLeadDocumentsBySubDocType(leadInformation, leadDocument.getSubDocumentType());
            logger.info("SubDoc---------------{}", leadDocuments2.get(0).getDocumentVerifiedStatus());
            if (!leadDocuments2.isEmpty() && leadDocuments2.get(0).getDocumentVerifiedStatus() == MCC_VERIFICATION_APPROVE) {
                leadCount = leadCount + 1;
            }
        }
        if (leadCount < 3) {
            return false;
        }
        return true;
    }
//        List<DocumentMapping> mappingList = leadDocumentRepository.fetchDocumentMappingByLeadId(leadInformation);
//        logger.info("-----Mapping----{}",mappingList);
//        if (mappingList.size() > 0) {
//            for (DocumentMapping documents : mappingList) {
//                List<LeadDocuments> leadDocumentsList = leadDocumentRepository.getLeadDocumentsByDocumentMapping(leadInformation, documents);
//                if (leadDocumentsList.size() > 0) {
//                    logger.info("--------Lead_{}",leadDocumentsList);
//                    if (leadDocumentsList.get(0).getDocumentVerifiedStatus() != MCC_VERIFICATION_REJECT) {
//                        return false;
//                    }
//                }
//            }
//            return true;
//        }
//        return true;
//    }

    public User getSessionUser() {
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // log.info("The session user ===========user id  {}, user name {}",user.getId(),user.getName());
        return userRepository.findSessionUser(user);
    }

    @Override
    public Pair<Integer, Object> aggregatorLeadApproval(LeadInformation leadInformation, String merchantCategoryCode, User curuser,String aml_Status) {

        logger.info("enter into verification part");
        if (leadInformation != null) {
            Boolean ica = false;
            String input = leadInformation.getVasInstanceMapping();
            if (input != null && !input.trim().isEmpty()) {
                try {
                    JSONArray jsonArray = new JSONArray(input);
                    String[] elements = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        elements[i] = jsonArray.getString(i);
                    }
                    for (String element : elements) {
                        if (Objects.equals(element, "ICA")) {
                            logger.info("elements-----------------------{}", element);
                            ica = true;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                logger.info("input is null {}", leadInformation.getLeadId());
            }
            if (!Objects.equals(leadInformation.getMcc().getMccCode(), merchantCategoryCode)) {
                InternalSubvention internalSubvention = internalSubventionRepository.getDetailByLeadId(leadInformation.getLeadId());
                if (internalSubvention != null) {
                    internalSubvention.setStatus(3);
                    internalSubvention.setMccChanged(true);
                    internalSubventionRepository.save(internalSubvention);
                }
            }
            LeadStatus status = null;
            InternalSubvention internalSubvention1 = !internalSubventionRepository.DetailByLeadIds(leadInformation.getLeadId()).isEmpty() ? internalSubventionRepository.DetailByLeadIds(leadInformation.getLeadId()).get(0) : null;
            if (internalSubvention1 != null && internalSubvention1.getStatus() == SUBMITTED) {
                status = leadInformation.getLeadStatus();
            } else if (leadInformation.getLeadSource().getLeadSourceName().equalsIgnoreCase("LS_KADAMBA")) {
                status = leadStatusRepository.fetchLeadStatusById(PENDING_DOCUMENT_UPLOAD);
            } else {
                status = leadStatusRepository.fetchLeadStatusById(PENDING_BIZ_DETAILS);
            }
            if (ica) {
                leadInformation.setIcaApproval(true);
            }
            leadInformation.setLeadStatus(status);
            leadInformation.setLeadApprovedBy(getSessionUser());
            leadInformation.setLeadApprovedDate(DateUtil.currentDate());
            MerchantCategoryCode merchantCategoryCode1 = merchantCategoryCodeRepository.findByCode(merchantCategoryCode);
            leadInformation.setOpsMcc(leadInformation.getMcc());
            leadInformation.setMcc(merchantCategoryCode1);
            leadInformation.setMccApproved(DateUtil.currentDate());
            leadInformation.setMccApproval(true);
            //For AML Status
            logger.info("Aml Status ------>{}", aml_Status != null ? aml_Status : "NA");
            LeadInformation lead = leadInformationRepository.save(leadInformation);

            if (aml_Status != null) {
                AmlStatus aml = amlStatusRepository.findByLead(lead).orElseGet(AmlStatus::new);
                aml.setLead(lead);              // @MapsId copies lead_id from here
                aml.setStatus(aml_Status);
                amlStatusRepository.save(aml);
                lead.setAmlStatus(aml);         // so the returned lead includes it in the response
            }
            leadInformationRepository.save(lead);
            Auditing_Table auditingTable = new Auditing_Table();
            auditingTable.setAction("MCC");
            auditingTable.setLeadId(leadInformation.getLeadId());
            auditingTable.setMccRequested(leadInformation.getOpsMcc());
            auditingTable.setMccApproved(leadInformation.getMcc());
            auditingTable.setStatus("MCC APPROVED");
            auditingTable.setRequestDate(leadInformation.getCreatedAt());
            auditingTable.setUpdatedDate(DateUtil.currentDate());
            auditingTable.setUser(curuser);
            auditingTableRepository.save(auditingTable);
            logger.info("verify----------------------------{}---------mcc--{}---{}", leadInformation.getOpsMcc(), merchantCategoryCode1, merchantCategoryCode);

            logger.info("check status -- : {}", lead.getLeadStatus().getLeadStatusName());
            if (leadInformation.getMcc() == leadInformation.getOpsMcc()) {
                String reason = "Your lead " + leadInformation.getLeadId() + " MCC has been approved by OPS. You can proceed with the rest of the details";
                String title = "Lead " + leadInformation.getLeadId() + " MCC is Approved!";
                sendPushNotification(title, reason, leadInformation, PUSH_NOTIFICATION_TO_MCC_APPROVE);
                logger.info("MCC is Approved!--{}", leadInformation.getLeadId());
            } else {
                String reason = "Your lead " + leadInformation.getLeadId() + "MCC has been updated by OPS. You can proceed with the rest of the details.";
                String title = "Lead " + leadInformation.getLeadId() + " MCC is Approved!";
                sendPushNotification(title, reason, leadInformation, PUSH_NOTIFICATION_TO_MCC_CHANGE);
                logger.info("MCC is Update!--{}", leadInformation.getLeadId());
            }
            return Pair.of(1, lead);
        }
        return Pair.of(0, "");
    }

    @Override
    public List<CounterDto> listOfCounter(long leadId) {

        List<CounterManagement> counterManagements = counterManagementRepository.getAllByLeads(leadId);
        Map<Integer, List<CounterManagement>> listMap = counterManagements.stream()
                .collect(Collectors.groupingBy(CounterManagement::getCounterNo));
        List<CounterDto> counterDtos = listMap.entrySet().stream().map(entry -> new CounterDto(entry.getKey(), entry.getValue())).toList();
        return counterDtos;
    }

    @Override
    public LeadInformation updateHostData(LeadInformation leadInformation, LeadInformation information) {
        leadInformation.setMerchantStateName(information.getMerchantStateName());
        leadInformation.setMerchantSubDistrictName(information.getMerchantSubDistrictName());
        leadInformation.setMerchantDistrictName(information.getMerchantDistrictName());
        leadInformation.setCity(information.getCity());
        leadInformation.setPincode(information.getPincode());
        leadInformation.setActive(true);
        return leadInformation;
    }

    @Override
    public Pair<Integer, Object> amlStatusUpdated(LeadInformation leadInformation,AggLeadApproveRequest data) {

        if(data!=null && leadInformation != null){
            String aml_Status=data.getAmlStatus();
            logger.info("Aml Status ------>{}", aml_Status != null ? aml_Status : "NA");
            LeadInformation lead = leadInformationRepository.findLead(leadInformation.getLeadId());

            if (aml_Status != null) {
                AmlStatus aml = amlStatusRepository.findByLead(lead).orElseGet(AmlStatus::new);
                aml.setLead(lead);              // @MapsId copies lead_id from here
                aml.setStatus(aml_Status);
                amlStatusRepository.save(aml);
                lead.setAmlStatus(aml);         // so the returned lead includes it in the response
            }
            leadInformationRepository.save(lead);
            return Pair.of(1,lead);
        }
        return Pair.of(0, "");
    }

}


