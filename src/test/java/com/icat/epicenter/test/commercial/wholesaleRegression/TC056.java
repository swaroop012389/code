/** Program Description: Endorse and update policy expiration date as producer and assert the referral message
 *  Author			   : Yeshashwini T A
 *  Date of Creation   : 15/11/2019
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
import com.icat.epicenter.pom.GLInformationPage;
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

public class TC056 extends AbstractCommercialTest {

	public TC056() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID056.xls";
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
		GLInformationPage glInformationPage = new GLInformationPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		LoginPage loginPage = new LoginPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approve_DeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
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
			locationPage = eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// entering general liability details
			if (!testData.get("L1-GLLocationClass").equals("")) {
				Assertions.verify(glInformationPage.continueButton.checkIfElementIsDisplayed(), true,
						"GL Information Page", "GL Information Page loaded successfully", false, false);
				glInformationPage.enterGLInformation(testData);
				Assertions.passTest("GL Information Page", "GL Information details entered successfully");
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

			// signout from application
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			loginPage.refreshPage();

			// login as producer
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as Producer successfully");

			// search for the policy number
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.passTest("Producer Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// endorse policy
			policySummarypage.producerEndorsePolicyLink.scrollToElement();
			policySummarypage.producerEndorsePolicyLink.click();

			// clicking on change policy expiration date hyperlink
			endorsePolicyPage.changeExpirationDateLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeExpirationDateLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeExpirationDateLink.scrollToElement();
			endorsePolicyPage.changeExpirationDateLink.click();

			// setting policy Expiration Date
			Assertions.verify(endorsePolicyPage.changeExpirationDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.policyExpirationDate.scrollToElement();
			endorsePolicyPage.policyExpirationDate.setData(testData.get("PolicyExpirationDate"));
			Assertions.passTest("Endorse Policy Page", "Changed Policy Expiration Date");

			// clicking on change Expiration date
			endorsePolicyPage.changeExpirationDate.scrollToElement();
			endorsePolicyPage.changeExpirationDate.click();

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Assert referral message
			Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
					"Refer Quote page loaded successfully", false, false);
			Assertions.addInfo("Refer Quote Page", "Asserting Referral message when Policy Expiration date is changed");
			Assertions.verify(
					referQuotePage.refMessageForUSM.getData().contains("Policy Expiration Date change requested"), true,
					"Refer Quote Page",
					"Referral message, " + referQuotePage.refMessageForUSM.getData() + " is displayed", false, false);

			// enter the refrral details
			referQuotePage.contactName.scrollToElement();
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.scrollToElement();
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();
			Assertions.passTest("Refer Quote Page", "Endorsement Quote referred successfully");

			// signout from application
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as Producer Successfully");
			loginPage.refreshPage();

			// Login to USM account
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// searching the policy number in grid and clicking on the policy number link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");
			referralPage.clickOnApprove();
			// approve_DeclineQuotePage.clickOnApprove();
			// Added this new code as these steps are newly included.(IO-21580)
			if (approve_DeclineQuotePage.internalUnderwriterComments.checkIfElementIsPresent()) {
				approve_DeclineQuotePage.internalUnderwriterComments.scrollToElement();
				approve_DeclineQuotePage.internalUnderwriterComments.setData("Test");
			}
			if (approve_DeclineQuotePage.externalUnderwriterComments.checkIfElementIsPresent()) {
				approve_DeclineQuotePage.externalUnderwriterComments.scrollToElement();
				approve_DeclineQuotePage.externalUnderwriterComments.setData("Test");
			}
			if (endorsePolicyPage.editEndtQuoteButton.checkIfElementIsPresent()) {
				endorsePolicyPage.editEndtQuoteButton.scrollToElement();
				endorsePolicyPage.editEndtQuoteButton.click();
				endorsePolicyPage.nextButton.scrollToElement();
				endorsePolicyPage.nextButton.click();
				endorsePolicyPage.submitButton.scrollToElement();
				endorsePolicyPage.submitButton.click();
				approve_DeclineQuotePage.clickOnApprove();
			}
			// new code ended

			Assertions.passTest("Referral Page", "Endorsement quote approved successfully");

			// clicking on close button in referral complete page
			approve_DeclineQuotePage.closeButton.scrollToElement();
			approve_DeclineQuotePage.closeButton.click();

			// signout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			loginPage.refreshPage();

			// login as producer
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as Producer successfully");

			// search for the policy number
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.passTest("Producer Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// endorse policy
			policySummarypage.producerEndorsePolicyLink.scrollToElement();
			policySummarypage.producerEndorsePolicyLink.click();

			// clicking on Submit in Endorse policy page
			endorsePolicyPage.submitButton.scrollToElement();
			endorsePolicyPage.submitButton.click();

			// Assert Referral message
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();
			Assertions.passTest("Refer Quote Page", "Endorsement Bind referred successfully");

			// signout as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			loginPage.refreshPage();

			// Login to USM account
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// searching the policy number in grid and clicking on the policy number
			// link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Endorsement bind loaded successfully");

			// approving referral
			policySummarypage.openCurrentReferral.scrollToElement();
			policySummarypage.openCurrentReferral.click();
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}
			referralPage.clickOnApprove();
			approve_DeclineQuotePage.clickOnApprove();
			Assertions.passTest("Home Page", "Endorsement bind approved successfully");

			// click on close button
			approve_DeclineQuotePage.closeButton.scrollToElement();
			approve_DeclineQuotePage.closeButton.click();

			// signout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// login as producer
			loginPage.refreshPage();
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as Producer successfully");

			// search for the policy number
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.passTest("Producer Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// verifying PB Endorsement record in policy summary page
			Assertions.addInfo("Policy Summary Page", "Verifying PB Endorsement record in policy summary page");
			testData = data.get(dataValue1);
			Assertions.verify(
					policySummarypage.producerTransactionType.formatDynamicPath(testData.get("TransactionType"))
							.getData(),
					testData.get("TransactionType"), "Policy Summary Page", "PB Endorsement Record Verified", false,
					false);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 56", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 56", "Executed Successfully");
			}
		}
	}
}