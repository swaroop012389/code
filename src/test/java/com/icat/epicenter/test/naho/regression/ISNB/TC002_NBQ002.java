package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC002_NBQ002 extends AbstractNAHOTest {

	public TC002_NBQ002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBQ002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		LoginPage loginPage = new LoginPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing Variables
		Map<String, String> testData;
		int data_Value1 = 0;
		int data_Value2 = 1;
		String quoteNo;
		String quoteNo1;
		String quoteNumber;
		String namedStorm = "2%";
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
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Io-20810
			// Verifying minimum default named storm deductible is 3% as producer
			Assertions.addInfo("Scenario 01", "Verifying minimu default named storm deductible is 3% as producer");
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
					"All other risks in AL, outside of Tri County, will have a minimum Named Storm deductible set to "
							+ createQuotePage.namedStormData.getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Enter Referral Contact Details
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

				// SignOut as Producer
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");

				// Sign in as USM
				loginPage.refreshPage();
				Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
						"Login page loaded successfully", false, false);
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote number
				// link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// homePage.searchReferral(quoteNumber);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber + " successfullly");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// approving referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// SignOut as USM
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as USM successfully");

				// Sign in as Producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as Producer successfully");

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);
			}

			// Getting quote no
			quoteNo = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "The Quote Number 1 is " + quoteNo);

			// Clicking on create another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();

			// Entering Quote details
			testData = data.get(data_Value2);
			Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
			createQuotePage.enterQuoteName(testData.get("QuoteName"));
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting hard stop message when Coverage C limit greater than 70% of
			// Coverage A.
			// Hard Stop message is "Coverage C limit greater than 70% of Coverage A."
			Assertions.addInfo("Scenario 02", "Asserting hard stop message");
			Assertions.verify(
					createQuotePage.globalErr.getData().contains("Coverage C limit greater than 70% of Coverage A."),
					true, "Create Quote Page",
					"The Hard Stop message is " + createQuotePage.globalErr.getData() + " displayed verified", false,
					false);
			Assertions.passTest("Scenario 02 ", "Scenario 02 is Ended");

			// Click on Previous button link
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);

			// Clicking on create another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Click on get a quote button");

			// Enter Referral Contact Details
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

				// SignOut as Producer
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");

				// Sign in as USM
				loginPage.refreshPage();
				Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
						"Login page loaded successfully", false, false);
				loginPage.enterLoginDetails(setUpData.get("Username"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote number
				// link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// homePage.searchReferral(quoteNumber);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber + " successfullly");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// approving referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// SignOut as USM
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as USM successfully");

				// Sign in as Producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as Producer successfully");

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);
			}

			// Click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting quote no
			quoteNo1 = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Refer Quote Page", "The Quote Number 2 is " + quoteNo1);

			// Editing Dwelling details
			testData = data.get(data_Value2);
			accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();

			String locationNumber = testData.get("LocCount");
			int locationCount = Integer.parseInt(locationNumber);

			String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
			int dwellingCount = Integer.parseInt(dwellingNumber);

			if (dwellingPage.totalSquareFootage.checkIfElementIsPresent()
					&& dwellingPage.totalSquareFootage.checkIfElementIsDisplayed()) {
				if (!testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingSqFoot").equals("")) {
					dwellingPage.totalSquareFootage.waitTillVisibilityOfElement(60);
					dwellingPage.totalSquareFootage.scrollToElement();
					dwellingPage.totalSquareFootage
							.appendData(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingSqFoot"));
					dwellingPage.totalSquareFootage.tab();
					dwellingPage.waitTime(3);
					if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()
							&& dwellingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
						dwellingPage.expiredQuotePopUp.scrollToElement();
						dwellingPage.continueWithUpdateBtn.scrollToElement();
						dwellingPage.continueWithUpdateBtn.click();
						dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
					}
				}
			}

			dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);

			Assertions.passTest("Dwelling Page", "Dwelling details edited successfully");

			// Clicking on create quote button
			dwellingPage.createQuote.waitTillPresenceOfElement(60);
			dwellingPage.createQuote.waitTillVisibilityOfElement(60);
			dwellingPage.createQuote.waitTillElementisEnabled(60);
			dwellingPage.createQuote.waitTillButtonIsClickable(60);
			dwellingPage.scrollToBottomPage();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Clicking on Get Quote button
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Enter Refer quote Details
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.waitTillVisibilityOfElement(60);
				referQuotePage.contactName.clearData();
				referQuotePage.contactName.scrollToElement();
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.clearData();
				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));

				// Click on refer quote button
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
			}

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Validating the status of quotes
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(1).getData().contains("Expired"), true,
					"Account Overview Page", "Quote 1 : " + quoteNo + " got expired after editing dwelling details",
					false, false);
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(2).getData().contains("Expired"), true,
					"Account Overview Page", "Quote 2 : " + quoteNo1 + " got expired after editing dwelling details",
					false, false);
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(3).getData().contains("Active"), true,
					"Account Overview Page", "Quote 3 : " + quoteNumber + " is in active state", false, false);

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			homePage.signOutButton.waitTillInVisibilityOfElement(60);
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC002 ", "Executed Successfully");
			}
		}
	}
}