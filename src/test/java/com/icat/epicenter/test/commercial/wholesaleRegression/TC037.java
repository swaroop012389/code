/** Program Description: Create 2 EQ quotes: One with 10% APC Ded and one with 3% APC. Check if quote document retains the APC Ded values on the View Quote details page and Added CR IO-19980
 *  Author			   : John
 *  Date of Creation   : 11/14/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC037 extends AbstractCommercialTest {

	public TC037() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID037.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int quotelength;
		Map<String, String> testData;
		int dataValue1 = 0;
		testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();
			buildingPage.addBuildingDetails(testData, 1, 1);

			// Added CR IO-19980
			// verifying absence of owner radio button
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			Assertions.addInfo("Building Page", "Absence of Owner Radio button");
			Assertions.verify(buildingPage.owner.checkIfElementIsPresent(), false, "Building Page",
					"Owner radio button not present is verified", false, false);

			// Add building occupancy
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			// buildingPage.addBuildingOccupancy(testData, 1, 1);

			// Add building values
			buildingPage.enterBuildingValues(testData, 1, 1);
			buildingPage.reviewBuilding();
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}
			Assertions.passTest("Building Page", "Building details entered successfully");
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// getting the quote number
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account Overview Page", "Quote Number 1 :  " + quoteNumber);

			// Asserting APC deductibles on view quote details page
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View or print full quote link");
			Assertions.addInfo("View or Print Full Quote Page", "Asserting APC deductibles on view quote details page");
			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(12).checkIfElementIsDisplayed(), true,
					"View or Print Full Quote Page",
					"The APC Deductible value "
							+ viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(12).getData()
							+ " diplayed is verified",
					false, false);
			viewOrPrintFullQuotePage.backButton.click();

			// Create another quote
			Assertions.addInfo("Account Overview Page", "Creating Another Quote");
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create another quote");

			// override bound building check
			if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			if (!testData.get("EQDeductibleValue1").equals("")) {
				createQuotePage.earthquakeDeductibleArrow.scrollToElement();
				createQuotePage.earthquakeDeductibleArrow.click();
				createQuotePage.earthquakeDeductibleOption.formatDynamicPath(testData.get("EQDeductibleValue1"))
						.scrollToElement();
				createQuotePage.earthquakeDeductibleOption.formatDynamicPath(testData.get("EQDeductibleValue1"))
						.click();
				Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			}

			// click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// getting the quote number
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber2 = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account Overview Page", "Quote Number 2 :  " + quoteNumber2);

			// Asserting APC deductibles on view quote details page
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View or print full quote link");
			Assertions.addInfo("View or Print Full Quote Page",
					"Asserting APC deductibles on view quote details page for the created quote");
			Assertions
					.verify(viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(12).checkIfElementIsDisplayed(),
							true, "View or Print Full Quote Page",
							"The APC Deductible "
									+ viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(12).getData()
									+ " displayed is verified ",
							false, false);

			// click on back
			viewOrPrintFullQuotePage.backButton.click();

			// entering details in request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber2);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// added below code to handle order of the quote displayed(Order is different in
			// UAT and QA)on the account overview page
			if (requestBindPage.submit.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber2);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}

			// Enter Bind Details
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
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 37", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 37", "Executed Successfully");
			}
		}
	}
}
