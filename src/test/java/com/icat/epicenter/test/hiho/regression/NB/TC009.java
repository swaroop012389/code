/** Program Description: To generate a policy with multiple location multiple buildings with different dwelling types,  multiple AIs and assert values.
 *  Author			   : SM Netserv
 *  Date of Creation   : June 2018
 **/

package com.icat.epicenter.test.hiho.regression.NB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC009 extends AbstractNAHOTest {

	public TC009() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID09.xls";
	}

	public HomePage homePage;
	public DwellingPage dwellingPage;
	public AccountOverviewPage accountOverviewPage;
	public BuildingUnderMinimumCostPage dwellingCost;
	public LocationPage locationPage;
	public PolicySummaryPage policySummaryPage;
	public RequestBindPage requestBindPage;
	public CreateQuotePage createQuotePage;
	public BindRequestSubmittedPage bindRequestPage;
	public ReferralPage referralPage;
	public BasePageControl basePage;
	public Map<String, String> testData;
	static final String ext_rpt_msg = " is verified";
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_UPDATED = "Values Updated";
	static final String VALUES_ENTERED = "Values Entered Successfully";
	static final String VALUES_VERIFIED = "Values Verified";
	static int data_Value1 = 0;
	static int data_Value2 = 1;
	static int data_Value3 = 2;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		dwellingPage = new DwellingPage();
		accountOverviewPage = new AccountOverviewPage();
		dwellingCost = new BuildingUnderMinimumCostPage();
		locationPage = new LocationPage();
		policySummaryPage = new PolicySummaryPage();
		createQuotePage = new CreateQuotePage();
		bindRequestPage = new BindRequestSubmittedPage();
		referralPage = new ReferralPage();
		dwellingPage = new DwellingPage();

		// New Account
		testData = data.get(data_Value1);
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "Page Navigated");
		Assertions.passTest("Home Page", "New Account created successfully");
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

		// Entering Dwelling Details
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.addRoofDetails(testData, 1, 1);
		dwellingPage.enterDwellingValues(testData, 1, 1);

		// Review Dwelling
		dwellingPage.reviewDwelling.click();
		dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

		// Click on Create Quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(60);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		Assertions.verify(createQuotePage.ordLawOption.checkIfElementIsPresent(), false, "Create Quote Page",
				"Ordinance and Law field is not present" + ext_rpt_msg, false, false);
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.getAQuote.waitTillInVisibilityOfElement(60);

		String accountQuoteNumber = accountOverviewPage.acntOverviewQuoteNumber.getData();

		// Click on Request Bind
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, accountQuoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");

		// Getting Quote Number
		String quoteNumber = requestBindPage.quoteNumber.getData();
		Assertions.passTest("Request Bind Page", "Quote Number :  " + quoteNumber);

		// Entering Request Bind Page Details
		basePage = requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		homePage = bindRequestPage.clickOnHomepagebutton();
		Assertions.passTest("Home Page", "Home Page loaded successfully");

		// Search Referral and approve
		homePage.searchQuote(quoteNumber);
		Assertions.passTest("Home Page", "Searched Quote successfullly");
		accountOverviewPage.openReferral.click();
		Assertions.passTest("Account Overview Page", "Clicked on Open Referral Link successfullly");
		Assertions.passTest("Referral Page", "Referral Page openned successfullly");
		requestBindPage = referralPage.clickOnApprove();
		Assertions.passTest("Referral Page", "Referral request approved successfully");
		policySummaryPage = requestBindPage.approveRequest();
		Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

		// Verifying Insured name and effective date
		String policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);
		Assertions.verify(policyNumber.startsWith("52-358"), true, "Policy Summary Page",
				"Policy Number format " + ext_rpt_msg, false, false);
		homePage.goToHomepage.click();
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC009", "Executed Successfully");
	}
}