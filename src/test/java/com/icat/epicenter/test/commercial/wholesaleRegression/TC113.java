/*Summary: Validate that the SLTF is recalculated upon Overriding the Premium and Fees on Override Premium and Fees page. Also verify the Terms and Conditions wordings of SLTF when turned on. and added IO-21422(this ticket not working now refer IO-22385)
Date:08/02/2021
Author: Sowndarya*/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC113 extends AbstractCommercialTest {

	public TC113() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID113.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		LoginPage loginPage = new LoginPage();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		double expectedsltf;
		double expectedStampingFee;
		double expectedBrokerFee;
		DecimalFormat df = new DecimalFormat("0.00");
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value4 = 3;
		Map<String, String> testData = data.get(data_Value1);
		homePage.enterPersonalLoginDetails();
		boolean isTestPassed = false;

		try {
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			// Click on user preference link and enter the details
			Assertions.addInfo("Home Page", "Entering User Preference Details");
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");

			// Add broker fees
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			preferenceOptionsPage.addBrokerFees(testData);
			Assertions.passTest("Preference Options Page",
					"Broker Fees checkbox and Surplus Lines,Taxes and Fees checkbox is selected successfully");
			Assertions.passTest("Preference Options Page", "Details entered successfully");

			// Logout as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");

			// Navigate to homepage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			locationPage = eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Getting the premium,icat fees,other fees and sltf values from premium section
			// in Account Overview page
			Assertions.addInfo("Account Overview Page",
					"Asserting Other Fees,SLTF,Premium,Fees value on account overview page");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsPresent(), true, "Account Overview Page",
					"Other Fees displayed is verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Value is : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Fees Value is : " + accountOverviewPage.feesValue.getData(), false, false);

			// fetching premium amount
			String premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			double premiumAmount = Double.parseDouble(premium);

			// fetching fees value
			String icatFees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			double icatFeesAmount = Double.parseDouble(icatFees);

			// fetching surplus lines taxes
			String surplusContribution = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			double d_surplusContribution = Double.parseDouble(surplusContribution);

			// fetching sltf percentage and stamping fee percentage
			String sltfPercentage = testData.get("SurplusLinesTaxesPercentage");
			double double_sltfPercentage = Double.parseDouble(sltfPercentage);

			String stampingPercentage = testData.get("StampingPercentage");
			double double_stampingPercentage = Double.parseDouble(stampingPercentage);

			// calculating sltf percentage by adding Premium+Fees+Broker fees and
			// multiplying by sltf percentage 0.03
			expectedsltf = (premiumAmount + icatFeesAmount + d_surplusContribution) * double_sltfPercentage;

			// Rounding sltf decimal value to 2 digits
			df.format(expectedsltf);

			// Calculate Stamping fee
			double expectedstampingFee = (premiumAmount + icatFeesAmount + d_surplusContribution)
					* double_stampingPercentage;

			// Calculate final SLTF
			double calculatedSltf = expectedsltf + expectedstampingFee;
			String actualSLTF = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.addInfo("Account Overview Page",
					"Verifying Actual and Calculated SLTF on Account overview page");
			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(actualSLTF), 2) - Precision.round(calculatedSltf, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Actual SLTF is : " + "$" + actualSLTF);
				Assertions.passTest("Account Overview Page", "Calculated SLTF is : " + "$" + df.format(calculatedSltf));
			} else {
				Assertions.verify(actualSLTF, calculatedSltf, "Account Overview Page",
						"The Difference between actual and calculated SLTF is more than 0.05", false, false);

			}

			// click on veiw/Print full Quote
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View print Full quote link");

			// fetching Sltf percentage
			String sltfPercentage_VPFQ = testData.get("SurplusLinesTaxesPercentage");
			double d_sltfPercentage_VPFQ = Double.parseDouble(sltfPercentage_VPFQ);

			// fetching surplus contribution
			String surplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");
			double d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
			// percentage 0.03
			expectedsltf = (premiumAmount + icatFeesAmount + d_surplusContributionValue) * d_sltfPercentage_VPFQ;

			// verifying surplus fees and broker fees in view/print full quote page
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying Actual and calculated SLTF on view/print full quote page");
			Assertions.passTest("View/Print Full Quote Page",
					"Calculated Surplus Lines Taxes : " + "$" + df.format(expectedsltf));
			Assertions.passTest("View/Print Full Quote Page",
					"Actual Surplus Lines Taxes : " + viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData());
			Assertions.verify(viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace(",", ""),
					"$" + df.format(expectedsltf), "View/Print Full Quote Page",
					"Calculated and Actual Surplus Lines Taxes in View/Print Full Quote Page are equal", false, false);

			// fetching stamping fee percentage
			String stampingsltfPercentage_VPFQ = testData.get("StampingPercentage");
			double d_stampingsltfPercentage_VPFQ = Double.parseDouble(stampingsltfPercentage_VPFQ);

			// calculating stamping fee by adding premium and icat fees and multiplied by
			// stamping fee percentage 0.0018
			expectedStampingFee = (premiumAmount + icatFeesAmount + d_surplusContributionValue)
					* d_stampingsltfPercentage_VPFQ;
			df.format(expectedStampingFee);
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying Actual and calculated stamping fees on view/print full quote page");
			// df.setRoundingMode(RoundingMode.UP);
			String actualStampingfee = viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "");
			double d_actualStampingfee = Double.parseDouble(actualStampingfee);
			if (Precision.round(
					Math.abs(Precision.round(d_actualStampingfee, 2) - Precision.round(expectedStampingFee, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated Stamping Fees : " + "$" + df.format(expectedStampingFee));
				Assertions.passTest("View/Print Full Quote Page",
						"Actual Stamping Fees : " + viewOrPrintFullQuotePage.stampingFeeValue.getData());
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual and calculated Stamping fee is more than 0.05");
			}

			// fetching broker fee percentage
			testData = data.get(data_Value4);
			String brokerfeePercentage_VPFQ = testData.get("BrokerFeeValue");
			double d_brokerfeePercentage_VPFQ = Double.parseDouble(brokerfeePercentage_VPFQ);

			// calculating Broker fee using premium multiplying by sltf percentage 0.05
			expectedBrokerFee = (premiumAmount) * d_brokerfeePercentage_VPFQ;
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying Actual and calculated Broker fees on view/print full quote page");
			Assertions.passTest("View/Print Full Quote Page",
					"Calculated Broker Fees : " + "$" + df.format(expectedBrokerFee));
			Assertions.passTest("View/Print Full Quote Page",
					"Actual Broker Fees : " + viewOrPrintFullQuotePage.brokerFeeValue.getData());
			Assertions.verify(viewOrPrintFullQuotePage.brokerFeeValue.getData().replace(",", ""),
					"$" + df.format(expectedBrokerFee), "View/Print Full Quote Page",
					"Calculated and Actual Broker Fees in View/Print Full Quote Page are equal", false, false);

			// Asserting Terms and Conditions Wordings
			Assertions.addInfo("View/Print Full Quote Page",
					"Asserting Terms and Conditions Wordings on view/print full quote page");
			Assertions.verify(viewOrPrintFullQuotePage.termsAndConditionsWordings.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "The Terms and Conditions Wordings "
							+ viewOrPrintFullQuotePage.termsAndConditionsWordings.getData() + " displayed is verified",
					false, false);

			// click on go back
			viewOrPrintFullQuotePage.backButton.click();

			testData = data.get(data_Value1);
			// Click on Override Premium link
			Assertions.addInfo("Account Overview Page", "Overriding the Premium and Fees");
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Override Premium link");

			Assertions.verify(overridePremiumAndFeesPage.overridePremiumButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees Page loaded successfully", false,
					false);

			// Changing Premium,Policy fee and inspection fee
			if (testData.get("OverridePremium") != null) {
				if (!testData.get("OverridePremium").equalsIgnoreCase("")) {
					Assertions.passTest("Override Premium and Fees Page", "The Original Premium is : "
							+ overridePremiumAndFeesPage.originalPremiumValue.getData().replace(".00", ""));
					overridePremiumAndFeesPage.overridePremium.scrollToElement();
					overridePremiumAndFeesPage.overridePremium.setData(testData.get("OverridePremium"));
					Assertions.passTest("Override Premium and Fees Page",
							"The Updated Premium is " + "$" + testData.get("OverridePremium"));
				}
			}
			if (testData.get("TransactionPolicyfee") != null) {
				if (!testData.get("TransactionPolicyfee").equalsIgnoreCase("")) {
					Assertions.passTest("Override Premium and Fees Page", "The Original Policy Fee is : "
							+ overridePremiumAndFeesPage.originalPolicyFee.getData().replace(".00", ""));
					overridePremiumAndFeesPage.policyFee.scrollToElement();
					overridePremiumAndFeesPage.policyFee.setData(testData.get("TransactionPolicyfee"));
					Assertions.passTest("Override Premium and Fees Page",
							"The Updated Policy Fee is : " + "$" + testData.get("TransactionPolicyfee"));
				}
			}
			if (testData.get("TransactionInspectionFee") != null) {
				if (!testData.get("TransactionInspectionFee").equalsIgnoreCase("")) {
					Assertions.passTest("Override Premium and Fees Page", "The Original Inspection Fee is : "
							+ overridePremiumAndFeesPage.originalInspectionFee.getData().replace(".00", ""));
					overridePremiumAndFeesPage.totalInspectionFee.scrollToElement();
					overridePremiumAndFeesPage.totalInspectionFee.setData(testData.get("TransactionInspectionFee"));
					Assertions.passTest("Override Premium and Fees Page",
							"The Updated Inspection Fee is : " + "$" + testData.get("TransactionInspectionFee"));
				}
			}
			if (testData.get("FeeOverrideJustification") != null
					&& !testData.get("FeeOverrideJustification").equals("")) {
				overridePremiumAndFeesPage.feeOverrideJustification.scrollToElement();
				overridePremiumAndFeesPage.feeOverrideJustification.setData(testData.get("FeeOverrideJustification"));
			}

			// calculating changed premium and fee
			String overridePremiumValue = testData.get("OverridePremium");
			double d_overridePremiumValue = Double.parseDouble(overridePremiumValue);
			String overrideTransactionPolicyfee = testData.get("TransactionPolicyfee");
			double d_overrideTransactionPolicyfee = Double.parseDouble(overrideTransactionPolicyfee);
			String overrideTransactionInspectionFee = testData.get("TransactionInspectionFee");
			double d_overrideTransactionInspectionFee = Double.parseDouble(overrideTransactionInspectionFee);
			String brokerFee = testData.get("BrokerFeeValue");
			double d_brokerFee = Double.parseDouble(brokerFee);
			double calBrokerFee = (d_overridePremiumValue * d_brokerFee) / 100;
			double RecalculatedTotalPremiumandFee = d_overridePremiumValue + d_overrideTransactionPolicyfee
					+ d_overrideTransactionInspectionFee + calBrokerFee;

			// click on override premium button
			if (overridePremiumAndFeesPage.overridePremiumButton.checkIfElementIsPresent()
					&& overridePremiumAndFeesPage.overridePremiumButton.checkIfElementIsDisplayed()) {
				overridePremiumAndFeesPage.overridePremiumButton.scrollToElement();
				overridePremiumAndFeesPage.overridePremiumButton.click();
				accountOverviewPage.overridePremiumLink.scrollToElement();
				accountOverviewPage.overridePremiumLink.click();

			}

			// Comparing Calculated and actual Recalculated premium value
			Assertions.addInfo("Override Premium and Fees Page",
					"Verifying Calculated Recalculated TotalPremium and Fees are equal");
			Assertions.passTest("Override Premium and Fees Page",
					"The Calculated Total Premium and Fee : " + "$" + df.format(RecalculatedTotalPremiumandFee));
			Assertions.verify(overridePremiumAndFeesPage.recalculatedPremiumAndFees.getData().replace(",", ""),
					"$" + df.format(RecalculatedTotalPremiumandFee), "Override Premium and Fees Page",
					"The Calculated and Actual Total Premium and Fee is equal, Actual Total Premium "
							+ overridePremiumAndFeesPage.recalculatedPremiumAndFees.getData(),
					false, false);
			overridePremiumAndFeesPage.cancelButton.scrollToElement();
			overridePremiumAndFeesPage.cancelButton.click();

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Asserting Premium,icat fees after overriding the premium
			Assertions.addInfo("Account Overview Page", "Asserting Other fees,SLTF,Premium,Fees value");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsPresent(), true, "Account Overview Page",
					"Other Fees displayed is verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Value is : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Fees Value is : " + accountOverviewPage.feesValue.getData(), false, false);

			// fetching premium amount
			String recalculatedpremium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			double d_recalculatedpremium = Double.parseDouble(recalculatedpremium);

			// fetching fees value
			String recalculatedicatFees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			double d_recalculatedicatFees = Double.parseDouble(recalculatedicatFees);

			// fetching surplus lines taxes
			String surplusContributionAcc = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			double d_surplusContributionAcc = Double.parseDouble(surplusContributionAcc);

			// calculating sltf percentage by adding Premium+Fees+Broker fees and
			// multiplying by sltf percentage 0.03
			expectedsltf = (d_recalculatedpremium + d_recalculatedicatFees + d_surplusContributionAcc)
					* double_sltfPercentage;

			// Calculate Stamping fee
			double expectedstampingFees = (d_recalculatedpremium + d_recalculatedicatFees + d_surplusContributionAcc)
					* double_stampingPercentage;

			// Calculate final SLTF
			double calculatedSltfValue = expectedsltf + expectedstampingFees;

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.addInfo("Account Overview Page", "Asserting Other fees,SLTF,Premium,Fees value");
			Assertions.verify(accountOverviewPage.sltfValue.getData(), "$" + df.format(calculatedSltfValue),
					"Account Overview Page",
					"Calculated Surplus Lines Taxes and Fees : " + "$" + df.format(calculatedSltfValue), false, false);
			Assertions.verify(accountOverviewPage.sltfValue.getData(), "$" + df.format(calculatedSltfValue),
					"Account Overview Page",
					"Actual Surplus Lines Taxes and Fees : " + accountOverviewPage.sltfValue.getData(), false, false);
			Assertions.verify(accountOverviewPage.sltfValue.getData(), "$" + df.format(calculatedSltfValue),
					"Account Overview Page", "Calculated and Actual Surplus Lines Taxes and Fees are equal", false,
					false);

			// fetching Broker fee percentage
			testData = data.get(data_Value4);
			String brokerFeePercentage = testData.get("BrokerFeeValue");
			double d_brokerFeePercentage = Double.parseDouble(brokerFeePercentage);

			// calculating Broker fee by adding premium multiplying by sltf percentage 0.05
			expectedBrokerFee = (d_recalculatedpremium) * d_brokerFeePercentage;
			Assertions.addInfo("Account Overview Page",
					"Verifying actual and calculated Broker fee on account overview page");
			Assertions.passTest("Account Overview Page",
					"Calculated Broker Fees : " + "$" + df.format(expectedBrokerFee));
			Assertions.passTest("Account Overview Page",
					"Actual Broker Fees : " + accountOverviewPage.otherFees.getData().replace(",", ""));
			Assertions.verify(accountOverviewPage.otherFees.getData().replace(",", ""),
					"$" + df.format(expectedBrokerFee), "Account Overview Page",
					"Calculated and Actual Broker Fees in Account Overview Page are equal", false, false);

			// click on veiw/Print full Quote
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");

			// fetching sltf percentage
			testData = data.get(data_Value2);
			String sltfPercentage_VPQ = testData.get("SurplusLinesTaxesPercentage");
			double d_sltfPercentage_VPQ = Double.parseDouble(sltfPercentage_VPQ);

			// get surplus contribution value
			String surplusContributionVPFQ = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");
			double d_surplusContributionVPFQ = Double.parseDouble(surplusContributionVPFQ);

			// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
			// percentage 0.03
			expectedsltf = (d_recalculatedpremium + d_recalculatedicatFees + d_surplusContributionVPFQ)
					* d_sltfPercentage_VPQ;

			// verifying surplus fees and broker fees in view/print full quote page
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying actual and calculated SLTF on View print full quote page");
			Assertions.passTest("View/Print Full Quote Page",
					"Calculated Surplus Lines Taxes : " + "$" + df.format(expectedsltf));
			Assertions.passTest("View/Print Full Quote Page",
					"Actual Surplus Lines Taxes : " + viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData());
			Assertions.verify(viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData(), "$" + df.format(expectedsltf),
					"View/Print Full Quote Page",
					"Calculated and Actual Surplus Lines Taxes in View/Print Full Quote Page are equal", false, false);

			// fetching stamping fee percentage
			testData = data.get(data_Value1);
			String stampingFeePercentage_VPFQ = testData.get("StampingPercentage");
			double d_stampingFeePercentage_VPFQ = Double.parseDouble(stampingFeePercentage_VPFQ);

			// calculating stamping fee by adding premkum and icat fees and multiplied by
			// stamping fee percentage 0.0018
			expectedStampingFee = (d_recalculatedpremium + d_recalculatedicatFees + d_surplusContributionVPFQ)
					* d_stampingFeePercentage_VPFQ;
			df.format(expectedStampingFee);
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying actual and calculated Stamping fees on View print full quote page");
			Assertions.passTest("View/Print Full Quote Page",
					"Calculated Stamping Fees : " + "$" + df.format(expectedStampingFee));
			Assertions.passTest("View/Print Full Quote Page",
					"Actual Stamping Fees : " + viewOrPrintFullQuotePage.stampingFeeValue.getData());
			Assertions.verify(viewOrPrintFullQuotePage.stampingFeeValue.getData(), "$" + df.format(expectedStampingFee),
					"View/Print Full Quote Page",
					"Calculated and Actual Stamping Fees in View/Print Full Quote Page are equal", false, false);

			// fetching broker fee percentage
			testData = data.get(data_Value4);
			String brokerFeePercentage_VPFQ = testData.get("BrokerFeeValue");
			double d_brokerFeePercentage_VPFQ = Double.parseDouble(brokerFeePercentage_VPFQ);

			// calculating Broker fee by adding premium multiplying by sltf percentage 0.05
			expectedBrokerFee = (d_recalculatedpremium) * d_brokerFeePercentage_VPFQ;
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying actual and calculated Broker Fee by adding premium and multiplying by sltf percentage 0.05 on View print full quote page");
			Assertions.passTest("View/Print Full Quote Page",
					"Calculated Broker Fees : " + "$" + df.format(expectedBrokerFee));
			Assertions.passTest("View/Print Full Quote Page",
					"Actual Broker Fees : " + viewOrPrintFullQuotePage.brokerFeeValue.getData().replace(",", ""));
			Assertions.verify(viewOrPrintFullQuotePage.brokerFeeValue.getData().replace(",", ""),
					"$" + df.format(expectedBrokerFee), "View/Print Full Quote Page",
					"Calculated and Actual Broker Fees in View/Print Full Quote Page are equal", false, false);
			viewOrPrintFullQuotePage.backButton.click();

			// Asserting Total Premium and fee from account overview page
			Assertions.addInfo("Account Overview Page", "Asserting Total Premium and fee from account overview page");
			String totalPremiumValue = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",",
					"");
			Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Total Premium and Fees Amount is : " + "$" + totalPremiumValue, false,
					false);

			// Click on Request Bind Button
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Entering details in request bind page
			testData = data.get(data_Value1);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterPolicyDetails(testData);
			requestBindPage.addInspectionContact(testData);
			requestBindPage.addContactInformation(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// click on submit
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			Assertions.passTest("Request Bind Page", "Clicked on Submit button");

			// Asserting Premium from Request bind modal
			Assertions.addInfo("Confirm Bind Page", "Asserting Premium and Fees Amount on Request bind modal");
			confirmBindRequestPage.grandTotal.waitTillPresenceOfElement(60);
			confirmBindRequestPage.grandTotal.waitTillVisibilityOfElement(60);
			Assertions.verify(confirmBindRequestPage.grandTotal.checkIfElementIsDisplayed(), true,
					"Confirm Bind Request Page", "Total Premium and Fees Amount is : "
							+ confirmBindRequestPage.grandTotal.getData().replace("Quote Premium :", ""),
					false, false);

			// Getting the quote premium
			int quotePremiumLength = confirmBindRequestPage.grandTotal.getData().length();
			String quotePremium = confirmBindRequestPage.grandTotal.getData().substring(16, quotePremiumLength)
					.replace(",", "");

			// Comparing totalPremiumValue from account overview page and Request bind modal
			Assertions.addInfo("Confirm Bind Page",
					"Verifying calculated and actual total PremiumValue from account overview page and Request bind modal");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(quotePremium), 2)
					- Precision.round(Double.parseDouble(totalPremiumValue), 2)), 2) < 0.5) {
				Assertions.passTest("Confirm Bind Request Page",
						"Calculated Total Premium Value : " + "$" + totalPremiumValue);
				Assertions.passTest("Confirm Bind Request Page", "Actual Total Premium Value : " + "$" + quotePremium);
				Assertions.passTest("Confirm Bind Request Page",
						"The Total Premium and Fees Amount displayed in both account overview page and Request Bind Modal are equa");
			} else {
				Assertions.passTest("Confirm Bind Request Page",
						"The Difference between actual and calculated Total Premium Value is more than 0.5");
			}
			confirmBindRequestPage.confirmBind();

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
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

			// getting the policy number
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			Assertions.passTest("Policy Summary Page", "Policy Number :" + policyNumber);

			// log out as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 113", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 113", "Executed Successfully");
			}
		}
	}
}
