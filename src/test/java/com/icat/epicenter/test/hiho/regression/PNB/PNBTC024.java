/** Program Description: To check  the pro-rata cancellation without minimum earned
 *  Author			   : SM Netserv
 *  Date of Creation   : Sep 2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC024 extends AbstractNAHOTest {

	public PNBTC024() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID24.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		LocationPage locationPage = new LocationPage();
		DwellingPage dwellingPage = new DwellingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		int dataValue1 = 0;
		String quoteNumber;
		Map<String, String> testData = data.get(dataValue1);

		// New account
		Assertions.passTest("Home Page", "Page Navigated");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "Values Entered successfully");

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", "Page Navigated");
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 Values Entered successfully");

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		String policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policySummaryPage.getPolicynumber());

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", "Page Navigated");

		// Find the policy by entering policy Number
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Created Policy is selected");

		// Cancel policy
		policySummaryPage.cancelPolicy.waitTillPresenceOfElement(60);
		policySummaryPage.cancelPolicy.waitTillVisibilityOfElement(60);
		policySummaryPage.cancelPolicy.waitTillElementisEnabled(60);
		policySummaryPage.cancelPolicy.waitTillButtonIsClickable(60);
		policySummaryPage.cancelPolicy.scrollToElement();
		policySummaryPage.cancelPolicy.click();
		cancelPolicyPage.cancelReasonArrow.scrollToElement();
		cancelPolicyPage.cancelReasonArrow.click();
		cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
		cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
		cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
		Assertions.passTest("Cancel Policy Page", "Details entered successfully");
		cancelPolicyPage.nextButton.scrollToElement();
		cancelPolicyPage.nextButton.click();

		// Enter UW comments
		cancelPolicyPage.underwriterComment.setData("Policy Cancelled with Pro rata");
		cancelPolicyPage.completeTransactionButton.scrollToElement();
		cancelPolicyPage.completeTransactionButton.click();
		cancelPolicyPage.cancellationSuccess.scrollToElement();
		String actualCancellationMsg = cancelPolicyPage.cancellationSuccess.getData();
		Assertions.verify(actualCancellationMsg, "Cancellation Successful", "Cancellation Result Page",
				"Validation of the Header - Cancellation Successful", false, false);
		cancelPolicyPage.closeButton.click();
		String cancellationStatus = cancelPolicyPage.cancellationStatus.getData();
		Assertions.verify(cancellationStatus, "NOC", "Policy Summary Page", "Validation of Policy Cancellation Status",
				false, false);

		// Verifying the Renewal Indicator and Renewal Hyper link on Policy Summary Page
		// For Non-Renewal Scenarios
		Assertions.addInfo("Policy Summary Page",
				"Verifying the Renewal Indicator and Renewal Hyper link on Policy Summary Page For Non-Renewal Scenarios");

		// Verifying the Renew policy link not available on cancelled policy Summary
		Assertions.addInfo("Policy Summary Page", "Renewal Policy Link should not diasplayed");
		Assertions.verify(!policySummaryPage.renewPolicy.checkIfElementIsPresent(), true, "Policy Summary Page",
				"Renewal Policy Link not diasplayed", false, false);

		// Verifying the Renewal Indicators link not available on cancelled policy
		// Summary
		Assertions.addInfo("Policy Summary Page", "Renewal Indicators Policy Link should not diasplayed");
		Assertions.verify(!policySummaryPage.renewalIndicators.checkIfElementIsPresent(), true, "Policy Summary Page",
				"Renewal Indicators Policy Link not diasplayed", false, false);

		Assertions.passTest("PNB_Regression_TC024", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}