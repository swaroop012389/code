/** Program Description:Check if the following fields are available when a Wholesale policy is BOR'ed to Retail.Check Insured Email and Pay plan fields are added.
Verify by Adding a Fee for a State in a Wholesale Producer and check whether the fees changes to Retail specific fees when BOR'ed to a Retail Policy
Verify absence of Roll forward button for PCIP Endorsement
*  Author			   : Sowndarya
*  Date of Creation   : 02/11/2022
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
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PCIPEndorsementTrackerPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC029 extends AbstractCommercialTest {

	public TC029() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID029.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

// Initializing the pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		BrokerOfRecordPage brokerOfRecordPage = new BrokerOfRecordPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		PCIPEndorsementTrackerPage pcipEndorsementTrackerPage = new PCIPEndorsementTrackerPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

// Initializing the variables
		Map<String, String> testData;
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		testData = data.get(dataValue1);
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
			Assertions.passTest("Location Page", "Location details entered successfully");

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

// Click on edit ICAT fees
			accountOverviewPage.editFees.scrollToElement();
			accountOverviewPage.editFees.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Fees link");

// Add Custom Name and fees
			accountOverviewPage.addCustomFee.scrollToElement();
			accountOverviewPage.addCustomFee.click();

// Adding Broker Fee
			accountOverviewPage.waitTime(2);// if wait time is removed test case will fail here
			accountOverviewPage.customFieldValue.formatDynamicPath("1").setData(testData.get("CustomFeeValue"));

			accountOverviewPage.customFieldName.formatDynamicPath("2").setData(testData.get("CustomFeeName"));
			accountOverviewPage.customFieldValue.formatDynamicPath("2").setData(testData.get("CustomFeeValue"));

// Click on delete Button
			accountOverviewPage.waitTime(2);// if wait time is removed test case will fail here
			accountOverviewPage.customFeeDeleteTrashCan.formatDynamicPath("1").scrollToElement();
			accountOverviewPage.customFeeDeleteTrashCan.formatDynamicPath("1").click();

// Click on save button
			accountOverviewPage.saveButton.scrollToElement();
			accountOverviewPage.saveButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Other Frees save Button");

// Verifying custom fee added in View/print full quote
// click on View/Print full quote
			waitTime(3); // needed to save the new fee value added
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account OverView Page", "Clciked on View/Print Full Quote Link");

// Asserting Custom fee Name and value
			if (viewOrPrintFullQuotePage.customerFeeName.formatDynamicPath("Test Fee").checkIfElementIsPresent()
					&& viewOrPrintFullQuotePage.customerFeeName.formatDynamicPath("Test Fee")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(
						viewOrPrintFullQuotePage.customerFeeName
								.formatDynamicPath("Test Fee").checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"Custom Fee Name is displayed as : "
								+ viewOrPrintFullQuotePage.customerFeeName.formatDynamicPath("Test Fee").getData(),
						false, false);
				Assertions.verify(
						viewOrPrintFullQuotePage.customFeeValue
								.formatDynamicPath("Test Fee").checkIfElementIsDisplayed(),
						true, "View or Print Full Quote Page",
						"Custom Fee Value is displayed as : "
								+ viewOrPrintFullQuotePage.customFeeValue.formatDynamicPath("Test Fee").getData(),
						false, false);
			}

// click on GoBack button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

// click on request bind
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

// Click on approve button
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

// Release Quote to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overvirw page", "Clicked on release quote to producer button");

// clicking on producer link to process AOR in account overview page
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

// Change BOR status
			brokerOfRecordPage.borStatusArrow.scrollToElement();
			brokerOfRecordPage.borStatusArrow.click();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus"))
					.waitTillVisibilityOfElement(60);
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).scrollToElement();
			brokerOfRecordPage.borStatusOption.formatDynamicPath(testData.get("BORStatus")).click();

// Processing BOR on account overview page for renewal quote and Verifying the
// presence of Insured Email and Pay plan fields
			Assertions.addInfo("Scenario 01",
					"Processing BOR on account overview page for renewal quote and Verifying the presence of Insured Email and Pay plan fields");
			Assertions.verify(brokerOfRecordPage.insuredEmail.checkIfElementIsDisplayed(), true,
					"Broker of Record Page", "The Insured Email Field displayed is verified", false, false);
			Assertions.verify(brokerOfRecordPage.payplanRadioBtn.formatDynamicPath("0").checkIfElementIsDisplayed(),
					true, "Broker of Record Page", "The Insured - Full Pay Radio button displayed is verified", false,
					false);
			Assertions.verify(brokerOfRecordPage.payplanRadioBtn.formatDynamicPath("1").checkIfElementIsDisplayed(),
					true, "Broker of Record Page", "The Premium Finance Pay Radio button displayed is verified", false,
					false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

// Enter insured Email and Payment plan
			testData = data.get(dataValue2);
			brokerOfRecordPage.insuredEmail.setData(testData.get("ProducerEmail"));
			brokerOfRecordPage.payplanRadioBtn.formatDynamicPath("1").click();

// Click on ProcessBOR button
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();

// Asserting waring message for email id and Payment Information
			Assertions.passTest("Scenario 02", "Verifying waring message when email and payment plan are not entered");
			Assertions.verify(brokerOfRecordPage.emailWarningMsg.checkIfElementIsDisplayed(), true,
					"Broker of Record Page",
					"Warning message for email displayed as : " + brokerOfRecordPage.emailWarningMsg.getData(), false,
					false);
			Assertions.verify(brokerOfRecordPage.paymentWarningMsg.checkIfElementIsDisplayed(), true,
					"Broker of Record Page",
					"Warning message for Payment displayed as : " + brokerOfRecordPage.paymentWarningMsg.getData(),
					false, false);
			Assertions.passTest("Scenario 02", "Scenario 02 Ended");

// Enter insured Email and Payment plan
			testData = data.get(dataValue1);
			brokerOfRecordPage.insuredEmail.setData(testData.get("ProducerEmail"));
			brokerOfRecordPage.payplanRadioBtn.formatDynamicPath(0).scrollToElement();
			brokerOfRecordPage.payplanRadioBtn.formatDynamicPath(0).click();

// Click on ProcessBOR button
			brokerOfRecordPage.processBOR.scrollToElement();
			brokerOfRecordPage.processBOR.click();
			Assertions.passTest("Broker Of Record Page", "Clicked on Process BOR Button");

// Asserting Old and New producer Details
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

// Click on close Button
			brokerOfRecordPage.closeBORPage.scrollToElement();
			brokerOfRecordPage.closeBORPage.click();

// Verify ICAT fees edit pencil is not displayed
			Assertions.addInfo("Scenario 03", "Verifying if edit fees is not displayed");
			Assertions.verify(accountOverviewPage.editFees.checkIfElementIsPresent(), false, "Account Overview Page",
					"Edit Fees link is not displayed for adding new fee for Retail Producer", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

// Click on View Previous policy
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();

// click on Endorse PB
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();

// click on Ok button
			policySummarypage.okEvent.scrollToElement();
			policySummarypage.okEvent.click();

// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

// clicking on process pcip endorsement hyperlink
			Assertions.addInfo("Endorse Policy Page", "Processing PCIP Endorsement");
			endorsePolicyPage.processPCIPEndorsementLink.scrollToElement();
			endorsePolicyPage.processPCIPEndorsementLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Process PCIP Endorsement Link");

// clicking yes in Make policy unautomated page
			endorsePolicyPage.makePolicyUnAutomated_Yes.scrollToElement();
			endorsePolicyPage.makePolicyUnAutomated_Yes.click();

// Asserting the Absence of Roll Forward button in endorsement tracker page
			Assertions.verify(pcipEndorsementTrackerPage.saveButton.checkIfElementIsDisplayed(), true,
					"Endorsement Tracker Page", "Endorsement Tracker page loaded successfully", false, false);
			Assertions.addInfo("Scenario 04", "Verifying Absence of Roll Forward Button");
			Assertions.verify(endorsePolicyPage.rollForwardBtn.checkIfElementIsPresent(), false,
					"Endorsement Tracker Page", "Roll Forward Button is not displayed", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

// entering details in endorsement tracker page
			pcipEndorsementTrackerPage.enterEndorsementDetails(testData);
			Assertions.passTest("Endorsement Tracker Page", "Endorsement history updated successfully");

// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC029 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC029 ", "Executed Successfully");
			}
		}
	}
}
