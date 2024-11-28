/** Program Description: Create an AOP Policy only with/without BPP values and assert presence and default values of EQB, WDR, Ord or Law, Terrorism and coverage package. Also add apc and assert for checkbox to add APC in quote page.
 *  Author			   : John
 *  Date of Creation   : 07/02/2020
**/

package com.icat.epicenter.test.commercial.nb;

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
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class AOPPolicyBPPCheckDefaultsTest extends AbstractCommercialTest {
	public AOPPolicyBPPCheckDefaultsTest() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/nb/NBTCID16.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> testDataSetup) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();

		// Initializing variables
		String quoteNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		final int TIME_OUT_SIXTY_SECS = 60;

		// creating New account
		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home page loaded successfully", false, false);
		homePage.createNewAccountWithNamedInsured(testData, testDataSetup);
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
		createQuotePage.enterDeductiblesCommercial(testData);
		createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
		createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();

		// Asserting presence/absence of optional coverages when building/BPP/BI value
		// is not provided
		scrollToTopPage();
		Assertions.addInfo("Create Quote Page",
				"Asserting presence/absence of optional coverages when building/BPP/BI value is not provided");
		Assertions.verify(
				createQuotePage.includeAPCCheckbox.checkIfElementIsPresent()
						&& createQuotePage.includeAPCCheckbox.checkIfElementIsDisplayed(),
				false, "Create Quote Page", "Include APC checkbox is not displayed when APC is not provided", false,
				false);
		Assertions.verify(
				createQuotePage.windDrivenRainArrow.checkIfElementIsPresent()
						&& createQuotePage.windDrivenRainArrow.checkIfElementIsDisplayed(),
				false, "Create Quote Page", "Wind-Driven Rain is not displayed when BPP value is not provided", false,
				false);

		// Ordinance or Law
		Assertions.verify(
				createQuotePage.ordinanceLawArrow.checkIfElementIsPresent()
						&& createQuotePage.ordinanceLawArrow.checkIfElementIsDisplayed(),
				false, "Create Quote Page", "Ordinance or Law is not displayed when BPP value is not provided", false,
				false);

		// EQB
		Assertions.verify(
				createQuotePage.equipmentBreakdownData.checkIfElementIsPresent()
						&& createQuotePage.equipmentBreakdownData.checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Equipment BreakDown is present when BPP value is not provided and default value is "
						+ createQuotePage.equipmentBreakdownData.getData(),
				false, false);

		// Terrorism
		Assertions.verify(
				createQuotePage.terrorismData.checkIfElementIsPresent()
						&& createQuotePage.terrorismData.checkIfElementIsDisplayed(),
				true, "Create Quote Page", "Terrorism is present when BPP value is not provided and default value is "
						+ createQuotePage.terrorismData.getData(),
				false, false);

		// Coverage Package
		Assertions.verify(
				createQuotePage.coverageExtensionPackageData.checkIfElementIsPresent()
						&& createQuotePage.coverageExtensionPackageData.checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Coverage Extension is present when BPP value is not provided and default value is "
						+ createQuotePage.coverageExtensionPackageData.getData(),
				false, false);

		// Adding Bpp value
		scrollToBottomPage();
		createQuotePage.bPPValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.bPPValue.formatDynamicPath(0, 0).scrollToElement();
		createQuotePage.bPPValue.formatDynamicPath(0, 0).clearData();
		createQuotePage.bPPValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgBPP"));
		createQuotePage.bPPValue.formatDynamicPath(0, 0).tab();
		Assertions.passTest("Create Quote Page", "BPP Value is added");

		// Asserting presence/absence of optional coverages when only BPP value is
		// provided
		scrollToTopPage();
		Assertions.addInfo("Create Quote Page",
				"Asserting presence/absence of optional coverages when only BPP value is provided");
		Assertions.verify(
				createQuotePage.windDrivenRainArrow.checkIfElementIsPresent()
						&& createQuotePage.windDrivenRainArrow.checkIfElementIsDisplayed(),
				false, "Create Quote Page", "Wind-Driven Rain is not displayed when only BPP value is provided", false,
				false);

		// Ordinance or Law
		Assertions.verify(
				createQuotePage.ordinanceLawArrow.checkIfElementIsPresent()
						&& createQuotePage.ordinanceLawArrow.checkIfElementIsDisplayed(),
				false, "Create Quote Page", "Ordinance or Law is not displayed when only BPP value is provided", false,
				false);

		// EQB
		Assertions.verify(
				createQuotePage.equipmentBreakdownData.checkIfElementIsPresent()
						&& createQuotePage.equipmentBreakdownData.checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Equipment BreakDown is present when only BPP value is provided and default value is "
						+ createQuotePage.equipmentBreakdownData.getData(),
				false, false);

		// Terrorism
		Assertions.verify(
				createQuotePage.terrorismData.checkIfElementIsPresent()
						&& createQuotePage.terrorismData.checkIfElementIsDisplayed(),
				true, "Create Quote Page", "Terrorism is present when only BPP value is provided and default value is "
						+ createQuotePage.terrorismData.getData(),
				false, false);

		// Coverage Package
		Assertions.verify(
				createQuotePage.coverageExtensionPackageData.checkIfElementIsPresent()
						&& createQuotePage.coverageExtensionPackageData.checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Coverage Extension is present when only BPP value is provided and default value is "
						+ createQuotePage.coverageExtensionPackageData.getData(),
				false, false);

		// Adding BI and EQ values
		scrollToBottomPage();
		createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
		createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
		createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
		createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
		Assertions.passTest("Create Quote Page", "Building value is added");

		// Click on previous button
		createQuotePage.previous.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.previous.scrollToElement();
		createQuotePage.previous.click();

		// Click on edit location
		accountOverviewPage.editLocation.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		accountOverviewPage.editLocation.scrollToElement();
		accountOverviewPage.editLocation.click();

		// Add APC values in Location page and create quote
		testData = data.get(data_Value2);
		Assertions.passTest("Location Page", "Location Page Loaded successfully");
		locationPage.addAPC(testData, 1);
		Assertions.passTest("Location Page", "APC values added successfully");
		locationPage.reviewLocation.scrollToElement();
		locationPage.reviewLocation.click();
		locationPage.createQuoteButton.scrollToElement();
		locationPage.createQuoteButton.click();

		// Select peril
		testData = data.get(data_Value1);
		selectPerilPage.selectPeril(testData.get("Peril"));

		// Asseritng include APC checkbox
		Assertions.addInfo("Create Quote Page",
				"Asserting Include APC checkbox is present and selected by default when APC is provided");
		Assertions.verify(createQuotePage.includeAPCCheckbox.formatDynamicPath("1").checkIfElementIsSelected(), true,
				"Create Quote Page", "Include APC checkbox is present and selected by default when APC is provided",
				false, false);

		// Enter quote details
		createQuotePage.enterDeductiblesCommercial(testData);

		// Create Quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.continueButton.checkIfElementIsPresent()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			createQuotePage.continueButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		}

		// getting the quote number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page loaded successfully", false, false);
		quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		// Sign out
		Assertions.passTest("Commercial NBRegression Test Case 16", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
	}

}
