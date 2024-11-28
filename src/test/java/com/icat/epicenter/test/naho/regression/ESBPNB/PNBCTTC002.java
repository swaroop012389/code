/*Program Description: Asserting Following:
 * 1. SLTF Value on view print full quote page for renewal quote
 * 2. Referral message on renewal quote when Cov A = 1000000 and Wind Premium = $2000
 * 3. Healthy Homes Surcharge is displayed as $12 on view print full quote page
 * 4. Claim message on prior loss page
 * 5. Due diligence text words on renewal request bind page
 * 6. Surplus Contribution Values and Total on the Renewal Quote, Renewal Account Overview Page, Renewal Request Bind Page and Renewal Policy Summary Page and IO-21792
 * Author            : Pavan Mule
 * Date of Creation  : 24-05-2022
 */

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBCTTC002 extends AbstractNAHOTest {

	public PNBCTTC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/CTTC002.xls";
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
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();

		// Initializing the variables
		int data_Value1 = 0;
		int quoteLen;
		String quoteNumber;
		Map<String, String> testData = data.get(data_Value1);
		String policyNumber;
		String actualPremium;
		String actualSLTF;
		double d_actualPremium;
		double d_actualSLTF;
		String SLTFPercentage;
		double d_SLTFPercentage;
		double calculatedSLTF;
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

			// Getting the Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on override premium link
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Override Premium link successfully");

			// Updating wind premium = 2000
			Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees Page loaded successfully", false,
					false);
			overridePremiumAndFeesPage.enterFeesDetailsNAHO(testData);

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// clicking on expac link in home page
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			Assertions.passTest("Home Page", "Clicked on Expaac Link");

			// entering expaac data
			Assertions.verify(expaccInfoPage.policyNumber.checkIfElementIsDisplayed(), true, "Expacc Info Page",
					"Update Expaac Data page loaded successfully", false, false);
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("Expacc Info Page", "Expaac Data updated successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Find the policy by entering policy Number
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// Click on Renew Policy Hyperlink
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

			// Assert renewal quote number
			Assertions.verify(
					accountOverviewPage.openReferral.checkIfElementIsPresent()
							&& accountOverviewPage.openReferral.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote number is : " + quoteNumber);

			// Fetching values for Surplus Contribution Calculation
			String quotePremiumValue = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			double d_quotePremiumValue = Double.parseDouble(quotePremiumValue);
			String quoteTotalPremValue = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",",
					"");
			double d_quoteTotalPremValue = Double.parseDouble(quoteTotalPremValue);

			// Commenting these lines as Reciprocal will not be active Please uncomment once
			// Reciprocal for Renewal is Back
			// Fetching VIE Values
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			String vieParticipationPercent = accountOverviewPage.vieParticipationValue.getData().replace("%", "");
			double d_vieParticipationPercent = Double.parseDouble(vieParticipationPercent) / 100;
			String vieContributionCharge = accountOverviewPage.vieContributionChargeValue.getData().replace("%", "");
			double d_vieContributionCharge = Double.parseDouble(vieContributionCharge) / 100;

			// Navigating to view/print rate trace page
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();

			// Fetching values for view/print rate trace page
			// Commenting these lines as Reciprocal will not be active Please uncomment once
			// Reciprocal for Renewal is Back
			String eqbPremium = viewOrPrintRateTrace.eqbPremiumValue.getData();
			double d_eqbPremium = Double.parseDouble(eqbPremium);
			String ulPremium = viewOrPrintRateTrace.serviceLinePremiumValue.getData();
			double d_ulPremium = Double.parseDouble(ulPremium);

			// Commenting these lines as Reciprocal will not be active Please uncomment once
			// Reciprocal for Renewal is Back
			// Calculating the Surplus contribution value
			double calcSurplusContributionValue = d_vieParticipationPercent * d_vieContributionCharge
					* (d_quotePremiumValue - d_eqbPremium - d_ulPremium);

			// navigating back to account overview page to fetch atual quote SC value
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();

			// Fetching Surplus Contribution Value form Account Overview Page
			String actualQuoteSCValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace("%", "");
			double d_actualQuoteSCValue = Double.parseDouble(actualQuoteSCValue);

			// Navigating to View/Print Full Quote Page
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Fetching total Premium Value, Surplus Contribution Value
			String vpfqTotalPremiumVlaue = viewOrPrintFullQuotePage.totalPremiumValue.getData().replace("$", "")
					.replace(",", "");
			double d_vpfqTotalPremiumVlaue = Double.parseDouble(vpfqTotalPremiumVlaue);
			String vpfqPolicyFee = viewOrPrintFullQuotePage.policyFeeNaho.getData().replace("$", "");
			double d_vpfqPolicyFee = Double.parseDouble(vpfqPolicyFee);
			String vpfqInspectionfee = viewOrPrintFullQuotePage.inspectionFeeNaho.getData().replace("$", "");
			double d_vpfqInspectionfee = Double.parseDouble(vpfqInspectionfee);
			String vpfqHealthyHomeFundCharge = viewOrPrintFullQuotePage.healthyHomesFund.getData().replace("$", "");
			double d_vpfqHealthyHomeFundCharge = Double.parseDouble(vpfqHealthyHomeFundCharge);
			String vpfqSLTF = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace("$", "").replace("%",
					"");
			double d_vpfqSLTF = Double.parseDouble(vpfqSLTF);
			// Commenting these lines as Reciprocal will not be active Please uncomment once
			// Reciprocal for Renewal is Back
			String vpfqSCValue = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
					.replace("%", "");
			double d_vpfqSCValue = Double.parseDouble(vpfqSCValue);

			// Calculating total Premium value
			// Commenting these lines as Reciprocal will not be active Please uncomment once
			// Reciprocal for Renewal is Back
			double calcTotalPremiumValue = d_quotePremiumValue + d_vpfqPolicyFee + d_vpfqInspectionfee
					+ d_vpfqHealthyHomeFundCharge + d_vpfqSLTF + d_vpfqSCValue;

//		double calcTotalPremiumValue = d_quotePremiumValue + d_vpfqPolicyFee + d_vpfqInspectionfee
//				+ d_vpfqHealthyHomeFundCharge + d_vpfqSLTF;

			// Verifying if the Calculated values are matching with the actual values

			if (Precision.round(
					Math.abs(Precision.round(d_quoteTotalPremValue, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Quote Total Premium Value is : " + "$" + Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("Account Overview Page",
						"The Actual Quote Total Premium Value is  : " + "$" + d_quoteTotalPremValue);
			} else {
				Assertions.verify(d_quoteTotalPremValue, calcTotalPremiumValue, "Account Overview Page",
						"The Actual Quote Total Premium Value is not same as the Calculated Total Premium Value", false,
						false);
			}

			if (Precision.round(
					Math.abs(Precision.round(d_vpfqTotalPremiumVlaue, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("View Or Print Full Quote Page",
						"Calculated Total Premium Value is : " + "$" + Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("View Or Print Full Quote Page",
						"The Actual Total Premium Value is  : " + "$" + d_vpfqTotalPremiumVlaue);
			}

			else {
				Assertions.verify(d_vpfqTotalPremiumVlaue, calcTotalPremiumValue, "View Or Print Full Quote Page",
						"The Actual Total Premium Value is not same as the Calculated Total Premium Value", false,
						false);
			}

			// Commenting these lines as Reciprocal will not be active Please uncomment once
			// Reciprocal for Renewal is Back
			if (calcSurplusContributionValue == d_actualQuoteSCValue) {
				Assertions.verify(d_actualQuoteSCValue, calcSurplusContributionValue, "Account Overview Page",
						"The Actual Surplus Contribution Value is same as the Calculated Surplus Contribution Value",
						false, false);
			} else {
				Assertions.verify(d_actualQuoteSCValue, calcSurplusContributionValue, "Account Overview Page",
						"The Actual Surplus Contribution Value is not same as the Calculated Surplus Contribution Value",
						false, false);
			}

			if (calcSurplusContributionValue == d_vpfqSCValue) {
				Assertions.verify(d_vpfqSCValue, calcSurplusContributionValue, "View Or Print Full Quote Page",
						"The Actual Surplus Contribution Value is same as the Calculated Surplus Contribution Value",
						false, false);
			} else {
				Assertions.verify(d_vpfqSCValue, calcSurplusContributionValue, "View Or Print Full Quote Page",
						"The Actual Surplus Contribution Value is not same as the Calculated Surplus Contribution Value",
						false, false);
			}

			// Navigating back to Account Overview PAge
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Opening the Referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Checking referral messages on referral page "A renewal referral has been
			// triggered due to the previous premium adjustment being above threshold.
			// Please review the account metrics prior to releasing the renewal quote".
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);

			// Approve the referral
			// IO-21792-As part of this our expectation is to validate if the referred quote
			// is assigned to USM not "Holder RMS"
			if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {

				Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true, "Referral Page",
						"Quote is referred to USM " + referralPage.assignedUser.getData(), false, false);
			} else {

				Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false, "Referral Page",
						"Quote is referred to USM " + referralPage.assignedUser.getData(), false, false);
			}
			// Ended

			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

			// Click on close
			if (referralPage.close.checkIfElementIsPresent() && referralPage.close.checkIfElementIsDisplayed()) {
				referralPage.close.scrollToElement();
				referralPage.close.click();
			}

			// Search for quote number
			homePage.searchQuote(quoteNumber);

			// Click on view/print full quote link
			Assertions.verify(
					accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsPresent()
							&& accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "View Print Full Quote Page loaded successfully", false, false);

			// Getting actual SLTF Value,Premium and Policy Fees
			actualPremium = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			String surplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
					.replace("%", "");
			actualSLTF = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace("$", "").replace(",", "")
					.replace("%", "");
			SLTFPercentage = testData.get("SLTFPercentage");
			d_actualPremium = Double.parseDouble(actualPremium);
			double d_surplusContribution = Double.parseDouble(surplusContribution);
			d_actualSLTF = Double.parseDouble(actualSLTF);
			d_SLTFPercentage = Double.parseDouble(SLTFPercentage);
			calculatedSLTF = (d_actualPremium + d_surplusContribution) * d_SLTFPercentage;
			// calculatedSLTF = (d_actualPremium ) * d_SLTFPercentage;
			Assertions.passTest("View/Print Full Quote Page", "Actual Premium Value " + d_actualPremium);

			// Verifying Actual SLTF and Calculated SLTF Values on View/Print full quote
			Assertions.addInfo("Scenario 01",
					"Verifying actual SLTF and calculated SLTF values View/Print full quote page");
			if (Precision.round(Math.abs(Precision.round(d_actualSLTF, 2) - Precision.round(calculatedSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated surplus lines taxes and fees : " + "$" + Precision.round(calculatedSLTF, 2));
				Assertions.passTest("View/Print Full Quote Page",
						"Actual surplus lines taxes and fees : " + "$" + Precision.round(d_actualSLTF, 2));
			} else {
				Assertions.verify(d_actualSLTF, calculatedSLTF, "View/Print Full Quote Page",
						"The Difference between actual SLTF value Fund and calculated SLTF value is more than 0.05",
						false, false);
			}
			Assertions.addInfo("Scenario 01 ", "Scenario 01 Ended");

			// verifying the presence of Healthy Homes Surcharge is displayed as $12
			Assertions.addInfo("Scenario 02", "Verifying the presence of Healthy Homes Surcharge = $12.00");
			Assertions.verify(
					viewOrPrintFullQuotePage.healthyHomesFund.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.healthyHomesFund.checkIfElementIsDisplayed(),
					viewOrPrintFullQuotePage.healthyHomesFund.getData().contains("$12.00"),
					"View Print Full Quote Page",
					"Healthy Homes Surcharge " + viewOrPrintFullQuotePage.healthyHomesFund.getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 02 ", "Scenario 02 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on Prior loss button
			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss page loaded successfully", false, false);

			// Verifying presence of Claims message "Claims made for the insured property by
			// the previous owner should not be included in the prior losses."
			Assertions.addInfo("Scenario 03", "Verifying the presence of claim message");
			Assertions.verify(
					priorLossesPage.claimsMessage.formatDynamicPath("previous owner").checkIfElementIsPresent()
							&& priorLossesPage.claimsMessage.formatDynamicPath("previous owner")
									.checkIfElementIsDisplayed(),
					priorLossesPage.claimsMessage.formatDynamicPath("previous owner").getData().contains(
							"Claims made for the insured property by the previous owner should not be included in the prior losses."),
					"Prior Loss Page",
					"Claim Message is " + priorLossesPage.claimsMessage.formatDynamicPath("previous owner").getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 03 ", "Scenario 03 Ended");
			// Click on continue
			priorLossesPage.continueButton.scrollToElement();
			priorLossesPage.continueButton.click();

			// Click on Release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();

			// Click on Request bind
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Verifying presence of A minimum 25% down payment (if not mortgagee billed)
			// Completed and signed application
			// Completed and signed diligent effort form is displayed correctly.
			Assertions.addInfo("Scenario 04",
					"Verifying presence of A minimum 25% down payment (if not mortgagee billed),Completed and signed application,Completed and signed diligent effort form");
			Assertions.verify(
					requestBindPage.diligenceText.checkIfElementIsPresent()
							&& requestBindPage.diligenceText.checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"Diligence Text Message is " + requestBindPage.diligenceText.getData() + " Displayed", false,
					false);
			Assertions.addInfo("Scenario 04 ", "Scenario 04 Ended");

			// Entering renewal bind details
			// Fetching Premium Values for the Assertion
			String rpPremiumValue = requestBindPage.quotePremium.getData().replace("$", "").replace(",", "");
			double d_rpPremiumValue = Double.parseDouble(rpPremiumValue);

			// Verifying if the Calculated Total Premium Value is same as the Premium Value
			// on the Request Bind Page
			Assertions.addInfo("Scenario 05",
					"Verifying if the Calculated Total Premium Value is same as the Premium Value on the Request Bind Page");

			if (Precision.round(
					Math.abs(Precision.round(d_rpPremiumValue, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Request Bind Page", "Calculated Premium Value on the Request Bind Page is : " + "$"
						+ Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("Request Bind Page",
						"The Actual Premium Value on the Request Bind Page is  : " + "$" + d_rpPremiumValue);
			}

			else {
				Assertions.verify(d_rpPremiumValue, calcTotalPremiumValue, "Request Bind Page",
						"The Actual Premium Value on the Request Bind Page is not same as the Calculated Premium Value",
						false, false);
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Fetching the Premium Values on the Payment Plan
			// FormatDynamicPath varies from environment to environment
			// for QA1: 1 and 2 , for UAT2 2 and 3
			String fullPayPrem = requestBindPage.payPlanPremium.formatDynamicPath("1").getData()
					.replace("First Payment: $", "").replace(",", "");
			double d_fullPayPrem = Double.parseDouble(fullPayPrem);
			String mortgageePayPrem = requestBindPage.payPlanPremium.formatDynamicPath("2").getData()
					.replace("First Payment: $", "").replace(",", "");
			double d_mortgageePayPrem = Double.parseDouble(mortgageePayPrem);

			// Verifying if the Calculated Total Premium Value is same as the Premium Value
			// on the Request Bind Page
			Assertions.addInfo("Scenario 06",
					"Verifying if the Calculated Total Premium Value is same as the Premium Value on the Request Bind Page");

			if (Precision.round(Math.abs(Precision.round(d_fullPayPrem, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Request Bind Page",
						"The Calculated Full Pay Premium Value on the Request Bind Page is : " + "$"
								+ Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("Request Bind Page",
						"The Actual Full Pay Premium Value on the Request Bind Page is : " + "$" + d_fullPayPrem);
			}

			else {
				Assertions.verify(d_fullPayPrem, calcTotalPremiumValue, "Request Bind Page",
						"The Actual Full Pay Premium Value on the Request Bind Page is not same as the Calculated Premium Value",
						false, false);
			}

			if (Precision.round(
					Math.abs(Precision.round(d_mortgageePayPrem, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Request Bind Page",
						"The Calculated Mortgagee Pay Premium Value on the Request Bind Page is : " + "$"
								+ Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("Request Bind Page",
						"The Actual Mortgagee Pay Premium Value on the Request Bind Page is : " + "$"
								+ d_mortgageePayPrem);
			}

			else {
				Assertions.verify(d_mortgageePayPrem, calcTotalPremiumValue, "Request Bind Page",
						"The Actual Mortgagee Pay Premium Value on the Request Bind Page is not same as the Calculated Premium Value",
						false, false);
			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Asserting VIE Message
			Assertions.verify(requestBindPage.vieMessage.checkIfElementIsPresent(), true, "Request Bind Page",
					requestBindPage.vieMessage.getData(), false, false);
			waitTime(3);
			if (requestBindPage.pageName.getData().contains("Quote Documents")) {
				policyDocumentsPage.addDocumentButton.scrollToElement();
				policyDocumentsPage.addDocumentButton.click();

				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
				waitTime(5);// adding wait time to load the element
				policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
				policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
				policyDocumentsPage.backButton.scrollToElement();
				policyDocumentsPage.backButton.click();
				if (accountOverviewPage.requestBind.checkIfElementIsPresent()
						&& accountOverviewPage.requestBind.checkIfElementIsDisplayed()) {
					accountOverviewPage.requestBind.scrollToElement();
					accountOverviewPage.requestBind.click();
				} else {
					requestBindPage.requestBind.scrollToElement();
					requestBindPage.requestBind.click();
				}
			}

			if (!testData.get("InsuredName").equals("")) {
				requestBindPage.namedInsured.setData(testData.get("InsuredName"));
				requestBindPage.namedInsured.tab();
				waitTime(3); // If waittime is removed,Element Not Interactable
								// exception is
				// thrown.Waittillpresence and Waittillvisibility is not working
				// here

				if (requestBindPage.no_NameChange.checkIfElementIsPresent()
						&& requestBindPage.no_NameChange.checkIfElementIsDisplayed()) {
					requestBindPage.no_NameChange.waitTillPresenceOfElement(60);
					requestBindPage.no_NameChange.waitTillVisibilityOfElement(60);
					requestBindPage.no_NameChange.scrollToElement();
					requestBindPage.no_NameChange.click();
					requestBindPage.no_NameChange.waitTillInVisibilityOfElement(60);
				}
			}

			if (requestBindPage.chooseFile.checkIfElementIsPresent()
					&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
			}

			if (requestBindPage.floodCoverageArrow.checkIfElementIsPresent()
					&& requestBindPage.floodCoverageArrow.checkIfElementIsDisplayed()) {
				requestBindPage.floodCoverageArrow.scrollToElement();
				requestBindPage.floodCoverageArrow.waitTillPresenceOfElement(60);// Added
																					// wait
				// time
				// to
				// counter the failure
				// due to Element not
				// interactable
				requestBindPage.floodCoverageArrow.click();
				requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy"))
						.scrollToElement();
				requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy")).click();
			}

			if (testData.get("ChangeInspectionContactDetails").equalsIgnoreCase("Yes")) {
				requestBindPage.addInspectionContact(testData);
			}

			if (testData.get("ChangeProducerContactDetails").equalsIgnoreCase("Yes")) {
				requestBindPage.addContactInformation(testData);
			}

			if (requestBindPage.dueDiligenceCheckbox.checkIfElementIsPresent()
					&& requestBindPage.dueDiligenceCheckbox.checkIfElementIsDisplayed()) {
				requestBindPage.dueDiligenceCheckbox.scrollToElement();
				requestBindPage.dueDiligenceCheckbox.select();
			}
			waitTime(2); // Added wait time as not clicking on submit button in headless
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			waitTime(2);

			// Fetching Confirm Bind Page Premium Value
			String quotePremCBP = confirmBindRequestPage.quotePremium.getData().replace("Grand Total: $", "")
					.replace(",", "");
			System.out.println(quotePremCBP);
			Double quotePremCBPVlaue = Double.parseDouble(quotePremCBP);

			// Validating Premium Values on Confirm Bind Page

			if (Precision.round(
					Math.abs(Precision.round(quotePremCBPVlaue, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Confirm Bind Page",
						"The Calculated Total Premium value on the left pane of Confirm Bind Page is : " + "$"
								+ Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("Confirm Bind Page",
						"The Actual Total Premium value on the left pane of Confirm Bind Page is : " + "$"
								+ quotePremCBPVlaue);
			}

			else {
				Assertions.verify(quotePremCBPVlaue, calcTotalPremiumValue, "Confirm Bind Page",
						"Calculated Total Premium value does not match with the Total Premium value on the left pane of Confirm Bind Page",
						false, false);
			}

			requestBindPage.confirmBindNAHO(testData);
			Assertions.passTest("Request Bind Page", "Renewal bind details entered successfully");

			// Getting renewal policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully. Renewal PolicyNumber is : " + policyNumber, false, false);

			// Fetching Values to Verify Surplus Contribution
			// Commenting these lines as Reciprocal will not be active Please uncomment once
			// Reciprocal for Renewal is Back
			String termSurplusContributionValue = policySummaryPage.termSurplusContribution.getData().replace("$", "")
					.replace("%", "");
			double d_termSurplusContributionValue = Double.parseDouble(termSurplusContributionValue);
			String annualSurplusContributionValue = policySummaryPage.annualSurplusContribution.getData()
					.replace("$", "").replace("%", "");
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
			Assertions.addInfo("Scenario 07",
					"Validating the Surplsus Contribution Values present on Policy Summary Page"
							+ " are matching the Calculated Surplus Contribution Value");

			if (Precision.round(Math.abs(Precision.round(d_termSurplusContributionValue, 2)
					- Precision.round(calcSurplusContributionValue, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "The Calculated Term Surplus Contribution value is : " + "$"
						+ Precision.round(calcSurplusContributionValue, 2));
				Assertions.passTest("Policy Summary Page",
						"The Actual Term Surplus Contribution value is : " + "$" + d_termSurplusContributionValue);
			}

			else {
				Assertions.verify(d_termSurplusContributionValue, calcSurplusContributionValue, "Policy Summary Page",
						"The Term Surplus Contribution value is not same as the Calculated Surplus Contribution Value",
						false, false);
			}

			if (Precision.round(Math.abs(Precision.round(d_annualSurplusContributionValue, 2)
					- Precision.round(calcSurplusContributionValue, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "The Calculated Annual Surplus Contribution value is : "
						+ "$" + Precision.round(calcSurplusContributionValue, 2));
				Assertions.passTest("Policy Summary Page",
						"The Actual Annual Surplus Contribution value is : " + "$" + d_annualSurplusContributionValue);
			} else {
				Assertions.verify(d_annualSurplusContributionValue, calcSurplusContributionValue, "Policy Summary Page",
						"The Annual Surplus Contribution value is not same as the Calculated Surplus Contribution Value",
						false, false);
			}

			if (Precision.round(Math.abs(Precision.round(d_transactionSurplusContributionValue, 2)
					- Precision.round(calcSurplusContributionValue, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "The Calculated Transaction Surplus Contribution value is : "
						+ "$" + Precision.round(calcSurplusContributionValue, 2));
				Assertions.passTest("Policy Summary Page", "The Actual Transaction Surplus Contribution value is : "
						+ "$" + d_transactionSurplusContributionValue);
			}

			else {
				Assertions.verify(d_transactionSurplusContributionValue, calcSurplusContributionValue,
						"Policy Summary Page",
						"The Transaction Surplus Contribution value is not same as the Calculated Surplus Contribution Value",
						false, false);
			}

			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			Assertions.addInfo("Scenario 8",
					"Validating the Surplsus Contribution Values present on Policy Summary Page"
							+ " are matching the Calculated Surplus Contribution Value");

			if (Precision.round(
					Math.abs(Precision.round(d_termtotalPremiumValue, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"The Calculated Term Total Premium is : " + "$" + Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("Policy Summary Page",
						"The Actual Term Total Premium is : " + "$" + d_termtotalPremiumValue);
			}

			else {
				Assertions.verify(d_termtotalPremiumValue, calcTotalPremiumValue, "Policy Summary Page",
						"The Term Total Premium is not same as the Calculated Total Premium", false, false);
			}

			if (Precision.round(
					Math.abs(Precision.round(d_annualtotalPremiumValue, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"The Calculated Annual Total Premium is : " + "$" + Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("Policy Summary Page",
						"The Actual Annual Total Premium is : " + "$" + d_annualtotalPremiumValue);
			}

			else {
				Assertions.verify(d_annualtotalPremiumValue, calcTotalPremiumValue, "Policy Summary Page",
						"The Annual Total Premium is not same as the Calculated Total Premium", false, false);
			}

			if (Precision.round(
					Math.abs(Precision.round(d_transactionPremiumValue, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "The Calculated Transaction Total Premium is : " + "$"
						+ Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("Policy Summary Page",
						"The Actual Transaction Total Premium is : " + "$" + d_transactionPremiumValue);
			}

			else {
				Assertions.verify(d_transactionPremiumValue, calcTotalPremiumValue, "Policy Summary Page",
						"The Transaction Total Premium is not same as the Calculated Total Premium", false, false);
			}

			// Click on view policy document
			policySummaryPage.viewDocuments.scrollToElement();
			policySummaryPage.viewDocuments.click();
			Assertions.verify(
					policyDocumentsPage.addDocumentButton.checkIfElementIsPresent()
							&& policyDocumentsPage.addDocumentButton.checkIfElementIsDisplayed(),
					true, "Policy Documents Page", "Policy Documents page loaded successfully", false, false);
			policyDocumentsPage.addDocumentButton.scrollToElement();
			policyDocumentsPage.addDocumentButton.click();

			if (!testData.get("FileNameToUpload").equals("")) {
				String fileName = testData.get("FileNameToUpload");
				if (StringUtils.isBlank(fileName)) {
					Assertions.failTest("Upload File", "Filename is blank");
				}
				String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
				waitTime(8);
				if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
						&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
					policyDocumentsPage.chooseDocument.setData(new File(uploadFileDir + fileName).getAbsolutePath());
					waitTime(5);// Adding wait time to load the element
					System.out.println("Choose document");
				} else {
					policyDocumentsPage.chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
					waitTime(5);// Adding wait time to load the element

				}
			}

			// Select due diligence form from drop down
			waitTime(3);
			policyDocumentsPage.documentCategoryArrow.waitTillPresenceOfElement(60);
			policyDocumentsPage.documentCategoryArrow.waitTillVisibilityOfElement(60);
			policyDocumentsPage.documentCategoryArrow.scrollToElement();
			policyDocumentsPage.documentCategoryArrow.click();

			waitTime(3);
			policyDocumentsPage.documentCategoryOptions.formatDynamicPath("Due Diligence").scrollToElement();
			policyDocumentsPage.documentCategoryOptions.formatDynamicPath("Due Diligence").click();

			// Verifying the presence of due diligence message "Please be sure to include
			// both the SL8 and CT Due Diligence forms as one file under the Due Diligence
			// category."
			Assertions.addInfo("Scenario 09",
					"Verifying the presence of due diligence message when document category = Due Diligence");
			Assertions.verify(
					policyDocumentsPage.dueDiligenceFormMessage.formatDynamicPath("SL8 and CT Due Diligence")
							.checkIfElementIsPresent()
							&& policyDocumentsPage.dueDiligenceFormMessage.formatDynamicPath("SL8 and CT Due Diligence")
									.checkIfElementIsDisplayed(),
					policyDocumentsPage.dueDiligenceFormMessage.formatDynamicPath("SL8 and CT Due Diligence").getData()
							.contains("SL8 and CT Due Diligence"),
					"Policy Documents Page", "Due Diligence message is " + policyDocumentsPage.dueDiligenceFormMessage
							.formatDynamicPath("SL8 and CT Due Diligence").getData() + "displayed",
					false, false);
			Assertions.addInfo("Scenario 09 ", "Scenario 09 Ended");
			policyDocumentsPage.cancelButton.scrollToElement();
			policyDocumentsPage.cancelButton.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBCTTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBCTTC002 ", "Executed Successfully");
			}
		}
	}

}
