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
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC058_NBAD003 extends AbstractNAHOTest {

	public TC058_NBAD003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBAD003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();

		String quoteNumber;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// assert Alt deductible section
			Assertions.verify(accountOverviewPage.otherDeductibleOptions.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Alt Deductible section present is verified", false, false);

			// looping through AOP section and asserting values
			for (int i = 1; i <= 3; i++) {
				int actualQuoteAOP = Integer.parseInt(accountOverviewPage.deductibleOptionsNAHO
						.formatDynamicPath("All Other Perils", 1).getData().replaceAll("[^\\d-.]", ""));
				System.out.println("quoteAOP: " + actualQuoteAOP);
				if (i == 1) {
					Assertions.verify(
							accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", i)
									.checkIfElementIsDisplayed(),
							true, "Account Overview Page",
							"Quote value All Other Perils selected : " + accountOverviewPage.deductibleOptionsNAHO
									.formatDynamicPath("All Other Perils", 1).getData(),
							false, false);

				} else {
					int altQuoteAOP = Integer.parseInt(accountOverviewPage.deductibleOptionsNAHO
							.formatDynamicPath("All Other Perils", i).getData().replaceAll("[^\\d-.]", ""));
					System.out.println("altQuoteAOP" + (i - 1) + " : " + altQuoteAOP);
					Assertions.verify(
							accountOverviewPage.deductibleOptionsNAHO
									.formatDynamicPath("All Other Perils", i).checkIfElementIsDisplayed(),
							true, "Account Overview Page",
							"Other Quote All Other Perils Options : Option " + (i - 1) + " : "
									+ accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Perils", i)
											.getData(),
							false, false);

					// Asserting alternate deductible option is lower than the Quote AOP
					int maximumAOP = Math.max(actualQuoteAOP, altQuoteAOP);
					System.out.println("maximumAOP: " + maximumAOP);
					if (maximumAOP == actualQuoteAOP) {
						Assertions.verify(
								accountOverviewPage.deductibleOptionsNAHO
										.formatDynamicPath("All Other Perils", i).checkIfElementIsDisplayed(),
								true, "Account Overview Page",
								"Other Quote All Other Perils Options : Option " + (i - 1)
										+ " : All Other Perils value is lower than the actual Quote All Other Perils value",
								false, false);
					}
				}
			}

			// looping through NS section and asserting values
			for (int i = 1; i <= 3; i++) {
				int actualQuoteNS = Integer.parseInt(accountOverviewPage.deductibleOptionsNAHO
						.formatDynamicPath("Named Storm", 1).getData().replaceAll("[^\\d-.]", ""));
				System.out.println("Actual quoteNS: " + actualQuoteNS);
				if (i == 1) {
					Assertions.verify(
							accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("Named Storm", i)
									.checkIfElementIsDisplayed(),
							true, "Account Overview Page",
							"Quote value Named Storm selected : " + accountOverviewPage.deductibleOptionsNAHO
									.formatDynamicPath("Named Storm", 1).getData(),
							false, false);
				} else {
					int altQuoteNS = Integer.parseInt(accountOverviewPage.deductibleOptionsNAHO
							.formatDynamicPath("Named Storm", i).getData().replaceAll("[^\\d-.]", ""));
					System.out.println("altQuoteNS: " + (i - 1) + " : " + altQuoteNS);
					Assertions.verify(
							accountOverviewPage.deductibleOptionsNAHO
									.formatDynamicPath("Named Storm", i).checkIfElementIsDisplayed(),
							true, "Account Overview Page",
							"Other Quote Named Storm Options : Option " + (i - 1) + " : "
									+ accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("Named Storm", i)
											.getData(),
							false, false);

					// Asserting alternate deductible option is lower than the Quote AOP
					int maximumNS = Math.max(actualQuoteNS, altQuoteNS);
					System.out.println("maximumNS: " + maximumNS);
					if (maximumNS == actualQuoteNS) {
						Assertions.verify(
								accountOverviewPage.deductibleOptionsNAHO
										.formatDynamicPath("Named Storm", i).checkIfElementIsDisplayed(),
								true, "Account Overview Page",
								"Other Quote Named Storm Options : Option " + (i - 1)
										+ " : Named Storm value is lower than the actual Quote Named Storm value",
								false, false);
					}
				}
			}
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC058 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC058 ", "Executed Successfully");
			}
		}
	}
}
