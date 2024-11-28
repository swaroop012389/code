/** Program Description: Validate the  SLTF calculated correctly on Requotes and Verify producer entered fees is included in the calculation of SLTF on Account overview page. Also asserting Producer Cancellation Request functionality.
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/28/2021
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

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
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RequestCancellationPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC106 extends AbstractCommercialTest {

	public TC106() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID106.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		LoginPage loginPage = new LoginPage();
		RequestCancellationPage requestCancellationPage = new RequestCancellationPage();
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		// Initializing variables
		String premiumAmount;
		String otherFees;
		String otherFees1;
		String icatFees;
		String actualSLTFValue;
		String actualSLTFValue1;
		String surplusLinesTaxesPercentage;
		String brokerFeesPercentage;
		double expectedsltf;
		BigDecimal roundOffExpectedSltf;
		double expectedBrokerFees;
		BigDecimal roundOffExpectedBrokerFees;
		String insurerInspectionFee;
		String insurerPolicyFee;
		String actualBrokerFees;
		String actualStampingFees;
		String stampingPercentage;
		double expectedStampingFees;
		BigDecimal roundOffExpectedStampingFees;
		String newSLTFValue;
		String quoteNumber2;
		String quoteNumber3;
		String producerFees;
		String policyNumber;
		String quoteNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		Map<String, String> testData = data.get(dataValue1);
		Map<String, String> testData1 = data.get(dataValue2);
		DecimalFormat df = new DecimalFormat("0.00");
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			// Click on user preference link and enter the details
			Assertions.addInfo("Home Page",
					"Select the Broker Fees checkbox and Surplus Lines,Taxes and Fees checkbox");
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

			// Creating New account
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

			// Selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Entering prior loss details
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

			// Getting the quote number Account Overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			Assertions.addInfo("Account Overview Page",
					"Assert the Presence of Other Fees and SLTF on Account Overview Page");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsPresent(), true, "Account Overview Page",
					"Other Fees is present verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// Fetching Premium Amount from Account Overview Page
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Premium Amount : " + "$" + premiumAmount);

			// Fetching ICAT fees value from Account Overview Page
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual ICAT Fees : " + "$" + icatFees);

			// Fetching Actual Other Fees or Broker fees from account overview page
			otherFees = accountOverviewPage.otherFees.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Other Fees : " + "$" + otherFees);

			// Fetching Surplus contribution value from account overview page
			String surplusContributionValue = accountOverviewPage.surplusContributionValue.getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page",
					"Actual Surplus Contribution Value : " + "$" + surplusContributionValue);

			// Actual SLTF value from Account Overview Page
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");

			// Getting surplusLinesTaxesPercentage from Data sheet
			surplusLinesTaxesPercentage = testData.get("SurplusLinesTaxesPercentage");

			// Getting Stamping Percentage from Datasheet
			stampingPercentage = testData.get("StampingPercentage");

			// calculating stamping fee (Premium+ICAT fees+surpluscontribution)*Stamping
			// Percentage 0.04%
			double calStampingFee = ((Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(otherFees) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(stampingPercentage));

			// Calculating sltf percentage by adding (Premium+ICAT
			// fees+otherfees+surpluscontribution*SLTf%)
			// (sltf percentage 4.85% +Stamping Percentage .04%) from account overview page
			expectedsltf = ((Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(otherFees) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(surplusLinesTaxesPercentage)) + calStampingFee;

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf = new BigDecimal(expectedsltf);
			roundOffExpectedSltf = roundOffExpectedSltf.setScale(2, RoundingMode.HALF_UP);

			// Verifying actual SLTF and calculated SLTF on Account Overview page 4.85% from
			// account overview page
			Assertions.addInfo("Scenario 01",
					"Verifying actual SLTF and calculated SLTF on Account Overview page 4.85% from account overview page");
			if (Precision.round(
					Math.abs(
							Precision.round(Double.parseDouble(actualSLTFValue), 2) - Precision.round(expectedsltf, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual and Calculated SLTF value both are same actual SLTF value:" + "$" + actualSLTFValue);
				Assertions.passTest("Account Overview Page", "Calculated SLTF value :" + "$" + df.format(expectedsltf));
			} else {

				Assertions.verify(actualSLTFValue, expectedsltf, "Account Overview Page",
						"The Difference between actual  and calculated SLTF is more than 0.05", false, false);

			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Getting Broker Percentage from Datasheet
			brokerFeesPercentage = testData.get("BrokerFeesPercentage");

			// Calculating Other Fees or Broker Fees as per entered percentage(Premium*10%)
			// from account overview page
			expectedBrokerFees = Double.parseDouble(premiumAmount) * Double.parseDouble(brokerFeesPercentage);

			// Rounding Other Fees or Broker Fees decimal value to 2 digits
			roundOffExpectedBrokerFees = new BigDecimal(expectedBrokerFees);
			roundOffExpectedBrokerFees = roundOffExpectedBrokerFees.setScale(2, RoundingMode.HALF_UP);

			// Verifying actual Broker Fees and calculated Broker Fees are the same
			Assertions.addInfo("Scenario 02", "Verifying actual Broker Fees and calculated Broker Fees are the same");
			Assertions.passTest("Account Overview Page",
					"Calculated Broker Fees : " + format.format(roundOffExpectedBrokerFees));
			if (format.format(Double.parseDouble(otherFees))
					.equalsIgnoreCase(format.format(roundOffExpectedBrokerFees))) {
				Assertions.verify(format.format(Double.parseDouble(otherFees)),
						format.format(roundOffExpectedBrokerFees), "Account Overview Page",
						"The Calculated and Actual Broker Fees are the same for TX Percentage 10%, Actual Broker Fees : "
								+ format.format(Double.parseDouble(otherFees)),
						false, false);
			} else {
				Assertions.verify(otherFees, format.format(roundOffExpectedBrokerFees), "Account Overview Page",
						"The Calculated and Actual Broker Fees are Not the same for TX Percentage 10% : " + otherFees,
						false, false);
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on View/Print Full quote document page link.
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page",
					"Clicked on ViewPrintFull Quote Link successfully and ViewOrPrintFullQuotePage loaded successfully");

			// Getting Commercial property Premium Amount from View/PrintFull quote document
			// page
			premiumAmount = viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath(5).getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page", "Premium Amount : " + "$" + premiumAmount);

			// Getting Insurer Inspection Fees from View/Print Full quote document page
			insurerInspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Insurer Inspection Fees : " + "$" + insurerInspectionFee);

			// Getting Insurer Policy Fee from View/Print Full quote document page
			insurerPolicyFee = viewOrPrintFullQuotePage.policyFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page", "Insurer Policy Fees : " + "$" + insurerPolicyFee);

			// Getting Broker Fee from View/Print Full quote link Page
			actualBrokerFees = viewOrPrintFullQuotePage.brokerFeeValue.getData().replaceAll("[^\\d-.]", "");

			// Getting surplus contribution value from View/Print Full quote link Page
			surplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Actual Surplus Contribution Value : " + "$" + surplusContributionValue);

			// Getting surplusLinesTaxesValue from View/Print Full quote document page
			actualSLTFValue = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replaceAll("[^\\d-.]", "");

			// Getting Stamping Fees from View/Print Full quote document page
			actualStampingFees = viewOrPrintFullQuotePage.stampingFeeValue.getData();

			// calculating sltf percentage by adding Premium+ICAT fees+OtherFees and
			// multiplying by
			// sltf percentage 0.0485
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(insurerInspectionFee)
					+ Double.parseDouble(actualBrokerFees)

					+ Double.parseDouble(insurerPolicyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(surplusLinesTaxesPercentage);

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf = new BigDecimal(expectedsltf);
			roundOffExpectedSltf = roundOffExpectedSltf.setScale(2, RoundingMode.HALF_UP);

			// Verifying actual SLTF and Calculated SLTF are the same( 4.85%)
			Assertions.addInfo("Scenario 03", "Verifying actual SLTF and Calculated SLTF are the same for 4.85%");
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated SLTF Values : " + format.format(roundOffExpectedSltf));
			if (format.format(Double.parseDouble(actualSLTFValue))
					.equalsIgnoreCase(format.format(roundOffExpectedSltf))) {
				Assertions.verify(format.format(Double.parseDouble(actualSLTFValue)),
						format.format(roundOffExpectedSltf), "View or Print Full Quote Page",
						"The Calculated and Actual SLTF values are the same for TX Percentage 4.85%, Actual SLTF values : "
								+ format.format(Double.parseDouble(actualSLTFValue)),
						false, false);
			} else {
				Assertions.verify(format.format(Double.parseDouble(actualSLTFValue)),
						format.format(roundOffExpectedSltf), "View or Print Full Quote Page",
						"The Calculated and Actual SLTF values are Not the same for TX Percentage 4.85% : "
								+ format.format(Double.parseDouble(actualSLTFValue)),
						false, false);
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Calculating Broker fees percentage by adding PremiumAmount+GL PremiumAmount
			// and multiplying by
			// Broker fees percentage 10%
			expectedBrokerFees = Double.parseDouble(premiumAmount) * Double.parseDouble(brokerFeesPercentage);

			// Rounding Broker fees decimal value to 2 digits
			roundOffExpectedBrokerFees = new BigDecimal(expectedBrokerFees);
			roundOffExpectedBrokerFees = roundOffExpectedBrokerFees.setScale(2, RoundingMode.HALF_UP);

			// Verifying actual Broker fees and calculated Broker fees are the same
			Assertions.addInfo("Scenario 04", "Verifying actual Broker fees and calculated Broker fees are the same");
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated Broker Fees : " + format.format(roundOffExpectedBrokerFees));
			if (format.format(Double.parseDouble(actualBrokerFees))
					.equalsIgnoreCase(format.format(roundOffExpectedBrokerFees))) {
				Assertions.verify(format.format(Double.parseDouble(actualBrokerFees)),
						format.format(roundOffExpectedBrokerFees), "View or Print Full Quote Page",
						"The Calculated and Actual Broker Fees values are the same for TX Percentage 10%, Actual Broker Fees : "
								+ format.format(Double.parseDouble(actualBrokerFees)),
						false, false);
			} else {
				Assertions.verify(format.format(Double.parseDouble(actualBrokerFees)),
						format.format(roundOffExpectedBrokerFees), "View or Print Full Quote Page",
						"The Calculated and Actual Broker Fees values are Not the same for TX Percentage 10% : "
								+ format.format(Double.parseDouble(actualBrokerFees)),
						false, false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// calculating Stamping Fees percentage by adding Premium+ICAT fees+OtherFees
			// and multiplying by
			// Stamping Fees percentage 0.0004
			expectedStampingFees = (Double.parseDouble(premiumAmount) + Double.parseDouble(insurerInspectionFee)
					+ Double.parseDouble(actualBrokerFees)

					+ Double.parseDouble(insurerPolicyFee) + Double.parseDouble(surplusContributionValue))
					* (Double.parseDouble(stampingPercentage));

			// Rounding Stamping Fees decimal value to 2 digits
			roundOffExpectedStampingFees = new BigDecimal(expectedStampingFees);
			roundOffExpectedStampingFees = roundOffExpectedStampingFees.setScale(2, RoundingMode.HALF_UP);

			// Verifying actual stamping fee and calculated stamping fee are the same
			Assertions.addInfo("Scenario 05", "Verifying actual stamping fee and calculated stamping fee are the same");
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated Stamping Fees : " + format.format(roundOffExpectedStampingFees));
			if (actualStampingFees.equalsIgnoreCase(format.format(roundOffExpectedStampingFees))) {
				Assertions.verify(actualStampingFees, format.format(roundOffExpectedStampingFees),
						"View or Print Full Quote Page",
						"The Calculated and Actual Stamping Fees are the same for TX Percentage 0.04%, Actual Stamping Fees : "
								+ actualStampingFees,
						false, false);
			} else {
				Assertions.verify(actualStampingFees, format.format(roundOffExpectedStampingFees),
						"View or Print Full Quote Page",
						"The Calculated and Actual Stamping Fees are Not the same for TX Percentage 0.04% : "
								+ actualStampingFees,
						false, false);
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// View or Print Full Quote Page click on go back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View or Print Full Quote Page",
					"Clicked on Back Button successfully and Navigated to Account Overview Page");

			// Clicking on Edit Fees Button and add the details
			actualSLTFValue1 = accountOverviewPage.sltfValue.getData();
			accountOverviewPage.editFees.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Fees Button successfully");

			// Click on add custom fee
			Assertions.addInfo("Account Overview Page", "Entering the Producer Fees");
			accountOverviewPage.addCustomFee.scrollToElement();
			accountOverviewPage.addCustomFee.click();
			accountOverviewPage.addCustomFee.formatDynamicPath(2).waitTillPresenceOfElement(60);
			accountOverviewPage.customFieldName.formatDynamicPath(1).waitTillPresenceOfElement(60);
			accountOverviewPage.customFieldName.formatDynamicPath(1).setData(testData.get("CustomFieldName"));
			accountOverviewPage.customFieldValue.formatDynamicPath(1).waitTillPresenceOfElement(60);
			accountOverviewPage.customFieldValue.formatDynamicPath(1).setData(testData.get("CustomFieldValue"));
			accountOverviewPage.customeFeeSave.waitTillVisibilityOfElement(60);
			accountOverviewPage.customeFeeSave.scrollToElement();
			accountOverviewPage.customeFeeSave.click();
			// accountOverviewPage.saveButton.waitTillVisibilityOfElement(60);
			// accountOverviewPage.saveButton.click();
			producerFees = testData.get("CustomFieldValue");
			Assertions.passTest("Account Overview Page",
					"Clicked on Editfees Button and Entered Producer Fees :" + producerFees);

			// Checking Other fees update or not after updating other fees
			accountOverviewPage.waitTime(3);// need wait time to load the page
			accountOverviewPage.refreshPage();
			Assertions.addInfo("Scenario 06", "Assert the Other Fees  and sltf Before and After Updating the Fees");
			otherFees1 = accountOverviewPage.otherFees.getData();
			Assertions.passTest("Account Overview Page", "Before Updating OtherFees :" + otherFees);
			Assertions.passTest("Account Overview Page", "After Updating OtherFees :" + otherFees1);

			// Getting New SLTF value on account overview page after updating Other Fees
			accountOverviewPage.refreshPage();
			newSLTFValue = accountOverviewPage.sltfValue.getData();
			Assertions.passTest("Account Overview Page", "SLTF Values, Before UpDating OtherFees :" + actualSLTFValue1);
			Assertions.passTest("Account Overview Page", "SLTF Values, After Updating OtherFess :" + newSLTFValue);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Clicking on create another quote button in Account overview page
			Assertions.addInfo("Account Overview Page", "Creating Another quote");
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview page loaded successfully", false, false);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create Another Quote");

			// Clicking on override in building no longer quotable page
			if (buildingNoLongerQuotablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuotablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuotablePage.override.scrollToElement();
				buildingNoLongerQuotablePage.override.click();
			}

			// Selecting peril
			if (!testData1.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData1.get("Peril"));
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData1);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting the quote number from Account Overview Page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber2 = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number2 :  " + quoteNumber2);

			// Click on View/Print Full quote link for Quote2.
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page",
					"Clicked on ViewPrintFull Quote Link successfully and ViewOrPrintFullQuotePage loaded successfully");

			// Getting Commercial property Premium Amount from View/Print Full quote link
			// Page
			premiumAmount = viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath(5).getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page", "Premium Amount for Quote2 : " + "$" + premiumAmount);

			// Getting Insurer Inspection Fees from View/Print Full quote link Page
			insurerInspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Insurer Inspection Fees for Quote2 : " + "$" + insurerInspectionFee);

			// Getting Insurer Policy Fee from View/Print Full quote link Page
			insurerPolicyFee = viewOrPrintFullQuotePage.policyFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Insurer Policy Fees for Quote2 : " + "$" + insurerPolicyFee);

			// Getting Broker Fee from View/Print Full quote link Page
			actualBrokerFees = viewOrPrintFullQuotePage.brokerFeeValue.getData().replaceAll("[^\\d-.]", "");

			// Getting surplus contribution value from View/Print Full quote link Page
			surplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Actual Surplus Contribution Value for Quote2: " + "$" + surplusContributionValue);

			// Getting surplusLinesTaxesValue from View/Print Full quote link Page
			actualSLTFValue = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replaceAll("[^\\d-.]", "");

			// Getting Stamping Fees from View/Print Full quote link Page
			actualStampingFees = viewOrPrintFullQuotePage.stampingFeeValue.getData();

			// Calculating sltf percentage by adding Premium+ICAT fees+OtherFees and
			// multiplying by
			// sltf percentage 0.0485...For Quote2
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(insurerInspectionFee)
					+ Double.parseDouble(actualBrokerFees)

					+ Double.parseDouble(insurerPolicyFee) + Double.parseDouble(surplusContributionValue))
					* (Double.parseDouble(surplusLinesTaxesPercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf = new BigDecimal(expectedsltf);
			roundOffExpectedSltf = roundOffExpectedSltf.setScale(2, RoundingMode.HALF_UP);

			// Verifying actual SLTF and calculated SLTF in VieworPrint quote page SLTF
			// Percentage 4.85% for Quote2
			Assertions.addInfo("Cenario 07",
					"Verifying actual SLTF and calculated SLTF in VieworPrint full quote page");
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated SLTF for Quote2 : " + format.format(roundOffExpectedSltf));
			if (format.format(Double.parseDouble(actualSLTFValue))
					.equalsIgnoreCase(format.format(roundOffExpectedSltf))) {
				Assertions.verify(format.format(Double.parseDouble(actualSLTFValue)),
						format.format(roundOffExpectedSltf), "View or Print Full Quote Page",
						"The Calculated and Actual SLTF values are the same for TX Percentage 4.85% for Quote2, Actual SLTF values : "
								+ format.format(Double.parseDouble(actualSLTFValue)),
						false, false);
			} else {
				Assertions.verify(format.format(Double.parseDouble(actualSLTFValue)),
						format.format(roundOffExpectedSltf), "View or Print Full Quote Page",
						"The Calculated and Actual SLTF values are Not the same for TX Percentage 4.85% for Quote2 : "
								+ format.format(Double.parseDouble(actualSLTFValue)),
						false, false);
			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Calculating Broker fees percentage by adding PremiumAmount and multiplying by
			// Broker fees percentage 10% for Quote2
			expectedBrokerFees = Double.parseDouble(premiumAmount) * Double.parseDouble(brokerFeesPercentage);

			// Rounding Broker fees decimal value to 2 digits
			roundOffExpectedBrokerFees = new BigDecimal(expectedBrokerFees);
			roundOffExpectedBrokerFees = roundOffExpectedBrokerFees.setScale(2, RoundingMode.HALF_UP);

			// Verifying actual Broker fees and calculated Broker fees percentage 10% for
			// Quote2
			Assertions.addInfo("Scenario 08",
					"Verifying actual Broker fees and calculated Broker fees on View or Print Full Quote Page ");
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated Broker Fees for Quote2 : " + format.format(roundOffExpectedBrokerFees));

			if (format.format(Double.parseDouble(actualBrokerFees))
					.equalsIgnoreCase(format.format(roundOffExpectedBrokerFees))) {
				Assertions.verify(format.format(Double.parseDouble(actualBrokerFees)),
						format.format(roundOffExpectedBrokerFees), "View or Print Full Quote Page",
						"The Calculated and Actual Broker Fees values are the same for TX Percentage 10% for Quote2, Actual Broker Fees  : "
								+ format.format(Double.parseDouble(actualBrokerFees)),
						false, false);
			} else {
				Assertions.verify(format.format(Double.parseDouble(actualBrokerFees)),
						format.format(roundOffExpectedBrokerFees), "View or Print Full Quote Page",
						"The Calculated and Actual Broker Fees values are Not the same for TX Percentage 10% for Quote2 : "
								+ format.format(Double.parseDouble(actualBrokerFees)),
						false, false);
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Calculating Stamping Fees percentage by adding Premium+ICAT fees+OtherFees
			// and multiplying by Stamping Fees percentage 0.0004 for Quote2
			expectedStampingFees = (Double.parseDouble(premiumAmount) + Double.parseDouble(insurerInspectionFee)
					+ Double.parseDouble(actualBrokerFees)

					+ Double.parseDouble(insurerPolicyFee) + Double.parseDouble(surplusContributionValue))
					* (Double.parseDouble(stampingPercentage));

			// Rounding Stamping Fees decimal value to 2 digits
			roundOffExpectedStampingFees = new BigDecimal(expectedStampingFees);
			roundOffExpectedStampingFees = roundOffExpectedStampingFees.setScale(2, RoundingMode.HALF_UP);

			// Verify actual stamping fees and calculated stamping fees in VieworPrint quote
			// page 0.04% for
			// Quot2
			Assertions.addInfo("Scenario 09",
					"Verifying actual Stamping Fees and calculated Stamping fees on View or Print Full Quote Page ");
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated Stamping Fees for Quote2 : " + format.format(roundOffExpectedStampingFees));
			if (actualStampingFees.equalsIgnoreCase(format.format(roundOffExpectedStampingFees))) {
				Assertions.verify(actualStampingFees, format.format(roundOffExpectedStampingFees),
						"View or Print Full Quote Page",
						"The Calculated and Actual Stamping Fees are the same for TX Percentage 0.025% for Quote2, Actual Stamping Fees : "
								+ actualStampingFees,
						false, false);
			} else {
				Assertions.verify(actualStampingFees, format.format(roundOffExpectedStampingFees),
						"View or Print Full Quote Page",
						"The Calculated and Actual Stamping Fees are Not the same for TX Percentage 0.025%  for Quote2 : "
								+ actualStampingFees,
						false, false);
			}
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// View or Print Full Quote Page click on go back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View or Print Full Quote Page",
					"Clicked on Back Button successfully and Navigated to Account Overview Page");

			// click on Quote2
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber2, 2).waitTillVisibilityOfElement(60);
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber2, 2).click();
			Assertions.passTest("Account Overview Page",
					"Clicked on  Quote Number2 successfully and Account Overview Page loaded successfully");

			// Click on Edit Deductibles Limits
			Assertions.addInfo("Account Overview Page",
					"Creating another Quote by clicking on Edit Deductibles and limits");
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Deductibles and Limits successfully");

			// Entering Create quote page Details
			testData1 = data.get(dataValue3);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData1);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting the Quote number3
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber3 = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number3 :  " + quoteNumber3);

			// Click on View/Print Full quote link for Quote3.
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page",
					"Clicked on ViewPrintFull Quote Link successfully and ViewOrPrintFullQuotePage loaded successfully");

			// Getting Commercial property Premium Amount from View/Print Full quote link
			// Page for Quote3
			premiumAmount = viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath(5).getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page", "Premium Amount for Quote3 : " + "$" + premiumAmount);

			// Getting Insurer Inspection Fees from View/Print Full quote link Page
			insurerInspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Insurer Inspection Fees for Quote3 : " + "$" + insurerInspectionFee);

			// Getting Insurer Policy Fee from View/Print Full quote link Page
			insurerPolicyFee = viewOrPrintFullQuotePage.policyFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Insurer Policy Fees for Quote3 : " + "$" + insurerPolicyFee);

			// Getting surplus contribution value from View/Print Full quote link Page
			surplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Actual Surplus Contribution Value for Quote3 : " + "$" + surplusContributionValue);

			// Getting Broker Fee from View/Print Full quote link Page
			actualBrokerFees = viewOrPrintFullQuotePage.brokerFeeValue.getData().replaceAll("[^\\d-.]", "");

			// Getting surplusLinesTaxesValue from View/Print Full quote link Page
			actualSLTFValue = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replaceAll("[^\\d-.]", "");

			// Getting Stamping Fees from View/Print Full quote link Page
			actualStampingFees = viewOrPrintFullQuotePage.stampingFeeValue.getData();

			// Calculating sltf percentage by adding Premium+ICAT fees+OtherFees and
			// multiplying by
			// sltf percentage 0.0485...For Quote3
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(insurerInspectionFee)
					+ Double.parseDouble(actualBrokerFees) + Double.parseDouble(insurerPolicyFee)
					+ Double.parseDouble(surplusContributionValue)) * (Double.parseDouble(surplusLinesTaxesPercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf = new BigDecimal(expectedsltf);
			roundOffExpectedSltf = roundOffExpectedSltf.setScale(2, RoundingMode.HALF_UP);

			// Verifying actual SLTF and calculated SLTF in VieworPrint quote page SLTF
			// Percentage 4.85% for Quote3
			Assertions.addInfo("Scenario 10", "Verifying actual SLTF and calculated SLTF in VieworPrint quote page");
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated SLTF for Quote3 : " + format.format(roundOffExpectedSltf));
			if (format.format(Double.parseDouble(actualSLTFValue))
					.equalsIgnoreCase(format.format(roundOffExpectedSltf))) {
				Assertions.verify(format.format(Double.parseDouble(actualSLTFValue)),
						format.format(roundOffExpectedSltf), "View or Print Full Quote Page",
						"The Calculated and Actual SLTF values are the same for TX Percentage 4.85% for Quote3, Actual SLTF values :"
								+ format.format(Double.parseDouble(actualSLTFValue)),
						false, false);
			} else {
				Assertions.verify(format.format(Double.parseDouble(actualSLTFValue)),
						format.format(roundOffExpectedSltf), "View or Print Full Quote Page",
						"The Calculated and Actual SLTF values are Not the same for TX Percentage 4.85% for Quote3 : "
								+ format.format(Double.parseDouble(actualSLTFValue)),
						false, false);
			}
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Calculating Broker fees percentage by adding PremiumAmount and multiplying by
			// Broker fees percentage 10% for Quote3
			expectedBrokerFees = Double.parseDouble(premiumAmount) * Double.parseDouble(brokerFeesPercentage);

			// Rounding Broker fees decimal value to 2 digits
			roundOffExpectedBrokerFees = new BigDecimal(expectedBrokerFees);
			roundOffExpectedBrokerFees = roundOffExpectedBrokerFees.setScale(2, RoundingMode.HALF_UP);

			// Verifying actual Broker fees and calculated Broker fees with percentage 10%
			// for
			// Quote3
			Assertions.addInfo("Scenario 11",
					"Verifying actual Broker Fees and calculated Broker Fees in VieworPrint quote page");
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated Broker Fees for Quote3 : " + format.format(roundOffExpectedBrokerFees));
			if (format.format(Double.parseDouble(actualBrokerFees))
					.equalsIgnoreCase(format.format(roundOffExpectedBrokerFees))) {
				Assertions.verify(format.format(Double.parseDouble(actualBrokerFees)),
						format.format(roundOffExpectedBrokerFees), "View or Print Full Quote Page",
						"The Calculated and Actual Broker Fees values are the same for TX Percentage 10% for Quote3, Actual Broker Fees :"
								+ format.format(Double.parseDouble(actualBrokerFees)),
						false, false);
			} else {
				Assertions.verify(format.format(Double.parseDouble(actualBrokerFees)),
						format.format(roundOffExpectedBrokerFees), "View or Print Full Quote Page",
						"The Calculated and Actual Broker Fees values are Not the same for TX Percentage 10% for Quote3 :"
								+ format.format(Double.parseDouble(actualBrokerFees)),
						false, false);
			}
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Calculating Stamping Fees percentage by adding Premium+ICAT fees+OtherFees
			// and multiplying by
			// Stamping Fees percentage 0.0004..for Quote3
			expectedStampingFees = (Double.parseDouble(premiumAmount) + Double.parseDouble(insurerInspectionFee)
					+ Double.parseDouble(actualBrokerFees)

					+ Double.parseDouble(insurerPolicyFee) + Double.parseDouble(surplusContributionValue))
					* (Double.parseDouble(stampingPercentage));

			// Rounding Stamping Fees decimal value to 2 digits
			roundOffExpectedStampingFees = new BigDecimal(expectedStampingFees);
			roundOffExpectedStampingFees = roundOffExpectedStampingFees.setScale(2, RoundingMode.HALF_UP);

			// Verifying actual SLTF and calculated Stamping fees in VieworPrint quote page
			// 0.04%
			Assertions.addInfo("Scenario 12",
					"Verifying actual Stamping Fees and calculated Stamping Fees in VieworPrint quote page");
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated Stamping Fees for Quote3 : " + format.format(roundOffExpectedStampingFees));
			if (actualStampingFees.equalsIgnoreCase(format.format(roundOffExpectedStampingFees))) {
				Assertions.verify(actualStampingFees, format.format(roundOffExpectedStampingFees),
						"View or Print Full Quote Page",
						"The Calculated and Actual Stamping Fees are the same for TX Percentage 0.04% for Quote3, Actual Stamping Fees : "
								+ actualStampingFees,
						false, false);
			} else {
				Assertions.verify(actualStampingFees, format.format(roundOffExpectedStampingFees),
						"View or Print Full Quote Page",
						"The Calculated and Actual Stamping Fees are Not the same for TX Percentage 0.04%  for Quote3 : "
								+ actualStampingFees,
						false, false);
			}
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			// View or Print Full Quote Page click on go back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View or Print Full Quote Page",
					"Clicked on Back Button successfully and Navigated to Account Overview Page");

			// Adding IO-21180
			// Getting the premium,fees and sltf values from premium section in Account
			// Overview page
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// Get the sltf value
			String actualSltf = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");

			// fetching premium amount
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "").replace(".00", "");
			double d_premiumAmount = Double.parseDouble(premiumAmount);

			// fetching icat fees value
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "").replace(".00", "");
			double d_icatFees = Double.parseDouble(icatFees);

			// fetching icat fees value
			otherFees = accountOverviewPage.otherFees.getData().replaceAll("[^\\d-.]", "").replace(".00", "");
			double d_otherFees = Double.parseDouble(otherFees);

			// Fetching Surplus contribution value from account overview page
			surplusContributionValue = accountOverviewPage.surplusContributionValue.getData().replaceAll("[^\\d-.]",
					"");

			// fetching SLTF percentage
			String sltfPercentage = testData.get("SurplusLinesTaxesPercentage");
			double d_sltfPercentage = Double.parseDouble(sltfPercentage);

			// calculating sltf percentage by adding Premium+Fees multiplying by sltf
			// percentage 0.0725
			expectedsltf = (d_premiumAmount + d_icatFees + d_otherFees + Double.parseDouble(surplusContributionValue))
					* d_sltfPercentage;

			// fetching stamping fee percentage
			String StampingFeePercentage = testData.get("StampingPercentage");
			double d_StampingFeePercentage = Double.parseDouble(StampingFeePercentage);

			// Calculate stamping fee
			double stampingsltf = (d_premiumAmount + d_icatFees + d_otherFees
					+ Double.parseDouble(surplusContributionValue)) * d_StampingFeePercentage;

			// Final sltf
			double sltfTotal = expectedsltf + stampingsltf;

			// Verify the sltf value with calculated value
			Assertions.addInfo("Scenario 13", "Verifying actual SLTF and calculated SLTF in Account Overview page");
			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(actualSltf), 2)) - Precision.round(sltfTotal, 2),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes and Fees : " + "$" + df.format(sltfTotal));
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + accountOverviewPage.sltfValue.getData());
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber3);

			if (requestBindPage.submit.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber3);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

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

			// click on quote 3 link
			accountOverviewPage.quoteLink.formatDynamicPath(quoteNumber3).scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath(quoteNumber3).click();

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			}

			// getting the policy number
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			Assertions.passTest("Policy Summary Page", "Policy Number :" + policyNumber);

			// Logout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as Producer successfully");

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicyByProducer(policyNumber);

			// Verifying PB not present
			Assertions.addInfo("Scenario 14", "Verifying Endorse PB link is not present");
			Assertions.verify(
					policySummarypage.endorsePB.checkIfElementIsPresent()
							&& policySummarypage.endorsePB.checkIfElementIsDisplayed(),
					false, "Policy Summary Page", "Endorse PB link is not present for producer", false, false);
			Assertions.addInfo("Scenario 14", "Scenario 14 Ended");

			// Verify request cancellation link
			Assertions.addInfo("Scenario 15", "Verifying Request cancellation link is present");
			Assertions.verify(policySummarypage.requestCancellationLink.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Request Cancellation Link is displayed", false, false);
			Assertions.addInfo("Scenario 15", "Scenario 15 Ended");

			// Click on cancel link
			policySummarypage.requestCancellationLink.scrollToElement();
			policySummarypage.requestCancellationLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on RequestCancellation link successfully");

			// asserting request cancellation page
			Assertions.verify(requestCancellationPage.insuredName.checkIfElementIsDisplayed(), true,
					"Request Cancellation Page", "Request Cancellation Page loaded successfully", false, false);

			// asserting presence of new effective date field
			Assertions.addInfo("Scenario 16", "Asserting the presence of new effective date field");
			for (int i = 1; i <= 6; i++) {
				testData = data.get(i);
				requestCancellationPage.cancellationReasonArrow.scrollToElement();
				requestCancellationPage.cancellationReasonArrow.click();
				requestCancellationPage.cancellationReasonOption.formatDynamicPath(testData.get("CancellationReason"))
						.scrollToElement();
				requestCancellationPage.cancellationReasonOption.formatDynamicPath(testData.get("CancellationReason"))
						.click();
				if (i != 6) {
					Assertions.verify(
							requestCancellationPage.newEffectiveDate.checkIfElementIsPresent()
									&& requestCancellationPage.newEffectiveDate.checkIfElementIsDisplayed(),
							false, "Request Cancellation Page",
							"New Effective Date field is not available for cancellation reason "
									+ requestCancellationPage.cancellationReasonData.getData(),
							false, false);
				} else {
					Assertions.verify(
							requestCancellationPage.newEffectiveDate.checkIfElementIsPresent()
									&& requestCancellationPage.newEffectiveDate.checkIfElementIsDisplayed(),
							true, "Request Cancellation Page",
							"New Effective Date field is available for cancellation reason "
									+ requestCancellationPage.cancellationReasonData.getData(),
							false, false);
				}
			}
			Assertions.addInfo("Scenario 16", "Scenario 16 Ended");

			testData = data.get(dataValue1);

			// Enter cancellation request details
			requestCancellationPage.enterRequestCancellationDetails(testData);
			Assertions.passTest("Request Cancellation Page", "Details entered successfully");

			// Asserting the request message
			Assertions.addInfo("Scenario 17", "Assert the request cancellation message");
			Assertions.verify(requestCancellationPage.cancellationRequestMsg.checkIfElementIsDisplayed(), true,
					"Request Cancellation Page",
					requestCancellationPage.cancellationRequestMsg.getData() + " is displayed", false, false);
			Assertions.addInfo("Scenario 17", "Scenario 17 Ended");

			// Logout as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 106", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 106", "Executed Successfully");
			}
		}
	}
}
