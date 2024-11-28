/** Program Description: To check whether the Dwelling values are populated as per the NB values when PB endorsement is initiated
 *  Author			   : Yeshashwini T.A
 *  Date of Creation   : 06/09/2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC005 extends AbstractNAHOTest {

	public PNBTC005() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID05.xls";
	}

	BasePageControl page;
	LoginPage login;
	HomePage homePage;
	LocationPage locationPage;
	DwellingPage dwellingPage;
	CreateQuotePage createQuotePage;
	AccountOverviewPage accountOverviewPage;
	RequestBindPage requestBindPage;
	ConfirmBindRequestPage confirmBindRequestPage;
	PolicySummaryPage policySummaryPage;
	String policyNumber;
	String quoteNumber;
	EndorsePolicyPage endorsePolicyPage;
	ChangeNamedInsuredPage changeNamedInsuredPage;
	Map<String, String> testData;
	BasePageControl basePage = new BasePageControl();
	static final String EXT_RPT_MSG = " is verified";
	static final String VALUES_VERIFIED = "Values Verified";
	static int dataValue1 = 0;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		dwellingPage = new DwellingPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		endorsePolicyPage = new EndorsePolicyPage();
		testData = data.get(dataValue1);
		page = new BasePageControl();

		// Create New Account
		Assertions.passTest("Home Page", "Page Navigated");
		homePage = new HomePage();
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "Values Entered");
		dwellingPage = (DwellingPage) basePage;

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", "Page Navigated");
		dwellingPage = new DwellingPage();
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + "Values Entered");

		// Entering Quote Details
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = createQuotePage.enterQuoteDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Clicking on Bind button in account Overview Page
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

		// Entering Bind Details
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		page = requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Getting NB Policy Number
		policySummaryPage = (PolicySummaryPage) page;
		Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "NB Policy Number is : " + policyNumber);

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", "Page Navigated");

		// Finding the policy by entering policy Number
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Created Policy is selected");

		// clicking on Endorse PB link
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();

		// setting Endorsement Effective Date
		Assertions.passTest("Endorse Policy Page", "Endorse Policy Page loaded successfully");
		endorsePolicyPage.endorsementEffDate.scrollToElement();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		Assertions.passTest("Endorse Policy Page", "Details entered successfully");

		// clicking on edit Location/Building information hyperlink
		endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
		endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
		endorsePolicyPage.editLocOrBldgInformationLink.click();
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

		// clicking on Dwelling 1-1 link
		dwellingPage.dwelling1.scrollToElement();
		dwellingPage.dwelling1.click();

		// Dwelling details and dwelling characteristics verification for Dwelling 1
		Assertions.verify(dwellingPage.address.getAttrributeValue("value"),
				testData.get("L1D1-DwellingAddress") + ", " + testData.get("L1D1-DwellingCity") + ", HI" + " "
						+ testData.get("L1D1-DwellingZIP"),
				"Dwelling Page", "Dwelling 1 address " + EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.propertyDescription.getAttrributeValue("value"),
				testData.get("L1D1-DwellingDesc"), "Dwelling Page", "Dwelling 1 Property description " + EXT_RPT_MSG,
				false, false);
		Assertions.verify(dwellingPage.dwelType.getData(), testData.get("L1D1-DwellingType"), "Dwelling Page",
				"Dwelling 1 Dwelling Type " + EXT_RPT_MSG, false, false);
		if (dwellingPage.dwellingCharacteristicsLink.checkIfElementIsPresent()
				&& dwellingPage.dwellingCharacteristicsLink.checkIfElementIsDisplayed()) {
			dwellingPage.dwellingCharacteristicsLink.scrollToElement();
			dwellingPage.dwellingCharacteristicsLink.click();
		}
		Assertions.verify(dwellingPage.constType.getData(), testData.get("L1D1-DwellingConstType"), "Dwelling Page",
				"Dwelling 1 Construction Type " + EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.yearBuilt.getData(), testData.get("L1D1-DwellingYearBuilt"), "Dwelling Page",
				"Dwelling 1 Year Built " + EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.livingSquareFootage.getData(), testData.get("L1D1-DwellingSqFoot"),
				"Dwelling Page", "Dwelling 1 Living Square Footage " + EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.nonLivingSquareFootage.getData(), testData.get("L1D1-DwellingNonLivingSqFoot"),
				"Dwelling Page", "Dwelling 1 Non Living Square Footage " + EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.numOfFloors.getData(), testData.get("L1D1-DwellingFloors"), "Dwelling Page",
				"Dwelling 1 Number of Floors  " + EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.inspectionAvailable_no.checkIfElementIsSelected(), true, "Dwelling Page",
				"Dwelling 1 Inspection available from Primary Carrier " + EXT_RPT_MSG, false, false);

		// Roof Details verification for Dwelling 1
		dwellingPage.waitTime(3);
		dwellingPage.roofDetailsLink.scrollToElement();
		dwellingPage.roofDetailsLink.click();
		Assertions.verify(dwellingPage.roofShape.getData(), testData.get("L1D1-DwellingRoofShape"), "Dwelling Page",
				"Dwelling 1 Roof Shape " + EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.yearRoofLastReplaced.getData(), testData.get("L1D1-DwellingRoofReplacedYear"),
				"Dwelling Page", "Dwelling 1 Year Roof Last replaced " + EXT_RPT_MSG, false, false);
		Assertions.passTest("Dwelling Page", VALUES_VERIFIED);

		// clicking on review dwelling button
		dwellingPage.waitTime(3); // added wait time to click on review dwelling button. test failing in Q4
		dwellingPage.reviewDwelling.scrollToElement();
		dwellingPage.reviewDwelling.click();

		// clicking on continue button
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();

		// clicking on override button
		if (dwellingPage.pageName.getData().contains("Dwelling values")) {
			createQuotePage.override.scrollToElement();
			createQuotePage.override.click();
			createQuotePage.override.waitTillInVisibilityOfElement(60);
		}
		Assertions.passTest("PNB_Regression_TC005", "Executed Successfully");
		homePage.goToHomepage.click();
		homePage.signOutButton.click();

	}
}