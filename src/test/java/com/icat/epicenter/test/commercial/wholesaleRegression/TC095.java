/** Program Description: Create quote,Enter Building and BPP values as 0 and assert the default value of Coverage extension package and added CR IO-20778
 *  Author			   : John
 *  Date of Creation   : 08/11/2020
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC095 extends AbstractCommercialTest {

	public TC095() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID095.xls";
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
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		LoginPage loginPage = new LoginPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		// Initializing the variables
		String quoteNumber;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		int dataValue2 = 1;
		Map<String, String> testData1 = data.get(dataValue2);
		int dataValue3 = 2;
		String policyNumber;
		String buildingValueNamedStorm;
		String bIValueNamedStorm;
		int buildingValuesNamedStormLen;
		int bIValueNamedStormLen;
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();
			buildingPage.addBuildingDetails(testData, 1, 1);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, 1, 1);
			buildingPage.scrollToBottomPage();
			buildingPage.waitTime(3); // Added waittime as review dwelling is not clicking many times
			buildingPage.reviewBuilding.click();
			buildingPage.override.scrollToElement();
			buildingPage.override.click();
			if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}

			// click on create quote
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();

			// building no longer override
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Enter Building and bpp values as 0
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData1.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			Assertions.passTest("Create Quote Page",
					"Building value is updated to " + createQuotePage.buildingValue.formatDynamicPath(0, 0).getData());
			createQuotePage.bPPValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).appendData(testData1.get("L1B1-BldgBPP"));
			createQuotePage.bPPValue.formatDynamicPath(0, 0).tab();
			createQuotePage.waitTime(2);// need wait time to load the page
			Assertions.passTest("Create Quote Page",
					"BPP value is updated to " + createQuotePage.bPPValue.formatDynamicPath(0, 0).getData());

			// asserting EQB
			Assertions.addInfo("Create Quote Page", "Assert Equipment Breakdown default value");
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.verify(createQuotePage.equipmentBreakdownArrow.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Equipment Breakdown coverage is displayed and default value is "
							+ createQuotePage.equipmentBreakdownData.getData(),
					false, false);

			// Assert for coverage Extension package
			Assertions.addInfo("Create Quote Page", "Assert the absence of coverage Extension package");
			Assertions.verify(
					createQuotePage.coverageExtensionPackageArrow.checkIfElementIsPresent()
							&& createQuotePage.coverageExtensionPackageArrow.checkIfElementIsDisplayed(),
					false, "Create Quote Page", "Coverage Extension Package is not displayed", false, false);

			// Enter deductibles
			createQuotePage.enterDeductiblesCommercialNew(testData1);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			Assertions.passTest("Create Quote Page",
					"Building value is updated to " + createQuotePage.buildingValue.formatDynamicPath(0, 0).getData());
			createQuotePage.bPPValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.bPPValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgBPP"));
			createQuotePage.bPPValue.formatDynamicPath(0, 0).tab();
			Assertions.passTest("Create Quote Page",
					"BPP value is updated to " + createQuotePage.bPPValue.formatDynamicPath(0, 0).getData());

			// Asserting Coverage extension package
			Assertions.addInfo("Create Quote Page",
					"Assert the Coverage extension package default value when Building value and BPP value is 0");
			Assertions.verify(createQuotePage.coverageExtensionPackageArrow.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Coverage Extension Package is displayed and default value is "
							+ createQuotePage.coverageExtensionPackageData.getData(),
					false, false);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			createQuotePage.addAdditionalCoveragesCommercial(testData1);

			// get a quote
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on get a Quote");

			// assert sinkhole warning message
			Assertions.addInfo("Create Quote Page", "Assert the  prior Sinkhole warning message");
			Assertions.verify(
					createQuotePage.warningMessageforAdjustments.formatDynamicPath("1").getData()
							.contains("ineligible due to a prior Sinkhole"),
					true, "Create Quote Page",
					"Warning message " + createQuotePage.warningMessageforAdjustments.formatDynamicPath("1").getData()
							+ " is displayed",
					false, false);

			// click on continue button
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// getting the quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View print full quote link");

			// assert sinkhole form
			Assertions.addInfo("View Print Full Quote Page", "Assert the Sinkole form on View Print Full Quote Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Sinkhole Collapse Exclusion (ICAT SCOL 210(a))")
							.checkIfElementIsDisplayed(),
					true, "View Print Full Quote Page",
					viewOrPrintFullQuotePage.forms.formatDynamicPath("Sinkhole Collapse Exclusion (ICAT SCOL 210(a))")
							.getData() + " is displayed",
					false, false);
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(3);// need wait time to scroll to top
			viewOrPrintFullQuotePage.backButton.click();

			// Assert acount overview page
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// navigate to prior loss page
			Assertions.addInfo("Account Overview Page", "Edit the Prior loss to select Damage Repair question as No");
			accountOverviewPage.editPriorLoss.waitTillVisibilityOfElement(60);
			accountOverviewPage.editPriorLoss.scrollToElement();
			accountOverviewPage.editPriorLoss.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Prior Losses link");

			// click on damage repaired no
			Assertions.verify(priorLossPage.damagesRepairedRadioNo.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss information is displayed", false, false);
			priorLossPage.damagesRepairedRadioNo.scrollToElement();
			priorLossPage.damagesRepairedRadioNo.click();
			priorLossPage.continueButton.scrollToElement();
			priorLossPage.continueButton.click();

			// Assert quote expiry message
			Assertions.addInfo("Account Overview Page", "Assert the loss history Warning message");
			Assertions.verify(
					accountOverviewPage.priorLoses.formatDynamicPath("Due to changes in loss history")
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					accountOverviewPage.priorLoses.formatDynamicPath("Due to changes in loss history").getData()
							+ " is displayed",
					false, false);
			accountOverviewPage.goToAccountOverviewPageBtn.scrollToElement();
			accountOverviewPage.goToAccountOverviewPageBtn.click();

			// Assert quote status
			Assertions.verify(accountOverviewPage.quoteStatus.formatDynamicPath("1").getData().contains("Expired"),
					true, "Account Overview Page",
					"Quote Status is " + accountOverviewPage.quoteStatus.formatDynamicPath("1").getData(), false,
					false);

			// Adding Below code for CR IO-20778
			// Sign out as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as admin(swilcox) because reciprocal ,if reciprocal carrier fixed,then
			loginPage.refreshPage();
			loginPage.enterLoginDetails("swilcox", setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to swilcox successfully");

			// creating New account
			testData = data.get(dataValue3);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// entering Location Details
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

			// Entering Prior Losses
			Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossPage.selectPriorLossesInformation(testData);

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

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

			// Getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully ", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy number is : " + policyNumber);

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on Expacc
			homePage.scrollToBottomPage();
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();

			// Enter expacc details
			Assertions.verify(expaccInfoPage.submit.checkIfElementIsDisplayed(), true, "Expacc Info Page",
					"Expacc details page loaded successfully", false, false);
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("Expacc Info Page", "Expacc Details entered successfully");

			// Click on Home Button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);

			// Renew policy and release to producer
			Assertions.addInfo("Policy Summary Page", "Renewing the Policy");
			policySummaryPage.renewPolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew policy link");

			// Click on continue
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// click on Yes
			if (policyRenewalPage.yesButton.checkIfElementIsPresent()
					&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.yesButton.scrollToElement();
				policyRenewalPage.yesButton.click();
			}

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Renewal created and released to producer");

			// Getting renewal quote number
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Renewal quote number is " + renewalQuoteNumber);

			// Sign out as admin
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			Assertions.passTest("Login Page", "Logged in to producer successfully");

			// Search for renewal quote
			homePage.searchQuoteByProducer(renewalQuoteNumber);
			Assertions.passTest("Home Page", "Renewal quote is searched successfully");

			// Click on edit deductibles link
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit deductibles and limits button successfully");

			// Validating warning message,"The previous quote had one or more deductibles
			// that were below the minimum allowed. They will re-set to the appropriate
			// minimum."
			Assertions.addInfo("Create Quote Page", "Validating warning message");
			Assertions.verify(
					createQuotePage.alertError.getData().contains(
							"The previous quote had one or more deductibles that were below the minimum allowed"),
					true, "Create Quote Page", "Validating alert message, The alert message is "
							+ createQuotePage.alertError.getData() + " is displayed ",
					false, false);

			// Click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on getquote button successfully");

			// Create requote and validate warning message "The deductible Named Storm has
			// been adjusted for the Location 1, Building 1 to 5%"
			Assertions.addInfo("Create Quote Page ", "Validating warning message");
			Assertions.verify(createQuotePage.wdrWarning
					.formatDynamicPath(
							"The deductible Named Storm has been adjusted for the Location 1, Building 1 to 5%")
					.getData()
					.contains("The deductible Named Storm has been adjusted for the Location 1, Building 1 to 5%"),
					true, "Create Quote Page",
					"Validating warning message, The warning message is " + createQuotePage.wdrWarning
							.formatDynamicPath(
									"The deductible Named Storm has been adjusted for the Location 1, Building 1 to 5%")
							.getData() + " is displayed",
					false, false);

			// Click on continue
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Getting renewal quote number
			renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().replace("(", "").replace(")", "");
			Assertions.passTest("Account Overview Page", "Renewal quote number is " + renewalQuoteNumber);

			// Click on view/print full quote
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View print full quote link");

			// Verifying location 1,building 1 BPP NS and Business Income NS Both are same
			buildingValuesNamedStormLen = viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(15).getData()
					.length();
			buildingValueNamedStorm = viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(15).getData()
					.substring(0, buildingValuesNamedStormLen - 10);
			bIValueNamedStormLen = viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(20).getData().length();
			bIValueNamedStorm = viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(20).getData().substring(0,
					bIValueNamedStormLen - 9);
			Assertions.addInfo("View Or Print Full Quote Page",
					"Verifying location 1,building 1 BPP NS and Business Income NS Both are same");
			Assertions.verify(buildingValueNamedStorm, bIValueNamedStorm, "View Or Print Full Quote Page",
					"Location 1 Building 1 Namesd Storm Deductible and Business Income Named Storm Deductible both are same is verified",
					false, false);
			Assertions.passTest("View Or Print Full Quote Page",
					"Location 1,Building 1 BPP Named Storm Deductible is " + buildingValueNamedStorm);
			Assertions.passTest("View Or Print Full Quote Page",
					"Location 1,Building 1 Business Income Named Storm Deductible is " + bIValueNamedStorm);

			// Verifying location 2,building 1 BPP NS and Business Income NS Both are same
			buildingValuesNamedStormLen = viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(26).getData()
					.length();
			buildingValueNamedStorm = viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(26).getData()
					.substring(0, buildingValuesNamedStormLen - 9);
			bIValueNamedStormLen = viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(31).getData().length();
			bIValueNamedStorm = viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(31).getData().substring(0,
					bIValueNamedStormLen - 9);
			Assertions.addInfo("View Or Print Full Quote Page",
					"Verifying location 2,building 1 BPP NS and Business Income NS Both are same");
			Assertions.verify(buildingValueNamedStorm, bIValueNamedStorm, "View Or Print Full Quote Page",
					"Location 2 Building 1 Namesd Storm Deductible and Business Income Named Storm Deductible both are same is verified",
					false, false);
			Assertions.passTest("View Or Print Full Quote Page",
					"Location 2,Building 1 BPP Named Storm Deductible is " + buildingValueNamedStorm);
			Assertions.passTest("View Or Print Full Quote Page",
					"Location 2,Building 1 Business Income Named Storm Deductible is " + bIValueNamedStorm);
			// CR IO-208778 Ended

			// signout as producer
			homePage.goToHomepage.click();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 95", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 95", "Executed Successfully");
			}
		}
	}
}