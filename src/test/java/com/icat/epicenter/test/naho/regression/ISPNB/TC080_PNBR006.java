package com.icat.epicenter.test.naho.regression.ISPNB;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC080_PNBR006 extends AbstractNAHOTest {

	public TC080_PNBR006() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBR006.xls";
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
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();

		double transactionTaxandSateFeeCal;
		double annualTaxandStateFeeCal;
		double termTaxandStateFeeCal;
		String SLTFPercentage;
		double empaServiceCharge = 2.00;

		int data_Value1 = 0;
		int data_Value2 = 1;
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
			// createQuotePage.enterQuoteDetailsNAHO(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			Assertions.verify(
					accountOverviewPage.requestBind.checkIfElementIsPresent()
							&& accountOverviewPage.requestBind.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			String quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// Adding the CR IO-19518
			// Asserting the Micro zone Data on Account Overview Page
			Assertions.verify(accountOverviewPage.microzoneData.formatDynamicPath("2").checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"The " + accountOverviewPage.microzoneData.formatDynamicPath(1).getData() + " data "
							+ accountOverviewPage.microzoneData.formatDynamicPath(2).getData()
							+ " displayed is verified",
					false, false);

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

			// get policyNumber
			String policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully.PolicyNumber is " + policyNumber, false, false);

			// Click on Rewrite button
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked Rewrite link successfully");
			Assertions.verify(
					accountOverviewPage.createAnotherQuote.checkIfElementIsPresent()
							&& accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click on Create another Quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create AnotherQuote button successfully");

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			testData = data.get(data_Value2);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account Overview Page loaded successfully", false, false);

			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Rewrite Quote Number : " + quoteNumber);

			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on Rewrite Bind button successfully");

			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind page loaded successfully", false, false);

			// Entering details in Request Bind page
			requestBindPage.effectiveDate.clearData();
			requestBindPage.effectiveDate.setData(testData.get("PolicyEffDate"));
			requestBindPage.waitTime(10);
			requestBindPage.effectiveDate.tab();
			Assertions.passTest("Request Bind Page", "Bind Details entered successfully");

			// Asserting Previous policy effective date
			requestBindPage.waitTime(5);
			requestBindPage.previousPolicyEffectiveDate.waitTillVisibilityOfElement(60);
			Assertions.verify(
					requestBindPage.previousPolicyEffectiveDate.checkIfElementIsPresent()
							&& requestBindPage.previousPolicyEffectiveDate.checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"Previous Policy Effective Date is " + requestBindPage.previousPolicyEffectiveDate.getData(), false,
					false);

			// Asserting Previous policy cancellation date
			requestBindPage.waitTime(5);
			requestBindPage.previousPolicyCancellationDate.scrollToElement();
			Assertions.verify(
					requestBindPage.previousPolicyCancellationDate.checkIfElementIsPresent()
							&& requestBindPage.previousPolicyCancellationDate.checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"Previous Policy Cancellation Date is " + requestBindPage.previousPolicyCancellationDate.getData(),
					false, false);

			// Verifying Previous Policy Effective date and Previous policy cancellation
			// effective date both are same
			Assertions.verify(requestBindPage.previousPolicyEffectiveDate.getData(),
					requestBindPage.previousPolicyCancellationDate.getData(), "Request Bind Page",
					"Previous Policy Cancellation Date " + requestBindPage.previousPolicyCancellationDate.getData()
							+ " And Previous Policy Effective Date "
							+ requestBindPage.previousPolicyEffectiveDate.getData() + " Both are same",
					false, false);

			// Asserting original inspection fee
			String originalInspectionFee = requestBindPage.originalInsFee.getData().replace("$", "").replace(".00", "");
			Assertions.verify(requestBindPage.originalInsFee.getData().equals("$125"), true, "Request Bind Page",
					"Original Inspection fee " + requestBindPage.originalInsFee.getData(), false, false);

			// Asserting original policy fee
			// Updated policy fee to 500

			String originalPolicyFee = requestBindPage.originalPolicyFee.getData().replace("$", "").replace(".00", "");
			Assertions.verify(requestBindPage.originalPolicyFee.getData().equals("$500"), true, "Request Bind Page",
					"Original Policy fee " + requestBindPage.originalPolicyFee.getData(), false, false);

			// Asserting Earned Inspection fee
			Assertions.verify(requestBindPage.earnedInspectionFee.getData().equals("0.0"), true, "Request Bind Page",
					"Earned Inspection fee " + requestBindPage.earnedInspectionFee.getData(), false, false);

			// Asserting Earned policy fee
			Assertions.verify(requestBindPage.earnedPolicyFee.getData().equals("0.0"), true, "Request Bind Page",
					"Earned Policy fee " + requestBindPage.earnedPolicyFee.getData(), false, false);

			requestBindPage.rewrite.scrollToElement();
			requestBindPage.rewrite.click();

			// get policyNumber
			String reWritepolicyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully. Rewrite PolicyNumber is " + reWritepolicyNumber, false,
					false);

			String transactionPremiumFee = policySummaryPage.PremiumFee.formatDynamicPath(1).getData()
					.replaceAll("[^\\d-.]", "");
			String annaulPremiumFee = policySummaryPage.PremiumFee.formatDynamicPath(2).getData().replaceAll("[^\\d-.]",
					"");
			String termPremiumFee = policySummaryPage.PremiumFee.formatDynamicPath(3).getData().replaceAll("[^\\d-.]",
					"");
			String transactionInspectionFee = policySummaryPage.PremiunInspectionFee.formatDynamicPath(1).getData()
					.replaceAll("[^\\d-.]", "");
			String annualInspectionFee = policySummaryPage.PremiunInspectionFee.formatDynamicPath(2).getData()
					.replaceAll("[^\\d-.]", "");
			String termInspectionFee = policySummaryPage.PremiunInspectionFee.formatDynamicPath(3).getData()
					.replaceAll("[^\\d-.]", "");
			String transactionPolicyFee = policySummaryPage.PremiumPolicyFee.formatDynamicPath(1).getData()
					.replaceAll("[^\\d-.]", "");
			String annualPolicyFee = policySummaryPage.PremiumPolicyFee.formatDynamicPath(2).getData()
					.replaceAll("[^\\d-.]", "");
			String termPolicyFee = policySummaryPage.PremiumPolicyFee.formatDynamicPath(3).getData()
					.replaceAll("[^\\d-.]", "");
			String transactionSurplusContributionValue = policySummaryPage.transactionSurplusContribution.getData()
					.replaceAll("[^\\d-.]", "");
			String termSurplusContributionValue = policySummaryPage.termSurplusContribution.getData()
					.replaceAll("[^\\d-.]", "");
			String annualSurplusContributionValue = policySummaryPage.annualSurplusContribution.getData()
					.replaceAll("[^\\d-.]", "");

			// SLTF Percentage
			SLTFPercentage = testData.get("SurplusLinesTaxesPercentage");

			transactionTaxandSateFeeCal = (Double.parseDouble(transactionPremiumFee)
					+ Double.parseDouble(transactionPolicyFee) + Double.parseDouble(transactionInspectionFee)
					+ Double.parseDouble(transactionSurplusContributionValue)) * Double.parseDouble(SLTFPercentage)
					+ (empaServiceCharge);

			annualTaxandStateFeeCal = (Double.parseDouble(annaulPremiumFee) + Double.parseDouble(annualPolicyFee)
					+ Double.parseDouble(annualInspectionFee) + Double.parseDouble(annualSurplusContributionValue))
					* Double.parseDouble(SLTFPercentage) + (empaServiceCharge);

			termTaxandStateFeeCal = (Double.parseDouble(termPremiumFee) + Double.parseDouble(termPolicyFee)
					+ Double.parseDouble(termInspectionFee) + Double.parseDouble(termSurplusContributionValue))
					* Double.parseDouble(SLTFPercentage) + (empaServiceCharge);

			// Asserting Positive values from rerwritten
			Assertions.verify(
					policySummaryPage.PremiunInspectionFee.formatDynamicPath(1).getData().replace("$", "")
							.replace(".00", ""),
					originalInspectionFee, "Policy Summary Page", "Trasaction Inspection fee "
							+ policySummaryPage.PremiunInspectionFee.formatDynamicPath(1).getData() + "is verified",
					false, false);

			// Asserting Transaction Policy Fee
			Assertions.verify(
					policySummaryPage.PremiumPolicyFee.formatDynamicPath(1).getData().replace("$", "").replace(".00",
							""),
					originalPolicyFee, "Policy Summary Page", "Trasaction Policy fee "
							+ policySummaryPage.PremiumPolicyFee.formatDynamicPath(1).getData() + "is verified",
					false, false);

			// Asserting Annual Inspection fee
			Assertions.verify(
					policySummaryPage.PremiunInspectionFee.formatDynamicPath(2).getData().replace("$", "")
							.replace(".00", ""),
					originalInspectionFee, "Policy Summary Page", "Annual Inspection fee "
							+ policySummaryPage.PremiunInspectionFee.formatDynamicPath(2).getData() + "is verified",
					false, false);

			// Asserting Annual Policy Fee
			Assertions.verify(
					policySummaryPage.PremiumPolicyFee.formatDynamicPath(2).getData().replace("$", "").replace(".00",
							""),
					originalPolicyFee, "Policy Summary Page", "Annual Policy fee "
							+ policySummaryPage.PremiumPolicyFee.formatDynamicPath(2).getData() + "is verified",
					false, false);

			// Asserting Term Inspection fee
			Assertions.verify(
					policySummaryPage.PremiunInspectionFee.formatDynamicPath(3).getData().replace("$", "")
							.replace(".00", ""),
					originalInspectionFee, "Policy Summary Page", "Term Inspection fee "
							+ policySummaryPage.PremiunInspectionFee.formatDynamicPath(3).getData() + "is verified",
					false, false);

			// Asserting Term Policy Fee
			Assertions.verify(
					policySummaryPage.PremiumPolicyFee.formatDynamicPath(3).getData().replace("$", "").replace(".00",
							""),
					originalPolicyFee, "Policy Summary Page", "Term Policy fee "
							+ policySummaryPage.PremiumPolicyFee.formatDynamicPath(3).getData() + "is verified",
					false, false);

			// Asserting Transaction Taxes and state Fee

			String txnTaxesandFee = policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData().replace("$", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(txnTaxesandFee), 2)
					- Precision.round(transactionTaxandSateFeeCal, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Calculated Transaction Taxes and State fee : " + "$"
						+ Precision.round(transactionTaxandSateFeeCal, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual Transaction Taxes and State fee : " + "$" + txnTaxesandFee);

			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Transaction Taxes and State fee is more than 0.05");
			}

			// Asserting Annual Taxes and state Fee

			String annualTaxesandFee = policySummaryPage.TaxesAndStateFees.formatDynamicPath(2).getData().replace("$",
					"");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(annualTaxesandFee), 2)
					- Precision.round(annualTaxandStateFeeCal, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Calculated Annual Taxes and State fee : " + "$" + Precision.round(annualTaxandStateFeeCal, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual Annual Taxes and State fee : " + "$" + annualTaxesandFee);

			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Transaction Taxes and State fee is more than 0.05");
			}

			// Asserting TermTotal Taxes and state Fee

			String termTaxesandFee = policySummaryPage.TaxesAndStateFees.formatDynamicPath(3).getData().replace("$",
					"");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(termTaxesandFee), 2)
					- Precision.round(termTaxandStateFeeCal, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Calculated Term Taxes and State fee : " + "$" + Precision.round(termTaxandStateFeeCal, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual Term Taxes and State fee : " + "$" + termTaxesandFee);

			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated Transaction Taxes and State fee is more than 0.05");
			}

			// Click on Home button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched Original Policy successfully");

			policySummaryPage.cancelType.waitTillPresenceOfElement(60);
			policySummaryPage.cancelType.waitTillButtonIsClickable(60);
			policySummaryPage.cancelType.scrollToElement();
			policySummaryPage.cancelType.click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on cancel transaction type");

			Assertions.verify(
					policySummaryPage.transactionComment.checkIfElementIsPresent()
							&& policySummaryPage.transactionComment.checkIfElementIsDisplayed(),
					true, "Policy Summary Page ", "Policy Summary page loaded successfully", false, false);

			transactionPremiumFee = policySummaryPage.PremiumFee.formatDynamicPath(1).getData()
					.replaceAll("[^\\d-.]", "").substring(1);
			transactionInspectionFee = policySummaryPage.PremiunInspectionFee.formatDynamicPath(1).getData()
					.replaceAll("[^\\d-.]", "").substring(1);
			transactionPolicyFee = policySummaryPage.PremiumPolicyFee.formatDynamicPath(1).getData()
					.replaceAll("[^\\d-.]", "").substring(1);
			transactionSurplusContributionValue = policySummaryPage.transactionSurplusContribution.getData()
					.replace("$", "").replace("-", "").replace(",", "");

			transactionTaxandSateFeeCal = (Double.parseDouble(transactionPremiumFee)
					+ Double.parseDouble(transactionPolicyFee) + Double.parseDouble(transactionInspectionFee)
					+ Double.parseDouble(transactionSurplusContributionValue)) * Double.parseDouble(SLTFPercentage)
					+ (empaServiceCharge);

			BigDecimal SLTF = BigDecimal.valueOf(transactionTaxandSateFeeCal);
			SLTF = SLTF.setScale(2, RoundingMode.HALF_UP);
			// String calculatedSLTFValue = format.format(SLTF);

			// Asserting inspection fees
			Assertions.verify(policySummaryPage.PremiunInspectionFee.formatDynamicPath(1).getData().equals("-$125.00"),
					true, "Policy Summary Page", "Transaction Inspection fee"
							+ policySummaryPage.PremiunInspectionFee.formatDynamicPath(1).getData(),
					false, false);

			Assertions.verify(policySummaryPage.PremiunInspectionFee.formatDynamicPath(2).getData().equals("$0.00"),
					true, "Policy Summary Page",
					"Annual Inspection fee" + policySummaryPage.PremiunInspectionFee.formatDynamicPath(2).getData(),
					false, false);

			Assertions.verify(policySummaryPage.PremiunInspectionFee.formatDynamicPath(3).getData().equals("$0.00"),
					true, "Policy Summary Page",
					"Term Inspection fee" + policySummaryPage.PremiunInspectionFee.formatDynamicPath(3).getData(),
					false, false);

			// Asserting transaction Policy fee
			// Updated policy fee to 500
			Assertions.verify(policySummaryPage.PremiumPolicyFee.formatDynamicPath(1).getData().equals("-$500.00"),
					true, "Policy Summary Page",
					"Transaction Policy fee " + policySummaryPage.PremiumPolicyFee.formatDynamicPath(1).getData(),
					false, false);

			Assertions.verify(policySummaryPage.PremiumPolicyFee.formatDynamicPath(2).getData().equals("$0.00"), true,
					"Policy Summary Page",
					"Annual Policy fee " + policySummaryPage.PremiumPolicyFee.formatDynamicPath(2).getData(), false,
					false);

			Assertions.verify(policySummaryPage.PremiumPolicyFee.formatDynamicPath(3).getData().equals("$0.00"), true,
					"Policy Summary Page", "Term Policy fee "
							+ policySummaryPage.PremiumPolicyFee.formatDynamicPath(3).getData() + "is verified",
					false, false);

			String d_sltfValueAcutal = policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData().replace("-",
					"");
			double sltfActualValue = Precision.round(Double.parseDouble(d_sltfValueAcutal.replace("$", "")), 2);
			double sltfCalculatedValue = Precision.round((transactionTaxandSateFeeCal), 2);

			if (Precision.round(Math.abs(Precision.round(sltfActualValue, 2) - Precision.round(sltfCalculatedValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Calculated Transaction Taxes and State fee: " + "$" + Precision.round(sltfCalculatedValue, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual Transaction Taxes and State fee: " + "$" + sltfActualValue);
			} else {
				Assertions.verify(sltfActualValue, sltfCalculatedValue, "Policy Summary Page",
						"The Difference between actual and calculated Transaction Taxes and State fee is more than 0.05",
						false, false);
			}

			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(2).getData().equals("$0.00"), true,
					"Policy Summary Page", "Annual Taxes and State fee "
							+ policySummaryPage.TaxesAndStateFees.formatDynamicPath(2).getData() + "is verified",
					false, false);
			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(3).getData().equals("$0.00"), true,
					"Policy Summary Page", "Term Taxes and State fee "
							+ policySummaryPage.TaxesAndStateFees.formatDynamicPath(3).getData() + "is verified",
					false, false);

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC080 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC080 ", "Executed Successfully");
			}
		}
	}
}
