//Summary: Check SLTF is calculated and displayed on Renewal quote and Verify SLTF is included in Total Premium of Alt quote table
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
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC112 extends AbstractCommercialTest {

	public TC112() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID112.xls";
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
		ReferQuotePage referQuotePage = new ReferQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		DecimalFormat df = new DecimalFormat("0.00");

		// Initializing the variables
		int data_Value1 = 0;
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

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// click on get a quote
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}

			// Enter refer quote Page
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
				homePage.signOutButton.click();
				Assertions.passTest("Login Page", "Logged out as Producer successfully");

				// Login as Producer
				loginPage.refreshPage();
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
			String surplusCntribution = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "");
			String sltfPercentage = testData.get("SurplusLinesTaxesPercentage");
			String premiumSurchargePrecentage = testData.get("PremiumSurchargePercentage");
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
			double d_premiumSurchargePrecentage = Double.parseDouble(premiumSurchargePrecentage);
			double d_brokerFeePercentage = Double.parseDouble(brokerFeePercentage);
			double d_surplusCntribution = Double.parseDouble(surplusCntribution);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValue = d_premium * (d_brokerFeePercentage / 100);
			double premiumSurchargeCalculatedValue = (d_premium + d_icatFees + d_otherFees + d_surplusCntribution)
					* d_premiumSurchargePrecentage;
			double sltfCalculatedValue = ((d_premium + d_icatFees + d_otherFees + d_surplusCntribution)
					* d_sltfPercentage) + premiumSurchargeCalculatedValue;
			double premiumPlusTaxesandFeesValueCal = d_premium + d_icatFees + d_otherFees + sltfCalculatedValue
					+ d_surplusCntribution;

			// Asserting Actual and calculated broker/sltf/Premium values
			Assertions.addInfo("Account Overview Page",
					"Verifying Actual and Calculated Broker Fee,SLTF,Total Premium on Account overview page");
			Assertions.verify(otherFees, df.format(brokerFeeCalculatedValue), "Account Overview Page",
					"Actual and Calculated Broker Fee values are matching as per 5%", false, false);

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusLinesandTaxes), 2)
					- Precision.round(sltfCalculatedValue, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Actual SLTF Value :" + "$" + surplusLinesandTaxes);
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Value :" + "$" + df.format(sltfCalculatedValue));
			} else {
				Assertions.verify(surplusLinesandTaxes, sltfCalculatedValue, "Account Overview Page",
						"The Difference between actual  and calculated SLTF is more than 0.05", false, false);
			}

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(premiumPlusTaxesandFees), 2)
					- Precision.round(premiumPlusTaxesandFeesValueCal, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual Total Premium Value :" + "$" + premiumPlusTaxesandFees);
				Assertions.passTest("Account Overview Page",
						"Calculated Total Premium Value :" + "$" + df.format(premiumPlusTaxesandFeesValueCal));
			} else {
				Assertions.verify(premiumPlusTaxesandFees, premiumPlusTaxesandFeesValueCal, "Account Overview Page",
						"The Difference between actual  and calculated Total Premium is more than 0.05", false, false);
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
			String premiumSurchargeVPFQ = viewOrPrintFullQuotePage.premiumSurcharge.getData().replace("$", "")
					.replace(",", "");
			String grandTotalVPFQ = viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(3).getData()
					.replace("$", "").replace(",", "").replace("Grand Total", "");
			String surplusContributionVPFQ = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");

			// Printing values from View/Print full quote page
			Assertions.addInfo("View/Print Full Quote Page",
					"Asserting Premium,Inspection fees,Policy Fees,Broker Fees,SLTF,Premium Surcharge Value and Premium Plus Taxes and Fees Value on View/Print Full Quote Page");
			Assertions.passTest("View/Print Full Quote Page", "Premium is $" + premiumVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Inspection Fees is $" + inspecionFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Policy Fees is $" + policyFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Broker Fees is $" + brokerFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Surplus Lines and Taxes Value is $" + sltfVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Premium Surcharge Value is $" + premiumSurchargeVPFQ);
			Assertions.passTest("View/Print Full Quote Page",
					"Premium Plus Taxes and Fees Value is $" + grandTotalVPFQ);
			Assertions.passTest("View/Print Full Quote Page",
					"Surplus Contribution Value is $" + surplusContributionVPFQ);

			// Converting String values to double
			double d_premiumVPFQ = Double.parseDouble(premiumVPFQ);
			double d_inspecionFeeVPFQ = Double.parseDouble(inspecionFeeVPFQ);
			double d_policyFeeVPFQ = Double.parseDouble(policyFeeVPFQ);
			double d_brokerFeeVPFQ = Double.parseDouble(brokerFeeVPFQ);
			double d_surplusContributionVPFQ = Double.parseDouble(surplusContributionVPFQ);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValueVPFQ = d_premiumVPFQ * (d_brokerFeePercentage / 100);
			double premiumSurchargeCalculatedValueVPFQ = ((d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ
					+ d_brokerFeeVPFQ + d_surplusContributionVPFQ)) * d_premiumSurchargePrecentage;
			double sltfCalculatedValueVPFQ = (((d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ + d_brokerFeeVPFQ
					+ d_surplusContributionVPFQ) * d_sltfPercentage));
			double premiumPlusTaxesandFeesValueVPFQ = d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ
					+ d_brokerFeeVPFQ + d_surplusContributionVPFQ + sltfCalculatedValueVPFQ
					+ premiumSurchargeCalculatedValueVPFQ;

			// Asserting Actual and calculated sltf/Premium values
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying  Actual and calculated broker fees,Premium Surcharge values,SLTF,Total Premium values on View/Print Full Quote page");
			Assertions.verify(brokerFeeVPFQ, df.format(brokerFeeCalculatedValueVPFQ), "View/Print Full Quote Page",
					"Actual and Calculated Broker Fee values are matching as per 5%", false, false);
			Assertions.verify(premiumSurchargeVPFQ, df.format(premiumSurchargeCalculatedValueVPFQ),
					"View/Print Full Quote Page",
					"Actual and Calculated Premium Surcharge values are matching as per 1.8% for KY state", false,
					false);
			Assertions.verify(sltfVPFQ, df.format(sltfCalculatedValueVPFQ), "View/Print Full Quote Page",
					"Actual SLTF and Calculated SLTF are matching as per 3% for KY state", false, false);

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(grandTotalVPFQ), 2))
					- Precision.round(premiumPlusTaxesandFeesValueVPFQ, 2), 2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated Total Premium : " + "$" + df.format(premiumPlusTaxesandFeesValueVPFQ));
				Assertions.passTest("View/Print Full Quote Page", "Actual Total Premium : " + "$" + grandTotalVPFQ);
			} else {
				Assertions.verify(grandTotalVPFQ, premiumPlusTaxesandFeesValueVPFQ, "View/Print Full Quote Page",
						"The Difference between actual  and calculated Total Premium is more than 0.05", false, false);

			}

			// Navigate to account overview page
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Navigate to homepage and logout
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as Producer successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");
			homePage.searchQuote(quoteNumber);

			// Entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Navigate to homepage and logout
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the quote number in grid and clicking on the quote link
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			Assertions.passTest("Policy Summary Page", "Policy Number :" + policyNumber);

		// Asserting absence of taxes and fees label
		Assertions.addInfo("Policy Summary Page", "Asserting the absence of taxes and fees on policy summary page");
		Assertions.verify(
				policySummaryPage.taxesAndStateFees.formatDynamicPath("1").checkIfElementIsPresent()
						&& policySummaryPage.taxesAndStateFees.formatDynamicPath("1").checkIfElementIsDisplayed(),
				false, "Policy Summary Page", "Taxes and Fees are not displayed in Policy Summary Page", false, false);

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on Expacc
			homePage.scrollToBottomPage();
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();

			// Enter expacc details
			Assertions.verify(expaccInfoPage.submit.checkIfElementIsDisplayed(), true, "Expacc Info Page",
					"Expacc details page loaded successfully", false, false);
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("Expacc Info Page", "Expacc Details entered successfully");

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);

			// Renew policy and release to producer
			Assertions.addInfo("Policy Summary Page", "Renewing the Policy");
			policySummaryPage.renewPolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew policy link");

			// Click on continue
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// click on Yes
			if (policyRenewalPage.yesButton.checkIfElementIsPresent()
					&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.yesButton.scrollToElement();
				policyRenewalPage.yesButton.click();
			}

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Renewal created and released to producer");

			// Getting renewal quote number
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Renewal quote number is " + renewalQuoteNumber);

			// Asserting the premium values
			String premiumRenewal = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			String icatFeesRenewal = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			String otherFeesRenewal = accountOverviewPage.otherFees.getData().replace("$", "").replace(",", "");

			String premiumPlusTaxesandFeesRenewal = accountOverviewPage.totalPremiumValue.getData().replace("$", "")
					.replace(",", "");
			String surplusContributionRenewal = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "");
			String sltfRenewal = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");

			// Printing values from account overview page
			Assertions.addInfo("Account Overview Page",
					"Asserting Premium,Icat fees,Broker Fees,SLTF,Premium Plus Taxes and Fees for renewal quote on account overview page");
			Assertions.passTest("Account Overview Page", "Premium for Renewal is $" + premiumRenewal);
			Assertions.passTest("Account Overview Page", "ICAT Fees for Renewal is $" + icatFeesRenewal);
			Assertions.passTest("Account Overview Page", "Broker Fees for Renewal is $" + otherFeesRenewal);
			Assertions.passTest("Account Overview Page",
					"Premium Plus Taxes and Fees Value for Renewal is $" + premiumPlusTaxesandFeesRenewal);
			Assertions.passTest("Account Overview Page",
					"Surplus Contribution value for Renewal is $" + surplusContributionRenewal);
			Assertions.passTest("Account Overview Page", "SLTF value for Renewal is $" + sltfRenewal);

			// Converting string values to double
			double d_premiumRenewal = Double.parseDouble(premiumRenewal);
			double d_icatFeesRenewal = Double.parseDouble(icatFeesRenewal);
			double d_otherFeesRenewal = Double.parseDouble(otherFeesRenewal);
			double d_surplusContributionRenewal = Double.parseDouble(surplusContributionRenewal);
			double d_sltfRenewal = Double.parseDouble(sltfRenewal);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValueRenewal = d_premiumRenewal * (d_brokerFeePercentage / 100);

			double premiumPlusTaxesandFeesValueRenewal = d_premiumRenewal + d_icatFeesRenewal + d_otherFeesRenewal
					+ d_surplusContributionRenewal + d_sltfRenewal;

			// Asserting Actual and calculated broker/sltf/Premium values
			Assertions.addInfo("Account Overview Page",
					"Verifying Actual and Calculated Broker Fee,SLTF,Total Premium for the renewal quote on Account overview page");
			Assertions.verify(otherFeesRenewal, df.format(brokerFeeCalculatedValueRenewal), "Account Overview Page",
					"Actual and Calculated Broker Fee values for Renewal quote are matching as per 5%", false, false);

			double d_premiumPlusTaxesandFeesRenewal = Double.parseDouble(premiumPlusTaxesandFeesRenewal);
			if (Precision.round(Math.abs(Precision.round(d_premiumPlusTaxesandFeesRenewal, 2)
					- Precision.round(premiumPlusTaxesandFeesValueRenewal, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Calculated Total Premium for Renewal quote : " + "$"
						+ Precision.round(d_premiumPlusTaxesandFeesRenewal, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Total Premium for Renewal quote : " + "$" + premiumPlusTaxesandFeesValueRenewal);
				Assertions.passTest("Account Overview Page",
						"Actual and Calculated Total Premium for Renewal quote are matching");
			} else {
				Assertions.verify(d_premiumPlusTaxesandFeesRenewal, premiumPlusTaxesandFeesValueRenewal,
						"Account Overview Page",
						"The Difference between actual  and calculated Total Premium is more than 0.05", false, false);

			}
			// Click on view/print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");

			// Add assertions
			String premiumVPFQRenewal = viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(5).getData()
					.replace("$", "").replace(",", "").replace("Premium", "");
			String policyFeeVPFQRenewal = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",",
					"");
			String brokerFeeVPFQRenewal = viewOrPrintFullQuotePage.brokerFeeValue.getData().replace("$", "")
					.replace(",", "");
			String grandTotalVPFQRenewal = viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(3).getData()
					.replace("$", "").replace(",", "").replace("Grand Total", "");
			String surpluscontributionVPFQRenewal = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");
			String sltfVPFQRenewal = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "")
					.replace(",", "");
			String premiumsurchargeVPFQRenewal = viewOrPrintFullQuotePage.premiumSurcharge.getData().replace("$", "")
					.replace(",", "");

			// Printing values from View/Print full quote page
			Assertions.addInfo("View/Print Full Quote Page",
					"Asserting Premium,Policy fees,Broker Fees,SLTF,Premium Surcharge Value,Premium Plus Taxes and Fees for renewal quote on account overview page");
			Assertions.passTest("View/Print Full Quote Page", "Premium for Renewal is $" + premiumVPFQRenewal);
			Assertions.passTest("View/Print Full Quote Page", "Policy Fees for Renewal is $" + policyFeeVPFQRenewal);
			Assertions.passTest("View/Print Full Quote Page", "Broker Fees for Renewal is $" + brokerFeeVPFQRenewal);
			Assertions.passTest("View/Print Full Quote Page",
					"Premium Plus Taxes and Fees Value for Renewal is $" + grandTotalVPFQRenewal);
			Assertions.passTest("View/Print Full Quote Page",
					"Surplus Contribution Value for Renewal is $" + surpluscontributionVPFQRenewal);
			Assertions.passTest("View/Print Full Quote Page", "SLTF Value for Renewal is $" + sltfVPFQRenewal);
			Assertions.passTest("View/Print Full Quote Page",
					"Premium SurchCharge for Renewal is $" + premiumsurchargeVPFQRenewal);

			// Converting String values to double
			double d_premiumVPFQRenewal = Double.parseDouble(premiumVPFQRenewal);
			double d_policyFeeVPFQRenewal = Double.parseDouble(policyFeeVPFQRenewal);
			double d_brokerFeeVPFQRenewal = Double.parseDouble(brokerFeeVPFQRenewal);
			double d_premiumsurchargeVPFQRenewal = Double.parseDouble(premiumsurchargeVPFQRenewal);
			double d_sltfVPFQRenewal = Double.parseDouble(sltfVPFQRenewal);
			double d_surpluscontributionVPFQRenewal = Double.parseDouble(surpluscontributionVPFQRenewal);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValueVPFQRenewal = d_premiumVPFQRenewal * (d_brokerFeePercentage / 100);

			double premiumPlusTaxesandFeesValueVPFQRenewal = d_premiumVPFQRenewal + d_policyFeeVPFQRenewal
					+ d_brokerFeeVPFQRenewal + d_premiumsurchargeVPFQRenewal + d_sltfVPFQRenewal
					+ d_surpluscontributionVPFQRenewal;

			// Asserting Actual and calculated sltf/Premium values
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying  Actual and calculated broker fees,Premium Surcharge values,SLTF,Total Premium values for renewal quote on View/Print Full Quote page");
			Assertions.verify(brokerFeeVPFQRenewal, df.format(brokerFeeCalculatedValueVPFQRenewal),
					"View/Print Full Quote Page",
					"Actual and Calculated Broker Fee for Renewal quote values are matching as per 5%", false, false);

			double d_grandTotalVPFQRenewal = Double.parseDouble(grandTotalVPFQRenewal);
			if (Precision.round(Math.abs(Precision.round(d_grandTotalVPFQRenewal, 2)
					- Precision.round(premiumPlusTaxesandFeesValueVPFQRenewal, 2)), 2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Calculated Total Premium : " + "$"
						+ Precision.round(premiumPlusTaxesandFeesValueVPFQRenewal, 2));
				Assertions.passTest("View/Print Full Quote Page",
						"Actual Total Premium : " + "$" + d_grandTotalVPFQRenewal);
				Assertions.passTest("View/Print Full Quote Page",
						"Actual Total Premium and Calculated Total Premium are matching");
			} else {
				Assertions.verify(d_grandTotalVPFQRenewal, premiumPlusTaxesandFeesValueVPFQRenewal,
						"View/Print Full Quote Page",
						"The Difference between actual  and calculated Total Premium is more than 0.05", false, false);

			}
			Assertions.addInfo("View/Print Full Quote Page", "Asserting Terms and Condition Wordings");
			Assertions.verify(viewOrPrintFullQuotePage.termsAndConditionsWordings.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					viewOrPrintFullQuotePage.termsAndConditionsWordings.getData() + " is displayed", false, false);

			// Navigate to account overview page
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on quote1 alt quotes total premium
			accountOverviewPage.quoteOptions1TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions1TotalPremium.click();
			String quoteNumberAlt1 = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number for Renewal Alt quote1 is " + quoteNumberAlt1);

			// Add assertions
			String premiumRenewalAlt1 = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			String icatFeesRenewalAlt1 = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			String otherFeesRenewalAlt1 = accountOverviewPage.otherFees.getData().replace("$", "").replace(",", "");
			String premiumPlusTaxesandFeesRenewalAlt1 = accountOverviewPage.totalPremiumValue.getData().replace("$", "")
					.replace(",", "");
			String surpluscontributionAlt1 = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "");
			String sltfAlt1 = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");

			// Printing values from account overview page
			Assertions.addInfo("Account Overview Page",
					"Asserting Premium,Icat fees,Broker Fees,SLTF,Premium Plus Taxes and Fees for renewal alt quote 1 on account overview page");
			Assertions.passTest("Account Overview Page", "Premium for Renewal Alt Quote 1 is $" + premiumRenewalAlt1);
			Assertions.passTest("Account Overview Page",
					"ICAT Fees for Renewal Alt Quote 1 is $" + icatFeesRenewalAlt1);
			Assertions.passTest("Account Overview Page",
					"Broker Fees for Renewal Alt Quote 1 is $" + otherFeesRenewalAlt1);
			Assertions.passTest("Account Overview Page",
					"Premium Plus Taxes and Fees Value for Renewal Alt Quote 1 is $"
							+ premiumPlusTaxesandFeesRenewalAlt1);
			Assertions.passTest("Account Overview Page", "SLTF Value Renewal Alt Quote 1 is $" + sltfAlt1);
			Assertions.passTest("Account Overview Page",
					"Surplus Contribution Value for Renewal Alt Quote 1 is $" + surpluscontributionAlt1);

			// Converting string values to double
			double d_premiumRenewalAlt1 = Double.parseDouble(premiumRenewalAlt1);
			double d_icatFeesRenewalAlt1 = Double.parseDouble(icatFeesRenewalAlt1);
			double d_otherFeesRenewalAlt1 = Double.parseDouble(otherFeesRenewalAlt1);
			double d_surpluscontributionAlt1 = Double.parseDouble(surpluscontributionAlt1);
			double d_sltfAlt1 = Double.parseDouble(sltfAlt1);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValueRenewalAlt1 = d_premiumRenewalAlt1 * (d_brokerFeePercentage / 100);

			double premiumPlusTaxesandFeesValueRenewalAlt1 = d_premiumRenewalAlt1 + d_icatFeesRenewalAlt1
					+ d_otherFeesRenewalAlt1 + d_sltfAlt1 + d_surpluscontributionAlt1;

			// Asserting Actual and calculated broker/sltf/Premium value
			Assertions.addInfo("Account Overview Page",
					"Verifying Actual and Calculated Broker Fee,SLTF,Total Premium for the renewal alt quote 1 on Account overview page");
			Assertions.verify(otherFeesRenewalAlt1, df.format(brokerFeeCalculatedValueRenewalAlt1),
					"Account Overview Page",
					"Actual and Calculated Broker Fee values are matching as per 5% for Renewal Alt Quote 1", false,
					false);

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(premiumPlusTaxesandFeesRenewalAlt1), 2)
					- Precision.round(premiumPlusTaxesandFeesValueRenewalAlt1, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Calculated Total Premium for Renewal Alt Quote 1 : " + "$"
						+ df.format(premiumPlusTaxesandFeesValueRenewalAlt1));
				Assertions.passTest("Account Overview Page",
						"Actual Total Premium for Renewal Alt Quote 1 : " + "$" + premiumPlusTaxesandFeesRenewalAlt1);
			} else {
				Assertions.verify(premiumPlusTaxesandFeesRenewalAlt1, premiumPlusTaxesandFeesValueRenewalAlt1,
						"Account Overview Page",
						"The Difference between actual  and calculated Total Premium is more than 0.05", false, false);

			}

			// Click on original quote
			accountOverviewPage.quoteLink2.formatDynamicPath(renewalQuoteNumber).scrollToElement();
			accountOverviewPage.quoteLink2.formatDynamicPath(renewalQuoteNumber).click();

			// Click on quote2 alt quotes total premium
			accountOverviewPage.quoteOptions2TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions2TotalPremium.click();
			String quoteNumberAlt2 = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number for Renewal Alt quote2 is " + quoteNumberAlt2);

			// Add assertions
			String premiumRenewalAlt2 = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			String icatFeesRenewalAlt2 = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			String otherFeesRenewalAlt2 = accountOverviewPage.otherFees.getData().replace("$", "").replace(",", "");
			String premiumPlusTaxesandFeesRenewalAlt2 = accountOverviewPage.totalPremiumValue.getData().replace("$", "")
					.replace(",", "");
			String surpluscontributionAlt2 = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "");
			String sltfAlt2 = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");

			// Printing values from account overview page
			Assertions.addInfo("Account Overview Page",
					"Asserting Premium,Icat fees,Broker Fees,SLTF,Premium Plus Taxes and Fees for renewal alt quote 2 on account overview page");
			Assertions.passTest("Account Overview Page", "Premium for Renewal Alt Quote 2 is $" + premiumRenewalAlt2);
			Assertions.passTest("Account Overview Page",
					"ICAT Fees for Renewal Alt Quote 2 is $" + icatFeesRenewalAlt2);
			Assertions.passTest("Account Overview Page",
					"Broker Fees for Renewal Alt Quote 2 is $" + otherFeesRenewalAlt2);
			Assertions.passTest("Account Overview Page",
					"Premium Plus Taxes and Fees Value for Renewal Alt Quote 2 is $"
							+ premiumPlusTaxesandFeesRenewalAlt2);
			Assertions.passTest("Account Overview Page", "SLTF Value Renewal Alt Quote 1 is $" + sltfAlt2);
			Assertions.passTest("Account Overview Page",
					"Surplus Contribution Value for Renewal Alt Quote 1 is $" + surpluscontributionAlt2);

			// Converting string values to double
			double d_premiumRenewalAlt2 = Double.parseDouble(premiumRenewalAlt2);
			double d_icatFeesRenewalAlt2 = Double.parseDouble(icatFeesRenewalAlt2);
			double d_otherFeesRenewalAlt2 = Double.parseDouble(otherFeesRenewalAlt2);
			double d_surpluscontributionAlt2 = Double.parseDouble(surpluscontributionAlt1);
			double d_sltfAlt2 = Double.parseDouble(sltfAlt1);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValueRenewalAlt2 = d_premiumRenewalAlt2 * (d_brokerFeePercentage / 100);

			double premiumPlusTaxesandFeesValueRenewalAlt2 = d_premiumRenewalAlt2 + d_icatFeesRenewalAlt2
					+ d_otherFeesRenewalAlt2 + d_sltfAlt2 + d_surpluscontributionAlt2;

			// Asserting Actual and calculated broker/sltf/Premium values
			Assertions.addInfo("Account Overview Page",
					"Verifying Actual and Calculated Broker Fee,SLTF,Total Premium for the renewal alt quote 2 on Account overview page");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(otherFeesRenewalAlt2), 2))
					- Precision.round(brokerFeeCalculatedValueRenewalAlt2, 2), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Broker Fees : " + "$" + df.format(brokerFeeCalculatedValueRenewalAlt2));
				Assertions.passTest("Account Overview Page", "Actual Broker Fees : " + "$" + otherFeesRenewalAlt2);
			} else {
				Assertions.verify(otherFeesRenewalAlt2, brokerFeeCalculatedValueRenewalAlt2, "Account Overview Page",
						"The Difference between actual  and calculated Broker fee is more than 0.05", false, false);

			}

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(premiumPlusTaxesandFeesRenewalAlt2), 2))
					- Precision.round(premiumPlusTaxesandFeesValueRenewalAlt2, 2), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Total Premium : " + "$" + df.format(premiumPlusTaxesandFeesValueRenewalAlt2));
				Assertions.passTest("Account Overview Page",
						"Actual Total Premium : " + "$" + premiumPlusTaxesandFeesRenewalAlt2);
			} else {
				Assertions.verify(premiumPlusTaxesandFeesRenewalAlt2, premiumPlusTaxesandFeesValueRenewalAlt2,
						"Account Overview Page",
						"The Difference between actual  and calculated Total Premium is more than 0.05", false, false);

			}
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 112", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 112", "Executed Successfully");
			}
		}
	}
}
