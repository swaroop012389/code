/** Program Description: Create an AOP Policy with single location and single building
 *  Author			   : Abha
 *  Date of Creation   : 11/20/2019
 **/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DateConversions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC006 extends AbstractCommercialTest {

	public TC006() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID006.xls";
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
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		LoginPage loginPage = new LoginPage();
		ViewPolicySnapShot viewPolicySnapshotPage = new ViewPolicySnapShot();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);

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
			homePage.createNewAccountProducer.waitTillVisibilityOfElement(60);
			homePage.createNewAccountProducer.scrollToElement();
			homePage.createNewAccountProducer.click();
			homePage.namedInsured.waitTillVisibilityOfElement(60);
			homePage.namedInsured.scrollToElement();
			homePage.namedInsured.setData(insuredName);
			Assertions.passTest("Home Page", "Insured Name is " + testData.get("InsuredName"));
			if (homePage.effectiveDate.formatDynamicPath("1").checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath("1").checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath("1").scrollToElement();
				homePage.effectiveDate.formatDynamicPath("1").waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath("1").setData(testData.get("PolicyEffDate"));
			}
			homePage.goButton.click();
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zip-code in Eligibility page
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

			// Adding the CR IO-20732
			Assertions.verify(buildingPage.gLInformationlink.checkIfElementIsPresent(), false, "Building Page",
					"GL Information link not present is verified for producer", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			Assertions.verify(selectPerilPage.gLandAllOtherPerils.checkIfElementIsPresent(), false, "Select Peril Page",
					"GL and AOP Option not present is verified for producer", false, false);

			// Signing out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");

			// Go to HomePage
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"USM Home page loaded successfully", false, false);
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();

			Assertions.verify(accountOverviewPage.quoteAccountButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			// click on quote account button
			accountOverviewPage.quoteAccountButton.scrollToElement();
			accountOverviewPage.quoteAccountButton.click();

			// verify the presence of GL option for USM
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			Assertions.verify(selectPerilPage.gLandAllOtherPerils.checkIfElementIsPresent(), true, "Select Peril Page",
					"GL and AOP Option present is verified for USM", false, false);

			// Signing out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as producer successfully");

			// search the account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.producerAccountNameSearchTextbox.setData(insuredName);
			homePage.producerAccountFindButton.scrollToElement();
			homePage.producerAccountFindButton.click();
			homePage.producerAccountNameLink.scrollToElement();
			homePage.producerAccountNameLink.click();
			Assertions.verify(accountOverviewPage.quoteAccountButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// click on quote account button
			accountOverviewPage.quoteAccountButton.scrollToElement();
			accountOverviewPage.quoteAccountButton.click();

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Asserting Coverage by Option is not present for single building and location
			Assertions.addInfo("Create Quote Page",
					"Asserting Coverage by Option is not present for single building and location");
			Assertions.verify(createQuotePage.chooseCoverageByLocation.checkIfElementIsPresent(), false,
					"Create Quote Page", "Coverage By Option is not available in create quote page is verified", false,
					false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
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

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Validating the referral message
			Assertions.addInfo("Bind Request Submitted Page", "Asserting Bind request referral message");
			Assertions
					.verify(bindRequestSubmittedPage.bindRequestMessage.getData().contains("request to bind"), true,
							"Bind Request Submitted Page", "Bind request referral message : "
									+ bindRequestSubmittedPage.bindRequestMessage.getData() + " is displayed",
							false, false);

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.waitTime(2);
			homePage.refreshPage();
			homePage.waitTime(1);
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Approve Referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Clicking on View Policy Snapshot link to view the details
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();

			// Verifying details on binder page
			Assertions.addInfo("Policy SnapShot Page",
					"Asserting Location and Building Details from Policy snapshot page");
			viewPolicySnapshotPage.refreshPage();
			Assertions.verify(viewPolicySnapshotPage.location.getData().contains("Location 1"), true,
					"Policy SnapShot Page",
					"There is only 1 Location : " + viewPolicySnapshotPage.location.getData() + " is verified", false,
					false);
			Assertions.verify(viewPolicySnapshotPage.building.getData().contains("Building 1"), true,
					"Policy SnapShot Page",
					"There is only 1 Building : " + viewPolicySnapshotPage.building.getData() + " is verified", false,
					false);

			// ----- Added IO-21040-----

			// Signing out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as producer successfully");

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.scrollToBottomPage();

			homePage.producerRenewalSearchButton.scrollToElement();
			homePage.producerRenewalSearchButton.click();
			homePage.producerRenewalNameSearchTextbox.scrollToElement();

			String insuredText = homePage.producerRenewalNameSearchTextbox.getAttrributeValue("placeholder");
			System.out.println("Insured text is " + insuredText);

			Assertions.addInfo("Scenario 01",
					"Verifying that the Named Insured box displays the 'Named Insured' text on the Renewal find section of producer homepage");
			Assertions.verify(
					homePage.producerRenewalNameSearchTextbox.checkIfElementIsPresent()
							&& homePage.producerRenewalNameSearchTextbox.getAttrributeValue("placeholder")
									.equals("Named Insured"),
					true, "Home Page",
					"Named Insured text is displayed and verified on the Renewal find section of producer homepage",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			homePage.producerPolicySearchButton.scrollToElement();
			homePage.producerPolicySearchButton.click();
			homePage.producerPolicyNameSearchTextbox.scrollToElement();

			Assertions.addInfo("Scenario 02",
					"Verifying that the Named Insured box displays the 'Named Insured' text on the Policy find section of producer homepage");
			Assertions.verify(
					homePage.producerPolicyNameSearchTextbox.checkIfElementIsPresent()
							&& homePage.producerPolicyNameSearchTextbox.getAttrributeValue("placeholder")
									.equals("Named Insured"),
					true, "Home Page",
					"Named Insured text is displayed and verified on the Policy find section of producer homepage",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			homePage.producerBinderSearchButton.scrollToElement();
			homePage.producerBinderSearchButton.click();
			homePage.producerBinderNameSearchTextbox.scrollToElement();

			Assertions.addInfo("Scenario 03",
					"Verifying that the Named Insured box displays the 'Named Insured' text on the Binder find section of producer homepage");
			Assertions.verify(
					homePage.producerBinderNameSearchTextbox.checkIfElementIsPresent()
							&& homePage.producerBinderNameSearchTextbox.getAttrributeValue("placeholder")
									.equals("Named Insured"),
					true, "Home Page",
					"Named Insured text is displayed and verified on the Binder find section of producer homepage",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			homePage.producerQuoteSearchButton.scrollToElement();
			homePage.producerQuoteSearchButton.click();
			homePage.producerQuoteNameSearchTextbox.scrollToElement();

			Assertions.addInfo("Scenario 04",
					"Verifying that the Named Insured box displays the 'Named Insured' text on the Quote find section of producer homepage");
			Assertions.verify(
					homePage.producerQuoteNameSearchTextbox.checkIfElementIsPresent()
							&& homePage.producerQuoteNameSearchTextbox.getAttrributeValue("placeholder")
									.equals("Named Insured"),
					true, "Home Page",
					"Named Insured text is displayed and verified on the Quote find section of producer homepage",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			homePage.producerBuildingSearchButton.scrollToElement();
			homePage.producerBuildingSearchButton.click();
			homePage.producerBuildingNameSearchTextbox.scrollToElement();

			Assertions.addInfo("Scenario 05",
					"Verifying that the Named Insured box displays the 'Named Insured' text on the Building find section of producer homepage");
			Assertions.verify(
					homePage.producerBuildingNameSearchTextbox.checkIfElementIsPresent()
							&& homePage.producerBuildingNameSearchTextbox.getAttrributeValue("placeholder")
									.equals("Named Insured"),
					true, "Home Page",
					"Named Insured text is displayed and verified on the Building find section of producer homepage",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			homePage.producerAccountSearchButton.scrollToElement();
			homePage.producerAccountSearchButton.click();
			homePage.producerAccountNameSearchTextbox.scrollToElement();

			Assertions.addInfo("Scenario 06",
					"Verifying that the Named Insured box displays the 'Named Insured' text on the Account find section of producer homepage");
			Assertions.verify(
					homePage.producerAccountNameSearchTextbox.checkIfElementIsPresent()
							&& homePage.producerAccountNameSearchTextbox.getAttrributeValue("placeholder")
									.equals("Named Insured"),
					true, "Home Page",
					"Named Insured text is displayed and verified on the Account find section of producer homepage",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// ------ IO-21040 Ended-----

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 06", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 06", "Executed Successfully");
			}
		}
	}
}