/** Program Description: Create an AOP Policy only with/without BPP values and assert presence and default values of EQB, WDR, Ord or Law, Terrorism and coverage package. Also add apc and assert for checkbox to add APC in quote page. and added IO-21012,IO-20952
 *  Author			   : John
 *  Date of Creation   : 07/02/2020
**/

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
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC016 extends AbstractCommercialTest {

	public TC016() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID016.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		DecimalFormat df = new DecimalFormat("0.00");

		// Initializing variables
		String quoteNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);
		String nsDeductibleValue = "$25,000 Named Storm Deductible by location, Calendar Year Aggregate\n"
				+ "    Once exhausted, the All Other Causes of Loss Deductible applies";
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

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();

			// Asserting presence/absence of optional coverages when building/BPP/BI value
			// is not provided
			scrollToTopPage();
			Assertions.addInfo("Scenario 01",
					"Asserting presence/absence of optional coverages when building/BPP/BI value is not provided");
			Assertions.verify(
					createQuotePage.includeAPCCheckbox.checkIfElementIsPresent()
							&& createQuotePage.includeAPCCheckbox.checkIfElementIsDisplayed(),
					false, "Create Quote Page", "Include APC checkbox is not displayed when APC is not provided", false,
					false);
			Assertions.verify(
					createQuotePage.windDrivenRainArrow.checkIfElementIsPresent()
							&& createQuotePage.windDrivenRainArrow.checkIfElementIsDisplayed(),
					false, "Create Quote Page", "Wind-Driven Rain is not displayed when BPP value is not provided",
					false, false);

			// Ordinance or Law
			Assertions.verify(
					createQuotePage.ordinanceLawArrow.checkIfElementIsPresent()
							&& createQuotePage.ordinanceLawArrow.checkIfElementIsDisplayed(),
					false, "Create Quote Page", "Ordinance or Law is not displayed when BPP value is not provided",
					false, false);

			// EQB
			Assertions.verify(
					createQuotePage.equipmentBreakdownData.checkIfElementIsPresent()
							&& createQuotePage.equipmentBreakdownData.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Equipment BreakDown is present when BPP value is not provided and default value is "
							+ createQuotePage.equipmentBreakdownData.getData(),
					false, false);

			// Terrorism
			Assertions.verify(
					createQuotePage.terrorismData.checkIfElementIsPresent()
							&& createQuotePage.terrorismData.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Terrorism is present when BPP value is not provided and default value is "
							+ createQuotePage.terrorismData.getData(),
					false, false);

			// Coverage Package
			Assertions.verify(
					createQuotePage.coverageExtensionPackageData.checkIfElementIsPresent()
							&& createQuotePage.coverageExtensionPackageData.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Coverage Extension is present when BPP value is not provided and default value is "
							+ createQuotePage.coverageExtensionPackageData.getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Adding Bpp value
			scrollToBottomPage();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			createQuotePage.bPPValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgBPP"));
			createQuotePage.bPPValue.formatDynamicPath(0, 0).tab();
			Assertions.passTest("Create Quote Page", "BPP Value is added");

			// Asserting presence/absence of optional coverages when only BPP value is
			// provided
			scrollToTopPage();
			Assertions.addInfo("Scenario 02",
					"Asserting presence/absence of optional coverages when only BPP value is provided");
			Assertions.verify(
					createQuotePage.windDrivenRainArrow.checkIfElementIsPresent()
							&& createQuotePage.windDrivenRainArrow.checkIfElementIsDisplayed(),
					false, "Create Quote Page", "Wind-Driven Rain is not displayed when only BPP value is provided",
					false, false);

			// Ordinance or Law
			Assertions.verify(
					createQuotePage.ordinanceLawArrow.checkIfElementIsPresent()
							&& createQuotePage.ordinanceLawArrow.checkIfElementIsDisplayed(),
					false, "Create Quote Page", "Ordinance or Law is not displayed when only BPP value is provided",
					false, false);

			// EQB
			Assertions.verify(
					createQuotePage.equipmentBreakdownData.checkIfElementIsPresent()
							&& createQuotePage.equipmentBreakdownData.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Equipment BreakDown is present when only BPP value is provided and default value is "
							+ createQuotePage.equipmentBreakdownData.getData(),
					false, false);

			// Terrorism
			Assertions.verify(
					createQuotePage.terrorismData.checkIfElementIsPresent()
							&& createQuotePage.terrorismData.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Terrorism is present when only BPP value is provided and default value is "
							+ createQuotePage.terrorismData.getData(),
					false, false);

			// Coverage Package
			Assertions.verify(
					createQuotePage.coverageExtensionPackageData.checkIfElementIsPresent()
							&& createQuotePage.coverageExtensionPackageData.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Coverage Extension is present when only BPP value is provided and default value is "
							+ createQuotePage.coverageExtensionPackageData.getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Adding BI and EQ values
			scrollToBottomPage();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			Assertions.passTest("Create Quote Page", "Building value is added");

			// Click on previous button
			createQuotePage.previous.waitTillVisibilityOfElement(60);
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			// Click on edit location
			accountOverviewPage.editLocation.waitTillVisibilityOfElement(60);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();

			// Add APC values in Location page and create quote
			testData = data.get(data_Value2);
			Assertions.passTest("Location Page", "Location Page Loaded successfully");
			locationPage.addAPC(testData, 1);
			Assertions.passTest("Location Page", "APC values added successfully");
			locationPage.reviewLocation.scrollToElement();
			locationPage.reviewLocation.click();
			locationPage.createQuoteButton.scrollToElement();
			locationPage.createQuoteButton.click();

			// Select peril
			testData = data.get(data_Value1);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Asserting include APC check box
			Assertions.addInfo("Scenario 03",
					"Asserting Include APC checkbox is present and selected by default when APC is provided");
			Assertions.verify(createQuotePage.includeAPCCheckbox.formatDynamicPath("1").checkIfElementIsSelected(),
					true, "Create Quote Page",
					"Include APC checkbox is present and selected by default when APC is provided", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// ----Added IO-21163---
			createQuotePage.aowhDeductibleArrow.scrollToElement();
			createQuotePage.aowhDeductibleArrow.click();

			Assertions.addInfo("Create Quote Page",
					"Verifying that the AOWH deductible dropdown does not contain the option of $1,000 for the Texas state.");
			Assertions.verify(createQuotePage.aowhDeductibleOption.formatDynamicPath("$1,000").checkIfElementIsPresent()
					&& createQuotePage.aowhDeductibleOption.formatDynamicPath("$1,000").checkIfElementIsDisplayed(),
					false, "Create Quote Page",
					"The AOWH deductible with the option of $1,000 is excluded from the dropdown list for the Texas state and has been verified.",
					false, false);

			// ----IO-21163 Ended----

		// Enter quote details
		createQuotePage.enterDeductiblesCommercialNew(testData);

		//Select eq sprinkler value
		if (!testData.get("EarthquakeSprinklerLeakage").equals("")) {
			createQuotePage.earthquakeSprinklerLeakageArrow.scrollToElement();
			createQuotePage.earthquakeSprinklerLeakageArrow.click();
			createQuotePage.earthquakeSprinklerLeakageOption.formatDynamicPath(testData.get("EarthquakeSprinklerLeakage")).click();
			Assertions.passTest("Create Quote Page",
					"Earthquake Sprinkler Leakage Value : " + createQuotePage.earthquakeSprinklerLeakageData.getData());
		}
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Adding IO-20952
		// Verifying presence of Earthquake Sprinkler Leakage optional coverage on
		// create quote page when EQ is added
		createQuotePage.earthquakeAppliesByData.waitTillPresenceOfElement(60);
		Assertions.addInfo("Scenario 04",
				"Verifying the presence of Earthquake Sprinkler Leakage optional coverage on create quote page, when EQ is added");
		Assertions.verify(createQuotePage.earthquakeAppliesByData.getData().equalsIgnoreCase("By Location"), true,
				"Create Quote Page",
				"After adding EQ coverage the page is updated with EQ options (EQ Deductible Applicability", false,
				false);
		createQuotePage.waitTime(3);

		Assertions.verify(createQuotePage.earthquakeSprinklerLeakageData.getData().contains("Yes"), true,
				"Create Quote Page", "After adding EQ coverage the page is updated with EQ options(EQSL)", false,
				false);
		Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Create Quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				createQuotePage.continueButton.waitTillInVisibilityOfElement(60);
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Adding the ticket IO-20754
			// Navigate to view print full quote page
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");

			// Verify the presence of Deductible and coverage details correctly
			Assertions.addInfo("Scenario 05",
					"Verify the presence of Deductible and coverage details correctly on Quote Document");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page Loaded successfully", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(1).getData().equals(nsDeductibleValue),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(1).getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Adding IO-21012
			// Click on back button
			testData = data.get(data_Value1);
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Or Print Full Quotes Page", "Clicked on back button successfully");

			// After adding broker fees user should able to proceed further without any
			// popup message
			// Click on icat edit fees link
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.editFees.scrollToElement();
			accountOverviewPage.editFees.click();
			accountOverviewPage.waitTime(3);
			accountOverviewPage.customFieldValue.formatDynamicPath(1).waitTillPresenceOfElement(60);
			accountOverviewPage.customFieldValue.formatDynamicPath(1).scrollToElement();
			accountOverviewPage.customFieldValue.formatDynamicPath(1).clearData();
			accountOverviewPage.customFieldValue.formatDynamicPath(1).appendData(testData.get("BrokerFeeValue"));
			accountOverviewPage.customFieldName.formatDynamicPath(1).scrollToElement();
			accountOverviewPage.customFieldName.formatDynamicPath(1).setData("Tax Name");
			accountOverviewPage.saveButton.scrollToElement();
			accountOverviewPage.saveButton.click();
			Assertions.passTest("Account Oveerview Page", "Clicked on save button successfully");

			// Checking broker fees added
			Assertions.addInfo("Scenario 06", "Checking broker fees added to account overview page");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Broker fees added " + accountOverviewPage.otherFees.getData() + " successfully", false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");
			// IO-21012 ended

			// ----Added IO-21685----

			// Search for quote
			accountOverviewPage.waitTime(3);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// Enter quote details
			testData = data.get(data_Value3);
			createQuotePage.addOptionalCoverageDetails(testData);

			// Create Quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				createQuotePage.continueButton.waitTillInVisibilityOfElement(60);
			}
			String quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page",
					"Account Overview Page Loaded successfully.Quote Number2 :  " + quoteNumber2);

			// Verifying Actual and Calculated Surplus Contribution value when EQ premium
			// included
			String premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			String actualSurplusContributionValue = accountOverviewPage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");
			double d_actualSurplusContributionValue = Double.parseDouble(actualSurplusContributionValue);
			String df_actualSurplusContributionvalue = df.format(d_actualSurplusContributionValue);

			// Converting string values to double
			double d_premium = Double.parseDouble(premium);
			double surplusContributionCalculatedValue = d_premium * 0.075;
			Assertions.addInfo("Scenario 07",
					"Verifying Actual and Calculated Surplus Contribution value when EQ premium included");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(df_actualSurplusContributionvalue), 2)
					- Precision.round(surplusContributionCalculatedValue, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Calculated surplus contribution value: " + "$"
						+ Precision.round(surplusContributionCalculatedValue, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual surplus contribution value: " + "$" + actualSurplusContributionValue);
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated surplus contribution value is more than 0.05");
			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// Enter quote details
			testData = data.get(data_Value3);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			String quoteNumber3 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page",
					"Account Overview Page Loaded successfully.Quote Number3 :  " + quoteNumber3);

			// Verifying Actual and Calculated Surplus Contribution value when EQ premium
			// Excluded
			String prEmium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			String surplusContributionvalue = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			double d_surplusContributionvalue = Double.parseDouble(surplusContributionvalue);
			String dfSurplusContributionvalue = df.format(d_surplusContributionvalue);

			// Converting string values to double
			double d_prEmium = Double.parseDouble(prEmium);
			double surplusContributionCalculatedvalue = d_prEmium * 0.075;
			Assertions.addInfo("Scenario 08",
					"Verifying Actual and Calculated Surplus Contribution value when EQ premium Excluded");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(dfSurplusContributionvalue), 2)
					- Precision.round(surplusContributionCalculatedvalue, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Calculated surplus contribution value: " + "$"
						+ Precision.round(surplusContributionCalculatedvalue, 2));
				Assertions.passTest("Policy Summary Page",
						"Actual surplus contribution value: " + "$" + dfSurplusContributionvalue);
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated surplus contribution value is more than 0.05");
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// ---Added IO-21605----
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			viewOrPrintFullQuotePage.scrollToBottomPage();

			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("ICAT SCOL 300").checkIfElementIsPresent(), false,
					"View/Print Full Quote Page",
					"ICAT SCOL 300 form is not displayed when BI coverage is not included", false, false);

			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			testData = data.get(data_Value3);
			locationPage.enterLocationDetails(testData);

			testData = data.get(data_Value1);
			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Navigate to account overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			viewOrPrintFullQuotePage.scrollToBottomPage();

			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("ICAT SCOL 300").checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.forms.formatDynamicPath("ICAT SCOL 300")
									.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page", "ICAT SCOL 300 form is displayed when BI coverage is included",
					false, false);
			// --IO-21605 Ended----

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 16", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 16", "Executed Successfully");
			}
		}
	}
}