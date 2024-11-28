/** Program Description: Create a quote.Click on Push to rms link and wait for View model results link and Assert the values on RMS Model Results Page
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/29/2021
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.RmsModelResultsPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC100 extends AbstractCommercialTest {

	public TC100() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID100.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ModifyForms modifyForms = new ModifyForms();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RmsModelResultsPage rmsModelResultsPage = new RmsModelResultsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// New Account creation
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully ", false, false);
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
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

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
			Assertions.passTest("Account Overview Page", "Quote Number : 1 " + quoteNumber);

			// click on push to rms link
			accountOverviewPage.pushToRMSLink.scrollToElement();
			accountOverviewPage.pushToRMSLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Push to RMS link");
			accountOverviewPage.runningLink.waitTillPresenceOfElement(60);
			accountOverviewPage.runningLink.waitTillVisibilityOfElement(60);

			// Click on view model results link
			while (!accountOverviewPage.viewModelResultsLink.checkIfElementIsPresent()) {
				accountOverviewPage.refreshPage();
			}

			// click on view model results link
			accountOverviewPage.viewModelResultsLink.scrollToElement();
			accountOverviewPage.viewModelResultsLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Model Results link successfully");

			Assertions.addInfo("RMS Model Results Page", "Assert the Values on RMS Model Results Page");
			rmsModelResultsPage.waitTime(2);// need waittime to load the window
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Total Premium")
							.checkIfElementIsDisplayed(),
					true, "RMS Model Results Page", "RMS Model Results Page loaded successfully", false, false);

			// Asserting the Rms values
			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Total Premium").checkIfElementIsDisplayed(),
					true, "RMS Model Results Page",
					"The Total Premium dispayed is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Total Premium").getData(),
					false, false);

			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("ELR Premium").checkIfElementIsDisplayed(),
					true, "RMS Model Results Page",
					"The ELR Premium dispayed is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("ELR Premium").getData(),
					false, false);

			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues
							.formatDynamicPath("Peril Deductible").checkIfElementIsDisplayed(),
					true, "RMS Model Results Page",
					"The Peril Deductible dispayed is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril Deductible").getData(),
					false, false);

			Assertions.verify(rmsModelResultsPage.tivValue.checkIfElementIsDisplayed(), true, "RMS Model Results Page",
					"The TIV dispayed is " + rmsModelResultsPage.tivValue.getData(), false, false);

			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril AAL").checkIfElementIsDisplayed(),
					true, "RMS Model Results Page",
					"The Peril AAL dispayed is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril AAL").getData(),
					false, false);

			Assertions.verify(
					rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR").checkIfElementIsDisplayed(),
					true, "RMS Model Results Page",
					"The Peril ELR dispayed is "
							+ rmsModelResultsPage.rmsModelResultValues.formatDynamicPath("Peril ELR").getData(),
					false, false);

			// Click on close button
			rmsModelResultsPage.closeButton.scrollToElement();
			rmsModelResultsPage.closeButton.click();

			// Click on Override premium link
			Assertions.addInfo("Account Overview Page", "Overriding the Premium");
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Override premium link");
			Assertions.verify(overridePremiumAndFeesPage.overridePremiumButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees Page loaded successfully", false,
					false);

			// click on premium override arrow
			overridePremiumAndFeesPage.premiumOverrideArrow.scrollToElement();
			overridePremiumAndFeesPage.premiumOverrideArrow.click();
			Assertions.verify(overridePremiumAndFeesPage.premiumoverrideDropdownValue.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page",
					"% values are not displayed and only "
							+ overridePremiumAndFeesPage.premiumoverrideDropdownValue.getData()
							+ " displayed is verified",
					false, false);

			// Overriding Premium,Inspection fees and policy fees
			if (testData.get("OverridePremium") != null) {
				if (!testData.get("OverridePremium").equalsIgnoreCase("")) {
					overridePremiumAndFeesPage.overridePremium.scrollToElement();
					overridePremiumAndFeesPage.overridePremium.setData(testData.get("OverridePremium"));
				}
			}

			if (testData.get("TransactionPolicyfee") != null) {
				if (!testData.get("TransactionPolicyfee").equalsIgnoreCase("")) {
					overridePremiumAndFeesPage.policyFee.scrollToElement();
					overridePremiumAndFeesPage.policyFee.setData(testData.get("TransactionPolicyfee"));
				}
			}
			if (testData.get("TransactionInspectionFee") != null) {
				if (!testData.get("TransactionInspectionFee").equalsIgnoreCase("")) {
					overridePremiumAndFeesPage.totalInspectionFee.scrollToElement();
					overridePremiumAndFeesPage.totalInspectionFee.setData(testData.get("TransactionInspectionFee"));
				}
			}

			if (testData.get("FeeOverrideJustification") != null) {
				if (!testData.get("FeeOverrideJustification").equalsIgnoreCase("")) {
					overridePremiumAndFeesPage.feeOverrideJustification.scrollToElement();
					overridePremiumAndFeesPage.feeOverrideJustification
							.setData(testData.get("FeeOverrideJustification"));
				}
			}
			Assertions.passTest("Override Premium and Fees Page", "Entered the values successfully");

			overridePremiumAndFeesPage.overridePremiumButton.scrollToElement();
			overridePremiumAndFeesPage.overridePremiumButton.click();
			if (overridePremiumAndFeesPage.overridetoAdjustedValueButton.checkIfElementIsPresent()
					&& overridePremiumAndFeesPage.overridetoAdjustedValueButton.checkIfElementIsDisplayed()) {
				overridePremiumAndFeesPage.overridetoAdjustedValueButton.scrollToElement();
				overridePremiumAndFeesPage.overridetoAdjustedValueButton.click();
			}
			testData = data.get(dataValue2);
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// click on location link
			accountOverviewPage.locationLink.formatDynamicPath(1).scrollToElement();
			accountOverviewPage.locationLink.formatDynamicPath(1).click();

			// click on add symbol
			accountOverviewPage.addSymbol.scrollToElement();
			accountOverviewPage.addSymbol.click();

			// click on add new location
			accountOverviewPage.addNewLocation.scrollToElement();
			accountOverviewPage.addNewLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on Add New Location");
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);

			// click on location link
			locationPage.locationLink.formatDynamicPath(2).waitTillVisibilityOfElement(60);
			locationPage.locationLink.formatDynamicPath(2).scrollToElement();
			locationPage.locationLink.formatDynamicPath(2).click();
			locationPage.businessIncome.setData(testData.get("L1-LocBI"));

			// click on add building button
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();

			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page loaded successfully", false, false);
			buildingPage.buildingLink.formatDynamicPath(2, 1).waitTillVisibilityOfElement(60);
			buildingPage.buildingLink.formatDynamicPath(2, 1).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(2, 1).click();
			buildingPage.addBuildingDetails(testData, dataValue2, dataValue2);
			buildingPage.addBuildingOccupancy(testData, dataValue2, dataValue2);
			buildingPage.addRoofDetails(testData, dataValue2, dataValue2);
			buildingPage.enterAdditionalBuildingInformation(testData, dataValue2, dataValue2);
			buildingPage.enterBuildingValues(testData, dataValue2, dataValue2);
			Assertions.passTest("Building Page", "Building Details entered successfully");

			// review building
			buildingPage.reviewBuilding();
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// click on internal Quote check box
			createQuotePage.internalQuoteChkBox.scrollToElement();
			createQuotePage.internalQuoteChkBox.select();
			Assertions.passTest("Create Quote Page", "Selected the Internal Quote Checkbox");

			// Click on Modify Forms
			Assertions.addInfo("Create Quote Page", "Select the available forms on Modify forms page");
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on Modify Forms");

			Assertions.verify(modifyForms.override.checkIfElementIsDisplayed(), true, "Modify Forms Page",
					"Modify Forms Page loaded successfully", false, false);

			// Selecting The CheckBoxes
			modifyForms.vacancypermitEntireTerm.select();
			modifyForms.heatMaintain.select();
			modifyForms.lockedAndSecured.select();
			modifyForms.outdoorTrees.select();
			modifyForms.fungusWetRot.waitTillVisibilityOfElement(60);
			modifyForms.fungusWetRot.scrollToElement();
			modifyForms.fungusWetRot.select();
			Assertions.passTest("Modify Forms Page", "Selected the forms successfully");
			modifyForms.override.scrollToElement();
			modifyForms.override.click();

			// click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.waitTillVisibilityOfElement(60);
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// getting the quote number
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			String quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number : 2 " + quoteNumber2);
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(2).checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Quote Status is "
							+ accountOverviewPage.quoteStatus.formatDynamicPath(2).getData() + " displayed is verified",
					false, false);

			// Signing out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as Producer successfully");

			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			// search the quote as producer
			homePage.producerQuoteSearchButton.scrollToElement();
			homePage.producerQuoteSearchButton.click();
			homePage.producerQuoteNumberSearchTextbox.scrollToElement();
			homePage.producerQuoteNumberSearchTextbox.setData(quoteNumber2);
			homePage.producerQuoteFindButton.scrollToElement();
			homePage.producerQuoteFindButton.click();
			Assertions.verify(
					homePage.producerQuoteNumberLink.formatDynamicPath(quoteNumber2).checkIfElementIsPresent(), false,
					"Home Page", "The Quote " + quoteNumber2 + " is not available is verified", false, false);

			// Signing out as Producer
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

			homePage.searchQuote(quoteNumber2);
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click on Release quote
			accountOverviewPage.releaseQuoteLink.scrollToElement();
			accountOverviewPage.releaseQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Quote link");
			Assertions.addInfo("Account Overvierw Page", "Assert the quote status after release the quote");
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath(2).checkIfElementIsPresent(), true,
					"Account Overview Page", "The Quote Status Internal only Removed is verified", false, false);

			accountOverviewPage.clickOnRequestBind(testData, quoteNumber2);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			if (requestBindPage.submit.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber2);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber2);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 100", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 100", "Executed Successfully");
			}
		}
	}

}
