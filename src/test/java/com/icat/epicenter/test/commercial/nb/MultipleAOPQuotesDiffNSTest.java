/** Program Description: 1. Create multiple AOP quotes with different NS values and bind the last quote. Assert the relevant data on policysnapshot
 *  					 2. Adding for Ticket IO-20460 - External requested cancellations are throwing stack trace error
 *  Author			   : Abha
 *  Date of Creation   : 11/26/2019
 **/

package com.icat.epicenter.test.commercial.nb;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RequestCancellationPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class MultipleAOPQuotesDiffNSTest extends AbstractCommercialTest {

	public MultipleAOPQuotesDiffNSTest() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/nb/NBTCID10.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> testDataSetup) {
		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		GLInformationPage gLInfoPage = new GLInformationPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ViewPolicySnapShot viewPolicySnapshotPage = new ViewPolicySnapShot();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();
		LoginPage loginPage = new LoginPage();
		RequestCancellationPage requestCancellationPage = new RequestCancellationPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		String externaluser = "testing@data.com";
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData1 = data.get(data_Value2);

		// creating New account
		Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
				"Home page loaded successfully", false, false);
		homePage.createNewAccountWithNamedInsured(testData, testDataSetup);
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

		// Entering Prior Losses
		Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
				"Prior Loss Page loaded successfully", false, false);
		priorLossesPage.selectPriorLossesInformation(testData);

		// Entering GL Information
		gLInfoPage.enterGLInformation(testData);

		// Entering Create quote page Details
		Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
				"Create Quote Page loaded successfully", false, false);
		createQuotePage.enterQuoteDetailsCommercialSmoke(testData);
		Assertions.passTest("Create Quote Page", "Quote1 details entered successfully");
		Assertions.passTest("Create Quote Page", "NS deductible for Quote 1 is " + testData.get("DeductibleValue"));

		// Create another quote
		accountOverviewPage.createAnotherQuote.scrollToElement();
		accountOverviewPage.createAnotherQuote.click();
		Assertions.passTest("Account Overview Page", "Clicked on Create Another Quote");

		// Click on override button
		if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
				&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
			buildingNoLongerQuoteablePage.override.scrollToElement();
			buildingNoLongerQuoteablePage.override.click();
		}

		// Selecting a peril
		Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
				"Select Peril Page loaded successfully", false, false);
		selectPerilPage.selectPeril(testData.get("Peril"));

		// Enter Q2 details
		createQuotePage.enterDeductiblesCommercial(testData1);
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		Assertions.passTest("Create Quote Page", "Quote2 details entered successfully");
		Assertions.passTest("Create Quote Page", "NS deductible for Quote 2 is " + testData1.get("DeductibleValue"));

		// getting the quote number
		Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true, "Account Overview Page",
				"Account Overview Page loaded successfully", false, false);
		quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		// entering details in request bind page
		Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
		requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
				"Request Bind Page loaded successfully", false, false);
		requestBindPage.enterBindDetails(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");

		// Go to HomePage
		homePage.goToHomepage.click();
		homePage.searchReferral(quoteNumber);
		Assertions.passTest("Home Page", "Quote for referral is searched successfully");

		// Approve Referral
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
		Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true, "Policy Summary Page",
				"Policy Number is " + policyNumber, false, false);

		// Clicking on View Policy Snapshot link to view the details
		policySummaryPage.viewPolicySnapshot.scrollToElement();
		policySummaryPage.viewPolicySnapshot.click();
		Assertions.passTest("Policy Snap Shot Page", "Policy Snap Shot Page loaded successfully");

		// Verifying quote details on Policy Snapshot page
		Assertions.addInfo("Policy Snap Shot Page", "Verifying deductible details on Policy Snapshot page");
		viewPolicySnapshotPage.policyDeductibles.formatDynamicPath("Named Storm")
				.waitTillVisibilityOfElement(TIME_OUT_SIXTY_SECS);
		Assertions.verify(
				viewPolicySnapshotPage.policyDeductibles
						.formatDynamicPath("Named Storm").getData().contains(testData1.get("DeductibleValue")),
				true, "Policy Snap Shot Page",
				"Named Storm deductible details are verified</br>"
						+ viewPolicySnapshotPage.policyDeductibles.formatDynamicPath("Named Storm").getData(),
				false, false);
		viewPolicySnapshotPage.goBackButton.scrollToElement();
		viewPolicySnapshotPage.goBackButton.click();
		Assertions.passTest("Policy Snap Shot Page", "Clicked on back button successfully");

		// Adding below code for IO-20221
		// Click on NPB link
		testData = data.get(data_Value1);
		policySummaryPage.endorseNPB.scrollToElement();
		policySummaryPage.endorseNPB.click();
		Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Endorse Policy Page loaded successfully", false, false);
		endorsePolicyPage.endorsementEffDate.scrollToElement();
		endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
		endorsePolicyPage.changeNamedInsuredLink.click();

		// Changing named insured
		Assertions.verify(changeNamedInsuredPage.okButton.checkIfElementIsDisplayed(), true,
				"Chnage Named Insured Page", "Chnage Named Insured Page loaded successfully", false, false);
		testData1 = data.get(data_Value2);
		changeNamedInsuredPage.namedInsured.scrollToElement();
		changeNamedInsuredPage.namedInsured.clearData();
		changeNamedInsuredPage.namedInsured.appendData(testData1.get("InsuredName"));
		changeNamedInsuredPage.namedInsured.tab();
		changeNamedInsuredPage.okButton.scrollToElement();
		changeNamedInsuredPage.okButton.click();
		changeNamedInsuredPage.no_NameChange.scrollToElement();
		changeNamedInsuredPage.no_NameChange.click();

		// Verifying presence of complete button or endorse policy page
		Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
				"Stack trace is not comming after Click on No button on name change detected page", false, false);

		// sign out and close the browser
		Assertions.passTest("Commercial NBRegression Test Case 10", "Executed Successfully");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();

		// Sigin in as External User
		// Adding for Ticket IO-20460 - External requested cancellations are throwing stack trace error
		loginPage.refreshPage();
		loginPage.enterLoginDetails(externaluser, testDataSetup.get("Password"));

		// Search Policy Number
		homePage.searchPolicyByProducer(policyNumber);
		Assertions.passTest("Login Page", "Logged in as External User successfully");

		// Click on Request Cancellation Link
		policySummaryPage.requestCancellationLink.scrollToElement();
		policySummaryPage.requestCancellationLink.click();

		// Enter the details for Cancellation
		Assertions.addInfo("Request Cancellation Page", "IO-20460 - External requested cancellations are throwing stack trace error");
		requestCancellationPage.enterRequestCancellationDetails(testData);
		Assertions
		.verify(requestCancellationPage.cancellationRequestMsg.checkIfElementIsDisplayed(), true,
				"Request Cancellation Page", "Request cancellation Message is : "
						+ requestCancellationPage.cancellationRequestMsg.getData() + " displayed and verified",
						false, false);
	}

}
