/** Program Description: NAHO IS validation - Change to Producer when carrier is available
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
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;
import com.icat.epicenter.utils.TestConstants;

public class TC001 extends AbstractNAHOTest {

	public TC001() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/smoke/NB004.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setupData) {

		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		LoginPage loginPage = new LoginPage();

		// initializing variables
		Map<String, String> testData = data.get(TestConstants.DATA_COL_1);
		int quoteLength;
		String quoteNumber;

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
			Assertions.passTest("Prior Loss Page", "Details Entered successfully");
		}

		// Entering Quote Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);

		// Asserting AOP deductible default value
		createQuotePage.aopDedValue.waitTillVisibilityOfElement(60);
		createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

		// Click on Continue
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Asserting quote number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLength = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLength - 1);
		Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

		// Asserting links on account overview page
		Assertions.verify(
				accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed()
						&& accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsEnabled(),
				true, "Account Overview Page", "View/Print full quote Link displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.emailQuoteLink.checkIfElementIsDisplayed()
						&& accountOverviewPage.emailQuoteLink.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Email Link displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed()
						&& accountOverviewPage.overridePremiumLink.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Override Premium Link displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.viewOrPrintRateTrace.checkIfElementIsDisplayed()
						&& accountOverviewPage.viewOrPrintRateTrace.checkIfElementIsEnabled(),
				true, "Account Overview Page", "View/Print Rate Trace Link displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.viewModelResultsLink.checkIfElementIsDisplayed()
						&& accountOverviewPage.viewModelResultsLink.checkIfElementIsEnabled(),
				true, "Account Overview Page", "View Model Results Link displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed()
						&& accountOverviewPage.createAnotherQuote.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Create Another Quote button displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.deleteAccount.checkIfElementIsDisplayed()
						&& accountOverviewPage.deleteAccount.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Delete Account button displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.editInsuredContactInfo.checkIfElementIsDisplayed()
						&& accountOverviewPage.editInsuredContactInfo.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Insured Link displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.editInspectionContact.checkIfElementIsDisplayed()
						&& accountOverviewPage.editInspectionContact.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Inspection Link displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.editAdditionalIntersets.checkIfElementIsDisplayed()
						&& accountOverviewPage.editAdditionalIntersets.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Additional Interest Link displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.priorLossEditLink.checkIfElementIsDisplayed()
						&& accountOverviewPage.priorLossEditLink.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Prior Loss Link displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.editDwelling.checkIfElementIsDisplayed()
						&& accountOverviewPage.editDwelling.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Edit Dwelling Link displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.deleteQuote.checkIfElementIsDisplayed()
						&& accountOverviewPage.deleteQuote.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Delete Quote Link displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.quoteSpecifics.checkIfElementIsDisplayed()
						&& accountOverviewPage.quoteSpecifics.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Quote Specifics Link displayed and enabled", false, false);
		Assertions.verify(
				accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed()
						&& accountOverviewPage.editDeductibleAndLimits.checkIfElementIsEnabled(),
				true, "Account Overview Page", "Edit Deductible And Limits Button displayed and enabled", false, false);

/*		// Log out and back in as the Producer
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();

		loginPage.enterLoginDetails("testing@qa-external.com", "L1ghthouse");

		homePage.searchQuoteByProducer(quoteNumber);

		// Edit the Dwelling to have 4 stories
		accountOverviewPage.editDwelling.waitTillVisibilityOfElement(10);
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();

		dwellingPage.address.waitTillVisibilityOfElement(10);

		try {
			dwellingPage.numOfFloors.setData("4");
		}
		catch (Throwable t) {
			waitTime(1);
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

				dwellingPage.numOfFloors.setData("4");
			}
		}

		if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
			dwellingPage.expiredQuotePopUp.scrollToElement();
			dwellingPage.continueWithUpdateBtn.scrollToElement();
			dwellingPage.continueWithUpdateBtn.click();
			dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		}
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		if (buildingNoLongerQuoteablePage.okButton.checkIfElementIsPresent()) {
			buildingNoLongerQuoteablePage.okButton.click();
		}

		// Create a new quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Referral page
		referQuotePage.contactEmail.setData("tfodor@icat.com");
		referQuotePage.referQuote.click();

		// Get referred quote number and go to the Homepage
		String referredQuoteNum = referQuotePage.quoteNumberforReferral.getData();
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.signOutButton.click();

		// Log in as USM
		loginPage.enterLoginDetails("tfodor", "L1ghthouse");

		// Find the referred quote and open the referral
		homePage.searchQuote(referredQuoteNum);
		accountOverviewPage.openReferral.click();

		Assertions.verify(referralPage.assignmentRow.getAttrributeValue("text").contains("Sean Minn"),
				true, "Check Quote Referral Assignment", "Quote Referral assigned to Sean Minn", false, false);
		referralPage.close.click();

		// Find the account by quote number
		homePage.searchQuote(referredQuoteNum);
*/
		// Delete account
		accountOverviewPage.deleteAccount.scrollToElement();
		accountOverviewPage.deleteAccount.click();
		accountOverviewPage.yesDeleteAccount.scrollToElement();
		accountOverviewPage.yesDeleteAccount.click();

		// Asserting account deleted message
		Assertions.verify(homePage.accountSuccessfullyDeletedMsg.checkIfElementIsDisplayed(), true, "Home page",
				homePage.accountSuccessfullyDeletedMsg.getData(), false, false);

		Assertions.passTest("NAHO Production Test TC001", "Executed Successfully");
	}

}