/** Program Description: Create a policy as a producer and initiate a self service endorsement. Check if the endt bind refers to the USM for approval.
 *  Author			   : Yeshashwini TA
 *  Date of Creation   : 12/09/2019
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

public class TC065 extends AbstractCommercialTest {

	public TC065() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID065.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
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
		ApproveDeclineQuotePage approve_DeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
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
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

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

			// Click on request bind
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

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Login Page", "Logged Out as USM Successfully");
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
			Assertions.addInfo("Policy Summary Page",
					"Initiating endorsement to Check if the endorsement bind refers to the USM for approval");
			policySummarypage.producerEndorsePolicyLink.scrollToElement();
			policySummarypage.producerEndorsePolicyLink.click();

			// clicking on all other changes link
			endorsePolicyPage.allOtherChanges.waitTillVisibilityOfElement(60);
			endorsePolicyPage.allOtherChanges.scrollToElement();
			endorsePolicyPage.allOtherChanges.click();

			// setting Endoresment Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on change coverage options hyperlink
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);
			endorsePolicyPage.changeCharacteristicsPopup.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCharacteristicsButton.scrollToElement();
			endorsePolicyPage.changeCharacteristicsButton.click();

			// modifying deductibles and coverages in create quote page
			testData = data.get(dataValue2);
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialPNB(testData);
			Assertions.passTest("Create Quote Page", "Deductibles and coverages details modified successfully");

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// enter details in refer quote page
			testData = data.get(dataValue1);
			Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
					"Refer Quote page loaded successfully", false, false);
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

			// searching the policy number in grid and clicking on the policy number
			// link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Endorsement Quote loaded successfully");

			// approving referral
			policySummarypage.openCurrentReferral.scrollToElement();
			policySummarypage.openCurrentReferral.click();
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
				Assertions.passTest("Referral Page", "Endorsement Quote approved successfully");
			}
			// new code ended

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

			// clicking on refer quote
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
			Assertions.passTest("Home Page", "Endorsement Bind loaded successfully");

			// approving referral
			policySummarypage.openCurrentReferral.scrollToElement();
			policySummarypage.openCurrentReferral.click();
			referralPage.clickOnApprove();
			approve_DeclineQuotePage.clickOnApprove();
			Assertions.passTest("Home Page", "Endorsement Bind approved successfully");

			// click on close button
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

			// verifying PB Endorsement record in policy summary page
			Assertions.addInfo("Policy Summary Page", "Verifying the Endorsement record in policy summary page");
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
			Assertions.failTest("Commercial NBRegression Test Case 65", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 65", "Executed Successfully");
			}
		}
	}
}
