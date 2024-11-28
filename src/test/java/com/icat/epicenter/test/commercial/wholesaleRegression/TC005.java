/** Program Description: IO-22340 Using A Really large square footage causes us to get a negative coverage amount
 *  Author			   : pavan mule
 *  Date of Creation   : 10/01/2024
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
//import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
//import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC005 extends AbstractCommercialTest {

	public TC005() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID005.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;
		Map<String, String> testData = data.get(data_Value1);
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
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);

			// Enter Building Details
			locationPage.addBuildingsButton.scrollToElement();
			locationPage.addBuildingsButton.click();
			buildingPage.addBuildingDetails(testData, 1, 1);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, 1, 1);

			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			buildingPage.override.scrollToElement();
			buildingPage.override.click();
			Assertions.passTest("Building Page", "Enter building details");

			// Asserting and verifying No negative coverage amount when larger square
			// footage
			Assertions.addInfo("Scenario 01",
					"Asserting and verifying No negative coverage amount when larger square footage");
			Assertions.verify(
					buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value for this risk is").getData()
							.substring(82, 95).contains("-"),
					false, "Bulding Under minimum page",
					"No Negative coverage amount is " + buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value for this risk is").getData()
							.substring(82, 95) + " verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on bring upTocost
			buildingUnderMinimumCostPage.bringUpToCost.scrollToElement();
			buildingUnderMinimumCostPage.bringUpToCost.click();
			Assertions.passTest("Building under minimum cost page", "Clicked on bring up to cost");

			// Click on get a quote button
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			buildingPage.override.scrollToElement();
			buildingPage.override.click();
			Assertions.passTest("Building Page", "Clicked on create quote button");

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Peril page", "Peril details entered successfully");

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior loss page", "Prior loss details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.refreshPage();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequest();
			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// click on endorse link
			testData = data.get(data_Value1);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link successfully");
			Assertions.verify(endorsePolicyPage.cancelButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Policy Page loaded successfully", false, false);
			endorsePolicyPage.waitTime(1);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on edit location/ building information
			endorsePolicyPage.waitTime(1);
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();

			// updating square footage = 800,000 and number of stories = Light Metal Frame,
			// building value =
			// 200,000
			testData = data.get(data_Value2);
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();
			buildingPage.constructionTypeArrow.scrollToElement();
			buildingPage.constructionTypeArrow.click();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).scrollToElement();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).click();
			Assertions.passTest("Building Page",
					"Latest construction type " + buildingPage.constructionTypeData.getData());
			buildingPage.totalSquareFootage.scrollToElement();
			buildingPage.totalSquareFootage.clearData();
			buildingPage.totalSquareFootage.appendData(testData.get("L1B1-BldgSqFeet"));
			Assertions.passTest("Building Page", "Latest square footage " + buildingPage.totalSquareFootage.getData());
			buildingPage.enterBuildingValues(testData, 1, 1);
			Assertions.passTest("Building Page", "Latest Building Value " + buildingPage.buildingValue.getData());

			// CLick on continue endorsement
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();
			Assertions.passTest("Building Page", "Clicked on continue button");

			// Asserting and verifying No negative coverage amount when larger square
			// footage
			Assertions.addInfo("Scenario 02",
					"Asserting and verifying No negative coverage amount when larger square footage");
			Assertions.verify(
					buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value for this risk is").getData()
							.substring(82, 94).contains("-"),
					false, "Bulding Under minimum page",
					"No Negative coverage amount is " + buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value for this risk is").getData()
							.substring(82, 94) + " verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on bring upTocost
			buildingUnderMinimumCostPage.bringUpToCost.scrollToElement();
			buildingUnderMinimumCostPage.bringUpToCost.click();
			Assertions.passTest("Building under minimum cost page", "Clicked on bring up to cost");

			// Click on continue button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on continue endorsement button");

			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			endorsePolicyPage.oKContinueButton.scrollToElement();
			endorsePolicyPage.oKContinueButton.click();
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button");

			// Click on close button
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button");

			// Go to Home Page
			testData = data.get(data_Value1);
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Click on expacc link
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();

			// Enter Expacc details
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// Click on renew policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// Click on Continue
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}
			policyRenewalPage.yesButton.scrollToElement();
			policyRenewalPage.yesButton.click();

			// Getting the quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + quoteNumber);

			// Click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on release renewal to producer button");

			// click on edit building icon
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit building");

			// Updating square footage = 1,000,000 and building value = 304,920
			testData = data.get(data_Value3);
			buildingPage.totalSquareFootage.scrollToElement();
			buildingPage.totalSquareFootage.clearData();
			buildingPage.waitTime(1);
			buildingPage.continueWithUpdateBtn.scrollToElement();
			buildingPage.continueWithUpdateBtn.click();
			buildingPage.totalSquareFootage.appendData(testData.get("L1B1-BldgSqFeet"));
			Assertions.passTest("Building Page", "Latest square footage " + buildingPage.totalSquareFootage.getData());
			buildingPage.enterBuildingValues(testData, 1, 1);
			Assertions.passTest("Building Page", "Latest Building Value " + buildingPage.buildingValue.getData());

			// Click on review building
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Enter building details");

			// Asserting and verifying No negative coverage amount when larger square
			// footage
			Assertions.addInfo("Scenario 03",
					"Asserting and verifying No negative coverage amount when larger square footage");
			Assertions.verify(
					buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value for this risk is").getData()
							.substring(79, 88).contains("-"),
					false, "Bulding Under minimum page",
					"No Negative coverage amount is " + buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value for this risk is").getData()
							.substring(79, 88) + " verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on bring upTocost
			buildingUnderMinimumCostPage.bringUpToCost.scrollToElement();
			buildingUnderMinimumCostPage.bringUpToCost.click();
			Assertions.passTest("Building under minimum cost page", "Clicked on bring up to cost");

			// Click on get a quote button
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			Assertions.passTest("Building Page", "Clicked on create quote button");

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Peril page", "Peril details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number2 :  " + quoteNumber);

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.refreshPage();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {

			}
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Renewal Policy Number is " + policyNumber, false, false);

			// Click on rewrite link
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on rewrite link");

			// click on edit building icon
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit building");

			// Updating square footage = 978,076 and building value = 50,000 and
			// Construction type = Fire Resistive
			testData = data.get(data_Value4);
			buildingPage.totalSquareFootage.scrollToElement();
			buildingPage.totalSquareFootage.clearData();
			buildingPage.waitTime(1);
			buildingPage.continueWithUpdateBtn.scrollToElement();
			buildingPage.continueWithUpdateBtn.click();
			buildingPage.totalSquareFootage.appendData(testData.get("L1B1-BldgSqFeet"));
			Assertions.passTest("Building Page", "Latest square footage " + buildingPage.totalSquareFootage.getData());
			buildingPage.constructionTypeArrow.scrollToElement();
			buildingPage.constructionTypeArrow.click();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).scrollToElement();
			buildingPage.constructionTypeOption.formatDynamicPath(testData.get("L1B1-BldgConstType")).click();
			Assertions.passTest("Building Page",
					"Latest construction type " + buildingPage.constructionTypeData.getData());
			buildingPage.enterBuildingValues(testData, 1, 1);
			Assertions.passTest("Building Page", "Latest Building Value " + buildingPage.buildingValue.getData());

			// Click on review building
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
			Assertions.passTest("Building Page", "Enter building details");

			// Asserting and verifying No negative coverage amount when larger square
			// footage
			Assertions.addInfo("Scenario 04",
					"Asserting and verifying No negative coverage amount when larger square footage");
			Assertions.verify(
					buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value for this risk is").getData()
							.substring(81, 92).contains("-"),
					false, "Bulding Under minimum page",
					"No Negative coverage amount is " + buildingUnderMinimumCostPage.costcardMessage
							.formatDynamicPath("The minimum Building value for this risk is").getData()
							.substring(81, 92) + " verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on bring upTocost
			buildingUnderMinimumCostPage.bringUpToCost.scrollToElement();
			buildingUnderMinimumCostPage.bringUpToCost.click();
			Assertions.passTest("Building under minimum cost page", "Clicked on bring up to cost");

			// Click on get a quote button
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			Assertions.passTest("Building Page", "Clicked on create quote button");

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Peril page", "Peril details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Rewrite Quote Number :  " + quoteNumber);

			// Click on rewrite bind button
			accountOverviewPage.clickOnRewriteBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on rewrite bind button");

			// entering details in request bind page
			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enteringRewriteData(testData);
			Assertions.passTest("Request Bind Page", "Rewrite details entered successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Rewrite Policy Number is " + policyNumber, false, false);

			// Sign out and close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 05", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 05", "Executed Successfully");
			}
		}

	}

}