/** Program Description: To check whether the discounts for HIHO New policy are applied at building level if user
* 						 adds another dwelling in another location during endorsement
*  Author			   : Aishwarya Rangasamy
*  Date of Creation    : 09/10/2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RateTracePage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC016 extends AbstractNAHOTest {

	public PNBTC016() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID16.xls";
	}

	HomePage homePage;
	DwellingPage dwellingPage;
	LocationPage locationPage;
	CreateQuotePage createQuotePage;
	AccountOverviewPage accountOverviewPage;
	PolicySummaryPage policySummaryPage;
	RequestBindPage requestBindPage;
	CancelPolicyPage cancelPolicyPage;
	ReinstatePolicyPage reinsatePolicyPage;
	Map<String, String> testData;
	EndorsePolicyPage endorsePolicyPage;
	EndorseInspectionContactPage endorseInspectionContactPage;
	RateTracePage rateTracePage;
	BasePageControl basePage = new BasePageControl();
	String quoteNumber;
	String policyNumber;
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_ENTERED = "Values Entered successfully";
	static int dataValue1 = 0;
	static int dataValue2 = 1;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		locationPage = new LocationPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		cancelPolicyPage = new CancelPolicyPage();
		reinsatePolicyPage = new ReinstatePolicyPage();
		endorsePolicyPage = new EndorsePolicyPage();
		endorseInspectionContactPage = new EndorseInspectionContactPage();
		rateTracePage = new RateTracePage();
		testData = data.get(dataValue1);

		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);
		dwellingPage = (DwellingPage) basePage;

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage = new DwellingPage();
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policySummaryPage.getPolicynumber());

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", PAGE_NAVIGATED);

		// Find the policy by entering policy Number
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Created Policy is selected");

		// Renew Policy
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();

		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Renewal Quote Number :  " + quoteNumber);

		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.confirmBind();
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Renewed Policy Number: " + policySummaryPage.getPolicynumber());

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", PAGE_NAVIGATED);

		// Find the policy by entering policy Number
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Renewed Policy is selected");

		// Endorse PB page
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
		endorsePolicyPage.editLocOrBldgInformationLink.click();
		Assertions.passTest("Create a Quote Page", "Successfully navigated ");

		// Add New Building to New Location
		locationPage.addSymbol.waitTillPresenceOfElement(30);
		locationPage.addSymbol.click();
		locationPage.addNewLocation.waitTillPresenceOfElement(30);
		locationPage.addNewLocation.scrollToElement();
		locationPage.addNewLocation.click();
		locationPage.addDwellingButton.waitTillPresenceOfElement(30);
		locationPage.addDwellingButton.scrollToElement();
		locationPage.addDwellingButton.click();
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		testData = data.get(dataValue2);
		dwellingPage.addDwellingDetails(testData, 2, 1);
		dwellingPage.addRoofDetails(testData, 2, 1);
		dwellingPage.reviewDwelling();
		Assertions.passTest("Dwelling Page", "Location 2 Dwelling 1 " + VALUES_ENTERED);
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();
		endorseInspectionContactPage.okButton.click();

		// Choose Coverage By Policy in Create Quote Page
		createQuotePage.chooseCoverageByPolicy.waitTillPresenceOfElement(30);
		createQuotePage.chooseCoverageByPolicy.click();
		createQuotePage.loading.waitTillInVisibilityOfElement(30);
		createQuotePage.enterInsuredValuesforNH(testData);

		createQuotePage.continueEndorsementButton.scrollToElement();
		createQuotePage.continueEndorsementButton.click();
		Assertions.passTest("Create Quote Page", "Choose Coverage By policy is selected");
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();
		endorsePolicyPage.viewRateTraceButton.scrollToElement();
		endorsePolicyPage.viewRateTraceButton.click();

		// Rate Trace Page
		rateTracePage.rateTraceHeader.waitTillPresenceOfElement(30);
		if (rateTracePage.location1Dwelling1Header.checkIfElementIsPresent()
				&& rateTracePage.location2Dwelling1Header.checkIfElementIsPresent()) {
			Assertions.verify(rateTracePage.L1D1WindNBDiscount.checkIfElementIsPresent(), true, "Rate Trace Page",
					"NB Discount for L1D1 - Wind is present", false, false);
			Assertions.verify(rateTracePage.L1D1EarthquakeNBDiscount.checkIfElementIsPresent(), true, "Rate Trace Page",
					"NB Discount for L1D1 - EarthQuake is present", false, false);
			Assertions.verify(rateTracePage.L2D1WindNBDiscount.checkIfElementIsPresent(), true, "Rate Trace Page",
					"NB Discount for L2D1 - Wind is present", false, false);
			Assertions.verify(rateTracePage.L2D1EarthquakeNBDiscount.checkIfElementIsPresent(), true,
					"Rate Trace Page", "NB Discount for L2D1 - EarthQuake is present", false, false);
		}
		rateTracePage.closeButton.waitTillVisibilityOfElement(30);
		rateTracePage.closeButton.scrollToElement();
		rateTracePage.closeButton.click();
		rateTracePage.closeButton.waitTillInVisibilityOfElement(30);

		// Wait Time is due to the Rate Trace pages' behavior
		Assertions.passTest("PNB_Regression_TC016", "Executed Successfully");
		homePage.signOutButton.waitTillVisibilityOfElement(30);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}