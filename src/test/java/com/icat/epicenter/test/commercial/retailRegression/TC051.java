/** Program Description: Verify the error message when BOR from retail producer to another retail producer who doesnt have authorization in that state.
 *  Author			   : Sowndarya
 *  Date of Creation   : 28/03/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BrokerOfRecordPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC051 extends AbstractCommercialTest {

	public TC051() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID051.xls";
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
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		BrokerOfRecordPage brokerOfRecordPage = new BrokerOfRecordPage();
		LoginPage loginPage = new LoginPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		// Initializing the variables
		Map<String, String> testData;
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
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
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

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

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering bind details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted quote " + quoteNumber + " successfullly");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Click on approve button
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// click on expacc link
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// click on renew policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// Log out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Log out USM successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.waitTime(3);// wait time is needed to load the page
			loginPage.enterLoginDetails(setUpData.get("RetailProducer"), setUpData.get("Password"));
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			// Searching renewal quote
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Renewal Quote is searched successfully");

			// Click on edit deductibles link
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Deductible and Linits link successfully");

			// Updating Building value from 35,00,000 to 105,00,000
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.passTest("Create Quote Page", "Original Building Value is " + testData.get("L1B1-BldgValue"));
			testData = data.get(dataValue2);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillPresenceOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			Assertions.passTest("Create Quote Page", "Latest Building Value is " + testData.get("L1B1-BldgValue"));

			// Updating BPP value 0 to 5,00,001
			Assertions.passTest("Create Quote Page", "Latest BBP Value is " + testData.get("L1B1-BldgBPP"));
			createQuotePage.bPPValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgBPP"));
			createQuotePage.bPPValue.formatDynamicPath(0, 0).tab();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Asserting hard stop message when building value more than 100,00,000
			Assertions.addInfo("Scenario 01", "Asserting Referral message when building value more than 100,00,000");

			// Validating the referral message
			Assertions.verify(referQuotePage.covA6MReferralMsg.checkIfElementIsDisplayed(), true, "Refer Quote Page",
					"Referral Message : " + referQuotePage.covA6MReferralMsg.getData() + " is displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Refer Quote for binding
			testData = data.get(dataValue1);
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();
			quoteNumber = referQuotePage.quoteNum.getData();

			// Signing out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Click on Open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// approving referral
			referralPage.clickOnApprove();
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral Request approved successfully");

			// click on close button
			approveDeclineQuotePage.closeButton.scrollToElement();
			approveDeclineQuotePage.closeButton.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Searching renewal quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Renewal Quote is searched successfully");

			// getting renewal quote number2
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number 2 :  " + quoteNumber);

			// click on issue quote
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();

			// clicking on producer link to process AOR in account overview page
			accountOverviewPage.producerLink.scrollToElement();
			accountOverviewPage.producerLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Producer Link");

			// changing the producer details
			Assertions.verify(brokerOfRecordPage.processBOR.checkIfElementIsDisplayed(), true, "Broker of Record Page",
					"Broker of Record Page loaded successfully", false, false);

			// Entering New producer Number
			brokerOfRecordPage.newProducerNumber.scrollToElement();
			brokerOfRecordPage.newProducerNumber.setData(testData.get("NewBORProducerNumber"));
			Assertions.passTest("Broker of Record Page",
					"New Producer Number : " + brokerOfRecordPage.newProducerNumber.getData());

			// Change BOR status
			brokerOfRecordPage.borStatusArrow.scrollToElement();
			brokerOfRecordPage.borStatusArrow.click();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus"))
					.waitTillVisibilityOfElement(60);
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).scrollToElement();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();

			// click on process bor
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();
			Assertions.passTest("Broker of Record Page", "Clicked on Process BOR");

			// Assert the not authorized message for AZ state
			Assertions.addInfo("Scenario 02",
					"Verifying the error message as Producer Number 14406.1 AZ is Not Authorized");
			Assertions.verify(
					brokerOfRecordPage.producerAuthorizationMsg
							.formatDynamicPath("not authorized").checkIfElementIsDisplayed(),
					true, "Broker of Record Page",
					"The Error message "
							+ brokerOfRecordPage.producerAuthorizationMsg.formatDynamicPath("not authorized").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC051 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC051 ", "Executed Successfully");
			}
		}
	}
}
