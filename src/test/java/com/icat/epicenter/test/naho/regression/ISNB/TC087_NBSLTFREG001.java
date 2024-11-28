package com.icat.epicenter.test.naho.regression.ISNB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

public class TC087_NBSLTFREG001 extends AbstractNAHOTest {

	public TC087_NBSLTFREG001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBSLTFREG001.xls";
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
		String quotePremium;
		double d_premium;
		double i_fees;
		double d_surplusLinesTaxesPercentage;
		double d_stampingFeePercentage;
		double d_surplusLinesTaxes;
		double d_stampingFee;
		double d_surplusContributionValue;
		String actualSLTFValue;
		String calculatedSLTFValue;
		String actualSLTValue;
		String calculatedSLTValue;
		String actualStampingFeeValue;
		String calculatedStampingFeeValue;
		BigDecimal SLTFRoundOff;
		BigDecimal surplusLinesTaxesRoundOff;
		BigDecimal stampingFeeRoundOff;

		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
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

			// Getting sltf value from account overview page
			actualSLTFValue = accountOverviewPage.sltfValue.getData();

			// Getting premium value from account overview page
			premium = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// Getting fees value from account overview page
			fees = accountOverviewPage.feesValue.getData().replace("$", "");

			// Getting surplus contribution value
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace("%", "").replace(",", "");

			// Getting surplusLinesTaxesPercentage and stampingFeePercentage from test data
			surplusLinesTaxesPercentage = testData.get("SurplusLinesTaxesPercentage");
			stampingFeePercentage = testData.get("StampingFeePercentage");

			// Conversion of String to double/int to calculate sltf
			d_premium = Double.parseDouble(premium);
			i_fees = Double.parseDouble(fees);
			d_surplusLinesTaxesPercentage = Double.parseDouble(surplusLinesTaxesPercentage);
			d_stampingFeePercentage = Double.parseDouble(stampingFeePercentage);
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// Calculate Surplus Lines Taxes
			// =(Premium+Fees+SurpluesContribution)*surplusLinesTaxesPercentage(0.0485)
			d_surplusLinesTaxes = (d_premium + i_fees + d_surplusContributionValue) * d_surplusLinesTaxesPercentage;

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
			SLTFRoundOff = BigDecimal
					.valueOf(surplusLinesTaxesRoundOff.doubleValue() + stampingFeeRoundOff.doubleValue());
			SLTFRoundOff = SLTFRoundOff.setScale(2, RoundingMode.HALF_UP);
			calculatedSLTFValue = format.format(SLTFRoundOff);

			// Verify actual and calculated SLTF values are equal
			if (actualSLTFValue != null && calculatedSLTFValue != null) {
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + actualSLTFValue);
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes and Fees : " + calculatedSLTFValue);
				Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true, "Account Overview Page",
						"The SLTF value is calculated correctly as per the TX percentage", false, false);
			}

			// Getting quote premium from quote tree
			quotePremium = accountOverviewPage.quotePremium.formatDynamicPath(testData.get("QuoteCount")).getData();
			Assertions.verify(!(quotePremium.isEmpty()), true, "Account Overview Page",
					"Premium in Quote tree : " + quotePremium, false, false);

			// ViewPrintFullQuote
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.waitTillElementisEnabled(60);
			accountOverviewPage.viewPrintFullQuoteLink.waitTillButtonIsClickable(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("View Print Full Quote Page", "View Print Full Quote Page loaded succesfully");
			viewOrPrintFullPage.backButton.waitTillVisibilityOfElement(60);
			viewOrPrintFullPage.backButton.waitTillPresenceOfElement(60);

			// Getting SurplusLinesTaxes value
			viewOrPrintFullPage.surplusLinesTaxesValue.scrollToElement();
			viewOrPrintFullPage.surplusLinesTaxesValue.waitTillVisibilityOfElement(60);
			viewOrPrintFullPage.surplusLinesTaxesValue.waitTillElementisEnabled(60);
			actualSLTValue = viewOrPrintFullPage.surplusLinesTaxNaho.getData();

			// Converting SLT value back to String
			calculatedSLTValue = format.format(surplusLinesTaxesRoundOff);

			// Verify actual and calculated SLT values are equal
			if (actualSLTValue != null && calculatedSLTValue != null) {
				Assertions.passTest("View Print Full Quote Page", "Actual Surplus Lines Taxes : " + actualSLTValue);
				Assertions.passTest("View Print Full Quote Page",
						"Calculated Surplus Lines Taxes : " + calculatedSLTValue);
				Assertions.verify(actualSLTValue.equalsIgnoreCase(calculatedSLTValue), true,
						"View Print Full Quote Page",
						"Surplus Lines Taxes is displayed correctly as per SLT percentage 4.85%", false, false);
			}

			// Getting Stamping fee value
			actualStampingFeeValue = viewOrPrintFullPage.stampingFeeNaho.getData();

			// Converting stamping fee back to String
			calculatedStampingFeeValue = format.format(stampingFeeRoundOff);

			// Verify actual and calculated Stamping values are equal
			if (actualStampingFeeValue != null && calculatedStampingFeeValue != null) {
				Assertions.passTest("View Print Full Quote Page", "Actual Stamping Fee : " + actualStampingFeeValue);
				Assertions.passTest("View Print Full Quote Page",
						"Calculated Stamping Fee : " + calculatedStampingFeeValue);
				Assertions.verify(actualStampingFeeValue.equalsIgnoreCase(calculatedStampingFeeValue), true,
						"View Print Full Quote Page",
						"Stamping Fee is displayed correctly as per TX stamping percentage 0.15%", false, false);
			}

			// Go to HomePage
			viewOrPrintFullPage.scrollToTopPage();
			homePage.goToHomepage.waitTillVisibilityOfElement(60);
			homePage.goToHomepage.waitTillElementisEnabled(60);
			homePage.goToHomepage.waitTillButtonIsClickable(60);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC087 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC087 ", "Executed Successfully");
			}
		}
	}
}
