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

public class TC071_NBGEN002 extends AbstractNAHOTest {

	public TC071_NBGEN002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBGEN002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();

		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
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

			// Entering Dwelling Details
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

			// verifying AOP Deductible default value in create quote page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.verify(createQuotePage.aopDeductibleData.getData(), testData.get("AOPDeductibleValue"),
					"Create Quote Page",
					"AOP Deductible is defaulted to $1,000 when occupancy is Primary and Short Term Rental is No is verified",
					false, false);

			// click on previous button in create Quote Page
			createQuotePage.previous.click();

			// modifying dwelling details
			accountOverviewPage.editDwelling.click();

			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);

			// selecting occupancy as Tenant and Short Term Rental as No
			testData = data.get(dataValue2);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			Assertions.passTest("Dwelling Page", "Dwelling details modified successfully");

			dwellingPage.reviewDwelling();
			dwellingPage.createQuote.waitTillVisibilityOfElement(60);
			dwellingPage.createQuote.waitTillButtonIsClickable(60);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// verifying AOP Deductible default value
			Assertions.verify(createQuotePage.aopDeductibleData.getData(), testData.get("AOPDeductibleValue"),
					"Create Quote Page",
					"AOP Deductible is defaulted to $2,500 when occupancy is Tenant and Short Term Rental is No is verified",
					false, false);

			// click on previous button in create Quote Page
			createQuotePage.previous.click();

			// modifying dwelling details
			accountOverviewPage.editDwelling.click();

			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);

			// selecting occupancy as Primary and Short Term Rental as Yes
			testData = data.get(dataValue3);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			Assertions.passTest("Dwelling Page", "Dwelling details modified successfully");

			dwellingPage.reviewDwelling();
			dwellingPage.createQuote.waitTillVisibilityOfElement(60);
			dwellingPage.createQuote.waitTillButtonIsClickable(60);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// verifying AOP Deductible default value
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.verify(createQuotePage.aopDeductibleData.getData(), testData.get("AOPDeductibleValue"),
					"Create Quote Page",
					"AOP Deductible is defaulted to $2,500 when occupancy is Primary and Short Term Rental is Yes is verified",
					false, false);

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC071 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC071 ", "Executed Successfully");
			}
		}
	}

}
