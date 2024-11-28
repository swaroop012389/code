/** Program Description: Override premium on renewal requote as producer
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
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC040 extends AbstractNAHOTest {

	public PNBTC040() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID40.xls";
	}

	BasePageControl page;
	LoginPage login = new LoginPage();
	HomePage homePage = new HomePage();
	EligibilityPage eligibilityPage = new EligibilityPage();
	LocationPage locationPage = new LocationPage();
	BuildingPage buildingPage = new BuildingPage();
	DwellingPage dwellingPage = new DwellingPage();
	CreateQuotePage createQuotePage = new CreateQuotePage();
	SelectPerilPage selectPerilPage = new SelectPerilPage();
	AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
	RequestBindPage requestBindPage = new RequestBindPage();
	ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
	BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
	ReferralPage referralPage = new ReferralPage();
	PolicySummaryPage policySummarypage = new PolicySummaryPage();
	RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
	ApproveDeclineQuotePage approve_declinePage = new ApproveDeclineQuotePage();
	OverridePremiumAndFeesPage overridePremium_Fees = new OverridePremiumAndFeesPage();
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
		Assertions.passTest("HomePage", "New Account created successfully");

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");
		dwellingPage = new DwellingPage();
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");
		createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.override.waitTillVisibilityOfElement(60);
		createQuotePage.override.scrollToElement();
		createQuotePage.override.click();

		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		// Clicking on Bind button in account Overview Page
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		Assertions.passTest("Account Overview Page", "Quote generated successfully");
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

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
		Assertions.passTest("Account Overview Page", "Account Overview Page loaded sucessfully");
		String renewalquoteFees = accountOverviewPage.totalFeeAmount.getData();
		Assertions.passTest("Account Overview Page", "Policy Fees before overriding :" + renewalquoteFees);
		Assertions.passTest("Account Overview Page", "Total Premium before overriding :" + renewalquoteFees);
		accountOverviewPage.overridePremiumLink.scrollToElement();
		accountOverviewPage.overridePremiumLink.click();
		Assertions.passTest("Override Premium and Fees Page", "Override and Premium Fees loaded Successfully");
		overridePremium_Fees = new OverridePremiumAndFeesPage();
		overridePremium_Fees.totalInspectionFee.scrollToElement();
		overridePremium_Fees.totalInspectionFee.setData(testData.get("TotalInspectionFee"));
		overridePremium_Fees.policyFee.scrollToElement();
		overridePremium_Fees.policyFee.setData(testData.get("PolicyFee"));
		overridePremium_Fees.feeOverrideJustification.scrollToElement();
		overridePremium_Fees.feeOverrideJustification.setData(testData.get("FeeOverrideJustification"));
		overridePremium_Fees.overrideFeesButton.scrollToElement();
		overridePremium_Fees.overrideFeesButton.click();
		Assertions.passTest("Account Overview Page",
				"Policy Fees after overriding :" + accountOverviewPage.totalFeeAmount.getData());
		Assertions.passTest("Account Overview Page",
				"Total Premium after overriding :" + accountOverviewPage.totalPremiumAndFeeAmount.getData());
		if (!accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsPresent()) {
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			referralPage = homePage.searchReferral(quoteNumber);
			referralPage.clickOnApprove();
			if (approve_declinePage.pageName.getData().contains("Approve/Decline Quote")) {
				approve_declinePage.clickOnApprove();
			}
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Page Navigated");

			// Find the policy by entering policy Number
			homePage.findAccountNamedInsured.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findOptionQuote.scrollToElement();
			homePage.findOptionQuote.click();
			homePage.quoteField.scrollToElement();
			homePage.quoteField.setData(quoteNumber);
			homePage.findButton.scrollToElement();
			homePage.findButton.click();
			Assertions.passTest("Home Page", "Values Updated");
		}
		accountOverviewPage.releaseRenewalToProducerButton.click();
		accountOverviewPage.releaseRenewalToProducerButton.waitTillInVisibilityOfElement(60);

		Assertions.addInfo("Policy Summary Page", "Scenario For Non-Renewal Started "
				+ "initiate the renewal with relesing to producer and set the NRNL");
		// Adding the Non-Renewal indicator scenario
		// initiate the renewal with relesing to producer and set the NRNL
		accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
		accountOverviewPage.viewPreviousPolicyButton.click();
		// click on renewal indicators link

		policySummarypage.renewalIndicators.scrollToElement();
		policySummarypage.renewalIndicators.click();
		Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

		// select non renewal checkbox
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

		// Verifying the Renewal Indicator message on the Renewal Indicator Page
		Assertions.addInfo("Non-Renewal Indicator Page",
				"Verifying the Renewal Indicator message on the Non Renewal Indicator Page");
		String warningmessage = renewalIndicatorsPage.nonRenewalErrorMessage.getData();
		Assertions.verify(renewalIndicatorsPage.nonRenewalErrorMessage.getData().contains(warningmessage), true,
				"Non-Renewal Indicator Page",
				"The Non-Renewal warning message on renewal Indicator Page Diaplayed as: " + warningmessage, false,
				false);

		// Clicking on Cancel button
		renewalIndicatorsPage.cancelButton.scrollToElement();
		renewalIndicatorsPage.cancelButton.click();

		// Clicking on View Active Renewal policy Link on Policy Summary Page
		policySummarypage.viewActiveRenewal.scrollToElement();
		policySummarypage.viewActiveRenewal.click();

		Assertions.addInfo("Policy Summary Page", "Scenario For Non Renewal Ended");

		Assertions.passTest("Account Overview Page", "Values Verified");
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();

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
		Assertions.verify(policySummarypage.policyNumber.checkIfElementIsPresent(), true, "Policy Summary Page",
				"Renewal Policy No is displayed", false, false);
		Assertions.passTest("Policy Summary Page", "Renewal Policy Number :" + policySummarypage.getPolicynumber());
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC040", "Executed Successfully");

	}
}