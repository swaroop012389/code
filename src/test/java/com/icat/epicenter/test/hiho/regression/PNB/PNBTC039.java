/** Program Description: Update payment plan on renewal requote as producer
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
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EditPaymentPlanPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC039 extends AbstractNAHOTest {

	public PNBTC039() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID39.xls";
	}

	public HomePage homePage;
	public LocationPage locationPage;
	public DwellingPage dwellingPage;
	public BuildingUnderMinimumCostPage dwellingCostPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public PolicySummaryPage policySummaryPage;
	public LoginPage loginPage;
	public EditPaymentPlanPage editPaymentPlanPage;
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

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		locationPage = new LocationPage();
		createQuotePage = new CreateQuotePage();
		dwellingPage = new DwellingPage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		loginPage = new LoginPage();
		dwellingCostPage = new BuildingUnderMinimumCostPage();
		editPaymentPlanPage = new EditPaymentPlanPage();
		testData = data.get(data_Value1);

		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();

		// Login to USM account
		loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as Producer Successfully");

		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);
		dwellingPage = (DwellingPage) basePage;

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.addRoofDetails(testData, 1, 1);
		dwellingPage.enterDwellingValues(testData, 1, 1);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingCostPage.bringUpToCost.scrollToElement();
		dwellingCostPage.bringUpToCost.click();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

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

		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();

		// Find the policy by entering policy Number
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.findAccountNamedInsured.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.waitTillVisibilityOfElement(60);
		homePage.policyNumber.setData(policyNumber);
		homePage.policyNumber.tab();
		homePage.findBtnPolicy.click();

		// Click on Renew Policy Hyperlink
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		if (policySummaryPage.pageName.getData().contains("Transfer")) {
			policySummaryPage.transferContinue.scrollToElement();
			policySummaryPage.transferContinue.click();
		}
		accountOverviewPage.quoteNoHolder.scrollToElement();
		quoteNumber = accountOverviewPage.quoteNoHolder.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Renewal Quote Number : " + quoteNumber);
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();

		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();

		// find the account by entering quote number in producer home page
		homePage.producerQuoteOption.waitTillVisibilityOfElement(60);
		homePage.producerQuoteOption.scrollToElement();
		homePage.producerQuoteOption.click();
		homePage.quoteField.scrollToElement();
		homePage.quoteField.setData(quoteNumber);
		homePage.producerBtnQuote.scrollToElement();
		homePage.producerBtnQuote.click();
		homePage.producerBtnQuote.waitTillInVisibilityOfElement(30);
		accountOverviewPage.editPaymentPlan.scrollToElement();
		accountOverviewPage.editPaymentPlan.click();
		editPaymentPlanPage.fourPay.scrollToElement();
		editPaymentPlanPage.fourPay.click();
		editPaymentPlanPage.update.scrollToElement();
		editPaymentPlanPage.update.click();
		editPaymentPlanPage.update.waitTillInVisibilityOfElement(30);

		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();

		// find the account by entering quote number
		homePage.findFilterArrow.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterQuoteOption.click();
		homePage.findQuoteNameInsured.setData(quoteNumber);
		homePage.findBtnQuote.scrollToElement();
		homePage.findBtnQuote.click();
		Assertions.passTest("Account Overview Page", "Click on Request Bind Quote");
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();
		testData = data.get(data_Value1);

		// Entering data in Request Bind Page
		Assertions.passTest("Request Bind Page", PAGE_NAVIGATED);
		Assertions.verify(requestBindPage.fourPay.checkIfElementIsSelected(), true, "Request Bind Page",
				"System should display the selected payment plan as Insured � 4 Pay" + ext_rpt_msg, false, false);
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", VALUES_ENTERED);
		requestBindPage.submit.click();
		requestBindPage.submit.waitTillInVisibilityOfElement(30);
		requestBindPage.requestBind.click();
		requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
		requestBindPage.overrideEffectiveDate.scrollToElement();
		requestBindPage.overrideEffectiveDate.select();
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.submit.waitTillInVisibilityOfElement(30);
		requestBindPage.requestBind.click();
		requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Policy Summary Page", PAGE_NAVIGATED);
		Assertions.passTest("Policy Summary Page", "Renewal Policy Number :" + policySummaryPage.getPolicynumber());
		Assertions.verify(policySummaryPage.paymentPlan.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				policySummaryPage.paymentPlan.getData() + " selected" + ext_rpt_msg, false, false);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC039", "Executed Successfully");
	}
}
