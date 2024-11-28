package com.icat.epicenter.test.naho.regression.ISNB;

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
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC085_NBGEN016 extends AbstractNAHOTest {

	public TC085_NBGEN016() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBGEN016.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ModifyForms modifyForms = new ModifyForms();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		String quoteNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData1 = data.get(data_Value2);
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
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

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesNAHO(testData);

			// Click on modify forms button
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();

			Assertions.passTest("Dwelling Page", "Year Built value is " + testData.get("L1D1-DwellingYearBuilt"));
			Assertions.passTest("Dwelling Page", "Occupied by value is " + testData.get("L1D1-OccupiedBy"));
			Assertions.passTest("Dwelling Page", "Plumbing replacement year value is not provided");

			// Assert for Limited coverage premises liability, water damage exclusion
			Assertions.verify(modifyForms.premisesLiability.checkIfElementIsSelected(), true, "Modify Forms Page",
					"Limited Coverage Premises Liability checkbox is selected by default", false, false);
			Assertions.verify(modifyForms.waterDamageExclusionTenThousand.checkIfElementIsSelected(), false,
					"Modify Forms Page",
					"Water Damage Exclusion Sublimit Ten Thousand checkbox is not selected by default", false, false);
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			if (modifyForms.override.checkIfElementIsPresent() && modifyForms.override.checkIfElementIsDisplayed()) {
				modifyForms.override.scrollToElement();
				modifyForms.override.click();
			}
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number 1 is : " + quoteNumber);

			// Edit dwelling
			accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();

			// Update year built, occupancy type, plumbing year
			dwellingPage.yearBuilt.waitTillVisibilityOfElement(60);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			if (dwellingPage.continueWithUpdateBtn.checkIfElementIsPresent()
					&& dwellingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
			}
			dwellingPage.yearBuilt.setData(testData1.get("L1D1-DwellingYearBuilt"));
			Assertions.passTest("Dwelling Page", "Year Built value is " + testData1.get("L1D1-DwellingYearBuilt"));
			dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
			dwellingPage.OccupiedByArrow.scrollToElement();
			dwellingPage.OccupiedByArrow.click();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData1.get("L1D1-OccupiedBy"))
					.waitTillVisibilityOfElement(60);
			dwellingPage.OccupiedByOption.formatDynamicPath(testData1.get("L1D1-OccupiedBy")).scrollToElement();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData1.get("L1D1-OccupiedBy")).click();
			Assertions.passTest("Dwelling Page", "Occupied by value is " + testData1.get("L1D1-OccupiedBy"));
			dwellingPage.protectionDiscounts.waitTillVisibilityOfElement(60);
			dwellingPage.protectionDiscounts.scrollToElement();
			dwellingPage.protectionDiscounts.click();
			dwellingPage.yearPlumbingUpdated.waitTillVisibilityOfElement(60);
			dwellingPage.yearPlumbingUpdated.scrollToElement();
			dwellingPage.yearPlumbingUpdated.setData(testData1.get("L1D1-PlumbingUpdatedYear"));
			Assertions.passTest("Dwelling Page",
					"Plumbing replacement year value is " + testData1.get("L1D1-PlumbingUpdatedYear"));
			dwellingPage.scrollToBottomPage();
			dwellingPage.waitTime(3); // to click on create quote page as the control is not going without waittime
			dwellingPage.createQuote.waitTillVisibilityOfElement(60);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on Create quote button");

			// Override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// click on create quote
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					&& dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.scrollToBottomPage();
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			// Override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Click on modify forms button
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();

			// Assert for Limited coverage premises liability, water damage exclusion
			Assertions.verify(modifyForms.premisesLiability.checkIfElementIsSelected(), false, "Modify Forms Page",
					"Limited Coverage Premises Liability checkbox is not selected by default", false, false);
			Assertions.verify(modifyForms.waterDamageExclusionTenThousand.checkIfElementIsSelected(), false,
					"Modify Forms Page",
					"Water Damage Exclusion Sublimit Ten Thousand checkbox is not selected by default", false, false);

			// Selecting wind related forms
			modifyForms.waterDamageExclusionZero.scrollToElement();
			modifyForms.waterDamageExclusionZero.select();
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Create Quote
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			if (modifyForms.override.checkIfElementIsPresent() && modifyForms.override.checkIfElementIsDisplayed()) {
				modifyForms.override.scrollToElement();
				modifyForms.override.click();
			}
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number 2 is : " + quoteNumber);

			// Edit dwelling and update plumbing replaced year
			accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Dwelling Page", "Year Built value is " + testData1.get("L1D1-DwellingYearBuilt"));
			Assertions.passTest("Dwelling Page", "Occupied by value is " + testData1.get("L1D1-OccupiedBy"));
			dwellingPage.protectionDiscounts.waitTillVisibilityOfElement(60);
			dwellingPage.protectionDiscounts.scrollToElement();
			dwellingPage.protectionDiscounts.click();
			dwellingPage.yearPlumbingUpdated.waitTillVisibilityOfElement(60);
			dwellingPage.yearPlumbingUpdated.scrollToElement();
			dwellingPage.yearPlumbingUpdated.clearData();
			// dwellingPage.yearPlumbingUpdated.tab();
			if (dwellingPage.continueWithUpdateBtn.checkIfElementIsPresent()
					&& dwellingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
			}
			Assertions.passTest("Dwelling Page", "Plumbing replacement year is Removed");

			// Create quote
			dwellingPage.scrollToBottomPage();
			dwellingPage.waitTime(3); // to click on create quote page as the control is not going without waittime
			dwellingPage.createQuote.waitTillVisibilityOfElement(60);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// click on create quote
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					&& dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.scrollToBottomPage();
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}
			// Click on modify forms button
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();

			// Assert for Limited coverage premises liability, water damage exclusion
			Assertions.verify(modifyForms.premisesLiability.checkIfElementIsSelected(), false, "Modify Forms Page",
					"Limited Coverage Premises Liability checkbox is not selected by default", false, false);
			Assertions.verify(modifyForms.waterDamageExclusionTenThousand.checkIfElementIsSelected(), true,
					"Modify Forms Page", "Water Damage Exclusion Sublimit Ten Thousand checkbox is selected by default",
					false, false);

			// Selecting wind related forms
			modifyForms.waterDamageExclusionZero.scrollToElement();
			modifyForms.waterDamageExclusionZero.select();
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Create Quote
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			if (modifyForms.override.checkIfElementIsPresent() && modifyForms.override.checkIfElementIsDisplayed()) {
				modifyForms.override.scrollToElement();
				modifyForms.override.click();
			}
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number 3 is : " + quoteNumber);

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC085 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC085 ", "Executed Successfully");
			}
		}
	}
}
