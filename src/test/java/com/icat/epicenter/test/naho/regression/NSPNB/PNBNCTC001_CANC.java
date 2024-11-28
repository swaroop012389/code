package com.icat.epicenter.test.naho.regression.NSPNB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBNCTC001_CANC extends AbstractNAHOTest {

	public PNBNCTC001_CANC() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/NC001.xls";
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
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			accountOverviewPage.quoteSpecifics.checkIfElementIsDisplayed();
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();

			String vieParticipationValue = accountOverviewPage.vieParticipationValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");
			String vieContributionChargeValue = accountOverviewPage.vieContributionChargeValue.getData()
					.replace("$", "").replace(",", "").replace("%", "");

			double d_vieParticipationValue = Double.parseDouble(vieParticipationValue);
			double d_vieContributionChargeValue = Double.parseDouble(vieContributionChargeValue);
			double d_totalVIE = d_vieContributionChargeValue / d_vieParticipationValue;

			// Binding the quote and creating the policy
			// accountOverviewPage.requestBind.scrollToElement();
			// accountOverviewPage.requestBind.click();
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
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			// click on cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();

			Assertions.addInfo("Scenario 01", "Asserting NOC Mailing Dates for USM");
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
					} else if (cancelPolicyPage.cancelReasonData.getData()
							.contains("ICAT Request - Non payment of premium")
							|| cancelPolicyPage.cancelReasonData.getData().contains("Material misrepresentation")) {
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

			// added ticket IO-20883
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();

			String termTotalsurplusContributionValue = policySummaryPage.surplusContributionValue.formatDynamicPath(3)
					.getData().replace("$", "").replace(",", "").replace("%", "");
			String transactionsurplusContributionValue = policySummaryPage.surplusContributionValue.formatDynamicPath(1)
					.getData().replace("$", "").replace(",", "").replace("%", "");

			Assertions.addInfo("Scenario 03",
					"Verifying surplusContibutionValue in the Term total and Transaction column on the NB policy summary page");
			Assertions.verify(
					policySummaryPage.surplusContributionValue.formatDynamicPath(3).getData()
							.equals(policySummaryPage.surplusContributionValue.formatDynamicPath(1).getData()),
					true, "Policy Summary Page",
					"Term total surplusContributionValue for New Business is same as Transaction surplusContributionValue on the NB Policy Summary Page",
					false, false);

			Assertions.passTest("New Business Policy Page",
					"The Term Total surplusContributionValue: " + termTotalsurplusContributionValue);
			Assertions.passTest("New Business Policy Page",
					"The Transaction surplusContributionValue: " + transactionsurplusContributionValue);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Performing flat Cancellation
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();

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

			String returnedsurplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(3)
					.getData().replace("$", "").replace(",", "");
			String originalsurplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(1)
					.getData().replace("$", "").replace(",", "");
			double d_originalsurplusContributionValue = Double.parseDouble(originalsurplusContributionValue);

			String orginialpremium = cancelPolicyPage.premium.formatDynamicPath(1).getData().replace("$", "")
					.replace(",", "");
			double d_originalpremium = Double.parseDouble(orginialpremium);

			// Calculating Surplus Contribution Value
			double cal_surplusContributionValue = d_originalpremium * d_totalVIE;

			Assertions.addInfo("Scenario 04",
					"Verifying the actual and calculated Surplus Contribution Value on Flat Cancel Policy Page ");
			if (d_originalsurplusContributionValue == cal_surplusContributionValue) {
				if (originalsurplusContributionValue != null) {
					Assertions.passTest("Cancel Policy Page",
							"Actual Surplus Contribution Value : " + d_originalsurplusContributionValue);
					Assertions.passTest("Cancel Policy Page",
							"Calculated Surplus Contribution Value : " + (cal_surplusContributionValue));
				}
			} else {
				Assertions.verify(cal_surplusContributionValue, d_originalsurplusContributionValue,
						"Cancel Policy Page",
						"The Difference observed between Actual and Calculated Surplus Contribution Value", false,
						false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			Assertions.addInfo("Scenario 05",
					"Verifying surplusContibutionValue in the Returned and Original column on the Cancel type page");
			Assertions.verify(
					cancelPolicyPage.surplusContributionVal.formatDynamicPath(3).getData().replace("-", "")
							.equals(cancelPolicyPage.surplusContributionVal.formatDynamicPath(1).getData()),
					true, "Cancel Policy Page",
					"Returned surplusContributionValue for Flat Cancel is same as Original surplusContributionValue on the Cancel Type page",
					false, false);

			Assertions.passTest("Cancel Policy Page",
					"The Returned surplusContributionValue: " + returnedsurplusContributionValue);
			Assertions.passTest("Cancel Policy Page",
					"The Original surplusContributionValue: " + originalsurplusContributionValue);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();

			// ------ IO-20883 Ended-----

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBNCTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBNCTC001 ", "Executed Successfully");
			}
		}
	}
}
