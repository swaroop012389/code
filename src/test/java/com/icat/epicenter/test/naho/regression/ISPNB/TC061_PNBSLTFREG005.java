package com.icat.epicenter.test.naho.regression.ISPNB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC061_PNBSLTFREG005 extends AbstractNAHOTest {

	public TC061_PNBSLTFREG005() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBSLTFREG005.xls";
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
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();

		String quoteNumber;
		String policyNumber;

		String premium;
		String inspectionFee;
		String policyFee;
		String surplusLinesTaxesPercentage;
		String premiumAmount;
		String surplusContributionValue;
		double surplusTax;
		double d_surplusContributionValue;

		double d_premium;
		double d_surplusLinesTaxesPercentage;
		double d_inspectionFee;
		double d_policyFee;
		double d_SLTFValue;
		BigDecimal SLTFRoundOff;

		String actualSLTFValue;
		String calculatedSLTFValue;

		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		Map<String, String> testData = data.get(dataValue1);
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
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

			// Verify SLTF label is displayed in account overview Page
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees Label is displayed", false, false);

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
			// Entering Details in Request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);

			// clicking on Home page button in Bind Request Submitted Page
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();

			Assertions.passTest("Home Page", "Clicked on Quote Number");

			// Searching for Quote Number in User Referrals Table
			Assertions.verify(homePage.goToHomepage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Home Page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Clicking on Approve/Decline Request button in Referrals page
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Clicking on Approve in Request Bind Page
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the Policy Number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary", "Policy Number is : " + policyNumber);

			// verifying the sltf of Transaction,Annual,Term total
			for (int i = 1; i <= 3; i++) {

				// Getting premium of Transaction, Annual, Term Total
				premium = policySummaryPage.PremiumFee.formatDynamicPath(i).getData().replaceAll("[^\\d-.]", "");
				inspectionFee = policySummaryPage.PremiunInspectionFee.formatDynamicPath(i).getData().replace("$", "");
				policyFee = policySummaryPage.PremiumPolicyFee.formatDynamicPath(i).getData().replace("$", "")
						.replace(",", "");
				surplusContributionValue = policySummaryPage.surplusContributionValue.formatDynamicPath(i).getData()
						.replace("$", "").replace(",", "");

				// Conversion of String to double/int to calculate sltf
				d_premium = Double.parseDouble(premium);
				d_inspectionFee = Double.parseDouble(inspectionFee);
				d_policyFee = Double.parseDouble(policyFee);
				d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

				// Getting surplusLinesTaxesPercentage from test data
				surplusLinesTaxesPercentage = testData.get("SurplusLinesTaxesPercentage");

				d_surplusLinesTaxesPercentage = Double.parseDouble(surplusLinesTaxesPercentage);

				// Calculate SLTF=(Premium+Fees)*surplusLinesTaxesPercentage(0.0485)
				d_SLTFValue = (d_premium + (d_inspectionFee + d_policyFee) + d_surplusContributionValue)
						* d_surplusLinesTaxesPercentage;

				// Rounding Off SLTF decimal value to 2 digits and Converting back to String
				SLTFRoundOff = BigDecimal.valueOf(d_SLTFValue);
				SLTFRoundOff = SLTFRoundOff.setScale(2, RoundingMode.HALF_UP);
				calculatedSLTFValue = format.format(SLTFRoundOff);

				// Getting Actual SLTF Value
				actualSLTFValue = policySummaryPage.TaxesAndStateFees.formatDynamicPath(i).getData();

				// Verify actual and calculated SLTF values are equal
				if (actualSLTFValue != null && calculatedSLTFValue != null) {
					if (i == 1) {
						Assertions.passTest("Policy Summary Page",
								"Actual Transation Surplus Lines Taxes and Fees : " + actualSLTFValue);
						Assertions.passTest("Policy Summary Page",
								"Calculated Transaction Surplus Lines Taxes and Fees: " + calculatedSLTFValue);
						Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true,
								"Policy Summary Page",
								"The Transaction SLTF value is calculated correctly as per the AL percentage(6%)",
								false, false);
					} else if (i == 2) {
						Assertions.passTest("Policy Summary Page",
								"Actual Annual Surplus Lines Taxes and Fees : " + actualSLTFValue);
						Assertions.passTest("Policy Summary Page",
								"Calculated Annual Surplus Lines Taxes and Fees : " + calculatedSLTFValue);
						Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true,
								"Policy Summary Page",
								"The Annual SLTF value is calculated correctly as per the AL percentage(6%)", false,
								false);
					} else if (i == 3) {
						Assertions.passTest("Policy Summary Page",
								"Actual Term Total Surplus Lines Taxes and Fees : " + actualSLTFValue);
						Assertions.passTest("Policy Summary Page",
								"Calculated Term Total Annual Surplus Lines Taxes and Fees : " + calculatedSLTFValue);
						Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true,
								"Policy Summary Page",
								"The Annual Term Total SLTF value is calculated correctly as per the AL percentage(6%)",
								false, false);
					}
				}
			}

			// Click on Endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			testData = data.get(dataValue2);

			// Setting Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on edit prior losses
			endorsePolicyPage.editPriorLoss.scrollToElement();
			endorsePolicyPage.editPriorLoss.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit prior loss link");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next button");

			// clicking on ok,continue in Endorse policy page
			if (endorsePolicyPage.pageName.getData().contains("Overrides Required")) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// SLTF value calculated and displayed correctly for Transaction, Annual
			// and Term total columns in Endorse Policy page
			testData = data.get(dataValue1);
			Assertions.passTest("Endorse Policy Page",
					"Transaction Term Premium : " + endorsePolicyPage.premiumDetails.formatDynamicPath(1, 2).getData());

			premiumAmount = endorsePolicyPage.premiumDetails.formatDynamicPath(1, 2).getData().replaceAll("[^\\d-.]",
					"");
			inspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath(2, 2).getData().replaceAll("[^\\d-.]",
					"");
			policyFee = endorsePolicyPage.premiumDetails.formatDynamicPath(3, 2).getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorsePolicyPage.premiumDetails.formatDynamicPath(5, 2).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal termsurplustaxes = BigDecimal.valueOf(surplusTax);
			termsurplustaxes = termsurplustaxes.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated
			// value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					format.format(termsurplustaxes), "Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(termsurplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					format.format(termsurplustaxes), "Endorse Policy Page", "Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing calculated value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 2).getData() + ".00",
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Policy Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 2).getData() + ".00",
					format.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(termsurplustaxes + "")
							+ (Integer.parseInt(inspectionFee) + (Integer.parseInt(policyFee))))),
					"Endorse Policy Page", "Actual Total Premium, Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(6, 2).getData(),
					false, false);

			Assertions.passTest("Endorse Policy Page",
					"Annual Term Premium : " + endorsePolicyPage.premiumDetails.formatDynamicPath(1, 3).getData());

			premiumAmount = endorsePolicyPage.premiumDetails.formatDynamicPath(1, 3).getData().replaceAll("[^\\d-.]",
					"");
			inspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath(2, 3).getData().replaceAll("[^\\d-.]",
					"");
			policyFee = endorsePolicyPage.premiumDetails.formatDynamicPath(3, 3).getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorsePolicyPage.premiumDetails.formatDynamicPath(5, 3).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal annualsurplustaxes = BigDecimal.valueOf(surplusTax);
			annualsurplustaxes = annualsurplustaxes.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated
			// value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					format.format(annualsurplustaxes), "Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(annualsurplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					format.format(annualsurplustaxes), "Endorse Policy Page", "Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing calculated value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes + "") + (Integer
									.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Policy Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Policy Page", "Actual Total Premium, Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(6, 3).getData(),
					false, false);

			Assertions.passTest("Endorse Policy Page",
					"Term Total Premium : " + endorsePolicyPage.premiumDetails.formatDynamicPath(1, 4).getData());

			premiumAmount = endorsePolicyPage.premiumDetails.formatDynamicPath(1, 4).getData().replaceAll("[^\\d-.]",
					"");
			inspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath(2, 4).getData().replaceAll("[^\\d-.]",
					"");
			policyFee = endorsePolicyPage.premiumDetails.formatDynamicPath(3, 4).getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorsePolicyPage.premiumDetails.formatDynamicPath(5, 4).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal totaltermsurplustaxes = BigDecimal.valueOf(surplusTax);
			totaltermsurplustaxes = totaltermsurplustaxes.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated
			// value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					format.format(totaltermsurplustaxes), "Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(totaltermsurplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					format.format(totaltermsurplustaxes), "Endorse Policy Page",
					"Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing calculated value
			String actualTotalPremiumTaxesandFees = endorsePolicyPage.premiumDetails.formatDynamicPath(6, 4).getData()
					.replace("$", "").replace(",", "");
			double calcTotalPremiumTaxesandFees = (Double.parseDouble(premiumAmount))
					+ (Double.parseDouble(totaltermsurplustaxes + "") + (Integer.parseInt(inspectionFee)
							+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))));
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualTotalPremiumTaxesandFees), 2)
					- Precision.round(calcTotalPremiumTaxesandFees, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Policy Page", "Calculated Premium, Taxes and Fees : " + "$"
						+ Precision.round(calcTotalPremiumTaxesandFees, 2));
				Assertions.passTest("Endorse Policy Page",
						"Actual Premium, Taxes and Fees: " + "$" + actualTotalPremiumTaxesandFees);
			} else {
				Assertions.verify(actualTotalPremiumTaxesandFees, calcTotalPremiumTaxesandFees, "Endorse Policy Page",
						"The Difference between actual and calculated total premium is more than 0.05", false, false);

			}

			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete");

			// getting transaction,annual and term total premiums from Endorse
			// Summary page
			Assertions.passTest("Endorse Summary Page", "Transaction Term Premium : "
					+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 2).getData());

			premiumAmount = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 2).getData()
					.replaceAll("[^\\d-.]", "");
			inspectionFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(2, 2).getData()
					.replaceAll("[^\\d-.]", "");
			policyFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(3, 2).getData()
					.replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(6, 2).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal termsurplustaxesSummary = BigDecimal.valueOf(surplusTax);
			termsurplustaxesSummary = termsurplustaxesSummary.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated
			// value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					format.format(termsurplustaxesSummary), "Endorse Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(termsurplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions
					.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 2).getData(),
							format.format(termsurplustaxesSummary), "Endorse Summary Page",
							"Actual Surplus Lines Taxes and Fees : "
									+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 2).getData(),
							false, false);

			// Comparing actual and expected total premium and fees value and
			// printing calculated value
			String actualTotalPremium = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 2).getData()
					.replace("$", "").replace(",", "");
			double calcTotalPremium = (Double.parseDouble(premiumAmount))
					+ (Double.parseDouble(termsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
							+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))));
			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(actualTotalPremium), 2) - Precision.round(calcTotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Summary Page",
						"Calculated Premium, Taxes and Fees : " + "$" + Precision.round(calcTotalPremium, 2));
				Assertions.passTest("Endorse Summary Page",
						"Actual Premium, Taxes and Fees: " + "$" + actualTotalPremium);
			} else {
				Assertions.verify(actualTotalPremium, calcTotalPremium, "Endorse Summary Page",
						"The Difference between actual and calculated total premium is more than 0.05", false, false);

			}

			Assertions.passTest("Endorse Summary Page", "Annual Term Premium : "
					+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 3).getData());

			premiumAmount = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 3).getData()
					.replaceAll("[^\\d-.]", "");
			inspectionFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(2, 3).getData()
					.replaceAll("[^\\d-.]", "");
			policyFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(3, 3).getData()
					.replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(6, 3).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal annualsurplustaxesSummary = BigDecimal.valueOf(surplusTax);
			annualsurplustaxesSummary = annualsurplustaxesSummary.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated
			// value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					format.format(annualsurplustaxesSummary), "Endorse Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(annualsurplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions
					.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 3).getData(),
							format.format(annualsurplustaxesSummary), "Endorse Summary Page",
							"Actual Surplus Lines Taxes and Fees : "
									+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 3).getData(),
							false, false);

			// Comparing actual and expected total premium and fees value and
			// printing calculated value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Summary Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing actual value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Summary Page", "Actual Total Premium, Taxes and Fees : "
							+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 3).getData(),
					false, false);

			Assertions.passTest("Endorse Summary Page", "Term Total Premium : "
					+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 4).getData());

			premiumAmount = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 4).getData()
					.replaceAll("[^\\d-.]", "");
			inspectionFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(2, 4).getData()
					.replaceAll("[^\\d-.]", "");
			policyFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(3, 4).getData()
					.replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(6, 4).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal totaltermsurplustaxesSummary = BigDecimal.valueOf(surplusTax);
			totaltermsurplustaxesSummary = totaltermsurplustaxesSummary.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated
			// value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					format.format(totaltermsurplustaxesSummary), "Endorse Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(totaltermsurplustaxes), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					format.format(totaltermsurplustaxesSummary), "Endorse Summary Page",
					"Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and
			// printing calculated value

			String actualTotalPremum = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 4).getData()
					.replace("$", "").replace(",", "");
			double calcTotalPremum = (Double.parseDouble(premiumAmount))
					+ (Double.parseDouble(totaltermsurplustaxesSummary + "") + (Integer.parseInt(inspectionFee)
							+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))));
			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(actualTotalPremum), 2) - Precision.round(calcTotalPremum, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Summary Page",
						"Calculated Premium, Taxes and Fees : " + "$" + Precision.round(calcTotalPremum, 2));
				Assertions.passTest("Endorse Summary Page",
						"Actual Premium, Taxes and Fees: " + "$" + actualTotalPremum);
			} else {
				Assertions.verify(actualTotalPremum, calcTotalPremum, "Endorse Summary Page",
						"The Difference between actual and calculated total premium is more than 0.05", false, false);

			}

			endorsePolicyPage.scrollToBottomPage();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.closeButton.click();

			// Click on EndorsePB link
			testData = data.get(dataValue3);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Setting Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			if (!testData.get("TransactionType").equals("")) {
				if (testData.get("TransactionType").contains("OOS")) {
					endorsePolicyPage.continueButton.scrollToElement();
					endorsePolicyPage.continueButton.click();
				}
			}

			// Click on Fee only endorsement
			endorsePolicyPage.feeOnlyEndorsement.waitTillPresenceOfElement(60);
			endorsePolicyPage.feeOnlyEndorsement.waitTillVisibilityOfElement(60);
			endorsePolicyPage.feeOnlyEndorsement.waitTillElementisEnabled(60);
			endorsePolicyPage.feeOnlyEndorsement.scrollToElement();
			endorsePolicyPage.feeOnlyEndorsement.click();

			Assertions.verify(overridePremiumAndFeesPage.saveAndClose.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees Page loaded successfully", false,
					false);

			if (testData.get("PolicyFeeOverride") != null) {
				if (!testData.get("PolicyFeeOverride").equalsIgnoreCase("")) {
					overridePremiumAndFeesPage.policyFee.scrollToElement();
					overridePremiumAndFeesPage.policyFee.setData(testData.get("PolicyFeeOverride"));
				}
			}
			if (testData.get("InspectionFeeOverride") != null) {
				if (!testData.get("InspectionFeeOverride").equalsIgnoreCase("")) {
					overridePremiumAndFeesPage.transactionInspectionFee.scrollToElement();
					overridePremiumAndFeesPage.transactionInspectionFee.setData(testData.get("InspectionFeeOverride"));
				}
			}
			overridePremiumAndFeesPage.saveAndClose.scrollToElement();
			overridePremiumAndFeesPage.saveAndClose.click();
			overridePremiumAndFeesPage.saveAndClose.waitTillInVisibilityOfElement(60);

			testData = data.get(dataValue1);
			// SLTF value calculated and displayed correctly for Transaction, Annual
			// and Term total columns in Endorse Policy page
			Assertions.passTest("Endorse Policy Page",
					"Transaction Term Premium : " + endorsePolicyPage.premiumDetails.formatDynamicPath(1, 2).getData());

			premiumAmount = endorsePolicyPage.premiumDetails.formatDynamicPath(1, 2).getData().replaceAll("[^\\d-.]",
					"");
			inspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath(2, 2).getData().replaceAll("[^\\d-.]",
					"");
			policyFee = endorsePolicyPage.premiumDetails.formatDynamicPath(3, 2).getData().replaceAll("[^\\d-.]", "");

			surplusContributionValue = endorsePolicyPage.premiumDetails.formatDynamicPath(5, 2).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal termsurplustaxes1 = BigDecimal.valueOf(surplusTax);
			termsurplustaxes1 = termsurplustaxes1.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					format.format(termsurplustaxes1), "Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(termsurplustaxes1), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					format.format(termsurplustaxes1), "Endorse Policy Page", "Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 2).getData() + ".00",
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxes1 + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Policy Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxes1 + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 2).getData() + ".00",
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxes1 + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Policy Page", "Actual Total Premium, Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(6, 2).getData(),
					false, false);

			Assertions.passTest("Endorse Policy Page",
					"Annual Term Premium : " + endorsePolicyPage.premiumDetails.formatDynamicPath(1, 3).getData());

			premiumAmount = endorsePolicyPage.premiumDetails.formatDynamicPath(1, 3).getData().replaceAll("[^\\d-.]",
					"");
			inspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath(2, 3).getData().replaceAll("[^\\d-.]",
					"");
			policyFee = endorsePolicyPage.premiumDetails.formatDynamicPath(3, 3).getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorsePolicyPage.premiumDetails.formatDynamicPath(5, 3).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal annualsurplustaxes1 = BigDecimal.valueOf(surplusTax);
			annualsurplustaxes1 = annualsurplustaxes1.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					format.format(annualsurplustaxes1), "Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(annualsurplustaxes1), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					format.format(annualsurplustaxes1), "Endorse Policy Page", "Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes1 + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Policy Page",
					"Calculated Total Premium, Taxes and Fees : " + format
							.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(annualsurplustaxes1 + "")
									+ (Integer.parseInt(inspectionFee) + (Integer.parseInt(policyFee))))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(6, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes1 + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Policy Page", "Actual Total Premium, Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(6, 3).getData(),
					false, false);

			Assertions.passTest("Endorse Policy Page",
					"Term Total Premium : " + endorsePolicyPage.premiumDetails.formatDynamicPath(1, 4).getData());

			premiumAmount = endorsePolicyPage.premiumDetails.formatDynamicPath(1, 4).getData().replaceAll("[^\\d-.]",
					"");
			inspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath(2, 4).getData().replaceAll("[^\\d-.]",
					"");
			policyFee = endorsePolicyPage.premiumDetails.formatDynamicPath(3, 4).getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorsePolicyPage.premiumDetails.formatDynamicPath(5, 4).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal totaltermsurplustaxes1 = BigDecimal.valueOf(surplusTax);
			totaltermsurplustaxes1 = totaltermsurplustaxes1.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					format.format(totaltermsurplustaxes1), "Endorse Policy Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(totaltermsurplustaxes1), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					format.format(totaltermsurplustaxes1), "Endorse Policy Page",
					"Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			String actualTotalPremiumTax = endorsePolicyPage.premiumDetails.formatDynamicPath(6, 4).getData()
					.replace("$", "").replace(",", "");
			double calcTotalPremiumTax = (Double.parseDouble(premiumAmount))
					+ (Double.parseDouble(totaltermsurplustaxes1 + "") + (Integer.parseInt(inspectionFee)
							+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))));
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualTotalPremiumTax), 2)
					- Precision.round(calcTotalPremiumTax, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Calculated Premium, Taxes and Fees : " + "$" + Precision.round(calcTotalPremiumTax, 2));
				Assertions.passTest("Endorse Policy Page",
						"Actual Premium, Taxes and Fees: " + "$" + actualTotalPremiumTax);
			} else {
				Assertions.verify(actualTotalPremiumTax, calcTotalPremiumTax, "Endorse Policy Page",
						"The Difference between actual and calculated total premium is more than 0.05", false, false);

			}

			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete");

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// getting transaction,annual and term total premiums from Endorse Summary page

			Assertions.passTest("Endorse Summary Page", "Transaction Term Premium : "
					+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 2).getData());

			premiumAmount = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 2).getData()
					.replaceAll("[^\\d-.]", "");
			inspectionFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(2, 2).getData()
					.replaceAll("[^\\d-.]", "");
			policyFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(3, 2).getData()
					.replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(6, 2).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal termsurplustaxesSummary1 = BigDecimal.valueOf(surplusTax);
			termsurplustaxesSummary1 = termsurplustaxesSummary1.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 2).getData(),
					format.format(termsurplustaxesSummary1), "Endorse Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(termsurplustaxes1), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions
					.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 2).getData(),
							format.format(termsurplustaxesSummary1), "Endorse Summary Page",
							"Actual Surplus Lines Taxes and Fees : "
									+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 2).getData(),
							false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 2).getData() + ".00",
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxesSummary1 + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Summary Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxesSummary1 + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 2).getData() + ".00",
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(termsurplustaxesSummary1 + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Summary Page", "Actual Total Premium, Taxes and Fees : "
							+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 2).getData(),
					false, false);

			Assertions.passTest("Endorse Summary Page", "Annual Term Premium : "
					+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 3).getData());

			premiumAmount = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 3).getData()
					.replaceAll("[^\\d-.]", "");
			inspectionFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(2, 3).getData()
					.replaceAll("[^\\d-.]", "");
			policyFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(3, 3).getData()
					.replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(6, 3).getData()
					.replaceAll("[^\\d-.]", "");
			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal annualsurplustaxesSummary1 = BigDecimal.valueOf(surplusTax);
			annualsurplustaxesSummary1 = annualsurplustaxesSummary1.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 3).getData(),
					format.format(annualsurplustaxesSummary1), "Endorse Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(annualsurplustaxes1), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions
					.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 3).getData(),
							format.format(annualsurplustaxesSummary1), "Endorse Summary Page",
							"Actual Surplus Lines Taxes and Fees : "
									+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 3).getData(),
							false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxesSummary1 + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Summary Page",
					"Calculated Total Premium, Taxes and Fees : " + format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxes1 + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 3).getData(),
					format.format((Double.parseDouble(premiumAmount))
							+ (Double.parseDouble(annualsurplustaxesSummary1 + "") + (Integer.parseInt(inspectionFee)
									+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))))),
					"Endorse Summary Page", "Actual Total Premium, Taxes and Fees : "
							+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 3).getData(),
					false, false);

			Assertions.passTest("Endorse Summary Page", "Term Total Premium : "
					+ endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 4).getData());

			premiumAmount = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(1, 4).getData()
					.replaceAll("[^\\d-.]", "");
			inspectionFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(2, 4).getData()
					.replaceAll("[^\\d-.]", "");
			policyFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(3, 4).getData()
					.replaceAll("[^\\d-.]", "");
			surplusContributionValue = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(6, 4).getData()
					.replaceAll("[^\\d-.]", "");

			surplusTax = (Double.parseDouble(premiumAmount) + Integer.parseInt(inspectionFee)
					+ Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			BigDecimal totaltermsurplustaxesSummary1 = BigDecimal.valueOf(surplusTax);
			totaltermsurplustaxesSummary1 = totaltermsurplustaxesSummary1.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					format.format(totaltermsurplustaxesSummary1), "Endorse Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(totaltermsurplustaxes1), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					format.format(totaltermsurplustaxesSummary1), "Endorse Summary Page",
					"Actual Surplus Lines Taxes and Fees : "
							+ endorsePolicyPage.premiumDetails.formatDynamicPath(4, 4).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			String actualTotalPremiumTaxesandFee = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath(7, 4)
					.getData().replace("$", "").replace(",", "");
			double calcTotalPremiumTaxesandFee = (Double.parseDouble(premiumAmount))
					+ (Double.parseDouble(totaltermsurplustaxesSummary1 + "") + (Integer.parseInt(inspectionFee)
							+ (Integer.parseInt(policyFee) + Double.parseDouble(surplusContributionValue))));
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualTotalPremiumTaxesandFee), 2)
					- Precision.round(calcTotalPremiumTaxesandFee, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Summary Page", "Calculated Premium, Taxes and Fees : " + "$"
						+ Precision.round(calcTotalPremiumTaxesandFee, 2));
				Assertions.passTest("Endorse Summary Page",
						"Actual Premium, Taxes and Fees: " + "$" + actualTotalPremiumTaxesandFee);
			} else {
				Assertions.verify(actualTotalPremiumTaxesandFee, calcTotalPremiumTaxesandFee, "Endorse Summary Page",
						"The Difference between actual and calculated total premium is more than 0.05", false, false);

			}

			endorsePolicyPage.scrollToBottomPage();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.closeButton.click();

			// Click on 3rd Endorsement History
			policySummaryPage.endorsementTransaction.formatDynamicPath(3).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(3).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(3).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(3).click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on Third Endorsement History");

			// Click on Policy Snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			Assertions.verify(viewPolicySnapShot.surplusLinesTaxesValue.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);

			// Comparing actual and expected SLTF value and printing actual and expected
			// value
			Assertions.verify(viewPolicySnapShot.surplusLinesTaxesValue.getData(),
					format.format(termsurplustaxesSummary1).replace(",", ""), "View Policy Snapshot Page",
					"Calculated Surplus Fees : " + format.format(termsurplustaxesSummary1).replace(",", ""), false,
					false);
			Assertions.verify(viewPolicySnapShot.surplusLinesTaxesValue.getData(),
					format.format(termsurplustaxesSummary1).replace(",", ""), "View Policy Snapshot Page",
					"Actual Surplus Fees : " + viewPolicySnapShot.surplusLinesTaxesValue.getData(), false, false);
			Assertions.verify(viewPolicySnapShot.surplusLinesTaxesValue.getData(),
					format.format(termsurplustaxesSummary1).replace(",", ""), "View Policy Snapshot Page",
					"Surplus Fees calculated as per Surplus Lines Taxes Percentage 6% for AL is verified", false,
					false);

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC061 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC061 ", "Executed Successfully");
			}
		}
	}
}
