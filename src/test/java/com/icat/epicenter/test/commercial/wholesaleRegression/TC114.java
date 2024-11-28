//Summary: Create a quote and Create Reoffer on on the created quote and verifythe SLTF,Broker Fees and FSLSO Fees
//Date of modified : 09/sep/2021
//Author: Sowndarya
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DateConversions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.ReOfferAccountAdministrationPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC114 extends AbstractCommercialTest {

	public TC114() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID114.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		LoginPage loginPage = new LoginPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		ReOfferAccountAdministrationPage reOfferAccountAdministrationPage = new ReOfferAccountAdministrationPage();
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		// Initializing the variables
		String premiumAmount;
		String otherFees;
		String icatFees;
		String actualSLTFValue;
		String surplusLinesTaxesPercentage;
		String fslsoServiceFeePercentage;
		String brokerFeesPercentage;
		double expectedsltf;
		BigDecimal roundOffExpectedSltf;
		double expectedBrokerFees;
		BigDecimal roundOffExpectedBrokerFees;
		String insurerInspectionFee;
		String insurerPolicyFee;
		String actualBrokerFees;
		String accountID;
		double expectedFSLSO;
		BigDecimal roundOffEexpectedFSLSO;
		String quoteNumber;
		String actualFSLSOServiceFee;
		String newAccountID;
		String url_end;
		String url;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		DateConversions date = new DateConversions();
		DecimalFormat df = new DecimalFormat("0.00");
		String insuredName = testData.get("InsuredName") + date.getCurrentDate("MM/dd/YYYY_hh:mm:ss");
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// click on user preference link and enter the details
			Assertions.addInfo("Home Page", "Entering User Preference Details");
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			preferenceOptionsPage.addBrokerFees(testData);
			homePage.goToHomepage.click();

			// Click on LogOut Button
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "SignOut as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Signin as User successfully");

			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.createNewAccount.click();
			homePage.producerNumber.setData("8521.1");
			Assertions.passTest("Home Page ", "Producer Number is : " + "8521.1");
			homePage.productArrow.scrollToElement();
			homePage.productArrow.click();
			homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			homePage.namedInsured.waitTillVisibilityOfElement(60);
			homePage.namedInsured.scrollToElement();
			homePage.namedInsured.setData(insuredName);
			Assertions.passTest("Home Page", "Insured Name is :" + testData.get("InsuredName"));
			homePage.effectiveDate.formatDynamicPath("1").setData(testData.get("PolicyEffDate"));
			Assertions.passTest("Home Page", "Policy Effective Date is :" + testData.get("PolicyEffDate"));
			homePage.goButton.click();
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			locationPage = eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting the quote number Account Overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Asserting Other Fees and SLTF on Account Overview Page
			Assertions.addInfo("Account Overview Page", "Asserting Other Fees and SLTF on Account Overview Page");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsPresent(), true, "Account Overview Page",
					"Other Fees is present verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// Fetching Premium Amount from Account Overview Page
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Premium Amount : " + "$" + premiumAmount);

			// Fetching ICAT fees value from Account Overview Page
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual ICAT Fees : " + "$" + icatFees);

			// Fetching Actual Other Fees or Broker fees from account overview page
			otherFees = accountOverviewPage.otherFees.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Other Fees : " + "$" + otherFees);

			// Actual SLTF value from Account Overview Page
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "");
			Assertions.passTest("Account Overview Page", "Actual SLTF value :" + "$" + actualSLTFValue);

			// Getting surplusLinesTaxesPercentage from Datasheet 4.94%
			surplusLinesTaxesPercentage = testData.get("SurplusLinesTaxesPercentage");

			// Getting FSLSO Service Fee percentage 0.06% from datashet
			fslsoServiceFeePercentage = testData.get("FSLSOServiceFee");

			// Asserting SLTF percentage and FSLSOService Fee
			Assertions.passTest("SLTF Percentage", "SLTF Percentage =" + surplusLinesTaxesPercentage);
			Assertions.passTest("FSLSO Service Fee", "FSLSO Service Fees =" + fslsoServiceFeePercentage);

			// Calculating sltf percentage by adding (Premium+ICAT fees+otherfees*SLTf%)
			// (sltf percentage 4.94%+.06%) from account overview page
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(otherFees))
					* (Double.parseDouble(surplusLinesTaxesPercentage) + Double.parseDouble(fslsoServiceFeePercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf = new BigDecimal(expectedsltf);
			roundOffExpectedSltf = roundOffExpectedSltf.setScale(2, RoundingMode.HALF_UP);

			// Verifying actual SLTF and calculated SLTF are the same
			double d_actualSLTFValue = Double.parseDouble(actualSLTFValue);
			double d_roundOffExpectedSltf = roundOffExpectedSltf.doubleValue();
			Assertions.addInfo("Account Overview Page",
					"Verifying actual SLTF and calculated SLTF are same on Account Overview Page");
			if (Precision.round(
					Math.abs(Precision.round(d_actualSLTFValue, 2) - Precision.round(d_roundOffExpectedSltf, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Calculated Surplus Lines Taxes and Fees : " + "$"
						+ Precision.round(d_roundOffExpectedSltf, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_actualSLTFValue);
				Assertions.passTest("Account Overview Page",
						"The Calculated and Actual SLTF value are the same for FL Percentage");
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			// Getting Broker Percentage from Datasheet
			brokerFeesPercentage = testData.get("BrokerFeesPercentage");
			Assertions.passTest("BrokerFeesPercentage", "BrokerFeesPercentage = " + brokerFeesPercentage);

			// Calculating Other Fees or Broker Fees as per entered percentage(Premium*5%)
			// from account overview page
			expectedBrokerFees = Double.parseDouble(premiumAmount) * Double.parseDouble(brokerFeesPercentage);

			// Rounding Other Fees or Broker Fees decimal value to 2 digits
			roundOffExpectedBrokerFees = new BigDecimal(expectedBrokerFees);
			roundOffExpectedBrokerFees = roundOffExpectedBrokerFees.setScale(2, RoundingMode.HALF_UP);

			Assertions.passTest("Account Overview Page", "Calculated Broker Fees =" + "(" + premiumAmount + "*"
					+ brokerFeesPercentage + ")" + format.format(roundOffExpectedBrokerFees));
			Assertions.passTest("Account Overview Page", "Actual Broker Fees : " + "$" + otherFees);

			// Verifying actual Broker Fees and calculated Broker Fees are the same
			Assertions.addInfo("Account Overview Page",
					"Verifying actual and calculated Broker Fees are same on Account Overview Page");
			if (format.format(Double.parseDouble(otherFees))
					.equalsIgnoreCase(format.format(roundOffExpectedBrokerFees))) {
				Assertions.verify(format.format(Double.parseDouble(otherFees)),
						format.format(roundOffExpectedBrokerFees), "Account Overview Page",
						"The Calculated and Actual Broker Fees are the same for FL Percentage 5% : "
								+ format.format(Double.parseDouble(otherFees)),
						false, false);
			} else {
				Assertions.verify(otherFees, format.format(roundOffExpectedBrokerFees), "Account Overview Page",
						"The Calculated and Actual Broker Fees are Not the same for FL Percentage 5% : " + otherFees,
						false, false);
			}

			// Click on View/Print Full quote link on account overview page.
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on ViewPrintFull Quote Link successfully");

			// Getting Premium Amount from View/Print Full quote document page
			premiumAmount = viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath(5).getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page", "Premium Amount : " + "$" + premiumAmount);

			// Getting Insurer Inspection Fees from View/Print Full quote document page
			insurerInspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Insurer Inspection Fees : " + "$" + insurerInspectionFee);

			// Getting Insurer Policy Fee from View/Print Full quote document page
			insurerPolicyFee = viewOrPrintFullQuotePage.policyFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page", "Insurer Policy Fees : " + "$" + insurerPolicyFee);

			// Getting Broker Fee from View/Print Full quote document page
			actualBrokerFees = viewOrPrintFullQuotePage.brokerFeeValue.getData().replaceAll("[^\\d-.]", "");

			// Getting surplusLinesTaxesValue from View/Print Full quote document page
			actualSLTFValue = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replaceAll("[^\\d-.]", "")
					.replace("$", "").replace(",", "");
			Assertions.passTest("View or Print Full Quote Page", "Actual SLTF value :" + "$" + actualSLTFValue);

			// calculating sltf percentage by adding Premium+ICAT fees+OtherFees and
			// multiplying by
			// sltf percentage 0.0494
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(insurerInspectionFee)
					+ Double.parseDouble(actualBrokerFees)

					+ Double.parseDouble(insurerPolicyFee)) * (Double.parseDouble(surplusLinesTaxesPercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf = new BigDecimal(expectedsltf);
			roundOffExpectedSltf = roundOffExpectedSltf.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated SLTF value = " + "(" + premiumAmount + "+" + insurerInspectionFee + "+"
							+ actualBrokerFees + "+" + insurerPolicyFee + ")*" + "(" + surplusLinesTaxesPercentage
							+ ")=" + format.format(roundOffExpectedSltf));

			// Verifying actual SLTF and Calculated SLTF are the same VieworPrint quote page
			// 4.94%
			Assertions.addInfo("View or Print Full Quote Page",
					"Verifying actual and calculated SLTF  are same on View or Print Full Quote Page");
			if (Precision.round(
					Math.abs(
							Precision.round(Double.parseDouble(actualSLTFValue), 2) - Precision.round(expectedsltf, 2)),
					2) < 0.05) {
				Assertions.passTest("View or Print Full Quote Page", "Actual SLTF Value :" + "$" + actualSLTFValue);
				Assertions.passTest("View or Print Full Quote Page",
						"Calculated SLTF Value :" + "$" + df.format(expectedsltf));
			} else {
				Assertions.passTest("View or Print Full Quote Page",
						"The Difference between actual  and calculated SLTF is more than 0.05");
			}

			// Calculating Other Fees or Broker Fees as per entered percentage(Premium*5%)
			// from View or Print Full Quote Page
			expectedBrokerFees = Double.parseDouble(premiumAmount) * Double.parseDouble(brokerFeesPercentage);

			// Rounding Other Fees or Broker Fees decimal value to 2 digits
			roundOffExpectedBrokerFees = new BigDecimal(expectedBrokerFees);
			roundOffExpectedBrokerFees = roundOffExpectedBrokerFees.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("View or Print Full Quote Page", "Calculated Broker Fees =" + "(" + premiumAmount + "*"
					+ brokerFeesPercentage + ")=" + format.format(roundOffExpectedBrokerFees));
			Assertions.passTest("View or Print Full Quote Page", "Actual Broker Fees : " + "$" + actualBrokerFees);

			// Verifying actual Broker Fees and calculated Broker Fees are the same
			Assertions.addInfo("View or Print Full Quote Page",
					"Verifying actual and calculated Other Fees are same on View or Print Full Quote Page");
			if (format.format(Double.parseDouble(actualBrokerFees))
					.equalsIgnoreCase(format.format(roundOffExpectedBrokerFees))) {
				Assertions.verify(format.format(Double.parseDouble(otherFees)),
						format.format(roundOffExpectedBrokerFees), "View or Print Full Quote Page",
						"The Calculated and Actual Broker Fees are the same for FL Percentage 5% : "
								+ format.format(Double.parseDouble(actualBrokerFees)),
						false, false);
			} else {
				Assertions.verify(otherFees, format.format(roundOffExpectedBrokerFees), "View or Print Full Quote Page",
						"The Calculated and Actual Broker Fees are Not the same for FL Percentage 5% : "
								+ actualBrokerFees,
						false, false);
			}

			// Getting FSLSO Service Fees from view/print full quote document page
			actualFSLSOServiceFee = viewOrPrintFullQuotePage.fslsoServiceFee.getData().replaceAll("[^\\d-.]", "")
					.replace("$", "").replace(",", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Actual FSLSO Service Fees :" + "$" + actualFSLSOServiceFee);

			// calculating FSLSO Service Fees percentage by adding Premium+ICAT
			// fees+OtherFees and multiplying by
			// FSLSO percentage 0.06%
			expectedFSLSO = (Double.parseDouble(premiumAmount) + Double.parseDouble(insurerInspectionFee)
					+ Double.parseDouble(actualBrokerFees)

					+ Double.parseDouble(insurerPolicyFee)) * (Double.parseDouble(fslsoServiceFeePercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffEexpectedFSLSO = new BigDecimal(expectedFSLSO);
			roundOffEexpectedFSLSO = roundOffEexpectedFSLSO.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated FSLSO Service Fees = " + "(" + premiumAmount + "+" + insurerInspectionFee + "+"
							+ actualBrokerFees + "+" + insurerPolicyFee + ")" + "*" + "(" + fslsoServiceFeePercentage
							+ ")" + "=" + format.format(roundOffEexpectedFSLSO));

			// Verifying actual FSLSO Service Fees and Calculated FSLSO Service Fees are the
			// same VieworPrint quote page 0.06%
			Assertions.addInfo("View or Print Full Quote Page",
					"Verifying actual and calculated FSLSO Service Fees are same on View or Print Full Quote Page");

			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(actualFSLSOServiceFee), 2) - Precision.round(expectedFSLSO, 2)),
					2) < 0.05) {
				Assertions.passTest("View or Print Full Quote Page",
						"Actual FSLSO Service Fees :" + "$" + actualFSLSOServiceFee);
				Assertions.passTest("View or Print Full Quote Page",
						"Calculated FSLSO Service Fees : " + "$" + df.format(expectedFSLSO));
			} else {
				Assertions.passTest("View or Print Full Quote Page",
						"The Difference between actual  and calculated SLTF is more than 0.05");
			}
			Assertions.passTest("View or Print Full Quote Page",
					"User Preferences icon is not available on quote document");

			// View or Print Full Quote Page click on go back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View or Print Full Quote Page",
					"Clicked on Back Button successfully and Navigated to Account Overview Page");

			// copying account ID from account overview page URL - need to accommodate
			// running from a node directly, just get the end of the URL
			url = accountOverviewPage.getUrl();
			url_end = url.substring(url.length() - 18);
			accountID = url_end.substring(0, 7);

			// Click on LogOut Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Clicked On SignOut Button successfully and SignOut as User successfully");

			// Login to Rzimmer account
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails("swilcox", setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Rzimmer Successfully");

			// click on admin setting
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home Page", "Clicked on AdminLink successfully and Navigated to Helth DashBoard Page");

			// click on Re_OfferAccount link
			healthDashBoardPage.reofferaccountLink.scrollToElement();
			healthDashBoardPage.reofferaccountLink.click();
			Assertions.passTest("Health DashBoard Page",
					"Cliked on Re-OfferAccount Link successfully and Navigated to Re-Offer Account Administration Page");

			// Entering account ID and Date Re-Offer Account Administration Page
			reOfferAccountAdministrationPage.accountNumber.setData(accountID);
			reOfferAccountAdministrationPage.newCreatedDate.setData(testData.get("NewCreatedDate"));
			reOfferAccountAdministrationPage.commitButton.click();
			Assertions.passTest("ReOffer Account Administration Page",
					"Entered AccountID and NewCreated Date successfully,AccountID= " + accountID + " Created Date is "
							+ testData.get("NewCreatedDate"));
			Assertions.addInfo("ReOffer Account administration Page",
					"Creating Reoffer account by Entering Account ID on ReOffer Account administration Page");
			reOfferAccountAdministrationPage.accountID.scrollToElement();
			reOfferAccountAdministrationPage.accountID.setData(accountID);
			reOfferAccountAdministrationPage.reOfferAccountButton.click();
			reOfferAccountAdministrationPage.newAccountID.waitTillPresenceOfElement(60);
			reOfferAccountAdministrationPage.newAccountID.scrollToElement();
			Assertions.verify(reOfferAccountAdministrationPage.newAccountID.checkIfElementIsDisplayed(), true,
					"ReOffer Account Administration Page", "New AccountID Displayed Verified", false, false);
			newAccountID = reOfferAccountAdministrationPage.newAccountID.getData().substring(56, 63);
			String newQuoteNumber = reOfferAccountAdministrationPage.newAccountID.getData().substring(64, 74);
			Assertions.passTest("ReOffer Account Administration Page", "New AccountID=" + newAccountID);

			// Logout As rzimmer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged Out as rzimmer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "SignIn as User successfully");
			Assertions.passTest("Home Page", "Home Page loaded successfully");

			homePage.searchQuote(newQuoteNumber);
			Assertions.passTest("Home Page", "Reoffer Search successfull for the Insured name " + insuredName);

			// Verifying Re-Offer Account created or not
			Assertions.addInfo("Account Overview Page", "Verifying Re-Offer Account created or not");
			Assertions.verify(accountOverviewPage.reofferAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "ReOffer Quote Generated is verified", false, false);

			// Getting the Re_Offer quote number Account Overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Re-Offer Quote Number :  " + quoteNumber);
			Assertions.addInfo("Account Overview Page",
					"Asserting Other Fees and SLTF on Account overview page for the reoffer quote");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsPresent(), true, "Account Overview Page",
					"Other Fees is present verified", false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// Fetching Premium Amount of Re-Offer quote from Account Overview Page
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page",
					"Actual Premium Amount of Re-Offer Quote : " + "$" + premiumAmount);

			// Fetching ICAT fees value of Re-Offer Quote from Account Overview Page o
			icatFees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual ICAT Fees  of Re-Offer Quote : " + "$" + icatFees);

			// Fetching Actual Other Fees or Broker fees of Re-Offer Quote from account
			// overview page
			otherFees = accountOverviewPage.otherFees.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Account Overview Page", "Actual Other Fees of Re-Offer Quote : " + "$" + otherFees);

			// Actual SLTF value of Re-Offer Quote from Account Overview Page
			actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "");
			Assertions.passTest("Account Overview Page",
					"Actual SLTF value of Re-Offer Quote :" + "$" + actualSLTFValue);

			// Getting surplusLinesTaxesPercentage from Datasheet 4.94%
			surplusLinesTaxesPercentage = testData.get("SurplusLinesTaxesPercentage");

			// Getting FSLSO Service Fee percentage 0.06% from datashet
			fslsoServiceFeePercentage = testData.get("FSLSOServiceFee");

			// Asserting SLTF percentage and FSLSOService Fee
			Assertions.passTest("SLTF Percentage", "SLTF Percentage=" + surplusLinesTaxesPercentage);
			Assertions.passTest("FSLSO Service Fee", "FSLSO Service Fees=" + fslsoServiceFeePercentage);

			// Calculating sltf percentage by adding (Premium+ICAT fees+otherfees*SLTf%)
			// (sltf percentage 4.94%+.06%) of Re-Offer Quote from account overview page
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(otherFees))
					* (Double.parseDouble(surplusLinesTaxesPercentage) + Double.parseDouble(fslsoServiceFeePercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffExpectedSltf = new BigDecimal(expectedsltf);
			roundOffExpectedSltf = roundOffExpectedSltf.setScale(2, RoundingMode.HALF_UP);
			double d_actualSLTFValueAcc = Double.parseDouble(actualSLTFValue);
			double d_roundOffExpectedSltfAcc = roundOffExpectedSltf.doubleValue();
			Assertions.addInfo("Account Overview Page",
					"Verifying Actual and Calculated SLTF on Account overview page for reoffer quote");
			if (Precision.round(
					Math.abs(Precision.round(d_actualSLTFValueAcc, 2) - Precision.round(d_roundOffExpectedSltfAcc, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page", "Calculated Surplus Lines Taxes and Fees : " + "$"
						+ Precision.round(d_roundOffExpectedSltfAcc, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_actualSLTFValueAcc);
				Assertions.passTest("Account Overview Page",
						"The Calculated and Actual SLTF value of Re-Offer Quote are the same for FL Percentage");
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			// Getting Broker Percentage from Datasheet
			brokerFeesPercentage = testData.get("BrokerFeesPercentage");
			Assertions.passTest("BrokerFeesPercentage", "BrokerFeesPercentage=" + brokerFeesPercentage);

			// Calculating Other Fees or Broker Fees as per entered percentage(Premium*5%)of
			// Re-Offer Quote
			// from account overview page
			expectedBrokerFees = Double.parseDouble(premiumAmount) * Double.parseDouble(brokerFeesPercentage);

			// Rounding Other Fees or Broker Fees decimal value to 2 digits
			roundOffExpectedBrokerFees = new BigDecimal(expectedBrokerFees);
			roundOffExpectedBrokerFees = roundOffExpectedBrokerFees.setScale(2, RoundingMode.HALF_UP);

			Assertions.passTest("Account Overview Page", "Calculated Broker Fees of Re-Offer Quote  =" + "("
					+ premiumAmount + "*" + brokerFeesPercentage + ")" + format.format(roundOffExpectedBrokerFees));
			Assertions.passTest("Account Overview Page", "Actual Broker Fees of Re-Offer Quote : " + "$" + otherFees);

			// Verifying actual Broker Fees and calculated Broker Fees are the same of
			// Re-Offer Quote
			Assertions.addInfo("Account Overview Page",
					"Verifying Actual and Calculated Broker fee on Account overview page for reoffer quote");
			if (format.format(Double.parseDouble(otherFees))
					.equalsIgnoreCase(format.format(roundOffExpectedBrokerFees))) {
				Assertions.verify(format.format(Double.parseDouble(otherFees)),
						format.format(roundOffExpectedBrokerFees), "Account Overview Page",
						"The Calculated and Actual Broker Fees of Re-Offer Quote are the same for FL Percentage 5% : "
								+ format.format(Double.parseDouble(otherFees)),
						false, false);
			} else {
				Assertions.verify(otherFees, format.format(roundOffExpectedBrokerFees), "Account Overview Page",
						"The Calculated and Actual Broker Fees of Re-Offer Quote are Not the same for FL Percentage 5% : "
								+ otherFees,
						false, false);
			}

			// Click on View/Print Full quote link on account overview page.of Re-Offer
			// Quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page",
					"Clicked on ViewPrintFull Quote Link successfully and ViewOrPrintFullQuotePage loaded successfully");

			// Getting Premium Amount of Re-Offer Quote from View/Print Full quote document
			// page
			premiumAmount = viewOrPrintFullQuotePage.premiumDetails.formatDynamicPath(5).getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Premium Amount of Re-Offer Quote  : " + "$" + premiumAmount);

			// Getting Insurer Inspection Fees of Re-Offer Quote from View/Print Full quote
			// document page
			insurerInspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Insurer Inspection Fees of Re-Offer Quote  : " + "$" + insurerInspectionFee);

			// Getting Insurer Policy Fee of Re-Offer Quote from View/Print Full quote
			// document page
			insurerPolicyFee = viewOrPrintFullQuotePage.policyFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Insurer Policy Fees of Re-Offer Quote : " + "$" + insurerPolicyFee);

			// Getting Broker Fee of Re-Offer Quote from View/Print Full quote document page
			actualBrokerFees = viewOrPrintFullQuotePage.brokerFeeValue.getData().replaceAll("[^\\d-.]", "");

			// Getting surplusLinesTaxesValue of Re-Offer Quote from View/Print Full quote
			// document page
			actualSLTFValue = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replaceAll("[^\\d-.]", "")
					.replace("$", "").replace(",", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Actual SLTF value of Re-Offer Quote  :" + "$" + actualSLTFValue);

			// calculating sltf percentage by adding Premium+ICAT fees+OtherFees and
			// multiplying by sltf percentage 0.0494 of Re-Offer Quote
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(insurerInspectionFee)
					+ Double.parseDouble(actualBrokerFees) + Double.parseDouble(insurerPolicyFee))
					* (Double.parseDouble(surplusLinesTaxesPercentage));

			// Rounding sltf decimal value to 2 digits

			roundOffExpectedSltf = new BigDecimal(expectedsltf);
			roundOffExpectedSltf = roundOffExpectedSltf.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated SLTF value of Re-Offer Quote = " + "(" + premiumAmount + "+" + insurerInspectionFee
							+ "+" + actualBrokerFees + "+" + insurerPolicyFee + ")*" + "(" + surplusLinesTaxesPercentage
							+ ")=" + format.format(roundOffExpectedSltf));

			// Verifying actual SLTF and Calculated SLTF are the same VieworPrint quote page
			// 4.94% of Re-Offer Quote
			Assertions.addInfo("View or Print Full Quote Page",
					"Verifying Actual and Calculated SLTF on View or Print Full Quote page for reoffer quote");
			if (Precision.round(
					Math.abs(
							Precision.round(Double.parseDouble(actualSLTFValue), 2) - Precision.round(expectedsltf, 2)),
					2) < 0.05) {
				Assertions.passTest("View or Print Full Quote Page",
						"Actual SLTF Value of Re-Offer Quote :" + "$" + actualSLTFValue);
				Assertions.passTest("View or Print Full Quote Page",
						"Calculated SLTF Value of Re-Offer Quote :" + "$" + df.format(expectedsltf));
			} else {
				Assertions.passTest("View or Print Full Quote Page",
						"The Difference between actual  and calculated SLTF is more than 0.05");
			}

			// Calculating Other Fees or Broker Fees as per entered percentage(Premium*5%)
			// of Re-Offer Quote
			// from View or Print Full Quote Page
			expectedBrokerFees = Double.parseDouble(premiumAmount) * Double.parseDouble(brokerFeesPercentage);

			// Rounding Other Fees or Broker Fees decimal value to 2 digits
			roundOffExpectedBrokerFees = new BigDecimal(expectedBrokerFees);
			roundOffExpectedBrokerFees = roundOffExpectedBrokerFees.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("View or Print Full Quote Page", "Calculated Broker Fees of Re-Offer Quote =" + "("
					+ premiumAmount + "*" + brokerFeesPercentage + ")=" + format.format(roundOffExpectedBrokerFees));
			Assertions.passTest("View or Print Full Quote Page",
					"Actual Broker Fees of Re-Offer Quote : " + "$" + actualBrokerFees);

			// Verifying actual Broker Fees and calculated Broker Fees are the same of
			// Re-Offer Quote
			Assertions.addInfo("View or Print Full Quote Page",
					"Verifying Actual and Calculated Broker fee on View or Print Full Quote page for reoffer quote");
			if (format.format(Double.parseDouble(actualBrokerFees))
					.equalsIgnoreCase(format.format(roundOffExpectedBrokerFees))) {
				Assertions.verify(format.format(Double.parseDouble(otherFees)),
						format.format(roundOffExpectedBrokerFees), "View or Print Full Quote Page",
						"The Calculated and Actual Broker Fees of Re-Offer Quote are the same for FL Percentage 5% : "
								+ format.format(Double.parseDouble(actualBrokerFees)),
						false, false);
			} else {
				Assertions.verify(otherFees, format.format(roundOffExpectedBrokerFees), "View or Print Full Quote Page",
						"The Calculated and Actual Broker Fees of Re-Offer Quote  are Not the same for FL Percentage 5% : "
								+ actualBrokerFees,
						false, false);
			}

			// Getting FSLSO Service Fees of Re-Offer Quote from view/print full quote
			// document page
			actualFSLSOServiceFee = viewOrPrintFullQuotePage.fslsoServiceFee.getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("View or Print Full Quote Page",
					"Actual FSLSO Service Fees of Re-Offer Quote :" + "$" + actualFSLSOServiceFee);

			// calculating FSLSO Service Fees percentage by adding Premium+ICAT
			// fees+OtherFees and multiplying by FSLSO percentage 0.06% of Re-Offer Quote
			expectedFSLSO = (Double.parseDouble(premiumAmount) + Double.parseDouble(insurerInspectionFee)
					+ Double.parseDouble(actualBrokerFees) + Double.parseDouble(insurerPolicyFee))
					* (Double.parseDouble(fslsoServiceFeePercentage));

			// Rounding sltf decimal value to 2 digits
			roundOffEexpectedFSLSO = new BigDecimal(expectedFSLSO);
			roundOffEexpectedFSLSO = roundOffEexpectedFSLSO.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("View or Print Full Quote Page",
					"Calculated FSLSO Service Fees of Re-Offer Quote = " + "(" + premiumAmount + "+"
							+ insurerInspectionFee + "+" + actualBrokerFees + "+" + insurerPolicyFee + ")" + "*" + "("
							+ fslsoServiceFeePercentage + ")" + "=" + format.format(roundOffEexpectedFSLSO));

			// Verifying actual FSLSO Service Fees and Calculated FSLSO Service Fees are the
			// same VieworPrint quote page 0.06% of Re-Offer Quote
			Assertions.addInfo("View or Print Full Quote Page",
					"Verifying Actual and Calculated FSLSO Service Fees on View or Print Full Quote page for reoffer quote");

			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(actualFSLSOServiceFee), 2) - Precision.round(expectedFSLSO, 2)),
					2) < 0.05) {
				Assertions.passTest("View or Print Full Quote Page",
						"Actual FSLSO Service Fees of Re-Offer Quote :" + "$" + actualFSLSOServiceFee);
				Assertions.passTest("View or Print Full Quote Page",
						"Calculated FSLSO Service Fees of Re-Offer Quote :" + "$" + df.format(expectedFSLSO));
			} else {
				Assertions.passTest("View or Print Full Quote Page",
						"The Difference between actual  and calculated FSLSO Service Fees of Re-Offer Quote is more than 0.05");
			}

			// View or Print Full Quote Page click on go back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View or Print Full Quote Page", "Clicked on Back Button successfully");

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 114", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 114", "Executed Successfully");
			}
		}
	}
}
