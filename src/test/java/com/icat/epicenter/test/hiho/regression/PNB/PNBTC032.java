/** Program Description: Renewal referral
 *  Author			   : SM Netserv
 *  Date of Creation   : Oct 2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC032 extends AbstractNAHOTest {

	public PNBTC032() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID32.xls";
	}

	BasePageControl page;
	LoginPage login;
	HomePage homePage;
	EligibilityPage eligibilityPage;
	LocationPage locationPage;
	BuildingPage buildingPage;
	DwellingPage dwellingPage;
	CreateQuotePage createQuotePage;
	SelectPerilPage selectPerilPage;
	AccountOverviewPage accountOverviewPage;
	RequestBindPage requestBindPage;
	ConfirmBindRequestPage confirmBindRequestPage;
	BindRequestSubmittedPage bindRequestPage;
	ReferralPage referralPage;
	PolicySummaryPage policySummaryPage;
	RenewalIndicatorsPage renewalIndicatorsPage;
	ApproveDeclineQuotePage approve_declinePage;
	String policyNumber;
	String quoteNumber;
	Map<String, String> testData;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		testData = data.get(0);
		page = new BasePageControl();

		// Create New Account
		homePage = new HomePage();
		page = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		dwellingPage = (DwellingPage) page;
		Assertions.passTest("New Account", "New Account created successfully");

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetails(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");
		createQuotePage = dwellingPage.enterDwellingDetails(testData);
		String covA_DwellingPage = testData.get("L1D1-DwellingCovA");
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

		// Entering Quote Details
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = createQuotePage.enterQuoteDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		// Clicking on Bind button in account Overview Page
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		requestBindPage = accountOverviewPage.clickOnRequestBind(setUpData, quoteNumber);

		// Entering Bind Details
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		page = requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Getting NB Policy Number
		policySummaryPage = (PolicySummaryPage) page;
		Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "NB Policy Number is : " + policyNumber);

		// Clicking on Renew Policy Link
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		if (policySummaryPage.pageName.getData().contains("Transfer")) {
			policySummaryPage.transferContinue.scrollToElement();
			policySummaryPage.transferContinue.click();
		}
		Assertions.passTest("Account Overview Page", "Account Overview Page loaded successfully");
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();
		int covA_Dwelling = Integer.parseInt(covA_DwellingPage);
		double covA_Dwellin = covA_Dwelling * (0.01) + covA_Dwelling + 1;
		Assertions.passTest("Account Overview Page",
				"Covergae A Value before Inflation gaurd is applied : " + covA_Dwelling
						+ " Coverage A value after inflation guard is applied : "
						+ createQuotePage.covA_NHinputBox.getData().replaceAll("[^A-Za-z0-9]+", ""));
		Assertions.verify(createQuotePage.covA_NHinputBox.getData().replace(",", ""),
				String.valueOf(covA_Dwellin).replace(".99", ""), "Account Overview Page",
				"Inflation guard is set to 1% while renewing the Policy is verified.", false, false);
		createQuotePage.floodDeductibleArrow.scrollToElement();
		createQuotePage.floodDeductibleArrow.click();
		createQuotePage.floodDeductibleOption.formatDynamicPath(testData.get("FloodDeductible")).scrollToElement();
		createQuotePage.floodDeductibleOption.formatDynamicPath(testData.get("FloodDeductible")).click();
		createQuotePage.enterFloodCovA(testData);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.continueButton.scrollToElement();
		createQuotePage.continueButton.click();
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		Assertions.passTest("Account Overview Page", "Successfully able to release renewal to Producer");
		Assertions.passTest("Account Overview Page", "Values Verified");
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();

		// Entering data in Request Bind Page
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", "Entered Values successfully");
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.submit.waitTillInVisibilityOfElement(60);
		requestBindPage.requestBind.scrollToElement();
		requestBindPage.requestBind.click();
		requestBindPage.requestBind.waitTillInVisibilityOfElement(60);

		// Override Effective date in Request Bind Page
		requestBindPage.overrideEffectiveDate.scrollToElement();
		requestBindPage.overrideEffectiveDate.select();
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.submit.waitTillInVisibilityOfElement(60);
		requestBindPage.requestBind.scrollToElement();
		requestBindPage.requestBind.click();
		requestBindPage.requestBind.waitTillInVisibilityOfElement(60);

		// Getting Renewal Policy Number
		Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
		Assertions.passTest("Policy Summary Page", "Renewal Policy Number :" + policySummaryPage.getPolicynumber());
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC032", "Executed Successfully");

	}
}