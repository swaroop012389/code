package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC037_NBRE030 extends AbstractNAHOTest {

	public TC037_NBRE030() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE030.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();

		int data_Value = 0;
		int data_Value1 = 1;
		Map<String, String> testData = data.get(data_Value);
		Map<String, String> testData1 = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Create New Account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("Home Page", "New account created successfully");

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
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Assert AOP value when short term rental is selected as Yes
			Assertions.verify(createQuotePage.aopDeductibleData.getData(), "$2,500", "Create Quote Page",
					"AOP value is " + createQuotePage.aopDeductibleData.getData()
							+ " when short term rentel is selected as Yes",
					false, false);
			createQuotePage.previous.waitTillVisibilityOfElement(60);
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			// change short term rental to no
			dwellingPage.editBuilding.waitTillVisibilityOfElement(60);
			dwellingPage.editBuilding.scrollToElement();
			dwellingPage.editBuilding.click();
			dwellingPage.shortTermRentalNo.scrollToElement();
			dwellingPage.shortTermRentalNo.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Assert AOP value when short term rental is selected as No
			Assertions.verify(
					createQuotePage.aopDeductibleData.getData(), "$2,500", "Create Quote Page", "AOP value is "
							+ createQuotePage.aopDeductibleData.getData() + " when short term rentel is selected as No",
					false, false);
			createQuotePage.previous.waitTillVisibilityOfElement(60);
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			// Update coverage A value to 2000000
			dwellingPage.editBuilding.waitTillVisibilityOfElement(60);
			dwellingPage.editBuilding.scrollToElement();
			dwellingPage.editBuilding.click();
			dwellingPage.editBuilding.waitTillInVisibilityOfElement(60);
			dwellingPage.scrollToBottomPage();

			dwellingPage.enterDwellingValuesNAHO(testData1, 1, 1);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.bringUpToCost.scrollToElement();
				buildingUnderMinimumCostPage.bringUpToCost.click();
			}

			// Assert AOP value when short term rental is selected as No and coverage A
			// value is 2000000
			Assertions.verify(createQuotePage.aopDeductibleData.getData(), "$10,000", "Create Quote Page",
					"AOP value is " + createQuotePage.aopDeductibleData.getData()
							+ " when short term rentel is selected as No and coverage A value is $2,000,000",
					false, false);
			createQuotePage.previous.waitTillVisibilityOfElement(60);
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			// change short term rental to Yes
			dwellingPage.editBuilding.waitTillVisibilityOfElement(60);
			dwellingPage.editBuilding.scrollToElement();
			dwellingPage.editBuilding.click();
			dwellingPage.shortTermRentalYes.scrollToElement();
			dwellingPage.shortTermRentalYes.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting hard stop message when Cov A less than minium value "The quoted
			// building has a Coverage A limit of less than $300,000 and is ineligible for
			// coverage"
			Assertions.verify(createQuotePage.globalErr.getData().contains(
					"The quoted building has a Coverage A limit of less than $300,000 and is ineligible for coverage"),
					true, "Create Quote Page", "The Hard Stop Message is " + createQuotePage.globalErr.getData(), false,
					false);

			// SignOut and Close the Browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC037 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC037 ", "Executed Successfully");
			}
		}
	}
}
