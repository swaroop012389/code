/*Program Description: Check if the line Completed and signed diligent effort form is not present under Subject To of commercial retail renewal quote for FL state if the buildings have a non-habitational primary occupancy
Verify the Effective date field is prefilled and not able to edit for renewal quote
 * Author            : Pavan Mule
 * Date of creation  : 02/02/2022
 */
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC036 extends AbstractCommercialTest {

	public TC036() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID036.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location page",
					"Location page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building page",
					//"Building page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select peril page",
					"Select peril page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Quote number :  " + quoteNumber);

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request bind button successfully");

			Assertions.verify(
					requestBindPage.submit.checkIfElementIsPresent()
							&& requestBindPage.submit.checkIfElementIsDisplayed(),
					true, "Request bind page", "Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind request page",
					"Bind bequest page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home page", "Clicked on home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy summary page", "Policy number is : " + policyNumber);

			// Click on Renewal link
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();

			// Go to Home Page
			if (policySummarypage.expaacMessage.checkIfElementIsPresent()
					&& policySummarypage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				policySummarypage.renewPolicy.waitTillPresenceOfElement(60);
				policySummarypage.renewPolicy.scrollToElement();
				policySummarypage.renewPolicy.click();
				Assertions.passTest("Policy summary page", "Clicked on renewal link successfully");
			}

			// click on continue
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			if (policyRenewalPage.renewalYes.checkIfElementIsPresent()
					&& policyRenewalPage.renewalYes.checkIfElementIsDisplayed()) {
				policyRenewalPage.renewalYes.scrollToElement();
				policyRenewalPage.renewalYes.click();
			}

			// Getting renewal qoute number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Renewal quote number :  " + quoteNumber);

			// Click on view/print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account overview page", "Clicked on view/print full quote link");

			// Asserting Completed and signed diligent effort form Text not present in
			// commercial retail renewal Quote
			// page
			Assertions.addInfo("Scenario 02",
					"Assert the absence of Completed and signed diligent effort form For FL state");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/print full quote page", "view/print full Quote page loaded successfully", false, false);

			Assertions.verify(
					viewOrPrintFullQuotePage.subjectToWordings
							.formatDynamicPath("diligent effort form").checkIfElementIsPresent(),
					false, "View/print full quote page",
					"Completed and signed diligent effort form not displayed under subjectTo section is verified since the state is "
							+ testData.get("QuoteState") + " and Occupancy is" + testData.get("L1B1-PrimaryOccupancy"),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// click back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View/print full quote page", "Clicked on Back Button");

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Request Bind Page loaded successfully", false, false);

			// Verify the Effective date field is prefilled and not able to edit for renewal
			// quote
			testData = data.get(data_Value2);
			Assertions.addInfo("Scenario 03",
					"Verifying the Effective date field is prefilled and not able to edit for renewal quote");
			Assertions.verify(
					requestBindPage.effectiveDate.getData().contains(testData.get("TransactionEffectiveDate")), true,
					"Request Bind Page", "Effective date Field Prefilled is verified", false, false);
			Assertions.verify(requestBindPage.effectiveDate.getAttrributeValue("readonly").contains("readonly"), false,
					"Request Bind Page", "Not able to edit the Effective Date field is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// select Premium finance pay as payplan
			requestBindPage.enterPaymentInformation(testData);
			requestBindPage.addContactInformation(testData);

			// Verify the absence of Surplus License Number field
			Assertions.addInfo("Scenario 04",
					"Verifying the absence of Surplus Lines Licence Number field in Request bind page for Renewal quote");
			Assertions.verify(requestBindPage.contactSurplusLicenseNumber.checkIfElementIsDisplayed(), false,
					"Request Bind Page",
					"Surplus Lines Licence Number field not present is verified in Request bind page for Renewal quote",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			confirmBindRequestPage.confirmBind();

			// Verify the Error message when Premium Finance payment plan is selected
			// without adding PFC as AI type.
			requestBindPage.payplanErrorMessage.waitTillPresenceOfElement(60);
			requestBindPage.payplanErrorMessage.waitTillVisibilityOfElement(60);
			requestBindPage.payplanErrorMessage.scrollToElement();
			Assertions.addInfo("Scenario 04",
					"Verifying the Error message when Premium Finance payment plan is selected without adding PFC as AI type for renewal quote");
			Assertions.verify(requestBindPage.payplanErrorMessage.checkIfElementIsDisplayed(), true,
					"Request Bind Page",
					"The Error Message " + requestBindPage.payplanErrorMessage.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// add AI type as PFC
			Assertions.addInfo("Scenario 05",
					"Adding PFC as AI type and Payplan as Premium finace and bind the renewal quote");
			requestBindPage.addAdditionalInterest(testData);
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.confirmBind();
			Assertions.passTest("Request Bind Page",
					" Bound the Renewal quote when Premium Finance payment plan and PFC is added");
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC036 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC036 ", "Executed Successfully");
			}
		}
	}
}
