/** Program Description: 1. Create a Policy,Initiate an Endorsement to change Sinkhole,CGCC and verify they are added or not,then cancel the policy
 * 						 2. Check if the Transaction Premium is negative also check Annual Total, Term Total and SLTF values are updated correctly based on the updated Policy expiration date. Added IO-20992 and IO-21063
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 04/05/2021
 **/

package com.icat.epicenter.test.naho.regression.NSPNB;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBFLTC001_ENDT extends AbstractNAHOTest {

	public PNBFLTC001_ENDT() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/FL001.xls";
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
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();

		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_value4 = 3;
		Map<String, String> testData = data.get(data_Value1);
		DecimalFormat df = new DecimalFormat("0.00");
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

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on View print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");

			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page ", "View/Print Full Quote Page loaded successfully", false, false);

			Assertions.addInfo("Scenario 01",
					"Verifying Sinkhole and CGCC are Not Selected in View/Print Full Quote Page");
			Assertions.verify(
					viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData().equals("Not Selected"), true,
					"View/Print Full Quote Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("21").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData() + " is verified",
					false, false);

			Assertions.verify(
					viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData().equals("Not Selected"), true,
					"View/Print Full Quote Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("23").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData() + " is verified",
					false, false);
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

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

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

			// Validating the premium amount
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Asserting Sinkhole and cgcc availability in quote document
			policySummaryPage.quoteNoLinkNAHO.scrollToElement();
			policySummaryPage.quoteNoLinkNAHO.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Quote Number link");
			Assertions.verify(viewOrPrintFullQuotePage.closeButton.checkIfElementIsDisplayed(), true,
					"Quote Document Page", "Quote Document Page loaded successfully", false, false);

			Assertions.addInfo("Scenario 02", "Verifying Sinkhole and CGCC are Not Selected in Quote Document Page");
			Assertions.verify(
					viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData().equals("Not Selected"), true,
					"Quote Document Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("21").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData() + " is verified",
					false, false);

			Assertions.verify(
					viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData().equals("Not Selected"), true,
					"Quote Document Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("23").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData() + " is verified",
					false, false);

			viewOrPrintFullQuotePage.closeButton.click();
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Check if the NRNL date is displayed according to the Notice period on Policy
			// Summary page.
			// click on renewal indicators link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// select non renewal check box
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);
			renewalIndicatorsPage.nonRenewal.scrollToElement();
			renewalIndicatorsPage.nonRenewal.select();
			Assertions.passTest("Renewal Indicators Page", "Selected the Non Renewal Checkbox");

			// select non renewal reason and enter legal notice wording
			renewalIndicatorsPage.nonRenewalReasonArrow.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonArrow.click();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason"))
					.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason")).click();
			renewalIndicatorsPage.nonRenewalLegalNoticeWording.setData(testData.get("LegalNoticeWording"));

			// click on update
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update Button");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 03",
					"Verifying the NRNL date is displayed according to the Notice period for the state FL");

			// below code is commented as change requested for message.
			Assertions.verify(policySummaryPage.nocMessage.getData().contains(testData.get("TransactionEffectiveDate")),
					true, "Policy Summary Page", "The Message displayed is " + policySummaryPage.nocMessage.getData(),
					false, false);
			Assertions.verify(
					policySummaryPage.renewalDelMsg.getData().contains("Renewal Indicators Successfully Updated"), true,
					"Policy Summary Page", "The Message displayed is " + policySummaryPage.nocMessage.getData(), false,
					false);
			Assertions.addInfo("Policy Summary Page", "Verifying the status of the application");
			Assertions.verify(policySummaryPage.autoRenewalIndicators.getData().contains("Non-Renewal"), true,
					"Policy Summary Page",
					"The status of the application changed to :" + policySummaryPage.autoRenewalIndicators.getData(),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on Policy Snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			// Assert EMPA Surcharge from policy snapshot page
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);
			Assertions.verify(viewPolicySnapShot.empaSurcharge.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "The EMPA Surcharge : " + viewPolicySnapShot.empaSurcharge.getData()
							+ " displayed for New Business is verified",
					false, false);

			viewPolicySnapShot.goBackButton.click();
			Assertions.passTest("View Policy Snapshot Page", "Clicked on Back button");

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Flat cancel the policy
			// click on cancel policy link
			Assertions.addInfo("Scenario 04", "Flat Cancellation,Verifying the Policy Status and EMPA Surcharge");
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page loaded successfully", false, false);
			cancelPolicyPage.enterCancellationDetails(testData);

			// Assert policy status
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status : " + policySummaryPage.policyStatus.getData() + " displayed is verified ",
					false, false);

			policySummaryPage.cancelEnd.waitTillInVisibilityOfElement(60);
			Assertions.verify(policySummaryPage.cancelEnd.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The " + policySummaryPage.cancelEnd.getData() + " document present is verified", false, false);

			policySummaryPage.transHistReason.formatDynamicPath("3").scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath("3").click();

			// Click on Policy Snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			// Assert EMPA Surcharge from policy snapshot page
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);
			Assertions.verify(viewPolicySnapShot.empaSurcharge.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "The EMPA Surcharge : " + viewPolicySnapShot.empaSurcharge.getData()
							+ " displayed after cancellation verified",
					false, false);

			viewPolicySnapShot.goBackButton.click();
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Reinstating the Policy
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Reinstate Policy link");
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);
			Assertions.passTest("Policy Summary Page", "Policy Reinstated successfully");

			// To Change from None to Sinkhole
			// Click on Endorse PB
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

			// Click on Change Coverage Option link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// Entering Optional Coverage details
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded Successfully", false, false);
			Assertions.passTest("Create Quote Page",
					"Sinkhole Original Value : " + createQuotePage.sinkholeData.getData());
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page",
					"Sinkhole Latest Value : " + createQuotePage.sinkholeData.getData());

			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Adding below code for IO-20992
			// Getting surplus contribution value from endorse policy page
			String transactionSurplusContributionValue = endorsePolicyPage.surplusContributionValueNAHO
					.formatDynamicPath(1).getData().replace("$", "");
			String annualSurplusContributionValue = endorsePolicyPage.surplusContributionValueNAHO.formatDynamicPath(2)
					.getData().replace("$", "").replace("%", "");
			String termTotalSurplusContributionValue = endorsePolicyPage.surplusContributionValueNAHO
					.formatDynamicPath(3).getData().replace("$", "");
			String transactionPremium = endorsePolicyPage.premiumDetails.formatDynamicPath(1, 2).getData()
					.replace("$", "").replace(",", "");
			String annualPremium = endorsePolicyPage.premiumDetails.formatDynamicPath(1, 3).getData().replace("$", "")
					.replace(",", "");
			String termTotalPremium = endorsePolicyPage.premiumDetails.formatDynamicPath(1, 4).getData()
					.replace("$", "").replace(",", "");

			// converting string to double
			double vieParticipation = Double.parseDouble(testData.get("VIEParticipation"));
			double d_transactionSurplusContributionValue = Double.parseDouble(transactionSurplusContributionValue);
			double d_annualSurplusContributionValue = Double.parseDouble(annualSurplusContributionValue);
			double d_termTotalSurplusContributionValue = Double.parseDouble(termTotalSurplusContributionValue);
			double d_transactionPremium = Double.parseDouble(transactionPremium);
			double d_annualPremium = Double.parseDouble(annualPremium);
			double d_termTotalPremium = Double.parseDouble(termTotalPremium);

			// Verifying actual and calculated surplus contribution value
			Assertions.addInfo("Scenario 05",
					"Verifying actual and calculated surplus contribution value on endorse policy page");
			// Calculating transaction surplus contribution value
			double calSurplusContributionValue = d_transactionPremium * vieParticipation;
			if (Precision.round(Math.abs(Precision.round(d_transactionSurplusContributionValue, 2)
					- Precision.round(calSurplusContributionValue, 2)), 2) < 0.5) {
				Assertions.passTest("Endorse Policy Page",
						"Actual and calculated transaction surplus contribution values both are same,calculated transaction surpluscontribution value is : "
								+ "$" + Precision.round(calSurplusContributionValue, 2));
				Assertions.passTest("Endorse Policy Page", "Actual transaction surplus contribution value is : " + "$"
						+ d_transactionSurplusContributionValue);
			} else {
				Assertions.verify(d_transactionSurplusContributionValue, calSurplusContributionValue,
						"Endorse Policy Page",
						"The Difference between actual and calculated transaction surplus contribution valu is more than 0.5",
						false, false);
			}

			// Calculating annual surplus contribution value
			calSurplusContributionValue = d_annualPremium * vieParticipation;
			if (Precision.round(Math.abs(Precision.round(d_annualSurplusContributionValue, 2)
					- Precision.round(calSurplusContributionValue, 2)), 2) < 0.5) {
				Assertions.passTest("Endorse Policy Page",
						"Actual and calculated annual surplus contribution values both are same,Calculated annual surpluscontribution value is : "
								+ "$" + Precision.round(calSurplusContributionValue, 2));
				Assertions.passTest("Endorse Policy Page",
						" Actual annual surplus contribution value is : " + "$" + d_annualSurplusContributionValue);
			} else {
				Assertions.verify(d_annualSurplusContributionValue, calSurplusContributionValue, "Endorse Policy Page",
						"The Difference between actual and calculated annual surplus contribution valu is more than 0.5",
						false, false);
			}

			// Calculating annual surplus contribution value
			calSurplusContributionValue = d_termTotalPremium * vieParticipation;
			if (Precision.round(Math.abs(Precision.round(d_termTotalSurplusContributionValue, 2)
					- Precision.round(calSurplusContributionValue, 2)), 2) < 0.5) {
				Assertions.passTest("Endorse Policy Page",
						"Actual and calculated termtotal surplus contribution values both are same,Calculated termtotal surpluscontribution value is : "
								+ "$" + Precision.round(calSurplusContributionValue, 2));
				Assertions.passTest("Endorse Policy Page", " Actual termtotal surplus contribution value is : " + "$"
						+ d_termTotalSurplusContributionValue);
			} else {
				Assertions.verify(d_termTotalSurplusContributionValue, calSurplusContributionValue,
						"Endorse Policy Page",
						"The Difference between actual and calculated termtotal surplus contribution valu is more than 0.5",
						false, false);
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");
			// IO-20992 Ended

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Asserting the Values From Endorse Summary Page
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true,
					"Endorse Summary Page", "Endorse Summary Page Loaded Successfully", false, false);
			Assertions.addInfo("Scenario 06", "Change The Sinkhole Coverage From None to Sinkhole");
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					endorsePolicyPage.endorsementSummary.formatDynamicPath(5).getData() + " Changed From : "
							+ endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData() + " To : "
							+ endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// IO-20992
			// Getting surplus contribution value from endorsement summary page
			double traxSurplusContributionValueEndtSummaryPage = Double
					.parseDouble(endorsePolicyPage.surplusContributionValueNAHO.formatDynamicPath(1).getData()
							.replace("$", "").replace("%", ""));
			double annualSurplusContributionValueEndtSummaryPage = Double
					.parseDouble(endorsePolicyPage.surplusContributionValueNAHO.formatDynamicPath(2).getData()
							.replace("$", "").replace("%", ""));
			double termSurplusContributionValueEndtSummaryPage = Double
					.parseDouble(endorsePolicyPage.surplusContributionValueNAHO.formatDynamicPath(3).getData()
							.replace("$", "").replace("%", ""));

			// Verifying surplus contribution value from endorse summary page and endorse
			// policy page
			Assertions.addInfo("Scenario 07",
					"Verifying surplus contribution value from endorse summary page and endorse policy page");
			if (Precision.round(Math.abs(Precision.round(traxSurplusContributionValueEndtSummaryPage, 2)
					- Precision.round(d_transactionSurplusContributionValue, 2)), 2) < 0.5) {
				Assertions.passTest("Endorse Summary Page",
						"Endorse summary page and endorse policy page transaction surplus contribution values both are same,transaction surpluscontribution value from endorse policy page : "
								+ "$" + Precision.round(d_transactionSurplusContributionValue, 2));
				Assertions.passTest("Endorse Summary Page",
						"Transaction surplus contribution value from endorse summary page : " + "$"
								+ traxSurplusContributionValueEndtSummaryPage);
			} else {
				Assertions.verify(traxSurplusContributionValueEndtSummaryPage, d_transactionSurplusContributionValue,
						"Endorse Policy Page",
						"The Difference between Endorse summary page and endorse policy page transaction surplus contribution valu is more than 0.5",
						false, false);
			}
			if (Precision.round(Math.abs(Precision.round(annualSurplusContributionValueEndtSummaryPage, 2)
					- Precision.round(d_annualSurplusContributionValue, 2)), 2) < 0.5) {
				Assertions.passTest("Endorse Summary Page",
						"Endorse summary page and endorse policy page annual surplus contribution values both are same,Annual surpluscontribution value from endorse policy page : "
								+ "$" + Precision.round(d_annualSurplusContributionValue, 2));
				Assertions.passTest("Endorse Summary Page",
						"Annual surplus contribution value from endorse summary page : " + "$"
								+ annualSurplusContributionValueEndtSummaryPage);
			} else {
				Assertions.verify(annualSurplusContributionValueEndtSummaryPage, d_annualSurplusContributionValue,
						"Endorse Policy Page",
						"The Difference between Endorse summary page and endorse policy page annual surplus contribution valu is more than 0.5",
						false, false);
			}
			if (Precision.round(Math.abs(Precision.round(termSurplusContributionValueEndtSummaryPage, 2)
					- Precision.round(d_termTotalSurplusContributionValue, 2)), 2) < 0.5) {
				Assertions.passTest("Endorse Summary Page",
						"Endorse summary page and endorse policy page termtotal surplus contribution values both are same,Termtotal surpluscontribution value from endorse policy page : "
								+ "$" + Precision.round(d_termTotalSurplusContributionValue, 2));
				Assertions.passTest("Endorse Summary Page",
						"Term total surplus contribution value from endorse summary page : " + "$"
								+ termSurplusContributionValueEndtSummaryPage);
			} else {
				Assertions.verify(termSurplusContributionValueEndtSummaryPage, d_termTotalSurplusContributionValue,
						"Endorse Policy Page",
						"The Difference between Endorse summary page and endorse policy page termtotal surplus contribution valu is more than 0.5",
						false, false);
			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");
			// IO-20992 ended

			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			policySummaryPage.waitTime(5);
			policySummaryPage.transHistReasonNAHO.formatDynamicPath("5").scrollToElement();
			policySummaryPage.transHistReasonNAHO.formatDynamicPath("5").click();

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			Assertions.verify(policySummaryPage.pbEndt800LinkNAHO.formatDynamicPath("5").checkIfElementIsDisplayed(),
					true, "Policy Summary Page",
					"The " + policySummaryPage.pbEndt800LinkNAHO.formatDynamicPath("5").getData()
							+ " form present is verified",
					false, false);

			// Click on View Policy Snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			Assertions.addInfo("Scenario 08", "Change The Sinkhole Coverage From None to Sinkhole");
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);
			Assertions.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData().equals("Included"),
					true, "View Policy Snapshot Page", "The Option "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Assert Coverage's and Premium section details and SLTF Calculation
			Assertions.addInfo("Scenario 09",
					"Sltf,Fslso calculation and verify Empa Surcharge is $0 after Endorsement in policy snapshot page");
			// Asserting SLTF value on view policy snapshot page
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData();
			String inspectionFeeValue = viewOrPrintFullQuotePage.insurerInspectionFeeValue.getData();
			String policyFeeValue = viewOrPrintFullQuotePage.insurerPolFeeVlaue.getData();
			String sltfValue = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace(",", "");
			String surplusContributionValueSnapShot = viewPolicySnapShot.surplusContributionValue.getData().replace("%",
					"");
			Assertions.passTest("View Policy Snapshot Page", "The Actual Surplus Lines Tax : " + sltfValue);

			double d_premiumValue = Double.parseDouble(premiumValue.replace("$", "").replace(",", ""));
			double d_inspectionFeeValue = Double.parseDouble(inspectionFeeValue.replace("$", "").replace(",", ""));
			double d_policyFeeValue = Double.parseDouble(policyFeeValue.replace("$", "").replace(",", ""));
			double d_surplusContributionValueSnapShot = Double
					.parseDouble(surplusContributionValueSnapShot.replace("$", "").replace(",", ""));
			double d_sltfPercentageValuevpfq = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_sltfValue = (d_premiumValue + d_inspectionFeeValue + d_policyFeeValue
					+ d_surplusContributionValueSnapShot) * d_sltfPercentageValuevpfq;
			Assertions.passTest("View Policy Snapshot Page",
					"The Calculated Surplus Lines Tax : " + "$" + df.format(d_sltfValue));

			Assertions.verify(sltfValue, "$" + df.format(d_sltfValue), "View Policy Snapshot Page",
					"Actual and Calculated SLTF Values are matching and calculated according to 4.94% SLTF value",
					false, false);

			// Calculate FSLSO Service Fee
			testData = data.get(data_Value1);
			double d_fslsoservicePercentage = Double.parseDouble(testData.get("FSLSOServiceFeePercentage"));
			double d_fslsocalculatedValue = (d_premiumValue + d_inspectionFeeValue + d_policyFeeValue
					+ d_surplusContributionValueSnapShot) * d_fslsoservicePercentage;
			Assertions.passTest("View Policy Snapshot Page",
					"The Calculated FSLSO Service Fee : " + "$" + df.format(d_fslsocalculatedValue));

			String actualFslsoServiceFee = viewPolicySnapShot.fSLSOServeceFee.getData();
			Assertions.passTest("View Policy Snapshot Page", "The Actual FSLSO Service Fee : " + actualFslsoServiceFee);

			Assertions.verify(actualFslsoServiceFee, "$" + df.format(d_fslsocalculatedValue),
					"View Policy Snapshot Page",
					"Actual and Calculated FSLSO Service Fee Values are matching acording the 0.06% FSLSO Service Fee",
					false, false);

			// Assert EMPA Surcharge
			double expectedEmpaSurcharge = 0.00;
			String actualEmpaSurcharge = viewPolicySnapShot.empaSurcharge.getData().replace("$", "");
			double d_actualEmpaSurcharge = Double.parseDouble(actualEmpaSurcharge);
			Assertions.verify("$" + df.format(d_actualEmpaSurcharge), "$" + df.format(expectedEmpaSurcharge),
					"View Policy Snapshot Page",
					"The EMPA Surcharge : " + viewPolicySnapShot.empaSurcharge.getData() + " displayed is verified",
					false, false);

			viewPolicySnapShot.goBackButton.click();

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			policySummaryPage.transHistReasonNAHO.formatDynamicPath("2").scrollToElement();
			policySummaryPage.transHistReasonNAHO.formatDynamicPath("2").click();

			// To Change from SinkHole to CGCC
			Assertions.addInfo("Scenario 10", "Change The Sinkhole Coverage from Sinkhole to CGCC");
			// Click on Endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Entering Endorsement effective date
			testData = data.get(data_Value3);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on Change Coverage Option link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// Entering Optional Coverage details
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded Successfully", false, false);
			Assertions.passTest("Create Quote Page",
					"Sinkhole Original Value : " + createQuotePage.sinkholeData.getData());
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page",
					"Sinkhole Latest Value : " + createQuotePage.sinkholeData.getData());

			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Asserting the Values From Endorse Summary Page
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true,
					"Endorse Summary Page", "Endorse Summary Page Loaded Successfully", false, false);

			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					endorsePolicyPage.endorsementSummary.formatDynamicPath(5).getData() + " Changed From : "
							+ endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData() + " To : "
							+ endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData() + " is verified",
					false, false);

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Click on View Policy Snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);

			Assertions.verify(
					viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData().equals("Not Selected"), true,
					"View Policy Snapshot Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("23").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData() + " is verified",
					false, false);

			viewPolicySnapShot.goBackButton.click();
			Assertions.passTest("View Policy Snapshot Page", "Clicked on Back Button");

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Click on transaction history
			policySummaryPage.transHistReasonNAHO.formatDynamicPath("2").scrollToElement();
			policySummaryPage.transHistReasonNAHO.formatDynamicPath("2").click();

			// To change from CGCC to Sinkhole
			Assertions.addInfo("Scenario 11", "Change The Sinkhole Coverage from CGCC to Sinkhole");
			// Click on Endorse PB
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

			// Click on Change Coverage Option link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// Entering Optional Coverage details
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded Successfully", false, false);
			Assertions.passTest("Create Quote Page",
					"Sinkhole Original Value : " + createQuotePage.sinkholeData.getData());
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page",
					"Sinkhole Latest Value : " + createQuotePage.sinkholeData.getData());

			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Asserting the Values From Endorse Summary Page
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true,
					"Endorse Summary Page", "Endorse Summary Page Loaded Successfully", false, false);

			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					endorsePolicyPage.endorsementSummary.formatDynamicPath(5).getData() + " Changed From : "
							+ endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData() + " To : "
							+ endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData() + " is verified",
					false, false);

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.waitTime(2);

			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// click on 7th txn
			policySummaryPage.transHistReasonNAHO.formatDynamicPath("7").scrollToElement();
			policySummaryPage.transHistReasonNAHO.formatDynamicPath("7").click();

			// Click on View Policy Snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);

			Assertions.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData().equals("Included"),
					true, "View Policy Snapshot Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("21").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData() + " is verified",
					false, false);

			viewPolicySnapShot.goBackButton.click();
			Assertions.passTest("View Policy Snapshot Page", "Clicked on Back Button");

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// click on cancel policy link to non flat cancel
			Assertions.addInfo("Scenario 12", "Non Flat cancellation and verifying the status");
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

			testData = data.get(data_Value2);
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page loaded successfully", false, false);

			// Select a Reason and process not flat cancellation
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"The Cancel Reason Selected is " + testData.get("CancellationReason"));
			if (!testData.get("Cancellation_LegalNoticeWording").equals("")) {
				cancelPolicyPage.legalNoticeWording.scrollToElement();
				cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			}

			if (!testData.get("CancellationEffectiveDate").equals("")) {
				cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
				cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
			}
			Assertions.passTest("Cancel Policy Page",
					"The Cancellation Effective date Entered is : " + testData.get("CancellationEffectiveDate"));

			// Click on Next
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Assert the Presence of Pro-Rated Min Earned premium
			Assertions.verify(cancelPolicyPage.cancelOption.checkIfElementIsPresent(), true, "Cancel Policy Page",
					"Pro-Rated Earned Premium displayed is verified", false, false);

			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Complete Button");
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Assert policy status
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status : " + policySummaryPage.policyStatus.getData() + " displayed is verified ",
					false, false);
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			// Reinstating the Policy
			testData = data.get(data_value4);
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Reinstate Policy link");
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);
			Assertions.passTest("Policy Summary Page", "Policy Reinstated successfully");

			// Click on Endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			Assertions.addInfo("Scenario 13",
					"Check if Transaction Premium is negative also check Annual Total, Term Total and SLTF values are updated correctly based on the updated Policy expiration date.");

			// Entering Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Entering Policy Expiration date
			endorsePolicyPage.policyExpirationDate.scrollToElement();
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.policyExpirationDate.tab();

			// Click on Change Expiration Button
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();

			// Click on Ok Button for out of sequence transaction
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Asserting the values for Transaction details and policy expiration date
			Assertions.verify(endorsePolicyPage.newPremium.formatDynamicPath(1).getData().contains("-"), true,
					"Endorse Policy Page", "Transaction premium is displayed as "
							+ endorsePolicyPage.newPremium.formatDynamicPath(1).getData() + " negative value",
					false, false);
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			// Asserting Total Values for Transaction , Annual and Term
			Assertions.verify(endorsePolicyPage.newTotalValues.formatDynamicPath(2).checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Transaction Total is displayed as : "
							+ endorsePolicyPage.newTotalValues.formatDynamicPath(2).getData(),
					false, false);
			Assertions.verify(endorsePolicyPage.newTotalValues.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Transaction Annual Total is displayed as : "
							+ endorsePolicyPage.newTotalValues.formatDynamicPath(3).getData(),
					false, false);
			Assertions.verify(endorsePolicyPage.newTotalValues.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Transaction Term Total is displayed as : "
							+ endorsePolicyPage.newTotalValues.formatDynamicPath(4).getData(),
					false, false);

			// Calculating SLTF Values
			// Actual SLTF Value
			String actalSltfData = endorsePolicyPage.newSLTF.formatDynamicPath(1).getData().replace("-", "")
					.replace("$", "").replace(",", "");
			double actualSLTFValue = Double.parseDouble(actalSltfData);

			// Validating SLTF value - 4.94% of Total Premium (Premium + Inspection Fee)
			testData = data.get(data_Value2);
			String premiumValue1 = endorsePolicyPage.newPremium.formatDynamicPath(1).getData().replace("-", "")
					.replace("$", "").replace(",", "");
			String inspectionValue = endorsePolicyPage.newInspectionFee.formatDynamicPath(2).getData().replace("-", "")
					.replace("$", "").replace(",", "");
			String surplusContributionValueNAHO = endorsePolicyPage.surplusContributionValueNAHO.formatDynamicPath(1)
					.getData().replace("-", "").replace("$", "").replace(",", "").replace("%", "");

			double sltfFee = (Double.parseDouble(premiumValue1) + Double.parseDouble(inspectionValue)
					+ Double.parseDouble(surplusContributionValueNAHO))
					* Double.parseDouble(testData.get("SLTFPercentage"));

			Assertions.addInfo("Scenario 14", "Calculating and verifying SLTF value in View or print full quote page");
			if (Precision.round(Math.abs(Precision.round(actualSLTFValue, 2) - Precision.round(sltfFee, 2)), 2) < 0.5) {
				Assertions.passTest("View print full quote page",
						"Calculated surplus lines taxes and fees : " + "$" + Precision.round(sltfFee, 2));
				Assertions.passTest("View print full quote page",
						"Actual surplus lines taxes and fees : " + "$" + actalSltfData);
			} else {
				Assertions.verify(actualSLTFValue, sltfFee, "Endorse Policy Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.5", false,
						false);
			}
			Assertions.addInfo("Scenario 14", "Scenario 14 Ended");

			// click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Asserting the Values From Endorse Summary Page
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true,
					"Endorse Summary Page", "Endorse Summary Page Loaded Successfully", false, false);

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on Ok Button for out of sequence transaction
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Adding Below code for IO-21063
			// Click on first endorsement transaction
			policySummaryPage.transHistReasonNAHO.formatDynamicPath(5).scrollToElement();
			policySummaryPage.transHistReasonNAHO.formatDynamicPath(5).click();
			Assertions.passTest("Policy Summary Page", "Clicked on first PB endorsment link successfully");

			// Getting surplus contribution value from first endorsement transaction
			String endtSurplusContributionValue = policySummaryPage.transactionSurplusContribution.getData();

			// Click on view policy snapshot
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true, "Policy Snapshot Page",
					"Policy snapshot page loaded successfully", false, false);

			// Getting surplus contribution value from policy snapshot page
			String snapshotSurplusContributionValue = viewPolicySnapShot.surplusContributionValue.getData();

			Assertions.addInfo("Scenario 15",
					"Verifying first endorsment transaction and view policy snapshot page surplus contribution value both are same");
			Assertions.verify(snapshotSurplusContributionValue, endtSurplusContributionValue, "Policy Summary Page",
					"First endorsment transaction and view policy snapshot page surplus contribution value both are same,First endorsement transaction surplus contribution value is "
							+ endtSurplusContributionValue + ", Policy snapshot page surplus contribution value is "
							+ snapshotSurplusContributionValue,
					false, false);
			Assertions.addInfo("Scenario 15", "Scenario 15 Ended");
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBFLTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBFLTC001 ", "Executed Successfully");
			}
		}
	}
}
