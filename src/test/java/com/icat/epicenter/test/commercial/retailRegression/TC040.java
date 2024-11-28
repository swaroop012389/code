/** Program Description: Validation of Surplus Lines Taxes and Fees on cancellation page for all the 14 states for renewal quote and requotes
Check if the Mandatory check box is available with the State specific wording on all 14 states
 *  Author			   : Sowndarya
 *  Date of Creation   : 01/02/2022
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC040 extends AbstractCommercialTest {

	public TC040() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID040.xls";
	}

	double surpluContributionValueAcc;

	double fslsoServiceFeeActual;
	double transactionFeeActual;
	double sltfValueCalculatedAcc;
	double premiumValueAcc;
	double feeValueAcc;
	double sltfValueActualAcc;
	double totalPremiumValueActualAcc;
	double premiumValue;
	double inspectionFee;
	double policyFee;
	double sltFValueActual;
	double sltfValueCalculated;
	double stampingFeeActual;
	double stampingFeeCalculated;
	double fslsoServiceFeeCalculated;
	double transactionFeeCalculated;
	double totalPremiumValueCalculatedAcc;
	double mwuaFeeCalculated;
	double mwuaFeeActual;
	double surpluContributionValue;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		String quoteNumber;
		String policyNumber;
		Map<String, String> testData;
		boolean isTestPassed = false;

		try {
			Assertions.addInfo("Scenario 01",
					"Verifying the SLTF values on Quote Document and Account Overview page for renewal quote for all 14 states");
			for (int i = 0; i <= 15; i++) {
				testData = data.get(i);

				// Search the Policy Number
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Producer Home page loaded successfully", false, false);
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchPolicy(testData.get("PolicyNumber"));

				// Asserting the policy number
				policyNumber = policySummaryPage.policyNumber.getData();
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
						"Policy Summary Page", "Policy Number for state " + testData.get("QuoteState") + " is "
								+ policySummaryPage.policyNumber.getData(),
						false, false);

				if (policySummaryPage.viewActiveRenewal.checkIfElementIsPresent()
						&& policySummaryPage.viewActiveRenewal.checkIfElementIsDisplayed()) {
					policySummaryPage.viewActiveRenewal.scrollToElement();
					policySummaryPage.viewActiveRenewal.click();
				}

				// click on renew link
				if (policySummaryPage.renewPolicy.checkIfElementIsPresent()
						&& policySummaryPage.renewPolicy.checkIfElementIsDisplayed()) {
					policySummaryPage.renewPolicy.scrollToElement();
					policySummaryPage.renewPolicy.click();
					Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

					// Add Expacc Details
					policyRenewalPage.addExpaccButton.scrollToElement();
					policyRenewalPage.addExpaccButton.click();
					Assertions.passTest("Policy Renewal Page", "Clicked on Add Expacc Button");
					expaccInfoPage.enterExpaccInfo(testData, policyNumber);
					Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

					// click on renew policy
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
				}

				// getting renewal quote number
				Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page loaded successfully", false, false);
				quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
				Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

				// getting premium, fees, sltf and total premium values from Account
				// overview
				// page
				premiumValueAcc = Double
						.parseDouble(accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", ""));
				feeValueAcc = Double
						.parseDouble(accountOverviewPage.feesValue.getData().replace("$", "").replace(",", ""));
				sltfValueActualAcc = Double
						.parseDouble(accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", ""));
				totalPremiumValueActualAcc = Double
						.parseDouble(accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", ""));
				surpluContributionValueAcc = Double.parseDouble(
						accountOverviewPage.surplusContributionValue.getData().replace("$", "").replace(",", ""));
				accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
				accountOverviewPage.viewPrintFullQuoteLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on View print Full quote");

				// getting premium, fees and sltf values from View/Print Full Quote
				// Page
				premiumValue = Double
						.parseDouble(viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", ""));
				policyFee = Double
						.parseDouble(viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", ""));
				sltFValueActual = Double.parseDouble(
						viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "").replace(",", ""));
				surpluContributionValue = Double.parseDouble(
						viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",", ""));

				// Calculate SLTF values for all 14 states
				if (testData.get("QuoteState").contains("AL") || testData.get("QuoteState").contains("AR")
						|| testData.get("QuoteState").contains("LA") || testData.get("QuoteState").contains("MO")
						|| testData.get("QuoteState").contains("SC")) {
					sltfValueCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Surplus Contribution is " + viewOrPrintFullQuotePage.surplusContributionValue.getData());
					Assertions.addInfo("View/Print Full Quote Page", "Calculating SLTF values for state "
							+ testData.get("QuoteState") + " on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
					} else {
						Assertions.verify(sltFValueActual, sltfValueCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
				} else if (testData.get("QuoteState").contains("NJ")) {
					sltfValueCalculated = (premiumValue + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Surplus Contribution is " + viewOrPrintFullQuotePage.surplusContributionValue.getData());
					Assertions.addInfo("View/Print Full Quote Page",
							"Calculating SLTF values for state NJ on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
					} else {
						Assertions.verify(sltFValueActual, sltfValueCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
				} else if (testData.get("QuoteState").contains("AZ") || testData.get("QuoteState").contains("NV")
						|| testData.get("QuoteState").contains("NC") || testData.get("QuoteState").contains("TX")
						|| testData.get("QuoteState").contains("UT")) {
					sltfValueCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					stampingFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "").replace(",", ""));
					stampingFeeCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("StampingFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Surplus Contribution is " + viewOrPrintFullQuotePage.surplusContributionValue.getData());
					Assertions.addInfo("View/Print Full Quote Page", "Calculating SLTF values for state "
							+ testData.get("QuoteState") + " on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
					} else {
						Assertions.verify(sltFValueActual, sltfValueCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					Assertions.addInfo("View/Print Full Quote Page",
							"Calculating Stamping Fee value on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(stampingFeeActual, 2) - Precision.round(stampingFeeCalculated, 2)),
							2) < 1.00) {
						Assertions.passTest("View/Print Full Quote Page", "MWUA Fee is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + stampingFeeActual);
					} else {
						Assertions.verify(stampingFeeActual, stampingFeeCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					if (testData.get("QuoteState").contains("MS")) {
						mwuaFeeActual = Double.parseDouble(
								viewOrPrintFullQuotePage.mwuaValue.getData().replace("$", "").replace(",", ""));
						mwuaFeeCalculated = (premiumValue + policyFee + surpluContributionValue)
								* (Double.parseDouble(testData.get("MWUAFeeValue"))) / 100;
						Assertions.addInfo("View/Print Full Quote Page",
								"Calculating MWUA Fee value on View/Print Full Quote Page");
						if (Precision.round(
								Math.abs(Precision.round(mwuaFeeActual, 2) - Precision.round(mwuaFeeCalculated, 2)),
								2) < 0.05) {
							Assertions.passTest("View/Print Full Quote Page",
									"MWUA Fee is calculated correctly for state " + testData.get("QuoteState")
											+ ". Value is $" + mwuaFeeActual);
						} else {
							Assertions.verify(mwuaFeeActual, mwuaFeeCalculated, "View/Print Full Quote Page",
									"Actual and Calculated values are not matching for state "
											+ testData.get("QuoteState"),
									false, false);
						}
					}
				} else if (testData.get("QuoteState").contains("MS")) {
					sltfValueCalculated = (premiumValue + policyFee) * (Double.parseDouble(testData.get("SLTFValue")))
							/ 100;
					stampingFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "").replace(",", ""));
					stampingFeeCalculated = (premiumValue + policyFee)
							* (Double.parseDouble(testData.get("StampingFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Surplus Contribution is " + viewOrPrintFullQuotePage.surplusContributionValue.getData());
					Assertions.addInfo("View/Print Full Quote Page", "Calculating SLTF values for state "
							+ testData.get("QuoteState") + " on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
					} else {
						Assertions.verify(sltFValueActual, sltfValueCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					Assertions.addInfo("View/Print Full Quote Page",
							"Calculating Stamping Fee value on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(stampingFeeActual, 2) - Precision.round(stampingFeeCalculated, 2)),
							2) < 1.00) {
						Assertions.passTest("View/Print Full Quote Page", "MWUA Fee is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + stampingFeeActual);
					} else {
						Assertions.verify(stampingFeeActual, stampingFeeCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					if (testData.get("QuoteState").contains("MS")) {
						mwuaFeeActual = Double.parseDouble(
								viewOrPrintFullQuotePage.mwuaValue.getData().replace("$", "").replace(",", ""));
						mwuaFeeCalculated = (premiumValue + policyFee)
								* (Double.parseDouble(testData.get("MWUAFeeValue"))) / 100;
						Assertions.addInfo("View/Print Full Quote Page",
								"Calculating MWUA Fee value on View/Print Full Quote Page");
						if (Precision.round(
								Math.abs(Precision.round(mwuaFeeActual, 2) - Precision.round(mwuaFeeCalculated, 2)),
								2) < 0.05) {
							Assertions.passTest("View/Print Full Quote Page",
									"MWUA Fee is calculated correctly for state " + testData.get("QuoteState")
											+ ". Value is $" + mwuaFeeActual);
						} else {
							Assertions.verify(mwuaFeeActual, mwuaFeeCalculated, "View/Print Full Quote Page",
									"Actual and Calculated values are not matching for state "
											+ testData.get("QuoteState"),
									false, false);
						}
					}
				} else if (testData.get("QuoteState").contains("FL")) {
					sltfValueCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					fslsoServiceFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.fslsoServiceFee.getData().replace("$", "").replace(",", ""));
					fslsoServiceFeeCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("FSLSOServiceFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());
					Assertions.addInfo("View/Print Full Quote Page",
							"Calculating EMPA Fee values on View/Print Full Quote Page");
					if (testData.get("QuoteState").contains("FL") && testData.get("Peril").equalsIgnoreCase("Wind")) {
						Assertions.verify(
								viewOrPrintFullQuotePage.empaValue.checkIfElementIsPresent()
										&& viewOrPrintFullQuotePage.empaValue.checkIfElementIsDisplayed(),
								false, "View/Print Full Quote Page",
								"EMPA Fee value is not displayed when FL quote has Wind peril", false, false);
					} else {
						Assertions.verify(viewOrPrintFullQuotePage.empaValue.getData(), "$4.00",
								"View/Print Full Quote Page",
								"EMPA Value " + viewOrPrintFullQuotePage.empaValue.getData() + " is displayed", false,
								false);
					}
					Assertions.addInfo("View/Print Full Quote Page", "Calculating SLTF values for state "
							+ testData.get("QuoteState") + " on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
					} else {
						Assertions.verify(sltFValueActual, sltfValueCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					Assertions.addInfo("View/Print Full Quote Page",
							"Calculating FSLSO Service Fee value on View/Print Full Quote Page");
					if (Precision.round(Math.abs(
							Precision.round(fslsoServiceFeeActual, 2) - Precision.round(fslsoServiceFeeCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + fslsoServiceFeeActual);
					} else {
						Assertions.verify(fslsoServiceFeeActual, fslsoServiceFeeCalculated,
								"View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
				} else if (testData.get("QuoteState").contains("TN")) {
					sltfValueCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					transactionFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.transactionFee.getData().replace("$", "").replace(",", ""));
					transactionFeeCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("TransactionFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Surplus Contribution is " + viewOrPrintFullQuotePage.surplusContributionValue.getData());
					Assertions.addInfo("View/Print Full Quote Page", "Calculating SLTF values for state "
							+ testData.get("QuoteState") + " on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
					} else {
						Assertions.verify(sltFValueActual, sltfValueCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					Assertions.addInfo("View/Print Full Quote Page",
							"Calculating Transaction Fee on View/Print Full Quote Page");
					if (Precision.round(Math.abs(
							Precision.round(transactionFeeActual, 2) - Precision.round(transactionFeeCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page",
								"Transaction Fee is calculated correctly for state " + testData.get("QuoteState")
										+ ". Value is $" + transactionFeeActual);
					} else {
						Assertions.verify(transactionFeeActual, transactionFeeCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
				}

				// click on back
				viewOrPrintFullQuotePage.scrollToTopPage();
				viewOrPrintFullQuotePage.waitTime(2);// need wait to scroll to top
														// page
				viewOrPrintFullQuotePage.backButton.click();

				// Calculate SLTF on account overview page
				if (testData.get("QuoteState").contains("NJ")) {
					sltfValueCalculatedAcc = (premiumValueAcc + surpluContributionValueAcc)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
				} else {
					sltfValueCalculatedAcc = (premiumValueAcc + feeValueAcc + surpluContributionValueAcc)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
				}
				totalPremiumValueCalculatedAcc = premiumValueAcc + feeValueAcc + sltfValueActualAcc
						+ surpluContributionValueAcc;
				Assertions.passTest("Account Overview Page",
						"Premium is " + accountOverviewPage.premiumValue.getData());
				Assertions.passTest("Account Overview Page",
						"Surplus Contribution is " + accountOverviewPage.surplusContributionValue.getData());
				Assertions.passTest("Account Overview Page", "Total Fee is " + accountOverviewPage.feesValue.getData());
				Assertions.addInfo("Account Overview Page", "Calculating SLTF values for state "
						+ testData.get("QuoteState") + " on Account Overview Page");
				if (Precision.round(
						Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
						2) < 0.05) {
					Assertions.passTest("Account Overview Page", "SLTF is calculated correctly for state "
							+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
				} else {
					Assertions.verify(sltFValueActual, sltfValueCalculated, "Account Overview Page",
							"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
							false, false);
				}
				Assertions.addInfo("Account Overview Page", "Calculating Policy Total on Account Overview Page");
				if (Precision.round(Math.abs(Precision.round(totalPremiumValueActualAcc, 2)
						- Precision.round(totalPremiumValueCalculatedAcc, 2)), 2) < 0.05) {
					Assertions.passTest("Account Overview Page", "Policy Total is calculated correctly for state "
							+ testData.get("QuoteState") + ". Value is $" + totalPremiumValueActualAcc);
				} else {
					Assertions.verify(totalPremiumValueActualAcc, totalPremiumValueCalculatedAcc,
							"Account Overview Page",
							"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
							false, false);
				}
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Verifying diligent effort wordings for all 14 states renewal quotes
			Assertions.addInfo("Scenario 02",
					"Verifying for the Presence/Absence of Diligent Effort wordings for renewal quote");
			for (int i = 0; i <= 15; i++) {
				testData = data.get(i);

				// Search the Policy Number
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchPolicy(testData.get("PolicyNumber"));

				// Asserting the policy number
				policyNumber = policySummaryPage.policyNumber.getData();
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
						"Policy Summary Page", "Policy Number for state " + testData.get("QuoteState") + " is "
								+ policySummaryPage.policyNumber.getData(),
						false, false);
				if (policySummaryPage.viewActiveRenewal.checkIfElementIsPresent()
						&& policySummaryPage.viewActiveRenewal.checkIfElementIsDisplayed()) {
					policySummaryPage.viewActiveRenewal.scrollToElement();
					policySummaryPage.viewActiveRenewal.click();
				}

				// getting renewal quote number
				Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page loaded successfully", false, false);
				quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
				Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

				// Click on View Print full quote link
				accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
				accountOverviewPage.viewPrintFullQuoteLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on View print Full quote");
				Assertions.verify(quoteDetailsPage.quoteNumber.checkIfElementIsDisplayed(), true, "Quote Details Page",
						"Quote Details Page Loaded successfully", false, false);

				if (testData.get("QuoteState").contains("AL") || testData.get("QuoteState").contains("TX")
						|| testData.get("QuoteState").contains("AR") || testData.get("QuoteState").contains("SC")
						|| testData.get("QuoteState").contains("NC") || testData.get("QuoteState").contains("NJ")
						|| testData.get("QuoteState").contains("MO") || testData.get("QuoteState").contains("TN")
						|| testData.get("QuoteState").contains("FL") || testData.get("QuoteState").contains("AZ")
						|| testData.get("QuoteState").contains("LA") || testData.get("QuoteState").contains("MS")
						|| testData.get("QuoteState").contains("UT") || testData.get("QuoteState").contains("NV")) {
					if ((quoteDetailsPage.dueDiligenceCertificate.checkIfElementIsPresent()
							&& quoteDetailsPage.dueDiligenceCertificate.checkIfElementIsDisplayed())
							&& (quoteDetailsPage.dueDiligenceDetails.checkIfElementIsPresent()
									&& quoteDetailsPage.dueDiligenceDetails.checkIfElementIsDisplayed())) {
						Assertions.passTest("Quote Details Page",
								"The Words related to Diligent efforts are displayed when the occupany is habitational");

					}
				} else {
					if (!((quoteDetailsPage.dueDiligenceCertificate.checkIfElementIsPresent())
							|| (quoteDetailsPage.dueDiligenceDetails.checkIfElementIsPresent())
									&& testData.get("QuoteState").contains("FL"))) {
						Assertions.passTest("Quote Details Page",
								"The Words related to Diligent efforts are not displayed when the occupany is non habitational");
					}
				}
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Creating requotes for all renewal policies
			Assertions.addInfo("Scenario 03",
					"Creating Renewal Requotes for all 14 states and verifying the SLTF values on Quote document and account overview page");
			for (int i = 0; i <= 15; i++) {
				testData = data.get(i);

				// Search the Policy Number
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchPolicy(testData.get("PolicyNumber"));

				// Asserting the policy number
				policyNumber = policySummaryPage.policyNumber.getData();
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
						"Policy Summary Page", "Policy Number for state " + testData.get("QuoteState") + " is "
								+ policySummaryPage.policyNumber.getData(),
						false, false);

				// click on view active renewal
				policySummaryPage.viewActiveRenewal.scrollToElement();
				policySummaryPage.viewActiveRenewal.click();

				// getting renewal quote number
				Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page loaded successfully", false, false);
				quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
				Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

				// click on release renewal to producer button
				accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
				accountOverviewPage.releaseRenewalToProducerButton.click();
				Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

				// click on edit deductibles and limits button
				accountOverviewPage.editDeductibleAndLimits.scrollToElement();
				accountOverviewPage.editDeductibleAndLimits.click();
				Assertions.passTest("Account Overview Page", "Clicked on Edit Deductibles and limits");

				// entering BPP value
				createQuotePage.bPPValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
				createQuotePage.bPPValue.formatDynamicPath(0, 0).scrollToElement();
				createQuotePage.bPPValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgBPP"));
				createQuotePage.bPPValue.formatDynamicPath(0, 0).tab();
				createQuotePage.getAQuote.waitTillVisibilityOfElement(60);

				// click on get a quote
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
				Assertions.passTest("Create Quote Page", "Clicked on Get a Quote");

				// click on continue
				if (createQuotePage.continueButton.checkIfElementIsPresent()
						&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
					createQuotePage.continueButton.scrollToElement();
					createQuotePage.continueButton.click();
				}

				// getting renewal quote number
				Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page loaded successfully", false, false);
				quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
				Assertions.passTest("Account Overview Page", "The Renewal Requote Number :  " + quoteNumber);

				// getting premium, fees, sltf and total premium values from Account
				// overview
				// page
				premiumValueAcc = Double
						.parseDouble(accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", ""));
				feeValueAcc = Double
						.parseDouble(accountOverviewPage.feesValue.getData().replace("$", "").replace(",", ""));
				sltfValueActualAcc = Double
						.parseDouble(accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", ""));
				totalPremiumValueActualAcc = Double
						.parseDouble(accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", ""));
				surpluContributionValueAcc = Double.parseDouble(
						accountOverviewPage.surplusContributionValue.getData().replace("$", "").replace(",", ""));
				accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
				accountOverviewPage.viewPrintFullQuoteLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on View print Full quote");

				// getting premium, fees and sltf values from View/Print Full Quote
				// Page
				premiumValue = Double
						.parseDouble(viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", ""));
				policyFee = Double
						.parseDouble(viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", ""));
				sltFValueActual = Double.parseDouble(
						viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "").replace(",", ""));
				surpluContributionValue = Double.parseDouble(
						viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",", ""));

				// Calculate SLTF values for all 14 states
				if (testData.get("QuoteState").contains("AL") || testData.get("QuoteState").contains("AR")
						|| testData.get("QuoteState").contains("LA") || testData.get("QuoteState").contains("MO")
						|| testData.get("QuoteState").contains("SC")) {
					sltfValueCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Surplus Contribution is " + viewOrPrintFullQuotePage.surplusContributionValue.getData());
					Assertions.addInfo("View/Print Full Quote Page", "Calculating SLTF values for state "
							+ testData.get("QuoteState") + " on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
					} else {
						Assertions.verify(sltFValueActual, sltfValueCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
				} else if (testData.get("QuoteState").contains("NJ")) {
					sltfValueCalculated = (premiumValue + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Surplus Contribution is " + viewOrPrintFullQuotePage.surplusContributionValue.getData());
					Assertions.addInfo("View/Print Full Quote Page",
							"Calculating SLTF values for state NJ on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
					} else {
						Assertions.verify(sltFValueActual, sltfValueCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
				} else if (testData.get("QuoteState").contains("AZ") || testData.get("QuoteState").contains("NV")
						|| testData.get("QuoteState").contains("NC") || testData.get("QuoteState").contains("TX")
						|| testData.get("QuoteState").contains("UT")) {
					sltfValueCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					stampingFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "").replace(",", ""));
					stampingFeeCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("StampingFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Surplus Contribution is " + viewOrPrintFullQuotePage.surplusContributionValue.getData());
					Assertions.addInfo("View/Print Full Quote Page", "Calculating SLTF values for state "
							+ testData.get("QuoteState") + " on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
					} else {
						Assertions.verify(sltFValueActual, sltfValueCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					Assertions.addInfo("View/Print Full Quote Page",
							"Calculating Stamping Fee value on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(stampingFeeActual, 2) - Precision.round(stampingFeeCalculated, 2)),
							2) < 1.00) {
						Assertions.passTest("View/Print Full Quote Page", "MWUA Fee is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + stampingFeeActual);
					} else {
						Assertions.verify(stampingFeeActual, stampingFeeCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					if (testData.get("QuoteState").contains("MS")) {
						mwuaFeeActual = Double.parseDouble(
								viewOrPrintFullQuotePage.mwuaValue.getData().replace("$", "").replace(",", ""));
						mwuaFeeCalculated = (premiumValue + policyFee + surpluContributionValue)
								* (Double.parseDouble(testData.get("MWUAFeeValue"))) / 100;
						Assertions.addInfo("View/Print Full Quote Page",
								"Calculating MWUA Fee value on View/Print Full Quote Page");
						if (Precision.round(
								Math.abs(Precision.round(mwuaFeeActual, 2) - Precision.round(mwuaFeeCalculated, 2)),
								2) < 0.05) {
							Assertions.passTest("View/Print Full Quote Page",
									"MWUA Fee is calculated correctly for state " + testData.get("QuoteState")
											+ ". Value is $" + mwuaFeeActual);
						} else {
							Assertions.verify(mwuaFeeActual, mwuaFeeCalculated, "View/Print Full Quote Page",
									"Actual and Calculated values are not matching for state "
											+ testData.get("QuoteState"),
									false, false);
						}
					}
				} else if (testData.get("QuoteState").contains("MS")) {
					sltfValueCalculated = (premiumValue + policyFee) * (Double.parseDouble(testData.get("SLTFValue")))
							/ 100;
					stampingFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "").replace(",", ""));
					stampingFeeCalculated = (premiumValue + policyFee)
							* (Double.parseDouble(testData.get("StampingFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Surplus Contribution is " + viewOrPrintFullQuotePage.surplusContributionValue.getData());
					Assertions.addInfo("View/Print Full Quote Page", "Calculating SLTF values for state "
							+ testData.get("QuoteState") + " on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
					} else {
						Assertions.verify(sltFValueActual, sltfValueCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					Assertions.addInfo("View/Print Full Quote Page",
							"Calculating Stamping Fee value on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(stampingFeeActual, 2) - Precision.round(stampingFeeCalculated, 2)),
							2) < 1.00) {
						Assertions.passTest("View/Print Full Quote Page", "MWUA Fee is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + stampingFeeActual);
					} else {
						Assertions.verify(stampingFeeActual, stampingFeeCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					if (testData.get("QuoteState").contains("MS")) {
						mwuaFeeActual = Double.parseDouble(
								viewOrPrintFullQuotePage.mwuaValue.getData().replace("$", "").replace(",", ""));
						mwuaFeeCalculated = (premiumValue + policyFee)
								* (Double.parseDouble(testData.get("MWUAFeeValue"))) / 100;
						Assertions.addInfo("View/Print Full Quote Page",
								"Calculating MWUA Fee value on View/Print Full Quote Page");
						if (Precision.round(
								Math.abs(Precision.round(mwuaFeeActual, 2) - Precision.round(mwuaFeeCalculated, 2)),
								2) < 0.05) {
							Assertions.passTest("View/Print Full Quote Page",
									"MWUA Fee is calculated correctly for state " + testData.get("QuoteState")
											+ ". Value is $" + mwuaFeeActual);
						} else {
							Assertions.verify(mwuaFeeActual, mwuaFeeCalculated, "View/Print Full Quote Page",
									"Actual and Calculated values are not matching for state "
											+ testData.get("QuoteState"),
									false, false);
						}
					}
				} else if (testData.get("QuoteState").contains("FL")) {
					sltfValueCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					fslsoServiceFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.fslsoServiceFee.getData().replace("$", "").replace(",", ""));
					fslsoServiceFeeCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("FSLSOServiceFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Surplus Contribution is " + viewOrPrintFullQuotePage.surplusContributionValue.getData());
					Assertions.addInfo("View/Print Full Quote Page",
							"Calculating EMPA Fee values on View/Print Full Quote Page");
					if (testData.get("QuoteState").contains("FL") && testData.get("Peril").equalsIgnoreCase("Wind")) {
						Assertions.verify(
								viewOrPrintFullQuotePage.empaValue.checkIfElementIsPresent()
										&& viewOrPrintFullQuotePage.empaValue.checkIfElementIsDisplayed(),
								false, "View/Print Full Quote Page",
								"EMPA Fee value is not displayed when FL quote has Wind peril", false, false);
					} else {
						Assertions.verify(viewOrPrintFullQuotePage.empaValue.getData(), "$4.00",
								"View/Print Full Quote Page",
								"EMPA Value " + viewOrPrintFullQuotePage.empaValue.getData() + " is displayed", false,
								false);
					}
					Assertions.addInfo("View/Print Full Quote Page", "Calculating SLTF values for state "
							+ testData.get("QuoteState") + " on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
					} else {
						Assertions.verify(sltFValueActual, sltfValueCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					Assertions.addInfo("View/Print Full Quote Page",
							"Calculating FSLSO Service Fee value on View/Print Full Quote Page");
					if (Precision.round(Math.abs(
							Precision.round(fslsoServiceFeeActual, 2) - Precision.round(fslsoServiceFeeCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + fslsoServiceFeeActual);
					} else {
						Assertions.verify(fslsoServiceFeeActual, fslsoServiceFeeCalculated,
								"View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
				} else if (testData.get("QuoteState").contains("TN")) {
					sltfValueCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					transactionFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.transactionFee.getData().replace("$", "").replace(",", ""));
					transactionFeeCalculated = (premiumValue + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("TransactionFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Surplus Contribution is " + viewOrPrintFullQuotePage.surplusContributionValue.getData());
					Assertions.addInfo("View/Print Full Quote Page", "Calculating SLTF values for state "
							+ testData.get("QuoteState") + " on View/Print Full Quote Page");
					if (Precision.round(
							Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page", "SLTF is calculated correctly for state "
								+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
					} else {
						Assertions.verify(sltFValueActual, sltfValueCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
					Assertions.addInfo("View/Print Full Quote Page",
							"Calculating Transaction Fee on View/Print Full Quote Page");
					if (Precision.round(Math.abs(
							Precision.round(transactionFeeActual, 2) - Precision.round(transactionFeeCalculated, 2)),
							2) < 0.05) {
						Assertions.passTest("View/Print Full Quote Page",
								"Transaction Fee is calculated correctly for state " + testData.get("QuoteState")
										+ ". Value is $" + transactionFeeActual);
					} else {
						Assertions.verify(transactionFeeActual, transactionFeeCalculated, "View/Print Full Quote Page",
								"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
								false, false);
					}
				}

				// click on back
				viewOrPrintFullQuotePage.scrollToTopPage();
				viewOrPrintFullQuotePage.waitTime(2);// need wait to scroll to top
														// page
				viewOrPrintFullQuotePage.backButton.click();

				// Calculate SLTF on account overview page
				if (testData.get("QuoteState").contains("NJ")) {
					sltfValueCalculatedAcc = (premiumValueAcc + surpluContributionValueAcc)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
				} else {
					sltfValueCalculatedAcc = (premiumValueAcc + feeValueAcc + surpluContributionValueAcc)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
				}
				totalPremiumValueCalculatedAcc = premiumValueAcc + feeValueAcc + sltfValueActualAcc
						+ surpluContributionValueAcc;
				Assertions.passTest("Account Overview Page",
						"Premium is " + accountOverviewPage.premiumValue.getData());
				Assertions.passTest("Account Overview Page", "Total Fee is " + accountOverviewPage.feesValue.getData());
				Assertions.passTest("Account Overview Page",
						"Surplus Contribution is " + accountOverviewPage.surplusContributionValue.getData());
				Assertions.addInfo("Account Overview Page", "Calculating SLTF values for state "
						+ testData.get("QuoteState") + " on Account Overview Page");
				if (Precision.round(
						Math.abs(Precision.round(sltFValueActual, 2) - Precision.round(sltfValueCalculated, 2)),
						2) < 0.05) {
					Assertions.passTest("Account Overview Page", "SLTF is calculated correctly for state "
							+ testData.get("QuoteState") + ". Value is $" + sltfValueActualAcc);
				} else {
					Assertions.verify(sltFValueActual, sltfValueCalculated, "Account Overview Page",
							"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
							false, false);
				}
				Assertions.addInfo("Account Overview Page", "Calculating Policy Total on Account Overview Page");
				if (Precision.round(Math.abs(Precision.round(totalPremiumValueActualAcc, 2)
						- Precision.round(totalPremiumValueCalculatedAcc, 2)), 2) < 0.05) {
					Assertions.passTest("Account Overview Page", "Policy Total is calculated correctly for state "
							+ testData.get("QuoteState") + ". Value is $" + totalPremiumValueActualAcc);
				} else {
					Assertions.verify(totalPremiumValueActualAcc, totalPremiumValueCalculatedAcc,
							"Account Overview Page",
							"Actual and Calculated values are not matching for state " + testData.get("QuoteState"),
							false, false);
				}
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
			}

			for (int i = 0; i <= 15; i++) {
				testData = data.get(i);

				// Search the Policy Number
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchPolicy(testData.get("PolicyNumber"));

				// Asserting the policy number
				policyNumber = policySummaryPage.policyNumber.getData();
				Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
						"Policy Summary Page", "Policy Number for state " + testData.get("QuoteState") + " is "
								+ policySummaryPage.policyNumber.getData(),
						false, false);

				// click on view active renewal
				policySummaryPage.viewActiveRenewal.scrollToElement();
				policySummaryPage.viewActiveRenewal.click();

				// getting renewal quote number
				Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page loaded successfully", false, false);
				quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
				Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

				if (accountOverviewPage.issueQuoteButton.checkIfElementIsPresent()
						&& accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed()) {
					accountOverviewPage.issueQuoteButton.scrollToElement();
					accountOverviewPage.issueQuoteButton.click();
				}

				// click on request bind button
				accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
				Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
				Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Bind Request Page",
						"Request Bind Page loaded successfully", false, false);

				// Asserting checkbox and wordings for specific state
				if (testData.get("QuoteState").contains("AL") || testData.get("QuoteState").contains("TX")
						|| testData.get("QuoteState").contains("AR") || testData.get("QuoteState").contains("SC")
						|| testData.get("QuoteState").contains("NC") || testData.get("QuoteState").contains("NJ")
						|| testData.get("QuoteState").contains("MO") || testData.get("QuoteState").contains("TN")
						|| testData.get("QuoteState").contains("FL")) {
					Assertions.verify(requestBindPage.specialInstructionCheckbox.checkIfElementIsDisplayed(), true,
							"Request Bind Page", "Special Instruction checkbox present is verified", false, false);
					Assertions.verify(requestBindPage.dueDiligenceCheckbox.checkIfElementIsDisplayed(), true,
							"Request Bind Page", "Due Diligence checkbox present is verified", false, false);
					Assertions.verify(
							requestBindPage.dueDiligenceText
									.formatDynamicPath("diligent effort").checkIfElementIsDisplayed(),
							true, "Request Bind Page",
							"The Wordings "
									+ requestBindPage.dueDiligenceText.formatDynamicPath("diligent effort").getData()
									+ "displayed is verified",
							false, false);
					Assertions.verify(
							requestBindPage.dueDiligenceText.formatDynamicPath("total cost")
									.checkIfElementIsDisplayed(),
							true, "Request Bind Page",
							"The Wordings " + requestBindPage.dueDiligenceText.formatDynamicPath("total cost").getData()
									+ "displayed is verified",
							false, false);
				} else if (testData.get("QuoteState").contains("LA") || testData.get("QuoteState").contains("AZ")
						|| testData.get("QuoteState").contains("UT") || testData.get("QuoteState").contains("MS")
						|| testData.get("QuoteState").contains("NV")) {
					Assertions.verify(requestBindPage.specialInstructionCheckbox.checkIfElementIsDisplayed(), true,
							"Request Bind Page", "Special Instruction checkbox present is verified", false, false);
					Assertions.verify(requestBindPage.dueDiligenceCheckbox.checkIfElementIsDisplayed(), true,
							"Request Bind Page", "Due Diligence checkbox present is verified", false, false);
					Assertions.verify(
							requestBindPage.dueDiligenceText.formatDynamicPath("total cost")
									.checkIfElementIsDisplayed(),
							true, "Request Bind Page",
							"The Wordings " + requestBindPage.dueDiligenceText.formatDynamicPath("total cost").getData()
									+ "displayed is verified",
							false, false);
				}
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC040 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC040 ", "Executed Successfully");
			}
		}
	}
}
