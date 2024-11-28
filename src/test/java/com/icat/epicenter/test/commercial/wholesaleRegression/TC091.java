/** Program Description: Create a quote Edit the Location and Building and Check for the Presence of Quote some Buildings Button
 *  Author			   : John
 *  Date of Creation   : 08/04/2020
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

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

public class TC091 extends AbstractCommercialTest {

	public TC091() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID091.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();

		// Initializing the variables
		String quoteNumber;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		int data_Value2 = 1;
		Map<String, String> testData1 = data.get(data_Value2);
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

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

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
			createQuotePage.quoteName.scrollToElement();
			createQuotePage.quoteName.setData(testData.get("QuoteName"));
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			Assertions.verify(
					accountOverviewPage.quoteLink.formatDynamicPath("1").getData().contains(testData.get("QuoteName")),
					true, "Account Overview Page", "Quote name is " + testData.get("QuoteName"), false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 1:  " + quoteNumber);

			// Click on Edit Location
			Assertions.addInfo("Account Overview Page", "Edit the Location and Buildung Details");
			accountOverviewPage.editLocation.waitTillVisibilityOfElement(60);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit location link");

			// Drag and Drop Building 1 from loc 2 and drop it in loc 3
			Actions builder1 = new Actions(WebDriverManager.getCurrentDriver())
					.moveToElement(WebDriverManager.getCurrentDriver().findElement(By.xpath(
							"//span[contains(text(),'Location 2')]//following::span[contains(text(),'Building 2-1')]")))
					.pause(Duration.ofSeconds(3));
			builder1.clickAndHold(WebDriverManager.getCurrentDriver().findElement(By
					.xpath("//span[contains(text(),'Location 2')]//following::span[contains(text(),'Building 2-1')]")));
			builder1.pause(Duration.ofSeconds(3)).moveByOffset(1, 0);
			builder1.moveToElement(
					WebDriverManager.getCurrentDriver().findElement(By.xpath("(//ul[@class='ui-sortable'])[3]")));
			builder1.moveByOffset(1, 0).pause(Duration.ofSeconds(3)).release();
			builder1.perform();
			Assertions.passTest("Building Page", "Building 2-1 is moved from Location 2 to Location 3");
			accountOverviewPage.saveOrder.waitTillVisibilityOfElement(60);
			accountOverviewPage.saveOrder.scrollToElement();
			accountOverviewPage.saveOrder.click();
			accountOverviewPage.saveOrder.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Building Page", "Clicked on Save Order");
			accountOverviewPage.refreshPage();

			// Drag and Drop Building 2 from loc 2 and drop it in loc 3
			Actions builder2 = new Actions(WebDriverManager.getCurrentDriver());
			builder2.moveToElement(WebDriverManager.getCurrentDriver()
					.findElement(By.xpath(
							"//span[contains(text(),'Location 2')]//following::span[contains(text(),'Building 2-1')]")))
					.pause(Duration.ofSeconds(3));
			builder2.clickAndHold(WebDriverManager.getCurrentDriver().findElement(By
					.xpath("//span[contains(text(),'Location 2')]//following::span[contains(text(),'Building 2-1')]")));
			builder2.pause(Duration.ofSeconds(3)).moveByOffset(1, 0);
			builder2.moveToElement(
					WebDriverManager.getCurrentDriver().findElement(By.xpath("(//ul[@class='ui-sortable'])[3]")));
			builder2.moveByOffset(1, 0).pause(Duration.ofSeconds(3)).release();
			builder2.perform();
			Assertions.passTest("Building Page", "Building 2-2 is moved from Location 2 to Location 3");
			accountOverviewPage.saveOrder.waitTillVisibilityOfElement(60);
			accountOverviewPage.saveOrder.scrollToElement();
			accountOverviewPage.saveOrder.click();
			accountOverviewPage.saveOrder.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Building Page", "Clicked on Save Order");
			accountOverviewPage.refreshPage();

			// create a quote
			buildingPage.createQuote.waitTillVisibilityOfElement(60);
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// select buildings to quote
			buildingPage.createQuote.waitTillVisibilityOfElement(60);
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
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

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.quoteName.scrollToElement();
			createQuotePage.quoteName.setData(testData1.get("QuoteName"));
			createQuotePage.enterQuoteDetailsCommercialNew(testData1);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			System.out.println(accountOverviewPage.quoteLink.formatDynamicPath("Quote Test").getData());
			Assertions.verify(
					accountOverviewPage.quoteLink.formatDynamicPath("Quote Test").getData()
							.contains("Quote Test - Location Wise"),
					true, "Account Overview Page", "Quote name is updated to " + testData1.get("QuoteName"), false,
					false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 2:  " + quoteNumber);

			// Go to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search for quote
			homePage.searchQuote(quoteNumber);

			// Quote some dwellings
			Assertions.addInfo("Account Overview Page", "Verifying the Presence of Quote some Buildings button");
			Assertions.verify(accountOverviewPage.quoteSomeBuildingsButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Quote some Buildings button is displayed", false, false);
			accountOverviewPage.quoteSomeBuildingsButton.scrollToElement();
			accountOverviewPage.quoteSomeBuildingsButton.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 91", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 91", "Executed Successfully");
			}
		}
	}
}
