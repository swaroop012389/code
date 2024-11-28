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
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC058_PNBSLTFREG002 extends AbstractNAHOTest {

	public TC058_PNBSLTFREG002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBSLTFREG002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewPolicySnapShot viewPolicySnapshot = new ViewPolicySnapShot();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

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

		// String sltf;
		int dataValue1 = 0;
		int dataValue2 = 1;
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
			// Adding this condition to handle deductible minimum warning
			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}

			// Getting the quote number from account overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Getting the premium,fees, surplus ContributionValue and sltf values from
			// premium section in Account
			// Overview page
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Value is : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Fees Value is : " + accountOverviewPage.feesValue.getData(), false, false);
			Assertions.verify(accountOverviewPage.surplusContibutionValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Surplus Contribution Value is : " + accountOverviewPage.surplusContibutionValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// getting premium amount
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// getting fees value
			fees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");

			// getting surplus ContributionValue value
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page",
					"Surplus Contribution Value " + "$" + surplusContributionValue);

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
			double d_stampingtaxes = stampingtaxes.doubleValue();
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
			double d_surplustaxesandFeesAcc = surplustaxesandFees.doubleValue();

			String actualSltfValue = accountOverviewPage.sltfValue.getData().replace(",", "").replace("$", "");
			double d_actualSltfValue = Double.parseDouble(actualSltfValue);

			// Comparing actual and expected SLTF value and printing calculated and Actual
			// value
			if (Precision.round(
					Math.abs(Precision.round(d_actualSltfValue, 2) - Precision.round(d_surplustaxesandFeesAcc, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Calculated Surplus Lines Taxes and Fees :   " + "$"
						+ Precision.round(d_surplustaxesandFeesAcc, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_actualSltfValue);
			} else {
				Assertions.verify(d_actualSltfValue, d_surplustaxesandFeesAcc, "Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);

			}

			// Comparing actual and expected total premium and fees value and printing
			// calculated and actual value
			String actualPremiumValueAcc = accountOverviewPage.totalPremiumValue.getData().replace(",", "").replace("$",
					"");
			double d_actualPremiumValueAcc = Double.parseDouble(actualPremiumValueAcc);

			String Str_calcPremiunValueAcc = df
					.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxesandFees + "")
							+ (Double.parseDouble(fees) + Double.parseDouble(surplusContributionValue))));
			double d_calcPremiunValueAcc = Double.parseDouble(Str_calcPremiunValueAcc);
			if (Precision.round(
					Math.abs(Precision.round(d_actualPremiumValueAcc, 2) - Precision.round(d_calcPremiunValueAcc, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Premium, Taxes and Fees : " + "$" + Precision.round(d_calcPremiunValueAcc, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Premium, Taxes and Fees :" + "$" + d_actualPremiumValueAcc);
			} else {

				Assertions.verify(d_actualPremiumValueAcc, d_calcPremiunValueAcc, "Account Overview Page",
						"The Difference between Actual Premium, Taxes and Fees and calculated Premium, Taxes and Fees is more than 0.05",
						false, false);

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
			String actualStampingFee = viewPolicySnapshot.stampingFeeValue.getData().replace("$", "");
			double d_actualStampingFee = Double.parseDouble(actualStampingFee);
			if (Precision.round(Math.abs(Precision.round(d_actualStampingFee, 2) - Precision.round(d_stampingtaxes, 2)),
					2) < 0.05) {
				Assertions.passTest("View Policy Snapshot Page",
						"Calculated Stamping Fees : " + "$" + Precision.round(d_stampingtaxes, 2));
				Assertions.passTest("View Policy Snapshot Page", "Actual Stamping Fees : " + "$" + d_actualStampingFee);
				Assertions.passTest("View Policy Snapshot Page",
						"Stamping Fees calculated as per Stamping Fees Percentage 0.25% for MS is verified");
			} else {
				Assertions.verify(d_actualStampingFee, d_stampingtaxes, "View Policy Snapshot Page",
						"The Difference between Actual Stamping Fees  and calculated Stamping Fees is more than 0.05",
						false, false);

			}

			// Comparing actual and expected MWUA fee value and printing actual and expected
			// value
			String actualMWUAFees = viewPolicySnapshot.mwuaValue.getData().replace(",", "").replace("$", "");

			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(actualMWUAFees), 2) - Precision.round(MWUAFee, 2)),
					2) < 0.05) {
				Assertions.passTest("View Policy Snapshot Page",
						"Calculated MWUA Fees : " + "$" + Precision.round(MWUAFee, 2));
				Assertions.passTest("View Policy Snapshot Page", "Actual MWUA Fees : " + "$" + actualMWUAFees);
				Assertions.passTest("View Policy Snapshot Page",
						"MWUA Fees calculated as per MWUA Fee Percentage 3% for MS is verified");
			} else {
				Assertions.verify(actualMWUAFees, MWUAFee, "View Policy Snapshot Page",
						"The Difference between actual and calculated MWUA fee is more than 0.05", false, false);

			}

			// Click on back button
			viewPolicySnapshot.scrollToTopPage();
			viewPolicySnapshot.waitTime(3);
			viewPolicySnapshot.goBackButton.click();

			// click on Rewrite Policy Link
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Rewrite Policy link");

			// Navigation to Account overview page
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// click on edit dwelling symbol
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Dwelling Symbol");
			Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

			// getting location and building count
			String locationNumber = testData.get("LocCount");
			int locNo = Integer.parseInt(locationNumber);

			String dwellingNumber = testData.get("L" + locNo + "-DwellingCount");
			int bldgNo = Integer.parseInt(dwellingNumber);

			// Changing Construction type to Fire Resistive and Occupied by to Tenant
			testData = data.get(dataValue2);
			Assertions.passTest("Dwelling Page",
					"Construction Type Before Change: " + dwellingPage.constructionTypeData.getData());
			dwellingPage.constructionTypeArrow.waitTillVisibilityOfElement(60);
			dwellingPage.constructionTypeArrow.scrollToElement();
			dwellingPage.constructionTypeArrow.click();
			dwellingPage.constructionTypeOption
					.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingConstType"))
					.waitTillVisibilityOfElement(60);
			dwellingPage.constructionTypeOption
					.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingConstType"))
					.scrollToElement();
			dwellingPage.constructionTypeOption
					.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-DwellingConstType")).click();

			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
			}
			Assertions.passTest("Dwelling Page",
					"Construction Type After Change : " + dwellingPage.constructionTypeData.getData());

			Assertions.passTest("Dwelling Page", "Occupied By Before Change: " + dwellingPage.occupiedByData.getData());
			dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
			dwellingPage.OccupiedByArrow.scrollToElement();
			dwellingPage.OccupiedByArrow.click();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-OccupiedBy"))
					.scrollToElement();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L" + locNo + "D" + bldgNo + "-OccupiedBy"))
					.click();
			Assertions.passTest("Dwelling Page", "Occupied By After Change: " + dwellingPage.occupiedByData.getData());
			Assertions.passTest("Dwelling Page", "Details modified successfully");

			// click on continue button
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			if (dwellingPage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
			}

			// changing deductible values
			Assertions.passTest("Create Quote Page", "Create Quote loaded successfully");
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Deductibles details modified successfully");

			// click on continue button
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// getting quote number 2
			testData = data.get(dataValue1);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(2).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Getting the premium,fees and sltf values from premium section in Account
			// Overview page
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Value is : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Fees Value is : " + accountOverviewPage.feesValue.getData(), false, false);
			Assertions.verify(accountOverviewPage.surplusContibutionValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Surplus Contribution Value is : " + accountOverviewPage.surplusContibutionValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// getting premium amount
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// getting fees value
			fees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");

			// getting surplus ContributionValue value
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page",
					"Surplus Contribution Value " + "$" + surplusContributionValue);

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

			// calculating MWUA percentage by adding Premium+Fees and multiplying by mwua
			// percentage 0.03
			MWUAFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees))
					* Double.parseDouble(testData.get("MWUAFeePercentage"));

			// Rounding MWUA decimal value to 2 digits
			mwuataxes = BigDecimal.valueOf(MWUAFee);
			mwuataxes = mwuataxes.setScale(2, RoundingMode.HALF_UP);

			// Rounding Surplus Lines,Taxes and Fees decimal value to 2 digits
			surplustaxesandFees = BigDecimal.valueOf(surplusTax + stampingFee + MWUAFee);

			// Comparing actual and expected SLTF value and printing calculated value
			String actualSltf = accountOverviewPage.sltfValue.getData().replace(",", "").replace("$", "");
			double d_actualSltf = Double.parseDouble(actualSltf);
			double d_surplustaxesandFees = surplustaxesandFees.doubleValue();
			if (Precision.round(Math.abs(Precision.round(d_actualSltf, 2) - Precision.round(d_surplustaxesandFees, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes and Fees : " + "$" + Precision.round(d_surplustaxesandFees, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_actualSltf);
			} else {
				Assertions.verify(d_actualSltf, d_surplustaxesandFees, "Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);

			}

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			String actualPremiumValue = accountOverviewPage.totalPremiumValue.getData().replace(",", "").replace("$",
					"");
			double d_actualPremiumValue = Double.parseDouble(actualPremiumValue);
			String calcPremiumValue = df
					.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplustaxesandFees + "")
							+ (Double.parseDouble(fees) + Double.parseDouble(surplusContributionValue))));
			double d_calcPremiumValue = Precision.round(Double.parseDouble(calcPremiumValue), 2);
			if (Precision.round(
					Math.abs(Precision.round(d_actualPremiumValue, 2) - Precision.round(d_calcPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Total Premium, Taxes and Fees :  " + "$" + Precision.round(d_calcPremiumValue, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Total Premium, Taxes and Fees : " + "$" + d_actualPremiumValue);
			} else {
				Assertions.verify(d_actualPremiumValue, d_calcPremiumValue, "Account Overview Page",
						"The Difference between actual Total Premium, Taxes and Fees and calculated Total Premium, Taxes and Fees is more than 0.05",
						false, false);

			}

			// click on view/Print full Quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Comparing actual and expected SLTF value and printing actual and expected
			// value
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace(",", ""),
					format.format(surplustaxes).replace(",", ""), "View/Print Full Quote Page",
					"Calculated Surplus Fees : " + format.format(surplustaxes), false, false);
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace(",", ""),
					format.format(surplustaxes).replace(",", ""), "View/Print Full Quote Page",
					"Actual Surplus Fees : " + viewPolicySnapshot.surplusLinesTaxesValue.getData().replace(",", ""),
					false, false);
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace(",", ""),
					format.format(surplustaxes).replace(",", ""), "View/Print Full Quote Page",
					"Surplus Fees calculated as per Surplus Lines Taxes Percentage 4% for MS is verified", false,
					false);

			// Comparing actual and expected Stamping fee value and printing actual and
			// expected value
			String actualStampingValue = viewPolicySnapshot.stampingFeeValue.getData().replace("$", "");
			double d_actualStampingValue = Double.parseDouble(actualStampingValue);
			double d_stampingtaxesvpfq = stampingtaxes.doubleValue();
			if (Precision.round(
					Math.abs(Precision.round(d_actualStampingValue, 2) - Precision.round(d_stampingtaxesvpfq, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated Stamping Fees :  " + "$" + Precision.round(d_stampingtaxesvpfq, 2));
				Assertions.passTest("View/Print Full Quote Page",
						"Actual Stamping Fees : " + "$" + d_actualStampingValue);
				Assertions.passTest("View/Print Full Quote Page",
						"Stamping Fees calculated as per Stamping Fees Percentage 0.25% for MS is verified");
			} else {

				Assertions.verify(d_actualStampingValue, d_stampingtaxesvpfq, "View/Print Full Quote Page",
						"The Difference between actual Stamping Fees and calculated Stamping Fees is more than 0.05",
						false, false);

			}

			// Comparing actual and expected MWUA fee value and printing actual and expected
			// value
			Assertions.verify(viewPolicySnapshot.mwuaValue.getData().replace(",", ""),
					format.format(mwuataxes).replace(",", ""), "View/Print Full Quote Page",
					"Calculated MWUA Fees : " + format.format(mwuataxes).replace(",", ""), false, false);
			Assertions.verify(viewPolicySnapshot.mwuaValue.getData().replace(",", ""),
					format.format(mwuataxes).replace(",", ""), "View/Print Full Quote Page",
					"Actual MWUA Fees : " + viewPolicySnapshot.mwuaValue.getData(), false, false);
			Assertions.verify(viewPolicySnapshot.mwuaValue.getData().replace(",", ""),
					format.format(mwuataxes).replace(",", ""), "View/Print Full Quote Page",
					"MWUA Fees calculated as per MWUA Fee Percentage 3% for MS is verified", false, false);

			// click on go back
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(3);
			viewOrPrintFullQuotePage.backButton.click();

			// click on rewrite bind button
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on Rewrite Bind");

			// entering details for rewrite
			requestBindPage.enteringRewriteDataNAHO(testData);

			// Getting Rewritten Policy Number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary", "Rewritten Policy Number is : " + policyNumber);

			Assertions.addInfo("<span class='header'> Rewritten Policy SLTF Verification</span>", "");
			premiumAmount = policySummaryPage.transactionPremium.getData().replaceAll("[^\\d-.]", "");
			inspectionFee = policySummaryPage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			policyFee = policySummaryPage.policyFee.getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = policySummaryPage.transactionSurplusContribution.getData().replaceAll("[^\\d-.]",
					"");

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
					+ Double.parseDouble(policyFee)) * Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

			// calculating stamping percentage by adding Premium+Fees and multiplying by
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

			// Comparing actual and expected SLTF value and printing calculated value
			String actualSltfpolicysummary = policySummaryPage.TaxesAndStateFees.formatDynamicPath(2).getData()
					.replace(",", "").replace("$", "").replace("-", "");
			double d_actualSltfpolicysummary = Double.parseDouble(actualSltfpolicysummary);
			double d_surplustaxesandFeespolicysummary = surplustaxesandFees.doubleValue();
			if (Precision.round(Math.abs(Precision.round(d_actualSltfpolicysummary, 2)
					- Precision.round(d_surplustaxesandFeespolicysummary, 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Calculated Surplus Lines Taxes and Fees : " + "$"
						+ Precision.round(d_surplustaxesandFeespolicysummary, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_actualSltfpolicysummary);
			} else {
				Assertions.verify(d_actualSltfpolicysummary, d_surplustaxesandFeespolicysummary, "Policy Summary Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);

			}

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			String actualtotalpremium = policySummaryPage.policyTotalPremium.getData().replace(",", "").replace("$",
					"");
			double d_actualtotalpremium = Double.parseDouble(actualtotalpremium);
			String calculatedtotpremium = df.format((Double.parseDouble(premiumAmount))
					+ (Double.parseDouble(surplustaxesandFees + "") + (Double.parseDouble(inspectionFee))
							+ (Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))));
			double d_calculatedtotpremium = Double.parseDouble(calculatedtotpremium);
			if (Precision.round(
					Math.abs(Precision.round(d_actualtotalpremium, 2) - Precision.round(d_calculatedtotpremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Calculated Total Premium, Taxes and Fees :  " + "$"
						+ Precision.round(d_calculatedtotpremium, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual Total Premium, Taxes and Fees : " + "$" + d_calculatedtotpremium);
			} else {
				Assertions.verify(d_actualtotalpremium, d_calculatedtotpremium, "Policy Summary Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);

			}
			// click on Rewritten From Policy Link
			policySummaryPage.rewrittenPolicyNumber.scrollToElement();
			policySummaryPage.rewrittenPolicyNumber.click();

			policySummaryPage.transHistReason.formatDynamicPath("3").waitTillVisibilityOfElement(60);
			policySummaryPage.transHistReason.formatDynamicPath("3").scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath("3").click();

			Assertions.addInfo("<span class='header'> Cancelled Policy SLTF Verification</span>", "");
			premiumAmount = policySummaryPage.transactionPremium.getData().replaceAll("[^\\d-.]", "");
			inspectionFee = policySummaryPage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			policyFee = policySummaryPage.policyFee.getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = policySummaryPage.transactionSurplusContribution.getData().replaceAll("[^\\d-.]",
					"");

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
					+ Double.parseDouble(policyFee)) * Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

			// calculating stamping percentage by adding Premium+Fees and multiplying by
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

			String actualSltfPolicySummary = policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData()
					.replace(",", "").replace("$", "").replace("-", "");
			double d_actualSltfPolicySummary = Double.parseDouble(actualSltfPolicySummary);

			String Str_calcSltfPolicySummary = df.format(surplustaxesandFees).replace("(", "").replace(")", "")
					.replace("-", "");
			double d_calcSltfPolicySummary = Double.parseDouble(Str_calcSltfPolicySummary);

			// Comparing actual and expected SLTF value and printing calculated value
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

			String actualTotaPremium = policySummaryPage.policyTotalPremium.getData().replace(",", "").replace("$", "");
			double d_actualTotaPremium = Double.parseDouble(actualTotaPremium);
			double d_calctotalPremium = Double.parseDouble(premiumAmount) + Double.parseDouble(surplustaxesandFees + "")
					+ Double.parseDouble(inspectionFee) + Double.parseDouble(policyFee)
					+ Double.parseDouble(surplusContributionValue);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			if (Precision.round(
					Math.abs(Precision.round(d_actualTotaPremium, 2) - Precision.round(d_calctotalPremium, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Calculated Premium, Taxes and Fees : " + "$" + Precision.round(d_calctotalPremium, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual Premium, Taxes and Fees : " + "$" + d_actualTotaPremium);
			} else {

				Assertions.verify(d_actualTotaPremium, d_calctotalPremium, "Policy Summary Page",
						"The Difference between actual Premium, Taxes and Fees and calculated Premium, Taxes and Fees is more than 0.05",
						false, false);

			}
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC058 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC058 ", "Executed Successfully");
			}
		}
	}
}