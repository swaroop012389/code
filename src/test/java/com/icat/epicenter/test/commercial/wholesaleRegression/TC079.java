/** Program Description: Create a GL Quote and click on View/Print Ful quote. Assert all available data on the quote document.
 *  Author			   : Sowndarya
 *  Date of Creation   : 08/28/2020
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC079 extends AbstractCommercialTest {

	public TC079() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID079.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Pages
		HomePage homePage = new HomePage();
		LoginPage loginPage = new LoginPage();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		GLInformationPage glInformationPage = new GLInformationPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing variables
		String quoteNumber;
		String quoteNumber2;
		String premiumAmount, icatFees, actualSLTFValue, otherFees;
		String sidebarTotalPremium1, actualTotalPremium1, surplusContributionValue1;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Added code for Taxes and fees update for the AL state
			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as usm successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login", "Logged in as Producer successfully");

			// Click on user preference link and enter the details
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");

			// Add broker fees
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			testData = data.get(data_Value1);
			preferenceOptionsPage.addBrokerFees(testData);
			Assertions.passTest("Preference Options Page", "Details entered successfully");

			// Navigate to homepage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as producer successfully");
			// New code ended

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsPresent(), true, "Home Page",
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

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// entering general liability details
			if (!testData.get("L1-GLLocationClass").equals("")) {
				Assertions.verify(glInformationPage.continueButton.checkIfElementIsDisplayed(), true,
						"GL Information Page", "GL Information Page loaded successfully", false, false);
				glInformationPage.enterGLInformation(testData);
				Assertions.passTest("GL Information Page", "GL Information details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on View or print full quote Page
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View or Print Full quote link");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View or Print Full Quote Page loaded successfully", false, false);

			// Assert values on view print full quote
			Assertions.verify(viewOrPrintFullQuotePage.quoteNumber.getData().contains(quoteNumber), true,
					"View/Print Full Quote Page", viewOrPrintFullQuotePage.quoteNumber.getData() + " is displayed",
					false, false);

			// Premium Details
			Assertions.addInfo("View/Print Full Quote Page", "Assert the Premium Details on Quote Document");
			for (int i = 1; i <= 3; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.commAopDetails.formatDynamicPath(i).checkIfElementIsDisplayed(), true,
						"View/Print Full Quote Page",
						viewOrPrintFullQuotePage.commAopDetails.formatDynamicPath(i).getData()
								+ " displayed is verified",
						false, false);
			}

			for (int i = 5; i <= 7; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.commAopDetails.formatDynamicPath(i).checkIfElementIsDisplayed(), true,
						"View/Print Full Quote Page",
						viewOrPrintFullQuotePage.commAopDetails.formatDynamicPath(i).getData()
								+ " displayed is verified",
						false, false);
			}

			Assertions.verify(viewOrPrintFullQuotePage.commAopDetails.formatDynamicPath(13).checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The " + viewOrPrintFullQuotePage.commAopDetails.formatDynamicPath(13).getData()
							+ " displayed is verified",
					false, false);

			// Coverages, Limits and Deductibles
			Assertions.addInfo("View/Print Full Quote Page",
					"Assert Coverages, Limits and Deductibles Details on Quote Document");
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Coverages, Limits and Deductibles")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Coverages, Limits and Deductibles")
							.getData() + " section displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(1).getData()
							.contains(testData.get("DeductibleValue")),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(1).getData() + " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(2).getData()
							.contains(testData.get("AOWHDeductibleValue")),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(2).getData() + " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(3).getData()
							.contains(testData.get("EquipmentBreakdown")),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(3).getData() + " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(4).getData()
							.contains(testData.get("AOCLDeductibleValue")),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(4).getData() + " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(11).getData()
							.contains(testData.get("L1B1-BldgAddr1")),
					true, "View/Print Full Quote Page",
					"The Building Address " + viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(11).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(13).getData()
							.contains(testData.get("L1B1-BldgValue")),
					true, "View/Print Full Quote Page",
					"The Building Value " + viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(13).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(17).getData()
							.contains(testData.get("L1B1-BldgBPP")),
					true, "View/Print Full Quote Page",
					"The BPP value " + viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(17).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(19).checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The Total limit of Insurance "
							+ viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(19).getData()
							+ " displayed is verified",
					false, false);

			// GL Coverage Details
			Assertions.addInfo("View/Print Full Quote Page", "Assert the GL Coverage Details on Quote Document");
			Assertions
					.verify(viewOrPrintFullQuotePage.mainSections
							.formatDynamicPath("Commercial General Liability Coverage").checkIfElementIsDisplayed(),
							true, "View/Print Full Quote Page",
							viewOrPrintFullQuotePage.mainSections
									.formatDynamicPath("Commercial General Liability Coverage").getData()
									+ " section displayed is verified",
							false, false);

			Assertions
					.verify(viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(2).checkIfElementIsDisplayed(),
							true, "View/Print Full Quote Page",
							"Each Occurrence Limit "
									+ viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(2).getData()
									+ " displayed is verified",
							false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The Products-Completed Operations Aggregate Limit "
							+ viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(4).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(6).checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The Personal and Adverting Injury Limit "
							+ viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(6).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(8).checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The Hired and Non-Owned Auto Limit "
							+ viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(8).getData()
							+ " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(10).checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The Assault and Battery Limit "
							+ viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(10).getData()
							+ " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(12).checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page",
					"The Medical Expense Limit "
							+ viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(12).getData()
							+ " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(14).checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The Damage To Premises Rented To You Limit "
							+ viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(14).getData()
							+ " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(16).checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"General Aggregate Limit "
							+ viewOrPrintFullQuotePage.glCoverageDetails.formatDynamicPath(16).getData()
							+ " is verified",
					false, false);

			// Standard Coverages
			Assertions.addInfo("View/Print Full Quote Page",
					"Assert the Standard Coverages Dertails on Quote Document");
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Standard Coverage")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Standard Coverage").getData()
							+ " section displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues.formatDynamicPath("Coinsurance", "1")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Coinsurance " + viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Coinsurance", "1").getData() + " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Replacement Cost", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Replacement Cost (Building and Personal Property) "
							+ viewOrPrintFullQuotePage.standardCoverageValues.formatDynamicPath("Replacement Cost", "1")
									.getData()
							+ " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Limited Coverage", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Limited Coverage for Fungus, Wet Rot,Dry Rot and Bacteria "
							+ viewOrPrintFullQuotePage.standardCoverageValues.formatDynamicPath("Limited Coverage", "1")
									.getData()
							+ " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues.formatDynamicPath("Debris Removal", "1")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Debris Removal " + viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Debris Removal", "1").getData() + " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Pollutant Clean Up and Removal", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Pollutant Clean Up and Removal "
							+ viewOrPrintFullQuotePage.standardCoverageValues
									.formatDynamicPath("Pollutant Clean Up and Removal", "1").getData()
							+ " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Unscheduled Additional Property", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Unscheduled Additional Property "
							+ viewOrPrintFullQuotePage.standardCoverageValues
									.formatDynamicPath("Unscheduled Additional Property", "1").getData()
							+ " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Increased Cost of Construction", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Increased Cost of Construction "
							+ viewOrPrintFullQuotePage.standardCoverageValues
									.formatDynamicPath("Increased Cost of Construction", "1").getData()
							+ " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Preservation of Property", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Preservation of Property " + viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Preservation of Property", "1").getData() + " is verified",
					false, false);

			Assertions
					.verify(viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Detached Trailers", "1").checkIfElementIsDisplayed(), true,
							"View/Print Full Quote Page",
							"Non-Owned Detached Trailers " + viewOrPrintFullQuotePage.standardCoverageValues
									.formatDynamicPath("Detached Trailers", "1").getData() + " is verified",
							false, false);

			// Selected Coverages
			Assertions.addInfo("View/Print Full Quote Page", "Assert the Selected Coverages Details on Quote Document");
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Selected Endorsements")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Selected Endorsements").getData()
							+ " section displayed is verified",
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Wind-Driven", "1").getData().contains(testData.get("WindDrivenRain")),
							true, "View/Print Full Quote Page",
							"Wind Driven Rain value " + viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Wind-Driven", 1).getData() + " displayed is verified",
							false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Ordinance", "1").getData().contains(testData.get("OrdinanceOrLaw")),
							true, "View/Print Full Quote Page",
							"Ordinance or Law value " + viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Ordinance", "1").getData() + " displayed is verified",
							false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Data and Media", "1")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Data and Media " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Data and Media", "1").getData() + " is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Demolition and Increased Cost of Construction", "1")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Demolition and Increased Cost of Construction "
							+ viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Demolition and Increased Cost of Construction", "1").getData()
							+ "  is verified",
					false, false);

			Assertions
					.verify(viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Drying Out", "1").checkIfElementIsDisplayed(), true,
							"View/Print Full Quote Page",
							"Drying Out " + viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Drying Out", "1").getData() + " displayed is verified",
							false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Expediting Expenses", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Expediting Expenses " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Expediting Expenses", "1").getData() + " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Electronic Vandalism", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Electronic Vandalism "
							+ viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Electronic Vandalism", "1").getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Pollution Clean Up and Removal", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Pollution Clean Up and Removal "
							+ viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Pollution Clean Up and Removal", "1").getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("New Generation", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"New Generation " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("New Generation", "1").getData() + " displayed is verified",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Utility Interruption", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Utility Interruption "
							+ viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Utility Interruption", "1").getData()
							+ " displayed is verified",
					false, false);

			Assertions
					.verify(viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Spoilage", "1").checkIfElementIsDisplayed(), true,
							"View/Print Full Quote Page",
							"Spoilage " + viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Spoilage", "1").getData() + " displayed is verified",
							false, false);

			Assertions
					.verify(viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Water Damage", "1").checkIfElementIsDisplayed(), true,
							"View/Print Full Quote Page",
							"Water Damage "
									+ viewOrPrintFullQuotePage.selectedCoverageDetails
											.formatDynamicPath("Water Damage", "1").getData()
									+ " displayed is verified",
							false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.packageValues
							.formatDynamicPath("Valuable Papers and Records", "1").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Valuable Papers and Records "
							+ viewOrPrintFullQuotePage.packageValues
									.formatDynamicPath("Valuable Papers and Records", "1").getData()
							+ " displayed is verified",
					false, false);

			// Coverage Sublimits
			Assertions.addInfo("View/Print Full Quote Page", "Assert the Sublimit details on Quote Document");
			Assertions
					.verify(viewOrPrintFullQuotePage.mainSections
							.formatDynamicPath("Coverage Sublimits & Extensions Package").checkIfElementIsDisplayed(),
							true, "View/Print Full Quote Page",
							viewOrPrintFullQuotePage.mainSections
									.formatDynamicPath("Coverage Sublimits & Extensions Package").getData()
									+ " section displayed is verified",
							false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.coverageSublimitsDetails.formatDynamicPath(2).checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Package " + viewOrPrintFullQuotePage.coverageSublimitsDetails.formatDynamicPath(2).getData()
							+ " is verified",
					false, false);

			// Notices and Forms
			Assertions.addInfo("View/Print Full Quote Page", "Assert the  Notices and Forms details on Quote Document");
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Notices & Forms")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Notices & Forms").getData()
							+ " section displayed is verified",
					false, false);

			// Building Details
			Assertions.addInfo("View/Print Full Quote Page", "Assert the  Building Details on Quote Document");
			Assertions.verify(
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Location 1, Building 1 Details")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.mainSections.formatDynamicPath("Location 1, Building 1 Details").getData()
							+ " section displayed is verified",
					false, false);

			int k = 1;
			for (int i = 1; i <= 6; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.buildingDetails.formatDynamicPath(k, k, i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.buildingDetails.formatDynamicPath(k, k, i).getData() + " is displayed",
						false, false);
			}

			for (int i = 8; i <= 23; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.buildingDetails.formatDynamicPath(k, k, i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.buildingDetails.formatDynamicPath(k, k, i).getData() + " is displayed",
						false, false);
			}

			// Added ticket IO-21620
			// go back to account overview page
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// clicking on edit building on the renewal account overview page as a producer
			accountOverviewPage.editBuildingLink1.scrollToElement();
			accountOverviewPage.editBuildingLink1.click();

			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 2:  " + quoteNumber2);

			// Fetching premium, icat fees, other fees, surplus contribution and SLTF for
			// "active" quote number 2
			// Fetching Premium Amount from Account Overview Page
			premiumAmount = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			Assertions.passTest("Account Overview Page", "Actual Premium Amount : " + "$" + premiumAmount);

			// Fetching ICAT fees value from Account Overview Page
			icatFees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			Assertions.passTest("Account Overview Page", "Actual ICAT Fees : " + "$" + icatFees);

			// Fetching Actual Other Fees or Broker fees from account overview page
			otherFees = accountOverviewPage.otherFees.getData().replace("$", "").replace(",", "");
			Assertions.passTest("Account Overview Page", "Actual Other Fees : " + "$" + otherFees);

			// Fetching Surplus contribution value from account overview page
			String surplusContributionValue = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			Assertions.passTest("Account Overview Page",
					"Actual Surplus Contribution Value : " + "$" + surplusContributionValue);

			// Actual SLTF value from Account Overview Page
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");

			double calTotalPremium = ((Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(otherFees) + Double.parseDouble(surplusContributionValue))
					+ Double.parseDouble(actualSLTFValue));

			accountOverviewPage.totalPremiumValue.checkIfElementIsPresent();
			accountOverviewPage.totalPremiumValue.scrollToElement();
			String actualTotalPremium = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",",
					"");

			Assertions.addInfo("Scenario 1",
					"Verifying the total premium on the account overview page for active quote");
			Assertions.verify(Double.parseDouble(actualTotalPremium), Precision.round(calTotalPremium, 2),
					"Account Overview Page", "Actual and Calculated total premiums are matching", false, false);
			Assertions.passTest("Account Overview Page", "Actual Total Premium Value : " + "$" + actualTotalPremium);
			Assertions.addInfo("Scenario 1", "Scenario 1 is Ended");

			accountOverviewPage.quotePremium.formatDynamicPath(2).checkIfElementIsPresent();
			accountOverviewPage.quotePremium.formatDynamicPath(2).scrollToElement();
			String sidebarTotalPremium = accountOverviewPage.quotePremium.formatDynamicPath(2).getData().substring(1, 9)
					.replace(",", "");

			Assertions.addInfo("Scenario 2",
					"Verifying the total premium on the sidebar present on the account overview page for active quote");
			Assertions.verify(Double.parseDouble(sidebarTotalPremium), Precision.round(calTotalPremium, 2),
					"Account Overview Page",
					"Actual and Calculated total premiums are matching on the sidebar for active quote.", false, false);
			Assertions.passTest("Account Overview Page",
					"Actual Total Premium Value on the sidebar : " + "$" + sidebarTotalPremium);
			Assertions.addInfo("Scenario 2", "Scenario 2 is Ended");

			// Go to homepage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.searchQuote(quoteNumber);

			// Fetching premium, icat fees, other fees, surplus contribution and SLTF for
			// "Expired" quote number
			// Fetching Premium Amount from Account Overview Page
			premiumAmount = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			Assertions.passTest("Account Overview Page", "Actual Premium Amount : " + "$" + premiumAmount);

			// Fetching ICAT fees value from Account Overview Page
			icatFees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			Assertions.passTest("Account Overview Page", "Actual ICAT Fees : " + "$" + icatFees);

			// Fetching Actual Other Fees or Broker fees from account overview page
			otherFees = accountOverviewPage.otherFees.getData().replace("$", "").replace(",", "");
			Assertions.passTest("Account Overview Page", "Actual Other Fees : " + "$" + otherFees);

			// Fetching Surplus contribution value from account overview page
			surplusContributionValue1 = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			Assertions.passTest("Account Overview Page",
					"Actual Surplus Contribution Value : " + "$" + surplusContributionValue1);

			// Actual SLTF value from Account Overview Page
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");

			double calTotalPremium1 = ((Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(otherFees) + Double.parseDouble(surplusContributionValue))
					+ Double.parseDouble(actualSLTFValue));

			accountOverviewPage.totalPremiumValue.checkIfElementIsPresent();
			accountOverviewPage.totalPremiumValue.scrollToElement();
			actualTotalPremium1 = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", "");

			Assertions.addInfo("Scenario 3",
					"Verifying the total premium on the account overview page for expired quote.");
			Assertions.verify(Double.parseDouble(actualTotalPremium1), Precision.round(calTotalPremium1, 2),
					"Account Overview Page", "Actual and Calculated total premiums are matching", false, false);
			Assertions.passTest("Account Overview Page", "Actual Total Premium Value : " + "$" + actualTotalPremium1);
			Assertions.addInfo("Scenario 3", "Scenario 3 is Ended");

			accountOverviewPage.quotePremium.formatDynamicPath(1).checkIfElementIsPresent();
			accountOverviewPage.quotePremium.formatDynamicPath(1).scrollToElement();
			sidebarTotalPremium1 = accountOverviewPage.quotePremium.formatDynamicPath(1).getData().substring(1, 9)
					.replace(",", "");

			Assertions.addInfo("Scenario 4",
					"Verifying the total premium on the sidebar present on the account overview page for expired quote");
			Assertions.verify(Double.parseDouble(sidebarTotalPremium1), Precision.round(calTotalPremium1, 2),
					"Account Overview Page",
					"Actual and Calculated total premiums are matching on the sidebar for expired quote.", false,
					false);
			Assertions.passTest("Account Overview Page",
					"Actual Total Premium Value on the sidebar : " + "$" + sidebarTotalPremium1);
			Assertions.addInfo("Scenario 4", "Scenario 4 is Ended");

			// Ticket IO-21620 Ended

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 79", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 79", "Executed Successfully");
			}
		}
	}
}
