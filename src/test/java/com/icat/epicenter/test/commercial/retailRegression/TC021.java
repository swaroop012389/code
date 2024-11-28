/*Description:Check that once a PFC is added to the account, the option wont be available in the dropdown as a type of additional interest and Added CR-19336
Cancel the policy and Reinstate policy to calculate SLTF value on Policy summary page
Author: Pavan Mule
Date :  23/07/2021*/
package com.icat.epicenter.test.commercial.retailRegression;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.icat.epicenter.pom.EditAdditionalInterestInformationPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC021 extends AbstractCommercialTest {

	public TC021() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID021.xls";
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
		EditAdditionalInterestInformationPage editAdditionalInterestInformationPage = new EditAdditionalInterestInformationPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ReinstatePolicyPage reinstatePolicyPage = new ReinstatePolicyPage();

		// Initializing variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value4 = 4;
		double calPro_RatedProratFactor;
		double d_calPro_RatedProratFactor;
		double calShort_RatedProrataFactor = 0;
		String actualProratedProratFactor;
		double d_actualProratedProratFactor;
		String actualShortratedProrataFactor;
		double d_actualShortratedProrataFactor;
		double premiumValueAcc;
		double feeValueAcc;
		double policyFeesAcc;
		double totalActualSltfValueAcc;
		double sltfValueCalculatedAcc;
		double premiumValueAnnual;
		double feeValueAnnual;
		double policyFeesAnnual;
		double totalActualSltfValueAnnual;
		double sltfValueCalculatedAnnual;
		double premiumValueTerm;
		double feeValueTerm;
		double policyFeesTerm;
		double totalActualSltfValueTerm;
		double sltfValueCalculatedTerm;
		double policySurplusContributionValue;

		Map<String, String> testData = data.get(data_Value1);

		// Days Difference between policy effective date and cancellation effective date
		double differenceDays = 12;

		// Number of days in the year
		double totalNoOfDays = 365;
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

			// Click on continue button
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();
			Assertions.passTest("Building Page", "Clicked on Continue Button");

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

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// click on Additional Interests
			accountOverviewPage.editAdditionalIntersets.scrollToElement();
			accountOverviewPage.editAdditionalIntersets.click();
			Assertions.verify(editAdditionalInterestInformationPage.update.checkIfElementIsPresent(), true,
					"Edit Additional Interests Page", "Edit Additional Interests page loaded successfully", false,
					false);

			// adding additional interests with premium finance company(PFC)
			editAdditionalInterestInformationPage.addAdditionalInterest(testData);
			editAdditionalInterestInformationPage.update.waitTillPresenceOfElement(60);
			editAdditionalInterestInformationPage.update.waitTillVisibilityOfElement(60);
			editAdditionalInterestInformationPage.update.scrollToElement();
			editAdditionalInterestInformationPage.update.click();
			Assertions.passTest("Edit Additional Interests Page", "The AI Type Selected is Premium Finance Company");

			// checking PFC available are not in drop down after adding the PFC in
			// additional interests
			accountOverviewPage.waitTime(3);
			accountOverviewPage.editAdditionalIntersets.waitTillPresenceOfElement(60);
			accountOverviewPage.editAdditionalIntersets.waitTillVisibilityOfElement(60);
			accountOverviewPage.editAdditionalIntersets.scrollToElement();
			accountOverviewPage.editAdditionalIntersets.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Additional Interest Icon");

			// Click on Add symbol
			editAdditionalInterestInformationPage.aIAddSymbol.waitTillVisibilityOfElement(60);
			editAdditionalInterestInformationPage.aIAddSymbol.click();
			editAdditionalInterestInformationPage.aITypeArrow.scrollToElement();
			editAdditionalInterestInformationPage.aITypeArrow.click();

			// checking PFC available are not in drop down after adding the PFC in
			// additional interests
			Assertions.addInfo("Scenario 01", "Checking PFC is Not available after adding Additional interest");
			Assertions.verify(
					editAdditionalInterestInformationPage.aITypeOption.formatDynamicPath(0, "Premium Finance Company")
							.checkIfElementIsPresent()
							&& editAdditionalInterestInformationPage.aITypeOption
									.formatDynamicPath(0, "Premium Finance Company").checkIfElementIsDisplayed(),
					false, "Edit Additional Interests Page",
					"Additional Interest Type " + testData.get("1-AIType") + " Not Available is verified", false,
					false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on cancel link
			editAdditionalInterestInformationPage.cancel.click();
			Assertions.passTest("Edit Additional Interests Page", "Clicked on cancel link");

			// Adding Below Code for CR 19336
			// Click on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request bind button successfully");

			// Enter Bind Details
			testData = data.get(data_Value2);
			Assertions.verify(
					requestBindPage.submit.checkIfElementIsPresent()
							&& requestBindPage.submit.checkIfElementIsDisplayed(),
					true, "Request Bind Page", "Request Bind Page loaded successfully", false, false);
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

			// Click on approve link
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Click on Cancel Policy link
			policySummarypage.cancelPolicy.waitTillPresenceOfElement(60);
			policySummarypage.cancelPolicy.scrollToElement();
			policySummarypage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on cancel policy link successfully");

			// Calculating pro rata factor value for Pro-Rated pro rata factor =
			// totalNoOfdays - difference days of policy effective date and cancellation
			// effective date/totalNoOfdays((365-11)/365)=0.97

			calPro_RatedProratFactor = (totalNoOfDays - differenceDays) / totalNoOfDays;
			d_calPro_RatedProratFactor = Precision.round(calPro_RatedProratFactor, 2);
			Assertions.passTest("Cancel Policy Page",
					"Calculated Pro-Rated Prorata Factor is " + d_calPro_RatedProratFactor);

			Assertions.addInfo("Scenario 2", "Verification Prorata for Pro-Rated and Short-Rated Factor");
			testData = data.get(data_Value1);
			String cancellationDate = testData.get("CancellationEffectiveDate");

			// Define the formatter to parse the date in MM/dd/yyyy format
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

			// Parse the future date using the formatter
			LocalDate cancellationEffectiveDate = LocalDate.parse(cancellationDate, formatter);

			// Define the start and end dates for the Wind Season for that year
			LocalDate windSeasonStart = LocalDate.of(cancellationEffectiveDate.getYear(), 6, 1);
			LocalDate windSeasonEnd = LocalDate.of(cancellationEffectiveDate.getYear(), 11, 30);

			// Define the start and end dates for the Other Season, considering the year
			// overlap
			LocalDate otherSeasonStart = LocalDate.of(cancellationEffectiveDate.getYear(), 12, 1);
			LocalDate otherSeasonEnd = LocalDate.of(cancellationEffectiveDate.getYear() + 1, 5, 31); // Wraps to next
																										// year

			// Check if the future date falls in the Wind Season
			if ((cancellationEffectiveDate.isEqual(windSeasonStart)
					|| cancellationEffectiveDate.isAfter(windSeasonStart))
					&& (cancellationEffectiveDate.isEqual(windSeasonEnd)
							|| cancellationEffectiveDate.isBefore(windSeasonEnd))) {
				// 0-183 days Percent of premium earned is 80%
				calShort_RatedProrataFactor = (1 - 0.8);
				Assertions.passTest("Cancel Policy Page",
						"Calculated Short-Rated Prorata Factor is " + calShort_RatedProrataFactor);
			}
			// Check if the future date falls in the Other Season
			else if ((cancellationEffectiveDate.isEqual(otherSeasonStart)
					|| cancellationEffectiveDate.isAfter(otherSeasonStart))
					|| (cancellationEffectiveDate.isEqual(otherSeasonEnd)
							|| cancellationEffectiveDate.isBefore(otherSeasonEnd))) {
				// Calculating pro rata factor for short-rated pro rata factor formula(90% of
				// Pro-rated pro rata factor(.97))
				// 90%=90/100=0.9*0.97=0.873
				calShort_RatedProrataFactor = 0.9 * d_calPro_RatedProratFactor;

				Assertions.passTest("Cancel Policy Page",
						"Calculated Short-Rated Prorata Factor is " + calShort_RatedProrataFactor);
			} else {

			}

			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Enter cancellation details
			Assertions.verify(
					cancelPolicyPage.nextButton.checkIfElementIsPresent()
							&& cancelPolicyPage.nextButton.checkIfElementIsDisplayed(),
					true, "Cancel Policy Page", "Cancel Policy page loaded successfully", false, false);

			// Comparing calculated and actual pro-rated prorata factor and short-rated
			// prorata factor under cancellation option for all insured request
			Assertions.addInfo("Scenario 03",
					"Calculating and Comparing the Prorata values of Pro Rated and Short Rated when Cancellation reason selecting insured request");
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

				// Getting Actual Pro-rated prorata factor and short-rated prorata factor
				actualProratedProratFactor = cancelPolicyPage.prorataFactor.formatDynamicPath(1).getData();
				d_actualProratedProratFactor = Double.parseDouble(actualProratedProratFactor);
				actualShortratedProrataFactor = cancelPolicyPage.prorataFactor.formatDynamicPath(2).getData();
				d_actualShortratedProrataFactor = Double.parseDouble(actualShortratedProrataFactor);

				// Comparing Actual and calculated values for short-rated prorata factor

				if (Precision.round(Math.abs(Precision.round(d_actualShortratedProrataFactor, 2)
						- Precision.round(calShort_RatedProrataFactor, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Actual Short-rated prorata factor and calculated Short-rated prorata factor both are equal, Actual Short-rated prorata factor is "
									+ d_actualShortratedProrataFactor + " verified");
				} else {
					Assertions.verify(d_actualShortratedProrataFactor, calShort_RatedProrataFactor,
							"Policy Summary Page",
							"Actual and Calculated Short-rated prorata factor are not matching for state "
									+ testData.get("QuoteState"),
							false, false);
				}

				// Comparing Actual and calculated values for pro-rated prorata factor

				if (Precision.round(Math.abs(Precision.round(d_actualProratedProratFactor, 2)
						- Precision.round(d_calPro_RatedProratFactor, 2)), 2) < 0.05) {
					Assertions.passTest("Cancel Policy Page",
							"Actual Pro-rated prorata factor and calculated Pro-rated prorata factor both are equal, Actual Pro-rated prorata factor is "
									+ d_calPro_RatedProratFactor + " verified");
				} else {
					Assertions.verify(d_actualProratedProratFactor, d_calPro_RatedProratFactor, "Policy Summary Page",
							"Actual and Calculated Pro-rated prorata factor are not matching for state "
									+ testData.get("QuoteState"),
							false, false);
				}

				cancelPolicyPage.previousButton.waitTillPresenceOfElement(60);
				cancelPolicyPage.previousButton.scrollToElement();
				cancelPolicyPage.previousButton.click();
				Assertions.passTest("Cancel Policy page", "Navigated to Cancel policy page");
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// click on home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search the policy
			homePage.searchPolicy(policyNumber);

			// Click on cancel policy
			Assertions.addInfo("Policy Summary Page", "Cancellation of Policy");
			testData = data.get(data_Value4);
			policySummarypage.cancelPolicy.scrollToElement();
			policySummarypage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link");
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page loaded successfully", false, false);
			cancelPolicyPage.enterCancellationDetails(testData);
			Assertions.passTest("Policy Sumary Page", "Policy Cancelled successfully");

			// Asserting Premium and Return Premiums
			Assertions.addInfo("Policy Summary Page", "Asserting Premium and Return Premiums");
			policySummarypage.transHistReason.formatDynamicPath(2).scrollToElement();
			policySummarypage.transHistReason.formatDynamicPath(2).click();
			Assertions.verify(policySummarypage.policyTotalPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "The Premium is " + policySummarypage.policyTotalPremium.getData(), false,
					false);

			policySummarypage.transHistReason.formatDynamicPath(3).scrollToElement();
			policySummarypage.transHistReason.formatDynamicPath(3).click();
			Assertions.verify(policySummarypage.policyTotalPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "The Return Premium is " + policySummarypage.policyTotalPremium.getData(),
					false, false);

			// click on reinstate policy
			policySummarypage.reinstatePolicy.scrollToElement();
			policySummarypage.reinstatePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on reinstate policy link");

			// click on ok button
			if (reinstatePolicyPage.okButton.checkIfElementIsPresent()
					&& reinstatePolicyPage.okButton.checkIfElementIsDisplayed()) {
				reinstatePolicyPage.okButton.scrollToElement();
				reinstatePolicyPage.okButton.click();
			}

			// reinstate policy
			Assertions.verify(reinstatePolicyPage.completeReinstatement.checkIfElementIsDisplayed(), true,
					"Reinstatement Policy Page", "Reinstatement Policy Page is loaded successfully", false, false);
			testData = data.get(data_Value1);
			reinstatePolicyPage.enterReinstatePolicyDetails(testData);

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// calculate SLTF values after reinstatement
			// getting premium, fees and sltf values from Policy Summary
			// page
			premiumValueAcc = Double.parseDouble(
					policySummarypage.PremiumFee.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));
			feeValueAcc = Double.parseDouble(
					policySummarypage.inspectionFee.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));
			policyFeesAcc = Double.parseDouble(
					policySummarypage.policyFee.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));
			totalActualSltfValueAcc = Double.parseDouble(
					policySummarypage.sltfValue.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));
			policySurplusContributionValue = Double.parseDouble(policySummarypage.surplusContributionValue
					.formatDynamicPath(1).getData().replace("$", "").replace(",", ""));

			// Calculate SLTF for Transaction details on policy summary page
			Assertions.addInfo("Scenario 04",
					"Verifying SLTF value after Reinstate Policy for Transaction , Annual , Term Total");
			sltfValueCalculatedAcc = (premiumValueAcc + feeValueAcc + policyFeesAcc + policySurplusContributionValue)
					* (Double.parseDouble(testData.get("SLTFValue"))) / 100;
			Assertions.passTest("Policy Summary Page ",
					"Transaction Premium is " + policySummarypage.PremiumFee.formatDynamicPath(1).getData());
			Assertions.passTest("Policy Summary Page",
					"Transaction Inspection Fee is " + policySummarypage.inspectionFee.formatDynamicPath(1).getData());
			Assertions.passTest("Policy Summary Page",
					"Transaction Policy Fee is " + policySummarypage.policyFee.formatDynamicPath(1).getData());

			Assertions.addInfo("Policy Summary Page",
					"Calculating SLTF values for state " + testData.get("QuoteState") + " on Policy Summary Page");
			if (Precision.round(
					Math.abs(Precision.round(totalActualSltfValueAcc, 2) - Precision.round(sltfValueCalculatedAcc, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Transaction SLTF is calculated correctly for state "
						+ testData.get("QuoteState") + ". Value is $" + sltfValueCalculatedAcc);
			} else {
				Assertions.verify(totalActualSltfValueAcc, sltfValueCalculatedAcc, "Policy Summary Page",
						"Actual and Calculated values are not matching for state " + testData.get("QuoteState"), false,
						false);
			}

			// calculate SLTF values after reinstatement
			// getting premium, fees and sltf values from Policy Summary
			// page
			premiumValueAnnual = Double.parseDouble(
					policySummarypage.PremiumFee.formatDynamicPath(2).getData().replace("$", "").replace(",", ""));
			feeValueAnnual = Double.parseDouble(
					policySummarypage.inspectionFee.formatDynamicPath(2).getData().replace("$", "").replace(",", ""));
			policyFeesAnnual = Double.parseDouble(
					policySummarypage.policyFee.formatDynamicPath(2).getData().replace("$", "").replace(",", ""));
			totalActualSltfValueAnnual = Double.parseDouble(
					policySummarypage.sltfValue.formatDynamicPath(2).getData().replace("$", "").replace(",", ""));

			policySurplusContributionValue = Double.parseDouble(policySummarypage.surplusContributionValue
					.formatDynamicPath(2).getData().replace("$", "").replace(",", ""));

			// Calculate SLTF for Annual details on policy summary page
			sltfValueCalculatedAnnual = (premiumValueAnnual + feeValueAnnual + policyFeesAnnual
					+ policySurplusContributionValue) * (Double.parseDouble(testData.get("SLTFValue"))) / 100;
			Assertions.passTest("Policy Summary Page ",
					"Annual Premium is " + policySummarypage.PremiumFee.formatDynamicPath(1).getData());
			Assertions.passTest("Policy Summary Page",
					"Annual Inspection Fee is " + policySummarypage.inspectionFee.formatDynamicPath(1).getData());
			Assertions.passTest("Policy Summary Page",
					"Annual Policy Fee is " + policySummarypage.policyFee.formatDynamicPath(1).getData());

			Assertions.addInfo("Policy Summary Page",
					"Calculating SLTF values for state " + testData.get("QuoteState") + " on Policy Summary Page");
			if (Precision.round(Math.abs(
					Precision.round(totalActualSltfValueAnnual, 2) - Precision.round(sltfValueCalculatedAnnual, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Annual SLTF is calculated correctly for state "
						+ testData.get("QuoteState") + ". Value is $" + sltfValueCalculatedAcc);
			} else {
				Assertions.verify(totalActualSltfValueAnnual, sltfValueCalculatedAcc, "Policy Summary Page",
						"Actual and Calculated values are not matching for state " + testData.get("QuoteState"), false,
						false);
			}

			// calculate SLTF values after reinstatement
			// getting premium, fees and sltf values from Policy Summary
			// page
			premiumValueTerm = Double.parseDouble(
					policySummarypage.PremiumFee.formatDynamicPath(3).getData().replace("$", "").replace(",", ""));
			feeValueTerm = Double.parseDouble(
					policySummarypage.inspectionFee.formatDynamicPath(3).getData().replace("$", "").replace(",", ""));
			policyFeesTerm = Double.parseDouble(
					policySummarypage.policyFee.formatDynamicPath(3).getData().replace("$", "").replace(",", ""));
			totalActualSltfValueTerm = Double.parseDouble(
					policySummarypage.sltfValue.formatDynamicPath(3).getData().replace("$", "").replace(",", ""));
			policySurplusContributionValue = Double.parseDouble(policySummarypage.surplusContributionValue
					.formatDynamicPath(3).getData().replace("$", "").replace(",", ""));

			// Calculate SLTF for Transaction details on policy summary page
			sltfValueCalculatedTerm = (premiumValueTerm + feeValueTerm + policyFeesTerm
					+ policySurplusContributionValue) * (Double.parseDouble(testData.get("SLTFValue"))) / 100;
			Assertions.passTest("Policy Summary Page ",
					"Term total Premium is " + policySummarypage.PremiumFee.formatDynamicPath(1).getData());
			Assertions.passTest("Policy Summary Page",
					"Term total Inspection Fee is " + policySummarypage.inspectionFee.formatDynamicPath(1).getData());
			Assertions.passTest("Policy Summary Page",
					"Term total Policy Fee is " + policySummarypage.policyFee.formatDynamicPath(1).getData());

			Assertions.addInfo("Policy Summary Page",
					"Calculating SLTF values for state " + testData.get("QuoteState") + " on Policy Summary Page");
			if (Precision.round(
					Math.abs(
							Precision.round(totalActualSltfValueTerm, 2) - Precision.round(sltfValueCalculatedTerm, 2)),
					2) < 0.05) {
				Assertions.passTest("Policy Summary Page", "Term total SLTF is calculated correctly for state "
						+ testData.get("QuoteState") + ". Value is $" + sltfValueCalculatedTerm);
			} else {
				Assertions.verify(totalActualSltfValueTerm, sltfValueCalculatedTerm, "Policy Summary Page",
						"Actual and Calculated values are not matching for state " + testData.get("QuoteState"), false,
						false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC021 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC021 ", "Executed Successfully");
			}
		}
	}
}