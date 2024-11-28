package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC025_PNBC002 extends AbstractNAHOTest {

	public TC025_PNBC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBC002.xls";
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
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		ModifyForms modifyForms = new ModifyForms();

		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData1 = data.get(data_Value2);
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

			// Validating the premium amount
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on PB endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			policySummaryPage.endorsePB.waitTillInVisibilityOfElement(60);

			// Click on Edit Location or Building Information
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData1.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillElementisEnabled(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on change coverage option link successfully");

			// Asserting coverage values
			Assertions.verify(createQuotePage.coverageADwelling.getData().contains(testData.get("L1D1-DwellingCovA")),
					true, "Create Quote Page", "Coverage A value is: " + createQuotePage.coverageADwelling.getData(),
					false, false);
			Assertions.verify(
					createQuotePage.coverageBOtherStructures.getData().contains(testData.get("L1D1-DwellingCovB")),
					true, "Create Quote Page",
					"Coverage B value is: " + createQuotePage.coverageBOtherStructures.getData(), false, false);
			Assertions.verify(
					createQuotePage.coverageCPersonalProperty.getData().contains(testData.get("L1D1-DwellingCovC")),
					true, "Create Quote Page",
					"Coverage C value is: " + createQuotePage.coverageCPersonalProperty.getData(), false, false);
			Assertions.verify(createQuotePage.coverageDFairRental.getData().contains(testData.get("L1D1-DwellingCovD")),
					true, "Create Quote Page", "Coverage D value is: " + createQuotePage.coverageDFairRental.getData(),
					false, false);
			Assertions.verify(createQuotePage.covEValue.getData().contains(testData.get("L1D1-DwellingCovE")), true,
					"Create Quote Page", "Coverage E value is: " + createQuotePage.covEValue.getData(), false, false);
			Assertions.verify(createQuotePage.covFValue.getData().contains(testData.get("L1D1-DwellingCovF")), true,
					"Create Quote Page", "Coverage F value is: " + createQuotePage.covFValue.getData(), false, false);

			// Update dwelling address
			testData1 = data.get(data_Value2);
			createQuotePage.enterInsuredValuesNAHO(testData1, 1, 1);

			// Review and continue create quote page
			createQuotePage.scrollToBottomPage();
			createQuotePage.continueEndorsementButton.waitTillVisibilityOfElement(60);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			if (modifyForms.override.checkIfElementIsPresent() && modifyForms.override.checkIfElementIsDisplayed()) {
				modifyForms.override.scrollToElement();
				modifyForms.override.click();
			}

			// assert the warning message when coverage A more than $3000,000
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("Coverage A limit").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Warning message "
							+ createQuotePage.warningMessages.formatDynamicPath("Coverage A limit").getData()
							+ " displayed is verified",
					false, false);
			createQuotePage.continueButton.waitTillButtonIsClickable(60);
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}

			// continue with endorsement the endorsement
			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			endorsePolicyPage.continueButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
			endorsePolicyPage.scrollToBottomPage();
			Assertions.passTest("Endorse Policy Page", "Clicked on continue button successfully");

			// Asserting Dwelling address change in endorsement summary page
			Assertions.verify(endorseSummaryDetailsPage.buildingLevelChangesToColNAHO.formatDynamicPath(1, 5).getData(),
					"$" + testData1.get("L1D1-DwellingCovA"), "Create Quote Page",
					"Coverage A value is updated to "
							+ endorseSummaryDetailsPage.buildingLevelChangesToColNAHO.formatDynamicPath(1, 5).getData(),
					false, false);
			Assertions.verify(endorseSummaryDetailsPage.buildingLevelChangesToColNAHO.formatDynamicPath(2, 5).getData(),
					"$" + testData1.get("L1D1-DwellingCovB"), "Create Quote Page",
					"Coverage B value is updated to "
							+ endorseSummaryDetailsPage.buildingLevelChangesToColNAHO.formatDynamicPath(2, 5).getData(),
					false, false);
			Assertions.verify(endorseSummaryDetailsPage.buildingLevelChangesToColNAHO.formatDynamicPath(3, 5).getData(),
					"$" + testData1.get("L1D1-DwellingCovC"), "Create Quote Page",
					"Coverage C value is updated to "
							+ endorseSummaryDetailsPage.buildingLevelChangesToColNAHO.formatDynamicPath(3, 5).getData(),
					false, false);
			Assertions.verify(endorseSummaryDetailsPage.buildingLevelChangesToColNAHO.formatDynamicPath(4, 5).getData(),
					"$" + testData1.get("L1D1-DwellingCovD"), "Create Quote Page",
					"Coverage D value is updated to "
							+ endorseSummaryDetailsPage.buildingLevelChangesToColNAHO.formatDynamicPath(4, 5).getData(),
					false, false);
			Assertions.verify(endorseSummaryDetailsPage.buildingLevelChangesToColNAHO.formatDynamicPath(5, 5).getData(),
					"$" + testData1.get("L1D1-DwellingCovE"), "Create Quote Page",
					"Coverage E value is updated to "
							+ endorseSummaryDetailsPage.buildingLevelChangesToColNAHO.formatDynamicPath(5, 5).getData(),
					false, false);
			Assertions.verify(endorseSummaryDetailsPage.buildingLevelChangesToColNAHO.formatDynamicPath(6, 5).getData(),
					"$" + testData1.get("L1D1-DwellingCovF"), "Create Quote Page",
					"Coverage F value is updated to "
							+ endorseSummaryDetailsPage.buildingLevelChangesToColNAHO.formatDynamicPath(6, 5).getData(),
					false, false);

			// Complete the endorsement
			endorsePolicyPage.completeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.completeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.completeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.scrollToBottomPage();
			endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC025 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC025 ", "Executed Successfully");
			}
		}
	}
}
