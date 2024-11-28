/** Program Description: To generate the HIHO policy with past effective date.
 *  Author			   : SM Netserv
 *  Date of Creation   : June 2018
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
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC012 extends AbstractNAHOTest {

	public TC012() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID12.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		DwellingPage dwellingPage = new DwellingPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		Assertions.passTest("Home Page", "Logged in as Producer ");

		// Create New Account
		testData = data.get(data_Value1);

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		// asserting user preferences icon
		Assertions.verify(
				homePage.userPreferences.checkIfElementIsPresent()
						&& homePage.userPreferences.checkIfElementIsDisplayed(),
				true, "Home Page", "User Preferences icon is displayed on Residential producer's Home page", false,
				false);
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "New Account created successfully");

		// Entering Dwelling Details
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Dwelling details entered");

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.globalErr.checkIfElementIsPresent()
				&& createQuotePage.globalErr.checkIfElementIsDisplayed()) {
			createQuotePage.covA_NHinputBox.scrollToElement();
			createQuotePage.covA_NHinputBox.clearData();
			createQuotePage.covA_NHinputBox.setData("4000000");
			createQuotePage.covA_NHinputBox.tab();
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.waitTillButtonIsClickable(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			createQuotePage.getAQuote.waitTillInVisibilityOfElement(60);
		}
		Assertions.passTest("Create Quote Page", "Clicked on get a quote button");

		// Click on Request Bind
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");

		Assertions.verify(
				homePage.userPreferences.checkIfElementIsPresent()
						&& homePage.userPreferences.checkIfElementIsDisplayed(),
				true, "Account Overview Page",
				"User Preferences icon is displayed on Residential producer's Account overview page", false, false);

		// Getting Quote Number
		String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");

		// Entering Request Bind Page Details
		requestBindPage.enterPaymentInformation(testData);
		requestBindPage.addInspectionContact(testData);
		requestBindPage.enterPolicyDetails(testData);
		requestBindPage.addContactInformation(testData);
		requestBindPage.addAdditionalInterestEQHO(testData);
		requestBindPage.submit.waitTillButtonIsClickable(60);
		requestBindPage.submit.click();
		requestBindPage.confirmBind();
		if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
				&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
			requestBindPage.overrideEffectiveDate.select();
			requestBindPage.submit.waitTillVisibilityOfElement(60);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			requestBindPage.requestBind.click();
			requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
		}

		String policyNumber = policySummarypage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);
		Assertions.verify(policyNumber.startsWith("52-358"), true, "Policy Summary Page",
				"Policy Number format is verified", false, false);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC012", "Executed Successfully");

	}
}