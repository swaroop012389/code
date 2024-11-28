/** Program Description: Check if Flat Dollar Deductible Value is available for Named Hurricane Deductible and Named Hurricane Deductible is displayed as 10% for a Producer.
 *  Author			   : Sowndarya
 *  Date of Creation   : 04/08/2022
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC014 extends AbstractNAHOTest {

	public PNBTC014() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID14.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		DwellingPage dwellingPage = new DwellingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		Map<String, String> testData;
		String quoteNumber;
		int quoteLen;
		final String PAGE_NAVIGATED = "Page Navigated";
		final String VALUES_ENTERED = "Values Entered successfully";
		int dataValue1 = 0;
		testData = data.get(dataValue1);

		// creating New account
		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home page loaded successfully", false, false);
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("New Account", "New Account created successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Building Page",
				"Dwelling Page loaded successfully", false, false);
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 01",
				"Verifying the presence of Named Hurricane Deductible value $20,000 in the dropdown");
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption
						.formatDynamicPath(testData.get("NamedHurricaneDedValue")).checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value " + createQuotePage.namedHurricaneDeductibleOption
						.formatDynamicPath(testData.get("NamedHurricaneDedValue")).getData() + " is verified",
				false, false);
		Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.waitTillVisibilityOfElement(60);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.scrollToElement();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.click();
		Assertions.passTest("Create Quote Page",
				"The Named Hurricane Deductible value selected is " + createQuotePage.namedHurricaneData.getData());
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

		// Signing out as USM
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Home Page", "Logged out as USM successfully");

		// Login as producer
		loginPage.refreshPage();
		Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
				"Login page loaded successfully", false, false);
		loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
		Assertions.passTest("Login Page", "Logged in as Producer " + setUpData.get("NahoProducer") + " Successfully");

		// search the renewal quote
		Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
				"Producer Home Page loaded successfully", false, false);
		homePage.searchQuoteByProducer(quoteNumber);
		Assertions.passTest("Home Page", "Searched the quote " + quoteNumber + " successfully");

		// click on edit deductibles and limits
		Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Account Overview Page loaded successfully", false, false);
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();
		Assertions.passTest("Account Overview Page", "Clicked on Edit deductibles and limits");

		// Verifying the Default Named Hurricane Deductible displayed as 10%
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 02", "Verifying the Default Named Hurricane Deductible displayed as $20,000");
		Assertions
				.verify(createQuotePage.namedHurricaneData.getData(), testData.get("NamedHurricaneDedValue"),
						"Create Quote Page", "The default Named Hurricane deductible value "
								+ createQuotePage.namedHurricaneData.getData() + " displayed is verified",
						false, false);
		Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

		// Check if $25000 and 10% is available under Named Hurricane Deductible
		// options.
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 03",
				"Verifying if $25000 and 10% is available under Named Hurricane Deductible options.");
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("$25,000").checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("$25,000").getData()
						+ " is verified",
				false, false);
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("10%").checkIfElementIsDisplayed(),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("10%").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", PAGE_NAVIGATED);

		// quite the browser
		Assertions.passTest("PNB_Regression_TC014", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}