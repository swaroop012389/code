/*Summary: Check display of SLTF on the quote when SLTF option is toggled on and off in User Preferences section and added ticket IO-20456
Date:05/02/2021
Author: Sowndarya*/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC104 extends AbstractCommercialTest {

	public TC104() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID104.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		LoginPage loginPage = new LoginPage();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		EditAdditionalInterestInformationPage editAdditionalInterestInformationPage = new EditAdditionalInterestInformationPage();

		// Initializing the variables
		String quoteNumber;
		double expectedsltf;
		double expectedStampingFee;
		double expectedMWUAFee;
		BigDecimal roundOffMWUAFee;
		DecimalFormat df = new DecimalFormat("0.00");
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// click on user preference link and enter the details
			Assertions.addInfo("Home Page",
					"Deselecting Broker Fees checkbox and Selecting Surplus Lines,Taxes and Fees checkbox");
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			preferenceOptionsPage.addBrokerFees(testData);
			Assertions.passTest("Preference Options Page",
					"Broker Fees checkbox is deselected and Surplus Lines,Taxes and Fees checkbox is selected successfully");
			homePage.goToHomepage.click();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
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

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

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

			// Getting the premium,fees and sltf values from premium section in Account
			// Overview page
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsDisplayed(), false, "Account Overview Page",
					"Other Fees is not present verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Value is : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Fees Value is : " + accountOverviewPage.feesValue.getData(), false, false);

			// fetching premium amount
			String premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			double premiumAmount = Double.parseDouble(premium);

			// fetching fees value
			String icatFees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			double inspectionAndPolicyFee = Double.parseDouble(icatFees);

			// fetching SLTF percentage
			String sltfPercentage = testData.get("SurplusLinesTaxesPercentage");
			double d_sltfPercentage = Double.parseDouble(sltfPercentage);

			// calculating stamping fee
			String stampingFeePercent = testData.get("StampingPercentage");
			double d_stampingFeePercent = Double.parseDouble(stampingFeePercent);
			double calStampingFee = (premiumAmount + inspectionAndPolicyFee) * d_stampingFeePercent;

			// calculating MWUA fee
			String mwuaFee = testData.get("MWUAPercentage");
			double d_mwuaFee = Double.parseDouble(mwuaFee);
			double calMWUAFee = (premiumAmount + inspectionAndPolicyFee) * d_mwuaFee;

			// calculating sltf percentage by adding ((Premium+Fees)*sltf
			// percentage 0.04)+stamping + mwua fee
			expectedsltf = ((premiumAmount + inspectionAndPolicyFee) * d_sltfPercentage) + calStampingFee + calMWUAFee;

			// Rounding sltf decimal value to 2 digits
			df.format(expectedsltf);

			// Comparing actual and expected SLTF value and printing calculated value
			String actualSltf = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			Assertions.addInfo("Account Overview Page",
					"Verifying actual and expected SLTF values on account overview page");
			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(actualSltf), 2)) - Precision.round(expectedsltf, 2),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes and Fees : " + "$" + df.format(expectedsltf));
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + accountOverviewPage.sltfValue.getData());
			} else {
				Assertions.verify(actualSltf, expectedsltf, "Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);

			}

			// click on view/Print full Quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// fetching Sltf percentage
			testData = data.get(data_Value2);
			String sltfPercentage_VPFQ = testData.get("SurplusLinesTaxesPercentage");
			double d_sltfPercentage_VPFQ = Double.parseDouble(sltfPercentage_VPFQ);

			// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
			// percentage 0.04
			double expectedsltf_VPFQ = (premiumAmount + inspectionAndPolicyFee) * d_sltfPercentage_VPFQ;

			// fetching actual sltf
			String actualSLTF_VPFQ = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "")
					.replace(",", "");

			// verifying surplus fees in view/print full quote page
			Assertions.addInfo("Account Overview Page",
					"Verifying actual and expected SLTF values on View/Print Full quote page");

			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualSLTF_VPFQ), 2))
					- Precision.round(expectedsltf_VPFQ, 2), 2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated Surplus Lines Taxes and Fees : " + "$" + df.format(expectedsltf_VPFQ));
				Assertions.passTest("View/Print Full Quote Page", "Actual Surplus Lines Taxes and Fees : "
						+ viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData());
			} else {
				Assertions.verify(actualSLTF_VPFQ, expectedsltf, "View/Print Full Quote Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);

			}

			// fetching stamping fee percentage
			testData = data.get(data_Value3);
			String stampingsltfPercentage_VPFQ = testData.get("StampingPercentage");
			double d_stampingsltfPercentage_VPFQ = Double.parseDouble(stampingsltfPercentage_VPFQ);

			// calculating sltf percentage by adding Premium+Fees+Broker fees and
			// multiplying by sltf percentage 0.0025
			expectedStampingFee = (premiumAmount + inspectionAndPolicyFee) * d_stampingsltfPercentage_VPFQ;
			df.format(expectedStampingFee);

			String stampingFees = viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "").replace(",", "");

			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(stampingFees), 2) - Precision.round(expectedStampingFee, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Actual Stamping Fee Value :" + "$" + stampingFees);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated Stamping Fee Value :" + "$" + df.format(expectedStampingFee));
			} else {

				Assertions.verify(stampingFees, expectedStampingFee, "View/Print Full Quote Page",
						"The Difference between actual  and calculated Stamping fee is more than 0.05", false, false);

			}

			// fetching mwua fee percentage
			testData = data.get(data_Value4);
			String mwuaSltfPercentage_VPFQ = testData.get("SurplusLinesTaxesPercentage");
			double d_mwuaStampingsltfPercentage_VPFQ = Double.parseDouble(mwuaSltfPercentage_VPFQ);

			// calculating mwua fee (premium+fees)*mwuapercent 0.03
			expectedMWUAFee = (premiumAmount + inspectionAndPolicyFee) * d_mwuaStampingsltfPercentage_VPFQ;

			// Rounding sltf decimal value to 2 digits
			roundOffMWUAFee = new BigDecimal(expectedMWUAFee);
			roundOffMWUAFee = roundOffMWUAFee.setScale(2, RoundingMode.HALF_UP);

			String mwuaFeeActual = viewOrPrintFullQuotePage.mwuaValue.getData().replace("$", "").replace(",", "");

			Assertions.addInfo("Account Overview Page",
					"Verifying actual and expected MWUA Fees on View/Print Full quote page");
			if (Precision.round(Math
					.abs(Precision.round(Double.parseDouble(mwuaFeeActual), 2) - Precision.round(expectedMWUAFee, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Actual MWUA Fees :" + "$" + mwuaFeeActual);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated MWUA Fees :" + "$" + df.format(expectedMWUAFee));
			} else {

				Assertions.verify(mwuaFeeActual, expectedMWUAFee, "View/Print Full Quote Page",
						"The Difference between actual  and calculated MWUA Fees is more than 0.05", false, false);

			}

			// click on go back
			viewOrPrintFullQuotePage.backButton.click();

			// click on user preferences option
			homePage.userPreferences.click();

			// disable slt checkbox and enable broker checkbox
			testData = data.get(data_Value2);
			Assertions.addInfo("Home Page",
					"Selecting Broker Fees checkbox and Deselecting Surplus Lines,Taxes and Fees checkbox");
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			preferenceOptionsPage.addBrokerFees(testData);
			if (testData.get("BrokerFeePercentageOrDollar").equals("%")) {
				Assertions.passTest("Preference Options Page", "Broker Fee value : " + testData.get("BrokerFeeValue")
						+ testData.get("BrokerFeePercentageOrDollar"));
			} else {
				Assertions.passTest("Preference Options Page", "Broker Fee value : "
						+ testData.get("BrokerFeePercentageOrDollar") + testData.get("BrokerFeeValue"));
			}
			Assertions.passTest("Preference Options Page", "Details entered successfully");
			homePage.goToHomepage.click();

			// search the quote
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Quote Number " + quoteNumber + " successfully");

			// verify sltf and other fees is not present in premium section
			Assertions.addInfo("Account Overview Page",
					"Verifying sltf and other fees is not present in premium section on Account Overview Page");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsDisplayed(), false, "Account Overview Page",
					"Other Fees not present is verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsPresent(), false, "Account Overview Page",
					"Surplus Lines Taxes and Fees label is not present is verified", false, false);

			// click on view/print full quote
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying sltf and other fees is not present in premium section on View/Print Full Quote Page");
			Assertions.verify(viewOrPrintFullQuotePage.brokerFeeValue.checkIfElementIsPresent(), false,
					"View/Print Full Quote Page", "Broker Fees not present is verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsPresent(), false,
					"View/Print Full Quote Page", "Surplus Lines Taxes and Fees label is not present is verified",
					false, false);

			// click on go back
			viewOrPrintFullQuotePage.backButton.click();

			// click on user preferences option
			homePage.userPreferences.click();
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			preferenceOptionsPage.addBrokerFees(testData);
			if (testData.get("BrokerFeePercentageOrDollar").equals("%")) {
				Assertions.passTest("Preference Options Page", "Broker Fee value : " + testData.get("BrokerFeeValue")
						+ testData.get("BrokerFeePercentageOrDollar"));
			} else {
				Assertions.passTest("Preference Options Page", "Broker Fee value : "
						+ testData.get("BrokerFeePercentageOrDollar") + testData.get("BrokerFeeValue"));
			}
			Assertions.passTest("Preference Options Page", "Details entered successfully");
			homePage.goToHomepage.click();

			// search the quote
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.addInfo("Account Overview Page", "Creating Another Quote");
			accountOverviewPage.createAnotherQuote.click();

			testData = data.get(data_Value1);
			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

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

			// verify sltf is not present and other fees is present in premium section
			Assertions.addInfo("Account Overview Page",
					"Verifying sltf absence and other fees presence in premium section on Account Overview Page");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsPresent(), true, "Account Overview Page",
					"Other Fees is present verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsPresent(), false, "Account Overview Page",
					"Surplus Lines Taxes and Fees label is not present is verified", false, false);

			// click on view/print full quote
			accountOverviewPage.viewPrintFullQuoteLink.click();
			testData = data.get(data_Value2);
			Assertions.addInfo("View/Print Full Quote Page",
					"Verifying Broker Fees presence on View/Print Full Quote Page");
			Assertions.verify(viewOrPrintFullQuotePage.brokerFeeValue.checkIfElementIsPresent(), true,
					"View/Print Full Quote Page", "Broker Fees is present verified", false, false);
			String brokerfeeValue = viewOrPrintFullQuotePage.brokerFeeValue.getData().replaceAll("[^\\d-.]", "")
					.replace(".00", "");
			Assertions.verify("$" + brokerfeeValue,
					testData.get("BrokerFeePercentageOrDollar") + testData.get("BrokerFeeValue"),
					"View/Print Full Quote Page",
					"Broker Fees Value :" + viewOrPrintFullQuotePage.brokerFeeValue.getData(), false, false);
			Assertions.addInfo("View/Print Full Quote Page", "Verifying sltf absence on View/Print Full Quote Page");
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsPresent(), false, "Account Overview Page",
					"Surplus Lines Taxes and Fees label is not present is verified", false, false);

			// click on go back
			viewOrPrintFullQuotePage.backButton.click();

			// Logout as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Search the create quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Quote Number " + quoteNumber + " successfully");
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// verify sltf is not present and other fees is present in premium section
			Assertions.addInfo("Account Overview Page",
					"Verifying sltf is not present and other fees is present in premium section on Account Overview page");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsPresent(), true, "Account Overview Page",
					"Other Fees is present verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), false, "Account Overview Page",
					"Surplus Lines Taxes and Fees label is not present is verified", false, false);

			// click on view/print full quote
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.addInfo("Account Overview Page",
					"Verifying sltf is not present and other fees is present in premium section on View/Print Full Quote Page");
			Assertions.verify(viewOrPrintFullQuotePage.brokerFeeValue.checkIfElementIsPresent(), true,
					"View/Print Full Quote Page", "Broker Fees is present verified", false, false);
			String brokrefeeUSM = viewOrPrintFullQuotePage.brokerFeeValue.getData().replaceAll("[^\\d-.]", "")
					.replace(".00", "");
			Assertions.verify("$" + brokrefeeUSM,
					testData.get("BrokerFeePercentageOrDollar") + testData.get("BrokerFeeValue"),
					"View/Print Full Quote Page",
					"Broker Fees Value :" + viewOrPrintFullQuotePage.brokerFeeValue.getData(), false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsPresent(), false, "Account Overview Page",
					"Surplus Lines Taxes and Fees label is not present is verified", false, false);

			// click on go back
			viewOrPrintFullQuotePage.backButton.click();

			// adding below code for IO-20456
			// click on edit additional interest link
			accountOverviewPage.editAdditionalIntersets.scrollToElement();
			accountOverviewPage.editAdditionalIntersets.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit interest link successfully");

			// select AI By location
			editAdditionalInterestInformationPage.aIByLocation.formatDynamicPath(1).scrollToElement();
			editAdditionalInterestInformationPage.aIByLocation.formatDynamicPath(1).click();
			Assertions.verify(editAdditionalInterestInformationPage.update.checkIfElementIsDisplayed(), true,
					"Edit Additional Interest Page", "Edit Additional Interest Page loaded successfully", false, false);

			// Select any building
			editAdditionalInterestInformationPage.buildingSelection.formatDynamicPath(1).scrollToElement();
			editAdditionalInterestInformationPage.buildingSelection.formatDynamicPath(1).select();

			// Click on add symbol
			editAdditionalInterestInformationPage.aIAddSymbol.scrollToElement();
			editAdditionalInterestInformationPage.aIAddSymbol.click();

			// select AI By location
			editAdditionalInterestInformationPage.waitTime(2);
			editAdditionalInterestInformationPage.scrollToBottomPage();
			editAdditionalInterestInformationPage.aIByLocation.formatDynamicPath(2).scrollToElement();
			editAdditionalInterestInformationPage.aIByLocation.formatDynamicPath(2).click();

			// While adding second additional interest verifying none of the check boxes are
			// selected as default
			Assertions.addInfo("Additional Interest Page",
					"While adding second additional interest verifying none of the check boxes are selected default");
			Assertions.verify(
					editAdditionalInterestInformationPage.buildingSelection.formatDynamicPath(1)
							.checkIfElementIsSelected(),
					true, "Edit Additional Interest Page", "None of the check boxes are not selectetd as default",
					false, false);

			// click on cancel link
			editAdditionalInterestInformationPage.cancel.scrollToElement();
			editAdditionalInterestInformationPage.cancel.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 104", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 104", "Executed Successfully");
			}
		}
	}
}
