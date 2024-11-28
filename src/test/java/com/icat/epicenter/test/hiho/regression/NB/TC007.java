/** Program Description: TO generate a HIHO policy with multiple locations and multiple dwellings with coverage limits check.
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
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountDetails;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EditInspectionContactPage;
import com.icat.epicenter.pom.EditInsuredContactInfoPage;
import com.icat.epicenter.pom.FloodPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC007 extends AbstractNAHOTest {

	public TC007() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID07.xls";
	}

	public HomePage homePage;
	public DwellingPage dwellingPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public BindRequestSubmittedPage bindRequestPage;
	public ReferralPage referralPage;
	public PolicySummaryPage policySummaryPage;
	public BuildingUnderMinimumCostPage dwellingcost;
	public LocationPage locationPage;
	public AccountDetails accountDetailsPage;
	public FloodPage floodPage;
	public EditInsuredContactInfoPage editContactInfoPage;
	public EditInspectionContactPage editInspectionContactPage;
	public EditAdditionalInterestInformationPage editAdditionalInterest;
	public ConfirmBindRequestPage confirmBindRequestPage;
	public BindRequestSubmittedPage bindRequestSubmittedPage;
	public ApproveDeclineQuotePage approveDeclineQuotePage;
	public Map<String, String> testData;
	public BuildingPage buildingPage;
	public BasePageControl basePage;
	String quoteNumber;
	String policyNumber;
	static final String ext_rpt_msg = " is verified";
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_UPDATED = "Values Updated";
	static final String VALUES_VERIFIED = "Values Verified";
	static int data_Value1 = 0;
	static int data_Value2 = 1;
	static int data_Value3 = 2;
	static int data_Value4 = 3;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		locationPage = new LocationPage();
		dwellingcost = new BuildingUnderMinimumCostPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		accountDetailsPage = new AccountDetails();
		policySummaryPage = new PolicySummaryPage();
		floodPage = new FloodPage();
		editContactInfoPage = new EditInsuredContactInfoPage();
		editInspectionContactPage = new EditInspectionContactPage();
		editAdditionalInterest = new EditAdditionalInterestInformationPage();
		confirmBindRequestPage = new ConfirmBindRequestPage();
		bindRequestPage = new BindRequestSubmittedPage();
		requestBindPage = new RequestBindPage();
		buildingPage = new BuildingPage();
		approveDeclineQuotePage = new ApproveDeclineQuotePage();
		referralPage = new ReferralPage();
		testData = data.get(data_Value1);

		// Create Quote Default value
		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		Assertions.passTest("Home Page", "New Account created successfully");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_UPDATED);
		dwellingPage = (DwellingPage) basePage;

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage = new DwellingPage();
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.addRoofDetails(testData, 1, 1);
		dwellingPage.enterDwellingValues(testData, 1, 1);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1" + VALUES_UPDATED);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();

		// Verifying dwelling details
		Assertions.verify(dwellingPage.dwellingAddress.getData(),
				testData.get("L1D1-DwellingAddress") + ", " + testData.get("L1D1-DwellingCity") + ", " + "HI "
						+ testData.get("L1D1-DwellingZIP"),
				"Dwelling Details Page", "Dwelling Adrress for Location 1 Dwelling 1-1 " + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.propertyDescriptionData.getData(), testData.get("L1D1-DwellingDesc"),
				"Dwelling Details Page", "Dwelling property Desription for Location 1 Dwelling 1-1 " + ext_rpt_msg,
				false, false);
		Assertions.verify(dwellingPage.dwellingType.getData(), testData.get("L1D1-DwellingType"),
				"Dwelling Details Page", "Dwelling Type for Location 1 Dwelling 1-1 " + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.constructionType.getData(), testData.get("L1D1-DwellingConstType"),
				"Dwelling Details Page", "Dwelling Construction Type for Location 1 Dwelling 1-1 " + ext_rpt_msg, false,
				false);
		Assertions.verify(dwellingPage.dwellingYearBuilt.getData(), testData.get("L1D1-DwellingYearBuilt"),
				"Dwelling Details Page", "Dwelling year built for Location 1 Dwelling 1-1 " + ext_rpt_msg, false,
				false);
		Assertions.verify(dwellingPage.livingSqFootageData.getData(),
				testData.get("L1D1-DwellingSqFoot").replace(",", ""), "Dwelling Details Page",
				"Dwelling Living Square Footage for Location 1 Dwelling 1-1 " + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.nonLivingSqFootageData.getData(), testData.get("L1D1-DwellingNonLivingSqFoot"),
				"Dwelling Details Page",
				"Dwelling Non-Living Square Footage for Location 1 Dwelling 1-1 " + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.dwellingFloors.getData(), testData.get("L1D1-DwellingFloors"),
				"Dwelling Details Page", "Dwelling Number of Floors for Location 1 Dwelling 1-1 " + ext_rpt_msg, false,
				false);
		Assertions.verify(dwellingPage.inspectionData.getData(), testData.get("L1D1-InspfromPrimaryCarrier"),
				"Dwelling Details Page",
				"Dwelling Inspection available from Primary Carrier for Location 1 Dwelling 1-1 " + ext_rpt_msg, false,
				false);
		Assertions.verify(dwellingPage.roofShapeDataHIHO.getData(), testData.get("L1D1-DwellingRoofShape"),
				"Dwelling Details Page", "Dwelling Roof Shape for Location 1 Dwelling 1-1 " + ext_rpt_msg, false,
				false);
		Assertions.verify(dwellingPage.roofLastReplacedData.getData(),
				testData.get("L1D1-DwellingRoofReplacedYear").equals("") ? "N/A"
						: testData.get("L1D1-DwellingRoofReplacedYear"),
				"Dwelling Details Page", "Dwelling Roof Last Replaced year for Location 1 Dwelling 1-1 " + ext_rpt_msg,
				false, false);
		Assertions.verify(dwellingPage.WindMitigationData.getData(), testData.get("L1D1-DwellingWindMitigation"),
				"Dwelling Details Page", "Dwelling Wind Mitigation for Location 1 Dwelling 1-1 " + ext_rpt_msg, false,
				false);
		Assertions.verify(dwellingPage.coverageA.getData(), "$" + testData.get("L1D1-DwellingCovA"), "Dwelling Page",
				"Dwelling Coverage A for Location 1 Dwelling 1-1" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.coverageB.getData(), "$" + testData.get("L1D1-DwellingCovB"), "Dwelling Page",
				"Dwelling Coverage B for Location 1 Dwelling 1-1" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.coverageC.getData(), "$" + testData.get("L1D1-DwellingCovC"), "Dwelling Page",
				"Dwelling Coverage C for Location 1 Dwelling 1-1" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.coverageD.getData(), "$" + testData.get("L1D1-DwellingCovD"), "Dwelling Page",
				"Dwelling Coverage D for Location 1 Dwelling 1-1" + ext_rpt_msg, false, false);

		// Adding 2nd dwelling
		dwellingPage.addSymbol.scrollToElement();
		dwellingPage.addSymbol.click();
		dwellingPage.addNewDwelling.click();
		Assertions.passTest("Dwelling Details Page",
				"Dwelling Details Page is dispayed when clicked on Add new Dwelling hyperlink" + ext_rpt_msg);
		testData = data.get(data_Value1);

		// Entering Location 1 Dwelling 2 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, 1, 2);
		dwellingPage.addRoofDetails(testData, 1, 2);
		dwellingPage.enterDwellingValues(testData, 1, 2);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1-2 " + VALUES_UPDATED);
		Assertions.verify(dwellingPage.covCNamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
				"Coverage C not available for Course of Construction dwellings " + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.covDNamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
				"Coverage D not available for Course of Construction dwellings " + ext_rpt_msg, false, false);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();

		// Asserting warning messge for course of construction
		Assertions.verify(dwellingPage.cOCWarningMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
				"Course Of Construction Warning message " + dwellingPage.cOCWarningMsg.getData() + " is displayed",
				false, false);

		// Update year built to current
		dwellingPage.dwellingCharacteristicsLink.waitTillVisibilityOfElement(60);
		dwellingPage.dwellingCharacteristicsLink.scrollToElement();
		dwellingPage.dwellingCharacteristicsLink.click();
		testData = data.get(data_Value2);
		dwellingPage.yearBuilt.setData(testData.get("L1D2-DwellingYearBuilt"));

		// Click on create quote button
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");
		dwellingPage.scrollToBottomPage();
		dwellingPage.waitTime(2); // to overcome elementclickinterceptedexception
		dwellingPage.reSubmit.waitTillVisibilityOfElement(60);
		dwellingPage.reSubmit.scrollToElement();
		dwellingPage.reSubmit.click();
		testData = data.get(data_Value1);
		dwellingcost.override.scrollToElement();
		dwellingcost.override.click();
		Assertions.verify(dwellingPage.coverageA.getData(), "$" + testData.get("L1D2-DwellingCovA"), "Dwelling Page",
				"Dwelling Coverage A for Location 1 Dwelling 1-1" + ext_rpt_msg, false, false);
		dwellingPage.addSymbol.scrollToElement();
		dwellingPage.addSymbol.click();
		dwellingPage.addNewLocation.click();
		Assertions.passTest("Location Details Page",
				"Location Details Page is dispayed when clicked on Add new Location hyperlink" + ext_rpt_msg);
		testData = data.get(data_Value2);
		locationPage
				.addLocationAddress(testData.get("L1D1-DwellingAddress") + ", " + testData.get("L1D1-DwellingCity"));
		locationPage.addDwellingButton.scrollToElement();
		locationPage.addDwellingButton.click();
		Assertions.passTest("Dwelling Details Page",
				"Dwelling Details Page is dispayed when clicked on Add Dwelling button in Location Page" + ext_rpt_msg);

		// Entering Location 2 Dwelling 1 Details
		testData = data.get(data_Value1);
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, 2, 1);
		dwellingPage.addRoofDetails(testData, 2, 1);
		dwellingPage.enterDwellingValues(testData, 2, 1);
		Assertions.passTest("Dwelling Page", "Location 2 Dwelling 2-1 " + VALUES_UPDATED);
		Assertions.verify(dwellingPage.covANamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
				"Coverage A not available for Dwelling Type Tenant " + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.covBNamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
				"Coverage B not available for Dwelling Type Tenant " + ext_rpt_msg, false, false);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingPage.copyDwellingSymbol.scrollToElement();
		dwellingPage.copyDwellingSymbol.click();
		Assertions.verify(dwellingPage.copyDwelling2.checkIfElementIsDisplayed(), true, "Dwelling Details Page",
				"Dwelling details page with dwelling name Dwelling 2-2 when dwelling is copied " + ext_rpt_msg, false,
				false);
		dwellingPage.editDwellingSymbol.scrollToElement();
		dwellingPage.editDwellingSymbol.click();

		// Entering Location 2 Dwelling 2 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, 2, 2);
		dwellingPage.addRoofDetails(testData, 2, 2);
		dwellingPage.enterDwellingValues(testData, 2, 2);
		Assertions.passTest("Dwelling Page", "Location 2 Dwelling 2-2 " + VALUES_UPDATED);
		Assertions.verify(dwellingPage.covBNamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
				"Coverage B not available for Dwelling Type Condominium unit owner " + ext_rpt_msg, false, false);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingPage.copyDwellingSymbol.scrollToElement();
		dwellingPage.copyDwellingSymbol.click();
		Assertions.verify(dwellingPage.copyDwelling3.checkIfElementIsDisplayed(), true, "Dwelling Details Page",
				"Dwelling details page with dwelling name Dwelling 2-3 when dwelling is copied " + ext_rpt_msg, false,
				false);
		dwellingPage.editDwellingSymbol.scrollToElement();
		dwellingPage.editDwellingSymbol.click();

		// Entering Location 2 Dwelling 3 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, 2, 3);
		dwellingPage.addRoofDetails(testData, 2, 3);
		dwellingPage.enterDwellingValues(testData, 2, 3);
		Assertions.passTest("Dwelling Page", "Location 2 Dwelling 2-3 " + VALUES_UPDATED);
		Assertions.verify(dwellingPage.covANamedHurricane.checkIfElementIsPresent(), true, "Dwelling Page",
				"Coverage A displayed for Dwelling Type Dwelling " + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.covBNamedHurricane.checkIfElementIsPresent(), true, "Dwelling Page",
				"Coverage B displayed for Dwelling Type Dwelling " + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.covCNamedHurricane.checkIfElementIsPresent(), true, "Dwelling Page",
				"Coverage C displayed for Dwelling Type Dwelling " + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.covDNamedHurricane.checkIfElementIsPresent(), true, "Dwelling Page",
				"Coverage D displayed for Dwelling Type Dwelling " + ext_rpt_msg, false, false);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		if (dwellingPage.bringUptoCost.checkIfElementIsPresent()
				&& dwellingPage.bringUptoCost.checkIfElementIsDisplayed()) {
			dwellingPage.bringUptoCost.scrollToElement();
			dwellingPage.bringUptoCost.click();
		}
		dwellingPage.addSymbol.scrollToElement();
		dwellingPage.addSymbol.click();
		dwellingPage.addNewLocation.click();
		Assertions.passTest("Location Details Page",
				"Location Details Page is dispayed when clicked on Add new Location hyperlink" + ext_rpt_msg);
		testData = data.get(data_Value3);
		locationPage.addLocationAddress(testData.get("L1D1-DwellingAddress") + ", " + testData.get("L1D1-DwellingCity")
				+ ", " + testData.get("L1D1-DwellingZIP"));
		locationPage.addDwellingButton.scrollToElement();
		locationPage.addDwellingButton.click();
		Assertions.passTest("Dwelling Details Page",
				"Dwelling Details Page is dispayed when clicked on Add Dwelling button in Location Page" + ext_rpt_msg);

		// Entering Location 3 Dwelling 1 Details
		testData = data.get(data_Value1);
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, 3, 1);
		dwellingPage.addRoofDetails(testData, 3, 1);
		dwellingPage.enterDwellingValues(testData, 3, 1);
		Assertions.passTest("Dwelling Page", "Location 3 Dwelling 1-1 " + VALUES_UPDATED);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingPage.copySymbol.scrollToElement();
		dwellingPage.copySymbol.click();
		dwellingPage.editDwellingSymbol.scrollToElement();
		dwellingPage.editDwellingSymbol.click();

		// Entering Location 3 Dwelling 2 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, 3, 2);
		dwellingPage.addRoofDetails(testData, 3, 2);
		dwellingPage.enterDwellingValues(testData, 3, 2);
		Assertions.passTest("Dwelling Page", "Location 3 Dwelling 1-2 " + VALUES_UPDATED);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingcost.leaveIneligible.scrollToElement();
		dwellingcost.leaveIneligible.click();
		Assertions.verify(dwellingPage.exclamatorySignDwellin1.checkIfElementIsDisplayed(), true, "Dwelling Page",
				"Location 3 Dwelling 2 exclamatory sign" + ext_rpt_msg, false, false);
		locationPage.coveragesStep2.scrollToElement();
		locationPage.coveragesStep2.click();
		Assertions.verify(dwellingPage.ineligibleWarning.getData(),
				"This account contains an ineligible building. Choose from the remaining buildings, or contact your USM for additional consideration.",
				"Building Selection Page",
				"This account contains an ineligible building. Choose from the remaining buildings, or contact your USM for additional consideration.warning message "
						+ ext_rpt_msg,
				false, false);
		Assertions.verify(buildingPage.ineligibleDwelling.checkIfElementIsPresent(), true, "Building Selection Page",
				"Checkbox is not available for ineligible dwelling (Dwelling 3-2)" + ext_rpt_msg, false, false);
		dwellingPage.uncheckDwelling2n3.scrollToElement();
		dwellingPage.uncheckDwelling2n3.deSelect();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// verifying deductible values
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), testData.get("NamedHurricaneDedValue"),
				"Create Quote Page", "Default Named Hurricane Value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"Default EarthQuake Value" + ext_rpt_msg, false, false);
		testData = data.get(data_Value3);
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Deductible details entered successfully");

		// verifying coverage values
		testData = data.get(data_Value1);
		Assertions.verify(createQuotePage.personalPropertyReplacementCostData.getData(),
				testData.get("ReplacementCost"), "Create Quote Page ",
				"Default Personal Property Replacement cost" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.enhancedReplacementCostDataHIHO.getData(), testData.get("EnhancedReplCost"),
				"Create Quote page", "Default Enhanced Replacement cost" + ext_rpt_msg, false, false);
		testData = data.get(data_Value1);
		Assertions.verify(createQuotePage.ordinancelawData.getData(), testData.get("OrdinanceOrLaw"),
				"Create Quote Page", "Default ordinance or Law value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Coverage details verified successfully");

		// Entering coverage Details
		testData = data.get(data_Value3);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Coverage details entered successfully");
		testData = data.get(data_Value1);
		for (int i = 1; i <= Integer.parseInt(testData.get("LocCount")); i++) {
			for (int j = 1; j <= Integer.parseInt(testData.get("L" + i + "-DwellingCount")); j++) {
				Assertions.addInfo("<span class='group'> Create Quote Page</span>",
						"<span class='group'> Location " + i + " - Dwelling " + i + "-" + j + " </span>");
				BaseWebElementControl locationDwelling = new BaseWebElementControl(
						By.xpath("//span[contains(text(),'Location " + i + " - Dwelling " + i + "-" + j + "')]"));
				TextFieldControl covA_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[2]//input"));
				TextFieldControl covB_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[3]//td[2]//input"));
				TextFieldControl covC_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[4]//td[2]//input"));
				TextFieldControl covD_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[5]//td[2]//input"));
				BaseWebElementControl covA_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covB_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covC_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covD_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				TextFieldControl covA_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[3]//input"));
				TextFieldControl covB_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[3]//td[3]//input"));
				TextFieldControl covC_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[4]//td[3]//input"));
				TextFieldControl covD_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[5]//td[3]//input"));
				BaseWebElementControl covA_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covB_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covC_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covD_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covA_Floodlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covB_Floodlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covC_Floodlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covD_Floodlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				if (locationDwelling.checkIfElementIsPresent() && locationDwelling.checkIfElementIsDisplayed()) {
					if (testData.get("L" + i + "D" + j + "-DwellingCovA") != "") {
						Assertions.verify(covA_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovA").replace(",", ""), "Create Quote Page",
								"Coverage A Named Hurricane value: " + covA_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covA_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovA").replace(",", ""),
										"Create Quote Page", "Coverage A Earthquake value: "
												+ covA_EQinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covA_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage A Named Hurricane value: " + covA_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covA_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage A Earthquake value: " + covA_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovB") != "") {
						Assertions.verify(covB_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovB").replace(",", ""), "Create Quote Page",
								"Coverage B Named Hurricane value: " + covB_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covB_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovB").replace(",", ""),
										"Create Quote Page", "Coverage B Earthquake value: "
												+ covB_EQinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covB_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage B Named Hurricane value: " + covB_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covB_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage B Earthquake value: " + covB_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovC") != "") {
						Assertions.verify(covC_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovC").replace(",", ""), "Create Quote Page",
								"Coverage C Named Hurricane value: " + covC_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covC_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovC").replace(",", ""),
										"Create Quote Page", "Coverage C Earthquake value: "
												+ covC_NHinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covC_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage C Named Hurricane value: " + covC_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covC_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage C Earthquake value: " + covC_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovD") != "") {
						Assertions.verify(covD_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovD").replace(",", ""), "Create Quote Page",
								"Coverage D Named Hurricane value: " + covD_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covD_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovD").replace(",", ""),
										"Create Quote Page", "Coverage D Earthquake value: "
												+ covD_EQinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covD_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage D Named Hurricane value: " + covD_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covD_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage D Earthquake value: " + covD_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					Assertions.verify(covA_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage A Flood value: " + covA_Floodlabel.getData() + ext_rpt_msg, false, false);
					Assertions.verify(covB_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage B Flood value: " + covB_Floodlabel.getData() + ext_rpt_msg, false, false);
					Assertions.verify(covC_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage C Flood value: " + covC_Floodlabel.getData() + ext_rpt_msg, false, false);
					Assertions.verify(covD_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage D Flood value: " + covD_Floodlabel.getData() + ext_rpt_msg, false, false);
				}
			}
		}
		Assertions.passTest("Create Quote Page", "Insured values verified successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.verify(createQuotePage.minimumCost.getData(),
				"Location 1, Dwelling 2 - The Coverage A value will be increased to meet the minimum cost per square foot. The minimum Dwelling value for this risk is $813,800",
				"Create Quote Page",
				"Location 1, Dwelling 2 - The Coverage A value will be increased to meet the minimum cost per square foot. The minimum Dwelling value for this risk is $813,800 warning message"
						+ ext_rpt_msg,
				false, false);
		dwellingPage.override.scrollToElement();
		dwellingPage.override.click();
		for (int i = 1; i <= 3; i++) {
			BaseWebElementControl warningMessageforAdjustments = new BaseWebElementControl(By.xpath("//li[" + i + "]"));
			Assertions.verify(warningMessageforAdjustments.checkIfElementIsDisplayed(), true, "Create Quote Page",
					warningMessageforAdjustments.getData() + ext_rpt_msg, false, false);
		}
		createQuotePage.goBack.scrollToElement();
		createQuotePage.goBack.click();
		createQuotePage.previous.scrollToElement();
		createQuotePage.previous.click();
		dwellingPage.dwellingLink1.scrollToElement();
		dwellingPage.dwellingLink1.click();
		dwellingPage.editBuilding.scrollToElement();
		dwellingPage.editBuilding.click();
		testData = data.get(data_Value2);
		dwellingPage.enterDwellingValues(testData, 1, 1);
		dwellingPage.dwellingLink3.scrollToElement();
		dwellingPage.dwellingLink3.click();
		dwellingPage.dwellingValuesLink.scrollToElement();
		dwellingPage.dwellingValuesLink.click();
		dwellingPage.covBNamedHurricane.clearData();
		dwellingPage.covBNamedHurricane.tab();
		dwellingPage.covBNamedHurricane.scrollToElement();
		dwellingPage.covBNamedHurricane.setData(testData.get("L3D1-DwellingCovB"));
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.uncheckDwelling2n3.scrollToElement();
		dwellingPage.uncheckDwelling2n3.deSelect();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		createQuotePage.chooseCoverageByLocation.scrollToElement();
		createQuotePage.chooseCoverageByLocation.click();
		testData = data.get(data_Value1);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);
		// createQuotePage.coverageByLocationHIHO(testData, 3);

		createQuotePage.chooseCoverageByLocation.scrollToElement();
		createQuotePage.chooseCoverageByLocation.click();
		createQuotePage.namedHurricaneDeductibleArrow.waitTillVisibilityOfElement(30);

		if (!testData.get("L" + 1 + "-NamedHurricaneDed").equals("")) {
			createQuotePage.namedHurricaneDeductibleArrow1.formatDynamicPath(1).scrollToElement();
			createQuotePage.namedHurricaneDeductibleArrow1.formatDynamicPath(1).click();
			waitTime(3);
			createQuotePage.locationNamedHurricaneDeductibleOption1
					.formatDynamicPath(1, testData.get("L" + 1 + "-NamedHurricaneDed")).click();
		}
		if (!testData.get("L" + 2 + "-NamedHurricaneDed").equals("")) {
			createQuotePage.namedHurricaneDeductibleArrow1.formatDynamicPath(2).scrollToElement();
			createQuotePage.namedHurricaneDeductibleArrow1.formatDynamicPath(2).click();
			waitTime(3);
			createQuotePage.locationNamedHurricaneDeductibleOption1
					.formatDynamicPath(2, testData.get("L" + 2 + "-NamedHurricaneDed")).click();
		}
		if (!testData.get("L" + 3 + "-NamedHurricaneDed").equals("")) {
			createQuotePage.namedHurricaneDeductibleArrow1.formatDynamicPath(3).scrollToElement();
			createQuotePage.namedHurricaneDeductibleArrow1.formatDynamicPath(3).click();
			waitTime(3);
			createQuotePage.locationNamedHurricaneDeductibleOption1
					.formatDynamicPath(3, testData.get("L" + 3 + "-NamedHurricaneDed")).click();
		}

		if (!testData.get("L" + 1 + "-EarthquakeDed").equals("")) {
			createQuotePage.locationEarthquakeDeductibleArrow.formatDynamicPath(1).scrollToElement();
			createQuotePage.locationEarthquakeDeductibleArrow.formatDynamicPath(1).click();
			createQuotePage.locationEarthquakeDeductibleOption1
					.formatDynamicPath(1, testData.get("L" + 1 + "-EarthquakeDed")).click();
			createQuotePage.loading.waitTillInVisibilityOfElement(30);
		}
		if (!testData.get("L" + 2 + "-EarthquakeDed").equals("")) {
			createQuotePage.locationEarthquakeDeductibleArrow.formatDynamicPath(2).scrollToElement();
			createQuotePage.locationEarthquakeDeductibleArrow.formatDynamicPath(2).click();
			createQuotePage.locationEarthquakeDeductibleOption1
					.formatDynamicPath(2, testData.get("L" + 2 + "-EarthquakeDed")).click();
			createQuotePage.loading.waitTillInVisibilityOfElement(30);
		}
		if (!testData.get("L" + 3 + "-EarthquakeDed").equals("")) {
			createQuotePage.locationEarthquakeDeductibleArrow.formatDynamicPath(3).scrollToElement();
			createQuotePage.locationEarthquakeDeductibleArrow.formatDynamicPath(3).click();
			createQuotePage.locationEarthquakeDeductibleOption1
					.formatDynamicPath(3, testData.get("L" + 3 + "-EarthquakeDed")).click();
			createQuotePage.loading.waitTillInVisibilityOfElement(30);
		}

		if (!testData.get("L" + 1 + "-EnhancedReplCost").equals("")) {
			createQuotePage.locationEnhancedReplacementCostArrow1.formatDynamicPath(0).scrollToElement();
			createQuotePage.locationEnhancedReplacementCostArrow1.formatDynamicPath(0).click();
			createQuotePage.locationEnhancedReplacementCostOption1
					.formatDynamicPath(0, testData.get("L" + 1 + "-EnhancedReplCost")).click();
		}
		if (!testData.get("L" + 2 + "-EnhancedReplCost").equals("")) {
			createQuotePage.locationEnhancedReplacementCostArrow1.formatDynamicPath(1).scrollToElement();
			createQuotePage.locationEnhancedReplacementCostArrow1.formatDynamicPath(1).click();
			createQuotePage.locationEnhancedReplacementCostOption1
					.formatDynamicPath(1, testData.get("L" + 2 + "-EnhancedReplCost")).click();
		}
		if (!testData.get("L" + 3 + "-EnhancedReplCost").equals("")) {
			createQuotePage.locationEnhancedReplacementCostArrow1.formatDynamicPath(2).scrollToElement();
			createQuotePage.locationEnhancedReplacementCostArrow1.formatDynamicPath(2).click();
			createQuotePage.locationEnhancedReplacementCostOption1
					.formatDynamicPath(2, testData.get("L" + 3 + "-EnhancedReplCost")).click();
		}

		for (int i = 1; i <= 3; i++) {
			testData = data.get(data_Value1);
			if (!testData.get("L" + i + "-AOCLDed").equals("")) {
				createQuotePage.aoclDeductibleArrow.scrollToElement();
				createQuotePage.aoclDeductibleArrow.click();
				createQuotePage.aOCLDedValueOption.formatDynamicPath(testData.get("L" + i + "-AOCLDed")).click();
			}
			if (!testData.get("L" + i + "-AOWHDed").equals("")) {
				createQuotePage.aOWHNamedHurricaneDedValueArrow.scrollToElement();
				createQuotePage.aOWHNamedHurricaneDedValueArrow.click();
				createQuotePage.aOWHNamedHurricaneDedValueOption.formatDynamicPath(testData.get("L" + i + "-AOWHDed"))
						.click();
			}
			if (!testData.get("L" + i + "-FloodDed").equals("")) {
				createQuotePage.floodDeductibleArrow.scrollToElement();
				createQuotePage.floodDeductibleArrow.click();
				createQuotePage.floodDeductibleOption.formatDynamicPath(testData.get("L" + i + "-FloodDed")).click();
			}
			if (!testData.get("L" + i + "-PropertyReplCost").equals("")) {
				createQuotePage.locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i).scrollToElement();
				createQuotePage.locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i).click();
				createQuotePage.locationPersonalPropertyReplacementCostOption
						.formatDynamicPath(i, testData.get("L" + i + "-PropertyReplCost")).click();
			}
		}

		if (!testData.get("L" + 1 + "-OrdinanceLaw").equals("")) {
			if (createQuotePage.locationOridnanceLawArrow.formatDynamicPath(0).checkIfElementIsPresent()
					&& createQuotePage.locationOridnanceLawArrow.formatDynamicPath(0).checkIfElementIsDisplayed()) {
				createQuotePage.locationOridnanceLawArrow.formatDynamicPath(0).scrollToElement();
				createQuotePage.locationOridnanceLawArrow.formatDynamicPath(0).click();
				createQuotePage.locationOrdLawOption.formatDynamicPath(0, testData.get("L" + 1 + "-OrdinanceLaw"))
						.click();
			}
			if (!testData.get("L" + 2 + "-OrdinanceLaw").equals("")) {
				if (createQuotePage.locationOridnanceLawArrow.formatDynamicPath(1).checkIfElementIsPresent()
						&& createQuotePage.locationOridnanceLawArrow.formatDynamicPath(1).checkIfElementIsDisplayed()) {
					createQuotePage.locationOridnanceLawArrow.formatDynamicPath(1).scrollToElement();
					createQuotePage.locationOridnanceLawArrow.formatDynamicPath(1).click();
					createQuotePage.locationOrdLawOption.formatDynamicPath(1, testData.get("L" + 2 + "-OrdinanceLaw"))
							.click();
				}
			}
			if (!testData.get("L" + 3 + "-OrdinanceLaw").equals("")) {
				if (createQuotePage.locationOridnanceLawArrow.formatDynamicPath(2).checkIfElementIsPresent()
						&& createQuotePage.locationOridnanceLawArrow.formatDynamicPath(2).checkIfElementIsDisplayed()) {
					createQuotePage.locationOridnanceLawArrow.formatDynamicPath(2).scrollToElement();
					createQuotePage.locationOridnanceLawArrow.formatDynamicPath(2).click();
					createQuotePage.locationOrdLawOption.formatDynamicPath(2, testData.get("L" + 3 + "-OrdinanceLaw"))
							.click();
				}
			}
		}

		testData = data.get(data_Value2);
		for (int i = 1; i <= Integer.parseInt(testData.get("LocCount")); i++) {
			for (int j = 1; j <= Integer.parseInt(testData.get("L" + i + "-DwellingCount")); j++) {
				Assertions.addInfo("<span class='group'> Create Quote Page</span>",
						"<span class='group'> Location " + i + " - Dwelling " + i + "-" + j + " </span>");
				BaseWebElementControl locationDwelling = new BaseWebElementControl(
						By.xpath("//span[contains(text(),'Location " + i + " - Dwelling " + i + "-" + j + "')]"));
				TextFieldControl covA_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[2]//input"));
				TextFieldControl covB_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[3]//td[2]//input"));
				TextFieldControl covC_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[4]//td[2]//input"));
				TextFieldControl covD_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[5]//td[2]//input"));
				BaseWebElementControl covA_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covB_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covC_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covD_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				TextFieldControl covA_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[3]//input"));
				TextFieldControl covB_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[3]//td[3]//input"));
				TextFieldControl covC_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[4]//td[3]//input"));
				TextFieldControl covD_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[5]//td[3]//input"));
				BaseWebElementControl covA_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covB_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covC_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covD_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covA_Floodlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covB_Floodlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covC_Floodlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covD_Floodlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));

				if (locationDwelling.checkIfElementIsPresent() && locationDwelling.checkIfElementIsDisplayed()) {
					if (testData.get("L" + i + "D" + j + "-DwellingCovA") != "") {
						Assertions.verify(covA_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovA").replace(",", ""), "Create Quote Page",
								"Coverage A Named Hurricane value: " + covA_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covA_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovA").replace(",", ""),
										"Create Quote Page", "Coverage A Earthquake value: "
												+ covA_EQinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covA_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage A Named Hurricane value: " + covA_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covA_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage A Earthquake value: " + covA_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovB") != "") {
						Assertions.verify(covB_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovB").replace(",", ""), "Create Quote Page",
								"Coverage B Named Hurricane value: " + covB_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covB_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovB").replace(",", ""),
										"Create Quote Page", "Coverage B Earthquake value: "
												+ covB_EQinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covB_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage B Named Hurricane value: " + covB_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covB_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage B Earthquake value: " + covB_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovC") != "") {
						Assertions.verify(covC_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovC").replace(",", ""), "Create Quote Page",
								"Coverage C Named Hurricane value: " + covC_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covC_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovC").replace(",", ""),
										"Create Quote Page", "Coverage C Earthquake value: "
												+ covC_NHinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covC_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage C Named Hurricane value: " + covC_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covC_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage C Earthquake value: " + covC_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovD") != "") {
						Assertions.verify(covD_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovD").replace(",", ""), "Create Quote Page",
								"Coverage D Named Hurricane value: " + covD_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covD_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovD").replace(",", ""),
										"Create Quote Page", "Coverage D Earthquake value: "
												+ covD_EQinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covD_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage D Named Hurricane value: " + covD_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covD_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage D Earthquake value: " + covD_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					Assertions.verify(covA_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage A Flood value: " + covA_Floodlabel.getData() + ext_rpt_msg, false, false);
					Assertions.verify(covB_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage B Flood value: " + covB_Floodlabel.getData() + ext_rpt_msg, false, false);
					Assertions.verify(covC_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage C Flood value: " + covC_Floodlabel.getData() + ext_rpt_msg, false, false);
					Assertions.verify(covD_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage D Flood value: " + covD_Floodlabel.getData() + ext_rpt_msg, false, false);
				}
			}
		}
		Assertions.passTest("Create Quote Page", "Insured values verified successfully");

		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.override.scrollToElement();
		createQuotePage.override.click();

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		Assertions.verify(accountOverviewPage.acntOverviewQuoteNumber.checkIfElementIsDisplayed(), true,
				"Account Overview page", "Quote Number generated" + ext_rpt_msg, false, false);
		quoteNumber = accountOverviewPage.acntOverviewQuoteNumber.getData();
		Assertions.verify(accountOverviewPage.acntOverviewQuoteNumber.getData(), quoteNumber, "Account Overview page",
				"Quote Number is :" + accountOverviewPage.acntOverviewQuoteNumber.getData(), false, false);
		Assertions.verify(accountOverviewPage.nhEqVar.checkIfElementIsDisplayed(), true, "Account Overview page",
				"Named Hurricance and Earthquake var displayed" + ext_rpt_msg, false, false);
		accountOverviewPage.quoteSomeDwellingsButton.scrollToElement();
		accountOverviewPage.quoteSomeDwellingsButton.click();
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 3; j++) {
				BaseWebElementControl dwellingName = new BaseWebElementControl(
						By.xpath("//span[contains(text(),'Dwelling " + i + "-" + j + "')]"));
				CheckBoxControl buildingSelections = new CheckBoxControl(
						By.xpath("//span[contains(text(),'Dwelling " + i + -j + "')]//following-sibling::input"));
				if (dwellingName.checkIfElementIsPresent() && dwellingName.checkIfElementIsDisplayed()) {
					if (buildingSelections.checkIfElementIsPresent()
							&& buildingSelections.checkIfElementIsDisplayed()) {
						Assertions.verify(
								buildingSelections.checkIfElementIsPresent()
										&& buildingSelections.checkIfElementIsDisplayed(),
								true, "Building Selections Page",
								"Checkbox for Location " + i + " Dwelling " + i + " -" + j + ext_rpt_msg, false, false);
					} else
						Assertions.verify(
								buildingSelections.checkIfElementIsPresent()
										&& buildingSelections.checkIfElementIsDisplayed(),
								false, "Building Selections Page", "Checkbox for Location " + i + " Dwelling " + i
										+ " -" + j + "not displayed" + ext_rpt_msg,
								false, false);
				}
			}
		}
		dwellingPage.uncheckDwelling2n2.scrollToElement();
		dwellingPage.uncheckDwelling2n2.deSelect();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		createQuotePage.chooseCoverageByLocation.scrollToElement();
		createQuotePage.chooseCoverageByLocation.click();
		testData = data.get(data_Value1);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);
		testData = data.get(data_Value2);
		// createQuotePage.coverageByLocation(testData, 3);
		createQuotePage.chooseCoverageByLocation.scrollToElement();
		createQuotePage.chooseCoverageByLocation.click();
		createQuotePage.namedHurricaneDeductibleArrow.waitTillVisibilityOfElement(30);

		if (!testData.get("L" + 1 + "-NamedHurricaneDed").equals("")) {
			createQuotePage.namedHurricaneDeductibleArrow1.formatDynamicPath(1).scrollToElement();
			createQuotePage.namedHurricaneDeductibleArrow1.formatDynamicPath(1).click();
			waitTime(3);
			createQuotePage.locationNamedHurricaneDeductibleOption1
					.formatDynamicPath(1, testData.get("L" + 1 + "-NamedHurricaneDed")).click();
		}
		if (!testData.get("L" + 2 + "-NamedHurricaneDed").equals("")) {
			createQuotePage.namedHurricaneDeductibleArrow1.formatDynamicPath(2).scrollToElement();
			createQuotePage.namedHurricaneDeductibleArrow1.formatDynamicPath(2).click();
			waitTime(3);
			createQuotePage.locationNamedHurricaneDeductibleOption1
					.formatDynamicPath(2, testData.get("L" + 2 + "-NamedHurricaneDed")).click();
		}
		if (!testData.get("L" + 3 + "-NamedHurricaneDed").equals("")) {
			createQuotePage.namedHurricaneDeductibleArrow1.formatDynamicPath(3).scrollToElement();
			createQuotePage.namedHurricaneDeductibleArrow1.formatDynamicPath(3).click();
			waitTime(3);
			createQuotePage.locationNamedHurricaneDeductibleOption1
					.formatDynamicPath(3, testData.get("L" + 3 + "-NamedHurricaneDed")).click();
		}

		if (!testData.get("L" + 1 + "-EarthquakeDed").equals("")) {
			createQuotePage.locationEarthquakeDeductibleArrow.formatDynamicPath(1).scrollToElement();
			createQuotePage.locationEarthquakeDeductibleArrow.formatDynamicPath(1).click();
			createQuotePage.locationEarthquakeDeductibleOption1
					.formatDynamicPath(1, testData.get("L" + 1 + "-EarthquakeDed")).click();
			createQuotePage.loading.waitTillInVisibilityOfElement(30);
		}
		if (!testData.get("L" + 2 + "-EarthquakeDed").equals("")) {
			createQuotePage.locationEarthquakeDeductibleArrow.formatDynamicPath(2).scrollToElement();
			createQuotePage.locationEarthquakeDeductibleArrow.formatDynamicPath(2).click();
			createQuotePage.locationEarthquakeDeductibleOption1
					.formatDynamicPath(2, testData.get("L" + 2 + "-EarthquakeDed")).click();
			createQuotePage.loading.waitTillInVisibilityOfElement(30);
		}
		if (!testData.get("L" + 3 + "-EarthquakeDed").equals("")) {
			createQuotePage.locationEarthquakeDeductibleArrow.formatDynamicPath(3).scrollToElement();
			createQuotePage.locationEarthquakeDeductibleArrow.formatDynamicPath(3).click();
			createQuotePage.locationEarthquakeDeductibleOption1
					.formatDynamicPath(3, testData.get("L" + 3 + "-EarthquakeDed")).click();
			createQuotePage.loading.waitTillInVisibilityOfElement(30);
		}

		if (!testData.get("L" + 1 + "-EnhancedReplCost").equals("")) {
			createQuotePage.locationEnhancedReplacementCostArrow1.formatDynamicPath(0).scrollToElement();
			createQuotePage.locationEnhancedReplacementCostArrow1.formatDynamicPath(0).click();
			createQuotePage.locationEnhancedReplacementCostOption1
					.formatDynamicPath(0, testData.get("L" + 1 + "-EnhancedReplCost")).click();
		}
		if (!testData.get("L" + 2 + "-EnhancedReplCost").equals("")) {
			createQuotePage.locationEnhancedReplacementCostArrow1.formatDynamicPath(1).scrollToElement();
			createQuotePage.locationEnhancedReplacementCostArrow1.formatDynamicPath(1).click();
			createQuotePage.locationEnhancedReplacementCostOption1
					.formatDynamicPath(1, testData.get("L" + 2 + "-EnhancedReplCost")).click();
		}
		if (!testData.get("L" + 3 + "-EnhancedReplCost").equals("")) {
			createQuotePage.locationEnhancedReplacementCostArrow1.formatDynamicPath(2).scrollToElement();
			createQuotePage.locationEnhancedReplacementCostArrow1.formatDynamicPath(2).click();
			createQuotePage.locationEnhancedReplacementCostOption1
					.formatDynamicPath(2, testData.get("L" + 3 + "-EnhancedReplCost")).click();
		}

		for (int i = 1; i <= 3; i++) {
			testData = data.get(data_Value2);
			if (!testData.get("L" + i + "-AOCLDed").equals("")) {
				createQuotePage.aoclDeductibleArrow.scrollToElement();
				createQuotePage.aoclDeductibleArrow.click();
				createQuotePage.aOCLDedValueOption.formatDynamicPath(testData.get("L" + i + "-AOCLDed")).click();
			}
			if (!testData.get("L" + i + "-AOWHDed").equals("")) {
				createQuotePage.aOWHNamedHurricaneDedValueArrow.scrollToElement();
				createQuotePage.aOWHNamedHurricaneDedValueArrow.click();
				createQuotePage.aOWHNamedHurricaneDedValueOption.formatDynamicPath(testData.get("L" + i + "-AOWHDed"))
						.click();
			}
			if (!testData.get("L" + i + "-FloodDed").equals("")) {
				createQuotePage.floodDeductibleArrow.scrollToElement();
				createQuotePage.floodDeductibleArrow.click();
				createQuotePage.floodDeductibleOption.formatDynamicPath(testData.get("L" + i + "-FloodDed")).click();
			}
			if (!testData.get("L" + i + "-PropertyReplCost").equals("")) {
				createQuotePage.locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i).scrollToElement();
				createQuotePage.locationPersonalPropertyReplacementCostArrow.formatDynamicPath(i).click();
				createQuotePage.locationPersonalPropertyReplacementCostOption
						.formatDynamicPath(i, testData.get("L" + i + "-PropertyReplCost")).click();
			}
		}

		if (!testData.get("L" + 1 + "-OrdinanceLaw").equals("")) {
			if (createQuotePage.locationOridnanceLawArrow.formatDynamicPath(0).checkIfElementIsPresent()
					&& createQuotePage.locationOridnanceLawArrow.formatDynamicPath(0).checkIfElementIsDisplayed()) {
				createQuotePage.locationOridnanceLawArrow.formatDynamicPath(0).scrollToElement();
				createQuotePage.locationOridnanceLawArrow.formatDynamicPath(0).click();
				createQuotePage.locationOrdLawOption.formatDynamicPath(0, testData.get("L" + 1 + "-OrdinanceLaw"))
						.click();
			}
			if (!testData.get("L" + 2 + "-OrdinanceLaw").equals("")) {
				if (createQuotePage.locationOridnanceLawArrow.formatDynamicPath(1).checkIfElementIsPresent()
						&& createQuotePage.locationOridnanceLawArrow.formatDynamicPath(1).checkIfElementIsDisplayed()) {
					createQuotePage.locationOridnanceLawArrow.formatDynamicPath(1).scrollToElement();
					createQuotePage.locationOridnanceLawArrow.formatDynamicPath(1).click();
					createQuotePage.locationOrdLawOption.formatDynamicPath(1, testData.get("L" + 2 + "-OrdinanceLaw"))
							.click();
				}
			}
			if (!testData.get("L" + 3 + "-OrdinanceLaw").equals("")) {
				if (createQuotePage.locationOridnanceLawArrow.formatDynamicPath(2).checkIfElementIsPresent()
						&& createQuotePage.locationOridnanceLawArrow.formatDynamicPath(2).checkIfElementIsDisplayed()) {
					createQuotePage.locationOridnanceLawArrow.formatDynamicPath(2).scrollToElement();
					createQuotePage.locationOridnanceLawArrow.formatDynamicPath(2).click();
					createQuotePage.locationOrdLawOption.formatDynamicPath(2, testData.get("L" + 3 + "-OrdinanceLaw"))
							.click();
				}
			}
		}

		Assertions.verify(
				dwellingPage.dwelling23.checkIfElementIsPresent()
						&& dwellingPage.dwelling23.checkIfElementIsDisplayed(),
				false, "Create Quote Page", "Dwelling 2-2 not displayed" + ext_rpt_msg, false, false);
		Assertions.verify(
				dwellingPage.dwelling32.checkIfElementIsPresent()
						&& dwellingPage.dwelling32.checkIfElementIsDisplayed(),
				false, "Create Quote Page", "Dwelling 3-1 not displayed" + ext_rpt_msg, false, false);

		// Create Quote Page Grid is verified testData = data.get(data_Value2);
		for (int i = 1; i <= Integer.parseInt(testData.get("LocCount")); i++) {
			for (int j = 1; j <= Integer.parseInt(testData.get("L" + i + "-DwellingCount")); j++) {
				Assertions.addInfo("<span class='group'> Create Quote Page</span>",
						"<span class='group'> Location " + i + " - Dwelling " + i + "-" + j + " </span>");
				BaseWebElementControl locationDwelling = new BaseWebElementControl(
						By.xpath("//span[contains(text(),'Location " + i + " - Dwelling " + i + "-" + j + "')]"));
				TextFieldControl covA_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[2]//input"));
				TextFieldControl covB_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[3]//td[2]//input"));
				TextFieldControl covC_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[4]//td[2]//input"));
				TextFieldControl covD_NHinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[5]//td[2]//input"));
				BaseWebElementControl covA_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covB_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covC_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covD_NHlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				TextFieldControl covA_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[3]//input"));
				TextFieldControl covB_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[3]//td[3]//input"));
				TextFieldControl covC_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[4]//td[3]//input"));
				TextFieldControl covD_EQinputBox = new TextFieldControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[5]//td[3]//input"));
				BaseWebElementControl covA_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covB_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covC_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covD_EQlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covA_Floodlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covB_Floodlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covC_Floodlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				BaseWebElementControl covD_Floodlabel = new BaseWebElementControl(
						By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location " + i + " - Dwelling " + i
								+ "-" + j + "')]]]]]]//following-sibling::table[1]//tr[2]//td[4]//div"));
				if (locationDwelling.checkIfElementIsPresent() && locationDwelling.checkIfElementIsDisplayed()) {
					if (testData.get("L" + i + "D" + j + "-DwellingCovA") != "") {
						Assertions.verify(covA_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovA").replace(",", ""), "Create Quote Page",
								"Coverage A Named Hurricane value: " + covA_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covA_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovA").replace(",", ""),
										"Create Quote Page", "Coverage A Earthquake value: "
												+ covA_EQinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covA_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage A Named Hurricane value: " + covA_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covA_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage A Earthquake value: " + covA_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovB") != "") {
						Assertions.verify(covB_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovB").replace(",", ""), "Create Quote Page",
								"Coverage B Named Hurricane value: " + covB_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covB_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovB").replace(",", ""),
										"Create Quote Page", "Coverage B Earthquake value: "
												+ covB_EQinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covB_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage B Named Hurricane value: " + covB_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covB_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage B Earthquake value: " + covB_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovC") != "") {
						Assertions.verify(covC_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovC").replace(",", ""), "Create Quote Page",
								"Coverage C Named Hurricane value: " + covC_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covC_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovC").replace(",", ""),
										"Create Quote Page", "Coverage C Earthquake value: "
												+ covC_NHinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covC_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage C Named Hurricane value: " + covC_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covC_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage C Earthquake value: " + covC_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovD") != "") {
						Assertions.verify(covD_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovD").replace(",", ""), "Create Quote Page",
								"Coverage D Named Hurricane value: " + covD_NHinputBox.getAttrributeValue("Value")
										+ ext_rpt_msg,
								false, false);
						Assertions
								.verify(covD_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovD").replace(",", ""),
										"Create Quote Page", "Coverage D Earthquake value: "
												+ covD_EQinputBox.getAttrributeValue("Value") + ext_rpt_msg,
										false, false);
					} else {
						Assertions.verify(covD_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage D Named Hurricane value: " + covD_NHlabel.getData() + ext_rpt_msg, false,
								false);
						Assertions.verify(covD_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage D Earthquake value: " + covD_EQlabel.getData() + ext_rpt_msg, false, false);
					}
					Assertions.verify(covA_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage A Flood value: " + covA_Floodlabel.getData() + ext_rpt_msg, false, false);
					Assertions.verify(covB_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage B Flood value: " + covB_Floodlabel.getData() + ext_rpt_msg, false, false);
					Assertions.verify(covC_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage C Flood value: " + covC_Floodlabel.getData() + ext_rpt_msg, false, false);
					Assertions.verify(covD_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage D Flood value: " + covD_Floodlabel.getData() + ext_rpt_msg, false, false);
				}
			}
		}
		Assertions.passTest("Create Quote Page", "Insured values verified successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.override.scrollToElement();
		createQuotePage.override.click();

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		Assertions.verify(accountOverviewPage.acntOverviewQuoteNumber.checkIfElementIsDisplayed(), true,
				"Account Overview page", "Quote Number genearted" + ext_rpt_msg, false, false);
		quoteNumber = accountOverviewPage.acntOverviewQuoteNumber.getData();
		Assertions.verify(accountOverviewPage.acntOverviewQuoteNumber.getData(), quoteNumber, "Account Overview page",
				"Quote Number is :" + accountOverviewPage.acntOverviewQuoteNumber.getData(), false, false);
		Assertions.verify(accountOverviewPage.nhEqVar.checkIfElementIsDisplayed(), true, "Account Overview page",
				"Named Hurricance and Earthquake var displayed" + ext_rpt_msg, false, false);
		testData = data.get(data_Value1);
		accountOverviewPage.editInsuredContactInfo.scrollToElement();
		accountOverviewPage.editInsuredContactInfo.click();
		Assertions.verify(editContactInfoPage.namedInsured.getData(), testData.get("InsuredName"),
				"Edit Insured Contact Info Page", "Named Insured " + ext_rpt_msg, false, false);
		editContactInfoPage.insuredEmail.setData(testData.get("InsuredEmail"));
		editContactInfoPage.insuredPhoneNoAreaCode.setData(testData.get("InsuredPhoneNumAreaCode"));
		editContactInfoPage.insuredPhoneNoPrefix.setData(testData.get("InsuredPhoneNumPrefix"));
		editContactInfoPage.insuredPhoneNoEnd.setData(testData.get("InsuredPhoneNum"));
		editContactInfoPage.useExistingAddress.scrollToElement();
		editContactInfoPage.useExistingAddress.click();
		ButtonControl existingAddressOption = new ButtonControl(
				By.xpath("//a[contains(text(),'" + testData.get("L1D1-DwellingAddress") + ", "
						+ testData.get("L1D1-DwellingCity") + ", " + "HI " + testData.get("L1D1-DwellingZIP") + "')]"));
		existingAddressOption.scrollToElement();
		existingAddressOption.click();
		Assertions.verify(editContactInfoPage.manualAddressLine1.getData(), testData.get("L1D1-DwellingAddress"),
				"Edit Contact Info page", "Address 1" + ext_rpt_msg, false, false);
		Assertions.verify(editContactInfoPage.city.getData(), testData.get("L1D1-DwellingCity"),
				"Edit Contact Info page", "Address 1" + ext_rpt_msg, false, false);
		Assertions.verify(editContactInfoPage.zipCode.getData(), testData.get("L1D1-DwellingZIP"),
				"Edit Contact Info page", "Address 1" + ext_rpt_msg, false, false);
		editContactInfoPage.update.scrollToElement();
		editContactInfoPage.update.click();
		editContactInfoPage.update.waitTillInVisibilityOfElement(60);
		accountOverviewPage.requestBind.waitTillVisibilityOfElement(60);
		String accountTotalPremium = accountOverviewPage.totalPremiumValue.getData();
		String accountQuoteNumber = accountOverviewPage.acntOverviewQuoteNumber.getData();
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, accountQuoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");

		// Verifying quote Number,name and premium same as account Overview page
		// in Request Bind Page
		quoteNumber = requestBindPage.quoteNumber.getData();
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
		testData = data.get(data_Value1);
		requestBindPage.enterPolicyDetailsNAHO(testData);

		// Calendar c = Calendar.getInstance();
		// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //
		// c.add(Calendar.YEAR, 1);
		// String expirationDate = sdf.format(c.getTime());
		Assertions.verify(requestBindPage.effectiveDate.getData(), testData.get("PolicyEffDate"), "Request Bind Page",
				"Effective Date" + ext_rpt_msg, false, false);
		Assertions.verify(requestBindPage.expirationDate.getData(), testData.get("PolicyExpirationDate"),
				"Request Bind Page", "Expiration Date" + ext_rpt_msg, false, false);
		Assertions.verify(requestBindPage.namedInsured.getData(), testData.get("InsuredName"), "Request Bind Page",
				"Insured Name" + ext_rpt_msg, false, false);
		Assertions.verify(requestBindPage.insuredEmail.getData(), testData.get("InsuredEmail"), "Request Bind Page",
				"Insured Email" + ext_rpt_msg, false, false);
		Assertions.verify(
				requestBindPage.insuredPhoneNoAreaCode.getData() + requestBindPage.insuredPhoneNoPrefix.getData()
						+ requestBindPage.insuredPhoneNoEnd.getData(),
				testData.get("InsuredPhoneNumAreaCode") + testData.get("InsuredPhoneNumPrefix")
						+ testData.get("InsuredPhoneNum"),
				"Request Bind Page", "Insured Phone Number" + ext_rpt_msg, false, false);
		Assertions.verify(requestBindPage.insuredCountry.getData(), testData.get("InsuredCountry"), "Request Bind Page",
				"Insured Country" + ext_rpt_msg, false, false);
		Assertions.verify(requestBindPage.mailingAddress.getData(),
				testData.get("InsuredAddr1") + ", " + testData.get("InsuredCity") + ", " + testData.get("InsuredState")
						+ " " + testData.get("InsuredZIP"),
				"Request Bind Page", "Mailing Address" + ext_rpt_msg, false, false);
		requestBindPage.singlePay.scrollToElement();
		requestBindPage.singlePay.click();
		testData = data.get(data_Value3);
		requestBindPage.addInspectionContact(testData);
		testData = data.get(data_Value1);
		requestBindPage.addContactInformation(testData);
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		Assertions.passTest("Confirm Bind Request Page", PAGE_NAVIGATED);
		confirmBindRequestPage.requestBind.waitTillVisibilityOfElement(60);
		confirmBindRequestPage.requestBind.click();
		confirmBindRequestPage.requestBind.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
		if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
				&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
			requestBindPage.overrideEffectiveDate.select();
			requestBindPage.submit.waitTillVisibilityOfElement(60);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			confirmBindRequestPage.requestBind.waitTillVisibilityOfElement(60);
			confirmBindRequestPage.requestBind.click();
			confirmBindRequestPage.requestBind.waitTillInVisibilityOfElement(60);
		}

		// navigate to homepage
		if (bindRequestPage.homePage.checkIfElementIsPresent()
				&& bindRequestPage.homePage.checkIfElementIsDisplayed()) {
			homePage.scrollToTopPage();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Quote successfullly");
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral Link successfullly");
			Assertions.passTest("Referral Page", "Referral Page openned successfullly");
			referralPage.clickOnApprove();
			approveDeclineQuotePage.clickOnApprove();
			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.select();
				approveDeclineQuotePage.approveButton.waitTillVisibilityOfElement(60);
				approveDeclineQuotePage.approveButton.scrollToElement();
				approveDeclineQuotePage.approveButton.click();
			}
		}

		// Verification in Policy Summary Page
		policySummaryPage.policyNumber.waitTillVisibilityOfElement(60);
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsPresent(), true, "Policy Summary Page",
				"Policy Number Displayed" + ext_rpt_msg, false, false);
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policySummaryPage.getPolicynumber());
		Assertions.verify(policySummaryPage.policyStatus.getData(), testData.get("Status"), "Policy Summary Page",
				"Policy Active Status" + ext_rpt_msg, false, false);
		policySummaryPage.viewPolicySnapshot.click();
		Assertions.verify(policySummaryPage.policySnapshotScreen.checkIfElementIsDisplayed(), true,
				"Policy Summary Page", "Policy Snapshot Document displayed" + ext_rpt_msg, false, false);
		createQuotePage.goBack.click();
		createQuotePage.quoteLink.waitTillVisibilityOfElement(60);
		createQuotePage.quoteLink.scrollToElement();
		createQuotePage.quoteLink.click();
		Assertions.verify(createQuotePage.quoteDetails.checkIfElementIsDisplayed(), true, "Quote Details Page",
				"Quote Details Document displayed" + ext_rpt_msg, false, false);
		createQuotePage.closeButton.click();
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC007", "Executed Successfully");

	}
}
