/** Program Description: Validating and asserting the following for Renewal Quote
 * 						1. Producer comments for Cov - E = None and Cov - F = '$5000', roof cladding is 'other' and building is 40yrs older
 *						2. Warning messagge for Coverage(A,B,C,D,E,F), Roof cladding = 'Other' and Building older than 40yrs after create a quote page
 *						3. View or print full Quote page - State specific messages for Insurer supervision message and Insolvency of the surplus lines insurer message
 *						4. View or print full Quote page - Validating SLTF value
 *						5. Request Bing page - Diligence Text message
 *  Author			   : Priyanka S
 *  Date of Creation   : 18/05/2022
 **/

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
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBDETC002 extends AbstractNAHOTest {

	public PNBDETC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/DETC002.xls";
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
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing the variables
		int dataValue1 = 0;
		int dataValue2 = 1;
		String quoteNumber;
		String renewalQuoteNumber;
		String policyNumber;
		String sltfData;
		double actalSltfData;
		Map<String, String> testData = data.get(dataValue1);
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

			// Asserting Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

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

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Asserting Status of Policy
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy status is : " + policySummaryPage.policyStatus.getData(), false, false);

			// Click on Renewal Link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal link successfully ");

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

				// Clicking on expaac link in home page
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				Assertions.passTest("Home Page", "Clicked on Expaac Link");

				// Entering expaac data
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

				// Click on continue button in Renewal building review page
				if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
						&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
					policyRenewalPage.continueRenewal.scrollToElement();
					policyRenewalPage.continueRenewal.click();
					Assertions.passTest("Renewal Building Review Page", "Clicked on Continue");
				}
			}

			// Getting quote number 2
			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overvew Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + quoteNumber);

			// Click on Open referral
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Asserting Producer comments for
			Assertions.addInfo("Scenario 01",
					"Verifying warning message for Cov - E and Cov - F, roof cladding is 'other' and building is 40yrs older");
			Assertions.verify(referralPage.producerCommentsProducerSection.checkIfElementIsDisplayed(), true,
					"Referral Page", referralPage.producerCommentsProducerSection.getData()
							+ " Producer comment is verified successfully",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Pick up Referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}

			// Approve request
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);

			// IO-21792-As part of this our expectation is to validate if the referred quote
			// is assigned to USM not "Holder RMS"
			if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {
				Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true, "Referral Page",
						"Quote is referred to USM " + referralPage.assignedUser.getData(), false, false);
			} else {
				Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false, "Referral Page",
						"Quote is referred to USM " + referralPage.assignedUser.getData(), false, false);
			}
			// Ended
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Approve Referral
			approveDeclineQuotePage.clickOnApprove();

			// Click on Close Button
			if (referralPage.close.checkIfElementIsPresent() && referralPage.close.checkIfElementIsDisplayed()) {
				referralPage.close.scrollToElement();
				referralPage.close.click();
			}

			// Search renewal quote
			homePage.searchQuote(quoteNumber);

			// Click on edit dwelling button
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();

			// Select Lapse renewal in dwelling page
			testData = data.get(dataValue2);
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.modifyDwellingDetailsNAHO(testData);

			// Enter Insured values
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);

			// Click on Get Quote Button
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create A Quote Page", "Clicked on Get A Quote button");

			// Asserting Warning message
			Assertions.addInfo("Scenario 02", "Verifing warning messages displayed for coverages");
			Assertions.verify(createQuotePage.warningMessages.formatDynamicPath("roof age").checkIfElementIsDisplayed(),
					true, "Create Quote Page", "Roof Cladding warning message is verified and displayed as : "
							+ createQuotePage.warningMessages.formatDynamicPath("roof age").getData(),
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("Coverage C limit greater than 70%").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Coverage C limit greater than 70% warning message is verified and displayed as : "
							+ createQuotePage.warningMessages.formatDynamicPath("Coverage C limit greater than 70%")
									.getData(),
					false, false);
			Assertions.verify(
					createQuotePage.coverageEWarningmessage
							.formatDynamicPath("Medical Payments but no limit for Cov E").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Coverage E Warning message is verified and displayed as : "
							+ createQuotePage.coverageEWarningmessage
									.formatDynamicPath("Medical Payments but no limit for Cov E").getData(),
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("Coverage D limit greater than 40%").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Coverage D limit greater than 40% warning message is verified and displayed as : "
							+ createQuotePage.warningMessages.formatDynamicPath("Coverage D limit greater than 40%")
									.getData(),
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("Coverage B limit greater than 50%").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Coverage B limit greater than 50% warning message is verified and displayed as : "
							+ createQuotePage.warningMessages.formatDynamicPath("Coverage B limit greater than 50%")
									.getData(),
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("The quoted building has a Coverage A limit of more than $3000000")
							.checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Coverage A limit of more than $3,000,000 warning message is verified and displayed as : "
							+ createQuotePage.warningMessages
									.formatDynamicPath(
											"The quoted building has a Coverage A limit of more than $3000000")
									.getData(),
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("quoted building being 40 years").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Quoted building is 40 years warning message is verified and displayed as : "
							+ createQuotePage.warningMessages.formatDynamicPath("quoted building being 40 years")
									.getData(),
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("online underwriter").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Online underwriter reason for Lapse warning message is verified and displayed as : "
							+ createQuotePage.warningMessages.formatDynamicPath("online underwriter").getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on continue
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Click on view or print full quote link
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Asserting the State specific messages
			Assertions.addInfo("Scenario 03",
					"Verifying state specific messages displayed in vie or print full quote page");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Or Print Full Quote Page", "View Or Print Full Quote Page Loaded successfully", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.stateSpecificwords
							.formatDynamicPath("the broker places").checkIfElementIsDisplayed(),
					true, "View Or Print full Quote Page",
					"Insurer supervision message is verified and displayed as : "
							+ viewOrPrintFullQuotePage.stateSpecificwords.formatDynamicPath("the broker places")
									.getData(),
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.stateSpecificwords
							.formatDynamicPath("insolvency of the surplus lines insurer").checkIfElementIsDisplayed(),
					true, "View Or Print full Quote Page",
					"Insolvency of the surplus lines insurer message is verified and displayed as : "
							+ viewOrPrintFullQuotePage.stateSpecificwords
									.formatDynamicPath("insolvency of the surplus lines insurer").getData(),
					false, false);
			Assertions.addInfo("scenario 03", "Scenario 03 Ended");

			// Validating SLTF value - 3% of Total Premium (Premium + Policy Fee +
			// Inspection Fee)
			viewOrPrintFullQuotePage.surplusLinesTaxesValue.scrollToElement();
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			String inspectionValue = viewOrPrintFullQuotePage.inspectionFeeNaho.getData().replace("$", "").replace(",", "");
			String policyValue = viewOrPrintFullQuotePage.policyFeeNaho.getData().replace("$", "").replace(",", "");
			// Commenting due to Reciprocal being Unavailable for Renewals, please uncomment
			// once Reciprocal is back for Renewals
			String surplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "").replace("%", "");

			double sltfFee = Double.parseDouble(premiumValue) + Double.parseDouble(inspectionValue)
					+ Double.parseDouble(policyValue) + Double.parseDouble(surplusContributionValue);
//		double sltfFee = (double) (Double.parseDouble(premiumValue) + Double.parseDouble(inspectionValue) + Double.parseDouble(policyValue));
			double sltfFeeValue = sltfFee * 3 / 100;

			// Click on Go Back Button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Asserting SLTF Value
			sltfData = accountOverviewPage.sltfValue.getData().replace("$", "");
			actalSltfData = Double.parseDouble(sltfData);
			Assertions.addInfo("Scenario 04", "Calculating and verifying SLTF value in View or print full quote page");
			if (Precision.round(Math.abs(Precision.round(actalSltfData, 2) - Precision.round(sltfFeeValue, 2)),
					2) < 0.05) {
				Assertions.passTest("View print full quote page",
						"Calculated surplus lines taxes and fees : " + "$" + Precision.round(sltfFeeValue, 2));
				Assertions.passTest("View print full quote page",
						"Actual surplus lines taxes and fees : " + "$" + actalSltfData);
			} else {
				Assertions.verify(actalSltfData, sltfFeeValue, "View print full quote page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			Assertions.passTest("Account Overview page", "Quote Number : "
					+ accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12));

			// Click on Release renewal to producer button
			renewalQuoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release renewal to producer Button");

			// Click on issue Quote button
			if (accountOverviewPage.issueQuoteButton.checkIfElementIsPresent()
					&& accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed()) {
				accountOverviewPage.issueQuoteButton.scrollToElement();
				accountOverviewPage.issueQuoteButton.click();
				Assertions.passTest("Account Overview Page", "Clicked on Issue Quote Button");
			}

			// Click on Request Bind
			accountOverviewPage.clickOnRenewalRequestBind(testData, renewalQuoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Asserting Diligence message
			Assertions.addInfo("Scenario 05", "Verifying diligence text message in request bind page");
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page Loaded successfully", false, false);
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Diligence Text message is verified and displayed as :" + requestBindPage.diligenceText.getData(),
					false, false);
			requestBindPage.renewalRequestBindNAHO(testData);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Asserting the policy Status
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);
			Assertions.verify(policySummaryPage.policyNumber.getData().endsWith("01"), true, "Policy Summary Page",
					"Policy Number ends with 01 is verified successfully", false, false);
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Status is : " + policySummaryPage.policyStatus.getData(), false, false);

			// Sign out as Usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBDETC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBDETC002 ", "Executed Successfully");
			}
		}
	}
}
