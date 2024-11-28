/** Program Description: Create a commercial Policy with APC coverages and assert details on PolicySnapShot page and adding Ticket IO-21010
 *  Author			   : Abha
 *  Date of Creation   : 11/08/2019
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC001 extends AbstractCommercialTest {

	public TC001() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID001.xls";
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
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		LoginPage loginPage = new LoginPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		String renewalOfferQuoteNumber;
		String renewalOfferRenewalAdjustmentValue;
		String renewalReQuote1;
		String renewalReQuote1RAFValue;
		String renewalReQuote2;
		String renewalReQuote2RAFValue;
		String originalPolicyNumber;

		// variables for columns
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData1 = data.get(data_Value2);
		Map<String, String> testData2 = data.get(data_Value3);
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
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Approve Referral
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
			Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			originalPolicyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(originalPolicyNumber), true,
					"Policy Summary Page", "Policy Number is " + originalPolicyNumber, false, false);

			// Clicking on View Policy Snapshot link to view the details
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("View Policy Snap Shot Page", "Policy Snap Shot Page Loaded successfully");

			// Verifying details on Policy Snap Shot Page
			viewPolicySnapshotPage.refreshPage();
			Assertions.addInfo("View Policy Snap Shot Page", "Asserting APC Coverage Details");
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Awnings").checkIfElementIsPresent()) {
				Assertions.verify(
						viewPolicySnapshotPage.apcLimit
								.formatDynamicPath("Awnings").getData().contains(testData.get("L" + 1 + "-APCAwnings")),
						true, "View Policy Snap Shot Page",
						"APC limit for APC " + viewPolicySnapshotPage.apcName.formatDynamicPath("Awnings").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Awnings").getData(),
						false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Fences").checkIfElementIsPresent()) {
				Assertions.verify(
						viewPolicySnapshotPage.apcLimit
								.formatDynamicPath("Fences").getData().contains(testData.get("L" + 1 + "-APCFences")),
						true, "View Policy Snap Shot Page",
						"APC limit for APC " + viewPolicySnapshotPage.apcName.formatDynamicPath("Fences").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Fences").getData(),
						false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Other Structures - Fully")
					.checkIfElementIsPresent()) {
				Assertions.verify(
						viewPolicySnapshotPage.apcLimit.formatDynamicPath("Other Structures - Fully").getData()
								.contains(testData.get("L" + 1 + "-APCOtherStructures")),
						true, "View Policy Snap Shot Page",
						"APC limit for APC "
								+ viewPolicySnapshotPage.apcName.formatDynamicPath("Other Structures - Fully").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Other Structures - Fully")
										.getData(),
						false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Boardwalks").checkIfElementIsPresent()) {
				Assertions.verify(viewPolicySnapshotPage.apcLimit
						.formatDynamicPath("Boardwalks").getData().contains(testData.get("L" + 1 + "-APCBoardwalks")),
						true, "View Policy Snap Shot Page",
						"APC limit for APC " + viewPolicySnapshotPage.apcName.formatDynamicPath("Boardwalks").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Boardwalks").getData(),
						false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Satellite").checkIfElementIsPresent()) {
				Assertions.verify(
						viewPolicySnapshotPage.apcLimit.formatDynamicPath("Satellite").getData()
								.contains(testData.get("L" + 1 + "-APCSatelliteDishes")),
						true, "View Policy Snap Shot Page",
						"APC limit for APC " + viewPolicySnapshotPage.apcName.formatDynamicPath("Satellite").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Satellite").getData(),
						false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Fountains").checkIfElementIsPresent()) {
				Assertions.verify(viewPolicySnapshotPage.apcLimit
						.formatDynamicPath("Fountains").getData().contains(testData.get("L" + 1 + "-APCFountains")),
						true, "View Policy Snap Shot Page",
						"APC limit for APC " + viewPolicySnapshotPage.apcName.formatDynamicPath("Fountains").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Fountains").getData(),
						false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Underground").checkIfElementIsPresent()) {
				Assertions.verify(
						viewPolicySnapshotPage.apcLimit.formatDynamicPath("Underground").getData()
								.contains(testData.get("L" + 1 + "-APCUndergroundUtilities")),
						true, "View Policy Snap Shot Page",
						"APC limit for APC " + viewPolicySnapshotPage.apcName.formatDynamicPath("Underground").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Underground").getData(),
						false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Playground").checkIfElementIsPresent()) {
				Assertions.verify(viewPolicySnapshotPage.apcLimit
						.formatDynamicPath("Playground").getData().contains(testData.get("L" + 1 + "-APCPlayground")),
						true, "View Policy Snap Shot Page",
						"APC limit for APC " + viewPolicySnapshotPage.apcName.formatDynamicPath("Playground").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Playground").getData(),
						false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Driveways").checkIfElementIsPresent()) {
				Assertions.verify(viewPolicySnapshotPage.apcLimit
						.formatDynamicPath("Driveways").getData().contains(testData.get("L" + 1 + "-APCDriveways")),
						true, "View Policy Snap Shot Page",
						"APC limit for APC " + viewPolicySnapshotPage.apcName.formatDynamicPath("Driveways").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Driveways").getData(),
						false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Light Poles and Unattached Signs")
					.checkIfElementIsPresent()) {
				Assertions
						.verify(viewPolicySnapshotPage.apcLimit.formatDynamicPath("Light Poles and Unattached Signs")
								.getData().contains(testData.get("L" + 1 + "-APCLightPolesandUnattachedSigns")), true,
								"View Policy Snap Shot Page",
								"APC limit for APC "
										+ viewPolicySnapshotPage.apcName
												.formatDynamicPath("Light Poles and Unattached Signs").getData()
										+ " is "
										+ viewPolicySnapshotPage.apcLimit
												.formatDynamicPath("Light Poles and Unattached Signs").getData(),
								false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Pools").checkIfElementIsPresent()) {
				Assertions.verify(
						viewPolicySnapshotPage.apcLimit
								.formatDynamicPath("Pools").getData().contains(testData.get("L" + 1 + "-APCPools")),
						true, "View Policy Snap Shot Page",
						"APC limit for APC " + viewPolicySnapshotPage.apcName.formatDynamicPath("Pools").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Pools").getData(),
						false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Machinery").checkIfElementIsPresent()) {
				Assertions.verify(viewPolicySnapshotPage.apcLimit
						.formatDynamicPath("Machinery").getData().contains(testData.get("L" + 1 + "-APCMachinery")),
						true, "View Policy Snap Shot Page",
						"APC limit for APC " + viewPolicySnapshotPage.apcName.formatDynamicPath("Machinery").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Machinery").getData(),
						false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Other Structures - Open").checkIfElementIsPresent()) {
				Assertions.verify(
						viewPolicySnapshotPage.apcLimit.formatDynamicPath("Other Structures - Open").getData()
								.contains(testData.get("L" + 1 + "-APCOtherStructuresOpenorNotFullyEnclosed")),
						true, "View Policy Snap Shot Page",
						"APC limit for APC "
								+ viewPolicySnapshotPage.apcName.formatDynamicPath("Other Structures - Open").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Other Structures - Open")
										.getData(),
						false, false);
			}
			if (viewPolicySnapshotPage.apcName.formatDynamicPath("Carports").checkIfElementIsPresent()) {
				Assertions.verify(viewPolicySnapshotPage.apcLimit
						.formatDynamicPath("Carports").getData().contains(testData.get("L" + 1 + "-APCCarports")), true,
						"View Policy Snap Shot Page",
						"APC limit for APC " + viewPolicySnapshotPage.apcName.formatDynamicPath("Carports").getData()
								+ " is " + viewPolicySnapshotPage.apcLimit.formatDynamicPath("Carports").getData(),
						false, false);
			}

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
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policyNumber.startsWith("23-759"), true, "Policy Summary Page",
					"Policy Number format is verified", false, false);
			Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);

			// Searching Policy on global search
			homePage.scrollToTopPage();
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Home button successfully");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.policyGlobleSearch.appendData(policyNumber);
			homePage.searchedPolicyButton.waitTillPresenceOfElement(60);
			homePage.searchedPolicyButton.waitTillVisibilityOfElement(60);
			homePage.searchedPolicyButton.click();
			Assertions.passTest("Home Page", "Searched Policy through Global Search successfully");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policyNumber.startsWith("23-759"), true, "Policy Summary Page",
					"Policy Number format is verified", false, false);
			Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);

			// adding note to account
			policySummaryPage.scrollToBottomPage();
			policySummaryPage.waitTime(2);// need wait time to load the element
			Assertions.verify(policySummaryPage.newNote.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"New Note Link is displayed successfully", false, false);
			policySummaryPage.newNote.waitTillPresenceOfElement(60);
			policySummaryPage.newNote.waitTillVisibilityOfElement(60);
			policySummaryPage.newNote.click();

			// Click on note link
			Assertions.passTest("Policy Summary Page", "Clicked on Account Note Link successfully");
			policySummaryPage.selectCategory.formatDynamicPath(4).waitTillPresenceOfElement(60);
			policySummaryPage.selectCategory.formatDynamicPath(4).waitTillVisibilityOfElement(60);
			policySummaryPage.selectCategory.formatDynamicPath(4).click();
			Assertions.passTest("Policy Summary Page",
					"Account Category selected: " + policySummaryPage.selectCategory.formatDynamicPath(4).getData());
			policySummaryPage.enterNote.setData("Testing Account Notes");

			// Saving the note
			policySummaryPage.saveNoteButton.waitTillPresenceOfElement(60);
			policySummaryPage.saveNoteButton.waitTillVisibilityOfElement(60);
			policySummaryPage.saveNoteButton.scrollToElement();
			policySummaryPage.saveNoteButton.click();
			policySummaryPage.yesSaveNoteButton.checkIfElementIsDisplayed();
			policySummaryPage.yesSaveNoteButton.waitTillPresenceOfElement(60);
			policySummaryPage.yesSaveNoteButton.waitTillVisibilityOfElement(60);
			policySummaryPage.yesSaveNoteButton.click();
			policySummaryPage.waitTime(3);// need waittime to load the element

			// Asserting the notes added
			Assertions.addInfo("Policy Summary Page", "Asserting Account Notes from policy summary page");
			Assertions.verify(
					policySummaryPage.accountNote
							.formatDynamicPath("Testing Account Notes").checkIfElementIsDisplayed(),
					true, "Policy Summary Page",
					"Account Note added correctly, Account Note is "
							+ policySummaryPage.accountNote.formatDynamicPath("Testing Account Notes").getData(),
					false, false);

			// Adding code for IO-21010
			// Signing Out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Signing in as USM
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

			// Navigating to Policy Summary page
			homePage.searchPolicy(originalPolicyNumber);

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
				expaccInfoPage.enterExpaccInfo(testData, originalPolicyNumber);
			}

			// Performing Renewal
			homePage.goToHomepage.click();
			homePage.searchPolicy(originalPolicyNumber);
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
			renewalOfferRenewalAdjustmentValue = accountOverviewPage.renewalAdjustmentFactorvalue.getData();

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
			if (!accountOverviewPage.createAnotherQuote.checkIfElementIsPresent()
					&& !accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed()) {
				homePage.producerQuoteNumberLink.formatDynamicPath((testData.get("InsuredName"))).click();
			} else if (!accountOverviewPage.createAnotherQuote.checkIfElementIsPresent()
					&& !accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed()) {
				homePage.producerQuoteNumberLink.formatDynamicPath(renewalOfferQuoteNumber)
						.waitTillInVisibilityOfElement(60);
				homePage.producerQuoteNumberLink.formatDynamicPath(renewalOfferQuoteNumber).click();
			}

			// Creating another Quote
			accountOverviewPage.createAnotherQuote.checkIfElementIsPresent();
			accountOverviewPage.createAnotherQuote.click();
			selectPerilPage.allOtherPeril.click();
			selectPerilPage.continueButton.click();

			// Entering Quote Details Values
			createQuotePage.editDeductiblesCommercialPNB(testData1);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
			}

			// Fetching RenewalQuote1 Number
			renewalReQuote1 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal ReQuote Number 1: " + renewalReQuote1);

			// signing out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Logging in as USM to approve the referral
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

			// Navigating to RenewalQuote1's Account Overview Page
			homePage.searchQuote(renewalReQuote1);

			// Fetching Renewal ReQuote's Renewal Adjustment Value
			renewalReQuote1RAFValue = accountOverviewPage.renewalAdjustmentFactorvalue.getData();

			// Verifying if Renewal Offer's Renewal Adjustment Value and Renewal ReQuote1's
			// Renewal Adjustment Value are same
			if (renewalOfferRenewalAdjustmentValue == renewalReQuote1RAFValue) {
				Assertions.verify(renewalOfferRenewalAdjustmentValue, renewalReQuote1RAFValue, "Account Overview Page",
						"The Renewal Offer's Renewal Adjustment Value and Renewal ReQuote1's Renewal Adjustment Value are same ",
						false, false);
			} else {
				Assertions.verify(renewalOfferRenewalAdjustmentValue, renewalReQuote1RAFValue, "Account Overview Page",
						"The Renewal Offer's Renewal Adjustment Value and Renewal ReQuote1's Renewal Adjustment Value are not matching ",
						false, false);
			}

			// Creating another Quote by Deleting a Dwelling on the Account as a Producer
			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Signing in as Producer
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));

			// Navigating to the Account Overview Page of the Renewal ReQuote1
			homePage.searchQuoteByProducer(renewalReQuote1);
			if (!accountOverviewPage.createAnotherQuote.checkIfElementIsPresent()
					&& !accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed()) {
				homePage.producerQuoteNumberLink.formatDynamicPath(testData.get("InsuredName")).click();
			} else if (!accountOverviewPage.createAnotherQuote.checkIfElementIsPresent()
					&& !accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed()) {
				homePage.producerQuoteNumberLink.formatDynamicPath(renewalOfferQuoteNumber)
						.waitTillInVisibilityOfElement(60);
				homePage.producerQuoteNumberLink.formatDynamicPath(renewalOfferQuoteNumber).click();
			}

			// Deleting a Dwelling on the Account
			waitTime(5);
			accountOverviewPage.selectDwelling.formatDynamicPath(2).checkIfElementIsPresent();
			accountOverviewPage.selectDwelling.formatDynamicPath(2).scrollToElement();
			accountOverviewPage.selectDwelling.formatDynamicPath(2).click();
			waitTime(5);
			accountOverviewPage.deleteBuilding.formatDynamicPath(1).checkIfElementIsPresent();
			accountOverviewPage.deleteBuilding.formatDynamicPath(1).checkIfElementIsDisplayed();
			accountOverviewPage.deleteBuilding.formatDynamicPath(1).scrollToElement();
			accountOverviewPage.deleteBuilding.formatDynamicPath(1).click();

			// Creating another Renewal ReQuote
			accountOverviewPage.createAnotherQuote.checkIfElementIsPresent();
			accountOverviewPage.createAnotherQuote.click();
			selectPerilPage.allOtherPeril.click();
			selectPerilPage.continueButton.click();

			// Entering Quote Details Values
			createQuotePage.editDeductiblesCommercialPNB(testData2);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
			}

			// Fetching RenewalQuote1 Number
			renewalReQuote2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal ReQuote Number 1: " + renewalReQuote2);

			// signing out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Logging in as USM to approve the referral
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

			// Navigating to RenewalQuote1's Account Overview Page
			homePage.searchQuote(renewalReQuote2);

			// Fetching Renewal ReQuote's Renewal Adjustment Value
			renewalReQuote2RAFValue = accountOverviewPage.renewalAdjustmentFactorvalue.getData();

			// Verifying if Renewal Offer's Renewal Adjustment Value and Renewal ReQuote1's
			// Renewal Adjustment Value are same
			if (renewalOfferRenewalAdjustmentValue == renewalReQuote1RAFValue) {
				Assertions.verify(renewalOfferRenewalAdjustmentValue, renewalReQuote2RAFValue, "Account Overview Page",
						"The Renewal Offer's Renewal Adjustment Value and Renewal ReQuote2's Renewal Adjustment Value are same ",
						false, false);
			} else {
				Assertions.verify(renewalOfferRenewalAdjustmentValue, renewalReQuote2RAFValue, "Account Overview Page",
						"The Renewal Offer's Renewal Adjustment Value and Renewal ReQuote2's Renewal Adjustment Value are not matching ",
						false, false);
			}

			// Adding IO-21917
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on View previous policy button successfully");

			// Click on PB endorsement link
			Assertions.verify(policySummaryPage.endorsePB.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy summary page loaded successfully", false, false);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link successfully");

			// Click on OK button
			policySummaryPage.renewalCreatedOkBtn.scrollToElement();
			policySummaryPage.renewalCreatedOkBtn.click();

			// Enter Endorsement transaction effective date
			testData2 = data.get(data_Value3);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData2.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Transaction effective date entered successfully");

			// Click on change coverage option link
			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Clicked on change coverage option link successfully", false, false);

			// Removing APC
			createQuotePage.includeAPCCheckbox.formatDynamicPath(1).scrollToElement();
			createQuotePage.includeAPCCheckbox.formatDynamicPath(1).deSelect();
			Assertions.passTest("Create Quote Page", "APC check box deselected successfully");

			// Verifying continue endorsement button displayed or not when removing APC
			Assertions.addInfo("Create Quote Page",
					"Verifying continue endorsement button displayed or not when removing APC");
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "After removing APCs continue endorsement button displayed", false, false);

			// click on continue endorsement
			createQuotePage.continueEndorsementButton.waitTillPresenceOfElement(60);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on continue endorsement button successfully");

			// Click on next button
			Assertions.verify(endorsePolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on next button successfully");

			// Click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete and close button successfully");

			// verifying policy status
			Assertions.addInfo("", policyNumberResultTable);
			Assertions.verify(policySummaryPage.policyStatus.getData().contains("Active"), true, "Policy Summary Page",
					"Policy Status is " + policySummaryPage.policyStatus.getData(), false, false);
			// IO-21917 Ended

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Throwable e) {
			Assertions.failTest("Commercial NBRegression Test Case 01", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 01", "Executed Successfully");
			}
		}
	}
}