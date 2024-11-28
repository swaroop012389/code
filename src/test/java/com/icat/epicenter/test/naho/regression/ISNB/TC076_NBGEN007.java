package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC076_NBGEN007 extends AbstractNAHOTest {

	public TC076_NBGEN007() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBGEN007.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);
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

			// Enter dwelling details
			dwellingPage.addDwellingDetails(testData, locationCount, dwellingCount);
			dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
			dwellingPage.enterProtectionDiscountsDetails(testData, locationCount, dwellingCount);
			dwellingPage.enterDwellingValuesNAHO(testData, locationCount, dwellingCount);
			dwellingPage.reviewDwelling.click();

			if (dwellingPage.pageName.getData().contains("Existing Account Found")) {
				existingAccountPage.OverrideExistingAccount();
			}

			String covC1 = dwellingPage.covC.getData().replace(",", "");
			String covCValue1 = covC1.replace("$", "");
			int covCNum1 = Integer.parseInt(covCValue1);
			String covA1 = dwellingPage.covA.getData().replace(",", "");
			String covAValue1 = covA1.replace("$", "");
			int covANum1 = Integer.parseInt(covAValue1);

			// Assertion to verify coverageC is 50 percent of coverageA
			Assertions.verify(covCNum1, (covANum1 / 100) * 50, "Dwelling Page", "CoverageC is 50% of CoverageA", false,
					false);
			dwellingPage.editDwellingSymbol.click();
			testData = data.get(data_Value2);
			dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
			dwellingPage.OccupiedByArrow.scrollToElement();
			dwellingPage.OccupiedByArrow.click();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).scrollToElement();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).click();

			// Updating dwelling values
			dwellingPage.dwellingValuesLink.waitTillVisibilityOfElement(60);
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();
			dwellingPage.waitTime(2);
			dwellingPage.coverageADwelling.waitTillVisibilityOfElement(60);
			dwellingPage.coverageADwelling.scrollToElement();
			dwellingPage.coverageADwelling.clearData();
			dwellingPage.coverageBOtherStructures.waitTillVisibilityOfElement(60);
			dwellingPage.coverageBOtherStructures.scrollToElement();
			dwellingPage.coverageBOtherStructures.clearData();
			dwellingPage.coverageCPersonalProperty.waitTillVisibilityOfElement(60);
			dwellingPage.coverageCPersonalProperty.scrollToElement();
			dwellingPage.coverageCPersonalProperty.clearData();
			dwellingPage.coverageDFairRental.waitTillVisibilityOfElement(60);
			dwellingPage.coverageDFairRental.scrollToElement();
			dwellingPage.coverageDFairRental.clearData();
			dwellingPage.coverageADwelling.waitTillVisibilityOfElement(60);
			dwellingPage.coverageADwelling.scrollToElement();
			dwellingPage.coverageADwelling.setData(testData.get("L1D1-DwellingCovA"));
			dwellingPage.coverageADwelling.tab();
			dwellingPage.reviewDwelling.click();
			String covC2 = dwellingPage.covC.getData().replace(",", "");
			String covCValue2 = covC2.replace("$", "");
			int covCNum2 = Integer.parseInt(covCValue2);
			String covA2 = dwellingPage.covA.getData().replace(",", "");
			String covAValue2 = covA2.replace("$", "");
			int covANum2 = Integer.parseInt(covAValue2);

			// Assertion to verify coverageC is 10 percent of coverageA
			Assertions.verify(covCNum2, (covANum2 / 100) * 10, "Dwelling Page", "CoverageC is 10% of CoverageA", false,
					false);
			dwellingPage.editDwellingSymbol.click();
			testData = data.get(data_Value3);

			// Updating dwelling values
			dwellingPage.dwellingValuesLink.waitTillVisibilityOfElement(60);
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();
			dwellingPage.coverageCPersonalProperty.waitTillVisibilityOfElement(60);
			dwellingPage.coverageCPersonalProperty.scrollToElement();
			dwellingPage.coverageCPersonalProperty.clearData();
			dwellingPage.coverageCPersonalProperty.setData(testData.get("L1D1-DwellingCovC"));
			dwellingPage.coverageCPersonalProperty.tab();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			if (buildingNoLongerQuotablePage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
			}
			testData = data.get(data_Value1);
			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Assert referral message for coverageC greater than coverageA
			Assertions.verify(
					referQuotePage.covCGreaterReferralMessage.checkIfElementIsPresent()
							&& referQuotePage.covCGreaterReferralMessage.checkIfElementIsDisplayed(),
					true, "Referral Page",
					"Referral message " + referQuotePage.covCGreaterReferralMessage.getData() + " is displayed", false,
					false);

			// Click on Continue
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC076 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC076 ", "Executed Successfully");
			}
		}
	}
}
