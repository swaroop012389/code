/* Name: Pavan Mule
Description:IO-21935 NAHO Renewal Requotes (And NB) are not referred when previous quotes were overridden (Not an NRNL Issue)
Date: 21/5/2024  */
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
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC015_NBRE008 extends AbstractNAHOTest {

	public TC015_NBRE008() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE008.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		LoginPage loginPage = new LoginPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		String quoteNumber;
		int quoteLen;
		boolean isTestPassed = false;

		try {
			// Creating a new account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
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
			Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is " + quoteNumber);

			// Click on Override Premium link
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
					"Override PremiumAndFees Page", "Override PremiumAndFees Page loaded successfully", false, false);

			// Enter override premium fee details
			overridePremiumAndFeesPage.enterFeesDetailsNAHO(testData);
			Assertions.passTest("Override PremiumAnadFees Page", "Override PremiumAnadFees entered successfully");

			// Log out as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign Out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// Quote searched successfully
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Producer Home Page", "Quote Number : " + quoteNumber + " searched successfully");

			// Click on edit deductible and limits
			// Creating requote and it should refer
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit deductible and limits button successfully");

			// click on get a quote button
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on get a quote button successfully");

			// Asserting referral message when override premium fees is added in usm NB
			// quote 'referral message is 'This account has a quote with premium overrides.'
			referQuotePage.referralMessages.formatDynamicPath("This account has a quote with premium overrides.")
					.waitTillPresenceOfElement(60);
			Assertions.addInfo("Scenarion 01", "Asserting referral message when override premium fees is added");
			Assertions.verify(
					referQuotePage.referralMessages
							.formatDynamicPath("This account has a quote with premium overrides.").getData()
							.contains("This account has a quote with premium overrides."),
					true, "Refer Quote Page",
					"The referral message is "
							+ referQuotePage.referralMessages
									.formatDynamicPath("This account has a quote with premium overrides.").getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Enter Referral Contact Details
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();

			// verifying referral message
			Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true, "Referral Page",
					"Quote " + referQuotePage.quoteNumberforReferral.getData() + " referring to USM " + " is verified",
					false, false);
			quoteNumber = referQuotePage.quoteNumberforReferral.getData();
			Assertions.passTest("Refer Quote Page", "Re-quote number is " + quoteNumber);

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

			// searching the quote number in grid and clicking on the quote number link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote Searched successfullly");

			// Click on open referral link
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on open referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Click on home page link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// searching the quote number in grid and clicking on the quote number link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote Searched successfullly");

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on bind button successfully");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Click on home page link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// searching the quote number in grid and clicking on the quote number link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote Searched successfullly");

			// Click on open referral link
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on open referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Approved bind referal successfully");

			// Asserting policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

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

			// asserting renewal quote
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1,
					(accountOverviewPage.quoteNumber.getData().length() - 1));
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is " + renewalQuoteNumber);

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on release renewal to producer button successfully");

			// Click on Override Premium link
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
					"Override PremiumAndFees Page", "Override PremiumAndFees Page loaded successfully", false, false);

			// Enter override premium fee details
			overridePremiumAndFeesPage.enterFeesDetailsNAHO(testData);
			Assertions.passTest("Override PremiumAnadFees Page", "Override PremiumAnadFees entered successfully");

			// Log out as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign Out as USM successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// Quote searched successfully
			homePage.searchQuoteByProducer(renewalQuoteNumber);
			Assertions.passTest("Producer Home Page",
					"Quote Number : " + renewalQuoteNumber + " searched successfully");

			// Click on edit deductible and limits
			// Creating renewal requote and it should be referred
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit deductible and limits button successfully");

			// click on get a quote button
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on get a quote button successfully");

			// Asserting referral message when override premium fees is added in usm Renewal
			// quote
			// quote 'referral message is 'This account has a quote with premium overrides.'
			referQuotePage.referralMessages.formatDynamicPath("This account has a quote with premium overrides.")
					.waitTillPresenceOfElement(60);
			Assertions.addInfo("Scenarion 02", "Asserting referral message when override premium fees is added");
			Assertions.verify(
					referQuotePage.referralMessages
							.formatDynamicPath("This account has a quote with premium overrides.").getData()
							.contains("This account has a quote with premium overrides."),
					true, "Refer Quote Page",
					"The referral message is "
							+ referQuotePage.referralMessages
									.formatDynamicPath("This account has a quote with premium overrides.").getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Enter Referral Contact Details
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();

			// verifying referral message
			Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true, "Referral Page",
					"Quote " + referQuotePage.quoteNumberforReferral.getData() + " referring to USM " + " is verified",
					false, false);
			renewalQuoteNumber = referQuotePage.quoteNumberforReferral.getData();
			Assertions.passTest("Refer Quote Page", "Renewal re-quote number is " + renewalQuoteNumber);

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

			// searching the quote number in grid and clicking on the quote number link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(renewalQuoteNumber);
			Assertions.passTest("Home Page", "Quote Searched successfullly");

			// Click on open referral link
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on open referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Click on home page link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// searching the quote number in grid and clicking on the quote number link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(renewalQuoteNumber);
			Assertions.passTest("Home Page", "Quote Searched successfullly");

			// Click on issue quote button
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on issue quote button successfully");

			// Click on bind button
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, renewalQuoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Enter renewal bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.renewalRequestBindNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Click on home page link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// searching the quote number in grid and clicking on the quote number link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(renewalQuoteNumber);
			Assertions.passTest("Home Page", "Quote Searched successfullly");

			// Click on open referral link
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on open referral link successfully");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Referral page
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Referral Page", "Approved bind referral successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. Renewal PolicyNumber is : " + policyNumber, false, false);

			// SignOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC015 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC015 ", "Executed Successfully");
			}
		}
	}
}
