/** Program Description: Renewal referral and binding
 *  Author			   : SM Netserv
 *  Date of Creation   : Oct 2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC036 extends AbstractNAHOTest {

	public PNBTC036() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID36.xls";
	}

	public HomePage homePage;
	public DwellingPage dwellingPage;
	public CreateQuotePage createQuotePage;
	public LocationPage locationPage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public PolicySummaryPage policySummaryPage;
	public LoginPage loginPage;
	public BindRequestSubmittedPage bindRequestPage;
	public ReferralPage referralPage;
	public BasePageControl basePage;
	public Map<String, String> testData;
	String quoteNumber;
	String policyNumber;
	static final String ext_rpt_msg = " is verified";
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_UPDATED = "Values Updated";
	static final String VALUES_VERIFIED = "Values Verified";
	static final String VALUES_ENTERED = "Values Entered successfully";
	static int data_Value1 = 0;
	static int data_Value2 = 1;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		locationPage = new LocationPage();
		dwellingPage = new DwellingPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		loginPage = new LoginPage();
		bindRequestPage = new BindRequestSubmittedPage();
		referralPage = new ReferralPage();
		testData = data.get(data_Value1);

		// NB Test case 01 is used to generate the policy
		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);
		dwellingPage = (DwellingPage) basePage;

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetails(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage = new DwellingPage();
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.override.scrollToElement();
		createQuotePage.override.click();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policySummaryPage.getPolicynumber());

		// Find the policy by entering policy Number
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.policyNumber.tab();
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();

		// Click on Renew Policy Hyperlink
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		if (policySummaryPage.pageName.getData().contains("Transfer")) {
			policySummaryPage.transferContinue.scrollToElement();
			policySummaryPage.transferContinue.click();
		}
		accountOverviewPage.quoteNo1Holder.scrollToElement();
		quoteNumber = accountOverviewPage.quoteNo1Holder.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Renewal Quote Number : " + quoteNumber);
		Assertions.verify(accountOverviewPage.quoteNo1Holder.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Renewal Quote Number generated " + ext_rpt_msg, false, false);

		// Create Another quote with EQ deductible
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);
		accountOverviewPage.floodLink.scrollToElement();
		accountOverviewPage.floodLink.click();
		accountOverviewPage.editFloodLink.scrollToElement();
		accountOverviewPage.editFloodLink.click();
		if (accountOverviewPage.yesButton.checkIfElementIsPresent()) {
			accountOverviewPage.yesButton.scrollToElement();
			accountOverviewPage.yesButton.click();
		}
		if (accountOverviewPage.quoteExpiredPopupMsg.checkIfElementIsPresent()) {
			accountOverviewPage.quoteExpiredPopupMsg.scrollToElement();
			accountOverviewPage.quoteExpiredPopupMsg.click();
			accountOverviewPage.quoteExpiredPopupMsg.waitTillInVisibilityOfElement(60);
		}

		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);
		testData = data.get(data_Value2);
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", VALUES_ENTERED);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.override.checkIfElementIsPresent()
				&& createQuotePage.override.checkIfElementIsDisplayed()) {
			createQuotePage.override.scrollToElement();
			createQuotePage.override.click();
		}
		createQuotePage.continueButton.scrollToElement();
		createQuotePage.continueButton.click();
		Assertions.verify(accountOverviewPage.floodDeductible.checkIfElementIsDisplayed(), true,
				"Account Overview Page",
				"Flood Deductible added on Renewal Requote: " + testData.get("FloodDeductible") + ext_rpt_msg, false,
				false);
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		Assertions.passTest("Account Overview Page", "Successfully able to release renewal to Producer");
		accountOverviewPage.issueQuoteButton.scrollToElement();
		accountOverviewPage.issueQuoteButton.click();
		Assertions.passTest("Account Overview Page", "Values Verified");
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();

		// Entering data in Request Bind Page
		testData = data.get(data_Value1);
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", "Entered Values successfully");
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.requestBind.waitTillVisibilityOfElement(60);
		requestBindPage.requestBind.scrollToElement();
		requestBindPage.requestBind.click();
		requestBindPage.requestBind.waitTillInVisibilityOfElement(60);

		// Override Effective date in Request Bind Page
		if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
				&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
			requestBindPage.overrideEffectiveDate.scrollToElement();
			requestBindPage.overrideEffectiveDate.select();
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			requestBindPage.requestBind.scrollToElement();
			requestBindPage.requestBind.click();
			requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
		}
		Assertions.passTest("Bind Request Page", "Bind Request page loaded successfully");
		bindRequestPage.homePage.click();
		Assertions.passTest("Home Page", "Clicked on Quote Number");
		homePage.goToHomepage.click();
		homePage.searchReferral(quoteNumber);
		Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");
		Assertions.passTest("Referral Page", "Referral page loaded successfully");
		referralPage.clickOnApprove();
		Assertions.passTest("Referral Page", "Referral request approved successfully");
		requestBindPage.approveRequest();
		Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
		Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");

		// Getting Renewal Policy Number
		Assertions.passTest("Policy Summary Page", "Renewal Policy Number :" + policySummaryPage.getPolicynumber());
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC036", "Executed Successfully");

	}
}