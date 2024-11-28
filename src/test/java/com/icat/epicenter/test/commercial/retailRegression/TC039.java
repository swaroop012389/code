/** Program Description: 1. Verify the taxes and fees are calculated correctly for all Pro-Rated, Pro-Rated Minimum Earned and Short Rated.
 *                       2. Modify Inspection Fee and Policy fee and verify the taxes and fees are calculated correctly for all Pro-Rated, Pro-Rated Minimum Earned  and Short Rated.
 *  Author			   : Priyanka S
 *  Date of Creation   : 03/02/2022
 **/

package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CancelPolicyPage;
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

public class TC039 extends AbstractCommercialTest {

	public TC039() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID039.xls";
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
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		// Initializing variables
		String quoteNumber;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		String policyNumber;
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

			// click on Request bind
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

			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on Renewal Link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc Info Page", "Entered Expacc deatils successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				Assertions.addInfo("Policy Summary Page", "Renewing the Policy");
				policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew policy link");
			}

			// Click on Continue button
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

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Renewal created and released to producer");

			// Getting renewal quote number
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Renewal quote number is " + renewalQuoteNumber);

			// Click on request bind
			accountOverviewPage.clickOnRequestBind(testData, renewalQuoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			requestBindPage.renewalRequestBind(testData);

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(renewalQuoteNumber);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Home Page", "Searched Submitted quote " + quoteNumber + " successfullly");

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Renewal Policy Number is : " + policyNumber);

			// Click on Cancel Policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

			// entering details in cancellation page
			Assertions.verify(cancelPolicyPage.daysBeforeNOC.checkIfElementIsDisplayed(), true, "Cancel policy Page",
					"Cancel policy page loaded successfully", false, false);
			// cancelPolicyPage.enterCancellationDetails(testData);
			if (!testData.get("Cancellation_DaysBeforeNOC").equals("")) {
				cancelPolicyPage.daysBeforeNOC.scrollToElement();
				cancelPolicyPage.daysBeforeNOC.setData(testData.get("Cancellation_DaysBeforeNOC"));
			}
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());
			if (!testData.get("CancellationEffectiveDate").equals("")) {
				cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
				cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
				cancelPolicyPage.cancellationEffectiveDate.tab();
			}
			if (!testData.get("Cancellation_LegalNoticeWording").equals("")) {
				cancelPolicyPage.legalNoticeWording.scrollToElement();
				cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			}
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();
			Assertions.passTest("Cancel Policy page", "Cancel policy page details entered successfullly");

			// calculate values for all Pro-Rated, Pro-Rated Minimum Earned and
			// Short Rated.
			Assertions.addInfo("Scenario 1",
					"Verify Taxes & Fees , Total permium for Pro-Rated, Pro-Rated Minimum Earned and Short Rated");
			for (int k = 1; k <= 3; k++) {
				if (k == 1 && cancelPolicyPage.cancelOption.checkIfElementIsPresent()
						&& cancelPolicyPage.cancelOption.checkIfElementIsDisplayed()) {

					// Asserting Pro Rated Min Earned SLTF value on cancellation
					// page
					Assertions.addInfo("Cancel Policy Page",
							"<strong>" + "SLTF Calculation for Pro Rated Min Earned for state "
									+ testData.get("QuoteState") + "</strong>");
					cancelPolicyPage.cancelOption.scrollToElement();
					cancelPolicyPage.cancelOption.click();
					cancelPolicyPage.waitTime(2);
				} else if (k == 2 && cancelPolicyPage.proRatedRadioBtn.checkIfElementIsPresent()
						&& cancelPolicyPage.proRatedRadioBtn.checkIfElementIsDisplayed()) {

					// Asserting Pro Rated SLTF value on cancellation page
					Assertions.addInfo("Cancel Policy Page", "<strong>" + "SLTF Calculation for Pro Rated for state "
							+ testData.get("QuoteState") + "</strong>");
					cancelPolicyPage.proRatedRadioBtn.scrollToElement();
					cancelPolicyPage.proRatedRadioBtn.click();
					cancelPolicyPage.waitTime(2);
				} else if (k == 3 && cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsPresent()
						&& cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsDisplayed()) {

					// Asserting Short Rated SLTF value on cancellation page
					Assertions.addInfo("Cancel Policy Page", "<strong>" + "SLTF Calculation for Short Rated for state "
							+ testData.get("QuoteState") + "</strong>");
					cancelPolicyPage.shortRatedRadioBtn.scrollToElement();
					cancelPolicyPage.shortRatedRadioBtn.click();
					cancelPolicyPage.waitTime(2);
				}
				// claculate taxes and fees are calculated correctly for all
				// Pro-Rated,Policy Total

				// get original values
				double premiumOriginal = Double.parseDouble(
						cancelPolicyPage.premiumValue.formatDynamicPath(2).getData().replace("$", "").replace("-", ""));
				double inspectionFeeOriginal = Double.parseDouble(cancelPolicyPage.inspectionFee.formatDynamicPath(2)
						.getData().replace("$", "").replace("-", ""));
				double policyFeeOriginal = Double.parseDouble(
						cancelPolicyPage.policyFee.formatDynamicPath(2).getData().replace("$", "").replace("-", ""));
				double sltfActualOriginal = Double.parseDouble(
						cancelPolicyPage.taxesAndFees.formatDynamicPath(2).getData().replace("$", "").replace("-", ""));
				double totalPremiumActualOriginal = Double.parseDouble(
						cancelPolicyPage.policyTotal.formatDynamicPath(2).getData().replace("$", "").replace("-", ""));
				double surplusContributionValueOriginal = Double.parseDouble(cancelPolicyPage.surplusContributionVlaue
						.formatDynamicPath(2).getData().replace("$", "").replace("-", ""));
				double sltfCalculatedOriginal = (premiumOriginal + inspectionFeeOriginal + policyFeeOriginal
						+ surplusContributionValueOriginal) * 0.06;
				double totalPremiumCalculatedOriginal = premiumOriginal + inspectionFeeOriginal + policyFeeOriginal
						+ sltfActualOriginal + surplusContributionValueOriginal;

				// asserting sltf and Total premium values Original
				Assertions.addInfo("Cancel Policy Page",
						"Asserting Original SLTF value for state " + testData.get("QuoteState"));
				if (Precision.round(
						Math.abs(Precision.round(sltfActualOriginal, 2) - Precision.round(sltfCalculatedOriginal, 2)),
						2) < 1.00) {
					Assertions.passTest("Cancel Policy Page",
							"Original SLTF Value is calculated and " + "Value is $" + sltfActualOriginal);
				} else {
					Assertions.verify(sltfActualOriginal, sltfCalculatedOriginal, "Cancel Policy Page",
							"Original - Actual and Calculated values for " + testData.get("QuoteState")
									+ " are not matching",
							false, false);
				}
				Assertions.addInfo("Cancel Policy Page",
						"Asserting Original Premium Total value for state " + testData.get("QuoteState"));
				if (Precision.round(Math.abs(Precision.round(totalPremiumActualOriginal, 2)
						- Precision.round(totalPremiumCalculatedOriginal, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Earned Premium Total Value is calculated correctly for state " + "and Value is $"
									+ totalPremiumActualOriginal);
				} else {
					Assertions.verify(totalPremiumActualOriginal, totalPremiumCalculatedOriginal, "Cancel Policy Page",
							"Original - Actual and Calculated for " + testData.get("QuoteState")
									+ " values are not matching",
							false, false);
				}

				// get Earned values
				double premiumEarned = Double.parseDouble(
						cancelPolicyPage.premiumValue.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double inspectionFeeEarned = Double.parseDouble(cancelPolicyPage.inspectionFeeEarned
						.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double policyFeeEarned = Double.parseDouble(cancelPolicyPage.policyFeeEarned.formatDynamicPath(3)
						.getData().replace("$", "").replace("-", "").replace(",", ""));
				double sltfActualEarned = Double.parseDouble(
						cancelPolicyPage.taxesAndFees.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double totalPremiumActualEarned = Double.parseDouble(
						cancelPolicyPage.policyTotal.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double surplusContributionValueEarned = Double.parseDouble(cancelPolicyPage.surplusContributionVlaue
						.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double sltfCalculatedEarned = (premiumEarned + inspectionFeeEarned + policyFeeEarned
						+ surplusContributionValueEarned) * 0.06;
				double totalPremiumCalculatedEarned = premiumEarned + inspectionFeeEarned + policyFeeEarned
						+ sltfActualEarned + surplusContributionValueEarned;

				// asserting sltf and Total premium values earned
				Assertions.addInfo("Cancel Policy Page", "Asserting Earned SLTF value ");
				if (Precision.round(
						Math.abs(Precision.round(sltfActualEarned, 2) - Precision.round(sltfCalculatedEarned, 2)),
						2) < 1.00) {
					Assertions.passTest("Cancel Policy Page", "Earned SLTF Value " + testData.get("QuoteState")
							+ "is calculated and " + "Value is $" + sltfActualEarned);
				} else {
					Assertions.verify(sltfActualEarned, sltfCalculatedEarned, "Cancel Policy Page",
							"Earned - Actual and Calculated values for " + testData.get("QuoteState")
									+ " are not matching",
							false, false);
				}
				Assertions.addInfo("Cancel Policy Page",
						"Asserting Earned Premium Total value for state " + testData.get("QuoteState"));
				if (Precision.round(Math.abs(Precision.round(totalPremiumActualEarned, 2)
						- Precision.round(totalPremiumCalculatedEarned, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Earned Premium Total Value is calculated correctly for state " + testData.get("QuoteState")
									+ "and Value is $" + totalPremiumActualEarned);
				} else {
					Assertions.verify(totalPremiumActualEarned, totalPremiumCalculatedEarned, "Cancel Policy Page",
							"Earned - Actual and Calculated values for " + testData.get("QuoteState")
									+ " are not matching ",
							false, false);
				}

				// get Returned values
				double premiumReturned = Double.parseDouble(
						cancelPolicyPage.premiumValue.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));
				double inspectionFeeReturned = Double.parseDouble(cancelPolicyPage.inspectionFee.formatDynamicPath(4)
						.getData().replace("$", "").replace("-", ""));
				double policyFeeReturned = Double.parseDouble(
						cancelPolicyPage.policyFee.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));
				double sltfActualReturned = Double.parseDouble(
						cancelPolicyPage.taxesAndFees.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));
				double totalPremiumActualReturned = Double.parseDouble(
						cancelPolicyPage.policyTotal.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));
				double surplusContributionValueReturned = Double.parseDouble(cancelPolicyPage.surplusContributionVlaue
						.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));
				double sltfCalculatedReturned = (premiumReturned + inspectionFeeReturned + policyFeeReturned
						+ surplusContributionValueReturned) * 0.06;
				double totalPremiumCalculatedReturned = premiumReturned + inspectionFeeReturned + policyFeeReturned
						+ sltfActualReturned + surplusContributionValueReturned;

				// asserting sltf and Total premium values earned
				Assertions.addInfo("Cancel Policy Page", "Asserting Returned SLTF value ");
				if (Precision.round(
						Math.abs(Precision.round(sltfActualReturned, 2) - Precision.round(sltfCalculatedReturned, 2)),
						2) < 1.00) {
					Assertions.passTest("Cancel Policy Page", "Returned SLTF Value " + testData.get("QuoteState")
							+ "is calculated and " + "Value is $" + sltfActualReturned);
				} else {
					Assertions.verify(sltfActualReturned, sltfCalculatedReturned, "Cancel Policy Page",
							"Returned - Actual and Calculated values for " + testData.get("QuoteState")
									+ " are not matching",
							false, false);
				}
				Assertions.addInfo("Cancel Policy Page",
						"Asserting Earned Premium Total value for state " + testData.get("QuoteState"));
				if (Precision.round(Math.abs(Precision.round(totalPremiumActualReturned, 2)
						- Precision.round(totalPremiumCalculatedReturned, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Returned Premium Total Value is calculated correctly for state "
									+ testData.get("QuoteState") + "and Value is $" + totalPremiumActualReturned);
				} else {
					Assertions.verify(totalPremiumActualReturned, totalPremiumCalculatedReturned, "Cancel Policy Page",
							"Returned - Actual and Calculated values for " + testData.get("QuoteState")
									+ " are not matching ",
							false, false);
				}
			}
			Assertions.addInfo("Scenario 1", "Scenario 1 Ended");

			// modify Policy fee and calculate Pro-Rated,
			// Pro-Rated Minimum Earned and Short Rated.
			Assertions.passTest("Scenario 2",
					"Modify Inspection fees, policy fee and verify Taxes & Fees , Total permium for Pro-Rated, Pro-Rated Minimum Earned and Short Rated");
			for (int k = 1; k <= 3; k++) {
				if (k == 1 && cancelPolicyPage.cancelOption.checkIfElementIsPresent()
						&& cancelPolicyPage.cancelOption.checkIfElementIsDisplayed()) {

					// Asserting Pro Rated Min Earned SLTF value on cancellation
					// page
					Assertions.addInfo("Cancel Policy Page",
							"<strong>" + "SLTF Calculation for Pro Rated Min Earned for state "
									+ testData.get("QuoteState") + "</strong>");
					cancelPolicyPage.cancelOption.scrollToElement();
					cancelPolicyPage.cancelOption.click();
					cancelPolicyPage.waitTime(2);
					cancelPolicyPage.policyFeeEarned.setData("550");
					cancelPolicyPage.waitTime(2);
				} else if (k == 2 && cancelPolicyPage.proRatedRadioBtn.checkIfElementIsPresent()
						&& cancelPolicyPage.proRatedRadioBtn.checkIfElementIsDisplayed()) {

					// Asserting Pro Rated SLTF value on cancellation page
					Assertions.addInfo("Cancel Policy Page", "<strong>" + "SLTF Calculation for Pro Rated for state "
							+ testData.get("QuoteState") + "</strong>");
					cancelPolicyPage.proRatedRadioBtn.scrollToElement();
					cancelPolicyPage.proRatedRadioBtn.click();
					cancelPolicyPage.waitTime(2);
					cancelPolicyPage.waitTime(2);
				} else if (k == 3 && cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsPresent()
						&& cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsDisplayed()) {

					// Asserting Short Rated SLTF value on cancellation page
					Assertions.addInfo("Cancel Policy Page", "<strong>" + "SLTF Calculation for Short Rated for state "
							+ testData.get("QuoteState") + "</strong>");
					cancelPolicyPage.shortRatedRadioBtn.scrollToElement();
					cancelPolicyPage.shortRatedRadioBtn.click();
					cancelPolicyPage.waitTime(2);
				}
				// calculate taxes and fees are calculated correctly for all
				// Pro-Rated,Policy Total

				// get original values
				cancelPolicyPage.waitTime(3);
				double premiumOriginal = Double.parseDouble(
						cancelPolicyPage.premiumValue.formatDynamicPath(2).getData().replace("$", "").replace("-", ""));
				double inspectionFeeOriginal = Double.parseDouble(cancelPolicyPage.inspectionFee.formatDynamicPath(2)
						.getData().replace("$", "").replace("-", ""));
				double policyFeeOriginal = Double.parseDouble(
						cancelPolicyPage.policyFee.formatDynamicPath(2).getData().replace("$", "").replace("-", ""));
				double sltfActualOriginal = Double.parseDouble(
						cancelPolicyPage.taxesAndFees.formatDynamicPath(2).getData().replace("$", "").replace("-", ""));
				double totalPremiumActualOriginal = Double.parseDouble(
						cancelPolicyPage.policyTotal.formatDynamicPath(2).getData().replace("$", "").replace("-", ""));
				double surplusContributionOriginal = Double.parseDouble(cancelPolicyPage.surplusContributionVlaue
						.formatDynamicPath(2).getData().replace("$", "").replace("-", ""));
				double sltfCalculatedOriginal = (premiumOriginal + inspectionFeeOriginal + policyFeeOriginal
						+ surplusContributionOriginal) * 0.06;
				double totalPremiumCalculatedOriginal = premiumOriginal + inspectionFeeOriginal + policyFeeOriginal
						+ sltfActualOriginal + surplusContributionOriginal;

				// asserting sltf and Total premium values Original
				Assertions.addInfo("Cancel Policy Page",
						"Asserting Original SLTF value for state " + testData.get("QuoteState"));
				if (Precision.round(
						Math.abs(Precision.round(sltfActualOriginal, 2) - Precision.round(sltfCalculatedOriginal, 2)),
						2) < 1.00) {
					Assertions.passTest("Cancel Policy Page",
							"Original SLTF Value is calculated and " + "Value is $" + sltfActualOriginal);
				} else {
					Assertions.verify(sltfActualOriginal, sltfCalculatedOriginal, "Cancel Policy Page",
							"Original - Actual and Calculated values for " + testData.get("QuoteState")
									+ " are not matching",
							false, false);
				}
				Assertions.addInfo("Cancel Policy Page",
						"Asserting Original Premium Total value for state " + testData.get("QuoteState"));
				if (Precision.round(Math.abs(Precision.round(totalPremiumActualOriginal, 2)
						- Precision.round(totalPremiumCalculatedOriginal, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Earned Premium Total Value is calculated correctly for state " + "and Value is $"
									+ totalPremiumActualOriginal);
				} else {
					Assertions.verify(totalPremiumActualOriginal, totalPremiumCalculatedOriginal, "Cancel Policy Page",
							"Original - Actual and Calculated for " + testData.get("QuoteState")
									+ " values are not matching",
							false, false);
				}

				// get Earned values
				double premiumEarned = Double.parseDouble(
						cancelPolicyPage.premiumValue.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double inspectionFeeEarned = Double.parseDouble(cancelPolicyPage.inspectionFeeEarned
						.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double policyFeeEarned = Double.parseDouble(cancelPolicyPage.policyFeeEarned.formatDynamicPath(3)
						.getData().replace("$", "").replace("-", "").replace(",", ""));
				double sltfActualEarned = Double.parseDouble(
						cancelPolicyPage.taxesAndFees.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double totalPremiumActualEarned = Double.parseDouble(
						cancelPolicyPage.policyTotal.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double surplusContributionEarned = Double.parseDouble(cancelPolicyPage.surplusContributionVlaue
						.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double sltfCalculatedEarned = (premiumEarned + inspectionFeeEarned + policyFeeEarned
						+ surplusContributionEarned) * 0.06;
				double totalPremiumCalculatedEarned = premiumEarned + inspectionFeeEarned + policyFeeEarned
						+ sltfActualEarned + surplusContributionEarned;

				// asserting sltf and Total premium values earned
				Assertions.addInfo("Cancel Policy Page", "Asserting Earned SLTF value ");
				if (Precision.round(
						Math.abs(Precision.round(sltfActualEarned, 2) - Precision.round(sltfCalculatedEarned, 2)),
						2) < 1.00) {
					Assertions.passTest("Cancel Policy Page", "Earned SLTF Value " + testData.get("QuoteState")
							+ "is calculated and " + "Value is $" + sltfActualEarned);
				} else {
					Assertions.verify(sltfActualEarned, sltfCalculatedEarned, "Cancel Policy Page",
							"Earned - Actual and Calculated values for " + testData.get("QuoteState")
									+ " are not matching",
							false, false);
				}
				Assertions.addInfo("Cancel Policy Page",
						"Asserting Earned Premium Total value for state " + testData.get("QuoteState"));
				if (Precision.round(Math.abs(Precision.round(totalPremiumActualEarned, 2)
						- Precision.round(totalPremiumCalculatedEarned, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Earned Premium Total Value is calculated correctly for state " + testData.get("QuoteState")
									+ "and Value is $" + totalPremiumActualEarned);
				} else {
					Assertions.verify(totalPremiumActualEarned, totalPremiumCalculatedEarned, "Cancel Policy Page",
							"Earned - Actual and Calculated values for " + testData.get("QuoteState")
									+ " are not matching ",
							false, false);
				}

				// get Returned values
				double premiumReturned = Double.parseDouble(
						cancelPolicyPage.premiumValue.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));
				double inspectionFeeReturned = Double.parseDouble(cancelPolicyPage.inspectionFee.formatDynamicPath(4)
						.getData().replace("$", "").replace("-", ""));
				double policyFeeReturned = Double.parseDouble(
						cancelPolicyPage.policyFee.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));
				double sltfActualReturned = Double.parseDouble(
						cancelPolicyPage.taxesAndFees.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));
				double totalPremiumActualReturned = Double.parseDouble(
						cancelPolicyPage.policyTotal.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));
				double surplusContributionReturned = Double.parseDouble(cancelPolicyPage.surplusContributionVlaue
						.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));
				double sltfCalculatedReturned = (premiumReturned + inspectionFeeReturned + policyFeeReturned
						+ surplusContributionReturned) * 0.06;
				double totalPremiumCalculatedReturned = premiumReturned + inspectionFeeReturned + policyFeeReturned
						+ sltfActualReturned + surplusContributionReturned;

				// asserting sltf and Total premium values earned
				Assertions.addInfo("Cancel Policy Page", "Asserting Returned SLTF value ");
				if (Precision.round(
						Math.abs(Precision.round(sltfActualReturned, 2) - Precision.round(sltfCalculatedReturned, 2)),
						2) < 1.00) {
					Assertions.passTest("Cancel Policy Page", "Returned SLTF Value " + testData.get("QuoteState")
							+ "is calculated and " + "Value is $" + sltfActualReturned);
				} else {
					Assertions.verify(sltfActualReturned, sltfCalculatedReturned, "Cancel Policy Page",
							"Returned - Actual and Calculated values for " + testData.get("QuoteState")
									+ " are not matching",
							false, false);
				}
				Assertions.addInfo("Cancel Policy Page",
						"Asserting Earned Premium Total value for state " + testData.get("QuoteState"));
				if (Precision.round(Math.abs(Precision.round(totalPremiumActualReturned, 2)
						- Precision.round(totalPremiumCalculatedReturned, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Returned Premium Total Value is calculated correctly for state "
									+ testData.get("QuoteState") + "and Value is $" + totalPremiumActualReturned);
				} else {
					Assertions.verify(totalPremiumActualReturned, totalPremiumCalculatedReturned, "Cancel Policy Page",
							"Returned - Actual and Calculated values for " + testData.get("QuoteState")
									+ " are not matching ",
							false, false);
				}
			}
			Assertions.addInfo("Scenario 2", "Scenario 2 Ended");

			// Click on Complete Transaction
			if (!testData.get("TransactionDescription").equals("")) {
				cancelPolicyPage.underwriterComment.scrollToElement();
				cancelPolicyPage.underwriterComment.setData(testData.get("TransactionDescription"));
			}
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC039 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC039 ", "Executed Successfully");
			}
		}
	}
}
