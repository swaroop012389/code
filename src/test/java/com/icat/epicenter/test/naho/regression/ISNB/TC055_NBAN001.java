/* Description :Verify  the AOWH deductible is defaulted to current deductible until it is higher than the calculated deductible for PB Endorsement,
  */

package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC055_NBAN001 extends AbstractNAHOTest {

	public TC055_NBAN001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBAN001.xls";
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
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		int data_Value1 = 0;
		int data_Value2 = 1;
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

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// click on Notebar link
			accountOverviewPage.noteBar.scrollToElement();
			accountOverviewPage.noteBar.click();

			// Adding New account note
			accountOverviewPage.newLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.newLink.click();
			accountOverviewPage.selectCatagoryArrow.click();
			String categoryOption = accountOverviewPage.selectCatagoryOption
					.formatDynamicPath(testData.get("CategoryOption")).getData();
			accountOverviewPage.selectCatagoryOption.formatDynamicPath(testData.get("CategoryOption"))
					.scrollToElement();
			accountOverviewPage.selectCatagoryOption.formatDynamicPath(testData.get("CategoryOption")).click();
			Assertions.passTest("Account Overview Page", "The category Selected is " + categoryOption);
			accountOverviewPage.textArea.setData(testData.get("AccountNotesTextArea"));
			accountOverviewPage.saveLink.click();
			accountOverviewPage.accountNotePopup.waitTillVisibilityOfElement(60);
			accountOverviewPage.yesBtn.click();

			String addedCategory = accountOverviewPage.categoryAdded.formatDynamicPath(0).getData();

			accountOverviewPage.refreshPage();

			Assertions.verify(categoryOption, addedCategory, "Account Overview Page",
					"The selected Category Inspection saved is verified", false, false);

			// Verifying the Test note" Test Note - NAHO Automation"
			accountOverviewPage.testNote.formatDynamicPath(0).waitTillPresenceOfElement(60);
			accountOverviewPage.testNote.formatDynamicPath(0).waitTillVisibilityOfElement(60);
			String testNotes = accountOverviewPage.testNote.formatDynamicPath(0).getData();
			Assertions.addInfo("Scenario 01", "Verifying the Test note");
			Assertions.verify(accountOverviewPage.testNote.formatDynamicPath(0).checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The test note displayed " + testNotes + " is verified ", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on Bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on bind button successfully");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting questions details entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Asserting policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Click on NPB endorsement link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// Entering Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Endorsement effective date entered successfully");

			// Click on change coverage option link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on change coverage option link");

			// Verify and asserting that the AOWH and AOP Deductible is defaulted to the
			// value selected in the NB quote
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create quote page loaded successfully", false, false);
			Assertions.addInfo("Scenario 02",
					"Verify and asserting that the AOWH and AOP Deductible is defaulted to the value selected in the NB quote");
			Assertions.verify(createQuotePage.aowhDeductibleData.getData(), testData.get("AOWHDeductibleValue"),
					"Create quote page",
					"AOWH deductible defaulted value shows which is slected in the NB quote, the default AOWH value is "
							+ createQuotePage.aowhDeductibleData.getData() + " displayed",
					false, false);
			Assertions.verify(createQuotePage.aopDeductibleData.getData(), testData.get("AOPDeductibleValue"),
					"Create quote page",
					"AOP deductible defaulted value shows which is slected in the NB quote, the default AOP value is "
							+ createQuotePage.aopDeductibleData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Change cov A value = $2000001M
			Assertions.passTest("Create Quote Page", "Original Cov A value " + testData.get("L1D1-DwellingCovA"));
			testData = data.get(data_Value2);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page", "Latest Cov A value " + testData.get("L1D1-DwellingCovA"));
			Assertions.passTest("Create Quote Page", "Endorsement details entred successfully");

			// Verify and assert that the AOWH and AOP deductibles selected in NB is changed
			// to override options. after changing Cov A, AOP changed from $5000 to
			// $5000(override) and AOWH from $25000 to $25000(override)
			Assertions.addInfo("Scenario 03",
					"Verify and assert that the AOWH and AOP deductibles selected in NB is changed to override options. after changing Cov A, AOP changed from $5000 to $5000(override) and AOWH from $25000 to $25000(override)");
			Assertions.verify(createQuotePage.aowhDeductibleData.getData().contains("$25,000 (Override)"), true,
					"Create quote page",
					"After changing Cov A value AOWH deductible selected in NB changed to override option, AOWH value is "
							+ createQuotePage.aowhDeductibleData.getData() + " displayed",
					false, false);
			Assertions.verify(createQuotePage.aopDeductibleData.getData().contains("$5,000 (Override)"), true,
					"Create quote page",
					"After changing Cov A value AOWH deductible selected in NB changed to override option, AOP value is "
							+ createQuotePage.aopDeductibleData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on continue endorsement button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			Assertions.passTest("Create Quote Page", "Clicked on continue endorsement button");
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			if (endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();
			}
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complee button");

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC055 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC055 ", "Executed Successfully");
			}
		}
	}
}
