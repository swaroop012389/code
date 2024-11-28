package com.icat.epicenter.test.naho.regression.ISNB;

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
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC049_NBLH001 extends AbstractNAHOTest {

	public TC049_NBLH001() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBLH001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();

		// Initializing Variables
		Map<String, String> testData;
		int data_Value1 = 0;
		testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Creating a new account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Checking the processing of Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering Prior Loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			// Entering Quote details
			Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Validating prior loss details
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview page loaded successfully", false, false);

			accountOverviewPage.priorLossDetails.formatDynamicPath(testData.get("PriorLossType1"))
					.waitTillVisibilityOfElement(60);
			accountOverviewPage.priorLossDetails.formatDynamicPath(testData.get("PriorLossType1")).scrollToElement();

			Assertions.verify(
					accountOverviewPage.priorLossDetails.formatDynamicPath(testData.get("PriorLossType1")).getData()
							.contains(testData.get("PriorLossType1")),
					true, "Account Overview Page",
					"Prior Loss 1 Details : " + accountOverviewPage.priorLossDetails
							.formatDynamicPath(testData.get("PriorLossType1")).getData() + " are verified",
					false, false);

			Assertions.verify(
					accountOverviewPage.priorLossDetails.formatDynamicPath(testData.get("PriorLossType2")).getData()
							.contains(testData.get("PriorLossType2")),
					true, "Account Overview Page",
					"Prior Loss 2 Details : " + accountOverviewPage.priorLossDetails
							.formatDynamicPath(testData.get("PriorLossType2")).getData() + " are verified",
					false, false);

			Assertions.verify(
					accountOverviewPage.priorLossDetails.formatDynamicPath(testData.get("PriorLossType3")).getData()
							.contains(testData.get("PriorLossType3")),
					true, "Account Overview Page",
					"Prior Loss 3 Details : " + accountOverviewPage.priorLossDetails
							.formatDynamicPath(testData.get("PriorLossType3")).getData() + " are verified",
					false, false);
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC049 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC049 ", "Executed Successfully");
			}
		}
	}
}
