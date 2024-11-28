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

public class TC028_PNBC005 extends AbstractNAHOTest {

	public TC028_PNBC005() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBC005.xls";
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
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
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

			// Click on EndorsePB Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// Entering Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// Click on Change Coverage Option link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Coverage Options link");

			// Changing All Coverage Values
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page Loaded Successfully", false, false);

			testData = data.get(dataValue2);
			createQuotePage.enterInsuredValuesNAHO(testData, dataValue2, dataValue2);

			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click On complete Button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting the Changed Values From Endorse Summary Page
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true,
					"Endorse Summary Page", "Endorse Summary Page Loaded Successfully", false, false);

			String covAfrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData();
			String covAto = endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Coverage A Value " + covAfrom + " changed to : " + covAto + " displayed is verified", false,
					false);

			String covBfrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(11).getData();
			String covBto = endorsePolicyPage.endorsementSummary.formatDynamicPath(12).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(10).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Coverage B Value " + covBfrom + " changed to : " + covBto + " displayed is verified", false,
					false);

			String covCfrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(16).getData();
			String covCto = endorsePolicyPage.endorsementSummary.formatDynamicPath(17).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(15).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Coverage C Value " + covCfrom + " changed to : " + covCto + " displayed is verified", false,
					false);

			String covDfrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(21).getData();
			String covDto = endorsePolicyPage.endorsementSummary.formatDynamicPath(22).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(20).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Coverage D Value " + covDfrom + " changed to : " + covDto + " displayed is verified", false,
					false);

			String covEfrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(26).getData();
			String covEto = endorsePolicyPage.endorsementSummary.formatDynamicPath(27).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(25).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Coverage E Value " + covEfrom + " changed to : " + covEto + " displayed is verified", false,
					false);

			String covFfrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(31).getData();
			String covFto = endorsePolicyPage.endorsementSummary.formatDynamicPath(32).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(30).checkIfElementIsDisplayed(),
					true, "Endorse Summary Page",
					"The Coverage F Value " + covFfrom + " changed to : " + covFto + " displayed is verified", false,
					false);
			// Click on Complete Button
			endorsePolicyPage.scrollToBottomPage();
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Click on Close button
			endorsePolicyPage.scrollToBottomPage();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Go to Home page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Search the policy
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// Click on Policy Snapshot link
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully ", false, false);

			// Click on Endorsement Transaction
			policySummaryPage.transHistReason.formatDynamicPath(3).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(3).click();

			// Click on Policy snapshot link
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();

			// Asserting the Default values from Policy Snapshot page
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successully", false, false);

			String increasedBppValue = viewPolicySnapShot.endorsementValues.formatDynamicPath(4).getData();
			Assertions.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath(4).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Increased Limits on BPP value " + increasedBppValue + " displayed is verified", false, false);

			String poolEnclosureValue = viewPolicySnapShot.limitedWaterBackup.getData();
			Assertions.verify(viewPolicySnapShot.limitedWaterBackup.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page",
					"The Pool Enclosure Buy-Up value " + poolEnclosureValue + " displayed is verified", false, false);

			String lossAssesmentValue = viewPolicySnapShot.lossAssessment.getData();
			Assertions.verify(viewPolicySnapShot.lossAssessment.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page",
					"The Loss Assesment value " + lossAssesmentValue + " displayed is verified", false, false);

			String ordinanceValue = viewPolicySnapShot.endorsementValues.formatDynamicPath(6).getData();
			Assertions.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath(6).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Ordinance or Law value " + ordinanceValue + " displayed is verified", false, false);

			String moldValue = viewPolicySnapShot.moldBuyUp.getData();
			Assertions.verify(viewPolicySnapShot.moldBuyUp.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page",
					"The Mold Property and Mold liability Value " + moldValue + " displayed is verified", false, false);

			// Asserting the Changed coverages
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(11).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Coverage A value changed : "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(11).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(12).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Coverage B value changed : "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(12).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(13).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Coverage C value changed : "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(13).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(14).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Coverage D value changed : "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(14).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(15).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Coverage E value changed : "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(15).getData()
							+ " displayed is verified",
					false, false);

			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(16).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Coverage F value changed "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(16).getData()
							+ " displayed is verified",
					false, false);

			// sign out as Usm
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC028 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC028 ", "Executed Successfully");
			}
		}
	}

}
