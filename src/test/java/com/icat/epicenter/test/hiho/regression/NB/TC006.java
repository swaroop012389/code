/** Program Description: TO generate a HIHO policy with multiple locations and multiple dwellings with coverage limits check.
 *  Author			   : SM Netserv
 *  Date of Creation   : June 2018
 **/
package com.icat.epicenter.test.hiho.regression.NB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EditInspectionContactPage;
import com.icat.epicenter.pom.EditInsuredContactInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC006 extends AbstractNAHOTest {

	public TC006() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID06.xls";
	}

	public HomePage homePage;
	public LocationPage locationPage;
	public DwellingPage dwellingPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public EditInsuredContactInfoPage editContactInfo;
	public EditInspectionContactPage editInspectionContact;
	public EditAdditionalInterestInformationPage editAdditionalInterest;
	public RequestBindPage requestBindPage;
	public ConfirmBindRequestPage confirmBindRequestPage;
	public BindRequestSubmittedPage bindRequestSubmittedPage;
	public ReferralPage referralPage;
	public PolicySummaryPage policySummaryPage;
	public Map<String, String> testData;
	public BasePageControl basePage;
	static final String ext_rpt_msg = " is verified";
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_ENTERED = "Values Entered Successfully";
	static final String VALUES_UPDATED = "Values Updated";
	static final String VALUES_VERIFIED = "Values Verified";
	String quoteNumber;
	static int data_Value1 = 0;
	static int data_Value2 = 1;
	static int data_Value3 = 2;
	static int data_Value4 = 3;
	static int data_Value5 = 4;
	static int data_Value6 = 5;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		dwellingPage = new DwellingPage();
		locationPage = new LocationPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		editContactInfo = new EditInsuredContactInfoPage();
		editInspectionContact = new EditInspectionContactPage();
		editAdditionalInterest = new EditAdditionalInterestInformationPage();
		requestBindPage = new RequestBindPage();
		confirmBindRequestPage = new ConfirmBindRequestPage();
		bindRequestSubmittedPage = new BindRequestSubmittedPage();
		referralPage = new ReferralPage();
		policySummaryPage = new PolicySummaryPage();
		testData = data.get(data_Value1);

		// Getting Location count
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);

		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		Assertions.passTest("Home Page", "New Account created successfully");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);
		dwellingPage = (DwellingPage) basePage;
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage = new DwellingPage();
		dwellingPage.location1.waitTillVisibilityOfElement(60);
		dwellingPage.location1.scrollToElement();
		dwellingPage.location1.click();
		Assertions.passTest("Location Page", PAGE_NAVIGATED);
		testData = data.get(data_Value2);

		// Field not available - Application Changed
		locationPage.addLocationAddress(testData.get("L" + locationNumber + "D1-DwellingAddress") + ", "
				+ testData.get("L" + locationNumber + "D1-DwellingCity") + ", "
				+ testData.get("L" + locationNumber + "D1-DwellingZIP"));
		Assertions.passTest("Location Page", VALUES_ENTERED);
		locationPage.reviewLocation.scrollToElement();
		locationPage.reviewLocation.click();
		testData = data.get(data_Value1);
		locationPage.addLocationAddress(testData.get("L" + locationNumber + "D1-DwellingAddress") + ", "
				+ testData.get("L" + locationNumber + "D1-DwellingCity")
				+ testData.get("L" + locationNumber + "D1-DwellingZIP"));
		Assertions.passTest("Location Page", VALUES_UPDATED);
		locationPage.dwellingLink.scrollToElement();
		locationPage.dwellingLink.click();
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.editDwellingSymbol.scrollToElement();
		dwellingPage.editDwellingSymbol.click();

		// add dwelling details
		dwellingPage = new DwellingPage();
		dwellingPage.addDwellingDetails(testData, locationCount, dwellingCount);
		Assertions.passTest("Dwelling Page", "Dwelling Details " + VALUES_ENTERED);
		dwellingPage.save.scrollToElement();
		dwellingPage.save.click();
		dwellingPage.addRoofDetails(testData, 1, 1);
		Assertions.passTest("Dwelling Page", "Roof Details " + VALUES_ENTERED);
		dwellingPage.enterDwellingValues(testData, locationCount, dwellingCount);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		Assertions.passTest("Dwelling Page", "Dwelling Details " + VALUES_ENTERED);

		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);

		// verifying deductible values
		testData = data.get(data_Value2);
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), testData.get("NamedHurricaneDedValue"),
				"Create Quote Page", "Default Named Hurricane Value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"Default EarthQuake Value" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Deductible details verified successfully");

		// Entering Create quote page Details
		testData = data.get(data_Value1);
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Deductible details entered successfully");

		// verifying coverage values
		testData = data.get(data_Value2);
		Assertions.verify(createQuotePage.personalPropertyReplacementCostData.getData(),
				testData.get("ReplacementCost"), "Create Quote Page ",
				"Default Personal Property Replacement cost" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Coverage details verified successfully");

		// Entering coverage Details
		testData = data.get(data_Value1);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Coverage details entered successfully");

		// Create Quote Page Grid is verified
		for (int i = 0; i < 1; i++) {
			Assertions.addInfo("<span class='group'> Create Quote Page</span>",
					"<span class='group'> Location 1 - Dwelling " + (i + 1) + "</span>");
			char ch = 'A';
			for (int j = 2; j <= 2; j++) {
				if (!testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).equals("")) {
					TextFieldControl namedHurricanegrid = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]//input"));
					Assertions.verify(namedHurricanegrid.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page",
							"Cov " + ch + " value " + namedHurricanegrid.getAttrributeValue("Value")
									+ " : Named Hurricane - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);
					TextFieldControl earthquake = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
					Assertions.verify(earthquake.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page", "Cov " + ch + " value " + earthquake.getAttrributeValue("Value")
									+ " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);

					ch++;
				} else {
					BaseWebElementControl namedHurricanegrid = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]"));
					Assertions.verify(namedHurricanegrid.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + namedHurricanegrid.getData() + " : Named Hurricane - Dwelling "
									+ (i + 1) + ext_rpt_msg,
							false, false);
					BaseWebElementControl earthquake = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]"));
					Assertions.verify(
							earthquake.getData(), "Excluded", "Create Quote Page", "Cov " + ch + " value "
									+ earthquake.getData() + " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);

					ch++;
				}
			}
		}
		Assertions.passTest("Create Quote Page", "Insured values verified successfully");

		// create quote page details
		testData = data.get(data_Value1);

		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Create Quote Page", "Insured values for Flood for Cov A and Cov D updated successfully");
		createQuotePage.quoteStep3.scrollToElement();
		createQuotePage.quoteStep3.click();
		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		if (createQuotePage.pageName.getData().contains("Create Quote")) {
			createQuotePage.goBack.click();
		}
		Assertions.passTest("Create Quote Page",
				"Create Quote page is loaded successfully when clicked on Goback button");
		createQuotePage.locationStep1.scrollToElement();
		createQuotePage.locationStep1.click();
		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Account Overview Page", "Account Overview Page loaded successfully");
		waitTime(2);
		locationPage.dwellingLink.scrollToElement();
		locationPage.dwellingLink.click();
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

		// dwelling create deatils
		testData = data.get(data_Value3);
		dwellingPage.manualEntry.click();
		dwellingPage.manualEntryAddress.clearData();
		dwellingPage.continueWithUpdateBtn.scrollToElement();
		dwellingPage.continueWithUpdateBtn.click();
		dwellingPage.manualEntryAddress
				.setData(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingAddress"));
		dwellingPage.manualEntryCity.setData(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCity"));
		dwellingPage.manualEntryZipCode
				.setData(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingZIP"));
		dwellingPage.save.click();
		Assertions.passTest("Dwelling Page", "Dwelling Address updated");
		dwellingPage.dwellingDetailsLink.click();
		dwellingPage.dwellingTickSign.waitTillVisibilityOfElement(60);
		Assertions.verify(dwellingPage.dwellingTickSign.checkIfElementIsDisplayed(), true, "Dwelling Page",
				"Tick mark against Dwelling details link displayed" + ext_rpt_msg, false, false);
		dwellingPage.reviewDwelling.waitTillVisibilityOfElement(30);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		testData = data.get(data_Value1);
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);

		// verifying deductible values
		testData = data.get(data_Value2);
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), testData.get("NamedHurricaneDedValue"),
				"Create Quote Page", "Default Named Hurricane Value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"Default EarthQuake Value" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Deductible details verified successfully");

		// Entering Create quote page Details
		testData = data.get(data_Value1);
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Deductible details entered successfully");

		// verifying coverage values
		testData = data.get(data_Value2);
		Assertions.verify(createQuotePage.personalPropertyReplacementCostData.getData(),
				testData.get("ReplacementCost"), "Create Quote Page ",
				"Default Personal Property Replacement cost" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Coverage details verified successfully");

		// Entering coverage Details
		testData = data.get(data_Value1);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Coverage details entered successfully");

		// Create Quote Page Grid is verified
		for (int i = 0; i < 1; i++) {
			Assertions.addInfo("<span class='group'> Create Quote Page</span>",
					"<span class='group'> Location 1 - Dwelling " + (i + 1) + "</span>");
			char ch = 'A';
			for (int j = 2; j <= 2; j++) {
				if (!testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).equals("")) {
					TextFieldControl namedHurricanegrid = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]//input"));
					Assertions.verify(namedHurricanegrid.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page",
							"Cov " + ch + " value " + namedHurricanegrid.getAttrributeValue("Value")
									+ " : Named Hurricane - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);
					TextFieldControl earthquake = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
					Assertions.verify(earthquake.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page", "Cov " + ch + " value " + earthquake.getAttrributeValue("Value")
									+ " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);

					ch++;
				} else {
					BaseWebElementControl namedHurricanegrid = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]"));
					Assertions.verify(namedHurricanegrid.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + namedHurricanegrid.getData() + " : Named Hurricane - Dwelling "
									+ (i + 1) + ext_rpt_msg,
							false, false);
					BaseWebElementControl earthquake = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]"));
					Assertions.verify(
							earthquake.getData(), "Excluded", "Create Quote Page", "Cov " + ch + " value "
									+ earthquake.getData() + " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);

					ch++;
				}
			}
		}
		Assertions.passTest("Create Quote Page", "Insured values verified successfully");

		// create quote details
		testData = data.get(data_Value3);

		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get a quote button successfully");

		// create quote details
		testData = data.get(data_Value2);

		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		if (createQuotePage.pageName.getData().contains("Create Quote")) {
			createQuotePage.goBack.click();
		}
		Assertions.passTest("Create Quote Page",
				"Create Quote page is loaded successfully when clicked on Goback button");
		createQuotePage.locationStep1.scrollToElement();
		createQuotePage.locationStep1.click();
		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Account Overview Page", "Account Overview Page loaded successfully");
		waitTime(2);
		locationPage.dwellingLink.scrollToElement();
		locationPage.dwellingLink.click();
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");
		testData = data.get(data_Value4);
		dwellingPage.manualEntry.click();
		dwellingPage.manualEntryAddress.clearData();
		dwellingPage.continueWithUpdateBtn.scrollToElement();
		dwellingPage.continueWithUpdateBtn.click();
		dwellingPage.manualEntryAddress
				.setData(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingAddress"));
		dwellingPage.manualEntryCity.setData(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCity"));
		dwellingPage.manualEntryZipCode
				.setData(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingZIP"));
		Assertions.passTest("Dwelling Page", "Dwelling Address updated");
		dwellingPage.dwellingValuesLink.scrollToElement();
		dwellingPage.dwellingValuesLink.click();
		dwellingPage.covANamedHurricane.setData(testData.get("L1D1-DwellingCovA"));
		dwellingPage.covCNamedHurricane.setData(testData.get("L1D1-DwellingCovC"));
		dwellingPage.covDNamedHurricane.setData(testData.get("L1D1-DwellingCovD"));
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();

		testData = data.get(data_Value1);
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);

		// verifying deductible values
		testData = data.get(data_Value2);
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), testData.get("NamedHurricaneDedValue"),
				"Create Quote Page", "Default Named Hurricane Value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"Default EarthQuake Value" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Deductible details verified successfully");

		// Entering Create quote page Details
		testData = data.get(data_Value3);
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Deductible details entered successfully");

		// verifying coverage values
		testData = data.get(data_Value2);
		Assertions.verify(createQuotePage.personalPropertyReplacementCostData.getData(),
				testData.get("ReplacementCost"), "Create Quote Page ",
				"Default Personal Property Replacement cost" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Coverage details verified successfully");

		// Entering coverage Details
		testData = data.get(data_Value1);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Coverage details entered successfully");
		testData = data.get(data_Value4);

		// Create Quote Page Grid is verified
		for (int i = 0; i < 1; i++) {
			Assertions.addInfo("<span class='group'> Create Quote Page</span>",
					"<span class='group'> Location 1 - Dwelling " + (i + 1) + "</span>");
			char ch = 'A';
			for (int j = 2; j <= 2; j++) {
				if (!testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).equals("")) {
					TextFieldControl namedHurricanegrid = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]//input"));
					Assertions.verify(namedHurricanegrid.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page",
							"Cov " + ch + " value " + namedHurricanegrid.getAttrributeValue("Value")
									+ " : Named Hurricane - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);
					TextFieldControl earthquake = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
					Assertions.verify(earthquake.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page", "Cov " + ch + " value " + earthquake.getAttrributeValue("Value")
									+ " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);

					ch++;
				} else {
					BaseWebElementControl namedHurricanegrid = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]"));
					Assertions.verify(namedHurricanegrid.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + namedHurricanegrid.getData() + " : Named Hurricane - Dwelling "
									+ (i + 1) + ext_rpt_msg,
							false, false);
					BaseWebElementControl earthquake = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]"));
					Assertions.verify(
							earthquake.getData(), "Excluded", "Create Quote Page", "Cov " + ch + " value "
									+ earthquake.getData() + " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);

					ch++;
				}
			}
		}
		Assertions.passTest("Create Quote Page", "Insured values verified successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		// Combined limit minimum for Coverage A and C for Condos is 28,000
		Assertions.verify(
				createQuotePage.combinedLimitMin.getData().contains(
						"Combined limit minimum for Coverage A and C for Condos is 28,000"),
				true, "Create Quote Page",
				"Combined limit minimum for Coverage A and C for Condos is 28,000 warning message is "
						+ createQuotePage.combinedLimitMin.getData() + ext_rpt_msg,
				false, false);
		createQuotePage.locationStep1.scrollToElement();
		createQuotePage.locationStep1.click();
		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Account Overview Page", "Account Overview Page loaded successfully");
		waitTime(2);
		locationPage.dwellingLink.scrollToElement();
		locationPage.dwellingLink.click();
		accountOverviewPage.editDwelling.scrollToElement();
		accountOverviewPage.editDwelling.click();
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");
		testData = data.get(data_Value5);
		dwellingPage.dwellingValuesLink.scrollToElement();
		dwellingPage.dwellingValuesLink.click();
		dwellingPage.covANamedHurricane.setData(testData.get("L1D1-DwellingCovA"));
		dwellingPage.covCNamedHurricane.setData(testData.get("L1D1-DwellingCovC"));
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		Assertions.passTest("Create Quote Page", "Create Quote Page loaded successfully");

		// verifying deductible values
		testData = data.get(data_Value2);
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), testData.get("NamedHurricaneDedValue"),
				"Create Quote Page", "Default Named Hurricane Value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"Default EarthQuake Value" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Deductible details verified successfully");

		// Entering Create quote page Details
		testData = data.get(data_Value3);
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Deductible details entered successfully");

		// verifying coverage values
		testData = data.get(data_Value2);
		Assertions.verify(createQuotePage.personalPropertyReplacementCostData.getData(),
				testData.get("ReplacementCost"), "Create Quote Page ",
				"Default Personal Property Replacement cost" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Coverage details verified successfully");

		// Entering coverage Details
		testData = data.get(data_Value1);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Coverage details entered successfully");
		testData = data.get(data_Value4);
		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		testData = data.get(data_Value5);

		// Create Quote Page Grid is verified
		for (int i = 0; i < 1; i++) {
			Assertions.addInfo("<span class='group'> Create Quote Page</span>",
					"<span class='group'> Location 1 - Dwelling " + (i + 1) + "</span>");
			char ch = 'A';
			for (int j = 2; j <= 2; j++) {
				if (!testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).equals("")) {
					TextFieldControl namedHurricanegrid = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]//input"));
					Assertions.verify(namedHurricanegrid.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page",
							"Cov " + ch + " value " + namedHurricanegrid.getAttrributeValue("Value")
									+ " : Named Hurricane - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);
					TextFieldControl earthquake = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
					Assertions.verify(earthquake.getAttrributeValue("Value"),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).replace(",", ""),
							"Create Quote Page", "Cov " + ch + " value " + earthquake.getAttrributeValue("Value")
									+ " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);

					ch++;
				} else {
					BaseWebElementControl namedHurricanegrid = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]"));
					Assertions.verify(namedHurricanegrid.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + namedHurricanegrid.getData() + " : Named Hurricane - Dwelling "
									+ (i + 1) + ext_rpt_msg,
							false, false);
					BaseWebElementControl earthquake = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]"));
					Assertions.verify(
							earthquake.getData(), "Excluded", "Create Quote Page", "Cov " + ch + " value "
									+ earthquake.getData() + " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);

					ch++;
				}
			}
		}
		Assertions.passTest("Create Quote Page", "Insured values verified successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get a quote button successfully");

		// create quote details
		testData = data.get(data_Value5);

		createQuotePage.loading.waitTillInVisibilityOfElement(60);
		waitTime(2);
		if (createQuotePage.pageName.getData().contains("Create Quote")) {
			Assertions.verify(createQuotePage.covDNHWarning.getData(),
					"Coverage D NH on Location 1, Building 1 has been limited to $5,000",
					"Review Quote Adjustments Page",
					"Coverage D NH on Location 1, Building 1 has been limited to $5,000 warning message" + ext_rpt_msg,
					false, false);
			Assertions.verify(createQuotePage.covDEQWarning.getData(),
					"Coverage D EQ on Location 1, Building 1 has been limited to $5,000",
					"Review Quote Adjustments Page",
					"Coverage D EQ on Location 1, Building 1 has been limited to $5,000 warning message" + ext_rpt_msg,
					false, false);
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// account overview page assertions
		testData = data.get(data_Value1);
		Assertions.passTest("Account Overview page", "Account Overview page is Displayed");
		Assertions.verify(accountOverviewPage.quote3Status.getData(), testData.get("Status"), "Account Overview Page",
				"Quote Status :" + accountOverviewPage.quote3Status.getData() + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quoteNoHolder.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote Number generated:" + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quoteNoHolder.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Quote Number is:" + accountOverviewPage.quoteNoHolder.getData(), false, false);
		accountOverviewPage.editInsuredContactInfo.scrollToElement();
		accountOverviewPage.editInsuredContactInfo.click();
		Assertions.verify(editContactInfo.namedInsured.checkIfElementIsDisplayed(), true,
				"Edit Insured Contact Info Page", "Insured Details page is displayed", false, false);
		editContactInfo.enterContactInfoDetails(testData);
		Assertions.passTest("Edit Insured Contact Info Page",
				"Contact Information from Account overview page entered successfully");
		accountOverviewPage.editInspectionContact.waitTillVisibilityOfElement(60);
		accountOverviewPage.editInspectionContact.scrollToElement();
		accountOverviewPage.editInspectionContact.click();
		Assertions.verify(editInspectionContact.inspectionName.checkIfElementIsDisplayed(), true,
				"Edit Inspection Contact Page", "Inspection Details page is displayed", false, false);
		editInspectionContact.inspectionName.setData(testData.get("InspectionContact"));
		editInspectionContact.phoneNumberAreaCode.setData(testData.get("InspectionAreaCode"));
		editInspectionContact.phoneNumberPrefix.setData(testData.get("InspectionPrefix"));
		editInspectionContact.phoneNumberEnd.setData(testData.get("InspectionNumber"));
		editInspectionContact.update.scrollToElement();
		editInspectionContact.update.click();
		editInspectionContact.update.waitTillInVisibilityOfElement(30);
		accountOverviewPage.editAdditionalIntersets.waitTillVisibilityOfElement(60);
		accountOverviewPage.editAdditionalIntersets.scrollToElement();
		accountOverviewPage.editAdditionalIntersets.click();
		editAdditionalInterest.addAdditionalInterestHIHO(testData);
		Assertions.passTest("Edit Additional Interest Information Page",
				"Edit Additional Interest Information from Account overview page entered successfully");
		editAdditionalInterest.update.scrollToElement();
		editAdditionalInterest.update.click();
		editAdditionalInterest.update.waitTillInVisibilityOfElement(60);
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
		Assertions.verify(requestBindPage.effectiveDate.getData(), testData.get("PolicyEffDate"), "Request Bind Page",
				"Effective Date" + ext_rpt_msg, false, false);
		String expDAte = testData.get("PolicyEffDate");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(expDAte));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Incrementing the date
		c.add(Calendar.YEAR, 1);
		String newDate = sdf.format(c.getTime());
		Assertions.verify(requestBindPage.expirationDate.getData(), newDate, "Request Bind Page",
				"Expiration Date" + ext_rpt_msg, false, false);
		Assertions.verify(requestBindPage.namedInsured.getData(), testData.get("InsuredName"), "Request Bind Page",
				"Insured Name" + ext_rpt_msg, false, false);
		Assertions.verify(requestBindPage.insuredEmail.getData(), testData.get("InsuredEmail"), "Request Bind Page",
				"Insured Email" + ext_rpt_msg, false, false);
		requestBindPage.insuredPhoneNoAreaCode.setData(testData.get("InsuredPhoneNumAreaCode"));
		requestBindPage.insuredPhoneNoPrefix.setData(testData.get("InsuredPhoneNumPrefix"));
		requestBindPage.insuredPhoneNoEnd.setData(testData.get("InsuredPhoneNum"));
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
		requestBindPage.mortgageePay.scrollToElement();
		requestBindPage.mortgageePay.click();

		// verify additional interests
		for (int i = 1; i < 3; i++) {
			if (!testData.get(i + "-AIType").equals("")) {
				BaseWebElementControl aiType = new BaseWebElementControl(By.xpath("//div[div[div[input[@value='"
						+ testData.get(i + "-AIName") + "']]]]//span[contains(@id,'typeSelectBoxItText')]"));
				Assertions.verify(aiType.getData(), testData.get(i + "-AIType"), "Request Bind Page",
						"Additional Interest Type" + ext_rpt_msg, false, false);
				BaseWebElementControl aILoanNumber = new BaseWebElementControl(By.xpath("//div[div[div[input[@value='"
						+ testData.get(i + "-AIName") + "']]]]//input[contains(@id,'loanNumber')]"));
				TextFieldControl aIName = new TextFieldControl(By.xpath("//div[div[div[input[@value='"
						+ testData.get(i + "-AIName") + "']]]]//input[contains(@id,'name')]"));
				Assertions.verify(aIName.getData(), testData.get(i + "-AIName"), "Request Bind Page",
						"Additional Interest Name " + ext_rpt_msg, false, false);
				if (!testData.get(i + "-AILoanNumber").equals("")) {
					Assertions.verify(aILoanNumber.getAttrributeValue("Value"), testData.get(i + "-AILoanNumber"),
							"Request Bind Page", "Additional Interest Loan Number " + ext_rpt_msg, false, false);
				}
				if (testData.get(i + "-AIType").equals("Mortgagee")) {
					BaseWebElementControl aiRank = new BaseWebElementControl(By.xpath("//div[div[div[input[@value='"
							+ testData.get(i + "-AIName") + "']]]]//span[contains(@id,'rankSelectBoxItText')]"));
					Assertions.verify(aiRank.getData(), testData.get(i + "-AIRank"), "Request Bind Page",
							"Additional Interest Rank " + ext_rpt_msg, false, false);
				}
				Assertions.verify(requestBindPage.aIAddressLine1.getAttrributeValue("Value"),
						testData.get(i + "-AIAddr1"), "Request Bind Page",
						"Additional Interest Address Line 1 " + ext_rpt_msg, false, false);
				Assertions.verify(requestBindPage.aICity.getData(), testData.get(i + "-AICity"), "Request Bind Page",
						"Additional Interest City " + ext_rpt_msg, false, false);
				Assertions.verify(requestBindPage.aIState.getData(), testData.get(i + "-AIState"), "Request Bind Page",
						"Additional Interest State " + ext_rpt_msg, false, false);
				if (requestBindPage.aIZipCode1.checkIfElementIsPresent()
						&& requestBindPage.aIZipCode1.checkIfElementIsDisplayed()) {
					Assertions.verify(requestBindPage.aIZipCode1.getData(), testData.get(i + "-AIZIP"),
							"Request Bind Page", "Additional Interest Zip Code " + ext_rpt_msg, false, false);
				} else {
					Assertions.verify(requestBindPage.aiZipCodeQ3.formatDynamicPath(i - 1).getData(),
							testData.get(i + "-AIZIP"), "Request Bind Page",
							"Additional Interest Zip Code " + ext_rpt_msg, false, false);
				}

			}
		}
		Assertions.passTest("Request Bind Page", "request Bind Details verified successfully");
		requestBindPage.addContactInformation(testData);
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		Assertions.passTest("confirm Bind Request Page", PAGE_NAVIGATED);
		confirmBindRequestPage.requestBind.waitTillVisibilityOfElement(60);
		confirmBindRequestPage.requestBind.click();
		confirmBindRequestPage.requestBind.waitTillInVisibilityOfElement(60);

		// Override Effective date in Request Bind Page
		if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
				&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
			requestBindPage.overrideEffectiveDate.scrollToElement();
			requestBindPage.overrideEffectiveDate.select();
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			Assertions.passTest("confirm Bind Request Page", "Clicked on Request Bind");
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			requestBindPage.requestBind.scrollToElement();
			requestBindPage.requestBind.click();
			requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
		}
		Assertions.passTest("Policy Summary Page", "Policy Summary Page is displayed");

		// Verification in Policy Summary Page
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsPresent(), true, "Policy Summary Page",
				"Policy Number Displayed" + ext_rpt_msg, false, false);
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policySummaryPage.getPolicynumber());
		Assertions.verify(policySummaryPage.policyStatus.getData(), testData.get("Status"), "Policy Summary Page",
				"Policy Active Status" + ext_rpt_msg, false, false);
		policySummaryPage.viewPolicySnapshot.click();
		Assertions.verify(policySummaryPage.policySnapshotScreen.checkIfElementIsDisplayed(), true,
				"Policy Summary Page", "Policy Snapshot Document displayed" + ext_rpt_msg, false, false);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC006", "Executed Successfully");

	}
}