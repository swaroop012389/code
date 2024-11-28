/** Program Description: Checking For Inclusion of TRIA Premium Value for GL and AOP Peril Quotes
 *  Author			   :  Karthik Malles
 *  Date of Creation   : 08/16/2021
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
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC117 extends AbstractCommercialTest {

	public TC117() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID117.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		GLInformationPage gLInformationPage = new GLInformationPage();

		// Initializing and declaring the variables
		String quoteNumberWithoutTRIA, quoteNumberWithTRIA;
		int premiumWithoutTRIA, premiumWithTRIA, tRIAPremiumValue;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData1 = data.get(data_Value1);
		Map<String, String> testData2 = data.get(data_Value2);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData1, setUpData);

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData1);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData1);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData1);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData1.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData1);

			// Entering GL Information
			if (!testData1.get("L1-GLLocationClass").equals("")) {
				Assertions.verify(gLInformationPage.locationClassArrow.formatDynamicPath(0).checkIfElementIsDisplayed(),
						true, "GL Information Page", "GL Information Page loaded successfully", false, false);
				gLInformationPage.enterGLInformation(testData1);
				Assertions.passTest("GL Information Page", "GL Information details entered successfully");
			}

			// Adding code for IO-19411
			// Entering Create Quote Page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData1);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Account Overview Page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumberWithoutTRIA = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page",
					"Quote Number with Terrorism Chosen as No: " + quoteNumberWithoutTRIA);

			// click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Or Print Full Quote Link Successfully");

			// View Or Print Full Quote Page
			Assertions.verify(viewOrPrintFullQuotePage.quoteNumber.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "View Or Print Full Quote Page loaded Successfully", false, false);
			Assertions.addInfo("View Or Print Full Quote Page",
					"Asserting TRIA Premium on View Or Print Full Quote Page when Terrorism is selected as No");
			viewOrPrintFullQuotePage.tRIAValue.scrollToElement();
			tRIAPremiumValue = Integer
					.parseInt(viewOrPrintFullQuotePage.tRIAValue.getData().substring(40).replaceAll(",", ""));
			premiumWithoutTRIA = Integer.parseInt(viewOrPrintFullQuotePage.premiumSubTotal.getData()
					.substring(1, viewOrPrintFullQuotePage.premiumSubTotal.getData().length()).replaceAll(",", ""));
			Assertions.passTest("View Or Print Full Quote Page",
					"Premium Value without TRIA Premium and The Value of TRIA Premium are extracted The "
							+ "Premium Value Without TRIA Premium is   $" + premiumWithoutTRIA
							+ "  and The TRIA Premium is   $" + tRIAPremiumValue);

			// click on back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Account Overview Page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded Successfully", false, false);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Edit Deductibles Clicked Successfully");

			// Create Quote Page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded Successfully", false, false);
			if (!testData2.get("Terrorism").equals("") && createQuotePage.terrorismArrow.checkIfElementIsDisplayed()) {
				Assertions.addInfo("Create Quote Page",
						"Terrorism original Value : " + createQuotePage.terrorismData.getData());
				createQuotePage.terrorismArrow.waitTillVisibilityOfElement(60);
				createQuotePage.terrorismArrow.scrollToElement();
				createQuotePage.terrorismArrow.click();
				createQuotePage.terrorismOption.formatDynamicPath(testData2.get("Terrorism")).scrollToElement();
				createQuotePage.terrorismOption.formatDynamicPath(testData2.get("Terrorism")).click();
				Assertions.passTest("Create Quote Page",
						"Terrorism Latest Value : " + createQuotePage.terrorismData.getData());
			}

			// click on get a quote
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				createQuotePage.continueButton.waitTillInVisibilityOfElement(60);
			}

			// Account Overview Page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded Successfully", false, false);
			quoteNumberWithTRIA = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page",
					"Quote Number with Terrorism Chosen as Yes: " + quoteNumberWithTRIA);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Or Print Full Quote Link");

			// View Or Print Full Quote Page
			Assertions.verify(viewOrPrintFullQuotePage.quoteNumber.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "View Or Print Quote Page loaded Successfully", false, false);
			Assertions.addInfo("View Or Print Full Quote Page",
					"Asserting TRIA Premium on View Or Print Full Quote Page when Terrorism is selected as Yes");
			viewOrPrintFullQuotePage.premiumSubTotal.scrollToElement();
			premiumWithTRIA = Integer.parseInt(viewOrPrintFullQuotePage.premiumSubTotal.getData()
					.substring(1, viewOrPrintFullQuotePage.premiumSubTotal.getData().length()).replaceAll(",", ""));
			Assertions.verify((premiumWithTRIA == premiumWithoutTRIA + tRIAPremiumValue), true,
					"View Or Print Full Quote Page",
					"The Premium Value with TRIA Premium Included is   $" + premiumWithTRIA
							+ "  The Premium Value when Terrorism is selected as Yes is found to have TRIA Premium included in it",
					false, false);
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 117", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 117", "Executed Successfully");
			}
		}
	}
}
