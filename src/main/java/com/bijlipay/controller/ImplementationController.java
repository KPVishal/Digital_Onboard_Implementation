package com.bijlipay.controller;

import com.bijlipay.config.Constants;
import com.bijlipay.config.security.AESUtil;
import com.bijlipay.config.security.JwtUtil;
import com.bijlipay.model.*;
import com.bijlipay.model.dto.*;
import com.bijlipay.model.dto.ApplicationResponse;
import com.bijlipay.model.dto.TerminalDetails;
import com.bijlipay.model.marsdto.*;
import com.bijlipay.repository.*;
import com.bijlipay.service.ImplementationService;
import com.bijlipay.service.LeadInformationService;
import com.bijlipay.service.UserAuthenticationService;
import com.bijlipay.service.implementation.ImplementationServiceImp;
import com.bijlipay.util.*;
import com.bijlipay.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
import com.google.gson.Gson;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import javax.ws.rs.POST;
import java.io.*;
import java.net.*;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.bijlipay.config.Constants.*;
import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/implementation/device")
public class ImplementationController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ImplementationController.class);

    @Autowired
    private UserAuthenticationService userAuthenticateService;


    @Autowired
    private LeadInformationRepository leadInformationRepository;
    @Autowired
    private ImplementationService implementationService;
    @Autowired
    private AttributeEncryptor attributeEncryptor;
    @Autowired
    private MarsDeviceDetailsRepository marsDeviceDetailsRepository;
    @Autowired
    private ScheduleLogRepository scheduleLogRepository;
    @Autowired
    private LeadStatusRepository leadStatusRepository;
    @Autowired
    private CounterManagementRepository counterManagementRepository;

    @Autowired
    private ImplementationServiceImp implementationServiceImp;

    @Autowired
    private VpaQrRepository vpaQrRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private MarsOnboardingRepository marsOnboardingRepository;

    @Autowired
    private MailHandler mailHandler;
    @Value("${scan.pick.update.url}")
    private String scanPickUpdateUrl;

    @Value("${re-scan.pick.update.url}")
    private String ReScanPickUpdateUrl;

    @Value("${qr.scan.pick.update.url}")
    private String qrScanPickUpdateUrl;

    @Value("${qr.device.scan.pick.update.url}")
    private String qrDeviceScanPickUpdateUrl;
    @Value("${implementation.mars.update.url}")
    private String implementationMarsUpdateUrl;
    @Value("${mars.tid.url}")
    private String marsTidUrl;
    @Value("${mars.vpa.url}")
    private String marsVpaUrl;
    @Value("${agg.generates.vpatid.url}")
    private String aggGeneratesVpaTidUrl;

    @Value("${sound.box}")
    private String soundBox;
    @Value("${sticker}")
    private String sticker;

    @Value("${gee.nee.pos.full}")
    private long geeNeePosFull;

    @Value("${gee.nee.pos.small}")
    private long geeNeePosMini;


    @Autowired
    private LeadInformationService leadInformationService;

    @Autowired
    private RandomGenerator randomGenerator;
    @Autowired
    private AuditingTableRepository auditingTableRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private FileTransferRepository fileTransferRepository;
    @Autowired
    private MerchantCategoryCodeRepository merchantCategoryCodeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LeadSourceRepo leadSourceRepo;
    @Autowired
    private MerchantTypeRepository merchantTypeRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private MarsMdrChargesRepository marsMdrChargesRepository;
    @Autowired
    private MarsRentalPlansRepository marsRentalPlansRepository;
    @Autowired
    private KitTypeRepo kitTypeRepo;

    @Autowired
    private LanguageSoundboxRepository languageSoundboxRepository;
    @Autowired
    private SoundBoxRepository soundBoxRepository;
    @Autowired
    private ConsentVerificationRepository consentVerificationRepository;
    @Value("${sendSms}")
    private String sendSms;
    @Value("${sendMail}")
    private String sendMail;
    @Value("${sendConsentLink}")
    private String consentLink;
    @Value("${mars.addOn.url}")
    private String marsAddOnUrl;

    @Value("${qr.mars.addOn.url}")
    private String qrmarsAddOnUrl;

    @Value("${mars.addOnRetry.url}")
    private String marsAddOnRetryUrl;

    @Value("${mars.yesbank.addOnRetry.url}")
    private String marsAddOnRetryUrlYesBank;
    @Value("${qr.mars.addOnRetry.url}")
    private String qrMarsAddOnRetryUrl;
    @Autowired
    private UserAuthenticationRepo userAuthenticationRepo;
    @Autowired
    private JwtUtil util;
    @Autowired
    private InternalSubventionRepository internalSubventionRepository;
    @Autowired
    private OnboardInstanseRepo onboardInstanseRepo;
    @Value("${mars.yesbank.addOn.url}")
    private String marsYesBankAddOnUrl;

    @Autowired
    private ImplementationDocumentReposistory implementationDocumentReposistory;
//    @Autowired
//    private VpaQrRepository vpaQrRepository;

//    @PutMapping("/submit-to-mars-from-so-app1/{id}")
//    public ResponseEntity<?> submitToMars(@PathVariable("id") long id) throws Exception {
//
//        LeadInformation leadInformation = leadInformationRepository.findLeadId(id);
//
//        if (leadInformation != null && leadInformation.getLeadStatus().getLeadStatusId() == AGREEMENT_SIGNING_COMPLETED) {
//
//            List<UploadedDocuments> uploadedDocumentsList = implementationService.findByLeadId(leadInformation);
//            if (!uploadedDocumentsList.isEmpty()) {
//                List<SignzyFetchAndVerification> panDetails = implementationService.getPanDetails(uploadedDocumentsList);
//                if (!panDetails.isEmpty()) {
////                    leadInformation.setPanNumber(panDetails.get(0).getPanNumber());
//                    leadInformation.setPanNumber(attributeEncryptor.decrypt(panDetails.get(0).getPanNumber()));
//                    leadInformationRepository.save(leadInformation);
////                    logger.info("pan NUmber ---> : {}", panDetails.get(0).getPanNumber());
//                    logger.info("pan Number -- : {}", attributeEncryptor.decrypt(panDetails.get(0).getPanNumber()));
//                }
//            }
//
//
//            MarsOnboardingInformation marsOnboardingInformation = implementationService.setMarsDetails(leadInformation);
//
//            logger.info("onboarding values inserted.................");
//            MarsDto marsDto = new MarsDto();
//            EDCMarsResponseDto edcMarsResponseDto = new EDCMarsResponseDto();
//            StringBuilder strResp = new StringBuilder();
//            JSONParser parser = new JSONParser();
//            marsDto.setDevice("");
//            marsDto.setSalesInformation((JSONObject) parser.parse(marsOnboardingInformation.getSalesInformation()));
//            marsDto.setAdditionalInfo((JSONObject) parser.parse(marsOnboardingInformation.getAdditionalInfo()));
//            marsDto.setCompanyInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getCompanyInformation())));
//            marsDto.setBusinessInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBusinessInformation())));
//            marsDto.setPartnerInformation((JSONArray) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getPartnerInformation())));
//
//            List<CustomIncentiveRatesDto> customIncentiveRatesDtos = new ArrayList<>();
//            CustomIncentiveRatesDto customIncentiveRates1 = new CustomIncentiveRatesDto();
//            customIncentiveRates1.setMaxValue(999);
//            customIncentiveRates1.setMechantFixed(0.5);
//            customIncentiveRates1.setMerchantPercent(0);
//            customIncentiveRates1.setMinValue(200);
//            customIncentiveRates1.setSharingFixed(0);
//            customIncentiveRates1.setSharingPercent(0);
//            customIncentiveRatesDtos.add(customIncentiveRates1);
//            CustomIncentiveRatesDto customIncentiveRates2 = new CustomIncentiveRatesDto();
//            customIncentiveRates2.setMaxValue(1999);
//            customIncentiveRates2.setMechantFixed(2.0);
//            customIncentiveRates2.setMerchantPercent(0);
//            customIncentiveRates2.setMinValue(1000);
//            customIncentiveRates2.setSharingFixed(1);
//            customIncentiveRates2.setSharingPercent(0);
//            customIncentiveRatesDtos.add(customIncentiveRates2);
//            CustomIncentiveRatesDto customIncentiveRates3 = new CustomIncentiveRatesDto();
//            customIncentiveRates3.setMaxValue(2999);
//            customIncentiveRates3.setMechantFixed(5.0);
//            customIncentiveRates3.setMerchantPercent(0);
//            customIncentiveRates3.setMinValue(2000);
//            customIncentiveRates3.setSharingFixed(1);
//            customIncentiveRates3.setSharingPercent(0);
//            customIncentiveRatesDtos.add(customIncentiveRates3);
//            CustomIncentiveRatesDto customIncentiveRates4 = new CustomIncentiveRatesDto();
//            customIncentiveRates4.setMaxValue(3499);
//            customIncentiveRates4.setMechantFixed(9.5);
//            customIncentiveRates4.setMerchantPercent(0);
//            customIncentiveRates4.setMinValue(3000);
//            customIncentiveRates4.setSharingFixed(1);
//            customIncentiveRates4.setSharingPercent(0);
//            customIncentiveRatesDtos.add(customIncentiveRates4);
//            CustomIncentiveRatesDto customIncentiveRates5 = new CustomIncentiveRatesDto();
//            customIncentiveRates5.setMaxValue(10000);
//            customIncentiveRates5.setMechantFixed(5.0);
//            customIncentiveRates5.setMerchantPercent(0);
//            customIncentiveRates5.setMinValue(3500);
//            customIncentiveRates5.setSharingFixed(2);
//            customIncentiveRates5.setSharingPercent(0);
//            customIncentiveRatesDtos.add(customIncentiveRates5);
//            marsDto.setCustomIncentiveRates(customIncentiveRatesDtos);
//
//            PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto();
//            paymentDetailsDto.setAdvanceRentCollected("1000");
//            paymentDetailsDto.setAdvanceRentMode("NEFT");
//            paymentDetailsDto.setCardAcceptance("0");
//            paymentDetailsDto.setCashAtPosEnabled(""); //need to check
//            paymentDetailsDto.setCreditCardBlock("N");
//            paymentDetailsDto.setDeviceOwnedBy("S");
//            paymentDetailsDto.setEmiStartDate("17/10/2022");
//            paymentDetailsDto.setEmiTenure("");
//            paymentDetailsDto.setGracePeriod(0);
//            paymentDetailsDto.setInstallationFee("");
//            paymentDetailsDto.setIntlCardAcceptance("N");
//            paymentDetailsDto.setNetworkPreferred("1");
//            paymentDetailsDto.setNoOfMonthRentPaidInAdvance("1000");
//            paymentDetailsDto.setNumberOfTerminals(1);
//            paymentDetailsDto.setOmcEnabled(false);
//            paymentDetailsDto.setOtherCharges("");
//            paymentDetailsDto.setRecurringFees("9912");
//            paymentDetailsDto.setRentalMode("S");
//            paymentDetailsDto.setRentalPlanCode(1);
//            paymentDetailsDto.setRentalType("A");
//            paymentDetailsDto.setServiceProvider("1");
//            paymentDetailsDto.setTerminalModeCode("33");//33
//            paymentDetailsDto.setTerminalType("POS");
//            paymentDetailsDto.setTipPercentage(String.valueOf(leadInformation.getTipPercentage()));
//            paymentDetailsDto.setTotalAmountPaid("");
//            paymentDetailsDto.setTotalEmiAmount("");
//            marsDto.setPaymentDetails(paymentDetailsDto);
//            marsDto.setBankInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBankInformation())));
//
//            MdrPlanDto mdrPlanDto = new MdrPlanDto();
//            mdrPlanDto.setCode(2); //need to check
//            logger.info("mdrPlanDto info data.............");
//            MdrChargesDto cashAtPos = new MdrChargesDto();
//            cashAtPos.setFixed(0);
//            cashAtPos.setMinimum(0);
//            cashAtPos.setPercentage(0);
//            mdrPlanDto.setCashAtPos(cashAtPos);
//            MdrChargesDto commercialOrCorporate = new MdrChargesDto();
//            commercialOrCorporate.setFixed(0);
//            commercialOrCorporate.setMinimum(0);
//            commercialOrCorporate.setPercentage(0);
//            mdrPlanDto.setCommercialOrCorporate(commercialOrCorporate);
//            MdrChargesDto convenientFee = new MdrChargesDto();
//            convenientFee.setFixed(0);
//            convenientFee.setMinimum(0);
//            convenientFee.setPercentage(0);
//            mdrPlanDto.setConvenientFee(convenientFee);
//            MdrChargesDto diners = new MdrChargesDto();
//            diners.setFixed(0);
//            diners.setMinimum(0);
//            diners.setPercentage(0);
//            mdrPlanDto.setDiners(diners);
//            MdrChargesDto domesticDebitAbove2000 = new MdrChargesDto();
//            domesticDebitAbove2000.setFixed(0);
//            domesticDebitAbove2000.setMinimum(99999);
//            domesticDebitAbove2000.setPercentage(0);
//            mdrPlanDto.setDomesticDebitAbove2000(domesticDebitAbove2000);
//            MdrChargesDto domesticDebitUpTo2000 = new MdrChargesDto();
//            domesticDebitUpTo2000.setFixed(0);
//            domesticDebitUpTo2000.setMinimum(99999);
//            domesticDebitUpTo2000.setPercentage(0);
//            mdrPlanDto.setDomesticDebitUpTo2000(domesticDebitUpTo2000);
//            MdrChargesDto incentive = new MdrChargesDto();
//            mdrPlanDto.setIncentive(incentive);
//            MdrChargesDto internationalCreditCard = new MdrChargesDto();
//            internationalCreditCard.setFixed(0);
//            internationalCreditCard.setMinimum(0);
//            internationalCreditCard.setPercentage(0);
//            mdrPlanDto.setInternationalCreditCard(internationalCreditCard);
//            MdrChargesDto internationalDebitCard = new MdrChargesDto();
//            internationalDebitCard.setFixed(0);
//            internationalDebitCard.setMinimum(0);
//            internationalDebitCard.setPercentage(0);
//            mdrPlanDto.setInternationalDebitCard(internationalDebitCard);
//            MdrChargesDto masterPass = new MdrChargesDto();
//            masterPass.setFixed(0);
//            masterPass.setMinimum(0);
//            masterPass.setPercentage(0);
//            mdrPlanDto.setMasterPass(masterPass);
//            MdrChargesDto mVisa = new MdrChargesDto();
//            mVisa.setFixed(0);
//            mVisa.setMinimum(0);
//            mVisa.setPercentage(3);
//            mdrPlanDto.setmVisa(mVisa);
//            MdrChargesDto onus = new MdrChargesDto();
//            onus.setFixed(0);
//            onus.setMinimum(0);
//            onus.setPercentage(0);
//            mdrPlanDto.setOnus(onus);
//            MdrChargesDto premiumOrPlatinum = new MdrChargesDto();
//            premiumOrPlatinum.setFixed(0);
//            premiumOrPlatinum.setMinimum(0);
//            premiumOrPlatinum.setPercentage(0);
//            mdrPlanDto.setPremiumOrPlatinum(premiumOrPlatinum);
//            MdrChargesDto standardOrClassic = new MdrChargesDto();
//            standardOrClassic.setFixed(0);
//            standardOrClassic.setMinimum(0);
//            standardOrClassic.setPercentage(0);
//            mdrPlanDto.setStandardOrClassic(standardOrClassic);
//            MdrChargesDto superPremiumOrSignature = new MdrChargesDto();
//            superPremiumOrSignature.setFixed(0);
//            superPremiumOrSignature.setMinimum(0);
//            superPremiumOrSignature.setPercentage(0);
//            mdrPlanDto.setSuperPremiumOrSignature(superPremiumOrSignature);
//            marsDto.setMdrPlan(mdrPlanDto);
//            marsDto.setRemarks("");
//            marsDto.setRevParameters((JSONObject) parser.parse(marsOnboardingInformation.getPaymentDetails()));
//
//            EdcResponseDto edcData = null;
//            logger.info(" data 2 ... {}", marsDto);
//            HashMap<String, Object> dummyDto = new HashMap<>();
//            dummyDto.put("merchant", marsDto);
//            JSONObject jsonObject = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
//            logger.info(" JSON OBJ : ---- : {}", jsonObject);
//            logger.info(" dummy -- : {}", CommonUtil.parseJson(jsonObject));
//            HttpURLConnection conn = null;
//            try {
//                URL obj = new URL("https://test01.bijlipay.co.in:9090/v1/merchant");
////            URL obj = new URL(marsUrl);
////                    URL obj = new URL("https://marspp.bijlipay.co.in:9090/v1/merchant");
//                conn = (HttpURLConnection) obj.openConnection();
//                conn.setRequestProperty("NII", "111");
//                conn.setRequestProperty("Content-Type", "application/json");
//                conn.setDoOutput(true);
//                conn.setRequestMethod("POST");
//                // HTTP Connection Headers completed
//                logger.info("HTTP headers : ----- : {}", conn);
//                // Sending API request
//                logger.info("Getting Tickets Status {}", dummyDto);
//                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
//                JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
//                out.write(CommonUtil.parseJson(reqData)); // sending the data
//                logger.info("Getting Tickets Status {}", reqData);
//                out.close(); // closing the output stream
//                int responsecode = conn.getResponseCode();
//                logger.info("The response code is {}", conn);
//                String message = conn.getResponseMessage();
//                logger.info("The response code is {}", responsecode);
//                logger.info("The response message is {}", message);
//                edcMarsResponseDto.setCode(conn.getResponseCode());
//            } catch (Exception e) {
//                return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "SOMETHING WENT WRONG", e.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
//            }
//
//            if (conn.getResponseCode() == 201) {
//                logger.info("Processing the request response -------------------------");
//                // Inputstream parse it as response
//                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                logger.info("response data {}", conn.getInputStream());
//                String inputLine;
//                while ((inputLine = in.readLine()) != null) {
//                    strResp.append(inputLine);
//                    logger.info("responseee ----> {}", strResp);
//                }
//                in.close();
//                edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
////                edcMarsResponseDto.setData(edcData);
//                String data = strResp.toString();
//                Gson gson = new Gson();
//                edcData = gson.fromJson(data, EdcResponseDto.class);
//                if (edcData.getMerchantId() != null && edcData.getTerminalId() != null) {
//                    edcData.setRemarks("Onboard-Success");
//                }
//                edcMarsResponseDto.setData(edcData);
//                edcMarsResponseDto.setMessage("Onboard Success");
//
////                    String data = strResp.toString();
////                    Gson gson = new Gson();
////                    edcData = gson.fromJson(data, EdcResponseDto.class);
////                    if (edcData.getMerchantId() != null && edcData.getTerminalId() != null) {
////                        edcData.setRemarks("Onboard-Success");
////                    } else {
////                        try {
////                            tptService.getErrorStreamData(edcData, edcData.getApplicationNumber());
////                        } catch (Exception e) {
////                            return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Error storing worldline data", e), INTERNAL_SERVER_ERROR);
////                        }
////                    }
//
////                edcMarsResponseDto.setData((JSONObject) parser.parse(data));
////                edcMarsResponseDto.setMessage("Onboard Success");
////                implementationService.saveDatasToMarsAndLeadTable(implementationService.setValuesToDto(edcMarsResponseDto));
////                return new ResponseEntity<>(new ApiResponse(OK, "SUCCESS"), OK);
//
//            } else if (conn.getResponseCode() == 500) {
////                edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
////                edcMarsResponseDto.setCode(500);
////                edcMarsResponseDto.setMessage("Technical issue....Contact bijlipay");
//
//                return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical issue ....Contact Bijlipay"), INTERNAL_SERVER_ERROR);
//            } else if (conn.getResponseCode() == 400) {
////                edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
////                edcMarsResponseDto.setCode(400);
////                edcMarsResponseDto.setMessage("Technical issue....Contact bijlipay");
//                leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(LEAD_REJECTED_BY_SWITCH));
//                leadInformation.setSwitchRejectDate(DateUtil.currentDate());
//                leadInformation.setActive(false);
//                leadInformationRepository.save(leadInformation);
//                return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Technical issue ....Contact Bijlipay", edcMarsResponseDto), BAD_REQUEST);
//            } else if (conn.getResponseCode() == 0) {
////                edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
////                edcMarsResponseDto.setCode(0);
////                edcMarsResponseDto.setMessage("Connection Timed Out..........Try again");
//                return new ResponseEntity<>(new ApiResponse(CONFLICT, "Connection timeout ..... Try again"), CONFLICT);
//            }
//
//
//            if (edcMarsResponseDto.getData().getTerminalId() != null && edcMarsResponseDto.getData().getMerchantId() != null) {
//                MarsDeviceDetails marsDeviceDetails1 = implementationService.saveDatasToMarsAndLeadTable(implementationService.setValuesToDto(edcMarsResponseDto));
//                List<MarsDeviceDetails> marsDeviceDetails = marsDeviceDetailsRepository.findLeadId(marsDeviceDetails1.getLeadInformation());
//
//                marsDeviceDetails.forEach(l -> {
//                    l.setScheduleDate(DateUtil.currentDate());
//                    marsDeviceDetailsRepository.save(l);
//                });
//                CompletableFuture.runAsync(()->{
//                    try {
//                        implementationService.updateLeadAndMarsData(marsDeviceDetails1);
//                    } catch (ExecutionException | InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                });
//                logger.info("Schedule date set --");
//            }
//
//            return new ResponseEntity<>(new ApiResponse(OK, "Success", edcData), OK);
//
//        }
//        return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Lead Not Found"), BAD_REQUEST);
//    }

    @PutMapping("/submit-to-mars-from-so-app1/{id}")
    public ResponseEntity<?> submitToMars(HttpServletRequest request, @PathVariable("id") long id) throws Exception {
        String authtoken = request.getHeader("Authorization");
        User curuser = getSessionUser();
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            LeadInformation leadInformation = leadInformationRepository.findLeadId(id);
            if (leadInformation != null && leadInformation.getLeadStatus().getLeadStatusId() == AGREEMENT_SIGNING_COMPLETED) {
                List<UploadedDocuments> uploadedDocumentsList = implementationService.findByLeadId(leadInformation);
                if (!uploadedDocumentsList.isEmpty()) {
                    List<SignzyFetchAndVerification> panDetails = implementationService.getPanDetails(uploadedDocumentsList);
                    if (!panDetails.isEmpty()) {
                        leadInformation.setPanNumber(attributeEncryptor.decrypt(panDetails.get(0).getPanNumber()));
                        leadInformationRepository.save(leadInformation);
                        logger.info("pan Number -- : {}", attributeEncryptor.decrypt(panDetails.get(0).getPanNumber()));
                    }
                }


                MarsOnboardingInformation marsOnboardingInformation = implementationService.setMarsDetails(leadInformation);
                MarsDto marsDto = new MarsDto();
                EDCMarsResponseDto edcMarsResponseDto = new EDCMarsResponseDto();
                StringBuilder strResp = new StringBuilder();
                JSONParser parser = new JSONParser();
                marsDto.setDevice("");
                marsDto.setSalesInformation((JSONObject) parser.parse(marsOnboardingInformation.getSalesInformation()));
                marsDto.setAdditionalInfo((JSONObject) parser.parse(marsOnboardingInformation.getAdditionalInfo()));
                marsDto.setCompanyInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getCompanyInformation())));
                marsDto.setBusinessInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBusinessInformation())));
                marsDto.setPartnerInformation((JSONArray) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getPartnerInformation())));

                PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto();
                paymentDetailsDto.setAdvanceRentCollected(String.valueOf(leadInformation.getAmountCollected()));
                paymentDetailsDto.setAdvanceRentMode("NEFT");
//        paymentDetails.setCardAcceptance("0");
//        paymentDetails.setCashAtPosEnabled("N");
//        paymentDetails.setCreditCardBlock("N");
                paymentDetailsDto.setDeviceOwnedBy("S");
                paymentDetailsDto.setDevicePrice(0.00);
                paymentDetailsDto.setEmiStartDate("17/10/2022");
                paymentDetailsDto.setEmiTenure("null");
                paymentDetailsDto.setGracePeriod(0);
                paymentDetailsDto.setInstallationFee(String.valueOf(0.00));
//        paymentDetails.setIntlCardAcceptance("N");
//        paymentDetails.setNetworkPreferred(String.valueOf(1));
                paymentDetailsDto.setNoOfMonthRentPaidInAdvance("0");
//        paymentDetails.setNumberOfTerminals(String.valueOf(1));
//        paymentDetails.setOmcEnabled(String.valueOf(false));
//        paymentDetails.setOtherCharges("");
                paymentDetailsDto.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
//                paymentDetailsDto.setRentalMode("S");
                paymentDetailsDto.setRentalPlanCode(Integer.parseInt(leadInformation.getMarsRentalPlans().getCode()));
//                paymentDetailsDto.setRentalType("A");
//        paymentDetails.setServiceProvider(String.valueOf(1));

//                String rentalMode=null;
//                if(leadInformation.getMarsRentalPlans().getPlanFrequency().equals("M")){
//                    rentalMode="SE";
//                } else if (leadInformation.getMarsRentalPlans().getPlanFrequency().equals("L")) {
//                    rentalMode="RW";
//                }else {
//                    rentalMode=" ";
//                }

//                if(leadInformation.getLeadSource().getLeadSourceName().contains("LS_DCB BANK _ONE TIME")){
//                    paymentDetailsDto.setRentalMode(rentalMode);
//                }else{
                paymentDetailsDto.setRentalMode("SE");
//                }
//                paymentDetailsDto.setTerminalModeCode("33"); //need to check
                paymentDetailsDto.setTerminalModeCode(leadInformation.getDevice() != null ? leadInformation.getDevice().getMarsDeviceCode() : ""); //need to check

                logger.info("deviceModeCode ---> {}", paymentDetailsDto.getTerminalModeCode());
                paymentDetailsDto.setTerminalType("POS");
//        paymentDetails.setTipPercentage(leadInformation.getTipPercentage());
//        paymentDetails.setTotalAmountPaid("");
                paymentDetailsDto.setTotalEmiAmount("null");

                marsDto.setPaymentDetails(paymentDetailsDto);
                marsDto.setBankInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBankInformation())));

                MdrPlanDto mdrPlanDto = new MdrPlanDto();

                ObjectMapper mapper = new ObjectMapper();
                if (leadInformation.getMarsMdrCharges() != null) {
                    mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
                    mdrPlanDto.setDiners(mapper.readValue(leadInformation.getMarsMdrCharges().getDiners(), MdrChargesDto.class));
                    mdrPlanDto.setOnus(mapper.readValue(leadInformation.getMarsMdrCharges().getOnus(), MdrChargesDto.class));
                    mdrPlanDto.setStandardOrClassic(mapper.readValue(leadInformation.getMarsMdrCharges().getStandardOrClassic(), MdrChargesDto.class));
                    mdrPlanDto.setDomesticDebitUpTo2000(mapper.readValue(leadInformation.getMarsMdrCharges().getDomesticDebitUpTo2000(), MdrChargesDto.class));
                    mdrPlanDto.setDomesticDebitAbove2000(mapper.readValue(leadInformation.getMarsMdrCharges().getDomesticDebitAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(leadInformation.getMarsMdrCharges().getSuperPremiumOrSignature(), MdrChargesDto.class));
                    mdrPlanDto.setInternationalCreditCard(mapper.readValue(leadInformation.getMarsMdrCharges().getInternationalCreditCard(), MdrChargesDto.class));
                    mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInformation.getMarsMdrCharges().getInternationalDebitCard(), MdrChargesDto.class));
                    mdrPlanDto.setCommercialOrCorporate(mapper.readValue(leadInformation.getMarsMdrCharges().getCommercialOrCorporate(), MdrChargesDto.class));
                    mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(leadInformation.getMarsMdrCharges().getPremiumOrPlatinum(), MdrChargesDto.class));
                    mdrPlanDto.setCashAtPos(mapper.readValue(leadInformation.getMarsMdrCharges().getCashAtPos(), MdrChargesDto.class));
                    mdrPlanDto.setCode(Integer.parseInt(leadInformation.getMarsMdrCharges().getCode()));
                }
//                if (leadInformation.isQrFlag()) {
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    String str = leadInformation.getMarsMdrCharges().getDomesticDebitAbove2000();
//                    MdrChargesDto DebitAbove = objectMapper.readValue(str, MdrChargesDto.class);
//                    DebitAbove.setPercentage(leadInformation.getAppliedDebitGreaterthanAmount());
//
//                    String str1 = leadInformation.getMarsMdrCharges().getDomesticDebitUpTo2000();
//                    MdrChargesDto DebitUP = objectMapper.readValue(str1, MdrChargesDto.class);
//                    DebitUP.setPercentage(leadInformation.getAppliedDebitLessthanAmount());
//
//                    String str2 = leadInformation.getMarsMdrCharges().getCommercialOrCorporate();
//                    MdrChargesDto corpCC = objectMapper.readValue(str2, MdrChargesDto.class);
//                    corpCC.setPercentage(leadInformation.getAppliedCorpCC());
//
//                    String str3 = leadInformation.getMarsMdrCharges().getInternationalCreditCard();
//                    MdrChargesDto intlcc = objectMapper.readValue(str3, MdrChargesDto.class);
//                    intlcc.setPercentage(leadInformation.getIntlCC());
//
//                    String str4 = leadInformation.getMarsMdrCharges().getPremiumOrPlatinum();
//                    MdrChargesDto primiumCC = objectMapper.readValue(str4, MdrChargesDto.class);
//                    primiumCC.setPercentage(leadInformation.getAppliedPremiumCC());
//
//                    String str5 = leadInformation.getMarsMdrCharges().getStandardOrClassic();
//                    MdrChargesDto stdCC = objectMapper.readValue(str5, MdrChargesDto.class);
//                    stdCC.setPercentage(leadInformation.getAppliedStdCC());
//
//                    String str6 = leadInformation.getMarsMdrCharges().getSuperPremiumOrSignature();
//                    MdrChargesDto superPremium = objectMapper.readValue(str6, MdrChargesDto.class);
//                    superPremium.setPercentage(leadInformation.getAppliedSuperPremiumCC());
//
//                    mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
//                    mdrPlanDto.setDiners(mapper.readValue(leadInformation.getMarsMdrCharges().getDiners(), MdrChargesDto.class));
//                    mdrPlanDto.setOnus(mapper.readValue(leadInformation.getMarsMdrCharges().getOnus(), MdrChargesDto.class));
//                    mdrPlanDto.setStandardOrClassic(stdCC);
//                    mdrPlanDto.setDomesticDebitUpTo2000(DebitUP);
//                    mdrPlanDto.setDomesticDebitAbove2000(DebitAbove);
//                    mdrPlanDto.setSuperPremiumOrSignature(superPremium);
//                    mdrPlanDto.setInternationalCreditCard(intlcc);
//                    mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInformation.getMarsMdrCharges().getInternationalDebitCard(), MdrChargesDto.class));
//                    mdrPlanDto.setCommercialOrCorporate(corpCC);
//                    mdrPlanDto.setPremiumOrPlatinum(primiumCC);
//                    mdrPlanDto.setCashAtPos(mapper.readValue(leadInformation.getMarsMdrCharges().getCashAtPos(), MdrChargesDto.class));
//                    mdrPlanDto.setCode(Integer.parseInt(leadInformation.getMarsMdrCharges().getCode()));
//                }

                marsDto.setMdrPlan(mdrPlanDto);
                marsDto.setRemarks("");
                marsDto.setRevParameters((JSONObject) parser.parse(marsOnboardingInformation.getPaymentDetails()));

                EdcResponseDto edcData = null;
                HashMap<String, Object> dummyDto = new HashMap<>();
                dummyDto.put("merchant", marsDto);
                JSONObject jsonObject = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                HttpURLConnection conn = null;
                try {
                    URL obj = new URL(marsTidUrl);
                    conn = (HttpURLConnection) obj.openConnection();
                    conn.setRequestProperty("NII", "104");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    // HTTP Connection Headers completed
                    // Sending API request
                    OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                    JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                    out.write(CommonUtil.parseJson(reqData)); // sending the data
                    logger.info("Mars Request Payload for Tid Creation {}", reqData);
                    out.close(); // closing the output stream
                    int responsecode = conn.getResponseCode();
                    String message = conn.getResponseMessage();
                    logger.info("The response code and message is {} ----{}", responsecode, message);
                    edcMarsResponseDto.setCode(conn.getResponseCode());
                } catch (Exception e) {
                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "SOMETHING WENT WRONG", e.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
                }

                // Inputstream parse it as response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    strResp.append(inputLine);
                    logger.info("TID Creation API Response From Mars----> {}", strResp);
                }
                in.close();
                if (conn.getResponseCode() == 201) {
                    edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
                    String data = strResp.toString();
                    Gson gson = new Gson();
                    edcData = gson.fromJson(data, EdcResponseDto.class);
                    if (Objects.equals(edcData.getResponseCode(), "00")) {
                        logger.info("-------------------Sucesss-----------------{}", edcData.getResponseCode());
                        if (edcData.getMerchantId() != null && edcData.getTerminalId() != null) {
                            edcData.setRemarks("Onboard-Success");
                            leadInformation.setIsMarsApiTriggered(MARS_RESPONSE_TID_CREATED);
                            leadInformationRepository.save(leadInformation);
                        } else {

                            logger.info("-------------------InternalServerError-----------------{}", edcData.getResponseCode());
                            leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(HOST_API_FAILED));
                            leadInformation.setActive(false);
                            leadInformationRepository.save(leadInformation);

                            leadInformation.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                            leadInformationRepository.save(leadInformation);
                            return new ResponseEntity<>(new ApiResponse(CONFLICT, "Failure", edcData), CONFLICT);
                        }
                        edcMarsResponseDto.setData(edcData);
                        edcMarsResponseDto.setMessage("Onboard Success");

                    }
                    if (Objects.equals(edcData.getResponseCode(), "101") || Objects.equals(edcData.getResponseCode(), "102")) {
                        logger.info("-------------------DMO Failed-----------------{}", edcData.getResponseCode());
                        leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(DMO_API_FAILED));
                        leadInformation.setActive(false);
                        leadInformation.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                        leadInformationRepository.save(leadInformation);

                        return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical issue ....Contact Bijlipay", MARS_REJECT), INTERNAL_SERVER_ERROR);
                    } else if (Objects.equals(edcData.getResponseCode(), "100")) {
                        logger.info("-------------------Host Failed-----------------{}", edcData.getResponseCode());
                        leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(HOST_API_FAILED));
                        leadInformation.setActive(false);
                        leadInformation.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                        leadInformationRepository.save(leadInformation);

                        return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical issue ....Contact Bijlipay", MARS_REJECT), INTERNAL_SERVER_ERROR);
                    } else if (Objects.equals(edcData.getResponseCode(), "103")) {
                        logger.info("-------------------OGS Failed-----------------{}", edcData.getResponseCode());
                        leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(OGS_API_FAILED));
                        leadInformation.setActive(false);
                        leadInformation.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                        leadInformationRepository.save(leadInformation);

                        return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical issue ....Contact Bijlipay", MARS_REJECT), INTERNAL_SERVER_ERROR);

                    }
                } else if (conn.getResponseCode() == 500) {
                    leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(HOST_API_FAILED));
                    leadInformation.setSwitchRejectDate(DateUtil.currentDate());
                    leadInformation.setActive(false);
                    leadInformationRepository.save(leadInformation);
//                edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
//                edcMarsResponseDto.setCode(500);
//                edcMarsResponseDto.setMessage("Technical issue....Contact bijlipay");

                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical issue ....Contact Bijlipay"), INTERNAL_SERVER_ERROR);
                } else if (conn.getResponseCode() == 400) {
//                edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
//                edcMarsResponseDto.setCode(400);
//                edcMarsResponseDto.setMessage("Technical issue....Contact bijlipay");
                    leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(HOST_API_FAILED));
                    leadInformation.setSwitchRejectDate(DateUtil.currentDate());
                    leadInformation.setActive(false);
                    leadInformationRepository.save(leadInformation);
                    return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Technical issue ....Contact Bijlipay", edcMarsResponseDto), BAD_REQUEST);
                } else if (conn.getResponseCode() == 0) {
//                edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
//                edcMarsResponseDto.setCode(0);
//                edcMarsResponseDto.setMessage("Connection Timed Out..........Try again");
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "Connection timeout ..... Try again"), CONFLICT);
                }


                if (edcMarsResponseDto.getData().getTerminalId() != null && edcMarsResponseDto.getData().getMerchantId() != null) {


                    MarsDeviceDetails marsDeviceDetails1 = implementationService.saveDatasToMarsAndLeadTablePos(implementationService.setValuesToDto(edcMarsResponseDto));
                    List<MarsDeviceDetails> marsDeviceDetails = marsDeviceDetailsRepository.findLeadId(marsDeviceDetails1.getLeadInformation());

                    marsDeviceDetails.forEach(l -> {
                        l.setScheduleDate(DateUtil.currentDate());
                        marsDeviceDetailsRepository.save(l);
                    });
                    CompletableFuture.runAsync(() -> {
                        try {
                            logger.info("-Webbackend triggered-");
                            implementationService.updateLeadAndMarsData(marsDeviceDetails1);
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    logger.info("Schedule date set --");
                }

                return new ResponseEntity<>(new ApiResponse(OK, "Success", edcData), OK);

            }
            return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Lead Not Found"), BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("implementation-details/{leadId}")
    public ResponseEntity<?> getImplementationDetails(HttpServletRequest request, @Valid @PathVariable("leadId") LeadInformation leadInformation) throws URISyntaxException {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            try {
                LeadInformation leadInformation1 = leadInformationRepository.findLeadId(leadInformation.getLeadId());
                if (leadInformation1 == null) {
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, "error"), HttpStatus.BAD_REQUEST);
                }
                List<MarsDeviceDetails> marsDeviceDetailsResponse = implementationService.getImplementationDetailsApp(leadInformation, leadInformation.getCreatedBy()); //getsessionuser
                HashMap<String, Object> result = new HashMap<String, Object>();
                result.put("leadInformation", marsDeviceDetailsResponse);
                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success", result), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("implementation-scan-pick/{serialNumber}/{id}")
    public ResponseEntity<?> getImplementationScanAndPickApp(HttpServletRequest request, @RequestBody MarsDeviceDetails marsDeviceDetails, @PathVariable("serialNumber") String serialNumber, @PathVariable("id") Long id) throws URISyntaxException, IOException, ParseException {

        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            MarsDeviceDetails marsData = marsDeviceDetailsRepository.findId(id);
            DeviceDto deviceDto = new DeviceDto();
            if (marsData.getLeadInformation().isGeeNeePos()) {
                if (marsData.getLeadInformation().getDevice().getId() == geeNeePosFull) {
                    deviceDto.setDeviceName("Q161_PRO_SQR");
                } else if (marsData.getLeadInformation().getDevice().getId() == geeNeePosMini) {
                    deviceDto.setDeviceName("Q161_PRO_SQR_MINI");
                }
            } else {
                deviceDto.setDeviceName(marsData.getLeadInformation().getDevice().getDeviceName());
            }
//        deviceDto.setDevice(marsDeviceDetails.getLeadInformation().getDevice());
//        deviceDto.setUser(marsDeviceDetails.getLeadInformation().getCreatedBy());
            deviceDto.setSerialNumber(serialNumber);
            deviceDto.setTid(marsData.getTid());
            deviceDto.setMid(marsData.getMid());
            deviceDto.setApplicationNumber(marsData.getApplicationNumber());


            JSONParser parser = new JSONParser();
            HttpURLConnection conn = null;
//    try {
//        URL obj = new URL("https://uatapp.bijlipay.co.in:8181/staticApi/implementation-scan-pick");
            URL obj = new URL(scanPickUpdateUrl);
//            URL obj = new URL(marsUrl);
//                    URL obj = new URL("https://marspp.bijlipay.co.in:9090/v1/merchant");
            conn = (HttpURLConnection) obj.openConnection();
//                        conn.setRequestProperty("NII", "110");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            // HTTP Connection Headers completed
            logger.info("HTTP headers : ----- : {}", conn);
            // Sending API request
            logger.info("Getting Tickets Status {}", marsDeviceDetails);
            logger.info("Getting Tickets Status1 {}", deviceDto);
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(deviceDto));
            out.write(CommonUtil.parseJson(reqData)); // sending the data
            logger.info("Getting Tickets Status {}", reqData);
            out.close(); // closing the output stream

            int responsecode = conn.getResponseCode();
            logger.info("The response code is {}", conn);
            String message = conn.getResponseMessage();

            logger.info("The response code is {}", responsecode);
            logger.info("The response message is {}", message);

            if (conn.getResponseCode() == 400 || conn.getResponseCode() == 404) {
                return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Serial Number Not found"), BAD_REQUEST);
            } else if (conn.getResponseCode() == 500) {
                return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Connection failed"), INTERNAL_SERVER_ERROR);
            } else if (conn.getResponseCode() == 200) {
                MarsDeviceDetails marsDeviceDetails1 = marsDeviceDetailsRepository.findId(id);
                marsDeviceDetails1.setTerminalSNo(serialNumber);
                marsDeviceDetails1.setDeviceStatus(MARS_DEVICE_STATUS_SCAN_PICKED);
                marsDeviceDetails1.setDeviceStatusDate(DateUtil.currentDate());
                marsDeviceDetailsRepository.save(marsDeviceDetails1);
                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success"), HttpStatus.OK);

            }

            return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Already Allocated"), BAD_REQUEST);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("sim-scan-pick/{id}/{simNumber}")
    public ResponseEntity<?> getImplementationScanAndPickApp(HttpServletRequest request, @PathVariable("id") Long id, @PathVariable("simNumber") String simNumber) throws URISyntaxException, IOException, ParseException {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            MarsDeviceDetails marsDeviceDetails = marsDeviceDetailsRepository.findId(id);

            if (marsDeviceDetails != null) {
                marsDeviceDetails.setSim(simNumber);
                marsDeviceDetailsRepository.save(marsDeviceDetails);
            }
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success"), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("device-details-update/{id}")
    public ResponseEntity<?> updateDeviceDetail(HttpServletRequest request, @PathVariable("id") Long id) throws URISyntaxException, IOException, ParseException {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            List<MarsDeviceDetails> marsDeviceDetailss = marsDeviceDetailsRepository.findIdByIds(id);
            for (MarsDeviceDetails marsDeviceDetails : marsDeviceDetailss) {
                logger.info("m,ars {}", marsDeviceDetails);
                if (marsDeviceDetails.getLeadInformation().isPosFlag() && marsDeviceDetails.getLeadInformation().isQrFlag()) {
                    if (marsDeviceDetails.getDeviceStatus() == MARS_DEVICE_STATUS_SCAN_PICKED || marsDeviceDetails.getDeviceStatus() == MARS_DEVICE_STATUS_TID_GENERATED && marsDeviceDetails.getSim() != null) {
                        LeadInformation leadInformation = leadInformationRepository.findLeadId(marsDeviceDetails.getLeadInformation().getLeadId());
//                        leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(IMPL_PENDING_SCHEDULE_VISIT));
                        leadInformationRepository.save(leadInformation);
                        logger.info("Status {}", leadInformation.getLeadStatus());
                        marsDeviceDetails.setImplDeviceCompleteDate(DateUtil.currentDate());
                        marsDeviceDetailsRepository.save(marsDeviceDetails);
                        logger.info("date {}", marsDeviceDetails.getImplDeviceCompleteDate());
                    }
                } else if (marsDeviceDetails.getLeadInformation().isQrFlag() && !marsDeviceDetails.getLeadInformation().isPosFlag()) {

                    if (marsDeviceDetails.getDeviceStatus() == MARS_DEVICE_STATUS_TID_GENERATED) {
                        LeadInformation leadInformation = leadInformationRepository.findLeadId(marsDeviceDetails.getLeadInformation().getLeadId());
//                        leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(IMPL_PENDING_SCHEDULE_VISIT));
                        leadInformationRepository.save(leadInformation);

                        logger.info("Status {}", leadInformation.getLeadStatus());
                        marsDeviceDetails.setImplDeviceCompleteDate(DateUtil.currentDate());
                        marsDeviceDetailsRepository.save(marsDeviceDetails);
                        logger.info("date {}", marsDeviceDetails.getImplDeviceCompleteDate());
                    }
                } else if (marsDeviceDetails.getLeadInformation().isPosFlag() && !marsDeviceDetails.getLeadInformation().isQrFlag()) {
                    if (marsDeviceDetails.getDeviceStatus() == MARS_DEVICE_STATUS_SCAN_PICKED && marsDeviceDetails.getSim() != null) {
                        LeadInformation leadInformation = leadInformationRepository.findLeadId(marsDeviceDetails.getLeadInformation().getLeadId());
//                        leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(IMPL_PENDING_SCHEDULE_VISIT));
                        leadInformationRepository.save(leadInformation);
                        logger.info("Status {}", leadInformation.getLeadStatus());
                        marsDeviceDetails.setImplDeviceCompleteDate(DateUtil.currentDate());
                        marsDeviceDetailsRepository.save(marsDeviceDetails);
                        logger.info("date {}", marsDeviceDetails.getImplDeviceCompleteDate());
                    }
                }

            }
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);

    }


    @PutMapping("implementation-schedule-details/{id}")
    public ResponseEntity<?> getScheduleDetails(HttpServletRequest request, @Valid @PathVariable("id") Long id, @RequestBody MarsDeviceDetails marsDeviceDetails) throws URISyntaxException {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            MarsDeviceDetails marsDeviceDetails1 = marsDeviceDetailsRepository.findId2(id);
            ScheduleResponse scheduleResponse = null;
            if (marsDeviceDetails1 != null) {
                LeadInformation leadInformation = leadInformationRepository.findLeadId(marsDeviceDetails1.getLeadInformation().getLeadId());
                leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(IMPL_PENDING_LOCATION_FETCH));
                leadInformationRepository.save(leadInformation);
                marsDeviceDetails1.setScheduleDate(marsDeviceDetails.getScheduleDate());
                marsDeviceDetails1.setDeviceStatus(MARS_DEVICE_STATUS_INSTALLATION_SCHEDULED);
                marsDeviceDetails1.setDeviceStatusDate(DateUtil.currentDate());
                marsDeviceDetails1.setImplScheduleCompleteDate(DateUtil.currentDate());
                marsDeviceDetailsRepository.save(marsDeviceDetails1);
                logger.info("------status--{}", leadInformation.getLeadStatus());
                ScheduleLogs scheduleLogs = new ScheduleLogs();
                scheduleLogs.setScheduleDate(marsDeviceDetails.getScheduleDate());
                scheduleLogs.setLeadInformation(marsDeviceDetails1.getLeadInformation());
                scheduleLogRepository.save(scheduleLogs);
                scheduleResponse = new ScheduleResponse();
                scheduleResponse.setScheduleDate(marsDeviceDetails.getScheduleDate());
                scheduleResponse.setLeadInformation(marsDeviceDetails1.getLeadInformation());
            }

            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success", scheduleResponse), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("implementation-re-schedule-details/{id}")
    public ResponseEntity<?> getReScheduleDetails(HttpServletRequest request, @Valid @PathVariable("id") Long id, @RequestBody MarsDeviceDetails marsDeviceDetails) throws URISyntaxException {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            ScheduleResponse scheduleResponse = null;
            MarsDeviceDetails marsDeviceDetails1 = marsDeviceDetailsRepository.findId2(id);
            if (marsDeviceDetails1 != null) {
                marsDeviceDetails1.setReScheduleDate(marsDeviceDetails.getReScheduleDate());
                marsDeviceDetails1.setDeviceStatus(MARS_DEVICE_STATUS_INSTALLATION_RE_SCHEDULED);
                marsDeviceDetails1.setDeviceStatusDate(DateUtil.currentDate());
                marsDeviceDetails1.setRescheduleReason(marsDeviceDetails.getRescheduleReason());
                marsDeviceDetails1.setRescheduleRemarks(marsDeviceDetails.getRescheduleRemarks());
                marsDeviceDetailsRepository.save(marsDeviceDetails1);
                ScheduleLogs scheduleLogs = new ScheduleLogs();
                scheduleLogs.setScheduleDate(marsDeviceDetails.getReScheduleDate());
                scheduleLogs.setLeadInformation(marsDeviceDetails1.getLeadInformation());
                scheduleLogs.setRescheduleReason(marsDeviceDetails1.getRescheduleReason());
                scheduleLogs.setRescheduleRemarks(marsDeviceDetails1.getRescheduleRemarks());
                scheduleLogRepository.save(scheduleLogs);

                scheduleResponse = new ScheduleResponse();
                scheduleResponse.setReScheduleDate(marsDeviceDetails.getReScheduleDate());
                scheduleResponse.setLeadInformation(marsDeviceDetails1.getLeadInformation());
                scheduleResponse.setRescheduleReason(marsDeviceDetails.getRescheduleReason());
                scheduleResponse.setRescheduleRemarks(marsDeviceDetails.getRescheduleRemarks());
//                implementationService.sendSmsToMerchantForReschedule(marsDeviceDetails1.getLeadInformation().getLeadId());
                String template = "Your Appointment with Bijlipay Officer " + marsDeviceDetails1.getLeadInformation().getCreatedBy().getName() + " has been rescheduled to " + DateUtil.formattedTime(marsDeviceDetails.getReScheduleDate(), "yyyy-MM-dd HH:mm:ss") + ". Please contact " + marsDeviceDetails1.getLeadInformation().getCreatedBy().getContactNumber() + " for further details. Team Bijlipay -Bijlipay";
                String templateId = "1107170487204505813";
                implementationService.sms(template, marsDeviceDetails1.getLeadInformation().getContactNumber(), templateId);


            }
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success", scheduleResponse), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/schedule-log-details/{id}")
    public ResponseEntity<?> getScheduleLogs(HttpServletRequest request, @PathVariable("id") Long id) {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            MarsDeviceDetails marsDeviceDetails = marsDeviceDetailsRepository.findId(id);
            List<ScheduleLogs> scheduleLogs = null;
            if (marsDeviceDetails != null) {
                scheduleLogs = scheduleLogRepository.getLogs(marsDeviceDetails.getLeadInformation());
            }
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Success", scheduleLogs), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }


    @PutMapping("implementation-location-details/{id}")
    public ResponseEntity<?> getLocationDetails(HttpServletRequest request, @Valid @PathVariable("id") Long id, @RequestBody MarsDeviceDetails marsDeviceDetails) throws URISyntaxException {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            List<MarsDeviceDetails> marsDeviceDetailss = marsDeviceDetailsRepository.findIds(id);
            if (!marsDeviceDetailss.isEmpty()) {
                for (MarsDeviceDetails marsDeviceDetails1 : marsDeviceDetailss) {
                    LeadInformation leadInformation = leadInformationRepository.findLeadId(marsDeviceDetails1.getLeadInformation().getLeadId());
                    leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(IMPLEMENTATION_COMPLETED));
                    leadInformationRepository.save(leadInformation);
                    marsDeviceDetails1.setImplStateName(marsDeviceDetails.getImplStateName());
                    marsDeviceDetails1.setImplDistrictName(marsDeviceDetails.getImplDistrictName());
                    marsDeviceDetails1.setImplSubDistrictName(marsDeviceDetails.getImplSubDistrictName());
                    marsDeviceDetails1.setImplVillageName(marsDeviceDetails.getImplVillageName());
                    marsDeviceDetails1.setImplTier(marsDeviceDetails.getImplTier());
                    marsDeviceDetails1.setImplLatLng(marsDeviceDetails.getImplLatLng());
                    marsDeviceDetails1.setDocumentCollected(marsDeviceDetails.isDocumentCollected());
                    marsDeviceDetails1.setDeviceStatus(MARS_DEVICE_STATUS_IMPLEMENTED);
                    marsDeviceDetails1.setDeviceStatusDate(DateUtil.currentDate());
                    marsDeviceDetails1.setImplementationCompleteDate(DateUtil.currentDate());
                    marsDeviceDetails1.setImplPincodeName(marsDeviceDetails.getImplPincodeName());
                    marsDeviceDetails1.setImplCityName(marsDeviceDetails.getImplCityName());
                    marsDeviceDetails1.setImplLocationName(marsDeviceDetails.getImplLocationName());
                    marsDeviceDetailsRepository.save(marsDeviceDetails1);
                    CompletableFuture.runAsync(() -> {
                        logger.info("mars device details1 ----- : {}", marsDeviceDetails1);
                        try {
                            this.updatedImplementedData(marsDeviceDetails1);
                        } catch (IOException | ParseException e) {
                            logger.info("exception -----------: {}", e.getMessage());
                            e.printStackTrace();
                        }
                    });
                }
                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success", marsDeviceDetailss), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "MarsDeviceDetailsNotFound"), BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
    }

    private void updatedImplementedData(MarsDeviceDetails marsDeviceDetails) throws IOException, ParseException {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setTid(marsDeviceDetails.getTid());
        deviceDto.setMid(marsDeviceDetails.getMid());
        deviceDto.setApplicationNumber(marsDeviceDetails.getApplicationNumber());
        deviceDto.setImplStateName(marsDeviceDetails.getImplStateName());
        deviceDto.setImplDistrictName(marsDeviceDetails.getImplDistrictName());
        deviceDto.setImplSubDistrictName(marsDeviceDetails.getImplSubDistrictName());
        deviceDto.setImplVillageName(marsDeviceDetails.getImplVillageName());
        deviceDto.setImplLatLng(marsDeviceDetails.getImplLatLng());
        deviceDto.setImplTier(marsDeviceDetails.getImplTier());


        JSONParser parser = new JSONParser();
        HttpURLConnection conn = null;

        URL obj = new URL(implementationMarsUpdateUrl);
        conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        // HTTP Connection Headers completed
        logger.info("HTTP headers : ----- : {}", conn);
        // Sending API request
        logger.info("Getting Tickets Status {}", marsDeviceDetails);
        logger.info("Getting Tickets Status1 {}", deviceDto);
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(deviceDto));
        out.write(CommonUtil.parseJson(reqData)); // sending the data
        logger.info("Getting Tickets Status {}", reqData);
        out.close(); // closing the output stream

        int responsecode = conn.getResponseCode();
        logger.info("The response code is {}", conn);
        String message = conn.getResponseMessage();

        logger.info("The response code is {}", responsecode);
        logger.info("The response message is {}", message);
    }

    @GetMapping("/getStateList")
    public ResponseEntity<?> getstatelist(HttpServletRequest request) {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            List<HashMap<String, Object>> StateList = implementationService.getmerchantdataStatename();
            logger.info("Get the List of States{}..", StateList);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Success", StateList), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getDistrictList")
    public ResponseEntity<?> getListofDistrict(HttpServletRequest request, @RequestParam("Statename") Optional<String> Statename) {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            List<HashMap<String, Object>> districtList = implementationService.getmerchantdataDistrictname(Statename);
            logger.info("Get the list of District{}...", districtList);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Success", districtList), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getSubDistrictList")
    public ResponseEntity<?> getListofSubDistrict(HttpServletRequest request, @RequestParam("Statename") Optional<String> Statename, @RequestParam("Districtname") Optional<String> Districtname) {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            List<HashMap<String, Object>> SubDistrictList = implementationService.getmerchantdataSubDistrictname(Statename, Districtname);
            logger.info("Get the List of SubDistrict{}..", SubDistrictList);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Success", SubDistrictList), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getVillagenameList")
    public ResponseEntity<?> getListofVillagename(HttpServletRequest request, @RequestParam("Statename") Optional<String> Statename, @RequestParam("Districtname") Optional<String> Districtname, @RequestParam("SubDistrictname") Optional<String> SubDistrictname) {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            List<HashMap<String, Object>> VillageList = implementationService.getmerchantdataTownVillagename(Statename, Districtname, SubDistrictname);
            logger.info("Get the List of Villages{}...", VillageList);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Success", VillageList), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getTierName")
    public ResponseEntity<?> getVillageTiername(HttpServletRequest request, @RequestParam("Statename") Optional<String> Statename, @RequestParam("Districtname") Optional<String> Districtname, @RequestParam("SubDistrictname") Optional<String> SubDistrictname, @RequestParam("Villagename") Optional<String> Villagename) {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            String TierName = implementationService.getmerchantTiername(Statename, Districtname, SubDistrictname, Villagename);
            Map<String, Object> responseData = new HashedMap<String, Object>();
            responseData.put("Tier", TierName);
            logger.info("Get the Tier Name {}...", TierName);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Success", responseData), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Lead Approve in Ops portal
     */

    @PutMapping("/leadUpdate/{leadId}")
    public ResponseEntity<?> leadUpdate(HttpServletRequest request, @PathVariable("leadId") long leadId, @RequestBody LeadInformation leadInformation) throws Exception {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            logger.info("Approval process");
            LeadInformation leadInformation1 = implementationService.checkLead(leadId);
            if (leadInformation1 != null) {
                Pair<Integer, Object> lead = implementationService.leadApproval(leadId, leadInformation);
                if (lead.getFirst() == 1) {
                    LeadStatus status = leadStatusRepository.fetchLeadStatusById(LEAD_COMPLETED);
                    leadInformation1.setLeadStatus(status);
                    leadInformation1.setLeadApprovedBy(getSessionUser());
                    leadInformation1.setLeadApprovedDate(DateUtil.currentDate());
                    LeadInformation leads = leadInformationRepository.save(leadInformation1);
                    logger.info("check status settlement -- : {}", leads.getLeadStatus().getLeadStatusName());
                    logger.info("success flow -- : {}", lead.getFirst());
                    return new ResponseEntity<>(new ApiResponse(OK, "success", lead.getSecond()), OK);
                } else if (lead.getFirst() == 0) {
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "documents not verified"), CONFLICT);
                } else {
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "Something went wrong! Please Try Again"), CONFLICT);
                }
            }
            return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "lead not found"), NOT_FOUND);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }


    @PutMapping("/leadUpdateDcb/{leadId}")
    public ResponseEntity<?> leadUpdateDcb(HttpServletRequest request, @PathVariable("leadId") long leadId, @RequestBody LeadInformation leadInformation) throws Exception {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            logger.info("Approval process");
            LeadInformation leadInformation1 = implementationService.checkLead(leadId);
            if (leadInformation1 != null) {
                Pair<Integer, Object> lead = implementationService.leadApprovalDcb(leadId, leadInformation);
                if (lead.getFirst() == 1) {
                    LeadStatus status = leadStatusRepository.fetchLeadStatusById(LEAD_COMPLETED);
                    leadInformation1.setLeadStatus(status);
                    leadInformation1.setLeadApprovedBy(getSessionUser());
                    leadInformation1.setLeadApprovedDate(DateUtil.currentDate());
                    LeadInformation leads = leadInformationRepository.save(leadInformation1);
                    logger.info("success flow -- : {}", lead.getFirst());
                    return new ResponseEntity<>(new ApiResponse(OK, "success", lead.getSecond()), OK);
                } else {
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "Something went wrong! Please Try Again"), CONFLICT);
                }
            }
            return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "lead not found"), NOT_FOUND);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Lead Reject in Ops portal
     * Action
     * 0 - No Settlement
     * 1- Settle On T+1
     * 2- Hold Settlement
     * 3- CardHolder Refund
     */

    @PutMapping("/leadReject/{leadId}/{action}")
    public ResponseEntity<?> leadReject(HttpServletRequest request, @PathVariable("leadId") long leadId, @PathVariable("action") long action, @RequestBody LeadInformation leadInformation) throws Exception {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            logger.info("Approval process");
            LeadInformation lead = implementationService.check(leadId);
            if (lead != null) {
                Pair<Integer, Object> leadInfo = implementationService.leadReject(leadId, action, leadInformation);
                if (leadInfo.getFirst() == 1) {
                    logger.info("Reject flow -- : {}", leadInfo.getFirst());
                    return new ResponseEntity<>(new ApiResponse(OK, "success", leadInfo.getSecond()), OK);
                } else if (leadInfo.getFirst() == 2) {
                    return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Something went wrong"), BAD_REQUEST);
                } else if (leadInfo.getFirst() == 3) {
                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical Error! Please Try Again"), INTERNAL_SERVER_ERROR);
                }
                return new ResponseEntity<>(new ApiResponse(CONFLICT, "Lead not Available"), CONFLICT);
            }
            return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "lead not found"), NOT_FOUND);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Transaction amount details in single TID
     */

    @GetMapping("/tidAmount/{tid}")
    public ResponseEntity<?> tidAmount(HttpServletRequest request, @PathVariable("tid") String tid) throws Exception {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            logger.info("transaction details -- : ");
            MarsDeviceDetails marsDeviceDetails = implementationService.tidCheck(tid);
            if (marsDeviceDetails != null) {
                AmountDto amountDto = implementationService.collectAmountDetails(marsDeviceDetails.getTid());
                if (amountDto != null) {
                    return new ResponseEntity<>(new ApiResponse(OK, "success", amountDto), OK);
                }
            }
            return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "tid not found"), NOT_FOUND);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }


    /**
     * Aggregator Lead Approve in OPS Portal
     */
    @PutMapping("agg-lead-update/{leadId}")
    public ResponseEntity<?> aggLeadUpdate(HttpServletRequest request, @PathVariable("leadId") long leadId) throws Exception {
//        String authtoken = request.getHeader("Authorization");
//        logger.info("authtoken==={}", authtoken);
//        User curuser = getSessionUser();
//        logger.info("Session User {}", curuser);
//        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
//        if (authtoken.equals(userauth.getToken())) {
        logger.info("approval process ----------:");
        LeadInformation leadInfo = implementationService.checkLead(leadId);
        if (leadInfo != null) {
            if (leadInfo.getLeadStatus().getLeadStatusId() == AGREEMENT_SIGNING_COMPLETED || leadInfo.isAgreementSuccess()) {
//            boolean isAllDocumentsVerified = implementationService.checkAllAggregatorDocumentsVerifiedByOps(leadInfo);
//            if(isAllDocumentsVerified){
//                Pair<Integer, Object> lead = implementationService.aggregatorLeadApproval(leadId);
//
//                if (leadInfo.getLeadStatus().getLeadStatusId() == LEAD_COMPLETED) {

                MarsOnboardingInformation marsOnboardingInformation = implementationService.setMarsDetails2(leadInfo);

                MarsDto marsDto = new MarsDto();
                EDCMarsResponseDto edcMarsResponseDto = new EDCMarsResponseDto();
                StringBuilder strResp = new StringBuilder();
                JSONParser parser = new JSONParser();
                marsDto.setDevice("");
                marsDto.setSalesInformation((JSONObject) parser.parse(marsOnboardingInformation.getSalesInformation()));
                marsDto.setAdditionalInfo((JSONObject) parser.parse(marsOnboardingInformation.getAdditionalInfo()));
                marsDto.setCompanyInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getCompanyInformation())));
                marsDto.setBusinessInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBusinessInformation())));
                marsDto.setPartnerInformation((JSONArray) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getPartnerInformation())));

                PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto();


                paymentDetailsDto.setAdvanceRentCollected(String.valueOf(leadInfo.getAmountCollected()));
                paymentDetailsDto.setAdvanceRentMode(leadInfo.getAdvanceRentMode());
//        paymentDetails.setCardAcceptance("0");
//        paymentDetails.setCashAtPosEnabled("N");
//        paymentDetails.setCreditCardBlock("N");
                paymentDetailsDto.setDeviceOwnedBy(leadInfo.getDeviceOwnedBy());
                paymentDetailsDto.setDevicePrice(Double.parseDouble(leadInfo.getDevicePrice()));
//                paymentDetailsDto.setEmiStartDate(leadInfo.getEmiStartDate());
                paymentDetailsDto.setEmiStartDate("17/10/2022");
                paymentDetailsDto.setEmiTenure(leadInfo.getEmiTenure());
                paymentDetailsDto.setGracePeriod(Integer.parseInt(String.valueOf(leadInfo.getGracePeriod())));
//                paymentDetailsDto.setInstallationFee(leadInfo.getMarsRentalPlans().getSetup_fees());
                paymentDetailsDto.setInstallationFee(String.valueOf(leadInfo.getSetUpFees()));
//        paymentDetails.setIntlCardAcceptance("N");
//        paymentDetails.setNetworkPreferred(String.valueOf(1));
                paymentDetailsDto.setNoOfMonthRentPaidInAdvance(String.valueOf(leadInfo.getNoOfMonthRentPaidInAdvance()));
//        paymentDetails.setNumberOfTerminals(String.valueOf(1));
//        paymentDetails.setOmcEnabled(String.valueOf(false));
//        paymentDetails.setOtherCharges("");
                paymentDetailsDto.setRecurringFees(String.valueOf(leadInfo.getRecurringFees()));
                paymentDetailsDto.setRentalMode(leadInfo.getRentalMode());
                paymentDetailsDto.setRentalPlanCode(Integer.parseInt(leadInfo.getMarsRentalPlans().getCode()));
                paymentDetailsDto.setRentalType(leadInfo.getRentalType());
//        paymentDetails.setServiceProvider(String.valueOf(1));
                paymentDetailsDto.setTerminalModeCode(leadInfo.getTerminalModeCode()); //need to check
                logger.info("deviceModeCode ---> {}", paymentDetailsDto.getTerminalModeCode());
                paymentDetailsDto.setTerminalType(leadInfo.getTerminalType());
//        paymentDetails.setTipPercentage(leadInformation.getTipPercentage());
//        paymentDetails.setTotalAmountPaid("");
                paymentDetailsDto.setTotalEmiAmount(leadInfo.getTotalEmiAmount());

                marsDto.setPaymentDetails(paymentDetailsDto);
                marsDto.setBankInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBankInformation())));

//                    MdrPlanDto mdrPlanDto = new MdrPlanDto();
//                    mdrPlanDto.setCode(9); //need to check
//                    logger.info("mdrPlanDto info data.............");
//                    MdrChargesDto masterPass = new MdrChargesDto();
//                    masterPass.setFixed(0);
//                    masterPass.setMinimum(0);
//                    masterPass.setPercentage(0);
//                    mdrPlanDto.setMasterPass(masterPass);
//                    MdrChargesDto diners = new MdrChargesDto();
//                    diners.setFixed(0);
//                    diners.setMinimum(0);
//                    diners.setPercentage(0);
//                    mdrPlanDto.setDiners(diners);
//                    MdrChargesDto onus = new MdrChargesDto();
//                    onus.setFixed(0);
//                    onus.setMinimum(0);
//                    onus.setPercentage(0);
//                    mdrPlanDto.setOnus(onus);
//                    MdrChargesDto standardOrClassic = new MdrChargesDto();
//                    standardOrClassic.setFixed(0);
//                    standardOrClassic.setMinimum(0);
//                    standardOrClassic.setPercentage(0);
//                    mdrPlanDto.setStandardOrClassic(standardOrClassic);
//                    MdrChargesDto domesticDebitUpTo2000 = new MdrChargesDto();
//                    domesticDebitUpTo2000.setFixed(0);
//                    domesticDebitUpTo2000.setMinimum(99999);
//                    domesticDebitUpTo2000.setPercentage(0);
//                    mdrPlanDto.setDomesticDebitUpTo2000(domesticDebitUpTo2000);
//                    MdrChargesDto superPremiumOrSignature = new MdrChargesDto();
//                    superPremiumOrSignature.setFixed(0);
//                    superPremiumOrSignature.setMinimum(0);
//                    superPremiumOrSignature.setPercentage(0);
//                    mdrPlanDto.setSuperPremiumOrSignature(superPremiumOrSignature);
//                    MdrChargesDto incentive = new MdrChargesDto();
//                    incentive.setFixed(0);
//                    incentive.setMinimum(0);
//                    incentive.setPercentage(0);
//                    mdrPlanDto.setIncentive(incentive);
//                    MdrChargesDto internationalDebitCard = new MdrChargesDto();
//                    internationalDebitCard.setFixed(0);
//                    internationalDebitCard.setMinimum(0);
//                    internationalDebitCard.setPercentage(0);
//                    mdrPlanDto.setInternationalDebitCard(internationalDebitCard);
//                    MdrChargesDto internationalCreditCard = new MdrChargesDto();
//                    internationalCreditCard.setFixed(0);
//                    internationalCreditCard.setMinimum(0);
//                    internationalCreditCard.setPercentage(0);
//                    mdrPlanDto.setInternationalCreditCard(internationalCreditCard);
//                    MdrChargesDto premiumOrPlatinum = new MdrChargesDto();
//                    premiumOrPlatinum.setFixed(0);
//                    premiumOrPlatinum.setMinimum(0);
//                    premiumOrPlatinum.setPercentage(0);
//                    mdrPlanDto.setPremiumOrPlatinum(premiumOrPlatinum);
//                    MdrChargesDto domesticDebitAbove2000 = new MdrChargesDto();
//                    domesticDebitAbove2000.setFixed(0);
//                    domesticDebitAbove2000.setMinimum(99999);
//                    domesticDebitAbove2000.setPercentage(0);
//                    mdrPlanDto.setDomesticDebitAbove2000(domesticDebitAbove2000);
//                    MdrChargesDto cashAtPos = new MdrChargesDto();
//                    cashAtPos.setFixed(0);
//                    cashAtPos.setMinimum(0);
//                    cashAtPos.setPercentage(0);
//                    mdrPlanDto.setCashAtPos(cashAtPos);
//                    MdrChargesDto convenientFee = new MdrChargesDto();
//                    convenientFee.setFixed(0);
//                    convenientFee.setMinimum(0);
//                    convenientFee.setPercentage(0);
//                    mdrPlanDto.setConvenientFee(convenientFee);
//                    MdrChargesDto amexDomestic = new MdrChargesDto();
//                    amexDomestic.setFixed(0);
//                    amexDomestic.setMinimum(0);
//                    amexDomestic.setPercentage(0);
//                    mdrPlanDto.setAmexDomestic(amexDomestic);
//                    MdrChargesDto amexInternational = new MdrChargesDto();
//                    amexInternational.setFixed(0);
//                    amexInternational.setMinimum(0);
//                    amexInternational.setPercentage(0);
//                    mdrPlanDto.setAmexInternational(amexInternational);
//                    MdrChargesDto commercialOrCorporate = new MdrChargesDto();
//                    commercialOrCorporate.setFixed(0);
//                    commercialOrCorporate.setMinimum(0);
//                    commercialOrCorporate.setPercentage(0);
//                    mdrPlanDto.setCommercialOrCorporate(commercialOrCorporate);
//                    MdrChargesDto upiCreditUpto2000 = new MdrChargesDto();
//                    upiCreditUpto2000.setFixed(0);
//                    upiCreditUpto2000.setMinimum(0);
//                    upiCreditUpto2000.setPercentage(0);
//                    mdrPlanDto.setUpiCreditUpto2000(upiCreditUpto2000);
//                    MdrChargesDto upiCreditAbove2000 = new MdrChargesDto();
//                    upiCreditAbove2000.setFixed(0);
//                    upiCreditAbove2000.setMinimum(0);
//                    upiCreditAbove2000.setPercentage(0);
//                    mdrPlanDto.setUpiCreditAbove2000(upiCreditAbove2000);
//                    MdrChargesDto upiUpto2000 = new MdrChargesDto();
//                    upiUpto2000.setFixed(0);
//                    upiUpto2000.setMinimum(0);
//                    upiUpto2000.setPercentage(0);
//                    mdrPlanDto.setUpiUpto2000(upiUpto2000);
//                    MdrChargesDto upiAbove2000 = new MdrChargesDto();
//                    upiAbove2000.setFixed(0);
//                    upiAbove2000.setMinimum(0);
//                    upiAbove2000.setPercentage(0);
//                    mdrPlanDto.setUpiAbove2000(upiAbove2000);

                MdrPlanDto mdrPlanDto = new MdrPlanDto();

                ObjectMapper mapper = new ObjectMapper();

                if (leadInfo.getMarsMdrCharges() != null) {
                    mdrPlanDto.setMasterPass(mapper.readValue(leadInfo.getMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
                    mdrPlanDto.setDiners(mapper.readValue(leadInfo.getDiners(), MdrChargesDto.class));
                    mdrPlanDto.setOnus(mapper.readValue(leadInfo.getMarsMdrCharges().getOnus(), MdrChargesDto.class));
                    mdrPlanDto.setStandardOrClassic(mapper.readValue(leadInfo.getStandardOrClassic(), MdrChargesDto.class));
                    mdrPlanDto.setDomesticDebitUpTo2000(mapper.readValue(leadInfo.getDomesticDebitUpTo2000(), MdrChargesDto.class));
                    mdrPlanDto.setDomesticDebitAbove2000(mapper.readValue(leadInfo.getDomesticDebitAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(leadInfo.getSuperPremiumOrSignature(), MdrChargesDto.class));
                    mdrPlanDto.setInternationalCreditCard(mapper.readValue(leadInfo.getInternationalCreditCard(), MdrChargesDto.class));
                    mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInfo.getInternationalDebitCard(), MdrChargesDto.class));
                    mdrPlanDto.setCommercialOrCorporate(mapper.readValue(leadInfo.getCommercialOrCorporate(), MdrChargesDto.class));
                    mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(leadInfo.getPremiumOrPlatinum(), MdrChargesDto.class));
                    mdrPlanDto.setCashAtPos(mapper.readValue(leadInfo.getMarsMdrCharges().getCashAtPos(), MdrChargesDto.class));
                    mdrPlanDto.setCode(Integer.parseInt(leadInfo.getMarsMdrCharges().getCode()));
                    mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInfo.getUpiAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInfo.getUpiUpto2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiCreditAbove2000(mapper.readValue(leadInfo.getUpiCreditAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiCreditUpto2000(mapper.readValue(leadInfo.getUpiCreditUpto2000(), MdrChargesDto.class));
                    if (leadInfo.getLeadSource().getLeadSourceName().equals("LS_Wowpe")) {
                        mdrPlanDto.setAmexDomestic(mapper.readValue(leadInfo.getAmexDomestic(), MdrChargesDto.class));
                        mdrPlanDto.setAmexInternational(mapper.readValue(leadInfo.getAmexInternational(), MdrChargesDto.class));
                    }

                }
                marsDto.setMdrPlan(mdrPlanDto);
                marsDto.setRemarks("");
                marsDto.setRevParameters((JSONObject) parser.parse(marsOnboardingInformation.getPaymentDetails()));

                EdcResponseDto edcData = null;
                logger.info(" data 2 ... {}", marsDto);
                HashMap<String, Object> dummyDto = new HashMap<>();
                dummyDto.put("merchant", marsDto);
                JSONObject jsonObject = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                logger.info(" JSON OBJ : ---- : {}", jsonObject);
                logger.info(" dummy -- : {}", CommonUtil.parseJson(jsonObject));
                HttpURLConnection conn = null;
                try {
                    URL obj = new URL(marsTidUrl);
//            URL obj = new URL(marsUrl);
//                    URL obj = new URL("https://marspp.bijlipay.co.in:9090/v1/merchant");
                    conn = (HttpURLConnection) obj.openConnection();
                    conn.setRequestProperty("NII", "104");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    // HTTP Connection Headers completed
                    logger.info("HTTP headers : ----- : {}", conn);
                    // Sending API request
                    logger.info("Getting Tickets Status {}", dummyDto);
                    OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                    JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                    out.write(CommonUtil.parseJson(reqData)); // sending the data
                    logger.info("Getting Tickets Status {}", reqData);
                    out.close(); // closing the output stream
                    int responsecode = conn.getResponseCode();
                    logger.info("The response code is {}", conn);
                    String message = conn.getResponseMessage();
                    logger.info("The response code is {}", responsecode);
                    logger.info("The response message is {}", message);
                    edcMarsResponseDto.setCode(conn.getResponseCode());
                } catch (Exception e) {
                    leadInfo.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                    leadInformationRepository.save(leadInfo);
                    logger.info("exception occurred -----------: {}", e.getLocalizedMessage());
                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "SOMETHING WENT WRONG", e.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
                }

                if (conn.getResponseCode() == 201) {
                    logger.info("Processing the request response -------------------------");
                    // Inputstream parse it as response
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    logger.info("response data {}", conn.getInputStream());
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        strResp.append(inputLine);
                        logger.info("responseee ----> {}", strResp);
                    }
                    in.close();
                    edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
//                edcMarsResponseDto.setData(edcData);
                    String data = strResp.toString();
                    Gson gson = new Gson();
                    edcData = gson.fromJson(data, EdcResponseDto.class);
                    if (Objects.equals(edcData.getResponseCode(), "00")) {
                        logger.info("-------------------Sucesss-----------------{}", edcData.getResponseCode());
                        if (edcData.getMerchantId() != null && edcData.getTerminalId() != null) {
                            edcData.setRemarks("Onboard-Success");
                            leadInfo.setIsMarsApiTriggered(MARS_RESPONSE_TID_CREATED);
                            leadInformationRepository.save(leadInfo);
                        } else {

                            logger.info("-------------------InternalServerError-----------------{}", edcData.getResponseCode());
                            leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(HOST_API_FAILED));
                            leadInfo.setActive(false);
                            leadInformationRepository.save(leadInfo);

                            leadInfo.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                            leadInformationRepository.save(leadInfo);
                            return new ResponseEntity<>(new ApiResponse(CONFLICT, "Failure", edcData), CONFLICT);
                        }
                        edcMarsResponseDto.setData(edcData);
                        edcMarsResponseDto.setMessage("Onboard Success");

                    }
                    if (Objects.equals(edcData.getResponseCode(), "101") || Objects.equals(edcData.getResponseCode(), "102")) {
                        logger.info("-------------------DMO Failed-----------------{}", edcData.getResponseCode());
                        leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(DMO_API_FAILED));
                        leadInfo.setActive(false);
                        leadInfo.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                        leadInformationRepository.save(leadInfo);

                        return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical issue ....Contact Bijlipay", MARS_REJECT), INTERNAL_SERVER_ERROR);
                    } else if (Objects.equals(edcData.getResponseCode(), "100")) {
                        logger.info("-------------------Host Failed-----------------{}", edcData.getResponseCode());
                        leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(HOST_API_FAILED));
                        leadInfo.setActive(false);
                        leadInfo.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                        leadInformationRepository.save(leadInfo);

                        return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical issue ....Contact Bijlipay", MARS_REJECT), INTERNAL_SERVER_ERROR);
                    } else if (Objects.equals(edcData.getResponseCode(), "103")) {
                        logger.info("-------------------OGS Failed-----------------{}", edcData.getResponseCode());
                        leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(OGS_API_FAILED));
                        leadInfo.setActive(false);
                        leadInfo.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                        leadInformationRepository.save(leadInfo);

                        return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical issue ....Contact Bijlipay", MARS_REJECT), INTERNAL_SERVER_ERROR);
                    }
                } else if (conn.getResponseCode() == 404) {

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    String jsonString = sb.toString();
                    JSONObject object = (JSONObject) parser.parse(jsonString);
                    logger.info("object -------------- : {}", object);

                    leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(HOST_API_FAILED));
                    leadInfo.setActive(false);
                    leadInfo.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                    leadInformationRepository.save(leadInfo);

                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Something went wrong", MARS_REJECT), INTERNAL_SERVER_ERROR);
                } else if (conn.getResponseCode() == 400) {

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    String jsonString = sb.toString();
                    JSONObject object = (JSONObject) parser.parse(jsonString);
                    logger.info("object -------------- : {}", object);

                    leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(HOST_API_FAILED));
                    leadInfo.setActive(false);
                    leadInfo.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                    leadInformationRepository.save(leadInfo);

                    CompletableFuture.runAsync(() -> {
                        implementationService.updateSwitchRejectedToOnboard(leadInfo);
                    });

                    org.json.JSONObject jsonObject1 = new org.json.JSONObject(object);
                    logger.info("jsonObject ------------: {}", jsonObject1);
                    org.json.JSONObject jsonArray = new org.json.JSONObject(jsonObject1.getJSONArray("errorDetails"));
                    logger.info("jsonArray -----------: {}", jsonArray);

//                edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
//                edcMarsResponseDto.setCode(400);
//                edcMarsResponseDto.setMessage("Technical issue....Contact bijlipay");
//                        leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(LEAD_REJECTED_BY_SWITCH));

//                    leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(AGREEMENT_SIGNING_COMPLETED));
//                    leadInfo.setSwitchRejectDate(DateUtil.currentDate());
//                    leadInfo.setActive(true);
//                    leadInfo.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
//                    leadInformationRepository.save(leadInfo);
//                    //Update Data to Onbooard Portal
//                    CompletableFuture.runAsync(() -> {
//                        implementationService.updateSwitchRejectedToOnboard(leadInfo);
//                    });

                    return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Technical issue ....Contact Bijlipay", MARS_REJECT), BAD_REQUEST);
                } else if (conn.getResponseCode() == 0) {
//                edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
//                edcMarsResponseDto.setCode(0);
//                edcMarsResponseDto.setMessage("Connection Timed Out..........Try again");
                    leadInfo.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                    leadInformationRepository.save(leadInfo);
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "Connection timeout ..... Try again"), CONFLICT);
                } else if (conn.getResponseCode() == 500) {
                    leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(HOST_API_FAILED));
                    leadInfo.setActive(false);
                    leadInfo.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                    leadInformationRepository.save(leadInfo);

                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical issue ....Contact Bijlipay(500)", MARS_REJECT), INTERNAL_SERVER_ERROR);
                }

                MarsDeviceDetails deviceDetails = null;
                if (edcMarsResponseDto.getData().getTerminalId() != null && edcMarsResponseDto.getData().getMerchantId() != null) {
                    MarsDeviceDetails marsDeviceDetails1 = implementationService.saveDatasToMarsAndLeadTable1(implementationService.setValuesToDto(edcMarsResponseDto));
                    if (leadInfo.getLeadSource() != null && leadInfo.getLeadSource().getLeadSourceName().equalsIgnoreCase(LS_VIYONA_FINTECH)) {
                        String uniqueKey = randomGenerator.generateUniqueRandomString(RandomGenerator.DEFAULT_LENGTH);
                        logger.info("uniqueKey -----------:{}", uniqueKey);
                        marsDeviceDetails1.setTidKey(uniqueKey);
                        marsDeviceDetailsRepository.save(marsDeviceDetails1);
                    }

                    deviceDetails = marsDeviceDetails1;
                    List<MarsDeviceDetails> marsDeviceDetails = marsDeviceDetailsRepository.findLeadId(marsDeviceDetails1.getLeadInformation());

                    marsDeviceDetails.forEach(l -> {
                        l.setScheduleDate(DateUtil.currentDate());
                        marsDeviceDetailsRepository.save(l);
                    });

                    //Update Data to Onbooard Portal
                    CompletableFuture.runAsync(() -> {
                        implementationService.updateLeadAndMarsDataToOnboard(marsDeviceDetails1);
                    });
//                    if (leadInfo.getLeadSource().getLeadSourceName().equals("LS_Wowpe")) {
//                        CompletableFuture.runAsync(() -> {
//                            logger.info("-Webbackend triggered-");
//                            implementationService.updateLeadImplOnboardPortal(marsDeviceDetails1);
//                        });
//                    }
                    logger.info("Schedule date set --");
                }
                Pair<Integer, Object> lead = null;
                if (!leadInfo.getLeadSource().getLeadSourceName().equals("LS_Wowpe")) {
                    lead = implementationService.aggregatorLeadApproval(leadId);
                } else {
                    logger.info("lead is Wowpe");
                    leadInfo.setActive(true);
                    leadInformationRepository.save(leadInfo);
                    return new ResponseEntity<>(new ApiResponse(OK, "success", deviceDetails), OK);
                }
//                Pair<Integer, Object> lead = implementationService.aggregatorLeadApproval(leadId);
//                if (leadInfo.getLeadStatus().getLeadStatusId() == LEAD_COMPLETED)
                logger.info("lead -------------: {}", lead.getFirst());
                if (lead.getFirst() == 1) {
                    logger.info("----------------------------------leadUpdate--{}", lead.getFirst());
                    ResponseEntity<String> out = null;
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<Long> entity = new HttpEntity<>(leadInfo.getLeadId(), httpHeaders);
                    logger.info("-------------SQR TRIGGERED---------{}", entity);//SQR Onboarding trigger
                    String url = aggGeneratesVpaTidUrl + "/{leadId}";
                    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
                    Map<String, Long> urlParams = new HashMap<>();
                    urlParams.put("leadId", leadInfo.getLeadId());
                    out = restTemplate.exchange(builder.build(urlParams), HttpMethod.POST, entity, String.class);
                    int responseCode = out.getStatusCode().value();
                    logger.info("--------------------------------responseCode---{}", responseCode);
                    return new ResponseEntity<>(new ApiResponse(OK, "success", deviceDetails), OK);
                } else if (lead.getFirst() == 2) {
                    logger.info("lead -------------: {}", lead.getFirst());
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "Tid created , settlement not enabled", deviceDetails), OK);
                }
                return new ResponseEntity<>(new ApiResponse(CONFLICT, "Lead not approved"), CONFLICT);
//                }
            }
            return new ResponseEntity<>(new ApiResponse(CONFLICT, "Agreement not completed"), CONFLICT);
//            }
//            return new ResponseEntity<>(new ApiResponse(CONFLICT,"Documents not verified !"),CONFLICT);
        }
        return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "Lead not Found"), NOT_FOUND);
//        } else {
//
//            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
//        }
    }

    @PutMapping("/agg-lead-reject/{leadId}")
    public ResponseEntity<?> aggregatorLeadReject(HttpServletRequest request, @PathVariable("leadId") long leadId, @RequestBody LeadInformation leadInformation) throws Exception {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            logger.info("Approval process");
            LeadInformation lead = implementationService.checkLead(leadId);
            if (lead != null) {
                Pair<Integer, Object> leadInfo = implementationService.aggregatorLeadReject(leadId, leadInformation);
                if (leadInfo.getFirst() == 1) {
                    logger.info("Reject flow -- : {}", leadInfo.getFirst());
                    return new ResponseEntity<>(new ApiResponse(OK, "success", leadInfo.getSecond()), OK);
                } else if (leadInfo.getFirst() == 2) {
                    return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Something went wrong"), BAD_REQUEST);
                } else if (leadInfo.getFirst() == 3) {
                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical Error! Please Try Again"), INTERNAL_SERVER_ERROR);
                }
                return new ResponseEntity<>(new ApiResponse(CONFLICT, "documents not verified"), CONFLICT);
            }
            return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "lead not found"), NOT_FOUND);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/activateSqr")
    public ResponseEntity<?> activateSqr(@RequestBody TidVpaMap tidVpaMapDto) throws Exception {
        logger.info("activate Sqr -- : ");
        TidVpaMapping tidVpaMapping = leadInformationService.checkVpaExists(tidVpaMapDto);
        if (tidVpaMapping != null) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.CONFLICT, "vpa already mapped -- :{}", tidVpaMapDto.getVpa()), HttpStatus.CONFLICT);
        }
        LeadInformation leadInfo = leadInformationService.findLead(tidVpaMapDto.getLead_id());
        logger.info("-----------------------------------------------------------------------{}", leadInfo);
        if (leadInfo != null) {
            MarsOnboardingInformation marsOnboardingInformation = leadInformationService.setMarsDetails(leadInfo);
            MarsDto marsDto = new MarsDto();
            EDCMarsResponseDto edcMarsResponseDto = new EDCMarsResponseDto();
            StringBuilder strResp = new StringBuilder();
            JSONParser parser = new JSONParser();
            marsDto.setDevice("");
            marsDto.setSalesInformation((JSONObject) parser.parse(marsOnboardingInformation.getSalesInformation()));
            marsDto.setAdditionalInfo((JSONObject) parser.parse(marsOnboardingInformation.getAdditionalInfo()));
            marsDto.setCompanyInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getCompanyInformation())));
            marsDto.setBusinessInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBusinessInformation())));
            marsDto.setPartnerInformation((JSONArray) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getPartnerInformation())));

            PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto();

            paymentDetailsDto.setAdvanceRentCollected(String.valueOf(leadInfo.getAmountCollected()));
            paymentDetailsDto.setAdvanceRentMode(leadInfo.getAdvanceRentMode());
//        paymentDetails.setCardAcceptance("0");
//        paymentDetails.setCashAtPosEnabled("N");
//        paymentDetails.setCreditCardBlock("N");
            paymentDetailsDto.setDeviceOwnedBy(leadInfo.getDeviceOwnedBy());
//            paymentDetailsDto.setDevicePrice(Double.parseDouble(leadInfo.getDevicePrice()));
            paymentDetailsDto.setDevicePrice(Double.parseDouble(leadInfo.getDevicePrice()));
            paymentDetailsDto.setEmiStartDate(leadInfo.getEmiStartDate());
            paymentDetailsDto.setEmiTenure(leadInfo.getEmiTenure());
            paymentDetailsDto.setGracePeriod(leadInfo.getGracePeriod());
            paymentDetailsDto.setInstallationFee(String.valueOf(leadInfo.getInstallationFee()));
//        paymentDetails.setIntlCardAcceptance("N");
//        paymentDetails.setNetworkPreferred(String.valueOf(1));
            paymentDetailsDto.setNoOfMonthRentPaidInAdvance(String.valueOf(leadInfo.getNoOfMonthRentPaidInAdvance()));
//        paymentDetails.setNumberOfTerminals(String.valueOf(1));
//        paymentDetails.setOmcEnabled(String.valueOf(false));
//        paymentDetails.setOtherCharges("");
            paymentDetailsDto.setRecurringFees(String.valueOf(leadInfo.getRecurringFees()));
            paymentDetailsDto.setRentalMode(leadInfo.getRentalMode());
            paymentDetailsDto.setRentalPlanCode(Integer.parseInt(leadInfo.getMarsRentalPlans().getCode()));
            paymentDetailsDto.setRentalType(leadInfo.getRentalType());
//        paymentDetails.setServiceProvider(String.valueOf(1));
            paymentDetailsDto.setTerminalModeCode(leadInfo.getTerminalModeCode()); //need to check
            logger.info("deviceModeCode ---> {}", paymentDetailsDto.getTerminalModeCode());
            paymentDetailsDto.setTerminalType(leadInfo.getTerminalType());
//        paymentDetails.setTipPercentage(leadInformation.getTipPercentage());
//        paymentDetails.setTotalAmountPaid("");
            paymentDetailsDto.setTotalEmiAmount(leadInfo.getTotalEmiAmount());
            paymentDetailsDto.setVpa(Collections.singletonList(tidVpaMapDto.getVpa()));

            marsDto.setPaymentDetails(paymentDetailsDto);
            marsDto.setBankInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBankInformation())));

            com.bijlipay.model.marsdto.MdrPlanDto mdrPlanDto = new MdrPlanDto();

            ObjectMapper mapper = new ObjectMapper();

            if (leadInfo.getMarsMdrCharges() != null) {
                mdrPlanDto.setMasterPass(mapper.readValue(leadInfo.getMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
                mdrPlanDto.setDiners(mapper.readValue(leadInfo.getMarsMdrCharges().getDiners(), MdrChargesDto.class));
                mdrPlanDto.setOnus(mapper.readValue(leadInfo.getMarsMdrCharges().getOnus(), MdrChargesDto.class));
                mdrPlanDto.setStandardOrClassic(mapper.readValue(leadInfo.getMarsMdrCharges().getStandardOrClassic(), MdrChargesDto.class));
                mdrPlanDto.setDomesticDebitUpTo2000(mapper.readValue(leadInfo.getMarsMdrCharges().getDomesticDebitUpTo2000(), MdrChargesDto.class));
                mdrPlanDto.setDomesticDebitAbove2000(mapper.readValue(leadInfo.getMarsMdrCharges().getDomesticDebitAbove2000(), MdrChargesDto.class));
                mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(leadInfo.getMarsMdrCharges().getSuperPremiumOrSignature(), MdrChargesDto.class));
                mdrPlanDto.setInternationalCreditCard(mapper.readValue(leadInfo.getMarsMdrCharges().getInternationalCreditCard(), MdrChargesDto.class));
                mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInfo.getMarsMdrCharges().getInternationalDebitCard(), MdrChargesDto.class));
                mdrPlanDto.setCommercialOrCorporate(mapper.readValue(leadInfo.getMarsMdrCharges().getCommercialOrCorporate(), MdrChargesDto.class));
                mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(leadInfo.getMarsMdrCharges().getPremiumOrPlatinum(), MdrChargesDto.class));
                mdrPlanDto.setCashAtPos(mapper.readValue(leadInfo.getMarsMdrCharges().getCashAtPos(), MdrChargesDto.class));
                mdrPlanDto.setCode(Integer.parseInt(leadInfo.getMarsMdrCharges().getCode()));
                mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInfo.getUpiAbove2000(), MdrChargesDto.class));
                mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInfo.getUpiUpto2000(), MdrChargesDto.class));
                mdrPlanDto.setUpiCreditAbove2000(mapper.readValue(leadInfo.getUpiCreditAbove2000(), MdrChargesDto.class));
                mdrPlanDto.setUpiCreditUpto2000(mapper.readValue(leadInfo.getUpiCreditUpto2000(), MdrChargesDto.class));

            }
            marsDto.setMdrPlan(mdrPlanDto);
            marsDto.setRemarks("");
            marsDto.setRevParameters((JSONObject) parser.parse(marsOnboardingInformation.getPaymentDetails()));

            EdcResponseDto edcData = null;
            logger.info(" data 2 ... {}", marsDto);
            HashMap<String, Object> dummyDto = new HashMap<>();
            dummyDto.put("merchant", marsDto);
            JSONObject jsonObject = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
            logger.info(" JSON OBJ : ---- : {}", jsonObject);
            logger.info(" dummy -- : {}", CommonUtil.parseJson(jsonObject));
            HttpURLConnection conn = null;
            try {
                URL obj = new URL(marsVpaUrl);
                conn = (HttpURLConnection) obj.openConnection();
                conn.setRequestProperty("NII", "110");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                // HTTP Connection Headers completed
                logger.info("HTTP headers : ----- : {}", conn);
                // Sending API request
                logger.info("Getting Tickets Status {}", dummyDto);
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                out.write(CommonUtil.parseJson(reqData)); // sending the data
                logger.info("Getting Tickets Status {}", reqData);
                out.close(); // closing the output stream
                int responsecode = conn.getResponseCode();
                logger.info("The response code is {}", conn);
                String message = conn.getResponseMessage();
                logger.info("The response code is {}", responsecode);
                logger.info("The response message is {}", message);
                edcMarsResponseDto.setCode(conn.getResponseCode());
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "SOMETHING WENT WRONG", e.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
            }
            if (conn.getResponseCode() == 201) {
                logger.info("Processing the request response -------------------------");
                // Inputstream parse it as response+
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                logger.info("response data {}", conn.getInputStream());
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    strResp.append(inputLine);
                    logger.info("responseee ----> {}", strResp);
                }
                in.close();
                edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
//                edcMarsResponseDto.setData(edcData);
                String data = strResp.toString();
                Gson gson = new Gson();
                edcData = gson.fromJson(data, EdcResponseDto.class);
                logger.info("10 ---- {}", edcData.getResponseCode());
                if (edcData.getMerchantId() != null && edcData.getTerminalId() != null) {
                    edcData.setRemarks("Onboard-Success");

                } else if (Objects.equals(edcData.getResponseCode(), "101")) {
                    logger.info("responseee 101 ----> {}", edcData.getResponseCode());
                    edcData.setRemarks("Sqr-Failed");

                } else {
                    edcData.setRemarks("Sqr-Failed");
                    logger.info("------------------------else---------------{}", edcData.getRemarks());
                    leadInfo.setLeadStatus(leadStatusRepository.fetchLeadStatusById(SQR_ONBOARDING_FAILED));
                    edcData.setFlag(false);
                    leadInfo.setActive(false);
                    leadInformationRepository.save(leadInfo);
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "Failure", edcData), CONFLICT);
                }
                edcMarsResponseDto.setData(edcData);
                edcMarsResponseDto.setMessage("Onboard Success");

            } else if (conn.getResponseCode() == 500) {
                return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical issue ....Contact Bijlipay"), INTERNAL_SERVER_ERROR);
            } else if (conn.getResponseCode() == 404) {

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                String jsonString = sb.toString();
                JSONObject object = (JSONObject) parser.parse(jsonString);
                logger.info("object -------------- : {}", object);

                return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Something went wrong"), INTERNAL_SERVER_ERROR);
            } else if (conn.getResponseCode() == 400) {

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                String jsonString = sb.toString();
                JSONObject object = (JSONObject) parser.parse(jsonString);
                logger.info("object -------------- : {}", object);

                org.json.JSONObject jsonObject1 = new org.json.JSONObject(object);
                logger.info("jsonObject ------------: {}", jsonObject1);
//                org.json.JSONObject jsonArray = new org.json.JSONObject(jsonObject1.getJSONArray("errorDetails"));
//                logger.info("jsonArray -----------: {}", jsonArray);


                return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Technical issue ....Contact Bijlipay"), BAD_REQUEST);
            } else if (conn.getResponseCode() == 0) {
//                edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
//                edcMarsResponseDto.setCode(0);
//                edcMarsResponseDto.setMessage("Connection Timed Out..........Try again");

                return new ResponseEntity<>(new ApiResponse(CONFLICT, "Connection timeout ..... Try again"), CONFLICT);
            }

            MarsDeviceDetails deviceDetails = null;

            if (edcMarsResponseDto.getData().getTerminalId() != null && edcMarsResponseDto.getData().getMerchantId() != null) {
                deviceDetails = leadInformationService.saveDatasToMarsAndLeadTable(leadInformationService.setValuesToDto(edcMarsResponseDto));
            }
            MarsDeviceDetails marsDeviceDetails1 = marsDeviceDetailsRepository.findMarsData(leadInfo.getLeadId());
//            CompletableFuture.runAsync(() -> {
            logger.info("------------settlement enable---{}", new Gson().toJson(deviceDetails));
            MarsDeviceDetails mars = implementationService.updateLeadAndMarsDataToOnboard1(deviceDetails);
            logger.info("mars ----------------: {}", mars);
//            });
            logger.info("------------settlement---{}-------{}-------{}-----------{}", deviceDetails.getTid(), deviceDetails.getMid(), deviceDetails.getSqrVpa(), tidVpaMapDto.getVpa());

            if (deviceDetails.getTid() != null && deviceDetails.getMid() != null && deviceDetails.getSqrVpa().equalsIgnoreCase(tidVpaMapDto.getVpa())) {
                logger.info("proceed to settlement enable -- : ");
                Pair<Integer, Object> lead = leadInformationService.enableSettlement(deviceDetails);
                logger.info("--------lead update for settlement-------{}", lead.getFirst());
                if (lead.getFirst() == 1) {
                    logger.info("proceed to tid mapping --: ");
                    //Mapping Tid with VPA after mars Onboard completion
                    MarsDeviceDetails marsDeviceDetails = leadInformationService.activateVpa(tidVpaMapDto);
                    if (marsDeviceDetails != null) {
                        return new ResponseEntity<>(new ApiResponse(OK, "success", marsDeviceDetails), OK);
                    } else {
                        return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "vpa not mapped"), HttpStatus.INTERNAL_SERVER_ERROR);
                    }
//                    return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "vpa not mapped"), HttpStatus.INTERNAL_SERVER_ERROR);
                } else if (lead.getFirst() == 2) {
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "Tid created , settlement not enabled", deviceDetails), OK);
                }
            }
        }
        return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "Lead not Found"), NOT_FOUND);
    }

    @PostMapping("/sqr-lead-mars-update/{leadNumber}")
    public ResponseEntity<?> sqrMarsUpdateData(@PathVariable("leadNumber") String leadNumber, @RequestBody LeadMarsDataUpdateDto leadMarsDataUpdateDto) {
        LeadInformation leadInformation = implementationService.findLeadByLeadNumber(leadNumber);
        if (leadInformation != null) {
            LeadMarsDataUpdateDto marsDataUpdateDto = implementationService.updateMarsDataFromInventory(leadNumber, leadMarsDataUpdateDto);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success", marsDataUpdateDto), OK);
        }
        return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "Lead Not Found"), NOT_FOUND);
    }

    @PutMapping("/agg-mcc-reject/{leadId}")
    public ResponseEntity<?> aggregatorLeadRejectMcc(HttpServletRequest request, @PathVariable("leadId") long leadId, @RequestBody DocumentUpdateDto documentUpdateDto) throws Exception {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            logger.info("Approval process");
            LeadInformation lead = implementationService.checkLead(leadId);
            if (lead != null) {
                Pair<Integer, Object> leadInfo = implementationService.aggregatorLeadRejectMcc(leadId, documentUpdateDto, curuser);
                if (leadInfo.getFirst() == 1) {
                    logger.info("Reject flow -- : {}", leadInfo.getFirst());
                    return new ResponseEntity<>(new ApiResponse(OK, "success", leadInfo.getSecond()), OK);
                } else if (leadInfo.getFirst() == 2) {
                    return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Something went wrong"), BAD_REQUEST);
                } else if (leadInfo.getFirst() == 3) {
                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical Error! Please Try Again"), INTERNAL_SERVER_ERROR);
                }
                return new ResponseEntity<>(new ApiResponse(CONFLICT, "documents not verified"), CONFLICT);
            }
            return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "lead not found"), NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    //1--->verify document
    //2--->approve Document
    @PostMapping("agg-lead-approve/{leadId}/{action}")
    public ResponseEntity<?> aggLeadApprove(@PathVariable("leadId") Long leadId, HttpServletRequest request, @PathVariable("action") int action, @RequestBody AggLeadApproveRequest data) {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            LeadInformation leadInformation = leadInformationService.findLead(leadId);
            logger.info("leadDetails -- : {}", leadInformation);
            String merchantCategoryCode = data.getMerchantCategoryCode();
            String amlStatus = data.getAmlStatus();
            logger.info("MCC --->{}",merchantCategoryCode!=null?merchantCategoryCode:"NA");
            logger.info("Aml Status --->{}",amlStatus!=null?amlStatus:"NA");
            if (leadInformation != null) {
                if (action == MCC_DOCUMENT_VERIFY) {
                    boolean isAllDocumentsVerified = leadInformationService.checkAllAggregatorDocumentsVerifiedByOps(leadInformation);
                    if (isAllDocumentsVerified) {
                        return new ResponseEntity<>(new ApiResponse(OK, "success", true), OK);
                    } else {
                        return new ResponseEntity<>(new ApiResponse(CONFLICT, "Documents not verified"), CONFLICT);

                    }
                } else if (action == MCC_FINAL_APPROVE) {
                    Pair<Integer, Object> lead = leadInformationService.aggregatorLeadApproval(leadInformation, merchantCategoryCode, curuser,amlStatus);
                    if (lead.getFirst() == 1) {
                        return new ResponseEntity<>(new ApiResponse(OK, "success", lead.getSecond()), OK);
                    }
                }
            }
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, "Lead Not Found"), NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);

        }
    }

    @PutMapping(value = {"implementation-qr-scan-pick/{vpa}/{id}", "implementation-qr-scan-pick/{vpa}/{id}/{isSoundBox}"})
    public ResponseEntity<?> getQrScanpick(HttpServletRequest request, @PathVariable("vpa") String vpa, @PathVariable("id") Long id, @PathVariable("isSoundBox") Optional<Boolean> isSoundBox) throws URISyntaxException, IOException, ParseException {

        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            LeadInformation leadData = leadInformationRepository.findLeadId(id);
            QrDto qrDto = new QrDto();
            qrDto.setVpa(vpa);

            JSONParser parser = new JSONParser();
            HttpURLConnection conn = null;
            URL obj = new URL(qrScanPickUpdateUrl);
            conn = (HttpURLConnection) obj.openConnection();
//                        conn.setRequestProperty("NII", "110");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            // HTTP Connection Headers completed
            logger.info("HTTP headers : ----- : {}", conn);
            // Sending API request

            logger.info("Getting Tickets Status1 {}", qrDto);
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(qrDto));
            out.write(CommonUtil.parseJson(reqData)); // sending the data
            logger.info("Getting Tickets Status {}", reqData);
            out.close(); // closing the output stream

            int responsecode = conn.getResponseCode();
            logger.info("The response code is {}", conn);
            String message = conn.getResponseMessage();

            logger.info("The response code is {}", responsecode);
            logger.info("The response message is {}", message);

            if (conn.getResponseCode() == 400 || conn.getResponseCode() == 404) {
                return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "VPA Not found"), BAD_REQUEST);
            } else if (conn.getResponseCode() == 500) {
                return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Connection failed"), INTERNAL_SERVER_ERROR);
            } else if (conn.getResponseCode() == 200) {
                if (leadData != null) {
                    VpaQr vpaQr = new VpaQr();
                    vpaQr.setVpa(vpa);
                    vpaQr.setSoundBox(isSoundBox.isPresent() && isSoundBox.get());
                    vpaQr.setLeadInformation(leadData);
                    vpaQrRepository.save(vpaQr);

                    leadData.setLeadStatus(leadStatusRepository.fetchLeadStatusById(MARS_QR_STATUS_SCAN_PICKED));
                    leadInformationRepository.save(leadData);
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success"), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Lead not found"), BAD_REQUEST);
                }


            }

            return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Already Scanned"), BAD_REQUEST);
        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("implementation-qr-scan-pick-multi/{soundBoxId}/{vpa}/{id}")
    public ResponseEntity<?> getQrScanpickMultiVpa(HttpServletRequest request, @PathVariable("vpa") List<String> vpa, @PathVariable("id") Long id, @PathVariable("soundBoxId") long soundBoxId) throws URISyntaxException, IOException, ParseException {

        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            LeadInformation leadData = leadInformationRepository.findLeadId(id);
            for (String Vpa : vpa) {
//                QrDto qrDto = new QrDto();
//                qrDto.setVpa(Vpa);
//
//                JSONParser parser = new JSONParser();
//                HttpURLConnection conn = null;
//                URL obj = new URL(qrScanPickUpdateUrl);
//                conn = (HttpURLConnection) obj.openConnection();
////                        conn.setRequestProperty("NII", "110");
//                conn.setRequestProperty("Content-Type", "application/json");
//                conn.setDoOutput(true);
//                conn.setRequestMethod("POST");
//                // HTTP Connection Headers completed
//                logger.info("HTTP headers : ----- : {}", conn);
//                // Sending API request
//
//                logger.info("Getting Tickets Status1 {}", qrDto);
//                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
//                JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(qrDto));
//                out.write(CommonUtil.parseJson(reqData)); // sending the data
//                logger.info("Getting Tickets Status {}", reqData);
//                out.close(); // closing the output stream
//
//                int responsecode = conn.getResponseCode();
//                logger.info("The response code is {}", conn);
//                String message = conn.getResponseMessage();
//
//                logger.info("The response code is {}", responsecode);
//                logger.info("The response message is {}", message);
//
//                if (conn.getResponseCode() == 400 || conn.getResponseCode() == 404) {
//                    return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "VPA Not found"), BAD_REQUEST);
//                } else if (conn.getResponseCode() == 500) {
//                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Connection failed"), INTERNAL_SERVER_ERROR);
//                } else if (conn.getResponseCode() == 200) {
//                if (leadData != null) {
//                    QRVpa qrVpa = new QRVpa();
//                    qrVpa.setVpa(vpa);
//                    qrVpa.setLeadInformation(leadData);
//                    qrVpaRepository.save(qrVpa);
                SoundBox soundBox = soundBoxRepository.findById1(soundBoxId);
                VpaQr vpaQr = new VpaQr();
                vpaQr.setVpa(Vpa);
                vpaQr.setSoundBox(soundBox);
                vpaQr.setLeadInformation(leadData);
                soundBox.getVpas().add(vpaQr);
                soundBoxRepository.save(soundBox);

//                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success"), HttpStatus.OK);
//            else {
//                    return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Lead not found"), BAD_REQUEST);
//                }


//            }
//
//            return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Already Scanned"), BAD_REQUEST);


        } else {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
//        return null;
    }

//    @PutMapping("implementation-qr-device-scan-pick-multi/{serialNumber}/{id}")
//    public ResponseEntity<?> getQRDeviceScanpickMultiSoundBox(HttpServletRequest request, @PathVariable("serialNumber") String serialNumber, @PathVariable("id") Long id) throws URISyntaxException, IOException, ParseException {
//
//        String authtoken = request.getHeader("Authorization");
//        logger.info("authtoken==={}", authtoken);
//        User curuser = getSessionUser();
//        logger.info("Session User {}", curuser);
//        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
//        if (authtoken.equals(userauth.getToken())) {
//            LeadInformation leadInformation = leadInformationRepository.findLeadId(id);
////            DeviceDto deviceDto = new DeviceDto();
////            deviceDto.setDeviceName(leadInformation.getSqrDevice().getDeviceName());
//////        deviceDto.setDevice(marsDeviceDetails.getLeadInformation().getDevice());
//////        deviceDto.setUser(marsDeviceDetails.getLeadInformation().getCreatedBy());
////            deviceDto.setSerialNumber(serialNumber);
//////            deviceDto.setTid(marsData.getTid());
//////            deviceDto.setMid(marsData.getMid());
////            deviceDto.setApplicationNumber(leadInformation.getApplicationNumber());
////
////
////            JSONParser parser = new JSONParser();
////            HttpURLConnection conn = null;
//////    try {
//////        URL obj = new URL("https://uatapp.bijlipay.co.in:8181/staticApi/implementation-scan-pick");
////            URL obj = new URL(qrDeviceScanPickUpdateUrl);
//////            URL obj = new URL(marsUrl);
//////                    URL obj = new URL("https://marspp.bijlipay.co.in:9090/v1/merchant");
////            conn = (HttpURLConnection) obj.openConnection();
//////                        conn.setRequestProperty("NII", "110");
////            conn.setRequestProperty("Content-Type", "application/json");
////            conn.setDoOutput(true);
////            conn.setRequestMethod("POST");
////            // HTTP Connection Headers completed
////            logger.info("HTTP headers : ----- : {}", conn);
////            // Sending API request
////            logger.info("Getting Tickets Status1 {}", deviceDto);
////            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
////            JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(deviceDto));
////            out.write(CommonUtil.parseJson(reqData)); // sending the data
////            logger.info("Getting Tickets Status {}", reqData);
////            out.close(); // closing the output stream
////
////            int responsecode = conn.getResponseCode();
////            logger.info("The response code is {}", conn);
////            String message = conn.getResponseMessage();
////
////            logger.info("The response code is {}", responsecode);
////            logger.info("The response message is {}", message);
////
////            if (conn.getResponseCode() == 400 || conn.getResponseCode() == 404) {
////                return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Serial Number Not found"), BAD_REQUEST);
////            } else if (conn.getResponseCode() == 500) {
////                return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Connection failed"), INTERNAL_SERVER_ERROR);
////            } else if (conn.getResponseCode() == 200) {
//            leadInformation.setSoundboxSerialNumber(serialNumber);
//            leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(MARS_SOUNDBOX_DEVICE_STATUS_SCAN_PICKED));
//            leadInformation.setUpdatedAt(DateUtil.currentDate());
//            leadInformationRepository.save(leadInformation);
//            SoundBox soundBox = new SoundBox();
//            soundBox.setLeadInformation(leadInformation);
//            soundBox.setSerialNumber(serialNumber);
//            soundBoxRepository.save(soundBox);
//            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success"), HttpStatus.OK);
//
////            }
//
////            return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Already Allocated"), BAD_REQUEST);
//        } else {
//            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
//        }
//    }


    @PutMapping("implementation-qr-device-scan-pick/{serialNumber}/{id}")
    public ResponseEntity<?> getQRDeviceScanpick(HttpServletRequest request, @PathVariable("serialNumber") String serialNumber, @PathVariable("id") Long id) throws URISyntaxException, IOException, ParseException {

        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            LeadInformation leadInformation = leadInformationRepository.findLeadId(id);
            DeviceDto deviceDto = new DeviceDto();
            deviceDto.setDeviceName(leadInformation.getSqrDevice().getDeviceName());
//        deviceDto.setDevice(marsDeviceDetails.getLeadInformation().getDevice());
//        deviceDto.setUser(marsDeviceDetails.getLeadInformation().getCreatedBy());
            deviceDto.setSerialNumber(serialNumber);
//            deviceDto.setTid(marsData.getTid());
//            deviceDto.setMid(marsData.getMid());
            deviceDto.setApplicationNumber(leadInformation.getApplicationNumber());


            JSONParser parser = new JSONParser();
            HttpURLConnection conn = null;
//    try {
//        URL obj = new URL("https://uatapp.bijlipay.co.in:8181/staticApi/implementation-scan-pick");
            URL obj = new URL(qrDeviceScanPickUpdateUrl);
//            URL obj = new URL(marsUrl);
//                    URL obj = new URL("https://marspp.bijlipay.co.in:9090/v1/merchant");
            conn = (HttpURLConnection) obj.openConnection();
//                        conn.setRequestProperty("NII", "110");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            // HTTP Connection Headers completed
            logger.info("HTTP headers : ----- : {}", conn);
            // Sending API request
            logger.info("Getting Tickets Status1 {}", deviceDto);
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(deviceDto));
            out.write(CommonUtil.parseJson(reqData)); // sending the data
            logger.info("Getting Tickets Status {}", reqData);
            out.close(); // closing the output stream

            int responsecode = conn.getResponseCode();
            logger.info("The response code is {}", conn);
            String message = conn.getResponseMessage();

            logger.info("The response code is {}", responsecode);
            logger.info("The response message is {}", message);

            if (conn.getResponseCode() == 400 || conn.getResponseCode() == 404) {
                return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Serial Number Not found"), BAD_REQUEST);
            } else if (conn.getResponseCode() == 500) {
                return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Connection failed"), INTERNAL_SERVER_ERROR);
            } else if (conn.getResponseCode() == 200) {
                leadInformation.setSoundboxSerialNumber(serialNumber);
                leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(MARS_SOUNDBOX_DEVICE_STATUS_SCAN_PICKED));
                leadInformation.setUpdatedAt(DateUtil.currentDate());
                leadInformationRepository.save(leadInformation);
                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success"), HttpStatus.OK);

            }

            return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Already Allocated"), BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/qr-submit-to-mars-from-so-app/{id}")
    public ResponseEntity<?> qrSubmitToMars(HttpServletRequest request, @PathVariable("id") long id) throws Exception {

        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            LeadInformation leadInformation = leadInformationRepository.findLeadId(id);
            logger.info("----lleeaadd---{}", leadInformation);
            if (leadInformation != null && leadInformation.getLeadStatus().getLeadStatusId() == MARS_QR_STATUS_SCAN_PICKED || leadInformation.getLeadStatus().getLeadStatusId() == MARS_SOUNDBOX_DEVICE_STATUS_SCAN_PICKED || leadInformation.getLeadStatus().getLeadStatusId() == SQR_ONBOARDING_FAILED) {

                List<UploadedDocuments> uploadedDocumentsList = implementationService.findByLeadId(leadInformation);
                if (!uploadedDocumentsList.isEmpty() && leadInformation.getLeadSource().getId() != 7) {             //leadSource=7 is DCB in production
                    List<SignzyFetchAndVerification> panDetails = implementationService.getPanDetails(uploadedDocumentsList);
                    if (!panDetails.isEmpty()) {
                        leadInformation.setPanNumber(attributeEncryptor.decrypt(panDetails.get(0).getPanNumber()));
                        leadInformationRepository.save(leadInformation);
                        logger.info("pan Number -- : {}", attributeEncryptor.decrypt(panDetails.get(0).getPanNumber())); //need to remove
                    }
                }
                MarsOnboardingInformation marsOnboardingInformation = implementationService.setQRMarsDetails(leadInformation);

                logger.info("onboarding values inserted.................");
                MarsDto marsDto = new MarsDto();
                EDCMarsResponseDto edcMarsResponseDto = new EDCMarsResponseDto();
                StringBuilder strResp = new StringBuilder();
                JSONParser parser = new JSONParser();
                marsDto.setDevice("");
                marsDto.setSalesInformation((JSONObject) parser.parse(marsOnboardingInformation.getSalesInformation()));
                marsDto.setAdditionalInfo((JSONObject) parser.parse(marsOnboardingInformation.getAdditionalInfo()));
                marsDto.setCompanyInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getCompanyInformation())));
                marsDto.setBusinessInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBusinessInformation())));
                marsDto.setPartnerInformation((JSONArray) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getPartnerInformation())));

                PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto();
                paymentDetailsDto.setAdvanceRentCollected(String.valueOf(leadInformation.getAmountCollected()));
                paymentDetailsDto.setAdvanceRentMode("NEFT");
//        paymentDetails.setCardAcceptance("0");
//        paymentDetails.setCashAtPosEnabled("N");
//        paymentDetails.setCreditCardBlock("N");
                paymentDetailsDto.setDeviceOwnedBy("S");
                paymentDetailsDto.setDevicePrice(0.00);
                paymentDetailsDto.setEmiStartDate("17/10/2022");
                paymentDetailsDto.setEmiTenure("");
                paymentDetailsDto.setGracePeriod(0);
                paymentDetailsDto.setInstallationFee(String.valueOf(leadInformation.getSetUpFees()));
//        paymentDetails.setIntlCardAcceptance("N");
//        paymentDetails.setNetworkPreferred(String.valueOf(1));
                paymentDetailsDto.setNoOfMonthRentPaidInAdvance("0");
//        paymentDetails.setNumberOfTerminals(String.valueOf(1));
//        paymentDetails.setOmcEnabled(String.valueOf(false));
//        paymentDetails.setOtherCharges("");
                paymentDetailsDto.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
                String rentalMode = null;
                if (leadInformation.getSqrMarsRentalPlans() != null && leadInformation.getSqrMarsRentalPlans().getPlanFrequency().equals("M")) {
                    rentalMode = "SE";
                } else if (leadInformation.getSqrMarsRentalPlans() != null && leadInformation.getSqrMarsRentalPlans().getPlanFrequency().equals("L")) {
                    rentalMode = "RW";
                }

                logger.info("rentalMode------{}", rentalMode);
                if (leadInformation.getLeadSource().getLeadSourceName().contains("LS_DCB BANK _ONE TIME")) {
                    paymentDetailsDto.setRentalMode(rentalMode);
                    logger.info("rentalMode ---> {}", paymentDetailsDto.getRentalMode());
                } else {
                    paymentDetailsDto.setRentalMode("SE");
                }
                if (leadInformation.getSqrMarsRentalPlans() != null) {
                    paymentDetailsDto.setRentalType((Objects.equals(leadInformation.getSqrMarsRentalPlans().getPlanFrequency(), "M")) ? "R" : "A");
                } else {
                    paymentDetailsDto.setRentalType(" ");
                }
//                paymentDetailsDto.setRentalMode("SE");
                paymentDetailsDto.setRentalPlanCode(Integer.parseInt(leadInformation.getSqrMarsRentalPlans().getCode()));
//                paymentDetailsDto.setRentalType("A");
//        paymentDetails.setServiceProvider(String.valueOf(1));
//                if (leadInformation.isSoundBox()) {
//                    paymentDetailsDto.setTerminalModeCode(soundBox); //need to check
                paymentDetailsDto.setTerminalModeCode(leadInformation.getSqrDevice() != null ? leadInformation.getSqrDevice().getMarsDeviceCode() : ""); //need to check
//                } else {
////                    paymentDetailsDto.setTerminalModeCode(sticker);//need to check
//                    paymentDetailsDto.setTerminalModeCode(leadInformation.getStickerDevice() != null ? leadInformation.getStickerDevice().getMarsDeviceCode() : ""); //need to check
//                }
//                paymentDetailsDto.setTerminalModeCode("40"); //need to check
                logger.info("deviceModeCode ---> {}", paymentDetailsDto.getTerminalModeCode());
                paymentDetailsDto.setTerminalType("POS");
//        paymentDetails.setTipPercentage(leadInformation.getTipPercentage());
//        paymentDetails.setTotalAmountPaid("");
                paymentDetailsDto.setTotalEmiAmount("");
                VpaQr vpa = vpaQrRepository.findID(leadInformation.getLeadId());
                paymentDetailsDto.setVpa(Collections.singletonList(vpa.getVpa()));
                marsDto.setPaymentDetails(paymentDetailsDto);
                marsDto.setBankInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBankInformation())));
                MdrPlanDto mdrPlanDto = new MdrPlanDto();
                ObjectMapper mapper = new ObjectMapper();
//                logger.info("-----------SQRMDR----------{}", leadInformation.getSqrMarsMdrCharge());
                if (leadInformation.getSqrMarsMdrCharge() != null) {
                    mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getMasterPass(), MdrChargesDto.class));
                    mdrPlanDto.setDiners(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getDiners(), MdrChargesDto.class));
                    mdrPlanDto.setOnus(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getOnus(), MdrChargesDto.class));
                    mdrPlanDto.setStandardOrClassic(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getStandardOrClassic(), MdrChargesDto.class));
                    mdrPlanDto.setDomesticDebitUpTo2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getDomesticDebitUpTo2000(), MdrChargesDto.class));
                    mdrPlanDto.setDomesticDebitAbove2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getDomesticDebitAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getSuperPremiumOrSignature(), MdrChargesDto.class));
                    mdrPlanDto.setInternationalCreditCard(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getInternationalCreditCard(), MdrChargesDto.class));
                    mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getInternationalDebitCard(), MdrChargesDto.class));
                    mdrPlanDto.setCommercialOrCorporate(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getCommercialOrCorporate(), MdrChargesDto.class));
                    mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getPremiumOrPlatinum(), MdrChargesDto.class));
                    mdrPlanDto.setCashAtPos(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getCashAtPos(), MdrChargesDto.class));
                    mdrPlanDto.setCode(Integer.parseInt(leadInformation.getSqrMarsMdrCharge().getCode()));
                    mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getUpiUpto2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getUpiAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiCreditAbove2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getUpiCreditAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiCreditUpto2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getUpiCreditUpto2000(), MdrChargesDto.class));
                }
//                if (leadInformation.isQrFlag()) {
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    String str = leadInformation.getSqrMarsMdrCharge().getDomesticDebitUpTo2000();
//                    MdrChargesDto DebitLess = objectMapper.readValue(str, MdrChargesDto.class);
//                    DebitLess.setPercentage(leadInformation.getSqrAppliedDebitLessthanAmount());
//
//                    String str1 = leadInformation.getSqrMarsMdrCharge().getDomesticDebitAbove2000();
//                    MdrChargesDto DebitAbove = objectMapper.readValue(str1, MdrChargesDto.class);
//                    DebitAbove.setPercentage(leadInformation.getSqrAppliedDebitGreaterthanAmount());
//
//                    String str2 = leadInformation.getSqrMarsMdrCharge().getUpiUpto2000();
//                    MdrChargesDto creditLess = objectMapper.readValue(str2, MdrChargesDto.class);
//                    creditLess.setPercentage(leadInformation.getSqrAppliedCreditLessthanAmount());
//
//
//                    String str3 = leadInformation.getSqrMarsMdrCharge().getUpiAbove2000();
//                    MdrChargesDto creditAbove = objectMapper.readValue(str3, MdrChargesDto.class);
//                    creditLess.setPercentage(leadInformation.getSqrAppliedCreditGreaterthanAmount());
//
//                    mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getMasterPass(), MdrChargesDto.class));
//                    mdrPlanDto.setDiners(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getDiners(), MdrChargesDto.class));
//                    mdrPlanDto.setOnus(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getOnus(), MdrChargesDto.class));
//                    mdrPlanDto.setStandardOrClassic(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getStandardOrClassic(), MdrChargesDto.class));
//                    mdrPlanDto.setDomesticDebitUpTo2000(DebitLess);
//                    mdrPlanDto.setDomesticDebitAbove2000(DebitAbove);
//                    mdrPlanDto.setUpiUpto2000(creditLess);
//                    mdrPlanDto.setUpiAbove2000(creditAbove);
//                    mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getSuperPremiumOrSignature(), MdrChargesDto.class));
//                    mdrPlanDto.setInternationalCreditCard(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getInternationalCreditCard(), MdrChargesDto.class));
//                    mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getInternationalDebitCard(), MdrChargesDto.class));
//                    mdrPlanDto.setCommercialOrCorporate(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getCommercialOrCorporate(), MdrChargesDto.class));
//                    mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getPremiumOrPlatinum(), MdrChargesDto.class));
//                    mdrPlanDto.setCashAtPos(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getCashAtPos(), MdrChargesDto.class));
//                    mdrPlanDto.setCode(Integer.parseInt(leadInformation.getSqrMarsMdrCharge().getCode()));
//
//                }

                marsDto.setMdrPlan(mdrPlanDto);
                marsDto.setRemarks("");
                marsDto.setRevParameters((JSONObject) parser.parse(marsOnboardingInformation.getPaymentDetails()));

                EdcResponseDto edcData = null;
                logger.info(" data 2 ... {}", marsDto);
                HashMap<String, Object> dummyDto = new HashMap<>();
                dummyDto.put("merchant", marsDto);
                JSONObject jsonObject = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                logger.info(" JSON OBJ : ---- : {}", jsonObject);
                logger.info(" dummy -- : {}", CommonUtil.parseJson(jsonObject));
                HttpURLConnection conn = null;
                try {
                    URL obj = new URL(marsVpaUrl);

//            URL obj = new URL(marsUrl);
//                    URL obj = new URL("https://marspp.bijlipay.co.in:9090/v1/merchant");
                    conn = (HttpURLConnection) obj.openConnection();
                    conn.setRequestProperty("NII", "110");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    // HTTP Connection Headers completed
                    logger.info("HTTP headers : ----- : {}", conn);
                    // Sending API request
                    logger.info("Getting Tickets Status {}", dummyDto);
                    OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                    JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                    out.write(CommonUtil.parseJson(reqData)); // sending the data
                    logger.info("Getting Tickets Status {}", reqData);
                    out.close(); // closing the output stream
                    int responsecode = conn.getResponseCode();
                    logger.info("The response code is {}", conn);
                    String message = conn.getResponseMessage();
                    logger.info("The response code is {}", responsecode);
                    logger.info("The response message is {}", message);
                    edcMarsResponseDto.setCode(conn.getResponseCode());
                } catch (Exception e) {
                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "SOMETHING WENT WRONG", e.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
                }

                if (conn.getResponseCode() == 201) {
                    logger.info("Processing the request response -------------------------");
                    // Inputstream parse it as response
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    logger.info("response data {}", conn.getInputStream());
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        strResp.append(inputLine);
                        logger.info("responseee ----> {}", strResp);
                    }
                    in.close();
                    edcMarsResponseDto.setStatus(HttpStatus.valueOf(conn.getResponseCode()));
//                edcMarsResponseDto.setData(edcData);
                    String data = strResp.toString();
                    Gson gson = new Gson();
                    edcData = gson.fromJson(data, EdcResponseDto.class);
                    if (edcData.getMerchantId() != null && edcData.getTerminalId() != null) {
                        edcData.setRemarks("Onboard-Success");

                    } else if (Objects.equals(edcData.getResponseCode(), "101")) {
                        logger.info("responseee 101 ----> {}", edcData.getResponseCode());
                        edcData.setRemarks("Sqr-Failed");
                        logger.info("-leadStatus ----> {}", leadStatusRepository.fetchLeadStatusById(SQR_ONBOARDING_FAILED));
                        leadInformation.setActive(false);
                        leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(SQR_ONBOARDING_FAILED));
                        leadInformationRepository.save(leadInformation);
                        return new ResponseEntity<>(new ApiResponse(CONFLICT, "Failure", edcData), CONFLICT);

                    } else {
                        edcData.setRemarks("Sqr-Failed");
                        logger.info("------------------------else---------------{}", edcData.getRemarks());
                        leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(SQR_ONBOARDING_FAILED));
                        edcData.setFlag(false);
                        leadInformation.setActive(false);
                        leadInformationRepository.save(leadInformation);
                        return new ResponseEntity<>(new ApiResponse(CONFLICT, "Failure", edcData), CONFLICT);
                    }
                    edcMarsResponseDto.setData(edcData);
                } else if (conn.getResponseCode() == 500) {
                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical issue ....Contact Bijlipay"), INTERNAL_SERVER_ERROR);
                } else if (conn.getResponseCode() == 400) {
                    leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(SQR_ONBOARDING_FAILED));
                    leadInformation.setSwitchRejectDate(DateUtil.currentDate());
                    leadInformation.setActive(false);
                    leadInformationRepository.save(leadInformation);
                    return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Technical issue ....Contact Bijlipay", edcMarsResponseDto), BAD_REQUEST);
                } else if (conn.getResponseCode() == 0) {
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "Connection timeout ..... Try again"), CONFLICT);
                }

                logger.info("eeeeeee ----> {}", strResp);

                if (edcMarsResponseDto.getData().getTerminalId() != null && edcMarsResponseDto.getData().getMerchantId() != null) {
                    leadInformation.setActive(true);
                    leadInformationRepository.save(leadInformation);
                    MarsDeviceDetails marsDeviceDetails1 = implementationService.saveDatasToMarsAndLeadTable(implementationService.setValuesToDto(edcMarsResponseDto));
                    List<MarsDeviceDetails> marsDeviceDetails = marsDeviceDetailsRepository.findLeadId(marsDeviceDetails1.getLeadInformation());

                    marsDeviceDetails.forEach(l -> {
                        l.setScheduleDate(DateUtil.currentDate());
                        marsDeviceDetailsRepository.save(l);
                    });
                    CompletableFuture.runAsync(() -> {
                        logger.info("-Webbackend triggered-");
                        implementationService.updateLeadAndMarsDataFromDigiOnboardSqr(marsDeviceDetails1);
                    });
                    logger.info("Schedule date set --");
                    //TMS onboarding
                    if (leadInformation.isQrFlag() && leadInformation.isSoundBox()) {
                        CompletableFuture.runAsync(() -> {
                            logger.info("--tmsTriggered--");
                            implementationService.tmsOnboard(marsDeviceDetails1, implementationService.setValuesToDto(edcMarsResponseDto));
                        });
                    }

                }
                return new ResponseEntity<>(new ApiResponse(OK, "Success", edcData), OK);
            }
            return new ResponseEntity<>(new ApiResponse(CONFLICT, "LeadNotFount"), CONFLICT);
        }
        return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/tmsOnboarding/{leadId}")
    public ResponseEntity<?> tmsOnboard(@PathVariable("leadId") long leadId) {
        VpaQr vpaQr = vpaQrRepository.findID(leadId);
        MarsDeviceDetails marsDeviceDetails = marsDeviceDetailsRepository.findId1(leadId);
        logger.info("mars--{}", marsDeviceDetails);
        LeadInformation leadInformation = leadInformationRepository.findLead(leadId);
        SavingToTable savingToTable = new SavingToTable();
        savingToTable.setVpa(vpaQr.getVpa());
        logger.info("--tmsTriggered--");
        Pair<Integer, Object> lead = implementationService.tmsOnboardingretry(savingToTable, marsDeviceDetails);
        if (lead.getFirst() == 1) {
            leadInformation.setTmsFlag(true);
            leadInformationRepository.save(leadInformation);
            return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
        } else {
            leadInformation.setTmsFlag(false);
            leadInformationRepository.save(leadInformation);
            return new ResponseEntity<>(new ApiResponse(CONFLICT, "failed"), CONFLICT);
        }

    }

    @PostMapping("/tms-addOn-retrigger/{leadId}")
    public ResponseEntity<?> tmsRetriggeredAddOn(@PathVariable("leadId") long leadId) {
        List<String> vpaQrs = vpaQrRepository.findID1(leadId);
        String vpa = vpaQrs.get(0);
        SavingToTable savingToTable = new SavingToTable();
        savingToTable.setVpa(vpa);
        MarsDeviceDetails marsDeviceDetails = marsDeviceDetailsRepository.findByList(leadId);
        Pair<Integer, String> pair = implementationService.tmsOnboardAddOnretriggered(marsDeviceDetails, savingToTable);
        if (pair.getFirst() == 1) {
            return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
        }
        return new ResponseEntity<>(new ApiResponse(CONFLICT, "failed"), CONFLICT);
    }

    @PostMapping("/tms-addOn-retrigger-Q161/{leadId}")
    public ResponseEntity<?> tmsRetriggeredAddOnQ161(@PathVariable("leadId") long leadId) {
        List<String> vpaQrs = vpaQrRepository.findID1(leadId);
        String vpa = vpaQrs.get(0);
        SavingToTable savingToTable = new SavingToTable();
        savingToTable.setVpa(vpa);
        MarsDeviceDetails marsDeviceDetails = marsDeviceDetailsRepository.findByList(leadId);
        MarsDeviceDetails marsDeviceDetailsQr = marsDeviceDetailsRepository.findLeadQr(leadId).get(0);
        implementationService.tmsOnboardQ161(marsDeviceDetails, marsDeviceDetailsQr, savingToTable);

        return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
    }

    @GetMapping("/qr-scanned-list/{id}")
    public ResponseEntity<?> getScannedList(@PathVariable("id") long id) throws URISyntaxException {
        LeadInformation leadInformation = leadInformationRepository.findLeadId(id);
        List<VpaQr> vpaQr1 = null;
        if (leadInformation != null) {

            vpaQr1 = vpaQrRepository.getScannedList(id);
        }
//        leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(MARS_QR_STATUS_SCAN_PICKED));
//        leadInformationRepository.save(leadInformation);

        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success", vpaQr1), HttpStatus.OK);
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<?> excelUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    logger.info("------file--");
                    FileTransfer fileTransfer = new FileTransfer();
                    if (row.getRowNum() == 0) {
                        continue;
                    }

                    if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                        fileTransfer.setMbNo((long) row.getCell(0).getNumericCellValue());
                    } else {
                        return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "invalid Mobile Number"), BAD_REQUEST);
                    }
                    if (row.getCell(1).getCellType() == CellType.STRING) {
                        fileTransfer.setMailId(row.getCell(1).getStringCellValue());
                    } else {
                        return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "invalid mailId "), BAD_REQUEST);
                    }
                    fileTransfer.setName(row.getCell(2).getStringCellValue());
                    Cell cell = row.getCell(3);
                    DataFormatter dataFormatter = new DataFormatter();
                    String panNumber = null;
                    if (cell != null) {
                        panNumber = dataFormatter.formatCellValue(cell);
                    }
                    fileTransfer.setPanNumber(panNumber);
                    Cell cell1 = row.getCell(4);
                    logger.info("-----AccountNo--{}", cell1.getCellType());
                    long Account = cell1.getCellType() == CellType.NUMERIC ? (long) cell1.getNumericCellValue() : 0;
                    fileTransfer.setAccountNumber(Account);
                    Cell cell2 = row.getCell(5);
                    String ContactName = (cell2 != null && cell2.getCellType() == CellType.STRING) ? String.valueOf(cell2.getStringCellValue()) : "";
                    fileTransfer.setContactName(ContactName);
                    Cell cell3 = row.getCell(6);
                    long mcc = (cell3 != null && cell3.getCellType() == CellType.NUMERIC)
                            ? (long) cell3.getNumericCellValue()
                            : 0;
                    fileTransfer.setProhibitedMCC(mcc);

                    fileTransferRepository.save(fileTransfer);
                }
                return new ResponseEntity<>(new ApiResponse(OK, "sucess"), OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "SOMETHING WENT WRONG"), INTERNAL_SERVER_ERROR);

            }
        }
        return new ResponseEntity<>(new ApiResponse(UNAUTHORIZED, "unAuthorized"), UNAUTHORIZED);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download() throws IOException {
        List<FileTransfer> users = fileTransferRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream("C:\\Users\\jaikumar.s\\Desktop\\download.xlsx")) {
            Sheet sheet = workbook.createSheet("Users");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("MbNo");
            headerRow.createCell(1).setCellValue("Email");
            headerRow.createCell(2).setCellValue("name");
            headerRow.createCell(3).setCellValue("PanNo");
            headerRow.createCell(4).setCellValue("AccNo");
            headerRow.createCell(5).setCellValue("ContactName");
            headerRow.createCell(6).setCellValue("Mcc");
            int rowNum = 1;
            for (FileTransfer user : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getMbNo());
                row.createCell(1).setCellValue(user.getMailId());
                row.createCell(2).setCellValue(user.getName());
                row.createCell(3).setCellValue(user.getPanNumber());
                row.createCell(4).setCellValue(user.getAccountNumber());
                row.createCell(5).setCellValue(user.getContactName());
                row.createCell(6).setCellValue(user.getProhibitedMCC());
            }
            workbook.write(fileOut);
            String files = "C:\\Users\\jaikumar.s\\Desktop\\download.xlsx";

            Path file = Paths.get(files);
            System.out.println("Resolved Path: " + file.toAbsolutePath());
            Resource resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                System.out.println("File not found or not readable.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=users.xlsx");
            headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength()) // Set content length
                    .body(resource);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/manualUpload")
    public ResponseEntity<?> manualExcelUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            List<LeadInformation> leadInformations = new ArrayList<>();
            int count = 2;
            try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    logger.info("------file--");
                    LeadInformation leadInformation = new LeadInformation();
                    if (row.getRowNum() == 0) {
                        continue;
                    }
                    leadInformation.setBranch(getCellType(row.getCell(0)));
                    leadInformation.setBank(getCellType(row.getCell(1)));
                    leadInformation.setAccountNumber(getCellType(row.getCell(2)));
                    leadInformation.setIfscCode(getCellType(row.getCell(3)));
                    if (row.getCell(4).getCellType() == CellType.NUMERIC) {
                        leadInformation.setContactNumber(String.valueOf(row.getCell(4).getNumericCellValue()));
                    } else if (row.getCell(4).getCellType() == CellType.STRING) {
                        leadInformation.setContactNumber(row.getCell(4).getStringCellValue());
                    }
//                    leadInformation.setContactNumber(row.getCell(4).getStringCellValue());
                    String constitution = getCellType(row.getCell(5));
                    if (constitution != null) {
                        if (getCellType(row.getCell(5)).contains("individual")) {
                            leadInformation.setConstitution("IN");
                        } else {
                            leadInformation.setConstitution("SP");
                        }
                    }
                    leadInformation.setContactEmail(getCellType(row.getCell(6)));
                    leadInformation.setContactName(getCellType(row.getCell(7)));
                    logger.info("----pin--{}", row.getCell(8).getCellType());
                    leadInformation.setPincode(Long.parseLong(getCellType(row.getCell(8))));
                    leadInformation.setDbaName(getCellType(row.getCell(9)));
                    MerchantCategoryCode mcc = merchantCategoryCodeRepository.findByCode(getCellType(row.getCell(10)));
                    leadInformation.setMcc((mcc));
                    leadInformation.setContactEmail(getCellType(row.getCell(11)));
                    leadInformation.setLegalName(getCellType(row.getCell(12)));
                    leadInformation.setContactAddress(getCellType(row.getCell(13)));
                    leadInformation.setCity(getCellType(row.getCell(14)));
                    //row-15 -contactno
                    if (row.getCell(15).getCellType() == CellType.NUMERIC) {
                        leadInformation.setPanNumber(String.valueOf(row.getCell(15).getNumericCellValue()));
                    } else if (row.getCell(15).getCellType() == CellType.STRING) {
                        leadInformation.setPanNumber(row.getCell(15).getStringCellValue());
                    }
                    leadInformation.setMerchantStateName(getCellType((row.getCell(16))));
                    Cell cell = row.getCell(17);
                    if (cell != null && cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.STRING) {
                        Date date = cell.getDateCellValue();
                        leadInformation.setMemberSince(date);
                    } else {
                        leadInformation.setMemberSince(null);
                    }
                    leadInformation.setWeekDayTimingStart(String.valueOf(getCellType(row.getCell(18))));
//                    if (row.getCell(19).getCellType() == CellType.NUMERIC) {
//                        leadInformation.setAvgBillAmount(Double.parseDouble(getCellType(row.getCell(19))));
//                    } else if (row.getCell(19).getCellType() == CellType.STRING) {
//                        leadInformation.setAvgBillAmount(Double.parseDouble(getCellType(row.getCell(19))));
//                    }
//                    else {
//                        leadInformation.setAvgBillAmount(0.00);
//                    }
                    leadInformation.setAvgBillAmount(Double.parseDouble((getCellType(row.getCell(19))) != null ? getCellType(row.getCell(19)) : String.valueOf(0.00)));
                    leadInformation.setTurnOverLastFinancialYear(getCellType(row.getCell(20)) == null ? null : getCellType(row.getCell(20)));
                    leadInformation.setWeekendTimingEnd(getCellType(row.getCell(21)));
                    leadInformation.setWeekdayTimingEnd(getCellType(row.getCell(22)));
                    leadInformation.setWeekdayTimingEnd(getCellType(row.getCell(23)));
                    leadInformation.setWeekendTimingStart(getCellType(row.getCell(24)));
                    leadInformation.setState(getCellType(row.getCell(25)));
                    Cell cell1 = row.getCell(26);
                    if (cell1 != null && cell1.getCellType() == CellType.NUMERIC || cell1.getCellType() == CellType.STRING) {
                        Date date = cell1.getDateCellValue();
                        leadInformation.setOwnerDOB(date);
                    }
                    leadInformation.setMerchantDistrictName(getCellType(row.getCell(27)));
                    leadInformation.setMerchantSubDistrictName(getCellType(row.getCell(28)));
                    Region region = regionRepository.getRegion(getCellType(row.getCell(29)));
                    leadInformation.setRegion(region);
                    leadInformation.setRecurringFees(Long.parseLong(getCellType(row.getCell(30))));
                    leadInformation.setSetUpFees(Double.parseDouble(getCellType(row.getCell(31))));
                    User user = userRepository.findByEmail(getCellType(row.getCell(32)));
                    leadInformation.setSettlement("A");
                    leadInformation.setCreatedBy(user);
                    leadInformation.setActive(true);
                    leadInformation.setTmsFlag(true);
                    leadInformation.setLeadNumber(String.valueOf(new Date().getTime()));
                    leadInformation.setQrFlag(true);
                    leadInformation.setLeadSource(leadSourceRepo.findLeadSourceById(6));
                    leadInformation.setMerchantName(getCellType(row.getCell(12)));
                    leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(1));
                    int id = 0;
                    logger.info("-----constitution-{}", getCellType(row.getCell(5)));
                    String constitutions = getCellType(row.getCell(5));
                    if (constitutions == null) {
                        leadInformation.setMerchantType(null);
                    } else {
                        if (getCellType(row.getCell(5)).contains("individual")) {
                            id = 1;
                        } else {
                            id = 2;
                        }
                        leadInformation.setMerchantType(merchantTypeRepository.get(id));
                    }
                    leadInformation.setCategoryType(2);
                    leadInformation.setSqrDevice(deviceRepository.fetchByDeviceName(row.getCell(33).getStringCellValue()));
                    long ids = 8;
                    int leadSource = leadInformation.getLeadSource().getId();
                    if (leadInformation.getSqrDevice().getDeviceName().contains("Q181-SOUND BOX")) {
//                        leadInformation.setSqrMarsMdrCharge(marsMdrChargesRepository.findByID(116));
                        leadInformation.setSqrPlan(marsRentalPlansRepository.getPlanById(ids, 4, leadSource));
                    } else {
//                        leadInformation.setSqrMarsMdrCharge(marsMdrChargesRepository.findByID(115));
                        leadInformation.setSqrPlan(marsRentalPlansRepository.getPlanById(ids, 3, leadSource));
                    }
//                    leadInformation.setSqrMarsMdrCharge(marsMdrChargesRepository.findByID(27));
//                    logger.info("----sqrMars--{}",leadInformation.getSqrMarsMdrCharge().toString());
//                    long ids=8;
//                   int leadSource=leadInformation.getLeadSource().getId();
//                    leadInformation.setSqrPlan(marsRentalPlansRepository.getPlanById(ids,3,leadSource));
                    leadInformation.setSoundBox(leadInformation.getSqrDevice().getDeviceName().contains("Q181-SOUND BOX"));
                    leadInformation.setQrCount(1);
//                    leadInformation.setMerchantDetailsCompleteDate(DateUtil.currentDate());
//                    leadInformation.setDocumentDetailsCompleteDate(DateUtil.currentDate());
//                    leadInformation.setMdrDetailsCompleteDate(DateUtil.currentDate());
//                    leadInformation.setBizDetailsCompleteDate(DateUtil.currentDate());
                    leadInformation.setPaymentDetailsCompleteDate(DateUtil.currentDate());
                    MarsRentalPlans marsRentalPlans = marsRentalPlansRepository.getPlanByIds(ids, 3, leadSource);
                    if (leadInformation.getSqrDevice().getDeviceName().contains("Q181-SOUND BOX")) {
                        leadInformation.setSqrRecurringFees(marsRentalPlans.getMonthlyFees());
                        leadInformation.setSqrSetUpFees(Double.parseDouble(marsRentalPlans.getSetup_fees()));
                    } else {
                        leadInformation.setSqrRecurringFees(0);
                    }
                    leadInformation.setGstNumber(getCellType(row.getCell(36)));
                    Long kit = 1L;
                    leadInformation.setKitType(kitTypeRepo.find(kit));
                    leadInformation.setVasInstanceMapping("[]");
                    leadInformation.setSettlementMode(1);
                    String language1 = getCellType(row.getCell(34));
                    logger.info("-------Lang--{}", language1);
                    if (Objects.equals(language1, null)) {
                        leadInformation.setLanguageSoundbox(null);
                    } else {
//                    String language = "%" + language1 + "%";
//                    logger.info("-------Lang--{}",language);
                        List<LanguageSoundbox> languageSoundbox = languageSoundboxRepository.findSOundId(language1);
                        leadInformation.setLanguageSoundbox(languageSoundbox.get(0));
                        logger.info("Language---{}", leadInformation.getLanguageSoundbox());
                    }

                    leadInformations.add(leadInformation);
                    logger.info("-----row---{}-----Added----{}---", count, leadInformation);
                    count++;

                }
                leadInformationRepository.saveAll(leadInformations);
                return new ResponseEntity<>(new ApiResponse(OK, "success", leadInformations), OK);

            }
//
        }
        return new ResponseEntity<>(new ApiResponse(UNAUTHORIZED, "unAuthorized"), UNAUTHORIZED);
    }

    private String getCellType(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.STRING) {
            logger.info("value --------- : {}", cell.getStringCellValue());
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                return sdf.format(cell.getDateCellValue());
            } else {
                return String.valueOf(cell.getNumericCellValue());
            }
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.BLANK) {
            return null;
        }
        return null;
    }

    @PostMapping("/update/{leadId}/{deviceId}/{soundId}")
    private ResponseEntity<?> updateDcp(@PathVariable("leadId") long leadId, @PathVariable("deviceId") long deviceId, @PathVariable(value = "soundId", required = false) Long soundId, HttpServletRequest request) {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            LeadInformation leadInformation = leadInformationRepository.findLead(leadId);
            leadInformation.setSqrDevice(deviceRepository.fetchDeviceById(deviceId));
            logger.info("-------devvice---{}", leadInformation.getSqrDevice());
            long ids = 8;
            int leadSource = leadInformation.getLeadSource().getId();
            if (leadInformation.getSqrDevice().getDeviceName().equalsIgnoreCase("Q181-SOUND BOX")) {
                leadInformation.setSoundBox(true);
//                leadInformation.setSqrMarsMdrCharge(marsMdrChargesRepository.findByID(27));
                logger.info("------soundBox---");
                LanguageSoundbox languageSoundbox = languageSoundboxRepository.getById1(soundId);
                if (languageSoundbox != null) {
                    leadInformation.setLanguageSoundbox(languageSoundbox);
                }
                KitType kitType = kitTypeRepo.find(1L);
                leadInformation.setKitType(kitType);
                leadInformation.setSqrPlan(marsRentalPlansRepository.getPlanById(ids, 4, leadSource));
            } else {
//                leadInformation.setSqrMarsMdrCharge(marsMdrChargesRepository.findByID(26));
                logger.info("------sticker---");
                leadInformation.setSoundBox(false);
                leadInformation.setSqrPlan(marsRentalPlansRepository.getPlanById(ids, 3, leadSource));
            }
            LeadInformation leadInformation1 = leadInformationRepository.save(leadInformation);
            return new ResponseEntity<>(new ApiResponse(OK, "success", leadInformation1), OK);
        }
        return new ResponseEntity<>(new ApiResponse(UNAUTHORIZED, "unathorized"), UNAUTHORIZED);
    }

    @GetMapping("/riskVerify/{leadId}/{mcc}")
    private ResponseEntity<?> riskVerify(HttpServletRequest request, @PathVariable("leadId") long leadId, @PathVariable("mcc") String mcc) {
        logger.info("leadId---------:{}", leadId);
        LeadInformation leadInformation1 = leadInformationService.findLead(leadId);
        if (leadInformation1 != null) {
            int count = fileTransferRepository.findByCount(Long.parseLong(leadInformation1.getContactNumber()));
            int count1 = fileTransferRepository.findByCount1(leadInformation1.getContactEmail());
            if (count > 0) {
                return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "MobileNo already present in Risk"), BAD_REQUEST);
            } else if (count1 > 0) {
                return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "MailId already present in Risk"), BAD_REQUEST);
            }
            //riskMcc Verification
            List<String> riskMccNpcis = implementationService.getMccList();
            for (String mcc1 : riskMccNpcis) {
                if (mcc.equalsIgnoreCase(mcc1)) {
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "mcc present in Risk"), CONFLICT);
                }
            }
            return new ResponseEntity<>(new ApiResponse(OK, "riskSucess"), OK);
        }
        return new ResponseEntity<>(new ApiResponse(CONFLICT, "lead Not Found"), CONFLICT);

    }

    @PutMapping("/qr-submit-to-mars-from-so-app-addon/{id}")
    public ResponseEntity<?> qrSubmitToMars1(HttpServletRequest request, @PathVariable("id") long id) throws Exception {

        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            LeadInformation leadInformation = leadInformationRepository.findLeadId(id);
            InternalSubvention internalSubvention1 = !internalSubventionRepository.getDetailByLeadIds(leadInformation.getLeadId()).isEmpty() ? internalSubventionRepository.getDetailByLeadIds(leadInformation.getLeadId()).get(0) : null;
            logger.info("----lleeaadd---{}", leadInformation);
            if (leadInformation != null && leadInformation.getLeadStatus().getLeadStatusId() == MARS_QR_STATUS_SCAN_PICKED || leadInformation.getLeadStatus().getLeadStatusId() == MARS_SOUNDBOX_DEVICE_STATUS_SCAN_PICKED || leadInformation.getLeadStatus().getLeadStatusId() == SQR_ONBOARDING_FAILED || leadInformation.getLeadStatus().getLeadStatusId() == HOST_API_FAILED) {

                List<UploadedDocuments> uploadedDocumentsList = implementationService.findByLeadId(leadInformation);
                if (!uploadedDocumentsList.isEmpty() && leadInformation.getLeadSource().getId() != 7) {             //leadSource=7 is DCB
                    List<SignzyFetchAndVerification> panDetails = implementationService.getPanDetails(uploadedDocumentsList);
                    if (!panDetails.isEmpty()) {
                        leadInformation.setPanNumber(attributeEncryptor.decrypt(panDetails.get(0).getPanNumber()));
                        leadInformationRepository.save(leadInformation);
                        logger.info("pan Number -- : {}", attributeEncryptor.decrypt(panDetails.get(0).getPanNumber())); //need to remove
                    }
                }
                MarsOnboardingInformation marsOnboardingInformation = implementationService.setQRMarsDetails(leadInformation);

                logger.info("onboarding values inserted.................");
                MarsDto marsDto = new MarsDto();
                EDCMarsResponseDto edcMarsResponseDto = new EDCMarsResponseDto();
                StringBuilder strResp = new StringBuilder();
                JSONParser parser = new JSONParser();
                marsDto.setDevice("");
                marsDto.setSalesInformation((JSONObject) parser.parse(marsOnboardingInformation.getSalesInformation()));
                marsDto.setAdditionalInfo((JSONObject) parser.parse(marsOnboardingInformation.getAdditionalInfo()));
                marsDto.setCompanyInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getCompanyInformation())));
                marsDto.setBusinessInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBusinessInformation())));
                marsDto.setPartnerInformation((JSONArray) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getPartnerInformation())));

//                PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto();
//                paymentDetailsDto.setAdvanceRentCollected(String.valueOf(leadInformation.getAmountCollected()));
//                paymentDetailsDto.setAdvanceRentMode("NEFT");
//                paymentDetailsDto.setDeviceOwnedBy("S");
//                paymentDetailsDto.setDevicePrice(0.00);
//                paymentDetailsDto.setEmiStartDate("17/10/2022");
//                paymentDetailsDto.setEmiTenure("");
//                paymentDetailsDto.setGracePeriod(0);
//                paymentDetailsDto.setInstallationFee(String.valueOf(leadInformation.getSetUpFees()));
//                paymentDetailsDto.setNoOfMonthRentPaidInAdvance("0");
//                paymentDetailsDto.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
//                String rentalMode = null;
//                if (leadInformation.getSqrMarsRentalPlans() != null && leadInformation.getSqrMarsRentalPlans().getPlanFrequency().equals("M")) {
//                    rentalMode = "SE";
//                } else if (leadInformation.getSqrMarsRentalPlans() != null && leadInformation.getSqrMarsRentalPlans().getPlanFrequency().equals("L")) {
//                    rentalMode = "RW";
//                }
//
//                logger.info("rentalMode------{}", rentalMode);
//                if (leadInformation.getLeadSource().getLeadSourceName().contains("LS_DCB BANK _ONE TIME")) {
//                    paymentDetailsDto.setRentalMode(rentalMode);
//                    logger.info("rentalMode ---> {}", paymentDetailsDto.getRentalMode());
//                } else {
//                    paymentDetailsDto.setRentalMode("SE");
//                }
//                if (leadInformation.getSqrMarsRentalPlans() != null) {
//                    paymentDetailsDto.setRentalType((Objects.equals(leadInformation.getSqrMarsRentalPlans().getPlanFrequency(), "M")) ? "R" : "A");
//                } else {
//                    paymentDetailsDto.setRentalType(" ");
//                }
//                paymentDetailsDto.setRentalPlanCode(Integer.parseInt(leadInformation.getSqrMarsRentalPlans().getCode()));
//                if (leadInformation.isSoundBox()) {
//                    paymentDetailsDto.setTerminalModeCode(soundBox); //need to check
//                } else {
//                    paymentDetailsDto.setTerminalModeCode(sticker); //need to check
//                }
//                logger.info("deviceModeCode ---> {}", paymentDetailsDto.getTerminalModeCode());
//                paymentDetailsDto.setTerminalType("POS");
//                paymentDetailsDto.setTotalEmiAmount("");
//                List<String> vpa = vpaQrRepository.findID1(leadInformation.getLeadId());
//                paymentDetailsDto.setVpa(vpa);
//                marsDto.setPaymentDetails(paymentDetailsDto);
                List<String> vpas = new ArrayList<>();
                if (leadInformation.isGeeNeePos()) {
                    List<String> vpa1 = counterManagementRepository.findByDevice("GeeNee POS+ Pro", leadInformation.getLeadId());
                    vpa1.forEach(l -> {
                        org.json.JSONArray jsonArray = new org.json.JSONArray(l);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            logger.info("vpas for GeeNee----: {}", jsonArray.getString(i));
                            vpas.add(jsonArray.getString(i));
                        }
                    });
                } else {
                    List<String> vpa1 = counterManagementRepository.findByDevice("Sticker", leadInformation.getLeadId());
                    vpa1.forEach(l -> {
                        org.json.JSONArray jsonArray = new org.json.JSONArray(l);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            logger.info("vpas for Sticker----: {}", jsonArray.getString(i));
                            vpas.add(jsonArray.getString(i));
                        }
                    });
                }
                List<TerminalDetail> terminalDetails = new ArrayList<>();
                if (!vpas.isEmpty()) {
                    TerminalDetail terminalDetail = new TerminalDetail();
                    terminalDetail.setAdvanceRentCollected(String.valueOf(leadInformation.getAmountCollected()));
                    terminalDetail.setAdvanceRentMode("NEFT");
                    terminalDetail.setDeviceOwnedBy("S");
                    terminalDetail.setDevicePrice(0.00);
                    terminalDetail.setEmiStartDate("17/10/2022");
                    terminalDetail.setEmiTenure("");
                    terminalDetail.setGracePeriod(0);
//                    terminalDetail.setInstallationFee(String.valueOf(leadInformation.getSetUpFees()));
                    terminalDetail.setNoOfMonthRentPaidInAdvance("0");
//                    terminalDetail.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
                    terminalDetail.setRentalMode("SE");
                    terminalDetail.setIntlCardAcceptance(leadInformation.getIcaEnabled() == 1 ? "Y" : "N");
                    // ----------------------> GeeNee POS
                    if (internalSubvention1 != null && internalSubvention1.isPos() && internalSubvention1.isPosRental() && leadInformation.isGeeNeePos()) {
                        logger.info("Entered Rental Internal Subvention");
                        terminalDetail.setInstallationFee(internalSubvention1.getPosApprovedSetupFee());
                        terminalDetail.setRecurringFees(String.valueOf(internalSubvention1.getPosApprovedRecurringFee()));
                        terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getMarsRentalPlans().getCode()));
                    } else if (internalSubvention1 != null && internalSubvention1.isSticker() && internalSubvention1.isQrRental()) {
                        terminalDetail.setInstallationFee(internalSubvention1.getStickerApprovedSetupFee());
                        terminalDetail.setRecurringFees(String.valueOf(internalSubvention1.getStickerApprovedRecurringFee()));
                        terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getStickerMarsRentalPlans().getCode()));
                    } else if (leadInformation.getBankSubventionQr() != null && leadInformation.getBankSubventionQr().getRentalPlan() != null) {
                        terminalDetail.setInstallationFee(leadInformation.getBankSubventionQr().getBankSetupFee());
                        terminalDetail.setRecurringFees(leadInformation.getBankSubventionQr().getBankRecurringFee());
                        terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getStickerMarsRentalPlans().getCode()));
                    } else {
                        if (leadInformation.isGeeNeePos()) {
                            terminalDetail.setInstallationFee(String.valueOf(leadInformation.getSetUpFees()));
                            terminalDetail.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
                            terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getMarsRentalPlans().getCode()));
                        } else {
                            terminalDetail.setInstallationFee(String.valueOf(leadInformation.getSetUpFees()));
                            terminalDetail.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
                            terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getStickerMarsRentalPlans().getCode()));
                        }
                    }

                    if (leadInformation.isGeeNeePos()) {
                        terminalDetail.setRentalType((Objects.equals(leadInformation.getMarsRentalPlans().getPlanFrequency(), "M")) ? "R" : "A");
                    } else if (leadInformation.getStickerMarsRentalPlans() != null) {
                        terminalDetail.setRentalType((Objects.equals(leadInformation.getStickerMarsRentalPlans().getPlanFrequency(), "M")) ? "R" : "A");
                    } else {
                        terminalDetail.setRentalType(" ");
                    }
//                    terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getStickerMarsRentalPlans().getCode()));
//                    terminalDetail.setTerminalModeCode(sticker);
                    Device geenee = deviceRepository.fetchByDeviceName("QR-STICKER");
                    if (leadInformation.isGeeNeePos()) {
                        terminalDetail.setTerminalModeCode(geenee.getMarsDeviceCode());
                    } else {
                        terminalDetail.setTerminalModeCode(leadInformation.getStickerDevice() != null ? leadInformation.getStickerDevice().getMarsDeviceCode() : ""); //need to check
                    }
                    logger.info("deviceModeCode ---> {}", terminalDetail.getTerminalModeCode());
                    terminalDetail.setTerminalType("POS");
                    terminalDetail.setTotalEmiAmount("");
//                    List<String> vpa = vpaQrRepository.findStickerVpa(leadInformation.getLeadId());
//                    List<String> vpa1 = counterManagementRepository.findByDevice("Sticker", leadInformation.getLeadId());
//                    List<String> vpas = new ArrayList<>();
//                    vpa1.forEach(l -> {
//                        org.json.JSONArray jsonArray = new org.json.JSONArray(l);
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            logger.info("vpas----: {}", jsonArray.getString(i));
//                            vpas.add(jsonArray.getString(i));
//                        }
//                    });
                    terminalDetail.setVpa(vpas);
                    terminalDetails.add(terminalDetail);
                }
                List<String> vpa2 = counterManagementRepository.findByDevice("SoundBox", leadInformation.getLeadId());
                List<String> vpaList = new ArrayList<>();
                vpa2.forEach(l -> {
                    org.json.JSONArray jsonArray = new org.json.JSONArray(l);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        logger.info("vpas----: {}", jsonArray.getString(i));
                        vpaList.add(jsonArray.getString(i));
                    }
                });
                if (!vpaList.isEmpty()) {
                    TerminalDetail terminalDetail = new TerminalDetail();
                    terminalDetail.setAdvanceRentCollected(String.valueOf(leadInformation.getAmountCollected()));
                    terminalDetail.setAdvanceRentMode("NEFT");
                    terminalDetail.setDeviceOwnedBy("S");
                    terminalDetail.setDevicePrice(0.00);
                    terminalDetail.setEmiStartDate("17/10/2022");
                    terminalDetail.setEmiTenure("");
                    terminalDetail.setGracePeriod(0);
//                    terminalDetail.setInstallationFee(String.valueOf(leadInformation.getSetUpFees()));
                    terminalDetail.setNoOfMonthRentPaidInAdvance("0");
//                    terminalDetail.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
                    String rentalMode = null;
                    terminalDetail.setRentalMode("SE");
                    terminalDetail.setIntlCardAcceptance(leadInformation.getIcaEnabled() == 1 ? "Y" : "N");


                    if (internalSubvention1 != null && internalSubvention1.isSoundBox() && internalSubvention1.isSoundBoxRental()) {
                        terminalDetail.setInstallationFee(internalSubvention1.getSoundBoxApprovedSetupFee());
                        terminalDetail.setRecurringFees(String.valueOf(internalSubvention1.getSoundBoxApprovedRecurringFee()));
                        terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getSqrMarsRentalPlans().getCode()));
                    } else if (leadInformation.getBankSubventionSoundBox() != null && leadInformation.getBankSubventionSoundBox().getRentalPlan() != null) {
                        terminalDetail.setInstallationFee(leadInformation.getBankSubventionSoundBox().getBankSetupFee());
                        terminalDetail.setRecurringFees(leadInformation.getBankSubventionSoundBox().getBankRecurringFee());
                        terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getSqrMarsRentalPlans().getCode()));
                    } else {
                        terminalDetail.setInstallationFee(String.valueOf(leadInformation.getSetUpFees()));
                        terminalDetail.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
                        terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getSqrMarsRentalPlans().getCode()));
                    }


                    if (leadInformation.getSqrMarsRentalPlans() != null) {
                        terminalDetail.setRentalType((Objects.equals(leadInformation.getSqrMarsRentalPlans().getPlanFrequency(), "M")) ? "R" : "A");
                    } else {
                        terminalDetail.setRentalType(" ");
                    }
//                    terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getSqrMarsRentalPlans().getCode()));
//                    terminalDetail.setTerminalModeCode(soundBox);
                    terminalDetail.setTerminalModeCode(leadInformation.getSqrDevice() != null ? leadInformation.getSqrDevice().getMarsDeviceCode() : ""); //need to check
                    logger.info("deviceModeCode ---> {}", terminalDetail.getTerminalModeCode());
                    terminalDetail.setTerminalType("POS");
                    terminalDetail.setTotalEmiAmount("");
//                    List<String> vpa = vpaQrRepository.findSoundBoxVpa(leadInformation.getLeadId());
//                    List<String> vpa1 = counterManagementRepository.findByDevice("SoundBox", leadInformation.getLeadId());
//                    List<String> vpas = new ArrayList<>();
//                    vpa1.forEach(l -> {
//                        org.json.JSONArray jsonArray = new org.json.JSONArray(l);
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            logger.info("vpas----: {}", jsonArray.getString(i));
//                            vpas.add(jsonArray.getString(i));
//                        }
//                    });
                    terminalDetail.setVpa(vpaList);
                    terminalDetails.add(terminalDetail);
                }
                marsDto.setTerminalDetails(terminalDetails);
                marsDto.setBankInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBankInformation())));
                MdrPlanDto mdrPlanDto = new MdrPlanDto();
                ObjectMapper mapper = new ObjectMapper();
//                logger.info("-----------SQRMDR----------{}", leadInformation.getStickerMarsMdrCharges());

                if (internalSubvention1 != null && internalSubvention1.isPos() && internalSubvention1.isPosMdr() && leadInformation.isGeeNeePos()) {
                    logger.info("Entered Internal Mdr Subvention");
                    ObjectMapper objectMapper = new ObjectMapper();

                    //Dinner
                    String str1 = leadInformation.getMarsMdrCharges().getDiners();
                    MdrChargesDto Dinner = objectMapper.readValue(str1, MdrChargesDto.class);
                    Dinner.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedDiners()));
                    //StandardOrClassic
                    String str3 = leadInformation.getMarsMdrCharges().getStandardOrClassic();
                    MdrChargesDto StandardOrClassic = objectMapper.readValue(str3, MdrChargesDto.class);
                    StandardOrClassic.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedStandardOrClassic()));
                    //DomesticDebitUpTo2000
                    String str4 = leadInformation.getMarsMdrCharges().getDomesticDebitUpTo2000();
                    MdrChargesDto DomesticDebitUpTo2000 = objectMapper.readValue(str4, MdrChargesDto.class);
                    DomesticDebitUpTo2000.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedDomesticDebitUpTo2000()));
                    //DomesticDebitAbove2000
                    String str5 = leadInformation.getMarsMdrCharges().getDomesticDebitAbove2000();
                    MdrChargesDto DomesticDebitAbove2000 = objectMapper.readValue(str5, MdrChargesDto.class);
                    DomesticDebitAbove2000.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedDomesticDebitAbove2000()));
                    //SuperPremiumOrSignature
                    String str6 = leadInformation.getMarsMdrCharges().getSuperPremiumOrSignature();
                    MdrChargesDto SuperPremiumOrSignature = objectMapper.readValue(str6, MdrChargesDto.class);
                    SuperPremiumOrSignature.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedSuperPremiumOrSignature()));
                    //InternationalCreditCard
                    String str7 = leadInformation.getMarsMdrCharges().getInternationalCreditCard();
                    MdrChargesDto InternationalCreditCard = objectMapper.readValue(str7, MdrChargesDto.class);
                    InternationalCreditCard.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedInternationalCreditCard()));
                    //InternationalDebitCard
                    String str8 = leadInformation.getMarsMdrCharges().getInternationalDebitCard();
                    MdrChargesDto InternationalDebitCard = objectMapper.readValue(str8, MdrChargesDto.class);
                    InternationalDebitCard.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedInternationalDebitCard()));
                    //CommercialOrCorporate
                    String str9 = leadInformation.getMarsMdrCharges().getCommercialOrCorporate();
                    MdrChargesDto CommercialOrCorporate = objectMapper.readValue(str9, MdrChargesDto.class);
                    CommercialOrCorporate.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedCommercialOrCorporate()));
                    //PremiumOrPlatinum
                    String str10 = leadInformation.getMarsMdrCharges().getPremiumOrPlatinum();
                    MdrChargesDto PremiumOrPlatinum = objectMapper.readValue(str10, MdrChargesDto.class);
                    PremiumOrPlatinum.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedPremiumOrPlatinum()));
                    //CashAtPos
                    String str11 = leadInformation.getMarsMdrCharges().getCashAtPos();
                    MdrChargesDto CashAtPos = objectMapper.readValue(str11, MdrChargesDto.class);
                    CashAtPos.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedCashAtPos()));
                    //UpiCreditAbove2000
                    String str12 = leadInformation.getMarsMdrCharges().getUpiCreditAbove2000();
                    MdrChargesDto upiCreditAbove2000 = objectMapper.readValue(str12, MdrChargesDto.class);
                    upiCreditAbove2000.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedUpiCreditAbove2000()));
                    //UpiCreditUpto2000
                    String str13 = leadInformation.getMarsMdrCharges().getUpiCreditUpto2000();
                    MdrChargesDto upiCreditUpto2000 = objectMapper.readValue(str13, MdrChargesDto.class);
                    upiCreditUpto2000.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedUpiCreditUpto2000()));
                    //AmexDomestic
                    String str14 = leadInformation.getMarsMdrCharges().getAmexDomestic();
                    MdrChargesDto amexDomestic = objectMapper.readValue(str14, MdrChargesDto.class);
                    amexDomestic.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedAmexDomestic()));
                    //AmexInternational
                    String str15 = leadInformation.getMarsMdrCharges().getAmexInternational();
                    MdrChargesDto amexInternational = objectMapper.readValue(str15, MdrChargesDto.class);
                    amexInternational.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedAmexInternational()));


                    mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
                    mdrPlanDto.setDiners(Dinner);
                    mdrPlanDto.setOnus(mapper.readValue(leadInformation.getMarsMdrCharges().getOnus(), MdrChargesDto.class));
                    mdrPlanDto.setStandardOrClassic(StandardOrClassic);
                    mdrPlanDto.setDomesticDebitUpTo2000(DomesticDebitUpTo2000);
                    mdrPlanDto.setDomesticDebitAbove2000(DomesticDebitAbove2000);
                    mdrPlanDto.setSuperPremiumOrSignature(SuperPremiumOrSignature);
                    mdrPlanDto.setInternationalCreditCard(InternationalCreditCard);
                    mdrPlanDto.setInternationalDebitCard(InternationalDebitCard);
                    mdrPlanDto.setCommercialOrCorporate(CommercialOrCorporate);
                    mdrPlanDto.setPremiumOrPlatinum(PremiumOrPlatinum);
                    mdrPlanDto.setCashAtPos(CashAtPos);
                    mdrPlanDto.setUpiCreditAbove2000(upiCreditAbove2000);
                    mdrPlanDto.setUpiCreditUpto2000(upiCreditUpto2000);
                    mdrPlanDto.setCode(Integer.parseInt(leadInformation.getMarsMdrCharges().getCode()));
                    mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInformation.getMarsMdrCharges().getUpiAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInformation.getMarsMdrCharges().getUpiUpto2000(), MdrChargesDto.class));
                    mdrPlanDto.setAmexDomestic(amexDomestic);
                    mdrPlanDto.setAmexInternational(amexInternational);
                } else if (leadInformation.isGeeNeePos() && leadInformation.getMarsMdrCharges() != null) {
                    mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
                    mdrPlanDto.setDiners(mapper.readValue(leadInformation.getMarsMdrCharges().getDiners(), MdrChargesDto.class));
                    mdrPlanDto.setOnus(mapper.readValue(leadInformation.getMarsMdrCharges().getOnus(), MdrChargesDto.class));
                    mdrPlanDto.setStandardOrClassic(mapper.readValue(leadInformation.getMarsMdrCharges().getStandardOrClassic(), MdrChargesDto.class));
                    mdrPlanDto.setDomesticDebitUpTo2000(mapper.readValue(leadInformation.getMarsMdrCharges().getDomesticDebitUpTo2000(), MdrChargesDto.class));
                    mdrPlanDto.setDomesticDebitAbove2000(mapper.readValue(leadInformation.getMarsMdrCharges().getDomesticDebitAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(leadInformation.getMarsMdrCharges().getSuperPremiumOrSignature(), MdrChargesDto.class));
                    mdrPlanDto.setInternationalCreditCard(mapper.readValue(leadInformation.getMarsMdrCharges().getInternationalCreditCard(), MdrChargesDto.class));
                    mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInformation.getMarsMdrCharges().getInternationalDebitCard(), MdrChargesDto.class));
                    mdrPlanDto.setCommercialOrCorporate(mapper.readValue(leadInformation.getMarsMdrCharges().getCommercialOrCorporate(), MdrChargesDto.class));
                    mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(leadInformation.getMarsMdrCharges().getPremiumOrPlatinum(), MdrChargesDto.class));
                    mdrPlanDto.setCashAtPos(mapper.readValue(leadInformation.getMarsMdrCharges().getCashAtPos(), MdrChargesDto.class));
                    mdrPlanDto.setCode(Integer.parseInt(leadInformation.getMarsMdrCharges().getCode()));
                    mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInformation.getMarsMdrCharges().getUpiAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInformation.getMarsMdrCharges().getUpiUpto2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiCreditAbove2000(mapper.readValue(leadInformation.getMarsMdrCharges().getUpiCreditAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiCreditUpto2000(mapper.readValue(leadInformation.getMarsMdrCharges().getUpiCreditUpto2000(), MdrChargesDto.class));
                    mdrPlanDto.setAmexDomestic(mapper.readValue(leadInformation.getMarsMdrCharges().getAmexDomestic(), MdrChargesDto.class));
                    mdrPlanDto.setAmexInternational(mapper.readValue(leadInformation.getMarsMdrCharges().getAmexInternational(), MdrChargesDto.class));
                } else if (internalSubvention1 != null && internalSubvention1.isSticker() && internalSubvention1.isQrMdr()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    //masterPass
//                    String str = leadInformation.getStickerMarsMdrCharges().getMasterPass();
//                    MdrChargesDto MasterPass = objectMapper.readValue(str, MdrChargesDto.class);
//                    MasterPass.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedMasterPass()));
                    //Dinner
                    String str1 = leadInformation.getStickerMarsMdrCharges().getDiners();
                    MdrChargesDto Dinner = objectMapper.readValue(str1, MdrChargesDto.class);
                    Dinner.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedDiners()));
                    //Onus
//                    String str2 = leadInformation.getStickerMarsMdrCharges().getOnus();
//                    MdrChargesDto Onus = objectMapper.readValue(str2, MdrChargesDto.class);
//                    Onus.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedOnus()));
                    //StandardOrClassic
                    String str3 = leadInformation.getStickerMarsMdrCharges().getStandardOrClassic();
                    MdrChargesDto StandardOrClassic = objectMapper.readValue(str3, MdrChargesDto.class);
                    StandardOrClassic.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedStandardOrClassic()));
                    //DomesticDebitUpTo2000
                    String str4 = leadInformation.getStickerMarsMdrCharges().getDomesticDebitUpTo2000();
                    MdrChargesDto DomesticDebitUpTo2000 = objectMapper.readValue(str4, MdrChargesDto.class);
                    DomesticDebitUpTo2000.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedDomesticDebitUpTo2000()));
                    //DomesticDebitAbove2000
                    String str5 = leadInformation.getStickerMarsMdrCharges().getDomesticDebitAbove2000();
                    MdrChargesDto DomesticDebitAbove2000 = objectMapper.readValue(str5, MdrChargesDto.class);
                    DomesticDebitAbove2000.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedDomesticDebitAbove2000()));
                    //SuperPremiumOrSignature
                    String str6 = leadInformation.getStickerMarsMdrCharges().getSuperPremiumOrSignature();
                    MdrChargesDto SuperPremiumOrSignature = objectMapper.readValue(str6, MdrChargesDto.class);
                    SuperPremiumOrSignature.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedSuperPremiumOrSignature()));
                    //InternationalCreditCard
                    String str7 = leadInformation.getStickerMarsMdrCharges().getInternationalCreditCard();
                    MdrChargesDto InternationalCreditCard = objectMapper.readValue(str7, MdrChargesDto.class);
                    InternationalCreditCard.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedInternationalCreditCard()));
                    //InternationalDebitCard
                    String str8 = leadInformation.getStickerMarsMdrCharges().getInternationalDebitCard();
                    MdrChargesDto InternationalDebitCard = objectMapper.readValue(str8, MdrChargesDto.class);
                    InternationalDebitCard.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedInternationalDebitCard()));
                    //CommercialOrCorporate
                    String str9 = leadInformation.getStickerMarsMdrCharges().getCommercialOrCorporate();
                    MdrChargesDto CommercialOrCorporate = objectMapper.readValue(str9, MdrChargesDto.class);
                    CommercialOrCorporate.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedCommercialOrCorporate()));
                    //PremiumOrPlatinum
                    String str10 = leadInformation.getStickerMarsMdrCharges().getPremiumOrPlatinum();
                    MdrChargesDto PremiumOrPlatinum = objectMapper.readValue(str10, MdrChargesDto.class);
                    PremiumOrPlatinum.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedPremiumOrPlatinum()));
                    //CashAtPos
                    String str11 = leadInformation.getStickerMarsMdrCharges().getCashAtPos();
                    MdrChargesDto CashAtPos = objectMapper.readValue(str11, MdrChargesDto.class);
                    CashAtPos.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedCashAtPos()));
                    //UpiCreditAbove2000
                    String str12 = leadInformation.getStickerMarsMdrCharges().getUpiCreditAbove2000();
                    MdrChargesDto upiCreditAbove2000 = objectMapper.readValue(str12, MdrChargesDto.class);
                    upiCreditAbove2000.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedUpiCreditAbove2000()));
                    //UpiCreditUpto2000
                    String str13 = leadInformation.getStickerMarsMdrCharges().getUpiCreditUpto2000();
                    MdrChargesDto upiCreditUpto2000 = objectMapper.readValue(str13, MdrChargesDto.class);
                    upiCreditUpto2000.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedUpiCreditUpto2000()));
                    //AmexDomestic
                    String str14 = leadInformation.getStickerMarsMdrCharges().getAmexDomestic();
                    MdrChargesDto amexDomestic = objectMapper.readValue(str14, MdrChargesDto.class);
                    amexDomestic.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedAmexDomestic()));
                    //AmexInternational
                    String str15 = leadInformation.getStickerMarsMdrCharges().getAmexInternational();
                    MdrChargesDto amexInternational = objectMapper.readValue(str15, MdrChargesDto.class);
                    amexInternational.setPercentage(Double.parseDouble(internalSubvention1.getStickerApprovedAmexInternational()));


                    mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
                    mdrPlanDto.setDiners(Dinner);
                    mdrPlanDto.setOnus(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getOnus(), MdrChargesDto.class));
                    mdrPlanDto.setStandardOrClassic(StandardOrClassic);
                    mdrPlanDto.setDomesticDebitUpTo2000(DomesticDebitUpTo2000);
                    mdrPlanDto.setDomesticDebitAbove2000(DomesticDebitAbove2000);
                    mdrPlanDto.setSuperPremiumOrSignature(SuperPremiumOrSignature);
                    mdrPlanDto.setInternationalCreditCard(InternationalCreditCard);
                    mdrPlanDto.setInternationalDebitCard(InternationalDebitCard);
                    mdrPlanDto.setCommercialOrCorporate(CommercialOrCorporate);
                    mdrPlanDto.setPremiumOrPlatinum(PremiumOrPlatinum);
                    mdrPlanDto.setCashAtPos(CashAtPos);
                    mdrPlanDto.setCode(Integer.parseInt(leadInformation.getStickerMarsMdrCharges().getCode()));
                    mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getUpiUpto2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getUpiAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiCreditAbove2000(upiCreditAbove2000);
                    mdrPlanDto.setUpiCreditUpto2000(upiCreditUpto2000);
                    mdrPlanDto.setAmexDomestic(amexDomestic);
                    mdrPlanDto.setAmexInternational(amexInternational);


                } else if (internalSubvention1 != null && internalSubvention1.isSoundBox() && internalSubvention1.isSoundBoxMdr()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    //masterPass
//                    String str = leadInformation.getSqrMarsMdrCharge().getMasterPass();
//                    MdrChargesDto MasterPass = objectMapper.readValue(str, MdrChargesDto.class);
//                    MasterPass.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedMasterPass()));
                    //Dinner
                    String str1 = leadInformation.getSqrMarsMdrCharge().getDiners();
                    MdrChargesDto Dinner = objectMapper.readValue(str1, MdrChargesDto.class);
                    Dinner.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedDiners()));
                    //Onus
//                    String str2 = leadInformation.getSqrMarsMdrCharge().getOnus();
//                    MdrChargesDto Onus = objectMapper.readValue(str2, MdrChargesDto.class);
//                    Onus.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedOnus()));
                    //StandardOrClassic
                    String str3 = leadInformation.getSqrMarsMdrCharge().getStandardOrClassic();
                    MdrChargesDto StandardOrClassic = objectMapper.readValue(str3, MdrChargesDto.class);
                    StandardOrClassic.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedStandardOrClassic()));
                    //DomesticDebitUpTo2000
                    String str4 = leadInformation.getSqrMarsMdrCharge().getDomesticDebitUpTo2000();
                    MdrChargesDto DomesticDebitUpTo2000 = objectMapper.readValue(str4, MdrChargesDto.class);
                    DomesticDebitUpTo2000.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedDomesticDebitUpTo2000()));
                    //DomesticDebitAbove2000
                    String str5 = leadInformation.getSqrMarsMdrCharge().getDomesticDebitAbove2000();
                    MdrChargesDto DomesticDebitAbove2000 = objectMapper.readValue(str5, MdrChargesDto.class);
                    DomesticDebitAbove2000.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedDomesticDebitAbove2000()));
                    //SuperPremiumOrSignature
                    String str6 = leadInformation.getSqrMarsMdrCharge().getSuperPremiumOrSignature();
                    MdrChargesDto SuperPremiumOrSignature = objectMapper.readValue(str6, MdrChargesDto.class);
                    SuperPremiumOrSignature.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedSuperPremiumOrSignature()));
                    //InternationalCreditCard
                    String str7 = leadInformation.getSqrMarsMdrCharge().getInternationalCreditCard();
                    MdrChargesDto InternationalCreditCard = objectMapper.readValue(str7, MdrChargesDto.class);
                    InternationalCreditCard.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedInternationalCreditCard()));
                    //InternationalDebitCard
                    String str8 = leadInformation.getSqrMarsMdrCharge().getInternationalDebitCard();
                    MdrChargesDto InternationalDebitCard = objectMapper.readValue(str8, MdrChargesDto.class);
                    InternationalDebitCard.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedInternationalDebitCard()));
                    //CommercialOrCorporate
                    String str9 = leadInformation.getSqrMarsMdrCharge().getCommercialOrCorporate();
                    MdrChargesDto CommercialOrCorporate = objectMapper.readValue(str9, MdrChargesDto.class);
                    CommercialOrCorporate.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedCommercialOrCorporate()));
                    //PremiumOrPlatinum
                    String str10 = leadInformation.getSqrMarsMdrCharge().getPremiumOrPlatinum();
                    MdrChargesDto PremiumOrPlatinum = objectMapper.readValue(str10, MdrChargesDto.class);
                    PremiumOrPlatinum.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedPremiumOrPlatinum()));
                    //CashAtPos
                    String str11 = leadInformation.getSqrMarsMdrCharge().getCashAtPos();
                    MdrChargesDto CashAtPos = objectMapper.readValue(str11, MdrChargesDto.class);
                    CashAtPos.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedCashAtPos()));
                    //UpiCreditAbove2000
                    String str12 = leadInformation.getSqrMarsMdrCharge().getUpiCreditAbove2000();
                    MdrChargesDto upiCreditAbove2000 = objectMapper.readValue(str12, MdrChargesDto.class);
                    upiCreditAbove2000.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedUpiCreditAbove2000()));
                    //UpiCreditUpto2000
                    String str13 = leadInformation.getSqrMarsMdrCharge().getUpiCreditUpto2000();
                    MdrChargesDto upiCreditUpto2000 = objectMapper.readValue(str13, MdrChargesDto.class);
                    upiCreditUpto2000.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedUpiCreditUpto2000()));
                    //AmexDomestic
                    String str14 = leadInformation.getSqrMarsMdrCharge().getAmexDomestic();
                    MdrChargesDto amexDomestic = objectMapper.readValue(str14, MdrChargesDto.class);
                    amexDomestic.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedAmexDomestic()));
                    //AmexInternational
                    String str15 = leadInformation.getSqrMarsMdrCharge().getAmexInternational();
                    MdrChargesDto amexInternational = objectMapper.readValue(str15, MdrChargesDto.class);
                    amexInternational.setPercentage(Double.parseDouble(internalSubvention1.getSoundBoxApprovedAmexInternational()));


                    mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getMasterPass(), MdrChargesDto.class));
                    mdrPlanDto.setDiners(Dinner);
                    mdrPlanDto.setOnus(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getOnus(), MdrChargesDto.class));
                    mdrPlanDto.setStandardOrClassic(StandardOrClassic);
                    mdrPlanDto.setDomesticDebitUpTo2000(DomesticDebitUpTo2000);
                    mdrPlanDto.setDomesticDebitAbove2000(DomesticDebitAbove2000);
                    mdrPlanDto.setSuperPremiumOrSignature(SuperPremiumOrSignature);
                    mdrPlanDto.setInternationalCreditCard(InternationalCreditCard);
                    mdrPlanDto.setInternationalDebitCard(InternationalDebitCard);
                    mdrPlanDto.setCommercialOrCorporate(CommercialOrCorporate);
                    mdrPlanDto.setPremiumOrPlatinum(PremiumOrPlatinum);
                    mdrPlanDto.setCashAtPos(CashAtPos);
                    mdrPlanDto.setCode(Integer.parseInt(leadInformation.getSqrMarsMdrCharge().getCode()));
                    mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getUpiUpto2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getUpiAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiCreditAbove2000(upiCreditAbove2000);
                    mdrPlanDto.setUpiCreditUpto2000(upiCreditUpto2000);
                    mdrPlanDto.setAmexDomestic(amexDomestic);
                    mdrPlanDto.setAmexInternational(amexInternational);

                } else {
                    if (leadInformation.getStickerMarsMdrCharges() != null) {
                        mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
                        mdrPlanDto.setDiners(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getDiners(), MdrChargesDto.class));
                        mdrPlanDto.setOnus(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getOnus(), MdrChargesDto.class));
                        mdrPlanDto.setStandardOrClassic(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getStandardOrClassic(), MdrChargesDto.class));
                        mdrPlanDto.setDomesticDebitUpTo2000(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getDomesticDebitUpTo2000(), MdrChargesDto.class));
                        mdrPlanDto.setDomesticDebitAbove2000(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getDomesticDebitAbove2000(), MdrChargesDto.class));
                        mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getSuperPremiumOrSignature(), MdrChargesDto.class));
                        mdrPlanDto.setInternationalCreditCard(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getInternationalCreditCard(), MdrChargesDto.class));
                        mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getInternationalDebitCard(), MdrChargesDto.class));
                        mdrPlanDto.setCommercialOrCorporate(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getCommercialOrCorporate(), MdrChargesDto.class));
                        mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getPremiumOrPlatinum(), MdrChargesDto.class));
                        mdrPlanDto.setCashAtPos(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getCashAtPos(), MdrChargesDto.class));
                        mdrPlanDto.setCode(Integer.parseInt(leadInformation.getStickerMarsMdrCharges().getCode()));
                        mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getUpiUpto2000(), MdrChargesDto.class));
                        mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getUpiAbove2000(), MdrChargesDto.class));
                        mdrPlanDto.setUpiCreditAbove2000(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getUpiCreditAbove2000(), MdrChargesDto.class));
                        mdrPlanDto.setUpiCreditUpto2000(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getUpiCreditUpto2000(), MdrChargesDto.class));
                        mdrPlanDto.setAmexDomestic(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getAmexDomestic(), MdrChargesDto.class));
                        mdrPlanDto.setAmexInternational(mapper.readValue(leadInformation.getStickerMarsMdrCharges().getAmexInternational(), MdrChargesDto.class));
                    } else {
                        mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getMasterPass(), MdrChargesDto.class));
                        mdrPlanDto.setDiners(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getDiners(), MdrChargesDto.class));
                        mdrPlanDto.setOnus(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getOnus(), MdrChargesDto.class));
                        mdrPlanDto.setStandardOrClassic(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getStandardOrClassic(), MdrChargesDto.class));
                        mdrPlanDto.setDomesticDebitUpTo2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getDomesticDebitUpTo2000(), MdrChargesDto.class));
                        mdrPlanDto.setDomesticDebitAbove2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getDomesticDebitAbove2000(), MdrChargesDto.class));
                        mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getSuperPremiumOrSignature(), MdrChargesDto.class));
                        mdrPlanDto.setInternationalCreditCard(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getInternationalCreditCard(), MdrChargesDto.class));
                        mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getInternationalDebitCard(), MdrChargesDto.class));
                        mdrPlanDto.setCommercialOrCorporate(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getCommercialOrCorporate(), MdrChargesDto.class));
                        mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getPremiumOrPlatinum(), MdrChargesDto.class));
                        mdrPlanDto.setCashAtPos(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getCashAtPos(), MdrChargesDto.class));
                        mdrPlanDto.setCode(Integer.parseInt(leadInformation.getSqrMarsMdrCharge().getCode()));
                        mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getUpiUpto2000(), MdrChargesDto.class));
                        mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getUpiAbove2000(), MdrChargesDto.class));
                        mdrPlanDto.setUpiCreditAbove2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getUpiCreditAbove2000(), MdrChargesDto.class));
                        mdrPlanDto.setUpiCreditUpto2000(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getUpiCreditUpto2000(), MdrChargesDto.class));
                        mdrPlanDto.setAmexDomestic(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getAmexDomestic(), MdrChargesDto.class));
                        mdrPlanDto.setAmexInternational(mapper.readValue(leadInformation.getSqrMarsMdrCharge().getAmexInternational(), MdrChargesDto.class));
                    }
                }
//                logger.info("-----------SQRMDR----------{}", leadInformation.getSqrMarsMdrCharge());
                marsDto.setMdrPlan(mdrPlanDto);
                marsDto.setRemarks("");
                marsDto.setRevParameters((JSONObject) parser.parse(marsOnboardingInformation.getPaymentDetails()));


                EdcResponseDto edcData = null;
                logger.info(" data 2 ... {}", marsDto);
                HashMap<String, Object> dummyDto = new HashMap<>();
                dummyDto.put("merchant", marsDto);
                JSONObject jsonObject = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                logger.info(" JSON OBJ : ---- : {}", jsonObject);
                logger.info(" dummy -- : {}", CommonUtil.parseJson(jsonObject));
                HttpURLConnection conn = null;
                try {
//                    URL obj = new URL(marsVpaUrl);
                    URL obj = new URL(qrmarsAddOnUrl);
                    conn = (HttpURLConnection) obj.openConnection();
                    conn.setRequestProperty("NII", "110");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    // HTTP Connection Headers completed
                    logger.info("HTTP headers : ----- : {}", conn);
                    // Sending API request
                    logger.info("Getting Tickets Status {}", dummyDto);
                    OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                    JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                    out.write(CommonUtil.parseJson(reqData)); // sending the data
                    logger.info("Getting Tickets Status {}", reqData);
                    out.close(); // closing the output stream
                    int responsecode = conn.getResponseCode();
                    logger.info("The response code is {}", conn);
                    String message = conn.getResponseMessage();
                    logger.info("The response code is {}", responsecode);
                    logger.info("The response message is {}", message);
                    edcMarsResponseDto.setCode(conn.getResponseCode());
                } catch (Exception e) {
                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "SOMETHING WENT WRONG", e.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
                }

                if (conn.getResponseCode() == 200 || conn.getResponseCode() == 202) {
                    leadInformation.setCounterCompleted(true);
                    leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(MARS_QR_PENDING));
                    leadInformationRepository.save(leadInformation);
                    return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
                } else {
                    logger.info("-------------------InternalServerError-----------------{}", conn.getResponseCode());
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "Failure", conn.getResponseMessage()), CONFLICT);
                }

            }
            return new ResponseEntity<>(new ApiResponse(CONFLICT, "LeadNotFount"), CONFLICT);
        }
        return new ResponseEntity<>(new

                ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/submit-to-mars-from-so-app1-addOn/{id}")
    public ResponseEntity<?> submitToMarsAddOn(HttpServletRequest request, @PathVariable("id") long id) throws Exception {
        String authtoken = request.getHeader("Authorization");
        User curuser = getSessionUser();
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            LeadInformation leadInformation = leadInformationRepository.findLeadId(id);
            if (leadInformation != null && leadInformation.getLeadStatus().getLeadStatusId() == AGREEMENT_SIGNING_COMPLETED || leadInformation.getLeadStatus().getLeadStatusId() == HOST_API_FAILED) {
                List<UploadedDocuments> uploadedDocumentsList = implementationService.findByLeadId(leadInformation);
                if (!uploadedDocumentsList.isEmpty()) {
                    List<SignzyFetchAndVerification> panDetails = implementationService.getPanDetails(uploadedDocumentsList);
                    if (!panDetails.isEmpty()) {
                        leadInformation.setPanNumber(attributeEncryptor.decrypt(panDetails.get(0).getPanNumber()));
                        leadInformationRepository.save(leadInformation);
                        logger.info("pan Number -- : {}", attributeEncryptor.decrypt(panDetails.get(0).getPanNumber()));
                    }
                }
                MarsOnboardingInformation marsOnboardingInformation = implementationService.setMarsDetails(leadInformation);
                MarsDto marsDto = new MarsDto();
                EDCMarsResponseDto edcMarsResponseDto = new EDCMarsResponseDto();
                StringBuilder strResp = new StringBuilder();
                JSONParser parser = new JSONParser();
                marsDto.setDevice("");
                marsDto.setSalesInformation((JSONObject) parser.parse(marsOnboardingInformation.getSalesInformation()));
                marsDto.setAdditionalInfo((JSONObject) parser.parse(marsOnboardingInformation.getAdditionalInfo()));
                marsDto.setCompanyInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getCompanyInformation())));
                marsDto.setBusinessInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBusinessInformation())));
                marsDto.setPartnerInformation((JSONArray) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getPartnerInformation())));

                List<TerminalDetail> terminalDetails = new ArrayList<>();
                TerminalDetail terminalDetail = new TerminalDetail();
                terminalDetail.setAdvanceRentCollected(String.valueOf(leadInformation.getAmountCollected()));
                terminalDetail.setAdvanceRentMode("NEFT");
                terminalDetail.setDeviceOwnedBy("S");
                terminalDetail.setDevicePrice(0.00);
                terminalDetail.setEmiStartDate("17/10/2022");
                terminalDetail.setEmiTenure("null");
                terminalDetail.setGracePeriod(0);
                terminalDetail.setNoOfMonthRentPaidInAdvance("0");
                terminalDetail.setNumberOfTerminals(leadInformation.getDeviceCount());
                terminalDetail.setIntlCardAcceptance(leadInformation.getIcaEnabled() == 1 ? "Y" : "N");
                InternalSubvention internalSubvention1 = !internalSubventionRepository.getDetailByLeadIds(leadInformation.getLeadId()).isEmpty() ? internalSubventionRepository.getDetailByLeadIds(leadInformation.getLeadId()).get(0) : null;
                logger.info("internalSubvention---{}", internalSubvention1 != null ? internalSubvention1.getId() : null);
                if (internalSubvention1 != null && internalSubvention1.isPos() && internalSubvention1.isPosRental()) {
                    logger.info("Entered Rental Internal Subvention");
                    terminalDetail.setInstallationFee(internalSubvention1.getPosApprovedSetupFee());
                    terminalDetail.setRecurringFees(String.valueOf(internalSubvention1.getPosApprovedRecurringFee()));
                    terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getMarsRentalPlans().getCode()));
                } else if (leadInformation.getBankSubventionPos() != null && leadInformation.getBankSubventionPos().getRentalPlan() != null) {
                    logger.info("Entered Rental Bank Subvention");
                    terminalDetail.setInstallationFee(leadInformation.getBankSubventionPos().getBankSetupFee());
                    terminalDetail.setRecurringFees(leadInformation.getBankSubventionPos().getBankRecurringFee());
                    terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getMarsRentalPlans().getCode()));
                } else {
                    terminalDetail.setInstallationFee(String.valueOf(0.00));
                    terminalDetail.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
                    terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getMarsRentalPlans().getCode()));
                }
                terminalDetail.setRentalMode("SE");
                terminalDetail.setTerminalModeCode(leadInformation.getDevice() != null ? leadInformation.getDevice().getMarsDeviceCode() : ""); //need to check
                logger.info("deviceModeCode ---> {}", terminalDetail.getTerminalModeCode());
                terminalDetail.setTerminalType("POS");
                terminalDetail.setTotalEmiAmount("null");
                terminalDetails.add(terminalDetail);
                marsDto.setTerminalDetails(terminalDetails);
//                PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto();
//                paymentDetailsDto.setAdvanceRentCollected(String.valueOf(leadInformation.getAmountCollected()));
//                paymentDetailsDto.setAdvanceRentMode("NEFT");
//                paymentDetailsDto.setDeviceOwnedBy("S");
//                paymentDetailsDto.setDevicePrice(0.00);
//                paymentDetailsDto.setEmiStartDate("17/10/2022");
//                paymentDetailsDto.setEmiTenure("null");
//                paymentDetailsDto.setGracePeriod(0);
//                paymentDetailsDto.setInstallationFee(String.valueOf(0.00));
//                paymentDetailsDto.setNoOfMonthRentPaidInAdvance("0");
//                paymentDetailsDto.setNumberOfTerminals(leadInformation.getDeviceCount());
//                paymentDetailsDto.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
//                paymentDetailsDto.setRentalPlanCode(Integer.parseInt(leadInformation.getMarsRentalPlans().getCode()));
//                paymentDetailsDto.setRentalMode("SE");
//                paymentDetailsDto.setTerminalModeCode("33");
//                logger.info("deviceModeCode ---> {}", paymentDetailsDto.getTerminalModeCode());
//                paymentDetailsDto.setTerminalType("POS");
//                paymentDetailsDto.setTotalEmiAmount("null");
//                marsDto.setPaymentDetails(paymentDetailsDto);
                marsDto.setBankInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBankInformation())));

                MdrPlanDto mdrPlanDto = new MdrPlanDto();

                ObjectMapper mapper = new ObjectMapper();

                if (internalSubvention1 != null && internalSubvention1.isPos() && internalSubvention1.isPosMdr()) {
                    logger.info("Entered Internal Mdr Subvention");
                    ObjectMapper objectMapper = new ObjectMapper();
                    //masterPass
//                    String str = leadInformation.getMarsMdrCharges().getMasterPass();
//                    MdrChargesDto MasterPass = objectMapper.readValue(str, MdrChargesDto.class);
//                    MasterPass.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedMasterPass()));
                    //Dinner
                    String str1 = leadInformation.getMarsMdrCharges().getDiners();
                    MdrChargesDto Dinner = objectMapper.readValue(str1, MdrChargesDto.class);
                    Dinner.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedDiners()));
                    //Onus
//                    String str2 = leadInformation.getMarsMdrCharges().getOnus();
//                    MdrChargesDto Onus = objectMapper.readValue(str2, MdrChargesDto.class);
//                    Onus.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedOnus()));
                    //StandardOrClassic
                    String str3 = leadInformation.getMarsMdrCharges().getStandardOrClassic();
                    MdrChargesDto StandardOrClassic = objectMapper.readValue(str3, MdrChargesDto.class);
                    StandardOrClassic.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedStandardOrClassic()));
                    //DomesticDebitUpTo2000
                    String str4 = leadInformation.getMarsMdrCharges().getDomesticDebitUpTo2000();
                    MdrChargesDto DomesticDebitUpTo2000 = objectMapper.readValue(str4, MdrChargesDto.class);
                    DomesticDebitUpTo2000.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedDomesticDebitUpTo2000()));
                    //DomesticDebitAbove2000
                    String str5 = leadInformation.getMarsMdrCharges().getDomesticDebitAbove2000();
                    MdrChargesDto DomesticDebitAbove2000 = objectMapper.readValue(str5, MdrChargesDto.class);
                    DomesticDebitAbove2000.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedDomesticDebitAbove2000()));
                    //SuperPremiumOrSignature
                    String str6 = leadInformation.getMarsMdrCharges().getSuperPremiumOrSignature();
                    MdrChargesDto SuperPremiumOrSignature = objectMapper.readValue(str6, MdrChargesDto.class);
                    SuperPremiumOrSignature.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedSuperPremiumOrSignature()));
                    //InternationalCreditCard
                    String str7 = leadInformation.getMarsMdrCharges().getInternationalCreditCard();
                    MdrChargesDto InternationalCreditCard = objectMapper.readValue(str7, MdrChargesDto.class);
                    InternationalCreditCard.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedInternationalCreditCard()));
                    //InternationalDebitCard
                    String str8 = leadInformation.getMarsMdrCharges().getInternationalDebitCard();
                    MdrChargesDto InternationalDebitCard = objectMapper.readValue(str8, MdrChargesDto.class);
                    InternationalDebitCard.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedInternationalDebitCard()));
                    //CommercialOrCorporate
                    String str9 = leadInformation.getMarsMdrCharges().getCommercialOrCorporate();
                    MdrChargesDto CommercialOrCorporate = objectMapper.readValue(str9, MdrChargesDto.class);
                    CommercialOrCorporate.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedCommercialOrCorporate()));
                    //PremiumOrPlatinum
                    String str10 = leadInformation.getMarsMdrCharges().getPremiumOrPlatinum();
                    MdrChargesDto PremiumOrPlatinum = objectMapper.readValue(str10, MdrChargesDto.class);
                    PremiumOrPlatinum.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedPremiumOrPlatinum()));
                    //CashAtPos
                    String str11 = leadInformation.getMarsMdrCharges().getCashAtPos();
                    MdrChargesDto CashAtPos = objectMapper.readValue(str11, MdrChargesDto.class);
                    CashAtPos.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedCashAtPos()));
                    //UpiCreditAbove2000
                    String str12 = leadInformation.getMarsMdrCharges().getUpiCreditAbove2000();
                    MdrChargesDto upiCreditAbove2000 = objectMapper.readValue(str12, MdrChargesDto.class);
                    upiCreditAbove2000.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedUpiCreditAbove2000()));
                    //UpiCreditUpto2000
                    String str13 = leadInformation.getMarsMdrCharges().getUpiCreditUpto2000();
                    MdrChargesDto upiCreditUpto2000 = objectMapper.readValue(str13, MdrChargesDto.class);
                    upiCreditUpto2000.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedUpiCreditUpto2000()));
                    //AmexDomestic
                    String str14 = leadInformation.getMarsMdrCharges().getAmexDomestic();
                    MdrChargesDto amexDomestic = objectMapper.readValue(str14, MdrChargesDto.class);
                    amexDomestic.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedAmexDomestic()));
                    //AmexInternational
                    String str15 = leadInformation.getMarsMdrCharges().getAmexInternational();
                    MdrChargesDto amexInternational = objectMapper.readValue(str15, MdrChargesDto.class);
                    amexInternational.setPercentage(Double.parseDouble(internalSubvention1.getPosApprovedAmexInternational()));


                    mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
                    mdrPlanDto.setDiners(Dinner);
                    mdrPlanDto.setOnus(mapper.readValue(leadInformation.getMarsMdrCharges().getOnus(), MdrChargesDto.class));
                    mdrPlanDto.setStandardOrClassic(StandardOrClassic);
                    mdrPlanDto.setDomesticDebitUpTo2000(DomesticDebitUpTo2000);
                    mdrPlanDto.setDomesticDebitAbove2000(DomesticDebitAbove2000);
                    mdrPlanDto.setSuperPremiumOrSignature(SuperPremiumOrSignature);
                    mdrPlanDto.setInternationalCreditCard(InternationalCreditCard);
                    mdrPlanDto.setInternationalDebitCard(InternationalDebitCard);
                    mdrPlanDto.setCommercialOrCorporate(CommercialOrCorporate);
                    mdrPlanDto.setPremiumOrPlatinum(PremiumOrPlatinum);
                    mdrPlanDto.setCashAtPos(CashAtPos);
                    mdrPlanDto.setUpiCreditAbove2000(upiCreditAbove2000);
                    mdrPlanDto.setUpiCreditUpto2000(upiCreditUpto2000);
                    mdrPlanDto.setCode(Integer.parseInt(leadInformation.getMarsMdrCharges().getCode()));
                    mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInformation.getMarsMdrCharges().getUpiAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInformation.getMarsMdrCharges().getUpiUpto2000(), MdrChargesDto.class));
                    mdrPlanDto.setAmexDomestic(amexDomestic);
                    mdrPlanDto.setAmexInternational(amexInternational);
                } else if (leadInformation.getBankSubventionPos() != null) {
                    logger.info("Entered Bank Mdr Subvention");
                    BankSubvention bankSubvention = leadInformation.getBankSubventionPos();
                    mdrPlanDto.setMasterPass(mapper.readValue(bankSubvention.getBankMasterPass(), MdrChargesDto.class));
                    mdrPlanDto.setDiners(mapper.readValue(bankSubvention.getBankDiners(), MdrChargesDto.class));
                    mdrPlanDto.setOnus(mapper.readValue(bankSubvention.getBankOnus(), MdrChargesDto.class));
                    mdrPlanDto.setStandardOrClassic(mapper.readValue(bankSubvention.getBankStandardOrClassic(), MdrChargesDto.class));
                    mdrPlanDto.setDomesticDebitUpTo2000(mapper.readValue(bankSubvention.getBankDomesticDebitUpTo2000(), MdrChargesDto.class));
                    mdrPlanDto.setDomesticDebitAbove2000(mapper.readValue(bankSubvention.getBankDomesticDebitAbove2000(), MdrChargesDto.class));
                    mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(bankSubvention.getBankSuperPremiumOrSignature(), MdrChargesDto.class));
                    mdrPlanDto.setInternationalCreditCard(mapper.readValue(bankSubvention.getBankInternationalCreditCard(), MdrChargesDto.class));
                    mdrPlanDto.setInternationalDebitCard(mapper.readValue(bankSubvention.getBankInternationalDebitCard(), MdrChargesDto.class));
                    mdrPlanDto.setCommercialOrCorporate(mapper.readValue(bankSubvention.getBankCommercialOrCorporate(), MdrChargesDto.class));
                    mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(bankSubvention.getBankPremiumOrPlatinum(), MdrChargesDto.class));
                    mdrPlanDto.setCashAtPos(mapper.readValue(bankSubvention.getBankCashAtPos(), MdrChargesDto.class));
                    mdrPlanDto.setCode(Integer.parseInt(leadInformation.getMarsMdrCharges().getCode()));
                } else {
                    logger.info("Entered No Subvention");
                    if (leadInformation.getMarsMdrCharges() != null) {
                        mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
                        mdrPlanDto.setDiners(mapper.readValue(leadInformation.getMarsMdrCharges().getDiners(), MdrChargesDto.class));
                        mdrPlanDto.setOnus(mapper.readValue(leadInformation.getMarsMdrCharges().getOnus(), MdrChargesDto.class));
                        mdrPlanDto.setStandardOrClassic(mapper.readValue(leadInformation.getMarsMdrCharges().getStandardOrClassic(), MdrChargesDto.class));
                        mdrPlanDto.setDomesticDebitUpTo2000(mapper.readValue(leadInformation.getMarsMdrCharges().getDomesticDebitUpTo2000(), MdrChargesDto.class));
                        mdrPlanDto.setDomesticDebitAbove2000(mapper.readValue(leadInformation.getMarsMdrCharges().getDomesticDebitAbove2000(), MdrChargesDto.class));
                        mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(leadInformation.getMarsMdrCharges().getSuperPremiumOrSignature(), MdrChargesDto.class));
                        mdrPlanDto.setInternationalCreditCard(mapper.readValue(leadInformation.getMarsMdrCharges().getInternationalCreditCard(), MdrChargesDto.class));
                        mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInformation.getMarsMdrCharges().getInternationalDebitCard(), MdrChargesDto.class));
                        mdrPlanDto.setCommercialOrCorporate(mapper.readValue(leadInformation.getMarsMdrCharges().getCommercialOrCorporate(), MdrChargesDto.class));
                        mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(leadInformation.getMarsMdrCharges().getPremiumOrPlatinum(), MdrChargesDto.class));
                        mdrPlanDto.setCashAtPos(mapper.readValue(leadInformation.getMarsMdrCharges().getCashAtPos(), MdrChargesDto.class));
                        mdrPlanDto.setCode(Integer.parseInt(leadInformation.getMarsMdrCharges().getCode()));
                        mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInformation.getMarsMdrCharges().getUpiAbove2000(), MdrChargesDto.class));
                        mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInformation.getMarsMdrCharges().getUpiUpto2000(), MdrChargesDto.class));
                        mdrPlanDto.setUpiCreditAbove2000(mapper.readValue(leadInformation.getMarsMdrCharges().getUpiCreditAbove2000(), MdrChargesDto.class));
                        mdrPlanDto.setUpiCreditUpto2000(mapper.readValue(leadInformation.getMarsMdrCharges().getUpiCreditUpto2000(), MdrChargesDto.class));
                        mdrPlanDto.setAmexDomestic(mapper.readValue(leadInformation.getMarsMdrCharges().getAmexDomestic(), MdrChargesDto.class));
                        mdrPlanDto.setAmexInternational(mapper.readValue(leadInformation.getMarsMdrCharges().getAmexInternational(), MdrChargesDto.class));
                    } else {
                        logger.info("Entered No Found");
                    }
                }
                marsDto.setMdrPlan(mdrPlanDto);
                marsDto.setRemarks("");
                marsDto.setRevParameters((JSONObject) parser.parse(marsOnboardingInformation.getPaymentDetails()));
                EdcResponseDto edcData = new EdcResponseDto();
                HashMap<String, Object> dummyDto = new HashMap<>();
                dummyDto.put("merchant", marsDto);
                JSONObject jsonObject = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                HttpURLConnection conn = null;
                try {
                    URL obj = null;
//                    URL obj = new URL(marsTidUrl);
                    if (leadInformation.getOnboardInstance() != null && leadInformation.getOnboardInstance().getInstitutionCode() == 115) {
                        obj = new URL(marsYesBankAddOnUrl);
                        logger.info("URL---{}", marsYesBankAddOnUrl);
                    } else {
                        obj = new URL(marsAddOnUrl);
                        logger.info("URL---{}", marsAddOnUrl);
                    }
                    conn = (HttpURLConnection) obj.openConnection();
                    if (leadInformation.getOnboardInstance() != null) {
                        conn.setRequestProperty("NII", String.valueOf(leadInformation.getOnboardInstance().getInstitutionCode())); //need to check
                        logger.info("NII ---{}", leadInformation.getOnboardInstance().getInstitutionCode());
                    } else {
                        conn.setRequestProperty("NII", "104");
                        logger.info("NII ---{}", "104");
                    }
//                    conn.setRequestProperty("NII", "104");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    // HTTP Connection Headers completed
                    // Sending API request
                    JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                    logger.info("Mars Request Payload for Tid Creation {}", reqData);

                    OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
//                    JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                    out.write(CommonUtil.parseJson(reqData)); // sending the data
//                    logger.info("Mars Request Payload for Tid Creation {}", reqData);
                    out.close(); // closing the output stream
                    int responsecode = conn.getResponseCode();
                    String message = conn.getResponseMessage();
                    logger.info("The response code and message is {} ----{}", responsecode, message);
                    edcMarsResponseDto.setCode(conn.getResponseCode());
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("exception occurred -----------------: {}", e.getLocalizedMessage());
                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "SOMETHING WENT WRONG", e.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
                }

                if (conn.getResponseCode() == 200 || conn.getResponseCode() == 202) {
                    leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(MARS_PENDING));
                    leadInformationRepository.save(leadInformation);
                    implementationService.sendToRoleSubmit("Digital Ops", null);

                    return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
                } else {
                    Gson gson = new Gson();
                    try {
                        logger.info("-------------------Error-----------------{}", gson.toJson(conn));
                    }catch (Exception e){
                        logger.info("exception--{}",e.getMessage());
                        e.printStackTrace();
                    }
                    logger.info("-------------------InternalServerError-----------------{}", edcData.getResponseCode());
                    implementationService.sendToRoleTech("Digital Ops", null);

                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "Failure", edcData), CONFLICT);
                }

            }
            return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Lead Not Found"), BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/call-back-addOn")
    public ResponseEntity<?> callBackAddOn(@RequestBody ApplicationResponse applicationResponse) throws Exception {
        Gson gson = new Gson();
        String requestBody = gson.toJson(applicationResponse);
        logger.info("DeviceDetailsUpdateFromMars : Request Body API : ---- : {}", requestBody);
        EdcResponseDto edcData = new EdcResponseDto();
        EDCMarsResponseDto edcMarsResponseDto = new EDCMarsResponseDto();
        LeadInformation leadInformation = leadInformationRepository.getLeadByApplicationNumber(applicationResponse.getApplicationNumber());
        MarsDeviceDetails marsDeviceDetails1 = null;
        if (leadInformation != null) {
            if (applicationResponse.getResponseCode().equals("00")) {
                if (applicationResponse.getData().isEmpty()) {
                    return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "data Not Fount", applicationResponse), BAD_REQUEST);
                }
                for (TerminalDetails terminalDetails : applicationResponse.getData()) {
                    edcData.setTerminalId(terminalDetails.getTerminalId());
                    edcData.setMerchantId(terminalDetails.getMerchantId());
                    edcData.setApplicationNumber(applicationResponse.getApplicationNumber());
                    edcData.setMerchantRefCode((applicationResponse.getMerchantRefCode()) != null ? Long.parseLong(applicationResponse.getMerchantRefCode()) : null);
                    if (applicationResponse.getInstitutionCode().equals("110")) {
                        edcData.setVpa(terminalDetails.getVpa());
                    }
                    edcMarsResponseDto.setData(edcData);
                    if (edcMarsResponseDto.getData().getTerminalId() != null && edcMarsResponseDto.getData().getMerchantId() != null) {
                        MarsDeviceDetails marsDeviceDetail = marsDeviceDetailsRepository.findTid(edcMarsResponseDto.getData().getTerminalId());
                        logger.info("MarsDeviceDetails ---- : {}", marsDeviceDetail);
                        if (marsDeviceDetail == null) {
                            logger.info("------ENTERED---");

                            if (applicationResponse.getInstitutionCode().equals("104") || applicationResponse.getInstitutionCode().equals("115")) {
                                marsDeviceDetails1 = implementationService.saveDatasToMarsAndLeadTableAddOnPos(implementationService.setValuesToDto(edcMarsResponseDto));
                                MarsDeviceDetails finalMarsDeviceDetails = marsDeviceDetails1;
                                if (!leadInformation.getLeadSource().getLeadSourceName().equals("LS_Wowpe")) {
                                    CompletableFuture.runAsync(() -> {
                                        try {
                                            logger.info("-Webbackend triggered-");
                                            implementationService.updateLeadAndMarsData(finalMarsDeviceDetails);
                                        } catch (ExecutionException | InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    String reason = "TID Created";
                                    String title = "TID Created for POS";
                                    sendPushNotificationConsentLink(title, reason, leadInformation, PUSH_NOTIFICATION_TO_TID_CREATE_POS);
                                }
                                List<MarsDeviceDetails> marsDeviceDetails = marsDeviceDetailsRepository.findLeadPOS(marsDeviceDetails1.getLeadInformation());
                                marsDeviceDetails.forEach(l -> {
                                    l.setScheduleDate(DateUtil.currentDate());
                                    marsDeviceDetailsRepository.save(l);
                                });
                                if (leadInformation.getLeadSource().getLeadSourceName().equals("LS_Wowpe")) {
                                    CompletableFuture.runAsync(() -> {
                                        implementationService.updateLeadAndMarsDataToOnboardMulti(finalMarsDeviceDetails);
                                    });
                                    mailHandler.sendEmails("TID CREATED |" + leadInformation.getLeadNumber(), leadInformation.getLeadNumber());
                                }
                                logger.info("Schedule date set --");
                            } else if (applicationResponse.getInstitutionCode().equals("110")) {
                                marsDeviceDetails1 = implementationService.saveDatasToMarsAndLeadTableAddOn(implementationService.setValuesToDto(edcMarsResponseDto));
                                MarsDeviceDetails finalMarsDeviceDetails1 = marsDeviceDetails1;
                                if (marsDeviceDetails1.getDeviceStatus() == MARS_DEVICE_STATUS_TID_GENERATED) {
                                    marsDeviceDetails1.setImplDeviceCompleteDate(DateUtil.currentDate());
                                    marsDeviceDetailsRepository.save(marsDeviceDetails1);
                                    logger.info("date {}", marsDeviceDetails1.getImplDeviceCompleteDate());
                                }
                                CompletableFuture.runAsync(() -> {
                                    logger.info("-Webbackend triggered-");
                                    logger.info("-mars Device Detail ID---{}", finalMarsDeviceDetails1.getId());
                                    implementationService.updateLeadAndMarsDataFromDigiOnboardSqr(finalMarsDeviceDetails1);
                                });
                                String reason = "TID Created";
                                String title = "TID Created for QR";
                                sendPushNotificationConsentLink(title, reason, leadInformation, PUSH_NOTIFICATION_TO_TID_CREATE_QR);
                                List<MarsDeviceDetails> marsDeviceDetails = marsDeviceDetailsRepository.findLeadQr(leadInformation.getLeadId());
                                marsDeviceDetails.forEach(l -> {
                                    l.setScheduleDate(DateUtil.currentDate());
                                    marsDeviceDetailsRepository.save(l);
                                });
                            } else {
                                logger.info("Institution Code Not fount--{}", applicationResponse.getInstitutionCode());
                                return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "institution code not fount"), BAD_REQUEST);
                            }
                        }
                    }
                }
            } else if ("115".equals(applicationResponse.getInstitutionCode())) {

                String responseCode = applicationResponse.getResponseCode();

                Integer leadStatusId = null;
                String message = null;

                switch (responseCode) {
                    case "100" -> {
                        logger.info("-------------------HITACHI_CREATE_MERCHANT_FAILED-----------------{}", responseCode);
                        leadStatusId = HITACHI_CREATE_MERCHANT_FAILED;
                        message = "Hitachi Create merchant API";
                    }
                    case "101" -> {
                        logger.info("-------------------UPI_SUBMERCHANT_FAILED-----------------{}", responseCode);
                        leadStatusId = UPI_SUBMERCHANT_FAILED;
                        message = "UPI Submerchant API";
                    }
                    case "102" -> {
                        logger.info("-------------------UPI_ENABLE_REFID_FAILED-----------------{}", responseCode);
                        leadStatusId = UPI_ENABLE_REFID_FAILED;
                        message = "UPI Enable REFID";
                    }
                    case "103" -> {
                        logger.info("-------------------JUPITER_ONBOARDING_FAILED-----------------{}", responseCode);
                        leadStatusId = JUPITER_ONBOARDING_FAILED;
                        message = "Jupiter Onbaording";
                    }
                    case "104" -> {
                        logger.info("-------------------BENEFICIARY_FETCH_FAILED-----------------{}", responseCode);
                        leadStatusId = BENEFICIARY_FETCH_FAILED;
                        message = " Beneficiary Fetch";
                    }
                    case "105" -> {
                        logger.info("-------------------BENEFICIARY_MANAGEMENT_FAILED-----------------{}", responseCode);
                        leadStatusId = BENEFICIARY_MANAGEMENT_FAILED;
                        message = "Beneficiary Management";
                    }
                    case "106" -> {
                        logger.info("-------------------BENEFICIARY_VALIDATE_FAILED-----------------{}", responseCode);
                        leadStatusId = BENEFICIARY_VALIDATE_FAILED;
                        message = "Beneficiary Validate";
                    }
                    default -> {
                    }
                }

                if (leadStatusId != null) {
                    leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(leadStatusId));
                    leadInformation.setActive(false);
                    leadInformation.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                    leadInformationRepository.save(leadInformation);
                    return ResponseEntity.ok(new ApiResponse(OK, message, applicationResponse));
                }
            } else {
                if ((Objects.equals(applicationResponse.getResponseCode(), "101") && applicationResponse.getInstitutionCode().equals("104")) || (Objects.equals(applicationResponse.getResponseCode(), "102") && applicationResponse.getInstitutionCode().equals("104"))) {
                    logger.info("-------------------DMO Failed-----------------{}", applicationResponse.getResponseCode());
                    leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(DMO_API_FAILED));
                    leadInformation.setActive(false);
                    leadInformation.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                    leadInformationRepository.save(leadInformation);
                    return new ResponseEntity<>(new ApiResponse(OK, "DMO Failed", applicationResponse), OK);

                } else if (Objects.equals(applicationResponse.getResponseCode(), "100")) {
                    logger.info("-------------------Host Failed-----------------{}", applicationResponse.getResponseCode());
                    leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(HOST_API_FAILED));
                    leadInformation.setActive(false);
                    leadInformation.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                    leadInformationRepository.save(leadInformation);
                    return new ResponseEntity<>(new ApiResponse(OK, "Host Failed", applicationResponse), OK);

                } else if (Objects.equals(applicationResponse.getResponseCode(), "103")) {
                    logger.info("-------------------OGS Failed-----------------{}", applicationResponse.getResponseCode());
                    leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(OGS_API_FAILED));
                    leadInformation.setActive(false);
                    leadInformation.setIsMarsApiTriggered(MARS_API_NOT_TRIGGERED);
                    leadInformationRepository.save(leadInformation);
                    return new ResponseEntity<>(new ApiResponse(OK, "OGS Failed-", applicationResponse), OK);

                } else if (Objects.equals(applicationResponse.getResponseCode(), "101") && applicationResponse.getInstitutionCode().equals("110")) {
                    logger.info("responseee 101 ----> {}", applicationResponse.getResponseCode());
                    logger.info("-leadStatus ----> {}", leadStatusRepository.fetchLeadStatusById(SQR_ONBOARDING_FAILED));
                    leadInformation.setActive(false);
                    leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(SQR_ONBOARDING_FAILED));
                    leadInformationRepository.save(leadInformation);
                    return new ResponseEntity<>(new ApiResponse(OK, "DMO Failed", applicationResponse), OK);
                }
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "Application Number Not Found", applicationResponse), NOT_FOUND);
        }
//        if (applicationResponse.getInstitutionCode().equals("104")) {
//            int posTidCount = marsDeviceDetailsRepository.findCount(leadInformation.getLeadId());
//            if (posTidCount == leadInformation.getDeviceCount()) {
//                List<MarsDeviceDetails> marsDeviceDetail = marsDeviceDetailsRepository.findLeadPOS(leadInformation);
//                for (MarsDeviceDetails marsDeviceDetails : marsDeviceDetail) {
//                    if (marsDeviceDetails.getDeviceStatus() == MARS_DEVICE_STATUS_SCAN_PICKED || marsDeviceDetails.getDeviceStatus() == MARS_DEVICE_STATUS_TID_GENERATED && marsDeviceDetails.getSim() != null) {
//                        leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(IMPL_PENDING_SCHEDULE_VISIT));
//                        leadInformationRepository.save(leadInformation);
//                        logger.info("Status {}", leadInformation.getLeadStatus());
//                        marsDeviceDetails.setImplDeviceCompleteDate(DateUtil.currentDate());
//                        marsDeviceDetailsRepository.save(marsDeviceDetails);
//                        logger.info("date {}", marsDeviceDetails.getImplDeviceCompleteDate());
//                    }
//                }
//            }
//        }
        if (applicationResponse.getInstitutionCode().equals("110")) {
////            MarsDeviceDetails marsDeviceDetails = marsDeviceDetailsRepository.findVpa(marsDeviceDetails1.getVpa());
//            if (marsDeviceDetails1.getDeviceStatus() == MARS_DEVICE_STATUS_TID_GENERATED) {
//                marsDeviceDetails1.setImplDeviceCompleteDate(DateUtil.currentDate());
//                marsDeviceDetailsRepository.save(marsDeviceDetails1);
//                logger.info("date {}", marsDeviceDetails1.getImplDeviceCompleteDate());
//            }

            int qrTidCount = marsDeviceDetailsRepository.findCount(leadInformation.getLeadId());
            logger.info("counts--{}---tid--{}", leadInformation.getQrCount(), qrTidCount);
            if (leadInformation.getQrCount() == qrTidCount) {
                leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(IMPL_PENDING_SCHEDULE_VISIT));
                MarsDeviceDetails marsDeviceDetail = marsDeviceDetailsRepository.findByList(leadInformation.getLeadId());
                leadInformationRepository.save(leadInformation);
                if (leadInformation.isSoundBox()) {
                    CompletableFuture.runAsync(() -> {
                        logger.info("TMS TRIGGERED----");
                        implementationService.tmsOnboardAddOn(marsDeviceDetail, implementationService.setValuesToDto(edcMarsResponseDto));
                    });
                }
                if (leadInformation.isGeeNeePos()) {
                    CompletableFuture.runAsync(() -> {
                        logger.info("Q161 TMS TRIGGERED----");
                        MarsDeviceDetails marsDeviceDetails = marsDeviceDetailsRepository.findLead(leadInformation.getLeadId());
                        implementationService.tmsOnboardQ161(marsDeviceDetails, marsDeviceDetail, implementationService.setValuesToDto(edcMarsResponseDto));
                    });
                }
            }
        }

        return new ResponseEntity<>(new ApiResponse(OK, "Success", applicationResponse), OK);
    }

    @PostMapping("/statusUpdate/{leadId}")
    public ResponseEntity<?> updateStatus(HttpServletRequest request, @PathVariable("leadId") long leadId) {
        String authtoken = request.getHeader("Authorization");
        User curuser = getSessionUser();
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            LeadInformation leadInformation = leadInformationRepository.findLeadId(leadId);
            if (leadInformation != null) {
                leadInformation.setCounterCompleted(true);
                leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(IMPL_PENDING_SCHEDULE_VISIT));
                leadInformationRepository.save(leadInformation);
                return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
            }
            return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "lead not fount"), NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse(UNAUTHORIZED, "unAuthorized"), UNAUTHORIZED);
    }

    @PostMapping(value = {"/counter-mangement/{counterNo}/{leadId}", "/counter-mangement/{counterNo}/{leadId}/{tid}"})
    public ResponseEntity<?> counterManagement(HttpServletRequest request, @PathVariable("counterNo") int counterNo, @PathVariable("leadId") long leadId, @PathVariable("tid") Optional<String> tid, @RequestBody CounterManagementRequest counterManagementRequest) {
        String authtoken = request.getHeader("Authorization");
        User curuser = getSessionUser();
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            LeadInformation leadInformation = leadInformationRepository.findLead(leadId);
            if (leadInformation != null) {
                CounterManagement counterManagement = new CounterManagement();
                counterManagement.setCounterNo(counterNo);
                counterManagement.setLeadInformation(leadInformation);
                counterManagement.setSim(counterManagementRequest.getSim());
                counterManagement.setVpa(counterManagementRequest.getVpa());
                counterManagement.setDeviceSerialNo(counterManagementRequest.getDeviceSerialNo());
                counterManagement.setSoundBoxSno(counterManagementRequest.getSoundBoxSno());
                counterManagement.setDeviceType(counterManagementRequest.getDeviceType());
                counterManagement.setNetworkType(counterManagementRequest.getNetworkType());
                counterManagement.setTid(tid.orElse(null));
                CounterManagement counterManagement1 = counterManagementRepository.save(counterManagement);
                return new ResponseEntity<>(new ApiResponse(OK, "success", counterManagement1), OK);
            }
            return new ResponseEntity<>(new ApiResponse(CONFLICT, "lead not found"), CONFLICT);
        }
        return new ResponseEntity<>(new ApiResponse(UNAUTHORIZED, "unAuthorized"), UNAUTHORIZED);

    }

    @GetMapping("/get-counter-management/{leadId}")
    private ResponseEntity<?> getCounterManagement(HttpServletRequest request, @PathVariable("leadId") long leadId) {
        String authtoken = request.getHeader("Authorization");
        User curuser = getSessionUser();
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            List<CounterDto> counterManagement = leadInformationService.listOfCounter(leadId);
            return new ResponseEntity<>(new ApiResponse(OK, "success", counterManagement), OK);
        }
        return new ResponseEntity<>(new ApiResponse(UNAUTHORIZED, "unAuthorized"), UNAUTHORIZED);
    }

    @PutMapping("/de-activate-vpa/{leadId}")
    public ResponseEntity<?> deActivateVpa(HttpServletRequest request, @PathVariable("leadId") long leadId, @RequestBody DeactivateDto dto) throws IOException, ParseException {
        String authtoken = request.getHeader("Authorization");
        User curuser = getSessionUser();
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            LeadInformation leadData = leadInformationRepository.findLeadId(leadId);
            //device type change
            String device = dto.getDevice();
            if (dto.getDevice() != null) {
                if (leadData.isGeeNeePos()) {
                    // Hard code because SAT had different Device Name
                    dto.setDevice("Q161_PRO_SQR");
                } else {
                    dto.setDevice(dto.getDevice().equals("Pos")
                            ? leadData.getDevice().getDeviceName()
                            : "Q181-SOUND BOX"
                    );
                }
            }
            DeactivateDto deactivateDto = new DeactivateDto();
            deactivateDto.setVpa(dto.getVpa());
            deactivateDto.setDevice(dto.getDevice());
            logger.info("type of device--{}", deactivateDto.getDevice());
            deactivateDto.setSerialNumber(dto.getSerialNumber());
            ResponseEntity<String> out = null;
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DeactivateDto> entity = new HttpEntity<>(deactivateDto, httpHeaders);
            String url = ReScanPickUpdateUrl;
            try {
                out = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                int responseCode = out.getStatusCode().value();
                String responseBody = out.getBody();
                logger.info("response code ----------- :{} , response out -------- : {}", responseCode, responseBody);
                int responsecode = out.getStatusCodeValue();
                logger.info("The response code is {}", out.getBody());
                String message = out.getBody();

                logger.info("The response code is {}", responsecode);
                logger.info("The response message is {}", message);

                if (out.getStatusCodeValue() == 400 || out.getStatusCodeValue() == 404) {
                    return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "VPA OR Device Not found"), BAD_REQUEST);
                } else if (out.getStatusCodeValue() == 500) {
                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Connection failed"), INTERNAL_SERVER_ERROR);
                } else if (out.getStatusCodeValue() == 200) {
                    if (leadData != null) {
                        if (dto.getVpa() != null) {
                            for (String sqrVpa : dto.getVpa()) {
                                vpaQrRepository.deleteVpa(sqrVpa);

                                String vpa = "%" + sqrVpa + "%";
                                logger.info("vvppaa---{}", vpa);
                                CounterManagement counterManagement = counterManagementRepository.findByVpa(vpa, leadData.getLeadId());
                                if (counterManagement == null) {
                                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "counter Not Fount"), CONFLICT);
                                }
                                logger.info("deviceVpa--{}", counterManagement.toString());
                                String qrVpa = counterManagement.getVpa();


                                org.json.JSONArray jsonArray = new org.json.JSONArray(qrVpa);
                                List<String> str = new ArrayList<>();
                                String[] elements = new String[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    elements[i] = jsonArray.getString(i);
                                }
                                for (String element : elements) {
                                    if (!Objects.equals(element, sqrVpa)) {
                                        logger.info("elements-----------------------{}", element);
                                        str.add(element);
                                    }
                                }
                                org.json.JSONArray result = new org.json.JSONArray(str);
                                counterManagement.setVpa(String.valueOf(result));
                                logger.info("result-----{}", result);
                                CounterManagement counterManagement1 = counterManagementRepository.save(counterManagement);
                                logger.info("deviceVpa---{}", counterManagement1);
                            }

                        }
                        if (dto.getSerialNumber() != null) {
                            logger.info("device---{}", dto.getDevice());
                            if (device.equals("Pos") || leadData.isGeeNeePos()) {
                                CounterManagement counterManagement = counterManagementRepository.findBySerialNoDevice(dto.getSerialNumber(), leadData.getLeadId());
                                if (counterManagement == null) {
                                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "counter Not Fount"), CONFLICT);
                                }
                                counterManagement.setDeviceSerialNo(null);
                                CounterManagement counterManagement1 = counterManagementRepository.save(counterManagement);
                                logger.info("deviceA75---{}", counterManagement1.toString());
                            } else {
                                logger.info("deviceQ181---{}", dto.getDevice());
                                CounterManagement counterManagement = counterManagementRepository.findBySerialNo(dto.getSerialNumber(), leadData.getLeadId());
                                if (counterManagement == null) {
                                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "counter Not Fount"), CONFLICT);
                                }
                                counterManagement.setSoundBoxSno(null);
                                CounterManagement counterManagement1 = counterManagementRepository.save(counterManagement);
                                logger.info("deviceQ181---{}", counterManagement1.toString());
                            }
                        }
                        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success"), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Lead not found"), BAD_REQUEST);
                    }
                }
                return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "someThing Went Wrong"), BAD_REQUEST);
            } catch (HttpClientErrorException httpClientErrorException) {
                int responseCode = httpClientErrorException.getStatusCode().value();
                String clientErrorValue = httpClientErrorException.getResponseBodyAsString();
                logger.info("response code-------------- : {} , client Error Response --------------- :{}", responseCode, clientErrorValue);
                return new ResponseEntity<>(new ApiResponse(CONFLICT, "someThing Went Wrong"), CONFLICT);
            } catch (HttpServerErrorException httpServerErrorException) {
                int responseCode = httpServerErrorException.getStatusCode().value();
                String serverErrorResponse = httpServerErrorException.getResponseBodyAsString();
                logger.info("response code --------- : {}, server error response --------- : {}", responseCode, serverErrorResponse);
                return new ResponseEntity<>(new ApiResponse(CONFLICT, "someThing Went Wrong"), CONFLICT);
            }
        }
        return new ResponseEntity<>(new ApiResponse(UNAUTHORIZED, "unAuthorized"), UNAUTHORIZED);
    }

    @PutMapping("/update-counter-management/{leadId}/{id}")
    public ResponseEntity<?> updateCounter(HttpServletRequest request, @PathVariable("leadId") long leadId, @PathVariable("id") long id, @RequestBody CounterManagementRequest counterManagementRequest) {
        String authtoken = request.getHeader("Authorization");
        User curuser = getSessionUser();
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            CounterManagement counterManagement = counterManagementRepository.findByIds(id, leadId);
            if (counterManagement != null) {
                counterManagement.setVpa((counterManagementRequest.getVpa()) != null ? counterManagementRequest.getVpa() : counterManagement.getVpa());
                counterManagement.setDeviceSerialNo((counterManagementRequest.getDeviceSerialNo()) != null ? counterManagementRequest.getDeviceSerialNo() : counterManagement.getDeviceSerialNo());
                counterManagement.setSoundBoxSno((counterManagementRequest.getSoundBoxSno()) != null ? counterManagementRequest.getSoundBoxSno() : counterManagement.getSoundBoxSno());
                counterManagement.setSim((counterManagementRequest.getSim()) != null ? counterManagementRequest.getSim() : counterManagement.getSim());
                counterManagement.setNetworkType((counterManagementRequest.getNetworkType()) != null ? counterManagementRequest.getNetworkType() : counterManagement.getNetworkType());
                CounterManagement counterManagement1 = counterManagementRepository.save(counterManagement);
                return new ResponseEntity<>(new ApiResponse(OK, "success", counterManagement1), OK);
            }
            return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "not found"), BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(UNAUTHORIZED, "unAuthorized"), UNAUTHORIZED);
    }

    @DeleteMapping("delete-counter/{id}")
    public ResponseEntity<?> deleteCounter(HttpServletRequest request, @PathVariable("id") long id) {
        String authtoken = request.getHeader("Authorization");
        User curuser = getSessionUser();
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            counterManagementRepository.findId(id);
            return new ResponseEntity<>(new ApiResponse(OK, "successfully Deleted"), OK);
        }
        return new ResponseEntity<>(new ApiResponse(UNAUTHORIZED, "unAuthorized"), UNAUTHORIZED);
    }

    @PutMapping("/settlement-addOn/{leadId}")
    public ResponseEntity<?> leadUpdateAddOn(HttpServletRequest request, @PathVariable("leadId") long leadId, @RequestBody LeadInformation leadInformation) throws Exception {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            logger.info("Approval process");
            LeadInformation leadInformation1 = implementationService.checkLead(leadId);
            if (leadInformation1 != null) {
                Pair<Integer, Object> lead = implementationService.leadApprovalAddOn(leadId, leadInformation);
                logger.info("get first : {}", lead.getFirst());
                if (lead.getFirst() == 1) {
                    LeadStatus status = leadStatusRepository.fetchLeadStatusById(LEAD_COMPLETED);
                    leadInformation1.setLeadStatus(status);
                    leadInformation1.setLeadApprovedBy(getSessionUser());
                    leadInformation1.setLeadApprovedDate(DateUtil.currentDate());
                    LeadInformation leads = leadInformationRepository.save(leadInformation1);
                    logger.info("check status settlement -- : {}", leads.getLeadStatus().getLeadStatusName());
                    logger.info("success flow -- : {}", lead.getFirst());
                    return new ResponseEntity<>(new ApiResponse(OK, "success", lead.getSecond()), OK);
                } else if (lead.getFirst() == 0) {
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "documents not verified"), CONFLICT);
                } else {
                    return new ResponseEntity<>(new ApiResponse(CONFLICT, "Something went wrong! Please Try Again"), CONFLICT);
                }
            }
            return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "lead not found"), NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/merchantDeActivation/{leadId}")
    public ResponseEntity<?> merchantDeactivation(HttpServletRequest request, @PathVariable("leadId") long leadId) {
        String authToken = request.getHeader("Authorized");
        User user = getSessionUser();
        logger.info("user--{}", user.getName());
        UserAuthentication userAuthentication = userAuthenticateService.getToken(user.getId());
        if (authToken.equals(userAuthentication.getToken())) {

            Pair<String, Object> pair = implementationService.merchantDeactivation(leadId);
            if (pair.getFirst().equals("1")) {
                return new ResponseEntity<>(new ApiResponse(OK, "success"), CONFLICT);
            }
        }

        return new ResponseEntity<>(new ApiResponse(CONFLICT, "something went wrong"), CONFLICT);
    }

    @PutMapping("/leadReject-addOn/{leadId}/{action}")
    public ResponseEntity<?> leadRejectAddOn(HttpServletRequest request, @PathVariable("leadId") long leadId, @PathVariable("action") long action, @RequestBody LeadInformation leadInformation) throws Exception {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            logger.info("Approval process");
            LeadInformation lead = implementationService.check(leadId);
            if (lead != null) {
                Pair<Integer, Object> leadInfo = implementationService.leadRejectAddOn(leadId, action, leadInformation);
                if (leadInfo.getFirst() == 1) {
                    LeadStatus status = leadStatusRepository.fetchLeadStatusById(REJECTED_BY_OPS);
                    lead.setLeadStatus(status);
                    lead.setActive(false);
                    lead.setLeadRejectBy(getSessionUser());
                    lead.setLeadRejectedDate(DateUtil.currentDate());
                    lead.setOpsRejectedRemarks(leadInformation.getOpsRejectedRemarks());
                    LeadInformation leads = leadInformationRepository.save(lead);
                    return new ResponseEntity<>(new ApiResponse(OK, "success", leads), OK);
                } else if (leadInfo.getFirst() == 2) {
                    return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Something went wrong"), BAD_REQUEST);
                } else if (leadInfo.getFirst() == 3) {
                    return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Technical Error! Please Try Again"), INTERNAL_SERVER_ERROR);
                }
                return new ResponseEntity<>(new ApiResponse(CONFLICT, "Lead not Available"), CONFLICT);
            }
            return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "lead not found"), NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/retryAddOn/{applicationNo}")
    public ResponseEntity<?> retryApi(@PathVariable("applicationNo") String applicationNo) {

        int institution = 0;
        String url = null;
        LeadInformation leadInformation1 = leadInformationRepository.getLeadByApplicationNumber(applicationNo);
        if (leadInformation1.getOnboardInstance() != null) {
            institution = (int) leadInformation1.getOnboardInstance().getInstitutionCode();
        } else {
            institution = 104;
        }
        RetryMultiTid retryMultiTid = new RetryMultiTid();
        retryMultiTid.setApplicationNumber(applicationNo);
        retryMultiTid.setInsCode(institution);
        HttpHeaders headers = new HttpHeaders();
        headers.set("NII", String.valueOf(institution));
        HttpEntity<RetryMultiTid> entity = new HttpEntity<>(retryMultiTid, headers);
        if (leadInformation1.getOnboardInstance() != null && leadInformation1.getOnboardInstance().getInstitutionCode() == 115) {
            url = marsAddOnRetryUrlYesBank;
        } else {
            url = marsAddOnRetryUrl;
        }
        Gson g = new Gson();
        String data = g.toJson(retryMultiTid);
        logger.info("retry Api Payload--{}", data);
        try {
            ResponseEntity<String> out = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (out.getStatusCodeValue() == 200) {
                logger.info("success ----{}", out.getStatusCodeValue());
                return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
            } else {
                return new ResponseEntity<>(new ApiResponse(CONFLICT, "something went wrong"), CONFLICT);
            }
        } catch (HttpClientErrorException httpClientErrorException) {
            int responseCode = httpClientErrorException.getStatusCode().value();
            String clientError = httpClientErrorException.getResponseBodyAsString();
            logger.info("responseCode ----{},clientError---{}", responseCode, clientError);
            return new ResponseEntity<>(new ApiResponse(CONFLICT, "something went wrong"), CONFLICT);
        } catch (HttpServerErrorException httpServerErrorException) {
            int responseCode = httpServerErrorException.getStatusCode().value();
            String serverError = httpServerErrorException.getResponseBodyAsString();
            logger.info("responseCode---{},serverError---{}", responseCode, serverError);
            return new ResponseEntity<>(new ApiResponse(CONFLICT, "something went wrong"), CONFLICT);
        }
    }

    @PutMapping("/retryAddOnQr/{applicationNo}")
    public ResponseEntity<?> retryApiQr(@PathVariable("applicationNo") String applicationNo) {
        RetryMultiTid retryMultiTid = new RetryMultiTid();
        retryMultiTid.setApplicationNumber(applicationNo);
        retryMultiTid.setInsCode(110);
        HttpHeaders headers = new HttpHeaders();
        headers.set("NII", "110");
        HttpEntity<RetryMultiTid> entity = new HttpEntity<>(retryMultiTid, headers);
        String url = qrMarsAddOnRetryUrl;
        Gson g = new Gson();
        String data = g.toJson(retryMultiTid);
        logger.info("retry Api Payload--{}", data);
        try {
            ResponseEntity<String> out = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (out.getStatusCodeValue() == 200) {
                logger.info("success ----{}", out.getStatusCodeValue());
                return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
            } else {
                return new ResponseEntity<>(new ApiResponse(CONFLICT, "something went wrong"), CONFLICT);
            }
        } catch (HttpClientErrorException httpClientErrorException) {
            int responseCode = httpClientErrorException.getStatusCode().value();
            String clientError = httpClientErrorException.getResponseBodyAsString();
            logger.info("responseCode ----{},clientError---{}", responseCode, clientError);
            return new ResponseEntity<>(new ApiResponse(CONFLICT, "something went wrong"), CONFLICT);
        } catch (HttpServerErrorException httpServerErrorException) {
            int responseCode = httpServerErrorException.getStatusCode().value();
            String serverError = httpServerErrorException.getResponseBodyAsString();
            logger.info("responseCode---{},serverError---{}", responseCode, serverError);
            return new ResponseEntity<>(new ApiResponse(CONFLICT, "something went wrong"), CONFLICT);
        }
    }

    @PostMapping("/retryPOSQR/{appNoPos}")
    public ResponseEntity<?> retryPosQr(HttpServletRequest request, @PathVariable("appNoPos") String appNo) {
        String authtoken = request.getHeader("Authorization");
        logger.info("authtoken==={}", authtoken);
        User curuser = getSessionUser();
        logger.info("Session User {}", curuser);
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (authtoken.equals(userauth.getToken())) {
            boolean posFlag = true;
            boolean qrFlag = true;
            LeadInformation leadInformation = leadInformationRepository.getLeadByApplicationNumber(appNo);
            int posCount = marsDeviceDetailsRepository.findCountAllPOS(leadInformation.getLeadId());
            logger.info("POS Count --{}--{}", leadInformation.getDeviceCount(), posCount);
            if (!leadInformation.getDeviceCount().equals(posCount)) {
                logger.info("POS Retriggered-");
                ResponseEntity<?> response = retryApi(appNo);
                logger.info("POS Retriggered Response---{}", response.getStatusCodeValue());
                if (!(response.getStatusCodeValue() == 200)) {
                    posFlag = false;
                }
            }
            int qrCount = marsDeviceDetailsRepository.findCount(leadInformation.getLeadId());

            logger.info("QR Count --{}--{}", leadInformation.getQrCount(), qrCount);
            if (!leadInformation.getQrCount().equals(qrCount) && leadInformation.getDeviceCount().equals(posCount)) {  //QR retry Api call only after POS sucess
                logger.info("QR Retriggered-");
                ResponseEntity<?> response = retryApiQr(appNo);
                logger.info("QR Retriggered Response---{}", response.getStatusCodeValue());
                if (!(response.getStatusCodeValue() == 200)) {
                    qrFlag = false;
                }
            }
            if (posFlag && qrFlag) {
                return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
            } else {
                return new ResponseEntity<>(new ApiResponse(CONFLICT, "something went wrong"), CONFLICT);
            }
        }
        return new ResponseEntity<>(new ApiResponse(UNAUTHORIZED, "unauthorized"), UNAUTHORIZED);
    }

    @PostMapping("/consentUpdate/{leadId}/{action}")
    public ResponseEntity<?> consentUpdate(@RequestBody ConsentDTO consentDTO, @PathVariable("leadId") long leadId, @PathVariable("action") int action) {
        LeadInformation leadInformation = leadInformationRepository.findLeadId(leadId);
        if (leadInformation != null) {
            Gson g = new Gson();
            String gson = g.toJson(consentDTO);
            logger.info("gson{}", gson);
            if (action == 1) {
                OtpDTO otpDTO1 = new OtpDTO();
                otpDTO1.setCmd("generate otp");
                otpDTO1.setMerchantVerify(leadInformation.getContactEmail());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<OtpDTO> entity = new HttpEntity<>(otpDTO1, headers);
                String url = sendSms + leadInformation.getContactNumber();
                logger.info("url sms -{}", url);
                try {
                    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                    if (response.getStatusCodeValue() != 200) {
                        return new ResponseEntity<>(new ApiResponse(CONFLICT, "Sms Otp Failed"), CONFLICT);
                    } else if (response.getStatusCodeValue() == 400) {
                        return new ResponseEntity<>(new ApiResponse(CONFLICT, "failed"), CONFLICT);
                    }
                } catch (HttpClientErrorException | HttpServerErrorException e) {
                    String exception = e.getResponseBodyAsString();
                    logger.info("httpError--{}", exception);
                    Gson gson1 = new Gson();
                    ApiResponse apiResponse = gson1.fromJson(exception, ApiResponse.class);
                    return new ResponseEntity<>(new ApiResponse(OK, apiResponse.getMessage()), OK);
                }
                OtpDTO otpDTO = new OtpDTO();
                otpDTO.setCmd("generate otp");
                otpDTO.setMerchantVerify(leadInformation.getContactEmail());
                HttpEntity<OtpDTO> entity1 = new HttpEntity<>(otpDTO, headers);
                String url1 = sendMail + leadInformation.getContactEmail();
                logger.info("url mail -{}", url1);
                try {
                    ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.POST, entity1, String.class);
                    if (response1.getStatusCodeValue() != 200) {
                        return new ResponseEntity<>(new ApiResponse(CONFLICT, "Email Otp Failed"), CONFLICT);
                    }
                } catch (HttpClientErrorException | HttpServerErrorException e) {
                    String exception = e.getResponseBodyAsString();
                    logger.info("httpError--{}", exception);
                    Gson gson1 = new Gson();
                    ApiResponse apiResponse = gson1.fromJson(exception, ApiResponse.class);
                    return new ResponseEntity<>(apiResponse, CONFLICT);
                }
            }
            ConsentVerification consentDTO1 = consentVerificationRepository.getConsentDetailsById(leadId);
            if (consentDTO1 != null) {
                if (action == 1) {
                    consentDTO1.setIpAddress(consentDTO.getIpAddress());
                    consentDTO1.setLat(consentDTO.getLat());
                    consentDTO1.setLang(consentDTO.getLang());
                    consentDTO1.setSystemDate(consentDTO.getSystemDate());
                    consentDTO1.setLocalDate(DateUtil.currentDate());
                    consentDTO1.setLeadId(leadInformation);
                } else if (action == 2) {
                    consentDTO1.setIpAddressPh(consentDTO.getIpAddressPh());
                    consentDTO1.setLatPh(consentDTO.getLatPh());
                    consentDTO1.setLangPn(consentDTO.getLangPh());
                    consentDTO1.setSystemDatePh(consentDTO.getSystemDatePh());
                    consentDTO1.setLocalDatePh(DateUtil.currentDate());
                    consentDTO1.setVerifiedPh(true);
                } else if (action == 3) {
                    consentDTO1.setIpAddressEmail(consentDTO.getIpAddressEmail());
                    consentDTO1.setLangEmail(consentDTO.getLangEmail());
                    consentDTO1.setLatEmail(consentDTO.getLatEmail());
                    consentDTO1.setLocalDateEmail(DateUtil.currentDate());
                    consentDTO1.setSystemDateEmail(consentDTO.getSystemDateEmail());
                    consentDTO1.setVerifiedEmail(true);
                }
                ConsentVerification consentVerification = consentVerificationRepository.save(consentDTO1);
                return new ResponseEntity<>(new ApiResponse(OK, "success", consentVerification), OK);

            } else {
                ConsentVerification consentDTO11 = new ConsentVerification();
                if (action == 1) {
                    consentDTO11.setIpAddress(consentDTO.getIpAddress());
                    consentDTO11.setLat(consentDTO.getLat());
                    consentDTO11.setLang(consentDTO.getLang());
                    consentDTO11.setSystemDate(consentDTO.getSystemDate());
                    consentDTO11.setLocalDate(DateUtil.currentDate());
                    consentDTO11.setLeadId(leadInformation);
                } else if (action == 2) {
                    consentDTO11.setIpAddressPh(consentDTO.getIpAddressPh());
                    consentDTO11.setLatPh(consentDTO.getLatPh());
                    consentDTO11.setLangPn(consentDTO.getLangPh());
                    consentDTO11.setSystemDatePh(consentDTO.getSystemDatePh());
                    consentDTO11.setLocalDatePh(DateUtil.currentDate());
                } else if (action == 3) {
                    consentDTO11.setIpAddressEmail(consentDTO.getIpAddressEmail());
                    consentDTO11.setLangEmail(consentDTO.getLangEmail());
                    consentDTO11.setLatEmail(consentDTO.getLatEmail());
                    consentDTO11.setLocalDateEmail(DateUtil.currentDate());
                    consentDTO11.setSystemDateEmail(consentDTO.getSystemDateEmail());
                }
                ConsentVerification consentVerification = consentVerificationRepository.save(consentDTO11);
                return new ResponseEntity<>(new ApiResponse(OK, "success", consentVerification), OK);
            }
        }
        return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "leadId not found"), BAD_REQUEST);
    }
//new changes digi
    @PostMapping("/consent-link-send/{email}/{phNo}/{leadId}")
    public ResponseEntity<?> consentLink(@PathVariable("email") String email, @PathVariable("phNo") String phNo, @PathVariable("leadId") long leadId) {
        LeadInformation leadInformation = leadInformationRepository.findLeadId(leadId);
        leadInformation.setContactNumber(phNo);
        leadInformation.setContactEmail(email);
        LeadInformation lead = leadInformationRepository.save(leadInformation);

        // REST calls for SMS OTP and Email OTP are commented out
        // because Email Verification is handled via Vyapar App.
//        OtpDTO otpDTO1 = new OtpDTO();
//        otpDTO1.setCmd("generate otp");
//        otpDTO1.setMerchantVerify(email);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<OtpDTO> entity = new HttpEntity<>(otpDTO1, headers);
//        String url = sendSms + phNo;
//        logger.info("url sms -{}", url);
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//            if (response.getStatusCodeValue() != 200) {
//                return new ResponseEntity<>(new ApiResponse(CONFLICT, "Sms Otp Failed"), CONFLICT);
//            } else if (response.getStatusCodeValue() == 400) {
//                return new ResponseEntity<>(new ApiResponse(CONFLICT, "failed"), CONFLICT);
//            }
//        } catch (HttpClientErrorException | HttpServerErrorException e) {
//            String exception = e.getResponseBodyAsString();
//            logger.info("httpError--{}", exception);
//            Gson gson = new Gson();
//            ApiResponse apiResponse = gson.fromJson(exception, ApiResponse.class);
//            return new ResponseEntity<>(new ApiResponse(OK, apiResponse.getMessage()), OK);
//        }
//        OtpDTO otpDTO = new OtpDTO();
//        otpDTO.setCmd("generate otp");
//        otpDTO.setMerchantVerify(email);
//        HttpEntity<OtpDTO> entity1 = new HttpEntity<>(otpDTO, headers);
//        String url1 = sendMail + email;
//        logger.info("url mail -{}", url1);
//        try {
//            ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.POST, entity1, String.class);
//            if (response1.getStatusCodeValue() != 200) {
//                return new ResponseEntity<>(new ApiResponse(CONFLICT, "Email Otp Failed"), CONFLICT);
//            }
//        } catch (HttpClientErrorException | HttpServerErrorException e) {
//            String exception = e.getResponseBodyAsString();
//            logger.info("httpError--{}", exception);
//            Gson gson = new Gson();
//            ApiResponse apiResponse = gson.fromJson(exception, ApiResponse.class);
//            return new ResponseEntity<>(apiResponse, CONFLICT);
//        }
        String merchantName = leadInformation.getMerchantName();
        long leadId1 = leadInformation.getLeadId();
//        String consentLink = "https://uatapp2.bijlipay.co.in/Consent/#/consentlink/" + leadId1;/

        String messageTemplate = "Dear " + merchantName + ", " +
                "To complete your KYC verification, please click the link below and follow the instructions:" + consentLink + leadId1 + " -Bijlipay";

        logger.info("url--{}", messageTemplate);

        String smsTemplate = "&msg=" + URLEncoder.encode(messageTemplate, StandardCharsets.UTF_8);
        sms(messageTemplate, phNo);


        return new ResponseEntity<>(new ApiResponse(OK, "sucess"), OK);
    }

    @PostMapping("/qikberry/{phNo}")
    public void qikberry(@PathVariable("phNo") String phNo) {
        String template = consentLink;
        String messageTemplate = "Dear " + "Jai" + ", " +
                "To complete your KYC verification, please click the link below and follow the instructions:" + consentLink + 1213 + " -Bijlipay";

        logger.info("qubserb--{}", messageTemplate);
        this.sms(messageTemplate, phNo);
    }

    public void sms(String smsTemplate, String phn) {
        SmsRequestDto smsRequestDto = new SmsRequestDto();
        smsRequestDto.setTo(phn);
        smsRequestDto.setSender("BIJLPY");
        smsRequestDto.setService("SI");
        smsRequestDto.setCountry_code("+91");
        smsRequestDto.setTemplate_id("1107174246963986661");
        smsRequestDto.setMessage(smsTemplate);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer 630e4cc055707c99c2055ccb1bc8549b");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SmsRequestDto> entity = new HttpEntity<>(smsRequestDto, headers);
        logger.info("request body ---------------: {}", smsRequestDto);
        logger.info("qikberry entity ----------------- : {}", entity);
        ResponseEntity<String> response = restTemplate.exchange("https://rest.qikberry.ai/v1/sms/messages", HttpMethod.POST, entity, String.class);
        logger.info("response out ---------------------: {}", response.getBody());
        logger.info("response from qikberry--{}", response.getStatusCodeValue());
        Gson gson = new Gson();
        String out = gson.toJson(response);
        logger.info("qikberry response body--{}", out);
    }


    @PutMapping("/consent-link-final/{leadId}")
    public ResponseEntity<?> consentFinal(@PathVariable("leadId") long leadId, @RequestBody ConsentDTO consentDTO) {
        LeadInformation leadInformation = leadInformationRepository.findLeadId(leadId);
        ConsentVerification consentVerification = null;
        if (leadInformation != null) {
            Gson g = new Gson();
            String gson = g.toJson(consentDTO);
            logger.info("gson{}", gson);
            ConsentVerification consentDTO1 = consentVerificationRepository.getConsentDetailsById(leadId);
            if (consentDTO1 != null) {
                consentDTO1.setIpAddress2(consentDTO.getIpAddress2());
                consentDTO1.setLat2(consentDTO.getLat2());
                consentDTO1.setLang2(consentDTO.getLang2());
                consentDTO1.setSystemDate2(consentDTO.getSystemDate2());
                consentDTO1.setLocalDate2(DateUtil.currentDate());
                consentDTO1.setLeadId(leadInformation);
                consentDTO1.setVerified(consentDTO.isVerified());
                consentVerification = consentVerificationRepository.save(consentDTO1);

            } else {
                ConsentVerification consentDTO2 = new ConsentVerification();
                consentDTO2.setIpAddress2(consentDTO.getIpAddress2());
                consentDTO2.setLat2(consentDTO.getLat2());
                consentDTO2.setLang2(consentDTO.getLang2());
                consentDTO2.setSystemDate2(consentDTO.getSystemDate2());
                consentDTO2.setLocalDate(DateUtil.currentDate());
                consentDTO2.setLeadId(leadInformation);
                consentDTO2.setVerified(consentDTO.isVerified());
                consentVerification = consentVerificationRepository.save(consentDTO2);

            }
            String reason = "consent-link";
            String title = "consent link sended";
            sendPushNotificationConsentLink(title, reason, leadInformation, PUSH_NOTIFICATION_TO_CONSENT_LINK);
            return new ResponseEntity<>(new ApiResponse(OK, "success", consentVerification), OK);
        }
        return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "leadId not found"), BAD_REQUEST);
    }

    //new changes digi
    @GetMapping("consent-details/{leadId}")
    public ResponseEntity<?> consentDetails(@PathVariable("leadId") long leadId) {
        ConsentVerification consentVerification = consentVerificationRepository.getConsentDetailsById(leadId);
        if (consentVerification != null) {
            return new ResponseEntity<>(new ApiResponse(OK, "success", consentVerification), OK);
        }
        return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "leadId not Found"), BAD_REQUEST);
    }

    @PostMapping("/validate-token/{leadId}")
    public ResponseEntity<?> generateGuestToken(HttpServletResponse response, @PathVariable("leadId") long leadId) {
        //This is work only in UAT not local to local and UAT to local
        // Generate a JWT for guest users
        LeadInformation leadInformation = leadInformationRepository.findLeadId(leadId);
        User user = leadInformation.getCreatedBy();
        String token = userAuthenticationRepo.getToken(user.getId()).getToken();
        String auth = AESUtil.encrypt(token);
        boolean isAuthenticated = util.validateToken(token, user.getEmail());
        logger.info("user is Authenticated--{}", isAuthenticated);
        // Store JWT in HTTP-only cookie
        if (isAuthenticated) {
            Cookie jwtCookie = new Cookie("jwt", auth);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
//            jwtCookie.setDomain("dashboard.bijlipay.co.in");
            response.addCookie(jwtCookie);
            return new ResponseEntity<>(new ApiResponse(OK, "Token Issued"), OK);
        }
        return new ResponseEntity<>(new ApiResponse(OK, "Token Expired"), OK);
    }

    @GetMapping("/getDetails/{leadId}")
    public ResponseEntity<?> testCookie(@PathVariable("leadId") long leadId) {
        LeadInformation leadInformation = leadInformationRepository.findLeadId(leadId);
        ImplementationDocument implementationDocument=implementationDocumentReposistory.getAllByLeads(leadId);
        if(implementationDocument!=null) {
            leadInformation.setImplementationForm(implementationDocument.getImplementationFormPicture());
        }else{
            leadInformation.setImplementationForm("");
        }
        if (leadInformation != null) {
            return new ResponseEntity<>(new ApiResponse(OK, "success", leadInformation), OK);
        }
        return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "Failed"), BAD_REQUEST);
    }

    @PostMapping("refresh-implementation")
    public ResponseEntity<?> refresh(@RequestBody ImplementationUpdate implementationUpdate) throws MessagingException {
        logger.info("status update--{}", implementationUpdate.getStatus());
        LeadInformation leadInformation = leadInformationRepository.findLeadNumber(implementationUpdate.getLeadNumber());
        if (leadInformation != null) {

            MarsDeviceDetails marsDeviceDetails = marsDeviceDetailsRepository.findTid(implementationUpdate.getTid());
            marsDeviceDetails.setDeviceStatus(implementationUpdate.getStatus());
            MarsDeviceDetails marsDeviceDetails1 = marsDeviceDetailsRepository.save(marsDeviceDetails);
            int totalOnboard = marsDeviceDetailsRepository.findCountAll(leadInformation.getLeadId());
            int totalImplemented = marsDeviceDetailsRepository.findCountAllByImpl(leadInformation.getLeadId());
            logger.info("totalOnboard--{},totalImplemented--{}", totalOnboard, totalImplemented);
            if (implementationUpdate.getStatus() == MARS_DEVICE_STATUS_IMPLEMENTED && totalOnboard == totalImplemented) {
                logger.info("marsUpdate--{}", marsDeviceDetails1.getLeadInformation().getLeadNumber());
                leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(LEAD_COMPLETED));
                leadInformationRepository.save(leadInformation);
                return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
            }
            return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
        }
        return new ResponseEntity<>(new ApiResponse(CONFLICT, "Technical Error"), CONFLICT);
    }

    @PutMapping("host-update/{leadId}")
    public ResponseEntity<?> hostUpdate(@PathVariable("leadId") long leadId, @RequestBody LeadInformation leadInformation) {
        LeadInformation leadInformation1 = leadInformationRepository.findLead(leadId);
        if (leadInformation1 != null) {
            LeadInformation leadInformations = leadInformationService.updateHostData(leadInformation1, leadInformation);
            LeadInformation leadInformation2 = leadInformationRepository.save(leadInformations);
            return new ResponseEntity<>(new ApiResponse(OK, "success", leadInformation2), OK);
        }
        return new ResponseEntity<>(new ApiResponse(CONFLICT, "Lead Not Found"), CONFLICT);
    }

    @PutMapping("/aggregator-mars-onboard-POS/{id}")
    public ResponseEntity<?> submitToMarsaggregator(HttpServletRequest request, @PathVariable("id") long id) throws Exception {
        logger.info("aggregator-mars-onboard-{}", id);
        LeadInformation leadInformation = leadInformationRepository.findLeadId(id);
        if (leadInformation != null) {
            logger.info("aggregator-Lead-{}", leadInformation.getLeadStatus().getLeadStatusName());
            logger.info("aggregator-Lead-{}", leadInformation.getLeadId());
            List<UploadedDocuments> uploadedDocumentsList = implementationService.findByLeadId(leadInformation);
            if (!uploadedDocumentsList.isEmpty()) {
                List<SignzyFetchAndVerification> panDetails = implementationService.getPanDetails(uploadedDocumentsList);
                if (!panDetails.isEmpty()) {
                    leadInformation.setPanNumber(attributeEncryptor.decrypt(panDetails.get(0).getPanNumber()));
                    leadInformationRepository.save(leadInformation);
                    logger.info("pan Number -- : {}", attributeEncryptor.decrypt(panDetails.get(0).getPanNumber()));
                }
            }

            MarsOnboardingInformation marsOnboardingInformation = implementationService.setMarsDetails(leadInformation);
            MarsDto marsDto = new MarsDto();
            EDCMarsResponseDto edcMarsResponseDto = new EDCMarsResponseDto();
            StringBuilder strResp = new StringBuilder();
            JSONParser parser = new JSONParser();
            marsDto.setDevice("");
            marsDto.setSalesInformation((JSONObject) parser.parse(marsOnboardingInformation.getSalesInformation()));
            marsDto.setAdditionalInfo((JSONObject) parser.parse(marsOnboardingInformation.getAdditionalInfo()));
            marsDto.setCompanyInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getCompanyInformation())));
            marsDto.setBusinessInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBusinessInformation())));
            marsDto.setPartnerInformation((JSONArray) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getPartnerInformation())));

            List<TerminalDetail> terminalDetails = new ArrayList<>();
            TerminalDetail terminalDetail = new TerminalDetail();
            terminalDetail.setAdvanceRentCollected(String.valueOf(leadInformation.getAmountCollected()));
            terminalDetail.setAdvanceRentMode("NEFT");
            terminalDetail.setDeviceOwnedBy("S");
            terminalDetail.setDevicePrice(0.00);
            terminalDetail.setEmiStartDate("17/10/2022");
            terminalDetail.setEmiTenure("null");
            terminalDetail.setGracePeriod(0);
            terminalDetail.setInstallationFee(String.valueOf(0.00));
            terminalDetail.setNoOfMonthRentPaidInAdvance("0");
            terminalDetail.setNumberOfTerminals(leadInformation.getDeviceCount());
            terminalDetail.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
            terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getMarsRentalPlans().getCode()));
            terminalDetail.setRentalMode("SE");
            terminalDetail.setTerminalModeCode(leadInformation.getDevice() != null ? leadInformation.getDevice().getMarsDeviceCode() : ""); //need to check
            logger.info("deviceModeCode ---> {}", terminalDetail.getTerminalModeCode());
            terminalDetail.setTerminalType("POS");
            terminalDetail.setTotalEmiAmount("null");
            terminalDetail.setIntlCardAcceptance(leadInformation.getIcaEnabled() == 1 ? "Y" : "N");
            terminalDetails.add(terminalDetail);
            marsDto.setTerminalDetails(terminalDetails);
            marsDto.setBankInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBankInformation())));

            MdrPlanDto mdrPlanDto = new MdrPlanDto();

            ObjectMapper mapper = new ObjectMapper();
            if (leadInformation.getMarsMdrCharges() != null) {
//                mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
//                mdrPlanDto.setDiners(mapper.readValue(leadInformation.getMarsMdrCharges().getDiners(), MdrChargesDto.class));
//                mdrPlanDto.setOnus(mapper.readValue(leadInformation.getMarsMdrCharges().getOnus(), MdrChargesDto.class));
//                mdrPlanDto.setStandardOrClassic(mapper.readValue(leadInformation.getMarsMdrCharges().getStandardOrClassic(), MdrChargesDto.class));
//                mdrPlanDto.setDomesticDebitUpTo2000(mapper.readValue(leadInformation.getMarsMdrCharges().getDomesticDebitUpTo2000(), MdrChargesDto.class));
//                mdrPlanDto.setDomesticDebitAbove2000(mapper.readValue(leadInformation.getMarsMdrCharges().getDomesticDebitAbove2000(), MdrChargesDto.class));
//                mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(leadInformation.getMarsMdrCharges().getSuperPremiumOrSignature(), MdrChargesDto.class));
//                mdrPlanDto.setInternationalCreditCard(mapper.readValue(leadInformation.getMarsMdrCharges().getInternationalCreditCard(), MdrChargesDto.class));
//                mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInformation.getMarsMdrCharges().getInternationalDebitCard(), MdrChargesDto.class));
//                mdrPlanDto.setCommercialOrCorporate(mapper.readValue(leadInformation.getMarsMdrCharges().getCommercialOrCorporate(), MdrChargesDto.class));
//                mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(leadInformation.getMarsMdrCharges().getPremiumOrPlatinum(), MdrChargesDto.class));
//                mdrPlanDto.setCashAtPos(mapper.readValue(leadInformation.getMarsMdrCharges().getCashAtPos(), MdrChargesDto.class));
//                mdrPlanDto.setCode(Integer.parseInt(leadInformation.getMarsMdrCharges().getCode()));

                mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getMarsMdrCharges().getMasterPass(), MdrChargesDto.class));
                mdrPlanDto.setDiners(mapper.readValue(leadInformation.getDiners(), MdrChargesDto.class));
                mdrPlanDto.setOnus(mapper.readValue(leadInformation.getMarsMdrCharges().getOnus(), MdrChargesDto.class));
                mdrPlanDto.setStandardOrClassic(mapper.readValue(leadInformation.getStandardOrClassic(), MdrChargesDto.class));
                mdrPlanDto.setDomesticDebitUpTo2000(mapper.readValue(leadInformation.getDomesticDebitUpTo2000(), MdrChargesDto.class));
                mdrPlanDto.setDomesticDebitAbove2000(mapper.readValue(leadInformation.getDomesticDebitAbove2000(), MdrChargesDto.class));
                mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(leadInformation.getSuperPremiumOrSignature(), MdrChargesDto.class));
                mdrPlanDto.setInternationalCreditCard(mapper.readValue(leadInformation.getInternationalCreditCard(), MdrChargesDto.class));
                mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInformation.getInternationalDebitCard(), MdrChargesDto.class));
                mdrPlanDto.setCommercialOrCorporate(mapper.readValue(leadInformation.getCommercialOrCorporate(), MdrChargesDto.class));
                mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(leadInformation.getPremiumOrPlatinum(), MdrChargesDto.class));
                mdrPlanDto.setCashAtPos(mapper.readValue(leadInformation.getMarsMdrCharges().getCashAtPos(), MdrChargesDto.class));
                mdrPlanDto.setCode(Integer.parseInt(leadInformation.getMarsMdrCharges().getCode()));
                mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInformation.getUpiAbove2000(), MdrChargesDto.class));
                mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInformation.getUpiUpto2000(), MdrChargesDto.class));
                mdrPlanDto.setUpiCreditAbove2000(mapper.readValue(leadInformation.getUpiCreditAbove2000(), MdrChargesDto.class));
                mdrPlanDto.setUpiCreditUpto2000(mapper.readValue(leadInformation.getUpiCreditUpto2000(), MdrChargesDto.class));
                if (leadInformation.getLeadSource().getLeadSourceName().equals("LS_Wowpe")) {
                    mdrPlanDto.setAmexDomestic(mapper.readValue(leadInformation.getAmexDomestic(), MdrChargesDto.class));
                    mdrPlanDto.setAmexInternational(mapper.readValue(leadInformation.getAmexInternational(), MdrChargesDto.class));
                }
            }
            marsDto.setMdrPlan(mdrPlanDto);
            marsDto.setRemarks("");
            marsDto.setRevParameters((JSONObject) parser.parse(marsOnboardingInformation.getPaymentDetails()));
            EdcResponseDto edcData = new EdcResponseDto();
            HashMap<String, Object> dummyDto = new HashMap<>();
            dummyDto.put("merchant", marsDto);
            JSONObject jsonObject = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
            HttpURLConnection conn = null;
            try {
//                    URL obj = new URL(marsTidUrl);
                URL obj = new URL(marsAddOnUrl);

                conn = (HttpURLConnection) obj.openConnection();
                conn.setRequestProperty("NII", "104");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                // HTTP Connection Headers completed
                // Sending API request
                JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                logger.info("URL----:{}", marsAddOnUrl);
                logger.info("Mars Request Payload for Tid Creation {}", reqData);

                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
//                    JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
                out.write(CommonUtil.parseJson(reqData)); // sending the data
//                    logger.info("Mars Request Payload for Tid Creation {}", reqData);
                out.close(); // closing the output stream
                int responsecode = conn.getResponseCode();
                String message = conn.getResponseMessage();
                logger.info("The response code and message is {} ----{}", responsecode, message);
                edcMarsResponseDto.setCode(conn.getResponseCode());
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("exception occurred -----------------: {}", e.getLocalizedMessage());
                return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "SOMETHING WENT WRONG", e.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
            }

            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 202) {
                leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(MARS_PENDING));
                leadInformationRepository.save(leadInformation);
                return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
            } else {
                Gson gson = new Gson();
                logger.info("-------------------Error-----------------{}", gson.toJson(conn));
                logger.info("-------------------InternalServerError-----------------{}", edcData.getResponseCode());
                return new ResponseEntity<>(new ApiResponse(CONFLICT, "Failure", edcData), CONFLICT);
            }
        }
        return new ResponseEntity<>(new ApiResponse(CONFLICT, "Lead Not Found"), CONFLICT);
    }

    @PutMapping("/qr-agg-addon/{id}")
    public ResponseEntity<?> AggAddOnQr(HttpServletRequest request, @PathVariable("id") long id) throws Exception {
        LeadInformation leadInformation = leadInformationRepository.findLeadId(id);
        logger.info("----lleeaadd---{}", leadInformation);
        List<UploadedDocuments> uploadedDocumentsList = implementationService.findByLeadId(leadInformation);
        if (!uploadedDocumentsList.isEmpty() && leadInformation.getLeadSource().getId() != 7) {             //leadSource=7 is DCB
            List<SignzyFetchAndVerification> panDetails = implementationService.getPanDetails(uploadedDocumentsList);
            if (!panDetails.isEmpty()) {
                leadInformation.setPanNumber(attributeEncryptor.decrypt(panDetails.get(0).getPanNumber()));
                leadInformationRepository.save(leadInformation);
                logger.info("pan Number -- : {}", attributeEncryptor.decrypt(panDetails.get(0).getPanNumber())); //need to remove
            }
        }
        MarsOnboardingInformation marsOnboardingInformation = implementationService.setQRMarsDetails(leadInformation);
        logger.info("onboarding values inserted.................");
        MarsDto marsDto = new MarsDto();
        EDCMarsResponseDto edcMarsResponseDto = new EDCMarsResponseDto();
        StringBuilder strResp = new StringBuilder();
        JSONParser parser = new JSONParser();
        marsDto.setDevice("");
        marsDto.setSalesInformation((JSONObject) parser.parse(marsOnboardingInformation.getSalesInformation()));
        marsDto.setAdditionalInfo((JSONObject) parser.parse(marsOnboardingInformation.getAdditionalInfo()));
        marsDto.setCompanyInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getCompanyInformation())));
        marsDto.setBusinessInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBusinessInformation())));
        marsDto.setPartnerInformation((JSONArray) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getPartnerInformation())));
        logger.info("lead_id--{}", leadInformation.getLeadId());
        List<String> vpas = vpaQrRepository.findID1(leadInformation.getLeadId());
        logger.info("VPAS--{}", vpas);
        List<TerminalDetail> terminalDetails = new ArrayList<>();
        if (leadInformation.getSqrDevice().getDeviceName().equals("QR-STICKER")) {
            TerminalDetail terminalDetail = new TerminalDetail();
            terminalDetail.setAdvanceRentCollected(String.valueOf(leadInformation.getAmountCollected()));
            terminalDetail.setAdvanceRentMode("NEFT");
            terminalDetail.setDeviceOwnedBy("S");
            terminalDetail.setDevicePrice(0.00);
            terminalDetail.setEmiStartDate("17/10/2022");
            terminalDetail.setEmiTenure("");
            terminalDetail.setGracePeriod(0);
            terminalDetail.setInstallationFee(String.valueOf(leadInformation.getSetUpFees()));
            terminalDetail.setNoOfMonthRentPaidInAdvance("0");
            terminalDetail.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
            terminalDetail.setRentalMode("SE");
            terminalDetail.setIntlCardAcceptance(leadInformation.getIcaEnabled() == 1 ? "Y" : "N");
            if (leadInformation.getSqrMarsRentalPlans() != null) {
                terminalDetail.setRentalType((Objects.equals(leadInformation.getSqrMarsRentalPlans().getPlanFrequency(), "M")) ? "R" : "A");
            } else {
                terminalDetail.setRentalType(" ");
            }
            terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getSqrMarsRentalPlans().getCode()));
            terminalDetail.setTerminalModeCode(leadInformation.getSqrDevice() != null ? leadInformation.getSqrDevice().getMarsDeviceCode() : ""); //need to check
            logger.info("deviceModeCode ---> {}", terminalDetail.getTerminalModeCode());
            terminalDetail.setTerminalType("POS");
            terminalDetail.setTotalEmiAmount("");
            terminalDetail.setVpa(vpas);
            terminalDetails.add(terminalDetail);
        } else {
            TerminalDetail terminalDetail = new TerminalDetail();
            terminalDetail.setAdvanceRentCollected(String.valueOf(leadInformation.getAmountCollected()));
            terminalDetail.setAdvanceRentMode("NEFT");
            terminalDetail.setDeviceOwnedBy("S");
            terminalDetail.setDevicePrice(0.00);
            terminalDetail.setEmiStartDate("17/10/2022");
            terminalDetail.setEmiTenure("");
            terminalDetail.setGracePeriod(0);
            terminalDetail.setInstallationFee(String.valueOf(leadInformation.getSetUpFees()));
            terminalDetail.setNoOfMonthRentPaidInAdvance("0");
            terminalDetail.setRecurringFees(String.valueOf(leadInformation.getRecurringFees()));
            terminalDetail.setRentalMode("SE");
            terminalDetail.setIntlCardAcceptance(leadInformation.getIcaEnabled() == 1 ? "Y" : "N");
            if (leadInformation.getSqrMarsRentalPlans() != null) {
                terminalDetail.setRentalType((Objects.equals(leadInformation.getSqrMarsRentalPlans().getPlanFrequency(), "M")) ? "R" : "A");
            } else {
                terminalDetail.setRentalType(" ");
            }
            terminalDetail.setRentalPlanCode(Integer.parseInt(leadInformation.getSqrMarsRentalPlans().getCode()));
            terminalDetail.setTerminalModeCode(leadInformation.getSqrDevice() != null ? leadInformation.getSqrDevice().getMarsDeviceCode() : ""); //need to check
            logger.info("deviceModeCode ---> {}", terminalDetail.getTerminalModeCode());
            terminalDetail.setTerminalType("POS");
            terminalDetail.setTotalEmiAmount("");
            terminalDetail.setVpa(vpas);
            terminalDetails.add(terminalDetail);
        }
        marsDto.setTerminalDetails(terminalDetails);
        marsDto.setBankInformation((JSONObject) parser.parse(attributeEncryptor.decrypt(marsOnboardingInformation.getBankInformation())));
        MdrPlanDto mdrPlanDto = new MdrPlanDto();
        ObjectMapper mapper = new ObjectMapper();
        if (leadInformation.getSqrMarsMdrCharge() != null) {
            logger.info("-----------SQRMDR---------");
            mdrPlanDto.setMasterPass(mapper.readValue(leadInformation.getMasterPass(), MdrChargesDto.class));
            mdrPlanDto.setDiners(mapper.readValue(leadInformation.getDiners(), MdrChargesDto.class));
            mdrPlanDto.setOnus(mapper.readValue(leadInformation.getOnus(), MdrChargesDto.class));
            mdrPlanDto.setStandardOrClassic(mapper.readValue(leadInformation.getStandardOrClassic(), MdrChargesDto.class));
            mdrPlanDto.setDomesticDebitUpTo2000(mapper.readValue(leadInformation.getDomesticDebitUpTo2000(), MdrChargesDto.class));
            mdrPlanDto.setDomesticDebitAbove2000(mapper.readValue(leadInformation.getDomesticDebitAbove2000(), MdrChargesDto.class));
            mdrPlanDto.setSuperPremiumOrSignature(mapper.readValue(leadInformation.getSuperPremiumOrSignature(), MdrChargesDto.class));
            mdrPlanDto.setInternationalCreditCard(mapper.readValue(leadInformation.getInternationalCreditCard(), MdrChargesDto.class));
            mdrPlanDto.setInternationalDebitCard(mapper.readValue(leadInformation.getInternationalDebitCard(), MdrChargesDto.class));
            mdrPlanDto.setCommercialOrCorporate(mapper.readValue(leadInformation.getCommercialOrCorporate(), MdrChargesDto.class));
            mdrPlanDto.setPremiumOrPlatinum(mapper.readValue(leadInformation.getPremiumOrPlatinum(), MdrChargesDto.class));
            mdrPlanDto.setCashAtPos(mapper.readValue(leadInformation.getCashAtPos(), MdrChargesDto.class));
            mdrPlanDto.setCode(Integer.parseInt(leadInformation.getSqrMarsMdrCharge().getCode()));
            mdrPlanDto.setUpiUpto2000(mapper.readValue(leadInformation.getUpiUpto2000(), MdrChargesDto.class));
            mdrPlanDto.setUpiAbove2000(mapper.readValue(leadInformation.getUpiAbove2000(), MdrChargesDto.class));
            mdrPlanDto.setUpiCreditAbove2000(mapper.readValue(leadInformation.getUpiCreditAbove2000(), MdrChargesDto.class));
            mdrPlanDto.setUpiCreditUpto2000(mapper.readValue(leadInformation.getUpiCreditUpto2000(), MdrChargesDto.class));
        }
        marsDto.setMdrPlan(mdrPlanDto);
        marsDto.setRemarks("");
        marsDto.setRevParameters((JSONObject) parser.parse(marsOnboardingInformation.getPaymentDetails()));


        EdcResponseDto edcData = null;
        logger.info(" data 2 ... {}", marsDto);
        HashMap<String, Object> dummyDto = new HashMap<>();
        dummyDto.put("merchant", marsDto);
        JSONObject jsonObject = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
        logger.info(" JSON OBJ : ---- : {}", jsonObject);
        logger.info(" dummy -- : {}", CommonUtil.parseJson(jsonObject));
        HttpURLConnection conn = null;
        try {
//                    URL obj = new URL(marsVpaUrl);
            URL obj = new URL(qrmarsAddOnUrl);
            conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestProperty("NII", "110");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            // HTTP Connection Headers completed
            logger.info("HTTP headers : ----- : {}", conn);
            // Sending API request
            logger.info("Getting Tickets Status {}", dummyDto);
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            JSONObject reqData = (JSONObject) parser.parse(CommonUtil.parseJson(dummyDto));
            out.write(CommonUtil.parseJson(reqData)); // sending the data
            logger.info("Getting Tickets Status {}", reqData);
            out.close(); // closing the output stream
            int responsecode = conn.getResponseCode();
            logger.info("The response code is {}", conn);
            String message = conn.getResponseMessage();
            logger.info("The response code is {}", responsecode);
            logger.info("The response message is {}", message);
            edcMarsResponseDto.setCode(conn.getResponseCode());
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "SOMETHING WENT WRONG", e.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
        }

        if (conn.getResponseCode() == 200 || conn.getResponseCode() == 202) {
            leadInformation.setCounterCompleted(true);
            leadInformation.setLeadStatus(leadStatusRepository.fetchLeadStatusById(MARS_QR_PENDING));
            leadInformationRepository.save(leadInformation);
            return new ResponseEntity<>(new ApiResponse(OK, "success"), OK);
        } else {
            logger.info("-------------------InternalServerError-----------------{}", edcData.getResponseCode());
            return new ResponseEntity<>(new ApiResponse(CONFLICT, "Failure", edcData), CONFLICT);
        }
    }

    @GetMapping("wowpe-reports")
    public void wowpeReports(HttpServletResponse response) throws IOException {
        List<LeadInformation> wowpeLeads = leadInformationRepository.findByLeadSourceWowpe();

        if (wowpeLeads == null || wowpeLeads.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("text/plain");
            response.getWriter().write("No Completed Data found");
            return;
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("WowpeReport" + LocalDate.now());

        String[] headers = {
                "S.No", "UPLOAD DATE", "CREDENTIAL DATE", "MERCHANT LEGAL NAME", "MERCHANT DBA NAME", "Region",
                "MID", "TID", "STATUS"
        };

        Row header = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            header.createCell(i).setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (LeadInformation m : wowpeLeads) {
            MarsDeviceDetails marsDeviceDetails = marsDeviceDetailsRepository.findByList(m.getLeadId());
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(rowNum);
            row.createCell(1).setCellValue(m.getCreatedAt() != null ? m.getCreatedAt().toString() : "");
            row.createCell(2).setCellValue(marsDeviceDetails != null && marsDeviceDetails.getCreatedAt() != null ? marsDeviceDetails.getCreatedAt().toString() : "");
            row.createCell(3).setCellValue(m.getLegalName() != null ? m.getLegalName() : "");
            row.createCell(4).setCellValue(m.getMerchantName() != null ? m.getMerchantName() : "");
            row.createCell(5).setCellValue(m.getRegion() != null ? m.getRegion().getRegionAreaName() : "");
            row.createCell(6).setCellValue(marsDeviceDetails != null && marsDeviceDetails.getMid() != null ? marsDeviceDetails.getMid() : "");
            row.createCell(7).setCellValue(marsDeviceDetails != null && marsDeviceDetails.getTid() != null ? marsDeviceDetails.getTid() : "");
            row.createCell(8).setCellValue(m.getLeadStatus() != null ? m.getLeadStatus().getLeadStatusName() : "");
            rowNum++;
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        String fileName = "WowpeReport" + LocalDate.now() + ".xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @PostMapping("mail")
    public ResponseEntity<?> mail() throws Exception {
        mailHandler.sendEmails("TID CREATED |", "1234");
        return null;
    }

    @PutMapping("update-institution/{id}/{leadId}")
    public ResponseEntity<?> updateInstitution(@PathVariable("id") long id, @PathVariable("leadId") long leadId) {
        LeadInformation leadInformation = leadInformationRepository.findLead(leadId);
        Optional<OnboardInstance> onboardInstance = onboardInstanseRepo.findById(id);
        if (onboardInstance.isPresent() && leadInformation != null) {
            leadInformation.setOnboardInstance(onboardInstance.get());
            leadInformationRepository.save(leadInformation);
            return new ResponseEntity<>(new ApiResponse(OK, "Update Success"), OK);
        }
        return new ResponseEntity<>(new ApiResponse(BAD_REQUEST, "LeadId or onboardInstance Not Found"), BAD_REQUEST);
    }


    //new changes digi new api
    /**
     * Endpoint 1: Send / Resend Mobile OTP
     * URL: POST http://<host>:8095/auth/otp/vyaapar/send-mobile-otp
     * Replaces old consentLink link sender.
     */
    @PostMapping("/send-mobile-otp")
    public ResponseEntity<?> sendMobileOtp(@RequestBody MobileOtpRequestDto requestDto) {

        if (requestDto.getMobileNumber() == null || !requestDto.getMobileNumber().matches("^[6-9]\\d{9}$")) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, "Invalid 10-digit mobile number"), HttpStatus.BAD_REQUEST);
        }

        try {
            boolean isSent = implementationService.triggerMobileOTP(requestDto);

            if (isSent) {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "OTP sent successfully to mobile number"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.TOO_MANY_REQUESTS, "Please wait for 60 seconds cooldown before requesting a new OTP"), HttpStatus.TOO_MANY_REQUESTS);
            }
        } catch (Exception e) {
            logger.error("Error in sendMobileOtp API: ", e);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send OTP"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint 2: OSV Screen OTP Verification
     * URL: POST http://<host>:8095/auth/otp/vyaapar/verify-mobile-otp
     */
//    @PostMapping("/verify-mobile-otp")
//    public ResponseEntity<?> verifyMobileOtp(@RequestBody MobileOtpRequestDto requestDto) {
//
//        if (requestDto.getMobileNumber() == null || requestDto.getOtp() == null || requestDto.getOtp().trim().isEmpty()) {
//            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, "Mobile number and OTP are required"), HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            boolean isVerified = implementationService.verifyMobileOTP(requestDto);
//
//            if (isVerified) {
//                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Mobile OTP verified successfully in OSV screen"), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "Invalid or Expired OTP"), HttpStatus.UNAUTHORIZED);
//            }
//        } catch (Exception e) {
//            logger.error("Error in verifyMobileOtp API: ", e);
//            return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Verification failed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @PostMapping("/verify-mobile-otp")
//    public ResponseEntity<?> verifyMobileOtp(@RequestBody MobileOtpRequestDto requestDto) {
//
//        if (requestDto.getMobileNumber() == null || requestDto.getOtp() == null || requestDto.getOtp().trim().isEmpty()) {
//            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, "Mobile number and OTP are required"), HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            boolean isVerified = implementationService.verifyMobileOTP(requestDto);
//
//            if (isVerified) {
//                // 🛑 Check if implementationService.updateLeadOnboarding(requestDto)
//                // or similar methods inside your service are calling triggerMobileOTP() again.
//
//                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Mobile OTP verified successfully in OSV screen"), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "Invalid or Expired OTP"), HttpStatus.UNAUTHORIZED);
//            }
//        } catch (Exception e) {
//            logger.error("Error in verifyMobileOtp API: ", e);
//            return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Verification failed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }




    @PostMapping("/verify-mobile-otp")
    public ResponseEntity<?> verifyMobileOtp(@RequestBody MobileOtpRequestDto requestDto) {

        // 1. Basic Validation
        if (requestDto.getMobileNumber() == null || requestDto.getMobileNumber().trim().isEmpty() ||
                requestDto.getOtp() == null || requestDto.getOtp().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(HttpStatus.BAD_REQUEST, "Mobile number and OTP are required"));
        }

        try {
            boolean isVerified = implementationService.verifyMobileOTP(requestDto);

            if (isVerified) {
                return ResponseEntity.ok(
                        new ApiResponse(HttpStatus.OK, "Mobile OTP verified successfully in OSV screen")
                );
            } else {
                // Returns 400 Bad Request instead of 401 Unauthorized for invalid/rate-limited OTP
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(HttpStatus.BAD_REQUEST, "Invalid or Expired OTP / Cooldown Active"));
            }
        } catch (Exception e) {
            logger.error("Error in verifyMobileOtp API: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Verification failed due to internal server error"));
        }
    }

    @PostMapping("/check-one-submit")
    public ResponseEntity<?> checkingpurposes()
    {
        implementationService.sendToRoleSubmit("Digital Ops", null);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Successfully worked and triggered the push notification for Submit change"),HttpStatus.OK);
    }

    @PostMapping("/check-two-techissue")
    public ResponseEntity<?> checkingpurposestwo()
    {
        implementationService.sendToRoleTech("Digital Ops", null);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Successfully worked and triggered the push notification for Tech issues"),HttpStatus.OK);
    }

    @PostMapping("/save-edd-amount/{leadId}/{eddAmount}")
    public  ResponseEntity<?> saveEddAmount(@PathVariable("leadId")Long leadId,@PathVariable("eddAmount")Double eddAmount) throws IOException{
        logger.info("leadId :---{}",leadId);
        MarsOnboardingInformation marsOnboardingInformation=marsOnboardingRepository.findLeadId(leadId);
        marsOnboardingInformation.setEddAmount(eddAmount);
        marsOnboardingRepository.save(marsOnboardingInformation);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "success"), HttpStatus.OK);
    }


}












