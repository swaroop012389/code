/** Program Description: Renewal re-quotes - Adding new buildings and deleting existing buildings / locations.
 *  Author			   : SM Netserv
 *  Date of Creation   : Sep 2018
**/
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
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC026 extends AbstractNAHOTest {

	public PNBTC026() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID26.xls";
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
		Map<String, String> testData;
		final String ext_rpt_msg = " is verified";
		final String PAGE_NAVIGATED = "Page Navigated";
		final String VALUES_UPDATED = "Values Updated";
		final String VALUES_ENTERED = "Values Entered Successfully";
		final String VALUES_VERIFIED = "Values Verified";
		int Data_Value1 = 0;
		int Data_Value2 = 1;
		String quoteNumber;
		testData = data.get(Data_Value1);

		// Creation of NB policy
		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);

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

		// Initiate the renewal
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		Assertions.passTest("Renew Policy Page", "Renewal initiated Successfully");

		// Clicking on Dwelling Link in Account Overview Page
		accountOverviewPage.dwelling1.waitTillVisibilityOfElement(30);
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
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);

		// Deleting Location 3
		dwellingPage.location3.scrollToElement();
		dwellingPage.location3.click();
		dwellingPage.deleteLoc.waitTillButtonIsClickable(60);
		dwellingPage.deleteLoc.scrollToElement();
		dwellingPage.deleteLoc.click();
		dwellingPage.deleteBldgYes.scrollToElement();
		dwellingPage.deleteBldgYes.click();
		dwellingPage.deleteBldgYes.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Dwelling Page", "Deleted Location 3 Sucessfully");

		// Deleting Dwelling 1 of Location 2
		dwellingPage.location2.scrollToElement();
		dwellingPage.location2.click();
		dwellingPage.deleteLoc.scrollToElement();
		dwellingPage.deleteLoc.click();
		dwellingPage.deleteBldgYes.waitTillVisibilityOfElement(60);
		dwellingPage.deleteBldgYes.scrollToElement();
		dwellingPage.deleteBldgYes.click();
		dwellingPage.deleteBldgYes.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Dwelling Page", "Deleted Location 2 Dwelling 1 Sucessfully");

		// Adding Dwelling 3 to Location 1
		dwellingPage.location1.scrollToElement();
		dwellingPage.location1.click();
		dwellingPage.addSymbol.scrollToElement();
		dwellingPage.addSymbol.click();
		dwellingPage.addNewDwelling.scrollToElement();
		dwellingPage.addNewDwelling.click();
		dwellingPage.addNewDwelling.waitTillInVisibilityOfElement(30);
		testData = data.get(Data_Value2);
		dwellingPage.addDwellingDetails(testData, 1, 3);
		dwellingPage.addRoofDetails(testData, 1, 3);
		dwellingPage.enterDwellingValues(testData, 1, 3);
		dwellingPage.reviewDwelling();
		dwellingPage.createQuote.scrollToElement();
		dwellingPage.createQuote.click();
		Assertions.passTest("Create Quote Page", PAGE_NAVIGATED);
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", VALUES_UPDATED);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Click on issue Quote and Release note
		Assertions.passTest("Account Overview Page", PAGE_NAVIGATED);
		accountOverviewPage.issueQuoteButton.scrollToElement();
		accountOverviewPage.issueQuoteButton.click();
		accountOverviewPage.issueQuoteButton.waitTillInVisibilityOfElement(60);
		Assertions.verify(accountOverviewPage.issueMessage.checkIfElementIsDisplayed(), true, "Account Overview Page",
				accountOverviewPage.issueMessage.getData() + ext_rpt_msg, false, false);
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();
		accountOverviewPage.releaseRenewalToProducerButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("Account Overview Page", VALUES_VERIFIED);
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();

		// Entering data in Request Bind Page
		Assertions.passTest("Request Bind Page", PAGE_NAVIGATED);
		testData = data.get(Data_Value1);
		requestBindPage.addContactInformation(testData);
		Assertions.passTest("Request Bind Page", VALUES_ENTERED);
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
		Assertions.passTest("Policy Summary Page", PAGE_NAVIGATED);
		Assertions.passTest("Policy Summary Page", policySummaryPage.getPolicynumber());
		Assertions.passTest("PNB_Regression_TC026", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
	}
}