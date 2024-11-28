package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BrokerOfRecordPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC030_PNBR001 extends AbstractNAHOTest {

	public TC030_PNBR001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBR001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		BrokerOfRecordPage brokerOfRecordPage = new BrokerOfRecordPage();
		int dataValue1 = 0;
		int dataValue2 = 1;

		Map<String, String> testData = data.get(dataValue1);
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

			// Asserting policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Click on Rewrite Policy Link
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();

			// Changing the producer
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account Overview Page loaded successfully", false, false);

			// Asserting Old producer Number
			String oldProducerNumber = accountOverviewPage.producerNumber.getData();
			Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The producer Number Before Updating New Producer is  " + oldProducerNumber
							+ " displayed is verified",
					false, false);

			// Click on edit producer
			accountOverviewPage.producerLink.scrollToElement();
			accountOverviewPage.producerLink.click();

			Assertions.verify(brokerOfRecordPage.processBOR.checkIfElementIsDisplayed(), true, "Broker of Record Page",
					"Broker of Record Page loaded successfully", false, false);

			testData = data.get(dataValue2);
			brokerOfRecordPage.newProducerNumber.setData(testData.get("NewProducerNumber"));
			brokerOfRecordPage.borStatusArrow.click();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();

			// Click on Process Bor Button
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();

			// Click on close Button
			brokerOfRecordPage.closeBORPage.scrollToElement();
			brokerOfRecordPage.closeBORPage.click();

			// Asserting the Changed producer number From Account overview Page
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page ", "Account Overview Page loaded successfully", false, false);
			String newProducerNumber = accountOverviewPage.producerNumber.getData();
			Assertions.verify(accountOverviewPage.producerNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The User is able to Update the Producer and the producer number Updated is " + newProducerNumber
							+ " displayed is verified",
					false, false);

			// Creating another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on create another quote");

			// Click on Override Button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Entering quote details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber1 = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber1);

			// Click on Rewrite Bind
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on Rewrite Bind");

			// Entering rewrite Bind details
			requestBindPage.enteringRewriteDataNAHO(testData);

			// Asserting Cancelled Policy Number and Rewritten Policy Number
			String policyNumber1 = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. Rewritten PolicyNumber is : " + policyNumber1, false,
					false);

			String cancelledPolicyNumber = policySummaryPage.rewrittenPolicyNumber.getData();
			Assertions.verify(policySummaryPage.rewrittenPolicyNumber.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "The Cancelled Policy Number is " + cancelledPolicyNumber, false, false);

			// Asserting the New Producer Number from Policy Summary page
			Assertions.verify(policySummaryPage.producerNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Producer Number Displayed  " + policySummaryPage.producerNumber.getData()
							+ " displayed is verified",
					false, false);

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC030 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC030 ", "Executed Successfully");
			}
		}
	}

}
