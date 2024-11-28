/** Program Description: Verifying File Review Referral for the states Massachusetts, Georgia, Maryland, Maine, New Hampshire, Rhode Island, and Delaware
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 17/05/2022
 **/

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ScheduledJobsAdmin;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBMDTC003 extends AbstractNAHOTest {

	public PNBMDTC003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/MDTC003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		ScheduledJobsAdmin scheduledJobsAdmin = new ScheduledJobsAdmin();

		// Initializing the variables
		int data_Value = 0;
		int quoteLen;
		String quoteNumber;
		String policyNumber;
		Map<String, String> testData = data.get(data_Value);
		boolean isTestPassed = false;
		try {

			// Staring the loop
			for (int i = 0; i < 7; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);

				// Creating New Account
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home Page loaded successfully", false, false);
				homePage.createNewAccountWithNamedInsured(testData, setUpData);
				Assertions.passTest("New Account", "New Account Created successfully");

				// Entering Zipcode
				Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
						"Eligibility Page loaded successfully", false, false);
				eligibilityPage.processSingleZip(testData);
				Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

				// Entering Location 1 Dwelling 1 Details
				Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
						"Dwelling Page Loaded successfully", false, false);
				dwellingPage.enterDwellingDetailsNAHO(testData);
				Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

				// Entering prior loss details
				if (!testData.get("PriorLoss1").equals("")) {
					Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true,
							"Prior Loss Page", "Prior Loss Page loaded successfully", false, false);
					priorLossesPage.selectPriorLossesInformation(testData);
				}

				// Entering Quote Details
				Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Create Quote Page loaded successfully", false, false);
				createQuotePage.enterQuoteDetailsNAHO(testData);
				Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

				// Getting the Quote Number
				Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
				quoteLen = accountOverviewPage.quoteNumber.getData().length();
				quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
				Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

				// Click on Request bind
				accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
				Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

				// Entering details in Underwriting Questions Page
				Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
						"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
				underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
				Assertions.passTest("Underwriting Questions Page",
						"Underwriting Questions details entered successfully");

				// Entering bind details
				Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
						"Request Bind Page loaded successfully", false, false);
				requestBindPage.enterBindDetailsNAHO(testData);
				Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

				// Clicking on homepage button
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");

				// Opening the Referral
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Approve Referral
				Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true,
						"Referral Page", "Referral page loaded successfully", false, false);
				// IO-21792-As part of this our expectation is to validate if the referred quote
				// is assigned to USM not "Holder RMS"
				if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {

					Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true, "Referral Page",
							"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);
				} else {

					Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false,
							"Referral Page", "Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '",
							false, false);
				}
				// Ended
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
						"Request Bind page loaded successfully", false, false);
				requestBindPage.approveRequestNAHO(testData);
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

				// Asserting the Policy number
				policyNumber = policySummaryPage.getPolicynumber();
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
						"Policy Summary Page",
						"Policy Summary Page loaded successfully. PolicyNumber is " + policyNumber, false, false);

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// Clicking on expaac link in home page
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				Assertions.passTest("Home Page", "Clicked on Expaac Link");

				// Entering expaac data
				Assertions.verify(expaccInfoPage.policyNumber.checkIfElementIsDisplayed(), true, "Expacc Info Page",
						"Update Expaac Data page loaded successfully", false, false);
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc Info Page", "Expaac Data updated successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// Find the policy by entering policy Number
				homePage.searchPolicy(policyNumber);
				Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

				// Click on Renew Policy Hyperlink
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

				// click on open referral link
				if (accountOverviewPage.openReferral.checkIfElementIsPresent()
						&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
					Assertions.verify(accountOverviewPage.openReferral.checkIfElementIsDisplayed(), true,
							"Account Overview Page", "Account Overview Page loaded successfully", false, false);

					// Getting renewal quote number
					quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
					Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + quoteNumber);
					accountOverviewPage.openReferral.scrollToElement();
					accountOverviewPage.openReferral.click();
					Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

					// Approving the Referral message
					Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
							"Referral Page loaded successfully", false, false);
					referralPage.clickOnApprove();

					// click on approve in Approve Decline Quote page
					approveDeclineQuotePage.clickOnApprove();
					Assertions.passTest("Referral Page", "Referral request approved successfully");

					// Verifying referral complete message
					Assertions.verify(referralPage.referralCompleteMsg.checkIfElementIsDisplayed(), true,
							"Referral Page",
							referralPage.referralCompleteMsg.getData() + " message is verified successfully", false,
							false);

					// Click on close
					referralPage.close.scrollToElement();
					referralPage.close.click();

					// Search quote on home page
					Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
							"Home page loaded successfully", false, false);
					homePage.searchQuote(quoteNumber);
					Assertions.passTest("Home Page", "Searched the Quote : " + quoteNumber + " successfully");
				}

				// Click on release renewal to producer button
				Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page loaded successfully", false, false);
				accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
				accountOverviewPage.releaseRenewalToProducerButton.click();
				Assertions.passTest("Account Overview Page", "Clicked on Release renewal to producer button");

				// Click on request bind
				accountOverviewPage.clickOnRenewalRequestBind(testData, quoteNumber);
				Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

				// Enter bind details
				Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
						"Request Bind Page loaded successfully", false, false);

				// Enter Renewal bind details
				requestBindPage.renewalRequestBindNAHO(testData);

				// Asserting policy number
				policyNumber = policySummaryPage.getPolicynumber();
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
						"Policy Summary Page",
						"Policy Summary Page loaded successfully. The Renewal PolicyNumber is : " + policyNumber, false,
						false);

				// Signout
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as USM successfully");

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
						true, "Health Dash Borad Page", "Health dash board page loaded successfully", false, false);
				healthDashBoardPage.scheduledJobManagerlink.scrollToElement();
				healthDashBoardPage.scheduledJobManagerlink.click();
				Assertions.passTest("Health Dash Borad Page", "Clicked on Scheduled job manager link successfully");

				// Running NAHO Renewal file Review referral job
				Assertions.verify(
						scheduledJobsAdmin.checkCronButton.checkIfElementIsPresent()
								&& scheduledJobsAdmin.checkCronButton.checkIfElementIsDisplayed(),
						true, "Scheduled Jobs Admin Page", "Scheduled jobs admin page loaded successfully", false,
						false);
				while (!scheduledJobsAdmin.nahoRenewalRunWithOptionsLink.checkIfElementIsPresent()) {
					homePage.signOutButton.scrollToElement();
					homePage.signOutButton.click();

					// Sign in as Rzimmer
					loginPage.refreshPage();
					loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));

					// Click on admin link
					homePage.adminLink.scrollToElement();
					homePage.adminLink.click();

					// Click on scheduled job manager link
					healthDashBoardPage.scheduledJobManagerlink.scrollToElement();
					healthDashBoardPage.scheduledJobManagerlink.click();
				}
				scheduledJobsAdmin.nahoRenewalRunWithOptionsLink.scrollToElement();
				scheduledJobsAdmin.nahoRenewalRunWithOptionsLink.click();

				// Entering number of days"0"
				scheduledJobsAdmin.nahoNumberofDays.scrollToElement();
				scheduledJobsAdmin.nahoNumberofDays.appendData(testData.get("State_Colon_NumberOfDay"));
				scheduledJobsAdmin.nahoNumberofDays.tab();

				// Click on run job
				scheduledJobsAdmin.nahoRenewalRunJobButton.scrollToElement();
				scheduledJobsAdmin.nahoRenewalRunJobButton.click();
				Assertions.passTest("Scheduled Jobs Admin Page", "Clicked on  run job successfully");

				// Check Naho Renewal referral running message "Ran commercial retail file
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
				Assertions.passTest("Home Page", "Signout as  rzimmer successfully");

				// login as usm
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Login Page", "Logged in to application successfully as USM");

				// Searching policy in team referral section
				homePage.teamReferralFiltersArrow.scrollToElement();
				homePage.teamReferralFiltersArrow.click();
				homePage.teamReferralFiltersOption.formatDynamicPath("Rnl File Review").scrollToElement();
				homePage.teamReferralFiltersOption.formatDynamicPath("Rnl File Review").click();
				homePage.waitTime(3);// Adding wait time to visible the element

				// Verifying presence of file review referral policy
				Assertions.addInfo("Home Page", "Verifying file review referral");
				Assertions.passTest("Home Page", "The Policy state is " + testData.get("QuoteState"));
				while (!homePage.referralquoteLink.formatDynamicPath(policyNumber).checkIfElementIsPresent()) {
					homePage.refreshPage();
				}
				homePage.waitTime(7);// Adding wait time to visible the element
				if (homePage.teamReferralQ3.formatDynamicPath(policyNumber).checkIfElementIsPresent()
						&& homePage.teamReferralQ3.formatDynamicPath(policyNumber).checkIfElementIsDisplayed()) {
					Assertions.verify(
							homePage.teamReferralQ3.formatDynamicPath(policyNumber).checkIfElementIsDisplayed(), true,
							"Home page", "File review referral happened is verified", false, false);
				} else {
					Assertions.verify(
							homePage.referralquoteLink.formatDynamicPath(policyNumber).checkIfElementIsDisplayed(),
							true, "Home page", "File review referral happened is verified", false, false);
				}

				// Verify the type of the policy
				Assertions.verify(
						homePage.teamReferralType.formatDynamicPath(policyNumber).getData().contains("Rnl File Review"),
						true, "Home page",
						"The Review Referral Type "
								+ homePage.teamReferralType.formatDynamicPath(policyNumber).getData()
								+ " displayed is verified",
						false, false);
				Assertions.addInfo("Scenario ", "Scenario Ended");
			}

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBMDTC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBMDTC003 ", "Executed Successfully");
			}
		}
	}
}
