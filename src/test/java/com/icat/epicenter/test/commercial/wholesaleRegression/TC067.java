/** Program Description: Create AOP policy and initiate a self service endorsement. Check if the endt quote refers to the USM for approval.
 *  Author			   : Abha
 *  Date of Creation   : 12/02/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC067 extends AbstractCommercialTest {

	public TC067() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID067.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		LoginPage loginPage = new LoginPage();
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
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing variables
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
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
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
			homePage.searchQuote(quoteNumber);
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

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged Out as USM Successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login", "Logged in as Producer successfully");

			// Searching the policy
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.passTest("Producer Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// Clicking on Endorse Policy link
			Assertions.addInfo("Policy Summary Page",
					"Initiate self endorsement to Check if the endorsement quote refers to the USM for approval");
			policySummaryPage.producerEndorsePolicyLink.scrollToElement();
			policySummaryPage.producerEndorsePolicyLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse Policy link");

			// Clicking on All Other Changes link
			endorsePolicyPage.allOtherChanges.waitTillVisibilityOfElement(60);
			endorsePolicyPage.allOtherChanges.scrollToElement();
			endorsePolicyPage.allOtherChanges.click();

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Clicking on Deductibles and Coverage Link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();

			// Clicking on 'I need to change risk' button
			endorsePolicyPage.changeCharacteristicsButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCharacteristicsButton.scrollToElement();
			endorsePolicyPage.changeCharacteristicsButton.click();

			// Entering Optional Coverages
			testData = data.get(data_Value2);
			createQuotePage.editOptionalCoverageDetailsPNB(testData);
			scrollToBottomPage();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Clicking on Continue button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Below code commenting because of referral not happening
			// Entering Referral details
			testData = data.get(data_Value1);
			referQuotePage.contactEmail.scrollToElement();
			referQuotePage.contactEmail.clearData();
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.contactName.scrollToElement();
			referQuotePage.contactName.clearData();
			referQuotePage.contactName.setData(testData.get("ProducerName"));

			// Clicking on Refer Quote button
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();

			// Validating the referral message
			Assertions.addInfo("Endorse Policy Page", "Asserting the referral message");
			Assertions.verify(endorsePolicyPage.endorsementReferralMessage.getData().contains("Referral Received"),
					true, "Referral Complete Page",
					"Referral Message : " + endorsePolicyPage.endorsementReferralMessage.getData() + " is displayed",
					false, false);

			// Sign out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in as USM successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Approve Referral
			policySummaryPage.openCurrentReferral.scrollToElement();
			policySummaryPage.openCurrentReferral.click();
			referralPage.clickOnApprove();
			// approve_DeclineQuotePage.clickOnApprove();
			// Added this new code as these steps are newly included.(IO-21580)
			if (approveDeclineQuotePage.internalUnderwriterComments.checkIfElementIsPresent()) {
				approveDeclineQuotePage.internalUnderwriterComments.scrollToElement();
				approveDeclineQuotePage.internalUnderwriterComments.setData("Test");
			}
			if (approveDeclineQuotePage.externalUnderwriterComments.checkIfElementIsPresent()) {
				approveDeclineQuotePage.externalUnderwriterComments.scrollToElement();
				approveDeclineQuotePage.externalUnderwriterComments.setData("Test");
			}
			if (endorsePolicyPage.editEndtQuoteButton.checkIfElementIsPresent()) {
				endorsePolicyPage.editEndtQuoteButton.scrollToElement();
				endorsePolicyPage.editEndtQuoteButton.click();
				endorsePolicyPage.nextButton.scrollToElement();
				endorsePolicyPage.nextButton.click();
				if (endorsePolicyPage.continueButton.checkIfElementIsPresent()) {
					endorsePolicyPage.continueButton.scrollToElement();
					endorsePolicyPage.continueButton.click();
				}
				endorsePolicyPage.submitButton.scrollToElement();
				endorsePolicyPage.submitButton.click();
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Endorsement Quote approved successfully");
			}
			// new code ended

			// Validating the Referral Confirmation message
			Assertions.verify(referralPage.referralCompleteMsg.getData().contains("referred quote has been approved"),
					true, "Referral Complete Page",
					"Referral Message : " + referralPage.referralCompleteMsg.getData() + " is displayed", false, false);

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM Successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login", "Logged in as Producer successfully");

			// Searching the policy
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.passTest("Producer Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// Clicking on Endorse Policy link
			policySummaryPage.producerEndorsePolicyLink.scrollToElement();
			policySummaryPage.producerEndorsePolicyLink.click();

			// Clicking on Submit button
			endorsePolicyPage.submitButton.scrollToElement();
			endorsePolicyPage.submitButton.click();

			// Entering Referral details
			testData = data.get(data_Value1);
			referQuotePage.contactEmail.scrollToElement();
			referQuotePage.contactEmail.clearData();
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.contactName.scrollToElement();
			referQuotePage.contactName.clearData();
			referQuotePage.contactName.setData(testData.get("ProducerName"));

			// Clicking on Refer Quote button
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();

			// Validating the referral message
			Assertions.verify(endorsePolicyPage.endorsementReferralMessage.getData().contains("Referral Received"),
					true, "Referral Complete Page",
					"Referral Message : " + endorsePolicyPage.endorsementReferralMessage.getData() + " is displayed",
					false, false);

			// Sign out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Approve Referral
			policySummaryPage.openCurrentReferral.scrollToElement();
			policySummaryPage.openCurrentReferral.click();
			referralPage.clickOnApprove();

			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Endorsement Referral Approved successfully");

			// Validating the Referral Confirmation message
			Assertions.verify(referralPage.referralCompleteMsg.getData().contains("referred quote has been approved"),
					true, "Referral Complete Page",
					"Referral Message : " + referralPage.referralCompleteMsg.getData() + " is displayed", false, false);

			// Navigating to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the policy
			homePage.searchPolicy(policyNumber);

			// Validating the Endorsement
			Assertions.addInfo("Policy Summary Page", "Verifying the Endorsement Record on Policy Summary Page");
			Assertions.verify(policySummaryPage.transHistReason.formatDynamicPath(3).getData().contains("Endorsement"),
					true, "Policy Summary Page Page", "Endorsement record is displayed", false, false);

			// Signout and close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 67", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 67", "Executed Successfully");
			}
		}
	}
}