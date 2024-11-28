/** Program Description: Renewal referral and binding
 *  Author			   : SM Netserv
 *  Date of Creation   : Oct 2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC037 extends AbstractNAHOTest {

	public PNBTC037() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID37.xls";
	}

	public HomePage homePage;
	public DwellingPage dwellingPage;
	public LocationPage locationPage;
	public BuildingUnderMinimumCostPage dwellingCostPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public PolicySummaryPage policySummaryPage;
	public LoginPage loginPage;
	public ReferQuotePage referQuotePage;
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
		loginPage = new LoginPage();
		dwellingCostPage = new BuildingUnderMinimumCostPage();
		referQuotePage = new ReferQuotePage();
		referralPage = new ReferralPage();
		approveDeclineQuotePage = new ApproveDeclineQuotePage();
		testData = data.get(data_Value1);

		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();

		// Login to producer account
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
		String covA_DwellingValue = testData.get("L1D1-DwellingCovA");
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

		// logout as producer
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Signed Out as Producer Successfully");

		// login as USM
		loginPage.waitTime(2);
		loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as USM Successfully");

		// Find the policy by entering policy Number
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.findAccountNamedInsured.scrollToElement();
		homePage.findFilterArrow.click();
		HyperLink findoption = new HyperLink(By.xpath("//a[text()='Policy']"));
		findoption.scrollToElement();
		findoption.click();
		homePage.policyNumber.waitTillVisibilityOfElement(60);
		homePage.policyNumber.scrollToElement();
		homePage.policyNumber.setData(policyNumber);
		homePage.policyNumber.tab();
		ButtonControl findButton = new ButtonControl(By.xpath(".//*[@id='search-policy']/fieldset/div[4]/div/button"));
		findButton.scrollToElement();
		findButton.click();

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

		// logout as USM
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Signed Out as USM Successfully");

		// Login to producer account
		loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as USM Successfully");

		// find the account by entering quote number in producer home page
		homePage.producerQuoteOption.scrollToElement();
		homePage.producerQuoteOption.click();
		homePage.quoteField.scrollToElement();
		homePage.quoteField.setData(quoteNumber);
		homePage.findBtnQuote.scrollToElement();
		homePage.findButton.click();
		homePage.findButton.waitTillInVisibilityOfElement(30);
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();
		accountOverviewPage.createAnotherQuote.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Account Overview Page", "Clicked on Create Another Quote");
		BaseWebElementControl moldCleanup = new BaseWebElementControl(
				By.xpath("//label[contains(text(),'Mold Clean Up')]//following-sibling::span//span[contains(text(),'"
						+ testData.get("Mold") + "')]"));
		Assertions.verify(moldCleanup.getData(), testData.get("Mold"), "Create Quote Page",
				"Mold Clean Up default value" + ext_rpt_msg, false, false);
		Assertions.verify(moldCleanup.getAttrributeValue("unselectable"), "on", "Create Quote Page",
				"Mold Clean Up is unchangeable" + ext_rpt_msg, false, false);
		int covA_Dwelling = Integer.parseInt(covA_DwellingValue);
		double covC_Dwelling = covA_Dwelling * (0.7) + 1;

		// enter coverage c value
		createQuotePage.covC_NHinputBox.scrollToElement();
		createQuotePage.covC_NHinputBox.clearData();
		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		createQuotePage.covC_NHinputBox.scrollToElement();
		createQuotePage.covC_NHinputBox.setData(String.valueOf(covC_Dwelling));
		createQuotePage.covC_NHinputBox.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.verify(referQuotePage.covCExceedWarning.getData(), "Coverage C cannot exceed 70% of Coverage A.",
				"Refer Quote Page", "Coverage C cannot exceed 70% of Coverage A. message " + ext_rpt_msg, false, false);
		referQuotePage.referQuote.scrollToElement();
		referQuotePage.referQuote.click();
		referQuotePage.referQuoteNumber.scrollToElement();
		String referquoteNumber = referQuotePage.referQuoteNumber.getData();

		// logout as producer
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Signed Out as USM Successfully");

		// login as USM
		loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as USM Successfully");
		homePage.searchReferral(referquoteNumber);
		Assertions.passTest("Home Page", "Referred quote number appearing in USM�s work queue " + ext_rpt_msg);
		referralPage.clickOnApprove();
		approveDeclineQuotePage.clickOnApprove();
		Assertions.passTest("Approve/Decline Quote Page", "Quote is successfully approved");
		if (policySummaryPage.pageName.getData().contains("Referral Complete")) {
			referQuotePage.referralClose.scrollToElement();
			referQuotePage.referralClose.click();
		}
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();

		// find the account by entering quote number
		homePage.findFilterArrow.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findOptionQuote.click();
		homePage.quoteField.setData(quoteNumber);
		homePage.findBtnQuote.click();
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);
		accountOverviewPage.quoteNoHolder.scrollToElement();
		quoteNumber = accountOverviewPage.quoteNoHolder.getData().substring(1, 12);
		Assertions.verify(accountOverviewPage.quoteNoHolder.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote is successfully approved and the same is displayed on the Account Overview page" + ext_rpt_msg,
				false, false);
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();

		// Entering data in Request Bind Page
		Assertions.passTest("Request Bind Page", "Page Navigated");
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", "Entered Values successfully");
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.submit.waitTillInVisibilityOfElement(30);
		requestBindPage.requestBind.scrollToElement();
		requestBindPage.requestBind.click();
		requestBindPage.requestBind.waitTillInVisibilityOfElement(60);

		// Override Effective date in Request Bind Page
		requestBindPage.overrideEffectiveDate.scrollToElement();
		requestBindPage.overrideEffectiveDate.select();
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.submit.waitTillInVisibilityOfElement(30);
		requestBindPage.requestBind.scrollToElement();
		requestBindPage.requestBind.click();
		requestBindPage.requestBind.waitTillInVisibilityOfElement(60);

		// Getting Renewal Policy Number
		Assertions.passTest("Policy Summary Page", "Page Navigated");
		Assertions.passTest("Policy Summary Page", "Renewal Policy Number :" + policySummaryPage.getPolicynumber());
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC037", "Executed Successfully");

	}
}
