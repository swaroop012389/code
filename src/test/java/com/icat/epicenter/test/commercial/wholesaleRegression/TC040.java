/** Program Description : 1. Create an AOP Policy,initiate a renewal and create a renewal requote.
 * 						  2. IO-21070 - PBU Renewals Missing Surplus Contribution
 *                        3. IO-21033 - Reciprocal quote premium including surplus contribution twice
 *                        4. IO-21039 - Surplus Contribution should not be included in the calculations for policy or inspection fees
 *  Author			    : Vinay
 *  Date of Creation    : 11/19/2019
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

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
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC040 extends AbstractCommercialTest {

	public TC040() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID040.xls";
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
		GLInformationPage glInformationPage = new GLInformationPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		String aoICATFeeValue;
		double d_aoICATFeeValue;
		String aoPremiumValue;
		double d_aoPremiumValue;
		String aoTotalPremiumValue;
		double d_aoTotalPremiumValue;
		double calcTotalPremiumValue;
		String aoSurplusLineTax;
		double d_aoSurplusLineTax;
		String aoQuotePremiumValue;
		double d_aoQuotePremiumValue;
		DecimalFormat df = new DecimalFormat("0.00");
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
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

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

			// entering general liability details
			if (!testData.get("L1-GLLocationClass").equals("")) {
				Assertions.verify(glInformationPage.locationClassArrow.checkIfElementIsDisplayed(), true,
						"GL Information Page", "GL Information Page loaded successfully", false, false);
				glInformationPage.enterGLInformation(testData);
				Assertions.passTest("GL Information Page", "GL Information details entered successfully");
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

			// Adding Ticket IO-21039
			// Clicking on View or Print Full Quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Verifying if the actual Policy Fee and Inspection Value are matching the
			// Expected Value per the Rater Sheet
			Assertions.addInfo("Scenario 01",
					"Verifying if the Policy Fee and Inspection Fee are matching the Rater Sheet Value");
			Assertions.verify(viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(".00", ""),
					testData.get("PolicyFee"), "View or Print Full Quote Page",
					"The Policy Fee Value is matching the Rater Sheet Value and the Surplus Contribution Value is not included",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(".00", ""),
					testData.get("InspectionFee"), "View or Print Full Quote Page",
					"The Inspection Fee Value is matching the Rater Sheet Value and the Surplus Contribution Value is not included",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Clicking on Back button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Clicking on Request Bind Button
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

			// Approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
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

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Adding Ticket IO-21039
			Assertions.addInfo("Scenario 02",
					"Verifying if the Policy Fee and Inspection Fee are matching the Rater Sheet Values on Policy Summary Page");
			Assertions.verify(policySummarypage.policyFee.formatDynamicPath(1).getData().replace("$", ""),
					testData.get("PolicyFee"), "Policy Summary Page",
					"The Policy Fee Value on Transaction Column is matching the Rater Sheet Value and the "
							+ "Surplus Contribution is not included to Policy Fee",
					false, false);

			Assertions.verify(policySummarypage.inspectionFee.formatDynamicPath(1).getData().replace("$", ""),
					testData.get("InspectionFee"), "Policy Summary Page",
					"The Inspection Fee Value on Transaction Column is matching the Rater Sheet Value and the "
							+ "Surplus Contribution is not included to Policy Fee",
					false, false);

			Assertions.verify(policySummarypage.policyFee.formatDynamicPath(2).getData().replace("$", ""),
					testData.get("PolicyFee"), "Policy Summary Page",
					"The Policy Fee Value on Annual Column is matching the Rater Sheet Value and the "
							+ "Surplus Contribution is not included to Policy Fee",
					false, false);
			Assertions.verify(policySummarypage.inspectionFee.formatDynamicPath(2).getData().replace("$", ""),
					testData.get("InspectionFee"), "Policy Summary Page",
					"The Inspection Fee Value on Annual Column is matching the Rater Sheet Value and the "
							+ "Surplus Contribution is not included to Policy Fee",
					false, false);

			Assertions.verify(policySummarypage.policyFee.formatDynamicPath(3).getData().replace("$", ""),
					testData.get("PolicyFee"), "Policy Summary Page",
					"The Policy Fee Value on Term Total Column is matching the Rater Sheet Value and the "
							+ "Surplus Contribution is not included to Policy Fee",
					false, false);
			Assertions.verify(policySummarypage.inspectionFee.formatDynamicPath(3).getData().replace("$", ""),
					testData.get("InspectionFee"), "Policy Summary Page",
					"The Inspection Fee Value on Term Total Column is matching the Rater Sheet Value and the "
							+ "Surplus Contribution is not included to Policy Fee",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			policySummarypage.policyFee.formatDynamicPath(3).getData().replace("$", "");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Click on Expacc link
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// click on renew policy
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();

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

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();

			// getting the renewal quote number
			Assertions.addInfo("Scenario 03", "Assert the renewal quote number on Account Overview Page");
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Below code commenting because of reciprocal not available for renewal,once
			// reciprocal available we will update the code
			// Adding IO-21070
			// Verifying if Surplus Contribution is Present on Account Overview Page
			Assertions.addInfo("Scenario 03",
					"Verifying if the Surplus Contribution Value is present on the Renewal Account");
			Assertions.verify(
					accountOverviewPage.surplusContributionValue.checkIfElementIsPresent()
							&& accountOverviewPage.surplusContributionValue.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "The Surplus Contribution Value is Present: "
							+ accountOverviewPage.surplusContributionValue.getData(),
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Fetching and Calculating Surplus Contribution Values
			aoPremiumValue = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			d_aoPremiumValue = Double.parseDouble(aoPremiumValue);
			aoICATFeeValue = accountOverviewPage.icatFees.getData().replace("$", "");
			d_aoICATFeeValue = Double.parseDouble(aoICATFeeValue);
			aoSurplusLineTax = accountOverviewPage.sltfValue.getData().replace("$", "");
			d_aoSurplusLineTax = Double.parseDouble(aoSurplusLineTax);
			String aoSurplusContributionValue = accountOverviewPage.surplusContributionValue.getData().replace("$", "");
			double d_aoSurplusContributionValue = Double.parseDouble(aoSurplusContributionValue);
			aoTotalPremiumValue = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", "");
			d_aoTotalPremiumValue = Double.parseDouble(aoTotalPremiumValue);
			calcTotalPremiumValue = d_aoPremiumValue + d_aoICATFeeValue + d_aoSurplusLineTax
					+ d_aoSurplusContributionValue;

			// Verifying if the Calculated Total Premium Value and Actual Total Premium
			// Value are the Same on Account Overview Page
			Assertions.addInfo("Scenario 04",
					"Verifying if the Calculated Total Premium Value and Actual Total Premium Value "
							+ "are the Same on Account Overview Page");
			Assertions.verify(d_aoTotalPremiumValue, calcTotalPremiumValue, "Account Overview Page",
					"The actual Premium Value and calculated Premium Values are matching" + "Actual Premium Value: "
							+ d_aoTotalPremiumValue + "and Calculated Premium Value: " + calcTotalPremiumValue,
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Below code commenting because of reciprocal not available for renewal,once
			// reciprocal available we will update the code
			// Adding IO-21070
			// Clicking on View or Print Full Quote Page
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Verifying if Surplus Contribution is Present on VieworPrintFullQuotePage
			Assertions.addInfo("Scenario 05",
					"Verifying if the Surplus Contribution Value is present on View or Print Full Quote Page");
			Assertions.verify(
					viewOrPrintFullQuotePage.surplusContributionValue.checkIfElementIsPresent()
							&& viewOrPrintFullQuotePage.surplusContributionValue.checkIfElementIsDisplayed(),
					true, "View or Print Full Quote Page",
					"The Surplus Contribution Value is present on View or Print Full Quote Page: "
							+ viewOrPrintFullQuotePage.surplusContributionValue.getData(),
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Clicking on Back Button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// click on edit deductibles and limits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// Entering Create quote page Details
			testData = data.get(data_Value2);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 06", "Assert the renewal requote number on Account Overview Page");
			String quoteNumber2 = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number 2 :  " + quoteNumber2);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Adding Ticket IO-21033
			// Fetching values for Premium Calculation and Verifying the Values
			aoPremiumValue = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			d_aoPremiumValue = Double.parseDouble(aoPremiumValue);
			aoICATFeeValue = accountOverviewPage.icatFees.getData().replace("$", "");
			d_aoICATFeeValue = Double.parseDouble(aoICATFeeValue);
			aoSurplusLineTax = accountOverviewPage.sltfValue.getData().replace("$", "");
			d_aoSurplusLineTax = Double.parseDouble(aoSurplusLineTax);
			aoSurplusContributionValue = accountOverviewPage.surplusContributionValue.getData().replace("$", "");
			d_aoSurplusContributionValue = Double.parseDouble(aoSurplusContributionValue);
			aoTotalPremiumValue = accountOverviewPage.totalPremiumValue.getData().replace("$", "").replace(",", "");
			d_aoTotalPremiumValue = Double.parseDouble(aoTotalPremiumValue);
			calcTotalPremiumValue = d_aoPremiumValue + d_aoICATFeeValue + d_aoSurplusLineTax
					+ d_aoSurplusContributionValue;
			aoQuotePremiumValue = accountOverviewPage.quotePremium.formatDynamicPath(2).getData().substring(1, 10)
					.replace("$", "").replace(",", "");
			d_aoQuotePremiumValue = Double.parseDouble(aoQuotePremiumValue);

			// Verifying if the Calculated Total Premium Value and Actual Total Premium
			// Value are the Same on Account Overview Page
			Assertions.addInfo("Scenario 07",
					"Verifying if the Calculated Total Premium Value and Actual Total Premium Value "
							+ "are the Same on Account Overview Page");

			if (Precision.round(
					Math.abs(Precision.round(d_aoTotalPremiumValue, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"The actual Premium Value is : " + "$" + d_aoTotalPremiumValue);
				Assertions.passTest("Account Overview Page",
						"Calculated Premium Values is : " + "$" + df.format(calcTotalPremiumValue));
			} else {
				Assertions.verify(d_aoTotalPremiumValue, calcTotalPremiumValue, "Account Overview Page",
						"The Difference between actual and calculated Premium is more than 0.05", false, false);

			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Verifying if the Quote Premium Value on the left Pane is same as the
			// Calculated Total Premium and the Actual Total Premium Values
			Assertions.addInfo("Scenario 08",
					"Verifying if the Calculated Total Premium Value and Actual Quote Premium Value "
							+ "are the Same on Account Overview Page");

			if (Precision.round(
					Math.abs(Precision.round(d_aoQuotePremiumValue, 2) - Precision.round(calcTotalPremiumValue, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"The actual quote premium Value is : " + "$" + d_aoQuotePremiumValue);
				Assertions.passTest("Account Overview Page",
						"Calculated Premium Values is : " + "$" + df.format(calcTotalPremiumValue));
			} else {
				Assertions.verify(d_aoQuotePremiumValue, calcTotalPremiumValue, "Account Overview Page",
						"The Difference between actual and calculated Premium is more than 0.05", false, false);

			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			Assertions.addInfo("Scenario 09",
					"Verifying if the Actual Quote Total Premium Value and Actual Total Premium Value "
							+ "are the Same on Account Overview Page");
			Assertions.verify(d_aoQuotePremiumValue, d_aoTotalPremiumValue, "Account Overview Page",
					"The actual Premium Value and actual quote Premium Values are matching "
							+ "Actual Quote Premium Value: " + d_aoQuotePremiumValue
							+ " and Actual Total Premium Value: " + d_aoTotalPremiumValue,
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Fetching Total Premium Value on the Left Pane of Account Overview Page

			// Click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber2);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// added below code to handle order of the quote displayed(Order is different in
			// UAT and QA)on the account overview page
			if (requestBindPage.submit.checkIfElementIsPresent()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber2);
				accountOverviewPage.requestBind.scrollToElement();
				accountOverviewPage.requestBind.click();
			}

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Bind the renewal quote
			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			if (bindRequestPage.homePage.checkIfElementIsPresent()
					&& bindRequestPage.homePage.checkIfElementIsDisplayed()) {
				Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
						"Bind Request Page loaded successfully", false, false);
				bindRequestPage.clickOnHomepagebutton();
				Assertions.passTest("Home Page", "Clicked on Home button");

				// searching the quote number in grid and clicking on the quote link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber2);
				Assertions.passTest("Home Page", "Quote opened successfully");

				// click on open referral link
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

				// approving referral
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				requestBindPage.approveRequest();
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			}

			// getting the policy number
			Assertions.addInfo("Scenario 10", "Assert the renewal policy number on Policy Summary Page");
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Renewal Policy Number is : " + policyNumber);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 40", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 40", "Executed Successfully");
			}
		}
	}
}
