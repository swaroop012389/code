/** Program Description: 1.To Verify the Sinkhole default value as None and create policy check the selected option is available in Quote Document
 *  					 2.IO-20460 - External requested cancellations are throwing stack trace error
 *  					 3.Check the Premium Overridden not done scenario  - Quote with Bound status
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 03/25/2021
 **/

package com.icat.epicenter.test.naho.regression.NSNB;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RequestCancellationPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class NBFLTC005 extends AbstractNAHOTest {

	public NBFLTC005() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/FL005.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		LoginPage loginPage = new LoginPage();
		RequestCancellationPage requestCancellationPage = new RequestCancellationPage();
		BuildingNoLongerQuoteablePage noLongerQuoteable = new BuildingNoLongerQuoteablePage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();

		int dataValue1 = 0;
		int dataValue2 = 1;
		double empaServiceCharge = 2.00;
		Map<String, String> testData = data.get(dataValue1);
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
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// IO-2010
			// Asserting warning message FL state when YOC less than
			// 1980, The warning
			// message is 'Due to a year built prior to 1980 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 01", "Asserting warning message when yoc is less than 1980");
			Assertions.verify(
					dwellingPage.protectionClassWarMsg
							.formatDynamicPath(
									"Due to a year built prior to 1980 this risk is ineligible for coverage.")
							.getData()
							.contains("Due to a year built prior to 1980 this risk is ineligible for coverage."),
					true, "Dwelling page",
					"The warning message, "
							+ dwellingPage.protectionClassWarMsg
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
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			Assertions.addInfo("Scenario 02",
					"Verifying NS and aowh deductibles minimum values and the values present in the dropdown greater than default values");
			// Assert NS deductible and AOWH Minimum Value
			Assertions.verify(createQuotePage.namedStormData.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Named Storm Deductible Minimum Value : " + createQuotePage.namedStormData.getData(), false, false);
			String aowhminValue = createQuotePage.aowhDeductibleData.getData().replace("$", "").replace(",", "")
					.replace("%", "").replace(" (Override)", "");
			double daowhMinValue = Double.parseDouble(aowhminValue);
			Assertions.verify(createQuotePage.aowhDeductibleData.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"The AOWH Default and Minimum Value : " + createQuotePage.aowhDeductibleData.getData(), false,
					false);

			double dCovAValue = Double.parseDouble(testData.get("L1D1-DwellingCovA"));
			// Verifying The aowh drop down has the values greater than default value
			for (int j = 1; j < 4; j++) {
				int dataValuej = j;
				testData = data.get(dataValuej);
				createQuotePage.aowhDeductibleArrow.scrollToElement();
				createQuotePage.aowhDeductibleArrow.click();
				createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath(testData.get("AOWHDeductibleValue"))
						.scrollToElement();
				createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath(testData.get("AOWHDeductibleValue")).click();
				String aowhselectedValue = testData.get("AOPDeductibleValue").replace("%", "");
				double daowhselectedValue = Double.parseDouble(aowhselectedValue);
				double aowhCalculatedValue = dCovAValue * daowhselectedValue;
				Assertions.passTest("Create Quote Page", "The AOWH Deductible Value :  "
						+ createQuotePage.aowhDeductibleData.getData() + " displayed is verified");
				Assertions.verify(aowhCalculatedValue > daowhMinValue, true, "Create Quote Page",
						"The AOWH Deductible Value : " + createQuotePage.aowhDeductibleData.getData()
								+ " is greater than Minimum AOWH deductible is verified",
						false, false);

			}

			// Assert Sinkhole default value is None
			Assertions.verify(createQuotePage.sinkholeData.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Sinkhole Default value is " + createQuotePage.sinkholeData.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			testData = data.get(dataValue1);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			if (buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsPresent()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Asserting and validating warning message when prior loss is sinkhole/CGCC
			// "The account is in-eligible due to a
			// Sinkhole or Catastrophic Ground Cover Collapse reported loss at the property.
			// //Ticket IO-20816
			Assertions.addInfo("Scenario 03", "Asserting warning message");
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath(
									"Sinkhole or Catastrophic Ground Cover Collapse reported loss at the property.")
							.getData().contains(
									"a Sinkhole or Catastrophic Ground Cover Collapse reported loss at the property."),
					true, "Create Quote Page",
					"The Warning message " + createQuotePage.warningMessages
							.formatDynamicPath(
									"Sinkhole or Catastrophic Ground Cover Collapse reported loss at the property.")
							.getData() + " is displayed verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// IO-20816
			// Asserting and Verifying hard stop message when dwelling 40 years and older
			// and they have stated that the HVAC or Electrical is more than 15 years old
			// (Is it possible to get this validation to trigger on the building page?)
			Assertions.addInfo("Scenario 04", "Asserting and Verifying hard stop message");
			Assertions.verify(createQuotePage.warningMessages.formatDynamicPath(
					"quoted building being 40 years or older with no HVAC/Electrical updates in the last 15 years.")
					.getData().contains("40 years or older with no HVAC/Electrical updates in the last 15 years."),
					true, "Create Quote Page",
					"The hard stop message  " + createQuotePage.warningMessages.formatDynamicPath(
							"quoted building being 40 years or older with no HVAC/Electrical updates in the last 15 years.")
							.getData() + "is displyed",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			// IO-20816 ended

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Asserting SLTF value on account overview page
			Assertions.addInfo("Scenario 05", "Verifying sltf values on account overview Page");
			String premiumOnAccountOverviewPage = accountOverviewPage.premiumValue.getData();
			String feesOnAccountOverviewPage = accountOverviewPage.feesValue.getData();
			String sltfValueAccountOverviewPage = accountOverviewPage.sltfValue.getData().replace(",", "");
			String surplusContributionAccountOverviewPage = accountOverviewPage.surplusContibutionValue.getData();

			System.out.println("premiumOnAccountOverviewPage " + premiumOnAccountOverviewPage);
			System.out.println("feesOnAccountOverviewPage " + feesOnAccountOverviewPage);
			System.out.println("sltfValueAccountOverviewPage " + sltfValueAccountOverviewPage);
			System.out.println("surplusContributionAccountOverviewPage " + surplusContributionAccountOverviewPage);

			Assertions.passTest("Account Overview Page",
					"The Actual Surplus Lines Taxes & Fees : " + sltfValueAccountOverviewPage);
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
			double d_sltfValueempa = d_sltfValueOnAccountOverviewPage + empaServiceCharge;
			Assertions.passTest("Account Overview Page",
					"The Calculated Surplus Lines Taxes & Fees : " + "$" + df.format(d_sltfValueempa));

			double d_sltfValueAccountOverviewPage = Double.parseDouble(sltfValueAccountOverviewPage.replace("$", ""));
			if (Precision.round(
					Math.abs(Precision.round(d_sltfValueAccountOverviewPage, 2) - Precision.round(d_sltfValueempa, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual and Calculated SLTF Values are matching and calculated according to 5%  SLTF value including FSLSO Service Fee and EMPA Service Fee");
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Asserting declinations column on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");

			// Asserting SLTF value on view print full quote page
			Assertions.addInfo("Scenario 06", "Verifying SLTF,FSLSO,EMPA surcharge in View/Print Full Quote Page");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote page loaded successfully", false, false);
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData();
			String inspectionFeeValue = viewOrPrintFullQuotePage.inspectionFee.getData();
			String policyFeeValue = viewOrPrintFullQuotePage.policyFee.getData();
			String sltfValue = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace(",", "");
			String surplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData();

			Assertions.passTest("View/Print Full Quote Page", "The Actual Surplus Lines Tax : " + sltfValue);

			double d_premiumValue = Double.parseDouble(premiumValue.replace("$", "").replace(",", ""));
			double d_inspectionFeeValue = Double.parseDouble(inspectionFeeValue.replace("$", "").replace(",", ""));
			double d_policyFeeValue = Double.parseDouble(policyFeeValue.replace("$", "").replace(",", ""));
			double d_surplusContribution = Double.parseDouble(surplusContribution.replace("$", "").replace(",", ""));
			testData = data.get(dataValue2);
			double d_sltfPercentageValuevpfq = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_sltfValue = (d_premiumValue + d_inspectionFeeValue + d_policyFeeValue + d_surplusContribution)
					* d_sltfPercentageValuevpfq;
			Assertions.passTest("View/Print Full Quote Page",
					"The Calculated Surplus Lines Tax : " + "$" + df.format(d_sltfValue));

			double d_actualsltfValue = Double.parseDouble(sltfValue.replace("$", ""));
			if (Precision.round(Math.abs(Precision.round(d_actualsltfValue, 2) - Precision.round(d_sltfValue, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Actual and Calculated SLTF Values are matching and calculated according to 4.94% SLTF value");
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			// Calculate FSLSO Service Fee
			testData = data.get(dataValue1);
			double d_fslsoservicePercentage = Double.parseDouble(testData.get("FSLSOServiceFeePercentage"));
			double d_fslsocalculatedValue = (d_premiumValue + d_inspectionFeeValue + d_policyFeeValue)
					* d_fslsoservicePercentage;
			Assertions.passTest("View/Print Full Quote Page",
					"The Calculated FSLSO Service Fee : " + "$" + df.format(d_fslsocalculatedValue));

		String actualFslsoServiceFee = viewOrPrintFullQuotePage.fslsoServiceFee.getData();
		Assertions.passTest("View/Print Full Quote Page", "The Actual FSLSO Service Fee : " + actualFslsoServiceFee);

			double d_actualFslsoServiceFee = Double.parseDouble(actualFslsoServiceFee.replace("$", ""));
			if (Precision.round(
					Math.abs(Precision.round(d_actualFslsoServiceFee, 2) - Precision.round(d_fslsocalculatedValue, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Actual and Calculated FSLSO Service Fee Values are matching acording the 0.06% FSLSO Service Fee");
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			// Assert EMPA Service Charge
			Assertions.verify(viewOrPrintFullQuotePage.empaSurcharge.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "The EMPA Service Charge : "
							+ viewOrPrintFullQuotePage.empaSurcharge.getData() + " displayed is verified",
					false, false);

			viewOrPrintFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 06", "Scenario 06  Ended");

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Bind Referral
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			// Asserting policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Asserting Sinkhole and cgcc availability in quote document
			policySummaryPage.quoteNoLink.formatDynamicPath(quoteNumber).scrollToElement();
			policySummaryPage.quoteNoLink.formatDynamicPath(quoteNumber).click();

			Assertions.addInfo("Scenario 07",
					"Verifying Sinkhole Included and CGCC are Not Selected,Producer Details,Premium details in Quote Document Page");

			Assertions.passTest("Quote Document Page", "Quote Document Page loaded successfully");

			Assertions.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData().equals("Included"),
					true, "Quote Document Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("21").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData() + " is verified",
					false, false);

			Assertions.verify(
					viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData().equals("Not Available"),
					true, "Quote Document Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("23").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData() + " is verified",
					false, false);

			// Assert Producer Details
			Assertions.verify(viewOrPrintFullQuotePage.producerDetails.checkIfElementIsDisplayed(), true,
					"Quote Document Page", "The Name and Address of Producer is "
							+ viewOrPrintFullQuotePage.producerDetails.getData() + " displayed is verified",
					false, false);

			// Assert the Fees Details
			Assertions.verify(viewOrPrintFullQuotePage.premiumValue.checkIfElementIsDisplayed(), true,
					"Quote Document Page",
					"The Premium Value : " + viewOrPrintFullQuotePage.premiumValue.getData() + " displayed is verified",
					false, false);

			Assertions.verify(viewOrPrintFullQuotePage.policyFee.checkIfElementIsDisplayed(), true,
					"Quote Document Page",
					"The Policy Fee : " + viewOrPrintFullQuotePage.policyFee.getData() + " displayed is verified",
					false, false);

			Assertions
					.verify(viewOrPrintFullQuotePage.inspectionFee.checkIfElementIsDisplayed(), true,
							"Quote Document Page", "The Inspection Fee : "
									+ viewOrPrintFullQuotePage.inspectionFee.getData() + " displayed is verified",
							false, false);

			Assertions.verify(viewOrPrintFullQuotePage.surplusLinesTaxesValue.checkIfElementIsDisplayed(), true,
					"Quote Document Page", "The Surplus Lines Tax : "
							+ viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData() + " displayed is verified",
					false, false);

			Assertions.verify(viewOrPrintFullQuotePage.fslsoServiceFee.checkIfElementIsDisplayed(), true,
					"Quote Document Page", "The FSLSO Service Fee : "
							+ viewOrPrintFullQuotePage.fslsoServiceFee.getData() + " displayed is verified",
					false, false);

			Assertions
					.verify(viewOrPrintFullQuotePage.installmentFee.checkIfElementIsDisplayed(), true,
							"Quote Document Page", "The Installment Fee : "
									+ viewOrPrintFullQuotePage.installmentFee.getData() + " displayed is verified",
							false, false);

			Assertions.verify(viewOrPrintFullQuotePage.minimumEarnedPremium.checkIfElementIsDisplayed(), true,
					"Quote Document Page", "The Minimum Earned Premium : "
							+ viewOrPrintFullQuotePage.minimumEarnedPremium.getData() + " displayed is verified",
					false, false);

			// EMPA Service charge assertion
			Assertions.verify(viewOrPrintFullQuotePage.empaSurcharge.checkIfElementIsDisplayed(), true,
					"Quote Document Page", "The EMPA Service Charge : "
							+ viewOrPrintFullQuotePage.empaSurcharge.getData() + " displayed is verified",
					false, false);

			Assertions
					.verify(viewOrPrintFullQuotePage.totalPremiumValue.checkIfElementIsDisplayed(), true,
							"Quote Document Page", "The Total Premium : "
									+ viewOrPrintFullQuotePage.totalPremiumValue.getData() + " displayed is verified",
							false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on close button
			viewOrPrintFullQuotePage.closeButton.scrollToElement();
			viewOrPrintFullQuotePage.closeButton.click();
			Assertions.passTest("Quote Document Page", "Clicked on close button");

			// Create a BufferedWriter to write to the CSV file
			String BasePath = EnvironmentDetails.getEnvironmentDetails().getString("CSVFilePath");
			String filename = "FL005.csv";
			String fullPath = BasePath + filename;

			try {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {

					// Write the header
					writer.write("Quote Number,Wind Premium,AOP Premium,Liability Premium,Earthquake Premium");
					writer.newLine();

					// Write data rows
					writer.write(quoteNumber + "," + testData.get("WindPremiumOverride") + ","
							+ testData.get("AOPPremiumOverride") + "," + testData.get("LiabilityPremiumOverride")
							+ ",");
				}

				Assertions.passTest("Override Premium and Fees Page", "CSV file created successfully");

			} catch (IOException e) {
				e.printStackTrace();
			}

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Clicked on home link");

			// Click on uploadOverride button
			homePage.uplaodOverride.scrollToElement();
			homePage.uplaodOverride.click();
			Assertions.passTest("Home Page", "Clicked on upload override button");

			// click on naho radio button
			overridePremiumAndFeesPage.submit.formatDynamicPath(1).waitTillPresenceOfElement(60);
			overridePremiumAndFeesPage.submit.formatDynamicPath(1).scrollToElement();
			Assertions.verify(overridePremiumAndFeesPage.submit.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override premium and fees page loaded successfully", false,
					false);
			overridePremiumAndFeesPage.NahoRadioButton.scrollToElement();
			overridePremiumAndFeesPage.NahoRadioButton.click();
			Assertions.passTest("Override Premium and Fees Page", "NAHO product selected successfully");

			// upload csv file
			testData = data.get(dataValue1);
			overridePremiumAndFeesPage.csvFileUpload(testData.get("CSVFileUpload"));
			overridePremiumAndFeesPage.submit.formatDynamicPath(2).scrollToElement();
			overridePremiumAndFeesPage.submit.formatDynamicPath(2).click();
			Assertions.verify(overridePremiumAndFeesPage.continueButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "CSV file uploaded successfully", false, false);

			// Asserting and verifying warning message
			Assertions.addInfo("Scenario 08", "Asserting and verifying warning message");
			Assertions.verify(
					overridePremiumAndFeesPage.warningMessage
							.formatDynamicPath("This quote is not in a state that allows premium overrides").getData()
							.contains("This quote is not in a state that allows premium overrides"),
					true, "Override Premium and Fees Page",
					"The warning message is " + overridePremiumAndFeesPage.warningMessage
							.formatDynamicPath("This quote is not in a state that allows premium overrides").getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");
			overridePremiumAndFeesPage.continueButton.waitTillPresenceOfElement(60);
			overridePremiumAndFeesPage.continueButton.scrollToElement();

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Clicked on home link");

			// Searching bound quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Bound quote searched successfully");

			// verifying the absence of confirmation message "This quote has a premium
			// adjustment. Please review to determine if the premium adjustment should be
			// reapplied."
			Assertions.addInfo("Scenario 09", "Checking the absence of premium adjustment confirmation message");
			Assertions.verify(accountOverviewPage.premiumWarningMessage.checkIfElementIsPresent(), false,
					"Account Overview Page", "Premium adjustment message not deisplayed", false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// after overridden premium values should not be changed
			String boundPremium = accountOverviewPage.premiumValue.getData();
			String boundIcatFees = accountOverviewPage.icatFees.getData();
			String boundSLTF = accountOverviewPage.sltfValue.getData().replace(",", "");
			String boundSurplusContribution = accountOverviewPage.surplusContibutionValue.getData();

			// Asserting and verifying the Premium value, SLTF and Surplus Contribution
			// should remains the same value, Premium should not be overridden. on account
			// overview page
			Assertions.addInfo("Scenario 10",
					"Asserting and verifying the Premium value, SLTF and Surplus Contribution should remains the same value, Premium should not be overridden");
			Assertions.verify(boundPremium, premiumOnAccountOverviewPage, "Account Overview Page",
					"Premium value not overridden, premium value is " + boundPremium, false, false);
			Assertions.verify(boundIcatFees, feesOnAccountOverviewPage, "Account Overview Page",
					"IcatFee not overridden, premium value is " + boundIcatFees, false, false);
			Assertions.verify(boundSLTF, sltfValueAccountOverviewPage, "Account Overview Page",
					"SLTF value not overridden, premium value is " + boundSLTF, false, false);
			Assertions.verify(boundSurplusContribution, surplusContributionAccountOverviewPage, "Account Overview Page",
					"Surplus Contribution value not overridden, premium value is " + boundSurplusContribution, false,
					false);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Click o view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view or print full quote link");

			// Asserting and verifying the Premium value, SLTF and Surplus Contribution
			// should remains the same value, Premium should not be overridden. on view
			// print full quote page
			Assertions.addInfo("Scenario 11",
					"Asserting and verifying the Premium value, SLTF and Surplus Contribution should remains the same value, Premium should not be overridden. on view print full quote page");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote page loaded successfully", false, false);
			String vpfqPremiumValue = viewOrPrintFullQuotePage.premiumValue.getData();
			String vpfqInspectionFeeValue = viewOrPrintFullQuotePage.inspectionFee.getData();
			String vpfqPolicyFeeValue = viewOrPrintFullQuotePage.policyFee.getData();
			String vpfqsltfValue = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace(",", "");
			String vpfqSurplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData();

			Assertions.verify(vpfqPremiumValue, premiumValue, "View Print Full Quote Page",
					"Premium value not overridden, premium value is " + vpfqPremiumValue, false, false);
			Assertions.verify(vpfqInspectionFeeValue, inspectionFeeValue, "View Print Full Quote Page",
					"Insepection Fee not overridden, premium value is " + vpfqInspectionFeeValue, false, false);
			Assertions.verify(vpfqPolicyFeeValue, policyFeeValue, "View Print Full Quote Page",
					"Policy Fee not overridden, premium value is " + vpfqPolicyFeeValue, false, false);

			Assertions.verify(vpfqsltfValue, sltfValue, "View Print Full Quote Page",
					"SLTF value not overridden, premium value is " + vpfqsltfValue, false, false);
			Assertions.verify(vpfqSurplusContribution, surplusContribution, "View Print Full Quote Page",
					"Surplus Contribution value not overridden, premium value is " + vpfqSurplusContribution, false,
					false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Print Full Quote Page", "Clicked on back button");

			// Asserting premium overridden message;
			Assertions.addInfo("Scenario 12", "Absence of  premium overridden message on note bar");
			Assertions.verify(accountOverviewPage.noteBarMessage.getData().contains("Premium overridden as follows:"),
					false, "Account Overview Page", "The premium overridden message is not displayed", false, false);
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in in as External User
			// Adding for Ticket IO-20460 - External requested cancellations are throwing
			// stack trace error
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as External User successfully");

			// Search Policy Number
			Assertions.addInfo("Scenario 13",
					"IO-20460 - External requested cancellations are throwing stack trace error");
			testData = data.get(dataValue1);
			homePage.searchPolicyByProducer(policyNumber);

			// Click on Request Cancellation Link
			policySummaryPage.requestCancellationLink.scrollToElement();
			policySummaryPage.requestCancellationLink.click();

			// Enter the details for Cancellation
			requestCancellationPage.enterRequestCancellationDetails(testData);
			Assertions.verify(requestCancellationPage.cancellationRequestMsg.checkIfElementIsDisplayed(), true,
					"Request Cancellation Page", "Request cancellation Message is : "
							+ requestCancellationPage.cancellationRequestMsg.getData() + " displayed and verified",
					false, false);
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBFLTC005 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBFLTC005 ", "Executed Successfully");
			}
		}
	}

}
