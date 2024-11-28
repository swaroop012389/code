/** Program Description: Checking account is ineligible and also not overrideable in create quote page when year built prior to 1978 and occupancy as tenant
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 17/12/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class RITC002 extends AbstractNAHOTest {

	public RITC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/RITC002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();

		// Initializing the variables
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
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
			Assertions.passTest("Dwelling Page", "The Year Built Entered is " + testData.get("L1D1-DwellingYearBuilt"));
			Assertions.passTest("Dwelling Page", "Occupied By Selected is " + testData.get("L1D1-OccupiedBy"));
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// Verifying the hard stop message when year built prior to 1978 and occupancy
			// as
			// tenant
			Assertions.addInfo("Scenario 01",
					"Verifying the hardstop message when year built prior to 1978 and occupancy as tenant");
			Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Hardstop message is verified and displayed as : " + dwellingPage.protectionClassWarMsg.getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("RITC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("RITC002", "Executed Successfully");
			}
		}
	}
}
