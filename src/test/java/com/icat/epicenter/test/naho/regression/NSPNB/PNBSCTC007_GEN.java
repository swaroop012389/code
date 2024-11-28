/** Program Description :1) Create a New Business Policy with the below details---SCNH2181128
a) Year Built: 1969
b) Short Term Rental: Yes
c) Coverage A: 1,000,000
d) Prior Loss:
            Prior Loss Type: Earth Quake (Earth Movement)
            Prior Loss Date: 01/01/2022
            Prior Loss Amount: $ 1200.00
            Damages Repaired: Yes
            Claims Open: No
2) Add Expacc Details and Release To Producer.
3) Login as Producer [hiho1@test.com], click Edit Deductibles and Limits and Update
a)  Coverage A to 149,999 and click Get A Quote. Check if the message "Coverage A less than minimum limit of $150000" is displayed with hardstop
b) Again update Coverage A from 149,999 to 299,999 and click Get A Quote. Check if the message "The quoted building has a Coverage A
limit of less than $300,000 and is ineligible for coverage." is displayed with Hard Stop.
4)"The quoted building has a Coverage C limit greater than 70% of Coverage A and requires further review by an ICAT Online Underwriter."
5)"The quoted building has a Coverage D limit greater than 40% of Coverage A and requires further review by an ICAT Online Underwriter."
*  Author			    : Sowndarya NH
*  Date of Creation    : 26/05/2022
**/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.util.List;
import java.util.Map;

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
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBSCTC007_GEN extends AbstractNAHOTest {

	public PNBSCTC007_GEN() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/SC007.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value5 = 4;

		String quoteNumber;
		String policyNumber;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Getting the Quite number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request bind button");

			// Entering Underwriting question details
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Values Entered Successfully");

			// Approve bind request
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfully");

			// approving referral
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Asserting Policy Number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policyNumber, false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on Expacc link
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			Assertions.passTest("Home Page", "Clicked on Expacc link");

			// Enter Expacc details
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Performing Renewal Searches
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy " + policyNumber + " successfully");

			// clicking on renewal policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew policy link");

			// Getting renewal quote number
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);

			// Approve referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				Assertions.verify(
						referralPage.pickUp.checkIfElementIsPresent()
								|| referralPage.approveOrDeclineRequest.checkIfElementIsPresent(),
						true, "Referral Page", "Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
				referralPage.close.scrollToElement();
				referralPage.close.click();
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);
				Assertions.addInfo("Home Page", "Quote searched successfully");
			}

			// Getting renewal quote number
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + quoteNumber);

			// Click on release renewal to producer button
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release renewal to producer button");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// Search the renewal quote
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home Page loaded successfully", false, false);
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Searched the renewal quote " + quoteNumber + " successfully");

			// Click on editDeductibleAndLimits
			Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit deductibles and limits");

			// Enter Coverage A as 149,999
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			testData = data.get(data_Value2);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page",
					"Updated the Coverage A Value as " + "$" + createQuotePage.coverageADwelling.getData());

			// Click on Get a quote button
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get a quote button");

			// Verify the Hard stop error message when Coverage A = $149,999
			Assertions.addInfo("Scenario 01",
					"Verifying the Hardstop error message when Coverage A = $149,999 for producer");
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("Coverage A less than minimum limit").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Hardstop Error message " + createQuotePage.warningMessages
							.formatDynamicPath("Coverage A less than minimum limit").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Change the Coverage A as 299,999
			testData = data.get(data_Value3);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page",
					"Updated the Coverage A Value as " + "$" + createQuotePage.coverageADwelling.getData());

			// Click on Get a quote button
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get a quote button");

			// Verify the Hard stop error message when Coverage A = $299,999
			Assertions.addInfo("Scenario 02",
					"Verifying the Hardstop error message when Coverage A = $299,999 for producer");
			Assertions.verify(createQuotePage.covAMinimumReferralMessage.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "The Hardstop Error message "
							+ createQuotePage.covAMinimumReferralMessage.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Verifying hard stop message When Cov C that exceeds 70% of Coverage A value
			// and Coverage D that exceeds 40% of Coverage A value
			Assertions.addInfo("Scenario 03",
					"Verifying the Hard stop error message when Coverag C that exceeds 70% of Coverage A value and Coverage D that exceeds 40% of Coverage A value for producer");
			Assertions.verify(createQuotePage.warningMessages
					.formatDynamicPath("Coverage D limit greater than 40% of Coverage A").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Hardstop Error message "
							+ createQuotePage.warningMessages
									.formatDynamicPath("Coverage D limit greater than 40% of Coverage A").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(createQuotePage.warningMessages
					.formatDynamicPath("Coverage C limit greater than 70% of Coverage A").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Hardstop Error message "
							+ createQuotePage.warningMessages
									.formatDynamicPath("Coverage C limit greater than 70% of Coverage A").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Verifying default value of ordinance law = 10% when year built = 1970 for
			// producer
			testData = data.get(data_Value5);
			Assertions.addInfo("Scenario 04", "Verifying default value of ordinance law = 10% when year built = 1970");
			Assertions
					.verify(createQuotePage.ordinanceOrLawDedValue.getData().contains(testData.get("OrdinanceOrLaw")),
							true, "Create Quote Page", "The default ordinance law value is "
									+ createQuotePage.ordinanceOrLawDedValue.getData() + " displayed verified",
							false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBSCTC007 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBSCTC007 ", "Executed Successfully");
			}
		}
	}
}
