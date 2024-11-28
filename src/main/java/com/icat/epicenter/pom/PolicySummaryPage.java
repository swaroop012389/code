/** Program Description: Object Locators and methods defined in Policy summary page
 *  Author			   : SMNetserv
 *  Date of Creation   : 07/11/2017
 **/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class PolicySummaryPage extends BasePageControl {
	public BaseWebElementControl policyNumber;
	public TextFieldControl policyAsOfDate;
	public ButtonControl viewPolicyVersion;
	public HyperLink assignPFC;

	public HyperLink viewPolicySnapshot;
	public HyperLink viewDocuments;

	public HyperLink renewalIndicators;
	public HyperLink renewPolicy;
	public HyperLink rewritePolicy;
	public HyperLink cancelPolicy;
	public HyperLink endorseNPB;
	public HyperLink endorsePB;
	public HyperLink requestLossRun;
	public HyperLink close;

	public HyperLink re_RenewPolicyLink;
	public HyperLink reinstatePolicy;
	public HyperLink reversePreviousEndorsementLink;
	public HyperLink rewrittenPolicyNumber;
	public HyperLink ViewBillingInfoLink;

	public HyperLink viewActiveRenewal;
	public ButtonControl renewalProcessOkBtn;
	public ButtonControl renewalCreatedOkBtn;

	public BaseWebElementControl numberofYearsWritten;
	public BaseWebElementControl tiv;
	public BaseWebElementControl insuredName;
	public BaseWebElementControl mailingAddress;
	public BaseWebElementControl producerName;
	public BaseWebElementControl producerNumber;
	public BaseWebElementControl producerContactName;
	public BaseWebElementControl producerContactEmail;

	public BaseWebElementControl PremiumFee;
	public BaseWebElementControl PremiumInspectionFee;
	public BaseWebElementControl PremiumPolicyFee;
	public BaseWebElementControl transactionPremium;
	public BaseWebElementControl transactionSurplusContribution;
	public BaseWebElementControl policyTotalPremium;
	public BaseWebElementControl txnTaxesAndStateFees;

	public BaseWebElementControl bindRequestDate;
	public BaseWebElementControl processingDate;
	public BaseWebElementControl effectiveDate;
	public BaseWebElementControl expirationDate;
	public BaseWebElementControl inspectionFee;
	public BaseWebElementControl policyFee;
	public BaseWebElementControl paymentPlan;
	public BaseWebElementControl policyStatus;

	public BaseWebElementControl transactionPolicyFee;
	public BaseWebElementControl transactionInspectionFee;

	public BaseWebElementControl termInspectionFee;
	public BaseWebElementControl termPolicyFee;
	public BaseWebElementControl termTotal;
	public BaseWebElementControl termSurplusContribution;
	public BaseWebElementControl termPremium;
	public BaseWebElementControl termTaxesAndStateFees;

	public BaseWebElementControl annualInspectionFee;
	public BaseWebElementControl annualPolicyFee;
	public BaseWebElementControl annualTotal;
	public BaseWebElementControl annualSurplusContribution;
	public BaseWebElementControl annualPremium;
	public BaseWebElementControl annualTaxesAndStateFees;

	public BaseWebElementControl transHistTable;
	public ButtonControl transHistEffDate;
	public ButtonControl transHistReason;
	public ButtonControl transHistReasonNAHO;
	public HyperLink transHistNum;
	public HyperLink quoteNoLink;
	public HyperLink quoteNumText;
	public HyperLink quoteNoLinkNAHO;
	public ButtonControl removePendingNOCCancel;
	public ButtonControl removePendingNOCBtn;
	public BaseWebElementControl nocMessage;
	public ButtonControl okEvent;
	public BaseWebElementControl policySnapshotScreen;

	public HyperLink producerEndorsePolicyLink;
	public BaseWebElementControl reverseEndorsement;

	public BaseWebElementControl pageName;
	public ButtonControl transferContinue;

	public HyperLink pbEndt800Link;
	public HyperLink pbEndt800LinkNAHO;
	public HyperLink npbEndt800Link;
	public HyperLink npbEndt800LinkNAHO;
	public HyperLink pbEndtAmmDecLink;
	public HyperLink pbEndtAmmDecLinkNAHO;
	public HyperLink npbEndtAmmDecLink;
	public HyperLink npbEndtAmmDecLinkNAHO;

	public BaseWebElementControl autoRenewalIndicators;
	public BaseWebElementControl renewalDelMsg;

	public BaseWebElementControl altQuoteMin;
	public BaseWebElementControl altQuoteMax;

	public BaseWebElementControl transactionType;
	public BaseWebElementControl expaacMessage;
	public ButtonControl addExpaccInfo;

	public HyperLink esLink;
	public HyperLink decLink;

	public BaseWebElementControl policyInformation;
	public HyperLink removePendingNOCLink;
	public BaseWebElementControl endorsementDeletedWarningMsg;
	public HyperLink stopPolicyRewrite;
	public HyperLink workOnRewrite;
	public BaseWebElementControl producerTransactionType;
	public BaseWebElementControl taxesAndStateFees;
	public HyperLink requestCancellationLink;
	public HyperLink newNote;
	public ButtonControl selectCategory;
	public TextFieldControl enterNote;
	public HyperLink saveNoteButton;
	public ButtonControl yesSaveNoteButton;
	public BaseWebElementControl accountNote;
	public BaseWebElementControl commissionRateForNBPolicy;
	public BaseWebElementControl sltfValue;
	public BaseWebElementControl premiumTotal;
	public HyperLink previousPolicyNumber;
	public HyperLink rewrittenPolicyNumberTo;
	public BaseWebElementControl proRataFactor;

	public BaseWebElementControl surplusContributionValue;
	public HyperLink transRevReason;
	public ButtonControl openCurrentReferral;
	public BaseWebElementControl renewalMessage;
	public HyperLink customerViewLink;
	public HyperLink customerViewPsge;
	public BaseWebElementControl PremiunInspectionFee;
	public ButtonControl endorsementTransactionType;
	public BaseWebElementControl pageName1;
	public ButtonControl noteBar;
	public BaseWebElementControl noteBarMessage;
	public HyperLink transHistDocLink;
	public BaseWebElementControl noofRowsTrxnHistory;
	public BaseWebElementControl transHistType;
	public BaseWebElementControl transHistProcessedBy;
	public HyperLink viewPrintRateTrace;
	public ButtonControl okButton;
	public BaseWebElementControl pendingEndorsementWarningMsg;
	public BaseWebElementControl TaxesAndStateFees;
	public HyperLink cancelEnd;
	public HyperLink reinstatmentForm;
	public BaseWebElementControl transactionComment;
	public BaseWebElementControl niDisplay;
	public ButtonControl endorsementTransaction;
	public HyperLink endorsementRecordQ3;
	public ButtonControl cancelType;
	public BaseWebElementControl activeStatus;

	public PolicySummaryPage() {
		PageObject pageobject = new PageObject("PolicySummary");
		policyNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyNumber")));
		policyAsOfDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ShowPolicyAsOfDate")));
		viewPolicyVersion = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewPolicyVersion")));
		assignPFC = new HyperLink(By.xpath(pageobject.getXpath("xp_AssignPFC")));

		viewPolicySnapshot = new HyperLink(By.xpath(pageobject.getXpath("xp_ViewPolicySnapshot")));
		viewDocuments = new HyperLink(By.xpath(pageobject.getXpath("xp_ViewDocuments")));

		renewalIndicators = new HyperLink(By.xpath(pageobject.getXpath("xp_RenewalIndicators")));
		renewPolicy = new HyperLink(By.xpath(pageobject.getXpath("xp_RenewPolicy")));
		rewritePolicy = new HyperLink(By.xpath(pageobject.getXpath("xp_RewritePolicy")));
		cancelPolicy = new HyperLink(By.xpath(pageobject.getXpath("xp_CancelPolicy")));
		endorseNPB = new HyperLink(By.xpath(pageobject.getXpath("xp_EndorseNPB")));
		endorsePB = new HyperLink(By.xpath(pageobject.getXpath("xp_EndorsePB")));
		requestLossRun = new HyperLink(By.xpath(pageobject.getXpath("xp_RequestLossRun")));
		close = new HyperLink(By.xpath(pageobject.getXpath("xp_Close")));

		re_RenewPolicyLink = new HyperLink(By.xpath(pageobject.getXpath("xp_Re_renewPolicyLink")));
		reinstatePolicy = new HyperLink(By.xpath(pageobject.getXpath("xp_ReinstatePolicy")));
		reversePreviousEndorsementLink = new HyperLink(
				By.xpath(pageobject.getXpath("xp_ReversePreviousEndorsementLink")));
		rewrittenPolicyNumber = new HyperLink(By.xpath(pageobject.getXpath("xp_RewrittenPolicyNumber")));
		ViewBillingInfoLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ViewBillingInfoLink")));

		viewActiveRenewal = new HyperLink(By.xpath(pageobject.getXpath("xp_ViewActiveRenewal")));
		renewalProcessOkBtn = new HyperLink(By.xpath(pageobject.getXpath("xp_RenewalProcess")));
		renewalCreatedOkBtn = new HyperLink(By.xpath(pageobject.getXpath("xp_RenewalCreated")));

		termTotal = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TermTotal")));
		numberofYearsWritten = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NumOfYearsWritten")));
		tiv = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyCoverageTotal")));
		insuredName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InsuredName")));
		mailingAddress = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MailingAddress")));
		producerName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducerName")));
		producerNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducerNumber")));
		producerContactName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducerContactName")));
		producerContactEmail = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducerContactEmail")));

		PremiumFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumFee")));
		PremiumInspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumInspectionFee")));
		PremiumPolicyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumPolicyFee")));
		transactionPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TransactionPremium")));
		policyTotalPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalTransactionPremium")));
		txnTaxesAndStateFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TaxesAndStateFees")));

		effectiveDate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyEffectiveDate")));
		inspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspectionFee")));
		policyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyFee")));
		paymentPlan = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PaymentPlan")));
		policyStatus = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyStatus")));

		transactionPolicyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TxnPolicyFee")));
		transactionInspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TxnInspectionFee")));
		transactionSurplusContribution = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_TxnSurplusContribution")));
		transactionPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TransactionPremium")));

		annualPolicyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AnnualPolicyFee")));
		annualInspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AnnualInspectionFee")));
		annualSurplusContribution = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AnnualSurplusContribution")));
		annualPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AnnualPremium")));
		annualTaxesAndStateFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AnnualTaxesAndStateFees")));

		termInspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TermInspectionFee")));
		termPolicyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TermPolicyFee")));
		termSurplusContribution = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_TermSurplusContribution")));
		termPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TermPremium")));
		termTaxesAndStateFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TermTaxesAndStateFees")));

		transHistTable = new BaseWebElementControl(By.xpath(pageobject.getXpath(("xp_TransHistTable"))));
		transHistEffDate = new ButtonControl(By.xpath(pageobject.getXpath("xp_TransHistEffDate")));
		transHistReason = new ButtonControl(By.xpath(pageobject.getXpath("xp_TransHistReason")));
		transHistReasonNAHO = new ButtonControl(By.xpath(pageobject.getXpath("xp_TransHistReasonNAHO")));
		transHistNum = new HyperLink(By.xpath(pageobject.getXpath("xp_TransHistNum")));
		quoteNoLink = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteNo")));
		quoteNumText = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteNumText")));
		quoteNoLinkNAHO = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteNoLinkNAHO")));
		removePendingNOCCancel = new ButtonControl(By.xpath(pageobject.getXpath("xp_RemovePendingNOCCancel")));
		removePendingNOCBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_RemovePendingNOCBtn")));
		nocMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NOCMessage")));
		okEvent = new ButtonControl(By.xpath(pageobject.getXpath("xp_OkEvent")));
		policySnapshotScreen = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicySnapshotScreen")));

		producerEndorsePolicyLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ProducerEndorsePolicy")));
		reverseEndorsement = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReversalEndorse")));

		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
		transferContinue = new ButtonControl(By.xpath(pageobject.getXpath("xp_TransferContinue")));

		pbEndt800Link = new HyperLink(By.xpath(pageobject.getXpath("xp_PBEndt800Form")));
		pbEndt800LinkNAHO = new HyperLink(By.xpath(pageobject.getXpath("xp_PBEndt800FormNAHO")));
		npbEndt800Link = new HyperLink(By.xpath(pageobject.getXpath("xp_NPBEndt800Form")));
		npbEndt800LinkNAHO = new HyperLink(By.xpath(pageobject.getXpath("xp_NPBEndt800FormNAHO")));
		pbEndtAmmDecLink = new HyperLink(By.xpath(pageobject.getXpath("xp_PBEndtAmmDecForm")));
		pbEndtAmmDecLinkNAHO = new HyperLink(By.xpath(pageobject.getXpath("xp_PBEndtAmmDecFormNAHO")));
		npbEndtAmmDecLink = new HyperLink(By.xpath(pageobject.getXpath("xp_NPBEndtAmmDecForm")));
		npbEndtAmmDecLinkNAHO = new HyperLink(By.xpath(pageobject.getXpath("xp_NPBEndtAmmDecFormNAHO")));

		autoRenewalIndicators = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AutoRenewalIndicators")));
		renewalDelMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RenewalDeletedMsg")));

		processingDate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProcessingDate")));
		expirationDate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyExpirationDate")));
		altQuoteMin = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AltQuoteMin")));
		altQuoteMax = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AltQuoteMax")));

		transactionType = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TransactionType")));
		expaacMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ExpaacWarningMesage")));
		addExpaccInfo = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddExpaccInfo")));
		esLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ESLink")));
		decLink = new HyperLink(By.xpath(pageobject.getXpath("xp_DeclLink")));
		policyInformation = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyInformation")));
		removePendingNOCLink = new HyperLink(By.xpath(pageobject.getXpath("xp_RemovePendingNOCLink")));
		endorsementDeletedWarningMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_EndorsementDeletedWarningMsg")));
		stopPolicyRewrite = new HyperLink(By.xpath(pageobject.getXpath("xp_StopPolicyWrite")));
		workOnRewrite = new HyperLink(By.xpath(pageobject.getXpath("xp_WorkOnRewrite")));
		producerTransactionType = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ProducerTransactionType")));
		//leave this for wholesale regression tests
		taxesAndStateFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TaxesAndStateFees")));

		requestCancellationLink = new HyperLink(By.xpath(pageobject.getXpath("xp_RequestCancellationLink")));

		newNote = new HyperLink(By.xpath(pageobject.getXpath("xp_NewNote")));
		selectCategory = new ButtonControl(By.xpath(pageobject.getXpath("xp_SelectCategory")));
		enterNote = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EnterNote")));
		saveNoteButton = new HyperLink(By.xpath(pageobject.getXpath("xp_SaveNoteButton")));
		yesSaveNoteButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_YesSaveNoteButton")));
		accountNote = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AccountNote")));
		commissionRateForNBPolicy = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_CommissionRateForNBPolicy")));
		sltfValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SLTFValue")));
		premiumTotal = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumTotal")));

		previousPolicyNumber = new HyperLink(By.xpath(pageobject.getXpath("xp_PrviousPolicyNumber")));
		rewrittenPolicyNumberTo = new HyperLink(By.xpath(pageobject.getXpath("xp_RewrittenPolicyNumberTo")));
		proRataFactor = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProRata")));

		surplusContributionValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SurplusContributionValue")));
		transRevReason = new HyperLink(By.xpath(pageobject.getXpath("xp_TransRevReason")));
		openCurrentReferral = new ButtonControl(By.xpath(pageobject.getXpath("xp_OpenCurrentReferral")));
		renewalMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RenewalMessage")));
		customerViewLink = new HyperLink(By.xpath(pageobject.getXpath("xp_CustomerViewLink")));
		customerViewPsge = new HyperLink(By.xpath(pageobject.getXpath("xp_CustomerViewPsge")));
		PremiunInspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiunInspectionFee")));
		endorsementTransactionType = new ButtonControl(By.xpath(pageobject.getXpath("xp_EndorsementTransactionType")));
		pageName1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName1")));
		annualTotal = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AnnualTotal")));
		noteBar = new ButtonControl(By.xpath(pageobject.getXpath("xp_NoteBar")));
		noteBarMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NoteBarMessage")));
		transHistDocLink = new HyperLink(By.xpath(pageobject.getXpath("xp_TransHistDocLink")));
		noofRowsTrxnHistory = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NoofRowsTrxnHistory")));
		transHistType = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TransHistType")));
		transHistProcessedBy = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TransHistProcessedBy")));
		viewPrintRateTrace = new HyperLink(By.xpath(pageobject.getXpath("xp_ViewPrintRateTrace")));
		okButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OKButton")));
		pendingEndorsementWarningMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PendingEndorsementWarningMsg")));
		//leave this for NAHO tests
		TaxesAndStateFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TaxesAndStateFees")));
		cancelEnd = new HyperLink(By.xpath(pageobject.getXpath("xp_CancelEnd")));
		reinstatmentForm = new HyperLink(By.xpath(pageobject.getXpath("xp_ReinstatmentForm")));
		transactionComment = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TransactionComment")));
		niDisplay = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NIDisplay")));
		endorsementRecordQ3 = new HyperLink(By.xpath(pageobject.getXpath("xp_EndorsementRecordQ3")));
		activeStatus = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyStatusActive")));
		endorsementTransaction = new ButtonControl(By.xpath(pageobject.getXpath("xp_EndorsementTransaction")));
		cancelType= new ButtonControl(By.xpath(pageobject.getXpath("xp_cancelType")));
	}

	public String getPolicynumber() {
		policyNumber.waitTillPresenceOfElement(60);
		policyNumber.waitTillVisibilityOfElement(60);
		policyNumber.scrollToElement();
		return policyNumber.getData();
	}
}
