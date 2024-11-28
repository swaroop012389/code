/** Program Description: To check if protection class 8 and added IO-21621 and Added IO-21439
 *  Author			   : John
 *  Date of Creation   : 11/12/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

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
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC028 extends AbstractCommercialTest {

	public TC028() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID028.xls";
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
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		LoginPage loginPage = new LoginPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		Map<String, String> testData;
		int dataValue1 = 0;
		String protClassErrMsg;
		int qtlngth;
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
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();
			buildingPage.addBuildingDetails(testData, 1, 1);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, 1, 1);
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.waitTillElementisEnabled(60);
			buildingPage.reviewBuilding.waitTillButtonIsClickable(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building page", "Building details entered successfully");

			// Asserting the entered Building stories and Protection class
			Assertions.passTest("Building Page", "The Building Stories is : " + buildingPage.numOfStories.getData());
			Assertions.passTest("Building Page", "The protection class is : " + buildingPage.protectionClass.getData());

			// Asserting protection class and Building stories error message
			Assertions.addInfo("Scenario 01",
					"Asserting error message when  protection class =8 and Assert warning message when Building stories=7");
			protClassErrMsg = buildingPage.protectionClassErrMsg.getData();
			Assertions.verify(protClassErrMsg.contains(testData.get("ProtectionClassWarningMessage")), true,
					"Building Page", "The Error message for protection class is " + protClassErrMsg, false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on additional info link
			buildingPage.additionalInfoLink.scrollToElement();
			buildingPage.additionalInfoLink.click();
			buildingPage.waitTime(2);// if the wait time is removed test case will fail here
			buildingPage.protectionClass.scrollToElement();
			buildingPage.protectionClass.setData(testData.get("ProtectionClassNew"));
			Assertions.passTest("Building Page",
					"Changed the Protection class to " + testData.get("ProtectionClassNew"));

			// click on override
			buildingPage.override.scrollToElement();
			buildingPage.override.click();
			if (buildingPage.pageName.getData().contains("Existing Account Found")) {
				existingAccountPage.OverrideExistingAccount();
			}

			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.clickOnOverride();
			}
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			if (buildingPage.pageName.getData().contains("Buildings No Longer Quoteable")) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			testData = data.get(dataValue1);

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
				Assertions.passTest("Select Peril Page", "Peril selected successfully");
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.addAdditionalCoveragesCommercial(testData);
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Adding the Ticket IO-21621
			// Verifying warning message when prior loss = water damage added,The warning
			// message is "Accounts with a water loss require referral"
			Assertions.addInfo("Scenario 02", "Verifying warning message when prior loss = water damage added");
			Assertions.verify(createQuotePage.wdrWarning.formatDynamicPath("water loss").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Warning message " + createQuotePage.wdrWarning.formatDynamicPath("water loss").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			// IO-21621 Ended

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Navigate to account overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			qtlngth = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, qtlngth - 1);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Log out as usm successfully");

			// Adding below code for IO-21439
			// Login as producer
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
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
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
				Assertions.passTest("Select Peril Page", "Peril selected successfully");
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Verifying the producer not able to create AOP quote without referral when
			// prior type loss = water damage added.
			// Verifying referral message when prior type loss = water damage added and
			// referral message is "Accounts with a water loss require referral."
			Assertions.addInfo("Scenario 03",
					"Verifying the producer not able to create AOP quote without referral when prior type loss = water damage added.");
			Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer quote page",
					"Refer quote page loaded successfully", false, false);
			Assertions.verify(
					referQuotePage.priorLossesReferralMessage.formatDynamicPath(2).getData().contains("water loss"),
					true, "Refer quote page",
					"The water damage prior loss referral message is "
							+ referQuotePage.priorLossesReferralMessage.formatDynamicPath(2).getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 28", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 28", "Executed Successfully");
			}
		}
	}
}
