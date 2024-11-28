package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC042_PNBCR008 extends AbstractNAHOTest {

	public TC042_PNBCR008() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBCR008.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		Date date = new Date();
		boolean isTestPassed = false;

		try {
			// Creating New Account
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
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);

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

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			// Asserting policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Asserting Policy Effective Date
			String policyEffectiveDate = testData.get("PolicyEffDate");
			Assertions.verify(
					policyEffectiveDate.contains(policySummaryPage.transHistEffDate.formatDynamicPath(2).getData()),
					true, "Policy Summary Page",
					"The policy Effective Date is " + policySummaryPage.transHistEffDate.formatDynamicPath(2).getData()
							+ " displayed is verified",
					false, false);

			// click on cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel policy link");

			// Selecting Cancel Reasons and Asserting Cancellation Effective Date
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page loaded successfully", false, false);

			// Selecting Cancel Reasons
			Calendar calender = Calendar.getInstance();
			calender.setTime(date);
			boolean friday = calender.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
			if (friday) {
				for (int i = 0; i < 12; i++) {
					int dataValuei = i;
					testData = data.get(dataValuei);
					cancelPolicyPage.cancelReasonArrow.scrollToElement();
					cancelPolicyPage.cancelReasonArrow.click();
					String cancelReasoni = cancelPolicyPage.cancelReasonOption
							.formatDynamicPath(testData.get("CancellationReason")).getData();
					cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason"))
							.scrollToElement();
					cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
					Assertions.passTest("Cancel Policy Page", "The Cancel Reason Selected is " + cancelReasoni);

					// Asserting the Cancellation effective Date
					String calcEffDateReasons = testData.get("TransactionEffectiveDate");
					Assertions.passTest("Cancel Policy Page", "The Calculated date is " + calcEffDateReasons);
					Assertions.verify(calcEffDateReasons.equals(cancelPolicyPage.cancellationEffectiveDate.getData()),
							true, "Cancel Policy Page",
							"The Cancellation Efffective Date " + calcEffDateReasons + " displayed is verified", false,
							false);
				}
			}

			else {
				for (int i = 0; i < 12; i++) {
					int dataValuei = i;
					testData = data.get(dataValuei);
					cancelPolicyPage.cancelReasonArrow.scrollToElement();
					cancelPolicyPage.cancelReasonArrow.click();
					String cancelReasoni = cancelPolicyPage.cancelReasonOption
							.formatDynamicPath(testData.get("CancellationReason")).getData();
					cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason"))
							.scrollToElement();
					cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
					Assertions.passTest("Cancel Policy Page", "The Cancel Reason Selected is " + cancelReasoni);

					// Asserting the Cancellation effective Date
					String calcEffDateReasons = testData.get("CancellationEffectiveDate");
					Assertions.passTest("Cancel Policy Page", "The Calculated date is " + calcEffDateReasons);
					Assertions.verify(calcEffDateReasons.equals(cancelPolicyPage.cancellationEffectiveDate.getData()),
							true, "Cancel Policy Page",
							"The Cancellation Efffective Date " + calcEffDateReasons + " displayed is verified", false,
							false);
				}
			}

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC042 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC042 ", "Executed Successfully");
			}
		}
	}

}
