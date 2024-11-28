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

public class TC078_NBGEN009 extends AbstractNAHOTest {

	public TC078_NBGEN009() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBGEN009.xls";
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

			// Asserting Coverage E default value
			Assertions.verify(dwellingPage.coverageEArrow.getData(), "$100,000", "Dwelling Page",
					"CoverageE value is defaulted to " + dwellingPage.coverageEArrow.getData() + " is verified", false,
					false);
			// Click on Review Dwelling
			dwellingPage.reviewDwelling();
			dwellingPage.waitTime(5);
			dwellingPage.editDwellingSymbol.scrollToElement();
			dwellingPage.editDwellingSymbol.click();
			testData = data.get(dataValue2);

			// Changing Occupany type as Tenant

			// dwellingPage.dwellingDetailsLink.scrollToElement();
			// dwellingPage.dwellingDetailsLink.click();
			dwellingPage.waitTime(5);
			dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
			dwellingPage.OccupiedByArrow.scrollToElement();
			dwellingPage.OccupiedByArrow.click();
			dwellingPage.waitTime(5);
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).scrollToElement();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).click();
			Assertions.passTest("Dwelling Page", "Changed the Occupancy Type as Tenant");

			// Entering Cov E value
			dwellingPage.dwellingValuesLink.waitTillVisibilityOfElement(60);
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();
			dwellingPage.waitTime(2);
			dwellingPage.coverageEArrow.waitTillPresenceOfElement(60);
			dwellingPage.coverageEArrow.waitTillVisibilityOfElement(60);
			dwellingPage.coverageEArrow.scrollToElement();
			dwellingPage.coverageEArrow.click();
			dwellingPage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE")).scrollToElement();
			dwellingPage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE")).click();
			Assertions.passTest("Dwelling Page", "The coverage E value $500,000 is selected ");

			dwellingPage.reviewDwelling();
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");
			dwellingPage.createQuote.waitTillVisibilityOfElement(60);
			dwellingPage.createQuote.scrollToElement();
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
			Assertions.failTest("NBTC078 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC078 ", "Executed Successfully");
			}
		}
	}

}
