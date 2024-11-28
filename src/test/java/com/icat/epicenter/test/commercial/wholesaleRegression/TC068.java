/** Program Description: Add Location and add building as part of PB Endt.
 *  Author			   : Abha
 *  Date of Creation   : 12/01/2019
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC068 extends AbstractCommercialTest {

	public TC068() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID068.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		ModifyForms modifyForms = new ModifyForms();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
		ViewPolicySnapShot viewPolicySnapshotPage = new ViewPolicySnapShot();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Click on user preference link and enter the details
			Assertions.addInfo("Home Page", "Entering User Preference Details");
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");

			// Add broker fees
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			preferenceOptionsPage.addBrokerFees(testData);
			Assertions.passTest("Preference Options Page", "Details entered successfully");

			// Navigate to homepage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Signout
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged out as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");

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

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

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

			// Adding Code for IO-18802 - Inconsistent taxes and fees and alt deductibles
			// scroll to Alt Table and assert values
			Assertions.addInfo("Account Overview Page", "Assert the Alt Table values on Account Overview Page");
			accountOverviewPage.totalPremiumRowName.scrollToElement();
			accountOverviewPage.totalPremiumRowName.waitTillVisibilityOfElement(60);
			Assertions.verify(accountOverviewPage.totalPremiumRowName.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Total Premium Row Name is Verified and displayed as : "
							+ accountOverviewPage.totalPremiumRowName.getData(),
					false, false);

		// Asserting Values for Total Premium and other options
		String totalPremiumData = validateTotalPremium();
		Assertions.verify(accountOverviewPage.quoteOptionsTotalPremium.getData().replace("$", "").replace(",", ""), totalPremiumData,
				"Account Overview Page", "Your Total Premium Value is Verified and displayed as : "
						+ accountOverviewPage.quoteOptionsTotalPremium.getData(),
				false, false);

			// Asserting Values for Quote 1(Alt Option 1)
			accountOverviewPage.quoteOptions1TotalPremium.click();
			String totalPremiumData1 = validateTotalPremium();

		// Navigate back to Initial Quote Account Overview Page
		accountOverviewPage.refreshPage();
		waitTime(2);
		accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber, 1).checkIfElementIsDisplayed();
		accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber, 1).scrollToElement();
		accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber, 1).click();
		Assertions.verify(accountOverviewPage.quoteOptions1TotalPremium.getData().replace("$", "").replace(",", ""), totalPremiumData1,
				"Account Overview Page", "Your Total Premium Value Opt 1 is Verified and displayed as : "
						+ accountOverviewPage.quoteOptions1TotalPremium.getData(),
				false, false);

			// Asserting Values for Quote 1(Alt Option 2)
			accountOverviewPage.quoteOptions2TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions2TotalPremium.waitTillVisibilityOfElement(60);
			accountOverviewPage.quoteOptions2TotalPremium.click();
			String totalPremiumData2 = validateTotalPremium();

			// Navigate back to Initial Quote Account Overview Page
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber, 1).scrollToElement();
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber, 1).waitTillVisibilityOfElement(60);
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber, 1).click();
			Assertions.verify(accountOverviewPage.quoteOptions2TotalPremium.getData().replace("$", "").replace(",", ""),
					totalPremiumData2, "Account Overview Page",
					"Your Total Premium Value Opt 2 is Verified and displayed as : "
							+ accountOverviewPage.quoteOptions2TotalPremium.getData(),
					false, false);

			// Asserting Values for Quote 1(Alt Option 3)
			accountOverviewPage.quoteOptions3TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions3TotalPremium.waitTillVisibilityOfElement(60);
			accountOverviewPage.quoteOptions3TotalPremium.click();
			String quoteNumber3 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber3);

			String totalPremiumData3 = validateTotalPremium();

			// Navigate back to Initial Quote Account Overview Page
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber, 1).scrollToElement();
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber, 1).waitTillVisibilityOfElement(60);
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber, 1).click();
			Assertions.verify(accountOverviewPage.quoteOptions3TotalPremium.getData().replace("$", "").replace(",", ""),
					totalPremiumData3, "Account Overview Page",
					"Your Total Premium Value Opt 3 is Verified and displayed as : "
							+ accountOverviewPage.quoteOptions3TotalPremium.getData(),
					false, false);

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber3);
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber3, 1).scrollToElement();
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber3, 1).waitTillVisibilityOfElement(60);
			accountOverviewPage.quoteLink1.formatDynamicPath(quoteNumber3, 1).click();
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// endorse policy
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to add Location and building");
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Clicking on Edit Building/Location Details link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();

			// Entering new Location Details
			testData = data.get(data_Value2);
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter new Building Details
			buildingPage.enterBuildingDetails(testData);

			// Clicking on Continue button
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();

			// Clicking on Ok button
			endorseInspectionContactPage.okButton.waitTillVisibilityOfElement(60);
			endorseInspectionContactPage.okButton.scrollToElement();
			endorseInspectionContactPage.okButton.click();

			// clicking on continue button in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.chooseCoverageByPolicy.scrollToElement();
			createQuotePage.chooseCoverageByPolicy.click();
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue button successfully");

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// clicking on complete button in endorse policy page
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// clicking on close button in endorse policy page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on endorsement transaction
			Assertions.addInfo("Policy Summary Page", "Verifying Endorsement Record on Policy Summary Page");
			Assertions.verify(
					policySummaryPage.esLink.checkIfElementIsPresent()
							&& policySummaryPage.esLink.checkIfElementIsDisplayed(),
					true, "Policy Summary Page", "Endorsement transaction is recorded under Transaction History", false,
					false);
			policySummaryPage.esLink.waitTillButtonIsClickable(60);
			policySummaryPage.esLink.scrollToElement();
			policySummaryPage.esLink.click();

			// clicking on close button in endorse policy page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Clicking on View Policy Snapshot link to view the details
			policySummaryPage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();

			// Validating new building and location details on policy details page
			Assertions.addInfo("Policy Display Page",
					"Verifying new building and location details on policy details page");
			Assertions.verify(
					viewPolicySnapshotPage.locationName
							.formatDynamicPath("2", "Location").getData().contains("Location 2"),
					true, "Policy Display Page",
					"Location 2 Details added sucessfully<br/>"
							+ viewPolicySnapshotPage.locationName.formatDynamicPath("2", "Location").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapshotPage.buildingName.formatDynamicPath(2, "L2B1").getData().contains("L2B1"), true,
					"Policy Display Page",
					"Location 2 Building 1 Details added sucessfully<br/>"
							+ viewPolicySnapshotPage.buildingName.formatDynamicPath(2, "L2B1").getData().substring(3),
					false, false);

			// added IO-21781

			viewPolicySnapshotPage.goBackButton.scrollToElement();
			viewPolicySnapshotPage.goBackButton.click();

			// endorse policy
			Assertions.addInfo("Policy Summary Page",
					"Initiating Endorsement to verify the APC radio button on the Location page");
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// setting Endorsement Effective Date
			testData = data.get(data_Value1);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Clicking on Edit Building/Location Details link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();

			locationPage.additionalPropertyCoverages_Yes.checkIfElementIsPresent();
			locationPage.additionalPropertyCoverages_Yes.scrollToElement();
			locationPage.additionalPropertyCoverages_Yes.click();

			locationPage.apc_Value.formatDynamicPath("Other Structures - Open or Not Fully Enclosed").scrollToElement();
			locationPage.apc_Value.formatDynamicPath("Other Structures - Open or Not Fully Enclosed").setData("$1,000");

			if (buildingPage.continueButton.checkIfElementIsPresent()
					&& buildingPage.continueButton.checkIfElementIsDisplayed()) {
				buildingPage.continueButton.scrollToElement();
				buildingPage.continueButton.click();
			}

			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();

			modifyForms.override.scrollToElement();
			modifyForms.override.click();

			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();

			Assertions.verify(locationPage.additionalPropertyCoverages_Yes_Checked.checkIfElementIsPresent(), true,
					"Location Page", "The APC radio button on Location page is set to 'Yes' is verified.", false,
					false);
			// IO-21781 is ended

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 68", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 68", "Executed Successfully");
			}
		}
	}

	public String validateTotalPremium() {
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		accountOverviewPage.premiumValue.waitTillVisibilityOfElement(60);
		String premiumValue = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
		double premiumData = Double.valueOf(premiumValue);
		String icatFees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
		double icatFeesData = Double.valueOf(icatFees);
		String surplusContributionValue = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
				.replace(",", "");
		double surplusContributionValueData = Double.valueOf(surplusContributionValue);
		double totalPremium = (premiumData + icatFeesData + surplusContributionValueData);
		String totalPremiumData = String.valueOf(totalPremium);
		Assertions.passTest("Request Bind Page", "Premium + ICAT Fees + Surplus Contribution Fees = " + totalPremium);
		return totalPremiumData;

	}
}