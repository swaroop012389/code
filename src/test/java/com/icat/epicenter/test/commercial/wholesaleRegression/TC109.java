//Summary: Check SLTF is not calculated and displayed for cancellation and Reinstatement
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
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC109 extends AbstractCommercialTest {

	public TC109() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID109.xls";
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
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
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

			// Click on Get A quote
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}

			// Add assertions
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// getting the quote number
			String quoteForRef = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteForRef);
			String premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			String icatFees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			String otherFees = accountOverviewPage.otherFees.getData().replace("$", "").replace(",", "");
			String surplusLinesandTaxes = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			String premiumPlusTaxesandFees = accountOverviewPage.totalPremiumValue.getData().replace("$", "")
					.replace(",", "");
			String surplusContributionValue = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			String sltfPercentage = testData.get("SurplusLinesTaxesPercentage");
			String slasClearinghouseTrxnFeePrecentage = testData.get("SLASClearinghouseTransactionFeePrecentage");
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

			// Converting string values to double
			double d_premium = Double.parseDouble(premium);
			double d_icatFees = Double.parseDouble(icatFees);
			double d_otherFees = Double.parseDouble(otherFees);
			double d_sltfPercentage = Double.parseDouble(sltfPercentage);
			double d_surplusContributionValue = Double.parseDouble(surplusContributionValue);
			double d_slasClearinghouseTrxnFeePrecentage = Double.parseDouble(slasClearinghouseTrxnFeePrecentage);
			double d_brokerFeePercentage = Double.parseDouble(brokerFeePercentage);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValue = d_premium * (d_brokerFeePercentage / 100);
			double slasClearinghouseTrxnFeeCalculatedValue = (d_premium + d_icatFees + d_otherFees
					+ d_surplusContributionValue) * d_slasClearinghouseTrxnFeePrecentage;
			double sltfCalculatedValue = (((d_premium + d_icatFees + d_otherFees + d_surplusContributionValue)
					* d_sltfPercentage) + slasClearinghouseTrxnFeeCalculatedValue);
			double premiumPlusTaxesandFeesValue = d_premium + d_icatFees + d_otherFees + sltfCalculatedValue
					+ d_surplusContributionValue;

			// Asserting Actual and calculated broker/sltf/Premium values
			Assertions.addInfo("Account Overview Page",
					"Verifying  Actual and calculated broker fees,SLTF,Total Premium values on Account overview page");
			Assertions.verify(otherFees, df.format(brokerFeeCalculatedValue), "Account Overview Page",
					"Actual and Calculated Broker Fee values are matching as per 3%", false, false);
			Assertions.verify(surplusLinesandTaxes, df.format(sltfCalculatedValue), "Account Overview Page",
					"Actual and Calculated SLTF are matching as per 3% for WY state", false, false);
			Assertions.verify(premiumPlusTaxesandFees, df.format(premiumPlusTaxesandFeesValue), "Account Overview Page",
					"Actual and Calculated Total Premium are matching", false, false);

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
			String slasClearinghouseTrxnFeeVPFQ = viewOrPrintFullQuotePage.slasClearinghouseTrxnFee.getData()
					.replace("$", "").replace(",", "");
			String grandTotalVPFQ = viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(3).getData()
					.replace("$", "").replace(",", "").replace("Grand Total", "");
			String surplusContributionValueVPFQ = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");

			// Printing values from View/Print full quote page
			Assertions.addInfo("View/Print Full Quote Page",
					"Asserting Premium,Inspection fees,Policy Fees,Broker Fees,SLTF,SLAS Clearing House Transaction Fee, Premium Plus Taxes and Fees Value on View/Print Full Quote Page");
			Assertions.passTest("View/Print Full Quote Page", "Premium is $" + premiumVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Inspection Fees is $" + inspecionFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Policy Fees is $" + policyFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Broker Fees is $" + brokerFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page", "Surplus Lines and Taxes Value is $" + sltfVPFQ);
			Assertions.passTest("View/Print Full Quote Page",
					"SLAS Clearing House Transaction Fee Value is $" + slasClearinghouseTrxnFeeVPFQ);
			Assertions.passTest("View/Print Full Quote Page",
					"Premium Plus Taxes and Fees Value is $" + grandTotalVPFQ);

			// Converting String values to double
			double d_premiumVPFQ = Double.parseDouble(premiumVPFQ);
			double d_inspecionFeeVPFQ = Double.parseDouble(inspecionFeeVPFQ);
			double d_policyFeeVPFQ = Double.parseDouble(policyFeeVPFQ);
			double d_brokerFeeVPFQ = Double.parseDouble(brokerFeeVPFQ);
			double d_surplusContributionValueVPFQ = Double.parseDouble(surplusContributionValueVPFQ);

			// Calculating sltf,maintenance,totpremium,brokerfee
			double brokerFeeCalculatedValueVPFQ = d_premiumVPFQ * (d_brokerFeePercentage / 100);
			double slasClearinghouseTrxnFeeCalculatedValueVPFQ = ((d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ
					+ d_brokerFeeVPFQ + d_surplusContributionValueVPFQ)) * d_slasClearinghouseTrxnFeePrecentage;
			double sltfCalculatedValueVPFQ = (((d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ + d_brokerFeeVPFQ
					+ d_surplusContributionValueVPFQ) * d_sltfPercentage));
			double premiumPlusTaxesandFeesValueVPFQ = d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ
					+ d_brokerFeeVPFQ + sltfCalculatedValueVPFQ + slasClearinghouseTrxnFeeCalculatedValueVPFQ
					+ d_surplusContributionValueVPFQ;

			// Asserting Actual and calculated sltf/Premium values
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying  Actual and calculated broker fees,SLAS Clearing House Transaction Fee,SLTF,Total Premium values on View/Print Full Quote page");
			Assertions.verify(brokerFeeVPFQ, df.format(brokerFeeCalculatedValueVPFQ), "View/Print Full Quote Page",
					"Actual and Calculated Broker Fee values are matching as per 3%", false, false);
			Assertions.verify(slasClearinghouseTrxnFeeVPFQ, df.format(slasClearinghouseTrxnFeeCalculatedValueVPFQ),
					"View/Print Full Quote Page",
					"Actual and Calculated SLAS Clearing House Transaction Fee values are matching as per 1.75% for WY state",
					false, false);

			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(sltfVPFQ), 2) - Precision.round(sltfCalculatedValueVPFQ, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Actual SLTF is : " + "$" + sltfVPFQ);
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF is : " + "$" + df.format(sltfCalculatedValueVPFQ));
			} else {
				Assertions.verify(sltfVPFQ, sltfCalculatedValueVPFQ, "Account Overview Page",
						"The Difference between actual and calculated SLTF is more than 0.05", false, false);

			}
			Assertions.verify(grandTotalVPFQ.contains(df.format(premiumPlusTaxesandFeesValueVPFQ)), true,
					"View/Print Full Quote Page", "Actual Total Premium and Calculated Total Premium are matching",
					false, false);

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
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as Producer successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Go to homepage Search for quote
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteForRef);

			// Entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteForRef);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Navigate to homepage and logout
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the quote number in grid and clicking on the quote link
			homePage.searchQuote(quoteForRef);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			Assertions.passTest("Referral Page", "Referral Page loaded successfully");
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			Assertions.passTest("Policy Summary Page", "Policy Number :" + policyNumber);

		// Asserting presence of taxes and fees label
		Assertions.addInfo("Policy Summary Page",
				" Asserting the absence of taxes and fees label on policy summary page");
		Assertions.verify(
				policySummaryPage.taxesAndStateFees.formatDynamicPath("1").checkIfElementIsPresent()
						&& policySummaryPage.taxesAndStateFees.formatDynamicPath("1").checkIfElementIsDisplayed(),
				false, "Policy Summary Page", "Taxes and Fees are not displayed in Policy Summary Page", false, false);

			// Cancel policy
			Assertions.addInfo("Policy Summary Page", "Cancelling the Policy");
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.waitTime(3);// need waittime to load the element
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
			cancelPolicyPage.cancellationEffectiveDate.tab();
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Next Button");

			// Asserting absence of taxes and fees label
			Assertions.addInfo("Cancel Policy Page",
					" Asserting the absence of taxes and fees label on Cancel Policy page after cancelling the policy");
			Assertions.verify(
					cancelPolicyPage.sltfData.checkIfElementIsPresent()
							&& cancelPolicyPage.sltfData.checkIfElementIsDisplayed(),
					false, "Cancel Policy Page", "Taxes and Fees are not displayed in Cancel Policy Page", false,
					false);

			// Complete transaction
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Complete Button");

			// Asserting absence of taxes and fees label
			Assertions.addInfo("Cancel Policy Page",
					" Asserting the absence of taTaxes and Fees on Cancel Policy page after cancelling the policy");
			Assertions.verify(
					cancelPolicyPage.sltfData.checkIfElementIsPresent()
							&& cancelPolicyPage.sltfData.checkIfElementIsDisplayed(),
					false, "Cancel Policy Page", "Taxes and Fees are not displayed in Cancel Policy Page", false,
					false);

			// Close transaction
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Close Button");

			// Asserting absence of taxes and fees label
			Assertions.addInfo("Policy Summary Page",
					" Asserting the absence of Taxes and Fees on Policy Summary page after cancelling the policy");
			Assertions.verify(
					policySummaryPage.taxesAndStateFees.formatDynamicPath(1).checkIfElementIsPresent()
							&& policySummaryPage.taxesAndStateFees.formatDynamicPath(1).checkIfElementIsDisplayed(),
					false, "Policy Summary Page", "Taxes and Fees are not displayed in Policy Summary Page", false,
					false);

			// Reinstate Policy
			Assertions.addInfo("Policy Summary Page", "Reinstating the Policy");
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Reinstate Policy link");
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);
			Assertions.passTest("Reinstate Policy Page", "Policy is Reinstated successfully");

		// Asserting absence of taxes and fees label
		Assertions.passTest("Policy Summary Page",
				"Asserting the absence of taxes and fees label is not displayed on Policy Summary Page after Reinstate the policy");
		Assertions.verify(
				policySummaryPage.taxesAndStateFees.formatDynamicPath("1").checkIfElementIsPresent()
						&& policySummaryPage.taxesAndStateFees.formatDynamicPath("1").checkIfElementIsDisplayed(),
				false, "Policy Summary Page", "Taxes and Fees are not displayed in Policy Summary Page", false, false);

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 109", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 109", "Executed Successfully");
			}
		}
	}
}
