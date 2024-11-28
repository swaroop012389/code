/** Program Description: Added IO-21610
 *  Author			   : Pavan Mule
 *  Date of Creation   : 02/04/2024
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
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC026_NBRE019 extends AbstractNAHOTest {

	public TC026_NBRE019() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBRE019.xls";
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
		ModifyForms modifyForms = new ModifyForms();
		LoginPage loginPage = new LoginPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing Variables
		String quoteNumber;
		int quoteLen;
		Map<String, String> testData;
		int data_Value1 = 0;
		int data_Value2 = 1;
		testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

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
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Adding forms 1) SwimmingPoolLiabilityExclusion 2)TotalAnimalExclusion
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			modifyForms.addForms(testData);
			Assertions.passTest("Modify Forms Page", "Modify Forms Added successfully");

			// Below if condition adding because of services down message comming
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting quote number
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// IO-21692
			// Click on account details
			testData = data.get(data_Value2);
			accountOverviewPage.accountDetailsLink.scrollToElement();
			accountOverviewPage.accountDetailsLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on account details link successfully");

			accountOverviewPage.editAccountDetails.scrollToElement();
			accountOverviewPage.editAccountDetails.click();
			Assertions.verify(accountOverviewPage.accountDetailsReviewButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.accountEffectiveDate.scrollToElement();
			accountOverviewPage.accountEffectiveDate.clearData();
			accountOverviewPage.accountEffectiveDate.appendData(testData.get("PolicyEffDate"));
			accountOverviewPage.accountEffectiveDate.tab();
			accountOverviewPage.yesIWantToContinue.waitTillVisibilityOfElement(60);
			accountOverviewPage.yesIWantToContinue.scrollToElement();
			accountOverviewPage.yesIWantToContinue.click();

			// Asserting hard stop message when changing the policy effective date after
			// quote expiration date hard stop message is 'Policy effective dates for this
			// product are only allowed 30 days in the future (on or before 05/15/2024)'
			Assertions.addInfo("Scenario 01", "Asserting hard stop message");
			Assertions.verify(
					accountOverviewPage.globalError.getData()
							.contains("Policy effective dates for this product are only allowed 30 days in the future"),
					true, "Account Overview Page",
					"Hard Stop message is " + accountOverviewPage.globalError.getData() + " displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			// IO-21692 ended

			// sign out as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as USM Successfully");

			// login as producer
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Login Page",
					"Logged in as Producer " + setUpData.get("NahoProducer") + " Successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Searching the Quote 1
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Quote Number searched successfully");

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "View Print Full Quote page loaded successfully", false, false);

			// Verifying presence of 1)SwimmingPoolLiabilityExclusion 2)Animal Exclusion
			// when adde as usm
			Assertions.addInfo("Scenario 02", "Verifying presence of Animal Exclusion and Swimming Pool Liability");
			Assertions.verify(
					viewOrPrintFullQuotePage.nhAndAOPValue
							.formatDynamicPath("Animal Exclusion").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Animal Exclusion Form is "
							+ viewOrPrintFullQuotePage.nhAndAOPValue.formatDynamicPath("Animal Exclusion").getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.formsIncluded.formatDynamicPath(1).getData().contains("Included"), true,
					"View Print Full Quote Page", "Animal Exclusion Form is "
							+ viewOrPrintFullQuotePage.formsIncluded.formatDynamicPath(1).getData() + " Successfully",
					false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.nhAndAOPValue
							.formatDynamicPath("Swimming Pool Liability Exclusion").checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					"Animal Exclusion Form is " + viewOrPrintFullQuotePage.nhAndAOPValue
							.formatDynamicPath("Swimming Pool Liability Exclusion").getData() + " displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.formsIncluded.formatDynamicPath(2).getData().contains("Included"), true,
					"View Print Full Quote Page", "Animal Exclusion Form is "
							+ viewOrPrintFullQuotePage.formsIncluded.formatDynamicPath(2).getData() + " Successfully",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC026 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC026 ", "Executed Successfully");
			}
		}
	}
}