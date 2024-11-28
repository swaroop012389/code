/** Program Description: Performing various validations on NAHO Term Endorsement policy
 *  Author			   : Pavan Mule
 *  Date of Creation   : 26/10/2022
 **/

package com.icat.epicenter.test.naho.regression.NSPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBFLTC012_GEN extends AbstractNAHOTest {

	public PNBFLTC012_GEN() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/FL012.xls";
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
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		LoginPage loginPage = new LoginPage();

		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value4 = 3;
		int data_Value5 = 4;
		String policyNumber;
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
			// accountOverviewPage.requestBind.scrollToElement();
			// accountOverviewPage.requestBind.click();
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

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfuly", false, false);
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);

			// Click on Endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Entering Endorsement effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);

			// Checking the policy expiration date field is available on endorse policy page
			Assertions.addInfo("Scenario 01",
					"Checking the policy expiration date field is available on endorse policy page");
			Assertions.verify(
					endorsePolicyPage.policyExpirationDate.checkIfElementIsPresent()
							&& endorsePolicyPage.policyExpirationDate.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page", "Policy expiration date field is displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Checking the policy expiration date field is available on endorse policy
			// page, when endorsement effective date same as NB policy effective date

			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.addInfo("Scenario 02",
					"Checking the policy expiration date field is available on endorse policy page when endorsement effective date same as NB policy effective date");
			testData = data.get(data_Value1);
			Assertions.passTest("Endorse Policy Page", "Policy Effective date is " + testData.get("PolicyEffDate"));
			testData = data.get(data_Value2);
			Assertions.passTest("Endorse Policy Page",
					"Transaction Effective date is " + testData.get("TransactionEffectiveDate"));

			Assertions.verify(
					endorsePolicyPage.policyExpirationDate.checkIfElementIsPresent()
							&& endorsePolicyPage.policyExpirationDate.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page", "Policy expiration date field is displayed", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Checking the policy expiration date field is Not available on endorse policy
			// page, when endorsement effective date after NB policy effective date
			testData = data.get(data_Value4);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.addInfo("Scenario 03",
					"Checking the policy expiration date field is not  available on endorse policy page when endorsement effective date after NB policy effective date");
			testData = data.get(data_Value1);
			Assertions.passTest("Endorse Policy Page", "Policy Effective date is " + testData.get("PolicyEffDate"));
			testData = data.get(data_Value4);
			Assertions.passTest("Endorse Policy Page",
					"Transaction Effective date is " + testData.get("TransactionEffectiveDate"));
			Assertions.verify(endorsePolicyPage.policyExpirationDate.checkIfElementIsPresent(), false,
					"Endorse Policy Page", "Policy expiration date field not availabe", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Checking policy expiration date warning message when policy expiration date
			// same as NB policy effective date and before policy effective date "The new
			// effective date must be after the policy effective date"
			testData = data.get(data_Value2);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			for (int i = 1; i < 3; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);
				endorsePolicyPage.policyExpirationDate.scrollToElement();
				endorsePolicyPage.policyExpirationDate.appendData(testData.get("PolicyExpirationDate"));
				endorsePolicyPage.policyExpirationDate.tab();

				endorsePolicyPage.changeExpirationDate.scrollToElement();
				endorsePolicyPage.changeExpirationDate.click();
				if (i == 1) {
					Assertions.addInfo("Scenario 02",
							"Asserting policy expiration date warning message when policy expiration date same as NB policy effective date");
					Assertions.passTest("Endorse Policy Page",
							"Policy Expiration Date " + testData.get("PolicyExpirationDate"));
					Assertions.verify(
							endorsePolicyPage.expirationDateErrorMsg.checkIfElementIsPresent()
									&& endorsePolicyPage.expirationDateErrorMsg.checkIfElementIsDisplayed(),
							true, "Endorse Policy Page",
							"Policy expiration date warning message is "
									+ endorsePolicyPage.expirationDateErrorMsg.getData() + " displayed verified",
							false, false);
					Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
				} else {
					Assertions.addInfo("Scenario 05",
							"Asserting policy expiration date warning message when policy expiration date before NB policy effective date");
					Assertions.passTest("Endorse Policy Page",
							"Policy Expiration Date " + testData.get("PolicyExpirationDate"));
					Assertions.verify(
							endorsePolicyPage.expirationDateErrorMsg.checkIfElementIsPresent()
									&& endorsePolicyPage.expirationDateErrorMsg.checkIfElementIsDisplayed(),
							true, "Endorse Policy Page",
							"Policy expiration date warning message is "
									+ endorsePolicyPage.expirationDateErrorMsg.getData() + " displayed verified",
							false, false);
					Assertions.addInfo("Scenario 05", "Scenario 05 Ended");
				}
				endorsePolicyPage.policyExpirationDate.scrollToElement();
				endorsePolicyPage.policyExpirationDate.clearData();
			}

			endorsePolicyPage.cancelButton.scrollToElement();
			endorsePolicyPage.cancelButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on cancel button successfully");

			// Click on Endorse PB
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Entering Endorsement effective date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// enter policy expiration date after NB policy effective date
			testData = data.get(data_Value4);
			endorsePolicyPage.policyExpirationDate.scrollToElement();
			endorsePolicyPage.policyExpirationDate.appendData(testData.get("PolicyExpirationDate"));
			endorsePolicyPage.policyExpirationDate.tab();
			Assertions.passTest("Endorse Policy Page",
					"Policy expiration date entered successfully, policy expiration date is "
							+ testData.get("PolicyExpirationDate"));
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();
			Assertions.addInfo("Scenario 06",
					"Verifying the user is able to process endorsement when policy expiration date is after NB policy effective date");
			Assertions.verify(
					endorsePolicyPage.nextButton.checkIfElementIsPresent()
							&& endorsePolicyPage.nextButton.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page", "User is able to process the endorsement", false, false);
			endorsePolicyPage.cancelButton.scrollToElement();
			endorsePolicyPage.cancelButton.click();
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// ----Added IO-21489------

			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			testData = data.get(data_Value2);
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(5);

			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillElementisEnabled(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();

			testData = data.get(data_Value5);

			createQuotePage.enterQuoteDetailsNAHO(testData);

			// continue with endorsement the endorsement
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}
			endorsePolicyPage.viewModelResultsButton.scrollToElement();
			endorsePolicyPage.viewModelResultsButton.click();
			endorsePolicyPage.viewModelResults.formatDynamicPath("Total Premium:").checkIfElementIsPresent();
			Assertions.addInfo("Model Results Page",
					"Verifying that the Guy Carpenter results are displayed with respective values are verified.");
			Assertions.verify(
					endorsePolicyPage.viewModelResults.formatDynamicPath("Total Premium:").getData().contains("$"),
					true, "Model Results Page",
					"Total premium is displayed with respective value on the view model results page", false, false);
			Assertions.verify(
					endorsePolicyPage.viewModelResults.formatDynamicPath("ELR Premium:").getData().contains("$"), true,
					"Model Results Page",
					"ELR premium is displayed with respective value on the view model results page", false, false);
			Assertions.verify(
					endorsePolicyPage.viewModelResults.formatDynamicPath("Peril Deductible:").getData().contains("%"),
					true, "Model Results Page",
					"Peril Deductible is displayed with respective value on the view model results page", false, false);
			Assertions.verify(endorsePolicyPage.viewModelResults.formatDynamicPath("TIV").getData().contains("$"), true,
					"Model Results Page", "TIV is displayed with respective value on the view model results page",
					false, false);
			Assertions.verify(
					endorsePolicyPage.viewModelResults.formatDynamicPath("Peril AAL:").getData().contains("$"), true,
					"Model Results Page", "Peril AAL is displayed with respective value on the view model results page",
					false, false);
			Assertions.verify(
					endorsePolicyPage.viewModelResults.formatDynamicPath("Peril ELR:").getData().contains("%"), true,
					"Model Results Page", "Peril ELR is displayed with respective value on the view model results page",
					false, false);

			Assertions.addInfo("Model Results Page",
					"Guy Carpenter results are displayed with respective values are verified.");

			endorsePolicyPage.viewModelCloseButton.scrollToElement();
			endorsePolicyPage.viewModelCloseButton.click();
			// ----IO-21489 ended--------

			// LogOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "LogOut as USM successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer Successfully");

			// searching policy as producer
			homePage.enterPersonalLoginDetails();
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Click on endorse policy link
			policySummaryPage.producerEndorsePolicyLink.scrollToElement();
			policySummaryPage.producerEndorsePolicyLink.click();

			// Checking policy expiration field not available on endorse policy page
			Assertions.addInfo("Scenario 07",
					"Checking absence of policy expiration date field on endorse policy page as producer");
			Assertions.verify(
					endorsePolicyPage.policyExpirationDate.checkIfElementIsPresent()
							&& endorsePolicyPage.policyExpirationDate.checkIfElementIsDisplayed(),
					false, "Endorse Policy Page", "Policy expiration date field is not availabe verified", false,
					false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBFLTC012 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBFLTC012 ", "Executed Successfully");
			}
		}
	}

}
