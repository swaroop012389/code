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
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC046_PNBOOS001 extends AbstractNAHOTest {

	public TC046_PNBOOS001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBOOS001.xls";
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
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		int dataValue1 = 0;
		int dataValue2 = 1;
		int dataValue3 = 2;
		int dataValue4 = 3;
		Map<String, String> testData = data.get(dataValue1);
		Map<String, String> testData1 = data.get(dataValue2);
		Map<String, String> testData2 = data.get(dataValue3);
		Map<String, String> testData3 = data.get(dataValue4);
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

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			String quoteNumber = accountOverviewPage.quoteNumber.getData();
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);

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

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			// Asserting policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Asserting the Policy Effective date
			Assertions.verify(policySummaryPage.transHistEffDate.formatDynamicPath(2).checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"The Policy Effective Date is  " + policySummaryPage.transHistEffDate.formatDynamicPath(2).getData()
							+ " displayed is verified",
					false, false);

			// Process PB Endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			policySummaryPage.endorsePB.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Initiating PB Endorsement to update coverage details
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			Assertions.passTest("Endorse Policy Page",
					"The Transaction Effective Date is " + testData1.get("TransactionEffectiveDate"));
			Assertions.passTest("Endorse Policy Page", "Clicked On Change Coverage options Link");
			endorsePolicyPage.enterEndorsement_DetailsNAHO(testData1, testData.get("ProductSelection"));

			// Asserting the PB Endorsement transaction
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);
			Assertions.verify(policySummaryPage.transHistEffDate.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"The Transaction Effective Date for PB-END is "
							+ policySummaryPage.transHistEffDate.formatDynamicPath(3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(policySummaryPage.transactionType.formatDynamicPath("PB-END").checkIfElementIsDisplayed(),
					true, "Policy Summary Page",
					"The Transaction Type " + policySummaryPage.transactionType.formatDynamicPath("PB-END").getData()
							+ " displayed is verified",
					false, false);

			// Process NPB Endorsement
			policySummaryPage.endorseNPB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorseNPB.scrollToElement();
			policySummaryPage.endorseNPB.click();
			policySummaryPage.endorseNPB.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse NPB link");

			// Initiating NPB Endorsement to Update the AI Details
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			Assertions.passTest("Endorse Policy Page",
					"The Transaction Effective Date is " + testData2.get("TransactionEffectiveDate"));
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Additional Interest Information Link");
			endorsePolicyPage.enterEndorsement_DetailsNAHO(testData2, testData.get("ProductSelection"));

			// Asserting the NPB Endorsement transaction
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);
			Assertions.verify(policySummaryPage.transHistEffDate.formatDynamicPath(5).checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"The Transaction Effective Date for NPB-END is "
							+ policySummaryPage.transHistEffDate.formatDynamicPath(5).getData()
							+ " displayed is verified",
					false, false);
			Assertions
					.verify(policySummaryPage.transactionType.formatDynamicPath("NPB-END").checkIfElementIsDisplayed(),
							true, "Policy Summary Page",
							"The Transaction Type "
									+ policySummaryPage.transactionType.formatDynamicPath("NPB-END").getData()
									+ " displayed is verified",
							false, false);
			Assertions.verify(policySummaryPage.transHistReason.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "This Transaction has " + testData2.get("TransactionType") + " is verified",
					false, false);
			Assertions.verify(policySummaryPage.transHistReason.formatDynamicPath(4).checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"The Transaction Reason " + policySummaryPage.transHistReason.formatDynamicPath(4).getData()
							+ " displayed is verified",
					false, false);

			// Process PB Endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			policySummaryPage.endorsePB.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Policy Summary Page", "Clicked On Endorse PB Link");

			// Initiating PB Endorsement to Update coverage details
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			Assertions.passTest("Endorse Policy Page",
					"The Transaction Effective Date is " + testData3.get("TransactionEffectiveDate"));
			Assertions.passTest("Endorse Policy Page", "Clicked On Change Coverage options Link");
			endorsePolicyPage.enterEndorsement_DetailsNAHO(testData3, testData.get("ProductSelection"));

			// Asserting the Reversal of transaction History
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page Loaded successfully", false, false);
			Assertions.verify(policySummaryPage.transHistEffDate.formatDynamicPath(6).checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"The Transaction Effective Date for PB-END "
							+ policySummaryPage.transHistEffDate.formatDynamicPath(6).getData()
							+ " displayed is verified",
					false, false);
			String pbEndReversal = policySummaryPage.transHistReason.formatDynamicPath(4).getData();

			// Asserting If NPB endorsement is active PB endorsement is reversal
			String npbEndorsement = policySummaryPage.transHistReason.formatDynamicPath(5).getData();
			Assertions.verify(
					policySummaryPage.transactionType.formatDynamicPath("NPB-END").checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"The Transaction History displayed for First PB-END is " + pbEndReversal
							+ " And the Transaction History displayed for "
							+ policySummaryPage.transactionType.formatDynamicPath("NPB-END").getData() + " is "
							+ npbEndorsement + " displayed is verified",
					false, false);

			// Click on 3rd Endorsement History
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on First Endorsement History");

			// Click on View Policy Snapshot Link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot Link");

			// Asserting the Changed Value From Policy Snapshot Page
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy SnapShot Page", "View Policy SnapShot Page loaded successfully", false, false);

			String namedstromValue1stEnmt = viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(19)
					.getData();
			Assertions.verify(namedstromValue1stEnmt.contains(testData1.get("NamedStormValue")), true,
					"View Policy SnapShot Page", "The Named Storm Value Changed for the First Endorsement is "
							+ namedstromValue1stEnmt + " displayed is verified",
					false, false);

			// Click on Back button
			viewPolicySnapShot.goBackButton.click();
			Assertions.passTest("View Policy Snapshot Page", "Clicked on Back Button");

			// Click on 3rd Endorsement History
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(2).click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on Reversal Endorsement History");

			// Click on View Policy Snapshot Link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot Link");

			// Asserting the Changed Value From Policy Snapshot Page
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy SnapShot Page", "View Policy SnapShot Page loaded successfully", false, false);

			String namedstromInitialValue = viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(19)
					.getData();
			Assertions.verify(namedstromInitialValue.contains(testData.get("NamedStormValue")), true,
					"View Policy SnapShot Page", "The Named Storm Value Changed Back to  " + namedstromInitialValue
							+ " After NPB-END displayed is verified",
					false, false);

			// Click on Back button
			viewPolicySnapShot.goBackButton.click();
			Assertions.passTest("View Policy Snapshot Page", "Clicked on Back Button");

			// Click on 3rd Endorsement History
			policySummaryPage.endorsementTransaction.formatDynamicPath(4).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(4).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(4).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(4).click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on Third Endorsement History");

			// Click on View Policy Snapshot Link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View PolicySnapshot Link");

			// Asserting the Changed Value From Policy Snapshot Page
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy SnapShot Page", "View Policy SnapShot Page loaded successfully", false, false);

			String namedstromValue3rdEnmt = viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(19)
					.getData();
			Assertions.verify(namedstromValue3rdEnmt.contains(testData3.get("NamedStormValue")), true,
					"View Policy SnapShot Page", "The Named Storm Value Changed for the 3rd Endorsement is "
							+ namedstromValue3rdEnmt + " displayed is verified",
					false, false);

			// sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC046 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC046 ", "Executed Successfully");
			}
		}
	}

}
