package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC044_NBQA002 extends AbstractNAHOTest {

	public TC044_NBQA002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBQA002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> Data = data.get(data_Value1);
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

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on View Or print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.address.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "View Or Print Full Quote Page loaded successfully", false, false);

			// Asserting Dwelling Details fromView Or Print Full Quote Page"
			String address = viewOrPrintFullQuotePage.address.getData();
			Assertions.verify(address.contains(testData.get("L1D1-DwellingAddress")), true,
					"View Or Print Full Quote Page", "The Dwelling Address " + address + " displayed is verified",
					false, false);

			String constructionType = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(10).getData();
			Assertions.verify(testData.get("L1D1-DwellingConstType").contains(constructionType), true,
					"View Or Print Full Quote Page",
					"The construction type " + constructionType + " displayed is verified", false, false);

			String hardiePlankSiding = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(38).getData();
			Assertions.verify(testData.get("L1D1-HardiePlankSiding").contains(hardiePlankSiding), true,
					"View Or Print Full Quote Page",
					"The Hardie Plank Siding selected as " + hardiePlankSiding + " displayed is verified", false,
					false);

			String noOfUnits = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(14).getData();
			Assertions.verify(testData.get("L1D1-DwellingUnits").equals(noOfUnits), true,
					"View Or Print Full Quote Page", "The Number of Units " + noOfUnits + " displayed is verified",
					false, false);

			String noOfFloors = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(18).getData();
			Assertions.verify(testData.get("L1D1-DwellingFloors").contains(noOfFloors), true,
					"View Or Print Full Quote Page", "The Number of Stories " + noOfFloors + " displayed is verified",
					false, false);

			String squareFootage = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(6).getData();
			Assertions.verify(testData.get("L1D1-DwellingSqFoot").contains(squareFootage), true,
					"View Or Print Full Quote Page",
					"The Total Square footage " + squareFootage + " displayed is verified", false, false);

			String yearBuilt = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(2).getData();
			Assertions.verify(testData.get("L1D1-DwellingYearBuilt").equals(yearBuilt), true,
					"View Or Print Full Quote Page", "The Year Built " + yearBuilt + " displayed is verified", false,
					false);

			String occupancy = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(34).getData();
			Assertions.verify(testData.get("L1D1-OccupiedBy").equals(occupancy), true, "View Or Print Full Quote Page",
					"The occupancy type " + occupancy + " displayed is verified", false, false);

			String roofShape = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(22).getData();
			Assertions.verify(testData.get("L1D1-DwellingRoofShape").equals(roofShape), true,
					"View Or Print Full Quote Page", "The Roof Shape " + roofShape + " displayed is verified", false,
					false);

			String roofCladding = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(26).getData();
			Assertions.verify(testData.get("L1D1-DwellingRoofCladding").equals(roofCladding), true,
					"View Or Print Full Quote Page", "The Roof Cladding " + roofCladding + " displayed is verified",
					false, false);

			String roofUpdateYear = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(30).getData();
			Assertions.verify(testData.get("L1D1-DwellingRoofReplacedYear").equals(roofUpdateYear), true,
					"View Or Print Full Quote Page",
					"The Roof Updated year " + roofUpdateYear + " displayed is verified", false, false);

			String windMitigation = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(42).getData();
			Assertions.verify(testData.get("L1D1-DwellingWindMitigation").equals(windMitigation), true,
					"View Or Print Full Quote Page", "The Wind Mitigation " + windMitigation + " displayed is verified",
					false, false);

			String alarm = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(4).getData();
			Assertions.verify(testData.get("L1D1-CentralStationAlarm").equals(alarm), true,
					"View Or Print Full Quote Page", "The central station alarm " + alarm + " displayed is verified",
					false, false);

			String distacneToFireStation = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(32).getData();
			Assertions.verify(viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(31).checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page",
					"The distance to fire station " + distacneToFireStation + " displayed is verified", false, false);
			String DistancetoCoast = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(36).getData();
			Assertions.verify(viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(35).checkIfElementIsDisplayed(),
					true, "View Or Print Full Quote Page",
					"The distance to coast " + DistancetoCoast + " displayed is verified", false, false);

			String plumbingUpdateYear = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(8).getData();
			Assertions.verify(testData.get("L1D1-PlumbingUpdatedYear").equals(plumbingUpdateYear), true,
					"View Or Print Full Quote Page",
					"The Plumbing Update Year " + plumbingUpdateYear + " displayed is verified", false, false);

			String heatingUpdateYear = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(12).getData();
			Assertions.verify(testData.get("L1D1-HVACUpdatedYear").equals(heatingUpdateYear), true,
					"View Or Print Full Quote Page",
					"The Heating Update Year " + heatingUpdateYear + " displayed is verified", false, false);

			String electricalUpdateYear = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(16).getData();
			Assertions.verify(testData.get("L1D1-ElectricalUpdatedYear").equals(electricalUpdateYear), true,
					"View Or Print Full Quote Page",
					"The Electrical Update Year " + electricalUpdateYear + " displayed is verified", false, false);

			String lapse = viewOrPrintFullQuotePage.riskCharacters.formatDynamicPath(24).getData();
			Assertions.verify(testData.get("L1D1-LapseInCoverage").equals(lapse), true, "View Or Print Full Quote Page",
					"The Lapse in Coverage selected as " + lapse + " displayed is verified", false, false);

			// Added ticket IO-20972
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// Including EQB/UL values
			createQuotePage.enterOptionalcoverageDetailsNAHO(Data);

			// Click on getQuote
			createQuotePage.getAQuote.checkIfElementIsDisplayed();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Click on Continue
			createQuotePage.continueButton.checkIfElementIsDisplayed();
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Click on Override
			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed())

			{

				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber2 = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number2 is : " + quoteNumber2);

			accountOverviewPage.quoteSpecifics.checkIfElementIsDisplayed();
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();

			// Getting the VIE Participation and Contribution value from the quote specify
			String vieParticipationValue = accountOverviewPage.vieParticipationValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");
			String vieContributionChargeValue = accountOverviewPage.vieContributionChargeValue.getData()
					.replace("$", "").replace(",", "").replace("%", "");

			double d_vieParticipationValue = Double.parseDouble(vieParticipationValue);
			double d_vieContributionChargeValue = Double.parseDouble(vieContributionChargeValue);
			double d_totalVIE = d_vieContributionChargeValue / d_vieParticipationValue;

			// Click on View/Print Rate Trace Link
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();

			// Getting the EQB/Service Line(UL) value from the View/Print trace
			String serviceLinePremium = viewOrPrintRateTrace.serviceLinePremiumValue.getData().replace(".0000", "");
			String eqbPremium = viewOrPrintRateTrace.eqbPremiumValue.getData().replace(".0000", "");
			double d_eqbPremiumValue = Double.parseDouble(eqbPremium);
			double d_serviceLinePremiumValue = Double.parseDouble(serviceLinePremium);

			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();

			// Getting premium value from the account overview page
			accountOverviewPage.premiumValue.scrollToElement();
			String premiumValue = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			double d_premiumValue = Double.parseDouble(premiumValue);
			System.out.println(premiumValue);

			// Getting Surplus Contribution value from the account overview page
			accountOverviewPage.surplusContibutionValue.scrollToElement();
			String surplusContibutionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "");
			double d_surplusContibutionValue = Double.parseDouble(surplusContibutionValue);

			// Calculating Surplus Contribution Value:[(Premium-EQB premium-UL premium)* VIE
			// participation* VIE contribution charge]
			double calSurplusContributionValue = (d_premiumValue - d_eqbPremiumValue - d_serviceLinePremiumValue)
					* d_totalVIE;

			Assertions.addInfo("Scenario 01",
					"Verifying the actual and calculated Surplus Contribution Value on the Account Overview Page");
			if (d_surplusContibutionValue == calSurplusContributionValue) {
				if (surplusContibutionValue != null) {
					Assertions.passTest("Account Overview Page",
							"Actual Surplus Contribution Value : " + d_surplusContibutionValue);
					Assertions.passTest("Account Overview Page",
							"Calculated Surplus Contribution Value : " + (calSurplusContributionValue));
				}
			} else {
				Assertions.verify(calSurplusContributionValue, d_surplusContibutionValue, "Account Overview Page",
						"The Difference observed between actual and calculated Surplus Contribution Value", false,
						false);
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// ------ IO-20972 Ended-----

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC044 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC044 ", "Executed Successfully");
			}
		}
	}

}
