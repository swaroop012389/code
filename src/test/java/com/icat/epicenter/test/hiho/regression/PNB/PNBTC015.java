/** Program Description: Create renewal re-quote and bind it
 *  Author			   : SM Netserv
 *  Date of Creation   : Sep 2018
 **/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC015 extends AbstractNAHOTest {

	public PNBTC015() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID15.xls";
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
	BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage;
	RequestBindPage requestBindPage;
	ConfirmBindRequestPage confirmBindRequestPage;
	BindRequestSubmittedPage bindRequestPage;
	ReferralPage referralPage;
	PolicySummaryPage policySummarypage;
	RenewalIndicatorsPage renewalIndicatorspage;
	OverridePremiumAndFeesPage overridePremium_Fees;
	String policyNumber;

	String quoteNumber;

	Map<String, String> testData;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		testData = data.get(0);
		page = new BasePageControl();

		// Create New Account
		Assertions.passTest("Home Page", "Page Navigated");
		homePage = new HomePage();
		page = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "Values Entered");
		dwellingPage = (DwellingPage) page;

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", "Page Navigated");
		dwellingPage = new DwellingPage();
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + "Values Entered");

		// Entering Quote Details
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = createQuotePage.enterQuoteDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		int nbLength = accountOverviewPage.quote1Num.getData().length();
		String nbQuote = accountOverviewPage.quote1Num.getData().substring(1, nbLength - 1);
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();

		// Asserting view print quote link displays the quote page
		accountOverviewPage.quoteNumberVPQ.formatDynamicPath(nbQuote).scrollToElement();
		Assertions.verify(accountOverviewPage.quoteNumberVPQ.formatDynamicPath(nbQuote).getData().contains(nbQuote),
				true, "View/Print Full Quote Page", "Quote Number from quote document is verified", true, true);
		Assertions.passTest("Account Overview Page", "Account Overview Page loaded sucessfully");
		accountOverviewPage.waitTime(5);
		accountOverviewPage.scrollToTopPage();
		accountOverviewPage.goBackBtn.click();

		// Clicking on Bind button in account Overview Page
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, nbQuote);
		Assertions.passTest("Account Overview Page", "Quote generated successfully");
		quoteNumber = requestBindPage.quoteNumber.getData();
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		// Entering Bind Details
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		page = requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Getting NB Policy Number
		policySummarypage = (PolicySummaryPage) page;
		Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
		policyNumber = policySummarypage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "NB Policy Number is : " + policyNumber);

		// Clicking on Renew Policy Link
		policySummarypage.renewPolicy.scrollToElement();
		policySummarypage.renewPolicy.click();
		if (policySummarypage.pageName.getData().contains("Transfer")) {
			policySummarypage.transferContinue.scrollToElement();
			policySummarypage.transferContinue.click();
		}
		int renewalLength = accountOverviewPage.quoteNoHolder.getData().length();
		String renewalQuote = accountOverviewPage.quoteNoHolder.getData().substring(1, renewalLength - 1);
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();

		// Asserting view print quote link displays the quote page
		accountOverviewPage.quoteNumberVPQ.formatDynamicPath(renewalQuote).scrollToElement();
		Assertions.verify(
				accountOverviewPage.quoteNumberVPQ.formatDynamicPath(renewalQuote).getData().contains(renewalQuote),
				true, "View/Print Full Quote Page", "Quote Number from quote document is verified", true, true);
		Assertions.passTest("Account Overview Page", "Account Overview Page loaded sucessfully");
		accountOverviewPage.waitTime(5);
		accountOverviewPage.scrollToTopPage();
		accountOverviewPage.goBackBtn.click();

		// creating renewal requote
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();
		accountOverviewPage.editDeductibleAndLimits.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Account Overview Page", "Clicked on Edit Deductible and Limits sucessfully");
		Assertions.passTest("Create Quote Page", "Create Quote Page loaded sucessfully");
		createQuotePage.enterDeductibles(testData);
		testData = data.get(1);
		// createQuotePage.enterFloodCovA(testData);
		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		BuildingUnderMinimumCostPage minimumCost = new BuildingUnderMinimumCostPage();
		if (minimumCost.override.checkIfElementIsPresent() && minimumCost.override.checkIfElementIsDisplayed()) {
			minimumCost.clickOnOverride();
		}

		// Quote 2 Generated
		Assertions.passTest("Account Overview Page", "Account Overview Page loaded sucessfully");
		Assertions.passTest("Account Overview Page", "Quote 2 is Generated");
		Assertions.passTest("Account Overview Page",
				"Quote 2 - Quote Number : " + accountOverviewPage.quoteNumber.getData());
		accountOverviewPage.releaseRenewalToProducerButton.click();
		accountOverviewPage.releaseRenewalToProducerButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Account Overview Page", "Values Verified");
		accountOverviewPage.issueQuoteButton.scrollToElement();
		accountOverviewPage.issueQuoteButton.click();
		int renewalReLength = accountOverviewPage.quoteNoHolder.getData().length();
		String renewalReQuote = accountOverviewPage.quoteNoHolder.getData().substring(1, renewalReLength - 1);
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();

		// Asserting view print quote link displays the quote page
		accountOverviewPage.quoteNumberVPQ.formatDynamicPath(renewalReQuote).scrollToElement();
		Assertions.verify(
				accountOverviewPage.quoteNumberVPQ.formatDynamicPath(renewalReQuote).getData().contains(renewalReQuote),
				true, "View/Print Full Quote Page", "Quote Number from quote document is verified", true, true);
		Assertions.passTest("Account Overview Page", "Account Overview Page loaded sucessfully");
		accountOverviewPage.waitTime(5);
		accountOverviewPage.scrollToTopPage();
		accountOverviewPage.goBackBtn.click();

		// Binding the issued quote
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();
		testData = data.get(0);

		// Entering data in Request Bind Page
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", "Entered Values successfully");
		requestBindPage.submit.click();
		requestBindPage.requestBind.waitTillVisibilityOfElement(60);
		requestBindPage.requestBind.click();
		requestBindPage.requestBind.waitTillInVisibilityOfElement(60);

		// Override Effective date in Request Bind Page
		if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
				&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
			requestBindPage.overrideEffectiveDate.scrollToElement();
			requestBindPage.overrideEffectiveDate.select();
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			requestBindPage.requestBind.scrollToElement();
			requestBindPage.requestBind.click();
			requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
		}

		// Getting Renewal Policy Number
		Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfuly");
		Assertions.passTest("Policy Summary Page", "Renewal Policy Number :" + policySummarypage.getPolicynumber());

		// click on rewrite policy link
		policySummarypage.rewritePolicy.scrollToElement();
		policySummarypage.rewritePolicy.click();
		Assertions.passTest("Policy Summary Page", "Clicked on Rewrite Policy link");

		// click on dwelling 1-1
		accountOverviewPage.dwellingLink.scrollToElement();
		accountOverviewPage.dwellingLink.click();

		// click on edit dwelling link
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();
		Assertions.passTest("Account overview Page", "Clicked on Edit Dwelling link");

		// Edit living square footage
		dwellingPage.dwellingCharacteristicsLink.scrollToElement();
		dwellingPage.dwellingCharacteristicsLink.click();

		testData = data.get(1);
		dwellingPage.livingSquareFootage.waitTillPresenceOfElement(60);
		dwellingPage.livingSquareFootage.waitTillVisibilityOfElement(60);
		dwellingPage.livingSquareFootage.scrollToElement();
		dwellingPage.livingSquareFootage.clearData();
		if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()
				&& dwellingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
			dwellingPage.expiredQuotePopUp.scrollToElement();
			dwellingPage.continueWithUpdateBtn.scrollToElement();
			dwellingPage.continueWithUpdateBtn.click();
			dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
		}

		// change living sq footage
		dwellingPage.waitTime(2);
		dwellingPage.livingSquareFootage.setData(testData.get("L1D1-DwellingSqFoot"));
		Assertions.passTest("Dwelling Page", "Living square footage modifed successfully");

		// review dwelling
		dwellingPage.reviewDwelling();
		Assertions.passTest("Dwelling Page", "Clicked on Review Dwelling");

		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		if (buildingNoLongerQuotablePage.pageName.getData().contains("Buildings No")) {
			buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
		}

		testData = data.get(0);
		createQuotePage.enterQuoteDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		accountOverviewPage.rewriteBind.scrollToElement();
		accountOverviewPage.rewriteBind.click();
		requestBindPage.rewrite.waitTillVisibilityOfElement(60);
		Assertions.passTest("Account Overview Page", "Clicked on Rewrite Bind");

		// Entering data in Request Bind Page
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");
		testData = data.get(1);
		requestBindPage.previousPolicyCancellationDate.waitTillVisibilityOfElement(60);
		requestBindPage.previousPolicyCancellationDate.setData(testData.get("PolicyEffDate"));
		requestBindPage.previousPolicyCancellationDate.tab();

		if (requestBindPage.internalComments.checkIfElementIsPresent()) {
			requestBindPage.internalComments.scrollToElement();
			requestBindPage.internalComments.setData("Test");
		}
		if (requestBindPage.externalComments.checkIfElementIsPresent()) {
			requestBindPage.externalComments.scrollToElement();
			requestBindPage.externalComments.setData("Test");
		}

		requestBindPage.rewrite.scrollToElement();
		requestBindPage.rewrite.click();

		if (requestBindPage.backdatingRewrite.checkIfElementIsPresent()
				&& requestBindPage.backdatingRewrite.checkIfElementIsDisplayed()) {
			requestBindPage.backdatingRewrite.waitTillVisibilityOfElement(60);
			requestBindPage.backdatingRewrite.scrollToElement();
			requestBindPage.backdatingRewrite.click();
		}
		Assertions.passTest("Request Bind Page", "Rewrite Bind details entered successfully");
		// Getting Policy Number
		Assertions.passTest("Policy Summary Page", "Policy Summary page loaded successfully");
		Assertions.passTest("Policy Summary Page", "Rewritten policy Number : " + policySummarypage.getPolicynumber());

		Assertions.passTest("PNB_Regression_TC015", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}

}
