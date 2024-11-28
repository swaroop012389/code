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
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.test.hiho.AbstractHIHOTest;
import com.icat.epicenter.utils.TestConstants;

public class TC007 extends AbstractHIHOTest {

	public TC007() {
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
		DwellingPage dwellingPage = new DwellingPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();

		// New Account
		Map<String, String> testData = data.get(TestConstants.DATA_COL_1);
		homePage.createNewAccountWithNamedInsured(testData, setupData);
		Assertions.passTest("Home Page", "Page Navigated");
		Assertions.passTest("Home Page", "New Account created successfully");
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

		// Entering Dwelling Details
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.addRoofDetails(testData, 1, 1);
		dwellingPage.enterDwellingValues(testData, 1, 1);

		// Review Dwelling
		dwellingPage.reviewDwelling.click();
		dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

		// Click on Create Quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		Assertions.verify(createQuotePage.ordinanceLaw.checkIfElementIsPresent(), false, "Create Quote Page",
				"Ordinance and Law field is not present" + TestConstants.EXT_RPT_MSG, false, false);
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.getAQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Clicking on Continue
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Fetching Quote Number
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		// Account Overview Page Verifications
		Assertions.verify(accountOverviewPage.pageName.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Name present", true, true);
		Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Total Premium and Fee Amount present", true, true);
		Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Producer Number present", true, true);
		Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Create Another Quote present", true, true);
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

		Assertions.passTest("Smoke Testing TC007", "Executed Successfully");
	}
}