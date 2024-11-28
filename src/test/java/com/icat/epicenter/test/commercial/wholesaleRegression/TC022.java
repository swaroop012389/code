/** Program Description: Check if the application allows to delete an account and USM Check the Water Damage Deductible is available to update on NB and secondary transactions.[Water Damage deductible, Pavan Mule 29-09-2023] and Added IO-21381 and added IO-21283

 *  Author			   : John
 *  Date of Creation   : 11/25/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.BuildingUnderMinimumCostPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.ModifyForms;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC022 extends AbstractCommercialTest {

	public TC022() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID022.xls";
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
		ModifyForms modifyForms = new ModifyForms();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		BuildingUnderMinimumCostPage buildingUnderMinimumCostPage = new BuildingUnderMinimumCostPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing the variables
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		Map<String, String> testData = data.get(dataValue1);
		String quoteNumber;
		int quotelength;
		String policyNumber;
		String deleteMsg = "successfully deleted";
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
			locationPage = eligibilityPage.processSingleZip(testData);
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

			// enter prior loss details
			if (testData.get("PriorLoss1").equalsIgnoreCase("Yes")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Delete an account
			accountOverviewPage.deleteAccount.scrollToElement();
			accountOverviewPage.deleteAccount.click();
			accountOverviewPage.yesDeletePopup.scrollToElement();
			accountOverviewPage.yesDeletePopup.click();

			// Assert successfully deleted message
			Assertions.addInfo("Scenario 01", "Assert successfully deleted message of account");
			Assertions.verify(homePage.accountSuccessfullyDeletedMsg.getData().contains(deleteMsg), true, "HomePage",
					"Account Successfully deleted message is displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Adding below code for water damage deductible scenario
			// creating New account
			testData = data.get(dataValue2);
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
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building Page", "Building details entered successfully");

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// Entering Prior Losses
			Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossPage.selectPriorLossesInformation(testData);
			Assertions.passTest("Prior Loss Page", "Prior loss details entered successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Click on modify forms
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on modify forms button successfully");

			// Adding IO-21381
			// Verify presence of 'ICAT SCOL 425 - Specify Water Damage Deductible form'
			Assertions.verify(modifyForms.override.checkIfElementIsDisplayed(), true, "Modify Form Page",
					"Modify form page loaded successfully", false, false);
			Assertions.addInfo("Scenario 02",
					"Verify presence of 'ICAT SCOL 425 - Specify Water Damage Deductible form");
			Assertions.verify(
					modifyForms.specifyWaterDamageDeductibleText.getData().contains("Specify Water Damage Deductible"),
					true, "Modify Form Page", "Specify Water Damage Deductible form is "
							+ modifyForms.specifyWaterDamageDeductibleText.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			// IO-21381 Ended

			// Add the form 'ICAT SCOL 425 - Specify Water Damage Deductible form'
			modifyForms.specifyWaterDamageDeductibleCheckBox.scrollToElement();
			modifyForms.specifyWaterDamageDeductibleCheckBox.select();
			waitTime(5);
			Assertions.verify(modifyForms.byPolicyText.checkIfElementIsDisplayed(), true, "Modify Forms Page",
					"ICAT SCOL 425 - Specify Water Damage Deductible form selected successfully", false, false);

			// Verifying the presence of water damage deductible values in dropdown
			Assertions.addInfo("Scenario 03", "Verifying the presence of water damage deductible options in dropdown");
			for (int i = 1; i < 6; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);
				modifyForms.specifyWaterDamageDeductibleArrow.scrollToElement();
				modifyForms.specifyWaterDamageDeductibleArrow.click();
				waitTime(3);// if wait time removed means element intractable exception will come
				String specifyWaterDamageDeductibleOptioni = modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).getData();
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillPresenceOfElement(60);
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible"))
						.waitTillVisibilityOfElement(60);
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).scrollToElement();
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).click();
				Assertions.passTest("ModifyForms Page", "The specify water damage deductible option available "
						+ specifyWaterDamageDeductibleOptioni + " present is verified");
				waitTime(3);// if wait time removed means element intractable exception will come
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// select water damage deductible $10,000 and click on override button
			testData = data.get(dataValue2);
			modifyForms.specifyWaterDamageDeductibleArrow.scrollToElement();
			modifyForms.specifyWaterDamageDeductibleArrow.click();
			waitTime(3);// if wait time removed means element intractable exception will come
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillPresenceOfElement(60);
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillVisibilityOfElement(60);
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).scrollToElement();
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).click();
			waitTime(3);// if wait time removed means element intractable exception will come
			Assertions.passTest("ModifyForms Page",
					"The water damage deductible value "
							+ modifyForms.specifyWaterDamageDeductibleData
									.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).getData()
							+ " selected successfully");
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("ModifyForms Page", "Clicked on override button successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account Overview Page", "Quote Number1 :  " + quoteNumber);

			// Adding Below code for IO-21283
			// Click on view/print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View print full quote page", "View print full quote page loaded successfully", false, false);

			// Verifying the presence of water damage deductible in view or print full quote
			// page
			Assertions.addInfo("Scenario 04",
					"Verifying the presence of water damage deductible in the view print full quote page");
			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues
							.formatDynamicPath(3).getData().contains("Water Damage Deductible"),
					true, "View print full quote page",
					"The water deductible shown in the deductible section, "
							+ viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(3).getData()
							+ " is displayed",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View print full quote page", "Clicked on back button successfully");
			// IO-21283 ended

			// Click on edit deductible and limits link
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit deductible and limits button successfully");

			// Increase the All Other Cause of Loss to $10,000
			testData = data.get(dataValue3);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "All other cause of loss deductible "
					+ createQuotePage.aoclDeductibleData.getData() + " selected successfully");

			// Click on modify forms
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on modify forms button successfully");

			// Verifying the 'ICAT SCOL 425 - Specify Water Damage Deductible form' is not
			// selected
			Assertions.addInfo("Scenario 05",
					"Verifying the 'ICAT SCOL 425 - Specify Water Damage Deductible form' is not selected ");
			Assertions.verify(modifyForms.byPolicyText.checkIfElementIsPresent(), false, "Modify Forms Page",
					"ICAT SCOL 425 - Specify Water Damage Deductible form not selected successfully", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on override button
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("ModifyForms Page", "Clicked on override button successfully");

			// getting the another quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account Overview Page", "Quote Number2 :  " + quoteNumber);

			// entering details in request bind page
			testData = data.get(dataValue2);
			Assertions.passTest("Account Overview Page", "Click on Request Bind");
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Click endorse PB link
			testData = data.get(dataValue4);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on endorse pb link successfully");

			// Enter endorsement effective date and click on change coverage option link
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on change coverage option link successfully");

			// Updating All Other Cause of Loss to $15,000
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "All other cause of loss deductible "
					+ createQuotePage.aoclDeductibleData.getData() + " selected successfully");

			// Click on modify forms
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on modify forms button successfully");

			// Verify presence of 'ICAT SCOL 425 - Specify Water Damage Deductible form'
			Assertions.verify(modifyForms.override.checkIfElementIsDisplayed(), true, "Modify Form Page",
					"Modify form page loaded successfully", false, false);
			Assertions.addInfo("Scenario 06",
					"Verify presence of 'ICAT SCOL 425 - Specify Water Damage Deductible form");
			Assertions.verify(modifyForms.specifyWaterDamageDeductibleText.checkIfElementIsDisplayed(), true,
					"Modify Form Page", "Specify Water Damage Deductible form is "
							+ modifyForms.specifyWaterDamageDeductibleText.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Add the form 'ICAT SCOL 425 - Specify Water Damage Deductible form'
			modifyForms.specifyWaterDamageDeductibleCheckBox.scrollToElement();
			modifyForms.specifyWaterDamageDeductibleCheckBox.select();
			waitTime(5);
			Assertions.verify(modifyForms.byPolicyText.checkIfElementIsDisplayed(), true, "Modify Forms Page",
					"ICAT SCOL 425 - Specify Water Damage Deductible form selected successfully", false, false);

			// Verifying the presence of water damage deductible values in dropdown
			Assertions.addInfo("Scenario 07", "Verifying the presence of water damage deductible options in dropdown");
			for (int i = 3; i < 6; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);
				modifyForms.specifyWaterDamageDeductibleArrow.scrollToElement();
				modifyForms.specifyWaterDamageDeductibleArrow.click();
				waitTime(3);// if wait time removed means element intractable exception will come
				String specifyWaterDamageDeductibleOptioni = modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).getData();
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillPresenceOfElement(60);
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible"))
						.waitTillVisibilityOfElement(60);
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).scrollToElement();
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).click();
				Assertions.passTest("ModifyForms Page", "The specify water damage deductible option available "
						+ specifyWaterDamageDeductibleOptioni + " present is verified");
				waitTime(3);// if wait time removed means element intractable exception will come
			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// select water damage deductible $25,000 and click on override button
			testData = data.get(dataValue4);
			modifyForms.specifyWaterDamageDeductibleArrow.scrollToElement();
			modifyForms.specifyWaterDamageDeductibleArrow.click();
			waitTime(3);// if wait time removed means element intractable exception will come
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillPresenceOfElement(60);
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillVisibilityOfElement(60);
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).scrollToElement();
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).click();
			waitTime(3);// if wait time removed means element intractable exception will come
			Assertions.passTest("ModifyForms Page",
					"The water damage deductible value "
							+ modifyForms.specifyWaterDamageDeductibleData
									.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).getData()
							+ " selected successfully");
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("ModifyForms Page", "Clicked on override button successfully");

			// Click on next and complete button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on close button
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// Click on rewrite policy link
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on rewrite policy link successfully");

			// Click on building link successfully
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			accountOverviewPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.passTest("Account Overview Page", "Clicked on building link successfully");

			// Click on edit pencil link
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit pencil link successfully");

			// updating number of stories
			buildingPage.numOfStories.scrollToElement();
			buildingPage.numOfStories.appendData(testData.get("L1B1-BldgStories"));
			buildingPage.numOfStories.tab();
			buildingPage.continueWithUpdateBtn.scrollToElement();
			buildingPage.continueWithUpdateBtn.click();
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			Assertions.passTest("Building Page", "Number of stories updated successfully");

			if (buildingUnderMinimumCostPage.override.checkIfElementIsPresent()
					&& buildingUnderMinimumCostPage.override.checkIfElementIsDisplayed()) {
				buildingUnderMinimumCostPage.override.scrollToElement();
				buildingUnderMinimumCostPage.override.click();
			}

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Selecting a peril as 'Wind'
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// Verifying absence of modify form button
			Assertions.addInfo("Scenario 08", "Verifying the absence of modify form button");
			Assertions.verify(createQuotePage.modifyForms.checkIfElementIsPresent(), false, "Create Quote Page",
					"Modify form button not displayed verified", false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click on previous button
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.passTest("Create Quote Page", "Clicked on previous button successfully");

			// Click on create another quote
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on create another quote button successfully");

			// Click on override button
			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.override.scrollToElement();
				buildingNoLongerQuoteablePage.override.click();
			}

			// Selecting a peril
			testData = data.get(dataValue2);
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));
			Assertions.passTest("Select Peril Page", "Peril selected successfully");

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterDeductiblesCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Click on modify forms
			createQuotePage.modifyForms.scrollToElement();
			createQuotePage.modifyForms.click();
			Assertions.passTest("Create Quote Page", "Clicked on modify forms button successfully");

			// Verify presence of 'ICAT SCOL 425 - Specify Water Damage Deductible form'
			Assertions.verify(modifyForms.override.checkIfElementIsDisplayed(), true, "Modify Form Page",
					"Modify form page loaded successfully", false, false);
			Assertions.addInfo("Scenario 09",
					"Verify presence of 'ICAT SCOL 425 - Specify Water Damage Deductible form");
			Assertions.verify(modifyForms.specifyWaterDamageDeductibleText.checkIfElementIsDisplayed(), true,
					"Modify Form Page", "Specify Water Damage Deductible form is "
							+ modifyForms.specifyWaterDamageDeductibleText.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Add the form 'ICAT SCOL 425 - Specify Water Damage Deductible form'
			modifyForms.specifyWaterDamageDeductibleCheckBox.scrollToElement();
			modifyForms.specifyWaterDamageDeductibleCheckBox.select();
			waitTime(5);
			Assertions.verify(modifyForms.byPolicyText.checkIfElementIsDisplayed(), true, "Modify Forms Page",
					"ICAT SCOL 425 - Specify Water Damage Deductible form selected successfully", false, false);

			// Verifying the presence of water damage deductible values in dropdown
			Assertions.addInfo("Scenario 10", "Verifying the presence of water damage deductible options in dropdown");
			for (int i = 1; i < 6; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);
				modifyForms.specifyWaterDamageDeductibleArrow.scrollToElement();
				modifyForms.specifyWaterDamageDeductibleArrow.click();
				waitTime(3);// if wait time removed means element intractable exception will come
				String specifyWaterDamageDeductibleOptioni = modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).getData();
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillPresenceOfElement(60);
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible"))
						.waitTillVisibilityOfElement(60);
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).scrollToElement();
				modifyForms.specifyWaterDamageDeductibleOption
						.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).click();
				Assertions.passTest("ModifyForms Page", "The specify water damage deductible option available "
						+ specifyWaterDamageDeductibleOptioni + " present is verified");
				waitTime(3);// if wait time removed means element intractable exception will come
			}
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// select water damage deductible $5,000 and click on override button
			testData = data.get(dataValue2);
			modifyForms.specifyWaterDamageDeductibleArrow.scrollToElement();
			modifyForms.specifyWaterDamageDeductibleArrow.click();
			waitTime(3);// if wait time removed means element intractable exception will come
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillPresenceOfElement(60);
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).waitTillVisibilityOfElement(60);
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).scrollToElement();
			modifyForms.specifyWaterDamageDeductibleOption
					.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).click();
			waitTime(3);// if wait time removed means element intractable exception will come
			Assertions.passTest("ModifyForms Page",
					"The water damage deductible value "
							+ modifyForms.specifyWaterDamageDeductibleData
									.formatDynamicPath(testData.get("SpecifyWaterDamageDeductible")).getData()
							+ " selected successfully");
			modifyForms.override.scrollToElement();
			modifyForms.override.click();
			Assertions.passTest("ModifyForms Page", "Clicked on override button successfully");

			// getting the rewrite quote number
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			quotelength = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quotelength - 1);
			Assertions.passTest("Account Overview Page", "Rewrite quote number:  " + quoteNumber);

			// click on rewrite button
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on rewrite button successfully");

			// Enter rewrite details
			testData = data.get(dataValue1);
			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enteringRewriteData(testData);
			Assertions.passTest("Request Bind Page", "Rewrite bind details entered successfully");

			// Get rewrite policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Rewrite Policy Number is " + policyNumber, false, false);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 22", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 22", "Executed Successfully");
			}
		}
	}
}