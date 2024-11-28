/** Program Description: To check whether the Endorsement is referred if the Endorsement is out of sequence
 *  Author			   : Yeshashwini T.A
 *  Date of Creation   : 07/09/2018
**/

package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EndorseAdditionalInterestsPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC006 extends AbstractNAHOTest {

	public PNBTC006() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID06.xls";
	}

	BasePageControl page;
	LoginPage login;
	HomePage homePage;
	LocationPage locationPage;
	DwellingPage dwellingPage;
	CreateQuotePage createQuotePage;
	AccountOverviewPage accountOverviewPage;
	RequestBindPage requestBindPage;
	ConfirmBindRequestPage confirmBindRequestPage;
	PolicySummaryPage policySummaryPage;
	String policyNumber;
	String quoteNumber;
	EndorsePolicyPage endorsePolicyPage;
	EndorseAdditionalInterestsPage endorseAdditionalInterestsPage;
	ReferQuotePage referQuotePage;
	ReferralPage referralPage;
	ApproveDeclineQuotePage approveDeclineQuotePage;
	BasePageControl basePage = new BasePageControl();
	Map<String, String> testData;
	static final String EXT_RPT_MSG = " is verified";
	static int dataValue1 = 0;
	static int dataValue2 = 1;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		login = new LoginPage();
		dwellingPage = new DwellingPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		endorsePolicyPage = new EndorsePolicyPage();
		endorseAdditionalInterestsPage = new EndorseAdditionalInterestsPage();
		referQuotePage = new ReferQuotePage();
		referralPage = new ReferralPage();
		approveDeclineQuotePage = new ApproveDeclineQuotePage();
		testData = data.get(dataValue1);
		page = new BasePageControl();

		// Create New Account
		Assertions.passTest("Home Page", "Page Navigated");
		homePage = new HomePage();
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "Values Entered");
		dwellingPage = (DwellingPage) basePage;

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", "Page Navigated");
		dwellingPage = new DwellingPage();
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + "Values Entered");

		// Entering Quote Details
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = createQuotePage.enterQuoteDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Clicking on Bind button in account Overview Page
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Account Overview Page", "Quote generated successfully");

		// Entering Bind Details
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		page = requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Getting NB Policy Number
		policySummaryPage = (PolicySummaryPage) page;
		Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "NB Policy Number is : " + policyNumber);

		// click on Endorse PB link
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		Assertions.passTest("Endorse Policy Page", "Endorse Policy Page loaded successfully");
		endorsePolicyPage.endorsementEffDate.scrollToElement();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		Assertions.passTest("Endorse Policy Page", "Details entered successfully");
		endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
		endorsePolicyPage.editLocOrBldgInformationLink.click();
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");
		dwellingPage.dwelling1.scrollToElement();
		dwellingPage.dwelling1.click();
		dwellingPage.dwellingCharacteristicsLink.scrollToElement();
		dwellingPage.dwellingCharacteristicsLink.click();
		testData = data.get(dataValue2);
		dwellingPage.livingSquareFootage.scrollToElement();
		dwellingPage.livingSquareFootage.setData(testData.get("L1D1-DwellingSqFoot"));
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		if (dwellingPage.pageName.getData().contains("Dwelling Under")) {
			createQuotePage.override.scrollToElement();
			createQuotePage.override.click();
			createQuotePage.override.waitTillInVisibilityOfElement(60);
		}
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();
		createQuotePage.continueEndorsementButton.scrollToElement();
		createQuotePage.continueEndorsementButton.click();
		createQuotePage.override.scrollToElement();
		createQuotePage.override.click();
		endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
		endorsePolicyPage.changeCoverageOptionsLink.click();
		createQuotePage.enterInsuredValues(testData);
		createQuotePage.continueEndorsementButton.waitTillButtonIsClickable(60);
		createQuotePage.continueEndorsementButton.waitTillVisibilityOfElement(60);
		createQuotePage.continueEndorsementButton.scrollToElement();
		createQuotePage.continueEndorsementButton.click();
		if (createQuotePage.pageName.getData().contains("Dwelling values")) {
			createQuotePage.override.scrollToElement();
			createQuotePage.override.click();
			createQuotePage.override.waitTillInVisibilityOfElement(60);
		}
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();
		endorsePolicyPage.scrollToBottomPage();
		endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
		endorsePolicyPage.closeButton.scrollToElement();
		endorsePolicyPage.closeButton.click();
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Endorse Policy Page", "Signed out as USM Successfully");

		// Login to producer account
		login.refreshPage();
		login.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as Producer Successfully");

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		// find the account by entering quote number in producer home page
		homePage.producerPolicySearchButton.scrollToElement();
		homePage.producerPolicySearchButton.click();
		homePage.producerPolicyNumberSearchTextbox.scrollToElement();
		homePage.producerPolicyNumberSearchTextbox.setData(policyNumber);
		homePage.findPolicyButtonProducer.scrollToElement();
		homePage.findPolicyButtonProducer.click();
		homePage.producerPolicyNumberLink.formatDynamicPath(policyNumber).waitTillButtonIsClickable(60);
		homePage.producerPolicyNumberLink.formatDynamicPath(policyNumber).waitTillVisibilityOfElement(60);
		homePage.producerPolicyNumberLink.formatDynamicPath(policyNumber).scrollToElement();
		homePage.producerPolicyNumberLink.formatDynamicPath(policyNumber).click();
		policySummaryPage.producerEndorsePolicyLink.scrollToElement();
		policySummaryPage.producerEndorsePolicyLink.click();
		Assertions.passTest("Endorse Policy Page", "Endorse Policy Page loaded successfully");
		endorsePolicyPage.endorsementEffDate.scrollToElement();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		Assertions.passTest("Endorse Policy Page", "Details entered successfully");
		if (endorsePolicyPage.pageName.getData().contains("Out Of Sequence")) {
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
		}
		endorsePolicyPage.changeAIInformationLink.scrollToElement();
		endorsePolicyPage.changeAIInformationLink.click();
		endorseAdditionalInterestsPage.enterEndorsementAdditionalInterestDetails(testData);
		endorsePolicyPage.submitButton.scrollToElement();
		endorsePolicyPage.submitButton.click();
		Assertions.verify(referQuotePage.referralMsg.checkIfElementIsDisplayed(), true, "Refer Quote Page",
				"Out of Sequence transaction going for referral to USM" + EXT_RPT_MSG, false, false);
		testData = data.get(dataValue1);
		referQuotePage.contactName.scrollToElement();
		referQuotePage.contactName.setData(testData.get("ProducerName"));
		referQuotePage.contactEmail.scrollToElement();
		referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
		referQuotePage.referQuote.scrollToElement();
		referQuotePage.referQuote.click();

		// logout as producer
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Signed Out as Producer Successfully");

		// login as USM
		login.waitTime(2);
		login.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as USM Successfully");
		homePage.searchReferralByPolicy(policyNumber);
		Assertions.passTest("Home Page", "Referred quote number appearing in USMs work queue " + EXT_RPT_MSG);
		referralPage.clickOnApprove();
		approveDeclineQuotePage.clickOnApprove();
		Assertions.passTest("Approve/Decline Quote Page", "Quote is successfully approved");
		if (referQuotePage.pageName.getData().contains("Referral Complete")) {
			referQuotePage.referralClose.scrollToElement();
			referQuotePage.referralClose.click();
		}
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();

		// Find the policy by entering policy Number
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Created Policy is selected");
		Assertions.verify(
				policySummaryPage.endorsementRecordQ3.formatDynamicPath("Reversal - Endorsement")
						.checkIfElementIsDisplayed(),
				true, "Policy Summary Page", "First Endorsement reversed" + EXT_RPT_MSG, false, false);

		Assertions.passTest("PNB_Regression_TC006", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}