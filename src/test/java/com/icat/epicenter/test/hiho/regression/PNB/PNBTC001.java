/** Program Description: To test whether the endorsement is used to change the Coverage values and Inspection Contact
 *  Author			   : Aishwarya Rangasamy
 *  Date of Creation   : 17/08/2018
 **/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC001 extends AbstractNAHOTest {

	public PNBTC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID01.xls";
	}

	HomePage homePage;
	DwellingPage dwellingPage;
	LocationPage locationPage;
	CreateQuotePage createQuotePage;
	AccountOverviewPage accountOverviewPage;
	RequestBindPage requestBindPage;
	PolicySummaryPage policySummaryPage;
	EndorsePolicyPage endorsePolicyPage;
	EndorseInspectionContactPage endorseInspectContact;
	Map<String, String> testData;
	String quoteNumber;
	String policyNumber;
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_ENTERED = "Values Entered";
	static int dataValue1 = 0;
	static int dataValue2 = 1;
	String testName = String.class.getName();

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		locationPage = new LocationPage();
		dwellingPage = new DwellingPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		endorsePolicyPage = new EndorsePolicyPage();
		endorseInspectContact = new EndorseInspectionContactPage();
		dwellingPage = new DwellingPage();
		testData = data.get(dataValue1);

		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetails(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		if (dwellingPage.bringUptoCost.checkIfElementIsPresent()
				&& dwellingPage.bringUptoCost.checkIfElementIsDisplayed()) {
			dwellingPage.bringUptoCost.scrollToElement();
			dwellingPage.bringUptoCost.click();
		}
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
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Created Policy is selected");

		// Endorse PB page
		policySummaryPage.endorsePB.waitTillButtonIsClickable(60);
		policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("PolicyEffDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
		endorsePolicyPage.changeCoverageOptionsLink.click();
		Assertions.passTest("Create a Quote Page", "Successfully navigated ");

		// Modify NH and Inspection Area Code values
		testData = data.get(dataValue2);
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.scrollToElement();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.click();
		createQuotePage.continueEndorsementButton.scrollToElement();
		createQuotePage.continueEndorsementButton.click();
		if (createQuotePage.pageName.getData().contains("Create Quote")) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}
		endorsePolicyPage.changeInspectionContactLink.click();
		endorseInspectContact.inspectionAreaCode.setData(testData.get("InspectionAreaCode"));
		endorseInspectContact.okButton.click();
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();
		Assertions.passTest("Endorsement Policy Page",
				"Clicked on Complete after modifying the NH value and Inspection contact details");
		testData = data.get(dataValue1);

		// Validate the modified NH and Inspection AreaCode values
		endorsePolicyPage.namedHurricane.scrollToElement();
		String actualNamedHurricane = endorsePolicyPage.namedHurricane.getData().substring(0, 2);
		endorsePolicyPage.inspectionAreaCode.scrollToElement();
		String actualInspectionAreaCode = endorsePolicyPage.inspectionAreaCode.getData().substring(20, 23);
		testData = data.get(dataValue2);
		Assertions.verify(actualNamedHurricane, testData.get("NamedHurricaneDedValue"), "Endorsement Summary Page",
				"Changed Hurricane Value is reflected", false, false);
		Assertions.verify(actualInspectionAreaCode, testData.get("InspectionAreaCode"), "Endorsement Summary Page",
				"Changed Inspection AreaCode is reflected", false, false);
		Assertions.passTest("Endorsement Summary Page", "Validated new values of NH and Inspection contact details ");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC001", "Executed Successfully");

	}
}