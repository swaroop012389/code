/** Program Description: To verify referral message for elevation as producer and to create a policy
 *  Author			   : John
 *  Date of Creation   : 24/12/2020
**/
package com.icat.epicenter.test.hiho.regression.NB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountDetails;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC015 extends AbstractNAHOTest {

	public TC015() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID15.xls";
	}

	public LoginPage loginPage;
	public HomePage homePage;
	public DwellingPage dwellingPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public BindRequestSubmittedPage bindRequestPage;
	public ConfirmBindRequestPage confirmBindrequestPage;
	public ReferralPage referralPage;
	public ReferQuotePage referQuotePage;
	public PolicySummaryPage policySummarypage;
	public ApproveDeclineQuotePage approve_DeclineQuotePage;
	public AccountDetails accountDetailsPage;
	public Map<String, String> testData;
	public BasePageControl basePage;
	static final String ext_rpt_msg = " is verified";
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_UPDATED = "Values Updated";
	static final String VALUES_VERIFIED = "Values Verified";
	static final String VALUES_ENTERED = "Values Entered successfully";
	static int data_Value1 = 0;
	public ApproveDeclineQuotePage approveDeclineQuotePage;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing all Objects
		loginPage = new LoginPage();
		homePage = new HomePage();
		dwellingPage = new DwellingPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		accountDetailsPage = new AccountDetails();
		policySummarypage = new PolicySummaryPage();
		requestBindPage = new RequestBindPage();
		bindRequestPage = new BindRequestSubmittedPage();
		confirmBindrequestPage = new ConfirmBindRequestPage();
		approve_DeclineQuotePage = new ApproveDeclineQuotePage();
		referralPage = new ReferralPage();
		referQuotePage = new ReferQuotePage();
		testData = data.get(data_Value1);
		approveDeclineQuotePage = new ApproveDeclineQuotePage();
		String quoteNumber;

		// create New account by logging in as producer
		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		Assertions.passTest("Create Quote Page", VALUES_ENTERED);
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview page", "Quote Number with Referred status: " + quoteNumber);

		Assertions.passTest("Account Overview page", "Clicking on Bind Button");
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

		// Entering details in Request Bind page
		Assertions.passTest("Request Bind Page", PAGE_NAVIGATED);
		requestBindPage.enterPaymentInformation(testData);
		requestBindPage.addInspectionContact(testData);
		requestBindPage.enterPolicyDetailsNAHO(testData);
		requestBindPage.addContactInformation(testData);
		requestBindPage.addAdditionalInterestEQHO(testData);

		requestBindPage.submit.waitTillButtonIsClickable(60);
		requestBindPage.submit.click();
		requestBindPage.confirmBind();

		String policyNumber = policySummarypage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC015", "Executed Successfully");
	}
}
