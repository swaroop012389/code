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
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC064_PNBSLTFREG008 extends AbstractNAHOTest {

	public TC064_PNBSLTFREG008() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBSLTFREG008.xls";
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
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		CancelPolicySuccessfulPage cancelPolicySuccessfulPage = new CancelPolicySuccessfulPage();
		ReinstatePolicyPage reinstatePolicyPage = new ReinstatePolicyPage();
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
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
		int dataValue3 = 2;
		Map<String, String> testData = data.get(dataValue1);
		Map<String, String> testData1 = data.get(dataValue2);
		DecimalFormat df = new DecimalFormat("0.00");
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
			// percentage 0.0485 and stamping fee percentage 0.0015
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
			surplustaxesandFees = BigDecimal.valueOf(surplusTax + stampingFee);
			double d_surplustaxesandFees = surplustaxesandFees.doubleValue();

			// getting nb premium and taxes values
			Double nbPremium = Double.parseDouble(premiumAmount);
			Double nbFees = Double.parseDouble(fees);
			Double nbtotalPremium = Double.parseDouble(totalPremiumAmount);
			double nbSLTF = surplusTax + stampingFee;

			// Comparing actual and expected SLTF value
			if (Precision.round(Math.abs(Precision.round(
					Double.parseDouble(accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "")), 2)
					- Precision.round(d_surplustaxesandFees, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees Value : " + accountOverviewPage.sltfValue.getData());
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes and Fees Value : " + "$" + d_surplustaxesandFees);
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated SLTF is more than 0.05");
			}

			// Comparing actual and expected total premium and fees value
			String calcPremAmt = df
					.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxesandFees + "")
							+ (Double.parseDouble(fees) + Double.parseDouble(surplusContributionValue))));
			if (Precision.round(Math.abs(Precision
					.round(Double.parseDouble(
							accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", "")), 2)
					- Precision.round(Double.parseDouble(calcPremAmt), 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual Total Premium Value : " + accountOverviewPage.totalPremiumValue.getData());
				Assertions.passTest("Account Overview Page", "Calculated Total Premium Value : " + "$"
						+ Precision.round(Double.parseDouble(calcPremAmt), 2));
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated SLTF is more than 0.05");
			}

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
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on change coverage options
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();

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

			// Getting Adjusted Premium from Cancellation screen
			String adjustedPremium = endorsePolicyPage.premiumDetails.formatDynamicPath("1", "2").getData()
					.replace("$", "").replace("-", "").replace(",", "");
			// Getting Adjusted Inspection fee from Cancellation screen
			String adjustedInspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "2").getData()
					.replace("$", "");
			// Getting Adjusted Policy fee from Cancellation screen
			String adjustedPolicyFee = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "2").getData()
					.replace("$", "");
			// Getting Adjusted sltf value from Cancellation screen
			String adjustedSLTFValue = endorsePolicyPage.premiumDetails.formatDynamicPath("4", "2").getData()
					.replaceAll("[^\\d-.]", "");
			// Getting Adjusted sltf value from Cancellation screen

			String adjustedSuplusContributionValue = endorsePolicyPage.premiumDetails.formatDynamicPath("5", "2")
					.getData().replaceAll("[^\\d-.]", "");
			// Getting Adjusted sltf value from Cancellation screen
			String adjustedtTotalPremium = endorsePolicyPage.premiumDetails.formatDynamicPath("6", "2").getData()
					.replaceAll("[^\\d-.]", "");

			// Getting Updated Inspection fee from Cancellation screen
			String updatedInspectionFee = endorsePolicyPage.premiumDetails.formatDynamicPath("2", "3").getData()
					.replace("$", "").replace(",", "");
			// Getting Updated Policy fee from Cancellation screen
			String updatedPolicyFee = endorsePolicyPage.premiumDetails.formatDynamicPath("3", "3").getData()
					.replace("$", "").replace(",", "");

			// Conversion of String to double/int to calculate sltf
			double d_adjustedPremium = Double.parseDouble(adjustedPremium);
			double d_adjustedFees = (Double.parseDouble(adjustedInspectionFee) + Double.parseDouble(adjustedPolicyFee));
			double d_adjustedSLTFValue = Double.parseDouble(adjustedSLTFValue);
			double d_adjustedtTotalPremium = Double.parseDouble(adjustedtTotalPremium);
			double d_updatedPremium = nbPremium - d_adjustedPremium;
			double d_updatedFees = (Double.parseDouble(updatedInspectionFee) + Double.parseDouble(updatedPolicyFee));
			double d_updatedSLTFValue = nbSLTF - d_adjustedSLTFValue;
			double d_UpdatedTotalPremium = nbtotalPremium - d_adjustedtTotalPremium;
			double d_adjustedSuplusContributionValue = (Double.parseDouble(adjustedSuplusContributionValue));

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			BigDecimal PremiumRoundOffAdjusted = BigDecimal.valueOf(d_adjustedPremium);
			PremiumRoundOffAdjusted = PremiumRoundOffAdjusted.setScale(2, RoundingMode.HALF_UP);
			String adjustedPremiumAfterEndt = format.format(d_adjustedPremium);

			// Rounding Off SurplusContribution value decimal value to 2 digits and
			// Converting back to String
			BigDecimal suplusContributionValueAdjusted = BigDecimal.valueOf(d_adjustedSuplusContributionValue);
			suplusContributionValueAdjusted = suplusContributionValueAdjusted.setScale(2, RoundingMode.HALF_UP);
			String suplusContributionValueAfterEndt = format.format(d_adjustedSuplusContributionValue);

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			BigDecimal FeesRoundOffAdjusted = BigDecimal.valueOf(d_adjustedFees);
			FeesRoundOffAdjusted = FeesRoundOffAdjusted.setScale(2, RoundingMode.HALF_UP);
			String adjustedFeesAfterEndt = format.format(d_adjustedFees);

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			BigDecimal SLTFRoundOffAdjusted = BigDecimal.valueOf(d_adjustedSLTFValue);
			SLTFRoundOffAdjusted = SLTFRoundOffAdjusted.setScale(2, RoundingMode.HALF_UP);
			String adjustedSLTFAfterEndt = format.format(d_adjustedSLTFValue);

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			BigDecimal TotalPremiumRoundOffAdjusted = BigDecimal.valueOf(d_adjustedtTotalPremium);
			TotalPremiumRoundOffAdjusted = TotalPremiumRoundOffAdjusted.setScale(2, RoundingMode.HALF_UP);
			String adjustedTotalPremiumAfterEndt = format.format(d_adjustedtTotalPremium);

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			BigDecimal PremiumRoundOffUpdated = BigDecimal.valueOf(d_updatedPremium);
			PremiumRoundOffUpdated = PremiumRoundOffUpdated.setScale(2, RoundingMode.HALF_UP);
			String updatedPremiumAfterEndt = format.format(d_updatedPremium);

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			BigDecimal FeesRoundOffUpdated = BigDecimal.valueOf(d_updatedFees);
			FeesRoundOffUpdated = FeesRoundOffUpdated.setScale(2, RoundingMode.HALF_UP);
			String updatedFeesAfterEndt = format.format(d_updatedFees);

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			BigDecimal SLTFRoundOffUpdated = BigDecimal.valueOf(d_updatedSLTFValue);
			SLTFRoundOffUpdated = SLTFRoundOffUpdated.setScale(2, RoundingMode.HALF_UP);
			String updatedSLTFAfterEndt = format.format(d_updatedSLTFValue);

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			BigDecimal TotalPremiumRoundOffUpdated = BigDecimal.valueOf(d_UpdatedTotalPremium);
			TotalPremiumRoundOffUpdated = TotalPremiumRoundOffUpdated.setScale(2, RoundingMode.HALF_UP);
			String updatedTotalPremiumAfterEndt = format.format(d_UpdatedTotalPremium);
			Assertions.passTest("Endorse Policy Page",
					"Adjusted Premium value after endorsement is " + "-" + adjustedPremiumAfterEndt);
			Assertions.passTest("Endorse Policy Page",
					"Adjusted Fees value after endorsement is " + adjustedFeesAfterEndt);
			Assertions.passTest("Endorse Policy Page",
					"Adjusted SLTF value after endorsement is " + adjustedSLTFAfterEndt);
			Assertions.passTest("Endorse Policy Page",
					"Adjusted Total Premium value after endorsement is " + adjustedTotalPremiumAfterEndt);
			Assertions.passTest("Endorse Policy Page",
					"Adjusted Surplus Contribution value after endorsement is " + suplusContributionValueAfterEndt);
			Assertions.verify(format.format(nbPremium - d_adjustedPremium), updatedPremiumAfterEndt,
					"Endorse Policy Page", "Updated Premium value after endorsement is " + updatedPremiumAfterEndt,
					false, false);
			Assertions.verify(format.format(nbFees - d_adjustedFees), updatedFeesAfterEndt, "Endorse Policy Page",
					"Updated Fees value after endorsement is " + updatedFeesAfterEndt, false, false);
			Assertions.verify(format.format(nbSLTF - d_adjustedSLTFValue), updatedSLTFAfterEndt, "Endorse Policy Page",
					"Updated SLTF value after endorsement is " + updatedSLTFAfterEndt, false, false);
			Assertions.verify(format.format(nbtotalPremium - d_adjustedtTotalPremium), updatedTotalPremiumAfterEndt,
					"Endorse Policy Page",
					"Updated Total Premium value after endorsement is " + updatedTotalPremiumAfterEndt, false, false);

			// click on complete
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Asserting updated values on endorsment successful page
			Assertions.passTest("Endorse Summary Page",
					"Adjusted Premium value after endorsement is " + "-" + adjustedPremiumAfterEndt);
			Assertions.passTest("Endorse Summary Page",
					"Adjusted Fees value after endorsement is " + adjustedFeesAfterEndt);
			Assertions.passTest("Endorse Summary Page",
					"Adjusted SLTF value after endorsement is " + "-" + adjustedSLTFAfterEndt);
			Assertions.passTest("Endorse Summary Page",
					"Adjusted Total Premium value after endorsement is " + "-" + adjustedTotalPremiumAfterEndt);
			Assertions.verify(format.format(nbPremium - d_adjustedPremium), updatedPremiumAfterEndt,
					"Endorse Summary Page", "Updated Premium value after endorsement is " + updatedPremiumAfterEndt,
					false, false);
			Assertions.verify(format.format(nbFees - d_adjustedFees), updatedFeesAfterEndt, "Endorse Summary Page",
					"Updated Fees value after endorsement is " + updatedFeesAfterEndt, false, false);
			Assertions.verify(format.format(nbSLTF - d_adjustedSLTFValue), updatedSLTFAfterEndt, "Endorse Summary Page",
					"Updated SLTF value after endorsement is " + updatedSLTFAfterEndt, false, false);
			Assertions.verify(format.format(nbtotalPremium - d_adjustedtTotalPremium), updatedTotalPremiumAfterEndt,
					"Endorse Summary Page",
					"Updated Total Premium value after endorsement is " + updatedTotalPremiumAfterEndt, false, false);

			// click on close button
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// click on cancel policy
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();

			// enter cancellation details
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
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Getting Earned Premium from Cancellation screen
			String earnedPremium = cancelPolicyPage.newPremium.getData();
			// Getting Earned Inspection fee from Cancellation screen
			String earnedInspectionFee = cancelPolicyPage.newInspectionFee.getData();
			// Getting Earned Policy fee from Cancellation screen
			String earnedPolicyFee = cancelPolicyPage.newPolicyFee.getData();
			// Getting Earned sltf value from Cancellation screen
			String earnedSLTFValue1 = cancelPolicyPage.newSLTF.getData();
			// Getting Returned Premium from Cancellation screen
			String returnedPremium = cancelPolicyPage.returnedPremium.getData().replaceAll("[^\\d-.]", "");
			// Getting Returned Inspection fee from Cancellation screen
			String returnedInspectionFee = cancelPolicyPage.returnedInspectionFee.getData().replace("$", "");
			// Getting Returned Policy fee from Cancellation screen
			String returnedPolicyFee = cancelPolicyPage.returnedPolicyFee.getData().replace("$", "");
			// Getting Returned sltf value from Cancellation screen
			String returnedSLTFValue = cancelPolicyPage.returnedSLTF.getData();
			// Getting Returned surplusContribution value from Cancellation screen
			String returnedSurpluscontributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(3)
					.getData().replaceAll("[^\\d-.]", "");

			// converting stings to doubles
			double d_returnedPremium = Double.parseDouble(returnedPremium);
			double d_returnedInspectionFee = (Double.parseDouble(returnedInspectionFee)
					+ Double.parseDouble(returnedPolicyFee));
			double d_returnedSurpluscontributionValue = Double.parseDouble(returnedSurpluscontributionValue);
			double d_stampinFee = (d_returnedPremium + d_returnedInspectionFee + d_returnedSurpluscontributionValue)
					* Double.parseDouble(testData.get("StampingFeePercentage"));
			double d_returnedSLTFValue = ((d_returnedPremium + d_returnedInspectionFee
					+ d_returnedSurpluscontributionValue)
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"))) + d_stampinFee;

			//
			BigDecimal ReturnedPremiumRoundOff = BigDecimal.valueOf(d_returnedPremium);
			ReturnedPremiumRoundOff = ReturnedPremiumRoundOff.setScale(2, RoundingMode.HALF_UP);
			String returnedPremiumValue = format.format(d_returnedPremium);
			//
			BigDecimal ReturnedFeesRoundOff = BigDecimal.valueOf(d_returnedInspectionFee);
			ReturnedFeesRoundOff = ReturnedFeesRoundOff.setScale(2, RoundingMode.HALF_UP);
			String returnedFeesValue = format.format(d_returnedInspectionFee);
			//
			BigDecimal ReturnedSLTFRoundOff = BigDecimal.valueOf(d_returnedSLTFValue);
			ReturnedSLTFRoundOff = ReturnedSLTFRoundOff.setScale(2, RoundingMode.HALF_UP);
			// String returnedSLTFValue1 = format.format(d_returnedSLTFValue);

			// Asserting values on cancel Policy page page
			Assertions.passTest("Cancel Policy Page", "Earned premium value after flat cancel is " + earnedPremium);
			Assertions.passTest("Cancel Policy Page", "Earned fees value after flat cancel is $"
					+ Double.parseDouble(earnedInspectionFee) + Double.parseDouble(earnedPolicyFee));
			Assertions.passTest("Cancel Policy Page", "Earned SLTF value after flat cancel is " + earnedSLTFValue1);
			Assertions.verify(returnedPremiumValue.replace("(", "").replace(")", "").replace("-", ""),
					updatedPremiumAfterEndt.replace("(", "").replace(")", "").replace("-", ""), "Cancel Policy Page",
					"Returned Premium value after flat cancel is  " + "-" + updatedPremiumAfterEndt, false, false);
			Assertions.verify(returnedFeesValue.replace("(", "").replace(")", "").replace("-", ""),
					updatedFeesAfterEndt.replace("(", "").replace(")", "").replace("-", ""), "Cancel Policy Page",
					"Returned Fees value after flat cancel is  " + "-" + updatedFeesAfterEndt, false, false);

			double d_calculatedreturnedSLTFValue = Double.parseDouble(format.format(d_returnedSLTFValue)
					.replace(",", "").replace("(", "").replace(")", "").replace("-", "").replace("$", ""));

			double d_actualreturnedSLTFValue = Double
					.parseDouble(returnedSLTFValue.replace("(", "").replace(")", "").replace("-", "").replace("$", ""));
			if (Precision.round(Math.abs(
					Precision.round(d_actualreturnedSLTFValue, 2) - Precision.round(d_calculatedreturnedSLTFValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page",
						"Returned SLTF value after flat cancel is " + "$" + d_actualreturnedSLTFValue);
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			// cancel policy page
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();

			cancelPolicyPage.waitTime(2);
			// Asserting values on cancel successfull page
			Assertions.verify(cancelPolicySuccessfulPage.newPremium.getData().contains(earnedPremium), true,
					"Cancel Policy Successfull Page",
					"Earned Premium value after flat cancel is " + cancelPolicySuccessfulPage.newPremium.getData(),
					false, false);
			Assertions.verify(cancelPolicySuccessfulPage.newInspectionFee.getData().contains(earnedInspectionFee), true,
					"Cancel Policy Successfull Page", "Earned Inspection fee value after flat cancel is "
							+ cancelPolicySuccessfulPage.newInspectionFee.getData(),
					false, false);
			Assertions.verify(cancelPolicySuccessfulPage.newPolicyFee.getData().contains(earnedPolicyFee), true,
					"Cancel Policy Successfull Page",
					"Earned Policy fee value after flat cancel is " + cancelPolicySuccessfulPage.newPolicyFee.getData(),
					false, false);
			Assertions.verify(cancelPolicySuccessfulPage.newSLTF.getData().contains(earnedSLTFValue1), true,
					"Cancel Policy Successfull Page",
					"Earned SLTF value after flat cancel is " + cancelPolicySuccessfulPage.newSLTF.getData(), false,
					false);
			Assertions.verify(cancelPolicySuccessfulPage.returnedPremium.getData(),
					"-" + updatedPremiumAfterEndt.replace(",", "").replace(".00", ""), "Cancel Policy Successfull Page",
					"Returned Premium value after flat cancel is  "
							+ cancelPolicySuccessfulPage.returnedPremium.getData(),
					false, false);
			Assertions.verify(cancelPolicySuccessfulPage.returnedInspectionFee.getData(), "-$" + updatedInspectionFee,
					"Cancel Policy Successfull Page", "Returned Inspection Fees value after flat cancel is  "
							+ cancelPolicySuccessfulPage.returnedInspectionFee.getData(),
					false, false);
			Assertions.verify(cancelPolicySuccessfulPage.returnedPolicyFee.getData(), "-$" + updatedPolicyFee,
					"Cancel Policy Successfull Page", "Returned Policy Fees value after flat cancel is  "
							+ cancelPolicySuccessfulPage.returnedPolicyFee.getData(),
					false, false);
			String actualReturnedSltf = cancelPolicySuccessfulPage.returnedSLTF.getData().replace("(", "")
					.replace(")", "").replace("-", "").replace("$", "");
			double d_actualReturnedSltf = Double.parseDouble(actualReturnedSltf);
			double d_calculatedreturnedsltfvalue = Double.parseDouble(df.format(d_returnedSLTFValue).replace(",", "")
					.replace("(", "").replace(")", "").replace("-", "").replace("$", ""));

			if (Precision.round(Math
					.abs(Precision.round(d_actualReturnedSltf, 2) - Precision.round(d_calculatedreturnedsltfvalue, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page", "Returned SLTF value after flat cancel is "
						+ cancelPolicySuccessfulPage.returnedSLTF.getData());
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();

			// Reinstate policy
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			reinstatePolicyPage.completeReinstatement.scrollToElement();
			reinstatePolicyPage.completeReinstatement.click();
			reinstatePolicyPage.closeButton.scrollToElement();
			reinstatePolicyPage.closeButton.click();

			// click on cancel policy
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();

			testData = data.get(dataValue3);
			// enter cancellation details
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
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Getting Returned Premium from Cancellation screen
			String returnedPremiumNonFlat = cancelPolicyPage.returnedPremium.getData().replaceAll("[^\\d-.]", "");
			// Getting Returned Inspection fee from Cancellation screen
			String returnedInspectionFeeNonFlat = cancelPolicyPage.returnedInspectionFee.getData().replace("$", "");
			// Getting Returned Policy fee from Cancellation screen
			String returnedPolicyFeeNonFlat = cancelPolicyPage.returnedPolicyFee.getData().replace("$", "");
			// Getting Returned sltf value from Cancellation screen
			String returnedSLTFValueNonFlat = cancelPolicyPage.returnedSLTF.getData();
			// Getting Returned SurplusContribution value from Cancellation screen
			String returnedSurplusContributionValueNonFlat = cancelPolicyPage.surplusContributionVal
					.formatDynamicPath(3).getData().replaceAll("[^\\d-.]", "");

			// converting stings to doubles
			double d_returnedPremiumNonFlat = Double.parseDouble(returnedPremiumNonFlat);
			double d_returnedInspectionFeeNonFlat = (Double.parseDouble(returnedInspectionFeeNonFlat)
					+ Double.parseDouble(returnedPolicyFeeNonFlat));
			double d_returnedSurplusContributionValueNonFlat = Double
					.parseDouble(returnedSurplusContributionValueNonFlat);
			double d_stampinFeeNonFlat = (d_returnedPremiumNonFlat + d_returnedInspectionFeeNonFlat
					+ d_returnedSurplusContributionValueNonFlat)
					* Double.parseDouble(testData.get("StampingFeePercentage"));
			double d_returnedSLTFValueNonFlat = ((d_returnedPremiumNonFlat + d_returnedInspectionFeeNonFlat
					+ d_returnedSurplusContributionValueNonFlat)
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"))) + d_stampinFeeNonFlat;

			BigDecimal ReturnedSLTFRoundOffNonFlat = BigDecimal.valueOf(d_returnedSLTFValueNonFlat);
			ReturnedSLTFRoundOffNonFlat = ReturnedSLTFRoundOffNonFlat.setScale(2, RoundingMode.HALF_UP);
			// String returnedSLTFValue1NonFlat = format.format(d_returnedSLTFValueNonFlat);

			String actualreturnedSLTFValueNonFlat = returnedSLTFValueNonFlat.replace("(", "").replace(")", "")
					.replace("-", "").replace("$", "");
			double d_actualreturnedSLTFValueNonFlat = Double.parseDouble(actualreturnedSLTFValueNonFlat);
			double d_calculatedreturnedSLTFValueNonFlat = Double.parseDouble(format.format(d_returnedSLTFValueNonFlat)
					.replace(",", "").replace("(", "").replace(")", "").replace("-", "").replace("$", ""));

			if (Precision.round(Math.abs(Precision.round(d_actualreturnedSLTFValueNonFlat, 2)
					- Precision.round(d_calculatedreturnedSLTFValueNonFlat, 2)), 2) < 0.05) {
				Assertions.passTest("Cancel Policy Page",
						"Returned SLTF value after Non flat cancel is " + "$" + actualreturnedSLTFValueNonFlat);
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			// cancel policy page
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC064 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC064 ", "Executed Successfully");
			}
		}
	}
}
