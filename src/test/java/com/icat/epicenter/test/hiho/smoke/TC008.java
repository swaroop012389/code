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
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EditInspectionContactPage;
import com.icat.epicenter.pom.EditInsuredContactInfoPage;
import com.icat.epicenter.pom.FloodPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.test.hiho.AbstractHIHOTest;
import com.icat.epicenter.utils.TestConstants;

public class TC008 extends AbstractHIHOTest{
	public TC008() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/smoke/NBTCID08.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setupData) {

//	private static final int TIME_OUT_SIXTY_SECS = 60;
//	private static final int TIME_OUT_THIRTY_SECS = 30;
//	private static List<Integer> testStatus;
//
//	public String TC008_Exe(List<HashMap<String, String>> data, HashMap<String, String> setupData, String url) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		DwellingPage dwellingPage = new DwellingPage();
		FloodPage floodPage = new FloodPage();
		EditInsuredContactInfoPage editInsuredContactInfoPage = new EditInsuredContactInfoPage();
		EditAdditionalInterestInformationPage editAdditionalInterestInformationPage = new EditAdditionalInterestInformationPage();
		EditInspectionContactPage editInspectionContactPage1 = new EditInspectionContactPage();
		Map<String, String> testData = data.get(TestConstants.DATA_COL_1);

		// New account
		Assertions.passTest("Home Page", "Page Navigated");
		homePage.createNewAccountWithNamedInsured(testData, setupData);
		Assertions.passTest("Home Page", "New Account created successfully");

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

		// Click on Flood hyperlink
		floodPage.floodLink.click();

		// Entering Flood details
		floodPage.enterFloodDetails(testData);
		Assertions.verify(floodPage.constructionYearError.checkIfElementIsDisplayed(), true, "Flood Page",
				"Error message" + floodPage.constructionYearError.getData(), true, false);
		floodPage.originalYearOfConstruction.scrollToElement();
		floodPage.originalYearOfConstruction.setData(testData.get("L1D1-DwellingYearBuilt"));
		floodPage.reviewButton.click();

		// Click on Create Quote
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
		Assertions.verify(createQuotePage.floodCoverageError.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Error Message :" + createQuotePage.floodCoverageError.getData(), true, false);
		createQuotePage.floodCovC.setData("27999");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.verify(createQuotePage.floodCoverageError.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Error Message :" + createQuotePage.floodCoverageError.getData(), true, false);
		createQuotePage.floodCovC.clearData();
		createQuotePage.floodCovC.tab();
		createQuotePage.floodCovC.scrollToElement();
		createQuotePage.floodCovC.setData("28000");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.getAQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		Assertions.verify(createQuotePage.covDNHWarning.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Verified '" + createQuotePage.covDNHWarning.getData() + "' is Displayed", true, false);
		Assertions.verify(createQuotePage.covDEQWarning.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Verified '" + createQuotePage.covDEQWarning.getData() + "' is Displayed", true, false);
		createQuotePage.goBack.scrollToElement();
		createQuotePage.goBack.click();
		createQuotePage.goBack.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.floodCovC.setData("500001");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.verify(createQuotePage.UWReferralWarning.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Verified '" + createQuotePage.UWReferralWarning.getData() + "' is Displayed", true, false);
		Assertions.verify(createQuotePage.covDNHWarning.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Verified '" + createQuotePage.covDNHWarning.getData() + "' is Displayed", true, false);
		Assertions.verify(createQuotePage.covDEQWarning.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Verified '" + createQuotePage.covDEQWarning.getData() + "' is Displayed", true, false);
		waitTime(5); // to make the script wait till the element is visible
		createQuotePage.continueButton.scrollToElement();
		createQuotePage.continueButton.click();
		createQuotePage.continueButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		Assertions.passTest("Account overview page", "Quote is generated and displayed");
		Assertions.passTest("Account Overview page", "Account Overview page is Displayed");

		// account overview page details
		accountOverviewPage.requestBind.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		testData = data.get(TestConstants.DATA_COL_1);
		Assertions.verify(accountOverviewPage.quoteStatus.getData(), "Active", "Account Overview Page",
				"Quote Status :" + accountOverviewPage.quoteStatus.getData() + "is verified", false, false);
		Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
				"Account Overview Page",
				"Quote Number generated:" + accountOverviewPage.quoteNumber.getData() + "is verified",
				false, false);
		Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Total Premium and Fee Amount present", true, true);
		accountOverviewPage.editInsuredContactInfo.scrollToElement();
		accountOverviewPage.editInsuredContactInfo.click();
		editInsuredContactInfoPage.enterContactInfoDetails(testData);
		accountOverviewPage.editInspectionContact.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		accountOverviewPage.editInspectionContact.click();
		editInspectionContactPage1.cancel.waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
		editInspectionContactPage1.cancel.click();
		editInspectionContactPage1.cancel.waitTillInVisibilityOfElement(TIME_OUT_THIRTY_SECS);
		accountOverviewPage.editAdditionalIntersets.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		accountOverviewPage.editAdditionalIntersets.scrollToElement();
		accountOverviewPage.editAdditionalIntersets.click();
		editAdditionalInterestInformationPage.waitTime(3);
		editAdditionalInterestInformationPage.addAdditionalInterest(testData);
		editAdditionalInterestInformationPage.update.scrollToElement();
		editAdditionalInterestInformationPage.update.click();
		Assertions.verify(editAdditionalInterestInformationPage.aiSecondaryMartgageeError.checkIfElementIsDisplayed(),
				true, "Edit Additional Interest Information Page",
				"Error Message : " + editAdditionalInterestInformationPage.aiSecondaryMartgageeError.getData(), true,
				false);
		editAdditionalInterestInformationPage.cancel.scrollToElement();
		editAdditionalInterestInformationPage.cancel.click();
		editAdditionalInterestInformationPage.cancel.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();
		Assertions.verify(accountOverviewPage.viewPrintFullQuotePage.checkIfElementIsDisplayed(), true,
				"View / Print Full Quote Page", "Page Navigated", true, false);
		accountOverviewPage.goBackBtn.click();
		accountOverviewPage.emailQuoteLink.scrollToElement();
		accountOverviewPage.emailQuoteLink.click();
		Assertions.verify(accountOverviewPage.emailQuotePage.checkIfElementIsDisplayed(), true, "Email Quote Page",
				"Page Navigated", true, false);
		accountOverviewPage.scrollToBottomPage();
		accountOverviewPage.emailCancel.waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
		accountOverviewPage.emailCancel.click();
		accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
		accountOverviewPage.viewOrPrintRateTrace.click();
		Assertions.verify(accountOverviewPage.viewPrintRateTracePage.checkIfElementIsDisplayed(), true,
				"View / Print Rate Trace Page", "Page Navigated", true, false);
		accountOverviewPage.backBtn.click();
		String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
		Assertions.verify(accountOverviewPage.pageName.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Name present", true, true);
		Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Total Premium and Fee Amount present", true, true);
		Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Producer Number present", true, true);
		Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Create Another Quote present", true, true);
		Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Edit Deductibles and limits button present", true, true);
		Assertions.verify(accountOverviewPage.quoteStatus.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote Status present", true, true);
		Assertions.verify(accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Override Premium Link present", true, true);
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

		Assertions.passTest("Smoke Testing TC008", "Executed Successfully");
	}
}