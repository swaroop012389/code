/** Program Description: Verifying Cancel Policy Date for different Reason code and and Reinstatement - MA
 *  Author			   : Priyanka S
 *  Date of Creation   : 01/04/2022
 **/

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBMATC001 extends AbstractNAHOTest {

	public PNBMATC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/MATC001.xls";
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
		int dataValue1 = 0;
		int dataValue14 = 13;
		int quoteLen;
		String quoteNumber;
		String policyNumber;
		Date date = new Date();
		Map<String, String> testData = data.get(dataValue1);
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
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
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

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Asserting Status of Policy
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. Policy status is : "
							+ policySummaryPage.policyStatus.getData(),
					false, false);

			// Click on Cancel Policy
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();

			// Validation for NOC - Enter 100 in Days Before NOC and select any Cancellation
			// Reason.
			// Check if “90” is displayed in Days Before NOC Text Field
			Assertions.addInfo("Scenario 01",
					"Enter 100 in Days Before NOC and select any Cancellation Reason. Check if “90” is displayed in Days Before NOC Text Field");
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			waitTime(3);
			if (cancelPolicyPage.daysBeforeNOC.checkIfElementIsPresent()
					&& cancelPolicyPage.daysBeforeNOC.checkIfElementIsDisplayed()) {
				if (!testData.get("Cancellation_DaysBeforeNOC").equals("")) {
					cancelPolicyPage.daysBeforeNOC.scrollToElement();
					cancelPolicyPage.daysBeforeNOC.setData(testData.get("Cancellation_DaysBeforeNOC"));
				}
			}
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());
			if (!testData.get("CancellationEffectiveDate").equals("")) {
				cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
				cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
				cancelPolicyPage.cancellationEffectiveDate.tab();
			}

			// Verifying if the No of Days is changed to 90
			waitTime(3);
			Assertions
					.verify("90", cancelPolicyPage.daysBeforeNOC.getData(), "Cancel Policy Page",
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
			testData = data.get(dataValue14);
			cancelPolicyPage.enterCancellationDetails(testData);

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

			// sign out as Usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBMATC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBMATC001 ", "Executed Successfully");
			}
		}
	}
}
