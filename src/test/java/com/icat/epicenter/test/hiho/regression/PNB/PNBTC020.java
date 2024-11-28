/** Program Description: To Check that the first year new business discount is applied on the deleted dwelling if the endorsement is reversed manually
*  Author			   : Aishwarya Rangasamy
*  Date of Creation    : 11/10/2018
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

public class PNBTC020 extends AbstractNAHOTest {

	public PNBTC020() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID20.xls";
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

		// Entering Location 1 Dwelling 1 Details
		dwellingPage = new DwellingPage();
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		createQuotePage.enterInsuredValues(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		accountOverviewPage.clickOnRequestBind(setUpData, quoteNumber);
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

		accountOverviewPage.clickOnRequestBind(setUpData, quoteNumber);
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
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		homePage.findBtnPolicy.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Home Page", "Renewed Policy is selected");

		// Endorse PB page - 1st Endorsement
		policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
		endorsePolicyPage.editLocOrBldgInformationLink.click();
		Assertions.passTest("Create a Quote Page", "Successfully navigated ");

		// Delete L1D2
		dwellingPage.dwellingLink.formatDynamicPath(1, 2).waitTillVisibilityOfElement(30);
		if (dwellingPage.dwellingLink.formatDynamicPath(1, 2).checkIfElementIsPresent()) {
			dwellingPage.dwellingLink.formatDynamicPath(1, 2).scrollToElement();
			dwellingPage.dwellingLink.formatDynamicPath(1, 2).click();
		}
		locationPage = new LocationPage();
		locationPage.delete.scrollToElement();
		locationPage.delete.click();
		locationPage.deleteLocation_Yes.waitTillVisibilityOfElement(60);
		locationPage.deleteLocation_Yes.click();
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();
		createQuotePage.enterQuoteDetails(testData);
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();
		Assertions.passTest("Location Page", "Deletion of L1D2 is successful");

		// View Rate Trace Discount values
		endorsePolicyPage.viewRateTraceButton.scrollToElement();
		endorsePolicyPage.viewRateTraceButton.click();
		rateTracePage.rateTraceHeader.waitTillPresenceOfElement(30);
		Assertions.verify(rateTracePage.location1Dwelling1Header.checkIfElementIsPresent(), true, "Rate Trace Page",
				"Endorsement 1: Location 1 - Dwelling 1-1 Header is present", false, false);
		Assertions.verify(rateTracePage.L1D1WindNBDiscValue.checkIfElementIsPresent(), true, "Rate Trace Page",
				"Endorsement 1: NB Discount value for L1D1 - Wind is present", false, false);
		Assertions.verify(rateTracePage.L1D1EarthquakeNBDiscValue.checkIfElementIsPresent(), true, "Rate Trace Page",
				"Endorsement 1: NB Discount value for L1D1 - Earthquake is present", false, false);
		Assertions.verify(rateTracePage.location2Dwelling1Header.checkIfElementIsPresent(), false, "Rate Trace Page",
				"Endorsement 1: L1D2 is not present", false, false);
		rateTracePage.closeButton.waitTillVisibilityOfElement(60);
		rateTracePage.closeButton.scrollToElement();
		rateTracePage.closeButton.click();
		rateTracePage.closeButton.waitTillInVisibilityOfElement(60);
		endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();
		endorsePolicyPage.completeButton.waitTillInVisibilityOfElement(60);
		endorsePolicyPage.scrollToBottomPage();
		endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
		endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
		endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
		endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
		endorsePolicyPage.closeButton.scrollToElement();
		endorsePolicyPage.closeButton.click();
		Assertions.passTest("Rate Trace Page", "Values verified");

		// Endorse PB Page - 2nd Endorsement
		policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
		endorsePolicyPage.changeCoverageOptionsLink.click();
		Assertions.passTest("Create a Quote Page", "Successfully navigated ");

		// Change NH and EQ deductibles
		testData = data.get(dataValue2);
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.scrollToElement();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.click();
		createQuotePage.earthquakeDeductibleArrow.scrollToElement();
		createQuotePage.earthquakeDeductibleArrow.click();
		createQuotePage.earthquakeDeductibleOption.formatDynamicPath(testData.get("EQDeductible")).scrollToElement();
		createQuotePage.earthquakeDeductibleOption.formatDynamicPath(testData.get("EQDeductible")).click();
		createQuotePage.enterInsuredValuesforNH(testData);
		createQuotePage.continueEndorsementButton.scrollToElement();
		createQuotePage.continueEndorsementButton.click();
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();
		Assertions.passTest("Create a Quote Page", "NH and EQ Deductibles values entered");

		// View Rate Trace Discount values
		endorsePolicyPage.viewRateTraceButton.scrollToElement();
		endorsePolicyPage.viewRateTraceButton.click();
		rateTracePage.rateTraceHeader.waitTillPresenceOfElement(30);
		Assertions.verify(rateTracePage.location1Dwelling1Header.checkIfElementIsPresent(), true, "Rate Trace Page",
				"Endorsement 2: Location 1 - Dwelling 1-1 Header is present", false, false);
		Assertions.verify(rateTracePage.L1D1WindNBDiscValue.checkIfElementIsPresent(), true, "Rate Trace Page",
				"Endorsement 2: NB Discount value for L1D1 - Wind is present", false, false);
		Assertions.verify(rateTracePage.L1D1EarthquakeNBDiscValue.checkIfElementIsPresent(), true, "Rate Trace Page",
				"Endorsement 2: NB Discount value for L1D1 - Earthquake is present", false, false);
		Assertions.verify(rateTracePage.location2Dwelling1Header.checkIfElementIsPresent(), false, "Rate Trace Page",
				"Endorsement 2: L1D2 is not present", false, false);
		rateTracePage.closeButton.scrollToElement();
		rateTracePage.closeButton.click();
		rateTracePage.closeButton.waitTillInVisibilityOfElement(60);
		endorsePolicyPage.completeButton.waitTillPresenceOfElement(60);
		endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();
		endorsePolicyPage.completeButton.waitTillInVisibilityOfElement(60);
		endorsePolicyPage.scrollToBottomPage();
		endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
		endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
		endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
		endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
		endorsePolicyPage.closeButton.scrollToElement();
		endorsePolicyPage.closeButton.click();
		Assertions.passTest("Rate Trace Page", "Rate Trace Page- Endorsement 2 :Values verified");

		// Endorse PB Page - 3rd Endorsement
		testData = data.get(dataValue1);
		policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
		endorsePolicyPage.editLocOrBldgInformationLink.click();
		Assertions.passTest("Create a Quote Page", "Successfully navigated ");

		// Add New Dwelling to Existing Location
		locationPage.addSymbol.waitTillPresenceOfElement(30);
		locationPage.addSymbol.click();
		locationPage.addNewDwelling.waitTillPresenceOfElement(30);
		locationPage.addNewDwelling.scrollToElement();
		locationPage.addNewDwelling.click();
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		testData = data.get(dataValue2);
		dwellingPage.addDwellingDetails(testData, 1, 3);
		dwellingPage.addRoofDetails(testData, 1, 3);
		dwellingPage.reviewDwelling();
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 3 " + VALUES_ENTERED);
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();
		createQuotePage.enterQuoteDetails(testData);
		createQuotePage.enterInsuredValuesforNH(testData);
		endorsePolicyPage.continueDiv.scrollToElement();
		endorsePolicyPage.continueDiv.click();
		endorsePolicyPage.scrollToBottomPage();
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();

		// View Rate Trace Discount values
		endorsePolicyPage.viewRateTraceButton.scrollToElement();
		endorsePolicyPage.viewRateTraceButton.click();
		rateTracePage.rateTraceHeader.waitTillPresenceOfElement(30);
		testData = data.get(dataValue1);
		Assertions.verify(rateTracePage.location1Dwelling1Header.checkIfElementIsPresent(), true, "Rate Trace Page",
				"Endorsement 3: Location 1 - Dwelling 1-1 Header is present", false, false);
		Assertions.verify(rateTracePage.L1D1WindNBDiscValue.checkIfElementIsPresent(), true, "Rate Trace Page",
				"Endorsement 3: NB Discount value for L1D1 - Wind is present", false, false);
		Assertions.verify(rateTracePage.L1D1EarthquakeNBDiscValue.checkIfElementIsPresent(), true, "Rate Trace Page",
				"Endorsement 3: NB Discount value for L1D1 - Earthquake is present", false, false);
		Assertions.verify(rateTracePage.location1Dwelling3Header.checkIfElementIsPresent(), true, "Rate Trace Page",
				"Endorsement 3: L1D3 is present", false, false);
		Assertions.verify(rateTracePage.L1D3WindNBDiscValue.getData(), testData.get("NB Discount value"),
				"Rate Trace Page", "Endorsement 3: NB Discount value for L1D3 - Wind is verified", false, false);
		Assertions.verify(rateTracePage.L1D3EarthquakeNBDiscValue.getData(), testData.get("NB Discount value"),
				"Rate Trace Page", "Endorsement 3: NB Discount value for L1D3 - Earthquake is verified", false, false);
		rateTracePage.closeButton.scrollToElement();
		rateTracePage.closeButton.click();
		rateTracePage.closeButton.waitTillInVisibilityOfElement(60);
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();
		endorsePolicyPage.completeButton.waitTillInVisibilityOfElement(60);
		endorsePolicyPage.scrollToBottomPage();
		endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
		endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
		endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
		endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
		endorsePolicyPage.closeButton.scrollToElement();
		endorsePolicyPage.closeButton.click();
		Assertions.passTest("Rate Trace Page", "Rate Trace Page- Endorsement 2 :Values verified");
		Assertions.passTest("PNB_Regression_TC020", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}