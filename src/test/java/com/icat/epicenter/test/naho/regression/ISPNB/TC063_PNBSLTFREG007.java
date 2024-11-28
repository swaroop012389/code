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
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC063_PNBSLTFREG007 extends AbstractNAHOTest {

	public TC063_PNBSLTFREG007() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBSLTFREG007.xls";
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
		ViewPolicySnapShot viewPolicySnapshot = new ViewPolicySnapShot();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DecimalFormat df = new DecimalFormat("0.00");
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		String quoteNumber;
		String policyNumber;
		String premiumAmount;
		String totalPremiumAmount;
		String surplusContributionValue;
		double surplusTax;
		double stampingFee;
		String fees;
		BigDecimal surplustaxes;
		BigDecimal stampingtaxes;
		BigDecimal surplustaxesandFees;
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		Map<String, String> testData1 = data.get(dataValue2);
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
					"Account Overview Page", "Premium Value : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Fees Value : " + accountOverviewPage.feesValue.getData(), false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// getting premium amount
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// getting fees value
			fees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");

			// getting surplus contribution value
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "");

			// getting total premium value
			totalPremiumAmount = accountOverviewPage.totalPremiumValue.getData().replaceAll("[^\\d-.]", "");

			// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
			// percentage 0.0485 and stamping fee percentage 0.00075
			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees)
					+ Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));
			stampingFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees)
					+ Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("StampingFeePercentage"));

			// Rounding sltf taxes/stamping fee decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);
			stampingtaxes = BigDecimal.valueOf(stampingFee);
			stampingtaxes = stampingtaxes.setScale(2, RoundingMode.HALF_UP);

			// Rounding Surplus Lines,Taxes and Fees decimal value to 2 digits
			surplustaxesandFees = BigDecimal.valueOf(surplustaxes.doubleValue() + stampingtaxes.doubleValue());
			surplustaxesandFees = surplustaxesandFees.setScale(2, RoundingMode.HALF_UP);

			// getting nb premium and taxes values
			Double d_nbPremium = Double.parseDouble(premiumAmount);
			Double d_nbFees = Double.parseDouble(fees);
			Double d_nbtotalPremium = Double.parseDouble(totalPremiumAmount);
			Double d_nbSLTF = surplustaxes.doubleValue() + stampingtaxes.doubleValue();

			// Round off decimal values
			BigDecimal NBPremiumRoundOff = BigDecimal.valueOf(d_nbPremium);
			NBPremiumRoundOff = NBPremiumRoundOff.setScale(2, RoundingMode.HALF_UP);
			String nbPremium = format.format(d_nbPremium);
			BigDecimal NBFeesRoundOff = BigDecimal.valueOf(d_nbFees);
			NBFeesRoundOff = NBFeesRoundOff.setScale(2, RoundingMode.HALF_UP);
			String nbFees = format.format(d_nbFees);
			BigDecimal NBTotalPremiumRoundOff = BigDecimal.valueOf(d_nbtotalPremium);
			NBTotalPremiumRoundOff = NBTotalPremiumRoundOff.setScale(2, RoundingMode.HALF_UP);
			String nbTotalPremium = format.format(d_nbtotalPremium);
			BigDecimal NBSLFTRoundOff = BigDecimal.valueOf(d_nbSLTF);
			NBSLFTRoundOff = NBSLFTRoundOff.setScale(2, RoundingMode.HALF_UP);
			String nbSLTF = format.format(d_nbSLTF);

			// Comparing actual and expected SLTF value
			Assertions.verify(accountOverviewPage.sltfValue.getData(), format.format(surplustaxesandFees),
					"Account Overview Page",
					"Surplus Lines Taxes and Fees Value : " + accountOverviewPage.sltfValue.getData() + " is verified",
					false, false);

			// Comparing actual and expected total premium and fees value
			Assertions.verify(accountOverviewPage.totalPremiumValue.getData(),
					format.format((Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContributionValue))
							+ (Double.parseDouble(surplustaxesandFees + "") + (Double.parseDouble(fees)))),
					"Account Overview Page", "Total Premium Value :" + accountOverviewPage.totalPremiumValue.getData(),
					false, false);

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
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");

				accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();

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

			// clicking on policy snap shot page
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();

			// Comparing actual and expected SLTF value
			// value
			Assertions
					.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace(",", ""),
							format.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
							"Surplus Fees calculated as per Surplus Lines Taxes Percentage 4.85% for TX :"
									+ viewPolicySnapshot.surplusLinesTaxesValue.getData() + " is verified",
							false, false);

			// Comparing actual and expected Stamping fee value
			Assertions.verify(viewPolicySnapshot.stampingFeeValue.getData(),
					format.format(stampingtaxes).replace(",", ""), "View Policy Snapshot Page",
					"Stamping Fees calculated as per Stamping Fees Percentage 0.75% for TX :"
							+ viewPolicySnapshot.stampingFeeValue.getData() + " is verified",
					false, false);

			// click on go back button
			viewPolicySnapshot.scrollToTopPage();
			viewPolicySnapshot.waitTime(3);
			viewPolicySnapshot.goBackButton.click();

			// Click on endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// Setting Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on change coverage options
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on change coverage option link successfully");

			// update coverage details
			createQuotePage.enterDeductiblesNAHO(testData1);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData1);
			Assertions.passTest("Create Quote Page", "Details entered successfully");

			// continue endorsement
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// click on next
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting transaction values
			String transactionPremiumAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "2")
					.getData();
			String transactionInspectionFeeAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "2")
					.getData();
			String transactionPolicyFeeAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "2")
					.getData();
			String transactionSLTFAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "2").getData()
					.replace("$", "").replace("-", "").replace(",", "");
			String transactionSurplusContributionValueAfterPBEndt = endorsePolicyPage.premiumDetails
					.formatDynamicPath("5", "2").getData().replace("$", "").replace(",", "").replace("-", "");
			String transactionTotalPremiumAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("6", "2")
					.getData().replace("$", "").replace(",", "").replace("-", "");

			// converting string to double
			double d_transactionPremiumAfterPBEndt = Double
					.parseDouble(transactionPremiumAfterPBEndt.replace("$", "").replace("-", "").replace(",", ""));
			double d_transactionFeesAfterPBEndt = (Double
					.parseDouble(transactionInspectionFeeAfterPBEndt.replace("$", ""))
					+ Double.parseDouble(transactionPolicyFeeAfterPBEndt.replace("$", "")));
			double d_transactionSurplusContributionValueAfterPBEndt = Double
					.parseDouble(transactionSurplusContributionValueAfterPBEndt);

			// calculating sltf percentage by adding Premium+Fees+SurplusContribution and
			// multiplying by sltf
			// percentage 0.0485 and stamping fee percentage 0.00075
			double surplusTransactionTaxAfterPBEndt = (d_transactionPremiumAfterPBEndt + d_transactionFeesAfterPBEndt
					+ d_transactionSurplusContributionValueAfterPBEndt)
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));
			double stampingTransactionFeeAfterPBEndt = (d_transactionPremiumAfterPBEndt + d_transactionFeesAfterPBEndt
					+ d_transactionSurplusContributionValueAfterPBEndt)
					* Double.parseDouble(testData.get("StampingFeePercentage"));
			double sltf = surplusTransactionTaxAfterPBEndt + stampingTransactionFeeAfterPBEndt;

			// Calculating Transaction total premium = Premium+Fees+SurplusContribution+sltf
			double calculatedTransactionTotalPremium = Double.parseDouble(transactionSLTFAfterPBEndt)
					+ d_transactionSurplusContributionValueAfterPBEndt + d_transactionPremiumAfterPBEndt;

			// Rounding Off Fees decimal value to 2 digits and Converting back to String
			BigDecimal TransactionFeesRoundOffAfterPBEndt = BigDecimal.valueOf(d_transactionFeesAfterPBEndt);
			TransactionFeesRoundOffAfterPBEndt = TransactionFeesRoundOffAfterPBEndt.setScale(2, RoundingMode.HALF_UP);
			String transactionFeesAfterPBEndt1 = format.format(d_transactionFeesAfterPBEndt);

			// Asserting transaction values
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath("1", "2").checkIfElementIsDisplayed(),
					true, "Endorse Policy Page",
					"Transaction Premium after PB endorsement : " + transactionPremiumAfterPBEndt, false, false);
			Assertions.verify(transactionFeesAfterPBEndt1.contains("$0.00"), true, "Endorse Policy Page",
					"Transaction Fees after PB endorsement : " + transactionFeesAfterPBEndt1, false, false);
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath("5", "2").checkIfElementIsDisplayed(),
					true, "Endorse Policy Page", "Transaction Surplus contribution value after PB endorsement : " + "-$"
							+ transactionSurplusContributionValueAfterPBEndt,
					false, false);

			if (Precision.round(Math
					.abs(Precision.round(Double.parseDouble(transactionSLTFAfterPBEndt), 2) - Precision.round(sltf, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Calculated Transaction SLTF after PB endorsement : " + "$" + Precision.round(sltf, 2));
				Assertions.passTest("Endorse Policy Page",
						"Actual Transaction SLTF after PB endorsement : " + "$" + transactionSLTFAfterPBEndt);

			} else {
				Assertions.verify(transactionSLTFAfterPBEndt, sltf, "Endorse Policy Page",
						"The Difference between actual and calculated SLTF is more than 0.05", false, false);

			}
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(transactionTotalPremiumAfterPBEndt), 2)
					- Precision.round(calculatedTransactionTotalPremium, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Calculated Transaction Total Premium after PB endorsement : " + "$"
								+ Precision.round(calculatedTransactionTotalPremium, 2));
				Assertions.passTest("Endorse Policy Page", "Actual Transaction Total Premium after PB endorsement : "
						+ "$" + transactionTotalPremiumAfterPBEndt);

			} else {
				Assertions.verify(transactionTotalPremiumAfterPBEndt, calculatedTransactionTotalPremium,
						"Endorse Policy Page", "The Difference between actual and calculated SLTF is more than 0.05",
						false, false);

			}

			// Getting annual values
			String annualPremiumAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "3").getData()
					.replace("$", "").replace(",", "");
			String annualInspectionFeeAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "3")
					.getData().replace("$", "").replace(",", "");
			String annualPolicyFeeAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "3").getData()
					.replace("$", "").replace(",", "");
			String annualSLTFAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "3").getData()
					.replace("$", "").replace(",", "");
			String annualTotalPremiumAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("6", "3")
					.getData().replace("$", "").replace(",", "");
			String annualSurplusContributionAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("5", "3")
					.getData().replace("$", "").replace(",", "").replace(".00", "");

			// calculating sltf percentage by adding Premium+Fees +Surplus Contribution and
			// multiplying by sltf
			// percentage 0.0485 and stamping fee percentage 0.00075
			double surplusAnnualTaxAfterPBEndt = (Double.parseDouble(annualPremiumAfterPBEndt)
					+ Double.parseDouble(annualInspectionFeeAfterPBEndt)
					+ Double.parseDouble(annualPolicyFeeAfterPBEndt)
					+ Double.parseDouble(annualSurplusContributionAfterPBEndt))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));
			double stampingAnnualFeeAfterPBEndt = (Double.parseDouble(annualPremiumAfterPBEndt)
					+ Double.parseDouble(annualInspectionFeeAfterPBEndt)
					+ Double.parseDouble(annualPolicyFeeAfterPBEndt)
					+ Double.parseDouble(annualSurplusContributionAfterPBEndt))
					* Double.parseDouble(testData.get("StampingFeePercentage"));
			double calAnnualSLTF = surplusAnnualTaxAfterPBEndt + stampingAnnualFeeAfterPBEndt;
			double expectedCalAnnualSLTF = Double.parseDouble(df.format(calAnnualSLTF));

			// Calculating Annual total premium = Premium+Fees+SurplusContribution+sltf
			double calculatedAnnualTotalPremium = Double.parseDouble(annualSLTFAfterPBEndt)
					+ Double.parseDouble(annualSurplusContributionAfterPBEndt)
					+ Double.parseDouble(annualPremiumAfterPBEndt) + Double.parseDouble(annualInspectionFeeAfterPBEndt)
					+ Double.parseDouble(annualPolicyFeeAfterPBEndt);

			// Converting fee double to string
			double d_fees = Double.parseDouble(annualInspectionFeeAfterPBEndt)
					+ Double.parseDouble(annualPolicyFeeAfterPBEndt);
			String s_fees = String.valueOf(d_fees).replace(".0", "");

			Assertions.verify(annualPremiumAfterPBEndt,
					df.format(d_nbPremium - d_transactionPremiumAfterPBEndt).replace("$", "").replace(",", "")
							.replace(".00", ""),
					"Endorse Policy Page", "Annual Premium after PB endorsement : " + "$" + annualPremiumAfterPBEndt,
					false, false);
			Assertions.verify(s_fees,
					df.format(d_nbFees - d_transactionFeesAfterPBEndt).replace("$", "").replace(".00", ""),
					"Endorse Policy Page", "Annual Fee after PB endorsement : " + "$" + s_fees, false, false);
			Assertions.verify(annualSurplusContributionAfterPBEndt,
					df.format(Double.parseDouble(surplusContributionValue)
							- d_transactionSurplusContributionValueAfterPBEndt).replace("$", "").replace(",", "")
							.replace(".00", ""),
					"Endorse Policy Page", "Annual surplus contribution value after PB endorsement : " + "$"
							+ annualSurplusContributionAfterPBEndt,
					false, false);

			// if condition added for comparing actual and expected SLTF

			if (Math.abs(Precision.round(Double.parseDouble(annualSLTFAfterPBEndt), 2))
					- (expectedCalAnnualSLTF) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Actual Annual SLTF after PB endorsement : " + annualSLTFAfterPBEndt);
				Assertions.passTest("Endorse Policy Page",
						"The Calculated Annual SLTF after PB endorsement : " + "$" + expectedCalAnnualSLTF);
			} else {
				Assertions.verify(annualSLTFAfterPBEndt, expectedCalAnnualSLTF, "Endorse Policy Page",
						"The Difference between actual and calculated SLTF is more than 0.05", false, false);
			}

			Assertions.verify(annualTotalPremiumAfterPBEndt, df.format(calculatedAnnualTotalPremium),
					"Endorse Policy Page",
					"Annual Total Premium after PB endorsement : $" + annualTotalPremiumAfterPBEndt, false, false);

			// Getting term values
			String termPremiumAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "4").getData()
					.replace("$", "").replace(",", "");
			String termInspectionFeeAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "4").getData()
					.replace("$", "").replace(",", "");
			String termPolicyFeeAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "4").getData()
					.replace("$", "").replace(",", "");
			String termSLTFAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "4").getData()
					.replace("$", "").replace(",", "");
			// String termTotalPremiumAfterPBEndt =
			// endorsePolicyPage.premiumDetails.formatDynamicPath("6", "4").getData()
			// .replace("$", "").replace(",", "");
			String termSurplusContributionAfterPBEndt = endorsePolicyPage.premiumDetails.formatDynamicPath("5", "4")
					.getData().replace("$", "").replace(",", "").replace(".00", "");

			// Converting fee double to string
			double d_termFees = Double.parseDouble(termInspectionFeeAfterPBEndt)
					+ Double.parseDouble(termPolicyFeeAfterPBEndt);
			String s_termFees = String.valueOf(d_termFees).replace(".0", "");

			// calculating sltf percentage by adding Premium+Fees +Surplus Contribution and
			// multiplying by sltf
			// percentage 0.0485 and stamping fee percentage 0.00075
			double termSurplusTaxAfterPBEndt = (Double.parseDouble(termPremiumAfterPBEndt)
					+ Double.parseDouble(termInspectionFeeAfterPBEndt) + Double.parseDouble(termPolicyFeeAfterPBEndt)
					+ Double.parseDouble(termSurplusContributionAfterPBEndt))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));
			double termStampingFeeAfterPBEndt = (Double.parseDouble(annualPremiumAfterPBEndt)
					+ Double.parseDouble(termInspectionFeeAfterPBEndt) + Double.parseDouble(termPolicyFeeAfterPBEndt)
					+ Double.parseDouble(termSurplusContributionAfterPBEndt))
					* Double.parseDouble(testData.get("StampingFeePercentage"));
			double termCalSLTF = termSurplusTaxAfterPBEndt + termStampingFeeAfterPBEndt;

			// Calculating Annual total premium = Premium+Fees+SurplusContribution+sltf
			double termCalTotalPremium = Double.parseDouble(annualSLTFAfterPBEndt)
					+ Double.parseDouble(annualSurplusContributionAfterPBEndt)
					+ Double.parseDouble(annualPremiumAfterPBEndt) + Double.parseDouble(annualInspectionFeeAfterPBEndt)
					+ Double.parseDouble(annualPolicyFeeAfterPBEndt);

			// Asserting annual values
			Assertions.verify(termPremiumAfterPBEndt,
					df.format(d_nbPremium - d_transactionPremiumAfterPBEndt).replace("$", "").replace(",", "")
							.replace(".00", ""),
					"Endorse Policy Page", "Term Premium after PB endorsement: " + "$" + termPremiumAfterPBEndt, false,
					false);
			Assertions.verify(s_termFees,
					df.format(d_nbFees - d_transactionFeesAfterPBEndt).replace("$", "").replace(".00", ""),
					"Endorse Policy Page", "Term Fee after PB endorsement: " + "$" + s_termFees, false, false);

			Assertions.verify(termSurplusContributionAfterPBEndt,
					df.format(Double.parseDouble(surplusContributionValue)
							- d_transactionSurplusContributionValueAfterPBEndt).replace("$", "").replace(",", "")
							.replace(".00", ""),
					"Endorse Policy Page", "Term surplus contribution value after PB endorsement : " + "$"
							+ termSurplusContributionAfterPBEndt,
					false, false);

			if (Precision.round(Math
					.abs(Precision.round(Double.parseDouble(termSLTFAfterPBEndt), 2) - Precision.round(termCalSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorse Policy Page", "The actual Term total : " + "$" + termSLTFAfterPBEndt);
				Assertions.passTest("Endorse Policy Page",
						"The calculated Term total : " + "$" + df.format(termCalTotalPremium));
			} else {
				Assertions.passTest("Endorse Policy Page",
						"The Difference between actual and calculated Term total is more than 0.05");
			}

			// click on complete
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.verify(endorsePolicyPage.closeButton.checkIfElementIsDisplayed(), true,
					"Endorse Policy Details Page ", "Endorse Policy Details Page loaded successfully ", false, false);

			// Getting transaction values
			String transactionPremiumAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "2")
					.getData();
			String transactionInspectionFeeAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "2")
					.getData();
			String transactionPolicyFeeAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "2")
					.getData();
			String transactionSLTFAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "2").getData()
					.replace("$", "").replace("-", "").replace(",", "");
			String transactionTotalPremiumAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("7", "2")
					.getData().replace("$", "").replace("-", "").replace(",", "");
			String transactionSurplusContributionValueAfterPBEndt2 = endorsePolicyPage.premiumDetails
					.formatDynamicPath("6", "2").getData().replace("$", "").replace("-", "").replace(",", "");

			// converting string to double
			double d_transactionPremiumAfterPBEndt2 = Double
					.parseDouble(transactionPremiumAfterPBEndt2.replace("$", "").replace("-", "").replace(",", ""));
			double d_transactionFeesAfterPBEndt2 = (Double
					.parseDouble(transactionInspectionFeeAfterPBEndt2.replace("$", ""))
					+ Double.parseDouble(transactionPolicyFeeAfterPBEndt2.replace("$", "")));
			double d_transactionSurplusContributionValueAfterPBEndt2 = Double
					.parseDouble(transactionSurplusContributionValueAfterPBEndt2);

			// calculating sltf percentage by adding Premium+Fees+SurplusContribution and
			// multiplying by sltf
			// percentage 0.0485 and stamping fee percentage 0.00075
			double surplusTransactionTaxAfterPBEndt2 = (d_transactionPremiumAfterPBEndt2 + d_transactionFeesAfterPBEndt2
					+ d_transactionSurplusContributionValueAfterPBEndt2)
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));
			double stampingTransactionFeeAfterPBEndt2 = (d_transactionPremiumAfterPBEndt2
					+ d_transactionFeesAfterPBEndt2 + d_transactionSurplusContributionValueAfterPBEndt2)
					* Double.parseDouble(testData.get("StampingFeePercentage"));
			double transactionSLTF = surplusTransactionTaxAfterPBEndt2 + stampingTransactionFeeAfterPBEndt2;

			// Calculating Transaction total premium = Premium+Fees+SurplusContribution+sltf
			double calculatedTransactionTotalPremium2 = Double.parseDouble(transactionSLTFAfterPBEndt2)
					+ d_transactionSurplusContributionValueAfterPBEndt2 + d_transactionPremiumAfterPBEndt2;

			// Rounding Off Fees decimal value to 2 digits and Converting back to String
			BigDecimal TransactionFeesRoundOffAfterPBEndt2 = BigDecimal.valueOf(d_transactionFeesAfterPBEndt2);
			TransactionFeesRoundOffAfterPBEndt2 = TransactionFeesRoundOffAfterPBEndt2.setScale(2, RoundingMode.HALF_UP);
			String transactionFeesAfterPBEndt2 = format.format(d_transactionFeesAfterPBEndt2);

			// Asserting transaction values
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath("1", "2").checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"Transaction Premium after PB endorsement : " + transactionPremiumAfterPBEndt2, false, false);
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath("2", "2").checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"Transaction Fees after PB endorsement : " + transactionFeesAfterPBEndt2, false, false);
			Assertions.verify(endorsePolicyPage.premiumDetails.formatDynamicPath("6", "2").checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "Transaction Surplus contribution value after PB endorsement : "
							+ "-$" + transactionSurplusContributionValueAfterPBEndt2,
					false, false);

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(transactionSLTFAfterPBEndt2), 2)
					- Precision.round(transactionSLTF, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Summary Page", "Calculated Transaction SLTF after PB endorsement : " + "$"
						+ Precision.round(transactionSLTF, 2));
				Assertions.passTest("Endorse Summary Page",
						"Actual Transaction SLTF after PB endorsement : " + "$" + transactionSLTFAfterPBEndt2);

			} else {

				Assertions.verify(transactionSLTFAfterPBEndt2, transactionSLTF, "Endorse Summary Page",
						"The Difference between actual and calculated SLTF is more than 0.05", false, false);
			}

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(transactionTotalPremiumAfterPBEndt2), 2)
					- Precision.round(calculatedTransactionTotalPremium2, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Calculated Transaction Total Premium after PB endorsement : " + "$"
								+ Precision.round(calculatedTransactionTotalPremium2, 2));
				Assertions.passTest("Endorse Policy Page", "Actual Transaction Total Premium after PB endorsement : "
						+ "$" + transactionTotalPremiumAfterPBEndt2);

			} else {
				Assertions.verify(transactionTotalPremiumAfterPBEndt2, calculatedTransactionTotalPremium2,
						"Endorse Policy Page", "The Difference between actual and calculated SLTF is more than 0.05",
						false, false);

			}

			// Getting annual values
			String annualPremiumAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "3").getData()
					.replace("$", "").replace(",", "");
			String annualInspectionFeeAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "3")
					.getData().replace("$", "").replace(",", "");
			String annualPolicyFeeAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "3").getData()
					.replace("$", "").replace(",", "");
			String annualSLTFAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "3").getData()
					.replace("$", "").replace(",", "");
			String annualTotalPremiumAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("7", "3")
					.getData().replace("$", "").replace(",", "");
			String annualSurplusContributionAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("6", "3")
					.getData().replace("$", "").replace(",", "");

			// calculating sltf percentage by adding Premium+Fees +Surplus Contribution and
			// multiplying by sltf
			// percentage 0.0485 and stamping fee percentage 0.00075
			double surplusAnnualTaxAfterPBEndt2 = (Double.parseDouble(annualPremiumAfterPBEndt2)
					+ Double.parseDouble(annualInspectionFeeAfterPBEndt2)
					+ Double.parseDouble(annualPolicyFeeAfterPBEndt2)
					+ Double.parseDouble(annualSurplusContributionAfterPBEndt2))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));
			double stampingAnnualFeeAfterPBEndt2 = (Double.parseDouble(annualPremiumAfterPBEndt2)
					+ Double.parseDouble(annualInspectionFeeAfterPBEndt2)
					+ Double.parseDouble(annualPolicyFeeAfterPBEndt2)
					+ Double.parseDouble(annualSurplusContributionAfterPBEndt2))
					* Double.parseDouble(testData.get("StampingFeePercentage"));
			double annualCalSLTF = surplusAnnualTaxAfterPBEndt2 + stampingAnnualFeeAfterPBEndt2;
			double expectedannualCalSLTF = Double.parseDouble(df.format(annualCalSLTF));

			// Calculating Annual total premium = Premium+Fees+SurplusContribution+sltf
			double annualTotalPremiumCal = Double.parseDouble(annualSLTFAfterPBEndt2)
					+ Double.parseDouble(annualSurplusContributionAfterPBEndt2)
					+ Double.parseDouble(annualPremiumAfterPBEndt2)
					+ Double.parseDouble(annualInspectionFeeAfterPBEndt2)
					+ Double.parseDouble(annualPolicyFeeAfterPBEndt2);

			// Converting fee double to string
			double d_annualfees = Double.parseDouble(annualInspectionFeeAfterPBEndt2)
					+ Double.parseDouble(annualPolicyFeeAfterPBEndt2);
			String s_annualFees = String.valueOf(d_annualfees).replace(".0", "");

			Assertions.verify(annualPremiumAfterPBEndt2,
					df.format(d_nbPremium - d_transactionPremiumAfterPBEndt2).replace("$", "").replace(",", "")
							.replace(".00", ""),
					"Endorse Policy Page", "Annual Premium after PB endorsement : " + "$" + annualPremiumAfterPBEndt2,
					false, false);
			Assertions.verify(s_annualFees,
					df.format(d_nbFees - d_transactionFeesAfterPBEndt2).replace("$", "").replace(".00", ""),
					"Endorse Policy Page", "Annual Fee after PB endorsement : " + "$" + s_annualFees, false, false);

			// if condition added
			if (Math.abs(Precision.round(Double.parseDouble(annualSLTFAfterPBEndt2), 2))
					- (expectedannualCalSLTF) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"Actual Annual SLTF after PB endorsement : " + annualSLTFAfterPBEndt2);
				Assertions.passTest("Endorse Policy Page",
						"The Calculated Annual SLTF after PB endorsement : " + "$" + expectedannualCalSLTF);
			} else {
				Assertions.verify(annualSLTFAfterPBEndt2, expectedannualCalSLTF, "Endorse Policy Page",
						"The Difference between actual and calculated SLTF is more than 0.05", false, false);
			}

			Assertions.verify(annualTotalPremiumAfterPBEndt2, df.format(annualTotalPremiumCal), "Endorse Policy Page",
					"Annual Total Premium after PB endorsement : " + "$" + annualTotalPremiumAfterPBEndt2, false,
					false);

			// Getting term values
			String termPremiumAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "4").getData()
					.replace("$", "").replace(",", "");
			String termInspectionFeeAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "4")
					.getData().replace("$", "").replace(",", "");
			String termPolicyFeeAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "4").getData()
					.replace("$", "").replace(",", "");
			String termSLTFAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "4").getData()
					.replace("$", "").replace(",", "");
			String termTotalPremiumAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("7", "4").getData()
					.replace("$", "").replace(",", "");
			String termSurplusContributionAfterPBEndt2 = endorsePolicyPage.premiumDetails.formatDynamicPath("6", "4")
					.getData().replace("$", "").replace(",", "").replace(".00", "");

			// Converting fee double to string
			double d_termFeesPbEndt = Double.parseDouble(termInspectionFeeAfterPBEndt2)
					+ Double.parseDouble(termPolicyFeeAfterPBEndt2);
			String s_termFeesPbEndt = String.valueOf(d_termFeesPbEndt).replace(".0", "");

			// calculating sltf percentage by adding Premium+Fees +Surplus Contribution and
			// multiplying by sltf
			// percentage 0.0485 and stamping fee percentage 0.00075
			double termSurplusTaxAfterPBEndt2 = (Double.parseDouble(termPremiumAfterPBEndt2)
					+ Double.parseDouble(termInspectionFeeAfterPBEndt2) + Double.parseDouble(termPolicyFeeAfterPBEndt2)
					+ Double.parseDouble(termSurplusContributionAfterPBEndt2))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));
			double termStampingFeeAfterPBEndt2 = (Double.parseDouble(annualPremiumAfterPBEndt)
					+ Double.parseDouble(termInspectionFeeAfterPBEndt2) + Double.parseDouble(termPolicyFeeAfterPBEndt2)
					+ Double.parseDouble(termSurplusContributionAfterPBEndt2))
					* Double.parseDouble(testData.get("StampingFeePercentage"));
			double termCalSLTFPbEndt = termSurplusTaxAfterPBEndt2 + termStampingFeeAfterPBEndt2;

			// Calculating Annual total premium = Premium+Fees+SurplusContribution+sltf
			double termCalTotalPremiumPbEndt = Double.parseDouble(annualSLTFAfterPBEndt)
					+ Double.parseDouble(annualSurplusContributionAfterPBEndt)
					+ Double.parseDouble(annualPremiumAfterPBEndt) + Double.parseDouble(annualInspectionFeeAfterPBEndt)
					+ Double.parseDouble(annualPolicyFeeAfterPBEndt);

			// Asserting Term values
			Assertions.verify(termPremiumAfterPBEndt2,
					df.format(d_nbPremium - d_transactionPremiumAfterPBEndt2).replace("$", "").replace(",", "")
							.replace(".00", ""),
					"Endorse Policy Page", "Term Premium after PB endorsement: " + "$" + termPremiumAfterPBEndt2, false,
					false);
			Assertions.verify(s_termFeesPbEndt,
					df.format(d_nbFees - d_transactionFeesAfterPBEndt2).replace("$", "").replace(".00", ""),
					"Endorse Policy Page", "Term Fee after PB endorsement: " + "$" + s_termFeesPbEndt, false, false);
			Assertions.verify(termSurplusContributionAfterPBEndt2,
					df.format(Double.parseDouble(surplusContributionValue)
							- d_transactionSurplusContributionValueAfterPBEndt2).replace("$", "").replace(",", "")
							.replace(".00", ""),
					"Endorse Policy Page", "Term surplus contribution value after PB endorsement : " + "$"
							+ termSurplusContributionAfterPBEndt2,
					false, false);

			double d_termSLTFAfterPBEndt2 = Double.parseDouble(termSLTFAfterPBEndt2.replace("$", ""));
			if (Math.abs(Precision.round(d_termSLTFAfterPBEndt2, 2) - Precision.round(termCalSLTFPbEndt, 2)) < 0.9) {
				Assertions.passTest("Endorse Summary Page",
						"Term SLTF after PB endorsement : " + "$" + d_termSLTFAfterPBEndt2);
			} else {
				Assertions.passTest("Endorse Summary Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.9");
			}

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(termTotalPremiumAfterPBEndt2), 2)
					- Precision.round(termCalTotalPremiumPbEndt, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Policy Page",
						"The actual Term total : " + "$" + termTotalPremiumAfterPBEndt2);
				Assertions.passTest("Endorse Policy Page",
						"The calculated Term total : " + "$" + df.format(termCalTotalPremiumPbEndt));
			} else {
				Assertions.passTest("Endorse Policy Page",
						"The Difference between actual and calculated Term total premium is more than 0.05");
			}

			// click on close button
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page",
					"Clicked on close button successfully and navigate to policy summary paage");

			// click on reverse last endorsement
			policySummaryPage.reversePreviousEndorsementLink.scrollToElement();
			policySummaryPage.reversePreviousEndorsementLink.click();
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Clicked on reverse ednorsement link and navigated to endorse policy details page", false, false);

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Getting transaction/annual/term values
			String transactionPremiumReverseEndtPage = endorseSummaryDetailsPage.premiumDetails
					.formatDynamicPath("1", "2").getData();
			String transactionInspectionFeeReverseEndtPage = endorseSummaryDetailsPage.premiumDetails
					.formatDynamicPath("2", "2").getData();
			String transactionPolicyFeeReverseEndtPage = endorseSummaryDetailsPage.premiumDetails
					.formatDynamicPath("3", "2").getData();
			String transactionSLTFReverseEndtPage = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("4", "2")
					.getData();
			String transactionTotalPremiumReverseEndtPage = endorseSummaryDetailsPage.premiumDetails
					.formatDynamicPath("7", "2").getData();
			String transactionSurplusContributionValueReversEndtPage = endorseSummaryDetailsPage.premiumDetails
					.formatDynamicPath("6", "2").getData();

			String annualPremiumReverseEndtPage = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("1", "3")
					.getData();
			String annualInspectionFeeReverseEndtPage = endorseSummaryDetailsPage.premiumDetails
					.formatDynamicPath("2", "3").getData();
			String annualPolicyFeeReverseEndtPage = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("3", "3")
					.getData();
			String annualSLTFReverseEndtPage = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("4", "3")
					.getData();
			String annualTotalPremiumReverseEndtPage = endorseSummaryDetailsPage.premiumDetails
					.formatDynamicPath("7", "3").getData();
			String annualSurplusContributionValueReverseEndtPage = endorseSummaryDetailsPage.premiumDetails
					.formatDynamicPath("6", "3").getData();

			String termPremiumReverseEndtPage = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("1", "4")
					.getData();
			String termInspectionFeeReverseEndtPage = endorseSummaryDetailsPage.premiumDetails
					.formatDynamicPath("2", "4").getData();
			String termPolicyFeeReverseEndtPage = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("3", "4")
					.getData();
			String termSLTFReverseEndtPage = endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("4", "4")
					.getData();
			String termTotalPremiumReverseEndtPage = endorseSummaryDetailsPage.premiumDetails
					.formatDynamicPath("7", "4").getData().replace("$", "").replace(",", "");
			String termSurplusContributionValueReverseEndtPage = endorseSummaryDetailsPage.premiumDetails
					.formatDynamicPath("6", "4").getData();

			// Asserting Transaction values
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("1", "2").checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"Transaction Premium after Reversal endorsement : " + transactionPremiumReverseEndtPage, false,
					false);
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("2", "2").checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "Transaction Inspection Fees after Reversal endorsement : "
							+ transactionInspectionFeeReverseEndtPage,
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("3", "2").checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"Transaction Policy Fees after Reversal endorsement : " + transactionPolicyFeeReverseEndtPage,
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("6", "2").checkIfElementIsDisplayed(),
					true, "Endorse Summary Page", "Transaction Surplus Contribution Value after Reversal endorsement : "
							+ transactionSurplusContributionValueReversEndtPage,
					false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("4", "2").checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"Transaction SLTF after Reversal endorsement : " + transactionSLTFReverseEndtPage, false, false);
			Assertions.verify(
					endorseSummaryDetailsPage.premiumDetails.formatDynamicPath("7", "2").checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"Transaction Total Premium after Reversal endorsement : " + transactionTotalPremiumReverseEndtPage,
					false, false);

			double d_annualFeesAfterReversalEndt = (Double
					.parseDouble(annualInspectionFeeReverseEndtPage.replace("$", "").replace(",", ""))
					+ Double.parseDouble(annualPolicyFeeReverseEndtPage.replace("$", "").replace(",", "")));

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			BigDecimal AnnualFeesRoundOffAfterReversalEndt = BigDecimal.valueOf(d_annualFeesAfterReversalEndt);
			AnnualFeesRoundOffAfterReversalEndt = AnnualFeesRoundOffAfterReversalEndt.setScale(2, RoundingMode.HALF_UP);
			String annualFeesAfterReversalEndt = format.format(d_annualFeesAfterReversalEndt);

			// Asserting annual values
			Assertions.verify(annualPremiumReverseEndtPage, nbPremium.replace(".00", ""), "Endorse Summary Page",
					"Annual Premium after Reversal endorsement : " + annualPremiumReverseEndtPage, false, false);
			Assertions.verify(annualFeesAfterReversalEndt, nbFees, "Endorse Summary Page",
					"Annual Fee after Reversal endorsement : " + annualFeesAfterReversalEndt, false, false);
			Assertions.verify(annualSurplusContributionValueReverseEndtPage.replace("$", "").replace(",", ""),
					surplusContributionValue, "Endorse Summary Page",
					"Annual Surplus Contribution Value after Reversal endorsement : "
							+ annualSurplusContributionValueReverseEndtPage,
					false, false);
			Assertions.verify(annualSLTFReverseEndtPage, nbSLTF, "Endorse Summary Page",
					"Annual SLTF after Reversal endorsement : " + annualSLTFReverseEndtPage, false, false);
			Assertions.verify(annualTotalPremiumReverseEndtPage, nbTotalPremium, "Endorse Summary Page",
					"Annual Total Premium after Reversal endorsement : " + annualTotalPremiumReverseEndtPage, false,
					false);

			double d_termFeesAfterReversalEndt = (Double
					.parseDouble(termInspectionFeeReverseEndtPage.replace("$", "").replace(",", ""))
					+ Double.parseDouble(termPolicyFeeReverseEndtPage.replace("$", "").replace(",", "")));
			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			BigDecimal TermFeesRoundOffAfterReversalEndt = BigDecimal.valueOf(d_termFeesAfterReversalEndt);
			TermFeesRoundOffAfterReversalEndt = TermFeesRoundOffAfterReversalEndt.setScale(2, RoundingMode.HALF_UP);
			String termFeesAfterReversalEndt = format.format(d_termFeesAfterReversalEndt);

			// Asserting term values
			Assertions.verify(termPremiumReverseEndtPage, nbPremium.replace(".00", ""), "Endorse Summary Page",
					"Term Premium after Reversal endorsement : " + termPremiumReverseEndtPage, false, false);
			Assertions.verify(termFeesAfterReversalEndt, nbFees, "Endorse Summary Page",
					"Term Inspection Fee after Reversal endorsement : " + termFeesAfterReversalEndt, false, false);
			Assertions.verify(termSurplusContributionValueReverseEndtPage.replace("$", "").replace(",", ""),
					surplusContributionValue, "Endorse Summary Page",
					"Term Surplus Contribution Value after Reversal endorsement : "
							+ termSurplusContributionValueReverseEndtPage,
					false, false);
			Assertions.verify(termSLTFReverseEndtPage, nbSLTF, "Endorse Summary Page",
					"Term SLTF after Reversal endorsement : " + termSLTFReverseEndtPage, false, false);
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(termTotalPremiumReverseEndtPage), 2)
					- Precision.round(d_nbtotalPremium, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Summary Page",
						"Calculated Term Total Premium after Reversal endorsement : " + "$"
								+ Precision.round(d_nbtotalPremium, 2));
				Assertions.passTest("Endorse Summary Page", "Actual Term Total Premium after Reversal endorsement : "
						+ "$" + termTotalPremiumReverseEndtPage);

			} else {
				Assertions.passTest("Endorse Summary Page",
						"The Difference between actual and calculated Total premium is more than 0.05");
			}

			// click on close button
			endorseSummaryDetailsPage.closeBtn.scrollToElement();
			endorseSummaryDetailsPage.closeBtn.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC063 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC063 ", "Executed Successfully");
			}
		}
	}
}
