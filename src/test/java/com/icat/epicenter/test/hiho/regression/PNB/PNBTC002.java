/** Program Description: To test whether the endorsement is used to edit the location/Building information
 *						 and change the Named Insured/Mailing Address
 *  Author			   : Yeshashwini T.A
 *  Date of Creation   : 06/09/2018
 **/

package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC002 extends AbstractNAHOTest {

	public PNBTC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID02.xls";
	}

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
	EndorseInspectionContactPage endorseContactPage;
	Map<String, String> testData;
	static final String EXT_RPT_MSG = " is verified";
	static int dataValue1 = 0;
	static int dataValue2 = 1;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		dwellingPage = new DwellingPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		endorsePolicyPage = new EndorsePolicyPage();
		changeNamedInsuredPage = new ChangeNamedInsuredPage();
		locationPage = new LocationPage();
		homePage = new HomePage();
		createQuotePage = new CreateQuotePage();
		endorseContactPage = new EndorseInspectionContactPage();
		testData = data.get(dataValue1);

		// Create New Account
		Assertions.passTest("Home Page", "Page Navigated");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "Values Entered");

		// Entering Location Details
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetails(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

		// Entering Quote Details
		createQuotePage.enterQuoteDetails(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Clicking on Bind button in account Overview Page
		Assertions.passTest("Account Overview Page", "Account Overview page loaded successfully");
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Account Overview Page", "Quote generated successfully");

		// Entering Bind Details
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Getting NB Policy Number
		Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "NB Policy Number is : " + policyNumber);

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", "Page Navigated");

		// Finding the policy by entering policy Number
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findPolicyButton.scrollToElement();
		homePage.findPolicyButton.click();
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
		endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
		endorsePolicyPage.editLocOrBldgInformationLink.click();
		Assertions.passTest("Dwelling Page", "Dwelling Page loaded successfully");

		// adding new location
		testData = data.get(dataValue2);
		locationPage.addLocation(testData);

		// adding new dwelling for new location
		dwellingPage.addDwellingForNewLocation(testData);
		Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");
		dwellingPage.continueButton.scrollToElement();
		dwellingPage.continueButton.click();
		if (dwellingPage.pageName.getData().contains("Endorse Inspection")) {
			endorseContactPage.okButton.scrollToElement();
			endorseContactPage.okButton.click();
		}
		if (dwellingPage.pageName.getData().contains("Create a Quote")) {
			createQuotePage.enterQuoteDetailsforPNB(testData);
			Assertions.passTest("Create Quote Page", "Details entered successfully");
		}

		// click on Change named insured hyperlink
		endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
		endorsePolicyPage.changeNamedInsuredLink.click();
		Assertions.passTest("Change Named Insured Page", "Change Named Insured Page loaded successfully");

		// entering details in Change named insured page
		changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);
		Assertions.passTest("Change Named Insured Page", "Change Named Insured details entered successfully");

		// verifying the changes done in location and dwelling page
		Assertions.verify(dwellingPage.addedLocation.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Added New Location " + EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.addedDwelling.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Added New Dwelling " + EXT_RPT_MSG, false, false);

		// verifying the changes done in Change named insured page
		Assertions.verify(dwellingPage.addressChanged.getData(),
				testData.get("InsuredAddr1") + ", " + testData.get("InsuredCity") + ", " + testData.get("InsuredState")
						+ " " + testData.get("InsuredZIP"),
				"Endorse Policy Page", "Changed Mailing Address " + EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.emailChanged.getData(), testData.get("InsuredEmail"), "Endorse Policy Page",
				"Changed Email value " + EXT_RPT_MSG, false, false);
		Assertions.verify(dwellingPage.contactChanged.getData(),
				testData.get("InsuredPhoneNumAreaCode") + "." + testData.get("InsuredPhoneNumPrefix") + "."
						+ testData.get("InsuredPhoneNum"),
				"Endorse Policy Page", "Changed Contact details " + EXT_RPT_MSG, false, false);

		// clicking on next button
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();

		// clicking on continue button
		if (endorsePolicyPage.pageName.getData().contains("Overrides Required")) {
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
		}
		// verifying the transaction and total term premium
		Assertions.verify(endorsePolicyPage.transactionPremium.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Transaction Premium: " + endorsePolicyPage.transactionPremium.getData(), false, false);
		Assertions.verify(endorsePolicyPage.totalTermPremium.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Total term Premium: " + endorsePolicyPage.totalTermPremium.getData(), false, false);

		// clicking on complete button
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();

		// clicking on close button
		endorsePolicyPage.closeButton.scrollToElement();
		endorsePolicyPage.closeButton.click();
		Assertions.passTest("PNB_Regression_TC002", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}