/** Program Description: Create a Wind Quote and click on View/Print Full quote. Assert all available data on the quote document.
 *  Author			   : John
 *  Date of Creation   : 08/28/2020
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
//import com.epicenter.POM.AccountDetails;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC077 extends AbstractCommercialTest {

	public TC077() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID077.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages
		HomePage homePage = new HomePage();
		// AccountDetails accountDetails = AccountDetails.initialize();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		Map<String, String> testData;

		// Initializing the Variables
		String quoteNumber;
		int dataValue1 = 0;
		int k = 1;
		testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// New Account creation
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully ", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// ZipCode EligibilityPage
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Zipcode Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "ZipCode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location Details Entered successfully");

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Select peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Asserting navigation to create quote page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on view/print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full Quote link");

			// Assert values on view print full quote
			Assertions.addInfo("View/Print Full Quote Page",
					"Assert the Premium Details deductible Values on Quote Document");
			Assertions.verify(viewOrPrintFullQuotePage.quoteNumber.getData().contains(quoteNumber), true,
					"View/Print Full Quote Page", viewOrPrintFullQuotePage.quoteNumber.getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath("1").getData()
							.contains(testData.get("InsuredName")),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath("1").getData() + " is displayed", false,
					false);
			for (int i = 2; i <= 12; i++) {
				if (i == 7 || i == 8 || i == 9 || i == 10) {
					continue;
				} else {
					Assertions.verify(
							viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath(i).checkIfElementIsDisplayed(),
							true, "View/Print Full Quote Page",
							viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath(i).getData() + " is displayed",
							false, false);
				}
			}
			for (int i = 1; i <= 2; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(i).getData() + " is displayed",
						false, false);
			}
			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(9).checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"Address " + viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(9).getData()
							+ " is displayed",
					false, false);
			for (int i = 4, j = 10; i <= 7 && j <= 13; i++, j++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(i).getData() + " "
								+ viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(j).getData()
								+ " is displayed",
						false, false);

			}
			for (int i = 19; i <= 20; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						"Coverage not selected for the following APCs"
								+ viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(i).getData()
								+ " is displayed",
						false, false);
			}
			Assertions.addInfo("View/Print Full Quote Page", "Assert the Coverage Details on quote document");
			for (int i = 3, j = 2; i <= 14 && j <= 15; i += 2, j += 2) {
				Assertions.verify(
						viewOrPrintFullQuotePage.standardCoverageValues
								.formatDynamicPath("Coinsurance", i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.standardCoverageValues.formatDynamicPath("Coinsurance", j).getData()
								+ " " + viewOrPrintFullQuotePage.standardCoverageValues
										.formatDynamicPath("Coinsurance", i).getData()
								+ " is displayed",
						false, false);
			}
			Assertions.verify(
					viewOrPrintFullQuotePage.standardCoverageValues
							.formatDynamicPath("Coinsurance", "14").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.standardCoverageValues.formatDynamicPath("Coinsurance", "14").getData()
							+ " " + viewOrPrintFullQuotePage.standardCoverageValues
									.formatDynamicPath("Coinsurance", "15").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", "1")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Selected Coverage, Ordinance or Law value " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Ordinance", "1").getData() + " is displayed",
					false, false);
			for (int i = 4; i <= 6; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", "3")
								.checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						"Available Package " + viewOrPrintFullQuotePage.selectedCoverageDetails
								.formatDynamicPath("Ordinance", i).getData() + " is displayed",
						false, false);
			}
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Ordinance", "38").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", "38").getData()
							+ ", "
							+ viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", 39)
									.getData()
							+ " " + viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", 40)
									.getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Ordinance", "38").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", "38").getData()
							+ ", "
							+ viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", 41)
									.getData()
							+ " " + viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", 42)
									.getData()
							+ " is displayed",
					false, false);

			Assertions.addInfo("View/Print Full Quote Page", "Assert the Building Details on quote document");
			for (int i = 1; i <= 6; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.buildingDetails.formatDynamicPath(k, k, i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.buildingDetails.formatDynamicPath(k, k, i).getData() + " is displayed",
						false, false);
			}

			for (int i = 8; i <= 23; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.buildingDetails.formatDynamicPath(k, k, i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.buildingDetails.formatDynamicPath(k, k, i).getData() + " is displayed",
						false, false);
			}

			/*
			 * //Added IO-21706-ready for dev
			 * viewOrPrintFullQuotePage.backButton.scrollToElement();
			 * viewOrPrintFullQuotePage.backButton.click();
			 *
			 * accountOverviewPage.accountDetailsLink.checkIfElementIsPresent();
			 * accountOverviewPage.accountDetailsLink.scrollToElement();
			 * accountOverviewPage.accountDetailsLink.click();
			 *
			 * accountOverviewPage.editAccountDetails.checkIfElementIsPresent();
			 * accountOverviewPage.editAccountDetails.scrollToElement();
			 * accountOverviewPage.editAccountDetails.click();
			 *
			 * accountDetails.effectiveDate.checkIfElementIsPresent();
			 * accountDetails.effectiveDate.scrollToElement();
			 * accountDetails.effectiveDate.setData(testData.get("PolicyEffDate"));
			 *
			 * //Some code will need to be included once the ticket is deployed
			 */

			// SignOut
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 77", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 77", "Executed Successfully");
			}
		}
	}
}