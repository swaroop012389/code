/** Program Description: Object Locators and methods defined in AccountOverview page
 *  Author			   : SMNetserv
 *  Date of Creation   : 27/10/2017
 **/

package com.icat.epicenter.pom;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class AccountOverviewPage extends BasePageControl {
	public HyperLink viewPrintFullQuoteLink;
	public HyperLink viewQuoteDetailsLink;
	public HyperLink emailQuoteLink;
	public HyperLink requestPremiumChangeLink;
	public HyperLink overridePremiumLink;
	public HyperLink pushToRMSLink;
	public HyperLink releaseQuoteLink;
	public HyperLink viewModelResultsLink;
	public HyperLink viewOrPrintRateTrace;
	public ButtonControl editFees;

	public ButtonControl requestBind;
	public ButtonControl deleteQuote;
	public ButtonControl editDeductibleAndLimits;

	public HyperLink editLocation;
	public HyperLink editDwelling;
	public HyperLink dwelling3;
	public ButtonControl deleteBuilding;
	public HyperLink addSymbol;
	public HyperLink addNewLocation;
	public HyperLink addNewDwelling;
	public HyperLink editBuilding;
	public ButtonControl deleteDwelling3;
	public ButtonControl deleteDwelling3HIHO;

	public ButtonControl createAnotherQuote;
	public ButtonControl deleteAccount;
	public ButtonControl yesDeleteAccount;
	public HyperLink editInsuredContactInfo;
	public HyperLink editInspectionContact;
	public HyperLink editAdditionalIntersets;
	public HyperLink editPaymentPlan;

	public ButtonControl noReturnToAccountOverview;
	public ButtonControl wantToContinue;
	public BaseWebElementControl pageName;
	public ButtonControl locationStep1;

	public ButtonControl quoteAccountButton;
	public ButtonControl quoteSomeDwellingsButton;
	public BaseWebElementControl quoteNumber;
	public BaseWebElementControl quote1StatusActive;
	public BaseWebElementControl quote2StatusActive;
	public BaseWebElementControl quote1Status;
	public BaseWebElementControl quote3Status;
	public BaseWebElementControl quote4Status;
	public BaseWebElementControl quoteDedDetails;

	public ButtonControl deleteRenewalButton;
	public ButtonControl viewPreviousPolicyButton;
	public ButtonControl releaseRenewalToProducerButton;
	public ButtonControl blockRnlButton;
	public ButtonControl issueQuoteButton;
	public ButtonControl unblockRnlButton;
	public HyperLink producerLink;

	public ButtonControl viewRewritingPolicy;
	public ButtonControl rewriteBind;
	public BaseWebElementControl insuredAcctInfo;
	public ButtonControl saveOrder;
	public ButtonControl unlockAccount;

	public BaseWebElementControl viewPrintFullQuotePage;
	public ButtonControl goBackBtn;
	public BaseWebElementControl emailQuotePage;
	public ButtonControl emailCancel;
	public BaseWebElementControl viewPrintRateTracePage;
	public ButtonControl backBtn;

	public HyperLink quoteSpecifics;
	public HyperLink quoteSpecificsChangeLink;
	public TextFieldControl quoteExpirationDate;
	public BaseWebElementControl ratingEffectiveDate;

	public HyperLink accountDetailsLink;
	public HyperLink editAccountDetails;
	public TextFieldControl accountEffectiveDate;
	public ButtonControl accountDetailsReviewButton;
	public BaseWebElementControl accountName;

	public BaseWebElementControl pendingBORMsg;

	public HyperLink altQuoteOptEarthquakeDed;
	public HyperLink altQuoteOptChosenCoverageOption;
	public BaseWebElementControl priorQuoteDetails;
	public BaseWebElementControl eqDeductibles;
	public BaseWebElementControl coverageOption;
	public BaseWebElementControl quoteOptionsTotalPremium;
	public HyperLink quoteOptions1TotalPremium;
	public HyperLink quoteOptions3TotalPremium;
	public BaseWebElementControl paymentPlan;
	public BaseWebElementControl otherDeductibleOptions;
	public HyperLink quoteOptions4TotalPremium;

	public BaseWebElementControl renewalAdjustmentFactorvalue;
	public BaseWebElementControl premiumValue;
	public BaseWebElementControl feesValue;
	public BaseWebElementControl totalPremiumValue;

	public ButtonControl lapseRenewal;
	public ButtonControl unlapseRenewal;

	public ButtonControl eqDeductiblesCount;
	public ButtonControl eqDeductiblesOptions;
	public ButtonControl chosenCoverageOptions;

	public HyperLink dwelling;
	public BaseWebElementControl noteBarMessage;
	public BaseWebElementControl referredStatus;
	public HyperLink viewBillingInfo;
	public BaseWebElementControl boundStatus;
	public HyperLink noteBar;
	public BaseWebElementControl boundQuoteMessage;
	public BaseWebElementControl quoteStatus;
	public HyperLink quoteStatusData ;
	public BaseWebElementControl quoteStatusHIHO;

	public BaseWebElementControl producerNumber;
	public HyperLink yesDeleteBuilding;
	public BaseWebElementControl dwellingSuccessfullyDeletedMsg;
	public BaseWebElementControl deleteAccountMessage;
	public BaseWebElementControl quoteDocument;
	public HyperLink bindStep4;
	public BaseWebElementControl quoteTreeAopNsAowh;
	public BaseWebElementControl rateTraceForQuote;
	public BaseWebElementControl unlockMessage;
	public BaseWebElementControl noteBarFeeName;

	public BaseWebElementControl quotePremium;
	public BaseWebElementControl issueMessage;
	public HyperLink renewalQuote1;

	public HyperLink quoteLink;
	public HyperLink locationLink;
	public HyperLink deleteLocation;
	public HyperLink renewalOffer;
	public BaseWebElementControl renewalOfferData;
	public BaseWebElementControl newProducerNumberData;
	public BaseWebElementControl producerNumberData;

	public BaseWebElementControl dedOptionsNamedHurricaneValue;
	public BaseWebElementControl dedOptionsTotalPremium;
	public BaseWebElementControl deleteRenewalMsg;
	public HyperLink yesDeletePopup;
	public HyperLink floodLink;
	public HyperLink editFloodLink;
	public ButtonControl quoteExpiredPopupMsg;
	public ButtonControl quoteExpiredPopupMsg1;
	public BaseWebElementControl overridePremiumPage;

	public BaseWebElementControl floodDeductible;
	public HyperLink viewActivePolicy;
	public BaseWebElementControl quoteNumberVPQ;

	public BaseWebElementControl totalInspectionFee;
	public BaseWebElementControl totalPolicyFee;
	public HyperLink buildingLink;
	public HyperLink buildingLink1;

	public TextFieldControl customFieldName;
	public TextFieldControl customFieldValue;
	public TextAreaControl producerFeeComment;
	public ButtonControl customeFeeSave;
	public ButtonControl addCustomFee;
	public BaseWebElementControl apcQuoteData;

	public ButtonControl reasonForRequestArrow;
	public ButtonControl reasonForRequestOptions;
	public BaseWebElementControl premiumQuote;
	public TextFieldControl targetPremium;
	public TextFieldControl competitor;
	public ButtonControl competitorSuggestions;
	public TextAreaControl additionalInformation;
	public TextFieldControl yourName;
	public TextFieldControl yourEmailAddress;
	public ButtonControl rpcCancelBtn;
	public ButtonControl rpcUpdateBtn;
	public BaseWebElementControl originalPremiumData;

	public BaseWebElementControl accountReceivable;
	public BaseWebElementControl accountReceivableData;
	public BaseWebElementControl customersProperty;
	public BaseWebElementControl customersPropertyData;
	public BaseWebElementControl fineArts;
	public BaseWebElementControl fineArtsData;
	public BaseWebElementControl fireExtinguisher;
	public BaseWebElementControl fireExtinguisherData;
	public BaseWebElementControl lockReplacement;
	public BaseWebElementControl lockReplacementData;
	public BaseWebElementControl robbery;
	public BaseWebElementControl robberyData;
	public BaseWebElementControl sewerDrain;
	public BaseWebElementControl sewerDrainData;
	public BaseWebElementControl spoilage;
	public BaseWebElementControl spoilageData;
	public BaseWebElementControl theftDisappearance;
	public BaseWebElementControl theftDisappearanceData;
	public BaseWebElementControl utilityServicesDirectDamage;
	public BaseWebElementControl utilityServicesDirectDamageData;
	public BaseWebElementControl utilityServicesTimeElement;
	public BaseWebElementControl utilityServicesTimeElementData;
	public BaseWebElementControl electronic;
	public BaseWebElementControl electronicData;
	public BaseWebElementControl extendedPeriod;
	public BaseWebElementControl extendedPeriodData;
	public BaseWebElementControl fireDepartmentCharge;
	public BaseWebElementControl fireDepartmentChargeData;
	public BaseWebElementControl newlyAcquiredBpp;
	public BaseWebElementControl newlyAcquiredBppData;
	public BaseWebElementControl constructedProperty;
	public BaseWebElementControl constructedPropertyData;
	public BaseWebElementControl outdoorProperty;
	public BaseWebElementControl outdoorPropertyData;
	public BaseWebElementControl perimeterExtension;
	public BaseWebElementControl perimeterExtensionData;
	public BaseWebElementControl personalEffects;
	public BaseWebElementControl personalEffectsData;
	public BaseWebElementControl propertyinTransit;
	public BaseWebElementControl propertyinTransitData;
	public BaseWebElementControl propertyOffPremises;
	public BaseWebElementControl propertyOffPremisesData;
	public BaseWebElementControl tenantGlass;
	public BaseWebElementControl tenantGlassData;
	public BaseWebElementControl valuablePapersAndRecords;
	public BaseWebElementControl valuablePapersAndRecordsData;
	public BaseWebElementControl businessIncomeextraExpense;
	public BaseWebElementControl businessIncomeextraExpenseData;
	public BaseWebElementControl priorLossInfo;
	public BaseWebElementControl priorLossValue;

	public BaseWebElementControl earthQuakeSpinkler;
	public BaseWebElementControl earthQuakeSpinklerData;
	public BaseWebElementControl equipmentBreakdown;
	public BaseWebElementControl ordinanceOrLaw;
	public BaseWebElementControl ordinanceOrLawData;
	public BaseWebElementControl ordinanceOrLawvalue;
	public BaseWebElementControl windDriven;
	public BaseWebElementControl windDrivenData;
	public BaseWebElementControl terrorismCoverage;
	public BaseWebElementControl terrorismCoverageData;
	public BaseWebElementControl packageA;

	public BaseWebElementControl earthMovement;
	public BaseWebElementControl earthMovementData;
	public BaseWebElementControl propertyWithinCondoUnits;
	public BaseWebElementControl propertyWithinCondoUnitsData;
	public BaseWebElementControl volcanicEruption;
	public BaseWebElementControl volcanicEruptionData;
	public BaseWebElementControl heatMaintain;
	public BaseWebElementControl heatMaintainData;
	public BaseWebElementControl propertyWithinCondo;
	public BaseWebElementControl propertyWithinCondoData;
	public BaseWebElementControl vacancyPermitEntireTerm;
	public BaseWebElementControl vacancyPermitEntireTermData;
	public BaseWebElementControl outdoorTrees;
	public BaseWebElementControl outdoorTreesData;
	public BaseWebElementControl lockedAndSecured;
	public BaseWebElementControl lockedAndSecuredData;
	public BaseWebElementControl fungusWetRotData;

	public HyperLink runningLink;
	public BaseWebElementControl calculating;

	public BaseWebElementControl requestedEffectiveDate;
	public BaseWebElementControl quoteName;
	public BaseWebElementControl priorLossDetails;

	public ButtonControl cancelChanges;
	public ButtonControl cancelYes;
	public BaseWebElementControl userName;

	public HyperLink newLink;
	public ButtonControl selectCatagoryArrow;
	public ButtonControl selectCatagoryOption;
	public TextFieldControl textArea;
	public HyperLink saveLink;
	public HyperLink cancelLink;
	public BaseWebElementControl accountNotePopup;
	public ButtonControl yesBtn;
	public BaseWebElementControl categoryAdded;
	public BaseWebElementControl testNote;
	public HyperLink quoteOptions2TotalPremium;
	public BaseWebElementControl premiumWarningMessage;
	public ButtonControl priorLossEditLink;

	public BaseWebElementControl sltfValue;

	public HyperLink quoteOptTotalPremium;
	public BaseWebElementControl altQuoteName;
	public HyperLink quoteNumberLink;

	public HyperLink deductibleOptions;
	public BaseWebElementControl overrideFactor;

	public HyperLink openReferral;
	public BaseWebElementControl internalRenewal;

	public BaseWebElementControl quoteExpiredStatus;
	public ButtonControl gotoAccountOverviewButton;
	public ButtonControl undeleteQuote;
	public ButtonControl yesButton;
	public ButtonControl viewRenewalDocuments;
	public ButtonControl yesIWantToContinue;
	public BaseWebElementControl internalRenewalStatus;
	public BaseWebElementControl renewalStatus;
	public BaseWebElementControl expiredStatus;
	public BaseWebElementControl inspectionContactDetails;

	public BaseWebElementControl requestedExpirationDate;
	public ButtonControl viewPolicyButton;

	public HyperLink quoteReferred;
	public BaseWebElementControl microzoneData;

	public ButtonControl uploadPreBindDocuments;
	public BaseWebElementControl vieParticipationValue;
	public BaseWebElementControl vieContributionChargeValue;
	public BaseWebElementControl surplusContributionHead;
	public BaseWebElementControl surplusContibutionValue;

	public ButtonControl addDocumentButton;
	public ButtonControl chooseADoc;
	public ButtonControl uploadButton;
	public ButtonControl continueButton;

	public ButtonControl editPencilButton;
	public CheckBoxControl approvedByUSM;
	public ButtonControl updateButton;
	public ButtonControl backButton;
	public BaseWebElementControl documentCategory;
	public HyperLink coverageLink;
	public BaseWebElementControl niDisplay;
	public HyperLink requestESignatureLink;
	public ButtonControl closeSymbol;
	public ButtonControl quoteSomeBuildingsButton;
	public BaseWebElementControl packageData;
	public HyperLink openReferralLink;
	public BaseWebElementControl vieParicipation;
	public BaseWebElementControl vieContributionCharge;
	public BaseWebElementControl surplusContributionValue;
	public ButtonControl valuationReportButton;
	public BaseWebElementControl otherFees;
	public ButtonControl saveButton;
	public TextFieldControl reasonForRequestComments;
	public ButtonControl customFeeDeleteTrashCan;
	public HyperLink editPriorLoss;
	public ButtonControl viewPolicyBtn;
	public BaseWebElementControl icatFees;
	public BaseWebElementControl distanceToCoast;
	public BaseWebElementControl floodZone;
	public BaseWebElementControl inspectionFeeAmount;
	public HyperLink yesCancelChange;
	public BaseWebElementControl totalPremiumRowName;
	public HyperLink quoteLink1;
	public BaseWebElementControl prodInspectionContactInfo;
	public HyperLink editBuildingLink1;
	public BaseWebElementControl buildingValuationValue;
	public BaseWebElementControl buildingValue;
	public ButtonControl goToAccountOverviewPageBtn;
	public BaseWebElementControl priorLoses;
	public HyperLink noDeleteBuiling;
	public ButtonControl unDeleteAccountBtn;
	public HyperLink buildingDelete;
	public HyperLink editBuilding1;
	public HyperLink quoteLink2;
	public HyperLink reofferAccount;
	public HyperLink selectDwelling;
	public BaseWebElementControl address;
	public ButtonControl requestDeductibleBuyBackBtn;
	public HyperLink sl2FormLink;
	public BaseWebElementControl starMarkQuoteNumber;
	public HyperLink requestESignature;
	public CheckBoxControl subscriptionAgreementCheckBox;
	public TextFieldControl signatureEmailBox;
	public TextFieldControl signatureCommentBox;
	public ButtonControl disbaledDocusignSubmitBtn;
	public ButtonControl signatureSendButton;
	public BaseWebElementControl docusignSuccessMessage;
	public BaseWebElementControl requestPremiumChangeReferralMsg;
	public BaseWebElementControl globalError;
	public BaseWebElementControl minPremiumFlag;
	public BaseWebElementControl altQuoteDetails;
	public BaseWebElementControl deductibleOptionsNAHO;
	public BaseWebElementControl acntOverviewQuoteNumber;
	public BaseWebElementControl acntOverviewQuoteName;
	public BaseWebElementControl quoteNo1Holder;
	public BaseWebElementControl quoteNo2Holder;
	public BaseWebElementControl quoteNo3Holder;
	public BaseWebElementControl quoteNoHolder;
	public BaseWebElementControl nhEqVar;
	public HyperLink editDwelling11;
	public HyperLink dwellingName;
	public BaseWebElementControl totalPremiumAmount;
	public BaseWebElementControl totalFeeAmount;
	public BaseWebElementControl aowhLable;
	public BaseWebElementControl coverageValue;
	public BaseWebElementControl coverageValue1;
	public HyperLink dwelling1;
	public HyperLink quoteAccDetails;
	public HyperLink quoteEditAccDetails;
	public BaseWebElementControl quote1Num;
	public HyperLink dwellingLink;
	public BaseWebElementControl referred;
	public BaseWebElementControl notebarText;
	public BaseWebElementControl quoteDeleteMessage;
	public BaseWebElementControl totalPremiumAndFeeAmount;

	public AccountOverviewPage() {
		PageObject pageobject = new PageObject("AccountOverview");
		viewPrintFullQuoteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_view/PrintFullQuote")));
		viewQuoteDetailsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ViewQuoteDetails")));
		emailQuoteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_EmailQuote")));
		requestPremiumChangeLink = new HyperLink(By.xpath(pageobject.getXpath("xp_RequestPremiumChange")));
		overridePremiumLink = new HyperLink(By.xpath(pageobject.getXpath("xp_OverridePremium")));
		pushToRMSLink = new HyperLink(By.xpath(pageobject.getXpath("xp_PushToRMS")));
		releaseQuoteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ReleaseQuote")));
		viewModelResultsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ViewModelResults")));
		viewOrPrintRateTrace = new HyperLink(By.xpath(pageobject.getXpath("xp_ViewOrPrintRateTrace")));
		editFees = new ButtonControl(By.xpath(pageobject.getXpath("xp_EditTaxesAndFees")));

		requestBind = new ButtonControl(By.xpath(pageobject.getXpath("xp_RequestBind")));
		deleteQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeleteQuote")));
		editDeductibleAndLimits = new ButtonControl(By.xpath(pageobject.getXpath("xp_EditDeductiblesAndLimits")));

		editLocation = new HyperLink(By.xpath(pageobject.getXpath("xp_EditLocation")));
		editDwelling = new HyperLink(By.xpath(pageobject.getXpath("xp_EditDwelling")));
		dwelling3 = new HyperLink(By.xpath(pageobject.getXpath("xp_Dwelling3")));
		deleteBuilding = new ButtonControl(By.xpath(pageobject.getXpath("xp_LocBuildDelete")));
		deleteDwelling3 = new ButtonControl(By.xpath(pageobject.getXpath("xp_BuildingDelete")));
		deleteDwelling3HIHO = new ButtonControl(By.xpath(pageobject.getXpath("xp_BuildingDeleteHIHO")));

		addSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_AddSymbol")));
		addNewLocation = new HyperLink(By.xpath(pageobject.getXpath("xp_AddNewLocation")));
		addNewDwelling = new HyperLink(By.xpath(pageobject.getXpath("xp_AddNewDwelling")));
		editBuilding = new HyperLink(By.xpath(pageobject.getXpath("xp_EditBuilding")));

		createAnotherQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_CreateAnotherQuote")));
		deleteAccount = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeleteAccount")));
		yesDeleteAccount = new ButtonControl(By.xpath(pageobject.getXpath("xp_YesDeleteAccount")));
		editInsuredContactInfo = new HyperLink(By.xpath(pageobject.getXpath("xp_EditInsuredContactInfo")));
		editInspectionContact = new HyperLink(By.xpath(pageobject.getXpath("xp_EditInspectionContact")));
		editAdditionalIntersets = new HyperLink(By.xpath(pageobject.getXpath("xp_EditAdditionalIntersets")));
		editPaymentPlan = new HyperLink(By.xpath(pageobject.getXpath("xp_EditPaymentPlan")));

		noReturnToAccountOverview = new ButtonControl(By.xpath(pageobject.getXpath("xp_NoReturnToAccountOverview")));
		wantToContinue = new ButtonControl(By.xpath(pageobject.getXpath("xp_WantToContinue")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
		locationStep1 = new ButtonControl(By.xpath(pageobject.getXpath("xp_LocationStep1")));

		quoteAccountButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteAccountButton")));
		quoteSomeDwellingsButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteSomeDwellingsButton")));
		quoteNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNumber")));
		quote1StatusActive = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Quote1StatusActive")));
		quote2StatusActive = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Quote2StatusActive")));
		quote1Status = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Quote1Status")));
		quote3Status = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Quote3Status")));
		quote4Status = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Quote4Status")));
		quoteDedDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteDedDetails")));

		deleteRenewalButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeleteRenewal")));
		viewPreviousPolicyButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewPreviousPolicy")));
		releaseRenewalToProducerButton = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_ReleaseRenewalToProducer")));
		blockRnlButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_BlockRnl")));
		issueQuoteButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_IssueQuote")));
		unblockRnlButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_UnblockRnl")));
		producerLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ProducerLink")));

		viewRewritingPolicy = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewRewritingpolicy")));
		rewriteBind = new ButtonControl(By.xpath(pageobject.getXpath("xp_Rewritebind")));
		insuredAcctInfo = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Insuredacctinfo")));
		saveOrder = new ButtonControl(By.xpath(pageobject.getXpath("xp_SaveOrder")));
		unlockAccount = new ButtonControl(By.xpath(pageobject.getXpath("xp_UnlockAccount")));

		viewPrintFullQuotePage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ViewPrintFullQuotePage")));
		goBackBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_GoBackBtn")));
		emailQuotePage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EmailQuotePage")));
		emailCancel = new ButtonControl(By.xpath(pageobject.getXpath("xp_EmailCancel")));
		viewPrintRateTracePage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ViewPrintRateTracePage")));
		backBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_BackBtn")));

		quoteSpecifics = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteSpecifics")));
		quoteSpecificsChangeLink = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteSpecificsChangeLink")));
		quoteExpirationDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_QuoteExpirationDate")));
		ratingEffectiveDate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RatingEffectiveDate")));

		accountDetailsLink = new HyperLink(By.xpath(pageobject.getXpath("xp_AccountDetailsLink")));
		editAccountDetails = new HyperLink(By.xpath(pageobject.getXpath("xp_EditAccountDetails")));
		accountEffectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AccountEffectiveDate")));
		accountDetailsReviewButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_AccountDetailsReviewButton")));
		accountName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AccountName")));

		pendingBORMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PendingBORMsg")));

		altQuoteOptEarthquakeDed = new HyperLink(By.xpath(pageobject.getXpath("xp_AltQuoteOptEarthquakeDed")));
		altQuoteOptChosenCoverageOption = new HyperLink(
				By.xpath(pageobject.getXpath("xp_AltQuoteOptChosenCoverageOption")));
		priorQuoteDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorQuoteDetails")));
		eqDeductibles = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EQDeductibles")));
		coverageOption = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CoverageOptions")));
		quoteOptionsTotalPremium = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_QuoteOptionsTotalPremium")));
		quoteOptions1TotalPremium = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteOptions1TotalPremium")));
		quoteOptions3TotalPremium = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteOptions3TotalPremium")));
		paymentPlan = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PaymentPlan")));
		otherDeductibleOptions = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OtherDeductibleOptions")));
		quoteOptions4TotalPremium = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteOptions4TotalPremium")));

		renewalAdjustmentFactorvalue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_renewalAdjustmentFactorvalue")));
		premiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumValue")));
		feesValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FeesValue")));
		totalPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalPremiumValue")));

		lapseRenewal = new ButtonControl(By.xpath(pageobject.getXpath("xp_LapseRenewal")));
		unlapseRenewal = new ButtonControl(By.xpath(pageobject.getXpath("xp_UnlapseRenewal")));
		eqDeductiblesCount = new ButtonControl(By.xpath(pageobject.getXpath("xp_EQDeductiblesCount")));
		eqDeductiblesOptions = new ButtonControl(By.xpath(pageobject.getXpath("xp_EQDeductiblesOptions")));
		chosenCoverageOptions = new ButtonControl(By.xpath(pageobject.getXpath("xp_ChosenCoverageOptions")));

		dwelling = new HyperLink(By.xpath(pageobject.getXpath("xp_Dwelling")));

		noteBarMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NoteBarMessage")));
		referredStatus = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReferredStatus")));
		viewBillingInfo = new HyperLink(By.xpath(pageobject.getXpath("xp_ViewBillingInfo")));
		boundStatus = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BindStatus")));
		noteBar = new HyperLink(By.xpath(pageobject.getXpath("xp_NoteBar")));
		boundQuoteMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BoundQuoteMessage")));
		quoteStatus = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteStatus")));
		quoteStatusData  = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteStatus")));
		quoteStatusHIHO = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteStatusHIHO")));
		producerNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducerNumber")));
		yesDeleteBuilding = new HyperLink(By.xpath(pageobject.getXpath("xp_YesDeleteBuilding")));
		dwellingSuccessfullyDeletedMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DwellingsuccessfullyDeletedMsg")));
		deleteAccountMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DeleteAccountMessage")));
		quoteDocument = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteDocument")));
		bindStep4 = new HyperLink(By.xpath(pageobject.getXpath("xp_BindStep4")));
		quoteTreeAopNsAowh = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteTreeAopNsAowh")));
		rateTraceForQuote = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RateTraceForQuote")));
		unlockMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Unlockmessage")));
		noteBarFeeName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NoteBarFeename")));
		quotePremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuotePremium")));

		issueMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_IssueMessage")));
		renewalQuote1 = new HyperLink(By.xpath(pageobject.getXpath("xp_RenewalQuote1")));

		quoteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteLink")));
		locationLink = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationLink")));
		deleteLocation = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteLocation")));
		renewalOffer = new HyperLink(By.xpath(pageobject.getXpath("xp_RenewalOffer")));
		renewalOfferData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RenewalOfferData")));
		newProducerNumberData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewProducerNumberData")));
		producerNumberData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducerNumberData")));

		dedOptionsNamedHurricaneValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DedOptionsNamedHurricaneValue")));
		dedOptionsTotalPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DedOptionsTotalPremium")));
		deleteRenewalMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DeleteRenewalmsg")));
		yesDeletePopup = new HyperLink(By.xpath(pageobject.getXpath("xp_YesDeletePopup")));
		floodLink = new HyperLink(By.xpath(pageobject.getXpath("xp_FloodLink")));
		editFloodLink = new HyperLink(By.xpath(pageobject.getXpath("xp_EditFloodLink")));
		quoteExpiredPopupMsg = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteExpiredPopupMsg")));
		quoteExpiredPopupMsg1 = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteExpiredPopupMsg1")));
		overridePremiumPage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OverridePremiumPage")));

		floodDeductible = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FloodDed")));
		viewActivePolicy = new HyperLink(By.xpath(pageobject.getXpath("xp_ViewActivePolicy")));
		quoteNumberVPQ = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteViewPrintFullQuote")));

		totalInspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalInspectionFee")));
		totalPolicyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalPolicyFee")));
		buildingLink = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingLink")));
		buildingLink1 = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingLink1")));

		customFieldName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CustomFeeName")));
		customFieldValue = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CustomFeeValue")));
		producerFeeComment = new TextAreaControl(By.xpath(pageobject.getXpath("xp_ProducerComment")));
		customeFeeSave = new ButtonControl(By.xpath(pageobject.getXpath("xp_CustomFeeSave")));
		addCustomFee = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddCustomFee")));
		apcQuoteData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_APCQuoteData")));

		reasonForRequestArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReasonForRequestArrow")));
		reasonForRequestOptions = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReasonForRequestOptions")));
		premiumQuote = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumQuote")));
		targetPremium = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TargetPremium")));
		competitor = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Competitor")));
		competitorSuggestions = new ButtonControl(By.xpath(pageobject.getXpath("xp_CompetitorSuggestions")));
		additionalInformation = new TextAreaControl(By.xpath(pageobject.getXpath("xp_AdditionalInformationTextArea")));
		yourName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_YourName")));
		yourEmailAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_YourEmailAddress")));
		rpcCancelBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_RPCCancelBtn")));
		rpcUpdateBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_RPCUpdateBtn")));
		originalPremiumData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OriginalPremiumAmount")));

		accountReceivable = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AccountReceivable")));
		accountReceivableData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AccountReceivableData")));
		customersProperty = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CustomersProperty")));
		customersPropertyData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CustomersPropertyData")));
		fineArts = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FineArts")));
		fineArtsData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FineArtsData")));
		fireExtinguisher = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FireExtinguisher")));
		fireExtinguisherData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FireExtinguisherData")));
		lockReplacement = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LockReplacement")));
		lockReplacementData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LockReplacementData")));
		robbery = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Robbery")));
		robberyData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RobberyData")));
		sewerDrain = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SewerDrain")));
		sewerDrainData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SewerDrainData")));
		spoilage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Spoilage")));
		spoilageData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SpoilageData")));
		theftDisappearance = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TheftDisappearance")));
		theftDisappearanceData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TheftDisappearanceData")));
		utilityServicesDirectDamage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_UtilityServicesDirectDamage")));
		utilityServicesDirectDamageData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_UtilityServicesDirectDamageData")));
		utilityServicesTimeElement = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_UtilityServicesTimeElement")));
		utilityServicesTimeElementData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_UtilityServicesTimeElementData")));
		electronic = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Electronic")));
		electronicData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ElectronicData")));
		extendedPeriod = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ExtendedPeriod")));
		extendedPeriodData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ExtendedPeriodData")));
		fireDepartmentCharge = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FireDepartmentCharge")));
		fireDepartmentChargeData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_FireDepartmentChargeData")));
		newlyAcquiredBpp = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewlyAcquiredBpp")));
		newlyAcquiredBppData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NewlyAcquiredBppData")));
		constructedProperty = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ConstructedProperty")));
		constructedPropertyData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ConstructedPropertyData")));
		outdoorProperty = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OutdoorProperty")));
		outdoorPropertyData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OutdoorPropertyData")));
		perimeterExtension = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PerimeterExtension")));
		perimeterExtensionData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PerimeterExtensionData")));
		personalEffects = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PersonalEffects")));
		personalEffectsData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PersonalEffectsData")));
		propertyinTransit = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyinTransit")));
		propertyinTransitData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyinTransitData")));
		propertyOffPremises = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyOffPremises")));
		propertyOffPremisesData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PropertyOffPremisesData")));
		tenantGlass = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TenantGlass")));
		tenantGlassData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TenantGlassData")));
		valuablePapersAndRecords = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ValuablePapersAndRecords")));
		valuablePapersAndRecordsData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ValuablePapersAndRecordsData")));
		businessIncomeextraExpense = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BusinessIncomeextraExpense")));
		businessIncomeextraExpenseData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_BusinessIncomeextraExpenseData")));
		priorLossInfo = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossInfo")));
		priorLossValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossValue")));

		earthQuakeSpinkler = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EarthQuakeSpinkler")));
		earthQuakeSpinklerData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EarthQuakeSpinklerData")));
		equipmentBreakdown = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EquipmentBreakdown")));
		ordinanceOrLaw = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OrdinanceOrLaw")));
		ordinanceOrLawData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OrdinanceOrLawData")));
		ordinanceOrLawvalue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OrdinanceOrLawvalue")));
		windDriven = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindDriven")));
		windDrivenData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WindDrivenData")));
		terrorismCoverage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TerrorismCoverage")));
		terrorismCoverageData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TerrorismCoverageData")));
		packageA = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageA")));

		earthMovement = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EarthMovement")));
		earthMovementData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EarthMovementData")));
		propertyWithinCondoUnits = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PropertyWithinCondoUnits")));
		propertyWithinCondoUnitsData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PropertyWithinCondoUnitsData")));
		volcanicEruption = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_VolcanicEruption")));
		volcanicEruptionData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_VolcanicEruptionData")));
		heatMaintain = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_HeatMaintain")));
		heatMaintainData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_HeatMaintainData")));
		propertyWithinCondo = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyWithinCondo")));
		propertyWithinCondoData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PropertyWithinCondoData")));
		vacancyPermitEntireTerm = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_VacancyPermitEntireTerm")));
		vacancyPermitEntireTermData = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_VacancyPermitEntireTermData")));
		outdoorTrees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OutdoorTrees")));
		outdoorTreesData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OutdoorTreesData")));
		lockedAndSecured = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LockedAndSecured")));
		lockedAndSecuredData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LockedAndSecuredData")));
		fungusWetRotData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FungusWetRotData")));

		runningLink = new HyperLink(By.xpath(pageobject.getXpath("xp_RunningLink")));
		calculating = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Calculating")));

		requestedEffectiveDate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RequestedEffectiveDate")));
		quoteName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteName")));
		priorLossDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossDetails")));
		cancelChanges = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelChanges")));
		cancelYes = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelYes")));
		userName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_UserName")));

		newLink = new HyperLink(By.xpath(pageobject.getXpath("xp_NewLink")));
		selectCatagoryArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_SelectCatagoryArrow")));
		selectCatagoryOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_SelectCatagoryOption")));
		textArea = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TextArea")));
		saveLink = new HyperLink(By.xpath(pageobject.getXpath("xp_SaveLink")));
		cancelLink = new HyperLink(By.xpath(pageobject.getXpath("xp_CancelLink")));
		accountNotePopup = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AccountNotePopup")));
		yesBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_YesBtn")));
		categoryAdded = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CatagoryAdded")));
		testNote = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TestNote")));
		quoteOptions2TotalPremium = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteOptions2TotalPremium")));
		premiumWarningMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumWarningMessage")));
		priorLossEditLink = new ButtonControl(By.xpath(pageobject.getXpath("xp_PriorLossEditLink")));

		sltfValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SLTFValue")));

		quoteOptTotalPremium = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteOptTotalPremium")));
		altQuoteName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AltQuoteName")));
		quoteNumberLink = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteNumberLink")));

		deductibleOptions = new HyperLink(By.xpath(pageobject.getXpath("xp_DeductibleOptions")));
		deductibleOptionsNAHO = new HyperLink(By.xpath(pageobject.getXpath("xp_DeductibleOptionsNAHO")));
		overrideFactor = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OverrideFactor")));
		openReferral = new HyperLink(By.xpath(pageobject.getXpath("xp_OpenReferral")));
		internalRenewal = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InternalRenewal")));
		quoteExpiredStatus = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteExpiredStatus")));
		gotoAccountOverviewButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_GotoAccountOverviewButton")));
		undeleteQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_UndeleteQuote")));
		yesButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_YesButton")));
		viewRenewalDocuments = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewRenewalDocuments")));
		yesIWantToContinue = new ButtonControl(By.xpath(pageobject.getXpath("xp_YesIWantToContinue")));
		internalRenewalStatus = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InternalRenewalStatus")));
		renewalStatus = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RenewalStatus")));
		expiredStatus = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ExpiredStatus")));
		inspectionContactDetails = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_InspectionContactDetails")));
		requestedExpirationDate = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_RequestedExpirationDate")));
		viewPolicyButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewPolicyButton")));

		quoteReferred = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteReferred")));
		microzoneData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MicrozoneData")));

		uploadPreBindDocuments = new ButtonControl(By.xpath(pageobject.getXpath("xp_UploadPreBindDocuments")));
		vieParticipationValue = new BaseWebElementControl((By.xpath(pageobject.getXpath("xp_VieParticipationValue"))));
		vieContributionChargeValue = new BaseWebElementControl((By.xpath(pageobject.getXpath("xp_VieContribution"))));
		surplusContributionHead = new BaseWebElementControl(
				(By.xpath(pageobject.getXpath("xp_SurplusContributionHead"))));
		surplusContibutionValue = new BaseWebElementControl(
				(By.xpath(pageobject.getXpath("xp_SurplusContibutionValue"))));

		addDocumentButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddDocumentButton")));
		chooseADoc = new ButtonControl(By.xpath(pageobject.getXpath("xp_ChooseADoc")));
		uploadButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_UploadButton")));
		continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueButton")));

		editPencilButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_EditPencil")));
		approvedByUSM = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_ApprovedByUSM")));
		updateButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_UpdateButton")));
		backButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_BackButton")));
		documentCategory = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DocumentCategory")));

		coverageLink = new HyperLink(By.xpath(pageobject.getXpath("xp_CoverageLink")));
		niDisplay = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NIDisplay")));
		requestESignatureLink = new HyperLink(By.xpath(pageobject.getXpath("xp_RequestESignatureLink")));
		closeSymbol = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseSymbol")));
		quoteSomeBuildingsButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteSomeBuildingsButton")));
		packageData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PackageData")));
		openReferralLink = new HyperLink(By.xpath(pageobject.getXpath("xp_OpenReferralLink")));
		vieParicipation = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_VIEParicipation")));
		vieContributionCharge = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_VIEContributionCharge")));
		valuationReportButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ValuationReportButton")));
		otherFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OtherFees")));
		surplusContributionValue = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SurplusContributionValue")));
		saveButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_SaveButton")));
		reasonForRequestComments = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ReasonForRequestComments")));
		customFeeDeleteTrashCan = new ButtonControl(By.xpath(pageobject.getXpath("xp_CustomFeeDeleteTrashCan")));
		editPriorLoss = new HyperLink(By.xpath(pageobject.getXpath("xp_EditPriorLoss")));
		viewPolicyBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_ViewPolicy")));
		icatFees = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ICATFees")));
		distanceToCoast = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DistanceToCoast")));
		floodZone = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_FloodZone")));
		inspectionFeeAmount = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_InspectionFeeAmount")));
		yesCancelChange = new HyperLink(By.xpath(pageobject.getXpath("xp_YesCancelChange")));
		totalPremiumRowName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalPremiumRowName")));
		quoteLink1 = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteLink1")));
		prodInspectionContactInfo = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_InspectionContactInfo")));
		editBuildingLink1 = new HyperLink(By.xpath(pageobject.getXpath("xp_Building(1-1)Link")));
		buildingValuationValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BuildingValuationValue")));
		buildingValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BuildingValue")));
		priorLoses = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLoses")));
		goToAccountOverviewPageBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_GoToAccountOverviewPageBtn")));
		noDeleteBuiling = new HyperLink(By.xpath(pageobject.getXpath("xp_NoDeleteBuiling")));
		unDeleteAccountBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_UnDeleteAccountBtn")));
		editBuilding1 = new HyperLink(By.xpath(pageobject.getXpath("xp_EditBuilding1")));
		quoteLink2 = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteLink2")));
		reofferAccount = new HyperLink(By.xpath(pageobject.getXpath("xp_ReofferAccount")));
		selectDwelling = new HyperLink(By.xpath(pageobject.getXpath("xp_SelectDwelling")));
		address = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_address")));
		requestDeductibleBuyBackBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_rdbbbutton")));
		sl2FormLink = new HyperLink(By.xpath(pageobject.getXpath("xp_SL2FormLink")));
		starMarkQuoteNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_StarMarkQuoteNumber")));
		requestESignature = new HyperLink(By.xpath(pageobject.getXpath("xp_RequestESignature")));
		signatureEmailBox = new TextFieldControl(By.xpath(pageobject.getXpath("xp_SignatureEmailBox")));
		signatureCommentBox = new TextFieldControl(By.xpath(pageobject.getXpath("xp_SignatureCommentBox")));
		subscriptionAgreementCheckBox = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_SubscriptionAgreementCheckBox")));
		disbaledDocusignSubmitBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_DisbaledDocusignSubmitBtn")));
		signatureSendButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_SignatureSendButton")));
		docusignSuccessMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DocusignSuccessMessage")));
		requestPremiumChangeReferralMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_RequestPremiumChangeReferralMsg")));
		globalError = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GlobalError")));
		altQuoteDetails = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AltQuoteDetails")));
		minPremiumFlag = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MinPremiumFlag")));
		acntOverviewQuoteNumber = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AcntOverviewQuoteNumber")));
		acntOverviewQuoteName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AcntOverviewQuoteName")));
		quoteNo1Holder = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNo1Holder")));
		quoteNo2Holder = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNo2Holder")));
		quoteNo3Holder = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNo3Holder")));
		quoteNoHolder = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNoHolder")));
		aowhLable = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AOWHLable")));
		nhEqVar = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NHEqVar")));
		editDwelling11 = new HyperLink(By.xpath(pageobject.getXpath("xp_Dwelling11")));
		dwellingName = new HyperLink(By.xpath(pageobject.getXpath("xp_DwellingName")));
		totalPremiumAmount = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalPremiumAmount")));
		totalFeeAmount = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalFeeAmount")));
		coverageValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CoverageValue")));
		coverageValue1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CoverageValue1")));
		dwelling1 = new HyperLink(By.xpath(pageobject.getXpath("xp_Dwelling1")));
		quoteAccDetails = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteAccDetailsLink")));
		quoteEditAccDetails = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteEditAccDetails")));
		quote1Num = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Quote1")));
		dwellingLink = new HyperLink(By.xpath(pageobject.getXpath("xp_DwellingLink")));
		referred = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Referred")));
		notebarText = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NotebarText")));
		quoteDeleteMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DeleteMessage")));
		totalPremiumAndFeeAmount = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_TotalPremiumFeeAmount")));
	}

	public RequestBindPage clickOnRequestBind(Map<String, String> data, String quotenumber) {
		requestBind.scrollToElement();
		requestBind.click();

		// if quote docs are required, add them
		if (pageName.getData().contains("Quote Documents")) {
			QuoteDocumentsPage quoteDocumentsPage = new QuoteDocumentsPage();

			Boolean dueDiligenceRequired = quoteDocumentsPage.requiredDocsInfo.getData().contains("Due Diligence");

			quoteDocumentsPage.uploadQuoteDocs("Subscription Agreement");

			waitTime(12);// adding wait time to load the element
			quoteDocumentsPage.backButton.waitTillPresenceOfElement(60);
			quoteDocumentsPage.backButton.waitTillVisibilityOfElement(60);
			quoteDocumentsPage.backButton.scrollToElement();
			quoteDocumentsPage.backButton.click();

			if (dueDiligenceRequired) {
				uploadPreBindDocuments.scrollToElement();
				uploadPreBindDocuments.click();
				quoteDocumentsPage.uploadQuoteDocs("Due Diligence");
				waitTime(3);
				quoteDocumentsPage.backButton.waitTillPresenceOfElement(60);
				quoteDocumentsPage.backButton.waitTillVisibilityOfElement(60);
				quoteDocumentsPage.backButton.scrollToElement();
				quoteDocumentsPage.backButton.click();
			}

			if (requestBind.checkIfElementIsPresent()) {
				requestBind.scrollToElement();
				requestBind.click();
			}
		}

		if (pageName.getData().contains("Request Bind")) {
			return new RequestBindPage();
		}
		return null;
	}

	public void requestPremiumChanges(Map<String, String> testData) {
		premiumQuote.waitTillVisibilityOfElement(60);
		reasonForRequestArrow.scrollToElement();
		reasonForRequestArrow.click();
		reasonForRequestOptions.formatDynamicPath(testData.get("PremiumAdjustment_Reason")).click();
		Assertions.passTest("Request Premium Change Page",
				"Premium Request Reason is " + testData.get("PremiumAdjustment_Reason"));
		targetPremium.setData(testData.get("PremiumAdjustment_TargetPremium"));
		Assertions.passTest("Request Premium Change Page",
				"Adjusted Premium Value is " + testData.get("PremiumAdjustment_TargetPremium"));
		competitor.setData(testData.get("PremiumAdjustment_Competitor"));
		competitor.tab();
		// competitorSuggestions.formatDynamicPath(testData.get("PremiumAdjustment_Competitor")).click();
		additionalInformation.setData(testData.get("PremiumAdjustment_Description"));

		if (reasonForRequestComments.checkIfElementIsPresent()
				&& reasonForRequestComments.checkIfElementIsDisplayed()) {
			reasonForRequestComments.scrollToElement();
			reasonForRequestComments.appendData(testData.get("PremiumAdjustment_Reason_Comments"));
		}
		yourName.scrollToElement();
		yourName.setData(testData.get("PremiumAdjustment_Name"));
		yourEmailAddress.scrollToElement();
		yourEmailAddress.setData(testData.get("PremiumAdjustment_Email"));
		rpcUpdateBtn.scrollToElement();
		rpcUpdateBtn.click();
		Assertions.passTest("Request Premium Change Page", "Premium change request details entered successfully");
		calculating.waitTillInVisibilityOfElement(60);
	}

	public String approveRenewalReferralProducer(Map<String, String> Data) {
		// Approve producer referral
		ReferQuotePage referQuotePage = new ReferQuotePage();
		if (referQuotePage.referralMessages.formatDynamicPath("modeling service results").checkIfElementIsPresent()
				&& referQuotePage.referralMessages.formatDynamicPath("modeling service results")
						.checkIfElementIsDisplayed()) {
			Assertions.verify(
					referQuotePage.referralMessages.formatDynamicPath("modeling service results")
							.checkIfElementIsDisplayed(),
					true, "Refer Quote Page", "Refer Quote page loaded successfully", false, false);
		}

		// Enter Referral Contact Details
		referQuotePage.contactName.setData(Data.get("ProducerName"));
		referQuotePage.contactEmail.setData(Data.get("ProducerEmail"));
		referQuotePage.referQuote.click();

		// verifying referral message
		Assertions.verify(referQuotePage.quoteForRef.checkIfElementIsDisplayed(), true, "Referral Page",
				"Quote " + referQuotePage.quoteForRef.getData() + " referring to USM " + " is verified", false, false);
		String quoteNum = referQuotePage.quoteForRef.getData();

		// Sign in out as producer
		HomePage homePage = new HomePage();
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Logged out as Producer successfully");

		// Sign in as USM
		LoginPage loginPage = new LoginPage();
		loginPage.refreshPage();
		loginPage.enterLoginDetails(EnvironmentDetails.getEnvironmentDetails().getString("test.login.usm.userName"),
				EnvironmentDetails.getEnvironmentDetails().getString("test.login.usm.password"));
		Assertions.passTest("Home Page", "Logged in as USM Successfully");

		// Searching the quote number in grid and clicking on the quote number
		// link
		homePage.searchQuote(quoteNum);
		Assertions.passTest("Home Page", "Quote for referral is searched successfully");
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
		accountOverviewPage.openReferralLink.scrollToElement();
		accountOverviewPage.openReferralLink.click();

		// approving referral
		ReferralPage referralPage = new ReferralPage();
		referralPage.clickOnApprove();

		// click on approve in Approve Decline Quote page
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		approveDeclineQuotePage.clickOnApprove();
		Assertions.passTest("Referral Page", "Referral request approved successfully");

		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.searchQuote(quoteNum);

		return quoteNum;
	}

	public void approveRenewalReferralUSM(String quote) {
		// click on open referral link
		if (openReferral.checkIfElementIsPresent() && openReferral.checkIfElementIsDisplayed()) {
			openReferral.scrollToElement();
			openReferral.click();
		}

		// click on pick up button
		ReferralPage referralPage = new ReferralPage();
		if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
			referralPage.pickUp.scrollToElement();
			referralPage.pickUp.click();
		}

		// verifying the referral reason
		/*
		 * if (referralPage.producerQuoteStatus.formatDynamicPath("Service is down").
		 * checkIfElementIsDisplayed() &&
		 * referralPage.producerQuoteStatus.formatDynamicPath("No match found").
		 * checkIfElementIsDisplayed() && referralPage.producerQuoteStatus.
		 * formatDynamicPath("Results above maximum value") .checkIfElementIsDisplayed()
		 * && referralPage.producerQuoteStatus.formatDynamicPath("Missing information")
		 * .checkIfElementIsDisplayed()) { Assertions.passTest("Referral Page",
		 * "The Referral Reason displayed is " +
		 * referralPage.producerQuoteStatus.formatDynamicPath("Service is down").getData
		 * ());
		 */

		// approving referral
		referralPage.clickOnApprove();
		// }

		// Approve referral
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		approveDeclineQuotePage.clickOnApprove();
		Assertions.passTest("Referral Page", "Referral request approved successfully");
		HomePage homePage = new HomePage();
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.searchQuote(quote);
	}

	public RequestBindPage clickOnRewriteBind(Map<String, String> Data, String quotenumber) {
		if (uploadPreBindDocuments.checkIfElementIsPresent() && uploadPreBindDocuments.checkIfElementIsDisplayed()) {
			uploadPreBindDocuments.scrollToElement();
			uploadPreBindDocuments.click();

			if (pageName.getData().contains("Quote Documents")) {
				PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();

				policyDocumentsPage.addDocumentButton.scrollToElement();
				policyDocumentsPage.addDocumentButton.click();
				if (quotenumber.startsWith("SC") || quotenumber.startsWith("CT")) {
					policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
					waitTime(2);
					policyDocumentsPage.addDocumentButton.waitTillPresenceOfElement(60);
					policyDocumentsPage.addDocumentButton.scrollToElement();
					policyDocumentsPage.addDocumentButton.click();
					waitTime(2);
					policyDocumentsPage.documentCategoryArrow.scrollToElement();
					policyDocumentsPage.documentCategoryArrow.click();
					policyDocumentsPage.documentCategoryOptions.formatDynamicPath("Subscription Agreement")
							.scrollToElement();
					policyDocumentsPage.documentCategoryOptions.formatDynamicPath("Subscription Agreement").click();
				}

				if (!Data.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
				}
				waitTime(12);// adding wait time to load the element
				policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
				policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
				policyDocumentsPage.backButton.scrollToElement();
				policyDocumentsPage.backButton.click();
				rewriteBind.scrollToElement();
				rewriteBind.click();
			}
		}
		if (pageName.getData().contains("Request Bind")) {
			return new RequestBindPage();
		}
		return null;
	}

	public void uploadPreBindApproveAsUSM() {
		waitTime(2);
		if (uploadPreBindDocuments.checkIfElementIsPresent() && uploadPreBindDocuments.checkIfElementIsDisplayed()) {
			uploadPreBindDocuments.scrollToElement();
			uploadPreBindDocuments.click();
			editPencilButton.formatDynamicPath("Subscription Agreement").waitTillPresenceOfElement(60);
			editPencilButton.formatDynamicPath("Subscription Agreement").scrollToElement();
			editPencilButton.formatDynamicPath("Subscription Agreement").click();

			waitTime(2);
			approvedByUSM.waitTillPresenceOfElement(60);
			approvedByUSM.scrollToElement();
			approvedByUSM.select();

			waitTime(2);
			updateButton.scrollToElement();
			updateButton.click();

			if (documentCategory.checkIfElementIsPresent() && documentCategory.checkIfElementIsDisplayed()) {
				editPencilButton.formatDynamicPath("Due Diligence").waitTillPresenceOfElement(60);
				editPencilButton.formatDynamicPath("Due Diligence").scrollToElement();
				editPencilButton.formatDynamicPath("Due Diligence").click();
				waitTime(2);
				approvedByUSM.waitTillPresenceOfElement(60);
				approvedByUSM.scrollToElement();
				approvedByUSM.select();

				waitTime(2);
				updateButton.scrollToElement();
				updateButton.click();
			}
			waitTime(2);
			backButton.scrollToElement();
			backButton.click();

		}
		if (openReferral.checkIfElementIsPresent() && openReferral.checkIfElementIsDisplayed()) {
			openReferral.scrollToElement();
			openReferral.click();
		}

	}

	public RequestBindPage uploadSubscriptionDocument(Map<String, String> testData, String quotenumber,
			List<Map<String, String>> data) {

		if (uploadPreBindDocuments.checkIfElementIsPresent() && uploadPreBindDocuments.checkIfElementIsDisplayed()) {

			uploadPreBindDocuments.scrollToElement();
			uploadPreBindDocuments.click();

			if (pageName.getData().contains("Quote Documents")) {
				PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();

				policyDocumentsPage.addDocumentButton.scrollToElement();
				policyDocumentsPage.addDocumentButton.click();
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
					waitTime(3);
					// policyDocumentsPage.uploadButtonUAT.scrollToElement();
					// policyDocumentsPage.uploadButtonUAT.click();
				}
				// According to the the 'for loop' adjust the document category in the testdata
				// sheet if duplicate documents are uploaded or test failed.
				for (int i = 1; i <= 2; i++) {
					int dataValuei = i;
					Map<String, String> testDatai = data.get(dataValuei);
					waitTime(12);// adding wait time to load the element
					policyDocumentsPage.addDocumentButton.scrollToElement();
					policyDocumentsPage.addDocumentButton.click();

					waitTime(3);
					String fileName = testData.get("FileNameToUpload");
					String uploadFileDir;
					uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
					policyDocumentsPage.chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());

					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
					policyDocumentsPage.waitTime(3);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).waitTillButtonIsClickable(90);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
					policyDocumentsPage.categoryOptions.formatDynamicPath(testDatai.get("DocumentCategory"))
							.scrollToElement();
					policyDocumentsPage.waitTime(2);
					policyDocumentsPage.categoryOptions.formatDynamicPath(testDatai.get("DocumentCategory")).click();
					waitTime(3);
					policyDocumentsPage.uploadButtonUAT.scrollToElement();
					policyDocumentsPage.uploadButtonUAT.click();
				}
				waitTime(3);
				policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
				policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
				policyDocumentsPage.backButton.scrollToElement();
				policyDocumentsPage.backButton.click();
				requestBind.scrollToElement();
				requestBind.click();

			}
		}
		return null;
	}

	public RequestBindPage clickOnRenewalRequestBindNAHO(Map<String, String> Data, String quotenumber) {

		if (lapseRenewal.checkIfElementIsPresent() && lapseRenewal.checkIfElementIsDisplayed()) {
			AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
			accountOverviewPage.requestBind.scrollToElement();
			accountOverviewPage.requestBind.click();
			if (pageName.getData().contains("Quote Documents")) {

				PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();

				// Commenting as Reciprocal is not available for Renewals
				if (quotenumber.startsWith("SC") || quotenumber.startsWith("CT")) {
					policyDocumentsPage.addDocumentButton.scrollToElement();
					policyDocumentsPage.addDocumentButton.click();
					if (!Data.get("FileNameToUpload").equals("")) {
						String fileName = Data.get("FileNameToUpload");
						if (StringUtils.isBlank(fileName)) {
							Assertions.failTest("Upload File", "Filename is blank");
						}
						String uploadFileDir = EnvironmentDetails.getEnvironmentDetails()
								.getString("test.file.uploadpath");
						waitTime(8);
						if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
								&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
							policyDocumentsPage.chooseDocument
									.setData(new File(uploadFileDir + fileName).getAbsolutePath());
							waitTime(5);// Adding wait time to load the element
							System.out.println("Choose document");
						} else {
							policyDocumentsPage.chooseFile
									.setData(new File(uploadFileDir + fileName).getAbsolutePath());
							waitTime(5);// Adding wait time to load the element

						}
					}
					// policyDocumentsPage.fileUpload("TC_78Code.txt");
					waitTime(3);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
					waitTime(3);
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Due Diligence", 2)
							.scrollToElement();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Due Diligence", 2).click();
					policyDocumentsPage.uploadButtonUAT.waitTillPresenceOfElement(60);
					policyDocumentsPage.uploadButtonUAT.scrollToElement();
					policyDocumentsPage.uploadButtonUAT.click();
					waitTime(3);
				}

				policyDocumentsPage.addDocumentButton.scrollToElement();
				policyDocumentsPage.addDocumentButton.click();
				if (!Data.get("FileNameToUpload").equals("")) {
					String fileName = Data.get("FileNameToUpload");
					if (StringUtils.isBlank(fileName)) {
						Assertions.failTest("Upload File", "Filename is blank");
					}
					String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
					waitTime(8);
					if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
							&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
						policyDocumentsPage.chooseDocument
								.setData(new File(uploadFileDir + fileName).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element
						System.out.println("Choose document");
					} else {
						policyDocumentsPage.chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element

					}

					waitTime(3);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
					waitTime(3);
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
							.scrollToElement();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
							.click();
					policyDocumentsPage.uploadButtonUAT.waitTillPresenceOfElement(60);
					policyDocumentsPage.uploadButtonUAT.scrollToElement();
					policyDocumentsPage.uploadButtonUAT.click();
					Assertions.passTest("Policy Documents Page", "File Uploaded successfully");
				}
				waitTime(8);// adding wait time to load the element
				policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
				policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
				policyDocumentsPage.backButton.scrollToElement();
				policyDocumentsPage.backButton.click();
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}
			if (pageName.getData().contains("Request Bind")) {
				return new RequestBindPage();
			}
		}
		return null;
	}

	public RequestBindPage clickOnRenewalRequestBind(Map<String, String> Data, String quotenumber) {

		if (lapseRenewal.checkIfElementIsPresent() && lapseRenewal.checkIfElementIsDisplayed()) {
			requestBind.scrollToElement();
			requestBind.click();
			if (pageName.getData().contains("Quote Documents")) {

				PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
				policyDocumentsPage.addDocumentButton.scrollToElement();
				policyDocumentsPage.addDocumentButton.click();
				if (quotenumber.startsWith("SC") || quotenumber.startsWith("CT")) {
					policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
					waitTime(2);
					policyDocumentsPage.addDocumentButton.waitTillPresenceOfElement(60);
					policyDocumentsPage.addDocumentButton.scrollToElement();
					policyDocumentsPage.addDocumentButton.click();
					waitTime(2);
					policyDocumentsPage.documentCategoryArrow.scrollToElement();
					policyDocumentsPage.documentCategoryArrow.click();
					policyDocumentsPage.documentCategoryOptions.formatDynamicPath("Subscription Agreement")
							.scrollToElement();
					policyDocumentsPage.documentCategoryOptions.formatDynamicPath("Subscription Agreement").click();
				}
				if (!Data.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
					Assertions.passTest("Policy Documents Page", "File Uploaded successfully");
				}
				waitTime(12);// adding wait time to load the element
				policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
				policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
				policyDocumentsPage.backButton.scrollToElement();
				policyDocumentsPage.backButton.click();
				requestBind.scrollToElement();
				requestBind.click();
			}
		}

		if (pageName.getData().contains("Request Bind")) {
			return new RequestBindPage();
		}
		return null;
	}

}
