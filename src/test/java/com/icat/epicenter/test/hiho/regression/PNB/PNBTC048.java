/** Program Description: To verify Prior loss referral message during renewal when logged in as producer
 * 						 To Verify the New Minimum Cost Card Changes Message and Referral Message when Coverage A
 * 						 is increased to Value more than 100,000 while Renewal Re-Quoting.
 *  Author			   : John
 *  Date of Creation   : 09/27/19
 **/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC048 extends AbstractNAHOTest {

	public PNBTC048() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID48.xls";
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
		BuildingUnderMinimumCostPage dwellingCost = new BuildingUnderMinimumCostPage();
		LocationPage locationPage = new LocationPage();
		LoginPage loginPage = new LoginPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);
		String policyNumber;
		String quoteNumber;

		// New account
		Assertions.passTest("Home Page", "Page Navigated");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "Values Entered");

		// Entering Location Details
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Enter Dwelling details
		Assertions.passTest("Dwelling Page", "Page Navigated");
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 - 1 Values Entered successfully");

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Click on Override
		dwellingCost.clickOnOverride();

		// Click on continue
		if (createQuotePage.continueButton.checkIfElementIsPresent()) {
			createQuotePage.continueButton.click();
			createQuotePage.continueButton.waitTillInVisibilityOfElement(60);
		}
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :" + quoteNumber);
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policyNumber);

		// Initiate the renewal
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		Assertions.passTest("Renew Policy Page", "Renewal initiated Successfully");

		// click on edit location
		accountOverviewPage.editLocation.scrollToElement();
		accountOverviewPage.editLocation.click();

		// Click on Dwelling-1
		dwellingPage.dwelling1.scrollToElement();
		dwellingPage.dwelling1.click();

		// copy Dwelling - 1 for new dwelling details
		if (dwellingPage.copySymbol.checkIfElementIsPresent() && dwellingPage.copySymbol.checkIfElementIsDisplayed()) {
			dwellingPage.copySymbol.scrollToElement();
			dwellingPage.copySymbol.click();
		}

		// Click on review Building
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();

		// Click on override Button
		if (dwellingPage.override.checkIfElementIsPresent() && dwellingPage.override.checkIfElementIsDisplayed()) {
			dwellingPage.override.scrollToElement();
			dwellingPage.override.click();
		}

		// click on createQuote
		if (dwellingPage.createQuote.checkIfElementIsPresent()
				&& dwellingPage.createQuote.checkIfElementIsDisplayed()) {
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
		}

		// enter details in Create Quote page
		testData = data.get(data_Value3);
		createQuotePage.enterQuoteDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Click on override Button
		if (dwellingPage.override.checkIfElementIsPresent() && dwellingPage.override.checkIfElementIsDisplayed()) {
			dwellingPage.override.scrollToElement();
			dwellingPage.override.click();
		}

		// Release to Producer
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		Assertions.passTest("Account Overview Page", "Renewal released to producer Successfully");

		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();

		// Login to Producer account
		loginPage.refreshPage();
		loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as Producer Successfully");

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		homePage.searchPolicyByProducer(policyNumber);

		policySummaryPage.viewActiveRenewal.scrollToElement();
		policySummaryPage.viewActiveRenewal.click();

		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();

		// Previous Cov A value
		Assertions.passTest("Create Quote Page", "Previous Coverage A Value : " + createQuotePage.covAEQ.getData());

		testData = data.get(data_Value2);
		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		if (createQuotePage.covA_NHinputBox.checkIfElementIsPresent()
				&& createQuotePage.covA_NHinputBox.checkIfElementIsDisplayed()) {
			createQuotePage.covA_NHinputBox.scrollToElement();
			createQuotePage.covA_NHinputBox.waitTillVisibilityOfElement(60);
			createQuotePage.covA_NHinputBox.clearData();
			createQuotePage.covA_NHinputBox.setData(testData.get("L1D1-DwellingCovA"));
		}

		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Cov A EQ value after increasing Cov A value
		Assertions.passTest("Create Quote Page",
				"After Increase Coverage A Value : " + createQuotePage.covAEQ.getData());

		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// calculate and asserting the warning message for min dwelling value for L1D2
		Assertions.verify(
				createQuotePage.minimunCostWarningMsg.formatDynamicPath("Coverage A value will be increased")
						.checkIfElementIsPresent()
						&& createQuotePage.minimunCostWarningMsg
								.formatDynamicPath("Coverage A value will be increased").checkIfElementIsDisplayed(),
				true, "Account Overview Page",
				"Minimum cost warning messages for L1D2 is verified and displayed as :"
						+ createQuotePage.minimunCostWarningMsg.formatDynamicPath("Coverage A value will be increased")
								.getData(),
				false, false);

		// Calculate Minimum cost increase for L1D2
		testData = data.get(data_Value1);
		String livingSqFoot = testData.get("L1D1-DwellingSqFoot");
		String nonLivingSqFoot = testData.get("L1D1-DwellingNonLivingSqFoot");
		Double livingSqFootValue = Double.parseDouble(livingSqFoot);
		Double nonLivingSqFootvalue = Double.parseDouble(nonLivingSqFoot);
		int minCostIncrease = (int) ((livingSqFootValue * 200) + (nonLivingSqFootvalue * 115));
		String minCostIncreaseValue = Integer.toString(minCostIncrease);
		System.out.println("Int Value = " + minCostIncrease);
		System.out.println("String Value = " + minCostIncreaseValue);

		// calculate and asserting the warning message for min dwelling value for L1D2
		Assertions.verify(
				createQuotePage.minimunCostWarningMsg.formatDynamicPath("Coverage A value will be increased").getData()
						.replace(",", "").contains(minCostIncreaseValue),
				true, "Account Overview Page",
				"Minimum cost Value for L1D2 is verified and displayed as :" + "$" + minCostIncreaseValue, false,
				false);

		// Click on continue
		testData = data.get(data_Value2);
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.click();
		}

		// Quote refer details
		Assertions.verify(referQuotePage.priorLossesReferralMessage.formatDynamicPath(1).checkIfElementIsDisplayed(),
				true, "Quote Referral Page",
				"Referral Message 1 : " + referQuotePage.priorLossesReferralMessage.formatDynamicPath(1).getData(),
				false, false);

		testData = data.get(data_Value1);
		// Enter Referral Contact Details
		referQuotePage.contactName.setData(testData.get("ProducerName"));
		referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
		referQuotePage.referQuote.click();
		Assertions.passTest("Refer Quote Page", "Quote referred successfully");
		quoteNumber = referQuotePage.referQuoteNumber.getData();

		// logout as Producer
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Logged Out as Producer Successfully");

		// Login to USM account
		loginPage.refreshPage();
		loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as USM Successfully");

		homePage.goToHomepage.waitTillVisibilityOfElement(60);
		homePage.goToHomepage.click();

		homePage.searchReferral(quoteNumber);

		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC048", "Executed Successfully");
	}
}
