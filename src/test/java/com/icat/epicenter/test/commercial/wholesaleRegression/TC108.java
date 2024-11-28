//Summary: Check Broker fee is recalculated with changed value and SLTF and Broker Fee are not displayed for NPB Endorsement, added ticket IO-21306
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
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC108 extends AbstractCommercialTest {

	public TC108() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID108.xls";
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
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
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
			if (testData.get("PriorLoss1").equals("Yes")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Enter refer quote Page
			ReferQuotePage referQuotePage = new ReferQuotePage();
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();

				// Get quote for referral
				String quoteNumber = referQuotePage.quoteNumberforReferral.getData();

				// Navigate to homepage and logout
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Login Page", "Logged out as Producer successfully");

				// Login as Producer
				loginPage.refreshPage();
				loginPage.waitTime(2);// need wait time to load the page
				loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
				Assertions.passTest("Login Page", "Logged in as USM successfully");

				// searching the quote number in grid and clicking on the quote link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber);
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();
				Assertions.passTest("Home Page", "Searched Submitted quote " + quoteNumber + " successfullly");

				// Approve the quote for referral
				if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
					referralPage.pickUp.scrollToElement();
					referralPage.pickUp.click();
				}
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve/Decline Quote Page", "Quote approved successfully");

				// Search for approved quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);
			}

			// getting the quote number
			String quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page",
					"Account Overview Page Loaded successfully.Quote Number :  " + quoteNumber);

			// Add assertions
			String premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			String icatFees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			String otherFees = accountOverviewPage.otherFees.getData().replace("$", "").replace(",", "");
			String surplusLinesandTaxes = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			String premiumPlusTaxesandFees = accountOverviewPage.totalPremiumValue.getData().replace("$", "")
					.replace(",", "");
			String surplusContribution = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			String sltfPercentage = testData.get("SurplusLinesTaxesPercentage");
			String maintenancePercentage = testData.get("MaintenancePercentage");
			String brokerFeePercentage = testData.get("BrokerFeeValue");

			// Printing values from account overview page
			Assertions.addInfo("Account Overview Page",
					"Asserting Premium,Icat fees,Broker Fees,SLTF on account overview page");
			Assertions.passTest("Account Overview Page", "Premium is $" + premium);
			Assertions.passTest("Account Overview Page", "ICAT Fees is $" + icatFees);
			Assertions.passTest("Account Overview Page", "Broker Fees is $" + otherFees);
			Assertions.passTest("Account Overview Page", "Surplus Contribution Value is $" + surplusContribution);
			Assertions.passTest("Account Overview Page", "Surplus Lines and Taxes Value is $" + surplusLinesandTaxes);
			Assertions.passTest("Account Overview Page",
					"Premium Plus Taxes and Fees Value is $" + premiumPlusTaxesandFees);

			// Converting string values to double
			double d_premium = Double.parseDouble(premium);
			double d_icatFees = Double.parseDouble(icatFees);
			double d_otherFees = Double.parseDouble(otherFees);
			double d_sltfPercentage = Double.parseDouble(sltfPercentage);
			double d_maintenancePercentage = Double.parseDouble(maintenancePercentage);
			double d_brokerFeePercentage = Double.parseDouble(brokerFeePercentage);
			double d_surplusContribution = Double.parseDouble(surplusContribution);

			// Calculating sltf,maintenance,totl premium,brokerfee
			double brokerFeeCalculatedValue = d_premium * (d_brokerFeePercentage / 100);
			double maintenanceCalculatedValue = (d_premium + d_icatFees + d_otherFees + d_surplusContribution)
					* d_maintenancePercentage;
			double sltfCalculatedValue = ((d_premium + d_icatFees + d_otherFees + d_surplusContribution)
					* d_sltfPercentage) + maintenanceCalculatedValue;
			double premiumPlusTaxesandFeesValue = d_premium + d_icatFees + d_otherFees + d_surplusContribution
					+ sltfCalculatedValue;

			// Asserting Actual and calculated broker/sltf/Premium values
			Assertions.addInfo("Scenario 01",
					"Verifying  Actual and calculated broker fees,SLTF,Total Premium values on Account overview page");
			Assertions.verify(otherFees, df.format(brokerFeeCalculatedValue), "Account Overview Page",
					"Actual and Calculated Broker Fee values are matching as per 3%, calculated broker fee : "
							+ brokerFeeCalculatedValue,
					false, false);

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusLinesandTaxes), 2)
					- Precision.round(sltfCalculatedValue, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Actual SLTF is : " + "$" + surplusLinesandTaxes);
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF is : " + "$" + df.format(sltfCalculatedValue));
			} else {
				Assertions.verify(surplusLinesandTaxes, sltfCalculatedValue, "Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);

			}
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(premiumPlusTaxesandFees), 2)
					- Precision.round(premiumPlusTaxesandFeesValue, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual Total Premium is : " + "$" + premiumPlusTaxesandFees);
				Assertions.passTest("Account Overview Page",
						"Calculated Total Premium is : " + "$" + df.format(premiumPlusTaxesandFeesValue));
			} else {
				Assertions.verify(premiumPlusTaxesandFees, premiumPlusTaxesandFeesValue, "Account Overview Page",
						"The Difference between actual Total Premium and calculated Total Premium is more than 0.05",
						false, false);

			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on view/print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("View/Print Full Quote Page", "Clicked on View/Print Full Quote link");

			// Add assertions
			String premiumVPFQ = viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath(5).getData().replace("$", "")
					.replace(",", "").replace("Premium", "");
			String inspecionFeeVPFQ = viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(",",
					"");
			String policyFeeVPFQ = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "");
			String brokerFeeVPFQ = viewOrPrintFullQuotePage.brokerFeeValue.getData().replace("$", "").replace(",", "");
			String sltfVPFQ = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "").replace(",",
					"");
			String maintenanceVPFQ = viewOrPrintFullQuotePage.maintenanceAssessmentValue.getData().replace("$", "")
					.replace(",", "");
			String surplusContributioVPFQ = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			String grandTotalVPFQ = viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath(3).getData()
					.replace("$", "").replace(",", "").replace("Grand Total", "");

			// Printing values from View/Print full quote page
			Assertions.addInfo("View/Print Full Quote Page",
					"Asserting Premium,Inspection fees,Policy Fees,Broker Fees,SLTF,Maintainance Value, Premium Plus Taxes and Fees Value on View/Print Full Quote Page");
			Assertions.passTest("View/Print Full Quote Page", "Premium is $" + premiumVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Inspection Fees is $" + inspecionFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Policy Fees is $" + policyFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Broker Fees is $" + brokerFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page",
					"Surplus Contribution Value is $" + surplusContributioVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Surplus Lines and Taxes Value is $" + sltfVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Maintenance Value is $" + maintenanceVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Grand total value $" + grandTotalVPFQ);

			// Converting String values to double
			double d_premiumVPFQ = Double.parseDouble(premiumVPFQ);
			double d_inspecionFeeVPFQ = Double.parseDouble(inspecionFeeVPFQ);
			double d_policyFeeVPFQ = Double.parseDouble(policyFeeVPFQ);
			double d_brokerFeeVPFQ = Double.parseDouble(brokerFeeVPFQ);
			double d_surplusContributioVPFQ = Double.parseDouble(surplusContributioVPFQ);

			// Calculating sltf,maintenance,Grand total,brokerfee
			double brokerFeeCalculatedValueVPFQ = d_premiumVPFQ * (d_brokerFeePercentage / 100);

			double maintenanceCalculatedValueVPFQ = (d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ
					+ d_brokerFeeVPFQ + d_surplusContributioVPFQ) * d_maintenancePercentage;

			double sltfCalculatedValueVPFQ = (d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ + d_brokerFeeVPFQ
					+ d_surplusContributioVPFQ) * d_sltfPercentage;

			double calculatedGrandTotal = d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ
					+ d_surplusContributioVPFQ + d_brokerFeeVPFQ + sltfCalculatedValueVPFQ
					+ maintenanceCalculatedValueVPFQ;

			// Asserting Actual and calculated sltf/Premium values
			Assertions.addInfo("Scenario 02",
					"Verifying  Actual and calculated broker fees,Maintenance values,SLTF,Total Premium values on View/Print Full Quote page");
			Assertions.verify(brokerFeeVPFQ, df.format(brokerFeeCalculatedValueVPFQ), "View/Print Full Quote Page",
					"Actual and Calculated Broker Fee values are matching as per 3%, Calculated Broker Fee  "
							+ df.format(brokerFeeCalculatedValueVPFQ),
					false, false);
			Assertions.verify(maintenanceVPFQ, df.format(maintenanceCalculatedValueVPFQ), "View/Print Full Quote Page",
					"Actual and Calculated Maintenance values are matching as per 0.00025 for VA state, Calculated Maintenance values  "
							+ df.format(maintenanceCalculatedValueVPFQ),
					false, false);

			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(sltfVPFQ), 2) - Precision.round(sltfCalculatedValueVPFQ, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Actual SLTF is :" + "$" + sltfVPFQ);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated SLTF is :" + "$" + df.format(sltfCalculatedValueVPFQ));
			} else {
				Assertions.verify(sltfVPFQ, sltfCalculatedValueVPFQ, "View/Print Full Quote Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);
			}

			// IO-21306
			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(grandTotalVPFQ), 2) - Precision.round(calculatedGrandTotal, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Actual Total Premium is :" + "$" + grandTotalVPFQ);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated Total Premium is :" + "$" + df.format(calculatedGrandTotal));
			} else {
				Assertions.verify(grandTotalVPFQ, calculatedGrandTotal, "View/Print Full Quote Page",
						"The Difference between actual Total Premium and calculated Total Premium is more than 0.05",
						false, false);

			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Navigate to account overview page
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Navigate to homepage and logout
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as USM successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.waitTime(2);// need wait time to load the page
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as Producer successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");

			testData = data.get(data_Value2);
			// Add broker fees
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			preferenceOptionsPage.addBrokerFees(testData);
			Assertions.passTest("Preference Options Page", "Details entered successfully");

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(quoteNumber);

			// Add new building in location 2
			Assertions.addInfo("Account Overview Page",
					"Creating Another Quote by adding new building and new location");
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			locationPage.locationLink.formatDynamicPath(2).scrollToElement();
			locationPage.locationLink.formatDynamicPath(2).click();
			locationPage.buildingLink.formatDynamicPath(2, 1).click();
			locationPage.copyDupBldgLink.scrollToElement();
			locationPage.copyDupBldgLink.click();
			buildingPage.scrollToBottomPage();
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// Create quote with AOP peril
			if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}

			// Click on OK
			if (buildingNoLongerQuoteablePage.okButton.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.okButton.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.okButton.scrollToElement();
				buildingNoLongerQuoteablePage.okButton.click();
			}
			if (buildingPage.createQuote.checkIfElementIsPresent()
					&& buildingPage.createQuote.checkIfElementIsDisplayed()) {
				buildingPage.createQuote.scrollToElement();
				buildingPage.createQuote.click();
			}

			if (buildingUnderMinimumCostPage.continueButton.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.continueButton.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.continueButton.scrollToElement();
				buildingUnderMinimumCostPage.continueButton.click();
			}

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

			// Enter refer quote Page
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();

				// Get quote for referral
				String quoteNumber2 = referQuotePage.quoteNumberforReferral.getData();

				// Navigate to homepage and logout
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.click();
				Assertions.passTest("Login Page", "Logged out as Producer successfully");

				// Login as Producer
				loginPage.refreshPage();
				loginPage.waitTime(2);// need wait time to load the page
				loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
				Assertions.passTest("Login Page", "Logged in as USM successfully");

				// searching the quote number in grid and clicking on the quote link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber2);
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();
				Assertions.passTest("Home Page", "Searched Submitted quote " + quoteNumber2 + " successfullly");

				// Approve the quote for referral
				if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
					referralPage.pickUp.scrollToElement();
					referralPage.pickUp.click();
				}
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve/Decline Quote Page", "Quote approved successfully");

				// Search for approved quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber2);
			}
			String quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page",
					"Account Overview Page Loaded successfully.Quote Number :  " + quoteNumber2);

			// Add assertions
			String premium2 = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			String icatFees2 = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			String otherFees2 = accountOverviewPage.otherFees.getData().replace("$", "").replace(",", "");
			String surplusLinesandTaxes2 = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			String premiumPlusTaxesandFees2 = accountOverviewPage.totalPremiumValue.getData().replace("$", "")
					.replace(",", "");
			String surplusContribution2 = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			String sltfPercentage2 = testData.get("SurplusLinesTaxesPercentage");
			String maintenancePercentage2 = testData.get("MaintenancePercentage");

			// Printing values from account overview page
			Assertions.addInfo("Account Overview Page",
					"Asserting Premium,Icat Fees,Broker Fees,SLTF on Account Overview page");
			Assertions.passTest("Account Overview Page", "Premium is $" + premium2);
			Assertions.passTest("Account Overview Page", "ICAT Fees is $" + icatFees2);
			Assertions.passTest("Account Overview Page", "Broker Fees is $" + otherFees2);
			Assertions.passTest("Account Overview Page", "Surpluscontribution is $" + surplusContribution2);
			Assertions.passTest("Account Overview Page", "Surplus Lines and Taxes Value is $" + surplusLinesandTaxes2);
			Assertions.passTest("Account Overview Page",
					"Premium Plus Taxes and Fees Value is $" + premiumPlusTaxesandFees2);

			// Converting string values to double
			double d_premium2 = Double.parseDouble(premium2);
			double d_icatFees2 = Double.parseDouble(icatFees2);
			double d_otherFees2 = Double.parseDouble(otherFees2);
			double d_surplusContribution2 = Double.parseDouble(surplusContribution2);
			double d_sltfPercentage2 = Double.parseDouble(sltfPercentage2);
			double d_maintenancePercentage2 = Double.parseDouble(maintenancePercentage2);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double maintenanceCalculatedValue2 = (d_premium2 + d_icatFees2 + d_otherFees2 + d_surplusContribution2)
					* d_maintenancePercentage2;
			double sltfCalculatedValue2 = ((d_premium2 + d_icatFees2 + d_otherFees2 + d_surplusContribution2)
					* d_sltfPercentage2) + maintenanceCalculatedValue2;
			double premiumPlusTaxesandFeesValue2 = d_premium2 + d_icatFees2 + d_otherFees2 + d_surplusContribution2
					+ sltfCalculatedValue2;

			// verifying actual and calculated sltf and total premium
			Assertions.addInfo("Scenario 03",
					"Verifying  Actual and calculated SLTF,Total Premium values on Account overview page");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusLinesandTaxes2), 2)
					- Precision.round(sltfCalculatedValue2, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Actual SLTF is : " + "$" + surplusLinesandTaxes2);
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF is : " + "$" + df.format(sltfCalculatedValue2));
			} else {
				Assertions.verify(surplusLinesandTaxes2, sltfCalculatedValue2, "Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);
			}

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(premiumPlusTaxesandFees2), 2)
					- Precision.round(premiumPlusTaxesandFeesValue2, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual Total Premium is : " + "$" + premiumPlusTaxesandFees2);
				Assertions.passTest("Account Overview Page",
						"Calculated Total Premium is : " + "$" + df.format(premiumPlusTaxesandFeesValue2));
			} else {
				Assertions.verify(premiumPlusTaxesandFees2, premiumPlusTaxesandFeesValue2, "Account Overview Page",
						"The Difference between actual and calculated Total Premium is more than 0.05", false, false);

			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			testData = data.get(data_Value1);

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
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as Producer successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.waitTime(2);// need waittime to load the page
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Searching the quote number in grid and clicking on the quote link
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.uploadPreBindApproveAsUSM();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			Assertions.passTest("Policy Summary Page", "Policy Number :" + policyNumber);

			// Click on NPB endorse link
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse NPB link");

			testData = data.get(data_Value2);
			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Click on change named insured
			endorsePolicyPage.changeNamedInsuredLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse NPB link");

			// Change namedInsured details
			changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);
			Assertions.passTest("Change Named Insured Page", "Insured Details updated successfully");

			// Complete endorsement
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.scrollToTopPage();
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Endorsement completed successfully");

		// Asserting the absence of taxes and fees label
		Assertions.addInfo("Policy Summary Page",
				" Asserting the absence of taxes and fees label on policy summary page");
		Assertions.verify(
				policySummaryPage.taxesAndStateFees.formatDynamicPath("1").checkIfElementIsPresent()
						&& policySummaryPage.taxesAndStateFees.formatDynamicPath("1").checkIfElementIsDisplayed(),
				false, "Policy Summary Page", "Taxes and Fees are not displayed in Policy Summary Page", false, false);

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 108", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 108", "Executed Successfully");
			}
		}
	}
}
