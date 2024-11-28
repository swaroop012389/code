package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BrokerOfRecordPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EmailPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ProducerLetter;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC074_PNBREN008 extends AbstractNAHOTest {

	public TC074_PNBREN008() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBREN008.xls";
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
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		BrokerOfRecordPage brokerOfRecordPage = new BrokerOfRecordPage();
		ProducerLetter producerLetter = new ProducerLetter();
		EmailPage emailPage = new EmailPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
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

			// getting quote number
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
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Bind Referral
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Referral page
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			requestBindPage.approveRequestNAHO(testData);

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Go to home page and search the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home Page loaded successfully");

			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy " + policyNumber + " successfully");

			// Click on Renew policy
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link successfully");

			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				Assertions.verify(policySummaryPage.expaacMessage.checkIfElementIsDisplayed(), true,
						"Policy Renewal Page", policySummaryPage.expaacMessage.getData() + " Message verified", false,
						false);

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// clicking on expaac link in home page
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				Assertions.passTest("Home Page", "Clicked on Expaac Link");

				// entering expaac data
				Assertions.verify(expaccInfoPage.policyNumber.checkIfElementIsDisplayed(), true, "Expacc Info Page",
						"Update Expaac Data page loaded successfully", false, false);
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc Info Page", "Expaac Data updated successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// Find the policy by entering policy Number
				homePage.searchPolicy(policyNumber);
				Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

				// Click on Renew Policy Hyperlink
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

				// click on continue button in Renewal building review page
				if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
						&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
					policyRenewalPage.continueRenewal.scrollToElement();
					policyRenewalPage.continueRenewal.click();
					Assertions.passTest("Renewal Building Review Page", "Clicked on Continue");
				}
			}

			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Getting Renewal Quote Number
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + renewalQuoteNumber);

			// Approving referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.approveRenewalReferralUSM(renewalQuoteNumber);
			}

			// click on release to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer successfully");

			// Asserting Old producer Number
			String oldProducerNumber = accountOverviewPage.producerNumber.getData();
			Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The producer Number Before Updating New Producer is  " + oldProducerNumber
							+ " displayed is verified",
					false, false);

			// Click on edit producer
			accountOverviewPage.producerLink.scrollToElement();
			accountOverviewPage.producerLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Producer Number successfully");

			Assertions.verify(brokerOfRecordPage.processBOR.checkIfElementIsDisplayed(), true, "Broker of Record Page",
					"Broker of Record Page loaded successfully", false, false);

			brokerOfRecordPage.newProducerNumber.setData(testData.get("NewProducerNumber"));
			Assertions.passTest("Broker of Record Page",
					"The New Producer Number : " + testData.get("NewProducerNumber") + " updated successfully ");
			brokerOfRecordPage.borStatusArrow.click();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();
			Assertions.passTest("Broker of Record Page",
					"The BOR status " + testData.get("BORStatus") + " selected successfully");

			// Click on Process Bor Button
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();
			Assertions.passTest("Broker of Record Page", "Clicked on Process BOR");

			// Asserting New Producer Number from BOR page
			Assertions.verify(brokerOfRecordPage.updatedProducerNumber.checkIfElementIsDisplayed(), true,
					"Processed Broker of Record Page", "The New Producer Number : "
							+ brokerOfRecordPage.updatedProducerNumber.getData() + " displayed is verified",
					false, false);

			// Asserting the presence of Incumbent producer letter and New Producer letter
			// links
			Assertions.verify(brokerOfRecordPage.viewIncumbentProducerLetterBtn.checkIfElementIsDisplayed(), true,
					" Processed Broker of Record Page", "The Incumbent Producer Letter Button displayed is verified",
					false, false);
			Assertions.verify(brokerOfRecordPage.viewNewProducerLetterBtn.checkIfElementIsDisplayed(), true,
					"Processed Broker of Record Page", "The New Producer Letter Button displayed is verified", false,
					false);

			// Click on IncumbentProducerLetterBtn
			brokerOfRecordPage.viewIncumbentProducerLetterBtn.scrollToElement();
			brokerOfRecordPage.viewIncumbentProducerLetterBtn.click();
			Assertions.passTest("Processed Broker of Record Page", "Clicked on View Incumbent Producer Letter");

			// Assert the Policy number
			Assertions.verify(producerLetter.emailProducerLetterButton.checkIfElementIsDisplayed(), true,
					"Producer Letter Page", "Producer Letter Page loaded successfully", false, false);
			Assertions.verify(producerLetter.policyNumber.checkIfElementIsDisplayed(), true, "Producer Letter Page",
					"The Insured Name and Policy number displayed is : "
							+ producerLetter.policyNumber.getData().replace("RE:", ""),
					false, false);

			// click on Email producer letter button
			producerLetter.emailProducerLetterButton.scrollToElement();
			producerLetter.emailProducerLetterButton.click();
			Assertions.passTest("Producer Letter Page", "Clicked on Email Producer Letter Button");

			// Send email
			Assertions.verify(emailPage.sendEmailButton.checkIfElementIsDisplayed(), true, "Email Page",
					"Email Page loaded successfully", false, false);
			emailPage.toAddressFiled.setData(testData.get("ProducerEmail"));
			emailPage.commentField.setData(testData.get("EmailComment"));
			Assertions.passTest("Email Page", "Entered the Details successfully");

			// click on send email
			emailPage.sendEmailButton.scrollToElement();
			emailPage.sendEmailButton.click();
			Assertions.passTest("Email Page", "Clicked on Send Email Button");

			// Assert Email sent successful message
			Assertions.verify(brokerOfRecordPage.emailSentSuccessfulMsg.checkIfElementIsDisplayed(), true,
					"Broker Of Record Page", "Broker Of Record Page loaded successfully", false, false);
			Assertions.verify(brokerOfRecordPage.emailSentSuccessfulMsg.checkIfElementIsDisplayed(), true,
					"Broker Of Record Page", "The Email Sent Successful message "
							+ brokerOfRecordPage.emailSentSuccessfulMsg.getData() + " displayed is verified",
					false, false);

			brokerOfRecordPage.newProducerNumber.setData(testData.get("NewProducerNumber"));
			brokerOfRecordPage.borStatusArrow.click();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();

			// Click on Process Bor Button
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();

			// Click on viewNewProducerLetterBtn
			brokerOfRecordPage.viewNewProducerLetterBtn.scrollToElement();
			brokerOfRecordPage.viewNewProducerLetterBtn.click();
			Assertions.passTest("Processed Broker of Record Page", "Clicked on View New Producer Letter");

			// Assert the Policy number
			Assertions.verify(producerLetter.emailProducerLetterButton.checkIfElementIsDisplayed(), true,
					"Producer Letter Page", "Producer Letter Page loaded successfully", false, false);
			Assertions.verify(producerLetter.policyNumber.checkIfElementIsDisplayed(), true, "Producer Letter Page",
					"The Insured Name and Policy number displayed is : "
							+ producerLetter.policyNumber.getData().replace("RE:", ""),
					false, false);

			// click on Email producer letter button
			producerLetter.emailProducerLetterButton.scrollToElement();
			producerLetter.emailProducerLetterButton.click();
			Assertions.passTest("Producer Letter Page", "Clicked on Email Producer Letter Button");

			// Send email
			Assertions.verify(emailPage.sendEmailButton.checkIfElementIsDisplayed(), true, "Email Page",
					"Email Page loaded successfully", false, false);
			emailPage.toAddressFiled.setData(testData.get("ProducerEmail"));
			emailPage.commentField.setData(testData.get("EmailComment"));
			Assertions.passTest("Email Page", "Entered the Details successfully");

			// click on send email
			emailPage.sendEmailButton.scrollToElement();
			emailPage.sendEmailButton.click();
			Assertions.passTest("Email Page", "Clicked on Send Email Button");

			// Assert Email sent successful message
			Assertions.verify(brokerOfRecordPage.emailSentSuccessfulMsg.checkIfElementIsDisplayed(), true,
					"Broker Of Record Page", "Broker Of Record Page loaded successfully", false, false);
			Assertions.verify(brokerOfRecordPage.emailSentSuccessfulMsg.checkIfElementIsDisplayed(), true,
					"Broker Of Record Page", "The Email Sent Successful message "
							+ brokerOfRecordPage.emailSentSuccessfulMsg.getData() + " displayed is verified",
					false, false);

			// Click on close Button
			brokerOfRecordPage.cancel.scrollToElement();
			brokerOfRecordPage.cancel.click();

			// Assert the New Producer number from Account overview page
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			String newProducerNumber = accountOverviewPage.producerNumber.getData();
			Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The producer Number After Updating New Producer is  " + newProducerNumber
							+ " displayed is verified",
					false, false);

			// Asserting renewal quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + quoteNumber);

			// SignOut as USM
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			// Search for Policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.passTest("Home Page", "Searched the Policy " + policyNumber + " successfully");

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Click on Endorse Policy link
			policySummaryPage.producerEndorsePolicyLink.scrollToElement();
			policySummaryPage.producerEndorsePolicyLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse Policy Link");

			// Asserting the Warning message
			Assertions.verify(endorsePolicyPage.producerEndorseWarningMessage.checkIfElementIsDisplayed(), true,
					"Not Eligible for Producer Endorsement Page", "The Warning message "
							+ endorsePolicyPage.producerEndorseWarningMessage.getData() + " displayed is verified",
					false, false);

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

			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search the renewal quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Renewal Quote Number " + quoteNumber + " successfully");

			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// click on view Previous policy button
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Previous Policy Button");

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// click on endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

			// Entering Endorsement effective date
			testData = data.get(dataValue2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on edit prior losses
			endorsePolicyPage.editPriorLoss.scrollToElement();
			endorsePolicyPage.editPriorLoss.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit prior loss link");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next button");

			// clicking on ok,continue in Endorse policy page
			if (endorsePolicyPage.pageName.getData().contains("Overrides Required")) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Asserting AOR warning message
			Assertions.verify(endorsePolicyPage.aorWarningMessage.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page",
					"The Warning message " + endorsePolicyPage.aorWarningMessage.getData() + " displayed is verified",
					false, false);

			if (endorsePolicyPage.transactionComments.checkIfElementIsPresent()
					&& endorsePolicyPage.transactionComments.checkIfElementIsDisplayed()) {
				endorsePolicyPage.transactionComments.scrollToElement();
				endorsePolicyPage.transactionComments.setData(testData.get("TransactionDescription"));
			}
			// Click on Complete
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete");

			// Asserting Roll forward option is not available
			Assertions.verify(endorsePolicyPage.rollForwardBtn.checkIfElementIsPresent(), false, "Endorse Policy Page",
					"Roll Forward Option is not available is verified", false, false);

			endorsePolicyPage.waitTime(3);
			if (endorsePolicyPage.closeButton.checkIfElementIsPresent()
					&& endorsePolicyPage.closeButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
				endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
				endorsePolicyPage.closeButton.scrollToElement();
				endorsePolicyPage.closeButton.click();
				endorsePolicyPage.closeButton.waitTillInVisibilityOfElement(60);
			}

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC074 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC074 ", "Executed Successfully");
			}
		}
	}

}
