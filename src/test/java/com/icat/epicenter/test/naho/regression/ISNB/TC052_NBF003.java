package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC052_NBF003 extends AbstractNAHOTest {

	public TC052_NBF003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBF003.xls";
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
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		BasePageControl basePage = new BasePageControl();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		String totalPolicyFee;
		String totalInspectionFee;
		String quoteNumber;
		String totalPremiumValue;
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
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Assert total premium value before adding policy fee and inspection
			// fee
			totalPremiumValue = accountOverviewPage.totalPremiumValue.getData();
			Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The total Premium value before Overriding the fee " + totalPremiumValue + " displayed is verified",
					false, false);

			// Click on edit fees
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Assert policy fee before Adding
			totalPolicyFee = viewOrPrintFullQuotePage.policyFee.getData();
			Assertions.verify(viewOrPrintFullQuotePage.policyFee.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Policy fee applied on the account " + totalPolicyFee + " is verified ",
					false, false);

			// Assert inspection fee before Adding
			totalInspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData();
			Assertions.verify(viewOrPrintFullQuotePage.inspectionFee.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Inspection fee applied on the account when Coverage A is less than 1M is "
							+ totalInspectionFee + " is verified",
					false, false);

			basePage.scrollToTopPage();
			viewOrPrintFullQuotePage.backButton.click();

			// create another qouote with Cov A 1M
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			testData = data.get(dataValue2);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page", "Coverage A value updated to 1M");

			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Assert total premium value before adding policy fee and inspection
			// fee
			totalPremiumValue = accountOverviewPage.totalPremiumValue.getData();
			Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"The total Premium value before Overriding the fee " + totalPremiumValue + " displayed is verified",
					false, false);

			// Click on edit fees
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Assert policy fee before Adding
			totalPolicyFee = viewOrPrintFullQuotePage.policyFee.getData();
			Assertions.verify(viewOrPrintFullQuotePage.policyFee.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Policy fee applied on the account " + totalPolicyFee + " is verified ",
					false, false);

			// Assert inspection fee before Adding
			totalInspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData();
			Assertions.verify(viewOrPrintFullQuotePage.inspectionFee.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Inspection fee applied on the account when Coverage A is 1M is "
							+ totalInspectionFee + " is verified",
					false, false);

			basePage.scrollToTopPage();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on Override Premium Link
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.passTest("Override Premium and Fees Page ", "Clicked on Override Premium Link");

			testData = data.get(dataValue1);
			Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page ", "Override Premium and Fees Page  Loaded successfully", false,
					false);
			overridePremiumAndFeesPage.enterFeesDetailsNAHO(testData);
			Assertions.passTest("Override Premium and Fees Page ", "Override the Inspection fees and Policy fees");

			// Verifying the Added policy fee and Inspection fee After Adding
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Assert total premium value After adding policy fee and inspection fee
			accountOverviewPage.waitTime(2);
			accountOverviewPage.noteBar.scrollToElement();
			accountOverviewPage.noteBar.click();
			String totalPremiumValuenew = accountOverviewPage.totalPremiumValue.getData();
			Assertions.verify(accountOverviewPage.totalPremiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The total Premium value After adding policy fee and inspection fee "
							+ totalPremiumValuenew + " displayed is verified",
					false, false);

			// Assert policy fee and inspection fee
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Assert policy fee After Adding
			String totalPolicyFeenew = viewOrPrintFullQuotePage.policyFee.getData();
			Assertions.verify(viewOrPrintFullQuotePage.policyFee.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Policy fee After Addition " + totalPolicyFeenew + " is verified ", false,
					false);

			// Assert Inspection fee After Adding
			String totalInspectionFeenew = viewOrPrintFullQuotePage.inspectionFee.getData();
			Assertions.verify(viewOrPrintFullQuotePage.inspectionFee.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Inspection fee After Addition " + totalInspectionFeenew + " is verified",
					false, false);

			basePage.scrollToTopPage();
			viewOrPrintFullQuotePage.backButton.click();

			accountOverviewPage.noteBar.click();
			accountOverviewPage.userName.waitTillPresenceOfElement(60);
			accountOverviewPage.userName.waitTillVisibilityOfElement(60);

			// Verifying the Name of the person overriding the fees in comment
			// section
			String userName = accountOverviewPage.userName.getData();
			Assertions.verify(
					accountOverviewPage.userName.checkIfElementIsPresent()
							|| accountOverviewPage.userName.checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"The Identity Overriding the fees " + userName + " displayed is verified", false, false);

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC052 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC052 ", "Executed Successfully");
			}
		}
	}
}
