/** Program Description: Create a Policy.Assert all the available cancellation reasons and assert the cancellation effective for all the reasons
 *  Author			   : John
 *  Date of Creation   : 04/05/2021
 **/
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
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBNJTC001_CANC extends AbstractNAHOTest {

	public PNBNJTC001_CANC() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/NJ001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BuildingNoLongerQuoteablePage noLongerQuoteable = new BuildingNoLongerQuoteablePage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

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
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Click on view print full quote and assert water damage limitation
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.addInfo("Scenario 01",
					"Asserting Presence of Water Damage Limitation when Year Built is Prior than 40 year");
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsText.formatDynamicPath("Water Damage Limitation")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.discountsText.formatDynamicPath("Water Damage Limitation")
									.checkIfElementIsDisplayed(),
					false, "View Or Print FullQuote Page",
					"When Year Built is within 40 years then Water Damage Limitation coverage is not available", false,
					false);

			// added IO-21175
			String premValue = viewOrPrintFullQuotePage.premValue.getData();
			String insurerPolFeeVlaue = viewOrPrintFullQuotePage.insurerPolFeeVlaue.getData();
			String insurerInspectionFeeValue = viewOrPrintFullQuotePage.insurerInspectionFeeValue.getData();
			String surplusLinesTaxesValue = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData();
			String surplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData();
			String totalPremValue = viewOrPrintFullQuotePage.totalPremValue.getData();

			double d_premValue = Double.parseDouble(premValue.replace("$", ""));
			double d_insurerPolFeeVlaue = Double.parseDouble(insurerPolFeeVlaue.replace("$", ""));
			double d_insurerInspectionFeeValue = Double.parseDouble(insurerInspectionFeeValue.replace("$", ""));
			double d_surplusLinesTaxesValue = Double.parseDouble(surplusLinesTaxesValue.replace("$", ""));
			double d_surplusContributionValue = Double
					.parseDouble(surplusContributionValue.replace("$", "").replace("%", ""));
			double d_totalPremValue = Double.parseDouble(totalPremValue.replace("$", "").replace(",", ""));

			// Calculating total premium value
			double calTotalPremValue = d_premValue + d_insurerPolFeeVlaue + d_insurerInspectionFeeValue
					+ d_surplusLinesTaxesValue + d_surplusContributionValue;

			if (d_totalPremValue == calTotalPremValue) {
				if (totalPremValue != null) {
					Assertions.passTest("View/Print Full QuotePage",
							"Actual Total Premium displayed on the View/Print full quote page is: " + d_totalPremValue);
					Assertions.passTest("View/Print Full QuotePage",
							"Calculated Total Premium on the View/Print full quote page is: " + calTotalPremValue);
				}
			} else {
				Assertions.verify(calTotalPremValue, d_totalPremValue, "View/Print Full QuotePage",
						"The Difference observed between actual and calculated Total Premium on the View/Print full quote page",
						false, false);
			}
			// ------ IO-21175 Ended-----

			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();

			// Edit dwelling and update year built to 40 years prior not present
			testData = data.get(data_Value2);
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.verify(dwellingPage.createQuote.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.yearBuilt.clearData();
			dwellingPage.continueWithUpdateBtn.scrollToElement();
			dwellingPage.continueWithUpdateBtn.click();
			dwellingPage.yearBuilt.waitTillPresenceOfElement(60);
			dwellingPage.yearBuilt.waitTillVisibilityOfElement(60);
			dwellingPage.yearBuilt.appendData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();
			Assertions.passTest("Dwelling Page", "Year Built is Updated to " + testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// Click on create quote
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Clicked on create quote button successfully");

			// Click on override
			if (noLongerQuoteable.override.checkIfElementIsPresent()
					&& noLongerQuoteable.override.checkIfElementIsDisplayed()) {
				noLongerQuoteable.override.scrollToElement();
				noLongerQuoteable.override.click();
			}

			// Click on Get A Quote
			createQuotePage.getAQuote.waitTillPresenceOfElement(60);
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote page", "Quote details entered uccessfully");

			// Adding IO-20816
			// Verifying roof age warning message when year built 40 years older on dwelling
			// page and usm can override
			// Message: The account is ineligible due to the quoted building being 40 years
			// or older with no updates in the last 15 years.
			Assertions.addInfo("Scenario 02",
					"Verifying roof age warning message when year built 40 years older on dwelling page");
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath(
									"The account is ineligible due to the quoted building being 40 years or older")
							.getData().contains("building being 40 years or older"),
					true, "Create Quote Page",
					"The year built warning message is " + createQuotePage.warningMessages
							.formatDynamicPath(
									"The account is ineligible due to the quoted building being 40 years or older")
							.getData() + " displayed verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Asserting Roof age warning message when year built older 40 Years
			// Year built = 1980
			Assertions.addInfo("Scenario 03", "Verifying roof age warning message");
			Assertions.verify(createQuotePage.warningMessages.formatDynamicPath("roof age").checkIfElementIsDisplayed(),
					true, "Create quote page",
					"Account is ineligible due to an roof age warning message is '"
							+ createQuotePage.warningMessages.formatDynamicPath("roof age").getData() + "' is verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Edit dwelling and update year built to 40 years prior present
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsText.formatDynamicPath("Water Damage Limitation")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.discountsText.formatDynamicPath("Water Damage Limitation")
									.checkIfElementIsDisplayed(),
					true, "View Or Print FullQuote Page",
					"When Year Buiit is 40 years or prior then Water Damage Limitation coverage is available", false,
					false);
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();

			// Get quote number
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
			testData = data.get(data_Value1);

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");


			// Assert policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			// click on cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.addInfo("Scenario 04", "Asserting NOC Mailing Dates for USM");
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
				SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
				SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
				Date policyEffectiveDateData = sdf2.parse(policyEffectiveDate);
				Date nocMailingDateData = sdf1.parse(nocMailingDate);
				long diffInMillies = Math.abs(nocMailingDateData.getTime() - policyEffectiveDateData.getTime());
				long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				Assertions.verify(String.valueOf(diff), "90", "Cancel Policy Page",
						"Difference between policy effective date and NOC Mailing date is " + diff, false, false);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Asserting cancellation dates for each reason
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.addInfo("Scenario 05",
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

					if (cancelPolicyPage.cancelReasonData.getData().contains("Insured's Request")
							|| cancelPolicyPage.cancelReasonData.getData()
									.contains("Insured's Request - Duplicate coverage")
							|| cancelPolicyPage.cancelReasonData.getData().contains("Cancel/Rewrite")
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

					} else {
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
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Added code for IO-19096
			// Processing Flat Cancel
			cancelPolicyPage.refreshPage();
			testData = data.get(12);
			cancelPolicyPage.enterCancellationDetails(testData);
			Assertions.verify(policySummaryPage.policyStatus.getData(), "Cancelled", "Policy Summary Page",
					"Flat Cancel Processed successfully", false, false);

			// Processing Reinstate
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);
			Assertions.verify(policySummaryPage.policyStatus.getData(), "Active", "Policy Summary Page",
					"Reinstate Processed successfully", false, false);

			// Processing Non Flat Cancel
			Assertions.addInfo("Scenario 06", "Asserting SLTF calculation on a pro rata cancel");
			testData = data.get(13);
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			cancelPolicyPage.enterCancellationDetails(testData);
			Assertions.verify(policySummaryPage.policyStatus.getData(), "NOC", "Policy Summary Page",
					"Non Flat Cancel Processed successfully", false, false);

			// Calculate transaction SLTF for non flat cancel
			policySummaryPage.transHistReason.formatDynamicPath("5").scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath("5").click();
			double transactionPremiumActual = Double.parseDouble(
					policySummaryPage.transactionPremium.getData().replace("-", "").replace("$", "").replace(",", ""));
			double transactionSurplusContributionValue = Double
					.parseDouble(policySummaryPage.transactionSurplusContribution.getData().replace("-", "")
							.replace("$", "").replace("%", ""));
			String sltfValueAcutal = policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData().replace("-", "")
					.replace("$", "").replace(",", "");
			double sltfPercentageValue = Double.parseDouble(testData.get("SLTFPercentage"));
			double sltfValueCalculated = (transactionPremiumActual + transactionSurplusContributionValue)
					* sltfPercentageValue;
			double d_sltfValueAcutal = Double.parseDouble(sltfValueAcutal);

			if (Precision.round(
					Math.abs(Precision.round(d_sltfValueAcutal, 2) - Precision.round(sltfValueCalculated, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Calculated surplus lines taxes and fees are matching for Non Flat Cancel transaction: " + "$"
								+ Precision.round(sltfValueCalculated, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual surplus lines taxes and fees are matching for Non Flat Cancel transaction: " + "$"
								+ d_sltfValueAcutal);
			} else {
				Assertions.verify(d_sltfValueAcutal, sltfValueCalculated, "Policy Summary Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05", false,
						false);
			}

			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBNJTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBNJTC001 ", "Executed Successfully");
			}
		}

	}
}
