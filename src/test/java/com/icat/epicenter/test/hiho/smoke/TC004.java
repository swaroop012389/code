/** Program Description:
 *  Author			   : SM Netserv
 *  Date of Creation   : May 2023
 **/

package com.icat.epicenter.test.hiho.smoke;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountDetails;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.test.hiho.AbstractHIHOTest;
import com.icat.epicenter.utils.TestConstants;

public class TC004 extends AbstractHIHOTest {

	public TC004() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/smoke/NBTCID04.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setupData) {
		// Initializing all Objects
		HomePage homePage = new HomePage();
		LocationPage locationPage = new LocationPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		AccountDetails accountDetailsPage = new AccountDetails();
		DwellingPage dwellingPage = new DwellingPage();
		Map<String, String> testData = data.get(0);

		// Getting Location and Building count
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);

		// New account
		Assertions.passTest("Home Page", "New Account created successfully");
		homePage.createNewAccountWithNamedInsured(testData, setupData);
		Assertions.passTest("Home Page", TestConstants.VALUES_ENTERED);

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", TestConstants.PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, locationCount, 1);
		dwellingPage.addRoofDetails(testData, locationCount, 1);
		dwellingPage.enterDwellingValues(testData, locationCount, 1);
		Assertions.passTest("Dwelling Page", TestConstants.VALUES_ENTERED);
		Assertions.verify(dwellingPage.covBNamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
				"Dwelling 1 Cov B not present for Condo " + TestConstants.EXT_RPT_MSG, false, false);

		// Changing 55 Retired from No to Yes
		Assertions.passTest("Account Details Page", TestConstants.PAGE_NAVIGATED);
		dwellingPage.waitTime(2);
		locationPage.accountDetailsLink.waitTillPresenceOfElement(TIME_OUT_SIXTY_SECS);
		locationPage.accountDetailsLink.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		locationPage.accountDetailsLink.waitTillElementisEnabled(TIME_OUT_SIXTY_SECS);
		locationPage.accountDetailsLink.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		locationPage.accountDetailsLink.scrollToElement();
		locationPage.accountDetailsLink.click();
		testData = data.get(1);  // data value 2
		accountDetailsPage.save.waitTillPresenceOfElement(TIME_OUT_SIXTY_SECS);
		accountDetailsPage.save.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		accountDetailsPage.save.waitTillElementisEnabled(TIME_OUT_SIXTY_SECS);
		accountDetailsPage.save.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		accountDetailsPage.save.scrollToElement();
		accountDetailsPage.save.click();
		accountDetailsPage.reviewButton.waitTillPresenceOfElement(TIME_OUT_SIXTY_SECS);
		accountDetailsPage.reviewButton.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		accountDetailsPage.reviewButton.waitTillElementisEnabled(TIME_OUT_SIXTY_SECS);
		accountDetailsPage.reviewButton.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		accountDetailsPage.reviewButton.scrollToElement();
		accountDetailsPage.reviewButton.click();
		accountDetailsPage.reviewButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		Assertions.passTest("Account Details Page", TestConstants.VALUES_UPDATED);
		accountOverviewPage.locationStep1.click();

		// Verifying Building Saved successfully message
		Assertions.passTest("Dwelling Page", TestConstants.PAGE_NAVIGATED);
		dwellingPage.dwellingLink.click();
		dwellingPage.editBuilding.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.editBuilding.scrollToElement();
		dwellingPage.editBuilding.click();
		dwellingPage.save.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.save.scrollToElement();
		dwellingPage.save.click();
		Assertions.verify(dwellingPage.buildingSavedSuccessfully.checkIfElementIsDisplayed(), true, "Dwelling Page",
				"Building saved successfully message" + TestConstants.EXT_RPT_MSG, false, false);
		dwellingPage.addSymbol.scrollToElement();
		dwellingPage.addSymbol.click();
		dwellingPage.addNewDwelling.scrollToElement();
		dwellingPage.addNewDwelling.click();
		dwellingPage.addNewDwelling.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		Assertions.passTest("Dwelling Page", "Dwelling 2 Added successfully");

		// Dwelling 2 empty fields verification for Dwelling Details
		Assertions.verify(dwellingPage.address.getAttrributeValue("value"), "", "Dwelling Page",
				"Dwelling 2 address is empty" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.propertyDescription.getAttrributeValue("value"), "", "Dwelling Page",
				"Dwelling 2 Property description is empty" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.dwellingType.getData(), "Please select", "Dwelling Page",
				"Dwelling 2 Dwelling Type is empty" + TestConstants.EXT_RPT_MSG, false, false);
		if (dwellingPage.dwellingCharacteristicsLink.checkIfElementIsPresent()
				&& dwellingPage.dwellingCharacteristicsLink.checkIfElementIsDisplayed()) {
			dwellingPage.dwellingCharacteristicsLink.scrollToElement();
			dwellingPage.dwellingCharacteristicsLink.click();
		}
		Assertions.verify(dwellingPage.constructionType.getData(), "Please select", "Dwelling Page",
				"Dwelling 2 Construction Type is empty" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.yearBuilt.getData(), "", "Dwelling Page",
				"Dwelling 2 Year Built is empty" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.livingSquareFootage.getData(), "", "Dwelling Page",
				"Dwelling 2 Living Square Footage is empty" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.nonLivingSquareFootage.getData(), "", "Dwelling Page",
				"Dwelling 2 Non Living Square Footage is empty" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.numOfFloors.getData(), "", "Dwelling Page",
				"Dwelling 2 Number of Floors is empty " + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.inspectionAvailable_no.getData(), "", "Dwelling Page",
				"Dwelling 2 Inspection available from Primary Carrier is empty" + TestConstants.EXT_RPT_MSG, false, false);

		// Dwelling 2 empty fields verification for Roof Details
		dwellingPage.waitTime(2);
		dwellingPage.roofDetailsLink.waitTillPresenceOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.roofDetailsLink.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.roofDetailsLink.waitTillElementisEnabled(TIME_OUT_SIXTY_SECS);
		dwellingPage.roofDetailsLink.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		dwellingPage.roofDetailsLink.scrollToElement();
		dwellingPage.roofDetailsLink.click();
		Assertions.verify(dwellingPage.roofShape.getData(), "Please select", "Dwelling Page",
				"Dwelling 2 Roof Shape is empty" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.yearRoofLastReplaced.getData(), "", "Dwelling Page",
				"Dwelling 2 Year Roof Last replaced is empty" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.windMitigationData.getData(), "Please select", "Dwelling Page",
				"Dwelling 2 Wind Mitigation is empty" + TestConstants.EXT_RPT_MSG, false, false);

		// Dwelling 2 empty fields verification for Dwelling Values Details
		dwellingPage.waitTime(2);
		dwellingPage.dwellingValuesLink.scrollToElement();
		dwellingPage.dwellingValuesLink.click();
		Assertions.verify(dwellingPage.covANamedHurricane.getData(), "", "Dwelling Page",
				"Dwelling 2 Cov A is empty" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.passTest("Dwelling Page", TestConstants.VALUES_VERIFIED);

		// Entering Details for Dwelling 2
		testData = data.get(0);  // value 1
		dwellingPage.scrollToTopPage();
		dwellingPage.dwellingDetailsLink.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.dwellingDetailsLink.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		dwellingPage.dwellingDetailsLink.waitTillPresenceOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.dwellingDetailsLink.moveToElement();
		dwellingPage.dwellingDetailsLink.scrollToElement();
		dwellingPage.waitTime(3); // added waittime to work in headless mode
		dwellingPage.dwellingDetailsLink.click();
		dwellingPage.manualEntry.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.addDwellingDetails(testData, locationCount, 2);
		dwellingPage.addRoofDetails(testData, locationCount, 2);
		dwellingPage.enterDwellingValues(testData, locationCount, 2);
		Assertions.passTest("Dwelling Page", TestConstants.VALUES_ENTERED);
		Assertions.verify(dwellingPage.covBNamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
				"Dwelling 2 Cov B not present for condo " + TestConstants.EXT_RPT_MSG, false, false);
		dwellingPage.waitTime(3);
		dwellingPage.save.scrollToElement();
		dwellingPage.save.click();
		Assertions.verify(
				dwellingPage.buildingSavedSuccessfully.checkIfElementIsPresent()
						|| dwellingPage.buildingSavedSuccessfully.checkIfElementIsDisplayed(),
				true, "Dwelling Page", "Building saved successfully message" + TestConstants.EXT_RPT_MSG, false, false);

		// Copy Dwelling 2
		dwellingPage.copySymbol.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.copySymbol.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		dwellingPage.copySymbol.scrollToElement();
		dwellingPage.copySymbol.click();
		Assertions.passTest("Dwelling Page", "Copied Dwelling 2 Successfully");
		Assertions.passTest("Dwelling Page", "Dwelling 3 values verification started");

		// Verifying Dwelling 3 values same as Dwelling 2 for Dwelling Details
		dwellingPage.manualEntry.scrollToElement();
		dwellingPage.manualEntry.click();
		dwellingPage.manualEntry.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		Assertions.verify(dwellingPage.manualEntryAddress.getData(), testData.get("L1D2-DwellingAddress"),
				"Dwelling Page", "Dwelling address is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.manualEntryCity.getData(), testData.get("L1D2-DwellingCity"), "Dwelling Page",
				"Dwelling City is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.manualEntryZipCode.getData(), testData.get("L1D2-DwellingZIP"), "Dwelling Page",
				"Dwelling Zipcode is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.dwellingType.getData(), testData.get("L1D2-DwellingType"), "Dwelling Page",
				"Dwelling Type is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		if (dwellingPage.dwellingCharacteristicsLink.checkIfElementIsPresent()
				&& dwellingPage.dwellingCharacteristicsLink.checkIfElementIsDisplayed()) {
			dwellingPage.dwellingCharacteristicsLink.scrollToElement();
			dwellingPage.dwellingCharacteristicsLink.click();
		}
		Assertions.verify(dwellingPage.constructionType.getData(), testData.get("L1D2-DwellingConstType"), "Dwelling Page",
				"Construction Type is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.yearBuilt.getData(), testData.get("L1D2-DwellingYearBuilt"), "Dwelling Page",
				"Year Built is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.livingSquareFootage.getData(), testData.get("L1D2-DwellingSqFoot"),
				"Dwelling Page", "Living Square Footage is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.nonLivingSquareFootage.getData(), testData.get("L1D2-DwellingNonLivingSqFoot"),
				"Dwelling Page", "Non Living Square Footage is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.numOfFloors.getAttrributeValue("value"), testData.get("L1D2-DwellingFloors"),
				"Dwelling Page", "Number of Floors is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.inspectionAvailable_no.getAttrributeValue("value"),
				testData.get("L1D2-InspfromPrimaryCarrier"), "Dwelling Page",
				"Inspection available from Primary Carrier is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);

		// Verifying Dwelling 3 values same as Dwelling 2 for Roof Details
		dwellingPage.waitTime(3);
		dwellingPage.roofDetailsLink.waitTillPresenceOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.roofDetailsLink.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.roofDetailsLink.waitTillElementisEnabled(TIME_OUT_SIXTY_SECS);
		dwellingPage.roofDetailsLink.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		dwellingPage.roofDetailsLink.scrollToElement();
		dwellingPage.roofDetailsLink.click();
		dwellingPage.roofShape.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.roofShape.scrollToElement();
		Assertions.verify(dwellingPage.roofShape.getData(), testData.get("L1D2-DwellingRoofShape"), "Dwelling Page",
				"Roof Shape is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.yearRoofLastReplaced.getData(), testData.get("L1D2-DwellingRoofReplacedYear"),
				"Dwelling Page", "Year Roof Last replaced is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		dwellingPage.windMitigationData.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.windMitigationData.scrollToElement();
		Assertions.verify(dwellingPage.windMitigationData.getData(), testData.get("L1D2-DwellingWindMitigation"),
				"Dwelling Page", "Wind Mitigation is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);

		// Verifying Dwelling 3 values same as Dwelling 2 for Dwelling Value
		// Details.
		dwellingPage.waitTime(3);
		dwellingPage.dwellingValuesLink.scrollToElement();
		dwellingPage.dwellingValuesLink.click();
		Assertions.verify(dwellingPage.covANamedHurricane.getData(), testData.get("L1D2-DwellingCovA"), "Dwelling Page",
				"Cov A is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.covCNamedHurricane.getData(), testData.get("L1D2-DwellingCovC"), "Dwelling Page",
				"Cov C is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.covDNamedHurricane.getData(), testData.get("L1D2-DwellingCovD"), "Dwelling Page",
				"Cov D is same as Dwelling 2" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.passTest("Dwelling Page", "Dwelling 3 values verification ended");
		Assertions.passTest("Dwelling Page", TestConstants.VALUES_VERIFIED);
		dwellingPage.waitTime(3);// adding hard coded waitTIme
		dwellingPage.reviewDwelling.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		dwellingPage.reviewDwelling.waitTillElementisEnabled(TIME_OUT_SIXTY_SECS);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// $ symbol verification for all three building-TO DO--------------
		dwellingPage.coveragesStep2.click();
		Assertions.passTest("Create Quote Page", TestConstants.PAGE_NAVIGATED);
		Assertions.verify(createQuotePage.pageName.getData(), testData.get("InsuredName") + ": Create a Quote",
				"Create Quote Page", "Page Name" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(createQuotePage.moldCleanup.getData(), testData.get("Mold"), "Create Quote Page",
				"Mold Clean Up default value" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(createQuotePage.moldCleanup.getAttrributeValue("unselectable"), "on", "Create Quote Page",
				"Mold Clean Up is unchangeable" + TestConstants.EXT_RPT_MSG, false, false);

		// Entering Create quote page Details
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", TestConstants.VALUES_UPDATED);

		// Create Quote Page Grid is verified
		testData = data.get(0); // data value 1
		for (int i = 0; i < 3; i++) {
			Assertions.addInfo("<span class='group'> Create Quote Page</span>",
					"<span class='group'> Location 1 - Dwelling " + (i + 1) + "</span>");
			char ch = 'A';
			for (int j = 2; j <= 5; j++) {
				if (!testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).equals("")) {
					BaseWebElementControl namedHurricane = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]//input"));
					Assertions.verify(namedHurricane.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page",
							"Cov " + ch + " value " + " : Named Hurricane - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false,
							false);
					BaseWebElementControl earthquake = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
					Assertions.verify(earthquake.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page",
							"Cov " + ch + " value " + " : Earthquake - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false,
							false);
					BaseWebElementControl flood = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
					Assertions.verify(flood.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Flood - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false, false);
					ch++;
				} else {
					BaseWebElementControl namedHurricane = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]"));
					Assertions.verify(namedHurricane.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Named Hurricane - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false,
							false);
					BaseWebElementControl earthquake = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]"));
					Assertions.verify(earthquake.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Earthquake - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false,
							false);
					BaseWebElementControl flood = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
					Assertions.verify(flood.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Flood - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false, false);
					ch++;
				}
			}
		}
		createQuotePage.previous.scrollToElement();
		createQuotePage.previous.click();
		createQuotePage.previous.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Deleting Dwelling 3
		Assertions.passTest("Account Overview Page", TestConstants.PAGE_NAVIGATED);
		accountOverviewPage.dwelling3.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		accountOverviewPage.dwelling3.scrollToElement();
		accountOverviewPage.dwelling3.click();
		accountOverviewPage.deleteDwelling3.scrollToElement();
		accountOverviewPage.deleteDwelling3.click();
		accountOverviewPage.yesDeleteBuilding.click();
		accountOverviewPage.yesDeleteBuilding.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		Assertions.verify(accountOverviewPage.dwellingSuccessfullyDeletedMsg.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Dwelling 3 Deleted successfully Message" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.passTest("Account Overview Page", TestConstants.VALUES_VERIFIED);

		// Click on account account
		accountOverviewPage.quoteAccountButton.click();
		accountOverviewPage.quoteAccountButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", TestConstants.PAGE_NAVIGATED);
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", TestConstants.VALUES_UPDATED);

		// Create Quote Page Grid is verified
		for (int i = 0; i < 2; i++) {
			Assertions.addInfo("<span class='group'> Create Quote Page</span>",
					"<span class='group'> Location 1 - Dwelling " + (i + 1) + "</span>");
			char ch = 'A';
			for (int j = 2; j <= 5; j++) {
				if (!testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).equals("")) {
					BaseWebElementControl namedHurricane = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]//input"));
					Assertions.verify(namedHurricane.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page",
							"Cov " + ch + " value " + " : Named Hurricane - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false,
							false);
					BaseWebElementControl earthquake = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
					Assertions.verify(earthquake.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page",
							"Cov " + ch + " value " + " : Earthquake - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false,
							false);
					BaseWebElementControl flood = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
					Assertions.verify(flood.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Flood - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false, false);
					ch++;
				} else {
					BaseWebElementControl namedHurricane = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]"));
					Assertions.verify(namedHurricane.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Named Hurricane - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false,
							false);
					BaseWebElementControl earthquake = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]"));
					Assertions.verify(earthquake.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Earthquake - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false,
							false);
					BaseWebElementControl flood = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
					Assertions.verify(flood.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Flood - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false, false);
					ch++;
				}
			}
		}
		Assertions.verify(createQuotePage.locDwellingLink.checkIfElementIsPresent(), false, "Create Quote Page",
				"Deleted dwelling (1-3) is not listed in the insured values under Create quote page " + TestConstants.EXT_RPT_MSG,
				false, false);
		createQuotePage.quoteStep3.click();

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		Assertions.passTest("Account Overview Page", TestConstants.PAGE_NAVIGATED);
		String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12); // added by Kent; quote number wasn't be set
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
		Assertions.verify(accountOverviewPage.pageName.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Name present", true, true);
		Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Total Premium and Fee Amount present", true, true);
		Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Producer Number present", true, true);
		if (locationCount >= 2) {
			Assertions.verify(accountOverviewPage.quoteSomeDwellingsButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Create Another Quote present", true, true);
		} else {
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Create Another Quote present", true, true);
		}
		Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Edit Deductibles and limits button present", true, true);
		Assertions.verify(accountOverviewPage.quoteStatus.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote Status present", true, true);
		Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "View/Print Full Quote Link present", true, true);
		Assertions.verify(accountOverviewPage.emailQuoteLink.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Email Quote Link present", true, true);
		Assertions.verify(accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Override Premium Link present", true, true);
		Assertions.verify(accountOverviewPage.viewOrPrintRateTrace.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "View/Print Rate Trace Link present", true, true);
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Bind button present is verified", true, true);
		accountOverviewPage.deleteAccount.scrollToElement();
		accountOverviewPage.deleteAccount.click();
		accountOverviewPage.yesDeletePopup.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		accountOverviewPage.yesDeletePopup.scrollToElement();
		accountOverviewPage.yesDeletePopup.click();
		Assertions.passTest("Account Overview Page", "Account deleted successfully");

		// Signing out
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		Assertions.passTest("Smoke Testing TC004", "Executed Successfully");
	}
}