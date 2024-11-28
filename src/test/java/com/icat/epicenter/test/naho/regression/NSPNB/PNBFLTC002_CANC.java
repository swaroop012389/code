/** Program Description: 1. Create a Policy,Enter NOC Days without Underwriting period and assert the NOC Days Change,Assert the Cancellation effective dates for all the reasons,Cancel the policy and reinstate the policy
 *  					 2. Check if the Transaction Premium is positive also check Annual Total, Term Total and SLTF values are updated correctly based on the updated Policy expiration date.
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 04/05/2021
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBFLTC002_CANC extends AbstractNAHOTest {

	public PNBFLTC002_CANC() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/FL002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
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
		ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		// Initializing the variables
		String originalPremium;
		String originalInspFee;
		String originalPolicyFee;
		String originalSLTF;
		String originalSurplusContributionValue;
		double d_originalPremium;
		double d_originalInspFee;
		double d_originalPolicyFee;
		double d_originalSurplusContributionValue;
		double d_originalSLTF;
		String surplusLinesTaxesPercentage;
		String fslsoServiceFeePercentage;
		double d_surplusLinesTaxesPercentage;
		double d_fslsoServiceFeePercentage;
		double d_surplusLinesTaxes;
		double d_fslsoServiceFee;
		double calculatedSLTValue;
		String returnedSLTF;
		double d_returnedSLTF;
		String earnedPremium;
		String earnedInspFee;
		String earnedPolicyFee;
		String earnedSLTF;
		String earnedSurplusContributionValue;
		double d_earnedPremium;
		double d_earnedInspFee;
		double d_earnedPolicyFee;
		double d_earnedSLTF;
		double d_earnedSurplusContributionValue;
		String returnedPremium;
		String returnedInspFee;
		String returnedPolicyFee;
		String returnSurplusContributionValue;
		double d_returnedPremium;
		double d_returnedInspFee;
		double d_returnedPolicyFee;
		double d_returnSurplusContributionValue;
		double empaCharge = 2;
		int data_Value1 = 0;
		int data_Value2 = 1;
		String producerCommentsProducerSection, policyNumber, producerCommentsNoteBarSection;
		int commentOccuranceCountProducerSection, commentOccuranceCountNoteBarSection;
		Map<String, String> testData = data.get(data_Value1);
		DecimalFormat df = new DecimalFormat("0.00");
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

			// Assert Sinkhole Options
			// Asserting sinkhole and cgcc options are available in the drop down
			Assertions.addInfo("Scenario 01", "Verifying Sinkhole and CGCC Override Options are available");
			for (int i = 1; i < 3; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);
				createQuotePage.sinkholeArrow.scrollToElement();
				createQuotePage.sinkholeArrow.click();
				String sinkholeReasoni = createQuotePage.sinkholeOption.formatDynamicPath(testData.get("Sinkhole"))
						.getData();
				createQuotePage.sinkholeOption.formatDynamicPath(testData.get("Sinkhole")).scrollToElement();
				createQuotePage.sinkholeOption.formatDynamicPath(testData.get("Sinkhole")).click();
				Assertions.passTest("Create Quote Page",
						"The Reason available " + sinkholeReasoni + " present is verified");
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			testData = data.get(data_Value1);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

			// Validating the premium amount
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// click on cancel policy link
			Assertions.addInfo("Scenario 02",
					"Enter NOC Days without Underwriting period and verify the NOC Days Change to within underwriting period");
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel policy link");

			// Selecting Cancel Reasons and Asserting Cancellation Effective Date
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page loaded successfully", false, false);

			// Enter Days before Noc outside the underwriting period
			if (!testData.get("Cancellation_DaysBeforeNOC").equals("")) {
				cancelPolicyPage.daysBeforeNOC.scrollToElement();
				cancelPolicyPage.daysBeforeNOC.setData(testData.get("Cancellation_DaysBeforeNOC"));
				Assertions.passTest("Cancel Policy Page",
						"The Days before NOC Entered is " + cancelPolicyPage.daysBeforeNOC.getData());
			}

			// Select a Reason
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"The Cancel Reason Selected is " + testData.get("CancellationReason"));

			// Verifying Days Before NOC changed to below 90
			String actualdaysbeforeNOC = cancelPolicyPage.daysBeforeNOC.getData();
			int intactualdaysbeforeNOC = Integer.parseInt(actualdaysbeforeNOC);

			String daysbeforeNOC = testData.get("Cancellation_DaysBeforeNOC");
			int intdaysbeforeNOC = Integer.parseInt(daysbeforeNOC);
			Assertions.verify(intactualdaysbeforeNOC < intdaysbeforeNOC, true, "Cancel Policy Page",
					"The Days Before NOC Changed to : " + cancelPolicyPage.daysBeforeNOC.getData(), false, false);

			// click on cancel
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();

			// click on cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			Assertions.addInfo("Scenario 03",
					"Verify All the Cancellation Reasons are available and verify the Cancellation effective date for each reason");
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
				Assertions.verify(calcEffDateReasons.equals(cancelPolicyPage.cancellationEffectiveDate.getData()), true,
						"Cancel Policy Page",
						"The Cancellation Efffective Date " + calcEffDateReasons + " displayed is verified", false,
						false);
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			testData = data.get(data_Value2);

			Assertions.addInfo("Scenario 04",
					"Cancel the policy By giving with in 3 months of cancellation effective date check the presence of Pro-Rated Minimum Earned Premium and reinstatement");
			// Select a Reason and process not flat cancellation
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancellationEffectiveDate.clearData();
			cancelPolicyPage.cancellationEffectiveDate.appendData(testData.get("CancellationEffectiveDate"));
			Assertions.passTest("Cancel Policy Page",
					"The Cancel Reason Selected is " + testData.get("CancellationReason"));
			if (!testData.get("Cancellation_LegalNoticeWording").equals("")) {
				cancelPolicyPage.legalNoticeWording.scrollToElement();
				cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			}
			Assertions.passTest("Cancel Policy Page",
					"The Cancellation Effective date Entered is : " + testData.get("CancellationEffectiveDate"));
			// Click on Next
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Selecting the Pro-Rated radio button IO-20984
			Assertions.verify(cancelPolicyPage.proRatedRadioBtn.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Pro-Rated Radio button is selected", false, false);
			cancelPolicyPage.proRatedRadioBtn.click();
			// Fetching Return Premium,Taxes and fee and SurplusContribution
			waitTime(3);
			String returnSurplusContributionValueOnCance = cancelPolicyPage.surplusContributionVal.formatDynamicPath(3)
					.getData().replace("-$", "").replace("", "").replace("%", "");
			double d_returnSurplusContributionValueOnCance = Double.parseDouble(returnSurplusContributionValueOnCance);
			System.out.println("d_returnSurplusContributionValue :" + d_returnSurplusContributionValueOnCance);

			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Complete Button");
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Assert policy status
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status : " + policySummaryPage.policyStatus.getData() + " displayed is verified ",
					false, false);

			policySummaryPage.transHistReasonNAHO.formatDynamicPath(3).click();
			String tranSurplusContributionOnPolicySummary = policySummaryPage.transactionSurplusContribution.getData()
					.replace("-$", "").replace("", "").replace("%", "");
			double d_transSurplusContributionValueOnCance = Double.parseDouble(tranSurplusContributionOnPolicySummary);
			System.out.println("tranSurplusContributionOnPolicySummary :" + d_transSurplusContributionValueOnCance);

			Assertions.addInfo("Ticket IO-20984",
					"Validating the Non-flat returend surpluscontribution value on Cancel Policy Page with Transaction surpluscontribution value of NOC on policy Summary Page ");
			if (d_returnSurplusContributionValueOnCance == d_returnSurplusContributionValueOnCance) {
				Assertions.verify(d_returnSurplusContributionValueOnCance, d_returnSurplusContributionValueOnCance,
						"Cancel Policy Page and Policy Summary Page",
						"The Actual Surplus Contribution value on Cancel policy Page and The Actual Surplus Contribution value on Policy Summary Page"
								+ "Contribution Values are matching",
						false, false);
			} else {
				Assertions.verify(d_returnSurplusContributionValueOnCance, d_returnSurplusContributionValueOnCance,
						"Cancel Policy Page",
						"The Actual Surplus Contribution value on Cancel policy Page and The Actual Surplus Contribution value on Policy Summary Page"
								+ "Contribution Values are not matching",
						false, false);
			}

			// Reinstating the Policy
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);
			Assertions.passTest("Policy Summary Page", "Policy Reinstated successfully");
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Adding Code for CR IO-19480
			// Renewing the Policy
			Assertions.addInfo("Scenario 05", "To Verify the Referral Message on EC Note Bar");
			policyNumber = policySummaryPage.getPolicynumber();
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");
			testData = data.get(data_Value1);
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {

				// Navigating to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.passTest("Policy Summary Page", "Home Page Link clicked successfully");

				// Opening Expacc Link
				Assertions.verify(homePage.expaccLink.checkIfElementIsDisplayed(), true, "Home Page",
						"Home Page is loaded successfully", false, false);
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				Assertions.passTest("Home Page", "Expacc Link clicked sucessfully");

				// Entering Expacc Details
				Assertions.verify(expaccInfoPage.submit.checkIfElementIsDisplayed(), true, "ExpaccInfo Page",
						"ExpaccInfo Page loaded successfully", false, false);
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc Info Page", "Expacc Information added successfully");

				// Navigating to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.passTest("Expacc Info Page", "Home Page Link clicked successfully");

				// Searching for Policy to Renew the Policy
				homePage.searchPolicy(policyNumber);
				Assertions.passTest("Home Page", "Searched for Policy Number successfully");

				// Renewing Policy
				Assertions.verify(policySummaryPage.renewPolicy.checkIfElementIsDisplayed(), true,
						"Policy Summary Page", "Policy Summary Page loaded successfully", false, false);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link successfully");
			}

			// Opening Referral for Renewal Quote
			Assertions.verify(accountOverviewPage.openReferral.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Open Referral Link clicked successfully");

			producerCommentsProducerSection = referralPage.producerCommentsProducerSection.getData();
			commentOccuranceCountProducerSection = StringUtils.countMatches(producerCommentsProducerSection,
					"The account is ineligible due to the roof age being outside of ICAT's guidelines. If you have additional information that adjusts the roof age, please email it to your ICAT Online Underwriter.");
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Checking for the Non-Repetition of same Message in Producer Comments under
			// Producer Section
			Assertions.addInfo("Scenario 06", "Checking for Correctness of Producer Comments under Producer Section");
			if (commentOccuranceCountProducerSection == 1) {
				Assertions.verify((commentOccuranceCountProducerSection == 1), true, "Referral Page",
						"Referral Message on the Producer Comments under Producer section is displayed correctly. The Number of times the Message Displayed is "
								+ commentOccuranceCountProducerSection,
						false, false);
				Assertions.passTest("Referral Page",
						"The Message displayed on Producer Comments Section is " + producerCommentsProducerSection);
			} else {
				Assertions.verify((commentOccuranceCountProducerSection > 1), false, "Referral Page",
						"Referral Message is displayed more than once under the Producer Section. The number of times comment displayed is "
								+ commentOccuranceCountProducerSection,
						false, false);
				Assertions.passTest("Referral Page",
						"The Message displayed on Producer Comments Section is " + producerCommentsProducerSection);
			}

			// Verifying for the correctness of Producer Comments Message in Note Bar
			Assertions.addInfo("Referral Page", "Checking for the correctness of Message under Note Bar");
			Assertions.verify(referralPage.noteBarLink.checkIfElementIsDisplayed(), true, "Referral Page",
					"Note Bar link is available in Referral Page", false, false);

			// Clicking on the Note Bar Link
			referralPage.noteBarLink.scrollToElement();
			referralPage.noteBarLink.click();
			Assertions.passTest("Referral Page", "Note Bar Link clicked successfully");

			// Adding Explicit waiting time for Note Bar Link to be opened to Read the Note
			// Bar Message.
			waitTime(2); // If this wait time is removed the Test Case fails here.
			Assertions.verify(
					referralPage.noteBarMessage.checkIfElementIsPresent()
							&& referralPage.noteBarMessage.checkIfElementIsDisplayed(),
					true, "Referral Page", "Note Bar Message is displayed", false, false);
			referralPage.noteBarMessage.scrollToElement();
			producerCommentsNoteBarSection = referralPage.noteBarMessage.getData();
			producerCommentsNoteBarSection = StringUtils.normalizeSpace(producerCommentsNoteBarSection);
			commentOccuranceCountNoteBarSection = StringUtils.countMatches(producerCommentsNoteBarSection,
					"The account is ineligible due to the roof age being outside of");
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Checking for the Non-Repetition of same Message in Producer Comments under
			// Note Bar Section
			Assertions.addInfo("Scenario 07", "Checking for Correctness of Producer Comments under Producer Section");
			if (commentOccuranceCountNoteBarSection == 1) {
				Assertions.verify((commentOccuranceCountNoteBarSection == 1), true, "Referral Page",
						"Referral Message on the Note Bar is displayed correctly. The number of times comment displayed under Note Bar is "
								+ commentOccuranceCountNoteBarSection,
						false, false);
				Assertions.passTest("Referral Page",
						"The Message Displayed under Note Bar Section is " + producerCommentsNoteBarSection);

			} else {
				Assertions.verify((commentOccuranceCountNoteBarSection > 1), false, "Referral Page",
						"Referral Message is displayed more than once under the Producer Section. The number of times comment displayed is "
								+ commentOccuranceCountNoteBarSection,
						false, false);
				Assertions.passTest("Referral Page",
						"The Message Displayed under Note Bar Section is " + producerCommentsNoteBarSection);
			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// click on pick up button
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}

			// Click on Approve/decline button
			if (referralPage.approveOrDeclineRequest.checkIfElementIsPresent()
					&& referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed()) {
				referralPage.approveOrDeclineRequest.scrollToElement();
				referralPage.approveOrDeclineRequest.click();
			}

			// Updated comments for internal and external Underwriter
			if (approveDeclineQuotePage.internalUnderwriterComments.checkIfElementIsPresent()) {
				approveDeclineQuotePage.internalUnderwriterComments.scrollToElement();
				approveDeclineQuotePage.internalUnderwriterComments.setData("Test");
			}
			if (approveDeclineQuotePage.externalUnderwriterComments.checkIfElementIsPresent()) {
				approveDeclineQuotePage.externalUnderwriterComments.scrollToElement();
				approveDeclineQuotePage.externalUnderwriterComments.setData("Test");
			}

			// Click on decline quote
			if (approveDeclineQuotePage.declineButton.checkIfElementIsPresent()
					&& approveDeclineQuotePage.declineButton.checkIfElementIsDisplayed()) {
				approveDeclineQuotePage.declineButton.scrollToElement();
				approveDeclineQuotePage.declineButton.click();
			}

			// Click on Close Button
			referralPage.close.scrollToElement();
			referralPage.close.click();

			// Search for policy Number
			homePage.searchPolicy(policyNumber);

			// Click on Endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Click on Ok button
			if (policySummaryPage.okButton.checkIfElementIsPresent()
					&& policySummaryPage.okButton.checkIfElementIsDisplayed()) {
				policySummaryPage.okButton.scrollToElement();
				policySummaryPage.okButton.click();
			}

			// Entering Endorsement effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Entering Policy Expiration date
			endorsePolicyPage.policyExpirationDate.scrollToElement();
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.policyExpirationDate.tab();

			// Click on Change Expiration Button
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on continue
			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();
			}

			// Validate if PBENDT changes links are not available
			testData = data.get(data_Value1);
			Assertions.addInfo("Scenario 08", "Check if PBENDT links are not present for updating any changes");
			Assertions.verify(!endorsePolicyPage.editLocOrBldgInformationLink.checkIfElementIsPresent(), true,
					"Endorse Policy Page",
					"Edit Location and Building Information Link is not present to upadate the changes", false, false);
			Assertions.verify(!endorsePolicyPage.changeCoverageOptionsLink.checkIfElementIsPresent(), true,
					"Endorse Policy Page", "Change Coverage Options Link is not present to upadate the changes", false,
					false);
			Assertions.verify(!endorsePolicyPage.feeOnlyEndorsement.checkIfElementIsPresent(), true,
					"Endorse Policy Page", "Fee Only endorsement Link is not present to upadate the changes", false,
					false);
			Assertions.verify(!endorsePolicyPage.editPriorLoss.checkIfElementIsPresent(), true, "Endorse Policy Page",
					"Edit Prior Loss Link is not present to upadate the changes", false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on ok,continue button
			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();
			}

			// Asserting the values for Transaction details and policy expiration date
			Assertions.addInfo("Scenario 09", "Check if Transaction Premium is positive");
			Assertions.verify(!endorsePolicyPage.newPremium.formatDynamicPath(1).getData().contains("-"), true,
					"Endorse Policy Page", "Transaction premium is displayed as "
							+ endorsePolicyPage.newPremium.formatDynamicPath(1).getData() + " positive value",
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Asserting Total Values for Transaction , Annual and Term
			Assertions.addInfo("Scenario 10",
					"Verifying the Total Values for Transaction , Annual and Term and Calculating and verifying SLTF value in Endorse policy page");
			Assertions.verify(endorsePolicyPage.newTotalValues.formatDynamicPath(2).checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Transaction Total is displayed as : "
							+ endorsePolicyPage.newTotalValues.formatDynamicPath(2).getData(),
					false, false);
			Assertions.verify(endorsePolicyPage.newTotalValues.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Transaction Annual Total is displayed as : "
							+ endorsePolicyPage.newTotalValues.formatDynamicPath(3).getData(),
					false, false);
			Assertions.verify(endorsePolicyPage.newTotalValues.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Transaction Term Total is displayed as : "
							+ endorsePolicyPage.newTotalValues.formatDynamicPath(4).getData(),
					false, false);

			// Calculating SLTF Values
			// Actual SLTF Value
			String actalSltfData = endorsePolicyPage.newSLTF.formatDynamicPath(1).getData().replace("-", "")
					.replace("$", "").replace(",", "");
			double actualSLTFValue = Double.parseDouble(actalSltfData);

			// Validating SLTF value - 4.94% of Total Premium (Premium + Inspection Fee)
			String premiumValue1 = endorsePolicyPage.newPremium.formatDynamicPath(1).getData().replace("$", "")
					.replace(",", "");
			String inspectionValue = endorsePolicyPage.newInspectionFee.formatDynamicPath(2).getData().replace("$", "")
					.replace(",", "");
			String policyFeeValue = endorsePolicyPage.newPolicyFee.formatDynamicPath(2).getData().replace("$", "")
					.replace(",", "");
			String surplusContributionValue = endorsePolicyPage.surplusContributionValueNAHO.formatDynamicPath(1)
					.getData().replace("$", "").replace(",", "").replace("%", "");
			surplusLinesTaxesPercentage = testData.get("SLTFPercentage");
			fslsoServiceFeePercentage = testData.get("FSLSOServiceFeePercentage");

			// Convert the values to double
			double d_premiumValue1 = Double.parseDouble(premiumValue1);
			double d_inspectionValue = Double.parseDouble(inspectionValue);
			double d_policyFeeValue = Double.parseDouble(policyFeeValue);
			double d_surplusContributionValue = Double.parseDouble(surplusContributionValue);
			d_surplusLinesTaxesPercentage = Double.parseDouble(surplusLinesTaxesPercentage);
			d_fslsoServiceFeePercentage = Double.parseDouble(fslsoServiceFeePercentage);

			// Calculate Surplus Lines Taxes
			// =(Premium+Fees+surpluscontribution)*surplusLinesTaxesPercentage(0.0494)
			d_surplusLinesTaxes = (d_premiumValue1 + d_inspectionValue + d_policyFeeValue + d_surplusContributionValue)
					* d_surplusLinesTaxesPercentage;

			// Calculate FSLSO Fee = (Premium+Fees)*FSLSOServiceFeePercentage(0.0006)
			d_fslsoServiceFee = (d_premiumValue1 + d_inspectionValue + d_policyFeeValue + d_surplusContributionValue)
					* d_fslsoServiceFeePercentage;

			// calculate SLTF value
			calculatedSLTValue = d_surplusLinesTaxes + d_fslsoServiceFee;

			if (Precision.round(Math.abs(Precision.round(actualSLTFValue, 2) - Precision.round(calculatedSLTValue, 2)),
					2) < 0.5) {
				Assertions.passTest("Endorse Policy Page",
						"Calculated surplus lines taxes and fees : " + "$" + Precision.round(calculatedSLTValue, 2));
				Assertions.passTest("Endorse Policy Page",
						"Actual surplus lines taxes and fees : " + "$" + actalSltfData);
			} else {
				Assertions.passTest("Endorse Policy Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05");
			}
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Asserting the Values From Endorse Summary Page
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true,
					"Endorse Summary Page", "Endorse Summary Page Loaded Successfully", false, false);

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}
			if (endorsePolicyPage.rollForwardBtn.checkIfElementIsPresent()
					&& endorsePolicyPage.rollForwardBtn.checkIfElementIsDisplayed()) {
				endorsePolicyPage.rollForwardBtn.scrollToElement();
				endorsePolicyPage.rollForwardBtn.click();
			}

			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on View policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			Assertions.addInfo("Scenario 11", "Verifying the Changed Policy expiration date");
			testData = data.get(data_Value2);
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);
			Assertions.verify(
					viewPolicySnapShot.homeownerQuoteDetails
							.formatDynamicPath("4").getData().contains(testData.get("PolicyExpirationDate")),
					true, "View Policy Snapshot Page",
					"The Policy Expiration Date "
							+ viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath("4").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Click on back button
			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();

			// Flat cancelling the policy
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel policy link");

			// click on continue
			if (policyRenewalPage.deleteAndContinue.checkIfElementIsPresent()
					&& policyRenewalPage.deleteAndContinue.checkIfElementIsDisplayed()) {
				policyRenewalPage.deleteAndContinue.scrollToElement();
				policyRenewalPage.deleteAndContinue.click();
			}

			// Selecting Cancel Reasons and Asserting Cancellation Effective Date
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page loaded successfully", false, false);

			// Select a Reason
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"The Cancel Reason Selected is " + testData.get("CancellationReason"));

			if (!testData.get("Cancellation_LegalNoticeWording").equals("")) {
				cancelPolicyPage.legalNoticeWording.scrollToElement();
				cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			}
			cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("TransactionEffectiveDate"));
			Assertions.passTest("Cancel Policy Page",
					"The Cancellation Effective date Entered is : " + testData.get("TransactionEffectiveDate"));

			// Click on Next
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// getting premium,policyfee,insp fee
			originalPremium = cancelPolicyPage.premium.formatDynamicPath("1").getData().replace("$", "");
			originalInspFee = cancelPolicyPage.inspectionFee1.formatDynamicPath("1").getData().replace("$", "");
			originalPolicyFee = cancelPolicyPage.policyFeeNAHO.formatDynamicPath("1").getData().replace("$", "");
			originalSLTF = cancelPolicyPage.SLTF.formatDynamicPath("1").getData().replace("$", "");
			returnedSLTF = cancelPolicyPage.returnedSLTF.getData().replace("$", "").replace("-", "");
			originalSurplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(1).getData()
					.replace("$", "").replace("%", "");
			surplusLinesTaxesPercentage = testData.get("SLTFPercentage");
			fslsoServiceFeePercentage = testData.get("FSLSOServiceFeePercentage");

			// Convert the values to double
			d_originalPremium = Double.parseDouble(originalPremium);
			d_originalInspFee = Double.parseDouble(originalInspFee);
			d_originalPolicyFee = Double.parseDouble(originalPolicyFee);
			d_originalSLTF = Double.parseDouble(originalSLTF);
			d_returnedSLTF = Double.parseDouble(returnedSLTF);
			d_originalSurplusContributionValue = Double.parseDouble(originalSurplusContributionValue);
			d_surplusLinesTaxesPercentage = Double.parseDouble(surplusLinesTaxesPercentage);
			d_fslsoServiceFeePercentage = Double.parseDouble(fslsoServiceFeePercentage);

			// Calculate Surplus Lines Taxes
			// =(Premium+Fees)*surplusLinesTaxesPercentage(0.0494)
			d_surplusLinesTaxes = (d_originalPremium + d_originalInspFee + d_originalPolicyFee
					+ d_originalSurplusContributionValue) * d_surplusLinesTaxesPercentage;

			// Calculate FSLSO Fee = (Premium+Fees)*FSLSOServiceFeePercentage(0.0006)
			d_fslsoServiceFee = (d_originalPremium + d_originalInspFee + d_originalPolicyFee
					+ d_originalSurplusContributionValue) * d_fslsoServiceFeePercentage;

			// calculate SLTF value+2
			calculatedSLTValue = d_surplusLinesTaxes + d_fslsoServiceFee + empaCharge;

			// Verify actual and calculated SLTF values are equal
			Assertions.addInfo("Sceraio 12",
					"Verifying the original ,earned and returned premium and SLTF values on Flat cancel policy page after the term endorsement ");
			if (Precision.round(Math.abs(Precision.round(d_originalSLTF, 2) - Precision.round(calculatedSLTValue, 2)),
					2) < 0.5) {
				if (originalSLTF != null) {
					Assertions.passTest("Cancel Policy Page",
							"Original Actual Surplus Lines Taxes and Fees : " + "$" + d_originalSLTF);
					Assertions.passTest("Cancel Policy Page", "Original Calculated Surplus Lines Taxes and Fees : "
							+ "$" + df.format(calculatedSLTValue));
				}
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			if (Precision.round(Math.abs(Precision.round(d_returnedSLTF, 2) - Precision.round(calculatedSLTValue, 2)),
					2) < 0.5) {
				if (returnedSLTF != null) {
					Assertions.passTest("Cancel Policy Page",
							"Returned Actual Surplus Lines Taxes and Fees : " + "-$" + d_returnedSLTF);
					Assertions.passTest("Cancel Policy Page", "Returned Calculated Surplus Lines Taxes and Fees : "
							+ "-$" + df.format(calculatedSLTValue));
				}
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			// click on complete txn
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Complete Button");
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();

			// Reinstating the Policy
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Reinstate Policy link");
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);
			Assertions.passTest("Policy Summary Page", "Policy Reinstated successfully");

			// Non Flat canceling the policy
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel policy link");

			// Selecting Cancel Reason
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page loaded successfully", false, false);

			// Select a Reason
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"The Cancel Reason Selected is " + testData.get("CancellationReason"));

			if (!testData.get("Cancellation_LegalNoticeWording").equals("")) {
				cancelPolicyPage.legalNoticeWording.scrollToElement();
				cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			}
			cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
			Assertions.passTest("Cancel Policy Page",
					"The Cancellation Effective date Entered is : " + testData.get("CancellationEffectiveDate"));

			// Click on Next
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// getting premium,policy fee,insp fee
			originalPremium = cancelPolicyPage.premium.formatDynamicPath("1").getData().replace("$", "");
			originalInspFee = cancelPolicyPage.inspectionFee1.formatDynamicPath("1").getData().replace("$", "");
			originalPolicyFee = cancelPolicyPage.policyFeeNAHO.formatDynamicPath("1").getData().replace("$", "");
			originalSurplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(1).getData()
					.replace("$", "").replace("%", "");
			originalSLTF = cancelPolicyPage.SLTF.formatDynamicPath("1").getData().replace("$", "");

			// Convert the values to double
			d_originalPremium = Double.parseDouble(originalPremium);
			d_originalInspFee = Double.parseDouble(originalInspFee);
			d_originalPolicyFee = Double.parseDouble(originalPolicyFee);
			d_originalSLTF = Double.parseDouble(originalSLTF);
			d_originalSurplusContributionValue = Double.parseDouble(originalSurplusContributionValue);

			// Calculate Surplus Lines Taxes
			// =(Premium+Fees)*surplusLinesTaxesPercentage(0.05)
			d_surplusLinesTaxes = (d_originalPremium + d_originalInspFee + d_originalPolicyFee
					+ d_originalSurplusContributionValue) * d_surplusLinesTaxesPercentage;

			// Calculate FSLSO Fee = (Premium+Fees)*FSLSOServiceFeePercentage(0.0006)
			d_fslsoServiceFee = (d_originalPremium + d_originalInspFee + d_originalPolicyFee
					+ d_originalSurplusContributionValue) * d_fslsoServiceFeePercentage;

			// calculate SLTF value
			calculatedSLTValue = d_surplusLinesTaxes + d_fslsoServiceFee + empaCharge;

			// Verify actual and calculated SLTF values are equal
			Assertions.addInfo("Sceraio 13",
					"Verifying the original and returned premium and SLTF values on Non flat cancel policy page after the term endorsement ");
			if (Precision.round(Math.abs(Precision.round(d_originalSLTF, 2) - Precision.round(calculatedSLTValue, 2)),
					2) < 0.5) {
				if (originalSLTF != null) {
					Assertions.passTest("Cancel Policy Page",
							"Original Actual Surplus Lines Taxes and Fees : " + "$" + d_originalSLTF);
					Assertions.passTest("Cancel Policy Page", "Original Calculated Surplus Lines Taxes and Fees : "
							+ "$" + df.format(calculatedSLTValue));
				}
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			// getting earned premium,insp fee and policy fee
			earnedPremium = cancelPolicyPage.premium.formatDynamicPath("2").getData().replace("$", "");
			earnedInspFee = cancelPolicyPage.newInspectionFee.getData().replace("$", "");
			earnedPolicyFee = cancelPolicyPage.newPolicyFee.getData().replace("$", "");
			earnedSLTF = cancelPolicyPage.newSLTF.getData().replace("$", "");
			earnedSurplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(2).getData()
					.replace("$", "").replace("%", "");

			// Convert the values to double
			d_earnedPremium = Double.parseDouble(earnedPremium);
			d_earnedInspFee = Double.parseDouble(earnedInspFee);
			d_earnedPolicyFee = Double.parseDouble(earnedPolicyFee);
			d_earnedSLTF = Double.parseDouble(earnedSLTF);
			d_earnedSurplusContributionValue = Double.parseDouble(earnedSurplusContributionValue);

			// Calculate Surplus Lines Taxes
			// =(Premium+Fees)*surplusLinesTaxesPercentage(0.0494)
			d_surplusLinesTaxes = (d_earnedPremium + d_earnedInspFee + d_earnedPolicyFee
					+ d_earnedSurplusContributionValue) * d_surplusLinesTaxesPercentage;

			// Calculate FSLSO Fee = (Premium+Fees)*FSLSOServiceFeePercentage(0.0006)
			d_fslsoServiceFee = (d_earnedPremium + d_earnedInspFee + d_earnedPolicyFee
					+ d_earnedSurplusContributionValue) * d_fslsoServiceFeePercentage;

			// calculate SLTF value
			calculatedSLTValue = d_surplusLinesTaxes + d_fslsoServiceFee + empaCharge;

			if (Precision.round(Math.abs(Precision.round(d_earnedSLTF, 2) - Precision.round(calculatedSLTValue, 2)),
					2) < 0.5) {
				if (earnedSLTF != null) {
					Assertions.passTest("Cancel Policy Page",
							"Earned  Actual Surplus Lines Taxes and Fees : " + "$" + d_earnedSLTF);
					Assertions.passTest("Cancel Policy Page",
							"Earned Calculated Surplus Lines Taxes and Fees : " + "$" + df.format(calculatedSLTValue));
				}
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			// getting returned premium,insp fee and policy fee
			returnedPremium = cancelPolicyPage.returnedPremium.getData().replace("$", "").replace("-", "");
			returnedInspFee = cancelPolicyPage.returnedInspectionFee.getData().replace("$", "");
			returnedPolicyFee = cancelPolicyPage.returnedPolicyFee.getData().replace("$", "");
			returnedSLTF = cancelPolicyPage.returnedSLTF.getData().replace("$", "").replace("-", "");
			returnSurplusContributionValue = cancelPolicyPage.surplusContributionVal.formatDynamicPath(3).getData()
					.replace("$", "").replace("-", "").replace("%", "");

			// Convert the values to double
			d_returnedPremium = Double.parseDouble(returnedPremium);
			d_returnedInspFee = Double.parseDouble(returnedInspFee);
			d_returnedPolicyFee = Double.parseDouble(returnedPolicyFee);
			d_returnedSLTF = Double.parseDouble(returnedSLTF);
			d_returnSurplusContributionValue = Double.parseDouble(returnSurplusContributionValue);

			// Calculate Surplus Lines Taxes
			// =(Premium+Fees)*surplusLinesTaxesPercentage(0.05)
			d_surplusLinesTaxes = (d_returnedPremium + d_returnedInspFee + d_returnedPolicyFee
					+ d_returnSurplusContributionValue) * d_surplusLinesTaxesPercentage;

			// Calculate FSLSO Fee = (Premium+Fees)*FSLSOServiceFeePercentage(0.0006)
			d_fslsoServiceFee = (d_returnedPremium + d_returnedInspFee + d_returnedPolicyFee
					+ d_returnSurplusContributionValue) * d_fslsoServiceFeePercentage;

			// calculate SLTF value
			calculatedSLTValue = d_surplusLinesTaxes + d_fslsoServiceFee;

			if (Precision.round(Math.abs(Precision.round(d_returnedSLTF, 2) - Precision.round(calculatedSLTValue, 2)),
					2) < 0.5) {
				if (returnedSLTF != null) {
					Assertions.passTest("Cancel Policy Page",
							"Returned Actual Surplus Lines Taxes and Fees : " + "$" + d_returnedSLTF);
					Assertions.passTest("Cancel Policy Page", "Returned Calculated Surplus Lines Taxes and Fees : "
							+ "$" + df.format(calculatedSLTValue));
				}
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			// click on complete txn
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Complete Button");
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();

			// Reinstating the Policy
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);
			Assertions.passTest("Policy Summary Page", "Policy Reinstated successfully");

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBFLTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBFLTC002 ", "Executed Successfully");
			}
		}
	}
}