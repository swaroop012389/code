/** Program Description:  Verify whether Retail producer is able to make BOR to another Retail Producer and
 * 						  verify if the user gets the system adjustment warning message for BI Coverage if the BI value is set relatively high $ compared to Bldg and BPP values
 *  Author			   : Priyanka S
 *  Date of Creation   : 24/037/2022
 **/

package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BrokerOfRecordPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC050 extends AbstractCommercialTest {

	public TC050() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID050.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		BrokerOfRecordPage brokerOfRecordPage = new BrokerOfRecordPage();

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

			// Entering Location Details - BI Value
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
			// Entering Prior Losses
			if (priorLossPage.continueButton.checkIfElementIsPresent()
					&& priorLossPage.continueButton.checkIfElementIsDisplayed()) {
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

			// Click on Request bind
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

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// click on expacc link and entering expacc details
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

			if (policyRenewalPage.renewalYes.checkIfElementIsPresent()
					&& policyRenewalPage.renewalYes.checkIfElementIsDisplayed()) {
				policyRenewalPage.renewalYes.scrollToElement();
				policyRenewalPage.renewalYes.click();
			}

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

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// click on Producer edit button
			accountOverviewPage.producerLink.scrollToElement();
			accountOverviewPage.producerLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on producer edit link");

			// enter New producer Number and status
			Assertions.verify(brokerOfRecordPage.processBOR.checkIfElementIsDisplayed(), true, "Broker of Record Page",
					"Broker of Record Page loaded successfully", false, false);
			brokerOfRecordPage.newProducerNumber.setData(testData.get("NewBORProducerNumber"));
			brokerOfRecordPage.borStatusArrow.click();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();

			// click on Process BOR
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();
			Assertions.passTest("Broker Of Record", "Clicked on Process BOR Butoon");

			// Asserting Old and New producer Details
			Assertions.addInfo("Scenario 01", "Asserting New Producer Details");
			Assertions.verify(
					brokerOfRecordPage.producerDetails
							.formatDynamicPath("Original Producer Name").checkIfElementIsDisplayed(),
					true, "Broker of Record Page",
					"Original Producer Name : "
							+ brokerOfRecordPage.producerDetails.formatDynamicPath("Original Producer Name").getData(),
					false, false);
			Assertions.verify(
					brokerOfRecordPage.producerDetails.formatDynamicPath("Original Producer Number")
							.checkIfElementIsDisplayed(),
					true, "Broker of Record Page", "Original Producer Number : " + brokerOfRecordPage.producerDetails
							.formatDynamicPath("Original Producer Number").getData(),
					false, false);

			Assertions.verify(
					brokerOfRecordPage.producerDetails
							.formatDynamicPath("New Producer Name").checkIfElementIsDisplayed(),
					true, "Broker of Record Page",
					"New Producer Name : "
							+ brokerOfRecordPage.producerDetails.formatDynamicPath("New Producer Name").getData(),
					false, false);
			Assertions.verify(
					brokerOfRecordPage.producerDetails
							.formatDynamicPath("New Producer Number").checkIfElementIsDisplayed(),
					true, "Broker of Record Page",
					"New Producer Number : "
							+ brokerOfRecordPage.producerDetails.formatDynamicPath("New Producer Number").getData(),
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Clcik on Close Button
			brokerOfRecordPage.closeBORPage.scrollToElement();
			brokerOfRecordPage.closeBORPage.click();
			Assertions.passTest("Broker of Record Page", "Clicked on Close Button");

			// Click on Edit Location
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account OverView Page", "Clicked on Edit Location Link");

			// Updating BI value
			testData = data.get(data_Value2);
			locationPage.businessIncome.scrollToElement();
			locationPage.businessIncome.setData(testData.get("L1-LocBI"));
			Assertions.passTest("Location Page", "Updated BI value is : " + locationPage.businessIncome.getData());

			// Click on Create A quote
			locationPage.createQuoteButton.scrollToElement();
			locationPage.createQuoteButton.click();
			Assertions.passTest("Location Page", "Clicked on Creae A Quote Button");

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Deductibles details entered successfully");

			// Click on Get A Quote Button
			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}

			// Asserting waring message
			Assertions.addInfo("Scenario 02", "Asserting warning messages for BI Coverage");
			Assertions
					.verify(createQuotePage.wdrWarning.formatDynamicPath("BI coverage").checkIfElementIsDisplayed(),
							true, "Create A Quote Page",
							"Waring Message for Limit applied on BI is verified and disaplyed as : "
									+ createQuotePage.wdrWarning.formatDynamicPath("BI coverage").getData(),
							false, false);
			Assertions.verify(
					createQuotePage.wdrWarning
							.formatDynamicPath("$1,000,000 in business income").checkIfElementIsDisplayed(),
					true, "Create A Quote Page",
					"Waring Message for Business Income for Account is verified and disaplyed as : "
							+ createQuotePage.wdrWarning.formatDynamicPath("$1,000,000 in business income").getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC050 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC050 ", "Executed Successfully");
			}
		}
	}
}
