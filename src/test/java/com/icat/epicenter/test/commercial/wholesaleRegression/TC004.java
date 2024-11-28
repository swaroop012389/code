/** Program Description: Create a commerical policy without BI but BLDG value as $6M and check if all details can be asserted on Quote and Binder pages. Check if the quote refers with appropriate message
 * 						 IO-20568 - Fix 'Other Catastrophes' link
 *  Author			   : Abha
 *  Date of Creation   : 11/19/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

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

public class TC004 extends AbstractCommercialTest {

	public TC004() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID004.xls";
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
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		Map<String, String> testData = data.get(dataValue1);
		String locationNumber = testData.get("LocCount");
		int locationCount = Integer.parseInt(locationNumber);
		String quoteNumber;
		String policyNumber;
		String vieContributionCharge, vieParicipation, premiumAmount, surplusContributionValue;
		double totalContribution, calSurplusContribution;
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Click on home page link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Adding code for ticket - IO-20568 - Fix 'Other Catastrophes' link
			Assertions.verify(homePage.sideBarLinks.formatDynamicPath("Other Catastrophes").checkIfElementIsPresent()
					&& homePage.sideBarLinks.formatDynamicPath("Other Catastrophes").checkIfElementIsDisplayed(), true,
					"Home Page", "Other Catastrophes Link is displayed and verified", false, false);
			// Ended

			// Verifying Effective Date Label in Commercial "Create Account in Zip" from Zip
			// Checker- 18624
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Entering zipcode in zipcode checker field
			homePage.zipcodeCheckerField.setData(testData.get("ZipCode"));
			Assertions.passTest("Home Page", "Entered the Zipcode in Zipcode Checker field successfully");
			homePage.zipcodeCheckerField.tab();
			homePage.checkZipcodeButton.waitTillButtonIsClickable(60);
			homePage.checkZipcodeButton.click();
			Assertions.passTest("Home Page", "Clicked on Check Zipcode Button");
			homePage.createAccountInZipLink.waitTillVisibilityOfElement(60);
			homePage.createAccountInZipLink.waitTillButtonIsClickable(60);
			homePage.createAccountInZipLink.click();
			Assertions.passTest("Home Page", "Clicked on Create Account In Zip link successfully");

			// Asserting the presence of effective date label
			Assertions.addInfo("Home Page", "Verifying Effective Date Label for Commercial product");
			Assertions.verify(homePage.effectiveDateLabel.checkIfElementIsDisplayed(), true, "Home Page",
					"Effective Date Label displayed in Create Account in Zip from Zipcode Checker is verified", false,
					false);

			// go to home page
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
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
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
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("1").getData().contains("Referred"),
					true, "Account Overview Page", "Quote " + quoteNumber + " Status is "
							+ accountOverviewPage.quoteStatus.formatDynamicPath("1").getData(),
					false, false);

			// Click on Open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			referralPage.approveOrDeclineRequest.waitTillButtonIsClickable(60);
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();
			referralPage.scrollToBottomPage();
			referralPage.internalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
			referralPage.externalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
			referralPage.approveRequest.waitTillButtonIsClickable(60);
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
			referralPage.approveOrDeclineRequest.waitTillButtonIsClickable(60);
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();
			requestBindPage.scrollToBottomPage();
			requestBindPage.internalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
			requestBindPage.externalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
			requestBindPage.approve.waitTillButtonIsClickable(60);
			requestBindPage.approve.scrollToElement();
			requestBindPage.approve.click();
			Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

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

			// Clicking on View Policy Snapshot link to view the details
			policySummarypage.viewPolicySnapshot.scrollToElement();
			policySummarypage.viewPolicySnapshot.click();

			// Verifying details on binder page
			Assertions.addInfo("Binder Page", "Asserting Building  and BPP value from Binder Page");
			viewPolicySnapshotPage.refreshPage();
			Assertions.verify(
					viewPolicySnapshotPage.propertyCovBldgLimit.getData()
							.contains(testData.get("L" + locationCount + "B" + locationCount + "-BldgValue")),
					true, "Binder Page", "The value of Property Coverage : Building is "
							+ viewPolicySnapshotPage.propertyCovBldgLimit.getData(),
					false, false);

			// ----Added ticket IO-21385-----

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			Assertions.addInfo("Scenario 01",
					"Verifying that a quote is created successfully when the Green Upgrade is selected as Yes.");

			// creating New account
			testData = data.get(dataValue1);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			testData = data.get(dataValue2);
			// Entering Dwelling Details
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Checking if the quote is created when Green upgrade is selected as 'Yes'
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page",
					"Quote is created successfully when 'Green Upgrade' selected and quote number is : " + quoteNumber);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// ----IO-21385 Ended------

			// Added IO-21571
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();

			accountOverviewPage.vieContributionCharge.checkIfElementIsPresent();
			vieContributionCharge = accountOverviewPage.vieContributionCharge.getData().replace("%", "");

			accountOverviewPage.vieParicipation.checkIfElementIsPresent();
			vieParicipation = accountOverviewPage.vieParicipation.getData().replace("%", "");

			totalContribution = (Double.parseDouble(vieContributionCharge) / 100)
					* (Double.parseDouble(vieParicipation) / 100);

			// Fetching Premium Amount from Account Overview Page
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Premium Amount : " + "$" + premiumAmount);

			// calculating surplus contribution.
			calSurplusContribution = Double.parseDouble(premiumAmount) * totalContribution;

			// Getting the actual surplus contribution from account overview page.
			surplusContributionValue = accountOverviewPage.surplusContributionValue.getData().replaceAll("[^\\d-.]",
					"");

			Assertions.addInfo("Scenario 2",
					"Verifying  Actual and calculated Surplus Contribution for the base package on the Account overview page");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusContributionValue), 2)
					- Precision.round(calSurplusContribution, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Contribution for the base package is : " + "$" + surplusContributionValue);
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Contribution for the base package is : " + "$" + calSurplusContribution);
			} else {
				Assertions.verify(surplusContributionValue, calSurplusContribution, "Account Overview Page",
						"The Difference between actual and calculated Surplus Contribution is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 2", "Scenario 2 is Ended");

			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			testData = data.get(dataValue3);
			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();

			accountOverviewPage.vieContributionCharge.checkIfElementIsPresent();
			vieContributionCharge = accountOverviewPage.vieContributionCharge.getData().replace("%", "");

			accountOverviewPage.vieParicipation.checkIfElementIsPresent();
			vieParicipation = accountOverviewPage.vieParicipation.getData().replace("%", "");

			totalContribution = (Double.parseDouble(vieContributionCharge) / 100)
					* (Double.parseDouble(vieParicipation) / 100);

			// Fetching Premium Amount from Account Overview Page
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Premium Amount : " + "$" + premiumAmount);

			// calculating surplus contribution.
			calSurplusContribution = Double.parseDouble(premiumAmount) * totalContribution;

			// Getting the actual surplus contribution from account overview page.
			surplusContributionValue = accountOverviewPage.surplusContributionValue.getData().replaceAll("[^\\d-.]",
					"");

			Assertions.addInfo("Scenario 3",
					"Verifying  Actual and calculated Surplus Contribution for the package A on the Account overview page");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusContributionValue), 2)
					- Precision.round(calSurplusContribution, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Contribution for the package A is : " + "$" + surplusContributionValue);
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Contribution for the package A is : " + "$" + calSurplusContribution);
			} else {
				Assertions.verify(surplusContributionValue, calSurplusContribution, "Account Overview Page",
						"The Difference between actual and calculated Surplus Contribution is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 3", "Scenario 3 is Ended");

			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			testData = data.get(dataValue4);
			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();

			accountOverviewPage.vieContributionCharge.checkIfElementIsPresent();
			vieContributionCharge = accountOverviewPage.vieContributionCharge.getData().replace("%", "");

			accountOverviewPage.vieParicipation.checkIfElementIsPresent();
			vieParicipation = accountOverviewPage.vieParicipation.getData().replace("%", "");

			totalContribution = (Double.parseDouble(vieContributionCharge) / 100)
					* (Double.parseDouble(vieParicipation) / 100);

			// Fetching Premium Amount from Account Overview Page
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Premium Amount : " + "$" + premiumAmount);

			// calculating surplus contribution.
			calSurplusContribution = Double.parseDouble(premiumAmount) * totalContribution;

			// Getting the actual surplus contribution from account overview page.
			surplusContributionValue = accountOverviewPage.surplusContributionValue.getData().replaceAll("[^\\d-.]",
					"");

			Assertions.addInfo("Scenario 4",
					"Verifying  Actual and calculated Surplus Contribution for the package B on the Account overview page");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusContributionValue), 2)
					- Precision.round(calSurplusContribution, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Contribution for the package B is : " + "$" + surplusContributionValue);
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Contribution for the package B is : " + "$" + calSurplusContribution);
			} else {
				Assertions.verify(surplusContributionValue, calSurplusContribution, "Account Overview Page",
						"The Difference between actual and calculated Surplus Contribution is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 4", "Scenario 4 is Ended");

			// Ticket IO-21571 is Ended.

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 04", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 04", "Executed Successfully");
			}
		}
	}
}