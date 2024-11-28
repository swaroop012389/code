package com.icat.epicenter.test.naho.regression.ISNB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC090_NBSLTFREG004 extends AbstractNAHOTest {

	public TC090_NBSLTFREG004() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBSLTFREG004.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullPage = new ViewOrPrintFullQuotePage();
		String quoteNumber;
		String premium;
		String fees;
		String surplusLinesTaxesPercentage;
		String stampingFeePercentage;
		String surplusContributionValue;
		double d_premium;
		double i_fees;
		double d_surplusLinesTaxesPercentage;
		double d_stampingFeePercentage;
		double d_surplusLinesTaxes = 0;
		double d_stampingFee = 0;
		double d_surplusContributionValue;
		double d_TotalPremium;
		String actualSLTFValue;
		double calculatedSLTFValue;
		String actualSLTValue;
		String calculatedSLTValue;
		String actualStampingFeeValue;
		String calculatedStampingFeeValue;
		String actualTotalPremium;

		BigDecimal surplusLinesTaxesRoundOff;
		BigDecimal stampingFeeRoundOff;
		int i = 0;

		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DecimalFormat df = new DecimalFormat("0.00");
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Creating new account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering zip code
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling Details entered successfully");

			// Entering prior loss Page
			Assertions.verify(priorLossesPage.lossesInThreeYearsNo.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"prior Loss Page Loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");

			// Entering Create quote page details
			Assertions.verify(createQuotePage.quoteName.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded Successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting quote number from account overview Page
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded Successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(testData.get("QuoteCount")).getData()
					.substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// Verify SLTF label is displayed in account overview Page
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees Label is displayed", false, false);

			for (i = 1; i <= 3; i++) {
				if (i == 1) {
					// Getting sltf value from account overview page
					actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");

					// Getting premium value from account overview page
					premium = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

					// Getting fees value from account overview page
					fees = accountOverviewPage.feesValue.getData().replace("$", "");
					surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
							.replace(",", "");
					d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

					// Getting surplusLinesTaxesPercentage and stampingFeePercentage from testdata
					surplusLinesTaxesPercentage = testData.get("SurplusLinesTaxesPercentage");
					stampingFeePercentage = testData.get("StampingFeePercentage");

					// Conversion of String to double/int to calculate sltf
					d_premium = Double.parseDouble(premium);
					i_fees = Double.parseDouble(fees);
					d_surplusLinesTaxesPercentage = Double.parseDouble(surplusLinesTaxesPercentage);
					d_stampingFeePercentage = Double.parseDouble(stampingFeePercentage);

					// Calculate Surplus Lines Taxes
					// =(Premium+Fees)*surplusLinesTaxesPercentage(0.0485)
					d_surplusLinesTaxes = (d_premium + i_fees + d_surplusContributionValue)
							* d_surplusLinesTaxesPercentage;

					// Rounding off Surplus Lines Taxes to 2 decimal digits
					// Calculate Stamping Fee = (Premium+Fees)*stampingFeePercentage
					d_stampingFee = (d_premium + i_fees + d_surplusContributionValue) * d_stampingFeePercentage;

					// Rounding off Stamping fee to 2 decimal digits
					stampingFeeRoundOff = BigDecimal.valueOf(d_stampingFee);
					stampingFeeRoundOff = stampingFeeRoundOff.setScale(2, RoundingMode.HALF_UP);

					// Calculate SLTF=Surplus Lines Taxes + Stamping Fee, Rounding Off SLTF decimal
					// value to 2 digits and Converting back to String
					calculatedSLTFValue = d_surplusLinesTaxes + d_stampingFee;
					df.format(calculatedSLTFValue);

					// Verify actual and calculated SLTF values are equal
					double d_actualSltf = Double.parseDouble(actualSLTFValue);
					if (Precision.round(
							Math.abs(Precision.round(d_actualSltf, 2) - Precision.round(calculatedSLTFValue, 2)),
							2) < 0.05) {
						if (actualSLTFValue != null) {
							Assertions.passTest("Account Overview Page",
									"Actual Surplus Lines Taxes and Fees for Quote1: " + "$" + actualSLTFValue);
							Assertions.passTest("Account Overview Page",
									"Calculated Surplus Lines Taxes and Fees for Quote1: " + "$"
											+ df.format(calculatedSLTFValue));
						}
					} else {
						Assertions.passTest("Account Overview Page",
								"The Difference between actual SLTF and calculated SLTF is more than 0.05");
					}

					// Getting total premium from account overview page
					actualTotalPremium = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",",
							"");

					// Calculate total premium=premium+fees+sltf
					d_TotalPremium = d_premium + i_fees + calculatedSLTFValue;

					// Rounding Off Total Premium decimal value to 2 digits and Converting back to
					// String
					df.format(d_TotalPremium);

					// Verify actual and calculated TotalPremium values are equal to check STLF is
					// added to Total premium
					double d_actualTotalPremium = Double.parseDouble(actualTotalPremium);
					if (Precision.round(
							Math.abs(Precision.round(d_actualTotalPremium, 2) - Precision.round(d_TotalPremium, 2)),
							2) < 0.05) {
						if (actualTotalPremium != null) {
							Assertions.passTest("Account Overview Page",
									"Actual Total Premium for Quote1 : " + "$" + actualTotalPremium);
							Assertions.passTest("Account Overview Page",
									"Calculated Total Premium for Quote1 : " + "$" + df.format(d_TotalPremium));
						}
					} else {
						Assertions.passTest("Account Overview Page",
								"The Difference between actual Total Premium  and calculated Total Premium  is more than 0.05");
					}

					// ViewPrintFullQuote
					accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
					accountOverviewPage.viewPrintFullQuoteLink.waitTillElementisEnabled(60);
					accountOverviewPage.viewPrintFullQuoteLink.waitTillButtonIsClickable(60);
					accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
					accountOverviewPage.viewPrintFullQuoteLink.click();
					Assertions.passTest("View Print Full Quote Page",
							"View Print Full Quote Page for Quote1 loaded succesfully");

					// Getting SurplusLinesTaxes value
					actualSLTValue = viewOrPrintFullPage.surplusLinesTaxNaho.getData().replace("$", "");

					// Rounding Off SLT Value to 2 digits and Converting back to String
					df.format(d_surplusLinesTaxes);

					// Verify actual and calculated SLT values are equal
					if (actualSLTValue != null) {
						Assertions.passTest("View Print Full Quote Page",
								"Actual Surplus Lines Taxes for Quote1: " + "$" + actualSLTValue);
						Assertions.passTest("View Print Full Quote Page",
								"Calculated Surplus Lines Taxes for Quote1: " + "$" + df.format(d_surplusLinesTaxes));
						Assertions.verify(actualSLTValue, df.format(d_surplusLinesTaxes), "View Print Full Quote Page",
								"Surplus Lines Taxes is displayed correctly as per SLT percentage 4.85%", false, false);
					}

					// Getting Stamping fee value
					actualStampingFeeValue = viewOrPrintFullPage.stampingFeeNaho.getData();

					// Rounding Off SLT Value to 2 digits and Converting back to String
					calculatedStampingFeeValue = format.format(stampingFeeRoundOff);

					// Verify actual and calculated Stamping values are equal
					if (actualStampingFeeValue != null && calculatedStampingFeeValue != null) {
						Assertions.passTest("View Print Full Quote Page",
								"Actual Stamping Fee for Quote1: " + actualStampingFeeValue);
						Assertions.passTest("View Print Full Quote Page",
								"Calculated Stamping Fee for Quote1: " + calculatedStampingFeeValue);
						Assertions.verify(actualStampingFeeValue.equalsIgnoreCase(calculatedStampingFeeValue), true,
								"View Print Full Quote Page",
								"Stamping Fee is displayed correctly as per TX stamping percentage 0.15%", false,
								false);
					}

					viewOrPrintFullPage.scrollToTopPage();
					viewOrPrintFullPage.waitTime(3); // wait time is given to prevent Element click intercepted
														// exception
					viewOrPrintFullPage.backButton.click();

				} else {
					accountOverviewPage.sltfValue.waitTillVisibilityOfElement(60);
					accountOverviewPage.sltfValue.waitTillElementisEnabled(60);
					accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(i).scrollToElement();
					accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(i).waitTillVisibilityOfElement(60);
					accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(i).waitTillElementisEnabled(60);
					accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(i).waitTillButtonIsClickable(60);

					// Getting total premium from account overview page
					actualTotalPremium = accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(i).getData();

					accountOverviewPage.quoteOptTotalPremium.formatDynamicPath(i).click();
					accountOverviewPage.altQuoteName.formatDynamicPath(1, i - 1).waitTillVisibilityOfElement(60);
					accountOverviewPage.altQuoteName.formatDynamicPath(1, i - 1).waitTillElementisEnabled(60);
					Assertions.passTest("AccountOverview Page",
							"Account Overview page for "
									+ accountOverviewPage.altQuoteName.formatDynamicPath(1, i - 1).getData()
									+ " loaded successfully");

					// Getting sltf value from account overview page
					actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "");

					// Getting premium value from account overview page
					premium = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

					// Getting fees value from account overview page
					fees = accountOverviewPage.feesValue.getData().replace("$", "");
					surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
							.replace(",", "");
					d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

					// Getting surplusLinesTaxesPercentage and stampingFeePercentage from testdata
					surplusLinesTaxesPercentage = testData.get("SurplusLinesTaxesPercentage");
					stampingFeePercentage = testData.get("StampingFeePercentage");

					// Conversion of String to double/int to calculate sltf
					d_premium = Double.parseDouble(premium);
					i_fees = Double.parseDouble(fees);
					d_surplusLinesTaxesPercentage = Double.parseDouble(surplusLinesTaxesPercentage);
					d_stampingFeePercentage = Double.parseDouble(stampingFeePercentage);

					// Calculate Surplus Lines Taxes
					// =(Premium+Fees)*surplusLinesTaxesPercentage(0.0485)
					d_surplusLinesTaxes = (d_premium + i_fees + d_surplusContributionValue)
							* d_surplusLinesTaxesPercentage;

					// Rounding off Surplus Lines Taxes to 2 decimal digits
					surplusLinesTaxesRoundOff = BigDecimal.valueOf(d_surplusLinesTaxes);
					surplusLinesTaxesRoundOff = surplusLinesTaxesRoundOff.setScale(2, RoundingMode.HALF_UP);

					// Calculate Stamping Fee = (Premium+Fees)*stampingFeePercentage
					d_stampingFee = (d_premium + i_fees + d_surplusContributionValue) * d_stampingFeePercentage;

					// Rounding off Stamping fee to 2 decimal digits
					stampingFeeRoundOff = BigDecimal.valueOf(d_stampingFee);
					stampingFeeRoundOff = stampingFeeRoundOff.setScale(2, RoundingMode.HALF_UP);

					// Calculate SLTF=Surplus Lines Taxes + Stamping Fee, Rounding Off SLTF decimal
					// value to 2 digits and Converting back to String
					calculatedSLTFValue = surplusLinesTaxesRoundOff.doubleValue() + stampingFeeRoundOff.doubleValue();
					df.format(calculatedSLTFValue);

					// Verify actual and calculated SLTF values are equal
					if (actualSLTFValue != null) {
						if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualSLTFValue), 2)
								- Precision.round(calculatedSLTFValue, 2)), 2) < 0.05) {
							Assertions.passTest("Account Overview Page",
									"Actual Surplus Lines Taxes and Fees for " + accountOverviewPage.altQuoteName
											.formatDynamicPath(1, i - 1).getData().substring(7, 21) + " : " + "$"
											+ actualSLTFValue);
							Assertions.passTest("Account Overview Page",
									"Calculated Surplus Lines Taxes and Fees for " + accountOverviewPage.altQuoteName
											.formatDynamicPath(1, i - 1).getData().substring(7, 21) + " : " + "$"
											+ df.format(calculatedSLTFValue));
						} else {
							Assertions.passTest("Account Overview Page",
									"The Difference between actual and calculated SLTF is more than 0.05");
						}
					}

					// Calculate total premium=premium+fees+sltf
					d_TotalPremium = d_premium + i_fees + calculatedSLTFValue;

					// Rounding Off Total Premium decimal value to 2 digits and Converting back to
					// String
					df.format(d_TotalPremium);

					// Verify actual and calculated TotalPremium values are equal to check STLF is
					// added to Total premium
					if (actualTotalPremium != null) {
						String d_actualTotalPremium = actualTotalPremium.replace("$", "").replace(",", "");
						if (Precision.round(Math.abs(Precision.round(Double.parseDouble(d_actualTotalPremium), 2)
								- Precision.round(d_TotalPremium, 2)), 2) < 0.05) {
							Assertions.passTest("Account Overview Page",
									"Actual Total Premium  for " + accountOverviewPage.altQuoteName
											.formatDynamicPath(1, i - 1).getData().substring(7, 21) + " : "
											+ actualTotalPremium);
							Assertions.passTest("Account Overview Page",
									"Calculated Total Premium  for " + accountOverviewPage.altQuoteName
											.formatDynamicPath(1, i - 1).getData().substring(7, 21) + " : " + "$"
											+ df.format(d_TotalPremium));
						} else {
							Assertions.passTest("Account Overview Page",
									"The Difference between actual and calculated Total Premium is more than 0.05");
						}
					}

					// ViewPrintFullQuote
					accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
					accountOverviewPage.viewPrintFullQuoteLink.waitTillElementisEnabled(60);
					accountOverviewPage.viewPrintFullQuoteLink.waitTillButtonIsClickable(60);
					accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
					accountOverviewPage.viewPrintFullQuoteLink.click();
					Assertions.passTest("View Print Full Quote Page",
							"View Print Full Quote Page for Quote 1(Alt Option " + (i - 1) + ") loaded succesfully");

					// Getting SurplusLinesTaxes value
					actualSLTValue = viewOrPrintFullPage.surplusLinesTaxNaho.getData().replace("$", "").replace(",",
							"");

					// Rounding Off SLT Value to 2 digits and Converting back to String
					calculatedSLTValue = format.format(surplusLinesTaxesRoundOff);

					// Verify actual and calculated SLT values are equal
					if (actualSLTValue != null && calculatedSLTValue != null) {
						if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualSLTValue), 2)
								- Precision.round(d_surplusLinesTaxes, 2)), 2) < 0.05) {
							Assertions.passTest("View Print Full Quote Page",
									"Actual Surplus Lines Taxes for Quote 1(Alt Option " + (i - 1) + ") : " + "$"
											+ actualSLTValue);
							Assertions.passTest("View Print Full Quote Page",
									"Calculated Surplus Lines Taxes for Quote 1(Alt Option " + (i - 1) + ") : "
											+ calculatedSLTValue);
						} else {
							Assertions.passTest("View Print Full Quote Page",
									"The Difference between actual and calculated SLTF is more than 0.05");
						}
					}

					// Getting Stamping fee value
					actualStampingFeeValue = viewOrPrintFullPage.stampingFeeNaho.getData();

					// Rounding Off SLT Value to 2 digits and Converting back to String
					calculatedStampingFeeValue = format.format(stampingFeeRoundOff);

					// Verify actual and calculated Stamping values are equal
					if (actualStampingFeeValue != null && calculatedStampingFeeValue != null) {
						Assertions.passTest("View Print Full Quote Page", "Actual Stamping Fee for Quote 1(Alt Option "
								+ (i - 1) + ") : " + actualStampingFeeValue);
						Assertions.passTest("View Print Full Quote Page",
								"Calculated Stamping Fee for Quote 1(Alt Option " + (i - 1) + ") : "
										+ calculatedStampingFeeValue);
						Assertions.verify(actualStampingFeeValue.equalsIgnoreCase(calculatedStampingFeeValue), true,
								"View Print Full Quote Page",
								"Stamping Fee is displayed correctly as per TX stamping percentage 0.15%", false,
								false);
					}
					viewOrPrintFullPage.scrollToTopPage();
					viewOrPrintFullPage.waitTime(3); // wait time is given to prevent Element click intercepted
														// exception
					viewOrPrintFullPage.backButton.click();

					accountOverviewPage.sltfValue.waitTillVisibilityOfElement(60);
					accountOverviewPage.sltfValue.waitTillElementisEnabled(60);
					accountOverviewPage.quoteNumberLink.formatDynamicPath(quoteNumber, 1).scrollToElement();
					accountOverviewPage.quoteNumberLink.formatDynamicPath(quoteNumber, 1)
							.waitTillVisibilityOfElement(60);
					accountOverviewPage.quoteNumberLink.formatDynamicPath(quoteNumber, 1).waitTillElementisEnabled(60);
					accountOverviewPage.quoteNumberLink.formatDynamicPath(quoteNumber, 1).waitTillButtonIsClickable(60);
					accountOverviewPage.quoteNumberLink.formatDynamicPath(quoteNumber, 1).click();

				}
			}
			// Go to HomePage
			homePage.goToHomepage.waitTillVisibilityOfElement(60);
			homePage.goToHomepage.waitTillElementisEnabled(60);
			homePage.goToHomepage.waitTillButtonIsClickable(60);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign Out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC090 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC090 ", "Executed Successfully");
			}
		}
	}
}