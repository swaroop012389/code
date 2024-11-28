/** Program Description: Create an AOP quote and click on Edit Ded and Limits and assert the retention of values on the quote creation page. and IO-22098 and IO-22112
 *  Author			   : John
 *  Date of Creation   : 11/25/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC023 extends AbstractCommercialTest {

	public TC023() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID023.xls";
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
		PriorLossesPage priorLossPage = new PriorLossesPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();

		// Initializing the variables
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		Map<String, String> testData = data.get(dataValue1);
		String quoteNumber;
		int quotelength;
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
			locationPage = eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
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
			createQuotePage.namedHurricaneRadio.scrollToElement();
			createQuotePage.namedHurricaneRadio.click();
			Assertions.passTest("Create Quote Page", "Deductible Type is " + testData.get("DeductibleType"));
			createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
			createQuotePage.namedHurricaneDeductibleArrow.click();
			createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("DeductibleValue"))
					.scrollToElement();
			createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("DeductibleValue")).click();
			Assertions.passTest("Create Quote Page", "NH Deductible value is " + testData.get("DeductibleValue"));
			createQuotePage.namedHurricaneDeductibleAppliesByArrow.scrollToElement();
			createQuotePage.namedHurricaneDeductibleAppliesByArrow.click();
			createQuotePage.namedHurricaneDeductibleAppliesByOption
					.formatDynamicPath(testData.get("DeductibleApplicability")).scrollToElement();
			createQuotePage.namedHurricaneDeductibleAppliesByOption
					.formatDynamicPath(testData.get("DeductibleApplicability")).click();
			Assertions.passTest("Create Quote Page",
					"NH Deductible Applicability is " + testData.get("DeductibleApplicability"));
			createQuotePage.namedHurricaneDeductibleApplicabilityArrow.scrollToElement();
			createQuotePage.namedHurricaneDeductibleApplicabilityArrow.click();
			createQuotePage.namedHurricaneDeductibleApplicabilityOption
					.formatDynamicPath(testData.get("DeductibleOccurence")).scrollToElement();
			createQuotePage.namedHurricaneDeductibleApplicabilityOption
					.formatDynamicPath(testData.get("DeductibleOccurence")).click();
			Assertions.passTest("Create Quote Page",
					"NH Deductible Occurance is " + testData.get("DeductibleOccurence"));
			createQuotePage.earthquakeDeductibleArrow.scrollToElement();
			createQuotePage.earthquakeDeductibleArrow.click();
			createQuotePage.earthquakeDeductibleOption.formatDynamicPath(testData.get("EQDeductibleValue"))
					.scrollToElement();
			createQuotePage.earthquakeDeductibleOption.formatDynamicPath(testData.get("EQDeductibleValue")).click();
			Assertions.passTest("Create Quote Page",
					"EQ Deductible value selected is " + testData.get("EQDeductibleValue"));
			Assertions.passTest("Create Quote Page",
					"EQ Deductible Applicability value is " + testData.get("EQDeductibleApplicability"));
			createQuotePage.aowhDeductibleArrow.scrollToElement();
			createQuotePage.aowhDeductibleArrow.click();
			createQuotePage.aowhDeductibleOption.formatDynamicPath(testData.get("AOWHDeductibleValue"))
					.scrollToElement();
			createQuotePage.aowhDeductibleOption.formatDynamicPath(testData.get("AOWHDeductibleValue")).click();
			Assertions.passTest("Create Quote Page", "AOWH Deductible value is " + testData.get("AOWHDeductibleValue"));
			createQuotePage.aowhDeductibleAppliesByArrow.scrollToElement();
			createQuotePage.aowhDeductibleAppliesByArrow.click();
			createQuotePage.aowhDeductibleAppliesByOption.formatDynamicPath(testData.get("AOWHDeductibleApplicability"))
					.scrollToElement();
			createQuotePage.aowhDeductibleAppliesByOption.formatDynamicPath(testData.get("AOWHDeductibleApplicability"))
					.click();
			Assertions.passTest("Create Quote Page",
					"AOWH Deductible Applicability is " + testData.get("AOWHDeductibleApplicability"));
			createQuotePage.aoclDeductibleArrow.scrollToElement();
			createQuotePage.aoclDeductibleArrow.click();
			createQuotePage.aoclDeductibleOption.formatDynamicPath(testData.get("AOCLDeductibleValue"))
					.scrollToElement();
			createQuotePage.aoclDeductibleOption.formatDynamicPath(testData.get("AOCLDeductibleValue")).click();
			Assertions.passTest("Create Quote Page", "AOCL Deductible value is " + testData.get("AOCLDeductibleValue"));
			createQuotePage.addAdditionalCoveragesCommercial(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// click on override
			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				createQuotePage.continueButton.waitTillInVisibilityOfElement(60);
			}

			// Added IO-22098
			// Checking user is able to create quote,when year built = year roof last
			// replaced
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 01",
					"Checking user is able to create quote,when year built = year roof last replaced");
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account Overview Page",
					"User is able to create quote,when year built = year roof last replaced,the Quote Number :  "
							+ quoteNumber);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			// IO-22098 Ended

			// Clicking on Edit deductible values
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Deductibles and limits");

			// Asserting if values from original quote are retained when edit deductible is
			// clicked
			Assertions.addInfo("Scenario 02",
					"Asserting values from original quote are retained when edit deductible is clicked");
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded successfully", false, false);
			Assertions.passTest("Create Quote Page",
					"Deductible Type Original value is " + testData.get("DeductibleType"));
			Assertions.verify(createQuotePage.namedHurricaneRadio.checkIfElementIsSelected(), true, "Create Quote Page",
					"Deductible type Retained is Named Hurricane", false, false);
			Assertions.passTest("Create Quote Page",
					"NH Deductible Original value is " + testData.get("DeductibleValue"));
			Assertions.verify(createQuotePage.namedHurricaneData.getData().contains(testData.get("DeductibleValue")),
					true, "Create Quote Page",
					"NH Deductible Retained is " + createQuotePage.namedHurricaneData.getData(), false, false);
			Assertions.passTest("Create Quote Page",
					"NH Deductible Applicability Original value is " + testData.get("DeductibleApplicability"));
			Assertions.verify(
					createQuotePage.namedStormHurricaneAppliesByData.getData()
							.contains(testData.get("DeductibleApplicability")),
					true, "Create Quote Page", "NH Deductible Applicability Retained is "
							+ createQuotePage.namedStormHurricaneAppliesByData.getData(),
					false, false);
			Assertions.passTest("Create Quote Page",
					"NH Deductible Occurance Original value is " + testData.get("DeductibleOccurence"));
			Assertions.verify(
					createQuotePage.namedStormHurricaneApplicabilityData.getData()
							.contains(testData.get("DeductibleOccurence")),
					true, "Create Quote Page", "NH Deductible Occurance Retained is "
							+ createQuotePage.namedStormHurricaneApplicabilityData.getData(),
					false, false);

			Assertions.passTest("Create Quote Page",
					"AOWH Deductible Original value is " + testData.get("AOWHDeductibleValue"));
			Assertions.verify(
					createQuotePage.aowhDeductibleData.getData().contains(testData.get("AOWHDeductibleValue")), true,
					"Create Quote Page", "AOWH Deductible Retained is " + createQuotePage.aowhDeductibleData.getData(),
					false, false);

			Assertions.passTest("Create Quote Page",
					"AOWH Deductible Applicability Original value is " + testData.get("AOWHDeductibleApplicability"));
			Assertions.verify(
					createQuotePage.aowhDeductibleAppliesByData.getData()
							.contains(testData.get("AOWHDeductibleApplicability")),
					true, "Create Quote Page", "AOWH Deductible Applicability Retained is "
							+ createQuotePage.aowhDeductibleAppliesByData.getData(),
					false, false);
			Assertions.passTest("Create Quote Page",
					"AOCL Deductible Original value is " + testData.get("AOCLDeductibleValue"));
			Assertions.verify(createQuotePage.aoclDeductibleData.getData().equals(testData.get("AOCLDeductibleValue")),
					true, "Create Quote Page",
					"AOCL Deductible Retained is " + createQuotePage.aoclDeductibleData.getData(), false, false);
			Assertions.passTest("Create Quote Page",
					"EQ Deductible Original value is " + testData.get("EQDeductibleValue"));
			Assertions.verify(createQuotePage.earthquakeData.getData().contains(testData.get("EQDeductibleValue")),
					true, "Create Quote Page", "EQ Deductible Retained is " + createQuotePage.earthquakeData.getData(),
					false, false);
			Assertions.passTest("Create Quote Page",
					"EQ Deductible Applicability Original value is " + testData.get("EQDeductibleApplicability"));
			Assertions.verify(
					createQuotePage.earthquakeAppliesByData.getData()
							.contains(testData.get("EQDeductibleApplicability")),
					true, "Create Quote Page",
					"EQ Deductible Applicability Retained is " + createQuotePage.earthquakeAppliesByData.getData(),
					false, false);
			Assertions.passTest("Create Quote Page",
					"Earthquake Sprinkler Leakage Original value is " + testData.get("EarthquakeSprinklerLeakage"));
			Assertions.verify(
					createQuotePage.earthquakeSprinklerLeakageData.getData()
							.contains(testData.get("EarthquakeSprinklerLeakage")),
					true, "Create Quote Page", "Earthquake Sprinkler Leakage Retained is "
							+ createQuotePage.earthquakeSprinklerLeakageData.getData(),
					false, false);
			Assertions.passTest("Create Quote Page",
					"Equipment breakdown Original value is " + testData.get("EquipmentBreakdown"));
			Assertions.verify(createQuotePage.equipmentBreakdownData.getData().contains("None"), true,
					"Create Quote Page",
					"Equipment Breakdown Retained is " + createQuotePage.equipmentBreakdownData.getData(), false,
					false);
			Assertions.passTest("Create Quote Page",
					"Ordinance Or Law Original value is " + testData.get("OrdinanceOrLaw"));
			Assertions.verify(createQuotePage.ordinanceLawData.getData().contains(testData.get("OrdinanceOrLaw")), true,
					"Create Quote Page", "Ordinance or Law Retained is " + createQuotePage.ordinanceLawData.getData(),
					false, false);
			Assertions.passTest("Create Quote Page",
					"Wind Driven Rain Original value is " + testData.get("WindDrivenRain"));
			Assertions.verify(createQuotePage.windDrivenRainData.getData().contains(testData.get("WindDrivenRain")),
					true, "Create Quote Page",
					"Wind Driven Rain Retained is " + createQuotePage.windDrivenRainData.getData(), false, false);
			Assertions.passTest("Create Quote Page", "Terrorism Original value  is " + testData.get("Terrorism"));
			Assertions.verify(createQuotePage.terrorismData.getData().contains(testData.get("Terrorism")), true,
					"Create Quote Page", "Terrorism Retained is " + createQuotePage.terrorismData.getData(), false,
					false);
			Assertions.passTest("Create Quote Page",
					"Coverage Extension Package Original value is " + testData.get("CoverageExtensionPackage"));
			Assertions.verify(
					createQuotePage.coverageExtensionPackageData.getData()
							.contains(testData.get("CoverageExtensionPackage")),
					true, "Create Quote Page",
					"Coverage Extension Package Retained is " + createQuotePage.coverageExtensionPackageData.getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Create a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Adding IO-22098
			// Click on edit location
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit location link and building link");

			// Click on building link
			buildingPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.passTest("Building Page", "Clicked on edit building");

			// click on roof details link
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page is loaded successfully", false, false);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Building Page", "Clicked on roof details link");

			// Updating Year roof last replaced
			Assertions.passTest("Building Page",
					"Original year roof last replaced is " + testData.get("YearRoofLastReplaced"));
			testData = data.get(dataValue2);
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.clearData();
			buildingPage.continueWithUpdateBtn.scrollToElement();
			buildingPage.continueWithUpdateBtn.click();
			buildingPage.yearRoofLastReplaced.setData(testData.get("YearRoofLastReplaced"));
			buildingPage.yearRoofLastReplaced.tab();
			Assertions.passTest("Building Page",
					"Year roof last replaced updated successfully, updated year roof last replaced is "
							+ testData.get("YearRoofLastReplaced"));

			// Click on review building
			buildingPage.waitTime(3);
			buildingPage.scrollToBottomPage();
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on review building button");

			// Asserting and verifying hard stop message when year built > year roof last
			// replaced,Hard stop message is'Year roof last replaced cannot be before Year
			// Constructed'
			Assertions.addInfo("Scenario 03",
					"Asserting and verifying hard stop message when year built > year roof last replaced");
			Assertions.verify(
					buildingPage.globalError.getData()
							.equals("Year roof last replaced cannot be before Year Constructed"),
					true, "Building Page", "The hard stop message is " + buildingPage.globalError.getData(), false,
					false);
			Assertions.passTest("Scenario 03", "Scenario 03 Ended");

			// click on roof details link
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page is loaded successfully", false, false);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Building Page", "Clicked on roof details link");

			// Updating year roof last replaced
			testData = data.get(dataValue3);
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.clearData();
			buildingPage.yearRoofLastReplaced.setData(testData.get("YearRoofLastReplaced"));
			buildingPage.yearRoofLastReplaced.tab();
			Assertions.passTest("Building Page",
					"Year roof last replaced updated successfully, updated year roof last replaced is "
							+ testData.get("YearRoofLastReplaced"));

			// Click on create quote button
			buildingPage.waitTime(2);
			buildingPage.scrollToBottomPage();
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}
			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}
			Assertions.passTest("Building Page", "Clicked on create quote button");

			// selecting peril
			testData = data.get(dataValue1);
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Checking user is able to create quote,when year built < year roof last
			// replaced
			Assertions.addInfo("Scenario 04",
					"Checking user is able to create quote,when year built < year roof last replaced");
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account Overview Page",
					"User is able to create quote,when year built < year roof last replaced,the Quote Number :  "
							+ quoteNumber);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			// IO-22098 ended

			// Adding IO-22112
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit location link and building link");

			// Click on building link
			buildingPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.passTest("Building Page", "Clicked on edit building");

			// click on roof details link
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page is loaded successfully", false, false);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			Assertions.passTest("Building Page", "Clicked on roof details link");

			// Updating Year roof last replaced as future year
			testData = data.get(dataValue4);
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.clearData();
			buildingPage.continueWithUpdateBtn.scrollToElement();
			buildingPage.continueWithUpdateBtn.click();
			buildingPage.yearRoofLastReplaced.setData(testData.get("YearRoofLastReplaced"));
			buildingPage.yearRoofLastReplaced.tab();
			Assertions.passTest("Building Page",
					"Year roof last replaced updated successfully, updated year roof last replaced is "
							+ testData.get("YearRoofLastReplaced"));

			// Click on review building
			buildingPage.waitTime(3);
			buildingPage.scrollToBottomPage();
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Clicked on review building button");

			// Asserting and verifying hard stop message when year roof last replaced in
			// future year,Hard stop message is'Future year roof last replaced is not
			// allowed.
			// Constructed'
			Assertions.addInfo("Scenario 05",
					"Asserting and verifying hard stop message when year roof last replaced in future year");
			Assertions.verify(
					buildingPage.globalError.getData().equals("Future year roof last replaced is not allowed."), true,
					"Building Page", "The hard stop message is " + buildingPage.globalError.getData(), false, false);
			Assertions.passTest("Scenario 05", "Scenario 05 Ended");

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 23", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 23", "Executed Successfully");
			}
		}
	}
}