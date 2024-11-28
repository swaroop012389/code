/* Name: Sowndarya
Description:
Date: 02/04/2020   */
package com.icat.epicenter.test.naho.regression.ISNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DateConversions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReOfferAccountAdministrationPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC007_NBR001 extends AbstractNAHOTest {

	public TC007_NBR001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISNB/NBR001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuotablePage = new BuildingNoLongerQuoteablePage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		LoginPage loginPage = new LoginPage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		ReOfferAccountAdministrationPage reOfferAccountAdministrationPage = new ReOfferAccountAdministrationPage();
		int data_Value1 = 0;
		int data_Value2 = 1;
		String accountID;
		String url_end;
		String url;
		Map<String, String> testData;
		Map<String, String> Data;
		DateConversions date = new DateConversions();

		Data = data.get(data_Value1);
		testData = data.get(data_Value1);
		String insuredName = testData.get("InsuredName") + " " + date.getCurrentDate("MM/dd/YYYY_hh:mm:ss");
		boolean isTestPassed = false;

		try {
			// Creating a new account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.createNewAccount.waitTillPresenceOfElement(60);
			homePage.createNewAccount.waitTillVisibilityOfElement(60);
			homePage.createNewAccount.moveToElement();
			homePage.createNewAccount.click();

			homePage.namedInsured.waitTillVisibilityOfElement(60);
			homePage.namedInsured.setData(insuredName);
			Assertions.passTest("Home Page", "Insured Name is " + insuredName);

			if (homePage.producerNumber.checkIfElementIsPresent()) {
				homePage.producerNumber.setData("11250.1");
			}
			if (homePage.productArrow.checkIfElementIsPresent() && homePage.productArrow.checkIfElementIsDisplayed()) {
				homePage.productArrow.scrollToElement();
				homePage.productArrow.click();
				homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			}

			if (homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath(1).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(1).waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath(1).setData(testData.get("PolicyEffDate"));
			} else {
				homePage.effectiveDate.formatDynamicPath(2).scrollToElement();
				homePage.effectiveDate.formatDynamicPath(2).waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath(2).setData(testData.get("PolicyEffDate"));
			}
			homePage.goButton.click();
			homePage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("New Account", "New Account created successfully");

			// Checking the processing of Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			// dwellingPage.enterDwellingDetailsNAHO(testData);
			String locationNumber = testData.get("LocCount");
			int locationCount = Integer.parseInt(locationNumber);

			String dwellingNumber = testData.get("L" + locationCount + "-DwellingCount");
			int dwellingCount = Integer.parseInt(dwellingNumber);

			dwellingPage.addDwellingDetails(testData, locationCount, dwellingCount);
			dwellingPage.addRoofDetails(testData, locationCount, dwellingCount);
			dwellingPage.enterProtectionDiscountsDetails(testData, locationCount, dwellingCount);

			if (!testData.get("L" + locationCount + "D" + dwellingCount + "-ProtectionClassOverride").equals("")) {
				dwellingPage.enterInternalSectionDetails(testData, locationCount, dwellingCount);
			}

			dwellingPage.enterDwellingValuesNAHO(testData, locationCount, dwellingCount);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// dwellingPage.save.waitTillVisibilityOfElement(60);
			dwellingPage.scrollToTopPage();
			dwellingPage.waitTime(2);
			dwellingPage.save.scrollToElement();
			dwellingPage.save.click();
			Assertions.passTest("Dwelling Page", "Clicked on save");

			// navigate to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home page loaded successfully");

			// find the account by entering insured name and producer name
			homePage.findAccountNamedInsured.scrollToElement();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findBtnAccount.click();
			Assertions.passTest("Home Page", "Clicked on Find Account button");

			if (accountOverviewPage.pageName.getData().contains("Account Overview")) {
				Assertions.passTest("Account Overview Page", "Account overview page loaded successfully");
			} else {
				Assertions.failTest("Account Overview Page", "Failed to load Account overview page");
			}

			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();

			Assertions.passTest("Dwelling Page", "Dwelling page loaded successfully");
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.reviewDwelling.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Dwelling Page", "Clicked on Review Dwelling");

			if (dwellingPage.createQuote.checkIfElementIsPresent()
					|| dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillPresenceOfElement(60);
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.waitTillButtonIsClickable(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			if (buildingNoLongerQuotablePage.pageName.getData().contains("Buildings No")) {
				buildingNoLongerQuotablePage.overrideNoLongerQuotableBuildings();
			}
			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on View/Prinr Rate Trace Link
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();

			// Verifying the rates are displayed
			Assertions.verify(viewOrPrintRateTrace.baseRate.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "View Or Print Rate Trace Page Loaded successfully", false,
					false);
			Assertions.verify(viewOrPrintRateTrace.baseRate.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page", "Base Rate Displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.covAbaseRate.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page", "CovA Base Rate Displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.covBbaseRate.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page", "CovB Base Rate Displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.covCbaseRate.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page", "CovC Base Rate Displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.covDbaseRate.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page", "CovD Base Rate Displayed is verified", false, false);

			// Verifying the premiums are displayed
			Assertions.verify(viewOrPrintRateTrace.windPremiumOverride.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Wind Premium Override Adjustment displayed is verified", false,
					false);
			Assertions.verify(viewOrPrintRateTrace.windMinPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Wind Min Premium displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.windPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Wind Premium displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.aopPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Aop Premium displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.aopMinPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Aop Min Premium displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.aopPremiumOverride.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "AOP Premium Override Adjustment displayed is verified", false,
					false);
			Assertions.verify(viewOrPrintRateTrace.increasedSpecialLimitPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Increased Special Limit Premium displayed is verified", false,
					false);
			Assertions.verify(viewOrPrintRateTrace.identityFraudPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Identity Fraud Expense Premium displayed is verified", false,
					false);
			Assertions.verify(viewOrPrintRateTrace.increasedLimitBusinesssPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Increased Limit Business Property Premium displayed is verified",
					false, false);
			Assertions.verify(viewOrPrintRateTrace.lossAssesmentPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Loss Assessment Premium displyed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.moldPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Mold Premium displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.limitedPoolPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Limited Pool Patio Enclosure Premium displayed is verified",
					false, false);
			Assertions.verify(viewOrPrintRateTrace.limiteswaterBackupPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ",
					"Limited Water Back-Up Sump Discharge Overflow Premium displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.glPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "GL Premium displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.glPremiumOverride.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "GL Premium Override Adjustment dispalyed is verified", false,
					false);
			Assertions.verify(viewOrPrintRateTrace.covEbasePremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "CovE Base Premium displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.covFbasePremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "CovF Base Premium displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.persInjuryPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Pers Injury Premium displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.serviceLinePremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "ServiceLine Premium dispalyed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.eqbPremium.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "PremiumType - EQBPremium displayed is verified", false, false);

			// Verifying the Discounts
			Assertions.verify(viewOrPrintRateTrace.discountCsba.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Discount CSBA	displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.discountCfsa.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Discount CSFA displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.hardiplank.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Discount Hardiplank displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.fullySprinkled.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Discount Fully Sprinklered dispalyed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.gatedCommunity.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Discount Gated Community displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.companionPolicy.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Discount Companion Policy displayed is verified", false, false);

			// Verifying wind mitigation presence
			Assertions.verify(viewOrPrintRateTrace.windMitigation.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page",
					"Wind Mitigation Discount Opening Protect Factor displayed is verified", false, false);

			// Verifying renovated Home discounts
			Assertions.verify(viewOrPrintRateTrace.plumbingYearFactor.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Plumbing Year Factor displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.heatingAcYearFactor.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Heating AC Year Factor displayed is verified", false, false);
			Assertions.verify(viewOrPrintRateTrace.electricalYearFactor.checkIfElementIsDisplayed(), true,
					"View Or Print Rate Trace Page ", "Electrical Year Factor displayed is verified", false, false);

			// Adding the Individual Premiums

			int windMPremValueLen = viewOrPrintRateTrace.windPremiumVaue.getData().length();
			String windMPremValue = viewOrPrintRateTrace.windPremiumVaue.getData().substring(1, windMPremValueLen);
			String premiumValue1 = windMPremValue.replace(",", "");
			double s1 = Double.parseDouble(premiumValue1);
			int p1 = (int) s1;

			int aopPremValueLen = viewOrPrintRateTrace.aopPremiumValue.getData().length();
			String aopPremValue = viewOrPrintRateTrace.aopPremiumValue.getData().substring(0, aopPremValueLen);
			String aopPremValue1 = aopPremValue.replace(",", "");
			double s2 = Double.parseDouble(aopPremValue1);
			int p2 = (int) s2;

			int glpremValueLen = viewOrPrintRateTrace.glPremiumValue.getData().length();
			String glpremValue = viewOrPrintRateTrace.glPremiumValue.getData().substring(0, glpremValueLen);
			double s3 = Double.parseDouble(glpremValue);
			int p3 = (int) s3;

			int[] array = { p1, p2, p3 };
			int sum = 0;
			for (int num : array) {
				sum = sum + num;
			}

			Assertions.passTest("View Or Print Rate Trace Page", "The sum of individual premiums is " + sum);

			// Click on Back Btn
			viewOrPrintRateTrace.backBtn.scrollToElement();
			viewOrPrintRateTrace.backBtn.click();

			// verifying final Premium is summation of individual premiums
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			int actualPremiumValueLength = accountOverviewPage.premiumValue.getData().length();
			String actualPremiumValue = accountOverviewPage.premiumValue.getData().substring(1,
					actualPremiumValueLength);
			String actualPremval = actualPremiumValue.replace(",", "");
			double actualPremiumValueconverted = Double.parseDouble(actualPremval);
			int actPremvalCon = (int) actualPremiumValueconverted;
			Assertions.passTest("Account Overview Page", "The Actual premium Value is " + actPremvalCon);

			Assertions.verify(actPremvalCon, sum, "View Or Print Rate Trace Page", "The Final Premium Value "
					+ actPremvalCon + " is summation of individul premium values " + sum + " is verified", false,
					false);

			// added ticket IO-20825
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			Assertions.addInfo("Scenario 01",
					"Verifying that the carrier box displays the 'Carrier' text when one carrier is added.");
			Assertions.verify(
					viewOrPrintFullQuotePage.carrierBoxText.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.carrierBoxText.getData().equals("Carrier"),
					true, "View Or Print FullQuote Page",
					"Carrier box displayed the 'Carrier' text when one carrier is added verified", false, false);

			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// ----Added IO-21163---
			createQuotePage.aowhDeductibleArrow.scrollToElement();
			createQuotePage.aowhDeductibleArrow.click();

			Assertions.addInfo("Create Quote Page",
					"Verifying that the AOWH deductible dropdown does not contain the option of $1,000 for the Texas state.");
			Assertions.verify(
					createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath("$1,000").checkIfElementIsPresent()
							&& createQuotePage.aowhDeductibleOptionNAHO.formatDynamicPath("$1,000")
									.checkIfElementIsDisplayed(),
					false, "Create Quote Page",
					"The AOWH deductible with the option of $1,000 is excluded from the dropdown list for the Texas state and has been verified.",
					false, false);

			// ----IO-21163 Ended----

			if (!Data.get("ServiceLine1").equals("")) {
				if (createQuotePage.serviceLineArrow.checkIfElementIsPresent()
						&& createQuotePage.serviceLineArrow.checkIfElementIsDisplayed()) {
					createQuotePage.serviceLineArrow.scrollToElement();
					createQuotePage.serviceLineArrow.click();
					createQuotePage.serviceLineOption.formatDynamicPath(Data.get("ServiceLine1")).scrollToElement();
					createQuotePage.serviceLineOption.formatDynamicPath(Data.get("ServiceLine1")).click();
				}
			}

			if (!Data.get("EquipmentBreakdown1").equals("")) {
				if (createQuotePage.equipmentBreakdownArrow_NAHO.checkIfElementIsPresent()
						&& createQuotePage.equipmentBreakdownArrow_NAHO.checkIfElementIsDisplayed()) {
					createQuotePage.equipmentBreakdownArrow_NAHO.scrollToElement();
					createQuotePage.equipmentBreakdownArrow_NAHO.click();
					createQuotePage.equipmentBreakdownOption.formatDynamicPath(Data.get("EquipmentBreakdown1"))
							.scrollToElement();
					createQuotePage.equipmentBreakdownOption.formatDynamicPath(Data.get("EquipmentBreakdown1")).click();
				}
			}

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create quote page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			String quoteNumber2 = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number2 is : " + quoteNumber2);

			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			Assertions.addInfo("Scenario 02",
					"Verifying that the carrier box displays the 'Carriers' text when more than one carrier is added.");
			Assertions.verify(
					viewOrPrintFullQuotePage.carrierBoxText2.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.carrierBoxText2.getData().equals("Carriers"),
					true, "View Or Print FullQuote Page",
					"Carrier box displayed the 'Carriers' text when more than one carrier is added verified", false,
					false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// ------ IO-20825 Ended-----

			// -----Added ticket IO-21419----

			// navigate to home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			testData = data.get(data_Value1);
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

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			testData = data.get(data_Value2);
			createQuotePage.enterQuoteDetailsNAHO(testData);

			// copying account ID from account overview page URL - need to accommodate
			// running from a node directly, just get the end of the URL
			url = accountOverviewPage.getUrl();
			url_end = url.substring(url.length() - 19);
			accountID = url_end.substring(0, 7);

			// Click on LogOut Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Clicked On SignOut Button successfully and SignOut as USM successfully");

			// Log into Rzimmer account
			Assertions.verify(loginPage.userName.checkIfElementIsDisplayed(), true, "Login Page",
					"Login page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("Admin"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Rzimmer Successfully");

			// click on admin settings
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home Page", "Clicked on AdminLink successfully and Navigated to Helth DashBoard Page");

			// click on Re_OfferAccount link
			healthDashBoardPage.reofferaccountLink.scrollToElement();
			healthDashBoardPage.reofferaccountLink.click();
			Assertions.passTest("Health DashBoard Page",
					"Clicked on Re-OfferAccount Link successfully and Navigated to Re-Offer Account Administration Page");

			// Entering account ID and Date Re-Offer Account Administration Page
			reOfferAccountAdministrationPage.accountNumber.setData(accountID);
			testData = data.get(data_Value1);
			reOfferAccountAdministrationPage.newCreatedDate.setData(testData.get("NewCreatedDate"));
			reOfferAccountAdministrationPage.commitButton.click();
			Assertions.passTest("ReOffer Account Administration Page",
					"Entered AccountID and NewCreated Date successfully,AccountID= " + accountID + " Created Date is "
							+ testData.get("NewCreatedDate"));
			Assertions.addInfo("ReOffer Account administration Page",
					"Creating Reoffer account by Entering Account ID on ReOffer Account administration Page");
			reOfferAccountAdministrationPage.accountID.scrollToElement();
			reOfferAccountAdministrationPage.accountID.setData(accountID);
			waitTime(2);
			reOfferAccountAdministrationPage.reOfferAccountButton.click();

			Assertions.addInfo("Scenario 03",
					"Verifying that the re-offer quote is not generated when the USM adds the forms.");
			Assertions.verify(
					reOfferAccountAdministrationPage.newAccountID.checkIfElementIsPresent()
							&& reOfferAccountAdministrationPage.newAccountID.getData()
									.contains("User Added Form found. Do not Reoffer."),
					true, "Re-offer Account Administration Page",
					"Unable to create reoffer quote when the USM adds the forms is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// -------IO-21419 Ended------

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBTC007 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBTC007 ", "Executed Successfully");
			}
		}
	}
}
