/** Program Description: Check if NJ quote has state specific wordings and forms
and verifying the NRNL dates are displayed according to the Notice period and verifying the presence of NRNL documents
 *  Author			   : John
 *  Date of Creation   : 23/07/202
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;
import org.openqa.selenium.JavascriptExecutor;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ScheduledJobsAdmin;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC018 extends AbstractCommercialTest {

	public TC018() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID018.xls";
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
		PriorLossesPage priorLossPage = new PriorLossesPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		ScheduledJobsAdmin scheduledJobsAdmin = new ScheduledJobsAdmin();

		// Initializing variables
		// testData = data.get(0);
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		String policyNumber;
		String quoteNumber;
		int numOfPolicyinEffect = 60;
		double d_numOfPolicyinEffect = numOfPolicyinEffect;
		int totalNumofdays = 365;
		double d_totalNumofdays = totalNumofdays;
		int numone = 1;
		double d_numone = numone;
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Entering Location 1 Dwelling 1 Details
			
			buildingPage.enterBuildingDetails(testData);

			// select peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

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

			// Asserting quote number
			quoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Quote Number is " + quoteNumber, false, false);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View/Print Full quote link");

			// Assert forms, label and wording
			Assertions.addInfo("Scenario 01", "Asserting SLPS-6-CERT-1 form, NJ specific wording and labels");
			Assertions.verify(
					viewOrPrintFullQuotePage.statementData.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.statementData.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.statementData.getData() + " is displayed", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("Transaction Control Number")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("Transaction Control Number").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page", viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("Transaction Control Number").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording.formatDynamicPath("document is not a part")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
									.formatDynamicPath("document is not a part").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page", viewOrPrintFullQuotePage.viewOrPrintFullQuoteWording
							.formatDynamicPath("document is not a part").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("SLPS-6-CERT-1").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.mainSections.formatDynamicPath("SLPS-6-CERT-1")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("SLPS-6-CERT-1").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("CERTIFICATION OF EFFORT")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.mainSections.formatDynamicPath("CERTIFICATION OF EFFORT")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("CERTIFICATION OF EFFORT").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.labelData.formatDynamicPath("Name of Insured").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.labelData.formatDynamicPath("Name of Insured")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.labelData.formatDynamicPath("Name of Insured").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.labelData.formatDynamicPath("Address of Insured").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.labelData.formatDynamicPath("Address of Insured")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.labelData.formatDynamicPath("Address of Insured").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.labelData.formatDynamicPath("Location of Property or Risk")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.labelData.formatDynamicPath("Location of Property or Risk")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.labelData.formatDynamicPath("Location of Property or Risk").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.labelData.formatDynamicPath("Insurance Coverage: Description and Amount")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.labelData
									.formatDynamicPath("Insurance Coverage: Description and Amount")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.labelData.formatDynamicPath("Insurance Coverage: Description and Amount")
							.getData() + " is displayed",
					false, false);

			// click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			waitTime(3);
			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getCurrentDriver();

			// Scroll to the middle of the page
			js.executeScript("window.scrollTo(0, document.body.scrollHeight * 0.65);");

			Assertions.verify(
					viewOrPrintFullQuotePage.labelData1.formatDynamicPath(5).checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.labelData1.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.labelData1.formatDynamicPath(5).getData() + " is displayed", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.labelData1.formatDynamicPath(6).checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.labelData1.formatDynamicPath(6).checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.labelData1.formatDynamicPath(6).getData() + " is displayed", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.labelData1.formatDynamicPath(7).checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.labelData1.formatDynamicPath(7).checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.labelData1.formatDynamicPath(7).getData() + " is displayed", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("REPRESENTATIVE").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.mainSections.formatDynamicPath("REPRESENTATIVE")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("REPRESENTATIVE").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("TELEPHONE NO").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.mainSections.formatDynamicPath("TELEPHONE NO")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("TELEPHONE NO").getData() + " is displayed",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.mainSections.formatDynamicPath("DATE").checkIfElementIsPresent()
					&& viewOrPrintFullQuotePage.mainSections.formatDynamicPath("DATE").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("DATE").getData() + " is displayed", false,
					false);
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("RESULTS CODE").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.mainSections.formatDynamicPath("RESULTS CODE")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("RESULTS CODE").getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on back button
			viewOrPrintFullQuotePage.backButton.click();

			// click on edit dwelling
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Building link");

			// Click on building occupancy link
			testData = data.get(1);
			buildingPage.buildingOccupancyLink.waitTillVisibilityOfElement(60);
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();

			// Update building occupancy percentage to less than 31%
			if (buildingPage.primaryPercentOccupied.checkIfElementIsPresent()
					&& buildingPage.primaryPercentOccupied.checkIfElementIsDisplayed()) {
				buildingPage.primaryPercentOccupied.clearData();
				buildingPage.waitTime(2);// wait time is needed to load the element after clear the data

				// Click on continue update button
				if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
						&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
					buildingPage.continueWithUpdateBtn.scrollToElement();
					buildingPage.continueWithUpdateBtn.click();
				}
				buildingPage.primaryPercentOccupied.setData(testData.get("L1B1-PercentOccupied"));
				buildingPage.primaryPercentOccupied.tab();
			}

			// Select building occupancy No
			if (buildingPage.buildingOccupancy_no.checkIfElementIsPresent()
					&& buildingPage.buildingOccupancy_no.checkIfElementIsDisplayed()) {
				buildingPage.waitTime(3);
				buildingPage.buildingOccupancy_no.scrollToElement();
				buildingPage.buildingOccupancy_no.click();
				buildingPage.waitTime(3);// wait time is needed to load the element after clear the data

				// Click on continue update button
				if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
						&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
					buildingPage.continueWithUpdateBtn.scrollToElement();
					buildingPage.continueWithUpdateBtn.click();
				}
			}

			// click on review building button
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();

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

			// click on override button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// select peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting quote number
			String quoteNumber2 = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Quote Number is " + quoteNumber2, false, false);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View print full quote link");

			// Assert forms, label and wording
			Assertions.addInfo("Scenario 02",
					"Asserting absence of SLPS-6-CERT-1 form when occupancy percentage is less than 31%");
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
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// click on back button
			viewOrPrintFullQuotePage.backButton.click();

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber2);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			if (!accountOverviewPage.requestBind.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber2);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}
			// entering details in request bind page
			testData = data.get(data_Value1);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// Approve Referral
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
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Adding the CR IO-19337
			// click on cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy Link");
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page Loaded successfully", false, false);

			// Select Cancellation reason and enter cancellation effective date as 60 days
			// future
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.waitTime(3);// need waittime to select the option
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());
			if (!testData.get("CancellationEffectiveDate").equals("")) {
				cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
				cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
				cancelPolicyPage.cancellationEffectiveDate.tab();
				Assertions.passTest("Cancel Policy Page",
						"Cancellation Effective date : " + testData.get("CancellationEffectiveDate"));
			}
			if (!testData.get("Cancellation_LegalNoticeWording").equals("")) {
				cancelPolicyPage.legalNoticeWording.scrollToElement();
				cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			}
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Getting Actual Prorata values for Pro Rated,Short Rated,Pro Rated Min Earned
			String actualProratedProratafactor = cancelPolicyPage.prorataFactor.formatDynamicPath(2).getData();
			double d_actualProratedProratafactor = Double.parseDouble(actualProratedProratafactor);
			String actualShortratedProratafactor = cancelPolicyPage.prorataFactor.formatDynamicPath(3).getData();
			double d_actualShortratedProratafactor = Double.parseDouble(actualShortratedProratafactor);
			String actualProratedMinearnedProratafactor = cancelPolicyPage.prorataFactor.formatDynamicPath(1).getData();
			double d_actualProratedMinearnedProratafactor = Double.parseDouble(actualProratedMinearnedProratafactor);

			// Calculation of Pro rata factor value for Pro Rated [1- (The No. of Days
			// Policy is in Effect/ 365 Days)]
			double calcProratedProratafactor = d_numone - (d_numOfPolicyinEffect / d_totalNumofdays);

			// Calculation of Pro rata factor value for Short Rated [Prorata Factor (above)
			// * 0.9]
			double calcShortratedProratafactor = calcProratedProratafactor * 0.9;

			// Pro-Rated Min Earned is always equal to 0.75
			String calcMinearnedProratafactor = "0.75";

			// Comparing Actual and calculated values
			Assertions.addInfo("Scenario 03",
					"Calculating and Comparing the Prorata values of Pro Rated,Short Rated,Pro Rated Min Earned when the cancellation effective date is 60 days future date");
			if (Precision.round(Math.abs(
					Precision.round(d_actualProratedProratafactor, 3) - Precision.round(calcProratedProratafactor, 3)),
					3) < 0.2) {
				Assertions.passTest("Cancel Policy Page",
						"Calculated Prorated Prorata Factor is : " + Precision.round(calcProratedProratafactor, 3));
				Assertions.passTest("Cancel Policy Page",
						"Actual Prorated Prorata Factor is : " + actualProratedProratafactor);
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual  and calculated Prorated Prorata factor is more than 0.05");
			}

			if (Precision.round(Math.abs(Precision.round(d_actualShortratedProratafactor, 3)
					- Precision.round(calcShortratedProratafactor, 3)), 3) < 0.2) {
				Assertions.passTest("Cancel Policy Page", "Calculated Short Rated Prorata Factor is : "
						+ Precision.round(calcShortratedProratafactor, 3));
				Assertions.passTest("Cancel Policy Page",
						"Actual Short Rated Prorata Factor is : " + actualShortratedProratafactor);
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual  and calculated Shortrated Prorata factor is more than 0.05");
			}

			if (Precision.round(Math.abs(Precision.round(d_actualProratedMinearnedProratafactor, 3)
					- Precision.round(Double.parseDouble(calcMinearnedProratafactor), 3)), 3) < 0.2) {
				Assertions.passTest("Cancel Policy Page", "Calculated  Prorata Factor for Pro-Rated Min Earned is : "
						+ Precision.round(Double.parseDouble(calcMinearnedProratafactor), 3));
				Assertions.passTest("Cancel Policy Page", "Actual Prorata Factor for Pro-Rated Min Earned is : "
						+ d_actualProratedMinearnedProratafactor);
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual  and calculated Shortrated Prorata factor is more than 0.05");
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on home link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search the Policy Number
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// Asserting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

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

			// Verifying the NRNL date is displayed according to the Notice period for the
			// state FL
			Assertions.addInfo("Scenario 04",
					"Verifying the NRNL date is displayed according to the Notice period for the state FL");
			Assertions.verify(
					policySummaryPage.nocMessage.getData().contains("Renewal Indicators Successfully Updated"), true,
					"Policy Summary Page", "The Message displayed is " + policySummaryPage.nocMessage.getData(), false,
					false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

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

			// enter report LOB
			scheduledJobsAdmin.runDate.setData(testData.get("RunDate"));
			scheduledJobsAdmin.runStateArrow.scrollToElement();
			scheduledJobsAdmin.runStateArrow.click();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("QuoteState")).scrollToElement();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("QuoteState")).click();

			// enter report only option
			scheduledJobsAdmin.reportOnlyArrow.scrollToElement();
			scheduledJobsAdmin.reportOnlyArrow.click();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("ReportOnlyOption")).scrollToElement();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("ReportOnlyOption")).click();

			// select product
			scheduledJobsAdmin.lineOfBusinessArrow.scrollToElement();
			scheduledJobsAdmin.lineOfBusinessArrow.click();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("ProductSelection")).scrollToElement();
			scheduledJobsAdmin.runReportLOBOption.formatDynamicPath(testData.get("ProductSelection")).click();
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
					"Producer Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);

			// Asserting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC018 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC018 ", "Executed Successfully");
			}
		}
	}
}
