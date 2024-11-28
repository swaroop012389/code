/** Program Description: Create a policy with Building Coverage of $1M and check if WDR sub limits of $50000 and $100000 are available. Update the building value to $75,000 and check if WDR sublimits of $50000 and $100000(Override) are available
 *  Author			   : John
 *  Date of Creation   : 11/13/2019
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC033 extends AbstractCommercialTest {

	public TC033() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID033.xls";
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
		RequestBindPage requestBindPage = new RequestBindPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int quotelength;
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
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

			// Entering Location Details
			Assertions.verify(locationPage.addBuildingsButton.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Enter prior loss details
			if (testData.get("PriorLoss1").equalsIgnoreCase("Yes")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			} else {
				priorLossPage.lossesInThreeYearsNo.click();
				priorLossPage.continueButton.click();
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Asserting Wind driven rain values when building value is 1M
			createQuotePage.windDrivenRainArrow.waitTillVisibilityOfElement(60);
			createQuotePage.windDrivenRainArrow.scrollToElement();
			createQuotePage.windDrivenRainArrow.click();
			Assertions.addInfo("Create Quote Page", "Asserting Wind driven rain values when building value is 1M");
			Assertions.verify(
					createQuotePage.windDrivenRainOption.formatDynamicPath(testData.get("WindDrivenRain")).getData(),
					testData.get("WindDrivenRain"), "Create Quote Page",
					"WDR value:" + createQuotePage.windDrivenRainOption
							.formatDynamicPath(testData.get("WindDrivenRain")).getData()
							+ " is displayed when Building value is 1M",
					false, false);
			Assertions.verify(
					createQuotePage.windDrivenRainOption.formatDynamicPath(testData.get("WindDrivenRain1")).getData(),
					testData.get("WindDrivenRain1"), "Create Quote Page",
					"WDR value:" + createQuotePage.windDrivenRainOption
							.formatDynamicPath(testData.get("WindDrivenRain1")).getData()
							+ " is displayed when Building value is 1M",
					false, false);

			testData = data.get(dataValue2);
			// Asserting Wind driven rain values when building value is less than 1M
			createQuotePage.buildingValue.formatDynamicPath(0, 0).waitTillVisibilityOfElement(60);
			createQuotePage.buildingValue.formatDynamicPath(0, 0).scrollToElement();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).clearData();
			createQuotePage.buildingValue.formatDynamicPath(0, 0).appendData(testData.get("L1B1-BldgValue"));
			createQuotePage.buildingValue.formatDynamicPath(0, 0).tab();
			Assertions.passTest("Create Quote Page", "Building value is updated to " + testData.get("L1B1-BldgValue"));
			createQuotePage.refreshPage();
			createQuotePage.windDrivenRainArrow.waitTillVisibilityOfElement(60);
			createQuotePage.windDrivenRainArrow.scrollToElement();
			createQuotePage.windDrivenRainArrow.click();
			Assertions.addInfo("Create Quote Page",
					"Asserting Wind driven rain values when building value is less than 1M");
			Assertions.verify(
					createQuotePage.windDrivenRainOption.formatDynamicPath(testData.get("WindDrivenRain")).getData(),
					testData.get("WindDrivenRain"), "Create Quote Page",
					"WDR value:" + createQuotePage.windDrivenRainOption
							.formatDynamicPath(testData.get("WindDrivenRain")).getData()
							+ " is displayed when Building value is $75,000",
					false, false);
			Assertions.verify(
					createQuotePage.windDrivenRainOption.formatDynamicPath(testData.get("WindDrivenRain1")).getData(),
					testData.get("WindDrivenRain1"), "Create Quote Page",
					"WDR value:" + createQuotePage.windDrivenRainOption
							.formatDynamicPath(testData.get("WindDrivenRain1")).getData()
							+ " is displayed when Building value is $75,000",
					false, false);

			// Create a quote
			testData = data.get(dataValue1);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			buildingUnderMinimumCostPage.clickOnOverride();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// getting the quote number
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// entering details in request bind page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage.enterBindDetails(testData);

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully and values entered", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
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
			Assertions.failTest("Commercial NBRegression Test Case 33", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 33", "Executed Successfully");
			}
		}
	}
}
