/** Program Description: Create policy in HI and check if policy fee = 100
 *  Author			   : John
 *  Date of Creation   : 11/13/2019
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
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC035 extends AbstractCommercialTest {

	public TC035() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID035.xls";
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
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		String originalPolicyFee;
		String originalPolFee = "250";
		int quotelength;
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		testData = data.get(dataValue1);
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
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "The Building Address State is : " + testData.get("InsuredState"));

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
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
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Asserting Original Policy Fee
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Override premium link");
			Assertions.addInfo("Override Premium and Fee Page", "Asserting Original Policy Fee");
			originalPolicyFee = overridePremiumAndFeesPage.originalPolicyFee.getData().substring(0, 4);
			Assertions.verify(originalPolicyFee, "$" + originalPolFee, "Override Premium and Fee Page",
					"Original Policy Fee is " + originalPolicyFee, false, false);
			overridePremiumAndFeesPage.cancelButton.waitTillVisibilityOfElement(60);
			overridePremiumAndFeesPage.cancelButton.waitTillButtonIsClickable(60);
			overridePremiumAndFeesPage.cancelButton.scrollToElement();
			overridePremiumAndFeesPage.cancelButton.click();

			// entering details in request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
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
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on View policy snapshot link
			policySummarypage.viewPolicySnapshot.scrollToElement();
			policySummarypage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy snapshot link");
			Assertions.addInfo("View Policy Snapshot Page", "Asserting the Policy fee from policy snapshot page");
			Assertions.verify(
					viewPolicySnapShot.policyDeductiblesValues
							.formatDynamicPath("Term Policy Fee", "2").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Policy Fee " + viewPolicySnapShot.policyDeductiblesValues
							.formatDynamicPath("Term Policy Fee", "2").getData() + " displayed is verified",
					false, false);
			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();

			// Added IO-21789
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

			// Clicking on Edit Building/Location Details link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();

			// Enter new Building Details
			// Changing the roof age to 11 to 15 years.
			testData = data.get(dataValue2);
			buildingPage.modifyBuildingDetailsPNB_old(testData);

			if(buildingPage.continueButton.checkIfElementIsPresent()&&buildingPage.continueButton.checkIfElementIsDisplayed()) {
				buildingPage.continueButton.scrollToElement();
				buildingPage.continueButton.click();
			}

			// clicking on continue button in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue button successfully");

			// checking that the endorsement changes are available under summary of pending
			// endorsement changes section.
			Assertions.verify(endorsePolicyPage.endorsementChanges
					.formatDynamicPath("Building Level Changes", "Year Roof Updated").checkIfElementIsDisplayed(), true,
					"Endorse Summary Page",
					"Roof age changes are displayed under building level changes successfully on the Summary of Pending Endorsement Changes section.",
					false, false);

			Assertions.addInfo("Scenario",
					"Verifying that the Summary of Pending Endorsement Changes section is not displaying any changed APC's when there is change in the building characteristics");
			Assertions.verify(
					endorsePolicyPage.endorsementChanges
							.formatDynamicPath("Location Level Changes", "Additional Property - Awnings and Canopies")
							.checkIfElementIsPresent(),
					false, "Endorse Summary Page",
					"Summary of Pending Endorsement Changes section is not displaying any changed APC's when there is change in the building characteristics is verified.",
					false, false);
			Assertions.addInfo("Scenario", "Scenario Ended");
			// IO-21789 is Ended.

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 35", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 35", "Executed Successfully");
			}
		}
	}
}