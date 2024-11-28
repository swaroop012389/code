/** Program Description: Validation of Surplus Lines Taxes and Fees for all the 14 states
 *  Author			   : John
 *  Date of Creation   : 20/07/2021
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.FWProperties;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC009 extends AbstractCommercialTest {

	public TC009() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID009.xls";
	}

	// Initializing the variables globally
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
	String quoteNumber;
	FWProperties property;
	String testDataFilePath;
	String testDataSheetName;
	SheetMatchedAccessManager testDataSheet;
	double surpluContributionValue;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		Map<String, String> testData;
		DecimalFormat df = new DecimalFormat("0.00");
		boolean isTestPassed = false;

		try {
			// 0 = AL 1 = AZ 2=AR 3=FL_AOP 4=FL_Wind 5=LA 6=MS 7=MO 8=NJ 9=NV 10=NC 11=SC
			// 12=TN 13=TX 14=UT
			for (int i = 0; i <= 15; i++) {
				testData = data.get(i);

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
				//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
						//"Building Page loaded successfully", false, false);
				buildingPage.enterBuildingDetails(testData);

				// selecting peril
				if (!testData.get("Peril").equals("Quake")) {
					Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true,
							"Select Peril Page", "Select Peril Page loaded successfully", false, false);
					selectPerilPage.selectPeril(testData.get("Peril"));
				}

				// Enter prior loss details
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

				// verifying the presence of Upload prebind documents button
				Assertions.verify(accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Upload Prebind Documents button displayed is verified", false, false);

				// getting premium, fees, sltf and total premium values from Account overview
				// page
				premiumValueAcc = Double
						.parseDouble(accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", ""));
				feeValueAcc = Double
						.parseDouble(accountOverviewPage.feesValue.getData().replace("$", "").replace(",", ""));
				sltfValueActualAcc = Double
						.parseDouble(accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", ""));
				totalPremiumValueActualAcc = Double
						.parseDouble(accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", ""));
				surpluContributionValue = Double.parseDouble(
						accountOverviewPage.surplusContributionValue.getData().replace("$", "").replace(",", ""));

				// Click on view or print full quote link
				accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
				accountOverviewPage.viewPrintFullQuoteLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on View print Full quote");

				// getting premium, fees and sltf values from View/Print Full Quote Page
				premiumValue = Double
						.parseDouble(viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", ""));
				inspectionFee = Double.parseDouble(
						viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(",", ""));
				policyFee = Double
						.parseDouble(viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", ""));
				sltFValueActual = Double.parseDouble(
						viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "").replace(",", ""));
				surpluContributionValue = Double.parseDouble(
						viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",", ""));

				// Calculate SLTF values for all 14 states
				Assertions.addInfo("Scenario 01",
						"Calculating SLTF values,StampingFeeValue,EMPA,MWUA Fees for all 14 states on View/Print Full Quote Page");
				if (testData.get("QuoteState").contains("AL") || testData.get("QuoteState").contains("LA")
						|| testData.get("QuoteState").contains("SC")) {
					sltfValueCalculated = (premiumValue + inspectionFee + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Inspection Fee is " + viewOrPrintFullQuotePage.inspectionFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());

					// Verify the calculated surplus contribution value is same as actual value on
					// quote document
					Assertions.verify(
							viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",",
									""),
							df.format(surpluContributionValue), "View/Print Full Quote Page",
							"Actual and Calculated Surplus Contribution values are matching and the value is "
									+ viewOrPrintFullQuotePage.surplusContributionValue.getData(),
							false, false);

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
				} else if (testData.get("QuoteState").contains("MO") || testData.get("QuoteState").contains("AR")) {
					sltfValueCalculated = (premiumValue + inspectionFee + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Inspection Fee is " + viewOrPrintFullQuotePage.inspectionFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());

					// Verify the calculated surplus contribution value is same as actual value on
					// quote document
					Assertions.verify(
							viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",",
									""),
							df.format(surpluContributionValue), "View/Print Full Quote Page",
							"Actual and Calculated Surplus Contribution values are matching and the value is "
									+ viewOrPrintFullQuotePage.surplusContributionValue.getData(),
							false, false);

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

					// Verify the calculated surplus contribution value is same as actual value on
					// quote document
					Assertions.verify(
							viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",",
									""),
							df.format(surpluContributionValue), "View/Print Full Quote Page",
							"Actual and Calculated Surplus Contribution values are matching and the value is "
									+ viewOrPrintFullQuotePage.surplusContributionValue.getData(),
							false, false);

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
				} else if (testData.get("QuoteState").contains("AZ") || testData.get("QuoteState").contains("NV")) {
					sltfValueCalculated = (premiumValue + inspectionFee + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					stampingFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "").replace(",", ""));
					stampingFeeCalculated = (premiumValue + inspectionFee + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("StampingFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Inspection Fee is " + viewOrPrintFullQuotePage.inspectionFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());

					// Verify the calculated surplus contribution value is same as actual value on
					// quote document
					Assertions.verify(
							viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",",
									""),
							df.format(surpluContributionValue), "View/Print Full Quote Page",
							"Actual and Calculated Surplus Contribution values are matching and the value is "
									+ viewOrPrintFullQuotePage.surplusContributionValue.getData(),
							false, false);

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
				} else if (testData.get("QuoteState").contains("MS")) {
					sltfValueCalculated = (premiumValue + inspectionFee + policyFee)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					stampingFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "").replace(",", ""));
					stampingFeeCalculated = (premiumValue + inspectionFee + policyFee)
							* (Double.parseDouble(testData.get("StampingFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Inspection Fee is " + viewOrPrintFullQuotePage.inspectionFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());

					// Verify the calculated surplus contribution value is same as actual value on
					// quote document
					Assertions.verify(
							viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",",
									""),
							df.format(surpluContributionValue), "View/Print Full Quote Page",
							"Actual and Calculated Surplus Contribution values are matching and the value is "
									+ viewOrPrintFullQuotePage.surplusContributionValue.getData(),
							false, false);

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
						mwuaFeeCalculated = (premiumValue + inspectionFee + policyFee)
								* (Double.parseDouble(testData.get("MWUAFeeValue"))) / 100;

						// Verify the calculated surplus contribution value is same as actual value on
						// quote document
						Assertions.verify(
								viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(
										",", ""),
								df.format(surpluContributionValue), "View/Print Full Quote Page",
								"Actual and Calculated Surplus Contribution values are matching and the value is "
										+ viewOrPrintFullQuotePage.surplusContributionValue.getData(),
								false, false);

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

				} else if (testData.get("QuoteState").contains("NC") || testData.get("QuoteState").contains("TX")
						|| testData.get("QuoteState").contains("UT")) {
					sltfValueCalculated = (premiumValue + inspectionFee + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					stampingFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "").replace(",", ""));
					stampingFeeCalculated = (premiumValue + inspectionFee + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("StampingFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Inspection Fee is " + viewOrPrintFullQuotePage.inspectionFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());

					// Verify the calculated surplus contribution value is same as actual value on
					// quote document
					Assertions.verify(
							viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",",
									""),
							df.format(surpluContributionValue), "View/Print Full Quote Page",
							"Actual and Calculated Surplus Contribution values are matching and the value is "
									+ viewOrPrintFullQuotePage.surplusContributionValue.getData(),
							false, false);

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
				} else if (testData.get("QuoteState").contains("FL")) {
					sltfValueCalculated = (premiumValue + inspectionFee + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					fslsoServiceFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.fslsoServiceFee.getData().replace("$", "").replace(",", ""));
					fslsoServiceFeeCalculated = (premiumValue + inspectionFee + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("FSLSOServiceFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Inspection Fee is " + viewOrPrintFullQuotePage.inspectionFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());

					// Verify the calculated surplus contribution value is same as actual value on
					// quote document
					Assertions.verify(
							viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",",
									""),
							df.format(surpluContributionValue), "View/Print Full Quote Page",
							"Actual and Calculated Surplus Contribution values are matching and the value is "
									+ viewOrPrintFullQuotePage.surplusContributionValue.getData(),
							false, false);

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
					sltfValueCalculated = (premiumValue + inspectionFee + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
					transactionFeeActual = Double.parseDouble(
							viewOrPrintFullQuotePage.transactionFee.getData().replace("$", "").replace(",", ""));
					transactionFeeCalculated = (premiumValue + inspectionFee + policyFee + surpluContributionValue)
							* (Double.parseDouble(testData.get("TransactionFeeValue"))) / 100;
					Assertions.passTest("View/Print Full Quote Page",
							"Premium is " + viewOrPrintFullQuotePage.premiumValue.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Inspection Fee is " + viewOrPrintFullQuotePage.inspectionFee.getData());
					Assertions.passTest("View/Print Full Quote Page",
							"Policy Fee is " + viewOrPrintFullQuotePage.policyFee.getData());

					// Verify the calculated surplus contribution value is same as actual value on
					// quote document
					Assertions.verify(
							viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "").replace(",",
									""),
							df.format(surpluContributionValue), "View/Print Full Quote Page",
							"Actual and Calculated Surplus Contribution values are matching and the value is "
									+ viewOrPrintFullQuotePage.surplusContributionValue.getData(),
							false, false);

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
				Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

				// click on back
				viewOrPrintFullQuotePage.scrollToTopPage();
				viewOrPrintFullQuotePage.waitTime(2);// need wait to scroll to top page
				viewOrPrintFullQuotePage.backButton.click();

				// Calculate SLTF on account overview page
				Assertions.addInfo("Scenario 02",
						"Calculating SLTF values,StampingFeeValue,EMPA,MWUA Fees for all 14 states on Account Overview Page");
				if (testData.get("QuoteState").contains("NJ")) {
					sltfValueCalculatedAcc = (premiumValueAcc + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
				} else {
					sltfValueCalculatedAcc = (premiumValueAcc + feeValueAcc + surpluContributionValue)
							* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
				}
				totalPremiumValueCalculatedAcc = premiumValueAcc + feeValueAcc + sltfValueActualAcc
						+ surpluContributionValue;
				Assertions.passTest("Account Overview Page",
						"Premium is " + accountOverviewPage.premiumValue.getData());
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
				Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Writing values to excel sheet
				testDataFilePath = EnvironmentDetails.getEnvironmentDetails()
						.getString("CommRetailNBRegressionTestDataFilePath");
				testDataSheetName = EnvironmentDetails.getEnvironmentDetails()
						.getString("CommRetailNBRegressionTestDataSheetName");
				testDataSheet = new SheetMatchedAccessManager(testDataFilePath + "NBTCID023.xls", testDataSheetName);
				testDataSheet.setExcelRowData(1, i + 1, quoteNumber);
			}

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC009 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC009 ", "Executed Successfully");
			}
		}
	}
}
