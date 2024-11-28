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
import com.icat.epicenter.pom.FindUserDomain;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.ManageOfficeUsersPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC005_NBRQ002 extends AbstractNAHOTest {

	public TC005_NBRQ002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRQ002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ManageOfficeUsersPage manageOfficeUsersPage = new ManageOfficeUsersPage();
		FindUserDomain findUserDomain = new FindUserDomain();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		String quoteNumber2;
		String quoteNumber1;
		int dataValue1 = 0;
		int dataValue2 = 1;
		String quoteName;
		int quoteLen;
		String quoteNumber;

		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
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

			// Asserting hard stop message AL state for producer login when YOC less than
			// 1980, The hard stop
			// message is 'Due to a year built prior to 1980 this risk is ineligible for
			// coverage. If the home has undergone a gut rehab and supporting documentation
			// can be provided, we will reconsider offering terms. Please reach out to your
			// assigned UW.'
			Assertions.addInfo("Scenario 01",
					"Asserting hard stop message when yoc is less than 1980, for prodcuer login");
			Assertions.verify(
					dwellingPage.dwellingDetailsErrorMessages
							.formatDynamicPath(
									"Due to a year built prior to 1980 this risk is ineligible for coverage.")
							.getData()
							.contains("Due to a year built prior to 1980 this risk is ineligible for coverage."),
					true, "Dwelling page",
					"The hard stop message, "
							+ dwellingPage.dwellingDetailsErrorMessages
									.formatDynamicPath(
											"Due to a year built prior to 1980 this risk is ineligible for coverage.")
									.getData()
							+ " is displayed",
					false, false);
			Assertions.passTest("Scenario 01", "Scenario 01 Ended");

			// Updating year built 1979 to 2010
			testData = data.get(dataValue2);
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			dwellingPage.yearBuilt.appendData(testData.get("L1D1-DwellingYearBuilt"));
			dwellingPage.yearBuilt.tab();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			testData = data.get(dataValue1);
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.quoteName.setData(testData.get("QuoteName"));
			Assertions.passTest("Create Quote Page", "Quote name Entered is " + testData.get("QuoteName"));
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page ", "The Quote Number is " + quoteNumber);

				// SignOut as Producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");

				// Sign in as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote
				// number
				// link
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber + " successfullly");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// approving referral
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// SignOut as USM
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as USM successfully");

				// Sign in as Producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as Producer successfully");

				homePage.enterPersonalLoginDetails();
				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);

			}

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber1 = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number1 is : " + quoteNumber1);

			// Creating Another Quote with optional name
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on create another quote");
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Entering Create quote page Details
			testData = data.get(dataValue2);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.quoteName.setData(testData.get("QuoteName"));
			Assertions.passTest("Create Quote Page", "Quote name Entered is " + testData.get("QuoteName"));
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			testData = data.get(dataValue1);
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page ", "The Quote Number is " + quoteNumber);

				// SignOut as Producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");

				// Sign in as USM
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote
				// number
				// link
				homePage.searchReferral(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

				// approving referral
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// SignOut as USM
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as USM successfully");

				// Sign in as Producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as Producer successfully");

				homePage.enterPersonalLoginDetails();
				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);
				Assertions.passTest("Home Page", "Referred quote searched successfully");

			}

			// Asserting quote number
			testData = data.get(dataValue1);
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber2 = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number2 is : " + quoteNumber2);

			// sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as Producer Successfully");

			loginPage.refreshPage();

			// Login to USM account
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// searching the quote number in grid and clicking on the quote number link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// search for the quote number
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("USM Home Page", "Quote Number : " + quoteNumber2 + " searched successfully");

			// Verifying Optional name is displayed or not
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteName = accountOverviewPage.quoteName.formatDynamicPath(2).getData().substring(10, 26);
			Assertions.addInfo("Scenario 02", "Verifying the presence of quote name on UI");
			Assertions.passTest("Account Overview Page", "The Quote Name displayed on theUI is " + quoteName);
			Assertions.passTest("Scenario 02", "Scenario 02 Ended");

			// Checking presence of quote1 and quote2 total premium values
			Assertions.addInfo("Scenario 03", "Checking the presence of quote1 and quote2 total premium values");
			accountOverviewPage.quoteLink.formatDynamicPath(1).scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath(1).click();
			String Quote1PremiumValue = accountOverviewPage.totalPremiumValue.getData();
			Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The Total premium value displayed for QUOTE-1 	is " + Quote1PremiumValue, false, false);
			accountOverviewPage.quoteLink.formatDynamicPath(2).scrollToElement();
			accountOverviewPage.quoteLink.formatDynamicPath(2).click();
			String Quote2PremiumValue = accountOverviewPage.totalPremiumValue.getData();
			Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The Total premium value displayed for QUOTE-2 	is " + Quote2PremiumValue, false, false);
			Assertions.passTest("Scenario 03", "Scenario 03 Ended");

			// Verifying Aop,Ns deductible and Ho5 presence in the parenthesis
			Assertions.addInfo("Scenario 04", "Verifying the presence of AOP,NS Deductible and HO5 parenthesis");
			Assertions.verify(accountOverviewPage.quoteLink.formatDynamicPath(2).getData().contains("AOP"), true,
					"Account Overview Page", "AOP is displayed in the parenthesis", false, false);
			Assertions.verify(accountOverviewPage.quoteLink.formatDynamicPath(2).getData().contains("NS"), true,
					"Account Overview Page", "NS is displayed in the parenthesis", false, false);
			Assertions.verify(accountOverviewPage.quoteLink.formatDynamicPath(2).getData().contains("HO5"), true,
					"Account Overview Page", "HO5 is displayed in the parenthesis", false, false);
			Assertions.passTest("Scenario 04", "Scenario 04 Ended");

			// Delete the new Quote Created
			accountOverviewPage.deleteQuote.scrollToElement();
			accountOverviewPage.deleteQuote.click();
			accountOverviewPage.yesDeletePopup.scrollToElement();
			accountOverviewPage.yesDeletePopup.click();
			Assertions.passTest("Account Overview Page", "Deleted the Quote " + quoteNumber2);

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			Assertions.passTest("Login Page", "Logged Out as USM Successfully");

			loginPage.refreshPage();

			// login as producer
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Login Page",
					"Logged in as Producer " + setUpData.get("NahoProducer") + " Successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Searching the Quote 1
			homePage.searchQuoteByProducer(quoteNumber1);

			// Searching the deleted quote
			Assertions.addInfo("Scenario 05", "Searching the deleted quote");
			Assertions.verify(
					accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber2, 2).checkIfElementIsPresent(), false,
					"Account Overview Page", "Producer cannot view the deleted quote " + quoteNumber2 + " is Verified",
					false, false);
			Assertions.passTest("Scenario 05", "Scenario 05 Ended");

			// Adding below code for IO-21882
			// Click on home button
			testData = data.get(dataValue1);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on manage office user link
			homePage.manageOfficeUsers.scrollToElement();
			homePage.manageOfficeUsers.click();
			Assertions.passTest("Home Page", "Clicked on manage office user link successfully");

			// Click on new user
			manageOfficeUsersPage.inviteNewUser.scrollToElement();
			manageOfficeUsersPage.inviteNewUser.click();
			Assertions.passTest("Manage Office User Page", "Clicked on new user");

			// Enter new user details
			Assertions.verify(manageOfficeUsersPage.sendInvite.checkIfElementIsDisplayed(), true,
					"Manage Office User Page", "Manage office user page loaded successfully", false, false);
			manageOfficeUsersPage.enterInviteNewUser(testData);
			Assertions.passTest("Manage Office Users Page", "New inviter added successfully");

			// Asserting invite new user message is'Thank you for inviting karthik sanga to
			// create their ICAT Online credentials. An email has been sent to the new user
			// with the sign up invite.'
			manageOfficeUsersPage.globalMessage.waitTillVisibilityOfElement(60);
			manageOfficeUsersPage.globalMessage.checkIfElementIsDisplayed();
			Assertions.addInfo("Scenario 06", "Asserting invite new user message");
			Assertions.verify(
					manageOfficeUsersPage.globalMessage.getData()
							.contains("An email has been sent to the new user with the sign up invite"),
					true, "Manage Office User Page",
					"Invite new user message is " + manageOfficeUsersPage.globalMessage.getData() + " displayed", false,
					false);
			Assertions.addInfo("Scenario 06 ", "Scenario 06 Ended");

			// sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as Producer Successfully");
			loginPage.refreshPage();

			// Login to USM account
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Click on user domain
			homePage.userDomainMgnt.scrollToElement();
			homePage.userDomainMgnt.click();
			Assertions.passTest("Home Page", "Clicked on user domain management link successfully");

			// Search for new user using producer number
			findUserDomain.producerNumber.scrollToElement();
			findUserDomain.producerNumber.appendData(testData.get("ProducerNumber"));
			findUserDomain.findButton.scrollToElement();
			findUserDomain.findButton.click();

			// Click on new user
			findUserDomain.emailAddress.scrollToElement();
			findUserDomain.emailAddress.click();
			Assertions.passTest("Find User Domain Page", "New user find successfully");

			// Update user name
			testData = data.get(dataValue2);
			findUserDomain.userFirstName.scrollToElement();
			findUserDomain.userFirstName.clearData();
			findUserDomain.userFirstName.appendData(testData.get("InviteeFirstName"));
			findUserDomain.saveUserButton.scrollToElement();
			findUserDomain.saveUserButton.click();

			// Asserting user update message is 'user updated'
			Assertions.addInfo("Scenario 07", "Asserting user update message");
			manageOfficeUsersPage.globalMessage.waitTillPresenceOfElement(60);
			Assertions.verify(manageOfficeUsersPage.globalMessage.getData().contains("User Updated"), true,
					"Manage Office User Page", "The User first name updated successfully, the updated message is "
							+ manageOfficeUsersPage.globalMessage.getData(),
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as USM Successfully");

			// login as producer
			loginPage.refreshPage();
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Login Page",
					"Logged in as Producer " + setUpData.get("NahoProducer") + " Successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			// Click on manage office user link
			homePage.manageOfficeUsers.scrollToElement();
			homePage.manageOfficeUsers.click();

			// Cancel the new invite user
			manageOfficeUsersPage.cancelReInvite.scrollToElement();
			manageOfficeUsersPage.cancelReInvite.click();
			manageOfficeUsersPage.confirmButton.scrollToElement();
			manageOfficeUsersPage.confirmButton.click();
			Assertions.passTest("Manage Office User Page", "Clicked on cancel button successfully");

			// Asserting new user cancel message is 'The invite has been canceled for
			// kartik.sanga@marsh.com.'
			Assertions.addInfo("Scenario 08", "Asserting new user cancel message");
			Assertions.verify(
					manageOfficeUsersPage.globalMessage.getData()
							.contains("The invite has been canceled for kartik.sanga@marsh.com."),
					true, "Manage Office User Page", "The new user canceled successfully,the canceled message is "
							+ manageOfficeUsersPage.globalMessage.getData(),
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Go to HomePage and Sign out
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC005 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC005 ", "Executed Successfully");
			}
		}
	}
}