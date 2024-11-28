/** Program Description: Validating dwelling page elements for NB and renewal as USM.
 *  Author			   : John
 *  Date of Creation   : 09/16/19
 **/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC044 extends AbstractNAHOTest {

	public PNBTC044() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID44.xls";
	}

	public Map<String, String> testData;
	public BasePageControl basePage;
	public HomePage homePage;
	public DwellingPage dwellingPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public PolicySummaryPage policySummaryPage;
	public BindRequestSubmittedPage bindRequestPage;
	public BuildingUnderMinimumCostPage dwellingCost;
	public ReferralPage referralPage;
	String quoteNumber;
	String policyNumber;
	static int data_Value1 = 0;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		dwellingCost = new BuildingUnderMinimumCostPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		policySummaryPage = new PolicySummaryPage();
		dwellingPage = new DwellingPage();
		testData = data.get(data_Value1);

		// New account
		Assertions.passTest("Home Page", "Logged in as USM successfully");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "New Account created successfully");

		// Entering Dwelling Details
		dwellingPage.enterAndAssertDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Dwelling Page elements validated for NB successfully");

		// Review Dwelling
		dwellingPage.reviewDwelling.click();
		dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(60);

		// Click on Create Quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(60);

		// Entering Create quote page Details
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.getAQuote.waitTillInVisibilityOfElement(60);
		if (createQuotePage.pageName.getData().contains("Create Quote")) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			createQuotePage.continueButton.waitTillInVisibilityOfElement(60);
		}
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);

		// Binding the quote
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

		// Entering Request Bind Page Details
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		String policyNumber = policySummaryPage.getPolicynumber();
		Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true, "Policy Summary Page",
				"Policy number is displayed", false, false);
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

		policySummaryPage.renewPolicy.click();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		Assertions.passTest("Account Overview Page", "Renewal released to producer successfully");

		// Assert dwelling details for renewal quote of USM
		accountOverviewPage.editDwelling11.waitTillVisibilityOfElement(60);
		accountOverviewPage.editDwelling11.scrollToElement();
		accountOverviewPage.editDwelling11.click();
		accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();
		if (accountOverviewPage.yesButton.checkIfElementIsPresent()) {
			accountOverviewPage.yesButton.scrollToElement();
			accountOverviewPage.yesButton.click();
		}
		if (accountOverviewPage.quoteExpiredPopupMsg.checkIfElementIsPresent()) {
			accountOverviewPage.quoteExpiredPopupMsg.scrollToElement();
			accountOverviewPage.quoteExpiredPopupMsg.click();
			accountOverviewPage.quoteExpiredPopupMsg.waitTillInVisibilityOfElement(60);
		}
		dwellingPage.waitTime(60);
		dwellingPage.enterAndAssertDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Validated elements for renewal quote as USM");

		// sign out
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC044", "Executed Successfully");

	}
}
