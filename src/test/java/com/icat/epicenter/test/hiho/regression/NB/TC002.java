/** Program Description: To generate a HIHO policy with single location/dwelling and Mortgagee AI and assert values. added IO-21907 and IO-21433
 *  Author			   : SM Netserv
 *  Date of Creation   : June 2018
 **/

package com.icat.epicenter.test.hiho.regression.NB;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC002 extends AbstractNAHOTest {

	public TC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHONB/NBTCID02.xls";
	}

	public Map<String, String> testData;
	public BasePageControl basePage;
	public HomePage homePage;
	public LocationPage locationPage;
	public DwellingPage dwellingPage;
	public CreateQuotePage createQuotePage;
	public AccountOverviewPage accountOverviewPage;
	public RequestBindPage requestBindPage;
	public PolicySummaryPage policySummaryPage;
	public BindRequestSubmittedPage bindRequestPage;
	public BuildingUnderMinimumCostPage dwellingCost;
	public ApproveDeclineQuotePage approveDeclineQuotePage;
	public ConfirmBindRequestPage confirmBindRequestPage;
	public ReferralPage referralPage;
	String quoteNumber;
	String policyNumber;
	static String ext_rpt_msg = " is verified";
	static int data_Value1 = 0;
	static int data_Value2 = 1;
	static final String PAGE_NAVIGATED = "Page navigated";
	static final String VALUES_UPDATED = "Values updated";

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		dwellingCost = new BuildingUnderMinimumCostPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		policySummaryPage = new PolicySummaryPage();
		dwellingPage = new DwellingPage();
		confirmBindRequestPage = new ConfirmBindRequestPage();
		approveDeclineQuotePage = new ApproveDeclineQuotePage();
		referralPage = new ReferralPage();
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

		// Click on Override
		dwellingCost.clickOnOverride();

		// Verification in Dwelling Page
		Assertions.verify(dwellingPage.dwellingType.getData(),
				testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingType"), "Dwelling Page",
				"Dwelling Type" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.covA.getData(),
				"$" + testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovA"), "Dwelling Page",
				"Coverage A" + ext_rpt_msg, false, false);
		Assertions.verify(dwellingPage.covB.getData(),
				"$" + testData.get("L" + locationCount + "D" + dwellingCount + "-DwellingCovB"), "Dwelling Page",
				"Coverage B" + ext_rpt_msg, false, false);
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

		// Click on continue
		createQuotePage.continueButton.click();
		createQuotePage.continueButton.waitTillInVisibilityOfElement(60);
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);

		// Click on Request Bind
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind Page loaded successfully");

		// Getting Quote Number
		quoteNumber = requestBindPage.quoteNumber.getData();
		Assertions.passTest("Request Bind Page", "Quote Number :  " + quoteNumber);

		// Entering Request Bind Page Details
		requestBindPage.enterPolicyDetailsNAHO(testData);
		requestBindPage.enterPaymentInformationNAHO(testData);
		requestBindPage.addInspectionContact(testData);
		requestBindPage.addContactInformation(testData);
		requestBindPage.scrollToTopPage();
		requestBindPage.effectiveDate.scrollToElement();

		// Adding IO-21907
		testData = data.get(data_Value2);
		requestBindPage.effectiveDate.clearData();
		requestBindPage.effectiveDate.appendData(testData.get("PolicyEffDate"));
		requestBindPage.effectiveDate.tab();
		requestBindPage.waitTime(3);

		if(requestBindPage.OkButton.checkIfElementIsPresent()&& requestBindPage.OkButton.checkIfElementIsDisplayed()) {
			requestBindPage.OkButton.scrollToElement();
			requestBindPage.OkButton.click();
		}

		requestBindPage.scrollToBottomPage();
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();

		confirmBindRequestPage.waitTime(5);
		confirmBindRequestPage.effectiveDate.scrollToElement();

		// Asserting policy effective date from confirm bind request page
		testData = data.get(data_Value1);
		Assertions.addInfo("Scenario 01", "Asserting policy effective date from confirm bindrequest page");
		Assertions.verify(confirmBindRequestPage.effectiveDate.getData().contains(testData.get("PolicyEffDate")), true,
				"Request Bind Page", "Effective date from confirm bindrequest page is "
						+ confirmBindRequestPage.effectiveDate.getData() + " displayed",
				false, false);
		Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
		// IO-21907 Ended

		requestBindPage.confirmBindNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Go to HomePage
		if (homePage.goToHomepage.checkIfElementIsPresent() && homePage.goToHomepage.checkIfElementIsDisplayed()) {
			homePage.scrollToTopPage();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Quote successfullly");
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral Link successfullly");
			Assertions.passTest("Referral Page", "Referral Page openned successfullly");
			referralPage.clickOnApprove();
			approveDeclineQuotePage.clickOnApprove();
			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.select();
				approveDeclineQuotePage.approveButton.waitTillVisibilityOfElement(60);
				approveDeclineQuotePage.approveButton.scrollToElement();
				approveDeclineQuotePage.approveButton.click();
				waitTime(2);
			}
		}
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getCurrentDriver();
		WebElement elementToClick = WebDriverManager.getCurrentDriver()
				.findElement(By.xpath("(//button[contains(text(),'Approve')])[2]"));
		js.executeScript("arguments[0].click();", elementToClick);

		if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
				&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
			requestBindPage.overrideEffectiveDate.select();
			approveDeclineQuotePage.approveButton.waitTillVisibilityOfElement(60);
			approveDeclineQuotePage.approveButton.scrollToElement();
			approveDeclineQuotePage.approveButton.click();
			WebElement elementToClick1 = WebDriverManager.getCurrentDriver()
					.findElement(By.xpath("(//button[contains(text(),'Approve')])[2]"));
			js.executeScript("arguments[0].click();", elementToClick1);
		}

		// Adding IO-21433
		// verifying policy number displayed when entering 31 character email id in
		// 'your email address field in request bind page
		// 'fchee@insuranceagencyhawaii.com'
		Assertions.addInfo("Scenario 02",
				"verifying policy number displayed when entering 31 character email id in 'your email address field in request bind page 'fchee@insuranceagencyhawaii.com'");
		Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);
		Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
		// IO-21433 Ended

		// Verifying Insured name and effective date
		Assertions.addInfo("Scenario 03", "Verifying Insured name and effective date");
		Assertions.verify(policySummaryPage.effectiveDate.getData().contains(testData.get("PolicyEffDate")), true,
				"Policy Summary Page", "Policy Effective Date" + ext_rpt_msg, false, false);
		Assertions.verify(policySummaryPage.insuredName.getData(), testData.get("InsuredName"), "Policy Summary Page",
				"Insured Name" + ext_rpt_msg, false, false);
		Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.verify(policyNumber.startsWith("52-358"), true, "Policy Summary Page",
				"Policy Number format " + ext_rpt_msg, false, false);

		// Click on sign out
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("NB_Regression_TC002", "Executed Successfully");
	}
}
