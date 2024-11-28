/** Description: Check for Default Cancellation Type and the Short Rated Pro-rata factor values, Earned Premium, Returned Premium and Total Values chosen
For various Cancellation Reasons and for various Time Period in which the Policy is in effect for Wind State New Jersey
Author: Priyanka S
Date :  09/03/2022 **/
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
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC046 extends AbstractCommercialTest {

	public TC046() {
		super(LoginType.ADMIN);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID046.xls";
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
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		double calShort_RatedProrataFactor;
		String actualShortratedProrataFactor;
		double d_actualShortratedProrataFactor;
		String originalPremiumData;
		double originalPremiumValue;
		String proRatedData;
		double proRatedValue;
		String shortRatedData;
		double shortRatedValue;

		String proRatedDataRnl;
		double proRatedValueRnl;
		String shortRatedDataRnl;
		double shortRatedValueRnl;
		String originalPremiumDataRnl;
		double originalPremiumValueRnl;

		String actualEarnedPremiumRnl;
		int d_actualEarnedPremiumRnl;
		String actualReturnedPremiumRnl;
		int d_actualReturnedPremiumRnl;
		double calEarned_PremiumRnl;
		int d_calEarned_PremiumRnl;
		double calReturned_PremiumRnl;
		int d_calReturned_PremiumRnl;

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
			if (priorLossesPage.continueButton.checkIfElementIsPresent()
					&& priorLossesPage.continueButton.checkIfElementIsDisplayed()) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// Getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Enter Bind Details
			Assertions.verify(
					requestBindPage.submit.checkIfElementIsPresent()
							&& requestBindPage.submit.checkIfElementIsDisplayed(),
					true, "Request Bind Page", "Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// Searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// Approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on Cancel Policy link
			policySummarypage.cancelPolicy.waitTillPresenceOfElement(60);
			policySummarypage.cancelPolicy.scrollToElement();
			policySummarypage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on cancel policy link successfully");

			// Enter cancellation details
			Assertions.verify(
					cancelPolicyPage.nextButton.checkIfElementIsPresent()
							&& cancelPolicyPage.nextButton.checkIfElementIsDisplayed(),
					true, "Cancel Policy Page", "Cancel Policy page loaded successfully", false, false);

			// Comparing calculated and actual pro-rated prorata factor and
			// short-rated
			// prorata factor under cancellation option for all insured request
			Assertions.addInfo("Cancel Policy Page",
					"Calculating and Comparing the Prorata values of Pro Rated and Short Rated when Cancellation reason selecting insured request");
			Assertions.addInfo("Scenario 01", "Verification Prorata for Pro-Rated and Short-Rated Factor");
			for (int i = 0; i < 4; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);
				cancelPolicyPage.cancelReasonArrow.scrollToElement();
				cancelPolicyPage.cancelReasonArrow.click();
				String cancelReasoni = cancelPolicyPage.cancelReasonOption
						.formatDynamicPath(testData.get("CancellationReason")).getData();
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason"))
						.scrollToElement();
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
				Assertions.passTest("Cancel Policy Page", "The Cancel Reason Selected is " + cancelReasoni);
				for (int j = 0; j < 1; j++) {
					cancelPolicyPage.legalNoticeWording.waitTillPresenceOfElement(60);
					cancelPolicyPage.legalNoticeWording.scrollToElement();
					cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
				}
				cancelPolicyPage.cancellationEffectiveDate.waitTillPresenceOfElement(60);
				cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
				cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
				cancelPolicyPage.cancellationEffectiveDate.tab();
				cancelPolicyPage.nextButton.waitTillPresenceOfElement(60);
				cancelPolicyPage.nextButton.scrollToElement();
				cancelPolicyPage.nextButton.click();

				// Getting Actual short-rated prorata factor
				actualShortratedProrataFactor = cancelPolicyPage.prorataFactor.formatDynamicPath(2).getData();
				d_actualShortratedProrataFactor = Double.parseDouble(actualShortratedProrataFactor);

				// Short_rated value
				shortRatedData = cancelPolicyPage.prorataFactor.formatDynamicPath(2).getData();
				shortRatedValue = Double.parseDouble(shortRatedData);

				// Original Premium Value
				originalPremiumData = cancelPolicyPage.premiumValue.formatDynamicPath(2).getData().replace("$", "");
				originalPremiumValue = Double.parseDouble(originalPremiumData);

				// Adding code for IO-20050 - PBU Prod - Short rate cancellation calculating
				// incorrectly
				// Calculating Original Short Rated Earned Premium Value
				String originalShortRatedEarnedPremium = cancelPolicyPage.earnedPremiumValue.formatDynamicPath(11)
						.getData().replace("$", "");
				double originalShortRatedEarnedPremiumValue = Double.parseDouble(originalShortRatedEarnedPremium);
				int d_originalShortRatedEarnedPremiumValue = (int) Math.round(originalShortRatedEarnedPremiumValue);
				double calcShortRatedEarnedPremium = originalPremiumValue * (1 - d_actualShortratedProrataFactor);
				int d_calcShortRatedEarnedPremium = (int) Math.round(calcShortRatedEarnedPremium);

				// Calculating Short Rated Returned Premium
				String originalShortRatedReturnedPremium = cancelPolicyPage.returnedPremiumValue.formatDynamicPath(7)
						.getData().replace("-", "").replace("$", "");
				double originalShortRatedReturnedPremiumValue = Double.parseDouble(originalShortRatedReturnedPremium);
				int d_originalShortRatedReturnedPremiumValue = (int) Math.round(originalShortRatedReturnedPremiumValue);
				double calcShortRatedReturnedPremium = originalPremiumValue * d_actualShortratedProrataFactor;
				int d_calcShortRatedReturnedPremium = (int) Math.round(calcShortRatedReturnedPremium);

				// Comparing Actual and calculated Short Rated Premium Value
				if (Precision.round(Math.abs(Precision.round(d_originalShortRatedEarnedPremiumValue, 2)
						- Precision.round(d_calcShortRatedEarnedPremium, 2)), 2) < 2.00) {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Short-Rated Premium is " + d_calcShortRatedEarnedPremium);
					Assertions.passTest("Cancel Policy Page",
							"Actual Short-Rated Premium is " + d_originalShortRatedEarnedPremiumValue);
					Assertions.passTest("Cancel Policy Page",
							"Actual Short-Rated Earned Premium Value and calculated Short Rated Earned Premium Value both are equal");
				} else {
					Assertions.passTest("Cancel Policy Page",
							"The difference between Actual and calculated Short-Rated Earned Premium Value is more than 2.00");
				}

				// Comparing Actual and calculated Short Rated Returned Premium Value
				if (Precision.round(Math.abs(Precision.round(d_originalShortRatedReturnedPremiumValue, 2)
						- Precision.round(d_calcShortRatedReturnedPremium, 2)), 2) < 2.00) {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Short-Rated Premium is $" + d_calcShortRatedReturnedPremium);
					Assertions.passTest("Cancel Policy Page",
							"Actual Short-Rated Premium is $" + d_originalShortRatedReturnedPremiumValue);
					Assertions.passTest("Cancel Policy Page",
							"Actual Short-Rated Returned Premium Value and calculated Short-Rated Returned Premium Value both are equal");
				} else {
					Assertions.passTest("Cancel Policy Page",
							"The difference between Actual and calculated Short-Rated Returned Premium Value is more than 2.00");
				}

				// Calculating Earned Premium
				double calEarned_Premium = originalPremiumValue * (1 - shortRatedValue);
				int d_calEarned_Premium = (int) Math.round(calEarned_Premium);

				// Calculating Earned Premium
				double calReturned_Premium = originalPremiumValue * shortRatedValue;
				int d_calReturned_Premium = (int) Math.round(calReturned_Premium);

				// Getting Actual Earned Premium Data
				String actualEarnedPremium = cancelPolicyPage.premiumValue.formatDynamicPath(3).getData().replace("$",
						"");
				int d_actualEarnedPremium = Integer.parseInt(actualEarnedPremium);

				// Pro-rated premium value
				proRatedData = cancelPolicyPage.prorataFactor.formatDynamicPath(1).getData();
				proRatedValue = Double.parseDouble(proRatedData);

				// Getting Actual Returned Premium Data
				String actualReturnedPremium = cancelPolicyPage.premiumValue.formatDynamicPath(4).getData()
						.replace("$", "").replace("-", "");
				int d_actualReturnedPremium = Integer.parseInt(actualReturnedPremium);

				// Calculating pro rata factor for short-rated pro rata factor
				// formula(90% of
				// Pro-rated pro rata factor(.97))
				// 90%=90/100=0.9*0.97=0.873
				testData = data.get(data_Value1);
				calShort_RatedProrataFactor = 0.9 * proRatedValue;
				double d_calShortRatedProrataFactor = Precision.round(calShort_RatedProrataFactor, 3);

				// Comparing Actual and calculated values for short-rated prorata
				// factor
				if (Precision.round(Math.abs(Precision.round(d_actualShortratedProrataFactor, 2)
						- Precision.round(d_calShortRatedProrataFactor, 2)), 2) < 2.00) {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Short-Rated Prorata Factor is " + d_calShortRatedProrataFactor);
					Assertions.passTest("Cancel Policy Page",
							"Short Rated Pro-Rata Factor is " + d_actualShortratedProrataFactor);
					Assertions.verify(d_actualShortratedProrataFactor, d_calShortRatedProrataFactor,
							"Cancel Policy Page",
							"Actual Shortrated Prorata Factor and calculated Shortrated Prorata Factor both are equal",
							false, false);
				} else {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Short-Rated Prorata Factor is " + d_calShortRatedProrataFactor);
					Assertions.passTest("Cancel Policy Page",
							"Short Rated Pro-Rata Factor is " + d_actualShortratedProrataFactor);
					Assertions.passTest("Cancel Policy Page",
							"The Difference between actual and calculated Short Rated Pro-Rata Factor more than 2.00");
				}

				// Comparing Actual and calculated values for Earned Premium Data
				if (Precision.round(
						Math.abs(Precision.round(d_actualEarnedPremium, 2) - Precision.round(d_calEarned_Premium, 2)),
						2) < 2.00) {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Earned Premium : " + "$" + d_calEarned_Premium);
					Assertions.passTest("Cancel Policy Page",
							"Actual Actual Earned Pemium : " + "$" + d_actualEarnedPremium);
				} else {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Earned Premium : " + "$" + d_calEarned_Premium);
					Assertions.passTest("Cancel Policy Page",
							"Actual Actual Earned Pemium : " + "$" + d_actualEarnedPremium);
					Assertions.passTest("Cancel Policy Page",
							"The Difference between actual and calculated Earned Premium value is more than 5.00");
				}

				// Comparing Actual and calculated values for Returned Premium Data
				if (Precision.round(Math
						.abs(Precision.round(d_actualReturnedPremium, 2) - Precision.round(d_calReturned_Premium, 2)),
						2) < 2.00) {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Returned Premium : " + "$" + d_calReturned_Premium);
					Assertions.passTest("Cancel Policy Page",
							"Actual Actual Returned Pemium : " + "$" + d_actualReturnedPremium);
				} else {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Returned Premium : " + "$" + d_calReturned_Premium);
					Assertions.passTest("Cancel Policy Page",
							"Actual Actual Returned Pemium : " + "$" + d_actualReturnedPremium);
					Assertions.passTest("Cancel Policy Page",
							"The Difference between actual and calculated Returned premium is more than 5.00");
				}

				// Click on previous button
				cancelPolicyPage.previousButton.waitTillPresenceOfElement(60);
				cancelPolicyPage.previousButton.scrollToElement();
				cancelPolicyPage.previousButton.click();
				Assertions.passTest("Cancel Policy page", "Navigated to Cancel policy page");
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on cancel Button
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();
			Assertions.passTest("Cancel Policy page", "Clicked on cancel button");

			// Click on Renewal link
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();

			// Go to Home Page
			if (policySummarypage.expaacMessage.checkIfElementIsPresent()
					&& policySummarypage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// Clicking on renewal policy link
				policySummarypage.renewPolicy.waitTillPresenceOfElement(60);
				policySummarypage.renewPolicy.scrollToElement();
				policySummarypage.renewPolicy.click();
				Assertions.passTest("Policy summary page", "Clicked on renewal link successfully");
			}

			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			if (policyRenewalPage.renewalYes.checkIfElementIsPresent()
					&& policyRenewalPage.renewalYes.checkIfElementIsDisplayed()) {
				policyRenewalPage.renewalYes.scrollToElement();
				policyRenewalPage.renewalYes.click();
			}

			// Getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account overview page", "Renewal quote number :  " + quoteNumber);

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// Click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account overview page", "Clicked on request bind button");

			// Enter bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind page",
					"Bind request page loaded successfully", false, false);

			requestBindPage.renewalRequestBind(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Clicking on home button
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home page", "Clicked on home button");

			// Searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// Approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			Assertions.passTest("Referral page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request bind page", "Bind request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy summary page", "Policy summary page loaded successfully");
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.verify(policySummarypage.policyNumber.getData().contains(policyNumber), true,
					"Policy summary page", "Renewal policy number is " + policyNumber, false, false);

			// Click on Cancel Policy link
			policySummarypage.cancelPolicy.waitTillPresenceOfElement(60);
			policySummarypage.cancelPolicy.scrollToElement();
			policySummarypage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on cancel policy link successfully");

			// Enter cancellation details
			Assertions.verify(
					cancelPolicyPage.nextButton.checkIfElementIsPresent()
							&& cancelPolicyPage.nextButton.checkIfElementIsDisplayed(),
					true, "Cancel Policy Page", "Cancel Policy page loaded successfully", false, false);

			// Comparing calculated and actual pro-rated prorata factor and
			// short-rated
			// prorata factor under cancellation option for all insured request
			Assertions.addInfo("Cancel Policy Page",
					"Calculating and Comparing the Prorata values of Pro Rated and Short Rated when Cancellation reason selecting insured request");
			Assertions.addInfo("Scenario 02", "Verification Prorata for Short-Rated Factor after Renewal");
			for (int i = 4; i < 8; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);
				cancelPolicyPage.cancelReasonArrow.scrollToElement();
				cancelPolicyPage.cancelReasonArrow.click();
				String cancelReasoni = cancelPolicyPage.cancelReasonOption
						.formatDynamicPath(testData.get("CancellationReason")).getData();
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason"))
						.scrollToElement();
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
				Assertions.passTest("Cancel Policy Page", "The Cancel Reason Selected is " + cancelReasoni);
				for (int j = 0; j < 1; j++) {
					cancelPolicyPage.legalNoticeWording.waitTillPresenceOfElement(60);
					cancelPolicyPage.legalNoticeWording.scrollToElement();
					cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
				}
				cancelPolicyPage.cancellationEffectiveDate.waitTillPresenceOfElement(60);
				cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
				cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
				cancelPolicyPage.cancellationEffectiveDate.tab();
				cancelPolicyPage.nextButton.waitTillPresenceOfElement(60);
				cancelPolicyPage.nextButton.scrollToElement();
				cancelPolicyPage.nextButton.click();

				// Getting Actual short-rated prorata factor
				actualShortratedProrataFactor = cancelPolicyPage.prorataFactor.formatDynamicPath(2).getData();
				d_actualShortratedProrataFactor = Double.parseDouble(actualShortratedProrataFactor);

				// Short_rated value
				shortRatedData = cancelPolicyPage.prorataFactor.formatDynamicPath(2).getData();
				shortRatedValue = Double.parseDouble(shortRatedData);

				// Original Premium Value
				originalPremiumData = cancelPolicyPage.premiumValue.formatDynamicPath(2).getData().replace("$", "");
				originalPremiumValue = Double.parseDouble(originalPremiumData);

				// Adding code for IO-20050 - PBU Prod - Short rate cancellation calculating
				// incorrectly
				// Calculating Original ProRated Earned Premium Value
				String originalShortRatedEarnedPremium = cancelPolicyPage.earnedPremiumValue.formatDynamicPath(11)
						.getData().replace("$", "");
				double originalShortRatedEarnedPremiumValue = Double.parseDouble(originalShortRatedEarnedPremium);
				int d_originalShortRatedEarnedPremiumValue = (int) Math.round(originalShortRatedEarnedPremiumValue);
				double calcShortRatedEarnedPremium = originalPremiumValue * (1 - d_actualShortratedProrataFactor);
				int d_calcShortRatedEarnedPremium = (int) Math.round(calcShortRatedEarnedPremium);

				// Calculating ProRated Returned Premium
				String originalShortRatedReturnedPremium = cancelPolicyPage.returnedPremiumValue.formatDynamicPath(7)
						.getData().replace("-", "").replace("$", "");
				double originalShortRatedReturnedPremiumValue = Double.parseDouble(originalShortRatedReturnedPremium);
				int d_originalShortRatedReturnedPremiumValue = (int) Math.round(originalShortRatedReturnedPremiumValue);
				double calcShortRatedReturnedPremium = originalPremiumValue * d_actualShortratedProrataFactor;
				int d_calcShortRatedReturnedPremium = (int) Math.round(calcShortRatedReturnedPremium);

				// Comparing Actual and calculated Pro Rated Premium Value
				if (Precision.round(Math.abs(Precision.round(d_originalShortRatedEarnedPremiumValue, 2)
						- Precision.round(d_calcShortRatedEarnedPremium, 2)), 2) < 2.00) {
					Assertions.passTest("Cancel Policy Page",
							"Calculated RNL Short-Rated Premium is " + d_calcShortRatedEarnedPremium);
					Assertions.passTest("Cancel Policy Page",
							"Actual RNL Short-Rated Premium is " + d_originalShortRatedEarnedPremiumValue);
				} else {
					Assertions.passTest("Cancel Policy Page",
							"Calculated RNL Short-Rated Premium is " + d_calcShortRatedEarnedPremium);
					Assertions.passTest("Cancel Policy Page",
							"Actual RNL Short-Rated Premium is " + d_originalShortRatedEarnedPremiumValue);
					Assertions.verify(d_originalShortRatedEarnedPremiumValue, d_calcShortRatedEarnedPremium,
							"Cancel Policy Page",
							"The difference between Actual and calculated RNL Short-Rated Earned Premium Value is more than 2.00",
							false, false);
				}

				// Comparing Actual and calculated Pro Rated Returned Premium Value
				if (Precision.round(Math.abs(Precision.round(d_originalShortRatedReturnedPremiumValue, 2)
						- Precision.round(d_calcShortRatedReturnedPremium, 2)), 2) < 2.00) {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Short-Rated Returned Premium is " + d_calcShortRatedReturnedPremium);
					Assertions.passTest("Cancel Policy Page",
							"Actual Short-Rated Returned Premium is " + d_originalShortRatedReturnedPremiumValue);
				} else {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Short-Rated Returned Premium is " + d_calcShortRatedReturnedPremium);
					Assertions.passTest("Cancel Policy Page",
							"Actual Short-Rated Returned Premium is " + d_originalShortRatedReturnedPremiumValue);
					Assertions.verify(d_originalShortRatedReturnedPremiumValue, d_calcShortRatedReturnedPremium,
							"Cancel Policy Page",
							"The difference between Actual and calculated Short-Rated Returned Premium Value is more than 2.00",
							false, false);
				}

				// Pro-rated premium value
				proRatedDataRnl = cancelPolicyPage.prorataFactor.formatDynamicPath(1).getData();
				proRatedValueRnl = Double.parseDouble(proRatedDataRnl);

				// Short_rated value
				shortRatedDataRnl = cancelPolicyPage.prorataFactor.formatDynamicPath(2).getData();
				shortRatedValueRnl = Double.parseDouble(shortRatedDataRnl);

				// Calculating pro rata factor for short-rated pro rata factor
				// formula(90% of
				// Pro-rated pro rata factor(.97))
				// 90%=90/100=0.9*0.97=0.873
				testData = data.get(data_Value1);
				calShort_RatedProrataFactor = 0.9 * proRatedValueRnl;
				double d_calShortRatedProrataFactor = Precision.round(calShort_RatedProrataFactor, 3);

				// Calculating Original Premium Premium
				originalPremiumDataRnl = cancelPolicyPage.premiumValue.formatDynamicPath(2).getData().replace("$", "");
				originalPremiumValueRnl = Double.parseDouble(originalPremiumDataRnl);

				// Calculating Earned Premium
				calEarned_PremiumRnl = originalPremiumValueRnl * (1 - shortRatedValueRnl);
				d_calEarned_PremiumRnl = (int) Math.round(calEarned_PremiumRnl);

				// Calculating Earned Premium
				calReturned_PremiumRnl = originalPremiumValueRnl * shortRatedValueRnl;
				d_calReturned_PremiumRnl = (int) Math.round(calReturned_PremiumRnl);

				// Getting Actual short-rated prorata factor
				actualShortratedProrataFactor = cancelPolicyPage.prorataFactor.formatDynamicPath(2).getData();
				d_actualShortratedProrataFactor = Double.parseDouble(actualShortratedProrataFactor);

				// Getting Actual Earned Premium Data
				actualEarnedPremiumRnl = cancelPolicyPage.premiumValue.formatDynamicPath(3).getData().replace("$", "");
				d_actualEarnedPremiumRnl = Integer.parseInt(actualEarnedPremiumRnl);

				// Getting Actual Returned Premium Data
				actualReturnedPremiumRnl = cancelPolicyPage.premiumValue.formatDynamicPath(4).getData().replace("$", "")
						.replace("-", "");
				d_actualReturnedPremiumRnl = Integer.parseInt(actualReturnedPremiumRnl);

				// Comparing Actual and calculated values for short-rated prorata
				// factor
				if (Precision.round(Math.abs(Precision.round(d_actualShortratedProrataFactor, 2)
						- Precision.round(d_calShortRatedProrataFactor, 2)), 2) < 2.00) {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Short-Rated Prorata Factor is " + d_calShortRatedProrataFactor);
					Assertions.passTest("Cancel Policy Page",
							"Actual Short Rated Pro-Rata Factor is " + d_actualShortratedProrataFactor);
					Assertions.verify(d_actualShortratedProrataFactor, d_calShortRatedProrataFactor,
							"Cancel Policy Page",
							"Actual Shortrated Prorata Factor and calculated Shortrated Prorata Factor both are equal",
							false, false);
				} else {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Short-Rated Prorata Factor is " + d_calShortRatedProrataFactor);
					Assertions.passTest("Cancel Policy Page",
							"Actual Short Rated Pro-Rata Factor is " + d_actualShortratedProrataFactor);
					Assertions.passTest("Cancel Policy Page",
							"The Difference between actual and calculated Short Rated Pro-Rata Factor more than 2.00");
				}

				// Comparing Actual and calculated values for Earned Premium Data
				if (Precision.round(Math
						.abs(Precision.round(d_actualEarnedPremiumRnl, 2) - Precision.round(d_calEarned_PremiumRnl, 2)),
						2) < 5.00) {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Earned Premium : " + "$" + d_calEarned_PremiumRnl);
					Assertions.passTest("Cancel Policy Page",
							"Actual Actual Earned Pemium : " + "$" + d_actualEarnedPremiumRnl);
				} else {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Earned Premium : " + "$" + d_calEarned_PremiumRnl);
					Assertions.passTest("Cancel Policy Page",
							"Actual Actual Earned Pemium : " + "$" + d_actualEarnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page",
							"The Difference between actual and calculated earned premium is more than 5.00");
				}

				// comparing Actual and calculated values for Returned Premium Data
				if (Precision.round(Math.abs(
						Precision.round(d_actualReturnedPremiumRnl, 2) - Precision.round(d_calReturned_PremiumRnl, 2)),
						2) < 5.00) {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Returned Premium : " + "$" + d_calReturned_PremiumRnl);
					Assertions.passTest("Cancel Policy Page",
							"Actual Actual Returned Pemium : " + "$" + d_actualReturnedPremiumRnl);
				} else {
					Assertions.passTest("Cancel Policy Page",
							"Calculated Renewal Returned Premium is $" + d_calReturned_PremiumRnl);
					Assertions.passTest("Cancel Policy Page",
							"Actual Actual Returned Pemium : " + "$" + d_actualReturnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page",
							"The Difference between actual and calculated earned premium is more than 5.00");
				}

				cancelPolicyPage.previousButton.waitTillPresenceOfElement(60);
				cancelPolicyPage.previousButton.scrollToElement();
				cancelPolicyPage.previousButton.click();
				Assertions.passTest("Cancel Policy page", "Navigated to Cancel policy page");
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on cancel Button
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();
			Assertions.passTest("Cancel Policy page", "Clicked on cancel button");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC046 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC046 ", "Executed Successfully");
			}
		}
	}
}
