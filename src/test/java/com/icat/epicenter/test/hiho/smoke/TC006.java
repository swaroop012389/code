/** Program Description:
 *  Author			   : SM Netserv
 *  Date of Creation   : May 2023
 **/

package com.icat.epicenter.test.hiho.smoke;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.DateConversions;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EmailQuotePage;
import com.icat.epicenter.pom.FloodPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.test.hiho.AbstractHIHOTest;
import com.icat.epicenter.utils.TestConstants;

public class TC006 extends AbstractHIHOTest {

	public TC006() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/smoke/NBTCID06.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setupData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		FloodPage floodPage = new FloodPage();
		EmailQuotePage emailQuote = new EmailQuotePage();
		DwellingPage dwellingPage = new DwellingPage();
		Map<String, String> testData = data.get(TestConstants.DATA_COL_1);
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);

		DateConversions date = new DateConversions();
		String insuredName = testData.get("InsuredName") + date.getCurrentDate("MM/dd/YYYY_hh:mm:ss");

		homePage.goToHomepage.click();
		homePage.createNewAccount.scrollToElement();
		homePage.createNewAccount.click();
		homePage.namedInsured.setData(insuredName);
		Assertions.passTest("Home Page", "Insured Name is " + insuredName);
		homePage.producerNumber.setData(setupData.get("ProducerNumber"));
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
		}
		homePage.goButton.click();
		waitTime(5);
		if (homePage.overRideCheckBox.checkIfElementIsPresent() && homePage.overRideCheckBox.checkIfElementIsDisplayed()) {
			homePage.overRideCheckBox.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			homePage.overRideCheckBox.waitTillElementisEnabled(TIME_OUT_SIXTY_SECS);
			homePage.overRideCheckBox.scrollToElement();
			homePage.overRideCheckBox.select();
			homePage.goButton.click();
			homePage.goButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		}
		Assertions.passTest("New Account", "New Account created successfully");

		// Click on Home page Button
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", TestConstants.PAGE_NAVIGATED);

		// find the account by entering insured name and producer name
		homePage.findAccountNamedInsured.scrollToElement();
		homePage.findAccountNamedInsured.setData(insuredName);
		homePage.findBtn.scrollToElement();
		homePage.findBtn.click();
		Assertions.passTest("Account Overview Page", TestConstants.PAGE_NAVIGATED);

		// Delete account
		accountOverviewPage.deleteAccount.scrollToElement();
		accountOverviewPage.deleteAccount.click();
		accountOverviewPage.yesDeleteBuilding.click();
		Assertions.verify(accountOverviewPage.deleteAccountMessage.checkIfElementIsDisplayed(), true, "Home Page",
				"Delete Account Message" + TestConstants.EXT_RPT_MSG, false, false);

		// New account 2
		testData = data.get(TestConstants.DATA_COL_2);
		homePage.createNewAccountWithNamedInsured(testData, setupData);
		Assertions.passTest("New Account", "New Account created successfully");
		testData = data.get(TestConstants.DATA_COL_1);

		// Entering Dwelling 1 Details
		Assertions.passTest("Dwelling Page", TestConstants.PAGE_NAVIGATED);
		dwellingPage.addDwellingDetails(testData, 1, 1);
		dwellingPage.addRoofDetails(testData, 1, 1);
		dwellingPage.enterDwellingValues(testData, 1, 1);
		Assertions.passTest("Dwelling Page", TestConstants.VALUES_ENTERED);

		// Entering Flood Details
		floodPage.floodLink.click();
		Assertions.passTest("Flood Page", TestConstants.PAGE_NAVIGATED);
		floodPage.enterFloodDetails(testData);
		Assertions.passTest("Flood Page", "Flood Details entered successfully");
		accountOverviewPage.dwelling.scrollToElement();
		accountOverviewPage.dwelling.click();
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingPage.override.scrollToElement();
		dwellingPage.override.click();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();

		// Entering Quote page details
		Assertions.passTest("Create Quote Page", TestConstants.PAGE_NAVIGATED);
		createQuotePage.enterDeductibles(testData);
		Assertions.verify(createQuotePage.floodData.checkIfElementIsPresent(), false, "Create Quote Page",
				"Flood not displayed" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(createQuotePage.addFloodDetailsLink.checkIfElementIsDisplayed(), true, "Home Page",
				"Return to Flood Link" + TestConstants.EXT_RPT_MSG, false, false);
		createQuotePage.addFloodDetailsLink.click();
		Assertions.passTest("Create Quote Page", TestConstants.VALUES_UPDATED + " and " + TestConstants.VALUES_VERIFIED);
		floodPage.reviewButton.scrollToElement();
		floodPage.reviewButton.click();
		accountOverviewPage.dwelling.scrollToElement();
		accountOverviewPage.dwelling.click();
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();
		dwellingPage.override.scrollToElement();
		dwellingPage.override.click();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Entering Quote page details
		Assertions.passTest("Create Quote Page", TestConstants.PAGE_NAVIGATED);
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + TestConstants.EXT_RPT_MSG, false, false);
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
										+ TestConstants.EXT_RPT_MSG,
								false, false);
						Assertions
								.verify(covA_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovA").replace(",", ""),
										"Create Quote Page", "Coverage A Earthquake value: "
												+ covA_EQinputBox.getAttrributeValue("Value") + TestConstants.EXT_RPT_MSG,
										false, false);
					} else {
						Assertions.verify(covA_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage A Named Hurricane value: " + covA_NHlabel.getData() + TestConstants.EXT_RPT_MSG, false,
								false);
						Assertions.verify(covA_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage A Earthquake value: " + covA_EQlabel.getData() + TestConstants.EXT_RPT_MSG, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovB") != "") {
						Assertions.verify(covB_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovB").replace(",", ""), "Create Quote Page",
								"Coverage B Named Hurricane value: " + covB_NHinputBox.getAttrributeValue("Value")
										+ TestConstants.EXT_RPT_MSG,
								false, false);
						Assertions
								.verify(covB_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovB").replace(",", ""),
										"Create Quote Page", "Coverage B Earthquake value: "
												+ covB_EQinputBox.getAttrributeValue("Value") + TestConstants.EXT_RPT_MSG,
										false, false);
					} else {
						Assertions.verify(covB_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage B Named Hurricane value: " + covB_NHlabel.getData() + TestConstants.EXT_RPT_MSG, false,
								false);
						Assertions.verify(covB_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage B Earthquake value: " + covB_EQlabel.getData() + TestConstants.EXT_RPT_MSG, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovC") != "") {
						Assertions.verify(covC_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovC").replace(",", ""), "Create Quote Page",
								"Coverage C Named Hurricane value: " + covC_NHinputBox.getAttrributeValue("Value")
										+ TestConstants.EXT_RPT_MSG,
								false, false);
						Assertions
								.verify(covC_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovC").replace(",", ""),
										"Create Quote Page", "Coverage C Earthquake value: "
												+ covC_NHinputBox.getAttrributeValue("Value") + TestConstants.EXT_RPT_MSG,
										false, false);
					} else {
						Assertions.verify(covC_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage C Named Hurricane value: " + covC_NHlabel.getData() + TestConstants.EXT_RPT_MSG, false,
								false);
						Assertions.verify(covC_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage C Earthquake value: " + covC_EQlabel.getData() + TestConstants.EXT_RPT_MSG, false, false);
					}
					if (testData.get("L" + i + "D" + j + "-DwellingCovD") != "") {
						Assertions.verify(covD_NHinputBox.getAttrributeValue("Value"),
								testData.get("L" + i + "D" + j + "-DwellingCovD").replace(",", ""), "Create Quote Page",
								"Coverage D Named Hurricane value: " + covD_NHinputBox.getAttrributeValue("Value")
										+ TestConstants.EXT_RPT_MSG,
								false, false);
						Assertions
								.verify(covD_EQinputBox.getAttrributeValue("Value"),
										testData.get("L" + i + "D" + j + "-DwellingCovD").replace(",", ""),
										"Create Quote Page", "Coverage D Earthquake value: "
												+ covD_EQinputBox.getAttrributeValue("Value") + TestConstants.EXT_RPT_MSG,
										false, false);
					} else {
						Assertions.verify(covD_NHlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage D Named Hurricane value: " + covD_NHlabel.getData() + TestConstants.EXT_RPT_MSG, false,
								false);
						Assertions.verify(covD_EQlabel.getData(), "Excluded", "Create Quote Page",
								"Coverage D Earthquake value: " + covD_EQlabel.getData() + TestConstants.EXT_RPT_MSG, false, false);
					}
					Assertions.verify(covA_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage A Flood value: " + covA_Floodlabel.getData() + TestConstants.EXT_RPT_MSG, false, false);
					Assertions.verify(covB_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage B Flood value: " + covB_Floodlabel.getData() + TestConstants.EXT_RPT_MSG, false, false);
					Assertions.verify(covC_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage C Flood value: " + covC_Floodlabel.getData() + TestConstants.EXT_RPT_MSG, false, false);
					Assertions.verify(covD_Floodlabel.getData(), "Excluded", "Create Quote Page",
							"Coverage D Flood value: " + covD_Floodlabel.getData() + TestConstants.EXT_RPT_MSG, false, false);
				}
			}
		}
		Assertions.passTest("Create Quote Page", "Insured values verified successfully");

		// Click on Quote step 3
		createQuotePage.quoteStep3.click();
		createQuotePage.goBack.waitTillPresenceOfElement(60);
		createQuotePage.goBack.scrollToElement();
		createQuotePage.goBack.click();
		createQuotePage.goBack.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Navigate to Flood Page
		createQuotePage.addFloodDetailsLink.click();
		createQuotePage.addFloodDetailsLink.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		floodPage.priorFloodloss_no.scrollToElement();
		floodPage.priorFloodloss_no.click();
		testData = data.get(TestConstants.DATA_COL_2);

		// Entering Flood Details
		Assertions.passTest("Flood Page", TestConstants.PAGE_NAVIGATED);
		floodPage.enterFloodDetails(testData);
		floodPage.createQuote.scrollToElement();
		floodPage.createQuote.click();
		floodPage.createQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Entering Quote page details
		testData = data.get(TestConstants.DATA_COL_2);
		Assertions.passTest("Create Quote Page", TestConstants.PAGE_NAVIGATED);
		createQuotePage = new CreateQuotePage();
		createQuotePage.enterDeductibles(testData);
		Assertions.verify(createQuotePage.floodData.checkIfElementIsPresent(), true, "Create Quote Page",
				"Flood field is displayed" + TestConstants.EXT_RPT_MSG, false, false);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + TestConstants.EXT_RPT_MSG, false, false);

		// Setting Insured values in create quote page - 1
		char ch = 'A';
		createQuotePage.getAQuote.scrollToElement();
		for (int j = 2; j < 5; j++) {
			TextFieldControl ele = new TextFieldControl(By.xpath(
					"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-1')]]]]]]//following-sibling::table[1]//tr["
							+ j + "]//td[2]//input"));
			ele.scrollToElement();
			ele.clearData();
			createQuotePage.waitTime(5);
			ele.setData(testData.get("L1D1-DwellingCov" + ch));
			ele.tab();
			createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			ch++;
		}

		// Entering flood value for Coverage in Grid
		createQuotePage.floodCovA.scrollToElement();
		createQuotePage.floodCovA.setData("2000001");
		createQuotePage.floodCovA.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.floodCovC.waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
		createQuotePage.floodCovC.scrollToElement();
		createQuotePage.floodCovC.clearData();
		createQuotePage.waitTime(2);// To clear the data from text field
		createQuotePage.floodCovC.setData("500001");
		createQuotePage.floodCovC.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Verifying Flood Coverage B excluded in Grid
		Assertions.verify(createQuotePage.covBFloodGrid.getData(), "Excluded", "Create Quote Page",
				"Cov B  value  : Flood - Dwelling 1" + TestConstants.EXT_RPT_MSG, false, false);

		// Verifying Earthquake Coverage in Grid
		char ch1 = 'A';
		for (int j = 2; j <= 5; j++) {
			WebElement ele = WebDriverManager.getCurrentDriver().findElement(By.xpath(
					"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-1')]]]]]]//following-sibling::table[1]//tr["
							+ j + "]//td[3]//input"));
			Assertions.verify(ele.getAttribute("value").replaceAll(",", ""),
					testData.get("L1D1-DwellingCov" + ch1).replaceAll(",", ""), "Create Quote Page",
					"Cov " + ch1 + " value " + " : Earthquake - Dwelling 1" + TestConstants.EXT_RPT_MSG, false, false);
			ch1++;
		}
		Assertions.verify(createQuotePage.covDFlood.getAttrributeValue("value"), "5,000", "Create Quote Page",
				"Cov D value " + " : Earthquake - Dwelling 1" + TestConstants.EXT_RPT_MSG, false, false);
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		waitTime(5); // to make the script wait till the element is visible
		createQuotePage.goBack.waitTillPresenceOfElement(TIME_OUT_THIRTY_SECS);
		createQuotePage.goBack.scrollToElement();
		createQuotePage.goBack.click();
		createQuotePage.goBack.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		testData = data.get(TestConstants.DATA_COL_3);

		// Setting Insured values in create quote page - 2nd
		char ch2 = 'A';
		for (int j = 2; j < 5; j++) {
			TextFieldControl ele = new TextFieldControl(By.xpath(
					"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-1')]]]]]]//following-sibling::table[1]//tr["
							+ j + "]//td[2]//input"));
			ele.scrollToElement();
			ele.clearData();
			createQuotePage.waitTime(5);// To clear the data from text field
			ele.setData(testData.get("L1D1-DwellingCov" + ch2));
			ele.tab();
			createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
			ch2++;
		}

		// Entering flood value for Coverage in Grid
		createQuotePage.floodCovA.waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
		createQuotePage.floodCovA.scrollToElement();
		createQuotePage.floodCovA.clearData();
		createQuotePage.waitTime(2);// To clear the data from text field
		createQuotePage.floodCovA.setData("2000000");
		createQuotePage.floodCovA.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.floodCovC.scrollToElement();
		createQuotePage.floodCovC.clearData();
		createQuotePage.floodCovC.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.floodCovC.setData("500000");
		createQuotePage.floodCovC.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		Assertions.passTest("Create Quote Page", TestConstants.VALUES_UPDATED + " and " + TestConstants.VALUES_VERIFIED);

		// Click on Get a quote Button
		createQuotePage.getAQuote.waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.continueButton.scrollToElement();
		createQuotePage.continueButton.click();
		createQuotePage.continueButton.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Navigate to Account Overview Page
		Assertions.passTest("Account Overview Page", TestConstants.PAGE_NAVIGATED);
		// String accountTotalPremium =
		// accountOverviewPage.acntOverviewTotalPremium.getData();
		Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Total Premium" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsPresent(), true, "Account Overview Page",
				"Quote Number is displayed" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(accountOverviewPage.quoteDedDetails.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Quote Deductibles Details", false, false);

		// Verifying Quote Details Link,Email Quote and Print Rate Trace
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();
		Assertions.verify(accountOverviewPage.quoteDocument.checkIfElementIsDisplayed(), true, "Quote Document",
				"Quote Document displayed" + TestConstants.EXT_RPT_MSG, false, false);
		createQuotePage.goBack.waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
		createQuotePage.goBack.click();
		accountOverviewPage.emailQuoteLink.click();
		Assertions.verify(accountOverviewPage.pageName.getData().contains("Email Quote"), true, "Email Quote Page",
				"Email Quote page displayed" + TestConstants.EXT_RPT_MSG, false, false);
		emailQuote.cancel.waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
		emailQuote.cancel.scrollToElement();
		emailQuote.cancel.click();
		accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
		accountOverviewPage.viewOrPrintRateTrace.click();
		Assertions.verify(accountOverviewPage.viewPrintRateTracePage.checkIfElementIsDisplayed(), true,
				"Rate Trace for Quote", "Rate Trace for Quote Screen displayed" + TestConstants.EXT_RPT_MSG, false, false);
		createQuotePage.backToAccountOverview.waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
		createQuotePage.backToAccountOverview.click();
		Assertions.passTest("Account Overview Page", TestConstants.VALUES_VERIFIED);

		// Creating another Quote
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();
		accountOverviewPage.createAnotherQuote.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);

		// Entering Quote page details
		Assertions.passTest("Create Quote Page", TestConstants.PAGE_NAVIGATED);
		createQuotePage.enterDeductibles(testData);
		Assertions.verify(createQuotePage.floodData.checkIfElementIsPresent(), true, "Create Quote Page",
				"Flood field is displayed" + TestConstants.EXT_RPT_MSG, false, false);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Default Mold value" + TestConstants.EXT_RPT_MSG, false, false);
		testData = data.get(TestConstants.DATA_COL_4);
		char ch4 = 'A';
		for (int j = 2; j < 5; j++) {
			TextFieldControl ele = new TextFieldControl(By.xpath(
					"//table[tbody[tr[td[div[span[contains(text(),'Location 1 - Dwelling 1-1')]]]]]]//following-sibling::table[1]//tr["
							+ j + "]//td[2]//input"));
			ele.scrollToElement();
			ele.clearData();
			createQuotePage.waitTime(5);
			ele.setData(testData.get("L1D1-DwellingCov" + ch4));
			createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		}
		createQuotePage.floodCovA.scrollToElement();
		createQuotePage.floodCovA.clearData();
		createQuotePage.floodCovA.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.floodCovA.setData("2000000");
		createQuotePage.floodCovA.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.floodCovC.scrollToElement();
		createQuotePage.floodCovC.clearData();
		createQuotePage.floodCovC.tab();
		createQuotePage.floodCovC.waitTillVisibilityOfElement(TIME_OUT_THIRTY_SECS);
		createQuotePage.floodCovC.setData("250001");
		createQuotePage.floodCovC.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.scrollToBottomPage();
		createQuotePage.getAQuote.click();
		createQuotePage.goBack.scrollToElement();
		createQuotePage.goBack.click();

		// Entering flood value for Coverage to 0
		createQuotePage.floodCovA.scrollToElement();
		createQuotePage.floodCovA.clearData();
		createQuotePage.floodCovA.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.floodCovA.setData("0");
		createQuotePage.floodCovA.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.floodCovC.scrollToElement();
		createQuotePage.floodCovC.clearData();
		createQuotePage.floodCovC.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.floodCovC.setData("0");
		createQuotePage.floodCovC.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.scrollToBottomPage();
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Verfying Warning Message
		Assertions.verify(createQuotePage.covAFloodMin20000_WarningMessage.checkIfElementIsDisplayed(), true,
				"Create Quote Page", "Cov A Flood Coverage cannot be greater than 20000 Warning Message" + TestConstants.EXT_RPT_MSG,
				false, false);

		// Entering flood value for Coverage to 19999
		createQuotePage.floodCovA.scrollToElement();
		createQuotePage.floodCovA.clearData();
		createQuotePage.floodCovA.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.floodCovA.setData("19999");
		createQuotePage.floodCovA.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.scrollToBottomPage();
		createQuotePage.getAQuote.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Verfying Warning Message
		createQuotePage.covAFloodMin20000_WarningMessage.scrollToElement();
		Assertions.verify(createQuotePage.covAFloodMin20000_WarningMessage.checkIfElementIsDisplayed(), true,
				"Create Quote Page", "Cov A Flood Coverage cannot be greater than 20000 Warning Message" + TestConstants.EXT_RPT_MSG,
				false, false);

		// Entering flood value for Coverage to 20000
		createQuotePage.scrollToBottomPage();
		createQuotePage.floodCovA.waitTillElementisEnabled(TIME_OUT_SIXTY_SECS);
		createQuotePage.floodCovA.scrollToElement();
		createQuotePage.floodCovA.clearData();
		createQuotePage.floodCovA.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		createQuotePage.floodCovA.setData("20000");
		createQuotePage.floodCovA.tab();
		createQuotePage.loading.waitTillInVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		Assertions.passTest("Create Quote Page", TestConstants.VALUES_UPDATED + " and " + TestConstants.VALUES_VERIFIED);
		createQuotePage.scrollToBottomPage();
		createQuotePage.getAQuote.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.continueButton.scrollToElement();
		createQuotePage.continueButton.click();

		// Verifying quote number in Account Overview Page
		Assertions.passTest("Account Overview Page", TestConstants.PAGE_NAVIGATED);
		Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsPresent(), true, "Account Overview Page",
				"Quote Number is displayed" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.verify(accountOverviewPage.quote2StatusActive.getData(), testData.get("Status"),
				"Account Overview Page", "Quote 2 Status" + TestConstants.EXT_RPT_MSG, false, false);
		Assertions.passTest("Account Overview Page", TestConstants.VALUES_VERIFIED);

		String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
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

		Assertions.passTest("Smoke Testing TC006", "Executed Successfully");
	}
}