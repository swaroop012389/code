/** Program Description: Create a AOP Quote and click on View/Print Ful quote. Assert all available data on the quote document
 *  Author			   : John
 *  Date of Creation   : 08/28/2020
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RmsModelResultsPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC078 extends AbstractCommercialTest {

	public TC078() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID078.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RmsModelResultsPage rmsModelResultsPage = new RmsModelResultsPage();

		// Initializing the variables
		String quoteNumber;
		int data_Value1 = 0;
		int k = 1;
		Map<String, String> testData = data.get(data_Value1);
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

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on view/print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full Quote link");

			// Assert values on view print full quote
			Assertions.verify(viewOrPrintFullQuotePage.quoteNumber.getData().contains(quoteNumber), true,
					"View/Print Full Quote Page", viewOrPrintFullQuotePage.quoteNumber.getData() + " is displayed",
					false, false);
			Assertions.addInfo("View/Print Full Quote Page",
					"Assert the Premium Details deductible Values on Quote Document");
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
			for (int i = 1; i <= 4; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(i).getData() + " is displayed",
						false, false);
			}
			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(10).checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"Address " + viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(10).getData()
							+ " is displayed",
					false, false);
			for (int i = 6, j = 11; i <= 8 && j <= 13; i++, j++) {
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
			for (int i = 3, j = 2; i <= 20 && j <= 21; i += 2, j += 2) {
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
							.formatDynamicPath("Coinsurance", "20").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.standardCoverageValues.formatDynamicPath("Coinsurance", "20").getData()
							+ " " + viewOrPrintFullQuotePage.standardCoverageValues
									.formatDynamicPath("Coinsurance", "21").getData()
							+ " is displayed",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", "1")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Ordinance or law value " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Ordinance", "1").getData() + " is displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", "2")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", "2").getData()
							+ " is displayed",
					false, false);

			for (int i = 3, j = 4; i <= 21 && j <= 22; i += 2, j += 2) {
				Assertions.verify(
						viewOrPrintFullQuotePage.selectedCoverageDetails
								.formatDynamicPath("Ordinance", i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", i).getData()
								+ " " + viewOrPrintFullQuotePage.selectedCoverageDetails
										.formatDynamicPath("Ordinance", j).getData()
								+ " is displayed",
						false, false);
			}

			for (int i = 25; i <= 27; i++) {
				Assertions.verify(
						viewOrPrintFullQuotePage.selectedCoverageDetails
								.formatDynamicPath("Ordinance", i).checkIfElementIsDisplayed(),
						true, "View/Print Full Quote Page",
						"Available Package "
								+ viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", i)
										.getData()
								+ " " + viewOrPrintFullQuotePage.selectedCoverageDetails
										.formatDynamicPath("Ordinance", i).getData()
								+ " is displayed",
						false, false);
			}
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Ordinance", "127").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", "126").getData()
							+ ", "
							+ viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Ordinance", "127")
									.getData()
							+ " " + viewOrPrintFullQuotePage.selectedCoverageDetails
									.formatDynamicPath("Ordinance", "128").getData()
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

			// Ticket IO-21662 is added

			// go back to account overview page
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Push to RMS
			accountOverviewPage.pushToRMSLink.checkIfElementIsPresent();
			accountOverviewPage.pushToRMSLink.scrollToElement();
			accountOverviewPage.pushToRMSLink.click();

			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();
			Assertions.addInfo("Scenario",
					"Verifying that the Guy Carpenter values are present on the Guy Carpenter/RMS Models Results Page");
			for (int i = 0; i < 5; i++) {
				int dataValue = i;
				testData = data.get(dataValue);
				rmsModelResultsPage.rmsModelResultValues.formatDynamicPath(testData.get("GuyCarpenterResult"))
						.waitTillPresenceOfElement(60);
				rmsModelResultsPage.rmsModelResultValues.formatDynamicPath(testData.get("GuyCarpenterResult"))
						.waitTillVisibilityOfElement(60);
				rmsModelResultsPage.rmsModelResultValues.formatDynamicPath(testData.get("GuyCarpenterResult"))
						.scrollToElement();
				String GCResults = rmsModelResultsPage.rmsModelResultValues
						.formatDynamicPath(testData.get("GuyCarpenterResult")).getData();
				Assertions.passTest("Guy Carpenter/RMS Models Results Page",
						testData.get("GuyCarpenterResult") + " is present and the value is " + (GCResults));
				rmsModelResultsPage.waitTime(2);

			}
			Assertions.verify(rmsModelResultsPage.tivValue.checkIfElementIsPresent(), true,
					"Guy Carpenter/RMS Models Results Page",
					"TIV value is present and the value is: " + rmsModelResultsPage.tivValue.getData(), false, false);
			Assertions.addInfo("Scenario", "Scenario Ended");

			rmsModelResultsPage.closeButton.scrollToElement();
			rmsModelResultsPage.closeButton.click();

			// Ticket IO-21662 is Ended

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 78", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 78", "Executed Successfully");
			}
		}
	}
}
