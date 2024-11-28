/** Program Description:  Check the generation of Renewal documents and Lapse notice in View renewal documents folder and IO-21801
 *
 *  Author			   :
 *  Date of Creation   : 08/09/2024
 **/

package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
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
import com.icat.epicenter.pom.ViewDocumentsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC069_PNBREN003 extends AbstractNAHOTest {

	public TC069_PNBREN003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBREN003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewDocumentsPage viewDocumentsPage = new ViewDocumentsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		int data_Value1 = 0;
		int data_Value2 = 1;
		String renewalQuoteNumber;
		Map<String, String> testData = data.get(data_Value1);
		String covAvalue;
		String inflationGuardPercentage;
		double calCovAValue;
		String actualCovAValue;
		String calCovAValue_s;
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

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details entered successfully");

			// get policyNumber
			String policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"PolicyNumber is " + policyNumber, false, false);

			// Renew policy
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

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

			// Get renewal quote status
			Assertions.verify(accountOverviewPage.referredStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Renewal quote status is " + accountOverviewPage.referredStatus.getData(),
					false, false);
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Approve Referral
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();

			// go to home page and open renewal
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchPolicy(policyNumber);

			// Click on View Active renewal link
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();

			// Adding IO-21801
			// Calculating inflation guard applying to Cov A
			covAvalue = testData.get("L1D1-DwellingCovA");
			inflationGuardPercentage = testData.get("InflationGuardPercentage");
			calCovAValue = (Double.parseDouble(covAvalue) * Double.parseDouble(inflationGuardPercentage));
			Assertions.passTest("View/Print Full Quote Page",
					"NB Coverage A value " + testData.get("L1D1-DwellingCovA"));
			Assertions.passTest("View/Print Full Quote Page",
					"Inflation Guard Percentage " + testData.get("InflationGuardPercentage"));

			// Rounding off
			long roundCalCovAValue = Math.round(calCovAValue);

			// Converting double to string
			calCovAValue_s = Double.toString(roundCalCovAValue).replace(".0", "");

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("View/Print Full Quote Page", "Clicked on vieworprint full quote link");

			// Verifying and Asserting actual and calculated Cov A value
			// Cov A = Cov A*inflationguard%(1.0713)
			Assertions.addInfo("Scenario 01", "Verifying inflation guard applied to renewal Cov A");
			actualCovAValue = viewOrPrintFullQuotePage.coveragesValues.formatDynamicPath(4).getData().replace(",", "");
			Assertions.verify(actualCovAValue, "$" + calCovAValue_s, "View Print Full Quote Page",
					"Inflation guard " + testData.get("InflationGuardPercentage")
							+ " applied to Coverage A, actual coverage A value " + actualCovAValue
							+ " and calculated coverage A value $" + calCovAValue_s + " bothe are same",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			// IO-21801 Ended

			// Get renewal quote status and release renewal to producer button
			Assertions.verify(accountOverviewPage.internalRenewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Renewal quote status is " + accountOverviewPage.internalRenewalStatus.getData(), false, false);
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Release Renewal to Producer is displayed", false, false);

			// Click on view documents
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();

			// Add assertions
			viewDocumentsPage.refreshPage();
			Assertions.passTest("Scenario 02", "Verifying absence of Producer and Insured document copy");
			Assertions.verify(
					viewDocumentsPage.documents.formatDynamicPath("_P.").checkIfElementIsPresent()
							&& viewDocumentsPage.documents.formatDynamicPath("_P.").checkIfElementIsDisplayed(),
					false, "View Documents Page", "Producer Copy is not displayed", false, false);
			Assertions.verify(
					viewDocumentsPage.documents.formatDynamicPath("_I.").checkIfElementIsPresent()
							&& viewDocumentsPage.documents.formatDynamicPath("_I.").checkIfElementIsDisplayed(),
					false, "View Documents Page", "Insured Copy is not displayed", false, false);
			viewDocumentsPage.backBtn.scrollToElement();
			viewDocumentsPage.backBtn.click();
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on Release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer Button");
			Assertions.verify(accountOverviewPage.lapseRenewal.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Lapse Renewal Button is displayed", false, false);
			Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Renewal quote status is " + accountOverviewPage.renewalStatus.getData(),
					false, false);

			// Click on view documents
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();

			// Add assertions
			viewDocumentsPage.waitTime(10);
			viewDocumentsPage.refreshPage();
			viewDocumentsPage.backBtn.scrollToElement();
			viewDocumentsPage.backBtn.click();
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();
			viewDocumentsPage.waitTime(10);
			viewDocumentsPage.refreshPage();
			viewDocumentsPage.backBtn.scrollToElement();
			viewDocumentsPage.backBtn.click();

			// Add assertions
			viewDocumentsPage.waitTime(10);
			viewDocumentsPage.refreshPage();
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();
			while (!viewDocumentsPage.documents.formatDynamicPath("_P.").checkIfElementIsPresent()
					|| !viewDocumentsPage.documents.formatDynamicPath("_I.").checkIfElementIsPresent()) {
				viewDocumentsPage.refreshPage();
			}

			Assertions.addInfo("Scenario 03", "Verifying presence of producer and insured document copy");
			Assertions.verify(
					viewDocumentsPage.documents.formatDynamicPath("_P.").checkIfElementIsPresent()
							&& viewDocumentsPage.documents.formatDynamicPath("_P.").checkIfElementIsDisplayed(),
					true, "View Documents Page",
					viewDocumentsPage.documents.formatDynamicPath("_P.").getData() + " is displayed", false, false);
			viewDocumentsPage.documents.formatDynamicPath("_I.").waitTillVisibilityOfElement(60);
			Assertions.verify(
					viewDocumentsPage.documents.formatDynamicPath("_I.").checkIfElementIsPresent()
							&& viewDocumentsPage.documents.formatDynamicPath("_I.").checkIfElementIsDisplayed(),
					true, "View Documents Page",
					viewDocumentsPage.documents.formatDynamicPath("_I.").getData() + " is displayed", false, false);
			Assertions.addInfo("Scenaro 03", "Scenario 03 Ended");
			viewDocumentsPage.backBtn.scrollToElement();
			viewDocumentsPage.backBtn.click();

			// create another quote
			testData = data.get(data_Value2);
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			String renewalQuoteNumber2 = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + renewalQuoteNumber2);

			// Assert issue quote and click
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Issue Quote button is displayed", false, false);
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Issue Quote Button");
			Assertions.verify(accountOverviewPage.lapseRenewal.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Lapse Renewal Button is displayed", false, false);
			Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Renewal quote status is " + accountOverviewPage.renewalStatus.getData(),
					false, false);

			// Click on view documents
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();
			viewDocumentsPage.waitTime(10);
			viewDocumentsPage.refreshPage();
			viewDocumentsPage.backBtn.scrollToElement();
			viewDocumentsPage.backBtn.click();

			// Add assertions
			viewDocumentsPage.waitTime(10);
			viewDocumentsPage.refreshPage();
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();
			while (!viewDocumentsPage.documents.formatDynamicPath("_P.").checkIfElementIsPresent()
					|| !viewDocumentsPage.documents.formatDynamicPath("_I.").checkIfElementIsPresent()) {
				viewDocumentsPage.refreshPage();
			}
			Assertions.addInfo("Scenario 04", "Verifying presence of producer and insured document copy");
			Assertions.verify(viewDocumentsPage.documents.formatDynamicPath("_P.").getNoOfWebElements(), 1,
					"View Documents Page",
					viewDocumentsPage.documents.formatDynamicPath("_P.").getData() + " is displayed", false, false);
			Assertions.verify(viewDocumentsPage.documents.formatDynamicPath("_I.").getNoOfWebElements(), 1,
					"View Documents Page",
					viewDocumentsPage.documents.formatDynamicPath("_I.").getData() + " is displayed", false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			viewDocumentsPage.backBtn.scrollToElement();
			viewDocumentsPage.backBtn.click();

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.searchQuote(renewalQuoteNumber2);
			// Click on lapse renewal
			accountOverviewPage.lapseRenewal.scrollToElement();
			accountOverviewPage.lapseRenewal.click();
			if (accountOverviewPage.yesIWantToContinue.checkIfElementIsPresent()
					&& accountOverviewPage.yesIWantToContinue.checkIfElementIsDisplayed()) {
				accountOverviewPage.yesIWantToContinue.scrollToElement();
				accountOverviewPage.yesIWantToContinue.click();
			}
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.searchQuote(renewalQuoteNumber2);

			Assertions.passTest("Account Overview Page", "Clicked on Lapse Renewal Button");
			Assertions.verify(accountOverviewPage.unlapseRenewal.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "UnLapse Renewal Button is displayed", false, false);
			Assertions.verify(accountOverviewPage.expiredStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Renewal quote status is " + accountOverviewPage.expiredStatus.getData(),
					false, false);

			// Click on view documents
			accountOverviewPage.viewRenewalDocuments.scrollToElement();
			accountOverviewPage.viewRenewalDocuments.click();

			// Add assertions
			viewDocumentsPage.waitTime(3);
			viewDocumentsPage.refreshPage();
			while (!viewDocumentsPage.documents.formatDynamicPath("LAPSE").checkIfElementIsPresent()) {
				viewDocumentsPage.refreshPage();
			}

			Assertions.addInfo("Scenario 05", "Verifying presence of lapse document");
			Assertions.verify(viewDocumentsPage.documents.formatDynamicPath("LAPSE").getNoOfWebElements(), 2,
					"View Documents Page",
					viewDocumentsPage.documents.formatDynamicPath("LAPSE").getData() + " is displayed", false, false);
			Assertions.addInfo("Scenario 05", "scenario 05 Ended");
			viewDocumentsPage.backBtn.scrollToElement();
			viewDocumentsPage.backBtn.click();

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.searchQuote(renewalQuoteNumber2);

			// Click on unlapse renewal
			accountOverviewPage.unlapseRenewal.scrollToElement();
			accountOverviewPage.unlapseRenewal.click();
			Assertions.passTest("Account Overview Page", "Clicked on UnLapse Renewal Button");

			Assertions.verify(accountOverviewPage.renewalStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Renewal quote status is " + accountOverviewPage.renewalStatus.getData(),
					false, false);

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.searchQuote(renewalQuoteNumber2);

			// getting renewal quote number
			renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + renewalQuoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);

			// Clicking on answer no button
			if (underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsPresent()
					&& underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed()) {
				underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
				Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
						"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
				underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
				underwritingQuestionsPage.answerNoToAllQuestions.click();

				// Clicking on save button
				underwritingQuestionsPage.saveButton.scrollToElement();
				underwritingQuestionsPage.saveButton.click();
			}

			// Entering bind details
			requestBindPage.floodCoverageArrow.scrollToElement();
			requestBindPage.floodCoverageArrow.click();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy"))
					.scrollToElement();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy")).click();
			requestBindPage.contactEmailAddress.scrollToElement();
			requestBindPage.contactEmailAddress.setData(testData.get("ProducerEmail"));
			requestBindPage.dueDiligenceCheckbox.scrollToElement();
			requestBindPage.dueDiligenceCheckbox.select();
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.waitTime(2);
			requestBindPage.requestBind.waitTillVisibilityOfElement(60);
			requestBindPage.requestBind.scrollToElement();
			requestBindPage.requestBind.click();

			// Click on overrride check box
			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.scrollToElement();
				requestBindPage.overrideEffectiveDate.select();
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
				requestBindPage.waitTime(2);
				requestBindPage.requestBind.waitTillVisibilityOfElement(60);
				requestBindPage.requestBind.scrollToElement();
				requestBindPage.requestBind.click();
			}
			Assertions.passTest("Request Bind Page", "Bind Details entered successfully");

			if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
					&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {
				// Clicking on home page button
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(renewalQuoteNumber2);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// Approve Referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Referral page
				Assertions.passTest("Referral Page", "Quote referral approved successfully");
				requestBindPage.approveRequestNAHO(testData);
			}

			// Asserting Renewal Policy Number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Renewal PolicyNumber is " + policySummaryPage.policyNumber.getData(), false, false);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC069 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC069 ", "Executed Successfully");
			}
		}
	}
}
