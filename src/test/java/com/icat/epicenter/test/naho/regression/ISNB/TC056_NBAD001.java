package com.icat.epicenter.test.naho.regression.ISNB;

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
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC056_NBAD001 extends AbstractNAHOTest {

	public TC056_NBAD001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBAD001.xls";
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
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		BuildingNoLongerQuoteablePage noLongerQuoteable = new BuildingNoLongerQuoteablePage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();

		// Initializing variables
		String originalQuoteNumber;
		String quoteNumber;
		int quoteLen;
		String premiumAmount;
		String fees;
		String surplusContributionValue;
		double surplusTax;
		BigDecimal roundOffSurplusTaxes;
		double calculatedPremiumAmount;
		String actualPremiumAmount;
		String policyNumber;
		int dataValue1 = 0;
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
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			dwellingPage.waitTime(1);
			dwellingPage.reviewDwelling.waitTillPresenceOfElement(60);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// IO-2010
			// Asserting warning message AL state, when YOC less than
			// 1980, The warning
			// message is 'Due to a year built prior to 1980 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 01", "Asserting warning message when yoc is less than 1980");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages
							.formatDynamicPath(
									"Due to a year built prior to 1980 this risk is ineligible for coverage.")
							.getData()
							.contains("Due to a year built prior to 1980 this risk is ineligible for coverage."),
					true, "Dwelling page",
					"The warning message, "
							+ dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath(
											"Due to a year built prior to 1980 this risk is ineligible for coverage.")
									.getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			// IO-20810 ended

			// Click on override button
			dwellingPage.override.scrollToElement();
			dwellingPage.override.click();
			if (existingAccountPage.override.checkIfElementIsPresent()
					&& existingAccountPage.override.checkIfElementIsDisplayed()) {
				existingAccountPage.override.scrollToElement();
				existingAccountPage.override.click();
			}
			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (noLongerQuoteable.override.checkIfElementIsPresent()
					&& noLongerQuoteable.override.checkIfElementIsPresent()) {
				noLongerQuoteable.override.scrollToElement();
				noLongerQuoteable.override.click();
			}

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

			// Getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			originalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + originalQuoteNumber);
			Assertions.addInfo("Account Overview Page",
					"<span class='header'> " + "Quote Number is : " + originalQuoteNumber + " </span>");

			// assert Alt deductible section
			Assertions.addInfo("Scenario 02", "Verifying presence of alt deductible section");
			Assertions.verify(accountOverviewPage.otherDeductibleOptions.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Alt Deductible section present is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
			// percentage 0.06
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			fees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replaceAll("[^\\d-.]", "");
			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees)
					+ Double.parseDouble(surplusContributionValue)) * 0.06;

			// Rounding sltf decimal value to 2 digits
			roundOffSurplusTaxes = BigDecimal.valueOf(surplusTax);
			roundOffSurplusTaxes = roundOffSurplusTaxes.setScale(2, RoundingMode.HALF_UP);

			// calculated sltf value
			Assertions.passTest("Account Overview Page",
					"Calculated SLTF for Your Quote : " + format.format(roundOffSurplusTaxes));

			// actual sltf value
			Assertions.passTest("Account Overview Page",
					"Actual SLTF for Your Quote : " + accountOverviewPage.sltfValue.getData());

			// comparing expected sltf and actual value
			Assertions.verify(accountOverviewPage.sltfValue.getData(), format.format(roundOffSurplusTaxes),
					"Account Overview Page", "Calculated and actual SLTF for Your Quote are equal", false, false);
			calculatedPremiumAmount = roundOffSurplusTaxes.doubleValue() + Double.parseDouble(premiumAmount)
					+ Double.parseDouble(fees) + Double.parseDouble(surplusContributionValue);
			Assertions.passTest("Account Overview Page",
					"Calcuated Total Premium taxes and Fees : " + format.format(calculatedPremiumAmount));
			actualPremiumAmount = accountOverviewPage.totalPremiumValue.getData();

			// fetching premium taxes and fees value in quote section
			Assertions.passTest("Account Overview Page", "Actual Premium ,Taxes and Fees in Premium section : "
					+ accountOverviewPage.totalPremiumValue.getData());

			// fetching premium taxes and fees value in other ded options section
			Assertions.passTest("Account Overview Page",
					"Actual Total Premium & ICAT Fees in Other deductibles section : "
							+ accountOverviewPage.quoteOptionsTotalPremium.getData());

			// calculating total,premium taxes and Fees
			Assertions.verify(accountOverviewPage.totalPremiumValue.getData(), format.format(calculatedPremiumAmount),
					"Account Overview Page",
					"Calcuated Total Premium taxes and Fees and Total Premium,taxes and Fees in Quote section are equal",
					false, false);

			Assertions.verify(accountOverviewPage.quoteOptionsTotalPremium.getData(),
					format.format(calculatedPremiumAmount), "Account Overview Page",
					"Calculated Total Premium,taxes and Fees and Total Premium,taxes and Fees in Your Quote other Deductible Options section are equal",
					false, false);

			// comparing premium,taxes and fees in quote tree section and other deductible
			// options section
			Assertions.verify(accountOverviewPage.totalPremiumValue.getData(),
					accountOverviewPage.quoteOptionsTotalPremium.getData(), "Account Overview Page",
					"Total Premium,taxes and Fees in Quote section and Your Quote in other Deductible Options section are equal",
					false, false);

			// Asserting the absence of not including taxes text in Total Premium & ICAT
			// Fees section
			Assertions.addInfo("Scenario 03",
					"Asserting the absence of not including taxes text in Total Premium & ICA Fees section");
			Assertions.verify(
					accountOverviewPage.priorLossDetails.formatDynamicPath("not including taxes")
							.checkIfElementIsPresent(),
					false, "Account Overview Page", "The Text Not Including Taxes not present is verified", false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			for (int i = 2; i <= 4; i++) {
				accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(i).waitTillVisibilityOfElement(60);
				accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(i).scrollToElement();
				accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(i).click();

				Assertions.addInfo("Account Overview Page", "<span class='header'> " + "Quote Number is : "
						+ accountOverviewPage.altQuoteName.formatDynamicPath(1, i - 1).getData() + " </span>");

				premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

				fees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");

				surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replaceAll("[^\\d-.]",
						"");

				// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
				// percentage 0.06
				surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees)
						+ Double.parseDouble(surplusContributionValue)) * 0.06;

				// Rounding sltf decimal value to 2 digits
				roundOffSurplusTaxes = BigDecimal.valueOf(surplusTax);
				double d_roundOffSurplusTaxes = roundOffSurplusTaxes.doubleValue();
				roundOffSurplusTaxes = roundOffSurplusTaxes.setScale(2, RoundingMode.HALF_UP);

				// Getting actual sltf value
				String actualSltf = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
				double d_actualSltf = Double.parseDouble(actualSltf);

				// calculated sltf value
				if (Precision.round(
						Math.abs(Precision.round(d_actualSltf, 2) - Precision.round(d_roundOffSurplusTaxes, 2)),
						2) < 0.05) {
					Assertions.passTest("Account Overview Page", "Calculated SLTF for Alt Option " + (i - 1) + " : "
							+ "$" + Precision.round(d_roundOffSurplusTaxes, 2));
					// actual sltf value
					Assertions.passTest("Account Overview Page",
							"Actual SLTF for Alt Option " + (i - 1) + " : " + accountOverviewPage.sltfValue.getData());
					Assertions.passTest("Account Overview Page",
							"Calculated and actual SLTF for Alt Option " + (i - 1) + " are equal");
				} else {
					Assertions.passTest("Account Overview Page",
							"The Difference between actual SLTF and calculated SLTF is more than 0.05");
				}

				calculatedPremiumAmount = roundOffSurplusTaxes.doubleValue() + Double.parseDouble(premiumAmount)
						+ Double.parseDouble(fees) + Double.parseDouble(surplusContributionValue);

				actualPremiumAmount = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", "");
				double d_actualPremiumAmount = Double.parseDouble(actualPremiumAmount);
				if (Precision.round(Math
						.abs(Precision.round(d_actualPremiumAmount, 2) - Precision.round(calculatedPremiumAmount, 2)),
						2) < 0.05) {
					Assertions.passTest("Account Overview Page",
							"Calcuated Total Premium taxes and Fees and for Alt Option " + (i - 1) + " : " + "$"
									+ Precision.round(calculatedPremiumAmount, 2));
					Assertions.passTest("Account Overview Page",
							"Actual Premium ,Taxes and Fees in Quote section for Alt Option " + (i - 1) + " : " + "$"
									+ d_actualPremiumAmount);
					Assertions.passTest("Account Overview Page",
							"Calcuated Total Premium taxes and Fees and Total Premium,taxes and Fees in Quote section for Alt Option "
									+ (i - 1) + " are equal");
				} else {
					Assertions.passTest("Account Overview Page",
							"The Difference between actual Total Premium taxes and Fees and calculated Total Premium taxes and Fees is more than 0.05");
				}

				accountOverviewPage.quoteNumberLink.formatDynamicPath(originalQuoteNumber).scrollToElement();
				accountOverviewPage.quoteNumberLink.formatDynamicPath(originalQuoteNumber).click();

				// fetching premium taxes and fees value in other ded options section
				String actualTotPremium = accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(i).getData()
						.replace(",", "").replace("$", "");
				double d_actualTotPremium = Double.parseDouble(actualTotPremium);
				if (Precision.round(
						Math.abs(Precision.round(d_actualTotPremium, 2) - Precision.round(calculatedPremiumAmount, 2)),
						2) < 0.05) {
					Assertions.passTest("Account Overview Page",
							"Actual Total Premium & ICAT Fees in other Deductible Options section for Alt Option "
									+ (i - 1) + " : " + "$" + d_actualTotPremium);
					Assertions.passTest("Account Overview Page",
							"Calculated Total Premium & ICAT Fees in other Deductible Options section for Alt Option "
									+ (i - 1) + " : " + "$" + Precision.round(calculatedPremiumAmount, 2));
					Assertions.passTest("Account Overview Page",
							"Calculated Total Premium,taxes and Fees and Total Premium,taxes and Fees in other Deductible Options section for Alt Option "
									+ (i - 1) + " are equal");
				} else {
					Assertions.passTest("Account Overview Page",
							"The Difference between actual Total Premium taxes and Fees and calculated Total Premium taxes and Fees is more than 0.05");
				}
			}

			// looping through AOP section and asserting values
			for (int i = 1; i <= 4; i++) {
				if (i == 1) {
					Assertions.verify(
							accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", i)
									.checkIfElementIsDisplayed(),
							true, "Account Overview Page",
							"Quote value selected : " + accountOverviewPage.deductibleOptionsNAHO
									.formatDynamicPath("All Other Perils", i).getData(),
							false, false);
				} else {
					Assertions.verify(
							accountOverviewPage.deductibleOptionsNAHO
									.formatDynamicPath("All Other Perils", i).checkIfElementIsDisplayed(),
							true, "Account Overview Page",
							"Other Quote Options : Option " + (i - 1) + " : "
									+ accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", i)
											.getData(),
							false, false);
				}
			}

			// asserting higher deductible options
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", 3)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Quote Options of deductible above selected : " + accountOverviewPage.deductibleOptionsNAHO
							.formatDynamicPath("All Other Perils", 3).getData(),
					false, false);

			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", 4)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Quote Options of deductible above selected : " + accountOverviewPage.deductibleOptionsNAHO
							.formatDynamicPath("All Other Perils", 4).getData(),
					false, false);

			// asserting lower deductible option
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", 2)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Quote Options of deductible below selected : " + accountOverviewPage.deductibleOptionsNAHO
							.formatDynamicPath("All Other Perils", 2).getData(),
					false, false);

			// clicking on 2nd quote option
			accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(3).waitTillPresenceOfElement(60);
			accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(3).waitTillVisibilityOfElement(60);
			accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(3).scrollToElement();
			accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(3).click();

			// adding assertion for IO-18904
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			accountOverviewPage.waitTime(20);
			Assertions.addInfo("Scenario 04", "Verifying presence of Mold option on create quote page");
			Assertions.verify(createQuotePage.moldDataNAHO.getData(), testData.get("MoldProperty"), "Account Overview Page",
					"For Alt Option 2 Mold present is verified and the value is " + createQuotePage.moldDataNAHO.getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			accountOverviewPage.requestBind.waitTillVisibilityOfElement(60);
			Assertions.verify(accountOverviewPage.altQuoteName.formatDynamicPath(1, 2).checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Alt Option 2 " + accountOverviewPage.altQuoteName.formatDynamicPath(1, 2).getData()
							+ " selected to bound is verified",
					false, false);

			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);

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

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);

			// approving referral
			accountOverviewPage.uploadPreBindApproveAsUSM();
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			// Getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.passTest("Policy Summary Page", "PolicyNumber is " + policyNumber);

			quoteNumber = policySummaryPage.quoteNoLinkNAHO.getData();
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			String quoteNumberBound = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);

			// Alt quote is bound is verified
			Assertions.addInfo("Scenario 05", "Verifying Alt quote is bound");
			Assertions.verify(quoteNumber, quoteNumberBound, "Policy Summary Page",
					"Alt Quote is bound is verified.Quote Number : " + quoteNumber, false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on home icon link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search policy
			homePage.searchPolicy(policyNumber);
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Click on PB Endorsement link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page loaded successfully", false, false);

			// Enter endorse effective date click on edit location/building information link
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("PolicyEffDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();

			// Click on review dwelling link
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// IO-2010
			// Asserting warning message AL state when YOC less than
			// 1980 for Endorsement, The warning
			// message is 'Due to a year built prior to 1980 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 06", "Asserting warning message when yoc is less than 1980");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages
							.formatDynamicPath(
									"Due to a year built prior to 1980 this risk is ineligible for coverage.")
							.getData()
							.contains("Due to a year built prior to 1980 this risk is ineligible for coverage."),
					true, "Dwelling page",
					"The warning message, "
							+ dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath(
											"Due to a year built prior to 1980 this risk is ineligible for coverage.")
									.getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on override button
			dwellingPage.override.scrollToElement();
			dwellingPage.override.click();
			Assertions.passTest("Dwelling Page", "Clicked on override button successfully");
			// IO-20810 ended

			// Click on home icon link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search policy
			homePage.searchPolicy(policyNumber);
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Click on Rewrite link
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			cancelPolicyPage.continueButton.scrollToElement();
			cancelPolicyPage.continueButton.click();
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click on quote 1
			accountOverviewPage.quoteNumberLink.formatDynamicPath(originalQuoteNumber).scrollToElement();
			accountOverviewPage.quoteNumberLink.formatDynamicPath(originalQuoteNumber).click();
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Clicked on quote link successfully", false, false);

			// Click on edit building link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			// Click on review dwelling button
			Assertions.verify(dwellingPage.createQuote.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// IO-2010
			// Asserting warning message AL state when YOC less than
			// 1980 for Rewrite, The warning
			// message is 'Due to a year built prior to 1980 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 07", "Asserting warning message when yoc is less than 1980");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages
							.formatDynamicPath(
									"Due to a year built prior to 1980 this risk is ineligible for coverage.")
							.getData()
							.contains("Due to a year built prior to 1980 this risk is ineligible for coverage."),
					true, "Dwelling page",
					"The warning message, "
							+ dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath(
											"Due to a year built prior to 1980 this risk is ineligible for coverage.")
									.getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on override button
			dwellingPage.override.scrollToElement();
			dwellingPage.override.click();
			Assertions.passTest("Dwelling Page", "Clicked on override button successfully");
			// IO-20810 ended

			if (existingAccountPage.override.checkIfElementIsPresent()
					&& existingAccountPage.override.checkIfElementIsDisplayed()) {
				existingAccountPage.override.scrollToElement();
				existingAccountPage.override.click();
			}
			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (noLongerQuoteable.override.checkIfElementIsPresent()
					&& noLongerQuoteable.override.checkIfElementIsPresent()) {
				noLongerQuoteable.override.scrollToElement();
				noLongerQuoteable.override.click();
			}
			// Click on get quote
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting the rewrite quote number
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.addInfo("Account Overview Page",
					"<span class='header'> " + "Rewrite Quote Number is : " + quoteNumber + " </span>");

			// Click on view policy link
			accountOverviewPage.viewPolicyButton.scrollToElement();
			accountOverviewPage.viewPolicyButton.click();

			// Stop policy rewrite
			policySummaryPage.stopPolicyRewrite.scrollToElement();
			policySummaryPage.stopPolicyRewrite.click();

			// clicking on renewal policy link
			Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			cancelPolicyPage.continueButton.scrollToElement();
			cancelPolicyPage.continueButton.click();

			// Go to Home Page
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);
				// clicking on renewal policy link
				policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				cancelPolicyPage.continueButton.scrollToElement();
				cancelPolicyPage.continueButton.click();
				Assertions.passTest("Policy Summary Page", "Clicked on renewal link successfully");
			}

			// Getting the renewal quote number
			Assertions.verify(accountOverviewPage.openReferral.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.addInfo("Account Overview Page",
					"<span class='header'> " + "Renewal Quote Number is : " + quoteNumber + " </span>");

			// Click on open referral link
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();
				Assertions.passTest("Account Overview Page", "Clicked on open referral link successfully");

				// click on approve in Approve Decline Quote page
				referralPage.clickOnApprove();
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Quote referral approved successfully");
			}

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search renewal quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Renewal quote searched successfully");

			// Click on release renewal producer
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on release renewal to producer successfully");

			// Click on edit building link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			// Click on review dwelling button
			Assertions.verify(dwellingPage.createQuote.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// IO-2010
			// Checking absence of warning message AL state when YOC less than
			// 1980 for Renewal, The warning
			// message is 'Due to a year built prior to 1980 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 08", "Checking absence of warning message when yoc is less than 1980");

			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages
							.formatDynamicPath(
									"Due to a year built prior to 1980 this risk is ineligible for coverage.")
							.checkIfElementIsPresent(),
					false, "Dwelling Page", "The warning message not displayed ", false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC056 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC056 ", "Executed Successfully");
			}
		}
	}
}