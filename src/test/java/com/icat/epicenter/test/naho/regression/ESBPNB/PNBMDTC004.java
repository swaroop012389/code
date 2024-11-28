/*Program Description: Asserting Following:
 * 					 1. Asserting referral message for decrease COv A and Increase TIV 20% or more as producer login and IO-21792
 * Author            : Priyanka S
 * Date of Creation  : 27-05-2022
 */

package com.icat.epicenter.test.naho.regression.ESBPNB;

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
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBMDTC004 extends AbstractNAHOTest {

	public PNBMDTC004() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/MDTC004.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		LoginPage login = new LoginPage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ReferralPage referralPage = new ReferralPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int quoteLen;
		String quoteNumber;
		String policyNumber;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

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
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Opening the Referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);

			// IO-21792-As part of this our expectation is to validate if the referred quote
			// is assigned to USM not "Holder RMS"
			if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {

				Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true, "Referral Page",
						"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);
			} else {

				Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false, "Referral Page",
						"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);
			}
			// Ended

			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind page loaded successfully", false, false);
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Asserting Status of Policy
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy status is : " + policySummaryPage.policyStatus.getData(), false, false);

			// Click on Renewal Link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal link successfully ");

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

				// Clicking on expaac link in home page
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				Assertions.passTest("Home Page", "Clicked on Expaac Link");

				// Entering expaac data
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

				// Click on continue button in Renewal building review page
				if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
						&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
					policyRenewalPage.continueRenewal.scrollToElement();
					policyRenewalPage.continueRenewal.click();
					Assertions.passTest("Renewal Building Review Page", "Clicked on Continue");
				}
			}

			// Getting quote number 2
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overvew Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + quoteNumber);

			// Click on Release Renewal to producer button
			if (accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsPresent()
					&& accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed()) {
				accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
				accountOverviewPage.releaseRenewalToProducerButton.click();
				Assertions.passTest("Account Overview Page", "Quote released to producer");
			}

			// Signout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.addInfo("Homepage", "Logged out as USM");

			// Login as Producer
			login.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.addInfo("Login Page", "Logged in as Producer");

			// Search for quote
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Quote searched successfully");

			// Click on Edit deductibles button
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overvew Page", "Account Overview Page loaded successfully", false, false);
			if (accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed()
					&& accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed()) {
				accountOverviewPage.editDeductibleAndLimits.scrollToElement();
				accountOverviewPage.editDeductibleAndLimits.click();
			}

			// Update Cov A and Cov E values
			testData = data.get(data_Value2);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Asserting the referral message for COv A "This renewal requote decreases the
			// Coverage A limit and requires further review by an ICAT Online Underwriter.
			// Please provide additional information regarding the reason for the decrease."
			Assertions.addInfo("Scenario 01", "Verifying and asserting referral message for Cov A");
			Assertions.verify(
					referQuotePage.referralMessages
							.formatDynamicPath("decreases the Coverage A ").checkIfElementIsDisplayed(),
					true, "Refer Quote Page",
					"Verified Cov A referral message is dispalyed as : "
							+ referQuotePage.referralMessages.formatDynamicPath("decreases the Coverage A ").getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Referring the Quote to USM
			referQuotePage.contactEmail.clearData();
			referQuotePage.contactEmail.setData("hiho1@icat.com");
			referQuotePage.comments.setData("Test");
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();

			// Fetching Quote Number
			quoteNumber = referQuotePage.quoteNumberforReferral.getData();

			// Signing out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as producer successfully");

			// Signing in as USM
			login.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Loged in as USM successfully");

			// Searching the referred quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Referred Quote searched successfully");

			// Opening the referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Approving the referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral Page loaded successfully", false, false);

			// IO-21792-As part of this our expectation is to validate if the referred quote
			// is assigned to USM not "Holder RMS"
			if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {
				Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true, "Referral Page",
						"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);

			} else {

				Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false, "Referral Page",
						"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);
			}
			// Ended

			// click on approve in Approve Decline Quote page
			referralPage.clickOnApprove();
			approveDeclineQuotePage.clickOnApprove();

			// Click on close
			referralPage.close.scrollToElement();
			referralPage.close.click();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Signout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.addInfo("Homepage", "Logged out as USM");

			// Login as Producer
			login.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.addInfo("Login Page", "Logged in as Producer");

			// Search for quote
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Quote searched successfully");

			// Click on Edit deductibles button
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overvew Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit deductible and limits button successfully");

			// Update insured values
			testData = data.get(data_Value3);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Asserting the referral message for Increase TIV 20% or more"This renewal
			// requote increases the TIV by 20% or more and requires further review by an
			// ICAT Online Underwriter. Please provide additional"
			Assertions.addInfo("Scenario 02", "Verifying and asserting referral message for increase TIV 20% more");
			Assertions.verify(referQuotePage.referralMessages
					.formatDynamicPath("renewal requote increases the TIV by 20% or more").checkIfElementIsDisplayed(),
					true, "Refer Quote Page",
					"Verified increase TIV 20% or more referral message is dispalyed as : "
							+ referQuotePage.referralMessages
									.formatDynamicPath("renewal requote increases the TIV by 20% or more").getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Referring the Quote to USM
			referQuotePage.contactEmail.clearData();
			referQuotePage.contactEmail.setData("hiho1@icat.com");
			referQuotePage.comments.setData("Test");
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();

			// Fetching Quote Number
			quoteNumber = referQuotePage.quoteNumberforReferral.getData();

			// Signing out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as producer successfully");

			// Signing in as USM
			login.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Loged in as USM successfully");

			// Searching the referred quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Referred Quote searched successfully");

			// Opening the referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Approving the referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral Page loaded successfully", false, false);

			// IO-21792-As part of this our expectation is to validate if the referred quote
			// is assigned to USM not "Holder RMS"
			if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {
				Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true, "Referral Page",
						"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);

			} else {

				Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false, "Referral Page",
						"Quote is referred USM is ' " + referralPage.assignedUser.getData() + " '", false, false);
			}
			// Ended

			// Sign out as Usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBMDTC004 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBMDTC004 ", "Executed Successfully");
			}
		}
	}

}
