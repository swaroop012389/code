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

public class TC002 extends AbstractHIHOTest {

	public TC002() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/smoke/NBTCID02.xls";
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

		// Getting Location and Building count
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);

		// New account
		Assertions.passTest("Home Page", "Page Navigated");
		Assertions.passTest("Home Page", "New Account created successfully");
		homePage.createNewAccountWithNamedInsured(testData, setupData);
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

		// Entering Dwelling Details
		dwellingPage.addDwellingDetails(testData, locationCount, dwellingCount);
		dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
		dwellingPage.enterDwellingValues(testData, locationCount, dwellingCount);

		// Review Dwelling
		dwellingPage.reviewDwelling.click();
		dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

		// Click on Override
		dwellingCost.clickOnOverride();

		// Verification in Dwelling Page
		Assertions.verify(dwellingPage.dwellingType.getData(),
				testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingType"), "Dwelling Page",
				"Dwelling Type" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.covA.getData(),
				"$" + testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovA"), "Dwelling Page",
				"Coverage A" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.covB.getData(),
				"$" + testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovB"), "Dwelling Page",
				"Coverage B" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.covC.getData(),
				"$" + testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovC"), "Dwelling Page",
				"Coverage C" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.covD.getData(),
				"$" + testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovD"), "Dwelling Page",
				"Coverage D" + TestConstants.EXT_RPT_MSG, false, false);

		// Click on Create Quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
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

		// Click on continue
		waitTime(5); // to make the script wait till the element is visible
		createQuotePage.continueButton.scrollToElement();
		createQuotePage.continueButton.click();
		createQuotePage.continueButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
		Assertions.verify(accountOverviewPage.pageName.checkIfElementIsDisplayed(), true, "Account Overview Page",
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
		Assertions.verify(accountOverviewPage.quoteStatus.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote Status present", true, true);
		Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "View/Print Full Quote Link present", true, true);
		Assertions.verify(accountOverviewPage.emailQuoteLink.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Email Quote Link present", true, true);
		Assertions.verify(accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Override Premium Link present", true, true);
		Assertions.verify(accountOverviewPage.viewOrPrintRateTrace.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "View/Print Rate Trace Link present", true, true);
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Bind button present is verified", true, true);
		accountOverviewPage.deleteAccount.scrollToElement();
		accountOverviewPage.deleteAccount.click();
		accountOverviewPage.yesDeletePopup.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		accountOverviewPage.yesDeletePopup.scrollToElement();
		accountOverviewPage.yesDeletePopup.click();
		Assertions.passTest("Account Overview Page", "Account deleted successfully");

		// Signing out
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		Assertions.passTest("Smoke Testing TC002", "Executed Successfully");
//		if (testStatus.get(1) > 0) {
//			return "FAIL";
//		} else {
//			return "PASS";
//		}
	}
}