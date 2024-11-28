/** Program Description:  Check if the SLTF is calculated omitting broker fees for AK State and SLTF is not included in Total Premium of Alt quotes
 *  Author			   : Sowndarya
 *  Date of Creation   : 09/28/2021
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math3.util.Precision;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.FWProperties;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC105 extends AbstractCommercialTest {

	public TC105() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID105.xls";
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
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		FWProperties property1 = new FWProperties("HealthDashBoardPage");

		// Initializing the variables
		String quoteNumber;
		String quoteNumber1;
		String quoteNumber2;
		String premiumAmount;
		String otherFees;
		double expectedsltf;
		double expectedsltf1;
		BigDecimal roundOffExpectedSltf;
		BigDecimal roundOffExpectedSltf1;
		String surplusLinesTaxesPercentage;
		String brokerFeesPercentage;
		String icatFees;
		String actualBrokerFees;
		String actualSLTFValue;
		double expectedBrokerFees;
		BigDecimal roundOffExpectedBrokerfees;
		String insurerInspectionFee;
		String insurerPolicyFee;
		String actualFilingFee;
		String sltfPercentage;
		String filingPercentage;
		double expectedFilingFee;
		BigDecimal roundOffExpectedFilingFee;
		double calculatedTotalPremiumAmount;
		String totalPremiumICATFees;
		double sltfComponentBrokerFee;
		double d_eqbPremiumValue = 0;
		BigDecimal roundOffsltfComponentBrokerFee;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		DecimalFormat df = new DecimalFormat("0.00");
		List<WebElement> eqbPremiumValue;
		String eqbPremValue1;
		Iterator<WebElement> itr;
		String carrierName = "Victor Insurance Exchange";
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// click on user preference link and enter the details
			Assertions.addInfo("Home Page", "Entering User Preference Details");
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			preferenceOptionsPage.addBrokerFees(testData);

			if (testData.get("BrokerFeePercentageOrDollar").equals("%")) {
				Assertions.passTest("Preference Options Page", "Broker Fee value : " + testData.get("BrokerFeeValue")
						+ testData.get("BrokerFeePercentageOrDollar"));
			} else {
				Assertions.passTest("Preference Options Page", "Broker Fee value : "
						+ testData.get("BrokerFeePercentageOrDollar") + testData.get("BrokerFeeValue"));
			}
			Assertions.passTest("Preference Options Page", "Details entered successfully");
			homePage.goToHomepage.click();

			// Creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

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

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting the quote number from Account Overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
			Assertions.addInfo("Account Overview Page",
					"Asserting the presence of Other Fees and SLTF on Account Overview Page");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsPresent(), true, "Account Overview Page",
					"Other Fees is present verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// Fetching Premium Amount from Account Overview page
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Premium Amount : " + "$" + premiumAmount);

			// Fetching ICAT fees value from Account Overview page
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual ICAT Fees : " + "$" + icatFees);

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as producer successfully");

			// Sign in as Rzimmer
			loginPage.refreshPage();
			loginPage.enterLoginDetails("swilcox", setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as Rzimmer");

			// click on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on admin link
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home Page", "Clicked on admin link successfully");

			// click on policy inspector page
			healthDashBoardPage.policyInspectorLink.scrollToElement();
			healthDashBoardPage.policyInspectorLink.click();
			Assertions.passTest("Health Dashboard Page", "Clicked on Policy Inspector link successfully");

			// enter the quote number in quote number field
			healthDashBoardPage.quoteNumberField.setData(quoteNumber);

			// click on find Policy
			healthDashBoardPage.findPolicyBtn.scrollToElement();
			healthDashBoardPage.findPolicyBtn.click();

			// Get EQB Premium value and store in a variable
			if (healthDashBoardPage.eqbPremium.checkIfElementIsPresent()
					&& healthDashBoardPage.eqbPremium.checkIfElementIsDisplayed()) {
				eqbPremiumValue = WebDriverManager.getCurrentDriver()
						.findElements(By.xpath(property1.getProperty("xp_EQBPremium")));
				itr = eqbPremiumValue.iterator();
				while (itr.hasNext()) {
					eqbPremValue1 = eqbPremiumValue.get(1).getText().toString();
					d_eqbPremiumValue = Double.parseDouble(eqbPremValue1);
					break;
				}
			}

			// click on back to tool list
			healthDashBoardPage.toolList.scrollToElement();
			healthDashBoardPage.toolList.click();

			// Sign out as swilcox
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Rzimmer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as USM");

			// click on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// search the quote
			homePage.searchQuote(quoteNumber);

			// click on quote specifics
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();

			// get VIE participation value
			accountOverviewPage.vieParicipation.waitTillVisibilityOfElement(60);
			int vieParticipationValuelength = accountOverviewPage.vieParicipation.getData().length();
			String vieParticipationValue = accountOverviewPage.vieParicipation.getData().substring(0,
					vieParticipationValuelength - 1);
			double d_vieParticipationValue = Double.parseDouble(vieParticipationValue) / 100;

			// get vie participation charge
			int vieContributionChargelength = accountOverviewPage.vieContributionCharge.getData().length();
			String vieContributionChargeValue = accountOverviewPage.vieContributionCharge.getData().substring(0,
					vieContributionChargelength - 1);
			double d_vieContributionChargeValue = Double.parseDouble(vieContributionChargeValue) / 100;

			// calculate surplus contribution value
			// 0.15* 0.1 * (transaction premium ? Utility Line Premium ? EQB Premium)
			double surplusContributionCalcValue = d_vieParticipationValue * d_vieContributionChargeValue
					* (Double.parseDouble(premiumAmount) - d_eqbPremiumValue);

			// verify the surplus contribution value with calculated value
			Assertions.verify(accountOverviewPage.surplusContributionValue.getData().replace("$", "").replace(",", ""),
					df.format(surplusContributionCalcValue), "Account Overview Page",
					"Actual and Calculated Surplus Contribution values are matching for state "
							+ testData.get("QuoteState") + " And the value is : "
							+ accountOverviewPage.surplusContributionValue.getData(),
					false, false);

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Sign in as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to application successfully as Producer");

			// click on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// search the quote
			homePage.searchQuoteByProducer(quoteNumber);

			// Getting surplusLinesTaxesPercentage
			surplusLinesTaxesPercentage = testData.get("SurplusLinesTaxesPercentage");

			// calculating sltf percentage by adding Premium+ICAT fees+surplus contribution
			// and multiplying by
			// sltf percentage 0.037
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ surplusContributionCalcValue) * (Double.parseDouble(surplusLinesTaxesPercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf = new BigDecimal(expectedsltf);
			roundOffExpectedSltf = roundOffExpectedSltf.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("Account Overview Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(roundOffExpectedSltf));

			// Actual SLTF value from Account Overview page
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			Assertions.passTest("Account Overview Page", "Actual SLTF Values:" + "$" + actualSLTFValue);

			// Verifying actual and calculated SLTF values are equal
			Assertions.addInfo("Account Overview Page",
					" Verifying actual and calculated SLTF values are equal on Account Overview Page");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualSLTFValue), 2))
					- Precision.round(expectedsltf, 2), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes and Fees : " + "$" + df.format(expectedsltf));
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + accountOverviewPage.sltfValue.getData());
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			// Getting BrokerFees Percentage from Datasheet
			brokerFeesPercentage = testData.get("BrokerFeesPercentage");

			// Fetching BrokerFees from Account Overview page
			actualBrokerFees = accountOverviewPage.otherFees.getData();
			Assertions.passTest("Account Overview Page", "Actual Broker Fees:" + actualBrokerFees);

			// calculating Broker fees percentage by adding Premium+Fees and multiplying by
			// Broker fees percentage 5%
			expectedBrokerFees = (Double.parseDouble(premiumAmount)) * (Double.parseDouble(brokerFeesPercentage));

			// Rounding Broker fees decimal value to 2 digits
			roundOffExpectedBrokerfees = new BigDecimal(expectedBrokerFees);
			roundOffExpectedBrokerfees = roundOffExpectedBrokerfees.setScale(2, RoundingMode.HALF_UP);

			Assertions.passTest("Account Overview Page",
					"Calculated Broker Fees : " + format.format(roundOffExpectedBrokerfees));

			// Verifying actual Broker fees and calculated Broker fees are same
			Assertions.addInfo("Account Overview Page",
					" Verifying actual and calculated Broker Fees are equal on Account Overview Page");
			if (actualBrokerFees.equalsIgnoreCase(format.format(roundOffExpectedBrokerfees))) {
				Assertions.verify(actualBrokerFees, format.format(roundOffExpectedBrokerfees), "Account Overview Page",
						"The Calculated and Actual Broker Fees values are the same for AK Percentage 5% : "
								+ actualBrokerFees,
						false, false);
			} else {
				Assertions.verify(actualBrokerFees, format.format(roundOffExpectedBrokerfees), "Account Overview Page",
						"The Calculated and Actual Broker Fees values are Not the same for AK Percentage 5% : "
								+ actualBrokerFees,
						false, false);
			}

			// Click on View/Print Full quote link.
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page",
					"Clicked on ViewPrintFull Quote Link successfully and ViewOrPrintFullQuotePage loaded successfully");

			// Getting Premium Amount from View/Print Full quote link Page
			premiumAmount = viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath(5).getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page", "Premium Amount : " + "$" + premiumAmount);

			// Getting Insurer Inspection Fees from View/Print Full quote link Page
			insurerInspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Insurer Inspection Fees : " + "$" + insurerInspectionFee);

			// Getting Insurer Policy Fee from View/Print Full quote link Page
			insurerPolicyFee = viewOrPrintFullQuotePage.policyFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page", "Insurer Policy Fees : " + "$" + insurerPolicyFee);

			// Getting Broker Fee from View/Print Full quote link Page
			actualBrokerFees = viewOrPrintFullQuotePage.brokerFeeValue.getData();
			Assertions.passTest("View or Print Full Quote Page", "Actual Broker Fees:" + actualBrokerFees);

			// calculating Broker fees percentage by adding Premium+Fees and multiplying by
			// Broker fees percentage 5%
			expectedBrokerFees = (Double.parseDouble(premiumAmount)) * (Double.parseDouble(brokerFeesPercentage));

			// Rounding Broker fees decimal value to 2 digits
			roundOffExpectedBrokerfees = new BigDecimal(expectedBrokerFees);
			roundOffExpectedBrokerfees = roundOffExpectedBrokerfees.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated Broker Fees : " + format.format(roundOffExpectedBrokerfees));

			// Verifying actual Broker fees and calculated Broker fees are the same
			Assertions.addInfo("View or Print Full Quote Page",
					"Verifying actual and calculated Broker Fees are equal on View or Print Full Quote Page");
			if (actualBrokerFees.equalsIgnoreCase(format.format(roundOffExpectedBrokerfees))) {
				Assertions.verify(actualBrokerFees, format.format(roundOffExpectedBrokerfees),
						"View or Print Full Quote Page",
						"The Calculated and Actual Broker Fees values are the same for AK Percentage 5% : "
								+ actualBrokerFees,
						false, false);
			} else {
				Assertions.verify(actualBrokerFees, format.format(roundOffExpectedBrokerfees),
						"View or Print Full Quote Page",
						"The Calculated and Actual Broker Fees values are Not the same for AK Percentage 5% : "
								+ actualBrokerFees,
						false, false);
			}

			// Getting surplusLinesTaxesValue from View/Print Full quote link Page
			actualSLTFValue = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData();
			Assertions.passTest("View or Print Full Quote Page", "Actual SLTF Value : " + actualSLTFValue);

			// verify the surplus contribution value with calculated value
			Assertions.verify(
					viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",", ""),
					df.format(surplusContributionCalcValue), "View or Print Full Quote Page",
					"Actual and Calculated Surplus Contribution values are matching for state "
							+ testData.get("QuoteState") + " And the value is : "
							+ viewOrPrintFullQuotePage.surplusContributionValue.getData(),
					false, false);

			// Getting SLTF percentage 2.7%
			sltfPercentage = testData.get("PrintViewPageSLTFPercentage");

			// calculating sltf percentage by adding Premium+ICAT fees and multiplying by
			// sltf percentage 0.027
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(insurerInspectionFee)
					+ Double.parseDouble(insurerPolicyFee) + surplusContributionCalcValue)
					* (Double.parseDouble(sltfPercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf = new BigDecimal(expectedsltf);
			roundOffExpectedSltf = roundOffExpectedSltf.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(roundOffExpectedSltf));

			// Verifying actual SLTF and calculated SLTF in VieworPrint quote page are the
			// same
			Assertions.addInfo("View or Print Full Quote Page",
					"Verifying actual and calculated SLTF values are equal on View or Print Full Quote Page");
			if (actualSLTFValue.equalsIgnoreCase(format.format(roundOffExpectedSltf).replace(",", ""))) {
				Assertions.verify(actualSLTFValue.replace(",", ""),
						format.format(roundOffExpectedSltf).replace(",", ""), "View or Print Full Quote Page",
						"The Calculated and Actual SLTF values are the same for AK Percentage 2.7% : "
								+ actualSLTFValue.replace(",", ""),
						false, false);
			} else {
				Assertions.verify(actualSLTFValue.replace(",", ""),
						format.format(roundOffExpectedSltf).replace(",", ""), "View or Print Full Quote Page",
						"The Calculated and Actual SLTF values are Not the same for AK Percentage 2.7% : "
								+ actualSLTFValue.replace(",", ""),
						false, false);
			}

			// Getting Filing Fees from View/Print Full quote Page
			actualFilingFee = viewOrPrintFullQuotePage.filingFee.getData().replace("$", "").replace(",", "");
			Assertions.passTest("View or Print Full Quote Page", "Actual Filing Fees :" + actualFilingFee);

			// Getting Filing Percentage 1%
			filingPercentage = testData.get("FilingPercentage");

			// calculating Filing Fees percentage by adding Premium+inspection fees+policy
			// fees and multiplying by filing percentage 0.01
			expectedFilingFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(insurerInspectionFee)
					+ Double.parseDouble(insurerPolicyFee) + surplusContributionCalcValue)
					* (Double.parseDouble(filingPercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedFilingFee = new BigDecimal(expectedFilingFee);
			roundOffExpectedFilingFee = roundOffExpectedFilingFee.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated Filing Fees : " + format.format(roundOffExpectedFilingFee));

			// Verifying actual Filing Fee and calculated Filing Fee are the same on
			// VieworPrint quote page
			Assertions.addInfo("View or Print Full Quote Page",
					"Verifying actual and calculated Filing Fee are equal on View or Print Full Quote Page");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualFilingFee), 2))
					- Precision.round(expectedFilingFee, 2), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Filing Fees : " + "$" + df.format(expectedFilingFee));
				Assertions.passTest("Account Overview Page", "Actual Filing Fees : " + "$" + actualFilingFee);
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated Filing fee is more than 0.05");
			}

			// Calculate Total premium on view print full quote page
			double totalPremiumVPFQ = (Double.parseDouble(premiumAmount)) + (Double.parseDouble(insurerInspectionFee))
					+ (Double.parseDouble(insurerPolicyFee)) + surplusContributionCalcValue + expectedBrokerFees
					+ expectedFilingFee + expectedsltf;

			// verify the Grand Total value with calculated value in view print full quote
			// page
			String totalPremium = viewOrPrintFullQuotePage.grandTotal.getData().replace("$", "").replace(",", "");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(totalPremium), 2))
					- Precision.round(totalPremiumVPFQ, 2), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Total Premium is : " + "$" + df.format(totalPremiumVPFQ));
				Assertions.passTest("Account Overview Page", "Actual Total Premium is : " + "$" + totalPremium);
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated total premium is more than 0.05");
			}

			// View or Print Full Quote Page click on go back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View or Print Full Quote Page",
					"Clicked on Back Button successfully and Navigated to Account Overview Page");

			// Calculating Total Premium Value by adding Premium+ICATfees+OtherFees+SLTF
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replaceAll("[^\\d-.]", "");
			otherFees = accountOverviewPage.otherFees.getData().replaceAll("[^\\d-.]", "");
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			calculatedTotalPremiumAmount = Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(otherFees) + Double.parseDouble(actualSLTFValue)
					+ surplusContributionCalcValue;

			// Comparing Total Premium & ICAT Fees from account overview page
			Assertions.addInfo("Account Overview Page",
					" Verifying actual and calculated Total Premium & ICAT Fees  on Account Overview Page");
			totalPremiumICATFees = accountOverviewPage.quoteOptionsTotalPremium.getData().replace("$", "").replace(",",
					"");
			double d_totalPremiumICATFees = Double.parseDouble(totalPremiumICATFees);
			if (Precision.round(Math
					.abs(Precision.round(d_totalPremiumICATFees, 2) - Precision.round(calculatedTotalPremiumAmount, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual TotalPremium Amount:" + accountOverviewPage.quoteOptionsTotalPremium.getData());
				Assertions.passTest("Account Overview Page",
						"Calculated Total Premium Plus Taxes and Fees :" + format.format(calculatedTotalPremiumAmount));
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual TotalPremium and calculated TotalPremium is more than 0.05");
			}

			// Clicked on Quote1 Alt Option1 account overview page
			accountOverviewPage.quoteOptions1TotalPremium.click();
			Assertions.passTest("Account OverView Page", "Clicked on Quote1 Alt Option1 successfully");
			quoteNumber1 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote1 Alt Option1 Quote Number1 :  " + quoteNumber1);
			accountOverviewPage.quoteLink.formatDynamicPath(quoteNumber).scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath(quoteNumber).click();

			// Adding the ticket IO-20988
			// click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");

			// Verify the presence of victor insurance carrier name
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page loaded successfully", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath("4").getData().contains(carrierName),
					true, "View/Print Full Quote Page",
					"Carrier Name " + viewOrPrintFullQuotePage.eqPremiumDetails.formatDynamicPath("4").getData()
							+ " displayed is verified",
					false, false);

			// View or Print Full Quote Page click on go back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View or Print Full Quote Page",
					"Clicked on Back Button successfully and Navigated to Account Overview Page");

			// Getting premium Amount from account overview page for Quote1 AltOption1
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Quote1 Alt Option1 Premium Amount : " + "$" + premiumAmount);

			// Geeting ICAT fee from account overview page for Quote1 AltOption1
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Quote1 Alt Option1 ICAT Fees : " + "$" + icatFees);

			// Getting Other fees from account overview page for Quote1 AltOption1
			otherFees = accountOverviewPage.otherFees.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Quote1 Alt Option1 Other Fees: " + otherFees);

			// Getting SLTF value from account overview page for Quote1 AltOption1
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			Assertions.passTest("Account Overview Page", "Actual SLTF for Quote1 Alt Option1:" + "$" + actualSLTFValue);

			// Getting surplus contribution value
			String surplusContributionVlaueAlt1 = accountOverviewPage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");

			// Calculating sltf values by adding Premium+ICAT fees and multiplying by sltf
			// percentage 0.037
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(surplusContributionVlaueAlt1))
					* (Double.parseDouble(surplusLinesTaxesPercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf = new BigDecimal(expectedsltf);

			// roundOffExpectedSltf = roundOffExpectedSltf.setScale(2,
			// RoundingMode.HALF_UP);
			Assertions.passTest("Account Overview Page",
					"SLTF Without Broker Fees for Quote1 Alt Option1 = : " + "(" + premiumAmount + "+" + icatFees + ")"
							+ "*" + surplusLinesTaxesPercentage + " = " + df.format(roundOffExpectedSltf));

			// Calculating sltf percentage by adding Premium+ICAT fees + Broker Fees and
			// multiplying by
			// sltf percentage 0.037
			expectedsltf1 = (Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(otherFees)) * (Double.parseDouble(surplusLinesTaxesPercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf1 = new BigDecimal(expectedsltf1);

			// roundOffExpectedSltf1 = roundOffExpectedSltf1.setScale(2,
			// RoundingMode.HALF_UP);
			Assertions.passTest("Account Overview Page",
					"SLTF With Broker Fees for Quote1 Alt Option1 = : " + "(" + premiumAmount + "+" + icatFees + "+"
							+ otherFees + ")" + "*" + surplusLinesTaxesPercentage + " = "
							+ df.format(roundOffExpectedSltf1));

			// Calculating SLTF component of broker fee (Otherfees*SLTF %0.037)
			sltfComponentBrokerFee = (Double.parseDouble(otherFees))
					* (Double.parseDouble(surplusLinesTaxesPercentage));

			// Rounding SLTF component of Broker fees
			roundOffsltfComponentBrokerFee = new BigDecimal(sltfComponentBrokerFee);
			roundOffsltfComponentBrokerFee = roundOffsltfComponentBrokerFee.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("Account Overview Page", "SLTF component of broker fees for Quote1 Alt Option1 :"
					+ format.format(roundOffsltfComponentBrokerFee));

			Assertions.passTest("Account Overview Page",
					"SLTF with broker fee for AK state is:" + format.format(roundOffExpectedSltf1) + "="
							+ format.format(roundOffExpectedSltf) + "+"
							+ format.format(roundOffsltfComponentBrokerFee));

			// Verifying actual SLTF and calculated SLTF in Quote1 Alt option1
			Assertions.addInfo("Account Overviw Page",
					"Verifying actual SLTF and calculated SLTF in Quote1 Alt option1");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualSLTFValue), 2))
					- Precision.round(expectedsltf, 2), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes and Fees : " + "$" + df.format(expectedsltf));
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + actualSLTFValue);
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			// Clicked on original quote
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber, 1).waitTillVisibilityOfElement(60);
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber, 1).click();
			Assertions.passTest("Account Overview Page",
					"Clicked on Orginal Quote Number successfully and Account Overview Page loaded successfully");

			// Clicked on Alt quote option 2.
			accountOverviewPage.quoteOptions2TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions2TotalPremium.waitTillVisibilityOfElement(60);
			accountOverviewPage.quoteOptions2TotalPremium.click();
			Assertions.passTest("Account Overview Page",
					"Clicked on Quote1 Alt Option2 successfully and Account Overview Page loaded successfully");

			// Geting Quote Number 2
			quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote1 Alt Option2 Quote Number2 :  " + quoteNumber2);

			// Getting premium Amount from alt quote option2
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Quote1 Alt Option2 Premium Amount : " + "$" + premiumAmount);

			// Getting ICAT fee from alt quote option2
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Quote1 Alt Option2 ICAT Fees : " + "$" + icatFees);

			// Getting Other fees from account overview page for Quote1
			otherFees = accountOverviewPage.otherFees.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Quote1 Alt Option2 Other Fees: " + otherFees);

			// Getting SLTF value from alt quote option2
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "");
			Assertions.passTest("Account Overview Page",
					"Actual SLTF Values for Quote1 Alt Option2 :" + "$" + actualSLTFValue);

			// Getting surplus contribution value
			String surplusContributionVlaueAlt2 = accountOverviewPage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");

			// calculating sltf Values by adding Premium+ICAT fees and multiplying by sltf
			// percentage 0.037
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(surplusContributionVlaueAlt2))
					* (Double.parseDouble(surplusLinesTaxesPercentage));
			String s_expectedsltf = Double.toString(expectedsltf).replace("$", "");

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf = new BigDecimal(expectedsltf);
			roundOffExpectedSltf = roundOffExpectedSltf.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("Account Overview Page",
					"SLTF Without Broker Fees for Quote1 Alt Option2 = : " + "(" + premiumAmount + "+" + icatFees + ")"
							+ "*" + surplusLinesTaxesPercentage + " = " + format.format(roundOffExpectedSltf));

			// calculating sltf percentage by adding Premium+ICAT fees + Broker Fees and
			// multiplying by
			// sltf percentage 0.037
			expectedsltf1 = (Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(otherFees)) * (Double.parseDouble(surplusLinesTaxesPercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf1 = new BigDecimal(expectedsltf1);
			roundOffExpectedSltf1 = roundOffExpectedSltf1.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("Account Overview Page",
					"SLTF With Broker Fees for Quote1 Alt Option2 = : " + "(" + premiumAmount + "+" + icatFees + "+"
							+ otherFees + ")" + "*" + surplusLinesTaxesPercentage + " = "
							+ format.format(roundOffExpectedSltf1));

			// Calculating SLTF component of broker fee (Otherfees*SLTF %0.037)
			sltfComponentBrokerFee = (Double.parseDouble(otherFees))
					* (Double.parseDouble(surplusLinesTaxesPercentage));

			// Rounding SLTF component of Broker fees
			roundOffsltfComponentBrokerFee = new BigDecimal(sltfComponentBrokerFee);
			roundOffsltfComponentBrokerFee = roundOffsltfComponentBrokerFee.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("Account Overview Page", "SLTF component of broker fee for Quote1 Alt Option2 :"
					+ format.format(roundOffsltfComponentBrokerFee));

			Assertions.passTest("Account Overview Page",
					"SLTF with broker fee for AK state is:" + format.format(roundOffExpectedSltf1) + "="
							+ format.format(roundOffExpectedSltf) + "+"
							+ format.format(roundOffsltfComponentBrokerFee));

			// Verifying actual SLTF and calculated SLTF in alt quote option2
			Assertions.addInfo("Account Overviw Page",
					"Verifying actual SLTF and calculated SLTF in Quote1 Alt option2");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualSLTFValue), 2)
					- Precision.round(Double.parseDouble(s_expectedsltf), 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Actual SLTF is : " + "$" + actualSLTFValue);
				Assertions.passTest("Account Overview Page", "Calculated SLTF is : " + "$" + df.format(expectedsltf));
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			// Click on Home Page Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 105", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 105", "Executed Successfully");
			}
		}
	}
}
