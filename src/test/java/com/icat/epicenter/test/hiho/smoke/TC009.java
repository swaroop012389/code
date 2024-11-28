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
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.test.hiho.AbstractHIHOTest;

public class TC009 extends AbstractHIHOTest {

	public TC009() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/smoke/NBTCID09.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setupData) {
		// Initializing all Objects
		HomePage homePage = new HomePage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		DwellingPage dwellingPage = new DwellingPage();
		OverridePremiumAndFeesPage overridePremAndFeesPage = new OverridePremiumAndFeesPage();
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);

		// Getting Location and Building count
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);

		// Create New Account
		testData = data.get(data_Value1);
		Assertions.passTest("Home Page", "Page Navigated");
		homePage.createNewAccountWithNamedInsured(testData, setupData);
		Assertions.passTest("Home Page", "New Account created successfully");
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

		// Entering Dwelling Details
		for (int j = 1; j <= locationCount; j++) {
			for (int i = 1; i <= dwellingCount; i++) {
				dwellingPage.addDwellingDetails(testData, 1, i);
				dwellingPage.addRoofDetails(testData, 1, i);
				dwellingPage.enterDwellingValues(testData, 1, i);
				if (i < 3) {
					dwellingPage.addSymbol.scrollToElement();
					dwellingPage.addSymbol.click();
					dwellingPage.addNewDwelling.click();
				}
				Assertions.passTest("Dwelling Page",
						("Dwelling " + (i + 1) + " :Dwelling details entered successfully"));
			}
		}

		// Create Quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.getAQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Override Premium and fees page
		// Check Inspection fee and Order Inspection
		accountOverviewPage.overridePremiumLink.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		accountOverviewPage.overridePremiumLink.scrollToElement();
		accountOverviewPage.overridePremiumLink.click();
		Assertions.passTest("Override Premium and Fees Page", "Override Premium and Fees page loaded successfully");
		overridePremAndFeesPage.originalInspectionFee.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		String inspectionfee = overridePremAndFeesPage.originalInspectionFee.getData();
		Assertions.passTest("Override Premium and Fees Page", "Total Inspection Fee is :" + inspectionfee);
		for (int locNo = 1; locNo <= locationCount; locNo++) {
			for (int bldgNo = 1; bldgNo <= dwellingCount; bldgNo++) {
				if (overridePremAndFeesPage.orderInspection.formatDynamicPath(locNo, bldgNo)
						.checkIfElementIsSelected()) {
					Assertions.passTest("Override Premium and Fees Page",
							"Order Inspection is selected by default for " + "Dwelling-" + locNo + "-" + bldgNo);
				} else {
					Assertions.passTest("Override Premium and Fees Page",
							"Order Inspection is not selected by default for " + "Dwelling-" + locNo + "-" + bldgNo);
				}
			}
		}

		overridePremAndFeesPage.cancelButton.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		overridePremAndFeesPage.cancelButton.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		overridePremAndFeesPage.cancelButton.scrollToElement();
		overridePremAndFeesPage.cancelButton.click();
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
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

		Assertions.passTest("Smoke Testing TC009", "Executed Successfully");
	}
}