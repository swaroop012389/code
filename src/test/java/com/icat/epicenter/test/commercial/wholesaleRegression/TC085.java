/** Program Description: Perform Fee only Endorsement and Assert the Policy number on Endorsemnet quote
 *  Author			   : John
 *  Date of Creation   : 07/29/2020
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DateConversions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC085 extends AbstractCommercialTest {

	public TC085() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID085.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		LoginPage loginPage = new LoginPage();
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
		OverridePremiumAndFeesPage overridePremiumandFees = new OverridePremiumAndFeesPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		DateConversions date = new DateConversions();
		String insuredName = testData.get("InsuredName") + date.getCurrentDate("MM/dd/YYYY_hh:mm:ss");
		boolean isTestPassed = false;

		try {
			// creating New account
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.createNewAccountProducer.click();
			homePage.namedInsured.waitTillVisibilityOfElement(60);
			homePage.namedInsured.scrollToElement();
			homePage.namedInsured.setData(insuredName);
			Assertions.passTest("Home Page", "Insured Name is " + testData.get("InsuredName"));
			homePage.waitTime(1);
			if (homePage.productArrow.checkIfElementIsPresent() && homePage.productArrow.checkIfElementIsDisplayed()) {
				homePage.productArrow.waitTillVisibilityOfElement(60);
				homePage.productArrow.scrollToElement();
				homePage.productArrow.click();
				homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			}
			if (homePage.effectiveDate.formatDynamicPath("1").checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath("1").checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath("1").scrollToElement();
				homePage.effectiveDate.formatDynamicPath("1").waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath("1").setData(testData.get("PolicyEffDate"));
			}
			homePage.goButton.click();
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			locationPage = eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// getting the quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// search the quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Quote successfully");

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Endorse fee only
			Assertions.addInfo("Policy Summary Page", "Initiating Fee only Endorsement");
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();

			// setting Endoresment Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on edit Fee only endorsement hyperlink
			endorsePolicyPage.feeOnlyEndorsement.waitTillVisibilityOfElement(60);
			endorsePolicyPage.feeOnlyEndorsement.scrollToElement();
			endorsePolicyPage.feeOnlyEndorsement.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);

			// Asserting override premium and fees page
			Assertions.addInfo("Override Premium and Fees Page", "Verifying the presence of Cancel Button");
			Assertions.verify(overridePremiumandFees.cancelButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Cancel button is displayed in Override Premium and Fees Page",
					false, false);
			overridePremiumandFees.cancelButton.click();

			// clicking on edit Fee only endorsement hyperlink
			endorsePolicyPage.feeOnlyEndorsement.waitTillVisibilityOfElement(60);
			endorsePolicyPage.feeOnlyEndorsement.scrollToElement();
			endorsePolicyPage.feeOnlyEndorsement.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);
			overridePremiumandFees.enterOverrideFeesDetails(testData);
			Assertions.passTest("Override Endorsement Page", "Details entered successfully");

			// Click on view endorsement quote button
			Assertions.verify(endorsePolicyPage.viewEndtQuoteButton.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "View Endorsement Quote button is displayed", false, false);
			scrollToBottomPage();
			endorsePolicyPage.viewEndtQuoteButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on View Endorsement Quote Button");

			// Asserting policy number on endorsement document
			Assertions.addInfo("Quote Details Page", "Asserting policy number on endorsement document");
			Assertions.verify(quoteDetailsPage.quoteNumber.getData().contains(policyNumber), true, "Quote Details Page",
					quoteDetailsPage.quoteNumber.getData() + " is displayed", false, false);
			scrollToTopPage();
			waitTime(3);// need wait time to scroll to top page
			quoteDetailsPage.closeBtn.click();

			// Click on Pend button
			Assertions.verify(endorsePolicyPage.pendButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Pend button is displayed", false, false);
			scrollToBottomPage();
			endorsePolicyPage.pendButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Pend Button");
			homePage.goToHomepage.click();

			// Search for account with insured name
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findBtnAccount.tab();
			homePage.findBtnAccount.waitTillVisibilityOfElement(60);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();

			// Click on Endorse PB link
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Asserting Delete Existing Endorsement Button
			Assertions.verify(endorsePolicyPage.deleteExistingEndorsementButton.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page",
					endorsePolicyPage.deleteExistingEndorsementButton.getData() + " button is displayed", false, false);
			endorsePolicyPage.deleteExistingEndorsementButton.click();

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on edit Fee only endorsement hyperlink
			endorsePolicyPage.feeOnlyEndorsement.waitTillVisibilityOfElement(60);
			endorsePolicyPage.feeOnlyEndorsement.scrollToElement();
			endorsePolicyPage.feeOnlyEndorsement.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);

			// Asserting Delete Existing Endorsement Button
			Assertions.addInfo("Endorse Policy Page", "Asserting Delete Existing Endorsement Button");
			Assertions.verify(
					endorsePolicyPage.deleteExistingEndorsementButton.checkIfElementIsPresent()
							&& endorsePolicyPage.deleteExistingEndorsementButton.checkIfElementIsDisplayed(),
					false, "Endorse Policy Page", "Delete Existing Endorsement Button is not displayed", false, false);

			// Signing out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 85", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 85", "Executed Successfully");
			}
		}
	}
}
