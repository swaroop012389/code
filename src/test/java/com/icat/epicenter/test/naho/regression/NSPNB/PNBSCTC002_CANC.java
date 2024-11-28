package com.icat.epicenter.test.naho.regression.NSPNB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBSCTC002_CANC extends AbstractNAHOTest {

	public PNBSCTC002_CANC() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/SC002.xls";
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
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
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
			requestBindPage.refreshPage();

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Values Entered Successfully");

			// Approve bind request
			if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
					&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchReferral(quoteNumber);
				referralPage.clickOnApprove();
				requestBindPage.approveRequestNAHO(testData);
			}

			// Validating the premium amount
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			Assertions.addInfo("Scenario 01", "Asserting NOC Mailing Dates for USM");
			// click on cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			cancelPolicyPage.daysBeforeNOC.setData(testData.get("Cancellation_DaysBeforeNOC"));
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			String nocMailingDate = cancelPolicyPage.nocMailDate.getData().substring(18, 28);
			String policyEffectiveDate = cancelPolicyPage.policyEffectiveDate.getData().substring(0, 10);
			Assertions.passTest("Cancel Policy Page", "Policy Effective Date is " + policyEffectiveDate);
			Assertions.passTest("Cancel Policy Page", "NOC Mailing Date is " + nocMailingDate);

			try {
				SimpleDateFormat noc = new SimpleDateFormat("MM-dd-yyyy");
				SimpleDateFormat polEffDate = new SimpleDateFormat("MM/dd/yyyy");
				Date policyEffectiveDateData = polEffDate.parse(policyEffectiveDate);
				Date nocMailingDateData = noc.parse(nocMailingDate);
				long diffInMillies = Math.abs(nocMailingDateData.getTime() - policyEffectiveDateData.getTime());
				long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				Assertions.verify(String.valueOf(diff), "120", "Cancel Policy Page",
						"Difference between policy effective date and NOC Mailing date is " + diff, false, false);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Asserting cancellation dates for each reason
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.addInfo("Scenario 02",
					"Asserting available Cancellation Reason and Cancellation Effective Dates");
			for (int i = 0; i <= 11; i++) {
				testData = data.get(i);
				cancelPolicyPage.cancelReasonArrow.scrollToElement();
				cancelPolicyPage.cancelReasonArrow.click();
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason"))
						.scrollToElement();
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					Date cancellationEffectiveDate = sdf.parse(cancelPolicyPage.cancellationEffectiveDate.getData());
					Date policyEffectiveDateData = sdf.parse(policyEffectiveDate);
					long diffInMillies = Math
							.abs(cancellationEffectiveDate.getTime() - policyEffectiveDateData.getTime());
					long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

					if (cancelPolicyPage.cancelReasonData.getData().contains("Cancel/Rewrite")
							|| cancelPolicyPage.cancelReasonData.getData().contains("Insured's Request")
							|| cancelPolicyPage.cancelReasonData.getData()
									.contains("Insured's Request - Duplicate coverage")
							|| cancelPolicyPage.cancelReasonData.getData()
									.contains("Dwelling sold or transfer of ownership")) {
						Assertions.verify(String.valueOf(diff), "5", "Cancel Policy Page",
								"Policy Effective Date is " + policyEffectiveDate + ". Cancellation Effective Date is "
										+ cancelPolicyPage.cancellationEffectiveDate.getData()
										+ " for Cancellation Reason " + cancelPolicyPage.cancelReasonData.getData()
										+ ". Difference between the dates is " + diff + " days",
								false, false);
					} else if (cancelPolicyPage.cancelReasonData.getData().contains("Ineligible risk")
							|| cancelPolicyPage.cancelReasonData.getData()
									.contains("Failure to comply with underwriting requirements")
							|| cancelPolicyPage.cancelReasonData.getData().contains("Material misrepresentation")
							|| cancelPolicyPage.cancelReasonData.getData().contains("Material change in risk")
							|| cancelPolicyPage.cancelReasonData.getData()
									.contains("Violation of law which materially increases risk")
							|| cancelPolicyPage.cancelReasonData.getData()
									.contains("Failure to provide requested information")
							|| cancelPolicyPage.cancelReasonData.getData().contains("Age, condition, maintenance")) {
						Assertions.verify(String.valueOf(diff), "36", "Cancel Policy Page",
								"Policy Effective Date is " + policyEffectiveDate + ". Cancellation Effective Date is "
										+ cancelPolicyPage.cancellationEffectiveDate.getData()
										+ " for Cancellation Reason " + cancelPolicyPage.cancelReasonData.getData()
										+ ". Difference between the dates is " + diff + " days",
								false, false);
					}

					else {
						Assertions.verify(String.valueOf(diff), "16", "Cancel Policy Page",
								"Policy Effective Date is " + policyEffectiveDate + ". Cancellation Effective Date is "
										+ cancelPolicyPage.cancellationEffectiveDate.getData()
										+ " for Cancellation Reason " + cancelPolicyPage.cancelReasonData.getData()
										+ ". Difference between the dates is " + diff + " days",
								false, false);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			testData = data.get(data_Value2);
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
			cancelPolicyPage.cancellationEffectiveDate.tab();
			cancelPolicyPage.nextButton.waitTillVisibilityOfElement(60);
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on next button");

			// Asserting Earned and Returned values for Flat cancel
			Assertions.addInfo("Scenario 03", "Asserting Earned and Returned Values For Flat/Non Flat Cancellations");
			Assertions.verify(cancelPolicyPage.newPremium.getData(), "$0", "Cancel Policy Page",
					"Earned Premium for Flat Cancel is " + cancelPolicyPage.newPremium.getData(), false, false);
			Assertions.verify(cancelPolicyPage.newInspectionFee.getData(), "0", "Cancel Policy Page",
					"Earned Inspection Fee for Flat Cancel is " + cancelPolicyPage.newPremium.getData(), false, false);
			Assertions.verify(cancelPolicyPage.newPolicyFee.getData(), "0", "Cancel Policy Page",
					"Earned Policy Fee for Flat Cancel is " + cancelPolicyPage.newPremium.getData(), false, false);
			Assertions.verify(cancelPolicyPage.newSLTF.getData(), "$0.00", "Cancel Policy Page",
					"Earned SLTF for Flat Cancel is " + cancelPolicyPage.newPremium.getData(), false, false);
			Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(2).getData(), "$0.00",
					"Cancel Policy Page", "Earned Total Premium for Flat Cancel is "
							+ cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(2).getData(),
					false, false);
			Assertions.verify(
					cancelPolicyPage.premium.formatDynamicPath(3).getData()
							.contains(cancelPolicyPage.premium.formatDynamicPath(1).getData()),
					true, "Cancel Policy Page", "Returned Premium for Flat Cancel is same as Original Premium", false,
					false);
			Assertions.verify(
					cancelPolicyPage.inspectionFee.formatDynamicPath(4).getData().replace("-", "")
							.contains(cancelPolicyPage.inspectionFee.formatDynamicPath(2).getData()),
					true, "Cancel Policy Page",
					"Returned Inspection Fee for Flat Cancel is same as Original Inspection Fee "
							+ "Original inspection fee " + cancelPolicyPage.inspectionFee.formatDynamicPath(2).getData()
							+ " Returned inspection fee "
							+ cancelPolicyPage.inspectionFee.formatDynamicPath(4).getData(),
					false, false);
			Assertions.verify(
					cancelPolicyPage.policyFee.formatDynamicPath(4).getData().replace("-", "")
							.contains(cancelPolicyPage.policyFee.formatDynamicPath(2).getData()),
					true, "Cancel Policy Page",
					"Retuned Policy Fee for Flat Cancel is same as Original Policy Fee "
							+ cancelPolicyPage.policyFee.formatDynamicPath(2).getData() + " Returned policy fee "
							+ cancelPolicyPage.policyFee.formatDynamicPath(4).getData(),
					false, false);
			Assertions.verify(cancelPolicyPage.SLTF
					.formatDynamicPath(3).getData().contains(cancelPolicyPage.SLTF.formatDynamicPath(1).getData()),
					true, "Cancel Policy Page",
					"Returned SLTF for Flat Cancel is same as Original SLTF "
							+ cancelPolicyPage.SLTF.formatDynamicPath(1).getData() + " Returned SLTF "
							+ cancelPolicyPage.SLTF.formatDynamicPath(3).getData(),
					false, false);
			Assertions.verify(
					cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(3).getData()
							.contains(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(1).getData()),
					true, "Cancel Policy Page",
					"Returned Total Premium and Taxes for Flat Cancel is same as Original Total Premium and Taxes "
							+ cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(1).getData()
							+ "Returned Total Premium and Taxes "
							+ cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(3).getData(),
					false, false);

			cancelPolicyPage.cancelButton.waitTillVisibilityOfElement(60);
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on cancel button ");

			// click on cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();

			testData = data.get(data_Value3);
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
			cancelPolicyPage.cancellationEffectiveDate.tab();
			cancelPolicyPage.nextButton.waitTillVisibilityOfElement(60);
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on next button ");

			// Getting original premium,earned premium and returned premium
			cancelPolicyPage.waitTime(3);
			String originalPremium = cancelPolicyPage.premium.formatDynamicPath(1).getData().replace("$", "")
					.replace(",", "");
			String earnedPremium = cancelPolicyPage.newPremium.getData().replace("$", "").replace(",", "");
			String returnedPremium = cancelPolicyPage.returnedPremium.getData().replace("$", "").replace(",", "")
					.replace("-", "");

			double d_originalPremium = Double.parseDouble(originalPremium);
			double d_earnedPremium = Double.parseDouble(earnedPremium);
			double d_returnedPremium = Double.parseDouble(returnedPremium);

			// getting original inspection fee and earned inspection fee
			String originalInspectionFee = cancelPolicyPage.inspectionFee.formatDynamicPath(2).getData()
					.replace("$", "").replace(",", "");
			String earnedInspectionFee = cancelPolicyPage.inspectionFeeEarned.getData().replace("$", "").replace(",",
					"");
			double d_originalInspectionFee = Double.parseDouble(originalInspectionFee);
			double d_earnedInspectionFee = Double.parseDouble(earnedInspectionFee);

			// getting original.earned policy fee
			String originalPolicyFee = cancelPolicyPage.policyFee.formatDynamicPath(2).getData().replace("$", "")
					.replace(",", "");
			String earnedPolicyFee = cancelPolicyPage.policyFeeEarned.getData().replace("$", "").replace(",", "");
			double d_originalPolicyFee = Double.parseDouble(originalPolicyFee);
			double d_earnedPolicyFee = Double.parseDouble(earnedPolicyFee);

			// Original,earned and returned SLTF
			String originalSLTF = cancelPolicyPage.SLTF.formatDynamicPath(1).getData().replace("$", "").replace(",",
					"");
			String earnedSLTF = cancelPolicyPage.SLTF.formatDynamicPath(2).getData().replace("$", "").replace(",", "");
			String returnedSLTF = cancelPolicyPage.SLTF.formatDynamicPath(3).getData().replace("$", "").replace(",", "")
					.replace("-", "");
			double d_originalELTF = Double.parseDouble(originalSLTF);
			double d_earnedSLTF = Double.parseDouble(earnedSLTF);
			double d_returnedSLTF = Double.parseDouble(returnedSLTF);

			// getting surplus contribution
			String originalVIESurplusContribution = cancelPolicyPage.surplusContributionVlaue.formatDynamicPath(2)
					.getData().replace("$", "");
			String returnedVIESurplusContribution = cancelPolicyPage.surplusContributionVlaue.formatDynamicPath(4)
					.getData().replace("$", "").replace("-", "");
			double d_originalVIESurplusContribution = Double.parseDouble(originalVIESurplusContribution);
			double d_returnedVIESurplusContribution = Double.parseDouble(returnedVIESurplusContribution);

			// Getting total premium for original,earned and returned
			String originalTotalPremium = cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(1).getData()
					.replace("$", "");
			String earnedTotalPremium = cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(2).getData()
					.replace("$", "");
			String returnedTotalPremium = cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(3).getData()
					.replace("$", "").replace("-", "");
			double d_originalTotalPremium = Double.parseDouble(originalTotalPremium);
			double d_earnedTotalPremium = Double.parseDouble(earnedTotalPremium);
			double d_returnedTotalPremium = Double.parseDouble(returnedTotalPremium);

			// Calculating original sltf
			testData = data.get(data_Value1);
			double sltfPercentage = Double.parseDouble(testData.get("SLTFPercentage"));
			double calOriginalSLTF = (d_originalPremium + d_originalPolicyFee + d_originalInspectionFee
					+ d_originalVIESurplusContribution) * sltfPercentage;

			// Calculating returned sltf
			double calReturnedSLTF = (d_returnedPremium + d_returnedVIESurplusContribution) * sltfPercentage;

			// calculating earned sltf
			double calEarnedSLTF = calOriginalSLTF - calReturnedSLTF;

			// calculating earned premium
			double calEarnedPremium = d_originalPremium - d_returnedPremium;

			// calculating total original premium
			double calTotalOriginaPrmium = d_originalPremium + d_originalPolicyFee + d_originalInspectionFee
					+ d_originalVIESurplusContribution + d_originalELTF;

			// calculating returned total prmium
			double calReturnedTotalPremium = d_returnedPremium + d_returnedVIESurplusContribution + d_returnedSLTF;

			// calculating earned total premium
			double calearnedTotalPremium = calTotalOriginaPrmium - calReturnedTotalPremium;

			// calculating returned premium
			double calReturnedPremium = d_originalPremium - d_earnedPremium;

			// Asserting original values for Non flat cancel
			Assertions.passTest("Cancel Policy Page", "Original Premium value " + "$" + d_originalPremium);
			Assertions.passTest("Cancel Policy Page", "Original Inspection Fee " + "$" + d_originalInspectionFee);
			Assertions.passTest("Cancel Policy Page", "Original Policy Fee " + "$" + d_originalPolicyFee);
			Assertions.passTest("Cancel Policy Page", "Original Total Premium value " + "$" + d_originalTotalPremium);

			if (Precision.round(Math.abs(Precision.round(d_originalELTF, 2) - Precision.round(calOriginalSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page", "Calculated original SLTF value for non flat cancel is : "
						+ "$" + Precision.round(calOriginalSLTF, 2));
				Assertions.passTest("Cancel Policy Page",
						"Actaul original SLTF value for non flat cancel is " + "$" + d_originalELTF);

			} else {
				Assertions.verify(d_originalELTF, calOriginalSLTF, "Cancel Policy Page",
						"The Difference between actual and calculated original SLTF value for non flat cancel is more than 0.05",
						false, false);

			}

			// Asserting Earned and Returned values for Non Flat cancel
			if (Precision.round(Math.abs(Precision.round(d_earnedPremium, 2) - Precision.round(calEarnedPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page", "Calculated earned premium for non flat cancel is : " + "$"
						+ Precision.round(calEarnedPremium, 2));
				Assertions.passTest("Cancel Policy Page",
						"Actaul earned premium  for non flat cancel is " + "$" + d_earnedPremium);

			} else {
				Assertions.verify(d_earnedPremium, calEarnedPremium, "Cancel Policy Page",
						"The Difference between actual and calculated earned premium for non flat cancel is more than 0.05",
						false, false);

			}

			Assertions.verify(d_earnedInspectionFee, d_originalInspectionFee, "Cancel Policy Page",
					"Earned Inspection Fee for Non Flat Cancel is " + d_earnedInspectionFee, false, false);

			Assertions.verify(d_earnedPolicyFee, d_originalPolicyFee, "Cancel Policy Page",
					"Earned Policy Fee for Non Flat Cancel is " + d_earnedPolicyFee, false, false);

			if (Precision.round(Math.abs(Precision.round(d_earnedSLTF, 2) - Precision.round(calEarnedSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page", "Calculated earned SLTF Value for non flat cancel is : " + "$"
						+ Precision.round(calEarnedSLTF, 2));
				Assertions.passTest("Cancel Policy Page",
						"Actaul earned SLTF value for non flat cancel is " + earnedSLTF);

			} else {
				Assertions.verify(d_earnedSLTF, calEarnedSLTF, "Cancel Policy Page",
						"The Difference between actual and calculated SLTF for non flat cancel is more than 0.05",
						false, false);

			}

			if (Precision.round(
					Math.abs(Precision.round(d_earnedTotalPremium, 2) - Precision.round(calearnedTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page", "Calculated total earned premium for non flat cancel is : "
						+ "$" + Precision.round(calearnedTotalPremium, 2));
				Assertions.passTest("Cancel Policy Page",
						"Actaul total earned premium  for non flat cancel is " + "$" + d_earnedTotalPremium);

			} else {
				Assertions.verify(d_earnedTotalPremium, calearnedTotalPremium, "Cancel Policy Page",
						"The Difference between actual and calculated earned total premium for non flat cancel is more than 0.05",
						false, false);

			}

			if (Precision.round(
					Math.abs(Precision.round(d_returnedPremium, 2) - Precision.round(calReturnedPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page", "Calculated returned premium for non flat cancel is : " + "$"
						+ Precision.round(calReturnedPremium, 2));
				Assertions.passTest("Cancel Policy Page",
						"Actual returned premium  for non flat cancel is " + "$" + d_returnedPremium);

			} else {
				Assertions.verify(d_returnedPremium, calReturnedPremium, "Cancel Policy Page",
						"The Difference between actual and calculated returned premium for non flat cancel is more than 0.05",
						false, false);

			}

			Assertions.verify(cancelPolicyPage.inspectionFee.formatDynamicPath(4).getData().equals("$0"), true,
					"Cancel Policy Page", " Returned Inspection for Non Flat Cancel is "
							+ cancelPolicyPage.inspectionFee.formatDynamicPath(4).getData(),
					false, false);
			Assertions.verify(cancelPolicyPage.policyFee.formatDynamicPath(4).getData().equals("$0"), true,
					"Cancel Policy Page", " Returned Policy Fee for Non Flat Cancel is "
							+ cancelPolicyPage.policyFee.formatDynamicPath(4).getData(),
					false, false);

			if (Precision.round(Math.abs(Precision.round(d_returnedSLTF, 2) - Precision.round(calReturnedSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page", "Calculated returned SLTF for non flat cancel is : " + "$"
						+ Precision.round(calReturnedSLTF, 2));
				Assertions.passTest("Cancel Policy Page",
						"Actual returned SLTF  for non flat cancel is " + "$" + d_returnedSLTF);

			} else {
				Assertions.verify(d_returnedSLTF, calReturnedSLTF, "Cancel Policy Page",
						"The Difference between actual and calculated returned SLTF for non flat cancel is more than 0.05",
						false, false);

			}

			if (Precision.round(
					Math.abs(Precision.round(d_returnedTotalPremium, 2) - Precision.round(calReturnedTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page", "Calculated total returned premium for non flat cancel is : "
						+ "$" + Precision.round(calReturnedTotalPremium, 2));
				Assertions.passTest("Cancel Policy Page",
						"Actaul total returned premium  for non flat cancel is " + "$" + d_returnedTotalPremium);

			} else {
				Assertions.verify(d_returnedTotalPremium, calReturnedTotalPremium, "Cancel Policy Page",
						"The Difference between actual and calculated returned total premium for non flat cancel is more than 0.05",
						false, false);

			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBSCTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBSCTC002 ", "Executed Successfully");
			}
		}
	}
}
