/** Program Description: Part1: Assert SLTF values on cancellation page after Pro Rata cancellation
Part2: Reinstate the policy and process PB endorsement by reducing the coverage values
Part3: Process reverse last endorsement and assert the original coverage values
*  Author			   : Pavan Mule
*  Date of Creation   : 01/03/2022
**/

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBNHTC002 extends AbstractNAHOTest {

	public PNBNHTC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/NHTC002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		testData = data.get(dataValue1);
		String quoteNumber;
		String policyNumber;
		String originalPremiumAmount;
		double d_originalPremiumAmount;
		String earnedPremiumAmount;
		double d_earnedPremiumAmount;
		String returnedPremiumAmount;
		double d_returnedPremiumAmount;
		String originalPolicyFees;
		double d_originalPolicyFees;
		String earnedPolicyFees;
		double d_earnedPolicyFees;
		String returnedPolicyFees;
		double d_returnedPolicyFees;
		String originalActualSLTF;
		double d_originalActualSLTF;
		String earnedActualSLTF;
		double d_earnedActualSLTF;
		String returnedActualSLTF;
		double d_returnedActualSLTF;
		double calPro_RatedProratFactor;
		double d_calPro_RatedProratFactor;
		String actualProratedProratFactor;
		double d_actualProratedProratFactor;
		String sltfPercentage;
		double d_sltfPercentage;
		double d_calculatedOriginalSLTF;
		double d_calculatedEarnedSLTF;
		double d_calculatedReturnedSLTF;
		String covAValue;
		String covBValue;
		String covCValue;
		String covDValue;
		String reverseCovAvalue;
		String reverseCovBvalue;
		String reverseCovCvalue;
		String reverseCovDvalue;
		String originalSurplusCont;
		double d_originalSurplusCont;
		String earnedSurplusCont;
		double d_earnedSurplusCont;
		String returnedSurplusCont;
		double d_returnedSurplusCont;

		// Days Difference between policy effective date and cancellation effective date
		String differenceDays = testData.get("CalcCancellationEffectiveDate");
		double daysDiff = Double.parseDouble(differenceDays);

		// Number of days in the year
		double totalNoOfDays = 366;
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New account", "New account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility page",
					"Eligibility page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling page",
					"Dwelling page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior loss page",
						"Prior loss page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account overview page", "Account overview page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account overview page", "Quote number is : " + quoteNumber);

			// click on request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting questions page", "Underwriting questions details entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request bind page",
					"Request bind page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request bind page", "Bind details entered successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Click on Cancellation link on policy summary page
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy summary page", "Clicked on cancel link successfully");

			// Enter Cancellation details
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel policy page",
					"Cancel policy page loaded successfully", false, false);
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason"))
					.waitTillPresenceOfElement(60);
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel policy page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());
			cancelPolicyPage.cancellationEffectiveDate.waitTillPresenceOfElement(60);
			cancelPolicyPage.cancellationEffectiveDate.clearData();
			cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
			cancelPolicyPage.cancellationEffectiveDate.tab();
			Assertions.passTest("Cancel policy page",
					"Cancellation effective date is :" + (testData.get("CancellationEffectiveDate")));
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// Select pro-rated radio button
			cancelPolicyPage.proRatedRadioBtn.scrollToElement();
			cancelPolicyPage.proRatedRadioBtn.click();
			Assertions.passTest("Cancel policy page", "Pro-Rated radio button selected successfully");
			cancelPolicyPage.waitTime(2);

			// Calculating prorata factor for pro-rated = 1 - (Number of Days between
			// Cancellation Effective Date and Policy Effective Date)/365
			calPro_RatedProratFactor = 1 - (daysDiff) / totalNoOfDays;
			d_calPro_RatedProratFactor = Precision.round(calPro_RatedProratFactor, 3);

			// Getting Actual Pro-rated prorata factor
			actualProratedProratFactor = cancelPolicyPage.prorataFactor.formatDynamicPath(1).getData();
			d_actualProratedProratFactor = Double.parseDouble(actualProratedProratFactor);
			Assertions.passTest("Cancel policy page", "Actual prorated prorata factor");

			// Comparing Actual and calculated values for pro-rated prorata factor
			Assertions.addInfo("Scenario 01", "Comparing actual and calculated prorated profatctor");
			if (Precision.round(Math.abs(
					Precision.round(d_actualProratedProratFactor, 2) - Precision.round(d_calPro_RatedProratFactor, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page",
						"Acual Pro-rated prorata factor is " + d_actualProratedProratFactor);
				Assertions.passTest("Cancel Policy Page",
						"Actual Pro-rated prorata factor and calculated Pro-rated prorata factor both are equal, Actual Pro-rated prorata factor is "
								+ d_calPro_RatedProratFactor + " verified");

			} else {
				Assertions.verify(d_actualProratedProratFactor, d_calPro_RatedProratFactor, "Cancel policy page",
						"The Difference between actual and calculated pro_rated prorat factor is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 ended");

			// Getting premium amount for original, Earned and Returned
			originalPremiumAmount = cancelPolicyPage.newPremiumNAHO.formatDynamicPath(1).getData().replace("$", "");
			earnedPremiumAmount = cancelPolicyPage.newPremiumNAHO.formatDynamicPath(2).getData().replace("$", "");
			returnedPremiumAmount = cancelPolicyPage.newPremiumNAHO.formatDynamicPath(3).getData().replace("$", "")
					.replace("-", "");
			Assertions.passTest("Cancel policy page", " Original premium amount : " + "$" + originalPremiumAmount);
			Assertions.passTest("Cancel policy page", " Earned premium amount : " + "$" + earnedPremiumAmount);
			Assertions.passTest("Cancel policy page",
					" Returned premium amount : " + "-" + "$" + returnedPremiumAmount);

			// Getting Policy fees for original, Earned and Returned
			originalPolicyFees = cancelPolicyPage.policyFeeNAHO.formatDynamicPath(1).getData().replace("$", "");
			earnedPolicyFees = cancelPolicyPage.newPolicyFee.getData();
			returnedPolicyFees = cancelPolicyPage.policyFeeNAHO.formatDynamicPath(3).getData().replace("$", "");
			Assertions.passTest("Cancel policy page", " Original policy fee : " + "$" + originalPolicyFees);
			Assertions.passTest("Cancel policy page", " Earned policy fee : " + "$" + earnedPolicyFees);
			Assertions.passTest("Cancel policy page", " Returned policy fee : " + "$" + returnedPolicyFees);

			// Getting Surplus Contribution Values for original, earned and returned
			originalSurplusCont = cancelPolicyPage.surplusContributionVal.formatDynamicPath(1).getData()
					.replace("$", "").replace("%", "");
			earnedSurplusCont = cancelPolicyPage.surplusContributionVal.formatDynamicPath(2).getData().replace("$", "")
					.replace("%", "");
			returnedSurplusCont = cancelPolicyPage.surplusContributionVal.formatDynamicPath(3).getData()
					.replace("-", "").replace("$", "").replace("%", "");

			Assertions.passTest("Cancel policy page",
					" Original Surplus Contribution Value : " + "$" + originalSurplusCont);
			Assertions.passTest("Cancel policy page",
					" Earned Surplus Contribution Value : " + "$" + earnedSurplusCont);
			Assertions.passTest("Cancel policy page",
					" RSurplus Contribution Value : " + "-" + "$" + returnedSurplusCont);

			// Getting actual SLTF values for original,Earned and Returned
			originalActualSLTF = cancelPolicyPage.SLTF.formatDynamicPath(1).getData().replace("$", "").replace("%", "");
			earnedActualSLTF = cancelPolicyPage.SLTF.formatDynamicPath(2).getData().replace("$", "").replace("%", "");
			returnedActualSLTF = cancelPolicyPage.SLTF.formatDynamicPath(3).getData().replace("$", "").replace("-", "")
					.replace("%", "");
			sltfPercentage = testData.get("SLTFPercentage");

			// Converting String to double
			d_originalPremiumAmount = Double.parseDouble(originalPremiumAmount);
			d_earnedPremiumAmount = Double.parseDouble(earnedPremiumAmount);
			d_returnedPremiumAmount = Double.parseDouble(returnedPremiumAmount);
			d_originalPolicyFees = Double.parseDouble(originalPolicyFees);
			d_earnedPolicyFees = Double.parseDouble(earnedPolicyFees);
			d_returnedPolicyFees = Double.parseDouble(returnedPolicyFees);
			d_originalActualSLTF = Double.parseDouble(originalActualSLTF);
			d_earnedActualSLTF = Double.parseDouble(earnedActualSLTF);
			d_returnedActualSLTF = Double.parseDouble(returnedActualSLTF);
			d_sltfPercentage = Double.parseDouble(sltfPercentage);
			d_originalSurplusCont = Double.parseDouble(originalSurplusCont);
			d_earnedSurplusCont = Double.parseDouble(earnedSurplusCont);
			d_returnedSurplusCont = Double.parseDouble(returnedSurplusCont);

			// Calculating Original column SLTF value (premium+policy fee)/3%(excluding
			// inspection
			// fees)
			d_calculatedOriginalSLTF = (d_originalPremiumAmount + d_originalPolicyFees + d_originalSurplusCont)
					* (d_sltfPercentage / 100);

			// Calculating earned column SLTF value (premium+policy fee)/3%(excluding
			// inspection
			// fees)
			d_calculatedEarnedSLTF = (d_earnedPremiumAmount + d_earnedPolicyFees + d_earnedSurplusCont)
					* (d_sltfPercentage / 100);

			// Calculating returned column SLTF value (premium+policy fee)/3%(excluding
			// inspection
			// fees)
			d_calculatedReturnedSLTF = (d_returnedPremiumAmount + d_returnedPolicyFees + d_returnedSurplusCont)
					* (d_sltfPercentage / 100);

			// Verifying Actual SLTF and Calculated SLTF Values for original column, earned
			// column and returned column on Cancel policy page
			Assertions.addInfo("Scenario 02", "Verifying actual SLTF and calculated SLTF values on Cancel policy page");
			if (Precision.round(
					Math.abs(Precision.round(d_originalActualSLTF, 2) - Precision.round(d_calculatedOriginalSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel policy page", "Original column calculated surplus lines taxes and fees : "
						+ "$" + Precision.round(d_calculatedOriginalSLTF, 2));
				Assertions.passTest("Cancel policy page",
						"Original column actual surplus lines taxes and fees : " + "$" + d_originalActualSLTF);
			} else {
				Assertions.verify(d_originalActualSLTF, d_calculatedOriginalSLTF, "Cancel policy page",
						"The Difference between actual SLTF value Fund and calculated SLTF value is more than 0.05",
						false, false);
			}
			if (Precision.round(
					Math.abs(Precision.round(d_earnedActualSLTF, 2) - Precision.round(d_calculatedEarnedSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel policy page", "Earned column calculated surplus lines taxes and fees : "
						+ "$" + Precision.round(d_calculatedEarnedSLTF, 2));
				Assertions.passTest("Cancel policy page",
						"Earned column actual surplus lines taxes and fees : " + "$" + d_earnedActualSLTF);
			} else {
				Assertions.verify(d_earnedActualSLTF, d_calculatedEarnedSLTF, "Cancel policy page",
						"The Difference between earned column actual SLTF and earned column value calculated SLTF value is more than 0.05",
						false, false);
			}
			if (Precision.round(
					Math.abs(Precision.round(d_returnedActualSLTF, 2) - Precision.round(d_calculatedReturnedSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel policy page", "Returned column calculated surplus lines taxes and fees : "
						+ "-" + "$" + Precision.round(d_calculatedReturnedSLTF, 2));
				Assertions.passTest("Cancel policy page",
						"Returned column actual surplus lines taxes and fees : " + "-" + "$" + d_returnedActualSLTF);
			} else {
				Assertions.verify(d_returnedActualSLTF, d_calculatedReturnedSLTF, "Cancel policy page",
						"The Difference between returned column actual SLTF and returned column value calculated SLTF value is more than 0.05",
						false, false);
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 ended");
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			cancelPolicyPage.closeButton.scrollToElement();
			cancelPolicyPage.closeButton.click();
			Assertions.passTest("Cancel policy page", "Cancellation details entered successfully");

			// checking policy status on policy summary page
			Assertions.verify(policySummaryPage.policyStatus.getData(), "NOC", "Policy summary page",
					"Policy status is :" + policySummaryPage.policyStatus.getData(), false, false);
			policySummaryPage.transHistReason.formatDynamicPath(3).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(3).click();

			// Click on reinstate policy link
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			Assertions.passTest("Policy summary page", "Clicked on reinstate policy link successfully");

			// Enter reinstate policy details
			Assertions.verify(reinsatePolicyPage.completeReinstatement.checkIfElementIsDisplayed(), true,
					"Reinsate policy page", "Reinsate policy page loaded successfully", false, false);
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);
			Assertions.passTest("Reinsate policy page", "Reinstate policy details entered successfully");

			// checking policy status on policy summary page
			Assertions.verify(policySummaryPage.policyStatus.getData(), "Active", "Policy summary page",
					"Policy status is :" + policySummaryPage.policyStatus.getData(), false, false);

			// Click on endorsement PB link
			policySummaryPage.transHistReason.formatDynamicPath(2).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(2).click();
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy summary page", "Clicked on endorse PB link successfully");

			// Enter Endorsement effective details
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse policy page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorsement policy page",
					"Endorsement effective date is " + (testData.get("TransactionEffectiveDate")));

			// Click on Change Coverage Option link on Endorsement policy page
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse policy page", "Clicked on change coverage Options link");

			// Retrieving dwelling coverage values
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create quote page", "Create quote page loaded Successfully", false, false);
			covAValue = createQuotePage.coverageADwelling.getData().replace(",", "");
			covBValue = createQuotePage.coverageBOtherStructures.getData().replace(",", "");
			covCValue = createQuotePage.coverageCPersonalProperty.getData().replace(",", "");
			covDValue = createQuotePage.coverageDFairRental.getData().replace(",", "");
			Assertions.addInfo("Scenario 03", "Retrieving dwelling coverage values from create quote page");
			Assertions.passTest("Create quote page", "Original COV A value is : " + "$" + covAValue);
			Assertions.passTest("Create quote page", "Original COV B value is : " + "$" + covBValue);
			Assertions.passTest("Create quote page", "Original COV C value is : " + "$" + covCValue);
			Assertions.passTest("Create quote page", "Original COV D value is : " + "$" + covDValue);
			Assertions.addInfo("Scenario 03", "Scenario 03 ended");

			// Updating Insured Coverage values
			testData = data.get(dataValue2);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.addInfo("Scenario 04", "Updating latest insured values");
			Assertions.passTest("Create quote page",
					"Latest COV A value is : " + "$" + (testData.get("L1D1-DwellingCovA")));
			Assertions.passTest("Create quote page",
					"Latest COV B value is : " + "$" + (testData.get("L1D1-DwellingCovB")));
			Assertions.passTest("Create quote page",
					"Latest COV C value is : " + "$" + (testData.get("L1D1-DwellingCovC")));
			Assertions.passTest("Create quote page",
					"Latest COV D value is : " + "$" + (testData.get("L1D1-DwellingCovD")));
			Assertions.addInfo("Scenario 04", "Scenario 04 ended");
			createQuotePage.continueEndorsementBtn.scrollToElement();
			createQuotePage.continueEndorsementBtn.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse policy page", "Endorsement details entered successfully");

			// Click on reverse endorsement link on policy summary page
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully", false, false);
			policySummaryPage.reversePreviousEndorsementLink.scrollToElement();
			policySummaryPage.reversePreviousEndorsementLink.click();
			Assertions.passTest("Policy summary page", "Clicked on reverse last endorsement link successfully");
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse policy page", "Reverse last endorsement transaction completed successfully");

			// Click on endorsement PB link
			policySummaryPage.transHistReason.formatDynamicPath(2).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(2).click();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully", false, false);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy summary page", "Clicked on endorse PB link successfully");

			// Enter Endorsement effective details
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse policy page", "Endorse policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorsement policy page",
					"Endorsement effective date is " + (testData.get("TransactionEffectiveDate")));

			// Click on Change Coverage Option link on Endorsement policy page
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse policy page", "Clicked on change coverage Options link");

			// Comparing original values with reverse endorsement values
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create quote page", "Create quote page loaded Successfully", false, false);
			reverseCovAvalue = createQuotePage.coverageADwelling.getData().replace(",", "");
			reverseCovBvalue = createQuotePage.coverageBOtherStructures.getData().replace(",", "");
			reverseCovCvalue = createQuotePage.coverageCPersonalProperty.getData().replace(",", "");
			reverseCovDvalue = createQuotePage.coverageDFairRental.getData().replace(",", "");
			Assertions.addInfo("Scenario 05",
					"Comparing original insured values with reversel endorsement insured valuse");
			Assertions.verify(reverseCovAvalue, covAValue, "Create quote page",
					"Original Cov A value and After reverse endorsement Cov A value are same, Cov A : " + "$"
							+ reverseCovAvalue,
					false, false);
			Assertions.verify(reverseCovBvalue, covBValue, "Create quote page",
					"Original Cov B value and After reverse endorsement Cov B value are same, Cov B : " + "$"
							+ reverseCovBvalue,
					false, false);
			Assertions.verify(reverseCovCvalue, covCValue, "Create quote page",
					"Original Cov C value and After reverse endorsement Cov C value are same, Cov C : " + "$"
							+ reverseCovCvalue,
					false, false);
			Assertions.verify(reverseCovDvalue, covDValue, "Create quote page",
					"Original Cov D value and After reverse endorsement Cov D value are same, Cov D : " + "$"
							+ reverseCovDvalue,
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 ended");

			// adding below code for validation, Asserting hard stop message when Cov A less
			// than $150,000
			testData = data.get(dataValue3);
			createQuotePage.coverageADwelling.scrollToElement();
			createQuotePage.coverageADwelling.clearData();
			// createQuotePage.coverageADwelling.tab();
			createQuotePage.coverageADwelling.waitTillPresenceOfElement(60);
			createQuotePage.coverageADwelling.waitTillVisibilityOfElement(60);
			// createQuotePage.coverageADwelling.scrollToElement();
			createQuotePage.coverageADwelling.appendData(testData.get("L1D1-DwellingCovA"));
			createQuotePage.coverageADwelling.tab();
			createQuotePage.continueEndorsementButton.waitTillPresenceOfElement(60);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			if (createQuotePage.override.checkIfElementIsPresent()
					&& createQuotePage.override.checkIfElementIsDisplayed()) {
				createQuotePage.override.scrollToElement();
				createQuotePage.override.click();
			}
			Assertions.addInfo("Scenario 06", "Asserting hard stop message when Cov A less than $150,000");
			Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Create quote page",
					"Hard stop message is " + dwellingPage.protectionClassWarMsg.getData(), false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 ended");

			// Close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBNHTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBNHTC002 ", "Executed Successfully");
			}
		}
	}
}
