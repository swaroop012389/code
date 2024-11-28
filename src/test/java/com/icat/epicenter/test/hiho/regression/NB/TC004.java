/** Program Description: To generate a HIHO policy with Single location multiple dwellings and assert values.
 *  Author			   : SM Netserv
 *  Date of Creation   : June 2018
 **/
package com.icat.epicenter.test.hiho.regression.NB;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountDetails;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC004 extends AbstractNAHOTest {

	public TC004() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID04.xls";
	}

	public HomePage homePage;
	public DwellingPage dwellingPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public BindRequestSubmittedPage bindRequestPage;
	public ConfirmBindRequestPage confirmBindrequestPage;
	public ReferralPage referralPage;
	public PolicySummaryPage policySummaryPage;
	public BuildingUnderMinimumCostPage dwellingCost;
	public LocationPage locationPage;
	public AccountDetails accountDetailsPage;
	public Map<String, String> testData;
	public BasePageControl basePage;
	String quoteNumber;
	String policyNumber;
	static final String ext_rpt_msg = " is verified";
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_UPDATED = "Values Updated";
	static final String VALUES_VERIFIED = "Values Verified";
	static final String VALUES_ENTERED = "Values Entered successfully";
	static int data_Value1 = 0;
	static int data_Value2 = 1;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		locationPage = new LocationPage();
		dwellingCost = new BuildingUnderMinimumCostPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		accountDetailsPage = new AccountDetails();
		policySummaryPage = new PolicySummaryPage();
		requestBindPage = new RequestBindPage();
		bindRequestPage = new BindRequestSubmittedPage();
		confirmBindrequestPage = new ConfirmBindRequestPage();
		dwellingPage = new DwellingPage();
		testData = data.get(data_Value1);

		// Getting Location and Building count
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);

		// New account
		Assertions.passTest("Home Page", "New Account created successfully");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, locationCount, 1);
		dwellingPage.addRoofDetails(testData, locationCount, 1);
		dwellingPage.enterDwellingValues(testData, locationCount, 1);
		Assertions.passTest("Dwelling Page", VALUES_ENTERED);
		Assertions.verify(dwellingPage.covBNamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
				"Dwelling 1 Cov B not present for Condo " + ext_rpt_msg, false, false);

		// Changing 55 Retired from No to Yes
		Assertions.passTest("Account Details Page", PAGE_NAVIGATED);
		dwellingPage.waitTime(2);
		locationPage.accountDetailsLink.waitTillPresenceOfElement(60);
		locationPage.accountDetailsLink.waitTillVisibilityOfElement(60);
		locationPage.accountDetailsLink.waitTillElementisEnabled(60);
		locationPage.accountDetailsLink.waitTillButtonIsClickable(60);
		locationPage.accountDetailsLink.scrollToElement();
		locationPage.accountDetailsLink.click();
		testData = data.get(data_Value2);
		accountDetailsPage.save.waitTillPresenceOfElement(60);
		accountDetailsPage.save.waitTillVisibilityOfElement(60);
		accountDetailsPage.save.waitTillElementisEnabled(60);
		accountDetailsPage.save.waitTillButtonIsClickable(60);
		accountDetailsPage.save.scrollToElement();
		accountDetailsPage.save.click();
		accountDetailsPage.reviewButton.waitTillPresenceOfElement(60);
		accountDetailsPage.reviewButton.waitTillVisibilityOfElement(60);
		accountDetailsPage.reviewButton.waitTillElementisEnabled(60);
		accountDetailsPage.reviewButton.waitTillButtonIsClickable(60);
		accountDetailsPage.reviewButton.scrollToElement();
		accountDetailsPage.reviewButton.click();
		accountDetailsPage.reviewButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Account Details Page", VALUES_UPDATED);
		accountOverviewPage.locationStep1.click();

		// Verifying Building Saved successfully message
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.dwelling1.click();
		dwellingPage.editBuilding.waitTillVisibilityOfElement(60);
		dwellingPage.editBuilding.scrollToElement();
		dwellingPage.editBuilding.click();
		dwellingPage.save.waitTillVisibilityOfElement(60);
		dwellingPage.save.scrollToElement();
		dwellingPage.save.click();
		Assertions.verify(dwellingPage.buildingSavedSuccessfully.checkIfElementIsDisplayed(), true, "Dwelling Page",
				"Building saved successfully message" + ext_rpt_msg, false, false);
		dwellingPage.addSymbol.scrollToElement();
		dwellingPage.addSymbol.click();
		dwellingPage.addNewDwelling.scrollToElement();
		dwellingPage.addNewDwelling.click();
		dwellingPage.addNewDwelling.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Dwelling Page", "Dwelling 2 Added successfully");

		// Dwelling 2 empty fields verification for Dwelling Details
		Assertions.verify(dwellingPage.address.getAttrributeValue("value"), "", "Dwelling Page",
				"Dwelling 2 address is empty" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.propertyDescription.getAttrributeValue("value"), "", "Dwelling Page",
				"Dwelling 2 Property description is empty" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.dwelType.getData(), "Please select", "Dwelling Page",
				"Dwelling 2 Dwelling Type is empty" + ext_rpt_msg, false, false);
		if (dwellingPage.dwellingCharacteristicsLink.checkIfElementIsPresent()
				&& dwellingPage.dwellingCharacteristicsLink.checkIfElementIsDisplayed()) {
			dwellingPage.dwellingCharacteristicsLink.scrollToElement();
			dwellingPage.dwellingCharacteristicsLink.click();
		}
		Assertions.verify(dwellingPage.constType.getData(), "Please select", "Dwelling Page",
				"Dwelling 2 Construction Type is empty" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.yearBuilt.getData(), "", "Dwelling Page",
				"Dwelling 2 Year Built is empty" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.livingSquareFootage.getData(), "", "Dwelling Page",
				"Dwelling 2 Living Square Footage is empty" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.nonLivingSquareFootage.getData(), "", "Dwelling Page",
				"Dwelling 2 Non Living Square Footage is empty" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.numOfFloors.getData(), "", "Dwelling Page",
				"Dwelling 2 Number of Floors is empty " + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.inspectionAvailable_no.getData(), "", "Dwelling Page",
				"Dwelling 2 Inspection available from Primary Carrier is empty" + ext_rpt_msg, false, false);

		// Dwelling 2 empty fields verification for Roof Details
		dwellingPage.waitTime(2);
		dwellingPage.roofDetailsLink.waitTillPresenceOfElement(60);
		dwellingPage.roofDetailsLink.waitTillVisibilityOfElement(60);
		dwellingPage.roofDetailsLink.waitTillElementisEnabled(60);
		dwellingPage.roofDetailsLink.waitTillButtonIsClickable(60);
		dwellingPage.roofDetailsLink.scrollToElement();
		dwellingPage.roofDetailsLink.click();
		Assertions.verify(dwellingPage.roofShape.getData(), "Please select", "Dwelling Page",
				"Dwelling 2 Roof Shape is empty" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.yearRoofLastReplaced.getData(), "", "Dwelling Page",
				"Dwelling 2 Year Roof Last replaced is empty" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.windMitigation1.getData(), "Please select", "Dwelling Page",
				"Dwelling 2 Wind Mitigation is empty" + ext_rpt_msg, false, false);

		// Dwelling 2 empty fields verification for Dwelling Values Details
		dwellingPage.waitTime(2);
		dwellingPage.dwellingValuesLink.scrollToElement();
		dwellingPage.dwellingValuesLink.click();
		Assertions.verify(dwellingPage.covANamedHurricane.getData(), "", "Dwelling Page",
				"Dwelling 2 Cov A is empty" + ext_rpt_msg, false, false);
		Assertions.passTest("Dwelling Page", VALUES_VERIFIED);

		// Entering Details for Dwelling 2
		testData = data.get(data_Value1);
		dwellingPage.scrollToTopPage();
		dwellingPage.dwellingDetailsLink.waitTillVisibilityOfElement(60);
		dwellingPage.dwellingDetailsLink.waitTillButtonIsClickable(60);
		dwellingPage.dwellingDetailsLink.waitTillPresenceOfElement(60);
		dwellingPage.dwellingDetailsLink.moveToElement();
		dwellingPage.dwellingDetailsLink.scrollToElement();
		dwellingPage.waitTime(3); // added waittime to work in headless mode
		dwellingPage.dwellingDetailsLink.click();
		dwellingPage.manualEntry.waitTillVisibilityOfElement(60);
		dwellingPage.addDwellingDetails(testData, locationCount, 2);
		dwellingPage.addRoofDetails(testData, locationCount, 2);
		dwellingPage.enterDwellingValues(testData, locationCount, 2);
		Assertions.passTest("Dwelling Page", VALUES_ENTERED);
		Assertions.verify(dwellingPage.covBNamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
				"Dwelling 2 Cov B not present for condo " + ext_rpt_msg, false, false);
		dwellingPage.waitTime(3);
		dwellingPage.save.scrollToElement();
		dwellingPage.save.click();
		Assertions.verify(
				dwellingPage.buildingSavedSuccessfully.checkIfElementIsPresent()
						|| dwellingPage.buildingSavedSuccessfully.checkIfElementIsDisplayed(),
				true, "Dwelling Page", "Building saved successfully message" + ext_rpt_msg, false, false);

		// Copy Dwelling 2
		dwellingPage.copySymbol.waitTillVisibilityOfElement(60);
		dwellingPage.copySymbol.waitTillButtonIsClickable(60);
		dwellingPage.copySymbol.scrollToElement();
		dwellingPage.copySymbol.click();
		Assertions.passTest("Dwelling Page", "Copied Dwelling 2 Successfully");
		Assertions.passTest("Dwelling Page", "Dwelling 3 values verification started");

		// Verifying Dwelling 3 values same as Dwelling 2 for Dwelling Details
		dwellingPage.manualEntry.scrollToElement();
		dwellingPage.manualEntry.click();
		dwellingPage.manualEntry.waitTillInVisibilityOfElement(60);
		Assertions.verify(dwellingPage.manualEntryAddress.getData(), testData.get("L1D2-DwellingAddress"),
				"Dwelling Page", "Dwelling address is same as Dwelling 2" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.manualEntryCity.getData(), testData.get("L1D2-DwellingCity"), "Dwelling Page",
				"Dwelling City is same as Dwelling 2" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.manualEntryZipCode.getData(), testData.get("L1D2-DwellingZIP"), "Dwelling Page",
				"Dwelling Zipcode is same as Dwelling 2" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.dwelType.getData(), testData.get("L1D2-DwellingType"), "Dwelling Page",
				"Dwelling Type is same as Dwelling 2" + ext_rpt_msg, false, false);
		if (dwellingPage.dwellingCharacteristicsLink.checkIfElementIsPresent()
				&& dwellingPage.dwellingCharacteristicsLink.checkIfElementIsDisplayed()) {
			dwellingPage.dwellingCharacteristicsLink.scrollToElement();
			dwellingPage.dwellingCharacteristicsLink.click();
		}
		Assertions.verify(dwellingPage.constType.getData(), testData.get("L1D2-DwellingConstType"), "Dwelling Page",
				"Construction Type is same as Dwelling 2" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.yearBuilt.getData(), testData.get("L1D2-DwellingYearBuilt"), "Dwelling Page",
				"Year Built is same as Dwelling 2" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.livingSquareFootage.getData(), testData.get("L1D2-DwellingSqFoot"),
				"Dwelling Page", "Living Square Footage is same as Dwelling 2" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.nonLivingSquareFootage.getData(), testData.get("L1D2-DwellingNonLivingSqFoot"),
				"Dwelling Page", "Non Living Square Footage is same as Dwelling 2" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.numOfFloors.getAttrributeValue("value"), testData.get("L1D2-DwellingFloors"),
				"Dwelling Page", "Number of Floors is same as Dwelling 2" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.inspectionAvailable_no.getAttrributeValue("value"),
				testData.get("L1D2-InspfromPrimaryCarrier"), "Dwelling Page",
				"Inspection available from Primary Carrier is same as Dwelling 2" + ext_rpt_msg, false, false);

		// Verifying Dwelling 3 values same as Dwelling 2 for Roof Details
		dwellingPage.waitTime(3);
		dwellingPage.roofDetailsLink.waitTillPresenceOfElement(60);
		dwellingPage.roofDetailsLink.waitTillVisibilityOfElement(60);
		dwellingPage.roofDetailsLink.waitTillElementisEnabled(60);
		dwellingPage.roofDetailsLink.waitTillButtonIsClickable(60);
		dwellingPage.roofDetailsLink.scrollToElement();
		dwellingPage.roofDetailsLink.click();
		dwellingPage.roofShape.waitTillVisibilityOfElement(60);
		dwellingPage.roofShape.scrollToElement();
		Assertions.verify(dwellingPage.roofShape.getData(), testData.get("L1D2-DwellingRoofShape"), "Dwelling Page",
				"Roof Shape is same as Dwelling 2" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.yearRoofLastReplaced.getData(), testData.get("L1D2-DwellingRoofReplacedYear"),
				"Dwelling Page", "Year Roof Last replaced is same as Dwelling 2" + ext_rpt_msg, false, false);
		dwellingPage.windMitigation1.waitTillVisibilityOfElement(60);
		dwellingPage.windMitigation1.scrollToElement();
		Assertions.verify(dwellingPage.windMitigation1.getData(), testData.get("L1D2-DwellingWindMitigation"),
				"Dwelling Page", "Wind Mitigation is same as Dwelling 2" + ext_rpt_msg, false, false);

		// Verifying Dwelling 3 values same as Dwelling 2 for Dwelling Value
		// Details.
		dwellingPage.waitTime(3);
		dwellingPage.dwellingValuesLink.scrollToElement();
		dwellingPage.dwellingValuesLink.click();
		Assertions.verify(dwellingPage.covANamedHurricane.getData(), testData.get("L1D2-DwellingCovA"), "Dwelling Page",
				"Cov A is same as Dwelling 2" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.covCNamedHurricane.getData(), testData.get("L1D2-DwellingCovC"), "Dwelling Page",
				"Cov C is same as Dwelling 2" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.covDNamedHurricane.getData(), testData.get("L1D2-DwellingCovD"), "Dwelling Page",
				"Cov D is same as Dwelling 2" + ext_rpt_msg, false, false);
		Assertions.passTest("Dwelling Page", "Dwelling 3 values verification ended");
		Assertions.passTest("Dwelling Page", VALUES_VERIFIED);
		dwellingPage.waitTime(3);// adding hard coded waitTIme
		dwellingPage.reviewDwelling.waitTillVisibilityOfElement(60);
		dwellingPage.reviewDwelling.waitTillElementisEnabled(60);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(60);

		// $ symbol verification for all three building-TO DO--------------
		dwellingPage.coveragesStep2.click();
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);
		Assertions.verify(createQuotePage.pageName.getData(), testData.get("InsuredName") + ": Create a Quote",
				"Create Quote Page", "Page Name" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.moldCleanup.getData(), testData.get("Mold"), "Create Quote Page",
				"Mold Clean Up default value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.moldCleanup.getAttrributeValue("unselectable"), "on", "Create Quote Page",
				"Mold Clean Up is unchangeable" + ext_rpt_msg, false, false);

		// Entering Create quote page Details
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", VALUES_UPDATED);

		// Create Quote Page Grid is verified
		testData = data.get(data_Value1);
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
							"Cov " + ch + " value " + " : Named Hurricane - Dwelling " + (i + 1) + ext_rpt_msg, false,
							false);
					BaseWebElementControl earthquake = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
					Assertions.verify(earthquake.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page",
							"Cov " + ch + " value " + " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg, false,
							false);
					BaseWebElementControl flood = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
					Assertions.verify(flood.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Flood - Dwelling " + (i + 1) + ext_rpt_msg, false, false);
					ch++;
				} else {
					BaseWebElementControl namedHurricane = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]"));
					Assertions.verify(namedHurricane.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Named Hurricane - Dwelling " + (i + 1) + ext_rpt_msg, false,
							false);
					BaseWebElementControl earthquake = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]"));
					Assertions.verify(earthquake.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg, false,
							false);
					BaseWebElementControl flood = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
					Assertions.verify(flood.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Flood - Dwelling " + (i + 1) + ext_rpt_msg, false, false);
					ch++;
				}
			}
		}
		createQuotePage.previous.scrollToElement();
		createQuotePage.previous.click();
		createQuotePage.previous.waitTillInVisibilityOfElement(60);

		// Deleting Dwelling 3
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);
		accountOverviewPage.dwelling3.waitTillVisibilityOfElement(60);
		accountOverviewPage.dwelling3.scrollToElement();
		accountOverviewPage.dwelling3.click();
		accountOverviewPage.deleteDwelling3HIHO.scrollToElement();
		accountOverviewPage.deleteDwelling3HIHO.click();
		accountOverviewPage.yesDeleteBuilding.click();
		accountOverviewPage.yesDeleteBuilding.waitTillInVisibilityOfElement(60);
		Assertions.verify(accountOverviewPage.dwellingSuccessfullyDeletedMsg.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Dwelling 3 Deleted successfully Message" + ext_rpt_msg, false, false);
		Assertions.passTest("Account Overview Page", VALUES_VERIFIED);

		// Click on account account
		accountOverviewPage.quoteAccountButton.click();
		accountOverviewPage.quoteAccountButton.waitTillInVisibilityOfElement(60);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", VALUES_UPDATED);

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
							"Cov " + ch + " value " + " : Named Hurricane - Dwelling " + (i + 1) + ext_rpt_msg, false,
							false);
					BaseWebElementControl earthquake = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
					Assertions.verify(earthquake.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page",
							"Cov " + ch + " value " + " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg, false,
							false);
					BaseWebElementControl flood = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
					Assertions.verify(flood.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Flood - Dwelling " + (i + 1) + ext_rpt_msg, false, false);
					ch++;
				} else {
					BaseWebElementControl namedHurricane = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]"));
					Assertions.verify(namedHurricane.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Named Hurricane - Dwelling " + (i + 1) + ext_rpt_msg, false,
							false);
					BaseWebElementControl earthquake = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]"));
					Assertions.verify(earthquake.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg, false,
							false);
					BaseWebElementControl flood = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
					Assertions.verify(flood.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + " : Flood - Dwelling " + (i + 1) + ext_rpt_msg, false, false);
					ch++;
				}
			}
		}
		Assertions.verify(createQuotePage.locDwellingLink.checkIfElementIsPresent(), false, "Create Quote Page",
				"Deleted dwelling (1-3) is not listed in the insured values under Create quote page " + ext_rpt_msg,
				false, false);
		createQuotePage.quoteStep3.click();

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Click on Request Bind
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);
		String accountTotalPremium = accountOverviewPage.totalPremiumValue.getData();
		String accountQuoteNumber = accountOverviewPage.acntOverviewQuoteNumber.getData();
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, accountQuoteNumber);

		// Verifying quote Number,name and premium same as account Overview page
		// in Request Bind Page
		Assertions.passTest("Request Bind Page", PAGE_NAVIGATED);
		Assertions.verify(requestBindPage.quoteNumber.getData().contains(requestBindPage.quoteNumber.getData()),
				accountQuoteNumber.contains(requestBindPage.quoteNumber.getData()), "Request Bind Page",
				"Quote Number same as in Account Overview Page" + ext_rpt_msg, false, false);
		Assertions.verify(
				requestBindPage.requestBindQuoteName.toString()
						.contains(accountOverviewPage.acntOverviewQuoteName.getData()),
				accountOverviewPage.acntOverviewQuoteName.toString()
						.contains(accountOverviewPage.acntOverviewQuoteName.getData()),
				"Request Bind Page", "Quote Name same as in Account Overview Page" + ext_rpt_msg, false, false);
		Assertions.verify(accountTotalPremium.contains(requestBindPage.quotePremium.getData()), true,
				"Request Bind Page", "Quote Premium same as in Account Overview Page" + ext_rpt_msg, false, false);
		Assertions.verify(requestBindPage.effectiveDate.getData(), testData.get("PolicyEffDate"), "Request Bind Page",
				"Effective Date" + ext_rpt_msg, false, false);
		Assertions.verify(requestBindPage.namedInsured.getData(), testData.get("InsuredName"), "Request Bind Page",
				"Insured Name" + ext_rpt_msg, false, false);
		Assertions.passTest("Request Bind Page", VALUES_VERIFIED);

		// Getting Quote Number
		quoteNumber = requestBindPage.quoteNumber.getData();
		Assertions.passTest("Request Bind Page", "Quote Number :  " + quoteNumber);

		// Entering Request Bind Page Details
		requestBindPage.enterPolicyDetailsNAHO(testData);
		requestBindPage.enterPaymentInformationNAHO(testData);
		requestBindPage.addInspectionContact(testData);
		requestBindPage.addContactInformation(testData);
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		Assertions.passTest("Request Bind Page", VALUES_VERIFIED);

		// Verifying details in Confirm Bind
		requestBindPage.requestBind.waitTillVisibilityOfElement(60);
		Assertions.verify(requestBindPage.effectiveDateData.getData(), testData.get("PolicyEffDate"),
				"Confirm Bind Page", "Effective Date" + ext_rpt_msg, false, false);
		Assertions.verify(
				requestBindPage.namedHurricaneValueData.getData().contains(testData.get("NamedHurricaneDedValue")),
				testData.get("NamedHurricaneDedValue").contains(testData.get("NamedHurricaneDedValue")),
				"Confirm Bind Page", "Named Hurricane" + ext_rpt_msg, false, false);
		Assertions.verify(confirmBindrequestPage.earthQuake.getData().contains(testData.get("EQDeductible")),
				testData.get("EQDeductible").contains(testData.get("EQDeductible")), "Confirm Bind Page",
				"Earthquake" + ext_rpt_msg, false, false);
		requestBindPage.requestBind.click();
		if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
				&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
			requestBindPage.overrideEffectiveDate.select();
			requestBindPage.submit.waitTillVisibilityOfElement(60);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			confirmBindrequestPage.requestBind.waitTillVisibilityOfElement(60);
			confirmBindrequestPage.requestBind.click();
			confirmBindrequestPage.requestBind.waitTillInVisibilityOfElement(60);
		}

		// Verification in Policy Summary Page
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsPresent(), true, "Policy Summary Page",
				"Policy Number Displayed" + ext_rpt_msg, false, false);
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policySummaryPage.getPolicynumber());
		Assertions.verify(policySummaryPage.policyStatus.getData(), testData.get("Status"), "Policy Summary Page",
				"Policy Active Status" + ext_rpt_msg, false, false);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC004", "Executed Successfully");

	}
}