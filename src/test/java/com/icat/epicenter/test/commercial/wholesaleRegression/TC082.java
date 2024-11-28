/** Program Description: Create a referral quote.Click on Display quote,Preview Binder,Pending modifications,handled outside system link and assert the quote details and added IO-21464
 *  Author			   : John
 *  Date of Creation   : 07/21/2020
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DateConversions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BinderPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
//import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC082 extends AbstractCommercialTest {

	public TC082() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID082.xls";
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
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
		BinderPage binderPage = new BinderPage();
		LoginPage loginPage = new LoginPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		String renewalOfferQuoteNumber;
		int locNo = 1;
		int bldgNoone = 1;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		int data_Value2 = 1;
		Map<String, String> testData1 = data.get(data_Value2);

		DateConversions date = new DateConversions();
		String insuredName = testData.get("InsuredName") + date.getCurrentDate("MM/dd/YYYY_hh:mm:ss");
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.createNewAccount.waitTillVisibilityOfElement(60);
			homePage.createNewAccount.scrollToElement();
			homePage.createNewAccount.click();
			homePage.namedInsured.waitTillVisibilityOfElement(60);
			homePage.namedInsured.scrollToElement();
			homePage.namedInsured.setData(insuredName);
			Assertions.passTest("Home Page", "Insured Name is " + testData.get("InsuredName"));
			homePage.producerNumber.appendData(testData.get("ProducerNumber"));
			homePage.productArrow.scrollToElement();
			homePage.productArrow.click();
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			if (homePage.effectiveDate.formatDynamicPath("1").checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath("1").checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath("1").scrollToElement();
				homePage.effectiveDate.formatDynamicPath("1").waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath("1").setData(testData.get("PolicyEffDate"));
			}
			homePage.goButton.click();
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
			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}

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

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 1 :  " + quoteNumber);

			// entering details in request bind page
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched");

			// Clicking on display Quote button in referral Page
			Assertions.verify(referralPage.displayQuote.checkIfElementIsDisplayed(), true, "Referral Page",
					"Display Quote Button is displayed in Referral Page", false, false);
			referralPage.displayQuote.scrollToElement();
			referralPage.displayQuote.click();
			Assertions.passTest("Referral Page", "Clicked on Display Quote button");

			// Asserting quote document
			Assertions.addInfo("Quote Details Page", "Asserting Quote Number on Quote Details page");
			Assertions.verify(quoteDetailsPage.quoteNumber.getData().contains(quoteNumber), true, "Quote Details Page",
					quoteDetailsPage.quoteNumber.getData() + " is displayed in quote document", false, false);
			referralPage.scrollToTopPage();
			referralPage.waitTime(3);// need wait time to load the element
			referralPage.close.click();

			// Approve Referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Clicking on display Quote button in referral Page
			Assertions.verify(requestBindPage.displayQuote.checkIfElementIsDisplayed(), true, "Referral Page",
					"Display Quote Button is displayed in Request Bind Referral Page", false, false);
			requestBindPage.displayQuote.scrollToElement();
			requestBindPage.displayQuote.click();
			Assertions.passTest("Request Bind Page", "Clicked on display Quote");

			// Asserting quote document
			Assertions.addInfo("Quote Details Page", "Asserting Quote Number on Quote Details page");
			Assertions.verify(quoteDetailsPage.quoteNumber.getData().contains(quoteNumber), true, "Quote Details Page",
					quoteDetailsPage.quoteNumber.getData() + " is displayed in quote document", false, false);
			scrollToTopPage();
			waitTime(3);// need wait time to load the element
			quoteDetailsPage.closeBtn.click();

			// Clicking on preview binder button in referral Page
			Assertions.verify(requestBindPage.previewBinder.checkIfElementIsDisplayed(), true, "Referral Page",
					"Preview Binder Button is displayed in Request Bind Referral Page", false, false);
			requestBindPage.previewBinder.scrollToElement();
			requestBindPage.previewBinder.click();
			Assertions.passTest("Request Bind Page", "Clicked on Preview Binder button");

			// Asserting quote document
			binderPage.scrollToBottomPage();
			Assertions.addInfo("Binder Page", "Assert Binder Message on Preview Binder Page");
			Assertions.verify(binderPage.binderMsg.getData().contains("This binder is issued"), true, "Binder Page",
					binderPage.binderMsg.getData() + " is displayed in binder document", false, false);
			binderPage.scrollToTopPage();
			binderPage.waitTime(3);// need wait time to load the element
			binderPage.goBackBtn.click();

			// Enter AI information and Internal/External comments in request bind page
			// while approving bind request
			Assertions.addInfo("Request Bind Page", "Adding Additional Interest Information");
			requestBindPage.addAdditionalInterest(testData1);
			requestBindPage.internalComments.scrollToElement();
			requestBindPage.internalComments.setData(testData1.get("PremiumAdjustment_InternalComments"));
			requestBindPage.externalComments.scrollToElement();
			requestBindPage.externalComments.setData(testData1.get("PremiumAdjustment_ExternalComments"));

			// Click on save link
			Assertions.verify(requestBindPage.save.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Save Button is displayed in Request Bind Referral Page", false, false);
			requestBindPage.save.scrollToElement();
			requestBindPage.save.click();
			Assertions.passTest("Request Bind Page", "Clicked on Save Button in Request Bind Referral Page");

			// Approve Referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Click on Cancel Button
			Assertions.verify(requestBindPage.cancel.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Cancel Button is displayed in Request Bind Referral Page", false, false);
			requestBindPage.cancel.scrollToElement();
			requestBindPage.cancel.click();
			Assertions.passTest("Request Bind Page", "Clicked on Cancel Button in Request Bind Referral Page");

			// Approve Referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Click on Pending Modifications
			Assertions.verify(requestBindPage.pendingModifications.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "Pending Modifications Button is displayed in Request Bind Referral Page",
					false, false);
			requestBindPage.internalComments.scrollToElement();
			requestBindPage.internalComments.setData(testData1.get("PremiumAdjustment_InternalComments"));
			requestBindPage.externalComments.scrollToElement();
			requestBindPage.externalComments.setData(testData1.get("PremiumAdjustment_ExternalComments"));
			requestBindPage.pendingModifications.scrollToElement();
			requestBindPage.pendingModifications.click();
			Assertions.passTest("Referral Page",
					"Clicked on Pending Notifications Button in Request Bind Referral Page");

			// Adding IO-21464
			// Verifying presence of close button
			Assertions.addInfo("Scenario 01", "Verifying the presence of close button");
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"The close button displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on close button
			referralPage.close.waitTillVisibilityOfElement(60);
			referralPage.close.scrollToElement();
			referralPage.close.click();
			Assertions.passTest("Referral Page", "Clicked on Close Button in Request Bind Referral Page");
			// IO-21464 ended

			// Signing out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as Producer successfully");

			// Search for account with insured name
			homePage.producerAccountSearchButton.waitTillVisibilityOfElement(60);
			homePage.producerAccountSearchButton.scrollToElement();
			homePage.producerAccountSearchButton.click();
			homePage.producerAccountNameSearchTextbox.scrollToElement();
			homePage.producerAccountNameSearchTextbox.setData(insuredName);
			homePage.producerAccountFindButton.scrollToElement();
			homePage.producerAccountFindButton.click();
			homePage.producerAccountNameLink.waitTillVisibilityOfElement(60);
			homePage.producerAccountNameLink.scrollToElement();
			homePage.producerAccountNameLink.click();

			// Verifying the quote status,Quote status = 'Pending modification'
			Assertions.addInfo("Scenario 02", "Verifying the Quote Status");
			Assertions.verify(
					accountOverviewPage.quoteStatus.formatDynamicPath("1").getData().contains("Pending Modification"),
					true, "Account Overview Page", "The Quote 1 Status is '"
							+ accountOverviewPage.quoteStatus.formatDynamicPath("1").getData() + "' verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on edit deductibles
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Deductibles Button");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get a Quote Button");

			// getting the quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(2).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 2:  " + quoteNumber);

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			accountOverviewPage.quoteLink.formatDynamicPath(quoteNumber).scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath(quoteNumber).click();
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Request Bind button is displayed", false, false);

			// Click on request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// bind the quote
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.contactName.setData(testData.get("ProducerName"));
			requestBindPage.contactEmailAddress.setData(testData.get("ProducerEmail"));
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			requestBindPage.requestBind.scrollToElement();
			requestBindPage.requestBind.click();
			Assertions.passTest("Request Bind Page", "Clicked on Request Bind Button");

			// Signing out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.scrollToBottomPage();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Approve Referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Click on handled outside system link
			Assertions.verify(requestBindPage.handledOutsideSystem.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "Handled Outside System Button is displayed in Request Bind Referral Page",
					false, false);
			requestBindPage.handledOutsideSystem.scrollToElement();
			requestBindPage.handledOutsideSystem.click();
			Assertions.passTest("Referral Page",
					"Clicked on Handled Outside System button in Request Bind Referral Page");

			// Click on close button
			referralPage.close.waitTillVisibilityOfElement(60);
			referralPage.close.scrollToElement();
			referralPage.close.click();
			Assertions.passTest("Referral Page", "Clicked on Close Button in Request Bind Referral Page");

			// Search quote
			homePage.searchQuote(quoteNumber);

			// unlock account
			Assertions.verify(accountOverviewPage.unlockAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.unlockAccount.scrollToElement();
			accountOverviewPage.unlockAccount.click();
			Assertions.passTest("Account Overview Page", "Clicked on Unlock Account Button in Account Overview Page");

			// Verifying the quote status,Quote status ='Handled Outside System"'
			Assertions.addInfo("Scenario 03", "Verifying the quote status");
			Assertions.verify(
					accountOverviewPage.quoteStatus.formatDynamicPath("2").getData().contains("Handled Outside System"),
					true, "Account Overview Page", "The Quote 2 Status is '"
							+ accountOverviewPage.quoteStatus.formatDynamicPath("2").getData() + "' verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Signing out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as Producer successfully");

			// Search quote
			homePage.searchQuoteByProducer(quoteNumber);

			// Create Another quote
			Assertions.addInfo("Account Overview Page", "Creating Another Quote");
			Assertions.verify(
					accountOverviewPage.quoteStatus.formatDynamicPath("2").getData().contains("Handled Outside System"),
					true, "Account Overview Page", "Account Overview Page is Loaded", false, false);
			accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed();
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create Another Quote Button");

			if (selectPerilPage.continueButton.checkIfElementIsPresent()
					&& selectPerilPage.continueButton.checkIfElementIsDisplayed()) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
				Assertions.passTest("Select Peril Page", "Peril details entered successfully");

				// Entering Create quote page Details
				createQuotePage.enterQuoteDetailsCommercialNew(testData);
				Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			}

			// click on ok button
			if (buildingNoLongerQuoteablePage.okButton.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.okButton.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.scrollToBottomPage();
				buildingNoLongerQuoteablePage.okButton.scrollToElement();
				buildingNoLongerQuoteablePage.okButton.click();
			}

			// click on edit deductibles and limits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// Entering Create quote page Details
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(3).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 3:  " + quoteNumber);

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.contactName.setData(testData.get("ProducerName"));
			requestBindPage.contactEmailAddress.setData(testData.get("ProducerEmail"));
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			requestBindPage.requestBind.scrollToElement();
			requestBindPage.requestBind.click();
			Assertions.passTest("Request Bind Page", "Clicked on Request Bind Button");

			// Go to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Signing out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Approve Referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Assert request Bind Page
			Assertions.verify(requestBindPage.inspectionName.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.inspectionName.scrollToElement();
			requestBindPage.inspectionName.setData(testData1.get("InspectionContact"));
			requestBindPage.inspectionAreaCode.setData(testData1.get("InspectionAreaCode"));
			requestBindPage.inspectionPrefix.setData(testData1.get("InspectionPrefix"));
			requestBindPage.inspectionNumber.setData(testData1.get("InspectionNumber"));
			Assertions.passTest("Referral Page", "Inspection contact details updated in Request Bind Referral Page");

			// Approve the bind referral
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

			// Find quote with account name
			Assertions.addInfo("Home Page", "Searching the quote with Insured Name");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findBtnAccount.waitTillVisibilityOfElement(60);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();

			// Click on view policy snapshot
			policySummaryPage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			// Assert policy number on view policy snapshot link
			Assertions.addInfo("View Policy Snapshot Page", "Assert the Policy Number on policy snapshot page");
			Assertions.verify(viewPolicySnapShot.policyNumberData.getData().contains(policyNumber), true,
					"View Policy Snapshot Page",
					viewPolicySnapShot.policyNumberData.getData() + " is displayed on Policy Snapshot Page", false,
					false);
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(3);// need wait time to scroll to top page
			viewPolicySnapShot.goBackButton.click();

			// Assert the policy status
			Assertions.addInfo("Policy Summary Page", "Assert the Policy Status");
			Assertions.verify(policySummaryPage.policyStatus.getData().contains("Active"), true, "Policy Summary Page",
					"Navigated to Policy Summary Page and policy status is " + policySummaryPage.policyStatus.getData(),
					false, false);

			// Asserting Account notes
			Assertions.addInfo("Policy Summary Page", "Asserting Notebar message on Policy summary page");
			policySummaryPage.waitTime(2);// to load note bar
			Assertions.verify(
					accountOverviewPage.noteBarMessage.formatDynamicPath("0").getData()
							.contains(testData.get("PremiumAdjustment_InternalComments")),
					true, "Policy Summary Page", accountOverviewPage.noteBarMessage.formatDynamicPath("0").getData()
							+ " is displayed in Account notes section",
					false, false);
			Assertions.verify(
					accountOverviewPage.noteBarMessage.formatDynamicPath("1").getData()
							.contains(testData.get("PremiumAdjustment_ExternalComments")),
					true, "Policy Summary Page", accountOverviewPage.noteBarMessage.formatDynamicPath("1").getData()
							+ " is displayed in Account notes section",
					false, false);

			// Assert for DEC link
			Assertions.addInfo("Policy Summary Page", "Asserting the presence of DEC link");
			policySummaryPage.decLink.waitTillVisibilityOfElement(60);
			Assertions.verify(policySummaryPage.decLink.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"DEC Link is displayed", false, false);

			// Added IO-21443

			// Clicking on Renew Policy link
			policySummaryPage.renewPolicy.checkIfElementIsPresent();
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Entering Expacc info
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsPresent()) {
				homePage.goToHomepage.checkIfElementIsPresent();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.checkIfElementIsPresent();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			}

			// Performing Renewal
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Click on continue button
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.click();
			}

			// Click on renewal pop up
			if (policyRenewalPage.renewalYes.checkIfElementIsPresent()
					&& policyRenewalPage.renewalYes.checkIfElementIsDisplayed()) {
				policyRenewalPage.renewalYes.checkIfElementIsPresent();
				policyRenewalPage.renewalYes.click();
			}

			// Fetching Renewal Offer QuoteNumber and Renewal Adjustment Factor Value
			renewalOfferQuoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal Offer Quote Number: " + renewalOfferQuoteNumber);

			// Releasing Renewal to Producer
			accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsPresent();
			accountOverviewPage.releaseRenewalToProducerButton.click();

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// signing in as Producer
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			// Navigating to AccountOverview Page of Renewal Quote
			homePage.searchQuoteByProducer(renewalOfferQuoteNumber);

			// clicking on edit building on the renewal account overview page as a producer
			accountOverviewPage.editBuildingLink1.scrollToElement();
			accountOverviewPage.editBuildingLink1.click();

			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			testData1 = data.get(data_Value2);
			buildingPage.enterBuildingValues(testData1, locNo, bldgNoone);

			// Click on review building
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on Review Building");

			// Click on bring up to cost
			buildingUnderMinimumCostPage.bringUpToCost.checkIfElementIsPresent();
			buildingUnderMinimumCostPage.bringUpToCost.scrollToElement();
			buildingUnderMinimumCostPage.bringUpToCost.click();

			// Log out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			homePage.searchQuote(renewalOfferQuoteNumber);

			// clicking on edit building on the renewal account overview page as an USM
			accountOverviewPage.editBuildingLink1.scrollToElement();
			accountOverviewPage.editBuildingLink1.click();

			accountOverviewPage.buildingValuationValue.checkIfElementIsPresent();
			String buildingValuationValue = accountOverviewPage.buildingValuationValue.getData().replace("$", "")
					.replace(",", "");

			double calBuildingValue = Double.parseDouble(buildingValuationValue) * 0.85;

			// rounding off value
			long calBuildingValueRoundingOff = Math.round(calBuildingValue);

			accountOverviewPage.buildingValue.checkIfElementIsPresent();
			String actualBuildingValue = accountOverviewPage.buildingValue.getData().replace("$", "").replace(",", "");

			Assertions.addInfo("Renewal Account Overview page",
					"Validating the building value on the Renewal Account Overview page based on the building valuation value.");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualBuildingValue), 2))
					- Precision.round(calBuildingValueRoundingOff, 2), 2) < 0.05) {
				Assertions.passTest("Renewal Account Overview page",
						"Calculated building value: " + "$" + calBuildingValueRoundingOff);
				Assertions.passTest("Renewal Account Overview page",
						"Actual building value: " + "$" + actualBuildingValue);
			} else {

				Assertions.verify(actualBuildingValue, calBuildingValueRoundingOff, "Renewal Account Overview page",
						"The difference between actual and calculated building value is more than 0.05", false, false);
			}

			// Ticket IO-21443 Ended

			// signout and close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 82", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 82", "Executed Successfully");
			}
		}
	}
}
