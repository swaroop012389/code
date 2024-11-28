/** Program Description: NAHO ESB validations
 *  Author			   : SMNetserv
 *  Date of Creation   : 03/05/2023
 **/

package com.icat.epicenter.test.naho.release;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EmailQuotePage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;
import com.icat.epicenter.utils.TestConstants;

public class TC002 extends AbstractNAHOTest {

	public TC002() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/smoke/NB005.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setupData) {
		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		EmailQuotePage emailQuotePage = new EmailQuotePage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();

		// Initializing the variables
		String quoteNumber;
		Map<String, String> testData = data.get(TestConstants.DATA_COL_1);

		// Creating New Account
		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home Page loaded successfully", false, false);
		homePage.createNewAccountWithNamedInsured(testData, setupData);
		Assertions.passTest("New Account", "New Account Created successfully");

		// Entering Zipcode
		Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
				"Eligibility Page loaded successfully", false, false);
		eligibilityPage.processSingleZip(testData);
		Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
				"Dwelling Page Loaded successfully", false, false);
		dwellingPage.enterDwellingDetailsNAHO(testData);
		Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

		// Entering prior loss details
		if (!testData.get("PriorLoss1").equals("")) {
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
		}

		// Entering Quote Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		createQuotePage.enterQuoteDetailsNAHO(testData);
		Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		int quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

		// Asserting viewPrintFullQuote Link
		Assertions.verify(
				accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed()
						&& accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsEnabled(),
				true, "Account Overview Page", "View/Print full quote Link displayed and enabled", false, false);
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();
		viewOrPrintFullQuotePage.backButton.scrollToElement();
		viewOrPrintFullQuotePage.backButton.click();

		// Asserting Email Link
		Assertions.verify(
				accountOverviewPage.emailQuoteLink.checkIfElementIsDisplayed()
						&& accountOverviewPage.emailQuoteLink.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Email Link displayed and enabled", false, false);
		accountOverviewPage.emailQuoteLink.scrollToElement();
		accountOverviewPage.emailQuoteLink.click();
		emailQuotePage.cancel.scrollToElement();
		emailQuotePage.cancel.click();

		// Asserting OverridePremium Link
		Assertions.verify(
				accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed()
						&& accountOverviewPage.overridePremiumLink.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Override Premium Link displayed and enabled", false, false);
		accountOverviewPage.overridePremiumLink.scrollToElement();
		accountOverviewPage.overridePremiumLink.click();
		overridePremiumAndFeesPage.cancelButton.scrollToElement();
		overridePremiumAndFeesPage.cancelButton.click();

		// Asserting viewOrPrintRateTrace Link
		Assertions.verify(
				accountOverviewPage.viewOrPrintRateTrace.checkIfElementIsDisplayed()
						&& accountOverviewPage.viewOrPrintRateTrace.checkIfElementIsEnabled(),
				true, "Account Overview Page", "View/Print Rate Trace Link displayed and enabled", false, false);
		accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
		accountOverviewPage.viewOrPrintRateTrace.click();
		viewOrPrintRateTrace.backBtn.scrollToElement();
		viewOrPrintRateTrace.backBtn.click();

		// Asserting viewModelResults Link
		Assertions.verify(
				accountOverviewPage.viewModelResultsLink.checkIfElementIsDisplayed()
						&& accountOverviewPage.viewModelResultsLink.checkIfElementIsEnabled(),
				true, "Account Overview Page", "View Model Results Link displayed and enabled", false, false);

		// Asserting producer Link
		Assertions.verify(
				accountOverviewPage.producerLink.checkIfElementIsDisplayed()
						&& accountOverviewPage.producerLink.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Producer Link displayed and enabled", false, false);

		// Asserting editInsuredContact Link
		Assertions.verify(
				accountOverviewPage.editInsuredContactInfo.checkIfElementIsDisplayed()
						&& accountOverviewPage.editInsuredContactInfo.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Insured Link displayed and enabled", false, false);

		// Asserting editInspectionContact Link
		Assertions.verify(
				accountOverviewPage.editInspectionContact.checkIfElementIsDisplayed()
						&& accountOverviewPage.editInspectionContact.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Inspection Link displayed and enabled", false, false);

		// Asserting editAdditionalInterests Link
		Assertions.verify(
				accountOverviewPage.editAdditionalIntersets.checkIfElementIsDisplayed()
						&& accountOverviewPage.editAdditionalIntersets.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Additional Interest Link displayed and enabled", false, false);

		// Asserting priorLossEdit Link
		Assertions.verify(
				accountOverviewPage.priorLossEditLink.checkIfElementIsDisplayed()
						&& accountOverviewPage.priorLossEditLink.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Prior Loss Link displayed and enabled", false, false);

		// Asserting editDwelling Link
		Assertions.verify(
				accountOverviewPage.editDwelling.checkIfElementIsDisplayed()
						&& accountOverviewPage.editDwelling.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Edit Dwelling Link displayed and enabled", false, false);

		// Asserting deleteQuote Link
		Assertions.verify(
				accountOverviewPage.deleteQuote.checkIfElementIsDisplayed()
						&& accountOverviewPage.deleteQuote.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Delete Quote Link displayed and enabled", false, false);
		accountOverviewPage.deleteQuote.scrollToElement();
		accountOverviewPage.deleteQuote.click();
		accountOverviewPage.yesDeletePopup.scrollToElement();
		accountOverviewPage.yesDeletePopup.click();

		// Asserting quote deleted message
		Assertions.verify(
				accountOverviewPage.dwellingSuccessfullyDeletedMsg.checkIfElementIsPresent()
						&& accountOverviewPage.dwellingSuccessfullyDeletedMsg.checkIfElementIsDisplayed(),
				true, "Account Overview Page",
				accountOverviewPage.dwellingSuccessfullyDeletedMsg.getData() + " is displayed", false, false);

		// Asserting createAnotherquote Link
		Assertions.verify(
				accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed()
						&& accountOverviewPage.createAnotherQuote.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Create Another Quote button displayed and enabled", false, false);
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();

		// Click on override
		if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
				&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
			buildingNoLongerQuoteablePage.override.scrollToElement();
			buildingNoLongerQuoteablePage.override.click();
		}

		// Click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Asserting Quotespecifics Link
		Assertions.verify(
				accountOverviewPage.quoteSpecifics.checkIfElementIsDisplayed()
						&& accountOverviewPage.quoteSpecifics.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Quote Specifics Link displayed and enabled", false, false);
		accountOverviewPage.quoteSpecifics.scrollToElement();
		accountOverviewPage.quoteSpecifics.click();

		// Asserting editDeductiblesandLimits Link
		Assertions.verify(
				accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed()
						&& accountOverviewPage.editDeductibleAndLimits.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Edit Deductible And Limits Button displayed and enabled", false, false);
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();

		// Click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Asserting requestbind Link
		Assertions.verify(
				accountOverviewPage.requestBind.checkIfElementIsDisplayed()
						&& accountOverviewPage.requestBind.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Request Bind Button displayed and enabled", false, false);
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();

		// Click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Delete account
		Assertions.verify(
				accountOverviewPage.deleteAccount.checkIfElementIsDisplayed()
						&& accountOverviewPage.deleteAccount.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Delete Account button displayed and enabled", false, false);
		accountOverviewPage.deleteAccount.scrollToElement();
		accountOverviewPage.deleteAccount.click();
		accountOverviewPage.yesDeleteAccount.scrollToElement();
		accountOverviewPage.yesDeleteAccount.click();

		// Asserting account deleted message
		Assertions.verify(homePage.accountSuccessfullyDeletedMsg.checkIfElementIsDisplayed(), true, "Home page",
				homePage.accountSuccessfullyDeletedMsg.getData(), false, false);

		Assertions.passTest("NAHO Production Test TC002", "Executed Successfully");
	}
}