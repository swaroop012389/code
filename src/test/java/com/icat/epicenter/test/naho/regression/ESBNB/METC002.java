/** Program Description: Verifying SLTF values on Quote document and Account overview page are matching, Adding 3 Loss Payees and 4th AI Relation as Other and Description as "Friend", verify due diligence text, request received messages.
 *  Author			   : Priyanka S
 *  Date of Creation   : 12/27/2021
 **/

package com.icat.epicenter.test.naho.regression.ESBNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class METC002 extends AbstractNAHOTest {

	public METC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBNB/METC002.xls";
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
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		String quoteNumber;
		testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

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
			testData = data.get(dataValue1);
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Fetching Quote Number
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);

			// Printing Quote Number
			Assertions.passTest("Account Overview Page", "The Quote Number: " + quoteNumber);

			// Entering View/Print Full Quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Fulll quote link");

			// Validate SLTF value
			viewOrPrintFullQuotePage.surplusLinesTaxesValue.scrollToElement();
			viewOrPrintFullQuotePage.surplusLinesTaxesValue.waitTillVisibilityOfElement(60);
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			String surplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
					.replace("%", "");
			String inspectionValue = viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(",", "")
					.replace("%", "");
			String policyValue = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "")
					.replace("%", "");

			double sltfFee = Double.parseDouble(premiumValue) + Double.parseDouble(inspectionValue)
					+ Double.parseDouble(policyValue) + Double.parseDouble(surplusContribution);
			double sltfFeeValue = sltfFee * 3 / 100;

			// Click on Go Back Button
			accountOverviewPage.goBackBtn.scrollToElement();
			accountOverviewPage.goBackBtn.click();

			// Asserting SLTF Value
			String sltfData = accountOverviewPage.sltfValue.getData().replace("$", "");
			double actalSltfData = Double.parseDouble(sltfData);
			Assertions.addInfo("Scenario 01", "Verify the SLTF value is displayed on Account overview page");
			if (Precision.round(Math.abs(Precision.round(actalSltfData, 2) - Precision.round(sltfFeeValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Data :  " + "$" + Precision.round(sltfFeeValue, 2));
				Assertions.passTest("Account Overview Page",
						"Actual SLTF Data : " + "$" + Precision.round(actalSltfData, 2));
			} else {
				Assertions.verify(actalSltfData, sltfFeeValue, "Account Overview Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			Assertions.passTest("Account Overview page", "Quote Number : "
					+ accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12));

			// Click on Request Bind
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer No button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);

			// Entering bind details
			// Entering policy details
			requestBindPage.enterPolicyDetailsNAHO(testData);

			// Entering payment information
			requestBindPage.enterPaymentInformationNAHO(testData);

			// Entering inspection details
			requestBindPage.addInspectionContact(testData);

			// Entering AI values
			requestBindPage.addAdditionalInterest(testData);

			// Entering contact information
			requestBindPage.addContactInformation(testData);
			Assertions.addInfo("Scenario 02", "Verify Diligence Text Message is displayed on Request Bind Page");
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind page",
					"Requesting bind on Policy message is displayed", false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("minimum 25%").checkIfElementIsDisplayed(), true,
					"Request Bind page",
					"Diligence Message 1 : "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("minimum 25%").getData(),
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("signed application").checkIfElementIsDisplayed(),
					true, "Request Bind page",
					"Diligence Message 2 : "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("signed application").getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			if (requestBindPage.submit.checkIfElementIsPresent()
					&& requestBindPage.submit.checkIfElementIsDisplayed()) {
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
				requestBindPage.requestBind.waitTillVisibilityOfElement(60);
				requestBindPage.requestBind.scrollToElement();
				requestBindPage.requestBind.click();
				requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
			}

			// Bind Request Submitted Page
			// Asserting Request Received Information
			Assertions.addInfo("Scenario 03", "Verify Request Received Message is displayed Request Submitted Page");
			Assertions.verify(
					bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("Additional Insured")
							.checkIfElementIsDisplayed(),
					true, "Bind Request Submitted page",
					"Request Received 1 : " + bindRequestSubmittedPage.requestReceivedMsg
							.formatDynamicPath("Additional Insured").getData(),
					false, false);
			Assertions.verify(
					bindRequestSubmittedPage.requestReceivedMsg
							.formatDynamicPath("three or more").checkIfElementIsDisplayed(),
					true, "Bind Request Submitted page",
					"Request Received 2 : "
							+ bindRequestSubmittedPage.requestReceivedMsg.formatDynamicPath("three or more").getData(),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("METC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("METC002 ", "Executed Successfully");
			}
		}
	}
}