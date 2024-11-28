/** Program Description: To generate a HIHO policy with flood coverage and assert values.
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
import com.icat.epicenter.pom.FloodPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC003 extends AbstractNAHOTest {

	public TC003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID03.xls";
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
	public FloodPage floodPage;
	public Map<String, String> testData;
	public BasePageControl basePage;
	String quoteNumber;
	String policyNumber;
	static String ext_rpt_msg = " is verified";
	static int data_Value1 = 0;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		dwellingCost = new BuildingUnderMinimumCostPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		floodPage = new FloodPage();
		policySummaryPage = new PolicySummaryPage();
		dwellingPage = new DwellingPage();
		requestBindPage = new RequestBindPage();
		testData = data.get(data_Value1);

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

		// Entering Dwelling Details
		dwellingPage.addDwellingDetails(testData, locationCount, dwellingCount);
		dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
		dwellingPage.enterDwellingValues(testData, locationCount, dwellingCount);

		// Review Dwelling
		dwellingPage.reviewDwelling.click();
		dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

		// Verification in Dwelling Page
		Assertions.verify(dwellingPage.dwellingType.getData(),
				testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingType"), "Dwelling Page",
				"Dwelling Type" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.covC.getData(),
				"$" + testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovC"), "Dwelling Page",
				"Coverage C" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.covD.getData(),
				"$" + testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovD"), "Dwelling Page",
				"Coverage D" + ext_rpt_msg, false, false);

		// Click on Create Quote
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillInVisibilityOfElement(60);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.addOptionalCoverageDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		Assertions.verify(createQuotePage.mold.getData(), testData.get("Mold"), "Create Quote Page",
				"Mold Clean Up default value" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.mold.getAttrributeValue("unselectable"), "on", "Create Quote Page",
				"Mold Clean Up is unchangeable" + ext_rpt_msg, false, false);

		// Verifying Create quote page Details
		Assertions.verify(createQuotePage.namedHurricaneValue.getData(), testData.get("NamedHurricaneDedValue"),
				"Create Quote Page", "Named Hurricane" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.earthquakeData.getData(), testData.get("EQDeductible"), "Create Quote Page",
				"EQ Deductible" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.flood.checkIfElementIsPresent(), false, "Create Quote Page",
				"Flood not displayed" + ext_rpt_msg, false, false);
		Assertions.verify(createQuotePage.personalPropertyReplacementCostData.getData(),
				testData.get("ReplacementCost"), "Create Quote Page",
				"Personal Property Replacement Cost" + ext_rpt_msg, false, false);

		// Click on get a quote button
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		createQuotePage.getAQuote.waitTillInVisibilityOfElement(60);
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);

		// Click on Request Bind
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");

		// Getting Quote Number
		quoteNumber = requestBindPage.quoteNumber.getData();
		Assertions.passTest("Request Bind Page", "Quote Number :  " + quoteNumber);

		// Entering Request Bind Page Details
		basePage = requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Verifying Insured name and effective date
		Assertions.verify(policySummaryPage.effectiveDate.getData().contains(testData.get("PolicyEffDate")), true,
				"Policy Summary Page", "Policy Effective Date" + ext_rpt_msg, false, false);
		Assertions.verify(policySummaryPage.insuredName.getData(), testData.get("InsuredName"), "Policy Summary Page",
				"Insured Name" + ext_rpt_msg, false, false);
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);
		Assertions.verify(policyNumber.startsWith("52-358"), true, "Policy Summary Page",
				"Policy Number format " + ext_rpt_msg, false, false);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC003", "Executed Successfully");
	}
}
