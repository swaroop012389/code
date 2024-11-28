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
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC049 extends AbstractNAHOTest {

	public PNBTC049() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID49.xls";
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
	String rewrittenPolicyNumber;
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
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
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
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Created Policy is selected");

		// Check if the NRNL date is displayed according to the Notice period on Policy
		// Summary page.
		// click on renewal indicators link
		policySummaryPage.renewalIndicators.scrollToElement();
		policySummaryPage.renewalIndicators.click();
		Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

		// select non renewal check box
		Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
				"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);
		renewalIndicatorsPage.nonRenewal.scrollToElement();
		renewalIndicatorsPage.nonRenewal.select();
		Assertions.passTest("Renewal Indicators Page", "Selected the Non Renewal Checkbox");

		// select non renewal reason and enter legal notice wording
		renewalIndicatorsPage.nonRenewalReasonArrow.scrollToElement();
		renewalIndicatorsPage.nonRenewalReasonArrow.click();
		renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason"))
				.scrollToElement();
		renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason")).click();
		renewalIndicatorsPage.nonRenewalLegalNoticeWording.setData(testData.get("LegalNoticeWording"));

		// click on update
		renewalIndicatorsPage.updateButton.scrollToElement();
		renewalIndicatorsPage.updateButton.click();
		Assertions.passTest("Renewal Indicators Page", "Clicked on Update Button");

		Assertions.verify(policySummaryPage.renewalDelMsg.getData().contains("Renewal Indicators Successfully Updated"),
				true, "Policy Summary Page", "The Message displayed is " + policySummaryPage.renewalDelMsg.getData(),
				false, false);
		Assertions.addInfo("Policy Summary Page", "Verifying the Auto-renewal indicator status of the application");
		Assertions.verify(policySummaryPage.autoRenewalIndicators.getData().contains("Non-Renewal"), true,
				"Policy Summary Page",
				"The status of the application changed to :" + policySummaryPage.autoRenewalIndicators.getData(), false,
				false);
		Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

		// Rewrite policy
		policySummaryPage.rewritePolicy.scrollToElement();
		policySummaryPage.rewritePolicy.click();
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
		accountOverviewPage.waitTime(3); // added waittime to work in headless mode
		accountOverviewPage.rewriteBind.click();
		// Enter New Effective Date
		testData = data.get(dataValue2);
		requestBindPage.effectiveDate.scrollToElement();
		requestBindPage.effectiveDate.setData(testData.get("PolicyEffDate"));
		requestBindPage.effectiveDate.tab();
		requestBindPage.waitTime(3);// adding waitTime to load the page
		requestBindPage.previousPolicyCancellationDate.scrollToElement();
		requestBindPage.waitTime(3);
		requestBindPage.previousPolicyCancellationDate.setData(testData.get("CancellationEffectiveDate"));
		Assertions.verify(
				requestBindPage.previousPolicyEffDate.getData().equals(testData.get("CancellationEffectiveDate")), true,
				"Request Bind Page", "Previous Policy Effective Date and Previous Policy cancellation date is same :"
						+ requestBindPage.previousPolicyEffDate.getData(),
				false, false);

		// cancel Re-write Section
		requestBindPage.waitTime(3);
		requestBindPage.cancelRewriteHeader.waitTillVisibilityOfElement(60);
		String originalInspFee = requestBindPage.originalInspectionFee.getData();
		String earnedInspFee = requestBindPage.earnedInspectionFee.getData();
		String OriginalPolFee = requestBindPage.originalPolicyFee.getData();
		String earnedPolFee = requestBindPage.earnedPolicyFee.getData();
		Assertions.verify(originalInspFee, "$70", "Request Bind Page",
				"Original Inspection Fee:" + requestBindPage.originalInspectionFee.getData(), false, false);
		Assertions.verify(earnedInspFee, "0.0", "Request Bind Page",
				"Earned Inspection Fee:" + requestBindPage.earnedInspectionFee.getData(), false, false);
		Assertions.verify(OriginalPolFee, "$30", "Request Bind Page",
				"Original Policy Fee:" + requestBindPage.originalPolicyFee.getData(), false, false);
		Assertions.verify(earnedPolFee, "0.0", "Request Bind Page",
				"Earned Policy Fee:" + requestBindPage.earnedPolicyFee.getData(), false, false);
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

		// Validate the Previous Transaction Summary Details
		policySummaryPage.PremiumFee.formatDynamicPath(1).scrollToElement();
		policySummaryPage.PremiumFee.formatDynamicPath(1).waitTillVisibilityOfElement(60);

		// Transaction Values
		Assertions.passTest("Policy Summary Page",
				"Active Transaction Premium : " + policySummaryPage.PremiumFee.formatDynamicPath(1).getData());
		Assertions.passTest("Policy Summary Page", "Active Transaction Inspection Fee : "
				+ policySummaryPage.PremiunInspectionFee.formatDynamicPath(1).getData());
		Assertions.passTest("Policy Summary Page",
				"Active Transaction Policy Fee : " + policySummaryPage.PremiumPolicyFee.formatDynamicPath(1).getData());

		// Annual values
		Assertions.passTest("Policy Summary Page",
				"Active Annual Premium : " + policySummaryPage.PremiumFee.formatDynamicPath(2).getData());
		Assertions.passTest("Policy Summary Page", "Active Annual Inspection  Fee : "
				+ policySummaryPage.PremiunInspectionFee.formatDynamicPath(2).getData());
		Assertions.passTest("Policy Summary Page",
				"Active Annual Policy Fee : " + policySummaryPage.PremiumPolicyFee.formatDynamicPath(2).getData());

		// Term Total values
		Assertions.passTest("Policy Summary Page",
				"Active Term Total Premium : " + policySummaryPage.PremiumFee.formatDynamicPath(3).getData());
		Assertions.passTest("Policy Summary Page", "Active Term Total Inspection Fee : "
				+ policySummaryPage.PremiunInspectionFee.formatDynamicPath(3).getData());
		Assertions.passTest("Policy Summary Page",
				"Active Term Total Policy Fee : " + policySummaryPage.PremiumPolicyFee.formatDynamicPath(3).getData());

		// Total Premium
		Assertions.passTest("Policy Summary Page", "Active Transaction Total Premium : "
				+ policySummaryPage.policyTotalPremium.formatDynamicPath(1).getData());

		// Validate the Previous Policy status
		policySummaryPage.rewrittenPolicyNumber.scrollToElement();
		policySummaryPage.rewrittenPolicyNumber.waitTillVisibilityOfElement(60);
		policySummaryPage.rewrittenPolicyNumber.click();

		// Transaction History Table
		policySummaryPage.waitTime(3);
		policySummaryPage.transHistReason.formatDynamicPath(3).waitTillVisibilityOfElement(60);
		policySummaryPage.transHistReason.formatDynamicPath(3).click();

		// Validate the Previous Transaction Summary Details
		policySummaryPage.PremiumFee.formatDynamicPath(1).scrollToElement();
		policySummaryPage.PremiumFee.formatDynamicPath(1).waitTillVisibilityOfElement(60);
		Assertions.passTest("Policy Summary Page", "Cancelled Policy Transaction Status");
		// Transaction Values
		Assertions.passTest("Policy Summary Page",
				"Cancelled Transaction Premium : " + policySummaryPage.PremiumFee.formatDynamicPath(1).getData());
		Assertions.passTest("Policy Summary Page", "Cancelled Transaction Inspection Fee : "
				+ policySummaryPage.PremiunInspectionFee.formatDynamicPath(1).getData());
		Assertions.passTest("Policy Summary Page", "Cancelled Transaction Policy Fee : "
				+ policySummaryPage.PremiumPolicyFee.formatDynamicPath(1).getData());

		// Annual values
		Assertions.passTest("Policy Summary Page",
				"Cancelled Annual Premium : " + policySummaryPage.PremiumFee.formatDynamicPath(2).getData());
		Assertions.passTest("Policy Summary Page", "Cancelled Annual Inspection  Fee : "
				+ policySummaryPage.PremiunInspectionFee.formatDynamicPath(2).getData());
		Assertions.passTest("Policy Summary Page",
				"Cancelled Annual Policy Fee : " + policySummaryPage.PremiumPolicyFee.formatDynamicPath(2).getData());

		// Term Total values
		Assertions.passTest("Policy Summary Page",
				"Cancelled Term Total Premium : " + policySummaryPage.PremiumFee.formatDynamicPath(3).getData());
		Assertions.passTest("Policy Summary Page", "Cancelled Term Total Inspection Fee : "
				+ policySummaryPage.PremiunInspectionFee.formatDynamicPath(3).getData());
		Assertions.passTest("Policy Summary Page", "Cancelled Term Total Policy Fee : "
				+ policySummaryPage.PremiumPolicyFee.formatDynamicPath(3).getData());

		// Total Premium
		Assertions.passTest("Policy Summary Page",
				"Transaction Total Premium : " + policySummaryPage.policyTotalPremium.formatDynamicPath(1).getData());
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC049", "Executed Successfully");
	}
}
