/** Program Description:  Check if the system provides a hardstop when the Total Coverage values exceeds the Threshold values for NJ and FL quotes
 *  Author			   : Sowndarya
 *  Date of Creation   : 29/07/2021
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
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
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC007 extends AbstractCommercialTest {

	public TC007() {
		super(LoginType.RETAILPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID007.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		Map<String, String> testData;
		String quoteNumber;
		boolean isTestPassed = false;

		try {
			for (int i = 0; i <= 1; i++) {
				testData = data.get(i);

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// creating New account
				Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
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

				// Entering Location 1 Dwelling 1 Details
				//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
						//"Building Page loaded successfully", false, false);
				buildingPage.enterBuildingDetails(testData);

				// selecting peril
				if (!testData.get("Peril").equals("")) {
					Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true,
							"Select Peril Page", "Select Peril Page loaded successfully", false, false);
					selectPerilPage.selectPeril(testData.get("Peril"));
				}

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
				Assertions.passTest("Create Quote Page",
						"Building Value Entered is " + "$" + testData.get("L1B1-BldgValue"));
				Assertions.passTest("Create Quote Page", "Quote details entered successfully");

				// Refer Quote for binding
				if (referQuotePage.referQuote.checkIfElementIsPresent()
						&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
					Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
							"Refer Quote Page Loaded successfully", false, false);
					referQuotePage.contactName.setData(testData.get("ProducerName"));
					referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
					referQuotePage.referQuote.click();
					quoteNumber = referQuotePage.quoteNum.getData();
					Assertions.passTest("Referral Quote Page", "Quote Number :  " + quoteNumber);

					// Logout as producer
					homePage.goToHomepage.scrollToElement();
					homePage.goToHomepage.click();
					homePage.signOutButton.scrollToElement();
					homePage.signOutButton.click();

					// Login as USM
					loginPage.refreshPage();
					loginPage.waitTime(3);// wait time is needed to load the page
					loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

					// searching the quote number in grid and clicking on the quote link
					Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
							"USM Home page loaded successfully", false, false);
					homePage.searchQuote(quoteNumber);
					Assertions.passTest("Home Page", "Quote for referral is searched successfully");
					Assertions.verify(accountOverviewPage.openReferralLink.checkIfElementIsDisplayed(), true,
							"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

					// Click on Open Referral Link
					accountOverviewPage.openReferralLink.scrollToElement();
					accountOverviewPage.openReferralLink.click();
					Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

					Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
							"Referral page loaded successfully", false, false);
					if (referralPage.pickUp.checkIfElementIsPresent()
							&& referralPage.pickUp.checkIfElementIsDisplayed()) {
						referralPage.pickUp.scrollToElement();
						referralPage.pickUp.click();
					}
					referralPage.approveOrDeclineRequest.waitTillButtonIsClickable(60);
					referralPage.approveOrDeclineRequest.scrollToElement();
					referralPage.approveOrDeclineRequest.click();
					referralPage.scrollToBottomPage();
					referralPage.internalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
					referralPage.externalComments.setData(testData.get("PremiumAdjustment_InternalComments"));
					referralPage.approveRequest.waitTillButtonIsClickable(60);
					referralPage.approveRequest.scrollToElement();
					referralPage.approveRequest.click();
					Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

					// Searching the account
					homePage.goToHomepage.click();
					homePage.searchQuote(quoteNumber);
				}

				// getting the quote number
				Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
						"Account Overview Page", "Account Overview Page loaded successfully", false, false);
				quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
				Assertions.passTest("Account Overview Page",
						"Quote Created is verified. Quote Number :  " + quoteNumber);

				// Click on Create Another Quote Button
				accountOverviewPage.createAnotherQuote.scrollToElement();
				accountOverviewPage.createAnotherQuote.click();
				Assertions.passTest("Account Overview Page", "Clicked on Create Another Quote Button");
				Assertions.addInfo("Create Quote Page", "Changing the Building Value From  $5,000,000 to  $6,000,000");

				// Selecting peril
				if (!testData.get("Peril").equals("")) {
					Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true,
							"Select Peril Page", "Select Peril Page loaded successfully", false, false);
					selectPerilPage.selectPeril(testData.get("Peril"));
				}

				// Entering Deductibles
				Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
						"Create Quote Page loaded successfully", false, false);
				createQuotePage.enterDeductiblesCommercialNew(testData);

				// Changing the Building Value to 6000000
				if (!testData.get("L1B2-BldgValue").equals("")) {
					createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
					createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
					createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
					createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
					createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B2-BldgValue"));
					createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
					createQuotePage.loading.waitTillInVisibilityOfElement(60);

				}
				Assertions.passTest("Create Quote Page", "Changed the Building Value from " + "$"
						+ testData.get("L1B1-BldgValue") + " To " + "$" + testData.get("L1B2-BldgValue"));

				// Click on get a quote
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
				Assertions.passTest("Create Quote Page", "Clicked on Get A Quote Button");

				// Asserting the Harstop error message and adding IO-20761
				Assertions.addInfo("Scenario 01",
						"Assert the Hardstop message if the Building value is more than  $5,000,000");
				if (createQuotePage.globalErr.checkIfElementIsPresent()) {
					Assertions.verify(createQuotePage.globalErr.getData().contains("threshold and is ineligible"), true,
							"Create Quote Page",
							"The Harstop Message displayed " + createQuotePage.globalErr.getData()
									+ " is verified for the Building Value more than " + "$"
									+ testData.get("L1B1-BldgValue"),
							false, false);
				} else {
					Assertions.verify(
							createQuotePage.wdrWarning.formatDynamicPath("insured value").checkIfElementIsDisplayed(),
							true, "Create Quote Page",
							"The Warning Message displayed "
									+ createQuotePage.wdrWarning.formatDynamicPath("insured value").getData()
									+ " is verified for the Building Value more than " + "$"
									+ testData.get("L1B1-BldgValue"),
							false, false);
				}
				Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

				// Logout as USM
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				// Login as Producer
				loginPage.refreshPage();
				loginPage.waitTime(3);// wait time is needed to load the page
				loginPage.enterLoginDetails(setUpData.get("RetailProducer"), setUpData.get("Password"));
			}

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC007 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC007 ", "Executed Successfully");
			}
		}
	}
}
