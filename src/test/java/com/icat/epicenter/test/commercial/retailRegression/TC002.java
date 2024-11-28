/** Program Description: Check Commercial retail agents will be required to enter the Effective Date of the policy
 * 					   : LA state verifying building valuation message, building valuation row and asserting building value for NB and Renewal [API Valuation, Pavan Mule-11/07/2023]
 *  Author			   : Karthik Malles
 *  Date of Creation   : 19/07/2021
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC002 extends AbstractCommercialTest {

	public TC002() {
		super(LoginType.RETAILPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page Objects and Variables
		HomePage homePage = new HomePage();
		LoginPage loginPage = new LoginPage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		PolicyRenewalPage policyrenewalPage = new PolicyRenewalPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();

		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		String quoteNumber;
		String policyNumber;
		String nbBuildingValue;
		String valuationAction;
		String buildingValue;
		String buildingMinimumValue;

		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Creating New Account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home Page Loaded Successfully", false, false);
			homePage.createNewAccountProducer.click();
			homePage.goButton.waitTillVisibilityOfElement(60);// if the wait time is removed the test case will fail
																// here
			homePage.namedInsured.setData(testData.get("InsuredName"));
			homePage.goButton.scrollToElement();
			homePage.goButton.click();

			// To Move and stay the cursor on effective date field wait is given
			Assertions.addInfo("Scenario 01", "Asserting the error message if the effective date is not entered");
			waitTime(6);// if the wait time is removed the test case will fail here
			homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed();
			homePage.effectiveDate.formatDynamicPath(1).scrollToElement();

			WebDriver driver = WebDriverManager.getCurrentDriver();
			Actions actions = new Actions(driver);

			WebElement effectiveDate = driver.findElement(
					By.xpath("(//div[contains(@class,'popover-content')]//input[contains(@id,'EFFECTIVE_DATE')])[1]"));

			// Perform the hover action
			actions.moveToElement(effectiveDate).perform();

			Assertions.verify(homePage.invalidResponseForNoEffectiveDate.checkIfElementIsDisplayed(), true, "Home Page",
					"Error Message is displayed since No Effective Date is entered as " + "\""
							+ homePage.invalidResponseForNoEffectiveDate.getData() + "\"",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// log out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home  page ", "SignOut as producer successfully");

			// API Valuation Started
			// Sign in as usm
			Assertions.passTest("Login page", "Login page loaded successfully");
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "logged in as USM successfully");

			// creating New account
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location page",
					"Location page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location page", "Location details entered successfully");

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
			buildingPage.buildingValue.tab();
			buildingPage.businessPersonalProperty.tab();
			waitTime(2);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building page", "Building details enter successfully");

			// Clicking on override button
			if (existingAccountPage.override.checkIfElementIsPresent()
					&& existingAccountPage.override.checkIfElementIsDisplayed()) {
				existingAccountPage.override.scrollToElement();
				existingAccountPage.override.click();
			}

			if (buildingPage.createQuote.checkIfElementIsPresent()) {
				buildingPage.editBuilding.scrollToElement();
				buildingPage.editBuilding.click();
				buildingPage.reviewBuilding.scrollToElement();
				buildingPage.reviewBuilding.click();
			}

			// Asserting the Minimum building valuation message, "The minimum
			// Building value for this risk is $XX
			// Do you want to update to $XX or override to allow the input value?"
			Assertions.addInfo("Scenario 02", "Asserting the minimum valuation message");
			Assertions.verify(
					buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value for this risk").getData()
							.contains("The minimum Building value for this risk"),
					true, "Building under minimum cost page",
					"The minimum building valuation message, "
							+ buildingUnderMinimumCostPage.costcardMessage
									.formatDynamicPath("The minimum Building value for this risk").getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 02 ", "Scenario 02 Ended");

			// Clicking on override button
			buildingUnderMinimumCostPage.clickOnOverride();

			// Checking Building valuation row is displayed on the building page.
			Assertions.addInfo("Scenario 03", "Asserting presence of building valuation row in building page");
			Assertions.verify(
					buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation").checkIfElementIsPresent()
							&& buildingPage.buildingValuationRow
									.formatDynamicPath("Building Valuation").checkIfElementIsDisplayed(),
					true, "Building page",
					"The building valuation row,  "
							+ buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation").getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on create quote
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// Clicking on override button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select peril page",
					"Select peril page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
					"Prior loss page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Asserting building value in create quote page. Here the building value should
			// be same as the value
			// from test data as we are selecting override option instead of bringuptocost
			Assertions.addInfo("Scenario 04",
					"Asserting building value in create quote page. Here the building value should be same as the value, from test data as we are selecting override option instead of bringuptocost");
			nbBuildingValue = createQuotePage.buildingValue.formatDynamicPath(0, 0).getData().replace(",", "");
			Assertions.verify(
					nbBuildingValue, testData.get("L1B1-BldgValue"), "Create quote page", "The building value, "
							+ createQuotePage.buildingValue.formatDynamicPath(0, 0).getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Quote number :  " + quoteNumber);

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request Bind");

			// entering bind details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind request page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home page", "Quote for referral is searched successfully");

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy summary page", "Policy summary page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy summary page", "Policy number is " + policyNumber, false, false);

			// Click on home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Add expac details
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search policy number
			homePage.searchPolicy(policyNumber);

			// clicking on renewal policy link
			policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy summary page", "Clicked on Renewal link successfully");

			// applying "leave at current" value
			Assertions.verify(policyrenewalPage.continueRenewal.checkIfElementIsDisplayed(), true,
					"Policy renewal page", "Policy renewal page loaded successfully", false, false);

			policyrenewalPage.valuationActionArrow.waitTillPresenceOfElement(60);
			policyrenewalPage.valuationActionArrow.waitTillVisibilityOfElement(60);
			policyrenewalPage.valuationActionArrow.waitTillButtonIsClickable(60);
			policyrenewalPage.valuationActionArrow.scrollToElement();
			policyrenewalPage.valuationActionArrow.click();
			policyrenewalPage.valuationActionOption.formatDynamicPath(1).scrollToElement();
			policyrenewalPage.valuationActionOption.formatDynamicPath(1).click();
			valuationAction = policyrenewalPage.valuationActionData.getData().substring(23, 28);
			Assertions.passTest("Policy renewal page", "Leave at current value applied successfully");

			// Click on continue button
			policyrenewalPage.continueRenewal.scrollToElement();
			policyrenewalPage.continueRenewal.click();
			Assertions.passTest("Policy renewal page", "Clicked on continue button successfully");
			if (policySummaryPage.renewalProcessOkBtn.checkIfElementIsPresent()
					&& policySummaryPage.renewalProcessOkBtn.checkIfElementIsDisplayed()) {
				policySummaryPage.renewalProcessOkBtn.scrollToElement();
				policySummaryPage.renewalProcessOkBtn.click();
			}

			// Click on building link
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			buildingValue = buildingPage.buildingValues.formatDynamicPath("Building").getData().replace("$", "")
					.replace(",", "");

			// Verifying leave at current value and building value from building page both
			// are same as the test data
			Assertions.addInfo("Scenario 05",
					"Verifying leave at current value and building value from building page both are same");
			Assertions.verify(buildingValue, valuationAction, "Building page",
					"Leave at current value and building value both are same as initital value. The leave at current value is "
							+ valuationAction + " and building value is " + buildingValue,
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on view previous policy
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account overview page", "Clicked on view previous policy link successfully");

			// Click on Re-renewal link
			policySummaryPage.re_RenewPolicyLink.scrollToElement();
			policySummaryPage.re_RenewPolicyLink.click();
			Assertions.passTest("Policy summary page", "Clicked on Re-renewal link successfully");

			if (policyrenewalPage.yesButton.checkIfElementIsPresent()
					&& policyrenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyrenewalPage.yesButton.scrollToElement();
				policyrenewalPage.yesButton.click();
			}

			// selecting "apply minimum value" building
			Assertions.verify(policyrenewalPage.continueRenewal.checkIfElementIsDisplayed(), true,
					"Policy renewal page", "Policy renewal page loaded successfully", false, false);
			valuationAction = policyrenewalPage.valuationActionData.getData().substring(30, 36);
			Assertions.passTest("Policy renewal page", "Apply minimum building value applied successfully");

			// Click on continue button
			policyrenewalPage.continueRenewal.scrollToElement();
			policyrenewalPage.continueRenewal.click();
			Assertions.passTest("Policy renewal page", "Clicked on continue button successfully");

			// Click on building link
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			buildingValue = buildingPage.buildingValues.formatDynamicPath("Building").getData().replace("$", "")
					.replace(",", "");

			// Verifying apply minimum building value and building value from building page
			// both are same but different from test data
			Assertions.addInfo("Scenario 06",
					"Verifying apply minimum building value and building value from building page both are same but different from initial value");
			Assertions.verify(buildingValue, valuationAction, "Building page",
					"Apply minimum building value and building value both are same but different from initial value. The apply minimum building value is "
							+ valuationAction + " and building value is " + buildingValue,
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();

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

			// Verifying the View Renewal and Renewal link not available on policy Summary
			// Page
			Assertions.addInfo("Policy Summary Page", "View Active Renewal Link should not diasplayed");
			Assertions.verify(!policySummaryPage.viewActiveRenewal.checkIfElementIsPresent(), true,
					"Policy Summary Page", "View Active Renewal Link not diasplayed", false, false);
			Assertions.addInfo("Policy Summary Page", "Renewal Policy Link should not diasplayed");
			Assertions.verify(!policySummaryPage.renewPolicy.checkIfElementIsPresent(), true, "Policy Summary Page",
					"Renewal Policy Link not diasplayed", false, false);

			// click on renewal indicators link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// select non renewal checkbox
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);
			renewalIndicatorsPage.nonRenewal.scrollToElement();
			renewalIndicatorsPage.nonRenewal.deSelect();
			Assertions.passTest("Renewal Indicators Page", "Selected the Non Renewal Checkbox");

			// click on update
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update Button");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);

			// clicking on renewal policy link
			policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy summary page", "Clicked on Renewal link successfully");

			policyrenewalPage.continueRenewal.scrollToElement();
			policyrenewalPage.continueRenewal.click();

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.verify(accountOverviewPage.lapseRenewal.checkIfElementIsDisplayed(), true,
					"Account overview page", "Clicked on release renewal to producer button successfully", false,
					false);

			// Click on edit pencil link
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			// Changing building value to $45,000
			testData = data.get(dataValue2);
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.enterBuildingValues(testData, 1, 1);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();

			// Asserting the Minimum building valuation message the message is "The minimum
			// Building value for this risk is $XX
			// Do you want to update to $XX or override to allow the input value?"
			buildingMinimumValue = buildingUnderMinimumCostPage.costcardMessage
					.formatDynamicPath("The minimum Building value for this risk").getData().substring(45, 52)
					.replace(",", "");
			Assertions.addInfo("Scenario 07", "Asserting the minimum valuation message");
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
			Assertions.addInfo("Scenario 07 ", "Scenario 07 Ended");

			// Clicking on bringUpToCost button
			if (buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.bringUpToCost.scrollToElement();
				buildingUnderMinimumCostPage.bringUpToCost.click();
			}

			// Checking Building valuation row is displayed on the building page.
			Assertions.addInfo("Scenario 08", "Asserting presence of building valuation row in building page");
			Assertions.verify(
					buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation").checkIfElementIsPresent()
							&& buildingPage.buildingValuationRow
									.formatDynamicPath("Building Valuation").checkIfElementIsDisplayed(),
					true, "Building page",
					"The building valuation row, "
							+ buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation").getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click on create quote
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// Clicking on override button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Selecting a peril
			testData = data.get(dataValue1);
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select peril page",
					"Select peril page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Checking building value from create quote page and building value under
			// buildingUnderMinimum Cost Page both are same
			Assertions.addInfo("Scenario 09",
					"Checking building value from create quote page and building value under building under minimum cost page both are same but different from intitial renewal value");
			nbBuildingValue = createQuotePage.buildingValue.formatDynamicPath(0, 0).getData().replace(",", "");
			Assertions.verify(nbBuildingValue, buildingMinimumValue, "Create quote page",
					"Building value from create quote page and building value from building under minimum cost page both are same but different from initial renewal value. The building value from create quote page is $"
							+ createQuotePage.buildingValue.formatDynamicPath(0, 0).getData()
							+ "The Building value from under minimum cost page is $" + buildingMinimumValue,
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// getting the quote number
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Renewal quote number :  " + quoteNumber);

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC002 ", "Executed Successfully");
			}
		}
	}
}
