/** Program Description: Validating dwelling page elements for NB and renewal as producer.
 *  Author			   : John
 *  Date of Creation   : 09/11/2019
 **/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC043 extends AbstractNAHOTest {

	public PNBTC043() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID43.xls";
	}
	int data_Value1 = 0;
	int locNo = 1;
	int bldgNo = 1;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		DwellingPage dwellingPage = new DwellingPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		Map<String, String> testData;
		String quoteNumber;

		// Create New Account
		testData = data.get(data_Value1);
		Assertions.passTest("Home Page", "Logged in as Producer successfully");

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "New Account created successfully");

		// Assertion of Dwelling page elements
		dwellingPage.enterAndAssertDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Dwelling Page elements validated for NB successfully");

		// Create Quote
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();

		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(60);

		// Entering Create quote page Details
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.globalErr.checkIfElementIsPresent()
				&& createQuotePage.globalErr.checkIfElementIsDisplayed()) {
			createQuotePage.covA_NHinputBox.scrollToElement();
			createQuotePage.covA_NHinputBox.clearData();
			createQuotePage.covA_NHinputBox.setData("4000000");
			createQuotePage.covA_NHinputBox.tab();
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.waitTillButtonIsClickable(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			createQuotePage.getAQuote.waitTillInVisibilityOfElement(60);
		}
		Assertions.passTest("Create Quote Page", "Clicked on get a quote button");
		if (createQuotePage.pageName.getData().contains("Create Quote")) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			createQuotePage.continueButton.waitTillInVisibilityOfElement(60);
		}

		// Enter Referral contact Details
		if (referQuotePage.contactName.checkIfElementIsPresent()
				&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
			referQuotePage.contactName.setData((testData.get("ProducerName")));
			referQuotePage.contactEmail.setData((testData.get("ProducerEmail")));
			referQuotePage.referQuote.click();
			Assertions.passTest("Quote referral Page", "Reference details entered");
			String refQuote = referQuotePage.quoteForRef.getData();
			Assertions.passTest("Quote referral Page", refQuote);

			// Sign out from Producer Login
			homePage.scrollToTopPage();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Navigated to Homepage");
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Clicked on Signout");

			// Login as USM for approving the referral
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "USM name and password entered");
			Assertions.passTest("Home Page", "HomePage Loaded");

			// Find the quote for approving referral
			homePage.searchReferral(refQuote);
			Assertions.passTest("Referral Page", "Quote for Referral to be Approved found");
			requestBindPage = referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote for Referral Approved");
			approveDeclineQuotePage.clickOnApprove();

			// Find the approved quote for binding
			homePage.scrollToTopPage();
			homePage.goToHomepage.click();
			homePage.findFilterArrow.checkIfElementIsPresent();
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterQuoteOption.scrollToElement();
			homePage.findFilterQuoteOption.click();
			Assertions.passTest("Home Page", "Selected Quote Search Type selected");
			homePage.findQuoteNumber.scrollToElement();
			homePage.findQuoteNumber.setData(refQuote);
			homePage.findBtnQuote.click();

			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on Request Bind
			Assertions.passTest("Account Overview Page", "Account Overview page loaded");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Request Bind Page", "Request Bind Page loaded");

		} else {
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on Request Bind
			Assertions.passTest("Account Overview Page", "Account Overview page loaded");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Request Bind Page", "Request Bind Page loaded");

			// Sign out from Producer Login
			homePage.scrollToTopPage();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Navigated to Homepage");
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Clicked on Signout");

			// Login as USM for approving the referral
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "USM name and password entered");
			Assertions.passTest("Home Page", "HomePage Loaded");

			// Find the approved quote for binding
			homePage.scrollToTopPage();
			homePage.goToHomepage.click();
			homePage.findFilterArrow.checkIfElementIsPresent();
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterQuoteOption.scrollToElement();
			homePage.findFilterQuoteOption.click();
			Assertions.passTest("Home Page", "Selected Quote Search Type selected");
			homePage.findQuoteNumber.scrollToElement();
			homePage.findQuoteNumber.setData(quoteNumber);
			homePage.findBtnQuote.click();

			// click on request bind
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		}

		// Entering Request Bind Page Details
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		String policyNumber = policySummarypage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);
		Assertions.verify(policyNumber.startsWith("52-358"), true, "Policy Summary Page",
				"Policy Number format is verified", false, false);

		// Renew Policy
		policySummarypage.renewPolicy.click();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		Assertions.passTest("Account Overview Page", "Renewal released to producer successfully");

		// Retrieve renewal quote number
		accountOverviewPage.quoteNoHolder.scrollToElement();
		int quote = accountOverviewPage.quoteNoHolder.getData().length();
		String renewalQuoteNum = accountOverviewPage.quoteNoHolder.getData().substring(1, quote - 1);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Home Page", "Logged out as USM successfully");

		// login as producer
		loginPage.waitTime(2);
		loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		homePage.scrollToBottomPage();
		homePage.quoteSearchTab.scrollToElement();
		homePage.quoteSearchTab.click();
		homePage.quoteSearchTextbox.scrollToElement();
		homePage.quoteSearchTextbox.setData(renewalQuoteNum);
		homePage.prodFindBtn.scrollToElement();
		homePage.prodFindBtn.click();
		homePage.scrollToBottomPage();
		homePage.waitTime(3);
		homePage.quoteToSelect.formatDynamicPath(renewalQuoteNum).click();

		// Assert dwelling details for renewal quote of producer
		accountOverviewPage.editDwelling11.waitTillVisibilityOfElement(60);
		accountOverviewPage.editDwelling11.scrollToElement();
		accountOverviewPage.editDwelling11.click();
		accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();
		if (accountOverviewPage.yesButton.checkIfElementIsPresent()
				&& accountOverviewPage.yesButton.checkIfElementIsDisplayed()) {
			accountOverviewPage.yesButton.scrollToElement();
			accountOverviewPage.yesButton.click();
		}
		if (accountOverviewPage.quoteExpiredPopupMsg.checkIfElementIsPresent()
				&& accountOverviewPage.quoteExpiredPopupMsg.checkIfElementIsDisplayed()) {
			accountOverviewPage.quoteExpiredPopupMsg.scrollToElement();
			accountOverviewPage.quoteExpiredPopupMsg.click();
			accountOverviewPage.quoteExpiredPopupMsg.waitTillInVisibilityOfElement(60);
		}

		testData = data.get(data_Value1);
		dwellingPage.enterAndAssertDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Validated elements for renewal quote as producer");

		// Closing the browser
		homePage.scrollToTopPage();
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.signOutButton.click();
		Assertions.passTest("PNB_Regression_TC043", "Executed Successfully");

	}
}