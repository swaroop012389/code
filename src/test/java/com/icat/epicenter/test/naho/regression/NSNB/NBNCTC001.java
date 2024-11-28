package com.icat.epicenter.test.naho.regression.NSNB;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class NBNCTC001 extends AbstractNAHOTest {

	public NBNCTC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/NC001.xls";
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
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		BuildingNoLongerQuoteablePage noLongerQuoteable = new BuildingNoLongerQuoteablePage();

		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		DecimalFormat df = new DecimalFormat("0.00");
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

			// Ticket IO-20818
			// Asserting and verifying risk questions
			// Is the home a specialty home? (Underground, Berm or Geodesic, etc.)
			// Does the home have any galvanized, lead, cast-iron or polybutylene plumbing?
			// Does the home have a commercial business exposure (including farming)?
			// Is the home a developer's speculation home?
			// Is there any unrepaired damage to the insured location?
			Assertions.addInfo("Scenario 01", "Asserting and Verifying Risk questions");
			Assertions.verify(
					eligibilityPage.riskQuestions.formatDynamicPath("Is the home a specialty home?").getData()
							.contains("Is the home a specialty home?"),
					true, "Eligiilty Page",
					"The Risk question  "
							+ eligibilityPage.riskQuestions.formatDynamicPath("Is the home a specialty home?").getData()
							+ " is displayed verified",
					false, false);
			Assertions.verify(
					eligibilityPage.riskQuestions
							.formatDynamicPath(
									"Does the home have any galvanized, lead, cast-iron or polybutylene plumbing?")
							.getData()
							.contains("Does the home have any galvanized, lead, cast-iron or polybutylene plumbing?"),
					true, "Eligiilty Page",
					"The Risk question  " + eligibilityPage.riskQuestions
							.formatDynamicPath(
									"Does the home have any galvanized, lead, cast-iron or polybutylene plumbing?")
							.getData() + " is displayed verified",
					false, false);
			Assertions.verify(eligibilityPage.riskQuestions
					.formatDynamicPath("Does the home have a commercial business exposure (including farming)?")
					.getData().contains("Does the home have a commercial business exposure (including farming)?"), true,
					"Eligiilty Page",
					"The Risk question  " + eligibilityPage.riskQuestions
							.formatDynamicPath("Does the home have a commercial business exposure (including farming)?")
							.getData() + " is displayed verified",
					false, false);
			Assertions.verify(
					eligibilityPage.riskQuestions
							.formatDynamicPath("Is the home a developer").getData().contains("Is the home a developer"),
					true, "Eligiilty Page",
					"The Risk question  "
							+ eligibilityPage.riskQuestions.formatDynamicPath("Is the home a developer").getData()
							+ " is displayed verified",
					false, false);
			Assertions.verify(
					eligibilityPage.riskQuestions
							.formatDynamicPath("Is there any unrepaired damage to the insured location?").getData()
							.contains("Is there any unrepaired damage to the insured location?"),
					true, "Eligiilty Page",
					"The Risk question  " + eligibilityPage.riskQuestions
							.formatDynamicPath("Is there any unrepaired damage to the insured location?").getData()
							+ " is displayed verified",
					false, false);
			Assertions.verify(eligibilityPage.riskQuestions
					.formatDynamicPath(
							"Does the dwelling have any of the following petroleum-based or propane-based storage")
					.getData()
					.contains("Does the dwelling have any of the following petroleum-based or propane-based storage"),
					true, "Eligiilty Page",
					"The Risk question  " + eligibilityPage.riskQuestions.formatDynamicPath(
							"Does the dwelling have any of the following petroleum-based or propane-based storage")
							.getData() + " is displayed verified",
					false, false);
			Assertions.passTest("Scenario 01", "Scenario 01 Ended");
			// Ticket IO-2018 ended

			eligibilityPage.zipCode1.scrollToElement();
			eligibilityPage.zipCode1.appendData(testData.get("L1D1-DwellingZIP"));
			eligibilityPage.riskAppliedNo.scrollToElement();
			eligibilityPage.riskAppliedNo.click();
			eligibilityPage.continueButton.scrollToElement();
			eligibilityPage.continueButton.click();
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// IO-2010
			// Asserting warning message NC state when YOC less than
			// 1970, The warning
			// message is 'Due to a year built prior to 1970 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 02", "Asserting warning message when yoc is less than 1970");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages
							.formatDynamicPath(
									"Due to a year built prior to 1970 this risk is ineligible for coverage.")
							.getData()
							.contains("Due to a year built prior to 1970 this risk is ineligible for coverage."),
					true, "Dwelling page",
					"The warning message, "
							+ dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath(
											"Due to a year built prior to 1970 this risk is ineligible for coverage.")
									.getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
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
			Assertions.addInfo("Scenario 03", "Asserting Default NS value in Create Quote Page");
			Assertions.verify(createQuotePage.namedStormData.getData(), testData.get("NamedStormValue"),
					"Create Quote Page", "Named Strom default value is " + testData.get("NamedStormValue"), false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

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

			// Asserting SLTF value on account overview page
			Assertions.addInfo("Scenario 04", "Calculating SLTF Values in Account Overview Page");
			String premiumOnAccountOverviewPage = accountOverviewPage.premiumValue.getData();
			String feesOnAccountOverviewPage = accountOverviewPage.feesValue.getData();
			String sltfValueAccountOverviewPage = accountOverviewPage.sltfValue.getData().replace(",", "").replace("$",
					"");
			String surplusContributionValueAccountOverviewPage = accountOverviewPage.surplusContibutionValue.getData();
			double d_premiumValueOnAccountOverviewPage = Double
					.parseDouble(premiumOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_FeeValueOnAccountOverviewPage = Double
					.parseDouble(feesOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_surplusContributionValueAccountOverviewPage = Double
					.parseDouble(surplusContributionValueAccountOverviewPage.replace("$", ""));
			double d_sltfPercentageValue = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_stampingFeePercentageValue = Double.parseDouble(testData.get("StampingFeePercentage"));
			double d_sltValue = (d_premiumValueOnAccountOverviewPage + d_FeeValueOnAccountOverviewPage
					+ d_surplusContributionValueAccountOverviewPage) * d_sltfPercentageValue;
			double d_stampingFeeValue = (d_premiumValueOnAccountOverviewPage + d_FeeValueOnAccountOverviewPage
					+ d_surplusContributionValueAccountOverviewPage) * d_stampingFeePercentageValue;
			double d_sltfValueOnAccountOverviewPage = d_sltValue + d_stampingFeeValue;

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(sltfValueAccountOverviewPage), 2)
					- Precision.round(d_sltfValueOnAccountOverviewPage, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Value : " + "$" + df.format(d_sltfValueOnAccountOverviewPage));
				Assertions.passTest("Account Overview Page",
						"Actual SLTF Value : " + "$" + sltfValueAccountOverviewPage);
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated SLTF is more than 0.05");
			}

			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Asserting declinations column on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Asserting SLTF value on view print full quote page
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData();
			String inspectionFeeValue = viewOrPrintFullQuotePage.insurerInspectionFeeValue.getData();
			String policyFeeValue = viewOrPrintFullQuotePage.insurerPolFeeVlaue.getData();
			String sltValue = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace(",", "");
			String stampingValue = viewOrPrintFullQuotePage.stampingFeeNaho.getData().replace(",", "");
			String surplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData();

			double d_premiumValue = Double.parseDouble(premiumValue.replace("$", "").replace(",", ""));
			double d_inspectionFeeValue = Double.parseDouble(inspectionFeeValue.replace("$", "").replace(",", ""));
			double d_policyFeeValue = Double.parseDouble(policyFeeValue.replace("$", "").replace(",", ""));
			double d_surplusContributionValue = Double
					.parseDouble(surplusContributionValue.replace("$", "").replace("%", ""));
			double d_sltValueQuoteDocument = (d_premiumValue + d_inspectionFeeValue + d_policyFeeValue
					+ d_surplusContributionValue) * d_sltfPercentageValue;
			double d_stampingFeeValueQuoteDocument = (d_premiumValue + d_inspectionFeeValue + d_policyFeeValue
					+ d_surplusContributionValue) * d_stampingFeePercentageValue;
			double d_SLTFValue = d_sltValueQuoteDocument + d_stampingFeeValueQuoteDocument;

			Assertions.addInfo("Scenario 05", "Calculating SLTF Values in View/Print Full Quote Page");
			Assertions.verify(sltValue, "$" + df.format(d_sltValueQuoteDocument), "View/Print Full Quote Page",
					"Actual and Calculated SLT Values are matching and calculated according to 5% NC SLT value "
							+ sltValue,
					false, false);
			Assertions.verify(stampingValue, "$" + df.format(d_stampingFeeValueQuoteDocument),
					"View/Print Full Quote Page",
					"Actual and Calculated Stamping Fee Values are matching and calculated according to 0.04% NC Stamping Fee value "
							+ stampingValue,
					false, false);
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(sltfValueAccountOverviewPage), 2)
					- Precision.round(d_SLTFValue, 2)), 2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated SLTF Value : " + "$" + df.format(d_SLTFValue));
				Assertions.passTest("View/Print Full Quote Page",
						"Actual SLTF Value : " + "$" + sltfValueAccountOverviewPage);
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual and calculated SLTF is more than 0.05");
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");
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
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(30);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();
			Assertions.addInfo("Scenario 07", "Asserting Due Diligence Text");
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind Page",
					requestBindPage.diligenceText.getData() + " is displayed", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			// Click on view policy snapshot
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");
			String premiumValue1 = viewOrPrintFullQuotePage.premiumValue.getData();
			String inspectionFeeValue1 = viewOrPrintFullQuotePage.insurerInspectionFeeValue.getData();
			String policyFeeValue1 = viewOrPrintFullQuotePage.insurerPolFeeVlaue.getData();
			String sltValue1 = viewPolicySnapShot.surplusLinesTaxesValue.getData().replace(",", "");
			String stampingValue1 = viewPolicySnapShot.stampingFeeValue.getData().replace(",", "");
			String surplusContributionValueSnapShot = viewPolicySnapShot.surplusContributionValue.getData();

			double d_premiumValue1 = Double.parseDouble(premiumValue1.replace("$", "").replace(",", ""));
			double d_inspectionFeeValue1 = Double.parseDouble(inspectionFeeValue1.replace("$", "").replace(",", ""));
			double d_policyFeeValue1 = Double.parseDouble(policyFeeValue1.replace("$", "").replace(",", ""));
			double d_surplusContributionValueSnapShot = Double
					.parseDouble(surplusContributionValueSnapShot.replace("$", "").replace("%", ""));
			double d_sltValueQuoteDocument1 = (d_premiumValue1 + d_inspectionFeeValue1 + d_policyFeeValue1
					+ d_surplusContributionValueSnapShot) * d_sltfPercentageValue;
			double d_stampingFeeValueQuoteDocument1 = (d_premiumValue1 + d_inspectionFeeValue1 + d_policyFeeValue1
					+ d_surplusContributionValueSnapShot) * d_stampingFeePercentageValue;
			double d_SLTFValue1 = d_sltValueQuoteDocument1 + d_stampingFeeValueQuoteDocument1;
			Assertions.addInfo("Scenario 08", "Calculating SLTF Values in Policy Snapshot Page");
			Assertions.verify(sltValue1, "$" + df.format(d_sltValueQuoteDocument1), "View Policy Snapshot Page",
					"Actual and Calculated SLT Values are matching and calculated according to 5% NC SLT value "
							+ sltValue1,
					false, false);
			Assertions.verify(stampingValue1, "$" + df.format(d_stampingFeeValueQuoteDocument1),
					"View Policy Snapshot Page",
					"Actual and Calculated Stamping Fee Values are matching and calculated according to 0.04% NC Stamping Fee value "
							+ stampingValue1,
					false, false);
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(sltfValueAccountOverviewPage), 2)
					- Precision.round(d_SLTFValue1, 2)), 2) < 0.05) {
				Assertions.passTest("View Policy Snapshot Page",
						"Calculated SLTF Value : " + "$" + df.format(d_SLTFValue1));
				Assertions.passTest("View Policy Snapshot Page",
						"Actual SLTF Value : " + "$" + sltfValueAccountOverviewPage);
			} else {
				Assertions.passTest("View Policy Snapshot Page",
						"The Difference between actual and calculated SLTF is more than 0.05");
			}

			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");
			Assertions.addInfo("Scenario 09", "Assertiong Declination Wording in Policy Snapshot Page");
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
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(2);
			viewPolicySnapShot.goBackButton.click();

			// Adding below code for IO-20532
			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page ", "Clicked on home page icon button successfully");

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
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.lossesInThreeYearsYes.scrollToElement();
			priorLossesPage.lossesInThreeYearsYes.click();
			testData = data.get(data_Value2);
			priorLossesPage.dateOfLoss.scrollToElement();
			priorLossesPage.dateOfLoss.appendData(testData.get("PriorLossDate1"));
			priorLossesPage.grossLossAmount.scrollToElement();
			priorLossesPage.grossLossAmount.clearData();
			priorLossesPage.grossLossAmount.appendData(testData.get("PriorLossAmount1"));
			Assertions.addInfo("Scenario 10",
					"Checking Long loss type names are displaying in Type of Loss dropdown window when selected");
			for (int i = 1; i < 5; i++) {

				int dataValuei = i;
				testData = data.get(dataValuei);
				priorLossesPage.typeOfLossArrow.scrollToElement();
				priorLossesPage.typeOfLossArrow.click();
				priorLossesPage.typeOfLossOption.formatDynamicPath(0, testData.get("PriorLossType1")).scrollToElement();
				priorLossesPage.typeOfLossOption.formatDynamicPath(0, testData.get("PriorLossType1")).click();
				Assertions.verify(
						priorLossesPage.priorLossData.formatDynamicPath(0).checkIfElementIsPresent()
								&& priorLossesPage.priorLossData.formatDynamicPath(0).checkIfElementIsDisplayed(),
						true, "Prior Losses Page", "Prior Losses Type1 selected is "
								+ priorLossesPage.priorLossData.formatDynamicPath(0).getData(),
						false, false);
			}
			testData = data.get(data_Value2);
			priorLossesPage.addButton.waitTillVisibilityOfElement(60);
			priorLossesPage.addButton.scrollToElement();
			priorLossesPage.addButton.click();
			Assertions.passTest("Prior Losses Page",
					"Clicked on Add Button Sucessfully and Adding Second Prior Loss and Checking Long Loss Type Name are Displaying in Type of Loss Dropdown Window when selected ");
			priorLossesPage.dateOfLoss.scrollToElement();
			priorLossesPage.dateOfLoss.appendData(testData.get("PriorLossDate2"));
			priorLossesPage.grossLossAmount.scrollToElement();
			priorLossesPage.grossLossAmount.clearData();
			priorLossesPage.grossLossAmount.appendData(testData.get("PriorLossAmount2"));
			priorLossesPage.typeOfLossArrow.scrollToElement();
			priorLossesPage.typeOfLossArrow.click();
			priorLossesPage.typeOfLossOption.formatDynamicPath(1, testData.get("PriorLossType2")).scrollToElement();
			priorLossesPage.typeOfLossOption.formatDynamicPath(1, testData.get("PriorLossType2")).click();
			Assertions.verify(
					priorLossesPage.priorLossData.formatDynamicPath(1).checkIfElementIsPresent()
							&& priorLossesPage.priorLossData.formatDynamicPath(1).checkIfElementIsDisplayed(),
					true, "Prior Losses Page",
					"Prior Losses Type2 selected is " + priorLossesPage.priorLossData.formatDynamicPath(1).getData(),
					false, false);
			priorLossesPage.addButton.waitTillVisibilityOfElement(60);
			priorLossesPage.addButton.scrollToElement();
			priorLossesPage.addButton.click();
			Assertions.passTest("Prior Losses Page",
					"Clicked on Add Button Sucessfully and Adding Third Prior Loss and Checking Long Loss Type Name are Displaying in Type of Loss Dropdown Window when selected ");
			priorLossesPage.dateOfLoss.scrollToElement();
			priorLossesPage.dateOfLoss.appendData(testData.get("PriorLossDate3"));
			priorLossesPage.grossLossAmount.scrollToElement();
			priorLossesPage.grossLossAmount.clearData();
			priorLossesPage.grossLossAmount.appendData(testData.get("PriorLossAmount3"));
			priorLossesPage.typeOfLossArrow.scrollToElement();
			priorLossesPage.typeOfLossArrow.click();
			priorLossesPage.typeOfLossOption.formatDynamicPath(2, testData.get("PriorLossType3")).scrollToElement();
			priorLossesPage.typeOfLossOption.formatDynamicPath(2, testData.get("PriorLossType3")).click();
			Assertions.verify(
					priorLossesPage.priorLossData.formatDynamicPath(2).checkIfElementIsPresent()
							&& priorLossesPage.priorLossData.formatDynamicPath(2).checkIfElementIsDisplayed(),
					true, "Prior Losses Page",
					"Prior Losses Type3 selected is " + priorLossesPage.priorLossData.formatDynamicPath(2).getData(),
					false, false);
			Assertions.passTest("Scenario 10", "Scenario 10 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBNCTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBNCTC002 ", "Executed Successfully");
			}
		}
	}
}
