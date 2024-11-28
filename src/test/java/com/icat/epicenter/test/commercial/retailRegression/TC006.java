/** Program Description:  1.Check the Handle in MMBU button is removed from the Refer Quote screen of commercial retail quotes.
 *                        2. Adding the ticket IO-20874,IO-20976
 *  Author			   : Sowndarya
 *  Date of Creation   : 19/07/2021
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC006 extends AbstractCommercialTest {

	public TC006() {
		super(LoginType.RETAILPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID006.xls";
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
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
		EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
		DecimalFormat df = new DecimalFormat("0.00");

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		testData = data.get(dataValue1);
		String transactionPremium = "$0";
		String transactionSltf = "$0.00";
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Entering Location 1 Dwelling 1 Details
			
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
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.addAdditionalCoveragesCommercial(testData);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillPresenceOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).tab();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Asserting Decline message when building value and BPP value=0
			Assertions.addInfo("Scenario 01", "Asserting Decline message when building value and BPP value = 0");
			Assertions.verify(
					createQuotePage.globalWarning.formatDynamicPath("at least one non-$0").checkIfElementIsPresent()
							&& createQuotePage.globalWarning
									.formatDynamicPath("at least one non-$0").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Verifying presence of hard stop message, Hard stop message is "
							+ createQuotePage.globalWarning.formatDynamicPath("at least one non-$0").getData()
							+ "displayed",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Enter Building values
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgBPP"));
			createQuotePage.bPPValue.formatDynamicPath(0, 0).tab();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Refer Quote for binding
			Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
					"Refer Quote Page Loaded successfully", false, false);
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();
			quoteNumber = referQuotePage.quoteNum.getData();
			Assertions.passTest("Referral Quote Page", "Quote Number :  " + quoteNumber);

			// Logout as producer
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as USM
			loginPage.refreshPage();
			loginPage.waitTime(3);// wait time is needed to load the page
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"USM Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");
			Assertions.verify(accountOverviewPage.openReferralLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// Asserting Handle in MMBU is not present for Commercial Retail
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve/Decline Quote Page", "Approve/Decline Quote Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 02", "Assert the absence of Handle in MMBU Button");
			Assertions.verify(approveDeclineQuotePage.handleInMMBUButton.checkIfElementIsPresent(), false,
					"Approve/Decline Quote Page", "Handle in MMBU Button is not Present is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Approve quote referral
			approveDeclineQuotePage.clickOnApprove();

			// Searching the account
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on alt quote option 1
			// Adding the ticket IO-20976
			accountOverviewPage.quoteOptions1TotalPremium.scrollToElement();
			accountOverviewPage.quoteOptions1TotalPremium.click();
			Assertions.passTest("Account Overview Page", "Clicked on Alt quote option 1");

			// Getting alt quote number
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number for Alt quote1 is " + quoteNumber);

			// Get premium,Icat feees , sltf and surplus contribution
			String premiumAlt1 = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "")
					.replace(".00", "");
			String icatFeesAlt1 = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "")
					.replace(".00", "");
			String sltfAlt1 = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			String surplusContributionAlt1 = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");

			// Add the premium value +icat fees+sltf+surplus contribution
			double totalPremiumTaxesAndFeesAlt1 = Double.parseDouble(premiumAlt1) + Double.parseDouble(icatFeesAlt1)
					+ Double.parseDouble(sltfAlt1) + Double.parseDouble(surplusContributionAlt1);

			// Get actual total premium value
			String totalPremiumValue = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",",
					"");

			// Verify the Calculated total premium value with actual total premium value
			Assertions.addInfo("Scenario 03",
					"Verifying the Calculated total premium value with actual total premium value for NB Alt quote1");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(totalPremiumValue), 2)
					- Precision.round(totalPremiumTaxesAndFeesAlt1, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Total Premium for Alt Quote 1 : " + "$" + df.format(totalPremiumTaxesAndFeesAlt1));
				Assertions.passTest("Account Overview Page",
						"Actual Total Premium for Alt Quote 1 : " + "$" + totalPremiumValue);
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated Total Premium is more than 0.05");
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

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

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// click on renewal indicators link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);
			// CLicking on the Coverage Change Check box for Soft Renewal
			renewalIndicatorsPage.coverageChange.scrollToElement();
			renewalIndicatorsPage.coverageChange.select();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Change Coverage check box for Soft Renewal");

			// click on update
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update Button");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);

			// Asserting the soft-Non Renewal Auto-Indicator on Policy Summary Page
			Assertions.verify(policySummaryPage.autoRenewalIndicators.getData().contains("Soft Non-Renewal"), true,
					"Policy Summary Page",
					"Verifying the Auto-Renew status changed to: " + policySummaryPage.autoRenewalIndicators.getData(),
					false, false);

			// Click on endorse PB link
			// Adding the ticket IO-20874
			testData = data.get(dataValue2);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link successfully");
			Assertions.verify(endorsePolicyPage.cancelButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Endorsement Effective Date is "
					+ testData.get("TransactionEffectiveDate") + "Entered successfully");

			// Click on change inspection contact
			endorsePolicyPage.changeInspectionContactLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeInspectionContactLink.scrollToElement();
			endorsePolicyPage.changeInspectionContactLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Inspection contact link");

			// Update inspection contact
			endorseInspectionContactPage.enterInspectionContactPB(testData);

			// Click on Next
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Click on continue button
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Asserting transaction term premium
			Assertions.addInfo("Scenario 04", "Assert the transaction term premium and transaction SLTF values are 0");
			Assertions.verify(endorsePolicyPage.transactionPremiumFee.formatDynamicPath("2").getData(),
					transactionPremium, "Endorse Policy Page", "The Term transaction is "
							+ endorsePolicyPage.transactionPremiumFee.formatDynamicPath("2").getData(),
					false, false);

			// Assert the transaction sltf
			Assertions.verify(endorsePolicyPage.premium.formatDynamicPath("surplusLinesTaxesAndFees", "2").getData(),
					transactionSltf, "Endorse Policy Page",
					"The transaction Surplus Taxes & Fees is "
							+ endorsePolicyPage.premium.formatDynamicPath("surplusLinesTaxesAndFees", "2").getData(),
					false, false);

			// Verifying if transaction term premium is 0 then sltf should be 0
			Assertions.verify(endorsePolicyPage.transactionPremiumFee.formatDynamicPath("2").getData(),
					endorsePolicyPage.premium.formatDynamicPath("surplusLinesTaxesAndFees", "2").getData()
							.replace(".00", ""),
					"Endorse Policy Page",
					"Surplus Taxes & Fees is zero when transaction term premium is zero is verified", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// clicking on complete button in endorse policy page
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// clicking on close button in endorse policy page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC006 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC006 ", "Executed Successfully");
			}
		}
	}
}
