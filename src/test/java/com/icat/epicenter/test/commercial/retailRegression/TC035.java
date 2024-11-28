/** Program Description: Check South Carolina quotes have notice printed after the loss history section and before the signature section for renewal quote
Check if the system is able to add their own fees when Retail policy is BOR'ed to a Wholesale policy.
*  Author			   : Sowndarya
*  Date of Creation   : 02/02/2022
**/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BrokerOfRecordPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC035 extends AbstractCommercialTest {

	public TC035() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID035.xls";
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
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		BrokerOfRecordPage brokerOfRecordPage = new BrokerOfRecordPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
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
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			// click on user preference link and enter the details
			Assertions.addInfo("Home Page", "Entering User Preference Details");
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");

			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			preferenceOptionsPage.taxesAndFeesTab.scrollToElement();
			preferenceOptionsPage.taxesAndFeesTab.click();

			// deselect sltf checkbox
			if (preferenceOptionsPage.enableSLTFCheckbox.checkIfElementIsSelected()) {
				preferenceOptionsPage.enableSLTFCheckbox.deSelect();
				Assertions.passTest("Preference Opitons Page", "Surplus Lines,Taxes and Fees checkbox is deselected");
			}

			// click on save preference
			preferenceOptionsPage.savePreferences.scrollToElement();
			preferenceOptionsPage.savePreferences.click();

			// logout as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"USM Home page loaded successfully", false, false);

			// create new account
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
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			if (requestBindPage.chooseFile.checkIfElementIsPresent()
					&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
				if (!testData.get("FileNameToUpload").equals("")) {
					policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
				}
			}
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
			Assertions.addInfo("Policy Summary Page", "Renew NB Policy");
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

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View/Print Full quote link");

			// Assert the SC state Wordings under loss section
			Assertions.addInfo("Scenario 01", "Asserting Notice Wordings for SC State for renewal quote");
			Assertions.verify(
					viewOrPrintFullQuotePage.scWording.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.scWording.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					viewOrPrintFullQuotePage.scWording.getData() + " displayed is verified for SC State", false, false);
			Assertions.addInfo("Scenario 01", "Scenario o1 Ended");

			// click on back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// clicking on producer link to process AOR in account overview page
			Assertions.addInfo("Scenario 02",
					"Processing BOR on account overview page for renewal quote and assert the changed producer number");
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.producerLink.scrollToElement();
			accountOverviewPage.producerLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Producer Link");

			// changing the producer details
			Assertions.verify(brokerOfRecordPage.processBOR.checkIfElementIsDisplayed(), true, "Broker of Record Page",
					"Broker of Record Page loaded successfully", false, false);

			// Entering New producer Number
			brokerOfRecordPage.newProducerNumber.scrollToElement();
			brokerOfRecordPage.newProducerNumber.setData(testData.get("NewBORProducerNumber"));
			Assertions.passTest("Broker of Record Page",
					"New Producer Number : " + brokerOfRecordPage.newProducerNumber.getData());

			brokerOfRecordPage.borStatusArrow.scrollToElement();
			brokerOfRecordPage.borStatusArrow.click();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus"))
					.waitTillVisibilityOfElement(60);
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).scrollToElement();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();

			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();
			Assertions.passTest("Broker of Record Page", "Clicked on Process BOR");

			brokerOfRecordPage.closeBORPage.scrollToElement();
			brokerOfRecordPage.closeBORPage.click();
			Assertions.passTest("Broker Of Record Page", "Changed the Producer Number successfully");
			Assertions
					.verify(accountOverviewPage.producerNumber.getData().contains("Wholesale"), true,
							"Account Overview Page", "The Changed producer number : "
									+ accountOverviewPage.producerNumber.getData() + " displayed is verified ",
							false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// verifying the presence of edit fee pencil icon
			Assertions.addInfo("Scenario 03", "Verifying the presence of Edit pencil icon next to ICAT Fees");
			Assertions.verify(accountOverviewPage.editFees.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Edit Fees button displayed is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Verify the absence of Surplus Lines Taxes & Fees after BOR is done
			Assertions.addInfo("Scenario 04", "Verifying the absence of Surplus Lines Taxes & Fees after BOR is done");
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), false, "Account Overview Page",
					"Surplus Lines Taxes & Fees is not displayed when SLTF checkbox is disabled", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// click on view previous policy
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Previous Policy Button");

			// click on Endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// click on ok
			if (endorsePolicyPage.okButton.checkIfElementIsPresent()
					&& endorsePolicyPage.okButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.okButton.scrollToElement();
				endorsePolicyPage.okButton.click();
			}

			// setting Endoresment Effective Date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// click on change coverage options link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// entering details in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialPNB(testData);
			Assertions.passTest("Create Quote Page", "Details modified successfully");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next Button");

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// click on No,Leave the Renewal Account as it is Button displayed
			Assertions.addInfo("Scenario 05", "Verifying the absence of Rollforward Button");
			Assertions.verify(endorsePolicyPage.rollForwardBtn.checkIfElementIsPresent(), false, "Endorse Policy Page",
					"Roll Forward Button not present is verified After Processing BOR", false, false);
			Assertions.passTest("Scenario 05", "Scenario 05 Ended");

			// click on close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// logout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Login as Producer pbu_retail@2021.com
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("RetailProducer"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in as pbu_retail@2021.com producer successfully");
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			// search the policy
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.passTest("Home Page", "Searched the Policy " + policyNumber + " Successfully");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded Successfully", false, false);

			// click on endorse policy link
			policySummaryPage.producerEndorsePolicyLink.scrollToElement();
			policySummaryPage.producerEndorsePolicyLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse Policy link");

			// click on allother link
			endorsePolicyPage.waitTime(2);
			endorsePolicyPage.allOtherChanges.scrollToElement();
			endorsePolicyPage.allOtherChanges.click();
			Assertions.passTest("Endorse Policy Page", "Clicked On All Other Changes link");

			// Verifying producer can not endorse warning message
			Assertions.addInfo("Scenario 06",
					"Verifying the producer can not endorse warning message after processing BOR");
			Assertions.verify(endorsePolicyPage.producerEndorseWarningMessage.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "The Warning message "
							+ endorsePolicyPage.producerEndorseWarningMessage.getData() + " displyed is verified",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// logout as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"USM Home page loaded successfully", false, false);

			// search the policy
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the Policy " + policyNumber + " Successfully");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded Successfully", false, false);

			// click on view active renewal
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Active Renewal link");

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// verify the absence of payment selection options after changing the producer
			// number to 8521.1
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Request Bind Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 07",
					"Verifying the absence of PFC payment plan after cahnging the producer number from retail to wholsesale");
			Assertions.verify(requestBindPage.financePay.checkIfElementIsPresent(), false, "Request Bind Page",
					"PFC payment type not present is verified", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Signin as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer successfully");

			// Searching the policy
			testData = data.get(data_Value1);
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Quote " + quoteNumber + " successfully");
			Assertions.verify(accountOverviewPage.editFees.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.editFees.scrollToElement();
			accountOverviewPage.editFees.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Fees Button successfully");

			// Click on add custom fee
			Assertions.addInfo("Scenario 08", "Entering the Fees using Edit Fee button under the changed producer");
			accountOverviewPage.addCustomFee.click();
			accountOverviewPage.addCustomFee.formatDynamicPath(2).waitTillPresenceOfElement(60);
			accountOverviewPage.customFieldName.formatDynamicPath(2).setData(testData.get("CustomFieldName"));
			accountOverviewPage.customFieldValue.formatDynamicPath(2).waitTillPresenceOfElement(60);
			accountOverviewPage.customFieldValue.formatDynamicPath(2).setData(testData.get("CustomFieldValue"));
			accountOverviewPage.customeFeeSave.waitTillVisibilityOfElement(60);
			accountOverviewPage.customeFeeSave.click();
			accountOverviewPage.saveButton.waitTillVisibilityOfElement(60);
			accountOverviewPage.saveButton.click();
			String producerFees = testData.get("CustomFieldValue");
			Assertions.passTest("Account Overview Page",
					"Clicked on Edit fees Button and Entered Producer Fees :" + producerFees);
			Assertions.passTest("Scenario 08", "Scenario 08 Ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC035 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC035 ", "Executed Successfully");
			}
		}
	}
}
