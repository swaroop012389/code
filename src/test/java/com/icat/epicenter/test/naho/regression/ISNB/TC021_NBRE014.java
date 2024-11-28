package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC021_NBRE014 extends AbstractNAHOTest {

	public TC021_NBRE014() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE014.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		int dataValue1 = 0;

		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			testData = data.get(dataValue1);
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);

			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.quoteName.setData(testData.get("QuoteName"));
			Assertions.passTest("Create Quote Page", "Quote name Entered is " + testData.get("QuoteName"));
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// IO-20816
			// Asserting Hard stop message when year building 40 years and older with no
			// update since last 15 years, hard stop message is"The account is ineligible
			// due to the quoted building being 40 years or older with no updates in the
			// last 15 years."
			Assertions.addInfo("Scenario 01",
					"Verifying and Asserting hard stop message when building 40 years and older");
			Assertions.verify(createQuotePage.warningMessages
					.formatDynamicPath("building being 40 years or older with no updates in the last 15 years")
					.getData().contains("building being 40 years or older with no updates in the last 15 years"), true,
					"Dwelling Page",
					"The hard stop message " + createQuotePage.warningMessages
							.formatDynamicPath("building being 40 years or older with no updates in the last 15 years")
							.getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Ended");

			// Verifying Hard Stop message as producer when prior loss selected as
			// liability, open claim = yes, unrepaired damage = No
			// Hard stop message The account is ineligible due to an open claim.
			// The account is ineligible due to the prior liability loss.
			// The account is ineligible due to unrepaired damage.
			Assertions.addInfo("Scenario 02", "Asserting and Validating hard stop message");
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("prior liability loss").getData().contains("prior liability loss"),
					true, "Create Quote Page",
					"The Hard Stop Message is "
							+ createQuotePage.warningMessages.formatDynamicPath("prior liability loss").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("due to an open claim").getData().contains("due to an open claim"),
					true, "Create Quote Page",
					"The Hard Stop Message is "
							+ createQuotePage.warningMessages.formatDynamicPath("due to an open claim").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(createQuotePage.warningMessages
					.formatDynamicPath("due to unrepaired damage").getData().contains("due to unrepaired damage"), true,
					"Create Quote Page",
					"The Hard Stop Message is "
							+ createQuotePage.warningMessages.formatDynamicPath("due to unrepaired damage").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Ended");

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC021 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC021 ", "Executed Successfully");
			}
		}
	}
}
