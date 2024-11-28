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

public class TC070_NBGEN001 extends AbstractNAHOTest {

	public TC070_NBGEN001() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBGEN001.xls";
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
		int data_Value2 = 1;
		int data_Value3 = 2;
		String locationNumber;
		int locationCount;
		String dwellingNumber;
		int dwellingCount;
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
			locationNumber = testData.get("LocCount");
			locationCount = Integer.parseInt(locationNumber);
			dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
			dwellingCount = Integer.parseInt(dwellingNumber);
			testData = data.get(data_Value2);
			createQuotePage.enterInsuredValuesNAHO(testData, locationCount, dwellingCount);

			// Assertion to verify HO5 not appearing for CovA value of 749,000
			Assertions.verify(
					createQuotePage.formType_HO5.checkIfElementIsPresent()
							&& createQuotePage.formType_HO5.checkIfElementIsDisplayed(),
					false, "Create Quote Page", "HO5 RadioButton is not appearing for coverageA value of 749,000",
					false, false);
			testData = data.get(data_Value3);
			createQuotePage.enterInsuredValuesNAHO(testData, locationCount, dwellingCount);

			// Assertions to verify HO5 appearing for CovA value of 751,000
			Assertions.verify(
					createQuotePage.formType_HO5.checkIfElementIsPresent()
							&& createQuotePage.formType_HO5.checkIfElementIsDisplayed(),
					true, "Create Quote Page", "HO5 RadioButton is appearing for coverageA value of 751,000", false,
					false);
			createQuotePage.coverageCPersonalProperty.waitTillVisibilityOfElement(60);
			createQuotePage.coverageCPersonalProperty.scrollToElement();
			createQuotePage.coverageCPersonalProperty.clearData();

			// Assertions to verify HO5 not appearing for CovA
			Assertions.verify(
					createQuotePage.formType_HO5.checkIfElementIsPresent()
							&& createQuotePage.formType_HO5.checkIfElementIsDisplayed(),
					false, "Create Quote Page",
					"HO5 RadioButton is not appearing for coverageA value of 751,000 and coverageC value is 0", false,
					false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Asserting quote number
			Assertions.passTest("Account Overview Page",
					"Quote Number is :" + accountOverviewPage.quoteNumber.getData());

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC070 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC070 ", "Executed Successfully");
			}
		}
	}
}