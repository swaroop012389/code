/** Program Description: Verifying the following
 * 1) Value of "Healthy Homes Fund Surcharge" is equal to $12.00 during NB and $0.00 during Endorsement.
 * 2) The Surplus Contribution Values on the Endorsement
 *
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 29/12/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBCTTC001 extends AbstractNAHOTest {

	public PNBCTTC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/CTTC001.xls";
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
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
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

			// Getting the Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Fetching VIE Values
			accountOverviewPage.quoteSpecifics.click();
			String vieParticipationValue = accountOverviewPage.vieParticipationValue.getData().replace("%", "");
			double d_vieParticipationValue = Double.parseDouble(vieParticipationValue) / 100;
			String vieContributionValue = accountOverviewPage.vieContributionChargeValue.getData().replace("%", "");
			double d_vieContributionValue = Double.parseDouble(vieContributionValue) / 100;

			// Click on View print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page loaded successfully", false, false);

			// verify the Value of Healthy Homes Fund Surcharge is equal to $12.00
			Assertions.addInfo("Scenario 01",
					" Verifying the Value of Healthy Homes Fund Surcharge is equal to $12.00");
			Assertions.verify(viewOrPrintFullQuotePage.healthyHomesFund.getData(), "$12.00",
					"View/Print Full Quote Page",
					"The Value of Healthy Homes Fund Surcharge is "
							+ viewOrPrintFullQuotePage.healthyHomesFund.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

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

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Opening Referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);

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
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind page loaded successfully", false, false);
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is " + policyNumber, false, false);

			// Click on PB endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Entering Endorsement effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// click on change coverage options link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);

			// Changing AOP deductible and named storm value
			Assertions.passTest("Create Quote Page",
					"AOP Deductible Original Value : " + createQuotePage.aopDeductibleData.getData());
			if (!testData.get("AOPDeductibleValue").equals("")) {
				if (!createQuotePage.aopDeductibleArrow.getAttrributeValue("class").contains("disabled")) {
					createQuotePage.aopDeductibleArrow.waitTillPresenceOfElement(60);
					createQuotePage.aopDeductibleArrow.waitTillVisibilityOfElement(60);
					createQuotePage.aopDeductibleArrow.scrollToElement();
					createQuotePage.aopDeductibleArrow.click();
					createQuotePage.aopDeductibleOption.formatDynamicPath(testData.get("AOPDeductibleValue"))
							.scrollToElement();
					createQuotePage.aopDeductibleOption.formatDynamicPath(testData.get("AOPDeductibleValue")).click();
					Assertions.passTest("Create Quote Page",
							"AOP Deductible Latest Value : " + createQuotePage.aopDeductibleData.getData());
				}
			}
			Assertions.passTest("Create Quote Page",
					"Named Strom Original Value : " + createQuotePage.namedStormData.getData());
			if (!testData.get("NamedStormValue").equals("")) {
				if (!createQuotePage.namedStormArrow_NAHO.getAttrributeValue("class").contains("disabled")) {
					createQuotePage.namedStormArrow_NAHO.scrollToElement();
					createQuotePage.namedStormArrow_NAHO.click();
					createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("NamedStormValue"))
							.waitTillVisibilityOfElement(60);
					createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("NamedStormValue"))
							.scrollToElement();
					createQuotePage.namedStormDeductibleOption.formatDynamicPath(testData.get("NamedStormValue"))
							.click();
					Assertions.passTest("Create Quote Page",
							"Named Strom Latest Value : " + createQuotePage.namedStormData.getData());
				}
			}

			// click on continue endorsement button
			createQuotePage.continueEndorsementButton.waitTillVisibilityOfElement(60);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}

			// Click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next Button");

			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();
			}

			// Fetching value for the Calculation of Surplus Contribution Values on
			// Transaction, Term and Annual Columns
			String transactionPremValue = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "2").getData()
					.replace("$", "").replace("-", "");
			double d_transactionPremValue = Double.parseDouble(transactionPremValue);
			String transactionInspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "2").getData()
					.replace("$", "");
			double d_transactionInspectionFee = Double.parseDouble(transactionInspectionFee);
			String transactionPolicyFee = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "2").getData()
					.replace("$", "");
			double d_transactionPolicyFee = Double.parseDouble(transactionPolicyFee);
			String transactionSLTF = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "2").getData()
					.replace("$", "").replace("-", "");
			double d_transactionSLTF = Double.parseDouble(transactionSLTF);
			String transactionSCValue = endorsePolicyPage.premiumDetails.formatDynamicPath("5", "2").getData()
					.replace("$", "").replace("-", "");
			double d_transactionSCValue = Double.parseDouble(transactionSCValue);
			String transactionTotalPremValue = endorsePolicyPage.premiumDetails.formatDynamicPath("6", "2").getData()
					.replace("$", "").replace("-", "");
			double d_transactionTotalPremValue = Double.parseDouble(transactionTotalPremValue);
			String annualPremValue = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "3").getData()
					.replace("$", "").replace(",", "");
			double d_annualPremValue = Double.parseDouble(annualPremValue);
			String annualInspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "3").getData()
					.replace("$", "");
			double d_annualInspectionFee = Double.parseDouble(annualInspectionFee);
			String annualPolicyFee = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "3").getData().replace("$",
					"");
			double d_annualPolicyFee = Double.parseDouble(annualPolicyFee);
			String annualFeeSLTF = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "3").getData().replace("$",
					"");
			double d_annualSLTF = Double.parseDouble(annualFeeSLTF);
			String annualSCValue = endorsePolicyPage.premiumDetails.formatDynamicPath("5", "3").getData().replace("$",
					"");
			double d_annualSCValue = Double.parseDouble(annualSCValue);
			String annualTotalPremValue = endorsePolicyPage.premiumDetails.formatDynamicPath("6", "3").getData()
					.replace("$", "").replace("-", "").replace(",", "");
			double d_annualTotalPremValue = Double.parseDouble(annualTotalPremValue);
			String termPremValue = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "4").getData()
					.replace("$", "").replace(",", "");
			double d_termPremValue = Double.parseDouble(termPremValue);
			String termInspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "4").getData()
					.replace("$", "");
			double d_termInspectionFee = Double.parseDouble(termInspectionFee);
			String termPolicyFee = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "4").getData().replace("$",
					"");
			double d_termPolicyFee = Double.parseDouble(termPolicyFee);
			String termFeeSLTF = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "4").getData().replace("$",
					"");
			double d_termSLTF = Double.parseDouble(termFeeSLTF);
			String termSCValue = endorsePolicyPage.premiumDetails.formatDynamicPath("5", "4").getData().replace("$",
					"");
			double d_termSCValue = Double.parseDouble(termSCValue);
			String termTotalPremValue = endorsePolicyPage.premiumDetails.formatDynamicPath("6", "3").getData()
					.replace("$", "").replace("-", "").replace(",", "");
			double d_termTotalPremValue = Double.parseDouble(termTotalPremValue);

			// Calculating transaction, term and annual SC values
			double calcTransactionSCValue = d_vieParticipationValue * d_vieContributionValue * (d_transactionPremValue);
			double calcAnnualSCValue = d_vieParticipationValue * d_vieContributionValue * (d_annualPremValue);
			double calcTermSCValue = d_vieParticipationValue * d_vieContributionValue * (d_termPremValue);

			// Calculating total Premium Values on Endorse Policy Page
			double calcTransactionTotalPremium = d_transactionPremValue + d_transactionInspectionFee
					+ d_transactionPolicyFee + d_transactionSLTF + d_transactionSCValue;
			double calcAnnualTotalPremium = d_annualPremValue + d_annualInspectionFee + d_annualPolicyFee + d_annualSLTF
					+ d_annualSCValue;
			double calcTermTotalPremium = d_termPremValue + d_termInspectionFee + d_termPolicyFee + d_termSLTF
					+ d_termSCValue;

			// Verifying the Surplus Contribution Value displayed is mathcing with the
			// calculated Surplus Contribution Value
			Assertions.addInfo("Scinario 02",
					"Verifying if the Actual Surplus Contribution Value is matching with Calculated "
							+ "Surplus Contribution Values on the Endorse Policy Page");
			if (Precision.round(
					Math.abs(Precision.round(d_transactionSCValue, 2) - Precision.round(calcTransactionSCValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Calculated Transaction SC Value: " + "$" + Precision.round(calcTransactionSCValue, 2));
				Assertions.passTest("Endorse Policy Page",
						"Actual Transaction SC Value: " + "$" + Precision.round(d_transactionSCValue, 2));
			} else {
				Assertions.verify(Precision.round(d_transactionSCValue, 2), Precision.round(calcTransactionSCValue, 2),
						"Endorse Policy Page",
						"The Actaul and Calculated Transaction Surplus Contribution Values are not the Same", false,
						false);
			}
			if (Precision.round(Math.abs(Precision.round(d_annualSCValue, 2) - Precision.round(calcAnnualSCValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Calculated Annual SC Value: " + "$" + Precision.round(calcAnnualSCValue, 2));
				Assertions.passTest("Endorse Policy Page",
						"Actual Annual SC Value: " + "$" + Precision.round(d_annualSCValue, 2));
			} else {
				Assertions.verify(Precision.round(d_annualSCValue, 2), Precision.round(calcAnnualSCValue, 2),
						"Endorse Policy Page",
						"The Actaul and Calculated Annual Surplus Contribution Values are not the Same", false, false);
			}
			if (Precision.round(Math.abs(Precision.round(d_termSCValue, 2) - Precision.round(calcTermSCValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Calculated Term SC Value: " + "$" + Precision.round(calcTermSCValue, 2));
				Assertions.passTest("Endorse Policy Page",
						"Actual Term SC Value: " + "$" + Precision.round(d_termSCValue, 2));
			} else {
				Assertions.verify(Precision.round(d_termSCValue, 2), Precision.round(calcTermSCValue, 2),
						"Endorse Policy Page",
						"The Actaul and Calculated Term Surplus Contribution Values are not the Same", false, false);
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Verifying the Total Premium Value displayed is matching with the
			// calculated Total Premium Value
			Assertions.addInfo("Scinario 03", "Verifying if the Actual Total Premium Value is matching with Calculated "
					+ "Total Premium Values on the Endorse Policy Page");
			if (Precision.round(Math.abs(
					Precision.round(d_transactionTotalPremValue, 2) - Precision.round(calcTransactionTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page", "Calculated Transaction Total Premium Value: " + "$"
						+ Precision.round(calcTransactionTotalPremium, 2));
				Assertions.passTest("Endorse Policy Page", "Actual Transaction Total Premium Value: " + "$"
						+ Precision.round(d_transactionTotalPremValue, 2));
			} else {
				Assertions.verify(Precision.round(d_termSCValue, 2), Precision.round(calcTransactionTotalPremium, 2),
						"Endorse Policy Page", "The Actual Transaction Total Premium Value is not matching "
								+ "with the Calculated Transaction Total Premium Value",
						false, false);
			}
			if (Precision.round(
					Math.abs(Precision.round(d_annualTotalPremValue, 2) - Precision.round(calcAnnualTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Calculated Annual Total Premium Value: " + "$" + Precision.round(calcAnnualTotalPremium, 2));
				Assertions.passTest("Endorse Policy Page",
						"Actual Annual Total Premium Value: " + "$" + Precision.round(d_annualTotalPremValue, 2));
			} else {
				Assertions.verify(Precision.round(d_annualTotalPremValue, 2),
						Precision.round(calcAnnualTotalPremium, 2), "Endorse Policy Page",
						"The Actual Annual Total Premium Value is not matching "
								+ "with the Calculated Transaction Total Premium Value",
						false, false);
			}
			if (Precision.round(
					Math.abs(Precision.round(d_termTotalPremValue, 2) - Precision.round(calcTermTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"The Actual Term Total is: " + Precision.round(calcTermTotalPremium, 2));
				Assertions.passTest("Endorse Policy Page",
						"The Actual Term Total is: " + Precision.round(d_termTotalPremValue, 2));
			} else {
				Assertions.verify(Precision.round(d_termTotalPremValue, 2), Precision.round(calcTermTotalPremium, 2),
						"Endorse Policy Page", "The Actual Term Total Premium Value is not matching "
								+ "with the Calculated Transaction Total Premium Value",
						false, false);
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on Complete Button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Close button");

			// Clicking on second transaction
			policySummaryPage.transHistReasonNAHO.formatDynamicPath("3").scrollToElement();
			policySummaryPage.transHistReasonNAHO.formatDynamicPath("3").click();

			// Click on View Policy snapshot
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy snapshot HyperLink");

			// verifying the Value of Healthy Homes Fund Surcharge is equal to $0.00 After
			// Initiating the Endorsement
			Assertions.addInfo("Scenario 04",
					"Verifying the Value of Healthy Homes Fund Surcharge is equal to $0.00 After Initiating the Endorsement");
			Assertions.verify(viewPolicySnapShot.sltfValue.formatDynamicPath("Healthy").getData(), "$0.00",
					"View/Print Full Quote Page",
					"The Value of Healthy Homes Fund Surcharge is "
							+ viewPolicySnapShot.sltfValue.formatDynamicPath("Healthy").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on GoBack Button
			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();

			// Click on PB endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Entering Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// click on edit location or building details
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on edit Location or building details link");

			// Changing the Roof cladding to other
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page Loaded successfully", false, false);
			testData = data.get(data_Value1);
			Assertions.addInfo("Dwelling Page",
					"Roof Cladding Original Value : " + testData.get("L1D1-DwellingRoofCladding"));
			testData = data.get(data_Value2);
			dwellingPage.addRoofDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page",
					"Roof Cladding is Latest Value : " + testData.get("L1D1-DwellingRoofCladding"));

			// click on continue
			dwellingPage.continueButton.scrollToElement();
			dwellingPage.continueButton.click();

			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}

			// click on continue endorsement button
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded successfully", false, false);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}

			// assert the warning message when roof cladding is other
			Assertions.addInfo("Scenario 05",
					"Verifying the Warning message when Roof Cladding is Other for Endorsement");
			Assertions.verify(createQuotePage.warningMessages.formatDynamicPath("roof age").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Warning message " + createQuotePage.warningMessages.formatDynamicPath("roof age").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Adding the CR IO-20275
			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy " + policyNumber + " successfully");

			// click on rewrite policy
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Rewrite Policy link");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click on create another quote
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create another quote button");

			// click on override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// Modify create quote details
			testData = data.get(data_Value3);
			createQuotePage.enterQuoteDetailsNAHO(testData);

			// Fetching values for Surplus Contribution Calculation
			String quotePremiumValue = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			double d_quotePremiumValue = Double.parseDouble(quotePremiumValue);
			String quoteTotalPremValue = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",",
					"");
			double d_quoteTotalPremValue = Double.parseDouble(quoteTotalPremValue);

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
			String eqbPremium = viewOrPrintRateTrace.eqbPremiumValue.getData();
			double d_eqbPremium = Double.parseDouble(eqbPremium);
			String ulPremium = viewOrPrintRateTrace.serviceLinePremiumValue.getData();
			double d_ulPremium = Double.parseDouble(ulPremium);

			// Calculating the Surplus contribution value
			double calcSurplusContributionValue = d_vieParticipationPercent * d_vieContributionCharge
					* (d_quotePremiumValue - d_eqbPremium - d_ulPremium);

			// navigating back to account overview page to fetch atual quote SC value
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();

			// Fetching Surplus Contribution Value form Account Overview Page
			String actualQuoteSCValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "");
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
			String vpfqSLTF = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace("$", "");
			double d_vpfqSLTF = Double.parseDouble(vpfqSLTF);
			String vpfqSCValue = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
					.replace("%", "");
			double d_vpfqSCValue = Double.parseDouble(vpfqSCValue);

			// Calculating total Premium value
			double calcTotalPremiumValue = d_quotePremiumValue + d_vpfqPolicyFee + d_vpfqInspectionfee
					+ d_vpfqHealthyHomeFundCharge + d_vpfqSLTF + d_vpfqSCValue;

			// Verifying if the Calculated values are matching with the actual values
			Assertions.addInfo("Scenario 06",
					"Verifying if the Calculated Total Premium and Surplus Contribution Values are matching with "
							+ "the Actual Totla Premium and Surplus Contribution Values");
			if (Precision.round(
					Math.abs(Precision.round(calcTotalPremiumValue, 2) - Precision.round(d_quoteTotalPremValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Quote Premium : " + "$" + Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Quote Premium  : " + "$" + Precision.round(d_quoteTotalPremValue, 2));
			} else {
				Assertions.verify(Precision.round(d_quoteTotalPremValue, 2), Precision.round(calcTotalPremiumValue, 2),
						"Account Overview Page",
						"The Actual Quote Total Premium Value is not same as the Calculated Total Premium Value", false,
						false);
			}

			if (Precision.round(
					Math.abs(Precision.round(calcTotalPremiumValue, 2) - Precision.round(d_vpfqTotalPremiumVlaue, 2)),
					2) < 0.05) {
				Assertions.passTest("View Or Print Full Quote Page",
						"Calculated Quote Premium : " + "$" + Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("View Or Print Full Quote Page",
						"Actual Quote Premium  : " + "$" + Precision.round(d_vpfqTotalPremiumVlaue, 2));
			} else {
				Assertions.verify(Precision.round(d_vpfqTotalPremiumVlaue, 2),
						Precision.round(calcTotalPremiumValue, 2), "View Or Print Full Quote Page",
						"The Actual Total Premium Value is not same as the Calculated Total Premium Value", false,
						false);
			}
			if (Precision.round(Math
					.abs(Precision.round(d_actualQuoteSCValue, 2) - Precision.round(calcSurplusContributionValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Pagee", "Calculated Quote Surplus Contribution : " + "$"
						+ Precision.round(calcSurplusContributionValue, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Quote Surplus Contribution  : " + "$" + Precision.round(d_actualQuoteSCValue, 2));
			} else {
				Assertions.verify(Precision.round(d_actualQuoteSCValue, 2),
						Precision.round(calcSurplusContributionValue, 2), "Account Overview Page",
						"The Actual Surplus Contribution Value is not same as the Calculated Surplus Contribution Value",
						false, false);
			}
			if (Precision.round(
					Math.abs(Precision.round(d_vpfqSCValue, 2) - Precision.round(calcSurplusContributionValue, 2)),
					2) < 0.05) {
				Assertions.passTest("View Or Print Full Quote Page", "Calculated Quote Surplus Contribution : " + "$"
						+ Precision.round(calcSurplusContributionValue, 2));
				Assertions.passTest("View Or Print Full Quote Page",
						"Actual Quote Surplus Contribution  : " + "$" + Precision.round(d_vpfqSCValue, 2));
			} else {
				Assertions.verify(Precision.round(d_vpfqSCValue, 2), Precision.round(calcSurplusContributionValue, 2),
						"View Or Print Full Quote Page",
						"The Actual Surplus Contribution Value is not same as the Calculated Surplus Contribution Value",
						false, false);
			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Navigating back to Account Overview PAge
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// click on rewrite bind
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on Rewrite bind");

			// Enter Previous policy cancellation date
			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.previousPolicyCancellationDate.waitTillElementisEnabled(60);
			requestBindPage.previousPolicyCancellationDate.scrollToElement();
			requestBindPage.previousPolicyCancellationDate.clearData();
			requestBindPage.previousPolicyCancellationDate.setData(testData.get("CancellationEffectiveDate"));
			requestBindPage.previousPolicyCancellationDate.tab();
			requestBindPage.previousPolicyCancellationDate.waitTillTextToBePresent(60);
			requestBindPage.waitTime(3);// wait time is meed to load the element

			// Entering renewal bind details
			// Fetching Premium Values for the Assertion
			String rpPremiumValue = requestBindPage.quotePremium.getData().replace("$", "").replace(",", "");
			double d_rpPremiumValue = Double.parseDouble(rpPremiumValue);

			// Verifying if the Calculated Total Premium Value is same as the Premium Value
			// on the Request Bind Page
			Assertions.addInfo("Scenario 07",
					"Verifying if the Calculated Total Premium Value is same as the Premium Value on the Request Bind Page");
			if (d_rpPremiumValue == Precision.round(calcTotalPremiumValue, 2)) {
				Assertions.verify(d_rpPremiumValue, Precision.round(calcTotalPremiumValue, 2), "Request Bind Page",
						"The Actual Premium Value on the Request Bind Page is same as the Calculated Premium Value",
						false, false);
			} else {
				Assertions.verify(d_rpPremiumValue, Precision.round(calcTotalPremiumValue, 2), "Request Bind Page",
						"The Actual Premium Value on the Request Bind Page is not same as the Calculated Premium Value",
						false, false);
			}
			Assertions.addInfo("Scenario 07", "Scenario 06 Ended");

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
			if (Precision.round(Math.abs(Precision.round(d_fullPayPrem, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Request Bind Page",
						"Calculated Total Premium Value: " + "$" + Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("Request Bind Page",
						"Actual Full Pay Premium Value: " + "$" + Precision.round(d_fullPayPrem, 2));
			} else {
				Assertions.verify(Precision.round(d_fullPayPrem, 2), Precision.round(calcTotalPremiumValue, 2),
						"Request Bind Page",
						"The Actaul Full Pay Premium and Calculated Total Premium Values are not the Same", false,
						false);
			}
			if (Precision.round(
					Math.abs(Precision.round(d_mortgageePayPrem, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Request Bind Page",
						"Calculated Total Premium Value: " + "$" + Precision.round(calcTotalPremiumValue, 2));
				Assertions.passTest("Request Bind Page",
						"Actual Mortgagee Pay Premium Value: " + "$" + Precision.round(d_mortgageePayPrem, 2));
			} else {
				Assertions.verify(Precision.round(d_mortgageePayPrem, 2), Precision.round(calcTotalPremiumValue, 2),
						"Request Bind Page",
						"The Actaul Mortgagee Pay Premium and Calculated Total Premium Values are not the Same", false,
						false);
			}
			Assertions.addInfo("Scenario 08", "Scenario 07 Ended");

			// Enter rewrite bind details
			requestBindPage.enteringRewriteDataNAHO(testData);

			// Assert the rewritten policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. The Rewritten PolicyNumber is " + policyNumber, false,
					false);

			// Fetching Values to Verify Surplus Contribution
			String termSurplusContributionValue = policySummaryPage.termSurplusContribution.getData().replace("$", "")
					.replace("%", "");
			double d_termSurplusContributionValue = Double.parseDouble(termSurplusContributionValue);
			String annualSurplusContributionValue = policySummaryPage.annualSurplusContribution.getData()
					.replace("$", "").replace("%", "");
			double d_annualSurplusContributionValue = Double.parseDouble(annualSurplusContributionValue);
			String transactionSurplusContributionValue = policySummaryPage.transactionSurplusContribution.getData()
					.replace("$", "").replace("%", "");
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
			Assertions.addInfo("Scenario 10",
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

			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			Assertions.addInfo("Scenario 11", "Validating the Total Premium Values present on Policy Summary Page"
					+ " are matching the Calculated Total Premium Value");
			if (d_termtotalPremiumValue == Precision.round(calcTotalPremiumValue, 2)) {
				Assertions.verify(d_termtotalPremiumValue, Precision.round(calcTotalPremiumValue, 2),
						"Policy Summary Page", "The Term Total Premium is same as the Calculated Total Premium", false,
						false);
			} else {
				Assertions.verify(d_termtotalPremiumValue, Precision.round(calcTotalPremiumValue, 2),
						"Policy Summary Page", "The Term Total Premium is not same as the Calculated Total Premium",
						false, false);
			}

			if (d_annualtotalPremiumValue == Precision.round(calcTotalPremiumValue, 2)) {
				Assertions.verify(d_annualtotalPremiumValue, Precision.round(calcTotalPremiumValue, 2),
						"Policy Summary Page", "The Annual Total Premium is same as the Calculated Total Premium",
						false, false);
			} else {
				Assertions.verify(d_annualtotalPremiumValue, Precision.round(calcTotalPremiumValue, 2),
						"Policy Summary Page", "The Annual Total Premium is not same as the Calculated Total Premium",
						false, false);
			}
			if (d_transactionPremiumValue == Precision.round(calcTotalPremiumValue, 2)) {
				Assertions.verify(d_transactionPremiumValue, Precision.round(calcTotalPremiumValue, 2),
						"Policy Summary Page", "The Transaction Total Premium is same as the Calculated Total Premium",
						false, false);
			} else {
				Assertions.verify(d_transactionPremiumValue, Precision.round(calcTotalPremiumValue, 2),
						"Policy Summary Page",
						"The Transaction Total Premium is not same as the Calculated Total Premium", false, false);
			}
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBCTTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBCTTC001 ", "Executed Successfully");
			}
		}
	}
}
