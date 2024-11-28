/** Program Description: Check the premium adjustment that is applied to the New Business  will apply to the endorsement.
 *  Author			   : Pavan Mule
 *  Date of Creation   : 02/27/2024
 **/

package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PremiumAdjustmentRequestPage;
import com.icat.epicenter.pom.PremiumReliefDecisionPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC081_PNBP01 extends AbstractNAHOTest {

	public TC081_PNBP01() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBP01.xls";
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
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		PremiumAdjustmentRequestPage premiumAdjustmentRequestPage = new PremiumAdjustmentRequestPage();
		PremiumReliefDecisionPage premiumReliefDecisionPage = new PremiumReliefDecisionPage();
		ReferralPage referralPage = new ReferralPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		LoginPage loginPage = new LoginPage();

		// Variables
		String quoteNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		String originalPremium;
		String transactionPremium;
		String endTransactionPremium;
		double calTransactionPremium;
		String endAnnualPremium;
		String annualPremium;
		String policyNumber;
		String windPremium;
		String aopPremium;
		String liabilityPremium;
		String offeredApprovedPremium;
		double sumOfAopGLandWind;
		String latestPremium;
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
				Assertions.passTest("Prior Loss Page", "Prior Loss Details Entered successfully");
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting Quote number
			Assertions.verify(
					accountOverviewPage.requestBind.checkIfElementIsPresent()
							&& accountOverviewPage.requestBind.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// Verifying the absence of request premium change link on account overview
			// page as usm
			Assertions.addInfo("Scenario 01", "Verifying absence of premium change link as usm");
			Assertions.verify(accountOverviewPage.requestPremiumChangeLink.checkIfElementIsPresent(), false,
					"Account Overview Page", "Premium Change link  not displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Logout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Loged out as usm successfully");

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in a as producer Successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Quote searched as a producer successfully");

			// Getting premium value from account overview page
			originalPremium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");

			// Verifying the presence of request premium change link on account overview
			// page
			Assertions.addInfo("Scenario 02", "Verifying presence of premium change link");
			Assertions.verify(accountOverviewPage.requestPremiumChangeLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Change link displayed successfully", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on request premium change link
			accountOverviewPage.requestPremiumChangeLink.scrollToElement();
			accountOverviewPage.requestPremiumChangeLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on rquest premium change link successfully");

			// Enter premium changes details
			premiumAdjustmentRequestPage.waitTime(1);
			Assertions.verify(premiumAdjustmentRequestPage.rpcUpdateBtn.checkIfElementIsDisplayed(), true,
					"Premium Adjustment Request Page", "Premium Adjustment Request Page loaded successfully", false,
					false);
			premiumAdjustmentRequestPage.requestPremiumChanges(testData);
			Assertions.passTest("Premium Adjustment Request Page", "Premium Adjustment Details entered successfully");

			// Asserting and verifying referral message,referral message is'Thank you for
			// your referral. Your Underwriting contact has been notified of this request
			// and will get back to you shortly. If you have any questions about this
			// request, please reference the following number:XX
			Assertions.addInfo("Scenario 03", "Asserting and Verifying referral message");
			Assertions.verify(
					accountOverviewPage.requestPremiumChangeReferralMsg.getData()
							.contains("Thank you for your referral"),
					true, "Account Overview Page",
					"The Referral messgae is " + accountOverviewPage.requestPremiumChangeReferralMsg.getData(), false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Logout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Loged out as usm successfully");

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as USM Successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote searched as a usm successfully");

			// Click on open referral(Here quote is referring because of changing the
			// premium in request premium page
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on open referral link successfully");

			// Click on approve/decline button
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();
			Assertions.passTest("Referral Page", "Clicked on Approve/Decline Request button successfully");

			// Verifying the target premium updated on premium relief decision page
			Assertions.addInfo("Scenario 04", "Verifying the target premium updated on premium relief decision page");
			Assertions.verify(
					premiumReliefDecisionPage.requestedPremium.getData().replace("$", "").contains(
							testData.get("PremiumAdjustment_TargetPremium")),
					true, "Premium Relife Decision Page",
					"The target premium updated on premium relief decision page, The requested premium is "
							+ premiumReliefDecisionPage.requestedPremium.getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Enter premium relief decision details
			premiumReliefDecisionPage.enterPremiumReliefDetailsNAHO(testData);
			Assertions.passTest("Premium Relife Decision Page", "Premium relief details entered successfully");

			// Getting wind, aop and GL premium
			windPremium = premiumReliefDecisionPage.windPremium.getData();
			aopPremium = premiumReliefDecisionPage.orgAopPremium.getData().replace("$", "").replace(",", "");
			liabilityPremium = premiumReliefDecisionPage.orgGLPremium.getData().replace("$", "").replace(",", "");
			offeredApprovedPremium = premiumReliefDecisionPage.offeredOrApprovedPremium.getData().replace(",", "");

			// Sum of the Original AOP, Wind and GL/Liability Premium (AOp+Wind+GL)
			sumOfAopGLandWind = (Double.parseDouble(windPremium) + Double.parseDouble(aopPremium)
					+ Double.parseDouble(liabilityPremium));
			Assertions.addInfo("Scenario 05", "Verifying Offered/Approved Premium is equal to sum Of Aop,GL and Wind");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(offeredApprovedPremium), 2)
					- Precision.round(sumOfAopGLandWind, 2)), 2) < 0.01) {
				Assertions.passTest("Referral Approve/Decline Page", "Offered Or Approved Premium: " + "$"
						+ Precision.round(Double.parseDouble(offeredApprovedPremium), 2));
				Assertions.passTest("Referral Approve/Decline Page",
						"Sum Of Aop GL and Wind: " + "$" + Precision.round(sumOfAopGLandWind, 2));
			} else {
				Assertions.verify(Precision.round(Double.parseDouble(offeredApprovedPremium), 2),
						Precision.round(sumOfAopGLandWind, 2), "Referral Approve/Decline Page",
						"The Actaul and Calculated Wind, AOP and GL Values are not the Same", false, false);
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on approve button
			premiumReliefDecisionPage.approveBtn.scrollToElement();
			premiumReliefDecisionPage.approveBtn.click();

			// Open the referred quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Referred quote searched successfully");

			// Getting updated premium from account overview page
			Assertions.addInfo("Scenario 06",
					"Asserting latest premium value from account overview page after reoffer approved premium");
			latestPremium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(latestPremium), 2)
					- Precision.round(Double.parseDouble(offeredApprovedPremium), 2)), 2) < 0.01) {
				Assertions.passTest("Account Overview Page",
						"Original Premium: " + "$" + Precision.round(Double.parseDouble(originalPremium), 2));
				Assertions.passTest("Account Overview Page", "Offered Or Approved Premium: " + "$"
						+ Precision.round(Double.parseDouble(offeredApprovedPremium), 2));
				Assertions.passTest("Account Overview Page",
						"Latest Premium: " + "$" + Precision.round(Double.parseDouble(latestPremium), 2));
			} else {
				Assertions.verify(Precision.round(Double.parseDouble(latestPremium), 2),
						Precision.round(Double.parseDouble(offeredApprovedPremium), 2), "Account Overview Page",
						"Offered or approved premium and latest premium are not matching", false, false);
			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request bind button successfully");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			Assertions.passTest("Underwriting Questions Page",
					"All underwriting questions are selecting NO successfully");

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// get policyNumber and Transaction premium
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully.PolicyNumber is " + policyNumber, false, false);
			transactionPremium = policySummaryPage.transactionPremium.getData().replace("$", "").replace(",", "");

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
			Assertions.passTest("Endorse Policy Page", "Transaction effective date entered successfully");

			// Click on Change Coverage Option link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// Entering Deductible details
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded Successfully", false, false);
			createQuotePage.enterDeductiblesNAHO(testData);
			Assertions.passTest("Create Quote Page", "Deductible details entered successfully");

			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting updated Deductible values from endorse summary details page
			Assertions.addInfo("Scenario 07", "Asserting updated Deductible values from endorse summary details page");
			Assertions.verify(
					endorseSummaryDetailsPage.buildingLevelChangesToCol
							.formatDynamicPath(1, 5).getData().contains(testData.get("NamedStormValue")),
					true, "Endorse Summary Details Page",
					"Latest Deductible value is "
							+ endorseSummaryDetailsPage.buildingLevelChangesToCol.formatDynamicPath(1, 5).getData(),
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on next
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Click on next button");
			endorsePolicyPage.oKContinueButton.scrollToElement();
			endorsePolicyPage.oKContinueButton.click();

			// Getting transaction and Annual premium from endorse policy page
			endTransactionPremium = endorsePolicyPage.transactionPremium.getData().replace("-", "").replace("$", "")
					.replace(",", "");
			endAnnualPremium = endorsePolicyPage.newPremium.formatDynamicPath(2).getData().replace("$", "").replace(",",
					"");

			// Calculating transaction premium
			calTransactionPremium = (Double.parseDouble(transactionPremium)
					- Double.parseDouble(endTransactionPremium));

			// Verifying and asserting the Transaction Premium is applied to Updated Premium
			// under Annual
			// Premium column
			Assertions.addInfo("Scenario 08",
					"Verifying and asserting the Transaction Premium is applied to Updated Premium under Annual");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(endAnnualPremium), 2)
					- Precision.round(calTransactionPremium, 2)), 2) < 0.05) {
				Assertions.passTest("Endorse Policy Page", "Upadated annual premium is :" + "$"
						+ Precision.round(Double.parseDouble(endAnnualPremium), 2));
				Assertions.passTest("Endorse Policy Page",
						"Calcualated transaction premium :" + "$" + Precision.round(calTransactionPremium, 2));
			} else {

				Assertions.verify(Precision.round(Double.parseDouble(endAnnualPremium), 2),
						Precision.round(calTransactionPremium, 2), "Endorse Policy Page",
						"The Difference between updated annual premium  and calculated transaction Premium is more than 0.05",
						false, false);
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			// Click on Close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// Getting annual premium
			policySummaryPage.transHistReason.formatDynamicPath(3).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(3).click();
			annualPremium = policySummaryPage.PremiumFee.formatDynamicPath(2).getData().replace(",", "").replace("$",
					"");

			// Assert the premium adjustment applied to endorsement on Policy Summary page
			Assertions.addInfo("Scenario 09",
					"Asserting and verifying the premium adjustment applied to endorsement on Policy Summary page");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(annualPremium), 2)
					- Precision.round(Double.parseDouble(endAnnualPremium), 2)), 2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Actual annual premium is :" + "$" + Precision.round(Double.parseDouble(annualPremium), 2));
				Assertions.passTest("Policy Summary Page",
						"Expected annual premium :" + "$" + Precision.round(Double.parseDouble(endAnnualPremium), 2));
			} else {
				Assertions.verify(annualPremium, endAnnualPremium, "Policy Summary Page",
						"The Difference between actual and expected annual premium is more than 0.05", false, false);
			}
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Click on sign out button
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC081 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC081 ", "Executed Successfully");
			}
		}
	}
}