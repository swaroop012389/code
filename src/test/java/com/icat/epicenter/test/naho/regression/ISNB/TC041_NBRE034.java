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
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.MoratoriumPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC041_NBRE034 extends AbstractNAHOTest {

	public TC041_NBRE034() {
		super(LoginType.ADMIN);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE034.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing the Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		MoratoriumPage moratoriumPage = new MoratoriumPage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Creating Moratorium for zipcode
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Rzimmer Home page loaded successfully", false, false);
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();

			// Click on Moratorium Admin Link
			Assertions.verify(healthDashBoardPage.moratoriumLink.checkIfElementIsDisplayed(), true,
					"Health Dashboard Page", "Health Dashboard Page Loaded successfully", false, false);
			healthDashBoardPage.moratoriumLink.scrollToElement();
			healthDashBoardPage.moratoriumLink.click();

			// Adding Moratorium for a zipcode
			Assertions.verify(moratoriumPage.createMoratoriumLink.checkIfElementIsDisplayed(), true, "Moratorium Page",
					"Moratorium Page Loaded successfully", false, false);
			moratoriumPage.createMoratoriumLink.scrollToElement();
			moratoriumPage.createMoratoriumLink.click();
			String moratoriumZipcode = testData.get("InsuredZIP");

			moratoriumPage.enterMoratoriumDetails(testData);
			Assertions.passTest("Moratorium Page", "Created Moratorium for the zipcode " + moratoriumZipcode);

			// Sign out as Rzimmer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as Rzimmer successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

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
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Enter Referral Contact Details
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();

			// verifying referral message
			Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true, "Referral Page",
					"Quote " + referQuotePage.quoteNumberforReferral.getData() + " referring to USM " + " is verified",
					false, false);
			String quoteNumber = referQuotePage.quoteNumberforReferral.getData();
			Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

			// Sign in out as producer
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as Producer successfully");

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

			// Sign out as USM
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Assert account overview page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Producer Home Page", "Quote Number : " + quoteNumber + " searched successfully");

			// Click On Bind
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			underwritingQuestionsPage.answerNoToAllQuestions.click();
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Asserting Moratorium Warning Message
			String moratoriumMsg = requestBindPage.moratoriumMsg.getData();
			Assertions.verify(requestBindPage.moratoriumMsg.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"The Moratorium warning message is " + moratoriumMsg + " is verified", false, false);

			// Sign in out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as Producer successfully");

			// Sign in as Rzimmer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Rzimmer successfully");

			// Deleting Moratorium for zipcode
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Rzimmer Home page loaded successfully", false, false);

			moratoriumPage.deleteMoratorium(testData.get("MoratoriumDescription"));

			Assertions.verify(moratoriumPage.moratoriumDeleteMsg.checkIfElementIsDisplayed(), true, "Moratorium Page",
					"Moratorium added for Zipcode " + testData.get("ZipCode") + " deleted successfully", false, false);

			// Sign out as Rzimmer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as Rzimmer successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Search for the quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Producer Home Page", "Quote Number : " + quoteNumber + " searched successfully");

			// click on RequestBind
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.requestBind.scrollToElement();
			accountOverviewPage.requestBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Verifying the absence of moratorium warning message after removal of
			// zipcode from moratorium
			requestBindPage.waitTime(5);
			Assertions.verify(requestBindPage.moratoriumMsg.checkIfElementIsPresent(), false, "Request Bind Page",
					"Moratorium Warning Message " + moratoriumMsg + " not present is verified", false, false);

			// SignOut as producer and close the Browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC041 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC041 ", "Executed Successfully");
			}
		}
	}

}
