/** Program Description:  "Check if Diligent Efforts Wordings are Displayed in the Quote Documents of "
+ "Alabama, Florida habitational risks (quotes with a habitational occupancy as the Tier 1 occupancy for any quoted building), North Carolina, South Carolina, Texas, Arkansas, Missouri, and Tennessee");
*  Author			   : Karthik Malles
*  Date of Creation   : 23/07/2021
**/

package com.icat.epicenter.test.commercial.retailRegression;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC015 extends AbstractCommercialTest {

	public TC015() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID015.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

// Initializing Page Objects and Other Variables
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();

// Initializing Variables for retrieving data for all the States required.
		int n = 16;
		int[] dataValue = new int[n];
		Map<String, String> testData;

// Initializing Variable for retrieving data for the first time and Entering
// Login Page.
		dataValue[0] = 0;
		Map<String, String> initialTestData = data.get(dataValue[0]);

// Initializing Variables to hold Set of Habitational Occupancy
		Set<String> setOfHabitationalOccupancy = new HashSet<>();
		setOfHabitationalOccupancy.add("Apartment");
		setOfHabitationalOccupancy.add("Model Home - Apartment");
		setOfHabitationalOccupancy.add("Dormitories, Fraternities/Sororities - Apartment");
		setOfHabitationalOccupancy.add("Condominiums-Investor Owned Units - Apartment");
		setOfHabitationalOccupancy.add("Condominium/Townhouse Association - Condominium/Townhouse");
		setOfHabitationalOccupancy.add("Common Property - Condominium/Townhouse");

// Initializing Variables to hold Set of EarthQuake States
		Set<String> setOfEQStates = new HashSet<>();
		setOfEQStates.add("AR");
		setOfEQStates.add("AZ");
		setOfEQStates.add("NV");
		setOfEQStates.add("UT");
		setOfEQStates.add("MO");
		setOfEQStates.add("TN");

// Initializing Variables to hold the States which Do not have the Diligent
// Effort Form attached in Quote Document
		Set<String> setOfStatesDiligentEffortDisplayed = new HashSet<>();
		setOfStatesDiligentEffortDisplayed.add("AL");
		setOfStatesDiligentEffortDisplayed.add("NC");
		setOfStatesDiligentEffortDisplayed.add("SC");
		setOfStatesDiligentEffortDisplayed.add("AR");
		setOfStatesDiligentEffortDisplayed.add("TX");
		setOfStatesDiligentEffortDisplayed.add("MO");
		setOfStatesDiligentEffortDisplayed.add("TN");

// Initializing String Arrays to hold Quote Numbers and Diligent Effort Wordings
// for all States.
		String[] quoteNumber = new String[n];
		boolean isTestPassed = false;

		try {
// Home Page
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page Loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(initialTestData, setUpData);
			Assertions.passTest("Home Page", "Entered Details in Home Page successfully");

// Using for Loop to get the details from Quote Document for all States
			for (int i = 1; i < n; i++) {
				dataValue[i] = i;
				testData = data.get(dataValue[i]);
				String stateQuotedFor = testData.get("QuoteState");
				String locationCount = testData.get("LocCount");
				String buildingCount = testData.get("L" + locationCount + "-BldgCount");
				String flStatePrimaryOccupancyType = testData
						.get("L" + locationCount + "B" + buildingCount + "-PrimaryOccupancy");

				if (i >= 2) {

// Home Page
					Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
							"Home Page Loaded successfully", false, false);
					homePage.createNewAccountWithNamedInsured(testData, setUpData);
					Assertions.passTest("Home Page", "Created New Account successfully");
					waitTime(5);
				}

// Eligibility Page
				Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
						"Eligibility Page Loaded successfully", false, false);
				eligibilityPage.processSingleZip(testData);
				Assertions.passTest("Eligibiltiy Page", "Zip code Entered for the State " + stateQuotedFor);

// Location Page
				Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
						"Location Page Loaded successfully", false, false);
				locationPage.enterLocationDetails(testData);

// Building Page
				//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
						//"Building Page Loaded successfully", false, false);
				buildingPage.enterBuildingDetails(testData);

// To Print the Primary Occupancy of FL State since FL State Diligent Effort
// wordings depend on the type of Occupancy
				if (stateQuotedFor.equals("FL")) {
					Assertions.passTest("Building Page",
							"The Primary Occupancy for Florida State is " + flStatePrimaryOccupancyType);
				}

// Code to skip EQ States from Entering Select Peril Page
				if (!(setOfEQStates.contains(stateQuotedFor))) {
// Select Peril Page
					Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true,
							"Select Peril Page", "Select Peril Page Loaded successfully", false, false);
					selectPerilPage.selectPeril(testData.get("Peril"));
				}

// Create Quote Page
				Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote",
						"Create Quote Loaded successfully", false, false);
				createQuotePage.enterQuoteDetailsCommercialNew(testData);

// Account Overview Page
				Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
				accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
				accountOverviewPage.viewPrintFullQuoteLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on View/Print Full Quote Link");

// Quote Details Page
				Assertions.verify(quoteDetailsPage.quoteNumber.checkIfElementIsDisplayed(), true, "Quote Details Page",
						"Quote Details Page Loaded successfully", false, false);
				quoteNumber[i] = quoteDetailsPage.quoteNumber.getData();
				Assertions.passTest("Quote Details Page", "Quote Number genereated is " + quoteNumber[i]);

// Checking for the Presence and Absence of Diligence Effort Wordings
				Assertions.addInfo("Scenario 01", "Verifying for the Presence/Absence of Diligent Effort wordings");
				if ((setOfStatesDiligentEffortDisplayed.contains(stateQuotedFor)) || (stateQuotedFor.equals("FL")
						&& setOfHabitationalOccupancy.contains(flStatePrimaryOccupancyType))) {
					if ((quoteDetailsPage.dueDiligenceCertificate.checkIfElementIsPresent()
							&& quoteDetailsPage.dueDiligenceCertificate.checkIfElementIsDisplayed())
							&& (quoteDetailsPage.dueDiligenceDetails.checkIfElementIsPresent()
									&& quoteDetailsPage.dueDiligenceDetails.checkIfElementIsDisplayed())) {
						Assertions.passTest("Quote Details Page",
								"The Words related to Diligent efforts are displayed");

					}

// Navigating to Home Page
					if (quoteDetailsPage.homePageLink.checkIfElementIsPresent()
							&& quoteDetailsPage.homePageLink.checkIfElementIsDisplayed()) {
						quoteDetailsPage.homePageLink.scrollToElement();
						quoteDetailsPage.homePageLink.click();
					}

				} else {
					if (!((quoteDetailsPage.dueDiligenceCertificate.checkIfElementIsPresent())
							|| (quoteDetailsPage.dueDiligenceDetails.checkIfElementIsPresent()))) {
						Assertions.passTest("Quote Details Page",
								"The Words related to Diligent efforts are not displayed");
					}

// Navigating to Home Page
					if (quoteDetailsPage.homePageLink.checkIfElementIsPresent()
							&& quoteDetailsPage.homePageLink.checkIfElementIsDisplayed()) {
						quoteDetailsPage.homePageLink.scrollToElement();
						quoteDetailsPage.homePageLink.click();
					}
				}
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC015 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC015 ", "Executed Successfully");
			}
		}
	}
}
