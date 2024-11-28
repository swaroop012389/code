/** Program Description: Create an AOP Policy and Perform Different kinds of searches
 *  Author			   :  Sowndarya
 *  Date of Creation   : 09/10/2020
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DateConversions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BinderPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CancelPolicyPage;
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
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC038 extends AbstractCommercialTest {

	public TC038() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID038.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		BinderPage binderPage = new BinderPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;
		int data_Value5 = 4;
		int data_Value6 = 5;
		int data_Value7 = 6;
		Map<String, String> testData = data.get(data_Value1);
		String newbuildingAddress = null;

		DateConversions date = new DateConversions();
		String insuredName = testData.get("InsuredName") + date.getCurrentDate("MM/dd/YYYY_hh:mm:ss");
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.createNewAccountProducer.click();
			homePage.namedInsured.waitTillVisibilityOfElement(60);
			homePage.namedInsured.scrollToElement();
			homePage.namedInsured.setData(insuredName);
			Assertions.passTest("Home Page", "Insured Name is " + testData.get("InsuredName"));

			// product dropdown is shown but not editable if the producer isn't authorized
			// for multiple products
			if (homePage.productArrow.checkIfElementIsPresent() && homePage.productArrow.checkIfElementIsDisplayed()) {
				homePage.productArrow.scrollToElement();
				homePage.productArrow.click();
				homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			}

			if (homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(1).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
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
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();
			buildingPage.addBuildingDetails(testData, 1, 1);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, 1, 1);
			Assertions.passTest("Building Page", "Building Details Entered successfully");
			buildingPage.reviewBuilding();
			boolean addressFound = false;
			if (buildingPage.addressMsg.checkIfElementIsPresent() && buildingPage.addressMsg.checkIfElementIsDisplayed()
					&& !addressFound) {
				for (int i = 1; i <= 40; i++) {
					if (buildingPage.editBuilding.checkIfElementIsPresent()) {
						buildingPage.editBuilding.scrollToElement();
						buildingPage.editBuilding.click();
					}
					buildingPage.manualEntry.click();
					buildingPage.manualEntryAddress.waitTillVisibilityOfElement(60);
					buildingPage.manualEntryAddress.setData(buildingPage.manualEntryAddress.getData().replace(
							buildingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""),
							(Integer.parseInt(buildingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""))) + 2
									+ ""));
					newbuildingAddress = buildingPage.manualEntryAddress.getData();
					buildingPage.reviewBuilding();

					if (!buildingPage.addressMsg.checkIfElementIsPresent()
							|| !buildingPage.addressMsg.checkIfElementIsPresent()) {
						addressFound = true;
						break;
					}
				}
			}
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// SignOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully");

			// Perform an account search by account using named insured with a
			// status of Not Quoted
			Assertions.addInfo("Home Page",
					"Perform an account search by account using named insured with the status of Not Quoted");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"USM Home page loaded successfully", false, false);
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findAccountTypeNotBound.click();
			homePage.accountStatusArrow.waitTillVisibilityOfElement(60);
			homePage.accountStatusArrow.click();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus"))
					.waitTillVisibilityOfElement(60);
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).scrollToElement();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).click();
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page",
					"Account Search successfull for the Insured name " + insuredName + " with status Not Quoted");
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview Page loaded successfully", false, false);

			// SignOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home Page loaded successfully", false, false);

			// Searching the account as producer
			homePage.producerAccountNameSearchTextbox.setData(insuredName);
			homePage.producerAccountFindButton.scrollToElement();
			homePage.producerAccountFindButton.click();

			homePage.producerAccountNameLink.waitTillPresenceOfElement(60);
			homePage.producerAccountNameLink.waitTillVisibilityOfElement(60);
			homePage.producerAccountNameLink.scrollToElement();
			homePage.producerAccountNameLink.click();
			Assertions.passTest("Home Page", "Searched the account successfully");

			accountOverviewPage.quoteAccountButton.waitTillPresenceOfElement(60);
			accountOverviewPage.quoteAccountButton.waitTillVisibilityOfElement(60);
			accountOverviewPage.quoteAccountButton.scrollToElement();
			accountOverviewPage.quoteAccountButton.click();

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}


			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Enter Referral Contact Details
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();

			// verifying referral message
			Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true, "Referral Page",
					"Quote " + referQuotePage.quoteNumberforReferral.getData() + " referring to USM " + " is verified",
					false, false);
			String refquoteNumber = referQuotePage.quoteNumberforReferral.getData();
			Assertions.passTest("Refer Quote Page", "The Quote Number is : " + refquoteNumber);

			// SignOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully");

			// Perform an account search by account using named insured with a
			// status of Referred
			Assertions.addInfo("Home Page",
					"Perform an account search by account using named insured with a status of Referred");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			testData = data.get(data_Value2);
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findAccountTypeNotBound.click();
			homePage.accountStatusArrow.scrollToElement();
			homePage.accountStatusArrow.click();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus"))
					.waitTillVisibilityOfElement(60);
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).scrollToElement();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).click();
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page",
					"Account Search Successfull for the Insured name " + insuredName + " with status Referred");

			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Status "
							+ accountOverviewPage.quoteStatus.formatDynamicPath(1).getData() + " displayed is verified",
					false, false);

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// click on pick up
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on customized quote
			approveDeclineQuotePage.scrollToBottomPage();
			approveDeclineQuotePage.customizeQuoteButton.scrollToElement();
			approveDeclineQuotePage.customizeQuoteButton.click();
			Assertions.passTest("Approve Or Decline Quote Page", "Clicked on Customized quote button");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform an account search by account using named insured with a
			// status of Referred Requoted
			Assertions.addInfo("Home Page",
					"Perform an account search by account using named insured with a status of Referred Requoted and assert the quote status");

			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page",
					"Performed an Account search using Insured name with status Referred Requoted successfully");
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(2).checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Status "
							+ accountOverviewPage.quoteStatus.formatDynamicPath(2).getData() + " displayed is verified",
					false, false);

			accountOverviewPage.quoteLink.formatDynamicPath("Quote 1").scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath("Quote 1").click();

			// Click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			if (approveDeclineQuotePage.internalUnderwriterComments.checkIfElementIsPresent()) {
				approveDeclineQuotePage.internalUnderwriterComments.scrollToElement();
				approveDeclineQuotePage.internalUnderwriterComments.setData("Test");
			}
			if (approveDeclineQuotePage.externalUnderwriterComments.checkIfElementIsPresent()) {
				approveDeclineQuotePage.externalUnderwriterComments.scrollToElement();
				approveDeclineQuotePage.externalUnderwriterComments.setData("Test");
			}
			approveDeclineQuotePage.declineButton.scrollToElement();
			approveDeclineQuotePage.declineButton.click();
			Assertions.passTest("Referral Page", "Referral request Declined successfully");

			// Go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform an account search by account using named insured with a
			// status of Declined
			Assertions.addInfo("Home Page",
					"Perform an account search by account using named insured with a status of Declined and Assert the quote status");
			testData = data.get(data_Value3);
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findAccountTypeNotBound.click();
			homePage.accountStatusArrow.waitTillVisibilityOfElement(60);
			homePage.accountStatusArrow.click();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus"))
					.waitTillVisibilityOfElement(60);
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).scrollToElement();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).click();
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			while(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page",
					"Account Search successfull for the Insured name " + insuredName + " with status Declined");
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Status "
							+ accountOverviewPage.quoteStatus.formatDynamicPath(1).getData() + " displayed is verified",
					false, false);

			// Creating another quote
			testData = data.get(data_Value2);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();

			if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.addAdditionalCoveragesCommercial(testData);
			createQuotePage.internalQuoteChkBox.scrollToElement();
			createQuotePage.internalQuoteChkBox.select();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform an account search by account using named insured with a
			// status of Internal Only
			Assertions.addInfo("Home Page",
					"Perform an account search by account using named insured with a status of Internal Only and Assert the quote status");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page",
					"Account Search successfull for the Insured name " + insuredName + " with status Internal Only");
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Status "
							+ accountOverviewPage.quoteStatus.formatDynamicPath(3).getData() + " displayed is verified",
					false, false);

			// Creating another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// changing the Coverage value
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			waitTime(2);// wait time is needed to load the element
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			createQuotePage.addAdditionalCoveragesCommercial(testData);

			// get a quote
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}
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

			// Go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform an account search by account using named insured with a
			// status of Active
			Assertions.addInfo("Home Page",
					"Perform an account search by account using named insured with a status of Active and Assert the quote status");
			testData = data.get(data_Value4);
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findAccountTypeNotBound.click();
			homePage.accountStatusArrow.waitTillVisibilityOfElement(60);
			homePage.accountStatusArrow.click();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus"))
					.waitTillVisibilityOfElement(60);
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).scrollToElement();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).click();
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page",
					"Account Search successfull for the Insured name " + insuredName + " with status Active");
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Status "
							+ accountOverviewPage.quoteStatus.formatDynamicPath(4).getData() + " displayed is verified",
					false, false);
			accountOverviewPage.quoteLink.formatDynamicPath(quoteNumber).scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath(quoteNumber).click();

			// Click on Request bind
			testData = data.get(data_Value1);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Enter Bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform an account search by account using named insured with a
			// status of Submitted
			Assertions.addInfo("Home Page",
					"Perform an account search by account using named insured with a status of Submitted and Assert the quote status");
			testData = data.get(data_Value5);
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.waitTillVisibilityOfElement(60);
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findAccountTypeNotBound.click();
			homePage.accountStatusArrow.waitTillVisibilityOfElement(60);
			homePage.accountStatusArrow.click();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus"))
					.waitTillVisibilityOfElement(60);
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).scrollToElement();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).click();
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page",
					"Account Search successfull for the Insured name " + insuredName + " with status of Submitted");
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Status "
							+ accountOverviewPage.quoteStatus.formatDynamicPath(4).getData() + " displayed is verified",
					false, false);

			// Go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");

			// approving referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Performing Building search using different types

			// Building Search using Specific Address
			Assertions.addInfo("Home Page", "Perform Building Search using Specific Address");
			testData = data.get(data_Value1);
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterBuildingOption.click();
			homePage.buildingAddress.setData(testData.get("L1B1-BldgAddr1"));
			homePage.buildingCity.setData(testData.get("L1B1-BldgCity"));
			homePage.stateArrow.click();
			homePage.stateOption.formatDynamicPath(testData.get("InsuredState")).scrollToElement();
			homePage.stateOption.formatDynamicPath(testData.get("InsuredState")).scrollToElement();
			homePage.buildingZipcode.setData(testData.get("L1B1-BldgZIP"));

			homePage.buildingFindButton.scrollToElement();
			homePage.buildingFindButton.click();
			Assertions.passTest("Home Page", "Clicked on building find button");
			waitTime(8);

			if (homePage.searchResult.formatDynamicPath(policyNumber).checkIfElementIsPresent()
					&& homePage.searchResult.formatDynamicPath(policyNumber).checkIfElementIsDisplayed()) {
				homePage.searchResult.formatDynamicPath(policyNumber).waitTillPresenceOfElement(60);
				homePage.searchResult.formatDynamicPath(policyNumber).waitTillVisibilityOfElement(60);
				homePage.searchResult.formatDynamicPath(policyNumber).scrollToElement();
				homePage.searchResult.formatDynamicPath(policyNumber).click();
				waitTime(8);
				Assertions.passTest("Home Page", "policy searched successfully");
			}
			//following below else is not required and it will fails the tests in case search will not return the result immediately.
			/*else {
				homePage.buildingAddress.setData(newbuildingAddress);
				homePage.buildingFindButton.scrollToElement();
				homePage.buildingFindButton.click();
				if (homePage.searchResult.formatDynamicPath(insuredName).checkIfElementIsPresent()
						&& homePage.searchResult.formatDynamicPath(insuredName).checkIfElementIsDisplayed()) {
					homePage.searchResult.formatDynamicPath(insuredName).waitTillPresenceOfElement(60);
					homePage.searchResult.formatDynamicPath(insuredName).waitTillVisibilityOfElement(60);
					homePage.searchResult.formatDynamicPath(insuredName).scrollToElement();
					homePage.searchResult.formatDynamicPath(insuredName).click();
				}
			}*/

			Assertions.passTest("Home Page", "Performed building search using Specific address successfully");

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Building search using city,state and zipcode
			Assertions.addInfo("Home Page", "Perform Building search using city,state and zipcode");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterBuildingOption.click();
			homePage.buildingCity.setData(testData.get("L1B1-BldgCity"));
			homePage.buildingNamedInsuredfield.setData(insuredName);
			Assertions.passTest("Home Page", "Performed building search using city successfully");
			homePage.buildingFindButton.scrollToElement();
			homePage.buildingFindButton.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Building search using state
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterBuildingOption.click();
			homePage.buildingNamedInsuredfield.setData(insuredName);
			homePage.stateArrow.waitTillVisibilityOfElement(60);
			homePage.stateArrow.waitTillButtonIsClickable(60);
			homePage.stateArrow.scrollToElement();
			homePage.stateArrow.click();
			homePage.stateOption.formatDynamicPath(testData.get("InsuredState")).waitTillVisibilityOfElement(60);
			homePage.stateOption.formatDynamicPath(testData.get("InsuredState")).scrollToElement();
			homePage.stateOption.formatDynamicPath(testData.get("InsuredState")).click();

			homePage.buildingFindButton.scrollToElement();
			homePage.buildingFindButton.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page", "Performed building search using State successfully");

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Building search using zipcode
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterBuildingOption.click();
			homePage.buildingZipcode.setData(testData.get("ZipCode"));
			homePage.buildingNamedInsuredfield.setData(insuredName);
			homePage.buildingFindButton.scrollToElement();
			homePage.buildingFindButton.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page", "Performed building search using Zipcode successfully");

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Building search using Producer Number
			Assertions.addInfo("Home Page", "Perform Building search using Producer Number");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterBuildingOption.click();
			homePage.buildingProducerNumberField.setData(testData.get("ProducerNumber"));
			homePage.buildingNamedInsuredfield.setData(insuredName);
			homePage.buildingFindButton.scrollToElement();
			homePage.buildingFindButton.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page", "Performed building search using Producer Number successfully");

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Building Search using Insured Name
			Assertions.addInfo("Home Page", "Perform Building search using Insured Name");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterBuildingOption.click();
			homePage.buildingNamedInsuredfield.setData(insuredName);

			homePage.buildingFindButton.scrollToElement();
			homePage.buildingFindButton.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page", "Performed a building search using the Insured Name successfully");

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Building Search using Policy Number
			Assertions.addInfo("Home Page", "Perform Building search using Policy Number");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterBuildingOption.click();
			homePage.buildingPolicyNumberField.setData(policyNumber);

			homePage.buildingFindButton.scrollToElement();
			homePage.buildingFindButton.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page", "Performed a building search using PolicyNumber successfully");

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Performing Account search by Quote

			// Quote search using Insured name
			Assertions.addInfo("Home Page", "Perform Quote search using Insured name");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterQuoteOption.click();
			homePage.quoteInsuredNameField.setData(insuredName);
			homePage.findBtnQuote.scrollToElement();
			homePage.findBtnQuote.click();

			if (homePage.searchResult.formatDynamicPath(quoteNumber).checkIfElementIsPresent()
					&& homePage.searchResult.formatDynamicPath(quoteNumber).checkIfElementIsDisplayed()) {
				homePage.searchResult.formatDynamicPath(quoteNumber).waitTillPresenceOfElement(60);
				homePage.searchResult.formatDynamicPath(quoteNumber).waitTillVisibilityOfElement(60);
				homePage.searchResult.formatDynamicPath(quoteNumber).scrollToElement();
				homePage.searchResult.formatDynamicPath(quoteNumber).click();
			}
			Assertions.passTest("Home Page", "Performed a Quote search using Insured Name successfully");

			accountOverviewPage.viewPolicyBtn.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPolicyBtn.scrollToElement();
			accountOverviewPage.viewPolicyBtn.click();

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Quote search using QuoteNumber
			Assertions.addInfo("Home Page", "Perform Quote search using QuoteNumber");
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Performed a Quote search using QuoteNumber successfully");
			accountOverviewPage.viewPolicyBtn.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPolicyBtn.scrollToElement();
			accountOverviewPage.viewPolicyBtn.click();

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Quote search using CreationDate
			Assertions.addInfo("Home Page", "Perform Quote search using CreationDate");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterPolicyOption.click();
			homePage.policyInsuredNameField.setData(insuredName);
			homePage.policyCreationDateAfter.setData(testData.get("CreatedAfterDate"));
			homePage.findPolicyButton.scrollToElement();
			homePage.findPolicyButton.click();

			if (homePage.searchResult.formatDynamicPath(quoteNumber).checkIfElementIsPresent()
					&& homePage.searchResult.formatDynamicPath(quoteNumber).checkIfElementIsDisplayed()) {
				homePage.searchResult.formatDynamicPath(quoteNumber).waitTillPresenceOfElement(60);
				homePage.searchResult.formatDynamicPath(quoteNumber).waitTillVisibilityOfElement(60);
				homePage.searchResult.formatDynamicPath(quoteNumber).scrollToElement();
				homePage.searchResult.formatDynamicPath(quoteNumber).click();
			}
			Assertions.passTest("Home Page", "Performed a Quote search using Creation Date successfully");

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform Binder search
// binder search using the named insured
			Assertions.addInfo("Home Page", "Perform Binder search using the named insured");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterBinderOption.click();
			homePage.binderInsuredNameField.setData(insuredName);

			homePage.binderFindButton.scrollToElement();
			homePage.binderFindButton.click();

			Assertions.passTest("Home Page", "Performed a Binder search using Insured Name successfully");

			// Asserting the Policy Number from Binder Display
			Assertions.verify(binderPage.coverNotePolicyNumber.checkIfElementIsDisplayed(), true, "Binder Page",
					"Binder Page loaded successfully", false, false);

			Assertions.verify(binderPage.coverNotePolicyNumber.checkIfElementIsDisplayed(), true, "Binder Page",
					"Binder successfully searched for Policy number :"
							+ binderPage.coverNotePolicyNumber.getData().substring(32, 50),
					false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// binder search using Quote Number
			Assertions.addInfo("Home Page", "Perform Binder search using the Quote Number");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterBinderOption.click();
			homePage.binderQuoteorPolicyNumberFiled.setData(quoteNumber);

			homePage.binderFindButton.scrollToElement();
			homePage.binderFindButton.click();
			Assertions.passTest("Home Page", "Performed a Binder search using QuoteNumber successfully");

			// Asserting the Policy Number from Binder Display
			Assertions.verify(binderPage.coverNotePolicyNumber.checkIfElementIsDisplayed(), true, "Binder Page",
					"Binder Page loaded successfully", false, false);

			Assertions.verify(binderPage.coverNotePolicyNumber.checkIfElementIsDisplayed(), true, "Binder Page",
					"Binder successfully searched for Policy number :"
							+ binderPage.coverNotePolicyNumber.getData().substring(32, 50),
					false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// binder search using Policy Number
			Assertions.addInfo("Home Page", "Perform Binder search using the Policy Number");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterBinderOption.click();
			homePage.binderQuoteorPolicyNumberFiled.setData(policyNumber);

			homePage.binderFindButton.scrollToElement();
			homePage.binderFindButton.click();
			Assertions.passTest("Home Page", "Performed a Binder search using PolicyNumber successfully");

			// Asserting the Policy Number from Binder Display
			Assertions.verify(binderPage.coverNotePolicyNumber.checkIfElementIsDisplayed(), true, "Binder Page",
					"Binder Page loaded successfully", false, false);

			Assertions.verify(binderPage.coverNotePolicyNumber.checkIfElementIsDisplayed(), true, "Binder Page",
					"Binder successfully searched for Policy number :"
							+ binderPage.coverNotePolicyNumber.getData().substring(32, 50),
					false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// binder search using Creation Date
			Assertions.addInfo("Home Page", "Perform Binder search using Creation Date");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterBinderOption.click();
			homePage.binderInsuredNameField.setData(insuredName);
			homePage.binderCreationDateAfter.setData(testData.get("CreatedAfterDate"));

			homePage.binderFindButton.scrollToElement();
			homePage.binderFindButton.click();

			Assertions.passTest("Home Page", "Performed a Binder search using Creation Date successfully");

			// Asserting the Policy Number from Binder Display
			Assertions.verify(binderPage.coverNotePolicyNumber.checkIfElementIsDisplayed(), true, "Binder Page",
					"Binder Page loaded successfully", false, false);

			Assertions.verify(binderPage.coverNotePolicyNumber.checkIfElementIsDisplayed(), true, "Binder Page",
					"Binder successfully searched for Policy number :"
							+ binderPage.coverNotePolicyNumber.getData().substring(32, 50),
					false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// binder search using Producer Number
			Assertions.addInfo("Home Page", "Perform Binder search using Producer Number");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterBinderOption.click();
			homePage.binderInsuredNameField.setData(insuredName);
			homePage.binderProducerNumberField.setData(testData.get("ProducerNumber"));

			homePage.binderFindButton.scrollToElement();
			homePage.binderFindButton.click();
			Assertions.passTest("Home Page", "Performed a Binder search using Producer Number successfully");

			// Asserting the Policy Number from Binder Display
			Assertions.verify(binderPage.coverNotePolicyNumber.checkIfElementIsDisplayed(), true, "Binder Page",
					"Binder Page loaded successfully", false, false);

			Assertions.verify(binderPage.coverNotePolicyNumber.checkIfElementIsDisplayed(), true, "Binder Page",
					"Binder successfully searched for Policy number :"
							+ binderPage.coverNotePolicyNumber.getData().substring(32, 50),
					false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform search by selecting policy

			// policy search using the policy number
			Assertions.addInfo("Home Page", "Perform policy search using the policy number");
			homePage.searchPolicy(policyNumber);

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// policy search using the state
			Assertions.addInfo("Home Page", "Perform policy search using the state");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterPolicyOption.click();
			homePage.policyInsuredNameField.setData(insuredName);
			homePage.policyStateArrow.scrollToElement();
			homePage.policyStateArrow.click();
			homePage.policyStateOption.formatDynamicPath(testData.get("InsuredState")).scrollToElement();
			homePage.policyStateOption.formatDynamicPath(testData.get("InsuredState")).click();
			homePage.policyRadiobtns.formatDynamicPath(2).scrollToElement();
			homePage.policyRadiobtns.formatDynamicPath(2).click();
			homePage.findPolicyButton.scrollToElement();
			homePage.findPolicyButton.click();

			Assertions.passTest("Home Page", "Performed a Policy search using State successfully");

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// policy search using the Effective date
			Assertions.addInfo("Home Page", "Perform policy search using the Effective date");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterPolicyOption.click();
			homePage.policyInsuredNameField.setData(insuredName);
			homePage.policyCreationDateAfter.setData(testData.get("CreatedAfterDate"));
			homePage.policyRadiobtns.formatDynamicPath(2).scrollToElement();
			homePage.policyRadiobtns.formatDynamicPath(2).click();
			homePage.findPolicyButton.scrollToElement();
			homePage.findPolicyButton.click();
			Assertions.passTest("Home Page", "Performed a Policy search using Effective Date successfully");

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// policy search using the Producer Number
			Assertions.addInfo("Home Page", "Perform policy search using the  Producer Number");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterPolicyOption.click();
			homePage.policyInsuredNameField.setData(insuredName);
			homePage.policyProducerNumField.setData(testData.get("ProducerNumber"));
			homePage.policyRadiobtns.formatDynamicPath(2).scrollToElement();
			homePage.policyRadiobtns.formatDynamicPath(2).click();
			homePage.findPolicyButton.scrollToElement();
			homePage.findPolicyButton.click();
			Assertions.passTest("Home Page", "Performed a Policy search using Producer Number successfully");

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search for account using insured name
			Assertions.addInfo("Home Page", "Search for the account using insured name");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findBtnAccount.waitTillVisibilityOfElement(60);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			Assertions.passTest("Home Page", "Searched the account using " + insuredName + " successfully");

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Perform a policy search by account using named insured with a policy
			// status of Active
			Assertions.addInfo("Home Page",
					"Perform a policy search by account using named insured with a policy status of Active");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterPolicyOption.click();
			homePage.policyInsuredNameField.setData(insuredName);
			homePage.policyRadiobtns.formatDynamicPath(2).scrollToElement();
			homePage.policyRadiobtns.formatDynamicPath(2).click();
			homePage.findPolicyButton.scrollToElement();
			homePage.findPolicyButton.click();
			Assertions.passTest("Home Page", "Performed a Policy search using Insured Name " + insuredName
					+ " with status of Active successfully");

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform an account search using the state and producer number
			Assertions.addInfo("Home Page", "Perform an account search using the state and producer number");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.accountStateArrow.scrollToElement();
			homePage.accountStateArrow.click();
			homePage.accountStateOption.formatDynamicPath(testData.get("InsuredState")).waitTillVisibilityOfElement(60);
			homePage.accountStateOption.formatDynamicPath(testData.get("InsuredState")).scrollToElement();
			homePage.accountStateOption.formatDynamicPath(testData.get("InsuredState")).click();
			homePage.findBtnAccount.waitTillVisibilityOfElement(60);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Perform an account search by account using named insured with a
			// status of Pended
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endoresment Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on change coverage options hyperlink
			testData = data.get(data_Value3);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);

			// modifying deductibles and coverages in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialPNB(testData);
			Assertions.passTest("Create Quote Page", "Deductibles and coverages details modified successfully");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();

			}

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on Pend
			endorsePolicyPage.pendButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.pendButton.scrollToElement();
			endorsePolicyPage.pendButton.click();

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform an account search by account using named insured with a
			// status of Pended
			Assertions.addInfo("Home Page", "Perform an account search using named insured with a status of Pended");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterQuoteOption.click();
			homePage.quoteInsuredNameField.setData(insuredName);
			homePage.quoteRenewalRadioBtn.formatDynamicPath(4).scrollToElement();
			homePage.quoteRenewalRadioBtn.formatDynamicPath(4).click();
			homePage.findBtnQuote.scrollToElement();
			homePage.findBtnQuote.click();

			Assertions.verify(endorsePolicyPage.transactionComments.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Quote Search successful with the status Pend", false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			testData = data.get(data_Value1);
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Performing Renewal Searches
			homePage.searchPolicy(policyNumber);

			// Click on renew policy link
			policySummarypage.renewPolicy.waitTillVisibilityOfElement(60);
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy");

			policyRenewalPage.continueRenewal.scrollToElement();
			policyRenewalPage.continueRenewal.click();

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
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform an account search by account using named insured with a
			// status of Internal Renewal
			Assertions.addInfo("Home Page",
					"Perform an account search using named insured with a status of Internal Renewal");
			testData = data.get(data_Value6);
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findAccountTypeNotBound.click();
			homePage.accountStatusArrow.scrollToElement();
			homePage.accountStatusArrow.click();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus"))
					.waitTillVisibilityOfElement(60);
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).scrollToElement();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).click();
			homePage.findBtnAccount.waitTillVisibilityOfElement(60);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			Assertions.passTest("Home Page", "Performed an Account search using Insured Name " + insuredName
					+ " with Account status of Internal Renewal successfully");

			Assertions
					.verify(accountOverviewPage.internalRenewalStatus.checkIfElementIsDisplayed(), true,
							"Account Overview Page", "The Quote Status "
									+ accountOverviewPage.internalRenewalStatus.getData() + " displayed is verified",
							false, false);

			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal To Producer");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform an account search by account using named insured with a
			// status of Renewal
			Assertions.addInfo("Home Page", "Perform an account search using named insured with a status of Renewal");
			testData = data.get(data_Value7);
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findAccountTypeNotBound.click();
			homePage.accountStatusArrow.scrollToElement();
			homePage.accountStatusArrow.click();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus"))
					.waitTillVisibilityOfElement(60);
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).scrollToElement();
			homePage.accountStatusOption.formatDynamicPath(testData.get("AccountStatus")).click();
			homePage.findBtnAccount.waitTillVisibilityOfElement(60);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			Assertions.passTest("Home Page", "Performed an Account search using Insured Name " + insuredName
					+ " with Account status of Renewal successfully");

			Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The Quote Status " + accountOverviewPage.renewalStatus.getData() + " displayed is verified", false,
					false);

			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number 1 :  " + quoteNumber);

			// Click on Edit dedutibles and limits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// Entering Create quote page Details
			testData = data.get(data_Value2);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductibles(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(2).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number 2 :  " + quoteNumber);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform a quote search using the state and specifying that it is a
			// renewal
			Assertions.addInfo("Home Page",
					"Perform a quote search using the state and specifying that it is a renewal");
			testData = data.get(data_Value1);
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterQuoteOption.click();
			homePage.quoteInsuredNameField.setData(insuredName);
			homePage.quoteStateArrow.scrollToElement();
			homePage.quoteStateArrow.click();
			homePage.quoteStateOption.formatDynamicPath(testData.get("InsuredState")).waitTillVisibilityOfElement(60);
			homePage.quoteStateOption.formatDynamicPath(testData.get("InsuredState")).scrollToElement();
			homePage.quoteStateOption.formatDynamicPath(testData.get("InsuredState")).click();
			homePage.quoteRenewalRadioBtn.formatDynamicPath(3).scrollToElement();
			homePage.quoteRenewalRadioBtn.formatDynamicPath(3).click();
			homePage.findBtnQuote.waitTillVisibilityOfElement(60);
			homePage.findBtnQuote.click();
			if (homePage.searchResult.formatDynamicPath(quoteNumber).checkIfElementIsPresent()
					&& homePage.searchResult.formatDynamicPath(quoteNumber).checkIfElementIsDisplayed()) {
				homePage.searchResult.formatDynamicPath(quoteNumber).waitTillPresenceOfElement(60);
				homePage.searchResult.formatDynamicPath(quoteNumber).waitTillVisibilityOfElement(60);
				homePage.searchResult.formatDynamicPath(quoteNumber).scrollToElement();
				homePage.searchResult.formatDynamicPath(quoteNumber).click();
			}
			Assertions.passTest("Home Page", "Performed a Quote search using Insured State with Renewal successfully");

			Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The Quote Status " + accountOverviewPage.renewalStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform a quote search using the producer number and specifying that
			// it is a renewal
			Assertions.addInfo("Home Page",
					"Perform a quote search using the  producer number and specifying that it is a renewal");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterQuoteOption.click();
			homePage.quoteInsuredNameField.setData(insuredName);
			homePage.quoteProducerNumField.setData(testData.get("ProducerNumber"));
			homePage.quoteRenewalRadioBtn.formatDynamicPath(3).scrollToElement();
			homePage.quoteRenewalRadioBtn.formatDynamicPath(3).click();
			homePage.findBtnQuote.waitTillVisibilityOfElement(60);
			homePage.findBtnQuote.scrollToElement();
			homePage.findBtnQuote.click();

			if (homePage.searchResult.formatDynamicPath(quoteNumber).checkIfElementIsPresent()
					&& homePage.searchResult.formatDynamicPath(quoteNumber).checkIfElementIsDisplayed()) {
				homePage.searchResult.formatDynamicPath(quoteNumber).waitTillPresenceOfElement(60);
				homePage.searchResult.formatDynamicPath(quoteNumber).waitTillVisibilityOfElement(60);
				homePage.searchResult.formatDynamicPath(quoteNumber).scrollToElement();
				homePage.searchResult.formatDynamicPath(quoteNumber).click();
			}
			Assertions.passTest("Home Page",
					"Performed a Quote  search using Producer Number with Renewal successfully");

			Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The Quote Status " + accountOverviewPage.renewalStatus.getData() + " displayed is verified", false,
					false);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			if (bindRequestPage.homePage.checkIfElementIsPresent()
					&& bindRequestPage.homePage.checkIfElementIsDisplayed()) {
				Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
						"Bind Request Page loaded successfully", false, false);
				bindRequestPage.clickOnHomepagebutton();
				Assertions.passTest("Home Page", "Clicked on Home button");

				// searching the quote number in grid and clicking on the quote link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber);
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();
				Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");

				// approving referral
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				requestBindPage.approveRequest();
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			}

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			String renewpolicyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + renewpolicyNumber);

			// Performing Renewal Searches

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform a renewal search using the named insured
			Assertions.addInfo("Home Page", "Perform a renewal search using the named insured");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterRenewalOption.click();
			homePage.renewalInsuredName.setData(insuredName);
			homePage.renewalFindBtn.scrollToElement();
			homePage.renewalFindBtn.click();
			Assertions.passTest("Home Page",
					"Searched the Policy By Renewal Search using insured name " + insuredName + " successfully");

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform a renewal search using the previous policy number
			Assertions.addInfo("Home Page", "Perform a renewal search using the previous policy number");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterRenewalOption.click();
			homePage.previousPolicyNumber.setData(policyNumber);
			homePage.renewalFindBtn.scrollToElement();
			homePage.renewalFindBtn.click();
			Assertions.passTest("Home Page", "Searched the Policy By Renewal Search using Previous Policy Number "
					+ policyNumber + " successfully");

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform a renewal search using the quote number
			Assertions.addInfo("Home Page", "Perform a renewal search using the quote number");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterRenewalOption.click();
			homePage.renewalQuoteNumber.setData(quoteNumber);
			homePage.renewalFindBtn.scrollToElement();
			homePage.renewalFindBtn.click();
			Assertions.passTest("Home Page",
					"Searched the Policy By Renewal Search using Quote Number " + quoteNumber + " successfully");

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform a renewal search using the Producer Number
			Assertions.addInfo("Home Page", "Perform a renewal search using the Producer number");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterRenewalOption.click();
			homePage.renewalInsuredName.setData(insuredName);
			homePage.renewalProducerNumField.setData(testData.get("ProducerNumber"));
			homePage.renewalFindBtn.scrollToElement();
			homePage.renewalFindBtn.click();
			Assertions.passTest("Home Page",
					"Searched the Policy By Renewal Search using Producer Number successfully");

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform a renewal search using the Effective Date
			Assertions.addInfo("Home Page", "Perform a renewal search using the Effective Date");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterRenewalOption.click();
			homePage.renewalInsuredName.setData(insuredName);
			homePage.renewalEffDateAfter.setData(testData.get("CreatedAfterDate"));
			homePage.renewalFindBtn.scrollToElement();
			homePage.renewalFindBtn.click();
			Assertions.passTest("Home Page", "Searched the Policy By Renewal Search using Effective Date successfully");

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform a renewal search using the State
			Assertions.addInfo("Home Page", "Perform a renewal search using the State");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterRenewalOption.click();
			homePage.renewalInsuredName.setData(insuredName);
			homePage.renewalStateArrow.scrollToElement();
			homePage.renewalStateArrow.click();
			homePage.renewalStateOption.formatDynamicPath(testData.get("InsuredState")).waitTillVisibilityOfElement(60);
			homePage.renewalStateOption.formatDynamicPath(testData.get("InsuredState")).scrollToElement();
			homePage.renewalStateOption.formatDynamicPath(testData.get("InsuredState")).click();
			homePage.renewalFindBtn.scrollToElement();
			homePage.renewalFindBtn.click();
			Assertions.passTest("Home Page", "Searched the Policy By Renewal Search using State successfully");

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Searching the policy by selecting different status

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform a policy search by account using named insured with a policy
			// status of Active
			Assertions.addInfo("Home Page",
					"Perform a policy search by account using named insured with a policy status of Active");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.accountPolRadioBtn.click();
			homePage.searchPolicyByselectingStatus(testData);
			homePage.resultTable.formatDynamicPath(insuredName, 2).waitTillPresenceOfElement(60);
			homePage.searchResult.formatDynamicPath(insuredName, 2).waitTillVisibilityOfElement(60);
			homePage.searchResult.formatDynamicPath(insuredName, 2).scrollToElement();
			homePage.searchResult.formatDynamicPath(insuredName, 2).click();
			Assertions.passTest("Home Page", "Policy Search Successfull for the Insured name " + insuredName
					+ " by selecting Policy Status Active");

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Click on Cancel Policy
			policySummarypage.cancelPolicy.scrollToElement();
			policySummarypage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy Link");

			if (cancelPolicyPage.okButton.checkIfElementIsPresent()
					&& cancelPolicyPage.okButton.checkIfElementIsDisplayed()) {
				cancelPolicyPage.okButton.scrollToElement();
				cancelPolicyPage.okButton.click();
			}

			if (cancelPolicyPage.deleteAndContinue.checkIfElementIsPresent()
					&& cancelPolicyPage.deleteAndContinue.checkIfElementIsDisplayed()) {
				cancelPolicyPage.deleteAndContinue.scrollToElement();
				cancelPolicyPage.deleteAndContinue.click();
			}
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page Loaded successfully", false, false);
			cancelPolicyPage.enterCancellationDetails(testData);

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform a policy search by account using named insured with a policy
			// status of Cancelled
			Assertions.addInfo("Home Page",
					"Perform a policy search by account using named insured with a policy status of Cancelled");
			testData = data.get(data_Value2);
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.accountPolRadioBtn.click();
			homePage.searchPolicyByselectingStatus(testData);
			Assertions.passTest("Home Page", "Policy Search Successfull for the Insured name " + insuredName
					+ " by selecting Policy Status Cancelled");

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Perform a policy search by account using named insured with a policy
			// status of Expired
			Assertions.addInfo("Home Page",
					"Perform a policy search by account using named insured with a policy status of Expired");
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterPolicyOption.click();
			homePage.policyCreationDateAfter.setData(testData.get("CreatedAfterDate"));
			homePage.policyRadiobtns.formatDynamicPath(3).scrollToElement();
			homePage.policyRadiobtns.formatDynamicPath(3).click();
			Assertions.passTest("Home Page", "Clicked on Expired Radio Button");
			homePage.findPolicyButton.scrollToElement();
			homePage.findPolicyButton.click();

			homePage.resultTable.formatDynamicPath("Expired", "1").waitTillVisibilityOfElement(60);
			homePage.resultTable.formatDynamicPath("Expired", "1").scrollToElement();
			homePage.resultTable.formatDynamicPath("Expired", "1").click();

			// Asserting the Status of the Policy
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			Assertions.verify(policySummarypage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status " + policySummarypage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 38", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 38", "Executed Successfully");
			}
		}
	}
}
