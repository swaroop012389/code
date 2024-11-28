/** Program Description:  Check if the line Completed and signed diligent effort form is present under Subject To in Terms and
 * Conditions Section of commercial retail quotes on New Jersey quotes if any of the buildings on the policy are less than 31% occupied
 *  Author			   : Karthik Malles
 *  Date of Creation   : 23/07/2021
 **/

package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

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

public class TC013 extends AbstractCommercialTest {

	public TC013() {
		super(LoginType.RETAILPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID013.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();

		// Initializing Variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		String quoteNumber;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData2 = data.get(data_Value2);
		int noOfLocationsValue1 = Integer.parseInt(testData.get("LocCount"));
		int noOfBuildingsValue1 = Integer.parseInt(testData.get("L" + noOfLocationsValue1 + "-BldgCount"));
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Home Page
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home Page loaded susscessfully)", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("Home Page", "New Account Created successfully");

			// Eligibility Page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Details in Location Page
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page Loaded Successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location Details Entered successfully");

			// Entering Details in Building Page
			
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Building Details Entered successfully");

			// adding the ticket IO-20760
			Assertions.addInfo("Scenario 1", "Verifing display of Formatting code on PBU products Validation Message");

			if (createQuotePage.globalWarning.formatDynamicPath("ineligible").getData().contains("<br>")
					|| createQuotePage.globalWarning.formatDynamicPath("ineligible").getData().contains("<li>")) {
				Assertions.verify(
						createQuotePage.globalWarning.formatDynamicPath("ineligible").checkIfElementIsDisplayed(), true,
						"Create Quote Page",
						"Create Quote Page Warning message is displayed with Formatting code"
								+ createQuotePage.globalWarning.formatDynamicPath("ineligible").getData(),
						false, false);
			} else {
				Assertions.verify(
						createQuotePage.globalWarning.formatDynamicPath("ineligible").checkIfElementIsDisplayed(), true,
						"Create Quote Page",
						"Create Quote Page Warning message displayed  Successfully as "
								+ createQuotePage.globalWarning.formatDynamicPath("ineligible").getData(),
						false, false);
			}
			Assertions.addInfo("Scenario 1", "Scenario 1 ended");

			// Create Quote Page for Occupancy Percent Value less than 31%
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Creat Quote Page",
					"Create Quote loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote Details Entered successfully");

			// Account Summary Page for Occupancy Percent Value less than 31%
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on View print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full Quote link successfully");

			// Quote Document for Occupancy Percent Value less than 31%
			Assertions.verify(quoteDetailsPage.goBackButton.checkIfElementIsDisplayed(), true, "Quote Details Page",
					"Quote Document loaded successfully", false, false);
			Assertions.verify(quoteDetailsPage.quoteNumber.checkIfElementIsDisplayed(), true, "Quote Details Page",
					"Quote Number generated is " + quoteDetailsPage.quoteNumber.getData(), false, false);

			// Verifying the absence of "Completed and signed diligent effort form"
			Assertions.addInfo("Scenario 02",
					"Assert the absence of Completed and signed diligent effort form for NJ state");
			Assertions.verify(quoteDetailsPage.diligentEffortFormText.checkIfElementIsPresent(), false,
					"Quote Details Page",
					"\"Completed and signed diligent effort form\" is not found in this Quote since State is "
							+ testData.get("QuoteState") + " and Occupancy Percent is "
							+ testData.get("L" + noOfLocationsValue1 + "B" + noOfBuildingsValue1 + "-PercentOccupied"),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			quoteDetailsPage.goBackButton.scrollToElement();
			quoteDetailsPage.goBackButton.click();

			// Account Overview Page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.editBuildingLink1.checkIfElementIsDisplayed();
			accountOverviewPage.editBuildingLink1.scrollToElement();
			accountOverviewPage.editBuildingLink1.click();
			accountOverviewPage.editBuilding.checkIfElementIsDisplayed();
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();

			// Building Page Occupancy Percent Value greater than or equal to 31%
			Assertions.verify(buildingPage.buildingOccupancyLink.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page loaded successfully", false, false);
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			Assertions.verify(buildingPage.buildingOccupancyLink.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Occupancy section loaded successfully", false, false);
			if (buildingPage.primaryPercentOccupied.checkIfElementIsPresent()
					&& buildingPage.primaryPercentOccupied.checkIfElementIsDisplayed()) {
				buildingPage.primaryPercentOccupied.scrollToElement();
				buildingPage.primaryPercentOccupied.clearData();
				buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed();
				buildingPage.continueWithUpdateBtn.scrollToElement();
				buildingPage.continueWithUpdateBtn.click();
				buildingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				buildingPage.primaryPercentOccupied.setData(testData2.get("L1B1-PercentOccupied"));
			}
			if (buildingPage.buildingOccupancy_yes.checkIfElementIsPresent()
					&& buildingPage.buildingOccupancy_yes.checkIfElementIsDisplayed()) {
				if ((testData2.get("L1B1-BuildingMorethan31%Occupied").equals("Yes"))) {
					buildingPage.buildingOccupancy_yes.waitTillPresenceOfElement(60);
					buildingPage.buildingOccupancy_yes.waitTillVisibilityOfElement(60);
					buildingPage.buildingOccupancy_yes.scrollToElement();
					buildingPage.waitTime(2);// wait time is needed to load the element
					buildingPage.buildingOccupancy_yes.click();
					buildingPage.waitTime(2);// wait time is needed to load the element
					buildingPage.continueWithUpdateBtn.checkIfElementIsDisplayed();
					buildingPage.continueWithUpdateBtn.scrollToElement();
					buildingPage.continueWithUpdateBtn.click();
					buildingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
			}
			buildingPage.createQuote.checkIfElementIsDisplayed();
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			waitTime(3);// if the wait time is removed test case will fail here
			if (buildingPage.createQuote.checkIfElementIsPresent()
					&& buildingPage.createQuote.checkIfElementIsDisplayed()) {
				buildingPage.createQuote.checkIfElementIsDisplayed();
				buildingPage.createQuote.scrollToElement();
				buildingPage.createQuote.click();
			}
			Assertions.passTest("Building Page", "Building Page Details are entered successfully");

			// Select Peril Page
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page is loaded successfully", false, false);
			selectPerilPage.selectPeril(testData2.get("Peril"));

			// Create Quote Page for Occupancy Percent Value greater than or equal to 31%
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData2);
			Assertions.passTest("Create Quote Page", "Deductible Values entered successfully in Create Quote Page");

			// Account Summary Page for Occupancy Percent Value greater than or equal to 31%
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click on View print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked to View Quote Document successfully");

			// Quote Document for Occupancy Percent Value greater than or equal to 31%
			Assertions.verify(quoteDetailsPage.goBackButton.checkIfElementIsDisplayed(), true, "Quote Details Page",
					"Quote Document loaded successfully", false, false);
			quoteDetailsPage.quoteNumber.waitTillVisibilityOfElement(60);
			Assertions.verify(quoteDetailsPage.quoteNumber.checkIfElementIsDisplayed(), true, "Quote Details Page",
					"Quote Number generated is " + quoteDetailsPage.quoteNumber.getData(), false, false);

			// Verifying the presence of "Completed and signed diligent effort form"
			Assertions.addInfo("Scenario 03", "Verifying the presence of Completed and signed diligent effort form");
			quoteDetailsPage.diligentEffortFormText.scrollToElement();
			Assertions.verify(quoteDetailsPage.diligentEffortFormText.checkIfElementIsDisplayed(), true,
					"Quote Details Page",
					"\"" + quoteDetailsPage.diligentEffortFormText.getData() + "\""
							+ " is found in this Quote since State is " + testData2.get("QuoteState")
							+ " and Occupancy Percent is "
							+ testData2.get("L" + noOfLocationsValue1 + "B" + noOfBuildingsValue1 + "-PercentOccupied"),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			quoteDetailsPage.goBackButton.scrollToElement();
			quoteDetailsPage.goBackButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC013 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC013 ", "Executed Successfully");
			}
		}

	}
}
