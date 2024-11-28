/*Description: 1.Check the \"(Optional)\" wording is NOT displayed with effective date in the Create Account Overlay
 * 			   2.Check the Guy Carpenter values for NB quote and validate the ELR and AAL values are calculated correctly and Check the Guy Carpenter values for Renewal requote and validate the ELR and AAL values are calculated correctly and IO-22101,IO-22093 and IO-22035
Author: Pavan Mule
Date :  19/07/2021*/

package com.icat.epicenter.test.commercial.retailRegression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RmsModelResultsPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC001 extends AbstractCommercialTest {

	public TC001() {
		super(LoginType.RETAILPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		LoginPage loginPage = new LoginPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		RmsModelResultsPage rmsModelResultsPage = new RmsModelResultsPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyrenewalPage = new PolicyRenewalPage();
		DwellingPage dwellingPage = new DwellingPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		int dataValue1 = 0;
		int dataValue3 = 2;
		int dataValue4 = 3;
		Map<String, String> testData = data.get(dataValue1);
		int locationNumber = Integer.parseInt(testData.get("LocCount"));
		int buildingNumber = Integer.parseInt(testData.get("L" + locationNumber + "-BldgCount"));
		String quoteNumber;
		String elrPremium;
		String aalValue;
		String elrValue;
		double calELR;
		BigDecimal b_calELR;
		String uiPremium;
		String datasheetPremium;
		String policyNumber;
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountProducer.waitTillPresenceOfElement(60);
			homePage.createNewAccountProducer.click();
			Assertions.passTest("Home Page", "Clicked on Create New Account button successfully");
			Assertions.addInfo("Scenario 01", "Asserting Optional word is not displayed with effective date");
			Assertions.verify(homePage.effectiveDate.formatDynamicPath(1).getData().contains("Optional"), false,
					"Home Page",
					"(Optional) wording is NOT displayed with effective date in the Create Account Overlay is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Adding ticket IO-20756
			// Entering Details in Create New Account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded Successfully", false, false);
			homePage.createNewAccountProducer.waitTillPresenceOfElement(60);
			homePage.createNewAccountProducer.click();
			Assertions.passTest("Home Page", "Clicked on Create New Account Button successfully");
			homePage.createNewAccountWithNamedInsured(testData, setUpData);

			// Entering Zipcode Details
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zip Code is entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page is loaded successfully", false, false);
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();
			Assertions.passTest("Location Page", "Location Page details entered successfully");

			// Entering Building Details
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Building Details entered successfully");

			// Checking for the presence of AOP Peril Option in Select Peril Page based on
			// IO-20756

			for (int i = 0; i <= 2; i++) {
				testData = data.get(i);
				Assertions.addInfo("Scenario 0" + String.valueOf(i + 2),
						"Asserting AOP Peril is not available for Wind Only Occupancy, "
								+ testData.get("L" + locationNumber + "B" + buildingNumber + "-PrimaryOccupancy"));
				Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Create Quote Page loaded Successfully", false, false);
				Assertions.addInfo("Scenario 0" + String.valueOf(i + 2),
						"Scenario 0" + String.valueOf(i + 2) + " Ended");

				// If Condition to break For Loop after checking the absence of AOP Peril option
				// for 3 Wind Only Occupancies
				if (i == 2) {
					break;
				}
				createQuotePage.previous.scrollToElement();
				createQuotePage.previous.click();

				// Go back to Edit Building Occupancy
				Assertions.verify(accountOverviewPage.editLocation.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page is loaded with option to edit Building Details",
						false, false);
				accountOverviewPage.editLocation.scrollToElement();
				accountOverviewPage.editLocation.click();

				Assertions.verify(
						buildingPage.buildingLink.formatDynamicPath(locationNumber, buildingNumber)
								.checkIfElementIsDisplayed(),
						true, "Building Page", "Building Page is loaded with options to Edit Building Details", false,
						false);
				buildingPage.buildingLink.formatDynamicPath(locationNumber, buildingNumber).scrollToElement();
				buildingPage.buildingLink.formatDynamicPath(locationNumber, buildingNumber).click();

				Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
						"Building Page is loaded successfully", false, false);
				buildingPage.buildingOccupancyLink.scrollToElement();
				buildingPage.buildingOccupancyLink.click();
				Assertions.passTest("Building Page", "Building 1-1 link is clicked successfully");

				Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
						"Building Page is loaded successfully", false, false);
				buildingPage.addBuildingPrimaryOccupancy(testData, locationNumber, buildingNumber);

				Assertions.passTest("Building Page", "Building Occupancy Details filled");
				buildingPage.reviewBuilding.scrollToElement();
				buildingPage.reviewBuilding.click();
				Assertions.passTest("Building Page", "Clicked on Review Building Button");
				//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
						//"Building Page loaded for Review", false, false);
				buildingPage.createQuote.scrollToElement();
				buildingPage.createQuote.click();
				Assertions.passTest("Building Page", "Create Quote Button is clicked");
			}

			// Adding ticket IO-22035,IO-22101 and IO-22093
			// Click on previous button
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.passTest("Create Quote Page", "Clicked on previous button successfully");

			// Click on edit location
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit location link and building link");

			// Click on building link
			buildingPage.buildingLink.formatDynamicPath(locationNumber, buildingNumber).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(locationNumber, buildingNumber).click();
			Assertions.passTest("Building Page", "Clicked on edit building");

			// click on roof details link
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page is loaded successfully", false, false);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Building Page", "Clicked on roof details link");

			// Verifying absence of roof age drop down
			Assertions.addInfo("Scenario 05", "Verifying absence of roof age drop down on building page");
			Assertions.verify(
					buildingPage.oldRoofAgeData.checkIfElementIsPresent()
							&& buildingPage.oldRoofAgeData.checkIfElementIsDisplayed(),
					false, "Building Page", "Roof age drop down not displayed", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Verifying Year roof last replaced wording is Year Roof Last Replaced
			// (Defaults to Year Built if empty): on building page
			Assertions.addInfo("Scenario 06", "Verifying Year roof last replaced wording on building page");
			Assertions.verify(buildingPage.yearRoofLastReplacedLabel.getData().contains("Year Roof Last Replaced"),
					true, "Building Page", "Year roof last replaced wording is "
							+ buildingPage.yearRoofLastReplacedLabel.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// add year roof last replaced
			testData = data.get(dataValue3);
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.appendData(testData.get("YearRoofLastReplaced"));
			buildingPage.yearRoofLastReplaced.tab();
			Assertions.passTest("Building Page", "Year roof last replaced added successfully");
			buildingPage.waitTime(3);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();

			// IO-22035
			// Asserting and verifying Year roof last replaced message, when adding year
			// roof last replaced, the message is'The entire roof must be fully replaced in
			// order to consider an updated roof year
			// Roof Replacement: The removal and replacement of the entire roof surface down
			// to the decking.
			// Partial repairs, recoating or temporary repairs are considered maintenance
			// only and do not determine the age of the roof.
			// Check out our Roof Replacement vs. Recoat Video for more information.

			Assertions.addInfo("Scenario 07",
					"Asserting and verifying Year roof last replaced message on building page, when year roof last replaced added");
			buildingPage.yearRoofLastReplacedMessage.scrollToElement();
			Assertions.verify(
					buildingPage.yearRoofLastReplacedMessage.getData().contains(
							"The entire roof must be fully replaced in order to consider an updated roof year"),
					true, "Building Page", "Year roof last replaced message is "
							+ buildingPage.yearRoofLastReplacedMessage.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// click on review building
			buildingPage.waitTime(3);
			buildingPage.scrollToBottomPage();
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on review building button");

			// Verifying presence of year roof last replaced label and value, when added the
			// year roof last replaced on dwelling page
			Assertions.addInfo("Scenario 08",
					"Verifying presence of year roof last replaced label and value on dwelling page, when added the year roof last replaced");
			Assertions.verify(
					dwellingPage.yearRoofLastReplacedlabel.checkIfElementIsDisplayed()
							&& dwellingPage.yearRoofLastReplacedlabel.getData().contains("Year Roof Last Replaced"),
					true, "Dwelling Page", "Year Roof Last Replaced wording is "
							+ dwellingPage.yearRoofLastReplacedlabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					dwellingPage.roofLastReplacedData.checkIfElementIsDisplayed() && dwellingPage.roofLastReplacedData
							.getData().contains(testData.get("YearRoofLastReplaced")),
					true, "Dwelling Page",
					"Year Roof Last Replaced value is " + dwellingPage.roofLastReplacedData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click on create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on create quote button");

			// Enter quote details
			testData = data.get(dataValue1);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// quote referring because of roof valuation
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {

				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
				quoteNumber = referQuotePage.quoteNum.getData();
				Assertions.passTest("Referral Quote Page", "Quote Number :  " + quoteNumber);

				// Logout as producer
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Log out as producer");

				// Login as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

				// searching the quote number in grid and clicking on the quote link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"USM Home page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");
				Assertions.verify(accountOverviewPage.openReferralLink.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

				// Click on Open Referral Link
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

				// Approve quote referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referred quote approved successfully");

				// Logout as usm
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Log out as usm");

				// Login as producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("RetailProducer"), setUpData.get("Password"));
				Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
						"Loged in to producer successfully", false, false);

				homePage.searchQuoteByProducer(quoteNumber);
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view print full quote link successfully");

			// Verifying presence of year roof last replaced, wording and value on view
			// print full quote page, when added
			// year roof last replaced
			Assertions.addInfo("Scenario 09",
					"Verifying presence of year roof last replaced, wording and value on view print full quote page, when added year roof last replaced");
			Assertions.verify(viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.checkIfElementIsDisplayed()
					&& viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.getData().contains("Year Roof Last Replaced"),
					true, "View Print Full Quote Page", "Year roof last replaced wording is "
							+ viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.getData(),
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.yearRoofLastReplacedValue.formatDynamicPath(1, 1, 2).getData()
							.substring(25, 29).contains(testData.get("YearRoofLastReplaced")), true,
							"View Print Full Quote Page",
							"Year Roof Last Replaced is " + viewOrPrintFullQuotePage.yearRoofLastReplacedValue
									.formatDynamicPath(1, 1, 2).getData().substring(25, 29) + " displayed",
							false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Print Full Quote Page", "Clicked on back button");

			// Click on edit location
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit location link");

			// Click on building link
			buildingPage.buildingLink.formatDynamicPath(locationNumber, buildingNumber).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(locationNumber, buildingNumber).click();

			// click on roof details link
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page is loaded successfully", false, false);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Building Page", "Clicked on roof details link ");

			// Removing year roof last replaced
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.clearData();

			// Click on continue with updated button
			buildingPage.continueWithUpdateBtn.scrollToElement();
			buildingPage.continueWithUpdateBtn.click();
			Assertions.passTest("Building Page",
					"Remove year roof last replaced and click on continue with update button");

			// click on review building
			buildingPage.waitTime(3);
			buildingPage.scrollToBottomPage();
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on review building button");

			// When year roof last replaced not added, the default value is N/A
			Assertions.addInfo("Scenario 10",
					"Verifying absence of year roof last replaced value on dwelling page, when year roof last replaced not added");
			Assertions.verify(
					dwellingPage.roofLastReplacedData.checkIfElementIsDisplayed()
							&& dwellingPage.roofLastReplacedData.getData().contains("N/A"),
					true, "Dwelling Page",
					"Year Roof Last Replaced value is " + dwellingPage.roofLastReplacedData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Click on create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on create quote button");

			// Enter quote details
			testData = data.get(dataValue1);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// quote referring because of roof valuation
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {

				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
				quoteNumber = referQuotePage.quoteNum.getData();
				Assertions.passTest("Referral Quote Page", "Quote Number :  " + quoteNumber);

				// Logout as producer
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Log out as producer");

				// Login as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

				// searching the quote number in grid and clicking on the quote link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"USM Home page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");
				Assertions.verify(accountOverviewPage.openReferralLink.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

				// Click on Open Referral Link
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

				// Approve quote referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referred quote approved successfully");

				// Logout as usm
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Log out as usm");

				// Login as producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("RetailProducer"), setUpData.get("Password"));
				Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
						"Loged in to producer successfully", false, false);

				homePage.searchQuoteByProducer(quoteNumber);
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view print full quote link successfully");

			// Verifying presence of year roof last replaced, wording and value, when not
			// added year roof last replaced,the value of year roof last replaced is same as
			// year built
			Assertions.addInfo("Scenario 11",
					"Verifying presence of year roof last replaced, wording and value,when not added year roof last replaced,the value of year roof last replaced is same as year built");
			Assertions.verify(viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.checkIfElementIsDisplayed()
					&& viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.getData().contains("Year Roof Last Replaced"),
					true, "View Print Full Quote Page", "Year roof last replaced wording is "
							+ viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.getData(),
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.yearRoofLastReplacedValue.formatDynamicPath(1, 1, 2).getData()
							.substring(25, 29).contains(testData.get("L1B1-BldgYearBuilt")), true,
							"View Print Full Quote Page",
							"Year Roof Last Replaced is " + viewOrPrintFullQuotePage.yearRoofLastReplacedValue
									.formatDynamicPath(1, 1, 2).getData().substring(25, 29) + " displayed",
							false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");
			// Ticket IO-22035,IO-22093 and IO-22101 ended

			// Log Out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Log out as producer successfully");

			// Below code for gut carpenter
			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Loged into usm successfully");

			// creating New account
			testData = data.get(dataValue4);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zip code in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Building details entered successfully");

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// enter prior loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior loss details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Verifying presence of view model result link
			Assertions.addInfo("Scenario 12", "Verifying presence of view model result link");
			Assertions.verify(accountOverviewPage.viewModelResultsLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View model result link displayed", false, false);
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			// Click on view model result link
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view model result link");

			// Verify presence of GC17,ELR Premium, peril AAL,peril ELR,Total
			// Premium,TIV,Peril and Peril Deductible labels
			rmsModelResultsPage.closeButton.waitTillVisibilityOfElement(60);
			Assertions.verify(rmsModelResultsPage.closeButton.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page", "RMS Model Result page loaded successfully", false, false);
			Assertions.addInfo("Scenario 13",
					"Verify presence of GC17,ELR Premium, peril AAL,peril ELR,Total Premium,TIV,Peril and Peril Deductible labels");
			Assertions.verify(
					rmsModelResultsPage.gc17Label.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.gc17Label.getData().contains("GC17"),
					true, "RMS Model Result Page",
					"Guy Carpenter label is " + rmsModelResultsPage.gc17Label.getData() + " displayed", false, false);
			Assertions.verify(
					rmsModelResultsPage.elrPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.elrPremiumLabel.getData().contains("ELR Premium"),
					true, "RMS Model Result Page", "Guy Carpenter ELR Premium label is "
							+ rmsModelResultsPage.elrPremiumLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterELRLabel.getData().contains("Peril ELR"),
					true, "RMS Model Result Page", "Guy Carpenter Peril ELR label is "
							+ rmsModelResultsPage.guyCarpenterELRLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterAALLabel.getData().contains("Peril AAL"),
					true, "RMS Model Result Page", "Guy Carpenter Peril AAL label is "
							+ rmsModelResultsPage.guyCarpenterAALLabel.getData() + " displayed",
					false, false);

			Assertions.verify(
					rmsModelResultsPage.perilDeductibleLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.perilDeductibleLabel.getData().contains("Peril Deductible:"),
					true, "RMS Model Result Page", "Guy Carpenter Peril Deductible label is "
							+ rmsModelResultsPage.perilDeductibleLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.perilLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.perilLabel.getData().contains("Peril:"),
					true, "RMS Model Result Page",
					"Guy Carpenter Peril label is " + rmsModelResultsPage.perilLabel.getData() + " displayed", false,
					false);
			Assertions.verify(
					rmsModelResultsPage.totalPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.totalPremiumLabel.getData().contains("Total Premium:"),
					true, "RMS Model Result Page", "Guy Carpenter Total Premium label is "
							+ rmsModelResultsPage.totalPremiumLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.tivValueLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.tivValueLabel.getData().contains("TIV"),
					true, "RMS Model Result Page",
					"Guy Carpenter TIV label is " + rmsModelResultsPage.tivValueLabel.getData() + " displayed", false,
					false);
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			// Verifying and Asserting ELR Premium,Peril AAL,Peril ELR,TIV,Peril,Peril
			// Deductible and Total Premium values
			Assertions.addInfo("Scenario 14",
					"Verifying and Asserting ELR Premium,Peril AAL,Peril ELR,TIV,Peril,Peril Deductible and Total Premium values");
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("ELR Premium", 1).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"ELR Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium", 1).getData()
							+ " displayed",
					false, false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterELR.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"Peril ELR value is " + rmsModelResultsPage.guyCarpenterELR.getData() + " displayed", false, false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterAAL.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"Peril AAL value is " + rmsModelResultsPage.guyCarpenterAAL.getData() + " displayed", false, false);
			Assertions.verify(rmsModelResultsPage.tivValue.checkIfElementIsDisplayed(), true, "RMS Model Result Page",
					"TIV value is " + rmsModelResultsPage.tivValue.getData() + " displayed", false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril:", 1).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Peril value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril:", 1).getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril Deductible", 1)
							.checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Peril Deductible value is " + rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Peril Deductible", 1).getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Total Premium", 1).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Total Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Total Premium", 1).getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 14", "Scenario 14 Ended");

			// Getting ELR Premium,AAL Value and ELR Value
			elrPremium = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium", 1).getData()
					.replaceAll("[^a-zA-Z0-9\\s]", "");
			aalValue = rmsModelResultsPage.guyCarpenterAAL.getData().replaceAll("[^a-zA-Z0-9\\s]", "");
			elrValue = rmsModelResultsPage.guyCarpenterELR.getData().replace("%", "");

			// Calculating ELR = (AAL/ELR Premium)*100
			// Verifying actual and calculated Peril ELR
			Assertions.addInfo("Scenario 15", "Verifying actual and calculated Peril ELR");
			calELR = (Double.parseDouble(aalValue) / Double.parseDouble(elrPremium)) * 100;

			// Rounding cal ELR decimal value to 1 digits(eg.36.08 = 36.1)
			b_calELR = new BigDecimal(Double.toString(calELR));
			b_calELR = b_calELR.setScale(1, RoundingMode.HALF_UP);
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(elrValue), 2) - Precision.round(calELR, 2)),
					2) < 0.5) {
				Assertions.passTest("RMS Model Result Page", "Calculated Peril ELR is " + b_calELR + "%");
				Assertions.passTest("RMS Model Result Page", "Actual Peril ELR is " + elrValue + "%");

			} else {
				Assertions.verify(elrValue, calELR, "RMS Model Result Page",
						"The Difference between actual and calculated peril ELR value is more than 0.5", false, false);
			}
			Assertions.addInfo("Scenario 15", "Scenario 15 Ended");

			// Click on close
			rmsModelResultsPage.closeButton.scrollToElement();
			rmsModelResultsPage.closeButton.click();
			Assertions.passTest("RMS Model Result Page", "Clicked on close button successfully");

			// Verifying presence of Push To RMS link
			Assertions.addInfo("Scenario 16", "Verifying presence of Push To RMS link");
			Assertions.verify(accountOverviewPage.pushToRMSLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Push To RMS link displayed ", false, false);
			Assertions.addInfo("Scenario 16", "Scenario 16 Ended");

			// Click on Push to RMS link
			accountOverviewPage.pushToRMSLink.scrollToElement();
			accountOverviewPage.pushToRMSLink.click();
			Assertions.addInfo("Scenario 17", "Asserting RMS message when clicked on push to rms button");
			Assertions.verify(
					accountOverviewPage.dwellingSuccessfullyDeletedMsg.checkIfElementIsDisplayed()
							&& accountOverviewPage.dwellingSuccessfullyDeletedMsg
									.getData().contains("The account is being modeled in RMS"),
					true, "Account Overview Page",
					"Clicked on push to rms button successfully and the message is "
							+ accountOverviewPage.dwellingSuccessfullyDeletedMsg.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 18", "Scenario 18 Ended");
			accountOverviewPage.runningLink.waitTillPresenceOfElement(60);
			accountOverviewPage.runningLink.waitTillVisibilityOfElement(60);

			// Click on view model results link
			while (accountOverviewPage.runningLink.checkIfElementIsPresent()) {
				accountOverviewPage.refreshPage();
			}
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.searchQuote(quoteNumber);
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Model Results link successfully");

			// Verifying and Asserting ELR Premium,Peril AAL,Peril ELR,TIV,Peril,Peril
			// Deductible and Total Premium values of RMS21
			Assertions.addInfo("Scenario 19",
					"Verifying and Asserting ELR Premium,Peril AAL,Peril ELR,TIV,Peril,Peril Deductible and Total Premium values of RMS21");
			Assertions.verify(
					rmsModelResultsPage.rms21Label.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.rms21Label.getData().contains("RMS21"),
					true, "RMS Model Result Page",
					"RMS21 label is " + rmsModelResultsPage.rms21Label.getData() + " displayed", false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("ELR Premium", 1).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"RMS21 ELR Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium", 1).getData()
							+ " displayed",
					false, false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterELR.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"RMS21 Peril ELR value is " + rmsModelResultsPage.guyCarpenterELR.getData() + " displayed", false,
					false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterAAL.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"RMS21 Peril AAL value is " + rmsModelResultsPage.guyCarpenterAAL.getData() + " displayed", false,
					false);
			Assertions.verify(rmsModelResultsPage.tivValue.checkIfElementIsDisplayed(), true, "RMS Model Result Page",
					"RMS21 TIV value is " + rmsModelResultsPage.tivValue.getData() + " displayed", false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril:", 1).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"RMS21 Peril value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril:", 1).getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril Deductible", 1)
							.checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"RMS21 Peril Deductible value is " + rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Peril Deductible", 1).getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Total Premium", 1).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"RMS21 Total Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Total Premium", 1).getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 19", "Scenario 19 Ended");

			// Getting ELR Premium,AAL Value and ELR Value
			elrPremium = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium", 1).getData()
					.replaceAll("[^a-zA-Z0-9\\s]", "");
			aalValue = rmsModelResultsPage.guyCarpenterAAL.getData().replaceAll("[^a-zA-Z0-9\\s]", "");
			elrValue = rmsModelResultsPage.guyCarpenterELR.getData().replace("%", "");

			// Calculating ELR = (AAL/ELR Premium)*100
			// Verifying actual and calculated Peril ELR of RMS21
			Assertions.addInfo("Scenario 20", "Verifying actual and calculated Peril ELR of RMS21");
			calELR = (Double.parseDouble(aalValue) / Double.parseDouble(elrPremium)) * 100;

			// Rounding cal ELR decimal value to 1 digits(eg.36.08 = 36.1)
			b_calELR = new BigDecimal(Double.toString(calELR));
			b_calELR = b_calELR.setScale(1, RoundingMode.HALF_UP);
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(elrValue), 2) - Precision.round(calELR, 2)),
					2) < 0.5) {
				Assertions.passTest("RMS Model Result Page", "Calculated RMS21 Peril ELR is " + b_calELR + "%");
				Assertions.passTest("RMS Model Result Page", "Actual RMS21 Peril ELR is " + elrValue + "%");

			} else {
				Assertions.verify(elrValue, calELR, "RMS Model Result Page",
						"The Difference between actual and calculated RMS21 peril ELR value is more than 0.5", false,
						false);
			}
			Assertions.addInfo("Scenario 20", "Scenario 20 Ended");

			// Click on close
			rmsModelResultsPage.closeButton.scrollToElement();
			rmsModelResultsPage.closeButton.click();
			Assertions.passTest("RMS Model Result Page", "Clicked on close button successfully");

			// click on override premium link
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Override premium link");

			// Enter override premium details
			Assertions.verify(overridePremiumAndFeesPage.overridePremiumButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees Page loaded successfully", false,
					false);
			overridePremiumAndFeesPage.overridePremiumAndFeesDetails(testData);
			Assertions.passTest("Override Premium and Fees Page",
					"Override premium and fees details entered successfully");

			// Verifying and asserting updated premium value and original premium value;
			Assertions.addInfo("Scenario 21", "Verifying and asserting updated premium values");
			uiPremium = accountOverviewPage.premiumValue.getData().replace(".00", "").replace(",", "").replace("$", "")
					.replace(" ", "");
			datasheetPremium = testData.get("OverridePremium").replace(".0", "").replace(" ", "");
			double d_datasheetPremium = Double.parseDouble(datasheetPremium);

			if (Precision.round(
					Math.abs(
							Precision.round(Double.parseDouble(uiPremium), 2) - Precision.round(d_datasheetPremium, 2)),
					2) < 1.0) {
				Assertions.passTest("Account Overview Page",
						"Original Premium value is $" + accountOverviewPage.originalPremiumData.getData());
				Assertions.passTest("Account Overview Page", "Updated premium value is $" + uiPremium);

			} else {
				Assertions.verify(uiPremium, datasheetPremium, "Account Overview Page",
						"The Difference between updated premium and data sheet premium value is more than 1.0", false,
						false);
			}
			Assertions.addInfo("Scenario 21", "Scenario 21 Ended");

			// Click on view model result link
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Model Results link successfully");

			// Verifying and Asserting ELR Premium,Peril AAL,Peril ELR,TIV,Peril,Peril
			// Deductible and Total Premium values after updating the override premium value
			Assertions.addInfo("Scenario 22",
					"After updating override premium value,verifying and asserting ELR premium,peril aal,peril elr,TIV,Peril,Peril Deductible and Total Premium values");
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("ELR Premium", 2).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"ELR Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium", 2).getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Peril ELR", 2).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Peril ELR value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR", 2).getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Peril AAL", 2).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Peril AAL value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril AAL", 2).getData()
							+ " displayed",
					false, false);
			Assertions.verify(rmsModelResultsPage.tivValue.checkIfElementIsDisplayed(), true, "RMS Model Result Page",
					"TIV value is " + rmsModelResultsPage.tivValue.getData() + " displayed", false, false);

			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril:", 2).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Peril value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril:", 2).getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril Deductible", 2)
							.checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Peril Deductible value is " + rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Peril Deductible", 2).getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Total Premium", 2).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Total Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Total Premium", 2).getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 22", "Scenario 22 Ended");

			// Getting ELR Premium,AAL Value and ELR Value
			elrPremium = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium", 2).getData()
					.replaceAll("[^a-zA-Z0-9\\s]", "");
			aalValue = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril AAL", 2).getData()
					.replaceAll("[^a-zA-Z0-9\\s]", "");
			elrValue = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR", 2).getData().replace("%",
					"");

			// Calculating ELR = (AAL/ELR Premium)*100
			// Verifying actual and calculated Peril ELR
			Assertions.addInfo("Scenario 23", "Verifying actual and calculated Peril ELR");
			calELR = (Double.parseDouble(aalValue) / Double.parseDouble(elrPremium)) * 100;

			// Rounding cal ELR decimal value to 1 digits(eg.36.08 = 36.1)
			b_calELR = new BigDecimal(Double.toString(calELR));
			b_calELR = b_calELR.setScale(1, RoundingMode.HALF_UP);
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(elrValue), 2) - Precision.round(calELR, 2)),
					2) < 0.5) {
				Assertions.passTest("RMS Model Result Page", "Calculated Peril ELR is " + b_calELR + "%");
				Assertions.passTest("RMS Model Result Page", "Actual Peril ELR is " + elrValue + "%");

			} else {
				Assertions.verify(elrValue, calELR, "RMS Model Result Page",
						"The Difference between actual and calculated peril ELR value is more than 0.5", false, false);
			}
			Assertions.addInfo("Scenario 23", "Scenario 23 Ended");

			// Click on close
			rmsModelResultsPage.closeButton.scrollToElement();
			rmsModelResultsPage.closeButton.click();
			Assertions.passTest("RMS Model Result Page", "Clicked on close button successfully");

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request Bind");

			// entering bind details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind request page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home page", "Quote for referral is searched successfully");

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy summary page", "Policy summary page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy summary page", "Policy number is " + policyNumber, false, false);

			// Click on home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Add expac details
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search policy number
			homePage.searchPolicy(policyNumber);

			// clicking on renewal policy link
			policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy summary page", "Clicked on Renewal link successfully");

			// Click on continue button
			policyrenewalPage.continueRenewal.scrollToElement();
			policyrenewalPage.continueRenewal.click();
			Assertions.passTest("Policy renewal page", "Clicked on continue button successfully");
			if (policyrenewalPage.yesButton.checkIfElementIsPresent()
					&& policyrenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyrenewalPage.yesButton.scrollToElement();
				policyrenewalPage.yesButton.click();
			}

			// Getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number :  " + quoteNumber);

			// Click on create another quote button
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on create another quote button successfully");

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number2 :  " + quoteNumber);

			// Verifying presence of view model result link
			Assertions.addInfo("Scenario 24", "Verifying presence of view model result link");
			Assertions.verify(accountOverviewPage.viewModelResultsLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View model result link displayed", false, false);
			Assertions.addInfo("Scenario 24", "Scenario 24 Ended");

			// Click on view model result link
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view model result link");

			// Verify presence of GC17,ELR Premium, peril AAL,peril ELR,Total
			// Premium,TIV,Peril and Peril Deductible labels
			rmsModelResultsPage.closeButton.waitTillVisibilityOfElement(60);
			Assertions.verify(rmsModelResultsPage.closeButton.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page", "RMS Model Result page loaded successfully", false, false);
			Assertions.addInfo("Scenario 25",
					"Verify presence of GC17,ELR Premium, peril AAL,peril ELR,Total Premium,TIV,Peril and Peril Deductible labels");
			Assertions.verify(
					rmsModelResultsPage.gc17Label.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.gc17Label.getData().contains("GC17"),
					true, "RMS Model Result Page",
					"Guy Carpenter label is " + rmsModelResultsPage.gc17Label.getData() + " displayed", false, false);
			Assertions.verify(
					rmsModelResultsPage.elrPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.elrPremiumLabel.getData().contains("ELR Premium"),
					true, "RMS Model Result Page", "Guy Carpenter ELR Premium label is "
							+ rmsModelResultsPage.elrPremiumLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterELRLabel.getData().contains("Peril ELR"),
					true, "RMS Model Result Page", "Guy Carpenter Peril ELR label is "
							+ rmsModelResultsPage.guyCarpenterELRLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterAALLabel.getData().contains("Peril AAL"),
					true, "RMS Model Result Page", "Guy Carpenter Peril AAL label is "
							+ rmsModelResultsPage.guyCarpenterAALLabel.getData() + " displayed",
					false, false);

			Assertions.verify(
					rmsModelResultsPage.perilDeductibleLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.perilDeductibleLabel.getData().contains("Peril Deductible:"),
					true, "RMS Model Result Page", "Guy Carpenter Peril Deductible label is "
							+ rmsModelResultsPage.perilDeductibleLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.perilLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.perilLabel.getData().contains("Peril:"),
					true, "RMS Model Result Page",
					"Guy Carpenter Peril label is " + rmsModelResultsPage.perilLabel.getData() + " displayed", false,
					false);
			Assertions.verify(
					rmsModelResultsPage.totalPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.totalPremiumLabel.getData().contains("Total Premium:"),
					true, "RMS Model Result Page", "Guy Carpenter Total Premium label is "
							+ rmsModelResultsPage.totalPremiumLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.tivValueLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.tivValueLabel.getData().contains("TIV"),
					true, "RMS Model Result Page",
					"Guy Carpenter TIV label is " + rmsModelResultsPage.tivValueLabel.getData() + " displayed", false,
					false);
			Assertions.addInfo("Scenario 25", "Scenario 25 Ended");

			// Verifying and Asserting ELR Premium,Peril AAL,Peril ELR,TIV,Peril,Peril
			// Deductible and Total Premium values
			Assertions.addInfo("Scenario 26",
					"Verifying and Asserting ELR Premium,Peril AAL,Peril ELR,TIV,Peril,Peril Deductible and Total Premium values");
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("ELR Premium", 1).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"ELR Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium", 1).getData()
							+ " displayed",
					false, false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterELR.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"Peril ELR value is " + rmsModelResultsPage.guyCarpenterELR.getData() + " displayed", false, false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterAAL.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"Peril AAL value is " + rmsModelResultsPage.guyCarpenterAAL.getData() + " displayed", false, false);
			Assertions.verify(rmsModelResultsPage.tivValue.checkIfElementIsDisplayed(), true, "RMS Model Result Page",
					"TIV value is " + rmsModelResultsPage.tivValue.getData() + " displayed", false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril:", 1).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Peril value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril:", 1).getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril Deductible", 1)
							.checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Peril Deductible value is " + rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Peril Deductible", 1).getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Total Premium", 1).checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Total Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Total Premium", 1).getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 26", "Scenario 26 Ended");

			// Getting ELR Premium,AAL Value and ELR Value
			elrPremium = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium", 1).getData()
					.replaceAll("[^a-zA-Z0-9\\s]", "");
			aalValue = rmsModelResultsPage.guyCarpenterAAL.getData().replaceAll("[^a-zA-Z0-9\\s]", "");
			elrValue = rmsModelResultsPage.guyCarpenterELR.getData().replace("%", "");

			// Calculating ELR = (AAL/ELR Premium)*100
			// Verifying actual and calculated Peril ELR
			Assertions.addInfo("Scenario 27", "Verifying actual and calculated Peril ELR");
			calELR = (Double.parseDouble(aalValue) / Double.parseDouble(elrPremium)) * 100;

			// Rounding cal ELR decimal value to 1 digits(eg.36.08 = 36.1)
			b_calELR = new BigDecimal(Double.toString(calELR));
			b_calELR = b_calELR.setScale(1, RoundingMode.HALF_UP);
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(elrValue), 2) - Precision.round(calELR, 2)),
					2) < 0.5) {
				Assertions.passTest("RMS Model Result Page", "Calculated Peril ELR is " + b_calELR + "%");
				Assertions.passTest("RMS Model Result Page", "Actual Peril ELR is " + elrValue + "%");

			} else {
				Assertions.verify(elrValue, calELR, "RMS Model Result Page",
						"The Difference between actual and calculated peril ELR value is more than 0.5", false, false);
			}
			Assertions.addInfo("Scenario 27", "Scenario 27 Ended");

			// Click on close
			rmsModelResultsPage.closeButton.scrollToElement();
			rmsModelResultsPage.closeButton.click();
			Assertions.passTest("RMS Model Result Page", "Clicked on close button successfully");

			// Below code adding for IO-22093,IO-22101 and IO-22035 Renewal
			// Click on edit location
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit location link and building link");

			// Click on building link
			buildingPage.buildingLink.formatDynamicPath(locationNumber, buildingNumber).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(locationNumber, buildingNumber).click();

			// click on roof details link
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page is loaded successfully", false, false);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Building Page", "Clicked on roof details link");

			// Verifying absence of roof age drop down renewal quote
			Assertions.addInfo("Scenario 29", "Verifying absence of roof age drop down on renewal quote");
			Assertions.verify(
					buildingPage.oldRoofAgeData.checkIfElementIsPresent()
							&& buildingPage.oldRoofAgeData.checkIfElementIsDisplayed(),
					false, "Building Page", "Roof age drop down not displayed", false, false);
			Assertions.addInfo("Scenario 29", "Scenario 29 Ended");

			// Verifying Year roof last replaced wording is Year Roof Last Replaced
			// (Defaults to Year Built if empty): on renewal quote
			Assertions.addInfo("Scenario 30", "Verifying Year roof last replaced wording on renewal quote");
			Assertions.verify(buildingPage.yearRoofLastReplacedLabel.getData().contains("Year Roof Last Replaced"),
					true, "Building Page", "Year roof last replaced wording is "
							+ buildingPage.yearRoofLastReplacedLabel.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 30", "Scenario 30 Ended");

			// add year roof last replaced
			testData = data.get(dataValue3);
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.appendData(testData.get("YearRoofLastReplaced"));
			buildingPage.yearRoofLastReplaced.tab();
			Assertions.passTest("Building Page", "Year roof last replaced added successfully");

			// Click on continue with updated button
			buildingPage.continueWithUpdateBtn.scrollToElement();
			buildingPage.continueWithUpdateBtn.click();
			Assertions.passTest("Building Page", "Year roof last replaced and click on continue");
			buildingPage.waitTime(3);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();

			// IO-22035
			// Asserting and verifying Year roof last replaced message, when adding year
			// roof last replaced, the message is'The entire roof must be fully replaced in
			// order to consider an updated roof year
			// Roof Replacement: The removal and replacement of the entire roof surface down
			// to the decking.
			// Partial repairs, recoating or temporary repairs are considered maintenance
			// only and do not determine the age of the roof.
			// Check out our Roof Replacement vs. Recoat Video for more information. on
			// renewal quote
			Assertions.addInfo("Scenario 31",
					"Asserting and verifying Year roof last replaced message on renewal quote");
			buildingPage.yearRoofLastReplacedMessage.scrollToElement();
			Assertions.verify(
					buildingPage.yearRoofLastReplacedMessage.getData().contains(
							"The entire roof must be fully replaced in order to consider an updated roof year"),
					true, "Building Page", "Year roof last replaced message is "
							+ buildingPage.yearRoofLastReplacedMessage.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 32", "Scenario 32 Ended");

			// click on review building
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			waitTime(2);
			Assertions.passTest("Building Page", "Clicked on review building button");

			// Verifying presence of year roof last replaced label and value, when added the
			// year roof last replaced in dwelling page on renewal quote
			Assertions.addInfo("Scenario 33",
					"Verifying presence of year roof last replaced label and value, when added the year roof last replaced on renewal quote");
			Assertions.verify(
					dwellingPage.yearRoofLastReplacedlabel.checkIfElementIsDisplayed()
							&& dwellingPage.yearRoofLastReplacedlabel.getData().contains("Year Roof Last Replaced"),
					true, "Dwelling Page", "Year Roof Last Replaced wording is "
							+ dwellingPage.yearRoofLastReplacedlabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					dwellingPage.roofLastReplacedData.checkIfElementIsDisplayed() && dwellingPage.roofLastReplacedData
							.getData().contains(testData.get("YearRoofLastReplaced")),
					true, "Dwelling Page",
					"Year Roof Last Replaced value is " + dwellingPage.roofLastReplacedData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 33", "Scenario 33 Ended");

			// Click on create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on create quote button");

			// selecting peril
			testData = data.get(dataValue4);
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// Enter quote details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quotw Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view print full quote link successfully");

			// Verifying presence of year roof last replaced, wording and value, when added
			// year roof last replaced on renewal quote
			testData = data.get(dataValue3);
			Assertions.addInfo("Scenario 34",
					"Verifying presence of year roof last replaced, wording and value, when added year roof last replaced on renewal quote");
			Assertions.verify(viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.checkIfElementIsDisplayed()
					&& viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.getData().contains("Year Roof Last Replaced"),
					true, "View Print Full Quote Page", "Year roof last replaced wording is "
							+ viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.getData(),
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.yearRoofLastReplacedValue.formatDynamicPath(1, 1, 2).getData()
							.substring(25, 29).contains(testData.get("YearRoofLastReplaced")), true,
							"View Print Full Quote Page",
							"Year Roof Last Replaced is " + viewOrPrintFullQuotePage.yearRoofLastReplacedValue
									.formatDynamicPath(1, 1, 2).getData().substring(25, 29) + " displayed",
							false, false);
			Assertions.addInfo("Scenario 34", "Scenario 34 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Print Full Quote Page", "Clicked on back button");

			// Click on edit location
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit location link");

			// Click on building link
			buildingPage.buildingLink.formatDynamicPath(locationNumber, buildingNumber).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(locationNumber, buildingNumber).click();

			// click on roof details link
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page is loaded successfully", false, false);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Building Page", "Clicked on roof details link ");

			// Removing year roof last replaced
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.clearData();

			// Click on continue with updated button
			if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
					&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
				buildingPage.continueWithUpdateBtn.scrollToElement();
				buildingPage.continueWithUpdateBtn.click();
				Assertions.passTest("Building Page",
						"Removed Year roof last replaced and click on continue with updated button");
			}

			// click on review building
			buildingPage.waitTime(3);
			buildingPage.scrollToBottomPage();
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on review building button");

			// When year roof last replaced not added the default value is N/A on renewal
			// quote
			testData = data.get(dataValue4);
			Assertions.addInfo("Scenario 35",
					"Verifying absence of year roof last replaced value when year roof last replaced not added on renewal quote");
			Assertions.verify(
					dwellingPage.roofLastReplacedData.checkIfElementIsDisplayed()
							&& dwellingPage.roofLastReplacedData.getData().contains("N/A"),
					true, "Dwelling Page",
					"Year Roof Last Replaced value is " + dwellingPage.roofLastReplacedData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 35", "Scenario 35 Ended");

			// Click on create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on create quote button");

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// Enter quote details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quotw Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view print full quote link successfully");

			// Verifying presence of year roof last replaced, wording and value,when not
			// added year roof last replaced the value of year roof last replaced is same as
			// year built
			// year roof last replaced on renewal quote
			Assertions.addInfo("Scenario 36",
					"Verifying presence of year roof last replaced, wording and value,when not added year roof last replaced the value of year roof last replaced is same as year built");
			Assertions.verify(viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.checkIfElementIsDisplayed()
					&& viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.getData().contains("Year Roof Last Replaced"),
					true, "View Print Full Quote Page", "Year roof last replaced wording is "
							+ viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.getData(),
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.yearRoofLastReplacedValue.formatDynamicPath(1, 1, 2).getData()
							.substring(25, 29).contains(testData.get("L1B1-BldgYearBuilt")), true,
							"View Print Full Quote Page",
							"Year Roof Last Replaced is " + viewOrPrintFullQuotePage.yearRoofLastReplacedValue
									.formatDynamicPath(1, 1, 2).getData().substring(25, 29) + " displayed",
							false, false);
			Assertions.addInfo("Scenario 36", "Scenario 36 Ended");
			// Ticket IO-22035,IO-22093 and IO-22101 ended
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC001 ", "Executed Successfully");
			}
		}
	}
}
