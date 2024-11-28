/** Program Description: Check the taxes and fees are re-calculated when the Inspection fees and Policy fees are overridden for FL
 *  Author			   : John
 *  Date of Creation   : 20/07/2021
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
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC027 extends AbstractCommercialTest {

	public TC027() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID027.xls";
	}

	// Initializing the variables globally
	double stampingFeeCalculatedEarned;
	double sltfCalculatedEarned;
	double totalPremiumCalculatedEarned;
	double stampingFeeCalculatedReturned;
	double sltfCalculatedReturned;
	double totalPremiumCalculatedReturned;
	String quoteNumber;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		Map<String, String> testData;
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		testData = data.get(0);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
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
			if (!testData.get("Peril").equals("Quake")) {
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
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Create Quote Page loaded successfully", false, false);
				createQuotePage.enterQuoteDetailsCommercialNew(testData);
			} else {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Asserting quote number
			quoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Quote Number for state " + testData.get("InsuredState") + " is "
							+ accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", ""),
					false, false);

			// Clicked on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// asserting commission rate
			Assertions.addInfo("Request Bind Page",
					"Asserting Commission Rate for state " + testData.get("QuoteState"));
			Assertions.verify(
					requestBindPage.commissionRate.getData(), "12.0%", "Request Bind Page", "Commission Rate for state "
							+ testData.get("QuoteState") + " is " + requestBindPage.commissionRate.getData(),
					false, false);

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");
			if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
					&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {

				// Go to HomePage
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Quote is searched successfully");

				// Click on Open Referral Link
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);

				// Click on approve button
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
						"Request Approval page loaded successfully", false, false);

				// Choose file
				if (requestBindPage.chooseFile.checkIfElementIsPresent()
						&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
					if (!testData.get("FileNameToUpload").equals("")) {
						policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
					}
				}
				requestBindPage.approveRequest();
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			}

			// getting policy number
			if (policySummaryPage.policyNumber.checkIfElementIsPresent()
					&& policySummaryPage.policyNumber.checkIfElementIsDisplayed()) {
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
						"Policy Summary Page", "Policy Number is " + policySummaryPage.policyNumber.getData(), false,
						false);
			}

			// Click on cancel policy
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

			// Click on cancel arrow
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.waitTime(2);// wait time is need to load the element

			// Select cancellation reason
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));

			// Click on next
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();
			for (int k = 1; k <= 3; k++) {
				if (k == 1 && cancelPolicyPage.cancelOption.checkIfElementIsPresent()
						&& cancelPolicyPage.cancelOption.checkIfElementIsDisplayed()) {

					// Asserting Pro Rated Min Earned SLTF value on cancellation page
					Assertions.addInfo("Cancel Policy Page",
							"SLTF Calculation for Pro Rated Min Earned for state " + testData.get("QuoteState"));
					cancelPolicyPage.cancelOption.scrollToElement();
					cancelPolicyPage.cancelOption.click();
					cancelPolicyPage.waitTime(2);// wait time is need to load the page
				} else if (k == 2 && cancelPolicyPage.proRatedRadioBtn.checkIfElementIsPresent()
						&& cancelPolicyPage.proRatedRadioBtn.checkIfElementIsDisplayed()) {

					// Asserting Pro Rated SLTF value on cancellation page
					Assertions.addInfo("Cancel Policy Page",
							"SLTF Calculation for Pro Rated for state " + testData.get("QuoteState"));
					cancelPolicyPage.proRatedRadioBtn.scrollToElement();
					cancelPolicyPage.proRatedRadioBtn.click();
					cancelPolicyPage.waitTime(2);// wait time is need to load the page
				} else if (k == 3 && cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsPresent()
						&& cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsDisplayed()) {

					// Asserting Short Rated SLTF value on cancellation page
					Assertions.addInfo("Cancel Policy Page",
							"SLTF Calculation for Short Rated for state " + testData.get("QuoteState"));
					cancelPolicyPage.shortRatedRadioBtn.scrollToElement();
					cancelPolicyPage.shortRatedRadioBtn.click();
					cancelPolicyPage.waitTime(2);// wait time is need to load the page
				}

				// Update Inspection
				cancelPolicyPage.inspectionFeeEarned.clearData();
				cancelPolicyPage.inspectionFeeEarned.setData(testData.get("TransactionInspectionFee"));
				cancelPolicyPage.inspectionFeeEarned.tab();
				cancelPolicyPage.waitTime(2);// wait time is need to load the element

				// Update Policy Fee
				cancelPolicyPage.policyFeeEarned.clearData();
				cancelPolicyPage.policyFeeEarned.setData(testData.get("TransactionPolicyfee"));
				cancelPolicyPage.policyFeeEarned.tab();
				cancelPolicyPage.waitTime(2);// wait time is need to load the element

				// get earned values
				double premiumEarned = Double.parseDouble(
						cancelPolicyPage.premiumValue.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double inspectionFeeEarned = Double.parseDouble(cancelPolicyPage.inspectionFeeEarned.getData());
				double policyFeeEarned = Double.parseDouble(cancelPolicyPage.policyFeeEarned.getData());
				double sltfActualEarned = Double.parseDouble(
						cancelPolicyPage.taxesAndFees.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double totalPremiumActualEarned = Double.parseDouble(
						cancelPolicyPage.policyTotal.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
				double surplusContributionValue = Double.parseDouble(cancelPolicyPage.surplusContributionVlaue
						.formatDynamicPath("3").getData().replace("$", "").replace("-", ""));

				// calculating earned values
				if (testData.get("QuoteState").contains("FL") && testData.get("Peril").contains("AOP")) {
					double flsoServiceFeeCalculatedEarned = (premiumEarned + inspectionFeeEarned + policyFeeEarned
							+ surplusContributionValue)
							* (Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100);
					sltfCalculatedEarned = ((premiumEarned + inspectionFeeEarned + policyFeeEarned
							+ surplusContributionValue) * (Double.parseDouble(testData.get("SLTFValue")) / 100))
							+ flsoServiceFeeCalculatedEarned + 4;
					totalPremiumCalculatedEarned = premiumEarned + inspectionFeeEarned + policyFeeEarned
							+ sltfActualEarned + surplusContributionValue;
				} else if (testData.get("QuoteState").contains("FL") && testData.get("Peril").contains("Wind")) {
					double flsoServiceFeeCalculatedEarned = (premiumEarned + inspectionFeeEarned + policyFeeEarned
							+ surplusContributionValue)
							* (Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100);
					sltfCalculatedEarned = ((premiumEarned + inspectionFeeEarned + policyFeeEarned
							+ surplusContributionValue) * (Double.parseDouble(testData.get("SLTFValue")) / 100))
							+ flsoServiceFeeCalculatedEarned;
					totalPremiumCalculatedEarned = premiumEarned + inspectionFeeEarned + policyFeeEarned
							+ sltfActualEarned + surplusContributionValue;
				}

				// asserting sltf values earned
				Assertions.addInfo("Cancel Policy Page",
						"Asserting Earned SLTF value after updating Inspection/Policy Fee for state "
								+ testData.get("QuoteState"));
				if (Precision.round(
						Math.abs(Precision.round(sltfActualEarned, 2) - Precision.round(sltfCalculatedEarned, 2)),
						2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Earned SLTF Value is calculated correctly after updating Inspection/Policy Fee for state "
									+ testData.get("QuoteState") + ". Value is $" + sltfActualEarned);
				} else {
					Assertions.verify(sltfActualEarned, sltfCalculatedEarned, "Cancel Policy Page",
							"Actual and Calculated values are not matching after updating Inspection/Policy Fee for state "
									+ testData.get("QuoteState"),
							false, false);
				}
				Assertions.addInfo("Cancel Policy Page",
						"Asserting Earned Premium Total value after updating Inspection/Policy Fee for state "
								+ testData.get("QuoteState"));
				if (Precision.round(Math.abs(Precision.round(totalPremiumActualEarned, 2)
						- Precision.round(totalPremiumCalculatedEarned, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Earned Premium Total Value is calculated correctly after updating Inspection/Policy Fee for state "
									+ testData.get("QuoteState") + ". Value is $" + totalPremiumActualEarned);
				} else {
					Assertions.verify(totalPremiumActualEarned, totalPremiumCalculatedEarned, "Cancel Policy Page",
							"Actual and Calculated values are not matching after updating Inspection/Policy Fee for state "
									+ testData.get("QuoteState"),
							false, false);
				}

				// get returned values
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

				// calculating returned values
				if (testData.get("QuoteState").contains("FL") && testData.get("Peril").contains("AOP")) {
					double flsoServiceFeeCalculatedReturned = (premiumReturned + inspectionFeeReturned
							+ policyFeeReturned + surplusContributionReturned)
							* (Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100);
					sltfCalculatedReturned = ((premiumReturned + inspectionFeeReturned + policyFeeReturned
							+ surplusContributionReturned) * (Double.parseDouble(testData.get("SLTFValue")) / 100))
							+ flsoServiceFeeCalculatedReturned;
					totalPremiumCalculatedReturned = premiumReturned + inspectionFeeReturned + policyFeeReturned
							+ sltfActualReturned + surplusContributionReturned;
				} else if (testData.get("QuoteState").contains("FL") && testData.get("Peril").contains("Wind")) {
					double flsoServiceFeeCalculatedReturned = (premiumReturned + inspectionFeeReturned
							+ policyFeeReturned + surplusContributionReturned)
							* (Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100);
					sltfCalculatedReturned = ((premiumReturned + inspectionFeeReturned + policyFeeReturned
							+ surplusContributionReturned) * (Double.parseDouble(testData.get("SLTFValue")) / 100))
							+ flsoServiceFeeCalculatedReturned;
					totalPremiumCalculatedReturned = premiumReturned + inspectionFeeReturned + policyFeeReturned
							+ sltfActualReturned + surplusContributionReturned;
				}

				// asserting sltf values returned
				Assertions.addInfo("Cancel Policy Page",
						"Asserting Returned SLTF value after updating Inspection/Policy Fee for state "
								+ testData.get("QuoteState"));
				if (Precision.round(
						Math.abs(Precision.round(sltfActualReturned, 2) - Precision.round(sltfCalculatedReturned, 2)),
						2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Returned SLTF Value is calculated correctly after updating Inspection/Policy Fee for state "
									+ testData.get("QuoteState") + ". Value is $" + sltfActualReturned);
				} else {
					Assertions.verify(sltfActualReturned, sltfCalculatedReturned, "Cancel Policy Page",
							"Actual and Calculated values are not matching after updating Inspection/Policy Fee for state "
									+ testData.get("QuoteState"),
							false, false);
				}
				Assertions.addInfo("Cancel Policy Page",
						"Asserting Returned Premium Total value after updating Inspection/Policy Fee for state "
								+ testData.get("QuoteState"));
				if (Precision.round(Math.abs(Precision.round(totalPremiumActualReturned, 2)
						- Precision.round(totalPremiumCalculatedReturned, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Returned Premium Total Value is calculated correctly after updating Inspection/Policy Fee for state "
									+ testData.get("QuoteState") + ". Value is $" + totalPremiumActualReturned);
				} else {
					Assertions.verify(totalPremiumActualReturned, totalPremiumCalculatedReturned,
							"View/Print Full Quote Page",
							"Actual and Calculated values are not matching after updating Inspection/Policy Fee for state "
									+ testData.get("QuoteState"),
							false, false);
				}
			}

			// click on homepage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC027 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC027 ", "Executed Successfully");
			}
		}
	}
}
