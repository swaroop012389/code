/** Program Description: To check BOR status of the renewal quote
 *  Author			   : SM Netserv
 *  Date of Creation   : Sep 2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BrokerOfRecordPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC008 extends AbstractNAHOTest {

	public PNBTC008() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID08.xls";
	}

	public HomePage homePage;
	public LocationPage locationPage;
	public DwellingPage dwellingPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public PolicySummaryPage policySummaryPage;
	public BrokerOfRecordPage brokerOfRecordPage;
	public LoginPage loginPage;
	public BindRequestSubmittedPage bindRequestPage;
	public ReferralPage referralPage;
	public BasePageControl basePage;
	public Map<String, String> testData;
	String quoteNumber;
	String policyNumber;
	static final String ext_rpt_msg = " is verified";
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_UPDATED = "Values Updated";
	static final String VALUES_VERIFIED = "Values Verified";
	static final String VALUES_ENTERED = "Values Entered";
	static int data_Value1 = 0;
	static int data_Value2 = 1;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		locationPage = new LocationPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		brokerOfRecordPage = new BrokerOfRecordPage();
		loginPage = new LoginPage();
		bindRequestPage = new BindRequestSubmittedPage();
		referralPage = new ReferralPage();
		testData = data.get(data_Value1);

		// NB Test case 8 is used to generate the policy
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
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage = new DwellingPage();
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
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

		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policySummaryPage.getPolicynumber());

		// Find the policy by entering policy Number
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.waitTillVisibilityOfElement(60);
		homePage.policyNumber.scrollToElement();
		homePage.policyNumber.setData(policyNumber);
		homePage.policyNumber.tab();
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Values Updated");

		// Click on Renew Policy Hyperlink
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		if (policySummaryPage.pageName.getData().contains("Transfer")) {
			policySummaryPage.transferContinue.scrollToElement();
			policySummaryPage.transferContinue.click();
		}
		accountOverviewPage.quoteNoHolder.scrollToElement();
		quoteNumber = accountOverviewPage.quoteNoHolder.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Renewal Quote generated by system: " + quoteNumber);
		accountOverviewPage.producerLink.scrollToElement();
		accountOverviewPage.producerLink.click();
		String producerData = "9128.1";
		brokerOfRecordPage.newProducerNumber.scrollToElement();
		brokerOfRecordPage.newProducerNumber.setData(producerData);
		testData = data.get(data_Value1);
		brokerOfRecordPage.borStatusArrow.scrollToElement();
		brokerOfRecordPage.borStatusArrow.click();
		brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).waitTillVisibilityOfElement(60);
		brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).scrollToElement();
		brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();
		brokerOfRecordPage.processBOR.scrollToElement();
		brokerOfRecordPage.processBOR.click();
		brokerOfRecordPage.closeBORPage.scrollToElement();
		brokerOfRecordPage.closeBORPage.click();
		Assertions.passTest("Account Overview Page", "Account Name is " + testData.get("InsuredName"));
		testData = data.get(data_Value2);
		accountOverviewPage.releaseRenewalToProducerButton.waitTillVisibilityOfElement(60);
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Signed Out as USM Successfully");

		// Login to producer account
		loginPage.waitTime(2);
		loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as Producer Successfully");

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		WebElement renewalQuotesLink = WebDriverManager.getCurrentDriver()
				.findElement(By.xpath("//a[@id='activities-renewal']"));
		renewalQuotesLink.click();
		homePage.producerNameGrid.formatDynamicPath(testData.get("InsuredName")).waitTillVisibilityOfElement(60);
		homePage.producerNameGrid.formatDynamicPath(testData.get("InsuredName")).scrollToElement();
		Assertions.verify(
				homePage.producerNameGrid.formatDynamicPath(testData.get("InsuredName")).checkIfElementIsDisplayed(),
				true, "Producer Home Page ", "Account Name displayed in producer work grid " + ext_rpt_msg, false,
				false);
		homePage.producerQuoteOption.scrollToElement();
		homePage.producerQuoteOption.click();
		homePage.quoteField.scrollToElement();
		homePage.quoteField.setData(quoteNumber);
		homePage.prodFindBtn.scrollToElement();
		homePage.prodFindBtn.click();
		Assertions.passTest("Home Page", "Values Updated");
		homePage.quoteLinkButton.waitTillVisibilityOfElement(60);
		homePage.quoteLinkButton.scrollToElement();
		homePage.quoteLinkButton.click();
		Assertions.passTest("Account Overview Page",
				"Producer is able to open the account overview page and view the renewal quote." + ext_rpt_msg);
		Assertions.passTest("Account Overview Page", "Renewal Quote: " + quoteNumber);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Signed Out as Producer Successfully");

		// Login to producer account
		loginPage.waitTime(2);
		loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as USM Successfully");

		// Find the policy by entering policy Number
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.waitTillVisibilityOfElement(60);
		homePage.policyNumber.scrollToElement();
		homePage.policyNumber.setData(policyNumber);
		homePage.policyNumber.tab();
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Values Updated");
		accountOverviewPage.viewActivePolicy.scrollToElement();
		accountOverviewPage.viewActivePolicy.click();
		Assertions.passTest("Account Overview Page", "Account Overview Page loaded successfully");
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();

		// Entering data in Request Bind Page
		testData = data.get(data_Value1);
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");
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

		Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");

		// Getting Renewal Policy Number
		Assertions.passTest("Policy Summary Page", "Renewal Policy Number :" + policySummaryPage.getPolicynumber());
		Assertions.passTest("PNB_Regression_TC008", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}
