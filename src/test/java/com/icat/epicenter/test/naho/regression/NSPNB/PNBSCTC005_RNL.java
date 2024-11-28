/** Program Description:Asserting SLTF calculations on cancelled policy, EQ/Loss assessment values roll forward, SC state specific wording on request bind page and renewal quote document, renewal lapse notice and Override premium and fees on renewals and IO-21801
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 05/27/2021
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
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBSCTC005_RNL extends AbstractNAHOTest {

	public PNBSCTC005_RNL() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/SC005.xls";
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
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ReferralPage referralPage = new ReferralPage();
		ViewOrPrintFullQuotePage viewPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		String quoteNumber;
		String policyNumber;
		String renewalQuoteNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		DecimalFormat df = new DecimalFormat("0.00");
		String covAvalue;
		String inflationGuardPercentage;
		double calCovAValue;
		String actualCovAValue;
		String calCovAValue_s;
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

			// Binding the quote and creating the policy
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);
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
			Assertions.passTest("Request Bind Page", "Values Entered Successfully");

			// Approve bind request
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfully");
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// approving referral
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Validating the premium amount
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policyNumber, false, false);

			// Go to homepage and serach the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home Page loaded successfully");
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy successfully");

			// Click on Renew policy
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Go to Home Page
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);
				// clicking on renewal policy link
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
			}
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link successfully");
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Getting Renewal Quote Number
			renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number is : " + renewalQuoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View print Full Quote");

			// Need to assert the Updated address
			Assertions.verify(viewPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 01",
					"Asserting the Presence of SC Wording and EQ Deductible,EQ Loss Assessment is not available");
			Assertions.verify(
					viewPrintFullQuotePage.quoteStatement
							.formatDynamicPath("A South Carolina deductible").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Wording " + viewPrintFullQuotePage.quoteStatement
							.formatDynamicPath("A South Carolina deductible").getData() + " displayed is verified",
					false, false);

			Assertions.verify(
					viewPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The EQ Deductible "
							+ viewPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData()
							+ " is verified",
					false, false);

			Assertions.verify(
					viewPrintFullQuotePage.discountsValue
							.formatDynamicPath("EQ Loss Assessment").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The EQ Loss Assessment "
							+ viewPrintFullQuotePage.discountsValue.formatDynamicPath("EQ Loss Assessment").getData()
							+ " is verified",
					false, false);

			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Adding IO-21801
			// Calculating inflation guard applying to Cov A
			covAvalue = testData.get("L1D1-DwellingCovA");
			inflationGuardPercentage = testData.get("InflationGuardPercentage");
			calCovAValue = (Double.parseDouble(covAvalue) * Double.parseDouble(inflationGuardPercentage));
			Assertions.passTest("View/Print Full Quote Page",
					"NB Coverage A value " + testData.get("L1D1-DwellingCovA"));
			Assertions.passTest("View/Print Full Quote Page",
					"Inflation Guard Percentage " + testData.get("InflationGuardPercentage"));

			// Rounding off
			long roundCalCovAValue = Math.round(calCovAValue);

			// Converting double to string
			calCovAValue_s = Double.toString(roundCalCovAValue).replace(".0", "");

			// Verifying and Asserting actual and calculated Cov A value
			// Cov A = Cov A*inflationguard%(1.0713)
			Assertions.addInfo("Scenario 02", "Verifying inflation guard applied to renewal Cov A");
			actualCovAValue = viewPrintFullQuotePage.coveragesValues.formatDynamicPath(4).getData().replace(",", "");
			Assertions.verify(actualCovAValue, "$" + calCovAValue_s, "View Print Full Quote Page",
					"Inflation guard " + testData.get("InflationGuardPercentage")
							+ " applied to Coverage A, actual coverage A value " + actualCovAValue
							+ " and calculated coverage A value $" + calCovAValue_s + " bothe are same",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			// IO-21801 Ended

			viewPrintFullQuotePage.backButton.click();

			// Click on Override Premium link
			accountOverviewPage.overridePremiumLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Override Premium link");

			Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees Page Loaded successfully", false,
					false);

			overridePremiumAndFeesPage.enterFeesDetailsNAHO(testData);
			Assertions.passTest("Override Premium Page", "Override premium details entered successfully");

			Assertions.addInfo("Scenario 03", "Calculation of SLTF on Account overview page");
			String premiumOnAccountOverviewPage = accountOverviewPage.premiumValue.getData();
			String feesOnAccountOverviewPage = accountOverviewPage.feesValue.getData();
			String sltfValueAccountOverviewPage = accountOverviewPage.sltfValue.getData().replace(",", "");
			String surplusContributionValues = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");

			double d_premiumValueOnAccountOverviewPage = Double
					.parseDouble(premiumOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_FeeValueOnAccountOverviewPage = Double
					.parseDouble(feesOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_surplusContributionValues = Double.parseDouble(surplusContributionValues);
			double d_sltfPercentageValue = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_sltfValueOnAccountOverviewPage = (d_premiumValueOnAccountOverviewPage
					+ d_FeeValueOnAccountOverviewPage + d_surplusContributionValues) * d_sltfPercentageValue;
			Assertions.passTest("Account Overview Page",
					"Calculated SLTF is : " + "$" + df.format(d_sltfValueOnAccountOverviewPage));
			Assertions.passTest("Account Overview Page", "Actual SLTF is : " + sltfValueAccountOverviewPage);
			Assertions.verify(sltfValueAccountOverviewPage, "$" + df.format(d_sltfValueOnAccountOverviewPage),
					"Account Overview Page",
					"Actual and Calculated SLTF Values are matching and calculated according to 6% SC SLTF value "
							+ sltfValueAccountOverviewPage,
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on View Previous Policy
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Previous Policy");
			Assertions.verify(policySummaryPage.viewActiveRenewal.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "The Original Policy Summary Page loaded successfully.The Policy Number is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on Endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Click on Ok button
			policyRenewalPage.rnlOkButton.scrollToElement();
			policyRenewalPage.rnlOkButton.click();

			// Entering Endorsement effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on Change Coverages link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// Change EQ and EQ Loss Assessment
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded successfully", false, false);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page",
					"Changed the EQ Deductible and EQ Loss Assessment values successfully");

			// Review and continue create quote page
			createQuotePage.scrollToBottomPage();
			createQuotePage.continueEndorsementButton.waitTillVisibilityOfElement(60);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.waitTillButtonIsClickable(60);
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Assert the Presence of EQ and EQ Loss Assessment sleceted
			endorsePolicyPage.waitTime(3);
			Assertions.addInfo("Scenario 04",
					"Asserting the Presence of Changed EQ Deductible and EQ Loss Assessment and Roll Forward Button");

			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(30).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The EQ Deductible Changed from "
							+ endorsePolicyPage.endorsementSummary.formatDynamicPath(31).getData() + " To "
							+ endorsePolicyPage.endorsementSummary.formatDynamicPath(32).getData(),
					false, false);
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(25).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The EQ Loss Assessment Coverage Changed from "
							+ endorsePolicyPage.endorsementSummary.formatDynamicPath(26).getData() + " To "
							+ endorsePolicyPage.endorsementSummary.formatDynamicPath(27).getData(),
					false, false);

			// continue with endorsement the endorsement
			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();

			}

			endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.completeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on Roll Forward enmt button
			Assertions.verify(endorsePolicyPage.rollForwardBtn.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"The Roll Forward Endorsement Button Present is verified", false, false);
			endorsePolicyPage.rollForwardBtn.scrollToElement();
			endorsePolicyPage.rollForwardBtn.click();
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			Assertions.passTest("Endorse Policy Page", "Clicked on Roll Forward the Endorsement Button");

			endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on View Active Renewal
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Active Renewal link");

			// Getting Renewal Quote Number
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number is : " + renewalQuoteNumber);

			// Asserting declinations column on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full Quote link");
			Assertions.addInfo("Scenario 05",
					"Asserting SLTF Values on View/Print Full Quote Page and Assert EQ/Loss assessment values Presence");

			// Asserting SLTF value on view print full quote page
			String premiumValue = viewPrintFullQuotePage.premiumValue.getData();
			String inspectionFeeValue = viewPrintFullQuotePage.insurerInspectionFeeValue.getData();
			String policyFeeValue = viewPrintFullQuotePage.insurerPolFeeVlaue.getData();
			String sltfValue = viewPrintFullQuotePage.surplusLinesTaxNaho.getData().replace(",", "").replace("$",
					"");
			String surplusContribution = viewPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");
			double d_premiumValue = Double.parseDouble(premiumValue.replace("$", "").replace(",", ""));
			double d_inspectionFeeValue = Double.parseDouble(inspectionFeeValue.replace("$", "").replace(",", ""));
			double d_policyFeeValue = Double.parseDouble(policyFeeValue.replace("$", "").replace(",", ""));
			double d_surplusContribution = Double.parseDouble(surplusContribution);
			double d_sltfValue = Double.parseDouble(sltfValue);

			// calculating SLTF
			double d_calSLTFValue = (d_premiumValue + d_inspectionFeeValue + d_policyFeeValue + d_surplusContribution)
					* d_sltfPercentageValue;

			if (Precision.round(Math.abs(Precision.round(d_sltfValue, 2) - Precision.round(d_calSLTFValue, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Actual and Calculated SLTF Values are matching and calculated according to 6% SC SLTF value ");
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated SLTF value is " + "$" + Precision.round(d_calSLTFValue, 2));
				Assertions.passTest("View/Print Full Quote Page", "Actaul SLTF value is " + "$" + d_sltfValue);

			} else {
				Assertions.verify(d_sltfValue, d_calSLTFValue, "View/Print Full Quote Page",
						"The Difference between actual and calculated SLTF value is more than 0.05", false, false);

			}

			Assertions.verify(
					viewPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The EQ Deductible "
							+ viewPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					viewPrintFullQuotePage.discountsValue
							.formatDynamicPath("EQ Loss Assessment").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The EQ Loss Assessment "
							+ viewPrintFullQuotePage.discountsValue.formatDynamicPath("EQ Loss Assessment").getData()
							+ " is verified",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");
			viewPrintFullQuotePage.backButton.click();

			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				quoteLen = accountOverviewPage.quoteNumber.getData().length();
				quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
				Assertions.passTest("Account overview page", "Renewal quote number " + quoteNumber);
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Approve Referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Referral page
				Assertions.passTest("Referral Page", "Quote referral approved successfully");
				approveDeclineQuotePage.clickOnApprove();

				referralPage.close.scrollToElement();
				referralPage.close.click();
				homePage.searchQuote(quoteNumber);
			}

			// click on release to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer successfully");

			testData = data.get(data_Value1);
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage.refreshPage();
			requestBindPage.renewalRequestBindNAHO(testData);

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. The Renewal PolicyNumber is : " + policyNumber, false,
					false);

			// Click on Cancel Policy Link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();

			testData = data.get(data_Value2);
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.waitTime(3);
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			if (!testData.get("CancellationEffectiveDate").equals("")) {
				cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
				cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
				cancelPolicyPage.cancellationEffectiveDate.tab();
			}

			if (cancelPolicyPage.legalNoticeWording.checkIfElementIsPresent()
					&& cancelPolicyPage.legalNoticeWording.checkIfElementIsDisplayed()) {
				if (!testData.get("Cancellation_LegalNoticeWording").equals("")) {
					cancelPolicyPage.legalNoticeWording.scrollToElement();
					cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
				}
			}
			cancelPolicyPage.nextButton.waitTillVisibilityOfElement(60);
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			Assertions.addInfo("Scenario 06",
					"Asserting sltf values calculated correctly on cancel policy page for Flat cancel");
			// Asserting SLTF value on view print full quote page
			String cancelPagepremiumValue = cancelPolicyPage.premium.formatDynamicPath("1").getData();
			String cancelPageinspectionFeeValue = cancelPolicyPage.inspectionFee1.formatDynamicPath("1").getData();
			String cancelPagepolicyFeeValue = cancelPolicyPage.policyFeeNAHO.formatDynamicPath("1").getData();
			String cancelPagesltfValue = cancelPolicyPage.SLTF.formatDynamicPath("1").getData().replace(",", "")
					.replace("$", "");
			String cnacelPageSurplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(1)
					.getData().replace("$", "").replace(",", "").replace("%", "");

			double d_cancelPagepremiumValue = Double
					.parseDouble(cancelPagepremiumValue.replace("$", "").replace(",", ""));
			double d_cancelPageinspectionFeeValue = Double
					.parseDouble(cancelPageinspectionFeeValue.replace("$", "").replace(",", ""));
			double d_cancelPagepolicyFeeValue = Double
					.parseDouble(cancelPagepolicyFeeValue.replace("$", "").replace(",", ""));
			double d_cnacelPageSurplusContributionValue = Double.parseDouble(cnacelPageSurplusContributionValue);
			double d_cancelPagesltfValue = Double.parseDouble(cancelPagesltfValue);

			// calculating SLTF
			double d_calcCancelPagesltfValue = (d_cancelPagepremiumValue + d_cancelPageinspectionFeeValue
					+ d_cancelPagepolicyFeeValue + d_cnacelPageSurplusContributionValue) * d_sltfPercentageValue;

			// Verifying and asserting SLTF value
			if (Precision.round(
					Math.abs(Precision.round(d_cancelPagesltfValue, 2) - Precision.round(d_calcCancelPagesltfValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page",
						"Actual and Calculated SLTF Values are matching and calculated according to 6% SC SLTF value ");
				Assertions.passTest("Cancel Policy Page",
						"Calculated SLTF value is " + "$" + Precision.round(d_calcCancelPagesltfValue, 2));
				Assertions.passTest("Cancel Policy Page", "Actaul SLTF value is " + "$" + d_cancelPagesltfValue);

			} else {
				Assertions.verify(d_cancelPagesltfValue, d_calcCancelPagesltfValue, "Cancel Policy Page",
						"The Difference between actual and calculated SLTF value is more than 0.05", false, false);

			}

			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on Complete Transaction
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();

			// click on Close
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on close button");

			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully.The Policy Status is "
							+ policySummaryPage.policyStatus.getData(),
					false, false);

			policySummaryPage.transHistReason.formatDynamicPath("3").scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath("3").click();

			// Click on view documents link
			policySummaryPage.viewDocuments.scrollToElement();
			policySummaryPage.viewDocuments.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Documents link");

			// click on home link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);

			policySummaryPage.transHistReason.formatDynamicPath("3").scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath("3").click();

			// Click on view documents link
			policySummaryPage.viewDocuments.scrollToElement();
			policySummaryPage.viewDocuments.click();
			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();
			policySummaryPage.viewDocuments.scrollToElement();
			policySummaryPage.viewDocuments.click();

			while (!policyDocumentsPage.policyDocuments.formatDynamicPath("CAN ENDT").checkIfElementIsPresent()) {
				policyDocumentsPage.refreshPage();
			}

			policyDocumentsPage.policyDocuments.formatDynamicPath("CAN ENDT").waitTillVisibilityOfElement(60);
			Assertions.addInfo("Scenario 07", "Asserting the Presence of Cancel Document");
			Assertions.verify(
					policyDocumentsPage.policyDocuments.formatDynamicPath("CAN ENDT").checkIfElementIsDisplayed(), true,
					"Policy Documents Page", policyDocumentsPage.policyDocuments.formatDynamicPath("CAN ENDT").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBSCTC005 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBSCTC005 ", "Executed Successfully");
			}
		}
	}

}
