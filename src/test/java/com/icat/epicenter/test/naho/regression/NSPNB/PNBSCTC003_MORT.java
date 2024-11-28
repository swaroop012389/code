/** Program Description: Creating Moratorium for SC State,Deleting Moratorium and Verifying Moratorium Warning message Selecting/Deselecting EQ Deductible
 *  Author			   : Pavan
 *  Date of Creation   : 05/20/2021
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

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
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBSCTC003_MORT extends AbstractNAHOTest {

	public PNBSCTC003_MORT() {
		super(LoginType.ADMIN);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/SC003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		LoginPage loginPage = new LoginPage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		MoratoriumPage moratoriumPage = new MoratoriumPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();

		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		String quoteNumber;
		int quoteLen;
		boolean isTestPassed = false;

		try {
			// Creating Moratorium for SC state
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home  Page",
					"Rzimmer Home page loaded successfully", false, false);
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home Page", "Clicked on Admin link successfully");

			// Click on moratorium link
			Assertions.verify(healthDashBoardPage.moratoriumLink.checkIfElementIsDisplayed(), true,
					"HealthDashBoard  Page", "HealthDashBoard Page loaded successfully", false, false);
			healthDashBoardPage.moratoriumLink.scrollToElement();
			healthDashBoardPage.moratoriumLink.click();
			Assertions.passTest("HealthDashBoard  Page ", "Clicked on Moratorium link successfully");

			// Click on moratorium link
			Assertions.verify(moratoriumPage.createMoratoriumLink.checkIfElementIsDisplayed(), true,
					"Moratorium  Page ", "Moratorium Page loaded successfully", false, false);
			moratoriumPage.createMoratoriumLink.scrollToElement();
			moratoriumPage.createMoratoriumLink.click();

			// Entering moratorium details
			String moratoriumZipcode = testData.get("ZipCode");
			Assertions.passTest("Moratorium Page ", "Created Moratorium for the zipcode " + moratoriumZipcode);
			moratoriumPage.enterMoratoriumDetails(testData);
			Assertions.passTest("Moratorium Page ", "Moratorium details entered successfully");

			// Logout as rzimmer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home  Page ", "SignOut as Rzimmer successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as Producer Successfully");

			// Create account as producer
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page ",
					"Home Page loaded successfully", false, false);
			homePage.enterPersonalLoginDetails();
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account ", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page ",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page ", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page ",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page ", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page ",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page ", "Quote details entered successfully");

			// Below code adding because of quote is referring because of modeling service
			// down message
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

				// SignOut as Producer
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
				Assertions.passTest("Home Page", "Searched the Quote " + quoteNumber + " sucessfully");

			}
			// Ended

			// getting Quote number1
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page ", "Quote Number1 is : " + quoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page ", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			Assertions.passTest("Underwriting Questions Page ",
					"Underwriting Questions selected as answer No To AllQuestions");

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Verifying the absence of moratorium warning message when EQ deductible
			// selecting As None
			Assertions.addInfo("Scenario 01",
					"Asserting Moratorium warning message not present in Request bind page when EQ value is None");
			Assertions.verify(
					requestBindPage.moratoriumMsg.checkIfElementIsPresent()
							&& requestBindPage.moratoriumMsg.checkIfElementIsDisplayed(),
					false, "Request Bind Page ", "When EQ Deductible Value selected as "
							+ testData.get("EQDeductibleValue") + " The Moratorium warning message is not displayed",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Request Bind Page ", "Clicked on Go To Home Page button successfully");
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page ",
					"Home Page loaded successfully", false, false);
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page ", "Quote Searched successfully");

			// Creating another Quote
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page ", "Clicked on Create Another Quote successfully");
			testData = data.get(data_Value2);

			// creating quote with eq value
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote page loaded successfully", false, false);
			createQuotePage.earthquakeDeductibleArrow.scrollToElement();
			createQuotePage.earthquakeDeductibleArrow.click();
			createQuotePage.earthquakeDeductibleOptionEQHO.formatDynamicPath(testData.get("EQDeductibleValue"))
					.scrollToElement();
			createQuotePage.earthquakeDeductibleOptionEQHO.formatDynamicPath(testData.get("EQDeductibleValue")).click();
			Assertions.passTest("Create Quote Page ",
					"EQ Deductible Value selected as " + testData.get("EQDeductibleValue"));
			createQuotePage.getAQuote.waitTillPresenceOfElement(60);
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page ", "Clicked on GetQuote button successfully");

			// Below code adding because of quote is referring because of modeling service
			// down message
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

				// SignOut as Producer
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
				Assertions.passTest("Home Page", "Searched the Quote " + quoteNumber + " sucessfully");

			}
			// Ended

			// getting Quote number2
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page ", "Quote Number2 is : " + quoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.requestBind.scrollToElement();
			accountOverviewPage.requestBind.click();
			Assertions.passTest("Account Overview Page ", "Clicked on Request Bind button successfully");

			// Verifying the moratorium warning message when EQ deductible selected
			Assertions.addInfo("Scenario 02",
					"Asserting Moratorium warning message present in Request bind page when EQ value is Selected");
			Assertions.verify(
					requestBindPage.moratoriumMsg.checkIfElementIsPresent()
							&& requestBindPage.moratoriumMsg.checkIfElementIsDisplayed(),
					true, "Request Bind Page ",
					"Moratorium warning message, " + requestBindPage.moratoriumMsg.getData()
							+ " is displayed when EQ Deductible value selected as " + testData.get("EQDeductibleValue"),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Entering bind details
			testData = data.get(data_Value1);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page ",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);

			// asserting moratorium referral message
			Assertions.addInfo("Scenario 03", "Asserting Moratorium warning message in Referral page");
			Assertions.verify(
					requestBindPage.moratoriumReferralMessage.checkIfElementIsPresent()
							&& requestBindPage.moratoriumReferralMessage.checkIfElementIsDisplayed(),
					true, "Request Bind Page ", "Moratorium referral message, "
							+ requestBindPage.moratoriumReferralMessage.getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// SignOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page ", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as USM Successfully");

			// searching the quote number in grid and clicking on the quote number link
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page ", "Searched Submitted Quote " + quoteNumber + " successfullly");

			// Approving Subscription agreement document By USM
			accountOverviewPage.uploadPreBindApproveAsUSM();

			// adding moratorium assertions on rferral page
			Assertions.addInfo("Scenario 04",
					"Asserting Moratorium warning message in Referral Page while approving referral");
			Assertions.verify(
					referralPage.producerQuoteStatus.formatDynamicPath("moratorium").checkIfElementIsDisplayed(), true,
					"Referral Page ",
					"Moratorium referral message, "
							+ referralPage.producerQuoteStatus.formatDynamicPath("moratorium").getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// approving referral
			referralPage.clickOnApprove();

			// adding moratorium assertions on request bind page
			Assertions.addInfo("Scenario 05",
					"Asserting Moratorium warning message in Request Bind Page while approving referral");
			Assertions.verify(requestBindPage.moratoriumWarningMsg.checkIfElementIsDisplayed(), true,
					"Request Bind Page",
					"Moratorium warning message, " + requestBindPage.moratoriumWarningMsg.getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page ", "Referral request approved successfully");

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page ",
					"Policy Summary Page loaded successfully " + "Policy Number "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// SignOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page ", "Logged out as USM successfully");

			// Sign in as Rzimmer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as Rzimmer Successfully");

			// Deleting Moratorium for zipcode
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page ",
					"Rzimmer Home page loaded successfully", false, false);
			testData = data.get(data_Value1);
			moratoriumPage.deleteMoratorium(testData.get("MoratoriumDescription"));
			Assertions.verify(moratoriumPage.deleteLink.checkIfElementIsPresent(), false, "Moratorium Page ",
					"Moratorium for Zipcode " + testData.get("ZipCode") + " deleted successfully", false, false);

			// SignOut as Rzimmer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBSCTC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBSCTC003 ", "Executed Successfully");
			}
		}
	}
}
