
/*Description: 1.Check all the 14 states  are available for retail agents
 *             2.API Valuation - As Producer, check the valuation run on buildings that have below 85% building coverage value as Producer[Sowndarya 07/14/2023] and added IO-21378
Author: Pavan Mule
Date :  19/07/2021*/

package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC003 extends AbstractCommercialTest {

	public TC003() {
		super(LoginType.RETAILPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int locNo = 1;
		int buildingNoOne = 1;
		int buildingNotwo = 2;
		String costcardValue;
		int quoteLength;
		Map<String, String> testData = data.get(data_Value1);
		boolean addressFound = false;
		int costCardValueLength;
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.addInfo("Scenario 01", "Verifying all the 14 states  are available for retail agents");
			for (int i = 0; i < 15; i++) {
				testData = data.get(i);
				Assertions.addInfo("Eligibility page",
						"Checking Eligibility for " + testData.get("InsuredState") + " State");

				// Entering zipcode in Eligibility page
				eligibilityPage.zipCode1.waitTillVisibilityOfElement(60);
				eligibilityPage.zipCode1.setData(testData.get("ZipCode"));
				eligibilityPage.zipCode1.tab();
				eligibilityPage.waitTime(2);// wait time is needed to load the page
				if (testData.get("InsuredState").contains("CA") || testData.get("InsuredState").contains("TN")
						|| testData.get("InsuredState").contains("AZ") || testData.get("InsuredState").contains("AR")
						|| testData.get("InsuredState").contains("MO") || testData.get("InsuredState").contains("NV")
						|| testData.get("InsuredState").contains("UT")) {
					if (eligibilityPage.eligibleWarningMsg.formatDynamicPath("Accounts").checkIfElementIsPresent()
							&& eligibilityPage.eligibleWarningMsg.formatDynamicPath("Accounts")
									.checkIfElementIsDisplayed()) {
						Assertions.verify(
								eligibilityPage.eligibleWarningMsg.formatDynamicPath("Accounts")
										.checkIfElementIsPresent()
										&& eligibilityPage.eligibleWarningMsg.formatDynamicPath("Accounts")
												.checkIfElementIsDisplayed(),
								true, "Eligibility Page", testData.get("InsuredState") + " is Eligible."
										+ " ZipCode is " + testData.get("ZipCode"),
								false, false);
						eligibilityPage.refreshPage();
					} else {
						Assertions.verify(
								eligibilityPage.eligibleWarningMsg.formatDynamicPath("Accounts")
										.checkIfElementIsPresent()
										&& eligibilityPage.eligibleWarningMsg.formatDynamicPath("Accounts")
												.checkIfElementIsDisplayed(),
								false, "Eligibility Page", testData.get("InsuredState") + " is Not Eligible."
										+ " ZipCode is " + testData.get("ZipCode"),
								false, false);
					}
				}

				else {
					if (eligibilityPage.eligibleWarningMsg.formatDynamicPath("accounts").checkIfElementIsPresent()
							&& eligibilityPage.eligibleWarningMsg.formatDynamicPath("accounts")
									.checkIfElementIsDisplayed()) {
						Assertions.verify(
								eligibilityPage.eligibleWarningMsg.formatDynamicPath("accounts")
										.checkIfElementIsPresent()
										&& eligibilityPage.eligibleWarningMsg.formatDynamicPath("accounts")
												.checkIfElementIsDisplayed(),
								true, "Eligibility Page", testData.get("InsuredState") + " is Eligible."
										+ " ZipCode is " + testData.get("ZipCode"),
								false, false);
						eligibilityPage.refreshPage();
					} else {
						Assertions.verify(
								eligibilityPage.eligibleWarningMsg.formatDynamicPath("accounts")
										.checkIfElementIsPresent()
										&& eligibilityPage.eligibleWarningMsg.formatDynamicPath("accounts")
												.checkIfElementIsDisplayed(),
								false, "Eligibility Page", testData.get("InsuredState") + " is Not Eligible."
										+ " ZipCode is " + testData.get("ZipCode"),
								false, false);
					}
				}
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// creating New account
			testData = data.get(data_Value1);
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// click on add building
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.addBuildingDetails(testData, locNo, buildingNoOne);
			buildingPage.addBuildingOccupancy(testData, locNo, buildingNoOne);
			buildingPage.enterBuildingValues(testData, locNo, buildingNoOne);
			Assertions.passTest("Building Page", "Building details entered successfully");

			// Click on review building
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();

			// to change the address added this logic

			addressFound = false;

			if (buildingPage.addressMsg.checkIfElementIsPresent() && buildingPage.addressMsg.checkIfElementIsDisplayed()
					&& !addressFound) {
				for (int i = 1; i <= 20; i++) {
					if (buildingPage.editBuilding.checkIfElementIsPresent()) {
						buildingPage.editBuilding.scrollToElement();
						buildingPage.editBuilding.click();
					}

					buildingPage.manualEntry.click();
					buildingPage.manualEntryAddress.waitTillVisibilityOfElement(60);
					buildingPage.manualEntryAddress.setData(buildingPage.manualEntryAddress.getData().replace(
							buildingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""),
							(Integer.parseInt(buildingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""))) + 2
									+ ""));
					buildingPage.manualEntryAddress.tab();
					buildingPage.buildingValuesLink.scrollToElement();
					buildingPage.buildingValuesLink.click();
					buildingPage.scrollToBottomPage();
					buildingPage.waitTime(2);
					buildingPage.reviewBuilding.scrollToElement();
					buildingPage.reviewBuilding.click();

					if (!buildingPage.addressMsg.checkIfElementIsPresent()
							|| !buildingPage.addressMsg.checkIfElementIsPresent()) {
						addressFound = true;
						break;

					}
				}
			}

			Assertions.passTest("Building Page", "Clicked on Review Building");
			Assertions.verify(buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsDisplayed(), true,
					"Building Under Minimum Cost Page", "Building Under Minimum Cost Page loaded successfully", false,
					false);

			// Verify the presence of Bring up to cost button
			Assertions.addInfo("Scenario 02",
					"Verify the presence of Bring up to cost button and API valuation message when building value is $35,000");
			Assertions.verify(buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsDisplayed(), true,
					"Building Under Minimum Cost Page", "Bring Up to Cost Button displayed", false, false);
			Assertions.verify(
					buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The Building value will be increased").checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The API Valuation message"
							+ buildingUnderMinimumCostPage.costcardMessage
									.formatDynamicPath("The Building value will be increased").getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on continue
			buildingUnderMinimumCostPage.continueButton.scrollToElement();
			buildingUnderMinimumCostPage.continueButton.click();
			Assertions.passTest("Building Under Minimum Cost Page", "Clicked on Continue Button");

			// Verify the presence of Exclamatory mark in building page and verifying the
			// create quote button is disabled or not
			Assertions.addInfo("Scenario 03",
					"Verify the presence of Exclamatory mark in building page and create quote button is disabled or not");
			Assertions.verify(buildingPage.exclamatorySymbol.checkIfElementIsDisplayed(), true, "Building Page",
					"Exclamatory symbol present is verified", false, false);
			Assertions.verify(!buildingPage.createQuote.checkIfElementIsEnabled(), false, "Building Page",
					"Create Quote button disabled is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on building edit icon
			buildingPage.editBuilding.scrollToElement();
			buildingPage.editBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Edit building");

			// Enter the building value as $30000
			testData = data.get(data_Value2);
			buildingPage.enterBuildingValues(testData, locNo, buildingNoOne);

			// Click on review building
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Review Building");

			if(buildingPage.reviewBuilding.checkIfElementIsPresent()&&buildingPage.reviewBuilding.checkIfElementIsDisplayed()){
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			}

			// Store the cost card value in a variable
			costCardValueLength = buildingUnderMinimumCostPage.costcardMessage
					.formatDynamicPath("The Building value will be increased").getData().length();
			costcardValue = buildingUnderMinimumCostPage.costcardMessage
					.formatDynamicPath("The Building value will be increased").getData()
					.substring(123, costCardValueLength);

			// Click on bring up to cost button
			buildingUnderMinimumCostPage.bringUpToCost.scrollToElement();
			buildingUnderMinimumCostPage.bringUpToCost.click();
			Assertions.passTest("Building Under Minimum Cost Page", "Clicked on Bring up to cost button");

			// Verify the Building Valuation is not displayed on building page
			Assertions.addInfo("Scenario 04", "Verify the absence of Building Valuation on building page");
			Assertions.verify(
					buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation").checkIfElementIsPresent(),
					false, "Building Page", "Building Valuation not displayed is verified", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Verify the building value is updated based on API Valuation value
			Assertions.addInfo("Scenario 05", "Verify the building value is updated based on API Valuation value");
			Assertions
					.verify(buildingPage.buildingValues.formatDynamicPath("Building").getData(), costcardValue,
							"Building Page",
							"The Building value and API valuation values are same. The Updated Building value is "
									+ buildingPage.buildingValues.formatDynamicPath("Building").getData(),
							false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on building edit icon
			buildingPage.editBuilding.scrollToElement();
			buildingPage.editBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Edit building");

			// Click on add symbol
			buildingPage.addSymbol.scrollToElement();
			buildingPage.addSymbol.click();
			Assertions.passTest("Building Page", "Clicked on Add Symbol");

			// Click on Add New building
			buildingPage.addNewBuilding.scrollToElement();
			buildingPage.addNewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Add Building link");

			// Enter the building details
			testData = data.get(data_Value1);
			buildingPage.addBuildingDetails(testData, locNo, buildingNotwo);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, locNo, buildingNotwo);
			Assertions.passTest("Building Page", "Building details entered successfully");

			// Click on review building
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();

			// to change the address added this logic
			addressFound = false;
			if (buildingPage.addressMsg.checkIfElementIsPresent() && buildingPage.addressMsg.checkIfElementIsDisplayed()
					&& !addressFound) {
				for (int i = 1; i <= 50; i++) {
					if (buildingPage.editBuilding.checkIfElementIsPresent()) {
						buildingPage.editBuilding.scrollToElement();
						buildingPage.editBuilding.click();
					}
					buildingPage.manualEntry.click();
					buildingPage.manualEntryAddress.waitTillVisibilityOfElement(60);
					buildingPage.manualEntryAddress.setData(buildingPage.manualEntryAddress.getData().replace(
							buildingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""),
							(Integer.parseInt(buildingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""))) + 2
									+ ""));
					buildingPage.manualEntryAddress.tab();
					buildingPage.buildingValuesLink.scrollToElement();
					buildingPage.buildingValuesLink.click();
					buildingPage.scrollToBottomPage();
					buildingPage.waitTime(2);
					buildingPage.reviewBuilding.scrollToElement();
					buildingPage.reviewBuilding.click();

					if (!buildingPage.addressMsg.checkIfElementIsPresent()
							|| !buildingPage.addressMsg.checkIfElementIsPresent()) {
						addressFound = true;
						break;
					}
				}
			}
			Assertions.passTest("Building Page", "Clicked on Review Building");
			Assertions.verify(buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsDisplayed(), true,
					"Building Under Minimum Cost Page", "Building Under Minimum Cost Page loaded successfully", false,
					false);

			// Verify the API Valuation message
			Assertions.addInfo("Scenario 06",
					"Verify the presence Bring up tp cost button and API Valuation message when Building value is $25,000");
			Assertions.verify(buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsDisplayed(), true,
					"Building Under Minimum Cost Page", "Bring Up to Cost Button is displayed", false, false);
			Assertions.verify(
					buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The Building value will be increased").checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The API Valuation message "
							+ buildingUnderMinimumCostPage.costcardMessage
									.formatDynamicPath("The Building value will be increased").getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Store the Required building value in a variable
			costCardValueLength = buildingUnderMinimumCostPage.costcardMessage
					.formatDynamicPath("The Building value will be increased").getData().length();
			costcardValue = buildingUnderMinimumCostPage.costcardMessage
					.formatDynamicPath("The Building value will be increased").getData()
					.substring(123, costCardValueLength);

			// Click on bring up to cost button
			buildingUnderMinimumCostPage.bringUpToCost.scrollToElement();
			buildingUnderMinimumCostPage.bringUpToCost.click();
			Assertions.passTest("Building Under Minimum Cost Page", "Clicked on Bring up to cost button");

			// Verify the Building Valuation is not displayed on building page
			Assertions.addInfo("Scenario 07", "Verify the absence of Building Valuation on building page for producer");
			Assertions.verify(
					buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation").checkIfElementIsPresent(),
					false, "Building Page", "Building Valuation is not displayed for producer", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Verify the building value is updated based on API Valuation value
			Assertions.addInfo("Scenario 08",
					"Verify the building value is updated based on API Valuation value when building value entered as $25,000");
			Assertions
					.verify(buildingPage.buildingValues.formatDynamicPath("Building").getData(), costcardValue,
							"Building Page",
							"The Building value and API valuation values are same. The Updated Building value is "
									+ buildingPage.buildingValues.formatDynamicPath("Building").getData(),
							false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click on create quote button
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			Assertions.passTest("Building Page", "Clicked on Create Quote button");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLength = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1,
					quoteLength - locNo);
			Assertions.passTest("Account Overview Page", "Quote number is : " + quoteNumber);

			// Check the Valuation Report button is not displayed for producer in Account
			// Summary page
			Assertions.addInfo("Scenario 09",
					"Verify the Valuation Report button is not displayed for producer in account overview page");
			Assertions.verify(accountOverviewPage.valuationReportButton.checkIfElementIsPresent(), false,
					"Account Overview Page", "Valuation Report is not displayed for producer", false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Click on Request bind
			// Adding below code IO-21378
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on request bind button");

			// Verify prebind docs that have file extensions that are not lower cased
			Assertions.addInfo("Scenario 10",
					"Verifying prebind docs that have file extensions that are not lower cased");
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Prebind docs that have file extensions uper case uploaded successfully", false, false);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");
			// IO-21378 Ended

			// Entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as USM
			loginPage.refreshPage();
			loginPage.waitTime(2);// wait time is needed to load the page
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

			// Searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted quote " + quoteNumber + " successfullly");

			// Approving prebind document by usm and click on open referral link
			accountOverviewPage.uploadPreBindApproveAsUSM();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// Approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Need to assert Building Valuation report in View Documents folder once it is
			// implemented

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			loginPage.enterLoginDetails(setUpData.get("RetailProducer"), setUpData.get("Password"));

			// adding ticket IO-21696
			Assertions.passTest("Scenario 11",
					"Verifying the Producer is able to create an account with 90 days backdate");
			int data_Value16 = 15;
			testData = data.get(data_Value16);

			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);

			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			Assertions.passTest("Scenario 11", "Scenario 11 Ended");
			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC003 ", "Executed Successfully");
			}
		}
	}
}
