/** Program Description: 1. Performing various validations on NAHO Renewal product as Producer
 * 						 2. Provide the Policy Expiration date beyond the Actual expiration date and check if the user is allowed to perform a NPB Endorsment.
 *  					 3. Create a PB Endt. with coverage change & with the actual Exp. Date and complete it and Create PB Endt2 with the change in Exp. date in future . and IO-18756 and IO-21801
 *  Author			   : Pavan Mule
 *  Date of Creation   : 26/05/2022
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.ChangePaymentPlanPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBFLTC007_GEN extends AbstractNAHOTest {

	public PNBFLTC007_GEN() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/FL007.xls";
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
		LoginPage loginPage = new LoginPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ChangePaymentPlanPage changePaymentPlanPage = new ChangePaymentPlanPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// initializing variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;
		int quoteLen;
		String quoteNumber;
		String policyNumber;
		Map<String, String> testData = data.get(data_Value1);
		String covAvalue;
		String inflationGuardPercentage;
		double calCovAValue;
		String actualCovAValue;
		String calCovAValue_s;
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

			// Enter quote details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

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

			// Asserting policy Effective Date and Policy Expiration Date
			Assertions.verify(policySummaryPage.effectiveDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Effective date is disaplyed as : " + policySummaryPage.effectiveDate.getData(), false,
					false);
			Assertions.verify(policySummaryPage.expirationDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Expiration date is disaplyed as : " + policySummaryPage.expirationDate.getData(), false,
					false);

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfuly", false, false);
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);

			// clicking on renewal policy link
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

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

			}

			// Assert renewal quote number
			Assertions.verify(
					accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsPresent()
							&& accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote number is : " + quoteNumber);

			// Adding IO-21801
			// Calculating inflation guard applying to Cov A
			covAvalue = testData.get("L1D1-DwellingCovA");
			inflationGuardPercentage = testData.get("InflationGuardPercentage");
			calCovAValue = (Double.parseDouble(covAvalue) * Double.parseDouble(inflationGuardPercentage));
			Assertions.passTest("View/Print Full Quote Page",
					"NB Coverage A value " + testData.get("L1D1-DwellingCovA"));
			Assertions.passTest("View/Print Full Quote Page",
					"Inflation Guard Percentage " + testData.get("InflationGuardPercentage"));

			// Rounding off
			long roundCalCovAValue = Math.round(calCovAValue);

			// Converting double to string
			calCovAValue_s = Double.toString(roundCalCovAValue).replace(".0", "");

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("View/Print Full Quote Page", "Clicked on vieworprint full quote link");

			// Verifying and Asserting actual and calculated Cov A value
			// Cov A = Cov A*inflationguard%(1.0713)
			Assertions.addInfo("Scenario 01", "Verifying inflation guard applied to renewal Cov A");
			actualCovAValue = viewOrPrintFullQuotePage.coveragesValues.formatDynamicPath(4).getData().replace(",", "");
			Assertions.verify(actualCovAValue, "$" + calCovAValue_s, "View Print Full Quote Page",
					"Inflation guard " + testData.get("InflationGuardPercentage")
							+ " applied to Coverage A, actual coverage A value " + actualCovAValue
							+ " and calculated coverage A value $" + calCovAValue_s + " bothe are same",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			// IO-21801 Ended

			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

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
				homePage.searchQuote(quoteNumber);
			}

			// Click on Release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on release renewal to producer button successfully");

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
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Quote Searched successfully");

			// click on EditDeductibles and Limits button
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// updating Cov A = $249,999
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page ",
					"Create Quote Page loaded successfully", false, false);
			Assertions.passTest("Create Quote Page", "Original Cov A value is " + testData.get("L1D1-DwellingCovA"));
			testData = data.get(data_Value2);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.waitTime(3);
			createQuotePage.getAQuote.waitTillPresenceOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Latest Cov A value is " + testData.get("L1D1-DwellingCovA"));

			// Asserting hard stop message when Cov A = 249999, hard stop message is
			// Coverage A less than minimum limit of $250000"
			Assertions.addInfo("Scenario 02", "Asserting Hard stop message");
			Assertions.verify(
					createQuotePage.globalErr.checkIfElementIsPresent()
							&& createQuotePage.globalErr.checkIfElementIsDisplayed(),
					createQuotePage.globalErr.getData().contains("Coverage A less than minimum limit of $250000"),
					"Create quote page",
					"The Hard stop message is " + createQuotePage.globalErr.getData() + "displayed", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Verifying Cov E does not have $1m option when selecting occupied by
			// tenant(ticket IO-20815)
			testData = data.get(data_Value3);
			createQuotePage.coverageEArrow.scrollToElement();
			createQuotePage.coverageEArrow.click();
			Assertions.addInfo("Scenario 03",
					"Verifying Cov E does not have $1m option when selecting occupied by tenant");
			Assertions.verify(
					createQuotePage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE"))
							.checkIfElementIsPresent(),
					false, "Create Quote Page", "The $1000000 option not displayed is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer Successfully");

			// Sign In as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Search for Quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote Searched successfully");

			// click on Bind Request
			testData = data.get(data_Value2);
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
			requestBindPage.renewalRequestBindNAHO(testData);

			// Validating the premium amount
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Renewal Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on previous policy Number
			policySummaryPage.previousPolicyNumber.scrollToElement();
			policySummaryPage.previousPolicyNumber.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Previous policy Number link");

			// Click on Endorse PB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on endorse pb link");

			// Click on OK button
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

			// Click on change payment plan link
			endorsePolicyPage.changePaymentPlanLink.scrollToElement();
			endorsePolicyPage.changePaymentPlanLink.click();
			Assertions.passTest("Change Payment Plan Page", "Clicked on payment plan change link successfully");

			// IO-18756
			// while executing in uat1 uncomment line number from 356 to 371
			// Asserting Payment plan waring message is 'Renewal Payment Plan cannot be
			// changed because Renewal on this policy has been bound.'
			Assertions.addInfo("Scenario 04", "Asserting Payment plan waring message");
			Assertions.verify(changePaymentPlanPage.renewalPaymentPlanWarningmessage
					.formatDynamicPath(
							"Renewal Payment Plan cannot be changed because Renewal on this policy has been bound")
					.getData()
					.contains("Renewal Payment Plan cannot be changed because Renewal on this policy has been bound"),
					true, "Change Payment Plan Page",
					"Renewal Payment plan warning message is " + changePaymentPlanPage.renewalPaymentPlanWarningmessage
							.formatDynamicPath(
									"Renewal Payment Plan cannot be changed because Renewal on this policy has been bound")
							.getData() + " displayed",
					false, false);

			changePaymentPlanPage.okButton.scrollToElement();
			changePaymentPlanPage.okButton.click();
			// End IO-18756

			// below code added because of in uat2 and uat1 on renewal payment plan change
			// functionality changed
			// Initiate NPB Transaction
			if (changePaymentPlanPage.paymentPlanWarningMsg.checkIfElementIsPresent()
					&& changePaymentPlanPage.paymentPlanWarningMsg.checkIfElementIsDisplayed()) {
				Assertions.addInfo("Scenario 04", "PB Transaction - Change Payment Plan");
				// Change Payment Plan
				changePaymentPlanPage.enterChangePaymentPlanPB(testData);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();
			}

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button");

			// Click on Endorse PB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on endrose pb link");

			// Click on Ok button
			if (policySummaryPage.okButton.checkIfElementIsPresent()
					&& policySummaryPage.okButton.checkIfElementIsDisplayed()) {
				policySummaryPage.okButton.scrollToElement();
				policySummaryPage.okButton.click();
			}

			// Initiate change coverage PB ENDT without expiration date change
			// Entering Endorsement effective date
			testData = data.get(data_Value4);
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
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create A Quote Page", "Cov A value changed Successfully");

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();
			}

			// Asserting the Values From Endorse Summary Page
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true,
					"Endorse Summary Page", "Endorse Summary Page Loaded Successfully", false, false);

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button");

			// Initiate PB ENDT with expiration date in future
			// Click on Endorse PB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on endrose pb link");

			// Click on Ok button
			if (policySummaryPage.okButton.checkIfElementIsPresent()
					&& policySummaryPage.okButton.checkIfElementIsDisplayed()) {
				policySummaryPage.okButton.scrollToElement();
				policySummaryPage.okButton.click();
			}

			// Entering Endorsement effective date
			testData = data.get(data_Value4);
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
			Assertions.passTest("Endorse Policy Page", "Clicked on change expiration date");

			// Asserting Out of Sequence Warning Message
			Assertions.addInfo("Scenario 05", "Asserting out of sequence warning message");
			Assertions.verify(endorsePolicyPage.oosMsg.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Out of Sequence message is displayed as : " + endorsePolicyPage.oosMsg.getData(), false, false);

			// Click on continue Button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click On next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();
			}

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// Asserting Out of Sequence Warning Message
			Assertions.verify(endorsePolicyPage.oosMsg.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Out of Sequence message is displayed as : " + endorsePolicyPage.oosMsg.getData(), false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on continue Button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Click on Close Button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button");

			// Asserting policy Effective Date and Policy Expiration Date
			Assertions.addInfo("Scenario 06", "Asserting policy Effective Date and Policy Expiration Date");
			Assertions.verify(policySummaryPage.effectiveDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Effective date is displayed as : "
							+ policySummaryPage.effectiveDate.getData().substring(0, 10),
					false, false);
			Assertions.verify(policySummaryPage.expirationDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Policy Expiration date is displayed as : "
							+ policySummaryPage.expirationDate.getData().substring(0, 10),
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBFLTC007 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBFLTC007 ", "Executed Successfully");
			}
		}
	}

}
