/** Program Description: To generate the HIHO policy with inspection ordering.
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
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC011 extends AbstractNAHOTest {

	public TC011() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID11.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		DwellingPage dwellingPage = new DwellingPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		OverridePremiumAndFeesPage overridePremAndFeesPage = new OverridePremiumAndFeesPage();
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);

		// Getting Location and Building count
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);

		// Create New Account
		testData = data.get(data_Value1);
		Assertions.passTest("Home Page", "Page Navigated");

		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "New Account created successfully");
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

		// Entering Dwelling Details
		for (int j = 1; j <= locationCount; j++) {
			for (int i = 1; i <= dwellingCount; i++) {
				dwellingPage.addDwellingDetails(testData, 1, i);
				dwellingPage.addRoofDetails(testData, 1, i);
				dwellingPage.enterDwellingValues(testData, 1, i);
				if (i < 3) {
					dwellingPage.addSymbol.scrollToElement();
					dwellingPage.addSymbol.click();
					dwellingPage.addNewDwelling.click();
				}
				Assertions.passTest("Dwelling Page",
						("Dwelling " + (i + 1) + " :Dwelling details entered successfully"));
			}
		}

		// Create Quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(60);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.getAQuote.waitTillInVisibilityOfElement(60);

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}
		String accountQuoteNumber = accountOverviewPage.acntOverviewQuoteNumber.getData();

		// Override Premium and fees page
		// Check Inspection fee and Order Inspection
		accountOverviewPage.overridePremiumLink.waitTillVisibilityOfElement(60);
		accountOverviewPage.overridePremiumLink.scrollToElement();
		accountOverviewPage.overridePremiumLink.click();
		Assertions.passTest("Override Premium and Fees Page", "Override Premium and Fees page loaded successfully");
		overridePremAndFeesPage.originalInspectionFee.waitTillVisibilityOfElement(60);
		String inspectionfee = overridePremAndFeesPage.originalInspectionFee.getData();
		Assertions.passTest("Override Premium and Fees Page", "Total Inspection Fee is :" + inspectionfee);
		for (int locNo = 1; locNo <= locationCount; locNo++) {
			for (int bldgNo = 1; bldgNo <= dwellingCount; bldgNo++) {
				if (overridePremAndFeesPage.transactionOrderInspection.formatDynamicPath(locNo, bldgNo)
						.checkIfElementIsSelected()) {
					Assertions.passTest("Override Premium and Fees Page",
							"Order Inspection is selected by default for " + "Dwelling-" + locNo + "-" + bldgNo);
				} else {
					Assertions.passTest("Override Premium and Fees Page",
							"Order Inspection is not selected by default for " + "Dwelling-" + locNo + "-" + bldgNo);
				}
			}
		}

		overridePremAndFeesPage.cancelButton.waitTillButtonIsClickable(60);
		overridePremAndFeesPage.cancelButton.waitTillVisibilityOfElement(60);
		overridePremAndFeesPage.cancelButton.scrollToElement();
		overridePremAndFeesPage.cancelButton.click();

		// Click on Request Bind
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, accountQuoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");

		// Getting Quote Number
		String quoteNumber = requestBindPage.quoteNumber.getData();
		Assertions.passTest("Request Bind Page", "Quote Number :  " + quoteNumber);

		// Entering Request Bind Page Details
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		String policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);
		Assertions.verify(policyNumber.startsWith("52-358"), true, "Policy Summary Page",
				"Policy Number format is verified", false, false);
		Assertions.verify(policySummaryPage.transactionInspectionFee.getData(), inspectionfee, "Policy Summary Page",
				"Inspection Fee displayed is : " + policySummaryPage.transactionInspectionFee.getData(), true, false);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC011", "Executed Successfully");

	}
}
