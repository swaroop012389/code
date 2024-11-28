/** Program Description: 1. Validating hardstop and referral conditions for dwelling page values as producer
 * 					     2. Adding Ticket IO-20815 (Ordinance or Law Scenario)
 * 	                     3. Adding Ticket IO-21062 - NAHO Referral message for Coverage B includes line break text
 * 	Author			   : Pavan Mule
 *  Date of Creation   : 12/22/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class MDTC002 extends AbstractNAHOTest {

	public MDTC002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/MDTC002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
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

			// Asserting hard stop message when Number of units = 5 on create quote page
			Assertions.addInfo("Scenario 01", "Asserting hard stop message when number of units = 5");
			Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Dwelling page",
					"Number of units hard stop message displayed is " + dwellingPage.protectionClassWarMsg.getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Navigating to dwelling page and updating number of units = 2, construction
			// year = 1969, year of roof last replaced = 2020, year of plumbing = 2020, year
			// of
			// electrical = 2020 and year of HVAC = 2020
			testData = data.get(dataValue2);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page", "Modified the Dwelling details successfully");
			dwellingPage.createQuote.waitTillPresenceOfElement(60);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling page", "Clicked on create quote button successfully");

			// Entering prior loss details
			testData = data.get(dataValue1);
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Clicking on Create Another Quote
			accountOverviewPage.createAnotherQuote.checkIfElementIsPresent();
			accountOverviewPage.createAnotherQuote.click();

			// Adding Ticket IO-20815 Ticket Validation
			// Asserting that the External User does not have an option to select ordinance
			// or law more than 10%, when the YOC is 1970 or Prior
			if (!createQuotePage.ordinanceLawOption.formatDynamicPath("25%").checkIfElementIsPresent()) {
				Assertions.verify(createQuotePage.ordinanceLawOption.formatDynamicPath("10%").checkIfElementIsPresent(),
						true, "Create Quote Page",
						"Per the Ticket IO-20815, for External users there is no Coverage option of 25%", false, false);
			}

			else {
				Assertions.verify(createQuotePage.ordinanceLawOption.formatDynamicPath("25%").checkIfElementIsPresent(),
						true, "Create Quote Page",
						"The Ticket IO-20815 validation failed, there is a Coverage option of 25%", false, false);
			}

			// Updating ordinance law = 10%
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			Assertions.passTest("Create quote page", "Modified the optional coverage details successfully");
			createQuotePage.getAQuote.waitTillPresenceOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("create quote page", "Clicked on get quote successfully");

//		// Asserting hard stop message when year of construction = 1969 and ordinance or law = 25%
//		Assertions.addInfo("Scenario 02",
//				"Asserting hard stop message when year of construction = 1970 and ordinance or law = 25%");
//		Assertions.verify(createQuotePage.errorMessageForYearOfConstruction.checkIfElementIsDisplayed(), true,
//				"Create quote page",
//				"Hard stop message displayed is " + createQuotePage.errorMessageForYearOfConstruction.getData(), false,
//				false);
//		Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Asserting that the External User does not have an option to select ordinance
			// or law more that 10%

//		// Click on previous link on create quote page
//		createQuotePage.previous.waitTillPresenceOfElement(Duration.ofSeconds(TIME_OUT_SIXTY_SECS));
//		createQuotePage.previous.scrollToElement();
//		createQuotePage.previous.click();
//		Assertions.passTest("Create quote page", "Clicked on previous button successfully");

			// Navigating to account overview page and click on edit dwelling link
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editBuilding.waitTillPresenceOfElement(60);
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account overview page", "Clicked on edit building link successfully");

			// Updating construction type = other, year built = 2020 and number of stories =
			// 4
			testData = data.get(dataValue3);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.coverageBOtherStructures.waitTillPresenceOfElement(60);
			dwellingPage.coverageBOtherStructures.waitTillInVisibilityOfElement(60);
			dwellingPage.coverageBOtherStructures.waitTillElementisEnabled(60);
			dwellingPage.coverageBOtherStructures.scrollToElement();
			dwellingPage.coverageBOtherStructures.setData(testData.get("L" + 1 + "D" + 1 + "-DwellingCovB"));
			Assertions.passTest("Dwelling Page", "Modified the Dwelling details successfully");
			dwellingPage.createQuote.waitTillPresenceOfElement(60);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Click on get quote button
			createQuotePage.getAQuote.waitTillPresenceOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("create quote page", "Clicked on get quote successfully");

			// Asserting referral messages when number of stories = more than 3 and
			// construction type
			// = other and Referral Message validation for Ticket IO-21062
			Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer quote page",
					"Refer quote page loaded successfully", false, false);
			Assertions.addInfo("Scenario 03",
					"Asserting referral messages when number of stories = 4 and construction type = other");
			Assertions.verify(referQuotePage.constructionClassOtherReferralMessage.checkIfElementIsDisplayed(), true,
					"Refer quote page",
					"Referral message 1 : " + referQuotePage.constructionClassOtherReferralMessage.getData(), false,
					false);
			Assertions.verify(referQuotePage.noofStoriesReferralMessage.checkIfElementIsDisplayed(), true,
					"Refer quote page", "Referral message 2 : " + referQuotePage.noofStoriesReferralMessage.getData(),
					false, false);
			if (referQuotePage.referralMessages
					.formatDynamicPath("The quoted building has a Coverage B limit greater than 50% of Coverage A")
					.getData().contains("<br />")) {
				Assertions.verify(
						referQuotePage.referralMessages
								.formatDynamicPath(
										"The quoted building has a Coverage B limit greater than 50% of Coverage A")
								.getData().contains("<br />"),
						false, "Refer quote page",
						"Referral message 3 : " + referQuotePage.referralMessages
								.formatDynamicPath(
										"The quoted building has a Coverage B limit greater than 50% of Coverage A")
								.getData(),
						false, false);
			} else {
				Assertions.verify(
						referQuotePage.referralMessages
								.formatDynamicPath(
										"The quoted building has a Coverage B limit greater than 50% of Coverage A")
								.checkIfElementIsDisplayed(),
						true, "Refer quote page",
						"Referral message 3 : " + referQuotePage.referralMessages
								.formatDynamicPath(
										"The quoted building has a Coverage B limit greater than 50% of Coverage A")
								.getData(),
						false, false);
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("MDTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("MDTC002 ", "Executed Successfully");
			}
		}
	}
}
