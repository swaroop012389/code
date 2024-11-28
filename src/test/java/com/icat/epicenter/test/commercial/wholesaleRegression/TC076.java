/** Program Description: Create an account enter invalid zipcodes and enter ten zipcodes and check the elements present in building page
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/28/2019
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC076 extends AbstractCommercialTest {

	public TC076() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID076.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();

		// Initializing the variables
		int data_Value0 = 0;
		int data_Value11 = 11;
		int data_Value12 = 12;
		Map<String, String> testData1 = data.get(data_Value0);
		Map<String, String> testData2 = data.get(data_Value11);
		Map<String, String> testData3 = data.get(data_Value12);
		boolean isTestPassed = false;

		try {
			// creating New account
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.createNewAccountProducer.waitTillPresenceOfElement(60);
			homePage.createNewAccountProducer.waitTillVisibilityOfElement(60);
			homePage.createNewAccountProducer.moveToElement();
			homePage.createNewAccountProducer.click();
			if (homePage.producerNumber.checkIfElementIsPresent()) {
				homePage.producerNumber.setData(testData1.get("ProducerNumber"));
			}

			if (homePage.effectiveDate.formatDynamicPath("1").checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath("1").checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath("1").scrollToElement();
				homePage.effectiveDate.formatDynamicPath("1").waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath("1").setData(testData1.get("PolicyEffDate"));
			}
			homePage.goButton.click();
			Assertions.passTest("Home Page", "Clicked on go button without entering Insured name");

			// Verifying Named insured field is mandatory
			Assertions.addInfo("Home Page", "Verifying Named insured field is mandatory");
			Assertions.verify(homePage.namedInsuredMandatoryError.checkIfElementIsDisplayed(), true, "Home Page",
					"The filed Named Insured is mandatory is verified", false, false);
			homePage.createNewAccountWithNamedInsured(testData2, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering invalid data for zipcode filed
			Assertions.addInfo("Eligibility Page",
					"Entering invalid data for zipcode field and verifying it is not going forward for the invalid data");
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);

			// Entering the Comination of alphabets in the zipcode field
			Assertions.addInfo("Eligibility Page", "Entering the Comination of alphabets in the zipcode field");
			eligibilityPage.zipCode1.setData(testData1.get("ZipCode"));
			Assertions.passTest("Eligibility Page", "The Entered Zipcode is " + testData1.get("ZipCode"));
			eligibilityPage.zipCode1.tab();
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsEnabled(), false, "Eligibility Page",
					"The Entered zipcode " + testData1.get("ZipCode") + " is Invalid", false, false);

			// Entering the Comination of Special Characters in the zipcode field
			Assertions.addInfo("Eligibility Page",
					"Entering the Comination of Special Characters in the zipcode field");
			eligibilityPage.zipCode1.clearData();
			eligibilityPage.zipCode1.setData(testData2.get("ZipCode"));
			Assertions.passTest("Eligibility Page", "The Entered Zipcode is  " + testData2.get("ZipCode"));
			eligibilityPage.zipCode1.tab();

			// Entering the 4 digits number in zipcode field
			Assertions.addInfo("Eligibility Page", "Entering the 4 digits number in zipcode field");
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsEnabled(), false, "Eligibility Page",
					"The Entered zipcode " + testData2.get("ZipCode") + " is Invalid", false, false);
			eligibilityPage.zipCode1.clearData();
			eligibilityPage.zipCode1.setData(testData3.get("ZipCode"));
			Assertions.passTest("Eligibility Page", "The Entered Zipcode is " + testData3.get("ZipCode"));
			eligibilityPage.zipCode1.tab();
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsEnabled(), false, "Eligibility Page",
					"The Entered zipcode " + testData3.get("ZipCode") + " is Invalid", false, false);

			// Entering 10 Valid zipcodes
			Assertions.addInfo("Eligibility Page", "Entering 10 Valid Zipcodes");
			eligibilityPage.zipCode1.clearData();
			for (int i = 1; i < 11; i++) {
				int data_Valuei = i;
				Map<String, String> testDatai = data.get(data_Valuei);
				eligibilityPage.zipcodeField.formatDynamicPath(i).setData(testDatai.get("ZipCode"));
				eligibilityPage.zipcodeField.formatDynamicPath(i).scrollToElement();
				eligibilityPage.zipcodeField.formatDynamicPath(i).tab();
				Assertions.passTest("Eligibility Page", "The Entered Zipcode is " + testDatai.get("ZipCode"));
			}
			Assertions.passTest("Eligibility Page", "Ten valid Zipcodes Entered successfully");

			// click on continue button
			eligibilityPage.continueButton.waitTillElementisEnabled(60);
			eligibilityPage.continueButton.click();
			eligibilityPage.continueButton.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Eligibility Page", "Clicked on Continue button");

			// Enter Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			if (locationPage.addBuildingsButton.checkIfElementIsPresent()
					&& locationPage.addBuildingsButton.checkIfElementIsDisplayed()) {
				locationPage.addBuildingsButton.scrollToElement();
				locationPage.addBuildingsButton.click();
			}

			// Asserting the elements from Building page
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page loaded successfully", false, false);
			Assertions.addInfo("Building Page", "Assert the Presence of Elements present in Building Page");
			Assertions.verify(buildingPage.buildingDetailsLink.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Details Link displayed is verified", false, false);
			Assertions.verify(buildingPage.address.checkIfElementIsDisplayed(), true, "Building Page",
					"Address field displayed is verified", false, false);
			Assertions.verify(buildingPage.manualEntry.checkIfElementIsDisplayed(), true, "Building Page",
					"Manual Entry link displayed is verified", false, false);
			Assertions.verify(buildingPage.address2.checkIfElementIsDisplayed(), true, "Building Page",
					"Address 2 Field displayed is verified", false, false);
			Assertions.verify(buildingPage.constructionTypeArrow.checkIfElementIsDisplayed(), true, "Building Page",
					"Construction Type Dropdown displayed is verified", false, false);
			Assertions.verify(buildingPage.exteriorCladdingArrow.checkIfElementIsDisplayed(), true, "Building Page",
					"Exterior Cladding Dropdown displayed is verified", false, false);
			Assertions.verify(buildingPage.numOfStories.checkIfElementIsDisplayed(), true, "Building Page",
					"Number of Stories filed displayed is verified", false, false);
			Assertions.verify(buildingPage.yearBuilt.checkIfElementIsDisplayed(), true, "Building Page",
					"Year Built field displayed is verified", false, false);
			Assertions.verify(buildingPage.totalSquareFootage.checkIfElementIsDisplayed(), true, "Building Page",
					"Total Square Footage field displayed is verified", false, false);
			Assertions.verify(buildingPage.softStoryCharacteristics_No.checkIfElementIsDisplayed(), true,
					"Building Page", "Soft Story characteristics No Radio button displayed is verified", false, false);
			Assertions.verify(buildingPage.softStoryCharacteristics_Yes.checkIfElementIsDisplayed(), true,
					"Building Page", "Soft Story characteristics Yes Radio button displayed is verified", false, false);

			// Click on Building occupancy link
			Assertions.verify(buildingPage.buildingOccupancyLink.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Occupancy Link displayed is verified", false, false);
			buildingPage.buildingOccupancyLink.scrollToElement();
			buildingPage.buildingOccupancyLink.click();
			if (buildingPage.primaryPercentOccupied.checkIfElementIsPresent()
					&& buildingPage.primaryPercentOccupied.checkIfElementIsDisplayed()) {
				Assertions.verify(buildingPage.primaryPercentOccupied.checkIfElementIsDisplayed(), true,
						"Building Page", "Percent Occupied field displayed is verified", false, false);
			}
			if (buildingPage.buildingOccupancy_yes.checkIfElementIsPresent()
					&& buildingPage.buildingOccupancy_yes.checkIfElementIsDisplayed()) {
				Assertions.verify(buildingPage.buildingOccupancy_yes.checkIfElementIsDisplayed(), true, "Building Page",
						"Building Occupancy Yes radio button displayed is verified", false, false);
			}
			if (buildingPage.buildingOccupancy_yes.checkIfElementIsPresent()
					&& buildingPage.buildingOccupancy_yes.checkIfElementIsDisplayed()) {
				Assertions.verify(buildingPage.buildingOccupancy_no.checkIfElementIsDisplayed(), true, "Building Page",
						"Building Occupancy No radio button displayed is verified", false, false);
			}
			Assertions.verify(buildingPage.primaryOccupancy.checkIfElementIsDisplayed(), true, "Building Page",
					"Primary Occupancy Field displayed is verified", false, false);
			Assertions.verify(buildingPage.secondaryOccupancy.checkIfElementIsDisplayed(), true, "Building Page",
					"Secondary Occupancy Filed displayed is verified", false, false);

			// Adding the ticket IO-21087
			Assertions.addInfo("Building Page", "Verifying the secondary occupancy is None");
			Assertions.verify(buildingPage.secondaryOccupancyText.getData(), "None", "Building Page",
					"Secondary Occupancy None is displayed", false, false);
			Assertions.addInfo("Building Page",
					"Verifying the absence of Secondary occupancy percent when secondary occupancy is None");
			Assertions.verify(buildingPage.secondaryPercentOccupied.checkIfElementIsDisplayed(), false, "Building Page",
					"Secondary Occupancy Percent field is not displayed when secondary occupancy is None", false,
					false);

			// Adding the ticket IO-20771
			buildingPage.buildingPageLinks.formatDynamicPath("Secondary Occupancy").scrollToElement();
			buildingPage.buildingPageLinks.formatDynamicPath("Secondary Occupancy").click();
			buildingPage.waitTime(3);
			buildingPage.buildingPageLinks.formatDynamicPath("Secondary Occupancy").click();
			buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Secondary Occupancy")
					.waitTillVisibilityOfElement(60);
			Assertions.verify(
					!buildingPage.buildingPageLinksFlyerText
							.formatDynamicPath("Secondary Occupancy").getData().contains("<ul><li>"),
					true, "Building Page",
					"The Secondary Occupancy Flyer Text "
							+ buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Secondary Occupancy").getData()
							+ " displayed is verified",
					false, false);

			// click on roof details link
			Assertions.verify(buildingPage.roofDetailsLink.checkIfElementIsDisplayed(), true, "Building Page",
					"Roof Details link displayed is verified", false, false);
			buildingPage.waitTime(2);// added waittime to click on roof details link
			buildingPage.roofDetailsLink.waitTillVisibilityOfElement(60);
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			buildingPage.waitTime(3);// Added waittime to check presence roof shape arrow
			buildingPage.roofShapeArrow.waitTillPresenceOfElement(60);
			buildingPage.roofShapeArrow.waitTillElementisEnabled(60);
			buildingPage.roofShapeArrow.waitTillButtonIsClickable(60);
			buildingPage.roofShapeArrow.waitTillVisibilityOfElement(60);
			Assertions.verify(buildingPage.roofShapeArrow.checkIfElementIsDisplayed(), true, "Building Page",
					"Roof shape dropdown displayed is verified", false, false);
			Assertions.verify(buildingPage.roofCladdingArrow.checkIfElementIsDisplayed(), true, "Building Page",
					"Roof Cladding dropdown displayed is verified", false, false);
			Assertions.verify(buildingPage.yearRoofLastReplaced.checkIfElementIsDisplayed(), true, "Building Page",
					"Roof last replaced test field displayed is verified", false, false);

			// Adding the ticket IO-20771
			buildingPage.buildingPageLinks.formatDynamicPath("Roof Cladding").scrollToElement();
			buildingPage.buildingPageLinks.formatDynamicPath("Roof Cladding").click();
			waitTime(3);
			buildingPage.buildingPageLinks.formatDynamicPath("Roof Cladding").click();
			buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Roof Cladding").waitTillVisibilityOfElement(60);
			Assertions.verify(
					!buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Roof Cladding").getData()
							.contains("<ul><li>"),
					true, "Building Page",
					"The Roof Cladding Flyer Text "
							+ buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Roof Cladding").getData()
							+ " displayed is verified",
					false, false);

			// click on Additional info link
			Assertions.verify(buildingPage.additionalInfoLink.checkIfElementIsDisplayed(), true, "Building Page",
					"Additional Building Information link displayed is verified", false, false);
			buildingPage.additionalInfoLink.waitTillVisibilityOfElement(60);
			buildingPage.additionalInfoLink.scrollToElement();
			buildingPage.additionalInfoLink.click();
			buildingPage.buildingSecurityArrow.waitTillPresenceOfElement(60);
			buildingPage.buildingSecurityArrow.waitTillVisibilityOfElement(60);
			Assertions.verify(buildingPage.buildingSecurityArrow.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Security Dropdown displayed is verified", false, false);
			Assertions.verify(buildingPage.fireProtectionArrow.checkIfElementIsDisplayed(), true, "Building Page",
					"Fire Protection dropdown displayed is verified", false, false);
			Assertions.verify(buildingPage.windResistive_No.checkIfElementIsDisplayed(), true, "Building Page",
					"Wind Resistive No radio button displayed is verified", false, false);
			Assertions.verify(buildingPage.windResistive_Yes.checkIfElementIsDisplayed(), true, "Building Page",
					"Wind Resistive Yes radio button displayed is verified", false, false);

			// Adding the ticket IO-20771
			buildingPage.waitTime(2);// if the wait time is removed test case will fail here
			buildingPage.buildingPageLinks.formatDynamicPath("Building Security").scrollToElement();
			buildingPage.buildingPageLinks.formatDynamicPath("Building Security").waitTillButtonIsClickable(60);
			buildingPage.buildingPageLinks.formatDynamicPath("Building Security").click();
			waitTime(3);
			buildingPage.buildingPageLinks.formatDynamicPath("Building Security").click();
			buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Building Security")
					.waitTillVisibilityOfElement(60);
			Assertions.verify(
					!buildingPage.buildingPageLinksFlyerText
							.formatDynamicPath("Building Security").getData().contains("<ul><li>"),
					true, "Building Page",
					"The Building Security Flyer Text "
							+ buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Building Security").getData()
							+ " displayed is verified",
					false, false);

			// Click on Fire protection
			buildingPage.buildingPageLinks.formatDynamicPath("Fire Protection").scrollToElement();
			buildingPage.buildingPageLinks.formatDynamicPath("Fire Protection").click();
			waitTime(3);
			buildingPage.buildingPageLinks.formatDynamicPath("Fire Protection").click();
			buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Fire Protection")
					.waitTillVisibilityOfElement(60);
			Assertions.verify(
					!buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Fire Protection").getData()
							.contains("<ul><li>"),
					true, "Building Page",
					"The Fire Protection Flyer Text "
							+ buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Fire Protection").getData()
							+ " displayed is verified",
					false, false);

			// Click on Wind Resistive
			buildingPage.buildingPageLinks.formatDynamicPath("Wind Resistive").scrollToElement();
			buildingPage.buildingPageLinks.formatDynamicPath("Wind Resistive").click();
			waitTime(3);
			buildingPage.buildingPageLinks.formatDynamicPath("Wind Resistive").click();
			buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Wind Resistive").waitTillVisibilityOfElement(60);
			Assertions.verify(
					!buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Wind Resistive").getData()
							.contains("<ul><li>"),
					true, "Building Page",
					"The Wind Resistive Flyer Text "
							+ buildingPage.buildingPageLinksFlyerText.formatDynamicPath("Wind Resistive").getData()
							+ " displayed is verified",
					false, false);

			// Click Building Values link
			Assertions.verify(buildingPage.buildingValuesLink.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Values link displayed is verified", false, false);
			buildingPage.buildingValuesLink.scrollToElement();
			buildingPage.buildingValuesLink.click();
			buildingPage.waitTime(2);// if the waittime removed the below assertions will fail
			buildingPage.buildingValue.waitTillVisibilityOfElement(60);
			Assertions.verify(buildingPage.buildingValue.checkIfElementIsDisplayed(), true, "Building Page",
					"Building value field displayed is verified ", false, false);
			Assertions.verify(buildingPage.businessPersonalProperty.checkIfElementIsDisplayed(), true, "Building Page",
					"Business Personal Property field displayed is verified", false, false);

			// Goto homepage
			buildingPage.scrollToTopPage();
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 76", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 76", "Executed Successfully");
			}
		}
	}
}
