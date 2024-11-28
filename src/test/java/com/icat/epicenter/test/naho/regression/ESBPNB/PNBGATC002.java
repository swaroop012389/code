/** Program Description : 1.Verifying SLTF Values on Quote Document for NB and Renewal Quote
 * 2. Verifying Diligence check box and wordings on Renewal Request bind page
 * 3.Verifying the "NOTICE OF COVERAGE REDUCTION" when selecting coverage change option under renewal indicators and Added IO-19950
 *  Author			    : Sowndarya NH
 *  Date of Creation    : 26/05/2022
 **/

package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBGATC002 extends AbstractNAHOTest {

	public PNBGATC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/GATC002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		// Initializing the variables
		int dataValue1 = 0;
		String quoteNumber;
		String renewalQuoteNumber;
		String policyNumber;
		String renewalPolicyNumber;
		Map<String, String> testData = data.get(dataValue1);
		DecimalFormat df = new DecimalFormat("0.00");
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

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
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Getting the Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page loaded successfully", false, false);

			// Getting premium,inspection and policy fee values
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			String policyFee = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "");
			String inspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(",", "");
			String actualSltf = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");
			String surplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
					.replace("%", "");

			// Converting String values to double
			double d_premiumVPFQ = Double.parseDouble(premiumValue);
			double d_inspecionFeeVPFQ = Double.parseDouble(inspectionFee);
			double d_policyFeeVPFQ = Double.parseDouble(policyFee);
			double d_actualSltf = Double.parseDouble(actualSltf);
			double d_surplusContribution = Double.parseDouble(surplusContribution);

			// Getting sltf percentage
			String sltfPercentage = testData.get("SLTFPercentage");

			// Calculating sltf
			double d_sltf = (d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ + d_surplusContribution);
			double d_calcSltf = d_sltf * Double.parseDouble(sltfPercentage);

			// Verifying calculated and actual sltf values
			Assertions.addInfo("Scenario 01",
					"Verifying the Actual and Calculated SLTF values on View Print Full Quote Page");
			if (Precision.round(Math.abs(Precision.round(d_calcSltf, 2) - Precision.round(d_actualSltf, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Actual SLTF Value :" + "$" + d_actualSltf);
				Assertions.passTest("View/Print Full Quote Page", "Calculated SLTF Value :" + "$" + d_calcSltf);
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual  and calculated SLTF is more than 0.05");
			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on go back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Click on Renewal indicators link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// Select Coverage Change Checkbox
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page Loaded successfully", false, false);
			renewalIndicatorsPage.coverageChange.scrollToElement();
			renewalIndicatorsPage.coverageChange.select();
			Assertions.passTest("Renewal Indicators Page", "De-Selected the coverage change checkbox");

			// Verifying the NOTICE OF COVERAGE REDUCTION wordings
			Assertions.addInfo("Scenario 02",
					"Verifying the NOTICE OF COVERAGE REDUCTION wordings after selecting coverage change checkbox");
			Assertions.verify(
					renewalIndicatorsPage.coverageChangeLegalNoticeWording.getData()
							.contains("NOTICE OF COVERAGE REDUCTION"),
					true, "Renewal Indicators Page",
					"The Legal Notice wording " + renewalIndicatorsPage.coverageChangeLegalNoticeWording.getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// click on update
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update Button");
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);

			// Asserting the soft-Non Renewal Auto-Indicator on Policy Summary Page
			Assertions.addInfo("Policy Summary Page", "Verifying the Auto Non Renewal status as Soft Non-Renewal");
			Assertions.verify(policySummaryPage.autoRenewalIndicators.getData().contains("Soft Non-Renewal"), true,
					"Policy Summary Page",
					"Verifying the Auto-Renew status changed to: " + policySummaryPage.autoRenewalIndicators.getData(),
					false, false);

			// De-selecting the Coverage Change under Renewal indicator Page
			// Click on Renewal indicators link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// Select Coverage Change Checkbox
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page Loaded successfully", false, false);
			renewalIndicatorsPage.coverageChange.scrollToElement();
			renewalIndicatorsPage.coverageChange.select();
			Assertions.passTest("Renewal Indicators Page", "De-Selected the coverage change checkbox");

			// click on update
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update Button");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Click on Expacc link
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			Assertions.passTest("Home Page", "Clicked on Expacc link");
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Performing Renewal Searches
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the Policy " + policyNumber + " successfully");

			// clicking on renewal policy link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy link");

			// Getting renewal quote number
			Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			renewalQuoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + renewalQuoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page loaded successfully", false, false);

			// Getting premium,inspection and policy fee values
			String premiumValueRnl = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			String policyFeeRnl = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "");
			String inspectionFeeRnl = viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(",",
					"");
			String actualSltfRnl = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");

			// Converting String values to double
			double d_premiumVPFQRnl = Double.parseDouble(premiumValueRnl);
			double d_inspecionFeeVPFQRnl = Double.parseDouble(inspectionFeeRnl);
			double d_policyFeeVPFQRnl = Double.parseDouble(policyFeeRnl);
			double d_actualSltfRnl = Double.parseDouble(actualSltfRnl);

			// Getting sltf percentage
			String sltfPercentageRnl = testData.get("SLTFPercentage");

			// Calculating sltf
			double d_sltfRnl = (d_premiumVPFQRnl + d_inspecionFeeVPFQRnl + d_policyFeeVPFQRnl);
			double d_calcSltfRnl = d_sltfRnl * Double.parseDouble(sltfPercentageRnl);

			// Verifying calculated and actual sltf values
			Assertions.addInfo("Scenario 03",
					"Verifying the Actual and Calculated SLTF values on View Print Full Quote Page for Renewal Quote");
			if (Precision.round(Math.abs(Precision.round(d_calcSltfRnl, 2) - Precision.round(d_actualSltfRnl, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Actual SLTF Value :" + "$" + d_actualSltfRnl);
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated SLTF Value :" + "$" + df.format(d_calcSltfRnl));
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual  and calculated SLTF is more than 0.05");
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on go back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Assert renewal quote number
			Assertions.verify(
					accountOverviewPage.quoteNumber.checkIfElementIsPresent()
							&& accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote number is : " + renewalQuoteNumber);
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {

				// Opening the Referral from Account Overview Page
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();
				Assertions.passTest("Account Overview Page", "Referral is Openned");

				// Approve the referral
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

				// Click on close
				if (referralPage.close.checkIfElementIsPresent() && referralPage.close.checkIfElementIsDisplayed()) {
					referralPage.close.scrollToElement();
					referralPage.close.click();
				}

				// Search for quote number
				homePage.searchQuote(renewalQuoteNumber);
			}

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release renewal to producer button");

			// Click on request bind
			accountOverviewPage.clickOnRenewalRequestBind(testData, renewalQuoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");

			// Enter bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Verify the presence of due diligence checkbox and text
			Assertions.addInfo("Scenario 04",
					"Verifying the Due diligence checkbox and wordings on renewal request bind page");
			Assertions.verify(requestBindPage.dueDiligenceCheckbox.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "Diligence Check Box present is verified", false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("A minimum 25%").checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"The Wordings " + requestBindPage.dueDiligenceText.formatDynamicPath("A minimum 25%").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("Completed and signed application").checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"The Wordings " + requestBindPage.dueDiligenceText
							.formatDynamicPath("Completed and signed application").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("diligent effort form").checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"The Wordings "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("diligent effort form").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Enter Renewal Bind Details
			requestBindPage.renewalRequestBindNAHO(testData);
			Assertions.passTest("Request Bind Page", "Entered the renewal request bind details successfully");

			// Asserting renewal policy number
			renewalPolicyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. The Renewal PolicyNumber is : " + renewalPolicyNumber,
					false, false);

			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBGATC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBGATC002 ", "Executed Successfully");
			}
		}
	}
}
