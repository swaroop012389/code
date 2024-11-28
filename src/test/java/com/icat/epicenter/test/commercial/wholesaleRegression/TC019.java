/** Program Description: Create multiple quotes,change dwelling details for one quote check if all other quotes are expired and change the quote expiration date bind the unexpired quote and added IO-21628
 *  Author			   : Yeshashwini TA
 *  Date of Creation   : 11/21/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EmailQuotePage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC019 extends AbstractCommercialTest {

	public TC019() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID019.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		EmailQuotePage emailQuotePage = new EmailQuotePage();

		// Initializing the variables
		String quoteNumber1;
		String quoteNumber2;
		String quoteNumber3;
		String quoteNumber4;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		Map<String, String> testData = data.get(dataValue1);
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

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Entering quote 1 Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number1
			quoteNumber1 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 1 :  " + quoteNumber1);

			// clicking on create another quote in account overview page
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create another quote");

			// click on override
			if (buildingPage.pageName.getData().contains("Buildings No")) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}

			// Entering quote 2 Details
			testData = data.get(dataValue2);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number2
			quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(2).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 2 :  " + quoteNumber2);

			// clicking on create another quote in account overview page
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create another quote");
			if (buildingPage.pageName.getData().contains("Buildings No")) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}

			// Entering quote 3 Details
			testData = data.get(dataValue3);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			quoteNumber3 = accountOverviewPage.quoteNumber.formatDynamicPath(3).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 3 :  " + quoteNumber3);

			// Changing the building Details
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();

			// Click on edit building
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Building");
			testData = data.get(dataValue2);
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page Loaded successfully", false, false);

			// handling expired quote pop up
			if (buildingPage.expiredQuotePopUp.checkIfElementIsPresent()
					&& buildingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
				buildingPage.expiredQuotePopUp.waitTillPresenceOfElement(60);
				buildingPage.expiredQuotePopUp.waitTillVisibilityOfElement(60);
				buildingPage.continueWithUpdateBtn.waitTillPresenceOfElement(60);
				buildingPage.continueWithUpdateBtn.waitTillVisibilityOfElement(60);
				buildingPage.continueWithUpdateBtn.click();
			}
			buildingPage.numOfStories.waitTillVisibilityOfElement(60);
			buildingPage.numOfStories.scrollToElement();
			buildingPage.numOfStories.clearData();
			if (buildingPage.expiredQuotePopUp.checkIfElementIsPresent()
					&& buildingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
				buildingPage.expiredQuotePopUp.waitTillPresenceOfElement(60);
				buildingPage.expiredQuotePopUp.waitTillVisibilityOfElement(60);
				buildingPage.continueWithUpdateBtn.waitTillPresenceOfElement(60);
				buildingPage.continueWithUpdateBtn.waitTillVisibilityOfElement(60);
				buildingPage.continueWithUpdateBtn.click();
			}
			buildingPage.numOfStories.tab();
			if (buildingPage.expiredQuotePopUp.checkIfElementIsPresent()
					&& buildingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
				buildingPage.expiredQuotePopUp.waitTillPresenceOfElement(60);
				buildingPage.expiredQuotePopUp.waitTillVisibilityOfElement(60);
				buildingPage.continueWithUpdateBtn.waitTillPresenceOfElement(60);
				buildingPage.continueWithUpdateBtn.waitTillVisibilityOfElement(60);
				buildingPage.continueWithUpdateBtn.click();
			}
			buildingPage.numOfStories.setData(testData.get("L1B1-BldgStories"));
			if (buildingPage.expiredQuotePopUp.checkIfElementIsPresent()
					&& buildingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
				buildingPage.expiredQuotePopUp.waitTillPresenceOfElement(60);
				buildingPage.expiredQuotePopUp.waitTillVisibilityOfElement(60);
				buildingPage.continueWithUpdateBtn.waitTillPresenceOfElement(60);
				buildingPage.continueWithUpdateBtn.waitTillVisibilityOfElement(60);
				buildingPage.continueWithUpdateBtn.click();
			}
			buildingPage.totalSquareFootage.waitTillVisibilityOfElement(60);
			buildingPage.totalSquareFootage.scrollToElement();
			buildingPage.totalSquareFootage.setData(testData.get("L1B1-BldgSqFeet"));
			Assertions.passTest("Building Page", "Building Details entered successfully");
			buildingPage.scrollToBottomPage();
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}

			// Click on Create quote
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			if (buildingPage.pageName.getData().contains("Buildings No")) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number4
			quoteNumber4 = accountOverviewPage.quoteNumber.formatDynamicPath(3).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 4 :  " + quoteNumber4);

			// Asserting all other quotes are expired
			Assertions.addInfo("Scenario 01", "Asserting the Quotes Status as expired");
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Number 1 status is "
							+ accountOverviewPage.quoteStatus.formatDynamicPath(1).getData() + " displayed is verified",
					false, false);
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(2).checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Number 2 status is "
							+ accountOverviewPage.quoteStatus.formatDynamicPath(2).getData() + " displayed is verified",
					false, false);
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Number 3 status is "
							+ accountOverviewPage.quoteStatus.formatDynamicPath(3).getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on Expired quote link
			accountOverviewPage.quoteLink.formatDynamicPath(4).scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath(4).click();
			Assertions.passTest("Account Overview Page", "Clicked on Quote Number 4 link");
			testData = data.get(dataValue1);
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.passTest("Account Overview Page", "Clicked on Quote Specifics");
			accountOverviewPage.quoteSpecificsChangeLink.click();
			accountOverviewPage.quoteExpirationDate.setData(testData.get("QuoteExpirationDate"));
			accountOverviewPage.quoteSpecificsChangeLink.click();
			Assertions.passTest("Account Overview Page", "Entered the Quote Expiration Date successfully");

			// Asserting the quote status as active
			Assertions.addInfo("Scenario 02", "Asserting quote status as active");
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Number 4 status is "
							+ accountOverviewPage.quoteStatus.formatDynamicPath(4).getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// clicking on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber4);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// added below code to handle order of the quote displayed(Order is different in
			// UAT and QA)on the account overview page
			if (!accountOverviewPage.requestBind.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber4);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}

			// entering details in request bind page
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
			homePage.searchQuote(quoteNumber4);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// Adding below code for IO-21628
			// Click on Request E_Signatures
			accountOverviewPage.requestESignatureLink.scrollToElement();
			accountOverviewPage.requestESignatureLink.click();
			Assertions.verify(emailQuotePage.cancel.checkIfElementIsDisplayed(), true, "Request E_signature page",
					"Request E_signature page loaded successfully", false, false);

			// Verifying presence of subscription agreement check box
			Assertions.addInfo("Scenario 03", "Verifying presence of subscription agreement check box");
			Assertions.verify(emailQuotePage.subscriptionAgreementlabel.checkIfElementIsDisplayed(), true,
					"Request E_signature page", "Subscription agreement check box displayed", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			emailQuotePage.cancel.scrollToElement();
			emailQuotePage.cancel.click();
			Assertions.passTest("Request E_signature page", "Clicked on cancel button successfully");
			// IO-21628 Ended

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
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

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 19", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 19", "Executed Successfully");
			}
		}
	}
}