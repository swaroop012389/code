/** Program Description: To check if a renewal requote(Edit deductibles) throws NLL warning message when the zip code has No Loss Letter(NLL) for USM
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
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC047 extends AbstractNAHOTest {

	public PNBTC047() {
		super(LoginType.ADMIN);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID47.xls";
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
		LoginPage loginPage = new LoginPage();
		loginPage.refreshPage();
		loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as USM Successfully");

		// Verification of create New Account button
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "New Account created Successfully");

		// Enter Dwelling details
		dwellingPage.addDwellingDetails(testData, locNo, bldgNo);
		dwellingPage.addRoofDetails(testData, locNo, bldgNo);
		dwellingPage.enterDwellingValues(testData, locNo, bldgNo);
		Assertions.passTest("Dwelling Page", "Details entered successfully");
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
				requestBindPage.waitTime(3);
				requestBindPage.approveBackDating.waitTillVisibilityOfElement(60);
				requestBindPage.approveBackDating.scrollToElement();
				requestBindPage.approveBackDating.click();
			}
		}
		Assertions.passTest("Referral Page", "Bind referral approved auccessfully");
		String policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

		// Renew policy and create re-quote
		policySummaryPage.renewPolicy.click();
		accountOverviewPage.editDeductibleAndLimits.waitTillVisibilityOfElement(60);
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();
		createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Assert no loss letter message
		// Message used to be NLL but as per rebeccas comments it is updated to
		// moratorium
		int noLossLetterMsgLength = createQuotePage.noLossLetterMsg1.getData().length();
		String noLossLetterMsg1 = createQuotePage.noLossLetterMsg1.getData().substring(0, noLossLetterMsgLength - 1);
		Assertions.passTest("Create Quote Page",
				"No loss letter warning message, " + noLossLetterMsg1 + " is displayed");
		createQuotePage.continueButton.scrollToElement();
		createQuotePage.continueButton.click();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		int quoteLength = accountOverviewPage.acntOverviewQuoteNumber.getData().length();
		String renewalQuoteNumber = accountOverviewPage.acntOverviewQuoteNumber.getData().substring(1, quoteLength - 1);
		Assertions.verify(accountOverviewPage.acntOverviewQuoteNumber.getData().contains(renewalQuoteNumber), true,
				"Account Overview Page", "Renewal Quote is displayed", false, false);
		Assertions.passTest("Account Overview Page", "Renewal quote number is " + renewalQuoteNumber);

		homePage.goToHomepage.click();
		homePage.signOutButton.click();
		Assertions.passTest("Home Page", "Logged out as USM successfully");

		// delete moratorium
		loginPage.refreshPage();
		loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as Rzimmer Successfully");
		moratoriumPage.deleteMoratorium(testData.get("MoratoriumDescription"));
		homePage.goToHomepage.click();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC047", "Executed Successfully");
	}
}
