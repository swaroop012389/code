/** Program Description: 1. Create multiple AOP quotes with different NS values and bind the last quote. Assert the relevant data on policysnapshot
 *  					 2. Adding for Ticket IO-20460 - External requested cancellations are throwing stack trace error [Abha]
 *  				     3. API Valuation - Checking if the Building valuation value and Building Value changes after updating the Sq. Ft and Checking
                             if the Valuation run on buildings that have building coverage as USM [Murali 11/07/2023]
 *  Author			   : Abha
 *  Date of Creation   : 11/26/2019
 **/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RequestCancellationPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC010 extends AbstractCommercialTest {

	public TC010() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID010.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		GLInformationPage gLInfoPage = new GLInformationPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ViewPolicySnapShot viewPolicySnapshotPage = new ViewPolicySnapShot();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();
		LoginPage loginPage = new LoginPage();
		RequestCancellationPage requestCancellationPage = new RequestCancellationPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;
		int data_Value5 = 4;
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
		double calcBuildingValuationValueupdated;
		double calcBuildingValueupdated;
		double apiPercentage = 0.85;
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

			// Entering GL Information
			gLInfoPage.enterGLInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote1 details entered successfully");
			Assertions.passTest("Create Quote Page", "NS deductible for Quote 1 is " + testData.get("DeductibleValue"));

			// Create another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create Another Quote");

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

			// Enter Q2 details
			createQuotePage.enterDeductiblesCommercialNew(testData1);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote2 details entered successfully");
			Assertions.passTest("Create Quote Page",
					"NS deductible for Quote 2 is " + testData1.get("DeductibleValue"));

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Clicking on View Policy Snapshot link to view the details
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Snap Shot Page", "Policy Snap Shot Page loaded successfully");

			// Verifying quote details on Policy Snapshot page
			Assertions.addInfo("Scenario 01", "Verifying deductible details on Policy Snapshot page");
			viewPolicySnapshotPage.policyDeductibles.formatDynamicPath("Named Storm").waitTillVisibilityOfElement(60);
			Assertions.verify(
					viewPolicySnapshotPage.policyDeductibles
							.formatDynamicPath("Named Storm").getData().contains(testData1.get("DeductibleValue")),
					true, "Policy Snap Shot Page",
					"Named Storm deductible details are verified</br>"
							+ viewPolicySnapshotPage.policyDeductibles.formatDynamicPath("Named Storm").getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			viewPolicySnapshotPage.goBackButton.scrollToElement();
			viewPolicySnapshotPage.goBackButton.click();
			Assertions.passTest("Policy Snap Shot Page", "Clicked on back button successfully");

			// Adding below code for IO-20221
			// Click on NPB link
			testData = data.get(data_Value1);
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();

			// Changing named insured
			Assertions.verify(changeNamedInsuredPage.okButton.checkIfElementIsDisplayed(), true,
					"Chnage Named Insured Page", "Chnage Named Insured Page loaded successfully", false, false);
			testData1 = data.get(data_Value2);
			changeNamedInsuredPage.namedInsured.scrollToElement();
			changeNamedInsuredPage.namedInsured.clearData();
			changeNamedInsuredPage.namedInsured.appendData(testData1.get("InsuredName"));
			changeNamedInsuredPage.namedInsured.tab();
			changeNamedInsuredPage.okButton.scrollToElement();
			changeNamedInsuredPage.okButton.click();
			changeNamedInsuredPage.no_NameChange.scrollToElement();
			changeNamedInsuredPage.no_NameChange.click();

			// Verifying presence of complete button or endorse policy page
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Stack trace is not comming after Click on No button on name change detected page", false, false);

			// sign out and close the browser
			Assertions.passTest("Commercial NBRegression Test Case 10", "Executed Successfully");
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sig in in as External User
			// Adding for Ticket IO-20460 - External requested cancellations are throwing
			// stack trace error
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));

			// Search Policy Number
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.passTest("Login Page", "Logged in as External User successfully");

			// Click on Request Cancellation Link
			policySummaryPage.requestCancellationLink.scrollToElement();
			policySummaryPage.requestCancellationLink.click();

			// Adding for Ticket IO-20460 - External requested cancellations are throwing
			// stack trace error
			// Enter the details for Cancellation
			Assertions.addInfo("Scenario 02",
					"IO-20460 - External requested cancellations are throwing stack trace error on Request Cancellation Page");
			requestCancellationPage.enterRequestCancellationDetails(testData);
			Assertions.verify(requestCancellationPage.cancellationRequestMsg.checkIfElementIsDisplayed(), true,
					"Request Cancellation Page", "Request cancellation Message is : "
							+ requestCancellationPage.cancellationRequestMsg.getData() + " displayed and verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Navigating to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Adding API Valuation TC01 - Checking if the valuation run on buildings that
			// have building coverage as USM
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

			// Verifying API Valuation MSG on Building Under Minimum Cost Page
			Assertions.addInfo("Scenarion 03",
					"Verifying if API Validation MSG, Bring Up Cost, Override and Leave Ineligible Buttons are displayed");
			Assertions.verify(
					buildingUnderMinimumCostPage.costcardMessage.formatDynamicPath("The minimum Building value")
							.checkIfElementIsPresent()
							&& buildingUnderMinimumCostPage.costcardMessage
									.formatDynamicPath("The minimum Building value").checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The API Valuation Message is displayed: " + buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value").getData(),
					false, false);
			Assertions.verify(
					buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsPresent()
							&& buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page", "Bring up Cost Button is present and displayed", false,
					false);
			Assertions.verify(
					buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
							&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page", "Override Button is present and displayed", false, false);
			Assertions.verify(
					buildingUnderMinimumCostPage.leaveIneligible.checkIfElementIsPresent()
							&& buildingUnderMinimumCostPage.leaveIneligible.checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page", "Leave Ineligible Button is present and displayed", false,
					false);
			Assertions.addInfo("Scenarion 03", "Scenarion 03 Ended");

			// Fetching Building Value Displayed on Building Under Minimum Cost Page
			buildingValueBUMCPinitial = buildingUnderMinimumCostPage.costcardMessage
					.formatDynamicPath("The minimum Building value").getData().substring(45, 52).replace(".", "")
					.replace(",", "");
			d_buildingValueBUMCPinitial = Double.parseDouble(buildingValueBUMCPinitial);

			// Clicking on Override button
			buildingUnderMinimumCostPage.clickOnOverride();

			// Fetching Values for the Assertion of Building Values
			buildingValueBuildingPageinitial = buildingPage.buildingValues.formatDynamicPath("Building").getData()
					.replace("$", "").replace(",", "");
			d_buildingValueBuildingPageinitial = Double.parseDouble(buildingValueBuildingPageinitial);
			buildingValuationValueinitial = buildingPage.buildingValues.formatDynamicPath("Building Valuation")
					.getData().replace("$", "").replace(",", "");
			d_buildingValuationValueinitial = Double.parseDouble(buildingValuationValueinitial);

			// Asserting Building Values on Building Under Minimum Cost Page and Building
			// Page are same
			Assertions.addInfo("Scenarion 04",
					"Verifying if Building value on Building Page is same as the initial value after clicking on override button");
			Assertions.verify(d_buildingValueBuildingPageinitial,
					Double.parseDouble(testData.get("L1B1-BldgValue").replace(",", "")), "Building Page",
					"The Building value on Building Page is same as the initial value, after clicking on override button",
					false, false);
			Assertions.addInfo("Scenarion 04", "Scenarion 04 Ended");

			// Clicking on Edit Icon on Building Page
			buildingPage.editBuilding.scrollToElement();
			buildingPage.editBuilding.click();

			// Updating SQ.Ft on Building Page
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			testData = data.get(data_Value4);
			buildingPage.totalSquareFootage.setData(testData.get("L" + locNo + "B" + bldgNo + "-BldgSqFeet"));
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.waitTillElementisEnabled(60);
			buildingPage.reviewBuilding.waitTillButtonIsClickable(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();

			// Verifying API Valuation MSG on Building Under Minimum Cost Page
			Assertions.addInfo("Scenarion 05",
					"Verifying if API Validation MSG, Bring Up Cost, Override and Leave Ineligible Buttons are displayed");
			Assertions.verify(
					buildingUnderMinimumCostPage.costcardMessage.formatDynamicPath("The minimum Building value")
							.checkIfElementIsPresent()
							&& buildingUnderMinimumCostPage.costcardMessage.formatDynamicPath("The minimum Building")
									.checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page",
					"The API Valuation Message is displayed: " + buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value").getData(),
					false, false);
			Assertions.verify(
					buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsPresent()
							&& buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page", "Bring up Cost Button is present and displayed", false,
					false);
			Assertions.verify(
					buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
							&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page", "Override Button is present and displayed", false, false);
			Assertions.verify(
					buildingUnderMinimumCostPage.leaveIneligible.checkIfElementIsPresent()
							&& buildingUnderMinimumCostPage.leaveIneligible.checkIfElementIsDisplayed(),
					true, "Building Under Minimum Cost Page", "Leave Ineligible Button is present and displayed", false,
					false);
			Assertions.addInfo("Scenarion 05", "Scenarion 05 Ended");
			buildingValueBUMCPupdated = buildingUnderMinimumCostPage.costcardMessage
					.formatDynamicPath("The minimum Building value").getData().substring(45, 52).replace(".", "")
					.replace(",", "");
			d_buildingValueBUMCPupdated = Double.parseDouble(buildingValueBUMCPupdated);

			// Clicking on Bring up to Cost Button
			buildingUnderMinimumCostPage.bringUpToCost.scrollToElement();
			buildingUnderMinimumCostPage.bringUpToCost.click();

			// Fetching Values for Asserting Building Values on Building Under Minimum Cost
			// Page and Building
			// Page are same
			buildingValueBuildingPageupdated = buildingPage.buildingValues.formatDynamicPath("Building").getData()
					.replace("$", "").replace(",", "");
			d_buildingValueBuildingPageupdated = Double.parseDouble(buildingValueBuildingPageupdated);
			buildingValuationValueupdated = buildingPage.buildingValues.formatDynamicPath("Building Valuation")
					.getData().replace("$", "").replace(",", "");
			d_buildingValuationValueupdated = Double.parseDouble(buildingValuationValueupdated);
			calcBuildingValuationValueupdated = Precision
					.round((Precision.round(d_buildingValueBuildingPageupdated, 2) / apiPercentage), 2);
			calcBuildingValueupdated = Precision
					.round((Precision.round(d_buildingValuationValueupdated, 2) * apiPercentage), 2);

			// Asserting Building Values on Building Under Minimum Cost Page and Building
			// Page are same
			Assertions.addInfo("Scenarion 06",
					"Verifying if Building values on Building Under Minimum Cost Page and Building Page are different from the intial value, "
							+ " after changing Sq. Ft value, when bring upto cost is selected");
			Assertions.verify(d_buildingValueBuildingPageupdated, d_buildingValueBUMCPupdated, "Building Page",
					"The Building Value on Building Under Minimum Cost Page and Building Page are same after clicking on Bring up cost and different from the initial value",
					false, false);
			if (d_buildingValueBuildingPageinitial != d_buildingValueBuildingPageupdated) {
				Assertions.passTest("Building Page",
						"The Initial Building Value and Updated Building Value are not Matching after Clicking on Bring Up To Cost Button as Expected"
								+ "Initial Building Value: " + d_buildingValueBuildingPageinitial
								+ "and the Updated Building Value: " + d_buildingValueBuildingPageupdated);
			} else {
				Assertions.verify((d_buildingValueBuildingPageinitial == d_buildingValueBuildingPageupdated), false,
						"Building Page",
						"The Initial Building Value and Updated Building Value are Matching after Clicking on Bring Up To Cost Button",
						false, false);
			}
			if (d_buildingValuationValueinitial != d_buildingValuationValueupdated) {
				Assertions.passTest("Building Page",
						"The Initial Building Valuation Value and Updated Building Valuation Value are not Matching after Clicking on Bring Up To Cost Button as Expected"
								+ "Initial Building Value: " + d_buildingValuationValueinitial
								+ " and the Updated Building Value: " + d_buildingValuationValueupdated);
			} else {
				Assertions.verify((d_buildingValuationValueinitial == d_buildingValuationValueupdated), false,
						"Building Page",
						"The Initial Building Valuation Value and Updated Building Valuation Value are Matching after Updating the Total Sq.Ft Value as expected",
						false, false);
			}
			if (d_buildingValueBUMCPinitial != d_buildingValueBUMCPupdated) {
				Assertions.passTest("Building Page",
						"The Initial Building Value on Building Under Minimum Cost Page and Updated Building Value on Building Under Minimum Cost "
								+ "Page are not Matching after Clicking on Bring Up To Cost Button as Expected"
								+ "Initial Building Value on Building Under Minimum Cost Page: "
								+ d_buildingValueBUMCPinitial
								+ " and the Updated Building Value on Building Under Minimum Cost Page: "
								+ d_buildingValueBUMCPupdated);
			} else {
				Assertions.verify((d_buildingValueBUMCPinitial == d_buildingValueBUMCPupdated), false, "Building Page",
						"The Initial Building Valuation Value on Building Under Minimum Cost Page and Updated Building Valuation Value on Building Under Minimum Cost Page are Matching after Updating the Total Sq.Ft Value",
						false, false);
			}
			Assertions.addInfo("Scenarion 06", "Scenarion 06 Ended");

			// Asserting Building Valuation Value and Build Value against the Calculated
			// Value after Overriding
			Assertions.addInfo("Scenario 07",
					"Verifying if Calculated Building and Building Valuation Value are matching with "
							+ "Actual Building Valuation Value and Actual Building Value");
			if (Precision.round(Math.abs(Precision.round(calcBuildingValuationValueupdated, 2)
					- Precision.round(d_buildingValuationValueupdated, 2)), 2) < 1.50) {
				Assertions.passTest("Building Page",
						"The Calculated and Actual Building Valuation Values are Matching after Bringing Up The Cost");
				Assertions.passTest("Building Page", "The Calculated Building Valuation Value: "
						+ Precision.round(calcBuildingValuationValueupdated, 2));
				Assertions.passTest("Building Page",
						"The Actual Building Valuation Value: " + Precision.round(d_buildingValuationValueupdated, 2));
			} else {
				Assertions.verify(Precision.round(d_buildingValuationValueupdated, 2),
						Precision.round(calcBuildingValuationValueupdated, 2), "Building Page",
						"The Calculated and Actual Building Valuation Values are not matching after Bringing Up The Cost",
						false, false);
			}

			if (Precision.round(Math.abs(Precision.round(calcBuildingValueupdated, 2)
					- Precision.round(d_buildingValueBuildingPageupdated, 2)), 2) < 1.50) {
				Assertions.passTest("Building Page",
						"The Calculated and Actual Building Values are Matching after Bringing Up The Cost");
				Assertions.passTest("Building Page",
						"The Calculated Building Valuation Value: " + Precision.round(calcBuildingValueupdated, 2));
				Assertions.passTest("Building Page", "The Actual Building Valuation Value: "
						+ Precision.round(d_buildingValueBuildingPageupdated, 2));
			} else {
				Assertions.verify(Precision.round(d_buildingValueBuildingPageupdated, 2),
						Precision.round(calcBuildingValueupdated, 2), "Building Page",
						"The Calculated and Actual Building Valuation Values are not matching after Bringing Up The Cost",
						false, false);
			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Navigating to Create Quote Page
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// Overriding Building No longer Quote able
			if (buildingNoLongerQuoteablePage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// Selecting Peril
			selectPerilPage.windOnly.waitTillPresenceOfElement(60);
			selectPerilPage.windOnly.scrollToElement();
			selectPerilPage.windOnly.click();
			selectPerilPage.continueButton.scrollToElement();
			selectPerilPage.continueButton.click();

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
			Assertions.addInfo("Scenario 08", "Verifying if the Valuation Report Button is displayed");
			Assertions.verify(
					accountOverviewPage.valuationReportButton.checkIfElementIsPresent()
							&& accountOverviewPage.valuationReportButton.checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Valuation Report Button is Present and Displayed on Account Overview Page", false, false);
			Assertions.addInfo("Scenarion 08", "Scenarion 08 Ended");

			// signing out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Logging in as USM to approve the referral
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

			// creating New account
			testData = data.get(data_Value5);
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

			// Asserting the API MSG is not displayed when the Building Value is not entered
			Assertions.addInfo("Scenarion 09",
					"Verifying if Building Valuation Value is not Displayed when Building Value is not entered");
			Assertions.verify(
					!buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation")
							.checkIfElementIsPresent(),
					true, "Building Page",
					"The Building Valuation Value is not Displayed when Building Value is not entered", false, false);
			Assertions.addInfo("Scenarion 09", "Scenario 09 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 10", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 10", "Executed Successfully");
			}
		}
	}
}