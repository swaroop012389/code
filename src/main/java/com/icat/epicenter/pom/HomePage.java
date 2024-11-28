/** Program Description: Object Locators and methods defined in Homepage
 *  Author			   : SMNetserv
 *  Date of Creation   : 11/11/2017
 **/

package com.icat.epicenter.pom;

import java.io.File;
import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class HomePage extends BasePageControl {
	public ButtonControl sovUpload;
	public TextFieldControl chooseSOV;
	public TextFieldControl sovProducerNumber;
	public ButtonControl upload;

	public ButtonControl createNewAccount;
	public ButtonControl createNewAccountProducer;
	public TextFieldControl namedInsured;
	public TextFieldControl producerNumber;
	public ButtonControl goButton;
	public CheckBoxControl usmDateOverride;

	public ButtonControl productArrow;
	public HyperLink productSelection;
	public TextFieldControl effectiveDate;
	public RadioButtonControl policyHolder_Yes;
	public RadioButtonControl policyHolder_No;
	public BaseWebElementControl close;

	public ButtonControl referralStatus;
	public HyperLink referralquoteLink;

	public ButtonControl findFilterArrow;
	public ButtonControl findFilterAccountOption;
	public ButtonControl findFilterQuoteOption;
	public ButtonControl findFilterPolicyOption;

	public TextFieldControl findAccountNamedInsured;
	public TextFieldControl findAccountProducerNumber;
	public RadioButtonControl findAccountTypeAll;
	public RadioButtonControl findAccountTypePolicy;
	public RadioButtonControl findAccountTypeNotBound;
	public ButtonControl findBtn;
	public ButtonControl findBtnAccount;
	public ButtonControl accountRecord;

	public TextFieldControl findQuoteNumber;
	public ButtonControl findBtnQuote;

	public TextFieldControl policyNumber;
	public ButtonControl findPolicyButton;

	public HyperLink goToHomepage;

	public ButtonControl producerAccountSearchButton;
	public TextFieldControl producerAccountNameSearchTextbox;
	public ButtonControl producerAccountFindButton;
	public HyperLink producerAccountNameLink;

	public ButtonControl producerQuoteSearchButton;
	public TextFieldControl producerQuoteNumberSearchTextbox;
	public ButtonControl producerQuoteFindButton;
	public HyperLink producerQuoteNumberLink;

	public ButtonControl producerPolicySearchButton;
	public TextFieldControl producerPolicyNumberSearchTextbox;
	public ButtonControl producerPolicyFindButton;
	public HyperLink producerPolicyNumberLink;
	public HyperLink prodQuoteSearchResult;

	public HyperLink expaccLink;
	public HyperLink adminLink;
	public BaseWebElementControl loading;
	public BaseWebElementControl accountSuccessfullyDeletedMsg;

	public ButtonControl signOutButton;
	public BaseWebElementControl pageName;

	public BaseWebElementControl namedInsuredMandatoryError;

	public ButtonControl policyStatusArrow;
	public HyperLink policyStatusOption;
	public ButtonControl findFilterBuildingOption;
	public ButtonControl findFilterBinderOption;
	public ButtonControl findFilterRenewalOption;
	public TextFieldControl buildingAddress;
	public TextFieldControl buildingCity;
	public TextFieldControl buildingZipcode;
	public ButtonControl stateArrow;
	public ButtonControl stateOption;
	public TextFieldControl buildingProducerNumberField;
	public HyperLink searchResult;
	public HyperLink searchResultNAHO;
	public TextFieldControl buildingPolicyNumberField;
	public TextFieldControl buildingNamedInsuredfield;
	public ButtonControl buildingFindButton;

	public TextFieldControl quotecreationDateAfterfield;
	public TextFieldControl quoteInsuredNameField;

	public TextFieldControl binderInsuredNameField;
	public TextFieldControl binderQuoteorPolicyNumberFiled;
	public TextFieldControl binderProducerNumberField;
	public TextFieldControl binderCreationDateAfter;
	public ButtonControl binderFindButton;

	public ButtonControl policyStateArrow;
	public ButtonControl policyStateOption;
	public TextFieldControl policyCreationDateAfter;
	public TextFieldControl policyProducerNumField;
	public ButtonControl policyRadiobtns;
	public TextFieldControl policyInsuredNameField;
	public ButtonControl accountStatusArrow;
	public ButtonControl accountStatusOption;

	public TextFieldControl previousPolicyNumber;
	public TextFieldControl renewalInsuredName;
	public TextFieldControl renewalQuoteNumber;
	public TextFieldControl renewalProducerNumField;
	public TextFieldControl renewalEffDateAfter;
	public ButtonControl renewalStateArrow;
	public ButtonControl renewalStateOption;
	public ButtonControl renewalFindBtn;
	public ButtonControl accountStateArrow;
	public ButtonControl accountStateOption;
	public ButtonControl quoteStateArrow;
	public ButtonControl quoteStateOption;
	public ButtonControl quoteRenewalRadioBtn;
	public TextFieldControl quoteProducerNumField;
	public TextFieldControl findQuoteNameInsured;
	public ButtonControl quoteNameOption;
	public HyperLink resultTable;

	public HyperLink userReferral;
	public HyperLink teamReferral;
	public ButtonControl teamReferralFilter;
	public ButtonControl teamReferralFilterOption;
	public HyperLink userReferralQ3;
	public HyperLink teamReferralQ3;
	public ButtonControl accountPolRadioBtn;

	public TextFieldControl effectiveDateOptional;
	public ButtonControl userPreferences;

	public TextFieldControl zipcodeCheckerField;
	public ButtonControl checkZipcodeButton;
	public HyperLink createAccountInZipLink;
	public BaseWebElementControl effectiveDateLabel;

	public ButtonControl businessTypePolicyArrow;
	public ButtonControl businessTypePolicyOption;
	public TextFieldControl policyGlobleSearch;
	public ButtonControl searchedPolicyButton;
	public BaseWebElementControl invalidResponseForNoEffectiveDate;

	public ButtonControl yesButton;
	public ButtonControl notYetButton;
	public BaseWebElementControl personalLogionPopup;

	public ButtonControl referralStatus1;
	public HyperLink batchBrokerofRecordLink;
	public BaseWebElementControl teamReferralsResultTable;
	public ButtonControl myReferralArrow;
	public ButtonControl myReferralsOption;
	public BaseWebElementControl userResultTable;
	public CheckBoxControl overRideCheckBox;
	public HyperLink sideBarLinks;

	public ButtonControl producerRenewalSearchButton;
	public TextFieldControl producerRenewalNameSearchTextbox;

	public ButtonControl producerBinderSearchButton;
	public TextFieldControl producerBinderNameSearchTextbox;

	public ButtonControl producerBuildingSearchButton;
	public TextFieldControl producerBuildingNameSearchTextbox;

	public TextFieldControl producerQuoteNameSearchTextbox;
	public TextFieldControl producerPolicyNameSearchTextbox;
	public HyperLink resultStatus;
	public BaseWebElementControl userResultStatus;

	public HyperLink buildingLink;
	public HyperLink copyBuilding;
	public HyperLink deleteBuilding;
	public HyperLink deleteYes;
	public HyperLink addBuilding;
	public TextFieldControl quotecreationDateBeforefield;

	public TextFieldControl confirmEffectiveDate;
	public ButtonControl continueButton;

	public ButtonControl businessTypeQuoteArrow;
	public ButtonControl businessTypeQuoteOption;

	public HyperLink underwritingGuidelinesLink;
	public HyperLink subscriptionPolicyholderAgreementLink;

	public BaseWebElementControl effectiveDateErrorMsg;
	public ButtonControl effectiveDateField;
	public ButtonControl teamReferralFiltersArrow;
	public HyperLink teamReferralFiltersOption;
	public HyperLink teamReferralType;
	public HyperLink userDomainMgnt;
	public HyperLink manageOfficeUsers;
	public BaseWebElementControl messageForWrongProducerNo;
	public RadioButtonControl onNoticeRadioBtn;
	public HyperLink resultsTableStateAndStatus;
	public TextFieldControl afterEffectiveDate;
	public TextFieldControl beforeEffectiveDate;
	public TextFieldControl searchField;
	public ButtonControl producerReferralFilterArrow;
	public ButtonControl producerReferralFilterData;
	public HyperLink producerReferralQuoteLink;
	public HyperLink producerReferralTab;
	public HyperLink recentEnhancement;
	public TextFieldControl effectiveDateNew;
	public HyperLink findOptionQuote;
	public TextFieldControl quoteField;
	public ButtonControl findButton;
	public ButtonControl findBtnPolicy;
	public ButtonControl findPolicyButtonProducer;
	public BaseWebElementControl producerNameGrid;
	public HyperLink producerQuoteOption;
	public ButtonControl uplaodOverride;
	public ButtonControl prodFindBtn;
	public ButtonControl quoteLinkButton;
	public ButtonControl quoteSearchBtn;
	public TextFieldControl policySearchTextbox;
	public HyperLink policySearchTab;
	public ButtonControl policySearchBtn;
	public ButtonControl policyNum;
	public ButtonControl createNewAcct;
	public ButtonControl producerBtnQuote;
	public TextFieldControl quoteSearchTextbox;
	public HyperLink quoteSearchTab;
	public HyperLink quoteToSelect;
	public BaseWebElementControl noResultsFound;

	public HomePage() {
		PageObject pageobject = new PageObject("HomePage");
		sovUpload = new ButtonControl(By.xpath(pageobject.getXpath("xp_SOVUpload")));
		chooseSOV = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ChooseSOV")));
		sovProducerNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_SOVProducerNumber")));
		upload = new ButtonControl(By.xpath(pageobject.getXpath("xp_Upload")));

		createNewAccount = new ButtonControl(By.xpath(pageobject.getXpath("xp_CreateNewAccount")));
		createNewAccountProducer = new ButtonControl(By.xpath(pageobject.getXpath("xp_CreateNewAccountProducer")));
		namedInsured = new TextFieldControl(By.xpath(pageobject.getXpath("xp_createNewAccountNamedInsured")));
		producerNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ProducerNumber")));
		goButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_GoButton")));
		usmDateOverride = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_USMDateOverride")));

		productArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_ProductArrow")));
		productSelection = new HyperLink(By.xpath(pageobject.getXpath("xp_ProductSelection")));
		effectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EffectiveDate")));
		policyHolder_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_PolicyHolderYes")));
		policyHolder_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_PolicyHolderNo")));
		close = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CloseSymbol")));

		referralStatus = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReferralStatus")));
		referralquoteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteLink")));

		findFilterArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindFilterArrow")));
		findFilterAccountOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindFilterAccountOption")));
		findAccountNamedInsured = new TextFieldControl(By.xpath(pageobject.getXpath("xp_FindAccountNamedInsured")));
		findAccountProducerNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AccountProducerNumber")));
		findAccountTypeAll = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_AccountTypeAll")));
		findAccountTypePolicy = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_AccountTypePolicy")));
		findAccountTypeNotBound = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_AccountTypeNotBound")));
		findBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_Find_Button")));
		findBtnAccount = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindAccountButton")));
		accountRecord = new ButtonControl(By.xpath(pageobject.getXpath("xp_AccountRecord")));

		findFilterQuoteOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindFilterQuoteOption")));
		findQuoteNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_QuoteNumber")));
		findBtnQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteFindButton")));

		findFilterPolicyOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindFilterPolicyOption")));
		policyNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyNumber")));
		findPolicyButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_PolicyFindButton")));

		goToHomepage = new HyperLink(By.xpath(pageobject.getXpath("xp_GotoHomepage")));

		producerAccountSearchButton = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_ProducerAccountSearchButton")));
		producerAccountNameSearchTextbox = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_ProducerAccountNameSearchTextbox")));
		producerAccountFindButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ProducerAccountFindBtn")));
		producerAccountNameLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ProducerAccountNameLink")));

		producerQuoteSearchButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ProducerQuoteSearchButton")));
		producerQuoteNumberSearchTextbox = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_ProducerQuoteNumSearchTextbox")));
		producerQuoteFindButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ProducerQuoteFindBtn")));
		producerQuoteNumberLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ProducerQuoteNumberLink")));

		producerPolicySearchButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ProducerPolicySearchButton")));
		producerPolicyNumberSearchTextbox = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_ProducerPolicyNumSearchTextbox")));
		producerPolicyFindButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ProducerPolicyFindBtn")));
		producerPolicyNumberLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ProducerPolicyNumberLink")));
		prodQuoteSearchResult = new HyperLink(By.xpath(pageobject.getXpath("xp_ProdQuoteSearchResult")));

		expaccLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ExpaccLink")));
		adminLink = new HyperLink(By.xpath(pageobject.getXpath("xp_AdminLink")));
		loading = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Loading")));
		accountSuccessfullyDeletedMsg = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AccountSuccessfullyDeletedMsg")));

		signOutButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Signout")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
		namedInsuredMandatoryError = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_NamedInsuredMandatoryError")));

		policyStatusArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_PolicyStatusArrow")));
		policyStatusOption = new HyperLink(By.xpath(pageobject.getXpath("xp_PolicyStatusOption")));
		findFilterBuildingOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindFilterBuildingOption")));
		findFilterBinderOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindFilterBinderOption")));
		findFilterRenewalOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindFilterRenewalOption")));
		buildingAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BuildingAddress")));
		buildingCity = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BuildingCity")));
		buildingZipcode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BuildingZipcode")));
		stateArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_StateArrow")));
		stateOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_StateOption")));
		buildingProducerNumberField = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_BuildingProducerNumberField")));
		searchResult = new HyperLink(By.xpath(pageobject.getXpath("xp_SearchResult")));
		searchResultNAHO = new HyperLink(By.xpath(pageobject.getXpath("xp_SearchResultNAHO")));
		buildingPolicyNumberField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BuildingPolicyNumberField")));
		buildingNamedInsuredfield = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BuildingNamedInsuredfield")));
		buildingFindButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_BuildingFindButton")));

		quotecreationDateAfterfield = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_QuoteCreationDateAfterfield")));
		quoteInsuredNameField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_QuoteInsuredNameField")));

		binderInsuredNameField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BinderInsuredNameField")));
		binderQuoteorPolicyNumberFiled = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_BinderQuoteorPolicyNumberField")));
		binderProducerNumberField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BinderProducerNumberField")));
		binderCreationDateAfter = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BinderCreationDateAfter")));
		binderFindButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_BinderFindButton")));

		policyStateArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_PolicyStateArrow")));
		policyStateOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_PolicyStateOption")));
		policyCreationDateAfter = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyCreationDateAfter")));
		policyProducerNumField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyProducerNumField")));
		policyRadiobtns = new ButtonControl(By.xpath(pageobject.getXpath("xp_PolicyRadiobtns")));
		policyInsuredNameField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyInsuredNameField")));
		accountStatusArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_AccountStatusArrow")));
		accountStatusOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_AccountStatusOption")));
		previousPolicyNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PreviousPolicyNumber")));
		renewalInsuredName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RenewalInsuredName")));
		renewalQuoteNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RenewalQuoteNumber")));
		renewalProducerNumField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RenewalProducerNumField")));
		renewalEffDateAfter = new TextFieldControl(By.xpath(pageobject.getXpath("xp_RenewalEffDateAfter")));
		renewalStateArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_RenewalStateArrow")));
		renewalStateOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_RenewalStateOption")));
		renewalFindBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_RenewalFindBtn")));
		accountStateArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_AccountStateArrow")));
		accountStateOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_AccountStateOption")));
		quoteStateArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteStateArrow")));
		quoteStateOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteStateOption")));
		quoteRenewalRadioBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteRenewalRadioBtn")));
		quoteProducerNumField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_QuoteProducerNumField")));
		resultTable = new HyperLink(By.xpath(pageobject.getXpath("xp_ResultTable")));

		userReferral = new HyperLink(By.xpath(pageobject.getXpath("xp_UserReferral")));
		teamReferral = new HyperLink(By.xpath(pageobject.getXpath("xp_TeamReferral")));
		teamReferralFilter = new ButtonControl(By.xpath(pageobject.getXpath("xp_TeamReferralFilter")));
		teamReferralFilterOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_TeamReferralFilterOption")));
		userReferralQ3 = new HyperLink(By.xpath(pageobject.getXpath("xp_UserReferralQ3")));
		teamReferralQ3 = new HyperLink(By.xpath(pageobject.getXpath("xp_TeamReferralQ3")));
		accountPolRadioBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_AccountPolRadioBtn")));
		effectiveDateOptional = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EffectiveDateOptional")));
		userPreferences = new ButtonControl(By.xpath(pageobject.getXpath("xp_UserPreferences")));

		zipcodeCheckerField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ZipcoderCheckerField")));
		checkZipcodeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CheckZipcodeButton")));
		createAccountInZipLink = new HyperLink(By.xpath(pageobject.getXpath("xp_CreateAccountInZipLink")));
		effectiveDateLabel = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EffectiveDateLabel")));
		findQuoteNameInsured = new TextFieldControl(By.xpath(pageobject.getXpath("xp_FindQuoteNameInsured")));
		quoteNameOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteNameOption")));

		businessTypeQuoteArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_BusinessTypeQuoteArrow")));
		businessTypeQuoteOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_BusinessTypeQuoteOption")));

		businessTypePolicyArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_BusinessTypePolicyArrow")));
		businessTypePolicyOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_BusinessTypePolicyOption")));
		policyGlobleSearch = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyGlobleSearch")));
		searchedPolicyButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_SearchedPolicyButton")));
		invalidResponseForNoEffectiveDate = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_InvalidResponseForNoEffectiveDateGiven")));
		yesButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_YesButton")));
		notYetButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_NotYetButton")));
		personalLogionPopup = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PersonalLogionPopup")));

		referralStatus1 = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReferralStatus1")));
		batchBrokerofRecordLink = new HyperLink(By.xpath(pageobject.getXpath("xp_BatchBrokerofRecordLink")));
		teamReferralsResultTable = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_TeamReferralsResultTable")));
		myReferralArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_MyReferralArrow")));
		myReferralsOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_MyReferralsOption")));
		userResultTable = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_UserResultTable")));
		overRideCheckBox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_OverrideCheckBox")));
		sideBarLinks = new HyperLink(By.xpath(pageobject.getXpath("xp_SideBarLinks")));

		producerRenewalSearchButton = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_ProducerRenewalSearchButton")));
		producerRenewalNameSearchTextbox = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_ProducerRenewalNameSearchTextbox")));

		producerBinderSearchButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ProducerBinderSearchButton")));
		producerBinderNameSearchTextbox = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_ProducerBinderNameSearchTextbox")));

		producerBuildingSearchButton = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_ProducerBuildingSearchButton")));
		producerBuildingNameSearchTextbox = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_ProducerBuildingNameSearchTextbox")));

		producerQuoteNameSearchTextbox = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_ProducerQuoteNameSearchTextbox")));
		producerPolicyNameSearchTextbox = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_ProducerPolicyNameSearchTextbox")));
		resultStatus = new HyperLink(By.xpath(pageobject.getXpath("xp_ResultStatus")));
		userResultStatus = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_UserResultStatus")));
		buildingLink = new HyperLink(By.xpath(pageobject.getXpath("xp_BuildingLink")));
		copyBuilding = new HyperLink(By.xpath(pageobject.getXpath("xp_CopyBuilding")));
		deleteBuilding = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteBuilding")));
		deleteYes = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteYes")));
		addBuilding = new HyperLink(By.xpath(pageobject.getXpath("xp_AddBuilding")));
		quotecreationDateBeforefield = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_QuoteCreationDateBeforefield")));
		confirmEffectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ConfirmEffectiveDate")));
		continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueButton")));

		underwritingGuidelinesLink = new HyperLink(By.xpath(pageobject.getXpath("xp_UnderwritingGuidelinesLink")));
		subscriptionPolicyholderAgreementLink = new HyperLink(
				By.xpath(pageobject.getXpath("xp_SubscriptionPolicyholderAgreementLink")));
		effectiveDateErrorMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EffectiveDateErrorMsg")));
		effectiveDateField = new ButtonControl(By.xpath(pageobject.getXpath("xp_EffectiveDateField")));
		teamReferralFiltersArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_TeamReferralsFilterArrow")));
		teamReferralFiltersOption = new HyperLink(By.xpath(pageobject.getXpath("xp_TeamReferralsFilterOptions")));
		teamReferralType = new HyperLink(By.xpath(pageobject.getXpath("xp_TeamReferralType")));
		userDomainMgnt = new HyperLink(By.xpath(pageobject.getXpath("xp_UserDomainMgnt")));
		manageOfficeUsers = new HyperLink(By.xpath(pageobject.getXpath("xp_ManageOfficeUsers")));
		messageForWrongProducerNo = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_MessageForWrongProducerNo")));
		onNoticeRadioBtn = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_OnNoticeRadioBtn")));
		resultsTableStateAndStatus = new HyperLink(By.xpath(pageobject.getXpath("xp_ResultsTableStateAndStatus")));
		afterEffectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AfterEffectiveDate")));
		beforeEffectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BeforeEffectiveDate")));
		searchField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_SearchField")));
		producerReferralFilterArrow = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_ProducerReferralFilterArrow")));
		producerReferralFilterData = new ButtonControl(By.xpath(pageobject.getXpath("xp_ProducerReferralFilterData")));
		producerReferralQuoteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_ProducerReferralQuoteLink")));
		producerReferralTab = new HyperLink(By.xpath(pageobject.getXpath("xp_ProducerReferralTab")));
		recentEnhancement = new HyperLink(By.xpath(pageobject.getXpath("xp_RecentEnhancement")));
		effectiveDateNew = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EffectiveDateNew")));
		findOptionQuote = new HyperLink(By.xpath(pageobject.getXpath("xp_FindOptionQuote")));
		quoteField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_QuoteField")));
		findButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindButton")));
		findBtnPolicy = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindBtnPolicy")));
		findPolicyButtonProducer = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindButtonPolicyProducer")));
		uplaodOverride = new ButtonControl(By.xpath(pageobject.getXpath("xp_UploadOverride")));
		producerNameGrid = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProducerNameGrid")));
		producerQuoteOption = new HyperLink(By.xpath(pageobject.getXpath("xp_ProdSearchByQuote")));
		prodFindBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_ProdFindBtn")));
		quoteLinkButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteLinkButton")));
		quoteSearchBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteSearchBtn")));
		policySearchTab = new HyperLink(By.xpath(pageobject.getXpath("xp_PolicySerachtab")));
		policySearchTextbox = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicySearchtextBox")));
		policySearchBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_PolicySearchBtn")));
		policyNum = new ButtonControl(By.xpath(pageobject.getXpath("xp_PolicyNumberData")));
		createNewAcct = new ButtonControl(By.xpath(pageobject.getXpath("xp_CreateNewAcct")));
		producerBtnQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindbuttonHomepage")));
		quoteSearchTextbox = new TextFieldControl(By.xpath(pageobject.getXpath("xp_QuoteSearchTextbox")));
		quoteSearchTab = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteSerachTab")));
		quoteToSelect = new HyperLink(By.xpath(pageobject.getXpath("xp_QuoteToSelect")));
		noResultsFound = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NoResultsFound")));
	}

	/**
	 * Create account not using override.
	 */
	public BasePageControl createNewAccountWithNamedInsured(Map<String, String> data, Map<String, String> dataSetup) {
		return createNewAccountWithNamedInsured(data, dataSetup, false);
	}

	/**
	 * Create a new account and check the override box if that option is true.
	 */
	public BasePageControl createNewAccountWithNamedInsured(Map<String, String> data, Map<String, String> dataSetup,
			boolean allowOverride) {
		goToHomepage.scrollToElement();
		goToHomepage.click();
		if (createNewAccount.checkIfElementIsPresent() && createNewAccount.checkIfElementIsDisplayed()) {
			createNewAccount.scrollToElement();
			createNewAccount.click();
		} else {
			createNewAccountProducer.moveToElement();
			createNewAccountProducer.click();
		}
		namedInsured.setData(data.get("InsuredName"));
		Assertions.passTest("Home Page", "Insured Name is " + data.get("InsuredName"));
		if (producerNumber.checkIfElementIsPresent()) {
			producerNumber.setData(dataSetup.get("ProducerNumber"));
		}

		String product = data.get("ProductSelection");

		if (productSelection.formatDynamicPath(product).checkIfElementIsPresent()) {
			productArrow.scrollToElement();
			productArrow.click();
			productSelection.formatDynamicPath(product).click();
		}

		if (effectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
				&& effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
			effectiveDate.formatDynamicPath(1).scrollToElement();
			effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(60);
			effectiveDate.formatDynamicPath(1).setData(data.get("PolicyEffDate"));
		} else {
			effectiveDate.formatDynamicPath(2).scrollToElement();
			effectiveDate.formatDynamicPath(2).waitTillVisibilityOfElement(60);
			effectiveDate.formatDynamicPath(2).setData(data.get("PolicyEffDate"));
		}

		/*
		 * TF 09/25/2024 - setting product selection above - i'm not sure how long this
		 * has been commented out, but delete if things work from here on out if
		 * (productSelection.formatDynamicPath(Data.get("ProductSelection")).
		 * checkIfElementIsPresent() &&
		 * productSelection.formatDynamicPath(Data.get("ProductSelection")).
		 * checkIfElementIsDisplayed()) { productArrow.scrollToElement();
		 * productArrow.click();
		 * productSelection.formatDynamicPath(Data.get("ProductSelection")).click();
		 */
		/*
		 * else { productArrow.scrollToElement(); productArrow.click();
		 * producerProductSelection.formatDynamicPath(Data.get("ProductSelection")).
		 * click(); }
		 */

		if (policyHolder_Yes.checkIfElementIsPresent() && policyHolder_Yes.checkIfElementIsDisplayed()) {
			if (data.get("Discount55Years").equalsIgnoreCase("Yes")) {
				policyHolder_Yes.click();
			} else {
				policyHolder_No.click();
			}
		}

		goButton.click();

		if (allowOverride) {
			waitTime(3);
			Assertions.passTest("Home Page", "Checking for override");
			if (overRideCheckBox.checkIfElementIsPresent() && overRideCheckBox.checkIfElementIsDisplayed()) {
				overRideCheckBox.waitTillVisibilityOfElement(60);
				overRideCheckBox.waitTillElementisEnabled(60);
				overRideCheckBox.scrollToElement();
				overRideCheckBox.select();
				goButton.click();
			}
		}

		if (loading.checkIfElementIsPresent()) {
			loading.waitTillInVisibilityOfElement(120);
		}

		if (pageName.getData().contains("Dwelling")) {
			return new DwellingPage();
		} else if (pageName.getData().contains("Eligibility")) {
			return new EligibilityPage();
		}
		return null;
	}

	public ReferralPage searchReferral(String quote) {
		while (!referralquoteLink.formatDynamicPath(quote).checkIfElementIsDisplayed()) {
			referralStatus.click();
		}
		waitTime(5);
		referralquoteLink.formatDynamicPath(quote).click();
		ReferralPage referral = new ReferralPage();
		if (referral.approveOrDeclineRequest.checkIfElementIsPresent() || referral.pickUp.checkIfElementIsPresent()) {
			return new ReferralPage();
		}
		return null;
	}

	public void searchPolicy(String policyNo) {
		findFilterArrow.scrollToElement();
		findFilterArrow.click();
		findFilterPolicyOption.click();
		policyNumber.setData(policyNo);
		findPolicyButton.scrollToElement();
		findPolicyButton.click();
	}

	public void searchQuote(String quoteNo) {
		waitTime(4);
		findFilterArrow.scrollToElement();
		findFilterArrow.click();
		findFilterQuoteOption.click();
		findQuoteNumber.setData(quoteNo);
		findBtnQuote.scrollToElement();
		findBtnQuote.click();
		refreshPage();
	}

	public void searchQuoteByProducer(String quoteNo) {
		waitTime(3);
		producerQuoteSearchButton.scrollToElement();
		producerQuoteSearchButton.click();

		producerQuoteNumberSearchTextbox.scrollToElement();
		producerQuoteNumberSearchTextbox.setData(quoteNo);

		producerQuoteFindButton.scrollToElement();
		producerQuoteFindButton.click();

		producerQuoteNumberLink.formatDynamicPath(quoteNo).waitTillButtonIsClickable(60);
		producerQuoteNumberLink.formatDynamicPath(quoteNo).scrollToElement();
		producerQuoteNumberLink.formatDynamicPath(quoteNo).click();
	}

	public void searchPolicyByProducer(String policyNo) {
		producerPolicySearchButton.scrollToElement();
		producerPolicySearchButton.click();

		producerPolicyNumberSearchTextbox.scrollToElement();
		producerPolicyNumberSearchTextbox.setData(policyNo);

		producerPolicyFindButton.waitTillPresenceOfElement(60);
		producerPolicyFindButton.waitTillVisibilityOfElement(60);
		producerPolicyFindButton.scrollToElement();
		producerPolicyFindButton.click();

		producerPolicyNumberLink.formatDynamicPath(policyNo).waitTillPresenceOfElement(60);
		producerPolicyNumberLink.formatDynamicPath(policyNo).waitTillVisibilityOfElement(60);
		producerPolicyNumberLink.formatDynamicPath(policyNo).scrollToElement();
		producerPolicyNumberLink.formatDynamicPath(policyNo).click();
	}

	public void refreshUntilQuoteFound(String quote) {
		while (!referralquoteLink.formatDynamicPath(quote).checkIfElementIsPresent()) {
			refreshPage();
			waitTime(3);
		}
		referralquoteLink.formatDynamicPath(quote).waitTillVisibilityOfElement(60);
		referralquoteLink.formatDynamicPath(quote).click();
		referralquoteLink.formatDynamicPath(quote).waitTillInVisibilityOfElement(60);
	}

	public void refreshUntilRenewalQuoteFound(String quote) {
		while (!referralquoteLink.formatDynamicPath(quote).checkIfElementIsPresent()) {
			refreshPage();
			waitTime(3);
		}
		Assertions.passTest("Home page", "Referred quote is "
				+ referralquoteLink.formatDynamicPath("Renewal Prem Adj").getData() + " Searched successfully");
		referralquoteLink.formatDynamicPath(quote).waitTillVisibilityOfElement(60);
		referralquoteLink.formatDynamicPath(quote).click();
		referralquoteLink.formatDynamicPath(quote).waitTillInVisibilityOfElement(60);
	}

	public void searchPolicyByselectingStatus(Map<String, String> Data) {
		policyStatusArrow.scrollToElement();
		policyStatusArrow.click();
		policyStatusOption.formatDynamicPath(Data.get("PolicyStatus")).waitTillVisibilityOfElement(60);
		policyStatusOption.formatDynamicPath(Data.get("PolicyStatus")).scrollToElement();
		policyStatusOption.formatDynamicPath(Data.get("PolicyStatus")).click();
		Assertions.passTest("Home Page", "The Policy Status Selected is " + Data.get("PolicyStatus"));
		findBtnAccount.scrollToElement();
		findBtnAccount.click();
	}

	public void SovFileUpload(String fileName, Map<String, String> Data) {
		if (fileName == null || fileName.equals("")) {
			Assertions.failTest("Upload SOV", "Filname is blank");
		}
		String uploadFileDir = null;
		uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.sov.uploadpath");

		switchToChildWindow();
		chooseSOV.setData(new File(uploadFileDir + fileName).getAbsolutePath());

		upload.waitTillVisibilityOfElement(60);
		upload.scrollToElement();
		upload.click();
		switchToMainWindow();
		if (confirmEffectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
				&& confirmEffectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
			confirmEffectiveDate.formatDynamicPath(1).scrollToElement();
			confirmEffectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			confirmEffectiveDate.formatDynamicPath(1).clearData();
			confirmEffectiveDate.formatDynamicPath(1).setData(Data.get("PolicyEffDate"));
			continueButton.scrollToElement();
			continueButton.click();
			waitTime(3);
		}
	}

	public void teamReferralFilter(String filterValue) {
		teamReferralFilter.waitTillPresenceOfElement(60);
		teamReferralFilter.waitTillVisibilityOfElement(60);
		teamReferralFilter.scrollToElement();
		teamReferralFilter.click();
		teamReferralFilterOption.formatDynamicPath(filterValue).waitTillVisibilityOfElement(60);
		teamReferralFilterOption.formatDynamicPath(filterValue).scrollToElement();
		teamReferralFilterOption.formatDynamicPath(filterValue).click();
	}

	public void enterPersonalLoginDetails() {
		waitTime(2);
		if (personalLogionPopup.checkIfElementIsPresent() && personalLogionPopup.checkIfElementIsDisplayed()) {
			personalLogionPopup.waitTillPresenceOfElement(60);
			personalLogionPopup.waitTillVisibilityOfElement(60);
			notYetButton.scrollToElement();
			notYetButton.click();
			personalLogionPopup.waitTillInVisibilityOfElement(60);
		}
	}

	public ReferralPage searchReferralByPolicy(String policyNumber) {

		if (teamReferralQ3.formatDynamicPath(policyNumber).checkIfElementIsPresent()
				&& teamReferralQ3.formatDynamicPath(policyNumber).checkIfElementIsDisplayed()) {
			waitTime(2); // to click on referral quote in team referrals
			teamReferralQ3.formatDynamicPath(policyNumber).click();
		} else {
			while (!userReferralQ3.formatDynamicPath(policyNumber).checkIfElementIsDisplayed()) {
				referralStatus.click();
			}
			userReferralQ3.formatDynamicPath(policyNumber).click();
		}
		ReferralPage referral = new ReferralPage();
		if (referral.pickUp.checkIfElementIsPresent() && referral.pickUp.checkIfElementIsDisplayed()
				|| referral.approveOrDeclineRequest.checkIfElementIsDisplayed()) {
			return new ReferralPage();
		}
		return null;
	}

}
