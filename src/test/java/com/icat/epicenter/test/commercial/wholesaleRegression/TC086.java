/** Program Description: 1.Create Aop policy->change roof cladding,Roof age,construction year and Building value assert the referral messages and added IO-21031
 *                       2.API Valuation - Check if the valuation will be run on Endorsements [Sowndarya 11/07/2023]
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/28/2021
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
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC086 extends AbstractCommercialTest {

	public TC086() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID086.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int locNo = 1;
		int bldgNoone = 1;
		int bldgNotwo = 2;
		int quoteLength;
		testData = data.get(dataValue1);
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
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();
			buildingPage.addBuildingDetails(testData, locNo, bldgNoone);
			buildingPage.addBuildingOccupancy(testData, locNo, bldgNoone);
			buildingPage.addRoofDetails(testData, locNo, bldgNoone);
			buildingPage.enterAdditionalBuildingInformation(testData, locNo, bldgNoone);
			buildingPage.enterBuildingValues(testData, locNo, bldgNoone);
			Assertions.passTest("Building Page", "Building Value entered is " + buildingPage.buildingValue.getData());
			Assertions.passTest("Building Page",
					"BPP Value entered is " + buildingPage.businessPersonalProperty.getData());

			// Click on review building
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Review Building");

			// Verify the absence of building valuation row
			Assertions.addInfo("Scenario 01",
					"Verify the absence of Building valuation row when only BPP value is provided");
			Assertions.verify(
					buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation").checkIfElementIsPresent(),
					false, "Building Page", "Building Valuation is not displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on override
			if (buildingPage.pageName.getData().contains("Existing Account Found")) {
				existingAccountPage.OverrideExistingAccount();
			}

			// Click on create quote button
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			Assertions.passTest("Building Page", "Clicked on Create Quote button");

			// Click on override
			if (buildingPage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
			}

			// Selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Enter quote details
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLength = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, quoteLength - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is " + quoteNumber);

			// Entering details in request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// Click on open referral link
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

			// Adding the ticket IO-21031
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				Assertions.addInfo("Scenario 02", "Asserting carrier warning message when TIV is greater than 5M");
				Assertions.verify(requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed(), true,
						"Request Bind Page", "Carrier warning message " + requestBindPage.carrierWarningMsg.getData()
								+ " is displayed when the TIV is greater than 5M",
						false, false);
				Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

				// carrier selection
				if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
						&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
					requestBindPage.approveRequestCommercialData(testData);
				} else {
					requestBindPage.approveRequest();
				}
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			}

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.verify(policySummarypage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Find the policy by entering policy Number
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// Endorse policy
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to add the building value");
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered the Endorsement Effective Date");

			// Clicking on edit Location/Building information hyperlink
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building Information link");

			// Clicking on Building link
			testData = data.get(dataValue3);
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();

			// Modifying Building value
			Assertions.passTest("Building Page", "Building Page loaded successfully");
			buildingPage.editBuildingValuesPNB(testData, dataValue2, dataValue2);

			// Click on Review building
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Review Building");

			// Click on Override
			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}

			// Verify the presence of building valuation row
			Assertions.addInfo("Scenario 02",
					"Verify the presence of Building valuation row and asserting the Building valuation value when Building value is provided as part of endorsement");
			Assertions.verify(
					buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation")
							.checkIfElementIsDisplayed(),
					true, "Building Page", "Building Valuation is displayed", false, false);
			Assertions.verify(
					buildingPage.buildingValues.formatDynamicPath("Building Valuation").checkIfElementIsDisplayed(),
					true, "Building Page",
					"Building Valuation Value "
							+ buildingPage.buildingValues.formatDynamicPath("Building Valuation").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on Continue button
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();
			Assertions.passTest("Building Page", "Clicked on Continue Button");

			// Click on continue endorsement button
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue endorsement button");

			// Click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on next button");

			// Click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
				Assertions.passTest("Endorse Policy Page", "Clicked on Continue button");
			}

			// Click on Complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete button");

			// Click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Endorse policy
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to add another building to Location1");
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Setting Endorsement Effective Date
			testData = data.get(dataValue1);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered the Endorsement Effective Date");

			// Clicking on edit Location/Building information hyperlink
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building Information link");

			// Modifying Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();

			// Click on add symbol
			buildingPage.addSymbol.scrollToElement();
			buildingPage.addSymbol.click();
			Assertions.passTest("Building Page", "Clicked on Add Symbol");

			// Click on Add New building
			buildingPage.addNewBuilding.scrollToElement();
			buildingPage.addNewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Add Building link");

			// Modifying Location 1 Building 1 Details
			Assertions.passTest("Building Page", "Building Page loaded successfully");
			buildingPage.addBuildingDetails(testData, locNo, bldgNotwo);
			buildingPage.addBuildingOccupancy(testData, locNo, bldgNotwo);
			buildingPage.addRoofDetails(testData, locNo, bldgNotwo);
			buildingPage.enterAdditionalBuildingInformation(testData, locNo, bldgNotwo);
			buildingPage.enterBuildingValues(testData, locNo, bldgNotwo);
			Assertions.passTest("Building Page",
					"BPP Value entered is " + buildingPage.businessPersonalProperty.getData());
			Assertions.passTest("Building Page", "Building Value entered is " + buildingPage.buildingValue.getData());

			// Click on review building
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Review Building");

			// Click on override
			if (buildingPage.pageName.getData().contains("Existing Account Found")) {
				existingAccountPage = new ExistingAccountPage();
				existingAccountPage.OverrideExistingAccount();
			}

			// Click on Continue button
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();
			Assertions.passTest("Building Page", "Clicked on Continue Button");

			// Click on continue endorsement button
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue endorsement button");

			// Click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on next button");

			// Click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
				Assertions.passTest("Endorse Policy Page", "Clicked on Continue button");
			}

			// Click on Complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete button");

			// Click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Verify the absence of building valuation row
			Assertions.addInfo("Scenario 03",
					"Verify the absence of Building valuation row when only BPP is provided as part of Endorsement");
			Assertions.verify(
					buildingPage.buildingValuationRow.formatDynamicPath("Building Valuation").checkIfElementIsPresent(),
					false, "Building Page", "Building Valuation not is displayed", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Endorse policy
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to change building details");
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Setting Endorsement Effective Date
			testData = data.get(dataValue1);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered the Endorsement Effective Date");

			// Clicking on edit Location/Building information hyperlink
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building Information link");

			// Modifying Location Details
			testData = data.get(dataValue2);
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);

			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();

			// modifying Location 1 Building 1 Details
			Assertions.passTest("Building Page", "Building Page loaded successfully");

			// Modifying roof details
			Assertions.addInfo("Building Page", "Modifying Roof Details(Roof Age = Older Than 25 Years)");
			buildingPage.editRoofDetailsPNB(testData, dataValue2, dataValue2);

			// Click on review building
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Review Building");

			// Asserting the Warning message
			Assertions.addInfo("Scenario 04", "Asserting Warning message when the roof age is Older Than 25 Years");
			Assertions.verify(buildingPage.roofAgeWarningMessage.checkIfElementIsDisplayed(), true, "Building Page",
					"The Warning message " + buildingPage.roofAgeWarningMessage.getData(), false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on Override
			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}

			// Click on edit building
			buildingPage.editBuilding.scrollToElement();
			buildingPage.editBuilding.click();

			// Modifying Building Details and building values
			Assertions.addInfo("Building Page", "Modifying the Year Built and Building Values");
			Assertions.addInfo("Building Page", "Year Built original Value : " + buildingPage.yearBuilt.getData());
			buildingPage.yearBuilt.clearData();
			buildingPage.yearBuilt.setData(testData.get("L1B1-BldgYearBuilt"));
			Assertions.passTest("Building Page", "Year Built Latest Value : " + buildingPage.yearBuilt.getData());
			buildingPage.editBuildingValuesPNB(testData, dataValue2, dataValue2);
			buildingPage.reviewBuilding();
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();
			Assertions.passTest("Building Page", "Clicked on Continue Button");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);

			// click on continue endorsement button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			// added IO-21504
			// Click on Home button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Clicked on Home button successfully");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);

			// Search policy through Find section
			Assertions.addInfo("HomePage",
					"Searching the policy through Find Section and verifying the Policy Number format");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterPolicyOption.scrollToElement();
			homePage.findFilterPolicyOption.click();
			homePage.policyStateArrow.scrollToElement();
			homePage.policyStateArrow.click();
			homePage.policyStateOption.formatDynamicPath("MS").waitTillPresenceOfElement(60);
			homePage.policyStateOption.formatDynamicPath("MS").waitTillVisibilityOfElement(60);
			homePage.policyStateOption.formatDynamicPath("MS").scrollToElement();
			homePage.policyStateOption.formatDynamicPath("MS").click();
			homePage.businessTypePolicyArrow.scrollToElement();
			homePage.businessTypePolicyArrow.click();
			homePage.businessTypePolicyOption.formatDynamicPath("Commercial Wholesale").scrollToElement();
			homePage.businessTypePolicyOption.formatDynamicPath("Commercial Wholesale").click();
			homePage.findPolicyButton.click();
			Assertions.passTest("Home page", "Clicked on findbutton successfully");

			// Clicked on result status
			homePage.resultStatus.waitTillPresenceOfElement(60);
			homePage.resultStatus.scrollToElement();
			homePage.resultStatus.click();
			Assertions.passTest("Home page", "Clicked on result status link successfully");

			// Click on the policy from results
			Assertions.verify(homePage.resultTable.formatDynamicPath("Active", 1).checkIfElementIsDisplayed(), true,
					"Home Page", "Clicked on Find Policy button and result table loaded successfully", false, false);
			String policyNumberResultTable = homePage.resultTable.formatDynamicPath("Active", 1).getData();
			Assertions.passTest("Home Page", "Policy Number From Find Result Table: " + policyNumberResultTable);
			homePage.resultTable.formatDynamicPath("Active", 1).scrollToElement();
			homePage.resultTable.formatDynamicPath("Active", 1).click();
			Assertions.passTest("Home Page", "Searched Policy through Find Section successfully");
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);

			// Endorse policy
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to add new building");
			policySummarypage.waitTime(5);
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			if (endorsePolicyPage.okButton.checkIfElementIsPresent()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
				if (policySummarypage.endorsePB.checkIfElementIsPresent()) {
					policySummarypage.endorsePB.scrollToElement();
					policySummarypage.endorsePB.click();
				}
			}
			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

			// Setting Endorsement Effective Date
			testData = data.get(dataValue1);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered the Endorsement Effective Date");

			// Clicking on edit Location/Building information hyperlink
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building Information link");

			// adding a new building on the pre-existing policy
			locationPage.addSymbol.checkIfElementIsPresent();
			locationPage.addSymbol.scrollToElement();
			locationPage.addSymbol.click();

			locationPage.addNewBuilding.checkIfElementIsPresent();
			locationPage.addNewBuilding.scrollToElement();
			locationPage.addNewBuilding.click();

			buildingPage.manualEntry.click();
			buildingPage.manualEntryAddress.waitTillVisibilityOfElement(60);
			Assertions.verify(buildingPage.manualEntryAddress.checkIfElementIsPresent(), true, "Building Page",
					"New building created successfully on a pre-existing policy", false, false);
			// Ticket IO-21504 Ended

			// Signout and close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 86", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 86", "Executed Successfully");
			}
		}
	}
}
