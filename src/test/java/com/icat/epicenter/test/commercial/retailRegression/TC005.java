/** Program Description:  Check the PCIP Endorsement link is removed from the Endorse Policy page.
 * Verifying the warning message when releasing the renewal quote to producer, put the orginal policy under non renewal.
 * Verifying the available non renewal reasons and verifying file review happened, And Added IO-20850
 *  Author			   : Sowndarya
 *  Date of Creation   : 20/07/2021
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
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ScheduledJobsAdmin;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC005 extends AbstractCommercialTest {

	public TC005() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID005.xls";
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
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
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
			// IO-20850 started
			// Click on underwriting guidelines link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.underwritingGuidelinesLink.scrollToElement();
			homePage.underwritingGuidelinesLink.click();
			Assertions.passTest("Home page", "Clicked on underwriting guidlines link successfully");

			// switch to child window
			waitTime(3);// waiting for control to switch to bin
			switchToChildWindow();

			// Verifying presence of subscription policyholder agreement
			Assertions.addInfo("Scenario 01", "Verifying presence of subscription holder agreement link");
			Assertions.verify(
					homePage.subscriptionPolicyholderAgreementLink.checkIfElementIsPresent()
							&& homePage.subscriptionPolicyholderAgreementLink.checkIfElementIsDisplayed(),
					true, "Documents page", "Subscription policyholder agreement link is displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Close the child window and switch to main window
			// WebDriverManager.closeCurrentBrowser();
			switchToMainWindow();
			// IO-20850 ended

			// creating New account
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

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
				Assertions.passTest("Select Peril Page", "Peril selected successfully");
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior loss details entered successfully");
			}

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
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
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

			// Click on Endorse PB link
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);

			// Asserting the PCIP Endorsement link is not present
			Assertions.addInfo("Scenario 02", "Assert the absence of PCIPEndorsement link");
			Assertions.verify(endorsePolicyPage.processPCIPEndorsementLink.checkIfElementIsPresent(), false,
					"Endorse Policy Page", "PCIP Endorsement link Removed is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

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
			Assertions.addInfo("Policy Summary Page", "Renew NB Policy");
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

			// clik on view previous policy
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Previous Policy Button");
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			// Check if the NRNL date is displayed according to the Notice period on Policy
			// Summary page.
			// click on renewal indicators link
			policySummarypage.renewalIndicators.scrollToElement();
			policySummarypage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// select non renewal checkbox
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);
			renewalIndicatorsPage.nonRenewal.scrollToElement();
			renewalIndicatorsPage.nonRenewal.select();
			Assertions.passTest("Renewal Indicators Page", "Selected the Non Renewal Checkbox");

			// Verifying the Presence of all available non renewal reasons
			Assertions.addInfo("Scenario 03", "Verifying the Presence of all available non renewal reasons");
			for (int i = 0; i < 4; i++) {
				int dataValuei = i;
				Map<String, String> testDatai = data.get(dataValuei);
				renewalIndicatorsPage.nonRenewalReasonArrow.scrollToElement();
				renewalIndicatorsPage.nonRenewalReasonArrow.click();
				String nonRenewalReasoni = renewalIndicatorsPage.nonRenewalReasonOption
						.formatDynamicPath(testDatai.get("NonRenewalReason")).getData();
				Assertions.verify(
						renewalIndicatorsPage.nonRenewalReasonOption
								.formatDynamicPath(testDatai.get("NonRenewalReason")).checkIfElementIsDisplayed(),
						true, "Renewal Indicators Page",
						"The Non Renewal Reason " + nonRenewalReasoni + " displayed is verified", false, false);
				renewalIndicatorsPage.updateButton.scrollToElement();
				renewalIndicatorsPage.updateButton.click();
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// select non renewal reason and enter legal notice wording
			renewalIndicatorsPage.nonRenewalReasonArrow.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonArrow.click();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason"))
					.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason")).click();
			renewalIndicatorsPage.nonRenewalLegalNoticeWording.setData(testData.get("LegalNoticeWording"));

			// click on update
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update Button");

			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 04", "Verifying Existing Renewal has been removed from the policy message");
			Assertions.verify(
					policySummarypage.nocMessage.getData()
							.contains("renewal has already been released to the producer"),
					true, "Policy Summary Page", "The Message displayed is " + policySummarypage.nocMessage.getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 ended");

			// Click on view active renewal link
			policySummarypage.viewActiveRenewal.scrollToElement();
			policySummarypage.viewActiveRenewal.click();
			Assertions.passTest("Policy summary page", "Clicked on view active renewal link successfully");

			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request bind button successfully");

			// Enter renewal bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request bind page", "Renewal bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind request page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home page", "Clicked on home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home page", "Searched submitted bind request successfullly");

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");

			requestBindPage.approveRequest();
			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully", false, false);
			RenewalPolicyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy summary page", "Renewal policy number is : " + RenewalPolicyNumber);

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home page", "Sign out as usm successfully");

			// Sign in as Rzimmer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));
			Assertions.passTest("Login page", "Logged in to application successfully as Rzimmer");

			// Click on admin link
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home page", "Clicked on admin link successfully");

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
			Assertions.passTest("Login page", "Logged in to application successfully as usm");

			// Searching policy in team referral section
			homePage.teamReferralFilter.scrollToElement();
			homePage.teamReferralFilter.click();
			homePage.teamReferralFilterOption.formatDynamicPath("File Review").scrollToElement();
			homePage.teamReferralFilterOption.formatDynamicPath("File Review").click();
			homePage.waitTime(2);// Adding wait time to visible the element

			// Verifying presence of file review referral policy
			Assertions.addInfo("Scenario 05", "Verifying file review referral");
			while (!homePage.referralquoteLink.formatDynamicPath(policyNumber).checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			homePage.waitTime(3);// Adding wait time to visible the element
			Assertions.verify(homePage.teamReferralQ3.formatDynamicPath(policyNumber).checkIfElementIsDisplayed(), true,
					"Home page", "File review referral happened is verified", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 ended");

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC005 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC005 ", "Executed Successfully");
			}
		}
	}
}
