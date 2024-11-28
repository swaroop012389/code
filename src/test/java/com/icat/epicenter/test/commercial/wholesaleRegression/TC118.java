/** Program Description: CR IO-19978 Upload a sov file and enter the missing details and create policy and Added CR IO-20286
 *  Author			   : Pavan mule
 *  Date of Modified   : 06/29/2022
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

public class TC118 extends AbstractCommercialTest {

	public TC118() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID118.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		BuildingPage buildingPage = new BuildingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();

		// Initializing the variables
		String quoteNumber;
		int quoteLen;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);
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
			testData = data.get(data_Value1);
			homePage.SovFileUpload(testData.get("FileNameToUpload"), testData);
			Assertions.passTest("Home Page", "Uploaded the SOV Document successfully");

			// Selecting building primary occupancy
			buildingPage.addBuildingPrimaryOccupancy(testData, 1, 1);
			Assertions.passTest("Building Page", "Entered L1B1 Details successfully");

			// Click on next button
			buildingPage.waitTime(3);// adding wait time to select occupancy
			buildingPage.nextButton.scrollToElement();
			buildingPage.nextButton.click();
			Assertions.passTest("Building Page", "Clicked on Next button");

			/*
			 * if (buildingPage.primaryOccupancyCondition.checkIfElementIsDisplayed() &&
			 * buildingPage.primaryOccupancyCondition.checkIfElementIsEnabled()) {
			 * buildingPage.primaryOccupancyCondition.scrollToElement();
			 * buildingPage.primaryOccupancyCondition.select();
			 * buildingPage.nextButton.scrollToElement(); buildingPage.nextButton.click(); }
			 */

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

			// Click on get quote button
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on get a quote button successfully");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on request bind button
			testData = data.get(data_Value2);
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			testData = data.get(data_Value2);
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
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			testData = data.get(data_Value1);
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

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			} else {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is " + policyNumber);

			// Adding below code for IO-20678
			// Click on home button link
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Clicked on home button successfully");

			// click on find filter arrow
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();

			Assertions.passTest("Home Page", "Clicked on Find Filter Arrow");
			homePage.findFilterQuoteOption.scrollToElement();
			homePage.findFilterQuoteOption.click();

			// Enter creation date after and creation date before
			homePage.quotecreationDateAfterfield.setData(testData.get("CreatedAfterDate"));
			homePage.quotecreationDateBeforefield.setData(testData.get("CreatedBeforeDate"));
			Assertions.passTest("Home Page", "Entered the CreatedAfterDate and CreatedBeforeDate");

			homePage.businessTypeQuoteArrow.scrollToElement();
			homePage.businessTypeQuoteArrow.click();
			homePage.businessTypeQuoteOption.formatDynamicPath("Commercial Wholesale").scrollToElement();
			homePage.businessTypeQuoteOption.formatDynamicPath("Commercial Wholesale").click();

			homePage.findBtnQuote.scrollToElement();
			homePage.findBtnQuote.click();
			Assertions.passTest("Home Page", "Clicked on Find Button");

			// click on the expired quote
			homePage.resultTable.formatDynamicPath("Expired", "1").waitTillVisibilityOfElement(60);
			homePage.resultTable.formatDynamicPath("Expired", "1").scrollToElement();
			homePage.resultTable.formatDynamicPath("Expired", "1").click();

			// Assert the quote status
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 01", "Verifying the Quote Status");
			Assertions.verify(accountOverviewPage.quoteExpiredStatus.getData().contains("Expired"), true,
					"Account Overview Page",
					"The Quote Status " + accountOverviewPage.quoteExpiredStatus.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on quote specifics
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.passTest("Account Overview Page", "Clicked on Quote Specifics");

			// Verify the rating eff date is current
			Assertions.addInfo("Scenario 02", "Verifying the rating effective date for the expired quote");
			String ratingEffDateofExpiredQte = accountOverviewPage.ratingEffectiveDate.getData();
			Assertions.verify(accountOverviewPage.ratingEffectiveDate.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Rating Effective Date of the expired quote is "
							+ ratingEffDateofExpiredQte + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// click on create another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on Create another quote");

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}
			// Selecting peril
			if (selectPerilPage.continueButton.checkIfElementIsPresent()
					&& selectPerilPage.continueButton.checkIfElementIsDisplayed()) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril("AOP");
			}

			// Enter Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			testData = data.get(data_Value2);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			/*
			 * createQuotePage.earthquakeDeductibleArrow.scrollToElement();
			 * createQuotePage.earthquakeDeductibleArrow.click();
			 * createQuotePage.earthquakeDeductibleOption.formatDynamicPath(testData.get(
			 * "EQDeductibleValue")) .scrollToElement();
			 * createQuotePage.earthquakeDeductibleOption.formatDynamicPath(testData.get(
			 * "EQDeductibleValue")).click();
			 *
			 * // click on get a quote createQuotePage.getAQuote.scrollToElement();
			 * createQuotePage.getAQuote.click(); Assertions.passTest("Create Quote Page",
			 * "Clicked on get a quote button successfully");
			 *
			 * if (createQuotePage.continueButton.checkIfElementIsPresent() &&
			 * createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			 * createQuotePage.continueButton.scrollToElement();
			 * createQuotePage.continueButton.click(); }
			 */

			// Getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// click on quote specifics
			accountOverviewPage.quoteSpecifics.scrollToElement();
			accountOverviewPage.quoteSpecifics.click();
			Assertions.passTest("Account Overview Page", "Clicked on Quote Specifics");

			// Verify the rating eff date is current
			Assertions.addInfo("Scenario 03", "Verifying the rating effective date is current date or not");
			testData = data.get(data_Value1);
			Assertions.verify(!accountOverviewPage.ratingEffectiveDate.getData().contains(ratingEffDateofExpiredQte),
					true, "Account Overview Page", "The Rating Effective Date "
							+ accountOverviewPage.ratingEffectiveDate.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Added IO-21598
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			Assertions.addInfo("Scenario",
					"Uploading the accord file and Verifying that the user is able to create the quote successfully.");
			Assertions.addInfo("Home Page", "Uploading the SOV file");
			homePage.sovUpload.scrollToElement();
			homePage.sovUpload.click();
			Assertions.passTest("Home Page", "Clicked on Upload SOV");
			Assertions.verify(homePage.upload.checkIfElementIsDisplayed(), true, "Home Page",
					"Upload SOV popup displayed is verified", false, false);

			// Entering Producer Number
			homePage.sovProducerNumber.setData("8521.1");
			Assertions.passTest("Home Page", "Producer Number Entered successfully");

			// Uploading accord file
			testData = data.get(data_Value1);
			homePage.SovFileUpload(testData.get("AccordFileName"), testData);
			Assertions.passTest("Home Page", "Uploaded the SOV Document successfully");

			if (buildingPage.exteriorCladdingArrow.checkIfElementIsPresent()) {
				buildingPage.exteriorCladdingArrow.scrollToElement();
				buildingPage.exteriorCladdingArrow.click();
				buildingPage.exteriorCladdingOption.formatDynamicPath(testData.get("L" + 1 + "B" + 1 + "-BldgCladding"))
						.scrollToElement();
				buildingPage.exteriorCladdingOption.formatDynamicPath(testData.get("L" + 1 + "B" + 1 + "-BldgCladding"))
						.click();

			}
			if (buildingPage.softStoryCharacteristics_No.checkIfElementIsPresent()) {
				buildingPage.softStoryCharacteristics_No.scrollToElement();
				buildingPage.softStoryCharacteristics_No.click();
			}

			if (testData.get("L" + 1 + "B" + 1 + "-BuildingMorethan31%Occupied").equals("Yes")) {
				buildingPage.buildingOccupancy_yes.scrollToElement();
				buildingPage.waitTime(2);
				buildingPage.buildingOccupancy_yes.click();
			} else {
				buildingPage.buildingOccupancy_no.scrollToElement();
				buildingPage.buildingOccupancy_no.click();
			}

			buildingPage.addBuildingPrimaryOccupancy(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			Assertions.passTest("Building Page", "Entered L1B1 Details successfully");

			buildingPage.nextButton.scrollToElement();
			buildingPage.nextButton.click();
			Assertions.passTest("Building Page", "Clicked on Next button");

			if (buildingPage.primaryOccupancyCondition.checkIfElementIsDisplayed()
					&& buildingPage.primaryOccupancyCondition.checkIfElementIsEnabled()) {
				buildingPage.primaryOccupancyCondition.scrollToElement();
				buildingPage.primaryOccupancyCondition.select();
			}

			buildingPage.nextButton.scrollToElement();
			buildingPage.nextButton.click();

			buildingPage.buildingCoverageArrow.scrollToElement();
			buildingPage.buildingCoverageArrow.click();

			buildingPage.buildingCoverageOptions.formatDynamicPath("Carports").checkIfElementIsPresent();
			buildingPage.buildingCoverageOptions.formatDynamicPath("Carports").scrollToElement();
			buildingPage.buildingCoverageOptions.formatDynamicPath("Carports").click();

			buildingPage.updateCoveragesButton.scrollToElement();
			buildingPage.updateCoveragesButton.click();

			// Selecting a peril
			testData = data.get(data_Value3);
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
			Assertions.addInfo("Scenario",
					"The user is able to create the quote successfully when the accord file is uploaded.");

			// IO-21598 is ended.

			// sign out and close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 118", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 118", "Executed Successfully");
			}
		}
	}

}