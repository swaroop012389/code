/** Program Description: Verify if retail policy is moved to wholesale via batch BOR, the renewal quote will generate as wholesale,display of taxes and fees, ability to add broker fees
 *  Author			   : Sowndarya
 *  Date of Creation   : 07/04/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BatchBrokerOfRecord;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC054 extends AbstractCommercialTest {

	public TC054() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID054.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		LoginPage loginPage = new LoginPage();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		BatchBrokerOfRecord batchBrokerOfRecord = new BatchBrokerOfRecord();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

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

			// Click on user preference link and enter the details
			Assertions.addInfo("Home Page", "Entering User Preference Details");
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");

			// Select the sltf checkbox
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			if (preferenceOptionsPage.taxesAndFeesTab.checkIfElementIsPresent()
					&& preferenceOptionsPage.taxesAndFeesTab.checkIfElementIsDisplayed()) {
				preferenceOptionsPage.taxesAndFeesTab.scrollToElement();
				preferenceOptionsPage.taxesAndFeesTab.click();
			}
			if (!testData.get("SurplusCheckbox").equals("")) {
				if (testData.get("SurplusCheckbox").equalsIgnoreCase("Yes")) {
					if (!preferenceOptionsPage.enableSLTFCheckbox.checkIfElementIsSelected()) {
						preferenceOptionsPage.enableSLTFCheckbox.select();
						Assertions.passTest("Preference Opitons Page",
								"Surplus Lines,Taxes and Fees checkbox is selected");
					}
				}
			}

			preferenceOptionsPage.brokerFeeStateArrow.click();
			preferenceOptionsPage.brokerFeeStateOption.formatDynamicPath(testData.get("QuoteState"))
					.waitTillVisibilityOfElement(60);
			preferenceOptionsPage.brokerFeeStateOption.formatDynamicPath(testData.get("QuoteState")).scrollToElement();
			preferenceOptionsPage.brokerFeeStateOption.formatDynamicPath(testData.get("QuoteState")).click();

			// click on save
			preferenceOptionsPage.savePreferences.scrollToElement();
			preferenceOptionsPage.savePreferences.click();

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as tuser
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM successfully");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// search the quote
			homePage.searchQuote(quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering bind details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
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

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Go to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// click on batch broker of record
			homePage.scrollToBottomPage();
			homePage.batchBrokerofRecordLink.scrollToElement();
			homePage.batchBrokerofRecordLink.click();

			// Enter From and to producer numbers
			Assertions.verify(batchBrokerOfRecord.submit.checkIfElementIsDisplayed(), true,
					"Batch Broker of Record Page", "Batch Broker of Record Page Loaded successfully", false, false);
			batchBrokerOfRecord.fromProducerNumber.setData(testData.get("ProducerNumber"));
			batchBrokerOfRecord.toProducerNumber.setData(testData.get("NewBORProducerNumber"));

			// click on submit
			batchBrokerOfRecord.submit.scrollToElement();
			batchBrokerOfRecord.submit.click();

			// Find the policy and select the check box
			batchBrokerOfRecord.policyNumberCheckBox.formatDynamicPath(policyNumber).scrollToElement();
			batchBrokerOfRecord.policyNumberCheckBox.formatDynamicPath(policyNumber).select();
			batchBrokerOfRecord.scrollToTopPage();

			// click on change broker of record button
			batchBrokerOfRecord.changeBrokerOfRecord.scrollToElement();
			batchBrokerOfRecord.changeBrokerOfRecord.click();

			// checking BOR confirmation message
			Assertions.verify(batchBrokerOfRecord.confirmationMessage.checkIfElementIsDisplayed(), true,
					"Batch Broker of Record Page",
					"BOR confirmation message is " + batchBrokerOfRecord.confirmationMessage.getData() + " displayed",
					false, false);

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
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
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
					"Verify the presence of wholesale producer number,edit fees icon after Batch broker of record is completed");
			Assertions
					.verify(accountOverviewPage.producerNumber.getData().contains("Wholesale"), true,
							"Account Overview Page", "The Changed producer number : "
									+ accountOverviewPage.producerNumber.getData() + " displayed is verified ",
							false, false);
			Assertions.verify(accountOverviewPage.editFees.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Edit Fees Icon Present is verified", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

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

			// Click on user preference link and enter the details
			Assertions.addInfo("Home Page", "Entering User Preference Details");
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");

			// Deselect the sltf checkbox
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			if (preferenceOptionsPage.taxesAndFeesTab.checkIfElementIsPresent()
					&& preferenceOptionsPage.taxesAndFeesTab.checkIfElementIsDisplayed()) {
				preferenceOptionsPage.taxesAndFeesTab.scrollToElement();
				preferenceOptionsPage.taxesAndFeesTab.click();
			}
			testData = data.get(data_Value2);
			if (testData.get("SurplusCheckbox").equalsIgnoreCase("No")) {
				if (preferenceOptionsPage.enableSLTFCheckbox.checkIfElementIsSelected()) {
					preferenceOptionsPage.enableSLTFCheckbox.deSelect();
					Assertions.passTest("Preference Opitons Page",
							"Surplus Lines,Taxes and Fees checkbox is deselected");
				}
			}

			preferenceOptionsPage.brokerFeeStateArrow.click();
			preferenceOptionsPage.brokerFeeStateOption.formatDynamicPath(testData.get("QuoteState"))
					.waitTillVisibilityOfElement(60);
			preferenceOptionsPage.brokerFeeStateOption.formatDynamicPath(testData.get("QuoteState")).scrollToElement();
			preferenceOptionsPage.brokerFeeStateOption.formatDynamicPath(testData.get("QuoteState")).click();

			// click on save
			preferenceOptionsPage.savePreferences.scrollToElement();
			preferenceOptionsPage.savePreferences.click();

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as tuser
			testData = data.get(data_Value1);
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM successfully");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// search the renewal quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Renewal Quote " + quoteNumber + " successfully");
			Assertions.addInfo("Scenario 02",
					"Verify the absence of Surplus Lines Taxes & Fees when Surplus Lines Taxes & Fees checkbox is disabled under user preference");
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), false, "Account Overview Page",
					"Surplus Lines Taxes & Fees not displayed is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as retail producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("RetailProducer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as Producer successfully");
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			// search the renewal quote
			Assertions.addInfo("Scenario 03",
					"Verifying the absence of Renewal quote under retail producer after batch BOR'ed from retail to wholesale producer");
			homePage.producerQuoteSearchButton.scrollToElement();
			homePage.producerQuoteSearchButton.click();
			homePage.producerQuoteNumberSearchTextbox.scrollToElement();
			homePage.producerQuoteNumberSearchTextbox.setData(quoteNumber);
			homePage.producerQuoteFindButton.scrollToElement();
			homePage.producerQuoteFindButton.click();
			Assertions.verify(homePage.producerQuoteNumberLink.formatDynamicPath(quoteNumber).checkIfElementIsPresent(),
					false, "Home Page", "The Renewal Quore Number not available  under retail producer is verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

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
			Assertions.addInfo("Scenario 04",
					"Verifying the presence of Renewal quote under wholesale producer after batch BOR'ed from retail to wholesale producer");
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Renewal Quote Available under wholesale producer is verified", false,
					false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC054 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC054 ", "Executed Successfully");
			}
		}
	}
}
