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

public class TC069_NBP001 extends AbstractNAHOTest {

	public TC069_NBP001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBP001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing page objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();

		// Initializing Variables
		String quoteNumber;
		Map<String, String> testData;
		int data_Value1 = 0;
		int data_Value2 = 1;
		testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Creating a new account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			// homePage.createNewAccountWithNamedInsured(testData);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			if (homePage.createNewAccount.checkIfElementIsPresent()
					&& homePage.createNewAccount.checkIfElementIsDisplayed()) {
				homePage.createNewAccount.scrollToElement();
				homePage.createNewAccount.click();
			} else {
				homePage.createNewAccountProducer.waitTillPresenceOfElement(60);
				homePage.createNewAccountProducer.waitTillVisibilityOfElement(60);
				homePage.createNewAccountProducer.moveToElement();
				homePage.createNewAccountProducer.click();
			}
			homePage.namedInsured.waitTillVisibilityOfElement(60);
			homePage.namedInsured.setData(testData.get("InsuredName"));
			Assertions.passTest("Home Page", "Insured Name is " + testData.get("InsuredName"));
			if (homePage.producerNumber.checkIfElementIsPresent()) {
				homePage.producerNumber.setData("1143.1");
			}
			if (homePage.productArrow.checkIfElementIsPresent() && homePage.productArrow.checkIfElementIsDisplayed()) {
				homePage.productArrow.scrollToElement();
				homePage.productArrow.click();
				homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			}
			if (homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(1).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			} else {
				homePage.effectiveDate.formatDynamicPath(2).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(2).waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath(2).setData(testData.get("PolicyEffDate"));
			}
			homePage.goButton.scrollToElement();
			homePage.goButton.click();
			homePage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("New Account", "New Account created successfully");
			homePage.productArrow.moveToElement();

			// Validating the message for wrong producer no for product
			Assertions.verify(homePage.messageForWrongProducerNo.checkIfElementIsDisplayed(), true, "Home Page",
					"Message for selecting wrong product against the Producer No : "
							+ homePage.messageForWrongProducerNo.getData() + " is displayed",
					false, false);

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			homePage.signOutButton.waitTillInVisibilityOfElement(60);

			// Sign in as USM
			// Adding the CR IO-19421
			loginPage.refreshPage();
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Creating new account by entering 30 days future effective date
			testData = data.get(data_Value2);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering zip code
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling Details entered successfully");

			// Entering prior loss Page
			Assertions.verify(priorLossesPage.lossesInThreeYearsNo.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"prior Loss Page Loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			// Entering Create quote page details
			Assertions.verify(createQuotePage.quoteName.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded Successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting quote number from account overview Page
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded Successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(testData.get("QuoteCount")).getData()
					.substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// Asserting the Quote Status
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(1).getData().contains("Active"), true,
					"Account Overview Page", "The Quote Status "
							+ accountOverviewPage.quoteStatus.formatDynamicPath(1).getData() + " displayed is verified",
					false, false);
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC069 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC069 ", "Executed Successfully");
			}
		}
	}
}