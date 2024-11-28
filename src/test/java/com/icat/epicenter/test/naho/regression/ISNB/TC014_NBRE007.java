package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC014_NBRE007 extends AbstractNAHOTest {

	public TC014_NBRE007() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE007.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();

		// Initializing Variables
		Map<String, String> testData;
		int data_Value1 = 0;
		testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Creating a new account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
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

			// Asserting Hard Stop message when No of units more than 3 as producer,The hard
			// stop message is "Due to the number of units, this account is ineligible"
			Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
					" Hard Stop Message for No of Units : " + dwellingPage.protectionClassWarMsg.getData()
							+ " is displayed",
					false, false);

			// Added IO-21040
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.scrollToBottomPage();

			homePage.producerRenewalSearchButton.scrollToElement();
			homePage.producerRenewalSearchButton.click();
			homePage.producerRenewalNameSearchTextbox.scrollToElement();

			String insuredText = homePage.producerRenewalNameSearchTextbox.getAttrributeValue("placeholder");
			System.out.println("Insured text is " + insuredText);

			Assertions.addInfo("Scenario 01",
					"Verifying that the Named Insured box displays the 'Named Insured' text on the Renewal find section of producer homepage");
			Assertions.verify(
					homePage.producerRenewalNameSearchTextbox.checkIfElementIsPresent()
							&& homePage.producerRenewalNameSearchTextbox.getAttrributeValue("placeholder")
									.equals("Named Insured"),
					true, "Home Page",
					"Named Insured text is displayed and verified on the Renewal find section of producer homepage",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			homePage.producerPolicySearchButton.scrollToElement();
			homePage.producerPolicySearchButton.click();
			homePage.producerPolicyNameSearchTextbox.scrollToElement();

			Assertions.addInfo("Scenario 02",
					"Verifying that the Named Insured box displays the 'Named Insured' text on the Policy find section of producer homepage");
			Assertions.verify(
					homePage.producerPolicyNameSearchTextbox.checkIfElementIsPresent()
							&& homePage.producerPolicyNameSearchTextbox.getAttrributeValue("placeholder")
									.equals("Named Insured"),
					true, "Home Page",
					"Named Insured text is displayed and verified on the Policy find section of producer homepage",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			homePage.producerBinderSearchButton.scrollToElement();
			homePage.producerBinderSearchButton.click();
			homePage.producerBinderNameSearchTextbox.scrollToElement();

			Assertions.addInfo("Scenario 03",
					"Verifying that the Named Insured box displays the 'Named Insured' text on the Binder find section of producer homepage");
			Assertions.verify(
					homePage.producerBinderNameSearchTextbox.checkIfElementIsPresent()
							&& homePage.producerBinderNameSearchTextbox.getAttrributeValue("placeholder")
									.equals("Named Insured"),
					true, "Home Page",
					"Named Insured text is displayed and verified on the Binder find section of producer homepage",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			homePage.producerQuoteSearchButton.scrollToElement();
			homePage.producerQuoteSearchButton.click();
			homePage.producerQuoteNameSearchTextbox.scrollToElement();

			Assertions.addInfo("Scenario 04",
					"Verifying that the Named Insured box displays the 'Named Insured' text on the Quote find section of producer homepage");
			Assertions.verify(
					homePage.producerQuoteNameSearchTextbox.checkIfElementIsPresent()
							&& homePage.producerQuoteNameSearchTextbox.getAttrributeValue("placeholder")
									.equals("Named Insured"),
					true, "Home Page",
					"Named Insured text is displayed and verified on the Quote find section of producer homepage",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			homePage.producerBuildingSearchButton.scrollToElement();
			homePage.producerBuildingSearchButton.click();
			homePage.producerBuildingNameSearchTextbox.scrollToElement();

			Assertions.addInfo("Scenario 05",
					"Verifying that the Named Insured box displays the 'Named Insured' text on the Building find section of producer homepage");
			Assertions.verify(
					homePage.producerBuildingNameSearchTextbox.checkIfElementIsPresent()
							&& homePage.producerBuildingNameSearchTextbox.getAttrributeValue("placeholder")
									.equals("Named Insured"),
					true, "Home Page",
					"Named Insured text is displayed and verified on the Building find section of producer homepage",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			homePage.producerAccountSearchButton.scrollToElement();
			homePage.producerAccountSearchButton.click();
			homePage.producerAccountNameSearchTextbox.scrollToElement();

			Assertions.addInfo("Scenario 06",
					"Verifying that the Named Insured box displays the 'Named Insured' text on the Account find section of producer homepage");
			Assertions.verify(
					homePage.producerAccountNameSearchTextbox.checkIfElementIsPresent()
							&& homePage.producerAccountNameSearchTextbox.getAttrributeValue("placeholder")
									.equals("Named Insured"),
					true, "Home Page",
					"Named Insured text is displayed and verified on the Account find section of producer homepage",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// ------ IO-21040 Ended-----

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC014 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC014 ", "Executed Successfully");
			}
		}
	}
}