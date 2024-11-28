/** Program Description: Create an AOP Policy with Equipment breakdown, ordinance or law, wind driven rain and terrorism coverages and assert the values in the quote page and policy snapshot page
 *  Author			   : Abha
 *  Date of Creation   : 11/26/2019
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

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
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC012 extends AbstractCommercialTest {

	public TC012() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID012.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ViewPolicySnapShot viewPolicySnapshotPage = new ViewPolicySnapShot();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
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
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);

			// Clicking on View Quote Link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("View Print Full Quote Page", "Quote Page Loaded successfully");

			// Validating the details on Quote Details page
			Assertions.addInfo("View Print Full Quote Page", "Asserting the Coverage details on Quote document");
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Wind-Driven", "1").getData().contains(testData.get("WindDrivenRain")),
					true, "View Print Full Quote Page",
					"Wind Driven Rain value " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Wind-Driven", 1).getData() + " is displayed in quote page",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.selectedCoverageDetails
					.formatDynamicPath("Ordinance or Law", "1").getData().contains(testData.get("OrdinanceOrLaw")),
					true, "View Print Full Quote Page",
					"Ordinance or Law value " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Ordinance or Law", "1").getData() + " displayed in quote page",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance or Law", "2")
							.getData().contains("Equipment Breakdown"),
					true, "View Print Full Quote Page", viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Ordinance or Law", "2").getData() + " is displayed in quote document",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.terrorismCoverage.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "Terrorism coverage is included in quote page", false, false);
			scrollToTopPage();
			waitTime(3);// need wait time to scroll to top page
			viewOrPrintFullQuotePage.backButton.click();

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number is " + quoteNumber);

			// entering details in request bind page
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			}

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Clicking on View Policy Snapshot link to view the details
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy SnapShot Page", "Policy SnapShot Page Loaded successfully");

			// Verifying optional coverage details on policy snapshot page
			Assertions.addInfo("View Policy Snapshot Page",
					"Verifying optional coverage details on policy snapshot page");
			Assertions.verify(
					viewPolicySnapshotPage.policyCoverages.formatDynamicPath("1", "1", "Ordinance or Law", "3")
							.getData().contains(testData.get("OrdinanceOrLaw")),
					true, "View Policy Snapshot Page",
					"Ordinance or law value "
							+ viewPolicySnapshotPage.policyCoverages
									.formatDynamicPath("1", "1", "Ordinance or Law", "3").getData()
							+ "is displayed in Policy Snapshot page",
					false, false);
			Assertions.verify(viewPolicySnapshotPage.equipmentBreakdown.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page",
					"Equipment Breakdown value " + viewPolicySnapshotPage.equipmentBreakdown.getData()
							+ " is displayed in Policy Snapshot page",
					false, false);
			Assertions.verify(viewPolicySnapshotPage.terrorism.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "Terrorism coverage is included in Policy Snapshot page", false,
					false);

			// ----Added ticket IO-21596-----

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// creating a New account
			testData = data.get(data_Value1);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Dwelling Details
			testData = data.get(data_Value2);
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Quote Number is " + quoteNumber);

			// entering details in request bind page
			testData = data.get(data_Value1);
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			}

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page",
					"Policy is created successfully when TIV is greater than 2.5M and PolicyNumber is: " + policyNumber,
					false, false);

			// ---IO-21596 Ended-----

			// Sign out and close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 12", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 12", "Executed Successfully");
			}
		}
	}
}