/** Program Description: 1. Create Policy in FL state with premium more than 2501 and check Policy fee = 340 and adding CR IO-20562
 * 					     2. Adding IO-21329 [Murali][08/16/2023]
 *  Author			   : John
 *  Date of Creation   : 11/13/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC034 extends AbstractCommercialTest {

	public TC034() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID034.xls";
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
		LoginPage loginPage = new LoginPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int quotelength;
		String originalPolicyFee;
		String originalPolFee = "2,170.00";
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		testData = data.get(dataValue1);
		String quotePremiumValue;
		String quoteICATfeesValue;
		String quoteSLTFValue;
		String quoteSurplusContributionValue;
		String quoteDetailsValue;
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

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// enter prior loss details
			if (!testData.get("PriorLoss1").equalsIgnoreCase("Yes")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			} else {
				priorLossPage.lossesInThreeYearsNo.click();
				priorLossPage.continueButton.click();
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
			String premiumValue = accountOverviewPage.premiumValue.getData();
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
			Assertions.verify(accountOverviewPage.editDeductibleAndLimits.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Edit deductibles button is displayed", false, false);

			// Asserting Original Policy Fee
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.addInfo("Override Premium and Fee Page", "Asserting Original Policy Fee");
			originalPolicyFee = overridePremiumAndFeesPage.originalPolicyFee.getData();
			Assertions.passTest("Account Overview Page", "Premium Value is " + premiumValue);
			Assertions.verify(originalPolicyFee, "$" + originalPolFee, "Override Premium and Fee Page",
					"Original Policy Fee is " + originalPolicyFee, false, false);
			overridePremiumAndFeesPage.cancelButton.waitTillVisibilityOfElement(60);
			overridePremiumAndFeesPage.cancelButton.waitTillButtonIsClickable(60);
			overridePremiumAndFeesPage.cancelButton.scrollToElement();
			overridePremiumAndFeesPage.cancelButton.click();

			// entering details in request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Enter Bind Details
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

			// Signout as tuser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Below code adding for IO-20562
			// Login as producer
			testData = data.get(dataValue2);
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// creating New account
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			locationPage = eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Dwelling Details
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Validating the referral message
			Assertions.verify(
					referQuotePage.referralMessages.formatDynamicPath(1).getData()
							.contains("This account contains a building that was previously cancelled or non-renewed"),
					true, "Refer Quote Page", "Referral Message : "
							+ referQuotePage.referralMessages.formatDynamicPath(1).getData() + " is displayed",
					false, false);

			// Refer Quote for binding
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();
			quoteNumber = referQuotePage.quoteNum.getData();

			// Adding Ticket IO-21329
			// Navigating to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(
					homePage.createNewAccountProducer.checkIfElementIsPresent()
							&& homePage.createNewAccountProducer.checkIfElementIsDisplayed(),
					true, "Home Page", "Home Page Loaded Successfully", false, false);

			// Searching for The referred quote
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.verify(
					accountOverviewPage.uploadPreBindDocuments.checkIfElementIsPresent()
							&& accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Referred Quote is found and Account Overview Page Loaded Successfully", false, false);
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("1").getData().contains("Referred"),
					true, "Account Overview Page",
					"The Quote status is: " + accountOverviewPage.quoteStatus.formatDynamicPath("1").getData(), false,
					false);

			// Fetching Premium, Surplus Contribution, ICAT Fees and SLTF from Account
			// Overview Page when the Quote is in Referral Status
			quotePremiumValue = accountOverviewPage.premiumValue.getData();
			quoteICATfeesValue = accountOverviewPage.feesValue.getData();
			quoteSLTFValue = accountOverviewPage.sltfValue.getData();
			quoteSurplusContributionValue = accountOverviewPage.surplusContributionValue.getData();
			quoteDetailsValue = testData.get("QuoteValues");

			// Validating if all the Quote Details (Premium, Surplus Contribution, ICAT Fees
			// and SLTF are Shown as TBD)
			Assertions.addInfo("Scenario 01",
					"Validating if all the Quote Details (Premium, Surplus Contribution, ICAT Fees and SLTF) are Shown as TBD");
			Assertions.verify(quotePremiumValue, quoteDetailsValue, "Account Overview Page",
					"The Premium Value is displayed as TBD when the quote is in Referred Status", false, false);
			Assertions.verify(quoteICATfeesValue, quoteDetailsValue, "Account Overview Page",
					"The ICAT Fee Value is displayed as TBD when the quote is in Referred Status", false, false);
			Assertions.verify(quoteSLTFValue, quoteDetailsValue, "Account Overview Page",
					"The SLTF Value is displayed as TBD when the quote is in Referred Status", false, false);
			Assertions.verify(quoteSurplusContributionValue, quoteDetailsValue, "Account Overview Page",
					"The Surplus Contribution Value is displayed as TBD when the quote is in Referred Status", false,
					false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Signing out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Asserting the Quote Status
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("1").getData().contains("Referred"),
					true, "Account Overview Page", "Quote " + quoteNumber + " Status is "
							+ accountOverviewPage.quoteStatus.formatDynamicPath("1").getData(),
					false, false);

			// Click on Open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Asserting referral message :This account contains a building that was
			// previously cancelled or non-renewed by ICAT but may still be eligible for
			// coverage after review by an online underwriter.
			referralPage.referralReason
					.formatDynamicPath("This account contains a building that was previously cancelled or non-renewed")
					.waitTillPresenceOfElement(60);
			referralPage.referralReason
					.formatDynamicPath("This account contains a building that was previously cancelled or non-renewed")
					.scrollToElement();
			Assertions.addInfo("Scenario 02",
					"Asserting referral message :This account contains a building that was previously cancelled or non-renewed by ICAT "
							+ "but may still be eligible for coverage after review by an online underwriter");
			Assertions.verify(
					referralPage.referralReason
							.formatDynamicPath(
									"This account contains a building that was previously cancelled or non-renewed")
							.checkIfElementIsPresent()
							&& referralPage.referralReason.formatDynamicPath(
									"This account contains a building that was previously cancelled or non-renewed")
									.checkIfElementIsDisplayed(),
					true, "Referral Page",
					"Referral Message : " + referralPage.referralReason
							.formatDynamicPath(
									"This account contains a building that was previously cancelled or non-renewed")
							.getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Approve Referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			referralPage.approveOrDeclineRequest.waitTillButtonIsClickable(60);
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();

			// click on cancelled link
			approveDeclineQuotePage.statusLink.formatDynamicPath("Cancelled").waitTillPresenceOfElement(60);
			approveDeclineQuotePage.statusLink.formatDynamicPath("Cancelled").scrollToElement();
			approveDeclineQuotePage.statusLink.formatDynamicPath("Cancelled").click();
			Assertions.passTest("Approve Decline Quote Page", "Clicked on Cancelled link successfully");

			// switch to child window
			waitTime(3);
			switchToChildWindow();

			// After click on cancelled link,it should navigate to policy summary page
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"After clicking on cancelled policy its navigating to policy summary page", false, false);
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 34", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 34", "Executed Successfully");
			}
		}
	}
}
