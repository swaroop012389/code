/** Program Description: To check that the USM is able process a BOR and requote the policy into a Rewritten policy.
 *  Author			   : Aishwarya Rangasamy
 *  Date of Creation   : 07/09/2018
 **/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BrokerOfRecordPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC007 extends AbstractNAHOTest {

	public PNBTC007() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID07.xls";
	}

	HomePage homePage;
	DwellingPage dwellingPage;
	LocationPage locationPage;
	CreateQuotePage createQuotePage;
	AccountOverviewPage accountOverviewPage;
	PolicySummaryPage policySummaryPage;
	RequestBindPage requestBindPage;
	CancelPolicyPage cancelPolicyPage;
	BrokerOfRecordPage brokerOfRecordPage;
	Map<String, String> testData;
	BasePageControl basePage = new BasePageControl();
	String quoteNumber;
	String policyNumber;
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_ENTERED = "Values Entered successfully";
	static int dataValue1 = 0;
	static int dataValue2 = 1;

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
		brokerOfRecordPage = new BrokerOfRecordPage();
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
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
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
		homePage.findFilterArrow.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Created Policy is selected");

		// Rewrite policy
		policySummaryPage.rewritePolicy.scrollToElement();
		policySummaryPage.rewritePolicy.click();
		accountOverviewPage.producerLink.waitTillButtonIsClickable(60);
		accountOverviewPage.producerLink.waitTillVisibilityOfElement(60);
		accountOverviewPage.producerLink.scrollToElement();
		accountOverviewPage.producerLink.click();
		brokerOfRecordPage.newProducerNumber.setData("11250.1");
		brokerOfRecordPage.borStatusArrow.scrollToElement();
		brokerOfRecordPage.borStatusArrow.click();
		brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();
		brokerOfRecordPage.processBOR.click();
		cancelPolicyPage.closeButton.scrollToElement();
		cancelPolicyPage.closeButton.click();
		Assertions.passTest("Broker of Record Page", "Details Entered successfully");

		// Create Another Quote and Modify NH values
		accountOverviewPage.createAnotherQuote.click();
		testData = data.get(dataValue2);
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.scrollToElement();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.click();
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Creation of Another quote is successful");
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		accountOverviewPage.rewriteBind.scrollToElement();
		// adding hard coded waitTime
		accountOverviewPage.waitTime(3); // added wait time to work in headless mode
		accountOverviewPage.rewriteBind.click();
		requestBindPage.previousPolicyCancellationDate.scrollToElement();
		testData = data.get(dataValue1);
		requestBindPage.previousPolicyCancellationDate.setData(testData.get("PolicyEffDate"));
		requestBindPage.rewrite.waitTillButtonIsClickable(60);
		requestBindPage.rewrite.waitTillPresenceOfElement(60);
		requestBindPage.rewrite.waitTillVisibilityOfElement(60);
		requestBindPage.rewrite.scrollToElement();
		// adding hard coded waitTime
		requestBindPage.waitTime(3); // added wait time to work in headless mode
		requestBindPage.rewrite.click();
		Assertions.passTest("Policy Summary Page", "New Policy Number is: " + policySummaryPage.policyNumber.getData()
				+ " and Rewritten Policy Number is: " + policySummaryPage.rewrittenPolicyNumber.getData());

		// Validate the Previous Policy status
		String activeStatusData = policySummaryPage.activeStatus.getData();
		Assertions.verify(activeStatusData, "Active", "Policy Summary Page", "Validation of New Policy Status - Active",
				false, false);

		// Validate the Previous Policy status
		policySummaryPage.rewrittenPolicyNumber.scrollToElement();
		policySummaryPage.rewrittenPolicyNumber.click();
		String cancellationStatusData = policySummaryPage.activeStatus.getData();
		Assertions.verify(cancellationStatusData, "Cancelled", "Policy Summary Page",
				"Validation of Old Policy Status - Cancelled", false, false);
		Assertions.passTest("PNB_Regression_TC007", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}