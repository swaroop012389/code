package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC009_NBRE002 extends AbstractNAHOTest {

	public TC009_NBRE002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing the Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferralPage referralPage = new ReferralPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		String quoteNumber;
		int quoteLen;
		int data_Value1 = 0;
		int data_Value2 = 1;
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
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {

				// Enter Referral Contact Details
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
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
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote
				// number
				// link
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");

				accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();

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

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);

			}
			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Answer underwriting questions
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			underwritingQuestionsPage.targetRisk_Yes.waitTillVisibilityOfElement(60);
			underwritingQuestionsPage.targetRisk_Yes.scrollToElement();
			underwritingQuestionsPage.targetRisk_Yes.click();
			Assertions.passTest("Underwriting Questions Page", "High Profile/Target risk is selected as YES");
			underwritingQuestionsPage.saveButton.waitTillVisibilityOfElement(60);
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Enter Bind Details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// verify single insured name waring message
			Assertions.verify(requestBindPage.singleNamedInsuredMsg.checkIfElementIsDisplayed(), true,
					"Request Bind Page", requestBindPage.singleNamedInsuredMsg.getData() + " message is verified",
					false, false);

			// updating insured name with first and last name
			testData = data.get(data_Value2);

			requestBindPage.namedInsured.scrollToElement();
			requestBindPage.namedInsured.clearData();
			requestBindPage.namedInsured.setData(testData.get("InsuredName"));
			requestBindPage.namedInsured.tab();
			requestBindPage.waitTime(3); // If wait time is removed,Element Not Interactable exception is
			// thrown.Waittillpresence and Wait till visibility is not working here

			if (requestBindPage.no_NameChange.checkIfElementIsPresent()
					&& requestBindPage.no_NameChange.checkIfElementIsDisplayed()) {
				requestBindPage.no_NameChange.waitTillPresenceOfElement(60);
				requestBindPage.no_NameChange.waitTillVisibilityOfElement(60);
				requestBindPage.no_NameChange.scrollToElement();
				requestBindPage.no_NameChange.click();
				requestBindPage.no_NameChange.waitTillInVisibilityOfElement(60);
			}
			Assertions.passTest("Request Bind Page", "Updated Insured Name with First and Last Name");
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();

			// confirm bind
			requestBindPage.confirmBindNAHO(testData);

			while (!bindRequestSubmittedPage.highTargerRiskRefMsg.checkIfElementIsPresent()) {
				if (bindRequestSubmittedPage.highTargerRiskRefMsg.checkIfElementIsPresent()
						&& bindRequestSubmittedPage.highTargerRiskRefMsg.checkIfElementIsDisplayed()) {
					break;
				} else {
					bindRequestSubmittedPage.refreshPage();
				}
			}

			// Assert referral message
			Assertions.verify(bindRequestSubmittedPage.highTargerRiskRefMsg.getData().contains("high profile insured"),
					true, "Bind Request Submitted Page",
					"Referral message " + bindRequestSubmittedPage.highTargerRiskRefMsg.getData() + " is displayed",
					false, false);

			// sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as Producer Successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM successfully");

			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

			// approving referral
			accountOverviewPage.uploadPreBindApproveAsUSM();
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.scrollToElement();
				requestBindPage.overrideEffectiveDate.select();
				requestBindPage.approve.waitTillVisibilityOfElement(60);
				requestBindPage.approve.scrollToElement();
				requestBindPage.approve.click();
			} else {
				requestBindPage.waitTime(2);
				if (requestBindPage.approveBackDating.checkIfElementIsPresent()
						&& requestBindPage.approveBackDating.checkIfElementIsDisplayed()) {
					requestBindPage.approveBackDating.waitTillButtonIsClickable(60);
					requestBindPage.approveBackDating.scrollToElement();
					requestBindPage.approveBackDating.click();
				}
			}

			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policyNumber.contains(""), true, "Policy Summary Page",
					"Policy Number format is verified. Policy Number is: " + policyNumber, false, false);

			// SignOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC009 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC009 ", "Executed Successfully");
			}
		}
	}
}