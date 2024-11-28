/** Program Description: Asserting moratorium warning message for AL State
 *  Author			   : Pavan mule
 *  Date of Creation   : 09/03/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.MoratoriumPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC047 extends AbstractCommercialTest {

	public TC047() {
		super(LoginType.ADMIN);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID047.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		LoginPage loginPage = new LoginPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		MoratoriumPage moratoriumPage = new MoratoriumPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		// Initializing variables
		String quoteNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		String policyNumber;
		boolean isTestPassed = false;

		try {
			// Creating Moratorium for AL state
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home  Page",
					"Rzimmer Home page loaded successfully", false, false);
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home Page", "Clicked on Admin link successfully");

			// Click on moratorium link
			Assertions.verify(healthDashBoardPage.moratoriumLink.checkIfElementIsDisplayed(), true,
					"HealthDashBoard  Page", "HealthDashBoard Page loaded successfully", false, false);
			healthDashBoardPage.moratoriumLink.scrollToElement();
			healthDashBoardPage.moratoriumLink.click();
			Assertions.passTest("HealthDashBoard  Page ", "Clicked on Moratorium link successfully");

			// Click on moratorium link
			Assertions.verify(moratoriumPage.createMoratoriumLink.checkIfElementIsDisplayed(), true,
					"Moratorium  Page ", "Moratorium Page loaded successfully", false, false);
			moratoriumPage.createMoratoriumLink.scrollToElement();
			moratoriumPage.createMoratoriumLink.click();

			// Entering moratorium details
			String moratoriumZipcode = testData.get("ZipCode");
			Assertions.passTest("Moratorium Page ", "Created Moratorium for the zipcode " + moratoriumZipcode);
			moratoriumPage.enterMoratoriumDetails(testData);
			Assertions.passTest("Moratorium Page ", "Moratorium details entered successfully");

			// Logout as rzimmer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home  Page ", "SignOut as Rzimmer successfully");

			// login as USM
			Assertions.passTest("Login Page", "Login Page loaded successfully");
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in as USM successfully");

			// creating New account
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

			// click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Verifying the presence of moratorium warning message
			Assertions.addInfo("Scenario 01", "Asserting Moratorium warning message in request bind page ");
			Assertions.verify(
					requestBindPage.moratoriumMsg.checkIfElementIsPresent()
							&& requestBindPage.moratoriumMsg.checkIfElementIsDisplayed(),
					true, "Request Bind Page ",
					"Moratorium warning message, " + requestBindPage.moratoriumMsg.getData() + " is displayed", false,
					false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Enter bind details
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// Asserting moratorium message on bind approval page "A moratorium is in effect
			// for one or more zip codes on this account. Confirm that this transaction is
			// within your authority for moratorium processing."
			Assertions.addInfo("Scenario 02",
					"Asserting Moratorium warning message in request bind page while approving referral");
			Assertions.verify(requestBindPage.moratoriumWarningMsg.checkIfElementIsDisplayed(), true,
					"Request Bind Page",
					"Moratorium warning message, " + requestBindPage.moratoriumWarningMsg.getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on approve button
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number : " + policyNumber, false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// click on expacc link and add expacc details
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// click on renew policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// click on yes
			if (policyRenewalPage.yesButton.checkIfElementIsPresent()
					&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.yesButton.scrollToElement();
				policyRenewalPage.yesButton.click();
			}

			// getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

			// Click on create another quote and increasing coverage value
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account overview page", "Clicked on create another quote link");

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.passTest("Create quote page",
					"Original building coverage value is: " + testData.get("L1B1-BldgValue"));
			testData = data.get(data_Value2);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();

			// updating building value
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();

			// Click on getAquote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote page",
					"Latest building coverage value is: " + testData.get("L1B1-BldgValue"));
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// getting the Renewal Re quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal ReQuote Number :  " + quoteNumber);

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click on Release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on release renewal to producer");

			// Click on issue quote
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on issue quote ");

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on request bind button");

			// Enter renewal bind details
			testData = data.get(data_Value1);
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request Bind Page", "Clicked on request bind button");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);

			// Asserting Moratorium warning message on Renewal Re quote Referral Page while
			// approving referral
			Assertions.addInfo("Scenario 03",
					"Asserting Moratorium warning message on renewal Requote referral page while approving referral");
			Assertions.verify(referralPage.referralReason.formatDynamicPath("moratorium").checkIfElementIsDisplayed(),
					true, "Referral Page ",
					"Moratorium referral message, "
							+ referralPage.referralReason.formatDynamicPath("moratorium").getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on approve
			referralPage.clickOnApprove();
			Assertions.passTest("Referral page", "Clicked on approve link");

			// Asserting mortorium message on renewal bind approval page "A moratorium is in
			// effect
			// for one or more zip codes on this account. Confirm that this transaction is
			// within your authority for moratorium processing."
			Assertions.addInfo("Scenario 04",
					"Asserting Moratorium warning message in Request Bind Page while approving referral");
			Assertions.verify(requestBindPage.moratoriumWarningMsg.checkIfElementIsDisplayed(), true,
					"Request Bind Page",
					"Moratorium warning message, " + requestBindPage.moratoriumWarningMsg.getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on approve button
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get Renewal policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Renewal Policy Number : " + policyNumber, false, false);

			// Checking policy status as "active"
			Assertions.addInfo("Scenario 05", "Asserting policy status ");
			Assertions.verify(policySummaryPage.policyStatus.getData().contains("Active"), true, "Policy summary page",
					"Policy status is " + policySummaryPage.policyStatus.getData() + " displayed", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// SignOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page ", "Logged out as USM successfully");

			// Signin as Rzimmer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as Rzimmer Successfully");

			// Deleting Moratorium for zipcode
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page ",
					"Rzimmer Home page loaded successfully", false, false);
			testData = data.get(data_Value1);
			moratoriumPage.deleteMoratorium(testData.get("MoratoriumDescription"));
			Assertions.verify(moratoriumPage.deleteLink.checkIfElementIsPresent(), false, "Moratorium Page ",
					"Moratorium for Zipcode " + testData.get("ZipCode") + " deleted successfully", false, false);

			// SignOut as Rzimmer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page ", "Logged out as Rzimmer successfully");

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC047 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC047 ", "Executed Successfully");
			}
		}
	}
}
