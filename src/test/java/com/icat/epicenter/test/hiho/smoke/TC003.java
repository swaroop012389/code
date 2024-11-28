
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
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.FloodPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.hiho.AbstractHIHOTest;
import com.icat.epicenter.utils.TestConstants;

public class TC003 extends AbstractHIHOTest {

	public TC003() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/smoke/NBTCID03.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setupData) {
		// Initializing all Objects
		HomePage homePage = new HomePage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		FloodPage floodPage = new FloodPage();
		DwellingPage dwellingPage = new DwellingPage();
		RequestBindPage requestBindPage = new RequestBindPage();
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

		// Verification in Dwelling Page
		Assertions.verify(dwellingPage.dwellingType.getData(),
				testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingType"), "Dwelling Page",
				"Dwelling Type" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.covC.getData(),
				"$" + testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovC"), "Dwelling Page",
				"Coverage C" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.covD.getData(),
				"$" + testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovD"), "Dwelling Page",
				"Coverage D" + TestConstants.EXT_RPT_MSG, false, false);
		floodPage.floodLink.click();
		floodPage.enterFloodDetails(testData);

		// Click on Create Quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		Assertions.verify(createQuotePage.moldCleanup.getData(), testData.get("Mold"), "Create Quote Page",
				"Mold Clean Up default value" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(createQuotePage.moldCleanup.getAttrributeValue("unselectable"), "on", "Create Quote Page",
				"Mold Clean Up is unchangeable" + TestConstants.EXT_RPT_MSG, false, false);

		// Verifying Create quote page Details
		Assertions.verify(createQuotePage.namedHurricaneValue.getData(), testData.get("NamedHurricaneDedValue"),
				"Create Quote Page", "Named Hurricane" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"EQ Deductible" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(createQuotePage.flood.checkIfElementIsPresent(), false, "Create Quote Page",
				"Flood not displayed" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(createQuotePage.personalPropertyReplacementCostData.getData(),
				testData.get("ReplacementCost"), "Create Quote Page",
				"Personal Property Replacement Cost" + TestConstants.EXT_RPT_MSG, false, false);
		createQuotePage.floodGrid3.scrollToElement();
		createQuotePage.floodGrid3.setData(testData.get("L1D1-DwellingCovC"));
		createQuotePage.floodGrid3.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.getAQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Clicking on Moratorium Continue Button
		if(createQuotePage.continueButton.checkIfElementIsPresent() && createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Fetching Quote Number
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
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Bind details page", "Navigated to bind details page");
		String year1 = testData.get("L1D1-DwellingYearBuilt");
		int yearBuilt = Integer.parseInt(year1);
		int yearValidation = yearBuilt - 5;
		if (testData.get("L1D1-InspfromPrimaryCarrier").equalsIgnoreCase("No")
				&& (testData.get("L1D1-DwellingType").equalsIgnoreCase("Dwelling"))
				&& ((yearValidation + 5 >= yearBuilt))) {
			Assertions.verify(requestBindPage.selfInspection.checkIfElementIsPresent(), true, "Request Bind Page",
					"Self Inspection checkbox is present", true, true);
		} else {
			Assertions.verify(!requestBindPage.selfInspection.checkIfElementIsPresent(), true, "Request Bind Page",
					"Self Inspection checkbox is not present", true, true);
		}
		requestBindPage.cancel.scrollToElement();
		requestBindPage.cancel.click();
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

		Assertions.passTest("Smoke Testing TC003", "Executed Successfully");
	}
}