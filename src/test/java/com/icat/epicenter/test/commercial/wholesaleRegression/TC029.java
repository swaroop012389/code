/** Program Description: AOP Policy --> 4 Losses in last 3 years and total loss of $26000. Check for referral.
 *  Author			   : John
 *  Date of Creation   : 11/26/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC029 extends AbstractCommercialTest {

	public TC029() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID029.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();

		// Initializing the variables
		Map<String, String> testData;
		int dataValue1 = 0;
		testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// creating New account
			homePage.enterPersonalLoginDetails();
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
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// enter prior loss details
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

			// Quote refer details
			Assertions.addInfo("Quote Referral Page",
					"Asserting Prior Loss Harstop Error Message and Warning messages");
			/*
			 * Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(),
			 * true, "Create Quote Page", "Hard stop Error Message : "
			 * +createQuotePage.globalErr.getData() + " displayed is verified", false,
			 * false);
			 */
			Assertions.verify(
					createQuotePage.wdrWarning.formatDynamicPath("All Other Perils losses").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Warning Message 1 : "
							+ createQuotePage.wdrWarning.formatDynamicPath("All Other Perils losses").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.wdrWarning.formatDynamicPath("three or more losses").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Warning Message 2 : "
							+ createQuotePage.wdrWarning.formatDynamicPath("three or more losses").getData()
							+ " displayed is verified",
					false, false);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 29", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 29", "Executed Successfully");
			}
		}
	}
}