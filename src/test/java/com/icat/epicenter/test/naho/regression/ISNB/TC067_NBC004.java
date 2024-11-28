package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BrokerOfRecordPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC067_NBC004 extends AbstractNAHOTest {

	public TC067_NBC004() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBC004.xls";
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
		BrokerOfRecordPage brokerOfRecordPage = new BrokerOfRecordPage();

		// Initializing Variables
		Map<String, String> testData;
		int data_Value1 = 0;
		int data_Value2 = 1;
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

			// Entering Prior Loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			// Entering Quote details
			Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote page loaded successfully", false, false);

			Assertions.verify(createQuotePage.specialLimitsArrow.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Increase Special Limits dropdown is displayed when Cov c value is not zero", false, false);
			testData = data.get(data_Value2);
			createQuotePage.coverageCPersonalProperty.clearData();
			createQuotePage.coverageCPersonalProperty.tab();
			createQuotePage.coverageCPersonalProperty.scrollToElement();
			createQuotePage.coverageCPersonalProperty.setData(testData.get("L1D1-DwellingCovC"));
			createQuotePage.coverageCPersonalProperty.tab();
			Assertions.passTest("Create Quote Page", "Cov C value zero entered successfully");

			Assertions.verify(createQuotePage.specialLimitsArrow.checkIfElementIsPresent(), false, "Create Quote Page",
					"Increase Special Limits dropdown is not displayed when Cov c value is zero", false, false);

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Creating New Account
			// Adding the ticket IO-21810
			testData = data.get(data_Value1);
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
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Asserting Old producer Number
			String oldProducerNumber = accountOverviewPage.producerNumber.getData();
			Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The producer Number Before Updating New Producer is  " + oldProducerNumber
							+ " displayed is verified",
					false, false);

			// Click on edit producer
			accountOverviewPage.producerLink.scrollToElement();
			accountOverviewPage.producerLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit producer link");
			Assertions.verify(brokerOfRecordPage.processBOR.checkIfElementIsDisplayed(), true, "Broker of Record Page",
					"Broker of Record Page loaded successfully", false, false);

			// Change the producer number to 11252.1
			testData = data.get(data_Value2);
			brokerOfRecordPage.newProducerNumber.setData(testData.get("NewProducerNumber"));
			brokerOfRecordPage.borStatusArrow.click();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();

			// Click on Process BOR Button
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();

			// Click on close Button
			brokerOfRecordPage.closeBORPage.scrollToElement();
			brokerOfRecordPage.closeBORPage.click();

			// Assert the updated producer number
			String newProducerNumber = accountOverviewPage.producerNumber.getData();
			Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The producer Number After Updating New Producer is  " + newProducerNumber
							+ " displayed is verified",
					false, false);

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page",
					"Logged in as producer " + setUpData.get("NahoProducer") + " successfully");
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer home page loaded successfully", false, false);

			// Search the bor'd quote
			homePage.producerQuoteSearchButton.scrollToElement();
			homePage.producerQuoteSearchButton.click();
			homePage.producerQuoteNumberSearchTextbox.waitTillVisibilityOfElement(30);
			homePage.producerQuoteNumberSearchTextbox.scrollToElement();
			homePage.producerQuoteNumberSearchTextbox.setData(quoteNumber);
			homePage.producerQuoteFindButton.waitTillVisibilityOfElement(30);
			homePage.producerQuoteFindButton.scrollToElement();
			homePage.producerQuoteFindButton.click();

			// Verifying the Bor'd quote is not present
			Assertions.verify(homePage.producerQuoteNumberLink.formatDynamicPath(quoteNumber).checkIfElementIsPresent(),
					false, "Home Page", "The BOR'd quote number " + quoteNumber + " not displayed is verified", false,
					false);

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM successfully");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Search the quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the quote " + quoteNumber + " successfully");
			Assertions.verify(accountOverviewPage.producerLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click on edit producer
			accountOverviewPage.producerLink.scrollToElement();
			accountOverviewPage.producerLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit producer link");
			Assertions.verify(brokerOfRecordPage.processBOR.checkIfElementIsDisplayed(), true, "Broker of Record Page",
					"Broker of Record Page loaded successfully", false, false);

			// Change the producer number back to 11250.1
			testData = data.get(data_Value1);
			brokerOfRecordPage.newProducerNumber.setData(testData.get("NewProducerNumber"));
			brokerOfRecordPage.borStatusArrow.waitTillVisibilityOfElement(30);
			brokerOfRecordPage.borStatusArrow.scrollToElement();
			brokerOfRecordPage.borStatusArrow.click();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();

			// Click on Process BOR Button
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();
			Assertions.passTest("Broker of Record Page", "Changed the producer number successfully");

			// Click on close Button
			brokerOfRecordPage.closeBORPage.scrollToElement();
			brokerOfRecordPage.closeBORPage.click();

			// Assert the updated producer number
			String updatedProducerNumber = accountOverviewPage.producerNumber.getData();
			Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The changed producer number is " + updatedProducerNumber + " displayed is verified", false, false);

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page",
					"Logged in as producer " + setUpData.get("NahoProducer") + " successfully");
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer home page loaded successfully", false, false);

			// Search the bor'd quote
			homePage.producerQuoteSearchButton.scrollToElement();
			homePage.producerQuoteSearchButton.click();
			homePage.producerQuoteNumberSearchTextbox.waitTillVisibilityOfElement(30);
			homePage.producerQuoteNumberSearchTextbox.scrollToElement();
			homePage.producerQuoteNumberSearchTextbox.setData(quoteNumber);
			homePage.producerQuoteFindButton.waitTillVisibilityOfElement(30);
			homePage.producerQuoteFindButton.scrollToElement();
			homePage.producerQuoteFindButton.click();

			// Verifying the Bor'd quote is not present
			Assertions.verify(
					homePage.producerQuoteNumberLink.formatDynamicPath(quoteNumber).checkIfElementIsDisplayed(), true,
					"Home Page", "The BOR'd quote number " + quoteNumber + " displayed is verified", false, false);

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC067 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC067 ", "Executed Successfully");
			}
		}

	}
}