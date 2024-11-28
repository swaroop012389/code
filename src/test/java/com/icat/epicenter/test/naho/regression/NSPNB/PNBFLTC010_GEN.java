/** Program Description: Complete the Endt with Policy Exp date change. Then, create other additional PB/NPB Endts on the same policy and check on the behaviour
 *  1. Create the NB policy.
2. Create the Endt with the Exp. Date extension and complete it.
3. On the same policy, just create a Renewal but do not release it to producer.Check if the Renewal Account is having the previuosly updated Exp. Date as Renewal Requested Effective Date.
 *
 *  Author			   : Sowndarya
 *  Date of Creation   : 27/10/2022
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBFLTC010_GEN extends AbstractNAHOTest {

	public PNBFLTC010_GEN() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/FL010.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ReferralPage referralPage = new ReferralPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();

		// initializing variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int quoteLen;
		String quoteNumber;
		String policyNumber;
		String policyExpirationDate;
		int policyExpirationDateLen;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Create account as producer
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page ",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account ", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page ",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page ", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page ",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page ", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page ",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Enter quote details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Bind button on request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");

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
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Approve bind request
			if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
					&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchReferral(quoteNumber);
				referralPage.clickOnApprove();
				requestBindPage.approveRequestNAHO(testData);
			}

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfuly", false, false);
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);

			// Initiating endt to verify the changed policy expiration date
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Enter Endorsement Eff date as policy eff date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// enter policy expiration date
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.policyExpirationDate.tab();
			Assertions.passTest("Endorse Policy Page",
					"Entered the Policy Expiration Date as " + endorsePolicyPage.policyExpirationDate.getData());
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on change expiration date link successfully");

			// click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next Button");

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete button");

			// click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on first endorsement transaction
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).click();
			Assertions.passTest("Policy Summary Page", "Clicked on first endorsement link");

			// Click on View policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			policyExpirationDateLen = viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath("4").getData()
					.length();
			policyExpirationDate = viewPolicySnapShot.homeownerQuoteDetails.formatDynamicPath("4").getData()
					.substring(10, policyExpirationDateLen - 23);

			Assertions.addInfo("Scenario 01", "Verifying the Changed Policy expiration date,Premium and fee details");
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);
			Assertions.verify(policyExpirationDate.contains(testData.get("PolicyExpirationDate")), true,
					"View Policy Snapshot Page",
					"The Policy Expiration Date " + policyExpirationDate + " displayed is verified", false, false);
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("1").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Premium   displayed is "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("1").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("2").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Policy Fee displayed is "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("2").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("3").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Inspection Fee displayed is  "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("3").getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on back button
			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();

			// Initiating endt to change coverage
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Enter Endorsement Eff date as policy eff date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on Change Coverage Option link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// Entering Optional Coverage details
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded Successfully", false, false);
			testData = data.get(data_Value2);
			createQuotePage.enterDeductiblesNAHO(testData);

			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			// click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next Button");

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete button");

			// click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on transaction history
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).waitTillButtonIsClickable(2);
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).click();
			Assertions.passTest("Policy Summary Page", "Clicked on second endorsement link");

			// Click on View policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			Assertions.addInfo("Scenario 02", "Verifying the Changed Policy expiration date");
			testData = data.get(data_Value1);
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);
			Assertions.verify(policyExpirationDate.contains(testData.get("PolicyExpirationDate")), true,
					"View Policy Snapshot Page",
					"The Policy Expiration Date " + policyExpirationDate + " displayed is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on back button
			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();

			// Initiating endt to change coverage
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse NPB link");

			// Enter Endorsement Eff date as policy eff date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// click on change inspection contact link
			endorsePolicyPage.changeInspectionContactLink.scrollToElement();
			endorsePolicyPage.changeInspectionContactLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change inspection contact link");

			Assertions.verify(endorseInspectionContactPage.okButton.checkIfElementIsDisplayed(), true,
					"Endorse Inspection Contact Page", "Endorse Inspection Contact Page loaded successfully", false,
					false);
			testData = data.get(data_Value2);
			endorseInspectionContactPage.enterInspectionContactPB(testData);

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete button");

			// click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Clicked on transaction history
			policySummaryPage.transHistReason.formatDynamicPath(3).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(3).click();

			// Click on View policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			Assertions.addInfo("Scenario 03", "Verifying the Changed Policy expiration date");
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);
			Assertions.verify(policyExpirationDate.contains(testData.get("PolicyExpirationDate")), true,
					"View Policy Snapshot Page",
					"The Policy Expiration Date " + policyExpirationDate + " displayed is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on back button
			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();

			// clicking on renewal policy link
			testData = data.get(data_Value1);
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

				// Search the policy
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");
			}

			// Assert renewal quote number
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote Number is : " + quoteNumber);

			// Verifying the Requested Effective Date on the renewal quote
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.passTest("Account Overview Page", "Clicked on Quote Specifics link");
			Assertions.addInfo("Scenario 04", "Verifying the Requested Effective Date on the renewal quote");
			Assertions.verify(
					accountOverviewPage.requestedEffectiveDate.getData().contains(testData.get("PolicyExpirationDate")),
					true, "Account Overview Page", "The Requested Effective Date "
							+ accountOverviewPage.requestedEffectiveDate.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBFLTC010 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBFLTC010 ", "Executed Successfully");
			}
		}
	}
}
