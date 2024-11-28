/** Program Description:
 *  Author			   : SM Netserv
 *  Date of Creation   : May 2023
 **/

package com.icat.epicenter.test.hiho.smoke;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.DateConversions;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EmailQuotePage;
import com.icat.epicenter.pom.FloodPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.test.hiho.AbstractHIHOTest;
import com.icat.epicenter.utils.TestConstants;

public class TC005 extends AbstractHIHOTest {

	public TC005() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/smoke/NBTCID05.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setupData) {
		// Initializing all Objects
		HomePage homePage = new HomePage();
		DwellingPage dwellingPage = new DwellingPage();
		BuildingUnderMinimumCostPage dwellingCost = new BuildingUnderMinimumCostPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		FloodPage floodPage = new FloodPage();
		EmailQuotePage emailQuote = new EmailQuotePage();
		Map<String, String> testData = data.get(TestConstants.DATA_COL_1); // first data column
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
		String prodNumber = setupData.get("ProducerNumber");
		homePage.producerNumber.setData(prodNumber);
		if (homePage.productArrow.checkIfElementIsPresent()) {
			homePage.productArrow.click();
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection"))
					.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
		}
		if (!testData.get("ProductSelection").equalsIgnoreCase("Commercial")) {
			if (homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
				homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			} else if (homePage.effectiveDate.formatDynamicPath(2).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(2).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(2).waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
				homePage.effectiveDate.formatDynamicPath(2).setData(testData.get("PolicyEffDate"));
			} else {
				homePage.effectiveDate.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
				homePage.effectiveDate.scrollToElement();
				homePage.effectiveDate.setData(testData.get("PolicyEffDate"));
			}
			homePage.goButton.click();
			// To wait for the Override checkbox is visible
			waitTime(5);
//			homePage.goButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

			if (homePage.overRideCheckBox.checkIfElementIsPresent() && homePage.overRideCheckBox.checkIfElementIsDisplayed()) {
				homePage.overRideCheckBox.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
				homePage.overRideCheckBox.waitTillElementisEnabled(TIME_OUT_SIXTY_SECS);
				homePage.overRideCheckBox.scrollToElement();
				homePage.overRideCheckBox.select();
				homePage.goButton.click();
				homePage.goButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			}
			BasePageControl basePage = new DwellingPage();
			Assertions.passTest("Home Page", TestConstants.VALUES_ENTERED);
			dwellingPage = (DwellingPage) basePage;

			// Entering Dwelling Details
			Assertions.passTest("Dwelling Page", TestConstants.PAGE_NAVIGATED);
			dwellingPage.addDwellingDetails(testData, locationCount, 1);
			dwellingPage.addRoofDetails(testData, locationCount, 1);
			dwellingPage.enterDwellingValues(testData, locationCount, 1);
			Assertions.passTest("Dwelling Page", TestConstants.VALUES_ENTERED);
			dwellingPage.save.waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
			dwellingPage.save.scrollToElement();
			dwellingPage.save.click();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", TestConstants.PAGE_NAVIGATED);
			dwellingPage.exclamatorySignDwellin1.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

			// find the account by entering insured name and producer name
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findAccountProducerNumber.setData(prodNumber);
			homePage.findBtn.click();
			Assertions.passTest("Account Overview Page", TestConstants.PAGE_NAVIGATED);
			dwellingPage.dwellingLink.click();
			dwellingPage.editBuilding.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			Assertions.passTest("Dwelling Page", TestConstants.PAGE_NAVIGATED);
			dwellingPage.editBuilding.click();
			dwellingPage.editBuilding.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			dwellingCost.bringUpToCost.click();
			dwellingCost.bringUpToCost.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			testData = data.get(TestConstants.DATA_COL_4); // 4th column
			Assertions.verify(dwellingPage.covA.getData(), testData.get("L1D1-DwellingCovA"), "Dwelling Page",
					"Coverage A value is updated with bring up to cost value " + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(dwellingPage.dollarSymbol.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dollar symbol" + TestConstants.EXT_RPT_MSG, false, false);
			testData = data.get(TestConstants.DATA_COL_1); // 1st column
			floodPage.floodLink.click();
			Assertions.passTest("Flood Page", TestConstants.PAGE_NAVIGATED);
			floodPage.enterFloodDetails(testData);
			Assertions.passTest("Flood Page", "Flood Details entered successfully");
			floodPage.editFlood.click();
			Assertions.verify(floodPage.floodAddress1.getData(), testData.get("L1D1-DwellingAddress"), "Flood Page",
					"Address 1 same as Dwelling 1" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(floodPage.floodCity.getData(), testData.get("L1D1-DwellingCity"), "Flood Page",
					"City same as Dwelling 1" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(floodPage.floodZip.getData(), testData.get("L1D1-DwellingZIP"), "Flood Page",
					"Zip same as Dwelling 1" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(floodPage.latitude.checkIfElementIsDisplayed(), true, "Flood Page",
					"Latitude" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(floodPage.longitude.checkIfElementIsDisplayed(), true, "Flood Page",
					"Longitude" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(floodPage.distToCoast.checkIfElementIsDisplayed(), true, "Flood Page",
					"Distance to Coast" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(floodPage.overrideDistanceCoast.getData(), testData.get("L1D1-DisttoCoastOverride"),
					"Flood Page", "Override Distance to Coast" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(floodPage.elevation.checkIfElementIsDisplayed(), true, "Flood Page",
					"Elevation" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(floodPage.overrideElevation.getData(), testData.get("L1D1-ElevationOverride"),
					"Flood Page", "Elevation Override" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(floodPage.floodZone.checkIfElementIsDisplayed(), true, "Flood Page",
					"Elevation" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.passTest("Flood Page", "Flood Details verified successfully");
			floodPage.reviewButton.scrollToElement();
			floodPage.reviewButton.click();
			floodPage.reviewButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			floodPage.createQuote.scrollToElement();
			floodPage.createQuote.click();
			floodPage.createQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			Assertions.passTest("Create Quote Page", TestConstants.PAGE_NAVIGATED);

			// verifying deductible values
			Assertions.verify(createQuotePage.namedHurricaneData.getData(),
					testData.get("NamedHurricaneDedApplicability"), "Create Quote Page",
					"Default Named Hurricane Value" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"),
					"Create Quote Page", "Default EarthQuake Value" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(createQuotePage.floodData.checkIfElementIsPresent(), true, "Create Quote Page",
					"Availability of Flood option" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.passTest("Create Quote Page", "Deductible details verified successfully");

			// Entering Create quote page Details
			testData = data.get(TestConstants.DATA_COL_2);  // 2nd column
			createQuotePage.enterDeductibles(testData);

			// verifying coverage values
			testData = data.get(TestConstants.DATA_COL_1);  // 1st column
			Assertions.verify(createQuotePage.personalPropertyReplacementCostData.getData(),
					testData.get("ReplacementCost"), "Create Quote Page ",
					"Default Personal Property Replacement cost" + TestConstants.EXT_RPT_MSG, false, false);
			testData = data.get(TestConstants.DATA_COL_4);  // 4th column
			Assertions.verify(createQuotePage.enhancedReplacementCostData.getData(), testData.get("EnhancedReplCost"),
					"Create Quote page", "Default Enhanced Replacement cost" + TestConstants.EXT_RPT_MSG, false, false);
			testData = data.get(TestConstants.DATA_COL_1);  // 1st column
			Assertions.verify(createQuotePage.ordinanceLaw.getData(), testData.get("OrdinanceOrLaw"),
					"Create Quote Page", "Default ordinance or Law value is 10% included" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
					"Default Mold value" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.passTest("Create Quote Page", "Coverage details verified successfully");

			// Entering coverage Details
			testData = data.get(TestConstants.DATA_COL_2);  // 2nd column
			createQuotePage.addOptionalCoverageDetails(testData);
			createQuotePage.floodGrid1.setData(testData.get("L1D1-DwellingCovA"));
			createQuotePage.floodGrid1.tab();
			createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			createQuotePage.floodGrid3.scrollToElement();
			createQuotePage.floodGrid3.setData(testData.get("L1D1-DwellingCovC"));
			createQuotePage.floodGrid3.tab();
			createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

			// Create Quote Page Grid is verified
			for (int i = 0; i < 1; i++) {
				Assertions.addInfo("<span class='group'> Create Quote Page</span>",
						"<span class='group'> Location 1 - Dwelling " + (i + 1) + "</span>");
				char ch = 'A';
				for (int j = 2; j <= 5; j++) {
					if (!testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).equals("")) {
						TextFieldControl namedHurricanegrid = new TextFieldControl(By.xpath(
								"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
										+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]//input"));
						Assertions.verify(namedHurricanegrid.getData(),
								testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
								"Create Quote Page", "Cov " + ch + " value " + namedHurricanegrid.getData()
										+ " : Named Hurricane - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
								false, false);
						TextFieldControl earthquakegrid = new TextFieldControl(By.xpath(
								"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
										+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
						Assertions.verify(earthquakegrid.getData(),
								testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
								"Create Quote Page", "Cov " + ch + " value " + earthquakegrid.getData()
										+ " : Earthquake - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
								false, false);
						if (ch == 'A' || ch == 'C') {
							TextFieldControl floodgrid = new TextFieldControl(By.xpath(
									"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
											+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//input"));
							Assertions.verify(floodgrid.getData(),
									testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
									"Create Quote Page", "Cov " + ch + " value " + floodgrid.getData()
											+ " : Flood - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
									false, false);
						}
						if (ch == 'D') {
							TextFieldControl floodgrid = new TextFieldControl(By.xpath(
									"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
											+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//input"));
							Assertions.verify(floodgrid.getData(), "5,000", "Create Quote Page",
									"Cov D value 5000 " + " : Flood - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG, false, false);
						}
						if (ch == 'B') {
							BaseWebElementControl floodGridExc = new BaseWebElementControl(By.xpath(
									"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
											+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
							Assertions.verify(
									floodGridExc.getData(), "Excluded", "Create Quote Page", "Cov " + ch
											+ floodGridExc.getData() + " : Flood - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
									false, false);
						}
						ch++;
					}
				}
			}
			Assertions.passTest("Create Quote Page", "Insured values verified successfully");
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				waitTime(5); // to make the script wait till the element is enabled
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			testData = data.get(TestConstants.DATA_COL_1);  // 1st column
			Assertions.passTest("Account Overview Page", TestConstants.PAGE_NAVIGATED);
			String quote1 = accountOverviewPage.quoteNumber.getData();
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsPresent(), true,
					"Account Overview Page", "Quote Number 1 displayed" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(accountOverviewPage.quoteNumber.getData(), quote1, "Account Overview Page",
					"Quote Number 1 is : " + accountOverviewPage.quoteNumber.getData(), false, false);
			Assertions.verify(accountOverviewPage.quote1StatusActive.getData(), testData.get("Status"),
					"Account Overview Page", "Quote 1 Status" + TestConstants.EXT_RPT_MSG, false, false);
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(createQuotePage.quoteDetails.checkIfElementIsDisplayed(), true, "Quote Document",
					"Quote Document displayed" + TestConstants.EXT_RPT_MSG, false, false);
			createQuotePage.goBack.click();
			accountOverviewPage.emailQuoteLink.click();
			Assertions.verify(accountOverviewPage.pageName.getData().contains("Email Quote"), true, "Email Quote Page",
					"Email Quote page displayed" + TestConstants.EXT_RPT_MSG, false, false);
			emailQuote.cancel.scrollToElement();
			emailQuote.cancel.click();
			waitTime(3);
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.verify(accountOverviewPage.overridePremiumPage.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium Screen displayed" + TestConstants.EXT_RPT_MSG, false, false);
			emailQuote.cancel.scrollToElement();
			emailQuote.cancel.click();
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();
			Assertions.verify(accountOverviewPage.rateTraceForQuote.checkIfElementIsDisplayed(), true,
					"Rate Trace for Quote Page", "Rate Trace for Quote Screen displayed" + TestConstants.EXT_RPT_MSG, false, false);
			createQuotePage.backToAccountOverview.click();
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Edit Deductibles Page", "Edit deductibles navigates to create quote page verified");

			// verifying deductible values
			testData = data.get(TestConstants.DATA_COL_2);  // 2nd column
			Assertions.verify(createQuotePage.namedHurricaneData.getData(),
					testData.get("NamedHurricaneDedApplicability"), "Create Quote Page",
					"Default Named Hurricane Value" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"),
					"Create Quote Page", "Default EarthQuake Value" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(createQuotePage.floodData.getData(), testData.get("FloodDeductible"), "Create Quote Page",
					"Default Flood Value" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.passTest("Create Quote Page", "Deductible details verified successfully");
			testData = data.get(TestConstants.DATA_COL_3);  // 3rd column
			createQuotePage.enterDeductibles(testData);
			Assertions.passTest("Create Quote Page", "Deductible details entered successfully");
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				waitTime(5); // to make the script wait till the element is enabled
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			testData = data.get(TestConstants.DATA_COL_1);  // 1st column
			String quote2 = accountOverviewPage.quoteNumber.getData();
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsPresent(), true,
					"Account Overview Page", "Quote Number 2 displayed " + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(accountOverviewPage.quoteNumber.getData(), quote2, "Account Overview Page",
					"Quote Number 2 is : " + accountOverviewPage.quoteNumber.getData(), false, false);
			Assertions.verify(accountOverviewPage.quote2StatusActive.getData(), testData.get("Status"),
					"Account Overview Page", "Quote 2 Status" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(accountOverviewPage.quote1StatusActive.getData(), testData.get("Status"),
					"Account Overview Page", "Quote 1 Status" + TestConstants.EXT_RPT_MSG, false, false);
			dwellingPage.dwellingLink1.scrollToElement();
			dwellingPage.dwellingLink1.click();
			dwellingPage.editBuilding.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
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
				accountOverviewPage.yesIWantToContinue.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			}

			dwellingPage.addSymbol.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			dwellingPage.addSymbol.scrollToElement();
			dwellingPage.addSymbol.click();
			dwellingPage.addNewDwelling.waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
			dwellingPage.addNewDwelling.scrollToElement();
			dwellingPage.addNewDwelling.click();

			// Entering Dwelling Details
			Assertions.passTest("Dwelling Page", TestConstants.PAGE_NAVIGATED);
			dwellingPage.addDwellingDetails(testData, locationCount, 2);
			dwellingPage.addRoofDetails(testData, locationCount, 2);
			dwellingPage.enterDwellingValues(testData, locationCount, 2);
			Assertions.passTest("Dwelling Page", TestConstants.VALUES_UPDATED);
			Assertions.verify(dwellingPage.covCNamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
					"Coverage C not available for Course of Construction dwellings", false, false);
			Assertions.verify(dwellingPage.covDNamedHurricane.checkIfElementIsPresent(), false, "Dwelling Page",
					"Coverage D not available for Course of Construction dwellings", false, false);
			Assertions.passTest("Dwelling Page", TestConstants.VALUES_VERIFIED);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			testData = data.get(TestConstants.DATA_COL_2);  // 2nd column
			Assertions.verify(createQuotePage.globalErr.getData(), "Year built cannot be newer than current year.",
					"Dwelling Page", "Year built greater than current year warning message" + TestConstants.EXT_RPT_MSG, false,
					false);
			if (dwellingPage.dwellingCharacteristicsLink.checkIfElementIsPresent()
					&& dwellingPage.dwellingCharacteristicsLink.checkIfElementIsDisplayed()) {
				dwellingPage.dwellingCharacteristicsLink.scrollToElement();
				dwellingPage.dwellingCharacteristicsLink.click();
			}
			dwellingPage.yearBuilt.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.setData(testData.get("L1D2-DwellingYearBuilt"));
			Assertions.verify(floodPage.floodLink.checkIfElementIsPresent(), false, "Dwelling Page",
					"Flood is not displayed", false, false);
			dwellingPage.scrollToBottomPage();
			dwellingPage.waitTime(2); // element not interactable exception
			dwellingPage.reSubmit.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			dwellingPage.reSubmit.scrollToElement();
			dwellingPage.reSubmit.click();
			if (dwellingPage.bringUptoCost.checkIfElementIsPresent()
					&& dwellingPage.bringUptoCost.checkIfElementIsDisplayed()) {
				dwellingPage.bringUptoCost.scrollToElement();
				dwellingPage.bringUptoCost.click();
			}
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Create Quote Page", TestConstants.PAGE_NAVIGATED);

			// verifying deductible values
			testData = data.get(TestConstants.DATA_COL_1);  // 1st column
			Assertions.verify(createQuotePage.namedHurricaneData.getData(),
					testData.get("NamedHurricaneDedApplicability"), "Create Quote Page",
					"Default Named Hurricane Value" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"),
					"Create Quote Page", "Default EarthQuake Value" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(createQuotePage.floodData.checkIfElementIsPresent(), false, "Create Quote Page",
					"Flood not displayed" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.passTest("Create Quote Page", "Deductible details verified successfully");

			// Entering Create quote page Details
			testData = data.get(TestConstants.DATA_COL_4);  // 4th column
			createQuotePage.enterDeductibles(testData);
			Assertions.passTest("Create Quote Page", "Deductible details entered successfully");

			// verifying coverage values
			testData = data.get(TestConstants.DATA_COL_1);  // 1st column
			Assertions.verify(createQuotePage.personalPropertyReplacementCostData.getData(),
					testData.get("ReplacementCost"), "Create Quote Page ",
					"Default Personal Property Replacement cost" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(createQuotePage.ordinanceLaw.getData(), testData.get("OrdinanceOrLaw"),
					"Create Quote Page", "Default ordinance or Law value" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
					"Default Mold value" + TestConstants.EXT_RPT_MSG, false, false);
			testData = data.get(TestConstants.DATA_COL_4);  // 4th column
			Assertions.verify(createQuotePage.enhancedReplacementCostData.getData(), testData.get("EnhancedReplCost"),
					"Create Quote page", "Default Enhanced Replacement cost" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.passTest("Create Quote Page", "Coverage details verified successfully");

			// Entering coverage Details
			testData = data.get(TestConstants.DATA_COL_3);  // 3rd column
			createQuotePage.addOptionalCoverageDetails(testData);
			Assertions.passTest("Create Quote Page", "Coverage details entered successfully");
			testData = data.get(TestConstants.DATA_COL_2);  // 2nd column

			// Create Quote Page Grid is verified
			for (int i = 0; i < 2; i++) {
				Assertions.addInfo("<span class='group'> Create Quote Page</span>",
						"<span class='group'> Location 1 - Dwelling " + (i + 1) + "</span>");
				char ch = 'A';
				for (int j = 2; j <= 5; j++) {
					if (!testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).equals("")) {
						TextFieldControl namedHurricanegrid = new TextFieldControl(By.xpath(
								"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
										+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]//input"));
						Assertions.verify(namedHurricanegrid.getData(),
								testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
								"Create Quote Page", "Cov " + ch + " value " + namedHurricanegrid.getData()
										+ " : Named Hurricane - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
								false, false);
						TextFieldControl earthquakegrid = new TextFieldControl(By.xpath(
								"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
										+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
						Assertions.verify(earthquakegrid.getData(),
								testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
								"Create Quote Page", "Cov " + ch + " value " + earthquakegrid.getData()
										+ " : Earthquake - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
								false, false);
						BaseWebElementControl floodgrid = new BaseWebElementControl(By.xpath(
								"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
										+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
						Assertions.verify(
								floodgrid.getData(), "Excluded", "Create Quote Page", "Cov " + ch + " value "
										+ floodgrid.getData() + " : Flood - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
								false, false);
						ch++;
					} else {
						BaseWebElementControl namedHurricanegrid = new BaseWebElementControl(
								By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-"
										+ (i + 1) + "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]"));
						Assertions
								.verify(namedHurricanegrid.getData(), "Excluded", "Create Quote Page",
										"Cov " + ch + " value " + namedHurricanegrid.getData()
												+ " : Named Hurricane - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
										false, false);
						BaseWebElementControl earthquakegrid = new BaseWebElementControl(
								By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-"
										+ (i + 1) + "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]"));
						Assertions.verify(earthquakegrid.getData(), "Excluded", "Create Quote Page",
								"Cov " + ch + " value " + earthquakegrid.getData() + " : Earthquake - Dwelling "
										+ (i + 1) + TestConstants.EXT_RPT_MSG,
								false, false);
						BaseWebElementControl floodgrid = new BaseWebElementControl(By.xpath(
								"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
										+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
						Assertions.verify(
								floodgrid.getData(), "Excluded", "Create Quote Page", "Cov " + ch + " value "
										+ floodgrid.getData() + " : Flood - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
								false, false);
						ch++;
					}
				}
			}
			Assertions.passTest("Create Quote Page", "Insured values verified successfully");
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				waitTime(5); // to make the script wait till the element is enabled
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			Assertions.passTest("Account Overview page", TestConstants.PAGE_NAVIGATED);
			String quote3 = accountOverviewPage.quoteNumber.getData();
			testData = data.get(TestConstants.DATA_COL_1);  // 1st column
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsPresent(), true,
					"Account Overview Page", "Quote Number 3 displayed " + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(accountOverviewPage.quoteNumber.getData(), quote3, "Account Overview Page",
					"Quote Number 3 is : " + accountOverviewPage.quoteNumber.getData(), false, false);
			Assertions.verify(accountOverviewPage.quote3Status.getData(), testData.get("Status"),
					"Account Overview Page", "Quote 3 Status" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(accountOverviewPage.quote2StatusActive.getData(), testData.get("Status"),
					"Account Overview Page", "Quote 2 Status" + TestConstants.EXT_RPT_MSG, false, false);
			dwellingPage.dwellingLink1.scrollToElement();
			dwellingPage.dwellingLink1.click();
			dwellingPage.editBuilding.scrollToElement();
			dwellingPage.editBuilding.click();

			// commented due to CR19068
			if (accountOverviewPage.yesButton.checkIfElementIsPresent()) {
				accountOverviewPage.yesButton.scrollToElement();
				accountOverviewPage.yesButton.click();
			}
			if (accountOverviewPage.quoteExpiredPopupMsg.checkIfElementIsPresent()) {
				accountOverviewPage.quoteExpiredPopupMsg.scrollToElement();
				accountOverviewPage.quoteExpiredPopupMsg.click();
				accountOverviewPage.quoteExpiredPopupMsg.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			}

			Assertions.passTest("Dwelling Page", TestConstants.PAGE_NAVIGATED);
			testData = data.get(TestConstants.DATA_COL_2);  // 2nd column
			dwellingPage.dwellingTypeArrow.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			dwellingPage.dwellingTypeArrow.click();
			dwellingPage.dwellingTypeOption.formatDynamicPath(testData.get("L1D1-DwellingType")).click();
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			}
			if (dwellingPage.dwellingCharacteristicsLink.checkIfElementIsPresent()
					&& dwellingPage.dwellingCharacteristicsLink.checkIfElementIsDisplayed()) {
				dwellingPage.dwellingCharacteristicsLink.scrollToElement();
				dwellingPage.dwellingCharacteristicsLink.click();
			}
			dwellingPage.yearBuilt.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			testData = data.get(TestConstants.DATA_COL_3);  // 3rd column
			dwellingPage.waitTime(3);
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();
			dwellingPage.waitTime(3);
			dwellingPage.covANamedHurricane.setData(testData.get("L1D1-DwellingCovA"));
			dwellingPage.covBNamedHurricane.setData(testData.get("L1D1-DwellingCovB"));
			Assertions.passTest("Dwelling Page", TestConstants.VALUES_UPDATED);
			dwellingPage.save.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			dwellingPage.save.click();
			dwellingPage.dwellingLink2.scrollToElement();
			dwellingPage.dwellingLink2.click();
			dwellingPage.deleteSymbol.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			dwellingPage.deleteSymbol.scrollToElement();
			dwellingPage.deleteSymbol.click();
			dwellingPage.deleteBldgPopup.waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
			dwellingPage.deleteBldgYes.click();
			dwellingPage.dwellingLink1.scrollToElement();
			dwellingPage.dwellingLink1.click();
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.coveragesStep2.scrollToElement();
			dwellingPage.coveragesStep2.click();
			Assertions.passTest("Create Quote Page", TestConstants.PAGE_NAVIGATED);

			// verifying deductible values
			testData = data.get(TestConstants.DATA_COL_1);  // 1st column
			Assertions.verify(createQuotePage.namedHurricaneData.getData(),
					testData.get("NamedHurricaneDedApplicability"), "Create Quote Page",
					"Default Named Hurricane Value" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"),
					"Create Quote Page", "Default EarthQuake Value" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(createQuotePage.floodData.checkIfElementIsPresent(), false, "Create Quote Page",
					"Flood not displayed" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.passTest("Create Quote Page", "Deductible details verified successfully");

			// Entering Create quote page Details
			testData = data.get(TestConstants.DATA_COL_5);
			createQuotePage.enterDeductibles(testData);
			Assertions.passTest("Create Quote Page", "Deductible details entered successfully");

			// verifying coverage values
			testData = data.get(TestConstants.DATA_COL_4);  // 4th column
			Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
					"Default Mold value" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(createQuotePage.enhancedReplacementCostData.getData(), testData.get("EnhancedReplCost"),
					"Create Quote page", "Default Enhanced Replacement cost" + TestConstants.EXT_RPT_MSG, false, false);

			// Entering coverage Details
			testData = data.get(TestConstants.DATA_COL_5);
			createQuotePage.addOptionalCoverageDetails(testData);
			Assertions.passTest("Create Quote Page", "Coverage details entered successfully");

			// Create Quote Page Grid is verified
			for (int i = 0; i < 1; i++) {
				Assertions.addInfo("<span class='group'> Create Quote Page</span>",
						"<span class='group'> Location 1 - Dwelling " + (i + 1) + "</span>");
				char ch = 'A';
				for (int j = 2; j <= 5; j++) {
					testData = data.get(TestConstants.DATA_COL_3);  // 3rd column
					if (!testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch).equals("")) {
						TextFieldControl namedHurricanegrid = new TextFieldControl(By.xpath(
								"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
										+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]//input"));
						Assertions.verify(namedHurricanegrid.getData(),
								testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
								"Create Quote Page", "Cov " + ch + " value " + namedHurricanegrid.getData()
										+ " : Named Hurricane - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
								false, false);
						TextFieldControl earthquakegrid = new TextFieldControl(By.xpath(
								"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
										+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]//input"));
						Assertions.verify(earthquakegrid.getData(),
								testData.get("L" + locationCount + "D" + (i + 1) + "-DwellingCov" + ch),
								"Create Quote Page", "Cov " + ch + " value " + earthquakegrid.getData()
										+ " : Earthquake - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
								false, false);
						testData = data.get(TestConstants.DATA_COL_5);
						BaseWebElementControl floodgrid = new BaseWebElementControl(By.xpath(
								"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
										+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
						Assertions.verify(
								floodgrid.getData(), "Excluded", "Create Quote Page", "Cov " + ch + " value "
										+ floodgrid.getData() + " : Flood - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
								false, false);
						ch++;
					} else {
						testData = data.get(TestConstants.DATA_COL_3);  // 3rd column
						BaseWebElementControl namedHurricanegrid = new BaseWebElementControl(
								By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-"
										+ (i + 1) + "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[2]"));
						Assertions
								.verify(namedHurricanegrid.getData(), "Excluded", "Create Quote Page",
										"Cov " + ch + " value " + namedHurricanegrid.getData()
												+ " : Named Hurricane - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
										false, false);
						BaseWebElementControl earthquakegrid = new BaseWebElementControl(
								By.xpath("//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-"
										+ (i + 1) + "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[3]"));
						Assertions.verify(earthquakegrid.getData(), "Excluded", "Create Quote Page",
								"Cov " + ch + " value " + earthquakegrid.getData() + " : Earthquake - Dwelling "
										+ (i + 1) + TestConstants.EXT_RPT_MSG,
								false, false);
						BaseWebElementControl floodgrid = new BaseWebElementControl(By.xpath(
								"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-" + (i + 1)
										+ "')]]]]]]//following-sibling::table[1]//tr[" + j + "]//td[4]//div"));
						Assertions.verify(
								floodgrid.getData(), "Excluded", "Create Quote Page", "Cov " + ch + " value "
										+ floodgrid.getData() + " : Flood - Dwelling " + (i + 1) + TestConstants.EXT_RPT_MSG,
								false, false);
						ch++;
					}
				}
			}
			Assertions.passTest("Create Quote Page", "Insured values verified successfully");
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Clicking on Continue button
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				waitTime(5); // to make the script wait till the element is enabled
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			testData = data.get(TestConstants.DATA_COL_1);  // 1st column
			Assertions.passTest("Account Overview page", TestConstants.PAGE_NAVIGATED);
			Assertions.verify(accountOverviewPage.quote4Status.getData(), testData.get("Status"),
					"Account Overview Page", "Quote 4 Status" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(accountOverviewPage.quote4Status.getData(), testData.get("Status"),
					"Account Overview Page", "Quote 1 Status" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(accountOverviewPage.quote4Status.getData(), testData.get("Status"),
					"Account Overview Page", "Quote 2 Status" + TestConstants.EXT_RPT_MSG, false, false);
			Assertions.verify(accountOverviewPage.quote4Status.getData(), testData.get("Status"),
					"Account Overview Page", "Quote 3 Status" + TestConstants.EXT_RPT_MSG, false, false);
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
			Assertions.verify(accountOverviewPage.pageName.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Account Name present", true, true);
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Total Premium and Fee Amount present", true, true);
			Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Producer Number present", true, true);
			if (locationCount >= 2) {
				Assertions.verify(accountOverviewPage.quoteSomeDwellingsButton.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Create Another Quote present", true, true);
			} else {
				Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Create Another Quote present", true, true);
			}
			Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Edit Deductibles and limits button present", true, true);
			Assertions.verify(accountOverviewPage.quoteStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Quote Status present", true, true);
			Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View/Print Full Quote Link present", true, true);
			Assertions.verify(accountOverviewPage.emailQuoteLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Email Quote Link present", true, true);
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

			Assertions.passTest("Smoke Testing TC005", "Executed Successfully");
		}
	}
}