/** Program Description:  Check for Default Cancellation Type and the Short Rated Pro-rata factor values, Earned Premium, Returned Premium and Total Values chosen
For various Cancellation Reasons and for various Time Period in which the Policy is in effect for Wind State Alabama
 *  Author			   : Sowndarya
 *  Date of Creation   : 09/03/2022
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
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC037 extends AbstractCommercialTest {

	public TC037() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID037.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();

		// Initializing variables
		String quoteNumber;
		int data_Value1 = 0;
		int data_Value7 = 7;
		Map<String, String> testData = data.get(data_Value1);
		String policyNumber;
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
			Assertions.passTest("Home Page", "Quote is searched successfully");

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
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on Cancel Policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

			// entering details in cancellation page
			Assertions.verify(cancelPolicyPage.daysBeforeNOC.checkIfElementIsDisplayed(), true, "Cancel policy Page",
					"Cancel policy page loaded successfully", false, false);

			// click on cancel reason arrow
			Assertions.addInfo("Scenario 01",
					"Verifying the actual and calculated earned premiums and returned premiums for NB Policy when Cancellation Option chosen by default is Pro-rated");
			for (int i = 0; i <= 2; i++) {
				int dataValuei = i;
				Map<String, String> testDatai = data.get(dataValuei);
				cancelPolicyPage.cancelReasonArrow.scrollToElement();
				cancelPolicyPage.cancelReasonArrow.click();
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testDatai.get("CancellationReason")).click();
				Assertions.passTest("Cancel Policy Page",
						"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());

				// enter cancellation effective date
				if (!testDatai.get("CancellationEffectiveDate").equals("")) {
					cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
					cancelPolicyPage.cancellationEffectiveDate.setData(testDatai.get("CancellationEffectiveDate"));
					cancelPolicyPage.cancellationEffectiveDate.tab();
				}

				// enter days before noc
				if (!testDatai.get("Cancellation_DaysBeforeNOC").equals("")) {
					cancelPolicyPage.daysBeforeNOC.scrollToElement();
					cancelPolicyPage.daysBeforeNOC.setData(testData.get("Cancellation_DaysBeforeNOC"));
				}

				// enter legal notice wording
				if (!testDatai.get("Cancellation_LegalNoticeWording").equals("")) {
					cancelPolicyPage.legalNoticeWording.scrollToElement();
					cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
				}

				// click on next button
				cancelPolicyPage.nextButton.scrollToElement();
				cancelPolicyPage.nextButton.click();

				// Verify the Short Rated,Earned Premium,Returned Premium
				// Verify the default selected option
				Assertions.verify(cancelPolicyPage.proRatedRadioBtn.checkIfElementIsSelected(), true,
						"Cancel Policy Page", "Pro-Rated Option selected by default is verified", false, false);
				cancelPolicyPage.shortRatedRadioBtn.scrollToElement();
				cancelPolicyPage.shortRatedRadioBtn.click();
				cancelPolicyPage.waitTime(2);// need wait time to load the page

				// verify the short rated value
				Assertions.verify(
						cancelPolicyPage.prorataFactor.formatDynamicPath("2").getData()
								.contains(testDatai.get("ProrataFactor")),
						true, "Cancel Policy Page", "Short Rated Pro-Rata Factor is "
								+ cancelPolicyPage.prorataFactor.formatDynamicPath("2").getData(),
						false, false);

				// calculate earned premium
				String originalPremium = cancelPolicyPage.premiumValue.formatDynamicPath("2").getData().replace("$",
						"");
				double d_originalPremium = Double.parseDouble(originalPremium);

				// Earned Premium = Original Premium * (1-ProrataFactor)
				String prorataValue = testDatai.get("ProrataFactor");
				double d_prorataValue = Double.parseDouble(prorataValue);
				double d_calcprorataValue = 1 - d_prorataValue;
				double calcEarnedPremium = d_originalPremium * d_calcprorataValue;

				// getting actual Earned premium value
				String actualEarnedPremium = cancelPolicyPage.premiumValue.formatDynamicPath("3").getData().replace("$",
						"");
				double d_actualEarnedPremium = Double.parseDouble(actualEarnedPremium);

				// Verify the actual and calculated earned premium
				if (Precision.round(
						Math.abs(Precision.round(d_actualEarnedPremium, 2) - Precision.round(calcEarnedPremium, 2)),
						2) < 4.00) {
					Assertions.passTest("Cancel Policy Page", "Actual Earned Premium " + d_actualEarnedPremium);
					Assertions.passTest("Cancel Policy Page", "Calculated Earned Premium " + calcEarnedPremium);
					Assertions.passTest("Cancel Policy Page", "Actual and Calculated Earned Premiums are equal");

				} else {

					Assertions.verify(d_actualEarnedPremium, calcEarnedPremium, "Cancel Policy Page",
							"The Difference between actual and calculated earned premium is more than 4.00", false,
							false);

				}

				// calculate Returned premium
				double calcReturnedPremium = d_originalPremium * d_prorataValue;

				// getting actual returned premium
				String actualReturnedPremium = cancelPolicyPage.premiumValue.formatDynamicPath("4").getData()
						.replace("$", "").replace("-", "");
				double d_actualReturnedPremium = Double.parseDouble(actualReturnedPremium);

				// Verify the actual and calculated returned premium
				if (Precision.round(
						Math.abs(Precision.round(d_actualReturnedPremium, 2) - Precision.round(calcReturnedPremium, 2)),
						2) < 4.00) {
					Assertions.passTest("Cancel Policy Page", "Actual returned Premium " + d_actualReturnedPremium);
					Assertions.passTest("Cancel Policy Page", "Calculated returned Premium " + calcReturnedPremium);
					Assertions.passTest("Cancel Policy Page", "Actual and Calculated Returned Premiums are equal");
				} else {
					Assertions.verify(d_actualReturnedPremium, calcReturnedPremium, "Cancel Policy Page",
							"The Difference between actual and calculated returned premium is more than 4.00", false,
							false);

				}

				// click on previous
				cancelPolicyPage.previousButton.scrollToElement();
				cancelPolicyPage.previousButton.click();
				cancelPolicyPage.refreshPage();
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on cancel button
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();

			// Click on Cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

			// entering details in cancellation page
			Assertions.verify(cancelPolicyPage.daysBeforeNOC.checkIfElementIsDisplayed(), true, "Cancel policy Page",
					"Cancel policy page loaded successfully", false, false);

			// click on cancel reason arrow
			Assertions.addInfo("Scenario 02",
					"Verifying the actual and calculated earned premiums and returned premiums for NB Policy when Cancellation Option chosen by default is Short-rated");
			for (int j = 3; j <= 6; j++) {
				int dataValuej = j;
				Map<String, String> testDataj = data.get(dataValuej);
				cancelPolicyPage.cancelReasonArrow.scrollToElement();
				cancelPolicyPage.cancelReasonArrow.click();
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testDataj.get("CancellationReason")).click();
				Assertions.passTest("Cancel Policy Page",
						"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());

				// enter cancellation effective date
				if (!testDataj.get("CancellationEffectiveDate").equals("")) {
					cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
					cancelPolicyPage.cancellationEffectiveDate.setData(testDataj.get("CancellationEffectiveDate"));
					cancelPolicyPage.cancellationEffectiveDate.tab();
				}

				// enter days before noc
				if (!testDataj.get("Cancellation_DaysBeforeNOC").equals("")) {
					cancelPolicyPage.daysBeforeNOC.scrollToElement();
					cancelPolicyPage.daysBeforeNOC.setData(testDataj.get("Cancellation_DaysBeforeNOC"));
				}

				// enter legal notice wording
				if (!testDataj.get("Cancellation_LegalNoticeWording").equals("")) {
					cancelPolicyPage.legalNoticeWording.scrollToElement();
					cancelPolicyPage.legalNoticeWording.setData(testDataj.get("Cancellation_LegalNoticeWording"));
				}

				// click on next button
				cancelPolicyPage.nextButton.scrollToElement();
				cancelPolicyPage.nextButton.click();

				// Verify the Short Rated,Earned Premium,Returned Premium
				// Verify the default selected option
				Assertions.verify(cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsSelected(), true,
						"Cancel Policy Page", "Short-Rated Option selected by default is verified", false, false);

				// verify the short rated value
				Assertions.verify(
						cancelPolicyPage.prorataFactor.formatDynamicPath("2").getData()
								.contains(testDataj.get("ProrataFactor")),
						true, "Cancel Policy Page", "Short Rated Pro-Rata Factor is "
								+ cancelPolicyPage.prorataFactor.formatDynamicPath("2").getData(),
						false, false);

				// calculate earned premium
				String originalPremium = cancelPolicyPage.premiumValue.formatDynamicPath("2").getData().replace("$",
						"");
				double d_originalPremium = Double.parseDouble(originalPremium);

				// Earned Premium = Original Premium * (1-ProrataFactor)
				String prorataValue = testDataj.get("ProrataFactor");
				double d_prorataValue = Double.parseDouble(prorataValue);
				double d_calcprorataValue = 1 - d_prorataValue;
				double calcEarnedPremium = d_originalPremium * d_calcprorataValue;

				// getting actual Earned premium value
				String actualEarnedPremium = cancelPolicyPage.premiumValue.formatDynamicPath("3").getData().replace("$",
						"");
				double d_actualEarnedPremium = Double.parseDouble(actualEarnedPremium);

				// Verify the actual and calculated earned premium
				if (Precision.round(
						Math.abs(Precision.round(d_actualEarnedPremium, 2) - Precision.round(calcEarnedPremium, 2)),
						2) < 4.00) {
					Assertions.passTest("Cancel Policy Page", "Actual Earned Premium " + d_actualEarnedPremium);
					Assertions.passTest("Cancel Policy Page", "Calculated Earned Premium " + calcEarnedPremium);
					Assertions.passTest("Cancel Policy Page", "Actual and Calculated Earned Premiums are equal");
				} else {
					Assertions.verify(d_actualEarnedPremium, calcEarnedPremium, "Cancel Policy Page",
							"The Difference between actual and calculated earned premium is more than 4.00", false,
							false);

				}

				// calculate Returned premium
				double calcReturnedPremium = d_originalPremium * d_prorataValue;

				// getting actual returned premium
				String actualReturnedPremium = cancelPolicyPage.premiumValue.formatDynamicPath("4").getData()
						.replace("$", "").replace("-", "");
				double d_actualReturnedPremium = Double.parseDouble(actualReturnedPremium);

				// Verify the actual and calculated returned premium
				if (Precision.round(
						Math.abs(Precision.round(d_actualReturnedPremium, 2) - Precision.round(calcReturnedPremium, 2)),
						2) < 4.00) {
					Assertions.passTest("Cancel Policy Page", "Actual returned Premium " + d_actualReturnedPremium);
					Assertions.passTest("Cancel Policy Page", "Calculated returned Premium " + calcReturnedPremium);
					Assertions.passTest("Cancel Policy Page", "Actual and Calculated Returned Premiums are equal");
				} else {
					Assertions.verify(d_actualReturnedPremium, calcReturnedPremium, "Cancel Policy Page",
							"The Difference between actual and calculated returned premium is more than 4.00", false,
							false);

				}

				// click on previous
				cancelPolicyPage.previousButton.scrollToElement();
				cancelPolicyPage.previousButton.click();
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// click on cancel button
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();

			// Click on Cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

			// entering details in cancellation page
			Assertions.verify(cancelPolicyPage.daysBeforeNOC.checkIfElementIsDisplayed(), true, "Cancel policy Page",
					"Cancel policy page loaded successfully", false, false);

			// click on cancel reason arrow
			testData = data.get(data_Value7);
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());

			// enter cancellation effective date
			if (!testData.get("CancellationEffectiveDate").equals("")) {
				cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
				cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
				cancelPolicyPage.cancellationEffectiveDate.tab();
			}

			// enter days before noc
			if (!testData.get("Cancellation_DaysBeforeNOC").equals("")) {
				cancelPolicyPage.daysBeforeNOC.scrollToElement();
				cancelPolicyPage.daysBeforeNOC.setData(testData.get("Cancellation_DaysBeforeNOC"));
			}

			// enter legal notice wording
			if (!testData.get("Cancellation_LegalNoticeWording").equals("")) {
				cancelPolicyPage.legalNoticeWording.scrollToElement();
				cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			}

			// click on next button
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Verify the Short Rated,Earned Premium,Returned Premium
			// Verify the default selected option
			Assertions.verify(cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsSelected(), true,
					"Cancel Policy Page", "Short-Rated Option selected by default is verified", false, false);

			// calculate short rated prorata factor value
			String proRatedProrataFactor = cancelPolicyPage.prorataFactor.formatDynamicPath("1").getData();
			double d_proRatedProrataFactor = Double.parseDouble(proRatedProrataFactor);

			double calcShortRatedProrataFactor = d_proRatedProrataFactor
					* Double.parseDouble(testData.get("ProrataFactor"));

			// verify the short rated value
			Assertions.addInfo("Scenario 03",
					"Verifying the Short rated Prorata factor,earned and returned premiums when the reason is Insured's Request and cancellation effective date is May 31, 2022");
			String actualShortratedProrataFactor = cancelPolicyPage.prorataFactor.formatDynamicPath("2").getData();
			double d_actualShortratedProrataFactor = Double.parseDouble(actualShortratedProrataFactor);
			if (Precision.round(Math.abs(Precision.round(d_actualShortratedProrataFactor, 2)
					- Precision.round(calcShortRatedProrataFactor, 2)), 2) < 1.00) {
				Assertions.passTest("Cancel Policy Page",
						"Actual Short Rated Pro-Rata Factor is " + d_actualShortratedProrataFactor);
				Assertions.passTest("Cancel Policy Page",
						"Calculated Short Rated Pro-Rata Factor is " + calcShortRatedProrataFactor);
			} else {
				Assertions.verify(d_actualShortratedProrataFactor, calcShortRatedProrataFactor, "Cancel Policy Page",
						"The Difference between actual and calculated Short Rated Pro-Rata Factor more than 1.00",
						false, false);

			}

			// calculate earned premium
			String originalPremium = cancelPolicyPage.premiumValue.formatDynamicPath("2").getData().replace("$", "");
			double d_originalPremium = Double.parseDouble(originalPremium);

			// Earned Premium = Original Premium * (1-ProrataFactor)
			double d_calcprorataValue = 1 - d_actualShortratedProrataFactor;
			double calcEarnedPremium = d_originalPremium * d_calcprorataValue;

			// getting actual Earned premium value
			String actualEarnedPremium = cancelPolicyPage.premiumValue.formatDynamicPath("3").getData().replace("$",
					"");
			double d_actualEarnedPremium = Double.parseDouble(actualEarnedPremium);

			// Verify the actual and calculated earned premium
			if (Precision.round(
					Math.abs(Precision.round(d_actualEarnedPremium, 2) - Precision.round(calcEarnedPremium, 2)),
					2) < 4.00) {
				Assertions.passTest("Cancel Policy Page", "Actual Earned Premium " + d_actualEarnedPremium);
				Assertions.passTest("Cancel Policy Page", "Calculated Earned Premium " + calcEarnedPremium);
				Assertions.passTest("Cancel Policy Page", "Actual and Calculated Earned Premiums are equal");
			} else {
				Assertions.verify(d_actualEarnedPremium, calcEarnedPremium, "Cancel Policy Page",
						"The Difference between actual and calculated Earned premium is more than 4.00", false, false);

			}

			// calculate Returned premium
			double calcReturnedPremium = d_originalPremium * d_actualShortratedProrataFactor;

			// getting actual returned premium
			String actualReturnedPremium = cancelPolicyPage.premiumValue.formatDynamicPath("4").getData()
					.replace("$", "").replace("-", "");
			double d_actualReturnedPremium = Double.parseDouble(actualReturnedPremium);

			// Verify the actual and calculated returned premium
			if (Precision.round(
					Math.abs(Precision.round(d_actualReturnedPremium, 2) - Precision.round(calcReturnedPremium, 2)),
					2) < 4.00) {
				Assertions.passTest("Cancel Policy Page", "Actual returned Premium " + d_actualReturnedPremium);
				Assertions.passTest("Cancel Policy Page", "Calculated returned Premium " + calcReturnedPremium);
				Assertions.passTest("Cancel Policy Page", "Actual and Calculated Returned Premiums are equal");
			} else {
				Assertions.verify(d_actualReturnedPremium, calcReturnedPremium, "Cancel Policy Page",
						"The Difference between actual and calculated returned premium is more than 4.00", false,
						false);

			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// click on cancel button
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();

			// Click on Renewal Link
			testData = data.get(data_Value1);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// click on add expacc button
			policyRenewalPage.addExpaccButton.scrollToElement();
			policyRenewalPage.addExpaccButton.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("Expacc Info Page", "Entered Expacc deatils successfully");

			// clicking on renewal policy link
			policySummaryPage.renewPolicy.waitTillPresenceOfElement(60);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew policy link");

			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// Click on yes button
			if (accountOverviewPage.yesButton.checkIfElementIsPresent()
					&& accountOverviewPage.yesButton.checkIfElementIsDisplayed()) {
				accountOverviewPage.yesButton.scrollToElement();
				accountOverviewPage.yesButton.click();
			}

			// getting renewal quote number
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

			// click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer button");

			// Click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.renewalRequestBind(testData);

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Home Page", "Searched Submitted quote " + quoteNumber + " successfullly");

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Renewal Policy Number is : " + policyNumber);

			// Click on Cancel Policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

			// entering details in cancellation page
			Assertions.verify(cancelPolicyPage.daysBeforeNOC.checkIfElementIsDisplayed(), true, "Cancel policy Page",
					"Cancel policy page loaded successfully", false, false);

			// click on cancel reason arrow
			Assertions.addInfo("Scenario 04",
					"Verifying the actual and calculated earned premiums and returned premiums for Renewal Policy when Cancellation Option chosen by default is Pro-rated");
			for (int i = 0; i <= 2; i++) {
				int dataValuei = i;
				Map<String, String> testDatai = data.get(dataValuei);
				cancelPolicyPage.cancelReasonArrow.scrollToElement();
				cancelPolicyPage.cancelReasonArrow.click();
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testDatai.get("CancellationReason")).click();
				Assertions.passTest("Cancel Policy Page",
						"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());

				// enter cancellation effective date
				if (!testDatai.get("CalcCancellationEffectiveDate").equals("")) {
					cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
					cancelPolicyPage.cancellationEffectiveDate.setData(testDatai.get("CalcCancellationEffectiveDate"));
					cancelPolicyPage.cancellationEffectiveDate.tab();
				}

				// enter days before noc
				if (!testDatai.get("Cancellation_DaysBeforeNOC").equals("")) {
					cancelPolicyPage.daysBeforeNOC.scrollToElement();
					cancelPolicyPage.daysBeforeNOC.setData(testDatai.get("Cancellation_DaysBeforeNOC"));
				}

				// enter legal notice wording
				if (!testDatai.get("Cancellation_LegalNoticeWording").equals("")) {
					cancelPolicyPage.legalNoticeWording.scrollToElement();
					cancelPolicyPage.legalNoticeWording.setData(testDatai.get("Cancellation_LegalNoticeWording"));
				}

				// click on next button
				cancelPolicyPage.nextButton.scrollToElement();
				cancelPolicyPage.nextButton.click();

				// Verify the Short Rated,Earned Premium,Returned Premium
				// Verify the default selected option
				Assertions.verify(cancelPolicyPage.proRatedRadioBtn.checkIfElementIsSelected(), true,
						"Cancel Policy Page", "Pro-Rated Option selected by default is verified", false, false);
				cancelPolicyPage.shortRatedRadioBtn.scrollToElement();
				cancelPolicyPage.shortRatedRadioBtn.click();
				cancelPolicyPage.waitTime(2);// need wait time to load the page

				// verify the short rated value
				Assertions.verify(
						cancelPolicyPage.prorataFactor.formatDynamicPath("2").getData()
								.contains(testDatai.get("ProrataFactor")),
						true, "Cancel Policy Page", "Short Rated Pro-Rata Factor is "
								+ cancelPolicyPage.prorataFactor.formatDynamicPath("2").getData(),
						false, false);

				// calculate earned premium
				String originalPremiumRnl = cancelPolicyPage.premiumValue.formatDynamicPath("2").getData().replace("$",
						"");
				double d_originalPremiumRnl = Double.parseDouble(originalPremiumRnl);

				// Earned Premium = Original Premium * (1-ProrataFactor)
				String prorataValue = testDatai.get("ProrataFactor");
				double d_prorataValue = Double.parseDouble(prorataValue);
				double d_calcprorataValueRnl = 1 - d_prorataValue;
				double calcEarnedPremiumRnl = d_originalPremiumRnl * d_calcprorataValueRnl;

				// getting actual Earned premium value
				String actualEarnedPremiumRnl = cancelPolicyPage.premiumValue.formatDynamicPath("3").getData()
						.replace("$", "");
				double d_actualEarnedPremiumRnl = Double.parseDouble(actualEarnedPremiumRnl);

				// Verify the actual and calculated earned premium
				if (Precision.round(Math
						.abs(Precision.round(d_actualEarnedPremiumRnl, 2) - Precision.round(calcEarnedPremiumRnl, 2)),
						2) < 4.00) {
					Assertions.passTest("Cancel Policy Page", "Actual Earned Premium " + d_actualEarnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page", "Calculated Earned Premium " + calcEarnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page", "Actual and Calculated Earned Premiums are equal");
				} else {
					Assertions.verify(d_actualEarnedPremiumRnl, calcEarnedPremiumRnl, "Cancel Policy Page",
							"The Difference between actual and calculated Earned premium is more than 4.00", false,
							false);
					Assertions.passTest("Cancel Policy Page", "Actual Earned Premium " + d_actualEarnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page", "Calculated Earned Premium " + calcEarnedPremiumRnl);

				}

				// calculate Returned premium
				double calcReturnedPremiumRnl = d_originalPremiumRnl * d_prorataValue;

				// getting actual returned premium
				String actualReturnedPremiumRnl = cancelPolicyPage.premiumValue.formatDynamicPath("4").getData()
						.replace("$", "").replace("-", "");
				double d_actualReturnedPremiumRnl = Double.parseDouble(actualReturnedPremiumRnl);

				// Verify the actual and calculated returned premium
				if (Precision.round(Math.abs(
						Precision.round(d_actualReturnedPremiumRnl, 2) - Precision.round(calcReturnedPremiumRnl, 2)),
						2) < 4.00) {
					Assertions.passTest("Cancel Policy Page", "Actual returned Premium " + d_actualReturnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page", "Calculated returned Premium " + calcReturnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page", "Actual and Calculated Returned Premiums are equal");
				} else {
					Assertions.verify(d_actualReturnedPremiumRnl, calcReturnedPremiumRnl, "Cancel Policy Page",
							"The Difference between actual and calculated returned premium is more than 4.00", false,
							false);
					Assertions.passTest("Cancel Policy Page", "Actual returned Premium " + d_actualReturnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page", "Calculated returned Premium " + calcReturnedPremiumRnl);

				}

				// click on previous
				cancelPolicyPage.previousButton.scrollToElement();
				cancelPolicyPage.previousButton.click();
				cancelPolicyPage.refreshPage();
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// click on cancel button
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();

			// Click on Cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

			// entering details in cancellation page
			Assertions.verify(cancelPolicyPage.daysBeforeNOC.checkIfElementIsDisplayed(), true, "Cancel policy Page",
					"Cancel policy page loaded successfully", false, false);

			// click on cancel reason arrow
			Assertions.addInfo("Scenario 05",
					"Verifying the actual and calculated earned premiums and returned premiums for NB Policy when Cancellation Option chosen by default is Short-rated");
			for (int j = 3; j <= 6; j++) {
				int dataValuej = j;
				Map<String, String> testDataj = data.get(dataValuej);
				cancelPolicyPage.cancelReasonArrow.scrollToElement();
				cancelPolicyPage.cancelReasonArrow.click();
				cancelPolicyPage.cancelReasonOption.formatDynamicPath(testDataj.get("CancellationReason")).click();
				Assertions.passTest("Cancel Policy Page",
						"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());

				// enter cancellation effective date
				if (!testDataj.get("CalcCancellationEffectiveDate").equals("")) {
					cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
					cancelPolicyPage.cancellationEffectiveDate.setData(testDataj.get("CalcCancellationEffectiveDate"));
					cancelPolicyPage.cancellationEffectiveDate.tab();
				}

				// enter days before noc
				if (!testDataj.get("Cancellation_DaysBeforeNOC").equals("")) {
					cancelPolicyPage.daysBeforeNOC.scrollToElement();
					cancelPolicyPage.daysBeforeNOC.setData(testDataj.get("Cancellation_DaysBeforeNOC"));
				}

				// enter legal notice wording
				if (!testDataj.get("Cancellation_LegalNoticeWording").equals("")) {
					cancelPolicyPage.legalNoticeWording.scrollToElement();
					cancelPolicyPage.legalNoticeWording.setData(testDataj.get("Cancellation_LegalNoticeWording"));
				}

				// click on next button
				cancelPolicyPage.nextButton.scrollToElement();
				cancelPolicyPage.nextButton.click();

				// Verify the Short Rated,Earned Premium,Returned Premium
				// Verify the default selected option
				Assertions.verify(cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsSelected(), true,
						"Cancel Policy Page", "Short-Rated Option selected by default is verified", false, false);

				// verify the short rated value
				Assertions.verify(
						cancelPolicyPage.prorataFactor.formatDynamicPath("2").getData()
								.contains(testDataj.get("ProrataFactor")),
						true, "Cancel Policy Page", "Short Rated Pro-Rata Factor is "
								+ cancelPolicyPage.prorataFactor.formatDynamicPath("2").getData(),
						false, false);

				// calculate earned premium
				String originalPremiumRnl = cancelPolicyPage.premiumValue.formatDynamicPath("2").getData().replace("$",
						"");
				double d_originalPremiumRnl = Double.parseDouble(originalPremiumRnl);

				// Earned Premium = Original Premium * (1-ProrataFactor)
				String prorataValue = testDataj.get("ProrataFactor");
				double d_prorataValue = Double.parseDouble(prorataValue);
				double d_calcprorataValueRnl = 1 - d_prorataValue;
				double calcEarnedPremiumRnl = d_originalPremiumRnl * d_calcprorataValueRnl;

				// getting actual Earned premium value
				String actualEarnedPremiumRnl = cancelPolicyPage.premiumValue.formatDynamicPath("3").getData()
						.replace("$", "");
				double d_actualEarnedPremiumRnl = Double.parseDouble(actualEarnedPremiumRnl);

				// Verify the actual and calculated earned premium
				Assertions.addInfo("Scenario 02", "Verifying the actual and calculated earned premiums");
				if (Precision.round(Math
						.abs(Precision.round(d_actualEarnedPremiumRnl, 2) - Precision.round(calcEarnedPremiumRnl, 2)),
						2) < 4.00) {
					Assertions.passTest("Cancel Policy Page", "Actual Earned Premium " + d_actualEarnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page", "Calculated Earned Premium " + calcEarnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page", "Actual and Calculated Earned Premiums are equal");
				} else {
					Assertions.verify(d_actualEarnedPremiumRnl, calcEarnedPremiumRnl, "Cancel Policy Page",
							"The Difference between actual and calculated Earned premium is more than 4.00", false,
							false);
					Assertions.passTest("Cancel Policy Page", "Actual Earned Premium " + d_actualEarnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page", "Calculated Earned Premium " + calcEarnedPremiumRnl);

				}

				// calculate Returned premium
				double calcReturnedPremiumRnl = d_originalPremiumRnl * d_prorataValue;

				// getting actual returned premium
				String actualReturnedPremiumRnl = cancelPolicyPage.premiumValue.formatDynamicPath("4").getData()
						.replace("$", "").replace("-", "");
				double d_actualReturnedPremiumRnl = Double.parseDouble(actualReturnedPremiumRnl);

				// Verify the actual and calculated returned premium
				if (Precision.round(Math.abs(
						Precision.round(d_actualReturnedPremiumRnl, 2) - Precision.round(calcReturnedPremiumRnl, 2)),
						2) < 4.00) {
					Assertions.passTest("Cancel Policy Page", "Actual returned Premium " + d_actualReturnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page", "Calculated returned Premium " + calcReturnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page", "Actual and Calculated Returned Premiums are equal");
				} else {
					Assertions.verify(d_actualReturnedPremiumRnl, calcReturnedPremiumRnl, "Cancel Policy Page",
							"The Difference between actual and calculated returned premium is more than 4.00", false,
							false);
					Assertions.passTest("Cancel Policy Page", "Actual returned Premium " + d_actualReturnedPremiumRnl);
					Assertions.passTest("Cancel Policy Page", "Calculated returned Premium " + calcReturnedPremiumRnl);

				}

				// click on previous
				cancelPolicyPage.previousButton.scrollToElement();
				cancelPolicyPage.previousButton.click();
			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// click on cancel button
			cancelPolicyPage.cancelButton.scrollToElement();
			cancelPolicyPage.cancelButton.click();

			// Click on Cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");

			// entering details in cancellation page
			Assertions.verify(cancelPolicyPage.daysBeforeNOC.checkIfElementIsDisplayed(), true, "Cancel policy Page",
					"Cancel policy page loaded successfully", false, false);

			// click on cancel reason arrow
			testData = data.get(data_Value7);
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());

			// enter cancellation effective date
			if (!testData.get("CalcCancellationEffectiveDate").equals("")) {
				cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
				cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("" + "CalcCancellationEffectiveDate"));
				cancelPolicyPage.cancellationEffectiveDate.tab();
				Assertions.passTest("Cancel Policy Page", "The Cancellation effective date entered is "
						+ cancelPolicyPage.cancellationEffectiveDate.getData());
			}

			// enter days before noc
			if (!testData.get("Cancellation_DaysBeforeNOC").equals("")) {
				cancelPolicyPage.daysBeforeNOC.scrollToElement();
				cancelPolicyPage.daysBeforeNOC.setData(testData.get("Cancellation_DaysBeforeNOC"));
			}

			// enter legal notice wording
			if (!testData.get("Cancellation_LegalNoticeWording").equals("")) {
				cancelPolicyPage.legalNoticeWording.scrollToElement();
				cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			}

			// click on next button
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Verify the Short Rated,Earned Premium,Returned Premium
			// Verify the default selected option
			Assertions.verify(cancelPolicyPage.shortRatedRadioBtn.checkIfElementIsSelected(), true,
					"Cancel Policy Page", "Short-Rated Option selected by default is verified", false, false);

			// calculate short rated prorata factor value
			String proRatedProrataFactorRnl = cancelPolicyPage.prorataFactor.formatDynamicPath("1").getData();
			double d_proRatedProrataFactorRnl = Double.parseDouble(proRatedProrataFactorRnl);

			double calcShortRatedProrataFactorRnl = d_proRatedProrataFactorRnl
					* Double.parseDouble(testData.get("ProrataFactor"));

			// verify the short rated value
			Assertions.addInfo("Scenario 06",
					"Verifying the Short rated Prorata factor,earned and returned premiums when the reason is Insured's Request and cancellation effective date is May 31, 2024");
			String actualShortratedProrataFactorRnl = cancelPolicyPage.prorataFactor.formatDynamicPath("2").getData();
			double d_actualShortratedProrataFactorRnl = Double.parseDouble(actualShortratedProrataFactorRnl);
			if (Precision.round(Math.abs(Precision.round(d_actualShortratedProrataFactorRnl, 2)
					- Precision.round(calcShortRatedProrataFactorRnl, 2)), 2) < 1.00) {
				Assertions.passTest("Cancel Policy Page",
						"Actual Short Rated Pro-Rata Factor is " + d_actualShortratedProrataFactorRnl);
				Assertions.passTest("Cancel Policy Page", "Calculated Short Rated Pro-Rata Factor is "
						+ Precision.round(calcShortRatedProrataFactorRnl, 3));
			} else {
				Assertions.verify(d_actualShortratedProrataFactorRnl, calcShortRatedProrataFactorRnl,
						"Cancel Policy Page",
						"The Difference between actual and calculated Short Rated Pro-Rata Factor more than 1.00",
						false, false);
				Assertions.passTest("Cancel Policy Page",
						"Actual Short Rated Pro-Rata Factor is " + d_actualShortratedProrataFactorRnl);
				Assertions.passTest("Cancel Policy Page", "Calculated Short Rated Pro-Rata Factor is "
						+ Precision.round(calcShortRatedProrataFactorRnl, 3));

			}

			// calculate earned premium
			String originalPremiumRnl = cancelPolicyPage.premiumValue.formatDynamicPath("2").getData().replace("$", "");
			double d_originalPremiumRnl = Double.parseDouble(originalPremiumRnl);

			// Earned Premium = Original Premium * (1-ProrataFactor)
			double d_calcprorataValueRnl = 1 - d_actualShortratedProrataFactorRnl;
			double calcEarnedPremiumRnl = d_originalPremiumRnl * d_calcprorataValueRnl;

			// getting actual Earned premium value
			String actualEarnedPremiumRnl = cancelPolicyPage.premiumValue.formatDynamicPath("3").getData().replace("$",
					"");
			double d_actualEarnedPremiumRnl = Double.parseDouble(actualEarnedPremiumRnl);

			// Verify the actual and calculated earned premium
			if (Precision.round(
					Math.abs(Precision.round(d_actualEarnedPremiumRnl, 2) - Precision.round(calcEarnedPremiumRnl, 2)),
					2) < 4.00) {
				Assertions.passTest("Cancel Policy Page", "Actual Earned Premium " + d_actualEarnedPremiumRnl);
				Assertions.passTest("Cancel Policy Page", "Calculated Earned Premium " + calcEarnedPremiumRnl);
				Assertions.passTest("Cancel Policy Page", "Actual and Calculated Earned Premiums are equal");
			} else {
				Assertions.verify(d_actualEarnedPremiumRnl, calcEarnedPremiumRnl, "Cancel Policy Page",
						"The Difference between actual and calculated Earned premium is more than 4.00", false, false);
				Assertions.passTest("Cancel Policy Page", "Actual Earned Premium " + d_actualEarnedPremiumRnl);
				Assertions.passTest("Cancel Policy Page", "Calculated Earned Premium " + calcEarnedPremiumRnl);
			}

			// calculate Returned premium
			double calcReturnedPremiumRnl = d_originalPremiumRnl * d_actualShortratedProrataFactorRnl;

			// getting actual returned premium
			String actualReturnedPremiumRnl = cancelPolicyPage.premiumValue.formatDynamicPath("4").getData()
					.replace("$", "").replace("-", "");
			double d_actualReturnedPremiumRnl = Double.parseDouble(actualReturnedPremiumRnl);

			// Verify the actual and calculated returned premium
			if (Precision.round(Math
					.abs(Precision.round(d_actualReturnedPremiumRnl, 2) - Precision.round(calcReturnedPremiumRnl, 2)),
					2) < 4.00) {
				Assertions.passTest("Cancel Policy Page", "Actual Returned Premium " + d_actualReturnedPremiumRnl);
				Assertions.passTest("Cancel Policy Page", "Calculated Returned Premium " + calcReturnedPremiumRnl);
				Assertions.passTest("Cancel Policy Page", "Actual and Calculated Returned Premiums are equal");
			} else {
				Assertions.verify(d_actualReturnedPremiumRnl, calcReturnedPremiumRnl, "Cancel Policy Page",
						"The Difference between actual and calculated returned premium is more than 4.00", false,
						false);
				Assertions.passTest("Cancel Policy Page", "Actual Returned Premium " + d_actualReturnedPremiumRnl);
				Assertions.passTest("Cancel Policy Page", "Calculated Returned Premium " + calcReturnedPremiumRnl);

			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC037 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC037 ", "Executed Successfully");
			}
		}
	}
}
