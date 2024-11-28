/** Program Description: Validation of Surplus Lines Taxes and Fees on cancellation page for all the 14 states
 *  Author			   : John
 *  Date of Creation   : 20/07/2021
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC023 extends AbstractCommercialTest {

	public TC023() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID023.xls";
	}

	// Initializing the variables globally
	double stampingFeeCalculatedEarned;
	double sltfCalculatedEarned;
	double totalPremiumCalculatedEarned;
	double stampingFeeCalculatedReturned;
	double sltfCalculatedReturned;
	double totalPremiumCalculatedReturned;
	String testDataFilePath;
	String testDataSheetName;
	SheetMatchedAccessManager testDataSheet;
	String policyNumber, quoteNumber;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		Map<String, String> testData;
		boolean isTestPassed = false;

		try {
			// 0 = AL 1 = AZ 2=AR 3=FL_AOP 4=FL_Wind 5=LA 6=MS 7=MO 8=NJ 9=NV 10=NC 11=SC
			// 12=TN 13=TX 14=UT
			for (int i = 0; i <= 15; i++) {
				testData = data.get(i);

				// search for Quote
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Producer Home page loaded successfully", false, false);
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(testData.get("QuoteNumber"));

				// Asserting quote number
				Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
						"Account Overview Page",
						"Quote Number for state " + testData.get("InsuredState") + " is "
								+ accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", ""),
						false, false);
				quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);

				// Clicked on request bind button
				if (accountOverviewPage.requestBind.checkIfElementIsPresent()
						&& accountOverviewPage.requestBind.checkIfElementIsDisplayed()) {
					accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
					Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

					// asserting commission rate
					Assertions.addInfo("Request Bind Page",
							"Asserting Commission Rate for state " + testData.get("QuoteState"));
					Assertions.verify(requestBindPage.commissionRate.getData(), "12.0%", "Request Bind Page",
							"Commission Rate for state " + testData.get("QuoteState") + " is "
									+ requestBindPage.commissionRate.getData(),
							false, false);

					// Asserting state specific wording
					Assertions.addInfo("Request Bind Page",
							"Asserting Due Diligence Text state " + testData.get("QuoteState"));
					Assertions.verify(requestBindPage.dueDiligenceText
							.formatDynamicPath("communicated with the insured").checkIfElementIsPresent()
							&& requestBindPage.dueDiligenceText.formatDynamicPath("communicated with the insured")
									.checkIfElementIsPresent(),
							true, "Request Bind Page",
							requestBindPage.dueDiligenceText.formatDynamicPath("communicated with the insured")
									.getData() + " is displayed for state " + testData.get("QuoteState"),
							false, false);
					if (testData.get("QuoteState").contains("AL") || testData.get("QuoteState").contains("TX")
							|| testData.get("QuoteState").contains("SC") || testData.get("QuoteState").contains("NC")
							|| testData.get("QuoteState").contains("AR") || testData.get("QuoteState").contains("MO")
							|| testData.get("QuoteState").contains("TN")) {
						Assertions.verify(requestBindPage.dueDiligenceText
								.formatDynamicPath("signed diligent effort form").checkIfElementIsPresent()
								&& requestBindPage.dueDiligenceText
										.formatDynamicPath("signed diligent effort form").checkIfElementIsPresent(),
								true, "Request Bind Page",
								requestBindPage.dueDiligenceText.formatDynamicPath("signed diligent effort form")
										.getData() + " is displayed for state " + testData.get("QuoteState"),
								false, false);
					} else if (testData.get("QuoteState").contains("FL")) {
						Assertions.verify(
								requestBindPage.dueDiligenceText.formatDynamicPath("diligent effort form (if required)")
										.checkIfElementIsPresent()
										&& requestBindPage.dueDiligenceText
												.formatDynamicPath("diligent effort form (if required)")
												.checkIfElementIsPresent(),
								true, "Request Bind Page",
								requestBindPage.dueDiligenceText.formatDynamicPath("diligent effort form (if required)")
										.getData() + " is displayed for state " + testData.get("QuoteState"),
								false, false);
					} else if (testData.get("QuoteState").contains("NJ")) {
						Assertions.verify(
								requestBindPage.dueDiligenceText.formatDynamicPath("New Jersey Certification of Effort")
										.checkIfElementIsPresent()
										&& requestBindPage.dueDiligenceText
												.formatDynamicPath("New Jersey Certification of Effort")
												.checkIfElementIsPresent(),
								true, "Request Bind Page",
								requestBindPage.dueDiligenceText.formatDynamicPath("New Jersey Certification of Effort")
										.getData() + " is displayed for state " + testData.get("QuoteState"),
								false, false);
					}

					// entering details in request bind page
					if (!testData.get("QuoteState").contains("SC")) {
						Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
								"Request Bind Page loaded successfully", false, false);
						requestBindPage.enterBindDetails(testData);
						Assertions.passTest("Request Bind Page", "Bind details entered successfully");
						if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
								&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {

							// Go to HomePage
							homePage.goToHomepage.click();
							homePage.searchReferral(testData.get("QuoteNumber"));
							Assertions.passTest("Home Page", "Quote for referral is searched successfully");

							Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
									"Referral page loaded successfully", false, false);
							referralPage.clickOnApprove();

							// carrier selection
							if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
									&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
								requestBindPage.approveRequestCommercialData(testData);
							}

							Assertions.passTest("Referral Page", "Referral request approved successfully");

							if (requestBindPage.chooseFile.checkIfElementIsPresent()
									&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
								if (!testData.get("FileNameToUpload").equals("")) {
									policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
								}
							}
							if (requestBindPage.approve.checkIfElementIsPresent()
									&& requestBindPage.approve.checkIfElementIsDisplayed()) {
								Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true,
										"Request Bind Page", "Request Approval page loaded successfully", false, false);
								requestBindPage.approveRequest();
								Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
							}
						}
					} else {
						Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
								"Request Bind Page loaded successfully", false, false);
						requestBindPage.enterBindDetails(testData);
						if (requestBindPage.submit.checkIfElementIsPresent()
								&& requestBindPage.submit.checkIfElementIsDisplayed()) {
							if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
									&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
								requestBindPage.overrideEffectiveDate.scrollToElement();
								requestBindPage.overrideEffectiveDate.select();
							}
							requestBindPage.waitTime(2);// need wait time to load element
							if (policyDocumentsPage.chooseFile.checkIfElementIsPresent()
									&& policyDocumentsPage.chooseFile.checkIfElementIsDisplayed()) {
								if (!testData.get("FileNameToUpload").equals("")) {
									policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
								}
							}
							requestBindPage.submit.scrollToElement();
							requestBindPage.submit.click();
							confirmBindRequestPage.waitTime(2);// need wait time to load element
							confirmBindRequestPage.requestBind.scrollToElement();
							confirmBindRequestPage.requestBind.click();
						}

						if (requestBindPage.submit.checkIfElementIsPresent()
								&& requestBindPage.submit.checkIfElementIsDisplayed()) {
							if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
									&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
								requestBindPage.overrideEffectiveDate.scrollToElement();
								requestBindPage.overrideEffectiveDate.select();
							}
							requestBindPage.waitTime(2);// need wait time to load element
							if (policyDocumentsPage.chooseFile.checkIfElementIsPresent()
									&& policyDocumentsPage.chooseFile.checkIfElementIsDisplayed()) {
								if (!testData.get("FileNameToUpload").equals("")) {
									policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
								}
							}
							requestBindPage.submit.scrollToElement();
							requestBindPage.submit.click();
							confirmBindRequestPage.waitTime(2);// need wait time to load element
							confirmBindRequestPage.requestBind.scrollToElement();
							confirmBindRequestPage.requestBind.click();
						}
						Assertions.passTest("Request Bind Page", "Bind details entered successfully");
						if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
								&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {

							// Go to HomePage
							homePage.goToHomepage.click();
							homePage.searchReferral(testData.get("QuoteNumber"));
							Assertions.passTest("Home Page", "Quote for referral is searched successfully");

							// Approve Referral
							Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
									"Referral page loaded successfully", false, false);
							referralPage.clickOnApprove();
							Assertions.passTest("Referral Page", "Referral request approved successfully");

							Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true,
									"Request Bind Page", "Request Approval page loaded successfully", false, false);
							if (requestBindPage.chooseFile.checkIfElementIsPresent()
									&& requestBindPage.chooseFile.checkIfElementIsDisplayed()) {
								if (!testData.get("FileNameToUpload").equals("")) {
									policyDocumentsPage.fileUpload(testData.get("FileNameToUpload"));
								}
							}
							requestBindPage.approve.scrollToElement();
							requestBindPage.approve.click();
							requestBindPage.waitTime(2);// need wait time to load element
							if (requestBindPage.approveBackDating.checkIfElementIsPresent()
									&& requestBindPage.approveBackDating.checkIfElementIsDisplayed()) {
								requestBindPage.approveBackDating.moveToElement();
								requestBindPage.approveBackDating.scrollToElement();
								requestBindPage.approveBackDating.click();
							}

							if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
									&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
								requestBindPage.overrideEffectiveDate.scrollToElement();
								requestBindPage.overrideEffectiveDate.select();
								requestBindPage.waitTime(2);// need wait time to load element
								requestBindPage.approve.scrollToElement();
								requestBindPage.approve.click();
								requestBindPage.waitTime(3);// need wait time to load element
								if (requestBindPage.approveBackDating.checkIfElementIsPresent()
										&& requestBindPage.approveBackDating.checkIfElementIsDisplayed()) {
									requestBindPage.approveBackDating.moveToElement();
									requestBindPage.approveBackDating.scrollToElement();
									requestBindPage.approveBackDating.click();
								}
							}
							Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
						}
					}

					// getting policy number
					if (policySummaryPage.policyNumber.checkIfElementIsPresent()
							&& policySummaryPage.policyNumber.checkIfElementIsDisplayed()) {
						policyNumber = policySummaryPage.policyNumber.getData();
						Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
								"Policy Summary Page", "Policy Number is " + policySummaryPage.policyNumber.getData(),
								false, false);
					}
				} else {
					accountOverviewPage.viewPolicyBtn.scrollToElement();
					accountOverviewPage.viewPolicyBtn.click();
				}

				// Click on cancel policy
				policySummaryPage.cancelPolicy.scrollToElement();
				policySummaryPage.cancelPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

				if (cancelPolicyPage.lapseContinueButton.checkIfElementIsPresent()
						&& cancelPolicyPage.lapseContinueButton.checkIfElementIsDisplayed()) {
					cancelPolicyPage.lapseContinueButton.scrollToElement();
					cancelPolicyPage.lapseContinueButton.click();
				}

				// select cancellation details
				cancelPolicyPage.cancelReasonArrow.scrollToElement();
				cancelPolicyPage.cancelReasonArrow.click();
				cancelPolicyPage.waitTime(2);// Need wait time to load the element
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
				cancelPolicyPage.legalNoticeWording.scrollToElement();
				cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
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
						cancelPolicyPage.waitTime(2);// need wait time to load element
					} else if (k == 2 && cancelPolicyPage.proRatedRadioBtn.checkIfElementIsPresent()
							&& cancelPolicyPage.proRatedRadioBtn.checkIfElementIsDisplayed()) {

						// Asserting Pro Rated SLTF value on cancellation page
						Assertions.addInfo("Cancel Policy Page",
								"SLTF Calculation for Pro Rated for state " + testData.get("QuoteState"));
						cancelPolicyPage.proRatedRadioBtn.scrollToElement();
						cancelPolicyPage.proRatedRadioBtn.click();
						cancelPolicyPage.waitTime(2);// need wait time to load element
					} else if (k == 3 && cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsPresent()
							&& cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsDisplayed()) {

						// Asserting Short Rated SLTF value on cancellation page
						Assertions.addInfo("Cancel Policy Page",
								"SLTF Calculation for Short Rated for state " + testData.get("QuoteState"));
						cancelPolicyPage.shortRatedRadioBtn.scrollToElement();
						cancelPolicyPage.shortRatedRadioBtn.click();
						cancelPolicyPage.waitTime(2);// need wait time to load element
					}

					// get earned values
					double premiumEarned = Double.parseDouble(cancelPolicyPage.premiumValue.formatDynamicPath(3)
							.getData().replace("$", "").replace("-", ""));
					double inspectionFeeEarned = Double.parseDouble(cancelPolicyPage.inspectionFeeEarned.getData());
					double policyFeeEarned = Double
							.parseDouble(cancelPolicyPage.policyFeeEarned.getData().replace(",", ""));
					double sltfActualEarned = Double.parseDouble(cancelPolicyPage.taxesAndFees.formatDynamicPath(3)
							.getData().replace("$", "").replace("-", ""));
					double totalPremiumActualEarned = Double.parseDouble(cancelPolicyPage.policyTotal
							.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));
					double surplusContributionEarned = Double.parseDouble(cancelPolicyPage.surplusContributionVlaue
							.formatDynamicPath(3).getData().replace("$", "").replace("-", ""));

					// calculating earned values
					if (testData.get("QuoteState").contains("NV") || testData.get("QuoteState").contains("NC")
							|| testData.get("QuoteState").contains("AZ") || testData.get("QuoteState").contains("TX")
							|| testData.get("QuoteState").contains("UT")) {
						stampingFeeCalculatedEarned = (premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ surplusContributionEarned)
								* (Double.parseDouble(testData.get("StampingFeeValue")) / 100);
						sltfCalculatedEarned = ((premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ surplusContributionEarned) * (Double.parseDouble(testData.get("SLTFValue")) / 100))
								+ stampingFeeCalculatedEarned;
						totalPremiumCalculatedEarned = premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ sltfActualEarned + surplusContributionEarned;
					} else if (testData.get("QuoteState").contains("AL") || testData.get("QuoteState").contains("AR")
							|| testData.get("QuoteState").contains("LA") || testData.get("QuoteState").contains("MO")
							|| testData.get("QuoteState").contains("SC")) {
						sltfCalculatedEarned = (premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ surplusContributionEarned) * (Double.parseDouble(testData.get("SLTFValue")) / 100);
						totalPremiumCalculatedEarned = premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ sltfActualEarned + surplusContributionEarned;
					} else if (testData.get("QuoteState").contains("FL") && testData.get("Peril").contains("AOP")) {
						double flsoServiceFeeCalculatedEarned = (premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ surplusContributionEarned)
								* (Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100);
						sltfCalculatedEarned = ((premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ surplusContributionEarned) * (Double.parseDouble(testData.get("SLTFValue")) / 100))
								+ flsoServiceFeeCalculatedEarned + 4;
						totalPremiumCalculatedEarned = premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ sltfActualEarned + surplusContributionEarned;
					} else if (testData.get("QuoteState").contains("FL") && testData.get("Peril").contains("Wind")) {
						double flsoServiceFeeCalculatedEarned = (premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ surplusContributionEarned)
								* (Double.parseDouble(testData.get("FSLSOServiceFeeValue")) / 100);
						sltfCalculatedEarned = ((premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ surplusContributionEarned) * (Double.parseDouble(testData.get("SLTFValue")) / 100))
								+ flsoServiceFeeCalculatedEarned;
						totalPremiumCalculatedEarned = premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ sltfActualEarned + surplusContributionEarned;
					} else if (testData.get("QuoteState").contains("MS")) {
						double mwuaFeeCalculatedEarned = (premiumEarned + inspectionFeeEarned + policyFeeEarned)
								* (Double.parseDouble(testData.get("MWUAFeeValue")) / 100);
						stampingFeeCalculatedEarned = (premiumEarned + inspectionFeeEarned + policyFeeEarned)
								* (Double.parseDouble(testData.get("StampingFeeValue")) / 100);
						sltfCalculatedEarned = ((premiumEarned + inspectionFeeEarned + policyFeeEarned)
								* (Double.parseDouble(testData.get("SLTFValue")) / 100)) + stampingFeeCalculatedEarned
								+ mwuaFeeCalculatedEarned;
						totalPremiumCalculatedEarned = premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ sltfActualEarned + surplusContributionEarned;
					} else if (testData.get("QuoteState").contains("NJ")) {
						sltfCalculatedEarned = (premiumEarned + surplusContributionEarned)
								* (Double.parseDouble(testData.get("SLTFValue")) / 100);
						totalPremiumCalculatedEarned = premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ sltfActualEarned + surplusContributionEarned;
					} else if (testData.get("QuoteState").contains("TN")) {
						double transactionFeeCalculatedEarned = (premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ surplusContributionEarned)
								* (Double.parseDouble(testData.get("TransactionFeeValue")) / 100);
						sltfCalculatedEarned = ((premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ surplusContributionEarned) * (Double.parseDouble(testData.get("SLTFValue")) / 100))
								+ transactionFeeCalculatedEarned;
						totalPremiumCalculatedEarned = premiumEarned + inspectionFeeEarned + policyFeeEarned
								+ sltfActualEarned + surplusContributionEarned;
					}

					// asserting sltf values earned
					Assertions.addInfo("Cancel Policy Page",
							"Asserting Earned SLTF value for state " + testData.get("QuoteState"));
					if (Precision.round(
							Math.abs(Precision.round(sltfActualEarned, 2) - Precision.round(sltfCalculatedEarned, 2)),
							2) < 1.00) {
						Assertions.passTest("Cancel Policy Page", "Earned SLTF Value is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfActualEarned);
					} else {
						Assertions.verify(sltfActualEarned, sltfCalculatedEarned, "Cancel Policy Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					Assertions.addInfo("Cancel Policy Page",
							"Asserting Earned Premium Total value for state " + testData.get("QuoteState"));
					if (Precision.round(Math.abs(Precision.round(totalPremiumActualEarned, 2)
							- Precision.round(totalPremiumCalculatedEarned, 2)), 2) < 0.05) {
						Assertions.passTest("Cancel Policy Page",
								"Earned Premium Total Value is calculated correctly for state "
										+ testData.get("QuoteState") + ". Value is $" + totalPremiumActualEarned);
					} else {
						Assertions.verify(totalPremiumActualEarned, totalPremiumCalculatedEarned,
								"View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}

					// get returned values
					double premiumReturned = Double.parseDouble(cancelPolicyPage.premiumValue.formatDynamicPath(4)
							.getData().replace("$", "").replace("-", ""));
					double inspectionFeeReturned = Double.parseDouble(cancelPolicyPage.inspectionFee
							.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));
					double policyFeeReturned = Double.parseDouble(cancelPolicyPage.policyFee.formatDynamicPath(4)
							.getData().replace("$", "").replace("-", ""));
					double sltfActualReturned = Double.parseDouble(cancelPolicyPage.taxesAndFees.formatDynamicPath(4)
							.getData().replace("$", "").replace("-", ""));
					double totalPremiumActualReturned = Double.parseDouble(cancelPolicyPage.policyTotal
							.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));
					double surplusContributionReturned = Double.parseDouble(cancelPolicyPage.surplusContributionVlaue
							.formatDynamicPath(4).getData().replace("$", "").replace("-", ""));

					// calculating returned values
					if (testData.get("QuoteState").contains("NV") || testData.get("QuoteState").contains("NC")
							|| testData.get("QuoteState").contains("AZ") || testData.get("QuoteState").contains("TX")
							|| testData.get("QuoteState").contains("UT")) {
						stampingFeeCalculatedReturned = (premiumReturned + inspectionFeeReturned + policyFeeReturned
								+ surplusContributionReturned)
								* (Double.parseDouble(testData.get("StampingFeeValue")) / 100);
						sltfCalculatedReturned = ((premiumReturned + inspectionFeeReturned + policyFeeReturned
								+ surplusContributionReturned) * (Double.parseDouble(testData.get("SLTFValue")) / 100))
								+ stampingFeeCalculatedReturned;
						totalPremiumCalculatedReturned = premiumReturned + inspectionFeeReturned + policyFeeReturned
								+ sltfActualReturned + surplusContributionReturned;
					} else if (testData.get("QuoteState").contains("AL") || testData.get("QuoteState").contains("AR")
							|| testData.get("QuoteState").contains("LA") || testData.get("QuoteState").contains("MO")
							|| testData.get("QuoteState").contains("SC")) {
						sltfCalculatedReturned = (premiumReturned + inspectionFeeReturned + policyFeeReturned
								+ surplusContributionReturned) * (Double.parseDouble(testData.get("SLTFValue")) / 100);
						totalPremiumCalculatedReturned = premiumReturned + inspectionFeeReturned + policyFeeReturned
								+ sltfActualReturned + surplusContributionReturned;
					} else if (testData.get("QuoteState").contains("FL") && testData.get("Peril").contains("AOP")) {
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
					} else if (testData.get("QuoteState").contains("MS")) {
						double mwuaFeeCalculatedReturned = (premiumReturned + inspectionFeeReturned + policyFeeReturned)
								* (Double.parseDouble(testData.get("MWUAFeeValue")) / 100);
						stampingFeeCalculatedReturned = (premiumReturned + inspectionFeeReturned + policyFeeReturned)
								* (Double.parseDouble(testData.get("StampingFeeValue")) / 100);
						sltfCalculatedReturned = ((premiumReturned + inspectionFeeReturned + policyFeeReturned)
								* (Double.parseDouble(testData.get("SLTFValue")) / 100)) + stampingFeeCalculatedReturned
								+ mwuaFeeCalculatedReturned;
						totalPremiumCalculatedReturned = premiumReturned + inspectionFeeReturned + policyFeeReturned
								+ sltfActualReturned + surplusContributionReturned;
					} else if (testData.get("QuoteState").contains("NJ")) {
						sltfCalculatedReturned = (premiumReturned + surplusContributionReturned)
								* (Double.parseDouble(testData.get("SLTFValue")) / 100);
						totalPremiumCalculatedReturned = premiumReturned + inspectionFeeReturned + policyFeeReturned
								+ sltfActualReturned + surplusContributionReturned;
					} else if (testData.get("QuoteState").contains("TN")) {
						double transactionFeeCalculatedReturned = (premiumReturned + inspectionFeeReturned
								+ policyFeeReturned + surplusContributionReturned)
								* (Double.parseDouble(testData.get("TransactionFeeValue")) / 100);
						sltfCalculatedReturned = ((premiumReturned + inspectionFeeReturned + policyFeeReturned
								+ surplusContributionReturned) * (Double.parseDouble(testData.get("SLTFValue")) / 100))
								+ transactionFeeCalculatedReturned;
						totalPremiumCalculatedReturned = premiumReturned + inspectionFeeReturned + policyFeeReturned
								+ sltfActualReturned + surplusContributionReturned;
					}

					// asserting sltf values returned
					Assertions.addInfo("Cancel Policy Page",
							"Asserting Returned SLTF value for state " + testData.get("QuoteState"));
					if (Precision.round(Math
							.abs(Precision.round(sltfActualReturned, 2) - Precision.round(sltfCalculatedReturned, 2)),
							2) < 1.00) {
						Assertions.passTest("Cancel Policy Page",
								"Returned SLTF Value is calculated correctly for state " + testData.get("QuoteState")
										+ ". Value is $" + sltfActualReturned);
					} else {
						Assertions.verify(sltfActualReturned, sltfCalculatedReturned, "Cancel Policy Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					Assertions.addInfo("Cancel Policy Page",
							"Asserting Returned Premium Total value for state " + testData.get("QuoteState"));
					if (Precision.round(Math.abs(Precision.round(totalPremiumActualReturned, 2)
							- Precision.round(totalPremiumCalculatedReturned, 2)), 2) < 0.05) {
						Assertions.passTest("Cancel Policy Page",
								"Returned Premium Total Value is calculated correctly for state "
										+ testData.get("QuoteState") + ". Value is $" + totalPremiumActualReturned);
					} else {
						Assertions.verify(totalPremiumActualReturned, totalPremiumCalculatedReturned,
								"View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
				}

				// click on homepage
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Writing values to excel sheet
				testDataFilePath = EnvironmentDetails.getEnvironmentDetails()
						.getString("CommRetailNBRegressionTestDataFilePath");
				testDataSheetName = EnvironmentDetails.getEnvironmentDetails()
						.getString("CommRetailNBRegressionTestDataSheetName");
				testDataSheet = new SheetMatchedAccessManager(testDataFilePath + "NBTCID040.xls", testDataSheetName);
				testDataSheet.setExcelRowData(1, i + 1, policyNumber);
			}

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC023 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC023 ", "Executed Successfully");
			}
		}
	}
}
