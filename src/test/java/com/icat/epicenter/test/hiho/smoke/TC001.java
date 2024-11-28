/** Program Description:
 *  Author			   : SM Netserv
 *  Date of Creation   : May 2023
 **/

package com.icat.epicenter.test.hiho.smoke;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.test.hiho.AbstractHIHOTest;
import com.icat.epicenter.utils.TestConstants;

public class TC001 extends AbstractHIHOTest {

	public TC001() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/smoke/NBTCID01.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setupData) {
		// Initializing all Objects
		HomePage homePage = new HomePage();
		BuildingUnderMinimumCostPage dwellingCost = new BuildingUnderMinimumCostPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		DwellingPage dwellingPage = new DwellingPage();
		Map<String, String> testData = data.get(0);
		String namedInsured = testData.get("InsuredName");

		// Getting Location and Building count
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);

		// New account
		Assertions.passTest("Home Page", "Page Navigated");
		Assertions.passTest("Home Page", "New Account created successfully");
		homePage.createNewAccountWithNamedInsured(testData, setupData, true);
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

		// Fetching Named Insured Value for ticket IO-20806
		dwellingPage.niDisplay.checkIfElementIsPresent();
		String niDwellingPage = dwellingPage.niDisplay.getData().substring(0, 20);

		// Verifying the NI value does not have Format Characters
		if (niDwellingPage.equalsIgnoreCase(namedInsured)) {
			Assertions.verify(niDwellingPage, namedInsured, "Dwelling Page",
					"The NI Display does not show Format Charachters", false, false);
		} else {
			Assertions.verify(niDwellingPage, namedInsured, "Dwelling Page",
					"The NI Display contains show Format Charachters", false, false);
		}

		// Entering Dwelling Details
		dwellingPage.addDwellingDetails(testData, locationCount, dwellingCount);
		dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
		dwellingPage.enterDwellingValues(testData, locationCount, dwellingCount);

		// Review Dwelling
		dwellingPage.reviewDwelling.click();
		dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Asserting warning messge for course of construction
		Assertions.verify(dwellingPage.cOCWarningMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
				"Course Of Construction Warning message " + dwellingPage.cOCWarningMsg.getData() + " is displayed",
				false, false);

		// Update year built to current
		dwellingPage.dwellingCharacteristicsLink.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.dwellingCharacteristicsLink.scrollToElement();
		dwellingPage.dwellingCharacteristicsLink.click();
		testData = data.get(1);
		dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));

		// Click on create quote button
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");
		dwellingPage.scrollToBottomPage();
		waitTime(2); // to overcome elementclickinterceptedexception
		dwellingPage.reSubmit.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.reSubmit.scrollToElement();
		dwellingPage.reSubmit.click();

		// Click on Override
		dwellingCost.clickOnOverride();

		// Verification in Dwelling Page
		Assertions.verify(
				dwellingPage.dwellingType.getData()
						.contains(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingType")),
				true, "Dwelling Page", "Dwelling Type" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(
				dwellingPage.covA.getData()
						.contains(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovA")),
				true, "Dwelling Page", "Coverage A" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(
				dwellingPage.covB.getData()
						.contains(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovB")),
				true, "Dwelling Page", "Coverage B" + TestConstants.EXT_RPT_MSG, false, false);

		// Click on Create Quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		testData = data.get(0);

		// Fetching niDisplay
		createQuotePage.niDisplay.checkIfElementIsPresent();
		String niCreateQuotePage = createQuotePage.niDisplay.getData().substring(0, 20);

		// Verifying the NI value does not have Format Characters
		if (niCreateQuotePage.equalsIgnoreCase(namedInsured)) {
			Assertions.verify(niCreateQuotePage, namedInsured, "Create Quote Page",
					"The NI Display does not show Format Charachters", false, false);
		} else {
			Assertions.verify(niCreateQuotePage, namedInsured, "Create Quote Page",
					"The NI Display contains show Format Charachters", false, false);
		}

		// Entering Create quote page Details
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Verifying Create quote page Details
		Assertions.verify(createQuotePage.namedHurricaneValue.getData(), testData.get("NamedHurricaneDedValue"),
				"Create Quote Page", "Named Hurricane" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"EQ Deductible" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(createQuotePage.flood.checkIfElementIsPresent(), false, "Create Quote Page",
				"Flood not displayed" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(
				createQuotePage.ordinanceLaw.formatDynamicPath(testData.get("OrdinanceOrLaw")).getData()
						.contains(testData.get("OrdinanceOrLaw")),
				true, "Create Quote Page", "Ordinance Or Law" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(createQuotePage.moldCleanup.getData().contains(testData.get("Mold")), true,
				"Create Quote Page", "Mold Clean Up default value" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(createQuotePage.moldCleanup.getAttrributeValue("unselectable"), "on", "Create Quote Page",
				"Mold Clean Up is unchangeable" + TestConstants.EXT_RPT_MSG, false, false);

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.getAQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Click on Override
		dwellingCost.clickOnOverride();

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Verifying the NI Display in the Account Overview Page does not contain Format
		// Characters
		accountOverviewPage.niDisplay.checkIfElementIsPresent();
		String niAccountOverviewPage = accountOverviewPage.niDisplay.getData().substring(0, 20);
		if (niAccountOverviewPage.equalsIgnoreCase(namedInsured)) {
			Assertions.verify(niAccountOverviewPage, namedInsured, "Account Overview Page",
					"The NI Display does not show Format Charachters", false, false);
		} else {
			Assertions.verify(niAccountOverviewPage, namedInsured, "Account Overview Page",
					"The NI Display contains show Format Charachters", false, false);
		}

		String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
		Assertions.verify(accountOverviewPage.niDisplay.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Name present", true, true);
		Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Total Premium and Fee Amount present", true, true);
		Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Producer Number present", true, true);
		if (locationCount >= 2) {
			Assertions.verify(accountOverviewPage.quoteSomeDwellingsButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Create Another Quote present", true, true);
		} else {
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Create Another Quote present", true, true);
		}
		Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Edit Deductibles and limits button present", true, true);
		Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("1").checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote Status present", true, true);
		Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "View/Print Full Quote Link present", true, true);
		Assertions.verify(accountOverviewPage.emailQuoteLink.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Email Quote Link present", true, true);
		Assertions.verify(accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Override Premium Link present", true, true);
		Assertions.verify(accountOverviewPage.viewOrPrintRateTrace.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "View/Print Rate Trace Link present", true, true);
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Bind button present is verified", true, true);

		// Adding Code for IO-19410
		// Asserting Other Deductible Options table Total Premium values
		accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(2).waitTillVisibilityOfElement(60);
		Assertions
				.verify(accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(2).checkIfElementIsDisplayed(),
						true, "Account Overview Page",
						"Quote Options Total Premium 1 is displayed successfully : "
								+ accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(2).getData(),
						false, false);
		Assertions
				.verify(accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(3).checkIfElementIsDisplayed(),
						true, "Account Overview Page",
						"Quote Options Total Premium 2 is displayed successfully : "
								+ accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(3).getData(),
						false, false);
		Assertions
				.verify(accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(4).checkIfElementIsDisplayed(),
						true, "Account Overview Page",
						"Quote Options Total Premium 3 is displayed successfully : "
								+ accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(4).getData(),
						false, false);
		Assertions
				.verify(accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(5).checkIfElementIsDisplayed(),
						true, "Account Overview Page",
						"Quote Options Total Premium 4 is displayed successfully : "
								+ accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(5).getData(),
						false, false);

		accountOverviewPage.deleteAccount.scrollToElement();
		accountOverviewPage.deleteAccount.click();
		waitTime(5); // To avoid clicking on Request Bind
		accountOverviewPage.yesDeletePopup.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		accountOverviewPage.yesDeletePopup.scrollToElement();
		accountOverviewPage.yesDeletePopup.click();
		Assertions.passTest("Account Overview Page", "Account deleted successfully");

		// Signing out
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		Assertions.passTest("Smoke Testing TC001", "Executed Successfully");
//		if (testStatus.get(1) > 0) {
//			return "FAIL";
//		} else {
//			return "PASS";
//		}
	}
}