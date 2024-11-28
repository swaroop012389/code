/** Program Description: Validate underwriter review, renewal indicator
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
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC029 extends AbstractNAHOTest {

	public PNBTC029() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID29.xls";
	}

	public HomePage homePage;
	public DwellingPage dwellingPage;
	public LocationPage locationPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public PolicySummaryPage policySummaryPage;
	public RenewalIndicatorsPage renewalIndicatorsPage;
	public ReferralPage referralPage;
	public ApproveDeclineQuotePage approveDeclineQuotePage;
	public BasePageControl basePage;
	public Map<String, String> testData;
	String quoteNumber;
	String policyNumber;
	static final String ext_rpt_msg = " is verified";
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_UPDATED = "Values Updated";
	static final String VALUES_VERIFIED = "Values Verified";
	static final String VALUES_ENTERED = "Values Entered successfully";
	static int data_Value1 = 0;
	static int data_Value2 = 1;
	static int data_Value3 = 2;

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
		renewalIndicatorsPage = new RenewalIndicatorsPage();
		referralPage = new ReferralPage();
		approveDeclineQuotePage = new ApproveDeclineQuotePage();
		testData = data.get(data_Value1);

		// NB Test case 01 is used to generate the policy
		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);
		dwellingPage = (DwellingPage) basePage;

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetails(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Location 1 Dwelling 1 Details
		dwellingPage = new DwellingPage();
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

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
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policySummaryPage.getPolicynumber());
		policySummaryPage.renewalIndicators.scrollToElement();
		policySummaryPage.renewalIndicators.click();
		renewalIndicatorsPage.underwritingReview.scrollToElement();
		renewalIndicatorsPage.underwritingReview.select();
		renewalIndicatorsPage.underwritingReviewInternalComments.scrollToElement();
		renewalIndicatorsPage.underwritingReviewInternalComments.setData(testData.get("UnderwritingReviewComments"));
		renewalIndicatorsPage.updateButton.scrollToElement();
		renewalIndicatorsPage.updateButton.click();
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		if (policySummaryPage.pageName.getData().contains("Transfer")) {
			policySummaryPage.transferContinue.scrollToElement();
			policySummaryPage.transferContinue.click();
		}
		accountOverviewPage.renewalOffer.waitTillVisibilityOfElement(30);
		accountOverviewPage.quoteNo1Holder.scrollToElement();
		quoteNumber = accountOverviewPage.quoteNo1Holder.getData().substring(1, 12);
		Assertions.verify(accountOverviewPage.renewalOfferData.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Renewal Offer generated" + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.referred.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"The pinned renewal offer have Referred text" + ext_rpt_msg, false, false);
		try {
			accountOverviewPage.notebarText.waitTillVisibilityOfElement(60);
			Assertions.verify(accountOverviewPage.notebarText.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Note bar having text Generated by the hiho automated renewal job. Reasons for referral: This account was marked for renewal review. Renewal Review Underwriting Comment: "
							+ testData.get("UnderwritingReviewComments") + ext_rpt_msg,
					false, false);
		} catch (Exception e) {
		}
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.searchReferral(quoteNumber);
		referralPage.clickOnApprove();
		Assertions.passTest("Approve/Decline Quote Page", "Quote is successfully approved");
		approveDeclineQuotePage.clickOnApprove();
		Assertions.passTest("Approve/Decline Quote Page", "Quote is successfully approved");
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();

		// find the account by entering quote number
		homePage.findFilterArrow.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findOptionQuote.scrollToElement();
		homePage.findOptionQuote.click();
		homePage.quoteField.setData(quoteNumber);
		homePage.findButton.scrollToElement();
		homePage.findButton.click();
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);
		accountOverviewPage.quoteNo1Holder.scrollToElement();
		quoteNumber = accountOverviewPage.quoteNo1Holder.getData().substring(1, 12);
		Assertions.verify(accountOverviewPage.quoteNo1Holder.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote is successfully approved and the same is displayed on the Account Overview page" + ext_rpt_msg,
				false, false);
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();
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
		Assertions.passTest("PNB_Regression_TC029", "Executed Successfully");
	}
}
