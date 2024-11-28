/** Program Description: Added ticket IO-20384
 *  Author			   : Pavan mule
 *  Date of Creation   : 09/08/2022
 **/
package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyInspectorPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC092_NBGEN019 extends AbstractNAHOTest {

	public TC092_NBGEN019() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBGEN019.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		LoginPage loginPage = new LoginPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		PolicyInspectorPage policyInspectorPage = new PolicyInspectorPage();

		Map<String, String> testData;
		int dataValue1 = 0;
		testData = data.get(dataValue1);
		String quoteNumber;
		int quoteLen;
		String expected_GlPersInjuryPremium;
		String policyNumber;
		String actualTerm_GlPremiumValue;
		String actualAnnualized_GlPremiumValue;
		String actualAnnual_GlPremiumValue;
		String actualAnnualTxn_GlPremiumValue;
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
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			// Entering Quote details
			Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is " + quoteNumber);

			// Click on Request bind button
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

			// Enter bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind Request  Page",
					"Bind Request  Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Bind Request Page", "Bind Details Entered successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// click on view/print rate trace link
			policySummaryPage.viewPrintRateTrace.scrollToElement();
			policySummaryPage.viewPrintRateTrace.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View/print rate trace link successfully");

			// Asserting GL Building Premium Taxes and Fees Pers Injury Premium value =
			// "25.0000"
			Assertions.verify(viewOrPrintRateTrace.backBtn.checkIfElementIsDisplayed(), true,
					"View/Print Rate Trace Page ", "View/Print Rate Trace Page loaded successfully", false, false);
			expected_GlPersInjuryPremium = viewOrPrintRateTrace.persInjuryPremiumValue.getData().replace(".", "")
					.replace("0000", "");
			Assertions.passTest("View/Print Rate Trace Page",
					"GL Pers Injury Premium value is " + expected_GlPersInjuryPremium);

			// Click on back button
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();
			Assertions.passTest("View/Print Rate Trace Page", "Clicked on back button successfully");

			// Click on sign out as tuser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as tuser successfully");

			// login as rzimmer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged as rzimmer successfully");

			// Click on admin link
			Assertions.verify(homePage.adminLink.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home Page", "Clicked on admin link successfully");

			// click on policy inspector link
			Assertions.verify(healthDashBoardPage.policyInspectorLink.checkIfElementIsDisplayed(), true,
					"Health Dash Board Page", "Health Dash Board Page loaded successfully", false, false);
			healthDashBoardPage.policyInspectorLink.scrollToElement();
			healthDashBoardPage.policyInspectorLink.click();
			Assertions.passTest("Health Dash Board Page", "Clicked on Policy Inspector link successfully");

			// search policy number
			Assertions.verify(policyInspectorPage.backButton.checkIfElementIsDisplayed(), true, "Policy Inspector Page",
					"Policy Inspector Page loaded successfully", false, false);
			policyInspectorPage.enterPolicyNumber.scrollToElement();
			policyInspectorPage.enterPolicyNumber.appendData(policyNumber);
			policyInspectorPage.findPolicyutton.scrollToElement();
			policyInspectorPage.findPolicyutton.click();

			// Verifying GL Premium value for PersInjury Premium = 25.0
			actualTerm_GlPremiumValue = policyInspectorPage.glPremiumValue.formatDynamicPath(2).getData()
					.replace(".", "").replace("0", "");
			actualAnnualized_GlPremiumValue = policyInspectorPage.glPremiumValue.formatDynamicPath(3).getData()
					.replace(".", "").replace("0", "");
			actualAnnual_GlPremiumValue = policyInspectorPage.glPremiumValue.formatDynamicPath(4).getData()
					.replace(".", "").replace("0", "");
			actualAnnualTxn_GlPremiumValue = policyInspectorPage.glPremiumValue.formatDynamicPath(5).getData()
					.replace(".", "").replace("0", "");
			Assertions.addInfo("Scenario 01", "Verifying GL perInjury premium value = $25");
			Assertions.verify(actualTerm_GlPremiumValue, expected_GlPersInjuryPremium, "Policy Inspector Page",
					"Actual and Expected term GL perInjury premium are matching, Actual GL premium value is "
							+ actualTerm_GlPremiumValue,
					false, false);
			Assertions.verify(actualAnnualized_GlPremiumValue, expected_GlPersInjuryPremium, "Policy Inspector Page",
					"Actual and Expected Annualized GL perInjury premium are matching, Actual GL premium value is "
							+ actualAnnualized_GlPremiumValue,
					false, false);
			Assertions.verify(actualAnnual_GlPremiumValue, expected_GlPersInjuryPremium, "Policy Inspector Page",
					"Actual and Expected Annual GL perInjury premium are matching, Actual GL premium value is "
							+ actualAnnual_GlPremiumValue,
					false, false);
			Assertions.verify(actualAnnualTxn_GlPremiumValue, expected_GlPersInjuryPremium, "Policy Inspector Page",
					"Actual and Expected Annual Txn GL perInjury premium are matching, Actual GL premium value is "
							+ actualAnnualTxn_GlPremiumValue,
					false, false);

			Assertions.passTest("Scenario 01 ", "Scenario 01 Ended");

			// Click on back button
			policyInspectorPage.backButton.scrollToElement();
			policyInspectorPage.backButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC092 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC092 ", "Executed Successfully");
			}
		}
	}
}
