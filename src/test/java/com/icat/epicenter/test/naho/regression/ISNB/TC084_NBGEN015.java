/** Program Description: Check the Guy Carpenter values for NB quote and validate the ELR and AAL values are calculated correctly and verifying mold valie in view print
 *  Author			   : Pavan Mule
 *  Date of Creation   : 14/06/2024
 **/
package com.icat.epicenter.test.naho.regression.ISNB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RmsModelResultsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC084_NBGEN015 extends AbstractNAHOTest {

	public TC084_NBGEN015() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBGEN015.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewPolicySnapShot viewPolicySnapshot = new ViewPolicySnapShot();
		RmsModelResultsPage rmsModelResultsPage = new RmsModelResultsPage();
		int data_Value1 = 0;
		int data_Value2 = 1;

		Map<String, String> testData = data.get(data_Value1);
		DecimalFormat df = new DecimalFormat("0.0");
		String quoteNumber;
		String elrPremium;
		String aalValue;
		String elrValue;
		double calELR;
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			// createQuotePage.enterQuoteDetailsNAHO(testData);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);

			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// click on view/print full quote Premium link
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// verifying mold value in view/print full quote page
			viewPolicySnapshot.moldBuyUp.waitTillVisibilityOfElement(60);
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying Mold Property/Liability value without Modify Forms");
			Assertions.verify(viewPolicySnapshot.moldBuyUp.getData(),
					testData.get("MoldProperty") + " / " + testData.get("MoldLiability"),
					"Mold Value : " + viewPolicySnapshot.moldBuyUp.getData(),
					"Mold Value : " + testData.get("MoldProperty") + "/" + testData.get("MoldLiability"), false, false);

			accountOverviewPage.goBackBtn.click();

			// clicking on edit deductibles
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();

			if (buildingNoLongerQuotablePage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// click on view/print full quote Premium link
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// verifying mold value in view/print full quote page
			viewPolicySnapshot.moldBuyUp.waitTillVisibilityOfElement(60);
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying Mold Property/Liability value with Modify Forms");
			Assertions.verify(viewPolicySnapshot.moldBuyUp.getData(),
					testData.get("MoldProperty") + " / " + testData.get("MoldLiability"),
					"Mold Value : " + viewPolicySnapshot.moldBuyUp.getData(),
					"Mold Value : " + testData.get("MoldProperty") + "/" + testData.get("MoldLiability"), false, false);

			// Click on back button
			accountOverviewPage.goBackBtn.scrollToElement();
			accountOverviewPage.goBackBtn.click();
			Assertions.passTest("Account Overview Page", "Clicked on back button successfully");

			// Adding below code for Guy carpenter
			// Verifying presence of view model result link
			Assertions.addInfo("Scenario 01", "Verifying presence of view model result link");
			Assertions.verify(accountOverviewPage.viewModelResultsLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View model result link displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on view model result link
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view model result link");

			// Verify presence of GC17,ELR Premium, peril AAL and peril ELR labels
			rmsModelResultsPage.closeButton.waitTillVisibilityOfElement(60);
			Assertions.verify(rmsModelResultsPage.closeButton.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page", "RMS Model Result page loaded successfully", false, false);
			Assertions.addInfo("Scenario 02", "Verify presence of GC17,ELR Premium, peril AAL and peril ELR labels");
			Assertions.verify(
					rmsModelResultsPage.gc17Label.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.gc17Label.getData().contains("GC17"),
					true, "RMS Model Result Page",
					"Guy Carpenter label is " + rmsModelResultsPage.gc17Label.getData() + " displayed", false, false);
			Assertions.verify(
					rmsModelResultsPage.elrPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.elrPremiumLabel.getData().contains("ELR Premium"),
					true, "RMS Model Result Page", "Guy Carpenter ELR Premium label is "
							+ rmsModelResultsPage.elrPremiumLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterELRLabel.getData().contains("Peril ELR"),
					true, "RMS Model Result Page", "Guy Carpenter Peril ELR label is "
							+ rmsModelResultsPage.guyCarpenterELRLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterAALLabel.getData().contains("Peril AAL"),
					true, "RMS Model Result Page", "Guy Carpenter Peril AAL label is "
							+ rmsModelResultsPage.guyCarpenterAALLabel.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Verifying and Asserting ELR Premium,Peril AAL and Peril ELR values
			Assertions.addInfo("Scenario 03", "Verifying and Asserting ELR Premium,Peril AAL and Peril ELR values");
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("ELR Premium").checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"ELR Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium").getData()
							+ " displayed",
					false, false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterELR.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"Peril ELR value is " + rmsModelResultsPage.guyCarpenterELR.getData() + " displayed", false, false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterAAL.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"Peril AAL value is " + rmsModelResultsPage.guyCarpenterAAL.getData() + " displayed", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Getting ELR Premium,AAL Value and ELR Value
			elrPremium = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium").getData()
					.replaceAll("[^a-zA-Z0-9\\s]", "");
			aalValue = rmsModelResultsPage.guyCarpenterAAL.getData().replaceAll("[^a-zA-Z0-9\\s]", "");
			elrValue = rmsModelResultsPage.guyCarpenterELR.getData().replace("%", "");

			// Calculating ELR = (AAL/ELR Premium)*100
			// Verifying actual and calculated Peril ELR
			Assertions.addInfo("Scenario 04", "Verifying actual and calculated Peril ELR");
			calELR = (Double.parseDouble(aalValue) / Double.parseDouble(elrPremium)) * 100;

			// Rounding cal ELR decimal value to 1 digits(eg.36.08 = 36.1)
			BigDecimal b_calELR = new BigDecimal(Double.toString(calELR));
			b_calELR = b_calELR.setScale(1, RoundingMode.HALF_UP);
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(elrValue), 2) - Precision.round(calELR, 2)),
					2) < 0.5) {
				Assertions.passTest("RMS Model Result Page", "Calculated Peril ELR is " + b_calELR + "%");
				Assertions.passTest("RMS Model Result Page", "Actual Peril ELR is " + elrValue + "%");

			} else {
				Assertions.verify(elrValue, calELR, "RMS Model Result Page",
						"The Difference between actual and calculated peril ELR value is more than 0.5", false, false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on close
			rmsModelResultsPage.closeButton.scrollToElement();
			rmsModelResultsPage.closeButton.click();
			Assertions.passTest("RMS Model Result Page", "Clicked on close button successfully");

			// Click on edit building pencil button
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit building link successfully");

			// change construction type
			Assertions.passTest("Dwelling Page",
					"Original construcation type is " + testData.get("L1D1-DwellingConstType"));
			testData = data.get(data_Value2);
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page",
					"Updated construcation type is " + testData.get("L1D1-DwellingConstType"));
			Assertions.passTest("Dwelling Page", "Construction details entered successfully");

			// change cov A value
			testData = data.get(data_Value1);
			Assertions.passTest("Create Quote Page", "Original Cov A value is " + testData.get("L1D1-DwellingCovA"));
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote page loaded successfully", false, false);
			testData = data.get(data_Value2);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page", "Updated Cov A value is " + testData.get("L1D1-DwellingCovA"));
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			if (createQuotePage.pageName.getData().contains("Create Quote")) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			Assertions.passTest("Create Quote Page", "Insured Value entered successfully");

			// Getting the quote number from account overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Verifying presence of view model result link after updating construction type
			// and Cov A
			Assertions.addInfo("Scenario 05",
					"Verifying presence of view model result link after updating construction type and Cov A");
			Assertions.verify(accountOverviewPage.viewModelResultsLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View model result link displayed", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on view model result link
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view model result link");

			// Verify presence of GC17,ELR Premium, peril AAL and peril ELR labels, after
			// updating construction type and Cov A
			rmsModelResultsPage.closeButton.waitTillVisibilityOfElement(60);
			Assertions.verify(rmsModelResultsPage.closeButton.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page", "RMS Model Result page loaded successfully", false, false);
			Assertions.addInfo("Scenario 06",
					"Verify presence of GC17,ELR Premium, peril AAL and peril ELR labels,after updating construction type and Cov A");
			Assertions.verify(
					rmsModelResultsPage.gc17Label.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.gc17Label.getData().contains("GC17"),
					true, "RMS Model Result Page",
					"Guy Carpenter label is " + rmsModelResultsPage.gc17Label.getData() + " displayed", false, false);
			Assertions.verify(
					rmsModelResultsPage.elrPremiumLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.elrPremiumLabel.getData().contains("ELR Premium"),
					true, "RMS Model Result Page", "Guy Carpenter ELR Premium label is "
							+ rmsModelResultsPage.elrPremiumLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterELRLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterELRLabel.getData().contains("Peril ELR"),
					true, "RMS Model Result Page", "Guy Carpenter Peril ELR label is "
							+ rmsModelResultsPage.guyCarpenterELRLabel.getData() + " displayed",
					false, false);
			Assertions.verify(
					rmsModelResultsPage.guyCarpenterAALLabel.checkIfElementIsDisplayed()
							&& rmsModelResultsPage.guyCarpenterAALLabel.getData().contains("Peril AAL"),
					true, "RMS Model Result Page", "Guy Carpenter Peril AAL label is "
							+ rmsModelResultsPage.guyCarpenterAALLabel.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Verifying and Asserting ELR Premium,Peril AAL and Peril ELR values,after
			// updating construction type and Cov A
			Assertions.addInfo("Scenario 07",
					"Verifying and Asserting ELR Premium,Peril AAL and Peril ELR values,after updating construction type and Cov A");
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("ELR Premium").checkIfElementIsDisplayed(),
					true, "RMS Model Result Page",
					"Updated ELR Premium value is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium").getData()
							+ " displayed",
					false, false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterELR.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"Updated Peril ELR value is " + rmsModelResultsPage.guyCarpenterELR.getData() + " displayed", false,
					false);
			Assertions.verify(rmsModelResultsPage.guyCarpenterAAL.checkIfElementIsDisplayed(), true,
					"RMS Model Result Page",
					"Updated Peril AAL value is " + rmsModelResultsPage.guyCarpenterAAL.getData() + " displayed", false,
					false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Getting ELR Premium,AAL Value and ELR Value
			elrPremium = rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium").getData()
					.replaceAll("[^a-zA-Z0-9\\s]", "");
			aalValue = rmsModelResultsPage.guyCarpenterAAL.getData().replaceAll("[^a-zA-Z0-9\\s]", "");
			elrValue = rmsModelResultsPage.guyCarpenterELR.getData().replace("%", "");

			// Calculating ELR = (AAL/ELR Premium)*100
			// Verifying actual and calculated Peril ELR,after updating construction type
			// and Cov A
			Assertions.addInfo("Scenario 08",
					"Verifying actual and calculated Peril ELR,after updating construction type and Cov A");
			calELR = (Double.parseDouble(aalValue) / Double.parseDouble(elrPremium)) * 100;
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(elrValue), 2) - Precision.round(calELR, 2)),
					2) < 0.5) {
				Assertions.passTest("RMS Model Result Page", "Calculated Peril ELR is " + df.format(calELR) + "%");
				Assertions.passTest("RMS Model Result Page", "Actual Peril ELR is " + elrValue + "%");

			} else {
				Assertions.verify(elrValue, calELR, "RMS Model Result Page",
						"The Difference between actual and calculated peril ELR value is more than 0.5", false, false);
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click on close
			rmsModelResultsPage.closeButton.scrollToElement();
			rmsModelResultsPage.closeButton.click();
			Assertions.passTest("RMS Model Result Page", "Clicked on close button successfully");

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC084 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC084 ", "Executed Successfully");
			}
		}
	}
}
