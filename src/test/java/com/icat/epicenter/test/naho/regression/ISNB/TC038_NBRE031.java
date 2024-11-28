package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC038_NBRE031 extends AbstractNAHOTest {

	public TC038_NBRE031() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE031.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Create New Account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("Home Page", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			if (!testData.get("ZipCode").equals("")) {
				eligibilityPage.zipCode1.setData(testData.get("ZipCode"));
				eligibilityPage.riskAppliedYes.waitTillElementisEnabled(60);
				Assertions.passTest("Eligibility", "Zip code is " + eligibilityPage.zipCode1.getData());

				if (eligibilityPage.riskAppliedYes.checkIfElementIsPresent()
						&& eligibilityPage.riskAppliedYes.checkIfElementIsDisplayed()) {
					if (testData.get("RiskApplicable").equalsIgnoreCase("yes")) {
						eligibilityPage.riskAppliedYes.scrollToElement();
						eligibilityPage.riskAppliedYes.click();
					} else {
						eligibilityPage.riskAppliedNo.scrollToElement();
						eligibilityPage.riskAppliedNo.click();
					}
				}
				eligibilityPage.continueButton.waitTillElementisEnabled(60);
				eligibilityPage.continueButton.click();
			}

			// Validating the ineligibility message
			eligibilityPage.inEligibleRiskErrorMsg.waitTillVisibilityOfElement(60);
			eligibilityPage.inEligibleRiskErrorMsg.scrollToElement();
			Assertions
					.verify(eligibilityPage.inEligibleRiskErrorMsg.checkIfElementIsDisplayed(), true,
							"Eligibility Page", "Ineligible Risk Message : '"
									+ eligibilityPage.inEligibleRiskErrorMsg.getData() + "' is displayed is verified",
							false, false);

			// Clicking on close button
			eligibilityPage.closeButton.scrollToElement();
			eligibilityPage.closeButton.click();

			// SignOut and Close the Browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC038 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC038 ", "Executed Successfully");
			}
		}
	}
}