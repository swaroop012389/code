/** Program Description: Check if NJ quote has state specific wordings and forms when occupancy is less than 31% and more than 31% for renewal quote
 * and Verifying the hardstop error message when the year built prior to 1949
 *  Author			   : Sowndarya
 *  Date of Creation   : 01/02/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
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
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ScheduledJobsAdmin;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC033 extends AbstractCommercialTest {

	public TC033() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID033.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
		LoginPage loginPage = new LoginPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		ScheduledJobsAdmin scheduledJobsAdmin = new ScheduledJobsAdmin();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
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

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.addAdditionalCoveragesCommercial(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			Assertions.addInfo("Scenario 01",
					"Assert Prior loass warning message when water damage prior loss type is added");
			Assertions
					.verify(createQuotePage.warningMessageforAdjustments
							.formatDynamicPath("prior water loss").checkIfElementIsDisplayed(), true,
							"Create Quote Page",
							"The Warning message " + createQuotePage.warningMessageforAdjustments
									.formatDynamicPath("prior water loss").getData() + " displayed is verified",
							false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on Continue button
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			createQuotePage.continueButton.waitTillInVisibilityOfElement(60);

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

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
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
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

			// adding ticket IO-21777
			Assertions.passTest("Scenario 01 ",
					"Validating the Request Deductible buy back link Not displayed on quote");
			Assertions.verify(!accountOverviewPage.requestDeductibleBuyBackBtn.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Request Deductible buy back link Not displayed", false, false);
			Assertions.passTest("Scenario 01 ", "Scenario 01 Ended");

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View print full quote link");

			// Assert forms, label and wording
			Assertions.addInfo("Scenario 02",
					"Asserting the absence of SLPS-6-CERT-1 form and Completed and signed diligent effort form when occupancy percentage is less than 31% for renewal quote");
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("SLPS-6-CERT-1").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.mainSections.formatDynamicPath("SLPS-6-CERT-1")
									.checkIfElementIsDisplayed(),
					false, "View/Print Full Quote Page",
					"SLPS-6-CERT-1 form is not displayed when occupancy percentage is less than 31%", false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("CERTIFICATION OF EFFORT")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.mainSections.formatDynamicPath("CERTIFICATION OF EFFORT")
									.checkIfElementIsDisplayed(),
					false, "View/Print Full Quote Page",
					"CERTIFICATION OF EFFORT is not displayed when occupancy percentage is less than 31%", false,
					false);
			Assertions.verify(quoteDetailsPage.diligentEffortFormText.checkIfElementIsPresent(), false,
					"Quote Details Page",
					"\"Completed and signed diligent effort form\" is not found in this Quote since State is "
							+ testData.get("QuoteState") + " and Occupancy Percent is "
							+ testData.get("L1B1-PercentOccupied"),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			Assertions.addInfo("Scenario 03", "Asserting the Presence of NJ state Specific Wordings");
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("Any person who includes")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("Any person who includes").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Wording " + viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("Any person who includes").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.statesWording.formatDynamicPath("Transaction Control Number")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.statesWording
									.formatDynamicPath("Transaction Control Number").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Wording " + viewOrPrintFullQuotePage.statesWording
							.formatDynamicPath("Transaction Control Number").getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// click on back
			viewOrPrintFullQuotePage.backButton.click();

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// click on edit dwelling
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Building link");

			// Update building occupany percentage to less than 31%
			testData = data.get(data_Value2);
			buildingPage.buildingOccupancyLink.waitTillVisibilityOfElement(60);
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			buildingPage.waitTime(2);// need wait time to load the element

			if (buildingPage.primaryPercentOccupied.checkIfElementIsPresent()
					&& buildingPage.primaryPercentOccupied.checkIfElementIsDisplayed()) {
				buildingPage.primaryPercentOccupied.clearData();
				buildingPage.waitTime(2);// wait time is needed to load the element after clear the data
				if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
						&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
					buildingPage.continueWithUpdateBtn.scrollToElement();
					buildingPage.continueWithUpdateBtn.click();
				}
				buildingPage.primaryPercentOccupied.setData(testData.get("L1B1-PercentOccupied"));
				buildingPage.primaryPercentOccupied.tab();
			}
			if (buildingPage.buildingOccupancy_yes.checkIfElementIsPresent()
					&& buildingPage.buildingOccupancy_yes.checkIfElementIsDisplayed()) {
				buildingPage.buildingOccupancy_yes.waitTillVisibilityOfElement(60);
				buildingPage.buildingOccupancy_yes.scrollToElement();
				buildingPage.buildingOccupancy_yes.click();
				buildingPage.waitTime(2);// wait time is needed to load the element after clear the data
				if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
						&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
					buildingPage.continueWithUpdateBtn.scrollToElement();
					buildingPage.continueWithUpdateBtn.click();
					Assertions.passTest("Building Page", "Entered the Occupancy Percentage more than 31%");
				}
			}

			// click on review building button
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Review Building");

			// click on Override button
			if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
				buildingPage.override.waitTillVisibilityOfElement(60);
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}

			// click on create quote button
			buildingPage.createQuote.waitTillVisibilityOfElement(60);
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			Assertions.passTest("Building Page", "Clicked on Create Quote");

			// click on override button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// select peril
			testData = data.get(data_Value1);
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting quote number
			quoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Renewal Requote Number is " + quoteNumber, false, false);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View print full quote link");

			// Assert forms, label and wording
			Assertions.addInfo("Scenario 04",
					"Asserting presence of SLPS-6-CERT-1 form and completed and signed diligent effort form when occupancy percentage is more than 31% for renewal quote");
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("SLPS-6-CERT-1").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.mainSections.formatDynamicPath("SLPS-6-CERT-1")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"SLPS-6-CERT-1 form is displayed when occupancy percentage is more than 31%", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("CERTIFICATION OF EFFORT")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.mainSections.formatDynamicPath("CERTIFICATION OF EFFORT")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"CERTIFICATION OF EFFORT is displayed when occupancy percentage is more than 31%", false, false);
			Assertions.verify(quoteDetailsPage.diligentEffortFormText.checkIfElementIsDisplayed(), true,
					"Quote Details Page",
					"\"" + quoteDetailsPage.diligentEffortFormText.getData() + "\""
							+ " is found in this Quote since State is " + testData.get("QuoteState")
							+ " and Occupancy Percent is " + testData.get("L1B1-PercentOccupied"),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// click on back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// click on edit dwelling
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Building link");

			// change the year built to 1948
			testData = data.get(data_Value2);
			buildingPage.yearBuilt.scrollToElement();
			buildingPage.yearBuilt.clearData();
			if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
					&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
				buildingPage.continueWithUpdateBtn.scrollToElement();
				buildingPage.continueWithUpdateBtn.click();
			}
			buildingPage.yearBuilt.tab();
			if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
					&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
				buildingPage.continueWithUpdateBtn.scrollToElement();
				buildingPage.continueWithUpdateBtn.click();
			}
			buildingPage.yearBuilt.setData(testData.get("L1B1-BldgYearBuilt"));
			Assertions.passTest("Building Page", "The Year Built Updated to  : " + testData.get("L1B1-BldgYearBuilt"));

			// click on review building button
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Review Building");

			Assertions.addInfo("Scenario 05",
					"Verifying the hardstop error message when the year built prior to 1949 on renewal");
			Assertions.verify(buildingPage.globalError.getData().contains("National Historic registry"), true,
					"Building Page",
					"The Error message " + buildingPage.globalError.getData() + " displayed is verified", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// update the year built to 2020
			testData = data.get(data_Value1);
			buildingPage.yearBuilt.scrollToElement();
			buildingPage.yearBuilt.clearData();
			buildingPage.yearBuilt.setData(testData.get("L1B1-BldgYearBuilt"));
			Assertions.passTest("Building Page", "The Year Built Updated to  : " + testData.get("L1B1-BldgYearBuilt"));

			// click on resubmit
			buildingPage.reSubmit.scrollToElement();
			buildingPage.reSubmit.click();

			// click on create quote
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.addAdditionalCoveragesCommercial(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// getting renewal quote number
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			String quoteNumber3 = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal ReQuote Number :  " + quoteNumber3);

			// click on issue quote
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber3);
			Assertions.passTest("Account Overview Page", "Clicked on request bind button");

			if (!accountOverviewPage.requestBind.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber3);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}

			// enter bind details
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request bind page", "Renewal bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber3);
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

			// Click on approve request button
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Renewal Policy Number is " + policyNumber, false, false);

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as usm successfully");

			// Sign in as Rzimmer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as Rzimmer");

			// click on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

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
			scheduledJobsAdmin.commercialNumberofDays.scrollToElement();
			scheduledJobsAdmin.commercialNumberofDays.appendData(testData.get("NumberOfDays"));
			scheduledJobsAdmin.commercialNumberofDays.tab();
			scheduledJobsAdmin.stateColonNumOfDays.scrollToElement();
			scheduledJobsAdmin.stateColonNumOfDays.appendData(testData.get("State_Colon_NumberOfDay"));
			scheduledJobsAdmin.stateColonNumOfDays.tab();
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
			loginPage.enterLoginDetails("Sminn", setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as USM");

			// Searching policy in team referral section
			homePage.teamReferralFilter.scrollToElement();
			homePage.teamReferralFilter.click();
			homePage.teamReferralFilterOption.formatDynamicPath("File Review").scrollToElement();
			homePage.teamReferralFilterOption.formatDynamicPath("File Review").click();
			homePage.waitTime(3);// Adding wait time to visible the element

			// Verifying presence of file review referral policy
			Assertions.addInfo("Scenario 05", "Verifying file review referral when RNL requote has the occupancy >31%");
			while (!homePage.referralquoteLink.formatDynamicPath(policyNumber).checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			homePage.waitTime(3);// Adding wait time to visible the element
			Assertions.verify(homePage.teamReferralQ3.formatDynamicPath(policyNumber).checkIfElementIsDisplayed(), true,
					"Home page", "File review referral happened is verified", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC033 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC033 ", "Executed Successfully");
			}
		}
	}
}
