/** Program Description: IO-18745 - Overriden inspection fees are being reset with SLTF recalc at bind request and IO-22095
 *  Author			   : Yeshashwini T A
 *  Date of Creation   : 13/11/2019
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
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC054 extends AbstractCommercialTest {

	public TC054() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID054.xls";
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
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		DwellingPage dwellingPage = new DwellingPage();
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyrenewalPage = new PolicyRenewalPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		Map<String, String> testData = data.get(dataValue1);
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
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Entered location details successfully");

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);

			buildingPage.locationLink.formatDynamicPath(testData.get("LocCount")).scrollToElement();
			buildingPage.locationLink.formatDynamicPath(testData.get("LocCount")).click();
			buildingPage.addBuilding1.scrollToElement();
			buildingPage.addBuilding1.click();

			buildingPage.addBuildingDetails(testData, 1, 1);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			buildingPage.addRoofDetails(testData, 1, 1);
			buildingPage.enterAdditionalBuildingInformation(testData, 1, 1);
			buildingPage.enterBuildingValues(testData, 1, 1);
			buildingPage.reviewBuilding();

			// Added IO-22095
			// Asserting and verifying ACV(Actual cash value) message on building page when
			// year built more than
			// 16 years in the past and Roof cladding = Asphalt Shingles and year built =
			// year roof last replaced
			// The ACV message is'Roof Excluded From Replacement Cost and Covered Only at
			// Actual Cash Value.
			Assertions.passTest("Building Page", "Updated year built is " + testData.get("L1B1-BldgYearBuilt"));
			Assertions.passTest("Building Page",
					"Updated year roof last replaced" + testData.get("YearRoofLastReplaced"));
			Assertions.passTest("Building Page", "Updated roof cladding is " + testData.get("L1B1-BldgRoofCladding"));
			Assertions.addInfo("Scenario 01",
					"Verifying and Asserting Actual Cash Value message,When year built = "
							+ testData.get("L1B1-BldgYearBuilt") + ", year roof last replaced = "
							+ testData.get("YearRoofLastReplaced") + " and roof cladding = "
							+ testData.get("L1B1-BldgRoofCladding"));
			Assertions.verify(
					buildingPage.globalError.getData()
							.equals("Roof Excluded From Replacement Cost and Covered Only at Actual Cash Value."),
					true, "Building Page",
					"The ACV(Actual Cash Value) message is " + buildingPage.globalError.getData() + " displayed", false,
					false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			// IO-22095 Ended

			// Click on override button and create quote button
			buildingPage.override.scrollToElement();
			buildingPage.override.click();
			existingAccountPage.OverrideExistingAccount();
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			Assertions.passTest("Building Page", "Building details entered successfully");

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// entering prior loss details
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
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view print full quote link successfully");

			// Verifying and asserting ACV(Actual cash value) message in view print full
			// quote page when year built more than 16 year past and roof cladding = Asphalt
			// shingles
			Assertions.addInfo("Scenario 02",
					"Verifying and asserting ACV(Actual cash value) message in view print full quote page When year built = "
							+ testData.get("L1B1-BldgYearBuilt") + ", year roof last replaced = "
							+ testData.get("YearRoofLastReplaced") + " and roof cladding = "
							+ testData.get("L1B1-BldgRoofCladding"));
			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues
							.formatDynamicPath(12).getData().contains("Actual Cash Value"),
					true, "View Print Full Quote Page",
					"Asserting Actual Cash Value Under Your Coverages, Limits and Deductibles as they apply section,under Coverage Type.The message is "
							+ viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(12).getData() + " displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.statesWording
							.formatDynamicPath("excluding roofs valued at Actual Cash Value").getData()
							.contains("excluding roofs valued at Actual Cash Value"),
					true, "View Print Full Quote Page",
					"Asserting Actual Cash Value Under Standard Coverage section, against Replacement Cost (Building and Personal Property).The message is "
							+ viewOrPrintFullQuotePage.statesWording
									.formatDynamicPath("excluding roofs valued at Actual Cash Value").getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.termsAndConditions
							.formatDynamicPath(1, 13).getData().contains("Actual Cash Value"),
					true, "View Print Full Quote Page",
					"Asserting Actual Cash Value Under Terms & Conditions section, Last point under Conditions, The message is "
							+ viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(1, 13).getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Print Full Quote Page", "Clicked on back button successfully");
			// IO-22095 ended

			// Adding CR IO-18745
			// Click on override Premium link
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Override Premium link");

			// overriding the inspection fee as 0
			Assertions.verify(overridePremiumAndFeesPage.overridePremiumButton.checkIfElementIsDisplayed(), true,
					"Override Premium And Fees Page", "Override Premium And Fees Page loaded successfully", false,
					false);
			Assertions.verify(overridePremiumAndFeesPage.originalInspectionFee.checkIfElementIsDisplayed(), true,
					"Override Premium And Fees Page",
					"The Original Inspection Fee : " + overridePremiumAndFeesPage.originalInspectionFee.getData(),
					false, false);

			if (testData.get("TransactionInspectionFee") != null) {
				if (!testData.get("TransactionInspectionFee").equalsIgnoreCase("")) {
					overridePremiumAndFeesPage.totalInspectionFee.scrollToElement();
					overridePremiumAndFeesPage.totalInspectionFee.setData(testData.get("TransactionInspectionFee"));
					Assertions.passTest("Override Premium And Fees Page",
							"The Overridden Inspection Fee : " + "$" + testData.get("TransactionInspectionFee"));
				}
			}
			if (testData.get("FeeOverrideJustification") != null
					&& !testData.get("FeeOverrideJustification").equals("")) {
				overridePremiumAndFeesPage.feeOverrideJustification.scrollToElement();
				overridePremiumAndFeesPage.feeOverrideJustification.setData(testData.get("FeeOverrideJustification"));
			}

			// click on override premium button
			overridePremiumAndFeesPage.overridePremiumButton.scrollToElement();
			overridePremiumAndFeesPage.overridePremiumButton.click();

			// Asserting Inspection fee from edit fees section
			Assertions.addInfo("Account Overview Page", "Asserting Inspection fee from edit fees section");
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.editFees.scrollToElement();
			accountOverviewPage.editFees.click();
			Assertions.verify(
					accountOverviewPage.inspectionFeeAmount.getData()
							.contains(testData.get("TransactionInspectionFee")),
					true, "Account Overview Page", "The Overridden Inspection Fee : "
							+ accountOverviewPage.inspectionFeeAmount.getData() + " displayed is verified",
					false, false);
			accountOverviewPage.closeSymbol.scrollToElement();
			accountOverviewPage.closeSymbol.click();
			accountOverviewPage.yesCancelChange.click();
			accountOverviewPage.refreshPage();
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Enter the effective date as back date
			testData = data.get(dataValue2);
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
			} else {
				requestBindPage.cancel.scrollToElement();
				requestBindPage.cancel.click();
			}

			requestBindPage.waitTime(2); // If waittime is removed,Element Not Interactable exception is
			// thrown.Waittillpresence and Waittillvisibility is not working here

			// Asserting Inspection Fee from edit fee section
			Assertions.addInfo("Account Overview Page", "Asserting Inspection Fee from edit fee section");
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.waitTime(3);
			accountOverviewPage.editFees.scrollToElement();
			accountOverviewPage.editFees.click();
			accountOverviewPage.inspectionFeeAmount.waitTillVisibilityOfElement(60);
			testData = data.get(dataValue1);
			Assertions.verify(
					accountOverviewPage.inspectionFeeAmount.getData().substring(0, 2)
							.equals("$" + testData.get("TransactionInspectionFee")),
					true, "Account Overview Page", "The Overridden Inspection Fee : "
							+ accountOverviewPage.inspectionFeeAmount.getData() + " displayed is verified",
					false, false);
			accountOverviewPage.closeSymbol.scrollToElement();
			accountOverviewPage.closeSymbol.click();
			accountOverviewPage.yesCancelChange.click();
			accountOverviewPage.refreshPage();

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Enter bind Details
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

			// carrier selection
			if (requestBindPage.carrierWarningMsg.checkIfElementIsPresent()
					&& requestBindPage.carrierWarningMsg.checkIfElementIsDisplayed()) {
				requestBindPage.approveRequestCommercialData(testData);
			}

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// IO-22095
			// Click on endorse PB link
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on endorse pb link successfully");

			// Enter endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered endorsement effective date successfully");

			// Clicked on edit location/building information link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on edit location/building link successfully");

			// Clicked on building link
			// Update year built = 2005
			testData = data.get(dataValue2);
			Assertions.verify(buildingPage.continueButton.checkIfElementIsDisplayed(), true, "Building Page",
					"Building page loaded successfully", false, false);
			buildingPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(1, 1).click();
			buildingPage.yearBuilt.scrollToElement();
			buildingPage.yearBuilt.clearData();
			buildingPage.yearBuilt.appendData(testData.get("L1B1-BldgYearBuilt"));
			buildingPage.yearBuilt.tab();
			Assertions.passTest("Building Page", "Updated year built is " + testData.get("L1B1-BldgYearBuilt"));

			// Click on roof details link and Update roof cladding = Built-Up and year roof
			// last replaced
			// = 2005
			buildingPage.scrollToBottomPage();
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			buildingPage.waitTime(2);
			buildingPage.roofCladdingArrow.scrollToElement();
			buildingPage.roofCladdingArrow.click();
			buildingPage.roofCladdingOption.formatDynamicPath(testData.get("L1B1-BldgRoofCladding")).scrollToElement();
			buildingPage.roofCladdingOption.formatDynamicPath(testData.get("L1B1-BldgRoofCladding")).click();
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.clearData();
			buildingPage.yearRoofLastReplaced.appendData(testData.get("YearRoofLastReplaced"));
			Assertions.passTest("Building Page",
					"Updated year roof last replaced" + testData.get("YearRoofLastReplaced"));
			Assertions.passTest("Building Page", "Updated roof cladding is " + testData.get("L1B1-BldgRoofCladding"));
			buildingPage.reviewBuilding();
			Assertions.passTest("Building Page",
					"Year built,roof cladding and year roof last replaced updated successfully");

			// Asserting and verifying ACV(Actual cash value) message on dwelling page when
			// year built more than
			// 16 years in the past and Roof cladding = Built-Up and year built =
			// year roof last replaced
			// The ACV message is'Roof Excluded From Replacement Cost and Covered Only at
			// Actual Cash Value. for endorsement
			Assertions.addInfo("Scenario 03",
					"Verifying and Asserting Actual Cash Value message for endorsement,When year built = "
							+ testData.get("L1B1-BldgYearBuilt") + ", year roof last replaced = "
							+ testData.get("YearRoofLastReplaced") + " and roof cladding = "
							+ testData.get("L1B1-BldgRoofCladding"));
		if(buildingPage.reviewBuilding.checkIfElementIsPresent()&&buildingPage.reviewBuilding.checkIfElementIsDisplayed()) {
			System.out.println("Entered");
			buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
			buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
			buildingPage.reviewBuilding.waitTillElementisEnabled(60);
			buildingPage.reviewBuilding.waitTillButtonIsClickable(60);
			buildingPage.reviewBuilding.scrollToElement();
			buildingPage.reviewBuilding.click();
		}
			Assertions.verify(
					dwellingPage.ineligibleWarning.getData()
							.equals("Roof Excluded From Replacement Cost and Covered Only at Actual Cash Value."),
					true, "Dwelling Page",
					"The ACV(Actual Cash Value) message is " + dwellingPage.ineligibleWarning.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on create continue button
			dwellingPage.continueButton.scrollToElement();
			dwellingPage.continueButton.click();
			Assertions.passTest("Dwelling Page", "Clicked on continue button");

			// Clicked on Continue Endorsement Button
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue Endorsement Button");

			// Click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on next button");

			// Click on view endt quote button
			endorsePolicyPage.viewEndtQuoteButton.scrollToElement();
			endorsePolicyPage.viewEndtQuoteButton.click();

			// Verifying and asserting ACV(Actual cash value) message in view endorse quote
			// document page
			// quote page when year built and year roof last replaced more than 16 year
			// past, but both are same and roof cladding = Built-Up
			Assertions.addInfo("Scenario 04",
					"Verifying and asserting ACV(Actual cash value) message in view endorse quote document page When year built = "
							+ testData.get("L1B1-BldgYearBuilt") + ", year roof last replaced = "
							+ testData.get("YearRoofLastReplaced") + " and roof cladding = "
							+ testData.get("L1B1-BldgRoofCladding"));
			Assertions.verify(quoteDetailsPage.coverageType.getData().contains("Actual Cash Value"), true,
					"Quote Details Page",
					"Asserting Actual Cash Value Under Your Coverages, Limits and Deductibles as they apply section,under Coverage Type.The message is "
							+ quoteDetailsPage.coverageType.getData() + " displayed",
					false, false);
			Assertions.verify(
					quoteDetailsPage.deductibleData.formatDynamicPath("excluding roofs valued at Actual Cash Value")
							.getData().contains("excluding roofs valued at Actual Cash Value"),
					true, "Quote Details Page",
					"Asserting Actual Cash Value Under Standard Coverage section, against Replacement Cost (Building and Personal Property).The message is "
							+ quoteDetailsPage.deductibleData
									.formatDynamicPath("excluding roofs valued at Actual Cash Value").getData()
							+ " displayed",
					false, false);

			Assertions.verify(
					quoteDetailsPage.termsConditionsWordings.formatDynamicPath("Actual Cash Value").getData()
							.equals("Roof Excluded from Replacement Cost and covered only at Actual Cash Value."),
					true, "Quote Details Page",
					"Asserting Actual Cash Value Under Terms & Conditions section, Last point under Conditions, The message is "
							+ quoteDetailsPage.termsConditionsWordings.formatDynamicPath("Actual Cash Value").getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			quoteDetailsPage.closeBtn.scrollToElement();
			quoteDetailsPage.closeBtn.click();

			// Click on complete and close button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete and close button");

			// Click on rewrite link
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy summary page loaded successfully", false, false);
			policySummarypage.rewritePolicy.scrollToElement();
			policySummarypage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on rewrite link successfully");

			// Click on edit location
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit location link and building link");

			// Click on building link
			buildingPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.passTest("Building Page", "Clicked on edit building");

			// Updating Year built = 2007
			testData = data.get(dataValue3);
			buildingPage.yearBuilt.scrollToElement();
			buildingPage.yearBuilt.clearData();
			buildingPage.continueWithUpdateBtn.scrollToElement();
			buildingPage.continueWithUpdateBtn.click();
			buildingPage.yearBuilt.appendData(testData.get("L1B1-BldgYearBuilt"));
			buildingPage.yearBuilt.tab();
			Assertions.passTest("Building Page", "Updated year built is " + testData.get("L1B1-BldgYearBuilt"));

			// Click on roof details link and Update roof cladding = Single Ply Membrane
			// and year roof last replaced = 2007
			buildingPage.scrollToBottomPage();
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			buildingPage.waitTime(2);
			buildingPage.roofCladdingArrow.scrollToElement();
			buildingPage.roofCladdingArrow.click();
			buildingPage.roofCladdingOption.formatDynamicPath(testData.get("L1B1-BldgRoofCladding")).scrollToElement();
			buildingPage.roofCladdingOption.formatDynamicPath(testData.get("L1B1-BldgRoofCladding")).click();
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.clearData();
			buildingPage.yearRoofLastReplaced.appendData(testData.get("YearRoofLastReplaced"));
			Assertions.passTest("Building Page",
					"Updated year roof last replaced" + testData.get("YearRoofLastReplaced"));
			Assertions.passTest("Building Page", "Updated roof cladding is " + testData.get("L1B1-BldgRoofCladding"));
			buildingPage.reviewBuilding();
			Assertions.passTest("Building Page",
					"Year built,roof cladding and year roof last replaced updated successfully");

			// Asserting and verifying ACV(Actual cash value) message on building page when
			// year built more than
			// 16 years in the past and Roof cladding = Single Ply Membrane and year built =
			// year roof last replaced
			// The ACV message is'Roof Excluded From Replacement Cost and Covered Only at
			// Actual Cash Value. for rewrite
			Assertions.addInfo("Scenario 05",
					"Verifying and Asserting Actual Cash Value message,When year built = "
							+ testData.get("L1B1-BldgYearBuilt") + ", year roof last replaced = "
							+ testData.get("YearRoofLastReplaced") + " and roof cladding = "
							+ testData.get("L1B1-BldgRoofCladding"));

			if(buildingPage.reviewBuilding.checkIfElementIsPresent()&&buildingPage.reviewBuilding.checkIfElementIsDisplayed()) {
				System.out.println("Entered");
				buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
				buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
				buildingPage.reviewBuilding.waitTillElementisEnabled(60);
				buildingPage.reviewBuilding.waitTillButtonIsClickable(60);
				buildingPage.reviewBuilding.scrollToElement();
				buildingPage.reviewBuilding.click();
			}
			Assertions.verify(
					buildingPage.globalError.getData()
							.equals("Roof Excluded From Replacement Cost and Covered Only at Actual Cash Value."),
					true, "Building Page",
					"The ACV(Actual Cash Value) message is " + buildingPage.globalError.getData() + " displayed", false,
					false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on override button and create quote button
			buildingPage.override.scrollToElement();
			buildingPage.override.click();
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			Assertions.passTest("Building Page", "Building details entered successfully");

			// selecting peril
			testData = data.get(dataValue1);
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.rewriteBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Rewrite Quote Number :  " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view print full quote link successfully");

			// Verifying and asserting ACV(Actual cash value) message in view print full
			// quote page when year built more than 16 year past and roof cladding = Single
			// Ply Membrane
			testData = data.get(dataValue3);
			Assertions.addInfo("Scenario 06",
					"Verifying and asserting ACV(Actual cash value) message in view print full quote page When year built = "
							+ testData.get("L1B1-BldgYearBuilt") + ", year roof last replaced = "
							+ testData.get("YearRoofLastReplaced") + " and roof cladding = "
							+ testData.get("L1B1-BldgRoofCladding"));
			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues
							.formatDynamicPath(12).getData().contains("Actual Cash Value"),
					true, "View Print Full Quote Page",
					"Asserting Actual Cash Value Under Your Coverages, Limits and Deductibles as they apply section,under Coverage Type.The message is "
							+ viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(12).getData() + " displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.statesWording
							.formatDynamicPath("excluding roofs valued at Actual Cash Value").getData()
							.contains("excluding roofs valued at Actual Cash Value"),
					true, "View Print Full Quote Page",
					"Asserting Actual Cash Value Under Standard Coverage section, against Replacement Cost (Building and Personal Property).The message is "
							+ viewOrPrintFullQuotePage.statesWording
									.formatDynamicPath("excluding roofs valued at Actual Cash Value").getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.termsAndConditions
							.formatDynamicPath(1, 13).getData().contains("Actual Cash Value"),
					true, "View Print Full Quote Page",
					"Asserting Actual Cash Value Under Terms & Conditions section, Last point under Conditions, The message is "
							+ viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(1, 13).getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("View Print Full Quote Page", "Clicked on back button successfully");

			// Click on rewrite button
			accountOverviewPage.rewriteBind.scrollToElement();
			accountOverviewPage.rewriteBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on rewrite button");

			// Enter Rewrite details
			testData = data.get(dataValue1);
			Assertions.verify(requestBindPage.rewrite.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enteringRewriteData(testData);
			Assertions.passTest("Request Bind Page", "Rewrite details entered successfully");

			// Get policy number from policy summary page
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.verify(policySummarypage.policyNumber.getData().contains(policyNumber), true,
					"Policy summary page", "Rewrit Policy number is " + policyNumber, false, false);

			// Click on home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Add expac details
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search policy number
			homePage.searchPolicy(policyNumber);

			// clicking on renewal policy link
			policySummarypage.renewPolicy.waitTillPresenceOfElement(60);
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();
			Assertions.passTest("Policy summary page", "Clicked on Renewal link successfully");

			// Click on continue button
			policyrenewalPage.continueRenewal.scrollToElement();
			policyrenewalPage.continueRenewal.click();
			Assertions.passTest("Policy renewal page", "Clicked on continue button successfully");
			if (policyrenewalPage.yesButton.checkIfElementIsPresent()
					&& policyrenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyrenewalPage.yesButton.scrollToElement();
				policyrenewalPage.yesButton.click();
			}

			// Getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number :  " + quoteNumber);

			// Click on edit location
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);
			accountOverviewPage.editLocation.scrollToElement();
			accountOverviewPage.editLocation.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit location link and building link");

			// Click on building link
			buildingPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			buildingPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.passTest("Building Page", "Clicked on edit building");

			// Updating Year built = 2001
			testData = data.get(dataValue4);
			buildingPage.yearBuilt.scrollToElement();
			buildingPage.yearBuilt.clearData();
			buildingPage.continueWithUpdateBtn.scrollToElement();
			buildingPage.continueWithUpdateBtn.click();
			buildingPage.yearBuilt.appendData(testData.get("L1B1-BldgYearBuilt"));
			buildingPage.yearBuilt.tab();
			Assertions.passTest("Building Page", "Updated year built is " + testData.get("L1B1-BldgYearBuilt"));

			// Click on roof details link and Update roof cladding = Still or metal
			// and year roof last replaced = 2001
			buildingPage.scrollToBottomPage();
			buildingPage.roofDetailsLink.scrollToElement();
			buildingPage.roofDetailsLink.click();
			buildingPage.waitTime(2);
			buildingPage.roofCladdingArrow.scrollToElement();
			buildingPage.roofCladdingArrow.click();
			buildingPage.roofCladdingOption.formatDynamicPath(testData.get("L1B1-BldgRoofCladding")).scrollToElement();
			buildingPage.roofCladdingOption.formatDynamicPath(testData.get("L1B1-BldgRoofCladding")).click();
			buildingPage.yearRoofLastReplaced.scrollToElement();
			buildingPage.yearRoofLastReplaced.clearData();
			buildingPage.yearRoofLastReplaced.appendData(testData.get("YearRoofLastReplaced"));
			Assertions.passTest("Building Page",
					"Updated year roof last replaced" + testData.get("YearRoofLastReplaced"));
			Assertions.passTest("Building Page", "Updated roof cladding is " + testData.get("L1B1-BldgRoofCladding"));
			buildingPage.reviewBuilding();
			Assertions.passTest("Building Page",
					"Year built,roof cladding and year roof last replaced updated successfully");

			// Asserting and verifying ACV(Actual cash value) message on building page when
			// year built more than
			// 16 years in the past and Roof cladding = Still or metal and year built =
			// year roof last replaced
			// The ACV message is'Roof Excluded From Replacement Cost and Covered Only at
			// Actual Cash Value. for rewrite
			Assertions.addInfo("Scenario 07",
					"Verifying and Asserting Actual Cash Value message,When year built = "
							+ testData.get("L1B1-BldgYearBuilt") + ", year roof last replaced = "
							+ testData.get("YearRoofLastReplaced") + " and roof cladding = "
							+ testData.get("L1B1-BldgRoofCladding"));
			if(buildingPage.reviewBuilding.checkIfElementIsPresent()&&buildingPage.reviewBuilding.checkIfElementIsDisplayed()) {
				System.out.println("Entered");
				buildingPage.reviewBuilding.waitTillPresenceOfElement(60);
				buildingPage.reviewBuilding.waitTillVisibilityOfElement(60);
				buildingPage.reviewBuilding.waitTillElementisEnabled(60);
				buildingPage.reviewBuilding.waitTillButtonIsClickable(60);
				buildingPage.reviewBuilding.scrollToElement();
				buildingPage.reviewBuilding.click();
			}
			Assertions.verify(
					buildingPage.globalError.getData()
							.equals("Roof Excluded From Replacement Cost and Covered Only at Actual Cash Value."),
					true, "Building Page",
					"The ACV(Actual Cash Value) message is " + buildingPage.globalError.getData() + " displayed", false,
					false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			buildingPage.reSubmit.scrollToElement();
			buildingPage.reSubmit.click();

			if (buildingPage.override.checkIfElementIsPresent() && buildingPage.override.checkIfElementIsDisplayed()) {
				buildingPage.override.scrollToElement();
				buildingPage.override.click();
			}

			// Click on create quote button
			buildingPage.createQuote.scrollToElement();
			buildingPage.createQuote.click();
			Assertions.passTest("Building Page", "Building details entered successfully");

			// selecting peril
			testData = data.get(dataValue1);
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number1 :  " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view print full quote link successfully");

			// Verifying and asserting ACV(Actual cash value) message in view print full
			// quote page when year built more than 16 year past and roof cladding = still
			// or metal
			testData = data.get(dataValue4);
			Assertions.addInfo("Scenario 08",
					"Verifying and asserting ACV(Actual cash value) message in view print full quote page When year built = "
							+ testData.get("L1B1-BldgYearBuilt") + ", year roof last replaced = "
							+ testData.get("YearRoofLastReplaced") + " and roof cladding = "
							+ testData.get("L1B1-BldgRoofCladding"));
			Assertions.verify(
					viewOrPrintFullQuotePage.deductibleValues
							.formatDynamicPath(12).getData().contains("Actual Cash Value"),
					true, "View Print Full Quote Page",
					"Asserting Actual Cash Value Under Your Coverages, Limits and Deductibles as they apply section,under Coverage Type.The message is "
							+ viewOrPrintFullQuotePage.deductibleValues.formatDynamicPath(12).getData() + " displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.statesWording
							.formatDynamicPath("excluding roofs valued at Actual Cash Value").getData()
							.contains("excluding roofs valued at Actual Cash Value"),
					true, "View Print Full Quote Page",
					"Asserting Actual Cash Value Under Standard Coverage section, against Replacement Cost (Building and Personal Property).The message is "
							+ viewOrPrintFullQuotePage.statesWording
									.formatDynamicPath("excluding roofs valued at Actual Cash Value").getData()
							+ " displayed",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.termsAndConditions
							.formatDynamicPath(1, 13).getData().contains("Actual Cash Value"),
					true, "View Print Full Quote Page",
					"Asserting Actual Cash Value Under Terms & Conditions section, Last point under Conditions, The message is "
							+ viewOrPrintFullQuotePage.termsAndConditions.formatDynamicPath(1, 13).getData()
							+ " displayed",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");
			// IO-22095

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 54", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 54", "Executed Successfully");
			}
		}
	}
}
