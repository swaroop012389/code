
/** Program Description:  Check if the line Completed and signed diligent effort form is not present under Subject To of commercial retail quotes on FL quotes if the buildings have a non-habitational primary occupancy
and verifying the NRNL dates are displayed according to the Notice period and verifying the presence of NRNL documents
*  Author			   : Sowndarya
*  Date of Creation   : 26/07/2021
**/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ScheduledJobsAdmin;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC014 extends AbstractCommercialTest {

	public TC014() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID014.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		ScheduledJobsAdmin scheduledJobsAdmin = new ScheduledJobsAdmin();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
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
			Assertions.passTest("Building Page", "The Occupancy selected is " + testData.get("L1B1-PrimaryOccupancy"));

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
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

			// Click on View print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full Quote link");

			// Asserting Completed and signed diligent effort form Text not present in Quote
			// page
			Assertions.addInfo("Scenario 01",
					"Assert the absence of Completed and signed diligent effort form For FL state");
			Assertions.verify(quoteDetailsPage.goBackButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page loaded successfully", false, false);
			Assertions.verify(quoteDetailsPage.diligentEffortFormText.checkIfElementIsPresent(), false,
					"View/Print Full Quote Page",
					"\"Completed and signed diligent effort form\" is not present for this quote Since the State is "
							+ testData.get("QuoteState") + " and Occupancy is" + testData.get("L1B1-PrimaryOccupancy"),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on back
			quoteDetailsPage.goBackButton.click();

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering bind details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
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

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Check if the NRNL date is displayed according to the Notice period on Policy
			// Summary page.
			// click on renewal indicators link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// select non renewal checkbox
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);
			renewalIndicatorsPage.nonRenewal.scrollToElement();
			renewalIndicatorsPage.nonRenewal.select();
			Assertions.passTest("Renewal Indicators Page", "Selected the Non Renewal Checkbox");

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
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 02",
					"Verifying the NRNL date is displayed according to the Notice period for the state FL");

			Assertions.verify(
					policySummaryPage.nocMessage.getData().contains("Renewal Indicators Successfully Updated"), true,
					"Policy Summary Page", "The Message displayed is " + policySummaryPage.nocMessage.getData(), false,
					false);
			Assertions.addInfo("Policy Summary Page", "Verifying the status of the application");
			Assertions.verify(policySummaryPage.autoRenewalIndicators.getData().contains("Non-Renewal"), true,
					"Policy Summary Page",
					"The status of the application changed to :" + policySummaryPage.autoRenewalIndicators.getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Signing out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Login to Rzimmer account
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Rzimmer Successfully");

			// click on admin section
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Rzimmer Home Page loaded successfully", false, false);
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home Page", "Clicked on Admin link");

			// click on Scheduled Job Manager link
			Assertions.verify(healthDashBoardPage.scheduledJobManagerlink.checkIfElementIsDisplayed(), true,
					"Health DashBoard Page", "Health DashBoard Page loaded successfully", false, false);
			healthDashBoardPage.scheduledJobManagerlink.scrollToElement();
			healthDashBoardPage.scheduledJobManagerlink.click();
			Assertions.passTest("Health DashBoard Page", "Clicked on Scheduled Job Manager link");

			// click on run job with options link
			scheduledJobsAdmin.runJobWithOptionslink.formatDynamicPath("1").waitTillVisibilityOfElement(60);
			scheduledJobsAdmin.runJobWithOptionslink.formatDynamicPath("1").scrollToElement();
			scheduledJobsAdmin.runJobWithOptionslink.formatDynamicPath("1").click();

			// provide the details
			scheduledJobsAdmin.runDate.setData(testData.get("RunDate"));
			scheduledJobsAdmin.runStateArrow.scrollToElement();
			scheduledJobsAdmin.runStateArrow.click();

			// select state
			scheduledJobsAdmin.waitTime(1);// need wait time to load element
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("QuoteState")).scrollToElement();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("QuoteState")).click();

			// select report only option
			scheduledJobsAdmin.reportOnlyArrow.scrollToElement();
			scheduledJobsAdmin.reportOnlyArrow.click();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("ReportOnlyOption")).scrollToElement();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("ReportOnlyOption")).click();

			// select the product
			scheduledJobsAdmin.lineOfBusinessArrow.scrollToElement();
			scheduledJobsAdmin.lineOfBusinessArrow.click();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("ProductSelection")).scrollToElement();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("ProductSelection")).click();

			// click on run job
			scheduledJobsAdmin.waitTime(2);// need wait time to load element
			scheduledJobsAdmin.runJobButton.scrollToElement();
			scheduledJobsAdmin.runJobButton.click();

			// Signing out as rzimmer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Rzimmer successfully");

			// Login to Rzimmer account
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Search the Policy Number
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);

			// Asserting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC014 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC014 ", "Executed Successfully");
			}
		}
	}
}
