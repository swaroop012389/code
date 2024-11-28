/** Program Description: Validating and asserting the following for Renewal Quote
 * 						1. Producer comments for roof age outside of ICAT’s guidelines in Referral Page
 * 						2. Hardstop Message for year built prior to 1978, occupancy = tenant,Coverage A = $149,999
 *  Author			   : Priyanka S
 *  Date of Creation   : 20/05/2022
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
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBRITC002 extends AbstractNAHOTest {

	public PNBRITC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/RITC002.xls";
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
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		String quoteNumber;
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
			Assertions.addInfo("Scenario 01", "Verifying warning message for roof age outside of ICAT’s guidelines");
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
			referralPage.clickOnApprove();

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

			// Update Year built = 1977 , Occupied by = Tenant and Cov A = $149,999
			testData = data.get(dataValue2);
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			if (dwellingPage.yearBuilt.checkIfElementIsPresent()
					&& dwellingPage.yearBuilt.checkIfElementIsDisplayed()) {
				dwellingPage.yearBuilt.clearData();
				dwellingPage.continueWithUpdateBtn.waitTillVisibilityOfElement(60);
				dwellingPage.continueWithUpdateBtn.scrollToElement();
				dwellingPage.continueWithUpdateBtn.click();
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			}

			if (dwellingPage.OccupiedByArrow.checkIfElementIsPresent() && !testData.get("L1D1-OccupiedBy").equals("")) {
				dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
				dwellingPage.OccupiedByArrow.scrollToElement();
				dwellingPage.OccupiedByArrow.click();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).scrollToElement();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).click();
			}

			// Click on get quote button
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					&& dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			// Asserting global Hardstop message for Year built prior to 1978 and
			// Cov A less that minimum value
			Assertions.addInfo("Scenario 02",
					"Verifying global hardstop message for Year built prior to 1978 and Cov A less that minimum value");
			Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Hardstop message is verified and displayed as : " + dwellingPage.protectionClassWarMsg.getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Update Year built = 2000
			testData = data.get(dataValue3);
			Assertions.verify(dwellingPage.reSubmit.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			if (dwellingPage.yearBuilt.checkIfElementIsPresent()
					&& dwellingPage.yearBuilt.checkIfElementIsDisplayed()) {
				dwellingPage.yearBuilt.clearData();
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
			}

			// Click on create quote button
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					&& dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			// Click on get a Quote Button
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on get a Quote Button
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Asserting global Hardstop message for Cov A less that minimum value
			Assertions.addInfo("Scenario 03", "Verifying global hardstop message for Cov A less that minimum value");
			Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Hardstop message is verified and displayed as : " + createQuotePage.globalErr.getData(), false,
					false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on previous button
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.passTest("Create A Quote Page", "Clicked on Previous Button");

			// Click on Edit dwelling in Account overview page
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();

			// Click on get quote button
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					&& dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			// Click on get a Quote Button
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Asserting waring message for roof age being outside of ICAT's
			// guidelines
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				Assertions.addInfo("Scenario 04", "Verifying and asserting warning message for Roof age");
				Assertions.verify(
						createQuotePage.warningMessages
								.formatDynamicPath("roof age outside").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"Warning messsage for roof age is verified and dispalyed as : "
								+ createQuotePage.warningMessages.formatDynamicPath("roof age outside").getData(),
						false, false);
				Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				Assertions.passTest("create A quote page", "Clicked on continue button");
			}

			// Click on override premium button
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Override Premium Link");

			// Update new values for Premium , Inspection fee and Policy fee, Add
			// Justification comments
			testData = data.get(dataValue2);
			Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees Page Loaded successfully", false,
					false);
			overridePremiumAndFeesPage.enterFeesDetailsNAHO(testData);
			Assertions.passTest("Override Premium and Fees Page", "Entered details successfully");

			// Click on view or print full quote link
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			// Validating SLTF value - 4% of Total Premium
			viewOrPrintFullQuotePage.surplusLinesTaxesValue.scrollToElement();
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");

			// Commenting as Reciprocal is not available for Renewal
			String surplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "").replace("%", "");

			double sltfFee = Double.parseDouble(premiumValue) + (Double.parseDouble(surplusContributionValue));
			// double sltfFee = (double) (Double.parseDouble(premiumValue));
			double sltfFeeValue = sltfFee * 4 / 100;

			// Asserting presence of Insurer policy fee and Insurer Inspection Fee
			// in coverage section
			Assertions.addInfo("Scenario 05",
					"Verifying the presence of Insurer Policy and Insurer Inspection Header in View or print full quote page");
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Insurer Policy")
							.checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Insurer Policy Fee header is dispalyed in coverages and premium section", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("Insurer Inspection")
							.checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Insurer Inspection Fee header is dispalyed in coverages and premium section", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Asserting presence of Named Hurricane in Deductibles section
			Assertions.addInfo("Scenario 06", "Verifying the presence of Named Hurricane text in Deductibles Section");
			Assertions.verify(
					viewOrPrintFullQuotePage.nhAndAOPValue.formatDynamicPath("Named Hurricane")
							.checkIfElementIsDisplayed(),
					true, "View print full quote page", "Named Hurrricane is dispalyed in Deductibles section", false,
					false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Asserting presence of Affidavit section and Header
			Assertions.addInfo("Scenario 07", "Verifying the presence of Affidavit section");
			viewOrPrintFullQuotePage.affidavitScetion.scrollToElement();
			Assertions.verify(viewOrPrintFullQuotePage.affidavitScetion.checkIfElementIsDisplayed(), true,
					"View print full quote page", "Affidavit Section is dispalyed", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.headerValue
							.formatDynamicPath("AFFIDAVIT BY INSURED").checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Affidavit header is verified and dispalyed as : "
							+ viewOrPrintFullQuotePage.headerValue.formatDynamicPath("AFFIDAVIT BY INSURED").getData(),
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Asserting presence of Due Diligence section and header
			Assertions.addInfo("Scenario 08", "Verifying the presence of Due Diligence section");
			Assertions.verify(viewOrPrintFullQuotePage.dueDiligenceSection.checkIfElementIsDisplayed(), true,
					"View print full quote page", "Due Diligence Section is dispalyed", false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.declinationsData.formatDynamicPath("diligent effort requirement")
							.checkIfElementIsDisplayed(),
					true, "View print full quote page",
					"Due Diligence header is verified and dispalyed as : " + viewOrPrintFullQuotePage.declinationsData
							.formatDynamicPath("diligent effort requirement").getData(),
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Click on Go Back Button
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Asserting SLTF Value
			sltfData = accountOverviewPage.sltfValue.getData().replace("$", "");
			actalSltfData = Double.parseDouble(sltfData);
			Assertions.addInfo("Scenario 09", "Calculating and verifying SLTF value in View or print full quote page");
			if (Precision.round(Math.abs(Precision.round(actalSltfData, 2) - Precision.round(sltfFeeValue, 2)),
					2) < 0.05) {
				Assertions.passTest("View print full quote page",
						"Calculated surplus lines taxes and fees : " + "$" + Precision.round(sltfFeeValue, 2));
				Assertions.passTest("View print full quote page",
						"Actual surplus lines taxes and fees : " + "$" + actalSltfData);
			} else {
				Assertions.verify(actalSltfData, sltfFeeValue, "Account Overview Page",
						"The Difference between actual SLTF value and calculated SLTF value is more than 0.05", false,
						false);
			}
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");
			Assertions.passTest("Account Overview page", "Quote Number : "
					+ accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12));

			// Click on Release renewal to producer button
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
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
			testData = data.get(dataValue1);
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Asserting Diligence message
			Assertions.addInfo("Scenario 10", "Verifying Diligence Text Message");
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page Loaded successfully", false, false);
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Diligence Text message is verified and displayed as :" + requestBindPage.diligenceText.getData(),
					false, false);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// click on submit button
			if (requestBindPage.submit.checkIfElementIsPresent()
					&& requestBindPage.submit.checkIfElementIsDisplayed()) {
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
			}

			// click on confirm bind
			requestBindPage.confirmBindNAHO(testData);

			// Asserting warning message for due diligence error message
			Assertions.addInfo("Scenario 11", "Verifying Diligence checkbox error message");
			Assertions.verify(requestBindPage.diligenceCheckboxError.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "Diligence checkbox error message is verified and displayed as :"
							+ requestBindPage.diligenceCheckboxError.getData(),
					false, false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// click on request bind button
			requestBindPage.renewalRequestBindNAHO(testData);

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
			Assertions.failTest("PNBRITC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBRITC002 ", "Executed Successfully");
			}
		}
	}
}
