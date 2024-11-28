/** Program Description: 1.Create an AOP quote and add producer fee and producer inspection fee from Quote Page
 * 2.As Producer, Check the Green endorsement functionality for Commercial product with Premium calculation[Green Endorsement. Pavan Mule 10/09/2023]
 *  Author			   :  Vinay
 *  Date of Creation   : 11/14/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HealthDashBoardPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyInspectorPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC026 extends AbstractCommercialTest {

	public TC026() {
		super(LoginType.PRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID026.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		HealthDashBoardPage healthDashBoardPage = new HealthDashBoardPage();
		PolicyInspectorPage policyInspectorPage = new PolicyInspectorPage();

		// Initializing variables
		String quoteNumberProducer;
		String quoteNumberUSM;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Enter Login Details
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);

			// click on User Preference link and enter the details
			Assertions.passTest("Preference Options Page", "Adding Broker Fee");
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");
			Assertions.verify(preferenceOptionsPage.savePreferences.checkIfElementIsDisplayed(), true,
					"Preference Options Page", "Preferences Page loaded sucessfully", false, false);

			// Clicked on Taxes and Fees Tab
			preferenceOptionsPage.taxesAndFeesTab.scrollToElement();
			preferenceOptionsPage.taxesAndFeesTab.click();
			Assertions.passTest("Preference Options Page", "Taxes and Fees Tab clicked successfully");
			Assertions.verify(preferenceOptionsPage.brokerFeeCheckbox.checkIfElementIsDisplayed(), true,
					"Preference Options Page", "Taxes and Fees Tab loaded successfully", false, false);

			// Add Broker Fees
			preferenceOptionsPage.addBrokerFees(testData);
			Assertions.passTest("Preference Options Page",
					"Broker Fees checkbox and Surplus Lines,Taxes and Fees checkbox are also selected successfully");

			// Sign Out as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in as USM successfully");

			// Navigate to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering Zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// enter Prior Loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Account Overview Page
			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumberUSM = accountOverviewPage.quoteNumber.formatDynamicPath(data_Value2).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumberUSM);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"The ICAT fees value : " + accountOverviewPage.feesValue.getData(), false, false);

			// Click on Edit fees and add producer fee and Producer inspection fee
			// Code for IO-19437 (Checking as an USM)
			Assertions.verify(
					(accountOverviewPage.otherFees.checkIfElementIsDisplayed() && accountOverviewPage.otherFees
							.getData().replace("$", "").equals(testData.get("BrokerFeeValue"))),
					true, "Account Overview Page", "Broker Fees Before Deleting in Accout Overview Page is: "
							+ accountOverviewPage.otherFees.getData(),
					false, false);

			// Click On Edit Fees
			accountOverviewPage.editFees.scrollToElement();
			accountOverviewPage.editFees.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Fees Link");
			Assertions.verify(
					accountOverviewPage.customFeeDeleteTrashCan.formatDynamicPath(data_Value2)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Delete Trash Can for Custom Fee loaded successfully.", false,
					false);

			// Clicked On Custom Fee Trash Can
			accountOverviewPage.customFeeDeleteTrashCan.formatDynamicPath(data_Value2).scrollToElement();
			accountOverviewPage.customFeeDeleteTrashCan.formatDynamicPath(data_Value2).click();
			Assertions.passTest("Account Overview Page", "Clicked on Trash can Button");
			Assertions.verify(accountOverviewPage.customeFeeSave.checkIfElementIsDisplayed(), true,
					"Account OverView Page", "Save Button for Custom Fee is loaded successfully", false, false);

			// Clicked On Custom Fee Save Button
			accountOverviewPage.customeFeeSave.scrollToElement();
			accountOverviewPage.customeFeeSave.click();
			Assertions.passTest("Account Overview Page", "Clicked on Save Button");
			accountOverviewPage.customeFeeSave.waitTillInVisibilityOfElement(60);

			// Checking whether the Broker Fee is removed after Deleting the Fee
			Assertions.addInfo("Scenario 01",
					"Checking whether the Broker Fee Value is removed after clicking the Delete Trash Can and saving the changes as USM");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsDisplayed(), false, "Account Overview Page",
					"The Value of the other fees after the broker Fees is removed is verified", false, false);
			Assertions.verify(accountOverviewPage.editFees.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Edit Fees Icon loaded successfully", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Edit Fee Link is Clicked
			accountOverviewPage.editFees.scrollToElement();
			accountOverviewPage.editFees.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit fees");
			Assertions.verify(
					accountOverviewPage.customFieldName.formatDynamicPath(data_Value2).checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Entering Name and Value for Custom Fee 1", false, false);

			// Retrieving the Custom Field Name and Value
			accountOverviewPage.customFieldName.formatDynamicPath(data_Value2).clearData();
			accountOverviewPage.customFieldValue.formatDynamicPath(data_Value2).clearData();
			accountOverviewPage.customFieldName.formatDynamicPath(data_Value2).setData(testData.get("CustomFeeName"));
			accountOverviewPage.customFieldValue.formatDynamicPath(data_Value2).setData(testData.get("CustomFeeValue"));
			int ProducerValue = Integer.parseInt(testData.get("CustomFeeValue"));
			testData = data.get(data_Value2);

			// Adding Custom Fee
			accountOverviewPage.addCustomFee.scrollToElement();
			accountOverviewPage.addCustomFee.click();
			Assertions.passTest("Account Overview Page", "Clicked on Add Custom Fee Button");
			Assertions.verify(
					accountOverviewPage.customFieldName.formatDynamicPath(data_Value3).checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Entering name and value for Custom Fee 2", false, false);

			// Entering the Retrieved Custom Fee Values
			accountOverviewPage.customFieldName.formatDynamicPath(data_Value3).setData(testData.get("CustomFeeName"));
			accountOverviewPage.customFieldValue.formatDynamicPath(data_Value3).setData(testData.get("CustomFeeValue"));
			accountOverviewPage.customeFeeSave.scrollToElement();
			accountOverviewPage.customeFeeSave.click();
			Assertions.passTest("Account Overview Page", "Clicked on Custom Fee save Button");
			accountOverviewPage.customeFeeSave.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Account Overview Page", "Added the Other fees successfully");
			accountOverviewPage.refreshPage();
			int ProducerInpectionValue = Integer.parseInt(testData.get("CustomFeeValue"));
			int FeeVal = ProducerValue + ProducerInpectionValue;
			Assertions.passTest("Account Overview Page", "The Calculated other fees value : " + "$" + FeeVal);
			Assertions.passTest("Account Overview Page",
					"The Actual other fees value : " + accountOverviewPage.otherFees.getData().replace(".00", ""));
			Assertions.verify(accountOverviewPage.otherFees.getData().replace(".00", ""), "$" + FeeVal,
					"Account Overview Page", "The Calculated and Actual other fees values are equal", false, false);

			// Click on Request bind
			testData = data.get(data_Value1);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumberUSM);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumberUSM);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approving Referral
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

			// click on quote number link
			policySummarypage.quoteNoLink.formatDynamicPath(quoteNumberUSM).click();
			Assertions.passTest("Policy Summary page", "Clicked on Quote Number link");

			// Asserting Fee Details on View/Print Full Quote Page
			Assertions.addInfo("Scenario 02", "Asserting Fee Details on View/Print Full Quote Page");
			viewOrPrintFullQuotePage.customerFeeName.formatDynamicPath(testData.get("CustomFeeName"))
					.waitTillVisibilityOfElement(60);
			Assertions.verify(
					viewOrPrintFullQuotePage.customerFeeValue.formatDynamicPath("Insurer Inspection Fee")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Insurer Inspection Fee :" + viewOrPrintFullQuotePage.customerFeeValue
							.formatDynamicPath("Insurer Inspection Fee").getData(),
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.customerFeeValue.formatDynamicPath("Insurer Policy Fee")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"Insurer Policy Fee : " + viewOrPrintFullQuotePage.customerFeeValue
							.formatDynamicPath("Insurer Policy Fee").getData(),
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.customerFeeValue.formatDynamicPath(testData.get("CustomFeeName"))
							.getData(),
					"$" + testData.get("CustomFeeValue") + ".00", "View/Print Full Quote Page",
					"Producer Fee : " + viewOrPrintFullQuotePage.customerFeeValue
							.formatDynamicPath(testData.get("CustomFeeName")).getData(),
					false, false);
			testData = data.get(data_Value2);
			Assertions.verify(
					viewOrPrintFullQuotePage.customerFeeValue.formatDynamicPath(testData.get("CustomFeeName"))
							.getData(),
					"$" + testData.get("CustomFeeValue") + ".00", "View/Print Full Quote Page",
					"Producer Inspection Fee : " + viewOrPrintFullQuotePage.customerFeeValue
							.formatDynamicPath(testData.get("CustomFeeName")).getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Signing Out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign Out button is clicked");

			// Logging in as Producer
			// Code for IO-19437 (Checking as a Producer)
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login Page loaded successfully", false, false);
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			testData = data.get(data_Value3);

			// Home Page of Producer
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);

			// Eligibility Page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page is loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Location Page
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location Details entered successfully");

			// Building Page
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page Loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Building Details entered successfully");

			// Selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Prior Losses Page
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Losses Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Losses Page", "Prior Loss Details are entered successfully");

			// Create Quote Page
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote Details entered successfully");

			// Account Overview Page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumberProducer = accountOverviewPage.quoteNumber.formatDynamicPath(data_Value2).getData().substring(1,
					11);
			Assertions.passTest("Account Overview Page",
					"Quote Number created as a Producer is " + quoteNumberProducer);

			// Asserting Broker Fee before deleting
			accountOverviewPage.otherFees.waitTillPresenceOfElement(60);
			accountOverviewPage.otherFees.scrollToElement();
			Assertions.verify(
					(accountOverviewPage.otherFees.checkIfElementIsDisplayed() && accountOverviewPage.otherFees
							.getData().replace("$", "").equals(testData.get("BrokerFeeValue"))),
					true, "Account Overview Page",
					"The Broker Fee before deleting is: " + accountOverviewPage.otherFees.getData(), false, false);

			// Click on Edit Fees
			accountOverviewPage.editFees.scrollToElement();
			accountOverviewPage.editFees.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Fees successfully");

			// Click on Delete Trash Can
			Assertions.verify(accountOverviewPage.addCustomFee.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Add a fee is loaded successfully", false, false);
			accountOverviewPage.customFeeDeleteTrashCan.formatDynamicPath(data_Value2).scrollToElement();
			accountOverviewPage.customFeeDeleteTrashCan.formatDynamicPath(data_Value2).click();
			Assertions.passTest("Account Overview Page", "Clicked on Trash Can successfully");

			// Click on Save Button
			Assertions.verify(accountOverviewPage.customeFeeSave.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Save Button for Custom Fee loaded successfully", false, false);
			accountOverviewPage.customeFeeSave.scrollToElement();
			accountOverviewPage.customeFeeSave.click();
			Assertions.passTest("Account Overview Page", "Clicked on Custom Fee Save Button successfully");
			accountOverviewPage.customeFeeSave.waitTillInVisibilityOfElement(60);

			// Fee Value after deleting Broker Fee
			Assertions.addInfo("Scenario 03",
					"Checking whether the Broker Fee Value is removed after clicking the Delete Trash Can and saving the changes as Producer");
			Assertions.verify(accountOverviewPage.otherFees.checkIfElementIsDisplayed(), false, "Account Overview Page",
					"The Value of the other fees after the broker Fees is removed is verified", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Account overview page", "Clicked on home page button successfully");

			// creating New account
			testData = data.get(data_Value2);
			// Home Page of Producer
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);

			// Eligibility Page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page is loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Location Page
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location Details entered successfully");

			// Building Page
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page Loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Building Details entered successfully");

			// Selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Verifying the absence of green upgrades option in create quote page, When
			// BI values, building value and BPP values not added
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 04",
					"Verifying the absence of green upgrades option in create quote page,When BI values,building value and BPP value not added");
			Assertions.verify(createQuotePage.greenUpgradesLabel.checkIfElementIsPresent(), false, "Create Quote Page",
					"Green Upgrades option not displayed verified", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Adding BI value not building and BPP value and verify the absence of green
			// upgrades option not display on create quote page
			testData = data.get(data_Value1);
			createQuotePage.bIValue.formatDynamicPath(0).scrollToElement();
			createQuotePage.bIValue.formatDynamicPath(0).appendData(testData.get("L1-LocBI"));
			createQuotePage.bIValue.formatDynamicPath(0).tab();
			Assertions.addInfo("Scenario 05",
					"Verifying the absence of green upgrades option in create quote page,When BI values added and not building value and BPP value");
			Assertions.verify(createQuotePage.greenUpgradesLabel.checkIfElementIsPresent(), false, "Create Quote Page",
					"Green Upgrades option not displayed verified", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Add BPP value and remove BI value, verify the presence of green upgrades
			// option and default value is 'NO'
			createQuotePage.bIValue.formatDynamicPath(0).scrollToElement();
			createQuotePage.bIValue.formatDynamicPath(0).clearData();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgBPP"));
			createQuotePage.bPPValue.formatDynamicPath(0, 0).tab();
			Assertions.addInfo("Scenario 06",
					"Verifying the presence of green upgrades option in create quote page,When BPP values is added not building value");
			Assertions.verify(createQuotePage.greenUpgradesLabel.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Green Upgrades option displayed verified", false, false);
			Assertions.verify(createQuotePage.greenUpgradesData.getData().equalsIgnoreCase("NO"), true,
					"Create Quote Page",
					"Green upgrades default value is '" + createQuotePage.greenUpgradesData.getData() + "' displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Adding building value and removing BPP value and verifying the presence of
			// green upgrades option and default value is 'NO'
			createQuotePage.bPPValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).tab();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			Assertions.addInfo("Scenario 07",
					"Verifying the presence of green upgrades option in create quote page,When building values is added not BPP value");
			Assertions.verify(createQuotePage.greenUpgradesLabel.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Green Upgrades option displayed verified", false, false);
			Assertions.verify(createQuotePage.greenUpgradesData.getData().equalsIgnoreCase("NO"), true,
					"Create Quote Page",
					"Green upgrades default value is '" + createQuotePage.greenUpgradesData.getData() + "' displayed",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumberProducer = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number 1 :  " + quoteNumberProducer);

			// Click on view/print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view/print full quote page loaded successfully");

			// Verifying the green endorsement is not selected
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View print full quote page", "view print full quote page loaded successfully", false, false);
			Assertions.addInfo("Secnario 08", "Verifying the green endorsement is not selected");
			Assertions.verify(
					viewOrPrintFullQuotePage.greenUpgradesOption
							.formatDynamicPath(2).getData().equalsIgnoreCase("Not selected"),
					true, "View/print full quote page",
					"The green upgrade option is "
							+ viewOrPrintFullQuotePage.greenUpgradesOption.formatDynamicPath(2).getData() + " verified",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View/print full quote page", "Clicked on back button successfully");

			// Click on edit deductible button
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit/deductible limits button successfully");

			// Select green upgrade as yes
			createQuotePage.ordinanceLawArrow.scrollToElement();
			createQuotePage.ordinanceLawArrow.click();

			createQuotePage.ordinanceLawOption.formatDynamicPath("10%").scrollToElement();
			createQuotePage.ordinanceLawOption.formatDynamicPath("10%").click();

			createQuotePage.greenUpgradesArrow.scrollToElement();
			createQuotePage.greenUpgradesArrow.click();
			createQuotePage.greenUpgradesYesOption.scrollToElement();
			createQuotePage.greenUpgradesYesOption.click();
			Assertions.verify(createQuotePage.greenUpgradesData.getData().equalsIgnoreCase("Yes"), true,
					"Create quote page", "The green upgrades option selected as "
							+ createQuotePage.greenUpgradesData.getData() + " is verified",
					false, false);

			// click on get a quote button
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote page", "Clicked on get quote button successfully");

			// Getting quote number 2
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			String quoteNumberTwoProducer = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1,
					11);
			Assertions.passTest("Account Overview Page", "Quote Number 2 :  " + quoteNumberTwoProducer);

			// Getting premium value for quote 2
			String quoteNumberTwoPremiumValue = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",",
					"");
			Assertions.passTest("Account overview page",
					"Quote number 1 premium value is " + quoteNumberTwoPremiumValue);

			// Click on view/print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view/print full quote page loaded successfully");

			// Verifying the green endorsement is selected
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View print full quote page", "view print full quote page loaded successfully", false, false);
			Assertions.addInfo("Secnario 09", "Verifying the green endorsement is selected");
			Assertions
					.verify(viewOrPrintFullQuotePage.greenUpgradesInclude.getData().equalsIgnoreCase("Included"), true,
							"View/print full quote page", "The green upgrade option is "
									+ viewOrPrintFullQuotePage.greenUpgradesInclude.getData() + " verified",
							false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Logout as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home page", "Logout as producer successfully");

			// login as admin(swilcox)
			Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
					"Login Page loaded successfully", false, false);
			loginPage.enterLoginDetails("swilcox", setUpData.get("Password"));
			Assertions.verify(homePage.adminLink.checkIfElementIsDisplayed(), true, "home page",
					"Home page loaded successfully", false, false);

			// Click on admin link
			homePage.adminLink.scrollToElement();
			homePage.adminLink.click();
			Assertions.passTest("Home page", "Clicked on admin link successfully");

			// Click on policy inspector link
			Assertions.verify(healthDashBoardPage.policyInspectorLink.checkIfElementIsDisplayed(), true,
					"Health dash board page", "Health dash board page loaded successfully", false, false);
			healthDashBoardPage.policyInspectorLink.scrollToElement();
			healthDashBoardPage.policyInspectorLink.click();

			// Search for quote 1
			Assertions.verify(policyInspectorPage.backButton.checkIfElementIsDisplayed(), true, "Policy inspector page",
					"Policy inspector page", false, false);
			policyInspectorPage.enterQuoteNumber.appendData(quoteNumberProducer);
			policyInspectorPage.findPolicyutton.scrollToElement();
			policyInspectorPage.findPolicyutton.click();
			Assertions.passTest("Policy inspector page", "Quote number1 searched successfully");

			// Getting premium from building section
			String premiumBuildingSection = policyInspectorPage.premiumFromBuildingSection.formatDynamicPath(12)
					.getData();
			double d_premiumBuildingSection = Double.parseDouble(premiumBuildingSection);

			// Calculating quote number 2 premium value, quotenumber2 premium = [premium of
			// quote1 building section *15%]+premium of quote1 building section + premium of
			// quote1 ordinance law section
			double premiumPercentage = 0.15;
			double calPremiumofQuote2 = (d_premiumBuildingSection * premiumPercentage) + d_premiumBuildingSection;

			// Click on back button
			policyInspectorPage.backButton.scrollToElement();
			policyInspectorPage.backButton.click();
			Assertions.passTest("Policy inspector page", "Clicked on back button successfully");

			// Click on policy inspector link
			Assertions.verify(healthDashBoardPage.policyInspectorLink.checkIfElementIsDisplayed(), true,
					"Health dash board page", "Health dash board page loaded successfully", false, false);
			healthDashBoardPage.policyInspectorLink.scrollToElement();
			healthDashBoardPage.policyInspectorLink.click();

			// Search quote number 2
			Assertions.verify(policyInspectorPage.backButton.checkIfElementIsDisplayed(), true, "Policy inspector page",
					"Policy inspector page", false, false);
			policyInspectorPage.enterQuoteNumber.appendData(quoteNumberTwoProducer);
			policyInspectorPage.findPolicyutton.scrollToElement();
			policyInspectorPage.findPolicyutton.click();
			Assertions.passTest("Policy inspector page", "Quote number2 searched successfully");

			// Getting premium from building section of quote2
			String premiumBuildingSectionQuote2 = policyInspectorPage.premiumFromBuildingSection.formatDynamicPath(12)
					.getData();
			double d_premiumBuildingSectionQuote2 = Double.parseDouble(premiumBuildingSectionQuote2);

			// Getting premium from ordinance law section of quote2
			String premiumOrdinanceLawSection = policyInspectorPage.premiumFromOrdinanceSection.formatDynamicPath(12)
					.getData();
			double d_premiumOrdinanceLawSection = Double.parseDouble(premiumOrdinanceLawSection);

			// Calculating quote number 2 premium value
			double calQuotePremium = d_premiumBuildingSectionQuote2 + d_premiumOrdinanceLawSection;

			// Verifying calculated quote2 premium and actual quote2 premium from policy
			// inspector page
			Assertions.addInfo("Scenario 10",
					"Verifying calculated quote2 premium and actual quote2 premium from policy inspector page");
			if (Precision.round(Math.abs(Precision.round((d_premiumBuildingSectionQuote2), 2))
					- Precision.round(calPremiumofQuote2, 2), 2) < 0.05) {
				Assertions.passTest("Policy inspector page",
						"Calculated premimu of quote 2 : " + "$" + (calPremiumofQuote2));
				Assertions.passTest("Policy inspector page",
						"Actual premium of quote 2 : " + "$" + d_premiumBuildingSectionQuote2);
			} else {
				Assertions.verify(d_premiumBuildingSectionQuote2, calPremiumofQuote2, "Policy inspector page",
						"The Difference between actual premium and calculated premium is more than 0.05", false, false);

			}

			// Verifying quote2 premium from policy inspector page and quote2 premium from
			// account overview page
			if (Precision.round(Math.abs(Precision.round((calQuotePremium), 2))
					- Precision.round(Double.parseDouble(quoteNumberTwoPremiumValue), 2), 2) < 0.05) {
				Assertions.passTest("Policy inspector page",
						"Calculated premimu of quote 2 : " + "$" + (calQuotePremium));
				Assertions.passTest("Policy inspector page",
						"Actual premium of quote 2 : " + "$" + quoteNumberTwoPremiumValue);
			} else {
				Assertions.verify(calQuotePremium, quoteNumberTwoPremiumValue, "Policy inspector page",
						"The Difference between actual premium and calculated premium is more than 0.05", false, false);

			}
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 26", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 26", "Executed Successfully");
			}
		}
	}
}