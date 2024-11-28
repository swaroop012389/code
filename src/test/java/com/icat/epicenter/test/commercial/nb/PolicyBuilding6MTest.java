/** Program Description: Create a commerical policy without BI but BLDG value as $6M and check if all details can be asserted on Quote and Binder pages. Check if the quote refers with appropriate message
 *  Author			   : Abha
 *  Date of Creation   : 11/19/2019
 **/
package com.icat.epicenter.test.commercial.nb;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class PolicyBuilding6MTest extends AbstractCommercialTest {

	PolicyBuilding6MTest() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/nb/NBTCID04.xls";
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
		LoginPage loginPage = new LoginPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		ViewPolicySnapShot viewPolicySnapshotPage = new ViewPolicySnapShot();
		ReferQuotePage referQuotePage = new ReferQuotePage();

		// Initializing the variables
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		final int TIME_OUT_SIXTY_SECS = 60;
		String quoteNumber;
		String policyNumber;

		//Handling the pop up
		homePage.enterPersonalLoginDetails();

		Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
				"Home page loaded successfully", false, false);

		// Verifying Effective Date Label in Commercial "Create Account in Zip" from Zip
		// Checker- 18624
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();

		// Entering zipcode in zipcode checker field
		homePage.zipcodeCheckerField.setData(testData.get("ZipCode"));
		Assertions.passTest("Home Page", "Entered the Zipcode in Zipcode Checker field successfully");
		homePage.zipcodeCheckerField.tab();
		homePage.checkZipcodeButton.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		homePage.checkZipcodeButton.click();
		Assertions.passTest("Home Page", "Clicked on Check Zipcode Button");
		homePage.createAccountInZipLink.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		homePage.createAccountInZipLink.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		homePage.createAccountInZipLink.click();
		Assertions.passTest("Home Page", "Clicked on Create Account In Zip link successfully");

		// Asserting the presence of effective date label
		Assertions.addInfo("Home Page", "Verifying Effective Date Label for Commercial product");
		Assertions.verify(homePage.effectiveDateLabel.checkIfElementIsDisplayed(), true, "Home Page",
				"Effective Date Label displayed in Create Account in Zip from Zipcode Checker is verified", false,
				false);

		// go to homepage
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();

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
		createQuotePage.enterQuoteDetailsCommercialSmoke(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");

		// Validating the referral message
		Assertions.verify(referQuotePage.covA6MReferralMsg.checkIfElementIsDisplayed(), true, "Refer Quote Page",
				"Referral Message : " + referQuotePage.covA6MReferralMsg.getData() + " is displayed", false, false);

		// Refer Quote for binding
		referQuotePage.contactName.setData(testData.get("ProducerName"));
		referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
		referQuotePage.referQuote.click();
		quoteNumber = referQuotePage.quoteNum.getData();

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
		Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("1").getData().contains("Referred"), true,
				"Account Overview Page", "Quote " + quoteNumber + " Status is "
						+ accountOverviewPage.quoteStatus.formatDynamicPath("1").getData(),
				false, false);

		// Click on Open referral link
//		accountOverviewPage.openReferralLink.scrollToElement();
//		accountOverviewPage.openReferralLink.click();

		// Approve Referral
		if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
			referralPage.pickUp.scrollToElement();
			referralPage.pickUp.click();
		}
		referralPage.approveOrDeclineRequest.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		referralPage.approveOrDeclineRequest.scrollToElement();
		referralPage.approveOrDeclineRequest.click();
		referralPage.scrollToBottomPage();
		referralPage.internalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
		referralPage.externalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
		referralPage.approveRequest.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		referralPage.approveRequest.scrollToElement();
		referralPage.approveRequest.click();
		Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

		// Searching the account
		homePage.goToHomepage.click();
		homePage.searchQuote(quoteNumber);

		// entering details in request bind page
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Account Overview Page", "Quote Number is " + quoteNumber);
		Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		requestBindPage.enterBindDetails(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Go to HomePage
		homePage.goToHomepage.click();
		homePage.searchReferral(quoteNumber);
		Assertions.passTest("Home Page", "Quote for referral is searched successfully");

		// Approve Referral
		if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
			referralPage.pickUp.scrollToElement();
			referralPage.pickUp.click();
		}

		// Approve Referral
		referralPage.approveOrDeclineRequest.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		referralPage.approveOrDeclineRequest.scrollToElement();
		referralPage.approveOrDeclineRequest.click();
		requestBindPage.scrollToBottomPage();
		requestBindPage.internalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
		requestBindPage.externalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
		requestBindPage.approve.waitTillButtonIsClickable(TIME_OUT_SIXTY_SECS);
		requestBindPage.approve.scrollToElement();
		requestBindPage.approve.click();
		Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

		// getting the policy number
		Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
				"Policy Summary page loaded successfully", false, false);
		policyNumber = policySummarypage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

		// Clicking on View Policy Snapshot link to view the details
		policySummarypage.viewPolicySnapshot.scrollToElement();
		policySummarypage.viewPolicySnapshot.click();

		// Verifying details on binder page
		Assertions.addInfo("Binder Page", "Asserting Building  and BPP value from Binder Page");
		viewPolicySnapshotPage.refreshPage();
		Assertions.verify(
				viewPolicySnapshotPage.propertyCovBldgLimit.getData().contains(
						testData.get("L" + locationCount + "B" + locationCount + "-BldgValue")),
				true, "Binder Page",
				"The value of Property Coverage : Building is " + viewPolicySnapshotPage.propertyCovBldgLimit.getData(),
				false, false);

		// Signout
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		Assertions.passTest("Commercial NBRegression Test Case 04", "Executed Successfully");
	}
}
