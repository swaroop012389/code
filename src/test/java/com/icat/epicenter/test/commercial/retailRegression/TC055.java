/** Program Description: Verify if wholesale policy is moved to retail via batch BOR, the renewal quote will generate as retail,display of taxes and fees, absence of edit fees
 *  Author			   : Sowndarya
 *  Date of Creation   : 08/04/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BatchBrokerOfRecord;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC055 extends AbstractCommercialTest {

	public TC055() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID055.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		BatchBrokerOfRecord batchBrokerOfRecord = new BatchBrokerOfRecord();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// New Account creation
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully ", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on Create new account link
			if (homePage.createNewAccount.checkIfElementIsPresent()
					&& homePage.createNewAccount.checkIfElementIsDisplayed()) {
				homePage.createNewAccount.scrollToElement();
				homePage.createNewAccount.click();
			}

			// Enter insured name
			homePage.namedInsured.setData(testData.get("InsuredName"));
			Assertions.passTest("Home Page", "Insured Name is " + testData.get("InsuredName"));

			// Enter producer number
			if (homePage.producerNumber.checkIfElementIsPresent()) {
				homePage.producerNumber.setData("8521.1");
			}

			// Select product
			if (homePage.productSelection.formatDynamicPath(testData.get("ProductSelection"))
					.checkIfElementIsPresent()) {
				homePage.productArrow.scrollToElement();
				homePage.productArrow.click();
				homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			}

			// Enter effective date
			if (homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(1).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			} else {
				homePage.effectiveDate.formatDynamicPath(2).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(2).waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath(2).setData(testData.get("PolicyEffDate"));
			}

			// Click on go button
			homePage.goButton.click();
			homePage.loading.waitTillInVisibilityOfElement(60);
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
			Assertions.passTest("Home Page", "Searched Submitted quote " + quoteNumber + " successfullly");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

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

			// Go to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// click on batch broker of record
			homePage.scrollToBottomPage();
			homePage.batchBrokerofRecordLink.scrollToElement();
			homePage.batchBrokerofRecordLink.click();

			// Enter From and to producer numbers
			batchBrokerOfRecord.refreshPage();
			batchBrokerOfRecord.waitTime(5);
			Assertions.verify(batchBrokerOfRecord.submit.checkIfElementIsDisplayed(), true,
					"Batch Broker of Record Page", "Batch Broker of Record Page Loaded successfully", false, false);
			batchBrokerOfRecord.fromProducerNumber.setData(testData.get("ProducerNumber"));
			batchBrokerOfRecord.toProducerNumber.setData(testData.get("NewBORProducerNumber"));

			// click on submit
			batchBrokerOfRecord.submit.waitTillPresenceOfElement(60);
			batchBrokerOfRecord.submit.scrollToElement();
			batchBrokerOfRecord.submit.click();

			// Find the policy and select the check box
			batchBrokerOfRecord.refreshPage();
			batchBrokerOfRecord.waitTime(5);
			batchBrokerOfRecord.refreshPage();
			batchBrokerOfRecord.policyNumberCheckBox.formatDynamicPath(policyNumber).waitTillPresenceOfElement(60);
			batchBrokerOfRecord.policyNumberCheckBox.formatDynamicPath(policyNumber).scrollToElement();
			batchBrokerOfRecord.policyNumberCheckBox.formatDynamicPath(policyNumber).select();
			batchBrokerOfRecord.scrollToTopPage();

			// click on change broker of record button
			batchBrokerOfRecord.changeBrokerOfRecord.scrollToElement();
			batchBrokerOfRecord.changeBrokerOfRecord.click();

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// click on expacc link
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
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// click on continue
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

			Assertions.addInfo("Scenario 01",
					"Verify the presence of Retail producer number,absence of edit fees icon ,Surplus Lines Taxes & Fees after Batch broker of record is completed");
			Assertions
					.verify(accountOverviewPage.producerNumber.getData().contains("Retail"), true,
							"Account Overview Page", "The Changed producer number : "
									+ accountOverviewPage.producerNumber.getData() + " displayed is verified ",
							false, false);
			Assertions.verify(accountOverviewPage.editFees.checkIfElementIsPresent(), false, "Account Overview Page",
					"Edit Fees Icon not present is verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes & Fees displayed is verified", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as Wholesale producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// Searching the policy
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);

			// search the renewal quote
			Assertions.addInfo("Scenario 02",
					"Verifying the absence of Renewal quote under wholesale producer after batch BOR'ed from wholesale to retail producer");
			homePage.producerQuoteSearchButton.scrollToElement();
			homePage.producerQuoteSearchButton.click();
			homePage.producerQuoteNumberSearchTextbox.scrollToElement();
			homePage.producerQuoteNumberSearchTextbox.setData(quoteNumber);
			homePage.producerQuoteFindButton.scrollToElement();
			homePage.producerQuoteFindButton.click();
			Assertions.verify(homePage.producerQuoteNumberLink.formatDynamicPath(quoteNumber).checkIfElementIsPresent(),
					false, "Home Page", "The Renewal Quore Number not available  under wholesale producer is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as retail producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("RetailProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// Searching the quote
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);

			// search the renewal quote
			Assertions.addInfo("Scenario 03",
					"Verifying the presence of Renewal quote under retail producer after batch BOR'ed from wholesale to retail producer");
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Renewal Quote Available under Retail producer is verified", false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC055 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC055 ", "Executed Successfully");
			}
		}
	}
}
