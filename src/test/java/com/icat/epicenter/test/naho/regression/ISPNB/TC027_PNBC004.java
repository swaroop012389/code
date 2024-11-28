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
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC027_PNBC004 extends AbstractNAHOTest {

	public TC027_PNBC004() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBC004.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();

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

			// Asserting named storm ded values for Address 4567 Shrimpers Row, Houma, 70363
			if (!createQuotePage.namedStormArrow_NAHO.getAttrributeValue("class").contains("disabled")) {
				createQuotePage.namedStormArrow_NAHO.scrollToElement();
				createQuotePage.namedStormArrow_NAHO.click();

				Assertions.verify(
						createQuotePage.namedStormDeductibleOption.formatDynamicPath("3%").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Named Storm value 3% is displayed for 4567 Shrimpers Row, Houma, 70363", false, false);
				Assertions.verify(
						createQuotePage.namedStormDeductibleOption.formatDynamicPath("3%").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Named Storm value "
								+ createQuotePage.namedStormDeductibleOption.formatDynamicPath("3%").getData()
								+ " is displayed for 4567 Shrimpers Row, Houma, 70363",
						false, false);
				Assertions.verify(
						createQuotePage.namedStormDeductibleOption.formatDynamicPath("5%").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Named Storm value "
								+ createQuotePage.namedStormDeductibleOption.formatDynamicPath("5%").getData()
								+ " is displayed for 4567 Shrimpers Row, Houma, 70363",
						false, false);
				Assertions.verify(
						createQuotePage.namedStormDeductibleOption.formatDynamicPath("10%").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Named Storm value "
								+ createQuotePage.namedStormDeductibleOption.formatDynamicPath("10%").getData()
								+ " is displayed for 4567 Shrimpers Row, Houma, 70363",
						false, false);
				Assertions.verify(
						createQuotePage.namedStormDeductibleOption.formatDynamicPath("25%").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Named Storm value "
								+ createQuotePage.namedStormDeductibleOption.formatDynamicPath("25%").getData()
								+ " is displayed for 4567 Shrimpers Row, Houma, 70363",
						false, false);
			}
			createQuotePage.scrollToBottomPage();
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			// Click on edit dwelling link
			accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();

			// update dwelling address
			dwellingPage.addDwellingDetails(testData1, 1, 1);

			// click create quote button
			dwellingPage.scrollToBottomPage();
			dwellingPage.createQuote.waitTillVisibilityOfElement(60);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Added the following code as protection call is not available message is
			// displayed
			if (dwellingPage.internalSection.checkIfElementIsPresent()
					&& dwellingPage.internalSection.checkIfElementIsDisplayed()) {
				dwellingPage.internalSection.waitTillVisibilityOfElement(60);
				dwellingPage.internalSection.scrollToElement();
				dwellingPage.internalSection.click();
				dwellingPage.protectionClassOverride.waitTillVisibilityOfElement(60);
				dwellingPage.protectionClassOverride.scrollToElement();
				dwellingPage.protectionClassOverride.setData(testData1.get("L1D1-ProtectionClassOverride"));
				dwellingPage.protectionClassOverride.tab();
				dwellingPage.reSubmit.scrollToElement();
				dwellingPage.reSubmit.click();
				if (dwellingPage.override.checkIfElementIsPresent()
						&& dwellingPage.override.checkIfElementIsDisplayed()) {
					dwellingPage.override.scrollToElement();
					dwellingPage.override.click();
				}
			}
			if (existingAccountPage.override.checkIfElementIsPresent()) {
				existingAccountPage.OverrideExistingAccount();
			}

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			if (dwellingPage.createQuote.checkIfElementIsPresent()
					&& dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			if (existingAccountPage.override.checkIfElementIsPresent()) {
				existingAccountPage.OverrideExistingAccount();
			}

			// Asserting named storm ded values for Address 9870 Scotland Ave, Baton Rouge,
			// LA 70807, USA
			if (!createQuotePage.namedStormArrow_NAHO.getAttrributeValue("class").contains("disabled")) {
				createQuotePage.namedStormArrow_NAHO.scrollToElement();
				createQuotePage.namedStormArrow_NAHO.click();
				Assertions.verify(
						createQuotePage.namedStormDeductibleOption.formatDynamicPath("3%").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Named Storm value "
								+ createQuotePage.namedStormDeductibleOption.formatDynamicPath("3%").getData()
								+ " is displayed for 9870 Scotland Ave, Baton Rouge, LA 70807, USA",
						false, false);
				Assertions.verify(
						createQuotePage.namedStormDeductibleOption.formatDynamicPath("3%").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Named Storm value "
								+ createQuotePage.namedStormDeductibleOption.formatDynamicPath("3%").getData()
								+ " is displayed for 9870 Scotland Ave, Baton Rouge, LA 70807",
						false, false);
				Assertions.verify(
						createQuotePage.namedStormDeductibleOption.formatDynamicPath("5%").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Named Storm value "
								+ createQuotePage.namedStormDeductibleOption.formatDynamicPath("5%").getData()
								+ " is displayed for 9870 Scotland Ave, Baton Rouge, LA 70807",
						false, false);
				Assertions.verify(
						createQuotePage.namedStormDeductibleOption.formatDynamicPath("10%").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Named Storm value "
								+ createQuotePage.namedStormDeductibleOption.formatDynamicPath("10%").getData()
								+ " is displayed for 9870 Scotland Ave, Baton Rouge, LA 70807",
						false, false);
				Assertions.verify(
						createQuotePage.namedStormDeductibleOption.formatDynamicPath("25%").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Named Storm value "
								+ createQuotePage.namedStormDeductibleOption.formatDynamicPath("25%").getData()
								+ " is displayed for 9870 Scotland Ave, Baton Rouge, LA 70807",
						false, false);
			}
			createQuotePage.refreshPage();

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
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Getting policy number
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
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillElementisEnabled(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();

			// Clear coverage
			createQuotePage.coverageCPersonalProperty.clearData();
			createQuotePage.refreshPage();

			// Assert for HO5 absence
			Assertions.verify(
					createQuotePage.formType_HO5.checkIfElementIsPresent()
							&& createQuotePage.formType_HO5.checkIfElementIsDisplayed(),
					false, "Create Quote Page", "HO5 form is not present when coverage C is not present", false, false);

			// Review and continue create quote page
			createQuotePage.scrollToBottomPage();
			createQuotePage.continueEndorsementButton.waitTillVisibilityOfElement(60);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.waitTillButtonIsClickable(60);
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// continue with endorsement the endorsement
			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			endorsePolicyPage.continueButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
			endorsePolicyPage.scrollToBottomPage();

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
			Assertions.failTest("PNBTC027 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC027 ", "Executed Successfully");
			}
		}
	}
}
