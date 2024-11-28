/** Program Description: Check that for a Wind Quote in HI, AOWH option is not present in quote page. Also validate that Named Storm Deductible is NOT present and only Named Hurricane Deductible is present. and added IO-20977
 *  Author			   : Abha
 *  Date of Creation   : 11/26/2019
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC007 extends AbstractCommercialTest {

	public TC007() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID007.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();

		// Initializing variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully with effective date");

			// Entering zip code in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Building details entered successfully");

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril details entered successfully");

			// Asserting presence of NH but not AOWH and NS
			Assertions.addInfo("Scenario 01", "Asserting presence of NH but not AOWH and NS");
			Assertions.verify(createQuotePage.namedHurricaneRadio.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Deductible : Named Hurricane is displayed", false, false);
			Assertions.verify(createQuotePage.namedStormRadio.checkIfElementIsPresent(), false, "Create Quote Page",
					"Deductible : Named Storm is not displayed", false, false);
			Assertions.verify(createQuotePage.aowhDeductibleArrow.checkIfElementIsPresent(), false,
					"Create Quote Pagee", "Deductible : AOWH is not displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number is " + quoteNumber);

			// Clicking on View Quote Details Link
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");

			// Verifying the quote details
			Assertions.addInfo("Scenario 02", "Verifying the quote details from Quote Document");
			Assertions.verify(
					quoteDetailsPage.deductibleData
							.formatDynamicPath("Named Hurricane").getData().contains("Named Hurricane"),
					true, "Quote Display Page",
					"Deductible Named : Hurricane is displayed <br/>"
							+ quoteDetailsPage.deductibleData.formatDynamicPath("Named Hurricane").getData(),
					false, false);
			Assertions.verify(
					quoteDetailsPage.deductibleData.formatDynamicPath("Named Hurricane").getData()
							.contains("Named Storm"),
					false, "Quote Display Page", "Deductible : Named Storm is not displayed", false, false);
			Assertions.verify(
					quoteDetailsPage.deductibleData.formatDynamicPath("Named Hurricane").getData()
							.contains("All Other Wind"),
					false, "Quote Display Page", "Deductible : AOWH is not displayed", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Adding the following code for IO-18724
			ViewOrPrintFullQuotePage viewPrintFullQuotePage = new ViewOrPrintFullQuotePage();
			RequestBindPage requestBindPage = new RequestBindPage();
			viewPrintFullQuotePage.scrollToTopPage();
			viewPrintFullQuotePage.waitTime(2);// need wait time to scroll to top page
			viewPrintFullQuotePage.backButton.click();

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request bind button successfully");

			// Asserting commission rate
			Assertions.addInfo("Scenario 03", "Asserting commission rate");
			Assertions.verify(requestBindPage.commissionRate.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Commission Rate " + requestBindPage.commissionRate.getData()
							+ " is displayed when effective is entered while creating account",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			testData = data.get(data_Value2);
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully without effective date");

			testData = data.get(data_Value1);
			// Entering zip code in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Building details entered successfully");

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Adding Code for IO-19410
			// Asserting Other Deductible Options table Total Premium values
			Assertions.addInfo("Scenario 04", "Asserting Other Deductible Options table Total Premium values");
			accountOverviewPage.quoteOptions1TotalPremium.waitTillVisibilityOfElement(60);
			Assertions.verify(accountOverviewPage.quoteOptions1TotalPremium.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Quote Options Total Premium 1 is displayed successfully : "
							+ accountOverviewPage.quoteOptions1TotalPremium.getData(),
					false, false);
			Assertions.verify(accountOverviewPage.quoteOptions2TotalPremium.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Quote Options Total Premium 2 is displayed successfully : "
							+ accountOverviewPage.quoteOptions2TotalPremium.getData(),
					false, false);
			Assertions.verify(accountOverviewPage.quoteOptions3TotalPremium.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Quote Options Total Premium 3 is displayed successfully : "
							+ accountOverviewPage.quoteOptions3TotalPremium.getData(),
					false, false);
			Assertions.verify(
					accountOverviewPage.quoteOptions4TotalPremium.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Quote Options Total Premium 4 is displayed successfully : "
							+ accountOverviewPage.quoteOptions4TotalPremium.formatDynamicPath(5).getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Go to to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search the quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote searched successfully");

			// Click on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Asserting commission rate
			Assertions.addInfo("Scenario 05",
					"Asserting Commission Rate is not displayed when effective is not entered");
			Assertions.verify(requestBindPage.commissionRate.getData().contains(""), true, "Request Bind Page",
					"Commission Rate is not displayed when effective is not entered while creating account", false,
					false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// sign out and close browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 07", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 07", "Executed Successfully");
			}
		}
	}
}