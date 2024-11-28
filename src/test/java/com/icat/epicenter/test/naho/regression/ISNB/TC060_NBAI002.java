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

public class TC060_NBAI002 extends AbstractNAHOTest {

	public TC060_NBAI002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBAI002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		LoginPage loginPage = new LoginPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();

		String quoteNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
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
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
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

				// searching the quote number in grid and clicking on the quote number
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

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);

			}

			// clicking on request bind in Account overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "The Quote Number is " + quoteNumber);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);

			// verification of additional interest message in Request bind page
			Assertions.verify(requestBindPage.additionalMortgageeError.checkIfElementIsDisplayed(), true,
					"Request Bind Page", requestBindPage.additionalMortgageeError.getData() + " Message is verified",
					false, false);

			testData = data.get(dataValue2);
			requestBindPage.aIRankArrow1.formatDynamicPath(0).scrollToElement();
			requestBindPage.aIRankArrow1.formatDynamicPath(0).click();
			requestBindPage.aIRankoption1.formatDynamicPath(testData.get("1-AIRank")).waitTillVisibilityOfElement(60);
			requestBindPage.aIRankoption1.formatDynamicPath(testData.get("1-AIRank")).scrollToElement();
			requestBindPage.aIRankoption1.formatDynamicPath(testData.get("1-AIRank")).click();

			// Adding Additional Insured AI
			requestBindPage.aIAddSymbol.scrollToElement();
			requestBindPage.aIAddSymbol.click();
			requestBindPage.aITypeArrow.scrollToElement();
			requestBindPage.aITypeArrow.click();
			requestBindPage.aITypeOption1.formatDynamicPath(testData.get("2-AIType")).waitTillVisibilityOfElement(60);
			requestBindPage.aITypeOption1.formatDynamicPath(testData.get("2-AIType")).scrollToElement();
			requestBindPage.aITypeOption1.formatDynamicPath(testData.get("2-AIType")).click();

			requestBindPage.aIName.setData(testData.get("2-AIName"));
			requestBindPage.aILoanNumber.setData(testData.get("2-AILoanNumber"));
			requestBindPage.aIEnterAddressManuallyLink.scrollToElement();
			requestBindPage.aIEnterAddressManuallyLink.click();
			requestBindPage.aIAddressLine1.setData(testData.get("2-AIAddr1"));
			requestBindPage.aIAddressLine2.setData(testData.get("2-AIAddr2"));
			requestBindPage.aICity.setData(testData.get("2-AICity"));
			requestBindPage.aIState.setData(testData.get("2-AIState"));
			// requestBindPage.aIzipCode.formatDynamicPath(1).setData(testData.get("2-AIZIP"));

			if (requestBindPage.aIzipCode.checkIfElementIsPresent()
					&& requestBindPage.aIzipCode.checkIfElementIsDisplayed()) {
				requestBindPage.aIzipCode.setData(testData.get("2-AIZIP"));
				Assertions.passTest("Request Bind Page",
						"Additional Interest Zipcode  : " + requestBindPage.aIzipCode.formatDynamicPath(1).getData());
			} else if (requestBindPage.aIPostalCode.checkIfElementIsPresent()
					&& requestBindPage.aIPostalCode.checkIfElementIsDisplayed()) {
				requestBindPage.aIPostalCode.setData(testData.get("2-AIZIP"));
				Assertions.passTest("Request Bind Page", "Additional Interest Zipcode  : "
						+ requestBindPage.aIPostalCode.formatDynamicPath(1).getData());
			} else if (requestBindPage.aiZipCodeQ3.formatDynamicPath(1).checkIfElementIsPresent()
					&& requestBindPage.aiZipCodeQ3.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				requestBindPage.aiZipCodeQ3.formatDynamicPath(1).scrollToElement();
				requestBindPage.aiZipCodeQ3.formatDynamicPath(1).setData(testData.get("2-AIZIP"));
				Assertions.passTest("Request Bind Page", "Additional Interest zipcode : "
						+ requestBindPage.aiZipCodeQ3.formatDynamicPath((1)).getData());
			}
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();

			requestBindPage.confirmBindNAHO(testData);

			// Verifying the Relationship drop-down needs to be select
			requestBindPage.waitTime(2);
			String aIRelationShipErrorMsg = requestBindPage.relationshipMandatoryMsg.getData();
			Assertions.verify(requestBindPage.relationshipMandatoryMsg.checkIfElementIsDisplayed(), true,
					"Request Bind Page",
					aIRelationShipErrorMsg + " Relationship Mandatrory message displayed is verified", false, false);
			testData = data.get(dataValue3);
			requestBindPage.aIRelationShipArrow.formatDynamicPath(1).waitTillPresenceOfElement(60);
			requestBindPage.aIRelationShipArrow.formatDynamicPath(1).waitTillVisibilityOfElement(60);
			requestBindPage.aIRelationShipArrow.formatDynamicPath(1).scrollToElement();
			requestBindPage.aIRelationShipArrow.formatDynamicPath(1).click();
			requestBindPage.aIRelationShipOption.formatDynamicPath(testData.get("2-AIRelationship"))
					.waitTillVisibilityOfElement(60);
			requestBindPage.aIRelationShipOption.formatDynamicPath(testData.get("2-AIRelationship")).scrollToElement();
			requestBindPage.aIRelationShipOption.formatDynamicPath(testData.get("2-AIRelationship")).click();
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();

			requestBindPage.confirmBindNAHO(testData);

			Assertions.verify(bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed(), true,
					"Bind Request Submitted Page", "Bind Request Submitted Page loaded successfully", false, false);
			String aiRelationshipMessage = bindRequestSubmittedPage.aiRelationshipMessage.getData();
			Assertions.verify(bindRequestSubmittedPage.aiRelationshipMessage.checkIfElementIsDisplayed(), true,
					"Bind Request Submitted Page",
					"The Referral Message displayed " + aiRelationshipMessage + " is verified", false, false);

			// ----Added IO-21596----

			// Log out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			testData = data.get(dataValue1);

			// Creating a new account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			testData = data.get(dataValue4);

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// clicking on request bind in Account overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "The Quote Number is " + quoteNumber);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering the bind details on the request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);

			/*
			 * //approving the bind request homePage.goToHomepage.scrollToElement();
			 * homePage.goToHomepage.click();
			 *
			 * Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(),
			 * true, "Home Page", "Home page loaded successfully", false, false);
			 * homePage.searchQuote(quoteNumber); Assertions.passTest("Home Page",
			 * "Searched Submitted Quote successfullly");
			 *
			 * accountOverviewPage.openReferral.scrollToElement();
			 * accountOverviewPage.openReferral.click();
			 *
			 * // approving referral Assertions.verify(
			 * referralPage.pickUp.checkIfElementIsPresent() ||
			 * referralPage.approveOrDeclineRequest.checkIfElementIsPresent(), true,
			 * "Referral Page", "Referral page loaded successfully", false, false);
			 * referralPage.clickOnApprove();
			 *
			 * // click on approve in Approve Decline Quote page
			 * approveDeclineQuotePage.clickOnApprove();
			 * Assertions.passTest("Referral Page",
			 * "Referral request approved successfully");
			 *
			 * // verifying referral complete message
			 * Assertions.verify(referralPage.referralCompleteMsg.checkIfElementIsDisplayed(
			 * ), true, "Referral Page", referralPage.referralCompleteMsg.getData() +
			 * " message is verified successfully", false, false);
			 * referralPage.close.scrollToElement(); referralPage.close.click();
			 */

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy is created successfully when TIV is greater than 2.5M. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// ---IO-21596 Ended-----

			// Go to HomePage and Sign out
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC060 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC060 ", "Executed Successfully");
			}
		}
	}
}
