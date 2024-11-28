/** Program Description: Adding the ticket IO-20474 and Check the premium adjustment that is applied to the  Renewal quote will apply to the endorsement.
 *  Author			   : Sowndarya
 *  Date of Creation   : 05/09/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC056 extends AbstractCommercialTest {

	public TC056() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID056.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		// String premiumFee;
		String premiumFeeTx;
		String premiumFeeAnnual;
		// double calPremiumFeeAnnual;
		String premiumFeeEndTx;
		String premiumFeeEndAnnual;
		Map<String, String> testData = data.get(data_Value1);
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
			Assertions.passTest("Building page", "Building details entered successfully");

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

			// click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

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
			Assertions.addInfo("Policy Summary Page", "Renew NB Policy");
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// Click on create another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create another quote");

			// click on get a quote
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get a quote button");

			// getting renewal re-quote number
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Requote Number :  " + quoteNumber);

			// Check if Issue Button is displayed and Request Bind Button is not displayed.
			Assertions.addInfo("Scenario 01",
					"Verifying if Issue Quote Button is displayed and Request Bind Button is not displayed.");
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Issue Quote Button displayed is verified", false, false);
			Assertions.verify(
					accountOverviewPage.requestBind.checkIfElementIsPresent()
							&& accountOverviewPage.requestBind.checkIfElementIsDisplayed(),
					false, "Account Overview Page", "Request Bind Button not displayed is verified", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on issue quote button
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Issue Quote button");

			// check if Request Bind Button is displayed and Issue Quote Button is not
			// displayed.
			Assertions.addInfo("Scenario 02",
					"Verifying if Request Bind Button is displayed and Issue Quote Button is not displayed.");
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsPresent(), false,
					"Account Overview Page", "Issue Quote Button not displayed is verified", false, false);
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Request Bind Button displayed is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);

			// Enter renewal bind details
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home page", "Quote for referral is searched successfully");

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral page",
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

			// Get policy number from policy summary page
			Assertions.passTest("Policy summary page", "Policy summary page loaded successfully");
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.verify(policySummarypage.policyNumber.getData().contains(policyNumber), true,
					"Policy summary page", "Renewal policy number is " + policyNumber, false, false);

			// click on Endorse PB link
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// click on ok
			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Click on edit/building information link
			testData = data.get(data_Value2);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();
			buildingPage.constructionTypeArrow.scrollToElement();
			buildingPage.constructionTypeArrow.click();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).scrollToElement();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).click();
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();
			Assertions.passTest("Building Page", "Building details entered successfully");

			// Click on continue endorsement
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote page loaded successfully", false, false);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on continue endorsement button successfully");

			// Click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorsement Page", "Clicked on next button successfully");

			// Getting transaction premium and annual premium from endorse policy page and
			// calculating
			premiumFeeEndTx = endorsePolicyPage.transactionPremium.getData().replace("$", "").replace(",", "");
			premiumFeeEndAnnual = endorsePolicyPage.transactionPremiumFee.formatDynamicPath(3).getData()
					.replace("$", "").replace(",", "");

			// Click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			// Click on close button
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// Click on endorsement transaction type link
			policySummarypage.endorsementTransactionType.scrollToElement();
			policySummarypage.endorsementTransactionType.click();
			Assertions.passTest("Policy Summary Page", "Clicked on endorsement transaction type link successfully");

			// Verifying transaction premium fee from endorse policy page and transaction
			// premium fee from policy summary page
			premiumFeeTx = policySummarypage.PremiumFee.formatDynamicPath(1).getData().replace("$", "").replace(",", "")
					.replace(".00", "");
			Assertions.addInfo("Scenario 08", "Verifying transaction premium fee");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(premiumFeeTx), 2)
					- Precision.round(Double.parseDouble(premiumFeeEndTx), 2)), 2) < 1.00) {
				Assertions.passTest("Policy Summary Page",
						"Actual and Expected transaction premium fee bothe are same actual transaction premium fees is $"
								+ premiumFeeTx);
				Assertions.passTest("Policy Summary Page", "Expected transaction premium fee is $" + premiumFeeEndTx);

			} else {
				Assertions.verify(premiumFeeTx, premiumFeeEndTx, "Policy Summary Page",
						"Actual and Expected transaction premium fees are not matching", false, false);
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Verifying and asserting actual and calculated annual premium fee from policy
			// summary page
			premiumFeeAnnual = policySummarypage.PremiumFee.formatDynamicPath(2).getData().replace("$", "")
					.replace(",", "").replace(".00", "");
			Assertions.addInfo("Scenario 09", "Verifying annual premium fee");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(premiumFeeAnnual), 2)
					- Precision.round(Double.parseDouble(premiumFeeEndAnnual), 2)), 2) < 1.00) {
				Assertions.passTest("Policy Summary Page",
						"Actual and Expected annual premium fee bothe are same actual annual premium fees is $"
								+ premiumFeeAnnual);
				Assertions.passTest("Policy Summary Page", "Expected annual premium fee is $" + premiumFeeEndAnnual);

			} else {
				Assertions.verify(premiumFeeAnnual, premiumFeeEndAnnual, "Policy Summary Page",
						"Actual and Expected annual premium fees are not matching", false, false);
			}
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC056 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC056 ", "Executed Successfully");
			}
		}
	}
}
