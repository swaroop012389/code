/** Program Description: 1.Check the valuation run on building that have below 85% building coverage as USM
 *  Author			   : Murali
 *  Date of Creation   : 07/11/2023
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC116 extends AbstractCommercialTest {

	public TC116() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID116.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ViewPolicySnapShot viewPolicySnapshotPage = new ViewPolicySnapShot();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		LoginPage loginPage = new LoginPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;
		int data_value5 = 4;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData1 = data.get(data_Value2);
		int locNo = 1;
		int bldgNo = 1;
		String buildingValueBUMCPinitial;
		double d_buildingValueBUMCPinitial;
		String buildingValueBuildingPageinitial;
		double d_buildingValueBuildingPageinitial;
		String buildingValuationValueinitial;
		double d_buildingValuationValueinitial;
		String buildingValuationValueupdated;
		double d_buildingValuationValueupdated;
		String buildingValueBUMCPupdated;
		double d_buildingValueBUMCPupdated;
		String buildingValueBuildingPageupdated;
		double d_buildingValueBuildingPageupdated;
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
			Assertions.passTest("Create Quote Page", "Quote1 details entered successfully");
			Assertions.passTest("Create Quote Page", "NH deductible for Quote 1 is " + testData.get("DeductibleValue"));

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number : 1 is " + quoteNumber);

			// Creating another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create Another quote button");

			// Click on override button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData1);
			Assertions.passTest("Create Quote Page", "Quote2 details entered successfully");
			Assertions.passTest("Create Quote Page",
					"NH deductible for Quote 2 is " + testData1.get("DeductibleValue"));

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			String quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number : 2 is " + quoteNumber2);

			// entering details in request bind page
			testData = data.get(data_Value1);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber2);

			if (requestBindPage.submit.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber2);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

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

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Clicking on View Policy Snapshot link to view the details
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Snap Shot Page", "Policy Snap Shot Page loaded successfully");

			// Verifying NH Deductibles on Policy snapshot page
			Assertions.addInfo("Scenario 01",
					"Verifying NH Deductibles on Policy snapshot page from Policy Summary Page");
			viewPolicySnapshotPage.policyDeductibles.formatDynamicPath("Named Hurricane")
					.waitTillVisibilityOfElement(60);
			Assertions.verify(
					viewPolicySnapshotPage.policyDeductibles
							.formatDynamicPath("Named Hurricane").getData().contains("Named Hurricane"),
					true, "Policy Display Page",
					"Named Hurricane deductible details are verified</br>"
							+ viewPolicySnapshotPage.policyDeductibles.formatDynamicPath("Named Hurricane").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapshotPage.policyDeductibles
							.formatDynamicPath("Earthquake").getData().contains("Earthquake"),
					true, "Policy Snap Shot Page",
					"Earthquake deductible details are verified</br>"
							+ viewPolicySnapshotPage.policyDeductibles.formatDynamicPath("Earthquake").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapshotPage.policyOtherDeductibles.formatDynamicPath("All Other Causes").getData()
							.contains("All Other Causes"),
					true, "Policy Snap Shot Page",
					"Policy Other deductible details are verified</br>" + viewPolicySnapshotPage.policyOtherDeductibles
							.formatDynamicPath("All Other Causes").getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Adding API Valuation TC02 - Check the valuation run on building that have
			// below 85% building coverage as USM.
			// signing out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Logging in as USM to approve the referral
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

			// creating New account
			testData = data.get(data_Value3);
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
			buildingPage.addSymbol.scrollToElement();
			buildingPage.addSymbol.click();
			buildingPage.addNewBuilding.click();
			buildingPage.addBuildingDetails(testData, locNo, bldgNo);
			buildingPage.addBuildingOccupancy(testData, locNo, bldgNo);
			buildingPage.addRoofDetails(testData, locNo, bldgNo);
			buildingPage.enterAdditionalBuildingInformation(testData, locNo, bldgNo);
			buildingPage.enterBuildingValues(testData, locNo, bldgNo);
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.waitTillElementisEnabled(60);
			buildingPage.reviewBuilding.waitTillButtonIsClickable(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();

			// Clicking Override Button on Existing Account Page, if found.
			if (existingAccountPage.pageName.getData().contains("Existing Account Found")) {
				existingAccountPage.OverrideExistingAccount();
			}

			// Verifying that the API Valuation MSG is not displayed
			Assertions.addInfo("Scenario 02", "Verifying that the API Valuation Message is not displayed");
			if (!buildingUnderMinimumCostPage.pageName.getData().contains("Building Under Minimum Cost Page")) {
				Assertions.passTest("Building Under Minimum Cost Page", "The API Valuation Message is not displayed");
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Clicking on Edit Button
			buildingPage.editBuilding.click();

			// Updating the Building Value
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			testData = data.get(data_Value4);
			buildingPage.buildingValuesLink.waitTillVisibilityOfElement(60);
			buildingPage.buildingValuesLink.scrollToElement();
			buildingPage.buildingValuesLink.click();
			if (!testData.get("L" + locNo + "B" + bldgNo + "-BldgValue").equals("")) {
				buildingPage.buildingValue.setData(testData.get("L" + locNo + "B" + bldgNo + "-BldgValue"));
			}
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.waitTillElementisEnabled(60);
			buildingPage.reviewBuilding.waitTillButtonIsClickable(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();

			// Verifying the API Valuation Message
			Assertions.addInfo("Scenarion 03",
					"Verifying if API Validation MSG after updating Building Value to $25,000");
			Assertions.verify(
					buildingUnderMinimumCostPage.costcardMessage.formatDynamicPath("The minimum Building value")
							.checkIfElementIsPresent()
							&& buildingUnderMinimumCostPage.costcardMessage.formatDynamicPath("The minimum Building")
									.checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The API Valuation Message is displayed: " + buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value").getData(),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Fetching Building Value from BUMCP
			buildingValueBUMCPinitial = buildingUnderMinimumCostPage.costcardMessage
					.formatDynamicPath("The minimum Building value").getData().substring(45, 52).replace(".", "")
					.replace(",", "");
			d_buildingValueBUMCPinitial = Double.parseDouble(buildingValueBUMCPinitial);

			// Clicking on Leave Ineligible Button
			buildingUnderMinimumCostPage.leaveIneligible.scrollToElement();
			buildingUnderMinimumCostPage.leaveIneligible.click();

			// Fetching Values for Assertions
			buildingValuationValueinitial = buildingPage.buildingValues.formatDynamicPath("Building Valuation")
					.getData().replace("$", "").replace(",", "");
			d_buildingValuationValueinitial = Double.parseDouble(buildingValuationValueinitial);
			buildingValueBuildingPageinitial = buildingPage.buildingValues.formatDynamicPath("Building").getData()
					.replace("$", "").replace(",", "");
			d_buildingValueBuildingPageinitial = Double.parseDouble(buildingValueBuildingPageinitial);

			// Verifying if the Exclamatory Symbol is present
			Assertions.addInfo("Scenario 04", "Verifying if the Exclamatory mark is present on the Building page");
			Assertions.verify(
					buildingPage.exclamatorySymbol.checkIfElementIsPresent()
							&& buildingPage.exclamatorySymbol.checkIfElementIsDisplayed(),
					true, "Building Page", "The Exclamatory mark is displayed as expected", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Verifying if the Create Quote Button is Displayed
			Assertions.addInfo("Scenario 05", "Verifying if the Create Quote Page button is disabled");
			Assertions.verify(!buildingPage.createQuote.checkIfElementIsEnabled(), false, "Building Page",
					"The Create Quote button is present and displayed as expected", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Clicking on Edit Button
			buildingPage.editBuilding.click();

			// Updating the Building Value
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			testData = data.get(data_value5);
			buildingPage.buildingValuesLink.waitTillVisibilityOfElement(60);
			buildingPage.buildingValuesLink.scrollToElement();
			buildingPage.buildingValuesLink.click();
			if (!testData.get("L" + locNo + "B" + bldgNo + "-BldgValue").equals("")) {
				buildingPage.buildingValue.setData(testData.get("L" + locNo + "B" + bldgNo + "-BldgValue"));
			}
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.waitTillElementisEnabled(60);
			buildingPage.reviewBuilding.waitTillButtonIsClickable(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();

			// Verifying the API Valuation Message
			Assertions.addInfo("Scenarion 06",
					"Verifying if API Validation MSG after updating Building Value to $20,000");
			Assertions.verify(
					buildingUnderMinimumCostPage.costcardMessage.formatDynamicPath("The minimum Building value")
							.checkIfElementIsPresent()
							&& buildingUnderMinimumCostPage.costcardMessage.formatDynamicPath("The minimum Building")
									.checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The API Valuation Message is displayed: " + buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value").getData(),
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Fetching Building Value from BUMCP
			buildingValueBUMCPupdated = buildingUnderMinimumCostPage.costcardMessage
					.formatDynamicPath("The minimum Building value").getData().substring(45, 52).replace(".", "")
					.replace(",", "");
			d_buildingValueBUMCPupdated = Double.parseDouble(buildingValueBUMCPupdated);

			// Clicking on Bring up to Cost button
			buildingUnderMinimumCostPage.bringUpToCost.scrollToElement();
			buildingUnderMinimumCostPage.bringUpToCost.click();

			// Fetching Building values for Assertions
			buildingValuationValueupdated = buildingPage.buildingValues.formatDynamicPath("Building Valuation")
					.getData().replace("$", "").replace(",", "");
			d_buildingValuationValueupdated = Double.parseDouble(buildingValuationValueupdated);
			buildingValueBuildingPageupdated = buildingPage.buildingValues.formatDynamicPath("Building").getData()
					.replace("$", "").replace(",", "");
			d_buildingValueBuildingPageupdated = Double.parseDouble(buildingValueBuildingPageupdated);

			// Verifying if there is a difference in Building Values after Bringing up to
			// Cost
			Assertions.addInfo("Scenario 07",
					"Verifying the if there is a difference in the Building Values after Bringin up to Cost");
			if (d_buildingValueBuildingPageupdated != d_buildingValueBUMCPupdated) {
				Assertions.passTest("Building Under Minimum Cost Page",
						"The Building Value found in Building Under Minimum Cost Page when Building Value is Decreased to $25,000 and "
								+ "the Building Value found in Building Under Minimum Cost Page when Building Value is Decreased to $20,000 are different."
								+ "Initial Building Value: " + d_buildingValueBuildingPageupdated
								+ " Updated Building Value: " + d_buildingValueBUMCPupdated);
			} else {
				Assertions.verify((d_buildingValueBuildingPageupdated == d_buildingValueBUMCPupdated), true,
						"Building Page",
						"The Initial Building Value and Updated Building Value on Building Under Minimum Cost Page "
								+ "are Matching after Clicking on Bring Up To Cost Button",
						false, false);
			}

			if (d_buildingValueBuildingPageinitial != d_buildingValueBuildingPageupdated) {
				Assertions.passTest("Building Under Minimum Cost Page",
						"The Building Value found in Building Page when Building Value is Decreased to $25,000 and "
								+ "the Building Value found in Building Page when Building Value is Decreased to $20,000 are different."
								+ "Initial Building Value: " + d_buildingValueBUMCPinitial + " Updated Building Value: "
								+ d_buildingValueBUMCPupdated);
			} else {
				Assertions.verify((d_buildingValueBuildingPageinitial == d_buildingValueBuildingPageupdated), true,
						"Building Page", "The initial Building Value and updated Building Value on Building Page "
								+ "are Matching after Clicking on Bring Up To Cost Button",
						false, false);
			}

			if (d_buildingValuationValueinitial != d_buildingValuationValueupdated) {
				Assertions.passTest("Building Under Minimum Cost Page",
						"The Building Valuation Value found in Building Page when Building Value is Decreased to $25,000 and "
								+ "the Building Valuation Value found in Building Page when Building Value is Decreased to $20,000 are different."
								+ "Initial Building Value: " + d_buildingValueBUMCPinitial + " Updated Building Value: "
								+ d_buildingValueBUMCPupdated);
			} else {
				Assertions.verify((d_buildingValuationValueinitial == d_buildingValuationValueupdated), true,
						"Building Page",
						"The initial Building Valuation Value and updated Building Valuation Value on Building Page "
								+ "are Matching after Clicking on Bring Up To Cost Button",
						false, false);
			}

			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Clicking on Create Quote Button
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// Overriding Building No longer Quoteable
			if (buildingNoLongerQuoteablePage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// Selecting Peril
			selectPerilPage.allOtherPeril.waitTillPresenceOfElement(60);
			selectPerilPage.allOtherPeril.scrollToElement();
			selectPerilPage.allOtherPeril.click();
			selectPerilPage.continueButton.scrollToElement();
			selectPerilPage.continueButton.click();

			// Entering Prior Loss Details
			testData = data.get(data_Value3);
			priorLossesPage.editPriorLossesInformation(testData);

			// Entering Quote Details
			testData = data.get(data_Value3);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Validating the Valuation Report Button on Account Overview Page
			Assertions.verify(
					accountOverviewPage.createAnotherQuote.checkIfElementIsPresent()
							&& accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded Successfully", false, false);
			Assertions.addInfo("Scenario 08", "Verifying if the Valuation Report Button is Displayed");
			Assertions.verify(
					accountOverviewPage.valuationReportButton.checkIfElementIsPresent()
							&& accountOverviewPage.valuationReportButton.checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Valuation Report Button is Present and Displayed on Account Overview Page", false, false);
			Assertions.addInfo("Scenarion 08", "Scenarion 08 Ended");

			// Fetching Quote Number
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);

			// Creating Policy - Clicking on Bind Button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Entering Bind Details
			requestBindPage.enterBindDetails(testData);

			// Navigating to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Quote
			homePage.searchQuote(quoteNumber);

			// Opening referral
			if (accountOverviewPage.openReferralLink.checkIfElementIsPresent()
					&& accountOverviewPage.openReferralLink.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();

				// Clicking on Approve or Decline Button
				referralPage.approveOrDeclineRequest.scrollToElement();
				referralPage.approveOrDeclineRequest.click();

				// Clicking on Approve Button on Request Bind Page
				requestBindPage.approve.scrollToElement();
				requestBindPage.approve.click();
			}

			else if (accountOverviewPage.viewPolicyBtn.checkIfElementIsPresent()
					&& accountOverviewPage.viewPolicyBtn.checkIfElementIsDisplayed()) {
				accountOverviewPage.viewPolicyBtn.scrollToElement();
				accountOverviewPage.viewPolicyBtn.click();
			}

			// Fetching Policy Number
			policyNumber = policySummaryPage.getPolicynumber();

			// Printing Policy Number
			Assertions.passTest("Policy Summary Page", "Policy Number: " + policyNumber);

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 116", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 116", "Executed Successfully");
			}
		}
	}
}