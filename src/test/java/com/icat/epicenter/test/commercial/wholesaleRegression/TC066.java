/** Program Description: Create a wind policy  and initiate a self service endorsement. Check if the endt quote refers to the USM for approval.
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 12/02/2019
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
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC066 extends AbstractCommercialTest {

	public TC066() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID066.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		LoginPage loginPage = new LoginPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData;
		testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// New Account creation
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully ", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// ZipCode EligibilityPage
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Zipcode Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "ZipCode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Entering CreateQuotePage Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Getting the quote number
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind ");

			// Entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details Entered successfully");

			// Clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// Searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// SignOut
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page ", "Logged Out as USM Successfully");

			// Login As Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as producer successfully ");

			// Searching the policy
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.passTest("Producer Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// Click on Endorse policy Link
			Assertions.addInfo("Policy Summary Page",
					"Initiating  self service endorsement to Check if the endt quote refers to the USM for approval");
			policySummarypage.producerEndorsePolicyLink.scrollToElement();
			policySummarypage.producerEndorsePolicyLink.click();

			// Click on Endorse Policy link
			endorsePolicyPage.allOtherChanges.waitTillVisibilityOfElement(60);
			endorsePolicyPage.allOtherChanges.click();
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on Location/BuildinginformationLink
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);
			endorsePolicyPage.changeCharacteristicsPopup.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCharacteristicsButton.scrollToElement();
			endorsePolicyPage.changeCharacteristicsButton.click();
			Assertions.verify(locationPage.buildingLink.formatDynamicPath(1, 1).checkIfElementIsDisplayed(), true,
					"Location Page", "Location Page Loaded successfully", false, false);
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();
			testData = data.get(dataValue2);
			Assertions.verify(buildingPage.continueButton.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page Loaded successfully", false, false);
			buildingPage.modifyBuildingDetailsPNB_old(testData);
			Assertions.passTest("Building Page", "Modified the Building details successfully");
			if (buildingPage.continueButton.checkIfElementIsPresent()
					&& buildingPage.continueButton.checkIfElementIsDisplayed()) {
				buildingPage.continueButton.scrollToElement();
				buildingPage.continueButton.click();
			}
			if (createQuotePage.continueEndorsementButton.checkIfElementIsPresent()
					&& createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueEndorsementButton.scrollToElement();
				createQuotePage.continueEndorsementButton.click();
			}

			// Click On Next Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Click On Submit Button
			endorsePolicyPage.submitButton.scrollToElement();
			endorsePolicyPage.submitButton.click();

			// Entering Contact Name and Email
			Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
					"Refer Quote Page loaded successfully", false, false);
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));

			// Click on Refer Quote
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();

			// Refferal message
			Assertions.verify(referralPage.referalReceivedMsg.checkIfElementIsDisplayed(), true, "Referral Page",
					"Endorsement Bind referred successfully", false, false);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as Producer successfully");
			loginPage.refreshPage();

			// Login as tfodor
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login Page",
					"Login Page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully ");

			// adding the cr IO-20569
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.myReferralArrow.scrollToElement();
			homePage.myReferralArrow.click();
			homePage.myReferralsOption.formatDynamicPath(testData.get("ReferralOption"))
					.waitTillVisibilityOfElement(60);
			homePage.myReferralsOption.formatDynamicPath(testData.get("ReferralOption")).scrollToElement();
			homePage.myReferralsOption.formatDynamicPath(testData.get("ReferralOption")).click();
			Assertions.passTest("Home Page",
					"The My Referral filter Option selected is " + testData.get("ReferralOption"));
			homePage.referralquoteLink.formatDynamicPath(policyNumber).waitTillVisibilityOfElement(60);

			// Verifying the Endt quote is available under my referrals
			Assertions.verify(homePage.referralquoteLink.formatDynamicPath(policyNumber).checkIfElementIsDisplayed(),
					true, "Home Page", "The My Referrals Result table displayed the PolicyNumber is verified", false,
					false);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as Producer successfully");
			loginPage.refreshPage();

			// Login as tfodor
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login Page",
					"Login Page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully ");

			// Searching the policy Number in grid
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// Approving the request
			policySummarypage.openCurrentReferral.scrollToElement();
			policySummarypage.openCurrentReferral.click();
			referralPage.clickOnApprove();
			approveDeclineQuotePage.clickOnApprove();
			Assertions.verify(referralPage.referralCompleteMsg.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referred Quote approved Successfully", false, false);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as producer successfully ");

			// Searching the policy
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.passTest("Producer Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// Verifying Endorsement has been done or not
			Assertions.addInfo("Policy Summary Page", "Verifying Endorsement Record in Policy Summary Page");
			testData = data.get(dataValue1);
			Assertions.verify(
					policySummarypage.producerTransactionType.formatDynamicPath(testData.get("TransactionType"))
							.getData(),
					testData.get("TransactionType"), "Policy Summary Page", "PB Endorsement Record is Verified", false,
					false);

			// Go to Home Page and SignOut
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 66", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 66", "Executed Successfully");
			}
		}
	}
}
