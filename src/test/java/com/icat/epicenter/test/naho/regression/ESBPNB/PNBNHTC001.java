/** Program Description: Creating Rewrite policy and verifying SLTF on rewrite policy for New Hampshire state
 *  Author			   : Pavan Mule
 *  Date of Creation   : 12/30/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBNHTC001 extends AbstractNAHOTest {

	public PNBNHTC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/NHTC001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
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
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		testData = data.get(dataValue1);
		String quoteNumber;
		String policyNumber;
		String premiumAmount;
		String policyFees;
		String actualSLTF;
		String sltfPercentage;
		double d_premiumAmount;
		double d_policyFees;
		double d_calculatedSLTF;
		double d_sltfPercentage;
		double d_actualSLTF;
		String surplusContribution;
		double d_surplusContribution;
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling page",
					"Dwelling page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
						"Prior loss page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting questions page", "Underwriting questions details entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.waitTillButtonIsClickable(60);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind page loaded successfully", false, false);
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Click on Rewrite link
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy summary page", "Cliked on Rewrite link successfully");

			// Click on create another quote
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}
			Assertions.passTest("Account overview page", "Clicked on create another quote successfully");

			// Updating Named storm deductible = 1% to 10% and AOP Deductible as $5,000 to
			// $50,000 in create quote page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			testData = data.get(dataValue1);
			Assertions.passTest("Create quote page",
					"Original named storm deductible = " + testData.get("NamedStormValue"));
			Assertions.passTest("Create quote page", "Original AOP deductible = " + testData.get("AOPDeductibleValue"));
			testData = data.get(dataValue2);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote page",
					"Latest named storm deductible = " + testData.get("NamedStormValue"));
			Assertions.passTest("Create quote page", "Latest AOP deductible = " + testData.get("AOPDeductibleValue"));
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Click on view/print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.waitTillPresenceOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account overview page", "Clicked on view print full quote link successfully");

			// Asserting actual premium,inspection fee and SLTF value from view print full
			// quote page
			premiumAmount = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			policyFees = viewOrPrintFullQuotePage.policyFeeNaho.getData().replace("$", "");
			actualSLTF = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace("$", "");
			surplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
					.replace("%", "");
			d_actualSLTF = Double.parseDouble(actualSLTF);
			sltfPercentage = testData.get("SLTFPercentage");
			Assertions.addInfo("View print full quote page",
					"Asserting Premium, Icat fees, SLTF on account overview page");
			Assertions.passTest("View print full quote page", "Premium is $" + premiumAmount);
			Assertions.passTest("View print full quote page", "Policy fees is $" + policyFees);

			// Converting string values to double
			d_premiumAmount = Double.parseDouble(premiumAmount);
			d_policyFees = Double.parseDouble(policyFees);
			d_sltfPercentage = Double.parseDouble(sltfPercentage);
			d_surplusContribution = Double.parseDouble(surplusContribution);

			// Calculating SLTF value (premium+policy fee)/3%(excluding inspection fees)
			d_calculatedSLTF = (d_premiumAmount + d_policyFees + d_surplusContribution) * (d_sltfPercentage / 100);

			// Verifying Actual SLTF and Calculated SLTF Values on View print full quote
			// page
			Assertions.addInfo("Scenario 01",
					"Verifying actual SLTF and calculated SLTF values on View print full quote page");
			if (Precision.round(Math.abs(Precision.round(d_actualSLTF, 2) - Precision.round(d_calculatedSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("View print full quote page",
						"Calculated surplus lines taxes and fees : " + "$" + Precision.round(d_calculatedSLTF, 2));
				Assertions.passTest("View print full quote page",
						"Actual surplus lines taxes and fees : " + "$" + d_actualSLTF);
			} else {
				Assertions.verify(d_actualSLTF, d_calculatedSLTF, "Account Overview Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click rewrite bind link on account overview page
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.passTest("Account overview page", "Clicked on rewrite bind button successfully");

			// Enter rewrite bind details
			testData = data.get(dataValue1);
			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enteringRewriteDataNAHO(testData);
			Assertions.passTest("Request bind page", "Rewrite bind details entered successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully. Rewrite policyNumber is : " + policyNumber, false, false);

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBNHTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBNHTC001 ", "Executed Successfully");
			}
		}
	}
}
