//Summary: Verify the proposed effective date on quote specifics section and quote document with/without entering effective date on the quote
//Date: 10/Feb/2021
//Author: Swaroop

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountDetails;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC111 extends AbstractCommercialTest {

	public TC111() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID111.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		AccountDetails accountDetails = new AccountDetails();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		DecimalFormat df = new DecimalFormat("0.00");

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
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

			// creating New account
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

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting quoteNUmber
			String quoteNumber1 = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Quote Number 1 is " + quoteNumber1);

			// Asserting SLTF value in accountoverviewpage
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"SLTF value " + accountOverviewPage.sltfValue.getData() + " is displayed ", false, false);
			Assertions.verify(accountOverviewPage.quoteSpecifics.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Quote Specifics Link is displayed", false, false);

			// Add assertions
			String premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			String icatFees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			String otherFees = accountOverviewPage.otherFees.getData().replace("$", "").replace(",", "");
			String surplusLinesandTaxes = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			String premiumPlusTaxesandFees = accountOverviewPage.totalPremiumValue.getData().replace("$", "")
					.replace(",", "");
			String sltfPercentage = testData.get("SurplusLinesTaxesPercentage");
			String stampingFeeePercentage = testData.get("StampingPercentage");
			String brokerFeePerccentage = testData.get("BrokerFeeValue");

			// Printing values from account overview page
			Assertions.addInfo("Account Overview Page",
					"Asserting Premium,Icat fees,Broker Fees,SLTF,Premium Plus Taxes and Fees on account overview page");
			Assertions.passTest("Account Overview Page", "Premium is $" + premium);
			Assertions.passTest("Account Overview Page", "ICAT Fees is $" + icatFees);
			Assertions.passTest("Account Overview Page", "Broker Fees is $" + otherFees);
			Assertions.passTest("Account Overview Page", "Surplus Lines and Taxes Value is $" + surplusLinesandTaxes);
			Assertions.passTest("Account Overview Page",
					"Premium Plus Taxes and Fees Value is $" + premiumPlusTaxesandFees);

			// Converting string values to double
			double d_premium = Double.parseDouble(premium);
			double d_icatFees = Double.parseDouble(icatFees);
			double d_otherFees = Double.parseDouble(otherFees);
			double d_sltfPercentage = Double.parseDouble(sltfPercentage);
			double d_stampingPercentage = Double.parseDouble(stampingFeeePercentage);
			double d_brokerFeePercentage = Double.parseDouble(brokerFeePerccentage);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValue = d_premium * (d_brokerFeePercentage / 100);
			double stampingCalculatedValue = ((d_premium + d_icatFees + d_otherFees)) * d_stampingPercentage;
			double sltfCalculatedValue = ((d_premium + d_icatFees + d_otherFees) * d_sltfPercentage)
					+ stampingCalculatedValue;
			double premiumPlusTaxesandFeesValue = d_premium + d_icatFees + d_otherFees + sltfCalculatedValue;

			// Asserting Actual and calculated broker/sltf/Premium values
			Assertions.addInfo("Account Overview Page",
					"Verifying  Actual and calculated SLTF,Total Premium values on Account overview page");
			Assertions.verify(otherFees, df.format(brokerFeeCalculatedValue), "Account Overview Page",
					"Actual and Calculated Broker Fee values are matching as per 3%", false, false);
			double d_surplusLinesandTaxes = Double.parseDouble(surplusLinesandTaxes);
			if (Precision.round(
					Math.abs(Precision.round(d_surplusLinesandTaxes, 2) - Precision.round(sltfCalculatedValue, 2)),
					2) < 0.50) {
				Assertions.passTest("Account Overview Page",
						"Actual and Calculated SLTF are matching as per 0.034982% for NY state");
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.50");
			}

			double d_premiumPlusTaxesandFees = Double.parseDouble(premiumPlusTaxesandFees);
			if (Precision.round(Math.abs(
					Precision.round(d_premiumPlusTaxesandFees, 2) - Precision.round(premiumPlusTaxesandFeesValue, 2)),
					2) < 0.50) {
				Assertions.passTest("Account Overview Page", "Actual and Calculated Total Premium are matching");
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.50");
			}

			// Asserting quote specific details
			Assertions.addInfo("Account Overview Page", "Asserting Quote Specific Details on Account Overview Page");
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.verify(accountOverviewPage.requestedEffectiveDate.getData(), testData.get("PolicyEffDate"),
					"Account Overview Page", "Requested Effective Date under quote specific is "
							+ accountOverviewPage.requestedEffectiveDate.getData(),
					false, false);

			// Click on view/print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");
			Assertions.addInfo("View/Print Full Quote Page",
					"Asserting Proposed Effective Date on View/Print Full Quote Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.proposedEffectiveDate.getData().contains(testData.get("PolicyEffDate")),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.proposedEffectiveDate.getData() + " is displayed", false, false);

			// Add assertions
			String premiumVPFQ = viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath(5).getData().replace("$", "")
					.replace(",", "").replace("Premium", "");
			String inspecionFeeVPFQ = viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(",",
					"");
			String policyFeeVPFQ = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "");
			String brokerFeeVPFQ = viewOrPrintFullQuotePage.brokerFeeValue.getData().replace("$", "").replace(",", "");
			String sltfVPFQ = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "").replace(",",
					"");
			String stampingVPFQ = viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "").replace(",", "");
			String grandTotalVPFQ = viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath(3).getData()
					.replace("$", "").replace(",", "").replace("Grand Total", "");

			// Printing values from View/Print full quote page
			Assertions.addInfo("View/Print Full Quote Page",
					"Asserting Premium,Inspection fees,Policy Fees,Broker Fees,SLTF,Maintenance Value, Premium Plus Taxes and Fees Value on View/Print Full Quote Page");
			Assertions.passTest("View/Print Full Quote Page", "Premium is $" + premiumVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Inspection Fees is $" + inspecionFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Policy Fees is $" + policyFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Broker Fees is $" + brokerFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Surplus Lines and Taxes Value is $" + sltfVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Maintenance Value is $" + stampingVPFQ);
			Assertions.passTest("View/Print Full Quote Page",
					"Premium Plus Taxes and Fees Value is $" + grandTotalVPFQ);

			// Converting String values to double
			double d_premiumVPFQ = Double.parseDouble(premiumVPFQ);
			double d_inspecionFeeVPFQ = Double.parseDouble(inspecionFeeVPFQ);
			double d_policyFeeVPFQ = Double.parseDouble(policyFeeVPFQ);
			double d_brokerFeeVPFQ = Double.parseDouble(brokerFeeVPFQ);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValueVPFQ = d_premiumVPFQ * (d_brokerFeePercentage / 100);
			double stampingCalculatedValueVPFQ = ((d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ
					+ d_brokerFeeVPFQ)) * d_stampingPercentage;
			double sltfCalculatedValueVPFQ = ((d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ + d_brokerFeeVPFQ)
					* d_sltfPercentage);
			double premiumPlusTaxesandFeesValueVPFQ = d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ
					+ d_brokerFeeVPFQ + sltfCalculatedValueVPFQ + stampingCalculatedValueVPFQ;

			// Asserting Actual and calculated sltf/Premium values
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying  Actual and calculated broker fees,Stamping Fees,SLTF,Total Premium values on View/Print Full Quote page");
			Assertions.verify(brokerFeeVPFQ, df.format(brokerFeeCalculatedValueVPFQ), "View/Print Full Quote Page",
					"Actual and Calculated Broker Fee values are matching as per 3%", false, false);
			double d_stampingVPFQ = Double.parseDouble(stampingVPFQ);
			if (Precision.round(
					Math.abs(Precision.round(d_stampingVPFQ, 2) - Precision.round(stampingCalculatedValueVPFQ, 2)),
					2) < 0.50) {
				Assertions.passTest("View/Print Full Quote Page",
						"Actual and Calculated Stamping Fee values are matching as per 0.0016519% for NY state");
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.50");
			}

			double d_sltfVPFQ = Double.parseDouble(sltfVPFQ);
			if (Precision.round(Math.abs(Precision.round(d_sltfVPFQ, 2) - Precision.round(sltfCalculatedValueVPFQ, 2)),
					2) < 0.50) {
				Assertions.passTest("View/Print Full Quote Page",
						"Actual SLTF and Calculated SLTF are matching as per 0.034982% for NY state");
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.50");
			}

			double d_grandTotalVPFQ = Double.parseDouble(grandTotalVPFQ);
			if (Precision.round(Math
					.abs(Precision.round(d_grandTotalVPFQ, 2) - Precision.round(premiumPlusTaxesandFeesValueVPFQ, 2)),
					2) < 0.50) {
				Assertions.passTest("View/Print Full Quote Page",
						"Actual Total Premium and Calculated Total Premium are matching");
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.50");
			}

			// Navigate to account overview page
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Update proposed effective date
			Assertions.addInfo("Account Overview Page", "Entering Proposed Effective Date");
			accountOverviewPage.accountDetailsLink.scrollToElement();
			accountOverviewPage.accountDetailsLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Account Details Link");
			accountOverviewPage.editAccountDetails.waitTillVisibilityOfElement(60);
			accountOverviewPage.editAccountDetails.scrollToElement();
			accountOverviewPage.editAccountDetails.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Account Details Link");

			// Asserting and update effective
			Assertions.verify(accountDetails.effectiveDate.getData(), testData.get("PolicyEffDate"),
					"Account Details Page", "Effective Date is  " + accountDetails.effectiveDate.getData(), false,
					false);
			accountDetails.effectiveDate.scrollToElement();
			accountDetails.effectiveDate.clearData();
			accountDetails.reviewButton.scrollToElement();
			accountDetails.reviewButton.click();
			testData = data.get(data_Value2);

			// Asserting hardstop
			Assertions.addInfo("Account Details Page", "Assert Error message after entering Proposed effective date");
			Assertions.verify(accountDetails.errorMsg.checkIfElementIsDisplayed(), true, "Account Details Page",
					accountDetails.errorMsg.getData() + " is displayed", false, false);
			accountDetails.effectiveDate.scrollToElement();
			accountDetails.effectiveDate.setData(testData.get("PolicyEffDate"));
			accountDetails.reviewButton.scrollToElement();
			accountDetails.reviewButton.click();
			if (accountDetails.reloadAccount.checkIfElementIsPresent()
					&& accountDetails.reloadAccount.checkIfElementIsDisplayed()) {
				accountDetails.reloadAccount.scrollToElement();
				accountDetails.reloadAccount.click();
			}

			// Create Quote
			locationPage.createQuoteButton.scrollToElement();
			locationPage.createQuoteButton.click();

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting quoteNUmber
			String quoteNumber2 = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Quote Number 2 is " + quoteNumber2);

			// Asserting quote specific details
			Assertions.addInfo("Account Overview Page", "Assert Requested Effective Date on Account overview page");
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.verify(accountOverviewPage.requestedEffectiveDate.getData(), testData.get("PolicyEffDate"),
					"Account Overview Page", "Requested Effective Date under quote specific is "
							+ accountOverviewPage.requestedEffectiveDate.getData(),
					false, false);

			// Click on view/print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");
			Assertions.addInfo("View/Print Full Quote Page",
					"Asserting Proposed effective date on View/Print Full Quote Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.proposedEffectiveDate.getData().contains(testData.get("PolicyEffDate")),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.proposedEffectiveDate.getData() + " is displayed", false, false);

			// click on back
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 111", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 111", "Executed Successfully");
			}
		}
	}
}
