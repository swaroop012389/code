/** Program Description: Endorse PB-Edit Location/Building Information and Added IO-21518 and IO-21335
 *  Author			   : Yeshashwini T A
 *  Date of Creation   : 06/11/2019
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC049 extends AbstractCommercialTest {

	public TC049() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID049.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		String premium;
		String inspectionFee;
		String policyFee;
		String surpluscontributionValue;
		String actualSlasclearinghouseTransactionFee;
		String slasclearinghouseTransactionFeePrecentage;
		double calSlasclearinghouseTransactionFee;
		String icatFees;
		String actualSLTF;
		String sltfPercentage;
		double calSLTF;
		int dataValue1 = 0;
		int dataValue2 = 1;
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

			// Entering Location 1 Dwelling 1 Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// clicking on request bind button
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// getting the quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Adding below code IO-21335
			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view print full quote page");

			// Verifying presence of BI Limitation verbiage '*Coverage for Business
			// Income/Extra Expense/Rental Value was limited to $480,000 based on valuation
			// of $1,000,000'
			Assertions.addInfo("Scenario 01", "Verifying presence of BI Limitation verbiage");
			Assertions.verify(
					viewOrPrintFullQuotePage.biLimitationVerbiageWordings.getData()
							.contains("*Coverage for Business Income"),
					true, "View print full quote page", "The BI limitation verbiage is "
							+ viewOrPrintFullQuotePage.biLimitationVerbiageWordings.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			// IO-21335 Ended

			// Adding below code IO-21518
			// Verifying presence of "SLAS Clearinghouse Transaction Fee" on view print full
			// quote page
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View print full quote page", "View print full quote page loaded successfully", false, false);
			Assertions.addInfo("Scenario 02",
					"Verifying presence of 'slas clearinghouse transaction fee'on view print full quote page");
			Assertions.verify(viewOrPrintFullQuotePage.slasClearinghouseTrxnFeeLabel.checkIfElementIsDisplayed(), true,
					"View print full quote page", "Slas clearinghouse transaction fee displayed "
							+ viewOrPrintFullQuotePage.slasClearinghouseTrxnFeeLabel,
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Calculating 'SLAS Clearinghouse Transaction Fee(0.175%) = premium +inspection
			// fee + policy fee + surplus contribution value
			premium = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			inspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(",", "");
			policyFee = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "");
			surpluscontributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			actualSlasclearinghouseTransactionFee = viewOrPrintFullQuotePage.slasClearinghouseTrxnFee.getData()
					.replace("$", "").replace(",", "");
			slasclearinghouseTransactionFeePrecentage = testData.get("SLASClearinghouseTransactionFeePrecentage");
			calSlasclearinghouseTransactionFee = (Double.parseDouble(premium) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee) + Double.parseDouble(surpluscontributionValue))
					* Double.parseDouble(slasclearinghouseTransactionFeePrecentage);

			// Verifying calculating and actual slas clearinghouse transaction fee
			Assertions.addInfo("Scenario 03", "Verifying calculated and actual slas clearinghouse transaction fee");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(actualSlasclearinghouseTransactionFee), 2)
					- Precision.round(calSlasclearinghouseTransactionFee, 2)), 2) < 0.05) {
				Assertions.passTest("View Print Full Quote Page",
						"Actual and calculated slas clearinghouse transaction fee both are matching, actual slas clearinghouse transaction fee :"
								+ "$" + actualSlasclearinghouseTransactionFee);
				Assertions.passTest("View Print Full Quote Page", "Calculated slas clearinghouse transaction fee :"
						+ "$" + df.format(calSlasclearinghouseTransactionFee));
			} else {
				Assertions.passTest("View Print Full Quote Page",
						"The Difference between actual  and calculated slas clearinghouse transaction fee is more than 0.05");
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			Assertions.passTest("view print full quote page", "Clicked on back button");

			// Calculating SLTF(0.06)= premium + icatfees + surplus contribution value
			premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			icatFees = accountOverviewPage.icatFees.getData().replace("$", "").replace(",", "");
			surpluscontributionValue = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			actualSLTF = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			sltfPercentage = testData.get("SurplusLinesTaxesPercentage");
			calSLTF = ((Double.parseDouble(premium) + Double.parseDouble(icatFees)
					+ Double.parseDouble(surpluscontributionValue)) * Double.parseDouble(sltfPercentage))
					+ calSlasclearinghouseTransactionFee;

			// Verifying actual and calculated sltf
			Assertions.addInfo("Scenario 04", "Verifying calculated and actual SLTF for OK state(0.06)");
			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(actualSLTF), 2) - Precision.round(calSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Actual and calculated sltf bothe are matching, actual sltf :" + "$" + actualSLTF);
				Assertions.passTest("Account Overview Page", "Calculated sltf :" + "$" + df.format(calSLTF));
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual and calculated sltf is more than 0.05");
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			// IO-21518 Ended

			// Click on request bind button
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

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Find the policy by entering policy Number
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.click();
			homePage.findFilterPolicyOption.scrollToElement();
			homePage.findFilterPolicyOption.click();
			homePage.policyNumber.scrollToElement();
			homePage.policyNumber.setData(policyNumber);
			homePage.findPolicyButton.scrollToElement();
			homePage.findPolicyButton.click();
			Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

			// endorse policy
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endoresment Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on edit Location/Building information hyperlink
			Assertions.addInfo("Scenario 05", "Edit the Location/Building information");
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);

			// Modifying Location Details
			testData = data.get(dataValue2);
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.modifyLocationDetailsCommercial(testData);
			Assertions.passTest("Location Page", "Location details modified successfully");

			// modifying Location 1 Building 1 Details
			Assertions.passTest("Building Page", "Building Page loaded successfully");
			buildingPage.modifyBuildingDetailsPNB_old(testData);
			Assertions.passTest("Building Page", "Building details modified successfully");
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			if (buildingPage.continueButton.checkIfElementIsPresent()
					&& buildingPage.continueButton.checkIfElementIsDisplayed()) {
				buildingPage.continueButton.scrollToElement();
				buildingPage.continueButton.click();
			}

			// clicking on continue button in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on Continue button successfully");

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// clicking on complete button in endorse policy page
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// clicking on close button in endorse policy page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Asserting the Premium and Transaction premiums
			Assertions.addInfo("Scenario 06", "Asserting the Premium and Transaction premiums");
			policySummarypage.transHistReason.formatDynamicPath(2).scrollToElement();
			policySummarypage.transHistReason.formatDynamicPath(2).click();
			Assertions.verify(policySummarypage.policyTotalPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "The Previous Premium is " + policySummarypage.policyTotalPremium.getData(),
					false, false);
			policySummarypage.transHistReason.formatDynamicPath(3).scrollToElement();
			policySummarypage.transHistReason.formatDynamicPath(3).click();
			Assertions.verify(policySummarypage.policyTotalPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"The Transaction Premium is " + policySummarypage.policyTotalPremium.getData(), false, false);
			Assertions.verify(policySummarypage.policyTotalPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "The New Premium is " + policySummarypage.termTotal.getData(), false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// verifying PB Endorsement record in policy summary page
			Assertions.addInfo("Scenario 07", "Verifying PB Endorsement record in policy summary page");
			testData = data.get(dataValue1);
			Assertions.verify(
					policySummarypage.transactionType.formatDynamicPath(testData.get("TransactionType")).getData(),
					testData.get("TransactionType"), "Policy Summary Page", "PB Endorsement Record Verified", false,
					false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 49", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 49", "Executed Successfully");
			}
		}
	}
}