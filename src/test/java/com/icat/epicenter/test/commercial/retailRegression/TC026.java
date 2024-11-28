/** Program Description: Check if the commercial retail policies will have access to endorsements that will adjust the policy expiration date. And Added IO-20921
 *  Author			   : John
 *  Date of Creation   : 27/07/2021
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC026 extends AbstractCommercialTest {

	public TC026() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID026.xls";
	}

	// Initializing the variables globally
	String quoteNumber;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		Map<String, String> testData;
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();

		testData = data.get(0);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Entering Location 1 Dwelling 1 Details
			
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			if (!testData.get("Peril").equals("Quake")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Create Quote Page loaded successfully", false, false);
				createQuotePage.enterQuoteDetailsCommercialNew(testData);
			} else {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting quote number
			quoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Quote Number for state " + testData.get("InsuredState") + " is "
							+ accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", ""),
					false, false);

			// Clicked on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// asserting commission rate
			Assertions.addInfo("Request Bind Page",
					"Asserting Commission Rate for state " + testData.get("QuoteState"));
			Assertions.verify(
					requestBindPage.commissionRate.getData(), "12.0%", "Request Bind Page", "Commission Rate for state "
							+ testData.get("QuoteState") + " is " + requestBindPage.commissionRate.getData(),
					false, false);

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");
			if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
					&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {

				// Go to HomePage
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote is searched successfully");

				// Click on Open Referral Link
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
						"Request Approval page loaded successfully", false, false);
				if (requestBindPage.chooseFile.checkIfElementIsPresent()
						&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
					if (!testData.get("FileNameToUpload").equals("")) {
						policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
					}
				}
				requestBindPage.approveRequest();
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			}

			// getting policy number
			if (policySummaryPage.policyNumber.checkIfElementIsPresent()
					&& policySummaryPage.policyNumber.checkIfElementIsDisplayed()) {
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
						"Policy Summary Page", "Policy Number is " + policySummaryPage.policyNumber.getData(), false,
						false);
			}

			// Asserting transaction sltf value before changing expiry date
			Assertions.addInfo("Policy Summary Page",
					"Asserting SLTF and Total Premium value before updating expiry date");

			// getting transaction values
			double premiumTrxnBeforeEndt = Double.parseDouble(policySummaryPage.PremiumFee.formatDynamicPath(1)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double inspectionFeeTrxnBeforeEndt = Double.parseDouble(policySummaryPage.PremiunInspectionFee
					.formatDynamicPath(1).getData().replace("$", "").replace("-", "").replace(",", ""));
			double policyFeeTrxnBeforeEndt = Double.parseDouble(policySummaryPage.PremiumPolicyFee.formatDynamicPath(1)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double sltfTrxnBeforeEndtActual = Double.parseDouble(policySummaryPage.sltfValue.formatDynamicPath(1)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double surplusContributionTrxnBeforeEndtActual = Double
					.parseDouble(policySummaryPage.surplusContributionValue.formatDynamicPath(1).getData()
							.replace("$", "").replace("-", "").replace(",", ""));
			double totalTrxnBeforeEndtActual = Double.parseDouble(policySummaryPage.premiumTotal.formatDynamicPath(2)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double fslsoTrxnBeforeEndtCalculated = (premiumTrxnBeforeEndt + inspectionFeeTrxnBeforeEndt
					+ policyFeeTrxnBeforeEndt + surplusContributionTrxnBeforeEndtActual)
					* ((Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100));
			double sltfTrxnBeforeEndtCalculated = (premiumTrxnBeforeEndt + inspectionFeeTrxnBeforeEndt
					+ policyFeeTrxnBeforeEndt + surplusContributionTrxnBeforeEndtActual)
					* ((Double.parseDouble(testData.get("SLTFValue")) / 100)) + fslsoTrxnBeforeEndtCalculated + 4;
			double totalTrxnBeforeEndtCalculated = premiumTrxnBeforeEndt + inspectionFeeTrxnBeforeEndt
					+ policyFeeTrxnBeforeEndt + sltfTrxnBeforeEndtActual + surplusContributionTrxnBeforeEndtActual;

			// getting annual values
			double premiumAnnualBeforeEndt = Double.parseDouble(policySummaryPage.PremiumFee.formatDynamicPath(2)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double inspectionFeeAnnualBeforeEndt = Double.parseDouble(policySummaryPage.PremiunInspectionFee
					.formatDynamicPath(2).getData().replace("$", "").replace("-", "").replace(",", ""));
			double policyFeeAnnualBeforeEndt = Double.parseDouble(policySummaryPage.PremiumPolicyFee
					.formatDynamicPath(2).getData().replace("$", "").replace("-", "").replace(",", ""));
			double surplusContributionValueAnnualBeforeEndt = Double
					.parseDouble(policySummaryPage.surplusContributionValue.formatDynamicPath(2).getData()
							.replace("$", "").replace("-", "").replace(",", ""));
			double sltfAnnualBeforeEndtActual = Double.parseDouble(policySummaryPage.sltfValue.formatDynamicPath(2)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double totalAnnualBeforeEndtActual = Double.parseDouble(policySummaryPage.premiumTotal.formatDynamicPath(3)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double fslsoAnnualBeforeEndtCalculated = (premiumAnnualBeforeEndt + inspectionFeeAnnualBeforeEndt
					+ policyFeeAnnualBeforeEndt + surplusContributionValueAnnualBeforeEndt)
					* ((Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100));
			double sltfAnnualBeforeEndtCalculated = (premiumAnnualBeforeEndt + inspectionFeeAnnualBeforeEndt
					+ policyFeeAnnualBeforeEndt + surplusContributionValueAnnualBeforeEndt)
					* ((Double.parseDouble(testData.get("SLTFValue")) / 100)) + fslsoAnnualBeforeEndtCalculated + 4;
			double totalAnnualBeforeEndtCalculated = premiumAnnualBeforeEndt + inspectionFeeAnnualBeforeEndt
					+ policyFeeAnnualBeforeEndt + sltfAnnualBeforeEndtActual + surplusContributionValueAnnualBeforeEndt;

			// getting term values
			double premiumTermBeforeEndt = Double.parseDouble(policySummaryPage.PremiumFee.formatDynamicPath(3)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double inspectionFeeTermBeforeEndt = Double.parseDouble(policySummaryPage.PremiunInspectionFee
					.formatDynamicPath(3).getData().replace("$", "").replace("-", "").replace(",", ""));
			double policyFeeTermBeforeEndt = Double.parseDouble(policySummaryPage.PremiumPolicyFee.formatDynamicPath(3)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double sltfTermBeforeEndtActual = Double.parseDouble(policySummaryPage.sltfValue.formatDynamicPath(3)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double totalTermBeforeEndtActual = Double.parseDouble(policySummaryPage.premiumTotal.formatDynamicPath(4)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double surplusContributionTermBeforeEndtActual = Double
					.parseDouble(policySummaryPage.surplusContributionValue.formatDynamicPath(3).getData()
							.replace("$", "").replace("-", "").replace(",", ""));
			double fslsoTermBeforeEndtCalculated = (premiumTermBeforeEndt + inspectionFeeTermBeforeEndt
					+ policyFeeTermBeforeEndt + surplusContributionTermBeforeEndtActual)
					* ((Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100));
			double sltfTermBeforeEndtCalculated = (premiumTermBeforeEndt + inspectionFeeTermBeforeEndt
					+ policyFeeTermBeforeEndt + surplusContributionTermBeforeEndtActual)
					* ((Double.parseDouble(testData.get("SLTFValue")) / 100)) + fslsoTermBeforeEndtCalculated + 4;
			double totalTermBeforeEndtCalculated = premiumTermBeforeEndt + inspectionFeeTermBeforeEndt
					+ policyFeeTermBeforeEndt + sltfTermBeforeEndtActual + surplusContributionTermBeforeEndtActual;

			// asserting transaction sltf and total premium
			if (Precision.round(Math.abs(
					Precision.round(sltfTrxnBeforeEndtActual, 2) - Precision.round(sltfTrxnBeforeEndtCalculated, 2)),
					2) < 0.5) {
				Assertions.passTest("Policy Summary Page",
						"Transaction SLTF is calculated correctly before updating Expiration date");
			} else {
				Assertions.verify(sltfTrxnBeforeEndtActual, sltfTrxnBeforeEndtCalculated, "Policy Summary Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.50", false, false);
			}
			if (Precision.round(Math.abs(
					Precision.round(totalTrxnBeforeEndtActual, 2) - Precision.round(totalTrxnBeforeEndtCalculated, 2)),
					2) < 0.5) {
				Assertions.passTest("Policy Summary Page",
						"Transaction Premium Total is calculated correctly before updating Expiration date");
			} else {
				Assertions.verify(totalTrxnBeforeEndtActual, totalTrxnBeforeEndtCalculated, "Policy Summary Page",
						"The Difference between actual transaction premium total and calculated transaction premium total is more than 0.50",
						false, false);
			}

			// asserting annual sltf and total premium
			if (Precision.round(Math.abs(Precision.round(sltfAnnualBeforeEndtActual, 2)
					- Precision.round(sltfAnnualBeforeEndtCalculated, 2)), 2) < 0.5) {
				Assertions.passTest("Policy Summary Page",
						"Annual SLTF is calculated correctly before updating Expiration date");
			} else {
				Assertions.verify(sltfAnnualBeforeEndtActual, sltfAnnualBeforeEndtCalculated, "Policy Summary Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.50", false, false);
			}
			if (Precision.round(Math.abs(Precision.round(totalAnnualBeforeEndtActual, 2)
					- Precision.round(totalAnnualBeforeEndtCalculated, 2)), 2) < 0.5) {
				Assertions.passTest("Policy Summary Page",
						"Annual Premium Total is calculated correctly before updating Expiration date");
			} else {
				Assertions.verify(totalAnnualBeforeEndtActual, totalAnnualBeforeEndtCalculated, "Policy Summary Page",
						"The Difference between actual annual premium total and calculated annual premium total is more than 0.50",
						false, false);
			}

			// asserting term sltf and total premium
			if (Precision.round(Math.abs(
					Precision.round(sltfTermBeforeEndtActual, 2) - Precision.round(sltfTermBeforeEndtCalculated, 2)),
					2) < 0.5) {
				Assertions.passTest("Policy Summary Page",
						"Term SLTF is calculated correctly before updating Expiration date");
			} else {
				Assertions.verify(sltfTermBeforeEndtActual, sltfTermBeforeEndtCalculated, "Policy Summary Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.50", false, false);
			}

			if (Precision.round(Math.abs(
					Precision.round(totalTermBeforeEndtActual, 2) - Precision.round(totalTermBeforeEndtCalculated, 2)),
					2) < 0.5) {
				Assertions.passTest("Policy Summary Page",
						"Term Premium Total is calculated correctly before updating Expiration date");
			} else {
				Assertions.verify(totalTermBeforeEndtActual, totalTermBeforeEndtCalculated, "Policy Summary Page",
						"The Difference between actual term premium total and calculated term premium total is more than 0.50",
						false, false);
			}

			// Click on endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endoresment Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// enter policy expiration date
			endorsePolicyPage.scrollToBottomPage();
			endorsePolicyPage.waitTime(2);// wait time is needed to scroll down to bottom of the page
			endorsePolicyPage.policyExpirationDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.policyExpirationDate.scrollToElement();
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();
			Assertions.passTest("Endorse Policy Page", "Updated Policy Expiry Date");

			// click on next and complete
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Ticket IO-21653 Getting Stack Trace on clicking "Push to RMS" button on
			// Endorse Policy page
			// click on Push to RMS button

			endorsePolicyPage.pushToRMSButton.scrollToElement();
			endorsePolicyPage.pushToRMSButton.click();
			Assertions.addInfo("Endorse Policy Page",
					"Verifying the stack trace on clciking Push to RMS button on Endorse Policy page ");
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Clicked on Push To RMS", false, false);

			// click on complete
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// click on close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// click on endt txn
			policySummaryPage.waitTime(2);// need wait time to click on the correct txn
			policySummaryPage.transHistEffDate.formatDynamicPath("3").waitTillButtonIsClickable(60);
			policySummaryPage.transHistEffDate.formatDynamicPath("3").click();

			// Asserting transaction sltf value after changing expiry date
			Assertions.addInfo("Policy Summary Page",
					"Asserting SLTF and Total Premium value after updating expiry date");

			// getting transaction values
			double premiumTrxnAfterEndt = Double.parseDouble(policySummaryPage.PremiumFee.formatDynamicPath(1).getData()
					.replace("$", "").replace("-", "").replace(",", ""));
			double inspectionFeeTrxnAfterEndt = Double.parseDouble(policySummaryPage.PremiunInspectionFee
					.formatDynamicPath(1).getData().replace("$", "").replace("-", "").replace(",", ""));
			double policyFeeTrxnAfterEndt = Double.parseDouble(policySummaryPage.PremiumPolicyFee.formatDynamicPath(1)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double sltfTrxnAfterEndtActual = Double.parseDouble(policySummaryPage.sltfValue.formatDynamicPath(1)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double surplusContributionTrxnAfterEndtActual = Double
					.parseDouble(policySummaryPage.surplusContributionValue.formatDynamicPath(1).getData()
							.replace("$", "").replace("-", "").replace(",", ""));
			double totalTrxnAfterEndtActual = Double.parseDouble(policySummaryPage.premiumTotal.formatDynamicPath(2)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double fslsoTrxnAfterEndtCalculated = (premiumTrxnAfterEndt + inspectionFeeTrxnAfterEndt
					+ policyFeeTrxnAfterEndt + surplusContributionTrxnAfterEndtActual)
					* ((Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100));

			// Calculating sltf= (premium+icatfees+surpluscontribution*sltf percent)+empa
			// surcharge+fslso
			double sltfTrxnAfterEndtCalculated = (premiumTrxnAfterEndt + inspectionFeeTrxnAfterEndt
					+ policyFeeTrxnAfterEndt + surplusContributionTrxnAfterEndtActual)
					* ((Double.parseDouble(testData.get("SLTFValue")) / 100)) + fslsoTrxnAfterEndtCalculated + 4;
			double totalTrxnAfterEndtCalculated = premiumTrxnAfterEndt + inspectionFeeTrxnAfterEndt
					+ policyFeeTrxnAfterEndt + sltfTrxnAfterEndtActual + surplusContributionTrxnAfterEndtActual;

			// getting annual values
			double premiumAnnualAfterEndt = Double.parseDouble(policySummaryPage.PremiumFee.formatDynamicPath(2)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double inspectionFeeAnnualAfterEndt = Double.parseDouble(policySummaryPage.PremiunInspectionFee
					.formatDynamicPath(2).getData().replace("$", "").replace("-", "").replace(",", ""));
			double policyFeeAnnualAfterEndt = Double.parseDouble(policySummaryPage.PremiumPolicyFee.formatDynamicPath(2)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double sltfAnnualAfterEndtActual = Double.parseDouble(policySummaryPage.sltfValue.formatDynamicPath(2)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double surplusContributionAnnualAfterEndtActual = Double
					.parseDouble(policySummaryPage.surplusContributionValue.formatDynamicPath(2).getData()
							.replace("$", "").replace("-", "").replace(",", ""));
			double totalAnnualAfterEndtActual = Double.parseDouble(policySummaryPage.premiumTotal.formatDynamicPath(3)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double fslsoAnnualAfterEndtCalculated = (premiumAnnualAfterEndt + inspectionFeeAnnualAfterEndt
					+ policyFeeAnnualAfterEndt + surplusContributionAnnualAfterEndtActual)
					* ((Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100));
			double sltfAnnualAfterEndtCalculated = (premiumAnnualAfterEndt + inspectionFeeAnnualAfterEndt
					+ policyFeeAnnualAfterEndt + surplusContributionAnnualAfterEndtActual)
					* ((Double.parseDouble(testData.get("SLTFValue")) / 100)) + fslsoAnnualAfterEndtCalculated + 4;
			double totalAnnualAfterEndtCalculated = premiumAnnualAfterEndt + inspectionFeeAnnualAfterEndt
					+ policyFeeAnnualAfterEndt + sltfAnnualAfterEndtCalculated
					+ surplusContributionAnnualAfterEndtActual;

			// getting term values
			double premiumTermAfterEndt = Double.parseDouble(policySummaryPage.PremiumFee.formatDynamicPath(3).getData()
					.replace("$", "").replace("-", "").replace(",", ""));
			double inspectionFeeTermAfterEndt = Double.parseDouble(policySummaryPage.PremiunInspectionFee
					.formatDynamicPath(3).getData().replace("$", "").replace("-", "").replace(",", ""));
			double policyFeeTermAfterEndt = Double.parseDouble(policySummaryPage.PremiumPolicyFee.formatDynamicPath(3)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double sltfTermAfterEndtActual = Double.parseDouble(policySummaryPage.sltfValue.formatDynamicPath(3)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double surplusContributionTermAfterEndtActual = Double
					.parseDouble(policySummaryPage.surplusContributionValue.formatDynamicPath(3).getData()
							.replace("$", "").replace("-", "").replace(",", ""));
			double totalTermAfterEndtActual = Double.parseDouble(policySummaryPage.premiumTotal.formatDynamicPath(4)
					.getData().replace("$", "").replace("-", "").replace(",", ""));
			double fslsoTermAfterEndtCalculated = (premiumTermAfterEndt + inspectionFeeTermAfterEndt
					+ policyFeeTermAfterEndt + surplusContributionTermAfterEndtActual)
					* ((Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100));
			double sltfTermAfterEndtCalculated = (premiumTermAfterEndt + inspectionFeeTermAfterEndt
					+ policyFeeTermAfterEndt + surplusContributionTermAfterEndtActual)
					* ((Double.parseDouble(testData.get("SLTFValue")) / 100)) + fslsoTermAfterEndtCalculated + 4;
			double totalTermAfterEndtCalculated = premiumTermAfterEndt + inspectionFeeTermAfterEndt
					+ policyFeeTermAfterEndt + sltfTermAfterEndtCalculated + surplusContributionTermAfterEndtActual;

			// asserting transaction sltf and total premium
			if (Precision.round(Math
					.abs(Precision.round(sltfTrxnAfterEndtActual, 2) - Precision.round(sltfTrxnAfterEndtCalculated, 2)),
					2) < 1.00) {
				Assertions.passTest("Policy Summary Page",
						"Transaction SLTF is calculated correctly after updating Expiration date");
			} else {
				Assertions.verify(sltfTrxnAfterEndtActual, sltfTrxnAfterEndtCalculated, "Policy Summary Page",
						"Calculated and Actual Transaction SLTF values are not matching", false, false);
			}

			// Verifying Transaction Premium Total is calculated correctly after updating
			// Expiration date
			Assertions.addInfo("Scenario 01",
					"Transaction Premium Total is calculated correctly after updating Expiration date");

			if (Precision.round(Math.abs(
					Precision.round(totalTrxnAfterEndtActual, 2) - Precision.round(totalTrxnAfterEndtCalculated, 2)),
					2) < 0.5) {
				Assertions.passTest("Policy Summary Page",
						"Transaction Premium Total is calculated correctly after updating Expiration date");
			} else {
				Assertions.verify(totalTrxnAfterEndtActual, totalTrxnAfterEndtCalculated, "Policy Summary Page",
						"Calculated and Actual Transaction Premium Total values are not matching", false, false);
			}

			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// asserting annual sltf and total premium
			if (Precision.round(Math.abs(
					Precision.round(sltfAnnualAfterEndtActual, 2) - Precision.round(sltfAnnualAfterEndtCalculated, 2)),
					2) < 1.00) {
				Assertions.passTest("Policy Summary Page",
						"Annual SLTF is calculated correctly after updating Expiration date");
			} else {
				Assertions.verify(sltfAnnualAfterEndtActual, sltfAnnualAfterEndtCalculated, "Policy Summary Page",
						"Calculated and Actual Annual SLTF values are not matching", false, false);
			}

			// Verifying Annual Premium Total is calculated correctly after updating
			// Expiration date
			Assertions.addInfo("Scenario 02",
					"Verifying Annual Premium Total is calculated correctly after updating Expiration date");
			if (Precision.round(Math.abs(Precision.round(totalAnnualAfterEndtActual, 2)
					- Precision.round(totalAnnualAfterEndtCalculated, 2)), 2) < 0.5) {
				Assertions.passTest("Policy Summary Page",
						"Annual Premium Total is calculated correctly after updating Expiration date");
			} else {
				Assertions.verify(totalAnnualAfterEndtActual, totalAnnualAfterEndtCalculated, "Policy Summary Page",
						"Calculated and Actual Annual Premium Total values are not matching", false, false);
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// asserting term sltf and total premium
			if (Precision.round(Math
					.abs(Precision.round(sltfTermAfterEndtActual, 2) - Precision.round(sltfTermAfterEndtCalculated, 2)),
					2) < 1.00) {
				Assertions.passTest("Policy Summary Page",
						"Term SLTF is calculated correctly after updating Expiration date");
			} else {
				Assertions.verify(sltfTermAfterEndtActual, sltfTermAfterEndtCalculated, "Policy Summary Page",
						"Calculated and Actual Term SLTF values are not matching", false, false);
			}

			// Verifying Term Premium Total is calculated correctly after updating
			// Expiration date
			Assertions.addInfo("Scenario 03",
					"Verifying Term Premium Total is calculated correctly after updating Expiration date");

			if (Precision.round(Math.abs(
					Precision.round(totalTermAfterEndtActual, 2) - Precision.round(totalTermAfterEndtCalculated, 2)),
					2) < 0.5) {
				Assertions.passTest("Policy Summary Page",
						"Term Premium Total is calculated correctly after updating Expiration date");
			} else {
				Assertions.verify(totalTermAfterEndtActual, totalTermAfterEndtCalculated, "Policy Summary Page",
						"Calculated and Actual Term Premium Total are not matching", false, false);
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Adding IO-20921
			// Click on cancel link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel policy page",
					"Cancel policy page loaded successfully", false, false);
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.appendData(testData.get("Cancellation_LegalNoticeWording"));
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancellationEffectiveDate.clearData();
			cancelPolicyPage.cancellationEffectiveDate.appendData(testData.get("CancellationEffectiveDate"));
			cancelPolicyPage.cancellationEffectiveDate.tab();
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();
			Assertions.passTest("Cancel policy page", "Clicked on next button successfully");

			// Getting earned inspection fee,policy fee and SLTF
			cancelPolicyPage.completeTransactionButton.waitTillPresenceOfElement(60);
			double earnedInspectionFee = Double.parseDouble(cancelPolicyPage.inspectionFeeEarned.getData());
			double earnedPolicyFee = Double.parseDouble(cancelPolicyPage.policyFeeEarned.getData().replace(",", ""));
			double earnedActualSLTF = Double
					.parseDouble(cancelPolicyPage.taxesAndFees.formatDynamicPath(3).getData().replace("$", ""));

			// Calculating earned FSLSO and SLTF on cancel policy page
			double earnedFSLSOCalculated = (earnedInspectionFee + earnedPolicyFee)
					* ((Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100));
			double earnedSLTFCalculated = (earnedInspectionFee + earnedPolicyFee)
					* ((Double.parseDouble(testData.get("SLTFValue")) / 100)) + earnedFSLSOCalculated
					+ Double.parseDouble(testData.get("EMPASurcharge"));

			// Verifying Earned actual sltf and earned calculated stlf both are same
			Assertions.addInfo("Scenario 04", "Verifying earned actual sltf and earned calculated sltf bothe are same");
			if (Precision.round(
					Math.abs(Precision.round(earnedActualSLTF, 2) - Precision.round(earnedSLTFCalculated, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel policy page",
						"The Calculated and Actual SLTF value are the same for FL percentage, the calculated surplus lines taxes and fees : "
								+ "$" + Precision.round(earnedSLTFCalculated, 0));
				Assertions.passTest("Cancel policy page",
						"Actual surplus lines taxes and fees : " + "$" + earnedActualSLTF);

			} else {
				Assertions.verify(earnedActualSLTF, earnedSLTFCalculated, "Cancel policy page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			// IO-20921 ended

			// click on home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC026 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC026 ", "Executed Successfully");
			}
		}
	}
}
