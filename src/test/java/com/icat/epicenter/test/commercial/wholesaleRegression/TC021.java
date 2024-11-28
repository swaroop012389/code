/** Program Description: Request Premium change as a producer. and As a Producer, check if the Request Premium Change button is available and is working as expected.
 *  Author			   : John
 *  Date of Creation   : 11/22/2019
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
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PremiumReliefDecisionPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC021 extends AbstractCommercialTest {

	public TC021() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID021.xls";
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
		LoginPage login = new LoginPage();
		PremiumReliefDecisionPage premiumReliefDecisionPage = new PremiumReliefDecisionPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();

		// Initializing the variables
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		String quoteNumber;
		String policyNumber;
		int quotelength;
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"EligibilitypPage loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location page",
					"Location page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location page", "Location details entered successfully");

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building page", "Building details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account overview page", "Quote number :  " + quoteNumber);

			// Go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as producer.
			login.refreshPage();
			login.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login page", "Logged in as producer successfully ");

			// Searching the Quote
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Producer home page", "Quote number : " + quoteNumber + " searched successfully");

			// Asserting original premium
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page navigated", false, false);
			String originalPremium = accountOverviewPage.premiumValue.getData();
			Assertions.addInfo("Scenario 01", "Asserting the original premium value");
			Assertions.passTest("Request premium change page", "Original premium value is " + originalPremium);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on request premium change link
			accountOverviewPage.requestPremiumChangeLink.scrollToElement();
			accountOverviewPage.requestPremiumChangeLink.click();
			Assertions.passTest("Account overview page", "Clicked on request premium change link successfully");

			// Entering request premium details
			accountOverviewPage.requestPremiumChanges(testData);
			Assertions.passTest("Request premium page", "Request premium details entered successfully");

			// Assert quote status as "referred" when premium is changed
			Assertions.addInfo("Scenario 02", "Asserting the quote status as'referred' when premium is changed");
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("1").getData(), "Referred",
					"Account overview page",
					"Quote status is updated to " + accountOverviewPage.quoteStatus.formatDynamicPath("1").getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Logout as producer
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.click();

			// Login as USM
			login.refreshPage();
			login.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login page", "Logged as usm successfully");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Home Page", "Quote for approval is found successfullly");

			// approving referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");

			// Approve premium change request
			premiumReliefDecisionPage.enterPremiumReliefDetails(testData);
			referralPage.close.waitTillVisibilityOfElement(60);
			referralPage.close.click();
			Assertions.passTest("Premium relief decision page", "Premium change request approved");

			// Search for quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home page", "Quote searched successfully");

			// entering details in request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request bind");
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind request page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home page", "Clicked on home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account overview page", "Clicked on open referral link");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// Asserting updated premium on policy summary page
			Assertions.addInfo("Scenario 03", "Asserting updated premium on policy summary page");
			Assertions.verify(
					policySummarypage.transactionPremium.getData()
							.contains(testData.get("PremiumAdjustment_OfferedAmt")),
					true, "Policy summary page",
					"Transaction Premium on policy summary page is " + policySummarypage.transactionPremium.getData(),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy summary page", "Policy Number is : " + policyNumber);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as producer.
			testData = data.get(dataValue2);
			login.refreshPage();
			login.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login page", "Logged in as producer successfully ");
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location page", "Location details entered successfully");

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building page", "Building details entered successfully");

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select peril page",
					"Select peril page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select peril page", "Peril details entered successfully");

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
					"Prior loss page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior loss page", "Prior loss details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account overview page", "Quote number :  " + quoteNumber);

			// Verifying presence of request premium change link
			Assertions.addInfo("Scenario 04", "Verifying presence of request premium change link");
			Assertions.verify(
					accountOverviewPage.requestPremiumChangeLink.checkIfElementIsPresent()
							&& accountOverviewPage.requestPremiumChangeLink.checkIfElementIsDisplayed(),
					true, "Account overview page", "Request premium change link present is verified", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Asserting premium Before changing the premium
			originalPremium = accountOverviewPage.premiumValue.getData();
			Assertions.addInfo("Scenario 05", "Asserting the premium value before changing the premium");
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account overview page",
					"The premium before changing is " + accountOverviewPage.premiumValue.getData(), false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on request premium change link
			accountOverviewPage.requestPremiumChangeLink.scrollToElement();
			accountOverviewPage.requestPremiumChangeLink.click();
			Assertions.passTest("Account overview page", "Clicked on request premium change link successfully");

			// Enter premium details
			accountOverviewPage.requestPremiumChanges(testData);
			Assertions.passTest("Request premium page", "Premium details entered successfully");

			// Verifying referral message when premium changed "Thank you for your referral.
			// Your Underwriting contact has been notified of this request and will get back
			// to you shortly. If you have any questions about this request, please
			// reference the following number: XXXX."
			Assertions.addInfo("Scenario 06", "Verifying asserting referral message");
			Assertions.verify(accountOverviewPage.dwellingSuccessfullyDeletedMsg.getData().contains(
					"Thank you for your referral. Your Underwriting contact has been notified of this request and will get back to you shortly"),
					true, "Account overview page", "The referral message is "
							+ accountOverviewPage.dwellingSuccessfullyDeletedMsg.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as producer.
			login.refreshPage();
			login.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login page", "Logged in as usm successfully ");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Home Page", "Quote for approval is found successfullly");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// Asserting the updated premium value is displayed under pricing in the
			// referral
			// complete page.
			Assertions.addInfo("Scenario 07", "Asserting updated premium value on referral page");
			Assertions.verify(
					referralPage.requestedPremiumData.getData()
							.contains("$" + testData.get("PremiumAdjustment_TargetPremium")),
					true, "Referral page",
					"Requested premium is " + referralPage.requestedPremiumData.getData() + " displayed", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// enter internal and external comments
			premiumReliefDecisionPage.internalUWComments.scrollToElement();
			premiumReliefDecisionPage.internalUWComments.appendData(testData.get("PremiumAdjustment_InternalComments"));
			premiumReliefDecisionPage.externalUWComments.scrollToElement();
			premiumReliefDecisionPage.externalUWComments.appendData(testData.get("PremiumAdjustment_ExternalComments"));
			premiumReliefDecisionPage.denyBtn.scrollToElement();
			premiumReliefDecisionPage.denyBtn.click();
			Assertions.passTest("Premium relief decision Page", "Clicked on decline button successfully");

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as producer.
			login.refreshPage();
			login.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login page", "Logged in as producer successfully ");

			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Producer home page", "Quote number : " + quoteNumber + " searched successfully");

			// Asserting the premium value requested for change is NOT updated on the
			// Account
			// overview page,as the requested referral was decline
			Assertions.addInfo("Scenario 08",
					"Asserting the premium value requested for change is NOT updated on the account overview page,as the requested referral was decline");
			Assertions.verify(accountOverviewPage.premiumValue.getData(), originalPremium, "Account Overview Page",
					"The Premium after changing is " + accountOverviewPage.premiumValue.getData(), false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 21", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 21", "Executed Successfully");
			}
		}
	}
}
