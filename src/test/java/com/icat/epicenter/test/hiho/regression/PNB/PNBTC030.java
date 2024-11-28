/** Program Description: Validate non-renewal, renewal indicator
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

public class PNBTC030 extends AbstractNAHOTest {

	public PNBTC030() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID30.xls";
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
		locationPage.enterLocationDetails(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Location 1 Dwelling 1 Details
		dwellingPage = new DwellingPage();
		Assertions.passTest("Dwelling Page", "PAGE_NAVIGATED");
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + "VALUES_ENTERED");

		// Entering Quote Details
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = createQuotePage.enterQuoteDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		// Clicking on Bind button in account Overview Page
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Account Overview Page", "Quote generated successfully");

		// Entering Bind Details
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		page = requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Getting NB Policy Number
		policySummaryPage = (PolicySummaryPage) page;
		policySummaryPage = new PolicySummaryPage();
		Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "NB Policy Number is : " + policyNumber);

		// Clicking on Renewal Indicators Link in Policy Summary Page
		policySummaryPage.renewalIndicators.scrollToElement();
		policySummaryPage.renewalIndicators.click();
		Assertions.passTest("Renewal Indicators Page", "Renewal Indicators Page loaded successfully");
		renewalIndicatorsPage = new RenewalIndicatorsPage();

		// Selecting Non Renewal Check box
		renewalIndicatorsPage.nonRenewal.scrollToElement();
		renewalIndicatorsPage.nonRenewal.select();
		Assertions.passTest("Renewal Indicators Page", "Non Renewal Checkbox checked successfully");

		// Entering Non Renewals Details
		renewalIndicatorsPage.nonRenewalReasonArrow.waitTillVisibilityOfElement(60);
		renewalIndicatorsPage.nonRenewalReasonArrow.scrollToElement();
		renewalIndicatorsPage.nonRenewalReasonArrow.click();
		renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason"))
				.scrollToElement();
		renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason")).click();
		renewalIndicatorsPage.nonRenewalLegalNoticeWording.scrollToElement();
		renewalIndicatorsPage.nonRenewalLegalNoticeWording.setData("PNBTC002 Renewal Validation");
		renewalIndicatorsPage.nonRenewalInternalComments.scrollToElement();
		renewalIndicatorsPage.nonRenewalInternalComments.setData("PNBTC002 Renewal Validation");
		Assertions.passTest("Renewal Indicators Page", "Non Renewals details entered successfully");

		// Clicking on Update Button
		renewalIndicatorsPage.updateButton.scrollToElement();
		renewalIndicatorsPage.updateButton.click();
		renewalIndicatorsPage.updateButton.waitTillInVisibilityOfElement(60);

		// Verifying Renew Policy Link is not present
		Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
		Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsPresent(), false, "Policy Summary Page",
				"Renew Policy Link is not present is verified", false, false);
		Assertions.passTest("Policy Summary Page", "Policy Summary Page verification done successfully");

		// Again clicking on Renewal Indicators Hyperlink
		policySummaryPage.renewalIndicators.scrollToElement();
		policySummaryPage.renewalIndicators.click();
		Assertions.passTest("Renewal Indicators Page", "Renewal Indicators Page loaded successfully");

		// De-selecting Non Renewal check box and updating
		renewalIndicatorsPage.nonRenewal.scrollToElement();
		renewalIndicatorsPage.nonRenewal.deSelect();
		Assertions.passTest("Renewal Indicators Page", "Non Renewal Checkbox unchecked successfully");
		renewalIndicatorsPage.updateButton.scrollToElement();
		renewalIndicatorsPage.updateButton.click();
		renewalIndicatorsPage.updateButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
		Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsPresent(), true, "Policy Summary Page",
				"Renew Policy Link is present is verified", false, false);
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		if (policySummaryPage.pageName.getData().contains("Transfer")) {
			policySummaryPage.transferContinue.scrollToElement();
			policySummaryPage.transferContinue.click();
		}
		Assertions.passTest("Account Overview Page", "Account Overview Page loaded successfully");
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		accountOverviewPage.releaseRenewalToProducerButton.waitTillInVisibilityOfElement(60);
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();

		// Entering data in Request Bind Page
		Assertions.passTest("Request Bind Page", "Page Navigated");
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", "Entered Values successfully");
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.requestBind.waitTillVisibilityOfElement(60);
		requestBindPage.requestBind.scrollToElement();
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
		Assertions.passTest("Policy Summary Page", "Page Navigated");
		Assertions.passTest("Policy Summary Page", "Renewal Policy Number :" + policySummaryPage.getPolicynumber());
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC030", "Executed Successfully");

	}
}
