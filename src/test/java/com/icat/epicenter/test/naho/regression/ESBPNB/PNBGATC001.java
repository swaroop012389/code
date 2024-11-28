/** Program Description: 1. Verifying Cancel Policy Date for different Reason code and Reinstatement - GA
 * 						 2. Verifying the "NOTICE OF COVERAGE REDUCTION" when selecting coverage change option under renewal indicators and Added IO-19950
 *  Author			   : Priyanka S
 *  Date of Creation   : 12/31/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBGATC001 extends AbstractNAHOTest {

	public PNBGATC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/GATC001.xls";
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
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
		ReferralPage referralPage = new ReferralPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();

		// Initializing the variables
		int dataValue1 = 0;
		int dataValue14 = 13;
		int dataValue15 = 14;
		String quoteNumber;
		String policyNumber;
		String renewalQuoteNumber;
		int quoteLen;
		String renewalPolicyNumber;
		Date date = new Date();
		Map<String, String> testData = data.get(dataValue1);
		Map<String, String> testData1 = data.get(dataValue15);
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

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
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

			if (dwellingPage.homePageButton.checkIfElementIsPresent()
					&& dwellingPage.homePageButton.checkIfElementIsDisplayed()) {

				// Clicking on homepage button
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchReferral(quoteNumber);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");

				// Approve Referral
				Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true,
						"Referral Page", "Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
						"Request Bind page loaded successfully", false, false);
				requestBindPage.approveRequestNAHO(testData);
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			}

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Asserting Status of Policy
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy status is : " + policySummaryPage.policyStatus.getData(), false, false);

			// Click on Cancel Policy
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy Link");

			// Validation for NOC - Enter 100 in Days Before NOC and select any Cancellation
			// Reason.
			// Check if “60” is displayed in Days Before NOC Text Field
			Assertions.addInfo("Scenario 01",
					"Enter 100 in Days Before NOC and select any Cancellation Reason. Check if â€œ60â€� is displayed in Days Before NOC Text Field");
			// cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			waitTime(3);// if wait time removed value is not capturing
			if (cancelPolicyPage.daysBeforeNOC.checkIfElementIsPresent()
					&& cancelPolicyPage.daysBeforeNOC.checkIfElementIsDisplayed()) {
				if (!testData.get("Cancellation_DaysBeforeNOC").equals("")) {
					cancelPolicyPage.daysBeforeNOC.scrollToElement();
					cancelPolicyPage.daysBeforeNOC.setData(testData.get("Cancellation_DaysBeforeNOC"));
				}
			}
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());
			if (!testData.get("CancellationEffectiveDate").equals("")) {
				cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
				cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
				cancelPolicyPage.cancellationEffectiveDate.tab();
			}

			// Verifying if the No of Days is changed to 60
			waitTime(3);// if wait time removed value is not capturing
			Assertions
					.verify("60", cancelPolicyPage.daysBeforeNOC.getData(), "Cancel Policy Page",
							"No of days entered is " + testData.get("Cancellation_DaysBeforeNOC")
									+ " and is modified as : " + cancelPolicyPage.daysBeforeNOC.getData(),
							false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			waitTime(3);
			// Click on Cancel button
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();

			// Click on Cancel Policy
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();

			Assertions.passTest("Policy Summary Page", "Clicked on Cancel policy link");

			// Selecting Cancel Reasons and Asserting Cancellation Effective Date
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page loaded successfully", false, false);
			Assertions.passTest("Cancel Policy Page", "Clicked on Cancel policy link");

			// Selecting Cancel Reasons
			Assertions.addInfo("Scenario 02", "Scenario 02 Started");
			Assertions.addInfo("Cancel Policy page", "Verifying Cancel Policy Date for different Reason code");
			Calendar calender = Calendar.getInstance();
			calender.setTime(date);
			boolean friday = calender.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
			if (friday) {
				for (int i = 1; i < 13; i++) {
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
							"The Cancellation Reason is " + cancelReasoni
									+ " , the Actual Cancellation Effective Date is "
									+ cancelPolicyPage.cancellationEffectiveDate.getData()
									+ " and the Expected Cancellation Efffective Date " + calcEffDateReasons
									+ " displayed is verified",
							false, false);
				}
			}

			else {
				for (int i = 1; i < 13; i++) {
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
							"The Cancellation Reason is " + cancelReasoni
									+ " , the Actual Cancellation Effective Date is "
									+ cancelPolicyPage.cancellationEffectiveDate.getData()
									+ " and the Expected Cancellation Efffective Date " + calcEffDateReasons
									+ " displayed is verified",
							false, false);
				}
			}
			Assertions.addInfo("Scenario 02", "Scenario 2 Ended");

			waitTime(3);

			// Click on Cancel button
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();

			// Click on Cancel Policy
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();

			// Cancel Policy
			// Adding the ticket IO-19950
			testData = data.get(dataValue14);
			// cancelPolicyPage.enterCancellationDetails(testData);
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			waitTime(3);
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());
			if (!testData.get("CancellationEffectiveDate").equals("")) {
				cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
				cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
				cancelPolicyPage.cancellationEffectiveDate.tab();
			}
			if (cancelPolicyPage.legalNoticeWording.checkIfElementIsPresent()
					&& cancelPolicyPage.legalNoticeWording.checkIfElementIsDisplayed()) {
				if (!testData.get("Cancellation_LegalNoticeWording").equals("")) {
					cancelPolicyPage.legalNoticeWording.scrollToElement();
					cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
				}
			}

			Assertions.addInfo("Scenario 03",
					"Verifying the absence of Warning message for cancelling not outside of UW period for GA");
			Assertions.verify(cancelPolicyPage.globalError.checkIfElementIsPresent(), false, "Cancel Policy Page",
					"This transaction is outside of the UW period. Proceeding with a cancellation for Underwriting reasons may result in a fine this warning message not displyed is verified",
					false, false);

			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			Assertions.verify(cancelPolicyPage.globalError.checkIfElementIsPresent(), false, "Cancel Policy Page",
					"This transaction is outside of the UW period. Proceeding with a cancellation for Underwriting reasons may result in a fine this warning message not displyed is verified",
					false, false);
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Asserting Status of Policy
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. Policy status after cancellation is : "
							+ policySummaryPage.policyStatus.getData(),
					false, false);

			// Reinstate policy
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();

			// Re-instate Policy
			Assertions.passTest("Reinstate Policy Page", "Reinstate Policy Page is loaded successfully");
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);

			// Asserting Status of Policy
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. Policy status after reinstate is : "
							+ policySummaryPage.policyStatus.getData(),
					false, false);

			// Navigating to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Creating New Account
			testData1 = data.get(dataValue15);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData1, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData1);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData1);
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// Entering prior loss details
			if (!testData1.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData1);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData1);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Getting the Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData1, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData1);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			testData1 = data.get(dataValue15);
			requestBindPage.enterBindDetailsNAHO(testData1);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Clicking on home page button
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
						"Quote is referred to USM " + referralPage.assignedUser.getData(), false, false);
			} else {

				Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false, "Referral Page",
						"Quote is referred to USM " + referralPage.assignedUser.getData(), false, false);
			}
			// Ended

			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind page loaded successfully", false, false);
			requestBindPage.approveRequestNAHO(testData1);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Click on Renewal indicators link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// Select Coverage Change Checkbox
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page Loaded successfully", false, false);
			renewalIndicatorsPage.coverageChange.scrollToElement();
			renewalIndicatorsPage.coverageChange.select();
			Assertions.passTest("Renewal Indicators Page", "Selected the coverage change checkbox");

			// Verifying the NOTICE OF COVERAGE REDUCTION wordings
			Assertions.addInfo("Scenario 02",
					"Verifying the NOTICE OF COVERAGE REDUCTION wordings after selecting coverage change checkbox");
			Assertions.verify(
					renewalIndicatorsPage.coverageChangeLegalNoticeWording.getData()
							.contains("NOTICE OF COVERAGE REDUCTION"),
					true, "Renewal Indicators Page",
					"The Legal Notice wording " + renewalIndicatorsPage.coverageChangeLegalNoticeWording.getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on Cancel
			renewalIndicatorsPage.cancelButton.scrollToElement();
			renewalIndicatorsPage.cancelButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Cancel button successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on Expacc link
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			Assertions.passTest("Home Page", "Clicked on Expacc link");
			expaccInfoPage.enterExpaccInfo(testData1, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Performing Renewal Searches
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the Policy " + policyNumber + " successfully");

			// clicking on renewal policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// Getting renewal quote number
			Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			renewalQuoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + renewalQuoteNumber);

			// Assert renewal quote number
			Assertions.verify(
					accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsPresent()
							&& accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote number is : " + renewalQuoteNumber);
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {

				// Opening the Referral from Account Overview Page
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();
				Assertions.passTest("Account Overview Page", "Referral is Openned");

				// Approve the referral
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

				// Click on close
				if (referralPage.close.checkIfElementIsPresent() && referralPage.close.checkIfElementIsDisplayed()) {
					referralPage.close.scrollToElement();
					referralPage.close.click();
				}

				// Search for quote number
				homePage.searchQuote(renewalQuoteNumber);
			}

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release renewal to producer button");

			// Click on request bind
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData1, renewalQuoteNumber);
//		accountOverviewPage.requestBind.scrollToElement();
//		accountOverviewPage.requestBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");

			// Enter bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Verify the presence of due diligence checkbox and text
			Assertions.addInfo("Scenario 04",
					"Verifying the Due diligence checkbox and wordings on renewal request bind page");
			Assertions.verify(requestBindPage.dueDiligenceCheckbox.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "Diligence Check Box present is verified", false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("A minimum 25%").checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"The Wordings " + requestBindPage.dueDiligenceText.formatDynamicPath("A minimum 25%").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("Completed and signed application").checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"The Wordings " + requestBindPage.dueDiligenceText
							.formatDynamicPath("Completed and signed application").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("diligent effort form").checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"The Wordings "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("diligent effort form").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Enter Renewal Bind Details
			requestBindPage.renewalRequestBindNAHO(testData1);
			Assertions.passTest("Request Bind Page", "Entered the renewal request bind details successfully");

			// Asserting renewal policy number
			renewalPolicyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. The Renewal PolicyNumber is : " + renewalPolicyNumber,
					false, false);

			// Adding ticket IO-IO-19950
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching NB policy
			homePage.searchPolicy(policyNumber);
			Assertions.verify(policySummaryPage.cancelPolicy.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			// Click on cancel link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			changeNamedInsuredPage.okbtn.scrollToElement();
			changeNamedInsuredPage.okbtn.click();
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy page loaded successfully", false, false);

			// Select any cancel reason
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();

			// Asserting warning message Select any cancellation reason "This transaction is
			// outside of the UW period. Proceeding with a cancellation for Underwriting
			// reasons may result in a fine."
			Assertions.addInfo("Scenario 05", "Asserting warning message");

			Assertions.verify(
					cancelPolicyPage.globalError.getData().contains("This transaction is outside of the UW period"),
					true, "Cancel Policy Page",
					"Underwriting period warning message is " + cancelPolicyPage.globalError.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// sign out as Usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBGATC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBGATC001 ", "Executed Successfully");
			}
		}
	}
}
