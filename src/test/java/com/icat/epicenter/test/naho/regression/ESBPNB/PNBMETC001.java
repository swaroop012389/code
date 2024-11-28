/** Program Description: Initiating NPB endorsement and verifying OOS Transaction for Maine State
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 30/12/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.ChangePaymentPlanPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseAdditionalInterestsPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBMETC001 extends AbstractNAHOTest {

	public PNBMETC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/METC001.xls";
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
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();
		ChangePaymentPlanPage changePaymentPlanPage = new ChangePaymentPlanPage();
		EndorseAdditionalInterestsPage endoreseAdditionalInterestsPage = new EndorseAdditionalInterestsPage();

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int quoteLen;
		String quoteNumber;
		Map<String, String> testData = data.get(data_Value1);
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

			// Getting the Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.waitTillButtonIsClickable(60);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

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

			// Validating the premium amount
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on NPB endorsement link
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			Assertions.passTest("Policy Summaery Page", "Clicked on Endorse NPB link");

			// Entering Endorsement effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on Change Named Insured/Mailing address Link
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();
			Assertions.passTest("Endorse Policy Page",
					"Clicked on Change Named Insured/Mailing Address/Contact Information link");

			// Changing the Insured Email
			Assertions.verify(changeNamedInsuredPage.okButton.checkIfElementIsDisplayed(), true,
					"Change Named Insured/Mailing Address", "Change Named Insured/Mailing Address loaded successfully",
					false, false);
			changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);

			// Click on Change Payment plan link
			endorsePolicyPage.changePaymentPlanLink.scrollToElement();
			endorsePolicyPage.changePaymentPlanLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Payment Plan link");
			Assertions.verify(changePaymentPlanPage.okButton.checkIfElementIsDisplayed(), true,
					"Change Payment Plan Page", "Change Payment Plan Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 01", "Verifying Mortgagee Full Pay Is Selected or not");
			Assertions.verify(changePaymentPlanPage.mortgageeFullPay.checkIfElementIsSelected(), true,
					"Change Payment Plan Page", "Mortgagee Full Pay Selected is verified", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on ok
			changePaymentPlanPage.okButton.scrollToElement();
			changePaymentPlanPage.okButton.click();

			// Click on Change Additional Interest link
			endorsePolicyPage.changeAIInformationLink.scrollToElement();
			endorsePolicyPage.changeAIInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Additional Interest Information link");
			Assertions.verify(endoreseAdditionalInterestsPage.okButton.checkIfElementIsDisplayed(), true,
					"Endorse Additional Interests Page", "Endorse Additional Interests Page Loaded successfully", false,
					false);

			// Deleting the added AI
			endoreseAdditionalInterestsPage.deleteAdditionalInterestPB(testData);
			testData = data.get(data_Value1);
			Assertions.passTest("Endorse Additional Interest Page",
					"The Additional Interest " + testData.get("1-AIType") + " is Deleted");

			// Click on ok button
			endoreseAdditionalInterestsPage.okButton.scrollToElement();
			endoreseAdditionalInterestsPage.okButton.click();
			Assertions.passTest("Endorse Additional Interest Page", "Clicked on Ok Button");

			// Click on Complete Button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// Adding Scenario 02
			Assertions.addInfo("Scenario 02",
					"Verifying the Error Message when Mortgagee AI is deleted and Mortgagee payment plan is Selected");
			Assertions.verify(endorsePolicyPage.paymentPlanError.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page",
					"The Error Message " + endorsePolicyPage.paymentPlanError.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on Change Payment plan link
			endorsePolicyPage.changePaymentPlanLink.scrollToElement();
			endorsePolicyPage.changePaymentPlanLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Payment Plan link");
			Assertions.verify(changePaymentPlanPage.okButton.checkIfElementIsDisplayed(), true,
					"Change Payment Plan Page", "Change Payment Plan Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 03", "Verifying the absence of Mortgagee-Full Pay option");
			Assertions.verify(changePaymentPlanPage.mortgageeFullPay.checkIfElementIsPresent(), false,
					"Change Payment Plan Page", "Mortgagee Full Pay option not displayed is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// select insured full pay
			changePaymentPlanPage.insuredFullPay.scrollToElement();
			changePaymentPlanPage.insuredFullPay.click();
			Assertions.passTest("Change Payment Plan Page", "The Latest Payment Option selected is Insured Full Pay");

			// click on ok
			changePaymentPlanPage.okButton.scrollToElement();
			changePaymentPlanPage.okButton.click();
			Assertions.passTest("Change Payment Plan Page", "Clicked on Ok button");

			// Verify the changes under endorsement summary
			Assertions.addInfo("Scenario 04", "Verifying all the Changes made during NPB Endorsement");
			String paymentPlanFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData();
			String paymentPlanTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Payment Plan " + paymentPlanFrom + " changed to : " + paymentPlanTo + " displayed is verified",
					false, false);
			String entityFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(11).getData();
			String entityTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(12).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(10).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Entity " + entityFrom + " changed to : " + entityTo + " displayed is verified", false, false);
			String emailFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(16).getData();
			String emailTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(17).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(15).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Email " + emailFrom + " changed to : " + emailTo + " displayed is verified", false, false);
			String phoneNumFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(21).getData();
			String phoneNumTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(22).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(20).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Phone NUmber " + phoneNumFrom + " changed to : " + phoneNumTo + " displayed is verified",
					false, false);
			String AIFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(28).getData();
			String AITo = endorsePolicyPage.endorsementSummary.formatDynamicPath(29).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(20).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Additional Interest " + AIFrom + " changed to : " + AITo + " displayed is verified", false,
					false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on Complete Button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// click on close
			endorsePolicyPage.scrollToTopPage();
			endorsePolicyPage.waitTime(3);// need wait time to load the page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Close Button");

			// Click on NPB endorsement link
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			Assertions.passTest("Policy Summaery Page", "Clicked on Endorse NPB link");

			// Entering Endorsement effective date
			testData = data.get(data_Value3);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Asserting the OOS message
			Assertions.addInfo("Scenario 05", "Verifying the Presence of Out Of Sequence Message");
			Assertions.verify(endorsePolicyPage.conflictTxn.checkIfElementIsDisplayed(), true,
					"Out Of Sequesnce Transaction",
					"The Message " + endorsePolicyPage.conflictTxn.getData() + " displayed is verified", false, false);
			Assertions.verify(endorsePolicyPage.oosMsg.checkIfElementIsDisplayed(), true,
					"Out Of Sequesnce Transaction",
					"The Message " + endorsePolicyPage.oosMsg.getData() + " displayed is verified", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// click on continue
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Continue Button");

			// click on change additional Interest link
			endorsePolicyPage.changeAIInformationLink.scrollToElement();
			endorsePolicyPage.changeAIInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Additional Interest Information link");
			Assertions.verify(endoreseAdditionalInterestsPage.okButton.checkIfElementIsDisplayed(), true,
					"Endorse Additional Interest Page", "Endorse Additional Interest Page Loaded successfully", false,
					false);
			endoreseAdditionalInterestsPage.enterEndorsementAdditionalInterestDetailsNAHO(testData,
					"Residential Non-Admitted");
			Assertions.passTest("Endorse Additional Interest Page",
					"The Additional Interest " + testData.get("2-AIType") + " Added successfully");

			// Click on Complete Button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// Asserting the OOS message
			Assertions.addInfo("Scenario 06", "Verifying the Presence of Out Of Sequence Message");
			Assertions.verify(endorsePolicyPage.conflictTxn.checkIfElementIsDisplayed(), true,
					"Out Of Sequesnce Transaction",
					"The Message " + endorsePolicyPage.conflictTxn.getData() + " displayed is verified", false, false);
			Assertions.verify(endorsePolicyPage.oosMsg.checkIfElementIsDisplayed(), true,
					"Out Of Sequesnce Transaction",
					"The Message " + endorsePolicyPage.oosMsg.getData() + " displayed is verified", false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// click on continue
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Continue Button");

			// click on close
			endorsePolicyPage.scrollToTopPage();
			endorsePolicyPage.waitTime(3);// need wait time to load the page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Close Button");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBMETC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBMETC001 ", "Executed Successfully");
			}
		}
	}
}
