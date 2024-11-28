/** Program Description: To check that the cancellation effective date is calculated correctly based on NOC field input.
 *  Author			   : Yeshashwini T.A
 *  Date of Creation   : 07/09/2018
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
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC011 extends AbstractNAHOTest {

	public PNBTC011() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID11.xls";
	}

	HomePage homePage;
	DwellingPage dwellingPage;
	LocationPage locationPage;
	CreateQuotePage createQuotePage;
	AccountOverviewPage accountOverviewPage;
	PolicySummaryPage policySummaryPage;
	RequestBindPage requestBindPage;
	CancelPolicyPage cancelPolicyPage;
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
		if (dwellingPage.pageName.getData().contains("Dwelling values")) {
			createQuotePage.override.scrollToElement();
			createQuotePage.override.click();
			createQuotePage.override.waitTillInVisibilityOfElement(60);
		}
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		// Clicking on Bind button in account Overview Page
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

		// Entering Bind Details
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Getting NB Policy Number
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
		homePage.findBtnPolicy.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Home Page", "Created Policy is selected");

		// clicking on Cancel policy in Policy Summary page
		policySummaryPage.cancelPolicy.waitTillPresenceOfElement(60);
		policySummaryPage.cancelPolicy.waitTillVisibilityOfElement(60);
		policySummaryPage.cancelPolicy.waitTillElementisEnabled(60);
		policySummaryPage.cancelPolicy.waitTillButtonIsClickable(60);
		policySummaryPage.cancelPolicy.scrollToElement();
		policySummaryPage.cancelPolicy.click();

		// selecting the cancellation reason
		cancelPolicyPage.cancelReasonArrow.scrollToElement();
		cancelPolicyPage.cancelReasonArrow.click();
		cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();

		// verifying cancellation effective date before entering value in Days before
		// NOC field
		cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
		cancelPolicyPage.cancellationEffectiveDate.getData();
		Assertions.verify(cancelPolicyPage.cancellationEffectiveDate.checkIfElementIsDisplayed(), true,
				"Cancel Policy Page",
				"Cancellation Effective Date before NOC : " + cancelPolicyPage.cancellationEffectiveDate.getData(),
				false, false);
		Assertions.passTest("Cancel Policy Page", "Details entered successfully");
		cancelPolicyPage.cancelButton.scrollToElement();
		cancelPolicyPage.cancelButton.click();
		policySummaryPage.cancelPolicy.scrollToElement();
		policySummaryPage.cancelPolicy.click();

		// verifying cancellation effective date after entering value in Days before NOC
		// field
		cancelPolicyPage.daysBeforeNOC.scrollToElement();
		cancelPolicyPage.daysBeforeNOC.setData(testData.get("Cancellation_DaysBeforeNOC"));
		cancelPolicyPage.cancelReasonArrow.scrollToElement();
		cancelPolicyPage.cancelReasonArrow.click();
		cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
		Assertions.passTest("Cancel Policy Page", "Details entered successfully");
		cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
		cancelPolicyPage.cancellationEffectiveDate.getData();
		Assertions.verify(cancelPolicyPage.cancellationEffectiveDate.checkIfElementIsDisplayed(), true,
				"Cancel Policy Page", "Cancellation Effective Date after Days before NOC : "
						+ cancelPolicyPage.cancellationEffectiveDate.getData(),
				false, false);

		// clicking on next button
		cancelPolicyPage.nextButton.scrollToElement();
		cancelPolicyPage.nextButton.click();

		// clicking on complete transaction button
		cancelPolicyPage.completeTransactionButton.scrollToElement();
		cancelPolicyPage.completeTransactionButton.click();
		Assertions.passTest("PNB_Regression_TC011", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}
