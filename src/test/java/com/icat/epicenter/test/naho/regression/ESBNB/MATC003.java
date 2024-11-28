/** Program Description: Verify Request Premium Change link, referral message on the Account Overview page,  referred quote is displayed as NB Prem Adj,  updated value displayed under Offered/Approved Premium field, Premium requested for change is NOT updated on the Account Overview page
 * 	Author			   : Kartik Sanga
 *  Date of Creation   : 28/02/2024
 **/
package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PremiumAdjustmentRequestPage;
import com.icat.epicenter.pom.PremiumReliefDecisionPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class MATC003 extends AbstractNAHOTest {

	public MATC003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/MATC003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		LoginPage loginPage = new LoginPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PremiumAdjustmentRequestPage premiumAdjustmentRequestPage = new PremiumAdjustmentRequestPage();
		PremiumReliefDecisionPage premiumReliefDecisionPage = new PremiumReliefDecisionPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		int dataValue5 = 4;
		testData = data.get(dataValue1);
		testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			testData = data.get(dataValue1);
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			Assertions.addInfo("Adding Premium Relief Test Cases", "TC01");

			// Assert the "Request Premium Change" button is NOT displayed on the Account
			// Overview page when premium less than $1500
			Assertions.addInfo("Scenario 01",
					"Verifying Request Premium Change Link is NOT displayed on the Account Overview page when quote premium is less than $1500");
			Assertions.verify(!accountOverviewPage.requestPremiumChangeLink.checkIfElementIsPresent(), true,
					"Account Overview Page", "Request Premium Change link not displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Clicking on Create Another Quote
			testData = data.get(dataValue2);
			accountOverviewPage.createAnotherQuote.checkIfElementIsPresent();
			accountOverviewPage.createAnotherQuote.waitTillButtonIsClickable(60);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();

			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}

			createQuotePage.enterQuoteDetailsNAHO(testData);

			String quoteNumber1 = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			// String quotePremium1 =
			// accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");

			// Logout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Loged out as usm successfully");

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in a as producer Successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(quoteNumber1);
			Assertions.passTest("Home Page", "Quote searched as a producer successfully");

			// Click on request premium Change link
			accountOverviewPage.requestPremiumChangeLink.scrollToElement();
			accountOverviewPage.requestPremiumChangeLink.click();
			premiumAdjustmentRequestPage.waitTime(1);
			Assertions.verify(premiumAdjustmentRequestPage.rpcUpdateBtn.checkIfElementIsDisplayed(), true,
					"Premium Adjustment Request Page", "Premium Adjustment Request Page Displayed Successfully", false,
					false);
			Assertions.passTest("Account Overview Page", "Clicked on Request Premium change link");

			// Entering Target Premium on Premium Adjustment Request Page
			// Target Premium: $Existing Premium - $1000
			// Insured Name and Email id
			testData = data.get(dataValue2);
			Assertions.verify(premiumAdjustmentRequestPage.rpcUpdateBtn.checkIfElementIsDisplayed(), true,
					"Premium Adjustment Request Page", "Request Premium Page Loaded Successfully ", false, false);
			premiumAdjustmentRequestPage.requestPremiumChanges(testData);
			Assertions.passTest("Account Overview Page", "Clicked on Request Premium change link");

			// Verifying the displayed referral message on the Account Overview page.
			// Msg: Thank you for your referral. Your Underwriting contact has been notified
			// of this request and will get back to you shortly. If you have any questions
			// about this request, please reference the following number:
			Assertions.addInfo("Scenario 03", "Verifying asserting referral message");
			Assertions.verify(accountOverviewPage.requestPremiumChangeReferralMsg.getData().contains(
					"Thank you for your referral. Your Underwriting contact has been notified of this request and will get back to you shortly. If you have any questions about this request, please reference the following number:"),
					true, "Account overview page", "The referral message is "
							+ accountOverviewPage.requestPremiumChangeReferralMsg.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Login as USM and search for the referred quote number
			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in as USM successfully");

			// searching the quote number in grid and clicking on the quote link
			Assertions.addInfo("Scenario 04", "Asserting the Type of referred quote is displayed as NB Prem Adj");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.refreshUntilQuoteFound(quoteNumber1);
			Assertions.passTest("Home Page", "Quote for approval is found successfullly");
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Approving referral
			referralPage.clickOnApprove();
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			Assertions.passTest("Referral page", "Referral request approved successfully");

			// Entering wind premium
			premiumReliefDecisionPage.windPremium.setData(testData.get("WindPremium"));
			premiumReliefDecisionPage.windPremium.tab();

			// Assert the updated value displayed under Offered/Approved Premium field.
			// Fetching Values of Requested Premiun , AOP and Liability Premium From Premium
			// Relief Decision Page
			double requestedPremium = Double.parseDouble(
					premiumReliefDecisionPage.requestedPremium.getData().replace("$", "").replace(",", ""));
			String OrgAopPremium = premiumReliefDecisionPage.orgAopPremium.getData().replace("$", "");
			double orgAopPremium = Double.parseDouble(OrgAopPremium);
			String OrgGLPremium = premiumReliefDecisionPage.orgGLPremium.getData().replace("$", "");
			double orgGLPremium = Double.parseDouble(OrgGLPremium);
			double orgWindPremium = Double.parseDouble(testData.get("WindPremium"));

			// Sum of the Original AOP, Wind and GL/Liability Premium
			double sumOfAopGLandWind = orgGLPremium + orgAopPremium + orgWindPremium;
			premiumReliefDecisionPage.waitTime(2);
			String offeredOrApprovedPre = premiumReliefDecisionPage.offeredOrApprovedPremium.getData();
			double offeredOrApprovedPremium = Double.parseDouble(offeredOrApprovedPre);

			Assertions.addInfo("Scenario 05", "Verifying Offered/Approved Premium is equal to sum Of Aop,GL and Wind");
			if (Precision.round(
					Math.abs(Precision.round(offeredOrApprovedPremium, 2) - Precision.round(sumOfAopGLandWind, 2)),
					2) < 0.01) {
				Assertions.passTest("Referral Approve/Decline Page",
						"Offered Or Approved Premium: " + "$" + Precision.round(offeredOrApprovedPremium, 2));
				Assertions.passTest("Referral Approve/Decline Page",
						"Sum Of Aop GL and Wind: " + "$" + Precision.round(sumOfAopGLandWind, 2));
			} else {
				Assertions.verify(Precision.round(offeredOrApprovedPremium, 2), Precision.round(sumOfAopGLandWind, 2),
						"Referral Approve/Decline Page",
						"The Actaul and Calculated Wind, AOP and GL Values are not the Same", false, false);
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");
			// Entering Internal and External comment
			approveDeclineQuotePage.internalUnderwriterComments.setData(testData.get("PRCInternalUWComments"));
			approveDeclineQuotePage.externalUnderwriterComments.setData(testData.get("PRCExternalUWComments"));

			// Clicking on approve button
			approveDeclineQuotePage.approveButton.scrollToElement();
			approveDeclineQuotePage.approveButton.click();
			referralPage.close.scrollToElement();
			referralPage.close.click();

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber1);

			// Assert the Premium requested for change is updated on the Account
			// Overview page
			String OfferedApprovedQuotePre = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",",
					"");
			double offeredApprovedQuotePremium = Double.parseDouble(OfferedApprovedQuotePre);
			Assertions.addInfo("Scenario 06",
					"Verifying Offered/Approved  premium is updated on the Account Overview page");
			if (Precision.round(
					Math.abs(Precision.round(offeredApprovedQuotePremium, 2) - Precision.round(sumOfAopGLandWind, 2)),
					2) < 0.01) {
				Assertions.passTest("Account Overview Page",
						"OfferedOrApproved Quote Premium: " + "$" + Precision.round(offeredApprovedQuotePremium, 2));
				Assertions.passTest("Premium Relief Decision Page",
						"Sum Of Aop GL and Wind: " + "$" + Precision.round(sumOfAopGLandWind, 2));
			} else {
				Assertions.verify(Precision.round(offeredApprovedQuotePremium, 2),
						Precision.round(sumOfAopGLandWind, 2), "Referral Approve/Decline Page",
						"The Actaul and Calculated Wind, AOP and GL Values are not the Same", false, false);
			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// ---------------------------------TC01 Completed-----------------

			Assertions.addInfo("Adding Premium Relief Test Cases", "TC02");
			testData = data.get(dataValue3);
			// Sign out as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home page", "Sign out as usm successfully");

			// Logging as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as Producer Successfully");

			// Create account as producer
			testData = data.get(dataValue3);
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page ",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account ", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page ",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page ", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page ",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page ", "Dwelling details entered successfully");

			// click on Get A quote button
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					&& dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page ",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page ", "Quote details entered successfully");

			// Fetching Quote Number
			String quoteNumber3 = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");

			// Verifying presence of request premium change link when premium >1500
			Assertions.addInfo("Scenario 01", "Verify presnce of request premium change link when premium is >1500");
			Assertions.verify(
					accountOverviewPage.requestPremiumChangeLink.checkIfElementIsPresent()
							&& accountOverviewPage.requestPremiumChangeLink.checkIfElementIsDisplayed(),
					true, "Account overview page", "Request premium change link is displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Assertion of premium Before changing the premium
			Assertions.addInfo("Scenario 02", "Asserting the Premium value before changing the premium");
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The Premium Before Changing is " + accountOverviewPage.premiumValue.getData(), false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on request premium Change link
			accountOverviewPage.requestPremiumChangeLink.scrollToElement();
			accountOverviewPage.requestPremiumChangeLink.click();
			premiumAdjustmentRequestPage.waitTime(2);
			Assertions.verify(premiumAdjustmentRequestPage.rpcUpdateBtn.checkIfElementIsDisplayed(), true,
					"Premium Adjustment Request Page", "Premium Adjustment Request Page Displayed Successfully", false,
					false);
			Assertions.passTest("Account Overview Page", "Clicked on Request Premium change link");

			// Selecting the reason for premium change
			premiumAdjustmentRequestPage.reasonForRequestArrow.click();
			premiumAdjustmentRequestPage.waitTime(2);
			premiumAdjustmentRequestPage.reasonForRequestOptions
					.formatDynamicPath(testData.get("PremiumAdjustment_Reason")).scrollToElement();
			premiumAdjustmentRequestPage.reasonForRequestOptions
					.formatDynamicPath(testData.get("PremiumAdjustment_Reason")).click();
			Assertions.passTest("Premium Adjustment Request Page",
					"Reason For Request Options Selected is : " + testData.get("PremiumAdjustment_Reason"));

			// Entering Target Premium on Premium Adjustment Request Page
			// Target Premium: $Existing Premium - $1000
			// Insured Name and Email id
			premiumAdjustmentRequestPage.targetPremium.setData(testData.get("PremiumAdjustment_TargetPremium"));
			Assertions.addInfo("Premium Adjustment Request Page",
					"Target Premium is : " + testData.get("PremiumAdjustment_TargetPremium"));
			premiumAdjustmentRequestPage.yourName.setData(testData.get("InsuredName"));
			premiumAdjustmentRequestPage.yourEmailAddress.setData(testData.get("InsuredEmail"));

			// Clicking on Request Premium Change button
			premiumAdjustmentRequestPage.rpcUpdateBtn.click();
			Assertions.addInfo("Premium Adjustment Request Page", "Clciked on Request Premium Chnaged button");

			// Verifying the displayed referral message on the Account Overview page.
			// Msg: Thank you for your referral. Your Underwriting contact has been notified
			// of this request and will get back to you shortly. If you have any questions
			// about this request, please reference the following number:
			Assertions.addInfo("Scenario 03", "Verifying asserting referral message");
			Assertions.verify(accountOverviewPage.requestPremiumChangeReferralMsg.getData().contains(
					"Thank you for your referral. Your Underwriting contact has been notified of this request and will get back to you shortly. If you have any questions about this request, please reference the following number:"),
					true, "Account overview page", "The referral message is "
							+ accountOverviewPage.requestPremiumChangeReferralMsg.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Login as USM and search for the referred quote number
			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in as USM successfully");

			// searching the quote number in grid and clicking on the quote link
			Assertions.addInfo("Scenario 04", "Asserting the Type of referred quote is displayed as NB Prem Adj");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.refreshUntilQuoteFound(quoteNumber3);
			Assertions.passTest("Home Page", "Quote for approval is found successfullly");
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Approving referral
			referralPage.clickOnApprove();
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			Assertions.passTest("Referral page", "Referral request approved successfully");

			// Entering wind premium
			premiumReliefDecisionPage.windPremium.setData(testData.get("WindPremium"));
			premiumReliefDecisionPage.windPremium.tab();

			// Assert the updated value displayed under Offered/Approved Premium field.
			// Fetching Values of Requested Premiun , AOP and Liability Premium From Premium
			// Relief Decision Page
			Double.parseDouble(premiumReliefDecisionPage.requestedPremium.getData().replace("$", "").replace(",", ""));

			String OrgAopPremium1 = premiumReliefDecisionPage.orgAopPremium.getData().replace("$", "");
			double orgAopPremium1 = Double.parseDouble(OrgAopPremium1);

			String OrgGLPremium1 = premiumReliefDecisionPage.orgGLPremium.getData().replace("$", "");
			double orgGLPremium1 = Double.parseDouble(OrgGLPremium1);

			double orgWindPremium1 = Double.parseDouble(testData.get("WindPremium"));

			// Sum of the Original AOP, Wind and GL/Liability Premium
			double sumOfAopGLandWind1 = orgGLPremium1 + orgAopPremium1 + orgWindPremium1;
			premiumReliefDecisionPage.waitTime(2);
			String offeredOrApprovedPre1 = premiumReliefDecisionPage.offeredOrApprovedPremium.getData();
			double offeredOrApprovedPremium1 = Double.parseDouble(offeredOrApprovedPre1);

			Assertions.addInfo("Scenario 05", "Verifying Offered/Approved Premium is equal to sum Of Aop,GL and Wind");
			if (Precision.round(
					Math.abs(Precision.round(offeredOrApprovedPremium1, 2) - Precision.round(sumOfAopGLandWind1, 2)),
					2) < 0.01) {
				Assertions.passTest("Referral Approve/Decline Page",
						"Offered Or Approved Premium: " + "$" + Precision.round(offeredOrApprovedPremium1, 2));
				Assertions.passTest("Referral Approve/Decline Page",
						"Sum Of Aop GL and Wind: " + "$" + Precision.round(sumOfAopGLandWind1, 2));
			} else {
				Assertions.verify(Precision.round(offeredOrApprovedPremium1, 2), Precision.round(sumOfAopGLandWind1, 2),
						"Referral Approve/Decline Page",
						"The Actaul and Calculated Wind, AOP and GL Values are not the Same", false, false);
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			approveDeclineQuotePage.internalUnderwriterComments.setData(testData.get("PRCInternalUWComments"));
			approveDeclineQuotePage.externalUnderwriterComments.setData(testData.get("PRCExternalUWComments"));
			// Clicking on Decline button
			approveDeclineQuotePage.declineButton.scrollToElement();
			approveDeclineQuotePage.declineButton.click();
			referralPage.close.scrollToElement();
			referralPage.close.click();

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(quoteNumber3);

			// Assert the Premium requested for change is NOT updated on the Account
			// Overview page
			Assertions.addInfo("Scenario 06",
					"Verifying Premium requested for change is NOT updated on the Account Overview page");
			String quotePre = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			double quotePremium = Double.parseDouble(quotePre);
			Assertions.verify(requestedPremium == quotePremium, false, "Account Overview Page",
					"The Requested Premium Value : " + requestedPremium + " is NOT updated to Quote Premium is : "
							+ quotePremium + " on Account Overview page",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// -------------------TC03--------------
			testData = data.get(dataValue4);
			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in as USM successfully");

			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details

			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			String quoteNumber4 = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");

			// Click on Bind button on request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber4);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Assert policy number
			String policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page ",
					"Policy Summary Page loaded successfully " + "Policy Number "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on renew policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Go to Home Page
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
			}

			// click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked Release Renewal To Producer button successfully");

			// Assert the "Request Premium Change" button is NOT displayed on the Account
			// Overview page when premium less than $1500
			Assertions.addInfo("Scenario 01",
					"Verifying Request Premium Change Link is NOT displayed on the Account Overview page when quote premium is less than $1500");
			Assertions.verify(!accountOverviewPage.requestPremiumChangeLink.checkIfElementIsPresent(), true,
					"Account Overview Page", "Request Premium Change link not displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on create another quote link
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();

			testData = data.get(dataValue5);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page ", "Quote details entered successfully");

			// Adding if condition for continue

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();

			}
			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();

			}

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();

			}

			// Click Issue Quote Button
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page ", "Issue Quote Button is Clicked");

			// getting renewal quote number
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Renewal quote number " + renewalQuoteNumber);

			// Logout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Loged out as usm successfully");

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in a as producer Successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(renewalQuoteNumber);
			Assertions.passTest("Home Page", "Quote searched as a producer successfully");

			// Assert the "Request Premium Change" button is displayed on the Account
			// Overview page when premium greater than $1500
			Assertions.addInfo("Scenario 02",
					"Verifying Request Premium Change Link is displayed on the Account Overview page when quote premium is greater than $1500");
			Assertions.verify(accountOverviewPage.requestPremiumChangeLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Request Premium Change link displayed", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on request premium Change link
			accountOverviewPage.requestPremiumChangeLink.scrollToElement();
			accountOverviewPage.requestPremiumChangeLink.click();
			premiumAdjustmentRequestPage.waitTime(1);
			Assertions.verify(premiumAdjustmentRequestPage.rpcUpdateBtn.checkIfElementIsDisplayed(), true,
					"Premium Adjustment Request Page", "Premium Adjustment Request Page Displayed Successfully", false,
					false);
			Assertions.passTest("Account Overview Page", "Clicked on Request Premium change link");

			// Entering Target Premium on Premium Adjustment Request Page
			// Target Premium: $Existing Premium - $1000
			// Insured Name and Email id
			testData = data.get(dataValue5);
			Assertions.verify(premiumAdjustmentRequestPage.rpcUpdateBtn.checkIfElementIsDisplayed(), true,
					"Premium Adjustment Request Page", "Request Premium Page Loaded Successfully ", false, false);
			premiumAdjustmentRequestPage.requestPremiumChanges(testData);
			Assertions.passTest("Account Overview Page", "Clicked on Request Premium change link");

			// Verifying the displayed referral message on the Account Overview page.
			// Msg: Thank you for your referral. Your Underwriting contact has been notified
			// of this request and will get back to you shortly. If you have any questions
			// about this request, please reference the following number:
			Assertions.addInfo("Scenario 03", "Verifying asserting referral message");
			Assertions.verify(accountOverviewPage.requestPremiumChangeReferralMsg.getData().contains(
					"Thank you for your referral. Your Underwriting contact has been notified of this request and will get back to you shortly. If you have any questions about this request, please reference the following number:"),
					true, "Account overview page", "The referral message is "
							+ accountOverviewPage.requestPremiumChangeReferralMsg.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Logout as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Loged out as producer successfully");

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as USM Successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(renewalQuoteNumber);
			Assertions.passTest("Home Page", "Quote searched as a usm successfully");

			// Approve referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// approving referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.waitTillVisibilityOfElement(60);
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

			// Entering wind premium
			premiumReliefDecisionPage.windPremium.setData(testData.get("WindPremium"));
			premiumReliefDecisionPage.windPremium.tab();

			// Assert the updated value displayed under Offered/Approved Premium field.
			// Fetching Values of Requested Premiun , AOP and Liability Premium From Premium
			// Relief Decision Page
			String RnlOrgAopPremium1 = premiumReliefDecisionPage.orgAopPremium.getData().replace("$", "");
			double rnlOrgAopPremium1 = Double.parseDouble(RnlOrgAopPremium1);

			String RnlOrgGLPremium1 = premiumReliefDecisionPage.orgGLPremium.getData().replace("$", "");
			double rnlOrgGLPremium1 = Double.parseDouble(RnlOrgGLPremium1);

			double rnlOrgWindPremium1 = Double.parseDouble(testData.get("WindPremium"));

			// Sum of the Original AOP, Wind and GL/Liability Premium
			double sumOfRnlAopGLandWind1 = rnlOrgAopPremium1 + rnlOrgGLPremium1 + rnlOrgWindPremium1;
			premiumReliefDecisionPage.waitTime(2);
			String RnlofferedOrApprovedPre1 = premiumReliefDecisionPage.offeredOrApprovedPremium.getData();
			double rnlofferedOrApprovedPremium1 = Double.parseDouble(RnlofferedOrApprovedPre1);

			Assertions.addInfo("Scenario 05", "Verifying Offered/Approved Premium is equal to sum Of Aop,GL and Wind");
			if (Precision.round(Math
					.abs(Precision.round(rnlofferedOrApprovedPremium1, 2) - Precision.round(sumOfRnlAopGLandWind1, 2)),
					2) < 0.01) {
				Assertions.passTest("Referral Approve/Decline Page",
						"Offered Or Approved Premium: " + "$" + Precision.round(rnlofferedOrApprovedPremium1, 2));
				Assertions.passTest("Referral Approve/Decline Page",
						"Sum Of Aop GL and Wind: " + "$" + Precision.round(sumOfRnlAopGLandWind1, 2));
			} else {
				Assertions.verify(Precision.round(rnlofferedOrApprovedPremium1, 2),
						Precision.round(sumOfRnlAopGLandWind1, 2), "Referral Approve/Decline Page",
						"The Actaul and Calculated Wind, AOP and GL Values are not the Same", false, false);
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			approveDeclineQuotePage.internalUnderwriterComments.setData(testData.get("PRCInternalUWComments"));
			approveDeclineQuotePage.externalUnderwriterComments.setData(testData.get("PRCExternalUWComments"));
			// Clicking on Decline button
			approveDeclineQuotePage.approveButton.scrollToElement();
			approveDeclineQuotePage.approveButton.click();
			referralPage.close.scrollToElement();
			referralPage.close.click();

			// Search renewal quote
			homePage.searchQuote(renewalQuoteNumber);

			// Assert the Premium requested for change is updated on the Account
			// Overview page
			String RnlOfferedApprovedQuotePre = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",",
					"");
			double rnlOfferedApprovedQuotePremium = Double.parseDouble(RnlOfferedApprovedQuotePre);
			Assertions.addInfo("Scenario 06",
					"Verifying Offered/Approved  premium is updated on the Account Overview page");
			if (Precision.round(Math.abs(
					Precision.round(rnlOfferedApprovedQuotePremium, 2) - Precision.round(sumOfRnlAopGLandWind1, 2)),
					2) < 0.01) {
				Assertions.passTest("Account Overview Page",
						"OfferedOrApproved Quote Premium: " + "$" + Precision.round(rnlOfferedApprovedQuotePremium, 2));
				Assertions.passTest("Premium Relief Decision Page",
						"Sum Of Aop GL and Wind: " + "$" + Precision.round(sumOfRnlAopGLandWind1, 2));
			} else {
				Assertions.verify(Precision.round(rnlOfferedApprovedQuotePremium, 2),
						Precision.round(sumOfRnlAopGLandWind1, 2), "Referral Approve/Decline Page",
						"The Actaul and Calculated Wind, AOP and GL Values are not the Same", false, false);
			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("MATC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("MATC003 ", "Executed Successfully");
			}
		}
	}

}
