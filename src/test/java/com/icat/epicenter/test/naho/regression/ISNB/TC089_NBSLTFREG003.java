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
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC089_NBSLTFREG003 extends AbstractNAHOTest {

	public TC089_NBSLTFREG003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBSLTFREG003.xls";
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
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		String quoteNumber;
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		String premium;
		String fees;
		String surplusLinesTaxesPercentage;
		String surplusContributionValue;
		double d_surplusContributionValue;
		String totalPremium;
		double d_premium;
		double i_fees;
		double d_surplusLinesTaxesPercentage;
		double d_surplusLinesTaxesFees;
		String actualSLTFValue;
		String calculatedSLTFValue;
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
			Assertions.passTest("Account Overview Page", "Quote Number 1 : " + quoteNumber);

			// Getting total premium from quote tree
			totalPremium = accountOverviewPage.totalPremiumValue.formatDynamicPath(testData.get("QuoteCount"))
					.getData();
			Assertions.verify(!(totalPremium.isEmpty()), true, "Account Overview Page",
					"Total premium for Quote Number 1 : " + totalPremium, false, false);

			// Verify SLTF label is displayed in account overview Page
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees Label is displayed", false, false);

			// Getting premium value from account overview page
			premium = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// Getting fees value from account overview page
			fees = accountOverviewPage.feesValue.getData().replace("$", "");

			// Getting surplusLinesTaxesPercentage from testdata
			surplusLinesTaxesPercentage = testData.get("SurplusLinesTaxesPercentage");

			// ViewPrintFullQuote
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.waitTillElementisEnabled(60);
			accountOverviewPage.viewPrintFullQuoteLink.waitTillButtonIsClickable(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("View Print Full Quote Page", "View Print Full Quote Page loaded succesfully");

			// Getting actual sltf value from ViewPrintFullQuote
			String SLTFValue = viewOrPrintFullPage.surplusLinesTaxNaho.getData().replaceAll("[^\\d-.]", "");
			Double d_SLTFValue = Double.parseDouble(SLTFValue);
			actualSLTFValue = format.format(d_SLTFValue);
			surplusContributionValue = viewOrPrintFullPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// Conversion String to double/int to calculate sltf
			d_premium = Double.parseDouble(premium);
			i_fees = Double.parseDouble(fees);
			d_surplusLinesTaxesPercentage = Double.parseDouble(surplusLinesTaxesPercentage);

			// Calculate SLTF = (Premium+Fees)*surplusLinesTaxesPercentage(0.06)
			d_surplusLinesTaxesFees = (d_premium + i_fees + d_surplusContributionValue) * d_surplusLinesTaxesPercentage;

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			BigDecimal SLTFRoundOff = BigDecimal.valueOf(d_surplusLinesTaxesFees);
			SLTFRoundOff = SLTFRoundOff.setScale(2, RoundingMode.HALF_UP);
			calculatedSLTFValue = format.format(SLTFRoundOff);

			// Verify actual and calculated SLTF values are equal
			if (actualSLTFValue != null && calculatedSLTFValue != null) {
				Assertions.passTest("View Print Full Quote Page",
						"Actual Surplus Lines Taxes and Fees : " + actualSLTFValue);
				Assertions.passTest("View Print Full Quote Page",
						"Calculated Surplus Lines Taxes and Fees : " + calculatedSLTFValue);
				Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true,
						"View Print Full Quote Page",
						"The Surplus Lines Taxes is displayed correctly as per SLT percentage (0.06)", false, false);
			}

			viewOrPrintFullPage.scrollToTopPage();
			viewOrPrintFullPage.waitTime(3); // wait time is given to prevent Element click intercepted exception
			viewOrPrintFullPage.backButton.click();

			// Account Overview Page- Create quote2
			testData = data.get(data_Value2);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.waitTillVisibilityOfElement(60);
			accountOverviewPage.createAnotherQuote.waitTillElementisEnabled(60);
			accountOverviewPage.createAnotherQuote.waitTillButtonIsClickable(60);
			accountOverviewPage.createAnotherQuote.click();

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// CreateQuotePage
			Assertions.verify(createQuotePage.quoteName.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded Successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting quote number from account overview Page
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded Successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(testData.get("QuoteCount")).getData()
					.substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number 2 :" + quoteNumber);

			// Getting total premium from quote tree
			totalPremium = accountOverviewPage.totalPremiumValue.formatDynamicPath(testData.get("QuoteCount"))
					.getData();
			Assertions.verify(!(totalPremium.isEmpty()), true, "Account Overview Page",
					"Total Quote premium displayed for Quote2 is " + totalPremium, false, false);

			// Verify SLTF label is displayed in account overview Page
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees Label is displayed", false, false);

			// Getting premium value from account overview page
			premium = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// Getting fees value from account overview page
			fees = accountOverviewPage.feesValue.getData().replace("$", "");

			// Getting sltf value from account overview page
			actualSLTFValue = accountOverviewPage.sltfValue.getData();
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "");
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// ViewPrintFullQuote
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.waitTillElementisEnabled(60);
			accountOverviewPage.viewPrintFullQuoteLink.waitTillButtonIsClickable(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("View Print Full Quote Page", "View Print Full Quote Page loaded succesfully");
			viewOrPrintFullPage.backButton.waitTillVisibilityOfElement(60);
			viewOrPrintFullPage.backButton.waitTillPresenceOfElement(60);

			// Getting actual sltf value from ViewPrintFullQuote
			viewOrPrintFullPage.surplusLinesTaxesValue.scrollToElement();
			viewOrPrintFullPage.surplusLinesTaxesValue.waitTillVisibilityOfElement(60);
			viewOrPrintFullPage.surplusLinesTaxesValue.waitTillElementisEnabled(60);

			// Conversion of String to double/int to calculate sltf
			d_premium = Double.parseDouble(premium);
			i_fees = Double.parseDouble(fees);
			d_surplusLinesTaxesPercentage = Double.parseDouble(surplusLinesTaxesPercentage);

			// Calculate SLTF = (Premium+Fees)*surplusLinesTaxesPercentage(0.06)
			d_surplusLinesTaxesFees = (d_premium + i_fees + d_surplusContributionValue) * d_surplusLinesTaxesPercentage;

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			SLTFRoundOff = BigDecimal.valueOf(d_surplusLinesTaxesFees);
			SLTFRoundOff = SLTFRoundOff.setScale(2, RoundingMode.HALF_UP);
			calculatedSLTFValue = format.format(SLTFRoundOff);

			// Verify actual and calculated SLTF values are equal
			if (actualSLTFValue != null && calculatedSLTFValue != null) {
				Assertions.passTest("View Print Full Quote Page",
						"Actual Surplus Lines Taxes and Fees : " + actualSLTFValue);
				Assertions.passTest("View Print Full Quote Page",
						"Calculated Surplus Lines Taxes and Fees : " + calculatedSLTFValue);
				Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true,
						"View Print Full Quote Page",
						"The Surplus Lines Taxes is displayed correctly as per SLT percentage (0.06)", false, false);
			}

			viewOrPrintFullPage.scrollToTopPage();
			viewOrPrintFullPage.waitTime(3); // wait time is given to prevent Element click intercepted exception
			viewOrPrintFullPage.backButton.click();

			// Create quote3 by editing quote 2 deductibles
			testData = data.get(data_Value3);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.waitTillVisibilityOfElement(60);
			accountOverviewPage.editDeductibleAndLimits.waitTillElementisEnabled(60);
			accountOverviewPage.editDeductibleAndLimits.waitTillButtonIsClickable(60);
			accountOverviewPage.editDeductibleAndLimits.click();

			// CreateQuotePage
			Assertions.verify(createQuotePage.quoteName.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page Loaded Successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Account Overview Page
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded Successfully", false, false);
			// Getting quote number from account overview Page
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(testData.get("QuoteCount")).getData()
					.substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number 3 : " + quoteNumber);

			// Verify SLTF label is displayed in account overview Page
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees Label is displayed", false, false);

			// Getting premium value from account overview page
			premium = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// Getting fees value from account overview page
			fees = accountOverviewPage.feesValue.getData().replace("$", "");

			// ViewPrintFullQuote
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.waitTillElementisEnabled(60);
			accountOverviewPage.viewPrintFullQuoteLink.waitTillButtonIsClickable(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("View Print Full Quote Page", "View Print Full Quote Page loaded succesfully");
			viewOrPrintFullPage.totalPremiumValue.waitTillVisibilityOfElement(60);
			viewOrPrintFullPage.totalPremiumValue.waitTillPresenceOfElement(60);

			// Getting total premium from ViewPrintFullQuote
			totalPremium = viewOrPrintFullPage.totalPremiumValue.getData();
			Assertions.verify(!(totalPremium.isEmpty()), true, "View Print Full Quote Page",
					"Total Quote premium displayed for Quote3 is " + totalPremium, false, false);

			// SurplusLinesTaxes Verification
			SLTFValue = viewOrPrintFullPage.surplusLinesTaxNaho.getData().replaceAll("[^\\d-.]", "");
			d_SLTFValue = Double.parseDouble(SLTFValue);
			actualSLTFValue = format.format(d_SLTFValue);
			surplusContributionValue = viewOrPrintFullPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);

			// Conversion String to double/int to calculate sltf
			d_premium = Double.parseDouble(premium);
			i_fees = Double.parseDouble(fees);
			d_surplusLinesTaxesPercentage = Double.parseDouble(surplusLinesTaxesPercentage);

			// Calculate SLTF = (Premium+Fees)*surplusLinesTaxesPercentage(0.06)
			d_surplusLinesTaxesFees = (d_premium + i_fees + d_surplusContributionValue) * d_surplusLinesTaxesPercentage;

			// Rounding Off SLTF decimal value to 2 digits and Converting back to String
			SLTFRoundOff = BigDecimal.valueOf(d_surplusLinesTaxesFees);
			SLTFRoundOff = SLTFRoundOff.setScale(2, RoundingMode.HALF_UP);
			calculatedSLTFValue = format.format(SLTFRoundOff);

			// Verify actual and calculated SLTF values are equal
			if (actualSLTFValue != null && calculatedSLTFValue != null) {
				Assertions.passTest("View Print Full Quote Page",
						"Actual Surplus Lines Taxes and Fees : " + actualSLTFValue);
				Assertions.passTest("View Print Full Quote Page",
						"Calculated Surplus Lines Taxes and Fees : " + calculatedSLTFValue);
				Assertions.verify(actualSLTFValue.equalsIgnoreCase(calculatedSLTFValue), true,
						"View Print Full Quote Page",
						"The Surplus Lines Taxes is displayed correctly as per SLT percentage (0.06)", false, false);
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
			Assertions.failTest("NBTC089 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC089 ", "Executed Successfully");
			}
		}
	}
}
