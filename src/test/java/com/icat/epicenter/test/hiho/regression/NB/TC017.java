/** Program Description: IO-22112 - <Roof> Hard stop for Year Roof Replaced in the future
 *  Author			   : pavan mule
 *  Date of Creation   : 05/07/2024
 **/
package com.icat.epicenter.test.hiho.regression.NB;

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

public class TC017 extends AbstractNAHOTest {

	public TC017() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID17.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		DwellingPage dwellingPage = new DwellingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		String PAGE_NAVIGATED = "Page Navigated";
		String VALUES_ENTERED = "Values Entered successfully";

		String quoteNumber;

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", VALUES_ENTERED);

		// Asserting and verifying hard stop message when year roof last replaced in the
		// future year
		// Hard stop message is 'Year Roof Last Replaced cannot be after Policy
		// Effective year.Future year roof last replaced is not allowed.
		Assertions.addInfo("Scenario 01",
				"Asserting and verifying hard stop message when year roof last replaced in the future year ");
		Assertions.verify(
				dwellingPage.cOCWarningMsg.getData().contains("Future year roof last replaced is not allowed"), true,
				"Dwelling Page", "Hard stop message is " + dwellingPage.cOCWarningMsg.getData() + " displayed", false,
				false);
		Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

		// Remove year roof last replaced
		dwellingPage.roofDetailsLink.scrollToElement();
		dwellingPage.roofDetailsLink.click();
		dwellingPage.yearRoofLastReplaced.scrollToElement();
		dwellingPage.yearRoofLastReplaced.clearData();

		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", VALUES_ENTERED);

		// Getting quote number
		quoteNumber = accountOverviewPage.quoteNumber.getData();
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account overview page loaded successfully", false, false);
		Assertions.passTest("Account Overview Page", "Quote number is " + quoteNumber);

		// Sign out as producer
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(30);
		Assertions.passTest("Home Page", "Loged out as producer");

		// Login to Producer account
		loginPage.refreshPage();
		loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as usm Successfully");

		// asserting personal login details
		homePage.enterPersonalLoginDetails();

		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home Page loaded successfully", false, false);
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", VALUES_ENTERED);

		// Asserting and verifying hard stop message when year roof last replaced in the
		// future year
		// Hard stop message is 'Year Roof Last Replaced cannot be after Policy
		// Effective year.Future year roof last replaced is not allowed.
		Assertions.addInfo("Scenario 02",
				"Asserting and verifying hard stop message when year roof last replaced in the future year ");
		Assertions.verify(
				dwellingPage.cOCWarningMsg.getData().contains("Future year roof last replaced is not allowed"), true,
				"Dwelling Page", "Hard stop message is " + dwellingPage.cOCWarningMsg.getData() + " displayed", false,
				false);
		Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

		// Remove year roof last replaced
		dwellingPage.roofDetailsLink.scrollToElement();
		dwellingPage.roofDetailsLink.click();
		dwellingPage.yearRoofLastReplaced.scrollToElement();
		dwellingPage.yearRoofLastReplaced.clearData();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", VALUES_ENTERED);

		// Getting quote number
		quoteNumber = accountOverviewPage.quoteNumber.getData();
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account overview page loaded successfully", false, false);
		Assertions.passTest("Account Overview Page", "Quote number is " + quoteNumber);

		// Sign out as usm
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(30);
		Assertions.passTest("Home Page", "Loged out as usm");

		Assertions.passTest("NB_Regression_TC017", "Executed Successfully");

	}
}