package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC075_NBGEN006 extends AbstractNAHOTest {

	public TC075_NBGEN006() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBGEN006.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();

		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Dwelling Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);

			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			// waitTime(5); //Control is shifting to roof details link after
			// entering
			// dwelling values instead of clicking on review dwelling
			dwellingPage.reviewDwelling();
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Asserting Cov B is 10% of Cov A
			String covB2 = dwellingPage.covB.getData().replace(",", "");
			String covBValue2 = covB2.replace("$", "");
			int covBNum2 = Integer.parseInt(covBValue2);
			String covA2 = dwellingPage.covA.getData().replace(",", "");
			Assertions.passTest("Dwelling Page", "Cov A value is " + covA2);
			String covAValue2 = covA2.replace("$", "");
			int covANum2 = Integer.parseInt(covAValue2);

			// Assertion to verify coverageB is 10 percent of coverageA
			Assertions.verify(covBNum2, (covANum2 / 100) * 10, "Dwelling Page",
					"Coverage B is 10% of CoverageA is verified ", false, false);

			dwellingPage.editDwellingSymbol.click();
			testData = data.get(dataValue2);

			// Updating dwelling values
			dwellingPage.dwellingValuesLink.waitTillVisibilityOfElement(60);
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();
			dwellingPage.coverageBOtherStructures.waitTillVisibilityOfElement(60);
			dwellingPage.coverageBOtherStructures.scrollToElement();
			dwellingPage.coverageBOtherStructures.clearData();
			dwellingPage.coverageBOtherStructures.setData(testData.get("L1D1-DwellingCovB"));
			dwellingPage.coverageBOtherStructures.tab();
			dwellingPage.createQuote.click();

			testData = data.get(dataValue1);
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

			// Asserting Referral Message
			Assertions.verify(
					referQuotePage.covBGreaterReferralMessage.checkIfElementIsPresent()
							&& referQuotePage.covBGreaterReferralMessage.checkIfElementIsDisplayed(),
					true, "Referral Page", "Referral message " + referQuotePage.covBGreaterReferralMessage.getData()
							+ " displayed is verified ",
					false, false);

			// Enter Referral Contact Details
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();

			// verifying referral message
			Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true, "Referral Page",
					"Quote " + referQuotePage.quoteNumberforReferral.getData() + " referring to USM " + " is verified",
					false, false);

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC075 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC075 ", "Executed Successfully");
			}
		}
	}

}
