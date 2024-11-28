package com.icat.epicenter.test.naho.regression.ISNB;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC054_NBPO002 extends AbstractNAHOTest {

	public TC054_NBPO002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBPO002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();

		// Initializing Variables
		Map<String, String> testData;
		int data_Value1 = 0;
		int data_Value2 = 1;
		testData = data.get(data_Value1);
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat df1 = new DecimalFormat("0");
		boolean isTestPassed = false;

		try {
			// Creating a new account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Checking the processing of Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering Prior Loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Quote details
			Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Clicking on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.waitTillVisibilityOfElement(60);
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Validating the total premium value
			Assertions.passTest("Account Overview Page",
					"Quote Number is :" + accountOverviewPage.quoteNumber.getData());
			Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Policy Premium : " + accountOverviewPage.totalPremiumValue.getData(),
					false, false);

			// Assert premium values in rate trace before overriding the premium values
			accountOverviewPage.viewOrPrintRateTrace.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();
			double windPremiumValuebeforePremiumAdj = Double
					.parseDouble(viewOrPrintRateTrace.windPremiumVaue.getData().replace("$", "").replace(",", ""));
			double windMinPremiumValuebeforePremiumAdj = Double
					.parseDouble(viewOrPrintRateTrace.windMinPremiumValue.getData().replace("$", "").replace(",", ""));
			double aopPremiumValuebeforePremiumAdj = Double
					.parseDouble(viewOrPrintRateTrace.aopPremiumValue.getData().replace(",", ""));
			double aopMinPremiumValuebeforePremiumAdj = Double
					.parseDouble(viewOrPrintRateTrace.aopMinPremiumValue.getData().replace(",", ""));
			double glPremiumValuebeforePremiumAdj = Double
					.parseDouble(viewOrPrintRateTrace.glPremiumValue.getData().replace(",", ""));
			double glMinPremiumValuebeforePremiumAdj = Double
					.parseDouble(viewOrPrintRateTrace.gLMinPremiumValue.getData().replace(",", ""));
			viewOrPrintRateTrace.backBtn.waitTillVisibilityOfElement(60);
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();

			// Clicking on override premium link
			accountOverviewPage.overridePremiumLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();

			// Validating loading of Override Premium And Fees Page
			Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
					"Override Premium And Fees Page", "Override Premium And Fees Page loaded successfully", false,
					false);

			testData = data.get(data_Value2);

			// Entering override premium value
			overridePremiumAndFeesPage.enterFeesDetailsNAHO(testData);
			Assertions.passTest("Override Premium And Fees Page", "Override details entered successfully");

			// Validating the total premium value after override
			Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Policy Premium after override : " + accountOverviewPage.totalPremiumValue.getData(), false, false);

			// Assert premium values in rate trace after overriding the premium values
			accountOverviewPage.viewOrPrintRateTrace.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();
			double windPremiumValueafterPremiumAdj = Double
					.parseDouble(viewOrPrintRateTrace.windPremiumVaue.getData().replace("$", "").replace(",", ""));
			double windMinPremiumValueafterPremiumAdj = Double.parseDouble(viewOrPrintRateTrace.windPremiumOverrideValue
					.getData().replace("$", "").replace(",", "").replace("-", ""));
			double aopPremiumValueafterPremiumAdj = Double
					.parseDouble(viewOrPrintRateTrace.aopPremiumValue.getData().replace(",", ""));
			double aopMinPremiumValueafterPremiumAdj = Double.parseDouble(
					viewOrPrintRateTrace.aopPremiumOverrideValue.getData().replace(",", "").replace("-", ""));
			double glPremiumValueafterPremiumAdj = Double
					.parseDouble(viewOrPrintRateTrace.glPremiumValue.getData().replace(",", ""));
			double glMinPremiumValueafterPremiumAdj = Double
					.parseDouble(viewOrPrintRateTrace.gLMinPremiumValue.getData().replace(",", "").replace("-", ""));

			double windPremiumAdjValue = windPremiumValuebeforePremiumAdj - windPremiumValueafterPremiumAdj;
			double aopPremiumAdjValue = aopPremiumValuebeforePremiumAdj - aopPremiumValueafterPremiumAdj;
			double glPremiumAdjValue = glPremiumValuebeforePremiumAdj - glPremiumValueafterPremiumAdj;
			System.out.println("-" + windPremiumAdjValue);
			System.out.println(windMinPremiumValueafterPremiumAdj);
			Assertions.verify(viewOrPrintRateTrace.windPremiumVaue.checkIfElementIsDisplayed(), true,
					"View Print Rate Trace Page", "Wind Premium Value in Rate Trace page before premium override is: "
							+ windPremiumValuebeforePremiumAdj,
					false, false);
			Assertions.verify(windPremiumValueafterPremiumAdj < windPremiumValuebeforePremiumAdj, true,
					"View Print Rate Trace Page", "Wind Premium Value in Rate Trace page after premium override is: "
							+ windPremiumValueafterPremiumAdj,
					false, false);
			Assertions.verify(viewOrPrintRateTrace.windMinPremiumValue.checkIfElementIsDisplayed(), true,
					"View Print Rate Trace Page",
					"Wind Premium Adjustment Value in Rate Trace page before premium override is: "
							+ windMinPremiumValuebeforePremiumAdj,
					false, false);
			Assertions.verify(windMinPremiumValueafterPremiumAdj, windPremiumAdjValue, "View Print Rate Trace Page",
					"Wind Premium Adjustment Value in Rate Trace page after premium override is: " + "-"
							+ windMinPremiumValueafterPremiumAdj,
					false, false);
			Assertions.verify(viewOrPrintRateTrace.aopPremiumValue.checkIfElementIsDisplayed(), true,
					"View Print Rate Trace Page", "AOP Premium Value in Rate Trace page before premium override is: "
							+ aopPremiumValuebeforePremiumAdj,
					false, false);
			Assertions.verify(aopPremiumValueafterPremiumAdj < aopPremiumValuebeforePremiumAdj, true,
					"View Print Rate Trace Page",
					"AOP Premium Value in Rate Trace page after premium override is: " + aopPremiumValueafterPremiumAdj,
					false, false);
			Assertions.verify(viewOrPrintRateTrace.aopMinPremiumValue.checkIfElementIsDisplayed(), true,
					"View Print Rate Trace Page",
					"AOP Premium Adjustment Value in Rate Trace page before premium override is: "
							+ aopMinPremiumValuebeforePremiumAdj,
					false, false);
			Assertions.verify(aopMinPremiumValueafterPremiumAdj, aopPremiumAdjValue, "View Print Rate Trace Page",
					"AOP Premium Adjustment Value in Rate Trace page after premium override is: " + "-"
							+ aopMinPremiumValueafterPremiumAdj,
					false, false);
			Assertions.verify(viewOrPrintRateTrace.glPremiumValue.checkIfElementIsDisplayed(), true,
					"View Print Rate Trace Page",
					"GL Premium Value in Rate Trace page before premium override is: " + glPremiumValuebeforePremiumAdj,
					false, false);
			Assertions.verify(glPremiumValueafterPremiumAdj < glPremiumValuebeforePremiumAdj, true,
					"View Print Rate Trace Page",
					"GL Premium Value in Rate Trace page after premium override is: " + glPremiumValueafterPremiumAdj,
					false, false);
			Assertions.verify(viewOrPrintRateTrace.gLMinPremiumValue.checkIfElementIsDisplayed(), true,
					"View Print Rate Trace Page",
					"GL Premium Adjustment Value in Rate Trace page before premium override is: "
							+ glMinPremiumValuebeforePremiumAdj,
					false, false);
			Assertions.verify(glMinPremiumValueafterPremiumAdj, glPremiumAdjValue, "View Print Rate Trace Page",
					"GL Premium Adjustment Value in Rate Trace page after premium override is: " + "-"
							+ glMinPremiumValueafterPremiumAdj,
					false, false);

			// ---Added IO-21602----

			testData = data.get(data_Value1);
			// Getting the expected value for the Endorsement factor value from the test
			// data
			String windGreenEndorsementFactorValue = testData.get("WindGreenEndorsementFactorValue");
			String aopGreenEndorsementFactorValue = testData.get("AopGreenEndorsementFactorValue");
			String windGreenEndorsementFactorValue1 = testData.get("WindGreenEndorsementFactorValue1");
			String aopGreenEndorsementFactorValue1 = testData.get("AopGreenEndorsementFactorValue1");

			Double d_windGreenEndorsementFactorValue = Double
					.parseDouble(viewOrPrintRateTrace.windGreenEndorsementFactorValue.getData());
			String actualWindGreenEndorsementFactorValue = df.format(d_windGreenEndorsementFactorValue);
			Double d_aopGreenEndorsementFactorValue = Double
					.parseDouble(viewOrPrintRateTrace.aopGreenEndorsementFactorValue.getData());
			String actualAopGreenEndorsementFactorValue = df.format(d_aopGreenEndorsementFactorValue);

			Double d_windGreenEndorsementFactorValue1 = Double
					.parseDouble(viewOrPrintRateTrace.windGreenEndorsementFactorValue.getData());
			String actualWindGreenEndorsementFactorValue1 = df1.format(d_windGreenEndorsementFactorValue1);
			Double d_aopGreenEndorsementFactorValue1 = Double
					.parseDouble(viewOrPrintRateTrace.aopGreenEndorsementFactorValue.getData());
			String actualAopGreenEndorsementFactorValue1 = df1.format(d_aopGreenEndorsementFactorValue1);

			if (viewOrPrintRateTrace.windGreenEndorsementFactor.checkIfElementIsPresent()
					&& viewOrPrintRateTrace.windGreenEndorsementFactor.checkIfElementIsDisplayed()) {
				Assertions.addInfo("Scenario 01",
						"Verifying the green endorsement factor value displayed for the Wind in Rate trace page when the Green Endorsement is added");
				Assertions.verify(
						viewOrPrintRateTrace.windGreenEndorsementFactorValue.checkIfElementIsPresent()
								&& actualWindGreenEndorsementFactorValue.equals(windGreenEndorsementFactorValue),
						true, "View Or Print Rate Trace Page",
						"The Green Endorsement Factor Value is displayed for Wind in Rate trace page is verified",
						false, false);
				Assertions.passTest("View or Print Rate Trace Page",
						"The Wind Green Endorsement Factor Value with Green Endorsement Added is: "
								+ viewOrPrintRateTrace.windGreenEndorsementFactorValue.getData());
				Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			}

			if (viewOrPrintRateTrace.aopGreenEndorsementFactor.checkIfElementIsPresent()
					&& viewOrPrintRateTrace.aopGreenEndorsementFactor.checkIfElementIsDisplayed()) {
				Assertions.addInfo("Scenario 02",
						"Verifying the green endorsement factor value displayed for the AOP in Rate trace page when the Green Endorsement is added");
				Assertions.verify(
						viewOrPrintRateTrace.aopGreenEndorsementFactorValue.checkIfElementIsPresent()
								&& actualAopGreenEndorsementFactorValue.equals(aopGreenEndorsementFactorValue),
						true, "View Or Print Rate Trace Page",
						"The Green Endorsement Factor Value is displayed for AOP in Rate trace page is verified", false,
						false);
				Assertions.passTest("View or Print Rate Trace Page",
						"The AOP Green Endorsement Factor Value with Green Endorsement Added is: "
								+ viewOrPrintRateTrace.aopGreenEndorsementFactorValue.getData());
				Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			}

			// Navigate back to Account overview page

			viewOrPrintRateTrace.backBtn.waitTillVisibilityOfElement(60);
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();

			// Creating another quote
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// Selecting Green upgrade as 'No' on the create quote page
			if (createQuotePage.greenUpgradesArrow.checkIfElementIsPresent()
					&& createQuotePage.greenUpgradesArrow.checkIfElementIsDisplayed()) {
				createQuotePage.greenUpgradesArrow.scrollToElement();
				createQuotePage.greenUpgradesArrow.click();
				createQuotePage.greenUpgradesNoOption.scrollToElement();
				createQuotePage.greenUpgradesNoOption.click();
			}

			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Click continue for warning message
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			String quoteNumber2 = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number2 is : " + quoteNumber2);

			// Navigate to Rate trace page
			accountOverviewPage.viewOrPrintRateTrace.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();

			if (viewOrPrintRateTrace.windGreenEndorsementFactor.checkIfElementIsPresent()
					&& viewOrPrintRateTrace.windGreenEndorsementFactor.checkIfElementIsDisplayed()) {
				Assertions.addInfo("Scenario 03",
						"Verifying the green endorsement factor value displayed for the Wind in Rate trace page when the Green Endorsement is not added");
				Assertions.verify(
						viewOrPrintRateTrace.windGreenEndorsementFactorValue.checkIfElementIsPresent()
								&& actualWindGreenEndorsementFactorValue1.equals(windGreenEndorsementFactorValue1),
						true, "View Or Print Rate Trace Page",
						"The Green Endorsement Factor Value is displayed for Wind in Rate trace page is verified",
						false, false);
				Assertions.passTest("View or Print Rate Trace Page",
						"The Wind Green Endorsement Factor Value without Green Endorsement Added is: "
								+ viewOrPrintRateTrace.windGreenEndorsementFactorValue.getData());
				Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			}

			if (viewOrPrintRateTrace.aopGreenEndorsementFactor.checkIfElementIsPresent()
					&& viewOrPrintRateTrace.aopGreenEndorsementFactor.checkIfElementIsDisplayed()) {
				Assertions.addInfo("Scenario 04",
						"Verifying the green endorsement factor value displayed for the AOP in Rate trace page when the Green Endorsement is not added");
				Assertions.verify(
						viewOrPrintRateTrace.aopGreenEndorsementFactorValue.checkIfElementIsPresent()
								&& actualAopGreenEndorsementFactorValue1.equals(aopGreenEndorsementFactorValue1),
						true, "View Or Print Rate Trace Page",
						"The Green Endorsement Factor Value is displayed for AOP in Rate trace page is verified", false,
						false);
				Assertions.passTest("View or Print Rate Trace Page",
						"The AOP Green Endorsement Factor Value without Green Endorsement Added is: "
								+ viewOrPrintRateTrace.aopGreenEndorsementFactorValue.getData());
				Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			}
			// -----IO-21602 Ended-----

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC054 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC054 ", "Executed Successfully");
			}
		}
	}
}