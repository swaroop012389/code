/** Program Description: To validate if Roll forward is not applied on renewal quote of Wind policy if option is not selected
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/27/2019
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
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC073 extends AbstractCommercialTest {

	public TC073() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID073.xls";
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
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
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

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on Endorse policy
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to Change Deductibles and Coverages");
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endoresment Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on change coverage options hyperlink
			testData = data.get(data_Value2);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);

			// modifying deductibles and coverages in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialPNB(testData);
			Assertions.passTest("Create Quote Page", "Deductibles and coverages details modified successfully");

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// clicking on complete button in endorse policy page
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// clicking on close button in endorse policy page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// verifying PB Endorsement record in policy summary page
			Assertions.addInfo("Policy Summary Page", "Verifying PB Endorsement record in policy summary page");
			testData = data.get(data_Value1);
			Assertions.verify(
					policySummarypage.transactionType.formatDynamicPath(testData.get("TransactionType")).getData(),
					testData.get("TransactionType"), "Policy Summary Page", "PB Endorsement Record Verified", false,
					false);

			// Goto Home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Policy
			homePage.searchPolicy(policyNumber);

			// Validating the transaction values for 1st endorsement from policy summary
			// page
			Assertions.addInfo("Policy Summary Page",
					"Verifying the transaction values for 1st endorsement from policy summary page");
			policySummarypage.transHistReason.formatDynamicPath(3).waitTillVisibilityOfElement(60);
			policySummarypage.transHistReason.formatDynamicPath(3).scrollToElement();
			policySummarypage.transHistReason.formatDynamicPath(3).click();
			String transctionPremium = policySummarypage.transactionPremium.getData();
			String policyFee = policySummarypage.policyFee.getData();
			String inspectionFee = policySummarypage.inspectionFee.getData();
			String totalTransactionPremium = policySummarypage.policyTotalPremium.getData();
			Assertions.verify(policySummarypage.transactionPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Transaction Premium after endorsement : " + transctionPremium, false,
					false);
			Assertions.verify(policySummarypage.policyFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Fee after endorsement : " + policyFee, false, false);
			Assertions.verify(policySummarypage.inspectionFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Inspection Fee afte endorsement : " + inspectionFee, false, false);
			Assertions.verify(policySummarypage.policyTotalPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Total Transaction Premium after endorsement : " + totalTransactionPremium,
					false, false);

			// Clicking on reverse last endorsement
			policySummarypage.reversePreviousEndorsementLink.waitTillVisibilityOfElement(60);
			policySummarypage.reversePreviousEndorsementLink.scrollToElement();
			policySummarypage.reversePreviousEndorsementLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Reverse Last Endorsement link");

			// Clicking on complete
			endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Clicking on Close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Validating reversal of last endorsement
			Assertions.addInfo("Policy Summary Page", "Verifying the  reversal of last endorsement");
			Assertions.verify(policySummarypage.transHistReason.formatDynamicPath(4).getData().contains("Reversal"),
					true, "Policy Summary Page", "First endorsement is reversed is verified", false, false);

			// Validating the transaction values after 1st endorsement is reversed from
			// policy summary
			// page
			Assertions.addInfo("Policy Summary Page",
					"Verifying  the transaction values After 1st endorsement is reversed from policy summary page");
			policySummarypage.transHistReason.formatDynamicPath(4).waitTillVisibilityOfElement(60);
			policySummarypage.transHistReason.formatDynamicPath(4).scrollToElement();
			policySummarypage.transHistReason.formatDynamicPath(4).click();
			Assertions.verify(!policySummarypage.transactionPremium.getData().contains(transctionPremium), true,
					"Policy Summary Page", "Transaction Premium after reversing endorsement : "
							+ policySummarypage.transactionPremium.getData(),
					false, false);
			Assertions.verify(policySummarypage.policyFee.getData().contains(policyFee), true, "Policy Summary Page",
					"Policy Fee after reversing endorsement : " + policySummarypage.policyFee.getData(), false, false);
			Assertions.verify(policySummarypage.inspectionFee.getData().contains(inspectionFee), true,
					"Policy Summary Page",
					"Inspection Fee after reversing endorsement : " + policySummarypage.inspectionFee.getData(), false,
					false);
			Assertions.verify(!policySummarypage.policyTotalPremium.getData().contains(totalTransactionPremium), true,
					"Policy Summary Page", "Total Transaction Premium after reversing endorsement : "
							+ policySummarypage.policyTotalPremium.getData(),
					false, false);

			// Logout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 73", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 73", "Executed Successfully");
			}
		}
	}
}