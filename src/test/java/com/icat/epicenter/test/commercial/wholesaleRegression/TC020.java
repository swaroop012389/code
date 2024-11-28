/** Program Description: Create a wind quote and check if the application allows to override the premium.
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
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC020 extends AbstractCommercialTest {

	public TC020() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID020.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
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

			// Override Building
			if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Adding CR IO-19524
			// Clicking on Override Premium Link
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();

			// Override the Premium by entering new premium
			overridePremiumAndFeesPage.overridePremium.scrollToElement();
			overridePremiumAndFeesPage.overridePremium.clearData();
			overridePremiumAndFeesPage.overridePremium.setData(testData.get("OverridePremium"));

			// Entering Fee Override Justification
			overridePremiumAndFeesPage.feeOverrideJustification.scrollToElement();
			overridePremiumAndFeesPage.feeOverrideJustification.clearData();
			overridePremiumAndFeesPage.feeOverrideJustification.setData(testData.get("FeeOverrideJustification"));

			// Clicking on Override Premium button
			overridePremiumAndFeesPage.overridePremiumButton.scrollToElement();
			overridePremiumAndFeesPage.overridePremiumButton.click();

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// goto home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search the quote number
			homePage.searchQuote(quoteNumber);

			// Validating the values of Original and override Premium
			Assertions.addInfo("Account Overview Page",
					"Validating the values of Original and override Premiums on account overview page");
			accountOverviewPage.originalPremiumData.waitTillVisibilityOfElement(60);
			Assertions.verify(accountOverviewPage.originalPremiumData.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Original Premium : " + accountOverviewPage.originalPremiumData.getData(),
					false, false);
			Assertions.verify(
					accountOverviewPage.premiumValue.getData().trim().contains(testData.get("OverridePremium")), true,
					"Account Overview Page", "Override Premium : " + accountOverviewPage.premiumValue.getData(), false,
					false);

			// Navigate to view print full quote page
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
			Assertions.addInfo("View Print/Full quote Page",
					"Validating the values of Original and override Premiums on View Print/Full quote Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath("5").getData()
							.contains(testData.get("OverridePremium")),
					true, "View Print/Full quote Page",
					"Premium value: " + viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath("5").getData()
							+ " is equal to overriden premium",
					false, false);

			// click on back
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(3); // to click on back button as the script fails at this point randomly
			viewOrPrintFullQuotePage.backButton.click();

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
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
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "POlicy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Validating the premium
			Assertions.addInfo("Policy Summary Page", "Asserting Transaction premium");
			policySummaryPage.transactionPremium.scrollToElement();
			Assertions.verify(policySummaryPage.transactionPremium.getData().contains(testData.get("OverridePremium")),
					true, "Policy Summary Page", "Transaction Premium : "
							+ policySummaryPage.transactionPremium.getData() + " is equal to overriden premium",
					false, false);

			// Asserting on policy snapshot
			policySummaryPage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully");
			Assertions.addInfo("View Policy Snapshot Page", "Asserting Premium Details on Policy snapshot page");
			Assertions.verify(
					viewPolicySnapShot.policyDeductiblesValues.formatDynamicPath("Premium Total", "2").getData(),
					"$" + testData.get("OverridePremium"), "View Policy Snapshot Page",
					viewPolicySnapShot.policyDeductibles.formatDynamicPath("Premium Total").getData() + " is "
							+ viewPolicySnapShot.policyDeductiblesValues.formatDynamicPath("Premium Total", "2")
									.getData(),
					false, false);

			// sign out and close browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 20", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 20", "Executed Successfully");
			}
		}
	}
}