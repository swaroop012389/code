/** Program Description: Create Policy.Verify the presence of viewRewritingPolicy,Stop Rewrite,work on Rewrite links
 *  Author			   : John
 *  Date of Creation   : 08/10/2020
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC093 extends AbstractCommercialTest {

	public TC093() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID093.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
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
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number is  " + quoteNumber);

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
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

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on Rewrite policy
			policySummarypage.rewritePolicy.waitTillVisibilityOfElement(60);
			policySummarypage.rewritePolicy.scrollToElement();
			policySummarypage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Rewrite Policy Link");

			// click on view rewritten policy
			Assertions.addInfo("Account Overview Page", "Verifying the Presence of View Rewriting Policy button");
			Assertions.verify(accountOverviewPage.viewRewritingPolicy.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View Rewriting Policy Button is displayed", false, false);
			accountOverviewPage.viewRewritingPolicy.scrollToElement();
			accountOverviewPage.viewRewritingPolicy.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Rewriting Policy Button");

			// Click on stop policy rewrite
			Assertions.addInfo("Policy Summary Page", "Verifying the Presence of Stop Policy Rewrite button");
			Assertions.verify(policySummarypage.stopPolicyRewrite.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Stop Policy Rewrite Link is displayed", false, false);
			policySummarypage.stopPolicyRewrite.scrollToElement();
			policySummarypage.stopPolicyRewrite.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Stop Policy Rewrite Link");
			Assertions.verify(
					policySummarypage.stopPolicyRewrite.checkIfElementIsPresent()
							&& policySummarypage.stopPolicyRewrite.checkIfElementIsDisplayed(),
					false, "Policy Summary Page", "Stop Policy Rewrite link is not displayed", false, false);

			// Rewrite policy
			policySummarypage.rewritePolicy.waitTillVisibilityOfElement(60);
			policySummarypage.rewritePolicy.scrollToElement();
			policySummarypage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Rewrite Policy ");

			// click on view rewritten policy
			Assertions.verify(accountOverviewPage.viewRewritingPolicy.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "View Rewriting Policy Button is displayed", false, false);
			accountOverviewPage.viewRewritingPolicy.scrollToElement();
			accountOverviewPage.viewRewritingPolicy.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Rewriting Policy Button");

			// Click on work on rewrite
			Assertions.addInfo("Account Overview Page", "Verifying the Presence of work on Rewrite link");
			Assertions.verify(policySummarypage.workOnRewrite.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Work on Rewrite Link is displayed", false, false);
			policySummarypage.workOnRewrite.scrollToElement();
			policySummarypage.workOnRewrite.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Work on Rewrite Link");

			// Assert account summary page
			Assertions.verify(accountOverviewPage.viewRewritingPolicy.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded", false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 93", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 93", "Executed Successfully");
			}
		}
	}
}