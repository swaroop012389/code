package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC053_NBPO001 extends AbstractNAHOTest {

	public TC053_NBPO001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBPO001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();

		// Initializing Variables
		Map<String, String> testData;
		int data_Value1 = 0;
		int data_Value2 = 1;
		testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Creating a new account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Checking the processing of Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering Prior Loss details
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Quote details
			Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Clicking on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.waitTillVisibilityOfElement(60);
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			String quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Validating the total premium value
			Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Policy Premium : " + accountOverviewPage.totalPremiumValue.getData(),
					false, false);

			// Clicking on override premium link
			accountOverviewPage.overridePremiumLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();

			// Validating loading of Override Premium And Fees Page
			Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
					"Override Premium And Fees Page", "Override Premium And Fees Page loaded successfully", false,
					false);

			testData = data.get(data_Value2);

			// Entering override premium value
			overridePremiumAndFeesPage.enterFeesDetailsNAHO(testData);
			Assertions.passTest("Override Premium And Fees Page", "Override details entered successfully");

			// Validating the total premium value after override
			accountOverviewPage.refreshPage();
			String updatedPremiumValue = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",",
					"");
			double d_updatedPremiumValue = Double.parseDouble(updatedPremiumValue);
			Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Policy Premium after override : " + accountOverviewPage.totalPremiumValue.getData(), false, false);

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
			testData = data.get(data_Value1);

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);

			// Validating the premium amount
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary", "Policy Number is : " + policyNumber);
			String actualPremiumValue = policySummaryPage.policyTotalPremium.getData().replace("$", "").replace(",",
					"");
			double d_actualPremiumValue = Double.parseDouble(actualPremiumValue);

			if (Precision.round(
					Math.abs(Precision.round(d_actualPremiumValue, 2) - Precision.round(d_updatedPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page",
						"Policy Total Premium : " + "$" + Precision.round(d_updatedPremiumValue, 2));
				Assertions.passTest("Policy Summary Page", "Policy Total Premium : " + "$" + d_actualPremiumValue);
			} else {
				Assertions.passTest("Policy Summary Page",
						"The Difference between Updated Premium and Policy Total Premium is more than 0.05");
			}
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC053 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC053 ", "Executed Successfully");
			}
		}
	}
}
