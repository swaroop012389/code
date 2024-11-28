package com.icat.epicenter.test.naho.regression.ISPNB;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC068_PNBREN002 extends AbstractNAHOTest {

	public TC068_PNBREN002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBREN002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
		ViewOrPrintFullQuotePage viewprintFullQuotePage = new ViewOrPrintFullQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		DecimalFormat df = new DecimalFormat("0.00");

		// Initializing the variables
		String premiumAmount;
		String fees;
		double surplusTax;
		double expectedsltf;
		double expectedStampingFee;
		String quoteNumber;
		String policyNumber;
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
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting quote number 1
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// clicking on request bind in Account overview page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Bind Referral
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Referral page
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			requestBindPage.approveRequestNAHO(testData);

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Click on Renewal Indicators link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// Selecting UW review check box
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);

			renewalIndicatorsPage.underwritingReview.select();
			renewalIndicatorsPage.underwritingReviewInternalComments.setData(testData.get("UnderWritingReviewComment"));
			Assertions.passTest("Renewal Indicators Page", "Underwriting Review check box is selected successfully");

			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update button");

			// click on renew policy link
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");

			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link successfully");

			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				Assertions.verify(policySummaryPage.expaacMessage.checkIfElementIsDisplayed(), true,
						"Policy Renewal Page", policySummaryPage.expaacMessage.getData() + " Message verified", false,
						false);

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// clicking on expaac link in home page
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				Assertions.passTest("Home Page", "Clicked on Expaac Link");

				// entering expaac data
				Assertions.verify(expaccInfoPage.policyNumber.checkIfElementIsDisplayed(), true, "Expacc Info Page",
						"Update Expaac Data page loaded successfully", false, false);
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc Info Page", "Expaac Data updated successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// Find the policy by entering policy Number
				homePage.searchPolicy(policyNumber);
				Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

				// Click on Renew Policy Hyperlink
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

				// click on continue button in Renewal building review page
				if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
						&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
					policyRenewalPage.continueRenewal.scrollToElement();
					policyRenewalPage.continueRenewal.click();
					Assertions.passTest("Renewal Building Review Page", "Clicked on Continue");
				}
			}

			// verifying the Status of the renewal offer is Referred and click on open
			// referral link
			Assertions.verify(accountOverviewPage.openReferral.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			Assertions
					.verify(accountOverviewPage.referredStatus.checkIfElementIsDisplayed(), true,
							"Account Overview Page", "The Renewal Offer status "
									+ accountOverviewPage.referredStatus.getData() + " displayed is verified",
							false, false);

			// getting quote number 2
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + quoteNumber);

			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve the Referral
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// verifying referral complete message
			Assertions.verify(referralPage.referralCompleteMsg.checkIfElementIsDisplayed(), true, "Referral Page",
					referralPage.referralCompleteMsg.getData() + " message is verified successfully", false, false);
			referralPage.close.scrollToElement();
			referralPage.close.click();

			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Quote : " + quoteNumber + " successfully");

			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Verifying sltf is displayed in account overview page
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsPresent(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// getting premium amount
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// getting fees value
			fees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			// surplusContributionValue =
			// accountOverviewPage.surplusContibutionValue.getData().replaceAll("[^\\d-.]",
			// "");

			// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
			// percentage 0.04
			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			df.setRoundingMode(RoundingMode.UP);
			// Comparing actual and expected SLTF value and printing calculated value
			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(accountOverviewPage.sltfValue.getData().replace("$", "")), 2)
							- Precision.round(surplusTax, 2)),
					2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Fees : " + "$" + Precision.round(surplusTax, 2));
				Assertions.passTest("Account Overview Page",
						"Actual SLTF Fees: " + accountOverviewPage.sltfValue.getData());
			} else {
				Assertions.passTest("Account Overview Page",
						"The Difference between actual SLTF Fees and calculated SLTF Fees is more than 0.05");
			}

			// click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");

			testData = data.get(dataValue2);
			// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
			// percentage 4.85%
			expectedsltf = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// verifying surplus fees and broker fees in view/print full quote page
			if (Precision.round(Math.abs(Precision.round(
					Double.parseDouble(viewprintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "")), 2)
					- Precision.round(expectedsltf, 2)), 2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated SLTF Fees : " + "$" + Precision.round(expectedsltf, 2));
				Assertions.passTest("View/Print Full Quote Page",
						"Actual SLTF Fees: " + viewprintFullQuotePage.surplusLinesTaxesValue.getData());
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual SLTF Fees and calculated SLTF Fees is more than 0.05");
			}

			testData = data.get(dataValue1);
			// fetching stamping fee percentage
			String stampingsltfPercentage_VPFQ = testData.get("StampingFeePercentage");
			double d_stampingsltfPercentage_VPFQ = Double.parseDouble(stampingsltfPercentage_VPFQ);

			// calculating stamping fee by adding premium and icat fees and multiplied by
			// stamping fee percentage 0.00075
			expectedStampingFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees))
					* d_stampingsltfPercentage_VPFQ;
			if (Precision.round(Math.abs(Precision
					.round(Double.parseDouble(viewprintFullQuotePage.stampingFeeValue.getData().replace("$", "")), 2)
					- Precision.round(expectedStampingFee, 2)), 2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated Stamping Fees : " + "$" + Precision.round(expectedStampingFee, 2));
				Assertions.passTest("View/Print Full Quote Page",
						"Actual Stamping Fees : " + viewprintFullQuotePage.stampingFeeValue.getData());
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual Stamping Fees and calculated Stamping Fees is more than 0.05");
			}
			df.setRoundingMode(RoundingMode.DOWN);

			viewprintFullQuotePage.backButton.click();

			// click on edit deductibles and limits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Deductibles and Limits");

			// Entering Create quote page Details
			testData = data.get(dataValue2);
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting quote number 3
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// Verifying Bind button is not available for quote which is not issued
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsPresent(), false, "Account Overview Page",
					"Bind button is not available for quote which is not issued is verified", false, false);

			// click on release to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer successfully");

			// click on issue quote
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Issue Quote successfully");

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Bind Button is available after quote is issued", false, false);

			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			testData = data.get(dataValue1);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			requestBindPage.floodCoverageArrow.scrollToElement();
			requestBindPage.floodCoverageArrow.click();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy"))
					.scrollToElement();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy")).click();
			Assertions.passTest("Request Bind Page", "Selected the Flood as No");

			requestBindPage.addContactInformation(testData);

			if (requestBindPage.submit.checkIfElementIsPresent()
					&& requestBindPage.submit.checkIfElementIsDisplayed()) {
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
			}

			if (confirmBindRequestPage.requestBind.checkIfElementIsPresent()) {
				Assertions.passTest("Confirm Bind Request Page", "Confirm Bind Request page loaded successfully");
				confirmBindRequestPage.confirmBind();
				Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
			}

			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.scrollToElement();
				requestBindPage.overrideEffectiveDate.select();
				requestBindPage.waitTime(2);
			}

			if (requestBindPage.submit.checkIfElementIsPresent()
					&& requestBindPage.submit.checkIfElementIsDisplayed()) {
				requestBindPage.scrollToBottomPage();
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
			}

			if (confirmBindRequestPage.requestBind.checkIfElementIsPresent()) {
				confirmBindRequestPage.confirmBind();
			}

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. The Renewal PolicyNumber is : " + policyNumber, false,
					false);

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC068 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC068 ", "Executed Successfully");
			}
		}
	}

}
