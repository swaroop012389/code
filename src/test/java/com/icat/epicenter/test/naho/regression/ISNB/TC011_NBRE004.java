/* Name: John
Description:
Date: 02/04/2020   */
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

public class TC011_NBRE004 extends AbstractNAHOTest {

	public TC011_NBRE004() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE004.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		String quoteNumber;
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
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

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

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click On Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Answer underwriting questions
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Enter Bind Details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			while (!bindRequestSubmittedPage.mortgageReferralMessage.checkIfElementIsPresent()) {
				if (bindRequestSubmittedPage.mortgageReferralMessage.checkIfElementIsPresent()
						&& bindRequestSubmittedPage.mortgageReferralMessage.checkIfElementIsDisplayed()) {
					break;
				} else {
					bindRequestSubmittedPage.refreshPage();
				}
			}

			// Assert referral message for 3 mortgagees
			String mortgageeReferralMessage = bindRequestSubmittedPage.mortgageReferralMessage.getData();
			Assertions.verify(bindRequestSubmittedPage.mortgageReferralMessage.checkIfElementIsDisplayed(), true,
					"Bind Request Submitted Page",
					"Referral	message " + mortgageeReferralMessage + "displayed is verified", false, false);

			// SignOut as Producer
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM successfully");

			// Search Referral
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);

			// approving referral
			accountOverviewPage.uploadPreBindApproveAsUSM();
			referralPage.clickOnApprove();

			// ---Added IO-20798---

			// Clearing the data in the Inspection Contact details section
			if (requestBindPage.inspectionName.checkIfElementIsPresent()
					&& requestBindPage.inspectionName.checkIfElementIsDisplayed()) {
				requestBindPage.inspectionName.scrollToElement();
				requestBindPage.inspectionName.clearData();
				requestBindPage.inspectionAreaCode.clearData();
				requestBindPage.inspectionPrefix.clearData();
				requestBindPage.inspectionNumber.clearData();
			}

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();

			Assertions.addInfo("Scenario 01",
					"Verifying that the Mandatory message is displayed for inspection contact name field when the inspection name is not entered");
			Assertions.verify(
					requestBindPage.inspectionContactNameErrorMsg.checkIfElementIsPresent()
							&& requestBindPage.inspectionContactNameErrorMsg.getData()
									.contains("The Inspection Contact's Name is required"),
					true, "Request Bind Page",
					"Mandatory message is displayed for inspection contact name field when the inspection name is not entered",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			Assertions.addInfo("Scenario 02",
					"Verifying that the Mandatory message is displayed for inspection phone field when the inspection phone is not entered");
			Assertions.verify(
					requestBindPage.inspectionContactPhoneErrorMsg.checkIfElementIsPresent()
							&& requestBindPage.inspectionContactPhoneErrorMsg.getData()
									.contains("Must be a valid, 10 digit, US phone number"),
					true, "Request Bind Page",
					"Mandatory message is displayed for inspection Phone field when the inspection phone is not entered",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Enter Inspection contact details
			if (requestBindPage.inspectionName.checkIfElementIsPresent()
					&& requestBindPage.inspectionName.checkIfElementIsDisplayed()) {
				requestBindPage.inspectionName.scrollToElement();
				requestBindPage.inspectionName.setData(testData.get("InspectionContact"));
				requestBindPage.inspectionAreaCode.setData(testData.get("InspectionAreaCode"));
				requestBindPage.inspectionPrefix.setData(testData.get("InspectionPrefix"));
				requestBindPage.inspectionNumber.setData(testData.get("InspectionNumber"));
			}

			// ----IO-20798 Ended-------

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.select();
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

			// Assert policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy number is: " + policyNumber);

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC011 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC011 ", "Executed Successfully");
			}
		}
	}
}
