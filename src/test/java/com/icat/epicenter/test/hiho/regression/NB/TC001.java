/** Program Description: To generate a HIHO policy with single location/dwelling with Course of Construction and assert values.
 *  Author			   : SM Netserv
 *  Date of Creation   : June 2018
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

public class TC001 extends AbstractNAHOTest {

	public TC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID01.xls";
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
	public Map<String, String> testData;
	public BasePageControl basePage;
	String quoteNumber;
	String policyNumber;
	static String ext_rpt_msg = " is verified";
	static int data_Value1 = 0;
	static int data_Value2 = 1;
	String niDwellingPage;
	String niCreateQuotePage;
	String niAccountOverviewPage;
	String niRequestBindPage;
	String niPolicySummaryPage;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		dwellingCost = new BuildingUnderMinimumCostPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		dwellingPage = new DwellingPage();
		bindRequestPage = new BindRequestSubmittedPage();
		testData = data.get(data_Value1);
		String namedInsured = testData.get("InsuredName");

		// Getting Location and Building count
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
		int dwellingCount = Integer.parseInt(dwellingNumber);

		// New account
		Assertions.passTest("Home Page", "Page Navigated");
		Assertions.passTest("Home Page", "New Account created successfully");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

		// Fetching Named Insured Value for ticket IO-20806
		dwellingPage.niDisplay.checkIfElementIsPresent();
		niDwellingPage = dwellingPage.niDisplay.getData().substring(0, 20);

		// Verifying the NI value does not have Format Characters
		if (niDwellingPage.equalsIgnoreCase(namedInsured)) {
			Assertions.verify(niDwellingPage, namedInsured, "Dwelling Page",
					"The NI Display does not show Format Charachters", false, false);
		}

		else {
			Assertions.verify(niDwellingPage, namedInsured, "Dwelling Page",
					"The NI Display contains show Format Charachters", false, false);
		}

		// Entering Dwelling Details
		dwellingPage.addDwellingDetails(testData, locationCount, dwellingCount);
		dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
		dwellingPage.enterDwellingValues(testData, locationCount, dwellingCount);

		// Review Dwelling
		dwellingPage.reviewDwelling.click();
		dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(60);

		// Asserting warning messge for course of construction
		Assertions.verify(dwellingPage.cOCWarningMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
				"Course Of Construction Warning message " + dwellingPage.cOCWarningMsg.getData() + " is displayed",
				false, false);

		// Update year built to current
		dwellingPage.dwellingCharacteristicsLink.waitTillVisibilityOfElement(60);
		dwellingPage.dwellingCharacteristicsLink.scrollToElement();
		dwellingPage.dwellingCharacteristicsLink.click();
		testData = data.get(data_Value2);
		dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));

		// Click on create quote button
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");
		dwellingPage.scrollToBottomPage();
		waitTime(2); // to overcome elementclickinterceptedexception
		dwellingPage.reSubmit.waitTillVisibilityOfElement(60);
		dwellingPage.reSubmit.scrollToElement();
		dwellingPage.reSubmit.click();

		// Click on Override
		dwellingCost.clickOnOverride();

		// Verification in Dwelling Page
		Assertions.verify(
				dwellingPage.dwellingType.getData()
						.contains(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingType")),
				true, "Dwelling Page", "Dwelling Type" + ext_rpt_msg, false, false);
		Assertions.verify(
				dwellingPage.covA.getData()
						.contains(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovA")),
				true, "Dwelling Page", "Coverage A" + ext_rpt_msg, false, false);
		Assertions.verify(
				dwellingPage.covB.getData()
						.contains(testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovB")),
				true, "Dwelling Page", "Coverage B" + ext_rpt_msg, false, false);

		// Click on Create Quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(60);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		testData = data.get(data_Value1);

		// Fetching niDisplay
		createQuotePage.niDisplay.checkIfElementIsPresent();
		niCreateQuotePage = createQuotePage.niDisplay.getData().substring(0, 20);

		// Verifying the NI value does not have Format Characters
		if (niCreateQuotePage.equalsIgnoreCase(namedInsured)) {
			Assertions.verify(niCreateQuotePage, namedInsured, "Create Quote Page",
					"The NI Display does not show Format Charachters", false, false);
		}

		else {
			Assertions.verify(niCreateQuotePage, namedInsured, "Create Quote Page",
					"The NI Display contains show Format Charachters", false, false);
		}

		// Entering Create quote page Details
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Verifying Create quote page Details
		Assertions.verify(createQuotePage.namedHurricaneValue.getData(), testData.get("NamedHurricaneDedValue"),
				"Create Quote Page", "Named Hurricane" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"EQ Deductible" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.flood.checkIfElementIsPresent(), false, "Create Quote Page",
				"Flood not displayed" + ext_rpt_msg, false, false);
		Assertions.verify(
				createQuotePage.ordinanceLaw.formatDynamicPath(testData.get("OrdinanceOrLaw")).getData()
						.contains(testData.get("OrdinanceOrLaw")),
				true, "Create Quote Page", "Ordinance Or Law" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.moldCleanup.getData().contains(testData.get("Mold")), true,
				"Create Quote Page", "Mold Clean Up default value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.moldCleanup.getAttrributeValue("unselectable"), "on", "Create Quote Page",
				"Mold Clean Up is unchangeable" + ext_rpt_msg, false, false);

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.getAQuote.waitTillInVisibilityOfElement(60);

		// Click on Override
		dwellingCost.clickOnOverride();

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// Verifying the NI Display in the Account Overview Page does not contain Format
		// Characters
		accountOverviewPage.niDisplay.checkIfElementIsPresent();
		niAccountOverviewPage = accountOverviewPage.niDisplay.getData().substring(0, 20);
		if (niAccountOverviewPage.equalsIgnoreCase(namedInsured)) {
			Assertions.verify(niAccountOverviewPage, namedInsured, "Account Overview Page",
					"The NI Display does not show Format Charachters", false, false);
		}

		else {
			Assertions.verify(niAccountOverviewPage, namedInsured, "Account Overview Page",
					"The NI Display contains show Format Charachters", false, false);
		}

		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);

		// Adding Code for IO-19410
		// Asserting Other Deductible Options table Total Premium values
		accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(2).scrollToElement();
		accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(2).waitTillVisibilityOfElement(60);
		Assertions
				.verify(accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(2).checkIfElementIsDisplayed(),
						true, "Account Overview Page",
						"Quote Options Total Premium 1 is displayed successfully : "
								+ accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(2).getData(),
						false, false);
		Assertions
				.verify(accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(3).checkIfElementIsDisplayed(),
						true, "Account Overview Page",
						"Quote Options Total Premium 2 is displayed successfully : "
								+ accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(3).getData(),
						false, false);
		Assertions
				.verify(accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(4).checkIfElementIsDisplayed(),
						true, "Account Overview Page",
						"Quote Options Total Premium 3 is displayed successfully : "
								+ accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(4).getData(),
						false, false);
		Assertions
				.verify(accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(5).checkIfElementIsDisplayed(),
						true, "Account Overview Page",
						"Quote Options Total Premium 4 is displayed successfully : "
								+ accountOverviewPage.quoteOptions1TotalPremium.formatDynamicPath(5).getData(),
						false, false);

		// Click on Request Bind
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");

		// Getting Quote Number
		quoteNumber = requestBindPage.quoteNumber.getData();
		Assertions.passTest("Request Bind Page", "Quote Number :  " + quoteNumber);

		// Fetching NI Display and verifying if there are no Format Characters present
		requestBindPage.niDisplay.checkIfElementIsPresent();
		niRequestBindPage = requestBindPage.niDisplay.getData().substring(0, 20);
		if (niRequestBindPage.equalsIgnoreCase(namedInsured)) {
			Assertions.verify(niRequestBindPage, namedInsured, "Request Bind Page",
					"The NI Display does not show Format Charachters", false, false);
		}

		else {
			Assertions.verify(niRequestBindPage, namedInsured, "Request Bind Page",
					"The NI Display contains show Format Charachters", false, false);
		}

		// Entering Request Bind Page Details
		basePage = requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		homePage = bindRequestPage.clickOnHomepagebutton();
		Assertions.passTest("Home Page", "Home Page loaded successfully");

		// Search Referral and approve
		referralPage = homePage.searchReferral(quoteNumber);
		Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");
		requestBindPage = referralPage.clickOnApprove();
		Assertions.passTest("Referral Page", "Referral request approved successfully");
		policySummaryPage = requestBindPage.approveRequest();
		Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

		// Verifying Insured name and effective date
		Assertions.verify(policySummaryPage.effectiveDate.getData().contains(testData.get("PolicyEffDate")), true,
				"Policy Summary Page", "Policy Effective Date" + ext_rpt_msg, false, false);
		Assertions.verify(policySummaryPage.insuredName.getData(), testData.get("InsuredName"), "Policy Summary Page",
				"Insured Name" + ext_rpt_msg, false, false);
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);
		Assertions.verify(policyNumber.startsWith("52-358"), true, "Policy Summary Page",
				"Policy Number format " + ext_rpt_msg, false, false);

		// Verifying the NI Display value does not contain Format Characters
		policySummaryPage.niDisplay.checkIfElementIsPresent();
		niPolicySummaryPage = policySummaryPage.niDisplay.getData().substring(0, 20);
		if (niPolicySummaryPage.equalsIgnoreCase(namedInsured)) {
			Assertions.verify(niPolicySummaryPage, namedInsured, "Polcy Summary Page",
					"The NI Display does not show Format Charachters", false, false);
		}

		else {
			Assertions.verify(niPolicySummaryPage, namedInsured, "Polcy Summary Page",
					"The NI Display contains show Format Charachters", false, false);
		}

		// Searching Policy on globle
		// Adding code for IO-19401
		homePage.scrollToTopPage();
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home Page loaded successfully", false, false);

		// Searching policy through global search
		homePage.policyGlobleSearch.appendData(policyNumber);
		homePage.searchedPolicyButton.waitTillPresenceOfElement(60);
		homePage.searchedPolicyButton.waitTillVisibilityOfElement(60);
		Assertions.verify(homePage.policyGlobleSearch.getData().equals(policyNumber), true, "Home Page",
				"Home Page Global search with Policy Number is successfully : " + homePage.policyGlobleSearch.getData(),
				false, false);

		homePage.policyGlobleSearch.clearData();
		homePage.policyGlobleSearch.appendData(quoteNumber);
		homePage.searchedPolicyButton.waitTillPresenceOfElement(60);
		homePage.searchedPolicyButton.waitTillVisibilityOfElement(60);
		Assertions.verify(homePage.policyGlobleSearch.getData().equals(quoteNumber), true, "Home Page",
				"Home Page Global search with Quote Number is successfully : " + homePage.policyGlobleSearch.getData(),
				false, false);
		String actualQuoteNamedInsured = homePage.searchResultNAHO.formatDynamicPath(quoteNumber).getData();
		namedInsured = testData.get("InsuredName");
		homePage.searchResultNAHO.formatDynamicPath(quoteNumber).click();
		Assertions.passTest("Home Page", "Searched for policy through Global Search successfully");

		// Click on view policy
		accountOverviewPage.viewPolicyButton.scrollToElement();
		accountOverviewPage.viewPolicyButton.click();
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				"Policy Summary Page loaded successfully", false, false);
		Assertions.verify(actualQuoteNamedInsured, namedInsured, "Policy Summary Page",
				"The Insured Name " + policySummaryPage.insuredName.getData() + " displayed is verified", false, false);

		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.verify(policyNumber.startsWith("52-35600") || policyNumber.startsWith("52-35800"), true,
				"Policy Summary Page", "Policy Number format " + ext_rpt_msg, false, false);
		Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);

		policySummaryPage.scrollToBottomPage();
		Assertions.verify(policySummaryPage.newNote.checkIfElementIsPresent(), true, "Policy Summary Page",
				"New Note button", false, false);
		policySummaryPage.newNote.waitTillPresenceOfElement(60);
		policySummaryPage.newNote.waitTillVisibilityOfElement(60);
		policySummaryPage.newNote.click();
		Assertions.passTest("Policy Summary Page", "Clicked on NewNote button successfully");

		policySummaryPage.selectCategory.formatDynamicPath(3).waitTillPresenceOfElement(60);
		policySummaryPage.selectCategory.formatDynamicPath(3).waitTillVisibilityOfElement(60);
		policySummaryPage.selectCategory.formatDynamicPath(3).click();
		Assertions.passTest("Policy Summary Page",
				"Account Category selected: " + policySummaryPage.selectCategory.formatDynamicPath(3).getData());
		policySummaryPage.enterNote.setData("Testing Account Notes");

		policySummaryPage.saveNoteButton.waitTillPresenceOfElement(60);
		policySummaryPage.saveNoteButton.waitTillVisibilityOfElement(60);
		policySummaryPage.saveNoteButton.scrollToElement();
		policySummaryPage.saveNoteButton.click();

		policySummaryPage.yesSaveNoteButton.checkIfElementIsDisplayed();
		policySummaryPage.yesSaveNoteButton.waitTillPresenceOfElement(60);
		policySummaryPage.yesSaveNoteButton.waitTillVisibilityOfElement(60);
		policySummaryPage.yesSaveNoteButton.click();

		// Adding account note assertions
		Assertions.passTest("Policy Summary Page", "Account Note added correctly,Account note is "
				+ policySummaryPage.accountNote.formatDynamicPath("Testing Account Notes").getData());

		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC001", "Executed Successfully");
	}
}