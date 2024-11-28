/** Program Description: Renewal re-quotes - Adding new buildings/location as producer
 *  Author			   : SM Netserv
 *  Date of Creation   : Sep 2018
**/

/**Renewal re-quotes - Adding new buildings/location as producer **/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC027 extends AbstractNAHOTest {

	public PNBTC027() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID27.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		DwellingPage dwellingPage = new DwellingPage();
		LocationPage locationPage = new LocationPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		LoginPage loginPage = new LoginPage();
		int data_Value1 = 0;
		int data_Value2 = 1;
		String quoteNumber;
		Map<String, String> testData = data.get(data_Value1);

		// Creation of NB policy
		// New account
		Assertions.passTest("Home Page", "Page Navigated");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "Values Entered Successfully");

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Add location and dwellings
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling page", "Dwelling added succesfully");

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
		String policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policyNumber);

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", "Page Navigated");

		// Find the policy by entering policy Number
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.waitTillVisibilityOfElement(60);
		homePage.findFilterArrow.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.waitTillVisibilityOfElement(60);
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.waitTillVisibilityOfElement(60);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Created Policy is selected");

		// Initiate the renewal
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		Assertions.passTest("Renew Policy Page", "Renewal initiated Successfully");

		// Release to Producer
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		accountOverviewPage.releaseRenewalToProducerButton.waitTillInVisibilityOfElement(60);
		accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
		accountOverviewPage.viewPreviousPolicyButton.click();

		// Log out of USM
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Signed Out as USM Successfully");

		// Login as Producer
		loginPage.refreshPage();
		loginPage.waitTime(2);
		loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as Producer Successfully");

		// asserting personal login details
		homePage.enterPersonalLoginDetails();
		Assertions.passTest("Home Page", "Page Navigated");

		// Code for Policy search in Producer Home page
		homePage.policySearchTab.scrollToElement();
		homePage.policySearchTab.click();
		homePage.policySearchTextbox.scrollToElement();
		homePage.policySearchTextbox.setData(policyNumber);
		homePage.policySearchBtn.waitTillVisibilityOfElement(60);
		homePage.policySearchBtn.scrollToElement();
		homePage.policySearchBtn.click();
		homePage.policyNum.waitTillVisibilityOfElement(60);
		homePage.policyNum.scrollToElement();
		homePage.policyNum.click();
		policySummaryPage.viewActiveRenewal.scrollToElement();
		policySummaryPage.viewActiveRenewal.click();

		// Clicking on Dwelling Link in Account Overview Page
		accountOverviewPage.dwelling1.scrollToElement();
		accountOverviewPage.dwelling1.click();
		if (accountOverviewPage.editDwelling.checkIfElementIsPresent()) {
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			if (accountOverviewPage.yesButton.checkIfElementIsPresent()) {
				accountOverviewPage.yesButton.scrollToElement();
				accountOverviewPage.yesButton.click();
			}
			if (accountOverviewPage.quoteExpiredPopupMsg.checkIfElementIsPresent()) {
				accountOverviewPage.quoteExpiredPopupMsg.scrollToElement();
				accountOverviewPage.quoteExpiredPopupMsg.click();
				accountOverviewPage.quoteExpiredPopupMsg.waitTillInVisibilityOfElement(60);
			}
		}
		Assertions.passTest("Dwelling Page", "Page Navigated");

		// code to check the fields are read only and delete icon is absent
		// Editing the existing dwelling and deletion is not allowed
		Assertions.verify(dwellingPage.address.checkIfElementIsEnabled(), false, "Dwelling Page",
				"Address Field is disabled and Read only", true, false);
		Assertions.verify(dwellingPage.propertyDescription.checkIfElementIsEnabled(), false, "Dwelling Page",
				"Property Description Field is disabled and Read only", true, false);
		Assertions.verify(dwellingPage.disabledDwellingType.checkIfElementIsPresent(), true, "Dwelling Page",
				"Dwelling Type Field is disabled and Read only", true, false);
		dwellingPage.dwellingCharacteristicsLink.click();
		Assertions.verify(dwellingPage.disabledConstructionType.checkIfElementIsPresent(), true, "Dwelling Page",
				"Construction Type Field is disabled and Read only", true, false);
		Assertions.verify(dwellingPage.dwellingYearBuilt1.checkIfElementIsEnabled(), false, "Dwelling Page",
				"Year Built Field is disabled and Read only", true, false);
		Assertions.verify(dwellingPage.livingSquareFootage.checkIfElementIsEnabled(), false, "Dwelling Page",
				"Living Square Footage Field is disabled and Read only", true, false);
		Assertions.verify(dwellingPage.nonLivingSquareFootage.checkIfElementIsEnabled(), false, "Dwelling Page",
				"Non Living Square Footage Field is disabled and Read only", true, false);
		Assertions.verify(dwellingPage.numOfFloors.checkIfElementIsEnabled(), false, "Dwelling Page",
				"No of Floors Field is disabled and Read only", true, false);
		Assertions.verify(dwellingPage.inspectionAvailable_yes.checkIfElementIsEnabled(), false, "Dwelling Page",
				"Inspection Available Yes Radio Button is disabled and Read only", true, false);
		Assertions.verify(dwellingPage.inspectionAvailable_no.checkIfElementIsEnabled(), false, "Dwelling Page",
				"Inspection Available No Radio Button is disabled and Read only", true, false);
		dwellingPage.roofDetailsLink.click();
		Assertions.verify(dwellingPage.disabledRoofShape.checkIfElementIsPresent(), true, "Dwelling Page",
				"Roof Shape Field is disabled and Read only", true, false);
		Assertions.verify(dwellingPage.yearRoofLastReplaced.checkIfElementIsEnabled(), false, "Dwelling Page",
				"Year Roof Last Replaced Field is disabled and Read only", true, false);
		Assertions.verify(dwellingPage.disabledWindMitigation.checkIfElementIsPresent(), true, "Dwelling Page",
				"Wind Mitigation Field is disabled and Read only", true, false);

		// Adding Dwelling 2 to Location 1
		testData = data.get(data_Value2);
		dwellingPage.waitTime(3);
		dwellingPage.addSymbol.waitTillPresenceOfElement(60);
		dwellingPage.addSymbol.waitTillVisibilityOfElement(60);
		dwellingPage.addSymbol.waitTillElementisEnabled(60);
		dwellingPage.addSymbol.waitTillButtonIsClickable(60);
		dwellingPage.addSymbol.scrollToElement();
		dwellingPage.addSymbol.click();

		dwellingPage.addNewDwelling.waitTillVisibilityOfElement(60);
		dwellingPage.addNewDwelling.scrollToElement();
		dwellingPage.addNewDwelling.click();
		dwellingPage.addDwellingDetails(testData, 1, 2);
		dwellingPage.addRoofDetails(testData, 1, 2);
		dwellingPage.enterDwellingValues(testData, 1, 2);
		dwellingPage.reviewDwelling();
		Assertions.passTest("Dwelling Page", "Dwelling 1-2 is added Successfully");

		// Adding Location 2
		// Entering Location Details
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		testData = data.get(data_Value2);
		dwellingPage.addSymbol.waitTillPresenceOfElement(60);
		dwellingPage.addSymbol.waitTillVisibilityOfElement(60);
		dwellingPage.addSymbol.waitTillElementisEnabled(60);
		dwellingPage.addSymbol.waitTillButtonIsClickable(60);
		dwellingPage.addSymbol.scrollToElement();
		dwellingPage.addSymbol.click();
		dwellingPage.addNewLocation.scrollToElement();
		dwellingPage.addNewLocation.click();
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location 2 details entered successfully");

		// Add location and dwellings
		dwellingPage.addSymbol.waitTillPresenceOfElement(60);
		dwellingPage.addSymbol.waitTillVisibilityOfElement(60);
		dwellingPage.addSymbol.waitTillElementisEnabled(60);
		dwellingPage.addSymbol.waitTillButtonIsClickable(60);
		dwellingPage.addSymbol.scrollToElement();
		dwellingPage.addSymbol.click();
		dwellingPage.addNewDwelling.scrollToElement();
		dwellingPage.addNewDwelling.click();
		dwellingPage.addDwellingDetails(testData, 2, 2);
		dwellingPage.addRoofDetails(testData, 2, 2);
		dwellingPage.enterDwellingValues(testData, 2, 2);
		dwellingPage.reviewDwelling();
		Assertions.passTest("Dwelling page", "Dwelling added succesfully");
		dwellingPage.createQuote.waitTillVisibilityOfElement(60);
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		dwellingPage.createQuote.waitTillVisibilityOfElement(60);
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		Assertions.passTest("Create Quote Page", "Page Navigated");
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Values Updated");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Click on issue Quote and Release note
		Assertions.passTest("Account Overview Page", "Page Navigated");
		accountOverviewPage.issueQuoteButton.scrollToElement();
		accountOverviewPage.issueQuoteButton.click();
		accountOverviewPage.issueQuoteButton.waitTillInVisibilityOfElement(60);
		Assertions.verify(accountOverviewPage.issueMessage.checkIfElementIsDisplayed(), true, "Account Overview Page",
				accountOverviewPage.issueMessage.getData() + " is verified", false, false);

		// Log out of Producer
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Login Page", "Signed Out as Producer Successfully");

		// Login as USM
		loginPage.waitTime(2);
		loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
		Assertions.passTest("Home Page", "Logged in as USM Successfully");

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", "Page Navigated");

		// Find the policy by entering policy Number
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Created Policy is selected");
		policySummaryPage.viewActiveRenewal.scrollToElement();
		policySummaryPage.viewActiveRenewal.click();
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();
		testData = data.get(data_Value1);

		// Entering data in Request Bind Page
		Assertions.passTest("Request Bind Page", "Page Navigated");
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", "Values Entered Successfully");
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.requestBind.waitTillVisibilityOfElement(60);
		requestBindPage.requestBind.scrollToElement();
		requestBindPage.requestBind.click();
		requestBindPage.requestBind.waitTillInVisibilityOfElement(60);

		// Override Effective date in Request Bind Page
		if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
				&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
			requestBindPage.overrideEffectiveDate.scrollToElement();
			requestBindPage.overrideEffectiveDate.select();
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			requestBindPage.requestBind.scrollToElement();
			requestBindPage.requestBind.click();
			requestBindPage.requestBind.waitTillInVisibilityOfElement(60);
		}
		// Getting Policy Number
		Assertions.passTest("Policy Summary Page", "Page Navigated");
		Assertions.passTest("Policy Summary Page", policySummaryPage.getPolicynumber());
		Assertions.passTest("PNB_Regression_TC027", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}