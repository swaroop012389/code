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
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC008_NBRE001 extends AbstractNAHOTest {

	public TC008_NBRE001() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE001.xls";
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
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();

		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;

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

				// searching the quote number in grid and clicking on the quote number link
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

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);

			}

			// getting quote number 1
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// clicking on request bind in Account overview page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Added ticket IO-21373
			// Adding Subscription Agreement document twice
			underwritingQuestionsPage.backButton.click();

			// clicking on request bind in Account overview page
			Assertions.passTest("Quote Document", "Uploading Subscription Agreement Documet Scecond Time");
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
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			while (!bindRequestPage.entityMessage.checkIfElementIsPresent()) {
				if (bindRequestPage.entityMessage.checkIfElementIsPresent()
						&& bindRequestPage.entityMessage.checkIfElementIsDisplayed()) {
					break;
				} else {
					bindRequestPage.refreshPage();
				}
			}

			// verifying referral
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			Assertions.verify(bindRequestPage.entityMessage.checkIfElementIsDisplayed(), true, " Bind Request Page",
					bindRequestPage.entityMessage.getData() + " referring to USM is verified", false, false);

			// sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as Producer Successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);

			// Approving the referral
			accountOverviewPage.openReferral.click();
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();
			requestBindPage.approve.scrollToElement();
			requestBindPage.approve.click();
			Assertions.passTest("Referral Page", "Clicked on Approve button");

			// Clicked on link-Here button
			requestBindPage.clickHereToApprove.waitTillButtonIsClickable(60);
			requestBindPage.clickHereToApprove.scrollToElement();
			requestBindPage.clickHereToApprove.click();
			Assertions.passTest("Request Bind Page", "Clicked on link-Here");
			requestBindPage.waitTime(5);
			requestBindPage.switchToChildWindow();

			Assertions.verify(policyDocumentsPage.addDocumentButton.checkIfElementIsPresent(), true,
					"Policy Dcuments Page", "Policy Dcuments Page  loaded successfully", false, false);

			// Adding IO-21614
			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();
			Assertions.passTest("Policy Document Page", "Clicked on back button successfully");

			// Approving the referral
			accountOverviewPage.openReferral.click();
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();
			requestBindPage.internalComments.waitTillPresenceOfElement(60);
			requestBindPage.internalComments.scrollToElement();
			requestBindPage.internalComments.appendData(testData.get("InternalComments"));
			requestBindPage.externalComments.scrollToElement();
			requestBindPage.externalComments.appendData(testData.get("ExternalComments"));
			requestBindPage.pendingModifications.scrollToElement();
			requestBindPage.pendingModifications.click();

			// Verifying pending modification message
			Assertions.addInfo("Scenario 01", "Verifying pending modification message");
			Assertions.verify(
					requestBindPage.nllWarningMessage
							.formatDynamicPath("The bind request returned to the Producer for modifications.").getData()
							.contains("The bind request returned to the Producer for modifications."),
					true, "Request Bind Page",
					"The Pending modification message is " + requestBindPage.nllWarningMessage
							.formatDynamicPath("The bind request returned to the Producer for modifications.")
							.getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			// IO-21614 Ended

			// IO-22119 Started
			// Click on home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Clicked on home icon link successfully");

			// Creating New Account
			testData = data.get(dataValue2);
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
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
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

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on request bind button");

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();
			Assertions.passTest("Underwriting Questions Page", "Underwriting questions details entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Getting policy number
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is " + policyNumber, false, false);

			// Click on renew policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Go to Home Page
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
			}

			// Asserting renewal quote number
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1,
					(accountOverviewPage.quoteNumber.getData().length() - 1));
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is " + quoteNumber);

			// Quote referring because of year built and year roof last replaced is older
			// 21-25 years and roof cladding = Architectural Shingle
			// Warning message 'Generated by the residential automated renewal job. Reasons
			// for referral: This account is outside underwriting guidelines: The quoted
			// building has a roof age outside of our guidelines. For consideration, please
			// provide your ICAT Online Underwriter with additional information regarding
			// the condition of the roof, such as a recent inspection'
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account overview Page", "Clicked on open referral");

			// Approve the referral
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			Assertions.addInfo("Scenario 02",
					"Asserting and verifying referral message when year built and year roof last replaced is older 21-25 years and roof cladding = Architectural Shingle on renewal");
			Assertions.verify(
					referralPage.producerCommentsProducerSection.getData().contains(
							"The quoted building has a roof age outside of our guidelines"),
					true, "Referral Page",
					"The Referral reason is " + referralPage.producerCommentsProducerSection.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

			// Click on close
			if (referralPage.close.checkIfElementIsPresent() && referralPage.close.checkIfElementIsDisplayed()) {
				referralPage.close.scrollToElement();
				referralPage.close.click();
			}

			// Search for quote number
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote searched successfully");

			// Click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on release renewal to producer button");

			// Edit dwelling and update Year built and roof cladding
			testData = data.get(dataValue3);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();

			// Entering Location 1 Dwelling 1 Details
			dwellingPage.yearBuilt.scrollToElement();
			dwellingPage.yearBuilt.clearData();
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
				dwellingPage.expiredQuotePopUp.scrollToElement();
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
			}
			dwellingPage.yearBuilt.tab();
			dwellingPage.addRoofDetails(testData, 1, 1);
			Assertions.passTest("Dwelling Page",
					"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
							+ "Year roof last replaced updated to " + testData.get("L1D1-DwellingRoofReplacedYear")
							+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

			// Review dwelling and continue
			dwellingPage.waitTime(2);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Entering Create quote page Details
			createQuotePage.scrollToBottomPage();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on get a quote");

			// Asserting and verifying warning message when year built and year roof last
			// replaced is older 26 years and roof cladding = Architectural Shingle
			// Warning message is 'The account is ineligible due to the roof age being
			// outside of ICAT's guidelines. If you have additional information that adjusts
			// the roof age, please email it to your ICAT Online Underwriter.'
			Assertions.addInfo("Scenario 03", "Asserting and verifying warning message");
			Assertions.verify(createQuotePage.warningMessages
					.formatDynamicPath("The account is ineligible due to the roof age").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					createQuotePage.warningMessages.formatDynamicPath("The account is ineligible due to the roof age")
							.getData() + " is displayed when Roof Cladding is "
							+ testData.get("L1D1-DwellingRoofCladding") + " and Year Built is "
							+ testData.get("L1D1-DwellingYearBuilt"),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on continue
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Asserting renewal quote number 2
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1,
					(accountOverviewPage.quoteNumber.getData().length() - 1));
			Assertions.passTest("Account Overview Page", "Renewal Quote Number 2 is " + quoteNumber);

			// Click on view/print rate trace
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();
			Assertions.passTest("Account Overview Page", "Clicked on view or print rate trce");

			// Asserting and verifying roof age factor =1.646 when year built and year roof
			// last replaced is 26 years old and roof cladding =Architectural Shingle
			Assertions.addInfo("Scenario 04", "Asserting and verifying roof age factor = 1.646");
			Assertions.verify(viewOrPrintRateTrace.roofAgeFactorWBPTF.formatDynamicPath("Roof Age Factor", 2).getData(),
					testData.get("RoofAgeFactor"), "View or Print Rate Trace Page",
					"Roof Age Factor Vaule is "
							+ viewOrPrintRateTrace.roofAgeFactorWBPTF.formatDynamicPath("Roof Age Factor", 2).getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();
			Assertions.passTest("View or Print Rate Trace Page", "Clicked on back button");
			// IO-22119 Ended
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC008 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC008 ", "Executed Successfully");
			}
		}
	}
}