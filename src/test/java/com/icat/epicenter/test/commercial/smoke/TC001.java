/** Program Description:  Create a Wholesale Quote for EQ zipcode with single location and single building by entering all the details in building page
 *                       create quote page,Priorloss page and request bind page and decline the bind referral
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 04/05/2023
 **/

package com.icat.epicenter.test.commercial.smoke;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;
import com.icat.epicenter.utils.TestConstants;

public class TC001 extends AbstractCommercialTest {

	public TC001() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/smoke/NBTCID01.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setupData) {

		// Initializing the pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		Map<String, String> testData;

		// Initializing variables
		String quoteNumber;
		testData = data.get(TestConstants.DATA_COL_1);

		// New Account creation
		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home Page loaded successfully ", false, false);
		homePage.createNewAccountWithNamedInsured(testData, setupData);
		Assertions.passTest("New Account", "New Account created successfully");

		// ZipCode EligibilityPage
		Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
				"Zipcode Eligibility Page loaded successfully", false, false);
		eligibilityPage.processSingleZip(testData);
		Assertions.passTest("Eligibility Page", "ZipCode entered successfully");

		// Entering Location Details
		Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
				"Location Page loaded successfully", false, false);
		locationPage.enterLocationDetails(testData);
		Assertions.passTest("Location Page", "Location Details Entered successfully");

		// Entering Location 1 Building 1 Details
		//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page","Building Page loaded successfully", false, false);
		buildingPage.enterBuildingDetails(testData);

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		createQuotePage.enterQuoteDetailsCommercialSmoke(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// check that request bind button is available
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page loaded successfully", false, false);

		// getting the quote number
		quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		// Assert the presence of Account overview elements
		Assertions.addInfo("Account Overview Page", "Verifying the presence of Account Overview features");
		Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "View/Print Full Quote link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.emailQuoteLink.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Email Quote link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.requestESignatureLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Request E Signature link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Override Premium link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.pushToRMSLink.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Push to RMS link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Create Another Quote button displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Edit deductibles and limits button displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Delete Account Button displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.producerLink.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Producer Edit link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editInsuredContactInfo.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Edit Insured Contact link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editInspectionContact.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Edit Inspection Contact link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editAdditionalIntersets.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Edit Additional Interests link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Delete Account Button displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editFees.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Edit Fees icon displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.deleteQuote.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Delete Quote icon displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.quoteSpecifics.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote Specifics link displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.quotePremium.formatDynamicPath("1").checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Quote Premium displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("1").checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Quote Status displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editLocation.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Edit Location link displayed is verified", false, false);

		// Click on Request Bind
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

		// entering details in request bind page
		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		requestBindPage.enterBindDetails(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// clicking on home button
		Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
				"Bind Request Page loaded successfully", false, false);
		bindRequestPage.clickOnHomepagebutton();
		Assertions.passTest("Home Page", "Clicked on Home button");

		// searching the quote number in grid and clicking on the quote link
		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home page loaded successfully", false, false);
		homePage.searchQuote(quoteNumber);
		Assertions.passTest("Home Page", "Quote for referral is searched successfully");

		// Click on Open Referral link
		accountOverviewPage.openReferral.scrollToElement();
		accountOverviewPage.openReferral.click();
		Assertions.passTest("Account Overview Page", "Clicked on open referral link");

		// Approve Referral
		Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
				"Referral page loaded successfully", false, false);
		referralPage.approveOrDeclineRequest.scrollToElement();
		referralPage.approveOrDeclineRequest.click();
		requestBindPage.approve.scrollToElement();
		requestBindPage.approve.click();
		Assertions.passTest("Request Bind Page", "Clicked on approve button successfully");

		// Check the Policy Summary Page
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				"Policy Summary Page loaded successfully", false, false);
		Assertions.passTest("Policy Summary Page", "Referral request approved successfully");

		// Make sure docs are generating, which means Aftershock transfer worked as well
		try {
			policySummaryPage.decLink.waitTillPresenceOfElement(TIME_OUT_SIXTY_SECS);
			policySummaryPage.decLink.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);

			Assertions.verify(policySummaryPage.decLink.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"DEC page link loaded successfully after one minute, so Docs and Aftershock transfer are working", false, false);

		}
		catch(Throwable t) {
			policySummaryPage.decLink.waitTillPresenceOfElement(TIME_OUT_SIXTY_SECS);
			policySummaryPage.decLink.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			Assertions.verify(policySummaryPage.decLink.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"DEC page link loaded successfully after two minutes, so Docs and Aftershock transfer are working", false, false);
		}

		// Signing out
		Assertions.passTest("Commercial Smoke Test Case 01 - Wholesale EQ", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
	}
}