/** Program Description: Verify the Following
 * 1) Initiate the Non flat cancellation and verify the Prorata min earned option and prorata factor value for Maryland
 * 2) Initiate A Cancellation and Check the if the Calculated Surplus Contribution is same as the Actual Surplus Contribution Values on the Cancellation

 *  Author			   : Sowndarya NH
 *  Date of Creation   : 03/01/2022
 **/

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RmsModelResultsPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBMDTC001 extends AbstractNAHOTest {

	public PNBMDTC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/MDTC001.xls";
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
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		RmsModelResultsPage rmsModelResultsPage = new RmsModelResultsPage();

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int quoteLen;
		String quoteNumber, rewriteQuoteNumber;
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

			// Getting the Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Fetching Reciprocal Participation Percent and Contribution Charge Values
			accountOverviewPage.quoteSpecifics.click();
			String vieParticpationPercent = accountOverviewPage.vieParticipationValue.getData().replace("%", "");
			double d_vieParticpationPercent = Double.parseDouble(vieParticpationPercent) / 100;
			String vieContributionCharge = accountOverviewPage.vieContributionChargeValue.getData().replace("%", "");
			double d_vieContributionCharge = Double.parseDouble(vieContributionCharge) / 100;

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

			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Opening the Referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);

			// IO-21792-As part of this our expectation is to validate if the referred quote
			// is assigned to USM not "Holder RMS"
			if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {

				Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true, "Referral Page",
						"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);
			} else {

				Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false, "Referral Page",
						"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);
			}
			// Ended

			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind page loaded successfully", false, false);
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Validating the premium amount
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on Cancel Policy link
			policySummaryPage.cancelPolicy.waitTillVisibilityOfElement((60));
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

			// Selecting Cancel Reason
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page loaded successfully", false, false);
			testData = data.get(data_Value2);
			Assertions.addInfo("Scenario 01",
					"Initiating a Non Flat Cancellation with Insured’s Request as Cancellation Reason and Cancellation Effective Date as 90 days from Policy Effective Date");
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.waitTime(3);// if wait time is removed test case will fail here
			String cancelReason = cancelPolicyPage.cancelReasonOption
					.formatDynamicPath(testData.get("CancellationReason")).getData();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
			Assertions.passTest("Cancel Policy Page", "The Cancellation Reason Selected is " + cancelReason);
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Next Button");
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Adding Scenario 02
			Assertions.addInfo("Scenario 02",
					"Verifying the presence of Pro-Rated Min Earned Option and Prorata Factor under Pro-Rated Min Earned Option is 0.75");
			Assertions.verify(cancelPolicyPage.proRataRadioBtn.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Pro-Rated Min Earned Option displayed is verified", false, false);
			Assertions.verify(cancelPolicyPage.prorataFactor.formatDynamicPath("2").getData(), "0.75",
					"Cancel Policy Page", "The Prorata Factor displayed for Pro-Rated Min Earned Option is "
							+ cancelPolicyPage.prorataFactor.formatDynamicPath("2").getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on previous button
			cancelPolicyPage.previousButton.scrollToElement();
			cancelPolicyPage.previousButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Previous Button");

			// Changing the Cancellation Effective Date as Current Date + 93 Days
			testData = data.get(data_Value3);
			cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
			Assertions.passTest("Cancel Policy Page",
					"Entered the Cancellation Effective date as Current date + 93 days");
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Next Button");

			// Adding Scenario 03
			Assertions.addInfo("Scenario 03", "Verifying the absence of Pro-Rated Min Earned Option");
			Assertions.verify(cancelPolicyPage.proRataRadioBtn.checkIfElementIsPresent(), false, "Cancel Policy Page",
					"Pro-Rated Min Earned Option not displayed is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on complete transaction
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Complete Transaction Button");

			// click on close
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Close Button");

			// Adding Scenario 04
			Assertions.addInfo("Scenario 04", "Verifying the Status of the Policy on Policy Summary Page");
			Assertions.verify(policySummaryPage.policyStatus.getData(), "NOC", "Policy Summary Page",
					"The Policy Status " + policySummaryPage.policyStatus.getData() + " displayed is verified", false,
					false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Verifying the Renewal Indicator and Renewal Hyper link on Policy Summary Page
			// For Non-Renewal Scenarios
			Assertions.addInfo("Policy Summary Page",
					"Verifying the Renewal Indicator and Renewal Hyper link on Policy Summary Page For Non-Renewal Scenarios");

			// Verifying the Renewal link not available on cancelled policy Summary
			Assertions.addInfo("Policy Summary Page", "Renewal Policy Link should not diasplayed");
			Assertions.verify(!policySummaryPage.renewPolicy.checkIfElementIsPresent(), true, "Policy Summary Page",
					"Renewal Policy Link not diasplayed", false, false);

			// Verifying the Renewal Indicators link not available on cancelled policy
			// Summary
			Assertions.addInfo("Policy Summary Page", "Renewal Indicators Policy Link should not diasplayed");
			Assertions.verify(!policySummaryPage.renewalIndicators.checkIfElementIsPresent(), true,
					"Policy Summary Page", "Renewal Indicators Policy Link not diasplayed", false, false);

			// Reinstating the Policy
			policySummaryPage.reinstatePolicy.checkIfElementIsPresent();
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);

			// Canceling the Policy to Check the Reciprocal Surplus Contribution values for
			// different cancellation reasons
			policySummaryPage.cancelPolicy.checkIfElementIsPresent();
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();

			// Checking the Reciprocal Surplus Contribution values for different
			// cancellation reasons
			for (int i = 3; i < 14; i++) {

				int dataValuei = i;
				testData = data.get(dataValuei);
				cancelPolicyPage.cancelReasonArrow.scrollToElement();
				cancelPolicyPage.cancelReasonArrow.click();
				String cancelationReason = cancelPolicyPage.cancelReasonOption
						.formatDynamicPath(testData.get("CancellationReason")).getData();
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason"))
						.scrollToElement();
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
				cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
				Assertions.passTest("Cancel Policy Page", "The Cancel Reason Selected is " + cancelationReason);
				waitTime(3);
				cancelPolicyPage.nextButton.checkIfElementIsPresent();
				cancelPolicyPage.nextButton.scrollToElement();
				cancelPolicyPage.nextButton.click();

				// Fetching Values for Surplus Contribution Calculation and Validations
				String originalPremium = cancelPolicyPage.newPremiumNAHO.formatDynamicPath("1").getData()
						.replace("$", "").replace(",", "");
				Double d_originalPremium = Precision.round(Double.parseDouble(originalPremium), 2);
				String originalInspFee = cancelPolicyPage.inspectionFee1.formatDynamicPath(1).getData().replace("$", "")
						.replace(",", "");
				Double d_originalInspFee = Precision.round(Double.parseDouble(originalInspFee), 2);
				String originalPolicyFee = cancelPolicyPage.policyFeeNAHO.formatDynamicPath("1").getData()
						.replace("$", "").replace(",", "");
				Double d_originalPolicyFee = Precision.round(Double.parseDouble(originalPolicyFee), 2);
				String originalSLTF = cancelPolicyPage.SLTF.formatDynamicPath("1").getData().replace("$", "")
						.replace(",", "");
				Double d_originalSLTF = Precision.round(Double.parseDouble(originalSLTF), 2);
				String originalSCValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath("1").getData()
						.replace("$", "").replace("%", "");
				Double d_originalSCValue = Precision.round(Double.parseDouble(originalSCValue), 2);
				String originalTotalPrem = cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath("1").getData()
						.replace("$", "").replace(",", "");
				Double d_originalTotalPrem = Precision.round(Double.parseDouble(originalTotalPrem), 2);
				String earnedPremium = cancelPolicyPage.newPremiumNAHO.formatDynamicPath("2").getData().replace("$", "")
						.replace(",", "");
				Double d_earnedPremium = Precision.round(Double.parseDouble(earnedPremium), 2);

				String earnedInspFee = cancelPolicyPage.newInspectionFee.formatDynamicPath(2).getData();
				Double d_earnedInspFee = Precision.round(Double.parseDouble(earnedInspFee), 2);
				String earnedPolicyFee = cancelPolicyPage.newPolicyFee.formatDynamicPath("2").getData().replace("$", "")
						.replace(",", "");
				Double d_earnedPolicyFee = Precision.round(Double.parseDouble(earnedPolicyFee), 2);

				String earnedSLTF = cancelPolicyPage.SLTF.formatDynamicPath("2").getData().replace("$", "").replace(",",
						"");
				Double d_earnedSLTF = Precision.round(Double.parseDouble(earnedSLTF), 2);
				String earnedSCValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath("2").getData()
						.replace("$", "").replace("%", "").replace(",", "");
				Double d_earnedSCValue = Precision.round(Double.parseDouble(earnedSCValue), 2);
				String earnedTotalPrem = cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath("2").getData()
						.replace("$", "").replace(",", "");
				Double d_earnedTotalPrem = Precision.round(Double.parseDouble(earnedTotalPrem), 2);
				String returnedPremium = cancelPolicyPage.newPremiumNAHO.formatDynamicPath("3").getData()
						.replace("$", "").replace("-", "").replace(",", "");
				Double d_returnedPremium = Precision.round(Double.parseDouble(returnedPremium), 2);
				String returnedInspFee = cancelPolicyPage.inspectionFee1.formatDynamicPath("3").getData()
						.replace("$", "").replace(",", "");
				Double d_returnedInspFee = Precision.round(Double.parseDouble(returnedInspFee), 2);
				String returnedPolicyFee = cancelPolicyPage.policyFeeNAHO.formatDynamicPath("3").getData()
						.replace("$", "").replace(",", "");
				Double d_returnedPolicyFee = Precision.round(Double.parseDouble(returnedPolicyFee), 2);
				String returnedSLTF = cancelPolicyPage.SLTF.formatDynamicPath("3").getData().replace("$", "")
						.replace("-", "").replace(",", "");
				Double d_returnedSLTF = Precision.round(Double.parseDouble(returnedSLTF), 2);
				String returnedSCValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath("3").getData()
						.replace("$", "").replace("-", "").replace("%", "").replace(",", "");
				Double d_returnedSCValue = Precision.round(Double.parseDouble(returnedSCValue), 2);
				String returnedTotalPrem = cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath("3").getData()
						.replace("$", "").replace("-", "").replace(",", "");
				Double d_returnedTotalPrem = Precision.round(Double.parseDouble(returnedTotalPrem), 2);

				// Calculating Surplus Contribution Values
				double calcOriginalSCValue = Precision
						.round((d_vieParticpationPercent * d_vieContributionCharge * (d_originalPremium)), 2);
				double calcEarnedSCValue = Precision
						.round((d_vieParticpationPercent * d_vieContributionCharge * (d_earnedPremium)), 2);
				double calcReturnedSCValue = Precision
						.round((d_vieParticpationPercent * d_vieContributionCharge * (d_returnedPremium)), 2);

				// Calculating Total Premium Values
				double calcTotalOriginalPremium = Precision.round((d_originalPremium + d_originalInspFee
						+ d_originalPolicyFee + d_originalSLTF + d_originalSCValue), 2);
				double calcTotalearnedPremium = Precision.round(
						(d_earnedPremium + d_earnedInspFee + d_earnedPolicyFee + d_earnedSLTF + d_earnedSCValue), 2);
				double calcTotalreturnedPremium = Precision.round((d_returnedPremium + d_returnedInspFee
						+ d_returnedPolicyFee + d_returnedSLTF + d_returnedSCValue), 2);

				// Validating if the Actual and Calculated Values are same
				Assertions.addInfo("Scenario 05", "Validating if the Actual Surplus Contribution, Actual Total Premium "
						+ "and Calculated Surplus Contribution, Calculated Total Premium Values are same");
				if (calcOriginalSCValue == d_originalSCValue) {
					Assertions.verify(d_originalSCValue, calcOriginalSCValue, "Cancel Policy Page",
							"The Actual Original Surplus Contribution and The Calculated Surplus "
									+ "Contribution Values are matching",
							false, false);
				} else {
					Assertions.verify(d_originalSCValue, calcOriginalSCValue, "Cancel Policy Page",
							"The Actual Original Surplus Contribution and The Calculated Surplus "
									+ "Contribution Values are not matching",
							false, false);
				}
				if (calcEarnedSCValue == d_earnedSCValue) {
					Assertions.verify(d_earnedSCValue, calcEarnedSCValue, "Cancel Policy Page",
							"The Actual Earned Surplus Contribution and The Calculated Surplus "
									+ "Contribution Values are matching",
							false, false);
				} else {
					Assertions.verify(d_earnedSCValue, calcEarnedSCValue, "Cancel Policy Page",
							"The Actual Earned Surplus Contribution and The Calculated Surplus "
									+ "Contribution Values are not matching",
							false, false);
				}
				if (calcReturnedSCValue == d_returnedSCValue) {
					Assertions.verify(d_returnedSCValue, calcReturnedSCValue, "Cancel Policy Page",
							"The Actual Returned Surplus Contribution and The Calculated Surplus "
									+ "Contribution Values are matching",
							false, false);
				} else {
					Assertions.verify(d_returnedSCValue, calcReturnedSCValue, "Cancel Policy Page",
							"The Actual Returned Surplus Contribution and The Calculated Surplus "
									+ "Contribution Values are not matching",
							false, false);
				}

				if (calcTotalOriginalPremium == d_originalTotalPrem) {
					Assertions.verify(d_originalTotalPrem, calcTotalOriginalPremium, "Cancel Policy Page",
							"The Actual Original Total Premium and The Calculated Total Premium Values are matching",
							false, false);
				} else {
					Assertions.verify(d_originalTotalPrem, calcTotalOriginalPremium, "Cancel Policy Page",
							"The Actual Original Total Premium and The Calculated Total Premium Values are not matching",
							false, false);
				}
				if (calcTotalearnedPremium == d_earnedTotalPrem) {
					Assertions.verify(d_earnedTotalPrem, calcTotalearnedPremium, "Cancel Policy Page",
							"The Actual Earned Total Premium and The Calculated Total Premium Values are matching",
							false, false);
				} else {
					Assertions.verify(d_earnedTotalPrem, calcTotalearnedPremium, "Cancel Policy Page",
							"The Actual Earned Total Premium and The Calculated Total Premium Values are not matching",
							false, false);
				}
				if (calcTotalreturnedPremium == d_returnedTotalPrem) {
					Assertions.verify(d_returnedTotalPrem, calcTotalreturnedPremium, "Cancel Policy Page",
							"The Actual Returned Total Premium and The Calculated Total Premium Values are matching",
							false, false);
				} else {
					Assertions.verify(d_returnedTotalPrem, calcTotalOriginalPremium, "Cancel Policy Page",
							"The Actual Returned Total Premium and The Calculated Total Premium Values are not matching",
							false, false);
				}

				// Click on Previous Button
				cancelPolicyPage.previousButton.scrollToElement();
				cancelPolicyPage.previousButton.click();

			}

			// Canceling the Policy after all checks at Cancel Policy Page
			testData = data.get(data_Value1);
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			String cancelationReason = cancelPolicyPage.cancelReasonOption
					.formatDynamicPath(testData.get("CancellationReason")).getData();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			Assertions.passTest("Cancel Policy Page", "The Cancel Reason Selected is " + cancelationReason);
			waitTime(3);
			cancelPolicyPage.nextButton.checkIfElementIsPresent();
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Fetching the Values to Calculate Surplus Contribution and Total Premium
			// Values
			String summaryOriginalPremium = cancelPolicyPage.newPremiumNAHO.formatDynamicPath("1").getData()
					.replace("$", "").replace(",", "");
			Double d_summaryOriginalPremium = Precision.round(Double.parseDouble(summaryOriginalPremium), 2);
			String summaryOriginalInspFee = cancelPolicyPage.inspectionFee1.formatDynamicPath("1").getData()
					.replace("$", "").replace(",", "");
			Double d_summaryOriginalInspFee = Precision.round(Double.parseDouble(summaryOriginalInspFee), 2);
			String summaryOriginalPolicyFee = cancelPolicyPage.policyFeeNAHO.formatDynamicPath("1").getData()
					.replace("$", "").replace(",", "");
			Double d_summaryOriginalPolicyFee = Precision.round(Double.parseDouble(summaryOriginalPolicyFee), 2);
			String summaryOriginalSLTF = cancelPolicyPage.SLTF.formatDynamicPath("1").getData().replace("$", "")
					.replace(",", "");
			Double d_summaryOriginalSLTF = Precision.round(Double.parseDouble(summaryOriginalSLTF), 2);
			String summaryOriginalSCValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath("1").getData()
					.replace("$", "").replace("%", "").replace(",", "");
			Double d_summaryOriginalSCValue = Precision.round(Double.parseDouble(summaryOriginalSCValue), 2);
			String summaryOriginalTotalPrem = cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath("1").getData()
					.replace("$", "").replace(",", "");
			Double d_summaryOriginalTotalPrem = Precision.round(Double.parseDouble(summaryOriginalTotalPrem), 2);
			String summaryEarnedPremium = cancelPolicyPage.newPremiumNAHO.formatDynamicPath("2").getData()
					.replace("$", "").replace(",", "");
			Double d_summaryEarnedPremium = Precision.round(Double.parseDouble(summaryEarnedPremium), 2);
			String summaryEarnedInspFee = cancelPolicyPage.newInspectionFee.formatDynamicPath(2).getData();

			Double d_summaryEarnedInspFee = Precision.round(Double.parseDouble(summaryEarnedInspFee), 2);
			String summaryEarnedPolicyFee = cancelPolicyPage.newPolicyFee.formatDynamicPath("2").getData()
					.replace("$", "").replace(",", "");
			Double d_summaryEarnedPolicyFee = Precision.round(Double.parseDouble(summaryEarnedPolicyFee), 2);
			String summaryEarnedSLTF = cancelPolicyPage.newSLTF.formatDynamicPath("2").getData().replace("$", "")
					.replace(",", "");
			Double d_summaryEarnedSLTF = Precision.round(Double.parseDouble(summaryEarnedSLTF), 2);
			String summaryEarnedSCValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath("2").getData()
					.replace("$", "").replace("%", "").replace(",", "");
			Double d_summaryEarnedSCValue = Precision.round(Double.parseDouble(summaryEarnedSCValue), 2);
			String summaryEarnedTotalPrem = cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath("2").getData()
					.replace("$", "").replace(",", "");
			Double d_summaryEarnedTotalPrem = Precision.round(Double.parseDouble(summaryEarnedTotalPrem), 2);
			String summaryReturnedPremium = cancelPolicyPage.newPremiumNAHO.formatDynamicPath("3").getData()
					.replace("$", "").replace("-", "").replace(",", "");
			Double d_summaryReturnedPremium = Precision.round(Double.parseDouble(summaryReturnedPremium), 2);
			String summaryReturnedInspFee = cancelPolicyPage.inspectionFee1.formatDynamicPath("3").getData()
					.replace("$", "").replace(",", "");
			Double d_summaryReturnedInspFee = Precision.round(Double.parseDouble(summaryReturnedInspFee), 2);
			String summaryReturnedPolicyFee = cancelPolicyPage.policyFeeNAHO.formatDynamicPath("3").getData()
					.replace("$", "").replace(",", "");
			Double d_summaryReturnedPolicyFee = Precision.round(Double.parseDouble(summaryReturnedPolicyFee), 2);
			String summaryReturnedSLTF = cancelPolicyPage.SLTF.formatDynamicPath("3").getData().replace("$", "")
					.replace("-", "").replace(",", "");
			Double d_summaryReturnedSLTF = Precision.round(Double.parseDouble(summaryReturnedSLTF), 2);
			String summaryReturnedSCValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath("3").getData()
					.replace("$", "").replace("-", "").replace("%", "".replace(",", ""));
			Double d_summaryReturnedSCValue = Precision.round(Double.parseDouble(summaryReturnedSCValue), 2);
			String summaryReturnedTotalPrem = cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath("3").getData()
					.replace("$", "").replace("-", "").replace(",", "");
			Double d_summaryReturnedTotalPrem = Precision.round(Double.parseDouble(summaryReturnedTotalPrem), 2);

			// Calculating Surplus Contribution Values
			double calcOriginalSCValue = Precision
					.round((d_vieParticpationPercent * d_vieContributionCharge * (d_summaryOriginalPremium)), 2);
			double calcEarnedSCValue = Precision
					.round((d_vieParticpationPercent * d_vieContributionCharge * (d_summaryEarnedPremium)), 2);
			double calcReturnedSCValue = Precision
					.round((d_vieParticpationPercent * d_vieContributionCharge * (d_summaryReturnedPremium)), 2);

			// Calculating Total Premium Values
			double calcSummaryTotalOriginalPremium = Precision
					.round((d_summaryOriginalPremium + d_summaryOriginalInspFee + d_summaryOriginalPolicyFee
							+ d_summaryOriginalSLTF + d_summaryOriginalSCValue), 2);
			double calcSummaryTotalearnedPremium = Precision.round((d_summaryEarnedPremium + d_summaryEarnedInspFee
					+ d_summaryEarnedPolicyFee + d_summaryEarnedSLTF + d_summaryEarnedSCValue), 2);
			double calcSummaryTotalreturnedPremium = Precision
					.round((d_summaryReturnedPremium + d_summaryReturnedInspFee + d_summaryReturnedPolicyFee
							+ d_summaryReturnedSLTF + d_summaryReturnedSCValue), 2);

			// Validating if the Actual and Calculated Values are same
			Assertions.addInfo("Scenario 06", "Validating if the Actual Surplus Contribution, Actual Total Premium "
					+ "and Calculated Surplus Contribution, Calculated Total Premium Values are same");
			if (calcOriginalSCValue == d_summaryOriginalSCValue) {
				Assertions.verify(d_summaryOriginalSCValue, calcOriginalSCValue, "Cancel Policy Page",
						"The Actual Original Surplus Contribution and The Calculated Surplus "
								+ "Contribution Values are matching",
						false, false);
			} else {
				Assertions.verify(d_summaryOriginalSCValue, calcOriginalSCValue, "Cancel Policy Page",
						"The Actual Original Surplus Contribution and The Calculated Surplus "
								+ "Contribution Values are not matching",
						false, false);
			}
			if (calcEarnedSCValue == d_summaryEarnedSCValue) {
				Assertions.verify(d_summaryEarnedSCValue, calcEarnedSCValue, "Cancel Policy Page",
						"The Actual Earned Surplus Contribution and The Calculated Surplus "
								+ "Contribution Values are matching",
						false, false);
			} else {
				Assertions.verify(d_summaryEarnedSCValue, calcEarnedSCValue, "Cancel Policy Page",
						"The Actual Earned Surplus Contribution and The Calculated Surplus "
								+ "Contribution Values are not matching",
						false, false);
			}
			if (calcReturnedSCValue == d_summaryReturnedSCValue) {
				Assertions.verify(d_summaryReturnedSCValue, calcReturnedSCValue, "Cancel Policy Page",
						"The Actual Returned Surplus Contribution and The Calculated Surplus "
								+ "Contribution Values are matching",
						false, false);
			} else {
				Assertions.verify(d_summaryReturnedSCValue, calcReturnedSCValue, "Cancel Policy Page",
						"The Actual Returned Surplus Contribution and The Calculated Surplus "
								+ "Contribution Values are not matching",
						false, false);
			}

			if (calcSummaryTotalOriginalPremium == d_summaryOriginalTotalPrem) {
				Assertions.verify(d_summaryOriginalTotalPrem, calcSummaryTotalOriginalPremium, "Cancel Policy Page",
						"The Actual Original Total Premium and The Calculated Total Premium Values are matching", false,
						false);
			} else {
				Assertions.verify(d_summaryOriginalTotalPrem, calcSummaryTotalOriginalPremium, "Cancel Policy Page",
						"The Actual Original Total Premium and The Calculated Total Premium Values are not matching",
						false, false);
			}
			if (calcSummaryTotalearnedPremium == d_summaryEarnedTotalPrem) {
				Assertions.verify(d_summaryEarnedTotalPrem, calcSummaryTotalearnedPremium, "Cancel Policy Page",
						"The Actual Earned Total Premium and The Calculated Total Premium Values are matching", false,
						false);
			} else {
				Assertions.verify(d_summaryEarnedTotalPrem, calcSummaryTotalearnedPremium, "Cancel Policy Page",
						"The Actual Earned Total Premium and The Calculated Total Premium Values are not matching",
						false, false);
			}
			if (calcSummaryTotalreturnedPremium == d_summaryReturnedTotalPrem) {
				Assertions.verify(d_summaryReturnedTotalPrem, calcSummaryTotalreturnedPremium, "Cancel Policy Page",
						"The Actual Returned Total Premium and The Calculated Total Premium Values are matching", false,
						false);
			} else {
				Assertions.verify(d_summaryReturnedTotalPrem, calcSummaryTotalreturnedPremium, "Cancel Policy Page",
						"The Actual Returned Total Premium and The Calculated Total Premium Values are not matching",
						false, false);
			}

			// Completing the Cancellation
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Complete Transaction Button");

			// click on close
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Close Button");

			// ReInstating the Policy
			policySummaryPage.reinstatePolicy.checkIfElementIsPresent();
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);

			// Added IO-21846
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

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting the re-write Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			rewriteQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Re-write Quote Number is : " + rewriteQuoteNumber);

			// Adding Guy Carpenter scenario TC02
			Assertions.addInfo("Scenario 01 ",
					"Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL Labels and it's values for Re-written quote");

			Assertions.addInfo("Scenario 01", "Sceanrio TC02 started");
			Assertions.verify(
					accountOverviewPage.viewModelResultsLink.checkIfElementIsDisplayed()
							&& accountOverviewPage.viewModelResultsLink.checkIfElementIsPresent(),
					true, "Account Overview Page", "View Model Result Link Displayed", false, false);

			// Clicking on View Model Result Link
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();

			// Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL
			// Labels and it's values
			// Verifying the GC17 Label
			Assertions.verify(rmsModelResultsPage.gc17Label.getData().contains("GC17"), true, "View Model Results Page",
					"Label displayed on View Model Results Page is : " + rmsModelResultsPage.gc17Label.getData(), false,
					false);
			// Verifying the AAL label and it's value
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterAAL.checkIfElementIsDisplayed(),
					true, "View Model Results Page",
					"Guy Carpenter AAl Label is displayed as : " + rmsModelResultsPage.guyCarpenterAALLabel.getData()
							+ " and it's value displayed on View Model Results Page is: "
							+ rmsModelResultsPage.guyCarpenterAAL.getData(),
					false, false);
			// Verifying the ELR label and it's value
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsPresent()
							&& rmsModelResultsPage.rmsModelResultValues
									.formatDynamicPath("Peril ELR").checkIfElementIsDisplayed(),
					true, "View Model Results Page",
					"Guy Carpenter ELR Label is dasplayed as :" + rmsModelResultsPage.guyCarpenterELRLabel.getData()
							+ "and it's value displayed on View Model Results Page is: "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR").getData(),
					false, false);

			// Verifying the ELR Premium Label and it's value
			Assertions.verify(
					rmsModelResultsPage.elrPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.elrPremiumLabel.checkIfElementIsPresent(),
					true, "View Model Results Page",
					"Guy Carpenter ELR Premium Label is diaplayed as " + rmsModelResultsPage.elrPremiumLabel.getData(),
					false, false);

			// Verifying the TIV label and It's value
			Assertions.verify(
					rmsModelResultsPage.tivValue.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.tivValue.checkIfElementIsPresent(),
					true, "View Model Results Page",
					"TIV value displayed on View Model Results Page is: " + rmsModelResultsPage.tivValue.getData(),
					false, false);

			// Calculating GC ELR value
			// Verify the calculation of ELR: GC Peril ELR = GC Peril AAL amount/ELR premium
			String ELRPremium = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium").getData()
					.replace("$", "");
			String PerilAAL = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril AAL").getData()
					.replace("$", "");
			double PerilELR = Precision.round((Double.parseDouble(PerilAAL) / Double.parseDouble(ELRPremium)) * 100, 1);
			String actualPerilELRStr = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR").getData()
					.replace("%", "");
			double actualPerilELR = Double.parseDouble(actualPerilELRStr);

			Assertions.verify(PerilELR == actualPerilELR, true, "View Model Results Page",
					"The Actual and Calculated Peril ELR are matching", false, false);

			if (PerilELR == actualPerilELR) {
				Assertions.passTest("View Model Results Page", "Calculated Peril ELR: " + PerilELR + "%");
				Assertions.passTest("View Model Results Page", "Actual Peril ELR: " + actualPerilELR + "%");
			} else {
				Assertions.verify(PerilELR, actualPerilELR, "View Model Results Page",
						"The Actual and Calculated values are not matching", false, false);
			}

			// Clicking on close button on View Model Results Page
			rmsModelResultsPage.closeButton.click();
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Clicking on Alternative quote option from Other Deductible Option table
			accountOverviewPage.quoteOptions1TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions1TotalPremium.click();

			// Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL
			// Labels and it's values for Alternative quote
			Assertions.addInfo("Scenario 02 ",
					"Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL Labels and it's values for Alternative quote");
			// Clicking on View Model Result Link
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();

			// Asserting the Modeling data like GC17, ELR Premium, peril AAl and Peril ERL
			// Labels and it's values
			// Verifying the GC17 Label
			Assertions.verify(rmsModelResultsPage.gc17Label.getData().contains("GC17"), true, "View Model Results Page",
					"Label displayed on View Model Results Page is : " + rmsModelResultsPage.gc17Label.getData(), false,
					false);
			// Verifying the AAL label and it's value
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterAAL.checkIfElementIsDisplayed(),
					true, "View Model Results Page",
					"Guy Carpenter AAl Label is displayed as : " + rmsModelResultsPage.guyCarpenterAALLabel.getData()
							+ " and it's value displayed on View Model Results Page is: "
							+ rmsModelResultsPage.guyCarpenterAAL.getData(),
					false, false);

			// Verifying the ELR label and it's value
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsPresent()
							&& rmsModelResultsPage.rmsModelResultValues
									.formatDynamicPath("Peril ELR").checkIfElementIsDisplayed(),
					true, "View Model Results Page",
					"Guy Carpenter ELR Label is dasplayed as :" + rmsModelResultsPage.guyCarpenterELRLabel.getData()
							+ "and it's value displayed on View Model Results Page is: "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR").getData(),
					false, false);

			// Verifying the ELR Premium Label and it's value
			Assertions.verify(
					rmsModelResultsPage.elrPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.elrPremiumLabel.checkIfElementIsPresent(),
					true, "View Model Results Page",
					"Guy Carpenter ELR Premium Label is diaplayed as " + rmsModelResultsPage.elrPremiumLabel.getData(),
					false, false);

			// Verifying the TIV label and It's value
			Assertions.verify(
					rmsModelResultsPage.tivValue.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.tivValue.checkIfElementIsPresent(),
					true, "View Model Results Page",
					"TIV value displayed on View Model Results Page is: " + rmsModelResultsPage.tivValue.getData(),
					false, false);

			// Calculating GC ELR value
			// Verify the calculation of ELR: GC Peril ELR = GC Peril AAL amount/ELR premium
			String ELRPremium1 = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium").getData()
					.replace("$", "");
			String PerilAAL1 = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril AAL").getData()
					.replace("$", "");
			double PerilELR1 = Precision.round((Double.parseDouble(PerilAAL1) / Double.parseDouble(ELRPremium1)) * 100,
					1);
			String actualPerilELRStr1 = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR")
					.getData().replace("%", "");
			double actualPerilELR1 = Double.parseDouble(actualPerilELRStr1);

			System.out.println("PerilELR" + PerilELR);
			System.out.println("actualPerilELR" + actualPerilELR);
			Assertions.verify(PerilELR == actualPerilELR, true, "View Model Results Page",
					"The Actual and Calculated Peril ELR are matching", false, false);

			if (PerilELR == actualPerilELR) {
				Assertions.passTest("View Model Results Page", "Calculated Peril ELR: " + PerilELR1 + "%");
				Assertions.passTest("View Model Results Page", "Actual Peril ELR: " + actualPerilELR1 + "%");
			} else {
				Assertions.verify(PerilELR1, actualPerilELR1, "View Model Results Page",
						"The Actual and Calculated values are not matching", false, false);
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			rmsModelResultsPage.closeButton.waitTillVisibilityOfElement(60);
			rmsModelResultsPage.closeButton.click();

			// viewing re-writing policy
			accountOverviewPage.viewRewritingPolicy.checkIfElementIsDisplayed();
			accountOverviewPage.viewRewritingPolicy.scrollToElement();
			accountOverviewPage.viewRewritingPolicy.click();

			// clicking on stop policy re-write on policy summary page
			policySummaryPage.stopPolicyRewrite.checkIfElementIsDisplayed();
			policySummaryPage.stopPolicyRewrite.scrollToElement();
			policySummaryPage.stopPolicyRewrite.click();

			// Click on Cancel Policy
			policySummaryPage.cancelPolicy.checkIfElementIsDisplayed();
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();

			// Selecting Cancel Reasons and Cancellation Effective Date
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page loaded successfully", false, false);
			Assertions.passTest("Cancel Policy Page", "Clicked on Cancel policy link");

			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();

			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();

			testData = data.get(data_Value1);

			if (!testData.get("CancellationEffectiveDate").equals("")) {
				cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
				cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
				cancelPolicyPage.cancellationEffectiveDate.tab();
			}

			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancel policy page", "Cancellation details entered successfully");

			// navigating to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// searching the re-write quote number
			homePage.searchQuote(rewriteQuoteNumber);

			// checking re-write quote status on account overview page
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(2).getData(), "Deleted",
					"Account Overview page",
					"Re-Write Quote status is :" + accountOverviewPage.quoteStatus.formatDynamicPath(2).getData(),
					false, false);

			// IO-21846 Ended

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBMDTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBMDTC001 ", "Executed Successfully");
			}
		}

	}
}
