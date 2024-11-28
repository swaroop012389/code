/** Program Description: Upload a sov file and enter the missing details and create quote and CR IO-19972
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
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC101 extends AbstractCommercialTest {

	public TC101() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID101.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		BuildingPage buildingPage = new BuildingPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();

		// Initializing the variables
		String quoteNumber;
		String insuredName;
		int quoteLen;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value2);
		boolean isTestPassed = false;

		try {
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.refreshPage();

			// Uploading SOV
			Assertions.addInfo("Home Page", "Uploading the SOV file");
			homePage.sovUpload.scrollToElement();
			homePage.sovUpload.click();
			Assertions.passTest("Home Page", "Clicked on Upload SOV");
			Assertions.verify(homePage.upload.checkIfElementIsDisplayed(), true, "Home Page",
					"Upload SOV popup displayed is verified", false, false);

			// Entering Producer Number
			homePage.sovProducerNumber.setData("8521.1");
			Assertions.passTest("Home Page", "Producer Number Entered successfully");

			// Uploading the file
			testData = data.get(data_Value2);
			homePage.SovFileUpload(testData.get("FileNameToUpload"), testData);
			Assertions.passTest("Home Page", "Uploaded the Document successfully");

			// Selecting building primary occupancy
			testData = data.get(data_Value1);
			buildingPage.addBuildingPrimaryOccupancy(testData, 1, 1);
			Assertions.passTest("Building Page", "Entered L1B1 Details successfully");

			// adding roof replaced year
			if (buildingPage.yearRoofLastReplaced.checkIfElementIsPresent()
					&& buildingPage.yearRoofLastReplaced.checkIfElementIsDisplayed()) {
				buildingPage.yearRoofLastReplaced.scrollToElement();
				buildingPage.yearRoofLastReplaced.setData("2018");
			}

			// Click on next button
			buildingPage.waitTime(3);// adding wait time to select occupancy
			buildingPage.nextButton.scrollToElement();
			buildingPage.nextButton.click();
			buildingPage.nextButton.click();
			Assertions.passTest("Building Page", "Clicked on Next button");

			// Click on override button
			if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Enter the effective date as current date
			requestBindPage.effectiveDate.waitTillVisibilityOfElement(60);
			if (!testData.get("PolicyEffDate").equals("")) {
				requestBindPage.effectiveDate.scrollToElement();
				requestBindPage.effectiveDate.clearData();
				requestBindPage.effectiveDate.setData(testData.get("PolicyEffDate"));
				requestBindPage.effectiveDate.tab();
			}
			Assertions.passTest("Request Bind Page",
					"The Policy Effective Date " + requestBindPage.effectiveDate.getData() + " entered successfully");

			requestBindPage.waitTime(3); // If waittime is removed,Element Not Interactable exception is
											// thrown.Waittillpresence and Waittillvisibility is not working here

			if (requestBindPage.wanttoContinue.checkIfElementIsPresent()
					&& requestBindPage.wanttoContinue.checkIfElementIsDisplayed()) {
				Assertions.verify(requestBindPage.wanttoContinue.checkIfElementIsDisplayed(), true, "Request Bind Page",
						"Pop Up displayed is verified", false, false);
				requestBindPage.wanttoContinue.waitTillVisibilityOfElement(60);
				requestBindPage.wanttoContinue.scrollToElement();
				requestBindPage.wanttoContinue.click();
				Assertions.passTest("Request Bind Page", "Clicked on Want to continue button");
			}

			requestBindPage.waitTime(2); // If waittime is removed,Element Not Interactable exception is
			// thrown.Waittillpresence and Waittillvisibility is not working here

			// Click on request bind button
			// accountOverviewPage.clickOnRequestBind(testData);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
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
			Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);

			// Added IO-21661
			Assertions.verify(policySummarypage.customerViewLink.checkIfElementIsPresent(), true, "Policy Summary Page",
					"Customer View Page link is displayed on the policy summary page is verified", false, false);
			policySummarypage.customerViewLink.scrollToElement();
			policySummarypage.customerViewLink.click();

			Assertions.verify(
					policySummarypage.customerViewPsge.formatDynamicPath("Commercial Regression TC0101_AOP")
							.checkIfElementIsPresent(),
					true, "Customer View Page", "Customer View Page loaded successfully", false, false);
			policySummarypage.customerViewPsge.formatDynamicPath("Commercial Regression TC0101_AOP").scrollToElement();
			policySummarypage.customerViewPsge.formatDynamicPath("Commercial Regression TC0101_AOP").click();

			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			policySummarypage.insuredName.checkIfElementIsPresent();
			insuredName = policySummarypage.insuredName.getData();

			Assertions.verify(insuredName.equals("Commercial Regression TC0101_AOP"), true, "Policy Summary Page",
					"User is redirected to correct policy from customer view page is verified", false, false);

			// Ticket IO-21661 is ended

			// Added IO-21600
			homePage.goToHomepage.click();
			homePage.refreshPage();

			// Uploading SOV
			Assertions.addInfo("Home Page", "Uploading the SOV file");
			homePage.sovUpload.scrollToElement();
			homePage.sovUpload.click();
			Assertions.passTest("Home Page", "Clicked on Upload SOV");
			Assertions.verify(homePage.upload.checkIfElementIsDisplayed(), true, "Home Page",
					"Upload SOV popup displayed is verified", false, false);

			// Entering Producer Number
			homePage.sovProducerNumber.setData("8521.1");
			Assertions.passTest("Home Page", "Producer Number Entered successfully");

			// Uploading the file
			testData = data.get(data_Value3);
			homePage.SovFileUpload(testData.get("FileNameToUpload"), testData);
			Assertions.passTest("Home Page", "Uploaded the Document successfully");

			Assertions.verify(homePage.buildingLink.checkIfElementIsPresent(), true, "SOV Upload Page",
					"Building link 1-2 displayed is verified", false, false);
			homePage.buildingLink.scrollToElement();
			homePage.buildingLink.click();

			Assertions.verify(homePage.addBuilding.checkIfElementIsPresent(), true, "SOV Upload Page",
					"Add Building option displayed is verified", false, false);

			Assertions.verify(homePage.copyBuilding.checkIfElementIsPresent(), true, "SOV Upload Page",
					"Copy building icon displayed is verified", false, false);
			homePage.copyBuilding.scrollToElement();
			homePage.copyBuilding.click();

			Assertions.verify(homePage.deleteBuilding.checkIfElementIsPresent(), true, "SOV Upload Page",
					"Delete building icon displayed is verified", false, false);
			homePage.deleteBuilding.scrollToElement();
			homePage.deleteBuilding.click();

			Assertions.verify(homePage.deleteYes.checkIfElementIsPresent(), true, "SOV Upload Page",
					"Delete 'Yes' option displayed is verified", false, false);
			homePage.deleteYes.scrollToElement();
			homePage.deleteYes.click();
			Assertions.passTest("SOV Upload Page", "Copied builiding deleted successfully");

			// Ticket IO-21600 is ended

			// sign out and close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 101", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 101", "Executed Successfully");
			}
		}
	}
}
