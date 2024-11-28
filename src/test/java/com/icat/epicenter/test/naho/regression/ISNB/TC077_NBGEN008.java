package com.icat.epicenter.test.naho.regression.ISNB;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC077_NBGEN008 extends AbstractNAHOTest {

	public TC077_NBGEN008() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBGEN008.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();

		int dataValue1 = 0;
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
			// waitTime(5); //Control is shifting to roof details link after entering
			// dwelling values instead of clicking on review dwelling
			dwellingPage.reviewDwelling();
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// verifying Cov D value is 20% of Cov A
			NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
			String coverageD = testData.get("L1D1-DwellingCovA");
			int covD = Integer.parseInt(coverageD) * 20 / 100;

			Assertions.verify(dwellingPage.covA.getData(),
					format.format(Integer.valueOf(testData.get("L1D1-DwellingCovA"))).toString().replace(".00", ""),
					"Dwelling Page", "Coverage A value is " + format
							.format(Integer.valueOf(testData.get("L1D1-DwellingCovA"))).toString().replace(".00", ""),
					false, false);

			Assertions.verify(dwellingPage.covD.getData(), format.format(covD).replace(".00", ""), "Dwelling Page",
					"Coverage D value is 20% of Coverage A " + format.format(covD).replace(".00", "") + " is verified",
					false, false);

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC077 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC077 ", "Executed Successfully");
			}
		}
	}

}
