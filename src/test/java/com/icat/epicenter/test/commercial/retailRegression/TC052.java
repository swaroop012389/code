/** Program Description: Verifying the Cost card messagess for Arizona,Arkanas and Utah States and IO-22101,22093 and 22035
 *  Author			   : Sowndarya
 *  Date of Creation   : 31/03/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC052 extends AbstractCommercialTest {

	public TC052() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID052.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingUnderMinimumCostPage buildingUnderminimumCost = new BuildingUnderMinimumCostPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		DwellingPage dwellingPage = new DwellingPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();

		// Initializing variable
		int dataValue1 = 0;
		int dataValue4 = 3;

		Map<String, String> testData = data.get(dataValue1);
		String quoteNumber;
		String policyNumber;
		boolean isTestPassed = false;

		try {
			for (int i = 0; i <= 2; i++) {
				testData = data.get(i);

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

				// click on add buildings button
				locationPage.addBuildingsButton.scrollToElement();
				locationPage.addBuildingsButton.click();

				// Enter Building Details
				//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
						//"Building page loaded successfully", false, false);
				buildingPage.addBuildingDetails(testData, 1, 1);
				buildingPage.addBuildingOccupancy(testData, 1, 1);
				buildingPage.enterBuildingValues(testData, 1, 1);
				Assertions.passTest("Building Page", "Construction type: " + testData.get("L1B1-BldgConstType"));
				Assertions.passTest("Building Page", "Occupancy type: " + testData.get("L1B1-PrimaryOccupancy"));
				Assertions.passTest("Building Page", "Building Square Feet: " + testData.get("L1B1-BldgSqFeet"));
				Assertions.passTest("Building Page", "Building details entered successfully");

				// Click on Create quote button
				buildingPage.createQuote.scrollToElement();
				buildingPage.createQuote.click();

				// existing account found page
				if (buildingPage.pageName.getData().contains("Existing Account Found")) {
					existingAccountPage.OverrideExistingAccount();
				}

				// Verifying Bring UpToCost button
				Assertions.passTest("Building Page", "Clicked on Create Quote button successfully");
				Assertions.verify(buildingUnderminimumCost.bringUpToCost.checkIfElementIsDisplayed(), true,
						"Building Under Minimum Cost Page", "Bring UpToCost button displayed is verified", false,
						false);

				// Getting Expected Cost card value
				String costCardValue = testData.get("CostCardValue");
				Assertions.passTest("CostCard value", "CostCard value: " + costCardValue);

				// Getting expected square feet value
				String squareFeet = testData.get("L1B1-BldgSqFeet");
				Assertions.passTest("SquareFeet", "Square Feet: " + squareFeet);

				// Verifying the Costcard message and verifying the actual and expected cost
				// card values
				Assertions.addInfo("Scenario 01",
						"Verifying the Costcard message and Verifying the actual and expected cost card values");
				Assertions.addInfo("",
						"<span class='header'> " + "Construction Type: " + testData.get("L1B1-BldgConstType") + ","
								+ " Occupancy type: " + testData.get("L1B1-PrimaryOccupancy") + ", SquareFeet: "
								+ testData.get("L1B1-BldgSqFeet") + "," + " Quote State : " + testData.get("QuoteState")
								+ " </span>");
				Assertions.verify(
						buildingUnderminimumCost.costcardMessage
								.formatDynamicPath("Building value").checkIfElementIsDisplayed(),
						true, "Building Under Minimum Cost Page",
						"The Costcard message "
								+ buildingUnderminimumCost.costcardMessage.formatDynamicPath("Building value").getData()
								+ " displayed is verified",
						false, false);
				Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

				// click on BringUpToCost button
				if (buildingPage.pageName.getData().contains("Under Minimum Cost")) {
					buildingUnderminimumCost.clickOnOverride();
					Assertions.passTest("Building UnderMinimum Cost Page", "Clicked on Override button successfully");
				}

				if (buildingPage.pageName.getData().contains("Buildings No")) {
					buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
				}

				// go to home page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
			}

			// Adding below code for IO-22101,22093 and IO-22035
			// creating New account
			testData = data.get(dataValue4);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zip code in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Building details entered successfully");

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// enter prior loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior loss details entered successfully");

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

			// Click on Rewrite button
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on rewrite link");

			// Click on edit location
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit location link and building link");

			// Click on building link
			buildingPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.passTest("Building Page", "Clicked on edit building");

			// click on roof details link
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page is loaded successfully", false, false);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Building Page", "Clicked on roof details link");

			// Verifying absence of roof age drop down on building page for rewrite quote
			Assertions.addInfo("Scenario 01",
					"Verifying absence of roof age drop down on building page for rewrite quote");
			Assertions.verify(
					buildingPage.oldRoofAgeData.checkIfElementIsPresent()
							&& buildingPage.oldRoofAgeData.checkIfElementIsDisplayed(),
					false, "Building Page", "Roof age drop down not displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Verifying Year roof last replaced wording is Year Roof Last Replaced
			// (Defaults to Year Built if empty): on building page for rewrite quote
			Assertions.addInfo("Scenario 02",
					"Verifying Year roof last replaced wording on building page for rewrite quote");
			Assertions.verify(buildingPage.yearRoofLastReplacedLabel.getData().contains("Year Roof Last Replaced"),
					true, "Building Page", "Year roof last replaced wording is "
							+ buildingPage.yearRoofLastReplacedLabel.getData() + " dispalyed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// add year roof last replaced
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.appendData(testData.get("YearRoofLastReplaced"));
			buildingPage.yearRoofLastReplaced.tab();
			Assertions.passTest("Building Page", "Year roof last replaced added successfully");

			// Click on continue with updated button
			if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
					&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
				buildingPage.continueWithUpdateBtn.scrollToElement();
				buildingPage.continueWithUpdateBtn.click();
				Assertions.passTest("Building Page", "Clicked on continue with update button");
			}
			// IO-22035
			// Asserting and verifying Year roof last replaced message, when adding year
			// roof last replaced, the message is'The entire roof must be fully replaced in
			// order to consider an updated roof year
			// Roof Replacement: The removal and replacement of the entire roof surface down
			// to the decking.
			// Partial repairs, recoating or temporary repairs are considered maintenance
			// only and do not determine the age of the roof.
			// Check out our Roof Replacement vs. Recoat Video for more information.
			buildingPage.yearRoofLastReplacedMessage.waitTillPresenceOfElement(60);
			Assertions.addInfo("Scenario 03",
					"Asserting and verifying Year roof last replaced message when year roof last replaced added for rewrite");
			buildingPage.yearRoofLastReplacedMessage.scrollToElement();
			Assertions.verify(
					buildingPage.yearRoofLastReplacedMessage.getData().contains(
							"The entire roof must be fully replaced in order to consider an updated roof year"),
					true, "Building Page", "Year roof last replaced message is "
							+ buildingPage.yearRoofLastReplacedMessage.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// click on review building
			buildingPage.waitTime(2);
			buildingPage.scrollToBottomPage();
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on review building button");

			// Verifying presence of year roof last replaced label and value, when added the
			// year roof last replaced in dwelling page for rewrite
			Assertions.addInfo("Scenario 04",
					"Verifying presence of year roof last replaced label and value, when added the year roof last replaced for rewrite");
			Assertions.verify(
					dwellingPage.yearRoofLastReplacedlabel.checkIfElementIsDisplayed()
							&& dwellingPage.yearRoofLastReplacedlabel.getData().contains("Year Roof Last Replaced"),
					true, "Dwelling Page", "Year Roof Last Replaced wording is "
							+ dwellingPage.yearRoofLastReplacedlabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					dwellingPage.roofLastReplacedData.checkIfElementIsDisplayed() && dwellingPage.roofLastReplacedData
							.getData().contains(testData.get("YearRoofLastReplaced")),
					true, "Dwelling Page",
					"Year Roof Last Replaced value is " + dwellingPage.roofLastReplacedData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on create quote button");

			if (buildingPage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
			}

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// Enter quote details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quotw Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Rewrite Quote Number :  " + quoteNumber);

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view print full quote link successfully");

			// Verifying presence of year roof last replaced, wording and value, when added
			// year roof last replaced on rewrite quote
			Assertions.addInfo("Scenario 05",
					"Verifying presence of year roof last replaced, wording and value on view print full quote page,when added year roof last replaced on rewrite quote");
			Assertions.verify(viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.checkIfElementIsDisplayed()
					&& viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.getData().contains("Year Roof Last Replaced"),
					true, "View Print Full Quote Page", "Year roof last replaced wording is "
							+ viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.getData(),
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.yearRoofLastReplacedValue.formatDynamicPath(1, 1, 2).getData()
							.substring(25, 29).contains(testData.get("YearRoofLastReplaced")), true,
							"View Print Full Quote Page",
							"Year Roof Last Replaced is " + viewOrPrintFullQuotePage.yearRoofLastReplacedValue
									.formatDynamicPath(1, 1, 2).getData().substring(25, 29) + " displayed",
							false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Print Full Quote Page", "Clicked on back button");

			// Click on edit location
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit location link");

			// Click on building link
			buildingPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(1, 1).click();

			// click on roof details link
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page is loaded successfully", false, false);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Building Page", "Clicked on roof details link ");

			// Removing year roof last replaced
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.clearData();
			Assertions.passTest("Building Page", "Year roof last replaced removed successfully");

			// Click on continue with updated button
			if (buildingPage.continueWithUpdateBtn.checkIfElementIsPresent()
					&& buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed()) {
				buildingPage.continueWithUpdateBtn.scrollToElement();
				buildingPage.continueWithUpdateBtn.click();
				Assertions.passTest("Building Page", "Clicked on continue with updated button");
			}

			// click on review building
			buildingPage.waitTime(3);
			buildingPage.scrollToBottomPage();
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on review building button");

			// When year roof last replaced not added the default value is N/A on rewrite
			// quote
			Assertions.addInfo("Scenario 06",
					"Verifying absence of year roof last replaced value on dwelling page, when year roof last replaced not added on rewrite quote");
			Assertions.verify(
					dwellingPage.roofLastReplacedData.checkIfElementIsDisplayed()
							&& dwellingPage.roofLastReplacedData.getData().contains("N/A"),
					true, "Dwelling Page",
					"Year Roof Last Replaced value is " + dwellingPage.roofLastReplacedData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on create quote button");

			if (buildingPage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
			}

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// Enter quote details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quotw Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Rewrite Quote Number :  " + quoteNumber);

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view print full quote link successfully");

			// Verifying presence of year roof last replaced, wording and value,when not
			// added year roof last replaced the value of year roof last replaced is same as
			// year built
			// year roof last replaced on rewrite quote
			Assertions.addInfo("Scenario 07",
					"Verifying presence of year roof last replaced, wording and value on view print full quote page,when not added year roof last replaced, the value of year roof last replaced is same as year built");
			Assertions.verify(viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.checkIfElementIsDisplayed()
					&& viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.getData().contains("Year Roof Last Replaced"),
					true, "View Print Full Quote Page", "Year roof last replaced wording is "
							+ viewOrPrintFullQuotePage.yearRoofLastReplacedLabel.getData(),
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.yearRoofLastReplacedValue.formatDynamicPath(1, 1, 2).getData()
							.substring(25, 29).contains(testData.get("L1B1-BldgYearBuilt")), true,
							"View Print Full Quote Page",
							"Year Roof Last Replaced is " + viewOrPrintFullQuotePage.yearRoofLastReplacedValue
									.formatDynamicPath(1, 1, 2).getData().substring(25, 29) + " displayed",
							false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on go back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("view Print Full Quote Page", "Clicked on back button");

			// Click on rewrite button
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on rewrite button");

			// Enter Rewrite details
			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enteringRewriteData(testData);
			Assertions.passTest("Request Bind Page", "Rewrite details entered successfully");

			// Get policy number from policy summary page
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy summary page", "Rewrit Policy number is " + policyNumber, false, false);

			// Click on Endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on endorse pb link");

			// Enter endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Endorsement effective date entered successfully");

			// click on edit location or building link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on edit location or building link");

			// Clicked on building link and roof details
			Assertions.verify(buildingPage.continueButton.checkIfElementIsDisplayed(), true, "Building Page",
					"Building page loaded successfully", false, false);
			buildingPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(1, 1).click();
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Building Page", "Clicked on building link and roof details link");

			// Verifying absence of roof age drop down while creating endorsement
			Assertions.addInfo("Scenario 08",
					"Verifying absence of roof age drop down on building page, while creating endorsement");
			Assertions.verify(
					buildingPage.oldRoofAgeData.checkIfElementIsPresent()
							&& buildingPage.oldRoofAgeData.checkIfElementIsDisplayed(),
					false, "Building Page", "Roof age drop down not displayed", false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Verifying Year roof last replaced wording is Year Roof Last Replaced
			// (Defaults to Year Built if empty):while creating endorsement
			Assertions.addInfo("Scenario 09",
					"Verifying Year roof last replaced wording on building page,while creating endorsement");
			Assertions.verify(buildingPage.yearRoofLastReplacedLabel.getData().contains("Year Roof Last Replaced"),
					true, "Building Page", "Year roof last replaced wording is "
							+ buildingPage.yearRoofLastReplacedLabel.getData() + " dispalyed",
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// add year roof last replaced
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.appendData(testData.get("YearRoofLastReplaced"));
			buildingPage.yearRoofLastReplaced.tab();
			Assertions.passTest("Building Page", "Year roof last replaced added successfully");
			buildingPage.waitTime(2);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();

			// IO-22035
			// Asserting and verifying Year roof last replaced message, when adding year
			// roof last replaced, the message is'The entire roof must be fully replaced in
			// order to consider an updated roof year
			// Roof Replacement: The removal and replacement of the entire roof surface down
			// to the decking.
			// Partial repairs, recoating or temporary repairs are considered maintenance
			// only and do not determine the age of the roof.
			// Check out our Roof Replacement vs. Recoat Video for more information.
			buildingPage.yearRoofLastReplacedMessage.waitTillPresenceOfElement(60);
			buildingPage.yearRoofLastReplacedMessage.waitTillVisibilityOfElement(60);
			buildingPage.yearRoofLastReplacedMessage.scrollToElement();

			Assertions.addInfo("Scenario 10",
					"Asserting and verifying Year roof last replaced message on building page,while creating endorsement");
			buildingPage.yearRoofLastReplacedMessage.scrollToElement();
			Assertions.verify(
					buildingPage.yearRoofLastReplacedMessage.getData().contains(
							"The entire roof must be fully replaced in order to consider an updated roof year"),
					true, "Building Page", "Year roof last replaced message is "
							+ buildingPage.yearRoofLastReplacedMessage.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// click on review building
			buildingPage.waitTime(2);
			buildingPage.scrollToBottomPage();
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on review building button");

			// Verifying presence of year roof last replaced label and value, when added the
			// year roof last replaced in dwelling page on dwelling page, while creating
			// endorsement
			Assertions.addInfo("Scenario 11",
					"Verifying presence of year roof last replaced label and value on dwelling page, when added the year roof last replaced,while creating endorsement");
			Assertions.verify(
					dwellingPage.yearRoofLastReplacedlabel.checkIfElementIsDisplayed()
							&& dwellingPage.yearRoofLastReplacedlabel.getData().contains("Year Roof Last Replaced"),
					true, "Dwelling Page", "Year Roof Last Replaced wording is "
							+ dwellingPage.yearRoofLastReplacedlabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					dwellingPage.roofLastReplacedData.checkIfElementIsDisplayed() && dwellingPage.roofLastReplacedData
							.getData().contains(testData.get("YearRoofLastReplaced")),
					true, "Dwelling Page",
					"Year Roof Last Replaced value is " + dwellingPage.roofLastReplacedData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Click on continue button
			dwellingPage.continueButton.scrollToElement();
			dwellingPage.continueButton.click();
			Assertions.passTest("Dwelling Page", "Clicked on continue button");
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}
			Assertions.passTest("Endorse Policy Page", "Clicked on next button and ok button");

			// Click on view endorsement document
			endorsePolicyPage.viewEndtQuoteButton.scrollToElement();
			endorsePolicyPage.viewEndtQuoteButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on view endorsement document");

			// Verifying presence of year roof last replaced, wording and value on endorse
			// quote document, when added
			// year roof last replaced
			Assertions.addInfo("Scenario 12",
					"Verifying presence of year roof last replaced, wording and value on endorse quote document, when added year roof last replaced");
			Assertions.verify(
					quoteDetailsPage.yearRoofLastReplacedLabel.checkIfElementIsDisplayed()
							&& quoteDetailsPage.yearRoofLastReplacedLabel.getData().contains("Year Roof Last Replaced"),
					true, "Endorse Quote Details Page",
					"Year roof last replaced wording is " + quoteDetailsPage.yearRoofLastReplacedLabel.getData(), false,
					false);
			Assertions
					.verify(quoteDetailsPage.yearRoofLastReplacedValue.formatDynamicPath(1, 1, 2).getData()
							.substring(25, 29).contains(testData.get("YearRoofLastReplaced")), true,
							"Endorse Quote Details Page",
							"Year Roof Last Replaced is " + quoteDetailsPage.yearRoofLastReplacedValue
									.formatDynamicPath(1, 1, 2).getData().substring(25, 29) + " displayed",
							false, false);
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			// Click on close button
			quoteDetailsPage.closeBtn.scrollToElement();
			quoteDetailsPage.closeBtn.click();

			// click on complete button and close button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button and close button");

			// Click on Endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on endorse pb link");

			// Enter endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Endorsement effective date entered successfully");

			// click on edit location or building link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on edit location or building link");

			// Clicked on building link and roof details
			Assertions.verify(buildingPage.continueButton.checkIfElementIsDisplayed(), true, "Building Page",
					"Building page loaded successfully", false, false);
			buildingPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(1, 1).click();
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Building Page", "Clicked on building link and roof details link");

			// remove year roof last replaced
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.clearData();
			Assertions.passTest("Building Page", "Year roof last replaced removed successfully");

			// click on review building
			buildingPage.waitTime(3);
			buildingPage.scrollToBottomPage();
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on review building button");

			// When year roof last replaced not added the default value is N/A on dwelling
			// page, while creating endorsement
			Assertions.addInfo("Scenario 13",
					"Verifying absence of year roof last replaced value on dwelling page, when year roof last replaced removed while creating endorsement");
			Assertions.verify(
					dwellingPage.roofLastReplacedData.checkIfElementIsDisplayed()
							&& dwellingPage.roofLastReplacedData.getData().contains("N/A"),
					true, "Dwelling Page",
					"Year Roof Last Replaced value is " + dwellingPage.roofLastReplacedData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			// Click on continue button
			dwellingPage.continueButton.scrollToElement();
			dwellingPage.continueButton.click();
			Assertions.passTest("Dwelling Page", "Clicked on continue button");
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}
			Assertions.passTest("Endorse Policy Page", "Clicked on next button and ok button");

			// Click on view endorsement document
			endorsePolicyPage.viewEndtQuoteButton.scrollToElement();
			endorsePolicyPage.viewEndtQuoteButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on view endorsement document");

			// Verifying presence of year roof last replaced, wording and value on endorse
			// quote document, when not added year roof last replaced and the year roof last
			// replaced value is same as year build
			Assertions.addInfo("Scenario 14",
					"Verifying presence of year roof last replaced, wording and value on endorse quote document, when added year roof last replaced and the year roof last replaced value is same as year built");
			Assertions.verify(
					quoteDetailsPage.yearRoofLastReplacedLabel.checkIfElementIsDisplayed()
							&& quoteDetailsPage.yearRoofLastReplacedLabel.getData().contains("Year Roof Last Replaced"),
					true, "Endorse Quote Details Page",
					"Year roof last replaced wording is " + quoteDetailsPage.yearRoofLastReplacedLabel.getData(), false,
					false);
			Assertions
					.verify(quoteDetailsPage.yearRoofLastReplacedValue.formatDynamicPath(1, 1, 2).getData()
							.substring(25, 29).contains(testData.get("L1B1-BldgYearBuilt")), true,
							"Endorse Quote Details Page",
							"Year Roof Last Replaced is " + quoteDetailsPage.yearRoofLastReplacedValue
									.formatDynamicPath(1, 1, 2).getData().substring(25, 29) + " displayed",
							false, false);
			Assertions.addInfo("Scenario 14", "Scenario 14 Ended");

			// Click on close button
			quoteDetailsPage.closeBtn.scrollToElement();
			quoteDetailsPage.closeBtn.click();

			// click on complete button and close button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button and close button");
			// Ended IO-22101,22093 and 22035

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC052 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC052 ", "Executed Successfully");
			}
		}
	}
}
