package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
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

public class TC040_NBRE033 extends AbstractNAHOTest {

	public TC040_NBRE033() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE033.xls";
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
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		String quoteNumber;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
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
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Verifying and Entering details in refer quote page
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
						"Refer Quote page loaded successfully", false, false);

				referQuotePage.contactName.scrollToElement();
				referQuotePage.contactName.setData(testData.get("ProducerName"));

				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));

				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();

				// verifying referral message
				Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true,
						"Referral Page", "Quote " + referQuotePage.quoteNumberforReferral.getData()
								+ " referring to USM " + " is verified",
						false, false);

				quoteNumber = referQuotePage.quoteNumberforReferral.getData();

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

				// searching the quote number in grid and clicking on the quote number
				// link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote successfullly");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// approving referral
				Assertions.verify(
						referralPage.pickUp.checkIfElementIsPresent()
								|| referralPage.approveOrDeclineRequest.checkIfElementIsPresent(),
						true, "Referral Page", "Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// verifying referral complete message
				Assertions.verify(referralPage.referralCompleteMsg.checkIfElementIsDisplayed(), true, "Referral Page",
						referralPage.referralCompleteMsg.getData() + " message is verified successfully", false, false);
				referralPage.close.scrollToElement();
				referralPage.close.click();

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

				// search for the quote number
				homePage.searchQuoteByProducer(quoteNumber);
				Assertions.passTest("Producer Home Page", "Quote Number : " + quoteNumber + " searched successfully");
			}

			// getting quote number 1
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// clicking on request bind in Account overview page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// clear data for name and email fields in contact information
			requestBindPage.contactName.scrollToElement();
			requestBindPage.contactName.clearData();

			requestBindPage.contactEmailAddress.scrollToElement();
			requestBindPage.contactEmailAddress.clearData();

			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();

			confirmBindRequestPage.confirmBind();

			// verifying mandatory fields in Request bind page
			Assertions.verify(requestBindPage.mailingAddressMandatoryMessage.getLocator().contains("error"), true,
					"Request Bind Page", "Mailing Address field is mandatory is verfied", false, false);

			Assertions.verify(requestBindPage.insuredPhoneNoMandatoryMessage.getLocator().contains("error"), true,
					"Request Bind Page", "Insured Phone Number field is mandatory is verfied", false, false);

			Assertions.verify(requestBindPage.insuredEmailMandatoryMessage.getLocator().contains("error"), true,
					"Request Bind Page", "Insured Email field is mandatory is verfied", false, false);

			Assertions.verify(requestBindPage.contactNameMandatoryMsg.getLocator().contains("error"), true,
					"Request Bind Page", "Contact Name field is mandatory is verfied", false, false);

			Assertions.verify(requestBindPage.contactEmailMandatoryMsg.getLocator().contains("error"), true,
					"Request Bind Page", "Contact Email field is mandatory is verfied", false, false);

			Assertions.verify(requestBindPage.inspectionContactNameMandatoryMsg.getLocator().contains("error"), true,
					"Request Bind Page", "Inspection Contact Name field is mandatory is verfied", false, false);

			Assertions.verify(requestBindPage.inspectionContactPhoneMandatoryMsg.getLocator().contains("error"), true,
					"Request Bind Page", "Inspection Contact Phone field is mandatory is verfied", false, false);

			Assertions.verify(requestBindPage.diligenceMandatoryMsg.getLocator().contains("error"), true,
					"Request Bind Page", "Answer to Diligence Question field is mandatory is verfied", false, false);

			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

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

			// searching the quote number in grid and clicking on the quote number
			// link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);

			// approving referral
			accountOverviewPage.uploadPreBindApproveAsUSM();
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			// getting the policy number and verifying mandate fields that are entered in
			// Request Bind page
			// verification of Insured and Inspection details pending
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			Assertions.verify(policySummaryPage.mailingAddress.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Mailing Address displayed is verified", false, false);

			Assertions.verify(policySummaryPage.producerContactName.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Contact Name displayed is verified", false, false);

			Assertions.verify(policySummaryPage.producerContactEmail.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Contact Email displayed is verified", false, false);

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC040 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC040 ", "Executed Successfully");
			}
		}
	}
}
