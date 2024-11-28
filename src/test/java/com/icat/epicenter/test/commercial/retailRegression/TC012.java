/** Program Description: Check if for all states EXCEPT Louisiana, Mississippi, Arizona, Nevada, and Utah the following line will be added as a bullet point under Subject To in the Terms and Conditions Section of commercial retail quotes:
Completed and signed diligent effort form.
 *  Author			   : Yeshashwini
 *  Date of Creation   : 20/07/2021
 **/
package com.icat.epicenter.test.commercial.retailRegression;

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
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC012 extends AbstractCommercialTest {

	public TC012() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID012.xls";
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

		// Initializing variables
		String quoteNumber;
		Map<String, String> testData;
		boolean isTestPassed = false;

		try {
			for (int i = 0; i < 7; i++) {
				testData = data.get(i);

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
				if (!testData.get("Peril").equals("EQ")) {
					Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true,
							"Select Peril Page", "Select Peril Page loaded successfully", false, false);
					selectPerilPage.selectPeril(testData.get("Peril"));
				}

				// Entering Prior Losses
				if (!testData.get("PriorLoss1").equals("")) {
					Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true,
							"Prior Loss Page", "Prior Loss Page loaded successfully", false, false);
					priorLossesPage.selectPriorLossesInformation(testData);
				}

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

				// Click on View print full quote link
				accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
				accountOverviewPage.viewPrintFullQuoteLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on View/Print Full quote link");
				Assertions.addInfo("Scenario 01",
						"Checking all states EXCEPT Louisiana, Mississippi, Arizona, Nevada, and Utah as a bullet point under Subject To in the Terms and Conditions Section of commercial retail quotes");
				if (i == 5 || i == 6) {
					Assertions.verify(
							viewOrPrintFullQuotePage.subjectToWordings.formatDynamicPath("diligent effort form")
									.checkIfElementIsPresent(),
							true, "View/Print Full Quote Page",
							"Completed and signed diligent effort form displayed for Quote " + quoteNumber
									+ " under SubjectTo section is verified",
							false, false);
				} else {
					Assertions.verify(
							viewOrPrintFullQuotePage.subjectToWordings.formatDynamicPath("diligent effort form")
									.checkIfElementIsPresent(),
							false, "View/Print Full Quote Page",
							"Completed and signed diligent effort form not displayed for Quote " + quoteNumber
									+ " under SubjectTo section is verified",
							false, false);
				}
				Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
			}

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC012 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC012 ", "Executed Successfully");
			}
		}
	}
}
