/*Description:Create a Retail policy by selecting water damage deductible,cancel and reinstate the policy,initiate the renewal,perform PB endt by increasing the WDD on NB policy such that endorsement roll forwarded and Added IO-21380
Author: Pavan Mule
Date :  29/09/2023*/

package com.icat.epicenter.test.commercial.retailRegression;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC058 extends AbstractCommercialTest {

	public TC058() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID058.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ModifyForms modifyForms = new ModifyForms();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing variables
		int quotelength;
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		DecimalFormat df = new DecimalFormat("0.00");
		Map<String, String> testData = data.get(data_Value1);
		String transactioPremium = "$0";

		boolean isTestPassed = false;

		try {

			// Creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location page",
					"Location page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select peril page",
						"Select peril page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Entering Prior Losses
			Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
					"Prior loss page loaded successfully", false, false);
			priorLossPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior loss page", "Prior loss details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Click on modify forms
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create quote page", "Clicked on modify forms button successfully");

			// Verifying presence of 'ICAT SCOL 425 - Specify Water Damage Deductible form'
			// and Wordings
			Assertions.verify(modifyForms.override.checkIfElementIsDisplayed(), true, "Modify form page",
					"Modify form page loaded successfully", false, false);
			Assertions.addInfo("Scenario 01",
					"Verifying presence of 'ICAT SCOL 425 - specify water damage deductible form and wordings");
			Assertions.verify(modifyForms.specifyWaterDamageDeductibleText.checkIfElementIsDisplayed(), true,
					"Modify form page", "Specify water damage deductible form is displayed", false, false);
			Assertions.verify(
					modifyForms.specifyWaterDamageDeductibleText.getData()
							.contains("ICAT SCOL 425 - Specify Water Damage Deductible"),
					true, "Modify form page", "Specify water damage deductible wordings '"
							+ modifyForms.specifyWaterDamageDeductibleText.getData() + "' displayed",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Add the form 'ICAT SCOL 425 - Specify Water Damage Deductible form'
			modifyForms.specifyWaterDamageDeductibleCheckBox.scrollToElement();
			modifyForms.specifyWaterDamageDeductibleCheckBox.select();
			waitTime(5);
			Assertions.verify(modifyForms.byPolicyText.checkIfElementIsDisplayed(), true, "Modify forms page",
					"ICAT SCOL 425 - Specify water damage deductible form selected successfully", false, false);

			// Adding IO-21380
			// Verifying the presence of water damage deductible values in drop down and
			// presence of 'By policy' verbiage along with each of the drop down values
			Assertions.addInfo("Scenario 02",
					"Verifying the presence of water damage deductible options in dropdown and presence of 'By policy' verbiage along with each of the drop down values");
			for (int i = 0; i < 4; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);
				modifyForms.specifyWaterDamageDeductibleArrow.scrollToElement();
				modifyForms.specifyWaterDamageDeductibleArrow.click();
				waitTime(3);// if wait time removed means element intractable exception will come
				String specifyWaterDamageDeductibleOptioni = modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).getData();
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillPresenceOfElement(60);
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible"))
						.waitTillVisibilityOfElement(60);
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).scrollToElement();
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).click();
				Assertions.passTest("Modify forms page", "The specify water damage deductible option available "
						+ specifyWaterDamageDeductibleOptioni + " present is verified");
				waitTime(3);// if wait time removed means element intractable exception will come
				Assertions.verify(modifyForms.byPolicyText.getData().contains("By Policy"), true, "Modify forms page",
						"'By policy'verbiage is '" + modifyForms.byPolicyText.getData()
								+ "' displayed,when deductible option is " + specifyWaterDamageDeductibleOptioni,
						false, false);
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// select water damage deductible $10,000
			testData = data.get(data_Value1);
			modifyForms.specifyWaterDamageDeductibleArrow.scrollToElement();
			modifyForms.specifyWaterDamageDeductibleArrow.click();
			waitTime(3);// if wait time removed means element intractable exception will come
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillPresenceOfElement(60);
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillVisibilityOfElement(60);
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).scrollToElement();
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).click();
			waitTime(3);// if wait time removed means element intractable exception will come
			Assertions.passTest("Modify forms page",
					"The water damage deductible value "
							+ modifyForms.specifyWaterDamageDeductibleData
									.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).getData()
							+ " selected successfully");

			// Click on override button
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify forms page", "Clicked on override button successfully");

			// Click on Continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				Assertions.passTest("Create quote page", "Clicked on continue button successfully");
			}

			// Verifying the presence of star mark'*' on quote number, When water damage
			// deductible form selected
			Assertions.addInfo("Scenario 03",
					"Verifying the presence of star mark'*' on quote number, When water damage deductible form selected");
			Assertions.verify(accountOverviewPage.starMarkQuoteNumber.checkIfElementIsDisplayed(), true,
					"Account overview page",
					"In quote number '" + accountOverviewPage.starMarkQuoteNumber.getData() + "' is displayed", false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account overview page", "Quote Number1 :  " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View print full quote page", "View print full quote page loaded successfully", false, false);

			// Verifying the presence of 'By policy' verbiage along with water deductible
			// value on view print full quote page
			Assertions.addInfo("Scenario 04",
					"Verifying the presence of 'By policy' verbiage along with water deductible value");
			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(3).getData().contains("by policy"),
					true, "View print full quote page",
					"The 'By policy'verbiage is '"
							+ viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(3).getData()
							+ "' displayed for water damage deductible",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			// IO-21380 Ended

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View print full quote page", "Clicked on back button successfully");

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request Bind");

			// entering bind details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind request page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home page", "Quote for referral is searched successfully");

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy summary page", "Policy summary page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy summary page", "Policy number is " + policyNumber, false, false);

			// Click on cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy summary page", "Clicked on cancel policy link successfully");

			// Enter cancellation details
			Assertions.verify(cancelPolicyPage.cancellationEffectiveDate.checkIfElementIsDisplayed(), true,
					"Cancel policy page", "Cancel policy page loaded successfully", false, false);
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason"))
					.waitTillPresenceOfElement(60);
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			cancelPolicyPage.legalNoticeWording.waitTillPresenceOfElement(60);
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.appendData(testData.get("Cancellation_LegalNoticeWording"));
			cancelPolicyPage.legalNoticeWording.tab();
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();
			Assertions.passTest("Cancel policy page", "Cancellation details entred successfully");

			// Getting original premiumu and taxes and fees and pro-rated prorat factor
			String originalPremium = cancelPolicyPage.premiumValue.formatDynamicPath(2).getData().replace("$", "");
			String originalInspectionFee = cancelPolicyPage.inspectionFee.formatDynamicPath(2).getData().replace("$",
					"");
			String originalPolicyFee = cancelPolicyPage.policyFee.formatDynamicPath(2).getData().replace("$", "");
			String originalSurplusContributionValue = cancelPolicyPage.surplusContributionVlaue.formatDynamicPath(2)
					.getData().replace("$", "");
			String originalActualSLTF = cancelPolicyPage.taxesAndFees.formatDynamicPath(2).getData().replace("$", "");
			String slrfPercentage = testData.get("SLTFValue");
			String fslsoPercentage = testData.get("FSLSOServiceFeeValue");
			String empaSurchargeFee = testData.get("EMPASurcharge");
			String proRatedProrataFactor = cancelPolicyPage.prorataFactor.formatDynamicPath(1).getData();

			// Getting returned premium value and taxes and fees
			String returnedPremium = cancelPolicyPage.premiumValue.formatDynamicPath(4).getData().replace("-$", "");
			String returnedActualSltf = cancelPolicyPage.taxesAndFees.formatDynamicPath(4).getData().replace("-$", "");
			String returnedSurplusCOntributionValue = cancelPolicyPage.surplusContributionVlaue.formatDynamicPath(4)
					.getData().replace("-$", "");

			// Getting earned premium and SLTF value
			String earnedPremium = cancelPolicyPage.premiumValue.formatDynamicPath(3).getData().replace("$", "");
			String earnedActualSLTF = cancelPolicyPage.taxesAndFees.formatDynamicPath(3).getData().replace("$", "");
			String earnedSurplusContributionValue = cancelPolicyPage.surplusContributionVlaue.formatDynamicPath(3)
					.getData().replace("$", "");

			// Calculating original FSLSO Service Fee
			double originalCalFSLSOFee = (Double.parseDouble(originalPremium)
					+ Double.parseDouble(originalInspectionFee) + Double.parseDouble(originalPolicyFee)
					+ Double.parseDouble(originalSurplusContributionValue)) * (Double.parseDouble(fslsoPercentage));

			// Calculating original SLTF
			double originalCalSLTF = (Double.parseDouble(originalPremium) + Double.parseDouble(originalInspectionFee)
					+ Double.parseDouble(originalPolicyFee) + Double.parseDouble(originalSurplusContributionValue))
					* (Double.parseDouble(slrfPercentage)) + Double.parseDouble(empaSurchargeFee) + originalCalFSLSOFee;

			// Verifying original sltf value
			Assertions.addInfo("Scenario 05", "Verifying original sltf value");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(originalActualSLTF), 2))
					- Precision.round(originalCalSLTF, 2), 2) < 2.05) {
				Assertions.passTest("Cancel policy page",
						"Calculated original sltf value: " + "$" + df.format(originalCalSLTF));
				Assertions.passTest("Cancel policy page",
						"Actual original sltf value: " + cancelPolicyPage.taxesAndFees.formatDynamicPath(2).getData());
			} else {

				Assertions.verify(originalActualSLTF, originalCalSLTF, "Cancel policy page",
						"The difference between actual original sltf and calculated original sltf is more than 0.05",
						false, false);
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Calculating returned premium
			double calReturnedPremium = Double.parseDouble(originalPremium) * Double.parseDouble(proRatedProrataFactor);

			// Calculating returned FSLSO
			double calReturnedFSLSO = (Double.parseDouble(returnedPremium)
					+ Double.parseDouble(returnedSurplusCOntributionValue)) * Double.parseDouble(fslsoPercentage);

			// Calculating returned SLTF
			double calReturnedSLTF = ((Double.parseDouble(returnedPremium)
					+ Double.parseDouble(returnedSurplusCOntributionValue)) * Double.parseDouble(slrfPercentage))
					+ calReturnedFSLSO;

			// Verifying returned premium and returned sltf value
			Assertions.addInfo("Scenario 06", "Verifying returned premium and returned sltf value");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(returnedPremium), 2))
					- Precision.round(calReturnedPremium, 2), 2) < 2.05) {
				Assertions.passTest("Cancel policy page",
						"Calculated returned premium : " + "-$" + df.format(calReturnedPremium));
				Assertions.passTest("Cancel policy page",
						"Actual returned premium: " + cancelPolicyPage.premiumValue.formatDynamicPath(4).getData());
			} else {

				Assertions.verify(returnedPremium, calReturnedPremium, "Cancel policy page",
						"The difference between actual returned premium and calculated returned premium is more than 2.05",
						false, false);
			}

			// Verifying returned sltf value
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(returnedActualSltf), 2))
					- Precision.round(calReturnedSLTF, 2), 2) < 0.05) {
				Assertions.passTest("Cancel policy page",
						"Calculated returned sltf value: " + "-$" + df.format(calReturnedSLTF));
				Assertions.passTest("Cancel policy page",
						"Actual returned sltf value: " + cancelPolicyPage.taxesAndFees.formatDynamicPath(4).getData());
			} else {

				Assertions.verify(returnedActualSltf, calReturnedSLTF, "Cancel policy page",
						"The difference between actual returned sltf and calculated returned sltf is more than 0.05",
						false, false);
			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Calculating earned premium
			double calEarnedPremium = Double.parseDouble(originalPremium) - Double.parseDouble(returnedPremium);

			// Calculating earned sltf value
			double calEarnedSltf = Double.parseDouble(originalActualSLTF) - Double.parseDouble(returnedActualSltf);

			// Calculating earned surplus contribution
			double calEarnedSurpluscontribution = Double.parseDouble(originalSurplusContributionValue)
					- Double.parseDouble(returnedSurplusCOntributionValue);

			// Verifying earned premium and SLTF Value
			Assertions.addInfo("Scenario 07", "Verifying earned premium and SLTF value");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(earnedPremium), 2))
					- Precision.round(calEarnedPremium, 2), 2) < 0.05) {
				Assertions.passTest("Cancel policy page",
						"Calculated earned premium value: " + "$" + df.format(calEarnedPremium));
				Assertions.passTest("Cancel policy page",
						"Actual earned premium value: " + cancelPolicyPage.premiumValue.formatDynamicPath(3).getData());
			} else {

				Assertions.verify(earnedPremium, calEarnedPremium, "Cancel policy page",
						"The difference between actual earned premium and calculated earned premium is more than 0.05",
						false, false);
			}
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(earnedActualSLTF), 2))
					- Precision.round(calEarnedSltf, 2), 2) < 0.05) {
				Assertions.passTest("Cancel policy page", "Calculated sltf value: " + "$" + df.format(calEarnedSltf));
				Assertions.passTest("Cancel policy page",
						"Actual sltf value: " + cancelPolicyPage.taxesAndFees.formatDynamicPath(3).getData());
			} else {

				Assertions.verify(earnedActualSLTF, calEarnedSltf, "Cancel policy page",
						"The difference between actual earned sltf and calculated earned sltf is more than 0.05", false,
						false);
			}
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(earnedSurplusContributionValue), 2))
					- Precision.round(calEarnedSurpluscontribution, 2), 2) < 0.05) {
				Assertions.passTest("Cancel policy page", "Calculated earned surplus contribution value: " + "$"
						+ df.format(calEarnedSurpluscontribution));
				Assertions.passTest("Cancel policy page", "Actual earned surplus contribution Value: "
						+ cancelPolicyPage.surplusContributionVlaue.formatDynamicPath(3).getData());
			} else {

				Assertions.verify(earnedSurplusContributionValue, calEarnedSurpluscontribution, "Cancel policy page",
						"The difference between actual earned surplus contribution value and calculated earned surplus contribution value is more than 2.05",
						false, false);
			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on complete transaction button
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancel policy page", "Cancel transaction completed successfully");

			// Verifying policy status after canceling the policy,policy status ="NOC"
			Assertions.addInfo("Scenario 08", "Verifying policy status after canceling the policy");
			Assertions.verify(policySummaryPage.policyStatus.getData().contains("NOC"), true, "Policy summary page",
					"After canceling the policy the policy status is " + policySummaryPage.policyStatus.getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// IO-21081
			// Inspection and Policy fees must be zero for non-flat cancellation.
			policySummaryPage.transHistReason.formatDynamicPath(3).click();
			policySummaryPage.inspectionFee.scrollToElement();

			// Getting the policy fees and Inspection fees from Policy summary page
			String inspectionFee = policySummaryPage.inspectionFee.getData().replace("$", "");
			String policyFee = policySummaryPage.policyFee.getData().replace("$", "");

			// Verifying the Inspection fee for NOC transaction
			Assertions.addInfo("Policy Summary Page", "Verifying the Inspection fee for NOC transaction");
			Assertions.verify(inspectionFee, "0", "Policy Summary Page", "Inspection fees for NOC transaction is 0 ",
					false, false);

			// Verifying the Policy fee for NOC transaction
			Assertions.addInfo("Policy Summary Page", "Verifying the Policy fee for NOC transaction");
			Assertions.verify(policyFee, "0", "Policy Summary Page", "Policy fees for NOC transaction is 0 ", false,
					false);

			// IO-21081 is ended

			// Click on Reinstate policy link
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			Assertions.passTest("Policy summary page", "Clicked on reinstate link");

			// Enter reinstate details
			Assertions.verify(reinsatePolicyPage.completeReinstatement.checkIfElementIsDisplayed(), true,
					"Reinstate policy page", "Reinstate policy page loaded successfully", false, false);
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);
			Assertions.passTest("Reinstate policy page", "Reinstate policy details entered successfully");

			// Verifying policy status after reinstate,policy status ="Active"
			Assertions.addInfo("Scenario 09", "Verifying policy status after reinstate the policy");
			Assertions.verify(policySummaryPage.policyStatus.getData().contains("Active"), true, "Policy summary page",
					"After reinsating the policy the policy status is " + policySummaryPage.policyStatus.getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Clicking on Renew Policy link
			policySummaryPage.renewPolicy.checkIfElementIsPresent();
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy summary page", "Clicked on renew policy link successfully");

			// Entering Expacc info
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsPresent()) {
				homePage.goToHomepage.checkIfElementIsPresent();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.checkIfElementIsPresent();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc info page", "Expacc info details entered successfully");
			}

			// Performing Renewal
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Click on continue button
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.click();
			}

			// Click on renewal pop up
			if (policyRenewalPage.renewalYes.checkIfElementIsPresent()
					&& policyRenewalPage.renewalYes.checkIfElementIsDisplayed()) {
				policyRenewalPage.renewalYes.checkIfElementIsPresent();
				policyRenewalPage.renewalYes.click();
			}

			// Getting the renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account overview page", "Renewal Quote Number :  " + quoteNumber);

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account overview page", "Clicked on release renewal to producer button successfully");

			// Click on edit deductible and limits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account overview page", "Clicked on edit deductible and limits button successfully");

			// Click on modify forms
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create quote page", "Clicked on modify forms button successfully");

			// Verifying the Water Damage Deductible is checked and Deductible option
			// selected as $10000(retained the NB value)
			Assertions.addInfo("Scenario 10",
					"Verifying the water damage deductible is checked and Deductible option selected as $10000(retained the NB value)");
			Assertions.verify(modifyForms.byPolicyText.checkIfElementIsDisplayed(), true, "Modify forms page",
					"ICAT SCOL 425 - Specify Water Damage Deductible is checked verified", false, false);
			Assertions.verify(
					modifyForms.specifyWaterDamageDeductibleData.getData()
							.contains(testData.get("SpecifyWaterDamageDeductible")),
					true, "Modify forms page",
					"Deductible option selected as $10000(retained the NB value),selected deductible value is "
							+ modifyForms.specifyWaterDamageDeductibleData.getData(),
					false, false);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// click on back button
			WebDriverManager.getCurrentDriver().navigate().back();
			createQuotePage.waitTime(1);
			WebDriverManager.getCurrentDriver().navigate().back();
			Assertions.passTest("Modify forms page", "Clicked on back button successfully");

			// Click on previous policy link
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account overview page", "Clicked on view previous policy button successfully");

			// Click endorse PB link
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy summary page", "Policy summary page loaded successfully", false, false);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			policySummaryPage.renewalCreatedOkBtn.scrollToElement();
			policySummaryPage.renewalCreatedOkBtn.click();
			Assertions.passTest("Policy summary page", "Clicked on endorse pb link successfully");

			// Enter endorsement effective date and click on change coverage option link
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse policy page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse policy page", "Clicked on change coverage option link successfully");

			// Click on modify forms
			Assertions.verify(createQuotePage.continueToForms.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.continueToForms.scrollToElement();
			createQuotePage.continueToForms.click();
			Assertions.passTest("Create quote page", "Clicked on continue to forms button successfully");

			// select water damage deductible $25,000
			testData = data.get(data_Value2);
			modifyForms.specifyWaterDamageDeductibleArrow.scrollToElement();
			modifyForms.specifyWaterDamageDeductibleArrow.click();
			waitTime(3);// if wait time removed means element intractable exception will come
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillPresenceOfElement(60);
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillVisibilityOfElement(60);
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).scrollToElement();
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).click();
			waitTime(3);// if wait time removed means element intractable exception will come
			Assertions.passTest("Modify forms page",
					"The water damage deductible value "
							+ modifyForms.specifyWaterDamageDeductibleData
									.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).getData()
							+ " selected successfully");
			String specifyWaterDamageDeductibleValue = modifyForms.specifyWaterDamageDeductibleData
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).getData();

			// click on override button
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify forms page", "Clicked on override button successfully");

			// Click on Continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				Assertions.passTest("Create quote page", "Clicked on continue button successfully");
			}

			// Click on next
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// verify the transaction premium is $0
			Assertions.addInfo("Scenario 11", "Verifying the transaction premium = $0");
			Assertions.verify(endorsePolicyPage.transactionPremium.getData().contains(transactioPremium), true,
					"Endorse policy page",
					"The transaction premium is " + endorsePolicyPage.transactionPremium.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on complete button successfully");

			// Checking for roll forward endorsement button
			Assertions.addInfo("Scenario 12", "Verifying presence of Roll Forward button on endorse policy page");
			Assertions.verify(
					endorsePolicyPage.rollForwardBtn.checkIfElementIsPresent()
							&& endorsePolicyPage.rollForwardBtn.checkIfElementIsDisplayed(),
					true, "Endorse policy page", "Roll forward endorsement button is displayed", false, false);
			Assertions.addInfo("Scenario 12", "Scenario 12 ended");

			// Click on roll forward button
			endorsePolicyPage.rollForwardBtn.scrollToElement();
			endorsePolicyPage.rollForwardBtn.click();
			Assertions.passTest("Endorse policy page", "Clicked on roll forward endorsement button");

			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on close button successfully");

			// click on view active renewal link
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy summary page", "Clicked on view active renewal button successfully");

			// Verifying the renewal quote number is changed after roll forwarding the
			// Endorsement.
			Assertions.addInfo("Scenario 13",
					"Verifying the renewal quote number is changed after roll forwarding the Endorsement");
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			String newQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.verify(newQuoteNumber.equals(quoteNumber), false, "Account overview page",
					"Afteer roll forwarding the Endorsement, the latest quote number is " + newQuoteNumber
							+ " verified",
					false, false);
			Assertions.addInfo("Scenario 13", "Scenario 13 ended");

			// Click on edit deductible and limits link
			Assertions.verify(accountOverviewPage.lapseRenewal.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account overview page", "Clicked on editdeductible and limits button successfully");

			// Click on modify forms
			Assertions.verify(createQuotePage.modifyForms.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();

			// Verifying the Water Damage Deductible is checked and Deductible option
			// selected as $25,000 once the Endorsement is roll forwarded
			Assertions.addInfo("Scenario 14",
					"Verifying the water damage deductible is checked and Deductible option selected as $25,000 once the Endorsement is roll forwarded");
			Assertions.verify(modifyForms.specifyWaterDamageDeductibleData.getData(), specifyWaterDamageDeductibleValue,
					"Modify forms page",
					"Veridying the water deductible value is roll forwarded,the roll forwarded value is "
							+ modifyForms.specifyWaterDamageDeductibleData.getData(),
					false, false);
			Assertions.addInfo("Scenario 14", "Scenario 14 ended");

			// Click on back button
			WebDriverManager.getCurrentDriver().navigate().back();
			Assertions.passTest("Modify forms page", "Clicked on back button successfully");

			// updating AOCL to 50,000
			Assertions.passTest("Create quote page", "Updating AOCL to $50,000");
			createQuotePage.aoclDeductibleArrow.scrollToElement();
			createQuotePage.aoclDeductibleArrow.click();
			createQuotePage.aoclDeductibleOption.formatDynamicPath("$50,000").scrollToElement();
			createQuotePage.aoclDeductibleOption.formatDynamicPath("$50,000").click();

			// click on modify form button
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create quote page", "Clicked on modify form button successfully");

			// Verifying the 'ICAT SCOL 425 - Specify Water Damage Deductible form' is not
			// selected
			Assertions.addInfo("Scenario 15",
					"Verifying the 'ICAT SCOL 425 - Specify water damage deductible form' is not selected ");
			Assertions.verify(modifyForms.byPolicyText.checkIfElementIsPresent(), false, "Modify forms page",
					"ICAT SCOL 425 - Specify water damage deductible form not selected successfully", false, false);
			Assertions.addInfo("Scenario 15", "Scenario 15 Ended");

			// Click on back button
			WebDriverManager.getCurrentDriver().navigate().back();
			Assertions.passTest("Modify forms page", "Clicked on back button successfully");

			// Updating AOCL to $10,000
			Assertions.passTest("Create quote page", "Updating AOCL to $10,000");
			createQuotePage.aoclDeductibleArrow.scrollToElement();
			createQuotePage.aoclDeductibleArrow.click();
			createQuotePage.aoclDeductibleOption.formatDynamicPath(testData.get("AOCLDeductibleValue"))
					.scrollToElement();
			createQuotePage.aoclDeductibleOption.formatDynamicPath(testData.get("AOCLDeductibleValue")).click();
			Assertions.passTest("Create quote page",
					"The latest AOCL values is " + createQuotePage.aoclDeductibleData.getData());

			// click on modify form button
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create quote page", "Clicked on modify form button successfully");

			// Add the form 'ICAT SCOL 425 - Specify Water Damage Deductible form'
			modifyForms.specifyWaterDamageDeductibleCheckBox.scrollToElement();
			modifyForms.specifyWaterDamageDeductibleCheckBox.select();
			waitTime(5);
			Assertions.verify(modifyForms.byPolicyText.checkIfElementIsDisplayed(), true, "Modify forms page",
					"ICAT SCOL 425 - Specify Water Damage Deductible form selected successfully", false, false);

			// Verify the options displayed are greater than AOCL Deductible selected.
			// $25000, $50000, $100,000 under Water Damage Deductible
			Assertions.addInfo("Scenario 16",
					"Verify the options displayed are greater than AOCL Deductible selected.$25000, $50000, $100,000 under Water Damage Deductible");
			for (int i = 1; i < 4; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);
				modifyForms.specifyWaterDamageDeductibleArrow.scrollToElement();
				modifyForms.specifyWaterDamageDeductibleArrow.click();
				waitTime(3);// if wait time removed means element intractable exception will come
				String specifyWaterDamageDeductibleOptioni = modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).getData();
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillPresenceOfElement(60);
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible"))
						.waitTillVisibilityOfElement(60);
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).scrollToElement();
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).click();
				Assertions.passTest("Modify forms page", "The specify water damage deductible option available "
						+ specifyWaterDamageDeductibleOptioni + " present is verified");
				waitTime(3);// if wait time removed means element intractable exception will come
			}
			Assertions.addInfo("Scenario 16", "Scenario 16 Ended");

			// Select water damage deductible $25,000 and click on override button
			testData = data.get(data_Value2);
			modifyForms.specifyWaterDamageDeductibleArrow.scrollToElement();
			modifyForms.specifyWaterDamageDeductibleArrow.click();
			waitTime(3);// if wait time removed means element intractable exception will come
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillPresenceOfElement(60);
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillVisibilityOfElement(60);
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).scrollToElement();
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).click();
			waitTime(3);// if wait time removed means element intractable exception will come
			Assertions.passTest("Modify forms page",
					"The water damage deductible value "
							+ modifyForms.specifyWaterDamageDeductibleData
									.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).getData()
							+ " selected successfully");
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("Modify forms page", "Clicked on override button successfully");

			// Click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting the renewal re-quote number
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			String renewalReQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account overview page", "Renewal Re-Quote Number :  " + renewalReQuoteNumber);

			// Click on issue quote button
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.verify(accountOverviewPage.lapseRenewal.checkIfElementIsDisplayed(), true,
					"Account overview page", "Clicked on issue quote button successfully", false, false);

			// Click on create another quote button
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account overview page", "Clicked on create another quote button successfully");

			// Select peril, peril= wind
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select peril page",
						"Select peril page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Verifying modify form button not available
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			Assertions.addInfo("Scenario 17",
					"Verifying the absence of modify form button when peril selected as 'Wind'");
			Assertions.verify(createQuotePage.modifyForms.checkIfElementIsPresent(), false, "Create quote page",
					"Modify form button not displayed verified", false, false);
			Assertions.addInfo("Scenario 17", "Scenario 17 Ended");

			// Entering Create quote page Details
			testData = data.get(data_Value3);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// getting the renewal re-quote number2
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account overview page", "Renewal Re-Quote Number2 :  " + quoteNumber);

			// Selecting issue quote
			accountOverviewPage.renewalOffer.scrollToElement();
			accountOverviewPage.renewalOffer.click();
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "The issue quote selected successfully", false, false);

			// Enter bind details
			Assertions.passTest("Account overview page", "Click on request bind button");
			testData = data.get(data_Value1);
			accountOverviewPage.clickOnRequestBind(testData, renewalReQuoteNumber);

			if (!accountOverviewPage.requestBind.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(renewalReQuoteNumber);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(renewalReQuoteNumber);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Home page", "Quote for referral is searched successfully");

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy summary page", "Policy summary page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy summary page", "Renewal policy number is " + policyNumber, false, false);

			// Log Out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC058 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC058 ", "Executed Successfully");
			}
		}
	}
}
