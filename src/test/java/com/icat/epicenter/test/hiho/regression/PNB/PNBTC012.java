/** Program Description: To check whether the USM is able to Re-instate the policy after cancellation
 *  Author			   : Aishwarya Rangasamy
 *  Date of Creation   : 06/09/2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC012 extends AbstractNAHOTest {

	public PNBTC012() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID12.xls";
	}

	HomePage homePage;
	DwellingPage dwellingPage;
	LocationPage locationPage;
	CreateQuotePage createQuotePage;
	AccountOverviewPage accountOverviewPage;
	PolicySummaryPage policySummaryPage;
	RequestBindPage requestBindPage;
	CancelPolicyPage cancelPolicyPage;
	ReinstatePolicyPage reinsatePolicyPage;
	Map<String, String> testData;
	BasePageControl basePage = new BasePageControl();
	String quoteNumber;
	String policyNumber;
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_ENTERED = "Values Entered successfully";
	static int dataValue1 = 0;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		locationPage = new LocationPage();
		dwellingPage = new DwellingPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		cancelPolicyPage = new CancelPolicyPage();
		reinsatePolicyPage = new ReinstatePolicyPage();
		testData = data.get(dataValue1);

		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);
		dwellingPage = (DwellingPage) basePage;

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage = new DwellingPage();
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		accountOverviewPage.clickOnRequestBind(setUpData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policySummaryPage.getPolicynumber());

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", PAGE_NAVIGATED);

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
		cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("PolicyEffDate"));
		Assertions.passTest("Cancel Policy Page", "Details entered successfully");
		cancelPolicyPage.nextButton.scrollToElement();
		cancelPolicyPage.nextButton.click();
		cancelPolicyPage.completeTransactionButton.scrollToElement();
		cancelPolicyPage.completeTransactionButton.click();

		// Validate Cancellation Message
		cancelPolicyPage.cancellationSuccess.scrollToElement();
		String actualCancellationMsg = cancelPolicyPage.cancellationSuccess.getData();
		Assertions.verify(actualCancellationMsg, "Cancellation Successful", "Cancellation Result Page",
				"Validation of the Header - Cancellation Successful", false, false);
		cancelPolicyPage.closeButton.scrollToElement();
		cancelPolicyPage.closeButton.click();

		// Validate Policy Status - Cancelled
		String cancellationStatus = cancelPolicyPage.cancellationStatus.getData();
		Assertions.verify(cancellationStatus, "Cancelled", "Policy Summary Page",
				"Validation of Policy Cancellation Status", false, false);

		// Verifying the Renewal link non-available on flat cancelled policy Summary
		Assertions.addInfo("Policy Summary Page",
				"Verifying the non-availability of the Renewal Indicator Link on Flat Cancelled Policy");
		Assertions.verify(!policySummaryPage.renewalIndicators.checkIfElementIsPresent(), true, "Policy Summary Page",
				"Renewal Policy Link not diasplayed", false, false);

		// Verifying the Renew Policy link non-available on flat cancelled policy
		// Summary Page
		Assertions.addInfo("Policy Summary Page",
				"Verifying the non-availability of the Renew Policy link on flat cancelled policy Summary Page");
		Assertions.verify(!policySummaryPage.renewPolicy.checkIfElementIsPresent(), true, "Policy Summary Page",
				"Renew Policy Link not diasplayed", false, false);

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", PAGE_NAVIGATED);

		// Find the policy by entering policy Number
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Created Policy is selected");

		// Reinstate Policy
		policySummaryPage.reinstatePolicy.scrollToElement();
		policySummaryPage.reinstatePolicy.click();
		reinsatePolicyPage.reinstateComments.setData(testData.get("ReinstateComments"));
		reinsatePolicyPage.completeReinstatement.scrollToElement();
		reinsatePolicyPage.completeReinstatement.click();
		Assertions.passTest("Reinstate Policy Page", "Entered Reinstate comments and completed Transaction");

		// Validate Reinstate message
		reinsatePolicyPage.reinstateSuccess.scrollToElement();
		String actualReinstateMsg = reinsatePolicyPage.reinstateSuccess.getData();
		Assertions.verify(actualReinstateMsg, "Reinstatement Complete", "Reinstate Results Page",
				"Validation of the Header - Reinstatement completed", false, false);
		cancelPolicyPage.closeButton.scrollToElement();
		cancelPolicyPage.closeButton.click();

		// Validate Policy Status - Active
		String policyStatus = policySummaryPage.activeStatus.getData();
		Assertions.verify(policyStatus, "Active", "Policy Summary Page", "Validation of Policy Reinstate Status", false,
				false);
		Assertions.passTest("PNB_Regression_TC012", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}