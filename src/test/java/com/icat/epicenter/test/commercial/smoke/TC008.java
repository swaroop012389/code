/** Program Description: Create a Retail wind Quote with multi location and add one building for each location by entering all the details in building page
 *                       create quote page,Priorloss page and request bind page and decline the bind referral
 *  Author			   : Sowndarya
 *  Date of Creation   : 04/05/2023
 **/

package com.icat.epicenter.test.commercial.smoke;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;
import com.icat.epicenter.utils.TestConstants;

public class TC008 extends AbstractCommercialTest {

	public TC008() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/smoke/NBTCID08.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setupData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();

		// Initializing variables
		String quoteNumber;
		Map<String, String> testData = data.get(TestConstants.DATA_COL_1);

		// creating New account
		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home page loaded successfully", false, false);
		homePage.createNewAccountWithNamedInsured(testData, setupData);
		Assertions.passTest("New Account", "New Account created successfully");

		// Entering zipcode in Eligibility page
		Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
				"Eligibility Page loaded successfully", false, false);
		eligibilityPage.processSingleZip(testData);
		Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

		// Entering Location Details
		Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
				"Location Page loaded successfully", false, false);
		locationPage.enterLocationDetails(testData);

		// Enter Building Details
		//TF 10/28/24 - the Create Quote button isn't ready until buildings are added, can't use this assertion
		//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page", "Building Page loaded successfully", false, false);
		buildingPage.enterBuildingDetails(testData);

		// Selecting a peril
		Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
				"Select Peril Page loaded successfully", false, false);
		selectPerilPage.selectPeril(testData.get("Peril"));

		// Entering Prior Losses
		if (priorLossesPage.continueButton.checkIfElementIsPresent()
				&& priorLossesPage.continueButton.checkIfElementIsDisplayed()) {
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
		}

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		createQuotePage.enterQuoteDetailsCommercialSmoke(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// getting the quote number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page loaded successfully", false, false);
		quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
		Assertions.passTest("Account Overview Page", "Quote Number 1:  " + quoteNumber);

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
		Assertions.verify(accountOverviewPage.quoteSomeBuildingsButton.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Create Another Quote button displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Edit deductibles and limits button displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Delete Account Button displayed is verified", false, false);
		Assertions.verify(accountOverviewPage.priorLossEditLink.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Edit Prior Loss link displayed is verified", false, false);
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

		// Changing the building Details
		accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
		accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();

		// Click on edit building
		accountOverviewPage.editBuilding.scrollToElement();
		accountOverviewPage.editBuilding.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit Building");
		testData = data.get(TestConstants.DATA_COL_2);
		Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
				"Building Page Loaded successfully", false, false);

		// handling expired quote popup
		buildingPage.numOfStories.waitTillVisibilityOfElement(60);
		buildingPage.numOfStories.scrollToElement();
		buildingPage.numOfStories.clearData();
		if (buildingPage.expiredQuotePopUp.checkIfElementIsPresent()
				&& buildingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
			buildingPage.expiredQuotePopUp.waitTillPresenceOfElement(60);
			buildingPage.expiredQuotePopUp.waitTillVisibilityOfElement(60);
			buildingPage.continueWithUpdateBtn.waitTillPresenceOfElement(60);
			buildingPage.continueWithUpdateBtn.waitTillVisibilityOfElement(60);
			buildingPage.continueWithUpdateBtn.click();
		}
		buildingPage.numOfStories.tab();
		if (buildingPage.expiredQuotePopUp.checkIfElementIsPresent()
				&& buildingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
			buildingPage.expiredQuotePopUp.waitTillPresenceOfElement(60);
			buildingPage.expiredQuotePopUp.waitTillVisibilityOfElement(60);
			buildingPage.continueWithUpdateBtn.waitTillPresenceOfElement(60);
			buildingPage.continueWithUpdateBtn.waitTillVisibilityOfElement(60);
			buildingPage.continueWithUpdateBtn.click();
		}
		buildingPage.numOfStories.setData(testData.get("L1B1-BldgStories"));
		if (buildingPage.expiredQuotePopUp.checkIfElementIsPresent()
				&& buildingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
			buildingPage.expiredQuotePopUp.waitTillPresenceOfElement(60);
			buildingPage.expiredQuotePopUp.waitTillVisibilityOfElement(60);
			buildingPage.continueWithUpdateBtn.waitTillPresenceOfElement(60);
			buildingPage.continueWithUpdateBtn.waitTillVisibilityOfElement(60);
			buildingPage.continueWithUpdateBtn.click();
		}
		buildingPage.totalSquareFootage.waitTillVisibilityOfElement(60);
		buildingPage.totalSquareFootage.scrollToElement();
		buildingPage.totalSquareFootage.setData(testData.get("L1B1-BldgSqFeet"));
		Assertions.passTest("Building Page", "Building Details entered successfully");
		buildingPage.scrollToBottomPage();
		buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
		buildingPage.reviewBuilding.scrollToElement();
		buildingPage.reviewBuilding.click();
		if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
			buildingPage.override.scrollToElement();
			buildingPage.override.click();
		}

		// Click on Create quote
		buildingPage.createQuote.scrollToElement();
		buildingPage.createQuote.click();
		if (buildingPage.pageName.getData().contains("Buildings No")) {
			buildingPage.override.scrollToElement();
			buildingPage.override.click();
		}

		// selecting peril
		if (!testData.get("Peril").equals("")) {
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
		}

		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		testData = data.get(TestConstants.DATA_COL_1);
		createQuotePage.enterQuoteDetailsCommercialSmoke(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// getting the quote number 2
		quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(3).getData().substring(1, 11);
		Assertions.passTest("Account Overview Page", "Quote Number 2 :  " + quoteNumber);

		// Click on request bind page and entering details in request bind page
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		requestBindPage.enterBindDetails(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Go to HomePage
		homePage.goToHomepage.click();
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

		// Signing out
		Assertions.passTest("Commercial Smoke Test Case 08 - Retail Wind", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
	}
}