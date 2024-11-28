package com.icat.epicenter.test.naho.regression.ISNB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC088_NBSLTFREG002 extends AbstractNAHOTest {

	public TC088_NBSLTFREG002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBSLTFREG002.xls";
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
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewPolicySnapShot viewPolicySnapshot = new ViewPolicySnapShot();
		LoginPage loginPage = new LoginPage();

		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		String quoteNumber;
		String policyNumber;
		String premiumAmount;
		String fees;
		String surplusContribution;
		double surplusTax;
		double stampingFee;
		double MWUAFee;

		BigDecimal surplustaxes;
		BigDecimal stampingtaxes;
		BigDecimal mwuataxes;
		BigDecimal surplusLinestaxes;
		DecimalFormat df = new DecimalFormat("0.00");
		String namedStorm = "2%";

		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
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
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Io-20810
			// Verifying for MS State minimum default named storm deductible is 2% as USM
			Assertions.addInfo("Scenario 01", "Verifying minimum default named storm deductible is 2% as USM");
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
					"Verifing for MS states minimum Named Storm deductible set to "
							+ createQuotePage.namedStormData.getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Adding this condition to handle deductible minimum warning
			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}

			// Getting the quote number from account overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Getting the premium,fees and sltf values from premium section in Account
			// Overview page
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Value is : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Fees Value is : " + accountOverviewPage.feesValue.getData(), false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// getting premium amount
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// getting fees value
			fees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");

			surplusContribution = accountOverviewPage.surplusContibutionValue.getData().replaceAll("[^\\d-.]", "");

			// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
			// percentage 0.04
			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("Account Overview Page", "Surplus Lines Taxes :  " + surplustaxes);

			// calculating stamping percentage by adding Premium+Fees and multiplying by
			// stamping percentage 0.0025
			stampingFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees))
					* Double.parseDouble(testData.get("StampingFeePercentage"));

			// Rounding stamping decimal value to 2 digits
			stampingtaxes = BigDecimal.valueOf(stampingFee);
			stampingtaxes = stampingtaxes.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("Account Overview Page", "Stamping Fee :  " + stampingtaxes);

			// calculating MWUA percentage by adding Premium+Fees and multiplying by mwua
			// percentage 0.03
			MWUAFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees))
					* Double.parseDouble(testData.get("MWUAFeePercentage"));

			// Rounding MWUA decimal value to 2 digits
			mwuataxes = BigDecimal.valueOf(MWUAFee);
			mwuataxes = mwuataxes.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("Account Overview Page", "MWUA Fee :  " + MWUAFee);

			// Rounding Surplus Lines,Taxes and Fees decimal value to 2 digits
			surplusLinestaxes = BigDecimal
					.valueOf(surplustaxes.doubleValue() + stampingtaxes.doubleValue() + mwuataxes.doubleValue());

			// Comparing actual and expected SLTF value and printing calculated value
			String actualSltf = accountOverviewPage.sltfValue.getData().replace(",", "").replace("$", "");
			double d_actualSltf = Double.parseDouble(actualSltf);
			double d_surplustaxesandFees = surplusLinestaxes.doubleValue();
			if (Precision.round(Math.abs(Precision.round(d_actualSltf, 2) - Precision.round(d_surplustaxesandFees, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes and Fees : " + "$" + Precision.round(d_surplustaxesandFees, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_actualSltf);
			} else {
				Assertions.verify(d_actualSltf, d_surplustaxesandFees, "Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);
			}

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			String actualPremiumValue = accountOverviewPage.totalPremiumValue.getData().replace(",", "").replace("$",
					"");
			double d_actualPremiumValue = Double.parseDouble(actualPremiumValue);
			String calcPremiumValue = df
					.format((Double.parseDouble(premiumAmount)) + (Double.parseDouble(surplusLinestaxes + "")
							+ Double.parseDouble(surplusContribution) + (Double.parseDouble(fees))));

			double d_calcPremiumValue = Precision.round(Double.parseDouble(calcPremiumValue), 2);
			if (Precision.round(
					Math.abs(Precision.round(d_actualPremiumValue, 2) - Precision.round(d_calcPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Premium, Taxes and Fees :  " + "$" + Precision.round(d_calcPremiumValue, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Premium, Taxes and Fees : " + "$" + d_actualPremiumValue);
			} else {
				Assertions.verify(d_actualPremiumValue, d_calcPremiumValue, "Account Overview Page",
						"The Difference between actual Total Premium, Taxes and Fees and calculated Total Premium, Taxes and Fees is more than 0.05",
						false, false);
			}

			// clicking on Request Bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Request Bind Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering Details in Request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			if (bindRequestPage.homePage.checkIfElementIsPresent()
					&& bindRequestPage.homePage.checkIfElementIsDisplayed()) {
				// clicking on Home page button in Bind Request Submitted Page
				Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
						"Bind Request Page loaded successfully", false, false);
				bindRequestPage.clickOnHomepagebutton();
				Assertions.passTest("Home Page", "Clicked on Quote Number");

				// Searching for Quote Number in User Referrals Table
				Assertions.verify(homePage.goToHomepage.checkIfElementIsDisplayed(), true, "Bind Request Page",
						"Home Page loaded successfully", false, false);
				referralPage = homePage.searchReferral(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");

				// Clicking on Approve/Decline Request button in Referrals page
				Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true,
						"Referral Page", "Referral page loaded successfully", false, false);
				requestBindPage = referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// Clicking on Approve in Request Bind Page
				policySummaryPage = requestBindPage.approveRequestNAHO(testData);
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			}

			// Getting the Policy Number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary", "Policy Number is : " + policyNumber);

			// clicking on View Policy Snapshot link
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);

			// Comparing actual and expected SLTF value and printing actual and expected
			// value
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace(",", ""),
					format.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
					"Calculated Surplus Fees : " + format.format(surplustaxes).replace(",", ""), false, false);
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace(",", ""),
					format.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
					"Actual Surplus Fees : " + viewPolicySnapshot.surplusLinesTaxesValue.getData(), false, false);
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData().replace(",", ""),
					format.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
					"Surplus Fees calculated as per Surplus Lines Taxes Percentage 4% for MS is verified", false,
					false);

			// Comparing actual and expected Stamping fee value and printing actual and
			// expected value
			String actualStampingValue = viewPolicySnapshot.stampingFeeValue.getData().replace("$", "");
			double d_actualStampingValue = Double.parseDouble(actualStampingValue);
			double d_stampingtaxesvpfq = stampingtaxes.doubleValue();
			if (Precision.round(
					Math.abs(Precision.round(d_actualStampingValue, 2) - Precision.round(d_stampingtaxesvpfq, 2)),
					2) < 0.05) {
				Assertions.passTest("View Policy Snapshot Page",
						"Calculated Stamping Fees :  " + "$" + Precision.round(d_stampingtaxesvpfq, 2));
				Assertions.passTest("View Policy Snapshot Page",
						"Actual Stamping Fees : " + "$" + d_actualStampingValue);
				Assertions.passTest("View Policy Snapshot Page",
						"Stamping Fees calculated as per Stamping Fees Percentage 0.25% for MS is verified");
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual Stamping Fees and calculated Stamping Fees is more than 0.05");
			}
			// Comparing actual and expected MWUA fee value and printing actual and expected
			// value

			String actualMWUAFees = viewPolicySnapshot.mwuaValue.getData().replace(",", "").replace("$", "");

			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(actualMWUAFees), 2) - Precision.round(MWUAFee, 2)),
					2) < 0.05) {
				Assertions.passTest("View Policy Snapshot Page",
						"Calculated MWUA Fees : " + "$" + Precision.round(MWUAFee, 2));
				Assertions.passTest("View Policy Snapshot Page", "Actual MWUA Fees : " + "$" + actualMWUAFees);
				Assertions.passTest("View Policy Snapshot Page",
						"MWUA Fees calculated as per MWUA Fee Percentage 3% for MS is verified");
			} else {
				Assertions.passTest("View Policy Snapshot Page",
						"The Difference between actual and calculated MWUA fee is more than 0.05");
			}

			// sign out as Usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer Successfully");

			// Creating a new account
			testData = data.get(dataValue1);
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Checking the processing of Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering Prior Loss details
			Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			// Entering Quote details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Io-20810
			// Verifying minimum default named storm deductible is 2% as producer
			Assertions.addInfo("Scenario 02", "Verifying minimu default named storm deductible is 2% as producer");
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
					"All other risks in MS, outside of Tri County, will have a minimum Named Storm deductible set to "
							+ createQuotePage.namedStormData.getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Close the Browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC088 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC088 ", "Executed Successfully");
			}
		}
	}
}
