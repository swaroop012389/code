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
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC086_NBGEN017 extends AbstractNAHOTest {

	public TC086_NBGEN017() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBGEN017.xls";
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
		ReferQuotePage referQuotePage = new ReferQuotePage();
		LoginPage loginPage = new LoginPage();
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();

		String quoteNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int quoteLen;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData1 = data.get(data_Value2);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Creating New Account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Asserting hard stop message when prior loss added
			Assertions.verify(
					referQuotePage.referralMessagesNAHO
							.formatDynamicPath("unrepaired damage").getData().contains("unrepaired damage"),
					true, "Create Quote Page",
					"Hard Stop message for UnRepaired damages"
							+ referQuotePage.referralMessagesNAHO.formatDynamicPath("unrepaired damage").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					referQuotePage.referralMessagesNAHO
							.formatDynamicPath("liability loss").getData().contains("liability loss"),
					true, "Create Quote Page",
					"Hard Stop message for Liability loss type"
							+ referQuotePage.referralMessagesNAHO.formatDynamicPath("liability loss").getData()
							+ " is displayed",
					false, false);
			Assertions.verify(
					referQuotePage.referralMessagesNAHO
							.formatDynamicPath("open claim").getData().contains("open claim"),
					true, "Create Quote Page",
					"Hard Stop for Open claim "
							+ referQuotePage.referralMessagesNAHO.formatDynamicPath("open claim").getData()
							+ " is displayed",
					false, false);

			// Click on previous button
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Account Overview Page Loaded Successfully", false, false);

			// Click on prior loss edit link
			accountOverviewPage.priorLossEditLink.scrollToElement();
			accountOverviewPage.priorLossEditLink.click();
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prio Loss Page Loaded Successfully", false, false);

			// Select prior loss as no
			priorLossesPage.lossesInThreeYearsNo.scrollToElement();
			priorLossesPage.lossesInThreeYearsNo.click();
			priorLossesPage.continueButton.scrollToElement();
			priorLossesPage.continueButton.click();

			// Click on coverage link
			accountOverviewPage.coverageLink.scrollToElement();
			accountOverviewPage.coverageLink.click();
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Creat Quote Page",
					"Create Quote Page Loaded Successfully", false, false);

			// Click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as producer Successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// Assert account overview page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click On View Rate trace
			accountOverviewPage.viewOrPrintRateTrace.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();

			// Assert Rate trace values
			viewOrPrintRateTrace.waitTime(1);
			viewOrPrintRateTrace.windDiscountPriorClaim.waitTillPresenceOfElement(60);
			String discount = viewOrPrintRateTrace.windDiscountPriorClaim.getData();
			double discountValue = Double.parseDouble(discount);
			Assertions.verify(discountValue < 1, true, "View/Print Rate Trace Page",
					"Discount Prior Claims value when loss date is within 3 years in the past is " + discount, false,
					false);

			viewOrPrintRateTrace.backBtn.waitTillVisibilityOfElement(60);
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();

			// Delete account
			accountOverviewPage.deleteAccount.waitTillVisibilityOfElement(60);
			accountOverviewPage.deleteAccount.scrollToElement();
			accountOverviewPage.deleteAccount.click();
			accountOverviewPage.yesDeleteAccount.waitTillVisibilityOfElement(60);
			accountOverviewPage.yesDeleteAccount.scrollToElement();
			accountOverviewPage.yesDeleteAccount.click();
			Assertions.passTest("Account Overview Page", "Account Deleted Successfully");

			// Assert account overview page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData1, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			eligibilityPage.processSingleZip(testData);

			// Entering dwelling details
			dwellingPage.enterDwellingDetailsNAHO(testData);

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				priorLossesPage.selectPriorLossesInformation(testData1);
			}

			// Entering Quote Details
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Continue Button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click On View Rate trace
			accountOverviewPage.viewOrPrintRateTrace.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();

			// Assert Rate trace values
			String discount1 = viewOrPrintRateTrace.windDiscountPriorClaim.getData();
			double discountValue1 = Double.parseDouble(discount1);
			Assertions.verify(discountValue1 < 1, true, "View/Print Rate Trace Page",
					"Discount Prior Claims value when loss date is over 3 years in the past is " + discount1, false,
					false);

			viewOrPrintRateTrace.backBtn.waitTillVisibilityOfElement(60);
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC086 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC086 ", "Executed Successfully");
			}
		}
	}
}
