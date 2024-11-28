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
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC001_NBQ001 extends AbstractNAHOTest {

	public TC001_NBQ001() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBQ001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		LoginPage loginPage = new LoginPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();

		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue10 = 9;
		testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Creating New Account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home Page loaded successfully", false, false);
			homePage.createNewAccountProducer.moveToElement();
			homePage.createNewAccountProducer.click();
			homePage.productArrow.waitTillVisibilityOfElement(60);
			homePage.productArrow.click();

			// Select product
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection"))
					.waitTillVisibilityOfElement(60);
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).waitTillButtonIsClickable(60);
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			homePage.namedInsured.setData(testData.get("InsuredName"));
			Assertions.passTest("Home Page", "Insured Name is " + testData.get("InsuredName"));

			// Enter effective date
			homePage.effectiveDate.formatDynamicPath(1).scrollToElement();
			homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(60);
			homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			String effectiveDate = testData.get("PolicyEffDate");
			Assertions.passTest("HomePage", "The effective date entered is " + effectiveDate);
			homePage.goButton.scrollToElement();
			homePage.goButton.click();

			// Asserting Ineligible Risk Error Message
			homePage.waitTime(5); // To Move and stay the curser on effective date field wait is given
			homePage.effectiveDateField.scrollToElement();
			homePage.effectiveDateField.click();
			homePage.effectiveDateErrorMsg.waitTillPresenceOfElement(60);
			homePage.effectiveDateErrorMsg.waitTillVisibilityOfElement(60);
			String effectiveDateErrorMsg = homePage.effectiveDateErrorMsg.getData();
			Assertions.verify(homePage.effectiveDateErrorMsg.checkIfElementIsDisplayed(), true, "Home Page",
					"Error Message displayed is " + effectiveDateErrorMsg, false, false);

			testData = data.get(dataValue2);
			String neweffectiveDate = testData.get("PolicyEffDate");
			homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			homePage.effectiveDate.formatDynamicPath(1).tab();
			homePage.goButton.scrollToElement();
			homePage.goButton.click();
			Assertions.passTest("Home Page", "The effective date Entered is " + neweffectiveDate);

			// Entering Zipcode
			testData = data.get(dataValue1);
			Assertions.verify(eligibilityPage.zipCode1.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Zipcode Eligibility Page Loaded Successfully", false, false);
			eligibilityPage.zipCode1.setData(testData.get("ZipCode"));
			Assertions.passTest("Eligibility Page", "Zipcode Entered successfully");
			eligibilityPage.riskAppliedYes.scrollToElement();
			eligibilityPage.riskAppliedYes.click();
			Assertions.passTest("Eligibility Page", "Risk applied questions selected as Yes");
			eligibilityPage.continueButton.waitTillElementisEnabled(60);
			eligibilityPage.continueButton.click();
			eligibilityPage.ineligibleRiskPopup.waitTillPresenceOfElement(60);

			// Asserting the InEligible risk error message
			eligibilityPage.ineligibleRiskPopup.waitTillVisibilityOfElement(60);
			String ineligibleRiskErrorMsg = eligibilityPage.inEligibleRiskErrorMsg.getData();
			Assertions.verify(eligibilityPage.inEligibleRiskErrorMsg.checkIfElementIsDisplayed(), true,
					"Eligibility Page", "The Error Message Displayed is " + ineligibleRiskErrorMsg, false, false);
			eligibilityPage.closeButton.scrollToElement();
			eligibilityPage.closeButton.click();
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page Loaded successfully", false, false);
			eligibilityPage.riskAppliedNo.scrollToElement();
			eligibilityPage.riskAppliedNo.click();
			Assertions.passTest("Eligibility Page", "Risk applied questions selected as No");
			eligibilityPage.continueButton.scrollToElement();
			eligibilityPage.continueButton.click();

			// Verifying the Presence of Dwelling Page
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page Loaded successfully", false, false);

			// Go to HomePage and Signout
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			testData = data.get(dataValue3);
			// Adding code for CR IO-18763
			loginPage.refreshPage();
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login Page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.scrollToBottomPage();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			for (int i = 0; i <= 7; i++) {
				testData = data.get(i);
				createQuotePage.goBack.waitTillVisibilityOfElement(60);
				createQuotePage.goBack.scrollToElement();
				createQuotePage.goBack.click();
				createQuotePage.previous.waitTillVisibilityOfElement(60);
				createQuotePage.previous.scrollToElement();
				createQuotePage.previous.click();

				// Edit dwelling and update Year built and roof cladding
				accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
				accountOverviewPage.editDwelling.scrollToElement();
				accountOverviewPage.editDwelling.click();

				// Entering Location 1 Dwelling 1 Details
				dwellingPage.enterDwellingDetailsNAHO(testData);
				Assertions.passTest("Dwelling Page",
						"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
								+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

				// Entering Create quote page Details
				createQuotePage.scrollToBottomPage();
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();

				// Asserting Alert message for USM
				if (createQuotePage.roofAgeAlertMessage.checkIfElementIsPresent()
						&& createQuotePage.roofAgeAlertMessage.checkIfElementIsDisplayed()) {
					Assertions.verify(createQuotePage.roofAgeAlertMessage.checkIfElementIsDisplayed(), true,
							"Create Quote Page",
							createQuotePage.roofAgeAlertMessage.getData() + " is displayed when Roof Cladding is "
									+ testData.get("L1D1-DwellingRoofCladding") + " and Year Built is "
									+ testData.get("L1D1-DwellingYearBuilt"),
							false, false);
				}
				if (createQuotePage.warningMessages.formatDynamicPath("The account is ineligible due to the roof age")
						.checkIfElementIsPresent()
						&& createQuotePage.warningMessages
								.formatDynamicPath("The account is ineligible due to the roof age")
								.checkIfElementIsDisplayed()) {
					Assertions.verify(
							createQuotePage.warningMessages.formatDynamicPath(
									"The account is ineligible due to the roof age").checkIfElementIsDisplayed(),
							true, "Create Quote Page",
							createQuotePage.warningMessages
									.formatDynamicPath("The account is ineligible due to the roof age").getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
							false, false);
				}

			}

			// Go to HomePage and Signout
			// Added IO-22186
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			testData = data.get(dataValue10);

			loginPage.refreshPage();
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login Page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

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
			dwellingPage.addDwellingDetails(testData, 1, 1);

			// Asserting and Verifying hard stop for address message when address have
			// policy the message is 'According to ICATs records, we already insure the risk
			// at this address. Please contact your underwriting contact at ICAT if you
			// would like to discuss a Broker of Record change or if you have any
			// questions.'
			Assertions.addInfo("Dwelling Page", "Asserting and Verifying hard stop for address");
			Assertions.verify(dwellingPage.addressMsg.getData().equals(
					"According to ICATs records, we already insure the risk at this address. Please contact your underwriting contact at ICAT if you would like to discuss a Broker of Record change or if you have any questions."),
					true, "Dwelling Page",
					"Asserting address hard stop message is " + dwellingPage.addressMsg.getData() + " verified", false,
					false);
			// IO-22186 Ended
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC001 ", "Executed Successfully");
			}
		}
	}
}