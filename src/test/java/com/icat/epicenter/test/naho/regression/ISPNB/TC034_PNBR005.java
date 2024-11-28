package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC034_PNBR005 extends AbstractNAHOTest {

	public TC034_PNBR005() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBR005.xls";
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
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();

		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData1 = data.get(data_Value2);
		Map<String, String> testData2 = data.get(data_Value3);
		Map<String, String> testData3 = data.get(data_Value4);
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
			requestBindPage.enterBindDetailsNAHO(testData);

			// Clicking on homepage button
			/*
			 * homePage.goToHomepage.scrollToElement(); homePage.goToHomepage.click();
			 * homePage.searchReferral(quoteNumber); Assertions.passTest("Home Page",
			 * "Quote for referral is searched successfully");
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

			// Validating the premium amount
			String originalPolicyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is " + originalPolicyNumber, false, false);

			// Assert the NB details in policy snapshot
			policySummaryPage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("9").getData().contains("HO-3"),
					true, "View Policy Snapshot",
					"Form type value for NB is: "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("9").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues
							.formatDynamicPath("19").getData().contains(testData.get("NamedStormValue")),
					true, "View Policy Snapshot",
					"Named Storm value for NB is: "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("19").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues
							.formatDynamicPath("10").getData().contains(testData.get("L1D1-DwellingCovA")),
					true, "View Policy Snapshot",
					"CoverageA value for NB is: "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("10").getData(),
					false, false);
			Assertions.verify(viewPolicySnapShot.insuredDetails.getData().contains(testData.get("InsuredName")), true,
					"View Policy Snapshot", "Insured Name value for NB is: " + testData.get("InsuredName"), false,
					false);
			Assertions.addInfo("Scenario 1", "Ended");
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(3);
			viewPolicySnapShot.goBackButton.click();

			// Process PB Endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			policySummaryPage.endorsePB.waitTillInVisibilityOfElement(60);

			// Update coverage details
			endorsePolicyPage.enterEndorsement_DetailsNAHO(testData1, testData.get("ProductSelection"));
			Assertions.passTest("Endorse Policy Page", "Endorsement details entered successfully");

			// Click on endorsement transaction
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on endorsement transaction");

			// Assert the PB endorsement changes in policy snapshot
			policySummaryPage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();

			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues
							.formatDynamicPath("19").getData().contains(testData1.get("NamedStormValue")),
					true, "View Policy Snapshot",
					"Named Storm value After PB endorsement is: "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("19").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues
							.formatDynamicPath("10").getData().contains(testData1.get("L1D1-DwellingCovA")),
					true, "View Policy Snapshot",
					"CoverageA value After PB endorsement is: "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("10").getData(),
					false, false);
			Assertions.addInfo("Scenario 2", "Ended");
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(3);
			viewPolicySnapShot.goBackButton.click();

			// Process NPB Endorsement
			policySummaryPage.endorseNPB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			policySummaryPage.endorseNPB.waitTillInVisibilityOfElement(60);

			// Update coverage details
			endorsePolicyPage.enterEndorsement_DetailsNAHO(testData2, testData.get("ProductSelection"));
			Assertions.passTest("Endorse Policy Page", "Endorsement details entered successfully");

			// Click on endorsement transaction
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on endorsement transaction");

			// Assert the endorsement changes in policy snapshot
			policySummaryPage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.verify(viewPolicySnapShot.insuredDetails.getData().contains(testData2.get("InsuredName")), true,
					"View Policy Snapshot",
					"Insured Name value after NPB endorsement is: " + testData2.get("InsuredName"), false, false);
			Assertions.addInfo("Scenario 3", "Ended");

			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(3);
			viewPolicySnapShot.goBackButton.click();

			// Click on rewrite
			policySummaryPage.rewritePolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();

			// Create another quote and update coverage values
			accountOverviewPage.createAnotherQuote.click();
			BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();

			// Click on Override Button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData3);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Bind rewrite quote
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen1 = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber1 = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen1 - 1);
			Assertions.passTest("Account Overview Page", "Rewrite Quote Number is : " + quoteNumber1);

			// Binding the quote and creating the policy
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();

			// Entering bind details
			requestBindPage.enteringRewriteDataNAHO(testData3);
			Assertions.passTest("Request Bind Page", "Rewritten policy details entered successfully");

			// Asserting New policy number
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(originalPolicyNumber), false,
					"Policy Summary Page",
					"Updated policy number for Rewritten Policy is: " + policySummaryPage.policyNumber.getData(), false,
					false);

			// Asserting endorsement updates on NB policy are not applied to Rewritten
			// policy
			policySummaryPage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("9").getData().contains("HO-3"),
					true, "View Policy Snapshot",
					"Form type value in Rewritten policy is: "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("9").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues
							.formatDynamicPath("19").getData().contains(testData1.get("NamedStormValue")),
					false, "View Policy Snapshot",
					"Named Storm value in Rewritten policy is: "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("19").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues
							.formatDynamicPath("10").getData().contains(testData.get("L1D1-DwellingCovA")),
					true, "View Policy Snapshot",
					"CoverageA value in Rewritten policy is: "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("10").getData(),
					false, false);
			Assertions.verify(viewPolicySnapShot.insuredDetails.getData().contains(testData.get("InsuredName")), true,
					"View Policy Snapshot", "Insured Name value in Rewritten policy is: " + testData.get("InsuredName"),
					false, false);
			Assertions.addInfo("Scenario 4", "Ended");
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(3);
			viewPolicySnapShot.goBackButton.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC034 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC034 ", "Executed Successfully");
			}
		}
	}
}
