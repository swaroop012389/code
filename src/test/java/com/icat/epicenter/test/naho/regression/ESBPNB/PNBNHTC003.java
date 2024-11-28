/*Program Description: Asserting Following:
 * 1. SLTF Value on view print full quote page for renewal quote
 * 2. Referral message on renewal quote when  Roof Material = Tile Or Clay,Year of Construction = 2001 and Coverage A = $1,500,000
 * 3. Referral message on renewal quote when Short Term Rental as “Yes”,Coverage A as “1,000,000” and Coverage E as “1,000,000”
 * 4. Due diligence text words on renewal request bind page
 * Added ticket IO-20287
 * Author            : Pavan Mule
 * Date of Creation  : 18-05-2022
 */

package com.icat.epicenter.test.naho.regression.ESBPNB;

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
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBNHTC003 extends AbstractNAHOTest {

	public PNBNHTC003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/NHTC003.xls";
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
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing Variables
		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue2 = 1;
		testData = data.get(dataValue1);
		String quoteNumber;
		String policyNumber;
		int quoteLen;
		String actualPremium;
		String actualPolicyFees;
		String actualSLTF;
		double d_actualPremium;
		double d_actualPolicyFees;
		double d_actualSLTF;
		String SLTFPercentage;
		double d_SLTFPercentage;
		double calculatedSLTF;
		String actualSCValue;
		double d_actualSCValue;
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

			// Adding below code for IO-20287
			// Asserting minimum deductibles message in note bar when Cov A = 1200000 and
			// AOP = 1000Override
			waitTime(3);// adding wait time to load the note bar
			Assertions.addInfo("Policy Summary Page", "Asserting deductible message");
			policySummaryPage.noteBarMessage.formatDynamicPath("This policy has deductibles below the minimum.")
					.waitTillPresenceOfElement(60);
			policySummaryPage.noteBarMessage.formatDynamicPath("This policy has deductibles below the minimum.")
					.scrollToElement();
			Assertions.verify(
					policySummaryPage.noteBarMessage.formatDynamicPath("This policy has deductibles below the minimum.")
							.checkIfElementIsPresent()
							&& policySummaryPage.noteBarMessage
									.formatDynamicPath("This policy has deductibles below the minimum.")
									.checkIfElementIsDisplayed(),
					policySummaryPage.noteBarMessage.formatDynamicPath("This policy has deductibles below the minimum.")
							.getData().contains("This policy has deductibles below the minimum."),
					"Policy Summary Page",
					"The Deductible message is "
							+ policySummaryPage.noteBarMessage
									.formatDynamicPath("This policy has deductibles below the minimum").getData()
							+ " displayed",
					false, false);
			waitTime(2);// adding wait time to load the note bar
			// Ended

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

			// Assert renewal quote number
			Assertions.verify(
					accountOverviewPage.openReferral.checkIfElementIsPresent()
							&& accountOverviewPage.openReferral.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote number is : " + quoteNumber);

			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.waitTillButtonIsClickable(60);
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Checking referral messages on referral page
			// The account is ineligible due to the roof age being outside of ICATâ€™s
			// guidelines. If you have additional information that adjusts the roof age,
			// please email it to your ICAT Online Underwriter.â€�
			Assertions.addInfo("Scenario 01", "Asserting referral messages");
			Assertions.verify(
					referralPage.producerCommentsProducerSection.checkIfElementIsPresent()
							&& referralPage.producerCommentsProducerSection.checkIfElementIsDisplayed(),
					referralPage.producerCommentsProducerSection.getData()
							.contains("the roof age being outside of ICAT's"),
					"Referral Page", "Referral Message is " + referralPage.producerCommentsProducerSection.getData(),
					false, false);
			Assertions.addInfo("Scenario 01 ", "Scenario 01 Ended");

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
			homePage.searchQuote(quoteNumber);

			// Click on edit dwelling details link
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Adding below code for IO-20287
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			// Asserting minimum deductible AOP is = $2,500
			Assertions.addInfo("Create Quote Page", "Asserting minimum deductible AOP value");
			Assertions.verify(
					createQuotePage.aopDeductibleData.checkIfElementIsPresent()
							&& createQuotePage.aopDeductibleData.checkIfElementIsDisplayed(),
					createQuotePage.aopDeductibleData.getData().contains("$5,000"), "Create Quote Page",
					"The Minimum deductible is " + createQuotePage.aopDeductibleData.getData() + " displayed", false,
					false);

			// Click on aop deductible arrow
			createQuotePage.aopDeductibleArrow.click();
			createQuotePage.aopDeductibleOption.formatDynamicPath("$1,000 (Override)").scrollToElement();
			createQuotePage.aopDeductibleOption.formatDynamicPath("$1,000 (Override)").click();
			Assertions.passTest("Create Quote Page",
					"Renewal quote is able to enter min aop deductible value is verified");
			Assertions.verify(
					createQuotePage.aopDeductibleData.checkIfElementIsPresent()
							&& createQuotePage.aopDeductibleData.checkIfElementIsDisplayed(),
					createQuotePage.aopDeductibleData.getData().contains("$1,000 (Override)"), "Create Quote Page",
					"The Minimum deductible is " + createQuotePage.aopDeductibleData.getData() + " displayed", false,
					false);
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			// Click edit building link
			accountOverviewPage.editBuilding.scrollToElement();
			accountOverviewPage.editBuilding.click();
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);

			// Changing short term rental = yes, Cov A = 1,000,000 and Cov E = 1,000,000
			Assertions.passTest("Dwelling Page ",
					"Original Short term rental is " + testData.get("L1D1-DwellingShortTermRental"));
			Assertions.passTest("Dwelling Page", "Original Cov A value is " + testData.get("L1D1-DwellingCovA"));

			testData = data.get(dataValue2);
			dwellingPage.shortTermRentalYes.scrollToElement();
			dwellingPage.shortTermRentalYes.click();
			dwellingPage.continueWithUpdateBtn.waitTillPresenceOfElement(60);
			dwellingPage.continueWithUpdateBtn.scrollToElement();
			dwellingPage.continueWithUpdateBtn.click();
			dwellingPage.dwellingValuesLink.scrollToElement();
			dwellingPage.dwellingValuesLink.click();
			dwellingPage.coverageADwelling.scrollToElement();
			dwellingPage.coverageADwelling.clearData();
			dwellingPage.coverageADwelling.appendData(testData.get("L1D1-DwellingCovA"));
			dwellingPage.coverageADwelling.tab();
			dwellingPage.coverageEArrow.scrollToElement();
			dwellingPage.coverageEArrow.click();
			dwellingPage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE")).scrollToElement();
			dwellingPage.coverageEOption.formatDynamicPath(testData.get("L1D1-DwellingCovE")).click();
			Assertions.passTest("Dwelling Page",
					"Latest short term rental is " + testData.get("L1D1-DwellingShortTermRental"));
			Assertions.passTest("Dwelling Page", "Latest Cov A values is " + testData.get("L1D1-DwellingCovA"));
			Assertions.passTest("Dwelling Page", "Latest Cov E values is " + testData.get("L1D1-DwellingCovE"));

			// Click on Review button
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			if (dwellingPage.override.checkIfElementIsPresent() && dwellingPage.override.checkIfElementIsDisplayed()) {
				dwellingPage.override.scrollToElement();
				dwellingPage.override.click();
			}

			// Click on create quote button
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Entering renewal Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create quote page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote page", "Quote details entered successfully");

			// Asserting waring message “This is a tenant occupied or short-term rental
			// property with Coverage E limit of at least $500,000 and requires further
			// review by an ICAT Online Underwriter.”
			// “This renewal requote decreases the Coverage A limit and requires further
			// review by an ICAT Online Underwriter.
			// Please provide additional information regarding the reason for the decrease.”
			Assertions.addInfo("Scenario 02", "Asserting warning message");
//		Assertions.verify(
//				!!createQuotePage.warningMessages
//						.formatDynamicPath("Coverage E limit greater").checkIfElementIsDisplayed(),
//				true, "Create Quote Page",
//				"Short term rental warning message is not displayed",
//				false, false);
			Assertions.verify(createQuotePage.warningMessages
					.formatDynamicPath("renewal requote decreases the Coverage A limit").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Renewal requote Coverage A warning message is " + createQuotePage.warningMessages
							.formatDynamicPath("renewal requote decreases the Coverage A limit").getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Assert renewal quote number 2
			Assertions.verify(
					accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsPresent()
							&& accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account overview page", "Renewal Quote number2 is : " + quoteNumber);

			// Click on view/print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "View Print Full Quote Page loaded successfully", false, false);

			// Getting actual SLTF Value,Premium and Policy Fees
			actualPremium = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			actualPolicyFees = viewOrPrintFullQuotePage.policyFeeNaho.getData().replace("$", "").replace(",", "");
			actualSLTF = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace("$", "").replace(",", "");
			actualSCValue = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "").replace("%", "");
			SLTFPercentage = testData.get("SLTFPercentage");
			d_actualPremium = Double.parseDouble(actualPremium);
			d_actualPolicyFees = Double.parseDouble(actualPolicyFees);
			d_actualSLTF = Double.parseDouble(actualSLTF);
			d_SLTFPercentage = Double.parseDouble(SLTFPercentage);
			d_actualSCValue = Double.parseDouble(actualSCValue);
			calculatedSLTF = (d_actualPremium + d_actualPolicyFees + d_actualSCValue) * d_SLTFPercentage;
			Assertions.passTest("View/Print Full Quote Page", "Actual Premium Value " + d_actualPremium);
			Assertions.passTest("View/Print Full Quote Page", "Actual Policy Fees " + d_actualPolicyFees);
			Assertions.passTest("View/Print Full Quote Page", "Actual Surplus Contribution Value " + d_actualSCValue);

			// Verifying Actual SLTF and Calculated SLTF Values on View/Print full quote
			Assertions.addInfo("Scenario 03",
					"Verifying actual SLTF and calculated SLTF values View/Print full quote page");
			if (Precision.round(Math.abs(Precision.round(d_actualSLTF, 2) - Precision.round(calculatedSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated surplus lines taxes and fees : " + "$" + Precision.round(calculatedSLTF, 2));
				Assertions.passTest("View/Print Full Quote Page",
						"Actual surplus lines taxes and fees : " + "$" + d_actualSLTF);
			} else {
				Assertions.verify(d_actualSLTF, calculatedSLTF, "View/Print Full Quote Page",
						"The Difference between actual SLTF value Fund and calculated SLTF value is more than 0.05",
						false, false);
			}
			Assertions.passTest("Scenario 03", "Scenario 03 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on Release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			accountOverviewPage.requestBind.scrollToElement();
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request bind button successfully");

			// Verifying presence of A minimum 25% down payment (if not mortgagee billed)
			// Completed and signed application
			// Completed and signed diligent effort form is displayed correctly.
			Assertions.addInfo("Scenario 04",
					"Verifying presence of A minimum 25% down payment (if not mortgagee billed),Completed and signed application,Completed and signed diligent effort form");
			Assertions.verify(
					requestBindPage.diligenceText.checkIfElementIsPresent()
							&& requestBindPage.diligenceText.checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"Diligence Text Message is " + requestBindPage.diligenceText.getData() + " Displayed", false,
					false);
			Assertions.passTest("Scenario 04 ", "Scenario 04 Ended");

			// Entering renewal bind details
			requestBindPage.renewalRequestBindNAHO(testData);
			Assertions.passTest("Request Bind Page", "Renewal bind details entered successfully");

			// Getting renewal policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy summary page",
					"Policy summary page loaded successfully. Renewal PolicyNumber is : " + policyNumber, false, false);

			// Close the browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBNHTC003 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBNHTC003 ", "Executed Successfully");
			}
		}
	}

}
