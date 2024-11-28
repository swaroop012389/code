package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC059_NBAI001 extends AbstractNAHOTest {

	public TC059_NBAI001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBAI001.xls";
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
		EditAdditionalInterestInformationPage editAdditionalInterestInformationPage = new EditAdditionalInterestInformationPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
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
			String quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Add Additional Interest
			accountOverviewPage.editAdditionalIntersets.scrollToElement();
			accountOverviewPage.editAdditionalIntersets.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Additional Interests");

			// Assert that the options presence
			editAdditionalInterestInformationPage.aITypeArrow.waitTillPresenceOfElement(60);
			editAdditionalInterestInformationPage.aITypeArrow.waitTillVisibilityOfElement(60);
			Assertions.verify(editAdditionalInterestInformationPage.aITypeArrow.checkIfElementIsDisplayed(), true,
					"Edit Additional Interest Information Page",
					"Edit Additional Interest Information Page loaded successfully", false, false);
			editAdditionalInterestInformationPage.aITypeArrow.click();
			if (editAdditionalInterestInformationPage.aITypeOption.formatDynamicPath(0, "Additional Insured")
					.checkIfElementIsPresent()
					&& editAdditionalInterestInformationPage.aITypeOption.formatDynamicPath(0, "Additional Insured")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(
						editAdditionalInterestInformationPage.aITypeOption.formatDynamicPath(0, "Additional Insured")
								.checkIfElementIsDisplayed(),
						true, "Edit Additional Interest Information Page ",
						"AI Type Additonal Insured displayed is verified", false, false);
				Assertions.verify(
						editAdditionalInterestInformationPage.aITypeOption.formatDynamicPath(0, "Additional Interest")
								.checkIfElementIsDisplayed(),
						true, "Edit Additional Interest Information Page ",
						"AI Type Additional Interest displayed is verified", false, false);
				Assertions.verify(
						editAdditionalInterestInformationPage.aITypeOption.formatDynamicPath(0, "Mortgagee")
								.checkIfElementIsDisplayed(),
						true, "Edit Additional Interest Information Page ", "AI Type Mortgagee displayed is verified",
						false, false);
				Assertions.verify(
						editAdditionalInterestInformationPage.aITypeOption.formatDynamicPath(0, "Loss Payee")
								.checkIfElementIsDisplayed(),
						true, "Edit Additional Interest Information Page", "AI Type Loss Payee displayed is verified",
						false, false);
			}
			editAdditionalInterestInformationPage.cancel.scrollToElement();
			editAdditionalInterestInformationPage.cancel.click();

			accountOverviewPage.editAdditionalIntersets.scrollToElement();
			accountOverviewPage.editAdditionalIntersets.click();

			// Adding Additional Interest
			editAdditionalInterestInformationPage.addAdditionalInterestNAHO(testData);

			editAdditionalInterestInformationPage.update.scrollToElement();
			editAdditionalInterestInformationPage.update.click();

			// Verifying international address can be given to create an AI
			Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View or print Full quote link");
			Assertions.verify(viewOrPrintFullQuotePage.aiType.formatDynamicPath(6).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "View Or Print Full Quote Page loaded successfully", false, false);
			String aiType = viewOrPrintFullQuotePage.aiType.formatDynamicPath(6).getData();
			Assertions.verify(viewOrPrintFullQuotePage.aiType.formatDynamicPath(6).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "Additional Interest Type " + aiType + " displayed is verified",
					false, false);
			String aiAddress = viewOrPrintFullQuotePage.aiType.formatDynamicPath(7).getData();
			Assertions.verify(viewOrPrintFullQuotePage.aiType.formatDynamicPath(7).checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page",
					"The international Address " + aiAddress + " displayed is verified", false, false);

			// ----Added IO-21618-----

			// Navigate back to account overview page
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click edit deductible and limits on the account overview page
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			testData = data.get(data_Value2);

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Verifying that Alt Deds Options does not appear when the created quote
			// reaches the minimum premium.

			if (accountOverviewPage.otherDeductibleOptions.checkIfElementIsPresent()
					&& accountOverviewPage.otherDeductibleOptions.checkIfElementIsDisplayed()) {
				Assertions.passTest("Account Overview Page",
						"Other Dedudctible Options are dipslayed when quote hit minimum premium ");
			}

			// ----Ended IO-21618-----

			// ----Added IO-21616-----
			Assertions.addInfo("Scenario 01",
					"Verifying that the minimum premium flag is displayed when the quote hit minimum premium.");
			Assertions.verify(
					accountOverviewPage.minPremiumFlag.checkIfElementIsDisplayed() && accountOverviewPage.minPremiumFlag
							.getData().equals("This Quote has hit Minimum Premium"),
					true, "Account Overview",
					"The minimum premium flag is displayed when the quote hit minimum premium and the message is verified.",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// ----IO-21616 Ended------

			// ----Added IO-21608-----

			// Click edit deductible and limits on the account overview page
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			testData = data.get(data_Value3);

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Checking the AOP deductible value for the created quote on the other
			// deductible options section.
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", 1)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Quote value All Other Perils selected is: " + accountOverviewPage.deductibleOptionsNAHO
							.formatDynamicPath("All Other Perils", 1).getData(),
					false, false);

			// Checking the Named Storm deductible value for the created quote on the other
			// deductible options section.
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO
							.formatDynamicPath("Named Storm", 1).checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Quote value Named Storm selected is: "
							+ accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("Named Storm", 1).getData(),
					false, false);

			Assertions.addInfo("Scenario 02",
					"Verifying that Alt Deds Option 1 for the AOP displays the valid value with the created quote.");
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", 2)
							.checkIfElementIsDisplayed()
							&& accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", 2)
									.getData().equals(testData.get("AltDeductibleAOP")),
					true, "Account Overview",
					"The Alt Deds Option 1 for the AOP displays the valid value with the created quote is verified.",
					false, false);
			Assertions.passTest("Account Overview Page", "The displayed alt deductible option 1 value for the AOP is "
					+ accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", 2).getData());
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			Assertions.addInfo("Scenario 03",
					"Verifying that Alt Deds Option 1 for the Named Storm displays the valid value with the created quote.");
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("Named Storm", 2)
							.checkIfElementIsDisplayed()
							&& accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("Named Storm", 2).getData()
									.equals(testData.get("AltDeductibleNS")),
					true, "Account Overview Page",
					"The Alt Deds Option 1 for the Named Storm displays the valid value with the created quote is verified.",
					false, false);
			Assertions.passTest("Account Overview Page",
					"The displayed alt deductible option 1 value for the Named Storm is "
							+ accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("Named Storm", 2).getData());
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on the alt quote option 1
			accountOverviewPage.quoteOptions1TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions1TotalPremium.click();

			Assertions.addInfo("Scenario 04",
					"Verifying the deductible values for NS and AOP after clicking on the alternative quote option 1.");
			Assertions.verify(
					accountOverviewPage.altQuoteDetails.checkIfElementIsDisplayed()
							&& accountOverviewPage.altQuoteDetails.getData().contains(testData.get("AltQuoteDetails")),
					true, "Account Overview",
					"The deductible values for NS and AOP on the alternative quote option 1 is verified", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// ----Ended IO-21608-----

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC059 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC059 ", "Executed Successfully");
			}
		}
	}
}
