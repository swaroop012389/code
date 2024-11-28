/** Program Description: Check if the Taxes and fees are NOT calculated for waived premium. and added IO-21005
 *  Author			   : Yeshashwini
 *  Date of Creation   : 20/07/2021
 **/
package com.icat.epicenter.test.commercial.retailRegression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
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
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC025 extends AbstractCommercialTest {

	public TC025() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID025.xls";
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
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData;
		testData = data.get(data_Value1);
		String premium;
		double expectedsltf;
		BigDecimal taxes;
		String surplusContributionValue;
		double calSurplusContribution;
		String vieParticipationPercent;
		String vieContributionChargePercent;
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

			// Selecting a peril
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

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on request bind button
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);

			// Click on approve button
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			Assertions.passTest("Referral Page", "Bind Referral Approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// endorse policy
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endoresment Effective Date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on change coverage options hyperlink
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);

			// entering details in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialPNB(testData);
			Assertions.passTest("Create Quote Page", "Details modified successfully");

			// click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked next button");

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on override fees button
			endorsePolicyPage.overrideFeesButton.scrollToElement();
			endorsePolicyPage.overrideFeesButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on override Fees button");
			Assertions.verify(endorsePolicyPage.waivepremium.checkIfElementIsDisplayed(), true,
					"Override Fee/Waive premium Page", "Override Fee/Waive premium Page loaded successfully", false,
					false);

			// Select wave premium
			endorsePolicyPage.waivepremium.scrollToElement();
			endorsePolicyPage.waivepremium.select();
			Assertions.passTest("Override Fee/Waive premium Page", "Waive premium checkbox selected");
			endorsePolicyPage.saveAndCloseButton.click();

			// fetching the term premium
			Assertions.passTest("Endorse Policy Page", "Transaction Term Premium : "
					+ endorsePolicyPage.premium.formatDynamicPath("termPremium", 2).getData());
			Assertions.passTest("Endorse Policy Page", "Transaction Waived Premium : "
					+ endorsePolicyPage.premium.formatDynamicPath("waivedPremium", 2).getData());
			premium = endorsePolicyPage.premium.formatDynamicPath("termPremium", 2).getData().replace("$", "")
					.replace(",", "");
			surplusContributionValue = endorsePolicyPage.surplusContributionValue.formatDynamicPath(2).getData()
					.replace("$", "").replace(",", "");
			expectedsltf = 0.05 * (Double.parseDouble(premium) + Double.parseDouble(surplusContributionValue));
			taxes = new BigDecimal(expectedsltf);
			taxes = taxes.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("Endorse Policy Page",
					"Expected surplus taxes and Fees for Transaction : " + "$" + taxes);
			Assertions.passTest("Endorse Policy Page", "Actual surplus taxes and Fees for Transaction : "
					+ endorsePolicyPage.premium.formatDynamicPath("surplusLinesTaxesAndFees", 2).getData());

			// Transaction Taxes and state Fees for NJ does not include waived premium when
			// waivepremium is selected is verified
			Assertions.addInfo("Scenario 01",
					"Transaction Taxes and state Fees for NJ does not include waived premium when waivepremium is selected is verified");
			Assertions.verify(endorsePolicyPage.premium.formatDynamicPath("surplusLinesTaxesAndFees", 2).getData(),
					"$" + taxes, "Endorse Policy Page",
					"Transaction Taxes and state Fees for NJ does not include waived premium when waivepremium is selected is verified ",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// fetching the annual premium
			Assertions.passTest("Endorse Policy Page",
					"Annual Term Premium : " + endorsePolicyPage.premium.formatDynamicPath("termPremium", 3).getData());
			Assertions.passTest("Endorse Policy Page", "Annual Waived Premium : "
					+ endorsePolicyPage.premium.formatDynamicPath("waivedPremium", 3).getData());
			premium = endorsePolicyPage.premium.formatDynamicPath("termPremium", 3).getData().replace("$", "")
					.replace(",", "");
			surplusContributionValue = endorsePolicyPage.surplusContributionValue.formatDynamicPath(3).getData()
					.replace("$", "").replace(",", "");

			expectedsltf = 0.05 * (Double.parseDouble(premium) + Double.parseDouble(surplusContributionValue));
			taxes = new BigDecimal(expectedsltf);
			taxes = taxes.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("Endorse Policy Page", "Expected surplus taxes and Fees for Annual : " + "$" + taxes);
			Assertions.passTest("Endorse Policy Page", "Actual surplus taxes and Fees for Annual : "
					+ endorsePolicyPage.premium.formatDynamicPath("surplusLinesTaxesAndFees", 3).getData());

			// Annual Taxes and state Fees for NJ does not include waived premium when
			// waivepremium is selected is verified
			Assertions.addInfo("Scenario 02", "Checking the Taxes and fees are NOT calculated for waived premium.");
			Assertions.verify(endorsePolicyPage.premium.formatDynamicPath("surplusLinesTaxesAndFees", 3).getData(),
					"$" + taxes, "Endorse Policy Page",
					"Annual Taxes and state Fees for NJ does not include waived premium when waivepremium is selected is verified ",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on Complete
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on Close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on complete and close button successfully");

			// Adding IO-21005
			// Performing OOS endorsement and Click on endorse PB link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endorsement Effective Date
			testData = data.get(data_Value3);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Click on continue button
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on continue button");

			// clicking on change coverage options hyperlink
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);

			// entering details in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialPNB(testData);
			Assertions.passTest("Create Quote Page", "Details modified successfully");

			// click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked next button");

			if(endorsePolicyPage.oKContinueButton.checkIfElementIsPresent()&&endorsePolicyPage.oKContinueButton.checkIfElementIsDisplayed())
			{
				endorsePolicyPage.oKContinueButton.scrollToElement();
				endorsePolicyPage.oKContinueButton.click();
			}

			// Click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse policy page", "Clicked oncomplete button successfully");

			// Click on continue button
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on continue button");

			// Verifying presence of surplus contribution row on OOS transaction endorsement
			Assertions.addInfo("Scenario 03",
					"Verifying presence of surplus contribution row on OOS transaction endorsement");
			Assertions.verify(
					endorsePolicyPage.surplusContributionValue.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Endorse policy page", "Surplus contribution row displayed", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Getting premium and surplus contribution of transaction
			premium = endorsePolicyPage.premium.formatDynamicPath("termPremium", 2).getData().replace("$", "")
					.replace(",", "").replace("-", "");
			surplusContributionValue = endorsePolicyPage.surplusContributionValue.formatDynamicPath(2).getData()
					.replace("$", "").replace(",", "").replace("-", "");
			vieParticipationPercent = testData.get("VIEparticipationPercent");
			vieContributionChargePercent = testData.get("VIEContributionChargePercent");
			// Calculating surplus contribution = VIE participation *VIE Contribution
			// Charge*(premium)
			// VIE participation = 100% and VIE Contribution Charge = 10%
			calSurplusContribution = Double.parseDouble(vieParticipationPercent)
					* Double.parseDouble(vieContributionChargePercent) * (Double.parseDouble(premium));

			// Verifying actual and calculated surplus contribution for OOS transaction
			// endorsement
			Assertions.addInfo("Scenario 04",
					"Verifying actual and calculated surplus contribution for OOS transaction endorsement");
			Assertions.passTest("Endorse policy page", "Actual surplus contribution is -$" + surplusContributionValue);
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(surplusContributionValue), 2)
					- Precision.round(calSurplusContribution, 2)), 2) < 1.00) {
				Assertions.passTest("Endorse policy page",
						"Actual and Calculated surplus contribution both are matching,The Calculated surplus contribution value is -$"
								+ Precision.round(calSurplusContribution, 1));
			} else {
				Assertions.verify(Double.parseDouble(surplusContributionValue), calSurplusContribution,
						"Endorse policy page",
						"The Diffrence between calculated and actual surplus contribution value is more than 1.00",
						false, false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Click on Close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse policy page", "Clicked on complete and close button successfully");

			// Click on reversal endorsement reason
			policySummaryPage.transHistReason.formatDynamicPath(4).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(4).click();
			Assertions.passTest("Policy summary page", "Clicked on reversal transaction");

			// Verifying presence of surplus contribution row on policy summary page of
			// reversal endorsement transaction
			Assertions.addInfo("Scenario 05",
					"Verifying presence of surplus contribution row on policy summary page of reversal endorsement transaction");
			Assertions.verify(
					policySummaryPage.surplusContributionValue.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Policy summary page", "Surplus contribution row displayed for reversal endorsement transaction ",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");
			// IO-21005 Ended

			// ------IO-21127 Added-----
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.myReferralArrow.scrollToElement();
			homePage.myReferralArrow.click();
			homePage.myReferralsOption.formatDynamicPath("File Review").scrollToElement();
			homePage.myReferralsOption.formatDynamicPath("File Review").click();

			// Getting the file review Policy from the My Referral section
			// change the format dynamic path of 75 if it fails here.
			homePage.userResultTable.formatDynamicPath("75", 1).scrollToElement();
			String fileReviewpolicy = homePage.userResultTable.formatDynamicPath("75", 1).getData();
			homePage.searchPolicy(fileReviewpolicy);
			policySummaryPage.openCurrentReferral.scrollToElement();
			policySummaryPage.openCurrentReferral.click();
			referralPage.viewPolicy.scrollToElement();
			referralPage.viewPolicy.click();

			Assertions.addInfo("Policy Summary Page",
					"Verifying that the policy summary page is loaded when clicked on the 'View Policy' button");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			// ------IO-21127 Ended-----

			// Signing out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC025 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC025 ", "Executed Successfully");
			}
		}
	}
}
