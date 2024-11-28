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
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC066_PNBSLTFREG010 extends AbstractNAHOTest {

	public TC066_PNBSLTFREG010() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBSLTFREG010.xls";
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
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewPolicySnapShot viewPolicySnapshot = new ViewPolicySnapShot();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();

		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		String quoteNumber;
		String policyNumber;
		String premiumAmount;
		String inspectionFee;
		String policyFee;
		String surplusContributionValue;
		double surplusTax;
		double stampingFee;
		double MWUAFee;
		String fees;
		BigDecimal surplustaxes;
		BigDecimal stampingtaxes;
		BigDecimal mwuataxes;
		BigDecimal surplustaxesandFees;
		String sltf;
		String totalPremium;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		Map<String, String> testData = data.get(dataValue1);
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

			// Getting the premium,fees, surplusContributionValue and sltf values from
			// premium section in Account
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

			// getting surplusContributionValue
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page",
					"Surplus contribution value is " + accountOverviewPage.surplusContibutionValue.getData());

			// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
			// percentage 0.04
			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

			// calculating stamping percentage by adding Premium+Fees and multiplying by
			// stamping percentage 0.0025
			stampingFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees))
					* Double.parseDouble(testData.get("StampingFeePercentage"));

			// Rounding stamping decimal value to 2 digits
			stampingtaxes = BigDecimal.valueOf(stampingFee);
			stampingtaxes = stampingtaxes.setScale(2, RoundingMode.HALF_UP);

			// calculating MWUA percentage by adding Premium+Fees and multiplying by mwua
			// percentage 0.03
			MWUAFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees))
					* Double.parseDouble(testData.get("MWUAFeePercentage"));

			// Rounding MWUA decimal value to 2 digits
			mwuataxes = BigDecimal.valueOf(MWUAFee);
			mwuataxes = mwuataxes.setScale(2, RoundingMode.HALF_UP);

			// Rounding Surplus Lines,Taxes and Fees decimal value to 2 digits
			surplustaxesandFees = BigDecimal.valueOf(surplusTax + stampingFee + MWUAFee);
			double surplustaxesandFees1 = BigDecimal.valueOf(surplusTax + stampingFee + MWUAFee).doubleValue();
			double expectedSurplustaxesandFees1 = Double.parseDouble(df.format(surplustaxesandFees1));

			// Comparing actual and expected SLTF value and printing calculated value
			// Retrieve sltfValue
			// Adding if condition for calculating difference between actual and expected
			// value
			// Validating both expected and actual value difference is less than 0.05

			String sltfValue = accountOverviewPage.sltfValue.getData().replace(",", "").replace("$", "");
			double d_actualSltf = Double.parseDouble(sltfValue);
			if (Precision.round(
					Math.abs(Precision.round(d_actualSltf, 2) - Precision.round(expectedSurplustaxesandFees1, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Calculated Surplus Lines Taxes and Fees : " + "$"
						+ Precision.round(expectedSurplustaxesandFees1, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_actualSltf);
			} else {

				Assertions.verify(d_actualSltf, expectedSurplustaxesandFees1, "Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);

			}

			// Comparing actual and expected total premium and fees value and printing
			// calculated and actual value
			// Retrieve totalPremiumValue and expected value with converting into string and
			// double
			// Adding if condition for calculating difference between actual and expected
			// value
			// Validating both expected and actual value difference is less than 0.05

			String totalPremiumValue = accountOverviewPage.totalPremiumValue.getData().replace(",", "").replace("$",
					"");
			double d_totalPremiumValue = Double.parseDouble(totalPremiumValue);
			double expectedValue = Double.parseDouble(
					df.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxesandFees + "")
							+ (Double.parseDouble(fees) + Double.parseDouble(surplusContributionValue)))));
			if (Precision.round(Math.abs(Precision.round(d_totalPremiumValue, 2) - Precision.round(expectedValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated total premium : " + "$" + Precision.round(expectedValue, 2));
				Assertions.passTest("Account Overview Page", "Actual total premium : " + "$" + d_totalPremiumValue);
			} else {

				Assertions.verify(d_totalPremiumValue, expectedValue, "Account Overview Page",
						"The Difference between actual and calculated Premium is more than 0.05", false, false);

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

			// Getting the Policy Number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary", "Policy Number is : " + policyNumber);

			// clicking on View Policy Snapshot link
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);

			// Comparing actual and expected SLTF value and printing actual and expected
			// value
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace(",", ""),
					format.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
					"Calculated Surplus Fees : " + format.format(surplustaxes).replace(",", ""), false, false);
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace(",", ""),
					format.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
					"Actual Surplus Fees : " + viewPolicySnapshot.surplusLinesTaxesValue.getData(), false, false);
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace(",", ""),
					format.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
					"Surplus Fees calculated as per Surplus Lines Taxes Percentage 4% for MS is verified", false,
					false);

			// Comparing actual and expected Stamping fee value and printing actual and
			// expected value
			Assertions.verify(viewPolicySnapshot.stampingFeeValue.getData(),
					format.format(stampingtaxes).replace(",", ""), "View Policy Snapshot Page",
					"Calculated Stamping Fees : " + format.format(stampingtaxes).replace(",", ""), false, false);
			Assertions.verify(viewPolicySnapshot.stampingFeeValue.getData(),
					format.format(stampingtaxes).replace(",", ""), "View Policy Snapshot Page",
					"Actual Stamping Fees : " + viewPolicySnapshot.stampingFeeValue.getData(), false, false);
			Assertions.verify(viewPolicySnapshot.stampingFeeValue.getData(),
					format.format(stampingtaxes).replace(",", ""), "View Policy Snapshot Page",
					"Stamping Fees calculated as per Stamping Fees Percentage 0.25% for MS is verified", false, false);

			// Comparing actual and expected MWUA fee value and printing actual and expected
			// value
			Assertions.verify(viewPolicySnapshot.mwuaValue.getData(), format.format(mwuataxes).replace(",", ""),
					"View Policy Snapshot Page", "Calculated MWUA Fees : " + format.format(mwuataxes).replace(",", ""),
					false, false);
			Assertions.verify(viewPolicySnapshot.mwuaValue.getData(), format.format(mwuataxes).replace(",", ""),
					"View Policy Snapshot Page", "Actual MWUA Fees : " + viewPolicySnapshot.mwuaValue.getData(), false,
					false);
			Assertions.verify(viewPolicySnapshot.mwuaValue.getData(), format.format(mwuataxes).replace(",", ""),
					"View Policy Snapshot Page",
					"MWUA Fees calculated as per MWUA Fee Percentage 3% for MS is verified", false, false);
			viewPolicySnapshot.scrollToTopPage();
			viewPolicySnapshot.waitTime(3);
			viewPolicySnapshot.goBackButton.click();

			// click on Rewrite Policy Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on EndorsePB link");

			// Setting Endorsement effective date
			testData = data.get(dataValue2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on change coverage options link successfully");

			// update coverage values
			createQuotePage.enterInsuredValuesNAHO(testData, dataValue2, dataValue2);
			Assertions.passTest("Create Quote Page", "Coverage values updated successfully");

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

			// click on next
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on next button successfully");

			// click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// click on complete
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			// click on close button
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfull");

			// Assertions transaction on policy summary page
			Assertions.verify(
					policySummaryPage.transactionType.formatDynamicPath(testData.get("TransactionType"))
							.checkIfElementIsDisplayed(),
					true, "Policy Summary Page",
					policySummaryPage.transactionType.formatDynamicPath(testData.get("TransactionType")).getData()
							+ " transaction is displayed under transaction history",
					false, false);

			// click on Rewrite Policy Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on EndorsePB link");

			testData = data.get(dataValue3);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page",
					"Clicked on edit location building information link successfull");

			// Update construction type and roof details
			dwellingPage.constructionTypeArrow.waitTillVisibilityOfElement(60);
			dwellingPage.constructionTypeArrow.scrollToElement();
			dwellingPage.constructionTypeArrow.click();
			dwellingPage.constructionTypeOption.formatDynamicPath(testData.get("L1D1-DwellingConstType"))
					.waitTillVisibilityOfElement(60);
			dwellingPage.constructionTypeOption.formatDynamicPath(testData.get("L1D1-DwellingConstType"))
					.scrollToElement();
			dwellingPage.constructionTypeOption.formatDynamicPath(testData.get("L1D1-DwellingConstType")).click();

			dwellingPage.roofDetailsLink.scrollToElement();
			dwellingPage.roofDetailsLink.click();
			dwellingPage.waitTime(2);
			dwellingPage.roofShapeArrow.waitTillVisibilityOfElement(60);
			dwellingPage.roofShapeArrow.scrollToElement();
			dwellingPage.roofShapeArrow.click();
			dwellingPage.roofShapeOption.formatDynamicPath(testData.get("L1D1-DwellingRoofShape"))
					.waitTillVisibilityOfElement(60);
			dwellingPage.roofShapeOption.formatDynamicPath(testData.get("L1D1-DwellingRoofShape")).scrollToElement();
			dwellingPage.roofShapeOption.formatDynamicPath(testData.get("L1D1-DwellingRoofShape")).click();
			dwellingPage.roofCladdingArrow.waitTillVisibilityOfElement(60);
			dwellingPage.roofCladdingArrow.scrollToElement();
			dwellingPage.roofCladdingArrow.click();
			dwellingPage.roofCladdingOption.formatDynamicPath(testData.get("L1D1-DwellingRoofCladding"))
					.waitTillVisibilityOfElement(60);
			dwellingPage.roofCladdingOption.formatDynamicPath(testData.get("L1D1-DwellingRoofCladding"))
					.scrollToElement();
			dwellingPage.roofCladdingOption.formatDynamicPath(testData.get("L1D1-DwellingRoofCladding")).click();
			Assertions.passTest("Dwelling Page", "Update construction type and roof details successfull");

			dwellingPage.scrollToBottomPage();
			dwellingPage.waitTime(2);
			dwellingPage.continueButton.scrollToElement();
			dwellingPage.continueButton.click();

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
			Assertions.passTest("Endorse Policy Page", "Clicked on continue button successfully");

			// click on next
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			Assertions.passTest("Endorse Policy Page", "Clicked on next button successfully");

			// click on complete
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			// click on close button
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close successfull");

			// Asserting transaction on policy summary page
			Assertions.verify(
					policySummaryPage.transactionType.formatDynamicPath(testData.get("TransactionType"))
							.checkIfElementIsDisplayed(),
					true, "Policy Summary Page",
					policySummaryPage.transactionType.formatDynamicPath(testData.get("TransactionType")).getData()
							+ " transaction is displayed under transaction history",
					false, false);

			// click on cancel policy
			testData = data.get(dataValue1);
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on cancel link successfully");

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
			Assertions.passTest("Cancel Policy Page", "Clicked on next button successfully");

			// cancel policy page
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancel Policy Page", "Cancellation processed successfully");

			// Assertions transaction on policy summary page
			Assertions.verify(
					policySummaryPage.transactionType.formatDynamicPath(testData.get("TransactionType"))
							.checkIfElementIsDisplayed(),
					true, "Policy Summary Page",
					policySummaryPage.transactionType.formatDynamicPath(testData.get("TransactionType")).getData()
							+ " transaction is displayed under transaction history",
					false, false);

			policySummaryPage.transHistReason.formatDynamicPath("5").waitTillVisibilityOfElement(60);
			policySummaryPage.transHistReason.formatDynamicPath("5").scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath("5").click();

			Assertions.addInfo("Policy Summary Page", "Cancelled Policy SLTF Verification");
			premiumAmount = policySummaryPage.transactionPremium.getData().replaceAll("[^\\d-.]", "");
			inspectionFee = policySummaryPage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			policyFee = policySummaryPage.policyFee.getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = policySummaryPage.transactionSurplusContribution.getData().replaceAll("[^\\d-.]",
					"");

			// Getting the premium,fees,SurplusContributionand sltf values from premium
			// section in Policy
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
					+ Double.parseDouble(policyFee)) * Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

			// calculating stamping percentage by adding
			// Premium+Fees+surplusContributionValue and multiplying by
			// stamping percentage 0.0025
			stampingFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee)) * Double.parseDouble(testData.get("StampingFeePercentage"));

			// Rounding stamping decimal value to 2 digits
			stampingtaxes = BigDecimal.valueOf(stampingFee);
			stampingtaxes = stampingtaxes.setScale(2, RoundingMode.HALF_UP);

			// calculating MWUA percentage by adding Premium+Fees and multiplying by mwua
			// percentage 0.03
			MWUAFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee)) * Double.parseDouble(testData.get("MWUAFeePercentage"));

			// Rounding MWUA decimal value to 2 digits
			mwuataxes = BigDecimal.valueOf(MWUAFee);
			mwuataxes = mwuataxes.setScale(2, RoundingMode.HALF_UP);

			// Rounding Surplus Lines,Taxes and Fees decimal value to 2 digits
			surplustaxesandFees = BigDecimal.valueOf(surplusTax + stampingFee + MWUAFee);
			surplustaxesandFees = surplustaxesandFees.setScale(2, RoundingMode.HALF_UP);
			sltf = surplustaxesandFees.toString();

			// Comparing actual and expected SLTF value and printing calculated value
			String actualSltfPolicySummary = policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData()
					.replace("(", "").replace(")", "").replace("-", "").replace("$", "").replace(",", "");
			double d_actualSltfPolicySummary = Double.parseDouble(actualSltfPolicySummary);
			String calcSltfPolicySummary = format.format(Double.parseDouble(sltf)).replace("(", "").replace(")", "")
					.replace("-", "").replace("$", "").replace(",", "");
			double d_calcSltfPolicySummary = Double.parseDouble(calcSltfPolicySummary);

			if (Precision.round(Math
					.abs(Precision.round(d_actualSltfPolicySummary, 2) - Precision.round(d_calcSltfPolicySummary, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Calculated Surplus Lines Taxes and Fees : " + "$"
						+ Precision.round(d_calcSltfPolicySummary, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_actualSltfPolicySummary);
			} else {

				Assertions.verify(d_actualSltfPolicySummary, d_calcSltfPolicySummary, "Policy Summary Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);

			}

			// Comparing actual and expected total premium and fees value and printing
			// calculated value

			totalPremium = "" + (Double.parseDouble(premiumAmount) + Double.parseDouble(surplustaxesandFees + "")
					+ Double.parseDouble(inspectionFee) + Double.parseDouble(policyFee)
					+ Double.parseDouble(surplusContributionValue));
			String actualTotalPremiumPS = policySummaryPage.policyTotalPremium.getData().replace("(", "")
					.replace(")", "").replace("-", "").replace("$", "").replace(",", "");
			double d_actualTotalPremiumPS = Double.parseDouble(actualTotalPremiumPS);
			String calcTotaPremiumPS = format.format(Double.parseDouble(totalPremium)).replace("(", "").replace(")", "")
					.replace("-", "").replace("$", "").replace(",", "");
			double d_calcTotaPremiumPS = Double.parseDouble(calcTotaPremiumPS);

			if (Precision.round(
					Math.abs(Precision.round(d_actualTotalPremiumPS, 2) - Precision.round(d_calcTotaPremiumPS, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Calculated total premium : " + "$" + Precision.round(expectedValue, 2));
				Assertions.passTest("Policy Summary Page", "Actual total premium : " + "$" + d_calcTotaPremiumPS);
			} else {

				Assertions.verify(d_actualTotalPremiumPS, d_calcTotaPremiumPS, "Policy Summary Page",
						"The Difference between actual and calculated Premium is more than 0.05", false, false);

			}
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC066 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC066 ", "Executed Successfully");
			}
		}
	}
}
