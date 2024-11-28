/** Program Description: Checking if HIHO Minimum Named Hurricane Deductible available is 10% for HIHO Policies on and after September 22, 2022 [Based on IO-20450]
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 08/09/2022
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountDetails;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC031 extends AbstractNAHOTest {

	public PNBTC031() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID31.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		DwellingPage dwellingPage = new DwellingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		AccountDetails accountDetails = new AccountDetails();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		Map<String, String> testData;
		String quoteNumber;
		String policyNumber;
		int quoteLen;
		final String PAGE_NAVIGATED = "Page Navigated";
		final String VALUES_ENTERED = "Values Entered successfully";
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		testData = data.get(dataValue1);

		// creating New account
		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home page loaded successfully", false, false);
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("New Account", "New Account created successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Building Page",
				"Dwelling Page loaded successfully", false, false);
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);

		// verify if 1%, 2%, 3%, 4%, 5% and 7.5% options are available in the drop down
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();

		Assertions.addInfo("Scenario 01",
				"Verifying if 1%, 2%, 3%, 4%, 5% and 7.5% options are available in the named hurricane dropdown");
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData().contains("1%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData().contains("2%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData().contains("3%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData().contains("4%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData().contains("5%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData().contains("7.5%"),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

		// selecting 2% as named hurricane deductible
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.scrollToElement();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.click();

		// click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get A quote");

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number 1 is : " + quoteNumber);

		// Click on account detail link
		accountOverviewPage.quoteAccDetails.scrollToElement();
		accountOverviewPage.quoteAccDetails.click();

		// click on edit
		accountOverviewPage.quoteEditAccDetails.scrollToElement();
		accountOverviewPage.quoteEditAccDetails.click();
		Assertions.passTest("Account Overview Page", "Clicked on edit account details");

		// change effective date
		Assertions.verify(accountDetails.reviewButton.checkIfElementIsDisplayed(), true, "Account Details Page",
				"Account Details Page Loaded successfully", false, false);
		testData = data.get(dataValue2);
		accountDetails.effectiveDate.setData(testData.get("PolicyEffDate"));
		accountDetails.effectiveDate.tab();
		waitTime(2);// wait time is needed to load the element
		if (accountDetails.wanttoContinue.checkIfElementIsPresent()
				&& accountDetails.wanttoContinue.checkIfElementIsDisplayed()) {
			accountDetails.wanttoContinue.waitTillVisibilityOfElement(60);
			accountDetails.wanttoContinue.scrollToElement();
			accountDetails.wanttoContinue.click();
		}
		Assertions.passTest("Account Details Page", "The Effective date entered is " + testData.get("PolicyEffDate"));

		// Click on create quote
		accountDetails.createQuoteBtn.scrollToElement();
		accountDetails.createQuoteBtn.click();

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 02", "Verifying the Default Named Hurricane Deductible displayed as 10%");
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), "10%", "Create Quote Page",
				"The default Named Hurricane deductible value " + createQuotePage.namedHurricaneData.getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

		// click on named hurricane deductible arrow
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.passTest("Create Quote Page", "Clicked on Named hurricane deductible arrow");
		Assertions.addInfo("Scenario 03",
				"Verifying the absence of Deductibles below 10% that is 1%, 2%, 3%, 4%, 5% and 7.5%");
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData().contains("1%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData().contains("2%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData().contains("3%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData().contains("4%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData().contains("5%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData().contains("7.5%"),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

		// Click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get A quote");

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number 2 is : " + quoteNumber);

		// adding this line of code in place of above commented code
		Assertions.addInfo("Scenario 04", "Verifying the Status of the first created Quote that is Quote 1 is Invalid");
		Assertions.verify(accountOverviewPage.quoteStatusData.formatDynamicPath("1").getData(), "Active",
				"Account Overview Page", "The status of the first created quote is "
						+ accountOverviewPage.quoteStatusData.formatDynamicPath("1").getData(),
				false, false);
		Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

		// click on quote number 1
		accountOverviewPage.quoteStatusData.formatDynamicPath("1").scrollToElement();
		accountOverviewPage.quoteStatusData.formatDynamicPath("1").click();

		// adding this line of code in place of above commented code
		Assertions.addInfo("Scenario 05", "Verifying the absence of request bind button when quote 1 is selected");
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsPresent(), true, "Account Overview Page",
				"Request bind button is present is verified", false, false);
		Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

		// Click on account detail link
		accountOverviewPage.quoteAccDetails.scrollToElement();
		accountOverviewPage.quoteAccDetails.click();

		// click on edit
		accountOverviewPage.quoteEditAccDetails.scrollToElement();
		accountOverviewPage.quoteEditAccDetails.click();
		Assertions.passTest("Account Overview Page", "Clicked on edit account details");

		// change effective date
		Assertions.verify(accountDetails.reviewButton.checkIfElementIsDisplayed(), true, "Account Details Page",
				"Account Details Page Loaded successfully", false, false);
		testData = data.get(dataValue1);
		accountDetails.effectiveDate.setData(testData.get("PolicyEffDate"));
		accountDetails.effectiveDate.tab();
		waitTime(2);// wait time is needed to load the element
		if (accountDetails.wanttoContinue.checkIfElementIsPresent()
				&& accountDetails.wanttoContinue.checkIfElementIsDisplayed()) {
			accountDetails.wanttoContinue.waitTillVisibilityOfElement(60);
			accountDetails.wanttoContinue.scrollToElement();
			accountDetails.wanttoContinue.click();
		}
		Assertions.passTest("Account Details Page", "The Effective date entered is " + testData.get("PolicyEffDate"));

		// Click on create quote
		accountDetails.createQuoteBtn.scrollToElement();
		accountDetails.createQuoteBtn.click();

		// Verify the named hurricane deductibles
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);

		// verify if 1%, 2%, 3%, 4%, 5% and 7.5% options are available in the dropdown
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 06",
				"Verifying if 1%, 2%, 3%, 4%, 5% and 7.5% options are available in the named hurricane dropdown");
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData().contains("1%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData().contains("2%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData().contains("3%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData().contains("4%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData().contains("5%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData().contains("7.5%"),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

		// selecting 2% as named hurricane deductible
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.scrollToElement();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.click();

		// click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get A quote");

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number 3 is : " + quoteNumber);

		// Click on account detail link
		accountOverviewPage.quoteAccDetails.scrollToElement();
		accountOverviewPage.quoteAccDetails.click();

		// click on edit
		accountOverviewPage.quoteEditAccDetails.scrollToElement();
		accountOverviewPage.quoteEditAccDetails.click();
		Assertions.passTest("Account Overview Page", "Clicked on edit account details");

		// change effective date
		Assertions.verify(accountDetails.reviewButton.checkIfElementIsDisplayed(), true, "Account Details Page",
				"Account Details Page Loaded successfully", false, false);
		testData = data.get(dataValue3);
		accountDetails.effectiveDate.setData(testData.get("PolicyEffDate"));
		accountDetails.effectiveDate.tab();
		waitTime(2);// wait time is needed to load the element
		if (accountDetails.wanttoContinue.checkIfElementIsPresent()
				&& accountDetails.wanttoContinue.checkIfElementIsDisplayed()) {
			accountDetails.wanttoContinue.waitTillVisibilityOfElement(60);
			accountDetails.wanttoContinue.scrollToElement();
			accountDetails.wanttoContinue.click();
		}
		Assertions.passTest("Account Details Page", "The Effective date entered is " + testData.get("PolicyEffDate"));

		// Click on create quote
		accountDetails.createQuoteBtn.scrollToElement();
		accountDetails.createQuoteBtn.click();

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);

		// click on named hurricane deductible arrow
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 07",
				"Verifying the absence of Deductibles below 10% that is 1%, 2%, 3%, 4%, 5% and 7.5%");
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData().contains("1%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData().contains("2%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData().contains("3%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData().contains("4%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData().contains("5%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData().contains("7.5%"),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

		// Click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get a quote");

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number 4 is : " + quoteNumber);

		// addingt this new line of code in place of above commented code
		Assertions.addInfo("Scenario 08",
				"Verifying the Status of quote 1 and quote 3 is Active and verifying the presence of request bind button when the status is invalid");
		Assertions.verify(accountOverviewPage.quoteStatusData.formatDynamicPath("1").getData(), "Active",
				"Account Overview Page", "The status of the first created quote is "
						+ accountOverviewPage.quoteStatusData.formatDynamicPath("1").getData(),
				false, false);

		// click on quote number 1
		accountOverviewPage.quoteStatusData.formatDynamicPath("1").scrollToElement();
		accountOverviewPage.quoteStatusData.formatDynamicPath("1").click();

		// added this new line of code in place of above commented code
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsPresent(), true, "Account Overview Page",
				"Request bind button is present is verified", false, false);

		// added this new line of code in place of above commented code
		Assertions.verify(accountOverviewPage.quoteStatusData.formatDynamicPath("3").getData(), "Active",
				"Account Overview Page", "The status of the first created quote is "
						+ accountOverviewPage.quoteStatusData.formatDynamicPath("3").getData(),
				false, false);

		// click on quote number 3
		accountOverviewPage.quoteStatusData.formatDynamicPath("3").scrollToElement();
		accountOverviewPage.quoteStatusData.formatDynamicPath("3").click();

		// added this new line of code in place for above commented code
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsPresent(), true, "Account Overview Page",
				"Request bind button is present is verified", false, false);

		// Click on account detail link
		accountOverviewPage.quoteAccDetails.scrollToElement();
		accountOverviewPage.quoteAccDetails.click();

		// click on edit
		accountOverviewPage.quoteEditAccDetails.scrollToElement();
		accountOverviewPage.quoteEditAccDetails.click();
		Assertions.passTest("Account Overview Page", "Clicked on edit account details");

		// change effective date
		Assertions.verify(accountDetails.reviewButton.checkIfElementIsDisplayed(), true, "Account Details Page",
				"Account Details Page Loaded successfully", false, false);
		testData = data.get(dataValue1);
		accountDetails.effectiveDate.setData(testData.get("PolicyEffDate"));
		accountDetails.effectiveDate.tab();
		waitTime(2);// wait time is needed to load the element
		if (accountDetails.wanttoContinue.checkIfElementIsPresent()
				&& accountDetails.wanttoContinue.checkIfElementIsDisplayed()) {
			accountDetails.wanttoContinue.waitTillVisibilityOfElement(60);
			accountDetails.wanttoContinue.scrollToElement();
			accountDetails.wanttoContinue.click();
		}
		Assertions.passTest("Account Details Page", "The Effective date entered is " + testData.get("PolicyEffDate"));

		// Click on create quote
		accountDetails.createQuoteBtn.scrollToElement();
		accountDetails.createQuoteBtn.click();

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);

		// verify if 1%, 2%, 3%, 4%, 5% and 7.5% options are available in the dropdown
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 09",
				"Verifying if 1%, 2%, 3%, 4%, 5% and 7.5% options are available in the named hurricane dropdown");
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData().contains("1%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData().contains("2%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData().contains("3%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData().contains("4%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData().contains("5%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData().contains("7.5%"),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

		// selecting 2% as named hurricane deductible
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.scrollToElement();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.click();

		// click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get A quote");

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number 5 is : " + quoteNumber);

		// click on request bind
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");

		// Enter Bind Details
		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		// enter effective date
		testData = data.get(dataValue3);
		requestBindPage.effectiveDate.waitTillVisibilityOfElement(60);
		if (!testData.get("PolicyEffDate").equals("")) {
			requestBindPage.effectiveDate.scrollToElement();
			requestBindPage.effectiveDate.clearData();
			requestBindPage.effectiveDate.setData(testData.get("PolicyEffDate"));
			requestBindPage.effectiveDate.tab();
		}
		Assertions.passTest("Request Bind Page",
				"The Policy Effective Date " + requestBindPage.effectiveDate.getData() + " entered successfully");

		requestBindPage.waitTime(3); // If waittime is removed,Element Not Interactable exception is
										// thrown.Waittillpresence and Waittillvisibility is not working here

		if (requestBindPage.wanttoContinue.checkIfElementIsPresent()
				&& requestBindPage.wanttoContinue.checkIfElementIsDisplayed()) {
			Assertions.verify(requestBindPage.wanttoContinue.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Pop Up displayed is verified", false, false);
			requestBindPage.wanttoContinue.waitTillVisibilityOfElement(60);
			requestBindPage.wanttoContinue.scrollToElement();
			requestBindPage.wanttoContinue.click();
			Assertions.passTest("Request Bind Page", "Clicked on Want to continue button");
		}

		// added new line of code
		homePage.goToHomepage.click();
		homePage.searchQuote(quoteNumber);

		Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Account Overview Page loaded successfully", false, false);

		// added this new line of code in place for above commented code
		Assertions.addInfo("Scenario 10",
				"Verifying the Status of quote 1,quote 3 and quote 5 is Active and verifying the presence of request bind button when the status is invalid");
		Assertions.verify(accountOverviewPage.quoteStatusData.formatDynamicPath("1").getData(), "Active",
				"Account Overview Page", "The status of the first created quote is "
						+ accountOverviewPage.quoteStatusData.formatDynamicPath("1").getData(),
				false, false);

		// click on quote number 1
		accountOverviewPage.quoteStatusData.formatDynamicPath("1").scrollToElement();
		accountOverviewPage.quoteStatusData.formatDynamicPath("1").click();

		// added this new line of code in place for above commented code
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsPresent(), true, "Account Overview Page",
				"Request bind button is present is verified", false, false);

		// added this new line of code in place for commented code
		Assertions.verify(accountOverviewPage.quoteStatusData.formatDynamicPath("3").getData(), "Active",
				"Account Overview Page", "The status of the first created quote is "
						+ accountOverviewPage.quoteStatusData.formatDynamicPath("3").getData(),
				false, false);

		// click on quote number 3
		accountOverviewPage.quoteStatusData.formatDynamicPath("3").scrollToElement();
		accountOverviewPage.quoteStatusData.formatDynamicPath("3").click();

		// added this new line of code in place for above commented code
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsPresent(), true, "Account Overview Page",
				"Request bind button is present is verified", false, false);

		// added this new line of code in place for above commented code
		Assertions.verify(accountOverviewPage.quoteStatusData.formatDynamicPath("5").getData(), "Active",
				"Account Overview Page", "The status of the first created quote is "
						+ accountOverviewPage.quoteStatusData.formatDynamicPath("5").getData(),
				false, false);

		// click on quote number 5
		accountOverviewPage.quoteStatusData.formatDynamicPath("5").scrollToElement();
		accountOverviewPage.quoteStatusData.formatDynamicPath("5").click();

		// added this new line of code in place for above commented code
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsPresent(), true, "Account Overview Page",
				"Request bind button is  present is verified", false, false);

		Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

		// click on create another quote btn
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();
		Assertions.passTest("Account Overview Page", "Clicked on Create another quote button");

		// verify the default named hurricane value is 10%
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		Assertions.addInfo("Scenario 11", "Verifying the Default Named Hurricane Deductible displayed as 10%");
		Assertions.verify(createQuotePage.namedHurricaneData.getData(), "10%", "Create Quote Page",
				"The default Named Hurricane deductible value " + createQuotePage.namedHurricaneData.getData()
						+ " displayed is verified",
				false, false);
		Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

		// click on named hurricane deductible arrow
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 12",
				"Verifying the absence of Deductibles below 10% that is 1%, 2%, 3%, 4%, 5% and 7.5%");
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData().contains("1%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData().contains("2%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData().contains("3%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData().contains("4%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData().contains("5%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData().contains("7.5%"),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

		// Click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get a quote");

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number 6 is : " + quoteNumber);

		// click on request bind
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");

		// Enter Bind Details
		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		// enter effective date
		testData = data.get(dataValue1);
		requestBindPage.effectiveDate.waitTillVisibilityOfElement(60);
		if (!testData.get("PolicyEffDate").equals("")) {
			requestBindPage.effectiveDate.scrollToElement();
			requestBindPage.effectiveDate.clearData();
			requestBindPage.effectiveDate.setData(testData.get("PolicyEffDate"));
			requestBindPage.effectiveDate.tab();
		}
		Assertions.passTest("Request Bind Page",
				"The Policy Effective Date " + requestBindPage.effectiveDate.getData() + " entered successfully");

		requestBindPage.waitTime(3); // If waittime is removed,Element Not Interactable exception is
										// thrown.Waittillpresence and Waittillvisibility is not working here

		if (requestBindPage.wanttoContinue.checkIfElementIsPresent()
				&& requestBindPage.wanttoContinue.checkIfElementIsDisplayed()) {
			Assertions.verify(requestBindPage.wanttoContinue.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Pop Up displayed is verified", false, false);
			requestBindPage.wanttoContinue.waitTillVisibilityOfElement(60);
			requestBindPage.wanttoContinue.scrollToElement();
			requestBindPage.wanttoContinue.click();
			Assertions.passTest("Request Bind Page", "Clicked on Want to continue button");
		}

		homePage.goToHomepage.click();
		homePage.searchQuote(quoteNumber);

		Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Account Overview Page loaded successfully", false, false);

		// click on create another quote btn
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();
		Assertions.passTest("Account Overview Page", "Clicked on Create another quote button");

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);

		// verify if 1%, 2%, 3%, 4%, 5% and 7.5% options are available in the dropdown
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 13",
				"Verifying if 1%, 2%, 3%, 4%, 5% and 7.5% options are available in the named hurricane dropdown");
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData().contains("1%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData().contains("2%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData().contains("3%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData().contains("4%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData().contains("5%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData().contains("7.5%"),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

		// click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get A quote");

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number 7 is : " + quoteNumber);

		// click on request bind
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");

		// Enter Bind Details
		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		// enter effective date
		testData = data.get(dataValue3);
		requestBindPage.effectiveDate.waitTillVisibilityOfElement(60);
		if (!testData.get("PolicyEffDate").equals("")) {
			requestBindPage.effectiveDate.scrollToElement();
			requestBindPage.effectiveDate.clearData();
			requestBindPage.effectiveDate.setData(testData.get("PolicyEffDate"));
			requestBindPage.effectiveDate.tab();
		}
		Assertions.passTest("Request Bind Page",
				"The Policy Effective Date " + requestBindPage.effectiveDate.getData() + " entered successfully");

		requestBindPage.waitTime(3); // If waittime is removed,Element Not Interactable exception is
										// thrown.Waittillpresence and Waittillvisibility is not working here

		if (requestBindPage.wanttoContinue.checkIfElementIsPresent()
				&& requestBindPage.wanttoContinue.checkIfElementIsDisplayed()) {
			Assertions.verify(requestBindPage.wanttoContinue.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Pop Up displayed is verified", false, false);
			requestBindPage.wanttoContinue.waitTillVisibilityOfElement(60);
			requestBindPage.wanttoContinue.scrollToElement();
			requestBindPage.wanttoContinue.click();
			Assertions.passTest("Request Bind Page", "Clicked on Want to continue button");
		}
		homePage.goToHomepage.click();
		homePage.searchQuote(quoteNumber);

		Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
				"Account Overview Page", "Account Overview Page loaded successfully", false, false);

		// click on create another quote btn
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();
		Assertions.passTest("Account Overview Page", "Clicked on Create another quote button");

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);

		// click on named hurricane deductible arrow
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		Assertions.addInfo("Scenario 14",
				"Verifying the absence of Deductibles below 10% that is 1%, 2%, 3%, 4%, 5% and 7.5%");
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData().contains("1%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("1%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData().contains("2%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("2%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData().contains("3%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("3%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData().contains("4%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("4%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData().contains("5%"), true,
				"Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("5%").getData()
						+ " is verified",
				false, false);
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").checkIfElementIsDisplayed();
		Assertions.verify(
				createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData().contains("7.5%"),
				true, "Create Quote Page",
				"Named Hurricane dropdown contains the value "
						+ createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath("7.5%").getData()
						+ " is verified",
				false, false);
		Assertions.addInfo("Scenario 14", "Scenario 14 Ended");

		// click on get a quote
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Clicked on Get A quote");

		// Getting the Quote Number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page Loaded successfully", false, false);
		quoteLen = accountOverviewPage.quoteNumber.getData().length();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
		Assertions.passTest("Account Overview Page", "Quote Number 8 is : " + quoteNumber);

		// added this new line of code in place for above commented code.
		Assertions.addInfo("Scenario 15",
				"Verifying the Status of quote 1,quote 3, quote 5 and quote 7 is Active and verifying the presence of request bind button when the status is invalid");
		Assertions.verify(accountOverviewPage.quoteStatusData.formatDynamicPath("1").getData(), "Active",
				"Account Overview Page", "The status of the first created quote is "
						+ accountOverviewPage.quoteStatusData.formatDynamicPath("1").getData(),
				false, false);

		// click on quote number 1
		accountOverviewPage.quoteStatusData.formatDynamicPath("1").scrollToElement();
		accountOverviewPage.quoteStatusData.formatDynamicPath("1").click();

		// added this new line of code in place for above commented code.
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsPresent(), true, "Account Overview Page",
				"Request bind button is present is verified", false, false);

		// added this new line of code in place for above commented code.
		Assertions.verify(accountOverviewPage.quoteStatusData.formatDynamicPath("3").getData(), "Active",
				"Account Overview Page", "The status of the first created quote is "
						+ accountOverviewPage.quoteStatusData.formatDynamicPath("3").getData(),
				false, false);

		// click on quote number 3
		accountOverviewPage.quoteStatusData.formatDynamicPath("3").scrollToElement();
		accountOverviewPage.quoteStatusData.formatDynamicPath("3").click();

		// added this new line of code in place for above commented code.
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsPresent(), true, "Account Overview Page",
				"Request bind button is present is verified", false, false);

		// added this new line of code in place for above commented code.
		Assertions.verify(accountOverviewPage.quoteStatusData.formatDynamicPath("5").getData(), "Active",
				"Account Overview Page", "The status of the first created quote is "
						+ accountOverviewPage.quoteStatusData.formatDynamicPath("5").getData(),
				false, false);

		// click on quote number 5
		accountOverviewPage.quoteStatusData.formatDynamicPath("5").scrollToElement();
		accountOverviewPage.quoteStatusData.formatDynamicPath("5").click();

		// added this new line of code in place for above commented code.
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsPresent(), true, "Account Overview Page",
				"Request bind button is present is verified", false, false);

		// added this new line of code in place for above commented code.
		Assertions.verify(accountOverviewPage.quoteStatusData.formatDynamicPath("7").getData(), "Active",
				"Account Overview Page", "The status of the first created quote is "
						+ accountOverviewPage.quoteStatusData.formatDynamicPath("7").getData(),
				false, false);

		// click on quote number 7
		accountOverviewPage.quoteStatusData.formatDynamicPath("7").scrollToElement();
		accountOverviewPage.quoteStatusData.formatDynamicPath("7").click();

		// added this new line of code in place for above commented code.
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsPresent(), true, "Account Overview Page",
				"Request bind button is present is verified", false, false);

		Assertions.addInfo("Scenario 15", "Scenario 15 Ended");

		// click on quote number 8
		accountOverviewPage.quoteStatusData.formatDynamicPath("8").scrollToElement();
		accountOverviewPage.quoteStatusData.formatDynamicPath("8").click();

		// click on request bind
		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");

		// enter bind details
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Get the policy number
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				"Policy Summary Page loaded successfully", false, false);
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policyNumber);

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", PAGE_NAVIGATED);

		// quite the browser
		Assertions.passTest("PNB_Regression_TC031", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}