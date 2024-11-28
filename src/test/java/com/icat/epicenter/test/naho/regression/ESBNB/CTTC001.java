/** Program Description: Verifying the Following
 * 1. warning message in prior loss page and verifying various validations in request bind page and view documents page and Added CR IO-19939 and IO-19938
 * 2. Surplus Contribution Values and Total on the NB Quote, Account Overview Page, Request Bind Page and Policy Summary Page
 *
 *  Author			   : Pavan Mule
 *  Date of Creation   : 12/28/2021
 **/
package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class CTTC001 extends AbstractNAHOTest {

	public CTTC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/CTTC001.xls";
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
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		testData = data.get(dataValue1);
		int quoteLen;
		String quoteNumber;
		String policyNumber;
		int actualPremiumLen;
		String actualQutePremium;
		double d_actualQutePremium;
		String premiumAmount;
		double d_premiumAmount;
		String feesValues;
		double d_feesValues;
		String sltfValues;
		double d_sltfValues;
		double calTotalPremium;

		String niDwelling;
		String niPriorLoss;
		String niCreateQuote;
		String niAccountOverviewPage;
		String niRequestBindePage;
		String niUnderwritingQuestionsPage;
		String niPolicySummaryPage;
		String niPolicyDocumentPage;
		String namedInsured;

		String vieParticpationPercent;
		String vieContributionCharge;
		String eqbPremium;
		String ulPremium;
		String vpfqSCValue;
		String quoteSCValue;

		double d_vieParticpationPercent;
		double d_vieContributionCharge;
		double d_eqbPremium;
		double d_ulPremium;
		double calcSurplusContributionValue;
		double d_vpfqSCValue;
		double d_quoteSCValue;
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

			// Verifying IO-20806
			namedInsured = testData.get("InsuredName");
			dwellingPage.pageName.checkIfElementIsPresent();
			niDwelling = dwellingPage.pageName.getData().substring(0, 22);

			if (niDwelling.equalsIgnoreCase(namedInsured)) {
				Assertions.verify(niDwelling, namedInsured, "Dwelling Page", "The Insured name is displayed correctly",
						false, false);
			} else {
				Assertions.verify(niDwelling, namedInsured, "Dwelling Page",
						"The Insured name is not displayed correctly", false, false);
			}

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling page",
					"Dwelling page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling page", "Dwelling details entered successfully");

			// Verifying IO-20806
			priorLossesPage.pageName.checkIfElementIsPresent();
			niPriorLoss = priorLossesPage.pageName.getData().substring(0, 22);

			if (niPriorLoss.equalsIgnoreCase(namedInsured)) {
				Assertions.verify(niPriorLoss, namedInsured, "Prior Loss Page",
						"The Insured name is displayed correctly", false, false);
			} else {
				Assertions.verify(niPriorLoss, namedInsured, "Prior Loss Page",
						"The Insured name is not displayed correctly", false, false);
			}

			// Asserting Claims message
			Assertions.addInfo("Scanario 01", "Asserting claims message");
			Assertions.verify(
					priorLossesPage.claimsMessage
							.formatDynamicPath("Claims made for the insured").checkIfElementIsDisplayed(),
					true, "Prior losses page",
					"Claims message displayed is "
							+ priorLossesPage.claimsMessage.formatDynamicPath("Claims made for the insured").getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
						"Prior loss page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior loss page", "Prior loss details entered successfully");
			}

			// Verifying IO-20806
			createQuotePage.pageName.checkIfElementIsPresent();
			niCreateQuote = createQuotePage.pageName.getData().substring(0, 22);

			if (niCreateQuote.equalsIgnoreCase(namedInsured)) {
				Assertions.verify(niCreateQuote, namedInsured, "Create Quote Page",
						"The Insured name is displayed correctly", false, false);
			} else {
				Assertions.verify(niCreateQuote, namedInsured, "Create Quote Page",
						"The Insured name is not displayed correctly", false, false);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Verifying IO-20806
			accountOverviewPage.pageName.checkIfElementIsPresent();
			niAccountOverviewPage = accountOverviewPage.pageName.getData().substring(0, 22);

			if (niAccountOverviewPage.equalsIgnoreCase(namedInsured)) {
				Assertions.verify(niAccountOverviewPage, namedInsured, "Dwelling Page",
						"The Insured name is displayed correctly", false, false);
			} else {
				Assertions.verify(niAccountOverviewPage, namedInsured, "Dwelling Page",
						"The Insured name is not displayed correctly", false, false);
			}

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote number is : " + quoteNumber);

			// Adding Below Code For CR IO-19938
			// Asserting actual quote premium
			actualPremiumLen = accountOverviewPage.quotePremium.formatDynamicPath(1).getData().length();
			actualQutePremium = accountOverviewPage.quotePremium.formatDynamicPath(1).getData()
					.substring(1, actualPremiumLen - 34).replace("$", "").replace(",", "");
			d_actualQutePremium = Double.valueOf(actualQutePremium);

			// Asserting calculated quote premium
			premiumAmount = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			d_premiumAmount = Double.valueOf(premiumAmount);
			feesValues = accountOverviewPage.feesValue.getData().replace("$", "");
			d_feesValues = Double.valueOf(feesValues);
			sltfValues = accountOverviewPage.sltfValue.getData().replace("$", "");
			d_sltfValues = Double.valueOf(sltfValues);

			// Fetching the Surplus Contribution Value in the Middle Pane
			quoteSCValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "");
			d_quoteSCValue = Double.parseDouble(quoteSCValue);
			calTotalPremium = d_premiumAmount + d_feesValues + d_sltfValues + d_quoteSCValue;

			// Clicking on QUote Specifics Accordion
			accountOverviewPage.quoteSpecifics.checkIfElementIsPresent();
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();

			// Fetching Values for Surplus Contribution Calculation
			vieParticpationPercent = accountOverviewPage.vieParticipationValue.getData().replace("%", "");
			d_vieParticpationPercent = Double.parseDouble(vieParticpationPercent) / 100;
			vieContributionCharge = accountOverviewPage.vieContributionChargeValue.getData().replace("%", "");
			d_vieContributionCharge = Double.parseDouble(vieContributionCharge) / 100;

			// Clicking on View/Print Rate Trace
			accountOverviewPage.viewOrPrintRateTrace.checkIfElementIsPresent();
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();

			// Fetching EQB and UL Premium Values from view/print Rate Trace Page
			eqbPremium = viewOrPrintRateTrace.eqbPremiumValue.getData();
			d_eqbPremium = Double.parseDouble(eqbPremium);
			ulPremium = viewOrPrintRateTrace.serviceLinePremiumValue.getData();
			d_ulPremium = Double.parseDouble(ulPremium);

			// Navigating back to Account Overview Page
			viewOrPrintRateTrace.backBtn.checkIfElementIsPresent();
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();

			// calculating the Surplus Contribution Value
			calcSurplusContributionValue = d_vieParticpationPercent * d_vieContributionCharge
					* (d_premiumAmount - d_eqbPremium - d_ulPremium);

			// Comparing Actual Quote premium and calculated quote premium values
			Assertions.addInfo("Sceanrio 02", "Verifying actual and calculated quote premium");
			if (Precision.round(Math.abs(Precision.round(d_actualQutePremium, 2) - Precision.round(calTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Quote Premium : " + "$" + Precision.round(calTotalPremium, 2));
				Assertions.passTest("Account Overview Page", "Actual Quote Premium  : " + "$" + d_actualQutePremium);
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Below code for CR IO-19939
			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote page", "View Or Print full quote page loaded successfully", false, false);

			// Asserting actual total amount
			String actualTotalAmount = viewOrPrintFullQuotePage.totalPremiumValue.getData().replace("$", "")
					.replace(",", "");
			double d_actualTotalAmount = Double.valueOf(actualTotalAmount);

			// Asserting calculated total amount
			String premium = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			double d_premium = Double.valueOf(premium);

			String policyFees = viewOrPrintFullQuotePage.policyFeeNaho.getData().replace("$", "").replace(",", "");
			double d_policyFees = Double.valueOf(policyFees);

			String inspectionFees = viewOrPrintFullQuotePage.inspectionFeeNaho.getData().replace("$", "").replace(",", "");
			double d_inspectionFees = Double.valueOf(inspectionFees);

			String sltfValue = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace("$", "").replace(",",
					"");
			double d_sltfValue = Double.valueOf(sltfValue);

			String healthyHomesfundSurcharge = viewOrPrintFullQuotePage.healthyHomesFund.getData().replace("$", "")
					.replace(",", "");
			double d_healthyHomesfundSurcharge = Double.valueOf(healthyHomesfundSurcharge);

			vpfqSCValue = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace("%", "");
			d_vpfqSCValue = Double.parseDouble(vpfqSCValue);

			double calTotalAmount = d_premium + d_policyFees + d_inspectionFees + d_sltfValue
					+ d_healthyHomesfundSurcharge + d_vpfqSCValue;

			// Verifying Actual and calculated total premium amount
			Assertions.addInfo("Sceanrio 03", "Verifying actual and calculated total amount both are same");
			if (Precision.round(Math.abs(Precision.round(d_actualTotalAmount, 2) - Precision.round(calTotalAmount, 2)),
					2) < 0.05) {
				Assertions.passTest("View Print Full Quote Page",
						"Calculated Total Premium : " + "$" + Precision.round(calTotalAmount, 2));
				Assertions.passTest("View Print Full Quote Page",
						"Actual Total Premium  : " + "$" + d_actualTotalAmount);
			} else {
				Assertions.verify(d_actualTotalAmount, calTotalAmount, "View Print Full Quote Page",
						"The Difference between actual  and calculated total is more than 0.05", false, false);
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Verifying the Actual SC Value and Calculated SC Value on View/Print Full
			// quote Page
			Assertions.addInfo("Scenario 04",
					"Verifying if actual and calculated Surplus Contribution Value on the View/Print Full Quote Page are same");
			if (Precision.round(
					Math.abs(Precision.round(d_vpfqSCValue, 2) - Precision.round(calcSurplusContributionValue, 2)),
					2) < 0.05) {
				Assertions.passTest("View or Print Full Quote Page",
						"Calculated SC Value: " + "$" + Precision.round(calcSurplusContributionValue, 2));
				Assertions.passTest("View or Print Full Quote Page",
						"Actual SC Value: " + "$" + Precision.round(d_vpfqSCValue, 2));
			} else {
				Assertions.verify(Precision.round(d_vpfqSCValue, 2), Precision.round(calcSurplusContributionValue, 2),
						"View or Print Full Quote Page",
						"The Actaul and Calculated Surplus Contribution Values are not the Same", false, false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Verifying if the Actual SC Value and the Calculated SC Value are the on
			// Account Overview Page
			Assertions.addInfo("Scenario 05",
					"Verifying if actual and calculated Surplus Contribution Value on the Account Overview Page are same");
			if (Precision.round(
					Math.abs(Precision.round(d_quoteSCValue, 2) - Precision.round(calcSurplusContributionValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated SC Value: " + "$" + Precision.round(calcSurplusContributionValue, 2));
				Assertions.passTest("Account Overview Page",
						"Actual SC Value: " + "$" + Precision.round(d_quoteSCValue, 2));
			} else {
				Assertions.verify(Precision.round(d_quoteSCValue, 2), Precision.round(calcSurplusContributionValue, 2),
						"Account Overview Page",
						"The Actaul and Calculated Surplus Contribution Values are not the Same", false, false);
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// CR IO-19939 ended
			// Binding the quote and creating the policy
			testData = data.get(dataValue2);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			testData = data.get(dataValue1);

			// Verifying IO-20806
			underwritingQuestionsPage.pageName.checkIfElementIsPresent();
			niUnderwritingQuestionsPage = underwritingQuestionsPage.pageName.getData().substring(0, 22);

			if (niUnderwritingQuestionsPage.equalsIgnoreCase(namedInsured)) {
				Assertions.verify(niUnderwritingQuestionsPage, namedInsured, "Underwritting Questions Page",
						"The Insured name is displayed correctly", false, false);
			} else {
				Assertions.verify(niUnderwritingQuestionsPage, namedInsured, "Underwritting Questions Page",
						"The Insured name is not displayed correctly", false, false);
			}

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting questions page", "Underwriting questions page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Verifying IO-20806
			requestBindPage.pageName.checkIfElementIsPresent();
			niRequestBindePage = requestBindPage.pageName.getData().substring(0, 22);

			if (niRequestBindePage.equalsIgnoreCase(namedInsured)) {
				Assertions.verify(niRequestBindePage, namedInsured, "Dwelling Page",
						"The Insured name is displayed correctly", false, false);
			} else {
				Assertions.verify(niRequestBindePage, namedInsured, "Dwelling Page",
						"The Insured name is not displayed correctly", false, false);
			}

			// Asserting diligence texts
			Assertions.addInfo("Scenario 06", "Verifying diligence text");
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("minimum 25%").checkIfElementIsDisplayed(), true,
					"Request Bind page",
					"Diligence Message 1 : "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("minimum 25%").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("Completed and signed application").checkIfElementIsDisplayed(),
					true, "Request Bind page",
					"Diligence Message 2 : " + requestBindPage.dueDiligenceText
							.formatDynamicPath("Completed and signed application").getData() + " is displayed",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("Completed and signed SL-8").checkIfElementIsDisplayed(),
					true, "Request Bind page",
					"Diligence Message 3 : "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("Completed and signed SL-8").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("signed diligent effort form").checkIfElementIsDisplayed(),
					true, "Request bind page",
					"Diligence Message 4 : " + requestBindPage.dueDiligenceText
							.formatDynamicPath("signed diligent effort form").getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Verifying if the Request Bind Page is loaded
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Fetching Premium Values for the Assertion
			String rpPremiumValue = requestBindPage.quotePremium.getData().replace("$", "").replace(",", "");
			double d_rpPremiumValue = Double.parseDouble(rpPremiumValue);

			// Verifying if the Calculated Total Premium Value is same as the Premium Value
			// on the Request Bind Page
			Assertions.addInfo("Scenario 07",
					"Verifying if the Calculated Total Premium Value is same as the Premium Value on the Request Bind Page");
			if (Precision.round(Math.abs(Precision.round(d_rpPremiumValue, 2) - Precision.round(calTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Request Bind Page",
						"Calculated Total Premium Value: " + "$" + Precision.round(calTotalPremium, 2));
				Assertions.passTest("Request Bind Page",
						"Actual Total Premium Value: " + "$" + Precision.round(d_rpPremiumValue, 2));
			} else {
				Assertions.verify(Precision.round(d_rpPremiumValue, 2), Precision.round(calTotalPremium, 2),
						"Request Bind Page", "The Actaul and Calculated Total Premium Values are not the Same", false,
						false);
			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Fetching the Premium Values on the Payment Plan
			String fullPayPrem = requestBindPage.payPlanPremium.formatDynamicPath("1").getData()
					.replace("First Payment: $", "").replace(",", "");
			double d_fullPayPrem = Double.parseDouble(fullPayPrem);
			String mortgageePayPrem = requestBindPage.payPlanPremium.formatDynamicPath("2").getData()
					.replace("First Payment: $", "").replace(",", "");
			double d_mortgageePayPrem = Double.parseDouble(mortgageePayPrem);

			// Verifying if the Calculated Total Premium Value is same as the Premium Value
			// on the Request Bind Page
			Assertions.addInfo("Scenario 08",
					"Verifying if the Calculated Total Premium Value is same as the Premium Value on the Request Bind Page");
			if (Precision.round(Math.abs(Precision.round(d_fullPayPrem, 2) - Precision.round(calTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Request Bind Page",
						"Calculated Total Premium Value: " + "$" + Precision.round(calTotalPremium, 2));
				Assertions.passTest("Request Bind Page",
						"Actual Full Pay Premium Value: " + "$" + Precision.round(d_fullPayPrem, 2));
			} else {
				Assertions.verify(Precision.round(d_fullPayPrem, 2), Precision.round(calTotalPremium, 2),
						"Request Bind Page",
						"The Actaul Full Pay Premium and Calculated Total Premium Values are not the Same", false,
						false);
			}
			if (Precision.round(Math.abs(Precision.round(d_mortgageePayPrem, 2) - Precision.round(calTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Request Bind Page",
						"Calculated Total Premium Value: " + "$" + Precision.round(calTotalPremium, 2));
				Assertions.passTest("Request Bind Page",
						"Actual Mortgagee Pay Premium Value: " + "$" + Precision.round(d_mortgageePayPrem, 2));
			} else {
				Assertions.verify(Precision.round(d_mortgageePayPrem, 2), Precision.round(calTotalPremium, 2),
						"Request Bind Page",
						"The Actaul Mortgagee Pay Premium and Calculated Total Premium Values are not the Same", false,
						false);
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Asserting VIE Message
			Assertions.verify(requestBindPage.vieMessage.checkIfElementIsPresent(), true, "Request Bind Page",
					requestBindPage.vieMessage.getData(), false, false);

			// Entering Bind Details
			testData = data.get(dataValue1);
			requestBindPage.enterPolicyDetailsNAHO(testData);
			requestBindPage.enterPaymentInformationNAHO(testData);
			requestBindPage.addInspectionContact(testData);
			requestBindPage.addContactInformation(testData);
			waitTime(2);
			requestBindPage.submit.waitTillButtonIsClickable(60);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			waitTime(5);
			Assertions.passTest("Request Bind Page",
					"Bind Details Entered and Reciprocal Validations are completed successfully");

			// Upload due diligence form
			testData = data.get(dataValue2);
			if (requestBindPage.connecticutChooseFile.formatDynamicPath("Connecticut").checkIfElementIsPresent()
					&& requestBindPage.connecticutChooseFile.formatDynamicPath("Connecticut")
							.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
					requestBindPage.submit.waitTillPresenceOfElement(60);
					requestBindPage.submit.scrollToElement();
					requestBindPage.submit.click();
					if (confirmBindRequestPage.requestBind.checkIfElementIsPresent()) {
						confirmBindRequestPage.confirmBind();
					}
				}
			}
			Assertions.passTest("Request Bind Page", "Values Entered Successfully");

			// Fetching Confirm Bind Page Premium Value
			String quotePremCBP = confirmBindRequestPage.quotePremium.getData().replace("Grand Total: $", "")
					.replace(",", "");
			Double quotePremCBPVlaue = Double.parseDouble(quotePremCBP);

			// Validating Premium Values on Confirm Bind Page
			if (Precision.round(Math.abs(Precision.round(quotePremCBPVlaue, 2) - Precision.round(calTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Confirm Bind Page",
						"Calculated Total Premium Value: " + "$" + Precision.round(calTotalPremium, 2));
				Assertions.passTest("Confirm Bind Page",
						"Actual Premium Value: " + "$" + Precision.round(quotePremCBPVlaue, 2));
			} else {
				Assertions.verify(Precision.round(quotePremCBPVlaue, 2), Precision.round(calTotalPremium, 2),
						"Confirm Bind Page", "The Actaul and Calculated Total Premium Values are not the Same", false,
						false);
			}

			waitTime(2);
			if (confirmBindRequestPage.requestBind.checkIfElementIsPresent()
					&& confirmBindRequestPage.requestBind.checkIfElementIsPresent()) {
				confirmBindRequestPage.confirmBind();
			}

			testData = data.get(dataValue2);

			// Verifying IO-20806
			policySummaryPage.pageName1.checkIfElementIsPresent();
			niPolicySummaryPage = policySummaryPage.pageName1.getData().substring(0, 22);

			if (niPolicySummaryPage.equalsIgnoreCase(namedInsured)) {
				Assertions.verify(niPolicySummaryPage, namedInsured, "Policy Summary Page",
						"The Insured name is displayed correctly", false, false);
			} else {
				Assertions.verify(niPolicySummaryPage, namedInsured, "Policy Summary Page",
						"The Insured name is not displayed correctly", false, false);
			}

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.passTest("Policy summary page", "Policy number is : " + policyNumber);

			// Fetching Values to Verify Surplus Contribution
			String termSurplusContributionValue = policySummaryPage.termSurplusContribution.getData().replace("$", "");
			double d_termSurplusContributionValue = Double.parseDouble(termSurplusContributionValue);
			String annualSurplusContributionValue = policySummaryPage.annualSurplusContribution.getData().replace("$",
					"");
			double d_annualSurplusContributionValue = Double.parseDouble(annualSurplusContributionValue);
			String transactionSurplusContributionValue = policySummaryPage.transactionSurplusContribution.getData()
					.replace("$", "");
			double d_transactionSurplusContributionValue = Double.parseDouble(transactionSurplusContributionValue);
			String termtotalPremiumValue = policySummaryPage.termTotal.getData().replace("$", "").replace(",", "");
			double d_termtotalPremiumValue = Double.parseDouble(termtotalPremiumValue);
			String annualtotalPremiumValue = policySummaryPage.annualTotal.getData().replace("$", "").replace(",", "");
			double d_annualtotalPremiumValue = Double.parseDouble(annualtotalPremiumValue);
			String transactionPremiumValue = policySummaryPage.policyTotalPremium.getData().replace("$", "")
					.replace(",", "");
			double d_transactionPremiumValue = Double.parseDouble(transactionPremiumValue);

			// Verifying if the Surplus Contribution and Total Premium Values on the Policy
			// Summary Page are Matching with the Calculated Values
			Assertions.addInfo("Scenario 09",
					"Validating the Surplsus Contribution Values present on Policy Summary Page"
							+ " are matching the Calculated Surplus Contribution Value");

			if (Precision.round(Math.abs(Precision.round(d_termSurplusContributionValue, 2)
					- Precision.round(calcSurplusContributionValue, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Calculated SC Value: " + "$" + Precision.round(calcSurplusContributionValue, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual SC Value: " + "$" + Precision.round(d_termSurplusContributionValue, 2));
			} else {
				Assertions.verify(Precision.round(d_termSurplusContributionValue, 2),
						Precision.round(calcSurplusContributionValue, 2), "Policy Summary Page",
						"The Actaul and Calculated Term Surplus Contribution Values are not the Same", false, false);
			}

			if (Precision.round(Math.abs(Precision.round(d_annualSurplusContributionValue, 2)
					- Precision.round(calcSurplusContributionValue, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Calculated SC Value: " + "$" + Precision.round(calcSurplusContributionValue, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual SC Value: " + "$" + Precision.round(d_annualSurplusContributionValue, 2));
			} else {
				Assertions.verify(Precision.round(d_annualSurplusContributionValue, 2),
						Precision.round(calcSurplusContributionValue, 2), "Policy Summary Page",
						"The Actaul and Calculated Annual Surplus Contribution Values are not the Same", false, false);
			}

			if (Precision.round(Math.abs(Precision.round(d_transactionSurplusContributionValue, 2)
					- Precision.round(calcSurplusContributionValue, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Calculated SC Value: " + "$" + Precision.round(calcSurplusContributionValue, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual SC Value: " + "$" + Precision.round(d_transactionSurplusContributionValue, 2));
			} else {
				Assertions.verify(Precision.round(d_transactionSurplusContributionValue, 2),
						Precision.round(calcSurplusContributionValue, 2), "Policy Summary Page",
						"The Actaul and Calculated Transaction Surplus Contribution Values are not the Same", false,
						false);
			}
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			Assertions.addInfo("Scenario 10",
					"Validating the Surplsus Contribution Values present on Policy Summary Page"
							+ " are matching the Calculated Surplus Contribution Value");
			if (d_termtotalPremiumValue == calTotalPremium) {
				Assertions.verify(d_termtotalPremiumValue, calTotalPremium, "Policy Summary Page",
						"The Term Total Premium is same as the Calculated Total Premium", false, false);
			} else {
				Assertions.verify(d_termtotalPremiumValue, calTotalPremium, "Policy Summary Page",
						"The Term Total Premium is not same as the Calculated Total Premium", false, false);
			}

			if (d_annualtotalPremiumValue == calTotalPremium) {
				Assertions.verify(d_annualtotalPremiumValue, calTotalPremium, "Policy Summary Page",
						"The Annual Total Premium is same as the Calculated Total Premium", false, false);
			} else {
				Assertions.verify(d_annualtotalPremiumValue, calTotalPremium, "Policy Summary Page",
						"The Annual Total Premium is not same as the Calculated Total Premium", false, false);
			}
			if (d_transactionPremiumValue == calTotalPremium) {
				Assertions.verify(d_transactionPremiumValue, calTotalPremium, "Policy Summary Page",
						"The Transaction Total Premium is same as the Calculated Total Premium", false, false);
			} else {
				Assertions.verify(d_transactionPremiumValue, calTotalPremium, "Policy Summary Page",
						"The Transaction Total Premium is not same as the Calculated Total Premium", false, false);
			}

			// Click on view policy document link
			policySummaryPage.viewDocuments.scrollToElement();
			policySummaryPage.viewDocuments.click();
			Assertions.verify(policyDocumentsPage.addDocumentButton.checkIfElementIsDisplayed(), true,
					"Policy documents page", "Policy documents page loaded successfully", false, false);

			// Verifying IO-20806
			policyDocumentsPage.pageName.checkIfElementIsPresent();
			int pdPageNameLen = policySummaryPage.pageName1.getData().length();
			niPolicyDocumentPage = policyDocumentsPage.pageName.getData().substring(0, pdPageNameLen - 19);

			if (niPolicyDocumentPage.equalsIgnoreCase(namedInsured)) {
				Assertions.verify(niPolicyDocumentPage, namedInsured, "Policy Document Page",
						"The Insured name is displayed correctly", false, false);
			} else {
				Assertions.verify(niPolicyDocumentPage, namedInsured, "Policy Document Page",
						"The Insured name is not displayed correctly", false, false);
			}

			// Click on back button
			testData = data.get(dataValue1);
			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();
			Assertions.passTest("Policy documents page", "Clicked on back button successfully");

			// Click on cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.verify(cancelPolicyPage.cancellationEffectiveDate.checkIfElementIsDisplayed(), true,
					"Cancel policy page", "Cancel policy page loaded successfully", false, false);

			// Enter cancellation details
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			cancelPolicyPage.legalNoticeWording.waitTillPresenceOfElement(60);
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.appendData(testData.get("Cancellation_LegalNoticeWording"));
			cancelPolicyPage.legalNoticeWording.tab();
			cancelPolicyPage.cancellationEffectiveDate.waitTillPresenceOfElement(60);
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancellationEffectiveDate.clearData();
			cancelPolicyPage.cancellationEffectiveDate.appendData(testData.get("CancellationEffectiveDate"));
			cancelPolicyPage.cancellationEffectiveDate.tab();
			cancelPolicyPage.nextButton.waitTillPresenceOfElement(60);
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Verifying actual earned sltf and calculated earned sltf
			String earnedPremiumValue = cancelPolicyPage.newPremium.getData().replace("$", "");

			String earnedSurplusContributionValue = cancelPolicyPage.surplusContributionVlaue.formatDynamicPath(3)
					.getData().replace("$", "");

			String earnedActualSltfValue = cancelPolicyPage.newSLTF.getData().replace("$", "");

			double calEarnedSltf = ((Double.parseDouble(earnedPremiumValue)
					+ Double.parseDouble(earnedSurplusContributionValue))
					* Double.parseDouble(testData.get("SLTFPercentage")))
					+ Double.parseDouble(testData.get("HealthyHomesFundSurcharge"));

			Assertions.addInfo("Scenario 12", "Verifying actual earned sltf and calculated earned sltf");
			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(earnedActualSltfValue), 2) - Precision.round(calEarnedSltf, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel summary page",
						"earned calculated sltf value: " + "$" + Precision.round(calEarnedSltf, 2));
				Assertions.passTest("Cancel summary page", "Earned actual sltf value: " + "$"
						+ Precision.round(Double.parseDouble(earnedActualSltfValue), 2));
			} else {
				Assertions.verify(Precision.round(Double.parseDouble(earnedActualSltfValue), 2),
						Precision.round(calEarnedSltf, 2), "Cancel summary page",
						"The Difference between earned actual sltf and earned calculated stfl is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("CTTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("CTTC001 ", "Executed Successfully");
			}
		}
	}
}
