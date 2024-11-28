/*Description: verify the AOWH deductible can be selected independent of the AOP deductible and not defaulted to AOP deductible as USM for REWRITE transaction.
*/
package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC032_PNBR003 extends AbstractNAHOTest {

	public TC032_PNBR003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBR003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();

		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
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

			// Entering Quote Details and select AOWH = 5%
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully with AOWH 5%");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting Quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on bind request button");

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();
			Assertions.passTest("Underwriting Questions Page", "Clicked on answer NoToAll questions");

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();
			Assertions.passTest("Underwriting Questions Page", "Clicked on save button");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Validating the premium amount
			String originalPolicyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is " + originalPolicyNumber, false, false);

			// Click on rewrite
			policySummaryPage.rewritePolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on rewrite button");

			// Create another quote and update coverage values
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on create another quote button");

			BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Verifying and Asserting AOWH deductible is default to minimum 2%, when AOWH
			// selected 5% in NB quote
			Assertions.passTest("Create Quote Page",
					"AOWH deductible selected in NB quote is " + testData.get("AOWHDeductibleValue"));
			Assertions.addInfo("Scenari 01", "Verifying and Asserting AOWH deductible is default to minimum 2%");
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().equals("2%"), true, "Create Quote Page",
					" AOWH Deductible is default to minimum value is " + createQuotePage.aowhDeductibleData.getData()
							+ " selected in rewrite quote is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Enter AOWH Deductible
			testData = data.get(data_Value2);
			createQuotePage.enterDeductiblesNAHO(testData);
			Assertions.passTest("Create Quote Page", "AOWH Value entered successfully");

			// Verify the AOWH deductible can be selected by the user.
			Assertions.addInfo("Scenario 02", "Verifying the AOWH deductible can be selected by the user.");
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().equals(testData.get("AOWHDeductibleValue")),
					true, "Create Quote Page",
					"User is able to select AOWH deductible, Selected AOWH deductible value is "
							+ createQuotePage.aowhDeductibleData.getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Enter AOP Deductible value
			Assertions.passTest("Create Quote Page",
					"Before selecting AOP, the AOWH Value is " + createQuotePage.aowhDeductibleData.getData());
			testData = data.get(data_Value3);
			createQuotePage.enterDeductiblesNAHO(testData);
			Assertions.passTest("Create Quote Page", "AOP Value entered successfully");

			// Verifying that the AOWH deductible is not affected by the selected AOP
			// deductible
			testData = data.get(data_Value2);
			Assertions.addInfo("Scenario 03",
					"Verifying that the AOWH deductible is not affected by the selected AOP deductible");
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().equals(testData.get("AOWHDeductibleValue")),
					true, "Create Quote Page",
					"Verified that the AOWH deductible is not affected by the selected AOP deductible, AOWH Value is "
							+ createQuotePage.aowhDeductibleData.getData(),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Bind rewrite quote
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen1 = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber1 = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen1 - 1);
			Assertions.passTest("Account Overview Page", "Rewrite Quote Number is : " + quoteNumber1);

			// Verifying AOWH present in quote tree
			Assertions.addInfo("Scenario 04", "Verifying AOWH present in quote tree");
			Assertions.verify(
					accountOverviewPage.quoteTreeAopNsAowh.formatDynamicPath("AOWH").checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"AOWH displayed in quote tree AOWH value is " + accountOverviewPage.quoteTreeAopNsAowh
							.formatDynamicPath("AOWH").getData().substring(37, 41),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Verifying All other wind and Hail options are present
			Assertions.addInfo("Scenario 05", "Verifying All other wind and Hail options are present");
			Assertions.verify(accountOverviewPage.aowhLable.getData().equals("All Other Wind & Hail"), true,
					"Account Overview Page",
					"All Other Wind & Hail option present is " + accountOverviewPage.aowhLable.getData() + " verified",
					false, false);
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Wind & Hail", 2)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "All Other Wind & Hail Option1 present is verified", false, false);
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Wind & Hail", 3)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "All Other Wind & Hail Option2 present is verified", false, false);
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Wind & Hail", 4)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "All Other Wind & Hail Option3 present is verified", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on the View/Print Rate Trace link.
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();
			Assertions.passTest("Account Overview Page", "Clicked on view or print rate trace link");

			// Verify the Rate Trace contains DeductibleFactorAOWH for Wind peril.
			Assertions.addInfo("Scenario 06", "Verify the Rate Trace contains DeductibleFactorAOWH for Wind peril");
			Assertions.verify(viewOrPrintRateTrace.windDeductibleAOWHFactor.getData().equals("DeductibleFactorAOWH"),
					true, "View Or Print Rate Trace Page", "Rate Trace contains DeductibleFactorAOWH for Wind peril is "
							+ viewOrPrintRateTrace.windDeductibleAOWHFactor.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Verify the DeductibleFactorAOWH value in the Rate Trace is same as the
			// DeductibleFactorAOWH in the rater sheet.
			testData = data.get(data_Value1);
			Assertions.addInfo("Scenario 07",
					" Verify the DeductibleFactorAOWH value in the Rate Trace is same as the DeductibleFactorAOWH in the rater sheet.");
			Assertions.verify(viewOrPrintRateTrace.windDeductibleAOWHFactorValue.getData(),
					testData.get("DeductibleFactorAOWHValue"), "View Or Print Rate Trace Page",
					"The value of DeductibleFactorAOWH valu in the rate trace matches with the value in rater sheet(0.9000). Deductible Factor AOWH Value is "
							+ viewOrPrintRateTrace.windDeductibleAOWHFactorValue.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// click on back button
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();
			Assertions.passTest("View Or Print Rate Trace Page", "Clicked in back button");

			// Binding the quote and creating the policy
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.passTest("Account Overview Page", "Clicked in back button");

			// Entering bind details
			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enteringRewriteDataNAHO(testData);
			Assertions.passTest("Request Bind Page", "Rewrite details entered successfully");

			// Asserting New policy number
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(originalPolicyNumber), false,
					"Policy Summary Page",
					"Updated policy number for Rewritten Policy is: " + policySummaryPage.policyNumber.getData(), false,
					false);
			policySummaryPage.rewrittenPolicyNumber.scrollToElement();
			policySummaryPage.rewrittenPolicyNumber.click();

			// Asserting original policy status
			Assertions.addInfo("Scenario 08", "Asserting original policy status");
			Assertions.verify(policySummaryPage.policyStatus.getData().contains("Cancelled"), true,
					"Policy Summary Page", "Original policy is cancelled", false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC032 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC032 ", "Executed Successfully");
			}
		}
	}
}
