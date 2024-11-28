/** Program Description:  Create an AOP Policy and rewrite it and assert the values for Cancel Rewrite policy summary page.
 *  Author			   : Yeshashwini T A
 *  Date of Creation   : 25/05/2021
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC115 extends AbstractCommercialTest {

	public TC115() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID115.xls";
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
		PriorLossesPage priorLossPage = new PriorLossesPage();
		GLInformationPage glInformationPage = new GLInformationPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
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

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// entering general liability details
			if (!testData.get("L1-GLLocationClass").equals("")) {
				Assertions.verify(glInformationPage.continueButton.checkIfElementIsDisplayed(), true,
						"GL Information Page", "GL Information Page loaded successfully", false, false);
				glInformationPage.enterGLInformation(testData);
				Assertions.passTest("GL Information Page", "GL Information details entered successfully");
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

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			}

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Find the policy by entering policy Number
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Created Policy is selected");

			// clicking on rewrite policy link in policy summary page
			Assertions.verify(policySummarypage.rewritePolicy.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			Assertions.addInfo("Policy Summary Page", "Rewriting the Policy");
			policySummarypage.rewritePolicy.scrollToElement();
			policySummarypage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Rewrite Link");

			// clicking on create another quote button in Account overview page
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview page loaded successfully", false, false);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create Another Quote");

			// clicking on override in building no longer quotable page
			if (buildingNoLongerQuotablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuotablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuotablePage.override.scrollToElement();
				buildingNoLongerQuotablePage.override.click();
			}

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// modifying deductibles and coverages in create quote page
			testData = data.get(dataValue2);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialPNB(testData);
			Assertions.passTest("Create Quote Page", "Deductibles and coverages details entered successfully");

			// Click on Continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// clicked on re-write bind in Account overview page
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview page loaded successfully", false, false);
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on Rewrite Bind");

			// entering details in request bind page
			testData = data.get(dataValue2);
			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.effectiveDate.waitTillVisibilityOfElement(60);
			requestBindPage.effectiveDate.scrollToElement();
			requestBindPage.effectiveDate.setData(testData.get("PolicyEffDate"));
			requestBindPage.effectiveDate.tab();
			requestBindPage.waitTime(2);// need waittime to load the element
			testData = data.get(dataValue1);
			requestBindPage.previousPolicyCancellationDate.waitTillVisibilityOfElement(60);
			requestBindPage.previousPolicyCancellationDate.scrollToElement();
			requestBindPage.previousPolicyCancellationDate.setData(testData.get("PreviousPolicyCancellationDate"));
			requestBindPage.previousPolicyCancellationDate.tab();
			Assertions.verify(
					requestBindPage.previousPolicyEffDate.getData()
							.equals(testData.get("PreviousPolicyCancellationDate")),
					true, "Request Bind Page",
					"Previous Policy Effective Date and Previous Policy cancellation date is same :"
							+ requestBindPage.previousPolicyEffDate.getData(),
					false, false);

			// cancel Re-write Section
			requestBindPage.waitTime(3);// need wait time to load the page
			requestBindPage.cancelRewriteHeader.waitTillVisibilityOfElement(60);
			String originalInspFee = requestBindPage.originalInspectionFee.getData();
			String earnedInspFee = requestBindPage.earnedInspectionFee.getData();
			String OriginalPolFee = requestBindPage.originalPolicyFee.getData();
			String earnedPolFee = requestBindPage.earnedPolicyFee.getData();
			Assertions.addInfo("Request Bind Page", "Asserting Original and Earned Inspection fee and Policy Fee");
			Assertions.verify(originalInspFee, "$175", "Request Bind Page",
					"Original Inspection Fee :" + requestBindPage.originalInspectionFee.getData(), false, false);
			Assertions.verify(earnedInspFee, "0.0", "Request Bind Page",
					"Earned Inspection Fee :" + requestBindPage.earnedInspectionFee.getData(), false, false);
			Assertions.verify(OriginalPolFee, "$250", "Request Bind Page",
					"Original Policy Fee :" + requestBindPage.originalPolicyFee.getData(), false, false);
			Assertions.verify(earnedPolFee, "0.0", "Request Bind Page",
					"Earned Policy Fee :" + requestBindPage.earnedPolicyFee.getData(), false, false);

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				// set carrier
				requestBindPage.carrierArrow.scrollToElement();
				requestBindPage.carrierArrow.click();
				for (int i = 1; i <= 5; i++) {
					if (requestBindPage.carrierOption.formatDynamicPath(i).getData().contains("ICM")) {
						requestBindPage.carrierOption.formatDynamicPath(i).scrollToElement();
						requestBindPage.carrierOption.formatDynamicPath(i).click();
						break;
					}
				}
			}
			requestBindPage.rewrite.scrollToElement();
			requestBindPage.rewrite.click();
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Getting Policy Number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			// Transaction Values
			Assertions.addInfo("Policy Summary Page", "Asserting Tranaction Values");
			Assertions.passTest("Policy Summary Page",
					"Active Transaction Premium : " + policySummarypage.PremiumFee.formatDynamicPath(1).getData());
			Assertions.passTest("Policy Summary Page", "Active Transaction Inspection Fee : "
					+ policySummarypage.PremiunInspectionFee.formatDynamicPath(1).getData());
			Assertions.passTest("Policy Summary Page", "Active Transaction Policy Fee : "
					+ policySummarypage.PremiumPolicyFee.formatDynamicPath(1).getData());

			// Annual values
			Assertions.addInfo("Policy Summary Page", "Asserting Annual Values");
			Assertions.passTest("Policy Summary Page",
					"Active Annual Premium : " + policySummarypage.PremiumFee.formatDynamicPath(2).getData());
			Assertions.passTest("Policy Summary Page", "Active Annual Inspection  Fee : "
					+ policySummarypage.PremiunInspectionFee.formatDynamicPath(2).getData());
			Assertions.passTest("Policy Summary Page",
					"Active Annual Policy Fee : " + policySummarypage.PremiumPolicyFee.formatDynamicPath(2).getData());

			// Term Total values
			Assertions.addInfo("Policy Summary Page", "Asserting Term Total Values");
			Assertions.passTest("Policy Summary Page",
					"Active Term Total Premium : " + policySummarypage.PremiumFee.formatDynamicPath(3).getData());
			Assertions.passTest("Policy Summary Page", "Active Term Total Inspection Fee : "
					+ policySummarypage.PremiunInspectionFee.formatDynamicPath(3).getData());
			Assertions.passTest("Policy Summary Page", "Active Term Total Policy Fee : "
					+ policySummarypage.PremiumPolicyFee.formatDynamicPath(3).getData());

			// Total Premium
			Assertions.addInfo("Plocy Summary Page", "Asserting Active Transaction Total Premium");
			Assertions.passTest("Policy Summary Page", "Active Transaction Total Premium : "
					+ policySummarypage.policyTotalPremium.formatDynamicPath(1).getData());

			// Asserting the Policy Numbers
			policySummarypage.policyNumber.scrollToElement();
			policySummarypage.policyNumber.waitTillVisibilityOfElement(60);
			Assertions.passTest("Policy Summary Page",
					"Policy Number is : " + policySummarypage.policyNumber.getData());
			Assertions.passTest("Policy Summary Page",
					"Rewritten Policy Number is : " + policySummarypage.rewrittenPolicyNumber.getData());

			// verifying rewritten record in policy summary page
			Assertions.addInfo("Policy Summary Page", "Verifying rewritten record in policy summary page");
			testData = data.get(dataValue1);
			Assertions.verify(
					policySummarypage.transactionType.formatDynamicPath(testData.get("TransactionType")).getData(),
					testData.get("TransactionType"), "Policy Summary Page", "Policy Rewritten Record Verified", false,
					false);
			policySummarypage.rewrittenPolicyNumber.click();

			// Transaction History Table
			policySummarypage.waitTime(3);// need wait time to load the page
			policySummarypage.transHistReason.formatDynamicPath(3).waitTillVisibilityOfElement(60);
			policySummarypage.transHistReason.formatDynamicPath(3).click();

			// Validate the Previous Transaction Summary Details
			policySummarypage.PremiumFee.formatDynamicPath(1).scrollToElement();
			policySummarypage.PremiumFee.formatDynamicPath(1).waitTillVisibilityOfElement(60);
			Assertions.passTest("Policy Summary Page", "Cancelled Policy Transaction Status");

			// Transaction Values
			Assertions.addInfo("Policy Summary Page", "Asserting Tranaction Values for the Rewritten Policy");
			Assertions.passTest("Policy Summary Page",
					"Cancelled Transaction Premium : " + policySummarypage.PremiumFee.formatDynamicPath(1).getData());
			Assertions.passTest("Policy Summary Page", "Cancelled Transaction Inspection Fee : "
					+ policySummarypage.PremiunInspectionFee.formatDynamicPath(1).getData());
			Assertions.passTest("Policy Summary Page", "Cancelled Transaction Policy Fee : "
					+ policySummarypage.PremiumPolicyFee.formatDynamicPath(1).getData());

			// Annual values
			Assertions.addInfo("Policy Summary Page", "Asserting Annual Values for the Rewritten Policy");
			Assertions.passTest("Policy Summary Page",
					"Cancelled Annual Premium : " + policySummarypage.PremiumFee.formatDynamicPath(2).getData());
			Assertions.passTest("Policy Summary Page", "Cancelled Annual Inspection  Fee : "
					+ policySummarypage.PremiunInspectionFee.formatDynamicPath(2).getData());
			Assertions.passTest("Policy Summary Page", "Cancelled Annual Policy Fee : "
					+ policySummarypage.PremiumPolicyFee.formatDynamicPath(2).getData());

			// Term Total values
			Assertions.addInfo("Policy Summary Page", "Asserting Term Total Values for the Rewritten Policy");
			Assertions.passTest("Policy Summary Page",
					"Cancelled Term Total Premium : " + policySummarypage.PremiumFee.formatDynamicPath(3).getData());
			Assertions.passTest("Policy Summary Page", "Cancelled Term Total Inspection Fee : "
					+ policySummarypage.PremiunInspectionFee.formatDynamicPath(3).getData());
			Assertions.passTest("Policy Summary Page", "Cancelled Term Total Policy Fee : "
					+ policySummarypage.PremiumPolicyFee.formatDynamicPath(3).getData());

			// Total Premium
			Assertions.addInfo("Policy Summary Page", "Asserting Total Premium for Rewritten Policy");
			Assertions.passTest("Policy Summary Page", "Transaction Total Premium : "
					+ policySummarypage.policyTotalPremium.formatDynamicPath(1).getData());

			// capturing policy numbers in policy summary page
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 115", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 115", "Executed Successfully");
			}
		}
	}
}
