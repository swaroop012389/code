package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC044_PNBCR010 extends AbstractNAHOTest {

	public TC044_PNBCR010() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBCR010.xls";
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
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();

		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData1 = data.get(data_Value2);
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

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

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
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			/*
			 * homePage.goToHomepage.scrollToElement(); homePage.goToHomepage.click();
			 * homePage.searchQuote(quoteNumber); Assertions.passTest("Home Page",
			 * "Quote for referral is searched successfully");
			 *
			 * accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
			 * accountOverviewPage.openReferralLink.scrollToElement();
			 * accountOverviewPage.openReferralLink.click();
			 *
			 * // Approve Referral Assertions.verify(referralPage.approveOrDeclineRequest.
			 * checkIfElementIsDisplayed(), true, "Referral Page",
			 * "Referral page loaded successfully", false, false);
			 * referralPage.clickOnApprove(); Assertions.passTest("Referral Page",
			 * "Referral request approved successfully");
			 * Assertions.verify(approveDeclineQuotePage.approveButton.
			 * checkIfElementIsDisplayed(), true, "Approve Decline Quote Page",
			 * "Approve Decline Quote page loaded successfully", false, false);
			 * approveDeclineQuotePage.clickOnApprove();
			 * Assertions.passTest("Approve Decline Quote Page",
			 * "Bind Request approved successfully");
			 */

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Asserting premium for NB policy
			policySummaryPage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			String originalPremium = viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("1").getData();
			Assertions.passTest("View Policy Snapshot", "Original Premium value is: " + originalPremium);
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(3);
			viewPolicySnapShot.goBackButton.click();

			// Click on cancel policy link
			policySummaryPage.cancelPolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			policySummaryPage.waitTime(3);
			// Processing cancellation
			cancelPolicyPage.enterCancellationDetails(testData1);

			// Asserting Policy Status
			Assertions.verify(policySummaryPage.policyStatus.getData().contains("Cancelled"), true,
					"Policy Summary Page", "Policy Status is: " + policySummaryPage.policyStatus.getData(), false,
					false);

			// Asserting Premium for policy after cancellation
			policySummaryPage.transHistReason.formatDynamicPath("3").waitTillVisibilityOfElement(60);
			policySummaryPage.transHistReason.formatDynamicPath("3").scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath("3").click();
			policySummaryPage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			String premiumAfterCancellation = viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("1")
					.getData();
			Assertions.passTest("View Policy Snapshot Page", "Premium After Cancellation: " + premiumAfterCancellation);
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(3);
			viewPolicySnapShot.goBackButton.click();

			// Reinstate policy
			policySummaryPage.reinstatePolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();

			// Click on Ok Button in Late reinstatement page
			if (reinsatePolicyPage.okButton.checkIfElementIsPresent()
					&& reinsatePolicyPage.okButton.checkIfElementIsDisplayed()) {
				reinsatePolicyPage.okButton.scrollToElement();
				reinsatePolicyPage.okButton.click();
			}
			reinsatePolicyPage.enterReinstatePolicyDetails(testData1);

			// Asserting policy status after reinstatement
			Assertions.verify(policySummaryPage.policyStatus.getData().contains("Active"), true, "Policy Summary Page",
					"Policy Status is: " + policySummaryPage.policyStatus.getData(), false, false);

			// Asserting premium after reinstatement
			policySummaryPage.transHistReason.formatDynamicPath("4").waitTillVisibilityOfElement(60);
			policySummaryPage.transHistReason.formatDynamicPath("4").scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath("4").click();
			policySummaryPage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummaryPage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues
							.formatDynamicPath("1").getData().contains(originalPremium),
					true, "View Policy Snapshot Page",
					"Policy Premium is: "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("1").getData(),
					false, false);
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(3);
			viewPolicySnapShot.goBackButton.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC044 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC044 ", "Executed Successfully");
			}
		}
	}
}
