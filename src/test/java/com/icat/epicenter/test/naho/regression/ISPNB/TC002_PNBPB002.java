package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC002_PNBPB002 extends AbstractNAHOTest {

	public TC002_PNBPB002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBPB002.xls";
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
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();

		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);

		String policyNumber;
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
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page Loaded successfully", false, false);

			// Adding the ticket IO-20475
			Assertions.verify(requestBindPage.commissionRate.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"The Commission Rate " + requestBindPage.commissionRate.getData() + " displayed is verified", false,
					false);
			requestBindPage.enterBindDetailsNAHO(testData);

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

			// Validating the premium amount
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on PB endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			policySummaryPage.endorsePB.waitTillInVisibilityOfElement(60);

			// Click on Edit Location or Building Information
			testData = data.get(data_Value2);
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.feeOnlyEndorsement.waitTillPresenceOfElement(60);
			endorsePolicyPage.feeOnlyEndorsement.waitTillVisibilityOfElement(60);
			endorsePolicyPage.feeOnlyEndorsement.waitTillElementisEnabled(60);
			endorsePolicyPage.feeOnlyEndorsement.waitTillButtonIsClickable(60);
			endorsePolicyPage.feeOnlyEndorsement.scrollToElement();
			endorsePolicyPage.feeOnlyEndorsement.click();

			// Update inspection and policy fees
			overridePremiumAndFeesPage.enterOverrideFeesDetails(testData);
			Assertions.passTest("Endorse Policy Page", "Override Fees details entered successfully");

			// Assert updated inspection fee in endorse summary page
			Assertions.verify(
					endorseSummaryDetailsPage.policyLevelChangesToColNAHO.formatDynamicPath(1, 5).getData()
							.contains("520"),
					true, "Endorse Summary Details Page",
					"Inspection fee is updated to: "
							+ endorseSummaryDetailsPage.policyLevelChangesToColNAHO.formatDynamicPath(1, 5).getData(),
					false, false);

			// Assert updated policy fee in endorse summary page
			Assertions.verify(
					endorseSummaryDetailsPage.policyLevelChangesToColNAHO.formatDynamicPath(2, 5).getData()
							.contains("600"),
					true, "Endorse Summary Details Page",
					"Policy fee is updated to: "
							+ endorseSummaryDetailsPage.policyLevelChangesToColNAHO.formatDynamicPath(2, 5).getData(),
					false, false);

			// Assert dwelling address update in Endorsement summary page
			endorsePolicyPage.viewEndorsementQuote.waitTillVisibilityOfElement(60);
			endorsePolicyPage.viewEndorsementQuote.scrollToElement();
			endorsePolicyPage.viewEndorsementQuote.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on view endorsement quote button successfully");

			// Assert inspection policy fee update
			Assertions.verify(
					endorseSummaryDetailsPage.coverageAndPremiumValues.formatDynamicPath("Inspection Fee", "1")
							.getData().contains("520"),
					true, "Endorse Quote Details page",
					"Inspection Fee is updated to " + endorseSummaryDetailsPage.coverageAndPremiumValues
							.formatDynamicPath("Inspection Fee", "1").getData(),
					false, false);

			// Updated new total fee to 1100
			Assertions.verify(
					endorseSummaryDetailsPage.coverageAndPremiumValues.formatDynamicPath("Policy Fee", "1").getData()
							.contains("600"),
					true, "Endorse Quote Details page",
					"Policy Fee is updated to " + endorseSummaryDetailsPage.coverageAndPremiumValues
							.formatDynamicPath("Policy Fee", "1").getData(),
					false, false);
			endorseSummaryDetailsPage.scrollToTopPage();
			endorseSummaryDetailsPage.waitTime(3);
			endorseSummaryDetailsPage.closeBtn.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// Complete the endorsement
			endorsePolicyPage.completeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.completeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.completeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");
			endorsePolicyPage.scrollToBottomPage();
			endorsePolicyPage.closeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// Adding Code for IO-19318
			// Click on PB endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			policySummaryPage.endorsePB.waitTillInVisibilityOfElement(60);

			// Click on Edit Location or Building Information
			testData = data.get(data_Value3);
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillElementisEnabled(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on changecoverage option link successfully");

			// Navigate and change values in Quote Option page
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Entered Quote details successfully");

			// EndorsePolicyPage
			endorsePolicyPage.nextButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on next button successfully");

			// Overrides
			createQuotePage.continueButton.waitTillPresenceOfElement(60);
			createQuotePage.continueButton.waitTillVisibilityOfElement(60);
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();

			// Endorse Policy Page
			endorsePolicyPage.completeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			// Endorse Summary Page
			endorseSummaryDetailsPage.closeBtn.waitTillVisibilityOfElement(60);
			endorseSummaryDetailsPage.closeBtn.scrollToElement();
			endorseSummaryDetailsPage.closeBtn.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// View Endorsed Policy
			policySummaryPage.transHistReason.formatDynamicPath(4).waitTillVisibilityOfElement(60);
			policySummaryPage.transHistReason.formatDynamicPath(4).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(4).click();
			Assertions.passTest("Policy summary Page", "Clicked on sencod endorsement link successfully");

			// Scroll To Transaction details and Assert Transaction premium value
			policySummaryPage.transactionPremium.waitTillPresenceOfElement(60);
			policySummaryPage.transactionPremium.waitTillVisibilityOfElement(60);
			policySummaryPage.transactionPremium.scrollToElement();
			Assertions.verify(policySummaryPage.transactionPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Policy Summary Page loaded successfully. Transaction Premium is "
							+ policySummaryPage.transactionPremium.getData(),
					false, false);

			// Reverse Last Endorsement
			policySummaryPage.reversePreviousEndorsementLink.waitTillPresenceOfElement(60);
			policySummaryPage.reversePreviousEndorsementLink.waitTillVisibilityOfElement(60);
			policySummaryPage.reversePreviousEndorsementLink.scrollToElement();
			policySummaryPage.reversePreviousEndorsementLink.click();
			Assertions.passTest("Policy summary Page", "Clicked on reverseprevious endorsement link successfully");

			// Endorse Policy Page
			endorsePolicyPage.completeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			// Endorse Summary Page
			endorseSummaryDetailsPage.closeBtn.waitTillVisibilityOfElement(60);
			endorseSummaryDetailsPage.closeBtn.scrollToElement();
			endorseSummaryDetailsPage.closeBtn.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on close button successfully");

			// View Endorsed Policy
			policySummaryPage.transHistReason.formatDynamicPath(5).waitTillVisibilityOfElement(60);
			policySummaryPage.transHistReason.formatDynamicPath(5).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(5).click();
			Assertions.passTest("Policy summary Page", "Clicked on reversal endorsement link successfully");

			// Scroll To Transaction details and Assert Transaction premium value
			policySummaryPage.transactionPremium.waitTillPresenceOfElement(60);
			policySummaryPage.transactionPremium.waitTillVisibilityOfElement(60);
			policySummaryPage.transactionPremium.scrollToElement();
			Assertions.verify(policySummaryPage.transactionPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Policy Summary Page loaded successfully. Reversed Transaction Premium is "
							+ policySummaryPage.transactionPremium.getData(),
					false, false);

			// Adding code for IO-18763
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
				testData = data.get(data_Value1);
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
			// Adding Code for IO-19410
			// Asserting Other Deductible Options table Total Premium values
			accountOverviewPage.quoteOptions4TotalPremium.formatDynamicPath(2).waitTillVisibilityOfElement(60);
			Assertions.verify(
					accountOverviewPage.quoteOptions4TotalPremium.formatDynamicPath(2).checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Quote Options Total Premium 1 is displayed successfully : "
							+ accountOverviewPage.quoteOptions4TotalPremium.formatDynamicPath(2).getData(),
					false, false);
			Assertions.verify(
					accountOverviewPage.quoteOptions4TotalPremium.formatDynamicPath(3).checkIfElementIsDisplayed(),
					true, "Account Overview Page",
					"Quote Options Total Premium 2 is displayed successfully : "
							+ accountOverviewPage.quoteOptions4TotalPremium.formatDynamicPath(3).getData(),
					false, false);

			// Asserting status on renewal quote
			Assertions.verify(accountOverviewPage.referredStatus.getData(), "Referred", "Account Overview Page",
					"Renewal quote status is " + accountOverviewPage.referredStatus.getData() + " When Roof Age is "
							+ testData.get("L1D1-DwellingYearBuilt") + " and Roof Cladding is "
							+ testData.get("L1D1-DwellingRoofCladding") + " while renewal is processed",
					false, false);
			accountOverviewPage.waitTime(3);
			Assertions.verify(accountOverviewPage.noteBarMessage.checkIfElementIsDisplayed(), true,
					"Account Overview Page", accountOverviewPage.noteBarMessage.getData() + " is displayed in note bar",
					false, false);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC002 ", "Executed Successfully");
			}
		}
	}
}
