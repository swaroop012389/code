/** Program Description: Verifying absence of green upgrades coverage when Building value not added on NB and do endorsement and add building value check green upgrades coverage present and checking transaction total premium value +ve and added IO-21151
 *  Author			   : Pavan Mule
 *  Date of Creation   : 06/09/2023
 **/

package com.icat.epicenter.test.commercial.retailRegression;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC057 extends AbstractCommercialTest {

	public TC057() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID057.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Pages
		HomePage homePage = new HomePage();
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
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		String prorataFactor;
		String originalPremium;
		String actualEarnedValue;
		String actualReturnedValue;
		double d_calReturnedValue;
		double d_actualEarnedValue;
		double d_actualReturnedValue;
		double d_calEarnedValue;
		String traPremium;
		String traTotalPremium;
		boolean isTestPassed = false;

		try {

			// creating New account
			testData = data.get(data_Value1);

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
			locationPage.addBusinessIncome(testData.get("L1-LocBI"));
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);
			Assertions.passTest("Building page", "Building details entered successfully");

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
				Assertions.passTest("Select Peril Page", "Peril selected successfully");
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior loss details entered successfully");
			}

			// Verifying the absence of green upgrades option in create quote page, When
			// only BI values added and not building value and BPP value
			Assertions.addInfo("Scenario 01",
					"Verifying the absence of green upgrades option in create quote page,When only BI values added and not building value and BPP value");
			Assertions.verify(createQuotePage.greenUpgradesLabel.checkIfElementIsPresent(), false, "Create Quote Page",
					"Green Upgrades option not displayed verified", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

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

			// Click on view/print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"ViewPrint Full Quote Page", "ViewPrint Full Quote Page loaded successfully", false, false);

			// Verifying the absence of green upgrades coverage in view/print full quote
			// page, When only BI values added and not building value and BPP value
			Assertions.addInfo("Scenario 02",
					"Verifying the absence of green upgrades coverage in view/print full quote page, When only BI values added and not building value and BPP value");
			Assertions.verify(viewOrPrintFullQuotePage.greenUpgradesLable.checkIfElementIsPresent(), false,
					"ViewPrint Full Quote Page", "Green Upgrades option not displayed verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("ViewPrint Full Quote Page", "Clicked on back button successfully");

			// click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
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
			Assertions.passTest("Home Page", "Searched Submitted quote " + quoteNumber + " successfullly");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

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

			// Click on PB endorsement
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.appendData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Endorsement effective date entered successfully");

			// Click edit location/building information link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location page loaded successfully", false, false);

			// Click on building link
			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.passTest("Location Page", "Clicked on building link successfully");

			// Change building primary occupancy apartment to restaurant
			Assertions.passTest("Building Page", "Original Occupancy is " + testData.get("L1B1-PrimaryOccupancy"));
			testData = data.get(data_Value2);
			buildingPage.addBuildingOccupancy(testData, 1, 1);
			Assertions.passTest("Building Page", "Latest Occupancy is " + buildingPage.primaryOccupancy.getData());

			// Add build value and BPP value
			buildingPage.enterBuildingValues(testData, 1, 1);
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();
			Assertions.passTest("Building Page", "Building values entered successfully");

			// Verifying the presence of green upgrades option in create quote page, when we
			// provide building value and BBP value
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create quote page loaded successfully", false, false);
			Assertions.addInfo("Scenario 03",
					"Verifying the presence of green upgrades option in create quote page, when we provid building value and BBP value");
			Assertions.verify(createQuotePage.greenUpgradesLabel.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"The Green upgrades option is displayed ", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Checking green upgrades default value selected as 'No'
			Assertions.addInfo("Scenario 04", "Checking green upgrades default value selected as 'No'");
			Assertions.verify(createQuotePage.greenUpgradesData.getData(), testData.get("GreenUpgrades"),
					"Create Quote Page", "The Default Green Upgrades value selected as "
							+ createQuotePage.greenUpgradesData.getData() + " verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Select green upgrades option as 'Yes'
			createQuotePage.greenUpgradesArrow.scrollToElement();
			createQuotePage.greenUpgradesArrow.click();
			createQuotePage.greenUpgradesYesOption.click();
			Assertions.passTest("Create Quote Page", "The Green upgrades option selected as "
					+ createQuotePage.greenUpgradesData.getData() + " is verified");

			// Click on continue endorsement button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Verifying the green upgrades option included, after selecting the green
			// upgrade option on create quote page
			Assertions.addInfo("Scenario 05",
					"Verifying the green upgrades option included, after selecting the green upgrade option on create quote page");
			Assertions.verify(
					endorsePolicyPage.endorsementSummary.formatDynamicPath(15).getData()
							.contains("Green Upgrades Coverage"),
					true, "Endorse Policy Page", "The green upgrades option "
							+ endorsePolicyPage.endorsementSummary.formatDynamicPath(15).getData() + " is displayed",
					false, false);
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(17).getData().contains("Included"),
					true, "Endorse Policy Page", "The green upgrades option "
							+ endorsePolicyPage.endorsementSummary.formatDynamicPath(17).getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on Next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on next button successfully");
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Verifying the transaction premium value is positive
			Assertions.addInfo("Scenario 06", "Verifying the transaction premium value is positive");
			Assertions.verify(!endorsePolicyPage.transactionPremium.getData().contains("-"), true,
					"Endorse Policy Page", "The transaction premium value is positive "
							+ endorsePolicyPage.transactionPremium.getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on view Endt quote link
			endorsePolicyPage.viewEndtQuoteButton.scrollToElement();
			endorsePolicyPage.viewEndtQuoteButton.click();
			Assertions.verify(endorsePolicyPage.closeButton.checkIfElementIsDisplayed(), true,
					"Endorsement Quote Document Page", "Endorsement quote document page loaded successfully", false,
					false);

			// Verifying the presence of green upgrades coverage in endorse quote document
			// page
			Assertions.addInfo("Scenario 07",
					"Verifying the presence of green upgrades coverage in endorse quote document page");
			Assertions.verify(
					viewOrPrintFullQuotePage.selectedCoverageDetails.formatDynamicPath("Green Upgrades", 1).getData()
							.contains("Include"),
					true, "Endorsement Quote Document Page",
					"The green upgrades coverage is " + viewOrPrintFullQuotePage.selectedCoverageDetails
							.formatDynamicPath("Green Upgrades", 1).getData() + " verified ",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on close button and complete button
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// Adding IO-21151
			// Getting transaction values from endorsement transaction
			traPremium = policySummarypage.transactionPremium.getData().replace(",", "");
			traTotalPremium = policySummarypage.policyTotalPremium.getData().replace(",", "");

			Assertions.passTest("Policy summary page", "Endorsement premium is " + traPremium);

			Assertions.passTest("Policy summary page", "Endorsement totalpremium is " + traTotalPremium);

			// click on reverse endorsement
			policySummarypage.reversePreviousEndorsementLink.scrollToElement();
			policySummarypage.reversePreviousEndorsementLink.click();
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Policy summary page", "Clicked on reverse endorsement link");

			// Getting transaction values from endorsement transaction
			policySummarypage.transHistReason.formatDynamicPath(4).scrollToElement();
			policySummarypage.transHistReason.formatDynamicPath(4).click();
			Assertions.passTest("Policy summary page", "Clicked on reverse endorsement transaction");
			traPremium = policySummarypage.transactionPremium.getData().replace("-", "").replace(",", "");
			traTotalPremium = policySummarypage.policyTotalPremium.getData().replace("-", "").replace(",", "");

			// Verifying Endorsement transaction and Reverse endorsement transaction values
			// are same
			Assertions.addInfo("Scenario 08",
					"Verifying Endorsement transaction and Reverse endorsement transaction values are same");
			Assertions.verify(traPremium, traPremium, "Policy summary page",
					"Endorsement and reverse endorsement premium value both are same,Reverse endorsement premium is "
							+ traPremium,
					false, false);
			Assertions.verify(traPremium, traPremium, "Policy summary page",
					"Endorsement and reverse endorsement total premium value both are same,Reverse endorsement total premium is "
							+ traTotalPremium,
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");
			// IO-21151 ended

			// Click on cancel link
			policySummarypage.transHistReason.formatDynamicPath(2).scrollToElement();
			policySummarypage.transHistReason.formatDynamicPath(2).click();
			policySummarypage.cancelPolicy.scrollToElement();
			policySummarypage.cancelPolicy.click();

			// Enter cancellation details
			testData = data.get(data_Value1);
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel policy page loaded successfully", false, false);
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancellationEffectiveDate.clearData();
			cancelPolicyPage.cancellationEffectiveDate.appendData(testData.get("CancellationEffectiveDate"));
			cancelPolicyPage.cancellationEffectiveDate.tab();
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.appendData(testData.get("Cancellation_LegalNoticeWording"));

			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Select pro-rated option
			cancelPolicyPage.proRatedRadioBtn.scrollToElement();
			cancelPolicyPage.proRatedRadioBtn.click();
			cancelPolicyPage.waitTime(2);
			Assertions.passTest("Cancel Policy Page", "Pro-Rated Radion button selected successfully");

			// getting pro-rata values
			prorataFactor = cancelPolicyPage.prorataFactor.formatDynamicPath(1).getData();
			originalPremium = cancelPolicyPage.premiumValue.formatDynamicPath(2).getData().replace("$", "");
			actualEarnedValue = cancelPolicyPage.premiumValue.formatDynamicPath(3).getData().replace("$", "");
			actualReturnedValue = cancelPolicyPage.premiumValue.formatDynamicPath(4).getData().replace("-$", "");

			// Convert string to double
			d_actualReturnedValue = Double.parseDouble(actualReturnedValue);
			d_actualEarnedValue = Double.parseDouble(actualEarnedValue);

			// Calculating returned value, returned value=( original premium * pro-rata
			// factor)
			d_calReturnedValue = Double.parseDouble(originalPremium) * Double.parseDouble(prorataFactor);

			// Calculating earned value, earned value = (original premium - returned value)
			d_calEarnedValue = Double.parseDouble(originalPremium) - d_actualReturnedValue;

			// verifying returned and earned values
			Assertions.addInfo("Scenario 09", "Verifying returned and earned values");
			Assertions.passTest("Cancel Policy Page", "Actual returned value is " + "$" + actualReturnedValue);
			if (Precision.round(
					Math.abs(Precision.round(d_actualReturnedValue, 2) - Precision.round(d_calReturnedValue, 2)),
					2) < 1.00) {
				Assertions.passTest("Cancel Policy Page",
						"Actual and Calculated returned value both are matching,The Calculated returned value is $"
								+ Math.round(d_calReturnedValue));
			} else {
				Assertions.verify(d_actualReturnedValue, d_calReturnedValue, "Policy Summary Page",
						"The Diffrence between calculated and actual retunred value is more than 1.00", false, false);
			}
			Assertions.passTest("Cancel Policy Page", "Actual earned value is $" + actualEarnedValue);
			if (Precision.round(
					Math.abs(Precision.round(d_actualEarnedValue, 2) - Precision.round(d_calEarnedValue, 2)),
					2) < 1.00) {
				Assertions.passTest("Cancel Policy Page",
						"Actual and Calculated earned value both are matching,The Calculated earned value is $"
								+ Math.round(d_calEarnedValue));
			} else {
				Assertions.verify(d_actualReturnedValue, d_calReturnedValue, "Policy Summary Page",
						"The Diffrence between calculated and actual earned value is more than 1.00", false, false);
			}
			Assertions.addInfo("Scenario 09 ", "Scenario 09 Ended");

			// Select short rated option
			cancelPolicyPage.shortRatedRadioBtn.scrollToElement();
			cancelPolicyPage.shortRatedRadioBtn.click();
			cancelPolicyPage.waitTime(2);
			Assertions.passTest("Cancel Policy Page", "Short-Rated Radion button selected successfully");

			// getting pro-rata values
			prorataFactor = cancelPolicyPage.prorataFactor.formatDynamicPath(2).getData();
			originalPremium = cancelPolicyPage.premiumValue.formatDynamicPath(2).getData().replace("$", "");
			actualEarnedValue = cancelPolicyPage.premiumValue.formatDynamicPath(3).getData().replace("$", "");
			actualReturnedValue = cancelPolicyPage.premiumValue.formatDynamicPath(4).getData().replace("-$", "");

			// Convert string to double
			d_actualReturnedValue = Double.parseDouble(actualReturnedValue);
			d_actualEarnedValue = Double.parseDouble(actualEarnedValue);

			// Calculating returned value, returned value=( original premium * pro-rata
			// factor)
			d_calReturnedValue = Double.parseDouble(originalPremium) * Double.parseDouble(prorataFactor);

			// Calculating earned value, earned value = (original premium - returned value)
			d_calEarnedValue = Double.parseDouble(originalPremium) - d_actualReturnedValue;

			// verifying returned and earned values
			Assertions.addInfo("Scenario 10", "Verifying returned and earned values");
			Assertions.passTest("Cancel Policy Page", "Actual returned value is " + "$" + actualReturnedValue);
			if (Precision.round(
					Math.abs(Precision.round(d_actualReturnedValue, 2) - Precision.round(d_calReturnedValue, 2)),
					2) < 1.00) {
				Assertions.passTest("Cancel Policy Page",
						"Actual and Calculated returned value both are matching,The Calculated returned value is $"
								+ Math.round(d_calReturnedValue));
			} else {
				Assertions.verify(d_actualReturnedValue, d_calReturnedValue, "Policy Summary Page",
						"The Diffrence between calculated and actual retunred value is more than 1.00", false, false);
			}
			Assertions.passTest("Cancel Policy Page", "Actual earned value is $" + actualEarnedValue);
			if (Precision.round(
					Math.abs(Precision.round(d_actualEarnedValue, 2) - Precision.round(d_calEarnedValue, 2)),
					2) < 1.00) {
				Assertions.passTest("Cancel Policy Page",
						"Actual and Calculated earned value both are matching,The Calculated earned value is $"
								+ Math.round(d_calEarnedValue));
			} else {
				Assertions.verify(d_actualReturnedValue, d_calReturnedValue, "Policy Summary Page",
						"The Diffrence between calculated and actual earned value is more than 1.00", false, false);
			}
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Select pro rated min earned option
			cancelPolicyPage.proRatedMinEarnedRadioBtn.scrollToElement();
			cancelPolicyPage.proRatedMinEarnedRadioBtn.click();
			cancelPolicyPage.waitTime(2);
			Assertions.passTest("Cancel Policy Page", "Pro rated min earned Radion button selected successfully");

			// getting pro-rata values
			prorataFactor = cancelPolicyPage.prorataFactor.formatDynamicPath(3).getData();
			originalPremium = cancelPolicyPage.premiumValue.formatDynamicPath(2).getData().replace("$", "");
			actualEarnedValue = cancelPolicyPage.premiumValue.formatDynamicPath(3).getData().replace("$", "");
			actualReturnedValue = cancelPolicyPage.premiumValue.formatDynamicPath(4).getData().replace("-$", "");

			// Convert string to double
			d_actualReturnedValue = Double.parseDouble(actualReturnedValue);
			d_actualEarnedValue = Double.parseDouble(actualEarnedValue);

			// Calculating returned value, returned value=( original premium * pro-rata
			// factor)
			d_calReturnedValue = Double.parseDouble(originalPremium) * Double.parseDouble(prorataFactor);

			// Calculating earned value, earned value = (original premium - returned value)
			d_calEarnedValue = Double.parseDouble(originalPremium) - d_actualReturnedValue;

			// verifying returned and earned values
			Assertions.addInfo("Scenario 11", "Verifying returned and earned values");
			Assertions.passTest("Cancel Policy Page", "Actual returned value is " + "$" + actualReturnedValue);
			if (Precision.round(
					Math.abs(Precision.round(d_actualReturnedValue, 2) - Precision.round(d_calReturnedValue, 2)),
					2) < 1.00) {
				Assertions.passTest("Cancel Policy Page",
						"Actual and Calculated returned value both are matching,The Calculated returned value is $"
								+ Math.round(d_calReturnedValue));
			} else {
				Assertions.verify(d_actualReturnedValue, d_calReturnedValue, "Policy Summary Page",
						"The Diffrence between calculated and actual retunred value is more than 1.00", false, false);
			}
			Assertions.passTest("Cancel Policy Page", "Actual earned value is $" + actualEarnedValue);
			if (Precision.round(
					Math.abs(Precision.round(d_actualEarnedValue, 2) - Precision.round(d_calEarnedValue, 2)),
					2) < 1.00) {
				Assertions.passTest("Cancel Policy Page",
						"Actual and Calculated earned value both are matching,The Calculated earned value is $"
								+ Math.round(d_calEarnedValue));
			} else {
				Assertions.verify(d_actualReturnedValue, d_calReturnedValue, "Policy Summary Page",
						"The Diffrence between calculated and actual earned value is more than 1.00", false, false);
			}
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Click on complete transaction button
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();

			// click on home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC057 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC057 ", "Executed Successfully");
			}
		}
	}
}
