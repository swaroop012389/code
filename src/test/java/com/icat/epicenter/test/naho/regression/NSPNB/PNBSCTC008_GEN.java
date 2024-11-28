/** Program Description: As a USM, check if the EQ premium is displayed and added in the Premium total on the Referral quote page and Check the Premium Override done successfully - Renewal
 *  Author			   : Pavan Mule
 *  Date of Creation   : 05/03/2024
 **/
package com.icat.epicenter.test.naho.regression.NSPNB;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PremiumAdjustmentRequestPage;
import com.icat.epicenter.pom.PremiumReliefDecisionPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBSCTC008_GEN extends AbstractNAHOTest {

	public PNBSCTC008_GEN() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/SC008.xls";
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
		PremiumAdjustmentRequestPage premiumAdjustmentRequestPage = new PremiumAdjustmentRequestPage();
		PremiumReliefDecisionPage premiumReliefDecisionPage = new PremiumReliefDecisionPage();
		ReferralPage referralPage = new ReferralPage();
		LoginPage loginPage = new LoginPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Variables
		String quoteNumber;
		String originalPremium1;
		String windPremium;
		String aopPremium;
		String liabilityPremium;
		String eqPremium;
		String offeredApprovedPremium;
		double sumOfAopGLWindandEq;
		String latestPremium;
		String policyNumber;
		String expectedMinimuAOWH = "2%";
		String expectedAOP = "$5,000";
		String currentPremium;
		String newPremium;
		String windPremiumOverride;
		String aopPremiumOverride;
		String liabilityPremiumOverride;
		double d_windPremiumOverride;
		double d_aopPremiumOverride;
		double d_liabilityPremiumOverride;
		double calNewPremium;
		String premium;
		double d_premium;
		String icatFees;
		double d_icatFees;
		double calPremium;
		String eQPremiumOverride;
		double d_eQPremiumOverride;
		String overrideFactor;
		double d_overrideFactor;
		String originalPremium;
		double d_originalPremium;
		String overridePremium;
		double d_overridePremium;
		String actualSLTF;
		double d_actualSLTF;
		String surplusContributionValue;
		double d_surplusContributionValue;
		String actualIcatFees;
		double d_actualIcatFees;
		double calOverrideFactor;
		double calSLTF;
		String sltfPercentage;
		double d_sltfPercentage;
		String vpfqPremium;
		double d_vpfqPremium;
		String vpfqPolicyFee;
		double d_vpfqPolicyFee;
		String vpfqInspectionFee;
		double d_vpfqInspectionFee;
		String vpfqSLTF;
		double d_vpfqSLTF;
		String vpfqSurplusContribution;
		double d_vpfqSurplusContribution;
		double d_vpfqCalSLTF;

		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {

			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss Details Entered successfully");
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting Quote number
			Assertions.verify(
					accountOverviewPage.requestBind.checkIfElementIsPresent()
							&& accountOverviewPage.requestBind.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// Verifying the absence of request premium change link on account overview
			// page as usm
			Assertions.addInfo("Scenario 01", "Verifying absence of premium change link as usm");
			Assertions.verify(accountOverviewPage.requestPremiumChangeLink.checkIfElementIsPresent(), false,
					"Account Overview Page", "Premium Change link  not displayed", false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Logout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Loged out as usm successfully");

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in a as producer Successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Quote searched as a producer successfully");

			// Getting premium value from account overview page
			originalPremium1 = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");

			// Verifying the presence of request premium change link on account overview
			// page
			Assertions.addInfo("Scenario 02", "Verifying presence of premium change link");
			Assertions.verify(accountOverviewPage.requestPremiumChangeLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Change link displayed successfully", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			accountOverviewPage.requestPremiumChangeLink.scrollToElement();
			accountOverviewPage.requestPremiumChangeLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on request premium change link successfully");

			// Enter premium changes details
			premiumAdjustmentRequestPage.waitTime(1);
			Assertions.verify(premiumAdjustmentRequestPage.rpcUpdateBtn.checkIfElementIsDisplayed(), true,
					"Premium Adjustment Request Page", "Premium Adjustment Request Page loaded successfully", false,
					false);
			premiumAdjustmentRequestPage.requestPremiumChanges(testData);
			Assertions.passTest("Premium Adjustment Request Page", "Premium Adjustment Details entered successfully");

			// Asserting and verifying referral message,referral message is'Thank you for
			// your referral. Your Underwriting contact has been notified of this request
			// and will get back to you shortly. If you have any questions about this
			// request, please reference the following number:XX
			Assertions.addInfo("Scenario 03", "Asserting and Verifying referral message");
			Assertions.verify(
					accountOverviewPage.requestPremiumChangeReferralMsg.getData()
							.contains("Thank you for your referral"),
					true, "Account Overview Page",
					"The Referral messgae is " + accountOverviewPage.requestPremiumChangeReferralMsg.getData(), false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Logout as usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Loged out as usm successfully");

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page ", "Logged in as USM Successfully");

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Search for quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote searched as a usm successfully");

			// Click on open referral(Here quote is referring because of changing the
			// premium in request premium page
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on open referral link successfully");

			// Click on approve/decline button
			referralPage.approveOrDeclineRequest.scrollToElement();
			referralPage.approveOrDeclineRequest.click();
			Assertions.passTest("Referral Page", "Clicked on Approve/Decline Request button successfully");

			// Verifying the presence of EQ premium label and value in premium relief
			// decision page, when eq deductible added
			Assertions.addInfo("Scenario 04",
					"Verifying the presence of EQ premium label and value in premium relief decision page, when eq deductible added");
			Assertions.verify(premiumReliefDecisionPage.earthquakePremiumLabel.checkIfElementIsDisplayed(), true,
					"Premium Relief Decision Page",
					"The EQ Premium is " + premiumReliefDecisionPage.earthquakePremiumLabel.getData() + " displayed",
					false, false);
			Assertions
					.verify(premiumReliefDecisionPage.earthquakePremiumValue.checkIfElementIsDisplayed(), true,
							"Premium Relief Decision Page", "The EQ Premium value is "
									+ premiumReliefDecisionPage.earthquakePremiumValue.getData() + " displayed",
							false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Verifying the target premium updated on premium relief decision page
			Assertions.addInfo("Scenario 05", "Verifying the target premium updated on premium relief decision page");
			Assertions.verify(
					premiumReliefDecisionPage.requestedPremium.getData().replace("$", "").replace(",", "").contains(
							testData.get("PremiumAdjustment_TargetPremium")),
					true, "Premium Relife Decision Page",
					"The target premium updated on premium relief decision page, The requested premium is "
							+ premiumReliefDecisionPage.requestedPremium.getData(),
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Enter premium relief decision details
			premiumReliefDecisionPage.enterPremiumReliefDetailsNAHO(testData);
			Assertions.passTest("Premium Relife Decision Page", "Premium relief details entered successfully");

			// Getting wind, aop and GL premium
			windPremium = premiumReliefDecisionPage.windPremium.getData();
			aopPremium = premiumReliefDecisionPage.aopPremium.getData().replace("$", "").replace(",", "");
			liabilityPremium = premiumReliefDecisionPage.orgGLPremium.getData().replace("$", "").replace(",", "");
			eqPremium = premiumReliefDecisionPage.earthquakePremiumValue.getData().replace("$", "").replace(",", "");
			offeredApprovedPremium = premiumReliefDecisionPage.offeredOrApprovedPremium.getData().replace(",", "");

			// Calculating Sum of the Original AOP, Wind and GL/Liability Premium and EQ
			// premium
			// (AOp+Wind+GL+EQ)
			sumOfAopGLWindandEq = (Double.parseDouble(windPremium) + Double.parseDouble(aopPremium)
					+ Double.parseDouble(liabilityPremium) + Double.parseDouble(eqPremium));

			// Verifying Offered/Approved Premium is equal to sum Of Aop,GL,Wind and EQ
			Assertions.addInfo("Scenario 06",
					"Verifying Offered/Approved Premium is equal to sum Of Aop,GL,EQ and Wind");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(offeredApprovedPremium), 2)
					- Precision.round(sumOfAopGLWindandEq, 2)), 2) < 0.01) {
				Assertions.passTest("Referral Approve/Decline Page", "Offered Or Approved Premium: " + "$"
						+ +Precision.round(Double.parseDouble(offeredApprovedPremium), 2));
				Assertions.passTest("Referral Approve/Decline Page",
						"Sum Of Aop GL Wind and EQ: " + "$" + Precision.round(sumOfAopGLWindandEq, 2));
			} else {
				Assertions.verify(Precision.round(Double.parseDouble(offeredApprovedPremium), 2),
						Precision.round(sumOfAopGLWindandEq, 2), "Referral Approve/Decline Page",
						"The Actaul and Calculated Wind,AOP,GL and EQ Values are not the Same", false, false);
			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on approve button
			premiumReliefDecisionPage.approveBtn.scrollToElement();
			premiumReliefDecisionPage.approveBtn.click();
			Assertions.passTest("Premium Relief Decision Page", "The referred quote approved successfully");

			// Open the referred quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Referred quote searched successfully");

			// Getting updated premium from account overview page
			Assertions.addInfo("Scenario 07",
					"Comparing and Asserting latest premium value from account overview page after reoffer approved premium");
			latestPremium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(latestPremium), 2)
					- Precision.round(Double.parseDouble(offeredApprovedPremium), 2)), 2) < 0.01) {
				Assertions.passTest("Account Overview Page", "Original Premium: " + "$" + originalPremium1);
				Assertions.passTest("Account Overview Page", "Offered Or Approved Premium: " + "$"
						+ Precision.round(Double.parseDouble(offeredApprovedPremium), 2));
				Assertions.passTest("Account Overview Page",
						"Latest Premium: " + "$" + Precision.round(Double.parseDouble(latestPremium), 2));
			} else {
				Assertions.verify(Precision.round(Double.parseDouble(latestPremium), 2),
						Precision.round(Double.parseDouble(offeredApprovedPremium), 2), "Account Overview Page",
						"Offered or approved premium and latest premium are not matching", false, false);
			}
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request bind button");

			// Entering Underwriting question details
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting questions details entered Successfully");

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered Successfully");

			// Asserting Policy Number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policyNumber, false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on Expacc link
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			Assertions.passTest("Home Page", "Clicked on Expacc link");

			// Enter Expacc details
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Performing Renewal Searches
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy " + policyNumber + " successfully");

			// clicking on renewal policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew policy link");

			// Getting renewal quote number
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal quote number " + quoteNumber);

			// Quote is referring because of Reasons for referral: This account is outside
			// underwriting guidelines: A renewal referral has been triggered due to the
			// previous premium adjustment being above threshold
			// Approve referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				Assertions.verify(
						referralPage.pickUp.checkIfElementIsPresent()
								|| referralPage.approveOrDeclineRequest.checkIfElementIsPresent(),
						true, "Referral Page", "Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
				referralPage.close.scrollToElement();
				referralPage.close.click();
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);
				Assertions.addInfo("Home Page", "Quote searched successfully");
			}

			// Click on edit DeductibleAndLimits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit deductibleandlimits button");

			// Assert the AOWH deductible is default to minimum deductible(2%) in the
			// MinimumDeductibleNAHOAOWH table.
			String aowhDeductible = createQuotePage.aowhDeductibleData.getData();
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 08",
					"Verifying and Asserting AOWH deductible is default to minimum deductible(2%)");

			Assertions.verify(createQuotePage.aowhDeductibleData.getData(), expectedMinimuAOWH, "Create Quote Page",
					"The AOWH deductible default is " + createQuotePage.aowhDeductibleData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Assert the AOP deductible is default to minimum deductible($5000) in the
			// MinimumDeductibleNAHOAOP table for COV A greater than 1M and less than
			// S2000001.
			Assertions.addInfo("Scenario 09",
					"Verifying and Asserting AOP deductible is default to minimum deductible($5000)");
			Assertions.verify(createQuotePage.aopDeductibleData.getData(), expectedAOP, "Create Quote Page",
					"The AOP deductible default is " + createQuotePage.aopDeductibleData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Selecting AOP deductible = $25,000
			testData = data.get(data_Value2);
			createQuotePage.aopDeductibleArrow.scrollToElement();
			createQuotePage.aopDeductibleArrow.click();
			createQuotePage.aopDeductibleOption.formatDynamicPath(testData.get("AOPDeductibleValue")).scrollToElement();
			createQuotePage.aopDeductibleOption.formatDynamicPath(testData.get("AOPDeductibleValue")).click();

			// Verifying and Asserting the AOWH deductible is not affected by the selected
			// AOP deductible..
			Assertions.addInfo("Scenario 10",
					"Verifying and Asserting the AOWH deductible is not affected by the selected AOP deductible");
			Assertions.verify(createQuotePage.aowhDeductibleData.getData(), aowhDeductible, "Create Quote Page",
					"AOWH deductible is not affected by the selected AOP deductible,AOWH deductible is  "
							+ createQuotePage.aowhDeductibleData.getData(),
					false, false);
			Assertions.passTest("Create Quote Page", "Before selecting AOP,the AOWH value is " + aowhDeductible);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Selecting AOWH deductible = $25,000
			createQuotePage.aowhDeductibleArrow.scrollToElement();
			createQuotePage.aowhDeductibleArrow.click();
			createQuotePage.aowhDeductibleOption.formatDynamicPath(testData.get("AOWHDeductibleValue"))
					.scrollToElement();
			createQuotePage.aowhDeductibleOption.formatDynamicPath(testData.get("AOWHDeductibleValue")).click();

			// Verifying and Asserting the AOWH deductible can be selected by the user
			Assertions.addInfo("Scenario 11",
					"Verifying and Asserting the AOWH deductible can be selected by the user");
			Assertions.verify(createQuotePage.aowhDeductibleData.getData(), testData.get("AOWHDeductibleValue"),
					"Create Quote Page", "User is able to select AOWH deductible,selected AOWH deductible is "
							+ createQuotePage.aowhDeductibleData.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Click on get a quote button
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on get a quote and cintinue button");

			// Getting renewal quote number 2
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal quote number 2 " + quoteNumber);

			// getting premium and icat fees
			premium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			d_premium = Double.parseDouble(premium);

			icatFees = accountOverviewPage.icatFees.getData().replace("$", "").replace(",", "");
			d_icatFees = Double.parseDouble(icatFees);

			calPremium = d_premium + d_icatFees;

			// Verifying AOWH present in quote tree
			Assertions.addInfo("Scenario 12", "Verifying AOWH present in quote tree");
			Assertions.verify(
					accountOverviewPage.quoteTreeAopNsAowh.formatDynamicPath("AOWH").checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"AOWH displayed in quote tree AOWH value is " + accountOverviewPage.quoteTreeAopNsAowh
							.formatDynamicPath("AOWH").getData().substring(36, 39),
					false, false);
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			// Verifying All other wind and Hail options are present
			Assertions.addInfo("Scenario 13", "Verifying All other wind and Hail options are present");
			Assertions.verify(accountOverviewPage.aowhLable.getData().equals("All Other Wind & Hail"), true,
					"Account Overview Page",
					"All Other Wind & Hail option present is " + accountOverviewPage.aowhLable.getData() + " verified",
					false, false);
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Wind & Hail", 2)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "All Other Wind & Hail Option1 present is verified", false, false);
			Assertions.verify(
					accountOverviewPage.deductibleOptionsNAHO.formatDynamicPath("All Other Wind & Hail", 3)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "All Other Wind & Hail Option2 present is verified", false, false);
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			// Click on the View/Print Rate Trace link.
			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();
			Assertions.passTest("Account Overview Page", "Clicked on view or print rate trace link");

			// Verify the Rate Trace contains DeductibleFactorAOWH for Wind peril.
			Assertions.addInfo("Scenario 14", "Verify the Rate Trace contains DeductibleFactorAOWH for Wind peril");
			Assertions.verify(viewOrPrintRateTrace.windDeductibleAOWHFactor.getData().equals("DeductibleFactorAOWH"),
					true, "View Or Print Rate Trace Page", "Rate Trace contains DeductibleFactorAOWH for Wind peril is "
							+ viewOrPrintRateTrace.windDeductibleAOWHFactor.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 14", "Scenario 14 Ended");

			// Verify the DeductibleFactorAOWH value in the Rate Trace is same as the
			// DeductibleFactorAOWH in the rater sheet.
			testData = data.get(data_Value1);
			Assertions.addInfo("Scenario 15",
					" Verify the DeductibleFactorAOWH value in the Rate Trace is same as the DeductibleFactorAOWH in the rater sheet.");
			Assertions.verify(viewOrPrintRateTrace.windDeductibleAOWHFactorValue.getData(),
					testData.get("DeductibleFactorAOWHValue"), "View Or Print Rate Trace Page",
					"The value of DeductibleFactorAOWH value in the rate trace matches with the value in rater sheet(0.9250). Deductible Factor AOWH Value is "
							+ viewOrPrintRateTrace.windDeductibleAOWHFactorValue.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 15", "Scenario 15 Ended");

			// Create a BufferedWriter to write to the CSV file
			String BasePath = EnvironmentDetails.getEnvironmentDetails().getString("CSVFilePath");
			String filename = "SC008.csv";
			String fullPath = BasePath + filename;

			try {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {

					// Write the header
					writer.write("Quote Number,Wind Premium,AOP Premium,Liability Premium,Earthquake Premium");
					writer.newLine();

					// Write data rows
					writer.write(quoteNumber + "," + testData.get("WindPremiumOverride") + ","
							+ testData.get("AOPPremiumOverride") + "," + testData.get("LiabilityPremiumOverride") + ","
							+ testData.get("EarthquakePremiumOverride") + ",");
				}

				Assertions.passTest("Override Premium and Fees Page", "CSV file created successfully");

			} catch (IOException e) {
				e.printStackTrace();
			}

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Clicked on home link");

			// Click on uploadOverride button
			homePage.uplaodOverride.scrollToElement();
			homePage.uplaodOverride.click();
			Assertions.passTest("Home Page", "Clicked on upload override button");

			// click on naho radio button
			overridePremiumAndFeesPage.submit.formatDynamicPath(1).waitTillPresenceOfElement(60);
			overridePremiumAndFeesPage.submit.formatDynamicPath(1).scrollToElement();
			Assertions.verify(overridePremiumAndFeesPage.submit.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override premium and fees page loaded successfully", false,
					false);
			overridePremiumAndFeesPage.NahoRadioButton.scrollToElement();
			overridePremiumAndFeesPage.NahoRadioButton.click();
			Assertions.passTest("Override Premium and Fees Page", "NAHO product selected successfully");

			// upload csv file
			testData = data.get(data_Value1);
			overridePremiumAndFeesPage.csvFileUpload(testData.get("CSVFileUpload"));
			overridePremiumAndFeesPage.submit.formatDynamicPath(2).scrollToElement();
			overridePremiumAndFeesPage.submit.formatDynamicPath(2).click();
			Assertions.verify(overridePremiumAndFeesPage.continueButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "CSV file uploaded successfully", false, false);

			// Getting current premium and new premium
			currentPremium = overridePremiumAndFeesPage.currentPremium.getData().replace("$", "").replace(",", "");
			newPremium = overridePremiumAndFeesPage.newPremium.getData().replace("$", "").replace(",", "");
			windPremiumOverride = testData.get("WindPremiumOverride");
			d_windPremiumOverride = Double.parseDouble(windPremiumOverride);
			aopPremiumOverride = testData.get("AOPPremiumOverride");
			liabilityPremiumOverride = testData.get("LiabilityPremiumOverride");
			d_aopPremiumOverride = Double.parseDouble(aopPremiumOverride);
			d_liabilityPremiumOverride = Double.parseDouble(liabilityPremiumOverride);
			eQPremiumOverride = testData.get("EarthquakePremiumOverride");
			d_eQPremiumOverride = Double.parseDouble(eQPremiumOverride);
			calNewPremium = d_windPremiumOverride + d_aopPremiumOverride + d_liabilityPremiumOverride
					+ d_eQPremiumOverride;

			// Verifying and Asserting Current premium and New Premium
			Assertions.addInfo("Scenario 16", "Verifying and Asserting Current premium and New Premium");
			Assertions.verify(Double.parseDouble(newPremium), calNewPremium, "Override Premium and Fees Page",
					"Calculated and Actual New premium both are same, actual new premium is " + newPremium
							+ " calculated new premium is " + calNewPremium,
					false, false);

			Assertions.verify(Double.parseDouble(currentPremium), calPremium, "Override Premium and Fees Page",
					"Calculated and Actual current premium both are same, actual current premium is " + currentPremium
							+ " calculated current premium is " + calPremium,
					false, false);
			Assertions.addInfo("Scenario 16", "Scenario 16 Ended");

			// click continue button
			overridePremiumAndFeesPage.continueButton.scrollToElement();
			overridePremiumAndFeesPage.continueButton.click();
			Assertions.passTest("Override Premium and Fees Page", "Clicked on continue button");

			// Go to HomePage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search the quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber + " successfullly");

			// Assert and verifying the confirmation message "This quote has a premium
			// adjustment. Please review to determine if the premium adjustment should be
			// reapplied."
			Assertions.addInfo("Scenario 17", "Asserting and verifying the premium adjustment confirmation message");
			Assertions.verify(
					accountOverviewPage.premiumWarningMessage.getData().contains("This quote has a premium adjustment"),
					true, "Account Overview Page", "Premium adjustment message is "
							+ accountOverviewPage.premiumWarningMessage.getData() + " verified",
					false, false);
			Assertions.addInfo("Scenario 17", "Scenario 17 Ended");

			// Getting values after uploading CSV file
			overrideFactor = accountOverviewPage.overrideFactor.getData().replace("$", "").replace(",", "");
			originalPremium = accountOverviewPage.originalPremiumData.getData().replace("$", "").replace(",", "");
			overridePremium = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			actualSLTF = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replace("$", "")
					.replace(",", "");
			actualIcatFees = accountOverviewPage.icatFees.getData().replace("$", "").replace(",", "");
			sltfPercentage = testData.get("SurplusLinesTaxesPercentage");

			// Converting string to double
			d_overrideFactor = Double.parseDouble(overrideFactor);
			d_originalPremium = Double.parseDouble(originalPremium);
			d_overridePremium = Double.parseDouble(overridePremium);
			d_actualSLTF = Double.parseDouble(actualSLTF);
			d_surplusContributionValue = Double.parseDouble(surplusContributionValue);
			d_actualIcatFees = Double.parseDouble(actualIcatFees);
			d_sltfPercentage = Double.parseDouble(sltfPercentage);

			// Calculating OverrideFactor and SLTF
			calOverrideFactor = d_overridePremium / d_originalPremium;
			calSLTF = ((d_overridePremium + d_surplusContributionValue + d_actualIcatFees) * d_sltfPercentage);

			// Verifying and asserting override factor and SLTF after overriding premium
			Assertions.addInfo("Scenario 18",
					"Verifying and asserting override factor,SLTF,original premium and override premium after overriding premium value");
			if (Precision.round(Math.abs(Precision.round(d_overrideFactor, 2) - Precision.round(calOverrideFactor, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Override factor : " + "$" + Math.abs(Precision.round(calOverrideFactor, 2)));
				Assertions.passTest("Account Overview Page", "Actual Override factor : " + "$" + d_overrideFactor);
			} else {
				Assertions.verify(d_overrideFactor, calOverrideFactor, "Account Overview Page",
						"The Difference between actual and calculated Override factor is more than 0.05", false, false);
			}
			if (Precision.round(Math.abs(Precision.round(d_actualSLTF, 2) - Precision.round(calSLTF, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated Surplus Lines Taxes and Fees : " + "$" + Precision.round(calSLTF, 2));
				Assertions.passTest("Account Overview Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_actualSLTF);
			} else {
				Assertions.verify(d_actualSLTF, calSLTF, "Account Overview Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);
			}
			if (Precision.round(Math.abs(Precision.round(d_originalPremium, 2) - Precision.round(d_premium, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Original premium and Previous premium both are same, previous premium: " + "$"
								+ Precision.round(d_premium, 2));
				Assertions.passTest("Account Overview Page", "Original Premium : " + "$" + d_originalPremium);
			} else {
				Assertions.verify(d_originalPremium, d_premium, "Account Overview Page",
						"The Difference between original premium  and previous premium is more than 0.05", false,
						false);
			}

			if (Precision.round(
					Math.abs(
							Precision.round(d_overridePremium, 2) - Precision.round(Double.parseDouble(newPremium), 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Premium and Override premium both are same, override premium is : " + "$"
								+ Precision.round(Double.parseDouble(newPremium), 2));
				Assertions.passTest("Account Overview Page", "Actual premium is : " + "$" + d_overridePremium);
			} else {
				Assertions.verify(d_overridePremium, Double.parseDouble(newPremium), "Account Overview Page",
						"The Difference between actual and override premium is more than 0.05", false, false);
			}
			Assertions.addInfo("Scenario 18", "Scenario 18 Ended");

			// Click o view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view or print full quote link");

			// Getting premium,icatfees,sltf and surplus contribution
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "View print full quote page loaded successfully", false, false);
			vpfqPremium = viewOrPrintFullQuotePage.premiumValue.getData().replace(",", "").replace("$", "");
			vpfqPolicyFee = viewOrPrintFullQuotePage.insurerPolFeeVlaue.getData().replace(",", "").replace("$", "");
			vpfqInspectionFee = viewOrPrintFullQuotePage.insurerInspectionFeeValue.getData().replace(",", "")
					.replace("$", "");
			vpfqSLTF = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace(",", "").replace("$", "");
			vpfqSurplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace(",", "")
					.replace("$", "");

			// Converting string to double
			d_vpfqPremium = Double.parseDouble(vpfqPremium);
			d_vpfqPolicyFee = Double.parseDouble(vpfqPolicyFee);
			d_vpfqInspectionFee = Double.parseDouble(vpfqInspectionFee);
			d_vpfqSLTF = Double.parseDouble(vpfqSLTF);
			d_vpfqSurplusContribution = Double.parseDouble(vpfqSurplusContribution);

			// Calculating SLTF on view print full quote page
			d_vpfqCalSLTF = ((d_vpfqPremium + d_vpfqPolicyFee + d_vpfqInspectionFee + d_vpfqSurplusContribution)
					* d_sltfPercentage);

			// Verifying and asserting sltf and override premium after updating override
			// premium value on view print full quote
			Assertions.addInfo("Scenario 19",
					"Verifying and asserting sltf and override premium after updating override premium value on view print full quote");
			if (Precision.round(Math.abs(Precision.round(d_vpfqSLTF, 2) - Precision.round(d_vpfqCalSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("View Print Full Quote Page",
						"Calculated Surplus Lines Taxes and Fees : " + "$" + Precision.round(d_vpfqCalSLTF, 2));
				Assertions.passTest("View Print Full Quote Page",
						"Actual Surplus Lines Taxes and Fees : " + "$" + d_vpfqSLTF);
			} else {
				Assertions.verify(d_vpfqSLTF, d_vpfqCalSLTF, "View Print Full Quote Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05", false, false);
			}

			if (Precision.round(Math.abs(Precision.round(d_vpfqPremium, 2) - Precision.round(d_overridePremium, 2)),
					2) < 0.05) {
				Assertions.passTest("View Print Full Quote Page",
						"Actual override premium and expected override premium both are same,expected override premium is : "
								+ "$" + Precision.round(d_overridePremium, 2));
				Assertions.passTest("View Print Full Quote Page", "Actual override premium: " + "$" + d_vpfqPremium);
			} else {
				Assertions.verify(d_vpfqPremium, d_overridePremium, "View Print Full Quote Page",
						"The Difference between Actual override premium and expected override premium is more than 0.05",
						false, false);
			}
			Assertions.addInfo("Scenario 19", "Scenario 19 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Asserting premium overridden message;
			accountOverviewPage.noteBarMessage.waitTillPresenceOfElement(60);
			accountOverviewPage.noteBarMessage.waitTillVisibilityOfElement(60);
			accountOverviewPage.noteBarMessage.scrollToElement();
			Assertions.addInfo("Scenario 20", "Asserting premium overridden message on note bar");
			Assertions.verify(accountOverviewPage.noteBarMessage.getData().contains("Premium overridden as follows:"),
					true, "Account Overview Page",
					"The premium overridden message is " + accountOverviewPage.noteBarMessage.getData() + " displayed",
					false, false);
			Assertions.addInfo("Scenario 20", "Scenario 20 Ended");

			// Click on sign out button
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBSCTC008 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBSCTC008 ", "Executed Successfully");
			}
		}
	}

}
