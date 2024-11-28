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
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC047_NBQA005 extends AbstractNAHOTest {

	public TC047_NBQA005() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBQA005.xls";
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
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
		int data_Value1 = 0;
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

			// Enter Referral Contact Details
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.click();

				// verifying referral message
				Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true,
						"Referral Page", "Quote " + referQuotePage.quoteNumberforReferral.getData()
								+ " referring to USM " + " is verified",
						false, false);
				String quoteNumber = referQuotePage.quoteNumberforReferral.getData();

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
				loginPage.enterLoginDetails(setUpData.get("Username"), setUpData.get("Password"));
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
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

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

				// Sign out as USM
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Login Page", "Logged out as USM successfully");

				// Sign in as Producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as Producer successfully");

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// Assert account overview page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);
				Assertions.passTest("Producer Home Page", "Quote Number : " + quoteNumber + " searched successfully");
			}

			// Click On Bind
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Answer underwriting questions
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering Bind Details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as Producer Successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM successfully");

			homePage.searchQuote(quoteNumber);

			// approving referral
			accountOverviewPage.uploadPreBindApproveAsUSM();
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.select();
				requestBindPage.approve.scrollToElement();
				requestBindPage.approve.click();
			}

			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policyNumber.contains(""), true, "Policy Summary Page",
					"Policy Number is: " + policyNumber, false, false);

			// Click on Quote Number link
			policySummaryPage.quoteNoLinkNAHO.scrollToElement();
			policySummaryPage.quoteNoLinkNAHO.click();

			// Asserting AI Details from Quote details Page
			Assertions.verify(quoteDetailsPage.aitableHeaderName.checkIfElementIsDisplayed(), true,
					"Quote Details Page", "Quote Details Page loaded successfully", false, false);

			String aitableHeaderName = quoteDetailsPage.aitableHeaderName.getData();
			Assertions.verify(quoteDetailsPage.aitableHeaderName.checkIfElementIsDisplayed(), true,
					"Quote Details Page", "The Header Name displayed is " + aitableHeaderName, false, false);

			String aiName = quoteDetailsPage.aiName.getData();
			Assertions.verify(quoteDetailsPage.aiName.checkIfElementIsDisplayed(), true, "Quote Details Page",
					aiName + " header is displayed", false, false);

			String aiType = quoteDetailsPage.aiType.formatDynamicPath(1).getData();
			Assertions.verify(quoteDetailsPage.aiType.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Quote Details Page", aiType + " header is displayed", false, false);

			String address = quoteDetailsPage.aiType.formatDynamicPath(2).getData();
			Assertions.verify(quoteDetailsPage.aiType.formatDynamicPath(2).checkIfElementIsDisplayed(), true,
					"Quote Details Page", address + " header is displayed", false, false);

			String rank = quoteDetailsPage.aiType.formatDynamicPath(3).getData();
			Assertions.verify(quoteDetailsPage.aiType.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"Quote Details Page", rank + " header is displayed", false, false);

			String loanNumber = quoteDetailsPage.aiType.formatDynamicPath(4).getData();
			Assertions.verify(quoteDetailsPage.aiType.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"Quote Details Page", loanNumber + " header is displayed", false, false);

			// Asserting Mailing Address
			String mortgageeMailingAddress = quoteDetailsPage.aiType.formatDynamicPath(7).getData();
			Assertions.verify(quoteDetailsPage.aiType.formatDynamicPath(7).checkIfElementIsDisplayed(), true,
					"Quote Details Page", "The address displayed for mortgagee type is " + mortgageeMailingAddress,
					false, false);

			String insuredMailingAddress = quoteDetailsPage.aiType.formatDynamicPath(12).getData();
			Assertions.verify(quoteDetailsPage.aiType.formatDynamicPath(12).checkIfElementIsDisplayed(), true,
					"Quote Details Page",
					"The address displayed for Additional Insured type is " + insuredMailingAddress, false, false);

			// Asserting Rank value
			String rankvalue = quoteDetailsPage.aiType.formatDynamicPath(8).getData();
			Assertions.verify(quoteDetailsPage.aiType.formatDynamicPath(8).checkIfElementIsDisplayed(), true,
					"Quote Details Page", rankvalue + " rank selected is verified", false, false);

			// Asserting Loan numbers
			String mortgageeLoanNumber = quoteDetailsPage.aiType.formatDynamicPath(9).getData();
			Assertions.verify(quoteDetailsPage.aiType.formatDynamicPath(9).checkIfElementIsDisplayed(), true,
					"Quote Details Page", "The loan number displayed for Mortgagee type is " + mortgageeLoanNumber,
					false, false);

			String additionalInsuredLoanNumber = quoteDetailsPage.aiType.formatDynamicPath(14).getData();
			Assertions.verify(quoteDetailsPage.aiType.formatDynamicPath(14).checkIfElementIsDisplayed(), true,
					"Quote Details Page",
					"The loan number displayed for Additional Insured type is " + additionalInsuredLoanNumber, false,
					false);

			// Verifying the conditions presence
			String earnedPremiumCondition = quoteDetailsPage.earnedPremiumCondition.getData();
			Assertions.verify(quoteDetailsPage.earnedPremiumCondition.checkIfElementIsDisplayed(), true,
					"Quote Details Page", earnedPremiumCondition + " message displayed is verified", false, false);

			String allFeeCondition = quoteDetailsPage.allFeeCondition.getData();
			Assertions.verify(quoteDetailsPage.allFeeCondition.checkIfElementIsDisplayed(), true, "Quote Details Page",
					allFeeCondition + " message displayed is verified", false, false);

			String cancelCondition = quoteDetailsPage.cancelCondition.getData();
			Assertions.verify(quoteDetailsPage.cancelCondition.checkIfElementIsDisplayed(), true, "Quote Details Page",
					cancelCondition + " message displayed is verified", false, false);

			// SignOut and Close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC047 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC047 ", "Executed Successfully");
			}
		}
	}

}
