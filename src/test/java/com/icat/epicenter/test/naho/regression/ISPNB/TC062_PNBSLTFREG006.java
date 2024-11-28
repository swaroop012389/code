package com.icat.epicenter.test.naho.regression.ISPNB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CancelPolicySuccessfulPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC062_PNBSLTFREG006 extends AbstractNAHOTest {

	public TC062_PNBSLTFREG006() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBSLTFREG006.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		CancelPolicySuccessfulPage cancelPolicySuccessfulPage = new CancelPolicySuccessfulPage();
		ReinstatePolicyPage reinstatePolicyPage = new ReinstatePolicyPage();
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DecimalFormat df = new DecimalFormat();

		String quoteNumber;
		String policyNumber;
		String premiumAmount;
		double surplusTax;
		String fees;
		String inspectionFee;
		String policyFee;
		String surplusContributionValue;
		BigDecimal surplustaxes;

		String earnedpremiumAmount;
		String earnedinspectionFee;
		String earnedpolicyFee;
		String earnedTotal;
		String earnedsurplusContributionValue;
		BigDecimal earnedsurplustaxes;

		String returnedContributionValue;
		String returnedpremiumAmount;
		String returnedinspectionFee;
		String returnedpolicyFee;
		String returnedTotal;
		BigDecimal returnedsurplustaxes;

		String sltf;
		String totalPremium;

		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting the quote number from account overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Getting the premium,fees and sltf values from premium section in Account
			// Overview page
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Value is : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Fees Value is : " + accountOverviewPage.feesValue.getData(), false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// getting premium amount
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// getting fees value
			fees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");

			// getting surplus contribution value
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");

			// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
			// percentage 0.04
			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees)
					+ Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(accountOverviewPage.sltfValue.getData(), format.format(surplustaxes),
					"Account Overview Page", "Calculated Surplus Lines Taxes and Fees : " + format.format(surplustaxes),
					false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(accountOverviewPage.sltfValue.getData(), format.format(surplustaxes),
					"Account Overview Page",
					"Actual Surplus Lines Taxes and Fees : " + accountOverviewPage.sltfValue.getData(), false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions
					.verify(accountOverviewPage.totalPremiumValue.getData(),
							format.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxes + "")
									+ (Double.parseDouble(fees) + Double.parseDouble(surplusContributionValue)))),
							"Account Overview Page",
							"Calculated Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
									+ (Double.parseDouble(surplustaxes + "") + (Double.parseDouble(fees)))),
							false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(accountOverviewPage.totalPremiumValue.getData(),
					format.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxes + "")
							+ (Double.parseDouble(fees) + Double.parseDouble(surplusContributionValue)))),
					"Account Overview Page",
					"Actual Premium, Taxes and Fees : " + accountOverviewPage.totalPremiumValue.getData(), false,
					false);

			// clicking on Request Bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Request Bind Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering Details in Request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			if (bindRequestPage.pageName.getData().equalsIgnoreCase("Bind Request Submitted")) {

				// clicking on Home page button in Bind Request Submitted Page
				Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
						"Bind Request Page loaded successfully", false, false);
				bindRequestPage.clickOnHomepagebutton();
				Assertions.passTest("Home Page", "Clicked on Quote Number");

				// Searching for Quote Number in User Referrals Table
				Assertions.verify(homePage.goToHomepage.checkIfElementIsDisplayed(), true, "Bind Request Page",
						"Home Page loaded successfully", false, false);
				referralPage = homePage.searchReferral(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");

				// Clicking on Approve/Decline Request button in Referrals page
				Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true,
						"Referral Page", "Referral page loaded successfully", false, false);
				requestBindPage = referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// Clicking on Approve in Request Bind Page
				policySummaryPage = requestBindPage.approveRequestNAHO(testData);
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			}

			// Getting the Policy Number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary", "Policy Number is : " + policyNumber);

			premiumAmount = policySummaryPage.transactionPremium.getData().replaceAll("[^\\d-.]", "");
			inspectionFee = policySummaryPage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			policyFee = policySummaryPage.policyFee.getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = policySummaryPage.transactionSurplusContribution.getData().replace("$", "")
					.replace(",", "").replace("%", "");

			// Getting the premium,fees and sltf values from premium section in Policy
			// summary page
			Assertions.verify(policySummaryPage.transactionPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Premium Value is : " + policySummaryPage.transactionPremium.getData(),
					false, false);
			Assertions.verify(policySummaryPage.inspectionFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Inspection Fee Value is : " + policySummaryPage.inspectionFee.getData(), false, false);
			Assertions.verify(policySummaryPage.policyFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Fee Value is : " + policySummaryPage.policyFee.getData(), false, false);
			Assertions.verify(policySummaryPage.transactionSurplusContribution.checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"Surplus Contribution Value is : " + policySummaryPage.transactionSurplusContribution.getData(),
					false, false);

			// calculating surplus tax
			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(2).getData(),
					format.format(surplustaxes), "Policy Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(surplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(2).getData(),
					format.format(surplustaxes), "Policy Summary Page", "Actual Surplus Lines Taxes and Fees : "
							+ policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			String actualTotalPremium = policySummaryPage.policyTotalPremium.getData().replace("$", "").replace(",",
					"");
			double calcTotalPremium = (Double.parseDouble(premiumAmount))
					+ (Double.parseDouble(surplustaxes + "") + (Double.parseDouble(inspectionFee))
							+ (Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue)));
			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(actualTotalPremium), 2) - Precision.round(calcTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Calculated Premium, Taxes and Fees : " + "$" + Precision.round(calcTotalPremium, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual Premium, Taxes and Fees: " + "$" + actualTotalPremium);
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between actual and calculated total premium is more than 0.05");
			}

			// click on cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Cancellation Page", "Cancellation Page loaded successfully");

			// Entering cancellation details
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.waitTime(3);
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
			cancelPolicyPage.cancellationEffectiveDate.tab();
			if (!testData.get("Cancellation_LegalNoticeWording").equals("")) {
				cancelPolicyPage.legalNoticeWording.scrollToElement();
				cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			}
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// calculating sltf values for Earned and Returned before overriding Inspection
			// and Policy Fee
			Assertions.addInfo("SLTF Verification before Overriding Fees on Earned Column", "");

			// Getting data from Original,Earned and Returned columns and verifying sltf and
			// total premium
			for (int i = 1; i <= 3; i++) {
				premiumAmount = cancelPolicyPage.premium.formatDynamicPath(i).getData().replaceAll("[^\\d-.]", "");
				inspectionFee = cancelPolicyPage.inspectionFee1.formatDynamicPath(i).getData().replaceAll("[^\\d-.]",
						"");
				policyFee = cancelPolicyPage.policyFeeNAHO.formatDynamicPath(i).getData().replaceAll("[^\\d-.]", "");
				surplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).getData()
						.replace("$", "").replace(",", "").replace("%", "");
				if (i == 1) {
					Assertions.addInfo("Original Premium", "");
					// Getting data from Original and verifying sltf and total premium
					Assertions.verify(cancelPolicyPage.premium.formatDynamicPath(i).checkIfElementIsDisplayed(), true,
							"Cancel Policy Page", "Premium Value of Original Premium is : "
									+ cancelPolicyPage.premium.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(cancelPolicyPage.inspectionFee1.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page", "Inspection Fee Value of Original Premium is : "
									+ cancelPolicyPage.inspectionFee1.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(cancelPolicyPage.policyFeeNAHO.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page", "Policy Fee Value of Original Premium is : "
									+ cancelPolicyPage.policyFeeNAHO.formatDynamicPath(i).getData(),
							false, false);

					surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
							+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
							* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

					// Rounding sltf decimal value to 2 digits
					surplustaxes = BigDecimal.valueOf(surplusTax);
					surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

					// Comparing actual and expected SLTF value and printing calculated value
					String actualSltf = cancelPolicyPage.SLTF.formatDynamicPath(i).getData().replace("$", "")
							.replace(",", "");
					if (Precision.round(Math
							.abs(Precision.round(Double.parseDouble(actualSltf), 2) - Precision.round(surplusTax, 2)),
							2) < 0.05) {
						Assertions.passTest("Cancel Policy Page",
								"Calculated Surplus Lines Taxes and Fees of Original Premium : " + "$"
										+ Precision.round(surplusTax, 2));
						Assertions.passTest("Cancel Policy Page",
								"Actual Surplus Lines Taxes and Fees of Original Premium: " + "$" + actualSltf);
					} else {
						Assertions.passTest("Cancel Policy Page",
								"The Difference between actual and calculated SLTF is more than 0.05");
					}

					// Comparing actual and expected total premium and fees value and printing
					// calculated value
					String actualTotalPremiumTaxesandFees = cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i)
							.getData().replace("$", "").replace(",", "");
					double calcTotalPremiumTaxesandFees = (Double.parseDouble(premiumAmount)
							+ Double.parseDouble(surplustaxes + "") + Double.parseDouble(inspectionFee)
							+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue));
					if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualTotalPremiumTaxesandFees), 2)
							- Precision.round(calcTotalPremiumTaxesandFees, 2)), 2) < 0.05) {
						Assertions.passTest("Cancel Policy Page", "Calculated Premium, Taxes and Fees : " + "$"
								+ Precision.round(calcTotalPremiumTaxesandFees, 2));
						Assertions.passTest("Cancel Policy Page",
								"Actual Premium, Taxes and Fees: " + "$" + actualTotalPremiumTaxesandFees);
					} else {
						Assertions.passTest("Cancel Policy Page",
								"The Difference between actual and calculated total premium is more than 0.05");
					}
				}

				if (i == 2) {
					// Getting data from Earned columns and verifying sltf and total premium
					Assertions.addInfo("Earned Premium", "");
					inspectionFee = cancelPolicyPage.newInspectionFee.getData().replaceAll("[^\\d-.]", "");
					policyFee = cancelPolicyPage.newPolicyFee.formatDynamicPath(i).getData().replaceAll("[^\\d-.]", "");
					surplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).getData()
							.replace("$", "").replace(",", "").replace("%", "");

					Assertions.verify(cancelPolicyPage.premium.formatDynamicPath(i).checkIfElementIsDisplayed(), true,
							"Cancel Policy Page", "Premium Value for Earned Premium is : "
									+ cancelPolicyPage.premium.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(cancelPolicyPage.inspectionFee1.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page", "Inspection Fee Value for Earned Premium is : " + "$"
									+ cancelPolicyPage.newInspectionFee.getData(),
							false, false);
					Assertions.verify(cancelPolicyPage.policyFeeNAHO.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page",
							"Policy Fee Value for Earned Premium is : " + "$" + cancelPolicyPage.newPolicyFee.getData(),
							false, false);
					Assertions.verify(
							cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page",
							"Surplus Contribution Value for Earned Premium is : " + "$"
									+ cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).getData(),
							false, false);
					surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
							+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
							* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

					// Rounding sltf decimal value to 2 digits
					surplustaxes = BigDecimal.valueOf(surplusTax);
					surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

					// Comparing actual and expected SLTF value and printing calculated value
					Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(i).getData(), format.format(surplustaxes),
							"Cancel Policy Page", "Calculated Surplus Lines Taxes and Fees for Earned Premium : "
									+ format.format(surplustaxes),
							false, false);

					// Comparing actual and expected SLTF value and printing actual value
					Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(i).getData(), format.format(surplustaxes),
							"Cancel Policy Page", "Actual Surplus Lines Taxes and Fees for Earned Premium: "
									+ cancelPolicyPage.SLTF.formatDynamicPath(i).getData(),
							false, false);

					// Comparing actual and expected total premium and fees value and printing
					// calculated value
					Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData(),
							format.format(Double.parseDouble(premiumAmount) + Double.parseDouble(surplustaxes + "")
									+ Double.parseDouble(inspectionFee)
									+ (Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))),
							"Cancel Policy Page",
							"Calculated Premium, Taxes and Fees for Earned Premium : "
									+ format.format(
											(Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxes + "")
													+ (Double.parseDouble(inspectionFee))
													+ (Double.parseDouble(policyFee)
															+ Double.parseDouble(surplusContributionValue)))),
							false, false);

					// Comparing actual and expected total premium and fees value and printing
					// actual value
					Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData(),
							format.format(Double.parseDouble(premiumAmount) + Double.parseDouble(surplustaxes + "")
									+ Double.parseDouble(inspectionFee)
									+ (Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))),
							"Cancel Policy Page",
							"Actual Premium, Taxes and Fees for Earned Premium : "
									+ cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData(),
							false, false);
				}

				if (i == 3) {
					// Getting data from Returned columns and verifying sltf and total premium
					Assertions.addInfo("Returned Premium", "");
					Assertions.verify(cancelPolicyPage.premium.formatDynamicPath(i).checkIfElementIsDisplayed(), true,
							"Cancel Policy Page", "Premium Value for Returned Premium is : "
									+ cancelPolicyPage.premium.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(cancelPolicyPage.inspectionFee1.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page", "Inspection Fee Value for Returned Premium is : "
									+ cancelPolicyPage.inspectionFee1.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(cancelPolicyPage.policyFeeNAHO.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page", "Policy Fee Value for Returned Premium is : "
									+ cancelPolicyPage.policyFeeNAHO.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(
							cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page",
							"Policy Fee Value for Returned Premium is : "
									+ cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).getData(),
							false, false);

					surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
							+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
							* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

					// Rounding sltf decimal value to 2 digits
					surplustaxes = BigDecimal.valueOf(surplusTax);
					surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);
					sltf = surplustaxes.toString();

					totalPremium = Double.parseDouble(premiumAmount) + Double.parseDouble(surplustaxes + "")
							+ Double.parseDouble(inspectionFee) + Double.parseDouble(policyFee)
							+ Double.parseDouble(surplusContributionValue) + "".replace(",", "");

					// Comparing actual and expected SLTF value and printing calculated value
					Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(i).getData(),
							format.format(Double.parseDouble(sltf)).replace("(", "-").replace(")", "").toString(),
							"Cancel Policy Page",
							"Calculated Surplus Lines Taxes and Fees for Returned Premium : " + format
									.format(Double.parseDouble(sltf)).replace("(", "-").replace(")", "").toString(),
							false, false);

					// Comparing actual and expected SLTF value and printing actual value
					Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(i).getData(),
							format.format(Double.parseDouble(sltf)).replace("(", "-").replace(")", "").toString(),
							"Cancel Policy Page", "Actual Surplus Lines Taxes and Fees for Returned Premium : "
									+ cancelPolicyPage.SLTF.formatDynamicPath(i).getData(),
							false, false);

					// Comparing actual and expected total premium and fees value and printing
					// calculated value
					Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData(),
							format.format(Double.parseDouble(totalPremium)).replace("(", "-").replace(")", "")
									.replace(",", "").toString(),
							"Cancel Policy Page",
							"Calculated Premium, Taxes and Fees for Returned Premium : "
									+ format.format(Double.parseDouble(totalPremium)).replace("(", "-").replace(")", "")
											.replace(",", "").toString(),
							false, false);

					// Comparing actual and expected total premium and fees value and printing
					// actual value
					Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData(),
							format.format(Double.parseDouble(totalPremium)).replace("(", "-").replace(")", "")
									.replace(",", "").toString(),
							"Cancel Policy Page",
							"Actual Premium, Taxes and Fees for Returned Premium : "
									+ cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData(),
							false, false);
				}
			}
			// overriding Inspection and Policy Fee on Fee column to 200
			cancelPolicyPage.newInspectionFee.waitTillVisibilityOfElement(60);
			cancelPolicyPage.newInspectionFee.setData(testData.get("InspectionFeeOverride"));
			cancelPolicyPage.waitTime(3);

			cancelPolicyPage.newPolicyFee.waitTillVisibilityOfElement(60);
			cancelPolicyPage.newPolicyFee.setData(testData.get("PolicyFeeOverride"));
			cancelPolicyPage.waitTime(3);

			// calculating sltf values for Earned and Returned after overriding Inspection
			// and Policy Fee
			Assertions.addInfo("SLTF Verification after Overriding Fees on Earned Column", "");

			for (int i = 1; i <= 3; i++) {
				// Getting data from Original,Earned and Returned columns and verifying sltf and
				// total premium
				premiumAmount = cancelPolicyPage.premium.formatDynamicPath(i).getData().replaceAll("[^\\d-.]", "");
				inspectionFee = cancelPolicyPage.inspectionFee1.formatDynamicPath(i).getData().replaceAll("[^\\d-.]",
						"");
				policyFee = cancelPolicyPage.policyFeeNAHO.formatDynamicPath(i).getData().replaceAll("[^\\d-.]", "");
				surplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).getData()
						.replace("$", "").replace(",", "").replace("%", "");
				if (i == 1) {
					// Getting data from Original column and verifying sltf and total premium
					Assertions.addInfo("Original Premium", "");
					Assertions.verify(cancelPolicyPage.premium.formatDynamicPath(i).checkIfElementIsDisplayed(), true,
							"Cancel Policy Page", "Premium Value of Original Premium is : "
									+ cancelPolicyPage.premium.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(cancelPolicyPage.inspectionFee1.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page", "Inspection Fee Value of Original Premium is : "
									+ cancelPolicyPage.inspectionFee1.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(cancelPolicyPage.policyFeeNAHO.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page", "Policy Fee Value of Original Premium is : "
									+ cancelPolicyPage.policyFeeNAHO.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(
							cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page",
							"Surplus Contribution Value of Original Premium is : "
									+ cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).getData(),
							false, false);

					surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
							+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
							* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

					// Rounding sltf decimal value to 2 digits
					surplustaxes = BigDecimal.valueOf(surplusTax);
					surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

					// Comparing actual and expected SLTF value and printing calculated value
					Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(i).getData(), format.format(surplustaxes),
							"Cancel Policy Page", "Calculated Surplus Lines Taxes and Fees of Original Premium : "
									+ format.format(surplustaxes),
							false, false);

					// Comparing actual and expected SLTF value and printing actual value
					Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(i).getData(), format.format(surplustaxes),
							"Cancel Policy Page", "Actual Surplus Lines Taxes and Fees of Original Premium: "
									+ cancelPolicyPage.SLTF.formatDynamicPath(i).getData(),
							false, false);

					// Comparing actual and expected total premium and fees value and printing
					// calculated value

					String actualTotPremium = cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData()
							.replace("$", "").replace(",", "");
					double calcuTotalPremium = (Double.parseDouble(premiumAmount)
							+ Double.parseDouble(surplustaxes + "") + Double.parseDouble(inspectionFee)
							+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue));
					if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualTotPremium), 2)
							- Precision.round(calcuTotalPremium, 2)), 2) < 0.05) {
						Assertions.passTest("Cancel Policy Page",
								"Calculated Premium, Taxes and Fees of Original Premium : " + "$"
										+ Precision.round(calcuTotalPremium, 2));
						Assertions.passTest("Cancel Policy Page",
								"Actual Premium, Taxes and Fees of Original Premium : " + "$" + actualTotPremium);
					} else {
						Assertions.passTest("Cancel Policy Page",
								"The Difference between actual and calculated Total Premium is more than 0.05");
					}
				}
				if (i == 2) {
					// Getting data from Earned column and verifying sltf and total premium
					Assertions.addInfo("Earned Premium", "");
					inspectionFee = cancelPolicyPage.newInspectionFee.getData().replaceAll("[^\\d-.]", "");
					policyFee = cancelPolicyPage.newPolicyFee.formatDynamicPath(i).getData().replaceAll("[^\\d-.]", "");
					surplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).getData()
							.replace("$", "").replace(",", "").replace("%", "");
					Assertions.verify(cancelPolicyPage.premium.formatDynamicPath(i).checkIfElementIsDisplayed(), true,
							"Cancel Policy Page", "Premium Value for Earned Premium is : "
									+ cancelPolicyPage.premium.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(cancelPolicyPage.newInspectionFee.checkIfElementIsDisplayed(), true,
							"Cancel Policy Page", "Inspection Fee Value for Earned Premium is : " + "$"
									+ cancelPolicyPage.newInspectionFee.getData(),
							false, false);
					Assertions.verify(cancelPolicyPage.newPolicyFee.checkIfElementIsDisplayed(), true,
							"Cancel Policy Page",
							"Policy Fee Value for Earned Premium is : " + "$" + cancelPolicyPage.newPolicyFee.getData(),
							false, false);
					Assertions.verify(
							cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page",
							"Surplus Contribution Value for Earned Premium is : " + "$"
									+ cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).getData(),
							false, false);

					surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
							+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
							* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

					// Rounding sltf decimal value to 2 digits
					surplustaxes = BigDecimal.valueOf(surplusTax);
					surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

					// Comparing actual and expected SLTF value and printing calculated value
					Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(i).getData(), format.format(surplustaxes),
							"Cancel Policy Page", "Calculated Surplus Lines Taxes and Fees for Earned Premium : "
									+ format.format(surplustaxes),
							false, false);

					// Comparing actual and expected SLTF value and printing actual value
					Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(i).getData(), format.format(surplustaxes),
							"Cancel Policy Page", "Actual Surplus Lines Taxes and Fees for Earned Premium: "
									+ cancelPolicyPage.SLTF.formatDynamicPath(i).getData(),
							false, false);

					// Comparing actual and expected total premium and fees value and printing
					// calculated value
					Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData(),
							format.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxes + "")
									+ (Double.parseDouble(inspectionFee))
									+ (Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue)))),
							"Cancel Policy Page",
							"Calculated Premium, Taxes and Fees for Earned Premium : "
									+ format.format(
											(Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxes + "")
													+ (Double.parseDouble(inspectionFee))
													+ (Double.parseDouble(policyFee)
															+ Double.parseDouble(surplusContributionValue)))),
							false, false);

					// Comparing actual and expected total premium and fees value and printing
					// actual value
					Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData(),
							format.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxes + "")
									+ (Double.parseDouble(inspectionFee))
									+ (Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue)))),
							"Cancel Policy Page",
							"Actual Premium, Taxes and Fees for Earned Premium : "
									+ cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData(),
							false, false);
				}

				if (i == 3) {
					// Getting data from Returned columns and verifying sltf and total premium
					Assertions.addInfo("Returned Premium", "");
					Assertions.verify(cancelPolicyPage.premium.formatDynamicPath(i).checkIfElementIsDisplayed(), true,
							"Cancel Policy Page", "Premium Value for Returned Premium is : "
									+ cancelPolicyPage.premium.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(cancelPolicyPage.inspectionFee1.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page", "Inspection Fee Value for Returned Premium is : "
									+ cancelPolicyPage.inspectionFee1.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(cancelPolicyPage.policyFeeNAHO.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page", "Policy Fee Value for Returned Premium is : "
									+ cancelPolicyPage.policyFeeNAHO.formatDynamicPath(i).getData(),
							false, false);
					Assertions.verify(
							cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "Cancel Policy Page",
							"Surplus Contribution Value for Returned Premium is : "
									+ cancelPolicyPage.surplusContributionVal.formatDynamicPath(i).getData(),
							false, false);

					surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
							+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
							* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

					// Rounding sltf decimal value to 2 digits
					surplustaxes = BigDecimal.valueOf(surplusTax);
					surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);
					sltf = surplustaxes.toString();

					totalPremium = Double.parseDouble(premiumAmount) + Double.parseDouble(surplustaxes + "")
							+ Double.parseDouble(inspectionFee) + Double.parseDouble(policyFee)
							+ Double.parseDouble(surplusContributionValue) + "".replaceAll("[^\\d-.]", "");

					// Comparing actual and expected SLTF value and printing calculated value
					Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(i).getData(),
							format.format(Double.parseDouble(sltf)).replace("(", "-").replace(")", "").toString(),
							"Cancel Policy Page",
							"Calculated Surplus Lines Taxes and Fees for Returned Premium : " + format
									.format(Double.parseDouble(sltf)).replace("(", "-").replace(")", "").toString(),
							false, false);

					// Comparing actual and expected SLTF value and printing actual value
					Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(i).getData(),
							format.format(Double.parseDouble(sltf)).replace("(", "-").replace(")", "").replace(",", "")
									.toString(),
							"Cancel Policy Page", "Actual Surplus Lines Taxes and Fees for Returned Premium : "
									+ cancelPolicyPage.SLTF.formatDynamicPath(i).getData(),
							false, false);

					// Comparing actual and expected total premium and fees value and printing
					// calculated value
					Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData(),
							format.format(Double.parseDouble(totalPremium)).replace("(", "-").replace(")", "")
									.replace(",", "").toString(),
							"Cancel Policy Page",
							"Calculated Premium, Taxes and Fees for Returned Premium : " + format
									.format(Double.parseDouble(totalPremium)).replace("(", "-").replace(")", ""),
							false, false);

					// Comparing actual and expected total premium and fees value and printing
					// actual value
					Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData(),
							format.format(Double.parseDouble(totalPremium)).replace("(", "-").replace(")", "")
									.replace(",", "").toString(),
							"Cancel Policy Page",
							"Actual Premium, Taxes and Fees for Returned Premium : "
									+ cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(i).getData(),
							false, false);
				}
			}
			// overriding Inspection and Policy Fee on Fee column with negative values
			testData = data.get(dataValue2);
			cancelPolicyPage.newInspectionFee.waitTillVisibilityOfElement(60);
			cancelPolicyPage.newInspectionFee.setData(testData.get("InspectionFeeOverride"));
			cancelPolicyPage.newInspectionFee.tab();
			cancelPolicyPage.waitTime(2);

			cancelPolicyPage.newPolicyFee.setData(testData.get("PolicyFeeOverride"));
			cancelPolicyPage.newPolicyFee.tab();
			cancelPolicyPage.waitTime(3);
			Assertions.passTest("Cancel Policy Page",
					"Inspection Fee and Policy on Earned Column overridden to neagtive values");

			// click on complete transaction button
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();

			// verifying warning Message for negative inspection and policy fee
			testData = data.get(dataValue1);
			cancelPolicyPage.waitTime(2);
			cancelPolicyPage.feesNegativeWarningMsg.formatDynamicPath("Inspection").waitTillPresenceOfElement(60);
			cancelPolicyPage.feesNegativeWarningMsg.formatDynamicPath("Inspection").waitTillVisibilityOfElement(60);
			cancelPolicyPage.waitTime(2);
			cancelPolicyPage.scrollToBottomPage();
			cancelPolicyPage.inspectionFeeField.click();
			Assertions.verify(
					cancelPolicyPage.feesNegativeWarningMsg.formatDynamicPath("Inspection").checkIfElementIsPresent(),
					true, "Cancel Policy Page",
					"Earned Inspection Fees cannot be negative or increased on cancellation is verified", false, false);
			cancelPolicyPage.policyFeeField.waitTillVisibilityOfElement(60);
			cancelPolicyPage.policyFeeField.scrollToElement();
			cancelPolicyPage.policyFeeField.waitTillButtonIsClickable(60);
			cancelPolicyPage.waitTime(2);
			cancelPolicyPage.policyFeeField.click();
			cancelPolicyPage.feesNegativeWarningMsg.formatDynamicPath("Policy").waitTillPresenceOfElement(60);
			cancelPolicyPage.feesNegativeWarningMsg.formatDynamicPath("Policy").waitTillVisibilityOfElement(60);
			Assertions.verify(
					cancelPolicyPage.feesNegativeWarningMsg.formatDynamicPath("Policy").checkIfElementIsPresent(), true,
					"Cancel Policy Page",
					"Earned Policy Fees cannot be negative or increased on cancellation is verified", false, false);

			// clear negative Inspection and Policy Fee values on Earned Fee column
			cancelPolicyPage.newInspectionFee.clearData();
			cancelPolicyPage.newInspectionFee.tab();
			cancelPolicyPage.loading.waitTillInVisibilityOfElement(60);

			cancelPolicyPage.newPolicyFee.clearData();
			cancelPolicyPage.newPolicyFee.tab();
			cancelPolicyPage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Cancel Policy Page",
					"Inspection Fee and Policy on Earned Column overridden to neagtive values is removed");

			// calculating sltf values for Earned and Returned after removing Inspection and
			// Policy Fee
			Assertions.addInfo("SLTF Verification after removing Overridden Fees on Earned Column", "");
			Assertions.addInfo("Earned Premium", "");

			// Getting data from Earned column and verifying sltf and total premium
			earnedpremiumAmount = cancelPolicyPage.premium.formatDynamicPath(2).getData().replaceAll("[^\\d-.]", "");
			earnedinspectionFee = cancelPolicyPage.newInspectionFee.getData().replaceAll("[^\\d-.]", "");
			earnedpolicyFee = cancelPolicyPage.newPolicyFee.formatDynamicPath(2).getData().replaceAll("[^\\d-.]", "");
			earnedsurplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(2).getData()
					.replace("$", "").replace(",", "").replace("%", "");

			// Getting the premium,fees,Surplus Contribution Value and sltf values from
			// premium section in cancel
			// policy page
			Assertions.verify(cancelPolicyPage.premium.formatDynamicPath(2).checkIfElementIsDisplayed(), true,
					"Cancel Policy Page",
					"Premium Value for Earned Premium is : " + cancelPolicyPage.premium.formatDynamicPath(2).getData(),
					false, false);
			Assertions.verify(cancelPolicyPage.newInspectionFee.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Inspection Fee Value for Earned Premium is : $" + cancelPolicyPage.newInspectionFee.getData(),
					false, false);
			Assertions.verify(cancelPolicyPage.newPolicyFee.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Policy Fee Value for Earned Premium is : $" + cancelPolicyPage.newPolicyFee.getData(), false,
					false);
			Assertions.verify(cancelPolicyPage.surplusContributionVal.formatDynamicPath(3).checkIfElementIsDisplayed(),
					true, "Cancel Policy Page", "Surplus Contribution Value for Earned Premium is : $"
							+ cancelPolicyPage.surplusContributionVal.formatDynamicPath(3).getData(),
					false, false);

			surplusTax = (Double.parseDouble(earnedpremiumAmount) + Double.parseDouble(earnedinspectionFee)
					+ Double.parseDouble(earnedpolicyFee) + Double.parseDouble(earnedsurplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			earnedsurplustaxes = BigDecimal.valueOf(surplusTax);
			earnedsurplustaxes = earnedsurplustaxes.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(2).getData(), "$" + earnedsurplustaxes,
					"Cancel Policy Page",
					"Calculated Surplus Lines Taxes and Fees for Earned Premium : " + format.format(earnedsurplustaxes),
					false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(2).getData(), "$" + earnedsurplustaxes,
					"Cancel Policy Page", "Actual Surplus Lines Taxes and Fees for Earned Premium: "
							+ cancelPolicyPage.SLTF.formatDynamicPath(2).getData(),
					false, false);

			earnedTotal = cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(2).getData().replace(".00", "");
			df = new DecimalFormat("0.00");

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(2).getData().replace("$", ""),
					df.format(Double.parseDouble(earnedpremiumAmount)
							+ Double.parseDouble(earnedsurplustaxes + "") + Double.parseDouble(earnedinspectionFee)
							+ Double.parseDouble(earnedpolicyFee) + Double.parseDouble(earnedsurplusContributionValue)),
					"Cancel Policy Page",
					"Calculated Premium, Taxes and Fees for Earned Premium : "
							+ df.format((Double.parseDouble(earnedpremiumAmount))
									+ (Double.parseDouble(earnedsurplustaxes + "")
											+ (Double.parseDouble(earnedinspectionFee))
											+ (Double.parseDouble(earnedpolicyFee)
													+ Double.parseDouble(earnedsurplusContributionValue)))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(2).getData().replace("$", ""),
					df.format(Double.parseDouble(earnedpremiumAmount) + Double.parseDouble(earnedsurplustaxes + "")
							+ Double.parseDouble(earnedinspectionFee) + Double.parseDouble(earnedpolicyFee)
							+ Double.parseDouble(earnedsurplusContributionValue)),
					"Cancel Policy Page", "Actual Premium, Taxes and Fees for Earned Premium : "
							+ cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(2).getData(),
					false, false);

			Assertions.addInfo("Returned Premium", "");
			returnedpremiumAmount = cancelPolicyPage.premium.formatDynamicPath(3).getData().replaceAll("[^\\d-.]", "");
			returnedinspectionFee = cancelPolicyPage.inspectionFee1.formatDynamicPath(3).getData()
					.replaceAll("[^\\d-.]", "");
			returnedpolicyFee = cancelPolicyPage.policyFeeNAHO.formatDynamicPath(3).getData().replaceAll("[^\\d-.]",
					"");
			returnedContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(3).getData()
					.replace("$", "").replace(",", "").replace("%", "");

			// Getting data from Returned column and verifying sltf and total premium
			Assertions.verify(cancelPolicyPage.premium.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"Cancel Policy Page", "Premium Value for Returned Premium is : "
							+ cancelPolicyPage.premium.formatDynamicPath(3).getData(),
					false, false);
			Assertions.verify(cancelPolicyPage.inspectionFee1.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"Cancel Policy Page", "Inspection Fee Value for Returned Premium is : "
							+ cancelPolicyPage.inspectionFee1.formatDynamicPath(3).getData(),
					false, false);
			Assertions.verify(cancelPolicyPage.policyFeeNAHO.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"Cancel Policy Page", "Policy Fee Value for Returned Premium is : "
							+ cancelPolicyPage.policyFeeNAHO.formatDynamicPath(3).getData(),
					false, false);
			Assertions.verify(cancelPolicyPage.surplusContributionVal.formatDynamicPath(3).checkIfElementIsDisplayed(),
					true, "Cancel Policy Page", "Surplus Contribution Value for Returned Premium is : "
							+ cancelPolicyPage.surplusContributionVal.formatDynamicPath(3).getData(),
					false, false);

			surplusTax = (Double.parseDouble(returnedpremiumAmount) + Double.parseDouble(returnedinspectionFee)
					+ Double.parseDouble(returnedpolicyFee) + Double.parseDouble(returnedContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			returnedsurplustaxes = BigDecimal.valueOf(surplusTax);
			returnedsurplustaxes = returnedsurplustaxes.setScale(2, RoundingMode.HALF_UP);

			totalPremium = Double.parseDouble(returnedpremiumAmount) + Double.parseDouble(returnedsurplustaxes + "")
					+ Double.parseDouble(returnedinspectionFee) + Double.parseDouble(returnedpolicyFee)
					+ Double.parseDouble(returnedContributionValue) + "".replace(",", "");

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(3).getData(),
					format.format(Double.parseDouble(returnedsurplustaxes + "")).replace("(", "-").replace(")", "")
							.toString(),
					"Cancel Policy Page",
					"Calculated Surplus Lines Taxes and Fees for Returned Premium : " + format
							.format(Double.parseDouble(returnedsurplustaxes + "")).replace("(", "-").replace(")", ""),
					false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(cancelPolicyPage.SLTF.formatDynamicPath(3).getData(),
					format.format(Double.parseDouble(returnedsurplustaxes + "")).replace("(", "-").replace(")", "")
							.toString(),
					"Cancel Policy Page", "Actual Surplus Lines Taxes and Fees for Returned Premium : "
							+ cancelPolicyPage.SLTF.formatDynamicPath(3).getData(),
					false, false);

			returnedTotal = cancelPolicyPage.premium.formatDynamicPath(3).getData();

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(3).getData(),
					format.format(Double.parseDouble(totalPremium + "")).replace("(", "-").replace(")", "")
							.replace(",", "").toString(),
					"Cancel Policy Page",
					"Calculated Premium, Taxes and Fees for Returned Premium : "
							+ format.format(Double.parseDouble(totalPremium + "")).replace("(", "-").replace(")", "")
									.replace(",", ""),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(3).getData(),
					format.format(Double.parseDouble(totalPremium + "")).replace("(", "-").replace(")", "")
							.replace(",", "").toString(),
					"Cancel Policy Page", "Actual Premium, Taxes and Fees for Returned Premium : "
							+ cancelPolicyPage.totalPremiumTaxesFees.formatDynamicPath(3).getData(),
					false, false);

			// click on complete transaction button
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();

			// Cancellation Successful page
			Assertions.verify(cancelPolicySuccessfulPage.newPremium.checkIfElementIsDisplayed(), true,
					"Cancellation Successful Page", "Cancellation Successful Page loaded successfully", false, false);

			// Getting the premium,fees and sltf values from premium section in cancellation
			// successful page
			Assertions.verify(cancelPolicySuccessfulPage.newSLTF.getData(), "$" + earnedsurplustaxes,
					"Cancellation Successful Page",
					"Earned Taxes and Fees in Cancellation Successful Page and Cancel Policy Page are same is verified ",
					false, false);
			Assertions.verify(cancelPolicySuccessfulPage.newPremium.getData(), earnedTotal,
					"Cancellation Successful Page",
					"Earned Premium Total in Cancellation Successful Page and Cancel Policy Page are same is verified ",
					false, false);

			// Getting the premium,fees and sltf values from premium section in cancellation
			// successful page
			Assertions.verify(cancelPolicySuccessfulPage.returnedSLTF.getData(),
					returnedsurplustaxes.toString().replace("-", "-$"), "Cancellation Successful Page",
					"Returned Taxes and Fees in Cancellation Successful Page and Cancel Policy Page are same is verified",
					false, false);
			Assertions.verify(cancelPolicySuccessfulPage.returnedPremium.getData(),
					returnedTotal.toString().replace("$-", "-$"), "Cancellation Successful Page",
					"Returned Premium Total is in Cancellation Successful Page and Cancel Policy Page are same is verified",
					false, false);

			// Clicking close button
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.waitTillVisibilityOfElement(60);
			cancelPolicyPage.closeButton.waitTillElementisEnabled(60);
			cancelPolicyPage.closeButton.waitTillButtonIsClickable(60);
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancel Policy Page", "Policy cancelled successfullly");

			// reinstate policy
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			Assertions.passTest("Reinstate Policy Page", "Clicked on Reinstate Policy Link");

			reinstatePolicyPage.enterReinstatePolicyDetails(testData);
			Assertions.passTest("Reinstate Policy Page", "Policy reinstated successfullly");

			policySummaryPage.transHistReason.formatDynamicPath("4").waitTillVisibilityOfElement(60);
			policySummaryPage.transHistReason.formatDynamicPath("4").scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath("4").click();

			Assertions.addInfo("Reinstatement SLTF Verification", "");
			premiumAmount = policySummaryPage.transactionPremium.getData().replaceAll("[^\\d-.]", "");
			inspectionFee = policySummaryPage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			policyFee = policySummaryPage.policyFee.getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = policySummaryPage.transactionSurplusContribution.getData().replace("$", "")
					.replace(",", "").replace("%", "");

			// Getting the premium,fees and sltf values from premium section in Policy
			// summary page
			Assertions.verify(policySummaryPage.transactionPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Premium Value is : " + policySummaryPage.transactionPremium.getData(),
					false, false);
			Assertions.verify(policySummaryPage.inspectionFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Inspection Fee Value is : " + policySummaryPage.inspectionFee.getData(), false, false);
			Assertions.verify(policySummaryPage.policyFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Fee Value is : " + policySummaryPage.policyFee.getData(), false, false);
			Assertions.verify(policySummaryPage.transactionSurplusContribution.checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"Surplus Contribution Value is : " + policySummaryPage.transactionSurplusContribution.getData(),
					false, false);

			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(2).getData(),
					format.format(surplustaxes), "Policy Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(surplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(2).getData(),
					format.format(surplustaxes), "Policy Summary Page", "Actual Surplus Lines Taxes and Fees : "
							+ policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			String actualTotalPrem = policySummaryPage.policyTotalPremium.getData().replace("$", "").replace(",", "");
			double calcuTotalPrem = (Double.parseDouble(premiumAmount))
					+ (Double.parseDouble(surplustaxes + "") + (Double.parseDouble(inspectionFee))
							+ (Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue)));
			if (Precision.round(Math
					.abs(Precision.round(Double.parseDouble(actualTotalPrem), 2) - Precision.round(calcuTotalPrem, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page",
						"Calculated Premium, Taxes and Fees : " + "$" + Precision.round(calcuTotalPrem, 2));
				Assertions.passTest("Cancel Policy Page", "Actual Premium, Taxes and Fees : " + "$" + actualTotalPrem);
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual and calculated Total Premium is more than 0.05");
			}
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC062 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC062 ", "Executed Successfully");
			}
		}
	}
}