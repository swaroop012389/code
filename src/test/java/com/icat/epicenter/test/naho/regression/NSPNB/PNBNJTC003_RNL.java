/** Program Description: Asserting warning messages for rewrite and cancel operations when renewal is not released to producer and IO-21801
 *  Author			   : Priyanka
 *  Date of Creation   : 05/26/2021
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBNJTC003_RNL extends AbstractNAHOTest {

	public PNBNJTC003_RNL() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/NJ003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing variables
		int data_Value1 = 0;
		String policyNumber;
		String covAvalue;
		String inflationGuardPercentage;
		double calCovAValue;
		String actualCovAValue;
		String calCovAValue_s;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Create account as USM
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page ",
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

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page ",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Named storm Default Value Assertion
			Assertions.addInfo("Scenario 01", "Asserting Named Strom Default Value in Create A Quote page");
			Assertions.verify(createQuotePage.namedStormData.checkIfElementIsPresent(), true, "Create A Quote Page ",
					"Named Strom Value : " + createQuotePage.namedStormData.getData()
							+ " is displayed with Default %1 Value",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page ", "Quote details entered successfully");
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			// getting Quote number1
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account Overview Page loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page ", "Quote Number1 is : " + quoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page ", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			Assertions.passTest("Underwriting Questions Page ",
					"Underwriting Questions selected as answer No To AllQuestions");

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			testData = data.get(data_Value1);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page ",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
					&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchReferral(quoteNumber);
				referralPage.clickOnApprove();
				requestBindPage.approveRequestNAHO(testData);
			}

			// Getting the policy number
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			Assertions.passTest("Policy Summary Page", "PolicyNumber is " + policySummaryPage.policyNumber.getData());

			// Click on Renew Policy Link
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
			Assertions.passTest("Policy Summary Page ", "Renew Policy Button is Clicked");

			// Getting renewal quote
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal quote number " + quoteNumber);

			// Adding IO-21801
			// Calculating inflation guard applying to Cov A
			covAvalue = testData.get("L1D1-DwellingCovA");
			inflationGuardPercentage = testData.get("InflationGuardPercentage");
			calCovAValue = (Double.parseDouble(covAvalue) * Double.parseDouble(inflationGuardPercentage));
			Assertions.passTest("View/Print Full Quote Page",
					"NB Coverage A value " + testData.get("L1D1-DwellingCovA"));
			Assertions.passTest("View/Print Full Quote Page",
					"Inflation Guard Percentage " + testData.get("InflationGuardPercentage"));

			// Rounding off
			long roundCalCovAValue = Math.round(calCovAValue);

			// Converting double to string
			calCovAValue_s = Double.toString(roundCalCovAValue).replace(".0", "");

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("View/Print Full Quote Page", "Clicked on vieworprint full quote link");

			// Verifying and Asserting actual and calculated Cov A value
			// Cov A = Cov A*inflationguard%(1.0713)
			Assertions.addInfo("Scenario 02", "Verifying inflation guard applied to renewal Cov A");
			actualCovAValue = viewOrPrintFullQuotePage.coveragesValues.formatDynamicPath(4).getData().replace(",", "");
			Assertions.verify(actualCovAValue, "$" + calCovAValue_s, "View Print Full Quote Page",
					"Inflation guard " + testData.get("InflationGuardPercentage")
							+ " applied to Coverage A, actual coverage A value " + actualCovAValue
							+ " and calculated coverage A value $" + calCovAValue_s + " bothe are same",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			// IO-21801 Ended

			// Adding below if condition for referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Approve Referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Referral page
				Assertions.passTest("Referral Page", "Quote referral approved successfully");
				approveDeclineQuotePage.clickOnApprove();

				referralPage.close.scrollToElement();
				referralPage.close.click();
				homePage.searchQuote(quoteNumber);
			}

			// Named strom Default Value Assertion
			Assertions.addInfo("Scenario 03", "Asserting the presence of Release Renewal To Producer Button");
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsPresent(), true,
					"Account OverView Page ", "Release Renewal to Producer Button is displayed", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on View Previous Policy Button
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page ", "View Previous Policy Button is Clicked");

			// Click on Rewrite the policy HyperLink
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page ", "Rewrite Policy Button is Clicked");

			// Delete and Continue Button Assertion
			Assertions.addInfo("Scenario 04", "Asserting the presence of Delete and Continue Button");
			Assertions.verify(policyRenewalPage.deleteAndContinue.checkIfElementIsPresent(), true,
					"Polict Renewal Page ", "Delete and Continue Button is displayed", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on Delete and Continue Button
			policyRenewalPage.deleteAndContinue.scrollToElement();
			policyRenewalPage.deleteAndContinue.click();
			Assertions.passTest("Policy Renewal Page ", "Delete and Continue Button is Clicked");

			// Account Overview page , click on view Rewriting Policy Button
			accountOverviewPage.viewRewritingPolicy.scrollToElement();
			accountOverviewPage.viewRewritingPolicy.click();
			Assertions.passTest("Account Overview Page ", "View Rewriting Button is Clicked");

			// Policy Summary Page , Click on Stop Policy Rewrite Button
			policySummaryPage.stopPolicyRewrite.scrollToElement();
			policySummaryPage.stopPolicyRewrite.click();
			Assertions.passTest("Policy Summary Page ", "Stop Policy Rewrite is Clicked");

			// Policy Summary Page , Click on Renew Policy Link
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
			Assertions.passTest("Policy Summary Page ", "Renew Policy is Clicked");

			// Adding below if condition for referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				quoteLen = accountOverviewPage.quoteNumber.getData().length();
				quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
				Assertions.passTest("Account overview page", "Renewal quote number " + quoteNumber);
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Approve Referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Referral page
				Assertions.passTest("Referral Page", "Quote referral approved successfully");
				approveDeclineQuotePage.clickOnApprove();

				referralPage.close.scrollToElement();
				referralPage.close.click();
				homePage.searchQuote(quoteNumber);
			}

			// Account Overview Page , Click on Release Renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page ", "Release Renewal to Producer Button is Clicked");

			// Account Overview Page , Click on View Previous Policy button
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page ", "View Previous Policy Button  is Clicked");

			// Policy Summary Page , click on Cancel Policy Link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page ", "Cancel Policy Button is Clicked");

			// Delete and Continue Button Assertion
			Assertions.addInfo("Scenario 05", "Asserting the presence of Lapse and Continue Button");
			Assertions.verify(policyRenewalPage.lapseAndContinue.checkIfElementIsPresent(), true,
					"Polict Renewal Page ", "Lapse and Continue Button is displayed", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on Delete and Continue Button
			policyRenewalPage.lapseAndContinue.scrollToElement();
			policyRenewalPage.lapseAndContinue.click();
			Assertions.passTest("Policy Renewal Page ", "Lapse and Continue Button is Clicked");

			// Cancel Policy Page
			cancelPolicyPage.enterCancellationDetails(testData);
			Assertions.passTest("Cancel Policy Page ", "Cancel details entered successfully");

			// Policy Summary Page , Click View Active renewal
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy Summary Page ", "View Active Renewal is Clicked");

			// Click Unlapse Renewal Button
			Assertions.addInfo("Scenario 06", "Asserting the presence of UnLapse Renewal Button");
			Assertions.verify(accountOverviewPage.unlapseRenewal.checkIfElementIsPresent(), true,
					"Account Overview Page ", "Unlapse Button is displayed", false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on Delete and Continue Button
			accountOverviewPage.unlapseRenewal.scrollToElement();
			accountOverviewPage.unlapseRenewal.click();
			Assertions.passTest("Account Overview Page ", "UnLapse Renewal Button is Clicked");

			// Click on View Renewal Documents Button
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();
			Assertions.passTest("Account Overview Page ", "View Renewal Documents Button is Clicked");

			// Validation of presence of lapse document
			Assertions.verify(
					policyDocumentsPage.policyDocuments.formatDynamicPath("LAPSE").checkIfElementIsDisplayed(), true,
					"Policy Documents Page", "Presence of Lapse notice for Insured is verified ", true, true);
			Assertions.verify(
					policyDocumentsPage.policyDocuments.formatDynamicPath("LAPSE").checkIfElementIsDisplayed(), true,
					"Policy Documents Page", "Presence of Lapse notice for Producer is verified ", true, true);

			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();

			// Click on Edit Deductibles and Limits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page ", "Edit Deductible Button is Clicked");

			// Named strom Default Value Assertion
			Assertions.addInfo("Scenario 07", "Asserting Named Strom Default Value in Create A Quote page");
			Assertions.verify(createQuotePage.namedStormData.checkIfElementIsPresent(), true, "Create A Quote Page ",
					"Named Strom Value : " + createQuotePage.namedStormData.getData()
							+ " is displayed with Default %1 Value",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

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

			// Click Issue Quote Button
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page ", "Issue Quote Button is Clicked");

			// Entering bind details
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
			requestBindPage.renewalRequestBindNAHO(testData);
			Assertions.passTest("Request Bind Page", "Renewal bind details entered successfully");

			// getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Renewal Policy Number is : " + policyNumber);

			// SignOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBNJTC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBNJTC003 ", "Executed Successfully");
			}
		}
	}
}
