/** Program Description: To generate a HIHO policy with Dwelling Type Dwelling, single location single building, Mortgagee pay and assert values.
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
import com.NetServAutomationFramework.generic.DateConversions;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountDetails;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EmailQuotePage;
import com.icat.epicenter.pom.FloodPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC005 extends AbstractNAHOTest {

	public TC005() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID05.xls";
	}

	public HomePage homePage;
	public DwellingPage dwellingPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public BindRequestSubmittedPage bindRequestPage;
	public ReferralPage referralPage;
	public PolicySummaryPage policySummaryPage;
	public BuildingUnderMinimumCostPage dwellingCost;
	public LocationPage locationPage;
	public AccountDetails accountDetailsPage;
	public FloodPage floodPage;
	public EmailQuotePage emailQuote;
	public ConfirmBindRequestPage confirmBindRequestPage;
	public Map<String, String> testData;
	public BasePageControl basePage;
	String quoteNumber;
	String policyNumber;
	static final String ext_rpt_msg = " is verified";
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_UPDATED = "Values Updated";
	static final String VALUES_ENTERED = "Values Entered Successfully";
	static final String VALUES_VERIFIED = "Values Verified";
	static int data_Value1 = 0;
	static int data_Value2 = 1;
	static int data_Value3 = 2;
	static int data_Value4 = 3;
	static int data_Value5 = 4;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing all Objects
		homePage = new HomePage();
		locationPage = new LocationPage();
		dwellingPage = new DwellingPage();
		dwellingCost = new BuildingUnderMinimumCostPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		accountDetailsPage = new AccountDetails();
		policySummaryPage = new PolicySummaryPage();
		floodPage = new FloodPage();
		emailQuote = new EmailQuotePage();
		confirmBindRequestPage = new ConfirmBindRequestPage();
		requestBindPage = new RequestBindPage();
		testData = data.get(data_Value1);
		DateConversions date = new DateConversions();
		String insuredName = testData.get("InsuredName") + date.getCurrentDate("MM/dd/YYYY_hh:mm:ss");

		// Getting Location and Building count
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);

		// New account
		homePage.goToHomepage.click();
		homePage.createNewAccount.scrollToElement();
		homePage.createNewAccount.click();
		homePage.namedInsured.setData(insuredName);
		Assertions.passTest("Home Page", "Page Navigated");
		Assertions.passTest("Home Page", "New Account created successfully");
		Assertions.passTest("Home Page", "Insured Name is " + insuredName);

		homePage.producerNumber.setData("11250.1");
		if (homePage.productArrow.checkIfElementIsPresent()) {
			homePage.productArrow.click();
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection"))
					.waitTillVisibilityOfElement(60);
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
		}
		if (!testData.get("ProductSelection").equalsIgnoreCase("Commercial")) {
			if (homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(30);
				homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			} else if (homePage.effectiveDate.formatDynamicPath(2).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(2).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(2).waitTillVisibilityOfElement(30);
				homePage.effectiveDate.formatDynamicPath(2).setData(testData.get("PolicyEffDate"));
			} else {
				homePage.effectiveDateNew.waitTillVisibilityOfElement(60);
				homePage.effectiveDateNew.scrollToElement();
				homePage.effectiveDateNew.setData(testData.get("PolicyEffDate"));
			}
		}
		homePage.goButton.click();
		homePage.goButton.waitTillInVisibilityOfElement(30);
		basePage = new DwellingPage();
		Assertions.passTest("Home Page", VALUES_ENTERED);
		dwellingPage = (DwellingPage) basePage;

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, locationCount, 1);
		dwellingPage.addRoofDetails(testData, locationCount, 1);
		dwellingPage.enterDwellingValues(testData, locationCount, 1);
		Assertions.passTest("Dwelling Page", VALUES_ENTERED);
		dwellingPage.save.waitTillVisibilityOfElement(30);
		dwellingPage.save.scrollToElement();
		dwellingPage.save.click();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		dwellingPage.exclamatorySignDwellin1.waitTillInVisibilityOfElement(60);

		// find the account by entering insured name and producer name
		homePage.findAccountNamedInsured.setData(insuredName);
		homePage.findAccountProducerNumber.setData("11250.1");
		homePage.findBtn.click();
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);
		dwellingPage.dwelling1.click();
		dwellingPage.editBuilding.waitTillVisibilityOfElement(60);
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.editBuilding.click();
		dwellingPage.editBuilding.waitTillInVisibilityOfElement(60);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(60);
		dwellingCost.bringUpToCost.click();
		dwellingCost.bringUpToCost.waitTillInVisibilityOfElement(60);
		testData = data.get(data_Value4);
		Assertions.verify(dwellingPage.covAValue.getData(), testData.get("L1D1-DwellingCovA"), "Dwelling Page",
				"Coverage A value is updated with bring up to cost value " + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.dollarSymbol.checkIfElementIsDisplayed(), true, "Dwelling Page",
				"Dollar symbol" + ext_rpt_msg, false, false);
		testData = data.get(data_Value1);
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);

		// verifying deductible values
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), testData.get("NamedHurricaneDedApplicability"),
				"Create Quote Page", "Default Named Hurricane Value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"Default EarthQuake Value" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Deductible details verified successfully");

		// Entering Create quote page Details
		testData = data.get(data_Value2);
		createQuotePage.enterDeductibles(testData);

		// verifying coverage values
		testData = data.get(data_Value1);
		Assertions.verify(createQuotePage.personalPropertyReplacementCostData.getData(),
				testData.get("ReplacementCost"), "Create Quote Page ",
				"Default Personal Property Replacement cost" + ext_rpt_msg, false, false);
		testData = data.get(data_Value4);
		Assertions.verify(createQuotePage.enhancedReplacementCostDataHIHO.getData(), testData.get("EnhancedReplCost"),
				"Create Quote page", "Default Enhanced Replacement cost" + ext_rpt_msg, false, false);
		testData = data.get(data_Value1);
		Assertions.verify(createQuotePage.ordinancelawData.getData(), testData.get("OrdinanceOrLaw"),
				"Create Quote Page", "Default ordinance or Law value is 10% included" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Coverage details verified successfully");

		// Entering coverage Details
		testData = data.get(data_Value2);
		createQuotePage.addOptionalCoverageDetails(testData);

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
					Assertions.verify(namedHurricanegrid.getData(),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
							"Create Quote Page", "Cov " + ch + " value " + namedHurricanegrid.getData()
									+ " : Named Hurricane - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);
					TextFieldControl earthquakegrid = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
					Assertions.verify(earthquakegrid.getData(),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
							"Create Quote Page", "Cov " + ch + " value " + earthquakegrid.getData()
									+ " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);

					ch++;
				}
			}
		}
		Assertions.passTest("Create Quote Page", "Insured values verified successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.pageName.getData().contains("Create Quote")) {
			createQuotePage.continueButton.click();
		}
		testData = data.get(data_Value1);
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);
		String quote1 = accountOverviewPage.quoteNo1Holder.getData();
		Assertions.verify(accountOverviewPage.quoteNo1Holder.checkIfElementIsPresent(), true, "Account Overview Page",
				"Quote Number 1 displayed" + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quoteNo1Holder.getData(), quote1, "Account Overview Page",
				"Quote Number 1 is : " + accountOverviewPage.quoteNo1Holder.getData(), false, false);
		Assertions.verify(accountOverviewPage.quote1StatusActive.getData(), testData.get("Status"),
				"Account Overview Page", "Quote 1 Status" + ext_rpt_msg, false, false);
		accountOverviewPage.viewPrintFullQuoteLink.click();
		Assertions.verify(createQuotePage.quoteDetails.checkIfElementIsDisplayed(), true, "Quote Document",
				"Quote Document displayed" + ext_rpt_msg, false, false);
		createQuotePage.goBack.click();
		accountOverviewPage.emailQuoteLink.click();
		Assertions.verify(accountOverviewPage.pageName.getData().contains("Email Quote"), true, "Email Quote Page",
				"Email Quote page displayed" + ext_rpt_msg, false, false);
		emailQuote.cancel.scrollToElement();
		emailQuote.cancel.click();
		accountOverviewPage.overridePremiumLink.click();
		Assertions.verify(accountOverviewPage.overridePremiumPage.checkIfElementIsDisplayed(), true,
				"Override Premium and Fees Page", "Override Premium Screen displayed" + ext_rpt_msg, false, false);
		emailQuote.cancel.scrollToElement();
		emailQuote.cancel.click();
		accountOverviewPage.viewOrPrintRateTrace.click();
		Assertions.verify(accountOverviewPage.rateTraceForQuote.checkIfElementIsDisplayed(), true,
				"Rate Trace for Quote Page", "Rate Trace for Quote Screen displayed" + ext_rpt_msg, false, false);
		createQuotePage.backToAccountOverview.click();
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();
		Assertions.passTest("Edit Deductibles Page", "Edit deductibles navigates to create quote page verified");

		// verifying deductible values
		testData = data.get(data_Value2);
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), testData.get("NamedHurricaneDedApplicability"),
				"Create Quote Page", "Default Named Hurricane Value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"Default EarthQuake Value" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Deductible details verified successfully");
		testData = data.get(data_Value3);
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Deductible details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.pageName.getData().contains("Create Quote")) {
			createQuotePage.continueButton.click();
		}
		testData = data.get(data_Value1);
		String quote2 = accountOverviewPage.quoteNo2Holder.getData();
		Assertions.verify(accountOverviewPage.quoteNo2Holder.checkIfElementIsPresent(), true, "Account Overview Page",
				"Quote Number 2 displayed " + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quoteNo2Holder.getData(), quote2, "Account Overview Page",
				"Quote Number 2 is : " + accountOverviewPage.quoteNo2Holder.getData(), false, false);
		Assertions.verify(accountOverviewPage.quote2StatusActive.getData(), testData.get("Status"),
				"Account Overview Page", "Quote 2 Status" + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quote1StatusActive.getData(), testData.get("Status"),
				"Account Overview Page", "Quote 1 Status" + ext_rpt_msg, false, false);
		dwellingPage.dwellingLink1.scrollToElement();
		dwellingPage.dwellingLink1.click();
		dwellingPage.editBuilding.waitTillVisibilityOfElement(60);
		dwellingPage.editBuilding.scrollToElement();
		dwellingPage.editBuilding.click();

		// Commenting because of CR19068
		if (accountOverviewPage.yesButton.checkIfElementIsPresent()) {
			accountOverviewPage.yesButton.scrollToElement();
			accountOverviewPage.yesButton.click();
		}
		if (accountOverviewPage.yesIWantToContinue.checkIfElementIsPresent()) {
			accountOverviewPage.yesIWantToContinue.scrollToElement();
			accountOverviewPage.yesIWantToContinue.click();
			accountOverviewPage.yesIWantToContinue.waitTillInVisibilityOfElement(60);
		}

		dwellingPage.addSymbol.waitTillVisibilityOfElement(60);
		dwellingPage.addSymbol.scrollToElement();
		dwellingPage.addSymbol.click();
		dwellingPage.addNewDwelling.waitTillVisibilityOfElement(30);
		dwellingPage.addNewDwelling.scrollToElement();
		dwellingPage.addNewDwelling.click();

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, locationCount, 2);
		dwellingPage.addRoofDetails(testData, locationCount, 2);
		dwellingPage.enterDwellingValues(testData, locationCount, 2);
		Assertions.passTest("Dwelling Page", VALUES_UPDATED);
		Assertions.verify(dwellingPage.covCNamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
				"Coverage C not available for Course of Construction dwellings", false, false);
		Assertions.verify(dwellingPage.covDNamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
				"Coverage D not available for Course of Construction dwellings", false, false);
		Assertions.passTest("Dwelling Page", VALUES_VERIFIED);
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		testData = data.get(data_Value2);
		Assertions.verify(createQuotePage.globalErr.getData(), "Year built cannot be newer than current year.",
				"Dwelling Page", "Year built greater than current year warning message" + ext_rpt_msg, false, false);
		if (dwellingPage.dwellingCharacteristicsLink.checkIfElementIsPresent()
				&& dwellingPage.dwellingCharacteristicsLink.checkIfElementIsDisplayed()) {
			dwellingPage.dwellingCharacteristicsLink.scrollToElement();
			dwellingPage.dwellingCharacteristicsLink.click();
		}
		dwellingPage.yearBuilt.waitTillVisibilityOfElement(60);
		dwellingPage.yearBuilt.scrollToElement();
		dwellingPage.yearBuilt.setData(testData.get("L1D2-DwellingYearBuilt"));
		Assertions.verify(floodPage.floodLink.checkIfElementIsPresent(), false, "Dwelling Page",
				"Flood is not displayed", false, false);
		dwellingPage.scrollToBottomPage();
		dwellingPage.waitTime(2); // element not interactable exception
		dwellingPage.reSubmit.waitTillVisibilityOfElement(60);
		dwellingPage.reSubmit.scrollToElement();
		dwellingPage.reSubmit.click();
		if (dwellingPage.bringUptoCost.checkIfElementIsPresent()
				&& dwellingPage.bringUptoCost.checkIfElementIsDisplayed()) {
			dwellingPage.bringUptoCost.scrollToElement();
			dwellingPage.bringUptoCost.click();
		}
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);

		// verifying deductible values
		testData = data.get(data_Value1);
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), testData.get("NamedHurricaneDedApplicability"),
				"Create Quote Page", "Default Named Hurricane Value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"Default EarthQuake Value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.floodData.checkIfElementIsPresent(), false, "Create Quote Page",
				"Flood not displayed" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Deductible details verified successfully");

		// Entering Create quote page Details
		testData = data.get(data_Value4);
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Deductible details entered successfully");

		// verifying coverage values
		testData = data.get(data_Value1);
		Assertions.verify(createQuotePage.personalPropertyReplacementCostData.getData(),
				testData.get("ReplacementCost"), "Create Quote Page ",
				"Default Personal Property Replacement cost" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.ordinancelawData.getData(), testData.get("OrdinanceOrLaw"),
				"Create Quote Page", "Default ordinance or Law value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);
		testData = data.get(data_Value4);
		Assertions.verify(createQuotePage.enhancedReplacementCostDataHIHO.getData(), testData.get("EnhancedReplCost"),
				"Create Quote page", "Default Enhanced Replacement cost" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Coverage details verified successfully");

		// Entering coverage Details
		testData = data.get(data_Value3);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Coverage details entered successfully");
		testData = data.get(data_Value2);

		// Create Quote Page Grid is verified
		for (int i = 0; i < 2; i++) {
			Assertions.addInfo("<span class='group'> Create Quote Page</span>",
					"<span class='group'> Location 1 - Dwelling " + (i + 1) + "</span>");
			char ch = 'A';
			for (int j = 2; j <= 5; j++) {
				if (!testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).equals("")) {
					TextFieldControl namedHurricanegrid = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]//input"));
					Assertions.verify(namedHurricanegrid.getData(),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
							"Create Quote Page", "Cov " + ch + " value " + namedHurricanegrid.getData()
									+ " : Named Hurricane - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);
					TextFieldControl earthquakegrid = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
					Assertions.verify(earthquakegrid.getData(),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
							"Create Quote Page", "Cov " + ch + " value " + earthquakegrid.getData()
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
					BaseWebElementControl earthquakegrid = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]"));
					Assertions.verify(
							earthquakegrid.getData(), "Excluded", "Create Quote Page", "Cov " + ch + " value "
									+ earthquakegrid.getData() + " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);

					ch++;
				}
			}
		}
		Assertions.passTest("Create Quote Page", "Insured values verified successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (createQuotePage.pageName.getData().contains("Create Quote")) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}
		Assertions.passTest("Account Overview page", PAGE_NAVIGATED);
		String quote3 = accountOverviewPage.quoteNo3Holder.getData();
		testData = data.get(data_Value1);
		Assertions.verify(accountOverviewPage.quoteNo3Holder.checkIfElementIsPresent(), true, "Account Overview Page",
				"Quote Number 3 displayed " + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quoteNo3Holder.getData(), quote3, "Account Overview Page",
				"Quote Number 3 is : " + accountOverviewPage.quoteNo3Holder.getData(), false, false);
		Assertions.verify(accountOverviewPage.quote3Status.getData(), testData.get("Status"), "Account Overview Page",
				"Quote 3 Status" + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quote2StatusActive.getData(), testData.get("Status"),
				"Account Overview Page", "Quote 2 Status" + ext_rpt_msg, false, false);
		dwellingPage.dwellingLink1.scrollToElement();
		dwellingPage.dwellingLink1.click();
		dwellingPage.editBuilding.scrollToElement();
		dwellingPage.editBuilding.click();

		// commented due to CR19068
		if (accountOverviewPage.yesButton.checkIfElementIsPresent()) {
			accountOverviewPage.yesButton.scrollToElement();
			accountOverviewPage.yesButton.click();
		}
		if (accountOverviewPage.yesIWantToContinue.checkIfElementIsPresent()) {
			accountOverviewPage.yesIWantToContinue.scrollToElement();
			accountOverviewPage.yesIWantToContinue.click();
			accountOverviewPage.yesIWantToContinue.waitTillInVisibilityOfElement(60);
		}

		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		testData = data.get(data_Value2);
		dwellingPage.dwellingTypeArrow.waitTillVisibilityOfElement(60);
		dwellingPage.dwellingTypeArrow.click();
		dwellingPage.dwellingTypeOption.formatDynamicPath(testData.get("L1D1-DwellingType")).click();
		if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
			dwellingPage.expiredQuotePopUp.scrollToElement();
			dwellingPage.continueWithUpdateBtn.scrollToElement();
			dwellingPage.continueWithUpdateBtn.click();
			dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
		}
		if (dwellingPage.dwellingCharacteristicsLink.checkIfElementIsPresent()
				&& dwellingPage.dwellingCharacteristicsLink.checkIfElementIsDisplayed()) {
			dwellingPage.dwellingCharacteristicsLink.scrollToElement();
			dwellingPage.dwellingCharacteristicsLink.click();
		}
		dwellingPage.yearBuilt.waitTillVisibilityOfElement(60);
		dwellingPage.yearBuilt.scrollToElement();
		dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
		testData = data.get(data_Value3);
		dwellingPage.waitTime(3);
		dwellingPage.dwellingValuesLink.scrollToElement();
		dwellingPage.dwellingValuesLink.click();
		dwellingPage.waitTime(3);
		dwellingPage.covANamedHurricane.setData(testData.get("L1D1-DwellingCovA"));
		dwellingPage.covBNamedHurricane.setData(testData.get("L1D1-DwellingCovB"));
		Assertions.passTest("Dwelling Page", VALUES_UPDATED);
		dwellingPage.save.waitTillVisibilityOfElement(60);
		dwellingPage.save.click();
		dwellingPage.dwellingLink2.scrollToElement();
		dwellingPage.dwellingLink2.click();
		dwellingPage.deleteSymbol.waitTillVisibilityOfElement(60);
		dwellingPage.deleteSymbol.scrollToElement();
		dwellingPage.deleteSymbol.click();
		dwellingPage.deleteBldgPopup.waitTillVisibilityOfElement(30);
		dwellingPage.deleteBldgYes.click();
		dwellingPage.dwellingLink1.scrollToElement();
		dwellingPage.dwellingLink1.click();
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingPage.coveragesStep2.scrollToElement();
		dwellingPage.coveragesStep2.click();
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);

		// verifying deductible values
		testData = data.get(data_Value1);
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), testData.get("NamedHurricaneDedApplicability"),
				"Create Quote Page", "Default Named Hurricane Value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"Default EarthQuake Value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.floodData.checkIfElementIsPresent(), false, "Create Quote Page",
				"Flood not displayed" + ext_rpt_msg, false, false);
		Assertions.passTest("Create Quote Page", "Deductible details verified successfully");

		// Entering Create quote page Details
		testData = data.get(data_Value5);
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Deductible details entered successfully");

		// verifying coverage values
		testData = data.get(data_Value4);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.enhancedReplacementCostDataHIHO.getData(), testData.get("EnhancedReplCost"),
				"Create Quote page", "Default Enhanced Replacement cost" + ext_rpt_msg, false, false);

		// Entering coverage Details
		testData = data.get(data_Value5);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Coverage details entered successfully");

		// Create Quote Page Grid is verified
		for (int i = 0; i < 1; i++) {
			Assertions.addInfo("<span class='group'> Create Quote Page</span>",
					"<span class='group'> Location 1 - Dwelling " + (i + 1) + "</span>");
			char ch = 'A';
			for (int j = 2; j <= 5; j++) {
				testData = data.get(data_Value3);
				if (!testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).equals("")) {
					TextFieldControl namedHurricanegrid = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]//input"));
					Assertions.verify(namedHurricanegrid.getData(),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
							"Create Quote Page", "Cov " + ch + " value " + namedHurricanegrid.getData()
									+ " : Named Hurricane - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);
					TextFieldControl earthquakegrid = new TextFieldControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
					Assertions.verify(earthquakegrid.getData(),
							testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
							"Create Quote Page", "Cov " + ch + " value " + earthquakegrid.getData()
									+ " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);
					testData = data.get(data_Value5);

					ch++;
				} else {
					testData = data.get(data_Value3);
					BaseWebElementControl namedHurricanegrid = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]"));
					Assertions.verify(namedHurricanegrid.getData(), "Excluded", "Create Quote Page",
							"Cov " + ch + " value " + namedHurricanegrid.getData() + " : Named Hurricane - Dwelling "
									+ (i + 1) + ext_rpt_msg,
							false, false);
					BaseWebElementControl earthquakegrid = new BaseWebElementControl(
							By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
									+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]"));
					Assertions.verify(
							earthquakegrid.getData(), "Excluded", "Create Quote Page", "Cov " + ch + " value "
									+ earthquakegrid.getData() + " : Earthquake - Dwelling " + (i + 1) + ext_rpt_msg,
							false, false);

					ch++;
				}
			}
		}
		Assertions.passTest("Create Quote Page", "Insured values verified successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		testData = data.get(data_Value1);
		Assertions.passTest("Account Overview page", PAGE_NAVIGATED);
		Assertions.verify(accountOverviewPage.quote4Status.getData(), testData.get("Status"), "Account Overview Page",
				"Quote 4 Status" + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quote4Status.getData(), testData.get("Status"), "Account Overview Page",
				"Quote 1 Status" + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quote4Status.getData(), testData.get("Status"), "Account Overview Page",
				"Quote 2 Status" + ext_rpt_msg, false, false);
		Assertions.verify(accountOverviewPage.quote4Status.getData(), testData.get("Status"), "Account Overview Page",
				"Quote 3 Status" + ext_rpt_msg, false, false);
		String accountTotalPremium = accountOverviewPage.totalPremiumValue.getData();
		String accountQuoteNumber = accountOverviewPage.acntOverviewQuoteNumber.getData();
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, accountQuoteNumber);
		Assertions.passTest("Request Bind Page", PAGE_NAVIGATED);

		// Getting Quote Number
		quoteNumber = requestBindPage.quoteNumber.getData();
		Assertions.passTest("Request Bind Page", "Quote Number :  " + quoteNumber);

		// Verifying quote Number,name and premium same as account Overview page in
		// Request Bind Page
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
		// Calendar c = Calendar.getInstance();
		// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		// c.add(Calendar.YEAR, 1);
		// String expirationDate = sdf.format(c.getTime());
		Assertions.verify(requestBindPage.effectiveDate.getData(), testData.get("PolicyEffDate"), "Request Bind Page",
				"Effective Date" + ext_rpt_msg, false, false);
		Assertions.verify(requestBindPage.expirationDate.getData(), testData.get("PolicyExpirationDate"),
				"Request Bind Page", "Expiration Date" + ext_rpt_msg, false, false);
		Assertions.verify(requestBindPage.namedInsured.getData(), insuredName, "Request Bind Page",
				"Insured Name" + ext_rpt_msg, false, false);
		Assertions.passTest("Request Bind Page", VALUES_VERIFIED);

		// Entering Request Bind Page Details
		requestBindPage.enterPolicyDetailsNAHO(testData);
		requestBindPage.enterPaymentInformationNAHO(testData);
		requestBindPage.addInspectionContact(testData);
		requestBindPage.addContactInformation(testData);
		requestBindPage.addAdditionalInterest(testData);
		Assertions.verify(requestBindPage.aICountry.getData(), testData.get("InsuredCountry"), "Request Bind Page",
				"Additional Interest-Country: Default value United States" + ext_rpt_msg, false, false);
		Assertions.passTest("Request Bind Page", VALUES_ENTERED);
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		Assertions.passTest("Confirm Bind Request Page", PAGE_NAVIGATED);
		confirmBindRequestPage.requestBind.waitTillVisibilityOfElement(60);
		confirmBindRequestPage.requestBind.click();
		confirmBindRequestPage.requestBind.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
		Assertions.passTest("Policy Summary Page", PAGE_NAVIGATED);
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

		// Verification in Policy Summary Page
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsPresent(), true, "Policy Summary Page",
				"Policy Number Displayed" + ext_rpt_msg, false, false);
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policySummaryPage.getPolicynumber());
		Assertions.verify(policySummaryPage.policyStatus.getData(), testData.get("Status"), "Policy Summary Page",
				"Policy Active Status" + ext_rpt_msg, false, false);
		policySummaryPage.viewPolicySnapshot.click();
		Assertions.verify(policySummaryPage.policySnapshotScreen.checkIfElementIsDisplayed(), true,
				"Policy Summary Page", "Policy Snapshot Document displayed" + ext_rpt_msg, false, false);
		createQuotePage.goBack.click();
		createQuotePage.quoteLink.waitTillVisibilityOfElement(30);
		createQuotePage.quoteLink.scrollToElement();
		createQuotePage.quoteLink.click();
		Assertions.verify(createQuotePage.quoteDetails.checkIfElementIsDisplayed(), true, "Quote Details Page",
				"Quote Details Document displayed" + ext_rpt_msg, false, false);
		createQuotePage.closeButton.moveToElement();
		createQuotePage.closeButton.click();
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Welcome Page", PAGE_NAVIGATED);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC005", "Executed Successfully");

	}
}
