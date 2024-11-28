/** Program Description: Verifying file review not happened for Utah state
 *  Author			   : Pavan mule
 *  Date of Creation   : 09/03/2022
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
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC043 extends AbstractCommercialTest {

	public TC043() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID043.xls";
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
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		LoginPage loginPage = new LoginPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		ScheduledJobsAdmin scheduledJobsAdmin = new ScheduledJobsAdmin();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		String RenewalPolicyNumber;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building page", "Building details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

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

			// Enter number of days
			scheduledJobsAdmin.commercialNumberofDays.formatDynamicPath(1).appendData(testData.get("NumberOfDays"));
			scheduledJobsAdmin.commercialNumberofDays.formatDynamicPath(1).tab();
			scheduledJobsAdmin.commercialNumberofDays.formatDynamicPath(2).scrollToElement();

			// enter number of days with state
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
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as usm");

			// Searching policy in team referral section
			homePage.teamReferralFilter.scrollToElement();
			homePage.teamReferralFilter.click();
			homePage.teamReferralFilterOption.formatDynamicPath("File Review").scrollToElement();
			homePage.teamReferralFilterOption.formatDynamicPath("File Review").click();
			homePage.waitTime(10);// Adding wait time to visible the element

			// Verifying absence of file review referral for renewal policy
			Assertions.addInfo("Scenario 01", "Verifying file review referral not happened for NB policy");
			Assertions.verify(
					homePage.teamReferralQ3.formatDynamicPath(policyNumber).checkIfElementIsPresent()
							&& homePage.teamReferralQ3.formatDynamicPath(policyNumber).checkIfElementIsDisplayed(),
					false, "Home page", "File review referral not happened for NB policy", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 ended");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// click on expacc link
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// click on renew policy link
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// click on continue
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// click on yes
			if (policyRenewalPage.yesButton.checkIfElementIsPresent()
					&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.yesButton.scrollToElement();
				policyRenewalPage.yesButton.click();
			}

			// getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to producer Button");

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on request bind button successfully");

			// Enter renewal bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request Bind Page", "Renewal bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// Approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			RenewalPolicyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Renewal Policy Number is : " + RenewalPolicyNumber);

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

			// Enter number of days
			scheduledJobsAdmin.commercialNumberofDays.formatDynamicPath(1).appendData(testData.get("NumberOfDays"));
			scheduledJobsAdmin.commercialNumberofDays.formatDynamicPath(1).tab();
			scheduledJobsAdmin.commercialNumberofDays.formatDynamicPath(2).scrollToElement();

			// Enter number of days and state
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
			homePage.waitTime(10);// Adding wait time to visible the element

			// Verifying absence of file review referral for renewal policy
			Assertions.addInfo("Scenario 02", "Verifying file review referral not happened for renwal policy");
			Assertions.verify(homePage.teamReferralQ3.formatDynamicPath(RenewalPolicyNumber).checkIfElementIsPresent()
					&& homePage.teamReferralQ3.formatDynamicPath(RenewalPolicyNumber).checkIfElementIsDisplayed(),
					false, "Home page", "File review referral not happened for renewal policy", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 ended");

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC043 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC043 ", "Executed Successfully");
			}
		}
	}
}
