package com.bijlipay.config;

public final class Constants {



    // Lead Status
    public static final long PENDING_AGREEMENT_SIGNING=7;
    public static final long LEAD_COMPLETED = 13;
    public static final long REJECTED_BY_OPS = 10;
    public static final long IMPL_PENDING_SCHEDULE_VISIT = 14;

    public static final int AGREEMENT_SIGNING_COMPLETED = 8;

    public static final long LEAD_REJECTED_BY_SWITCH = 9;
    public static final long DMO_API_FAILED= 21;
    public static final long  HOST_API_FAILED= 22;
    public static final long OGS_API_FAILED = 23;
    public static final long SQR_ONBOARDING_FAILED = 24;

    public static final long QR_Pending = 27;


    public static final int LEAD_STATUS_MARS_APPROVED = 12;

    public static final int IMPL_PENDING_LOCATION_FETCH = 15;

    public static final int IMPLEMENTATION_COMPLETED = 16;
    public static final int Mcc_Document_Rejected_By_Ops = 31;
    public static final int MCC_VERIFICATION_APPROVE = 8;
    public static final int MCC_VERIFICATION_REJECT = 9;
    public static final long PENDING_DOCUMENT_UPLOAD =2;
    public static final int PENDING_ICA_APPROVAL = 32;

    public static final int MARS_PENDING=37;
    public static final int MARS_QR_PENDING=38;
    public static final long OWNER_DETAILS_PENDING=39;
    public static final long PENDING_DEVICE_DETAILS=40;
    public static final long PENDING_BIZ_DETAILS =4;


    // MARS DEVICE DETAILS TABLE
    public static final int MARS_DEVICE_STATUS_TID_GENERATED = 1;
    public static final int MARS_DEVICE_STATUS_SCAN_PICKED = 2;
    public static final int MARS_DEVICE_STATUS_INSTALLATION_SCHEDULED = 3;

    public static final int MARS_DEVICE_STATUS_INSTALLATION_RE_SCHEDULED = 4;

    public static final int MARS_DEVICE_STATUS_IMPLEMENTED = 5;
    public static final int MARS_DEVICE_STATUS_ASSIGNED_TO_SO = 6;
    public static final int MARS_DEVICE_STATUS_IMPLEMENTATION_PENDING =8;
    public static final int MARS_DEVICE_STATUS_SAT_PENDING =9;
    public static final int MARS_DEVICE_STATUS_SQR_PENDING = 7;

    // QR Status

    public static final int MARS_QR_STATUS_SCAN_PICKED = 18;
    public static final int MARS_SOUNDBOX_DEVICE_STATUS_SCAN_PICKED = 19;


    public static final int HITACHI_CREATE_MERCHANT_FAILED = 41;
    public static final int UPI_SUBMERCHANT_FAILED = 42;
    public static final int UPI_ENABLE_REFID_FAILED = 43;
    public static final int JUPITER_ONBOARDING_FAILED = 44;
    public static final int BENEFICIARY_FETCH_FAILED = 45;
    public static final int BENEFICIARY_MANAGEMENT_FAILED = 46;
    public static final int BENEFICIARY_VALIDATE_FAILED = 47;


    //Lead Document status
    public static final int DOCUMENT_VERIFICATION_PENDING = 0;
    public static final int SIGNZY_VERIFICATION_SUCCESS = 1;
    public static final int SIGNZY_VERIFICATION_FAILED = 2;
    public static final int OPS_VERIFICATION_PENDING = 3;
    public static final int OPS_VERIFICATION_SUCCESS = 4;
    public static final int OPS_VERIFICATION_FAILED = 5;
    public static final int SIGNZY_VERIFICATION_PENDING = 6;

    public static final String PUSH_NOTIFICATION_TO_APPROVE_LEAD = "ImplementationLeadApprovedDetails";
    public static final String PUSH_NOTIFICATION_TO_CONSENT_LINK = "Silent";
    public static final String PUSH_NOTIFICATION_TO_TID_CREATE_POS = "RedirectToTidPOS";
    public static final String PUSH_NOTIFICATION_TO_TID_CREATE_QR = "RedirectToTidQR";
    public static final String PUSH_NOTIFICATION_TO_REJECT_LEAD = "ImplementationLeadRejectedDetails";
    public static final String  PUSH_NOTIFICATION_TO_PENDING_MERCHANT_DETAILS_PENDING ="RedirectToMerchantDetails";
    public static final String  PUSH_NOTIFICATION_TO_MCC_APPROVE ="MccAproved";
    public static final String  PUSH_NOTIFICATION_TO_MCC_CHANGE ="MccChange";
    public static final String  PUSH_NOTIFICATION_TO_MCC_REJECTED ="MccRejected";

    //Document Category
    public static final long DOCUMENT_CATEGORY_PROOF_OF_IDENTITY =1;
    public static final long DOCUMENT_CATEGORY_PROOF_OF_ADDRESS=2;
    public static final long DOCUMENT_CATEGORY_PROOF_OF_BUSINESS=3;
    public static final long DOCUMENT_CATEGORY_PROOF_OF_BUSINESS_CATEGORY=4;
    public static final long DOCUMENT_CATEGORY_IMPLEMENTATION_CATEGORY=5;

    //Lead Category

    public static final int LEAD_CATEGORY_DIGITAL=0;
    public static final int LEAD_CATEGORY_NORMAL=1;
    public static final int LEAD_CATEGORY_EXCEPTION=2;

    //Document mandatory
    public static final int ANYONE_DOCUMENT = 1;
    public static final int ALL_DOCUMENT = 2;
    public static final int MANDATORY_DOCUMENT =3;

    public static final String professional_lead = "Mandatory Documents (for professionals)";

    //Mars Rejected Remarks
    public static final int MARS_REJECT =1;

    //Mars Api Actions
    public static final int MARS_API_NOT_TRIGGERED =0;
    public static final int MARS_API_TRIGGERED=1;
    public static final int MARS_RESPONSE_TID_CREATED=2;

    public static final String LS_VIYONA_FINTECH = "LS_Viyona_Fintech";
    public static final String LEAD_SOURCE_PET_POOJA = "LS_PET_POOJA";
    public static final String LEAD_SOURCE_FIDY_PAY = "LS_FidyPay";
    public static final String LEAD_SOURCE_KVB = "LS_KARUR VYSYA BANK";
    public static final String LEAD_SOURCE_WOWPE = "LS_Wowpe";

    //Mcc Final Approval
    public static final int MCC_DOCUMENT_VERIFY=1;
    public static final int MCC_FINAL_APPROVE=2;

    //tms
    public static final int TMS_FAILED=34;

    public static final String[] SENT_TO_REPORT = {"radhika.s@bijlipay.co.in","jaikumar.s@bijlipay.co.in","yugendran@bijlipay.co.in"};


    //subvention
    public static final int SUBMITTED = 1;
    public static final int APPROVE = 2;
    public static final int REJECTED = 3;
    public static final int REFFERBACK = 4;

}
