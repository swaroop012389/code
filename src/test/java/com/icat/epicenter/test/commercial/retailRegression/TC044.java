/** Program Description: Verifying file review happened on renewal policy and Added IO-21467
 *  Author			   : Pavan Mule
 *  Date of Creation   : 07/03/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ScheduledJobsAdmin;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC044 extends AbstractCommercialTest {

	public TC044() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID044.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		LoginPage loginPage = new LoginPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		ScheduledJobsAdmin scheduledJobsAdmin = new ScheduledJobsAdmin();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		String renewalPolicyNumber;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Producer home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location page",
					"Location page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select peril page",
						"Select peril page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Adding IO-21467
			// Verifying building numbers to value section.Example The building number
			// ='Building 1-1'
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create auote page",
					"Create quote page loaded successfully", false, false);
			Assertions.addInfo("Scenario 01", "Verifying building numbers to value section");
			Assertions.verify(
					createQuotePage.buildingNumbersLabel
							.formatDynamicPath("Building 1-1").getData().contains("Building 1-1"),
					true, "Create quote page",
					"The building number is '"
							+ createQuotePage.buildingNumbersLabel.formatDynamicPath("Building 1-1").getData()
							+ "' displayed",
					false, false);
			Assertions.verify(
					createQuotePage.buildingNumbersLabel
							.formatDynamicPath("Building 1-2").getData().contains("Building 1-2"),
					true, "Create quote page",
					"The building number is '"
							+ createQuotePage.buildingNumbersLabel.formatDynamicPath("Building 1-2").getData()
							+ "' displayed",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 ended");
			// IO-21467 ended

			// Entering Create quote page Details
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Quote number :  " + quoteNumber);

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request bind");

			// Entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind request page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home page", "Clicked on home button");

			// Searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// Approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy summary page", "Policy number is : " + policyNumber);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);

			// click on expacc link
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// click on renew policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy summary page", "Clicked on renew policy link");

			// click on continue
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			if (policyRenewalPage.yesButton.checkIfElementIsPresent()
					&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.yesButton.scrollToElement();
				policyRenewalPage.yesButton.click();
			}

			// Getting the renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Renewal quote number :  " + quoteNumber);

			// Click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account overview page", "Clicked on release renewal to producer successfully");

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request bind button successfully");

			// Entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind request page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// Searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// Approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			renewalPolicyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Renewal Policy Number is : " + renewalPolicyNumber);

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as usm successfully");

			// Sign in as Rzimmer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as Rzimmer");

			// Click on admin link
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home Page", "Clicked on admin link successfully");

			// click on scheduled job manager link
			Assertions.verify(
					healthDashBoardPage.scheduledJobManagerlink.checkIfElementIsPresent()
							&& healthDashBoardPage.scheduledJobManagerlink.checkIfElementIsDisplayed(),
					true, "Health dash borad page", "Health dash board page loaded successfully", false, false);
			healthDashBoardPage.scheduledJobManagerlink.scrollToElement();
			healthDashBoardPage.scheduledJobManagerlink.click();
			Assertions.passTest("Health dash borad page", "Clicked on scheduled job manager link successfully");

			// Running commercial retail file Review referral job
			Assertions.verify(
					scheduledJobsAdmin.checkCronButton.checkIfElementIsPresent()
							&& scheduledJobsAdmin.checkCronButton.checkIfElementIsDisplayed(),
					true, "Scheduled jobs admin page", "Scheduled jobs admin page loaded successfully", false, false);
			scheduledJobsAdmin.commercialRunWithOptionLink.formatDynamicPath(1).scrollToElement();
			scheduledJobsAdmin.commercialRunWithOptionLink.formatDynamicPath(1).click();
			scheduledJobsAdmin.commercialNumberofDays.formatDynamicPath(1).scrollToElement();

			// Enter number of days for automated job run
			scheduledJobsAdmin.commercialNumberofDays.formatDynamicPath(1).appendData(testData.get("NumberOfDays"));
			scheduledJobsAdmin.commercialNumberofDays.formatDynamicPath(1).tab();
			scheduledJobsAdmin.commercialNumberofDays.formatDynamicPath(2).scrollToElement();

			// Enter number of days and state for automated job run
			scheduledJobsAdmin.commercialNumberofDays.formatDynamicPath(2).appendData(testData.get("NumberOfDays"));
			scheduledJobsAdmin.commercialNumberofDays.formatDynamicPath(2).tab();
			scheduledJobsAdmin.commercialRunJobButton.scrollToElement();
			scheduledJobsAdmin.commercialRunJobButton.click();
			Assertions.passTest("Scheduled jobs admin page", "Clicked on  run job successfully");

			// check commercial job referral running message "Ran commercial retail file
			// review referral job job job"
			Assertions.verify(
					scheduledJobsAdmin.globalInfo.checkIfElementIsPresent()
							&& scheduledJobsAdmin.globalInfo.checkIfElementIsDisplayed(),
					true, "Scheduled jobs admin page",
					"Verifying presence of commercial retail file review referral job run is verified "
							+ scheduledJobsAdmin.globalInfo.getData(),
					false, false);

			// log out as rzimmer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home page", "Signout as  rzimmer successfully");

			// login as usm
			loginPage.refreshPage();
			loginPage.enterLoginDetails("sminn", setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as usm");

			// Searching policy in team referral section
			homePage.teamReferralFilter.scrollToElement();
			homePage.teamReferralFilter.click();
			homePage.teamReferralFilterOption.formatDynamicPath("File Review").scrollToElement();
			homePage.teamReferralFilterOption.formatDynamicPath("File Review").click();
			homePage.waitTime(2);// Adding wait time to visible the element

			// Verifying presence of file review referral policy
			Assertions.addInfo("Scenario 02", "Verifying file review referral");
			while (!homePage.referralquoteLink.formatDynamicPath(policyNumber).checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			homePage.waitTime(3);// Adding wait time to visible the element
			Assertions.verify(homePage.teamReferralQ3.formatDynamicPath(policyNumber).checkIfElementIsDisplayed(), true,
					"Home page", "File review referral happened is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 ended");

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC044 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC044 ", "Executed Successfully");
			}
		}
	}
}