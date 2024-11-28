/** Program Description: To check if a bind referral happens for NB when the zip code is under moratorium as producer
 *  Author			   : John
 *  Date of Creation   : 09/27/19
 **/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.MoratoriumPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC046 extends AbstractNAHOTest {

	public PNBTC046() {
		super(LoginType.ADMIN);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID46.xls";
	}

	public Map<String, String> testData;
	public BasePageControl basePage;
	public HomePage homePage;
	public DwellingPage dwellingPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public PolicySummaryPage policySummaryPage;
	public BindRequestSubmittedPage bindRequestPage;
	public ReferralPage referralPage;
	LoginPage loginPage;
	String quoteNumber;
	String policyNumber;
	int locNo = 1;
	int bldgNo = 1;
	static int data_Value1 = 0;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		policySummaryPage = new PolicySummaryPage();
		dwellingPage = new DwellingPage();
		testData = data.get(data_Value1);
		loginPage = new LoginPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();

		// Verification of Home page icon
		Assertions.passTest("Home Page", "Logged in as Rzimmer Successfully");
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.adminLink.scrollToElement();
		homePage.adminLink.click();

		// Clicked on moratorium link
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		healthDashBoardPage.moratoriumLink.scrollToElement();
		healthDashBoardPage.moratoriumLink.click();

		// Clicked on create moratorium link
		MoratoriumPage moratoriumPage = new MoratoriumPage();
		moratoriumPage.createMoratoriumLink.scrollToElement();
		moratoriumPage.createMoratoriumLink.click();

		// Enter moratorium information and submit
		moratoriumPage.enterMoratoriumDetails(testData);
		String successMsg = moratoriumPage.moratriumCreatedMsg.getData();
		Assertions.passTest("Moratorium Page",
				"Moratorium is created and the message, " + successMsg + " is displayed");

		// Got to HomePage and sign out
		homePage.goToHomepage.click();
		homePage.signOutButton.click();
		Assertions.passTest("Home Page", "Logged out as Rzimmer Successfully");

		// Login as USM
		loginPage.waitTime(2);
		loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as Producer Successfully");

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		// Verification of create New Account button
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "New Account created Successfully");

		// Enter Dwelling details
		dwellingPage.addDwellingDetails(testData, locNo, bldgNo);
		dwellingPage.addRoofDetails(testData, locNo, bldgNo);
		dwellingPage.enterDwellingValues(testData, locNo, bldgNo);
		Assertions.passTest("Dwelling Page", "Details entered successfully");
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();

		// Click on create quote button
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// Entering values in deductibles section
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Details entered successfully");

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		// Click on request bind
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

		// Entering details in Request Bind page
		RequestBindPage requestBindPage = new RequestBindPage();
		requestBindPage.moratoriumMsg.waitTillVisibilityOfElement(60);
		String moratoriumMsg = requestBindPage.moratoriumMsg.getData();
		Assertions.passTest("Request Bind Page", "Moratorium warning, " + moratoriumMsg + " is displayed");
		String quoteNumber = requestBindPage.quoteNumber.getData();
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind Details entered successfully");
		String moratoriumReferralMessage = requestBindPage.moratoriumReferralMessage.getData();
		Assertions.passTest("Request Bind Page",
				"Moratorium Referral message, " + moratoriumReferralMessage + " is displayed");
		Assertions.passTest("Home Page", "Logged out as Producer successfully");
		homePage.goToHomepage.click();
		homePage.signOutButton.click();

		// Login as USM
		loginPage.waitTime(2);
		loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as USM Successfully");

		// Approve bind referral
		homePage.goToHomepage.click();
		homePage.searchReferral(quoteNumber);
		ReferralPage referralPage = new ReferralPage();
		referralPage.clickOnApprove();
		if (requestBindPage.approve.checkIfElementIsPresent() && requestBindPage.approve.checkIfElementIsDisplayed()) {
			requestBindPage.approve.waitTillVisibilityOfElement(60);
			requestBindPage.approve.scrollToElement();
			requestBindPage.approve.click();
			requestBindPage.waitTime(3);
			if (requestBindPage.approveBackDating.checkIfElementIsPresent()
					&& requestBindPage.approveBackDating.checkIfElementIsDisplayed()) {
				requestBindPage.approveBackDating.waitTillVisibilityOfElement(60);
				requestBindPage.approveBackDating.scrollToElement();
				requestBindPage.approveBackDating.click();
			}
			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.select();
				requestBindPage.approve.waitTillVisibilityOfElement(60);
				requestBindPage.approve.scrollToElement();
				requestBindPage.approve.click();
			}
		}
		Assertions.passTest("Referral Page", "Bind referral approved auccessfully");
		String policyNumber = policySummaryPage.getPolicynumber();
		Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true, "Policy Summary Page",
				"Policy number is displayed", false, false);
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

		// Click on Renewal indicators link
		policySummaryPage.renewalIndicators.scrollToElement();
		policySummaryPage.renewalIndicators.click();
		Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

		// Select Coverage Change Checkbox
		Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
				"Renewal Indicators Page", "Renewal Indicators Page Loaded successfully", false, false);
		renewalIndicatorsPage.coverageChange.scrollToElement();
		renewalIndicatorsPage.coverageChange.select();
		Assertions.passTest("Renewal Indicators Page", "Selected the coverage change checkbox");

		// click on update
		renewalIndicatorsPage.updateButton.scrollToElement();
		renewalIndicatorsPage.updateButton.click();
		Assertions.passTest("Renewal Indicators Page", "Clicked on Update Button");
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				"Policy Summary Page Loaded successfully", false, false);

		// Asserting the soft-Non Renewal Auto-Indicator on Policy Summary Page
		Assertions.addInfo("Policy Summary Page", "Verifying the Auto Non Renewal status as Soft Non-Renewal");
		Assertions.verify(policySummaryPage.autoRenewalIndicators.getData().contains("Soft Non-Renewal"), true,
				"Policy Summary Page",
				"Verifying the Auto-Renew status changed to: " + policySummaryPage.autoRenewalIndicators.getData(),
				false, false);

		// closing the browser
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Home Page", "Logged out as USM Successfully");

		// delete moratorium
		loginPage.waitTime(2);
		loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as Rzimmer Successfully");
		moratoriumPage.deleteMoratorium(testData.get("MoratoriumDescription"));
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC046", "Executed Successfully");

	}
}
