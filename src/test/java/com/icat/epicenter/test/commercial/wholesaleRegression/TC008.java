/** Program Description: Create a commercial Policy with prior loss
 *  Author			   : Abha
 *  Date of Creation   : 11/25/2019
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC008 extends AbstractCommercialTest {

	public TC008() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID008.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		GLInformationPage gLInfoPage = new GLInformationPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		Map<String, String> testData1 = data.get(data_Value2);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zip code in Eligibility page
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

			// Entering GL Information
			gLInfoPage.enterGLInformation(testData);

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

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Clicking on Details link to view the Bound Quote details
			policySummaryPage.quoteNoLink.formatDynamicPath(quoteNumber).waitTillPresenceOfElement(60);
			policySummaryPage.quoteNoLink.formatDynamicPath(quoteNumber).waitTillVisibilityOfElement(60);
			policySummaryPage.quoteNoLink.formatDynamicPath(quoteNumber).waitTillElementisEnabled(60);
			policySummaryPage.quoteNoLink.formatDynamicPath(quoteNumber).waitTillButtonIsClickable(60);
			policySummaryPage.quoteNoLink.formatDynamicPath(quoteNumber).scrollToElement();
			policySummaryPage.quoteNoLink.formatDynamicPath(quoteNumber).click();
			Assertions.passTest("Quote Details Page", "Quote Details Page Loaded successfully");

			// Verifying quote details on account overview page
			Assertions.addInfo("Quote Details Page", "Verifying the Prior loss details on quote document");
			quoteDetailsPage.priorLossInfo.formatDynamicPath("Loss Type").waitTillVisibilityOfElement(60);
			Assertions.verify(
					quoteDetailsPage.priorLossInfo.formatDynamicPath("Loss Type").getData()
							.contains(testData.get("PriorLossType1")),
					true, "Quote Details Page",
					quoteDetailsPage.priorLossInfo.formatDynamicPath("Loss Type").getData() + " is verified", false,
					false);
			Assertions.verify(
					quoteDetailsPage.priorLossInfo.formatDynamicPath("Loss Date").getData()
							.contains(testData.get("PriorLossDate1")),
					true, "Quote Details Page",
					quoteDetailsPage.priorLossInfo.formatDynamicPath("Loss Date").getData() + " is verified", false,
					false);
			Assertions.verify(
					quoteDetailsPage.priorLossInfo.formatDynamicPath("Loss Amount").getData()
							.contains(testData.get("PriorLossAmount1")),
					true, "Quote Details Page",
					quoteDetailsPage.priorLossInfo.formatDynamicPath("Loss Amount").getData() + " is verified", false,
					false);
			Assertions.verify(
					quoteDetailsPage.priorLossInfo.formatDynamicPath("Repairs Completed").getData()
							.contains(testData.get("IsPriorLossDamageRepaired?1")),
					true, "Quote Details Page",
					quoteDetailsPage.priorLossInfo.formatDynamicPath("Repairs Completed").getData() + " is verified",
					false, false);
			scrollToTopPage();
			waitTime(3);// need wait time to scroll to top page
			quoteDetailsPage.closeBtn.click();
			ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();

			// Asserting prior loss values in policy snapshot page
			policySummaryPage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("View Policy Snapshot Page", "Clicked on ViewPolicySnapshot link");
			viewPolicySnapShot.priorLossData.formatDynamicPath("Loss Type").waitTillVisibilityOfElement(60);
			Assertions.passTest("View Policy Snapshot Page", "Policy Snapshot Page loaded successfully");
			Assertions.addInfo("View Policy Snapshot Page", "Asserting prior loss values in policy snapshot page");
			Assertions.verify(
					viewPolicySnapShot.priorLossData
							.formatDynamicPath("Loss Type").getData().contains(testData.get("PriorLossType1")),
					true, "View Policy Snapshot Page",
					viewPolicySnapShot.priorLossDetail.formatDynamicPath("Loss Type").getData() + " "
							+ viewPolicySnapShot.priorLossData.formatDynamicPath("Loss Type").getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					viewPolicySnapShot.priorLossData
							.formatDynamicPath("Loss Date").getData().contains(testData.get("PriorLossDate1")),
					true, "View Policy Snapshot Page",
					viewPolicySnapShot.priorLossDetail.formatDynamicPath("Loss Date").getData() + " "
							+ viewPolicySnapShot.priorLossData.formatDynamicPath("Loss Date").getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					viewPolicySnapShot.priorLossData
							.formatDynamicPath("Loss Amount").getData().contains(testData.get("PriorLossAmount1")),
					true, "View Policy Snapshot Page",
					viewPolicySnapShot.priorLossDetail.formatDynamicPath("Loss Amount").getData() + " "
							+ viewPolicySnapShot.priorLossData.formatDynamicPath("Loss Amount").getData()
							+ " is verified",
					false, false);
			Assertions.verify(
					viewPolicySnapShot.priorLossData.formatDynamicPath("Repairs Completed").getData()
							.contains(testData.get("IsPriorLossDamageRepaired?1")),
					true, "View Policy Snapshot Page",
					viewPolicySnapShot.priorLossDetail.formatDynamicPath("Repairs Completed").getData() + " "
							+ viewPolicySnapShot.priorLossData.formatDynamicPath("Repairs Completed").getData()
							+ " is verified",
					false, false);

			// Updating code base for IO-19532 - ENDT is showing NA for values
			// Adding another building for existing location
			viewPolicySnapShot.goBackButton.click();

			// ENDT PB
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to Assert the values");
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link successfully");
			Assertions.verify(endorsePolicyPage.cancelButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.appendData(testData1.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Endorsement Effective Date is "
					+ testData1.get("TransactionEffectiveDate") + "Entered successfully");

			// click on edit Loc or Building link
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page",
					"Clicked on Edit Location/Building Information link successfully");

			// Edit L1B1 - values and Add new Building
			// Edit Building Details
			buildingPage.enterEndorsementBuildingDetailsNew(testData1);
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();
			createQuotePage.enterQuoteDetailsCommercialNew(testData1);

			// Click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

		// Validating Endorsement Information table
		Assertions.addInfo("Endorsment Information Page",
				"Verifying Endorsement Information table to check the values");
		Assertions.passTest("Endorsment Information Page", "Total Transaction data");
		String totalTransactionData = validateTotalEndorsmentPremium(2, 2, 2, 2, 2);

		Assertions.verify(endorsePolicyPage.totalTerm.formatDynamicPath(2).getData().replace("$", "").replace(",", ""),
				totalTransactionData.replace(".0", ""), "Endorsement Policy Information Page",
				"Total Transaction Data is Verified and displayed as : "
						+ endorsePolicyPage.totalTerm.formatDynamicPath(2).getData(),
				false, false);
		Assertions.passTest("Endorsment Information Page", "Total Annual data");
		String totalAnnualData = validateTotalEndorsmentPremium(3, 3, 3, 3, 3);
		double d_totalAnnualData = Double.parseDouble(totalAnnualData);
		String actualAnnualData = endorsePolicyPage.totalTerm.formatDynamicPath(3).getData().replace("$", "")
				.replace(",", "");
		double d_actualAnnualData = Double.parseDouble(actualAnnualData);

			if (Precision.round(
					Math.abs(Precision.round(d_actualAnnualData, 2) - Precision.round(d_totalAnnualData, 2)),
					2) < 0.05) {
				Assertions.passTest("Endorsement Policy Information Page",
						"Total Annual Transaction Data is Verified and displayed as " + d_actualAnnualData);
			} else {
				Assertions.verify(d_actualAnnualData, d_totalAnnualData, "Endorsement Policy Information Page",
						"Actual and Calculated Total Annual Transaction Data are not matching", false, false);
			}

			Assertions.passTest("Endorsment Information Page", "Term Total data");
			String termTotalData = validateTotalEndorsmentPremium(4, 4, 4, 4, 4);
			Assertions.verify(
					endorsePolicyPage.totalTerm.formatDynamicPath(4).getData().replace("$", "").replace(",", ""),
					termTotalData, "Endorsement Policy Information Page",
					"Term Total Transaction Data is Verified and displayed as : "
							+ endorsePolicyPage.totalTerm.formatDynamicPath(4).getData(),
					false, false);

			// Click on Complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Sign out and close browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 08", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 08", "Executed Successfully");
			}
		}
	}

	public String validateTotalEndorsmentPremium(int transValue, int inspValue, int policyValue, int otherValue,
			int surplusContributionValue) {
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		endorsePolicyPage.inspectionFee.formatDynamicPath(2).waitTillVisibilityOfElement(60);
		String transactionPremiumValue = endorsePolicyPage.transactionPremiumFee.formatDynamicPath(transValue).getData()
				.replace("$", "").replace(",", "");
		double transactionPremiumFeeData = Double.valueOf(transactionPremiumValue);
		String inspectionFeeValue = endorsePolicyPage.inspectionFee.formatDynamicPath(inspValue).getData()
				.replace("$", "").replace(",", "");
		double inspectionFeeData = Double.valueOf(inspectionFeeValue);
		String policyFeeValue = endorsePolicyPage.policyFee.formatDynamicPath(policyValue).getData().replace("$", "")
				.replace(",", "");
		double policyFeeData = Double.valueOf(policyFeeValue);
		String otherFeesValue = endorsePolicyPage.otherFees.formatDynamicPath(otherValue).getData().replace("$", "")
				.replace(",", "");
		double otherFeesData = Double.valueOf(otherFeesValue);
		String surplusContributionvalue = endorsePolicyPage.surplusContributionValue
				.formatDynamicPath(surplusContributionValue).getData().replace("$", "").replace(",", "");
		double surplusContributionvalueData = Double.valueOf(surplusContributionvalue);
		Assertions.verify(
				!endorsePolicyPage.transactionPremiumFee
						.formatDynamicPath(transValue).getData().equalsIgnoreCase("N/A"),
				true, "Endorsement Policy Information Page",
				"Transaction Premium is displayed as : "
						+ endorsePolicyPage.transactionPremiumFee.formatDynamicPath(transValue).getData(),
				false, false);
		Assertions.verify(
				!endorsePolicyPage.inspectionFee.formatDynamicPath(inspValue).getData().equalsIgnoreCase("N/A"), true,
				"Endorsement Policy Information Page", "Inspection Fees is displayed as : "
						+ endorsePolicyPage.inspectionFee.formatDynamicPath(inspValue).getData(),
				false, false);
		Assertions.verify(!endorsePolicyPage.policyFee.formatDynamicPath(policyValue).getData().equalsIgnoreCase("N/A"),
				true, "Endorsement Policy Information Page",
				"Policy Fee is displayed as : " + endorsePolicyPage.policyFee.formatDynamicPath(policyValue).getData(),
				false, false);
		Assertions.verify(!endorsePolicyPage.otherFees.formatDynamicPath(otherValue).getData().equalsIgnoreCase("N/A"),
				true, "Endorsement Policy Information Page",
				"Other Fees is displayed as : " + endorsePolicyPage.otherFees.formatDynamicPath(otherValue).getData(),
				false, false);
		Assertions.verify(
				!endorsePolicyPage.surplusContributionValue.formatDynamicPath(surplusContributionValue).getData()
						.equalsIgnoreCase("N/A"),
				true, "Endorsement Policy Information Page",
				"Surplus Contribution value is displayed as : " + endorsePolicyPage.surplusContributionValue
						.formatDynamicPath(surplusContributionValue).getData(),
				false, false);

		double totalPremium = (transactionPremiumFeeData + inspectionFeeData + policyFeeData + otherFeesData
				+ surplusContributionvalueData);
		String totalPremiumData = String.valueOf(totalPremium);
		return totalPremiumData;

	}
}