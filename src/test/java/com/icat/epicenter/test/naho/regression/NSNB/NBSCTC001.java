package com.icat.epicenter.test.naho.regression.NSNB;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

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
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class NBSCTC001 extends AbstractNAHOTest {

	public NBSCTC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/SC001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages

		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ViewOrPrintRateTrace viewprOrPrintRateTrace = new ViewOrPrintRateTrace();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		BuildingNoLongerQuoteablePage noLongerQuoteable = new BuildingNoLongerQuoteablePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		DecimalFormat df = new DecimalFormat("0.00");
		int data_Value1 = 0;
		int data_Value2 = 1;
		String policyNumber;
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
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			dwellingPage.reviewDwelling.waitTillVisibilityOfElement(60);
			dwellingPage.waitTime(2);// need wait time to load the element
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// IO-2010
			// Asserting warning message SC state when YOC less than
			// 1970, The warning
			// message is 'Due to a year built prior to 1970 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 01", "Asserting warning message when yoc is less than 1970");
			Assertions.verify(dwellingPage.protectionClassWarMsg.getData().contains("year built prior to 1970"), true,
					"Dwelling page",
					"The warning message, " + dwellingPage.protectionClassWarMsg.getData() + " is displayed", false,
					false);
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
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Asserting NS Default Value in Create Quote Page
			Assertions.addInfo("Scenario 02", "Asserting NS Default Value in Create Quote Page");
			Assertions.verify(createQuotePage.namedStormData.getData(), testData.get("NamedStormValue"),
					"Create Quote Page",
					"Named Storm minimum deductible value is " + createQuotePage.namedStormData.getData(), false,
					false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Asserting Presence of EQ deductible[Values],EQ Loss Assessment
			// coverage[Values] and AOP/NS/EQ data in Create Quote Page
			Assertions.addInfo("Scenario 03",
					"Asserting Presence of EQ deductible[Values],EQ Loss Assessment coverage[Values] and AOP/NS/EQ data in Create Quote Page");
			testData = data.get(data_Value2);
			Assertions.verify(createQuotePage.earthquakeDeductibleArrow.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "EQ Deductible Dropdown is displayed and default value is "
							+ createQuotePage.earthquakeData.getData(),
					false, false);
			Assertions.verify(
					createQuotePage.eqLossAssessmentArrow.checkIfElementIsPresent()
							&& createQuotePage.eqLossAssessmentArrow.checkIfElementIsDisplayed(),
					false, "Create Quote Page",
					"EQ Loss Assessent Dropdown is not displayed when EQ Deductible value is "
							+ createQuotePage.earthquakeData.getData(),
					false, false);
			Assertions.verify(
					createQuotePage.aopNSEQData.formatDynamicPath("AOP/NS/EQ").checkIfElementIsPresent()
							&& createQuotePage.aopNSEQData.formatDynamicPath("AOP/NS/EQ").checkIfElementIsDisplayed(),
					false, "Create Quote Page", createQuotePage.aopNSEQData.formatDynamicPath("AOP/NS").getData()
							+ " is displayed when EQ is not selected",
					false, false);
			createQuotePage.earthquakeDeductibleArrow.scrollToElement();
			createQuotePage.earthquakeDeductibleArrow.click();

			// As per the EQDeductible values changes from None to Not Selected
			Assertions.verify(
					createQuotePage.earthquakeDeductibleOptionEQHO.formatDynamicPath("Not Selected")
							.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					createQuotePage.earthquakeDeductibleOptionEQHO.formatDynamicPath("Not Selected").getData()
							+ " is displayed under EQ Deductible Options",
					false, false);
			for (int i = 0; i <= 4; i++) {
				testData = data.get(i);
				Assertions.verify(
						createQuotePage.earthquakeDeductibleOptionEQHO
								.formatDynamicPath(testData.get("EQDeductibleValue")).checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						createQuotePage.earthquakeDeductibleOptionEQHO
								.formatDynamicPath(testData.get("EQDeductibleValue")).getData()
								+ " EQ Deductible value is displayed",
						false, false);
			}
			testData = data.get(data_Value2);
			createQuotePage.earthquakeDeductibleOptionEQHO.formatDynamicPath(testData.get("EQDeductibleValue"))
					.scrollToElement();
			createQuotePage.earthquakeDeductibleOptionEQHO.formatDynamicPath(testData.get("EQDeductibleValue")).click();
			Assertions.verify(createQuotePage.eqLossAssessmentArrow.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "EQ Loss Assessent Dropdown is displayed when EQ Deductible value is Selected",
					false, false);
			Assertions.verify(
					createQuotePage.aopNSEQData.formatDynamicPath("AOP/NS/EQ").checkIfElementIsPresent()
							&& createQuotePage.aopNSEQData.formatDynamicPath("AOP/NS/EQ").checkIfElementIsDisplayed(),
					true, "Create Quote Page", createQuotePage.aopNSEQData.formatDynamicPath("AOP/NS/EQ").getData()
							+ " is displayed when EQ is selected",
					false, false);
			createQuotePage.eqLossAssessmentArrow.scrollToElement();
			createQuotePage.eqLossAssessmentArrow.click();
			Assertions.verify(
					createQuotePage.eqLossAssessmentOption.formatDynamicPath("None").checkIfElementIsDisplayed(), true,
					"Create Quote Page", createQuotePage.eqLossAssessmentOption.formatDynamicPath("None").getData()
							+ " is displayed under EQ Loss Assessment Options",
					false, false);
			for (int i = 1; i <= 4; i++) {
				testData = data.get(i);
				Assertions
						.verify(createQuotePage.eqLossAssessmentOption
								.formatDynamicPath(testData.get("EQLossAssessment")).checkIfElementIsDisplayed(), true,
								"Create Quote Page",
								createQuotePage.eqLossAssessmentOption
										.formatDynamicPath(testData.get("EQLossAssessment")).getData()
										+ " EQ Loss Assessment value is displayed",
								false, false);
			}
			createQuotePage.eqLossAssessmentOption.formatDynamicPath(testData.get("EQLossAssessment"))
					.scrollToElement();
			createQuotePage.eqLossAssessmentOption.formatDynamicPath(testData.get("EQLossAssessment")).click();
			createQuotePage.refreshPage();
			testData = data.get(data_Value1);
			int eqDed = Integer.parseInt(testData.get("EQDeductibleValue").replace("%", ""));
			int covADed = Integer.parseInt(testData.get("L1D1-DwellingCovA"));
			String calculatedeqDedValue = String.valueOf(covADed / 100 * eqDed);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String originalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + originalQuoteNumber);

			// Asserting SLTF value on account overview page
			String premiumOnAccountOverviewPage = accountOverviewPage.premiumValue.getData();
			String feesOnAccountOverviewPage = accountOverviewPage.feesValue.getData();
			String sltfValueAccountOverviewPage = accountOverviewPage.sltfValue.getData().replace(",", "");
			String surplusContributionAccountOverviewPage = accountOverviewPage.surplusContibutionValue.getData();

			double d_premiumValueOnAccountOverviewPage = Double
					.parseDouble(premiumOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_FeeValueOnAccountOverviewPage = Double
					.parseDouble(feesOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_surplusContributionAccountOverviewPage = Double
					.parseDouble(surplusContributionAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_sltfPercentageValue = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_sltfValueOnAccountOverviewPage = (d_premiumValueOnAccountOverviewPage
					+ d_FeeValueOnAccountOverviewPage + d_surplusContributionAccountOverviewPage)
					* d_sltfPercentageValue;
			Assertions.addInfo("Scenario 04", "Asserting SLTF Values on Account Overview Page");
			Assertions.verify(sltfValueAccountOverviewPage, "$" + df.format(d_sltfValueOnAccountOverviewPage),
					"Account Overview Page",
					"Actual and Calculated SLTF Values are matching and calculated according to 6% SC SLTF value "
							+ sltfValueAccountOverviewPage,
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Asserting SLTF value on view print full quote page
			Assertions.addInfo("Scenario 05", "Asserting SLTF Values on View/Print Full Quote Page");
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData();
			String inspectionFeeValue = viewOrPrintFullQuotePage.insurerInspectionFeeValue.getData();
			String policyFeeValue = viewOrPrintFullQuotePage.insurerPolFeeVlaue.getData();
			String sltfValue = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace(",", "");
			String surplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData();

			double d_premiumValue = Double.parseDouble(premiumValue.replace("$", "").replace(",", ""));
			double d_inspectionFeeValue = Double.parseDouble(inspectionFeeValue.replace("$", "").replace(",", ""));
			double d_policyFeeValue = Double
					.parseDouble(policyFeeValue.replace("$", "").replace(",", "").replace(",", ""));
			double d_surplusContribution = Double
					.parseDouble(surplusContribution.replace("$", "").replace(",", "").replace("%", ""));

			double d_sltfValue = (d_premiumValue + d_inspectionFeeValue + d_policyFeeValue + d_surplusContribution)
					* d_sltfPercentageValue;

			Assertions.verify(sltfValue, "$" + df.format(d_sltfValue), "View/Print Full Quote Page",
					"Actual and Calculated SLTF Values are matching and calculated according to 6% SC SLTF value "
							+ sltfValue,
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Asserting declinations column on view print full quote
			Assertions.addInfo("Scenario 06", "Assertiong Declination Wording in View/Print Full Quote Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Declining Company")
							.getNoOfWebElements(),
					3, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Declining Company").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("NAIC").getNoOfWebElements(), 3,
					"View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("NAIC").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Reason for Declination")
							.getNoOfWebElements(),
					3, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Reason for Declination").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date Declined").getNoOfWebElements(),
					3, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date Declined").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Name")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Name")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Name").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Signature")
							.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Signature")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Producing Agent Signature").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Date").getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Asserting Hard coded Insured Wording in View/Print Full Quote Page
			Assertions.addInfo("Scenario 07", "Asserting Hardcoded Insured Wording in View/Print Full Quote Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.insurerWording.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.insurerWording.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.insurerWording.getData() + " is displayed", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Asserting EQ Deductible Value is calculated correctly in View/Print Full
			// Quote Page
			Assertions.addInfo("Scenario 08",
					"Asserting EQ Deductible Value is calculated correctly in View/Print Full Quote Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData()
							.substring(5,
									viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData()
											.length() - 1)
							.replace(",", "").contains(calculatedeqDedValue),
					true, "View/Print Full Quote Page", "EQ Deductible is calculated based on EQ% * CoverageA value",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();

			// Click on view or print trace link
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();

			// Assert EQ premium on rate trace before EQ Premium override
			Assertions.addInfo("Scenario 09",
					"Asserting Presence of EQ Premium, EQ Loss Assessment Premium and EQ Premium before Override in View Rate Trace page");
			Assertions.verify(
					viewprOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("EQPremium")
							.checkIfElementIsPresent()
							&& viewprOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("EQPremium")
									.checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page",
					viewprOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("EQPremium").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewprOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("EQ Loss Assessment Premium")
							.checkIfElementIsPresent()
							&& viewprOrPrintRateTrace.premiumandCoverageData
									.formatDynamicPath("EQ Loss Assessment Premium").checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page", viewprOrPrintRateTrace.premiumandCoverageData
							.formatDynamicPath("EQ Loss Assessment Premium").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewprOrPrintRateTrace.eqPremium.checkIfElementIsPresent()
							&& viewprOrPrintRateTrace.eqPremium.checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page",
					"EQ Premium Before override is " + viewprOrPrintRateTrace.eqPremium.getData(), false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Click on back button
			viewprOrPrintRateTrace.backBtn.scrollToElement();
			viewprOrPrintRateTrace.backBtn.click();

			// Override EQ premium
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.addInfo("Scenario 10",
					"Override EQ Premium and Assert Warning message. Assert the Updated Premium in View Rate Trace Page");
			Assertions.verify(
					overridePremiumAndFeesPage.eqPremiumData.checkIfElementIsPresent()
							&& overridePremiumAndFeesPage.eqPremiumData.checkIfElementIsDisplayed(),
					true, "Override Premium Page", overridePremiumAndFeesPage.eqPremiumData.getData() + " is displayed",
					false, false);
			overridePremiumAndFeesPage.eqPremium.scrollToElement();
			overridePremiumAndFeesPage.eqPremium.setData("EQPremiumOverride");
			overridePremiumAndFeesPage.overridePremiumButton.scrollToElement();
			overridePremiumAndFeesPage.overridePremiumButton.click();
			Assertions.verify(
					accountOverviewPage.premiumWarningMessage.checkIfElementIsPresent()
							&& accountOverviewPage.premiumWarningMessage.checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					accountOverviewPage.premiumWarningMessage.getData() + " is displayed", false, false);

			// Adding code for IO-19202
			testData = data.get(data_Value2);
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			overridePremiumAndFeesPage.eqPremium.scrollToElement();
			overridePremiumAndFeesPage.eqPremium.waitTillVisibilityOfElement(60);
			overridePremiumAndFeesPage.eqPremium.setData(testData.get("EQPremiumOverride"));
			overridePremiumAndFeesPage.overridePremiumButton.waitTillVisibilityOfElement(60);
			overridePremiumAndFeesPage.overridePremiumButton.scrollToElement();
			overridePremiumAndFeesPage.overridePremiumButton.click();

			// Adding Assertion for Justification override Warning Message
			Assertions.verify(
					overridePremiumAndFeesPage.overrideJustificationMsg.checkIfElementIsPresent()
							&& overridePremiumAndFeesPage.overrideJustificationMsg.checkIfElementIsDisplayed(),
					true, "Override Premium Page",
					overridePremiumAndFeesPage.overrideJustificationMsg.getData() + " is displayed", false, false);

			// Adding text Override Justification message
			overridePremiumAndFeesPage.feeOverrideJustification.scrollToElement();
			overridePremiumAndFeesPage.feeOverrideJustification.setData("FeeOverrideJustification");
			overridePremiumAndFeesPage.overridePremiumButton.scrollToElement();
			overridePremiumAndFeesPage.overridePremiumButton.click();

			// Assert EQ premium on rate trace after EQ Premium override
			testData = data.get(data_Value1);
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();
			Assertions.verify(
					viewprOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("EQPremium")
							.checkIfElementIsPresent()
							&& viewprOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("EQPremium")
									.checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page",
					viewprOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("EQPremium").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewprOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("EQ Loss Assessment Premium")
							.checkIfElementIsPresent()
							&& viewprOrPrintRateTrace.premiumandCoverageData
									.formatDynamicPath("EQ Loss Assessment Premium").checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page", viewprOrPrintRateTrace.premiumandCoverageData
							.formatDynamicPath("EQ Loss Assessment Premium").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewprOrPrintRateTrace.eqPremium.checkIfElementIsPresent()
							&& viewprOrPrintRateTrace.eqPremium.checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page",
					"EQ Premium After override is " + viewprOrPrintRateTrace.eqPremium.getData(), false, false);
			viewprOrPrintRateTrace.backBtn.scrollToElement();
			viewprOrPrintRateTrace.backBtn.click();
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Assert EQ for alt quotes
			Assertions.addInfo("Scenario 11", "Asserting EQ Deductible is Carry Forwarded to Multiple Alt quotes");
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			String eqDeductibleValue = viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake")
					.getData();
			Assertions.verify(
					viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"EQ Deductible" + viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData()
							+ " is displayed for quote 1",
					false, false);
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();

			// Select Alt quote 1
			accountOverviewPage.quoteOptions1TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions1TotalPremium.click();
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData(),
					eqDeductibleValue, "View/Print Full Quote Page",
					"EQ Deductible for Alt quote 1, "
							+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData()
							+ " is same as quote 1",
					false, false);
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();
			accountOverviewPage.quoteNumberLink.formatDynamicPath(originalQuoteNumber, 1).scrollToElement();
			accountOverviewPage.quoteNumberLink.formatDynamicPath(originalQuoteNumber, 1).click();

			// Select Alt quote 2
			accountOverviewPage.quoteOptions2TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions2TotalPremium.click();
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData(),
					eqDeductibleValue, "View/Print Full Quote Page",
					"EQ Deductible for Alt quote 2, "
							+ viewOrPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData()
							+ " is same as quote 1",
					false, false);
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");
			accountOverviewPage.quoteNumberLink.formatDynamicPath(quoteNumber, 1).scrollToElement();
			accountOverviewPage.quoteNumberLink.formatDynamicPath(quoteNumber, 1).click();

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

			requestBindPage.refreshPage();

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Values Entered Successfully");

			// Approve bind request
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfully");

			// approving referral
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting policy number
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			// Assert EQ premium on rate trace after EQ Premium override
			Assertions.addInfo("Scenario 12",
					"Asserting EQ Premium/ EQ Loss Assessment Premium in Rate Trace link on Policy Summary Page");
			policySummaryPage.viewPrintRateTrace.scrollToElement();
			policySummaryPage.viewPrintRateTrace.click();
			Assertions.verify(
					viewprOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("EQPremium")
							.checkIfElementIsPresent()
							&& viewprOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("EQPremium")
									.checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page",
					viewprOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("EQPremium").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewprOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("EQ Loss Assessment Premium")
							.checkIfElementIsPresent()
							&& viewprOrPrintRateTrace.premiumandCoverageData
									.formatDynamicPath("EQ Loss Assessment Premium").checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page", viewprOrPrintRateTrace.premiumandCoverageData
							.formatDynamicPath("EQ Loss Assessment Premium").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewprOrPrintRateTrace.eqPremium.checkIfElementIsPresent()
							&& viewprOrPrintRateTrace.eqPremium.checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page",
					"EQ Premium After override is " + viewprOrPrintRateTrace.eqPremium.getData(), false, false);
			viewprOrPrintRateTrace.backBtn.scrollToElement();
			viewprOrPrintRateTrace.backBtn.click();
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			// Click on View Policy Snapshot
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("View Policy Snapshot Page", "Clicked on View Policy Snapshot link");

			// Asserting SLTF value on view print full quote page
			String premiumValue1 = viewOrPrintFullQuotePage.premiumValue.getData();
			String inspectionFeeValue1 = viewOrPrintFullQuotePage.insurerInspectionFeeValue.getData();
			String policyFeeValue1 = viewOrPrintFullQuotePage.insurerPolFeeVlaue.getData();
			String sltfValue1 = viewPolicySnapShot.surplusLinesTaxesValue.getData().replace(",", "");
			String surplusContributionValueSnapShot = viewPolicySnapShot.surplusContributionValue.getData();

			double d_premiumValue1 = Double.parseDouble(premiumValue1.replace("$", "").replace(",", ""));
			double d_inspectionFeeValue1 = Double.parseDouble(inspectionFeeValue1.replace("$", "").replace(",", ""));
			double d_policyFeeValue1 = Double.parseDouble(policyFeeValue1.replace("$", "").replace(",", ""));
			double d_surplusContributionValueSnapShot = Double
					.parseDouble(surplusContributionValueSnapShot.replace("$", "").replace(",", "").replace("%", ""));
			double d_sltfValue1 = (d_premiumValue1 + d_inspectionFeeValue1 + d_policyFeeValue1
					+ d_surplusContributionValueSnapShot) * d_sltfPercentageValue;

			Assertions.addInfo("Scenario 13", "Asserting SLTF Values on Policy Snapshot Page");
			Assertions.verify(sltfValue1, "$" + df.format(d_sltfValue1), "View Policy Snapshot Page",
					"Actual and Calculated SLTF Values are matching and calculated according to 6% SC SLTF value "
							+ sltfValue1,
					false, false);
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			// Asserting Declination Data in View Policy Snapshot Page
			Assertions.addInfo("Scenario 14", "Asserting Declination Data in View Policy Snapshot Page");
			Assertions.verify(
					viewPolicySnapShot.declinationsData.formatDynamicPath("Declining Company").getNoOfWebElements(),
					3, "View Policy Snapshot Page",
					viewPolicySnapShot.declinationsData.formatDynamicPath("Declining Company").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(viewPolicySnapShot.declinationsData.formatDynamicPath("NAIC").getNoOfWebElements(), 3,
					"View Policy Snapshot Page",
					viewPolicySnapShot.declinationsData.formatDynamicPath("NAIC").getData() + " is displayed", false,
					false);
			Assertions.verify(
					viewPolicySnapShot.declinationsData.formatDynamicPath("Reason for Declination")
							.getNoOfWebElements(),
					3, "View Policy Snapshot Page",
					viewPolicySnapShot.declinationsData.formatDynamicPath("Reason for Declination").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewPolicySnapShot.declinationsData.formatDynamicPath("Date Declined").getNoOfWebElements(), 3,
					"View Policy Snapshot Page",
					viewPolicySnapShot.declinationsData.formatDynamicPath("Date Declined").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewPolicySnapShot.declinationsData.formatDynamicPath("Producing Agent Name")
							.checkIfElementIsPresent()
							&& viewPolicySnapShot.declinationsData.formatDynamicPath("Producing Agent Name")
									.checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					viewPolicySnapShot.declinationsData.formatDynamicPath("Producing Agent Name").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewPolicySnapShot.declinationsData.formatDynamicPath("Producing Agent Signature")
							.checkIfElementIsPresent()
							&& viewPolicySnapShot.declinationsData.formatDynamicPath("Producing Agent Signature")
									.checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					viewPolicySnapShot.declinationsData.formatDynamicPath("Producing Agent Signature").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(viewPolicySnapShot.declinationsData.formatDynamicPath("Date").checkIfElementIsPresent()
					&& viewPolicySnapShot.declinationsData.formatDynamicPath("Date").checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page",
					viewPolicySnapShot.declinationsData.formatDynamicPath("Date").getData() + " is displayed", false,
					false);
			Assertions.addInfo("Scenario 14", "Scenario 14 Ended");

			// Asserting Insured Wording on Policy Snapshot Page
			Assertions.addInfo("Scenario 15", "Asserting Insured Wording on Policy Snapshot Page");
			Assertions.verify(
					viewPolicySnapShot.insurerWording.checkIfElementIsPresent()
							&& viewPolicySnapShot.insurerWording.checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page", viewPolicySnapShot.insurerWording.getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 15", "Scenario 15 Ended");

			// Asserting if EQ deductible value is calculated correctly on View Policy
			// Snapshot Page
			Assertions.addInfo("Scenario 16",
					"Asserting if EQ deductible value is calculated correctly on View Policy Snapshot Page");
			Assertions.verify(
					viewPolicySnapShot.discountsValue.formatDynamicPath("Earthquake").getData()
							.substring(5,
									viewPolicySnapShot.discountsValue.formatDynamicPath("Earthquake").getData().length()
											- 1)
							.replace(",", "").contains(calculatedeqDedValue),
					true, "View Policy Snapshot Page", "EQ Deductible is calculated based on EQ% * CoverageA value",
					false, false);
			Assertions.addInfo("Scenario 16", "Scenario 16 Ended");

			// Click on back button
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(2);
			viewPolicySnapShot.goBackButton.click();

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
			// Asserting warning message SC state when YOC less than
			// 1970 for Endorsement, The warning
			// message is 'Due to a year built prior to 1970 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 17", "Asserting warning message when yoc is less than 1970");
			Assertions.verify(dwellingPage.protectionClassWarMsg.getData().contains("year built prior to 1970"), true,
					"Dwelling page",
					"The warning message, " + dwellingPage.protectionClassWarMsg.getData() + " is displayed", false,
					false);
			Assertions.addInfo("Scenario 17", "Scenario 17 Ended");

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
			// Asserting warning message SC state when YOC less than
			// 1970 for Rewrite, The warning
			// message is 'Due to a year built prior to 1970 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 18", "Asserting warning message when yoc is less than 1970");
			Assertions.verify(dwellingPage.protectionClassWarMsg.getData().contains("year built prior to 1970"), true,
					"Dwelling page",
					"The warning message, " + dwellingPage.protectionClassWarMsg.getData() + " is displayed", false,
					false);
			Assertions.addInfo("Scenario 18", "Scenario 18 Ended");

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
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.addInfo("Account Overview Page", "Rewrite Quote Number is : " + quoteNumber);

/*
			// Adding the ticket IO-21999
			// Logout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page",
					"Logged in as producer " + setUpData.get("NahoProducer") + " successfully");
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer home page loaded successfully", false, false);

			// Search the quote
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Searched the quote " + quoteNumber + " successfully");
			Assertions.verify(accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click on upload prebind documents button
			accountOverviewPage.uploadPreBindDocuments.scrollToElement();
			accountOverviewPage.uploadPreBindDocuments.click();
			Assertions.passTest("Account Overview Page", "Clicked on upload prebind documents button");
			Assertions.verify(policyDocumentsPage.addDocumentButton.checkIfElementIsDisplayed(), true,
					"Quote Documents Page", "Quote Documents Page loaded successfully", false, false);

			// Click on add document button
			policyDocumentsPage.addDocumentButton.scrollToElement();
			policyDocumentsPage.addDocumentButton.click();
			Assertions.passTest("Policy Documents Page", "Clicked on add document button");

			String fileName = testData.get("FileNameToUpload");
			if (StringUtils.isBlank(fileName)) {
				Assertions.failTest("Upload File", "Filename is blank");
			}
			String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
			waitTime(8);
			if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
					&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
				policyDocumentsPage.chooseDocument.setData(new File(uploadFileDir + fileName).getAbsolutePath());
				waitTime(5);// Adding wait time to load the element
				System.out.println("Choose document");
			} else {
				policyDocumentsPage.chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
				waitTime(5);// Adding wait time to load the element

			}
			policyDocumentsPage.waitTime(4);// wait time is needed to load the element

			// Click on document category arrow
			policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).waitTillPresenceOfElement(60);
			policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
			policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
			policyDocumentsPage.waitTime(3);// wait time is needed to load the element

			// Verify the options present in drop down as producer
			Assertions.verify(
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Due Diligence", 2)
							.checkIfElementIsDisplayed(),
					true, "Policy Documents Page", "The Option Due Diligence displayed is verified", false, false);
			Assertions.verify(
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
							.checkIfElementIsDisplayed(),
					true, "Policy Documents Page", "The Option Subscription Agreement displayed is verified", false,
					false);
			Assertions.verify(
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Signed Quote Application", 2)
							.checkIfElementIsDisplayed(),
					true, "Policy Documents Page", "The Option Signed Quote Application displayed is verified", false,
					false);

			// Click on cancel
			policyDocumentsPage.cancelButtonUAT.scrollToElement();
			policyDocumentsPage.cancelButtonUAT.click();
			// End of the ticket IO-21999

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM successfully");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Search the quote
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy " + policyNumber + " successfully");
			Assertions.verify(policySummaryPage.stopPolicyRewrite.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Policy Summary Page loaded successfully", false, false);
*/
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
			policyRenewalPage.continueRenewal.scrollToElement();
			policyRenewalPage.continueRenewal.click();

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
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
				Assertions.passTest("Policy Summary Page", "Clicked on renewal link successfully");
			}

			// Getting the renewal quote number
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.addInfo("Account Overview Page", "Renewal Quote Number is : " + quoteNumber);

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
			homePage.refreshPage();
			homePage.searchQuote(quoteNumber);
			homePage.refreshPage();
			Assertions.passTest("Home Page", "Renewal quote searched successfully");

			// Click on release renewal producer
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on release renewal to producer successfully");
/*
			// Adding the ticket IO-21999
			// Logout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page",
					"Logged in as producer " + setUpData.get("NahoProducer") + " successfully");
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer home page loaded successfully", false, false);

			// Search the quote
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Searched the quote " + quoteNumber + " successfully");
			Assertions.verify(accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click on upload pre-bind documents button
			accountOverviewPage.uploadPreBindDocuments.scrollToElement();
			accountOverviewPage.uploadPreBindDocuments.click();
			Assertions.passTest("Account Overview Page", "Clicked on upload prebind documents button");
			Assertions.verify(policyDocumentsPage.addDocumentButton.checkIfElementIsDisplayed(), true,
					"Quote Documents Page", "Quote Documents Page loaded successfully", false, false);

			// Click on add document button
			policyDocumentsPage.addDocumentButton.scrollToElement();
			policyDocumentsPage.addDocumentButton.click();
			Assertions.passTest("Policy Documents Page", "Clicked on add document button");

			String fileName1 = testData.get("FileNameToUpload");
			if (StringUtils.isBlank(fileName1)) {
				Assertions.failTest("Upload File", "Filename is blank");
			}
			String uploadFileDir1 = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
			waitTime(8);
			if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
					&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
				policyDocumentsPage.chooseDocument.setData(new File(uploadFileDir1 + fileName1).getAbsolutePath());
				waitTime(5);// Adding wait time to load the element
				System.out.println("Choose document");
			} else {
				policyDocumentsPage.chooseFile.setData(new File(uploadFileDir1 + fileName1).getAbsolutePath());
				waitTime(5);// Adding wait time to load the element

			}
			policyDocumentsPage.waitTime(8);// wait time is needed to load the element

			// Click on document category arrow
			policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).waitTillPresenceOfElement(60);
			policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
			policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
			policyDocumentsPage.waitTime(3);// wait time is needed to load the element

			// Verify the options present in drop down as producer
			Assertions.verify(
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Due Diligence", 2)
							.checkIfElementIsDisplayed(),
					true, "Policy Documents Page", "The Option Due Diligence displayed is verified", false, false);
			Assertions.verify(
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
							.checkIfElementIsDisplayed(),
					true, "Policy Documents Page", "The Option Subscription Agreement displayed is verified", false,
					false);
			Assertions.verify(
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Signed Quote Application", 2)
							.checkIfElementIsDisplayed(),
					true, "Policy Documents Page", "The Option Signed Quote Application displayed is verified", false,
					false);

			// Click on cancel
			policyDocumentsPage.cancelButtonUAT.scrollToElement();
			policyDocumentsPage.cancelButtonUAT.click();
			// End of the ticket IO-21999

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM successfully");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Search the quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the quote " + quoteNumber + " successfully");
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
*/
			// Click on edit building link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			// Click on review dwelling button
			Assertions.verify(dwellingPage.createQuote.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			// IO-2010
			// Checking absence of warning message SC state when YOC less than
			// 1970 for Renewal, The warning
			// message is 'Due to a year built prior to 1970 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 19", "Checking absence of warning message when yoc is less than 1970");

			Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsPresent(), false, "Dwelling Page",
					"The warning message not displayed ", false, false);
			Assertions.addInfo("Scenario 19", "Scenario 19 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBSCTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBSCTC001 ", "Executed Successfully");
			}
		}
	}
}
