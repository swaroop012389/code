/** Program Description: Verifying the NRNL dates are displayed according to the Notice period and verifying the presence of NRNL documents and Added CR IO-19960
 * 					   : CA states checking absence of building valuation row, asserting quote status is bound and checking presence of building valuation row and building valuation message for Rewrite [API Valuation, Pavan Mule-11-07-2023]
 *  Author			   : Sowndarya
 *  Date of Creation   : 25/02/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ScheduledJobsAdmin;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC042 extends AbstractCommercialTest {

	public TC042() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID042.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		ScheduledJobsAdmin scheduledJobsAdmin = new ScheduledJobsAdmin();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();

		// initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		String buildingMinimumValue;
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

			// API Valuation started
			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.addSymbol.scrollToElement();
			buildingPage.addSymbol.click();
			buildingPage.addNewBuilding.scrollToElement();
			buildingPage.addNewBuilding.click();
			buildingPage.addBuildingDetails(testData, 1, 1);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, 1, 1);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building page", "Building details enter successfully");

			// Checking Building valuation row is not displayed on the building page.
			Assertions.addInfo("Scenario 01", "Asserting absence of building valuation row in building page");
			Assertions.verify(
					buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation").checkIfElementIsPresent()
							&& buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation")
									.checkIfElementIsDisplayed(),
					false, "Building page", "The building valuation row is not displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			// API Valuation Ended

			// Clicking on override button
			if (existingAccountPage.override.checkIfElementIsPresent()
					&& existingAccountPage.override.checkIfElementIsDisplayed()) {
				existingAccountPage.override.scrollToElement();
				existingAccountPage.override.click();
			}

			// Click on create quote
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// Clicking on override button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Quote number :  " + quoteNumber);

			// Adding CR IO-19960
			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View print full quote page", "View print full quote page loaded successfully", false, false);

			// Adding CR IO-19960
			// Verifying presence of Form D-1 or D-1 notice
			Assertions.addInfo("Scenario 02", "Verifying presnce of Form D-1 or D-1 notice");
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("nonadmitted")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("nonadmitted")
									.checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("nonadmitted").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("California licensed")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("California licensed").checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("California licensed").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("California law")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("California law")
									.checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("California law").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("non-United States")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("non-United States").checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("non-United States").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("Foreign insurers")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("Foreign insurers").checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("Foreign insurers").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("country outside")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("country outside")
									.checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("country outside").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("List of Approved Surplus")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("List of Approved Surplus").checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("List of Approved Surplus").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("applicant, required")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("applicant, required").checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("applicant, required").getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// upload subscription document and clicked on request bind button
			accountOverviewPage.uploadSubscriptionDocument(testData, quoteNumber, data);
			Assertions.passTest("Account overview page", "Clicked on request bind");

			// Entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);
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
			if (requestBindPage.chooseFile.checkIfElementIsPresent()
					&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
			}
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
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// click on expacc link and entering expacc details
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
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// click on continue
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// click on yes button
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

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View print full quote page", "View print full quote page loaded successfully", false, false);

			// Adding CR IO-19960
			// Verifying presence of Form D-1 or D-1 notice on renewal quote
			Assertions.addInfo("Scenario 03", "Verifying presnce of Form D-1 or D-1 notice");
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("nonadmitted")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("nonadmitted")
									.checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("nonadmitted").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("California licensed")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("California licensed").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("California licensed").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("California law")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("California law")
									.checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("California law").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("non-United States")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("non-United States").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("non-United States").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("Foreign insurers")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("Foreign insurers").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("Foreign insurers").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("country outside")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("country outside")
									.checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("country outside").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("List of Approved Surplus")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("List of Approved Surplus").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("List of Approved Surplus").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("applicant, required")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("applicant, required").checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Asserting Form D-1 or D-1 notice wordings " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("applicant, required").getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on view previous policy
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on previous policy link");

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
			Assertions.passTest("Renewal indicators page", "Clicked on update button");

			// asserting NRNL message in policy summary page
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully", false, false);

			// Signing out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home page", "Logged out as USM successfully");

			// Login to Rzimmer account
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login page",
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
			Assertions.passTest("Health dashBoard page", "Clicked on scheduled job manager link");

			// click on run job with options link
			scheduledJobsAdmin.runJobWithOptionslink.formatDynamicPath("1").waitTillVisibilityOfElement(60);
			scheduledJobsAdmin.runJobWithOptionslink.formatDynamicPath("1").scrollToElement();
			scheduledJobsAdmin.runJobWithOptionslink.formatDynamicPath("1").click();

			// provide the details
			scheduledJobsAdmin.runDate.setData(testData.get("RunDate"));
			scheduledJobsAdmin.runStateArrow.scrollToElement();
			scheduledJobsAdmin.runStateArrow.click();
			scheduledJobsAdmin.waitTime(1);// need waitime to load element

			// select LOB Option based on state
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("QuoteState")).scrollToElement();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("QuoteState")).click();

			// select report only
			scheduledJobsAdmin.reportOnlyArrow.scrollToElement();
			scheduledJobsAdmin.reportOnlyArrow.click();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("ReportOnlyOption")).scrollToElement();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("ReportOnlyOption")).click();

			// select Line of Business option
			scheduledJobsAdmin.lineOfBusinessArrow.scrollToElement();
			scheduledJobsAdmin.lineOfBusinessArrow.click();

			// select Product
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("ProductSelection")).scrollToElement();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("ProductSelection")).click();
			scheduledJobsAdmin.waitTime(2);// need waittime to load element

			// click on job run button
			scheduledJobsAdmin.runJobButton.scrollToElement();
			scheduledJobsAdmin.runJobButton.click();

			// Signing out as rzimmer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home page", "Logged out as rzimmer successfully");

			// Login to USM account
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Home page", "Logged in as USM successfully");

			// Search the Policy Number
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Producer Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);

			// Asserting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// click on view documents link
			policySummaryPage.viewDocuments.scrollToElement();
			policySummaryPage.viewDocuments.click();
			Assertions.passTest("Policy ummary age", "Clicked on view documents link");
			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();
			policySummaryPage.viewDocuments.scrollToElement();
			policySummaryPage.viewDocuments.click();

			// check for the presence of NRNL Document
			Assertions.verify(policyDocumentsPage.policyDocuments.formatDynamicPath("_I.").getNoOfWebElements(), 1,
					"View documents page",
					policyDocumentsPage.policyDocuments.formatDynamicPath("_I.").getData() + " displayed is verified",
					false, false);

			// Click on back button
			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();
			Assertions.passTest("Policy documents page", "Clicked on back button successfully");

			// Click on Rewrite link
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully", false, false);
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();

			// Asserting quote number
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Quote number :  " + quoteNumber);

			// API Valuation stared
			// Verifying quote status is bound
			Assertions.addInfo("Scenario 05 ", "Verifying the quote status is bound in account overview page");
			Assertions.verify(
					accountOverviewPage.boundStatus.checkIfElementIsPresent()
							&& accountOverviewPage.boundStatus.checkIfElementIsDisplayed(),
					true, "Account overview page",
					"The quote status, " + accountOverviewPage.boundStatus.getData() + " is displayed", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on building link
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.passTest("Account overview page", "Clicked on building link successfully");

			// Click on edit building link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account overview page", "Clicked on editbuilding link successfully");

			// Enter Building Values
			testData = data.get(data_Value2);
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.enterBuildingValues(testData, 1, 1);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building page", "Building value enter successfully");

			// Asserting the Minimum building valuation message the message is "The minimum
			// Building value for this risk is $XX
			// Do you want to update to $XX or override to allow the input value?"
			Assertions.addInfo("Scenario 06", "Asserting the minimum building valuation message");
			buildingMinimumValue = buildingUnderMinimumCostPage.costcardMessage
					.formatDynamicPath("The minimum Building value for this risk").getData().substring(45, 52);
			Assertions.verify(
					buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value for this risk").getData()
							.contains("The minimum Building value for this risk"),
					true, "Building under minimum cost page",
					"Asserting the minimum building valuation message, "
							+ buildingUnderMinimumCostPage.costcardMessage
									.formatDynamicPath("The minimum Building value for this risk").getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on bring uptocost
			if (buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.bringUpToCost.scrollToElement();
				buildingUnderMinimumCostPage.bringUpToCost.click();
			}
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);

			// Checking Building valuation row is displayed on the building page.
			Assertions.addInfo("Scenario 07", "Asserting presence of building valuation row in building page");
			Assertions.verify(
					buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation").checkIfElementIsPresent()
							&& buildingPage.buildingValuationRow
									.formatDynamicPath("Building Valuation").checkIfElementIsDisplayed(),
					true, "Building page",
					"The building valuation row, "
							+ buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation").getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on create quote
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// Clicking on override button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Comparing building value shown in create quote page with building value shown
			// in building under minimum cost page
			Assertions.addInfo("Scenario 08",
					"Asserting and Comparing building value shown in create quote page with building value shown in building under minimum cost page");
			Assertions.verify(buildingMinimumValue, createQuotePage.buildingValue.formatDynamicPath(0, 0).getData(),
					"Create quote page",
					"Building value shown in create quote page and building value shown in building under minimum cost page both are same. The building value shown in create quote page is $"
							+ createQuotePage.buildingValue.formatDynamicPath(0, 0).getData()
							+ " and Building value shown in building under minimum cost page is $"
							+ buildingMinimumValue,
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// getting the rewrite quote number
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Rewrite quote number :  " + quoteNumber);

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC042 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC042 ", "Executed Successfully");
			}
		}
	}
}
