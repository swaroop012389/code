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

public class TC032_NBRE025 extends AbstractNAHOTest {

	public TC032_NBRE025() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE025.xls";
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
					"Home page loaded successfully", false, false);
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
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC032 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC032 ", "Executed Successfully");
			}
		}
	}
}
