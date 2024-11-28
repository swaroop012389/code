/** Program Description: Renewal referrals
 *  Author			   : SM Netserv
 *  Date of Creation   : Sep 2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;
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

public class PNBTC028 extends AbstractNAHOTest {

	public PNBTC028() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID28.xls";
	}

	SheetMatchedAccessManager testDataSheet;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		DwellingPage dwellingPage = new DwellingPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferralPage referralPage = new ReferralPage();
		LoginPage loginPage = new LoginPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approve_DeclineQuotePage = new ApproveDeclineQuotePage();
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testDataValue1 = data.get(data_Value2);
		String quoteNumber;

		// Login as Producer
		Assertions.passTest("Home Page", "Logged in as Producer Successfully");

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		homePage.createNewAcct.click();
		homePage.namedInsured.setData(testData.get("InsuredName"));
		Assertions.passTest("Home Page", "Insured Name is " + testData.get("InsuredName"));
		if (homePage.producerNumber.checkIfElementIsPresent()) {
			homePage.producerNumber.setData("11250.1");
		}
		if (homePage.productArrow.checkIfElementIsPresent()) {
			homePage.productArrow.scrollToElement();
			homePage.productArrow.click();
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
		}
		if (!testData.get("ProductSelection").equalsIgnoreCase("Commercial")) {
			if (homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(1).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(30);
				homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			} else if (homePage.effectiveDate.formatDynamicPath(2).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(2).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(2).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(2).waitTillVisibilityOfElement(30);
				homePage.effectiveDate.formatDynamicPath(2).setData(testData.get("PolicyEffDate"));
			} else {
				homePage.effectiveDateNew.waitTillVisibilityOfElement(60);
				homePage.effectiveDateNew.scrollToElement();
				homePage.effectiveDateNew.setData(testData.get("PolicyEffDate"));
			}
			if (homePage.policyHolder_Yes.checkIfElementIsDisplayed()) {
				if (testData.get("Discount55Years").equalsIgnoreCase("Yes")) {
					homePage.policyHolder_Yes.click();
				} else {
					homePage.policyHolder_No.click();
				}
			}
		}
		homePage.goButton.click();
		homePage.goButton.waitTillInVisibilityOfElement(30);
		Assertions.passTest("Home Page", "New Account created successfully");

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.addRoofDetails(testData, 1, 1);
		dwellingPage.enterDwellingValues(testData, 1, 1);

		// Review Dwelling
		dwellingPage.reviewDwelling.click();
		dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

		// Click on Create Quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(60);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		Assertions.verify(createQuotePage.ordLawOption.checkIfElementIsPresent(), false, "Create Quote Page",
				"Ordinance and Law field is not present" + " is verified", false, false);
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
			approve_DeclineQuotePage.clickOnApprove();

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

			// Getting Quote Number
			quoteNumber = requestBindPage.quoteNumber.getData();
			Assertions.passTest("Request Bind Page", "Quote Number :  " + quoteNumber);

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
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// click on request bind
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		}

		// Entering Request Bind Page Details
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Verifying Insured name and effective date
		String policyNumber = policySummarypage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

		// Initiate the renewal
		policySummarypage.renewPolicy.scrollToElement();
		policySummarypage.renewPolicy.click();
		Assertions.passTest("Renew Policy Page", "Renewal initiated Successfully");

		// Release to Producer
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		accountOverviewPage.releaseRenewalToProducerButton.waitTillInVisibilityOfElement(60);
		accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
		accountOverviewPage.viewPreviousPolicyButton.click();

		// Log out of USM - First time
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Signed Out as USM Successfully");

		// Login as Producer- Second time
		loginPage.refreshPage();
		loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as Producer Successfully");

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		// Code for Policy search in Producer Home page
		homePage.policySearchTab.scrollToElement();
		homePage.policySearchTab.click();
		homePage.policySearchTextbox.scrollToElement();
		homePage.policySearchTextbox.setData(policyNumber);
		homePage.policySearchBtn.scrollToElement();
		homePage.policySearchBtn.click();
		homePage.policyNum.waitTillVisibilityOfElement(60);
		homePage.policyNum.scrollToElement();
		homePage.policyNum.click();
		policySummarypage.viewActiveRenewal.scrollToElement();
		policySummarypage.viewActiveRenewal.click();
		Assertions.passTest("Account Overview page", "Navigated to Account Overview Successfully");

		// Create another quote
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();
		createQuotePage.enterDeductibles(testDataValue1);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Getting Insured Name
		Assertions.passTest("Create Quote page", "Insured Name :  " + quoteNumber);

		// Refer Quote page
		if (referQuotePage.contactName.checkIfElementIsPresent()
				&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
			Assertions.passTest("Refer Quote Page", "Navigated to Refer Quote page successfully");
			referQuotePage.contactName.clearData();
			referQuotePage.contactName.setData(testDataValue1.get("ProducerName"));
			referQuotePage.contactName.tab();
			referQuotePage.contactEmail.clearData();
			referQuotePage.contactEmail.setData(testDataValue1.get("ProducerEmail"));
			referQuotePage.contactEmail.tab();
			referQuotePage.comments.clearData();
			referQuotePage.comments.setData("Cov A is greater than $5 Million");
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();

			// code for Quote referred click on Home page button
			String QuoteNumber2 = referQuotePage.quoteNum.getData();

			// Log out of Producer- Second time
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Signed Out as Producer Successfully");

			// Login as USM - Second time
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Search Referral and approve
			referralPage = homePage.searchReferral(QuoteNumber2);
			Assertions.passTest("Home Page", "Searched referred quote successfullly");
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			approve_DeclineQuotePage.internalUnderwriterComments.setData("Approved Referral");
			approve_DeclineQuotePage.externalUnderwriterComments.setData("Approved Referral");
			approve_DeclineQuotePage.approveButton.scrollToElement();
			approve_DeclineQuotePage.approveButton.click();
			referralPage.close.scrollToElement();
			referralPage.close.click();
			Assertions.passTest("Approval/Decline Page", "Referral Quote approved successfully");
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
		} else {
			// Log out of Producer- Second time
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Signed Out as Producer Successfully");

			// Login as USM - Second time
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");
		}

		// search for Approved quote
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.waitTillVisibilityOfElement(60);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Referred Quote is selected");
		policySummarypage.viewActiveRenewal.scrollToElement();
		policySummarypage.viewActiveRenewal.click();
		accountOverviewPage.issueQuoteButton.scrollToElement();
		accountOverviewPage.issueQuoteButton.click();
		accountOverviewPage.issueQuoteButton.waitTillInVisibilityOfElement(60);
		Assertions.verify(accountOverviewPage.issueMessage.checkIfElementIsDisplayed(), true, "Account Overview Page",
				accountOverviewPage.issueMessage.getData() + " is verified", false, false);
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();

		// Entering data in Request Bind Page
		Assertions.passTest("Request Bind Page", "Page Navigated");
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", "Values Entered Successfully");
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

		// Getting Policy Number
		Assertions.passTest("Policy Summary Page", "Page Navigated");
		Assertions.passTest("Policy Summary Page", "Policy Numer is: " + policySummarypage.getPolicynumber());
		Assertions.passTest("PNB_Regression_TC028", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}