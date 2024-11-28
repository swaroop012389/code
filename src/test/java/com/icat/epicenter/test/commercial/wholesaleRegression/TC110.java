//Summary: Check if the Terrorism is included in taxes and fees calculated in NB quote and SLTF is not calculated and displayed for PB Endorsement
//Date: 10/Feb/2021
//Author: Swaroop

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC110 extends AbstractCommercialTest {

	public TC110() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID110.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ReferralPage referralPage = new ReferralPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		DecimalFormat df = new DecimalFormat("0.00");

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Click on user preference link and enter the details
			Assertions.addInfo("Home Page", "Entering User Preference Details");
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");

			// Add broker fees
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			preferenceOptionsPage.addBrokerFees(testData);
			Assertions.passTest("Preference Options Page", "Details entered successfully");

			// Navigate to homepage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("Home Page", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location Details Entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Location Details Entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Click on Get a quote
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}

			// Get quote for referral
			String quoteNumber1 = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Quote Number 1 is " + quoteNumber1);

			// Navigate to homepage and logout
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as Producer successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Search for approved quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber1);

			// Add assertions
			String premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			String icatFees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			String otherFees = accountOverviewPage.otherFees.getData().replace("$", "").replace(",", "");
			String surplusLinesandTaxes = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			String premiumPlusTaxesandFees = accountOverviewPage.totalPremiumValue.getData().replace("$", "")
					.replace(",", "");
			String surplusCntribution = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "");
			String sltfPercentage = testData.get("SurplusLinesTaxesPercentage");
			String fireMarshallTaxPercentage = testData.get("FireMarshallTaxPercentage");
			String surplusLinesServiceCharge = testData.get("SurplusLinesServiceCharge");
			String brokerFeePercentage = testData.get("BrokerFeeValue");

			// Printing values from account overview page
			Assertions.addInfo("Account Overview Page",
					"Asserting Premium,Icat fees,Broker Fees,SLTF,Premium Plus Taxes and Fees on account overview page");
			Assertions.passTest("Account Overview Page", "Premium is $" + premium);
			Assertions.passTest("Account Overview Page", "ICAT Fees is $" + icatFees);
			Assertions.passTest("Account Overview Page", "Broker Fees is $" + otherFees);
			Assertions.passTest("Account Overview Page", "Surplus Lines and Taxes Value is $" + surplusLinesandTaxes);
			Assertions.passTest("Account Overview Page",
					"Premium Plus Taxes and Fees Value is $" + premiumPlusTaxesandFees);
			Assertions.passTest("Account Overview Page", "Surplus Contribution valu is $" + surplusCntribution);

			// Converting string values to double
			double d_premium = Double.parseDouble(premium);
			double d_icatFees = Double.parseDouble(icatFees);
			double d_otherFees = Double.parseDouble(otherFees);
			double d_sltfPercentage = Double.parseDouble(sltfPercentage);
			double d_fireMarshallTaxPercentage = Double.parseDouble(fireMarshallTaxPercentage);
			double d_surplusLinesServiceCharge = Double.parseDouble(surplusLinesServiceCharge);
			double d_brokerFeePercentage = Double.parseDouble(brokerFeePercentage);
			double d_surplusCntribution = Double.parseDouble(surplusCntribution);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValue = d_premium * (d_brokerFeePercentage / 100);
			double fireMarshallTaxValue = (d_premium + d_icatFees + d_otherFees + d_surplusCntribution)
					* d_fireMarshallTaxPercentage;
			double sltfCalculatedValue = ((d_premium + d_icatFees + d_otherFees + d_surplusCntribution)
					* d_sltfPercentage) + fireMarshallTaxValue + d_surplusLinesServiceCharge;
			double premiumPlusTaxesandFeesValue = d_premium + d_icatFees + d_otherFees + sltfCalculatedValue
					+ d_surplusCntribution;

			// Asserting Actual and calculated broker/sltf/Premium values
			Assertions.addInfo("Account Overview Page",
					"Verifying  Actual and calculated SLTF,Total Premium values on Account overview page");
			Assertions.verify(otherFees, df.format(brokerFeeCalculatedValue), "Account Overview Page",
					"Actual and Calculated Broker Fee values are matching as per 5%", false, false);
			double d_surplusLinesandTaxes = Double.parseDouble(surplusLinesandTaxes);
			if (Precision.round(
					Math.abs(Precision.round(d_surplusLinesandTaxes, 2) - Precision.round(sltfCalculatedValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Actual SLTF Value :" + "$" + d_surplusLinesandTaxes);
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Value :" + "$" + df.format(sltfCalculatedValue));
			} else {

				Assertions.verify(d_surplusLinesandTaxes, sltfCalculatedValue, "Account Overview Page",
						"The Difference between actual  and calculated SLTF is more than 0.05", false, false);
			}

			double d_premiumPlusTaxesandFees = Double.parseDouble(premiumPlusTaxesandFees);
			if (Precision.round(Math.abs(
					Precision.round(d_premiumPlusTaxesandFees, 2) - Precision.round(premiumPlusTaxesandFeesValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual Total Premium Value :" + "$" + d_premiumPlusTaxesandFees);
				Assertions.passTest("Account Overview Page",
						"Calculated Total Premium Value :" + "$" + df.format(premiumPlusTaxesandFeesValue));
			} else {
				Assertions.verify(d_premiumPlusTaxesandFees, premiumPlusTaxesandFeesValue, "Account Overview Page",
						"The Difference between actual  and calculated Premium is more than 0.05", false, false);
			}

			// Click on view/print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");

			// Add assertions
			String premiumVPFQ = viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(5).getData()
					.replace("$", "").replace(",", "").replace("Premium", "");
			String inspecionFeeVPFQ = viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(",",
					"");
			String policyFeeVPFQ = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "");
			String brokerFeeVPFQ = viewOrPrintFullQuotePage.brokerFeeValue.getData().replace("$", "").replace(",", "");
			String sltfVPFQ = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "").replace(",",
					"");
			String slsfVPFQ = viewOrPrintFullQuotePage.surplusLinesServiceCharge.getData().replace("$", "").replace(",",
					"");
			String fireMarshallTaxVPFQ = viewOrPrintFullQuotePage.fireMarshallTax.getData().replace("$", "")
					.replace(",", "");
			String grandTotalVPFQ = viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(3).getData()
					.replace("$", "").replace(",", "").replace("Grand Total", "");
			String surplusContributionVPFQ = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");

			// Printing values from View/Print full quote page
			Assertions.addInfo("View/Print Full Quote Page",
					"Asserting Premium,Inspection fees,Policy Fees,Broker Fees,SLTF,Fire Marshall Tax Value,Surplus Lines Service Charge and Premium Plus Taxes and Fees on View/Print Full Quote Page");
			Assertions.passTest("View/Print Full Quote Page", "Premium is $" + premiumVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Inspection Fees is $" + inspecionFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Policy Fees is $" + policyFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Broker Fees is $" + brokerFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Surplus Lines and Taxes Value is $" + sltfVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Fire Marshall Tax Value is $" + fireMarshallTaxVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Surplus Lines Service Charge Value is $" + slsfVPFQ);
			Assertions.passTest("View/Print Full Quote Page",
					"Premium Plus Taxes and Fees Value is $" + grandTotalVPFQ);
			Assertions.passTest("View/Print Full Quote Page",
					"Surplus Contribution Value is $" + surplusContributionVPFQ);

			// Converting String values to double
			double d_premiumVPFQ = Double.parseDouble(premiumVPFQ);
			double d_inspecionFeeVPFQ = Double.parseDouble(inspecionFeeVPFQ);
			double d_policyFeeVPFQ = Double.parseDouble(policyFeeVPFQ);
			double d_brokerFeeVPFQ = Double.parseDouble(brokerFeeVPFQ);
			double d_slsfVPFQ = Double.parseDouble(slsfVPFQ);
			double d_surplusContributionVPFQ = Double.parseDouble(surplusContributionVPFQ);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValueVPFQ = d_premiumVPFQ * (d_brokerFeePercentage / 100);
			double fireMarshallTaxVPFQCalculatedValueVPFQ = ((d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ
					+ d_brokerFeeVPFQ + d_surplusContributionVPFQ)) * d_fireMarshallTaxPercentage;
			double sltfCalculatedValueVPFQ = (((d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ + d_brokerFeeVPFQ
					+ d_surplusContributionVPFQ)) * d_sltfPercentage);
			double premiumPlusTaxesandFeesValueVPFQ = d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ
					+ d_brokerFeeVPFQ + sltfCalculatedValueVPFQ + fireMarshallTaxVPFQCalculatedValueVPFQ + d_slsfVPFQ
					+ d_surplusContributionVPFQ;
			// Asserting Actual and calculated sltf/Premium values
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying  Actual and calculated Broker Fees,Fire Marshall Tax,SLTF,Total Premium values on View/Print Full Quote Page");
			Assertions.passTest("View/Print Full Quote Page", "Premium is $" + premiumVPFQ);
			Assertions.verify(brokerFeeVPFQ, df.format(brokerFeeCalculatedValueVPFQ), "View/Print Full Quote Page",
					"Actual and Calculated Broker Fee values are matching as per 5%", false, false);
			double d_fireMarshallTaxVPFQ = Double.parseDouble(fireMarshallTaxVPFQ);
			if (Precision.round(Math.abs(Precision.round(d_fireMarshallTaxVPFQ, 2)
					- Precision.round(fireMarshallTaxVPFQCalculatedValueVPFQ, 2)), 2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Actual Fire Marshall Tax is " + d_fireMarshallTaxVPFQ);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated Fire Marshall Tax is " + fireMarshallTaxVPFQCalculatedValueVPFQ);
				Assertions.passTest("View/Print Full Quote Page",
						"Actual and Calculated Fire Marshall Tax values are matching as per 0.3% for OR state");
			} else {
				Assertions.verify(d_fireMarshallTaxVPFQ, fireMarshallTaxVPFQCalculatedValueVPFQ,
						"View/Print Full Quote Page",
						"The Difference between Actual and Calculated Fire Marshall Tax values is more than 0.05",
						false, false);

			}
			double d_sltfVPFQ = Double.parseDouble(sltfVPFQ);
			if (Precision.round(Math.abs(Precision.round(d_sltfVPFQ, 2) - Precision.round(sltfCalculatedValueVPFQ, 2)),
					2) < 0.05) {

				Assertions.passTest("View/Print Full Quote Page", "Actual SLTF Value is " + d_sltfVPFQ);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated SLTF Value is " + sltfCalculatedValueVPFQ);
				Assertions.passTest("View/Print Full Quote Page",
						"Actual SLTF and Calculated SLTF are matching as per 2% for OR state");
			} else {
				Assertions.verify(d_sltfVPFQ, sltfCalculatedValueVPFQ, "View/Print Full Quote Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);
			}

			double d_grandTotalVPFQ = Double.parseDouble(grandTotalVPFQ);

			if (Precision.round(Math
					.abs(Precision.round(d_grandTotalVPFQ, 2) - Precision.round(premiumPlusTaxesandFeesValueVPFQ, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Actual Premium Value is " + d_grandTotalVPFQ);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated PremiumValue is " + premiumPlusTaxesandFeesValueVPFQ);

				Assertions.passTest("View/Print Full Quote Page",
						"Actual Total Premium and Calculated Total Premium are matching");
			} else {
				Assertions.verify(d_grandTotalVPFQ, premiumPlusTaxesandFeesValueVPFQ, "View/Print Full Quote Page",
						"The Difference between Actual Total Premium and Calculated Total Premium is more than 0.05",
						false, false);

			}
			Assertions.verify(
					viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(22).getData().contains("Included"),
					false, "View/Print Full Quote Page", "TRIA Coverage is not included when Terrorism is not selected",
					false, false);

			// Navigate to account overview page
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Create another quote and add Terrorism value
			Assertions.addInfo("Account Overview Page", "Creating Another Quote by Adding Terrorism Value");
			accountOverviewPage.scrollToTopPage();
			accountOverviewPage.editDeductibleAndLimits.waitTillVisibilityOfElement(60);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Deductibles and limits");
			testData = data.get(data_Value2);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Get quote for referral
			String quoteNumber2 = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Quote Number 2 is " + quoteNumber2);
			testData = data.get(data_Value1);

			// Add assertions
			String premium2 = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			String icatFees2 = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			String otherFees2 = accountOverviewPage.otherFees.getData().replace("$", "").replace(",", "");
			String surplusLinesandTaxes2 = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			String premiumPlusTaxesandFees2 = accountOverviewPage.totalPremiumValue.getData().replace("$", "")
					.replace(",", "");
			String surplusCntribution2 = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "");
			String sltfPercentage2 = testData.get("SurplusLinesTaxesPercentage");
			String fireMarshallTaxPercentage2 = testData.get("FireMarshallTaxPercentage");
			String surplusLinesServiceCharge2 = testData.get("SurplusLinesServiceCharge");

			// Printing values from account overview page
			Assertions.addInfo("Account Overview Page",
					"Asserting Premium,Icat fees,Broker Fees,SLTF,Premium Plus Taxes and Fees on account overview page");
			Assertions.passTest("Account Overview Page", "Premium is $" + premium2);
			Assertions.passTest("Account Overview Page", "ICAT Fees is $" + icatFees2);
			Assertions.passTest("Account Overview Page", "Broker Fees is $" + otherFees2);
			Assertions.passTest("Account Overview Page", "Surplus Lines and Taxes Value is $" + surplusLinesandTaxes2);
			Assertions.passTest("Account Overview Page",
					"Premium Plus Taxes and Fees Value is $" + premiumPlusTaxesandFees2);
			Assertions.passTest("Account Overview Page", "Surplus Contribution valu is $" + surplusCntribution2);
			// Converting string values to double
			double d_premium2 = Double.parseDouble(premium2);
			double d_icatFees2 = Double.parseDouble(icatFees2);
			double d_otherFees2 = Double.parseDouble(otherFees2);
			double d_sltfPercentage2 = Double.parseDouble(sltfPercentage2);
			double d_fireMarshallTaxPercentage2 = Double.parseDouble(fireMarshallTaxPercentage2);
			double d_surplusLinesServiceCharge2 = Double.parseDouble(surplusLinesServiceCharge2);
			double d_surplusCntribution2 = Double.parseDouble(surplusCntribution2);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValue2 = d_premium2 * (d_brokerFeePercentage / 100);
			double fireMarshallTaxValue2 = (d_premium2 + d_icatFees2 + d_otherFees2 + d_surplusCntribution2)
					* d_fireMarshallTaxPercentage2;
			double sltfCalculatedValue2 = ((d_premium2 + d_icatFees2 + d_otherFees2 + d_surplusCntribution2)
					* d_sltfPercentage2) + fireMarshallTaxValue2 + d_surplusLinesServiceCharge2;
			double premiumPlusTaxesandFeesValue2 = d_premium2 + d_icatFees2 + d_otherFees2 + sltfCalculatedValue2
					+ d_surplusCntribution2;

			// Asserting Actual and calculated broker/sltf/Premium values
			Assertions.addInfo("Account Overview Page",
					"Verifying  Actual and calculated broker fees,SLTF,Total Premium values on Account overview page");
			Assertions.verify(otherFees2, df.format(brokerFeeCalculatedValue2), "Account Overview Page",
					"Actual and Calculated Broker Fee values are matching as per 5%", false, false);

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusLinesandTaxes2), 2)
					- Precision.round(sltfCalculatedValue2, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Actual SLTF Value :" + "$" + surplusLinesandTaxes2);
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Value :" + "$" + df.format(sltfCalculatedValue2));
			} else {
				Assertions.verify(surplusLinesandTaxes2, sltfCalculatedValue2, "Account Overview Page",
						"The Difference between actual  and calculated SLTF is more than 0.05", false, false);
			}
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(premiumPlusTaxesandFees2), 2)
					- Precision.round(premiumPlusTaxesandFeesValue2, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual Total Premium Value :" + "$" + premiumPlusTaxesandFees2);
				Assertions.passTest("Account Overview Page",
						"Calculated Total Premium Value :" + "$" + df.format(premiumPlusTaxesandFeesValue2));
			} else {
				Assertions.verify(premiumPlusTaxesandFees2, premiumPlusTaxesandFeesValue2, "Account Overview Page",
						"The Difference between actual  and calculated Premium is more than 0.05", false, false);
			}

			// Click on view/print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");

			// Add assertions
			String premiumVPFQ2 = viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(5).getData()
					.replace("$", "").replace(",", "").replace("Premium", "");
			String inspecionFeeVPFQ2 = viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(",",
					"");
			String policyFeeVPFQ2 = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "");
			String brokerFeeVPFQ2 = viewOrPrintFullQuotePage.brokerFeeValue.getData().replace("$", "").replace(",", "");
			String sltfVPFQ2 = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "").replace(",",
					"");
			String slsfVPFQ2 = viewOrPrintFullQuotePage.surplusLinesServiceCharge.getData().replace("$", "")
					.replace(",", "");
			String fireMarshallTaxVPFQ2 = viewOrPrintFullQuotePage.fireMarshallTax.getData().replace("$", "")
					.replace(",", "");
			String grandTotalVPFQ2 = viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(3).getData()
					.replace("$", "").replace(",", "").replace("Grand Total", "");

			String surplusContributionVPFQ2 = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");

			// Printing values from View/Print full quote page
			Assertions.addInfo("View/Print Full Quote Page",
					"Asserting Premium,Inspection fees,Policy Fees,Broker Fees,SLTF,Fire Marshall Tax, Premium Plus Taxes and Fees Value on View/Print Full Quote Page");
			Assertions.passTest("View/Print Full Quote Page", "Premium is $" + premiumVPFQ2);
			Assertions.passTest("View/Print Full Quote Page", "Inspection Fees is $" + inspecionFeeVPFQ2);
			Assertions.passTest("View/Print Full Quote Page", "Policy Fees is $" + policyFeeVPFQ2);
			Assertions.passTest("View/Print Full Quote Page", "Broker Fees is $" + brokerFeeVPFQ2);
			Assertions.passTest("View/Print Full Quote Page", "Surplus Lines and Taxes Value is $" + sltfVPFQ2);
			Assertions.passTest("View/Print Full Quote Page", "Fire Marshall Tax Value is $" + fireMarshallTaxVPFQ2);
			Assertions.passTest("View/Print Full Quote Page", "Surplus Lines Service Charge Value is $" + slsfVPFQ2);
			Assertions.passTest("View/Print Full Quote Page",
					"Premium Plus Taxes and Fees Value is $" + grandTotalVPFQ2);
			Assertions.passTest("View/Print Full Quote Page",
					"Surplus Contribution Value is $" + surplusContributionVPFQ2);
			// Converting String values to double
			double d_premiumVPFQ2 = Double.parseDouble(premiumVPFQ2);
			double d_inspecionFeeVPFQ2 = Double.parseDouble(inspecionFeeVPFQ2);
			double d_policyFeeVPFQ2 = Double.parseDouble(policyFeeVPFQ2);
			double d_brokerFeeVPFQ2 = Double.parseDouble(brokerFeeVPFQ2);
			double d_slsfVPFQ2 = Double.parseDouble(slsfVPFQ2);
			double d_surplusContributionVPFQ2 = Double.parseDouble(surplusContributionVPFQ2);
			double d_sltfVPFQ2 = Double.parseDouble(sltfVPFQ2);
			double d_fireMarshallTaxVPFQ2 = Double.parseDouble(fireMarshallTaxVPFQ2);
			double d_grandTotalVPFQ2 = Double.parseDouble(grandTotalVPFQ2);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValueVPFQ2 = d_premiumVPFQ2 * (d_brokerFeePercentage / 100);

			double fireMarshallTaxVPFQCalculatedValueVPFQ2 = (d_premiumVPFQ2 + d_inspecionFeeVPFQ2 + d_policyFeeVPFQ2
					+ d_brokerFeeVPFQ2 + d_surplusContributionVPFQ2) * d_fireMarshallTaxPercentage2;

			double sltfCalculatedValueVPFQ2 = (d_premiumVPFQ2 + d_surplusContributionVPFQ2 + d_inspecionFeeVPFQ2
					+ d_policyFeeVPFQ2 + d_brokerFeeVPFQ2) * d_sltfPercentage2;

			double premiumPlusTaxesandFeesValueVPFQ2 = d_premiumVPFQ2 + d_inspecionFeeVPFQ2 + d_policyFeeVPFQ2
					+ d_brokerFeeVPFQ2 + sltfCalculatedValueVPFQ2 + fireMarshallTaxVPFQCalculatedValueVPFQ2
					+ d_slsfVPFQ2 + d_surplusContributionVPFQ2;

			// Asserting Actual and calculated sltf/Premium values
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying  Actual and calculated broker fees,Fire Marshall Tax,SLTF,Total Premium values on View/Print Full Quote page");
			if (Precision.round(
					Math.abs(Precision.round(d_brokerFeeVPFQ2, 2) - Precision.round(brokerFeeCalculatedValueVPFQ2, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Actual Broker fee is " + brokerFeeVPFQ2);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated Broker fee is " + brokerFeeCalculatedValueVPFQ2);
				Assertions.passTest("View/Print Full Quote Page",
						"Actual and Calculated Broker Fee values are matching as per 5%");
			} else {
				Assertions.verify(brokerFeeVPFQ2, brokerFeeCalculatedValueVPFQ2, "View/Print Full Quote Page",
						"The Difference between actual and calculated broker fee is more than 0.05", false, false);

			}

			if (Precision.round(Math.abs(Precision.round(d_fireMarshallTaxVPFQ2, 2)
					- Precision.round(fireMarshallTaxVPFQCalculatedValueVPFQ2, 2)), 2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page ",
						"Actual Fire Marshall Tax values is " + d_fireMarshallTaxVPFQ2);
				Assertions.passTest("View/Print Full Quote Page ",
						"Calculated Fire Marshall Tax values is " + fireMarshallTaxVPFQCalculatedValueVPFQ2);
				Assertions.passTest("View/Print Full Quote Page",
						"Actual and Calculated Fire Marshall Tax values are matching as per 0.3% for OR state");
			} else {
				Assertions.verify(d_fireMarshallTaxVPFQ2, fireMarshallTaxVPFQCalculatedValueVPFQ2,
						"View/Print Full Quote Page",
						"The Difference between Actual and Calculated Fire Marshall Tax values is more than 0.05",
						false, false);
			}

			if (Precision.round(
					Math.abs(Precision.round(d_sltfVPFQ2, 2) - Precision.round(sltfCalculatedValueVPFQ2, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Actual SLTF Value is " + d_sltfVPFQ2);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated SLTF Value is " + sltfCalculatedValueVPFQ2);
				Assertions.passTest("View/Print Full Quote Page",
						"Actual SLTF and Calculated SLTF are matching as per 2% for OR state");
			} else {
				Assertions.verify(d_sltfVPFQ2, sltfCalculatedValueVPFQ2, "View/Print Full Quote Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);
			}

			if (Precision.round(Math
					.abs(Precision.round(d_grandTotalVPFQ2, 2) - Precision.round(premiumPlusTaxesandFeesValueVPFQ2, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Actual Premium Value is " + d_grandTotalVPFQ2);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated PremiumValue is " + premiumPlusTaxesandFeesValueVPFQ2);
			} else {
				Assertions.verify(d_grandTotalVPFQ2, premiumPlusTaxesandFeesValueVPFQ2, "View/Print Full Quote Page",
						"The Difference between Actual Total Premium and Calculated Total Premium is more than 0.05",
						false, false);
			}

			Assertions.addInfo("View/Print Full Quote Page", "Verifying TRIA Coverage is included");
			Assertions.verify(
					viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(22).getData().contains("Included"),
					true, "View/Print Full Quote Page", "TRIA Coverage is included when Terrorism is selected", false,
					false);

			// Navigate to account overview page
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber2);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Navigate to homepage and logout
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the quote number in grid and clicking on the quote link
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			Assertions.passTest("Policy Summary Page", "Policy Number :" + policyNumber);

			// Asserting absence of taxes and fees label
			Assertions.addInfo("Policy Summary Page",
					" Asserting the absence of taxes and fees label on policy summary page");
			Assertions.verify(
					policySummaryPage.taxesAndStateFees.formatDynamicPath("1").checkIfElementIsPresent()
							&& policySummaryPage.taxesAndStateFees.formatDynamicPath("1").checkIfElementIsDisplayed(),
					false, "Policy Summary Page", "Taxes and Fees are not displayed in Policy Summary Page", false,
					false);

			// Click on Endorse PB
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to change the Coverages");
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");
			testData = data.get(data_Value2);

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Click on change coverage options
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			testData = data.get(data_Value3);
			// Entering Create quote page Details
			createQuotePage.enterQuoteDetailsCommercialPNB(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next Button");

			// Asserting the absence of sltf data
			Assertions.addInfo("Endorse Policy Page",
					" Asserting the absence of taxes and fees on endorse policy page");
			Assertions.verify(
					endorsePolicyPage.sltfData.checkIfElementIsPresent()
							&& endorsePolicyPage.sltfData.checkIfElementIsDisplayed(),
					false, "Endorse Policy Page", "Taxes and Fees are not displayed in Endorse Policy Page", false,
					false);

			// Click on Complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// Asserting the absence of sltf data
			Assertions.addInfo("Endorse Policy Page",
					" Asserting the absence of taxes and fees on endorse policy page");
			Assertions.verify(
					endorsePolicyPage.sltfData.checkIfElementIsPresent()
							&& endorsePolicyPage.sltfData.checkIfElementIsDisplayed(),
					false, "Endorse Policy Page", "Taxes and Fees are not displayed in Endorse Summary Page", false,
					false);

			// Click on Close button
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Close Button");

			// Asserting presence of taxes and fees label
			Assertions.addInfo("Policy Summary Page",
					" Asserting the absence of taxes and fees on policy summary page after the Endorsement is done");
			Assertions.verify(
					policySummaryPage.taxesAndStateFees.formatDynamicPath("1").checkIfElementIsPresent()
							&& policySummaryPage.taxesAndStateFees.formatDynamicPath("1").checkIfElementIsDisplayed(),
					false, "Policy Summary Page", "Taxes and Fees are not displayed in Policy Summary Page", false,
					false);

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 110", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 110", "Executed Successfully");
			}
		}
	}
}
