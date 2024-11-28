/** Program Description: To validate EQ deductible value
 *  Author			   : John
 *  Date of Creation   : August 2019
 **/

package com.icat.epicenter.test.hiho.regression.NB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC013 extends AbstractNAHOTest {

	public TC013() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID13.xls";
	}

	public HomePage homePage;
	public DwellingPage dwellingPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public BindRequestSubmittedPage bindRequestPage;
	public ReferralPage referralPage;
	public PolicySummaryPage policySummaryPage;
	public BuildingUnderMinimumCostPage dwellingCost;
	public Map<String, String> testData1;
	public Map<String, String> testData2;
	public BasePageControl basePage;
	String quoteNumber1;
	String quoteNumber2;
	static String ext_rpt_msg = " is verified";
	static int data_Value1 = 0;
	static int data_Value2 = 1;
	int covValue1;
	int covValue2;
	float covValueTot;
	float covValueTot1;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		dwellingCost = new BuildingUnderMinimumCostPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		dwellingPage = new DwellingPage();
		bindRequestPage = new BindRequestSubmittedPage();
		testData1 = data.get(data_Value1);
		testData2 = data.get(data_Value2);
		int eqDed1 = Integer.parseInt(testData1.get("EQDeductible").substring(0, 1));
		int eqDed2 = Integer.parseInt(testData2.get("EQDeductible").substring(0, 1));

		// Getting Location and Building count
		String locationNumber = testData1.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String dwellingNumber = testData1.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);

		// New account
		homePage.createNewAccountWithNamedInsured(testData1, setUpData);
		Assertions.passTest("Home Page", "New Account created successfully");
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

		// Entering Dwelling Details
		dwellingPage.addDwellingDetails(testData1, locationCount, dwellingCount);
		dwellingPage.addRoofDetails(testData1, locationCount, dwellingCount);
		dwellingPage.enterDwellingValues(testData1, locationCount, dwellingCount);

		// Review Dwelling
		dwellingPage.reviewDwelling.click();
		dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

		// Click on Create Quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(60);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData1);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Verifying Create quote page Details
		Assertions.passTest("Create Quote Page", "EQ Deductible Value for quote1 is: " + testData1.get("EQDeductible"));
		Assertions.passTest("Create Quote Page",
				"Coverage A value for quote1 is: " + testData1.get("L1D1-DwellingCovA"));
		Assertions.passTest("Create Quote Page",
				"Coverage B value for quote1 is: " + testData1.get("L1D1-DwellingCovB"));
		Assertions.passTest("Create Quote Page",
				"Coverage C value for quote1 is: " + testData1.get("L1D1-DwellingCovC"));
		Assertions.passTest("Create Quote Page",
				"Coverage D value for quote1 is: " + testData1.get("L1D1-DwellingCovD"));

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.getAQuote.waitTillInVisibilityOfElement(60);

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Quote1 Assertions
		quoteNumber1 = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account OverView Page", "Quote Number1: " + quoteNumber1);
		accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();
		for (int i = 3; i <= 6; i++) {
			accountOverviewPage.coverageValue.formatDynamicPath(i).waitTillVisibilityOfElement(60);
			accountOverviewPage.coverageValue.formatDynamicPath(i).scrollToElement();
			covValue1 = Integer.parseInt(
					accountOverviewPage.coverageValue.formatDynamicPath(i).getData().substring(1).replaceAll(",", ""));
			covValueTot = covValueTot + covValue1;
		}

		// Calculate percentage value
		float eqvalue1 = (eqDed1 * covValueTot) / 100;
		String eqvaluequote1 = Float.toString(eqvalue1);
		String actRes1 = eqvaluequote1.substring(0, (eqvaluequote1.length() - 2));
		String actRes2 = actRes1.replaceAll(",", "");
		String finalRes = accountOverviewPage.coverageValue.formatDynamicPath(9).getData().substring(0);

		// Assert Values
		Assertions.verify(finalRes.contains(testData1.get("EQDeductible")), true, "Quote Application Page",
				"EQ Deductible percent for Quote 1 is: " + testData1.get("EQDeductible"), true, true);
		Assertions.verify(finalRes.contains(testData1.get("EQDeductibleValue")), true, "Quote Application Page",
				"EQ Deductible value for Quote 1 is: " + "$" + actRes2, true, true);
		waitTime(5);
		accountOverviewPage.scrollToTopPage();
		waitTime(5);
		accountOverviewPage.goBackBtn.click();

		// create Quote2 and assert values
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.cova_NH.waitTillVisibilityOfElement(60);
		createQuotePage.cova_NH.clearData();
		waitTime(3);
		createQuotePage.cova_NH.setData("4000000");
		createQuotePage.cova_NH.tab();
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Verifying quote details
		Assertions.passTest("Create Quote Page", "EQ Deductible Value for quote2 is: " + testData2.get("EQDeductible"));
		Assertions.passTest("Create Quote Page",
				"Coverage A value for quote2 is: " + testData2.get("L1D1-DwellingCovA"));
		Assertions.passTest("Create Quote Page",
				"Coverage B value for quote2 is: " + testData2.get("L1D1-DwellingCovB"));
		Assertions.passTest("Create Quote Page",
				"Coverage C value for quote2 is: " + testData2.get("L1D1-DwellingCovC"));
		Assertions.passTest("Create Quote Page",
				"Coverage D value for quote2 is: " + testData2.get("L1D1-DwellingCovD"));
		createQuotePage.scrollToBottomPage();
		createQuotePage.getAQuote.waitTillButtonIsClickable(60);
		createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
		createQuotePage.getAQuote.moveToElement();
		createQuotePage.getAQuote.click();

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Quote2 Assertions
		quoteNumber2 = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account OverView Page", "Quote Number2: " + quoteNumber2);
		accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
		accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
		accountOverviewPage.viewPrintFullQuoteLink.click();
		for (int i = 3; i <= 6; i++) {
			accountOverviewPage.coverageValue.formatDynamicPath(i).waitTillVisibilityOfElement(60);
			accountOverviewPage.coverageValue.formatDynamicPath(i).scrollToElement();
			covValue2 = Integer.parseInt(
					accountOverviewPage.coverageValue.formatDynamicPath(i).getData().substring(1).replaceAll(",", ""));
			covValueTot1 = covValueTot1 + covValue2;
		}

		// Calculate percentage value
		float eqvalue2 = (eqDed2 * covValueTot1) / 100;
		String eqvaluequote2 = Float.toString(eqvalue2);
		String actRes3 = eqvaluequote2.substring(0, (eqvaluequote2.length() - 2));
		String actRes4 = actRes3.replace(",", "");
		String finalRes1 = accountOverviewPage.coverageValue1.formatDynamicPath(8).getData().substring(0);

		// Assert Values
		Assertions.verify(finalRes1.contains(testData2.get("EQDeductible")), true, "Quote Application Page",
				"EQ Deductible percent for Quote 2 is: " + testData2.get("EQDeductible"), true, true);
		Assertions.verify(finalRes1.contains(testData2.get("EQDeductibleValue")), true, "Quote Application Page",
				"EQ Deductible value for Quote 2 is: " + "$" + actRes4, true, true);
		waitTime(5);

		// Sign out
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC013", "Executed Successfully");

	}
}
