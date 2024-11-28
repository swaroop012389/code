/** Program Description: Check for the presence of quoteAccountButton,deleteAccount button,and assert referral message if Cov A is 6M
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/29/2021
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DateConversions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC098 extends AbstractCommercialTest {

	public TC098() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID098.xls";
	}

	DateConversions date = new DateConversions();
	public String insuredName = "Commercial Regression TC098_AOP " + date.getCurrentDate("MM/dd/YYYY_hh:mm:ss");

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();

		// Initializing the variables
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue4 = 3;
		int dataValue5 = 4;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// creating New account
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.createNewAccountProducer.click();
			homePage.namedInsured.waitTillVisibilityOfElement(60);
			homePage.namedInsured.scrollToElement();
			homePage.namedInsured.setData(insuredName);
			Assertions.passTest("Home Page", "Insured Name is " + testData.get("InsuredName"));
			if (homePage.productArrow.checkIfElementIsPresent() && homePage.productArrow.checkIfElementIsDisplayed()) {
				homePage.productArrow.scrollToElement();
				homePage.productArrow.click();
				homePage.productSelection.formatDynamicPath(testData.get("ProductSelection")).click();
			}
			if (homePage.effectiveDate.formatDynamicPath("1").checkIfElementIsPresent()
					&& homePage.effectiveDate.formatDynamicPath("1").checkIfElementIsDisplayed()) {
				homePage.effectiveDate.formatDynamicPath("1").scrollToElement();
				homePage.effectiveDate.formatDynamicPath("1").waitTillVisibilityOfElement(60);
				homePage.effectiveDate.formatDynamicPath("1").setData(testData.get("PolicyEffDate"));
			}
			homePage.goButton.click();
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page;
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
			Assertions.passTest("Building Page", "The Building Address State is : " + testData.get("InsuredState"));

			// selecting peril
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
			/*
			 * Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(),
			 * true, "Refer Quote Page", "Refer Quote Page loaded successfully", false,
			 * false);
			 *
			 * // Asserting the referral message Assertions.addInfo("Refer Quote Page",
			 * "Assert the Referral Message when Coverage A is 6M");
			 * Assertions.verify(referQuotePage.covA6MReferralMsg.checkIfElementIsDisplayed(
			 * ), true, "Refer Quote Page", "The Referral message " +
			 * referQuotePage.covA6MReferralMsg.getData() + " displayed is verified", false,
			 * false);
			 *
			 * // Click on Location icon
			 * referQuotePage.locationBtn.formatDynamicPath(1).scrollToElement();
			 * referQuotePage.locationBtn.formatDynamicPath(1).click();
			 * Assertions.passTest("Refer Quote Page", "Clicked on Location icon");
			 * Assertions.verify(accountOverviewPage.quoteAccountButton.
			 * checkIfElementIsDisplayed(), true, "Account Overview Page",
			 * "Account Overview Page loaded successfully", false, false);
			 */

			// get the url
			String quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 1 :  " + quoteNumber);

			String Url = accountOverviewPage.getUrl();
			accountOverviewPage.buildingLink1.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink1.formatDynamicPath(1, 1).waitTillButtonIsClickable(60);
			accountOverviewPage.buildingLink1.formatDynamicPath(1, 1).click();

			// delete the Building
			accountOverviewPage.deleteBuilding.formatDynamicPath(0).scrollToElement();
			accountOverviewPage.deleteBuilding.formatDynamicPath(0).click();
			Assertions.passTest("Account Overview Page", "Clicked on Delete Building icon");

			// click on no delete building
			accountOverviewPage.noDeleteBuiling.waitTillPresenceOfElement(60);
			accountOverviewPage.noDeleteBuiling.waitTillVisibilityOfElement(60);
			accountOverviewPage.waitTime(3);
			accountOverviewPage.noDeleteBuiling.scrollToElement();
			accountOverviewPage.noDeleteBuiling.click();
			Assertions.passTest("Account Overview Page", "Clicked on No Delete Building link");

			// click on delete account
			Assertions.addInfo("Account Overview Page", "Delete the Account Created");
			accountOverviewPage.deleteAccount.scrollToElement();
			accountOverviewPage.deleteAccount.click();
			Assertions.passTest("Account Overview Page", "Clicked on Delete Account Buttton");

			// click on yes delete account
			accountOverviewPage.yesDeleteAccount.scrollToElement();
			accountOverviewPage.yesDeleteAccount.click();
			Assertions.passTest("Account Overview Page", "Clicked on yes Delete Link");
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

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
			homePage.refreshPage();
			WebDriverManager.getCurrentDriver().navigate().to(Url);
			accountOverviewPage.refreshPage();
			if (loginPage.signInButton.checkIfElementIsPresent()
					&& loginPage.signInButton.checkIfElementIsDisplayed()) {
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			}
			Assertions.passTest("Home Page", "Searched the deleted account successfully");
			Assertions.verify(accountOverviewPage.unDeleteAccountBtn.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// click on undelete account
			accountOverviewPage.unDeleteAccountBtn.scrollToElement();
			accountOverviewPage.unDeleteAccountBtn.click();
			accountOverviewPage.quoteAccountButton.checkIfElementIsPresent();
			accountOverviewPage.quoteAccountButton.scrollToElement();
			Assertions.passTest("Account Overview Page", "Clicked on Undelete Account button");
			Assertions.addInfo("Account Overview Page",
					"Verifying the Presence of quote Account Button and delete Account Button");
			Assertions.verify(accountOverviewPage.quoteAccountButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Quote Account Button displayed is verified", false, false);
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Delete Account Button displayed is verified", false, false);

			// Signing out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Login as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as Producer successfully");
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			// Search the account using insured name
			homePage.producerAccountNameSearchTextbox.scrollToElement();
			homePage.producerAccountNameSearchTextbox.setData(insuredName);
			homePage.producerAccountFindButton.scrollToElement();
			homePage.producerAccountFindButton.click();
			homePage.producerAccountNameLink.waitTillPresenceOfElement(60);
			homePage.producerAccountNameLink.waitTillVisibilityOfElement(60);
			homePage.producerAccountNameLink.scrollToElement();
			homePage.producerAccountNameLink.click();
			Assertions.passTest("Home Page", "Searched the Account " + insuredName + " successfully");

			Assertions.verify(accountOverviewPage.quoteAccountButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click on building link
			accountOverviewPage.buildingLink1.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink1.formatDynamicPath(1, 1).click();

			// click on edit building
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Building");
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page loaded successfully", false, false);

			// changing the occupancy type
			Assertions.addInfo("Building Page", "Changeing the occupancy type=Restaurant");
			testData = data.get(dataValue2);
			buildingPage.editBuildingOccupancyPNB(testData, dataValue2, dataValue2);
			buildingPage.editRoofDetailsPNB(testData, dataValue2, dataValue2);
			buildingPage.editAdditionalBuildingInformationPNB(testData, dataValue2, dataValue2);
			buildingPage.editBuildingValuesPNB(testData, dataValue2, dataValue2);
			buildingPage.reviewBuilding();

			// click on location link
			buildingPage.locationLink.formatDynamicPath(1).scrollToElement();
			buildingPage.locationLink.formatDynamicPath(1).click();
			Assertions.passTest("Location Page", "Location Page loaded successfully");

			// click on edit location
			locationPage.editLocation.scrollToElement();
			locationPage.editLocation.click();
			Assertions.passTest("Location Page", "Clicked on Edit location");
			locationPage.enterLocationDetails(testData);

			// click on create quote button
			locationPage.createQuoteButton.scrollToElement();
			locationPage.createQuoteButton.click();
			Assertions.passTest("Location Page", "Clicked on create quote button");

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
					"Refer Quote Page loaded successfully", false, false);

			// Asserting the referral messages
			Assertions.addInfo("Refer Quote Page",
					"Assert the Coverage A referral message and other referral messages after adding the apc's in location page");
			// Assertions.verify(referQuotePage.covA6MReferralMsg.checkIfElementIsDisplayed(),
			// true, "Refer Quote Page",
			// "The message " + referQuotePage.covA6MReferralMsg.getData() + " displayed is
			// verified", false, false);
			Assertions.verify(referQuotePage.referralMessages.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Refer Quote Page", "The message " + referQuotePage.referralMessages.formatDynamicPath(1).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(referQuotePage.referralMessages.formatDynamicPath(2).checkIfElementIsDisplayed(), true,
					"Refer Quote Page", "The message " + referQuotePage.referralMessages.formatDynamicPath(2).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(referQuotePage.referralMessages.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"Refer Quote Page", "The message " + referQuotePage.referralMessages.formatDynamicPath(3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(referQuotePage.referralMessages.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"Refer Quote Page", "The message " + referQuotePage.referralMessages.formatDynamicPath(4).getData()
							+ " displayed is verified",
					false, false);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// TC99 merged to TC98
			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");

			// Searching the TC98 Account
			testData = data.get(dataValue4);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.goToHomepage.click();
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.scrollToElement();
			homePage.findFilterAccountOption.waitTillPresenceOfElement(60);
			homePage.findFilterAccountOption.waitTillVisibilityOfElement(60);
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.scrollToElement();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			Assertions.passTest("Home Page", "Searched the Account " + insuredName + " successfully");
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// click on edit location
			accountOverviewPage.waitTime(2);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit location");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			Assertions.addInfo("Location Page", "Removing the APC Details");
			locationPage.apc_Value.formatDynamicPath("Awnings").clearData();
			locationPage.apc_Value.formatDynamicPath("Catwalks").clearData();
			locationPage.apc_Value.formatDynamicPath("Carports").clearData();
			locationPage.apc_Value.formatDynamicPath("Fences").clearData();
			locationPage.apc_Value.formatDynamicPath("Fountains").clearData();
			locationPage.apc_Value.formatDynamicPath("Machinery").clearData();
			locationPage.apc_Value.formatDynamicPath("Other Structures").clearData();
			locationPage.apc_Value.formatDynamicPath("Driveways").clearData();
			locationPage.apc_Value.formatDynamicPath("Playground").clearData();
			locationPage.apc_Value.formatDynamicPath("Pools").clearData();
			locationPage.apc_Value.formatDynamicPath("Light Poles").clearData();
			locationPage.apc_Value.formatDynamicPath("Underground").clearData();
			locationPage.apc_Value.formatDynamicPath("Satellite").clearData();
			locationPage.apc_Value.formatDynamicPath("Not Fully Enclosed").clearData();
			Assertions.passTest("Location Page", "Removed the APC's successfully");
			locationPage.additionalPropertyCoverages_No.scrollToElement();
			locationPage.additionalPropertyCoverages_No.click();
			Assertions.passTest("Location Page", "Clicked on No radio button");

			// Entering wrong Building address
			Assertions.addInfo("Building Page", "Entering Wrong Building Address");
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page loaded successfully", false, false);
			buildingPage.manualEntry.scrollToElement();
			buildingPage.manualEntry.click();

			Assertions.addInfo("Building Page",
					"Building Address1 original Value : " + buildingPage.manualEntryAddress.getData().replace(",", ""));
			buildingPage.manualEntryAddress.setData(testData.get("L1B1-BldgAddr1"));
			Assertions.passTest("Building Page",
					"Building Address1 Latest Value : " + buildingPage.manualEntryAddress.getData().replace(",", ""));
			Assertions.addInfo("Building Page",
					"Building City original Value : " + buildingPage.manualEntryCity.getData());
			buildingPage.manualEntryCity.setData(testData.get("L1B1-BldgCity"));
			Assertions.passTest("Building Page",
					"Building City Latest Value : " + buildingPage.manualEntryCity.getData());
			Assertions.addInfo("Building Page",
					"Building Zipcode original Value : " + buildingPage.manualEntryZipCode.getData());
			buildingPage.manualEntryZipCode.setData(testData.get("L1B1-BldgZIP"));
			Assertions.passTest("Building Page",
					"Building Zipcode Latest Value : " + buildingPage.manualEntryZipCode.getData());

			// Entering Protection class
			buildingPage.additionalInfoLink.scrollToElement();
			buildingPage.additionalInfoLink.click();
			if (!testData.get("L1B1-ProtectionClassOverride").equals("")) {
				if (!testData.get("L1B1-ProtectionClassOverride").equalsIgnoreCase("")) {
					buildingPage.protectionClass.setData(testData.get("L1B1-ProtectionClassOverride"));
				}
			}
			Assertions.passTest("Building Page", "Entered the Wrong address successfully");

			// click on review building
			buildingPage.waitTime(2);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();

			// click on leave ineligible
			buildingPage.leaveIneligible.scrollToElement();
			buildingPage.leaveIneligible.click();
			Assertions.passTest("Building Page", "Clicked on leave ineligible button");

			// Assert the Error message
			Assertions.addInfo("Building Page", "Assert the Wrong Address error message when wrong address is entered");
			Assertions.verify(buildingPage.wrongAddresserrorMessage.checkIfElementIsDisplayed(), true, "Building Page",
					"The address of the Building could not be found or verified message displayed is verified", false,
					false);

			// Signing out as Producer
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Search the account using Account name
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterAccountOption.scrollToElement();
			homePage.findFilterAccountOption.waitTillPresenceOfElement(60);
			homePage.findFilterAccountOption.waitTillVisibilityOfElement(60);
			homePage.findFilterAccountOption.click();
			homePage.findAccountNamedInsured.scrollToElement();
			homePage.findAccountNamedInsured.setData(insuredName);
			homePage.findBtnAccount.waitTillVisibilityOfElement(60);
			homePage.findBtnAccount.scrollToElement();
			homePage.findBtnAccount.click();
			Assertions.passTest("Home Page", "Searched the Account " + insuredName + " successfully");
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// click on building link
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();

			// click on edit building
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Building");
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page loaded successfully", false, false);
			testData = data.get(dataValue5);
			Assertions.addInfo("Building Page", "Enter the Correct Building Address");
			Assertions.addInfo("Building Page",
					"Building Address1 original Value : " + buildingPage.manualEntryAddress.getData().replace(",", ""));
			buildingPage.manualEntryAddress.setData(testData.get("L1B1-BldgAddr1"));
			Assertions.passTest("Building Page",
					"Building Address1 Latest Value : " + buildingPage.manualEntryAddress.getData().replace(",", ""));
			Assertions.addInfo("Building Page",
					"Building City original Value : " + buildingPage.manualEntryCity.getData());
			buildingPage.manualEntryCity.setData(testData.get("L1B1-BldgCity"));
			Assertions.passTest("Building Page",
					"Building City Latest Value : " + buildingPage.manualEntryCity.getData());
			Assertions.addInfo("Building Page",
					"Building Zipcode original Value : " + buildingPage.manualEntryZipCode.getData());
			buildingPage.manualEntryZipCode.setData(testData.get("L1B1-BldgZIP"));
			Assertions.passTest("Building Page",
					"Building Zipcode Latest Value : " + buildingPage.manualEntryZipCode.getData());
			Assertions.passTest("Building Page", "Entered the Correct address successfully");
			testData = data.get(dataValue4);
			buildingPage.lattitude.scrollToElement();
			buildingPage.lattitude.setData(testData.get("OverrideLatitude"));
			buildingPage.lattitude.tab();
			buildingPage.longitude.scrollToElement();
			buildingPage.longitude.setData(testData.get("OverrideLongitude"));
			buildingPage.longitude.tab();
			Assertions.passTest("Building Page", "Entered the Latitude and Longitude values successfully");

			// Override Protection class
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);

			// click on review building
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();

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

			// search the account as producer
			homePage.producerAccountNameSearchTextbox.scrollToElement();
			homePage.producerAccountNameSearchTextbox.setData(insuredName);
			homePage.producerAccountFindButton.scrollToElement();
			homePage.producerAccountFindButton.click();
			if(homePage.noResultsFound.checkIfElementIsPresent()) {
				homePage.refreshPage();
			}
			homePage.producerAccountNameLink.waitTillPresenceOfElement(60);
			homePage.producerAccountNameLink.waitTillVisibilityOfElement(60);
			homePage.producerAccountNameLink.scrollToElement();
			homePage.producerAccountNameLink.click();
			Assertions.passTest("Home Page", "Searched the Account " + insuredName + " successfully");
			Assertions.verify(accountOverviewPage.deleteAccount.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			if (accountOverviewPage.quoteAccountButton.checkIfElementIsPresent()
					&& accountOverviewPage.quoteAccountButton.checkIfElementIsDisplayed()) {
				accountOverviewPage.quoteAccountButton.scrollToElement();
				accountOverviewPage.quoteAccountButton.click();
			} else {
				accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
				accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
				accountOverviewPage.editBuilding.scrollToElement();
				accountOverviewPage.editBuilding.click();

				// click on review building
				buildingPage.reviewBuilding();

				// to change the address added this logic
				boolean addressFound = false;
				if (buildingPage.addressMsg.checkIfElementIsPresent()
						&& buildingPage.addressMsg.checkIfElementIsDisplayed() && !addressFound) {
					for (int i = 1; i <= 10; i++) {
						if (buildingPage.editBuilding.checkIfElementIsPresent()) {
							buildingPage.editBuilding.scrollToElement();
							buildingPage.editBuilding.click();
						}
						buildingPage.manualEntry.click();
						buildingPage.manualEntryAddress.waitTillVisibilityOfElement(60);
						buildingPage.manualEntryAddress.setData(buildingPage.manualEntryAddress.getData().replace(
								buildingPage.manualEntryAddress.getData().replaceAll("[^0-9]", ""),
								(Integer.parseInt(buildingPage.manualEntryAddress.getData().replaceAll("[^0-9]", "")))
										+ 1 + ""));
						buildingPage.reviewBuilding();

						if (!buildingPage.addressMsg.checkIfElementIsPresent()
								|| !buildingPage.addressMsg.checkIfElementIsPresent()) {
							addressFound = true;
							break;
						}
					}
				}
			}

			// click on create quote
			if (buildingPage.createQuote.checkIfElementIsPresent()
					&& buildingPage.createQuote.checkIfElementIsDisplayed()) {
				buildingPage.createQuote.scrollToElement();
				buildingPage.createQuote.click();
			}

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 98", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 98", "Executed Successfully");
			}
		}
	}
}
