/** Program Description: Check the Error messages for different Occupancies
 *  Author			   : John
 *  Date of Creation   : 08/12/2020
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC097 extends AbstractCommercialTest {

	public TC097() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID097.xls";
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
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();

		// Initializing the variables
		String quoteNumber;
		int dataValue1 = 0;
		int dataValue3 = 2;
		int dataValue4 = 3;
		int dataValue5 = 4;
		int dataValue6 = 5;
		int dataValue7 = 6;
		Map<String, String> testData = data.get(dataValue1);
		Map<String, String> testData2 = data.get(dataValue3);
		Map<String, String> testData3 = data.get(dataValue4);
		Map<String, String> testData4 = data.get(dataValue5);
		Map<String, String> testData5 = data.get(dataValue6);
		Map<String, String> testData6 = data.get(dataValue7);
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

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();
			buildingPage.addBuildingDetails(testData, 1, 1);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, 1, 1);
			buildingPage.reviewBuilding();

			// Assert warning message
			Assertions.addInfo("Building Page",
					"Assert the Error message when occupancy is Agriculture/Food Processing - Agriculture");
			Assertions.verify(buildingPage.globalError.checkIfElementIsDisplayed(), true, "Building Page",
					buildingPage.globalError.getData() + " is displayed for occupancy type, Agriculture", false, false);

			// Click on building occupancy link
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			buildingPage.waitTime(2);// need wait time to load the element

			if (testData2.get("L1B1-PrimaryOccupancyCode") != null
					&& !testData2.get("L1B1-PrimaryOccupancyCode").equalsIgnoreCase("")) {
				if (!buildingPage.primaryOccupancy.getData().equals("")) {
					WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
							"//div[label[a[contains(text(),'Primary Occupancy')]]]//following-sibling::div//input[contains(@id,'primaryOccupancy')]"));
					ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
					ele.sendKeys(testData2.get("L1B1-PrimaryOccupancy"));
					buildingPage.primaryOccupancy.tab();
				}
				buildingPage.setOccupancyJS("primary", testData2.get("L1B1-PrimaryOccupancyCode"),
						testData.get("Peril"), testData.get("QuoteState"));
			}

			// click on resubmit
			buildingPage.waitTime(2);// need wait time to load the element
			buildingPage.reSubmit.waitTillVisibilityOfElement(60);
			buildingPage.reSubmit.scrollToElement();
			buildingPage.reSubmit.click();

			// Assert warning message
			Assertions.addInfo("Building Page", "Assert the Error message when occupancy is Manufacturing");
			Assertions.verify(buildingPage.globalError.checkIfElementIsDisplayed(), true, "Building Page",
					buildingPage.globalError.getData() + " is displayed for occupancy type, Manufacturing", false,
					false);

			if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}

			if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}

			// Create a quote
			buildingPage.createQuote.waitTillVisibilityOfElement(60);
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

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

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 1:  " + quoteNumber);

			// edit location
			accountOverviewPage.editLocation.waitTillVisibilityOfElement(60);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();

			// Update occupancy type to Office and secondary occupancy type wholesale
			buildingPage.yearBuilt.waitTillVisibilityOfElement(60);
			buildingPage.yearBuilt.scrollToElement();
			buildingPage.yearBuilt.clearData();
			buildingPage.waitTime(2); // continue with update is not clicking without waittime
			if (buildingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				buildingPage.expiredQuotePopUp.scrollToElement();
				buildingPage.continueWithUpdateBtn.scrollToElement();
				buildingPage.continueWithUpdateBtn.click();
				buildingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			buildingPage.yearBuilt.setData(testData3.get("L1B1-BldgYearBuilt"));

			buildingPage.buildingOccupancyLink.waitTillVisibilityOfElement(60);
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			buildingPage.buildingOccupancy_yes.scrollToElement();
			buildingPage.buildingOccupancy_yes.click();
			buildingPage.addBuildingPrimaryOccupancy(testData3, 1, 1);
			buildingPage.waitTime(1);// need waittime to load the element

			// add secondary occupancy
			if (testData3.get("L1B1-SecondaryOccupancyCode") != null
					&& !(testData3.get("L1B1-SecondaryOccupancyCode").equals(""))) {
				if (!buildingPage.secondaryOccupancy.getData().equals("")) {
					WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
							"//div[label[a[contains(text(),'Secondary Occupancy')]]]//following-sibling::div//input[contains(@id,'secondaryOccupancy')]"));
					ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
					buildingPage.waitTime(2);// need waittime to load the element
					buildingPage.secondaryOccupancy.tab();
				}
				buildingPage.setOccupancyJS("questionssecondary", testData3.get("L1B1-SecondaryOccupancyCode"),
						testData3.get("Peril"), testData3.get("QuoteState"));
				buildingPage.waitTime(2);// need waittime to load the element
			}
			buildingPage.waitTime(3);// if waittime is removed it is not going to if condition
			buildingPage.secondaryPercentOccupied.scrollToElement();
			buildingPage.secondaryPercentOccupied.setData(testData3.get("L1B1-SecOccupancyPercent"));
			buildingPage.waitTime(3);// if waittime is removed test case will fail here
			// buildingPage.waitTime(3); // not clicking on create quote button without
			// waittime
			if (buildingPage.createQuote.checkIfElementIsPresent()
					|| buildingPage.createQuote.checkIfElementIsDisplayed()) {
				buildingPage.createQuote.scrollToElement();
				buildingPage.createQuote.click();
			}

			// Assert warning message
			Assertions.addInfo("Building Page", "Assert the Error message when occupancy is Wholesale");
			Assertions.verify(buildingPage.globalError.checkIfElementIsDisplayed(), true, "Building Page",
					buildingPage.globalError.getData() + " is displayed for secondary occupancy type, Wholesale", false,
					false);

			// Update occupancy type to Office and secondary occupancy type wholesale
			buildingPage.yearBuilt.waitTillVisibilityOfElement(60);
			buildingPage.yearBuilt.scrollToElement();
			buildingPage.yearBuilt.clearData();
			buildingPage.waitTime(2); // continue with update is not clicking without waittime
			if (buildingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				buildingPage.expiredQuotePopUp.scrollToElement();
				buildingPage.continueWithUpdateBtn.scrollToElement();
				buildingPage.continueWithUpdateBtn.click();
				buildingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			buildingPage.yearBuilt.setData(testData3.get("L1B1-BldgYearBuilt"));

			// Update occupancy type to Office and secondary occupancy type wholesale
			buildingPage.buildingOccupancyLink.waitTillVisibilityOfElement(60);
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			buildingPage.addBuildingSecondaryOccupancy(testData4, 1, 1);
			buildingPage.scrollToBottomPage();
			buildingPage.createQuote.waitTillVisibilityOfElement(60);
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));

				// Entering Create quote page Details
				Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Create Quote Page loaded successfully", false, false);
				createQuotePage.enterQuoteDetailsCommercialNew(testData);
				Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 2:  " + quoteNumber);

			// Asserting quote status
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("1").getData().contains("Expired"),
					true, "Account Overview Page",
					"Quote 1 status is " + accountOverviewPage.quoteStatus.formatDynamicPath("1").getData(), false,
					false);
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("2").getData().contains("Active"), true,
					"Account Overview Page",
					"Quote 2 status is " + accountOverviewPage.quoteStatus.formatDynamicPath("2").getData(), false,
					false);

			// edit location
			accountOverviewPage.editLocation.waitTillVisibilityOfElement(60);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();

			// Update cladding year built
			// Building Cladding = Exterior Insulating Finishing Systems (EIFS) and year
			// built = 1994,Roof Cladding = Tile or Clay,Fire Protection = Superior .. Smoke
			// detectors and Automatic Sprinkler System
			Assertions.addInfo("Building Page",
					"Changing the Building Cladding = Exterior Insulating Finishing Systems (EIFS),year built = 1994,Roof Cladding = Tile or Clay,Fire Protection = Superior .. Smoke detectors and Automatic Sprinkler System");
			buildingPage.exteriorCladdingArrow.scrollToElement();
			buildingPage.exteriorCladdingArrow.click();
			buildingPage.exteriorCladdingOption.formatDynamicPath(testData5.get("L1B1-BldgCladding")).scrollToElement();
			buildingPage.exteriorCladdingOption.formatDynamicPath(testData5.get("L1B1-BldgCladding")).click();

			buildingPage.yearBuilt.scrollToElement();
			buildingPage.yearBuilt.clearData();
			buildingPage.waitTime(2); // continue with update is not clicking without waittime
			if (buildingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				buildingPage.expiredQuotePopUp.scrollToElement();
				buildingPage.continueWithUpdateBtn.scrollToElement();
				buildingPage.continueWithUpdateBtn.click();
				buildingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			buildingPage.yearBuilt.setData(testData3.get("L1B1-BldgYearBuilt"));

			// update occupancy details
			buildingPage.buildingOccupancyLink.waitTillPresenceOfElement(60);
			buildingPage.buildingOccupancyLink.waitTillVisibilityOfElement(60);
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			if (buildingPage.owner.checkIfElementIsPresent() && buildingPage.owner.checkIfElementIsDisplayed()) {
				buildingPage.owner.waitTillVisibilityOfElement(60);
				buildingPage.owner.scrollToElement();
				buildingPage.owner.click();
			}

			// update roof details
			buildingPage.waitTime(2);// without waittime not clicking on roofDetailsLink
			buildingPage.roofDetailsLink.waitTillVisibilityOfElement(60);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			buildingPage.waitTime(2);// without waittime not clicking on roofCladdingArrow
			buildingPage.roofCladdingArrow.waitTillVisibilityOfElement(60);
			buildingPage.roofCladdingArrow.scrollToElement();
			buildingPage.roofCladdingArrow.click();
			buildingPage.roofCladdingOption.formatDynamicPath(testData5.get("L1B1-BldgRoofCladding")).scrollToElement();
			buildingPage.roofCladdingOption.formatDynamicPath(testData5.get("L1B1-BldgRoofCladding")).click();

			// update additional details
			buildingPage.additionalInfoLink.waitTillVisibilityOfElement(60);
			buildingPage.additionalInfoLink.scrollToElement();
			buildingPage.additionalInfoLink.click();
			buildingPage.waitTime(2);// without waittime not clicking on fireProtectionArrow
			buildingPage.fireProtectionArrow.scrollToElement();
			buildingPage.fireProtectionArrow.click();
			buildingPage.fireProtectionoption.formatDynamicPath(testData5.get("L1B1-BldgFireProtection"))
					.waitTillPresenceOfElement(60);
			buildingPage.fireProtectionoption.formatDynamicPath(testData5.get("L1B1-BldgFireProtection"))
					.scrollToElement();
			buildingPage.fireProtectionoption.formatDynamicPath(testData5.get("L1B1-BldgFireProtection")).click();
			buildingPage.protectionClass.scrollToElement();
			buildingPage.protectionClass.setData(testData5.get("L1B1-ProtectionClassOverride"));
			buildingPage.scrollToBottomPage();
			buildingPage.createQuote.waitTillVisibilityOfElement(60);
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// Assertion warning message
			Assertions.addInfo("Building Page",
					"Assert the Error Message Building Cladding = Exterior Insulating Finishing Systems (EIFS),year built = 1994,Roof Cladding = Tile or Clay,Fire Protection = Superior .. Smoke detectors and Automatic Sprinkler System");
			Assertions.verify(buildingPage.globalError.checkIfElementIsDisplayed(), true, "Building Page",
					buildingPage.globalError.getData()
							+ " is displayed for Buildings built prior to 1995 and with EIFS",
					false, false);

			// update yearbuilt and stories
			Assertions.addInfo("Building Page", "Change the Year Built = 1995 and Number of Stories =6");
			buildingPage.yearBuilt.waitTillVisibilityOfElement(60);
			buildingPage.yearBuilt.scrollToElement();
			buildingPage.yearBuilt.setData(testData6.get("L1B1-BldgYearBuilt"));
			buildingPage.numOfStories.waitTillVisibilityOfElement(60);
			buildingPage.numOfStories.scrollToElement();
			buildingPage.numOfStories.setData(testData6.get("L1B1-BldgStories"));

			// Assertion warning message
			Assertions.addInfo("Building Page",
					"Assert the Error message when the Year Built = 1995 and Number of Stories =6");
			Assertions.verify(buildingPage.globalError.checkIfElementIsDisplayed(), true, "Building Page",
					buildingPage.globalError.getData() + " is displayed", false, false);

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 97", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 97", "Executed Successfully");
			}
		}
	}
}
