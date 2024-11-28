/** Program Description: Object Locators and methods defined in Request bind page
 *  Author			   : SMNetserv
 *  Date of Creation   : 07/11/2017
 **/

package com.icat.epicenter.pom;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.DateConversions;
import com.NetServAutomationFramework.generic.DropDownControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.NetServAutomationFramework.util.FrameworkUtil;

public class RequestBindPage extends BasePageControl {
	public BaseWebElementControl quoteNumber;
	public BaseWebElementControl quotePremium;

	public TextFieldControl effectiveDate;
	public TextFieldControl expirationDate;
	public TextFieldControl previousPolicyEffDate;
	public TextFieldControl namedInsured;
	public TextFieldControl extendedNamedInsured;
	public ButtonControl entityArrow;
	public ButtonControl entityOption;
	public TextFieldControl primaryFirstName;
	public TextFieldControl primaryLastName;
	public TextFieldControl insuredEmail;
	public TextFieldControl insuredPhoneNoAreaCode;
	public TextFieldControl insuredPhoneNoPrefix;
	public TextFieldControl insuredPhoneNoEnd;
	public ButtonControl insuredMailingAddressArrow;
	public ButtonControl insuredMailingAddressSelection;
	public TextFieldControl insuredCountry;
	public DropDownControl insuredCountrySelect;
	public ButtonControl grantorOrBenificiaryDifferentArrow;
	public ButtonControl grantorOrBenificiaryDifferentOptions;
	public TextFieldControl grantorOrBenificiaryName;

	public HyperLink enterAddressManuallLink;
	public TextFieldControl mailingAddress;
	public TextFieldControl address2;
	public TextFieldControl internationAddressLine3;
	public TextFieldControl addressLine1;
	public TextFieldControl addressLine3;
	public TextFieldControl insuredCity;
	public TextFieldControl insuredState;
	public TextFieldControl province;
	public TextFieldControl postalCode;
	public TextFieldControl insuredZipCode;
	public HyperLink useAutocompleteLink;

	public ButtonControl floodCoverageArrow;
	public ButtonControl floodCoverageOption;

	public RadioButtonControl singlePay;
	public RadioButtonControl mortgageePay;
	public RadioButtonControl fourPay;
	public RadioButtonControl threePay;
	public RadioButtonControl tenPay;

	public RadioButtonControl assignInspContactByPolicy;
	public RadioButtonControl assignInspContactByLocation;
	public TextFieldControl inspectionName;
	public TextFieldControl inspectionAreaCode;
	public TextFieldControl inspectionPrefix;
	public TextFieldControl inspectionNumber;
	public HyperLink deselectAll;
	public HyperLink selectAll;
	public ButtonControl addInspectionSymbol;
	public CheckBoxControl locationInspectionBox;

	public ButtonControl triaCoverageArrow;
	public ButtonControl triaCoverageSelection;

	public ButtonControl aITypeArrow;
	public HyperLink aITypeOption;
	public HyperLink aITypeOption1;
	public BaseWebElementControl aITypeData;
	public TextFieldControl aILoanNumber;
	public ButtonControl aIRelationShipArrow;
	public HyperLink aIRelationShipOption;
	public ButtonControl aIRankArrow;
	public HyperLink aIRankoption;
	public ButtonControl aIRankArrow1;
	public HyperLink aIRankoption1;
	public BaseWebElementControl aIRankData;
	public TextFieldControl aIName;
	public TextFieldControl aICountry;
	public HyperLink aIEnterAddressManuallyLink;
	public TextFieldControl aIAddress;
	public TextFieldControl aIAddressLine2;
	public TextFieldControl aIAddressLine1;
	public TextFieldControl aICity;
	public TextFieldControl aIState;
	public TextFieldControl aIzipCode;
	public TextFieldControl aIZipCode2;
	public HyperLink aIUseAutocompleteLink;
	public HyperLink aIAddSymbol;
	public RadioButtonControl aIByPolicy;
	public RadioButtonControl aIByLocation;
	public CheckBoxControl aIbuildingSelection;
	public CheckBoxControl aIbuildingSelectionData;

	public BaseWebElementControl cancelRewriteHeader;
	public BaseWebElementControl originalInspectionFee;
	public TextFieldControl earnedInspectionFee;
	public BaseWebElementControl originalPolicyFee;
	public TextFieldControl earnedPolicyFee;

	public TextFieldControl contactName;
	public TextFieldControl contactEmailAddress;
	public TextFieldControl contactSurplusLicenseNumber;
	public CheckBoxControl specialInstructionCheckbox;
	public TextFieldControl specialInstructionsText;
	public ButtonControl cancel;
	public ButtonControl submit;
	public ButtonControl editInformation;
	public ButtonControl requestBind;

	public DropDownControl carrier;
	public TextFieldControl previousPolicyNumber;
	public TextAreaControl internalComments;
	public TextAreaControl externalComments;
	public ButtonControl displayQuote;
	public ButtonControl quoteDetails;
	public ButtonControl previewBinder;
	public ButtonControl approve;
	public ButtonControl decline;
	public ButtonControl pendingModifications;
	public ButtonControl handledOutsideSystem;
	public ButtonControl save;
	public ButtonControl cancelButton;

	public ButtonControl approveBackDating;
	public ButtonControl cancelBackDating;
	public CheckBoxControl overrideEffectiveDate;
	public BaseWebElementControl pageName;
	public BaseWebElementControl additionalMortgageeError;

	public ButtonControl rewrite;
	public ButtonControl backdatingRewrite;
	public TextFieldControl previousPolicyCancellationDate;
	public CheckBoxControl selfInspection;
	public BaseWebElementControl carrierData;
	public BaseWebElementControl requestBindQuoteName;

	public BaseWebElementControl mortgageeWarningMessage;
	public DropDownControl carrierInfo;
	public ButtonControl carrierArrow;
	public ButtonControl carrierOption;
	public ButtonControl firstCarrierOption;
	public ButtonControl carrierOptionByText;
	public ButtonControl infoSymbol;
	public BaseWebElementControl moratoriumMsg;
	public BaseWebElementControl moratoriumReferralMessage;

	public ButtonControl yes_NameChange;
	public ButtonControl no_NameChange;

	public BaseWebElementControl effectiveDateErrorMsg;
	public BaseWebElementControl mailingAddressMandatoryMsg;
	public BaseWebElementControl inspectionContactNameMandatoryMsg;
	public BaseWebElementControl inspectionContactPhoneMandatoryMsg;
	public BaseWebElementControl contactNameMandatoryMsg;
	public BaseWebElementControl contactEmailMandatoryMsg;
	public BaseWebElementControl surplusLicenseNumMandatorymsg;

	public ButtonControl returntoBindRequest;
	public ButtonControl wanttoContinue;

	public BaseWebElementControl commissionRate;
	public DropDownControl aiCountrySelect;
	public RadioButtonControl financePay;
	public CheckBoxControl specialInstructionsCheckbox;
	public CheckBoxControl dueDiligenceCheckbox;
	public TextFieldControl chooseFile;
	public BaseWebElementControl dueDiligenceText;
	public BaseWebElementControl payplanErrorMessage;
	public BaseWebElementControl dueDiligenceMandatoryMsg;
	public BaseWebElementControl moratoriumWarningMsg;
	public BaseWebElementControl carrierWarningMsg;
	public BaseWebElementControl grandTotal;
	public ButtonControl okButton;
	public BaseWebElementControl effectiveDateErrorMsg1;
	public BaseWebElementControl reciprocalAgreementMsg;
	public BaseWebElementControl payPlanPremium;
	public BaseWebElementControl vieMessage;
	public TextFieldControl connecticutChooseFile;
	public BaseWebElementControl connectiCutFormError;
	public BaseWebElementControl inspectionContactNameErrorMsg;
	public BaseWebElementControl inspectionContactPhoneErrorMsg;
	public BaseWebElementControl diligenceText;
	public TextFieldControl primaryTrusteeFirstName;
	public TextFieldControl primaryTrusteeLastName;
	public TextFieldControl trusteeDateofBirth;
	public TextFieldControl secondaryTrusteeFirstName;
	public TextFieldControl secondaryTrusteeLastName;
	public TextFieldControl aiRelationshipOthersDescription;
	public BaseWebElementControl diligenceCheckboxError;
	public ButtonControl carrierArrowSelection;
	public HyperLink duediligenceClick;
	public BaseWebElementControl effDateErrorMsg;
	public TextFieldControl aiZipCodeQ3;
	public TextFieldControl aIPostalCode;
	public BaseWebElementControl nllWarningMessage;
	public BaseWebElementControl nllReferralMessage;
	public BaseWebElementControl nllWarningMsg;
	public HyperLink clickHereLink;
	public HyperLink clickHereToApprove;
	public BaseWebElementControl singleNamedInsuredMsg;
	public BaseWebElementControl insuredEmailMandatoryMessage;
	public BaseWebElementControl insuredPhoneNoMandatoryMessage;
	public BaseWebElementControl mailingAddressMandatoryMessage;
	public BaseWebElementControl diligenceMandatoryMsg;
	public BaseWebElementControl relationshipMandatoryMsg;
	public BaseWebElementControl prePolCancelEffDateWarningMsg;
	public TextFieldControl previousPolicyEffectiveDate;
	public BaseWebElementControl originalInsFee;
	public BaseWebElementControl niDisplay;
	public ButtonControl OkButton;
	public BaseWebElementControl effectiveDateData;
	public BaseWebElementControl namedHurricaneValueData;
	public TextFieldControl aIZipCode1;
	public ButtonControl nameChangeNo;

	BasePageControl page;
	AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
	PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();

	public RequestBindPage() {
		PageObject pageobject = new PageObject("RequestBind");
		quoteNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNumber")));
		quotePremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuotePremium")));

		effectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EffectiveDate")));
		expirationDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ExpirationDate")));
		previousPolicyEffDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PreviousPolicyEffDate")));
		namedInsured = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Namedinsured")));
		extendedNamedInsured = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ExtendedNamedInsured")));
		entityArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_EntityArrowAndData")));
		entityOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_EntityOption")));
		primaryFirstName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PrimaryFirstName")));
		primaryLastName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PrimaryLastName")));
		insuredEmail = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredEmail")));
		insuredPhoneNoAreaCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredPhoneNoAreaCode")));
		insuredPhoneNoPrefix = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredPhoneNoPrefix")));
		insuredPhoneNoEnd = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredPhoneNoEnd")));
		insuredMailingAddressArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_InsuredMailingAddressArrow")));
		insuredMailingAddressSelection = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_InsuredmailingAddressSelection")));
		insuredCountry = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredCountry")));
		insuredCountrySelect = new DropDownControl(By.xpath(pageobject.getXpath("xp_CountrySelect")));
		grantorOrBenificiaryDifferentArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_GrantorOrBenificiaryDifferentArrow")));
		grantorOrBenificiaryDifferentOptions = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_GrantorOrBenificiaryDifferentOptions")));
		grantorOrBenificiaryName = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_GrantorOrBenificiaryNameTextbox")));

		enterAddressManuallLink = new HyperLink(By.xpath(pageobject.getXpath("xp_InsuredManualEntryLink")));
		mailingAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_MailingAddress")));
		address2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine2")));
		internationAddressLine3 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InternationalAddressLine3")));
		addressLine1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine1")));
		addressLine3 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine3")));
		insuredCity = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredCity")));
		insuredState = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredState")));
		province = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Province")));
		postalCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PostalCode")));
		insuredZipCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InsuredZipcode")));
		useAutocompleteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_UseAutoCompleteAI")));

		floodCoverageArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_FloodCoverageArrow")));
		floodCoverageOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FloodCoverageOption")));

		singlePay = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_SinglePay")));
		mortgageePay = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_MortgageePay")));
		fourPay = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_4Pay")));
		threePay = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_3Pay")));
		tenPay = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_10Pay")));

		assignInspContactByPolicy = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_AssignInspContactToEntirePolicy")));
		assignInspContactByLocation = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_AssignInspContactByLocation")));
		inspectionName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InspectionContactName")));
		inspectionAreaCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InspectionPhoneNumberAreaCode")));
		inspectionPrefix = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InspectionPhoneNumberPrefix")));
		inspectionNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_InspectionPhoneNumberEnd")));
		deselectAll = new HyperLink(By.xpath(pageobject.getXpath("xp_DeselectAll")));
		selectAll = new HyperLink(By.xpath(pageobject.getXpath("xp_SelectAll")));
		addInspectionSymbol = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddInspection")));
		locationInspectionBox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_LocationInspectionbox")));

		triaCoverageArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_TriaArrow")));
		triaCoverageSelection = new ButtonControl(By.xpath(pageobject.getXpath("xp_TriaSelection")));

		aITypeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_AITypeArrow")));
		aITypeOption = new HyperLink(By.xpath(pageobject.getXpath("xp_AITypeOption")));
		aITypeOption1 = new HyperLink(By.xpath(pageobject.getXpath("xp_AITypeOptionNAHO")));
		aIRelationShipArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_AIRelationShipArrow")));
		aIRelationShipOption = new HyperLink(By.xpath(pageobject.getXpath("xp_AIRelationShipOption")));
		aITypeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AITypeData")));
		aILoanNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AILoanNumber")));
		aIRankArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_AIRankArrow")));
		aIRankoption = new HyperLink(By.xpath(pageobject.getXpath("xp_AIRankOption")));
		aIRankArrow1 = new ButtonControl(By.xpath(pageobject.getXpath("xp_AIRankArrowNAHO")));
		aIRankoption1 = new HyperLink(By.xpath(pageobject.getXpath("xp_AIRankOptionNAHO")));
		aIRankData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AIRankData")));
		aIName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AIName")));
		aICountry = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AICountry")));
		aIEnterAddressManuallyLink = new HyperLink(By.xpath(pageobject.getXpath("xp_AIEnterAddressManually")));
		aIAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AIAddress")));
		aIAddressLine2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AIAddressLine2")));
		aIAddressLine1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AILine1")));
		aICity = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AICity")));
		aIState = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AIState")));
		aIzipCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AIZipcode")));
		aIZipCode2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AIZipcodePlus4")));
		aIUseAutocompleteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_UseAutoCompleteAI")));
		aIAddSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_AddAIButton")));
		aIByPolicy = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_AIByPolicy")));
		aIByLocation = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_AIByLocation")));
		aIbuildingSelection = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_AIBuildingSelection")));
		aIbuildingSelectionData = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_AIBuildingSelectionData")));

		cancelRewriteHeader = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CancelRewriteHeader")));
		originalInspectionFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OriginalInspectionFee")));
		earnedInspectionFee = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EarnedInspectionFee")));
		originalPolicyFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OriginalPolicyFee")));
		earnedPolicyFee = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EarnedPolicyFee")));

		contactName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Yourname")));
		contactEmailAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EmailAddress")));
		contactSurplusLicenseNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_SurplusLicenseNumber")));
		specialInstructionsText = new TextFieldControl(By.xpath(pageobject.getXpath("xp_SpecialInstructionsText")));
		cancel = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
		submit = new ButtonControl(By.xpath(pageobject.getXpath("xp_Submit")));
		editInformation = new ButtonControl(By.xpath(pageobject.getXpath("xp_EditInformation")));
		requestBind = new ButtonControl(By.xpath(pageobject.getXpath("xp_RequestBind")));

		carrier = new DropDownControl(By.xpath(pageobject.getXpath("xp_Carrier")));
		previousPolicyNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Previous_PolicyNumber")));
		internalComments = new TextAreaControl(By.xpath(pageobject.getXpath("xp_InternalComments")));
		externalComments = new TextAreaControl(By.xpath(pageobject.getXpath("xp_ExternalComments")));
		displayQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_DisplayQuote")));
		quoteDetails = new ButtonControl(By.xpath(pageobject.getXpath("xp_Quote_Details")));
		previewBinder = new ButtonControl(By.xpath(pageobject.getXpath("xp_PreviewBinder")));
		approve = new ButtonControl(By.xpath(pageobject.getXpath("xp_Approve")));
		decline = new ButtonControl(By.xpath(pageobject.getXpath("xp_Decline")));
		pendingModifications = new ButtonControl(By.xpath(pageobject.getXpath("xp_Pending_Modifications")));
		handledOutsideSystem = new ButtonControl(By.xpath(pageobject.getXpath("xp_Handled_Outside_System")));
		save = new ButtonControl(By.xpath(pageobject.getXpath("xp_Save")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));

		approveBackDating = new ButtonControl(By.xpath(pageobject.getXpath("xp_ApproveBackDating")));
		cancelBackDating = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelBackDating")));
		overrideEffectiveDate = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_OverrideEffectiveDate")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
		additionalMortgageeError = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AdditionalMortgageeError")));

		rewrite = new ButtonControl(By.xpath(pageobject.getXpath("xp_RewriteButton")));
		backdatingRewrite = new ButtonControl(By.xpath(pageobject.getXpath("xp_BackdateRewrite")));
		previousPolicyCancellationDate = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_PreviouspolicyCancellationDate")));
		selfInspection = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_SelfInspection")));
		carrierData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CarrierData")));
		requestBindQuoteName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RequestBindQuoteName")));

		mortgageeWarningMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_MortgageeWarningMessage")));
		carrierInfo = new DropDownControl(By.xpath(pageobject.getXpath("xp_Carrier")));
		carrierArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_CarrierArrow")));
		carrierOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_CarrierOption")));
		firstCarrierOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FirstCarrierOption")));
		carrierOptionByText = new ButtonControl(By.xpath(pageobject.getXpath("xp_CarrierOptionByText")));
		infoSymbol = new ButtonControl(By.xpath(pageobject.getXpath("xp_InfoSymbol")));
		moratoriumMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MoratoriumWarningMessage")));
		moratoriumReferralMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_MoratoriumReferralMessage")));

		yes_NameChange = new ButtonControl(By.xpath(pageobject.getXpath("xp_NameChange_Yes")));
		no_NameChange = new ButtonControl(By.xpath(pageobject.getXpath("xp_NameChange_No")));
		effectiveDateErrorMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EffectiveDateErrorMsg")));
		mailingAddressMandatoryMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_MailingAddressMandatoryMsg")));
		inspectionContactNameMandatoryMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_InspectionContactNameMandatoryMsg")));
		inspectionContactPhoneMandatoryMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_InspectionContactPhoneMandatoryMsg")));
		contactNameMandatoryMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ContactNameMandatoryMsg")));
		contactEmailMandatoryMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_ContactEmailMandatoryMsg")));
		surplusLicenseNumMandatorymsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_SurplusLicenseNumMandatorymsg")));

		returntoBindRequest = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReturntoBindRequest")));
		wanttoContinue = new ButtonControl(By.xpath(pageobject.getXpath("xp_WanttoContinue")));
		commissionRate = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CommissionRate")));
		aiCountrySelect = new DropDownControl(By.xpath(pageobject.getXpath("xp_AICountrySelect")));
		financePay = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_FinancePay")));
		specialInstructionCheckbox = new CheckBoxControl(
				By.xpath(pageobject.getXpath("xp_SpecialInstructionsCheckbox")));
		dueDiligenceCheckbox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_DueDiligenceCheckbox")));
		chooseFile = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ChooseFile")));
		dueDiligenceText = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DueDiligenceText")));
		payplanErrorMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PayplanErrorMessage")));
		dueDiligenceMandatoryMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DueDiligenceMandatoryMsg")));
		moratoriumWarningMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MoratoriumWarningMsg")));
		carrierWarningMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CarrierWarningMsg")));
		grandTotal = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GrandTotal")));
		effectiveDateErrorMsg1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EffectiveDateErrorMsg1")));
		okButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OkButton")));
		reciprocalAgreementMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReciprocalAgreementMsg")));
		payPlanPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PayPlanPremium")));
		connecticutChooseFile = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ConnecticutchooseFile")));
		connectiCutFormError = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ConnectiCutFormError")));
		inspectionContactNameErrorMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_InspectionContactNameErrorMsg")));
		inspectionContactPhoneErrorMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_InspectionContactPhoneErrorMsg")));
		diligenceText = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DiligenceText")));
		primaryTrusteeFirstName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PrimaryTrusteeFirstName")));
		primaryTrusteeLastName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PrimaryTrusteeLastName")));
		trusteeDateofBirth = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TrusteeDateofBirth")));
		secondaryTrusteeFirstName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_SecondaryTrusteeFirstName")));
		secondaryTrusteeLastName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_SecondaryTrusteeLastName")));
		vieMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_VIEMessage")));
		aiRelationshipOthersDescription = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_AIRelationshipOthersDescription")));
		diligenceCheckboxError = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DiligenceCheckboxError")));
		carrierArrowSelection = new ButtonControl(By.xpath(pageobject.getXpath("xp_CarrierArrowSelection")));
		effDateErrorMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EffDateErrorMsg")));
		duediligenceClick = new HyperLink(By.xpath(pageobject.getXpath("xp_DuediligenceClick")));
		aIPostalCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AIPostalCode")));
		aiZipCodeQ3 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AIZipCodeQ3")));
		nllWarningMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NLLWarningMessage")));
		nllReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NLLReferralMessage")));
		nllWarningMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NLLWarningMsg")));
		clickHereLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ClickHereLink")));
		clickHereToApprove = new HyperLink(By.xpath(pageobject.getXpath("xp_ClickHereToApprove")));
		singleNamedInsuredMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SingleNamedInsuredMsg")));
		insuredEmailMandatoryMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_InsuredEmailMandatoryMsg")));
		insuredPhoneNoMandatoryMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_InsurePhoneNoMandatoryMsg")));
		mailingAddressMandatoryMessage = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_MailingAddressMandatoryMsg")));
		diligenceMandatoryMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DiligenceMandatoryMsg")));
		relationshipMandatoryMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_RelationshipMandatoryMsg")));
		prePolCancelEffDateWarningMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_PrevPolCancelEffDateWarMsg")));
		previousPolicyEffectiveDate = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_PreviousPolicyEffectiveDate")));
		originalInsFee = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OriginalInsFee")));
		niDisplay = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NIDisplay")));
		OkButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OkButton")));
		effectiveDateData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EffectiveDateData")));
		namedHurricaneValueData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NamedHurricaneValue")));
		aIZipCode1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Zipcode")));
		nameChangeNo = new ButtonControl(By.xpath(pageobject.getXpath("xp_NameChangeNo")));
	}

	public void enterPolicyDetails(Map<String, String> Data) {
		effectiveDate.waitTillVisibilityOfElement(60);
		if (Data.get("PolicyEffDate").equals("")) {
			effectiveDate.scrollToElement();
			effectiveDate.clearData();
			effectiveDate.setData(new DateConversions().getCurrentDate("MM/dd/yyyy"));
		} else {
			effectiveDate.clearData();
			effectiveDate.setData(Data.get("PolicyEffDate"));
			effectiveDate.tab();
		}
		/*
		 * namedInsured.clearData(); namedInsured.setData(Data.get("InsuredName"));
		 * namedInsured.tab();
		 */
		waitTime(3); // If wait time is removed,Element Not Interactable exception is
		// thrown.Waittillpresence and Waittillvisibility is not working here

		if (wanttoContinue.checkIfElementIsPresent() && wanttoContinue.checkIfElementIsDisplayed()) {
			wanttoContinue.waitTillVisibilityOfElement(60);
			wanttoContinue.scrollToElement();
			wanttoContinue.click();
			accountOverviewPage.requestBind.scrollToElement();
			accountOverviewPage.requestBind.click();
		}
		if (no_NameChange.checkIfElementIsPresent() && no_NameChange.checkIfElementIsDisplayed()) {
			no_NameChange.waitTillPresenceOfElement(60);
			no_NameChange.waitTillVisibilityOfElement(60);
			no_NameChange.scrollToElement();
			no_NameChange.click();
			no_NameChange.waitTillInVisibilityOfElement(60);
		}

		if (!Data.get("ExtendedNamedInsured").equals("")) {
			extendedNamedInsured.scrollToElement();
			extendedNamedInsured.setData(Data.get("ExtendedNamedInsured"));
		}

		if (entityArrow.checkIfElementIsPresent() && entityArrow.checkIfElementIsDisplayed()) {
			entityArrow.scrollToElement();
			entityArrow.click();
			entityOption.formatDynamicPath(Data.get("Entity")).scrollToElement();
			entityOption.formatDynamicPath(Data.get("Entity")).click();
		}

		if (insuredEmail.checkIfElementIsPresent() && insuredEmail.checkIfElementIsDisplayed()
				&& !Data.get("InsuredEmail").equalsIgnoreCase("")) {
			insuredEmail.scrollToElement();
			insuredEmail.setData(Data.get("InsuredEmail"));
		}
		if (insuredPhoneNoAreaCode.checkIfElementIsPresent() && insuredPhoneNoAreaCode.checkIfElementIsDisplayed()) {
			insuredPhoneNoAreaCode.setData(Data.get("InsuredPhoneNumAreaCode"));
			insuredPhoneNoPrefix.setData(Data.get("InsuredPhoneNumPrefix"));
			insuredPhoneNoEnd.setData(Data.get("InsuredPhoneNum"));
		}

		if (chooseFile.checkIfElementIsPresent() && chooseFile.checkIfElementIsDisplayed()) {
			if (!Data.get("FileNameToUpload").equals("")) {
				policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
			}
		}

		if (grantorOrBenificiaryDifferentArrow.checkIfElementIsPresent()
				&& grantorOrBenificiaryDifferentArrow.checkIfElementIsDisplayed()) {
			grantorOrBenificiaryDifferentArrow.scrollToElement();
			grantorOrBenificiaryDifferentArrow.click();
			grantorOrBenificiaryDifferentOptions.formatDynamicPath(Data.get("Grantor/BeneficiaryDifferent"))
					.scrollToElement();
			grantorOrBenificiaryDifferentOptions.formatDynamicPath(Data.get("Grantor/BeneficiaryDifferent")).click();
		}
		if (grantorOrBenificiaryName.checkIfElementIsPresent()
				&& grantorOrBenificiaryName.checkIfElementIsDisplayed()) {
			grantorOrBenificiaryName.scrollToElement();
			grantorOrBenificiaryName.setData(Data.get("Grantor/BeneficiaryName"));
		}
		if (primaryTrusteeFirstName.checkIfElementIsPresent() && primaryTrusteeFirstName.checkIfElementIsDisplayed()
				&& !Data.get("PrimaryTrusteeFirstName").equalsIgnoreCase("")) {
			primaryTrusteeFirstName.scrollToElement();
			primaryTrusteeFirstName.setData(Data.get("PrimaryTrusteeFirstName"));
		}
		if (primaryTrusteeLastName.checkIfElementIsPresent() && primaryTrusteeLastName.checkIfElementIsDisplayed()
				&& !Data.get("PrimaryTrusteeLastName").equalsIgnoreCase("")) {
			primaryTrusteeLastName.scrollToElement();
			primaryTrusteeLastName.setData(Data.get("PrimaryTrusteeLastName"));
		}
		if (trusteeDateofBirth.checkIfElementIsPresent() && trusteeDateofBirth.checkIfElementIsDisplayed()
				&& !Data.get("TrusteeDateofBirth").equalsIgnoreCase("")) {
			trusteeDateofBirth.scrollToElement();
			trusteeDateofBirth.setData(Data.get("TrusteeDateofBirth"));
		}
		if (secondaryTrusteeFirstName.checkIfElementIsPresent() && secondaryTrusteeFirstName.checkIfElementIsDisplayed()
				&& !Data.get("SecondaryTrusteeFirstName").equalsIgnoreCase("")) {
			secondaryTrusteeFirstName.scrollToElement();
			secondaryTrusteeFirstName.setData(Data.get("SecondaryTrusteeFirstName"));
		}
		if (secondaryTrusteeLastName.checkIfElementIsPresent() && secondaryTrusteeLastName.checkIfElementIsDisplayed()
				&& !Data.get("SecondaryTrusteeLastName").equalsIgnoreCase("")) {
			secondaryTrusteeLastName.scrollToElement();
			secondaryTrusteeLastName.setData(Data.get("SecondaryTrusteeLastName"));
		}

		// TF 02/23/2021 - this field is an old-school drop down when chrome is controlled by test automation - weird
		if (!Data.get("InsuredCountry").equals("United States")) {
			WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.id("insuredNameAddressModel.address.country"));
			ele.sendKeys(Keys.chord(FrameworkUtil.KEY_CMD_CTRL, "a"), Data.get("InsuredCountry"));
			ele.sendKeys(Keys.TAB);
		}

		if (enterAddressManuallLink.checkIfElementIsDisplayed()) {
			enterAddressManuallLink.scrollToElement();
			enterAddressManuallLink.click();
		}

		addressLine1.waitTillVisibilityOfElement(60);
		addressLine1.scrollToElement();
		addressLine1.setData(Data.get("InsuredAddr1"));
		if (!Data.get("InsuredAddr2").equals("")) {
			address2.scrollToElement();
			address2.setData(Data.get("InsuredAddr2"));
		}
		if (insuredCity.checkIfElementIsDisplayed()) {
			insuredCity.scrollToElement();
			insuredCity.setData(Data.get("InsuredCity"));
		}
		if (insuredState.checkIfElementIsPresent() && insuredState.checkIfElementIsDisplayed()) {
			insuredState.scrollToElement();
			insuredState.setData(Data.get("InsuredState"));
			insuredZipCode.setData(Data.get("InsuredZIP"));
		}
		if (province.checkIfElementIsPresent() && province.checkIfElementIsDisplayed()) {
			province.scrollToElement();
			province.setData(Data.get("InsuredState"));
			postalCode.setData(Data.get("InsuredZIP"));
		}
		if (addressLine3.checkIfElementIsDisplayed()) {
			addressLine3.scrollToElement();
			addressLine3
					.setData(Data.get("InsuredCity") + " " + Data.get("InsuredState") + " " + Data.get("InsuredZIP"));
		}
		if (floodCoverageArrow.checkIfElementIsPresent() && floodCoverageArrow.checkIfElementIsDisplayed()) {
			floodCoverageArrow.scrollToElement();
			floodCoverageArrow.click();
			floodCoverageOption.formatDynamicPath(Data.get("ApplicantHaveFloodPolicy")).scrollToElement();
			floodCoverageOption.formatDynamicPath(Data.get("ApplicantHaveFloodPolicy")).click();
		}
		if (Data.get("PreviousPolicyCancellationDate") != null
				&& !Data.get("PreviousPolicyCancellationDate").equals("")) {
			if (previousPolicyCancellationDate.checkIfElementIsPresent()
					&& previousPolicyCancellationDate.checkIfElementIsDisplayed()) {
				previousPolicyCancellationDate.scrollToElement();
				previousPolicyCancellationDate.clearData();
				previousPolicyCancellationDate.setData(new DateConversions().getCurrentDate("MM/dd/yyyy"));
			}
		} else if (previousPolicyCancellationDate.checkIfElementIsPresent()
				&& previousPolicyCancellationDate.checkIfElementIsDisplayed()) {
			previousPolicyCancellationDate.scrollToElement();
			previousPolicyCancellationDate.clearData();
			previousPolicyCancellationDate.setData(new DateConversions().getCurrentDate("MM/dd/yyyy"));
		}
	}

	public void setTria(String tria) {
		triaCoverageArrow.waitTillVisibilityOfElement(60);
		triaCoverageArrow.scrollToElement();
		triaCoverageArrow.click();
		triaCoverageSelection.formatDynamicPath(tria).click();
	}

	public void enterPaymentInformation(Map<String, String> Data) {
		if (!Data.get("ProductSelection").contains("Commercial")) {
			if (Data.containsKey("SinglePay") && Data.get("SinglePay").equalsIgnoreCase("yes")) {
				singlePay.scrollToElement();
				singlePay.click();
				Assertions.passTest("Request Bind Page", "Payment Plan selected : " + " Full Pay");
			} else if (Data.containsKey("MortgageePay") && Data.get("MortgageePay").equalsIgnoreCase("yes")) {
				mortgageePay.scrollToElement();
				mortgageePay.click();
				Assertions.passTest("Request Bind Page", "Payment Plan selected : " + " Mortgagee Pay");
			} else if (Data.containsKey("4Pay") && Data.get("4Pay").equalsIgnoreCase("yes")) {
				fourPay.scrollToElement();
				fourPay.click();
				Assertions.passTest("Request Bind Page", "Payment Plan selected : " + " Four Pay");
			} else if (Data.containsKey("FinancePay") && Data.get("FinancePay").equalsIgnoreCase("yes")) {
				financePay.scrollToElement();
				financePay.click();
				Assertions.passTest("Request Bind Page", "Payment Plan selected is Finance Pay");
			}
		} else {
			if (singlePay.checkIfElementIsPresent()) {
				singlePay.scrollToElement();
				singlePay.click();
				Assertions.passTest("Request Bind Page", "Payment Plan selected : " + " Full Pay");
			}
		}
	}

	public void enterPaymentInformationNAHO(Map<String, String> Data) {
		if (singlePay.checkIfElementIsPresent() && singlePay.checkIfElementIsDisplayed()
				&& Data.get("SinglePay").equalsIgnoreCase("yes")) {
			singlePay.scrollToElement();
			singlePay.click();
			Assertions.passTest("Request Bind Page", "Payment Plan selected : " + " Full Pay");
		} else if (mortgageePay.checkIfElementIsPresent() && mortgageePay.checkIfElementIsDisplayed()
				&& Data.get("MortgageePay").equalsIgnoreCase("yes")) {
			mortgageePay.scrollToElement();
			mortgageePay.click();
			Assertions.passTest("Request Bind Page", "Payment Plan selected : " + " Mortgagee Pay");
		} else if (fourPay.checkIfElementIsPresent() && fourPay.checkIfElementIsDisplayed()
				&& Data.get("4Pay").equalsIgnoreCase("yes")) {
			fourPay.scrollToElement();
			fourPay.click();
			Assertions.passTest("Request Bind Page", "Payment Plan selected : " + " 4 Pay");
		} else if (threePay.checkIfElementIsPresent() && threePay.checkIfElementIsDisplayed()
				&& Data.get("3Pay").equalsIgnoreCase("yes")) {
			threePay.scrollToElement();
			threePay.click();
			Assertions.passTest("Request Bind Page", "Payment Plan selected : " + " 3 Pay");
		} else if (tenPay.checkIfElementIsPresent() && tenPay.checkIfElementIsDisplayed()
				&& Data.get("10Pay").equalsIgnoreCase("yes")) {
			waitTime(2);
			tenPay.scrollToElement();
			tenPay.click();
			Assertions.passTest("Request Bind Page", "Payment Plan selected : " + " 10 Pay");
		}
	}

	public void verifypaymentinformation() {
		if (singlePay.checkIfElementIsSelected())
			Assertions.verify(singlePay.checkIfElementIsSelected(), true, "Request Bind Page",
					"Payment Plan selected : " + " Full Pay", false, false);
		if (fourPay.checkIfElementIsSelected())
			Assertions.verify(fourPay.checkIfElementIsSelected(), true, "Request Bind Page",
					"Payment Plan selected : " + " 4 Pay", false, false);
		// if (tenPay.checkIfElementIsSelected())
		// Assertions.verify(tenPay.checkIfElementIsSelected(), true, "Request Bind
		// Page",
		// "Payment Plan selected : " + " 10 Pay", false, false);
	}

	public void addInspectionContact(Map<String, String> Data) {
		if (Data.get("InspectionAssign") != null && Data.get("InspectionAssign").equalsIgnoreCase("By Location")) {
			assignInspContactByLocation.scrollToElement();
			assignInspContactByLocation.click();
			deselectAll.waitTillVisibilityOfElement(60);
			String[] inspectionApplicability = Data.get("InspectionAssignApplicability").split(",");
			for (int i = 1; i <= inspectionApplicability.length; i++) {
				locationInspectionBox.formatDynamicPath(i).scrollToElement();
				locationInspectionBox.formatDynamicPath(i).select();
			}
		}
		if (assignInspContactByPolicy.checkIfElementIsPresent()
				&& assignInspContactByPolicy.checkIfElementIsDisplayed()) {
			if (Data.get("InspectionAssign").equalsIgnoreCase("By Policy")) {
				assignInspContactByPolicy.scrollToElement();
				assignInspContactByPolicy.click();
			}
		}
		inspectionName.scrollToElement();
		inspectionName.setData(Data.get("InspectionContact"));
		inspectionAreaCode.setData(Data.get("InspectionAreaCode"));
		inspectionPrefix.setData(Data.get("InspectionPrefix"));
		inspectionNumber.setData(Data.get("InspectionNumber"));
	}

	public void addContactInformation(Map<String, String> Data) {
		contactName.waitTillVisibilityOfElement(60);
		contactName.scrollToElement();
		contactName.clearData();
		contactName.setData(Data.get("ProducerName"));
		contactEmailAddress.clearData();
		contactEmailAddress.scrollToElement();
		contactEmailAddress.setData(Data.get("ProducerEmail"));
		// surplus lines license number is imported from PMC, if available, no need to
		// set it if it's entered
		if (contactSurplusLicenseNumber.checkIfElementIsPresent()
				&& contactSurplusLicenseNumber.checkIfElementIsDisplayed()) {
			if (contactSurplusLicenseNumber.getData().equals("")) {
				contactSurplusLicenseNumber.clearData();
				contactSurplusLicenseNumber.setData(Data.get("SurplusLicenceNumber"));
			}
		}
		if (Data.get("SplInstructions") != null && !Data.get("SplInstructions").equals("")) {
			specialInstructionCheckbox.scrollToElement();
			specialInstructionCheckbox.select();
			specialInstructionsText.setData(Data.get("SplInstructions"));
		}
		if (specialInstructionCheckbox.checkIfElementIsPresent()
				&& specialInstructionCheckbox.checkIfElementIsDisplayed()) {
			specialInstructionCheckbox.scrollToElement();
			specialInstructionCheckbox.select();
		}
		if (dueDiligenceCheckbox.checkIfElementIsPresent() && dueDiligenceCheckbox.checkIfElementIsDisplayed()) {
			dueDiligenceCheckbox.scrollToElement();
			dueDiligenceCheckbox.select();
		}
	}

	public void addAdditionalInterest(Map<String, String> Data) {
		for (int addintrstNo = 1; addintrstNo < 5; addintrstNo++) {
			if (Data.get(addintrstNo + "-AIType") != null && !Data.get(addintrstNo + "-AIType").equals("")) {
				Assertions.addInfo("Additional Interest Added", "Additional Interest " + addintrstNo);
				if (addintrstNo > 1) {
					aIAddSymbol.scrollToElement();
					aIAddSymbol.click();
				}
				aITypeArrow.scrollToElement();
				aITypeArrow.click();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).scrollToElement();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).click();
				Assertions.passTest("Request Bind Page", "Additional Interest Type : " + aITypeData.getData());

				if (aIRelationShipArrow.checkIfElementIsPresent() && aIRelationShipArrow.checkIfElementIsDisplayed()) {
					aIRelationShipArrow.scrollToElement();
					aIRelationShipArrow.click();

					aIRelationShipOption.formatDynamicPath(Data.get(addintrstNo + "-AIRelationship"))
							.waitTillVisibilityOfElement(60);
					aIRelationShipOption.formatDynamicPath(Data.get(addintrstNo + "-AIRelationship")).scrollToElement();
					aIRelationShipOption.formatDynamicPath(Data.get(addintrstNo + "-AIRelationship")).click();
					Assertions.passTest("Request Bind Page",
							"Additional Interest Relationship Type : " + Data.get(addintrstNo + "-AIRelationship"));
				}

				if (aiRelationshipOthersDescription.checkIfElementIsPresent()
						&& aiRelationshipOthersDescription.checkIfElementIsDisplayed()) {
					aiRelationshipOthersDescription.setData(Data.get(addintrstNo + "-AIRelationshipOthersDesc"));
				}

				aIName.setData(Data.get(addintrstNo + "-AIName"));
				Assertions.passTest("Request Bind Page", "Additional Interest Name : " + aIName.getData());
				if (Data.get(addintrstNo + "-AIType") != null
						&& Data.get(addintrstNo + "-AIType").equalsIgnoreCase("Mortgagee")) {
					if (aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsPresent()
							&& aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsDisplayed()) {
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).scrollToElement();
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).click();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.waitTillVisibilityOfElement(60);
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.scrollToElement();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank")).click();
						Assertions.passTest("Request Bind Page", "Additional Interest Rank : " + aIRankData.getData());
					}
				}

				if (Data.get(addintrstNo + "-AILoanNumber") != null
						&& !Data.get(addintrstNo + "-AILoanNumber").equals("")) {
					aILoanNumber.setData(Data.get(addintrstNo + "-AILoanNumber"));
					Assertions.passTest("Request Bind Page",
							"Additional Interest Loan Number : " + aILoanNumber.getData());
				}

				if (Data.containsKey(addintrstNo + "-AICountry")) {
					if (Data.get(addintrstNo + "-AICountry").equalsIgnoreCase("USA")) {
						aICountry.scrollToElement();
						aICountry.setData(Data.get(addintrstNo + "-AICountry"));
						Assertions.passTest("Request Bind Page",
								"Additional Interest Country : " + aICountry.getData());
					}
				}
				if (Data.containsKey(addintrstNo + "-AICountry")) {
					if (aICountry.checkIfElementIsPresent() && aICountry.checkIfElementIsDisplayed()) {
						if (!Data.get(addintrstNo + "-AICountry").equalsIgnoreCase("USA")) {
							WebElement ele = WebDriverManager.getCurrentDriver()
									.findElement(By.xpath("(//input[contains(@id,'address.country')])[last()]"));
							ele.clear();
							ele.sendKeys(Keys.chord(FrameworkUtil.KEY_CMD_CTRL + "a"),
									Data.get(addintrstNo + "-AICountry"));
							Assertions.passTest("Request Bind Page",
									"Additional Interest Country : " + aICountry.getData());
						}
					} else if (aiCountrySelect.formatDynamicPath(addintrstNo - 1).checkIfElementIsPresent()
							&& aiCountrySelect.formatDynamicPath(addintrstNo - 1).checkIfElementIsDisplayed()) {
						aiCountrySelect.formatDynamicPath(addintrstNo - 1).scrollToElement();
						aiCountrySelect.formatDynamicPath(addintrstNo - 1)
								.selectByVisibleText(Data.get(addintrstNo + "-AICountry"));
						aiCountrySelect.formatDynamicPath(addintrstNo - 1).tab();
					}
				}
				if (aIEnterAddressManuallyLink.checkIfElementIsPresent()
						&& aIEnterAddressManuallyLink.checkIfElementIsDisplayed()) {
					aIEnterAddressManuallyLink.waitTillPresenceOfElement(60);
					aIEnterAddressManuallyLink.waitTillVisibilityOfElement(60);
					aIEnterAddressManuallyLink.scrollToElement();
					aIEnterAddressManuallyLink.click();
				}
				aIAddressLine1.setData(Data.get(addintrstNo + "-AIAddr1"));
				Assertions.passTest("Request Bind Page", "Additional Interest Address Line1 : "
						+ aIAddressLine1.formatDynamicPath(addintrstNo).getData().replace(",", ""));

				aIAddressLine2.setData(Data.get(addintrstNo + "-AIAddr2"));
				Assertions.passTest("Request Bind Page", "Additional Interest Address Line2 : "
						+ aIAddressLine2.formatDynamicPath(addintrstNo).getData());

				if (aICity.checkIfElementIsPresent() && aICity.checkIfElementIsDisplayed()) {
					aICity.setData(Data.get(addintrstNo + "-AICity"));
					Assertions.passTest("Request Bind Page",
							"Additional Interest City : " + aICity.formatDynamicPath(addintrstNo).getData());
				}
				if (aIState.checkIfElementIsPresent() && aIState.checkIfElementIsDisplayed()) {
					aIState.setData(Data.get(addintrstNo + "-AIState"));
					Assertions.passTest("Request Bind Page",
							"Additional Interest State  : " + aIState.formatDynamicPath(addintrstNo).getData());

					TextFieldControl code = aIzipCode.formatDynamicPath((addintrstNo - 1));
					code.setData(Data.get(addintrstNo + "-AIZIP"));
					Assertions.passTest("Request Bind Page", "Additional Interest zipcode : " + code.getData());
				}
				if (aIByLocation.checkIfElementIsPresent() && aIByLocation.checkIfElementIsEnabled()
						&& aIByLocation.checkIfElementIsDisplayed()) {
					aIByLocation.scrollToElement();
					aIByLocation.click();
					waitTime(1);

					List<String> applicability = Arrays.asList(Data.get(addintrstNo + "-AIApplicability").split(","));
					for (int j = 0; j < applicability.size(); j++) {
						aIbuildingSelectionData.formatDynamicPath((addintrstNo - 1), applicability.get(j)).scrollToElement();
						aIbuildingSelectionData.formatDynamicPath((addintrstNo - 1), applicability.get(j)).select();
					}
				}
			}
		}
	}

	public void addAdditionalInterestEQHO(Map<String, String> Data) {
		for (int addintrstNo = 1; addintrstNo < 4; addintrstNo++) {
			if (Data.get(addintrstNo + "-AIType") != null && !Data.get(addintrstNo + "-AIType").equals("")) {
				Assertions.addInfo("Additional Interest Added", "Additional Interest " + addintrstNo);
				if (addintrstNo > 1) {
					aIAddSymbol.scrollToElement();
					aIAddSymbol.click();
				}
				aITypeArrow.scrollToElement();
				aITypeArrow.click();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).scrollToElement();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).click();

				if (aIRelationShipArrow.checkIfElementIsPresent() && aIRelationShipArrow.checkIfElementIsDisplayed()) {
					aIRelationShipArrow.scrollToElement();
					aIRelationShipArrow.click();

					aIRelationShipOption.formatDynamicPath("Corporation").waitTillVisibilityOfElement(60);
					aIRelationShipOption.formatDynamicPath("Corporation").scrollToElement();
					aIRelationShipOption.formatDynamicPath("Corporation").click();
					Assertions.passTest("Request Bind Page", "Additional Interest Relationship Type: Corporation");
				}

				Assertions.passTest("Request Bind Page", "Additional Interest Type : " + aITypeData.getData());
				if (Data.get(addintrstNo + "-AIName") != null && !Data.get(addintrstNo + "-AIName").equals("")) {
					aIName.setData(Data.get(addintrstNo + "-AIName"));
					Assertions.passTest("Request Bind Page", "Additional Interest Name : " + aIName.getData());
				}
				if (Data.get(addintrstNo + "-AIType") != null
						&& Data.get(addintrstNo + "-AIType").equalsIgnoreCase("Mortgagee")) {
					if (aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsPresent()
							&& aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsDisplayed()) {
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).scrollToElement();
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).click();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.waitTillVisibilityOfElement(60);
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.scrollToElement();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank")).click();
						Assertions.passTest("Request Bind Page", "Additional Interest Rank : " + aIRankData.getData());
					}
				}
				if (Data.get(addintrstNo + "-AILoanNumber") != null
						&& !Data.get(addintrstNo + "-AILoanNumber").equals("")) {
					aILoanNumber.setData(Data.get(addintrstNo + "-AILoanNumber"));
					aILoanNumber.setData(Data.get(addintrstNo + "-AILoanNumber"));
				}
				if (Data.containsKey(addintrstNo + "-AICountry")) {
					if (Data.get(addintrstNo + "-AICountry").equalsIgnoreCase("USA")) {
						aICountry.scrollToElement();
						aICountry.setData(Data.get(addintrstNo + "-AICountry"));
						Assertions.passTest("Request Bind Page",
								"Additional Interest Loan Number : " + aILoanNumber.getData());
					}
				}
				if (Data.containsKey(addintrstNo + "-AICountry")) {
					if (!Data.get(addintrstNo + "-AICountry").equalsIgnoreCase("USA")) {
						WebElement ele = WebDriverManager.getCurrentDriver()
								.findElement(By.xpath("(//input[contains(@id,'address.country')])[last()]"));
						ele.clear();
						ele.sendKeys(Keys.chord(FrameworkUtil.KEY_CMD_CTRL + "a"),
								Data.get(addintrstNo + "-AICountry"));
					}
				}
				if (aIEnterAddressManuallyLink.checkIfElementIsPresent()
						&& aIEnterAddressManuallyLink.checkIfElementIsDisplayed()) {
					aIEnterAddressManuallyLink.scrollToElement();
					aIEnterAddressManuallyLink.click();
				}
				if (Data.get(addintrstNo + "-AIAddr1") != null && !Data.get(addintrstNo + "-AIAddr1").equals("")) {
					aIAddressLine1.setData(Data.get(addintrstNo + "-AIAddr1"));
					Assertions.passTest("Request Bind Page", "Additional Interest Address Line1 : "
							+ aIAddressLine1.formatDynamicPath(addintrstNo).getData().replace(",", ""));
				}
				if (Data.get(addintrstNo + "-AIAddr2") != null && !Data.get(addintrstNo + "-AIAddr2").equals("")) {
					aIAddressLine2.setData(Data.get(addintrstNo + "-AIAddr2"));
					Assertions.passTest("Request Bind Page", "Additional Interest Address Line2 : "
							+ aIAddressLine2.formatDynamicPath(addintrstNo).getData());
				}
				if (aICity.checkIfElementIsPresent() && aICity.checkIfElementIsDisplayed()) {
					aICity.setData(Data.get(addintrstNo + "-AICity"));
					Assertions.passTest("Request Bind Page",
							"Additional Interest City : " + aICity.formatDynamicPath(addintrstNo).getData());
				}
				if (aIState.checkIfElementIsPresent() && aIState.checkIfElementIsDisplayed()) {
					aIState.setData(Data.get(addintrstNo + "-AIState"));
					Assertions.passTest("Request Bind Page",
							"Additional Interest State  : " + aIState.formatDynamicPath(addintrstNo).getData());
					TextFieldControl code = aIzipCode.formatDynamicPath((addintrstNo - 1));
					code.setData(Data.get(addintrstNo + "-AIZIP"));
					Assertions.passTest("Request Bind Page", "Additional Interest zipcode : " + code.getData());
				}
				if (aIByLocation.checkIfElementIsPresent() && aIByLocation.checkIfElementIsEnabled()
						&& aIByLocation.checkIfElementIsDisplayed()) {
					aIByLocation.scrollToElement();
					aIByLocation.click();
					List<String> applicability = Arrays.asList(Data.get(addintrstNo + "-AIApplicability").split(","));
					for (int j = 0; j < applicability.size(); j++) {
						aIbuildingSelection.formatDynamicPath(applicability.get(j), (addintrstNo - 1))
								.scrollToElement();
						aIbuildingSelection.formatDynamicPath(applicability.get(j), (addintrstNo - 1)).select();
					}
				}
			}
		}
	}

	public void addAdditionalInterestforPNB(Map<String, String> Data) {
		for (int addintrstNo = 1; addintrstNo < 4; addintrstNo++) {
			if (Data.get(addintrstNo + "-AIType") != null && !Data.get(addintrstNo + "-AIType").equals("")) {
				Assertions.addInfo("Additional Interest Added", "");
				if (addintrstNo > 1) {
					aIAddSymbol.scrollToElement();
					aIAddSymbol.click();
				}
				aITypeArrow.scrollToElement();
				aITypeArrow.click();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).scrollToElement();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).click();
				Assertions.passTest("Additional Interest Type", aITypeData.getData());

				aIName.setData(Data.get(addintrstNo + "-AIName"));
				Assertions.passTest("Additional Interest Name", aIName.getData());

				if (Data.get(addintrstNo + "-AIType") != null
						&& Data.get(addintrstNo + "-AIType").equalsIgnoreCase("Mortgagee")) {
					if (aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsPresent()
							&& aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsDisplayed()) {
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).scrollToElement();
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).click();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.waitTillVisibilityOfElement(60);
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.scrollToElement();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank")).click();
						Assertions.passTest("Additional Interest Rank", aIRankData.getData());
					}
				}
				aILoanNumber.setData(Data.get(addintrstNo + "-AILoanNumber"));
				Assertions.passTest("Additional Interest Loan Number", aILoanNumber.getData());

				if (Data.containsKey(addintrstNo + "-AICountry")) {
					if (Data.get(addintrstNo + "-AICountry") != null
							&& Data.get(addintrstNo + "-AICountry").equalsIgnoreCase("USA")) {
						aICountry.scrollToElement();
						aICountry.setData(Data.get(addintrstNo + "-AICountry"));
						Assertions.passTest("Additional Interest Country", aICountry.getData());
					}
				}
				if (Data.containsKey(addintrstNo + "-AICountry")) {
					if (Data.get(addintrstNo + "-AICountry") != null
							&& !Data.get(addintrstNo + "-AICountry").equalsIgnoreCase("USA")) {
						WebElement ele = WebDriverManager.getCurrentDriver()
								.findElement(By.xpath("(//input[contains(@id,'address.country')])[last()]"));
						ele.clear();
						ele.sendKeys(Keys.chord(FrameworkUtil.KEY_CMD_CTRL + "a"),
								Data.get(addintrstNo + "-AICountry"));
						Assertions.passTest("Additional Interest Country", ele.getText());

					}
				}
				if (aIEnterAddressManuallyLink.checkIfElementIsPresent()
						&& aIEnterAddressManuallyLink.checkIfElementIsDisplayed()) {
					aIEnterAddressManuallyLink.scrollToElement();
					aIEnterAddressManuallyLink.click();
				}
				aIAddressLine1.setData(Data.get(addintrstNo + "-AIAddr1"));
				aIAddressLine2.setData(Data.get(addintrstNo + "-AIAddr2"));
				aICity.setData(Data.get(addintrstNo + "-AICity"));
				aIState.setData(Data.get(addintrstNo + "-AIState"));
				TextFieldControl code = aIzipCode.formatDynamicPath((addintrstNo - 1));
				code.setData(Data.get(addintrstNo + "-AIZIP"));

				Assertions.passTest("Additional Interest Address",
						aIAddressLine1.getData() + "," + aIAddressLine2.getData() + "," + aICity.getData() + ","
								+ aIState.getData() + "," + code.getData());

				if (aIByLocation.checkIfElementIsPresent() && aIByLocation.checkIfElementIsEnabled()
						&& aIByLocation.checkIfElementIsDisplayed()) {
					aIByLocation.scrollToElement();
					aIByLocation.click();
					List<String> applicability = Arrays.asList(Data.get(addintrstNo + "-AIApplicability").split(","));
					System.out.println("applicability = " + applicability);
					for (int j = 0; j < applicability.size(); j++) {
						aIbuildingSelection.formatDynamicPath(applicability.get(j), (addintrstNo - 1))
								.scrollToElement();
						aIbuildingSelection.formatDynamicPath(applicability.get(j), (addintrstNo - 1)).select();
					}
				}
			}
		}
	}

	public BasePageControl confirmBind() {
		requestBind.waitTillVisibilityOfElement(60);
		requestBind.scrollToElement();
		requestBind.click();
		if (overrideEffectiveDate.checkIfElementIsPresent() && overrideEffectiveDate.checkIfElementIsDisplayed()) {
			overrideEffectiveDate.scrollToElement();
			overrideEffectiveDate.select();
			submit.scrollToElement();
			submit.click();
			requestBind.waitTillVisibilityOfElement(60);
			requestBind.scrollToElement();
			requestBind.click();
			requestBind.waitTillInVisibilityOfElement(60);
		}
		requestBind.waitTillInVisibilityOfElement(60);
		if (pageName.getData().contains("Bind Request")) {
			return new BindRequestSubmittedPage();
		} else if (pageName.getData().contains("Policy Summary")) {
			return new PolicySummaryPage();
		}
		return null;
	}

	public BasePageControl addNAHOInfo(Map<String, String> Data) {

		entityArrow.scrollToElement();
		entityArrow.click();
		entityOption.formatDynamicPath("Individual").scrollToElement();
		entityOption.formatDynamicPath("Individual").click();

		primaryFirstName.scrollToElement();
		primaryFirstName.setData(Data.get("PrimaryTrusteeFirstName"));
		primaryLastName.setData(Data.get("PrimaryTrusteeLastName"));

		if (floodCoverageArrow.checkIfElementIsPresent() && floodCoverageArrow.checkIfElementIsDisplayed()) {
			floodCoverageArrow.scrollToElement();
			floodCoverageArrow.click();
			floodCoverageOption.formatDynamicPath(Data.get("ApplicantHaveFloodPolicy")).scrollToElement();
			floodCoverageOption.formatDynamicPath(Data.get("ApplicantHaveFloodPolicy")).click();
		}

		addAdditionalInterestEQHO(Data);
		verifypaymentinformation();

		return page;
	}

	public BasePageControl enterBindDetails(Map<String, String> Data) {
		enterPolicyDetails(Data);
		enterPaymentInformation(Data);
		addInspectionContact(Data);

		if (!Data.get("ProductSelection").equalsIgnoreCase("Residential")) {
			addAdditionalInterest(Data);
		}
		if (Data.get("ProductSelection").contains("Residential")) {
			addNAHOInfo(Data);
		}

		addContactInformation(Data);
		submit.scrollToElement();
		submit.click();
		Assertions.passTest("Request Bind Page", "Values Entered Successfully");
		if (requestBind.checkIfElementIsPresent()) {
			Assertions.passTest("Confirm Bind Request Page", "Confirm Bind Request page loaded successfully");
			page = confirmBind();
			Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
		} else
			Assertions.failTest("Request Bind", "Failed to Load Confirm Bind Pop-up");
		return page;
	}

	public PolicySummaryPage approveRequest() {
		// set carrier if particular carrier is not available
		if (carrierWarningMsg.checkIfElementIsPresent()) {
			carrierArrow.scrollToElement();
			carrierArrow.click();
			for (int i = 1; i <= 5; i++) {
				if (carrierOption.formatDynamicPath(i).getData().contains("ICM")) {
					carrierOption.formatDynamicPath(i).scrollToElement();
					carrierOption.formatDynamicPath(i).click();
					break;
				}
			}
		}
		approve.waitTillVisibilityOfElement(60);
		approve.scrollToElement();
		approve.click();
		waitTime(2);
		if (overrideEffectiveDate.checkIfElementIsPresent() && overrideEffectiveDate.checkIfElementIsDisplayed()) {
			overrideEffectiveDate.scrollToElement();
			overrideEffectiveDate.select();
			approve.scrollToElement();
			approve.click();
			waitTime(2);
		}
		if (approveBackDating.checkIfElementIsPresent() && approveBackDating.checkIfElementIsDisplayed()) {
			approveBackDating.moveToElement();
			approveBackDating.scrollToElement();
			approveBackDating.click();
		}
		if (overrideEffectiveDate.checkIfElementIsPresent() && overrideEffectiveDate.checkIfElementIsDisplayed()) {
			overrideEffectiveDate.scrollToElement();
			overrideEffectiveDate.select();
			approve.scrollToElement();
			approve.click();
			waitTime(2);
		}
		if (approveBackDating.checkIfElementIsPresent() && approveBackDating.checkIfElementIsDisplayed()) {
			approveBackDating.moveToElement();
			approveBackDating.scrollToElement();
			approveBackDating.click();
		}
		if (pageName.getData().contains("Policy Summary")) {
			return new PolicySummaryPage();
		}
		return null;
	}

	public PolicySummaryPage renewalRequestBind(Map<String, String> Data) {
		addContactInformation(Data);
		submit.scrollToElement();
		submit.click();
		if (requestBind.checkIfElementIsPresent()) {
			Assertions.passTest("Confirm Bind Request Page", "Confirm Bind Request page loaded successfully");
			page = confirmBind();
			Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
		} else {
			Assertions.failTest("Request Bind", "Failed to Load Confirm Bind Pop-up");
		}
		if (pageName.getData().contains("Policy Summary")) {
			return new PolicySummaryPage();
		}
		return null;
	}

	public PolicySummaryPage renewalRequestBindNAHO(Map<String, String> Data) {
		waitTime(3);
		if (pageName.getData().contains("Quote Documents")) {
			PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
			policyDocumentsPage.addDocumentButton.scrollToElement();
			policyDocumentsPage.addDocumentButton.click();
			if (!Data.get("FileNameToUpload").equals("")) {
				policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
				waitTime(4);
				policyDocumentsPage.documentCategoryArrow.scrollToElement();
				policyDocumentsPage.documentCategoryArrow.click();
				waitTime(3);
				policyDocumentsPage.documentCategoryOptions.formatDynamicPath("Subscription Agreement")
						.scrollToElement();
				policyDocumentsPage.documentCategoryOptions.formatDynamicPath("Subscription Agreement").click();
				policyDocumentsPage.uploadButton.waitTillPresenceOfElement(60);
				policyDocumentsPage.uploadButton.scrollToElement();
				policyDocumentsPage.uploadButton.click();
				Assertions.passTest("Policy Documents Page", "File Uploaded successfully");
			}
			waitTime(5);// adding wait time to load the element
			policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
			policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();
			if (accountOverviewPage.requestBind.checkIfElementIsPresent()
					&& accountOverviewPage.requestBind.checkIfElementIsDisplayed()) {
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			} else {
				requestBind.scrollToElement();
				requestBind.click();
			}
		}

		if (!Data.get("InsuredName").equals("")) {
			namedInsured.setData(Data.get("InsuredName"));
			namedInsured.tab();
			waitTime(3); // If waittime is removed,Element Not Interactable
							// exception is
			// thrown.Waittillpresence and Waittillvisibility is not working
			// here

			if (no_NameChange.checkIfElementIsPresent() && no_NameChange.checkIfElementIsDisplayed()) {
				no_NameChange.waitTillPresenceOfElement(60);
				no_NameChange.waitTillVisibilityOfElement(60);
				no_NameChange.scrollToElement();
				no_NameChange.click();
				no_NameChange.waitTillInVisibilityOfElement(60);
			}
		}

		if (chooseFile.checkIfElementIsPresent() && chooseFile.checkIfElementIsDisplayed()) {
			if (!Data.get("FileNameToUpload").equals("")) {
				PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
				policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
			}
		}

		if (floodCoverageArrow.checkIfElementIsPresent() && floodCoverageArrow.checkIfElementIsDisplayed()) {
			floodCoverageArrow.scrollToElement();
			floodCoverageArrow.waitTillPresenceOfElement(60);// Added wait time to counter the failure due to Element not interactable
			floodCoverageArrow.click();
			floodCoverageOption.formatDynamicPath(Data.get("ApplicantHaveFloodPolicy")).scrollToElement();
			floodCoverageOption.formatDynamicPath(Data.get("ApplicantHaveFloodPolicy")).click();
		}

		if (Data.get("ChangeInspectionContactDetails").equalsIgnoreCase("Yes")) {
			addInspectionContact(Data);
		}

		if (Data.get("ChangeProducerContactDetails").equalsIgnoreCase("Yes")) {
			addContactInformation(Data);
		}

		if (dueDiligenceCheckbox.checkIfElementIsPresent() && dueDiligenceCheckbox.checkIfElementIsDisplayed()) {
			dueDiligenceCheckbox.scrollToElement();
			dueDiligenceCheckbox.select();
		}
        waitTime(2); // Added waittime as not clicking on submit button in headless
		submit.scrollToElement();
		submit.click();
        waitTime(2); // Added waittime as not clicking on request bind button in headless
		if (requestBind.checkIfElementIsPresent()) {
			Assertions.passTest("Confirm Bind Request Page", "Confirm Bind Request page loaded successfully");
			page = confirmBindNAHO(Data);
			Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
		} else {
			Assertions.failTest("Request Bind", "Failed to Load Confirm Bind Pop-up");
		}
		if (pageName.getData().contains("Policy Summary")) {
			return new PolicySummaryPage();
		}
		return null;
	}

	public PolicySummaryPage approveRequestCommercialData(Map<String, String> Data) {
		approve.waitTillVisibilityOfElement(60);

		// don't set carrier, just use default, or select the first one if no default is selected and one isn't defined in the spreadsheet
		// this is for a barrier island carrier issue with scenarios 4 & 14
		if (!Data.get("Carrier").equals(null) && !Data.get("Carrier").equals("")) {
			carrierArrow.scrollToElement();
			carrierArrow.click();
			carrierOptionByText.formatDynamicPath(Data.get("Carrier")).scrollToElement();
			carrierOptionByText.formatDynamicPath(Data.get("Carrier")).click();
		} else if (carrier.getData().equals("Please select"))  {
			carrierArrow.scrollToElement();
			carrierArrow.click();
			firstCarrierOption.scrollToElement();
			firstCarrierOption.click();
		}

		approve.scrollToElement();
		approve.click();
		waitTime(2);

		if (approveBackDating.checkIfElementIsPresent() && approveBackDating.checkIfElementIsDisplayed()) {
			System.out.println("In Approve Backdating");
			approveBackDating.moveToElement();
			approveBackDating.scrollToElement();
			approveBackDating.click();
			waitTime(2);
		}
		if (overrideEffectiveDate.checkIfElementIsPresent() && overrideEffectiveDate.checkIfElementIsDisplayed()) {
			System.out.println("In Override Effective Date");
			overrideEffectiveDate.select();
			approve.scrollToElement();
			approve.click();
			waitTime(1);
		}
		if (approveBackDating.checkIfElementIsPresent() && approveBackDating.checkIfElementIsDisplayed()) {
			System.out.println("In Approve Backdating");
			approveBackDating.moveToElement();
			approveBackDating.scrollToElement();
			approveBackDating.click();
			waitTime(2);
		}
		if (pageName.getData().contains("Policy Summary")) {
			return new PolicySummaryPage();
		}
		return null;
	}

	public void enteringRewriteData(Map<String, String> Data) {
		waitTime(3);
		previousPolicyCancellationDate.waitTillVisibilityOfElement(60);
		refreshPage();
		previousPolicyCancellationDate.waitTillElementisEnabled(60);
		previousPolicyCancellationDate.scrollToElement();
		previousPolicyCancellationDate.clearData();
		previousPolicyCancellationDate.setData(Data.get("PreviousPolicyCancellationDate"));
		previousPolicyCancellationDate.tab();
		previousPolicyCancellationDate.waitTillTextToBePresent(60);

		if (internalComments.checkIfElementIsPresent()) {
			internalComments.scrollToElement();
			internalComments.setData("Test");
		}
		if (externalComments.checkIfElementIsPresent()) {
			externalComments.scrollToElement();
			externalComments.setData("Test");
		}
		rewrite.scrollToElement();
		rewrite.click();
		waitTime(1);
	}

	public void enteringRewriteDataNAHO(Map<String, String> Data) {
		waitTime(3);

		if (!Data.get("PolicyEffDate").equals("")) {
			effectiveDate.scrollToElement();
			effectiveDate.clearData();
			effectiveDate.setData(Data.get("PolicyEffDate"));
			effectiveDate.tab();
		}
		waitTime(3);
		if(okButton.checkIfElementIsPresent()&&okButton.checkIfElementIsDisplayed()) {
			okButton.waitTillVisibilityOfElement(60);
			okButton.scrollToElement();
			okButton.click();
		}
		waitTime(3);
		if (wanttoContinue.checkIfElementIsPresent() && wanttoContinue.checkIfElementIsDisplayed()) {
			wanttoContinue.waitTillVisibilityOfElement(60);
			wanttoContinue.scrollToElement();
			wanttoContinue.click();
			accountOverviewPage.requestBind.scrollToElement();
			accountOverviewPage.requestBind.click();
		}
		waitTime(3);
		previousPolicyCancellationDate.waitTillVisibilityOfElement(60);
		refreshPage();
		previousPolicyCancellationDate.waitTillElementisEnabled(60);
		previousPolicyCancellationDate.scrollToElement();
		previousPolicyCancellationDate.clearData();
		previousPolicyCancellationDate.setData(Data.get("CancellationEffectiveDate"));
		previousPolicyCancellationDate.tab();
		previousPolicyCancellationDate.waitTillTextToBePresent(60);
		waitTime(3);
		if (wanttoContinue.checkIfElementIsPresent() && wanttoContinue.checkIfElementIsDisplayed()) {
			wanttoContinue.waitTillVisibilityOfElement(60);
			wanttoContinue.scrollToElement();
			wanttoContinue.click();
			accountOverviewPage.requestBind.scrollToElement();
			accountOverviewPage.requestBind.click();
		}

		if (chooseFile.checkIfElementIsPresent() && chooseFile.checkIfElementIsDisplayed()) {
			if (!Data.get("FileNameToUpload").equals("")) {
				PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
				policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
			}
		}

		if (entityArrow.checkIfElementIsPresent() && entityArrow.checkIfElementIsDisplayed()) {
			entityArrow.scrollToElement();
			entityArrow.click();
			entityOption.formatDynamicPath(Data.get("Entity")).scrollToElement();
			entityOption.formatDynamicPath(Data.get("Entity")).click();
		}

		if (grantorOrBenificiaryDifferentArrow.checkIfElementIsPresent()
				&& grantorOrBenificiaryDifferentArrow.checkIfElementIsDisplayed()) {
			grantorOrBenificiaryDifferentArrow.waitTillVisibilityOfElement(60);
			grantorOrBenificiaryDifferentArrow.scrollToElement();
			grantorOrBenificiaryDifferentArrow.click();

			grantorOrBenificiaryDifferentOptions.formatDynamicPath(Data.get("Grantor/BeneficiaryDifferent"))
					.scrollToElement();
			grantorOrBenificiaryDifferentOptions.formatDynamicPath(Data.get("Grantor/BeneficiaryDifferent")).click();
		}

		if (floodCoverageArrow.checkIfElementIsPresent() && floodCoverageArrow.checkIfElementIsDisplayed()) {
			floodCoverageArrow.scrollToElement();
			floodCoverageArrow.click();
			floodCoverageOption.formatDynamicPath(Data.get("ApplicantHaveFloodPolicy")).scrollToElement();
			floodCoverageOption.formatDynamicPath(Data.get("ApplicantHaveFloodPolicy")).click();
		}

		if (internalComments.checkIfElementIsPresent()) {
			internalComments.scrollToElement();
			internalComments.setData("Test");
		}
		if (externalComments.checkIfElementIsPresent()) {
			externalComments.scrollToElement();
			externalComments.setData("Test");
		}
		if (dueDiligenceCheckbox.checkIfElementIsPresent() && dueDiligenceCheckbox.checkIfElementIsDisplayed()) {
			dueDiligenceCheckbox.scrollToElement();
			dueDiligenceCheckbox.select();
		}

		rewrite.scrollToElement();
		rewrite.click();
		waitTime(2);
		if (backdatingRewrite.checkIfElementIsPresent() && backdatingRewrite.checkIfElementIsDisplayed()) {
			backdatingRewrite.waitTillVisibilityOfElement(60);
			backdatingRewrite.scrollToElement();
			backdatingRewrite.click();
		}
		//Adding this condition to handle deductible minimum warning
		if (duediligenceClick.checkIfElementIsPresent() && duediligenceClick.checkIfElementIsDisplayed()) {
			duediligenceClick.waitTillVisibilityOfElement(60);
			duediligenceClick.scrollToElement();
			duediligenceClick.click();
			waitTime(3);
			switchToChildWindow();
			if (pageName.getData().contains("Quote Documents")) {
				PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
				policyDocumentsPage.addDocumentButton.scrollToElement();
				policyDocumentsPage.addDocumentButton.click();

				if (!Data.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
				}
				waitTime(8);// adding wait time to load the element
				policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
				policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
				policyDocumentsPage.backButton.scrollToElement();
				policyDocumentsPage.backButton.click();
				switchToMainWindow();
				refreshPage();
			}

		}
		if (chooseFile.checkIfElementIsPresent() && chooseFile.checkIfElementIsDisplayed()) {
			if (!Data.get("FileNameToUpload").equals("")) {
				PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
				policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
			}
			rewrite.scrollToElement();
			rewrite.click();
			waitTime(2);
			if (backdatingRewrite.checkIfElementIsPresent() && backdatingRewrite.checkIfElementIsDisplayed()) {
				backdatingRewrite.waitTillVisibilityOfElement(60);
				backdatingRewrite.scrollToElement();
				backdatingRewrite.click();
			}

			// Adding carrier for re-write
			if (effDateErrorMsg.formatDynamicPath("The suggested Carrier").checkIfElementIsPresent()
					&& effDateErrorMsg.formatDynamicPath("The suggested Carrier").checkIfElementIsDisplayed()) {
				carrierArrow.scrollToElement();
				carrierArrow.click();
				carrierArrowSelection.formatDynamicPath("Victor Insurance Exchange").checkIfElementIsPresent();
				carrierArrowSelection.formatDynamicPath("Victor Insurance Exchange").checkIfElementIsDisplayed();
				carrierArrowSelection.formatDynamicPath("Victor Insurance Exchange").click();
				rewrite.scrollToElement();
				rewrite.click();

			}
		}
	}


	public PolicySummaryPage approveRequestNAHO(Map<String, String> Data) {
		if (entityArrow.checkIfElementIsPresent() && entityArrow.checkIfElementIsDisplayed()
				&& !Data.get("Entity").equals("")) {
			entityArrow.scrollToElement();
			entityArrow.click();
			entityOption.formatDynamicPath(Data.get("Entity")).scrollToElement();
			entityOption.formatDynamicPath(Data.get("Entity")).click();
		}
		if (grantorOrBenificiaryDifferentArrow.checkIfElementIsPresent()
				&& grantorOrBenificiaryDifferentArrow.checkIfElementIsDisplayed()
				&& !Data.get("Grantor/BeneficiaryDifferent").equals("")) {
			grantorOrBenificiaryDifferentArrow.scrollToElement();
			grantorOrBenificiaryDifferentArrow.click();
			grantorOrBenificiaryDifferentOptions.formatDynamicPath(Data.get("Grantor/BeneficiaryDifferent"))
					.scrollToElement();
			grantorOrBenificiaryDifferentOptions.formatDynamicPath(Data.get("Grantor/BeneficiaryDifferent")).click();
		}
		if (grantorOrBenificiaryName.checkIfElementIsPresent() && grantorOrBenificiaryName.checkIfElementIsDisplayed()
				&& !Data.get("Grantor/BeneficiaryName").equals("")) {
			grantorOrBenificiaryName.scrollToElement();
			grantorOrBenificiaryName.setData(Data.get("Grantor/BeneficiaryName"));
		}
		if (chooseFile.checkIfElementIsPresent() && chooseFile.checkIfElementIsDisplayed()) {
			if (!Data.get("FileNameToUpload").equals("")) {
				PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
				policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
			}
		}
		if (dueDiligenceCheckbox.checkIfElementIsPresent() && dueDiligenceCheckbox.checkIfElementIsDisplayed()) {
			dueDiligenceCheckbox.scrollToElement();
			dueDiligenceCheckbox.select();
		}
		approve.waitTillVisibilityOfElement(60);
		approve.scrollToElement();
		approve.click();
		waitTime(2);

		if (approveBackDating.checkIfElementIsPresent() && approveBackDating.checkIfElementIsDisplayed()) {
			approveBackDating.moveToElement();
			approveBackDating.scrollToElement();
			approveBackDating.click();
		}

		if (overrideEffectiveDate.checkIfElementIsPresent() && overrideEffectiveDate.checkIfElementIsDisplayed()) {
			overrideEffectiveDate.scrollToElement();
			overrideEffectiveDate.select();
			if (chooseFile.checkIfElementIsPresent() && chooseFile.checkIfElementIsDisplayed()) {
				if (!Data.get("FileNameToUpload").equals("")) {
					PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
					policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
				}
			}
			approve.scrollToElement();
			approve.click();
			waitTime(2);
		}
		if (approveBackDating.checkIfElementIsPresent() && approveBackDating.checkIfElementIsDisplayed()) {
			approveBackDating.moveToElement();
			approveBackDating.scrollToElement();
			approveBackDating.click();
		}
		if (pageName.getData().contains("Policy Summary")) {
			return new PolicySummaryPage();
		}
		return null;
	}

	public BasePageControl confirmBindNAHO(Map<String, String> Data) {
		requestBind.waitTillVisibilityOfElement(60);
		requestBind.scrollToElement();
		requestBind.click();
		if (overrideEffectiveDate.checkIfElementIsPresent() && overrideEffectiveDate.checkIfElementIsDisplayed()) {
			overrideEffectiveDate.scrollToElement();
			overrideEffectiveDate.select();
			// upload file
			if (chooseFile.checkIfElementIsPresent() && chooseFile.checkIfElementIsDisplayed()) {
				if (!Data.get("FileNameToUpload").equals("")) {
					PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
					policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
				}
			}
			submit.scrollToElement();
			submit.click();
			requestBind.waitTillVisibilityOfElement(60);
			requestBind.scrollToElement();
			requestBind.click();
			requestBind.waitTillInVisibilityOfElement(60);
		}
		requestBind.waitTillInVisibilityOfElement(60);
		if (pageName.getData().contains("Bind Request")) {
			return new BindRequestSubmittedPage();
		} else if (pageName.getData().contains("Policy Summary")) {
			return new PolicySummaryPage();
		}
		return null;
	}

	public BasePageControl enterBindDetailsNAHO(Map<String, String> Data) {
		enterPolicyDetailsNAHO(Data);
		if (Data.get("TRIACoverage") != null) {
			if (!Data.get("TRIACoverage").equals("")) {
				setTria(Data.get("TRIACoverage"));
			}
		}
		enterPaymentInformationNAHO(Data);
		addInspectionContact(Data);
		if (!Data.get("ProductSelection").equalsIgnoreCase("Residential Earthquake")) {
			addAdditionalInterest(Data);
		}
		addContactInformation(Data);
		if (Data.get("ProductSelection").equalsIgnoreCase("Residential Earthquake")) {
			addAdditionalInterestEQHO(Data);
			verifypaymentinformation();
		}
		waitTime(2); // Added waittime as not clicking on submit button in headless
		if (submit.checkIfElementIsPresent() && submit.checkIfElementIsDisplayed()) {
			submit.scrollToElement();
			submit.click();
		}
		waitTime(2); // Added waittime as not clicking on request bind button in headless
		if (requestBind.checkIfElementIsPresent() && requestBind.checkIfElementIsDisplayed()) {
			Assertions.passTest("Confirm Bind Request Page", "Confirm Bind Request page loaded successfully");
			page = confirmBindNAHO(Data);
			Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
		} else
			Assertions.failTest("Request Bind", "Failed to Load Confirm Bind Pop-up");
		return page;
	}


	public void enterPolicyDetailsNAHO(Map<String, String> Data) {
		effectiveDate.waitTillVisibilityOfElement(60);
		if (Data.get("PolicyEffDate").equals("")) {
			effectiveDate.scrollToElement();
			effectiveDate.clearData();
			effectiveDate.setData(new DateConversions().getCurrentDate("MM/dd/yyyy"));
		} else {
			effectiveDate.clearData();
			effectiveDate.setData(Data.get("PolicyEffDate"));
		}
		namedInsured.clearData();
		namedInsured.setData(Data.get("InsuredName"));
		namedInsured.tab();
		waitTime(3); // If waittime is removed,Element Not Interactable
		// exception is
		// thrown.Waittillpresence and Waittillvisibility is not working here

		if (no_NameChange.checkIfElementIsPresent() && no_NameChange.checkIfElementIsDisplayed()) {
			no_NameChange.waitTillPresenceOfElement(60);
			no_NameChange.waitTillVisibilityOfElement(60);
			no_NameChange.scrollToElement();
			no_NameChange.click();
			no_NameChange.waitTillInVisibilityOfElement(60);
		}
		if (!Data.get("ExtendedNamedInsured").equals("")) {
			extendedNamedInsured.scrollToElement();
			extendedNamedInsured.setData(Data.get("ExtendedNamedInsured"));
		}
		if (entityArrow.checkIfElementIsPresent() && entityArrow.checkIfElementIsDisplayed()) {
			entityArrow.scrollToElement();
			entityArrow.click();
			waitTime(3);
			entityOption.formatDynamicPath(Data.get("Entity")).scrollToElement();
			entityOption.formatDynamicPath(Data.get("Entity")).click();
		}
		if (grantorOrBenificiaryDifferentArrow.checkIfElementIsPresent()
				&& grantorOrBenificiaryDifferentArrow.checkIfElementIsDisplayed()) {
			grantorOrBenificiaryDifferentArrow.scrollToElement();
			grantorOrBenificiaryDifferentArrow.click();
			grantorOrBenificiaryDifferentOptions.formatDynamicPath(Data.get("Grantor/BeneficiaryDifferent"))
					.scrollToElement();
			grantorOrBenificiaryDifferentOptions.formatDynamicPath(Data.get("Grantor/BeneficiaryDifferent")).click();
		}
		if (grantorOrBenificiaryName.checkIfElementIsPresent()
				&& grantorOrBenificiaryName.checkIfElementIsDisplayed()) {
			grantorOrBenificiaryName.scrollToElement();
			grantorOrBenificiaryName.setData(Data.get("Grantor/BeneficiaryName"));
		}
		if (chooseFile.checkIfElementIsPresent() && chooseFile.checkIfElementIsDisplayed()) {
			if (!Data.get("FileNameToUpload").equals("")) {
				PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
				policyDocumentsPage.fileUpload(Data.get("FileNameToUpload"));
			}
		}
		if (primaryTrusteeFirstName.checkIfElementIsPresent() && primaryTrusteeFirstName.checkIfElementIsDisplayed()
				&& !Data.get("PrimaryTrusteeFirstName").equalsIgnoreCase("")) {
			primaryTrusteeFirstName.scrollToElement();
			primaryTrusteeFirstName.setData(Data.get("PrimaryTrusteeFirstName"));
		}
		if (primaryTrusteeLastName.checkIfElementIsPresent() && primaryTrusteeLastName.checkIfElementIsDisplayed()
				&& !Data.get("PrimaryTrusteeLastName").equalsIgnoreCase("")) {
			primaryTrusteeLastName.scrollToElement();
			primaryTrusteeLastName.setData(Data.get("PrimaryTrusteeLastName"));
		}
		if (trusteeDateofBirth.checkIfElementIsPresent() && trusteeDateofBirth.checkIfElementIsDisplayed()
				&& !Data.get("TrusteeDateofBirth").equalsIgnoreCase("")) {
			trusteeDateofBirth.scrollToElement();
			trusteeDateofBirth.setData(Data.get("TrusteeDateofBirth"));
		}
		if (secondaryTrusteeFirstName.checkIfElementIsPresent() && secondaryTrusteeFirstName.checkIfElementIsDisplayed()
				&& !Data.get("SecondaryTrusteeFirstName").equalsIgnoreCase("")) {
			secondaryTrusteeFirstName.scrollToElement();
			secondaryTrusteeFirstName.setData(Data.get("SecondaryTrusteeFirstName"));
		}
		if (secondaryTrusteeLastName.checkIfElementIsPresent() && secondaryTrusteeLastName.checkIfElementIsDisplayed()
				&& !Data.get("SecondaryTrusteeLastName").equalsIgnoreCase("")) {
			secondaryTrusteeLastName.scrollToElement();
			secondaryTrusteeLastName.setData(Data.get("SecondaryTrusteeLastName"));
		}

		if (insuredEmail.checkIfElementIsPresent() && insuredEmail.checkIfElementIsDisplayed()
				&& !Data.get("InsuredEmail").equalsIgnoreCase("")) {
			insuredEmail.scrollToElement();
			insuredEmail.setData(Data.get("InsuredEmail"));
			insuredPhoneNoAreaCode.scrollToElement();
			insuredPhoneNoAreaCode.setData(Data.get("InsuredPhoneNumAreaCode"));
			insuredPhoneNoPrefix.scrollToElement();
			insuredPhoneNoPrefix.setData(Data.get("InsuredPhoneNumPrefix"));
			insuredPhoneNoEnd.scrollToElement();
			insuredPhoneNoEnd.setData(Data.get("InsuredPhoneNum"));
		}
		if (Data.containsKey("InsuredCountry")) {
			if (!Data.get("InsuredCountry").equalsIgnoreCase("USA")) {
				WebElement ele = WebDriverManager.getCurrentDriver()
						.findElement(By.xpath("//div[label[contains(text(),'Country')]]//input"));
				ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
				ele.sendKeys(Data.get("InsuredCountry"));
				ele.sendKeys(Keys.ARROW_RIGHT);
				waitTime(5);

				List<WebElement> listOfElements = WebDriverManager.getCurrentDriver()
						.findElements(By.xpath("//select[@id='insuredNameAddressModel.address.country']//option"));
				waitTime(5);

				for (int i = 0; i < listOfElements.size(); i++) {
					if (listOfElements.get(i).equals(Data.get("InsuredCountry"))) {
						listOfElements.get(i).click();
						break;
					}
				}
			}
		} else {
			WebElement ele = WebDriverManager.getCurrentDriver()
					.findElement(By.xpath("//div[label[contains(text(),'Country')]]//input"));
			ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Data.get("InsuredCountry"));
			insuredCountry.tab();
		}
		if (enterAddressManuallLink.checkIfElementIsPresent() && enterAddressManuallLink.checkIfElementIsDisplayed()) {
			enterAddressManuallLink.scrollToElement();
			enterAddressManuallLink.click();
		}
		addressLine1.waitTillVisibilityOfElement(60);
		addressLine1.scrollToElement();
		addressLine1.setData(Data.get("InsuredAddr1"));
		if (!Data.get("InsuredAddr2").equals("")) {
			address2.scrollToElement();
			address2.setData(Data.get("InsuredAddr2"));
		}
		if (insuredCity.checkIfElementIsDisplayed()) {
			insuredCity.scrollToElement();
			insuredCity.setData(Data.get("InsuredCity"));
		}
		if (insuredState.checkIfElementIsPresent() && insuredState.checkIfElementIsDisplayed()) {
			insuredState.scrollToElement();
			insuredState.setData(Data.get("InsuredState"));
			insuredZipCode.setData(Data.get("InsuredZIP"));
		}
		if (province.checkIfElementIsPresent() && province.checkIfElementIsDisplayed()) {
			province.scrollToElement();
			province.setData(Data.get("InsuredState"));
			postalCode.setData(Data.get("InsuredZIP"));
		}
		if (addressLine3.checkIfElementIsDisplayed()) {
			addressLine3.scrollToElement();
			addressLine3
					.setData(Data.get("InsuredCity") + " " + Data.get("InsuredState") + " " + Data.get("InsuredZIP"));
		}
		if (floodCoverageArrow.checkIfElementIsPresent() && floodCoverageArrow.checkIfElementIsDisplayed()) {
			floodCoverageArrow.scrollToElement();
			floodCoverageArrow.click();
			floodCoverageOption.formatDynamicPath(Data.get("ApplicantHaveFloodPolicy")).scrollToElement();
			floodCoverageOption.formatDynamicPath(Data.get("ApplicantHaveFloodPolicy")).click();
		}
		if (previousPolicyCancellationDate.checkIfElementIsPresent()
				&& previousPolicyCancellationDate.checkIfElementIsDisplayed()) {
			previousPolicyCancellationDate.scrollToElement();
			previousPolicyCancellationDate.clearData();
			previousPolicyCancellationDate.setData(new DateConversions().getCurrentDate("MM/dd/yyyy"));
		}
	}

}
