/** Program Description: Create a RNL policy with Occupancy <31% and Non-habitational Primary Occupancy (Restaurant). As a part of ENDT update the Occupancy to >31% and Occupancy to Habitational (Apartment).check the Referral is happening
 *  Author			   : Sowndarya
 *  Date of Creation   : 09/03/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
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
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ScheduledJobsAdmin;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC045 extends AbstractCommercialTest {

	public TC045() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID045.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		LoginPage loginPage = new LoginPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		ScheduledJobsAdmin scheduledJobsAdmin = new ScheduledJobsAdmin();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();

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
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 1 :  " + quoteNumber);

			// Adding the CR IO-19776
			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");

			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View or Print Full quote page loaded successfully", false, false);

			// Get the premium
			String premiumwithCgcc = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			double d_premiumwithCgcc = Double.parseDouble(premiumwithCgcc);

			// click back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// click on edit deductibles and limits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit deductibles and limits");

			// Change Sinkhole value to None
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			testData = data.get(data_Value2);
			createQuotePage.addAdditionalCoveragesCommercial(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get a quote");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			String quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 2 :  " + quoteNumber2);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");

			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View or Print Full quote page loaded successfully", false, false);

			// Get the premium
			String premiumwithoutCgcc = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",",
					"");
			double d_premiumwithoutCgcc = Double.parseDouble(premiumwithoutCgcc);

			// Substract the premiums
			double diffvalue = d_premiumwithCgcc - d_premiumwithoutCgcc;

			// click back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber2);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			if (requestBindPage.submit.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber2);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}

			// entering bind details in request bind page
			testData = data.get(data_Value1);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind Request Page",
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
					"Policy Summary Page", "Policy Number : " + policyNumber, false, false);

			// click on endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Enter Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Click on Change coverage options link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Changed Coverage Options link");

			// Change the sink home option to CGCC
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.addAdditionalCoveragesCommercial(testData);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue Endorsement Button");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// click on Next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on Override fees
			endorsePolicyPage.overrideFeesButton.scrollToElement();
			endorsePolicyPage.overrideFeesButton.click();

			// select Waive premium check box
			overridePremiumAndFeesPage.transactionWaivePremium.scrollToElement();
			overridePremiumAndFeesPage.transactionWaivePremium.select();

			// Click on save and close
			overridePremiumAndFeesPage.saveAndClose.scrollToElement();
			overridePremiumAndFeesPage.saveAndClose.click();

			// get the annual premium
			String annualPremium = endorsePolicyPage.premium.formatDynamicPath("waivedPremium", "2").getData()
					.replace("-", "").replace("$", "");

			// Compare the substracted value and and the actual waived premium
			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(annualPremium), 2) - Precision.round(diffvalue, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"The waived premium is equal to the value of differnce between the premium with CGCC and Premium without CGCC");
			} else {
				Assertions.passTest("Endorse Policy Page",
						"The Difference between actual  and calculated premium is more than 0.05");
			}

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
			if (policyRenewalPage.renewalYes.checkIfElementIsPresent()
					&& policyRenewalPage.renewalYes.checkIfElementIsDisplayed()) {
				policyRenewalPage.renewalYes.scrollToElement();
				policyRenewalPage.renewalYes.click();
			}

			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			if (policyRenewalPage.renewalYes.checkIfElementIsPresent()
					&& policyRenewalPage.renewalYes.checkIfElementIsDisplayed()) {
				policyRenewalPage.renewalYes.scrollToElement();
				policyRenewalPage.renewalYes.click();
			}

			// getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request bind button successfully");
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);

			// Enter bind details
			requestBindPage.addContactInformation(testData);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.confirmBind();
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// clicking on home button
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home page", "Clicked on home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy summary page", "Renewal policy number is : " + policyNumber);

			// endorse policy
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endorsement Effective Date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on edit Location/Building information hyperlink
			Assertions.addInfo("Endorse Policy Page", "Edit the Location/Building information");
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building Details link");

			// Modifying Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();

			// modifying Building 1 Occupancy details
			Assertions.passTest("Building Page", "Building Page loaded successfully");
			// buildingPage.editBuildingOccupancyPNB(testData, 1, 1);
			buildingPage.buildingOccupancyLink.waitTillVisibilityOfElement(60);
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			buildingPage.waitTime(1);
			if (buildingPage.buildingOccupancy_yes.checkIfElementIsPresent()
					&& buildingPage.buildingOccupancy_yes.checkIfElementIsDisplayed()) {
				if ((testData.get("L1B1-BuildingMorethan31%Occupied").equals("Yes"))) {
					buildingPage.buildingOccupancy_yes.waitTillPresenceOfElement(60);
					buildingPage.buildingOccupancy_yes.waitTillVisibilityOfElement(60);
					buildingPage.buildingOccupancy_yes.scrollToElement();
					buildingPage.buildingOccupancy_yes.click();
				}
			}

			if (testData.get("L1B1-PrimaryOccupancyCode") != null
					&& !testData.get("L1B1-PrimaryOccupancyCode").equalsIgnoreCase("")) {
				if (!buildingPage.primaryOccupancy.getData().equals("")) {
					WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
							"//div[label[a[contains(text(),'Primary Occupancy')]]]//following-sibling::div//input[contains(@id,'primaryOccupancy')]"));
					ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
					ele.sendKeys(testData.get("L1B1-PrimaryOccupancy"));
				}
				buildingPage.setOccupancyJS("primary", testData.get("L1B1-PrimaryOccupancyCode"), testData.get("Peril"),
						testData.get("QuoteState"));
			}
			buildingPage.waitTime(2);
			if (testData.get("L1B1-PriOccupancyCondition").equalsIgnoreCase("Yes")) {
				buildingPage.primaryOccupancyCondition.waitTillVisibilityOfElement(60);
				if (buildingPage.primaryOccupancyCondition.checkIfElementIsDisplayed()
						&& buildingPage.primaryOccupancyCondition.checkIfElementIsEnabled()) {
					buildingPage.primaryOccupancyCondition.scrollToElement();
					buildingPage.primaryOccupancyCondition.select();
				}
			}
			buildingPage.reviewBuilding();
			if (buildingPage.continueButton.checkIfElementIsPresent()
					&& buildingPage.continueButton.checkIfElementIsDisplayed()) {
				buildingPage.continueButton.scrollToElement();
				buildingPage.continueButton.click();
			}

			// click on continue button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on complete
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			endorsePolicyPage.closeButton.waitTillInVisibilityOfElement(60);

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
			loginPage.enterLoginDetails("sminn", setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as usm");

			// Searching policy in team referral section
			homePage.teamReferralFilter.scrollToElement();
			homePage.teamReferralFilter.click();
			homePage.teamReferralFilterOption.formatDynamicPath("File Review").scrollToElement();
			homePage.teamReferralFilterOption.formatDynamicPath("File Review").click();
			homePage.waitTime(3);// Adding wait time to visible the element

			// Verifying presence of file review referral policy
			Assertions.addInfo("Scenario 01", "Verifying file review referral");
			while (!homePage.referralquoteLink.formatDynamicPath(policyNumber).checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			homePage.waitTime(3);// Adding wait time to visible the element
			Assertions.verify(homePage.teamReferralQ3.formatDynamicPath(policyNumber).checkIfElementIsDisplayed(), true,
					"Home page", "File review referral happened is verified", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC045 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC045 ", "Executed Successfully");
			}
		}
	}
}
